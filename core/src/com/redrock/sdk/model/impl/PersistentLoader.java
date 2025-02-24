package com.redrock.sdk.model.impl;

import com.redrock.sdk.model.IDataAccess;

public class PersistentLoader<T> {

  IDataAccess<T>  da;
  T               model;

  public PersistentLoader(T model, Class<T> type, String syncKey) {
    this.da     = new PrefDA<>(syncKey, type);
    this.model  = model;
  }

  public void sync() {
    da.sync(model);
  }

  public T load() {
    T loaded = da.load();
    if (loaded == null)
      return model;

    model = loaded;
    return model;
  }
}
