package com.github.christiansanders.mineseeker.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Christian on 16-9-2016.
 */
public class PlayScreen implements Screen, InputProcessor {
    private BitmapFont font;
    private Batch batch;
    private Texture img;
    private Sprite sprite;
    private Boolean colorChanged;
    private OrthographicCamera cam;
    private Vector2 velocitySprite;


    @Override
    public void show() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("hi", "hi");
        System.out.println("hi");

        font = new BitmapFont();
        font.setColor(Color.GOLD);

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        sprite.setPosition(0, 0);
        sprite.setSize(80, 80);
        velocitySprite = new Vector2(0, 0);


        colorChanged = false;
        Gdx.input.setInputProcessor(this);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        // Setup the camera
        cam = new OrthographicCamera(100, 100 * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        sprite.setPosition(sprite.getX() + velocitySprite.x * delta, sprite.getY());

        if(!colorChanged) {

            Gdx.gl.glClearColor(1, 0, 0, 1);
        }else{
            Gdx.gl.glClearColor(1, 0, 1, 0);
        }

        batch.begin();
        font.draw(batch, "HI", 100, 100);
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 100f;
        cam.viewportHeight = 100f * height/width; // Lets keep things in proportion.
        cam.update();
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
        switch (keycode){
            case Input.Keys.LEFT:
                velocitySprite.set(-10, 0);
                break;
            case Input.Keys.RIGHT:
                velocitySprite.set(10, 0);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT){
            velocitySprite.set(0, 0);
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
        Gdx.app.debug("screenX", String.valueOf(screenX));
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
