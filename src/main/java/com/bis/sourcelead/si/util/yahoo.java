package com.bis.sourcelead.si.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

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
class yahoo {
	processor pr = new processor();
    
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String consumerkey,String consumersecret,String callback, String scope)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String authorizationUrl = "";
        authorizationUrl = "https://api.login.yahoo.com/oauth2/request_auth?client_id=" + consumerkey + "&redirect_uri=" + callback + "&response_type=code&scope="+ scope;
		try{
			data.put("token", "");
			data.put("tokensecret", "");
			data.put("tokenverifier", "");
			data.put("service", "yahoo");
			data.put("userid", "");
			data.put("authurl", authorizationUrl);
			
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);	
		}
		catch(Exception e){
			
		}
		return jsonresponse;
	}

	
	/*
	 * GET ACCESS TOKEN
	 */
	protected JSONObject getAccessToken(String consumerkey,String consumersecret,String callback,String token, String tokensecret, String tokenverifier)
	{

		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String get_or_post = "POST";
		String yahoo_endpoint = "https://api.login.yahoo.com/oauth2/get_token";		
		String postdata = "code=" + token + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
        String access_token ="", access_token_secret="", userid="";
		//MAKE API CALL
		String response = pr.excuteHTTPCall(get_or_post, yahoo_endpoint, postdata, "");
		try{
			//PARSE DATA
			if(!response.equals("error")){
				JSONObject jObj = new JSONObject(response);
				access_token = jObj.getString("access_token");
				userid = jObj.getString("xoauth_yahoo_guid");
			}
			if(access_token.equals("")) // if either key is empty, that's weird and bad
			{
				msg.put("message", "Oauth tokens in response were invalid");
				msg.put("type", "error");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else // otherwise, we're all good. Return the values (did not include oauth_token_confirmed here. no need)
			{
				data.put("token", access_token);
				data.put("tokensecret", access_token_secret);
				data.put("tokenverifier", tokenverifier);
				data.put("service", "yahoo");
				data.put("userid", userid);
				data.put("authurl", "");
				
				msg.put("message", "");
				msg.put("type", "success");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
				
			}
		}
		catch(Exception e){
			
		}
		return jsonresponse;
	}
	
	
	/*
	 * GET PROFILE INFORMATION
	 */
	protected JSONObject getProfile(String consumerkey,String consumersecret,String redirecturl,String token,String tokensecret,String tokenverifier, String userid){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		String oauth_signature = "";
		
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String yahoo_endpoint = "https://social.yahooapis.com/v1/user/" + userid + "/profile?format=json";		
		String signature_base_string = "GET&https%3A%2F%2Fsocial.yahooapis.com%2Fv1%2Fuser%2F"+userid+"%2Fprofile&format%3Djson%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+ pr.encode(token)+"%26oauth_version%3D1.0";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&"+ pr.encode(tokensecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String headerValue = "OAuth realm=\"yahooapis.com\",oauth_consumer_key=\""+consumerkey+"\",oauth_nonce=\""+oauth_nonce+"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\""+oauth_timestamp+"\",oauth_token=\""+token+"\",oauth_version=\"1.0\",oauth_signature=\""+oauth_signature+"\"";
		String urlParameters = "oauth_callback="+ pr.encode(redirecturl)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&screen_name="+userid+"&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("GETPOST", yahoo_endpoint, urlParameters, headerValue);
		
		try{
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response);
			JSONObject profileObj = detailsObj.getJSONObject("profile");
			JSONObject statusObj = profileObj.getJSONObject("status");
			details.put("aboutMe", statusObj.get("message"));
			details.put("id", profileObj.get("guid"));
			details.put("displayName", profileObj.get("givenName") + " " + profileObj.get("familyName"));
			details.put("firstName", profileObj.get("givenName"));
			details.put("lastName", profileObj.get("familyName"));
			details.put("email", "");
			details.put("profileUrl", profileObj.get("profileUrl"));
			JSONObject photo = profileObj.getJSONObject("image");
			details.put("photo", photo.get("imageUrl"));
			details.put("location", profileObj.get("location"));
			details.put("gender", profileObj.get("gender"));
			details.put("dob", profileObj.get("birthdate")+"/"+profileObj.get("birthYear"));
			data.put("details", details);
			data.put("service", "yahoo");
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
	protected JSONObject getContacts(String consumerkey,String consumersecret,String redirecturl,String token,String tokensecret,String tokenverifier, String userid,HttpServletRequest request){
		ArrayList<ReferContacts> refContactsList = new ArrayList<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray addressbook = new JSONArray();
		
		String yahoo_endpoint = "https://social.yahooapis.com/v1/user/me/contacts?format=json&access_token="+token+"&view=tinyusercard";
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("GET", yahoo_endpoint, "","");
		
		try{
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response);
			JSONObject contactsObj = detailsObj.getJSONObject("contacts");
			JSONArray contactObj = contactsObj.getJSONArray("contact");
			int len = contactObj.length();
			for(int i=0;i<len;i++){
				JSONObject feildObj = contactObj.getJSONObject(i);
				JSONArray fieldsObj = feildObj.getJSONArray("fields");
				int Objlen = fieldsObj.length();
				JSONObject name = new JSONObject();
				JSONObject addressObj = new JSONObject();
				JSONObject birthday = new JSONObject();
				JSONArray email = new JSONArray();
				JSONArray phone = new JSONArray();
				JSONArray address = new JSONArray();
				JSONArray website = new JSONArray();
				String imageurl = "";
				String notes = "";
				for(int j=0;j<Objlen;j++){
					JSONObject contObj = fieldsObj.getJSONObject(j);
					if(contObj.get("type").equals("name")){ //NAME
						if(contObj.has("value")){
							JSONObject nameObj = contObj.getJSONObject("value");
							name.put("first_name", nameObj.get("givenName"));
							name.put("last_name", nameObj.get("familyName"));
						}
					}
					else if(contObj.get("type").equals("birthday")){ //BIRTHDAY
						if(contObj.has("value")){
							JSONObject dobObj = contObj.getJSONObject("value");
							birthday.put("day", dobObj.get("day"));
							birthday.put("month", dobObj.get("month"));
							birthday.put("year", dobObj.get("year"));
						}
					}
					else if(contObj.get("type").equals("address")){ //ADDRESS
						if(contObj.has("value")){
							JSONObject addrObj = contObj.getJSONObject("value");
							if(addrObj.has("country")){
								addressObj.put("country", addrObj.get("country"));
							}
							if(addrObj.has("city")){
								addressObj.put("city", addrObj.get("city"));
							}
							if(addrObj.has("stateOrProvince")){
								addressObj.put("state", addrObj.get("stateOrProvince"));
							}
							if(addrObj.has("street")){
								addressObj.put("street", addrObj.get("street"));
							}
							if(addrObj.has("postalCode")){
								addressObj.put("zip", addrObj.get("postalCode"));
							}
							address.put(addressObj);
						}
					}
					else if(contObj.get("type").equals("Image")){ //IMAGE
						if(contObj.has("value")){
							JSONObject nameObj = contObj.getJSONObject("value");
							imageurl =  nameObj.get("imageUrl").toString();
						}
					}
					else if(contObj.get("type").equals("notes")){ //NOTES
						if(contObj.has("value")){
							notes = contObj.get("value").toString();
						}
					}
					else if(contObj.get("type").equals("email")){ //EMAIL
						if(contObj.has("value")){
							email.put(contObj.get("value").toString());
						}
					}
					else if(contObj.get("type").equals("link")){ //WEBSITE
						if(contObj.has("value")){
							website.put(contObj.get("value").toString());
						}
					}
					else if(contObj.get("type").equals("phone")){ //PHONE
						if(contObj.has("value")){
							phone.put(contObj.get("value").toString());
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
					ReferContacts referContacts = new ReferContacts();
					 referContacts.setContactSource(Contact_Source.YAHOO);
					 
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
			SocialInviterUtil.storeReferContacts(refContactsList,request);
			data.put("addressbook", addressbook);
			data.put("service", "yahoo");
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
