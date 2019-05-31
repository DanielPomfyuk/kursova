package com.pool.game.Handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.PoolMainClass;
import com.pool.game.control.Player;
import com.pool.game.model.Ball;

public class ContactHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body firstBody = contact.getFixtureA().getBody();
        Body secondBody = contact.getFixtureB().getBody();
        BodyData firstData = (BodyData)firstBody.getUserData();
        BodyData secondData = (BodyData)secondBody.getUserData();
        if((firstData.isABall && secondData.isAPocket) || (firstData.isAPocket && secondData.isABall)){
            BodyData ballData = firstData.isABall ? firstData : secondData;
            if(ballData.ballType == Ball.BallType.BLACK){
                for (Ball ball:PoolMainClass.balls) {
                    if(ball.ballType == Ball.BallType.CUE||ball.ballType== Ball.BallType.BLACK){
                        continue;
                    }
                    else if(ball.ballType == PoolMainClass.currentPlayer.ballType) {
                        PoolMainClass.winner = PoolMainClass.currentPlayer==PoolMainClass.player1?PoolMainClass.player2:PoolMainClass.player1;
                        return;
                    }
                }
                if(PoolMainClass.balls.size() == 2){
                    PoolMainClass.winner = PoolMainClass.currentPlayer;
                    return;
                }
            }
            if(!ballData.isaCueBall && PoolMainClass.player1.ballType==null&&PoolMainClass.player2.ballType==null){
                PoolMainClass.currentPlayer.ballType = ballData.ballType;
                Player secPlayer = PoolMainClass.currentPlayer==PoolMainClass.player1?PoolMainClass.player2:PoolMainClass.player1;
                secPlayer.ballType = PoolMainClass.currentPlayer.ballType == Ball.BallType.SOLID? Ball.BallType.STRIPE: Ball.BallType.SOLID;
            }
            if(ballData.ballType == PoolMainClass.currentPlayer.ballType){
                PoolMainClass.ballWasPotted = true;
            }
            ballData.isReadyToDie = true;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    public static void nextPlayer(){
        PoolMainClass.currentPlayer = PoolMainClass.currentPlayer == PoolMainClass.player1 ? PoolMainClass.player2 : PoolMainClass.player1;
    }
}
