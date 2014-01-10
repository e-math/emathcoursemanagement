package org.emath.model.communication;

import java.util.Date;
/**
 * This class is used to map the old state of content to the backup table when it is updates.
 *
 */
public class Backup {
	/**
	 * Type of content.
	 */
	public static final int TEACHER_CONTENT = 0;
	/**
	 * Type of content.
	 */
	public static final int SOLUTION = 1;
	/**
	 * Type of content.
	 */
	public static final int COMMENT = 2;

	private int id;
	/**
	 * The database id that was given to the original version of this content.
	 */
	private int originalContentId;
	/**
	 * Type of content that is being backed up.
	 */
	private int type;
	/**
	 * The actual content.
	 */
	private String data;
	/**
	 * The user id of the user who saved the content.
	 */
	private int userId;
	/**
	 * The course id of the course that this content is related to.
	 */
	private int courseId;
	/**
	 * The ip address of the client.
	 */
	private String clientIp;
	/**
	 * The time when this backup was saved.
	 */
	private Date timestamp;

	public Backup() {

	}

	public Backup(int type, int originalContentId, String data, int userId,
			int courseId, String clientIp) {
		super();
		this.originalContentId = originalContentId;
		this.type = type;
		this.data = data;
		this.userId = userId;
		this.clientIp = clientIp;
		this.courseId = courseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOriginalContentId() {
		return originalContentId;
	}

	public void setOriginalContentId(int originalContentId) {
		this.originalContentId = originalContentId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
