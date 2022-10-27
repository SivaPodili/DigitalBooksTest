package com.profile.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(	name = "create_profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@NotBlank
	@Size(min=5, max = 30)
	private String associateName;

	@NotBlank
	@Size(min=5, max = 30)
	private String associateId;
	
	@NotBlank
	@Size(max = 13)
	private String mobile;
	
	@NotBlank
	@Size(min=5, max = 30)
	@Email
	private String email;
	
	
	//private List<String> skillSet;

//	public List<String> getSkillSet() {
//		return skillSet;
//	}
//
//	public void setSkillSet(List<String> skillSet) {
//		this.skillSet = skillSet;
//	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	

}
