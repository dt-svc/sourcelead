package com.bis.sourcelead.web;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DayEntryReportsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping(value="/app")
public class DayEntriesReportController {

	@Autowired
	private DayEntryReportsService dayEntryReportsService;
	
	
	@RequestMapping(value="/dayEntriesReportList",method= RequestMethod.POST)
	public ModelAndView getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Map<List<DayWiseTimesheetReoprtsListDTO>, String[]>  listDto = dayEntryReportsService.getDayEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser) ;
		for (Entry<List<DayWiseTimesheetReoprtsListDTO>, String[]> list : listDto.entrySet()) {
			model.addObject("dayWiseTimesheetReoprtsList",list.getKey());
		}
		for (String[] totalHours : listDto.values()) {
			model.addObject("totalBillable",totalHours[0]);
			model.addObject("totalNonBillable",totalHours[1]);
		}
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.setViewName("timesheet/dayEntriesReportList");
		return model;
	}
	
}
