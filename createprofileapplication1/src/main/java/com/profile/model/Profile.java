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
@Table(name = "create_profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@NotBlank
	@Size(min = 5, max = 30)
	private String associateName;

	@NotBlank
	@Size(min = 5, max = 30)
	private String associateId;

	@NotBlank
	@Size(max = 13)
	private String mobile;

	@NotBlank
	@Size(min = 5, max = 30)
	@Email
	private String email;

	public String getAssociateId() {
		return associateId;
	}

	public String getAssociateName() {
		return associateName;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public int getUserId() {
		return userId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
