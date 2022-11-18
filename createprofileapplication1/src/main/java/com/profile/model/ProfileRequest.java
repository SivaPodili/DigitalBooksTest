package com.profile.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProfileRequest {

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

	@Override
	public String toString() {
		return "ProfileRequest [userId=" + userId + ", associateName=" + associateName + ", associateId=" + associateId
				+ ", mobile=" + mobile + ", email=" + email + "]";
	}

}
