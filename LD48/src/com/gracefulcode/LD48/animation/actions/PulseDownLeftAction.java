package com.gracefulcode.LD48.animation.actions;

import com.gracefulcode.LD48.TileActor;

public class PulseDownLeftAction extends BaseAction {
	public PulseDownLeftAction(int pulseSize, float pulseDuration, int difference) {
		super(pulseSize, pulseDuration, difference);
	}

	@Override
	public boolean act(float delta) {
		boolean done = super.act(delta);
		
		TileActor actor = (TileActor)this.getActor();
		
		if (actor == null)
			return false;
		
		if (actor.level == null)
			return false;
		
		if (done) {
			actor.changeCount(this.difference);
			TileActor actor2 = actor.level.getTile(actor.x - 1, actor.y - 1);
			if (actor2 != null) {
				this.setActor(actor2);
				actor2.addAction(this);
				this.restart();
			} else {
				return true;
			}
		}

		return false;
	}
}
