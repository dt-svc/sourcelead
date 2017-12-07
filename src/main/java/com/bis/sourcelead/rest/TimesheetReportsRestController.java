package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TimesheetReportService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController 
public class TimesheetReportsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
 	private TimesheetReportService timesheetReportService;
	
	
	//@RequestMapping(value = "/timesheetReportsDiv", method = RequestMethod.POST )   
	public @ResponseBody String newTimesheet(HttpServletRequest request,
		   @RequestParam(value="frequencyYear", required=false) String frequencyYear,
		   @RequestParam(value="frequencyMonth", required=false) String frequencyMonth) throws Exception {
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  String  prepareTimeSheetDiv = timesheetReportService.prepareTimeSheetDiv(frequencyYear,frequencyMonth,user,orgLocations);
		return prepareTimeSheetDiv;
	}
	
	
}
