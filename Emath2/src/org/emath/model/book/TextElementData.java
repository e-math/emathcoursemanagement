package org.emath.model.book;


public class TextElementData extends BookContent {

	public TextElementData() {

	}

	public TextElementData(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public TextElementData(DivAttributes attributes, String content) {
		super(attributes, content);
	}

}