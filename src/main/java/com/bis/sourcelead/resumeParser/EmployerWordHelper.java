package com.bis.sourcelead.resumeParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployerWordHelper {
public HashSet<String> getEmployer(String text) {
	String employer = "";
	String employer1 = "";
	HashSet<String> empo = new HashSet<String>();
	try {
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/cities.txt")));
		String keywords = "";
		String abcd = "";
		while (br.readLine() != null) {
			keywords += br.readLine();  	// assigned cities to keywords....
		}
		for(String a : text.split("\n")) {
			List<String> keywordsList = Arrays.asList(keywords.split("\\s*,\\s*"));    	//spitting keywords....
			String keywordsPattern = "\\b(" + StringUtils.join(keywordsList, "|") + ")\\b";   
			Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(a);
			if(matcher.find()) {
				String testcity = matcher.group(0);
				int end = matcher.end(0);
				String city = a.substring(0,end);
				System.out.println("city is :" +testcity+"   substring is  :" +city);
				String regx1 = "\\bfor\\b|\\bwith\\b|\\bin\\b|\\bat\\b.+\\b"+testcity+"\\b";
				Pattern p2 = Pattern.compile(regx1);
				Matcher m2 = p2.matcher(city);
				if(m2.find()) {
				abcd = m2.group().replaceAll("\\bin\\b|\\bwith\\b|\\bfor\\b|\\bat\\b|[.,():?/]", "").replace("name", "");
				System.out.println("matched substring : " +abcd);
				if(!"".equals(abcd)) {
					empo.add(abcd);
				}
				}
				else {	
					String regx = "\\w.+\\bLtd|Services|Solutions|Technologies|Limited\\b.+\\b"+testcity+"\\b";
					Pattern p1 = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
					Matcher m1 = p1.matcher(city);
					if(m1.find()) {
				      abcd  = m1.group(0).replace("Company", "").replaceAll("\t","").replace(":", "");
				      System.out.println("matched substring : " +abcd);
				      String test = "\\bsolutions|technologies|services|limited|ltd\\b";
				      Pattern pte = Pattern.compile(test,Pattern.CASE_INSENSITIVE);
				      Matcher mte = pte.matcher(abcd);
				      if(mte.find()) {
				    	  employer1 = mte.group(0);
				      }
				     String testing =  abcd.replace(employer1, "").replace("name", "");
				     if(!"".equals(testing)) {
				    	 empo.add(testing); 
				     }
					} 
					else {
						abcd = "";
						}
				}
		}
	}
	}
		catch (Exception e) {
			e.printStackTrace();
		}

	System.out.println("employers are : " +empo);
	return empo;

}
}
