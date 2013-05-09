package com.gracefulcode.LD48;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.Net.HttpRequest;
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
	
	public static int TILE_SIZE;
	
	public LD48(int tileSize) {
		LD48.TILE_SIZE = tileSize;
	}
	
	static public int fixX(int x) {
		return x;
	}
	
	static public int fixY(int y) {
		return y + (Gdx.graphics.getHeight() % LD48.TILE_SIZE) - LD48.TILE_SIZE;
	}
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		this.skin = new Skin();

		Pixmap p = new Pixmap(LD48.TILE_SIZE, LD48.TILE_SIZE, Pixmap.Format.RGB888);
		p.setColor(new Color(1, 1, 1, 0));
		p.fill();
		p.setColor(new Color(0.7f, 0.7f, 0.7f, 1.0f));
		p.drawLine(0,  0, LD48.TILE_SIZE,  0);
		p.drawLine(0, 0, 0, LD48.TILE_SIZE);
		
		Texture t = new Texture(p);
		
		NinePatch np = new NinePatch(t);
		this.skin.add("whiteImage", np);

		this.music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		this.music.setVolume(0.1f);
		this.music.setLooping(true);
//		this.music.play();
		
		p = new Pixmap(1024, 1024, Pixmap.Format.RGB888);
		p.setColor(new Color(0, 0, 0, 1));
		p.fill();
		
		t = new Texture(p);		
		np = new NinePatch(t, 0, 0, 0, 0);
		this.skin.add("blackImage", np);
		
		this.skin.add("default", new BitmapFont(Gdx.files.internal("data/Batang-32.fnt"), false));
		this.skin.add("small", new BitmapFont(Gdx.files.internal("data/Batang-15.fnt"), false));

		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = this.skin.getDrawable("whiteImage");
		this.skin.add("default", buttonStyle);
		
		Texture tex = new Texture(Gdx.files.internal("data/uiskin.png"));
		TextureRegion tr = new TextureRegion(tex, 1, 20, 28, 27);
		np = new NinePatch(tr, 5, 5, 20, 5);
		this.skin.add("windowBackground", np);
		
		tr = new TextureRegion(tex, 56, 29, 22, 20);
		np = new NinePatch(tr, 5, 5, 5, 5);
		this.skin.add("buttonBackground", np);
		
		tr = new TextureRegion(tex, 77, 29, 22, 20);
		np = new NinePatch(tr, 5, 5, 5, 5);
		this.skin.add("buttonDownBackground", np);
		
		WindowStyle windowStyle = new WindowStyle();
		windowStyle.background = this.skin.getDrawable("windowBackground");
		windowStyle.titleFont = this.skin.getFont("small");
		windowStyle.titleFontColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		this.skin.add("default", windowStyle);
		
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.font = this.skin.getFont("default");
		tbStyle.up = this.skin.getDrawable("buttonBackground");
		this.skin.add("default", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.skin.getFont("default");
		tbStyle.up = this.skin.getDrawable("buttonBackground");
		tbStyle.checked = this.skin.getDrawable("buttonDownBackground");
		this.skin.add("checkable", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.skin.getFont("default");
		tbStyle.up = this.skin.getDrawable("buttonBackground");
		tbStyle.checked = this.skin.getDrawable("buttonDownBackground");
		tbStyle.fontColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		this.skin.add("small", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.skin.getFont("default");
		tbStyle.over = this.skin.getDrawable("blackImage");
		this.skin.add("menuButton", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.up = this.skin.getDrawable("whiteImage");
		tbStyle.font = this.skin.getFont("default");
		tbStyle.fontColor = new Color(0, 0, 0, 1);
		this.skin.add("blank", tbStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = this.skin.getFont("default");
		this.skin.add("default", labelStyle);
		
		labelStyle = new LabelStyle();
		labelStyle.font = this.skin.getFont("small");
		this.skin.add("small", labelStyle);
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		batch.disableBlending();
		
		this.stage = new MainScreen(this.skin, this);
		this.stage.setCamera(camera);
		Gdx.input.setInputProcessor(this.stage);
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
				GameLevel level = ((GameLevel)this.stage);
				String json = "{diffuculty: '" + level.getDifficulty().name + "', level: " + level.getLevelNum() + ", paintbrush: '" + level.getPaintbrush().name + "', bestKnown: " + level.bestKnown + ", numClicks: " + level.numClicks + ", time: " + level.time + "}";

				HttpRequest req = new HttpRequest("GET");
				req.setContent("score=" + json);
				req.setUrl("http://gracefulcode.com/ld48/scores.php");
				
				HttpResponseListener listener = new HttpResponseListener() {
					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
					}

					@Override
					public void failed(Throwable t) {
					}					
				};
				Gdx.net.sendHttpRequest(req, listener);
				
				this.setupRecap(level);
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
		this.stage.setViewport(width,  height,  false);
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
