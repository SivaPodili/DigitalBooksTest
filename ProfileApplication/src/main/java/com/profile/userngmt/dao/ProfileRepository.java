package com.profile.userngmt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.model.Profile;
import com.profile.model.ProfileRequest;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Profile save(ProfileRequest requesProfile);

	List<Profile> findByAssociateName(String associateName);
	

}
