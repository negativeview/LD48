package com.gracefulcode.LD48;

public class HarderDifficulty extends Difficulty {
	public HarderDifficulty() {
		this.name = "harder";
	}
	
	@Override
	public int numRandoms(int levelNum) {
		return 1 + (levelNum * 2);
	}
}
