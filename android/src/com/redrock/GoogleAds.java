package com.redrock;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.badlogic.gdx.utils.Timer;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.redrock.sdk.AdsClose;
import com.redrock.sdk.IPlat;
import com.redrock.template.BuildConfig;

public class GoogleAds {

  private static final String TAG = "RedRockSdk";

  private static final String ADMOB_APP_ID                        = BuildConfig.ADMOB_APP_ID;
  private static final String ADMOB_BANNER_ID                     = BuildConfig.ADMOB_BANNER_ID;
  private static final String ADMOB_FULLSCREEN_ID                 = BuildConfig.ADMOB_FULLSCREEN_ID;
  private static final String ADMOB_FULLSCREEN_ID_HIGHECPM        = BuildConfig.FULLSCREEN_HIGH_ECPM;
  private static final String ADMOB_VIDEO_ID                      = BuildConfig.ADMOB_VIDEO_ID;
  private static final String ADMOB_VIDEO_ID_HIGHECPM             = BuildConfig.VIDEO_HIGH_ECPM;

  private final int DEFAULT_MAX_VIDEO_REWARD_LOAD_FALIED              = 10;
  private final int DEFAULT_MAX_VIDEO_REWARD_HIGH_ECPM_LOAD_FALIED    = 10;

  private final int DEFAULT_MAX_FULL_SCREEN_ADS_LOAD_FAILED           = 10;
  private final int DEFAULT_MAX_FULL_SCREEN_ADS_HIGH_ECPM_LOAD_FAILED = 10;

  private final int LOAD_FULL_SCREEN_DURATION           = 10;
  private final int LOAD_FULL_SCREEN_HIGH_ECPM_DURATION = 10;

  private final int LOAD_VIDEO_REWARD_DURATION            = 10;
  private final int LOAD_VIDEO_REWARD_HIGH_ECPM_DURATION  = 20;

  private Activity              activity;
  private IPlat                 iPlat;
  private FrameLayout           rootView;

  private InterstitialAd        interstitialHighView;
  private InterstitialAd        interstitialView;
  private RewardedAd            videoRewardedView;
  private RewardedAd            videoRewardedHighView;
  private AdView                bannerView;
  private AdSize                bannerAdSize;

  private int maxVideoRewardLoadFailed;
  private int maxVideoRewardHighEcpmLoadFailed;
  private int maxFullscreenLoadFailed;
  private int maxFullscreenHighEcpmLoadFailed;

  private int videoRewardLoadFailedCount  = 0;
  private int fullScreenLoadFailedCount   = 0;

  private int videoRewardHighEcpmLoadFailedCount  = 0;
  private int fullScreenHighEcpmLoadFailedCount   = 0;

  boolean bannerVisible = true;

