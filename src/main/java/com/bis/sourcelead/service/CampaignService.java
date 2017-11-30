package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CreateSender;

import java.util.List;

import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CampaignService {

	boolean createSender(CreateSenderDTO createSenderDTO, User user, OrgLocations orgLocation)throws Exception;

	boolean validateNickName(String nickName, OrgLocations orgLocation, Long senderId)throws Exception;

	ResponseList<CreateSenderDTO> getSendersListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	boolean deleteSenderIdentity(OrgLocations orgLocation, Long senderId) throws Exception;
	
	CreateSender findSenderIdentityById(Long senderId) throws Exception;

	boolean saveRecipientList(OrgLocations orgLocation, CampaignListDTO campaignListDTO, User user) throws Exception;

	List<CreateSenderDTO> getSenderIdentityByUserChoice(User user, OrgLocations orgLocations) throws Exception;

	ResponseList<CampaignListDTO> getRecipientListByUserId(User user, OrgLocations orgLocation, CampaignListDTO campaignListDTO) throws Exception;

	CampaignList getRecipientListByListId(String listId) throws Exception;

	Campaign saveCampaignScheduledHistroy(Campaign campaign) throws Exception;;


}
