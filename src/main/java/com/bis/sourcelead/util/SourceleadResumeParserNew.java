package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.FileInfo;
import com.google.gson.Gson;


@Component
public class SourceleadResumeParserNew {
	
	
	/**
	 * 
	 * @author Mahaveer Manoj
	 * @purpose Using 3rd party API, we are parsing resume(s)
	 * 
	 */
	
	
	@Value("${static.path}")
    private String staticPath;
	
	@Value("${env}")
	private String environment;
	
	@Value("${resumeParserUrlLocal}")
	private String resumeParserUrlLocal;
	
	@Value("${resumeParserUrlQa}")
	private String resumeParserUrlQa;
	
	public ResumeParserJsonUtil getCandidateDetailsFromResume(byte[] source, String country, String orgLocationDateFormat, String fileName) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			BodyContentHandler htmlHandler = new BodyContentHandler();
			ByteArrayInputStream stream = new ByteArrayInputStream(source);
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, htmlHandler, metadata);
			String parsedString = htmlHandler.toString();
			jsonUtil = getResumeParserJsonUtil(parsedString, orgLocationDateFormat, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}
	
	public ResumeParserJsonUtil getCandidateDetailsFromResume1(File file, String country, String orgLocationDateFormat, String fileName) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try (FileInputStream stream = new FileInputStream(file);) {
			BodyContentHandler htmlHandler = new BodyContentHandler();
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, htmlHandler, metadata);
			String parsedString = htmlHandler.toString();
			jsonUtil = getResumeParserJsonUtil(parsedString, orgLocationDateFormat, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}
	
	public ResumeParserJsonUtil getCandidateDetailsFromResumeForPublicPages(byte[] source, String fileName) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			BodyContentHandler htmlHandler = new BodyContentHandler();
			ByteArrayInputStream stream = new ByteArrayInputStream(source);
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, htmlHandler, metadata);
			String parsedString = htmlHandler.toString();
			jsonUtil = getResumeParserJsonUtilForPublicPage(parsedString, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}
	
	public ResumeParserJsonUtil getResumeParserJsonUtil(String resumeData, String orgLocationDateFormat, String fileName) {
		
		//splitting resume into multiple sections
		List<Integer> globalIndex = new LinkedList<>();
		List<Integer> objectiveIndex = getSectionIndex("OBJECTIVE", SourceleadResumeParserRegEx.OBJECTIVE, resumeData);
		List<Integer> educationIndex = getSectionIndex("EDUCATION", SourceleadResumeParserRegEx.EDUCATION, resumeData);
		List<Integer> experienceIndex = getSectionIndex("EXPERIENCE", SourceleadResumeParserRegEx.EXPERIENCE, resumeData);
		List<Integer> skillsIndex = getSectionIndex("SKILLS", SourceleadResumeParserRegEx.SKILLS, resumeData);
		List<Integer> strengthsIndex = getSectionIndex("STRENGTHS", SourceleadResumeParserRegEx.STRENGTHS, resumeData);
//		List<Integer> languageIndex = getSectionDetails(SourceleadResumeParserRegEx.LANGUAGE, parsedString);
		List<Integer> interestIndex = getSectionIndex("INTERESTS", SourceleadResumeParserRegEx.INTERESTS, resumeData);
		List<Integer> membershipIndex = getSectionIndex("MEMBERSHIPS", SourceleadResumeParserRegEx.MEMBERSHIP, resumeData);
		List<Integer> extraCurricularIndex = getSectionIndex("EXTRA_CURRICULARS", SourceleadResumeParserRegEx.EXTRA_CURRICULARS, resumeData);
//		List<Integer> designationIndex = getSectionDetails("DESIGNATION", SourceleadResumeParserRegEx.DESIGNATION_START, parsedString);

		/*Adding and sorting all indexes to global index list*/
		globalIndex.addAll(objectiveIndex);
		globalIndex.addAll(educationIndex);
		globalIndex.addAll(experienceIndex);
		globalIndex.addAll(skillsIndex);
		globalIndex.addAll(strengthsIndex);
//		globalIndex.addAll(languageIndex);
		globalIndex.addAll(interestIndex);
		globalIndex.addAll(membershipIndex);
		globalIndex.addAll(extraCurricularIndex);
//		globalIndex.addAll(designationIndex);
		Collections.sort(globalIndex);
		globalIndex = globalIndex.stream().distinct().collect(Collectors.toList());
//		System.out.println("globalIndex : " + globalIndex.toString());
		
		/*String summary = getSectionDetails(resumeData, objectiveIndex, globalIndex, RegEx.OBJECTIVE);
		System.out.println("summary " + summary);
		
		String education = getSectionDetails(resumeData, educationIndex, globalIndex, RegEx.EDUCATION);
		System.out.println("education " + education);
		
		String exp = getSectionDetails(resumeData, experienceIndex, globalIndex, RegEx.EXPERIENCE);
		System.out.println("exp" + exp);*/
		
		//getting values from resume string
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			String summary = getSectionDetails(resumeData, objectiveIndex, globalIndex, SourceleadResumeParserRegEx.OBJECTIVE);
//			System.out.println("Summary : " + summary);
			jsonUtil.setSummary(summary);

			//Email
			String email = getEmailFromTextFile(resumeData);
			jsonUtil.setEmail(email);

			//PhoneNumber(s)
			Set<String> phoneNumberSet = new LinkedHashSet<>();
			String primaryMobile = getMobileNumber(resumeData);
			if(!primaryMobile.isEmpty()) {
				phoneNumberSet.add(primaryMobile.replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim());
				
				String resumeData1 = resumeData.substring(resumeData.indexOf(primaryMobile) + primaryMobile.length(), resumeData.length());
				String alternativeMobile = getMobileNumber(resumeData1).replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim();
				if(!alternativeMobile.isEmpty()) {
					phoneNumberSet.add(alternativeMobile);
				}
			}
			jsonUtil.setPhoneNumbers(phoneNumberSet);
			
			//Name
			String name = getNameFromTextFile(resumeData, email, phoneNumberSet, fileName);
			name = name.replaceAll("\\s+", " ");
			if (name.split(" ").length > 2) {
				jsonUtil.setFirstName(name.split(" ")[0]);
				jsonUtil.setMiddleName(name.split(" ")[1]);
				jsonUtil.setLastName(name.split(" ")[2]);
			} else if (name.split(" ").length == 2) {
				jsonUtil.setFirstName(name.split(" ")[0]);
				jsonUtil.setLastName(name.split(" ")[1]);
			} else {
				jsonUtil.setFirstName(name);
			}
			
			
			jsonUtil.setSkills(getSkillsFromTextFile(resumeData));
			
			//Designation
			jsonUtil.setDesignation(getKeywordsFromTextFile(resumeData, "designation.txt"));
			
			//Experience
			String experience = getExperienceFromTextFile(resumeData);
			jsonUtil.setTotalYearsExp(experience.split(":").length > 0 && !experience.split(":")[0].isEmpty() ? Integer.parseInt(experience.split(":")[0]) : 0);
			jsonUtil.setTotalMonthsExp(experience.split(":").length > 1 && !experience.split(":")[1].isEmpty() ? Integer.parseInt(experience.split(":")[1]) : 0);
			
			//Education
			String eduSSC = getKeywordsFromTextFile(resumeData, "education_SSC.txt");
			String edueIntermediate = getKeywordsFromTextFile(resumeData, "education_intermediate.txt");
			String eduUG = getKeywordsFromTextFile(resumeData, "education_ug.txt");
			String eduPG = getKeywordsFromTextFile(resumeData, "education_pg.txt");
			
			Set<String> educationsSet = new LinkedHashSet<>();
			if(!eduSSC.isEmpty()) {
				educationsSet.add(eduSSC.split(",")[0]);
			}
			
			if(!edueIntermediate.isEmpty()) {
				educationsSet.add(edueIntermediate.split(",")[0]);
			}
			
			if(!eduUG.isEmpty()) {
				String[] eduUGArray = eduUG.split(",");
				Set<String> set = new HashSet<>(Arrays.asList(eduUGArray));
				educationsSet.addAll(set);
			}
			
			if(!eduPG.isEmpty()) {
				String[] eduPGArray = eduPG.split(",");
				Set<String> set2 = new HashSet<>(Arrays.asList(eduPGArray));
				educationsSet.addAll(set2);
			}
			
			educationsSet.remove("been");
			educationsSet.remove("BEEN");
			educationsSet.remove("\\,");
			
			jsonUtil.setEducations(educationsSet);
//			System.out.println(educationsSet);
			
			//Address
			/*String country = getKeywordsFromTextFile(resumeData, "countries.txt");
			jsonUtil.setCountry(country);
			
			String state = getKeywordsFromTextFile(resumeData, "states.txt");
			jsonUtil.setState(state);*/
			
			//Gender
			String gender = getGenderFromTextFile(resumeData);
			jsonUtil.setGender(gender);
			
			//Martial Status
			String maritalStatus = getMartialStatusFromTextFile(resumeData);
			jsonUtil.setMaritalStatus(maritalStatus);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}
	
