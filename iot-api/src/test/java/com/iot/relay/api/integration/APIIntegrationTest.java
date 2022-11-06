package com.iot.relay.api.integration;

import com.iot.relay.api.ApiApplication;
import com.iot.relay.api.config.JwtRequestFilter;
import com.iot.relay.api.config.JwtTokenUtil;
import com.iot.relay.api.request.QueryRequest;
import com.iot.relay.model.model.SensorDataEntity;
import com.iot.relay.model.repo.SensorDataRepository;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  classes = { ApiApplication.class },
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureDataMongo
@ContextConfiguration(classes = ApiApplication.class)
public class APIIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  private SensorDataRepository sensorDataRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @MockBean
  private JwtTokenUtil jwtUtil;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  AuthenticationManager authenticationManager;

  @Autowired
  private WebApplicationContext context;

  @Test
  public void testMinWithClusterIdAndTypeSuccess() {
    initTable(sensorDataRepository);
    QueryRequest request = new QueryRequest();
    request.setClusterId(1l);
    request.setEventType("TEMPERATURE");
    request.setStartDateTime("2021-12-23");
    request.setEndDateTime("2023-12-23");

    final UserDetails userDetails = new User("dilnawaz","password", new ArrayList<>());
    var tokenString = "Bearer " + jwtTokenUtil.generateToken(userDetails);
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", tokenString);
    ResponseEntity<Object> entity = new TestRestTemplate().exchange(
        createURLWithPort("/query/min/", request),
        HttpMethod.GET,
        new HttpEntity<Object>(headers),
        Object.class
      );
    Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    Assertions.assertEquals(111111, entity.getBody());
  }

  @Test
  public void testMaxWithClusterIdAndTypeSuccess() {
    initTable(sensorDataRepository);
    QueryRequest request = new QueryRequest();
    request.setClusterId(1l);
    request.setEventType("TEMPERATURE");
    request.setStartDateTime("2021-12-23");
    request.setEndDateTime("2023-12-23");

    final UserDetails userDetails = new User("dilnawaz","password",new ArrayList<>());
    var tokenString = "Bearer " + jwtTokenUtil.generateToken(userDetails);
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", tokenString);
    ResponseEntity<Object> entity = new TestRestTemplate().exchange(
        createURLWithPort("/query/max/", request),
        HttpMethod.GET,
        new HttpEntity<Object>(headers),
        Object.class
      );
    Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    Assertions.assertEquals(444444, entity.getBody());
  }

  @Test
  public void testMedianWithClusterIdAndTypeSuccess() {
    initTable(sensorDataRepository);
    QueryRequest request = new QueryRequest();
    request.setClusterId(1l);
    request.setEventType("TEMPERATURE");
    request.setStartDateTime("2021-12-23");
    request.setEndDateTime("2023-12-23");

    final UserDetails userDetails = new User("dilnawaz", "password", new ArrayList<>());
    var tokenString = "Bearer " + jwtTokenUtil.generateToken(userDetails);
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", tokenString);
    ResponseEntity<Object> entity = new TestRestTemplate().exchange(
        createURLWithPort("/query/median/", request),
        HttpMethod.GET,
        new HttpEntity<Object>(headers),
        Object.class
      );
    Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    Assertions.assertEquals(333333, entity.getBody());
  }

  private String createURLWithPort(String uri, QueryRequest request) {
    UriComponentsBuilder builder = UriComponentsBuilder
      .fromHttpUrl("http://localhost:" + port + uri)
      .queryParam("startDateTime", request.getStartDateTime())
      .queryParam("endDateTime", request.getEndDateTime())
      .queryParam("clusterId", request.getClusterId())
      .queryParam("eventType", request.getEventType());
    return builder.build().toUriString();
  }

  /**
   * Fill table with dummy data to query
   *
   * @param sensorDataRepository repository where data needs to be stored
   */
  public static void initTable(SensorDataRepository sensorDataRepository) {
    SensorDataEntity entity1 = SensorDataEntity
      .builder()
      .id(1l)
      .clusterId(1l)
      .type("TEMPERATURE")
      .name("Living Room Temp")
      .value(new BigDecimal("111111"))
      .timestamp(OffsetDateTime.parse("2022-08-14T18:18:55.479998Z"))
      .build();
    sensorDataRepository.save(entity1);

    SensorDataEntity entity2 = SensorDataEntity
      .builder()
      .id(1l)
      .clusterId(1l)
      .type("TEMPERATURE")
      .name("Living Room Temp")
      .value(new BigDecimal("222222"))
      .timestamp(OffsetDateTime.parse("2022-08-14T18:18:57.479998Z"))
      .build();
    sensorDataRepository.save(entity2);

    SensorDataEntity entity3 = SensorDataEntity
      .builder()
      .id(1l)
      .clusterId(1l)
      .type("TEMPERATURE")
      .name("Living Room Temp")
      .value(new BigDecimal("333333"))
      .timestamp(OffsetDateTime.parse("2022-08-14T18:18:58.479998Z"))
      .build();
    sensorDataRepository.save(entity3);

    SensorDataEntity entity4 = SensorDataEntity
      .builder()
      .id(1l)
      .clusterId(1l)
      .type("TEMPERATURE")
      .name("Living Room Temp")
      .value(new BigDecimal("444444"))
      .timestamp(OffsetDateTime.parse("2022-08-14T18:18:59.479998Z"))
      .build();
    sensorDataRepository.save(entity4);
  }
}
