package com.redrock.sdk.localize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class Language {

  private HashMap<String, HashMap<String, String>> hmLanguages;

  public Language() {
    load();
  }

  public void load() {
    FileHandle file = Gdx.files.internal("resource/language.txt");
    if (!file.exists())
      return;

    String[] contents = file.readString().split("\n");
    String[] keys     = contents[0].split(";");

    hmLanguages = new HashMap<>();
    for (int i = 1; i < contents.length; i++) {
      String[] data                     = contents[i].split(";");
      HashMap<String, String> values  = new HashMap<>();

      for (int j = 0; j < keys.length; j++)
        values.put(keys[j], data[j + 1]);

      //data[0] -> localize. ex (VN, EN,...)
      hmLanguages.put(data[0], values);
    }
  }

  /**
   * @param key key of text need to change language
   * ex: key = "ads_coin.20" -> extraKeys = [ads_coin, 20]
   *            extraKeys[0] -> id language
   *            extraKeys[1,...] -> value to replace value of id language (ads_coin = "you will get {0}", replace {0} to corresponding value in extraKeys)
   * @return text to change corresponding language
   */
  public String getText(String key, String localize) {
    String[] extraKey = key.split(".");

    HashMap<String, String> hmValueLanguage = hmLanguages.get(localize);
    String                  value           = hmValueLanguage.get(extraKey[0]);

    String newValueChange = value;
    for (int i = 1; i < extraKey.length; i++) {
      newValueChange = value.replace("{" + (i - 1) + "}", extraKey[i]);

      value = newValueChange;
    }

    return newValueChange.replace("\\n", "\n").replace("\\t", "\t");
  }

  public String getText(String key, String localize, int... parameters) {
    HashMap<String, String> hmValueLanguage = hmLanguages.get(localize.toUpperCase());

    if (!hmValueLanguage.containsKey(key))
      return "???";

    String text = hmValueLanguage.get(key);

    String newTextChange = text;
    for (int i = 0; i < parameters.length; i++) {
      newTextChange = text.replace("{" + i + "}", parameters[i] + "");

      text = newTextChange;
    }

    return newTextChange.replace("\\n", "\n").replace("\\t", "\t");
  }
}