  public void initAd(Activity android, IPlat platform, FrameLayout rootView) {
    this.activity = android;
    this.iPlat    = platform;
    this.rootView = rootView;

//    RequestConfiguration requestConfiguration = MobileAds.getRequestConfiguration()
//        .toBuilder()
//        .setTagForChildDirectedTreatment(TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
//        .setMaxAdContentRating(MAX_AD_CONTENT_RATING_G)
//        .build();
//    MobileAds.setRequestConfiguration(requestConfiguration);

    initLoadConfigAds();

    MobileAds.initialize(android, initializationStatus -> {});
    android.runOnUiThread(() -> {
      try {
        initBanner();
        initInterstitial();
        initVideoReward();
//        initVideoRewardHighEcpm();
//        initInterstitialHighEcpm();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  private void initLoadConfigAds() {
    maxVideoRewardLoadFailed          = iPlat.getConfigIntValue("MAX_VIDEO_REWARD_LOAD_FALIED", DEFAULT_MAX_VIDEO_REWARD_LOAD_FALIED);
    maxVideoRewardHighEcpmLoadFailed  = iPlat.getConfigIntValue("MAX_VIDEO_REWARD_HIGH_ECPM_LOAD_FAILED", DEFAULT_MAX_VIDEO_REWARD_HIGH_ECPM_LOAD_FALIED);

    maxFullscreenLoadFailed         = iPlat.getConfigIntValue("MAX_FULL_SCREEN_ADS_LOAD_FAILED", DEFAULT_MAX_FULL_SCREEN_ADS_LOAD_FAILED);
    maxFullscreenHighEcpmLoadFailed = iPlat.getConfigIntValue("MAX_FULL_SCREEN_ADS_HIGH_ECPM_LOAD_FAILED", DEFAULT_MAX_FULL_SCREEN_ADS_HIGH_ECPM_LOAD_FAILED);
  }

  private void initBanner() {
    bannerView    = new AdView(activity);
    bannerAdSize  = getAdSize();

    bannerView.setAdUnitId(ADMOB_BANNER_ID);
    bannerView.setAdSize(bannerAdSize);
    bannerView.setBackgroundColor(Color.BLACK);

    RelativeLayout relativeLayout = new RelativeLayout(activity);
    rootView.addView(relativeLayout);

    RelativeLayout.LayoutParams adViewParams = new RelativeLayout.LayoutParams(AdView.LayoutParams.WRAP_CONTENT, AdView.LayoutParams.WRAP_CONTENT);
    adViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    adViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
    adViewParams.height = bannerAdSize.getHeightInPixels(activity);
    relativeLayout.addView(bannerView, adViewParams);

    bannerView.loadAd(new AdRequest.Builder().build());
    bannerView.setAdListener(new AdListener() {
      @Override
      public void onAdLoaded() {
        super.onAdLoaded();
        bannerView.setVisibility(View.GONE);
        showBanner(bannerVisible);
      }

      @Override
      public void onAdFailedToLoad(LoadAdError adError) {
        super.onAdFailedToLoad(adError);
        bannerView.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onAdOpened() {
        // Code to be executed when an ad opens an overlay that
        // covers the screen.
      }

      @Override
      public void onAdClicked() {
        // Code to be executed when the user clicks on an ad.
      }

      @Override
      public void onAdClosed() {
        // Code to be executed when the user is about to return
        // to the app after tapping on an ad.
      }
    });

    AdRequest adRequest = new AdRequest.Builder().build();
    bannerView.loadAd(adRequest);
  }

  private void initInterstitial() {
    Log.d(TAG, "low init fullscreen");
    AdRequest adRequest   = new AdRequest.Builder().build();
    InterstitialAd.load(activity, ADMOB_FULLSCREEN_ID, adRequest, new InterstitialAdLoadCallback() {
      @Override
      public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
        Log.d(TAG, "Low fullscreen loaded");
        interstitialView          = interstitialAd;
        fullScreenLoadFailedCount = 0;

        interstitialView.setFullScreenContentCallback(new FullScreenContentCallback() {

          @Override
          public void onAdDismissedFullScreenContent() {
            Log.d(TAG, "onAdDismissedFullScreenContent");
            initInterstitial();
          }

          @Override
          public void onAdFailedToShowFullScreenContent(AdError adError) {
            Log.d(TAG, "onAdFailedToShowFullScreenContent");
          }

          @Override
          public void onAdShowedFullScreenContent() {
            Log.d(TAG, "onAdShowedFullScreenContent");
            interstitialView = null;
          }
        });
      }

      @Override
      public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        Log.i(TAG, loadAdError.getMessage());
        interstitialView = null;

        fullScreenLoadFailedCount++;
        if (fullScreenLoadFailedCount > maxFullscreenLoadFailed)
          fullScreenLoadFailedCount = maxFullscreenLoadFailed;

        Timer.schedule(new Timer.Task() {
          @Override
          public void run() {
            activity.runOnUiThread(GoogleAds.this::initInterstitial);
          }
        }, LOAD_FULL_SCREEN_DURATION * fullScreenLoadFailedCount);
      }
    });
  }

  private void initVideoReward() {
    Log.d(TAG, "init video reward");
    AdRequest adRequest = new AdRequest.Builder().build();

    RewardedAd.load(activity, ADMOB_VIDEO_ID, adRequest, new RewardedAdLoadCallback() {
      @Override
      public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        videoRewardedView = null;

        videoRewardLoadFailedCount++;
        if (videoRewardLoadFailedCount > maxVideoRewardLoadFailed)
          videoRewardLoadFailedCount = maxVideoRewardLoadFailed;

        Timer.schedule(new Timer.Task() {
          @Override
          public void run() {
            activity.runOnUiThread(GoogleAds.this::initVideoReward);
          }
        }, LOAD_VIDEO_REWARD_DURATION * videoRewardLoadFailedCount);
      }

      @Override
      public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
        Log.d(TAG, "video low load");
        videoRewardLoadFailedCount  = 0;
        videoRewardedView           = rewardedAd;

        videoRewardedView.setFullScreenContentCallback(new FullScreenContentCallback() {
          @Override
          public void onAdShowedFullScreenContent() {
            Log.d(TAG, "onAdShowedFullScreenContent");
            videoRewardedView = null;
          }

          @Override
          public void onAdFailedToShowFullScreenContent(AdError adError) {
            Log.d(TAG, "onAdFailedToShowFullScreenContent");
          }

          @Override
          public void onAdDismissedFullScreenContent() {
            Log.d(TAG, "onAdDismissedFullScreenContent");
            initVideoReward();
          }
        });
      }
    });
  }

