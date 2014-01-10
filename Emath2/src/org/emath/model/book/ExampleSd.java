package org.emath.model.book;

public class ExampleSd extends BookContent {

	private ExampleSdData data;

	public ExampleSd() {

	}

	public ExampleSd(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public ExampleSd(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public ExampleSdData getData() {
		return data;
	}

	public void setData(ExampleSdData data) {
		this.data = data;
	}

}
