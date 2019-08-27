package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ResponseList;
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
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		int start = dayWiseTimesheetReoprtsDto.getStart();
		int length = 0;
		if(dayWiseTimesheetReoprtsDto.getPageNo()<1) {
			dayWiseTimesheetReoprtsDto.setPageNo(1);
		}
		if(dayWiseTimesheetReoprtsDto.getPageSize()<1) {
			length = 10;
			dayWiseTimesheetReoprtsDto.setPageSize(10);
		}else {
			length = dayWiseTimesheetReoprtsDto.getPageSize();
		}
		dayWiseTimesheetReoprtsDto.setStart(start);
		dayWiseTimesheetReoprtsDto.setLength(length);
		
		ResponseList<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getDayEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser,isGlobalUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto.getData());
	    if(listDto != null && listDto.getData() != null && !listDto.getData().isEmpty()) {
	    	for (DayWiseTimesheetReoprtsListDTO totalHours : listDto.getData()) {
				model.addObject("totalBillable",totalHours.getBillableTotalCount());
				model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
			}
	    }
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("timesheet/dayEntriesReportList");
		model.addObject("totalRows",listDto.getRecordsFiltered());
		model.addObject("pageCount",dayWiseTimesheetReoprtsDto.getPageNo());
		model.addObject("pageSize",dayWiseTimesheetReoprtsDto.getLength());
		model.addObject("searchValue",dayWiseTimesheetReoprtsDto.getSearchValue());
		return model;
	}
	
	
	
	@RequestMapping(value="/projectTimeEntriesReportList",method= RequestMethod.POST)
	public ModelAndView projectTimeEntriesReportList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		int start = dayWiseTimesheetReoprtsDto.getStart();
		int length = 0;
		if(dayWiseTimesheetReoprtsDto.getPageNo()<1) {
			dayWiseTimesheetReoprtsDto.setPageNo(1);
		}
		if(dayWiseTimesheetReoprtsDto.getPageSize()<1) {
			length = 10;
			dayWiseTimesheetReoprtsDto.setPageSize(10);
		}else {
			length = dayWiseTimesheetReoprtsDto.getPageSize();
		}
		dayWiseTimesheetReoprtsDto.setStart(start);
		dayWiseTimesheetReoprtsDto.setLength(length);
		
		ResponseList<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getProjectTimeEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser,isGlobalUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto.getData());
	    if(listDto != null && listDto.getData() != null && !listDto.getData().isEmpty()) {
	    	for (DayWiseTimesheetReoprtsListDTO totalHours : listDto.getData()) {
				model.addObject("totalBillable",totalHours.getBillableTotalCount());
				model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
			}
	    }
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("timesheet/projectEntriesReportList");
		model.addObject("totalRows",listDto.getRecordsFiltered());
		model.addObject("pageCount",dayWiseTimesheetReoprtsDto.getPageNo());
		model.addObject("pageSize",dayWiseTimesheetReoprtsDto.getLength());
		model.addObject("searchValue",dayWiseTimesheetReoprtsDto.getSearchValue());
		return model;
	}
	
	
	
	@RequestMapping(value="/resourceTimeEntriesReportList",method= RequestMethod.POST)
	public ModelAndView resourceTimeEntriesReportList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		int start = dayWiseTimesheetReoprtsDto.getStart();
		int length = 0;
		if(dayWiseTimesheetReoprtsDto.getPageNo()<1) {
			dayWiseTimesheetReoprtsDto.setPageNo(1);
		}
		if(dayWiseTimesheetReoprtsDto.getPageSize()<1) {
			length = 10;
			dayWiseTimesheetReoprtsDto.setPageSize(10);
		}else {
			length = dayWiseTimesheetReoprtsDto.getPageSize();
		}
		dayWiseTimesheetReoprtsDto.setStart(start);
		dayWiseTimesheetReoprtsDto.setLength(length);
		
		ResponseList<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getResourceTimeEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser,isGlobalUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto.getData());
	    if(listDto != null && listDto.getData() != null && !listDto.getData().isEmpty()) {
	    	for (DayWiseTimesheetReoprtsListDTO totalHours : listDto.getData()) {
				model.addObject("totalBillable",totalHours.getBillableTotalCount());
				model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
			}
	    }
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("timesheet/resourceEntriesReportList");
		model.addObject("totalRows",listDto.getRecordsFiltered());
		model.addObject("pageCount",dayWiseTimesheetReoprtsDto.getPageNo());
		model.addObject("pageSize",dayWiseTimesheetReoprtsDto.getLength());
		model.addObject("searchValue",dayWiseTimesheetReoprtsDto.getSearchValue());
		return model;
	}
	
}
