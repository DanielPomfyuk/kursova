package com.pool.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.model.Ball;
import com.pool.game.model.Cue;
import com.pool.game.model.Table;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameScreen implements Screen {
    SpriteBatch batch;
    private Ball ball1;
    private Ball ball2;
    private Ball ball3;
    private Ball ball4;
    private Ball ball5;
    private Ball ball6;
    private Ball ball7;
    private Ball ball8;
    private Ball ball9;
    private Ball ball10;
    private Ball ball11;
    private Ball ball12;
    private Ball ball13;
    private Ball ball14;
    private Ball ball15;
    private Table table;
    private Cue cue;
    private World world;
    private Sprite testSpr;
    private Sprite tableSpr;
    private Fixture fixture;
    private Body body;
    private Box2DDebugRenderer debugCamera;
    private OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Body tableBody;
    private Fixture tFixture;
    private final float PIXELS_TO_METERS = 10f;
    @Override
    public void show() {
        this.debugCamera = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        Texture testText = new Texture(Gdx.files.internal("yellow_solid.png"));
        this.testSpr = new Sprite(testText);
        testSpr.setPosition(Gdx.graphics.getWidth() / 2 - testSpr.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.testSpr.setBounds(testSpr.getX(), testSpr.getY(), 50, 50);
        this.world = new World(new Vector2(0, 0), true);
        BodyDef bdy = new BodyDef();
        bdy.type = BodyDef.BodyType.DynamicBody;
        bdy.position.set(testSpr.getX(), testSpr.getY());
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(testSpr.getX(), testSpr.getY()));
        shape.setRadius(testSpr.getWidth());
        FixtureDef fixt = new FixtureDef();
        fixt.shape = shape;
        fixt.density = 1f;
        this.body = world.createBody(bdy);
        this.fixture = body.createFixture(fixt);
        // table body
        Texture tt = new Texture(Gdx.files.internal("a.png"));
        this.tableSpr = new Sprite(tt);
        BodyDef ttableBody = new BodyDef();
        ttableBody.type = BodyDef.BodyType.StaticBody;
        PolygonShape tshape = new PolygonShape();
        ttableBody.position.set(600,0);
        tshape.setAsBox(500,300);
        FixtureDef tfix = new FixtureDef();
        tfix.shape = tshape;
        tfix.density = 1f;
        this.tableBody = world.createBody(ttableBody);
        this.tFixture = this.tableBody.createFixture(tfix);
        tableSpr.setBounds(600,0,500,300);
        //table body

        table = new Table(new Texture(Gdx.files.internal("table.png")),0,0,1280,720);
        cue = new Cue(new Texture(Gdx.files.internal("cue.png")),20,260,600,162);
//        ball1 = new Ball(true,new Texture(Gdx.files.internal("yellow_solid.png")),600,260,50,50);
//        ball2 = new Ball(true,new Texture(Gdx.files.internal("blue_solid.png")),700,160,100,100);
//        ball3 = new Ball(true,new Texture(Gdx.files.internal("red_solid.png")),800,60,100,100);
        ball4 = new Ball(true,new Texture(Gdx.files.internal("purple_solid.png")),800,260,50,50);
//        ball5 = new Ball(true,new Texture(Gdx.files.internal("orange_solid.png")),800,460,100,100);
//        ball6 = new Ball(true,new Texture(Gdx.files.internal("green_solid.png")),700,360,100,100);
//        ball7 = new Ball(true,new Texture(Gdx.files.internal("brown_solid.png")),750,210,100,100);
//        ball9 = new Ball(false,new Texture(Gdx.files.internal("yellow_stripe.png")),650,210,100,100);
//        ball14 = new Ball(false,new Texture(Gdx.files.internal("green_stripe.png")),650,310,100,100);
//        ball8 = new Ball(false,new Texture(Gdx.files.internal("black_solid.png")),700,260,100,100);
//        ball10 = new Ball(false,new Texture(Gdx.files.internal("blue_stripe.png")),750,110,100,100);
        ball11 = new Ball(false,new Texture(Gdx.files.internal("red_stripe.png")),800,160,50,50);
//        ball12 = new Ball(false,new Texture(Gdx.files.internal("purple_stripe.png")),800,360,100,100);
//        ball13 = new Ball(false,new Texture(Gdx.files.internal("orange_stripe.png")),750,410,100,100);
//        ball15 = new Ball(true,new Texture(Gdx.files.internal("brown_stripe.png")),750,310,100,100);
        body.applyLinearImpulse(new Vector2(1000,-5000), body.getPosition(),false);
    }
    @Override
    public void render(float delta) {
        camera.update();
        world.step(Gdx.graphics.getDeltaTime(),6,2);
        tableSpr.setPosition(tableBody.getPosition().x,tableBody.getPosition().y);
        testSpr.setPosition(body.getPosition().x,body.getPosition().y);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,PIXELS_TO_METERS,0);
        batch.begin();
        table.draw(batch);
        batch.draw(tableSpr,tableSpr.getX(),tableSpr.getY(),500,300);
        cue.draw(batch);
//        ball1.draw(batch);
//        ball2.draw(batch);
//        ball3.draw(batch);
        ball4.draw(batch);
//        ball5.draw(batch);
//        ball6.draw(batch);
//        ball7.draw(batch);
//        ball8.draw(batch);
//        ball9.draw(batch);
//        ball10.draw(batch);
        ball11.draw(batch);
//        ball12.draw(batch);
//        ball13.draw(batch);
//        ball14.draw(batch);
//        ball15.draw(batch);
        batch.draw(testSpr,testSpr.getX(),testSpr.getY(), 50, 50);
        batch.end();
        debugCamera.render(world,debugMatrix);
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
