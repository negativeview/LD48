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
import com.gracefulcode.LD48.GraphicsConfiguration;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.views.MainMenuContainer;

public class MainScreen extends GameLevelBase {
	private Skin skin;
	private boolean done = false;
	private Image backgroundBackground;
	private ColorScheme colorScheme;
	private WidgetGroup tileHolder;
	private MainMenuContainer mmc;
	
	public MainScreen(Skin skin, LD48 ld48) {
		super(skin, ld48);
		this.colorScheme = new ColorScheme();
		
		this.skin = skin;
		
		this.tileHolder = new WidgetGroup();
		this.addActor(this.tileHolder);
		this.tileHolder.setSize(GraphicsConfiguration.effectiveWidth, GraphicsConfiguration.effectiveHeight);
	}
	
	@Override
	public void resize(int width, int height) {
		if (this.backgroundBackground != null) {
			this.backgroundBackground.remove();
		}
		Pixmap p = new Pixmap(GraphicsConfiguration.effectiveWidth, GraphicsConfiguration.effectiveHeight, Pixmap.Format.RGB888);
		p.setColor(new Color(1, 1, 1, 1));
		p.fill();
		
		p.setColor(new Color(0.0f, 0.0f, 0.0f, 1));
		
		for (int i = 0; i < GraphicsConfiguration.effectiveHeight; i += GraphicsConfiguration.tileSize) {
			p.drawLine(0,  i, GraphicsConfiguration.effectiveWidth, i);			
		}
		for (int i = 0; i < GraphicsConfiguration.effectiveWidth; i += GraphicsConfiguration.tileSize) {
			p.drawLine(i, 0, i, GraphicsConfiguration.effectiveHeight);
		}
		
		Texture tex = new Texture(p);
		this.backgroundBackground = new Image(tex);
		this.addActor(this.backgroundBackground);
		this.backgroundBackground.toBack();
		
		if (this.mmc != null)
			this.mmc.remove();

		this.mmc = new MainMenuContainer(this.skin, this.ld48);
		this.addActor(this.mmc);
		this.mmc.setPosition((GraphicsConfiguration.effectiveWidth / 2) - (MainMenuContainer.MENU_WIDTH / 2), 0);	
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
				Pixmap p = new Pixmap(GraphicsConfiguration.tileSize - 1, GraphicsConfiguration.tileSize - 1, Pixmap.Format.RGB888);
				
				Color c = this.colorScheme.getColor(i);
				int x = rand.nextInt((GraphicsConfiguration.effectiveWidth / GraphicsConfiguration.tileSize) + 1);
				int y = rand.nextInt((GraphicsConfiguration.effectiveHeight / GraphicsConfiguration.tileSize) + 1);
				
				p.setColor(c);
				p.fill();
				
				Texture t = new Texture(p);
				Image im = new Image(t);
				im.setZIndex(10);
				
				im.setPosition(
					LD48.fixX((x * GraphicsConfiguration.tileSize) + 1),
					LD48.fixY(y * GraphicsConfiguration.tileSize)
				);
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
