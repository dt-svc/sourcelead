package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.ResponseList;

import com.bis.sourcelead.entity.Campaign;

import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.CreateSender;

import java.util.List;

import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CampaignStatsDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CampaignService {

	String createSender(CreateSenderDTO createSenderDTO, User user, OrgLocations orgLocation)throws Exception;

	boolean validateNickName(String nickName, OrgLocations orgLocation, String senderId)throws Exception;

	ResponseList<CreateSenderDTO> getSendersListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	boolean deleteSenderIdentity(OrgLocations orgLocation, Long senderId) throws Exception;
	
	CreateSender findSenderIdentityById(Long senderId) throws Exception;

	String saveRecipientList(OrgLocations orgLocation, CampaignListDTO campaignListDTO, User user) throws Exception;

	List<CreateSenderDTO> getSenderIdentityByUserChoice(User user, OrgLocations orgLocations) throws Exception;

	ResponseList<CampaignListDTO> getRecipientListByUserId(User user, OrgLocations orgLocation, CampaignListDTO campaignListDTO) throws Exception;

	CampaignList getCampaignListById(long id);

	boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable);

	CampaignList getRecipientListByListId(String listId, int campaignType) throws Exception;

	Campaign saveCampaignScheduledHistroy(Campaign campaign) throws Exception;

	List<CampaignRecipient> getRecipientListByListIdAndOrgLocationId(String viewListId, Long id);

	CampaignStats saveCampaignStats(CampaignStats campaignStats) throws Exception;

	List<Campaign> getAllCampaigns()  throws Exception;


	ResponseList<CampaignStatsDTO> getCampaignStatsListByOrgLocationId(Long orgLocationId, CampaignStatsDTO campaignStatsDTO) throws Exception;

	CampaignStats getCampaignStatsByCampaignId(String campaignName) throws Exception;

	boolean validateCampaignSubject(String subjectName, Long orgLocationId, int campaignType) throws Exception;

	boolean deleteStats(String categoryName) throws Exception;

	boolean validateFromMail(String email, String toWhom, String id) throws Exception;





}
