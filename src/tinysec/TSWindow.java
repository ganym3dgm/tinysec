package tinysec;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import tinysec.ui.controls.NavMenuView;
import tinysec.ui.views.LoginView;

public class TSWindow extends JFrame {
	private static TSWindow instance;

	public static TSWindow getInstance() throws Exception {
		if (instance != null) {
			return instance;
		}
		instance = new TSWindow("TinySec v1.0");
		return instance;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			LoginView login = new LoginView();
			login.show();
			if (login.getDialogResult()) {
				TSWindow.getInstance().show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel centerpanel = new JPanel();
	private NavMenuView nmView;

	private JLabel status = new JLabel("TinySec Ready...");

	private JTextArea ta_PrincipalInfos = new JTextArea();

	private TSWindow(String title) {
		super(title);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				TSWindow.this.dispose();
				System.exit(0);
			}
		});
		this.init();
		this.setSize(1200, 800);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		this.setLocation(x, y);
	}

	protected void createNavigator() {
		this.nmView = new NavMenuView();
		this.getContentPane().add(this.nmView, "West");
		this.nmView.setVisible(true);
	}

	private void creteStatusPanel() {
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(BorderFactory.createEtchedBorder());
		statusPanel.add(this.status, "West");
		this.getContentPane().add(statusPanel, "South");
	}

	public void exit() {
		this.dispose();
	}

	public NavMenuView getNavMenuView() {
		return this.nmView;
	}

	public JLabel getStatus() {
		return this.status;
	}

	public JTextArea getTa_PrincipalInfos() {
		return this.ta_PrincipalInfos;
	}

	protected void init() {
		this.getContentPane().setLayout(new BorderLayout());
		this.createNavigator();
		this.ta_PrincipalInfos.setEnabled(false);
		JScrollPane scp = new JScrollPane(this.ta_PrincipalInfos);
		scp.setVerticalScrollBarPolicy(22);
		scp.setPreferredSize(new Dimension(250, 250));
		scp.setBorder(new TitledBorder("  Properties  "));
		this.centerpanel.add(scp);
		this.getContentPane().add(scp, "Center");
		this.creteStatusPanel();
		this.pack();
	}
}
