package com.bis.sourcelead.resumeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.si.util.RegEx;

@Component
public class FirstNameHelper {

	public String getNameFromTextFile(String parsedString,String email) {
		String name = "";
		try {
			String nameString = "";
			parsedString = parsedString.replaceAll(RegEx.ELIMINATE_KEYWORDS.toString(), "");
			
			//Getting name based on filename
			String regEx = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
//			String regEx = "^[a-zA-Z\\\\s]*$";
//			String regEx = "^[\\p{L} .'-]+$";
			Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(nameString);
			if(matcher.find()) {
				name = matcher.group(0).replaceAll("\\.", " ");
			}
			if (name.isEmpty()) {
				String[] totalLines = parsedString.split("\n");
				String firstTenLines = "";
				String lineSeparator = "";
				String ecludeNameRegex = "((?i)Name|name|NAME|\\.)";
				int count = 0;
				boolean nameMatchFound = false;
				for (String line : totalLines) {
					String nameRegex = "((?i)Name|name|NAME)(\\:|\\-|\\.|\\s)+";
					if (line.matches("(\\W*)" + nameRegex + ".*")) {
						// nameMatchFound = true;
						String[] nameLineArray = line.split(nameRegex);
						if (nameLineArray.length > 1) {
							String nameContent = nameLineArray[1].replaceAll("[^a-zA-Z0-9\\s]+", "").trim();
							if (StringUtils.isNotEmpty(nameContent) && Character.isUpperCase(nameContent.charAt(0))) {
								name = nameLineArray[1].replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
								// nameMatchFound = false;
							}
						}
						name = name.replaceAll(ecludeNameRegex, " ").trim();
						if (StringUtils.isNotEmpty(name)) {
							break;
						} else {
							nameMatchFound = true;
							continue;
						}
					} else if (nameMatchFound) {
						String regex = "[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}";
						Matcher nameMatcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(line);
						if (nameMatcher.find()) {
							if (StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "objective")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "resume")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "cell")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "mobile")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "email")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Summary")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Profile")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Address")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Date")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "page")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "engineer")
									|| StringUtils.containsIgnoreCase(nameMatcher.group(), "text"))
								continue;
							name = nameMatcher.group();
							name = name.replaceAll(ecludeNameRegex, " ").trim();
							break;
						} else {
							continue;
						}
					}

					if (count < 10) {
						if (StringUtils.isNotEmpty(firstTenLines)) {
							lineSeparator = System.getProperty("line.separator");
						}
						line = line.trim().replace(".","");
						String[] words = line.split("(\\,|\\s){2,}");
						if (words != null && words.length > 1) {
							for (int i = 0; i < words.length; i++) {
								if (StringUtils.containsIgnoreCase(words[i], "curriculum")
										|| StringUtils.containsIgnoreCase(words[i], "objective")
										|| StringUtils.containsIgnoreCase(words[i], "resume")
										|| StringUtils.containsIgnoreCase(words[i], "cell")
										|| StringUtils.containsIgnoreCase(words[i], "mobile")
										|| StringUtils.containsIgnoreCase(words[i], "email")
										|| StringUtils.containsIgnoreCase(words[i], "Summary")
										|| StringUtils.containsIgnoreCase(words[i], "Profile")
										|| StringUtils.containsIgnoreCase(words[i], "Address")
										|| StringUtils.containsIgnoreCase(words[i], "Date")
										|| StringUtils.containsIgnoreCase(words[i], "page")
										|| StringUtils.containsIgnoreCase(words[i], "engineer")
										|| StringUtils.containsIgnoreCase(words[i], "text"))
									continue;
								line = line + System.getProperty("line.separator") + words[i];
							}
						}
						firstTenLines = firstTenLines + lineSeparator + line.replaceAll("[^a-zA-Z0-9\\s\\@\\.\\_]+", "").trim();
						count++;
					}
				}

				if (!StringUtils.isNotEmpty(name)) {
					if (StringUtils.isEmpty(lineSeparator)) {
						lineSeparator = "\n";
					}
					String[] tenLines = firstTenLines.split(lineSeparator);
					outerloop: {
						for (String line : tenLines) {
							String regex = "[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}";
							Matcher nameMatcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(line);
							if (nameMatcher.find()) {
								if (StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "objective")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "resume")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "cell")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "mobile")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "email")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Summary")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Profile")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Address")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Date")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "page")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "engineer")
										|| StringUtils.containsIgnoreCase(nameMatcher.group(), "text"))
									continue;
								name = nameMatcher.group();
								break outerloop;
							}
						}
					}
					name = name.replaceAll(ecludeNameRegex, " ").trim();
				}
			}
			if(!name.isEmpty()) {
				String eliminateWord = "";
				pattern = Pattern.compile(RegEx.ELIMINATE_RESUME_NAME_KEYWORDS.toString(), Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(name);
				if(matcher.find()) {
					eliminateWord = matcher.group(0); 
				}
				name = name.replaceAll(eliminateWord, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return CommonUtil.getCaptilizedText(name.trim());
	}
   
}
