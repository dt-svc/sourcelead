package com.bis.sourcelead.parser;

import java.util.*;

public class Constants
{
	public static char[] UnwantedChar = {' ', ':', '-','\t'};

	public static final String NameKeyWord = "Name";
	public static final String ContactKeyWord = "Contact";
	public static final String EmailKeyWord = "Email";
	public static final String ExperienceKeyWord = "Experience";
	public static final String AddressKeyWord = "Address";
	public static final String SummaryKeyWord = "Summary";
	public static final String ObjectiveKeyWord = "Objective";
	public static final String EducationKeyWord = "Education";
	public static final String TechnicalSkillsKeyWord = "Technical Skills";
	public static final String ProfessionalKeyWord = "Professional";
	public static final String PersonalDetailsKeyWord = "Personal Details";
	public static final String DeclarationsKeyWord = "Declarations";
	public static final String ProjectDetailsKeyWord = "Project Details";
	public static final String HobbiesKeyword = "Hobbies";
	public static final String AchievementsKeyword = "Achievements";

	public static final String EmailPattern = "(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})";
	public static final String PhonePattern = "([\\-\\(\\)\\s0-9+]+){10}";

	public static String[] ExperienceIdentifiers = {"over", "around", "having", "more than", "nearly"};

	public static String[] ContactKeywords = {"ph:", "contact:", "mob:", "mobile:", "mobile no", "phone", "contact number", "contact no", "phone no", "cell no", "cell"};

	public static HashMap<String, String[]> KeywordTypes = new HashMap<String, String[]>()
	{
	};
	
	static {
		KeywordTypes.put(NameKeyWord, new String[] {"name:","name :"});
		KeywordTypes.put(ContactKeyWord, new String[]{"ph:","contact:","mob:","mobile:","mobile no","phone","contact number","contact no", "phone no", "cell no", "cell"});
		KeywordTypes.put(SummaryKeyWord, new String[]{"professional summary","career summary","experience summary","executive summary","summary","professional overview","profile snapshot","qualification highlight"});
		KeywordTypes.put(ObjectiveKeyWord, new String[]{"objective","career objective"});
		KeywordTypes.put(EducationKeyWord, new String[]{"education qualifications","education qualification","educational qualifications","educational qualification","academic education","academic qualification","scholastics","educational qualifications","educational qualification","educations","education & training","academic summary","qualifications","qualification","education","academics"});
		KeywordTypes.put(AchievementsKeyword, new String[]{"achievements", "paper publications", "accolades"});
		KeywordTypes.put(TechnicalSkillsKeyWord, new String[]{"technical skill set","technical skills","software skills","technical expertise", "core competencies", "technologies competencies", "technical proficiency","skill set","skills profile","technical experience"});
		KeywordTypes.put(ProfessionalKeyWord, new String[]{"professional experience","experience details","work experience","professional work experience","experience summary"});
		KeywordTypes.put(ProjectDetailsKeyWord, new String[]{"project work", "project experience","project details","projects profiles","project profiles", "project details & work experience", "project details and work experience","project description","project:"});
		KeywordTypes.put(HobbiesKeyword, new String[]{"hobbies"});
		KeywordTypes.put(PersonalDetailsKeyWord, new String[]{"personal details","personal vitae","personal profile", "personal information"});
		KeywordTypes.put(AddressKeyWord, new String[]{"permanent address:","permanent address", "address"});
		KeywordTypes.put(DeclarationsKeyWord, new String[]{"declaration","declarations"});
	
	}
}