package org.emath.model.book;

import com.google.gson.Gson;

public class SettingsEbook extends BookContent {
	private EbookContentTree contentTree;
	private Gson gson = new Gson();

	public SettingsEbook() {
	}

	public SettingsEbook(DivAttributes attributes, String content) {
		super(attributes, content);

	}

	public EbookContentTree getContentTree() {
		return contentTree;
	}

	public void buildContentTreeFromJson(String json) {
		this.contentTree = gson.fromJson(json, EbookContentTree.class);
	}

}
