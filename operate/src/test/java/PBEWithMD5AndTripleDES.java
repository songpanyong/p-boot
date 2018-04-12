
import java.nio.charset.Charset;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PBEWithMD5AndTripleDES {

	private final static String KEY_PBE = "PBEWithMD5AndTripleDES";
	private final static String PASSWORD = "ghjr/7fa09592bc583a57";

	public static void main(String[] args) throws Throwable {
		System.out.println(decode("Zf7y4CF/qSPxnxwnUuVehanYzEPEb0yY"));
	}

	public static String encode(String text) throws Throwable {
		byte[] salt = getSalt();
		Key key = getKey(PASSWORD);
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 1000);
		Cipher cipher = Cipher.getInstance(KEY_PBE);
		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
		byte[] finals = cipher.doFinal(text.getBytes(Charset.forName("utf-8")));

		byte[] bytes = new byte[salt.length + finals.length];
		System.arraycopy(salt, 0, bytes, 0, salt.length);
		System.arraycopy(finals, 0, bytes, salt.length, finals.length);

		String c = Base64.getEncoder().encodeToString(bytes);

		return c;
	}

	public static String decode(String text) throws Throwable {

		byte[] bytes = Base64.getDecoder().decode(text.getBytes("US-ASCII"));

		byte[] salt = new byte[8];
		byte[] kernel = new byte[bytes.length - 8];

		System.arraycopy(bytes, 0, salt, 0, 8);
		System.arraycopy(bytes, 8, kernel, 0, bytes.length - 8);

		Key key = getKey(PASSWORD);
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 1000);
		Cipher cipher = Cipher.getInstance(KEY_PBE);
		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

		byte[] msg = cipher.doFinal(kernel);
		return new String(msg, Charset.forName("utf-8"));
	}

	public static SecretKey getKey(String key) throws InvalidKeySpecException, NoSuchAlgorithmException {
		PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
		SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_PBE);
		SecretKey secretKey = factory.generateSecret(keySpec);
		return secretKey;
	}

	public static byte[] getSalt() {
		byte[] salt = new byte[8];
		Random random = new Random();
		random.nextBytes(salt);
		return salt;
	}

}
