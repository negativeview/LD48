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
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;
import com.gracefulcode.LD48.paintbrushes.PlusPaintbrush;
import com.gracefulcode.LD48.paintbrushes.SpiralPaintbrush;
import com.gracefulcode.LD48.paintbrushes.StarPaintbrush;

public class PaintbrushSelection extends LD48View {
	private Array<Paintbrush> paintbrushes;
	private Skin skin;
	private LD48 ld48;
	private MainMenuContainer mainMenuContainer;
	
	public PaintbrushSelection(LD48 ld48, Skin skin, final MainMenuContainer mainMenuContainer) throws Exception {
		this.skin = skin;
		this.ld48 = ld48;
		this.mainMenuContainer = mainMenuContainer;
		
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
			
			PaintbrushCallbackHack pch = new PaintbrushCallbackHack(this.ld48, p, this.skin, this.mainMenuContainer);
			tb.addListener(pch);
		}
	}
	
	private void setupPaintbrushArray() {
		this.paintbrushes = new Array<Paintbrush>();
		this.paintbrushes.add(new PlusPaintbrush());
		this.paintbrushes.add(new StarPaintbrush());
		this.paintbrushes.add(new SpiralPaintbrush());
	}

	private class PaintbrushCallbackHack extends ChangeListener {
		private LD48 ld48;
		private Paintbrush paintbrush;
		private MainMenuContainer mainMenuContainer;
		private Skin skin;
		
		public PaintbrushCallbackHack(LD48 ld48, Paintbrush p, Skin skin, MainMenuContainer mainMenuContainer) {
			this.ld48 = ld48;
			this.paintbrush = p;
			this.skin = skin;
			this.mainMenuContainer = mainMenuContainer;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			try {
				this.mainMenuContainer.addLD48View(new LevelSelection(ld48, skin, mainMenuContainer, this.paintbrush));
			} catch (Exception e) {
				e.printStackTrace();
			}
//			GameLevel gl = new GameLevel(0, this.skin, this.ld48, new Difficulty(), this.paintbrush);
//			ld48.gotoGame(gl);
		}
	}
}

