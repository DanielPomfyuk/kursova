package com.pool.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.Handlers.BodyData;

import java.util.List;

public class Wall extends GameObject{
    public Wall(World world){
        super(world);
    }
    public Body create(Vector2[] points) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.set(points);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
        body.setUserData(new BodyData(false,false,false,true,false, Ball.BallType.NONE));
        return body;
    }
}
