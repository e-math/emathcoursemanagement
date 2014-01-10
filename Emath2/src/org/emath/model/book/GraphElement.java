package org.emath.model.book;

public class GraphElement extends BookContent {

	public GraphElement() {

	}

	public GraphElement(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public GraphElement(DivAttributes attributes, String content) {
		super(attributes, content);
	}

}
