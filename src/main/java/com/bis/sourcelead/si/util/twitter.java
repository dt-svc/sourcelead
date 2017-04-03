package com.bis.sourcelead.si.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
class twitter {
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
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); 		// get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		String parameter_string =  "oauth_token=&oauth_callback="+ pr.encode(pr.encode(callback))+"&oauth_consumer_key=" + consumerkey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=1.0";
		String twitter_endpoint = "https://api.twitter.com/oauth/request_token";		
		String signature_base_string = get_or_post + "&"+ pr.encode(twitter_endpoint) + "&" + pr.encode(parameter_string);
		signature_base_string = "GET&https%3A%2F%2Fapi.twitter.com%2Foauth%2Frequest_token&oauth_callback%3D"+ pr.encode(pr.encode(callback))+"%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_version%3D1.0";
		
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
				
		String urlParameters = "oauth_callback="+ pr.encode(callback)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("GET", twitter_endpoint, urlParameters, "");
		
		try{
			//PARSE DATA
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
				msg.put("message", "Oauth tokens in response were invalid");
				msg.put("type", "error");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else // otherwise, we're all good. Return the values (did not include oauth_token_confirmed here. no need)
			{
				data.put("token", oauth_token);
				data.put("tokensecret", oauth_token_secret);
				data.put("tokenverifier", "");
				data.put("service", "twitter");
				data.put("userid", "");
				data.put("authurl", "https://api.twitter.com/oauth/authorize?oauth_token="+oauth_token);
				
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
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		String parameter_string =  "oauth_token=&oauth_callback="+ pr.encode(pr.encode(callback))+"&oauth_consumer_key=" + consumerkey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=1.0";
		String twitter_endpoint = "https://api.twitter.com/oauth/access_token";		
		String signature_base_string = get_or_post + "&"+ pr.encode(twitter_endpoint) + "&" + pr.encode(parameter_string);
		signature_base_string = "GET&https%3A%2F%2Fapi.twitter.com%2Foauth%2Faccess_token&oauth_callback%3D"+ pr.encode(pr.encode(callback))+"%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0";

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
				
		String urlParameters = "oauth_callback="+ pr.encode(callback)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall(get_or_post, twitter_endpoint, urlParameters, "");
		
		try{
			//PARSE DATA
			StringTokenizer st = new StringTokenizer(response, "&");
			String currenttoken = "";
			String access_token = "";
			String access_token_secret = "";
			String screen_name="";
			while(st.hasMoreTokens())
			 {
				 currenttoken = st.nextToken();
				 if(currenttoken.startsWith("oauth_token="))
					 access_token = currenttoken.substring(currenttoken.indexOf("=") + 1);
				 else if(currenttoken.startsWith("oauth_token_secret="))
					 access_token_secret = currenttoken.substring(currenttoken.indexOf("=") + 1);
				 else if(currenttoken.startsWith("screen_name="))
					 screen_name = currenttoken.substring(currenttoken.indexOf("=") + 1);
			 }
			if(access_token.equals("") || access_token_secret.equals("")) // if either key is empty, that's weird and bad
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
				data.put("service", "xing");
				data.put("userid", screen_name);
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
	 * GET DETAILS
	 */
	private JSONArray getDetails(String consumerkey,String consumersecret,String token,String tokensecret,String tokenverifier, String userid)
	{
		JSONArray detailAry = new JSONArray();
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.twitter.com/1.1/users/lookup.json";		
		String signature_base_string = "POST&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fusers%2Flookup.json&oauth_callback%3Doob%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0%26user_id%3D"+ pr.encode(pr.encode(userid));
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
				
		String urlParameters = "oauth_callback=oob&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&user_id="+ pr.encode(userid)+"&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("POST", twitter_endpoint, urlParameters, "");
		
		try{
			//PARSE DATA
			JSONArray friendslist = new JSONArray(response);
			detailAry = friendslist;
		}
		catch(Exception e){
			
		}
		
		return detailAry;
	}
	
	
	/*
	 * GET FRIENDS
	 */
	protected JSONObject getFriends(String consumerkey,String consumersecret,String token,String tokensecret,String tokenverifier, String userid)
	{
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray friends = new JSONArray();
		
		// generate authorization header
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.twitter.com/1.1/friends/ids.json";		
		String signature_base_string = "GET&https%3A%2F%2Fapi.twitter.com%2F1.1%2Ffriends%2Fids.json&oauth_callback%3Doob%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0%26screen_name%3D"+userid;
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
				
		String urlParameters = "oauth_callback=oob&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&screen_name="+userid+"&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("GET", twitter_endpoint, urlParameters, "");
		
		try{
			
			//PARSE DATA 
			JSONObject jo = new JSONObject(response);
			 JSONArray ids = jo.getJSONArray("ids");
			 int len = ids.length();
			 int count = (int)Math.ceil(((double)len) / 100.00);
			 String userids = "";
			 int looplen = 0;
			 
			 //EXTRACT IDS
			 for(int j=0;j<count;j++){
				 int startval = looplen;
				 looplen = looplen + 100;
				 int first=0;
				 if(looplen>len){
					 looplen = looplen - len;
					 looplen = looplen + startval;
				 }
				 userids="";
				 for(int i=startval;i<looplen;i++){
					 
						 if(first==0){
							 userids = ids.get(i).toString();
							 first=1;
						 }
						 else{
							 if(i<len){
								 userids += ","+ ids.get(i).toString();
							 }
						 }
					 
				 }
				 
				 
				 JSONArray friendsary = new JSONArray();
				 //GET FRIENDS DETAILS
				 friendsary = getDetails(consumerkey, consumersecret, token, tokensecret, tokenverifier, userids);
				 
				 int fcount = friendsary.length();
				 for(int n=0;n<fcount;n++){
					 JSONObject friend = new JSONObject();
					 JSONObject name = new JSONObject();
					 JSONObject thisObject = new JSONObject();
					 thisObject = (JSONObject)friendsary.get(n);
					 
					 //NAME
					 if(thisObject.get("name").toString().trim().indexOf(" ")!=-1){
						 String[] strname = thisObject.get("name").toString().trim().split(" ");
						 name.put("first_name", strname[0].toString());
						 name.put("last_name", strname[1].toString());
					 }
					 else{
						 name.put("first_name", thisObject.get("name").toString());
				
						 name.put("last_name", "");
					 }
					 friend.put("name", name);
					 
					 //IMAGE
					 String imageurl = thisObject.get("profile_image_url").toString();
					 friend.put("imageurl", imageurl);
					 
					 //ID
					 friend.put("id", thisObject.get("id").toString());
					 
					 //SCREEN NAME
					 friend.put("screenname", thisObject.get("screen_name").toString());
					 
					 //LOCATION
					 friend.put("location", thisObject.get("location").toString());
					 friends.put(friend);
				 }
				 
			 }
			 
			 data.put("friends", friends);
			 data.put("service", "twitter");
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
	 * SEND MESSAGE
	 */
	protected JSONObject sendMessage(String friendsids, String message, String subject, String consumerkey, String consumersecret,String token, String tokensecret, String tokenverifier){
		JSONObject jsonresponse = new JSONObject();
		if(friendsids.indexOf(",")!=-1){
			String[] friends = friendsids.split(",");
			int len = friends.length;
			for(int i=0;i<len;i++){
				//SEND MESSAGE ONE BY ONE
				jsonresponse = fireMessageToReceipient(friends[i], message, subject, consumerkey, consumersecret, token, tokensecret,tokenverifier);
			}
		}
		else{
			//SEND MESSAGE ONE BY ONE
			jsonresponse = fireMessageToReceipient(friendsids, message, subject, consumerkey, consumersecret, token, tokensecret, tokenverifier);
		}
		return jsonresponse;
	}
	
	
	/*
	 * SEND MESSAGE INTERNAL FUNCTION
	 */
	private JSONObject fireMessageToReceipient(String friendsids, String message, String subject, String consumerkey, String consumersecret,String token, String tokensecret,String tokenverifier)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.twitter.com/1.1/direct_messages/new.json";		
		String signature_base_string = "POST&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fdirect_messages%2Fnew.json&oauth_callback%3Doob%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0%26text%3D"+ pr.encode(pr.encode(message))+"%26user_id%3D"+friendsids;
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
				
		String urlParameters = "oauth_callback=oob&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&text="+ pr.encode(message)+"&user_id="+friendsids+"&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("POST", twitter_endpoint, urlParameters, "");
		
		try{
			
			//PARSE DATA
			JSONObject respo = new JSONObject(response);
			if(respo.has("id")){
			msg.put("message", "");
			 msg.put("type", "success");
			 data.put("status", "success");
			 data.put("service", "twitter");
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
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		
		// generate authorization header
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.twitter.com/1.1/users/show.json";		
		String signature_base_string = "GET&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fusers%2Fshow.json&oauth_callback%3D"+ pr.encode(redirecturl)+"%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0%26screen_name%3D"+userid;
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
				
		String urlParameters = "oauth_callback="+ pr.encode(redirecturl)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&screen_name="+userid+"&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("GET", twitter_endpoint, urlParameters, "");
		
		try{
			
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response);
			details.put("aboutMe", detailsObj.get("description"));
			details.put("id", detailsObj.get("id"));
			details.put("displayName", detailsObj.get("name"));
			if(detailsObj.get("name").toString().trim().indexOf(" ")!=-1){
				 String[] strname = detailsObj.get("name").toString().trim().split(" ");
				 details.put("firstName", strname[0].toString());
				 details.put("lastName", strname[1].toString());
			}
			details.put("email", "");
			details.put("profileUrl", "https://www.twitter.com/" +detailsObj.get("screen_name"));
			details.put("photo", detailsObj.get("profile_image_url_https"));
			details.put("location", detailsObj.get("location"));
			details.put("gender", "");
			details.put("dob", "");
			data.put("details", details);
			data.put("service", "twitter");
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
	 * POST TO WALL
	 */
	protected JSONObject postToWallObject(String description,String consumerkey, String consumersecret,String token, String tokensecret,String tokenverifier)
	{
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		Calendar tempcal = Calendar.getInstance(); // get the timestamp
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.twitter.com/1.1/statuses/update.json";		
		String signature_base_string = "POST&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fstatuses%2Fupdate.json&oauth_callback%3Doob%26oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp+"%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0%26status%3D"+ pr.encode(pr.encode(description));
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
				
		String urlParameters = "oauth_callback=oob&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&status="+ pr.encode(description)+"&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("POST", twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			JSONObject respo = new JSONObject(response);
			if(respo.has("id")){
				msg.put("message", "");
				msg.put("type", "success");
				data.put("status", "success");
				data.put("service", "twitter");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else{
				msg.put("message", "Error occured when posting.");
				msg.put("type", "error");
				data.put("status", "error");
				data.put("service", "twitter");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(Exception e){
			
		}
		
		 return jsonresponse;
	}

}
