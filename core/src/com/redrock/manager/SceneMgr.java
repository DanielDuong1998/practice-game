package com.redrock.manager;

import com.badlogic.gdx.ScreenAdapter;
import com.redrock.Main;
import com.redrock.scenes.GamePlayScene;
import com.redrock.scenes.StartScene;

import java.util.HashMap;
import java.util.Map;

public class SceneMgr {
  public static final String START_SCENE    = "start_scene";
  public static final String GAMEPLAY_SCENE = "gameplay_scene";

  private Map<String, ScreenAdapter>  mScene;
  private Main                        main;

  public SceneMgr(Main main) {
    this.main   = main;
    this.mScene = new HashMap<>();

    this.mScene.put(START_SCENE, new StartScene());
    this.mScene.put(GAMEPLAY_SCENE, new GamePlayScene());
  }

  public void addScene(String key, ScreenAdapter screen) {
    mScene.put(key, screen);
  }

  public void changeScene(String key) {
    ScreenAdapter s = mScene.get(key);

    if (s != null) {
      Main.getStage().clear();
      main.setScreen(s);
    }
  }

}
