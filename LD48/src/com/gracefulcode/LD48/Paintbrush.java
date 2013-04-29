package com.gracefulcode.LD48;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeByAction;

public class Paintbrush {

	public String name;
	public boolean diagonalsEnabled;

	public void pulse(final TileActor actor, final int direction, final int value, final boolean instant) {
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
						if (tmp.ld48.getTile(tmp.x - 1, tmp.y) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x - 1, tmp.y), 1, value, false);
					
					if (direction == 0 || direction == 2)
						if (tmp.ld48.getTile(tmp.x + 1, tmp.y) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x + 1, tmp.y), 2, value, false);
					
					if (direction == 0 || direction == 3)
						if (tmp.ld48.getTile(tmp.x, tmp.y - 1) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x, tmp.y - 1), 3, value, false);
					
					if (direction == 0 || direction == 4)
						if (tmp.ld48.getTile(tmp.x, tmp.y + 1) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x, tmp.y + 1), 4, value, false);
					
					if (tmp2.diagonalsEnabled) {
						if (direction == 0 || direction == 5) {
							if (tmp.ld48.getTile(tmp.x + 1, tmp.y + 1) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x + 1, tmp.y + 1), 5, value, false);
						}
						if (direction == 0 || direction == 6) {
							if (tmp.ld48.getTile(tmp.x + 1, tmp.y - 1) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x + 1, tmp.y - 1), 6, value, false);
						}
						if (direction == 0 || direction == 7) {
							if (tmp.ld48.getTile(tmp.x - 1, tmp.y + 1) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x - 1, tmp.y + 1), 7, value, false);
						}
						if (direction == 0 || direction == 8) {
							if (tmp.ld48.getTile(tmp.x - 1, tmp.y - 1) != null) tmp2.pulse(tmp.ld48.getTile(tmp.x - 1, tmp.y - 1), 8, value, false);
						}
					}
					
					tmp.level.setSound(-1);
					return true;
				}
			};
			
			sa.addAction(action);
			actor.toFront();
			actor.addAction(sa);
		} else {
			if (direction == 0 || direction == 1)
				if (actor.ld48.getTile(actor.x - 1, actor.y) != null) this.pulse(actor.ld48.getTile(actor.x - 1, actor.y), 1, value, true);
			
			if (direction == 0 || direction == 2)
				if (actor.ld48.getTile(actor.x + 1, actor.y) != null) this.pulse(actor.ld48.getTile(actor.x + 1, actor.y), 2, value, true);
			
			if (direction == 0 || direction == 3)
				if (actor.ld48.getTile(actor.x, actor.y - 1) != null) this.pulse(actor.ld48.getTile(actor.x, actor.y - 1), 3, value, true);
			
			if (direction == 0 || direction == 4)
				if (actor.ld48.getTile(actor.x, actor.y + 1) != null) this.pulse(actor.ld48.getTile(actor.x, actor.y + 1), 4, value, true);

			if (this.diagonalsEnabled) {
				if (direction == 0 || direction == 5) {
					if (actor.ld48.getTile(actor.x + 1, actor.y + 1) != null) this.pulse(actor.ld48.getTile(actor.x + 1, actor.y + 1), 5, value, true);
				}
				if (direction == 0 || direction == 6) {
					if (actor.ld48.getTile(actor.x + 1, actor.y - 1) != null) this.pulse(actor.ld48.getTile(actor.x + 1, actor.y - 1), 6, value, true);
				}
				if (direction == 0 || direction == 7) {
					if (actor.ld48.getTile(actor.x - 1, actor.y + 1) != null) this.pulse(actor.ld48.getTile(actor.x - 1, actor.y + 1), 7, value, true);
				}
				if (direction == 0 || direction == 8) {
					if (actor.ld48.getTile(actor.x - 1, actor.y - 1) != null) this.pulse(actor.ld48.getTile(actor.x - 1, actor.y - 1), 8, value, true);
				}
			}
		}
	}
}
