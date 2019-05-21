package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.MobilePushNotificationDTO;
import com.bis.sourcelead.service.MobilePushNotificationService;

@Component
public class PushNotificationUtill {

	@Autowired
	MobilePushNotificationService mobilePushNotificationService;
	 
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
        info.put("title", mobilePushNotificationDTO.getNotificationTitle()); // Notification title
        info.put("body", mobilePushNotificationDTO.getNotificationDescritpion().replaceAll("&nbsp;", " ")); // Notification body
        info.put("sound", "default"); // notification sound
        
        if("IOS".equalsIgnoreCase(mobilePushNotificationDTO.getDeviceType())) {
        	json.put("notification", info); // body information
        }else if("Android".equalsIgnoreCase(mobilePushNotificationDTO.getDeviceType())) {
        	json.put("data", info); // body information
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
