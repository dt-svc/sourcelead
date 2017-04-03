package com.bis.sourcelead.si.util;
import java.util.StringTokenizer;
import org.json.JSONException;
import org.json.JSONObject;
public class instagram {
processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		//SCOPE - PERMISSION
		String scope = "basic";
        try{
			String authurl = "https://api.instagram.com/oauth/authorize/?client_id=" + consumerkey + "&response_type=code&scope="+ scope +"&redirect_uri=" + callback;
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
			String postdata = "grant_type=authorization_code&client_id=" + consumerkey + "&client_secret=" + consumersecret + "&code=" + code+"&redirect_uri="+callback;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://api.instagram.com/oauth/access_token", postdata,"");
			
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
	 * GET PROFILE INFORMATION
	 */
	protected JSONObject getProfile(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		try{
			String requrl = "https://api.instagram.com/v1/users/self/?access_token=" + token;
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObjMain = new JSONObject(response.toString());
			JSONObject detailsObj = detailsObjMain.getJSONObject("data");
			details.put("aboutMe", detailsObj.get("bio"));
			details.put("id", detailsObj.get("id"));
			String name =  detailsObj.get("full_name").toString();
			details.put("displayName", name);
			if(name.contains(" ")){
				
				String[] splited = name.split("\\s+");
				details.put("firstName", splited[0]);
				details.put("lastName", splited[1]);
			}
			details.put("email", "");
			details.put("profileUrl", detailsObj.get("website"));
			details.put("photo", detailsObj.get("profile_picture"));

			details.put("location", "");
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
