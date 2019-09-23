package com.bis.sourcelead.si.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Contact_Source;
import com.bis.sourcelead.util.SocialInviterUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Component
class outlook {
	processor pr = new processor();
	
	/*
	 * GET CONTACTS
	 */
	public JSONObject getContacts(InputStream is,HttpServletRequest request) {
		
		ArrayList<ReferContacts> refContactsList = new ArrayList<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int initCount=0,fnameIndex=-1,lnameIndex=-1,emailIndex1=-1,emailIndex2=-1,emailIndex3=-1,primaryPhoneIndex=-1,homePhoneIndex=-1,mobilePhoneIndex=-1,birthdayIndex=-1,notesIndex=-1;
		int linkIndex=-1,homeStreetIndex1=-1,homeStreetIndex2=-1,homeStreetIndex3=-1,homeCityIndex=-1,homeStateIndex=-1,homeZipIndex=-1,homeCountryIndex=-1;
		int bussStreetIndex1=-1,bussStreetIndex2=-1,bussStreetIndex3=-1,bussCityIndex=-1,bussStateIndex=-1,bussZipIndex=-1,bussCountryIndex=-1;
		int totalColumnCount=0;
		String previousValues="";
		JSONArray addressbook = new JSONArray();
		try{
			
			try {
				//READ DATA FROM CSV FILE
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				
				//ITERATE LINE BY LINE
				while ((line = br.readLine()) != null) {
					//DECLARATION
					JSONObject name = new JSONObject();
					JSONObject birthday = new JSONObject();
					JSONArray email = new JSONArray();
					JSONArray phone = new JSONArray();
					JSONArray address = new JSONArray();
					JSONArray website = new JSONArray();
					
					String imageurl = "", notes = "";
					
					//FILTER CONDITION FOR ROWS OTHER THAN HEADER ROW
					if(initCount>0){
						line = line +" ";
						String[] fields = line.split(cvsSplitBy);
						if ((fields.length != totalColumnCount) && (fields.length < totalColumnCount))
                        {
                            int calclen = totalColumnCount - fields.length;
                            //DIFFERENCE >9 THEN ASSUMING THERE IS A LINE BREAK IN MIDDLE OF THE ROW, SO LETS GET THE NEXT ROW
                            if(calclen>9){
                            	//APPEND THE ROW DATA
                            	previousValues += line;
 
                            	//SPLIT AND FIND THE DIFFERENCE OF THE COLUMN
                            	String[] prevfields = previousValues.split(cvsSplitBy);
                            	int prevcalclen = totalColumnCount - prevfields.length;
                            	
                            	//IF DIFFERENCE <9 THEN CONSIDER THIS ROW BY ADDING COMMA FOR REST OF THE COLUMN
                            	if(prevcalclen<9){
                            		line = previousValues;
                            		for (int k = 0; k < prevcalclen; k++)
    	                            {
    	                            	line = line + ", ";
    	                            }
                            		previousValues = "";
                            	}
                            	else{
                            		//SKIP TO NEXT ROW
                            		continue;
                            	}
                            }
                            else{
	                            for (int k = 0; k < calclen; k++)
	                            {
	                            	line = line + ", ";
	                            }
	                            previousValues = "";
                            }
                        }
						line = line +" ";
						//STRIPPING THE DOUBLE QUOTES
						while(line.contains("\"")){
							int index1 = line.indexOf("\"");
							int index2 = line.indexOf("\"",index1+1);
							if(index2!=-1){
								String quote = line.substring(index1, index2+1);
								String newquote = quote.replace("\"", "");
								line = line.replace(quote, pr.encode(newquote));
							}
							else{
								line = line.replace("\"", "");
							}
						}
					}
					
					//SPLIT COLUMN BY COMMA
					String[] columnArry = line.split(cvsSplitBy);
					
					//WILL BE TRUE FOR COLUMN HEADER VALUE
					if(initCount==0){
						totalColumnCount = columnArry.length;
						
						//IDENTIFY THE INDEX OF THE COLUMN NAMES
						for(int i=0;i<totalColumnCount;i++){
								
							String columnName = columnArry[i].toString().toLowerCase();
							if(columnName.contains("first name") || columnName.contains("first") || columnName.contains("given name") || columnName.contains("vorname")){
								if(fnameIndex==-1){
									fnameIndex = i;
								}
							}
							else if(columnName.contains("last name") || columnName.contains("last") || columnName.contains("family name") || columnName.contains("nachname")){
								if(lnameIndex==-1){
									lnameIndex = i;
								}
							}
							else if(columnName.contains("phone 1 - value") || columnName.contains("mobile") || columnName.contains("mobile phone") || columnName.contains("weitere nummer")){
								if(mobilePhoneIndex==-1){
									mobilePhoneIndex = i;
								}
							}
							else if(columnName.contains("primary phone") || columnName.contains("phone1") || columnName.contains("home phone") || columnName.contains("telefon privat")||columnName.contains("phone number")){
								if(primaryPhoneIndex==-1){
									primaryPhoneIndex = i;
								}
							}
							else if(columnName.contains("home phone") || columnName.contains("phone2") || columnName.contains("business phone") || columnName.contains("work phone")){
								if(homePhoneIndex==-1){
									homePhoneIndex = i;
								}
							}
							else if(columnName.contains("birthday") || columnName.contains("Geburtstag")){
								if(birthdayIndex==-1){
									birthdayIndex = i;
								}
							}
							else if(columnName.contains("notes") || columnName.contains("Notizen")){
								if(notesIndex==-1){
									notesIndex = i;
								}
							}
							else if(columnName.contains("e-mail") || columnName.contains("email")){
								
								if(!columnName.contains("type") && columnName.contains("e-mail 2")){
									if(emailIndex2==-1){
										emailIndex2 = i;
									}
								}
								else if(!columnName.contains("type") && columnName.contains("e-mail 3")){
									if(emailIndex3==-1){
										emailIndex3 = i;
									}
								}
								else if(!columnName.contains("type")){
									if(emailIndex1==-1){
										emailIndex1 = i;
									}
								}
							}
							else if(columnName.contains("web page") || columnName.contains("webpage") || columnName.contains("firmen webseite") || columnName.contains("private webseite")){
								if(linkIndex==-1){
									linkIndex = i;
								}
							}
							else if(columnName.contains("notes") || columnName.contains("Notizen")){
								if(notesIndex==-1){
									notesIndex = i;
								}
							}
							else if(columnName.contains("homestreet2") || columnName.contains("home street 2") || columnName.contains("address 1 - street")){
								if(homeStreetIndex2==-1){
									homeStreetIndex2 = i;
								}
							}
							else if(columnName.contains("home street 3") || columnName.contains("homestreet3")){
								if(homeStreetIndex3==-1){
									homeStreetIndex3 = i;
								}
							}
							else if(columnName.contains("homestreet") || columnName.contains("home street") || columnName.contains("address 1 - formatted") || columnName.contains("home address")){
								if(homeStreetIndex1==-1){
									homeStreetIndex1 = i;
								}
							}
							else if(columnName.contains("home city") || columnName.contains("homecity")|| columnName.contains("city") || columnName.contains("plz privat")){
								if(homeCityIndex==-1){
									homeCityIndex = i;
								}
							}
							else if(columnName.contains("home state") || columnName.contains("homestate") || columnName.contains("region") || columnName.contains("stadt privat")){
								if(homeStateIndex==-1){
									homeStateIndex = i;
								}
							}
							else if(columnName.contains("home postal") || columnName.contains("homepostalcode") || columnName.contains("postal code") || columnName.contains("bundesl. privat")){
								if(homeZipIndex==-1){
									homeZipIndex = i;
								}
							}
							else if(columnName.contains("home country") || columnName.contains("homecountry") || columnName.contains("land privat")){
								if(homeCountryIndex==-1){
									homeCountryIndex = i;
								}
							}
							else if(columnName.contains("business street 2") || columnName.contains("businessstreet2")){
								if(bussStreetIndex2==-1){
									bussStreetIndex2 = i;
								}
							}
							else if(columnName.contains("business street 3") || columnName.contains("businessstreet3")){
								if(bussStreetIndex3==-1){
									bussStreetIndex3 = i;
								}
							}
							else if(columnName.contains("businessstreet") || columnName.contains("business street") || columnName.contains("work address") || columnName.contains("stra?e gesch.")){
								if(bussStreetIndex1==-1){
									bussStreetIndex1 = i;
								}
							}
							else if(columnName.contains("businesscity") || columnName.contains("business city") || columnName.contains("work city") || columnName.contains("plz gesch.")){
								if(bussCityIndex==-1){
									bussCityIndex = i;
								}
							}
							else if(columnName.contains("businessstate") || columnName.contains("business state") || columnName.contains("work state") || columnName.contains("stadt gesch.")){
								if(bussStateIndex==-1){
									bussStateIndex = i;
								}
							}
							else if(columnName.contains("businesspostalcode") || columnName.contains("business postal code") || columnName.contains("work postal code") || columnName.contains("bundesl. gesch.")){
								if(bussZipIndex==-1){
									bussZipIndex = i;
								}
							}
							else if(columnName.contains("businesscountry") || columnName.contains("business country") || columnName.contains("work country") || columnName.contains("land gesch.")){
								if(bussCountryIndex==-1){
									bussCountryIndex = i;
								}
							}
							
						}
						
					}
					else{
						//PARSING THE ROW VALUES
						if((emailIndex1!=-1 || emailIndex2!=-1 || emailIndex3!=-1)){
							//EMAIL
							if(emailIndex1!=-1){
								if(!columnArry[emailIndex1].equals("")){
									email.put(columnArry[emailIndex1]);
								}
							}
							if(emailIndex2!=-1){
								if(!columnArry[emailIndex2].equals("")){
									email.put(columnArry[emailIndex2]);
								}
							}
							if(emailIndex3!=-1){
								if(!columnArry[emailIndex3].equals("")){
									email.put(columnArry[emailIndex3]);
								}
							}
							
							//NAME
							name.put("first_name", columnArry[fnameIndex]);
							name.put("last_name", columnArry[lnameIndex]);
							
							//PHONE
							if(primaryPhoneIndex!=-1){
								if(!columnArry[primaryPhoneIndex].equals("")){
									phone.put(columnArry[primaryPhoneIndex]);
								}
							}
							else if(homePhoneIndex!=-1){
								if(!columnArry[homePhoneIndex].equals("")){
									phone.put(columnArry[homePhoneIndex]);
								}
							}
							else if(mobilePhoneIndex!=-1){
								if(!columnArry[mobilePhoneIndex].equals("")){
									phone.put(columnArry[mobilePhoneIndex]);
								}
							}
							
							//BIRTHDAY
							if(birthdayIndex!=-1){
								String bday = columnArry[birthdayIndex];
								if(bday.contains("/")){
									String[] bDayAry = bday.split("/");
									if(bDayAry.length>0){
										birthday.put("day", bDayAry[0]);
										if(bDayAry.length>1){
											birthday.put("month", bDayAry[1]);
											if(bDayAry.length>2){
												birthday.put("year", bDayAry[2]);	
											}
										}
									}
								}
								else if(bday.contains("-")){
									String[] bDayAry = bday.split("-");
									if(bDayAry.length>0){
										birthday.put("day", bDayAry[0]);
										if(bDayAry.length>1){
											birthday.put("month", bDayAry[1]);
											if(bDayAry.length>2){
												birthday.put("year", bDayAry[2]);	
											}
										}
									}
								}
							}
							
							//NOTES
							if(notesIndex!=-1){
								if(!columnArry[notesIndex].equals("")){
									notes = columnArry[notesIndex];
								}
							}
							
							//WEBSITES
							if(linkIndex!=-1){
								if(!columnArry[linkIndex].equals("")){
									website.put(columnArry[linkIndex]);
								}
							}
							
							//ADDRESS
							if(homeStreetIndex1!=-1 || homeStreetIndex2!=-1 || homeStreetIndex3!=-1 || homeCityIndex!=-1 || homeStateIndex!=-1 || homeZipIndex!=-1 || homeCountryIndex!=-1){
								JSONObject addrObj = new JSONObject();
								if(homeStreetIndex1!=-1){
									addrObj.put("street", columnArry[homeStreetIndex1]);
								}
								else if(homeStreetIndex2!=-1){
									addrObj.put("street", columnArry[homeStreetIndex2]);
								}
								else if(homeStreetIndex3!=-1){
									addrObj.put("street", columnArry[homeStreetIndex3]);
								}
								
								if(homeCityIndex!=-1){
									addrObj.put("city", columnArry[homeCityIndex]);
								}
								if(homeStateIndex!=-1){
									addrObj.put("state", columnArry[homeStateIndex]);
								}
								if(homeZipIndex!=-1){
									addrObj.put("zip", columnArry[homeZipIndex]);
								}
								if(homeCountryIndex!=-1){
									addrObj.put("country", columnArry[homeCountryIndex]);
								}
								address.put(addrObj);
							}
							if(bussStreetIndex1!=-1 || bussStreetIndex2!=-1 || bussStreetIndex3!=-1 || bussCityIndex!=-1 || bussStateIndex!=-1 || bussZipIndex!=-1 || bussCountryIndex!=-1){
								JSONObject addrObj = new JSONObject();
								if(bussStreetIndex1!=-1){
									addrObj.put("street", columnArry[bussStreetIndex1]);
								}
								else if(bussStreetIndex2!=-1){
									addrObj.put("street", columnArry[bussStreetIndex2]);
								}
								else if(bussStreetIndex3!=-1){
									addrObj.put("street", columnArry[bussStreetIndex3]);
								}
								
								if(bussCityIndex!=-1){
									addrObj.put("city", columnArry[bussCityIndex]);
								}
								if(bussStateIndex!=-1){
									addrObj.put("state", columnArry[bussStateIndex]);
								}
								if(bussZipIndex!=-1){
									addrObj.put("zip", columnArry[bussZipIndex]);
								}
								if(bussCountryIndex!=-1){
									addrObj.put("country", columnArry[bussCountryIndex]);
								}
								address.put(addrObj);
							}
							
							JSONObject aBook = new JSONObject();
							aBook.put("address", address);
							aBook.put("birthday", birthday);
							aBook.put("email", email);
							aBook.put("imageurl", imageurl);
							aBook.put("name", name);
							aBook.put("notes", notes);
							aBook.put("phone", phone);
							aBook.put("website", website);
							addressbook.put(aBook);
							
							ReferContacts referContacts = new ReferContacts();
							 referContacts.setContactSource(Contact_Source.CSV);
							 
							 for(int k=0;k< email.length();k++){
								 if(k==0){
									 referContacts.setContactUserEmail(email.getString(k)); 
								 }
								 else if(k==1){
									 referContacts.setContactUserEmail2(email.getString(k)); 
								 }
								  
							 }
							 
							 referContacts.setReferFromUser(loginUser);
							 referContacts.setOrgLocationId(orgLoc);
							 referContacts.setCreatedDate(new Date());
							 referContacts.setUpdatedDate(new Date());
							 referContacts.setCreatedBy(loginUser.getUserCode());
							 referContacts.setUpdatedBy(loginUser.getUserCode());
							 if(name.length() > 0 && name.get("first_name") != null)
							 referContacts.setFirstName(name.get("first_name").toString());
							 if(name.length() > 0 && name.get("last_name") != null)
							 referContacts.setLastName(name.get("last_name").toString());
							 if(phone.length() > 0){
								 for(int k=0;k< phone.length();k++){
									 if(k==0){
										 referContacts.setContactUserPhone(phone.getString(k)); 
									 }
									 else if(k==1){
										 referContacts.setContactUserPhone2(phone.getString(k)); 
									 }
									  
								 }
							 }
							 referContacts.setJsonOutPut(aBook.toString());
							
							 refContactsList.add(referContacts);
						}
					}
					initCount++;

				}
				SocialInviterUtil.storeReferContacts(refContactsList,request);
				data.put("addressbook", addressbook);
				data.put("service", "outlook");
				jsonresponse.put("data", data);
				msg.put("message", "");
				msg.put("type", "success");
				jsonresponse.put("responseStatus", msg);

			} 
			catch (IOException e) {
				e.printStackTrace();
				msg.put("message", "Error fetching profile");
				msg.put("type", "error");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						//e.printStackTrace();
					}
				}
			}
	        			
		}
		catch(Exception e){
			try{
				msg.put("message", "Error fetching profile");
				msg.put("type", "error");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			catch(JSONException je){
				
			}	
		}
		return jsonresponse;
	}
	
}
