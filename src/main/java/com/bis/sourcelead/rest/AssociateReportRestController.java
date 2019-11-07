package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.reportsDTO.TimeToFillDTO;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RequestMapping("/app")
public class AssociateReportRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private WageTaxSummaryService wageTaxSummaryService;
	
	@Autowired
	private TabsService tabsService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamService teamService;
	
	

}
