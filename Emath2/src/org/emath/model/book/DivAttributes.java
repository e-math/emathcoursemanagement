package org.emath.model.book;

import org.w3c.dom.NamedNodeMap;

public class DivAttributes {

	private int id;
	private String title;
	private String creator;
	private String created;
	private String modifier;
	private String modified;
	private String ebooktitle;
	private String tags;
	private String changecount;

	public DivAttributes() {

	}

	@Override
	public String toString() {
		String ret = "title=\"" + this.getTitle() + "\"";
		if (this.getCreator() != null) {
			ret += " creator=\"" + this.getCreator() + "\"";
		}
		if (this.getCreated() != null) {
			ret += " created=\"" + this.getCreated() + "\"";
		}
		if (this.getModifier() != null) {
			ret += " modifier=\"" + this.getModifier() + "\"";
		}
		if (this.getModified() != null) {
			ret += " modified=\"" + this.getModified() + "\"";
		}
		if (this.getEbooktitle() != null) {
			ret += " ebooktitle=\"" + this.getEbooktitle() + "\"";
		}
		if (this.getTags() != null) {
			ret += " tags=\"" + this.getTags() + "\"";
		}
		if (this.getChangecount() != null) {
			ret += " changecount=\"" + this.getChangecount() + "\"";
		}
		return ret;
	}

	public DivAttributes(NamedNodeMap attrs) {

		if (attrs.getNamedItem("title") != null) {
			this.setTitle(attrs.getNamedItem("title").getNodeValue());
		}
		if (attrs.getNamedItem("creator") != null) {
			this.setCreator(attrs.getNamedItem("creator").getNodeValue());
		}
		if (attrs.getNamedItem("created") != null) {
			this.setCreated(attrs.getNamedItem("created").getNodeValue());
		}
		if (attrs.getNamedItem("modifier") != null) {
			this.setModifier(attrs.getNamedItem("modifier").getNodeValue());
		}
		if (attrs.getNamedItem("modified") != null) {
			this.setModified(attrs.getNamedItem("modified").getNodeValue());
		}
		if (attrs.getNamedItem("ebooktitle") != null) {
			this.setEbooktitle(attrs.getNamedItem("ebooktitle").getNodeValue());
		}
		if (attrs.getNamedItem("changecount") != null) {
			this.setChangecount(attrs.getNamedItem("changecount")
					.getNodeValue());
		}
		if (attrs.getNamedItem("tags") != null) {
			this.setTags(attrs.getNamedItem("tags").getNodeValue());
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getEbooktitle() {
		return ebooktitle;
	}

	public void setEbooktitle(String ebooktitle) {
		this.ebooktitle = ebooktitle;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getChangecount() {
		return changecount;
	}

	public void setChangecount(String changecount) {
		this.changecount = changecount;
	}

}
