package com.pool.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.pool.game.Handlers.BodyData;
import com.pool.game.Handlers.ContactHandler;
import com.pool.game.Handlers.InputHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.control.Player;
import com.pool.game.model.Ball;
import com.pool.game.model.Pocket;
import com.pool.game.model.Wall;

import java.util.*;

import static com.pool.game.Handlers.B2DVars.PPM;

public class PoolMainClass extends Game {
    private Screen gameScreen;
    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 360;
    public static final int SCALE = 2;
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Sprite table;
    private ShapeRenderer shapeRenderer;
    public static List<Ball> balls;
    private Ball cueBall;
    private Sprite cueSprite;
    private Sprite winnerSprite;
    public static Player player1;
    public static Player player2;
    public static Player currentPlayer;
    public static Player winner;
    private BitmapFont font;
    private InputHandler inputHandler;
    public static boolean ballWasPotted=false;
    public static boolean moveEnded = false;
    private  boolean cheaked;
    @Override
    public void create() {
        cheaked = false;
        player1 = new Player(new Sprite(new Texture(Gdx.files.internal("player1.png"))));
        player2 = new Player(new Sprite(new Texture(Gdx.files.internal("player2.png"))));
        currentPlayer = player2;
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        shapeRenderer = new ShapeRenderer();
        balls = new ArrayList<Ball>();
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, V_WIDTH / PPM, V_HEIGHT / PPM);
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new ContactHandler());
        b2dr = new Box2DDebugRenderer();
        createATable(generateFiguresCordinates(), generateBallsPiramide(), generatePocketsCordinates());
        table = new Sprite(new Texture(Gdx.files.internal("table.png")));
        cueSprite = new Sprite(new Texture(Gdx.files.internal("cue2.png")),8,1);
        inputHandler.ball = cueBall.body;
        inputHandler.ballsOnTheTable = balls;
        cueSprite.setPosition(InputHandler.GetXMouseCoord(), InputHandler.GetYMouseCoord());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 30f, 6, 2);
        if(cheackIfAnyoneWon()){
            winnerSprite = new Sprite(new Texture(winner==player1?"winner1.png":"winner2.png"));
            sb.begin();
            sb.draw(winnerSprite,0, 0, V_WIDTH / PPM, V_HEIGHT / PPM);
            sb.end();
        }
        else {
            inputHandler.updateMovingData();
            sweepDeadBodies();
            if (currentPlayer.ballType != null && !cheaked) {
                cheaked = true;
                player1.sprite = player1.ballType == Ball.BallType.SOLID ? new Sprite(new Texture("player1_solid.png")) : new Sprite(new Texture("player1_stripes.png"));
                player2.sprite = player2.ballType == Ball.BallType.SOLID ? new Sprite(new Texture("player2_solid.png")) : new Sprite(new Texture("player2_stripes.png"));
            }
            sb.setProjectionMatrix(cam.combined);
            sb.begin();
            sb.draw(table, 0, 0, V_WIDTH / PPM, V_HEIGHT / PPM);
            Sprite curpl = currentPlayer == player1 ? player1.sprite : player2.sprite;
            sb.draw(curpl, 50 / PPM, 7 / PPM, 15, 3);
            for (Ball ball : balls) {
                ball.ballSprite.setPosition(ball.body.getPosition().x - ball.ballSprite.getWidth() / 2, ball.body.getPosition().y - ball.ballSprite.getHeight() / 2);
                ball.ballSprite.setOrigin(ball.body.getPosition().x, ball.body.getPosition().y);
                sb.draw(ball.ballSprite, ball.ballSprite.getX(), ball.ballSprite.getY(), 15 / PPM, 15 / PPM);
            }
            if (!InputHandler.IsInMove()) {
                if (!moveEnded && !ballWasPotted) {
                    ContactHandler.nextPlayer();
                    moveEnded = true;
                }
                updateSprite();
                cueSprite.draw(sb);
            }
            sb.end();
            b2dr.render(world, cam.combined);
        }
    }

    public void createATable(List<Vector2[]> vectors, List<Vector2> points, List<Vector2[]> pockets) {
        for (Vector2[] figure : vectors) {
            Wall side = new Wall(this.world);
            side.create(figure);
        }
        for (Vector2[] pocketCord : pockets) {
            Pocket pocket = new Pocket(world);
            pocket.create(pocketCord);
        }
        int counter = 1;
        for (Vector2 point : points) {
            Ball.BallType ballType;
            if (counter < 8) ballType = Ball.BallType.SOLID;
            else if (counter == 8) ballType = Ball.BallType.BLACK;
            else if (counter < 16) ballType = Ball.BallType.STRIPE;
            else {
                ballType = Ball.BallType.CUE;
            }
            Ball ball = new Ball(this.world, ballType);
            ball.create(counter, point.x, point.y);
            if (ball.ballType == Ball.BallType.CUE) {
                this.cueBall = ball;
            }
            balls.add(ball);
            counter++;
        }
    }

    public List<Vector2[]> generateFiguresCordinates() {
        List<Vector2[]> vector2s = new ArrayList<Vector2[]>();
        //bottom sides
        Vector2[] leftBottom = new Vector2[4];
        leftBottom[0] = new Vector2(20 / PPM, 0);
        leftBottom[1] = new Vector2(60 / PPM, 40 / PPM);
        leftBottom[2] = new Vector2(300 / PPM, 40 / PPM);
        leftBottom[3] = new Vector2(315 / PPM, 0);
        vector2s.add(leftBottom);
        Vector2[] rightBottom = new Vector2[4];
        rightBottom[0] = new Vector2(325 / PPM, 0);
        rightBottom[1] = new Vector2(340 / PPM, 40 / PPM);
        rightBottom[2] = new Vector2(575 / PPM, 40 / PPM);
        rightBottom[3] = new Vector2(615 / PPM, 0);
        vector2s.add(rightBottom);

        //bottom sides

        //top sides
        Vector2[] leftTop = new Vector2[4];
        leftTop[0] = new Vector2(20 / PPM, 360 / PPM);
        leftTop[1] = new Vector2(60 / PPM, 320 / PPM);
        leftTop[2] = new Vector2(300 / PPM, 320 / PPM);
        leftTop[3] = new Vector2(315 / PPM, 360 / PPM);
        vector2s.add(leftTop);

        Vector2[] rightTop = new Vector2[4];
        rightTop[0] = new Vector2(325 / PPM, 360 / PPM);
        rightTop[1] = new Vector2(340 / PPM, 320 / PPM);
        rightTop[2] = new Vector2(575 / PPM, 320 / PPM);
        rightTop[3] = new Vector2(615 / PPM, 360 / PPM);
        vector2s.add(rightTop);

        //top sides

        Vector2[] leftSide = new Vector2[4];
        leftSide[0] = new Vector2(0, 340 / PPM);
        leftSide[1] = new Vector2(0, 20 / PPM);
        leftSide[2] = new Vector2(45 / PPM, 60 / PPM);
        leftSide[3] = new Vector2(45 / PPM, 300 / PPM);
        vector2s.add(leftSide);
        Vector2[] rightSide = new Vector2[4];
        rightSide[0] = new Vector2(645 / PPM, 340 / PPM);
        rightSide[1] = new Vector2(645 / PPM, 20 / PPM);
        rightSide[2] = new Vector2(595 / PPM, 60 / PPM);
        rightSide[3] = new Vector2(595 / PPM, 300 / PPM);
        vector2s.add(rightSide);

        //side sides

        return vector2s;
    }

    public List<Vector2> generateBallsPiramide() {
        float firstX = (V_WIDTH + 150) / 2 / PPM;
        float firstY = V_HEIGHT / 2 / PPM;
        float ballDiameter = 15 / PPM;
        List<Vector2> positions = new ArrayList<Vector2>();
        //cue ball
        positions.add(new Vector2(firstX, firstY));
        positions.add(new Vector2(firstX + ballDiameter, firstY - ballDiameter / 2 / PPM));
        positions.add(new Vector2(firstX + ballDiameter * 2, firstY + ballDiameter));
        positions.add(new Vector2(firstX + ballDiameter, firstY + ballDiameter / 2 / PPM));
        positions.add(new Vector2(firstX + ballDiameter * 2, firstY - ballDiameter));
        positions.add(new Vector2(firstX + ballDiameter * 2, firstY));
        positions.add(new Vector2(firstX + ballDiameter * 3, firstY - ballDiameter * 1 / 2));
        positions.add(new Vector2(firstX + ballDiameter * 3, firstY + ballDiameter * 3 / 2));
        positions.add(new Vector2(firstX + ballDiameter * 4, firstY + ballDiameter));
        positions.add(new Vector2(firstX + ballDiameter * 3, firstY + ballDiameter / 2));
        positions.add(new Vector2(firstX + ballDiameter * 3, firstY - ballDiameter * 3 / 2));
        positions.add(new Vector2(firstX + ballDiameter * 4, firstY));
        positions.add(new Vector2(firstX + ballDiameter * 4, firstY - ballDiameter));
        positions.add(new Vector2(firstX + ballDiameter * 4, firstY + ballDiameter * 2));
        positions.add(new Vector2(firstX + ballDiameter * 4, firstY - ballDiameter * 2));
        positions.add(new Vector2(148 / PPM, V_HEIGHT / 2 / PPM));
        return positions;
    }

    public List<Vector2[]> generatePocketsCordinates() {
        List<Vector2[]> pockets = new ArrayList<Vector2[]>();
        Vector2[] leftTop = new Vector2[4];
        leftTop[0] = new Vector2(3, 31);
        leftTop[1] = new Vector2(4, 34);
        leftTop[2] = new Vector2(5, 33);
        leftTop[3] = new Vector2(2, 32);
        pockets.add(leftTop);
        Vector2[] topMiddle = new Vector2[4];
        topMiddle[0] = new Vector2(30, 33);
        topMiddle[1] = new Vector2(34, 33);
        topMiddle[2] = new Vector2(34, 34);
        topMiddle[3] = new Vector2(30, 34);
        pockets.add(topMiddle);
        Vector2[] rightTop = new Vector2[4];
        rightTop[0] = new Vector2(59, 34);
        rightTop[1] = new Vector2(61, 32);
        rightTop[2] = new Vector2(61, 31);
        rightTop[3] = new Vector2(59, 33);
        pockets.add(rightTop);
        Vector2[] rightBottom = new Vector2[4];
        rightBottom[0] = new Vector2(61, 2);
        rightBottom[1] = new Vector2(61, 5);
        rightBottom[2] = new Vector2(60, 1);
        rightBottom[3] = new Vector2(59, 2);
        pockets.add(rightBottom);
        Vector2[] bottomMiddle = new Vector2[4];
        bottomMiddle[0] = new Vector2(30, 3);
        bottomMiddle[1] = new Vector2(34, 3);
        bottomMiddle[2] = new Vector2(34, 2);
        bottomMiddle[3] = new Vector2(30, 2);
        pockets.add(bottomMiddle);
        Vector2[] leftBottom = new Vector2[4];
        leftBottom[0] = new Vector2(3, 4);
        leftBottom[1] = new Vector2(5, 3);
        leftBottom[2] = new Vector2(4, 2);
        leftBottom[3] = new Vector2(3, 5);
        pockets.add(leftBottom);
        return pockets;
    }

    public void sweepDeadBodies() {
        List<Ball> deadBodies = new ArrayList<Ball>();
        boolean wasAnyValidBall = false;
        for (Ball ball : balls) {
            BodyData bodyData = (BodyData) ball.body.getUserData();
            Ball.BallType ballType = ball.ballType;
            if (bodyData.isReadyToDie) {
                world.destroyBody(ball.body);
                ball.body.setUserData(null);
                if (ballType == Ball.BallType.CUE) {
                    ball = new Ball(world, Ball.BallType.CUE);
                    ball.body = ball.create(15, 148 / PPM, V_HEIGHT / 2 / PPM);
                    this.cueBall = ball;
                } else {
                    deadBodies.add(ball);
                }
            }
        }
        for (Ball deadBall : deadBodies) {
            balls.remove(deadBall);
        }

    }

    public void updateSprite() {
        Vector2 newpos = new Vector2(InputHandler.GetXMouseCoord() - cueBall.body.getPosition().x, InputHandler.GetYMouseCoord() - cueBall.body.getPosition().y);
        cueSprite.setOrigin(0, cueSprite.getOriginY());
        cueSprite.setRotation(newpos.angle());
        cueSprite.setPosition(cueBall.body.getPosition().x,cueBall.body.getPosition().y-cueSprite.getHeight()/2);
    }
    public boolean cheackIfAnyoneWon(){
        return winner != null;
    }
}
