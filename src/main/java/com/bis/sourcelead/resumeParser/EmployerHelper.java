package com.bis.sourcelead.resumeParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.si.util.RegEx;

/**
 * @author Sai sumanth
 * 
 * Get the skills dictinary to compare with resume skills
 * @return String
 * @throws IOException
 */
@Component
public class EmployerHelper {
	public List<Integer> getSectionIndex(String name, RegEx regEx, String parsedString) {
		List<Integer> indexesOfSection = new ArrayList<>();
		try {
		for(String s : parsedString.split(System.getProperty("line.separator"))) {
			//		Pattern pattern = Pattern.compile(regEx.toString(), Pattern.CASE_INSENSITIVE);
			Pattern pattern = Pattern.compile(regEx.toString());
			Matcher matcher = pattern.matcher(s);
			while (matcher.find()) {
				//        	indexesOfSection.add(matcher.start());
				indexesOfSection.add(parsedString.indexOf(matcher.group()));
				break;
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return indexesOfSection;
	}

	public String getSectionDetails(String parsedString, List<Integer> sectionIndex, List<Integer> globalIndex, RegEx regEx, int limit) {
		String details = "";
		try {
			for(int i = 0; i < globalIndex.size(); i++) {
				for(Integer index : sectionIndex) {
					if(globalIndex.get(i) == index) {
						int start = index;
						int end = i < globalIndex.size() ? ((i + 1) < globalIndex.size() ? globalIndex.get(i + 1) : parsedString.length()) : parsedString.length();
						//					int end = sectionIndex.size() > 1 ? sectionIndex.get(1) : parsedString.length();
						String sectionString = parsedString.substring(start, end);

						String eliminateWord = "";
						Pattern p = Pattern.compile(regEx.toString());
						Matcher matcher = p.matcher(sectionString);
						if(matcher.find()) {
							eliminateWord = matcher.group(0); 
						}
						sectionString = sectionString.replaceAll(eliminateWord, "").trim();

						/**
						 * if limit == 0, then return the entire sectionString
						 * else based on limit count, substring the sectionString
						 **/

						if(limit == 0) {
							details = sectionString;
						} else {
							String[] str = sectionString.split("\n\n");
							int lineCount = 0;
							for(String s : str) {
								if(lineCount < limit) {
									if(!s.isEmpty()) {
										details += s;
										lineCount++;
									}
								} else {
									break;
								}
							}
						}
						//					return removeSpecialChars ? details.replaceAll("[^a-zA-Z0-9.\\s]", "").trim() : details.trim();
						return details.trim();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}


public static HashSet<String> getEmployer(String experienceString) {
	 String abc = "(\\b\\d{1,2}\\D{0,3})?\\b(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|(Nov|Dec)(?:ember)?)\\D?(\\d{1,2}\\D?)?\\D?((19[7-9]\\d|20\\d{2})|\\d{2})\\s\\w*+\\W*\\s(\\b\\d{1,2}\\D{0,3}\\b)?(((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|(Nov|Dec)(?:ember)?)\\D?(\\d{1,2}\\D?)?\\D?((19[7-9]\\d|20\\d{2})|\\d{2}))|\\b(Till Date|tilldate|presentdate|present date|currentdate|current date|current|present)\\b)";
	 Pattern ab = Pattern.compile(abc,Pattern.CASE_INSENSITIVE);
	 String line="";
	 HashSet<String> abcd = new HashSet<>();	
	   String duration = "";
	   for(String a : experienceString.split(System.getProperty("line.separator"))) {
	      Matcher m = ab.matcher(a);
	      while (m.find()) {
	       int start = m.start(0);
	         int end = m.end(0);
	       // line = a.substring(0, e1);
	         duration += a.substring(start, end);
	         String s = "(\\bfor\\b|\\bwith\\b|\\bin\\b|\\bat\\b)\\s*\\D+(\\bLtd|Limited|Services|Solutions|Technologies\\b)";
	         Pattern p = Pattern.compile(s,Pattern.CASE_INSENSITIVE);
				Matcher m1 = p.matcher(a.substring(0, end));
				if (m1.find( )) {
					line = m1.group(0).replaceAll("\\bin\\b|\\bwith\\b|\\bfor\\b|\\bat\\b|\\t|[.,():?/]", "");
				abcd.add(line);
				}
				else {
					String s1 = "(\\bfor\\b|\\bwith\\b|\\bin\\b|\\bat\\b)\\s*\\D+[.,(]";

					Pattern p1 = Pattern.compile(s1,Pattern.CASE_INSENSITIVE);
					Matcher m2 = p1.matcher(a.substring(0, end));
					if (m2.find( )) {
						line = m2.group(0).replaceAll("\\bin\\b|\\bwith\\b|\\bfor\\b|\\bat\\b|[.,():?/]", "");
						abcd.add(line);
					}

					else
					{

						String s2 = "\\w.+\\s*\\D+[.,(]";
						Pattern p2 = Pattern.compile(s2,Pattern.CASE_INSENSITIVE);
						Matcher m3 = p2.matcher(a.substring(0, end));
						if (m3.find( )) {
							line = m3.group(0).replaceAll("\\bin\\b|\\bwith\\b|\\bfor\\b|\\bat\\b|[.,():?/]", "");
							abcd.add(line);
						}
						else
						{
							String s3 = "(\\bfor\\b|\\bwith\\b|\\bin\\b|\\bat\\b)\\s*\\D+(\\bfrom|frm|since\\b)";
							Pattern p3 = Pattern.compile(s3,Pattern.CASE_INSENSITIVE);
							Matcher m4 = p3.matcher(a.substring(0,end));
							if (m4.find( )) {
								line = m4.group(0).replaceAll("\\bin\\b|\\bwith\\b|\\bfor\\b|\\bat\\b|\\bfrom\\b|\\bsince\\b|\\bfrm\\b|[.,():?/]", "");
								abcd.add(line);
							}
							else {
								line = "notfound";
							}
		
						}
					}
				}
			
			}
		}
	    
	 
return abcd;
}
}