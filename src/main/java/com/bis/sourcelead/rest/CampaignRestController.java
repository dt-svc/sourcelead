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
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CreateSenderDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class CampaignRestController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignRestController.class);
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private OrgLocationService  orgLocationService;
	
	/**
	 * creating sender email identity
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
		boolean isSenderCreated = campaignService.createSender(createSenderDTO,user,orgLocation);
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
		boolean isSenderCreated = campaignService.validateNickName(nickName, orgLocation,senderId);
		return isSenderCreated;
	}
	
	/**
	 * Get list of senders per org
	 * @param orglocationId
	 * @param request
	 * @param response
	 * @return ResponseList<CreateSenderDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSendersByLocationId",method = RequestMethod.GET)
	public ResponseList<CreateSenderDTO> getSendersList(@RequestParam(value = "orgLocationId", required = false) Long orglocationId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orglocationId);
		ResponseList<CreateSenderDTO>  createSenderDTOList = campaignService.getSendersListByOrgLocationId(orgLocation);
		return createSenderDTOList;
	}
	
	/**
	 * Delete sender identity
	 * @param orglocationId
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSenderIdentity",method = RequestMethod.GET)
	public boolean deleteSenderIdentity(@RequestParam(value = "senderId", required = false) Long senderId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean  isSenderDeleted = campaignService.deleteSenderIdentity(orgLocation,senderId);
		return isSenderDeleted;
	}
	
	
	@RequestMapping(value = "/saveRecipientList",method = RequestMethod.POST)
	public boolean saveRecipientList(CampaignListDTO campaignListDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean  isSenderDeleted = campaignService.saveRecipientList(orgLocation,campaignListDTO,user);
		return isSenderDeleted;
	}
	
	/**
	 * Get Senders List
	 * @param campaignListDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSenders",method = RequestMethod.GET)
	public List<CreateSenderDTO> getSenders(CampaignListDTO campaignListDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CreateSenderDTO> createSenderDtoList = campaignService.getSenderIdentityByUserChoice(user,orgLocation);
		return createSenderDtoList;
	}
	
	
	/**
	 * Get Senders List
	 * @param campaignListDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRecipientList",method = RequestMethod.GET)
	public List<CampaignListDTO> getRecipientList(CampaignListDTO campaignListDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CampaignListDTO> cpnDtoList = campaignService.getRecipientListByUserId(user,orgLocation);
		System.out.println(cpnDtoList.size());
		return cpnDtoList;
	}
	
}
