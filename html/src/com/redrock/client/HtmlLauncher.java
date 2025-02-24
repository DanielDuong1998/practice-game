package com.redrock.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.redrock.Main;

@SuppressWarnings("unused")
public class HtmlLauncher extends GwtApplication {
        int PADDING = 0;
        boolean isHandleResize = false;

        @Override
        public GwtApplicationConfiguration getConfig () {
                int height = Window.getClientHeight() - PADDING;
                int width = Window.getClientWidth()- PADDING;

                GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(width, height);
                Window.enableScrolling(false);
                Window.setMargin("0");
                isHandleResize = true;
                Window.addResizeHandler(new ResizeListener(this));

                cfg.disableAudio = false;

                return cfg;
        }

        @Override
        protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
                meterPanel.setStyleName("gdx-meter");
                meterStyle.setProperty("backgroundColor","#0068ED");
                meterStyle.setProperty("backgroundImage","none");
                super.adjustMeterPanel(meterPanel, meterStyle);
        }

        @Override
        public Preloader.PreloaderCallback getPreloaderCallback() {
                return new Preloader.PreloaderCallback() {
                        public void error(String file) {
                                consoleLog("error: " + file);
                        }

                        public void update(Preloader.PreloaderState state) {
                                double percent = 100.0F * state.getProgress();
                                FBInstant.LoadingProgress((int)percent);

                                if(state.hasEnded()) {
                                        FBInstant.LoadingFinished();
                                }
                        }
                };
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Main(720, 1280, 2160, 3840, new IPlatAdapter(this));
        }

        public void setUpCanvas(){
                CanvasElement canvas = getCanvasElement();
                int w = canvas.getWidth();
                int h = canvas.getHeight();
                double dpr  = DOMServices.devicePixelRatio();
                int cw = (int)(w*dpr);
                int ch = (int)(h*dpr);

                if(isHandleResize){
                        w =   DOMServices.getInnerWidth();
                        h =   DOMServices.getInnerHeight();
                        cw =  DOMServices.getWidthPx();
                        ch =  DOMServices.getHeightPx();
                }

                canvas.getStyle().setWidth(w, Style.Unit.PX);
                canvas.getStyle().setHeight(h, Style.Unit.PX);
                canvas.setWidth(cw);
                canvas.setHeight(ch);
        }

        public void resizeCanvas(){
                CanvasElement canvas = getCanvasElement();
                int fw = DOMServices.getInnerWidth();
                int fh = DOMServices.getInnerHeight();

                canvas.getStyle().setWidth(fw, Style.Unit.PX);
                canvas.getStyle().setHeight(fh, Style.Unit.PX);
                canvas.setWidth(DOMServices.getWidthPx());
                canvas.setHeight(DOMServices.getHeightPx());
        }
}
