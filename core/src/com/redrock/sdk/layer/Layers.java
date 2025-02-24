package com.redrock.sdk.layer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Layers {

  private String sceneActivating = SceneMgr.START_SCENE;

  private HashMap<String, List<Group>>  hmLayers;

  public Layers() {
    hmLayers = new HashMap<>();
  }

  public void init(String scene, float width, float height) {
    List<Group> lsLayers = new ArrayList<>();

    for (int i = 0; i < LayerCfg.values().length; i++) {
      Group group = new Group();
      group.setSize(width, height);
      lsLayers.add(group);

      if (i > 0)
        group.setTouchable(Touchable.disabled);
    }

    hmLayers.put(scene, lsLayers);
  }

  public void resize(int width, int height) {
    for (List<Group> layers : hmLayers.values())
      for (Group layer : layers)
        layer.setSize(width, height);
  }

  public Group getLayer(String scene, LayerCfg layerCfg) {
    return hmLayers.get(scene).get(layerCfg.ordinal());
  }

  public void activeLayersBy(String scene) {
    sceneActivating = scene;

    for (Group layer : hmLayers.get(scene))
      Main.getStage().addActor(layer);
  }

  public void addTo(LayerCfg layerCfg, Actor actor) {
    hmLayers.get(sceneActivating).get(layerCfg.ordinal()).addActor(actor);
  }
}
