package com.bis.sourcelead.si.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
class hotmail {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		//SCOPE - PERMISSION
		String scope = "wl.basic+wl.emails+wl.contacts_emails+wl.contacts_photos+wl.birthday+wl.photos+wl.offline_access+wl.contacts_birthday+wl.contacts_skydrive+wl.phone_numbers";
		
        try{
			String authurl = "https://login.live.com/oauth20_authorize.srf?client_id=" + consumerkey;
	        authurl += "&scope=" + scope + "&response_type=code&redirect_uri=" + callback;
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
		JSONObject jsonresponse = new JSONObject();
		try{
			JSONObject data = new JSONObject();
			JSONObject msg = new JSONObject();
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
	        String access_token_secret = "";
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://login.live.com/oauth20_token.srf", postdata,"");
			
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", access_token_secret);
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", resp.get("user_id"));
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
	 * GET PROFILE INFORMATION
	 */
	protected JSONObject getProfile(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		try{
			String requrl = "https://apis.live.net/v5.0/me?access_token=" + pr.encode(token);
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			details.put("aboutMe", "");
			details.put("id", detailsObj.get("id"));
			details.put("displayName", detailsObj.get("name"));
			details.put("firstName", detailsObj.get("first_name"));
			details.put("lastName", detailsObj.get("last_name"));
			JSONObject emails = detailsObj.getJSONObject("emails");
			details.put("email", emails.get("preferred"));
			details.put("profileUrl", detailsObj.get("link")+ "cid-" + detailsObj.get("id"));
			details.put("photo", "https://apis.live.net/v5.0/" + detailsObj.get("id") + "/picture");
			details.put("location", "");
			details.put("gender", detailsObj.get("gender"));
			details.put("dob", detailsObj.get("birth_year") + "-" + detailsObj.get("birth_month") +"-"+detailsObj.get("birth_day"));
			data.put("details", details);
			data.put("service", service);
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
	
	
	/*
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String token,String service) {
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray addressbook = new JSONArray();
		try{
			String requrl = "https://apis.live.net/v5.0/me/contacts?access_token=" + pr.encode(token);
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			JSONArray dataArray = detailsObj.getJSONArray("data");
			int len = dataArray.length();
			for(int i=0;i<len;i++){
				JSONObject contact = dataArray.getJSONObject(i);
				JSONObject name = new JSONObject();
				JSONObject birthday = new JSONObject();
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
				if(contact.has("emails")){ //EMAIL
					JSONObject emailObj = contact.getJSONObject("emails");
					if(emailObj.has("preferred")){
						if(emailObj.get("preferred")!="" && emailObj.get("preferred")!=null){
							email.put(emailObj.get("preferred"));
						}
					}
					if(emailObj.has("business")){
						if(emailObj.get("business")!="" && emailObj.get("business")!=null){
							email.put(emailObj.get("business"));
						}
					}
					if(emailObj.has("other")){
						if(emailObj.get("other")!="" && emailObj.get("other")!=null){
							email.put(emailObj.get("other"));
						}
					}
					if(emailObj.has("personal")){
						if(emailObj.get("personal")!="" && emailObj.get("personal")!=null){
							email.put(emailObj.get("personal"));
						}
					}
					if(emailObj.has("account")){
						if(emailObj.get("account")!="" && emailObj.get("account")!= null){
							email.put(emailObj.get("account"));
						}
					}
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
			data.put("service", "hotmail");
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
