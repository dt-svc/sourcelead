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

/**
 * 
 * @author rajasekhar
 *@Date 02-07-2019
 */
@Component
public class ParsedSkillsHelper {

	/**
	 * @author rajasekhar
	 * 
	 * Get the skills dictinary to compare with resume skills
	 * @return String
	 * @throws IOException
	 */
	public String getExistedSkills() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/skills.txt")));
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
	 
	  
	/**
	 * Compare skills with existed skills
	 * 
	 * @author rajasekhar
	 * 
	 * @param text
	 * @param skillTokens
	 * @return Set<String>
	 * @throws IOException
	 */
	public Set<String> getMatchedSkills(String text, List<String> skillTokens) throws IOException {
	       Set<String> skill = new HashSet<>();
			String patternString = "\\b(" + StringUtils.join(skillTokens, "|") + ")\\b";
			Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
			    skill.add(matcher.group(1).toLowerCase());
			}
			return skill;
	   }
   
}
