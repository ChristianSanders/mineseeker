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
    Texture boxFlaggedTexture;
    private int nx, ny;

    public GameBoard(){
        boxTexture = new Texture("png/box.png");
        boxFlaggedTexture = new Texture("png/box-flag.png");
    }

    public void create(int nx, int ny){
        this.nx = nx;
        this.ny = ny;
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

    public void revealBox(float x, float y){
        Gdx.app.debug("reveal box", String.valueOf((int) x / this.nx));
        if(x > 0 && x < MineSeeker.V_WIDTH &&
                y > 0 && y < MineSeeker.V_WIDTH){
            int indexX = (int) x / this.nx;
            int indexY = (int) y / this.ny;
            Box target = boxes[indexX][indexY];
            if(target.isFlagged()){
                target.setFlagged(false);
                target.setTexture(boxTexture);
            } else if(!target.isBomb()){
                target.reveal();
            }
        }
    }

    public void flagBox(float x, float y){
        if(x > 0 && x < MineSeeker.V_WIDTH &&
                y > 0 && y < MineSeeker.V_WIDTH){
            int indexX = (int) x / this.nx;
            int indexY = (int) y / this.ny;
            Box target = boxes[indexX][indexY];
            if(!target.isFlagged()){
                target.setFlagged(true);
                target.setTexture(boxFlaggedTexture);
            }else{
                target.setFlagged(false);
                target.setTexture(boxTexture);
            }
        }
    }

    private void openSpace(){

    }

}
