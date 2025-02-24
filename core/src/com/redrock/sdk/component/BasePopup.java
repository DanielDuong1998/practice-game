package com.redrock.sdk.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.redrock.Main;
import com.redrock.sdk.common.SolidC;

public class BasePopup extends GenericModule {

  private final static float ALPHA_OVERLAY = .7f;

  private String soundID = "";

  public Image overlay;
  public Image icClose;

  public Runnable cbPopupHided;

  public BasePopup(TextureRegion rgBtnClose) {
    overlay = SolidC.createOverlay(Main.getStage().getWidth(), Main.getStage().getHeight(), ALPHA_OVERLAY);

    if (rgBtnClose != null)
      icClose = new Image(rgBtnClose);

    setBtnCloseClick();
  }

  private void setBtnCloseClick() {
    if (icClose == null)
      return;

    icClose.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        if (!soundID.equals(""))
          Main.soundMgr().playSound(soundID);

        hidePopup();
      }
    });
  }

  public void setOverlayClick() {
    overlay.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        hidePopup();
      }
    });
  }

  public void setSoundID(String soundID) {
    this.soundID = soundID;
  }

  //if popup is hiding, prevent all touch events
  public boolean checkSizeActions() {
    return getActions().size > 0;
  }
}
