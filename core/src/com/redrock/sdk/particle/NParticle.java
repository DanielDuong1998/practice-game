package com.redrock.sdk.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.redrock.Main;

public class NParticle {

  private ParticleEff particle;

  public NParticle(String particleName, String atlasKey) {
    ParticleEffect pe = new ParticleEffect();
    pe.load(Gdx.files.internal("particle/" + particleName), Main.asset().getAl(atlasKey));

    ParticleEffectPool pep = new ParticleEffectPool(pe, 0, 100);
    particle               = new ParticleEff(pep, pe);
  }

  public void setPosition(float x, float y) {
    particle.setPosition(x, y);
  }

  public void start(Group gParent, float x, float y, float sclEffect, int zIndex) {
    particle.start(gParent, x, y, sclEffect, zIndex);
  }

  public void start(Group gParent, float x, float y, float sclEffect) {
    particle.start(gParent, x, y, sclEffect);
  }

  public void start(Stage gParent, float x, float y, float sclEffect) {
    particle.start(gParent, x, y, sclEffect);
  }

  public void start(Group gParent, float x, float y, float sclEffect, float delay) {
    gParent.addAction(
        Actions.sequence(
            Actions.delay(delay),
            Actions.run(() -> particle.start(gParent, x, y, sclEffect))
        )
    );
  }

  public NParticle setSpriteAt(int idEmitter, String region, TextureAtlas atlas) {
    Sprite sprite = new Sprite(atlas.findRegion(region));
    sprite.setScale(.7f);
    particle.pe
        .getEmitters()
        .get(idEmitter)
        .getSprites()
        .get(0)
        .set(sprite);

    return this;
  }

  public void setColor(int idEmitter, Color color) {
    ParticleEmitter emitter = particle.pe.getEmitters().get(idEmitter);
    emitter.getTint().setColors(new float[]{color.r, color.g, color.b});
  }

  public NParticle setTransparency(int idEmitter) {
    ParticleEmitter emitter = particle.pe.getEmitters().get(idEmitter);
    emitter.getTransparency().setHigh(0);
    return this;
  }

  public void setColor(int idEmitter, int idColor, float color) {
    ParticleEmitter emitter = particle.pe.getEmitters().get(idEmitter);
    emitter.getTint().getColors()[idColor] = color;
  }

  public void setColorAll(Color color) {
    for (int i=0; i<particle.pe.getEmitters().size; i++)
      setColor(i, color);
  }

  public ParticleEff getPe() {
    return particle;
  }

  public boolean isActive() {
    return particle.isActive;
  }

  public void remove() {
    particle.remove();
  }

  public void setActive(boolean isActive) {
    particle.isActive = isActive;
  }


}
