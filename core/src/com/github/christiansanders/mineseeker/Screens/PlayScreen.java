package com.github.christiansanders.mineseeker.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Christian on 16-9-2016.
 */
public class PlayScreen implements Screen, InputProcessor {
    BitmapFont font;
    Batch batch;
    Texture img;
    Sprite sprite;
    Boolean colorChanged;

    @Override
    public void show() {
        font = new BitmapFont();
        font.setColor(Color.GOLD);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - sprite.getWidth() / 2);
        colorChanged = false;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!colorChanged) {

            Gdx.gl.glClearColor(1, 0, 0, 1);
        }else{
            Gdx.gl.glClearColor(1, 0, 1, 0);
        }
        batch.begin();
        font.draw(batch, "HI", 100, 100);
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.SPACE){
            colorChanged = !colorChanged;
        }
        Gdx.app.debug("key", String.valueOf(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
