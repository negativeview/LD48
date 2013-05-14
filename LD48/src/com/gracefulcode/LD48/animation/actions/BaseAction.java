package com.gracefulcode.LD48.animation.actions;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeByAction;
import com.gracefulcode.LD48.GameLevel;

public class BaseAction extends SequenceAction {
	protected int pulseSize;
	protected float pulseDuration;
	protected int difference;
	protected GameLevel level;
	
	public BaseAction(int pulseSize, float pulseDuration, int difference, GameLevel level) {
		this.pulseSize = pulseSize;
		this.pulseDuration = pulseDuration;
		this.difference = difference;
		this.level = level;
		
		if (level.isPaused())
			this.pulseDuration = 0.0f;
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(pulseSize * -0.5f, pulseSize * -0.5f);
		mba.setDuration(pulseDuration);
		
		SizeByAction sba = new SizeByAction();
		sba.setAmount(pulseSize, pulseSize);
		sba.setDuration(pulseDuration);
		
		ParallelAction pa = new ParallelAction();
		pa.addAction(mba);
		pa.addAction(sba);
		
		this.addAction(pa);

		mba = new MoveByAction();
		mba.setAmount(pulseSize * 0.5f, pulseSize * 0.5f);
		mba.setDuration(pulseDuration);
		
		sba = new SizeByAction();
		sba.setAmount(pulseSize * -1, pulseSize * -1);
		sba.setDuration(pulseDuration);
		
		pa = new ParallelAction();
		pa.addAction(mba);
		pa.addAction(sba);
		
		this.addAction(pa);
	}
}
