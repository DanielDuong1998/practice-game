package com.redrock.sdk.localize;

import com.redrock.sdk.modules.generic.GenericMessage;

public class ChangeLanguageMsg extends GenericMessage {

  public String localize = "EN";

  public ChangeLanguageMsg(String name) {
    super();
  }

  @Override
  public void reset() {
    localize = "EN";
  }
}
