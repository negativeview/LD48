package com.gracefulcode.LD48.paintbrushes;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.difficulty.Difficulty;

public class PlusModeTutorial extends Tutorial {
	Window window;
	Label label;
	
	public PlusModeTutorial(Skin skin, LD48 ld48, Difficulty difficulty, Paintbrush paintbrush) {
		super(skin, ld48, difficulty, paintbrush);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		int realX = (int)Math.floor(x / this.tileSize);
		int realY = (int)Math.floor((int)(this.getHeight() - y) / this.tileSize);
		
		Iterator<Vector2> it = this.resetData.iterator();
		while (it.hasNext()) {
			Vector2 tmp = it.next();
			if (tmp.x == realX && tmp.y == realY) {
				super.touchDown(x, y, pointer, button);
			}
		}
		
		return false;
	}
	
	private void positionTutorialOne() {
		Vector2 centralPoint = this.resetData.first();
		
		int numTiles = (int)Math.floor(this.getWidth() / this.tileSize);
		
		if (centralPoint.x < numTiles / 2) {
			int x = (int)((centralPoint.x * this.tileSize) + this.tileSize + 10);
			this.window.setX(x);
		} else {
			int x = (int) ((centralPoint.x * this.tileSize) - this.window.getWidth()) - 10;
			this.window.setX(x);
		}
		
		numTiles = (int)Math.floor(this.getHeight() / this.tileSize);
		if (centralPoint.y < numTiles / 2) {
			int y = (int)((centralPoint.y * this.tileSize) + this.tileSize + 10);
			this.window.setY(y);
		} else {
			int y = (int)((centralPoint.y * this.tileSize)- this.window.getHeight()) - 10;
			this.window.setY(y);
		}
	}

	@Override
	public void initialize() {
		super.initialize();
		
		this.window = new Window("Tutorial", skin);
		this.window.setPosition(10,  10);
		this.window.setSize(200, 200);
		this.window.padLeft(0);
		
		this.label = new Label("It is your goal to undo\nwhat the computer has\ndone.\n\nClick on the center of the\nplus in order to do so.", skin, "small");
		this.window.add(this.label).left();
		this.addActor(this.window);
		
		this.positionTutorialOne();
	}

}
