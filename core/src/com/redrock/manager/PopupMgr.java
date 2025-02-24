package com.redrock.manager;

import com.redrock.sdk.component.GenericModule;

import java.util.HashMap;

public class PopupMgr {

  public final static String ERROR_NETWORK = "error_network";

  private HashMap<String, GenericModule>  hmPopup;

  public PopupMgr() {
    this.hmPopup = new HashMap<>();

    init();
  }

  private void init() {}

  public void show(String key) {
    if (hmPopup.get(key) == null)
      return;

    hmPopup.get(key).showPopup();
  }

  public GenericModule getPopup(String key) {
    return hmPopup.get(key);
  }
}
