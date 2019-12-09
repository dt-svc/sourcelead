package com.bis.sourcelead.resumeParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.uwyn.jhighlight.fastutil.Hash;

@Component
public class ParsedCityHelper {
	@SuppressWarnings({ "resource", "unused" })
	public String getCity(String parsedString) {
		String city ="";
		HashSet<String> city1 = new HashSet<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/cities.txt")));
			String keywords = "";
			while (br.readLine() != null) {
				keywords += br.readLine();
			}
			String result = "";
			List<String> keywordsList = Arrays.asList(keywords.split("\\s*,\\s*"));
			String keywordsPattern = "\\b(" + StringUtils.join(keywordsList, "|") + ")\\b";
			Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				city = (matcher.group(1).toLowerCase());
				city1.add(city);
			}
			result = String.join(",", city);
			result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return city;
	}


	public String getCities(String parsedString) {
		String cityName ="";
		HashSet<String> city1 = new HashSet<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/cities.txt")));

			
			String keywords = "";
			while (br.readLine() != null) {
				keywords += br.readLine();
			}
			List<String> keywordsList = Arrays.asList(keywords.split("\\s*,\\s*"));
			String keywordsPattern = "\\b(" + StringUtils.join(keywordsList, "|") + ")\\b";
			Pattern pattern = Pattern.compile(keywordsPattern, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(parsedString);
			while (matcher.find()) {
				cityName = (matcher.group(1).toLowerCase());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			return cityName;
	}


}
