package com.example.my.config.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
  }

}
