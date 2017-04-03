package com.bis.sourcelead.si.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

class foursquare {
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
			String authurl = "https://foursquare.com/oauth2/authenticate?client_id=" + consumerkey;
	        authurl += "&response_type=code&redirect_uri=" + callback;
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
			
			String requrl = "https://foursquare.com/oauth2/access_token?client_id="+consumerkey+"&client_secret="+consumersecret+"&grant_type=authorization_code&redirect_uri="+callback+"&code=" + code;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
	        //PARSE DATA
			JSONObject resp = new JSONObject(response.toString());
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
	 * GET PROFILE INFORMATION
	 */
	protected JSONObject getProfile(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		try{
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String ver = ft.format(new Date()).toString();
			ver = ver.replace("-", "");
			String requrl = "https://api.foursquare.com/v2/users/self?oauth_token=" + token + "&v="+ver;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			JSONObject profileObj = detailsObj.getJSONObject("response");
			JSONObject userObj = profileObj.getJSONObject("user");
			details.put("aboutMe", userObj.get("bio"));
			details.put("id", userObj.get("id"));
			details.put("displayName", userObj.get("firstName") + " " + userObj.get("lastName"));
			details.put("firstName", userObj.get("firstName"));
			details.put("lastName", userObj.get("lastName"));
			
			JSONObject email = userObj.getJSONObject("contact");
			details.put("email", email.get("email"));
			
			details.put("profileUrl", userObj.get("canonicalUrl"));
			
			JSONObject image = userObj.getJSONObject("photo");
			details.put("photo", image.get("prefix") + "84x84"+image.get("suffix"));
			
			
			details.put("location", userObj.get("homeCity"));
			details.put("gender", userObj.get("gender"));
			details.put("dob", userObj.get("birthday"));
			data.put("details", details);
			data.put("service", service);
			jsonresponse.	put("data", data);
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
