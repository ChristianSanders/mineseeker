package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.christiansanders.mineseeker.screens.PlayScreen;

public class MineSeeker extends Game {
	SpriteBatch batch;
	Texture img;
	public static final int V_WIDTH = 100;
    public static final int V_HEIGHT = 100;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        setScreen(new PlayScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
