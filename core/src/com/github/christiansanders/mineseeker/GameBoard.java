package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Christian on 23-9-2016.
 */
public class GameBoard{
    public Box[][] boxes;
    Texture boxTexture;

    public GameBoard(){
        boxTexture = new Texture("png/box.png");
    }

    public void create(int nx, int ny){
        boxes = new Box[nx][ny];
        int size = MineSeeker.V_WIDTH / nx;
        for (int x = 0; x < nx; x++) {
            for(int y = 0; y < ny; y++){
                boxes[x][y] = new Box(boxTexture);
                boxes[x][y].setSize(size - 1, size - 1);
                boxes[x][y].setPosition((float) (0.5 + x * size), (float) (0.5 + y * size));
            }
        }
        Gdx.app.debug("box", "" + boxes[9][0].getY());
        Gdx.app.debug("size", ""+boxes[3][0].getWidth());
    }

    public void render(Batch batch){
        for (Box[] boxRow : boxes) {
            for(Box box: boxRow){
                box.draw(batch);
            }
        }
    }

    public void revealBox(){

    }

    private void openSpace(){

    }

}
