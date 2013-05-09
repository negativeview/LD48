package com.gracefulcode.LD48.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.LD48;

public class MainMenu extends LD48View {
	private Skin skin;
	private TextButton newGameButton;
	private TextButton exitButton;
	private Label gameTitleLabel;
	private Label creditLabel;
	
	public MainMenu(final LD48 ld48, final Skin skin, final MainMenuContainer mainMenuContainer) throws Exception {
		this.skin = skin;
		
		if (ld48 == null) {
			throw new Exception();
		}
		
		ChangeListener cl;
		
		this.gameTitleLabel = new Label("Exploding Boxes of Cleanliness", this.skin);
		this.gameTitleLabel.setPosition(25, LD48.fixY(Gdx.graphics.getHeight() - (40 * 2) + 4));
		this.addActor(this.gameTitleLabel);
		
		this.creditLabel = new Label("by Daniel Grace", this.skin);
		this.creditLabel.setPosition(350, LD48.fixY(Gdx.graphics.getHeight() - (40 * 3) + 4));
		this.addActor(this.creditLabel);
		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				try {
					mainMenuContainer.addLD48View(new PaintbrushSelection(ld48, skin, mainMenuContainer));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		this.newGameButton = new TextButton("New Game", this.skin, "menuButton");
		this.newGameButton.setPosition(0, LD48.fixY(Gdx.graphics.getHeight() - (40 * 7) + 8));
		this.newGameButton.setSize(MainMenuContainer.MENU_WIDTH, 120);
		this.newGameButton.addListener(cl);
		this.addActor(this.newGameButton);

		this.exitButton = new TextButton("Exit", this.skin, "menuButton");		
		this.exitButton.setPosition(0, LD48.fixY(40));
		if (LD48.TILE_SIZE == 40) {
			this.exitButton.setSize(MainMenuContainer.MENU_WIDTH, LD48.TILE_SIZE * 3);
		} else {
			this.exitButton.setSize(MainMenuContainer.MENU_WIDTH, LD48.TILE_SIZE);			
		}
		this.addActor(this.exitButton);
	}
}
