package com.gracefulcode.LD48.animation.actions;

import com.gracefulcode.LD48.TileActor;

public class StarCenterAction extends BaseAction {
	public StarCenterAction(int pulseSize, float pulseDuration, int difference) {
		super(pulseSize, pulseDuration, difference);
	}

	@Override
	public boolean act(float delta) {
		boolean done = super.act(delta);
		
		TileActor actor = (TileActor)this.getActor();
		
		if (done) {
			TileActor actor2 = actor.level.getTile(actor.x - 1, actor.y);
			if (actor2 != null)
				actor2.addAction(new PulseLeftAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x + 1, actor.y);
			if (actor2 != null)
				actor2.addAction(new PulseRightAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x, actor.y + 1);
			if (actor2 != null)
				actor2.addAction(new PulseUpAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x, actor.y - 1);
			if (actor2 != null)
				actor2.addAction(new PulseDownAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x - 1, actor.y - 1);
			if (actor2 != null)
				actor2.addAction(new PulseDownLeftAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x - 1, actor.y + 1);
			if (actor2 != null)
				actor2.addAction(new PulseUpLeftAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x + 1, actor.y - 1);
			if (actor2 != null)
				actor2.addAction(new PulseDownRightAction(this.pulseSize, this.pulseDuration, this.difference));
			
			actor2 = actor.level.getTile(actor.x + 1, actor.y + 1);
			if (actor2 != null)
				actor2.addAction(new PulseUpRightAction(this.pulseSize, this.pulseDuration, this.difference));
			
			return true;
		}

		return false;
	}

}
