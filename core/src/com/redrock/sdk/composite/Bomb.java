package com.redrock.sdk.composite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import com.redrock.sdk.particle.NParticle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Bomb extends Actor {

  private TextureRegion rgBomb;
  private NParticle     nParticle;

  public  boolean       active              = false;

  public Bomb(String key) {
    rgBomb = Main.asset().getTG(key);

    setSize(rgBomb.getRegionWidth(), rgBomb.getRegionHeight());
    setOrigin(Align.center);
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

    batch.draw(rgBomb, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
  }

  public void destroyTarget(Vector2 target, float delay, Runnable cb) {
    addAction(
        sequence(
            delay(delay),
            moveTo(target.x - getWidth()/2, target.y - getHeight()/2, .3f),
            run(cb)
        )
    );
  }

}
