package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.difficulty.Difficulty;

public class Tutorial extends GameLevel {
	public Tutorial(Skin skin, LD48 ld48, Difficulty difficulty, Paintbrush paintbrush) {
		super(0, skin, ld48, difficulty, paintbrush);
	}

	@Override
	public boolean isRealLevel() {
		return false;
	}
}
