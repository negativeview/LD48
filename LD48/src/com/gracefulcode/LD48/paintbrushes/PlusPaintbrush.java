package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gracefulcode.LD48.TileActor;

public class PlusPaintbrush extends Paintbrush {
	public Image getImage() {
		FileHandle fh = Gdx.files.internal("data/plus.png");
		Texture tex = new Texture(fh);
		Image im = new Image(tex);
		return im;
	}

	public String getDescription() {
		return "This is the easiest\npattern.";
	}

	@Override
	public void pulse(TileActor actor, final int direction, final int value, boolean instant) {
		actor.changeCount(value);
		
		if (!instant) {
			float pulseDuration = 0.02f;
			float pulseSize = actor.getHeight() / 2;
			
			if (direction != 0) {
				actor.level.setSound(1);
			} else {
				actor.level.resetSound();
				actor.level.setSound(1);
			}
			
			MoveByAction mba = new MoveByAction();
			mba.setActor(actor);
			mba.setAmount(pulseSize * -0.5f, pulseSize * -0.5f);
			mba.setDuration(pulseDuration);
			
			SizeByAction sba = new SizeByAction();
			sba.setActor(actor);
			sba.setAmount(pulseSize, pulseSize);
			sba.setDuration(pulseDuration);
			
			ParallelAction pa = new ParallelAction();
			pa.addAction(mba);
			pa.addAction(sba);
			
			SequenceAction sa = new SequenceAction();
			sa.addAction(pa);
	
			mba = new MoveByAction();
			mba.setActor(actor);
			mba.setAmount(pulseSize * 0.5f, pulseSize * 0.5f);
			mba.setDuration(pulseDuration);
			
			sba = new SizeByAction();
			sba.setActor(actor);
			sba.setAmount(pulseSize * -1, pulseSize * -1);
			sba.setDuration(pulseDuration);
			
			pa = new ParallelAction();
			pa.addAction(mba);
			pa.addAction(sba);
			
			sa.addAction(pa);
			
			final TileActor tmp = actor;
			final Paintbrush tmp2 = this;
			Action action = new Action() {
				public boolean act(float delta) {
					if (direction == 0 || direction == 1)
						if (tmp.level.getTile(tmp.x - 1, tmp.y) != null) tmp2.pulse(tmp.level.getTile(tmp.x - 1, tmp.y), 1, value, false);
					
					if (direction == 0 || direction == 2)
						if (tmp.level.getTile(tmp.x + 1, tmp.y) != null) tmp2.pulse(tmp.level.getTile(tmp.x + 1, tmp.y), 2, value, false);
					
					if (direction == 0 || direction == 3)
						if (tmp.level.getTile(tmp.x, tmp.y - 1) != null) tmp2.pulse(tmp.level.getTile(tmp.x, tmp.y - 1), 3, value, false);
					
					if (direction == 0 || direction == 4)
						if (tmp.level.getTile(tmp.x, tmp.y + 1) != null) tmp2.pulse(tmp.level.getTile(tmp.x, tmp.y + 1), 4, value, false);
					
					tmp.level.setSound(-1);
					return true;
				}
			};
			
			sa.addAction(action);
			actor.toFront();
			actor.addAction(sa);
		} else {
			if (direction == 0 || direction == 1)
				if (actor.level.getTile(actor.x - 1, actor.y) != null) this.pulse(actor.level.getTile(actor.x - 1, actor.y), 1, value, true);
			
			if (direction == 0 || direction == 2)
				if (actor.level.getTile(actor.x + 1, actor.y) != null) this.pulse(actor.level.getTile(actor.x + 1, actor.y), 2, value, true);
			
			if (direction == 0 || direction == 3)
				if (actor.level.getTile(actor.x, actor.y - 1) != null) this.pulse(actor.level.getTile(actor.x, actor.y - 1), 3, value, true);
			
			if (direction == 0 || direction == 4)
				if (actor.level.getTile(actor.x, actor.y + 1) != null) this.pulse(actor.level.getTile(actor.x, actor.y + 1), 4, value, true);
		}
	}
}
