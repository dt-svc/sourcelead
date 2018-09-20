package com.bis.sourcelead.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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
import com.bis.sourcelead.dto.CampaignStatsDTO;
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
@RequestMapping("/app")
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
	public String createSender(CreateSenderDTO createSenderDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isSenderCreated = campaignService.createSender(createSenderDTO, user, orgLocation);
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
	public boolean validateNickName(@RequestParam(value = "nickName", required = false) String nickName, @RequestParam(value = "senderId", required = false) String senderId, HttpServletRequest request, HttpServletResponse response) throws Exception
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
	public String saveRecipientList(CampaignListDTO campaignListDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isSenderDeleted = campaignService.saveRecipientList(orgLocation, campaignListDTO, user);
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
	public @ResponseBody ResponseList<CampaignListDTO> getCampaignRecipientList(CampaignListDTO campaignListDTO, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "user", required = false) Long userId, HttpServletRequest request) throws Exception
	{
		List<CampaignListDTO> campaignList = null;
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.valueOf(orgLocationId));
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<CampaignListDTO> cpnDtoList = campaignService.getRecipientListByUserId(user, orgLocation, campaignListDTO);
		return cpnDtoList;
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
	 * validate Campaign unique name
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
			campaignListdto.setFirstName(campaignRecipient.getFirstName());
			if(campaignRecipient.getLastName() !=null) {
				campaignListdto.setLastName(campaignRecipient.getLastName());
			}
			campaignListdto.setEmail(campaignRecipient.getEmail());
			campaiList.add(campaignListdto);
		}
		
		return campaiList;
		
	}
	
	/**
	 * Reading Single campaign stats
	 * 
	 * @param orgLocationId
	 * @param request
	 * @return List<CampaignStatsDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCampaignStats")
	public ResponseList<CampaignStatsDTO> getCampaignStats(CampaignStatsDTO campaignStatsDTO, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception
	{
		ResponseList<CampaignStatsDTO> campStats = campaignService.getCampaignStatsListByOrgLocationId(orgLocationId, campaignStatsDTO);
		return campStats;
		
	}
	
	
	/**
	 * Reading Single campaign stats
	 * 
	 * @param orgLocationId
	 * @param request
	 * @return List<CampaignStatsDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCampaignStatsByCampaignId")
	public CampaignStatsDTO getCampaignStatsByCampaignId(@RequestParam(value = "campaignName", required = false) String campaignName, HttpServletRequest request) throws Exception
	{
		CampaignStats campaignStats = campaignService.getCampaignStatsByCampaignId(campaignName);
		if(campaignStats !=null) {
			Campaign campaign = new Campaign();
			campaign.setCampaignCategory(campaignStats.getCategoryName());
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			String response = campaignUtil.readCampaignStatsByCategory(campaign,currentDate);
			if(response !=null) {
				CampaignStatsDTO campaignStatsDTO = new CampaignStatsDTO();
				JSONArray jsonArr = new JSONArray(response);
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					JSONArray statJsonArr = jsonObj.getJSONArray("stats");
					for (int j = 0; j < statJsonArr.length(); j++) {
						JSONObject statsObj = statJsonArr.getJSONObject(j);
						JSONObject metricsObj = statsObj.getJSONObject("metrics");
						campaignStatsDTO.setId(campaignStats.getId());
						campaignStatsDTO.setCampaignId(campaignStats.getCampaignId());
						campaignStatsDTO.setCategoryName(campaignStats.getCategoryName());
						campaignStatsDTO.setDelivered(String.valueOf(Integer.parseInt(campaignStats.getDelivered())+Integer.parseInt(metricsObj.getString("delivered"))));
						campaignStatsDTO.setUniqueOpens(String.valueOf(Integer.parseInt(campaignStats.getUniqueOpens())+Integer.parseInt(metricsObj.getString("unique_opens"))));
						campaignStatsDTO.setUniqueClicks(String.valueOf(Integer.parseInt(campaignStats.getUniqueClicks())+Integer.parseInt(metricsObj.getString("unique_clicks"))));
						campaignStatsDTO.setUnsubscribes(String.valueOf(Integer.parseInt(campaignStats.getUnsubscribes())+Integer.parseInt(metricsObj.getString("unsubscribes"))));
						campaignStatsDTO.setOpens(String.valueOf(Integer.parseInt(campaignStats.getOpens())+Integer.parseInt(metricsObj.getString("opens"))));
						campaignStatsDTO.setCampaignId(campaignStats.getCampaignId());
						campaignStatsDTO.setBlocks(String.valueOf(Integer.parseInt(campaignStats.getBlocks())+Integer.parseInt(metricsObj.getString("blocks"))));
						campaignStatsDTO.setBounceDrops(String.valueOf(Integer.parseInt(campaignStats.getBounceDrops())+Integer.parseInt(metricsObj.getString("bounce_drops"))));
						campaignStatsDTO.setBounces(String.valueOf(Integer.parseInt(campaignStats.getBounces())+Integer.parseInt(metricsObj.getString("bounces"))));
						campaignStatsDTO.setClicks(String.valueOf(Integer.parseInt(campaignStats.getClicks())+Integer.parseInt(metricsObj.getString("clicks"))));
						campaignStatsDTO.setDeferred(String.valueOf(Integer.parseInt(campaignStats.getDeferred())+Integer.parseInt(metricsObj.getString("deferred"))));
						campaignStatsDTO.setInvalidEmails(String.valueOf(Integer.parseInt(campaignStats.getInvalidEmails())+Integer.parseInt(metricsObj.getString("invalid_emails"))));
						campaignStatsDTO.setProcessed(String.valueOf(Integer.parseInt(campaignStats.getProcessed())+Integer.parseInt(metricsObj.getString("processed"))));
						campaignStatsDTO.setRequest(String.valueOf(Integer.parseInt(campaignStats.getRequest())+Integer.parseInt(metricsObj.getString("requests"))));
						campaignStatsDTO.setSpamReportDrops(String.valueOf(Integer.parseInt(campaignStats.getSpamReportDrops())+Integer.parseInt(metricsObj.getString("spam_report_drops"))));
						campaignStatsDTO.setSpamReports(String.valueOf(Integer.parseInt(campaignStats.getSpamReports())+Integer.parseInt(metricsObj.getString("spam_reports"))));
						campaignStatsDTO.setUnsubscribeDrops(String.valueOf(Integer.parseInt(campaignStats.getUnsubscribeDrops())+Integer.parseInt(metricsObj.getString("unsubscribe_drops"))));
						SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
						String campaignDate = newFormat.format(campaignStats.getCampaignDate());
						campaignStatsDTO.setCreatedDate(campaignDate);
						
					}
					
			}
				return campaignStatsDTO;
			}
		}else {
			Campaign campaign = new Campaign();
			campaign.setCampaignCategory(campaignName);
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			String response = campaignUtil.readCampaignStatsByCategory(campaign,currentDate);
			if(response !=null) {
				CampaignStatsDTO campaignStatsDto = new CampaignStatsDTO();
				JSONArray jsonArr = new JSONArray(response);
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					JSONArray statJsonArr = jsonObj.getJSONArray("stats");
					campaignStatsDto.setCreatedDate(jsonObj.getString("date"));
					for (int j = 0; j < statJsonArr.length(); j++) {
						JSONObject statsObj = statJsonArr.getJSONObject(j);
						JSONObject metricsObj = statsObj.getJSONObject("metrics");
//						campaignStatsListDTO.setId(campaignStats.getId());
//						campaignStatsListDTO.setCampaignId(campaignStats.getCampaignId());
						campaignStatsDto.setCategoryName(metricsObj.getString("delivered"));
						campaignStatsDto.setDelivered(metricsObj.getString("delivered"));
						campaignStatsDto.setUniqueOpens(metricsObj.getString("unique_opens"));
						campaignStatsDto.setUniqueClicks(metricsObj.getString("unique_clicks"));
						campaignStatsDto.setUnsubscribes(metricsObj.getString("unsubscribes"));
						campaignStatsDto.setOpens(metricsObj.getString("opens"));
//						campaignStatsListDTO.setCampaignId(campaignStats.getCampaignId());
						campaignStatsDto.setBlocks(metricsObj.getString("blocks"));
						campaignStatsDto.setBounceDrops(metricsObj.getString("bounce_drops"));
						campaignStatsDto.setBounces(metricsObj.getString("bounces"));
						campaignStatsDto.setClicks(metricsObj.getString("clicks"));
						campaignStatsDto.setDeferred(metricsObj.getString("deferred"));
						campaignStatsDto.setInvalidEmails(metricsObj.getString("invalid_emails"));
						campaignStatsDto.setProcessed(metricsObj.getString("processed"));
						campaignStatsDto.setRequest(metricsObj.getString("requests"));
						campaignStatsDto.setSpamReportDrops(metricsObj.getString("spam_report_drops"));
						campaignStatsDto.setSpamReports(metricsObj.getString("spam_reports"));
						campaignStatsDto.setUnsubscribeDrops(metricsObj.getString("unsubscribe_drops"));
//						SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
//						String campaignDate = newFormat.format(campaignStats.getCampaignDate());
						
						
					}
					
			}
				return campaignStatsDto;
			}
		}
		
		return null;
		
	}
	
	/**
	 * checking campaign duplicate or not
	 * 
	 * @param orgLocationId
	 * @param request
	 * @return List<CampaignStatsDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateCampaignSubject")
	public boolean validateCampaignSubject(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "campaignName", required = false) String subjectName, @RequestParam(value = "campaignType", required = false) int campaignType, HttpServletRequest request) throws Exception
	{
		return campaignService.validateCampaignSubject(subjectName, orgLocationId,campaignType);
	}
	
	
	
	/**
	 * Deleteing  campaign stats
	 * 
	 * @param orgLocationId
	 * @param request
	 * @return List<CampaignStatsDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteStats")
	public boolean deleteStats(@RequestParam(value = "categoryName", required = false) String categoryName, HttpServletRequest request) throws Exception
	{
		return campaignService.deleteStats(categoryName);
	}
	
	
	/**
	 * Check the From/Reply Mail is duplicate or not
	 * 
	 * @param nickName
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateFromMail", method = RequestMethod.GET)
	public boolean validateFromMail(@RequestParam(value = "email", required = false) String email, 
									@RequestParam(value = "toWhom", required = false) String toWhom, 
									@RequestParam(value = "id", required = false) String id,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isDuplicate = campaignService.validateFromMail(email,toWhom,id);
		return isDuplicate;
	}
}
