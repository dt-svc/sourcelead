package com.bis.sourcelead.rest.reportsRestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.service.CustomEmailConfigurationService;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class EmailConfogureReportRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil; 
	
	@Autowired
	CustomEmailConfigurationService customEmailConfigurationService;
	
    @RequestMapping(value = "/customEmailConfigureReport")
	public ModelAndView projectReport(HttpServletRequest request)throws Exception{
		 ModelAndView model = new ModelAndView();
		  model.setViewName("reports/customEmailConfigureReport");
		 return model;
	}

    @RequestMapping(value = "/getCustomEmailConfuguraionReportData", method = RequestMethod.POST)
	public @ResponseBody ResponseList<EmailScenarioConfiguration> getAssetReportList(AssetsReportDTO assetsReportDTO, HttpServletRequest request) throws Exception
	{
		// User user = (User)
		// SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date startTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assetsReportDTO.getFromDate());
//		Date endTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assetsReportDTO.getToDate());
//		String customStartTimeStr = TZ_DF_CSD.format(startTime);
//		String customEndTimeStr = TZ_DF_CED.format(endTime);
//		Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
//		Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
//		assetsReportDTO.setConvertedFromDate(startDateUTC);
//		assetsReportDTO.setConvertedToDate(endDateUTC);
		List<EmailScenarioConfiguration>  list = customEmailConfigurationService.getCustomEmailConfigurationByLocation(orgLocation.getId());
		ResponseList<EmailScenarioConfiguration> response = new ResponseList<EmailScenarioConfiguration>();
		for(EmailScenarioConfiguration cec : list) {
			System.out.println(cec.getOrgLocationId().getOrgLocationName());
		}
		if(list != null) {
			response.setData(list);
		}
		return response;
	}
	
}
