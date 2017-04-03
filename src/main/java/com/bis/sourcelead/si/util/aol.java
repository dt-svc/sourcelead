package com.bis.sourcelead.si.util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

class aol {
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
			String authurl = "https://api.screenname.aol.com/auth/authorize?response_type=code&client_id=" + consumerkey + "&scope=profile email addressbook&redirect_uri=" + callback;
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
		try{
			String postdata = "grant_type=authorization_code&client_id=" + consumerkey + "&client_secret=" + consumersecret;
            postdata += "&code=" + code + "&redirect_uri=" + callback + "&oauth_version=2.0";
	        
	        //MAKE API CALL
	        String response = pr.excutePost("POST", "https://api.screenname.aol.com/auth/access_token", postdata,"");
			
	        //PARSE DATA
			JSONObject resp = new JSONObject(response);
	        data.put("token", resp.get("access_token"));
			data.put("tokensecret", "");
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
	 * GET CONTACTS
	 */
	protected JSONObject getContacts(String token,String service) {
		//DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray addressbook = new JSONArray();
		//XML.toJSONObject(string)\
		
		try{
			String requrl = "https://carddav.aol.com/home/tellallfriend@aol.com/Contacts/";
			String postData = "<A:propfind xmlns:A=\"DAV:\"></A:propfind>";
			//MAKE API CALL
			String response = pr.makeRequestCarddav("PROPFIND",requrl,postData, token);
	        if(response=="error"){
	        	//Sometimes the AOL server could be slow and returning error, so its worth trying out one more time
	        	response = pr.makeRequestCarddav("PROPFIND",requrl,postData, token);
	        }
			JSONObject xmlJSONObj = XML.toJSONObject(response);
			JSONObject multiStatObj = xmlJSONObj.getJSONObject("D:multistatus");
			JSONArray dRespObj = multiStatObj.getJSONArray("D:response"); 
			int count = dRespObj.length();
			
			postData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			postData = "<B:addressbook-multiget xmlns:B=\"urn:ietf:params:xml:ns:carddav\">";
			postData += "<A:prop xmlns:A=\"DAV:\">";
			postData += "<A:getetag />";
			postData += "<B:address-data />";
			postData += "</A:prop>";
            for (int i = 0; i < count; i++)
            {
            	JSONObject contactRefObj = dRespObj.getJSONObject(i);
            	String href = contactRefObj.get("D:href").toString();
            	if(href.contains(".vcf")){
            		postData += "<A:href xmlns:A=\"DAV:\">" + href + "</A:href>";
            	}
            }
            postData += "</B:addressbook-multiget>";
            response = pr.makeRequestCarddav("REPORT","https://carddav.aol.com/home/tellallfriend@aol.com/Contacts/",postData, token);
            JSONObject xmlJSONContactsObj = XML.toJSONObject(response);
            

			//PARSE DATA
			
            JSONObject multiStatContactsObj = xmlJSONContactsObj.getJSONObject("D:multistatus");
			JSONArray ContactsObj = multiStatContactsObj.getJSONArray("D:response");
            int len = ContactsObj.length();
            for(int i=0;i<len;i++){
            	JSONObject contactRefObj = ContactsObj.getJSONObject(i);
            	JSONObject PropStatObj = contactRefObj.getJSONObject("D:propstat");
            	JSONObject DPropObj = PropStatObj.getJSONObject("D:prop");
            	JSONObject CAddressObj = DPropObj.getJSONObject("C:address-data");
            	String dataContent = CAddressObj.get("content").toString();
            	String[] rows = dataContent.split("\r\n");
            	
            	JSONObject name = new JSONObject();
				JSONObject birthday = new JSONObject();
				JSONArray email = new JSONArray();
				JSONArray phone = new JSONArray();
				JSONArray address = new JSONArray();
				JSONArray website = new JSONArray();
				String imageurl = "";
				String notes = "";
				
            	int rowsLen = rows.length;
                for (int c = 0; c < rowsLen; c++)
                {
                	
                    if (rows[c].contains("FN:"))
                    {
                        String[] keyVal = rows[c].toString().split(":");
                        if (keyVal[0].toString().equals("FN"))
                        {
                            String nameVal = keyVal[1].toString();
                            if (nameVal.contains(","))
                            {
                            	String[] splited = nameVal.split("\\s+");
    							name.put("first_name", splited[0].replace("\\",""));
    							name.put("last_name", splited[1]);
                            }
                            else
                            {
                            	name.put("first_name", nameVal);
                            	name.put("last_name", "");
                            }
                        }
                    }
                    else if (rows[c].contains("EMAIL;"))
                    {
                        String[] keyVal = rows[c].toString().split(":");
                        email.put(keyVal[1].toString());
                    }
                    else if (rows[c].contains("TEL;"))
                    {
                        String[] keyVal = rows[c].toString().split(":");
                        phone.put(keyVal[1].toString());
                    }
                    else if (rows[c].contains("BDAY;"))
                    {
                    	String[] keyVal = rows[c].toString().split(":");
                    	String[] bday = keyVal[1].toString().split("-");
                    	birthday.put("day", bday[2].toString());
                    	birthday.put("month", bday[1].toString());
                    	birthday.put("year", bday[0].toString());
                    }
                    else if (rows[c].contains("ADR;"))
                    {
                    	String[] keyVal = rows[c].toString().split(":");
                    	JSONObject addressObj = new JSONObject();
						addressObj.put("country", "");
						addressObj.put("city", "");
						addressObj.put("state", "");
						addressObj.put("formattedaddress", keyVal[1].toString().replace(";;", ""));
						address.put(addressObj);
                    }
                }
                if(email.length()>0){
					JSONObject aBook = new JSONObject();
					aBook.put("address", address);
					aBook.put("birthday", birthday);
					aBook.put("email", email);
					aBook.put("imageurl", imageurl);
					aBook.put("name", name);
					aBook.put("notes", notes);
					aBook.put("phone", phone);
					aBook.put("website", website);
					addressbook.put(aBook);
				}
            }
            
			data.put("addressbook", addressbook);
			data.put("service", "aol");
			jsonresponse.put("data", data);
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("responseStatus", msg);			
		}
		catch(Exception e){
			try{
				msg.put("message", "Error fetching contacts");
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
