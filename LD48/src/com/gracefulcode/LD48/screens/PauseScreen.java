package com.gracefulcode.LD48.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;

public class PauseScreen extends Stage {
	private Skin skin;
	private Image backgroundImage;

	public PauseScreen(Skin skin, final LD48 ld48) {
		this.skin = skin;
		this.backgroundImage = new Image(this.skin.getDrawable("blackImage"));
		this.backgroundImage.setPosition(300, 0);
		this.backgroundImage.setSize(this.getWidth() - 600, this.getHeight());
		this.addActor(this.backgroundImage);
		
		TextButton tb = new TextButton("Unpause", this.skin);
		tb.setPosition(310, 10);
		this.addActor(tb);
		
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ld48.unpause();
			}
		};
		tb.addListener(cl);

		tb = new TextButton("Reset Level", this.skin);
		tb.setPosition(460, 10);
		this.addActor(tb);
		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameLevel level = (GameLevel)ld48.stage;
				level.reset();
				ld48.isPaused = false;
			}
		};
		tb.addListener(cl);
		
		Label l = new Label("Color Progression:", this.skin);
		l.setPosition(310, this.getHeight() - 40);
		this.addActor(l);
	}
}
