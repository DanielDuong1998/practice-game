package com.redrock.sdk.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SolidC {

  public static Texture create(Color color) {
    Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
    pixmap.setColor(color);
    pixmap.fillRectangle(0, 0, 1, 1);
    Texture texture = new Texture(pixmap);
    pixmap.dispose();

    return texture;
  }

  public static Image createOverlay(float w, float h, float alpha) {
    Image overlay = new Image(SolidC.create(Color.BLACK));
    overlay.setSize(w, h);
    overlay.getColor().a = alpha;
    return overlay;
  }

}
