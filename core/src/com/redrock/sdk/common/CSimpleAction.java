package com.redrock.sdk.common;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class CSimpleAction extends Action {

  private IAct iAct;

  public static CSimpleAction add(IAct iAct) {
    CSimpleAction instance = Actions.action(CSimpleAction.class);
    instance.iAct = iAct;
    return instance;
  }

  @Override
  public boolean act(float delta) {
    return iAct.act(delta, this.actor);
  }

  public interface IAct {
    boolean act(float duration, Actor a);
  }
}
