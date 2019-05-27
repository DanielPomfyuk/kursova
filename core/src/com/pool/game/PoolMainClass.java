package com.pool.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pool.game.model.Wall;
import com.pool.game.view.GameScreen;
import com.pool.game.Handlers.B2DVars;

import java.util.*;

import static com.pool.game.Handlers.B2DVars.PPM;

public class PoolMainClass extends Game {
	private Screen gameScreen;
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 360;
	public static final int SCALE = 2;
	private SpriteBatch sb ;
	private OrthographicCamera cam;
	private World world;
	private Box2DDebugRenderer b2dr;
	private Sprite table;
	@Override
	public void create() {
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH/PPM, V_HEIGHT/PPM);
		world = new World(new Vector2(0,-9.8f),true);
		b2dr = new Box2DDebugRenderer();
		createATable(generateFiguresCordinates());
		table =  new Sprite(new Texture(Gdx.files.internal("table.png")));
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(V_WIDTH/2/PPM, V_HEIGHT/2/PPM);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		Body ball = world.createBody(bodyDef);
		FixtureDef fd = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(15/2/PPM);
		fd.shape = shape;
		ball.createFixture(fd);
	}

	@Override
	public void render(){
		world.step(Gdx.graphics.getDeltaTime(),6,2);
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(table,0,0,V_WIDTH/PPM,V_HEIGHT/PPM);
		sb.end();
		b2dr.render(world,cam.combined);
	}
	public void createATable(List<Vector2[]> vectors){
		for (Vector2[] figure:vectors) {
			Wall side = new Wall(this.world);
			side.create(figure);
		}
	}
	public List<Vector2[]> generateFiguresCordinates(){
		List<Vector2[]> vector2s = new ArrayList<Vector2[]>();
		//bottom sides
		Vector2[] leftBottom = new Vector2[4];
		leftBottom[0] = new Vector2(20/PPM,0);
		leftBottom[1]= new Vector2(60/PPM, 40/PPM);
		leftBottom[2] = new Vector2(295/PPM,40/PPM);
		leftBottom[3] = new Vector2(310/PPM,0);
		vector2s.add(leftBottom);
		Vector2[] rightBottom = new Vector2[4];
		rightBottom[0] = new Vector2(323/PPM,0);
		rightBottom[1]= new Vector2(337/PPM, 40/PPM);
		rightBottom[2] = new Vector2(575/PPM,40/PPM);
		rightBottom[3] = new Vector2(615/PPM,0);
		vector2s.add(rightBottom);

		//bottom sides

		//top sides
		Vector2[] leftTop = new Vector2[4];
		leftTop[0] = new Vector2(20/PPM,360/PPM);
		leftTop[1]= new Vector2(60/PPM, 320/PPM);
		leftTop[2] = new Vector2(295/PPM,320/PPM);
		leftTop[3] = new Vector2(310/PPM,360/PPM);
		vector2s.add(leftTop);

		Vector2[] rightTop = new Vector2[4];
		rightTop[0] = new Vector2(323/PPM,360/PPM);
		rightTop[1]= new Vector2(337/PPM, 320/PPM);
		rightTop[2] = new Vector2(575/PPM,320/PPM);
		rightTop[3] = new Vector2(615/PPM,360/PPM);
		vector2s.add(rightTop);

		//top sides

		Vector2[] leftSide = new Vector2[4];
		leftSide[0] = new Vector2(0,340/PPM);
		leftSide[1]= new Vector2(0, 20/PPM);
		leftSide[2] = new Vector2(40/PPM,60/PPM);
		leftSide[3] = new Vector2(40/PPM,300/PPM);
		vector2s.add(leftSide);
		Vector2[] rightSide = new Vector2[4];
		rightSide[0] = new Vector2(640/PPM,340/PPM);
		rightSide[1]= new Vector2(640/PPM, 20/PPM);
		rightSide[2] = new Vector2(600/PPM,60/PPM);
		rightSide[3] = new Vector2(600/PPM,300/PPM);
		vector2s.add(rightSide);

		//side sides

		return vector2s;
	}
}
