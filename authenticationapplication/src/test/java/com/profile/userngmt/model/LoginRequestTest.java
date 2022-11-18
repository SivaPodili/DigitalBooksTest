package com.profile.userngmt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
class LoginRequestTest {
LoginRequest login=new LoginRequest();
    
    @Test
       void userNameTest() {
           login.setUsername("siva");
           String name=login.getUsername();
           assertEquals("siva",name);
            }
    @Test
       void passwordTest() {
           login.setPassword("password");
           String pass=login.getPassword();
           assertEquals("password",pass);
           
         }
	

}
