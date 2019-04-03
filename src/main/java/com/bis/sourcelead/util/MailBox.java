package com.bis.sourcelead.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import com.bis.sourcelead.dto.AttachmentDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.enums.Result;

public class MailBox {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd/yy");
	private static SimpleDateFormat detailedDateFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm a");

	public static ResponseList<String> readMail(String host, String port, String storeType, String user, String password) {
		ResponseList<String> responseList = new ResponseList<>();
		List<String> folderList = new LinkedList<>();
		try {
			// create properties field
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore(storeType);

			store.connect(host, user, password);
			Folder[] f = store.getDefaultFolder().list("*");
			// Folder[] f = store.getDefaultFolder().listSubscribed();
			/*int temp = 1;*/
			for (Folder folder : f) {
				/*if (folder.getName().equalsIgnoreCase("INBOX") || folder.isSubscribed()) {
					Flags seen = new Flags(Flags.Flag.SEEN);
					FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
					folder.open(Folder.READ_ONLY);
					Message[] messages = folder.search(unseenFlagTerm);
					folderList.add(folder.getName() + "<<>>" + folder.getFullName().replaceAll("&", "-and-") + "<<>>" + messages.length);
				}*/
				int unreadCount = 0;
				try {
					unreadCount = folder.getUnreadMessageCount();
				} catch (Exception e) {

				}
				//System.out.println(folder.getName() + "<<>>" + folder.getFullName().replaceAll("&", "-and-") + "<<>>" + unreadCount + " " + (folder.getParent() != null ? folder.getParent().getName() : ""));
				folderList.add(folder.getName() + "<<>>" + folder.getFullName().replaceAll("&", "-and-") + "<<>>" + unreadCount + " " + (folder.getParent() != null ? folder.getParent().getName() : ""));
			}
			// close the store and folder objects
			store.close();
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
			responseList.setData(folderList);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == AuthenticationFailedException.class) {
				responseList.setResponseMessage("Authentication Failed, please try again....\n" + e.getMessage());
			} else
				responseList.setResponseMessage(e.getMessage());
			responseList.setResponse(Result.FAILED.name());
		}
		return responseList;
	}

	public static List<ReadMailDTO> readMailFromFolder(String host, String port, String storeType, String user, String password,
			String folderName, long userId, long orgLocationId, Paginator paginator) {
		List<ReadMailDTO> readMailDTOList = new ArrayList<>();
		try {
			// create properties field
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getInstance(properties);

			// create the store object and connect with the pop server
			Store store = emailSession.getStore(storeType);

			store.connect(host, user, password);
			Folder emailFolder = store.getFolder(folderName);
			emailFolder.open(Folder.READ_WRITE);
			// retrieve the messages from the folder in an array and print it
			int messagesCount = emailFolder.getMessageCount();
			int unreadCount = emailFolder.getUnreadMessageCount();
			int tempCount = messagesCount;
			int remainder = messagesCount % paginator.getPageSize();
			int noOfPages = 1;
			while (tempCount > paginator.getPageSize()) {
				tempCount -= paginator.getPageSize();
				noOfPages++;
			}
			int from = 0, to = 0;
			if (remainder == 0) {
				from = ((paginator.getPageSize() * (noOfPages - (paginator.getPageNo() + 1))) + 1);
				to = (paginator.getPageSize() * (noOfPages - paginator.getPageNo()));
			} else {
				from = ((paginator.getPageSize() * (noOfPages - (paginator.getPageNo() + 1))) - (9 - remainder));
				to = (paginator.getPageSize() * (noOfPages - (paginator.getPageNo() + 1))) + remainder;
			}
			from = from > 1 ? from : 1;
			to = to > messagesCount ? messagesCount : to;
			// Message[] messages = emailFolder.getMessages();
			Message[] messages = emailFolder.getMessages(from, to);

			paginator.setTotalRows(messagesCount);
			paginator.setFilterType(unreadCount);
			FetchProfile fetchProfile = new FetchProfile();
			fetchProfile.add(FetchProfile.Item.ENVELOPE);
			fetchProfile.add("X-mailer");
			emailFolder.fetch(messages, fetchProfile);

			for (Message message : messages) {
				ReadMailDTO readMailDTO = new ReadMailDTO();
				UIDFolder uf = (UIDFolder) emailFolder;
				long messageId = uf.getUID(message);
				boolean seen = message.isSet(Flags.Flag.SEEN);
				readMailDTO.setMessageId(messageId);
				readMailDTO.setSeen(seen);
				
				if (message.getFrom() != null && message.getFrom().length > 0) {
					InternetAddress internetAddress = (InternetAddress) ((Address) message.getFrom()[0]);
					readMailDTO.setFrom(internetAddress.getAddress());
					/*
					 * readMailDTO.setFrom(internetAddress.getPersonal() != null ?
					 * internetAddress.getPersonal() : internetAddress.getAddress());
					 */
				} else {
					readMailDTO.setFrom("");
				}
				readMailDTO.setTo(message.getAllRecipients() != null ? message.getAllRecipients()[0].toString() : "");
				readMailDTO.setSentDate(message.getSentDate() != null ? dateFormat.format(message.getSentDate()) : "");
				readMailDTO.setSubject(message.getSubject());
				readMailDTO.setFolderName(folderName);

				int attachmentsCount = 0;
				String contentType = message.getContentType();
				if (contentType.contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();
					int numberOfParts = multiPart.getCount();
					for (int i = 0; i < numberOfParts; i++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
						if (part.getDisposition() != null) {
							attachmentsCount++;
						}
					}
				}
				readMailDTO.setAttachmentsCount(attachmentsCount);
				readMailDTOList.add(readMailDTO);
			}
			// close the store and folder objects
			emailFolder.close(true);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.reverse(readMailDTOList);
		return readMailDTOList;
	}

	public static Message getMailByUID(String host, String port, String storeType, String user, String password, String folderName,
			long uid) {
		try {
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getInstance(properties);
			Store store = emailSession.getStore(storeType);

			store.connect(host, user, password);
			Folder emailFolder = store.getFolder(folderName);
			emailFolder.open(Folder.READ_WRITE);
			UIDFolder uf = (UIDFolder) emailFolder;
			Message message = uf.getMessageByUID(uid);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ReadMailDTO getMailDTOByUID(String host, String port, String storeType, String user, String password, String folderName,
			long orgId, long orgLocationId, long uid, String staticPath) {
		ReadMailDTO readMailDTO = new ReadMailDTO();
		try {
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getInstance(properties);

			// create the store object and connect with the pop server
			Store store = emailSession.getStore(storeType);

			store.connect(host, user, password);
			Folder emailFolder = store.getFolder(folderName);
			emailFolder.open(Folder.READ_WRITE);
			UIDFolder uf = (UIDFolder) emailFolder;
			Message message = uf.getMessageByUID(uid);
			if (message != null) {
				readMailDTO.setMessageId(uf.getUID(message));
				/*
				 * readMailDTO.setFrom(message.getFrom() != null ?
				 * message.getFrom()[0].toString() : "");
				 */
				if (message.getFrom() != null) {
					InternetAddress internetAddress = (InternetAddress) ((Address) message.getFrom()[0]);
					readMailDTO.setFrom(internetAddress.getAddress());
				} else {
					readMailDTO.setFrom("");
				}
				String cc = "";
				boolean ccFlag = false;
				Address[] ccList = message.getAllRecipients();
				if (ccList != null) {
					for (Address address : ccList) {
						if (ccFlag) {
							/*
							 * cc += (cc.isEmpty() ? "" : "; ") + ((InternetAddress) address).getPersonal()
							 * != null ? ((InternetAddress) address).getPersonal() : ((InternetAddress)
							 * address).getAddress();
							 */
							cc += (cc.isEmpty() ? "" : "; ") + ((InternetAddress) address).getAddress();
						} else
							readMailDTO.setTo(((InternetAddress) address).getAddress());
						ccFlag = true;
					}
				}
				readMailDTO.setCc(cc);
				readMailDTO.setSentDate(message.getSentDate() != null ? detailedDateFormat.format(message.getSentDate()) : "");
				readMailDTO.setSubject(message.getSubject());
				readMailDTO.setFolderName(folderName);
				String bodyContent = "";
				Object body = message.getContent();
				if (body != null && body instanceof String) {
					bodyContent = (String) body;
				}
				message.getAllHeaders();

				String contentType = message.getContentType();
				List<AttachmentDTO> filesList = new LinkedList<>();
				if (contentType.contains("multipart")) {
					// content may contain attachments
					Multipart multiPart = (Multipart) message.getContent();
					MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
					bodyContent = getTextFromMimeMultipart(mimeMultipart);
					int numberOfParts = multiPart.getCount();

					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
						if (part.getDisposition() != null) {
							AttachmentDTO attachment = new AttachmentDTO();
							String fileName = part.getFileName();
							String fileType = part.getContentType();
							String filePath = staticPath + System.getProperty("file.separator") + orgId
									+ System.getProperty("file.separator") + orgLocationId + System.getProperty("file.separator")
									+ "Email Attachments" + System.getProperty("file.separator") + user
									+ System.getProperty("file.separator") + folderName + System.getProperty("file.separator") + uid;
							try {
								File attachmentFolderPath = new File(filePath);
								if (!attachmentFolderPath.exists())
									attachmentFolderPath.mkdirs();
								File attachmentFile = new File(filePath + System.getProperty("file.separator") + fileName);
								if (!attachmentFile.exists())
									part.saveFile(attachmentFile);
								String fileUrl = "/slead-static/" + orgId + "/" + orgLocationId + "/Email Attachments/" + user + "/"
										+ folderName + "/" + uid;

								attachment.setUrl(fileUrl);
								attachment.setPath(attachmentFolderPath.getAbsolutePath());
								attachment.setFileExtension(fileName.substring(fileName.lastIndexOf("."), fileName.length()));
								/*float fileSize = attachmentFile.length() / 1024;
								attachment.setSizeUnit("KB");
								if (fileSize > 1024) {
									fileSize = fileSize / 1024;
									attachment.setSizeUnit("MB");
								}
								attachment.setFileSize(fileSize);*/
							} catch (Exception e) {
								attachment.setAttachmentNotFoundOrCorrupted(true);
								/*e.printStackTrace();*/
							}
							attachment.setFileName(fileName);
							attachment.setFileType(fileType);
							filesList.add(attachment);
						}
					}
				}
				readMailDTO.setBody(bodyContent);
				readMailDTO.setFilesList(filesList);
			}
			emailFolder.close(true);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readMailDTO;
	}

	public static boolean deleteEmailsByUid(String host, String port, String storeType, String email, String password, long[] messageIds,
			String folderName, boolean deleteForever) {
		try {
			Properties properties = new Properties();
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getInstance(properties);

			// create the store object and connect with the pop server
			Store store = emailSession.getStore(storeType);

			store.connect(host, email, password);
			Folder emailFolder = store.getFolder(folderName);
			emailFolder.open(Folder.READ_WRITE);
			UIDFolder uf = (UIDFolder) emailFolder;
			for (long uid : messageIds) {
				Message message = uf.getMessageByUID(uid);
				if (message != null) {
					if (deleteForever) {
						// emailFolder.setFlags(messageIds, new Flags(Flags.Flag.DELETED), true);
						message.setFlag(Flags.Flag.DELETED, true);
					} else {
						// copy message to trash folder
						Folder trashFolder = null;
						Folder[] allFolders = store.getDefaultFolder().list("*");
						for (Folder folder : allFolders) {
							if (folder.getName().contains("Trash")) {
								trashFolder = folder;
							}
						}
						if (trashFolder == null || !trashFolder.exists())
							trashFolder.create(Folder.HOLDS_MESSAGES);
						emailFolder.copyMessages(new Message[] { message }, trashFolder);
						// delete original message
						message.setFlag(Flags.Flag.DELETED, true);
					}
				}
			}
			emailFolder.close(true);
			store.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("static-access")
	public static Response<Boolean> composeEmail(String outgoingHost, String outgoingPort, boolean outgoingSSL, String incomingHost,
			String incomingPort, boolean incomingSSL, String transportType, String email, String password, long orgId, long orgLocationId,
			String staticPath, ReadMailDTO readMailDTO) {
		Response<Boolean> response = new Response<>();
		try {
			Properties properties = new Properties();
			properties.put("mail.transport.protocol", transportType);
			properties.put("mail.smtp.host", outgoingHost);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", outgoingPort);
			properties.put("mail.debug", "true");
			properties.put("mail.smtp.socketFactory.port", outgoingPort);
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			properties.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(readMailDTO.getTo()));

			if (readMailDTO.getCc() != null && !readMailDTO.getCc().isEmpty())
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(readMailDTO.getCc()));

			message.setSubject(readMailDTO.getSubject());
			String filePath = staticPath + System.getProperty("file.separator") + orgId + System.getProperty("file.separator")
					+ orgLocationId;
			Multipart multipart = new MimeMultipart();
			if (readMailDTO.getFilesList() != null && !readMailDTO.getFilesList().isEmpty()) {
				for (AttachmentDTO attachmentDTO : readMailDTO.getFilesList()) {
					BodyPart bodyPart = new MimeBodyPart();
					String attachmentFolder = (readMailDTO.getSendType() != null && readMailDTO.getSendType().equalsIgnoreCase("forward")
							&& !attachmentDTO.isNewUpload()) ? "Email Attachments" : "temp";

					String subFolder = (readMailDTO.getSendType() != null && readMailDTO.getSendType().equalsIgnoreCase("forward")
							&& !attachmentDTO.isNewUpload())
									? attachmentDTO.getUrl().substring(
											attachmentDTO.getUrl().indexOf(attachmentFolder) + attachmentFolder.length(),
											attachmentDTO.getUrl().lastIndexOf("/") + 1)
									: "";

					File file = new File(filePath + System.getProperty("file.separator") + attachmentFolder + subFolder,
							attachmentDTO.getFileName());
					DataSource dataSource = new FileDataSource(file);
					bodyPart.setDataHandler(new DataHandler(dataSource));
					bodyPart.setFileName(file.getName());
					multipart.addBodyPart(bodyPart);
				}
			}

			BodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(readMailDTO.getBody(), "text/html");
			multipart.addBodyPart(htmlBodyPart);

			message.setContent(multipart);

			Transport transport = session.getTransport();
			transport.connect(outgoingHost, email, password);
			transport.send(message, message.getAllRecipients());

			if (!outgoingHost.equalsIgnoreCase("smtp.gmail.com")) {
				Properties properties1 = new Properties();
				properties1.put("mail.imap.host", incomingHost);
				properties1.put("mail.imap.port", incomingPort);
				properties1.put("mail.imap.starttls.enable", "true");
				Session emailSession = Session.getInstance(properties1);

				// create the IMAP store object and connect with the pop server
				Store store = emailSession.getStore("imaps");

				store.connect(outgoingHost, email, password);

				Folder folder = (Folder) store.getFolder("Sent Items");
				if (folder.exists() == false) {
					folder = (Folder) store.getFolder("Sent");
					if (folder.exists() == false) {
						folder.create(Folder.HOLDS_MESSAGES);
					}
				}
				folder.open(Folder.READ_WRITE);
				message.setSentDate(new Date());
				folder.appendMessages(new Message[] { message });
			}
			transport.close();

			if (readMailDTO.getSendType() == null || (!readMailDTO.getSendType().equalsIgnoreCase("forward")
					&& readMailDTO.getFilesList() != null && !readMailDTO.getFilesList().isEmpty())) {
				for (AttachmentDTO attachmentDTO : readMailDTO.getFilesList()) {
					File file = new File(filePath + System.getProperty("file.separator") + "temp", attachmentDTO.getFileName());
					file.delete();
				}
			}
			System.out.println("------------------ Email Sent ------------------");
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage("Email Sent Successfully....");
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.FAILED.name());
			response.setResponseMessage(Result.FAILED.getValue());
		}
		return response;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {
		int count = mimeMultipart.getCount();
		if (count == 0)
			throw new MessagingException("Multipart with no body parts not supported.");
		boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
		if (multipartAlt) {
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
			result = html;
		} else if (bodyPart.getContent() instanceof MimeMultipart) {
			result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
		}
		return result;
	}

}
