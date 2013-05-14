package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.TileActor;

public class SpiralPaintbrush extends Paintbrush {
	public SpiralPaintbrush(Skin skin, LD48 ld48) {
		super(skin, ld48);
		this.name = "spiral";
	}
	
	public Image getImage() {
		FileHandle fh = Gdx.files.internal("data/spiral.png");
		Texture tex = new Texture(fh);
		Image im = new Image(tex);
		return im;
	}

	public String getDescription() {
		return "The most recent. No idea\nhow hard it is yet.";
	}

	@Override
	public void pulse(TileActor actor, final int direction, final int value, boolean instant, GameLevel level) {
		this.pulse(actor, direction, value, instant, 0, 1, level);
	}
		
	public void pulse(TileActor actor, int direction, final int value, boolean instant, int count, int target, GameLevel level) {
		actor.changeCount(value);
		
//		if (!instant) {
//		} else {
			if (count >= target) {
				direction += 1;
				count = 0;
				target++;
			}
			
			if (direction >= 4)
				direction -= 4;
			
			switch(direction) {
			case 0:
				if (actor.level.getTile(actor.x, actor.y - 1) != null)
					this.pulse(actor.level.getTile(actor.x, actor.y - 1), direction, value, instant, count + 1, target, level);
				break;
			case 1:
				if (actor.level.getTile(actor.x + 1, actor.y) != null)
					this.pulse(actor.level.getTile(actor.x + 1, actor.y), direction, value, instant, count + 1, target, level);
				break;
			case 2:
				if (actor.level.getTile(actor.x, actor.y + 1) != null)
					this.pulse(actor.level.getTile(actor.x, actor.y + 1), direction, value, instant, count + 1, target, level);
				break;
			case 3:
				if (actor.level.getTile(actor.x - 1, actor.y) != null)
					this.pulse(actor.level.getTile(actor.x - 1, actor.y), direction, value, instant, count + 1, target, level);
				break;
			}
//		}
	}
}
