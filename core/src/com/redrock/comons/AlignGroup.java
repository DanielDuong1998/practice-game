package com.redrock.comons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class AlignGroup extends Group {
  public AlignGroup() {

  }

  public AlignGroup(float w, float h) {
    setSize(w, h);
  }

  public void addActor(Actor actor, float padX, float padY, int align) {
    addActor(actor);
    align(padX, padY, align, actor);
  }

  public void addActor(Actor... actors){
    for(Actor actor: actors)
      this.addActor(actor);
  }

  protected void align(float padX, float padY, int align, Actor child) {
    float posX  = 0;
    float posY  = 0;
    int   textAlign = 0;

    if ((align & Align.center) == Align.center) {
      textAlign |= Align.center;
      posX = getWidth()/2 + padX;
      posY = getHeight()/2 + padY;
    }

    if ((align & Align.left) == Align.left) {
      textAlign |= Align.left;
      posX = padX;
    }

    if ((align & Align.top) == Align.top) {
      textAlign |= Align.top;
      posY = getHeight() - padY;
    }

    if ((align & Align.bottom) == Align.bottom) {
      textAlign |= Align.bottom;
      posY = padY;
    }

    if ((align & Align.right) == Align.right) {
      textAlign |= Align.right;
      posX = getWidth() - padX;
    }

    if (child instanceof Label)
      ((Label)child).setAlignment(textAlign);
    child.setPosition(posX, posY, align);
  }
}