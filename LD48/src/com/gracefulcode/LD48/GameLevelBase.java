package com.gracefulcode.LD48;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class GameLevelBase extends Stage {
	public Array<Drawable> colors;
	protected Skin skin;
	public LD48 ld48;
	public int bestKnown;
	public int numSounds = 0;
	public int numClicks = 0;
	public int tileSize;
	public float volume = 0.1f;
	public float time = 0.0f;
	
	public GameLevelBase(Skin skin, LD48 ld48) {
		this.colors = new Array<Drawable>();
		this.skin = skin;
		this.ld48 = ld48;
				
		this.addDrawable(0, new Color(1.0f, 1.0f, 1.0f, 1.0f));
		this.addDrawable(1, new Color(35.0f / 255.0f, 56.0f / 255.0f, 47.0f / 255.0f, 1.0f));
		this.addDrawable(2, new Color(83.0f / 255.0f, 140.0f / 255.0f, 81.0f / 255.0f, 1.0f));
		this.addDrawable(3, new Color(162.0f / 255.0f, 93.0f / 255.0f, 0.0f / 255.0f, 1.0f));
		this.addDrawable(4, new Color(242.0f / 255.0f, 135.0f / 255.0f, 5.0f / 255.0f, 1.0f));
		this.addDrawable(5, new Color(246.0f / 255.0f, 205.0f / 255.0f, 91.0f / 255.0f, 1.0f));
	}
	
	private int stupid = 0;
	private void addDrawable(int id, Color c) {
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = this.skin.getFont("default");
		tbs.pressedOffsetX = 0.0f;
		tbs.pressedOffsetY = 0.0f;
		tbs.unpressedOffsetX = 0.0f;
		tbs.unpressedOffsetY = 0.0f;
		
		Pixmap p = new Pixmap(40, 40, Pixmap.Format.RGB888);
		p.setColor(c);
		p.fill();

		p.setColor(new Color(0f, 0f, 0f, 1.0f));
		p.drawLine(0,  0, 40,  0);
		p.drawLine(0, 0, 0, 40);

		Texture t = new Texture(p);
		
		this.skin.add("tmp" + this.stupid, t);
		this.colors.add(this.skin.getDrawable("tmp" + this.stupid));

		tbs.up = this.skin.getDrawable("tmp" + this.stupid);
		this.skin.add("button" + id, tbs);

		this.stupid++;
	}
	
	public boolean isRealLevel() {
		return false;
	}
	
	public boolean isRecapScreen() {
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			this.ld48.pause();
		}
		return true;
	}	
	
	public boolean isDone() {
		return false;
	}
}
