package com.redrock.sdk.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ParticleEff extends Actor {

  private ParticleEffectPool                pep;
  public  ParticleEffect                    pe;
  private ParticleEffectPool.PooledEffect   poolEffect;
  public  boolean                           isActive = false;

  public ParticleEff(ParticleEffectPool pep, ParticleEffect pe) {
    this.pep = pep;
    this.pe = pe;
  }

  @Override
  public boolean remove() {
    if (poolEffect != null)
      poolEffect.free();

    isActive = false;

    return super.remove();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    pe.setPosition(getX(), getY());
    pe.update(delta);

    if (pe.isComplete())
      remove();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    pe.draw(batch);
  }

  public void start(Group gParent, float x, float y, float scl) {
    isActive = true;

    pe.reset();
    poolEffect = pep.obtain();

    setX(x);
    setY(y);
    pe.scaleEffect(scl);
    gParent.addActor(this);
  }

  public void start(Stage gParent, float x, float y, float scl) {
    isActive = true;

    pe.reset();
    poolEffect = pep.obtain();

    setX(x);
    setY(y);
    pe.scaleEffect(scl);
    gParent.addActor(this);
  }

  public void start(Group gParent, float x, float y, float sclX, float sclY) {
    isActive = true;

    pe.reset();
    poolEffect = pep.obtain();

    setX(x);
    setY(y);

    pe.scaleEffect(sclX, sclY, 0f);
    gParent.addActor(this);
  }

  public void start(Group gParent, float x, float y, float scl, int zIndex) {
    isActive = true;

    pe.reset();
    poolEffect = pep.obtain();

    setX(x);
    setY(y);

    pe.scaleEffect(scl);
    gParent.addActor(this);

    this.setZIndex(zIndex);
  }
}
