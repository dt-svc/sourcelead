package com.bis.sourcelead.si.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class xing {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String consumerkey,String consumersecret,String callback)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		// generate authorization header
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		String parameter_string =  "oauth_token=&oauth_callback="+ pr.encode(pr.encode(callback))+"&oauth_consumer_key=" + consumerkey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=1.0";
		
		String twitter_endpoint = "https://api.xing.com/v1/request_token";		
		String signature_base_string = get_or_post + "&"+ pr.encode(twitter_endpoint) + "&" + pr.encode(parameter_string);
		signature_base_string = "GET&https%3A%2F%2Fapi.xing.com%2Fv1%2Frequest_token&oauth_callback%3D"+ pr.encode(pr.encode(callback))+"%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_version%3D1.0";
		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&");  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "oauth_callback="+callback+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excutePostXing("GET", twitter_endpoint, urlParameters, "");
		try{
			StringTokenizer st = new StringTokenizer(response, "&");
			String currenttoken = "";
			String oauth_token = "";
			String oauth_token_secret = "";
			while(st.hasMoreTokens())
			{
				currenttoken = st.nextToken();
				if(currenttoken.startsWith("oauth_token="))
					oauth_token = currenttoken.substring(currenttoken.indexOf("=") + 1);
				else if(currenttoken.startsWith("oauth_token_secret="))
					oauth_token_secret = currenttoken.substring(currenttoken.indexOf("=") + 1);
			}
			if(oauth_token.equals("") || oauth_token_secret.equals("")) // if either key is empty, that's weird and bad
			{
				msg.put("message", "error");
				msg.put("type", "oauth tokens in response were invalid");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else 
			{
				data.put("token", oauth_token);
				data.put("tokensecret", oauth_token_secret);
				data.put("tokenverifier", "");
				data.put("service", "xing");
				data.put("userid", "");
				data.put("authurl", "https://api.xing.com/v1/authorize?oauth_token="+oauth_token);
				
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
	 * GET ACCESS TOKEN
	 */
	protected JSONObject getAccessToken(String consumerkey,String consumersecret,String callback,String token, String tokensecret, String tokenverifier)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		// generate authorization header
		String get_or_post = "POST";
		String oauth_signature_method = "HMAC-SHA1";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		String parameter_string =  "oauth_token=&oauth_callback="+ pr.encode(pr.encode(callback))+"&oauth_consumer_key=" + consumerkey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=1.0";
		String twitter_endpoint = "https://api.xing.com/v1/access_token";		
		String signature_base_string = get_or_post + "&"+ pr.encode(twitter_endpoint) + "&" + pr.encode(parameter_string);		
		signature_base_string = "POST&https%3A%2F%2Fapi.xing.com%2Fv1%2Faccess_token&oauth_callback%3D"+ pr.encode(pr.encode(callback))+"%26";
		signature_base_string += "oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26";
		signature_base_string += "oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0";

		
		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&" + pr.encode(tokensecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "oauth_callback="+callback+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		urlParameters = "oauth_callback="+ pr.encode(callback)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp;
		urlParameters += "&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excutePostXing(get_or_post, twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			StringTokenizer st = new StringTokenizer(response, "&");
			String currenttoken = "";
			String access_token = "";
			String access_token_secret = "";
			String user_id = "";
			while(st.hasMoreTokens())
			 {
				 currenttoken = st.nextToken();
				 if(currenttoken.startsWith("oauth_token="))
					 access_token = currenttoken.substring(currenttoken.indexOf("=") + 1);
				 else if(currenttoken.startsWith("oauth_token_secret="))
					 access_token_secret = currenttoken.substring(currenttoken.indexOf("=") + 1);
				 else if(currenttoken.startsWith("user_id="))
					 user_id = currenttoken.substring(currenttoken.indexOf("=") + 1);
			 }
			if(access_token.equals("") || access_token_secret.equals("")) // if either key is empty, that's weird and bad
			{
				msg.put("message", "error");
				msg.put("type", "oauth tokens in response were invalid");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else // otherwise, we're all good. Return the values (did not include oauth_token_confirmed here. no need)
			{
				data.put("token", access_token);
				data.put("tokensecret", access_token_secret);
				data.put("tokenverifier", tokenverifier);
				data.put("service", "xing");
				data.put("userid", user_id);
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
	 * GET FRIENDS
	 */
	protected JSONObject getFriends(String consumerkey,String consumersecret,String token,String tokensecret, String tokenverifer)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		// generate authorization header
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.xing.com/v1/users/me/contacts.json";		
		String signature_base_string = "GET&https%3A%2F%2Fapi.xing.com%2Fv1%2Fusers%2Fme%2Fcontacts.json&oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_version%3D1.0%26user_fields%3Ddisplay_name%252Cid%252Cfirst_name%252Clast_name%252Cgender%252Cbirth_date%252Cphoto_urls%252Cpermalink%252Cprivate_address%252Cbusiness_address%252Cactive_email";
		String oauth_signature = "";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&"+ pr.encode(tokensecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_version=1.0&user_fields=display_name%2Cid%2Cfirst_name%2Clast_name%2Cgender%2Cbirth_date%2Cphoto_urls%2Cpermalink%2Cprivate_address%2Cbusiness_address%2Cactive_email&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excutePostXing("GET", twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			JSONObject jsonobject = new JSONObject(response);
			JSONObject userObj = jsonobject.getJSONObject("contacts");
			JSONArray friendsary = userObj.getJSONArray("users");
			int count = friendsary.length();
			JSONArray friends = new JSONArray();
			for(int i=0;i<count;i++){
				JSONObject friend = new JSONObject();
				 JSONObject name = new JSONObject();
				 JSONObject thisObject = new JSONObject();
				 thisObject = (JSONObject)friendsary.get(i);
				 name.put("first_name", thisObject.get("first_name").toString());
				 name.put("last_name", thisObject.get("last_name").toString());
				 friend.put("name", name);
				 JSONObject photoObj = thisObject.getJSONObject("photo_urls");
				 friend.put("imageurl", photoObj.get("size_96x96").toString());
				 friend.put("id", thisObject.get("id").toString());
				 friend.put("screenname", thisObject.get("id").toString());
				 JSONObject businessObj = thisObject.getJSONObject("business_address");
				 String addr ="";
				 if(businessObj.has("street")){
					 if(!businessObj.get("street").equals(null)){
						 addr += " "+businessObj.get("street").toString();
					 }
				 }
				 if(businessObj.has("city")){
					 if(!businessObj.get("city").equals(null)){
						 addr += " "+businessObj.get("city").toString();
					 }
				 }
				 if(businessObj.has("province")){
					 if(!businessObj.get("province").equals(null)){
						 addr += " "+businessObj.get("province").toString();
					 }
				 }
				 if(businessObj.has("zip_code")){
					 if(!businessObj.get("zip_code").equals(null)){
						 addr += " "+businessObj.get("zip_code").toString();
					 }
				 }
				 if(businessObj.has("country")){
					 if(!businessObj.get("country").equals(null)){
						 addr += " "+businessObj.get("country").toString();
					 }
				 }
				 friend.put("location", addr.trim());
				 friends.put(friend);
			}
			data.put("friends", friends);
			data.put("service", "google");
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
	 * GET LOGGEDIN USER ID
	 */
	private String getLoggedInUserId(String consumerkey,String consumersecret,String token,String tokensecret){
		String userid="";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.xing.com/v1/users/me.json";		
		String signature_base_string = "GET&https%3A%2F%2Fapi.xing.com%2Fv1%2Fusers%2Fme.json&fields%3Did%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_version%3D1.0";
		String oauth_signature = "";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&"+ pr.encode(tokensecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "fields=id&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excutePostXing("GET", twitter_endpoint, urlParameters, "");
		try{
			JSONObject usr = new JSONObject(response);
			JSONArray usrAry = usr.getJSONArray("users");
			JSONObject uids = usrAry.getJSONObject(0);
			userid = uids.get("id").toString();
		}
		catch(Exception e){
			
		}
		
		return userid;
	}
	
	
	/*
	 * SEND MESSAGE INTERNAL FUNCTION
	 */
	private JSONObject fireMessageToReceipient(String friendsids, String message, String subject, String consumerkey, String consumersecret,String access_token, String access_token_secret,String userid){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		// generate authorization header
		String get_or_post = "POST";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		
		String twitter_endpoint = "https://api.xing.com/v1/users/me/conversations.json";		
		
		String signature_base_string = "POST&https%3A%2F%2Fapi.xing.com%2Fv1%2Fusers%2Fme%2Fconversations.json&content%3D"+ pr.encode(pr.encode(message));
		signature_base_string += "%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26";
		signature_base_string += "oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+access_token+"%26oauth_version%3D1.0%26";
		signature_base_string += "recipient_ids%3D"+friendsids+"%26subject%3D"+ pr.encode(pr.encode(subject))+"%26user_id%3D"+userid;

		
		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&" + pr.encode(access_token_secret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "content="+ pr.encode(message)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+access_token+"&oauth_version=1.0&recipient_ids="+friendsids+"&subject="+ pr.encode(subject)+"&user_id="+userid+"&oauth_signature="+oauth_signature;

		//MAKE API CALL
		String response = pr.excutePostXing(get_or_post, twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			JSONObject respo = new JSONObject(response);
			if(respo.has("conversation")){
				msg.put("message", "");
				msg.put("type", "success");
				data.put("status", "success");
				data.put("service", "xing");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(Exception e){
			
		}
		
		 
		 return jsonresponse;

	}
	
	
	/*
	 * SEND MESSAGE
	 */
	protected JSONObject sendMessage(String consumerkey,String consumersecret, String token, String tokensecret,String friendsids, String subject, String message){
		JSONObject jsonresponse = new JSONObject();
		String userid = getLoggedInUserId(consumerkey, consumersecret, token, tokensecret);
		jsonresponse = fireMessageToReceipient(friendsids, message, subject, consumerkey, consumersecret, token, tokensecret, userid);
		return jsonresponse;
	}

	
	/*
	 * POST TO WALL
	 */
	protected JSONObject postToWallObject(String consumerkey,String consumersecret,String callback,String token, String tokensecret, String tokenverifier,String link,String description)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		// generate authorization header
		String get_or_post = "POST";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		callback="oob";
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		
		String twitter_endpoint = "https://api.xing.com/v1/users/me/share/link.json";		
		
		String signature_base_string = "POST&https%3A%2F%2Fapi.xing.com%2Fv1%2Fusers%2Fme%2Fshare%2Flink.json&";
		signature_base_string += "oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26";
		signature_base_string += "oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_version%3D1.0%26";
		signature_base_string += "text%3D"+ pr.encode(pr.encode(description))+"%26uri%3D"+ pr.encode(pr.encode(link));


		
		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&" + pr.encode(tokensecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp;
		urlParameters += "&oauth_token="+ token+"&oauth_version=1.0&text="+ pr.encode(description)+"&uri="+ pr.encode(link)+"&oauth_signature="+oauth_signature;

		//MAKE API CALL
		String response = pr.excutePostXing(get_or_post, twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			JSONObject respo = new JSONObject(response);
			if(respo.has("ids")){
				msg.put("message", "");
				msg.put("type", "success");
				data.put("status", "success");
				data.put("service", "xing");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else{
				msg.put("message", "Error occured while posting.");
				msg.put("type", "error");
				data.put("status", "error");
				data.put("service", "xing");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(Exception e){
			try {
				msg.put("message", "Error occured while posting.");
				msg.put("type", "error");
				data.put("status", "error");
				data.put("service", "xing");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			} catch (JSONException e1) {}
		}
		 return jsonresponse;
	}

}
