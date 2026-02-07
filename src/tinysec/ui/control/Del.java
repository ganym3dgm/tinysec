package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tinysec.TSWindow;
import tinysec.bl.EntryManager;
import tinysec.entity.Account;
import tinysec.entity.Group;
import tinysec.entity.Note;
import tinysec.ui.controls.Navigator;
import tinysec.ui.nodes.AccountNode;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.GroupNode;
import tinysec.ui.nodes.NoteNode;

public class Del extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			EntryManager instance = EntryManager.getInstance();
			Navigator nav = TSWindow.getInstance().getNavMenuView().getNavigator();
			DefaultNode currentNode = nav.getCurrentNode();
			if (currentNode != null) {
				Object userObj = currentNode.getUserObject();
				if (userObj instanceof GroupNode) {
					this.removeGroup(instance, nav, userObj, currentNode);
				}
				if (userObj instanceof AccountNode) {
					this.removeAccount(instance, nav, userObj, currentNode);
				}
				if (userObj instanceof NoteNode) {
					this.removeNote(instance, nav, userObj, currentNode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int confirm(int id) {
		switch (id) {
		case 1: {
			return JOptionPane.showConfirmDialog(new JFrame(),
					"Are you sure you want to delete the Selected Group, and all Entries inside it?",
					"Confirm Deletion!", 0, 3);
		}
		case 2: {
			return JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete the Selected Account?",
					"Confirm Deletion!", 0, 3);
		}
		case 3: {
			return JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete the Selected Note?",
					"Confirm Deletion!", 0, 3);
		}
		}
		return 0;
	}

	private void removeAccount(EntryManager instance, Navigator nav, Object userObj, DefaultNode currentNode) {
		Account account = ((AccountNode) userObj).getAAccount();
		int result = this.confirm(2);
		if (result == 0 && instance.delAccount(account)) {
			nav.removeNode(currentNode);
			try {
				TSWindow.getInstance().getTa_PrincipalInfos().setText("");
				TSWindow.getInstance().getStatus().setText("TinySec ready...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void removeGroup(EntryManager instance, Navigator nav, Object userObj, DefaultNode currentNode) {
		Group group = ((GroupNode) userObj).getAssociatedGroup();
		int result = this.confirm(1);
		if (result == 0 && instance.delGroup(group)) {
			nav.removeNode(currentNode);
		}
	}

	private void removeNote(EntryManager instance, Navigator nav, Object userObj, DefaultNode currentNode) {
		Note note = ((NoteNode) userObj).getANote();
		int result = this.confirm(3);
		if (result == 0 && instance.delNote(note)) {
			nav.removeNode(currentNode);
			try {
				TSWindow.getInstance().getTa_PrincipalInfos().setText("");
				TSWindow.getInstance().getStatus().setText("TinySec ready...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
