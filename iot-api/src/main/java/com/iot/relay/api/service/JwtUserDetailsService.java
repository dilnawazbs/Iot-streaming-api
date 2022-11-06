package com.iot.relay.api.service;

import com.iot.relay.model.model.UserData;
import com.iot.relay.model.model.UserDataEntity;
import com.iot.relay.model.repo.UserDataRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserDataRepository userDataRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDataEntity user = userDataRepository.findUserByName(username);
    if (user == null) throw new UsernameNotFoundException("User not found : " + username);
    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  public void save(UserData userData, String encodedPassword) {
    userDataRepository.save(
      UserDataEntity.builder().username(userData.getUsername()).password(encodedPassword).build()
    );
  }
}
