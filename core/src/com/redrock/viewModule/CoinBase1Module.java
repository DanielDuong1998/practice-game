package com.redrock.viewModule;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.redrock.BmFontConfig;
import com.redrock.Main;
import com.redrock.sdk.common.LabelC;
import com.redrock.sdk.component.GenericModule;
import com.redrock.sdk.layer.LayerCfg;

public class CoinBase1Module extends GenericModule {

  private Label lbCoinBase;

  public CoinBase1Module() {
    lbCoinBase = new LabelC
        .NewBuilder("0", BmFontConfig.WHEEL)
        .build();

    Main.layers().addTo(LayerCfg.main, lbCoinBase);
  }
}
