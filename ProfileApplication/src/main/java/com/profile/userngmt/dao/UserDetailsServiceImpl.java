package com.profile.userngmt.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profile.model.Profile;
import com.profile.model.ProfileRequest;
import com.profile.payload.response.MessageResponse;
import com.profile.userngmt.model.ERole;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;
import com.profile.userngmt.service.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
	
	public MessageResponse validSignupRequest(SignupRequest signUpRequest) {
		List<String> errors=new ArrayList<>();
		
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
			errors.add("Error: Username is already taken!");
		}
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			errors.add("Error: Email is already taken!");
		}
		
		
		try {
			ERole.textValueOf(signUpRequest.getRole());
		} catch (Exception e) {
			errors.add("Error: Invalid Role");
			
		}
		
		int errorcode=(errors.size()>0)?400:200;
		return new MessageResponse<List<String>>(errors,errorcode);
		
	}
	
	//User/Admin can create profile
	public Profile createProfileService(Profile Profile) {
		return profileRepository.save(Profile);

	}
	
	//Admin can Search Profile
	public List<Profile> findByName(String associateName) {
        return profileRepository.findByAssociateName(associateName);
    }
	
}
