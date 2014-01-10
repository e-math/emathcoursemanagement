package org.emath.model.book;

public class ExampleBoxData extends BookContent {

	public ExampleBoxData() {

	}

	public ExampleBoxData(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public ExampleBoxData(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	@Override
	public String toString() {
		return "<div " + this.getAttributes() + "><pre>" + this.getContent()
				+ "</pre></div>";
	}

}
