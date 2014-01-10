package org.emath.model.communication;

import org.emath.model.book.Book;

/**
 * This class holds only information that the client may need to know about an e-book.
 *
 */
public class ClientBook {

	private int id;
	/**
	 * The name of the book.
	 */
	private String title;
	/**
	 * The id of the parent book if any or else null.
	 */
	private int parent;

	public ClientBook() {

	}

	public ClientBook(Book b) {
		setId(b.getId());
		setParent(b.getParentId());
		setTitle(b.getTitle());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
