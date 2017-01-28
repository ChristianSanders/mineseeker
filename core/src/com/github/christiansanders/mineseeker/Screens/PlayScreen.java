package com.github.christiansanders.mineseeker.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.christiansanders.mineseeker.GameBoard;
import com.github.christiansanders.mineseeker.MineSeeker;
import com.github.christiansanders.mineseeker.gestures.PlayScreenGestureListener;

/**
 * Created by Christian on 16-9-2016.
 */
public class PlayScreen implements Screen {
    private Batch batch;
    private OrthographicCamera cam;
    private Viewport viewport;
    private GameBoard gameBoard;
    private PlayScreenGestureListener playScreenGestureListener;


    @Override
    public void show() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();



        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        // Setup the camera
        cam = new OrthographicCamera(MineSeeker.V_WIDTH, MineSeeker.V_HEIGHT);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        viewport = new ExtendViewport(MineSeeker.V_WIDTH, MineSeeker.V_HEIGHT, cam);
        viewport.apply();



        // add gameboard
        gameBoard = new GameBoard();
        // let the board have 10 by 10 boxes
        gameBoard.create(10, 10);

        playScreenGestureListener = new PlayScreenGestureListener(cam, viewport, gameBoard);
        Gdx.input.setInputProcessor(new GestureDetector(playScreenGestureListener));
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

        Gdx.gl.glClearColor(.2f,.2f,.2f, 1);

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
}
