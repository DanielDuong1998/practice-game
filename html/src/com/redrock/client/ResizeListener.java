package com.redrock.client;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;

class ResizeListener implements ResizeHandler {
  private HtmlLauncher launcher;

  public ResizeListener(HtmlLauncher launcher) {
    this.launcher = launcher;
  }
  @Override
  public void onResize(ResizeEvent event) {
    launcher.resizeCanvas();
  }
}
