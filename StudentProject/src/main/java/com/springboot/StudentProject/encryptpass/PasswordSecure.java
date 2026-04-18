package com.springboot.StudentProject.encryptpass;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordSecure {
	   private static final String type="AES";
	   private static final byte[] key="MySecretKey12345".getBytes();
	   
	   
	   public static String encrypt(String data) throws Exception{
		   Cipher cipher =Cipher.getInstance(type); 
		   SecretKeySpec keyspec=new SecretKeySpec(key,type);
		   cipher.init(Cipher.ENCRYPT_MODE, keyspec);
		   byte[] encrypteddata=cipher.doFinal(data.getBytes());
		   return Base64.getEncoder().encodeToString(encrypteddata);
	   }
	   
	   public static String decrypt(String encryptdata) throws Exception{
		   Cipher cipher =Cipher.getInstance(type); 
		   SecretKeySpec keyspec=new SecretKeySpec(key,type);
		   cipher.init(Cipher.DECRYPT_MODE,keyspec);
		   byte[] decodedata=Base64.getDecoder().decode(encryptdata);
		   byte[] orginaldata=cipher.doFinal(decodedata);
		   return new String(orginaldata);
	   }
}
