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
import java.util.Arrays;

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
	private static char[] masterPassword;
	 
	public LoginView() {
		super((JFrame)null, true);
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
				setMasterPassword(pwd.toCharArray());
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
		this.setSize(313, 106);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		pfPW.setBounds(70, 8, 217, 20);
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
		panelRoot.setLayout(null);
		labelPassword.setBounds(11, 11, 50, 14);
		this.panelRoot.add(this.labelPassword);
		this.panelRoot.add(this.pfPW);
		JLabel label = new JLabel();
		label.setBounds(0, 0, 0, 0);
		this.panelRoot.add(label);
		this.panelRoot.setBorder(null);
		this.getContentPane().add(this.panelRoot);
		buttonOK.setBounds(168, 34, 48, 24);
		panelRoot.add(buttonOK);
		this.buttonOK.setText("OK");
		buttonCancel.setBounds(224, 34, 64, 24);
		panelRoot.add(buttonCancel);
		this.buttonCancel.setText("Cancel");
		this.buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.cancel();
			}
		});
		this.buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.ok();
			}
		});
	}

	protected void ok() {
		try {
			String password = this.pfPW.getText();
			File idF = new File(String.valueOf(System.getProperty("user.dir")) + "\\data\\ts.id");
			if (LoginVerifier.canLogin(idF, password)) {
				setMasterPassword(password.toCharArray());
				this.dialogResult = true;
				this.dispose();
			}
		} catch (WrongPasswordException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid password. Please try again.", "Login", JOptionPane.ERROR_MESSAGE);
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
	
	private static void setMasterPassword(char[] password) {
        masterPassword = password;
    }

    public static String getMasterPassword() {
        return new String(masterPassword);
    }
    
    public static void clear() {
        if (masterPassword != null) {
            Arrays.fill(masterPassword, '\0');
            masterPassword = null;
        }
    }
}
