package com.redrock.sdk.common;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class EffC {

  public static void delay(Actor a, float delay, Runnable cb) {
    a.addAction(
        sequence(
            Actions.delay(delay),
            run(cb)
        )
    );
  }

  public static <T extends Actor> void zoomInAndAlpha(T target, Group gParent, Runnable cb) {
    target.setScale(1.1f);
    target.getColor().a = 0f;
    SequenceAction seq = sequence(
        parallel(
            scaleTo(1f, 1f, .3f),
            alpha(1f, .3f)
        ),
        run(cb)
    );

    gParent.addActor(target);
    target.addAction(seq);
  }

}
