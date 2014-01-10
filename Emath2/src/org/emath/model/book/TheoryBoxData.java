package org.emath.model.book;


public class TheoryBoxData extends BookContent {

	public TheoryBoxData() {

	}

	public TheoryBoxData(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public TheoryBoxData(DivAttributes attributes, String content) {
		super(attributes, content);
	}

}
