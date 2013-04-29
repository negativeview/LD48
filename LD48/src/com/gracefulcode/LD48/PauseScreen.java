package com.gracefulcode.LD48;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

public class PauseScreen extends Stage {
	private Skin skin;
	private Image backgroundImage;
	private Array<Color> colors;

	public PauseScreen(Skin skin, final LD48 ld48) {
		this.skin = skin;
		this.backgroundImage = new Image(this.skin.getDrawable("blackImage"));
		this.backgroundImage.setPosition(300, 0);
		this.backgroundImage.setSize(this.getWidth() - 600, this.getHeight());
		this.addActor(this.backgroundImage);
		
		TextButton tb = new TextButton("Unpause", this.skin);
		tb.setPosition(310, 10);
		this.addActor(tb);
		
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ld48.unpause();
			}
		};
		tb.addListener(cl);

		tb = new TextButton("Reset Level", this.skin);
		tb.setPosition(450, 10);
		this.addActor(tb);
		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ld48.stage.reset();
				ld48.isPaused = false;
			}
		};
		tb.addListener(cl);
		
		tb = new TextButton("Kill The Music", this.skin);
		tb.setPosition(650, 10);
		this.addActor(tb);
		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ld48.music.stop();
			}
		};
		tb.addListener(cl);
		
		Label l = new Label("Color Progression:", this.skin);
		l.setPosition(310, this.getHeight() - 40);
		this.addActor(l);

		this.colors = ld48.stage.colors;
		int i = 0;
		Iterator<Color> colorIt = this.colors.iterator();
		while (colorIt.hasNext()) {
			Color color = colorIt.next();
			Button b = new Button(this.skin);
			b.setColor(color);
			
			this.addActor(b);
			b.setPosition(435 + (20 * i++), this.getHeight() - 35);
		}
	}
}
