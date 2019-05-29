package com.pool.game.Handlers;

import com.badlogic.gdx.physics.box2d.*;

public class ContactHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body firstBody = contact.getFixtureA().getBody();
        Body secondBody = contact.getFixtureB().getBody();
        BodyData firstData = (BodyData)firstBody.getUserData();
        BodyData secondData = (BodyData)secondBody.getUserData();
        if (firstData == null || secondData == null){
            int a = 5;
        }
        if(firstData.isABall && secondData.isAPocket){
            firstData.isReadyToDie = true;
        }
        else if(firstData.isAPocket && secondData.isABall){
            secondData.isReadyToDie = true;
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
}
