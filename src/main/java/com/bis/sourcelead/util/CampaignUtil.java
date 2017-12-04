package com.bis.sourcelead.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.dto.JobListEmailCampaignDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.google.gson.JsonObject;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Component
public class CampaignUtil {
	
	/**
	 * Create Sender Identity
	 * 
	 * @param createSenderDTO
	 * @param orgLocation
	 * @return String
	 * @throws IOException
	 */
	public String createSender(CreateSenderDTO createSenderDTO, OrgLocations orgLocation) throws IOException
	{
		String isSenderCreated = null;
		String city = "";
		String country = "";
		String address = "";
		String zipcode = "";
		String state = "";
		if (StringUtils.isNotBlank(orgLocation.getCity())) {
			city = orgLocation.getCity();
		}
		if (StringUtils.isNotBlank(orgLocation.getCountry())) {
			country = orgLocation.getCountry();
		}
		if (StringUtils.isNotBlank(orgLocation.getZipCode())) {
			zipcode = orgLocation.getZipCode();
		}
		if (StringUtils.isNotBlank(orgLocation.getState())) {
			state = orgLocation.getState();
		}
		StringBuilder sb = new StringBuilder();
		address = sb.append(city).append(",").append(state).append(country).toString();
		try {
			SendGrid sg = new SendGrid("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");
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("senders");
			request.setBody("{\"city\":\"" + city + "\"," + "\"from\":{\"email\":\""
					+ createSenderDTO.getCpnSenderFromMail() + "\"," + "\"name\":\""
					+ createSenderDTO.getCpnSenderName() + "\"}," + "\"zip\":\"" + zipcode + "\","
					+ "\"country\":\"" + country + "\"," + "\"state\":\"" + state + "\","
					+ "\"address_2\":\"\"," + "\"address\":\"" + address + "\","
					+ "\"reply_to\":{\"email\":\"" + createSenderDTO.getCpnReplyMail() + "\","
					+ "\"name\":\"\"}," + "\"nickname\":\"" + createSenderDTO.getCpnNickName()
					+ "\"}");
			Response response = sg.api(request);
			if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
				isSenderCreated = response.getBody();
			}
			return isSenderCreated;
		} catch (IOException ex) {
			throw ex;
		}
	}

	/**
	 * Update sender Identity
	 * 
	 * @param createSender
	 * @param orgLocation
	 * @return String
	 * @throws IOException
	 */
	public String updateSender(CreateSender createSender, OrgLocations orgLocation) throws IOException
	{
		String isSenderCreated = null;
		String city = "";
		String country = "";
		String address = "";
		String zipcode = "";
		String state = "";
		if (StringUtils.isNotBlank(orgLocation.getCity())) {
			city = orgLocation.getCity();
		}
		if (StringUtils.isNotBlank(orgLocation.getCountry())) {
			country = orgLocation.getCountry();
		}
		if (StringUtils.isNotBlank(orgLocation.getZipCode())) {
			zipcode = orgLocation.getZipCode();
		}
		if (StringUtils.isNotBlank(orgLocation.getState())) {
			state = orgLocation.getState();
		}
		StringBuilder sb = new StringBuilder();
		address = sb.append(city).append(",").append(state).append(country).toString();
		try {
			SendGrid sg = new SendGrid("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");
			Request request = new Request();
			request.setMethod(Method.PATCH);
			request.setEndpoint("senders/" + createSender.getSenderId() + "");
			request.setBody("{\"city\":\"" + city + "\"," + "\"from\":{\"email\":\""
					+ createSender.getFromMail() + "\"," + "\"name\":\""
					+ createSender.getFromName() + "\"}," + "\"zip\":\"" + zipcode + "\","
					+ "\"country\":\"" + country + "\"," + "\"state\":\"" + state + "\","
					+ "\"address_2\":\"\"," + "\"address\":\"" + address + "\","
					+ "\"reply_to\":{\"email\":\"" + createSender.getReplyTo() + "\","
					+ "\"name\":\"\"}," + "\"nickname\":\"" + createSender.getNickName() + "\"}");
			Response response = sg.api(request);
			if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
				isSenderCreated = response.getBody();
			}
			return isSenderCreated;
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	/**
	 * create list 
	 * @param campaignListDTO
	 * @return response string
	 * @throws IOException
	 */
	public String createList(CampaignListDTO campaignListDTO) throws IOException
	{
		String isListCreated = null;
		try {
			SendGrid sg = new SendGrid("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");
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("contactdb/lists");
			request.setBody("{\"name\":\"" + campaignListDTO.getCpnListName() + "\"}");
			Response response = sg.api(request);
			if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
				isListCreated = response.getBody();
			}
		} catch (IOException ex) {
			throw ex;
		}
		return isListCreated;
	}

	/**
	 * Add Multiple Recipients
	 * @param reciList
	 * @return response string
	 * @throws Exception
	 */
	public String createRecipientsl(String email) throws Exception
	{
		  String str = null;
		try {
			 String lasteName = "";
			 String firstName = "";
		      SendGrid sg = new SendGrid("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");
		      Request request = new Request();
		      request.setMethod(Method.POST);
		      request.setEndpoint("contactdb/recipients");
		      request.setBody("[{\"last_name\":\""+lasteName+"\",\"email\":\""+email+"\",\"first_name\":\""+firstName+"\"}]");
		      Response response = sg.api(request);
		     if(response.getStatusCode() ==200 || response.getStatusCode() == 201) {
		    	 str = response.getBody();
		     }
		    } catch (IOException ex) {
		      throw ex;
		    }
		return str;
	}

	public String createCampaign(JobListEmailCampaignDTO jobListEmailCampaignDTO, String htmlContent) throws IOException
	{
		 String isCampaignCreated = null;
		 String senderId = jobListEmailCampaignDTO.getSenderIdentity();
		 List<String> recipientList = new ArrayList<String>(Arrays.asList(jobListEmailCampaignDTO.getToemails().split(",")));
		 String subject = jobListEmailCampaignDTO.getSubject();
		    try {
		        SendGrid sg = new SendGrid("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");
		        Request request = new Request();
		        request.setMethod(Method.POST);
		        request.setEndpoint("campaigns");
		        request.setBody("{\"custom_unsubscribe_url\":\"www.sourcelead.com\",\"html_content\":\""+htmlContent+"\",\"list_ids\":"+recipientList+",\"sender_id\":"+senderId+",\"subject\":\""+subject+"\",\"plain_content\":\"Check out our sourcelead To unsubscribe, visit [unsubscribe].!\",\"title\":\""+subject+"\",\"categories\":[\""+subject+"\"]}");
		        Response response = sg.api(request);
		        isCampaignCreated = response.getBody();
		      } catch (IOException ex) {
		        throw ex;
		      }
		return isCampaignCreated;
	}

	public String sendCampaign(String campaignId) throws IOException
	{
		  String isScheduledCpn = null;
		try {
		      SendGrid sg = new SendGrid("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");
		      Request request = new Request();
		      request.setMethod(Method.POST);
		      request.setEndpoint("campaigns/"+campaignId+"/schedules/now");
		      Response response = sg.api(request);
		      if(response.getStatusCode() == 200 || response.getStatusCode() == 201) {
		    	  isScheduledCpn = response.getBody();
		      }
		    } catch (IOException ex) {
		      throw ex;
		    }
		return isScheduledCpn;
		
	}

	public String addRecipientsToList(CampaignList list) throws IOException
	{
		String isRecipientAddedToList = null;
		Set<CampaignRecipient>  recipientSet = list.getCampaignRecipient();
		List<String> listIds = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for(CampaignRecipient campaignRecipient : recipientSet) {
			listIds.add(campaignRecipient.getRecipientId());
			String email = campaignRecipient.getRecipientId();
			sb.append("\"").append(email).append("\"").append(",");
		}
		String recipientsIds = sb.toString();
		recipientsIds = recipientsIds.substring(0, recipientsIds.length() - 1);
		 try {
		      SendGrid sg = new SendGrid("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");
		      Request request = new Request();
		      request.setMethod(Method.POST);
		      request.setEndpoint("contactdb/lists/"+list.getListId()+"/recipients");
		      request.setBody("["+recipientsIds+"]");
		      Response response = sg.api(request);
		      if(response.getStatusCode() ==200 || response.getStatusCode() == 201) {
		    	  isRecipientAddedToList = response.getBody();
		      }
		    } catch (IOException ex) {
		      throw ex;
		    }
		return isRecipientAddedToList;
	}

	public int deleteSenderIdentity(String senderId) throws IOException
	{
		int statusCode = 0;
		 try {
		      SendGrid sg = new SendGrid("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");
		      Request request = new Request();
		      request.setMethod(Method.DELETE);
		      request.setEndpoint("senders/"+senderId+"");
		      Response response = sg.api(request);
		     if(response.getStatusCode() == 204) {
		    	 statusCode = response.getStatusCode();
		     }
		      System.out.println(response.getStatusCode());
		      System.out.println(response.getBody());
		      System.out.println(response.getHeaders());
		    } catch (IOException ex) {
		      throw ex;
		    }
		return statusCode;
		
	}

	public int deleteCampaignConatactList(String listId) throws IOException
	{
		 int deleteCampaignStatus = 0;
		 List<String> listIds = new ArrayList<>();
		 listIds.add(listId);
	    try {
	        SendGrid sg = new SendGrid("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");
	        Request request = new Request();
	        request.setMethod(Method.DELETE);
	        request.setEndpoint("contactdb/lists");
	        request.setBody(""+listIds+"");
	        Response response = sg.api(request);
	        System.out.println(response.getStatusCode());
	        if(response.getStatusCode() == 204) {
	        	deleteCampaignStatus = response.getStatusCode(); 
	        }
	      } catch (IOException ex) {
	        throw ex;
	      }
		return deleteCampaignStatus;
	}

	public String updaeteList(CampaignList campaignList, CampaignListDTO campaignListDTO) throws IOException
	{
	     String isUpdated = null;
		try {
	        SendGrid sg = new SendGrid("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");
	        Request request = new Request();
	        request.setMethod(Method.PATCH);
	        request.setEndpoint("contactdb/lists/"+campaignList.getListId()+"");
	        request.setBody("{\"name\":\""+campaignListDTO.getCpnListName()+"\"}");
	        request.addQueryParam("list_id", "1");
	        Response response = sg.api(request);
	        if(response.getStatusCode() == 200) {
	        	isUpdated = response.getBody();
	        }
	      } catch (IOException ex) {
	        throw ex;
	      }
		return isUpdated;
	}

	public int deleteRecipientFromList(Set<CampaignRecipient> recipientList, String listId) throws IOException
	{
		 int deletedStatus = 0;
		for(CampaignRecipient campaignReci: recipientList) {
			 try {
			        SendGrid sg = new SendGrid("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");
			        Request request = new Request();
			        request.setMethod(Method.DELETE);
			        request.setEndpoint("contactdb/lists/"+listId+"/recipients/"+campaignReci.getRecipientId()+"");
			        request.addQueryParam("recipient_id", "1");
			        request.addQueryParam("list_id", "1");
			        Response response = sg.api(request);
			       if(response.getStatusCode() == 204) {
			    	   deletedStatus = response.getStatusCode();
			       }
			      } catch (IOException ex) {
			        throw ex;
			      }
		}
		return deletedStatus;
	   
		
	}

	
	
}
