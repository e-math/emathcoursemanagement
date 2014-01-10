package org.emath.model.communication;

import java.util.Date;

/**
 * This class is used to create updates to dummy books.
 *
 */
public class DummySystemUpdate {

	private int id;
	/**
	 * The data content of the update.
	 */
	private String data;
	/**
	 * The version of dummy book that this update is for. 
	 */
	private String bookVersion;
	
	/** System update for teacher or student book or all. Use DummySystemUpdate.TEACHER, 
	 * DummySystemUpdate.STUDENT or DummySystemUpdate.ALL. */
	private int type;
	
	/**
	 * The time when this DummySystemUpdate was saved.
	 */
	private Date timestamp;
	
	/**
	 * Teacher type.
	 */
	public static final int TEACHER = 0;
	/**
	 * Student type.
	 */
	public static final int STUDENT = 1;
	/**
	 * All type.
	 */
	public static final int ALL = 2;

	public DummySystemUpdate(String data, int type) {
		this.data = data;
		this.type = type;
	}

	public DummySystemUpdate() {
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getBookVersion() {
		return bookVersion;
	}

	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

}
