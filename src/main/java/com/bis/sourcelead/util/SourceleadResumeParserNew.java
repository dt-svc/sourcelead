package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.resumeParser.CVXtractorService;

@Component
public class SourceleadResumeParserNew {
	
	/**
	 * 
	 * @author Mahaveer Manoj
	 * @purpose Using 3rd party API, we are parsing resume(s)
	 * 
	 */
	
	private String serviceURL = "http://cvxdemo.daxtra.com";
	private String account = "sourcelead";
	private boolean outputFormat = true; //true - JSON , false - XML

	public ResumeParserJsonUtil getCandidateDetailsFromResume(byte[] source, String country, String orgLocationDateFormat) throws Exception {
		ResumeParserJsonUtil jsonUtil = new ResumeParserJsonUtil();
		try {
			CVXtractorService cvXtractorService = new CVXtractorService(serviceURL, account, outputFormat);
			String resumeData = cvXtractorService.ProcessCV(source);
			System.out.println(resumeData);
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
			System.out.println(resumeData);
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
	}
	
	public String getMobileNumber(String text) throws Exception {
		String mobile = "";
		String regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*+\\b\\d{10}\\b";
		Pattern pattern = Pattern.compile(regEx);
		Matcher found = pattern.matcher(text);
		if (found.find()) {
			mobile = found.group();
		} else {
			regEx = "\\b\\d{10}\\b";
			pattern = Pattern.compile(regEx);
			found = pattern.matcher(text);
			if (found.find()) {
				mobile = found.group(0);
			} else {
				regEx = "\\W+\\b[0-9]{1}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*+\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
				pattern = Pattern.compile(regEx);
				found = pattern.matcher(text);
				if (found.find()) {
					mobile = found.group(0);
				} else {
					regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{4}\\b";
					pattern = Pattern.compile(regEx); // euro no.
					found = pattern.matcher(text);
					if (found.find()) {
						mobile = found.group(0);
					} else {
						regEx = "\\W+\\b[0-9]{2}\\b\\s*\\W*\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
						pattern = Pattern.compile(regEx); // 3x2 1x4 no.
						found = pattern.matcher(text);
						if (found.find()) {
							mobile = found.group();
						} else {
							regEx = "\\W+\\b[0-9]{3}\\b\\s*\\W*\\b[0-9]{3}\\s*\\W*\\b[0-9]{4}\\b";
							pattern = Pattern.compile(regEx); // 3x2 1x4 no.
							found = pattern.matcher(text);
							if (found.find()) {
								mobile = found.group(0);
							} 
						}
					}
				}
			}
		}
//		return mobile.replaceAll("[^\\w ]", "").trim();
		return mobile.replaceAll(":", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim();
	}
	
	@SuppressWarnings("resource")
	private String getKeywordsFromTextFile(String parsedString, String textFile, String textFileLocation) {
		String result = "";
		Set<String> keywordsSet = new HashSet<>();
		try {
			File file = new File(textFileLocation, textFile);
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
			result = String.join(", ", keywordsSet);
			result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	private String getSkillsFromTextFile(String parsedString, String textFileLocation) {
		String skills = "";
		Set<String> skillsSet = new HashSet<>();
		try {
			File file = new File(textFileLocation, "Skills.txt");
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
			skills = String.join(", ", skillsSet);
			skills = skills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skills;
	}
	
	@SuppressWarnings("resource")
	private String getDesignationFromTextFile(String parsedString, String textFileLocation) {
		String designation = "";
		Set<String> designationSet = new HashSet<>();
		try {
			File file = new File(textFileLocation, "designation.txt");
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
	}
	
	public static String getExperienceFromTextFile(String text) throws Exception {
		Pattern p = Pattern.compile("\\d+\\W+\\s+(?:months?|years?) | "
				+ "\\d+\\s+years?\\s+(?:and\\s*)?\\d+\\s+months?|\\d+\\s+(?:months?|years?) |"
				+ " \\d+\\.?\\d*\\W+\\s+(?:months?|years?) |\\d+\\.?\\d*\\s+(?:months?|years?)|"
				+ "\\d+\\W*((?i)plus(?-i))\\W*\\s+(?:months?|years?)");

		Matcher found = p.matcher(text);
		String experience = "";
		String years = "", months = "";
		if (found.find()) {
			experience = found.group(0).trim();
		} else {
			String exp = "\\w+\\s+\\bplus\\b+\\s+(?:months?|years?)";
			Pattern t = Pattern.compile(exp);
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
		return years + ":" + months;
	}	

}