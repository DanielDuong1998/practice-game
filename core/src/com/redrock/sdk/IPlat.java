package com.redrock.sdk;

public interface IPlat {
  String getDefaultLanguage();
  String getConfigStringValue(String name, String defaultValue);

  int getConfigIntValue(String name, int defaultValue);
  float getConfigFloatValue(String name, float defaultValue);

  boolean isVideoRewardReady();
  boolean isFullscreenReady();

  void showVideoReward(AdsClose callback);
  void showFullscreen(AdsClose onShow, AdsClose onDone);
  void showFullscreen();
  void showFullscreen(Runnable callback);
  void showBanner(boolean visible);
  void crashKey(String key, String value);
  void crashLog(String log);
  void openStore();
  void trackScreen(String screen);
  void trackCustomEvent(String event);
  void trackLevelInfo(String event, int mode, int level);
  void trackLevelCompleted(int level);
  void trackPlayerInfo(String event, int mode, int level);
  void trackPlaneInfo(String event, int planeid, int level);
  void trackVideoReward(String type);
  void trackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss);
  int getNotifyId();
  void setDailyNotification(int id, String header, String content, int days, int hours);
  void cancelDailyNotification(int id);
  void restart();
  void quitGame();
  void log(String msg);
  void onShow();
  String getMD5PathFile(String filename);
}
