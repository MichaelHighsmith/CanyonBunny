package com.michaelhighsmith.canyonbunnygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Owner on 6/13/2017.
 */

public class WorldRenderer implements Disposable{

    //We implement Disposable so that we properly dispose batches that are no longer needed
    //In this case, we call the WorldRenderer class' dispose() which in turn will call the SpriteBatch class' dispose()

    private OrthographicCamera cameraGUI;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;

    //constructor requires a WorldController so that it can render all the objects that are managed by the controller
    public WorldRenderer (WorldController worldController){
        //Allows us to access the game objects managed by the controller
        this.worldController = worldController;
        init();
    }
    private void init(){
        //Create a new SpriteBatch object which we will use for all our rendering tasks
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);  //flip y-axis
        cameraGUI.update();
    }

    public void render(){
        renderWorld(batch);
        renderGui(batch);
    }

    //Called when the screen size is changed (including the start of the program)
    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = Constants.VIEWPORT_GUI_WIDTH;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override public void dispose() {
        batch.dispose();
    }

    private void renderWorld(SpriteBatch batch){
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    private void renderGuiScore (SpriteBatch batch){
        float x = -15;
        float y = -15;
        batch.draw(Assets.instance.goldCoin.goldCoin, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
        Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
    }

    private void renderGuiExtraLive(SpriteBatch batch){
        float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
        float y = -15;
        for (int i = 0; i < Constants.LIVES_START; i++){
            if(worldController.lives <= i)
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            batch.draw(Assets.instance.bunny.head, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
            batch.setColor(1,1,1,1);
        }
    }

    private void renderGuiFpsCounter (SpriteBatch batch){
        float x = cameraGUI.viewportWidth - 55;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if(fps >= 45){
            //45 or more FPS show up in green
            fpsFont.setColor(0,1,0,1);
        } else if (fps >= 30){
            fpsFont.setColor(1,1,0,1);
        } else {
            //less than 30 FPS show up in red
            fpsFont.setColor(1,0,0,1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1,1,1,1); //white
    }

    private void renderGui(SpriteBatch batch){
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        //draw collected gold coins icon + text
        //(anchored to top left edge)
        renderGuiScore(batch);
        //draw collected feather icon (anchored to top left edge)
        renderGuiFeatherPowerup(batch);
        //draw extra lives icon + text (anchored to top right edge)
        renderGuiExtraLive(batch);
        //draw FPS text (anchored to the bottom right edge
        renderGuiFpsCounter(batch);
        //draw game over text
        renderGuiGameOverMessage(batch);
        batch.end();
    }

    private void renderGuiGameOverMessage(SpriteBatch batch){
        float x  = cameraGUI.viewportWidth / 2;
        float y = cameraGUI.viewportHeight / 2;
        if (worldController.isGameOver()){
            BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            fontGameOver.draw(batch, "GAME OVER", x, y, 0, Align.center, true);
            fontGameOver.setColor(1,1,1,1);
        }
    }

    private void renderGuiFeatherPowerup (SpriteBatch batch){
        float x = -15;
        float y = 30;
        float timeLeftFeatherPowerup = worldController.level.bunnyHead.timeLeftFeatherPowerup;
        if(timeLeftFeatherPowerup > 0){
            //Start icon fade in/out if the left power-up time is less than 4 seconds.
            //The fade interval is set to 5 changes per second
            if(timeLeftFeatherPowerup < 4){
                if(((int)(timeLeftFeatherPowerup * 5) % 2) != 0){
                    batch.setColor(1,1,1,0.5f);
                }
            }
            batch.draw(Assets.instance.feather.feather, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
            batch.setColor(1,1,1,1);
            Assets.instance.fonts.defaultSmall.draw(batch, "" + (int)timeLeftFeatherPowerup, x + 60, y + 57);
        }
    }

}
