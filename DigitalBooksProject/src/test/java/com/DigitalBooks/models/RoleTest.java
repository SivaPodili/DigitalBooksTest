package com.DigitalBooks.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoleTest {
	
	
	
	
	@Test
	public void idTest() {
		Role role= new Role();
		role.setId(1);
		int id=role.getId();
		assertEquals(1,id);
		
	}
	
//	@Test
//	public void EroleTest() {
//		Role role= new Role();
//		ERole erole=Mockito.mock(ERole.class);
//		role.setName(ERole.ROLE_AUTHOR);
//		erole=role.getName();
//		assertEquals(ERole.ROLE_AUTHOR, erole);
//		
//	}

}
