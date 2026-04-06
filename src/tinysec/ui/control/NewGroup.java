package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tinysec.TSWindow;
import tinysec.bl.EntryManager;
import tinysec.entity.Group;

public class NewGroup extends AbstractAction {
	public void actionPerformed(ActionEvent arg0) {
		String text = JOptionPane.showInputDialog(new JFrame(), "Group");
		if (text != null) {
			try {
				Group group = new Group(text);
				var nav = TSWindow.getInstance().getNavMenuView().getNavigator();
				if (EntryManager.getInstance().createGroup(group)) {
					nav.newGroup(new Group(text));
				}
				
				nav.focus();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
