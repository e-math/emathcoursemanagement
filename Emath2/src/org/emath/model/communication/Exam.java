package org.emath.model.communication;

import java.util.Date;

/**
 * This class represents an exam, that students can take.
 *
 */
public class Exam {
	private int id;
	/**
	 * The data content of the exam.
	 */
	private String data;
	/**
	 * The course where this exam is held.
	 */
	private int courseId;
	/**
	 * The type of exam.
	 */
	private int type;
	/**
	 * The name of this exam.
	 */
	private String name;
	/**
	 * The teachers that are holding the exam as text.
	 */
	private String teachers;
	/**
	 * The duration of the exam.
	 */
	private int duration;
	/**
	 * Presentation of this exam when the exam is not available.
	 */
	private String presentation;
	/**
	 * The time when the exam starts.
	 */
	private Date startTime;
	/**
	 * The amount of time that the students have to actually start the exam.
	 */
	private int registrationDuration;
	
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
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeachers() {
		return teachers;
	}
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPresentation() {
		return presentation;
	}
	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getRegistrationDuration() {
		return registrationDuration;
	}
	public void setRegistrationDuration(int registrationDuration) {
		this.registrationDuration = registrationDuration;
	}
	
}
