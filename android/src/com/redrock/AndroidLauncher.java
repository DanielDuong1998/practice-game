package com.redrock;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.redrock.sdk.AdsClose;
import com.redrock.sdk.IPlat;
import com.redrock.template.R;

public class AndroidLauncher extends AndroidApplication {
	private FrameLayout 					rootView;
	private FirebaseAnalytics 		mFireBaseAnalytics;
	private FirebaseRemoteConfig 	mFireBaseRemoteConfig;

	private GoogleAds googleAds = new GoogleAds();
	private Main main;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// use the full display, even if we have a device with a notch
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			Window applicationWindow = getApplicationWindow();
			WindowManager.LayoutParams attrib = applicationWindow.getAttributes();
			attrib.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
		}

		IPlat iPlat = new IPlat() {
			@Override
			public String getDefaultLanguage() {
				return "EN";
			}

			@Override
			public String getConfigStringValue(String name, String defaultValue) {
				try {
					String v = mFireBaseRemoteConfig.getString(name);
					Log.i("Remote Config", "name: " + name + "\tvalue: " + v);

					if (v.equals(""))
						return defaultValue;

					return v;
				}
				catch (Exception e) {
					return defaultValue;
				}
			}

			@Override
			public int getConfigIntValue(String name, int defaultValue) {
				try {
					String v = getConfigStringValue(name, "");
					if (v.equals(""))
						return defaultValue;

					return Integer.parseInt(v);
				}
				catch (Exception e) {
					return defaultValue;
				}
			}

			@Override
			public float getConfigFloatValue(String name, float defaultValue) {
				try {
					String v = getConfigStringValue(name, "");
					if (v.equals(""))
						return defaultValue;

					return Float.parseFloat(v);
				}
				catch (Exception e) {
					return defaultValue;
				}
			}

			@Override
			public boolean isVideoRewardReady() {
				return googleAds.isVideoRewardReady();
			}

			@Override
			public boolean isFullscreenReady() {
				return googleAds.isFullscreenReady();
			}

			@Override
			public void showVideoReward(AdsClose cb) {
				googleAds.showVideoReward(cb);
			}

			@Override
			public void showFullscreen(AdsClose onShow, AdsClose onDone) {

			}

			@Override
			public void showFullscreen() {
				googleAds.showFullscreen();
			}

			@Override
			public void showFullscreen(Runnable callback) {

			}

			@Override
			public void showBanner(boolean visible) {
				googleAds.showBanner(visible);
				googleAds.bannerVisible = visible;

				System.out.println("BANNER :" + visible);
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
				runOnUiThread(() -> {
					try {
						mFireBaseAnalytics.logEvent(screen, new Bundle());
					}
					catch (Exception ignored) {

					}
				});
			}

			@Override
			public void trackCustomEvent(String event) {
				try {
					mFireBaseAnalytics.logEvent(event, new Bundle());
				}
				catch (Exception ignored) {

				}
			}

			@Override
			public void trackLevelInfo(String event, int mode, int level) {
				try {
					mFireBaseAnalytics.logEvent(event + "_" + mode + "_" + level, new Bundle());
				}
				catch (Exception ignored) {

				}
			}

			@Override
			public void trackLevelCompleted(int level) {

			}

			@Override
			public void trackPlayerInfo(String event, int mode, int level) {
//				try {
//					Bundle bundle = new Bundle();
//					bundle.putInt("mode", mode);
//					bundle.putInt("difficult", difficult);
//					bundle.putInt("level", level);
//          mFireBaseAnalytics.logEvent(event, bundle);
//				}
//				catch (Exception ignored) {
//
//				}
			}

			@Override
			public void trackPlaneInfo(String event, int planeid, int level) {

			}

			@Override
			public void trackVideoReward(String type) {
				try {
					Bundle bundle = new Bundle();
					bundle.putString("type", type);
					mFireBaseAnalytics.logEvent("video_reward", bundle);
				}
				catch (Exception ignored) {

				}
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
				finishAffinity();
				finish();
			}

			@Override
			public void log(String msg) {

			}

			@Override
			public void onShow() {

			}
		};

//		main					= new Main(720, 1280, 2160, 3840, iPlat);
		main					= new Main(1280, 720, 3840, 2160, iPlat);
		View gameView = initializeForView(main, config);
		rootView 			= new FrameLayout(this);
		rootView.addView(gameView);
		setContentView(rootView);

		googleAds.initAd(this, iPlat, rootView);
		initRemoteConfig();
		initGoogleAnalytics();
	}

	private void initGoogleAnalytics() {
		try {
			mFireBaseAnalytics = FirebaseAnalytics.getInstance(this);
		}
		catch (Exception ignored) {

		}
	}

	private void initRemoteConfig() {
		try {
			mFireBaseRemoteConfig 											= FirebaseRemoteConfig.getInstance();
			FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();

			mFireBaseRemoteConfig.setConfigSettingsAsync(configSettings);
			mFireBaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default);

			int cacheExpiration = 1;
			mFireBaseRemoteConfig.fetch(cacheExpiration)
					.addOnCompleteListener(this, task -> {
						if (task.isSuccessful()) {
							Log.i("remote config", "success");
							mFireBaseRemoteConfig.fetchAndActivate();
						}
						else
							Log.i("remote config", "failed");
					});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			main.quit();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
