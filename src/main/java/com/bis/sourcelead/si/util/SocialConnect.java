package com.bis.sourcelead.si.util;

import org.json.JSONObject;

public class SocialConnect {
	twitter twi = new twitter();
	google goo = new google();
	facebook fac = new facebook();
	hotmail hot = new hotmail();
	linkedin lin = new linkedin();
	yahoo yah = new yahoo();
	foursquare fou = new foursquare();
	github git = new github();
	pinterest pin = new pinterest();
	instagram ins = new instagram();
	
	JSONObject responseObj = new JSONObject();
	processor pr = new processor();
	public JSONObject getAuthenticationObject(String service, String consumerkey, String consumersecret, String redirecturl){
		if(service.equals("twitter")){
			responseObj = twi.getAuthenticationUrl(consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("google") || service.equals("googleplus")){
			responseObj = goo.getAuthenticationUrl(service, "google", consumerkey, redirecturl);
		}
		else if(service.equals("facebook")){
			responseObj = fac.getAuthenticationUrl(consumerkey, redirecturl);
		}
		else if(service.equals("hotmail") || service.equals("outlook")){
			service = "outlook";
			responseObj = hot.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("linkedin")){
			responseObj = lin.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("github")){
			responseObj = git.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("pinterest")){
			responseObj = pin.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("instagram")){
			responseObj = ins.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("yahoo")){
			responseObj = yah.getAuthenticationUrl(consumerkey, consumersecret, redirecturl, "sdps-r sdrl-w");
		}
		else if(service.equals("foursquare")){
			responseObj = fou.getAuthenticationUrl(service, consumerkey, redirecturl);
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
		else if(service.equals("google") || service.equals("googleplus")){
			responseObj = goo.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("facebook")){
			responseObj = fac.getAccessToken(token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("hotmail")){
			responseObj = hot.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("linkedin")){
			responseObj = lin.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("github")){
			responseObj = git.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("pinterest")){
			responseObj = pin.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("instagram")){
			responseObj = ins.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("yahoo")){
			responseObj = yah.getAccessToken(consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		}
		else if(service.equals("foursquare")){
			responseObj = fou.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject getProfileObject(String service, String consumerkey, String consumersecret, String token, String tokensecret, String tokenverifier, String userid){
		if(service.equals("twitter")){
			responseObj = twi.getProfile(consumerkey, consumersecret, "oob", token, tokensecret, tokenverifier, userid);
		}
		else if(service.equals("google") || service.equals("googleplus") ){
			responseObj = goo.getProfile(token,service);
		}
		else if(service.equals("facebook")){
			responseObj = fac.getProfile(token, service);
		}
		else if(service.equals("hotmail")){
			responseObj = hot.getProfile(token, service);
		}
		else if(service.equals("linkedin")){
			responseObj = lin.getProfile(token, service);
		}
		else if(service.equals("github")){
			responseObj = git.getProfile(token, service);
		}
		else if(service.equals("pinterest")){
			responseObj = pin.getProfile(token, service);
		}
		else if(service.equals("instagram")){
			responseObj = ins.getProfile(token, service);
		}
		else if(service.equals("yahoo")){
			responseObj = yah.getProfile(consumerkey, consumersecret, "", token, tokensecret, tokenverifier, userid);
		}
		else if(service.equals("foursquare")){
			responseObj = fou.getProfile(token, service);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	
}
