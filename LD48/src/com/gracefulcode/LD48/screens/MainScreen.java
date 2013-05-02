package com.gracefulcode.LD48.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gracefulcode.LD48.ColorScheme;
import com.gracefulcode.LD48.GameLevelBase;
import com.gracefulcode.LD48.LD48;
import com.gracefulcode.LD48.difficulty.Difficulty;
import com.gracefulcode.LD48.difficulty.EasyDifficulty;
import com.gracefulcode.LD48.difficulty.HarderDifficulty;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;
import com.gracefulcode.LD48.paintbrushes.PlusPaintbrush;
import com.gracefulcode.LD48.paintbrushes.StarPaintbrush;
import com.gracefulcode.LD48.views.MainMenuContainer;

public class MainScreen extends GameLevelBase {
	private Button button;
	private Skin skin;
	private boolean done = false;
	private Image backgroundBackground;
	private ColorScheme colorScheme;
	private int tileSize = 40;
	private WidgetGroup tileHolder;
	
	public MainScreen(Skin skin, LD48 ld48) {
		super(skin, ld48);
		this.colorScheme = new ColorScheme();
		
		this.skin = skin;
		
		Pixmap p = new Pixmap(1280, 720, Pixmap.Format.RGB888);
		p.setColor(new Color(1, 1, 1, 1));
		p.fill();
		
		p.setColor(new Color(0.0f, 0.0f, 0.0f, 1));
		
		for (int i = 0; i < 720; i += this.tileSize) {
			p.drawLine(0,  i, 1280, i);			
		}
		for (int i = 0; i < 1280; i += this.tileSize) {
			p.drawLine(i, 0, i, 720);
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
	
	public void setupNormal() {
		final MainScreen tmp = this;
		
		Texture tex1 = new Texture(Gdx.files.internal("data/plus.png"));
		Image image1 = new Image(tex1);
		image1.setPosition(100,  100);
		this.addActor(image1);
		
		this.button = new TextButton("YouTube Commentator", this.skin);
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new Difficulty();
				tmp.ld48.paintbrush = new PlusPaintbrush();
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(10, 10);
		this.addActor(button);

		this.button = new TextButton("Normal", this.skin);
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new EasyDifficulty();
				tmp.ld48.paintbrush = new PlusPaintbrush();
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(170, 10);
		this.addActor(button);

		this.button = new TextButton("Harder", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new HarderDifficulty();
				tmp.ld48.paintbrush = new PlusPaintbrush();
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(228, 10);
		this.addActor(button);
	}

	public void setupFlower() {
		final MainScreen tmp = this;
		
		Texture tex1 = new Texture(Gdx.files.internal("data/flower.png"));
		Image image1 = new Image(tex1);
		image1.setPosition(500,  100);
		this.addActor(image1);
		
		this.button = new TextButton("YouTube Commentator", this.skin);		
		ChangeListener cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new Difficulty();
				tmp.ld48.paintbrush = new StarPaintbrush();
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(410, 10);
		this.addActor(button);

		this.button = new TextButton("Normal", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new EasyDifficulty();
				tmp.ld48.paintbrush = new StarPaintbrush();
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(570, 10);
		this.addActor(button);

		this.button = new TextButton("Harder", this.skin);		
		cl = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tmp.ld48.difficulty = new HarderDifficulty();
				tmp.ld48.paintbrush = new StarPaintbrush();
				tmp.done = true;
			}
		};
		this.button.addListener(cl);
		this.button.setPosition(628, 10);
		this.addActor(button);
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
				Pixmap p = new Pixmap(this.tileSize - 1, this.tileSize - 1, Pixmap.Format.RGB888);
				
				Color c = this.colorScheme.getColor(i);
				int x = rand.nextInt(1280 / this.tileSize);
				int y = rand.nextInt(720 / this.tileSize);
				
				p.setColor(c);
				p.fill();
				
				Texture t = new Texture(p);
				Image im = new Image(t);
				im.setZIndex(10);
				
				im.setPosition((x * this.tileSize) + 1,  (y * this.tileSize));
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
