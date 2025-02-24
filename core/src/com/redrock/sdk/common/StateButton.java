package com.redrock.sdk.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StateButton extends Actor {

  private boolean       active          = true;
  private TextureRegion rgOn, rgOff;

  public StateButton(TextureRegion rgStateOn, TextureRegion rgStateOff) {
    this.rgOn   = rgStateOn;
    this.rgOff  = rgStateOff;

    setSize(rgStateOn.getRegionWidth(), rgStateOn.getRegionHeight());
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.setColor(getColor());

    if (!active)
      batch.draw(rgOff, getX(), getY(), getOriginX(),  getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    else
      batch.draw(rgOn, getX(), getY(), getOriginX(),  getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x - rgOn.getRegionWidth() / 2f, y - rgOn.getRegionHeight() / 2f);
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }
}
