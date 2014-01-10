package org.emath.model.book;

import java.util.LinkedHashSet;
import java.util.Set;

public class Section extends BookContent {

	private Set<SubSection> subSections = new LinkedHashSet<SubSection>();

	public Section() {

	}

	public Section(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public Section(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public Set<SubSection> getSubSections() {
		return subSections;
	}

	public void setSubSections(Set<SubSection> subSections) {
		this.subSections = subSections;
	}

	public void addSubSection(SubSection subSection) {
		subSections.add(subSection);
	}

	@Override
	public String toString() {
		return "<div " + this.getAttributes().toString() + "><pre>"
				+ this.getContent() + "</pre></div>";
	}

}
