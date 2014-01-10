package org.emath.model.book;

import java.util.LinkedHashSet;
import java.util.Set;

import org.w3c.dom.Node;

public class Chapter extends BookContent {

	private Set<Section> sections = new LinkedHashSet<Section>();
	private Set<BookContent> bookContents = new LinkedHashSet<BookContent>();

	public Chapter() {

	}

	public Chapter(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public Chapter(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public Chapter(Node node) {
		this.setContent(node.getTextContent());
		this.setAttributes(new DivAttributes(node.getAttributes()));
	}

	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
	}

	public void addSection(Section section) {
		sections.add(section);
	}

	public Set<BookContent> getBookContents() {
		return bookContents;
	}

	public void setBookContents(Set<BookContent> bookContents) {
		this.bookContents = bookContents;
	}

	@Override
	public String toString() {
		return "<div " + this.getAttributes().toString() + "><pre>"
				+ this.getContent() + "</pre></div>";
	}

}
