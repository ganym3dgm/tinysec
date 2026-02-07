package tinysec.ui.controls;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import tinysec.ui.nodes.AccountNode;
import tinysec.ui.nodes.GroupNode;
import tinysec.ui.nodes.NoteNode;

public class TreeRenderer extends DefaultTreeCellRenderer {
	private final ImageIcon accountIcon = new ImageIcon(this.getClass().getResource("/icons/account.png"));
	private final ImageIcon groupsIcon = new ImageIcon(this.getClass().getResource("/icons/folder.png"));
	private final ImageIcon noteIcon = new ImageIcon(this.getClass().getResource("/icons/note.png"));
	private final ImageIcon rootIcon = new ImageIcon(this.getClass().getResource("/icons/tss.png"));

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object userObject = node.getUserObject();
		if (userObject != null) {
			if (node.getUserObject() instanceof GroupNode) {
				this.setIcon(this.groupsIcon);
				this.setToolTipText("Group");
			}
			if (node.getUserObject() instanceof AccountNode) {
				this.setIcon(this.accountIcon);
				this.setToolTipText("Account");
			}
			if (node.getUserObject() instanceof NoteNode) {
				this.setIcon(this.noteIcon);
				this.setToolTipText("Note");
			} else if (node.getUserObject() instanceof String) {
				this.setIcon(this.rootIcon);
				this.setToolTipText("TinySec");
			}
		}
		return this;
	}
}
