package com.bis.sourcelead.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.dto.JobListEmailCampaignDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.rest.CampaignRestController;
import com.google.gson.JsonObject;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Component
public class CampaignUtil {
	private static final Logger logger = LoggerFactory.getLogger(CampaignUtil.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sendgridKey}")
	private String sendgridKey;
	
	
	
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
			SendGrid sg = new SendGrid(sendgridKey);
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
//			throw ex;
			return ex.getMessage();
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
	public String updateSender(CreateSenderDTO createSenderDto, OrgLocations orgLocation) throws IOException
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
			SendGrid sg = new SendGrid(sendgridKey);
			Request request = new Request();
			request.setMethod(Method.PATCH);
			request.setEndpoint("senders/" + createSenderDto.getSenderId() + "");
			request.setBody("{\"city\":\"" + city + "\"," + "\"from\":{\"email\":\""
					+ createSenderDto.getCpnSenderFromMail() + "\"," + "\"name\":\""
					+ createSenderDto.getCpnSenderName() + "\"}," + "\"zip\":\"" + zipcode + "\","
					+ "\"country\":\"" + country + "\"," + "\"state\":\"" + state + "\","
					+ "\"address_2\":\"\"," + "\"address\":\"" + address + "\","
					+ "\"reply_to\":{\"email\":\"" + createSenderDto.getCpnReplyMail() + "\","
					+ "\"name\":\"\"}," + "\"nickname\":\"" + createSenderDto.getCpnNickName() + "\"}");
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
			SendGrid sg = new SendGrid(sendgridKey);
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("contactdb/lists");
			request.setBody("{\"name\":\"" + campaignListDTO.getCpnListName() + "\"}");
			Response response = sg.api(request);
			if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
				isListCreated = response.getBody();
			}
		} catch (IOException ex) {
//			throw ex;
			return ex.getMessage();
		}
		return isListCreated;
	}

	/**
	 * Add Multiple Recipients
	 * @param reciList
	 * @return response string
	 * @throws Exception
	 */
	public String createRecipients(String email) throws Exception
	{
		 String result = null;
		try {
			  SendGrid sg = new SendGrid(sendgridKey);
		      Request request = new Request();
		      request.setMethod(Method.POST);
		      request.setEndpoint("contactdb/recipients");
		      request.setBody("["+email+"]");
		      Response response = sg.api(request);
		      if(response.getStatusCode() == 200 || response.getStatusCode() == 201) {
		    	  result = response.getBody();
		      }
		    } catch (Exception ex) {
		      throw ex;
		    }
		return result;
	}

	public String createCampaign(JobListEmailCampaignDTO jobListEmailCampaignDTO, String htmlContent, String otherRecilistId) throws IOException
	{
		 String isCampaignCreated = null;
		 String senderId = jobListEmailCampaignDTO.getSenderIdentity();
		 List<String> recipientList = new ArrayList<String>(Arrays.asList(jobListEmailCampaignDTO.getToemails().split(",")));
		 if(StringUtils.isNotBlank(otherRecilistId)) {
			 recipientList.add(otherRecilistId);
		 }
		 String subject = jobListEmailCampaignDTO.getSubject();
		    try {
		        SendGrid sg = new SendGrid(sendgridKey);
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
		      SendGrid sg = new SendGrid(sendgridKey);
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
		      SendGrid sg = new SendGrid(sendgridKey);
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
		      SendGrid sg = new SendGrid(sendgridKey);
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
	        SendGrid sg = new SendGrid(sendgridKey);
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
	        SendGrid sg = new SendGrid(sendgridKey);
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
			        SendGrid sg = new SendGrid(sendgridKey);
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

	/**
	 * Get the campaign stats based on category
	 * @param campaign 
	 * @param currentDate 
	 * @return String
	 * @throws IOException
	 */
	public String readCampaignStatsByCategory(Campaign campaign, Date currentDate) throws IOException
	{
		String stats = null;
//		Date sDate = campaign.getCreatedDate();
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String cmpn_satrt_date = newFormat.format(sDate);
		String cmpn_current_date = newFormat.format(currentDate);
		try {
			SendGrid sg = new SendGrid(""+sendgridKey+"");
			Request request = new Request();
			request.setMethod(Method.GET);
			request.setEndpoint("categories/stats");
			 request.addQueryParam("end_date", cmpn_current_date);
			// request.addQueryParam("aggregated_by", "day");
			// request.addQueryParam("limit", "1");
			// request.addQueryParam("offset", "1");
			request.addQueryParam("start_date", cmpn_current_date);
			request.addQueryParam("categories", campaign.getCampaignCategory());
			Response response = sg.api(request);
			if (response.getStatusCode() == 200) {
				stats = response.getBody();
			}
//			System.out.println(response.getBody());
			// System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
		return stats;
	}
	
	/**
	 * Add Multiple Other Recipients
	 * @param reciList
	 * @return response string
	 * @throws Exception
	 */
	public List<String> createOtherRecipients(List<String> otherRecipient) throws Exception
	{
		List<String> otherrecipientIds = new ArrayList<>();
		try {
			for(String otherReci : otherRecipient) {
				 String lasteName = "";
				 String firstName = "";
			      SendGrid sg = new SendGrid(sendgridKey);
			      Request request = new Request();
			      request.setMethod(Method.POST);
			      request.setEndpoint("contactdb/recipients");
			      request.setBody("[{\"last_name\":\""+lasteName+"\",\"email\":\""+otherReci+"\",\"first_name\":\""+firstName+"\"}]");
			      Response response = sg.api(request);
			     if(response.getStatusCode() ==200 || response.getStatusCode() == 201) {
			    	 String res = response.getBody();
			    	   JSONObject reciJObject = new JSONObject(res);
						JSONArray recipientsIds = reciJObject.getJSONArray("persisted_recipients");
						String recipientId = (String) recipientsIds.get(0);
						otherrecipientIds.add(recipientId);
			     }	
			}
			
		    } catch (IOException ex) {
		      throw ex;
		    }
		return otherrecipientIds;
	}
	
	/**
	 * Adding other recipient to list
	 * @param otherRecipientList
	 * @param string 
	 * @returnn String
	 * @throws IOException
	 */
	public String addOtherRecipientsToList(List<String> otherRecipientList, String listIds) throws IOException
	{
		String isRecipientAddedToList = null;
		 List<String> listId = new ArrayList<String>(Arrays.asList(listIds.split(",")));
		 StringBuilder sb = new StringBuilder();
		 for(String email : otherRecipientList) {
				sb.append("\"").append(email).append("\"").append(",");
			}
			String recipientsIds = sb.toString();
			recipientsIds = recipientsIds.substring(0, recipientsIds.length() - 1);
		 
		 try {
		      SendGrid sg = new SendGrid(sendgridKey);
		      Request request = new Request();
		      request.setMethod(Method.POST);
		      request.setEndpoint("contactdb/lists/"+listId.get(0)+"/recipients");
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
}
