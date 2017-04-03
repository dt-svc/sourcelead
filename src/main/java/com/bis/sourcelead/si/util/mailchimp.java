package com.bis.sourcelead.si.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class mailchimp {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String product,String consumerkey,String callback){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
        try{
			String authurl = "https://login.mailchimp.com/oauth2/authorize?response_type=code&client_id=" + consumerkey;
	        authurl += "&redirect_uri=" + callback;
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
		String access_token_secret = "", user_id = "";
		try{
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://login.mailchimp.com/oauth2/token", postdata,"");
			
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", access_token_secret);
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", user_id);
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
	 * GET META DATA OF USER
	 */
	private String getMetaData(String token) {
		String dc="";
		try{
			String requrl = "https://login.mailchimp.com/oauth2/metadata";
			//MAKE API CALL
			String response = pr.executeGetMailChimp(requrl, token,"meta");
			
			//PARSE DATA
	        JSONObject dcObj = new JSONObject(response.toString());
	        dc = dcObj.getString("dc");
		}
		catch(Exception ee){
			
		}
		return dc;
	}
	
	
	/*
	 * GET CONTACTS DATA
	 */
	protected JSONObject getContactsData(String token,String dc,String listid) {
		JSONObject contactObj = new JSONObject();
		try{
			String requrl = "https://" + dc + ".api.mailchimp.com/3.0/lists/" + listid + "/members";
			
			//MAKE API CALL
			String response = pr.executeGetMailChimp(requrl, token, "");
			
			//PARSE DATA
	        contactObj = new JSONObject(response.toString());
		}
		catch(Exception ee){
			
		}
		return contactObj;
	}
	
	
	/*
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String consumerkey, String consumersecret,String token) {
		//DECLARATION
		JSONArray addressbook = new JSONArray();
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		try{
			//GET META DATA
			String dc = getMetaData(token);
			
			String requrl = "https://"+dc+".api.mailchimp.com/3.0/lists?fields=lists.id";
			
			//MAKE API CALL
			String response = pr.executeGetMailChimp(requrl, token, "");
			
			//PARSE DATA
	        JSONObject idObj = new JSONObject(response.toString());
	        JSONArray idAry = idObj.getJSONArray("lists");
	        int listLen = idAry.length();
	        for(int j=0;j<listLen;j++){
		        JSONObject idList = idAry.getJSONObject(j);
		        JSONObject contactsListObj = getContactsData(token,dc,idList.getString("id"));
		        JSONArray contactsObj = contactsListObj.getJSONArray("members");
		        int len = contactsObj.length();
		        for(int i=0;i<len;i++){
		        	JSONObject contactObj = contactsObj.getJSONObject(i);
		        	JSONObject name = new JSONObject();
		        	JSONObject addressObj = new JSONObject();
					JSONObject birthday = new JSONObject();
					JSONArray email = new JSONArray();
					JSONArray phone = new JSONArray();
					JSONArray address = new JSONArray();
					JSONArray website = new JSONArray();
					String imageurl = "";
					String notes = "";
					if(contactObj.has("email_address")){ //EMAIL
						email.put(contactObj.get("email_address"));
					}
					if(contactObj.has("merge_fields")){ //NAME
						JSONObject nameObj = contactObj.getJSONObject("merge_fields");
						name.put("first_name", nameObj.get("FNAME"));
						name.put("last_name", nameObj.get("LNAME"));
					}
					if(contactObj.has("location")){ //ADDRESS
						JSONObject addrObj = contactObj.getJSONObject("location");
						addressObj.put("country", addrObj.get("country_code"));
						addressObj.put("city", "");
						addressObj.put("state", "");
						addressObj.put("street", "");
						addressObj.put("zip", "");
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
	        }
	        data.put("addressbook", addressbook);
	        data.put("service", "mailchimp");
			jsonresponse.put("data", data);
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("responseStatus", msg);
		}
		catch(JSONException j){
			
		}
		catch(Exception e){
			
		}
		return jsonresponse;
	}
	
	
}
