package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tinysec.TSWindow;
import tinysec.bl.EntryManager;
import tinysec.entity.Group;

public class NewGroup extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String text = JOptionPane.showInputDialog(new JFrame(), "Please type the Name of the new Group!");
		if (text != null) {
			try {
				Group group = new Group(text);
				if (EntryManager.getInstance().createGroup(group)) {
					TSWindow.getInstance().getNavMenuView().getNavigator().newGroup(new Group(text));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
