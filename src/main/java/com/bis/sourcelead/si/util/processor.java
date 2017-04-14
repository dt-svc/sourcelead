package com.bis.sourcelead.si.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
public class processor {
	
	
	
	
	public String encode(String value) 
	{
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } 
            else if (focus == '+') {
                buf.append("%20");
            } 
            else if (focus == '%' && (i + 1) < encoded.length()  && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } 
            else {
                buf.append(focus);
            }
        }
        return buf.toString();
    }
	
	public String excutePost(String method, String targetURL, String urlParameters,String token) {
		
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
	
	private static final void setDelegateHttpConnection(final HttpURLConnection httpURLConnection, final String method,String token) {
		try {
			httpURLConnection.setRequestMethod(method);
		} catch (final ProtocolException pe) {
			Class<?> connectionClass = httpURLConnection.getClass();
			Field delegateField = null;
			try {
				delegateField = connectionClass.getDeclaredField("delegate");
				delegateField.setAccessible(true);
				HttpURLConnection delegateConnection = (HttpURLConnection) delegateField.get(httpURLConnection);
				setDelegateHttpConnection(delegateConnection, method,token);
			} catch (NoSuchFieldException e) {
				// Ignore for now, keep going
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			try {
				Field methodField;
				while (connectionClass != null) {
					try {
						methodField = connectionClass.getDeclaredField("method");
					} catch (NoSuchFieldException e) {
						connectionClass = connectionClass.getSuperclass();
						continue;
					}
					methodField.setAccessible(true);
					httpURLConnection.setRequestProperty("Authorization","Bearer "+token);
	                httpURLConnection.setRequestProperty("Content-Type", "text/xml");
	                httpURLConnection.setRequestProperty("Depth", "1");
					methodField.set(httpURLConnection, method);
					break;
				}
			} catch (final Exception e) {
				
			}
		} catch (@SuppressWarnings("hiding") IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	@SuppressWarnings("null")
	public String makeRequestCarddav(String method, String targetURL, String postData,String token) throws IOException {
		String response ="";
		HttpURLConnection httpURLConnection = null;  
		try {
			URL url = new URL(targetURL);
			httpURLConnection = (HttpURLConnection)url.openConnection();
			setDelegateHttpConnection(httpURLConnection,method,token);
        } 
		catch (final ProtocolException pe) {
            try {
                final Class<?> httpURLConnectionClass = httpURLConnection.getClass();
                final Class<?> parentClass = httpURLConnectionClass.getSuperclass();
                final Field methodField;
                // If the implementation class is an HTTPS URL Connection, we
                // need to go up one level higher in the heirarchy to modify the
                // 'method' field.
                if (parentClass == HttpsURLConnection.class) {
                    methodField = parentClass.getSuperclass().getDeclaredField("method");
                } else {
                    //methodField = parentClass.getDeclaredField("method");
                    methodField = parentClass.getSuperclass().getSuperclass().getDeclaredField("method");
                }
                httpURLConnection.setRequestProperty("Authorization","Bearer "+token);
                httpURLConnection.setRequestProperty("Content-Type", "text/xml");
                httpURLConnection.setRequestProperty("Depth", "1");
                methodField.setAccessible(true);
                methodField.set(httpURLConnection, method);
            } 
            catch (final Exception e) {
            	response = "error";
            }
	     } 
		catch (MalformedURLException e) {
	    	 response = "error";
		} 
		catch (IOException e) {
			response = "error";
		}
		httpURLConnection.setRequestProperty("Authorization","Bearer "+token);
        httpURLConnection.setRequestProperty("Content-Type", "text/xml");
        httpURLConnection.setRequestProperty("Depth", "1");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoOutput(true);
	    //Send request
	    DataOutputStream wr;
		try {
			wr = new DataOutputStream (httpURLConnection.getOutputStream());
			wr.writeBytes(postData);
		    wr.close();
		    //Get Response  
		    InputStream is = httpURLConnection.getInputStream();
		    java.util.Scanner s = extracted(is).useDelimiter("\\A");
		    response = s.hasNext() ? s.next() : "";
		} catch (IOException e) {
			response = "error";
		}
		
		return response;
	}
	

	public String executeGet(String requrl) throws IOException{
		URL urlObj = new URL(requrl);
        URLConnection yc = urlObj.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+

        while ((inputLine = in.readLine()) != null){
        	response.append(inputLine);
        }
        in.close();
		return response.toString();
	}

	public String executeGetJSON(String requrl) throws IOException{
		URL urlObj = new URL(requrl);
        URLConnection yc = urlObj.openConnection();
        yc.setRequestProperty("Content-Type", "application/json");
		yc.setRequestProperty("Accept", "application/json");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+

        while ((inputLine = in.readLine()) != null){
        	response.append(inputLine);
        }
        in.close();
        return response.toString();
	}
	
	public String executeGetMailChimp(String requrl,String token,String type) throws IOException{
		URL urlObj = new URL(requrl);
        URLConnection yc = urlObj.openConnection();
        yc.addRequestProperty("Authorization", "OAuth "+token);
        if(type.equals("meta")){
	        yc.addRequestProperty("Accept", "application/json");
	        yc.addRequestProperty("User-Agent", "oauth2-draft-v10");
        }
        yc.addRequestProperty("Method", "get");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+

        while ((inputLine = in.readLine()) != null){
        	response.append(inputLine);
        }

        in.close();
        return response.toString();
	}
	
	public String excuteHTTPCall(String method, String targetURL, String urlParameters,String headervalue) {
		  HttpURLConnection connection = null;  
		  try {
		    //Create connection
			  if(method.equals("GET")){
				  targetURL = targetURL +"?"+ urlParameters;
			  }
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    
		    
		    if(method.equals("GETPOSTJSON")){
		    	connection.setRequestMethod("GET");
		    	if(headervalue!=""){
		    		connection.setRequestProperty("Authorization",headervalue);
		    		connection.setRequestProperty("Content-Type", "application/json");
		    		connection.setRequestProperty("Accept", "application/json");
		    	}
		    }
		    else if(method.equals("GETPOST")){
		    	connection.setRequestMethod("GET");
		    	if(headervalue!=""){
		    		connection.setRequestProperty("Authorization",headervalue);
		    	}
		    }
		    else{
		    	connection.setRequestMethod(method);
		    }
		    connection.setDoOutput(true);
		    //Send request
		    if(method.equals("POST")){
			    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			    wr.writeBytes(urlParameters);
			    wr.close();
		    }
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    java.util.Scanner s = extracted(is).useDelimiter("\\A");
		    String response = s.hasNext() ? s.next() : "";
		    return response;
		    
		    
		  } catch (Exception e) {
		    return "error";
		  } finally {
		    if(connection != null) {
		      connection.disconnect(); 
		    }
		  }
	}
	
	public String excutePostXing(String method, String targetURL, String urlParameters,String token) {
		  HttpURLConnection connection = null;  
		  try {
			  
			  
			  
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
		   // connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    //Send request
		    if(method.equals("POST")){
			    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			    wr.writeBytes(urlParameters);
			    wr.close();
		    }
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
	
	public String excutePostLinkedin(String method, String targetURL, String urlParameters,String token) {
		
		  HttpURLConnection connection = null;  
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod(method);
		    if(method.equals("POST")){
		    	if(token!=""){
		    		connection.setRequestProperty("x-li-format","json");
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
	
	public String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException 
	{
	    SecretKey secretKey = null;

	    byte[] keyBytes = keyString.getBytes();
	    secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(secretKey);

	    byte[] text = baseString.getBytes();
	    java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
	    return new String(encoder.encode(mac.doFinal(text))).trim();
	}
	
	
	
	private static Scanner extracted(InputStream is) {
		return new java.util.Scanner(is);
	}
	
	public String GenerateNonce()
    {
		Random random = new Random();
		return Integer.toString(random.nextInt(123400));
    }

	public String MD5(String md5) {
	   try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        return sb.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
	    }
	    return null;
	}
	
	public JSONObject serviceNotAvailable(){
		JSONObject jsonresponse = new JSONObject();
		try{
    		JSONObject data = new JSONObject();
    		JSONObject msg = new JSONObject();
    		msg.put("message", "This service is not available");
			msg.put("type", "error");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
		}
		catch(Exception e){
		
		}
		return jsonresponse;
	}
	
	
}
