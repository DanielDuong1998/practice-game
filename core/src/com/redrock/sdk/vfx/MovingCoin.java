package com.redrock.sdk.vfx;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;

public class MovingCoin {

  public static void move(int nCoin, float sx, float sy, float ex, float ey, Runnable afterEff) {
    Vector2 src       = new Vector2(sx, sy);
    Vector2 dest      = new Vector2(ex, ey);

    float time;
    float duration    = 0;
    float scl         = 1;

    float maxLen      = 0;
    float maxDur      = 0;

    for (int i = 0; i < nCoin; i++) {
      Image sprite    = new Image(Main.asset().getTG("ic_coin"));
      float dx        = MathUtils.random(-60*scl, 60*scl);
      float dy        = MathUtils.random(-60*scl, 60*scl);
      float len       = src.dst(src.cpy().set(src.x + dx, src.y + dy));

      time            = len/500;
      duration        = src.dst(dest) / 1300;

      if (len > maxLen)
        maxLen = len;

      if (duration > maxDur)
        maxDur = duration;

      sprite.setPosition(src.x, src.y);
      sprite.setOrigin(Align.center);
      sprite.setScale(scl);

      final int index = i;
      sprite.addAction(Actions.sequence(
          Actions.moveTo(src.x + dx, src.y + dy, time),
          Actions.delay(time),
          Actions.moveTo(dest.x, dest.y, duration, Interpolation.swingIn),
          Actions.run(() -> {
            sprite.remove();

            if (index == nCoin-1) {
              //TODO: play sound in here
            }

          })
      ));
      Main.getStage().addActor(sprite);
    }

    Main.getStage().addAction(
        Actions.delay(maxDur + maxLen/200 + 0.5f,
            Actions.run(() -> {
              if (afterEff != null)
                afterEff.run();
            })
        ));

    Main.getStage().addAction(
        Actions.sequence(
            Actions.delay(duration + maxLen/200),
            Actions.run(() -> {}))
        );
  }

}
