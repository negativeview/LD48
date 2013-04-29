package com.gracefulcode.LD48;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

public class TileActor extends TextButton {
	public LD48 ld48;
	public int count = 0;
	private Array<Color> colors;
	GameLevel level;
	
	public TileActor(Skin skin, LD48 ld48, int x, int y, Array<Color> colors, GameLevel level) {
		super("", skin, "blank");
		this.colors = colors;
		this.ld48 = ld48;
		this.x = x;
		this.y = y;
		this.level = level;
	}
	
	public void changeCount(int by) {
		this.count += by;
		
		if (this.ld48.colorBlindMode) {
			if (this.count == 0)
				this.getLabel().setText("");
			else
				this.getLabel().setText("" + this.count);
		}
		
		while (this.count >= this.colors.size) {
			this.count -= this.colors.size;
		}
		
		while (this.count < 0) {
			this.count += this.colors.size;
		}
		
		this.setColor(this.colors.get(this.count));
	}
	
	
	public int x;
	public int y;
	
	private Array<Color> possibleColors;

	public void setPossibleColors(Array<Color> colors) {
		this.possibleColors = colors;
	}	
}
