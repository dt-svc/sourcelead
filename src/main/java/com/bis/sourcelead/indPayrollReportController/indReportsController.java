package com.bis.sourcelead.indPayrollReportController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class indReportsController {

private static final Logger logger = LoggerFactory.getLogger(indReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	TabsService tabsService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
		
		 @RequestMapping(value = "/indPayrollReportList")
			public ModelAndView indPayrollReportList(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollReportList");
			 return model;
		 }
		 
		 
		 @RequestMapping(value = "/indPayrollRegisterReport")
			public ModelAndView indPayrollRegisterReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollRegisterReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollBankTransferReport")
			public ModelAndView indPayrollBankTransferReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollBankTransferReport");
			 return model;
		 }
		 @RequestMapping(value = "/indPayrollYTDSummaryReport")
			public ModelAndView indPayrollYTDSummaryReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollYTDSummaryReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollAnnualTaxReport")
			public ModelAndView indPayrollAnnualTaxReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
             OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollAnnualTaxReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollLoanReport")
			public ModelAndView indPayrollLoanReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollLoanReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollSalaryStatementReport")
			public ModelAndView indPayrollSalaryStatementReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
             OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollSalaryStatementReport");
			 return model;
		 }
		 
		 
		 @RequestMapping(value = "/indPayrollESISummaryReport")
			public ModelAndView indPayrollESISummaryReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
             OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
             List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollESISummaryReport");
			 return model;
		 }
		 
		 @RequestMapping(value = "/indPayrollPFSummaryReport")
			public ModelAndView indPayrollPFSummaryReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
            OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
            List<String> finYearList = iNDPayrollCommonUtil.getFinancialYearByOrgLocation(orgLocation);
			 model.addObject("finYearList", finYearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollPFSummaryReport");
			 return model;
		 }
		 
		 
		 
		 @RequestMapping(value = "/indPayrollSalaryRegisterReport")
			public ModelAndView indPayrollSalaryRegisterReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/indPayrollSalaryRegisterReport");
			 return model;
		 }
}
