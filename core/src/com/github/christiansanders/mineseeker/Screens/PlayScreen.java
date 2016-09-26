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

        cam.position.add(
                cam.unproject(new Vector3(0, 0, 0))
                        .add(cam.unproject(new Vector3(deltaX, deltaY, 0)).scl(-1f))
        );
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    private Vector2 oldInitialFirstPointer=null, oldInitialSecondPointer=null;
    private float oldScale;

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
        if(!(initialFirstPointer.equals(oldInitialFirstPointer)&&initialSecondPointer.equals(oldInitialSecondPointer))){
            oldInitialFirstPointer = initialFirstPointer.cpy();
            oldInitialSecondPointer = initialSecondPointer.cpy();
            oldScale = cam.zoom;
        }
        Vector3 center = new Vector3(
                (firstPointer.x+initialSecondPointer.x)/2,
                (firstPointer.y+initialSecondPointer.y)/2,
                0
        );
        zoomCamera(center, oldScale*initialFirstPointer.dst(initialSecondPointer)/firstPointer.dst(secondPointer));
        return true;
    }
    private void zoomCamera(Vector3 origin, float scale){
        cam.update();
        Vector3 oldUnprojection = cam.unproject(origin.cpy()).cpy();
        cam.zoom = scale; //Larger value of zoom = small images, border view
        cam.zoom = Math.min(2.0f, Math.max(cam.zoom, 0.5f));
        cam.update();
        Vector3 newUnprojection = cam.unproject(origin.cpy()).cpy();
        cam.position.add(oldUnprojection.cpy().add(newUnprojection.cpy().scl(-1f)));
    }

    @Override
    public void pinchStop() {

    }
}
