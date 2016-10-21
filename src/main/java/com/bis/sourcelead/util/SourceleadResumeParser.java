package com.bis.sourcelead.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Writer;
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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
public class SourceleadResumeParser {
	
/**@param
 *  Sourcelead Resume Parser
 *  Using this we can able to get candidate details from upload resume
 *   
 *  @author: Naresh Bolisetty
 *  @date: 17-October-2016
 */
public  String getCandidateDetailsFromResume(byte[] source)
				throws IOException, SAXException, TikaException {
				
    	    BodyContentHandler htmlHandler = new BodyContentHandler();
    	    ByteArrayInputStream stream = new ByteArrayInputStream(source);
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, htmlHandler, metadata);
			String parsedString = htmlHandler.toString();
			ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
			String name = nameFinderFromParsedTextFile(parsedString);
			if(StringUtils.isNotEmpty(name) && name.lastIndexOf(" ") > 0){
				String firstName = name.substring(0,name.lastIndexOf(" "));
				String lastName = name.substring(name.lastIndexOf(" ")+1);
				jsonUtil.setName(firstName+"."+lastName);
			}
			else{
				jsonUtil.setName(name);
			}
			
			
			jsonUtil.setEmail(emailFinderFromParsedTextFile(parsedString));
			jsonUtil.setPhoneNumbers(phoneNumberFinderFromParsedTextFile(parsedString));
		    return toJSON(jsonUtil);
   }
    /**
	 * Name finder from parsed text file
	 * Here we used java regular expression logic for resolving name from text file 
	 */  
   private  String nameFinderFromParsedTextFile(String parsedString){
	   
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
			String[] tenLines = firstTenLines.split(System.getProperty("line.separator"));
			outerloop:{
			for(String line :tenLines){
				Matcher nameMatcher = Pattern.compile("([A-Z]{1}[A-Z]{1,30}[- ]{0,}|[A-Z]{1}[a-z]{1,30}[- ]{0,}|[A-Z]{1}[- \']{1}[A-Z]{0,1}  [a-z]{1,30}[- ]{0,1}|[a-z]{1,2}[ -\']{1}[A-Z]{1}[a-z]{1,30}){2,}").matcher(line);
				//Matcher nameMatcher = Pattern.compile("([A-Z]{1}[a-z]{1,30}[. -\']{1}[A-Z]{1,2}|[A-Z]{1}[A-Z]{1,30}[- ]{0,}|[A-Z]{1}[a-z]{1,30}[- ]{0,}|[A-Z]{1}[- \']{1}[A-Z]{0,1}  [a-z]{1,30}[- ]{0,1}|[A-Z]{1,2}[. -\']{1}[A-Z]{1}[a-z]{1,30}|[a-z]{1,2}[ -\']{1}[A-Z]{1}[a-z]{1,30})").matcher(firstTenLines); 
				//Matcher nameMatcher = Pattern.compile("([A-Z]{1,30}[- ]{0,}|[A-Z]{1}[A-Z]{1,30}[- ]{0,}|[A-Z]{1}[a-z]{1,30}[- ]{0,}|[A-Z]{1}[- \']{1}[A-Z]{0,1}  [a-z]{1,30}[- ]{0,1}|[a-z]{1,2}[ -\']{1}[A-Z]{1}[a-z]{1,30}){2,}").matcher(firstTenLines);
			    if(nameMatcher.find()){
				    	 if(StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum") || StringUtils.containsIgnoreCase(nameMatcher.group(), "objective") || StringUtils.containsIgnoreCase(nameMatcher.group(), "resume"))
				    		 continue;
				    	 name = nameMatcher.group();
				    	 break outerloop;
			    }
			    else{
			    	  nameMatcher = Pattern.compile("([A-Z]{1}[a-z]{1,30}[. -\']{1}[A-Z]{1,2}|[A-Z]{1,2}[. -\']{1}[A-Z]{1}[a-z]{1,30})").matcher(line);
			    	  while(nameMatcher.find()){
				    	 if(StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum") || StringUtils.containsIgnoreCase(nameMatcher.group(), "objective") || StringUtils.containsIgnoreCase(nameMatcher.group(), "resume"))
				    		 continue;
				    	 name = nameMatcher.group();
				    	 break outerloop;
				     }
			    }
				
				
			}
		   }
		}
		return name;
   }
   /**
 	 * Email finder from parsed text file
 	 * Here we used java regular expression logic for resolving email from text file 
 	 */
   private  String emailFinderFromParsedTextFile(String parsedString){
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
  
    /**
  	 * phone number finder from parsed text file
  	 * Here we used libphonenumber class for resolving phone numbers from text file 
  	 * (source:com.googlecode.libphonenumber.libphonenumber7.7.jar)
  	 */
   private  HashSet<String> phoneNumberFinderFromParsedTextFile(String parsedString){
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

	
	 /**
	 * this method converts list to Json String
	 * @param obj
	 * @return
	 */
		public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
	
}