package com.profile.userngmt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest( 
		  properties = {"spring.cloud.config.enabled=false"}
		)
class AuthenticationServiceImplTest {
	
	@Autowired
	AuthenticationServiceImpl authenticationServiceImpl;
	
	private Collection<? extends GrantedAuthority> authorities;

	@Test
	  public void ValidUsernameTest()
	  {
	    UserDetails userDetails = authenticationServiceImpl.loadUserByUsername ("sivapodili");
	    Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(authToken);
	    assertThat(authToken);
	    
	  }
	

}
