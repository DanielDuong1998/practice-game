package com.redrock;

import com.redrock.sdk.IPlat;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.redrock.Main;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();

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
            public void showVideoReward(OnVideoRewardClosed callback) {

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
        };

        return new IOSApplication(new Main(720, 1280, 2160, 3840, iPlat), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
