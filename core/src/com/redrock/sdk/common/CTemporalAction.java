package com.redrock.sdk.common;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class CTemporalAction extends TemporalAction {
  private IAct  iAct;
  private Actor actor;

  public static CTemporalAction add(float duration, IAct iAct) {
    CTemporalAction instance = Actions.action(CTemporalAction.class);
    instance.setDuration(duration);
    instance.iAct = iAct;
    return instance;
  }

  @Override
  protected void begin() {
    super.begin();

    actor = super.getActor();
  }

  @Override
  protected void update(float percent) {
    iAct.act(percent, actor);
  }

  public interface IAct {
    void act(double percent, Actor a);
  }
}
