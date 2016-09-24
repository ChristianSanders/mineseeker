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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.christiansanders.mineseeker.GameBoard;
import com.github.christiansanders.mineseeker.MineSeeker;

/**
 * Created by Christian on 16-9-2016.
 */
public class PlayScreen implements Screen, InputProcessor {
    private Batch batch;
    private Boolean colorChanged;
    private OrthographicCamera cam;
    private Viewport viewport;
    private Vector2 velocitySprite;
    private GameBoard gameBoard;


    @Override
    public void show() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();
        velocitySprite = new Vector2(0, 0);

        colorChanged = false;
        Gdx.input.setInputProcessor(this);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        // Setup the camera
        cam = new OrthographicCamera(MineSeeker.V_WIDTH, MineSeeker.V_HEIGHT);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        viewport = new FitViewport(MineSeeker.V_WIDTH, MineSeeker.V_HEIGHT, cam);
        viewport.apply();

        // add gameboard
        gameBoard = new GameBoard();
        // let the board have 10 by 10 boxes
        gameBoard.create(10, 10);
    }


    // the game logic
    // not needed yet since we are using an inputprocessors and thus only event listeners
    private void update(float delta){

    }

    @Override
    public void render(float delta) {
        // separating the game logic from actual rendering the game
        update(delta);

        // clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(1,1,1, 1);

        // start drawing everything on the screen
        batch.begin();
        gameBoard.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        switch(keycode){
            case Input.Keys.LEFT:
                if(velocitySprite.x == -10){
                    velocitySprite.x = 0;
                }
                break;
            case Input.Keys.RIGHT:
                if(velocitySprite.x == 10){
                    velocitySprite.x = 0;
                }

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
        Vector3 touchLoc = viewport.unproject(new Vector3(screenX, screenY, 0));
        gameBoard.revealBox(touchLoc.x, touchLoc.y);
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
