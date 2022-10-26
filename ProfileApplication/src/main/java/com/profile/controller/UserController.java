package com.profile.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.profile.model.Profile;
import com.profile.model.ProfileRequest;

//import com.profile.model.Profile;

import com.profile.payload.response.JwtResponse;
import com.profile.payload.response.MessageResponse;

import com.profile.security.jwt.JwtUtils;
import com.profile.userngmt.dao.UserDetailsServiceImpl;
import com.profile.userngmt.dao.UserRepository;
import com.profile.userngmt.model.ERole;
import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;
import com.profile.userngmt.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	//User Sign in
		@PostMapping("/signin")
		public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		}

		
	//User Sign up
		@PostMapping("/signup")
		public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
			
			ResponseEntity responseEntity;
			MessageResponse messageResponse=userDetailsServiceImpl.validSignupRequest(signUpRequest);
			
			if(messageResponse.getErrorcode()==400) {
				responseEntity=ResponseEntity.badRequest().body(messageResponse);
			}else {
				// Create new user's account
				User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));
				user.setRole(signUpRequest.getRole());

				userRepository.save(user);
			 responseEntity =ResponseEntity.ok(new MessageResponse<String>("User Registered Successfully",200));
				
			}

			return responseEntity;

		}
		
		//User can create profiles
		@PostMapping("/createprofile")
		public String createProfile(@RequestBody @Valid Profile profile) {
			userDetailsServiceImpl.createProfileService(profile);
			//System.out.println(profileRequest.toString());
			return "Profile is Successfully Created";
		}
		
		
		//Admin can Search Profiles
		@GetMapping("/searchprofile")
		@ResponseBody
		public List<Profile> SearchProfiles(@RequestParam String associateName) {
			List<Profile> profileList = userDetailsServiceImpl.findByName(associateName);
			return profileList;
		}

		
}
