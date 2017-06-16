package com.michaelhighsmith.canyonbunnygame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;

/**
 * Created by Owner on 6/13/2017.
 */

public class CanyonBunnyMain extends DirectedGame {

    @Override
    public void create(){
        //Set Libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //Load assets
        Assets.instance.init(new AssetManager());
        //Start game at menu screen
        ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
        setScreen(new MenuScreen(this), transition);
    }

}
