package com.bis.sourcelead.resumeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.si.util.RegEx;


@Component
public class AddressHelper {
public String getAddress(String text) { 
	String address ="";
	String brb = text.toString();	
	String regx = "\\bAddress\\b.+\\b[0-9]{6}\\b";
	Pattern p1  = Pattern.compile(regx); 				
	Matcher m1 = p1.matcher(brb.replaceAll("\n|:", " ").replaceAll(RegEx.ELIMINATE_KEYWORDS.toString(), ""));
	if(m1.find()) {
		address = m1.group(0).replaceAll("\\bAddress\\b|[|/]|\\bPresent\\b|\\bPRESENT\\b|\\bPermanent\\b|\\bPERMANENT\\b|\t|\\bLocal\\b", "").replace("  ", " ");
	} 
	else{
		String regx5 = "\\bAddress\\b.+\\bIndia\\b";
		Pattern p6  = Pattern.compile(regx5); 				
		Matcher m6 = p6.matcher(brb.replaceAll("\n", " "));
		if(m6.find()) {
			address = m6.group(0).replaceAll("\\bAddress\\b|[|/]|\\bPresent\\b|\\bPRESENT\\b|\\bPermanent\\b|\\bPERMANENT\\b|\t|\\bLocal\\b", "").replace("  ", " ");
		
	}else{
		String regx1  = "(\\bDOOR NO\\b|\\bFLAT NO\\b|\\bDR. NO\\b)\\w.+\\b[0-9]{6}\\b";
		//String regx1 = "\\bADDRESS\\b.+\\b[0-9]{6}\\b";
		Pattern p2 = Pattern.compile(regx1,Pattern.CASE_INSENSITIVE);
	Matcher m2 = p2.matcher(brb.replaceAll("\n", ""));
	if(m2.find()) {
		address = m2.group(0);
	}
	else {
		String regx2 =  "\\W\\b[0-9]{3}\\b.+\\b[0-9]{6}\\b";
		Pattern p3 = Pattern.compile(regx2,Pattern.CASE_INSENSITIVE);
		Matcher m3 = p3.matcher(brb.replaceAll("\n", " "));
		if(m3.find()) {
			address = m3.group(0);
		}
			
		else {
			String regx4 = "\\bNo\\b.+\\b[0-9]{6}\\b";
			Pattern p5 = Pattern.compile(regx4,Pattern.CASE_INSENSITIVE);
			Matcher m5 = p5.matcher(brb.replaceAll(",", " "));
			if(m5.find()) {
				address = m5.group(0);
			}
		else {
				String regx3 = "\\w.+\\b[0-9]{6}\\b";
				Pattern p4 = Pattern.compile(regx3,Pattern.CASE_INSENSITIVE);
				Matcher m4 = p4.matcher(brb.replaceAll("\n", " "));
				if(m4.find()) {
					address = m4.group(0);
				}
				
			}
		}
	}
	}
	}
	String address1 = address.replaceAll(RegEx.ELIMINATE_KEYWORDS.toString(), "").replaceAll(RegEx.EMAIL.toString(),"").replaceAll("\\bEmail\\b", "");
 	return address1;
}

public String getZipcode(String address) {
	String Zipcode = "";
	String regx = "\\b[0-9]{6}\\b";
	Pattern p1 = Pattern.compile(regx);
	Matcher m1 = p1.matcher(address);
	if(m1.find()) {
		Zipcode = m1.group(0);
	}
	else {
		Zipcode = "";
	}
	return Zipcode;
}

}
