package com.bis.sourcelead.si.util;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
public class SocialPost {
	twitter twi = new twitter();
	facebook fac = new facebook();
	linkedin lin = new linkedin();
	xing xin = new xing();
	delicious del = new delicious();
	tumblr tum = new tumblr();
	JSONObject responseObj = new JSONObject();
	processor pr = new processor();
	
	public JSONObject getAuthenticationObject(String service, String consumerkey, String consumersecret, String redirecturl){
		if(service.equals("twitter")){
			responseObj = twi.getAuthenticationUrl(consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("facebook")){
			responseObj = fac.getAuthenticationUrl(consumerkey, redirecturl);
		}
		else if(service.equals("linkedin")){
			responseObj = lin.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("xing")){
			responseObj = xin.getAuthenticationUrl(consumerkey,consumersecret,redirecturl);
		}
		else if(service.equals("delicious")){
			responseObj = del.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("tumblr")){
			responseObj = tum.getAuthenticationUrl(consumerkey, consumersecret, redirecturl);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject getAccessTokenObject(String service, String consumerkey, String consumersecret, String redirecturl, String token, String tokensecret, String tokenverifier){
		if(service.equals("twitter")){
			responseObj = twi.getAccessToken(consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		}
		else if(service.equals("tumblr")){
			responseObj = tum.getAccessToken(consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		}
		else if(service.equals("facebook")){
			responseObj = fac.getAccessToken(token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("linkedin")){
			responseObj = lin.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("xing")){
			responseObj = xin.getAccessToken(consumerkey,consumersecret,redirecturl,token,tokensecret,tokenverifier);
		}
		else if(service.equals("delicious")){
			responseObj = del.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject postToWallObject(String callback,String service, String consumerkey, String consumersecret, String token, String tokensecret, String tokenverifier, String title,String link,String picture,String description,String comment){
		if(service.equals("twitter")){
			responseObj = twi.postToWallObject(description,consumerkey, consumersecret, token, tokensecret, tokenverifier);
		}
		else if(service.equals("tumblr")){
			responseObj = tum.postToWallObject(consumerkey, consumersecret, token, tokensecret, tokenverifier,title,description,picture,link);
		}
		else if(service.equals("facebook")){
			responseObj = fac.postToWallObject(token, service,title,description,comment,link,picture);
		}
		else if(service.equals("linkedin")){
			responseObj = lin.postToWallObject(title,description,comment,link,picture,token);
		}
		else if(service.equals("xing")){
			responseObj = xin.postToWallObject(consumerkey, consumersecret, callback , token, tokensecret, tokenverifier, link, description);
		}
		else if(service.equals("delicious")){
			responseObj = del.postToWallObject(service, title, link, comment, token);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	
}
