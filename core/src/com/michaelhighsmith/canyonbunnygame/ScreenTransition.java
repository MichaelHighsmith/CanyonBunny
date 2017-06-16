package com.michaelhighsmith.canyonbunnygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Owner on 6/15/2017.
 */

public interface ScreenTransition {

    public float getDuration();

    public void render(SpriteBatch batch, Texture currScreen, Texture nextScreen, float alpha);

}
