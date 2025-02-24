package com.redrock.sdk.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import ze.spineactor.esotericsoftware.spine.*;

public class SpineVfx extends Actor {
  private final Skeleton          skeleton;
  private final AnimationState    state;
  private final SkeletonRenderer  renderer;

  public SpineVfx(String spineKey) {
    renderer = new SkeletonRenderer();
    renderer.setPremultipliedAlpha(false);

    SkeletonJson json             = new SkeletonJson(Main.asset().getAl(spineKey));
    SkeletonData skeletonData     = json.readSkeletonData(Gdx.files.internal("spines/" + spineKey + ".json"));
    skeleton                      = new Skeleton(skeletonData);
    AnimationStateData stateData  = new AnimationStateData(skeletonData);
    state                         = new AnimationState(stateData);

    setSize(skeleton.getData().getWidth(), skeleton.getData().getHeight());
    setOrigin(Align.center);
    setPosition(0, 0);
  }

  @Override
  public boolean remove() {
    state.clearTracks();
    skeleton.setToSetupPose();

    return super.remove();
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    state.update(delta);
    state.apply(skeleton);
    skeleton.updateWorldTransform();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

    int srcFunc = batch.getBlendSrcFunc();
    int dstFunc = batch.getBlendDstFunc();

    renderer.draw(batch, skeleton);

    batch.setBlendFunction(srcFunc, dstFunc);
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);

    skeleton.setPosition(x + getWidthSpine() / 2f, y + getHeightSpine() / 2f);
  }

  @Override
  public void setX(float x) {
    super.setX(x);

    skeleton.setX(x + getWidthSpine() / 2f);

  }

  @Override
  public void setY(float y) {
    super.setY(y);

    skeleton.setY( y + getHeightSpine() / 2f);
  }

  @Override
  public void setPosition(float x, float y, int alignment) {
    super.setPosition(x, y, alignment);

    skeleton.setPosition(x, y);
  }

  @Override
  public void setScale(float scaleXY) {
    super.setScale(scaleXY);

    skeleton.getRootBone().setScale(scaleXY);
  }

  @Override
  public void setScale(float scaleX, float scaleY) {
    super.setScale(scaleX, scaleY);

    skeleton.getRootBone().setScale(scaleX, scaleY);
  }

  @Override
  public void setRotation(float degrees) {
    super.setRotation(degrees);

    skeleton.getRootBone().setRotation(degrees);
  }

  public void setTimeScl(float timeScl) {
    state.setTimeScale(timeScl);
  }

  public void startAnim(String anim, float timeScl, boolean loop) {
    state.setTimeScale(timeScl);

    if (state.getData().getSkeletonData().findAnimation(anim) != null)
      state.setAnimation(0, anim, loop);
    else
      Gdx.app.error("SPINE", "Spine key animation \"" + anim + "\" is not exist!");
  }

  public void startAnim(String anim, float timeScl, float cbDelayed, Runnable cb) {
    startAnim(anim, timeScl, false);
    clearActions();
    addAction(
        Actions.delay(
            cbDelayed,
            Actions.run(() -> {
              if (cb != null)
                cb.run();
            })
        )
    );
  }

  public float getWidthSpine() {
    return skeleton.getData().getWidth();
  }

  public float getHeightSpine() {
    return skeleton.getData().getHeight();
  }
}
