package com.bis.sourcelead.resumeParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
public class EducationUG {
	public String getExistedEducationalDetails2() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/education_ug.txt")));
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
	
	public Set<String> getMatchedEducationalDetails2(String parsedString, List<String> educationug) throws IOException {
		String result = "";
	     Set<String> eduug = new HashSet<>();
	     String keywordsPattern = "\\b(" + StringUtils.join(educationug, "|") + ")\\b";
			Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				eduug.add(matcher.group(1).toLowerCase());
			}
			result = String.join(",", eduug);
			result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		return eduug;
	}
}

