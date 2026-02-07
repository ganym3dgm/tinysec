package tinysec.ui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tinysec.bl.EntryManager;
import tinysec.entity.Group;
import tinysec.entity.Note;

public class NewNoteView extends JDialog {
	private JButton btn_cancel = null;
	private JButton btn_ok = null;
	private Note createdNote;
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
	private JTextField tf_title = null;

	public NewNoteView(Group parentGroup) {
		super(new JFrame(), true);
		this.parentGroup = parentGroup;
		this.initialize();
	}

	private void cancelClicked() {
		this.dispose();
		this.dialogResult = false;
	}

	public Note getCreatedNote() {
		return this.createdNote;
	}

	public boolean getDialogResult() {
		return this.dialogResult;
	}

	private JButton getJButton1() {
		if (this.btn_cancel == null) {
			this.btn_cancel = new JButton();
			this.btn_cancel.setPreferredSize(new Dimension(75, 20));
			this.btn_cancel.setText("Cancel");
			this.btn_cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					NewNoteView.this.cancelClicked();
				}
			});
		}
		return this.btn_cancel;
	}

	private JButton getJButton2() {
		if (this.btn_ok == null) {
			this.btn_ok = new JButton();
			this.btn_ok.setPreferredSize(new Dimension(55, 20));
			this.btn_ok.setText("OK");
			this.btn_ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					NewNoteView.this.okClicked();
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
			JLabel jLabel4 = new JLabel();
			JLabel jLabel3 = new JLabel();
			this.jPanel = new JPanel();
			this.jPanel.setLayout(new FlowLayout());
			this.jPanel.setPreferredSize(new Dimension(125, 10));
			jLabel3.setText("Title: ");
			jLabel3.setPreferredSize(new Dimension(120, 20));
			jLabel4.setText("Note:");
			jLabel4.setPreferredSize(new Dimension(120, 20));
			jLabel5.setText("Link:");
			jLabel5.setPreferredSize(new Dimension(120, 20));
			this.jPanel.add(jLabel3, null);
			this.jPanel.add(jLabel4, null);
			this.jPanel.add(this.getJPanel3(), null);
			this.jPanel.add(jLabel5, null);
		}
		return this.jPanel;
	}

	private JPanel getJPanel1() {
		if (this.jPanel1 == null) {
			FlowLayout flowLayout8 = new FlowLayout();
			this.jPanel1 = new JPanel();
			this.jPanel1.setLayout(flowLayout8);
			this.jPanel1.setPreferredSize(new Dimension(10, 30));
			flowLayout8.setAlignment(2);
			this.jPanel1.add(this.getJButton2(), null);
			this.jPanel1.add(this.getJButton1(), null);
		}
		return this.jPanel1;
	}

	private JPanel getJPanel2() {
		if (this.tf_jPanel2 == null) {
			FlowLayout flowLayout7 = new FlowLayout();
			this.tf_jPanel2 = new JPanel();
			this.tf_jPanel2.setLayout(flowLayout7);
			flowLayout7.setAlignment(0);
			this.tf_jPanel2.setPreferredSize(new Dimension(175, 145));
			this.tf_jPanel2.add(this.getTf_title(), null);
			this.tf_jPanel2.add(this.getJScrollPane(), null);
			this.tf_jPanel2.add(this.getTf_link(), null);
		}
		return this.tf_jPanel2;
	}

	private JPanel getJPanel3() {
		if (this.jPanel3 == null) {
			this.jPanel3 = new JPanel();
			this.jPanel3.setLayout(new BorderLayout());
			this.jPanel3.setPreferredSize(new Dimension(125, 100));
		}
		return this.jPanel3;
	}

	private JScrollPane getJScrollPane() {
		if (this.jScrollPane == null) {
			this.jScrollPane = new JScrollPane();
			this.jScrollPane.setPreferredSize(new Dimension(225, 125));
			this.jScrollPane.setViewportView(this.getJTextArea());
			this.jScrollPane.setHorizontalScrollBarPolicy(32);
			this.jScrollPane.setVerticalScrollBarPolicy(22);
			this.jScrollPane.setViewportBorder(BorderFactory.createEtchedBorder(0));
		}
		return this.jScrollPane;
	}

	private JTextArea getJTextArea() {
		if (this.ta_note == null) {
			this.ta_note = new JTextArea();
			this.ta_note.setPreferredSize(new Dimension(0, 20));
			this.ta_note.setWrapStyleWord(true);
			this.ta_note.setLineWrap(true);
		}
		return this.ta_note;
	}

	private JTextField getTf_link() {
		if (this.tf_link == null) {
			this.tf_link = new JTextField();
			this.tf_link.setPreferredSize(new Dimension(225, 20));
		}
		return this.tf_link;
	}

	private JTextField getTf_title() {
		if (this.tf_title == null) {
			this.tf_title = new JTextField();
			this.tf_title.setPreferredSize(new Dimension(225, 20));
		}
		return this.tf_title;
	}

	private void initialize() {
		this.setContentPane(this.getJFrameContentPane());
		this.setTitle("New Account Wizard");
		this.setDefaultCloseOperation(2);
		this.setBounds(23, 36, 367, 253);
	}

	private void okClicked() {
		String title = this.tf_title.getText();
		String notiz = this.ta_note.getText();
		String link = this.tf_link.getText();
		if (!title.equals("") && !notiz.equals("")) {
			Note note = new Note(title, notiz, link);
			note.setAssociatedGroup(this.parentGroup);
			this.createdNote = note;
			try {
				if (EntryManager.getInstance().createNote(note)) {
					this.dialogResult = true;
					this.dispose();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("unsuffiant infos");
		}
	}
}
