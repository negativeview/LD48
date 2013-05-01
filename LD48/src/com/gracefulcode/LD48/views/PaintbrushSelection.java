package com.gracefulcode.LD48.views;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.gracefulcode.LD48.GameLevel;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.difficulty.Difficulty;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;
import com.gracefulcode.LD48.paintbrushes.PlusPaintbrush;
import com.gracefulcode.LD48.paintbrushes.SpiralPaintbrush;
import com.gracefulcode.LD48.paintbrushes.StarPaintbrush;

public class PaintbrushSelection extends LD48View {
	private Array<Paintbrush> paintbrushes;
	private Skin skin;
	private LD48 ld48;
	
	public PaintbrushSelection(LD48 ld48, Skin skin, final MainMenuContainer mainMenuContainer) throws Exception {
		this.skin = skin;
		this.ld48 = ld48;
		
		if (ld48 == null) {
			throw new Exception();
		}
		
		this.setupPaintbrushArray();
		this.setupPaintbrushImages();
		
		TextButton tb = new TextButton("Back", this.skin, "menuButton");
		tb.setPosition(20, 20);
		this.addActor(tb);
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mainMenuContainer.popView();
			}
		};
		tb.addListener(cl);
	}
	
	private void setupPaintbrushImages() {
		Iterator<Paintbrush> it = this.paintbrushes.iterator();
		
		int i = 0;
		while (it.hasNext()) {
			i += 200;
			
			TextButton tb = new TextButton("", this.skin, "menuButton");
			tb.setLayoutEnabled(false);
			
			final Paintbrush p = it.next();
			Image im = p.getImage();
			im.setPosition(100,  40);
			
			Label l = new Label(p.getDescription(), this.skin);
			tb.addActor(l);
			l.setPosition(200, 10);
			l.setSize(MainMenuContainer.MENU_WIDTH - 200, 160);
			
			tb.addActor(im);
			this.addActor(tb);
			tb.setPosition(0, Gdx.graphics.getHeight() - i);
			tb.setSize(MainMenuContainer.MENU_WIDTH, 160);
			
			final Skin skin = this.skin;
			final LD48 ld48 = this.ld48;
			ChangeListener cl = new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					GameLevel gl = new GameLevel(0, skin, ld48, new Difficulty(), p);
					ld48.gotoGame(gl);
				}
			};
			tb.addListener(cl);
		}
	}
	
	private void setupPaintbrushArray() {
		this.paintbrushes = new Array<Paintbrush>();
		this.paintbrushes.add(new PlusPaintbrush());
		this.paintbrushes.add(new StarPaintbrush());
		this.paintbrushes.add(new SpiralPaintbrush());
	}
}
