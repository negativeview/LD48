package com.gracefulcode.LD48.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;

public class RecapScreen extends GameLevel {
	private Skin skin;
	private Label label;
	private Button button;
	private boolean done = false;
	
	public RecapScreen(Skin skin, LD48 ld48, GameLevel level) {
		super(0, skin, ld48, level.difficulty, level.paintbrush);	
		this.skin = skin;
		
		this.label = new Label("YOU JUST BEAT LEVEL " + level.levelNum, this.skin);
		this.label.setPosition(30, 340);
		this.addActor(this.label);
		
		this.label = new Label("You did it in " + level.numClicks + " clicks!!!", this.skin);
		this.label.setPosition(30, 300);
		this.addActor(this.label);

		this.label = new Label("(The best known-possible score is " + level.bestKnown + ")", this.skin);
		this.label.setPosition(30, 260);
		this.addActor(this.label);
		
		this.label = new Label("It took you " + (int)level.time + ((int)level.time == 1 ? " second" : " seconds"), this.skin);
		this.label.setPosition(30, 220);
		this.addActor(this.label);
		
		this.button = new TextButton("Continue", this.skin);
		this.button.setPosition(30, 30);		
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
	public void reset() {
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
