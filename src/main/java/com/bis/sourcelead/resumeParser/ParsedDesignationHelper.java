package com.bis.sourcelead.resumeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ParsedDesignationHelper {

	public String getExistedDesignation() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/designation.txt")));
  
		   String everything = "";
		   try {
			    StringBuilder sb = new StringBuilder();
			    
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			     everything = sb.toString();
			} finally {
			    br.close();
			}
			return everything;
	}
	
	public Set<String> getMatchedDesignation(String parsedString,List<String> designationTokens) {
		String result = "";
		 Set<String> designation = new HashSet<>();
		String keywordsPattern = "\\b(" + StringUtils.join(designationTokens, "|") + ")\\b";
		Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(parsedString);
		while (matcher.find()) {
			designation.add(matcher.group(1).toLowerCase());
		}
		result = String.join(",", designation);
		result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		
	return designation;
    }

	
}
