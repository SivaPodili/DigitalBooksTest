package com.profile.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.profile.model.Profile;
import com.profile.model.ProfileRequest;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Profile save(ProfileRequest requesProfile);

	@Query("SELECT p FROM Profile p WHERE " +
            "p.associateName LIKE CONCAT('%',:associateName, '%')")
	List<Profile> findByAssociateName(String associateName);

	


}
