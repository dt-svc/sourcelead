package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bis.sourcelead.dto.ReadMailDTO;

public class ReadMail {
	
	//public static Reader rd = new Reader();
	
	public static List<String> readMail(String host, String storeType, String user, String password) {
		//JSONArray jasonArray = new JSONArray();
		 List<ReadMailDTO> readMailDTOList=new ArrayList<>();
		 List<String> folders=new ArrayList<>();
		String saveDirectory = "c:/resumes/";
		String saveTo = "C:/parsedFiles/";
		try {
		    
		      //create properties field
		      Properties properties = new Properties();

		      properties.put("mail.imap.host", host);
		      properties.put("mail.imap.port", "993");
		      properties.put("mail.imap.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
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
		          //System.out.println(">> "+fd.getName());
		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("Inbox");
		      emailFolder.open(Folder.READ_WRITE);
		      
		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      
		     
		      for (int i = 0, n = messages.length; i < n; i++) {
		    	  ReadMailDTO readMailDTO=new ReadMailDTO();
		    	  //JSONObject mailObj = new JSONObject();
		    	  String bodyContent = "";
		         Message message = messages[i];
		         
		         //mailObj.put("from", messages[i].getFrom()[0].toString());
		         //mailObj.put("sentDate", messages[i].getSentDate());
		         //mailObj.put("subject", messages[i].getSubject());
		         readMailDTO.setFrom(messages[i].getFrom()[0].toString());
		         readMailDTO.setSentDate( messages[i].getSentDate().toString());
		         readMailDTO.setSubject(messages[i].getSubject());
		         String contentType = message.getContentType();

		        // store attachment file name, separated by comma
		        String attachFiles = "";
		        JSONArray attachments = new JSONArray();
		        if (contentType.contains("multipart")) {
					// content may contain attachments
		        	 
					Multipart multiPart = (Multipart) message.getContent();
					MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			        bodyContent = getTextFromMimeMultipart(mimeMultipart);
					int numberOfParts = multiPart.getCount();
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
							
							// this part is attachment
							
							String fileName = part.getFileName();
							attachFiles += fileName + ", ";
							String fullfilepath = saveDirectory + fileName;
							part.saveFile(fullfilepath);
							
							/*JSONObject attchmt = rd.parseResume(part.getInputStream(), fileName, saveTo, saveDirectory + fileName);
							attachments.put(attchmt);*/
							
						}
					}
					
		         } 
		        //mailObj.put("body", bodyContent);
		       // mailObj.put("attachments", attachments);
		        //jasonArray.put(mailObj);
		        //Delete mail
	           // message.setFlag(Flags.Flag.DELETED, true);
		        readMailDTOList.add(readMailDTO);
	            
         	}
		      

	      //close the store and folder objects
	      emailFolder.close(true);
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
	
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {

	    int count = mimeMultipart.getCount();
	    if (count == 0)
	        throw new MessagingException("Multipart with no body parts not supported.");
	    boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
	    if (multipartAlt){
	        return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
	    }
	    String result = "";
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        result += getTextFromBodyPart(bodyPart);
	    }
	    return result;
	}

	private static String getTextFromBodyPart(BodyPart bodyPart) throws IOException, MessagingException {

	    String result = "";
	    if (bodyPart.isMimeType("text/plain")) {
	        result = (String) bodyPart.getContent();
	    } else if (bodyPart.isMimeType("text/html")) {
	        String html = (String) bodyPart.getContent();
	        result = org.jsoup.Jsoup.parse(html).text();
	    } else if (bodyPart.getContent() instanceof MimeMultipart){
	        result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	    }
	    return result;
	}
	
	/*public JSONArray retriveMails() { 
		JSONArray mails = new JSONArray();
		String host = "imap.gmail.com";// change accordingly
		String mailStoreType = "imaps";
		String username = "anushap.761@gmail.com";// change accordingly
		String password = "Anusha243";// change accordingly
		mails = readMail(host, mailStoreType, username, password);
	    return mails;
	 }*/
	 
	
}
