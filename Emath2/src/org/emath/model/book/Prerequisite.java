package org.emath.model.book;

public class Prerequisite extends BookContent {

	private PrerequisiteData data;

	public Prerequisite() {

	}

	public Prerequisite(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public Prerequisite(DivAttributes attributes, String content) {
		super(attributes, content);

	}

	public PrerequisiteData getData() {
		return data;
	}

	public void setData(PrerequisiteData data) {
		this.data = data;
	}

}
