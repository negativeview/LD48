package com.gracefulcode.LD48.client;

import com.gracefulcode.LD48.LD48;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1280, 720);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new LD48();
	}
}