package com.bis.sourcelead.resumeParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DesignationHelper {
	public static String getExistingDesignation() throws IOException
	{
		   BufferedReader chu = new BufferedReader(new FileReader("/home/java122/Downloads/txtfiles/designation_new.txt"));
		   String des = "";
		   try {
			    StringBuilder bc = new StringBuilder();
			    String line = chu.readLine();
 
			    while (line != null) {
			    	bc.append(line); 
			    	bc.append(System.lineSeparator());
			        line = chu.readLine();
			    }
			    des = bc.toString();
			} finally {
				chu.close();
			}
			return des;
	}
	
	public static Set<String> getMatchedDesignation(String text, List<String> DesignationTokens) throws IOException {
	       Set<String> designation = new HashSet<>();
			String patternString = "\\b(" + StringUtils.join(DesignationTokens, "|") + ")\\b";
			Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				designation.add(matcher.group(1).toLowerCase());
			}
			return designation;
	   }
}
