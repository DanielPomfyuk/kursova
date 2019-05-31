package com.pool.game.Handlers;

import com.pool.game.model.Ball;

public class BodyData {
    public boolean isReadyToDie;
    public boolean isABall;
    public boolean isAPocket;
    public boolean isAWall;
    public boolean isaCueBall;
    public Ball.BallType ballType;
    public BodyData(boolean isReadyToDie, boolean isABall, boolean isAPocket, boolean isAWall, boolean isaCueBall, Ball.BallType ballType){
        this.isReadyToDie = isReadyToDie;
        this.isABall = isABall;
        this.isAPocket = isAPocket;
        this.isAWall = isAWall;
        this.isaCueBall = isaCueBall;
        this.ballType = ballType;
    }
}
