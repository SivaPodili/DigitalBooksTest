package com.profile.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProfileRequestTest {

	ProfileRequest profilerequest =new ProfileRequest();

	@Test
	public void UserIdTest() {
		profilerequest.setUserId(1);
		int id=profilerequest.getUserId();
		assertEquals(1,id);

	}

	@Test
	public void associateIdTest() {
		profilerequest.setAssociateId("CTS-00010");
		String associateId=profilerequest.getAssociateId();
		assertEquals("CTS-00010",associateId);

	}

	@Test
	public void associateNameTest() {
		profilerequest.setAssociateName("sivapodili");
		String associateName=profilerequest.getAssociateName();
		assertEquals("sivapodili",associateName);

	}

	@Test
	public void mobileTest() {
		profilerequest.setMobile("8987678988");
		String mobile=profilerequest.getMobile();
		assertEquals("8987678988",mobile);

	}

	@Test
	public void emailTest() {
		profilerequest.setEmail("siva@gmail.com");
		String email=profilerequest.getEmail();
		assertEquals("siva@gmail.com",email);

	}

	@Test
	public void toString_userIdTest()
	{
		String toString = profilerequest.toString();
	    assertTrue(toString.contains("userId=" + profilerequest.getUserId()));

	}

	@Test
	public void toString_associateNameTest()
	{
		String toString = profilerequest.toString();
	    assertTrue(toString.contains("associateName=" + profilerequest.getAssociateName()));

	}

	@Test
	public void toString_associateIdTest()
	{
		String toString = profilerequest.toString();
	    assertTrue(toString.contains("associateName=" + profilerequest.getAssociateId()));

	}

}
