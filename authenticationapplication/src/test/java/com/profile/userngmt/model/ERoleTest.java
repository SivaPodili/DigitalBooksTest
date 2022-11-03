package com.profile.userngmt.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ERoleTest {

	@Test
	   public void eroleUserTest() {
		User erole = new User();
	    assertEquals("USER", ERole.USER.toString());

	}
	
	@Test
	   public void eroleAdminTest() {
		User erole = new User();
	    assertEquals("ADMIN", ERole.ADMIN.toString());

	}
	
	@Test
	   public void eroleUserNegativeTest() {
		User erole = new User();
	    assertNotEquals("ADMIN", ERole.USER.toString());

	}
	
	@Test
	   public void eroleAdminNegativeTest() {
		User erole = new User();
	    assertNotEquals("USER", ERole.ADMIN.toString());

	}

}
