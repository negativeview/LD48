package com.gracefulcode.LD48.paintbrushes;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
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

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		int realX = (int)Math.floor(x / this.tileSize);
		int realY = (int)Math.floor((int)(this.getHeight() - y) / this.tileSize);
		
		Iterator<Vector2> it = this.resetData.iterator();
		while (it.hasNext()) {
			Vector2 tmp = it.next();
			if (tmp.x == realX && tmp.y == realY) {
				super.touchDown(x, y, pointer, button);
			}
		}
		
		return false;
	}

	@Override
	protected void doPulse(Vector2 tmp) {
		this.paintbrush.pulse(this.getTile((int)tmp.x, (int)tmp.y), 0, 1, false, this);
	}
}
