package com.iot.relay.model.model;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class SensorData {

  private Long id;
  private BigDecimal value;
  private String timeStamp;
  private String type;
  private String name;

  @Nullable
  private Long clusterId;
}
