package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;


@Component
public class SourceleadResumeParserNew {
	
	/**
	 * 
	 * @author Mahaveer Manoj
	 * @purpose Using 3rd party API, we are parsing resume(s)
	 * 
	 */
	
	/*
	private String serviceURL = "http://cvxdemo.daxtra.com";
	private String account = "sourcelead";
	private boolean outputFormat = true; //true - JSON , false - XML

	public ResumeParserJsonUtil getCandidateDetailsFromResume(byte[] source, String country, String orgLocationDateFormat) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			CVXtractorService cvXtractorService = new CVXtractorService(serviceURL, account, outputFormat);
			String resumeData = cvXtractorService.ProcessCV(source);
			if (resumeData == "") {
				System.err.println("Error at processing :: " + cvXtractorService.getLastError());
			} else {
				jsonUtil = getResumeParserJsonUtil(resumeData, country, orgLocationDateFormat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}

	public ResumeParserJsonUtil getCandidateDetailsFromResume1(File file, String country, String orgLocationDateFormat) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			CVXtractorService cvXtractorService = new CVXtractorService(serviceURL, account, outputFormat);
			String resumeData = cvXtractorService.ProcessCV(file.getPath());
			if (resumeData == "") {
				System.err.println("Error at processing :: " + cvXtractorService.getLastError());
			} else {
				jsonUtil = getResumeParserJsonUtil(resumeData, country, orgLocationDateFormat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}
	
	
	public ResumeParserJsonUtil getResumeParserJsonUtil(String resumeData, String country, String orgLocationDateFormat) {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			JSONObject jsonObject = new JSONObject(resumeData);
			JSONObject resume = jsonObject.getJSONObject("Resume");
			if(resume != null) {
				JSONObject structuredResume = resume.getJSONObject("StructuredResume");
				if (structuredResume != null) {
//					System.out.println(structuredResume.toString());
					//Name & Gender
					if(structuredResume.has("PersonName")) {
						JSONObject personName = structuredResume.getJSONObject("PersonName");
						if(personName != null) {
							jsonUtil.setFirstName(personName.has("GivenName") ? personName.getString("GivenName") : "");
							jsonUtil.setMiddleName(personName.has("MiddleName") ? ((String) personName.getJSONArray("MiddleName").get(0)) : "");
							jsonUtil.setLastName(personName.has("FamilyName") ? personName.getString("FamilyName") : "");
							
							jsonUtil.setGender(personName.has("sex") ? personName.getString("sex") : "");
						}
					}
					
					//PhoneNumber, Email & Address
					if(structuredResume.has("ContactMethod")) {
						JSONObject contactMethod = structuredResume.getJSONObject("ContactMethod");
						if(contactMethod != null) {
							//Phone Number
							Set<String> phoneNumberSet = new LinkedHashSet<>();
							if(contactMethod.has("Telephone_home")) {
								phoneNumberSet.add(contactMethod.getString("Telephone_home"));
							} else if(contactMethod.has("Telephone_mobile")) {
								phoneNumberSet.add(contactMethod.getString("Telephone_mobile"));
							}
							
							if(contactMethod.has("Telephone_alt")) {
								phoneNumberSet.add(contactMethod.getString("Telephone_alt"));
							}
							jsonUtil.setPhoneNumbers(phoneNumberSet);

							//Email
							String email = "";
							if(contactMethod.has("InternetEmailAddress_main")) {
								email = contactMethod.getString("InternetEmailAddress_main");
							}
							if(contactMethod.has("InternetEmailAddress_alt")) {
								email += (email.isEmpty() ? "" : "<<>>") + contactMethod.getString("InternetEmailAddress_alt");
							}
							jsonUtil.setEmail(email);
							
							//Address
							if(contactMethod.has("PostalAddress_main")) {
								JSONObject postalAddress = contactMethod.getJSONObject("PostalAddress_main");
								if(postalAddress != null) {
									jsonUtil.setAddressLine1(postalAddress.has("AddressLine") ? postalAddress.getString("AddressLine") : "");
									jsonUtil.setCity(postalAddress.has("Municipality") ? postalAddress.getString("Municipality") : "");
									jsonUtil.setState(postalAddress.has("Region") ? postalAddress.getString("Region") : "");
									jsonUtil.setCountry(postalAddress.has("CountryCode") ? postalAddress.getString("CountryCode") : "");
									jsonUtil.setZipcode(postalAddress.has("PostalCode") ? postalAddress.getString("PostalCode") : "");
								}
							}
						}
					}
						
					//DOB
					if(structuredResume.has("DOB")) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat(orgLocationDateFormat);
							String dob = sdf.format(Constants.DF_YMD.parse(structuredResume.getString("DOB")));
							jsonUtil.setDob(dob);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					//Marital Status
					jsonUtil.setMaritalStatus(structuredResume.has("MaritalStatus") ? structuredResume.getString("MaritalStatus") : "");
					
					//Skills
					if(structuredResume.has("Competency")) {
						Set<String> skillSet = new HashSet<>();
						JSONArray competency = structuredResume.getJSONArray("Competency");
						if (competency != null) {
							for (int i = 0; i < competency.length(); i++) {
								if (competency.getJSONObject(i).has("description") && (competency.getJSONObject(i).getString("description").equalsIgnoreCase("Skill") || competency.getJSONObject(i).getString("description").equalsIgnoreCase("Skill > IT"))) {
									skillSet.add(competency.getJSONObject(i).getString("skillName"));
								}
							}
						}
						jsonUtil.setSkills(skillSet);
					}
					
					String designation = "";
					//Employeement History
					if(structuredResume.has("EmploymentHistory")) {
						Set<String> employeerSet = new HashSet<>();
						JSONArray employmentHistory = structuredResume.getJSONArray("EmploymentHistory");
						if (employmentHistory != null) {
							for (int i = 0; i < employmentHistory.length(); i++) {
								if(employmentHistory.getJSONObject(i).has("OrgName")) {
									if(employmentHistory.getJSONObject(i).has("Title") && designation.isEmpty()) {
										designation = (String) employmentHistory.getJSONObject(i).getJSONArray("Title").get(0);
									}
									employeerSet.add(employmentHistory.getJSONObject(i).getString("OrgName"));
								}
							}
						}
						jsonUtil.setEmployeers(employeerSet);
					}
					jsonUtil.setDesignation(designation);
					
					//Education History
					if(structuredResume.has("EducationHistory")) {
						Set<String> educationSet = new HashSet<>();
						JSONArray educationHistory = structuredResume.getJSONArray("EducationHistory");
						if (educationHistory != null) {
							for (int i = 0; i < educationHistory.length(); i++) {
								if(educationHistory.getJSONObject(i).has("Degree")) {
									JSONObject degreeObject = educationHistory.getJSONObject(i).getJSONObject("Degree");
									if(degreeObject != null) {
										if(degreeObject.has("DegreeName")) {
											educationSet.add(degreeObject.getString("DegreeName"));
										}
									}
								}
							}
						}
						jsonUtil.setEducations(educationSet);
					}
					
					//Summary
					jsonUtil.setSummary(structuredResume.has("ExecutiveSummary") ? structuredResume.getString("ExecutiveSummary") : "");
				}
				
				//Experience
				JSONObject experienceSummary = resume.getJSONObject("ExperienceSummary");
				if(experienceSummary != null) {
					int totalYearsOfExp = experienceSummary.has("TotalYearsOfWorkExperience") ? experienceSummary.getInt("TotalYearsOfWorkExperience") : 0;
					int totalMonthsOfExp = experienceSummary.has("TotalMonthsOfWorkExperience") ? experienceSummary.getInt("TotalMonthsOfWorkExperience") : 0;
					if(totalMonthsOfExp > 12) {
						jsonUtil.setTotalYearsExp(totalMonthsOfExp / 12);
						jsonUtil.setTotalMonthsExp(totalMonthsOfExp % 12);
					} else {
						jsonUtil.setTotalYearsExp(totalYearsOfExp);
						jsonUtil.setTotalMonthsExp(totalMonthsOfExp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonUtil;
	}*/
	
	
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
	
