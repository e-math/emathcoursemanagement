package org.emath.model.book;

public class ExampleBox extends BookContent {

	private ExampleBoxData data;

	public ExampleBox() {

	}

	public ExampleBox(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public ExampleBox(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public ExampleBoxData getData() {
		return data;
	}

	public void setData(ExampleBoxData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "<div " + this.getAttributes() + "><pre>" + this.getContent()
				+ "</pre></div>";
	}

}
