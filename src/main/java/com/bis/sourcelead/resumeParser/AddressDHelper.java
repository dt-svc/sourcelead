package com.bis.sourcelead.resumeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AddressDHelper {

	public String getAddress(String parsedString) {
		String emp = "";
		String emp1 = "";
		HashSet<String> address = new HashSet<>();
	try {
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/cities.txt")));
		String keywords = "";
		while (br.readLine() != null) {
			keywords += br.readLine();  	// assigned cities to keywords....
		}
		for(String a : parsedString.split("\n")) {
		List<String> keywordsList = Arrays.asList(keywords.split("\\s*,\\s*"));    	//spitting keywords....
		String keywordsPattern = "\\b(" + StringUtils.join(keywordsList, "|") + ")\\b";   
		Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(a);
		if(matcher.find()) {
			String abcd = matcher.group(0);
			int b = matcher.end(0);
			System.out.println(b);
			System.out.println(a.substring(0,b));
			String abc = a.substring(0,b);
			String regx = "\\bAddress\\b.+\\b"+abcd+"\\b";
			Pattern p1 = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
			Matcher m1 = p1.matcher(abc);
			if(m1.find()) {
				emp = m1.group(0);
			}
			else {
				String regx1 = "(\\bDOOR NO\\b|\\bFLAT NO\\b|\\bDR. NO\\b|\\bNo\\b)\\w.+\\b"+abcd+"\\b";
				Pattern p2 = Pattern.compile(regx1,Pattern.CASE_INSENSITIVE);
				Matcher m2 = p2.matcher(abc);
				if(m2.find()) {
				    emp = m2.group(0);
				}
				else {
					String regx2 = "\\W\\b[0-9]{3}\\b.+\\b"+abcd+"\\b";
					Pattern p3 = Pattern.compile(regx2);
					Matcher m3 = p3.matcher(abc);
					if(m3.find()) {
					    emp = m3.group(0);
					}
					else {
						System.out.println("Not working");
					}
				}
			}
		}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	emp1 = emp.replace("  ", " ").replaceAll("\\bAddress\\b|[|:/]|\\bPresent\\b|\\bPRESENT\\b|\\bPermanent\\b|\\bPERMANENT\\b|\t|\\bLocal\\b", "");
		return emp1;
		
	}
}
