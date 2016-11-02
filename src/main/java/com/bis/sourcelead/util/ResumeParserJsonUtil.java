package com.bis.sourcelead.util;

import java.util.HashSet;

public class ResumeParserJsonUtil {
	
	String name;
	
	String email;
	
	HashSet<String> phoneNumbers;
	
	String skills;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public HashSet<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(HashSet<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

}