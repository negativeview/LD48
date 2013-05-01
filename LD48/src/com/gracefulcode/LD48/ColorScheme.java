package com.gracefulcode.LD48;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class ColorScheme {
	private Array<Color> colors;
	
	public ColorScheme() {
		this.colors = new Array<Color>();
		this.colors.add(new Color(35.0f / 255.0f, 56.0f / 255.0f, 47.0f / 255.0f, 1.0f));
		this.colors.add(new Color(83.0f / 255.0f, 140.0f / 255.0f, 81.0f / 255.0f, 1.0f));
		this.colors.add(new Color(162.0f / 255.0f, 93.0f / 255.0f, 0.0f / 255.0f, 1.0f));
		this.colors.add(new Color(242.0f / 255.0f, 135.0f / 255.0f, 5.0f / 255.0f, 1.0f));
		this.colors.add(new Color(246.0f / 255.0f, 205.0f / 255.0f, 91.0f / 255.0f, 1.0f));
	}
	
	public int getNumColors() {
		return this.colors.size;
	}
	
	public Color getColor(int index) {
		return this.colors.get(index);
	}
}
