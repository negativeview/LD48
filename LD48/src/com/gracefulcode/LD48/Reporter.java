package com.gracefulcode.LD48;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class Reporter implements HttpResponseListener {
	private String platform;
	
	public Reporter(String platform) {
		this.platform = platform;
	}
	
	public void sendInfo(String eventName, GameLevel level, String extraInfo) {
		String json = "{";
		json += "\"event\": \"" + eventName + "\"";
		json += ",\"platform\": \"" + platform + "\"";
		json += ",\"version\": \"1\"";
		
		if (level != null) {
			json += ",\"level\": \"" + level.getLevelNum() + "\"";
			json += ",\"paintbrush\": \"" + level.getPaintbrush().name + "\"";
			json += ",\"bestKnown\": \"" + level.bestKnown + "\"";
			json += ",\"numClicks\": \"" + level.numClicks + "\"";
			json += ",\"time\": \"" + level.time + "\"";
		}
		
		if (extraInfo != null) {
			json += "," + extraInfo;
		}
		
		json += "}";
		
		Gdx.app.log("JSON", json);

		HttpRequest req = new HttpRequest("GET");
		req.setContent("score=" + json);
		req.setUrl("http://gracefulcode.com/ld48/scores.php");
		
		Gdx.net.sendHttpRequest(req, this);
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		Gdx.app.log("RESPONSE", "Here: " + httpResponse.getResultAsString());
	}

	@Override
	public void failed(Throwable t) {
	}
}
