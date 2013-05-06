package com.gracefulcode.LD48;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TileActor extends Actor {
	public LD48 ld48;
	public int count = 0;
	public GameLevel level;
	private Skin skin;
	private int colorCount;
	private Drawable drawable;
	
	public TileActor(Skin skin, LD48 ld48, int x, int y, int colorCount, GameLevel level) {
		this.skin = skin;
		this.ld48 = ld48;
		this.x = x;
		this.y = y;
		this.level = level;
		this.colorCount = colorCount;
		this.count = 0;
		this.changeCount(0);
	}
	
	public void draw(SpriteBatch batch, float alpha) {
		this.drawable.draw(batch, this.x * LD48.TILE_SIZE, this.y * LD48.TILE_SIZE, LD48.TILE_SIZE, LD48.TILE_SIZE);
	}
	
	public void changeCount(int by) {
		this.count += by;
		
		while (this.count >= this.colorCount) {
			this.count -= this.colorCount;
		}
		
		while (this.count < 0) {
			this.count += this.colorCount;
		}
		
		this.drawable = this.skin.getDrawable("button" + this.count);
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
