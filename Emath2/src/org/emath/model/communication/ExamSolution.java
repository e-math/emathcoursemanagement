package org.emath.model.communication;

import java.util.Date;

/**
 * These are the answers to an exam by a user. 
 *
 */
public class ExamSolution {
	private int id;
	/**
	 * The time when the solutions were received.
	 */
	private Date timestamp;
	/**
	 * The answers.
	 */
	private String data;
	/**
	 * The user id of the user who sent the answers.
	 */
	private int userId;
	/**
	 * The id of the exam, that was taken.
	 */
	private int examId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
