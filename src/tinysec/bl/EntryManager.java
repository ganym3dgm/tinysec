package tinysec.bl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

import tinysec.entity.Account;
import tinysec.entity.Group;
import tinysec.entity.Note;

public class EntryManager {
	private static EntryManager instance;
	private static final String srcDir;
	static {
		srcDir = String.valueOf(System.getProperty("user.dir")) + "\\data\\";
	}

	public static EntryManager getInstance() throws Exception {
		if (instance != null) {
			return instance;
		}
		instance = new EntryManager();
		return instance;
	}

	private Hashtable groups;

	public EntryManager() {
		this.loadEntries();
	}

	public boolean createAccount(Account account) {
		String associatedGroupName = account.getAsscociatedGroup().getTitle();
		File groupF = new File(String.valueOf(srcDir) + associatedGroupName);
		if (groupF.exists()) {
			File accountF = new File(groupF, String.valueOf(account.getTitle()) + ".tsa");
			try {
				FileOutputStream fos = new FileOutputStream(accountF);
				EntryWriter.writeAccount(account, fos);
				Group group = (Group) this.groups.get(associatedGroupName);
				group.addAccount(account);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean createGroup(Group group) {
		File groupF = new File(String.valueOf(srcDir) + group.getTitle());
		if (groupF.exists()) {
			return false;
		}
		if (groupF.mkdir()) {
			this.groups.put(group.getTitle(), group);
		}
		return true;
	}

	public boolean createNote(Note note) {
		String associatedGroupName = note.getAssociatedGroup().getTitle();
		File groupF = new File(String.valueOf(srcDir) + associatedGroupName);
		if (groupF.exists()) {
			File noteF = new File(groupF, String.valueOf(note.getTitle()) + ".tsn");
			try {
				FileOutputStream fos = new FileOutputStream(noteF);
				EntryWriter.writeNote(note, fos);
				Group group = (Group) this.groups.get(associatedGroupName);
				group.addNote(note);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean delAccount(Account account) {
		String associatedGroupName = account.getAsscociatedGroup().getTitle();
		File groupF = new File(String.valueOf(srcDir) + associatedGroupName);
		if (groupF.exists()) {
			File accountF = new File(groupF, String.valueOf(account.getTitle()) + ".tsa");
			try {
				if (accountF.delete()) {
					Group group = (Group) this.groups.get(associatedGroupName);
					group.delAccount(account);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			int i = 0;
			while (i < children.length) {
				boolean success = this.deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
				++i;
			}
		}
		return dir.delete();
	}

	public boolean delGroup(Group group) {
		File groupF = new File(String.valueOf(srcDir) + group.getTitle());
		return groupF.exists() && (groupF.delete() ? this.groups.remove(group.getTitle()) != null
				: this.deleteDir(groupF) && this.groups.remove(group.getTitle()) != null);
	}

	public boolean delNote(Note note) {
		String associatedGroupName = note.getAssociatedGroup().getTitle();
		File groupF = new File(String.valueOf(srcDir) + associatedGroupName);
		if (groupF.exists()) {
			File noteF = new File(groupF, String.valueOf(note.getTitle()) + ".tsn");
			try {
				if (noteF.delete()) {
					Group group = (Group) this.groups.get(associatedGroupName);
					group.delNote(note);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Hashtable getGroups() {
		return this.groups;
	}

	private void loadEntries() {
		this.groups = new Hashtable();
		File srcDirF = new File(srcDir);
		File[] entries = srcDirF.listFiles();
		Arrays.sort(entries, Comparator.comparing(file -> file.getName().toLowerCase()));
		int i = 0;
		while (i < entries.length) {
			File groupF = entries[i];
			this.loadEntries(groupF);
			++i;
		}
	}

	protected void loadEntries(File tempFile) {
		if (tempFile.isDirectory()) {
			Group group = new Group(tempFile.getName());
			File[] entries = tempFile.listFiles();
			Arrays.sort(entries, Comparator.comparing(file -> file.getName().toLowerCase()));
			int i = 0;
			while (i < entries.length) {
				File entrieFile = entries[i];
				String pName = entrieFile.getName();
				try {
					String pwd;
					if (pName.endsWith(".tsa")) {
						pwd = System.getProperty("pwd");
						Account account = (Account) EntryLoader.loadAccount(entrieFile, pwd);
						account.setAsscociatedGroup(group);
						int to = pName.lastIndexOf(".tsa");
						String title = pName.substring(0, to);
						account.setTitle(title);
						group.addAccount(account);
					}
					if (pName.endsWith(".tsn")) {
						pwd = System.getProperty("pwd");
						Note note = (Note) EntryLoader.loadNote(entrieFile, pwd);
						note.setAssociatedGroup(group);
						int to = pName.lastIndexOf(".tsn");
						String title = pName.substring(0, to);
						note.setTitle(title);
						group.addNote(note);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				++i;
			}

			this.groups.put(group.getTitle(), group);
		}
	}

	public boolean updateAccount(Account oldA, Account newA) {
		return this.delAccount(oldA) && this.createAccount(newA);
	}

	public boolean updateNote(Note oldNote, Note newNote) {
		return this.delNote(oldNote) && this.createNote(newNote);
	}
}
