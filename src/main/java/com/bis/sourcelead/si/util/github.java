package com.bis.sourcelead.si.util;

import java.util.StringTokenizer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
public class github {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		//SCOPE - PERMISSION
		String scope = "user";
        try{
			String authurl = "https://github.com/login/oauth/authorize?client_id=" + consumerkey + "&response_type=code&redirect_uri=" + callback + "&scope="+scope;
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
			String access_token_secret = "", user_id = "";
			String postdata = "grant_type=authorization_code&client_id=" + consumerkey + "&client_secret=" + consumersecret + "&code=" + code;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://github.com/login/oauth/access_token", postdata,"");
			
	        //PARSE DATA
	        StringTokenizer st = new StringTokenizer(response, "&");
			String currenttoken = "";
			String oauth_token = "";
			while(st.hasMoreTokens())
			{
				currenttoken = st.nextToken();
				if(currenttoken.startsWith("access_token="))
					oauth_token = currenttoken.substring(currenttoken.indexOf("=") + 1);
			}
	        data.put("token", oauth_token);
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
			String requrl = "https://api.github.com/user?access_token="+token;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			details.put("aboutMe", detailsObj.get("bio"));
			details.put("id", detailsObj.get("id"));
			String name =  detailsObj.get("name").toString();
			details.put("displayName", name);
			if(name.contains(" ")){
				
				String[] splited = name.split("\\s+");
				details.put("firstName", splited[0]);
				details.put("lastName", splited[1]);
			}
			details.put("email", detailsObj.get("email"));
			details.put("profileUrl", detailsObj.get("url"));
			details.put("photo", detailsObj.get("avatar_url"));

			details.put("location", detailsObj.get("location"));
			details.put("gender", "");
			details.put("dob", "");
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
	
}