	public ResumeParserJsonUtil getCandidateDetailsFromResume1(File file, String country, String orgLocationDateFormat) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try (FileInputStream stream = new FileInputStream(file);) {
			BodyContentHandler htmlHandler = new BodyContentHandler();
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			parser.parse(stream, htmlHandler, metadata);
			String parsedString = htmlHandler.toString();
			jsonUtil = getResumeParserJsonUtil(parsedString, orgLocationDateFormat, file.getName());
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
			
			jsonUtil.setSummary(getSectionDetails(resumeData, objectiveIndex, globalIndex, SourceleadResumeParserRegEx.OBJECTIVE));

			//Email
			String email = getEmailFromTextFile(resumeData);
			jsonUtil.setEmail(email);
			
			//Name
			String name = getNameFromTextFile(resumeData, email, fileName);
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
			
			Set<String> phoneNumberSet = new LinkedHashSet<>();
			String primaryMobile = getMobileNumber(resumeData);
			phoneNumberSet.add(primaryMobile.replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim());
			if(!primaryMobile.isEmpty()) {
				String resumeData1 = resumeData.substring(resumeData.indexOf(primaryMobile) + primaryMobile.length(), resumeData.length());
				phoneNumberSet.add(getMobileNumber(resumeData1).replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim());
			}
			jsonUtil.setPhoneNumbers(phoneNumberSet);
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
			
			//Employeers
			/*String employeers = getKeywordsFromTextFile(resumeData, "companies.txt");
			System.out.println("Employeers : " + employeers);*/
			
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
	private String getNameFromTextFile(String parsedString, String email, String fileName) {
//		System.out.println(parsedString);
		String name = "";
		try {
			String nameString = "";
			String[] str = parsedString.replaceAll(SourceleadResumeParserRegEx.ELIMINATE_KEYWORDS.toString(), "").trim().split("\n");
			
			//Getting name based on filename
			if(!fileName.isEmpty()) {
				String subStr = fileName.substring(0, 4);
				for(int i = 0; i < str.length; i++) {
					if(str[i].toLowerCase().contains(subStr.toLowerCase())) {
						String tempName = str[i].trim();
						if(email.isEmpty() || !tempName.toLowerCase().contains(email.toLowerCase())) {
							nameString = tempName.replaceAll("[^a-zA-Z\\s\\.]+", "");
							for(String eleString : Constants.ELIMINATE_NAME_KEYWORDS) {
								nameString = nameString.replaceAll("(?i)" + eleString, "").trim();
							}
							break;
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
				int index = emailRowIndex - empty < 10 ? 0 : emailRowIndex - 4;
				for(int i = index; i <= index + 4; i++) {
					if(!str[i].isEmpty()) {
						nameString += (nameString.isEmpty() ? "" : "\n") + str[i].replaceAll("[^a-zA-Z\\s\\.]+", "").trim();
					}
				}
			}
			System.out.println("Name String : " + nameString);
			
			String regEx = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
//			String regEx = "^[a-zA-Z\\\\s]*$";
//			String regEx = "^[\\p{L} .'-]+$";
			Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(nameString);
			if(matcher.find()) {
				name = matcher.group(0).replaceAll("\\.", " ");
			}
			if (name.isEmpty()) {
				String[] totalLines = nameString.split("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
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
			String fn = "/dictionary/" + textFile;
			URL url = getClass().getResource(fn);
			File file = new File(url.toURI());
			
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
			String fn = "/dictionary/skills.txt";
			URL url = getClass().getResource(fn);
			File file = new File(url.toURI());
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
	
	/*@SuppressWarnings("resource")
	private String getDesignationFromTextFile(String parsedString) {
		String designation = "";
		Set<String> designationSet = new HashSet<>();
		try {
			String fn = "/dictionary/designation.txt";
			URL url = getClass().getResource(fn);
			File file = new File(url.toURI());
			String designationKeywords = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (br.readLine() != null) {
				designationKeywords += br.readLine();
			}
			List<String> designaitonList = Arrays.asList(designationKeywords.split("\\s*,\\s*"));
			String desginationPattern = "\\b(" + StringUtils.join(designaitonList, "|") + ")\\b";
			Pattern pattern = Pattern.compile(desginationPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				System.out.println("Desig : " + matcher.group(1));
				designationSet.add(matcher.group(1).toLowerCase());
			}
			
			designation = String.join(", ", designationSet);
			designation = designation.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return designation;
	}*/
	
	private String getExperienceFromTextFile(String text) {
		String years = "", months = "";
		try {
			Pattern p = Pattern.compile("\\d+\\W+\\s+(?:months?|years?|yrs?) | "
					+ "\\d+\\s+years?\\s+(?:and\\s*)?\\d+\\s+months?|\\d+\\s+(?:months?|years?|yrs?) |"
					+ " \\d+\\.?\\d*\\W+\\s+(?:months?|years?|yrs?) |\\d+\\.?\\d*\\s+(?:months?|years?|yrs?)|"
					+ "\\d+\\W*((?i)plus(?-i))\\W*\\s+(?:months?|years?|yrs?)", Pattern.CASE_INSENSITIVE);
			
			/*Pattern p = Pattern.compile("\\d+\\W+\\s*\\S*(?:months?|years?|yrs?|mnths?) | "
					+ "\\d+\\s*\\S*years?\\s+(?:and\\s*)?\\d+\\s*\\S*months?|\\d+\\s+(?:months?|years?|yrs?|mnths?) | "
					+ "\\d+\\.?\\d*\\W+\\s*\\S*(?:months?|years?|yrs?|mnths?)"
					+ "  | \\d+\\s*\\S*yrs?\\s+(?:and\\s*)?\\d+\\s*\\S*mnths?|\\d+\\s+(?:months?|years?|yrs?|mnths?)| "
					+ "\\d+\\s*\\S*((?i)plus(?-i))\\s*\\S*(?:months?|years?|yrs?|mnths?) | "
					+ "\\d+\\s*\\S*(?:months?|years?|yrs?|mnths?)", Pattern.CASE_INSENSITIVE);*/
	
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
			/*String r="^(?:m|M|male|Male|f|F|female|Female)$";
			Pattern pattern = Pattern.compile(r, Pattern.CASE_INSENSITIVE);
			Matcher n = pattern.matcher(text);
			if (n.find()) {
				gender = n.group(0);
			}*/
			
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
			/*String r="^(?:single|Single|married|Married|divorsed|Divorsed|separated|Separated)$";
			Pattern pattern = Pattern.compile(r, Pattern.CASE_INSENSITIVE);
			Matcher n = pattern.matcher(text);
			if (n.find()) {
				martialStatus = n.group(0);
			}*/
			
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

}