package org.emath.model.book;

import java.util.Date;

public class AssignmentSolution {

	private int id;

	private int courseId;

	private int userId;

	private String data;

	private boolean isPublic;

	private Date timestamp;

	private String clientIp;

	private String solutionTo;

	public AssignmentSolution(int courseId, int userId, String data) {
		this.courseId = courseId;
		this.userId = userId;
		this.data = data;
	}

	public AssignmentSolution() {

	}

	public void setId(int id) {
		this.id = id;

	}

	public int getId() {
		return id;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isIsPublic() {
		return isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getSolutionTo() {
		return solutionTo;
	}

	public void setSolutionTo(String solutionTo) {
		this.solutionTo = solutionTo;
	}

}
