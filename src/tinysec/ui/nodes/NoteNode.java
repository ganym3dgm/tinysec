package tinysec.ui.nodes;

import tinysec.entity.Note;

public class NoteNode {
	private Note aNote;
	private DefaultNode parentGTN;
	private String title;

	public NoteNode(DefaultNode parentGTN, Note note) {
		this.aNote = note;
		this.title = note.getTitle();
		this.parentGTN = parentGTN;
	}

	public Note getANote() {
		return this.aNote;
	}

	public DefaultNode getParentGTN() {
		return this.parentGTN;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
