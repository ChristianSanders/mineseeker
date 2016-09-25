package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


/**
 * Created by Christian on 23-9-2016.
 */
public class GameBoard{
    public Box[][] boxes;
    Texture boxTexture;
    Texture boxFlaggedTexture;
    Texture boxBombTexture;
    Texture boxRevealedTexture;
    BitmapFont font;
    private int nx, ny;

    public GameBoard(){
        boxTexture = new Texture("png/box.png");
        boxFlaggedTexture = new Texture("png/box-flag.png");
        boxBombTexture = new Texture("png/box-mine.png");
        boxRevealedTexture = new Texture("png/box-revealed.png");
        font = new BitmapFont(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(0.3f);
        font.setColor(Color.LIME);
    }

    public void create(int nx, int ny){
        this.nx = nx;
        this.ny = ny;
        createBoard();
        addBombs(10);
    }
    private void createBoard(){
        boxes = new Box[nx][ny];
        int size = MineSeeker.V_WIDTH / nx;
        for (int x = 0; x < nx; x++) {
            for(int y = 0; y < ny; y++){
                boxes[x][y] = new Box(boxTexture, font);
                boxes[x][y].setSize(size - 1, size - 1);
                boxes[x][y].setPosition((float) (0.5 + x * size), (float) (0.5 + y * size));
            }
        }
    }

    private void addBombs(int amount){
        int x;
        int y;

        for (int i = 0; i < amount; i++){

            do{
                x = MathUtils.random(0, nx - 1);
                y = MathUtils.random(0, nx - 1);
            }while(boxes[x][y].isBomb());

            boxes[x][y].setBomb(true);
        }
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
                target.reveal(boxRevealedTexture);
            } else{
                target.reveal(boxBombTexture);
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
