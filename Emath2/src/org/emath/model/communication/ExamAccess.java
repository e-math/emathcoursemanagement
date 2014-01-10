package org.emath.model.communication;

import java.util.Date;

/**
 * This class is used to manage who can access exams.
 *
 */
public class ExamAccess {
	private int id;
	/**
	 * The user id of the user who gains access.
	 */
	private int userId;
	/**
	 * The exam that is granted access to.
	 */
	private int examId;
	/**
	 * The time when the right to access the exam will expire.
	 */
	private Date validUntil;
	
	
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
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public Date getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

}
