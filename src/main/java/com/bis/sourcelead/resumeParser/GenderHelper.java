package com.bis.sourcelead.resumeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class GenderHelper {
	public String getGenderFromTextFile(String text) {
		String gender = "";
		try {
			
			Pattern pattern = Pattern.compile("\\bMr\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				gender = "Male";
			} else {
				pattern = Pattern.compile("\\bmale\\b", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(text);
				if (matcher.find()) {
					gender = "Male";
				} else {
					pattern = Pattern.compile("\\bfemale\\b", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(text);
					if (matcher.find()) {
						gender = "Female";
					} else {
						pattern = Pattern.compile("\\bMiss\\b", Pattern.CASE_INSENSITIVE);
						matcher = pattern.matcher(text);
						if (matcher.find()) {
							gender = "Female";
							}
				
							}
						}
				}
					
				
			
	

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gender;
	}
}
