package com.profile.userngmt.model;

import com.profile.common.Constants;

public enum ERole {
	USER("USER"),
	ADMIN("ADMIN");
	
	public String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private ERole(String name) {
		this.setName(name);
	}
	
	
	 public static ERole textValueOf(String textValue){

	        for(ERole value : values()) {
	            if(value.name.equals(textValue)) {
	                return value;
	            }
	        }

	        throw new IllegalArgumentException(Constants.NO_ROLE + textValue);  
	    }   
	
}
