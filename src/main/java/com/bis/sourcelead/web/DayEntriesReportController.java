package com.bis.sourcelead.web;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value="/dayEntriesReportList",method= RequestMethod.POST)
	public ModelAndView getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getDayEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto);
		for (DayWiseTimesheetReoprtsListDTO totalHours : listDto) {
			model.addObject("totalBillable",totalHours.getBillableTotalCount());
			model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
		}
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("timesheet/dayEntriesReportList");
		return model;
	}
	
}
