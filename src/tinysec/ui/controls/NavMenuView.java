package tinysec.ui.controls;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class NavMenuView extends JPanel {
	private Navigator navigator = new Navigator();

	public NavMenuView() {
		this.setLayout(new BorderLayout());
		this.addNavigator();
	}

	protected void addNavigator() {
		this.add(this.navigator, "Center");
	}

	public Navigator getNavigator() {
		return this.navigator;
	}
}
