package com.gracefulcode.LD48.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.GameLevelBase;
import com.gracefulcode.LD48.LD48;

public class RecapScreen extends GameLevelBase {
	private Skin skin;
	private Label label;
	private Button button;
	private boolean done = false;
	private GameLevel realLevel;
	
	public RecapScreen(Skin skin, LD48 ld48, GameLevel level) {
		super(skin, ld48);	
		this.skin = skin;
		this.realLevel = level;

		Preferences preferences = Gdx.app.getPreferences("maxLevels");
		int maxLevel = preferences.getInteger(level.getPaintbrush().name, 0);
		
		if (maxLevel < level.getLevelNum()) {
			preferences.putInteger(level.getPaintbrush().name, level.getLevelNum());
			preferences.flush();
		}

		this.label = new Label("YOU JUST BEAT LEVEL " + level.getLevelNum(), this.skin);
		this.label.setPosition(30, 340);
		this.addActor(this.label);
		
		this.label = new Label("Clicks: " + level.numClicks, this.skin);
		this.label.setPosition(30, 300);
		this.addActor(this.label);

		this.label = new Label("Optimal: " + level.bestKnown, this.skin);
		this.label.setPosition(30, 260);
		this.addActor(this.label);
		
		this.label = new Label("Time: " + (int)level.getTime() + ((int)level.getTime() == 1 ? " second" : " seconds"), this.skin);
		this.label.setPosition(30, 220);
		this.addActor(this.label);
		
		this.button = new TextButton("Continue", this.skin, "small");
		this.button.setPosition(30, 30);
		this.button.pad(10.0f);
		this.button.setWidth(200);
		this.button.setHeight(100);
		this.addActor(button);
		
		final RecapScreen tmp = this;
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
	}

	@Override
	public boolean isRecapScreen() {
		return true;
	}
	
	public GameLevel getRealLevel() {
		return this.realLevel;
	}
	
	@Override
	public boolean isRealLevel() {
		return false;
	}

	@Override
	public boolean isDone() {
		return this.done;
	}
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		super.draw();
	}
}
