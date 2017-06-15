package com.michaelhighsmith.canyonbunnygame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Owner on 6/13/2017.
 */

public class CanyonBunnyMain implements ApplicationListener {

    private static final String TAG = CanyonBunnyMain.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;

    @Override
    public void create() {
        //Set Libgdx logg level to DEBUG
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //Load assets
        Assets.instance.init(new AssetManager());
        //Initialize controller and renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);

        //Game world is active on start
        paused = false;
    }

    @Override
    public void render() {
        //Don't update the game world when paused
        if(!paused){
            //Update game world by the time that has passed since last rendered frame
            worldController.update(Gdx.graphics.getDeltaTime());
        }

        //Sets the clear screen color to Blue
        Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
        //clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render game world to screen
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        //We hand over the incoming values to the worldRenderer's own resize method
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
        paused = false;
    }

    @Override
    public void dispose() {
        //whenever a dispose event occurs, we pass it to the worldRenderer's dispose
        worldRenderer.dispose();
        Assets.instance.dispose();
    }

}
