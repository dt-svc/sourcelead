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
		
		for(Campaign campaign : campaignList) {
			OrgLocations orgLocation = orgLocationService.getOrgLocations(campaign.getOrgLocation().getId());
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			String response = campaignUtil.readCampaignStatsByCategory(campaign,currentDate);
			System.out.println("response :: "+response);
			if(StringUtils.isNotBlank(response)) {
				CampaignStats campaignStats  = null;
				JSONArray jsonArr = new JSONArray(response);
//				String campaignName = "";
				Date date = campaign.getCreatedDate();
				campaignStats = campaignService.getCampaignStatsByCampaignId(campaign.getCampaignId());
				if(campaignStats !=null) {
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject jsonObj = jsonArr.getJSONObject(i);
						JSONArray statJsonArr = jsonObj.getJSONArray("stats");
						for (int j = 0; j < statJsonArr.length(); j++) {
							JSONObject statsObj = statJsonArr.getJSONObject(j);
							JSONObject metricsObj = statsObj.getJSONObject("metrics");
//							if(campaignStats !=null) {
								campaignStats.setBlocks(String.valueOf(Integer.parseInt(campaignStats.getBlocks()) + Integer.parseInt(metricsObj.getString("blocks"))));
								campaignStats.setBounceDrops(String.valueOf( Integer.parseInt(campaignStats.getBounceDrops()) + Integer.parseInt(metricsObj.getString("bounce_drops"))));
								campaignStats.setBounces(String.valueOf(Integer.parseInt(campaignStats.getBounces()) + Integer.parseInt(metricsObj.getString("bounces"))));
								campaignStats.setClicks(String.valueOf(Integer.parseInt(campaignStats.getClicks()) + Integer.parseInt(metricsObj.getString("clicks"))));
								campaignStats.setDeferred(String.valueOf(Integer.parseInt(campaignStats.getDeferred()) + Integer.parseInt(metricsObj.getString("deferred"))));
								campaignStats.setDelivered(String.valueOf(Integer.parseInt(campaignStats.getDelivered()) + Integer.parseInt(metricsObj.getString("delivered"))));
								campaignStats.setInvalidEmails(String.valueOf( Integer.parseInt(campaignStats.getInvalidEmails()) + Integer.parseInt(metricsObj.getString("invalid_emails"))));
								campaignStats.setOpens(String.valueOf(Integer.parseInt(campaignStats.getOpens()) + Integer.parseInt(metricsObj.getString("opens"))));
								campaignStats.setProcessed(String.valueOf(Integer.parseInt(campaignStats.getProcessed()) + Integer.parseInt(metricsObj.getString("processed"))));
								campaignStats.setRequest(String.valueOf(Integer.parseInt(campaignStats.getRequest()) + Integer.parseInt(metricsObj.getString("requests"))));
								campaignStats.setSpamReportDrops(String.valueOf(Integer.parseInt(campaignStats.getSpamReportDrops()) + Integer.parseInt(metricsObj.getString("spam_report_drops"))));
								campaignStats.setSpamReports(String.valueOf(Integer.parseInt(campaignStats.getSpamReports()) + Integer.parseInt(metricsObj.getString("spam_reports"))));
								campaignStats.setUniqueClicks(String.valueOf(Integer.parseInt(campaignStats.getUniqueClicks()) + Integer.parseInt(metricsObj.getString("unique_clicks"))));
								campaignStats.setUniqueOpens(String.valueOf(Integer.parseInt(campaignStats.getUniqueOpens()) + Integer.parseInt(metricsObj.getString("unique_opens"))));
								campaignStats.setUnsubscribeDrops(String.valueOf(Integer.parseInt(campaignStats.getUnsubscribeDrops()) + Integer.parseInt(metricsObj.getString("unsubscribe_drops"))));
								campaignStats.setUnsubscribes(String.valueOf(Integer.parseInt(campaignStats.getUnsubscribes()) + Integer.parseInt(metricsObj.getString("unsubscribes"))));
						}
						
				}
				}else {
					  campaignStats = new CampaignStats();
					  for (int i = 0; i < jsonArr.length(); i++) {
							JSONObject jsonObj = jsonArr.getJSONObject(i);
							JSONArray statJsonArr = jsonObj.getJSONArray("stats");
							for (int j = 0; j < statJsonArr.length(); j++) {
								JSONObject statsObj = statJsonArr.getJSONObject(j);
								JSONObject metricsObj = statsObj.getJSONObject("metrics");
									    campaignStats = new CampaignStats();
									    campaignStats.setCampaignDate(date);
										campaignStats.setCategoryName(statsObj.getString("name"));
										campaignStats.setCampaignId(campaign.getCampaignId());
										campaignStats.setBlocks(metricsObj.getString("blocks"));
										campaignStats.setBounceDrops(metricsObj.getString("bounce_drops"));
										campaignStats.setBounces(metricsObj.getString("bounces"));
										campaignStats.setClicks(metricsObj.getString("clicks"));
										campaignStats.setDeferred(metricsObj.getString("deferred"));
										campaignStats.setDelivered(metricsObj.getString("delivered"));
										campaignStats.setInvalidEmails(metricsObj.getString("invalid_emails"));
										campaignStats.setOpens(metricsObj.getString("opens"));
										campaignStats.setProcessed(metricsObj.getString("processed"));
										campaignStats.setRequest(metricsObj.getString("requests"));
										campaignStats.setSpamReportDrops(metricsObj.getString("spam_report_drops"));
										campaignStats.setSpamReports(metricsObj.getString("spam_reports"));
										campaignStats.setUniqueClicks(metricsObj.getString("unique_clicks"));
										campaignStats.setUniqueOpens(metricsObj.getString("unique_opens"));
										campaignStats.setUnsubscribeDrops(metricsObj.getString("unsubscribe_drops"));
										campaignStats.setUnsubscribes(metricsObj.getString("unsubscribes"));
										campaignStats.setStatus(1);
										campaignStats.setCreatedDate(currentDate);
										campaignStats.setUpdatedDate(currentDate);
										campaignStats.setOrgLocation(orgLocation);
							}
							
					}
				}
			
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
		ResponseList<CampaignStatsDTO> campaignStatsResList = new ResponseList<>();
		
		List<CampaignStatsDTO> campaignStatsList = new ArrayList<>();
		// SourceLeadSessionManager.getSessionAttribute(request,
		// "orgLocations");
		ResponseList<CampaignStats> campStats = campaignService.getCampaignStatsListByOrgLocationId(orgLocationId, campaignStatsDTO);
		for (CampaignStats campaignStats : campStats.getData()) {
			CampaignStatsDTO campaignStatsListDTO = new CampaignStatsDTO();
			campaignStatsListDTO.setId(campaignStats.getId());
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
		return campaignStatsResList;
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
	public CampaignStatsDTO getCampaignStatsByCampaignId(@RequestParam(value = "campaignId", required = false) String campaignId, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CampaignStats campaignStats = campaignService.getCampaignStatsByCampaignId(campaignId);
		CampaignStatsDTO campaignStatsListDTO = new CampaignStatsDTO();
		campaignStatsListDTO.setId(campaignStats.getId());
		campaignStatsListDTO.setCampaignId(campaignStats.getCampaignId());
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
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		String campaignDate = newFormat.format(campaignStats.getCampaignDate());
		campaignStatsListDTO.setCreatedDate(campaignDate);
		return campaignStatsListDTO;
	}
	
}
