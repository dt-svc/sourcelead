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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
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
	public ModelAndView dataMigrationCommon(@RequestParam(value = "filePath", required = false) String filePath, HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		commonUtil.highLightMenu(Constants.JOBS, request);
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		dataWithCellId = dataMigrationService.getfileData(ac+filePath);
		List<SourceleadFormFields> formFields = dataMigrationService.getfieldsBySectionIdAndImportStatus(3l, 1);
		model.setViewName("upload/importJobs");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("dataWithCellId", dataWithCellId);
		model.addObject("fields", formFields);
		model.addObject("exportFilePath", filePath);
		return model;
	}
	
	
	@RequestMapping(value = {"/getDataByMatchedFields","/rest/getDataByMatchedFields"}, method = RequestMethod.POST)
	public ModelAndView getDataByMatchedFields(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception
	{
		dataMigrationService.validateImportedFormData(dataMigrationDTO, request);
		
		return null;
	}
	
}
