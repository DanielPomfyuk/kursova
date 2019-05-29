package com.pool.game;
import com.badlogic.gdx.utils.Array;
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
import com.pool.game.model.Ball;
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
	private Array<Body> worldBodies;
	private Box2DDebugRenderer b2dr;
	private Sprite table;
	private ShapeRenderer shapeRenderer;
	private List<Ball> balls;
	private Ball cueBall;

	@Override
	public void create() {
		InputHandler koko = new InputHandler();
		Gdx.input.setInputProcessor(koko);
		shapeRenderer = new ShapeRenderer();
		worldBodies = new Array<Body>();
		balls = new ArrayList<Ball>();
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH / PPM, V_HEIGHT / PPM);
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new ContactHandler());
		b2dr = new Box2DDebugRenderer();
		createATable(generateFiguresCordinates(), generateBallsPiramide());
		table = new Sprite(new Texture(Gdx.files.internal("table.png")));
		cueBall = balls.get(15);
		float xCoord = cueBall.body.getPosition().x - V_WIDTH / 2 / PPM;
		float yCoord = cueBall.body.getPosition().y - V_HEIGHT / 2 / PPM;
		koko.sharik = cueBall.body;

		///////
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		Vector2[] aa = new Vector2[4];
		aa[0] = new Vector2(3, 31);
		aa[1] = new Vector2(4, 34);
		aa[2] = new Vector2(5, 33);
		aa[3] = new Vector2(2, 32);
		shape.set(aa);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		body.setUserData(new BodyData(false,false,true,false));
		body.createFixture(fdef);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		sweepDeadBodies();
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(table, 0, 0, V_WIDTH / PPM, V_HEIGHT / PPM);
		for (Ball ball : balls) {
			ball.ballSprite.setPosition(ball.body.getPosition().x - ball.ballSprite.getWidth() / 2, ball.body.getPosition().y - ball.ballSprite.getHeight() / 2);
			ball.ballSprite.setOrigin(ball.body.getPosition().x, ball.body.getPosition().y);
			sb.draw(ball.ballSprite, ball.ballSprite.getX(), ball.ballSprite.getY(), 15 / PPM, 15 / PPM);
		}
		sb.end();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.line(cueBall.body.getPosition().x, cueBall.body.getPosition().y, InputHandler.GetXMouseCoord(), InputHandler.GetYMouseCoord());
		shapeRenderer.end();
		b2dr.render(world, cam.combined);
	}

	public void createATable(List<Vector2[]> vectors, List<Vector2> points) {
		for (Vector2[] figure : vectors) {
			Wall side = new Wall(this.world);
			side.create(figure);
		}
		int counter = 1;
		for (Vector2 point : points) {
			Ball.BallType ballType;
			if (counter < 8) ballType = Ball.BallType.SOLID;
			else if (counter == 8) ballType = Ball.BallType.BLACK;
			else if (counter < 16) ballType = Ball.BallType.STRIPE;
			else ballType = Ball.BallType.CUE;
			Ball ball = new Ball(this.world, ballType);
			ball.create(counter, point.x, point.y);
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

	public void sweepDeadBodies() {
		List<Ball> deadBodies = new ArrayList<Ball>();
		for (Ball ball:balls) {
			Body body = ball.body;
			BodyData bodyData = (BodyData)body.getUserData();
			if (bodyData.isReadyToDie){
				world.destroyBody(body);
				body.setUserData(null);
				body = null;
				deadBodies.add(ball);
			}
		}
		for (Ball ball :deadBodies) {
			balls.remove(ball);
		}
	}
}
