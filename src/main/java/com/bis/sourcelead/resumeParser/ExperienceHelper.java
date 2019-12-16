package com.bis.sourcelead.resumeParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
//exp and email

@Component
public class ExperienceHelper {

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
	
	public String getDobfromfile(String text){
		String newDob = "";
		String Dob = "";
		String def = "";
		String month = "";
		String NdDob = "";
		String PDob = "";
		String newdDOb1 = "";
		String Shit = "";
			for(String a :text.split("\n")) {
				// to get dob substring
				String regx = "(\\bDate of birth\\b.+\\b[0-9]{2,4}\\b)|(\\bD.O.B\\b.+\\b[0-9]{2,4}\\b)|(\\bDOB\\b.+\\b[0-9]{2,4}\\b)";
				Pattern p2 = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
				Matcher m2 = p2.matcher(text);
				if(m2.find()) {
					//to get DOB String....
				Dob = m2.group(0).replaceAll("Date Of Birth|Date of Birth|Dob|DOB|DATE OF BIRTH|D.O.B", "").replace(":", "");
				
			      // replacing excess chars...
				String Dob1 = Dob.replace(",", "").replace("-", "");
				newdDOb1 = Dob1.replace("st", " ").replace("nd", " ").replace("rd", "").replace("th", " ");
				 NdDob = newdDOb1.replace("  ", " ");
				// matches date format 
				Pattern p1 = Pattern.compile("\\b[0-9]{2}\\b\\W\\b[0-9]{2}\\b\\W\\b[0-9]{2,4}");
				Matcher m1 = p1.matcher(Dob);
				if(m1.find())
				{
					PDob = m1.group(0); 
				}
				else {
					
					//to get months ...
				String regx1 = "\\b(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|(Nov|Dec)(?:ember)?)\\b";
				Pattern p3 = Pattern.compile(regx1,Pattern.CASE_INSENSITIVE);
				Matcher m3 = p3.matcher(NdDob);
				if(m3.find()) {
					
					// converts months to numbers...
					def = m3.group(0);
				    if("Jan".equalsIgnoreCase(def)||"January".equalsIgnoreCase(def)) {
				    	month = "-01-";
				    }
			    	else if("Feb".equalsIgnoreCase(def)||"February".equalsIgnoreCase(def)) {
						    	month = "-02-";
						    }
				    	else if("Mar".equalsIgnoreCase(def)||"March".equalsIgnoreCase(def)) {
					    	month = "-03-";
					    }
				    	else if("Apr".equalsIgnoreCase(def)||"April".equalsIgnoreCase(def)) {
				    	month = "-04-";
				    }
				    	else if("May".equalsIgnoreCase(def)) {
					    	month = "-05-";
					    }else if("Jun".equalsIgnoreCase(def)||"June".equalsIgnoreCase(def)) {
					    	month = "-06-";
					    }
					    else if("Jul".equalsIgnoreCase(def)||"July".equalsIgnoreCase(def)) {
					    	month = "-07-";
					    }
					    else if("Aug".equalsIgnoreCase(def)||"August".equalsIgnoreCase(def)) {
					    	month = "-08-";
					    }else if("Sep".equalsIgnoreCase(def)||"Sept".equalsIgnoreCase(def)||"September".equalsIgnoreCase(def)) {
					    	month = "-09-";
					    }else if("Oct".equalsIgnoreCase(def)||"October".equalsIgnoreCase(def)) {
					    	month = "-10-";
					    }else if("Nov".equalsIgnoreCase(def)||"November".equalsIgnoreCase(def)) {
					    	month = "-11-";
					    }else if("Dec".equalsIgnoreCase(def)||"December".equalsIgnoreCase(def)) {
					    	month = "-12-";
					    }
				}
				}
				}
				
				}
			if(!"".equals(def)) {
			newdDOb1 = NdDob.replaceAll("\\b"+def+"\\b", month).replace(" ", "");
			newDob = NdDob.replaceAll("\\b"+def+"\\b", month).replace(" ", "").replace("\t", "");
			String regx1 = "\\b"+def+"\\b\\s\\b[0-9]{2}\\b\\s\\b[0-9]{4}\\b";        		//for splitting date in format of eg 16 may 2018
			Pattern p1 = Pattern.compile(regx1);
			Matcher m1 = p1.matcher(NdDob.replaceAll(",", ""));
			if(m1.find()) {
			String month1  = 	m1.group(0).split("\\s")[0];
			String day2    =    m1.group(0).split("\\s")[1];
			String year2   =    m1.group(0).split("\\s")[2];
			String dash = "-";
			String testdob = day2+dash+month1+dash+year2;
			String test1 = testdob.replaceAll("\\b"+def+"\\b", month).replaceAll("--", "-");
			String day1 =	test1.split("\\W")[0];
        	String mnth1  =  test1.split("\\W")[1];
			String year1  =  test1.split("\\W")[2];
			 newDob  = day1+dash+mnth1+dash+year1;
			} else {
			String day = newDob.split("\\W")[0];
			String mnth = newDob.split("\\W")[1];
			String year = newDob.split("\\W")[2];
			String dash = "-";
			newDob = day+dash+mnth+dash+year;
			}
			}else if (!"".equals(Dob)) {
				String pday = PDob.split("\\W")[0];
				String pmnth = PDob.split("\\W")[1];
				String pyear = PDob.split("\\W")[2];
				String pdash = "-";
				newDob = pday+pdash+pmnth+pdash+pyear;
			}else {
				newDob = "";
			}
		Shit = newDob.replace("Dateofbir", "");
			return Shit;
	}

}