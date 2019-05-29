package com.pool.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.Handlers.BodyData;

public class Pocket extends GameObject {
    public Pocket(World world) {
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
        body.setUserData(new BodyData(false,false,true,false));
        return body;
    }
}
