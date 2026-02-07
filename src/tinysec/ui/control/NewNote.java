package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import tinysec.TSWindow;
import tinysec.entity.Group;
import tinysec.entity.Note;
import tinysec.ui.controls.Navigator;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.GroupNode;
import tinysec.ui.views.NewNoteView;

public class NewNote extends AbstractAction {
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
				NewNoteView nnw = new NewNoteView(parentGroup);
				nnw.setLocationRelativeTo(instance);
				nnw.setVisible(true);
				if (nnw.getDialogResult()) {
					Note note = nnw.getCreatedNote();
					nav.addNoteTN(node, note);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
