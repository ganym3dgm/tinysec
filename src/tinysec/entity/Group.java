package tinysec.entity;

import java.io.Serializable;
import java.util.Vector;

public class Group implements Serializable {

	private static final long serialVersionUID = 991125420710522289L;

	private Vector entries = new Vector();
	private String title;

	public Group(String title) {
		this.title = title;
	}

	public void addAccount(Account account) {
		this.entries.addElement(account);
	}

	public void addNote(Note note) {
		this.entries.addElement(note);
	}

	public boolean delAccount(Account account) {
		return this.entries.removeElement(account);
	}

	public boolean delNote(Note note) {
		return this.entries.removeElement(note);
	}

	public Vector getEntries() {
		return this.entries;
	}

	public String getTitle() {
		return this.title;
	}

	public void setEntries(Vector entries) {
		this.entries = entries;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
