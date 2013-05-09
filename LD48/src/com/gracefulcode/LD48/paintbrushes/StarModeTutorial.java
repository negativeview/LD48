package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.difficulty.Difficulty;

public class StarModeTutorial extends Tutorial {
	private Window window;
	private Label label;
	private int mode = 1;
	
	public StarModeTutorial(Skin skin, LD48 ld48, Difficulty difficulty, StarPaintbrush paintbrush) {
		super(skin, ld48, difficulty, paintbrush);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		this.window = new Window("Tutorial", skin);
		this.window.setPosition(10,  10);
		this.window.setSize(200, 200);
		this.window.padLeft(0);
		
		switch (this.mode) {
		case 1:
			this.label = new Label("It is your goal to undo\nwhat the computer has\ndone.\n\nClick on the center of the\nstar in order to do so.", skin, "small");
			break;
		case 2:
			this.label = new Label("As things progress,\nthe computer will take\nmore actions.\n\nYou will need to figure\nout what it did.", skin, "small");
			break;
		case 3:
			this.label = new Label("Patterns will form that\nyou will soon begin\nto recognize.", skin, "small");
		}
		
		this.window.add(this.label).left();
		this.addActor(this.window);
		this.positionTutorialOne();
	}
	
	@Override
	protected void doRandoms(int numRandoms) {
		this.resetData = new Array<Vector2>();

		int offset = (int) Math.ceil(240 / LD48.TILE_SIZE);

		Vector2 tmp;
		switch (this.mode) {
		case 1:
			tmp = new Vector2(1, 1);
			this.resetData.add(tmp);
			this.doPulse(tmp);
			break;
		case 2:
			tmp = new Vector2(1, 1);
			this.resetData.add(tmp);
			this.doPulse(tmp);
			
			tmp = new Vector2(2 + offset, 2 + offset);
			this.resetData.add(tmp);
			this.doPulse(tmp);
			break;
		case 3:
			tmp = new Vector2(1, 1);
			this.resetData.add(tmp);
			this.doPulse(tmp);
			
			tmp = new Vector2(2 + offset, 1);
			this.resetData.add(tmp);
			this.doPulse(tmp);
		}
	}
	
	public boolean isDone() {
		if (super.isDone()) {
			this.mode++;
			if (this.mode >= 4)
				return true;
			this.initialize();
		}
		
		return false;
	}

	@Override
	public boolean isRealLevel() {
		if (this.mode <= 3)
			return false;
		
		return true;
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
}
