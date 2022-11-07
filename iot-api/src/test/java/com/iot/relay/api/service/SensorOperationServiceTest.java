package com.iot.relay.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.iot.relay.api.request.QueryRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * Test class for link
 * {@link SensorOperationService#execute(String, com.relay.iot.services.api.request.QueryRequest)}
 *
 */
@ExtendWith(MockitoExtension.class)
public class SensorOperationServiceTest {

  @Mock
  private SensorOperationService sensorOperation;

  @Test
  public void testExecuteSuccess() {
    when(sensorOperation.execute(anyString(), any(QueryRequest.class))).thenReturn(BigDecimal.ONE);
    BigDecimal result = sensorOperation.execute("min", new QueryRequest());
    assertEquals(result, BigDecimal.ONE, "Result be 1.");
  }
}
