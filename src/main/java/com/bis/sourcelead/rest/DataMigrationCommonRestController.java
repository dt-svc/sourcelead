package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dto.DataMigrationCommonDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UploadXlsHistoryService;
import com.bis.sourcelead.util.CommonUtil;
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
			uploadXlsHistoryDTOList = uploadXlsHistoryService.getUploadXlsHistoryListByUserId(user.getId(), orgLocations.getId(), entityType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadXlsHistoryDTOList;
	}
	
}
