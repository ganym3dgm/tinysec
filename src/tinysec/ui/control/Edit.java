package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import tinysec.TSWindow;
import tinysec.entity.Account;
import tinysec.entity.Note;
import tinysec.ui.controls.Navigator;
import tinysec.ui.nodes.AccountNode;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.NoteNode;
import tinysec.ui.views.EditAccountView;
import tinysec.ui.views.EditNoteView;

public class Edit extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			TSWindow instance = TSWindow.getInstance();
			Navigator nav = instance.getNavMenuView().getNavigator();
			DefaultNode currentNode = (DefaultNode) nav.getNavTree().getCurrentSelectedNode();
			Object userObj = currentNode.getUserObject();
			if (userObj instanceof AccountNode) {
				this.editAccount(nav, userObj, currentNode);
			}
			if (userObj instanceof NoteNode) {
				this.editNote(nav, userObj, currentNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void editAccount(Navigator nav, Object userObj, DefaultNode node) {
		try {
			Account account;
			TSWindow instance = TSWindow.getInstance();
			AccountNode oldAccountTN = (AccountNode) userObj;
			Account oldAccount = oldAccountTN.getAAccount();
			EditAccountView eaw = new EditAccountView(oldAccount);
			eaw.setLocationRelativeTo(instance);
			eaw.setVisible(true);
			if (eaw.getDialogResult() && (account = eaw.getNewAccount()) != null) {
				nav.getNavTree().removeCurrentNode();
				DefaultNode parentGTN = oldAccountTN.getParentGTN();
				AccountNode newAccounTN = new AccountNode(parentGTN, account);
				nav.getNavTree().addAccountNode(parentGTN, newAccounTN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void editNote(Navigator nav, Object userObj, DefaultNode currentNode) {
		try {
			Note note;
			TSWindow instance = TSWindow.getInstance();
			NoteNode oldNoteTN = (NoteNode) userObj;
			Note oldNote = oldNoteTN.getANote();
			EditNoteView eaw = new EditNoteView(oldNote);
			eaw.setLocationRelativeTo(instance);
			eaw.setVisible(true);
			if (eaw.getDialogResult() && (note = eaw.getNewNote()) != null) {
				nav.getNavTree().removeCurrentNode();
				DefaultNode parentGTN = oldNoteTN.getParentGTN();
				NoteNode newNoteTN = new NoteNode(parentGTN, note);
				nav.getNavTree().addNoteNode(parentGTN, newNoteTN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
