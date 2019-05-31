package com.pool.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.Handlers.BodyData;

import static com.pool.game.Handlers.B2DVars.PPM;

public class Ball extends GameObject {
    public enum BallType {CUE,BLACK,SOLID,STRIPE,NONE}
    public Body body;
    public BallType ballType;
    public Sprite ballSprite;
    public Ball(World world,BallType ballType){
        super(world);
        this.ballType = ballType;
    }
    public Body create(int number,float positionX,float positionY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(positionX,positionY);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body ball = world.createBody(bodyDef);
        FixtureDef fd = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15/2/PPM);
        ball.setLinearDamping(0.4f);
        fd.shape = shape;
        fd.restitution = 1f;
        ball.createFixture(fd);
        this.ballSprite = new Sprite(new Texture(Gdx.files.internal(number+".png")));
        this.ballSprite.setPosition(positionX,positionY);
        this.ballSprite.setBounds(ballSprite.getX(), ballSprite.getY(), 15/PPM, 15/PPM);
        this.body = ball;
        this.body.setUserData(new BodyData(false,true,false,false,this.ballType==BallType.CUE,this.ballType));
        return ball;
    }
}
