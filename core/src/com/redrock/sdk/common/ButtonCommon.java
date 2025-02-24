package com.redrock.sdk.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;

public class ButtonCommon extends Group {

  private Builder builder;

  public ButtonCommon(Builder builder) {
    this.builder = builder;

    addActor(builder.frmBg);
    setSize(builder.frmBg.getWidth(), builder.frmBg.getHeight());
    setOrigin(builder.align);

    if (builder.frmBgActive != null) {
      builder.frmBgActive.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
      builder.frmBgActive.setVisible(false);
      addActor(builder.frmBgActive);
    }

    if (builder.icon != null) {
      builder.icon.setPosition(builder.padLeftIcon, builder.padBotIcon);
      addActor(builder.icon);
    }

    if (builder.lbText != null) {
      builder.lbText.setPosition(getWidth() / 2 + builder.padLeftText, getHeight() / 2 + builder.padBotText, Align.center);
      addActor(builder.lbText);
    }
  }

  public void setActive(boolean active) {
    if (builder.frmBgActive != null) {
      builder.frmBg.setVisible(!active);
      builder.frmBgActive.setVisible(active);
    }
  }

  @Override
  public void setColor(Color color) {
    super.setColor(color);

    builder.frmBg.setColor(color);

    if (builder.lbText != null)
      builder.lbText.setColor(color);
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);

    if (builder.frmBgActive != null)
      builder.frmBgActive.setPosition(getWidth() / 2, getHeight() / 2, Align.center);

    if (builder.lbText != null)
      builder.lbText.setPosition(getWidth() / 2 + builder.padLeftText, getHeight() / 2 + builder.padBotText, Align.center);

    if (builder.icon != null)
      builder.icon.setPosition(builder.padLeftIcon, builder.padBotIcon);
  }

  public static class Builder {

    private Image frmBg, frmBgActive, icon;
    private Label lbText;

    private int   align = Align.center;
    private float padLeftText = 0, padBotText = 0;
    private float padLeftIcon = 0, padBotIcon = 0;

    public Builder(TextureRegion frmBg) {
      this.frmBg = new Image(frmBg);
    }

    public Builder(TextureRegion frmBg, TextureRegion frmBgActive) {
      this.frmBg        = new Image(frmBg);
      this.frmBgActive  = new Image(frmBgActive);
    }

    public Builder align(int align) {
      this.align = align;

      return this;
    }

    public Builder icon(TextureRegion rgIcon) {
      if (rgIcon != null)
        this.icon = new Image(rgIcon);
      else
        this.icon = null;

      return this;
    }

    public Builder cfgIcon(float padL, float padB) {
      this.padLeftIcon  = padL;
      this.padBotIcon   = padB;

      return this;
    }

    public Builder text(String keyLocalize, BitmapFont bitmapFont, int... params) {
      if (keyLocalize.equals("")) {
        lbText = null;

        return this;
      }

      String text = Main.multiLangMgr().getText(keyLocalize, Main.session().profile.language, params);
      lbText      = new Label(text, new Label.LabelStyle(bitmapFont, Color.WHITE));

      Main.multiLangMgr().makeKey(lbText, keyLocalize, params);

      return this;
    }

    public Builder textNoneParam(String text, BitmapFont bitmapFont) {
      lbText = new Label(text, new Label.LabelStyle(bitmapFont, Color.WHITE));

      return this;
    }

    public Builder cfgText(float fontScl, int align) {
      lbText.setFontScale(fontScl);
      lbText.setAlignment(align);
      lbText.setSize(lbText.getPrefWidth(), lbText.getPrefHeight());

      return this;
    }

    public Builder setPaddingText(float padL, float padB) {
      this.padLeftText  = padL;
      this.padBotText   = padB;

      return this;
    }

    public ButtonCommon build() {
      return new ButtonCommon(this);
    }
  }
}
