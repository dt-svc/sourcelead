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
public class EducationIntermediate {
	public String getExistedEducationalDetails4() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/education_intermediate.txt")));

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
	
	public Set<String> getMatchedEducationalDetails4(String parsedString, List<String> educationintermediate) throws IOException {
		String result = "";
	     Set<String> eduIntermediate = new HashSet<>();
	     String keywordsPattern = "\\b(" + StringUtils.join(educationintermediate, "|") + ")\\b";
			Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				eduIntermediate.add(matcher.group(1).toLowerCase());
			}
			result = String.join(",", eduIntermediate);
			result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		return eduIntermediate;
	}
}
