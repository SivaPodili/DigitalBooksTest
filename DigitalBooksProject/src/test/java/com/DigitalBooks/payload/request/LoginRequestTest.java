package com.DigitalBooks.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoginRequestTest {
	LoginRequest login=new LoginRequest();
    
    @Test
       public void userNameTest() {
           login.setUsername("siva");
           String name=login.getUsername();
           assertEquals("siva",name);
            }
    @Test
       public void passwordTest() {
           login.setPassword("password");
           String pass=login.getPassword();
           assertEquals("password",pass);
           
         }

}
