package com.gracefulcode.LD48.paintbrushes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StarPaintbrush extends Paintbrush {
	public Image getImage() {
		FileHandle fh = Gdx.files.internal("data/flower.png");
		Texture tex = new Texture(fh);
		Image im = new Image(tex);
		return im;
	}

	public String getDescription() {
		return "This pattern is more\ncomplex and fun once\nyou master the basics.";
	}
}
