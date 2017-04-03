package com.bis.sourcelead.si.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class eventbrite {
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
			String authurl = "https://www.eventbrite.com/oauth/authorize?response_type=code&client_id="+consumerkey+"&scope=r_emailaddress w_share&state=DCEEFWF45453sdffef424&redirect_uri=" + callback;
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
		try{
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://www.eventbrite.com/oauth/token", postdata,"");
			
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", "");
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", "");
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
	 * GET CONTACTS DATA
	 */
	private JSONObject getContactsData(String token,String user_id,String uid) {
		JSONObject jsonresponse = new JSONObject();
		try{
			String requrl = "https://www.eventbriteapi.com/v3/users/"+user_id+"/contact_lists/" + uid + "/contacts/?token=" + pr.encode(token);
			
			//MAKE API CALL
			String response = pr.executeGetJSON(requrl);
			
			//PARSE DATA
	        jsonresponse = new JSONObject(response.toString());
		}
		catch(Exception er){
		}
		return jsonresponse;
	}
	
	
	/*
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray addressbook = new JSONArray();
		try{
			String requrl = "https://www.eventbriteapi.com/v3/users/me/contact_lists/?token=" + pr.encode(token);
			
			//MAKE API CALL
			String response = pr.executeGetJSON(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			JSONArray contactList = detailsObj.getJSONArray("contact_lists");
			int cLen = contactList.length();
			for(int j=0;j<cLen;j++){
				JSONObject userObj = contactList.getJSONObject(j);
				String userid = userObj.getString("user_id");
				String id = userObj.getString("id");
				JSONObject contactObj = getContactsData(token,userid,id);
				JSONArray contacts = contactObj.getJSONArray("contacts");
				int len = contacts.length();
				for(int i=0;i<len;i++){
					JSONObject contact = contacts.getJSONObject(i);
					JSONObject name = new JSONObject();
					JSONObject birthday = new JSONObject();
					JSONArray email = new JSONArray();
					JSONArray phone = new JSONArray();
					JSONArray address = new JSONArray();
					JSONArray website = new JSONArray();
					String imageurl = "";
					String notes = "";
					
					if(contact.has("first_name")){ //FIRST NAME
						if(contact.get("first_name")!=null){
							name.put("first_name", contact.get("first_name"));
						}
					}
					if(contact.has("last_name")){ //LAST NAME
						if(contact.get("last_name")!=null){
							name.put("last_name", contact.get("last_name"));
						}
					}
					if(contact.has("email")){ //EMAIL
						if(contact.get("email")!=null){
							email.put(contact.get("email"));
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
			}
			
			data.put("addressbook", addressbook);
			data.put("service", "eventbrite");
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
