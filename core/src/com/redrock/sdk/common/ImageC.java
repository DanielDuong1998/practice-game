package com.redrock.sdk.common;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonValue;
import com.redrock.Main;

public class ImageC {

  public static Image of(JsonValue js) {
    boolean isCenterX = js.get("center_x").asBoolean();
    boolean isCenterY = js.get("center_y").asBoolean();

    Image img = new Image(Main.asset().getTG(js.get("region").asString()));
    if (isCenterX && isCenterY) {

    }

    return img;
  }

}
