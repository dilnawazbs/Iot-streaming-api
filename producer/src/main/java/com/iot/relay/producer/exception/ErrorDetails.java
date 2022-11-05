package com.iot.relay.producer.exception;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
  private Date timestamp;
  private String message;
  private String details;
  private List<String> errorFields;
}
