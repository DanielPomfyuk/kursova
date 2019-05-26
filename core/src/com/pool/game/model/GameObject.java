package com.pool.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public abstract class GameObject {
    Rectangle bounds;
    Sprite object;
    public GameObject(Texture texture,int x ,int y,int width,int height){
        this.bounds = new Rectangle(x,y,width,height);
        this.object = new Sprite(texture);
    }
    public void draw(SpriteBatch batch){
        object.setBounds(
                (float)this.bounds.getX(),
                (float)this.bounds.getY(),
                (float)this.bounds.getWidth(),
                (float)this.bounds.getHeight()
                );
        object.draw(batch);
    }
}