  private void initInterstitialHighEcpm() {
    Log.d(TAG, "init high ads ecpm");
    AdRequest adRequest   = new AdRequest.Builder().build();

    InterstitialAd.load(activity, ADMOB_FULLSCREEN_ID_HIGHECPM, adRequest, new InterstitialAdLoadCallback() {
      @Override
      public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
        Log.d(TAG, "ecpm loaded");
        interstitialHighView              = interstitialAd;
        fullScreenHighEcpmLoadFailedCount = 0;

        interstitialHighView.setFullScreenContentCallback(new FullScreenContentCallback() {
          @Override
          public void onAdDismissedFullScreenContent() {
            Log.d(TAG, "onAdDismissedFullScreenContent");
            initInterstitialHighEcpm();
          }

          @Override
          public void onAdFailedToShowFullScreenContent(AdError adError) {
            Log.d(TAG, "onAdFailedToShowFullScreenContent");
          }

          @Override
          public void onAdShowedFullScreenContent() {
            Log.d(TAG, "onAdShowedFullScreenContent");
            interstitialHighView = null;
          }
        });
      }

      @Override
      public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        Log.i(TAG, loadAdError.getMessage());
        interstitialHighView = null;

        fullScreenHighEcpmLoadFailedCount++;
        if (fullScreenHighEcpmLoadFailedCount > maxFullscreenHighEcpmLoadFailed) //failed nhiều quá thì ko load nữa, tránh fillRate thấp
          fullScreenHighEcpmLoadFailedCount = maxFullscreenHighEcpmLoadFailed;

        Timer.schedule(new Timer.Task() {
          @Override
          public void run() {
            activity.runOnUiThread(GoogleAds.this::initInterstitialHighEcpm);
          }
        }, LOAD_FULL_SCREEN_HIGH_ECPM_DURATION * fullScreenHighEcpmLoadFailedCount);


      }
    });
  }

  private void initVideoRewardHighEcpm() {
    Log.d(TAG, "init video high ecpm");
    AdRequest adRequest = new AdRequest.Builder().build();

    RewardedAd.load(activity, ADMOB_VIDEO_ID_HIGHECPM, adRequest, new RewardedAdLoadCallback() {
      @Override
      public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        videoRewardedHighView = null;

        videoRewardHighEcpmLoadFailedCount++;
        if (videoRewardHighEcpmLoadFailedCount > maxVideoRewardHighEcpmLoadFailed)
          videoRewardHighEcpmLoadFailedCount = maxVideoRewardHighEcpmLoadFailed;

        Timer.schedule(new Timer.Task() {
          @Override
          public void run() {
            activity.runOnUiThread(GoogleAds. this::initVideoRewardHighEcpm);
          }
        }, LOAD_VIDEO_REWARD_HIGH_ECPM_DURATION * videoRewardHighEcpmLoadFailedCount);
      }

      @Override
      public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
        Log.d(TAG, "video high load");
        videoRewardHighEcpmLoadFailedCount  = 0;
        videoRewardedHighView               = rewardedAd;

        videoRewardedHighView.setFullScreenContentCallback(new FullScreenContentCallback() {
          @Override
          public void onAdShowedFullScreenContent() {
            Log.d(TAG, "onAdShowedFullScreenContent");
            videoRewardedHighView = null;
          }

          @Override
          public void onAdFailedToShowFullScreenContent(AdError adError) {
            Log.d(TAG, "onAdFailedToShowFullScreenContent");
          }

          @Override
          public void onAdDismissedFullScreenContent() {
            Log.d(TAG, "onAdDismissedFullScreenContent");
            initVideoRewardHighEcpm();
          }
        });
      }
    });
  }

  private AdSize getAdSize() {
    // Step 2 - Determine the screen width (less decorations) to use for the ad width.
    Display         display     = activity.getWindowManager().getDefaultDisplay();
    DisplayMetrics  outMetrics  = new DisplayMetrics();

    float widthPixels = outMetrics.widthPixels;
    float density     = outMetrics.density;
    int   adWidth     = (int) (widthPixels / density);

    display.getMetrics(outMetrics);
    // Step 3 - Get adaptive ad size and return for setting on the ad view.
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
  }

  public void showVideoReward(final AdsClose callback) {
    activity.runOnUiThread(() -> {
      if (videoRewardedHighView != null) {
        Log.d(TAG, "video high showing");
        videoRewardedHighView.show(activity, rewardItem -> callback.OnEvent(true));
      }
      else if (videoRewardedView != null) {
        Log.d(TAG, "video low showing");
        videoRewardedView.show(activity, rewardItem -> callback.OnEvent(true));
      }
      else {
        Log.d(TAG, "video not ready yet!");
        initVideoReward();

        callback.OnEvent(false);
      }
    });
  }

  public void showFullscreen() {
    activity.runOnUiThread(() -> {
      if (interstitialHighView != null) {
        interstitialHighView.show(activity);

        iPlat.trackCustomEvent("show_high_fullscreen");
        Log.d(TAG, "show_high_fullscreen");
      }
      else if (interstitialView != null) {
        interstitialView.show(activity);

        iPlat.trackCustomEvent("show_fullscreen");
        Log.d(TAG, "show_fullscreen");
      }
      else {
        initInterstitial();

        Log.d(TAG, "The interstitial ad wasn't ready yet!");
      }
    });
  }

  public boolean isFullscreenReady() {
    if (interstitialHighView != null || interstitialView != null)
      return true;

    initInterstitial();
    return false;
  }

  public boolean isVideoRewardReady() {
    if (videoRewardedHighView != null || videoRewardedView != null)
      return true;

    initVideoReward();
    return false;
  }

  public void showBanner(boolean visible) {
    final boolean v = visible;
    activity.runOnUiThread(() -> {
      if (v)
        bannerView.setVisibility(View.VISIBLE);
      else
        bannerView.setVisibility(View.GONE);
    });
  }

  public int getBannerHeightInPixel() {
    if (bannerAdSize != null)
      return bannerAdSize.getHeightInPixels(activity);
    return 0;
  }
}
