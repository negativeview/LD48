package com.gracefulcode.LD48;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class GameLevelBase extends Stage {
	protected Skin skin;
	public LD48 ld48;
	public int bestKnown;
	public int numSounds = 0;
	public int numClicks = 0;
	public float volume = 0.1f;
	public float time = 0.0f;
	
	public GameLevelBase(Skin skin, LD48 ld48) {
		this.skin = skin;
		this.ld48 = ld48;				
	}
	
	public boolean isRealLevel() {
		return false;
	}
	
	public boolean isRecapScreen() {
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			this.ld48.pause();
		}
		return true;
	}	
	
	public boolean isDone() {
		return false;
	}
}
