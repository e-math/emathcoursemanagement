package org.emath.model.book;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents an e-book.
 *
 */
public class Book {

	private int id;
	/**
	 * The name of the book.
	 */
	private String title;
	/**
	 * id of the parent book. For example the parent of a student book is the
	 * teacher book.
	 */
	private int parentId; // NOT IN USE

	/** All the original content. */
	private Set<Chapter> chapters = new LinkedHashSet<Chapter>();

	/**
	 * Content that teacher or student has added to the book (Content, comments,
	 * etc.).
	 */
	private Set<AddedContent> addedContent = new LinkedHashSet<AddedContent>(); // WHY
																				// NOT
																				// IN
																				// HIBERNATE
																				// MAPPING?

	public Book() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}

	public void addChapter(Chapter chapter) {
		chapters.add(chapter);
	}

	public Set<AddedContent> getAddedContent() {
		return addedContent;
	}

	public void setAddedContent(Set<AddedContent> addedContent) {
		this.addedContent = addedContent;
	}

	public void addAddedContent(AddedContent content) {
		addedContent.add(content);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
