package tinysec.ui.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import tinysec.bl.EntryWriter;
import tinysec.bl.LoginVerifier;
import tinysec.bl.WrongPasswordException;

public class LoginView extends JDialog {
	private final JButton buttonCancel = new JButton();
	private final JButton buttonOK = new JButton();
	private boolean dialogResult;
	private final JLabel labelPassword = new JLabel("Password:");
	private final JPanel panelRoot = new JPanel();
	private final JPasswordField pfPW = new JPasswordField();

	public LoginView() {
		super(new JFrame(), true);
		if (!this.repositoryExists()) {
			this.createRepository();
		}
		this.init();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		this.setLocation(x, y);
	}

	protected void cancel() {
		System.exit(0);
	}

	private void createRepository() {
		String pwd = JOptionPane.showInputDialog(new JFrame(),
				"TinySec will now set up the local 'data' repository.\n"
				+ "Please enter a password to protect your stored data.\n"
				+ "Important: If you lose this password, your data cannot be recovered.\n\n");
		if (pwd != null) {
			File uadmin = new File(String.valueOf(System.getProperty("user.dir")) + "\\data\\");
			uadmin.mkdir();
			try {
				EntryWriter.writeID(pwd);
				System.setProperty("pwd", pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.exit(0);
		}
	}

	public boolean getDialogResult() {
		return this.dialogResult;
	}

	private void init() {
		this.setTitle("Login");
		this.setSize(350, 135);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.buttonOK.setText("OK");
		this.buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.ok();
			}
		});
		this.buttonCancel.setText("Cancel");
		this.buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.cancel();
			}
		});
		this.pfPW.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					LoginView.this.ok();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		this.panelRoot.setLayout(new GridBagLayout());
		this.panelRoot.add(this.labelPassword,
				new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 17, 0, new Insets(5, 10, 0, 0), 0, 0));
		this.panelRoot.add(this.pfPW,
				new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 10, 2, new Insets(5, 5, 0, 10), 0, 0));
		this.panelRoot.add(new JLabel(),
				new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, 10, 1, new Insets(5, 10, 5, 10), 0, 0));
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new FlowLayout(2));
		bPanel.add(this.buttonOK);
		bPanel.add(this.buttonCancel);
		this.panelRoot.add(bPanel, new GridBagConstraints(1, 3, 2, 1, 1.0, 1.0, 10, 1, new Insets(5, 10, 5, 10), 0, 0));
		this.panelRoot.setBorder(new TitledBorder(" Login "));
		this.getContentPane().add(this.panelRoot);
	}

	protected void ok() {
		try {
			String password = this.pfPW.getText();
			File idF = new File(String.valueOf(System.getProperty("user.dir")) + "\\data\\ts.id");
			if (LoginVerifier.canLogin(idF, password)) {
				System.setProperty("pwd", password);
				this.dialogResult = true;
				this.dispose();
			}
		} catch (WrongPasswordException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Wrong password try again!");
			this.pfPW.setText("");
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(new JFrame(), "Repository not in expected format. Please delete entry point \"ts.id\" and try again.");
			System.exit(1);
		}
	}

	private boolean repositoryExists() {
		File uadmin = new File(String.valueOf(System.getProperty("user.dir")) + "\\data\\");
		if (uadmin.exists()) {
			File idF = new File(String.valueOf(System.getProperty("user.dir")) + "\\data\\ts.id");
			return idF.exists();
		}
		return false;
	}
}
