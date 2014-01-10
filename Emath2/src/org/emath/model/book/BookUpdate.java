package org.emath.model.book;

import java.util.Date;

/**
 * This class represents an update to a single e-book.
 *
 */
public class BookUpdate {

	private int id;
	/**
	 * The actual update.
	 */
	private String data;
	/**
	 * The book that this update is related to.
	 */
	private int bookId;

	/** Book update for teachers, students or to all. */
	private int type;

	/** The user i of the user who saved this BookUpdate. */
	private int userId;
	/**
	 * The time when this update was saved.
	 */
	private Date timestamp;
	/**
	 * The ip address of the client who saved this BookUpdate.
	 */
	private String clientIp;

	/**
	 * Language code if language specific update. Else null.
	 */
	private String lang;
	
	/**
	 * The title attribute of the element
	 */
	private String title;
	
	public static final int TEACHER = 0;

	public static final int STUDENT = 1;

	public static final int ALL = 2;
	

	public BookUpdate() {

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

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
