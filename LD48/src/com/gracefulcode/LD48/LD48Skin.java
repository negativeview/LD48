package com.gracefulcode.LD48;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

public class LD48Skin extends Skin {
	public LD48Skin() {
		Pixmap p = new Pixmap(GraphicsConfiguration.tileSize, GraphicsConfiguration.tileSize, Pixmap.Format.RGB888);
		p.setColor(new Color(1, 1, 1, 0));
		p.fill();
		p.setColor(new Color(0.7f, 0.7f, 0.7f, 1.0f));
		p.drawLine(0,  0, GraphicsConfiguration.tileSize,  0);
		p.drawLine(0, 0, 0, GraphicsConfiguration.tileSize);
		
		Texture t = new Texture(p);
		
		NinePatch np = new NinePatch(t);
		this.add("whiteImage", np);

		p = new Pixmap(GraphicsConfiguration.realWidth, GraphicsConfiguration.realHeight, Pixmap.Format.RGB888);
		p.setColor(new Color(0, 0, 0, 1));
		p.fill();
		
		t = new Texture(p);		
		np = new NinePatch(t, 0, 0, 0, 0);
		this.add("blackImage", np);
		
		this.add("default", new BitmapFont(Gdx.files.internal("data/Batang-32.fnt"), false));
		this.add("small", new BitmapFont(Gdx.files.internal("data/Batang-15.fnt"), false));

		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = this.getDrawable("whiteImage");
		this.add("default", buttonStyle);
		
		Texture tex = new Texture(Gdx.files.internal("data/uiskin.png"));
		TextureRegion tr = new TextureRegion(tex, 1, 20, 28, 27);
		np = new NinePatch(tr, 5, 5, 20, 5);
		this.add("windowBackground", np);
		
		tr = new TextureRegion(tex, 56, 29, 22, 20);
		np = new NinePatch(tr, 5, 5, 5, 5);
		this.add("buttonBackground", np);
		
		tr = new TextureRegion(tex, 77, 29, 22, 20);
		np = new NinePatch(tr, 5, 5, 5, 5);
		this.add("buttonDownBackground", np);
		
		WindowStyle windowStyle = new WindowStyle();
		windowStyle.background = this.getDrawable("windowBackground");
		windowStyle.titleFont = this.getFont("small");
		windowStyle.titleFontColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		this.add("default", windowStyle);
		
		TextButtonStyle tbStyle = new TextButtonStyle();
		tbStyle.font = this.getFont("default");
		tbStyle.up = this.getDrawable("buttonBackground");
		this.add("default", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.getFont("default");
		tbStyle.up = this.getDrawable("buttonBackground");
		tbStyle.checked = this.getDrawable("buttonDownBackground");
		this.add("checkable", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.getFont("default");
		tbStyle.up = this.getDrawable("buttonBackground");
		tbStyle.checked = this.getDrawable("buttonDownBackground");
		tbStyle.fontColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		this.add("small", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.font = this.getFont("default");
		tbStyle.over = this.getDrawable("blackImage");
		this.add("menuButton", tbStyle);

		tbStyle = new TextButtonStyle();
		tbStyle.up = this.getDrawable("whiteImage");
		tbStyle.font = this.getFont("default");
		tbStyle.fontColor = new Color(0, 0, 0, 1);
		this.add("blank", tbStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = this.getFont("default");
		this.add("default", labelStyle);
		
		labelStyle = new LabelStyle();
		labelStyle.font = this.getFont("small");
		this.add("small", labelStyle);
	}
}
