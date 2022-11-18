package com.profile.userngmt.common;

public class Path {
	public static final String BASE_PATH="/profiletracker";
	public static final String AUTH_PATH=BASE_PATH+"/api/auth";
	public static final String SIGNIN_PATH_V1="/v1/signin";
	public static final String SIGNUP_PATH_V1="/v1/signup";
	public static final String USER_CONTROLLER_PATH="/profiletracker/api/auth/**";
	public static final String TEST_CONTROLLER_PATH="/profiletracker/api/auth/**";

	private Path() {

	}
}
