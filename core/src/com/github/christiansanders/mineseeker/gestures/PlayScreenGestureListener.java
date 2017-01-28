package com.github.christiansanders.mineseeker.gestures;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.christiansanders.mineseeker.GameBoard;

/**
 * Created by Christian on 28-1-2017.
 */
public class PlayScreenGestureListener implements GestureDetector.GestureListener {
    private Viewport viewport;
    private OrthographicCamera cam;
    private GameBoard gameBoard;


    public PlayScreenGestureListener(OrthographicCamera cam, Viewport viewport, GameBoard gameBoard){
        this.cam = cam;
        this.viewport = viewport;
        this.gameBoard = gameBoard;
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
    public boolean fling(final float velocityX, final float velocityY, int button) {
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
