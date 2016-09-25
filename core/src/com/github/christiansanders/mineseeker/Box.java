package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Christian on 23-9-2016.
 */
public class Box extends Sprite {
    private Boolean visible;
    private Boolean bomb;
    private Boolean flagged;
    private Boolean revealed;
    private int bombNeighbours;
    private String bombNeighboursString;
    private BitmapFont font;


    public Box(Texture texture, BitmapFont font){
        super(texture);
        this.font = font;
        visible = false;
        bomb = false;
        flagged = false;
        revealed = false;
        bombNeighbours = 0;
        bombNeighboursString = "";
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean isVisible) {
        this.visible = isVisible;
    }

    public Boolean isBomb() {
        return bomb;
    }

    public void setBomb(Boolean bomb) {
        this.bomb = bomb;
    }

    public void reveal(Texture texture){
        this.setTexture(texture);
        if(!isBomb()){
            revealed = true;
        }
    }

    public Boolean isFlagged(){
        return this.flagged;
    }

    public void setFlagged(Boolean flagged){
        this.flagged = flagged;
    }

    @Override public void draw(Batch batch){
        super.draw(batch);
        if(revealed) {
            font.draw(batch, bombNeighboursString, getX() + (getWidth() - font.getXHeight()) / 2,
                    getY() + (getHeight() + font.getXHeight()) / 2);
        }
    }

    public void addBombNeighbour(){
        this.bombNeighbours += 1;
        this.bombNeighboursString = String.valueOf(bombNeighbours);
    }
}
