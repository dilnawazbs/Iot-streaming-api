package com.iot.relay.model.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.iot.relay.model.constants.SensorConstant;

@Document(collection = SensorConstant.USER_COLLECTION_NAME)
@Data
@Builder
public class UserDataEntity {

  @Id
  @Builder.Default
  private String uuid = UUID.randomUUID().toString();

  private String username;
  private String password;
}
