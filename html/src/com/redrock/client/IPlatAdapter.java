package com.redrock.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.redrock.sdk.AdsClose;
import com.redrock.sdk.IPlat;

public class IPlatAdapter implements IPlat {
  boolean isCanvasSetup = false;
  HtmlLauncher launcher;

  public IPlatAdapter(HtmlLauncher launcher) {
    this.launcher = launcher;
  }

  @Override
  public void log(String str) {
    GwtApplication.consoleLog(str);
  }

  @Override
  public void showVideoReward(AdsClose callback) {
    FBInstant.ShowVideoReward(callback);
  }

  @Override
  public void showFullscreen() {

  }

  @Override
  public void showFullscreen(AdsClose onShow, AdsClose onDone) {
    FBInstant.ShowFullscreen(onShow, onDone);
  }

  @Override
  public String getDefaultLanguage() {
    return "en";
  }

  @Override
  public String getConfigStringValue(String name, String defaultValue) {
    return defaultValue;
  }

  @Override
  public String getMD5PathFile(String filename) {
    Preloader preloader = ((GwtApplication) Gdx.app).getPreloader();
    return preloader.assetNames.get(filename);
  }

  @Override
  public int getConfigIntValue(String name, int defaultValue) {
    return defaultValue;
  }

  @Override
  public float getConfigFloatValue(String name, float defaultValue) {
    return defaultValue;
  }

  @Override
  public boolean isVideoRewardReady() {
    return FBInstant.IsVideoRewardReady();
  }

  @Override
  public boolean isFullscreenReady() {
    return FBInstant.IsFullscreenReady();
  }

  @Override
  public void showFullscreen(Runnable callback) {
  }


  @Override
  public void showBanner(boolean visible) {

  }

  @Override
  public void crashKey(String key, String value) {

  }

  @Override
  public void crashLog(String log) {
    GwtApplication.consoleLog(log);
  }

  @Override
  public void openStore() {

  }

  @Override
  public void trackScreen(String screen) {

  }

  @Override
  public void trackCustomEvent(String event) {

  }

  @Override
  public void trackLevelInfo(String event, int mode, int level) {

  }

  @Override
  public void trackLevelCompleted(int level) {

  }

  @Override
  public void trackPlayerInfo(String event, int mode, int level) {

  }

  @Override
  public void trackPlaneInfo(String event, int planeid, int level) {

  }

  @Override
  public void trackVideoReward(String type) {

  }

  @Override
  public void trackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss) {

  }

  @Override
  public int getNotifyId() {
    return 0;
  }

  @Override
  public void setDailyNotification(int id, String header, String content, int days, int hours) {

  }

  @Override
  public void cancelDailyNotification(int id) {

  }

  @Override
  public void restart() {

  }

  @Override
  public void quitGame() {

  }

  @Override
  public void onShow() {
    if(!isCanvasSetup){
      launcher.setUpCanvas();
      isCanvasSetup = true;
    }
  }
}
