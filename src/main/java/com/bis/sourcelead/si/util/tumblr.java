package com.bis.sourcelead.si.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONObject;
class tumblr {
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
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		String twitter_endpoint = "https://www.tumblr.com/oauth/request_token";		
		String signature_base_string = "GET&https%3A%2F%2Fwww.tumblr.com%2Foauth%2Frequest_token&oauth_callback%3D"+ pr.encode(pr.encode(callback))+"%26";
		signature_base_string += "oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp;
		signature_base_string += "%26oauth_version%3D1.0";
		
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
				
		String urlParameters = "oauth_callback="+ pr.encode(callback)+"&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce;
		urlParameters += "&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
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
			else 
			{
				data.put("token", oauth_token);
				data.put("tokensecret", oauth_token_secret);
				data.put("tokenverifier", "");
				data.put("service", "tumblr");
				data.put("userid", "");
				data.put("authurl", "https://www.tumblr.com/oauth/authorize?oauth_token="+oauth_token);
				
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
		
		String twitter_endpoint = "https://www.tumblr.com/oauth/access_token";		
		String signature_base_string = get_or_post + "&"+ pr.encode(twitter_endpoint) + "&" + pr.encode(parameter_string);
		
		signature_base_string = "POST&https%3A%2F%2Fwww.tumblr.com%2Foauth%2Faccess_token&oauth_callback%3Dhttp%253A%252F%252Flocalhost%253A54355%252Fsocialinviter%252Foauth.html%26";
		signature_base_string += "oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp;
		signature_base_string +="%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0";

		
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
				
		String urlParameters = "oauth_callback=http%3A%2F%2Flocalhost%3A54355%2Fsocialinviter%2Foauth.html&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce;
		urlParameters += "&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token+"&oauth_verifier="+tokenverifier+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
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
				data.put("service", "tumblr");
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
	 * GET BLOG NAME
	 */
	private String getBlogName(String token,String tokensecret,String tokenverifier,String consumerkey,String consumersecret)
	{
		String bName="";
		
		// generate authorization header
		String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
		
		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		String twitter_endpoint = "https://api.tumblr.com/v2/user/info";		
		String signature_base_string = "GET&https%3A%2F%2Fapi.tumblr.com%2Fv2%2Fuser%2Finfo&oauth_callback%3Doob%26oauth_consumer_key%3D"+consumerkey;
		signature_base_string += "%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp;
		signature_base_string += "%26oauth_token%3D"+token+"%26oauth_version%3D1.0";
		
		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			//oauth_signature = computeSignature(signature_base_string, consumersecret+"&");  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.computeSignature(signature_base_string, consumersecret+"&" + pr.encode(tokensecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
			oauth_signature = pr.encode(oauth_signature);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		String urlParameters = "oauth_callback=oob&oauth_consumer_key="+consumerkey+"&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp;
		urlParameters += "&oauth_token="+token+"&oauth_version=1.0&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("GET", twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			JSONObject blogObj = new JSONObject(response);
			JSONObject resObj = blogObj.getJSONObject("response");
			JSONObject userObj = resObj.getJSONObject("user");
			JSONArray blogNames = userObj.getJSONArray("blogs");
			JSONObject blognameObj = blogNames.getJSONObject(0);
			bName = blognameObj.getString("uuid");
		}
		catch(Exception ex){
		
		}
		return bName;
	}
	
	
	/*
	 * POST TO WALL
	 */
	protected JSONObject postToWallObject(String consumerkey, String consumersecret,String token, String tokensecret,String tokenverifier,String title, String description,String picture,String url)
	{
		//DECLARATION
		 JSONObject jsonresponse = new JSONObject();
		 JSONObject data = new JSONObject();
		 JSONObject msg = new JSONObject();
		 
		//GET BLOG NAME
		String blogName = getBlogName(token,tokensecret,tokenverifier,consumerkey,consumersecret);
		
		 String oauth_nonce = pr.GenerateNonce(); // any relatively random alphanumeric string will work here
			
		 // get the timestamp
		 Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		String twitter_endpoint = "https://api.tumblr.com/v2/blog/" + blogName + "/post";		
		String signature_base_string = "POST&https%3A%2F%2Fapi.tumblr.com%2Fv2%2Fblog%2Fnavinleon.tumblr.com%2Fpost&description%3DEngage%2520your%2520user%2520using%2520socialinviter%2520services.%26oauth_callback%3Doob%26";
		signature_base_string += "oauth_consumer_key%3D"+consumerkey+"%26oauth_nonce%3D"+oauth_nonce+"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D"+oauth_timestamp;
		signature_base_string +="%26oauth_token%3D"+token+"%26oauth_verifier%3D"+tokenverifier+"%26oauth_version%3D1.0%26thumbnail%3Dhttp%253A%252F%252Fsocialinviter.com%252Fassets%252Fimg%252Ficons%252Fbrandlogo.jpg%26title%3DSocial%2520Inviter%26type%3Dlink%26url%3Dhttp%253A%252F%252Fsocialinviter.com";
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
				
		String urlParameters = "description=Engage%20your%20user%20using%20socialinviter%20services.&oauth_callback=oob&oauth_consumer_key="+consumerkey;
		urlParameters += "&oauth_nonce="+oauth_nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+oauth_timestamp+"&oauth_token="+token;
		urlParameters += "&oauth_verifier="+tokenverifier+"&oauth_version=1.0&thumbnail=http%3A%2F%2Fsocialinviter.com%2Fassets%2Fimg%2Ficons%2Fbrandlogo.jpg&title=Social%20Inviter&type=link&url=http%3A%2F%2Fsocialinviter.com&oauth_signature="+oauth_signature;
		
		//MAKE API CALL
		String response = pr.excuteHTTPCall("POST", twitter_endpoint, urlParameters, "");
		try{
			
			//PARSE DATA
			JSONObject respObj = new JSONObject(response);
			JSONObject respo = respObj.getJSONObject("response");
			if(respo.has("id")){
				msg.put("message", "");
				msg.put("type", "success");
				data.put("status", "success");
				data.put("service", "tumblr");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else{
				msg.put("message", "Error occured when posting.");
				msg.put("type", "error");
				data.put("status", "error");
				data.put("service", "tumblr");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(Exception e){
			
		}
		
		 return jsonresponse;
	}

}
