package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.lf5.util.ResourceUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.MobilePushNotificationDTO;
import com.bis.sourcelead.entity.MobileDeviceTokenInformaion;
import com.bis.sourcelead.service.MobilePushNotificationService;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

@Component
public class PushNotificationUtill {

	@Autowired
	MobilePushNotificationService mobilePushNotificationService;
	
	@Value("${env}")
	private String environment;
	
	 
	public void sendIOSPushNotification(MobilePushNotificationDTO mobilePushNotificationDTO) throws Exception{
        BasicConfigurator.configure();
        //sourcelead qa related IOS .p12 certificate 
        File qa_File = org.springframework.util.ResourceUtils.getFile("classpath:Qa_Sourcelead.p12");
       
        //sourcelead production related IOS .p12 certificate 
        File pro_File = org.springframework.util.ResourceUtils.getFile("classpath:Sourcelead_Production.p12");
		try {
	            PushNotificationPayload payload = PushNotificationPayload.complex();
	            payload.addAlert(mobilePushNotificationDTO.getNotificationDescritpion().replaceAll("&nbsp;", " "));
	            payload.addSound("default");
	            payload.addCustomDictionary("id", "1");
        	    String filePath = null;
            	/*if (environment.equalsIgnoreCase("prod")) {
            		filePath = pro_File.getPath();
            	}else {*/
            		filePath = pro_File.getPath();
//            	}
            	
              //payload means pushNotification meassage body
              //filePah means .12 physical location 
              //false means Boolean value
              //device token value dynamically
        	  List < PushedNotification > NOTIFICATIONS = Push.payload(payload, filePath, "Sourcelead@79!", false, mobilePushNotificationDTO.getDeviceToken());
              for (PushedNotification NOTIFICATION: NOTIFICATIONS) {
                  if (NOTIFICATION.isSuccessful()) {
                      /* APPLE ACCEPTED THE NOTIFICATION AND SHOULD DELIVER IT */
                      System.out.println("PUSH NOTIFICATION SENT SUCCESSFULLY TO: " +
                          NOTIFICATION.getDevice().getToken());
                      System.out.println("success >>>>"+mobilePushNotificationDTO.getDeviceToken());
                      /* STILL NEED TO QUERY THE FEEDBACK SERVICE REGULARLY */
                  } else {
                	  System.out.println("failed >>>"+mobilePushNotificationDTO.getDeviceToken());
                      String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
                      /* ADD CODE HERE TO REMOVE INVALIDTOKEN FROM YOUR DATABASE */
                      /* FIND OUT MORE ABOUT WHAT THE PROBLEM WAS */
                      Exception THEPROBLEM = NOTIFICATION.getException();
                      THEPROBLEM.printStackTrace();
                      /* IF THE PROBLEM WAS AN ERROR-RESPONSE PACKET RETURNED BY APPLE, GET IT */
                      ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
                      if (THEERRORRESPONSE != null) {
                          System.out.println(THEERRORRESPONSE.getMessage());
                      }
                  }
              }
          
        } catch (CommunicationException e) {
            e.printStackTrace();
        } catch (KeystoreException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
	}
	
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	@SuppressWarnings("unchecked")
	public void sendAndriodPushNotification(MobilePushNotificationDTO mobilePushNotificationDTO) throws Exception{
		String authKey = "";
		if("IOS".equalsIgnoreCase(mobilePushNotificationDTO.getDeviceType())) {
			 // IOS FCM AUTH key
			authKey = "AAAAgygCNJE:APA91bEc_HRI7Q16LGmYySQuEncJoG0QR16WOhs5S1uxS6_JbPkMgS0RP5fqMvn1eEvB4JlhtSsvZIHgnF9V5IaS-y6KU-ACOIyUxYwd728K1bF1DoemEE2iJwFaRkRdqw9Zog8EKd7S";
		}else if("Android".equalsIgnoreCase(mobilePushNotificationDTO.getDeviceType())) {
			 // Android  FCM AUTH key
			authKey = "AAAAcCJ8Zqo:APA91bGcbtil0EWobEobneqgJfaQxt15JAtcnYQ17hZNDqMyHmSN7myt11P0aa4iBqG0O8lbleV2fnS2QCd55wPpCI4h24tYi3KQUgixjRz6-p4tskwezM_f6V4aK2PUVX1Y7Qi81Y07";
		}
        
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
       
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", mobilePushNotificationDTO.getDeviceToken().trim()); //device token information
        JSONObject info = new JSONObject();
        info.put("title", "Leave Approval"); // Notification title
        info.put("body", mobilePushNotificationDTO.getNotificationDescritpion().replaceAll("&nbsp;", " ")); // Notification body
        info.put("sound", "default"); // notification sound
        
        if("IOS".equalsIgnoreCase(mobilePushNotificationDTO.getDeviceType())) {
        	json.put("data", info); // body information
        }else if("Android".equalsIgnoreCase(mobilePushNotificationDTO.getDeviceType())) {
        	json.put("notification", info); // body information
        }
        
        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
       }

    }
	
}
