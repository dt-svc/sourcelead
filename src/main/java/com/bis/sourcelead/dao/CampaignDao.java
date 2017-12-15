package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CampaignStatsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CampaignDao {

	CreateSender createSender(CreateSender createSender)throws Exception;

	List<CreateSender> getSendersListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	CreateSender findSenderIdentityById(Long senderId) throws Exception;

	CampaignList saveRecipientList(CampaignList campaignList) throws Exception;

	List<CreateSender> getSenderIdentityByUserChoice(User user, OrgLocations orgLocations) throws Exception;

	ResponseList<CampaignList> getRecipientListByUserId(User user, OrgLocations orgLocation, CampaignListDTO campaignListDTO) throws Exception;

	CampaignList getCampaignListById(long id);
	
	boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable);

	CampaignList getRecipientListByListId(String listId, int campaignType)  throws Exception;

	Campaign saveCampaignScheduledHistroy(Campaign campaign) throws Exception;


	List<CampaignRecipient> getRecipientListByListIdAndOrgLocationId(String viewListId, Long orgLocationId);

	int deleteRecipientListByListId(Long campaignListId) throws Exception;

	CampaignStats saveCampaignStats(CampaignStats campaignStats) throws Exception;

	List<Campaign> getAllCampaigns() throws Exception;


	ResponseList<CampaignStats> getCampaignStatsListByOrgLocationId(Long orgLocationId, CampaignStatsDTO campaignStatsDTO) throws Exception;

	CampaignStats getCampaignStatsByCampaignId(String campaignId) throws Exception;
	
	CreateSender validateFromMail(String email, String toWhom,String id) throws Exception;

	boolean validateNickName(String nickName, OrgLocations orgLocation,String senderIdentity) throws Exception;
	
	Campaign validateCampaignSubject(String subjectName, Long orgLocationId,int campaignType) throws Exception;
	
	Map<String,Integer> getCampaignListName(String campaignId) throws Exception;



}
