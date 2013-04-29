package com.gracefulcode.LD48;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TutorialScreen extends GameLevel {
	private Label label;
	private Button button;
	private Skin skin;
	private boolean done = false;
	
	public TutorialScreen(Skin skin, LD48 ld48) {
		super(0, skin, ld48, null, null);
		
		this.skin = skin;
		
		this.label = new Label("Exploding Cubes of Cleanliness", this.skin);
		this.label.setPosition(10, this.getHeight() - 30);		
		this.addActor(label);
		
		this.label = new Label("A puzzle game by Daniel Grace", this.skin);
		this.label.setPosition(10, this.getHeight() - 70);		
		this.addActor(label);
		
		this.label = new Label("Made for Ludum Dare 26", this.skin);
		this.label.setPosition(10, this.getHeight() - 90);		
		this.addActor(label);

		this.label = new Label("Your goal is to rid the game world of all of the colorful cubes and return the world to its most minimal state.", this.skin);
		this.label.setPosition(10, this.getHeight() - 130);
		this.addActor(label);
		
		this.setupNormal();
		this.setupFlower();
		
		final TutorialScreen tmp = this;
		TextButton b = new TextButton("Colorblind Mode", this.skin, "checkable");
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.colorBlindMode = ((Button)actor).isChecked();
			}
		};
		b.addListener(cl);
		b.setPosition(900, 10);
		this.addActor(b);
	}
	
	public void setupNormal() {
		final TutorialScreen tmp = this;
		
		Texture tex1 = new Texture(Gdx.files.internal("data/plus.png"));
		Image image1 = new Image(tex1);
		image1.setPosition(100,  100);
		this.addActor(image1);
		
		this.button = new TextButton("YouTube Commentator", this.skin);
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new Difficulty();
				tmp.ld48.paintbrush = new Paintbrush();
				tmp.ld48.paintbrush.name = "plus";
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(10, 10);
		this.addActor(button);

		this.button = new TextButton("Normal", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new EasyDifficulty();
				tmp.ld48.paintbrush = new Paintbrush();
				tmp.ld48.paintbrush.name = "plus";
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(170, 10);
		this.addActor(button);

		this.button = new TextButton("Harder", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new HarderDifficulty();
				tmp.ld48.paintbrush = new Paintbrush();
				tmp.ld48.paintbrush.name = "plus";
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(228, 10);
		this.addActor(button);
	}

	public void setupFlower() {
		final TutorialScreen tmp = this;
		
		Texture tex1 = new Texture(Gdx.files.internal("data/flower.png"));
		Image image1 = new Image(tex1);
		image1.setPosition(500,  100);
		this.addActor(image1);
		
		this.button = new TextButton("YouTube Commentator", this.skin);		
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new Difficulty();
				tmp.ld48.paintbrush = new Paintbrush();
				tmp.ld48.paintbrush.name = "flower";
				tmp.ld48.paintbrush.diagonalsEnabled = true;
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(410, 10);
		this.addActor(button);

		this.button = new TextButton("Normal", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new EasyDifficulty();
				tmp.ld48.paintbrush = new Paintbrush();
				tmp.ld48.paintbrush.diagonalsEnabled = true;
				tmp.ld48.paintbrush.name = "flower";
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(570, 10);
		this.addActor(button);

		this.button = new TextButton("Harder", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new HarderDifficulty();
				tmp.ld48.paintbrush = new Paintbrush();
				tmp.ld48.paintbrush.diagonalsEnabled = true;
				tmp.ld48.paintbrush.name = "flower";
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(628, 10);
		this.addActor(button);
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
