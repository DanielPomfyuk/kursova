package com.pool.game.Handlers;

public class BodyData {
    public boolean isReadyToDie;
    public boolean isABall;
    public boolean isAPocket;
    public boolean isAWall;
    public BodyData(boolean isReadyToDie,boolean isABall,boolean isAPocket,boolean isAWall){
        this.isReadyToDie = isReadyToDie;
        this.isABall = isABall;
        this.isAPocket = isAPocket;
        this.isAWall = isAWall;
    }
}
