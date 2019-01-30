package com.bis.sourcelead.immigration.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dto.LcaReportViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsPdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ImmigrationReportRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private ImmigrationTemplateService immigrationTemplateService;

	@Autowired
	private ImmigrationCommonService immigrationCommonService;

	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	ReportsPdfUtil reportsPdfUtil;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ImmigrationReportRestController.class);
    
	
	
	/**
	 * Returning Petition Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/petitionReport")
	public ModelAndView petitionReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/immigration/petitionReport");
		List<LookUpData> petitionTypeList = immigrationTemplateService.getLookupDataByType(Constants.VISA);
		model.addObject("petitionTypeList", petitionTypeList);
		 
		List<LookUpData> petitionStatusList = immigrationTemplateService.getLookupDataByType(ImmigrationConstants.PETITION_STATUS);
		model.addObject("petitionStatusList", petitionStatusList);
		
		return model;
	}
	
	/**
	 * @author Prasad Korasikha
	 * 
	 * this method is used to run the Immigration report
	 * @param immigrationReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/runImmigrationReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<ImmigrationReportViewDTO> runImmigrationReport(ImmigrationReportViewDTO immigrationReportViewDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<ImmigrationReportViewDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = {"generatedId", "firstName", "email", "phone","petitionType","country", "onDate",  "attorneyName","lcaStartDate","lcaEndDate","status"};
				
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(immigrationReportViewDTO.getOrder() != null && !immigrationReportViewDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(immigrationReportViewDTO.getOrder().get(0).get("column"));
					sortColumnOrder = immigrationReportViewDTO.getOrder().get(0).get("dir");
				}
				immigrationReportViewDTO.setSortColumn(tableColumns[sortColumnIndex]);
				immigrationReportViewDTO.setSortOrder(sortColumnOrder);
				Date[] dates = new Date[2];
				if("Custom".equalsIgnoreCase(immigrationReportViewDTO.getDateFrequency())) {
					dates[0] = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationReportViewDTO.getFreqStartDate());
					dates[1] = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationReportViewDTO.getFreqEndDate());
				}else {
					dates = reportsUtil.getJobFilterDate(immigrationReportViewDTO.getDateFrequency(), orgLocations);
				}
				immigrationReportViewDTO.setFreqDates(dates);
			    responseList = immigrationCommonService.getImmigrationReports(immigrationReportViewDTO,orgLocations);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
				
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return responseList;
}
	
	@RequestMapping(value = { "/exportPetitionReportPdf"})
	public void exportPetitionReportPdf(ImmigrationReportViewDTO immigrationReportViewDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<ImmigrationReportViewDTO> responseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = {"generatedId", "firstName", "email", "phone","petitionType","country", "onDate",  "attorneyName","lcaStartDate","lcaEndDate","status"};
				
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(immigrationReportViewDTO.getOrder() != null && !immigrationReportViewDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(immigrationReportViewDTO.getOrder().get(0).get("column"));
					sortColumnOrder = immigrationReportViewDTO.getOrder().get(0).get("dir");
				}
				immigrationReportViewDTO.setSortColumn(tableColumns[sortColumnIndex]);
				immigrationReportViewDTO.setSortOrder(sortColumnOrder);
				Date[] dates = new Date[2];
				if("Custom".equalsIgnoreCase(immigrationReportViewDTO.getDateFrequency())) {
					dates[0] = commonUtil.getDateFormat(immigrationReportViewDTO.getFreqStartDate());
					dates[1] = commonUtil.getDateFormat(immigrationReportViewDTO.getFreqEndDate());
				}else {
					dates = reportsUtil.getJobFilterDate(immigrationReportViewDTO.getDateFrequency(), orgLocations);
				}
				immigrationReportViewDTO.setFreqDates(dates);
			    responseList = immigrationCommonService.getImmigrationReports(immigrationReportViewDTO,orgLocations);
			    
			    File file = null;
			    if(immigrationReportViewDTO.getExportType().equalsIgnoreCase("pdf")) {
			    	 file=  reportsPdfUtil.immigrationPerformancePdfExport(responseList,orgLocations);
			    }else {
			    	 file=  reportsPdfUtil.immigrationPerformanceExcelExport(responseList,orgLocations);
			    }
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
				if(file!= null){
					file.delete();
				}
			
	
      }
			
}
	
	/**
	 * Returning LCA Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/lcaReport")
	public ModelAndView lcaReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/lcaReport");
		return model;
	}
	/**
	 * @author Prasad Korasikha
	 * 
	 * this method is used to run the LCA report
	 * @param immigrationReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/runLCAReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<LcaReportViewDTO> runLCAReport(LcaReportViewDTO lcaReportViewDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "filterByDays", required = false) String filterByDays,
			@RequestParam(value = "availability", required = false) String availability,
            HttpServletRequest request) throws Exception
	{
		ResponseList<LcaReportViewDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = { "lcaNo", "lcaTitle", "noOfPositions", "location", "socCode", "socOccupationTitle", "jobTitle", "wagesLevel", "privilingWageCurrency", "prevailingWage", "privilingWageRateType", "startDate", "endDate", "lcaStatus", "status", "expiredOn"};// should be
				
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(lcaReportViewDTO.getOrder() != null && !lcaReportViewDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(lcaReportViewDTO.getOrder().get(0).get("column"));
					sortColumnOrder = lcaReportViewDTO.getOrder().get(0).get("dir");
				}
				
				Map<String, Long[]> filterVal = new HashMap<String, Long[]>();
				
				lcaReportViewDTO.setSortColumn(tableColumns[sortColumnIndex]);
				lcaReportViewDTO.setSortOrder(sortColumnOrder);
	            responseList = immigrationCommonService.getLcaReportsList(lcaReportViewDTO, lcaReportViewDTO.getStatus().toString(), lcaReportViewDTO.getLcaStatus(), searchValue, orgLocations, filterVal, filterByDays,availability);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
				
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return responseList;
	

}
	
	
	@RequestMapping(value = { "/exportLcaReportPdf"})
	public void exportLcaReportPdf(LcaReportViewDTO lcaReportViewDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "filterByDays", required = false) String filterByDays,
			@RequestParam(value = "availability", required = false) String availability,
            HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ResponseList<LcaReportViewDTO> responseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = { "lcaNo", "lcaTitle","noOfPositions", "location", "socCode","socOccupationTitle","jobTitle", "wagesLevel","privilingWageCurrency","prevailingWage","privilingWageRateType","startDate", "endDate", "lcaStatus","status","expiredOn"};// should be
				
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(lcaReportViewDTO.getOrder() != null && !lcaReportViewDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(lcaReportViewDTO.getOrder().get(0).get("column"));
					sortColumnOrder = lcaReportViewDTO.getOrder().get(0).get("dir");
				}
				Map<String, Long[]> filterVal = new HashMap<String, Long[]>();
				
				lcaReportViewDTO.setSortColumn(tableColumns[sortColumnIndex]);
				lcaReportViewDTO.setSortOrder(sortColumnOrder);
				
				responseList = immigrationCommonService.getLcaReportsList(lcaReportViewDTO, lcaReportViewDTO.getStatus().toString(), lcaReportViewDTO.getLcaStatus(), searchValue, orgLocations, filterVal, filterByDays,availability);
			    
			    File file = null;
			    if(lcaReportViewDTO.getExportType().equalsIgnoreCase("pdf")) {
			    	 file=  reportsPdfUtil.lcaPerformancePdfExport(responseList, orgLocations);
			    }else {
			    	 file=  reportsPdfUtil.lcaPerformanceExcelExport(responseList,orgLocations);
			    }
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
				if(file!= null){
					file.delete();
				}
			
	
      }
}

	/**
	 * Returning Petition Attorney Report Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/petitionAttorneyReport")
	public ModelAndView immigAttorneyReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/petitionAttorneyReport");
		
		List<LookUpData> petitionTypeList = immigrationTemplateService.getLookupDataByType(Constants.VISA);
		model.addObject("petitionTypeList", petitionTypeList);
		 
		List<LookUpData> petitionStatusList = immigrationTemplateService.getLookupDataByType(ImmigrationConstants.PETITION_STATUS);
		model.addObject("petitionStatusList", petitionStatusList);
		
		return model;
	}
	
	
	/**
	 * @author Prasad Korasikha
	 * 
	 * this method is used to run the Immigration Attorney report
	 * @param immigrationReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/runImmigrationAttorneyReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<ImmigrationReportViewDTO> runImmigrationAttorneyReport(ImmigrationReportViewDTO immigrationReportViewDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<ImmigrationReportViewDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = {"generatedId", "petitionType", "clientOrgName", "firstName", "email", "phone"};
				
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(immigrationReportViewDTO.getOrder() != null && !immigrationReportViewDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(immigrationReportViewDTO.getOrder().get(0).get("column"));
					sortColumnOrder = immigrationReportViewDTO.getOrder().get(0).get("dir");
				}
				immigrationReportViewDTO.setSortColumn(tableColumns[sortColumnIndex]);
				immigrationReportViewDTO.setSortOrder(sortColumnOrder);
				Date[] dates = new Date[2];
				if("Custom".equalsIgnoreCase(immigrationReportViewDTO.getDateFrequency())) {
					dates[0] = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationReportViewDTO.getFreqStartDate());
					dates[1] = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationReportViewDTO.getFreqEndDate());
				}else {
					dates = reportsUtil.getJobFilterDate(immigrationReportViewDTO.getDateFrequency(), orgLocations);
				}
				immigrationReportViewDTO.setFreqDates(dates);
			    responseList = immigrationCommonService.getImmigrationAttorneyReports(immigrationReportViewDTO,orgLocations);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
				
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return responseList;
}
	
	
	@RequestMapping(value = { "/exportPetitionAttorneyReportPdf"})
	public void exportPetitionAttorneyReportPdf(ImmigrationReportViewDTO immigrationReportViewDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<ImmigrationReportViewDTO> responseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = {"generatedId", "petitionType", "vendor", "firstName", "email", "phone"};
				
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(immigrationReportViewDTO.getOrder() != null && !immigrationReportViewDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(immigrationReportViewDTO.getOrder().get(0).get("column"));
					sortColumnOrder = immigrationReportViewDTO.getOrder().get(0).get("dir");
				}
				immigrationReportViewDTO.setSortColumn(tableColumns[sortColumnIndex]);
				immigrationReportViewDTO.setSortOrder(sortColumnOrder);
				Date[] dates = new Date[2];
				if("Custom".equalsIgnoreCase(immigrationReportViewDTO.getDateFrequency())) {
					dates[0] = commonUtil.getDateFormat(immigrationReportViewDTO.getFreqStartDate());
					dates[1] = commonUtil.getDateFormat(immigrationReportViewDTO.getFreqEndDate());
				}else {
					dates = reportsUtil.getJobFilterDate(immigrationReportViewDTO.getDateFrequency(), orgLocations);
				}
				immigrationReportViewDTO.setFreqDates(dates);
			    responseList = immigrationCommonService.getImmigrationAttorneyReports(immigrationReportViewDTO,orgLocations);
			    
			    File file = null;
			    if(immigrationReportViewDTO.getExportType().equalsIgnoreCase("pdf")) {
			    	 file=  reportsPdfUtil.immigrationAttorneyPerformancePdfExport(responseList,orgLocations);
			    }else {
			    	 file=  reportsPdfUtil.immigrationAttorneyPerformanceExcelExport(responseList,orgLocations);
			    }
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
				if(file!= null){
					file.delete();
				}
			
	
      }
			
}
	
}	
