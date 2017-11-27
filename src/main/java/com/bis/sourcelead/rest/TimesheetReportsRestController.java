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
@RequestMapping("/slead")
public class TimesheetReportsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
 	private TimesheetReportService timesheetReportService;
	
	
	@RequestMapping(value = "/timesheetReportsDiv", method = RequestMethod.POST )   
	public @ResponseBody String newTimesheet(HttpServletRequest request,
		   @RequestParam(value="frequencyYear", required=false) String frequencyYear,
		   @RequestParam(value="frequencyMonth", required=false) String frequencyMonth) throws Exception {
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  String  prepareTimeSheetDiv = timesheetReportService.prepareTimeSheetDiv(frequencyYear,frequencyMonth,user,orgLocations);
		return prepareTimeSheetDiv;
	}
	
	@RequestMapping(value = "/timeSheetReportExportList")   
	public @ResponseBody void timeSheetReportExportList(HttpServletRequest request,HttpServletResponse response,
		   @RequestParam(value="frequencyYear", required=false) String frequencyYear,
		   @RequestParam(value="frequencyMonth", required=false) String frequencyMonth,
		   @RequestParam(value="exportType", required=false) String exportType) throws Exception {
		   File timesheetReportFile = null;
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   
		   if(exportType.equalsIgnoreCase("pdf")){
		    timesheetReportFile = timesheetReportService.prepareTimeSheetExport(frequencyYear,frequencyMonth,user,orgLocations);
		   }else{
			timesheetReportFile = timesheetReportService.prepareTimeSheetExportExcel(frequencyYear,frequencyMonth,user,orgLocations);   
		   }
		   String mimeType = URLConnection.guessContentTypeFromName(timesheetReportFile.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			StringBuilder timesheetfileName = new StringBuilder();
			int pos = timesheetReportFile.getName().lastIndexOf("-");
			String result = timesheetReportFile.getName().substring(fileName.length(), pos);
			int index = timesheetReportFile.getName().lastIndexOf(".");
			String fileType = timesheetReportFile.getName().substring(index, timesheetReportFile.getName().length());
			String tsFileName = timesheetfileName.append(result).append(fileType).toString();
			
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(tsFileName).append("\"").toString());
			response.setContentLength((int) timesheetReportFile.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(timesheetReportFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
		   
		  
	}
	
}
