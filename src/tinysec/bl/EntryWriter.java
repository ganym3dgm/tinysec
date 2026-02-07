package tinysec.bl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import tinysec.entity.Account;
import tinysec.entity.Note;

public class EntryWriter {
	public static boolean writeAccount(Account account, FileOutputStream out) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(bos);
		objout.writeObject(account);
		objout.close();
		PBCrypto crypto = new PBCrypto(System.getProperty("pwd"));
		byte[] buf = bos.toByteArray();
		byte[] encObj = crypto.encrypt(buf);
		out.write(encObj);
		bos.close();
		out.close();
		return true;
	}

	public static boolean writeID(String pwd) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(bos);
		objout.writeObject(new String("One Ring to Rule them All"));
		objout.close();
		File idF = new File(String.valueOf(System.getProperty("user.dir")) + "\\data\\ts.id");
		FileOutputStream out = new FileOutputStream(idF);
		PBCrypto crypto = new PBCrypto(pwd);
		byte[] buf = bos.toByteArray();
		byte[] encObj = crypto.encrypt(buf);
		out.write(encObj);
		bos.close();
		out.close();
		return true;
	}

	public static boolean writeNote(Note note, FileOutputStream out) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(bos);
		objout.writeObject(note);
		objout.close();
		PBCrypto crypto = new PBCrypto(System.getProperty("pwd"));
		byte[] buf = bos.toByteArray();
		byte[] encObj = crypto.encrypt(buf);
		out.write(encObj);
		bos.close();
		out.close();
		return true;
	}
}
