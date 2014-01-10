package org.emath.model.book;

public class AssignmentData {

	private int type;
	private int level;

	public AssignmentData() {

	}

	public AssignmentData(int type, int level) {
		this.type = type;
		this.level = level;
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

}
