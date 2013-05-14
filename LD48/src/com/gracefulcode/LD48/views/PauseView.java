package com.gracefulcode.LD48.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.LD48;

public class PauseView extends LD48View {
	private Skin skin;
	private Image backgroundImage;
	private TextButton exitButton;
	private TextButton mainMenuButton;
	private TextButton resetButton;
	
	public PauseView(Skin skin, final LD48 ld48) {
		this.skin = skin;
		this.backgroundImage = new Image(this.skin.getDrawable("blackImage"));
		this.addActor(this.backgroundImage);
		
		ChangeListener cl;
		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("DEBUG", "Should be exiting...");
				// TODO Add confirmation?
				Gdx.app.exit();
			}
			
		};
		
		this.exitButton = new TextButton("Exit", this.skin, "menuButton");
		this.exitButton.addListener(cl);
		this.addActor(this.exitButton);

		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ld48.goBackToMenu();
			}			
		};
		
		this.mainMenuButton = new TextButton("Main Menu", this.skin, "menuButton");
		this.mainMenuButton.addListener(cl);
		this.addActor(this.mainMenuButton);
		
		this.resetButton = new TextButton("Reset", this.skin, "menuButton");
		this.addActor(this.resetButton);
	}

	@Override
	public void act(float delta) {
		this.backgroundImage.setPosition(300, 0);
		this.backgroundImage.setSize(this.getWidth() - 600, this.getHeight());
		this.setColor(0.0f,  0.0f,  0.0f,  0.75f);

		this.exitButton.setSize(this.backgroundImage.getWidth(), 100);
		this.exitButton.setPosition(300, 30);
		
		this.mainMenuButton.setSize(this.backgroundImage.getWidth(), 100);
		this.mainMenuButton.setPosition(300, 140);
		
		this.resetButton.setSize(this.backgroundImage.getWidth(), 100);
		this.resetButton.setPosition(300,  250);
		
		super.act(delta);
	}
}
