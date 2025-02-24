package com.redrock.sdk.common;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public class SymmetryImg {
  public Image left, right;

  public SymmetryImg(TextureRegion region) {
    left = new Image(region);
    left.setOrigin(Align.center);

    right = new Image(region);
    right.setOrigin(Align.center);

    right.setScaleX(-1);
  }

  public SymmetryImg(TextureRegion region, float sclX, float sclY) {
    left = new Image(region);
    left.setSize(left.getWidth() * sclX, left.getHeight() * sclY);
    left.setOrigin(Align.center);

    right = new Image(region);
    right.setSize(right.getWidth() * sclX, right.getHeight() * sclY);
    right.setOrigin(Align.center);

    right.setScaleX(-1);
  }

  public void addTo(Group group) {
    group.addActor(left);
    group.addActor(right);
  }

  public void setPos(float x, float y) {
    left.setPosition(x - left.getWidth(), y);
    right.setPosition(left.getX() + left.getWidth(), left.getY());
  }

  public float getW() { return left.getWidth() * 2; }

  public float getH() { return left.getHeight(); }

  public float getX() { return left.getX(); }

  public float getY() { return left.getY(); }
}
