package com.bis.sourcelead.si.util;

import org.json.JSONException;
import org.json.JSONObject;

class delicious {
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
			String authurl = "https://delicious.com/auth/authorize?client_id=" + consumerkey;
	        authurl += "&redirect_uri=" + callback;
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
		String access_token_secret = "";
		try{
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=code";
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://avosapi.delicious.com/api/v1/oauth/token", postdata,"");
			
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", access_token_secret);
			data.put("tokenverifier", "");
			data.put("service", service);
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
	 * POST TO WALL
	 */
	protected JSONObject postToWallObject(String service,String title,String link, String comment, String token){
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		try{
			String postdata = "url=" + pr.encode(link) + "&extended=" + pr.encode(comment) + "&shared=yes&description=" + pr.encode(title);
			
			//MAKE API CALL
	        String response = pr.excutePost("POST", "https://api.del.icio.us/v1/posts/add?"+postdata, postdata,token);
			String access_token_secret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result code=\"error adding link\"/>";
	        
			//PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", access_token_secret);
			data.put("tokenverifier", "");
			data.put("service", service);
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
	private String excutePost(String method, String targetURL, String urlParameters,String token) {
		
		  HttpURLConnection connection = null;  
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod(method);
		    if(method.equals("POST")){
		    	if(token!=""){
		    		connection.setRequestProperty("Authorization","Bearer "+token);
		    		connection.setRequestProperty("Content-Type", "application/json");
		    	}
		    	else{
		    		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    		connection.setRequestProperty("Content-Language", "en-US");
		    	}
			    connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			      
		    }
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    java.util.Scanner s = extracted(is).useDelimiter("\\A");
		    String response = s.hasNext() ? s.next() : "";
		    return response;
		    
		    
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if(connection != null) {
		      connection.disconnect(); 
		    }
		  }
	}

	private Scanner extracted(InputStream is) {
		return new java.util.Scanner(is);
	}*/
}
