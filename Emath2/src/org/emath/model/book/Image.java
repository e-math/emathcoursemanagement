package org.emath.model.book;

public class Image extends BookContent {

	public Image() {

	}

	public Image(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public Image(DivAttributes attributes, String content) {
		super(attributes, content);
	}

}