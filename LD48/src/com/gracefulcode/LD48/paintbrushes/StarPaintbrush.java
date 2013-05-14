package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.TileActor;
import com.gracefulcode.LD48.animation.actions.StarCenterAction;
import com.gracefulcode.LD48.difficulty.Difficulty;

public class StarPaintbrush extends Paintbrush {
	public StarPaintbrush(Skin skin, LD48 ld48) {
		super(skin, ld48);
		this.name = "star";
	}
	
	public Image getImage() {
		FileHandle fh = Gdx.files.internal("data/flower.png");
		Texture tex = new Texture(fh);
		Image im = new Image(tex);
		return im;
	}

	public String getDescription() {
		return "This pattern is more\ncomplex and fun once\nyou master the basics.";
	}

	@Override
	public boolean hasTutorialLevel() {
		return true;
	}
	
	@Override
	public Tutorial getTutorial(Difficulty difficulty) {
		return new StarModeTutorial(this.skin, this.ld48, difficulty, this);
	}

	@Override
	public void pulse(TileActor actor, final int direction, final int value, boolean instant, GameLevel level) {
		actor.changeCount(value);
		
		if (!instant) {
			if (direction != 0) {
				actor.level.setSound(1);
			} else {
				actor.level.resetSound();
				actor.level.setSound(1);
			}
			
			Action sa = new StarCenterAction((int)(actor.getWidth() / 2), 0.02f, value, level);
			actor.addAction(sa);
		} else {
			if (direction == 0 || direction == 1)
				if (actor.level.getTile(actor.x - 1, actor.y) != null) this.pulse(actor.level.getTile(actor.x - 1, actor.y), 1, value, true, level);
			
			if (direction == 0 || direction == 2)
				if (actor.level.getTile(actor.x + 1, actor.y) != null) this.pulse(actor.level.getTile(actor.x + 1, actor.y), 2, value, true, level);
			
			if (direction == 0 || direction == 3)
				if (actor.level.getTile(actor.x, actor.y - 1) != null) this.pulse(actor.level.getTile(actor.x, actor.y - 1), 3, value, true, level);
			
			if (direction == 0 || direction == 4)
				if (actor.level.getTile(actor.x, actor.y + 1) != null) this.pulse(actor.level.getTile(actor.x, actor.y + 1), 4, value, true, level);

			if (direction == 0 || direction == 5) {
				if (actor.level.getTile(actor.x + 1, actor.y + 1) != null) this.pulse(actor.level.getTile(actor.x + 1, actor.y + 1), 5, value, true, level);
			}
			if (direction == 0 || direction == 6) {
				if (actor.level.getTile(actor.x + 1, actor.y - 1) != null) this.pulse(actor.level.getTile(actor.x + 1, actor.y - 1), 6, value, true, level);
			}
			if (direction == 0 || direction == 7) {
				if (actor.level.getTile(actor.x - 1, actor.y + 1) != null) this.pulse(actor.level.getTile(actor.x - 1, actor.y + 1), 7, value, true, level);
			}
			if (direction == 0 || direction == 8) {
				if (actor.level.getTile(actor.x - 1, actor.y - 1) != null) this.pulse(actor.level.getTile(actor.x - 1, actor.y - 1), 8, value, true, level);
			}
		}
	}
}
