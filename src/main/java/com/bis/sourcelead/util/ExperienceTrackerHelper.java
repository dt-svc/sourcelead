package com.bis.sourcelead.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ExperienceTrackerHelper {

	public Map<String, Integer> getExperienceFromTextFile(String text) {
		String years = "", months = "0";
		try {
			Pattern p = Pattern.compile("\\d+\\W+\\s*\\S*(?:months?|years?|yrs?) | "
					+ "\\d+\\s*\\S*years?\\s+(?:and\\s*)?\\d+\\s*\\S*months?|\\d+\\s*\\S*(?:months?|years?|yrs?) |"
					+ " \\d+\\.?\\d*\\W+\\s+(?:months?|years?|yrs?) |\\d+\\.?\\s*\\S*\\d*\\s*\\S*(?:months?|years?|yrs?)|"
					+ "\\d+\\W*((?i)plus(?-i))\\W*\\s+(?:months?|years?|yrs?) | \\d+\\s*year\\W+s\\W+\\s*\\d+\\s*month\\W+s\\W+", Pattern.CASE_INSENSITIVE);


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
				//String[] expArray = experience.split("\\."); 
				String[] expArray = null;
				if((containsIgnoreCase(experience,"yrs"))) { 
					String numberOnly= experience.replaceAll("[^0-9.]", "");
					String boom = numberOnly.replace(".", "");
					expArray = boom.split("");
					years = expArray[0].trim(); 
					years = years.length() > 2 ? years.substring(0, 2) : years; 
					//if(expArray.length >= 1) { 
					months = expArray[1].trim(); // yrs
					months = months.length() > 2 ? months.substring(0, 2) : months; 
					//}
				}else if((containsIgnoreCase(experience,"years")) && (containsIgnoreCase(experience,"months"))){ 
					String numberOnly= experience.replaceAll("[^0-9.]", "");
					expArray = numberOnly.split("");
					if(numberOnly.length()>2) {
						if(numberOnly.length()!=3) {
							years = expArray[0]+expArray[1].trim();
						}else if(numberOnly.length() == 3){
							years = expArray[0].trim();    //expArray[1]
						}else {
							years = expArray[0].trim();
						}
					}else {
						years = expArray[0].trim(); 
					}
					years = years.length() > 2 ? years.substring(0, 2) : years; 
					//if(expArray.length >= 1) { 
					if(numberOnly.length()>2) {
						if(numberOnly.length()==4) {
							months = expArray[2]+expArray[3].trim(); 
						}else {
							if(numberOnly.length()!=3) {
								months = expArray[1]+expArray[2].trim();
							}else if(numberOnly.length()==3) {
								months = expArray[1]+expArray[2].trim(); 
							}else {
								months = expArray[2].trim(); 
							}
						}
					}else {
						months = expArray[1].trim(); 
					}
					months = months.length() > 2 ? months.substring(0, 2) : months; 
					//} 
				}else if(containsIgnoreCase(experience,"months")){ 
					String numberOnly= experience.replaceAll("[^0-9.]", "");
					expArray = numberOnly.split("");

					// if(expArray.length >= 1) { 
					if(numberOnly.length()>=2) {
						months = expArray[0]+expArray[1].trim(); 
					}else {
						months = expArray[0].trim(); 
					}

					months = months.length() > 2 ? months.substring(0, 2) : months; 
					//} 
				}else if(containsIgnoreCase(experience,"years")) { 
					String numberOnly= experience.replaceAll("[^0-9.]", "");
					expArray = numberOnly.split("");
					if(numberOnly.length()>=2 && !numberOnly.contains(".")) {
						years = expArray[0]+expArray[1].trim(); 
					} else if(numberOnly.length()==3 && numberOnly.contains(".")) {
						years = expArray[0].trim(); 
					} else if(numberOnly.length()>=2 && numberOnly.contains(".")) {
						years = expArray[0]+expArray[1].trim();
					}else {
						years = expArray[0].trim(); 
					}
					years = years.length() > 2 ? years.substring(0, 2) : years; 
					if(numberOnly.length()==3 && numberOnly.contains(".")) {
						months = expArray[2].trim(); 
					}else if(numberOnly.length()>3 && numberOnly.contains(".")) {
						months = expArray[3].trim(); 
					}
					months = months.length() > 2 ? months.substring(0, 2) : months; 
				}
			}else {
				if(text.contains(".")) {
					String[] totalExperience =  text.split("\\.");
					years = totalExperience[0];
					months = totalExperience[1];
					System.out.println("hi hello");
				}else {
					years = text;
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
		
		String experience =  years + "." + months;
		return convertMonthsToYears(experience);
		
		
	}

	private Map<String, Integer> convertMonthsToYears(String experience) {
		Map<String, Integer> map = new HashMap<>();
		try {
			String years = experience.split("\\.")[0];
			String months = experience.split("\\.")[1];
			if (!"".equalsIgnoreCase(years) && years != null) {
				if (years.isEmpty()) {
					map.put("years", 0);
				} else {
					map.put("years", Integer.parseInt(years));
				}
			}else {
				map.put("years", 0);
			}
			if (!"".equalsIgnoreCase(months) && months != null) {
				int m = Integer.valueOf(months);
				if (Integer.valueOf(months) > 12) {
					int yrs = m / 12;
					int mths = m % 12;
					map.put("years", yrs);
					map.put("months", mths);
				} else {
					map.put("months", m);
				}
			}
			else {
				map.put("months", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
				
    		 
	}
	public static boolean containsIgnoreCase(String str, String subString) { 
		return str.toLowerCase().contains(subString.toLowerCase()); 
	}

	public String getEmailFromTextFile(String text) {
		String email = "";
		Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(text);
		if (matcher.find()) {
			email = matcher.group().replaceAll("\\s", "").toLowerCase();
		} else {
			p = Pattern.compile("\\b[A-Z0-9\\s._%+-]+@[A-Z0-9\\s.-]+\\.[A-Z\\s]{2,8}\\b", Pattern.CASE_INSENSITIVE);
			matcher = p.matcher(text);
			if (matcher.find()) {
				email = matcher.group().replaceAll("\\s", "").toLowerCase();
			}
		}
		return email;


	}

}
