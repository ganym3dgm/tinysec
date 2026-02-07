package tinysec.ui.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import tinysec.TSWindow;
import tinysec.ui.views.AboutView;

public class About extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		AboutView atsw = new AboutView();
		try {
			TSWindow instance = TSWindow.getInstance();
			atsw.setLocationRelativeTo(instance);
			atsw.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
