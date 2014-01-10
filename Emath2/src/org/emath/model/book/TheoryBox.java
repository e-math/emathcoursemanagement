package org.emath.model.book;


public class TheoryBox extends BookContent {

	private TheoryBoxData data;

	public TheoryBox() {

	}

	public TheoryBox(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public TheoryBox(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public TheoryBoxData getData() {
		return data;
	}

	public void setData(TheoryBoxData data) {
		this.data = data;
	}

}