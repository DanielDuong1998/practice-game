package com.redrock.client;

import com.redrock.sdk.AdsClose;

public class FBInstant {
  public static native void ShowFullscreen(AdsClose start, AdsClose end) /*-{
            var s = function(D) {
              start.@com.redrock.sdk.AdsClose::OnEvent(*)(D);
            };
            var e = function(E) {
              end.@com.redrock.sdk.AdsClose::OnEvent(*)(E);
            }
            $wnd.showFullscreen(s, e);
        }-*/;


  public static native boolean IsFullscreenReady()/*-{
            return $wnd.isFullscreenReady();
        }-*/;

  public static native void ShowVideoReward(AdsClose callback) /*-{
            $wnd.showVideoReward(function(D){
                callback.@com.redrock.sdk.AdsClose::OnEvent(*)(D);
            });
        }-*/;

  public static native void LoadingFinished() /*-{
            $wnd.FBInstant.startGameAsync().then(function() {
                $wnd.startGame();
              });
    }-*/;

  public static native boolean IsVideoRewardReady()/*-{
            return $wnd.isVideoRewardReady();
        }-*/;
  public static native void LoadingProgress(int percent) /*-{
            $wnd.FBInstant.setLoadingProgress(percent);
    }-*/;
}
