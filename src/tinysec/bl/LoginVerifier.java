package tinysec.bl;

import java.io.File;

import javax.crypto.BadPaddingException;

public class LoginVerifier {
	public static boolean canLogin(File idF, String pwd) throws Exception {
		try {
			Object obj = EntryLoader.loadID(idF, pwd);
			return obj != null;
		} catch (BadPaddingException e) {
			String message = e.getMessage();
			if(message.contains("bad key")){
				throw new WrongPasswordException();
			}

			throw e;
		}
	}
}
