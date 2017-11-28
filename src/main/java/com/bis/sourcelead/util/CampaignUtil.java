package com.bis.sourcelead.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
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
//		      System.out.println(response.getStatusCode());
//		      System.out.println(response.getBody());
//		      System.out.println(response.getHeaders());
		    } catch (IOException ex) {
		      throw ex;
		    }
		return str;
	}
	
}
