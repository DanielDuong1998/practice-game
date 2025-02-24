package com.redrock.sdk.model;

import com.redrock.sdk.model.dto.Profile;

public class Session {
  public Profile profile;

  public static Session ofDefault() {
    Session session = new Session();

    session.profile = Profile.ofDefault();
    return session;
  }

}
