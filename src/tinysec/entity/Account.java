package tinysec.entity;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 8540795593239993379L;

	private Group asscociatedGroup;
	private String link;
	private String note;
	private String pwd;
	private String title;
	private String userID;

	public Account(String title, String userID, String pwd, String note, String link) {
		this.title = title;
		this.userID = userID;
		this.pwd = pwd;
		this.note = note;
		this.link = link;
	}

	public Group getAsscociatedGroup() {
		return this.asscociatedGroup;
	}

	public String getLink() {
		return this.link;
	}

	public String getNote() {
		return this.note;
	}

	public String getPwd() {
		return this.pwd;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUserID() {
		return this.userID;
	}

	public void setAsscociatedGroup(Group asscociatedGroup) {
		this.asscociatedGroup = asscociatedGroup;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		String info = "This Account includes following Informating:\n\nTitle: '" + this.title + "'\n\n" + "UserID: '"
				+ this.userID + "'\n\n" + "Password: '" + this.pwd + "'\n\n" + "Note: '" + this.note + "'\n\n"
				+ "Link: '" + this.link + "'";
		return info;
	}
}
