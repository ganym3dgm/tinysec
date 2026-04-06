package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import tinysec.TSWindow;
import tinysec.ui.views.AboutView;

public class About extends AbstractAction {
	public void actionPerformed(ActionEvent arg0) {
		AboutView view = new AboutView();
		try {
			TSWindow instance = TSWindow.getInstance();
			view.setLocationRelativeTo(instance);
			view.show();
			instance.getNavMenuView().getNavigator().focus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
