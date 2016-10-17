package com.bis.sourcelead.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
public class SourceleadResumeParser {
	
   private static String parseToTextFileUsingApacheTikka(String file)
				throws IOException, SAXException, TikaException {
				
    	    BodyContentHandler htmlHandler = new BodyContentHandler();
			InputStream stream = new FileInputStream(file);
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, htmlHandler, metadata);
		    return htmlHandler.toString();
   }
   
   private static String nameFinderFromParsedTextFile(String parsedString){
	   
		String[] totalLines = parsedString.split(System.getProperty("line.separator"));
		String firstTenLines = "";
		String name = "";
		boolean nameMatchFound = false;
		int count = 0;
		for(String line : totalLines){
			if(count < 10){
				String lineSeparator ="";
				if(StringUtils.isNotEmpty(firstTenLines)){
					 lineSeparator =System.getProperty("line.separator");
				}
				String finalLine = "";
				line = line.trim();
				String[] words = line.split("\\s{2,}");
				if(words != null &&  words.length > 1){
					for(String word : words){
						finalLine = finalLine+" "+word.replaceAll("\\s", "");
					}
					line = finalLine;
					
				}
			
			    firstTenLines = firstTenLines+lineSeparator+line.replaceAll("[^a-zA-Z0-9\\s\\@\\.\\_]+", "").trim();
				count++;
			}
			String nameRegex = "((?i)Name|name|NAME)";
			if(line.matches("(\\W*)"+nameRegex+".*")){
				nameMatchFound =true;
				String[] nameLineArray =  line.split(nameRegex);
				if(nameLineArray.length>1){
					String nameContent =nameLineArray[1].replaceAll("[^a-zA-Z0-9\\s]+", "").trim();
					if(StringUtils.isNotEmpty(nameContent) && Character.isUpperCase(nameContent.charAt(0))){
						name = nameLineArray[1].replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
						nameMatchFound = false;
					}
					
				}
			}
			else if(nameMatchFound){
		    	String nameContent =line.replaceAll("[^a-zA-Z0-9\\s\\@\\.\\_]+", "").trim();
		    	if(StringUtils.isNotEmpty(nameContent) && Character.isUpperCase(nameContent.charAt(0)))
					name = nameContent.replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
					nameMatchFound = false;
		    }
		}
		
		if(!StringUtils.isNotEmpty(name)){
			Matcher nameMatcher = Pattern.compile("([A-Z]{1}[A-Z]{1,30}[- ]{0,}|[A-Z]{1}[a-z]{1,30}[- ]{0,}|[A-Z]{1}[- \']{1}[A-Z]{0,1}  [a-z]{1,30}[- ]{0,1}|[a-z]{1,2}[ -\']{1}[A-Z]{1}[a-z]{1,30}){2,}").matcher(firstTenLines);
			 //Matcher nameMatcher = Pattern.compile("([A-Z]{1,30}[- ]{0,}|[A-Z]{1}[A-Z]{1,30}[- ]{0,}|[A-Z]{1}[a-z]{1,30}[- ]{0,}|[A-Z]{1}[- \']{1}[A-Z]{0,1}  [a-z]{1,30}[- ]{0,1}|[a-z]{1,2}[ -\']{1}[A-Z]{1}[a-z]{1,30}){2,}").matcher(firstTenLines);
		     while(nameMatcher.find()){
		    	 if(StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum") || StringUtils.containsIgnoreCase(nameMatcher.group(), "objective") || StringUtils.containsIgnoreCase(nameMatcher.group(), "resume"))
		    		 continue;
		    	 name = nameMatcher.group();
		    	 break;
		     }
		}
		return name;
   }

   private static String emailFinderFromParsedTextFile(String parsedString){
	   String email = null;
	   Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(parsedString);
		if(matcher.find()) {
			email = matcher.group().replaceAll("\\s", "").toLowerCase();
		}
		else{
			p = Pattern.compile("\\b[A-Z0-9\\s._%+-]+@[A-Z0-9\\s.-]+\\.[A-Z\\s]{2,8}\\b",Pattern.CASE_INSENSITIVE);
		    matcher = p.matcher(parsedString);
		    if(matcher.find()){
		    	email = matcher.group().replaceAll("\\s", "").toLowerCase();
		    }
		}
		return email;
   }
  
   private static HashSet<String> phoneNumberFinderFromParsedTextFile(String parsedString){
        HashSet<String> phoneNumbers = new HashSet<String>();
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		Iterator<PhoneNumberMatch> usMatches = phoneUtil.findNumbers(parsedString, "US").iterator();
		if(usMatches!= null){
			while(usMatches.hasNext()){
				phoneNumbers.add(usMatches.next().number().getNationalNumber()+"");
			}
			
		}
		else{
			Iterator<PhoneNumberMatch> inMatches = phoneUtil.findNumbers(parsedString, "IN").iterator();
			while(inMatches.hasNext()){
				phoneNumbers.add(inMatches.next().number().getNationalNumber()+"");
			}
		}
		return phoneNumbers;
   }

	
	public static void main(String[] args) {
		try {
			String parsedString = parseToTextFileUsingApacheTikka("/home/Resumes/NareshBolisetty_Java_5yr_Exp_CV.doc");
            System.out.println(nameFinderFromParsedTextFile(parsedString));
            System.out.println(emailFinderFromParsedTextFile(parsedString));
            System.out.println(phoneNumberFinderFromParsedTextFile(parsedString));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}