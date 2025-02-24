package com.redrock.sdk.wheel;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import com.redrock.manager.SoundMgr;
import com.redrock.sdk.common.SolidC;
import com.redrock.sdk.component.GenericModule;
import com.redrock.sdk.modules.generic.GenericMessage;

public class WheelModule extends GenericModule implements Wheel.IListener {

  private Wheel wheel;

  private Image overlay;
  private Image btnBack;

  public WheelModule() {
    setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
    setOrigin(Align.center);

    overlay = SolidC.createOverlay(Main.getStage().getWidth(), Main.getStage().getHeight(), .9f);

    wheel = new Wheel(Main.remoteConfig().getConfigWheel());
    wheel.setPosition(getWidth() / 2, getHeight() / 2 - 50, Align.center);
    wheel.setIListener(this);
    addActor(wheel);

    btnBack = new Image(Main.asset().getTG("icon_back"));
    btnBack.setPosition(20, getHeight() - btnBack.getHeight() - 20);
    btnBack.setOrigin(Align.center);
    addActor(btnBack);
    setBtnBackClickEvent();

    registerMsg();
  }

  private void setBtnBackClickEvent() {
    btnBack.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        Main.soundMgr().playSound(SoundMgr.BTN_CLICK);

        btnBack.setTouchable(Touchable.disabled);
        btnBack.setTouchable(Touchable.enabled);
      }
    });
  }

  private void registerMsg() {}

  private boolean canSpin() {
    return Main.session().profile.spinCount > 0;
  }

  private void handleShowWheelMsg(WheelMsg.WheelShowMsg msg) {
    setScale(1f);
    getColor().a = 1f;

    Main.getStage().addActor(overlay);
    Main.getStage().addActor(this);
  }

  //-------------------------------------------------------EFFECT-------------------------------------------------------
  @Override
  public void handleMsg(GenericMessage msg) {
    super.handleMsg(msg);

  }

  @Override
  public void comeOut(int prize, String textPrize) {

  }
}
