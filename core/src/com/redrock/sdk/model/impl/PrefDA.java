package com.redrock.sdk.model.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.redrock.sdk.model.IDataAccess;

public class PrefDA<T> implements IDataAccess<T> {
  Preferences pref;
  Json        serialize;
  String      syncKey;
  Class<T>    model;

  public PrefDA(String syncKey, Class<T> model) {
    this.model      = model;
    this.syncKey    = syncKey;
    this.pref       = Gdx.app.getPreferences(syncKey);
    this.serialize  = new Json();
    this.serialize.setIgnoreUnknownFields(true);
  }

  @Override
  public void sync(T model) {
    String js = serialize.toJson(model);
    pref.putString(syncKey, js);
    pref.flush();
  }

  @Override
  public T load() {
    String val = pref.getString(syncKey);
    return serialize.fromJson(model, val);
  }
}
