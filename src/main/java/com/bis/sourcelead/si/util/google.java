package com.bis.sourcelead.si.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Contact_Source;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.impl.ReferContactsServiceImpl;
import com.bis.sourcelead.util.SocialInviterUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
@RestController
class google {
	
	@Autowired
	HttpServletRequest request;
	
	@Configuration
	static class googleServiceContextConfiguration {
		@Bean
		public ReferContactsService referContactsService() {
			return new ReferContactsServiceImpl();
		}
	}
	
	@Autowired
	ReferContactsService referContactsService;
	
	processor pr = new processor();
	
	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service,String product,String consumerkey,String callback){
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		//SCOPE PERMISSIONS
		String scope = "https://www.google.com/m8/feeds";
		if(service.equals("google") || service.equals("googleplus")){
			scope = "openid+profile+email+https://www.googleapis.com/auth/userinfo.profile";
		}
        if(product.equals("friendsinviter") && (service.equals("google") || service.equals("googleplus")))   
        {
            scope = "https://www.googleapis.com/auth/gmail.compose https://www.google.com/m8/feeds";
        }
        
        try{
			String authurl = "https://accounts.google.com/o/oauth2/auth?client_id=" + consumerkey;
	        authurl += "&scope=" + scope + "&approval_prompt=force&response_type=code" + "&redirect_uri=" + callback;
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
			String access_token_secret = "";
			String user_id = "";
			String postdata = "code=" + code + "&client_id=" + consumerkey + "&client_secret=" + consumersecret;
	        postdata += "&grant_type=authorization_code&redirect_uri=" + callback;
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://accounts.google.com/o/oauth2/token", postdata,"");
			
	        //PARSE
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
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String consumerkey, String consumersecret,String token,HttpServletRequest request) {
		JSONObject jsonresponse = new JSONObject();
		
		ArrayList<ReferContacts> refContactsList = new ArrayList<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		try{
			JSONObject data = new JSONObject();
			JSONObject msg = new JSONObject();
			JSONArray friends = new JSONArray();
			JSONObject feed = new JSONObject();
			
			String requrl = "https://www.google.com/m8/feeds/contacts/default/full?v=3.0&oauth_token=" + token + "&max-results=99999&alt=json";
			//MAKE API CALL
			String response = pr.executeGet(requrl);
			
	        //PARSE
	        JSONObject friendsObj = new JSONObject(response.toString());
	        feed = friendsObj.getJSONObject("feed");
	        JSONArray friendsary = feed.getJSONArray("entry");
	        int fcount = friendsary.length();
	        
	        //ITERATE CONTACTS
			for(int n=0;n<fcount;n++){
				 JSONObject friend = new JSONObject();
				 JSONObject name = new JSONObject();
				 JSONObject thisObject = new JSONObject();
				 thisObject = friendsary.getJSONObject(n);
				 if(thisObject.has("gd$email")){
					
					 ReferContacts referContacts = new ReferContacts();
					 referContacts.setContactSource(Contact_Source.GOOGLE);
					 referContacts.setReferFromUser(loginUser);
					 referContacts.setOrgLocationId(orgLoc);
					 referContacts.setCreatedDate(new Date());
					 referContacts.setUpdatedDate(new Date());
					 referContacts.setCreatedBy(loginUser.getUserCode());
					 referContacts.setUpdatedBy(loginUser.getUserCode());
					 JSONArray emailAry = thisObject.getJSONArray("gd$email");
					 if(emailAry.length()>0){
						//EMAIL
						 int emailLen = emailAry.length();
						 JSONArray emails = new JSONArray();
						 JSONObject emailObj = null;
						 for(int k=0;k<emailLen;k++){
							  emailObj = (JSONObject)emailAry.get(k);
							 emails.put(emailObj.get("address").toString());
							 if(k==0){
								 referContacts.setContactUserEmail(emailObj.get("address").toString()); 
							 }
							 else if(k==1){
								 referContacts.setContactUserEmail2(emailObj.get("address").toString()); 
							 }
							  
						 }
						 friend.put("email",emails);
						/* Boolean flag =  referContactsService.validateGoogleEamil(emailObj.get("address").toString(), loginUser.getId());
						    if(flag.equals(true))
							 continue;*/
						 //NAME
						 if(thisObject.has("gd$name")){
							 JSONObject nameobj = thisObject.getJSONObject("gd$name");
							 if(nameobj.has("gd$givenName")){
								 JSONObject fnameobj = nameobj.getJSONObject("gd$givenName");
								 name.put("first_name", fnameobj.get("$t").toString());
								 referContacts.setFirstName(fnameobj.get("$t").toString());
							 }
							 else{
								 name.put("first_name", "");
								 referContacts.setFirstName("");
							 }
							 if(nameobj.has("gd$familyName")){
								 JSONObject lnameobj = nameobj.getJSONObject("gd$familyName");
								 name.put("last_name",  lnameobj.get("$t").toString());
								 referContacts.setLastName(lnameobj.get("$t").toString());
							 }
							 else{
								 name.put("last_name", "");
								 referContacts.setLastName("");
							 }
						 }
						 else{
							 name.put("first_name", "");
							 name.put("last_name",  "");
							 referContacts.setFirstName("");
							 referContacts.setLastName("");
							
						 }
						 friend.put("name", name);
							 
						 //PICTURE URL
						 String imageurl="";
						 if(thisObject.has("link")){
							 JSONArray linkary = thisObject.getJSONArray("link");
							 JSONObject linkobj = (JSONObject)linkary.get(0);
							 String imgurl = linkobj.get("href").toString().replace("?v=3.0", "/");
							 String imgid="";
							 if(linkobj.has("gd$etag")){
							 	imgid = linkobj.get("gd$etag").toString().replace("\"", "");
							 	imageurl = (imgurl + imgid + "?access_token=" + token).replace(".?", "?");
							    String staticPath =  (String) SourceLeadSessionManager.getSessionAttribute(request, "staticPath");
							    String url = "";
							 	if(StringUtils.isNotEmpty(imageurl) && StringUtils.isNotEmpty(staticPath)) {
							 		String fileName = name.getString("first_name");
								 	String extenstion = ".png";
								 	long time = new Date().getTime();
								 	
									url = staticPath.substring(staticPath.lastIndexOf("/"))+"/usercontacts/"+loginUser.getId()+"/" + fileName + time + extenstion;
									fileName = "/usercontacts/"+loginUser.getId()+"/" + fileName + time + extenstion;
									File folderFile = new File(staticPath, "/usercontacts");
									if (!folderFile.exists())
										folderFile.mkdirs();
									
								    folderFile = new File(staticPath, "/usercontacts/"+loginUser.getId());
									if (!folderFile.exists())
										folderFile.mkdirs();
									
									File file = new File(staticPath, fileName);
								 	FileOutputStream fos = new FileOutputStream(file);
								 	fos.write(fetchRemoteFile(imageurl));
								 	fos.close();
								 	 
							 	}
							 	referContacts.setFileURL(url);
							 	referContacts.setFileContentType("image/png");
							 }
						 }
							 
							 
						 //NOTES
						 if(thisObject.has("gd$notes")){
							 friend.put("notes", thisObject.getJSONObject("gd$notes"));
						 }
						 else{
							 friend.put("notes", "");
						 }
						 
						 //BIRTHDAY
						 if(thisObject.has("gContact$birthday")){
							 JSONObject dobObject = new JSONObject();
							 dobObject = thisObject.getJSONObject("gContact$birthday");
							 if(dobObject.has("when")){
								 String[] dob = dobObject.get("when").toString().split("-");
								 dobObject.put("day", dob[2].toString());
								 dobObject.put("month", dob[1].toString());
								 dobObject.put("year", dob[0].toString());
								 friend.put("birthday", dobObject);
							 }
							 else{
								 dobObject = new JSONObject();
								 dobObject.put("day", "");
								 dobObject.put("month", "");
								 dobObject.put("year", "");
								 friend.put("birthday", dobObject);
							 }
						 }
						 else{
							 JSONObject dobObject = new JSONObject();
							 dobObject.put("day", "");
							 dobObject.put("month", "");
							 dobObject.put("year", "");
							 friend.put("birthday", dobObject);
						 }
							 
						 //PHONE
						 JSONArray phones = new JSONArray();
						 if(thisObject.has("gd$phoneNumber")){
							 JSONArray phoneAry =  thisObject.getJSONArray("gd$phoneNumber");
							 int phoneLen = phoneAry.length();
							 for(int j=0;j<phoneLen;j++){
								 JSONObject phoneobj = phoneAry.getJSONObject(j);
								 phones.put(phoneobj.get("$t").toString());
								 if(j==0){
									 referContacts.setContactUserPhone(phoneobj.get("$t").toString());
								 }
								 else if(j==1){
									 referContacts.setContactUserPhone(phoneobj.get("$t").toString());
								 }
								
							 }
							 
						 }
						 friend.put("phone", phones);
						 
						 //WEBSITE
						 JSONArray websites = new JSONArray();
						 if(thisObject.has("gContact$website")){
							 JSONArray websiteAry =  thisObject.getJSONArray("gContact$website");
							 int websiteLen = websiteAry.length();
							 for(int j=0;j<websiteLen;j++){
								 JSONObject websiteobj = websiteAry.getJSONObject(j);
								 websites.put(websiteobj.get("href").toString());
							 }
						 }
						 friend.put("website", websites);
						 
						 //ADDRESS
						 JSONArray addresses = new JSONArray();
						 if(thisObject.has("gd$structuredPostalAddress")){
							 JSONArray addressAry =  thisObject.getJSONArray("gd$structuredPostalAddress");
							 int addrLen = addressAry.length();
							 for(int j=0;j<addrLen;j++){
								 JSONObject address = new JSONObject();
								 JSONObject addressobj = addressAry.getJSONObject(j);
								 
								 if(addressobj.has("gd$formattedAddress")){
									 JSONObject formattedAddrobj = addressobj.getJSONObject("gd$formattedAddress");
									 address.put("formattedaddress", formattedAddrobj.get("$t").toString());
								 }
								 else{
									 address.put("formattedaddress", "");
								 }
								 if(addressobj.has("gd$city")){
									 JSONObject formattedAddrobj = addressobj.getJSONObject("gd$city");
									 address.put("city", formattedAddrobj.get("$t").toString());
								 }
								 else{
									 address.put("city", "");
								 }
								 if(addressobj.has("gd$region")){
									 JSONObject formattedAddrobj = addressobj.getJSONObject("gd$region");
									 address.put("state", formattedAddrobj.get("$t").toString());
								 }
								 else{
									 address.put("state", "");
								 }
								 if(addressobj.has("gd$postcode")){
									 JSONObject formattedAddrobj = addressobj.getJSONObject("gd$postcode");
									 address.put("zip", formattedAddrobj.get("$t").toString());
								 }
								 else{
									 address.put("zip", "");
								 }
								 if(addressobj.has("gd$street")){
									 JSONObject formattedAddrobj = addressobj.getJSONObject("gd$street");
									 address.put("street", formattedAddrobj.get("$t").toString());
								 }
								 else{
									 address.put("street", "");
								 }
								 addresses.put(address);
							 }
						 }
						 if(emails.length()>0){
							 referContacts.setJsonOutPut(friend.toString());
							 refContactsList.add(referContacts);
							 
							 friend.put("imageurl", imageurl);
							 friend.put("address", addresses);
							 friends.put(friend);
						 }
						 
					 }
				 }
			 }
			SocialInviterUtil.storeReferContacts(refContactsList,request);
			data.put("addressbook", friends);
			data.put("service", "google");
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
		}
		catch(JSONException j){
			System.out.println(j.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return jsonresponse;
	}
	
	   
	private byte[] fetchRemoteFile(String location) throws Exception {
		  URL url = new URL(location);
		  InputStream is = null;
		  byte[] bytes = null;
		  try {
		    is = url.openStream ();
		    bytes = IOUtils.toByteArray(is);
		  } catch (IOException e) {
		    //handle errors
		  }
		  finally {
		    if (is != null) is.close();
		  }
		  return bytes;
		}
	
	
	/*
	 * GET FRIENDS
	 */
	protected JSONObject getFriends(String consumerkey, String consumersecret,String token) {
		JSONObject jsonresponse = new JSONObject();
		try{
			JSONObject data = new JSONObject();
			JSONObject msg = new JSONObject();
			JSONArray friends = new JSONArray();
			JSONObject feed = new JSONObject();
			String requrl = "https://www.google.com/m8/feeds/contacts/default/full?v=3.0&oauth_token=" + token + "&max-results=99999&alt=json";

			//MAKE API CALL
			String response = pr.executeGet(requrl);
			
	        //PARSE
	        JSONObject friendsObj = new JSONObject(response.toString());
	        
	        feed = friendsObj.getJSONObject("feed");
	        JSONArray friendsary = feed.getJSONArray("entry");
	        int fcount = friendsary.length();
			 for(int n=0;n<fcount;n++){
				 JSONObject friend = new JSONObject();
				 JSONObject name = new JSONObject();
				 JSONObject thisObject = new JSONObject();
				 thisObject = friendsary.getJSONObject(n);
				 if(thisObject.has("gd$email")){
					 JSONArray emailAry = thisObject.getJSONArray("gd$email");
					 if(emailAry.length()>0){
						 JSONObject emailObj = (JSONObject)emailAry.get(0);
						 String email = emailObj.get("address").toString();
						 if(email.contains("@")){
							 //SCREEN NAME
							 friend.put("screenname", emailObj.get("address").toString());
							 
							 //NAME
							 if(thisObject.has("gd$name")){
								 JSONObject nameobj = thisObject.getJSONObject("gd$name");
								 if(nameobj.has("gd$givenName")){
									 JSONObject fnameobj = nameobj.getJSONObject("gd$givenName");
									 name.put("first_name", fnameobj.get("$t").toString());
								 }
								 else{
									 name.put("first_name", "");
								 }
								 if(nameobj.has("gd$familyName")){
									 JSONObject lnameobj = nameobj.getJSONObject("gd$familyName");
									 name.put("last_name",  lnameobj.get("$t").toString());
								 }
								 else{
									 name.put("last_name", "");
								 }
								 friend.put("name", name);
								 
								 //PICTURE URL
								 String imageurl="";
								 if(thisObject.has("link")){
									 JSONArray linkary = thisObject.getJSONArray("link");
									 JSONObject linkobj = (JSONObject)linkary.get(0);
									 String imgurl = linkobj.get("href").toString().replace("?v=3.0", "/");
									 String imgid="";
									 if(linkobj.has("gd$etag")){
									 	imgid = linkobj.get("gd$etag").toString().replace("\"", "");
									 	imageurl = (imgurl + imgid + "?access_token=" + token).replace(".?", "?");
									 }
									
									 
								 }
								 friend.put("imageurl", imageurl);
								 
								 //ID
								 friend.put("id", email);
								 
								 //LOCATION
								 String location="";
								 if(thisObject.has("gd$structuredPostalAddress")){
									 JSONArray addressAry =  thisObject.getJSONArray("gd$structuredPostalAddress");
									 JSONObject addressobj = addressAry.getJSONObject(0);
									 JSONObject formattedAddrobj = addressobj.getJSONObject("gd$formattedAddress");
									 location = formattedAddrobj.get("$t").toString();
								 }
								 friend.put("location", location);
								 friends.put(friend);
							 }
							 else{
								 name.put("first_name", "");
								 name.put("last_name",  "");
								
							 }
							 
						 }
					 }
				 }
			 }
			data.put("friends", friends);
			data.put("service", "google");
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
	 * GET PROFILE
	 */
	protected JSONObject getProfile(String token,String service) {
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject details = new JSONObject();
		try{
			String requrl = "https://www.googleapis.com/plus/v1/people/me?v=3.0&oauth_token=" + token + "&alt=json";
			
			//MAKE API CALL
			String response = pr.executeGet(requrl);
	        
			//PARSE
			JSONObject detailsObj = new JSONObject(response.toString());
			if(detailsObj.has("aboutMe")){
				details.put("aboutMe", detailsObj.get("aboutMe"));
			}
			
			details.put("id", detailsObj.get("id"));
			details.put("displayName", detailsObj.get("displayName"));
			JSONObject name = detailsObj.getJSONObject("name");
			details.put("firstName", name.get("givenName"));
			details.put("lastName", name.get("familyName"));
			JSONArray emails = detailsObj.getJSONArray("emails");
			JSONObject email = (JSONObject)(emails.get(0));
			details.put("email", email.get("value"));
			details.put("profileUrl", detailsObj.get("url"));
			JSONObject image = detailsObj.getJSONObject("image");
			details.put("photo", image.get("url"));
			if(detailsObj.has("placesLived")){
				JSONArray locations = detailsObj.getJSONArray("placesLived");
				JSONObject location = (JSONObject)(locations.get(0));
				details.put("location", location.get("value"));
			}
			if(detailsObj.has("gender")){
				details.put("gender", detailsObj.get("gender"));
			}
			if(detailsObj.has("birthday")){
				details.put("dob", detailsObj.get("birthday"));
			}
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
	 * SEND MESSAGE
	 */
	protected JSONObject sendMessage(String friendsids, String message, String subject, String consumerkey, String consumersecret,String access_token, String access_token_secret){
		
		JSONObject jsonresponse = new JSONObject();
		if(friendsids.indexOf(",")!=-1){
			String[] friends = friendsids.split(",");
			int len = friends.length;
			for(int i=0;i<len;i++){
				jsonresponse = fireMessageToReceipient(friends[i], message, subject, consumerkey, consumersecret, access_token, access_token_secret);
			}
		}
		else{
			jsonresponse = fireMessageToReceipient(friendsids, message, subject, consumerkey, consumersecret, access_token, access_token_secret);
		}
		return jsonresponse;
	}
	
	/*
	 * SEND MESSAGE INTERNAL FUNCTION
	 */
	private JSONObject fireMessageToReceipient(String friendsids, String message, String subject, String consumerkey, String consumersecret,String access_token, String access_token_secret)
	{
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
		try{
		String msgs = "From: 'me'\r\nTo:  " + friendsids + "\r\n" + "Subject: " + subject + "\r\n" + "\r\n" + message;
		String rawmsg = new String(encoder.encode(msgs.getBytes()));
		String reqUrl = "https://www.googleapis.com/gmail/v1/users/me/messages/send?key=" + consumerkey + "&alt=json";
		String resp = pr.excutePost("POST",reqUrl,"{ \"raw\":\"" + rawmsg + "\"}",access_token);
			if(resp!="" && resp!="error"){
				 msg.put("message", "");
				 msg.put("type", "success");
				 data.put("status", "success");
				 data.put("service", "google");
				 jsonresponse.put("data", data);
				 jsonresponse.put("responseStatus", msg);
			}
			else{
				msg.put("message", "Error occured when posting.");
				msg.put("type", "error");
				data.put("status", "error");
				data.put("service", "google");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			}
		}
		catch(Exception e){
			
		}
		
		return jsonresponse;
	}	
}