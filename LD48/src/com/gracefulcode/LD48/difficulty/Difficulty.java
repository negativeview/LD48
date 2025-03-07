package com.gracefulcode.LD48.difficulty;

import com.gracefulcode.LD48.GraphicsConfiguration;

public class Difficulty {
	public String name;
	
	public Difficulty() {
		this.name = "ytc";
	}
	
	public int numRandoms(int levelNum) {
		return 1 + (int)Math.floor(Math.sqrt(levelNum));
	}

	public int tileSize() {
		return GraphicsConfiguration.tileSize;
	}
}
