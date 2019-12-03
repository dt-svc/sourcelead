package com.bis.sourcelead.resumeParser;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class MobileHelper {
	String mobile = "";
	int end;
	public HashSet<String> getMobileNumber(String text) throws Exception {
		String mobile = "";
		HashSet<String> mobilenum = new HashSet<>();	
		
		String regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*+\\b\\d{10}\\b";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			mobile = matcher.group();
			end = matcher.end();
			} else {
			regEx = "\\b\\d{10,12}\\b";
			pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(text);
			if (matcher.find()) {
				mobile = matcher.group(0);
				end = matcher.end();
			} else {
				regEx = "\\W+\\b[0-9]{1}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*+\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
				pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(text);
				if (matcher.find()) {
					mobile = matcher.group(0);
					end = matcher.end();
				} else {
					regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
					pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // euro no.
					matcher = pattern.matcher(text);
					if (matcher.find()) {
						mobile = matcher.group(0);
						end = matcher.end();
					} else {
						regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
						pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 3x2 1x4 no.
						matcher = pattern.matcher(text);
						if (matcher.find()) {
							mobile = matcher.group();
							end = matcher.end();
						} else {
							regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
							pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 3x2 1x4 no.
							matcher = pattern.matcher(text);
							if (matcher.find()) {
								mobile = matcher.group(0);
								end = matcher.end();
							} else {
								regEx = "\\W+\\b[0-9]{4}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b";
								pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 4 X 3 X 3 
								matcher = pattern.matcher(text);
								if (matcher.find()) {
									mobile = matcher.group(0);
									end = matcher.end();
								}
							}
						}
					}
				}
			}
		}
		mobilenum.add(mobile.replace(" ", "").replace(":", ""));
		String test = getnewMobileNum(text,end);
		if(!"".equals(test)) {
			mobilenum.add(test);			
		}
		return mobilenum;
}
	private String getnewMobileNum(String text, int end) {
		String newmobile = ""; // to get 2nd mobile num if availabe
		String abc = text.substring(end,text.length());
		String regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*+\\b\\d{10}\\b";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group(0);
		} else {
		regEx = "\\b\\d{10,12}\\b";
		pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group(0);
		} else {
		regEx = "\\W+\\b[0-9]{1}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*+\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
		pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group(0);
		} else {
		regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
		pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // euro no.
		matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group(0);
		} else {
		regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
		pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 3x2 1x4 no.
		matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group();
		} else {
		regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
		pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 3x2 1x4 no.
		matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group(0);
		} else {
		regEx = "\\W+\\b[0-9]{4}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b";
		pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // no. 4 X 3 X 3 
		matcher = pattern.matcher(abc);
		if (matcher.find()) {
		newmobile = matcher.group(0);
		}
		}
		}
		}
		}
		}
		}
		return newmobile;
		}
		
}
  
