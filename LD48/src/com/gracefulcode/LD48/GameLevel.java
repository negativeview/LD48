package com.gracefulcode.LD48;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class GameLevel extends Stage {
	public Array<Color> colors;
	private Random random;
	private Array<Array<TileActor>> buttons;
	private Skin skin;
	public int levelNum;
	public LD48 ld48;
	private Sound clickSound;
	public int bestKnown;
	public int numSounds = 0;
	public int numClicks = 0;
	public int tileSize;
	public float volume = 0.1f;
	public float time = 0.0f;
	
	private Array<Vector2> resetData;
	private long startTime;
	public Difficulty difficulty;
	public Paintbrush paintbrush;
	
	public GameLevel(int levelNum, Skin skin, LD48 ld48, Difficulty difficulty, Paintbrush paintbrush) {
		this.colors = new Array<Color>();
		this.random = new Random();
		this.buttons = new Array<Array<TileActor>>();
		this.skin = skin;
		this.levelNum = levelNum;
		this.difficulty = difficulty;
		this.paintbrush = paintbrush;
		this.ld48 = ld48;
		this.clickSound = Gdx.audio.newSound(Gdx.files.internal("data/Blip_Select.wav"));
		
		Label l = new Label("Your goal is to clean up all\n" +
				"this clutter and return the\nboard to a pristine, white\n" +
				"state.\n" +
				"\n" +
				"Clicking a tile will change the\n" +
				"color of it and all tiles in the\n" +
				"same row or column.", this.skin);
		l.setWrap(true);
		l.setWidth(150);
		
		this.colors.add(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		this.colors.add(new Color(35.0f / 255.0f, 56.0f / 255.0f, 47.0f / 255.0f, 1.0f));
		this.colors.add(new Color(83.0f / 255.0f, 140.0f / 255.0f, 81.0f / 255.0f, 1.0f));
		this.colors.add(new Color(162.0f / 255.0f, 93.0f / 255.0f, 0.0f / 255.0f, 1.0f));
		this.colors.add(new Color(242.0f / 255.0f, 135.0f / 255.0f, 5.0f / 255.0f, 1.0f));
		this.colors.add(new Color(246.0f / 255.0f, 205.0f / 255.0f, 91.0f / 255.0f, 1.0f));
	}
	
	public boolean isRealLevel() {
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			this.ld48.pause();
		}
		return true;
	}
	
	public void draw() {
		super.draw();
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
	
	public void startTiming() {
		this.startTime = System.nanoTime();
	}
	
	public void initialize() {
		final GameLevel gl = this;
		ChangeListener cl = new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				gl.paintbrush.pulse((TileActor)actor, 0, -1, false);
				if (gl.numClicks == 0) {
					gl.startTiming();
				}
				gl.numClicks++;
			}
		};
		
		ClickListener cll = new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (button == 1) {
					TileActor a = gl.getTile((int)Math.floor(x / gl.tileSize), (int)Math.floor(y / gl.tileSize));
					gl.paintbrush.pulse(a, 0, 1, false);
					return true;
				}
				
				return false;
			}
		};
		this.addListener(cll);
		
		int numRandoms = this.difficulty.numRandoms(this.levelNum);
		this.tileSize = this.difficulty.tileSize();		
		this.bestKnown = numRandoms;
		
		for (int x = 0; x < this.getWidth(); x += tileSize) {
			Array<TileActor> tmpArray = new Array<TileActor>();
			this.buttons.add(tmpArray);
			
			for (int y = 0; y < this.getHeight(); y += tileSize) {
				TileActor button = new TileActor(this.skin, ld48, x / this.tileSize,  y / this.tileSize, this.colors, this);
				button.setPossibleColors(this.colors);
				button.setSize(tileSize, tileSize);
				button.setColor(new Color(1, 1, 1, 1));
				button.setPosition(x,  y);
				this.addActor(button);
				button.addListener(cl);
				
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
		
		this.time = (System.nanoTime() - this.startTime) / 1000000000;
		return true;
	}

	public void resetSound() {
		this.clickSound.stop();
		this.clickSound.play(this.volume);
	}
}
