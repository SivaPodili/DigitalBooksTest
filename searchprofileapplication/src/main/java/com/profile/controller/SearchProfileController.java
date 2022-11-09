package com.profile.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.profile.common.Path;
import com.profile.model.Profile;
import com.profile.service.SearchProfileService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Path.AUTH_PATH)
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
	@GetMapping(Path.SEARCHPROFILE_PATH_V1)
	@ResponseBody
	public List<Profile> searchProfiles(@RequestParam String associateName) {
		Preconditions.checkArgument(associateName!=null,AssocNameNotEmpty);
		logger.info(insideSPController);
		return searchProfileService.findByAssociateName(associateName);

	}

}
