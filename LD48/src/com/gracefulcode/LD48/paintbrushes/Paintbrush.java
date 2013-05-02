package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gracefulcode.LD48.TileActor;


abstract public class Paintbrush {
	public String name;

	public abstract void pulse(final TileActor actor, final int direction, final int value, final boolean instant);

	public Image getImage() {
		return null;
	}

	public String getDescription() {
		return null;
	}
	
	protected SequenceAction getBaseAction(float pulseSize, float pulseDuration) {
		MoveByAction mba = new MoveByAction();
		mba.setAmount(pulseSize * -0.5f, pulseSize * -0.5f);
		mba.setDuration(pulseDuration);
		
		SizeByAction sba = new SizeByAction();
		sba.setAmount(pulseSize, pulseSize);
		sba.setDuration(pulseDuration);
		
		ParallelAction pa = new ParallelAction();
		pa.addAction(mba);
		pa.addAction(sba);
		
		SequenceAction sa = new SequenceAction();
		sa.addAction(pa);

		mba = new MoveByAction();
		mba.setAmount(pulseSize * 0.5f, pulseSize * 0.5f);
		mba.setDuration(pulseDuration);
		
		sba = new SizeByAction();
		sba.setAmount(pulseSize * -1, pulseSize * -1);
		sba.setDuration(pulseDuration);
		
		pa = new ParallelAction();
		pa.addAction(mba);
		pa.addAction(sba);
		
		sa.addAction(pa);
		
		return sa;
	}

	public void setupPulse(TileActor button) {
		button.setBaseAction(this.getBaseAction(button.getHeight() / 2, 0.02f));
	}
}
