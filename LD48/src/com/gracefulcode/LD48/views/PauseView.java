package com.gracefulcode.LD48.views;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseView extends LD48View {
	private Skin skin;
	private Image backgroundImage;
	private TextButton exitButton;
	private TextButton mainMenuButton;
	private TextButton resetButton;
	
	public PauseView(Skin skin) {
		this.skin = skin;
		this.backgroundImage = new Image(this.skin.getDrawable("blackImage"));
		this.addActor(this.backgroundImage);
		
		this.exitButton = new TextButton("Exit", this.skin, "menuButton");
		this.addActor(this.exitButton);

		this.mainMenuButton = new TextButton("Main Menu", this.skin, "menuButton");
		this.addActor(this.mainMenuButton);
		
		this.resetButton = new TextButton("Reset", this.skin, "menuButton");
		this.addActor(this.resetButton);
	}

	@Override
	public void act(float delta) {
		this.backgroundImage.setPosition(300, 0);
		this.backgroundImage.setSize(this.getWidth() - 600, this.getHeight());

		this.exitButton.setSize(300,  100);
		this.exitButton.setPosition(300, 30);
		
		this.mainMenuButton.setSize(300,  100);
		this.mainMenuButton.setPosition(300, 60);
		
		this.resetButton.setSize(300, 100);
		this.resetButton.setPosition(300,  90);
		
		super.act(delta);
	}
}
