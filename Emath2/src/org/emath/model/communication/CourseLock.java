package org.emath.model.communication;

import java.util.Date;

/**
 * This class is used to prevent changes to a single course while a transaction is in progress.
 *
 */
public class CourseLock {

	private int id;
	/**
	 * The course that is being locked.
	 */
	private int courseId;
	/**
	 * The time when this lock was added.
	 */
	private Date timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
