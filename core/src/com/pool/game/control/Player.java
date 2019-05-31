package com.pool.game.control;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pool.game.model.Ball;

public class Player {
    public Ball.BallType ballType;
    public Sprite sprite;
    public Player(Sprite sprite){
        this.sprite = sprite;
    }
}
