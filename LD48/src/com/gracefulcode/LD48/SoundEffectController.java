package com.gracefulcode.LD48;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IntMap;

public class SoundEffectController {
	private static HashMap<Sound, Integer> sounds = new HashMap<Sound, Integer>();
	
	public static Sound getNewSound() {
		Iterator<Sound> soundIt = SoundEffectController.sounds.keySet().iterator();
		return null;
	}	
}
