package com.redrock.sdk.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;

public class LabelC {

  private Label label;

  private LabelC(NewBuilder builder) {
    label = new Label(builder.text, new Label.LabelStyle(builder.bitmapFont, builder.color));
    label.setAlignment(builder.align);
    label.setFontScale(builder.fontSclX, builder.fontSclY);
    label.setBounds(0, 0, label.getPrefWidth(), label.getPrefHeight());
    label.getColor().a = builder.alpha;
    label.setTouchable(builder.touchable);
  }

  public void setText(String text) {
    label.setText(text);
  }

  public float getWidth() {
    return label.getWidth();
  }

  public float getHeight() {
    return label.getHeight();
  }

  public Label label() {
    return label;
  }

  //builder
  public static class NewBuilder {
    private BitmapFont        bitmapFont;
    private Color             color       = Color.WHITE;
    private String            text;
    private Touchable         touchable;

    private float             fontSclX    = 1f,
                              fontSclY    = 1f;
    private float             alpha       = 1f;
    private int               align       = Align.left;

    public NewBuilder(String text, String fontName) {
      this.text       = text;
      this.bitmapFont = Main.asset().getBMFont(fontName);
    }

    public NewBuilder scl(float fontScaleX, float fontScaleY) {
      this.fontSclX = fontScaleX;
      this.fontSclY = fontScaleY;
      return this;
    }

    public NewBuilder scl(float scl) {
      this.fontSclX = this.fontSclY = scl;
      return this;
    }

    public NewBuilder color(Color color) {
      this.color = color;
      return this;
    }

    public NewBuilder color(String hex) {
      this.color = Color.valueOf(hex);
      return this;
    }

    public NewBuilder align(int alignment) {
      this.align = alignment;
      return this;
    }

    public NewBuilder alpha(float alpha) {
      this.alpha = alpha;
      return this;
    }

    public NewBuilder touch(Touchable touchable) {
      this.touchable = touchable;
      return this;
    }

    public Label build() {
      return new LabelC(this).label;
    }
  }

}