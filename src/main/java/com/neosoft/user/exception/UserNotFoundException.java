package com.neosoft.user.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long userRecord) {
		System.out.println("No user found with ID "+userRecord);
	} 

}
