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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
public class InviteCompanyRestController {

	private static final Logger logger = LoggerFactory.getLogger(InviteCompanyRestController.class);
	
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
}
