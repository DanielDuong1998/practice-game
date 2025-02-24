package com.redrock.manager;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.redrock.sdk.localize.Language;

import java.util.ArrayList;
import java.util.List;

public class MultiLangMgr {

  private Language    language;
  private List<Label> lbs;

  public MultiLangMgr() {
    language  = new Language();
    lbs       = new ArrayList<>();
  }

  private void changeLanguageNoneParam(Label lbChange, String localize) {
    lbChange.setText(getText(lbChange.getName(), localize));
  }

  /**
   * @param lbChange
   * @param localize
   * @param keyExtra: {key language in language.txt file}.{param1}.{param2}.{paramN}
   */
  private void changeLanguageWithParams(Label lbChange, String localize, String[] keyExtra) {
    int[] params = new int[keyExtra.length - 1]; // just only get param remove `key language` in keySplit.

    for (int i = 1; i < keyExtra.length; i++)
      params[i - 1] = Integer.parseInt(keyExtra[i]);

    lbChange.setText(getText(keyExtra[0], localize, params));
  }

  public void add(Label lb) {
    lbs.add(lb);
  }

  public void changeLanguage(String localize) {
    for (Label lbChange : lbs) {
      String[] keyExtra = lbChange.getName().split("\\.");

      if (keyExtra.length <= 0)
        changeLanguageNoneParam(lbChange, localize);
      else
        changeLanguageWithParams(lbChange, localize, keyExtra);
    }
  }

  public String getText(String key, String localize, int... params) {
    return language.getText(key, localize, params);
  }

  public void makeKey(Label lb, String key, int ...params) {
    StringBuilder keyBuilder = new StringBuilder(key);

    for (int param : params)
      keyBuilder.append(".").append(param);

    lb.setName(keyBuilder.toString());
    lbs.add(lb);
  }
}
