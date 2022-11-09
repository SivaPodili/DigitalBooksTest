package com.profile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
@SpringBootTest(
		  properties = {"spring.cloud.config.enabled=false"}
		)
class SearchProfileServiceTest {

	@Autowired
	SearchProfileService searchProfileService;
	@MockBean
	ProfileRepository profileRepository;

	 @Test
	 public void searchbyAssociateNameTest(){
		 Profile profile=new Profile();
		 profile.setAssociateName("sivapodili");
		 String name=profile.getAssociateName();
	    	List<Profile> list=new ArrayList<>();
	    	 when(profileRepository.findByAssociateName("sivapodili")).thenReturn(list);
	    	 List<Profile> list1 = searchProfileService.findByAssociateName(name);
	    	 assertEquals(list1,list);


}

	 @Test
		public void searchProfileNullTest() {
		String associateName=null;
			boolean exceptionOccured=false;
			try {
				searchProfileService.findByAssociateName(associateName);
			}catch(Exception e) {
				exceptionOccured=true;
			}
			assertTrue(exceptionOccured);


		}

}
