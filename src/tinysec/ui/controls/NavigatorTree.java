package tinysec.ui.controls;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import tinysec.TSWindow;
import tinysec.ui.nodes.AccountNode;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.NoteNode;

public class NavigatorTree extends JTree {
	private Object currentSelectedNode;
	private DefaultTreeModel dTModel;
	private boolean isSuspended = false;
	private JPopupMenu popupMenu;
	private DefaultNode rootNode;

	public NavigatorTree(DefaultTreeModel dTModel) {
		super(dTModel);
		this.rootNode = (DefaultNode) dTModel.getRoot();
		this.dTModel = dTModel;
		this.popupMenu = new JPopupMenu();
		this.popupMenu.setOpaque(true);
		this.popupMenu.setLightWeightPopupEnabled(true);
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!e.isPopupTrigger()) {
					NavigatorTree.this.verifyAndActivateView(e);
				}
			}
		});
	}

	public DefaultMutableTreeNode addAccountNode(DefaultNode parentNode, AccountNode account) {
		DefaultNode accountNode = new DefaultNode(account);
		this.insertSorted(parentNode, accountNode);
		this.scrollToAndSelect(accountNode);
		return parentNode;
	}

	public DefaultMutableTreeNode addGroupNode(DefaultNode groupNode) {
		this.insertSorted(this.rootNode, groupNode);
		this.scrollToAndSelect(groupNode);
		return groupNode;
	}

	public DefaultMutableTreeNode addNoteNode(DefaultNode aTNode, NoteNode nTNode) {
		DefaultNode noteNode = new DefaultNode(nTNode);
		this.insertSorted(aTNode, noteNode);
		this.scrollToAndSelect(noteNode);
		return aTNode;
	}

	public DefaultNode getCurrentNode() {
		TreePath tp = this.getSelectionPath();
		if (tp != null) {
			return (DefaultNode) tp.getLastPathComponent();
		}
		return null;
	}

	public Object getCurrentSelectedNode() {
		return this.currentSelectedNode;
	}

	public DefaultTreeModel getDTModel() {
		return this.dTModel;
	}

	public DefaultNode getRootNode() {
		return this.rootNode;
	}

	private void handleSelection(DefaultNode node) {
		if (this.isSuspended) {
			return;
		}

		this.currentSelectedNode = node;
		Navigator nav = TSWindow.getInstance().getNavMenuView().getNavigator();
		nav.select(node);
	}

	private void insertSorted(DefaultNode parent, DefaultNode child) {
		String childName = child.getUserObject().toString();
		int index = 0;
		for (; index < parent.getChildCount(); index++) {
			DefaultNode current = (DefaultNode) parent.getChildAt(index);
			if (childName.compareToIgnoreCase(current.getUserObject().toString()) < 0) {
				break;
			}
		}

		this.dTModel.insertNodeInto(child, parent, index);
	}

	public void removeCurrentNode() {
		DefaultNode currentNode;
		MutableTreeNode parent;
		TreePath currentSelection = this.getSelectionPath();
		if (currentSelection != null
				&& (parent = (MutableTreeNode) (currentNode = (DefaultNode) currentSelection.getLastPathComponent())
						.getParent()) != null) {
			this.dTModel.removeNodeFromParent(currentNode);
			return;
		}
		Toolkit.getDefaultToolkit().beep();
	}

	public void resumeUpdates() {
		this.isSuspended = false;
	}

	public void scrollToAndSelect(DefaultNode node) {
		TreePath path = new TreePath(node.getPath());
		this.scrollPathToVisible(path);
		this.setSelectionPath(path);
		this.handleSelection(node);
	}

	public void selectCurrentNode() {
		DefaultNode node = this.getCurrentNode();
		this.scrollToAndSelect(node);
	}

	public void suspendUpdates() {
		this.isSuspended = true;
	}

	private void verifyAndActivateView(MouseEvent e) {
		DefaultNode dfNode;
		TreePath path = this.getPathForLocation(e.getX(), e.getY());
		if (path != null && (dfNode = (DefaultNode) path.getLastPathComponent()) != null) {
			this.handleSelection(dfNode);
		}
	}
}
