package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CampaignDao;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CampaignService;
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
	
	@Override
	public boolean createSender(CreateSenderDTO createSenderDTO, User user, OrgLocations orgLocation) throws Exception
	{
		boolean isSenderCreated = false;
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
		
		if (senderCreated != null) {
			
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
				isSenderCreated = true;
			}
		}
		return isSenderCreated;
	}

	@Override
	public boolean validateNickName(String nickName, OrgLocations orgLocation,Long senderId) throws Exception
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
				senderDTO.setId(sender.getId());
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
	public boolean saveRecipientList(OrgLocations orgLocation, CampaignListDTO campaignListDTO,User user) throws Exception
	{
		boolean isListInserted = false;
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
							isListInserted = true;
						}	
					}
					
				}else {
					isListInserted = false;
				}
			}
			return isListInserted;
		}
		String listResponse = campaignUtil.createList(campaignListDTO);
		if(StringUtils.isNotBlank(listResponse)) {
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
				isListInserted = true;
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
		for (String recipientMail : setReci) {
			CampaignRecipient campaignRecipient = new CampaignRecipient();
			String res = campaignUtil.createRecipients(recipientMail);
			JSONObject reciJObject = new JSONObject(res);
			JSONArray recipientsIds = reciJObject.getJSONArray("persisted_recipients");
			String recipientId = (String) recipientsIds.get(0);
			campaignRecipient.setCampaignListId(campaignList);
			campaignRecipient.setCreatedBy(user);
			campaignRecipient.setCreatedDate(date);
			campaignRecipient.setUpdatedBy(user.getUserCode());
			campaignRecipient.setStatus(1);
			campaignRecipient.setUpdatedDate(date);
			campaignRecipient.setEmail(recipientMail);
			campaignRecipient.setRecipientId(recipientId);
			recipientSet.add(campaignRecipient);
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
			createSenderDTO.setId(sender.getId());
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
			campaignListdto.setId(campaignList.getId());
			campaignListdto.setListId(campaignList.getListId());
			campaignListdto.setListName(campaignList.getListName());
			if(campaignList.getCampaignRecipient() !=null) {
				campaignListdto.setListCount(campaignList.getCampaignRecipient().size());
				
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
	public List<CampaignStats> getCampaignStatsListByOrgLocationId(Long orgLocationId) throws Exception
	{
		return campaignDao.getCampaignStatsListByOrgLocationId(orgLocationId);
	}
	@Override
	public CampaignStats getCampaignStatsByCampaignId(String campaignId) throws Exception
	{
		return campaignDao.getCampaignStatsByCampaignId(campaignId);

	}

}
