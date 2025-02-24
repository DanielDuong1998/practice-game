package com.redrock.sdk.component;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.redrock.sdk.modules.generic.GenericMessage;

public class GenericModule extends Group {

  public String id;

  public GenericModule() {}

  public void handleMsg(GenericMessage msg) { }

  public void showPopup() {}

  public void hidePopup() {}
}