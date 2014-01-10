package org.emath.model.book;

import java.util.Date;

/**
 * Any type of additional content that has been added to a book afterwards.
 *
 */
public class AddedContent {

	private int id;
	/**
	 * The title attribute of the content.
	 */
	private String title;
	/**
	 * The actual content.
	 */
	private String data;
	/**
	 * The id of the course.
	 */
	private int courseId;
	/**
	 * The user id of the user who saved this content.
	 */
	private int userId;
	/**
	 * If content is public, then also students can receive this content.
	 */
	private boolean isPublic;
	/**
	 * Type of content. All allowed types are listed in AddedContent.ContentType enum. 
	 */
	private int type;
	/**
	 * The time when this content was saved.
	 */
	private Date timestamp;
	/**
	 * The time when this content will be available to others if this.isIsPublic == true.
	 */
	private Date available;
	/**
	 * The ip address of the client who saved this content.
	 */
	private String clientIp;
	/**
	 * All allowed content types that exist.
	 */
	public enum ContentType {
		EXAMPLE(0, "example"), SD_MARKINGS(1, "sdmarkings"), HOME_ASSIGNMENTS(
				2, "homeassignments"), MODEL_SOLUTION(3, "modelsolution"), ASSIGNMENT(
				4, "assignment"), TEXT(5, "text"), THEORY(6, "theory");

		private final int intValue;

		private final String stringValue;

		ContentType(int intValue, String stringValue) {
			this.intValue = intValue;
			this.stringValue = stringValue;
		}

		public int getIntValue() {
			return intValue;
		}

		public String getStringValue() {
			return stringValue;
		}
	}

	public AddedContent() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean isIsPublic() {
		return isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Date getAvailable() {
		return available;
	}

	public void setAvailable(Date available) {
		this.available = available;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
