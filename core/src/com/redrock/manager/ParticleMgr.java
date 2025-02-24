package com.redrock.manager;

import com.redrock.sdk.particle.NParticle;

import java.util.HashMap;

public class ParticleMgr {

  public final static float SCL_HIT_CARD_IN_SP = 1.15f;

  public final static String HIT_CARD_IN_SP = "hit_card_in_sp";
  public final static String REWARD_COIN    = "reward_coin";
  public final static String WIN            = "win";
  public final static String PAPER          = "paper";

  private HashMap<String, NParticle>  hmParticles;
  private String                      keyAtlas      = "effect";

  public ParticleMgr() { }

  public void load() {
    hmParticles = new HashMap<>();
  }

  public NParticle getParticle(String key) {
    return hmParticles.get(key);
  }

}
