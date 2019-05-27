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
    private Sprite ballSprite;
    private Sprite tableSpr;
    private Fixture ballFixture;
    private Body ballBody;
    private Box2DDebugRenderer debugCamera;
    private OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Body tableBody;
    private Fixture tFixture;
    private final float PIXELS_TO_METERS = 1f;
    @Override
    public void show() {
        this.debugCamera = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch = new SpriteBatch();
//
        this.ballSprite = new Sprite(new Texture(Gdx.files.internal("yellow_solid.png")));
        ballSprite.setPosition(Gdx.graphics.getWidth() / 2 - ballSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.ballSprite.setBounds(ballSprite.getX(), ballSprite.getY(), 50, 50);
        this.world = new World(new Vector2(0, -98f), true);
        BodyDef ballBd = new BodyDef();
        ballBd.type = BodyDef.BodyType.DynamicBody;
        ballBd.position.set(ballSprite.getX(), ballSprite.getY());
        CircleShape ballShape = new CircleShape();
        ballShape.setPosition(new Vector2(ballSprite.getX(), ballSprite.getY()));
        ballShape.setRadius(ballSprite.getWidth()/2);
        FixtureDef ballFixt = new FixtureDef();
        ballFixt.shape = ballShape;
        ballFixt.density = 1f;
        this.ballBody = world.createBody(ballBd);
        this.ballFixture = ballBody.createFixture(ballFixt);
//        // table ballBody
//        this.tableSpr = new Sprite(new Texture(Gdx.files.internal("a.png")));
//        tableSpr.setPosition(600,0);
//        tableSpr.setBounds(tableSpr.getX(),tableSpr.getY(),500,200);
//        BodyDef ttableBody = new BodyDef();
//        ttableBody.type = BodyDef.BodyType.StaticBody;
//        PolygonShape tshape = new PolygonShape();
//        ttableBody.position.set(tableSpr.getX(),tableSpr.getY());
//        tshape.setAsBox(250,150);
//        FixtureDef tfix = new FixtureDef();
//        tfix.shape = tshape;
//        tfix.density = 1f;
//        this.tableBody = world.createBody(ttableBody);
//        this.tFixture = this.tableBody.createFixture(tfix);
//
//        //table ballBody
//
//        table = new Table(new Texture(Gdx.files.internal("table.png")),0,0,1280,720);
//        cue = new Cue(new Texture(Gdx.files.internal("cue.png")),20,260,600,162);
//        ball1 = new Ball(true,new Texture(Gdx.files.internal("yellow_solid.png")),600,260,50,50);
//        ball2 = new Ball(true,new Texture(Gdx.files.internal("blue_solid.png")),700,160,100,100);
//        ball3 = new Ball(true,new Texture(Gdx.files.internal("red_solid.png")),800,60,100,100);
//        ball4 = new Ball(true,new Texture(Gdx.files.internal("purple_solid.png")),800,260,50,50);
//        ball5 = new Ball(true,new Texture(Gdx.files.internal("orange_solid.png")),800,460,100,100);
//        ball6 = new Ball(true,new Texture(Gdx.files.internal("green_solid.png")),700,360,100,100);
//        ball7 = new Ball(true,new Texture(Gdx.files.internal("brown_solid.png")),750,210,100,100);
//        ball9 = new Ball(false,new Texture(Gdx.files.internal("yellow_stripe.png")),650,210,100,100);
//        ball14 = new Ball(false,new Texture(Gdx.files.internal("green_stripe.png")),650,310,100,100);
//        ball8 = new Ball(false,new Texture(Gdx.files.internal("black_solid.png")),700,260,100,100);
//        ball10 = new Ball(false,new Texture(Gdx.files.internal("blue_stripe.png")),750,110,100,100);
//        ball11 = new Ball(false,new Texture(Gdx.files.internal("red_stripe.png")),800,160,50,50);
//        ball12 = new Ball(false,new Texture(Gdx.files.internal("purple_stripe.png")),800,360,100,100);
//        ball13 = new Ball(false,new Texture(Gdx.files.internal("orange_stripe.png")),750,410,100,100);
//        ball15 = new Ball(true,new Texture(Gdx.files.internal("brown_stripe.png")),750,310,100,100);
//        ballBody.applyLinearImpulse(new Vector2(1000,-5000), ballBody.getPosition(),false);
    }
    @Override
    public void render(float delta) {
        camera.update();
        world.step(Gdx.graphics.getDeltaTime(),6,2);
//        tableSpr.setPosition(tableBody.getPosition().x,tableBody.getPosition().y);
        float a = ballBody.getPosition().y;
        ballSprite.setPosition(ballBody.getPosition().x - ballSprite.getWidth()/2, ballBody.getPosition().y-ballSprite.getHeight()/2);
        ballSprite.setOrigin(ballBody.getPosition().x, ballBody.getPosition().y);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//
//        cue.draw(batch);
////        ball1.draw(batch);
////        ball2.draw(batch);
////        ball3.draw(batch);
////        ball4.draw(batch);
////        ball5.draw(batch);
////        ball6.draw(batch);
////        ball7.draw(batch);
////        ball8.draw(batch);
////        ball9.draw(batch);
////        ball10.draw(batch);
////        ball11.draw(batch);
////        ball12.draw(batch);
////        ball13.draw(batch);
////        ball14.draw(batch);
////        ball15.draw(batch);
        batch.draw(ballSprite, ballSprite.getX(), ballSprite.getY(), 50, 50);
//        batch.draw(tableSpr,tableSpr.getX(),tableSpr.getY(),500,200);
////        this.renderer.render(this.world, this.camera.combined);
        batch.end();
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 1);
        debugCamera.render(world, debugMatrix);
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
    public void act() {
        this.camera.position.set(1280/2, 720/2, 0);
        this.camera.update();
    }
}
