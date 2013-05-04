package com.gracefulcode.LD48;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TileActor extends Actor {
	public LD48 ld48;
	public int count = 0;
	public GameLevel level;
	private Skin skin;
	private int colorCount;
	private Drawable drawable;
	private Label label;
	
	public TileActor(Skin skin, LD48 ld48, int x, int y, int colorCount, GameLevel level) {
		this.skin = skin;
		this.ld48 = ld48;
		this.x = x;
		this.y = y;
		this.level = level;
		this.colorCount = colorCount;
		this.count = 0;
		this.changeCount(0);
		this.label = new Label("", this.skin);
	}
	
	public void draw(SpriteBatch batch, float alpha) {
		this.drawable.draw(batch, this.x * 40, this.y * 40, 40, 40);
		this.label.setPosition(this.x * 40, this.y * 40);
		this.label.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		this.label.setText("" + this.x);
		this.label.draw(batch,  alpha);
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
