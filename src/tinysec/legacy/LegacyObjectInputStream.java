package tinysec.legacy;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class LegacyObjectInputStream extends ObjectInputStream {

	public LegacyObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {

		if (desc.getName().equals("nanosource.tinysec.app.entry.group.Group")) {
			return tinysec.entity.Group.class;
		}

		if (desc.getName().equals("nanosource.tinysec.app.entry.account.Account")) {
			return tinysec.entity.Account.class;
		}

		if (desc.getName().equals("nanosource.tinysec.app.entry.note.Note")) {
			return tinysec.entity.Note.class;
		}

		return super.resolveClass(desc);
	}
}