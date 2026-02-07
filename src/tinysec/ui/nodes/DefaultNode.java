package tinysec.ui.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

public class DefaultNode extends DefaultMutableTreeNode {
	public DefaultNode(Object node) {
		super(node);
		super.setUserObject(node);
	}
}
