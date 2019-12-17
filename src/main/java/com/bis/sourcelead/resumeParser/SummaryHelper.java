package com.bis.sourcelead.resumeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.si.util.RegEx;

@Component
public class SummaryHelper {

	public List<Integer> getSectionIndex(String name, RegEx regEx, String parsedString) {
		List<Integer> indexesOfSection = new ArrayList<>();
		try {
		parsedString =	parsedString.toUpperCase();
		for(String s : parsedString.split(System.getProperty("line.separator"))) {
			// Pattern pattern = Pattern.compile(regEx.toString(), Pattern.CASE_INSENSITIVE);
			Pattern pattern = Pattern.compile(regEx.toString());
			Matcher matcher = pattern.matcher(s);
			while (matcher.find()) {
				// indexesOfSection.add(matcher.start());		
				indexesOfSection.add(parsedString.indexOf(matcher.group()));
				break;
			}
		}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return indexesOfSection;
		}
	
	
	public String getSummaryDetails(String parsedString, List<Integer> sectionIndex, List<Integer> globalIndex, RegEx regEx, int limit) {
		String details = "";
		try {
			for(int i = 0; i < globalIndex.size(); i++) {
				for(Integer index : sectionIndex) {
					if(globalIndex.get(i) == index) {
						int start = index;
						int end = i < globalIndex.size() ? ((i + 1) < globalIndex.size() ? globalIndex.get(i + 1) : parsedString.length()) : parsedString.length();
						// int end = sectionIndex.size() > 1 ? sectionIndex.get(1) : parsedString.length();
						int end1 = start+400;
						
						String sectionString = "";

						if(end>end1){
						   // to print only 400 indexes after the start( basically 4 lines)
							sectionString = parsedString.substring(start, end1); 
						}else {
							sectionString = parsedString.substring(start, end); 
						}
								

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
						// return removeSpecialChars ? details.replaceAll("[^a-zA-Z0-9.\\s]", "").trim() : details.trim();
						return details.trim();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}
}
