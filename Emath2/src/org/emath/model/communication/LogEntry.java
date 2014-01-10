package org.emath.model.communication;

import java.util.Date;

/**
 * Represents a error log entry.
 *
 */
public class LogEntry {
	/**
	 * Illegal system keywords were found in the request.
	 */
	public static final int ILLEGAL_DATA_SUBMITTED = 1;
	
	/**
	 * No user was found with matching username and password.
	 */
	public static final int USER_VALIDATION_FAILED = 2;
	
	@Deprecated
	/**
	 * This class is deprecated in favor of TrafficLogEntry.class that is now used to
	 * log the traffic into a seperate traffic log.
	 */
	public static final int REQUEST_STRING = 3;
	
	/**
	 * The user is not in the course that was in the request.
	 */
	public static final int COURSE_VALIDATION_FAILED = 4;

	private int id;
	
	/**
	 * Type of entry or reason to log this request.
	 */
	private int type;
	/**
	 * The id of the user who sent this request or -1 if no user could be matched.
	 */
	private int userId;
	/**
	 * The id of the course that was received or -1 if the user is not in the course.
	 */
	private int courseId;
	/**
	 * The data that was received from the user.
	 */
	private String data;
	
	/**
	 * The ip address of the client.
	 */
	private String clientIp;
	/**
	 * Additional information about the client. 
	 */
	private String extraParams;

	/**
	 * The time when this request was received.
	 */
	private Date timestamp;

	public LogEntry() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(String extraParams) {
		this.extraParams = extraParams;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
