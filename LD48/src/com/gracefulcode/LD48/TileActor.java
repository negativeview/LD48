package com.gracefulcode.LD48;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TileActor extends Actor {
	public LD48 ld48;
	public int count = 0;
	public GameLevel level;
	private Skin skin;
	private int colorCount;
	
	public TileActor(Skin skin, LD48 ld48, int x, int y, int colorCount, GameLevel level) {
		this.skin = skin;
		this.ld48 = ld48;
		this.x = x;
		this.y = y;
		this.level = level;
		this.colorCount = colorCount;
	}
	
	public void changeCount(int by) {
		this.count += by;
		
		while (this.count >= this.colorCount) {
			this.count -= this.colorCount;
		}
		
		while (this.count < 0) {
			this.count += this.colorCount;
		}
		
//		this.setStyle(this.skin.get("button" + this.count, TextButtonStyle.class));
	}
	
	public int x;
	public int y;
	private SequenceAction baseAction;

	public void setBaseAction(SequenceAction baseAction) {
		this.baseAction = baseAction;
	}
	
	public SequenceAction getBaseAction() {
		return this.baseAction;
	}
}
