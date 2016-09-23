package com.github.christiansanders.mineseeker;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Christian on 23-9-2016.
 */
public class Box extends Sprite {
    private Boolean isVisible;
    private Boolean isBomb;

    public Box(){
        isVisible = false;
        isBomb = true;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean getIsBomb() {
        return isBomb;
    }

    public void setIsBomb(Boolean isBomb) {
        this.isBomb = isBomb;
    }
}
