package com.profile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;


@SpringBootTest( 
		  properties = {"spring.cloud.config.enabled=false"}
		)
class CreateProfileServiceTest {

	@Autowired
	CreateProfileService createProfileService;
	
	@MockBean
	ProfileRepository profileRepository;
	
	@Test
	public void CreateProfileServicePositiveTest() {
		
		Profile profile=new Profile();
		profile.setAssociateName("siva");
		
		when(profileRepository.save(any(Profile.class))).thenReturn(profile);
		MessageResponse<String> entity=createProfileService.createProfileService(profile);
		assertThat(entity.equals(profile));
		
		
	}
	
	@Test
	public void CreateProfileServiceNullTest() {
		Profile profile=null;
		boolean exceptionOccured=false;
		try {
		MessageResponse<String> entity=createProfileService.createProfileService(profile);
		}catch(Exception e) {
			exceptionOccured=true;
		}
		assertTrue(exceptionOccured);
		
		
	}

}
