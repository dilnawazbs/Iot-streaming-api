package com.iot.relay.api.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Filter;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.relay.model.model.UserData;
import com.iot.relay.api.config.JwtRequestFilter;
import com.iot.relay.api.config.JwtTokenUtil;
import com.iot.relay.api.service.JwtUserDetailsService;

@AutoConfigureDataMongo
@WebMvcTest(value = UserController.class, includeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtTokenUtil.class)})
public class UserControllerTest {

	@MockBean
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private MockMvc mvc;

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

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        dummy = new User("admin", "password", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

	/**
	 * Test the create new user
	 * 
	 * @throws Exception
	 */
	@Test
	public void createNewUser() throws Exception {
		UserData user = createTestUser();
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/create/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mvc.perform(request)
				.andExpect(status().isOk()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertTrue(response.getContentAsString().contains("User created successfully"));

	}

	private UserData createTestUser() {
		UserData user = new UserData();
		user.setUsername("admin");
		user.setPassword("password");
		return user;
	}


}
