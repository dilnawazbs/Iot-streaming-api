package com.iot.relay.api.request;

import com.iot.relay.api.validator.ValidQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.lang.Nullable;

@ValidQuery
@Data
public class QueryRequest {

  @NotEmpty(message = "Start time shouldn't be null")
  @Schema(description = "Start date time to filter the data.", required = true)
  private String startDateTime;

  @NotEmpty(message = "End time shouldn't be null")
  @Schema(description = "End date time to filter the data.", required = true)
  private String endDateTime;

  @Nullable
  @Schema(description = "Type of event.")
  private String eventType;

  @Nullable
  @Schema(description = "Cluster id.")
  private Long clusterId;
}
