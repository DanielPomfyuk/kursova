package com.pool.game.Handlers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.pool.game.model.Ball;

import static com.pool.game.Handlers.B2DVars.PPM;

public class InputHandler implements InputProcessor {
    private static float xMouseCoord;
    private static float yMouseCoord;
    private static long pressedTime;
    private final int MSECS_PER_N=10;

    public Body sharik;

    public static float GetXMouseCoord(){
        return xMouseCoord;
    }

    public static float GetYMouseCoord(){
        return yMouseCoord;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pressedTime = System.currentTimeMillis();

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        long power = (System.currentTimeMillis()-pressedTime)/MSECS_PER_N;
        UiebatiSharik((float)power,(int)xMouseCoord, (int)yMouseCoord);
        pressedTime = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        InputHandler.xMouseCoord = screenX/PPM/2;
        InputHandler.yMouseCoord = 360/PPM-screenY/PPM/2;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void UiebatiSharik(float power, int coordX, int coordY){
        float ballXCoord = sharik.getPosition().x;
        float ballYCoord = sharik.getPosition().y;
        float oldVectorX =  ballXCoord-coordX;
        float oldVectorY =  ballYCoord-coordY;
        Vector2 impulsVector = generateimpulsVector(power, oldVectorX, oldVectorY);
        sharik.applyLinearImpulse(impulsVector, sharik.getPosition(), true);
    }
    private Vector2 generateimpulsVector(float power, float x, float y){
        double coef = power/Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        return new Vector2((float)coef*x,(float)coef*y);
    }
}
