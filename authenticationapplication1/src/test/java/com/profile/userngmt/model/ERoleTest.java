package com.profile.userngmt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ERoleTest {

	@Test
	   public void eroleUserTest() {
	    assertEquals("USER", ERole.USER.toString());

	}
	
	@Test
	   public void eroleAdminTest() {
	    assertEquals("ADMIN", ERole.ADMIN.toString());

	}
	
	@Test
	   public void eroleUserNegativeTest() {
	    assertNotEquals("ADMIN", ERole.USER.toString());

	}
	
	@Test
	   public void eroleAdminNegativeTest() {
	    assertNotEquals("USER", ERole.ADMIN.toString());

	}

}
