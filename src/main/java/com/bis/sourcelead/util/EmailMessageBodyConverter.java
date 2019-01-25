package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EntityType;
import com.bis.sourcelead.service.ClassFieldService;

/**
 * 
 * @author Prasad salina
 *
 */
@Repository
public class EmailMessageBodyConverter {
	
	@Autowired 
	 ClassFieldService classFieldService;
	 
	public String convertBodyOfEmailTemplate(String body,Map<String, String> globalVarsMap) throws Exception {
		
		Matcher matcher = Pattern.compile("(\\{\\{)(.*?)(\\}\\})").matcher(body);
		List<String> delimitStr = new ArrayList<String>();
		StringBuilder tempStr = new StringBuilder(body);
		while (matcher.find())
		{
			String delimeter = matcher.group();
		    if(!isNullOrEmpty(delimeter)) {
			    ClassField classField = classFieldService.getClassFieldByDelimiterStr(delimeter);
			    if(classField != null) {
				    String temp = classField.getFieldName();
				    delimitStr.add(temp);
			    }
		    }
		}
		/*List<ClassField> classFieldList = classFieldService.getClassFieldsByUniqueName(37L);
		for(ClassField clsField:classFieldList){
			String temp = clsField.getFieldName();
		    delimitStr.add(temp);
		}*/
		if(delimitStr != null && !delimitStr.isEmpty()) {
			
			StringBuilder sb = new StringBuilder();
			Matcher matches = Pattern.compile("(\\{\\{)(.*?)(\\}\\})").matcher(body);
			
			for(String test : delimitStr) {
				String flag = globalVarsMap.get(test);
				if(!isNullOrEmpty(flag)){
					sb.append(flag).append("");
				}
				if(matches.find()) {
				String tests = matches.group();
				
				int first = tests.indexOf(tests);
				int last = tests.indexOf(tests)+tests.length();
				
				tempStr.replace(tempStr.indexOf(tests), tempStr.indexOf(tests)+tests.length(), sb.toString());
				
				if(test != null &&  (("facebookLink".equals(test)) || ("linkedInLink".equals(test)) || ("twitterLink".equals(test)) 
                        || ("googleLink".equals(test)) || ("supportLink".equals(test)))){

                sb.delete(0, sb.length());
                sb.setLength(0);
				}
				else{
					sb.delete(first, last);
					sb.setLength(0);
				 }
				
				}
			}
		}
		
		return tempStr.toString();
	}
	
	
public String convertBodyOfEmailScenerioTemplate(String body,Map<String, String> globalVarsMap,EmailScenario emailScenario) throws Exception {
		
		Matcher matcher = Pattern.compile("(\\[)(.*?)(\\])").matcher(body);
		List<String> delimitStr = new ArrayList<String>();
		StringBuilder tempStr = new StringBuilder(body);
		System.out.println("");
		while (matcher.find())
		{
			String delimeter = matcher.group();
		    if(!isNullOrEmpty(delimeter)) {
		    	ClassField classField = null;
		    	if(emailScenario.getParentId() != null){
		    		classField = classFieldService.getClassFieldByEmailScenerioIdAndfieldName(delimeter,emailScenario.getParentId().getId());
		    	}else{
		    		classField = classFieldService.getClassFieldByEmailScenerioIdAndfieldName(delimeter,emailScenario.getId());
		    	}
			    if(classField != null) {
				    String temp = classField.getFieldName();
				    delimitStr.add(temp);
			    }
		    }
		}
		/*List<ClassField> classFieldList = classFieldService.getClassFieldsByUniqueName(37L);
		for(ClassField clsField:classFieldList){
			String temp = clsField.getFieldName();
		    delimitStr.add(temp);
		}*/
		if(delimitStr != null && !delimitStr.isEmpty()) {
			
			StringBuilder sb = new StringBuilder();
			Matcher matches = Pattern.compile("(\\[)(.*?)(\\])").matcher(body);
			
			for(String test : delimitStr) {
				String flag = globalVarsMap.get(test);
				if(!isNullOrEmpty(flag)){
					sb.append(flag).append("");
				}
				if(matches.find()) {
				String tests = matches.group();
				
				int first = tests.indexOf(tests);
				int last = tests.indexOf(tests)+tests.length();
				
				tempStr.replace(tempStr.indexOf(tests), tempStr.indexOf(tests)+tests.length(), sb.toString());
				
				if(test != null &&  (("facebookLink".equals(test)) || ("linkedInLink".equals(test)) || ("twitterLink".equals(test)) 
                        || ("googleLink".equals(test)) || ("supportLink".equals(test)))){

                sb.delete(0, sb.length());
                sb.setLength(0);
				}
				else{
					sb.delete(first, last);
					sb.setLength(0);
				 }
				
				}
			}
		}
		
		return tempStr.toString();
	}
	
	
public String convertTempBodyOfEmailTemplate(String body,Map<String, String> globalVarsMap) throws Exception {
		
		Matcher matcher = Pattern.compile("(\\[)(.*?)(\\])").matcher(body);
		List<String> delimitStr = new ArrayList<String>();
		StringBuilder tempStr = new StringBuilder(body);
		/*while (matcher.find())
		{
			String delimeter = matcher.group();
		    if(!isNullOrEmpty(delimeter)) {
			    ClassField classField = classFieldService.getClassFieldByDelimiterStr(delimeter);
			    if(classField != null) {
				    String temp = classField.getFieldName();
				    delimitStr.add(temp);
			    }
		    }
		}*/
		List<ClassField> classFieldList = classFieldService.getClassFieldsByUniqueName(37L);
		for(ClassField clsField:classFieldList){
			String temp = clsField.getFieldName();
		    delimitStr.add(temp);
		}
		if(delimitStr != null && !delimitStr.isEmpty()) {
			
			StringBuilder sb = new StringBuilder();
			Matcher matches = Pattern.compile("(\\[)(.*?)(\\])").matcher(body);
			
			for(String test : delimitStr) {
				String flag = globalVarsMap.get(test);
				if(!isNullOrEmpty(flag)){
					sb.append(flag).append("");
				}
				if(matches.find()) {
				String tests = matches.group();
				
				int first = tests.indexOf(tests);
				int last = tests.indexOf(tests)+tests.length();
				
				tempStr.replace(tempStr.indexOf(tests), tempStr.indexOf(tests)+tests.length(), sb.toString());
				

				if(test != null && test != "" && ("loginURL".equals(test) || ("userEmailId".equals(test)) || ("orgName".equals(test)) || ("password".equals(test)) ||("vendorOrgName".equals(test))||("clientOrgName".equals(test)) 
                        || "acceptURL".equals(test) || ("userName".equals(test)) || "requirementName".equals(test) || "rejectURL".equals(test) || ("tentativeURL".equals(test)) || ("acceptLink".equals(test)) || ("rejectLink".equals(test)) 
                        || ("attachment".equals(test)) || ("RequirementTitle".equals(test)) || ("negotiateRateLink".equals(test)) || ("supportLink".equals(test)))){

                sb.delete(0, sb.length());
                sb.setLength(0);
				}
				else{
					sb.delete(first, last);
					sb.setLength(0);
				 }
				}
			}
		}
		
		return tempStr.toString();
	}

public String convertTemplateBodyOfEmailTemplate(String body,Map<String, String> globalVarsMap,EmailScenario emailScenario) throws Exception {
	
	Matcher matcher = Pattern.compile("(\\[)(.*?)(\\])").matcher(body);
	List<String> delimitStr = new ArrayList<String>();
	StringBuilder tempStr = new StringBuilder(body);
	/*while (matcher.find())
	{
		String delimeter = matcher.group();
	    if(!isNullOrEmpty(delimeter)) {
		    ClassField classField = classFieldService.getClassFieldByDelimiterStr(delimeter);
		    if(classField != null) {
			    String temp = classField.getFieldName();
			    delimitStr.add(temp);
		    }
	    }
	}*/
	while (matcher.find()){
		String delimeter = matcher.group();
	    if(!isNullOrEmpty(delimeter)) {
	    	ClassField classField = null;
	    	if(emailScenario.getParentId() != null){
	    		classField = classFieldService.getClassFieldByEmailScenerioIdAndfieldName(delimeter,emailScenario.getParentId().getId());
	    	}else{
	    		classField = classFieldService.getClassFieldByEmailScenerioIdAndfieldName(delimeter,emailScenario.getId());
	    	}
		    if(classField != null) {
			    String temp = classField.getFieldName();
			    delimitStr.add(temp);
		    }
	    }
	}
	/*List<ClassField> classFieldList = classFieldService.getClassFieldsByUniqueName(37L);
	for(ClassField clsField:classFieldList){
		String temp = clsField.getFieldName();
	    delimitStr.add(temp);
	}*/
	if(delimitStr != null && !delimitStr.isEmpty()) {
		
		StringBuilder sb = new StringBuilder();
		Matcher matches = Pattern.compile("(\\[)(.*?)(\\])").matcher(body);
		
		for(String test : delimitStr) {
			String flag = globalVarsMap.get(test);
			if(!isNullOrEmpty(flag)){
				sb.append(flag).append("");
			}
			if(matches.find()) {
			String tests = matches.group();
			
			int first = tests.indexOf(tests);
			int last = tests.indexOf(tests)+tests.length();
			tempStr.replace(tempStr.indexOf(tests), tempStr.indexOf(tests)+tests.length(), sb.toString());
			

			if(test != null && test != "" && ("loginURL".equals(test) || ("userEmailId".equals(test)) || ("orgName".equals(test)) || ("password".equals(test)) ||("vendorOrgName".equals(test))||("clientOrgName".equals(test)) 
                    || "acceptURL".equals(test) || ("userName".equals(test)) || "requirementName".equals(test) || "rejectURL".equals(test) || ("tentativeURL".equals(test)) || ("acceptLink".equals(test)) || ("rejectLink".equals(test)) 
                    || ("attachment".equals(test)) || ("RequirementTitle".equals(test)) || ("negotiateRateLink".equals(test)) || ("supportLink".equals(test)))){

            sb.delete(0, sb.length());
            sb.setLength(0);
			}
			else{
				sb.delete(first, last);
				sb.setLength(0);
			 }
			}
		}
	}
	
	return tempStr.toString();
}
	
   public static String convertTaskBody(String taskBody,HashMap<String,String> delimitMap) {
	   StringBuilder tempStr = new StringBuilder(taskBody);
		if(delimitMap != null && !delimitMap.isEmpty()){
			for(Entry<String, String> entry : delimitMap.entrySet()) {
				if(entry != null){
					Matcher matcher = Pattern.compile("(\\{\\{)(.*?)(\\}\\})").matcher(taskBody);
					
					while (matcher.find())
					{
						String delimeter = matcher.group();
						String tempDelimeter = matcher.group();
					    if(!isNullOrEmpty(delimeter) && !isNullOrEmpty(tempDelimeter)) {
					    	delimeter = delimeter.substring(2, delimeter.length());
					    	delimeter =  delimeter.substring(0,delimeter.length()-2);
						    if(!isNullOrEmpty(entry.getKey()) && entry.getKey().equalsIgnoreCase(delimeter) && !isNullOrEmpty(entry.getValue())){
								tempStr.replace(tempStr.indexOf(tempDelimeter), tempStr.indexOf(tempDelimeter)+tempDelimeter.length(),entry.getValue());
						    }
					    }
					}
				}
		    }
		}
		return tempStr.toString();
    }
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
