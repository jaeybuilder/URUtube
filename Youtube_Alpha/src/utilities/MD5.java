package utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private String password;
	
	public MD5(String password){
		this.password = password;
	}
	
	public String returnEncrypt() {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] messageDigest = md.digest(this.password.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		return hashtext;
	}
}
