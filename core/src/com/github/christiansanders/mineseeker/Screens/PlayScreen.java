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
import com.badlogic.gdx.input.GestureDetector;
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
public class PlayScreen implements Screen, GestureDetector.GestureListener {
    private Batch batch;
    private OrthographicCamera cam;
    private Viewport viewport;
    private GameBoard gameBoard;


    @Override
    public void show() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new GestureDetector(this));

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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 touchLoc = viewport.unproject(new Vector3(x, y, 0));
        gameBoard.revealBox(touchLoc.x, touchLoc.y);
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        Vector3 touchLoc = new Vector3(x, y, 0);
        cam.unproject(touchLoc);
        gameBoard.flagBox(touchLoc.x, touchLoc.y);
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        cam.translate(-deltaX / 3, deltaY / 3);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
//        Vector3 iP1 = new Vector3(initialPointer1, 0);
//        Vector3 iP2 = new Vector3(initialPointer2, 0);
//        Vector3 p1= new Vector3(pointer1, 0);
//        Vector3 p2 = new Vector3(pointer2, 0);
//
//        cam.unproject(iP1);
//        cam.unproject(iP2);
//        cam.unproject(p1);
//        cam.unproject(p2);



        return false;
    }

    @Override
    public void pinchStop() {

    }
}
