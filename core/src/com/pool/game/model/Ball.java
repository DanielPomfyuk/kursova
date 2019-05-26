package com.pool.game.model;

import com.badlogic.gdx.graphics.Texture;

public class Ball extends GameObject{
    boolean isSolid = true;
    public Ball(boolean isSolid,Texture texture, int x, int y, int width, int height) {
        super(texture, x, y, width, height);
        this.isSolid = isSolid;
    }
}
