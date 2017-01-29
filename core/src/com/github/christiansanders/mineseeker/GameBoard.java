package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;


/**
 * Created by Christian on 23-9-2016.
 */
public class GameBoard{
    private Box[][] boxes;
    private Texture boxTexture;
    private Texture boxFlaggedTexture;
    private Texture boxBombTexture;
    private Texture boxRevealedTexture;
    private BitmapFont font;
    private int nx, ny;

    public GameBoard(){
        boxTexture = new Texture("png/box.png");
        boxFlaggedTexture = new Texture("png/box-flag.png");
        boxBombTexture = new Texture("png/box-mine.png");
        boxRevealedTexture = new Texture("png/box-revealed.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.color = Color.LIME;
        this.font = generator.generateFont(parameter); // font size 12 pixels

        generator.dispose(); // don't forget to dispose to avoid memory leaks!


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
                boxes[x][y].setSize(size - 0.01f * MineSeeker.V_WIDTH, size - 0.01f * MineSeeker.V_HEIGHT);
                boxes[x][y].setPosition((float) (0.005f * MineSeeker.V_WIDTH + x * size),
                                        (float) (0.005f * MineSeeker.V_HEIGHT + y * size));
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
            addBombCount(x, y);
        }
    }

    private void addBombCount(int x, int y){
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if(x + i >= 0 && x + i < nx &&
                        y + j >= 0 && y + j <ny)
                boxes[x + i][y + j].addBombNeighbour();
            }
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

        if(x > 0 && x < MineSeeker.V_WIDTH &&
                y > 0 && y < MineSeeker.V_HEIGHT){
            int indexX = (int) x * this.nx / MineSeeker.V_WIDTH;
            int indexY = (int) y * this.ny / MineSeeker.V_HEIGHT;
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
            int indexX = (int) x * this.nx / MineSeeker.V_WIDTH;
            int indexY = (int) y * this.ny / MineSeeker.V_HEIGHT;
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
