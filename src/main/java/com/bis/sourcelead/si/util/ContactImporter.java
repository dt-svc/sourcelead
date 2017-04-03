package com.bis.sourcelead.si.util;
import org.json.JSONObject;
public class ContactImporter {
	google goo = new google();
	mailchimp mai = new mailchimp();
	hotmail hot = new hotmail();
	eventbrite eve = new eventbrite();
	mailru maru = new mailru();
	yahoo yah = new yahoo();
	box bo = new box();
	yammer yam = new yammer();
	aol ao = new aol();
	JSONObject responseObj = new JSONObject();
	processor pr = new processor();
	public JSONObject getAuthenticationObject(String service, String consumerkey, String consumersecret, String redirecturl){
		
		if(service.equals("gmail")){
			responseObj = goo.getAuthenticationUrl(service, "gmail", consumerkey, redirecturl);
		}
		else if(service.equals("mailchimp")){
			responseObj = mai.getAuthenticationUrl(service, "mailchimp", consumerkey, redirecturl);
		}
		else if(service.equals("eventbrite")){
			responseObj = eve.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("hotmail") || service.equals("outlook")){
			service = "outlook";
			responseObj = hot.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("mailru")){
			responseObj = maru.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("yahoo")){
			responseObj = yah.getAuthenticationUrl(consumerkey, consumersecret, redirecturl, "sdct-r");
		}
		else if(service.equals("box")){
			responseObj = bo.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("aol")){
			responseObj = ao.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else if(service.equals("yammer")){
			responseObj = yam.getAuthenticationUrl(service, consumerkey, redirecturl);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject getAccessTokenObject(String service, String consumerkey, String consumersecret, String redirecturl, String token, String tokensecret, String tokenverifier){
		if(service.equals("gmail")){
			responseObj = goo.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("mailchimp")){
			responseObj = mai.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("eventbrite")){
			responseObj = eve.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("box")){
			responseObj = bo.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("yammer")){
			responseObj = yam.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("aol")){
			responseObj = ao.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("hotmail") || service.equals("outlook")){
			responseObj = hot.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("mailru")){
			responseObj = maru.getAccessToken(service, token, consumerkey, consumersecret, redirecturl);
		}
		else if(service.equals("yahoo")){
			responseObj = yah.getAccessToken(consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject getContactsObject(String service, String consumerkey, String consumersecret, String token, String tokensecret, String tokenverifier, String userid){
		if(service.equals("gmail")){
			responseObj = goo.getContacts(consumerkey, consumersecret, token);
		}
		else if(service.equals("mailchimp")){
			responseObj = mai.getContacts(consumerkey, consumersecret, token);
		}
		else if(service.equals("eventbrite")){
			responseObj = eve.getContacts(token, service);
		}
		else if(service.equals("box")){
			responseObj = bo.getContacts(token, service);
		}
		else if(service.equals("yammer")){
			responseObj = yam.getContacts(token, service);
		}
		else if(service.equals("aol")){
			responseObj = ao.getContacts(token, service);
		}
		else if(service.equals("hotmail") || service.equals("outlook")){
			responseObj = hot.getContacts(token, service);
		}
		else if(service.equals("mailru")){
			responseObj = maru.getContacts(token, consumerkey, consumersecret);
		}
		else if(service.equals("yahoo")){
			responseObj = yah.getContacts(consumerkey, consumersecret, "", token, tokensecret, tokenverifier, userid);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	public JSONObject getContactsObject(String service, String uploadurl){
		if(service.equals("csv")){
			outlook ou = new outlook();
			responseObj = ou.getContacts(uploadurl);
		}
		else{
			responseObj = pr.serviceNotAvailable();
		}
		return responseObj;
	}
	
}