package org.emath.model.book;

public abstract class BookContent {
	private int id;
	private String content;
	private DivAttributes attributes;

	public BookContent() {
	}

	public BookContent(int id, String content, DivAttributes attributes) {
		this.id = id;
		this.content = content;
		this.attributes = attributes;
	}

	public BookContent(DivAttributes attributes, String content) {
		this.setAttributes(attributes);
		this.setContent(content);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DivAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(DivAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return id + content + attributes;
	}

}
