package com.redrock.sdk.common;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class CScreenShakeAction extends Action {
  private Group[]     layers;
  private Group       gr;

  private float       curOffX;
  private float       curOffY;
  private float       duration;
  private float       time;
  private float       shakeStrength = 25;

  public static CScreenShakeAction screenShake(float duration, int strength, Group... var1) {
    CScreenShakeAction var3 = Actions.action(CScreenShakeAction.class);

    var3.duration     = duration;
    var3.layers       = new Group[var1.length];
    var3.shakeStrength = strength;

    for(int var2 = 0; var2 < var1.length; ++var2)
      var3.layers[var2] = var1[var2];

    var3.time = 0.0F;
    return var3;
  }

  public static CScreenShakeAction screenShake1(float duration, float strength, Group var1) {
    CScreenShakeAction var3 = Actions.action(CScreenShakeAction.class);

    var3.duration       = duration;
    var3.gr             = var1;
    var3.shakeStrength  = strength;
    var3.time           = 0.0F;

    return var3;
  }

  public boolean act(float var1) {
    if (this.time == 0.0F)
      this.begin();

    if (this.time >= this.duration) {
      this.translateLayer1(-this.curOffX, -this.curOffY);
      return true;
    }
    else {
      float sx = MathUtils.random(-shakeStrength, shakeStrength);
      float sy = MathUtils.random(-shakeStrength, shakeStrength);

      this.translateLayer1(sx - this.curOffX, sy - this.curOffY);

      this.curOffX  = sx;
      this.curOffY  = sy;
      this.time    += var1+var1/2;
      return false;
    }
  }

  public void begin() {
    this.curOffY = 0;
    this.curOffX = 0;
  }

  public void translateLayer(int var1, int var2) {
    Group[] var5 = this.layers;
    int     var4 = var5.length;

    for (Group group : var5)
      group.moveBy((float) var1, (float) var2);
  }

  public void translateLayer1(float var1, float var2) {
    Actor var5 = this.gr;
    var5.moveBy(var1, var2);
  }
}
