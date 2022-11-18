package com.profile.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.profile.model.Profile;
import com.profile.service.SearchProfileService;

@SpringBootTest(
		  properties = {"spring.cloud.config.enabled=false"}
		)
class SearchProfileControllerTest {

	@Autowired
	SearchProfileController searchProfileController;
	@MockBean
	SearchProfileService searchProfileService;

//	 @Test
//	 void searchbyAssociateNameTest(){
//		 Profile profile=new Profile();
//		 profile.setAssociateName("sivapodili");
//		 String name=profile.getAssociateName();
//	    	List<Profile> list=new ArrayList<>();
//    	 when(searchProfileService.findByAssociateName("sivapodili")).thenReturn(list);
//	    	List<Profile> list1 = searchProfileController.searchProfiles(name);
//	    	 assertEquals(list1,list);
//
//
//}

//	 @Test
//		void searchProfileNullTest() {
//
//			String associateName=null;
//			boolean exceptionOccured=false;
//			try {
//				searchProfileController.searchProfiles(associateName);
//			}catch(Exception e) {
//				exceptionOccured=true;
//			}
//			assertTrue(exceptionOccured);
//
//
//		}


}
