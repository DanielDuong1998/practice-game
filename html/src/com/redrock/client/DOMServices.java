package com.redrock.client;

public class DOMServices {
  static native int dayDiff(double t1, double t2) /*-{
            var d1 = new Date(t1);
            var d2 = new Date(t2);

            return  Math.abs(d1.getDay() - d2.getDay());
        }-*/;
  static native double devicePixelRatio() /*-{
            return  $wnd.window.devicePixelRatio;
        }-*/;
  static native int getInnerWidth() /*-{
            return $wnd.window.innerWidth;
        }-*/;
  static native int getInnerHeight() /*-{
            return $wnd.window.innerHeight;
        }-*/;
  static native int getWidthPx() /*-{
            return $wnd.window.innerWidth * $wnd.window.devicePixelRatio;
        }-*/;
  static native int getHeightPx() /*-{
            return $wnd.window.innerHeight * $wnd.window.devicePixelRatio;
        }-*/;
  static native boolean jsResize() /*-{
            return $wnd.jsResize();
        }-*/;
  static native String getToken() /*-{
            return $wnd.getToken();
        }-*/;
}
