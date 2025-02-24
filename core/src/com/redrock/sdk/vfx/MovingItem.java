package com.redrock.sdk.vfx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;

public class MovingItem {

  public static void move(String item, Vector2 src, Vector2 dest, Runnable afterEff) {
    Image ic = new Image(Main.asset().getTG(item));

    ic.setPosition(src.x, src.y, Align.center);
    ic.setOrigin(Align.center);
    Main.getStage().addActor(ic);

    ic.addAction(Actions.sequence(
            Actions.moveTo(dest.x, dest.y,0.5f),
            Actions.run(() -> {
              Main.getStage().addAction(Actions.run(afterEff));

              ic.clear();
              ic.remove();
            })
    ));
  }

}
