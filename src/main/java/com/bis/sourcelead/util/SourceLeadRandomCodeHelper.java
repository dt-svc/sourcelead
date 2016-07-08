package com.bis.sourcelead.util;
import org.apache.commons.lang.RandomStringUtils;

public class SourceLeadRandomCodeHelper {

	public static String generateRandomUserCode() {
		String userCode = RandomStringUtils.randomAlphanumeric(8);
		return userCode;
	}
	
	public static String generateRandomOrgCode() {
		String orgCode = RandomStringUtils.randomAlphanumeric(4);
		return orgCode;
	}
	
	public static String generateRandomVerificationCode() {
		String verificatinCode = RandomStringUtils.randomAlphabetic(5);
		return verificatinCode;
	}
}
