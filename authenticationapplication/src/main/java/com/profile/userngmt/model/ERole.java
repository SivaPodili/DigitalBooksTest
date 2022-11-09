package com.profile.userngmt.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
public enum ERole {
	USER("USER"),
	ADMIN("ADMIN");

	@Value("${no.role}")
	static String noRole;

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

	        throw new IllegalArgumentException(noRole + textValue);
	    }

}
