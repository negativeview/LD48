package com.gracefulcode.LD48;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gracefulcode.LD48.difficulty.Difficulty;
import com.gracefulcode.LD48.paintbrushes.Paintbrush;
import com.gracefulcode.LD48.screens.RecapScreen;
import com.gracefulcode.LD48.screens.MainScreen;

public class LD48 implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public GameLevelBase stage;
	private Skin skin;
	public boolean isPaused = false;
	public Difficulty difficulty;
	public Paintbrush paintbrush;
	public Music music;
	public boolean colorBlindMode = false;

	public static Reporter REPORTER;
	
	public LD48(int tileSize, String platform) {
		GraphicsConfiguration.tileSize = tileSize;
		LD48.REPORTER = new Reporter(platform);
	}
	
	static public int fixX(int x) {
		return x;
	}
	
	static public int fixY(int y) {
		return y - (GraphicsConfiguration.tileSize - (GraphicsConfiguration.effectiveHeight % GraphicsConfiguration.tileSize));
	}
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		GraphicsConfiguration.realHeight = Gdx.graphics.getHeight();
		GraphicsConfiguration.realWidth = Gdx.graphics.getWidth();

		LD48.REPORTER.sendInfo("start", null, "\"width\": \"" + Gdx.graphics.getWidth() + "\",\"height\": \"" + Gdx.graphics.getHeight() + "\"");
		
		this.skin = new Skin();

		this.music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		this.music.setVolume(0.1f);
		this.music.setLooping(true);
//		this.music.play();
		
		this.skin = new LD48Skin();
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		batch.disableBlending();
		
		this.stage = new MainScreen(this.skin, this);

		this.stage.setCamera(camera);
		Gdx.input.setInputProcessor(this.stage);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
	}
	
	public void gotoGame(GameLevelBase gl) {
		this.stage = gl;
		gl.initialize();
		stage.setCamera(camera);
		Gdx.input.setInputProcessor(stage);		
	}
	
	public void setupRecap(GameLevel oldLevel) {
		this.stage = new RecapScreen(this.skin, this, oldLevel);
		stage.setCamera(this.camera);
		Gdx.input.setInputProcessor(this.stage);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (this.stage.isDone()) {
			if (this.stage.isRealLevel()) {
				LD48.REPORTER.sendInfo("complete", (GameLevel)this.stage, null);
				this.setupRecap((GameLevel)this.stage);
			} else if (this.stage.isRecapScreen()) {
				RecapScreen level = (RecapScreen)(this.stage);
				GameLevel tmp = new GameLevel(level.getRealLevel().getLevelNum() + 1, this.skin, this, level.getRealLevel().getDifficulty(), level.getRealLevel().getPaintbrush());
				this.gotoGame(tmp);
			}
		}
		
		stage.act();
		
		batch.begin();
		stage.draw();
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		GraphicsConfiguration.effectiveHeight = height;
		GraphicsConfiguration.effectiveWidth = width;
				
		this.stage.setViewport(width,  height,  false);
		if (this.stage != null) {
			this.stage.resize(width, height);
		}
	}

	@Override
	public void pause() {
		this.isPaused = true;
	}
	
	public void unpause() {
		this.isPaused = false;
	}

	@Override
	public void resume() {
	}
}
