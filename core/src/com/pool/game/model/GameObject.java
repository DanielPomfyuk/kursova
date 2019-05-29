package com.pool.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.*;

public abstract class GameObject {
    World world;
    public GameObject(World world){
        this.world = world;
    }
}
