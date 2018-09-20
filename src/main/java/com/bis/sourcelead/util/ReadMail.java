package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class ReadMail {
	
	//public static Reader rd = new Reader();
	
	public static List<String> readMail(String host, String storeType, String user, String password) {
		 List<String> folders=new ArrayList<>();
		try {
		    
		      //create properties field
		      Properties properties = new Properties();

		      properties.put("mail.imap.host", host);
		      properties.put("mail.imap.port", "993");
		      properties.put("mail.imap.starttls.enable", "true");
		      Session emailSession = Session.getInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("imaps");

		      store.connect(host, user, password);
		      /*javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
		      for (javax.mail.Folder folder : folders) {
		          if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
		              System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
		          }*/
		      Folder[] f = store.getDefaultFolder().list("*");
		     
		      for(Folder fd:f){
		    	  folders.add(fd.getName());
		      }
		         
		     
	      //close the store and folder objects
	      store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      }catch (AuthenticationFailedException e) {
		         e.printStackTrace();
		         //return false;
		      }catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return folders;
	}
	
	
}