public ResumeParserJsonUtil getResumeParserJsonUtilForPublicPage(String resumeData, String fileName) {
		
		//splitting resume into multiple sections
		List<Integer> globalIndex = new LinkedList<>();
		List<Integer> objectiveIndex = getSectionIndex("OBJECTIVE", SourceleadResumeParserRegEx.OBJECTIVE, resumeData);
		List<Integer> educationIndex = getSectionIndex("EDUCATION", SourceleadResumeParserRegEx.EDUCATION, resumeData);
		List<Integer> experienceIndex = getSectionIndex("EXPERIENCE", SourceleadResumeParserRegEx.EXPERIENCE, resumeData);
		List<Integer> skillsIndex = getSectionIndex("SKILLS", SourceleadResumeParserRegEx.SKILLS, resumeData);
		List<Integer> strengthsIndex = getSectionIndex("STRENGTHS", SourceleadResumeParserRegEx.STRENGTHS, resumeData);
//		List<Integer> languageIndex = getSectionDetails(SourceleadResumeParserRegEx.LANGUAGE, parsedString);
		List<Integer> interestIndex = getSectionIndex("INTERESTS", SourceleadResumeParserRegEx.INTERESTS, resumeData);
		List<Integer> membershipIndex = getSectionIndex("MEMBERSHIPS", SourceleadResumeParserRegEx.MEMBERSHIP, resumeData);
		List<Integer> extraCurricularIndex = getSectionIndex("EXTRA_CURRICULARS", SourceleadResumeParserRegEx.EXTRA_CURRICULARS, resumeData);
//		List<Integer> designationIndex = getSectionDetails("DESIGNATION", SourceleadResumeParserRegEx.DESIGNATION_START, parsedString);

		/*Adding and sorting all indexes to global index list*/
		globalIndex.addAll(objectiveIndex);
		globalIndex.addAll(educationIndex);
		globalIndex.addAll(experienceIndex);
		globalIndex.addAll(skillsIndex);
		globalIndex.addAll(strengthsIndex);
//		globalIndex.addAll(languageIndex);
		globalIndex.addAll(interestIndex);
		globalIndex.addAll(membershipIndex);
		globalIndex.addAll(extraCurricularIndex);
//		globalIndex.addAll(designationIndex);
		Collections.sort(globalIndex);
		globalIndex = globalIndex.stream().distinct().collect(Collectors.toList());
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			String summary = getSectionDetails(resumeData, objectiveIndex, globalIndex, SourceleadResumeParserRegEx.OBJECTIVE);
//			System.out.println("Summary : " + summary);
			jsonUtil.setSummary(summary);

			//Email
			String email = getEmailFromTextFile(resumeData);
			jsonUtil.setEmail(email);

			//PhoneNumber(s)
			Set<String> phoneNumberSet = new LinkedHashSet<>();
			String primaryMobile = getMobileNumber(resumeData);
			if(!primaryMobile.isEmpty()) {
				phoneNumberSet.add(primaryMobile.replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim());
				
				String resumeData1 = resumeData.substring(resumeData.indexOf(primaryMobile) + primaryMobile.length(), resumeData.length());
				String alternativeMobile = getMobileNumber(resumeData1).replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim();
				if(!alternativeMobile.isEmpty()) {
					phoneNumberSet.add(alternativeMobile);
				}
			}
			jsonUtil.setPhoneNumbers(phoneNumberSet);
			
			//Name
			String name = getNameFromTextFile(resumeData, email, phoneNumberSet, fileName);
			name = name.replaceAll("\\s+", " ");
			if (name.split(" ").length > 2) {
				jsonUtil.setFirstName(name.split(" ")[0]);
				jsonUtil.setMiddleName(name.split(" ")[1]);
				jsonUtil.setLastName(name.split(" ")[2]);
			} else if (name.split(" ").length == 2) {
				jsonUtil.setFirstName(name.split(" ")[0]);
				jsonUtil.setLastName(name.split(" ")[1]);
			} else {
				jsonUtil.setFirstName(name);
			}
			
			
			jsonUtil.setSkills(getSkillsFromTextFile(resumeData));
			
			//Designation
			jsonUtil.setDesignation(getKeywordsFromTextFile(resumeData, "designation.txt"));
			
			//Experience
			String experience = getExperienceFromTextFile(resumeData);
			jsonUtil.setTotalYearsExp(experience.split(":").length > 0 && !experience.split(":")[0].isEmpty() ? Integer.parseInt(experience.split(":")[0]) : 0);
			jsonUtil.setTotalMonthsExp(experience.split(":").length > 1 && !experience.split(":")[1].isEmpty() ? Integer.parseInt(experience.split(":")[1]) : 0);
			
			//Education
			String eduSSC = getKeywordsFromTextFile(resumeData, "education_SSC.txt");
			String edueIntermediate = getKeywordsFromTextFile(resumeData, "education_intermediate.txt");
			String eduUG = getKeywordsFromTextFile(resumeData, "education_ug.txt");
			String eduPG = getKeywordsFromTextFile(resumeData, "education_pg.txt");
			
			Set<String> educationsSet = new LinkedHashSet<>();
			if(!eduSSC.isEmpty()) {
				educationsSet.add(eduSSC.split(",")[0]);
			}
			
			if(!edueIntermediate.isEmpty()) {
				educationsSet.add(edueIntermediate.split(",")[0]);
			}
			
			if(!eduUG.isEmpty()) {
				String[] eduUGArray = eduUG.split(",");
				Set<String> set = new HashSet<>(Arrays.asList(eduUGArray));
				educationsSet.addAll(set);
			}
			
			if(!eduPG.isEmpty()) {
				String[] eduPGArray = eduPG.split(",");
				Set<String> set2 = new HashSet<>(Arrays.asList(eduPGArray));
				educationsSet.addAll(set2);
			}
			
			educationsSet.remove("been");
			educationsSet.remove("BEEN");
			educationsSet.remove("\\,");
			
			jsonUtil.setEducations(educationsSet);
//			System.out.println(educationsSet);
			
			//Address
			/*String country = getKeywordsFromTextFile(resumeData, "countries.txt");
			jsonUtil.setCountry(country);
			
			String state = getKeywordsFromTextFile(resumeData, "states.txt");
			jsonUtil.setState(state);*/
			
			//Gender
			String gender = getGenderFromTextFile(resumeData);
			jsonUtil.setGender(gender);
			
			//Martial Status
			String maritalStatus = getMartialStatusFromTextFile(resumeData);
			jsonUtil.setMaritalStatus(maritalStatus);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}
	
	private static List<Integer> getSectionIndex(String name, SourceleadResumeParserRegEx regEx, String parsedString) {
		List<Integer> indexesOfSection = new ArrayList<>();
		for(String s : parsedString.split(System.getProperty("line.separator"))) {
			Pattern pattern = Pattern.compile(regEx.toString(), Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(s);
	        while (matcher.find()) {
	        	indexesOfSection.add(matcher.start());
	        }
		}
//		System.out.println(name + " : " + indexesOfSection.toString());
		return indexesOfSection;
	}
	
	private static String getSectionDetails(String parsedString, List<Integer> sectionIndex, List<Integer> globalIndex, SourceleadResumeParserRegEx regEx) {
		String details = "";
		try {
			for(int i = 0; i < globalIndex.size() - 1; i++) {
				for(Integer index : sectionIndex) {
					if(globalIndex.get(i) == index) {
						int start = index;
//						int end = i < globalIndex.size() ? globalIndex.get(i + 1) : parsedString.length();
//						int end = sectionIndex.size() > 1 ? sectionIndex.get(1) : (globalIndex.size() > i ? globalIndex.get(i + 1) : parsedString.length());
						int end = globalIndex.size() > i ? globalIndex.get(i + 1) : parsedString.length();
						String subStr = parsedString.substring(start, end);
						
						String eliminateWord = "";
						Pattern p = Pattern.compile(regEx.toString(), Pattern.CASE_INSENSITIVE);
						Matcher matcher = p.matcher(subStr);
						if(matcher.find()) {
							eliminateWord = matcher.group(0); 
						}
						subStr = subStr.replaceAll(eliminateWord, "");
						
						String[] str = subStr.split("\n\n");
						int lineCount = 0;
						for(String s : str) {
							if(lineCount < 2) {
								if(!s.isEmpty()) {
									details += s;
									lineCount++;
								}
							} else {
								break;
							}
						}
						
						return details.replaceAll("[^a-zA-Z0-9.\\s]", "").trim();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}
	
	/**
	 * Name finder from parsed text file Here we used java regular expression
	 * logic for resolving name from text file
	 */
	private String getNameFromTextFile(String parsedString, String email, Set<String> phoneNumberSet, String fileName) {
//		System.out.println(parsedString);
		String name = "";
		try {
			String nameString = "";
			String[] str = parsedString.replaceAll(SourceleadResumeParserRegEx.ELIMINATE_KEYWORDS.toString(), "").trim().split("\n");
			
			//Getting name based on filename
			fileName = fileName.replaceAll(SourceleadResumeParserRegEx.ELIMINATE_RESUME_NAME_KEYWORDS.toString(), "");
			nameSearch: {
				if(!fileName.isEmpty()) {
					String[] fileNameArray = fileName.split("[^a-zA-Z\\s\\.]+");
					for(String subFileName : fileNameArray) {
						Pattern pattern = Pattern.compile(SourceleadResumeParserRegEx.ELIMINATE_RESUME_NAME_KEYWORDS.toString(), Pattern.CASE_INSENSITIVE);
						Matcher matcher = pattern.matcher(subFileName);
						if(!subFileName.startsWith(".") && !matcher.find()) {
							String subStr = subFileName.substring(0, subFileName.length() > 5 ? 5 : subFileName.length()).toLowerCase();
							for(int i = 0; i < str.length; i++) {
								if(str[i].toLowerCase().contains(subStr)) {
									String tempName = str[i].toLowerCase().trim();
									tempName = tempName.substring(tempName.indexOf(subStr), tempName.length());
									if(email.isEmpty() || !tempName.toLowerCase().contains(email.toLowerCase())) {
										nameString = tempName.replaceAll("[^a-zA-Z\\s\\.]+", "");
										for(String eleString : Constants.ELIMINATE_NAME_KEYWORDS) {
											nameString = nameString.replaceAll("(?i)" + eleString, "").trim();
										}
										break nameSearch;
									}
								}
							}
						}
					}
				}
			}
			
			//Getting name based on RegEx
			if(nameString.isEmpty()) {
				Pattern pattern = Pattern.compile(SourceleadResumeParserRegEx.NAME.toString(), Pattern.CASE_INSENSITIVE);
				for(int i = 0; i < str.length; i++) {
					Matcher matcher = pattern.matcher(str[i]);
					if(matcher.find()) {
						nameString = str[i].replaceAll(matcher.group(0), "").replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
						break;
					}
				}
			}
			
			//Getting name based on lines near to emailId
			if(nameString.isEmpty() && !email.isEmpty()) {
				int emailRowIndex = 0;
				int empty = 0;
				for(int i = 0; i < str.length; i++) {
					if(str[i].contains(email)) {
	//					System.out.println("Email found at line : " + i);
						emailRowIndex = i;
						break;
					} else if(str[i].isEmpty()) {
						empty++;
					}
				}
				
				/**
				 *  If emailID found with in 10 lines, then fetch name from 1st line
				 *	Else fetch name from 4 lines prior to EmailID
				 **/ 
				
				/*int index = emailRowIndex - empty < 10 ? 0 : emailRowIndex - 4;
				for(int i = index; i <= index + 4; i++) {
					if(!str[i].isEmpty()) {
						nameString += (nameString.isEmpty() ? "" : "\n") + str[i].replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
					}
				}*/
				
				/**
				 * finding name (starting from line above email found line to 1st line)
				 * **/
				for(int i = emailRowIndex - 1; i > 0; i--) {
					if(!str[i].isEmpty()) {
						/**
						 * If str[i] contains phone number string, eliminate that line
						 **/
						for(String phoneNumber : phoneNumberSet) {
							if(!str[i].contains(phoneNumber)) {
								nameString += (nameString.isEmpty() ? "" : "\n") + str[i].replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
							}
						}
					}
				}
			}
//			System.out.println("Name String : " + nameString);
			
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
						line = line.trim();
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
				pattern = Pattern.compile(SourceleadResumeParserRegEx.ELIMINATE_RESUME_NAME_KEYWORDS.toString(), Pattern.CASE_INSENSITIVE);
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
	
	public String getEmailFromTextFile(String parsedString) {
		String email = "";
		Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(parsedString);
		if (matcher.find()) {
			email = matcher.group().replaceAll("\\s", "").toLowerCase();
		} else {
			p = Pattern.compile("\\b[A-Z0-9\\s._%+-]+@[A-Z0-9\\s.-]+\\.[A-Z\\s]{2,8}\\b", Pattern.CASE_INSENSITIVE);
			matcher = p.matcher(parsedString);
			if (matcher.find()) {
				email = matcher.group().replaceAll("\\s", "").toLowerCase();
			}
		}
		return email;
	}
	
	public String getMobileNumber(String text) throws Exception {
		String mobile = "";
		String regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*+\\b\\d{10}\\b";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			mobile = matcher.group();
		} else {
			regEx = "\\b\\d{10}\\b";
			pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(text);
			if (matcher.find()) {
				mobile = matcher.group(0);
			} else {
				regEx = "\\W+\\b[0-9]{1}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*+\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
				pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(text);
				if (matcher.find()) {
					mobile = matcher.group(0);
				} else {
					regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
					pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // euro no.
					matcher = pattern.matcher(text);
					if (matcher.find()) {
						mobile = matcher.group(0);
					} else {
						regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
						pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 3x2 1x4 no.
						matcher = pattern.matcher(text);
						if (matcher.find()) {
							mobile = matcher.group();
						} else {
							regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
							pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 3x2 1x4 no.
							matcher = pattern.matcher(text);
							if (matcher.find()) {
								mobile = matcher.group(0);
							} else {
								regEx = "\\W*\\d+\\W\\d\\W*\\s*\\d+\\W*\\s*\\d+\\W*\\s*\\b[0-9]{3}\\b";
								pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 4 X 3 X 3 
								matcher = pattern.matcher(text);
								if (matcher.find()) {
									mobile = matcher.group(0);
								}
							}
						}
					}
				}
			}
		}
//		return mobile.replaceAll("[^\\w ]", "").trim();
		return mobile;
	}
	
	@SuppressWarnings("resource")
	private String getKeywordsFromTextFile(String parsedString, String textFile) {
		String result = "";
		Set<String> keywordsSet = new HashSet<>();
		try {
			File file = new File(staticPath+"/dictionary/"+textFile);
			String keywords = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (br.readLine() != null) {
				keywords += br.readLine();
			}
			List<String> keywordsList = Arrays.asList(keywords.split("\\s*,\\s*"));
			String keywordsPattern = "\\b(" + StringUtils.join(keywordsList, "|") + ")\\b";
			Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				keywordsSet.add(matcher.group(1).toLowerCase());
			}
			result = String.join(",", keywordsSet);
			result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	private Set<String> getSkillsFromTextFile(String parsedString) {
//		String skills = "";
		Set<String> skillsSet = new HashSet<>();
		try {
			File file = new File(staticPath+"/dictionary/skills.txt");
			String skillKeywords = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (br.readLine() != null) {
				skillKeywords += br.readLine();
			}
			List<String> skillsList = Arrays.asList(skillKeywords.split("\\s*,\\s*"));
			String skillsPattern = "\\b(" + StringUtils.join(skillsList, "|") + ")\\b";
			Pattern pattern = Pattern.compile(skillsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				skillsSet.add(matcher.group(1).toLowerCase());
			}
			/*skills = String.join(", ", skillsSet);
			skills = skills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skillsSet;
	}
	
	
	
	private String getExperienceFromTextFile(String text) {
		String years = "", months = "";
		try {
			Pattern p = Pattern.compile("\\d+\\W+\\s+(?:months?|years?|yrs?) | "
					+ "\\d+\\s+years?\\s+(?:and\\s*)?\\d+\\s+months?|\\d+\\s+(?:months?|years?|yrs?) |"
					+ " \\d+\\.?\\d*\\W+\\s+(?:months?|years?|yrs?) |\\d+\\.?\\d*\\s+(?:months?|years?|yrs?)|"
					+ "\\d+\\W*((?i)plus(?-i))\\W*\\s+(?:months?|years?|yrs?)", Pattern.CASE_INSENSITIVE);
			
	
			Matcher matcher = p.matcher(text);
			String experience = "";
			if (matcher.find()) {
				experience = matcher.group(0).trim();
			} else {
				String exp = "\\w+\\s+\\bplus\\b+\\s+(?:months?|years?)";
				Pattern t = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
				Matcher n = t.matcher(text);
				if (n.find()) {
					experience = n.group(0).trim();
				}
			}
			if(!experience.isEmpty()) {
				String[] expArray = experience.split("\\.");
				years = expArray[0].trim();
				years = years.length() > 2 ? years.substring(0, 2) : years;
				if(expArray.length > 1) {
					months = expArray[1].trim();
					months = months.length() > 2 ? months.substring(0, 2) : months;
				}
			}
	
			String r="(\\d+\\.?\\d*)";
			Pattern t = Pattern.compile(r, Pattern.CASE_INSENSITIVE);
			Matcher n = t.matcher(years);
			if (n.find()) {
				years = n.group(0);
			}
			
			Matcher m = t.matcher(months);
			if (m.find()) {
				months = m.group(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return years + ":" + months;
	}
	
	private String getGenderFromTextFile(String text) {
		String gender = "";
		try {
			
			Pattern pattern = Pattern.compile("\\bMr.\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				gender = "Male";
			} else {
				pattern = Pattern.compile("\\bmale\\b", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(text);
				if (matcher.find()) {
					gender = "Male";
				} else {
					pattern = Pattern.compile("\\bfemale\\b", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(text);
					if (matcher.find()) {
						gender = "Female";
					} else {
						pattern = Pattern.compile("\\bMiss\\b", Pattern.CASE_INSENSITIVE);
						matcher = pattern.matcher(text);
						if (matcher.find()) {
							gender = "Female";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gender;
	}
	
	private String getMartialStatusFromTextFile(String text) {
		String martialStatus = "";
		try {
			Pattern pattern = Pattern.compile("\\bsingle|solo|unmarried|un married\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				martialStatus = "Single";
			} else {
				pattern = Pattern.compile("\\bmarried\\b", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(text);
				if (matcher.find()) {
					martialStatus = "Married";
				} else {
					pattern = Pattern.compile("\\bdivorsed\\b", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(text);
					if (matcher.find()) {
						martialStatus = "Divorsed";
					} else {
						pattern = Pattern.compile("\\bseparated\\b", Pattern.CASE_INSENSITIVE);
						matcher = pattern.matcher(text);
						if (matcher.find()) {
							martialStatus = "Separated";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return martialStatus;
	}

	/**
	 * Getting Candidate details from Resume using resume parer service
	 * @param file
	 * @param fileInfo 
	 * @return ResumeParserJsonUtil
	 */
	public ResumeParserJsonUtil getCandidateDetailsFromResumeParser(File file, FileInfo fileInfo) {
		ResumeParserJsonUtil resume = new ResumeParserJsonUtil();
		try {
			String baseServicesUrl = "";
			if("qa".equalsIgnoreCase(environment)) {
				baseServicesUrl = resumeParserUrlQa;
			}else {
				baseServicesUrl = resumeParserUrlLocal;
			}
			final String url = baseServicesUrl+Constants.RESUME_PARSE_FILE_URL;
			 MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		      bodyMap.add("fileName",getUserFileResource(file));
		      HttpHeaders headers = new HttpHeaders();
		      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
	            
		      RestTemplate restTemplate = new RestTemplate();
		      ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
		      Gson gson = new Gson();
		       resume = gson.fromJson(response.getBody().toString(), ResumeParserJsonUtil.class);
		      resume.setFileInfo(fileInfo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resume;
	}
	
	public static Resource getUserFileResource(File file) throws IOException {
	      //to upload in-memory bytes use ByteArrayResource instead
	      return new FileSystemResource(file);
	  }

}