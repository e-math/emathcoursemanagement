package org.emath.model.book;


public class TextElement extends BookContent {

	private TextElementData data;

	public TextElement() {

	}

	public TextElement(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public TextElement(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public TextElementData getData() {
		return data;
	}

	public void setData(TextElementData data) {
		this.data = data;
	}

}