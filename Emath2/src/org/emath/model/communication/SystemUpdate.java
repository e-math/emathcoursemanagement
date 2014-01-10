package org.emath.model.communication;

import java.util.Date;

/**
 * This class represents an update to the book framework and is not content related. This 
 * kind of update is intended for all e-books. The update can be for teachers book, 
 * students book or all books.
 *
 */
public class SystemUpdate {

	private int id;
	/**
	 * The content of the update.
	 */
	private String data;

	/** System update for teacher or student book or both. */
	private int type;

	/** Committer id. */
	private int userId;
	
	/**
	 * The date when the update was added.
	 */
	private Date timestamp;
	/**
	 * The ip of the user who saved the update.
	 */
	private String clientIp;
	/**
	 * Title attribute of this SystemUpdate.
	 */
	private String title;

	public static final int TEACHER = 0;

	public static final int STUDENT = 1;

	public static final int ALL = 2;

	public SystemUpdate(String data, int type) {
		this.data = data;
		this.type = type;
	}

	public SystemUpdate() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
