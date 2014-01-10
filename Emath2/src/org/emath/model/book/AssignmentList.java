package org.emath.model.book;

import java.util.LinkedHashSet;
import java.util.Set;

import org.w3c.dom.Node;

public class AssignmentList extends BookContent {

	private Set<Assignment> assignments = new LinkedHashSet<Assignment>();

	public AssignmentList() {

	}

	public AssignmentList(int id, String content, DivAttributes attributes) {
		super(id, content, attributes);
	}

	public AssignmentList(DivAttributes attributes, String content) {
		super(attributes, content);
	}

	public AssignmentList(Node node) {
		super(new DivAttributes(node.getAttributes()), node.getTextContent());
	}

	public Set<Assignment> getAssignments() {
		return this.assignments;
	}

	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}

	@Override
	public String toString() {
		String ret = "<div ";
		ret += this.getAttributes();
		ret += "><pre>&lt;&lt;assignmentAccordion ";
		for (Assignment a : assignments) {
			ret += a.getAttributes().getTitle() + " ";
		}
		return ret + "&gt;&gt;</pre></div>";
	}

	public void addAssignment(Assignment assignment) {
		this.assignments.add(assignment);
	}

}
