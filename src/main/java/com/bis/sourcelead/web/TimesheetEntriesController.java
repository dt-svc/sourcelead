package com.bis.sourcelead.web;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.SessionMaintainanceData;
 

@Controller 
public class TimesheetEntriesController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	
	// sarath kumar punnana : this  controller method is for timesheet screen redirection.
	@RequestMapping(value = "/timesheet")
	public ModelAndView registration(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.TimeSheet);
			
			request.getSession().setAttribute("sessionData",sessionData);
		 
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheet");
			return model;

	}
	

	//Pavan Jampana : This method is for TimeSheet Pdf Download
	@RequestMapping(value = "/downloadTimesheetPdf", method = RequestMethod.GET)
	public ModelAndView generateAllTimesheetPdf(HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("redirect:/timesheetPdf/downloadPdf");
		return view;
	}
		
	// shivayogi s kadagad controller to download Exceltimesheet
	@RequestMapping(value = "/Excel", method = RequestMethod.GET)
	public ModelAndView getExcel() throws Exception {
		
		Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService .getAllTimesheetEntries(); 		
		return new ModelAndView(timeSheetExcelView,"timesheetEntriesList",timesheetEntriesList);
	}
		
	//sarath kumar punnana : this method is for null check
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
 
}