package org.emath.model.book;

public class DiscussionData extends BookContent {

	public DiscussionData() {

	}

	public DiscussionData(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public DiscussionData(DivAttributes attributes, String content) {
		super(attributes, content);

	}

}
