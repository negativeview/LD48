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

public class LD48 implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public GameLevel stage;
	private Skin skin;
	private int level = 0;
	public boolean isPaused = false;
	private PauseScreen pauseScreen;
	public Difficulty difficulty;
	public Paintbrush paintbrush;
	public Music music;
	public boolean colorBlindMode = false;
	
	public static int TILE_IMAGE_SIZE = 16;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		this.skin = new Skin();

		Pixmap p = new Pixmap(TILE_IMAGE_SIZE, TILE_IMAGE_SIZE, Pixmap.Format.RGB888);
		p.setColor(new Color(1, 1, 1, 0));
		p.fill();
		p.setColor(new Color(0.2f, 0.2f, 0.2f, 1.0f));
		p.drawLine(0,  0, TILE_IMAGE_SIZE,  0);
		p.drawLine(0, 0, 0, TILE_IMAGE_SIZE);
		
		this.music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		this.music.setVolume(0.1f);
		this.music.setLooping(true);
		this.music.play();
		
		Texture t = new Texture(p);
		
		NinePatch np = new NinePatch(t);
		this.skin.add("whiteImage", np);

		p = new Pixmap(1024, 1024, Pixmap.Format.RGB888);
		p.setColor(new Color(0, 0, 0, 1));
		p.fill();
		
		t = new Texture(p);		
		np = new NinePatch(t, 0, 0, 0, 0);
		this.skin.add("blackImage", np);
		
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
		this.skin.add("default", new BitmapFont());
		windowStyle.titleFont = this.skin.getFont("default");
		this.skin.add("default", windowStyle);
		
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.font = this.skin.getFont("default");
		tbStyle.up = this.skin.getDrawable("buttonBackground");
		//tbStyle.checked = this.skin.getDrawable("buttonDownBackground");
		this.skin.add("default", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.skin.getFont("default");
		tbStyle.up = this.skin.getDrawable("buttonBackground");
		tbStyle.checked = this.skin.getDrawable("buttonDownBackground");
		this.skin.add("checkable", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.up = this.skin.getDrawable("whiteImage");
		tbStyle.font = this.skin.getFont("default");
		this.skin.add("blank", tbStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = this.skin.getFont("default");
		this.skin.add("default", labelStyle);
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		
		this.stage = new TutorialScreen(this.skin, this);
		this.stage.setCamera(camera);
		Gdx.input.setInputProcessor(this.stage);
		
		this.pauseScreen = new PauseScreen(this.skin, this);
	}
	
	public void setupLevel() {
		this.stage = new GameLevel(this.level++, this.skin, this, this.difficulty, this.paintbrush);
		this.stage.initialize();
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
		if (isPaused) {
			Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
		} else {
			Gdx.gl.glClearColor(1, 1, 1, 1);
		}

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (this.stage.isDone()) {
			if (this.stage.isRealLevel()) {
				String json = "{diffuculty: '" + this.stage.difficulty.name + "', level: " + this.stage.levelNum + ", paintbrush: '" + this.stage.paintbrush.name + "', bestKnown: " + this.stage.bestKnown + ", numClicks: " + this.stage.numClicks + ", time: " + this.stage.time + "}";

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
				
				this.setupRecap(this.stage);
			} else {
				this.setupLevel();
			}
		}
		
		stage.act();
		
		batch.begin();
		if (isPaused) {
			Gdx.input.setInputProcessor(this.pauseScreen);
			this.pauseScreen.draw();
		} else {
			Gdx.input.setInputProcessor(stage);
			stage.draw();			
		}
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		this.stage.setViewport(width,  height,  false);
	}

	@Override
	public void pause() {
		Gdx.input.setInputProcessor(this.pauseScreen);
		this.isPaused = true;
	}
	
	public void unpause() {
		this.isPaused = false;
		Gdx.input.setInputProcessor(this.stage);		
	}

	@Override
	public void resume() {
	}

	public TileActor getTile(int x, int y) {
		return this.stage.getTile(x, y);
	}
	
	public TileActor getTile(float x, float y) {
		return this.getTile((int)x, (int)y);
	}
}
