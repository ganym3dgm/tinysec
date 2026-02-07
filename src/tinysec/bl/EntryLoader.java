package tinysec.bl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;

import tinysec.legacy.LegacyObjectInputStream;

public class EntryLoader {
	public static Object loadAccount(File account, String password) throws Exception {
		byte[] accountByte = FileToByteArray.getBytesFromFile(account);
		PBCrypto crypto = new PBCrypto(password);
		byte[] decryptedByte = crypto.decrypt(accountByte);
		ByteArrayInputStream bais = new ByteArrayInputStream(decryptedByte);
		LegacyObjectInputStream ois = new LegacyObjectInputStream(bais);
		Object myObject = ois.readObject();
		bais.close();
		ois.close();
		return myObject;
	}

	public static Object loadID(File id, String password) throws Exception {
		byte[] idByte = FileToByteArray.getBytesFromFile(id);
		PBCrypto crypto = new PBCrypto(password);
		byte[] decryptedID = crypto.decrypt(idByte);
		ByteArrayInputStream bais = new ByteArrayInputStream(decryptedID);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object myObject = ois.readObject();
		bais.close();
		ois.close();
		return myObject;
	}

	public static Object loadNote(File note, String password) throws Exception {
		byte[] noteByte = FileToByteArray.getBytesFromFile(note);
		PBCrypto crypto = new PBCrypto(password);
		byte[] decryptedNote = crypto.decrypt(noteByte);
		ByteArrayInputStream bais = new ByteArrayInputStream(decryptedNote);
		LegacyObjectInputStream ois = new LegacyObjectInputStream(bais);
		Object myObject = ois.readObject();
		bais.close();
		ois.close();
		return myObject;
	}
}
