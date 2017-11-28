package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CampaignDao {

	CreateSender createSender(CreateSender createSender)throws Exception;

	boolean validateNickName(String nickName, OrgLocations orgLocation, Long senderId) throws Exception;

	List<CreateSender> getSendersListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	CreateSender findSenderIdentityById(Long senderId) throws Exception;

	CampaignList saveRecipientList(CampaignList campaignList) throws Exception;

	CreateSender getSenderIdentityByUserChoice(User user, OrgLocations orgLocations) throws Exception;;

}
