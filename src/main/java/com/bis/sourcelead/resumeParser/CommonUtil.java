package com.bis.sourcelead.resumeParser;

public class CommonUtil {
	public static String getCaptilizedText(String text) {
		String convertedText = "";
		try {
			for(String t : text.split(" ")) {
				convertedText += (convertedText.isEmpty() ? "" : " ") + t.substring(0, 1).toUpperCase() + t.substring(1).toLowerCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedText;
	}

}
