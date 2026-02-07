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
import tinysec.entity.Account;
import tinysec.entity.Note;
import tinysec.ui.nodes.AccountNode;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.GroupNode;
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

		try {
			TSWindow instance = TSWindow.getInstance();
			this.currentSelectedNode = node;
			Object userObj = node.getUserObject();
			Navigator nav = instance.getNavMenuView().getNavigator();
			nav.rootClicked();
			if (userObj instanceof GroupNode) {
				nav.groupClicked();
				instance.getTa_PrincipalInfos().setText("");
				instance.getStatus().setText("Selected Group:'" + ((GroupNode) userObj).getTitle() + "'");
			}
			if (userObj instanceof AccountNode) {
				nav.aOrNoteClicked();
				AccountNode pTN = (AccountNode) userObj;
				Account account = pTN.getAAccount();
				if (account != null) {
					instance.getStatus().setText("Selected Account:'" + account.getTitle() + "'");
					instance.getTa_PrincipalInfos().setText(account.toString());
				}
			}
			if (userObj instanceof NoteNode) {
				nav.aOrNoteClicked();
				NoteNode nTN = (NoteNode) userObj;
				Note note = nTN.getANote();
				if (note != null) {
					instance.getStatus().setText("Selected Note:'" + note.getTitle() + "'");
					instance.getTa_PrincipalInfos().setText(note.toString());
				}
			} else if (userObj instanceof String) {
				nav.rootClicked();
				instance.getStatus().setText("TinySec ready...");
				instance.getTa_PrincipalInfos().setText("");
			}
		} catch (Exception ex) {

		}
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

	public void ResumeUpdates() {
		this.isSuspended = false;
	}

	public void scrollToAndSelect(DefaultNode node) {
		TreePath path = new TreePath(node.getPath());
		this.scrollPathToVisible(path);
		this.setSelectionPath(path);
		this.handleSelection(node);
	}

	public void SuspendUpdates() {
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
