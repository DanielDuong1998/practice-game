package com.redrock;

import com.badlogic.gdx.Gdx;

public class RemoteConfig {

  public RemoteConfig() { }

  public String getConfigWheel() {
    return Main.iPlat().getConfigStringValue("WHEEL_DATA", Gdx.files.internal("resource/wheel_data.json").readString());
  }

  public float getTimeLimitAds() {
    return Main.iPlat().getConfigFloatValue("TIME_LIMIT_ADS", 24);
  }

  public int getCountLimitReward() {
    return Main.iPlat().getConfigIntValue("COUNT_LIMIT_REWARD", 5);
  }

  public int getCountLimitShowFullscreen() {
    return Main.iPlat().getConfigIntValue("COUNT_LIMIT_SHOW_FULLSCREEN", 5);
  }
}
