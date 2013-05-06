package com.gracefulcode.LD48.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.difficulty.Difficulty;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;
import com.gracefulcode.LD48.paintbrushes.Tutorial;

public class LevelSelection extends LD48View {
	private Skin skin;
	private LD48 ld48;
	private MainMenuContainer mainMenuContainer;
	private Preferences preferences;

	public LevelSelection(LD48 ld48, Skin skin, final MainMenuContainer mainMenuContainer, Paintbrush paintbrush) {
		this.skin = skin;
		this.ld48 = ld48;
		this.mainMenuContainer = mainMenuContainer;
		
		this.preferences = Gdx.app.getPreferences("maxLevels");
		int maxLevel = this.preferences.getInteger(paintbrush.name, 0);
		
		if (maxLevel == 0) {
			if (paintbrush.hasTutorialLevel()) {
				Tutorial t = paintbrush.getTutorial(new Difficulty());
				ld48.gotoGame(t);
			} else {
				GameLevel gl = new GameLevel(0, this.skin, this.ld48, new Difficulty(), paintbrush);
				ld48.gotoGame(gl);
			}
			return;
		}
		

		TextButton tb = new TextButton("Back", this.skin, "menuButton");
		tb.setPosition(20, 20);
		this.addActor(tb);
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mainMenuContainer.popView();
			}
		};
		tb.addListener(cl);
	}
}
