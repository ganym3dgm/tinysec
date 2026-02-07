package tinysec.ui.nodes;

import tinysec.entity.Group;

public class GroupNode {
	Group associatedGroup;
	private String title;

	public GroupNode(Group group) {
		this.associatedGroup = group;
		this.title = group.toString();
	}

	public Group getAssociatedGroup() {
		return this.associatedGroup;
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
