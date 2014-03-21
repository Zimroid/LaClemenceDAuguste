package com.conseil7.auguste;

import auguste.client.graphical.GraphicalMain;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = GraphicalMain.APP_NAME;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new GraphicalMain(), cfg);
	}
}