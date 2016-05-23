/**
 * 
 */
package com.bis.sourcelead.util;

import java.security.MessageDigest;

/**
 * Testing class for implementing the encryption
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */
public class SHA512EncryptionTest {

	public static void main(String args[]) throws Exception {

		String password = "anypasswordstring";
		System.out.println("Password: '" + password + "' in SHA512 is:");
		System.out.println(hashText(password));

	}

	public static String convertByteToHex(byte data[]) {

		StringBuffer hexData = new StringBuffer();
		for (int byteIndex = 0; byteIndex < data.length; byteIndex++) {
			hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
		}

		return hexData.toString();

	}

	public static String hashText(String textToHash) throws Exception {

		// TODO if you want to change the encryption style other than SHA-512,
		// change it here.
		// as MD2, MD5, SHA, SHA-1, SHA-384, SHA-256 etc
		final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
		sha512.update(textToHash.getBytes());

		return convertByteToHex(sha512.digest());

	}

}
