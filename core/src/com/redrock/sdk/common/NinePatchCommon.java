package com.redrock.sdk.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.redrock.Main;

public class NinePatchCommon extends Actor {

  private final NinePatch ninePatch;

  public NinePatchCommon(String rgKey, int edge) {
    this(rgKey, edge, edge, edge, edge);
  }

  public NinePatchCommon(String rgKey, int left, int right, int top, int bottom) {
    TextureRegion rg  = Main.asset().getTG(rgKey);
    ninePatch         = new NinePatch(rg, left, right, top, bottom);

    setSize(rg.getRegionWidth(), rg.getRegionHeight());
  }

  @Override
  public float getWidth() {
    return super.getWidth() * super.getScaleX();
  }

  @Override
  public float getHeight() {
    return super.getHeight() * super.getScaleY();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.setColor(getColor());
    ninePatch.draw(
        batch,
        getX(), getY(),
        getOriginX(), getOriginY(),
        getWidth(), getHeight(),
        getScaleX(), getScaleY(),
        getRotation());
  }
}
