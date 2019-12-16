package com.bis.sourcelead.resumeParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class StateHelper {

	public String getState(String parsedString) {
		String state ="";

        try {
    		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/states.txt")));

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

                   state += (matcher.group(1).toLowerCase());

              }

              result = String.join(",", state);

              result = result.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");

             

        } catch (Exception e) {

              e.printStackTrace();

        }

        return state;
	}

}
