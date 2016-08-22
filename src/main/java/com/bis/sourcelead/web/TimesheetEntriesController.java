package com.bis.sourcelead.web;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
 

@Controller 
public class TimesheetEntriesController {
	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	@Autowired
	public CommonUtil commonUtil;
	
	
	/**
	 * @author sarath kumar punnana 
	 * this  controller method is for timesheet screen redirection.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/timesheet")
	public ModelAndView registration(HttpServletRequest request) {
			ModelAndView model = new ModelAndView();
			
			commonUtil.highLightMenu(Constants.TimeSheet, request);
		 
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheet");
			return model;

	}

	/**
	 * @author Pavan Jampana
	 * This method is for TimeSheet Pdf Download
	 * @return
	 */
	@RequestMapping(value = "/downloadTimesheetPdf", method = RequestMethod.GET)
	public ModelAndView generateAllTimesheetPdf()  {
		ModelAndView view = new ModelAndView("redirect:/timesheetPdf/downloadPdf");
		return view;
	}
		
	/**
	 * @author shivayogi s kadagad
	 * controller to download Exceltimesheet
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/Excel", method = RequestMethod.GET)
	public ModelAndView getExcel()  {
		
		Collection<TimesheetEntries> timesheetEntriesList = null;
		try {
			timesheetEntriesList = timesheetEntriesService .getAllTimesheetEntries();
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
		} 		
		return new ModelAndView(timeSheetExcelView,"timesheetEntriesList",timesheetEntriesList);
	}
		
	/**
	 * @author sarath kumar punnana
	 * this method is for null check
	 * @param str
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
 
}