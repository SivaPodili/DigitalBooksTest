package com.profile.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.profile.common.Path;
import com.profile.model.Profile;
import com.profile.service.SearchProfileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RefreshScope

public class SearchProfileController {

	private static final Logger logger =  LogManager.getLogger(SearchProfileController.class);

	@Autowired
	SearchProfileService searchProfileService;

	@Value("${inside.searchprofile.controller}")
	String insideSPController;

	@Value("${associatename.cannot.empty}")
	String AssocNameNotEmpty;

	/**
	 * searchProfiles method is used to search the profile based on associate name
	 * @param associateName
	 * @return
	 * returns list of profiles based on associate name.
	 */
	
	@ApiOperation(value="searchProfile",notes="Search profile method is used to search profiles based on Associate name", nickname="searchProfile")
	
	@ApiResponses(value = {
			
	        @ApiResponse(code = 500, message = "Unable to Search Profile"),
	         @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 200, message = "User Successfully Get the list of Associates details based on Associate Name",
	        response = SearchProfileController.class, responseContainer = "List"
	           ) })
	
	@GetMapping(Path.SEARCHPROFILE_PATH_V1)
	@ResponseBody
	public List<Profile> searchProfiles(@RequestParam("associateName") String associateName) {
		Preconditions.checkArgument(associateName!=null,AssocNameNotEmpty);
		logger.info(insideSPController);
		return searchProfileService.findByAssociateName(associateName);

	}
	


}


