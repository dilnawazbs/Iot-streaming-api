package com.iot.relay.model.repo;

import com.iot.relay.model.model.UserDataEntity;

public interface UserDataRepositoryCustom {
  public UserDataEntity findUserByName(String userName);
}
