package com.gracefulcode.LD48.difficulty;

public class EasyDifficulty extends Difficulty {
	public EasyDifficulty() {
		this.name = "normal";
	}
	
	@Override
	public int numRandoms(int levelNum) {
		return 1 + levelNum;
	}
}
