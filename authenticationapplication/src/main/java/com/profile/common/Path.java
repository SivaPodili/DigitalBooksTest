package com.profile.common;

public class Path {
	public static final String BASE_PATH="/profiletracker";
	public static final String AUTH_PATH=BASE_PATH+"/api/auth";
	public static final String SIGNIN_PATH_V1="/v1/signin";
	public static final String SIGNUP_PATH_V1="/v1/signup";
	public static final String CREATEPROFILE_PATH_V1="/v1/createprofile";
	public static final String SEARCHPROFILE_PATH_V1="/v1/searchprofile";
	public static final String ALL_PATH="/all";
	public static final String USER_PATH="/user";
	public static final String ADMIN_PATH="/admin";
	public static final String USER_CONTROLLER_PATH="/profiletracker/api/auth/**";
	public static final String TEST_CONTROLLER_PATH="/profiletracker/api/auth/**";

	private Path() {
		
	}
}
