package com.bis.sourcelead.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CampaignUtil;
import com.bis.sourcelead.util.CommonUtil;
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
	
	@Autowired
	private CommonUtil commonUtil;
	
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
		
		getCampaignStats();
		return campaignList;
	}
	
	private void getCampaignStats() throws Exception
	{
		List<Campaign> campaignList = campaignService.getAllCampaigns();
		System.out.println("****************************************"+campaignList.size());
		
		for(Campaign campaign : campaignList) {
			String response = campaignUtil.readCampaignStatsByCategory(campaign);
			OrgLocations orgLocation = orgLocationService.getOrgLocations(campaign.getOrgLocation().getId());
			System.out.println("response :: "+response);
			if(StringUtils.isNotBlank(response)) {
				JSONArray jsonArr = new JSONArray(response);
				int blocks = 0, bounce_drops = 0, bounces = 0, clicks = 0, deferred = 0, delivered = 0, invalid_emails = 0, opens = 0,
						processed = 0, requests = 0, spam_report_drops = 0, spam_reports = 0, unique_clicks = 0, unique_opens = 0,
						unsubscribe_drops = 0, unsubscribes = 0;
				String campaignName = "";
				Date date = campaign.getCreatedDate();
				CampaignStats  campaignStats = new CampaignStats();
				
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
//					System.out.println(jsonObj.getString("date"));
//					DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD"); 
//					Date date = (Date)formatter.parse(jsonObj.getString("date"));
//					campaignStats.setCampaignDate(date);
					JSONArray statJsonArr = jsonObj.getJSONArray("stats");
					for (int j = 0; j < statJsonArr.length(); j++) {
						JSONObject statsObj = statJsonArr.getJSONObject(j);
						JSONObject metricsObj = statsObj.getJSONObject("metrics");
						campaignName = statsObj.getString("name");
						blocks = blocks + Integer.parseInt(metricsObj.getString("blocks"));
						bounce_drops = bounce_drops + Integer.parseInt(metricsObj.getString("bounce_drops"));
						bounces = bounces + Integer.parseInt(metricsObj.getString("bounces"));
						clicks = clicks + Integer.parseInt(metricsObj.getString("clicks"));
						deferred = deferred + Integer.parseInt(metricsObj.getString("deferred"));
						delivered = delivered + Integer.parseInt(metricsObj.getString("delivered"));
						invalid_emails = invalid_emails + Integer.parseInt(metricsObj.getString("invalid_emails"));
						opens = opens + Integer.parseInt(metricsObj.getString("opens"));
						processed = processed + Integer.parseInt(metricsObj.getString("processed"));
						requests = requests + Integer.parseInt(metricsObj.getString("requests"));
						spam_report_drops = spam_report_drops + Integer.parseInt(metricsObj.getString("spam_report_drops"));
						spam_reports = spam_reports + Integer.parseInt(metricsObj.getString("spam_reports"));
						unique_clicks = unique_clicks + Integer.parseInt(metricsObj.getString("unique_clicks"));
						unique_opens = unique_opens + Integer.parseInt(metricsObj.getString("unique_opens"));
						unsubscribe_drops = unsubscribe_drops + Integer.parseInt(metricsObj.getString("unsubscribe_drops"));
						unsubscribes = unsubscribes + Integer.parseInt(metricsObj.getString("unsubscribes"));
						
					}
					
			}
				campaignStats.setCampaignDate(date);
				campaignStats.setCategoryName(campaignName);
				campaignStats.setCampaignId(campaign.getCampaignId());
				campaignStats.setBlocks(String.valueOf(blocks));
				campaignStats.setBounceDrops(String.valueOf(bounce_drops));
				campaignStats.setBounces(String.valueOf(bounces));
				campaignStats.setClicks(String.valueOf(clicks));
				campaignStats.setDeferred(String.valueOf(deferred));
				campaignStats.setDelivered(String.valueOf(delivered));
				campaignStats.setInvalidEmails(String.valueOf(invalid_emails));
				campaignStats.setOpens(String.valueOf(opens));
				campaignStats.setProcessed(String.valueOf(processed));
				campaignStats.setRequest(String.valueOf(requests));
				campaignStats.setSpamReportDrops(String.valueOf(spam_report_drops));
				campaignStats.setSpamReports(String.valueOf(spam_reports));
				campaignStats.setUniqueClicks(String.valueOf(unique_clicks));
				campaignStats.setUniqueOpens(String.valueOf(unique_opens));
				campaignStats.setUnsubscribeDrops(String.valueOf(unsubscribe_drops));
				campaignStats.setUnsubscribes(String.valueOf(unsubscribes));
				campaignStats.setStatus(1);
				Date orgDate = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
				campaignStats.setCreatedDate(orgDate);
				campaignStats.setUpdatedDate(orgDate);
				campaignStats.setOrgLocation(orgLocation);
				CampaignStats saveCampaignStats = campaignService.saveCampaignStats(campaignStats);
				if(saveCampaignStats !=null) {
					System.out.println("inserted");
				}
		}
		}
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
	
	@RequestMapping(value = "/listViewPop")
	public @ResponseBody  List<CampaignListDTO> getlistViewPop(CampaignListDTO campaignListDTO, @RequestParam(value = "viewListId", required = false) String viewListId, HttpServletRequest request) throws Exception
	{
		List<CampaignListDTO> campaiList = new ArrayList<>() ; 
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CampaignRecipient> campList = campaignService.getRecipientListByListIdAndOrgLocationId(viewListId, orgLocation.getId());
		for (CampaignRecipient campaignRecipient : campList) {
			CampaignListDTO campaignListdto = new CampaignListDTO(); 
			campaignListdto.setFirstName("");
			campaignListdto.setLastName("");
			campaignListdto.setEmail(campaignRecipient.getEmail());
			campaiList.add(campaignListdto);
		}
		
		return campaiList;
		
	}
	
	
}
