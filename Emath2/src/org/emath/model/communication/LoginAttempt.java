package org.emath.model.communication;

/**
 * This class is used to return information to the client about login attempts that failed.
 *
 */
public class LoginAttempt {
	
	/**
	 * The reason why login failed.
	 */
	private int errortype;
	/**
	 * An additional message to decryptify error messages on client-side.
	 */
	private String errormessage;
	/**
	 * User with received username and password doesn't exist.
	 */
	public static final int USER_VALIDATION_FAILED = 0;
	/**
	 * Given user is not in the course that was received.
	 */
	public static final int COURSE_VALIDATION_FAILED = 1;
	/**
	 * The book is not used in the course that is being logged on to.
	 */
	public static final int BOOK_VALIDATION_FAILED = 2;
		
	public LoginAttempt(int type, String message) {
		this.setErrortype(type);
		this.setErrormessage(message);	
	}

	public int getErrortype() {
		return errortype;
	}
	public void setErrortype(int errortype) {
		this.errortype = errortype;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

}
