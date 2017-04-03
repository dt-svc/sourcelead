package com.bis.sourcelead.si.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class box {
	
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
			String authurl = "https://account.box.com/api/oauth2/authorize?client_id=" + consumerkey + "&response_type=code&state=dfsdf&redirect_uri=" + callback;
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
			String postdata = "grant_type=authorization_code&client_id=" + consumerkey + "&client_secret=" + consumersecret + "&code=" + code;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://api.box.com/oauth2/token", postdata,"");
			
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
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray addressbook = new JSONArray();
		try{
			String requrl = "https://api.box.com/2.0/users";
			
			//MAKE API CALL
			String response = pr.excuteHTTPCall("GETPOST",requrl,"","Bearer " + token);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			JSONArray contactList = detailsObj.getJSONArray("entries");
			int cLen = contactList.length();
			for(int j=0;j<cLen;j++){
				JSONObject contact = contactList.getJSONObject(j);
				JSONObject name = new JSONObject();
				JSONObject birthday = new JSONObject();
				JSONArray email = new JSONArray();
				JSONArray phone = new JSONArray();
				JSONArray address = new JSONArray();
				JSONArray website = new JSONArray();
				String imageurl = "";
				String notes = "";
				if(contact.has("name")){ //NAME
					if(contact.get("name")!=null){
						String nme =  contact.get("name").toString();
						if(nme.contains(" ")){
							String[] splited = nme.split("\\s+");
							name.put("first_name", splited[0]);
							name.put("last_name", splited[1]);
						}
						else{
							name.put("first_name", nme);
							name.put("last_name", "");
						}
					}
				}
				
				if(contact.has("login")){ //EMAIL
					if(contact.get("login")!=null){
						email.put(contact.get("login"));
					}
				}
				if(contact.has("avatar_url")){
					if(contact.get("avatar_url")!=null){
						imageurl = contact.get("avatar_url").toString();
					}
				}
				if(contact.has("phone")){ 
					if(contact.get("phone")!=null){
						phone.put(contact.get("phone"));
					}
				}
				if(contact.has("address")){ 
					if(contact.get("address")!=null){
						JSONObject addressObj = new JSONObject();
						addressObj.put("country", "");
						addressObj.put("city", "");
						addressObj.put("state", "");
						addressObj.put("formattedaddress", contact.get("address"));
						address.put(addressObj);
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
			data.put("service", "box");
			jsonresponse.put("data", data);
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("responseStatus", msg);			
		}
		catch(Exception e){
			try{
				msg.put("message", "Error fetching contacts");
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
