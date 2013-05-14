package com.gracefulcode.LD48.animation.actions;

import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.TileActor;

public class PlusCenterAction extends BaseAction {
	public PlusCenterAction(int pulseSize, float pulseDuration, int difference, GameLevel level) {
		super(pulseSize, pulseDuration, difference, level);
	}

	@Override
	public boolean act(float delta) {
		boolean done = super.act(delta);
		
		TileActor actor = (TileActor)this.getActor();
		
		if (done) {
			TileActor actor2 = actor.level.getTile(actor.x - 1, actor.y);
			if (actor2 != null)
				actor2.addAction(new PulseLeftAction(this.pulseSize, this.pulseDuration, this.difference, this.level));
			
			actor2 = actor.level.getTile(actor.x + 1, actor.y);
			if (actor2 != null)
				actor2.addAction(new PulseRightAction(this.pulseSize, this.pulseDuration, this.difference, this.level));
			
			actor2 = actor.level.getTile(actor.x, actor.y + 1);
			if (actor2 != null)
				actor2.addAction(new PulseUpAction(this.pulseSize, this.pulseDuration, this.difference, this.level));
			
			actor2 = actor.level.getTile(actor.x, actor.y - 1);
			if (actor2 != null)
				actor2.addAction(new PulseDownAction(this.pulseSize, this.pulseDuration, this.difference, this.level));
			
			actor.level.setSound(-1);
			return true;
		}

		return false;
	}
}
