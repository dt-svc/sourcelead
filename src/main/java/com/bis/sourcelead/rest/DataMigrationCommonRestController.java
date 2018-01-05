package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DataMigrationCommonDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;

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
	
	@RequestMapping(value = {"/dataMigrationCommon","/rest/dataMigrationCommon"}, method = RequestMethod.POST)
	public ModelAndView dataMigrationCommon(DataMigrationCommonDTO dataMigrationCommonDTO , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		dataWithCellId = dataMigrationService.getfileData(ac+dataMigrationCommonDTO.getFilePath());
		List<SourceleadFormFields> formFields = null;
		
		switch(dataMigrationCommonDTO.getUploadType()){  
	    case "jobUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(46l, 1);break;  
	    //case "timesheetUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(1l, 1);break;  
	    }
		
		if("timesheetUpload".equalsIgnoreCase(dataMigrationCommonDTO.getUploadType())) {
			int noOfUnits = 0;
			switch(dataMigrationCommonDTO.getTimesheetMode()){
		    case "BiMonthly": noOfUnits=15;break;  
		    case "Monthly": noOfUnits=31;break;
		    case "MonthlyWeekWise": noOfUnits=5;break;  
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
		
		model.setViewName("upload/dataImport");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("dataWithCellId", dataWithCellId);
		model.addObject("fields", formFields);
		model.addObject("exportFilePath", dataMigrationCommonDTO.getFilePath());
		model.addObject("uploadType", dataMigrationCommonDTO.getUploadType());
		model.addObject("timesheetMode", dataMigrationCommonDTO.getTimesheetMode());
		return model;
	}
	
	
	@RequestMapping(value = {"/getDataByMatchedFields","/rest/getDataByMatchedFields"}, method = RequestMethod.POST)
	public ModelAndView getDataByMatchedFields(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String importStatus = dataMigrationService.validateImportedFormData(dataMigrationDTO, request);
		
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		dataWithCellId = dataMigrationService.getfileData(ac+dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/"));
		List<SourceleadFormFields> formFields = null;
		switch(dataMigrationDTO.getUploadType()){  
	    case "jobUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(46l, 1);break;  
	    case "timesheetUpload": formFields = dataMigrationService.getfieldsByActionIdAndImportStatus(1l, 1);break;  
	    }
		model.addObject("dataWithCellId", dataWithCellId);
		model.addObject("fields", formFields);
		model.setViewName("upload/dataImport");
		model.addObject("importStatus", importStatus);
		model.addObject("exportFilePath", dataMigrationDTO.getExportFilePath());
		model.addObject("uploadType", dataMigrationDTO.getUploadType());
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
}
