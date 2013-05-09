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
	private Preferences preferences;

	public LevelSelection(LD48 ld48, Skin skin, final MainMenuContainer mainMenuContainer, Paintbrush paintbrush) {
		this.skin = skin;
		this.ld48 = ld48;
		
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
		
		float interval = Gdx.graphics.getHeight() / 13.0f;
		
		float i = interval;
		
		if (paintbrush.hasTutorialLevel()) {
			TextButton tb = new TextButton("Tutorial", this.skin, "menuButton");
			this.addActor(tb);
			tb.setPosition(0, Gdx.graphics.getHeight() - i - (interval * 3));
			tb.setSize(MainMenuContainer.MENU_WIDTH, interval * 3);
			TutorialCallbackHack tch = new TutorialCallbackHack(this.ld48, paintbrush.getTutorial(new Difficulty()));
			tb.addListener(tch);
			
			i += (interval * 4);
		}

		TextButton tb = new TextButton("Level 1", this.skin, "menuButton");
		this.addActor(tb);
		tb.setPosition(0, Gdx.graphics.getHeight() - i - (interval * 3));
		tb.setSize(MainMenuContainer.MENU_WIDTH, interval * 3);
		LevelCallbackHack pch = new LevelCallbackHack(this.ld48, paintbrush, this.skin, 1);
		tb.addListener(pch);
		
		i += (interval * 4);

		if (maxLevel != 0) {
			tb = new TextButton("Level " + maxLevel, this.skin, "menuButton");
			this.addActor(tb);
			tb.setPosition(0, Gdx.graphics.getHeight() - i - (interval * 3));
			tb.setSize(MainMenuContainer.MENU_WIDTH, interval * 3);
			pch = new LevelCallbackHack(this.ld48, paintbrush, this.skin, maxLevel);
			tb.addListener(pch);
		}
		

		tb = new TextButton("Back", this.skin, "menuButton");
		tb.setPosition(0, 0);
		this.addActor(tb);
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mainMenuContainer.popView();
			}
		};
		tb.setSize(100, interval);
		tb.addListener(cl);
	}
	
	private class TutorialCallbackHack extends ChangeListener {
		private Tutorial t;
		private LD48 ld48;
		
		public TutorialCallbackHack(LD48 ld48, Tutorial t) {
			this.ld48 = ld48;
			this.t = t;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			this.ld48.gotoGame(this.t);
		}
		
	}

	private class LevelCallbackHack extends ChangeListener {
		private LD48 ld48;
		private Paintbrush paintbrush;
		private Skin skin;
		private int level;
		
		public LevelCallbackHack(LD48 ld48, Paintbrush p, Skin skin, int level) {
			this.ld48 = ld48;
			this.paintbrush = p;
			this.skin = skin;
			this.level = level;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			try {
				GameLevel gl = new GameLevel(this.level, this.skin, this.ld48, new Difficulty(), paintbrush);
				ld48.gotoGame(gl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
