package com.bis.sourcelead.si.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
public class pinterest {
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		//SCOPE - PERMISSION
		String scope = "read_public";
        try{
			String authurl = "https://api.pinterest.com/oauth/?client_id=" + consumerkey + "&response_type=code&redirect_uri=" + callback + "&scope="+ scope;
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
	        String response = pr.excutePost("POST", "https://api.pinterest.com/v1/oauth/token", postdata,"");
			
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
			String requrl = "https://api.pinterest.com/v1/me/?access_token=" + token + "&fields=first_name%2Cid%2Clast_name%2Curl%2Caccount_type%2Cusername%2Cbio%2Cimage";
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObjMain = new JSONObject(response.toString());
			JSONObject detailsObj = detailsObjMain.getJSONObject("data");
			if(detailsObj.get("bio")!=null){
				details.put("aboutMe", detailsObj.get("bio").toString());
			}
			details.put("id", detailsObj.get("id").toString());
			String name =  detailsObj.get("first_name").toString() + ' ' + detailsObj.get("last_name").toString();
			details.put("displayName", name);
				details.put("firstName", detailsObj.get("first_name").toString());
				details.put("lastName", detailsObj.get("last_name").toString());
			
			details.put("profileUrl", detailsObj.get("url"));
			
			JSONObject imgObj = detailsObj.getJSONObject("image");
			JSONObject img60 = imgObj.getJSONObject("60x60");
			
			details.put("photo", img60.get("url"));

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
