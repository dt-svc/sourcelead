package com.bis.sourcelead.rest;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.si.util.ContactImporter;
import com.bis.sourcelead.si.util.FriendsInviter;
import com.bis.sourcelead.si.util.SocialConnect;
import com.bis.sourcelead.si.util.SocialPost;
import com.bis.sourcelead.si.util.processor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class SocialInviterService {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialInviterService.class);
	
	
	@RequestMapping(value = "/getAPIService")
	@ResponseBody
	public String getAPIService(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {

		JSONObject obj = new JSONObject();
		String callback ="";
		
		if(request.getParameter("callback")!=null){
			callback = request.getParameter("callback").toString();
		}
	    if(request.getParameter("type")!=null){
	    	String token="",consumerkey="",consumersecret="",product="",tokensecret="",tokenverifier="",requestType="",service="",userid="",redirecturl="oob",message="",subject="",friendsids="",title="",link="",picture="",description="",comment="";
	    	if(request.getParameter("type")!=null){
	    		requestType = request.getParameter("type").toString();
	    	}
	    	if(request.getParameter("redirecturl")!=null){
	    		redirecturl = request.getParameter("redirecturl").toString();
	    	}
	    	
	    	if(request.getParameter("friendsids")!=null){
	    		friendsids = request.getParameter("friendsids").toString();
	    	}
	    	if(request.getParameter("message")!=null){
	    		message = request.getParameter("message").toString();
	    	}
	    	if(request.getParameter("subject")!=null){
	    		subject = request.getParameter("subject").toString();
	    	}
	    	if(request.getParameter("service")!=null){
	    		service = request.getParameter("service").toString();
	    		JSONObject credObj = getCredentials(service);
	    		consumerkey = credObj.get("consumerKey").toString();
	    		consumersecret = credObj.get("consumerSecret").toString();
	    	}
	    	if(request.getParameter("token")!=null){
	    		token = request.getParameter("token").toString();
	    	}
	    	if(request.getParameter("tokensecret")!=null){
	    		tokensecret = request.getParameter("tokensecret").toString();
	    	}
	    	if(request.getParameter("tokenverifier")!=null){
	    		tokenverifier = request.getParameter("tokenverifier").toString();
	    	}
	    	if(request.getParameter("userid")!=null){
	    		userid = request.getParameter("userid").toString();
	    	}
	    	if(request.getParameter("product")!=null){
	    		product = request.getParameter("product").toString();
	    	}
	    	if(request.getParameter("title")!=null){
	    		title = request.getParameter("title").toString();
	    	}
	    	if(request.getParameter("link")!=null){
	    		link = request.getParameter("link").toString();
	    	}
	    	if(request.getParameter("picture")!=null){
	    		picture = request.getParameter("picture").toString();
	    	}
	    	if(request.getParameter("description")!=null){
	    		description = request.getParameter("description").toString();
	    	}
	    	if(request.getParameter("comment")!=null){
	    		comment = request.getParameter("comment").toString();
	    	}
	    	
	    	
	    	if(product.equals("socialconnect")){
	    		SocialConnect sc = new SocialConnect();
	    		if(requestType.equals("authentication") && !service.equals("") && !consumerkey.equals("") && !consumersecret.equals("") && !redirecturl.equals("")){
		    		//Get authentication url
		    		obj = sc.getAuthenticationObject(service, consumerkey, consumersecret, redirecturl);
		    	}
		    	else if(requestType.equals("accesstoken")){
		    		//Get access token
		    		obj = sc.getAccessTokenObject(service, consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		    	}
		    	else if(requestType.equals("profile")){
		    		//Get information
		    		obj = sc.getProfileObject(service, consumerkey, consumersecret, token, tokensecret, tokenverifier, userid);
		    	}
		    	else{
		    		
					obj = getErrorResponse();
		    	}
	    		
	    	}
	    	else if(product.equals("socialpost")){
	    		SocialPost sp = new SocialPost();
	    		if(requestType.equals("authentication") && !service.equals("") && !consumerkey.equals("") && !consumersecret.equals("") && !redirecturl.equals("")){
		    		//Get authentication url
		    		obj = sp.getAuthenticationObject(service, consumerkey, consumersecret, redirecturl);
		    	}
		    	else if(requestType.equals("accesstoken")){
		    		//Get access token
		    		obj = sp.getAccessTokenObject(service, consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		    	}
		    	else if(requestType.equals("post")){
		    		//Get information
		    		obj = sp.postToWallObject(redirecturl,service, consumerkey, consumersecret, token, tokensecret, tokenverifier,title,link,picture,description,comment);
		    	}
		    	else{
		    		
					obj = getErrorResponse();
		    	}
	    		
	    	}
	    	else if(product.equals("friendsinviter")){
	    		FriendsInviter fi = new FriendsInviter();
	    		if(requestType.equals("authentication") && !service.equals("") && !consumerkey.equals("") && !consumersecret.equals("") && !redirecturl.equals("")){
		    		//Get authentication url
		    		obj = fi.getAuthenticationObject(service, consumerkey, consumersecret, redirecturl);
		    	}
		    	else if(requestType.equals("accesstoken")){
		    		//Get access token
		    		obj = fi.getAccessTokenObject(service, consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		    	}
		    	else if(requestType.equals("friends")){
		    		//Get friends
					obj = fi.getFriendsObject(service, consumerkey, consumersecret, token, tokensecret, tokenverifier, userid);
		    	}
		    	else if(requestType.equals("message")){
		    		//Send message
		    		obj = fi.sendMessageObject(service, consumerkey, consumersecret, token, tokensecret, tokenverifier, friendsids, subject, message);
		    	}
	    		else{
					obj = getErrorResponse();
		    	}
	    	}
	    	else if(product.equals("contactimporter")){
	    		ContactImporter ci = new ContactImporter();
	    		if(requestType.equals("authentication") && !service.equals("") && !consumerkey.equals("") && !consumersecret.equals("") && !redirecturl.equals("")){
		    		//Get authentication url
		    		obj = ci.getAuthenticationObject(service, consumerkey, consumersecret, redirecturl);
		    	}
		    	else if(requestType.equals("accesstoken")){
		    		//Get access token
		    		obj = ci.getAccessTokenObject(service, consumerkey, consumersecret, redirecturl, token, tokensecret, tokenverifier);
		    	}
		    	else if(requestType.equals("contacts")){
		    		//Get contacts
					obj = ci.getContactsObject(service, consumerkey, consumersecret, token, tokensecret, tokenverifier, userid);
		    	}
	    		else{
					obj = getErrorResponse();
		    	}
	    	}
	    	else{
	    		
				obj = getErrorResponse();
	    	}
	    	
	    }
	    else{
	    	obj = getErrorResponse();
	    }
	    processor pr = new processor();
        if(callback!=""){
	        String responseString = pr.encode(pr.encode(obj.toString()));
	        String strResponse = callback + "('" + responseString + "')";
	        return strResponse;
        }
        else{
        	return toJSON(obj);
        }
	}
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	private JSONObject getErrorResponse(){
		JSONObject jsonresponse = new JSONObject();
		try{
    		JSONObject data = new JSONObject();
    		JSONObject msg = new JSONObject();
    		msg.put("message", "Invalid request");
			msg.put("type", "error");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
		}
		catch(Exception e){
		
		}
		return jsonresponse;
	}
	protected JSONObject getCredentials(String service) throws JSONException{
    	JSONObject APICredentials = new JSONObject();
    	if(service.equals("twitter")){
        	APICredentials.put("consumerKey", "");
        	APICredentials.put("consumerSecret", "");
        }
    	else if (service.equals("facebook")){
        	APICredentials.put("consumerKey", "200281393793197");
        	APICredentials.put("consumerSecret", "3a62bba550c343eb4f42bbeabd2e0f5e");
        }
    	else if(service.equals("googleplus") || service.equals("gmail") || service.equals("google")){
        	APICredentials.put("consumerKey", "926632023543-jvf94kddpdemsb2p7r85j2clp1is9mkl.apps.googleusercontent.com");
        	APICredentials.put("consumerSecret", "UYJFyPtiGr3KE9pV6bzLaAj1");
        }
    	else if(service.equals("xing")){
        	APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
    	else if(service.equals("hotmail") || service.equals("outlook")){
       		APICredentials.put("consumerKey", "");
       		APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("box")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("slack")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("yammer")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("aol")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("github")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("pinterest")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("instagram")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("linkedin")){
        	APICredentials.put("consumerKey", "811b1hw3y5bw02");
	    	APICredentials.put("consumerSecret", "lptiApADpyjdF1PZ");
        }
		else if(service.equals("yahoo")){
       		APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("foursquare")){
        	APICredentials.put("consumerKey", "");
			APICredentials.put("consumerSecret", "");
        }
    	else if(service.equals("mailchimp")){
        	APICredentials.put("consumerKey", "");
        	APICredentials.put("consumerSecret", "");
        }
		else if(service.equals("eventbrite")){
        	APICredentials.put("consumerKey", "");
	    	APICredentials.put("consumerSecret", "");
        }
    	else if(service.equals("mailru")){
        	APICredentials.put("consumerKey", "");
			APICredentials.put("consumerSecret", "");
        }
    	else if(service.equals("delicious")){
        	APICredentials.put("consumerKey", "");
			APICredentials.put("consumerSecret", "");
        }
    	else if(service.equals("tumblr")){
        	APICredentials.put("consumerKey", "");
			APICredentials.put("consumerSecret", "");
		}
    	else{
    		APICredentials.put("consumerKey", "");
			APICredentials.put("consumerSecret", "");
    	}
    	return APICredentials;
    }
	
}
