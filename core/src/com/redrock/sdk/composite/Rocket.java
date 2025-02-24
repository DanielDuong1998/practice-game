package com.redrock.sdk.composite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import com.redrock.sdk.particle.NParticle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Rocket extends Actor {

  private TextureRegion rgRocket;
  private NParticle     nParticle;

  private Vector2       deltaPos;
  public  boolean       active              = false;

  public Rocket(String key) {
    rgRocket  = Main.asset().getTG(key);
    deltaPos  = new Vector2();

    setSize(rgRocket.getRegionWidth(), rgRocket.getRegionHeight());
    setOrigin(Align.center);
    setScale(0f);
  }

  @Override
  public boolean remove() {
    active = false;

    return super.remove();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

    batch.draw(rgRocket, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
  }

  private void calDeltaPos() {
    Vector2 center  = new Vector2(getWidth()/2, getHeight()/2);
    float   xx      = getWidth()/2;
    float   yy      = getHeight();

    float cos = MathUtils.cosDeg(getRotation());
    float sin = MathUtils.sinDeg(getRotation());

    deltaPos.x = (xx - center.x) * cos - (yy - center.y) * sin + center.x;
    deltaPos.y = (xx - center.x) * sin + (yy - center.y) * cos + center.y;
  }

  public void destroyTarget(Vector2 target, Action action1, Runnable cb) {
    calDeltaPos();
    addAction(
        sequence(
            action1,
            delay(.3f),
            moveTo(target.x - deltaPos.x, target.y - deltaPos.y, .3f),
            run(cb)
        )
    );
  }
}
