package com.redrock.sdk.wheel;

import com.redrock.sdk.modules.generic.GenericMessage;

public class WheelMsg {

  public final static String REWARD_PRIZES = "reward_prizes";

  public static class WheelShowMsg extends GenericMessage {

    public Runnable cbWheelExit;

    public WheelShowMsg(String name) {
      super();
    }

    @Override
    public void reset() {
      cbWheelExit = null;
    }
  }

  public static class RewardPrizesMsg extends GenericMessage {

    public int prize = 0;

    public RewardPrizesMsg(String name) {
      super();
    }

    @Override
    public void reset() {
      prize = 0;
    }
  }

}
