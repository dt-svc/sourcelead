package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	
    @RequestMapping(value="/getAPIService",  method =  RequestMethod.GET )
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
					obj = ci.getContactsObject(service, consumerkey, consumersecret, token, tokensecret, tokenverifier, userid,request);
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
    
    
    @RequestMapping(value = "/storeCsv", method = RequestMethod.POST)
	public @ResponseBody String storeCsv(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		JSONObject obj = new JSONObject();
		String callback ="";
		processor pr = new processor();
		if(request.getParameter("callback")!=null){
			callback = request.getParameter("callback").toString();
			
		}
		ContactImporter ci = new ContactImporter();
		byte [] byteArr=multiFile.getBytes();
		InputStream inputStream = new ByteArrayInputStream(byteArr);;
		obj = ci.getContactsObject("csv", inputStream,request);
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
        	APICredentials.put("consumerKey", "V4P2tb6xkkjPNTVblTlkZa3E6");
        	APICredentials.put("consumerSecret", "UVSn8ZaxvToCPy5jn7IDKLuQlA02nNTpAiXrzaKLOPFPoSYcab");
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
       		APICredentials.put("consumerKey", "09367f41-ab2e-49aa-8b4b-2868a8034074");
       		APICredentials.put("consumerSecret", "QxWu8nsUQKLKA8QbF4j3co1");
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
        	APICredentials.put("consumerKey", "81wdpepnlmef5o");
	    	APICredentials.put("consumerSecret", "sWQwek2HiUjqrLBL");
        }
		else if(service.equals("yahoo")){
       		APICredentials.put("consumerKey", "dj0yJmk9THZweTRuYnY4d3hWJmQ9WVdrOWJHSTFUalJ2Tm1VbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0yZQ--");
	    	APICredentials.put("consumerSecret", "d6d481b6f2d7b6d448cd83401b3317518d30e452");
        }
		else if(service.equals("foursquare")){
        	APICredentials.put("consumerKey", "YUZNQ1BUKIFEJKXKCGLWKVNQCASW1G50O1TWTEM1KEK3S3VB");
			APICredentials.put("consumerSecret", "1JF21FUR24MBXADKVC0RLP2XDFNTZDTXZ3MOWJNMCRF3TGKM");
        }
    	else if(service.equals("mailchimp")){
        	APICredentials.put("consumerKey", "153010893985");
        	APICredentials.put("consumerSecret", "99ffca5cdb8047fdf9d97ba4fe8ef4878e5be4b12aabfeb684");
        }
		else if(service.equals("eventbrite")){
        	APICredentials.put("consumerKey", "6F4V5IMYFRCFJEKDWG");
	    	APICredentials.put("consumerSecret", "LATRTU52ZHOQVT2KDOO4EMRXM54VM6WG7E75UJRJJCXF2XP7P7");
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
        	APICredentials.put("consumerKey", "1kosTR3wf5rHMYLr071sgsnxM1pE8WsujrIO7jcd62FdsDtqec");
			APICredentials.put("consumerSecret", "jpNNnSL0YacVeKEIcccxZsbzPlX6gr3bAPST9Pq6ZM2RfEuGjD");
		}
    	else{
    		APICredentials.put("consumerKey", "63b2a3143bd306a4");
			APICredentials.put("consumerSecret", "d46z1b2glf0blf2e");
    	}
    	return APICredentials;
    }
	
}
