package com.profile.userngmt.model;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class UserTest {

User author=new User();
@Test	
public void idTest() {
	author.setId(1L);
	Long id=author.getId();
	assertThat(1L).isEqualTo(id);
}
	
	@Test
	public void userNameTest() {
		author.setUsername("siva");
		String name=author.getUsername();
		assertEquals("siva",name);
		
		
	}
	
	@Test
	public void emailTest() {
		author.setEmail("siva@gmail.com");
		String email=author.getEmail();
		assertEquals("siva@gmail.com",email);
		
		
	}
	
	@Test
	public void passwordTest() {
		author.setPassword("password");
		String pass=author.getPassword();
		assertEquals("password",pass);
		
		
	}
	
	@Test
	public void roleTest() {
		
		author.setRole("USER");
		String role =author.getRole();
		assertEquals("USER",role);
		
	}
	
	@Test
	public void ConstructorTest() {
		String username="siva";
		String email="siva@gmail.com";
		String password="password";
		
		User author1=new User(username,email,password);
		assertEquals(username, author1.getUsername());
		assertEquals(email, author1.getEmail());
		assertEquals(password, author1.getPassword());
		
		
	}

}
