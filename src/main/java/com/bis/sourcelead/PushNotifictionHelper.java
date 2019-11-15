package com.bis.sourcelead;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class PushNotifictionHelper {
    public final static String AUTH_KEY_FCM = "AAAAgygCNJE:APA91bEc_HRI7Q16LGmYySQuEncJoG0QR16WOhs5S1uxS6_JbPkMgS0RP5fqMvn1eEvB4JlhtSsvZIHgnF9V5IaS-y6KU-ACOIyUxYwd728K1bF1DoemEE2iJwFaRkRdqw9Zog8EKd7S";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static String sendPushNotification(String deviceToken)
            throws IOException, JSONException {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
       
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", deviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title", "Leave Approval"); // Notification title
        info.put("body", "Hai Laxman Leave as Approved zxv"); // Notification
        info.put("sound", "default"); 
        
        // body
        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "OK";
        } catch (Exception e) {
            e.printStackTrace();
            result = "BAD";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;

    }

    public static void main(String [] args){
        try {
            PushNotifictionHelper.sendPushNotification("e_aLXR3G5_I:APA91bGcwLH785rxFWLcf21NNhM-e_IllY8zPJ5-aO9ue2zQi1hxmeUsDIgNntU9VkEQVBrEEkVbx5M89QiaPkOxLbKc76y6rXC3rDCEUvQMlYPFuTA0FkaoEl_mIn2sMnUrvenXagYH");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}