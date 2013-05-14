package com.gracefulcode.LD48;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.gracefulcode.LD48.difficulty.Difficulty;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;
import com.gracefulcode.LD48.views.LD48View;
import com.gracefulcode.LD48.views.PauseView;

public class GameLevel extends GameLevelBase {
	protected int levelNum;
	protected Difficulty difficulty;
	protected Paintbrush paintbrush;
	protected Random random;
	protected Array<Array<TileActor>> buttons;
	protected Array<Vector2> resetData;
	protected Sound clickSound;
	protected int tileSize;
	protected Image backgroundImage;
	protected float elapsedTime;
	protected boolean paused = false;
	private PauseView pauseView;

	public GameLevel(int levelNum, Skin skin, LD48 ld48, Difficulty difficulty, Paintbrush paintbrush) {
		super(skin, ld48);
		this.elapsedTime = 0;
		this.random = new Random();
		this.clickSound = Gdx.audio.newSound(Gdx.files.internal("data/Blip_Select.wav"));
		this.levelNum = levelNum;
		this.difficulty = difficulty;
		this.paintbrush = paintbrush;
		this.buttons = new Array<Array<TileActor>>();

		this.addDrawable(0, new Color(1.0f, 1.0f, 1.0f, 1.0f));
		this.addDrawable(1, new Color(35.0f / 255.0f, 56.0f / 255.0f, 47.0f / 255.0f, 1.0f));
		this.addDrawable(2, new Color(83.0f / 255.0f, 140.0f / 255.0f, 81.0f / 255.0f, 1.0f));
		this.addDrawable(3, new Color(162.0f / 255.0f, 93.0f / 255.0f, 0.0f / 255.0f, 1.0f));
		this.addDrawable(4, new Color(242.0f / 255.0f, 135.0f / 255.0f, 5.0f / 255.0f, 1.0f));
		this.addDrawable(5, new Color(246.0f / 255.0f, 205.0f / 255.0f, 91.0f / 255.0f, 1.0f));
		
		Pixmap t = this.skin.get("button0", Pixmap.class);
		
		Pixmap p = new Pixmap((int)this.getWidth(), (int)this.getHeight(), Pixmap.Format.RGB888);
		
		for (int x = 0; x < this.getWidth(); x += t.getWidth()) {
			for (int y = 0; y < this.getHeight(); y += t.getHeight()) {
				p.drawPixmap(t, x, y);
			}
		}
		
		Texture tt = new Texture(p);
		this.backgroundImage = new Image(tt);
		this.addActor(this.backgroundImage);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.MENU || keycode == Keys.ESCAPE) {
			if (this.paused) {
				this.paused = false;
				this.pauseView.remove();
			} else {
				this.paused = true;
				this.pauseView = new PauseView(this.skin);
				this.addActor(pauseView);
				pauseView.setSize(this.getWidth(), this.getHeight());
			}
		}
		return true;
	}

	@Override
	public void act(float delta) {
		if (!this.paused)
			this.elapsedTime += delta;
		super.act(delta);
	}

	private void addDrawable(int id, Color c) {
		Pixmap p = new Pixmap(GraphicsConfiguration.tileSize, GraphicsConfiguration.tileSize, Pixmap.Format.RGB888);
		p.setColor(c);
		p.fill();

		p.setColor(new Color(0f, 0f, 0f, 1.0f));
		p.drawLine(0,  0, GraphicsConfiguration.tileSize,  0);
		p.drawLine(0, 0, 0, GraphicsConfiguration.tileSize);
		
		this.skin.add("button" + id, p);

		Texture t = new Texture(p);		
		this.skin.add("button" + id, t);
	}

	@Override
	public boolean isRealLevel() {
		return true;
	}

	public void resetSound() {
		this.clickSound.stop();
		this.clickSound.play(this.volume);
	}

	public void setSound(int diff) {
		if (this.numSounds == 0 && diff > 0) {
			this.clickSound.play(this.volume);
		}
		
		this.numSounds += diff;
		
		if (numSounds <= 0) {
			this.clickSound.stop();
		}
	}

	protected Vector2 getRandomVector() {
		int x = this.random.nextInt((int)Math.floor(this.getWidth() / tileSize));
		int y = this.random.nextInt((int)Math.floor(this.getHeight() / tileSize));
		
		return new Vector2(x, y);
	}
	
	protected void doPulse(Vector2 tmp) {
		this.paintbrush.pulse(this.getTile((int)tmp.x, (int)tmp.y), 0, 1, true);
	}
	
	protected void doRandoms(int numRandoms) {
		this.resetData = new Array<Vector2>();
		for (int i = 0; i < numRandoms; i++) {
			Vector2 tmp = this.getRandomVector();
			this.resetData.add(tmp);
			this.doPulse(tmp);
		}		
	}
	
	@Override
	public void initialize() {
		int numRandoms = this.difficulty.numRandoms(this.levelNum);
		this.tileSize = this.difficulty.tileSize();		
		this.bestKnown = numRandoms;
		
		this.buttons.clear();
		for (int x = 0; x < Gdx.graphics.getWidth(); x += tileSize) {
			Array<TileActor> tmpArray = new Array<TileActor>();
			this.buttons.add(tmpArray);
			
			for (int y = 0; y < Gdx.graphics.getHeight(); y += tileSize) {
				TileActor button = new TileActor(this.skin, ld48, x / this.tileSize,  y / this.tileSize, 6, this);
				button.setSize(tileSize, tileSize);
				button.setColor(new Color(1, 1, 1, 1));
				button.setPosition(LD48.fixX(x),  LD48.fixY(y));
				this.paintbrush.setupPulse(button);
				this.addActor(button);
				
				tmpArray.add(button);
			}
		}
		
		this.doRandoms(numRandoms);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		int amount = 1;
		if (button == 0) {
			amount = -1;
		}
		
		y = (int)(this.getHeight() - y);
		TileActor a = this.getTile((int)Math.floor(x / this.tileSize), (int)Math.floor(y / this.tileSize));
		this.getPaintbrush().pulse(a, 0, amount, false);
		this.numClicks++;
		return true;
	}
	
	public TileActor getTile(int x, int y) {
		if (x < 0)
			return null;
		
		if (x > this.buttons.size - 1)
			return null;
		
		Array<TileActor> tmp = this.buttons.get(x);
		
		if (y < 0)
			return null;
		
		if (y > tmp.size - 1)
			return null;
		
		return tmp.get(y);
	}

	public void reset() {
		this.numClicks = 0;
		
		for (int x = 0; x < this.getWidth(); x += tileSize) {
			for (int y = 0; y < this.getHeight(); y += tileSize) {
				TileActor button = this.getTile((int)Math.floor(x / tileSize), (int)Math.floor(y / tileSize));
				button.setCount(0);
			}
		}
		
		Iterator<Vector2> it = this.resetData.iterator();
		while (it.hasNext()) {
			Vector2 tmp = it.next();
			TileActor button = this.getTile((int)tmp.x, (int)tmp.y);
			this.paintbrush.pulse(button,  0,  1,  true);
		}
	}

	public boolean isDone() {
		for (int i = 0; i < this.buttons.size; i++) {
			Array<TileActor> taa = this.buttons.get(i);
			
			for (int m = 0; m < taa.size; m++) {
				TileActor ta = taa.get(m);
				
				if (ta.getCount() != 0)
					return false;
			}
		}
		this.clickSound.stop();
		
		return true;
	}

	public int getLevelNum() {
		return this.levelNum;
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public Paintbrush getPaintbrush() {
		return this.paintbrush;
	}

	public float getTime() {
		return this.elapsedTime;
	}
}
