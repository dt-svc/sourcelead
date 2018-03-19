package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
public class InviteCompanyRestController {

	private static final Logger logger = LoggerFactory.getLogger(InviteCompanyRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	public InviteCompanyService inviteCompanyService;
	
	@Autowired
	 TimeZoneService timeZoneService;
	
	
	 @Autowired
	 CommonUtil commonUtil;
	

    @RequestMapping(value = "/storeInviteCompany", method = RequestMethod.POST)
	public @ResponseBody  void storeInviteCompany(InviteCompanyDTO inviteCompanyDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	  String inviteCompany =  inviteCompanyService.saveInviteCompany(inviteCompanyDTO);
	}
   
    @RequestMapping(value = "/storeScheduleMaintenance", method =RequestMethod.POST)
	public @ResponseBody void storeScheduleMaintenance(ScheduleMaintenanceDTO maintenanceDTO,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	inviteCompanyService.storeScheduleMaintenance(maintenanceDTO);
	}
    
    @RequestMapping(value="/loadScheduleMaintenance")
	public ModelAndView loadScheduleMaintenance(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/scheduleMaintenance");
		return model;
	}
    @RequestMapping(value="/getScheduleMaintenanceList")
	public @ResponseBody   ResponseList<ScheduleMaintenanceDTO> getScheduleMaintenanceList(ScheduleMaintenanceDTO scheduleMaintenanceDTO,@RequestParam(value = "searchValue", required = false) String searchValue,HttpServletRequest request) {
    	ResponseList<ScheduleMaintenanceDTO> maintenanceDTOs = null;
    	try {
    		String[] tableColumns = {"startDate", "endDate", "scheduleMessage",""};
		    int sortColumnIndex = 3; //by default sorting using updated date
			String sortColumnOrder = "desc"; //by default descending order
			if(scheduleMaintenanceDTO.getOrder() != null && !scheduleMaintenanceDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(scheduleMaintenanceDTO.getOrder().get(0).get("column"));
				sortColumnOrder = scheduleMaintenanceDTO.getOrder().get(0).get("dir");
			}
			scheduleMaintenanceDTO.setSortColumn(tableColumns[sortColumnIndex]);
			scheduleMaintenanceDTO.setSortOrder(sortColumnOrder);
			maintenanceDTOs = inviteCompanyService.getScheduleMaintenanceList(scheduleMaintenanceDTO, searchValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return maintenanceDTOs;
	}
    
    @RequestMapping(value="/deleteScheduleMaintenance")
   	public void deleteScheduleMaintenance(@RequestParam(value = "id", required = false) String id,HttpServletRequest request) {
    	inviteCompanyService.deleteScheduleMaintenance(id);
    }
       
}
