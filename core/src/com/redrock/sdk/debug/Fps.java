package com.redrock.sdk.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.redrock.Main;

public class Fps extends Group {

  public Label fps = new Label("", new Label.LabelStyle(Main.asset().getBMFont("wheel_font"), null));

  public Fps() {
    fps.setPosition(10, Main.getStage().getHeight() - fps.getHeight() - 50);
    addActor(fps);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

    fps.setText(Gdx.graphics.getFramesPerSecond());
  }
}
