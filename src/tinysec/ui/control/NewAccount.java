package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import tinysec.TSWindow;
import tinysec.entity.Account;
import tinysec.entity.Group;
import tinysec.ui.controls.Navigator;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.GroupNode;
import tinysec.ui.views.NewAccountView;

public class NewAccount extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			TSWindow instance = TSWindow.getInstance();
			Navigator nav = instance.getNavMenuView().getNavigator();
			DefaultNode node = (DefaultNode) nav.getNavTree().getCurrentSelectedNode();
			Object userObj = node.getUserObject();
			if (userObj instanceof GroupNode) {
				GroupNode parentGroupTN = (GroupNode) userObj;
				Group parentGroup = parentGroupTN.getAssociatedGroup();
				NewAccountView naw = new NewAccountView(parentGroup);
				naw.setLocationRelativeTo(instance);
				naw.setVisible(true);
				if (naw.getDialogResult()) {
					Account account = naw.getCreatedAccount();
					nav.addAccountTN(node, account);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
