package com.redrock.sdk.modules.genName;

import com.redrock.sdk.modules.generic.GenericMessage;

import java.util.ArrayList;
import java.util.List;

public class GenNameMsg extends GenericMessage {
  public final static String GEN_NAME_MSG = "gen name";

  public int maxSizeName = 0;

  public List<String> lsNames = new ArrayList<>();

  public GenNameMsg(String name) {
    super();
  }

  @Override
  public void reset() {
    maxSizeName = 0;
    lsNames.clear();
  }
}
