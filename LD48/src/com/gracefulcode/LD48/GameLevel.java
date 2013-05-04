package com.gracefulcode.LD48;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.gracefulcode.LD48.difficulty.Difficulty;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;

public class GameLevel extends GameLevelBase {
	private int levelNum;
	private Difficulty difficulty;
	private Paintbrush paintbrush;
	private Random random;
	private Array<Array<TileActor>> buttons;
	private Array<Vector2> resetData;
	private long startTime = 0;
	private Sound clickSound;
	public int tileSize;

	public GameLevel(int levelNum, Skin skin, LD48 ld48, Difficulty difficulty, Paintbrush paintbrush) {
		super(skin, ld48);
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
	}

	private void addDrawable(int id, Color c) {
		Pixmap p = new Pixmap(LD48.TILE_SIZE, LD48.TILE_SIZE, Pixmap.Format.RGB888);
		p.setColor(c);
		p.fill();

		p.setColor(new Color(0f, 0f, 0f, 1.0f));
		p.drawLine(0,  0, LD48.TILE_SIZE,  0);
		p.drawLine(0, 0, 0, LD48.TILE_SIZE);

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
	
	public void initialize() {
		int numRandoms = this.difficulty.numRandoms(this.levelNum);
		this.tileSize = this.difficulty.tileSize();		
		this.bestKnown = numRandoms;
		
		for (int x = 0; x < this.getWidth(); x += tileSize) {
			Array<TileActor> tmpArray = new Array<TileActor>();
			this.buttons.add(tmpArray);
			
			for (int y = 0; y < this.getHeight(); y += tileSize) {
				TileActor button = new TileActor(this.skin, ld48, x / this.tileSize,  y / this.tileSize, 6, this);
				button.setSize(tileSize, tileSize);
				button.setColor(new Color(1, 1, 1, 1));
				button.setPosition(x,  y);
				this.paintbrush.setupPulse(button);
				this.addActor(button);
				
				tmpArray.add(button);
			}
		}
		
		this.resetData = new Array<Vector2>();
		for (int i = 0; i < numRandoms; i++) {
			int x = this.random.nextInt((int)Math.floor(this.getWidth() / tileSize));
			int y = this.random.nextInt((int)Math.floor(this.getHeight() / tileSize));
			
			this.resetData.add(new Vector2(x, y));
			this.paintbrush.pulse(this.getTile(x, y), 0, 1, true);
		}
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		int amount = 1;
		if (button == 0) {
			amount = -1;
		}
		
		if (this.startTime == 0)
			this.startTiming();
		
		y = (int)(this.getHeight() - y);
		TileActor a = this.getTile((int)Math.floor(x / this.tileSize), (int)Math.floor(y / this.tileSize));
		this.getPaintbrush().pulse(a, 0, amount, false);
		this.numClicks++;
		return true;
	}
	
	public void startTiming() {
		this.startTime = System.currentTimeMillis();
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
				button.count = 0;
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
				
				if (ta.count != 0)
					return false;
			}
		}
		this.clickSound.stop();
		
		this.time = (System.currentTimeMillis() - this.startTime) / 1000;
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
}
