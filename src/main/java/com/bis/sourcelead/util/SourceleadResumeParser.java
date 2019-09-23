//package com.bis.sourcelead.util;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.Writer;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.parser.AutoDetectParser;
//import org.apache.tika.sax.BodyContentHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.bis.sourcelead.service.LookUpDataService;
//import com.google.i18n.phonenumbers.PhoneNumberMatch;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
//import com.thoughtworks.xstream.io.json.JsonWriter;
//
//@Component
//public class SourceleadResumeParser {
//
//	/**
//	 * @param Sourcelead
//	 *            Resume Parser Using this we can able to get candidate details
//	 *            from upload resume
//	 * 
//	 * @author: Naresh Bolisetty
//	 * @throws GateException
//	 * @date: 17-October-2016
//	 */
//	@Autowired
//	LookUpDataService lookUpDataService;
//
//	public ResumeParserJsonUtil getCandidateDetailsFromResume(byte[] source, String country) throws Exception {
//		try {
//			BodyContentHandler htmlHandler = new BodyContentHandler();
//			ByteArrayInputStream stream = new ByteArrayInputStream(source);
//			AutoDetectParser parser = new AutoDetectParser();
//			Metadata metadata = new Metadata();
//			parser.parse(stream, htmlHandler, metadata);
//			String parsedString = htmlHandler.toString();
//			// System.out.println(parsedString);
//			ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
//			String name = nameFinderFromParsedTextFile(parsedString);
//			name = name.trim();
//			if (StringUtils.isNotEmpty(name) && name.lastIndexOf(" ") > 0) {
//				String firstName = name.substring(0, name.lastIndexOf(" "));
//				String lastName = name.substring(name.lastIndexOf(" ") + 1);
//				jsonUtil.setName(firstName + "." + lastName);
//			} else {
//				jsonUtil.setName(name);
//			}
//			jsonUtil.setEmail(emailFinderFromParsedTextFile(parsedString));
//			jsonUtil.setPhoneNumbers(phoneNumberFinderFromParsedTextFile(parsedString, country));
//	
//			HashSet<String> skillsList = skillFinderFromParsedTextFile(parsedString);
//			if (skillsList != null && !skillsList.isEmpty()) {
//				String skills = String.join(", ", skillsList);
//				skills = skills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
//				jsonUtil.setSkills(skills);
//			}
//			return jsonUtil;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public ResumeParserJsonUtil getCandidateDetailsFromResume1(File file, String country) throws Exception {
//		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
//		BodyContentHandler htmlHandler = new BodyContentHandler();
//		try (FileInputStream stream = new FileInputStream(file);) {
//			AutoDetectParser parser = new AutoDetectParser();
//			Metadata metadata = new Metadata();
//			parser.parse(stream, htmlHandler, metadata);
//			String parsedString = htmlHandler.toString();
//			// System.out.println(parsedString);
//
//			String name = nameFinderFromParsedTextFile(parsedString);
//			name = name.trim();
//			if (StringUtils.isNotEmpty(name) && name.lastIndexOf(" ") > 0) {
//				String firstName = name.substring(0, name.lastIndexOf(" "));
//				String lastName = name.substring(name.lastIndexOf(" ") + 1);
//				jsonUtil.setName(firstName + "." + lastName);
//			} else {
//				jsonUtil.setName(name);
//			}
//			jsonUtil.setEmail(emailFinderFromParsedTextFile(parsedString));
//			jsonUtil.setPhoneNumbers(phoneNumberFinderFromParsedTextFile(parsedString, country));
//
//			HashSet<String> skillsList = skillFinderFromParsedTextFile(parsedString);
//			if (skillsList != null && !skillsList.isEmpty()) {
//				String skills = String.join(", ", skillsList);
//				skills = skills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
//				jsonUtil.setSkills(skills);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return jsonUtil;
//	}
//
//	/**
//	 * Skill finder from parsed text file Here we used java regular expression
//	 * logic for resolving name from text file
//	 * 
//	 * @throws Exception
//	 */
//	private HashSet<String> skillFinderFromParsedTextFile(String parsedString)
//			throws Exception {
//		String[] totalLines = parsedString.split(System.getProperty("line.separator"));
//		HashSet<String> skills = new HashSet<String>();
//		HashSet<String> duplicateSkills = new HashSet<String>();
//		List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
//		for (String sLine : totalLines) {
//			for (String searchWord : skillsList) {
//				if (searchWord.equalsIgnoreCase("J++"))
//					continue;
//				Matcher skillMatcher = Pattern.compile("\\b" + searchWord + "\\b", Pattern.CASE_INSENSITIVE)
//						.matcher(sLine);
//				while (skillMatcher.find()) {
//					if (duplicateSkills.add(skillMatcher.group().toUpperCase())) {
//						skills.add(skillMatcher.group());
//					}
//				}
//			}
//		}
//		return skills;
//	}
//
//	/**
//	 * Name finder from parsed text file Here we used java regular expression
//	 * logic for resolving name from text file
//	 */
//	private String nameFinderFromParsedTextFile(String parsedString) {
//
//		String[] totalLines = parsedString.split("\n");
//		String firstTenLines = "";
//		String name = "";
//		String lineSeparator = "";
//		String ecludeNameRegex = "((?i)Name|name|NAME|\\.)";
//		int count = 0;
//		 boolean nameMatchFound = false;
//		for (String line : totalLines) {
//			String nameRegex = "((?i)Name|name|NAME)(\\:|\\-|\\.|\\s)+";
//			if(line.matches("(\\W*)" + nameRegex + ".*")) {
//				// nameMatchFound =true;
//				String[] nameLineArray = line.split(nameRegex);
//				if (nameLineArray.length > 1) {
//					String nameContent = nameLineArray[1].replaceAll("[^a-zA-Z0-9\\s]+", "").trim();
//					if (StringUtils.isNotEmpty(nameContent) && Character.isUpperCase(nameContent.charAt(0))) {
//						name = nameLineArray[1].replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
//						// nameMatchFound = false;
//					}
//
//				}
//				name = name.replaceAll(ecludeNameRegex, "");
//				if(StringUtils.isNotEmpty(name)) {
//					break;
//				}else {
//					nameMatchFound = true;
//					continue;
//				}
//				
//			}else if(nameMatchFound) {
//				String regex = "[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}";
//				Matcher nameMatcher = Pattern.compile(regex).matcher(line);
//				if (nameMatcher.find()) {
//					if (StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "objective")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "resume")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "cell")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "mobile")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "email")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Summary")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Profile")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Address")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Date")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "page")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "engineer")
//							|| StringUtils.containsIgnoreCase(nameMatcher.group(), "text"))
//						continue;
//					name = nameMatcher.group();
//					name = name.replaceAll(ecludeNameRegex, "");
//					break ;
//				}else {
//					continue;
//				}
//			}
//			
//			if (count < 10) {
//				
//				if (StringUtils.isNotEmpty(firstTenLines)) {
//					lineSeparator = System.getProperty("line.separator");
//				}
//				line = line.trim();
//				String[] words = line.split("(\\,|\\s){2,}");
//				if (words != null && words.length > 1) {
//
//					for (int i = 0; i < words.length; i++) {
//						if (StringUtils.containsIgnoreCase(words[i], "curriculum")
//								|| StringUtils.containsIgnoreCase(words[i], "objective")
//								|| StringUtils.containsIgnoreCase(words[i], "resume")
//								|| StringUtils.containsIgnoreCase(words[i], "cell")
//								|| StringUtils.containsIgnoreCase(words[i], "mobile")
//								|| StringUtils.containsIgnoreCase(words[i], "email")
//								|| StringUtils.containsIgnoreCase(words[i], "Summary")
//								|| StringUtils.containsIgnoreCase(words[i], "Profile")
//								|| StringUtils.containsIgnoreCase(words[i], "Address")
//								|| StringUtils.containsIgnoreCase(words[i], "Date")
//								|| StringUtils.containsIgnoreCase(words[i], "page")
//								|| StringUtils.containsIgnoreCase(words[i], "engineer")
//								|| StringUtils.containsIgnoreCase(words[i], "text"))
//							continue;
//						line = line + System.getProperty("line.separator") + words[i];
//					}
//				}
//
//				firstTenLines = firstTenLines + lineSeparator + line.replaceAll("[^a-zA-Z0-9\\s\\@\\.\\_]+", "").trim();
//				count++;
//			}
//			
//		}
//
//		if (!StringUtils.isNotEmpty(name)) {
//			if(StringUtils.isEmpty(lineSeparator)){
//				lineSeparator = "\n";
//			}
//			String[] tenLines = firstTenLines.split(lineSeparator);
//			outerloop: {
//				for (String line : tenLines) {
//					String regex = "[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}";
//					Matcher nameMatcher = Pattern.compile(regex).matcher(line);
//					if (nameMatcher.find()) {
//						if (StringUtils.containsIgnoreCase(nameMatcher.group(), "curriculum")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "objective")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "resume")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "cell")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "mobile")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "email")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Summary")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Profile")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Address")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "Date")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "page")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(), "engineer")
//								|| StringUtils.containsIgnoreCase(nameMatcher.group(),  "text"))
//							continue;
//						name = nameMatcher.group();
//						break outerloop;
//					}
//				}
//			}
//
//			name = name.replaceAll(ecludeNameRegex, "");
//		}
//		return name;
//	}
//
//	/**
//	 * Email finder from parsed text file Here we used java regular expression
//	 * logic for resolving email from text file
//	 */
//	private String emailFinderFromParsedTextFile(String parsedString) {
//		String email = null;
//		Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
//		Matcher matcher = p.matcher(parsedString);
//		if (matcher.find()) {
//			email = matcher.group().replaceAll("\\s", "").toLowerCase();
//		} else {
//			p = Pattern.compile("\\b[A-Z0-9\\s._%+-]+@[A-Z0-9\\s.-]+\\.[A-Z\\s]{2,8}\\b", Pattern.CASE_INSENSITIVE);
//			matcher = p.matcher(parsedString);
//			if (matcher.find()) {
//				email = matcher.group().replaceAll("\\s", "").toLowerCase();
//			}
//		}
//		return email;
//	}
//
//	/**
//	 * phone number finder from parsed text file Here we used libphonenumber
//	 * class for resolving phone numbers from text file
//	 * (source:com.googlecode.libphonenumber.libphonenumber7.7.jar)
//	 */
//	private HashSet<String> phoneNumberFinderFromParsedTextFile(String parsedString, String country) {
//		HashSet<String> phoneNumbers = new HashSet<String>();
//		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//		String countryCode = "US";
//		if (StringUtils.isNotEmpty(country) && country.equalsIgnoreCase("India")) {
//			countryCode = "IN";
//		}
//		Iterator<PhoneNumberMatch> indMatches = phoneUtil.findNumbers(parsedString, countryCode).iterator();
//		if (indMatches != null) {
//			while (indMatches.hasNext()) {
//				phoneNumbers.add(indMatches.next().number().getNationalNumber() + "");
//			}
//
//		} else {
//			if (StringUtils.isNotEmpty(country) && country.equalsIgnoreCase("India")) {
//				countryCode = "US";
//			} else {
//				countryCode = "IN";
//			}
//			Iterator<PhoneNumberMatch> inMatches = phoneUtil.findNumbers(parsedString, countryCode).iterator();
//			while (inMatches.hasNext()) {
//				phoneNumbers.add(inMatches.next().number().getNationalNumber() + "");
//			}
//		}
//		return phoneNumbers;
//	}
//
//	/**
//	 * this method converts list to Json String
//	 * 
//	 * @param obj
//	 * @return
//	 */
//	public String toJSON(Object obj) {
//		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
//			public HierarchicalStreamWriter createWriter(Writer writer) {
//				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
//			}
//		});
//		return xstream.toXML(obj);
//	}
//
//}