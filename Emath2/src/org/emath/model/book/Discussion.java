package org.emath.model.book;

public class Discussion extends BookContent {

	private DiscussionData data;

	public Discussion() {

	}

	public Discussion(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public Discussion(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public DiscussionData getData() {
		return data;
	}

	public void setData(DiscussionData data) {
		this.data = data;
	}

}
