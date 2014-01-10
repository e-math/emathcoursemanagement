package org.emath.model.book;

import com.google.gson.Gson;


public class Assignment extends BookContent {

	private int type;
	private int level;

	public Assignment() {

	}

	public Assignment(int id, String content, DivAttributes attributes,
			int type, int level) {
		super(id, content, attributes);
		this.type = type;
		this.level = level;
	}

	public Assignment(DivAttributes attributes, String content, String jsonData) {
		super(attributes, content);
		AssignmentData data = new Gson().fromJson(jsonData,
				AssignmentData.class);
		type = data.getType();
		level = data.getLevel();
	}

	public Assignment(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setAssignmentTypeAndLevelFromJson(String jsonData) {
		AssignmentData d = (new Gson().fromJson(jsonData, AssignmentData.class));
		this.setLevel(d.getLevel());
		this.setType(d.getType());
	}

	@Override
	public String toString() {
		return "<div id=\"" + getId() + "\" " + this.getAttributes() + "><pre>"
				+ this.getContent() + "<data>"
				+ new Gson().toJson(new AssignmentData(type, level))
				+ "</data></pre></div>";
	}

}
