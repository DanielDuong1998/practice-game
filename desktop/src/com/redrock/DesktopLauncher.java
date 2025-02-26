package com.redrock;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.redrock.Main;
import com.redrock.practice.GameClient;
import com.redrock.practice.MyGame;
import com.redrock.sdk.AdsClose;
import com.redrock.sdk.IPlat;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
//		config.setWindowedMode((int) (720 * .75f), (int) (1280 * .75f));
		config.setWindowedMode((int) (1280 * .75f), (int) (720 * .75f));
//		config.setWindowedMode((int) (2868 * .5f), (int) (1320 * .5f));
//		config.setWindowedMode((int) (2420 * .5f), (int) (1668 * .5f)); // pad
//		config.setWindowedMode((int) (2960 * .4f), (int) (1848 * .4f)); // pad
		config.setWindowPosition(300, 40);
		config.setTitle("RedRockTemplate");

		IPlat iPlat = new IPlat() {
			@Override
			public String getDefaultLanguage() {
				return "EN";
			}

			@Override
			public String getConfigStringValue(String name, String defaultValue) {
				return defaultValue;
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
				return false;
			}

			@Override
			public boolean isFullscreenReady() {
				return false;
			}

			@Override
			public void showVideoReward(AdsClose callback) {

			}

			@Override
			public void showFullscreen(AdsClose onShow, AdsClose onDone) {

			}

			@Override
			public void showFullscreen() {

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
			public String getMD5PathFile(String filename) {
				return filename;
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
			public void log(String msg) {

			}

			@Override
			public void onShow() {

			}
		};

//		new Lwjgl3Application(new Main(720, 1280, 2160, 3840, iPlat), config);
		new Lwjgl3Application(new Main(1280, 720, 3840, 2160, iPlat), config);
//		new Lwjgl3Application(new GameClient(), config);
	}
}
