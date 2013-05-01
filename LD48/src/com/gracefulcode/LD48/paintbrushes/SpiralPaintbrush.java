package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SpiralPaintbrush extends Paintbrush {
	public Image getImage() {
		FileHandle fh = Gdx.files.internal("data/spiral.png");
		Texture tex = new Texture(fh);
		Image im = new Image(tex);
		return im;
	}

	public String getDescription() {
		return "The most recent. No idea\nhow hard it is yet.";
	}
}
