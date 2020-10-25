package com.facebook.api.exceptions;

public class FacebookAPIException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacebookAPIException(String message) {
		super(message);
	}
}
