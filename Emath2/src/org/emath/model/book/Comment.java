package org.emath.model.book;

import java.util.Date;

/**
 * This class represents a comment that was added to a e-book instance by the user.
 */
public class Comment {

	private int id;
	/**
	 * The title attribute of the Comment.
	 */
	private String title;
	/**
	 * The actual content of the Comment.
	 */
	private String data;
	/**
	 * The course id of the course that the user is attending.
	 */
	private int courseId;

	private int teacherId;

	private int studentId;

	private Date timestamp;

	private String clientIp;

	public Comment() {

	}

	public Comment(String title, String data, int courseId, int teacherId,
			int studentId) {
		this.title = title;
		this.data = data;
		this.courseId = courseId;
		this.teacherId = teacherId;
		this.studentId = studentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

}
