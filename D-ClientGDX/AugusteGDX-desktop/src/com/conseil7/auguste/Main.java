package com.conseil7.auguste;

import auguste.client.graphical.MainGr;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = MainGr.APP_NAME;
		cfg.width = 1500;
		cfg.height = 800;
		
		new LwjglApplication(new MainGr(), cfg);
	}
}