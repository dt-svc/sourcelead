package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dto.CandidateBulkUploadDTO;
import com.bis.sourcelead.dto.DataMigrationCommonDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.UploadEntriesDTO;
import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UploadXlsHistoryService;
import com.bis.sourcelead.service.XlsBulkUploadHistroyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 19-Dec-2017
 */
@RestController
@RequestMapping("/app")
public class DataMigrationCommonRestController {
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationCommonRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Value("${static.path}")
	private String staticPath;
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	DataMigrationService dataMigrationService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	UploadXlsHistoryService uploadXlsHistoryService;
	
	@Autowired
	XlsBulkUploadHistroyService xlsBulkUploadHistroyService;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	
	
   @RequestMapping(value = "/uploadCandidateForm")
	public ModelAndView uploadCandidateForm(HttpServletRequest request) {
	 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		
		request.getSession().setAttribute("sessionData",sessionData);
		ModelAndView model = new ModelAndView();
		model.setViewName("upload/uploadCandidateForm");
		model.addObject("sourceleadUrl", sourceleadUrl);
		sessionData.setNav(Constants.Candidates);
		return model;

	}
	 
	
	@RequestMapping(value = {"/dataMigrationCommon","/rest/dataMigrationCommon"}, method = RequestMethod.POST)
	public ModelAndView dataMigrationCommon(DataMigrationCommonDTO dataMigrationCommonDTO , HttpServletRequest request) throws Exception
	{
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		  
		ModelAndView model = new ModelAndView();
		try {
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		dataWithCellId = dataMigrationService.getfileData(ac+dataMigrationCommonDTO.getFilePath(), dataMigrationCommonDTO.getHeaderPosition());
		List<SourceleadFormFields> formFields = null;
		switch(dataMigrationCommonDTO.getUploadType()){  
	    case "jobUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(46l, 1);break;
	    case "projectTasks": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(94l, 1);break;  
	    case "associateUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(161l, 1);break;  
	    case "candidatesUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(73l, 1);break;
	    case "submmissionTemplateUpload": formFields = dataMigrationService.getSubmissionTemplateFormFields(dataMigrationCommonDTO.getRequirementId());break; 
	    }
		
		if("timesheetUpload".equalsIgnoreCase(dataMigrationCommonDTO.getUploadType())) {
			int noOfUnits = 0;
			switch(dataMigrationCommonDTO.getTimesheetMode()){
		    case "BiMonthly": noOfUnits=15;break;  
		    case "Monthly": noOfUnits=31;break;
		    case "MonthlyWeekWise": noOfUnits=6;break;  
		    case "Weekly": noOfUnits=7;break;
		    }
			formFields = new ArrayList<SourceleadFormFields>();
			for(int i=0; i<noOfUnits; i++) {
				SourceleadFormFields field = new SourceleadFormFields();
				if("Weekly".equalsIgnoreCase(dataMigrationCommonDTO.getTimesheetMode())) {
					field.setLabelName(Constants.SHORTWEEKENDS[i]);
				}else if("MonthlyWeekWise".equalsIgnoreCase(dataMigrationCommonDTO.getTimesheetMode())) {
					field.setLabelName(Constants.SHORTWEEKWISEMONTH[i]);
				}else {
					field.setLabelName(Constants.SHORTDAYS[i]);
				}
				formFields.add(field);
			}
		}
		
		List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
		model.addObject("shiftList", shiftList);
		
		model.setViewName("upload/dataImport");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("dataWithCellId", dataWithCellId);
		model.addObject("fields", formFields);
		model.addObject("requirementId", dataMigrationCommonDTO.getRequirementId());
		model.addObject("projectUUID", dataMigrationCommonDTO.getProjectUUID());
		model.addObject("projectClientUUID", dataMigrationCommonDTO.getProjectClientUUID());
		model.addObject("exportFilePath", dataMigrationCommonDTO.getFilePath());
		model.addObject("uploadType", dataMigrationCommonDTO.getUploadType());
		model.addObject("timesheetMode", dataMigrationCommonDTO.getTimesheetMode());
		model.addObject("headerPosition", dataMigrationCommonDTO.getHeaderPosition());
		if(StringUtils.isNotBlank(dataMigrationCommonDTO.getUploadType()) 
				&& dataMigrationCommonDTO.getUploadType().equalsIgnoreCase(ImmigrationConstants.IMMIGRATION_UPLOAD)) {

			model.addObject("columnHeaders", ImmigrationConstants.IMPORT_IMMIGRATION_EXCEL_HEADERS);
			model.addObject("headersNamesMap", ImmigrationConstants.HEADERS_NAMES_MAP);
			model.addObject("headersFieldNamesMap", ImmigrationConstants.HEADERS_FIELD_NAMES_MAP);
		}
		else if(StringUtils.isNotBlank(dataMigrationCommonDTO.getUploadType()) 
				&& dataMigrationCommonDTO.getUploadType().equalsIgnoreCase(ImmigrationConstants.LCA_UPLOAD)) {

			model.addObject("columnHeaders", ImmigrationConstants.IMPORT_LCA_EXCEL_HEADERS);
			model.addObject("headersNamesMap", ImmigrationConstants.LCA_HEADERS_NAMES_MAP);
			model.addObject("headersFieldNamesMap", ImmigrationConstants.LCA_HEADERS_FIELD_NAMES_MAP); // user for Mandatory column labels in UI.
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	
	@RequestMapping(value = {"/candidateUploadFieldMapping","/rest/candidateUploadFieldMapping"}, method = RequestMethod.POST)
	public ModelAndView candidateUploadFieldMapping(DataMigrationCommonDTO dataMigrationCommonDTO , HttpServletRequest request) throws Exception
	{
		  
		ModelAndView model = new ModelAndView();
		try {
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		dataWithCellId = dataMigrationService.getCandidateMappingFileData(ac+dataMigrationCommonDTO.getFilePath(), dataMigrationCommonDTO.getHeaderPosition());
		List<SourceleadFormFields>  formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(73l, 1);
		
		model.setViewName("upload/candidateImportFieldMapping");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("dataWithCellId", dataWithCellId);
		model.addObject("fields", formFields);
		model.addObject("exportFilePath", dataMigrationCommonDTO.getFilePath());
		model.addObject("uploadType", dataMigrationCommonDTO.getUploadType());
		model.addObject("headerPosition", dataMigrationCommonDTO.getHeaderPosition());
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = {"/storeCandidateBulkUploadToCouchbase","/rest/storeCandidateBulkUploadToCouchbase"}, method = RequestMethod.POST)
	public ModelAndView storeCandidateBulkUploadToCouchbase(CandidateBulkUploadDTO candidateBulkUploadDTO, UploadEntriesDTO uploadEntriesDTO, HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String couchbaseQueueKey = dataMigrationService.storeCandidateBulkUploadToCouchbase(candidateBulkUploadDTO,uploadEntriesDTO,request);
		 
		model.setViewName("upload/candidateImportFieldMapping");
		model.addObject("couchbaseQueueKey", couchbaseQueueKey);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = {"/storeCandidateBulkUpload","/rest/storeCandidateBulkUpload"}, method = RequestMethod.POST)
	public void storeCandidateBulkUpload(@RequestParam(value = "couchbaseQueueKey", required = true) String couchbaseQueueKey, HttpServletRequest request) throws Exception
	{
		dataMigrationService.saveCandidateBulkImports(couchbaseQueueKey,request);
	}
	
	
	@RequestMapping(value = {"/getDataByMatchedFields","/rest/getDataByMatchedFields"}, method = RequestMethod.POST)
	public ModelAndView getDataByMatchedFields(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String importStatus = dataMigrationService.validateImportedFormData(dataMigrationDTO, request);
		 
		model.setViewName("upload/dataImport");
		model.addObject("importStatus", importStatus);
		model.addObject("uploadType", dataMigrationDTO.getUploadType());
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/getXlsHistroyByUploadId", method = RequestMethod.POST)
	public @ResponseBody UploadXlsHistoryDTO getXlsHistroyByUploadId(@RequestParam(value = "uploadid", required = true) String uploadid) throws Exception {
		UploadXlsHistoryDTO uploadXlsHistoryDto  = null;
		try {
			uploadXlsHistoryDto = uploadXlsHistoryService.getUploadXlsHistoryByUploadId(uploadid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadXlsHistoryDto;
	}

	
	@RequestMapping(value = "/getXlsProcessingHistroyListByEntityType", method = RequestMethod.POST)
	public @ResponseBody List<UploadXlsHistoryDTO> getXlsProcessingHistroyListByEntityType(@RequestParam(value = "entityType", required = true) String entityType, 
			HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		 List<UploadXlsHistoryDTO> uploadXlsHistoryDTOList  = null;
		try {
			uploadXlsHistoryDTOList = uploadXlsHistoryService.getUploadXlsHistoryListByUserId(user.getId(), orgLocations.getId(), entityType,orgLocations.getDateFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadXlsHistoryDTOList;
	}
	
	@RequestMapping(value = "/getBulkImportsFailureUploadList", method = RequestMethod.POST)
	public @ResponseBody List<XlsBulkUploadHistroyDTO> getBulkImportsFailureUploadList(@RequestParam(value = "entityType", required = true) String entityType, 
			HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 List<XlsBulkUploadHistroyDTO> xlsBulkUploadHistroyDTOList  = null;
		try {
			xlsBulkUploadHistroyDTOList = xlsBulkUploadHistroyService.getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(entityType, Constants.COUCH_DOCUMENT_UPLOAD_FAILURE_TYPE, orgLocations.getId(), orgLocations.getDateFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xlsBulkUploadHistroyDTOList;
	}
	
	
	@RequestMapping(value = "/dumpBulkImportsFailureList", method = RequestMethod.GET)
	public void dumpBulkImportsFailureList(@RequestParam(value = "documentKey", required = true) String documentKey, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		JSONObject jsonObject = userStatsCacheHelper.getBulkImportsFailureList(documentKey);
		Map<String,String> columnsMap = commonUtil.getCandidateBulkImportHeaderMap();
		File file = null;
		Workbook createExcelworkbook =  new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 StringBuilder excelSheet = new StringBuilder();
		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSheet.append(orgLocations.getOrgName()).append("_BULK_UPLOAD_").append(dateFormat.format(new Date())).toString());	
		 Row writeRow = null;
		 int column=0;
		for (Object key : jsonObject.keySet()) {
	        String keyStr = (String)key;
	        if(keyStr.equalsIgnoreCase("failureList")) {
	        	String jsonString = jsonObject.get(keyStr).toString();
	    		JSONArray jsonArray = new JSONArray(jsonString);
	    		
	   		 for (int i = 0; i < jsonArray.length(); i++) {
	   			writeRow = createExcelsheet.createRow(column+1);
	   			 JSONParser parser = new JSONParser();
	   			 JSONObject jsonObjVal = (JSONObject) parser.parse(jsonArray.get(i).toString());
	   			 int j=0;
	   			 for (Object okey : jsonObjVal.keySet()) {
	   				 String failurekeyStr = (String) okey;
	   				 if(!failurekeyStr.equalsIgnoreCase("errorMsg")) {
	   					 String keyvalue = jsonObjVal.get(failurekeyStr).toString();
	   					 JSONObject errorJson = userStatsCacheHelper.getCandidateDocument(keyvalue);
	   					
	   					 for (Object errdockey : errorJson.keySet()) {
	   						 String docKeyStr = (String) errdockey;
	   						StringBuilder uploadedkeyvalue = new StringBuilder();
	   							 
	   						if(columnsMap.containsKey(docKeyStr)) {
	   							String headerColumn = columnsMap.get(docKeyStr);
	   							if(errorJson.get(docKeyStr) != null) {
	   								Object dockeyvalue = errorJson.get(docKeyStr);
	   								uploadedkeyvalue = uploadedkeyvalue.append(headerColumn).append(": ").append(dockeyvalue.toString());
			   						writeRow.createCell(j).setCellValue(uploadedkeyvalue.toString()); 
			   						j++;
	   							 }else {
	   								uploadedkeyvalue = uploadedkeyvalue.append(headerColumn).append(": ").append("");
	   								writeRow.createCell(j).setCellValue(uploadedkeyvalue.toString()); 
			   						j++;
	   							 }
	   						}
	   				
	   					 }
	   					 
	   				 }else {
	   					 String keyvalue = jsonObjVal.get(failurekeyStr).toString();
	   					 JSONObject jsonObjErrorVal = (JSONObject) parser.parse(keyvalue);
	   					 StringBuilder errorList = new StringBuilder();
	   					 for (Object errorkey : jsonObjErrorVal.keySet()) {
	   						 String errkeyStr = (String) errorkey;
	   						 String errkeyvalue = jsonObjErrorVal.get(errkeyStr).toString();
	   						 errorList = errorList.append(errkeyvalue).append(",");
	   					 }
	   					 
	   					String errorMassages = errorList.deleteCharAt(errorList.length()-1).toString();
	   					StringBuilder failureReasons = new StringBuilder();
	   					failureReasons = failureReasons.append("Failure Reasons : ").append(errorMassages);
	   					writeRow.createCell(j).setCellValue(failureReasons.toString());
	   				 }
	   				
	   			 }
	   			 column++;
	            }
	        }
	        
	    }
		 StringBuilder orgNameSB = new StringBuilder();
		 file = File.createTempFile(orgNameSB.append(orgLocations.getOrgName()).append("_BULK_UPLOAD_").append(dateFormat.format(new Date())).toString(),".xls");
		 file.setReadable(true);
			 FileOutputStream fos = new FileOutputStream(file);
			 try {
				 createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	 	
			 
			/* String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);

				StringBuilder filename = new StringBuilder();
				response.setHeader("Content-Disposition",
						filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
				createExcelworkbook.close();
				if (file != null) {
					file.delete();
				}
				*/
				
				response.setContentType("application/octet-stream");
				StringBuilder fileName = new StringBuilder();
				response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"")
						.append(file.getName()).append("\"").toString());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				OutputStream outStream = null;
				try {
					 outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}
					createExcelworkbook.close();
					inputStream.close();
					outStream.flush();
					outStream.close();
					
					
				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
				
	}
	
}
