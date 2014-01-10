package org.emath.model.communication;

import java.util.Date;

/**
 * This class is used to log all traffic between the client and the server. All successfully
 * validated requests are logged.
 *
 */
public class TrafficLogEntry {

	private int id;
	/**
	 * The ip address of the client.
	 */
	private String clientIp;
	/**
	 * The user id of the client.
	 */
	private int userId;
	/**
	 * The time when the request was received.
	 */
	private Date timestamp;
	/**
	 * Additional information about the request.
	 */
	private String extraParams;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(String extraParams) {
		this.extraParams = extraParams;
	}

}
