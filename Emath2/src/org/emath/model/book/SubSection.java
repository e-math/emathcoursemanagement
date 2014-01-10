package org.emath.model.book;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class SubSection extends BookContent {

	private Set<BookContent> bookContents = new LinkedHashSet<BookContent>();

	public SubSection() {

	}

	public SubSection(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public SubSection(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public void addBookContent(BookContent bookContent) {
		this.bookContents.add(bookContent);
	}

	public Set<BookContent> getBookContents() {
		return this.bookContents;
	}

	public void setBookContents(Set<BookContent> bookContents) {
		this.bookContents = bookContents;
	}

	public String getHtmlElementWithFullContent() {
		String ret = "<div " + this.getAttributes().toString() + ">";
		Iterator<BookContent> iterator = this.getBookContents().iterator();
		while (iterator.hasNext()) {
			ret += iterator.next().toString();
		}
		ret += "</div>";
		return ret;

	}

	@Override
	public String toString() {
		return "<div " + this.getAttributes().toString() + "><pre>"
				+ this.getContent() + "</pre></div>";
	}

}
