package com.redrock.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;

public class GamePlayScene extends ScreenAdapter {
  private Group gParent;

  public GamePlayScene() {
    Main.layers().init(SceneMgr.GAMEPLAY_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());

    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.GAMEPLAY_SCENE);
    Main.getStage().addActor(this.gParent);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

  }

  @Override
  public void resize(int width, int height) {

  }
}
