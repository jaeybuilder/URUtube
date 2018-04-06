package jmgr.org.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private String hashtext;
	
	public MD5(String password){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] messageDigest = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		hashtext = number.toString(16);
	}
	
	public String returnEncrypt() {
		return hashtext;
	}
}
