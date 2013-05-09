package com.gracefulcode.LD48.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.gracefulcode.LD48.ColorScheme;
import com.gracefulcode.LD48.GameLevelBase;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.views.MainMenuContainer;

public class MainScreen extends GameLevelBase {
	private Skin skin;
	private boolean done = false;
	private Image backgroundBackground;
	private ColorScheme colorScheme;
	private WidgetGroup tileHolder;
	
	public MainScreen(Skin skin, LD48 ld48) {
		super(skin, ld48);
		this.colorScheme = new ColorScheme();
		
		this.skin = skin;
		
		Pixmap p = new Pixmap((int)this.getWidth(), (int)this.getHeight(), Pixmap.Format.RGB888);
		p.setColor(new Color(1, 1, 1, 1));
		p.fill();
		
		p.setColor(new Color(0.0f, 0.0f, 0.0f, 1));
		
		for (int i = 0; i < this.getHeight(); i += LD48.TILE_SIZE) {
			p.drawLine(0,  i, (int)this.getWidth(), i);			
		}
		for (int i = 0; i < this.getWidth(); i += LD48.TILE_SIZE) {
			p.drawLine(i, 0, i, (int)this.getHeight());
		}
		
		Texture tex = new Texture(p);
		this.backgroundBackground = new Image(tex);

		this.addActor(this.backgroundBackground);
		this.tileHolder = new WidgetGroup();
		this.addActor(this.tileHolder);
		this.tileHolder.setSize(this.getWidth(), this.getHeight());
		
		MainMenuContainer mmc = new MainMenuContainer(this.skin, this.ld48);
		mmc.setPosition((this.getWidth() / 2) - (MainMenuContainer.MENU_WIDTH / 2), 0);
		this.addActor(mmc);
	}
	
	@Override
	public boolean isRealLevel() {
		return false;
	}

	@Override
	public boolean isDone() {
		return this.done;
	}
	
	private void spawnColors() {
		Random rand = new Random();
		for (int i = 0; i < this.colorScheme.getNumColors(); i++) {
			for (int m = 0; m < 10; m++) {
				Pixmap p = new Pixmap(LD48.TILE_SIZE - 1, LD48.TILE_SIZE - 1, Pixmap.Format.RGB888);
				
				Color c = this.colorScheme.getColor(i);
				int x = rand.nextInt(1280 / LD48.TILE_SIZE);
				int y = rand.nextInt(720 / LD48.TILE_SIZE);
				
				p.setColor(c);
				p.fill();
				
				Texture t = new Texture(p);
				Image im = new Image(t);
				im.setZIndex(10);
				
				im.setPosition((x * LD48.TILE_SIZE) + 1,  (y * LD48.TILE_SIZE));
				im.setColor(0, 0, 0, 0);
				this.tileHolder.addActor(im);
				
				SequenceAction sequence = new SequenceAction();
				AlphaAction aa = new AlphaAction();
				aa.setAlpha(1.0f);
				aa.setDuration(10.5f);
				sequence.addAction(aa);

				aa = new AlphaAction();
				aa.setAlpha(0.0f);
				aa.setDuration(10.5f);
				sequence.addAction(aa);
				
				RemoveAction ra = new RemoveAction();
				sequence.addAction(ra);
				
				im.addAction(sequence);
			}			
		}		
	}
	
	private int tick = 0;
	
	@Override
	public void draw() {
		this.tick++;
		
		if (this.tick % 100 == 0) {
			this.spawnColors();
		}
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		super.draw();
	}
}
