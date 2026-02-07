package tinysec.bl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PBCrypto {
	private Cipher dcipher;
	private Cipher ecipher;
	int iterationCount = 20;
	byte[] salt = new byte[] { -87, -101, -56, 50, 86, 53, -29, 3 };

	public PBCrypto(String passPhrase) {
		try {
			PBEKeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), this.salt, this.iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			this.ecipher = Cipher.getInstance(key.getAlgorithm());
			this.dcipher = Cipher.getInstance(key.getAlgorithm());
			PBEParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
			this.ecipher.init(1, key, paramSpec);
			this.dcipher.init(2, key, paramSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] decrypt(byte[] data) throws Exception {
		try {
			byte[] utf8 = this.dcipher.doFinal(data);
			return utf8;
		} catch (Exception e) {
			throw new BadPaddingException(e.getLocalizedMessage());
		}
	}

	public byte[] encrypt(byte[] data) throws Exception {
		try {
			byte[] enc = this.ecipher.doFinal(data);
			return enc;
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}
}
