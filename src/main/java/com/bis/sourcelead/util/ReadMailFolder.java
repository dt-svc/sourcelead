package com.bis.sourcelead.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;

import com.bis.sourcelead.dto.ReadMailDTO;

public class ReadMailFolder {
	
	//public static Reader rd = new Reader();
	
	public static List<ReadMailDTO> readMail(String host, String storeType, String user, String password,String folderName) {
		 List<ReadMailDTO> readMailDTOList=new ArrayList<>();
		 String saveDirectory = "c:/test/Resumes/";
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
		      Folder emailFolder=null;
		       if (folderName.equalsIgnoreCase("All Mail") || folderName.equalsIgnoreCase("Drafts") || folderName.equalsIgnoreCase("Important") || folderName.equalsIgnoreCase("Sent Mail")
		    		   ||folderName.equalsIgnoreCase("Spam") || folderName.equalsIgnoreCase("Starred") || folderName.equalsIgnoreCase("Trash")) {  
		       
		       folderName="[Gmail]/"+folderName;
	    	   emailFolder = store.getFolder(folderName);
		      
		       }else{
		    	   emailFolder = store.getFolder(folderName);
		    	  
		       }
		       UIDFolder uf = (UIDFolder)emailFolder;
		       emailFolder.open(Folder.READ_WRITE);
		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		     
		      for (int i = 0, n = messages.length; i < n; i++) {
		    	  ReadMailDTO readMailDTO=new ReadMailDTO();
		    	  String bodyContent = "";
		         Message message = messages[i];
		         
		         String content=message.getDescription();
		            Object body=message.getContent();
		            if(body instanceof String){
		            	bodyContent=(String)body;
		            }
		         
		         message.getAllHeaders();
		         Long messageId = uf.getUID(messages[i]);
		         readMailDTO.setFrom(messages[i].getFrom()[0].toString());
		         readMailDTO.setSentDate( messages[i].getSentDate().toString());
		         readMailDTO.setSubject(messages[i].getSubject());
		         String contentType = message.getContentType();
		        // store attachment <file name, separated by comma
		        String attachFiles = "";
		        List<String> filesList=new ArrayList<>();
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
							//String fullfilepath = saveDirectory + fileName;
							filesList.add(fileName);
							//part.saveFile(fullfilepath);							
							
						}
					}
					
		         } 
		        readMailDTO.setBody(bodyContent);
		        readMailDTO.setFilesList(filesList);
		        readMailDTO.setMessageId(messageId);
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
		return readMailDTOList;
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
	        result=html;
	    } else if (bodyPart.getContent() instanceof MimeMultipart){
	        result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	    }
	    return result;
	}
	
	
}
