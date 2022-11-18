package com.profile.userngmt.payload.response;



public class MessageResponse<V> {
	private V message;
	private int errorcode;

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public V getMessage() {
		return message;
	}

	public void setMessage(V message) {
		this.message = message;
	}

	public MessageResponse(V message, int errorcode) {
		super();
		this.message = message;
		this.errorcode = errorcode;
	}

	public MessageResponse() {
		super();
	}



}
