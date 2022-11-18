package com.profile.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.service.CreateProfileService;

@SpringBootTest( 
		  properties = {"spring.cloud.config.enabled=false"}
		)
class CreateProfileControllerTest {



	@MockBean
	CreateProfileService createProfileService;
	@MockBean
	ProfileRepository profileRepository;
	
	@Autowired
	CreateProfileController CreateProfileController;

	Profile mockValidProfile;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	 public void createProfilePositiveTest() {
		
		mockValidProfile=new Profile();
		mockValidProfile.setUserId(1);
		mockValidProfile.setAssociateId("CTS-00010");
		mockValidProfile.setAssociateName("siva");
		mockValidProfile.setMobile("8969786876");
		mockValidProfile.setEmail("siva@mail.com");
		 ResponseEntity entity= CreateProfileController.createProfile(mockValidProfile);
		 assertNotNull(entity);
		
		
		 }

	@Test
	 public void createProfileNagativeTest() {
		 Profile profile=new Profile();
		 profile.setUserId(1);
		 profile.setAssociateId("CTS-00010");
		 profile.setAssociateName("siva");
		 profile.setMobile("8969786876");
		 profile.setEmail("siva@mail.com");
		 MessageResponse<String> response=new MessageResponse<>();
		 when(createProfileService.createProfileService(profile)).thenReturn(response);
		 ResponseEntity entity= CreateProfileController.createProfile(profile);
		 assertNotNull(entity);
		
		
		 }
	
	@Test
	public void createProfileNullTest() {
		
		Profile profile=null;
		boolean exceptionOccured=false;
		try {
			ResponseEntity entity=CreateProfileController.createProfile(profile);
		}catch(Exception e) {
			exceptionOccured=true;
		}
		assertTrue(exceptionOccured);
		
		
	}

}
