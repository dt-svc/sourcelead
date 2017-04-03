package com.bis.sourcelead.si.util;

import java.util.StringTokenizer;
import org.json.JSONException;
import org.json.JSONObject;

class facebook {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String scope = "user_about_me,user_birthday,user_location,email,publish_actions,user_posts";
		
        try{
        	String authurl = "https://www.facebook.com/dialog/oauth?client_id=" + consumerkey + "&display=popup&redirect_uri=" + callback + "&scope=" + scope;
	        data.put("token", "");
			data.put("tokensecret", "");
			data.put("tokenverifier", "");
			data.put("service", "facebook");
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
	protected JSONObject getAccessToken(String token, String consumerkey, String consumersecret, String callback){
		JSONObject jsonresponse = new JSONObject();
		try{
			JSONObject data = new JSONObject();
			JSONObject msg = new JSONObject();
			String requrl = "https://graph.facebook.com/oauth/access_token?client_id="+consumerkey+"&redirect_uri="+ pr.encode(callback)+"&client_secret="+consumersecret+"&code="+token;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
			
			//PARSE DATA
	        StringTokenizer st = new StringTokenizer(response.toString(), "&");
			String currenttoken = "";
			String oauth_token = "";
	
			while(st.hasMoreTokens())
			{
				currenttoken = st.nextToken();
				if(currenttoken.startsWith("access_token="))
					oauth_token = currenttoken.substring(currenttoken.indexOf("=") + 1);
			}
	        data.put("token", oauth_token);
			data.put("tokensecret", "");
			data.put("tokenverifier", "");
			data.put("service", "facebook");
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
	 * GET PROFILE INFORMATION
	 */
	protected JSONObject getProfile(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		try{
			String requrl = "https://graph.facebook.com/v2.1/me?fields=id,name,first_name,last_name,birthday,picture,link,location,email,gender&access_token="+token+"&format=json";
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			if(detailsObj.has("bio")){
				details.put("aboutMe", detailsObj.get("bio"));
			}
			details.put("id", detailsObj.get("id"));
			details.put("displayName", detailsObj.get("name"));
			details.put("firstName", detailsObj.get("first_name"));
			details.put("lastName", detailsObj.get("last_name"));
			details.put("email", detailsObj.get("email"));
			details.put("profileUrl", detailsObj.get("link"));
			JSONObject picture = detailsObj.getJSONObject("picture");
			JSONObject image = picture.getJSONObject("data");
			details.put("photo", image.get("url"));
			if(detailsObj.has("location")){
				JSONObject location = detailsObj.getJSONObject("location");
				details.put("location", location.get("name"));
			}
			if(detailsObj.has("gender")){
				details.put("gender", detailsObj.get("gender"));
			}
			if(detailsObj.has("birthday")){
				details.put("dob", detailsObj.get("birthday"));
			}
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
	 * POST TO WALL
	 */
	protected JSONObject postToWallObject(String token,String service, String title, String description, String comment,String link,String picture){
		JSONObject jsonresponse = new JSONObject();
		try{
			JSONObject data = new JSONObject();
			JSONObject msg = new JSONObject();
			String postdata = "privacy=" + ("{value:'EVERYONE'}") + "&message=" + pr.encode(description) + "&link=" + pr.encode(link);
	        String response = pr.excutePost("POST", "https://graph.facebook.com/v2.5/feed?access_token="+token+ "&format=json", postdata,"");
	        try{
				JSONObject respo = new JSONObject(response);
				if(respo.has("id")){
					msg.put("message", "");
					msg.put("type", "success");
					data.put("status", "success");
					data.put("service", "facebook");
					jsonresponse.put("data", data);
					jsonresponse.put("responseStatus", msg);
				}
				else{
					msg.put("message", "Error occured when posting.");
					msg.put("type", "error");
					data.put("status", "success");
					data.put("service", "facebook");
					jsonresponse.put("data", data);
					jsonresponse.put("responseStatus", msg);
				}
			}
			catch(Exception e){
				msg.put("message", "Error occured when posting.");
				msg.put("type", "error");
				data.put("status", "success");
				data.put("service", "facebook");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(Exception e){
			
		}
		return jsonresponse;
	}
	
}
