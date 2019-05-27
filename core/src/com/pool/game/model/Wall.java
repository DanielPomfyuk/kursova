package com.pool.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

public class Wall {

    private World world;
    public Wall(World world) {
        this.world = world;
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
        return body;
    }
}
