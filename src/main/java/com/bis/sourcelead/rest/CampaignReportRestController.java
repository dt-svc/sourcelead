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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/console")
public class CampaignReportRestController {
	private static final Logger logger = LoggerFactory.getLogger(CampaignReportRestController.class);
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
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	
	
	
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
	 * returning campaign stats graph
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/campaignStatsGraph")
	public ModelAndView campaignStatsGraph(@RequestParam(value="campaignName",required=false) String campaignName,HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.addObject("campaignName", campaignName);
		   model.setViewName("campaign/campaignStatsGraph");
	       return model;
	}
	
	@RequestMapping(value = "/campaignStats")
	public ModelAndView campaignStats(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("campaign/campaignStats");
	       return model;
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
	
	
}
