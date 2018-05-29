package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CampaignDao;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CampaignStatsDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CampaignUtil;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class CampaignServiceImpl implements CampaignService{

	@Autowired
	CampaignUtil campaignUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CampaignDao campaignDao; 
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	ReferContactsService referContactsService;
	
	@Override
	public String createSender(CreateSenderDTO createSenderDTO, User user, OrgLocations orgLocation) throws Exception
	{
		String isSenderCreated = "failure";
		String senderCreated = null;
		CreateSender createSender = null;
		if(StringUtils.isNotBlank(createSenderDTO.getSenderId())) {
			 createSender  = campaignDao.findSenderIdentityById(Long.parseLong(createSenderDTO.getSenderId()));
			 createSenderDTO.setSenderId(createSender.getSenderId());
			 senderCreated = campaignUtil.updateSender(createSenderDTO, orgLocation);
		}else {
			 senderCreated = campaignUtil.createSender(createSenderDTO, orgLocation);
			 createSender = new CreateSender();
		}
		
		if(senderCreated.contains("400Body")) {
			isSenderCreated = "400Body";
		}else if(senderCreated.contains("403Body")) {
			isSenderCreated = "403Body";
		}
		
		if (senderCreated != null && !senderCreated.contains("400Body") && !senderCreated.contains("403Body")) {
			
			createSender.setFromName(createSenderDTO.getCpnSenderName().trim());
			createSender.setFromMail(createSenderDTO.getCpnSenderFromMail().trim());
			createSender.setReplyTo(createSenderDTO.getCpnReplyMail().trim());
			createSender.setNickName(createSenderDTO.getCpnNickName().trim());
			createSender.setOrgLocations(orgLocation);
			createSender.setStatus(1);
			createSender.setCreatedBy(user);
			Date date = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			createSender.setCreatedDate(date);
			createSender.setUpdatedBy(user.getUserCode());
			createSender.setUpdatedDate(date);
			if (StringUtils.isNotBlank(orgLocation.getCity())) {
				String city = orgLocation.getCity();
				createSender.setCity(city);
			}
			if (StringUtils.isNotBlank(orgLocation.getCountry())) {
				String country = orgLocation.getCountry();
				createSender.setCountry(country);
			}
			if (StringUtils.isNotBlank(orgLocation.getZipCode())) {
				String zipcode = orgLocation.getZipCode();
				createSender.setZip(zipcode);
			}
			if (StringUtils.isNotBlank(orgLocation.getState())) {
				String state = orgLocation.getState();
				createSender.setState(state);
			}
			JSONObject jObject  = new JSONObject(senderCreated); // json
			String senderId = jObject.getString("id");
			JSONObject data = jObject.getJSONObject("verified"); // get data object
			String isVerified = data.getString("status"); // g
			createSender.setIsSenderEmaiklVerified(isVerified);
			createSender.setSenderId(senderId);
			createSender.setSelectedSender("false");
			CreateSender sender = campaignDao.createSender(createSender);
			if(sender !=null) {
				isSenderCreated = "success";
			}
		}
		return isSenderCreated;
	}

	@Override
	public boolean validateNickName(String nickName, OrgLocations orgLocation,String senderId) throws Exception
	{
		return campaignDao.validateNickName(nickName, orgLocation,senderId);
	}

	@Override
	public ResponseList<CreateSenderDTO> getSendersListByOrgLocationId(OrgLocations orgLocation) throws Exception
	{
		ResponseList<CreateSenderDTO> responseList = new ResponseList<>();
		List<CreateSender> senderList = campaignDao.getSendersListByOrgLocationId(orgLocation);
		List<CreateSenderDTO> createSenderDTOList = new ArrayList<>();
		if (senderList != null && senderList.size() > 0) {
			for (CreateSender sender : senderList) {
				CreateSenderDTO senderDTO = new CreateSenderDTO();
				senderDTO.setCpnNickName(sender.getNickName());
				senderDTO.setCpnSenderName(sender.getFromName());
				senderDTO.setCpnReplyMail(sender.getReplyTo());
				senderDTO.setIsSenderEmailVerified(sender.getIsSenderEmaiklVerified());
				senderDTO.setCpnSenderFromMail(sender.getFromMail());
				senderDTO.setId(String.valueOf(sender.getId()));
				createSenderDTOList.add(senderDTO);
			}
			if (createSenderDTOList != null && createSenderDTOList.size() > 0) {
				responseList.setData(createSenderDTOList);
				responseList.setStatusCode("200");
			}
		}
		return responseList;
	}
	
	@Override
	public boolean deleteSenderIdentity(OrgLocations orgLocation, Long senderId) throws Exception
	{
		boolean isDeleted = false;
		CreateSender createSender = campaignDao.findSenderIdentityById(senderId);
		int statusCode = campaignUtil.deleteSenderIdentity(createSender.getSenderId()); 
		if (createSender != null && statusCode == 204) {
			createSender.setStatus(0);
			CreateSender updatedSender = campaignDao.createSender(createSender);
			if (updatedSender != null) {
				isDeleted = true;
			}
		}
		return isDeleted;
	}

	@Override
	public CreateSender findSenderIdentityById(Long senderId) throws Exception
	{
		return campaignDao.findSenderIdentityById(senderId);
	}

	@Override
	public String saveRecipientList(OrgLocations orgLocation, CampaignListDTO campaignListDTO,User user) throws Exception
	{
		String isListInserted = "failure";
		if(StringUtils.isNotBlank(campaignListDTO.getContactListId())){
			CampaignList campaignList = campaignDao.getCampaignListById(Long.parseLong(campaignListDTO.getContactListId()));
			String listResponse = campaignUtil.updaeteList(campaignList,campaignListDTO);
			if(StringUtils.isNotBlank(listResponse)) {
				JSONObject jObject  = new JSONObject(listResponse); 
				String listId = jObject.getString("id");
				campaignList.setListName(campaignListDTO.getCpnListName());
				Date date = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
				int deletedStatus = campaignUtil.deleteRecipientFromList(campaignList.getCampaignRecipient(),listId);
				if(deletedStatus == 204) {
					List<String> recipientList = campaignListDTO.getRecipientEmail();
					int i  = deleteRecipientListByListId(campaignList.getId());
					if(i > 0) {
						Set<CampaignRecipient>  recipientSet = getRecipientSet(recipientList,campaignList,date,user);
						campaignList.setCampaignRecipient(recipientSet);
						CampaignList list =campaignDao.saveRecipientList(campaignList);
						
						String res =  campaignUtil.addRecipientsToList(list);
						if(res !=null) {
							isListInserted = "success";
						}	
					}
					
				}else {
					isListInserted = "failure";
				}
			}
			return isListInserted;
		}
		String listResponse = campaignUtil.createList(campaignListDTO);
		if(listResponse.contains("400Body")) {
			isListInserted = "400Body";
		}else if(listResponse.contains("403Body")) {
			isListInserted = "403Body";
		}
		if(StringUtils.isNotBlank(listResponse) && !listResponse.contains("400Body") && (!listResponse.contains("403Body"))) {
			JSONObject jObject  = new JSONObject(listResponse); 
			String listId = jObject.getString("id");
			CampaignList  campaignList = new CampaignList();
			campaignList.setListName(campaignListDTO.getCpnListName());
			campaignList.setListId(listId);
			campaignList.setStatus(1);
			if("hotlistCampaign".equalsIgnoreCase(campaignListDTO.getCampaignType())) {
				campaignList.setCampaignType(0);
			}else {
				campaignList.setCampaignType(1);
			}
			campaignList.setCreatedBy(user);
			campaignList.setUpdatedBy(user.getUserCode());
			campaignList.setOrgLocation(orgLocation);
			Date date = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			campaignList.setCreatedDate(date);
			campaignList.setUpdatedDate(date);
			List<String> recipientList = campaignListDTO.getRecipientEmail();
			Set<CampaignRecipient>  recipientSet = getRecipientSet(recipientList,campaignList,date,user);
			campaignList.setCampaignRecipient(recipientSet);
			CampaignList list =campaignDao.saveRecipientList(campaignList);
			
			String res =  campaignUtil.addRecipientsToList(list);
			if(res !=null) {
				isListInserted = "success";
			}
			return isListInserted;
		}
		return isListInserted;
		
	}


	private int deleteRecipientListByListId(Long campaignListId) throws Exception
	{
		return campaignDao.deleteRecipientListByListId(campaignListId);
	}

	/**
	 * Reading Recipients set
	 * @param recipientList
	 * @param campaignList
	 * @param date
	 * @param user
	 * @return Set<CampaignRecipient>
	 * @throws Exception
	 */
	private Set<CampaignRecipient> getRecipientSet(List<String> recipientList, CampaignList campaignList, Date date, User user) throws Exception
	{
		Set<String> setReci = new HashSet<String>(recipientList);
		Set<CampaignRecipient> recipientSet = new HashSet<CampaignRecipient>();
		
		StringBuilder emailsb = new StringBuilder();
		 StringBuilder sb = new StringBuilder();
		 for (String recipientMail : setReci) {
			 String firstName = "";
			 String lastName = "";
				User usr = userService.findByEmail(recipientMail);
				if(usr != null) {
					firstName = usr.getFirstName();
					if(usr.getLastName() != null) {
						lastName = usr.getLastName();
					}
				}else {
					ReferContacts referContacts = referContactsService.findByEmail(recipientMail);
					if(referContacts !=null) {
						firstName = referContacts.getFirstName();
						if(referContacts.getLastName() != null) {
							lastName = referContacts.getLastName();
						}
					}
				}
	    	  sb.append("{\"last_name\":\""+firstName+"\",\"email\":\""+recipientMail+"\",\"first_name\":\""+lastName+"\"}").append(",");
	    	  emailsb.append(recipientMail).append(",");
		 }
		
		  String str = sb.toString();
	      str = str.substring(0, str.length() - 1);
		  String res = campaignUtil.createRecipients(str);
		  JSONObject jObject  = new JSONObject(res); 
		  
		  String emailSstr = emailsb.toString();
		  emailSstr = emailSstr.substring(0, emailSstr.length() - 1);
		   String[] emails = emailSstr.split(",");
		  if(res != null) {
			  JSONArray jsonArray = jObject.getJSONArray("persisted_recipients");
			  for (int j = 0; j < jsonArray.length(); j++) {
					CampaignRecipient campaignRecipient = new CampaignRecipient();
					String recipientId = jsonArray.getString(j);
					campaignRecipient.setCampaignListId(campaignList);
					campaignRecipient.setCreatedBy(user);
					campaignRecipient.setCreatedDate(date);
					campaignRecipient.setUpdatedBy(user.getUserCode());
					campaignRecipient.setStatus(1);
					campaignRecipient.setUpdatedDate(date);
					campaignRecipient.setEmail(emails[j]);
					campaignRecipient.setRecipientId(recipientId);
					User usr = userService.findByEmail(emails[j]);
					if(usr != null) {
						campaignRecipient.setFirstName(usr.getFirstName());
						if(usr.getLastName() != null) {
							campaignRecipient.setLastName(usr.getLastName());
						}
					}else {
						ReferContacts referContacts = referContactsService.findByEmail(emails[j]);
						if(referContacts !=null) {
							campaignRecipient.setFirstName(referContacts.getFirstName());
							if(referContacts.getLastName() != null) {
								campaignRecipient.setLastName(referContacts.getLastName());
							}
						}
					}
					recipientSet.add(campaignRecipient);
				}
		  }
		  
		 
		return recipientSet;
	}

	@Override
	public List<CreateSenderDTO> getSenderIdentityByUserChoice(User user, OrgLocations orgLocations) throws Exception
	{
		List<CreateSender> senderList = campaignDao.getSenderIdentityByUserChoice(user, orgLocations);
		List<CreateSenderDTO> list = new ArrayList<>();
		for (CreateSender sender : senderList) {
			CreateSenderDTO createSenderDTO = new CreateSenderDTO();
			createSenderDTO.setId(String.valueOf(sender.getId()));
			createSenderDTO.setCpnNickName(sender.getNickName());
			createSenderDTO.setCpnSenderName(sender.getFromName());
			createSenderDTO.setCpnSenderFromMail(sender.getFromMail());
			createSenderDTO.setSenderId(sender.getSenderId());
			list.add(createSenderDTO);
		}
		return list;
	}

	@Override
	public ResponseList<CampaignListDTO> getRecipientListByUserId(User user, OrgLocations orgLocation,CampaignListDTO campaignListDTO) throws Exception
	{
		ResponseList<CampaignListDTO> res = new ResponseList<>();
		ResponseList<CampaignList> response = campaignDao.getRecipientListByUserId(user,orgLocation,campaignListDTO);
		List<CampaignList> cpnList = response.getData(); 
		List<CampaignListDTO>  campaignListDtoList = new ArrayList<>();
		for(CampaignList  campaignList : cpnList) {
			CampaignListDTO campaignListdto = new CampaignListDTO();
			campaignListdto.setId(String.valueOf(campaignList.getId()));
			campaignListdto.setListId(campaignList.getListId());
			campaignListdto.setListName(campaignList.getListName());
			if(campaignList.getCampaignRecipient() !=null) {
				campaignListdto.setListCount(String.valueOf(campaignList.getCampaignRecipient().size()));
				
				List<String> emailList = new ArrayList<>();
				if(campaignList.getCampaignRecipient() !=null && campaignList.getCampaignRecipient().size() >0){
				Set<CampaignRecipient> recipientList = campaignList.getCampaignRecipient();
				for(CampaignRecipient campaignRecipient : recipientList){
				emailList.add(campaignRecipient.getEmail());
				} 
				}
				campaignListdto.setRecipientEmail(emailList);
			}
			campaignListDtoList.add(campaignListdto);
		}
		res.setData(campaignListDtoList);
		res.setRecordsFiltered(response.getRecordsFiltered());
		res.setRecordsTotal(response.getRecordsTotal());
		res.setStatusCode("200");
		return res;
	}

	@Override
	public CampaignList getCampaignListById(long id)
	{
		return campaignDao.getCampaignListById(id);
	}

	@Override
	public boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable)
	{
		return campaignDao.validateNameByOrgLocationIdandFromTable(name,orgLocationId,fromTable);
	}
	public CampaignList getRecipientListByListId(String listId,int campaignType) throws Exception
	{
		return campaignDao.getRecipientListByListId(listId,campaignType);
	}

	@Override
	public Campaign saveCampaignScheduledHistroy(Campaign campaign) throws Exception
	{
		return campaignDao.saveCampaignScheduledHistroy(campaign);

	}

	@Override
	public List<CampaignRecipient> getRecipientListByListIdAndOrgLocationId(String viewListId, Long orgLocationId)
	{
		return campaignDao.getRecipientListByListIdAndOrgLocationId(viewListId, orgLocationId);
	}

	@Override
	public CampaignStats saveCampaignStats(CampaignStats campaignStats) throws Exception
	{
		return campaignDao.saveCampaignStats(campaignStats);
	}

	@Override
	public List<Campaign> getAllCampaigns() throws Exception
	{
		return campaignDao.getAllCampaigns();
	}

	@Override
	public ResponseList<CampaignStatsDTO> getCampaignStatsListByOrgLocationId(Long orgLocationId, CampaignStatsDTO campaignStatsDTO) throws Exception
	{
		ResponseList<CampaignStatsDTO> campaignStatsResList = new ResponseList<>();
		List<CampaignStatsDTO> campaignStatsList = new ArrayList<>();
		
		ResponseList<CampaignStats>  campStats = campaignDao.getCampaignStatsListByOrgLocationId(orgLocationId, campaignStatsDTO);
		
		if(campStats.getData() !=null && campStats.getData().size() >0) {
			for (CampaignStats campaignStats : campStats.getData()) {
				Map<String,Integer> map = campaignDao.getCampaignListName(campaignStats.getCampaignId());
				CampaignStatsDTO campaignStatsListDTO = new CampaignStatsDTO();
				campaignStatsListDTO.setId(campaignStats.getId());
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					campaignStatsListDTO.setListName(entry.getKey());
					campaignStatsListDTO.setNumberOfRecipients(entry.getValue());
					String key = entry.getKey();
				    Object value = entry.getValue();
				    // you code here
				}
				campaignStatsListDTO.setCategoryName(campaignStats.getCategoryName());
				campaignStatsListDTO.setDelivered(campaignStats.getDelivered());
				campaignStatsListDTO.setUniqueOpens(campaignStats.getUniqueOpens());
				campaignStatsListDTO.setUniqueClicks(campaignStats.getUniqueClicks());
				campaignStatsListDTO.setUnsubscribes(campaignStats.getUnsubscribes());
				campaignStatsListDTO.setOpens(campaignStats.getOpens());
				campaignStatsListDTO.setCampaignId(campaignStats.getCampaignId());
				campaignStatsListDTO.setBlocks(campaignStats.getBlocks());
				campaignStatsListDTO.setBounceDrops(campaignStats.getBounceDrops());
				campaignStatsListDTO.setBounces(campaignStats.getBounces());
				campaignStatsListDTO.setClicks(campaignStats.getClicks());
				campaignStatsListDTO.setDeferred(campaignStats.getDeferred());
				campaignStatsListDTO.setInvalidEmails(campaignStats.getInvalidEmails());
				campaignStatsListDTO.setProcessed(campaignStats.getProcessed());
				campaignStatsListDTO.setRequest(campaignStats.getRequest());
				campaignStatsListDTO.setSpamReportDrops(campaignStats.getSpamReportDrops());
				campaignStatsListDTO.setSpamReports(campaignStats.getSpamReports());
				campaignStatsListDTO.setUnsubscribeDrops(campaignStats.getUnsubscribeDrops());
				campaignStatsList.add(campaignStatsListDTO);
			}
			campaignStatsResList.setData(campaignStatsList);
			campaignStatsResList.setRecordsTotal(campStats.getRecordsTotal());
			campaignStatsResList.setRecordsFiltered(campStats.getRecordsFiltered());
		}else {
			List<CampaignStatsDTO> statsList = getDailyCampaignStats();
			campaignStatsResList.setData(statsList);
			campaignStatsResList.setRecordsTotal(statsList.size());
			campaignStatsResList.setRecordsFiltered(statsList.size());
		}
		
		return campaignStatsResList;
	}
	@Override
	public CampaignStats getCampaignStatsByCampaignId(String campaignName) throws Exception
	{
		return campaignDao.getCampaignStatsByCampaignId(campaignName);

	}
	
	
	public List<CampaignStatsDTO> getDailyCampaignStats() throws Exception
	{
		List<CampaignStatsDTO> campaignStatsList = new ArrayList<>();
		List<Campaign> campaignList = campaignDao.getAllCampaigns();
		for(Campaign campaign : campaignList) {
			OrgLocations orgLocation = orgLocationService.getOrgLocations(campaign.getOrgLocation().getId());
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			String response = campaignUtil.readCampaignStatsByCategory(campaign,currentDate);
			if(StringUtils.isNotBlank(response)) {
				JSONArray jsonArr = new JSONArray(response);
				CampaignStatsDTO campaignStatsDto = new CampaignStatsDTO();
					  for (int i = 0; i < jsonArr.length(); i++) {
							JSONObject jsonObj = jsonArr.getJSONObject(i);
							JSONArray statJsonArr = jsonObj.getJSONArray("stats");
							for (int j = 0; j < statJsonArr.length(); j++) {
								JSONObject statsObj = statJsonArr.getJSONObject(j);
								JSONObject metricsObj = statsObj.getJSONObject("metrics");
								campaignStatsDto.setCategoryName(statsObj.getString("name"));
								campaignStatsDto.setCampaignId(campaign.getCampaignId());
								campaignStatsDto.setBlocks(metricsObj.getString("blocks"));
								campaignStatsDto.setBounceDrops(metricsObj.getString("bounce_drops"));
								campaignStatsDto.setBounces(metricsObj.getString("bounces"));
								campaignStatsDto.setClicks(metricsObj.getString("clicks"));
								campaignStatsDto.setDeferred(metricsObj.getString("deferred"));
								campaignStatsDto.setDelivered(metricsObj.getString("delivered"));
								campaignStatsDto.setInvalidEmails(metricsObj.getString("invalid_emails"));
								campaignStatsDto.setOpens(metricsObj.getString("opens"));
								campaignStatsDto.setProcessed(metricsObj.getString("processed"));
								campaignStatsDto.setRequest(metricsObj.getString("requests"));
								campaignStatsDto.setSpamReportDrops(metricsObj.getString("spam_report_drops"));
								campaignStatsDto.setSpamReports(metricsObj.getString("spam_reports"));
								campaignStatsDto.setUniqueClicks(metricsObj.getString("unique_clicks"));
								campaignStatsDto.setUniqueOpens(metricsObj.getString("unique_opens"));
								campaignStatsDto.setUnsubscribeDrops(metricsObj.getString("unsubscribe_drops"));
								campaignStatsDto.setUnsubscribes(metricsObj.getString("unsubscribes"));
							}
							
					}
//				}
					  campaignStatsList.add(campaignStatsDto);
				
		}
		}
		return campaignStatsList;
		
	}

	@Override
	public boolean validateCampaignSubject(String subjectName, Long orgLocationId,int campaignType) throws Exception
	{
		boolean isValid = false;
		Campaign campaign = campaignDao.validateCampaignSubject(subjectName,orgLocationId,campaignType);
		if(campaign != null) {
			isValid = true;
		}
		return isValid;
	}

	@Override
	public boolean deleteStats(String categoryName) throws Exception
	{
		boolean isValid = false;
		CampaignStats campaignStats = campaignDao.getCampaignStatsByCampaignId(categoryName);
		if(campaignStats != null) {
			campaignStats.setStatus(0);
			CampaignStats savedCampaignStats = campaignDao.saveCampaignStats(campaignStats);
			if(savedCampaignStats !=null) {
				isValid = true;
			}
		}
		return isValid;
	}

	@Override
	public boolean validateFromMail(String email, String toWhom,String id) throws Exception
	{
		boolean isDuplicate = false;
		CreateSender createSender = campaignDao.validateFromMail(email,toWhom,id);
		if(createSender !=null) {
			isDuplicate = true;
		}
		return isDuplicate;
	}

}