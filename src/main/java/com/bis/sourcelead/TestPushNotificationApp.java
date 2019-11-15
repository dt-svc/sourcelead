/*package com.bis.sourcelead;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONException;

import javapns.*;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;
public class TestPushNotificationApp {
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        File pro_File = org.springframework.util.ResourceUtils.getFile("classpath:Sourcelead_Development.p12");
        try {
            PushNotificationPayload payload = PushNotificationPayload.complex();
            payload.addAlert("Hai ra kanna .....");
            payload.addSound("default");
            payload.addCustomDictionary("id", "1");
            System.out.println(payload.toString());
            List < PushedNotification > NOTIFICATIONS = Push.payload(payload, pro_File.getPath(), "Sourcelead@79!", false, "1a3420d9a48eb13fe5449b66084322f610a086dc4700526e5b112682167f7f11");
            for (PushedNotification NOTIFICATION: NOTIFICATIONS) {
                if (NOTIFICATION.isSuccessful()) {
                     APPLE ACCEPTED THE NOTIFICATION AND SHOULD DELIVER IT 
                    System.out.println("PUSH NOTIFICATION SENT SUCCESSFULLY TO: " +
                        NOTIFICATION.getDevice().getToken());
                     STILL NEED TO QUERY THE FEEDBACK SERVICE REGULARLY 
                } else {
                    String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
                     ADD CODE HERE TO REMOVE INVALIDTOKEN FROM YOUR DATABASE 
                     FIND OUT MORE ABOUT WHAT THE PROBLEM WAS 
                    Exception THEPROBLEM = NOTIFICATION.getException();
                    THEPROBLEM.printStackTrace();
                     IF THE PROBLEM WAS AN ERROR-RESPONSE PACKET RETURNED BY APPLE, GET IT 
                    ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
                    if (THEERRORRESPONSE != null) {
                        System.out.println(THEERRORRESPONSE.getMessage());
                    }
                }
            }
        } catch (CommunicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeystoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}*/