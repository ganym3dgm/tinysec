package tinysec.entity;

import java.io.Serializable;

public class Note implements Serializable {

	private static final long serialVersionUID = 6431406416497149937L;

	private Group associatedGroup;
	private String link;
	private String note;
	private String title;

	public Note(String title, String note, String link) {
		this.title = title;
		this.note = note;
		this.link = link;
	}

	public Group getAssociatedGroup() {
		return this.associatedGroup;
	}

	public String getLink() {
		return this.link;
	}

	public String getNote() {
		return this.note;
	}

	public String getTitle() {
		return this.title;
	}

	public void setAssociatedGroup(Group associatedGroup) {
		this.associatedGroup = associatedGroup;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		String info = "This Note includes following Informating:\n\nTitle: '" + this.title + "'\n\n" + "Note: '"
				+ this.note + "'\n\n" + "Link: '" + this.link + "'";
		return info;
	}
}
