package tinysec.ui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import tinysec.bl.EntryManager;
import tinysec.entity.Account;
import tinysec.entity.Group;

public class NewAccountView extends JDialog {
	private JButton btn_cancel = null;
	private JButton btn_genPwd = null;
	private JButton btn_ok = null;
	private Account createdAccount;
	private boolean dialogResult;
	private JPanel ivjJFrameContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel3 = null;
	private JScrollPane jScrollPane = null;
	private Group parentGroup;
	private JTextArea ta_note = null;
	private JPanel tf_jPanel2 = null;
	private JTextField tf_link = null;
	private JTextField tf_password = null;
	private JTextField tf_title = null;
	private JTextField tf_uid = null;

	public NewAccountView(Group parentGroup) {
		super(new JFrame(), true);
		this.parentGroup = parentGroup;
		this.initialize();
	}

	private void cancelClicked() {
		this.dispose();
		this.dialogResult = false;
	}

	private void genPWD() {
		Random rand = new Random();
		String random = "" + rand.nextInt();
		this.tf_password.setText(random);
	}

	private JButton getBtn_genPwd() {
		if (this.btn_genPwd == null) {
			this.btn_genPwd = new JButton();
			btn_genPwd.setBounds(310, 55, 45, 21);
			this.btn_genPwd.setIcon(new ImageIcon(this.getClass().getResource("/icons/tss.png")));
			this.btn_genPwd.setPreferredSize(new Dimension(25, 20));
			this.btn_genPwd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					NewAccountView.this.genPWD();
				}
			});
		}
		return this.btn_genPwd;
	}

	public Account getCreatedAccount() {
		return this.createdAccount;
	}

	public boolean getDialogResult() {
		return this.dialogResult;
	}

	private JButton getJButton1() {
		if (this.btn_cancel == null) {
			this.btn_cancel = new JButton();
			btn_cancel.setBounds(353, 5, 75, 20);
			this.btn_cancel.setPreferredSize(new Dimension(75, 20));
			this.btn_cancel.setText("Cancel");
			this.btn_cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					NewAccountView.this.cancelClicked();
				}
			});
		}
		return this.btn_cancel;
	}

	private JButton getJButton2() {
		if (this.btn_ok == null) {
			this.btn_ok = new JButton();
			btn_ok.setBounds(293, 5, 55, 20);
			this.btn_ok.setPreferredSize(new Dimension(55, 20));
			this.btn_ok.setText("OK");
			this.btn_ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					NewAccountView.this.okClicked();
				}
			});
		}
		return this.btn_ok;
	}

	private JPanel getJFrameContentPane() {
		if (this.ivjJFrameContentPane == null) {
			this.ivjJFrameContentPane = new JPanel();
			BorderLayout layBorderLayout_3 = new BorderLayout();
			this.ivjJFrameContentPane.setLayout(layBorderLayout_3);
			this.ivjJFrameContentPane.add(this.getJPanel(), "West");
			this.ivjJFrameContentPane.add(this.getJPanel1(), "South");
			this.ivjJFrameContentPane.add(this.getJPanel2(), "Center");
		}
		return this.ivjJFrameContentPane;
	}

	private JPanel getJPanel() {
		if (this.jPanel == null) {
			JLabel jLabel5 = new JLabel();
			jLabel5.setBounds(8, 305, 60, 20);
			JLabel jLabel4 = new JLabel();
			jLabel4.setBounds(8, 80, 60, 20);
			JLabel jLabel3 = new JLabel();
			jLabel3.setBounds(8, 5, 60, 20);
			JLabel jLabel2 = new JLabel();
			jLabel2.setBounds(8, 55, 60, 20);
			JLabel jLabel = new JLabel();
			jLabel.setBounds(8, 30, 60, 20);
			this.jPanel = new JPanel();
			this.jPanel.setPreferredSize(new Dimension(72, 10));
			jLabel.setText("UserID:");
			jLabel.setPreferredSize(new Dimension(120, 20));
			jLabel2.setText("Password:");
			jLabel2.setPreferredSize(new Dimension(120, 20));
			jLabel3.setText("Title: ");
			jLabel3.setPreferredSize(new Dimension(120, 20));
			jLabel4.setText("Note:");
			jLabel4.setPreferredSize(new Dimension(120, 20));
			jLabel5.setText("Link:");
			jLabel5.setPreferredSize(new Dimension(120, 20));
			jPanel.setLayout(null);
			this.jPanel.add(jLabel3);
			this.jPanel.add(jLabel);
			this.jPanel.add(jLabel2);
			this.jPanel.add(jLabel4);
			this.jPanel.add(jLabel5);
		}
		return this.jPanel;
	}

	private JPanel getJPanel1() {
		if (this.jPanel1 == null) {
			this.jPanel1 = new JPanel();
			this.jPanel1.setPreferredSize(new Dimension(10, 30));
			jPanel1.setLayout(null);
			this.jPanel1.add(this.getJButton2());
			this.jPanel1.add(this.getJButton1());
		}
		return this.jPanel1;
	}

	private JPanel getJPanel2() {
		if (this.tf_jPanel2 == null) {
			this.tf_jPanel2 = new JPanel();
			this.tf_jPanel2.setPreferredSize(new Dimension(175, 145));
			tf_jPanel2.setLayout(null);
			tf_jPanel2.add(getTf_uid());
			tf_jPanel2.add(getTf_password());
			tf_jPanel2.add(getTf_title());
			tf_jPanel2.add(getBtn_genPwd());
			tf_jPanel2.add(getTf_link());
			tf_jPanel2.add(getJScrollPane());
		}
		return this.tf_jPanel2;
	}

	private JScrollPane getJScrollPane() {
		if (this.jScrollPane == null) {
			this.jScrollPane = new JScrollPane();
			jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setBounds(10, 80, 345, 220);
			this.jScrollPane.setPreferredSize(new Dimension(225, 100));
			this.jScrollPane.setViewportBorder(BorderFactory.createEtchedBorder(0));
			jScrollPane.setViewportView(getJTextArea());
		}
		return this.jScrollPane;
	}

	private JTextArea getJTextArea() {
		if (this.ta_note == null) {
			this.ta_note = new JTextArea();
			this.ta_note.setWrapStyleWord(true);
			this.ta_note.setLineWrap(true);
		}
		return this.ta_note;
	}

	private JTextField getTf_link() {
		if (this.tf_link == null) {
			this.tf_link = new JTextField();
			tf_link.setBounds(10, 305, 345, 20);
			this.tf_link.setPreferredSize(new Dimension(225, 20));
		}
		return this.tf_link;
	}

	private JTextField getTf_password() {
		if (this.tf_password == null) {
			this.tf_password = new JTextField();
			tf_password.setBounds(10, 55, 294, 20);
			this.tf_password.setPreferredSize(new Dimension(195, 20));
		}
		return this.tf_password;
	}

	private JTextField getTf_title() {
		if (this.tf_title == null) {
			this.tf_title = new JTextField();
			tf_title.setBounds(10, 5, 294, 20);
			this.tf_title.setPreferredSize(new Dimension(225, 20));
		}
		return this.tf_title;
	}

	private JTextField getTf_uid() {
		if (this.tf_uid == null) {
			this.tf_uid = new JTextField();
			tf_uid.setBounds(10, 30, 294, 20);
			this.tf_uid.setPreferredSize(new Dimension(225, 20));
		}
		return this.tf_uid;
	}

	private void initialize() {
		this.setContentPane(this.getJFrameContentPane());
		this.setTitle("Edit");
		this.setDefaultCloseOperation(2);
		this.setBounds(23, 36, 453, 396);
	}

	private void okClicked() {
		String title = this.tf_title.getText();
		String userid = this.tf_uid.getText();
		String pwd = this.tf_password.getText();
		String note = this.ta_note.getText();
		String link = this.tf_link.getText();
		if (!(title.equals("") || userid.equals("") || pwd.equals(""))) {
			Account account = new Account(title, userid, pwd, note, link);
			account.setAsscociatedGroup(this.parentGroup);
			this.createdAccount = account;
			try {
				if (EntryManager.getInstance().createAccount(account)) {
					this.dialogResult = true;
					this.dispose();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
