package org.emath.model.book;

import java.util.List;

public class EbookContentTree {
	private BookTocs booktocs;
	private Config config;
	private boolean pagetwovisible;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public boolean isPagetwovisible() {
		return pagetwovisible;
	}

	public void setPagetwovisible(boolean pagenotvisible) {
		this.pagetwovisible = pagenotvisible;
	}

	public BookTocs getBooktocs() {
		return booktocs;
	}

	public void setBooktocs(BookTocs booktocs) {
		this.booktocs = booktocs;
	}

	public static class BookTocs {
		private Course pitMat_kurssi_1;

		public Course getCourse() {
			return pitMat_kurssi_1;
		}

		public void setCourse(Course pitMat_kurssi_1) {
			this.pitMat_kurssi_1 = pitMat_kurssi_1;
		}
	}

	public static class Config {
		private String datatiddler;
		private String userdatatiddler;
		private String commenttiddler;
		private String showcomments;
		private String updateURL;

		public String getDatatiddler() {
			return datatiddler;
		}

		public void setDatatiddler(String datatiddler) {
			this.datatiddler = datatiddler;
		}

		public String getUserdatatiddler() {
			return userdatatiddler;
		}

		public void setUserdatatiddler(String userdatatiddler) {
			this.userdatatiddler = userdatatiddler;
		}

		public String getCommenttiddler() {
			return commenttiddler;
		}

		public void setCommenttiddler(String commenttiddler) {
			this.commenttiddler = commenttiddler;
		}

		public String getShowcomments() {
			return showcomments;
		}

		public void setShowcomments(String showcomments) {
			this.showcomments = showcomments;
		}

		public String getUpdateURL() {
			return updateURL;
		}

		public void setUpdateURL(String updateURL) {
			this.updateURL = updateURL;
		}

	}

	public static class Course {
		private String bookid;
		private String title;
		private String style;
		private Frontpage frontpage;

		public String getBookid() {
			return bookid;
		}

		public void setBookid(String bookid) {
			this.bookid = bookid;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getStyle() {
			return style;
		}

		public void setStyle(String style) {
			this.style = style;
		}

		public Frontpage getFrontpage() {
			return frontpage;
		}

		public void setFrontpage(Frontpage frontpage) {
			this.frontpage = frontpage;
		}

	}

	public static class Frontpage {
		private String title;
		private String chapid;
		private String content;
		private List<BookChapter> chapters;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getChapid() {
			return chapid;
		}

		public void setChapid(String chapid) {
			this.chapid = chapid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<BookChapter> getBookChapters() {
			return chapters;
		}

		public void setChapters(List<BookChapter> chapters) {
			this.chapters = chapters;
		}
	}

	public static class BookChapter {
		private String title;
		private String chapid;
		private String content;
		private List<Subchapter> chapters;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getChapid() {
			return chapid;
		}

		public void setChapid(String chapid) {
			this.chapid = chapid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<Subchapter> getSubChapters() {
			return chapters;
		}

		public void setSubChapters(List<Subchapter> subChapters) {
			this.chapters = subChapters;
		}
	}

	public static class Subchapter {
		private String title;
		private String chapid;
		private String content;
		private List<SubsubChapters> chapters;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getChapid() {
			return chapid;
		}

		public void setChapid(String chapid) {
			this.chapid = chapid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<SubsubChapters> getSubSubChapters() {
			return chapters;
		}

		public void setChapters(List<SubsubChapters> chapters) {
			this.chapters = chapters;
		}

	}

	public static class SubsubChapters {
		private String title;
		private String chapid;
		private String content;
		private List<EmptyChapter> chapters;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getChapid() {
			return chapid;
		}

		public void setChapid(String chapid) {
			this.chapid = chapid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<EmptyChapter> getChapters() {
			return chapters;
		}

		public void setChapters(List<EmptyChapter> chapters) {
			this.chapters = chapters;
		}
	}

	public static class EmptyChapter {

	}

}