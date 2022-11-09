package com.profile.userngmt.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {
	@NotBlank
	@Size(min = 5, max = 30)
	private String username;

	@NotBlank
	@Size(min = 5, max = 30)
	private String password;

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
