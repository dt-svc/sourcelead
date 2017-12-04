package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CampaignDao;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CampaignUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class CampaignRestController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignRestController.class);
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	CampaignDao campaignDao;
	
	@Autowired
	private CampaignUtil campaignUtil;
	
	/**
	 * creating sender email identity
	 * 
	 * @param createSenderDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCampaignSender")
	public boolean createSender(CreateSenderDTO createSenderDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isSenderCreated = campaignService.createSender(createSenderDTO, user, orgLocation);
		return isSenderCreated;
	}
	
	/**
	 * Check the NickName is duplicate or not
	 * 
	 * @param nickName
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateNickName", method = RequestMethod.GET)
	public boolean validateNickName(@RequestParam(value = "nickName", required = false) String nickName, @RequestParam(value = "senderId", required = false) Long senderId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isSenderCreated = campaignService.validateNickName(nickName, orgLocation, senderId);
		return isSenderCreated;
	}
	
	/**
	 * Get list of senders per org
	 * 
	 * @param orglocationId
	 * @param request
	 * @param response
	 * @return ResponseList<CreateSenderDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSendersByLocationId", method = RequestMethod.GET)
	public ResponseList<CreateSenderDTO> getSendersList(@RequestParam(value = "orgLocationId", required = false) Long orglocationId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orglocationId);
		ResponseList<CreateSenderDTO> createSenderDTOList = campaignService.getSendersListByOrgLocationId(orgLocation);
		return createSenderDTOList;
	}
	
	/**
	 * Delete sender identity
	 * 
	 * @param orglocationId
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSenderIdentity", method = RequestMethod.GET)
	public boolean deleteSenderIdentity(@RequestParam(value = "senderId", required = false) Long senderId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isSenderDeleted = campaignService.deleteSenderIdentity(orgLocation, senderId);
		return isSenderDeleted;
	}
	
	/**
	 * To save List , Recipient and add recipient to List 
	 * @param campaignListDTO
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveRecipientList", method = RequestMethod.POST)
	public boolean saveRecipientList(CampaignListDTO campaignListDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isSenderDeleted = campaignService.saveRecipientList(orgLocation, campaignListDTO, user);
		return isSenderDeleted;
	}
	
	/**
	 * Get Senders List
	 * 
	 * @param campaignListDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSenders", method = RequestMethod.GET)
	public List<CreateSenderDTO> getSenders(CampaignListDTO campaignListDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CreateSenderDTO> createSenderDtoList = campaignService.getSenderIdentityByUserChoice(user, orgLocation);
		return createSenderDtoList;
	}
	
	/**
	 * Get Senders List
	 * 
	 * @param campaignListDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRecipientList", method = RequestMethod.GET)
	public ResponseList<CampaignListDTO> getRecipientList(CampaignListDTO campaignListDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<CampaignListDTO> cpnDtoList = campaignService.getRecipientListByUserId(user, orgLocation, campaignListDTO);
		return cpnDtoList;
	}
	
	/**
	 * Get campaign recipient list
	 * @param campaignListDTO
	 * @param orgLocationId
	 * @param userId
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCampaignRecipientList")
	public @ResponseBody List<CampaignListDTO> getCampaignRecipientList(CampaignListDTO campaignListDTO, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "user", required = false) Long userId, HttpServletRequest request) throws Exception
	{
		List<CampaignListDTO> campaignList = null;
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.valueOf(orgLocationId));
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<CampaignListDTO> cpnDtoList = campaignService.getRecipientListByUserId(user, orgLocation, campaignListDTO);
		if (cpnDtoList.getData() != null) {
			campaignList = cpnDtoList.getData();
		}
		return campaignList;
	}
	
	/**
	 * Delete single campaign
	 * @param id
	 * @param orgLocationId
	 * @return true/false
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCampaignContactList")
	public boolean deleteCampaign(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception
	{
		boolean isDeleted = false;
		CampaignList campaignList = campaignService.getCampaignListById(Long.parseLong(id));
		int deleteStatusCode = campaignUtil.deleteCampaignConatactList(campaignList.getListId());
		if (campaignList != null && deleteStatusCode == 204) {
			campaignList.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			campaignDao.saveRecipientList(campaignList);
			isDeleted = true;
		}
		return isDeleted;
	}
	
	/**
	 * validate Campaign unique naem
	 * @param orgLocationId
	 * @param fromTable
	 * @param name
	 * @return boolean
	 */
	@RequestMapping(value = "/validateCampaignListName")
	public boolean validateCampaignListName(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId, @RequestParam(value = "from", required = true) String fromTable, @RequestParam(value = "name", required = true) String name)
	{
		boolean flag = false;
		flag = campaignService.validateNameByOrgLocationIdandFromTable(name, orgLocationId, fromTable);
		return flag;
	}
}
