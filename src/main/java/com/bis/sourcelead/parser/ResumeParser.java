package com.bis.sourcelead.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeParser {
	public static int LastFoundIndex = 0, FirstFoundIndex = 0;
	public static String LastFoundKeyWord;
	public static boolean IsFirstKeyWordFound = false;
	// public static HashMap<String, Object> ResumeParser(String path)
	
	public HashMap<String, Object> parseResume(String fileContent)
	{
		String[] fileContentLineArray = fileContent.split("\\r\\n|\\n|\\r");
		HashMap<String, Object> parsedLines = new HashMap<String, Object>();
		parsedLines = GetLines(fileContentLineArray);
		return parsedLines;
	}
	
	public final String getFileName(String fileName)
	{
		String fName = fileName.replace(" ", "_");
		fName = fName.replace(",", "_");
		int checkdot = fName.lastIndexOf('.');
		String targetname = "";
		if (checkdot != -1) {
			try {
				String[] arry = fName.split("[.]", -1);
				targetname = arry[0].toString();
				String savename = targetname;
				savename = "UUID.NewGuid()".toString();
				return targetname + "_" + savename + "." + arry[arry.length - 1].toString();
			} catch (RuntimeException e) {
			}
		}
		return fileName;
	}
	
	public static HashMap<String, Object> GetLines(String[] lines)
	{
		String lastLine = "";
		HashMap<String, Object> parsedResume = new HashMap<String, Object>();
		try {
			Constants idf = new Constants();
			// idf.initHashMap();
			HashMap<String, String[]> keywordTypes = idf.KeywordTypes;
			int len = lines.length;
			for (int i = 0; i < len; i++) {
				if (!DotNetToJavaStringHelper.isNullOrEmpty(lines[i])
						&& !DotNetToJavaStringHelper.isNullOrWhiteSpace(lines[i])
						&& !lines[i].equals("")) {
					RefObject<String> tempRef_lastLine = new RefObject<String>(lastLine);
					Model details = IsLineContainsKeywords(lines[i], i, keywordTypes, tempRef_lastLine);
					lastLine = tempRef_lastLine.argValue;
					if (details.IsFound) {
						if (!IsFirstKeyWordFound) {
							FirstFoundIndex = details.Index;
							IsFirstKeyWordFound = true;
						}
						if (!DotNetToJavaStringHelper.isNullOrEmpty(lastLine)
								&& LastFoundKeyWord != null && !parsedResume.isEmpty()) {
							parsedResume.put(LastFoundKeyWord, parsedResume.get(LastFoundKeyWord)
									+ "\n" + lastLine);
						}
						LastFoundIndex = details.Index;
						LastFoundKeyWord = details.Keyword;
						parsedResume.put(details.Keyword, details.Line);
						keywordTypes.remove(details.Keyword);
					}
					else {
						if (LastFoundIndex < details.Index && LastFoundKeyWord != null
								&& details.IsParagraphHeading) {
							// && LastFoundKeyWord != Constants.ContactKeyWord)
							if (!parsedResume.isEmpty()) {
								String lFound = parsedResume.get(LastFoundKeyWord).toString();
								if (lFound.trim().equals(":")) {
									lFound = "";
								}
								parsedResume.put(LastFoundKeyWord, lFound + "\n" + details.Line);
								// parsedResume.put(LastFoundKeyWord,
								// parsedResume.get(LastFoundKeyWord) + "\n" +
								// details.Line);
							}
						}
					}
				}
				else {
					if (!DotNetToJavaStringHelper.isNullOrEmpty(LastFoundKeyWord)
							&& !parsedResume.isEmpty()) {
						parsedResume.put(LastFoundKeyWord, parsedResume.get(LastFoundKeyWord)
								+ "\n");
					}
				}
			}
			String Name_ = GetName(lines).replace(",", "").trim();
			if (!Name_.equals("")) {
				parsedResume.put(Constants.NameKeyWord, Name_);
			}
			String Phone_ = GetPhone(lines, 6);
			if (Phone_.equals("")) {
				Phone_ = GetPhoneNumber(lines, 6, parsedResume);
			}
			if (!Phone_.equals("")) {
				parsedResume.put(Constants.ContactKeyWord, Phone_);
			}
			String Email_ = GetEmailAddress(lines, 6);
			if (!Email_.equals("")) {
				parsedResume.put(Constants.EmailKeyWord, Email_);
			}
			String Address_ = GetAddress(parsedResume);
			if (!Address_.equals("")) {
				parsedResume.put(Constants.AddressKeyWord, Address_);
			}
			String Experience_ = GetExperience(parsedResume, lines);
			if (!Experience_.equals("")) {
				parsedResume.put(Constants.ExperienceKeyWord, Experience_);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return parsedResume;
	}
	
	public static Model IsLineContainsKeywords(String line, int index, HashMap<String, String[]> keywordTypes, RefObject<String> lastLine)
	{
		lastLine.argValue = "";
		for (Map.Entry<String, String[]> keywordType : keywordTypes.entrySet()) {
			// C# TO JAVA CONVERTER TODO TASK: There is no equivalent to
			// implicit typing in Java:
			for (String keyword : keywordType.getValue()) {
				String processedLine = line.toLowerCase().trim();
				if (processedLine != null) {
					if (processedLine.contains(keyword)) {
						// String tempLine =
						// tangible.DotNetToJavaStringHelper.trim(processedLine.substring(processedLine.indexOf(keyword)
						// + keyword.getLength()), Constants.UnwantedChar);
						String tempLine = processedLine.substring(processedLine.indexOf(keyword)
								+ keyword.length()).trim();
						if ((Constants.EducationKeyWord.equals(keywordType.getKey())
								|| Constants.PersonalDetailsKeyWord.equals(keywordType.getKey()))
								&& processedLine.indexOf(keyword) == 0) {
							Model tempVar = new Model();
							tempVar.Index = index;
							tempVar.Line = tempLine.contains(":")
									? tempLine.substring(tempLine.indexOf(':') + 1).trim()
									: tempLine;
							tempVar.IsFound = true;
							tempVar.Keyword = keywordType.getKey();
							tempVar.IsParagraphHeading = DotNetToJavaStringHelper.isNullOrEmpty(tempLine);
							return tempVar;
						}
						if (processedLine.equals(keyword)) {
							Model tempVar2 = new Model();
							tempVar2.Index = index;
							tempVar2.Line = tempLine;
							tempVar2.IsFound = true;
							tempVar2.Keyword = keywordType.getKey();
							tempVar2.IsParagraphHeading = true;
							return tempVar2;
						}
						String tempLine2 = line.substring(line.toLowerCase().indexOf(keyword)
								+ keyword.length());
						if (tempLine2.trim().startsWith(":")) {
							lastLine.argValue = line.substring(0, line.toLowerCase().indexOf(keyword));
							Model tempVar3 = new Model();
							tempVar3.Index = index;
							tempVar3.Line = tempLine;
							tempVar3.IsFound = true;
							tempVar3.Keyword = keywordType.getKey();
							tempVar3.IsParagraphHeading = true;
							return tempVar3;
						}
					}
				}
			}
		}
		Model tempVar4 = new Model();
		tempVar4.Index = index;
		tempVar4.Line = line;
		tempVar4.IsFound = false;
		tempVar4.Keyword = "";
		tempVar4.IsParagraphHeading = true;
		return tempVar4;
	}
	
	private static String GetPhoneNumber(String[] lines, int checkIndex, HashMap<String, Object> parsedResume)
	{
		String phoneNumbers = "";
		// JSONArray ja = new JSONArray();
		for (int i = 0; i < checkIndex; i++) {
			Boolean isFound = false;
			String checkLine = lines[i];
			// validate phone numbers of format "1234567890"
			Matcher m = Pattern.compile("\\d{10}").matcher(checkLine);
			while (m.find()) {
				// ja.put(m.group().trim());
				phoneNumbers = m.group().trim();
				isFound = true;
			}
			// validating phone number with -, . or spaces
			if (!isFound) {
				m = Pattern.compile("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}").matcher(checkLine);
				while (m.find()) {
					// ja.put(m.group().trim());
					phoneNumbers = m.group().trim();
					isFound = true;
				}
			}
			// validating phone number with extension length from 3 to 5
			if (!isFound) {
				m = Pattern.compile("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}").matcher(checkLine);
				while (m.find()) {
					// ja.put(m.group().trim());
					phoneNumbers = m.group().trim();
					isFound = true;
				}
			}
			// validating phone number where area code is in braces ()
			if (!isFound) {
				m = Pattern.compile("\\(\\d{3}\\)-\\d{3}-\\d{4}").matcher(checkLine);
				while (m.find()) {
					phoneNumbers = m.group().trim();
					// ja.put(m.group().trim());
				}
			}
		}
		String pnum = phoneNumbers.replace(",", "");
		if ((phoneNumbers.equals("") || pnum.equals(""))
				&& parsedResume.containsKey(Constants.PersonalDetailsKeyWord)) {
			// C# TO JAVA CONVERTER TODO TASK: There is no equivalent to
			// implicit typing in Java:
			String[] personalDetails = parsedResume.get(Constants.PersonalDetailsKeyWord).toString().split("\\r\\n|\\n|\\r");
			int len = personalDetails.length;
			for (int i = 0; i < len; i++) {
				for (String phoneKey : Constants.ContactKeywords) {
					if (personalDetails[i].toLowerCase().contains(phoneKey)) {
						phoneNumbers = DotNetToJavaStringHelper.substring(personalDetails[i].toLowerCase(), phoneKey.length(), personalDetails[i].length()).trim();
						break;
					}
				}
			}
		}
		return phoneNumbers.trim();
	}
	
	private static String GetEmailAddress(String[] lines, int checkIndex)
	{
		for (int i = 0; i < checkIndex; i++) {
			Matcher m = Pattern.compile(Constants.EmailPattern).matcher(lines[i]);
			while (m.find()) {
				return m.group(0).toString();
			}
		}
		for (int i = 0; i < checkIndex; i++) {
			for (String keyword : Constants.KeywordTypes.get(Constants.EmailKeyWord)) {
				if (lines[i].toLowerCase().replace(",", "").trim().contains(keyword.toLowerCase())) {
					int startIndex = lines[i].toLowerCase().indexOf(keyword);
					return lines[i].substring(startIndex + keyword.length()).trim();
				}
			}
		}
		return "";
	}
	
	private static String GetName(String[] lines)
	{
		String[] nameKeyWord = new String[] { "name:", "name :" };
		for (String line : lines) {
			String loweredLine = line.toLowerCase();
			if (!DotNetToJavaStringHelper.isNullOrWhiteSpace(line)
					&& !loweredLine.trim().equals("resume")) {
				for (String keyword : nameKeyWord) {
					if (loweredLine.contains(keyword)) {
						String tempLine = line.substring(loweredLine.indexOf(keyword)
								+ keyword.length()).trim();
						return tempLine.substring(0, tempLine.indexOf(' '));
					}
				}
				return line.trim();
			}
		}
		return "";
	}
	
	private static String GetPhone(String[] lines, int checkIndex)
	{
		for (int i = 0; i < checkIndex; i++) {
			for (String keyword :Constants.KeywordTypes.get(Constants.ContactKeyWord)) {
				if (lines[i].toLowerCase().replace(",", "").trim().contains(keyword.toLowerCase())) {
					int startIndex = lines[i].toLowerCase().indexOf(keyword);
					return lines[i].substring(startIndex
							+ keyword.length()).replace(":", "").trim();
				}
			}
		}
		return "";
	}
	
	public static String GetExperience(HashMap<String, Object> parsedResume, String[] lines)
	{
		String experience = "";
		String[] experienceSearcher = { Constants.SummaryKeyWord, Constants.ObjectiveKeyWord };
		for (String searcher : experienceSearcher) {
			if (parsedResume.containsKey(searcher)) {
				String value = parsedResume.get(searcher).toString().toLowerCase();
				if (value.contains("year")) {
					for (String identifier : Constants.ExperienceIdentifiers) {
						if (value.substring(0, value.indexOf("year")).contains(identifier)) {
							Object[] temp = value.substring(value.indexOf(identifier), (value.indexOf("year")
									+ 4)).split("[ ]", -1);
							if (temp.length < 4 && temp.length >= 2) {
								return temp[1] + " " + temp[2];
							}
							if (temp.length == 4) {
								return temp[1] + " " + temp[2] + " " + temp[3];
							}
						}
					}
					Matcher m = Pattern.compile("(?:\\S+\\s)?\\S*year").matcher(value);
					while (m.find()) {
						experience = m.group(0).toString();
					}
				}
			}
		}
		if (DotNetToJavaStringHelper.isNullOrEmpty(experience)
				&& !DotNetToJavaStringHelper.isNullOrEmpty(parsedResume.get(Constants.EmailKeyWord).toString())) {
			int startIndex = 0, endIndex = 0;
			for (int i = 0; i < lines.length; i++) {
				if (lines[i].contains(parsedResume.get(Constants.EmailKeyWord).toString())) {
					startIndex = i;
					endIndex = startIndex + 10;
				}
			}
			for (int i = startIndex; i <= endIndex; i++) {
				Matcher m = Pattern.compile("(?:\\S+\\s)?\\S*year").matcher(lines[i]);
				while (m.find()) {
					experience = m.group(0).toString();
					break;
				}
			}
		}
		return experience;
	}
	
	public static String GetAddress(HashMap<String, Object> parsedResume)
	{
		String address = "";
		if (parsedResume.containsKey(Constants.PersonalDetailsKeyWord)) {
			// C# TO JAVA CONVERTER TODO TASK: There is no equivalent to
			// implicit typing in Java:
			Object[] personalDetails = parsedResume.get(Constants.PersonalDetailsKeyWord).toString().split("\\r\\n|\\n|\\r");
			int len = personalDetails.length;
			for (int i = 0; i < len; i++) {
				if (personalDetails[i].toString().contains("address")) {
					address = personalDetails[i].toString().split(":")[1];
					// .split(java.util.regex.Pattern.quote(new char[]
					// {':'}.toString()), -1)[1];
					int pLen = personalDetails.length;
					for (int j = i + 1; j < pLen; j++) {
						if (!personalDetails[j].toString().contains(":")) {
							address = address + "\n" + personalDetails[j];
						}
					}
				}
			}
		}
		return address.replace(":", "").trim();
	}
}