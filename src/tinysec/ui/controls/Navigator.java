package tinysec.ui.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.tree.DefaultTreeModel;

import tinysec.TSWindow;
import tinysec.bl.EntryManager;
import tinysec.entity.Account;
import tinysec.entity.Group;
import tinysec.entity.Note;
import tinysec.ui.control.About;
import tinysec.ui.control.Del;
import tinysec.ui.control.Edit;
import tinysec.ui.control.NewAccount;
import tinysec.ui.control.NewGroup;
import tinysec.ui.control.NewNote;
import tinysec.ui.nodes.AccountNode;
import tinysec.ui.nodes.DefaultNode;
import tinysec.ui.nodes.GroupNode;
import tinysec.ui.nodes.NoteNode;

public class Navigator extends JPanel {
	protected JButton bDelEntry;
	protected JButton bEdit;
	protected JButton bInfo;
	protected JButton bNewAccount;
	protected JButton bNewGroup;
	protected JButton bNewNote;
	private Hashtable groups;
	private NavigatorTree navTree;
	protected JToolBar toolBar = new JToolBar();

	public Navigator() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		this.toolBar.add(new JLabel("", new ImageIcon(this.getClass().getResource("/icons/ts.png")), 0));
		this.bNewGroup = new JButton(new NewGroup());
		this.bNewGroup.setEnabled(false);
		this.bNewGroup.setIcon(new ImageIcon(this.getClass().getResource("/icons/newFolder.png")));
		this.bNewGroup.setToolTipText("New Group");
		this.bNewAccount = new JButton(new NewAccount());
		this.bNewAccount.setEnabled(false);
		this.bNewAccount.setIcon(new ImageIcon(this.getClass().getResource("/icons/newAccount.png")));
		this.bNewAccount.setToolTipText("New Account");
		this.bNewNote = new JButton(new NewNote());
		this.bNewNote.setEnabled(false);
		this.bNewNote.setIcon(new ImageIcon(this.getClass().getResource("/icons/newNote.png")));
		this.bNewNote.setToolTipText("New Note");
		this.bDelEntry = new JButton(new Del());
		this.bDelEntry.setEnabled(false);
		this.bDelEntry.setIcon(new ImageIcon(this.getClass().getResource("/icons/del.png")));
		this.bDelEntry.setToolTipText("Del");
		this.bEdit = new JButton(new Edit());
		this.bEdit.setEnabled(false);
		this.bEdit.setIcon(new ImageIcon(this.getClass().getResource("/icons/edit.png")));
		this.bEdit.setToolTipText("Edit");
		this.bInfo = new JButton(new About());
		this.bInfo.setEnabled(true);
		this.bInfo.setIcon(new ImageIcon(this.getClass().getResource("/icons/info.png")));
		this.bInfo.setToolTipText("Info");
		this.toolBar.add(this.bNewGroup);
		this.toolBar.add(this.bNewAccount);
		this.toolBar.add(this.bNewNote);
		this.toolBar.add(this.bEdit);
		this.toolBar.add(this.bDelEntry);
		this.toolBar.add(this.bInfo);
		DefaultNode rootNode = new DefaultNode("TinySec");
		DefaultTreeModel dTModel = new DefaultTreeModel(rootNode);
		this.navTree = new NavigatorTree(dTModel);
		this.loadEntries();
		this.navTree.setCellRenderer(new TreeRenderer());
		JScrollPane scroller = new JScrollPane(this.navTree);
		scroller.setVerticalScrollBarPolicy(22);
		scroller.setPreferredSize(new Dimension(300, 250));
		this.add(this.toolBar, "North");
		this.add(scroller, "Center");
	}

	public void addAccountTN(DefaultNode dgroupTNode, Account account) {
		this.navTree.addAccountNode(dgroupTNode, new AccountNode(dgroupTNode, account));
	}

	private void addEntries(Group group, DefaultNode grNode) {
		Vector grEntries = group.getEntries();
		int i = 0;
		while (i < grEntries.size()) {
			Object entry = grEntries.elementAt(i);
			if (entry instanceof Account) {
				this.addAccountTN(grNode, (Account) entry);
			}
			if (entry instanceof Note) {
				this.addNoteTN(grNode, (Note) entry);
			}
			++i;
		}
	}

	public void addNoteTN(DefaultNode dgroupTNode, Note note) {
		this.navTree.addNoteNode(dgroupTNode, new NoteNode(dgroupTNode, note));
	}

	public void aOrNoteClicked() {
		this.bNewAccount.setEnabled(false);
		this.bNewGroup.setEnabled(false);
		this.bNewNote.setEnabled(false);
		this.bDelEntry.setEnabled(true);
		this.bEdit.setEnabled(true);
	}

	public NavigatorTree getNavTree() {
		return this.navTree;
	}

	public void groupClicked() {
		this.bNewAccount.setEnabled(true);
		this.bNewNote.setEnabled(true);
		this.bNewGroup.setEnabled(false);
		this.bDelEntry.setEnabled(true);
		this.bEdit.setEnabled(false);
	}



	private void initTree() {
		this.navTree.suspendUpdates();
		Enumeration enumeration = this.groups.elements();
		List<Group> list = Collections.list(enumeration);
		list.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));
		for (Group group : list) {
			DefaultNode groupNode = this.newGroup(group);
			this.addEntries(group, groupNode);
		}

		this.navTree.resumeUpdates();
	}

	private void loadEntries() {
		try {
			this.groups = EntryManager.getInstance().getGroups();
			this.initTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DefaultNode newGroup(Group group) {
		GroupNode grNode = new GroupNode(group);
		DefaultNode groupNode = new DefaultNode(grNode);
		this.navTree.addGroupNode(groupNode);
		return groupNode;
	}

	public void removeNode(DefaultNode node) {
		DefaultNode parent = (DefaultNode) node.getParent();
		this.navTree.getDTModel().removeNodeFromParent(node);
		this.navTree.scrollToAndSelect(parent);
	}

	public void rootClicked() {
		this.bDelEntry.setEnabled(false);
		this.bNewAccount.setEnabled(false);
		this.bNewGroup.setEnabled(true);
		this.bNewNote.setEnabled(false);
		this.bEdit.setEnabled(false);
	}

	public void select(DefaultNode node) {
		TSWindow instance = TSWindow.getInstance();
		Object userObj = node.getUserObject();
		this.rootClicked();
		if (userObj instanceof GroupNode) {
			this.groupClicked();
			instance.getTa_PrincipalInfos().setText("");
			instance.getStatus().setText("Selected Group:'" + ((GroupNode) userObj).getTitle() + "'");
		}
		if (userObj instanceof AccountNode) {
			this.aOrNoteClicked();
			AccountNode pTN = (AccountNode) userObj;
			Account account = pTN.getAAccount();
			if (account != null) {
				instance.getStatus().setText("Selected Account:'" + account.getTitle() + "'");
				instance.getTa_PrincipalInfos().setText(account.toString());
			}
		}
		if (userObj instanceof NoteNode) {
			this.aOrNoteClicked();
			NoteNode nTN = (NoteNode) userObj;
			Note note = nTN.getANote();
			if (note != null) {
				instance.getStatus().setText("Selected Note:'" + note.getTitle() + "'");
				instance.getTa_PrincipalInfos().setText(note.toString());
			}
		} else if (userObj instanceof String) {
			this.rootClicked();
			instance.getStatus().setText("TinySec ready...");
			instance.getTa_PrincipalInfos().setText("");
		}
	}
}
