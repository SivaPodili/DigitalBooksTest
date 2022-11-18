package com.profile.userngmt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest( 
		  properties = {"spring.cloud.config.enabled=false"}
		)
class AuthenticationServiceImplTest {
	
	@Autowired
	AuthenticationServiceImpl authenticationServiceImpl;

	@Test
	  void ValidUsernameTest()
	  {
	    UserDetails userDetails = authenticationServiceImpl.loadUserByUsername ("sivapodili");
	    Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(authToken);
	    assertEquals("sivapodili",userDetails.getUsername());
	    
	  }
	

}
