package com.bis.sourcelead.resumeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class MaritalStatusHelper {
	
	public String getMartialStatusFromTextFile(String text) {
		String martialStatus = "";
		try {
			Pattern pattern = Pattern.compile("\\bsingle|solo|unmarried|un married\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				martialStatus = "Single";
			} else {
				pattern = Pattern.compile("\\bmarried\\b", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(text);
				if (matcher.find()) {
					martialStatus = "Married";
				} else {
					pattern = Pattern.compile("\\bdivorsed\\b", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(text);
					if (matcher.find()) {
						martialStatus = "Divorsed";
					} else {
						pattern = Pattern.compile("\\bseparated\\b", Pattern.CASE_INSENSITIVE);
						matcher = pattern.matcher(text);
						if (matcher.find()) {
							martialStatus = "Separated";
						}
							else {
								martialStatus = "Not Mentioned";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return martialStatus;
	}


}
