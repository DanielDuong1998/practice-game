package com.redrock.sdk.model;

public interface IDataAccess<T> {
  void sync(T model);
  T load();
}
