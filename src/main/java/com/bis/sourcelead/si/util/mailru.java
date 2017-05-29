package com.bis.sourcelead.si.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
class mailru {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String consumerkey,String callback){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
        try{
			String authurl = "https://connect.mail.ru/oauth/authorize?client_id=" + consumerkey;
	        authurl += "&scope=messages&response_type=code&redirect_uri=" + callback;
	        data.put("token", "");
			data.put("tokensecret", "");
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", "");
			data.put("authurl", authurl);
			
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
        }
        catch(JSONException j){
        	
        }
        catch(Exception e){
        }
        
        return jsonresponse;
	}
	
	
	/*
	 * GET ACCESS TOKEN
	 */
	protected JSONObject getAccessToken(String service,String code, String consumerkey, String consumersecret, String callback){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String access_token_secret = "";
		try{
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://connect.mail.ru/oauth/token", postdata,"");
			
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", access_token_secret);
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", resp.get("x_mailru_vid"));
			data.put("authurl", "");
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
		}
		catch(JSONException j){
			
		}
		catch(Exception e){
			
		}
		return jsonresponse;
	}
	
	
	/*
	 * GET CONTACT IDS
	 */
	protected String getContactIds(String token,String consumerkey,String consumersecret) {
		String retResponse="";
		try{
			//PROCESS MD5 HASH
			String sig = pr.MD5("app_id=" + consumerkey + "method=friends.getsecure=1session_key=" + token + consumersecret);
			
			String requrl = "http://www.appsmail.ru/platform/api?method=friends.get&secure=1&app_id=" + consumerkey+ "&session_key=" + token + "&sig=" + sig;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        retResponse = response.toString().replace("[", "").replace("]", "").replace("\"", "");
		}
		catch(Exception ee){
		}
		return retResponse;
	}
	
	
	/*
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String token,String consumerkey,String consumersecret) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray addressbook = new JSONArray();
		try{
			//GET CONTACT ID
			String contactIds = getContactIds(token,consumerkey,consumersecret);
			
			//PROCESS MD5 HASH
			String sig = pr.MD5("app_id=" + consumerkey + "method=users.getInfosecure=1session_key=" + token + "uids=" + contactIds + consumersecret);
			String requrl = "http://www.appsmail.ru/platform/api?method=users.getInfo&secure=1&uids=" + contactIds+ "&app_id=" + consumerkey + "&session_key=" + token + "&sig=" + sig;;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONArray dataArray = new JSONArray(response.toString());
			int len = dataArray.length();
			for(int i=0;i<len;i++){
				JSONObject contact = dataArray.getJSONObject(i);
				JSONObject name = new JSONObject();
				JSONObject birthday = new JSONObject();
				JSONObject addressObj = new JSONObject();
				JSONArray email = new JSONArray();
				JSONArray phone = new JSONArray();
				JSONArray address = new JSONArray();
				JSONArray website = new JSONArray();
				String imageurl = "";
				String notes = "";
				if(contact.has("first_name")){ //NAME
					name.put("first_name", contact.get("first_name"));
					name.put("last_name", contact.get("last_name"));
				}
				if(contact.has("email")){ //EMAIL
					email.put(contact.get("email"));
				}
				if(contact.has("pic_180")){ //IMAGE
					imageurl = contact.getString("pic_180");
				}
				if(contact.has("birthday")){ //BIRTHDAY
					if(contact.get("birthday")!=null){
						String dob = contact.get("birthday").toString();
						String[] bday = dob.split("\\.");
						birthday.put("day", bday[1].toString());
						birthday.put("month", bday[0].toString());
						birthday.put("year", bday[2].toString());
					}
				}
				if(contact.has("location")){ //ADDRESS
					JSONObject locationObj = contact.getJSONObject("location");
					String country_="",city_="",state_="";
					if(locationObj.has("country")){
						JSONObject countryObj = locationObj.getJSONObject("country");
						if(countryObj.has("name")){
							country_ =countryObj.getString("name"); 
						}
					}
					if(locationObj.has("city")){
						JSONObject cityObj = locationObj.getJSONObject("city");
						if(cityObj.has("name")){
							city_ =cityObj.getString("name"); 
						}
					}
					if(locationObj.has("region")){
						JSONObject regionObj = locationObj.getJSONObject("region");
						if(regionObj.has("name")){
							state_ =regionObj.getString("name"); 
						}
					}
					addressObj.put("country", country_);
					addressObj.put("city", city_);
					addressObj.put("state", state_);
					address.put(addressObj);
				}
				if(email.length()>0){
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
				}
			}
			data.put("addressbook", addressbook);
			data.put("service", "mailru");
			jsonresponse.put("data", data);
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("responseStatus", msg);			
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
