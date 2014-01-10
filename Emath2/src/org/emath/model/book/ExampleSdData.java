package org.emath.model.book;

public class ExampleSdData extends BookContent {

	public ExampleSdData() {

	}

	public ExampleSdData(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public ExampleSdData(DivAttributes attributes, String content) {
		super(attributes, content);
	}

}
