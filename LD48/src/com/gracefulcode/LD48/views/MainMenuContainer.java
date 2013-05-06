package com.gracefulcode.LD48.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.gracefulcode.LD48.LD48;

public class MainMenuContainer extends LD48View {
	private Image backgroundImage;
	private Skin skin;
	private Array<LD48View> viewStack;
	private LD48 ld48;
	
	public static int MENU_WIDTH = 600;

	public MainMenuContainer(Skin skin, LD48 ld48) {
		this.skin = skin;
		this.ld48 = ld48;
		this.viewStack = new Array<LD48View>();
		Texture tex = new Texture(Gdx.files.internal("data/menu-back.png"));
		NinePatch np = new NinePatch(tex, 5, 5, 0, 0);
		
		this.backgroundImage = new Image(np);
		this.addActor(this.backgroundImage);		
		this.backgroundImage.setSize(MENU_WIDTH, Gdx.graphics.getHeight());
	
		try {
			MainMenu mm = new MainMenu(this.ld48, this.skin, this);
			mm.setPosition((this.getWidth() / 2) - (mm.getWidth() / 2), 0);
			this.addLD48View(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addLD48View(LD48View view) {
		if (this.viewStack.size > 0) {
			SequenceAction sa = new SequenceAction();
			MoveToAction mta = new MoveToAction();
			mta.setPosition(0, -Gdx.graphics.getHeight());
			mta.setDuration(0.25f);
			sa.addAction(mta);
			
			RemoveAction ra = new RemoveAction();
			sa.addAction(ra);
			
			this.viewStack.peek().addAction(sa);
			
			mta = new MoveToAction();
			mta.setPosition(0, 0);
			mta.setDuration(0.25f);
			view.addAction(mta);

			view.setPosition(0, Gdx.graphics.getHeight());
			this.viewStack.add(view);
			this.addActor(view);			
		} else {
			this.viewStack.add(view);
			this.addActor(view);			
		}
	}
	
	public void popView() {
		LD48View v = this.viewStack.pop();

		SequenceAction sa = new SequenceAction();
		MoveToAction mta = new MoveToAction();
		mta.setPosition(0, Gdx.graphics.getHeight());
		mta.setDuration(0.25f);
		sa.addAction(mta);
		
		RemoveAction ra = new RemoveAction();
		sa.addAction(ra);
		v.addAction(sa);
		
		v = this.viewStack.peek();
		v.setPosition(0, -Gdx.graphics.getHeight());
		this.addActor(v);
		
		mta = new MoveToAction();
		mta.setPosition(0, 0);
		mta.setDuration(0.25f);
		v.addAction(mta);
	}
}
