package com.bis.sourcelead.si.util;

import org.json.JSONObject;

public class FriendsInviter {
	twitter twi = new twitter();
	xing xi = new xing();
	slack slk = new slack();
	google goo = new google();
	JSONObject responseObj = new JSONObject();
	processor pr = new processor();
	public JSONObject getAuthenticationObject(String service, String consumerkey, String consumersecret, String redirecturl){
		if(service.equals("twitter")){
			responseObj = twi.getAuthenticationUrl(consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("google") || service.equals("googleplus")){
			responseObj = goo.getAuthenticationUrl(service, "friendsinviter", consumerkey, redirecturl);
		}
		else if(service.equals("xing")){
			responseObj = xi.getAuthenticationUrl(consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("slack")){
			responseObj = slk.getAuthenticationUrl(service, consumerkey, redirecturl);
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
		else if(service.equals("xing")){
			responseObj = xi.getAccessToken(consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		}
		else if(service.equals("slack")){
			responseObj = slk.getAccessToken(service,token, consumerkey, consumersecret, redirecturl);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject getFriendsObject(String service, String consumerkey, String consumersecret, String token, String tokensecret, String tokenverifier, String userid){
		if(service.equals("twitter")){
			responseObj = twi.getFriends(consumerkey, consumersecret, token, tokensecret, tokenverifier, userid);
		}
		else if(service.equals("google") || service.equals("googleplus")){
			responseObj = goo.getFriends(consumerkey,consumersecret,token);
		}
		else if(service.equals("xing")){
			responseObj = xi.getFriends(consumerkey, consumersecret, token, tokensecret, tokenverifier);
		}
		else if(service.equals("slack")){
			responseObj = slk.getFriends(token, service);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject sendMessageObject(String service, String consumerkey, String consumersecret, String token, String tokensecret, String tokenverifier, String friendsids, String subject, String message){
		if(service.equals("twitter")){
			responseObj = twi.sendMessage(friendsids, message, subject, consumerkey, consumersecret, token, tokensecret, tokenverifier);
		}
		else if(service.equals("google")){
			responseObj = goo.sendMessage(friendsids, message, subject, consumerkey, consumersecret, token, tokensecret);
		}
		else if(service.equals("xing")){
			responseObj = xi.sendMessage(consumerkey, consumersecret, token, tokensecret, friendsids, subject, message);
		}
		else if(service.equals("slack")){
			responseObj = slk.sendMessage(friendsids, message, token);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	
	
	
}
