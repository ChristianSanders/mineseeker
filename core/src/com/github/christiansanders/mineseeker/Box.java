package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Christian on 23-9-2016.
 */
public class Box extends Sprite {
    private Boolean visible;
    private Boolean bomb;
    private Boolean flagged;

    public Box(Texture texture){
        super(texture);
        visible = false;
        bomb = false;
        flagged = false;
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

    public void reveal(){
        this.setTexture(new Texture("badlogic.jpg"));
    }

    public Boolean isFlagged(){
        return this.flagged;
    }

    public void setFlagged(Boolean flagged){
        this.flagged = flagged;
    }


}
