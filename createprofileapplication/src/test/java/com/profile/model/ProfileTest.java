package com.profile.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProfileTest {
	
	Profile profile =new Profile();

	@Test
	void UserIdTest() {
		profile.setUserId(1);
		int id=profile.getUserId();
		assertEquals(1,id);
		
	}
	
	@Test
	void associateIdTest() {
		profile.setAssociateId("CTS-00010");
		String associateId=profile.getAssociateId();
		assertEquals("CTS-00010",associateId);
		
	}
	
	@Test
	void associateNameTest() {
		profile.setAssociateName("sivapodili");
		String associateName=profile.getAssociateName();
		assertEquals("sivapodili",associateName);
		
	}
	
	@Test
	void mobileTest() {
		profile.setMobile("8987678988");
		String mobile=profile.getMobile();
		assertEquals("8987678988",mobile);
		
	}
	
	@Test
	void emailTest() {
		profile.setEmail("siva@gmail.com");
		String email=profile.getEmail();
		assertEquals("siva@gmail.com",email);
		
	}
}
