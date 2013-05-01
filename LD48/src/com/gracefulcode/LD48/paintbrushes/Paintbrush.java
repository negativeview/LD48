package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.scenes.scene2d.Action;
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
}
