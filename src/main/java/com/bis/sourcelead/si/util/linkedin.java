package com.bis.sourcelead.si.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class linkedin {
	processor pr = new processor();
	
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		
		//SCOPE - PERMISSION
		String scope = "r_emailaddress w_share";
        try{
			String authurl = "https://www.linkedin.com/uas/oauth2/authorization?client_id=" + consumerkey;
	        authurl += "&scope=" + scope + "&state=DCEEFWF45453sdffef424&response_type=code" + "&redirect_uri=" + callback;
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
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
	        
	        //MAKE API CALL
	        String response = pr.excutePostLinkedin("POST", "https://www.linkedin.com/uas/oauth2/accessToken", postdata,"");
			
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
			String requrl = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,location,phone-numbers,date-of-birth,picture-url,site-standard-profile-request,email-address)?oauth2_access_token=" + token + "&format=json";
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			details.put("aboutMe", detailsObj.get("headline"));
			details.put("id", detailsObj.get("id"));
			details.put("displayName", detailsObj.get("firstName") + " " + detailsObj.get("lastName"));
			details.put("firstName", detailsObj.get("firstName"));
			details.put("lastName", detailsObj.get("lastName"));
			details.put("email", detailsObj.get("emailAddress"));
			JSONObject profileurl = detailsObj.getJSONObject("siteStandardProfileRequest");
			details.put("profileUrl", profileurl.get("url"));
			details.put("photo", detailsObj.get("pictureUrl"));
			JSONObject location = detailsObj.getJSONObject("location");
			JSONObject country = location.getJSONObject("country");

			details.put("location", location.get("name") +","+country.get("code"));
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
	
	
	/*
	 * GET EXTENDED PROFILE INFORMATION
	 */
	protected JSONObject getExtendedProfile(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		try{
			String requrl = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,location,phone-numbers,specialties,positions,industry,date-of-birth,picture-url,site-standard-profile-request,email-address)?oauth2_access_token=" + token + "&format=json";
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE DATA
			JSONObject detailsObj = new JSONObject(response.toString());
			details.put("aboutMe", detailsObj.get("headline"));
			details.put("id", detailsObj.get("id"));
			details.put("displayName", detailsObj.get("firstName") + " " + detailsObj.get("lastName"));
			details.put("firstName", detailsObj.get("firstName"));
			details.put("lastName", detailsObj.get("lastName"));
			details.put("email", detailsObj.get("emailAddress"));
			JSONObject profileurl = detailsObj.getJSONObject("siteStandardProfileRequest");
			details.put("profileUrl", profileurl.get("url"));
			details.put("photo", detailsObj.get("pictureUrl"));
			JSONObject location = detailsObj.getJSONObject("location");
			JSONObject country = location.getJSONObject("country");

			details.put("location", location.get("name") +","+country.get("code"));
			
			details.put("industry", detailsObj.get("industry"));
			
			JSONObject positionObj = detailsObj.getJSONObject("positions");
			JSONArray positions = positionObj.getJSONArray("values");
			details.put("positions", positions);
			
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

	
	/*
	 * POST TO WALL
	 */
	protected JSONObject postToWallObject(String title,String description,String comment,String link, String picture,String token){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		try{
			String postdata = "{\"comment\":\"" + comment + "\",\"content\":{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"submitted-url\":\"" + link + "\",";
	        postdata += "\"submitted-image-url\":\"" + picture + "\"},\"visibility\":{\"code\":\"anyone\"}}";
	        
	        //MAKE API CALL
	        String response = pr.excutePostLinkedin("POST", "https://www.linkedin.com/v1/people/~/shares?oauth2_access_token="+token, postdata,"token");
	        
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
			if(resp.has("updateKey")){
				msg.put("message", "");
				msg.put("type", "success");
				data.put("status", "success");
				data.put("service", "linkedin");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
			else{
				msg.put("message", "Error occured when posting.");
				msg.put("type", "error");
				data.put("status", "error");
				data.put("service", "linkedin");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(JSONException j){
			
		}
		catch(Exception e){
			
		}
		return jsonresponse;
	}
	
	
	
}
