package com.bis.sourcelead.payroll.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.entity.File;
import com.bis.sourcelead.payroll.entity.PayItem;
import com.bis.sourcelead.payroll.entity.PayrollFileValidator;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.PayItemService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author 
 *
 */
@Controller
@RequestMapping("/app")
public class PayrollController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	PayrollFileValidator validator;
	@Autowired
	private UserService userService;
	@Autowired
	private BisUserMappingService bisUserMappingService;
	@Autowired
	private WageTaxSummaryService wageTaxSummaryService;
	
	@Autowired
	PayItemService payItemService;
	
	@InitBinder
	private void initBinder(WebDataBinder binder)
	{
		binder.setValidator(validator);
	}
	
	@RequestMapping(value = "/payrollForm")
	public String getUploadForm(Model model)
	{
		File fileModel = new File();
		model.addAttribute("file", fileModel);
		return "payroll/uploadPayroll";
	}
	
	
	/**
	 * returns user mapping jsp for payroll
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/userMapping")
	public ModelAndView userMapping(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> usersList = userService.getUsersListByOrglocationId(orgLocations.getId());
		usersList.sort((User report1, User report2) -> report1.getFirstName().compareToIgnoreCase(report2.getFirstName()));
		List<WageTaxSummary> wageTaxSummaryList = wageTaxSummaryService.getAllImportedUsers(orgLocations);
		// lambda here!
		wageTaxSummaryList.sort((WageTaxSummary report1, WageTaxSummary report2) -> report1.getEmployeeName().compareToIgnoreCase(report2.getEmployeeName()));
		model.addObject("usersList", usersList);
		model.addObject("masterUsersList", wageTaxSummaryList);
		model.setViewName("payroll/userMapping");
		return model;
	}
	
	/**
	 * @author Pavan Jampana used to redirect to user pay screen
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userPay")
	public ModelAndView getUserPayroll(HttpServletRequest request) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("payroll/userPayroll");
		   
		   SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.PAY);
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			try {
				String empId =  wageTaxSummaryService.getWagesAndTaxEmployeeId(candidateId);
				 model.addObject("employeeId", empId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute("sessionData",sessionData);
			
	       return model;

	}
	
	@RequestMapping(value = "/payrollReport")
	public ModelAndView payrollReport(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<WageTaxSummary> wageTaxSummaryList = wageTaxSummaryService.getUniqueWagesAndTaxListByOrgLocationId(orgLocation);
		if (wageTaxSummaryList != null) {
			model.addObject("wageTaxSummaryList", wageTaxSummaryList);
		}
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/payrollReport");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Returns payroll documents upload jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addChecks")
	public ModelAndView addChecks(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.PAYROLL);
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<WageTaxSummary> wageTaxSummaryList = wageTaxSummaryService.getUniqueWagesAndTaxListByOrgLocationId(orgLocation);
		// List<WageTaxSummary> distinctList =
		// getUniqueWageTaxSummary(wageTaxSummaryList);
		if (wageTaxSummaryList != null) {
			model.addObject("wageTaxSummaryList", wageTaxSummaryList);
		}
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/addChecks");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Returns all payroll balance reports for all employess jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollAllBalanceReport")
	public ModelAndView allPayrollReportsJsp(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/allPayrollReports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Returns search based payroll reports
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchBasedReport")
	public ModelAndView searchBasedReport(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/searchPayrollReport");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	/**
	 * Return Payroll Deduction List Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollDeductionList")
	public ModelAndView payrollDeductionListJsp(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/payrollDeductionList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Return Payroll Contribution List Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollContribution")
	public ModelAndView payrollContributionListJsp(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/payrollContributionList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Return Payroll Add Deduction Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDeduction")
	public ModelAndView addDeductionJsp(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/addDeduction");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Return Payroll Add Contribution Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addContribution")
	public ModelAndView addContributionJsp(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/addContribution");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	/**
	 * Return Payroll Configuration Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollConfiguration")
	public ModelAndView payrollConfiguration(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		if(orgLocations.getCountry().equalsIgnoreCase("India")) {
			model.setViewName("indPayroll/payrollConfiguration");
		}else if(orgLocations.getCountry().equalsIgnoreCase("United States")) {
			model.setViewName("payroll/payrollConfiguration");
		}
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	/**
	 * Return Add Pay Item Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPayItem")
	public ModelAndView addPayItem(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		List<String> empList = Arrays.asList(Constants.EMPLOYMENT_TYPES);
		model.addObject("empList" , empList);
		model.setViewName("payroll/addPayItem");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	/**
	 * View PayItem List
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayItem")
	public ModelAndView viewPayItem(HttpServletRequest request) throws Exception
	{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		request.getSession().setAttribute("sessionData", sessionData);
		model.setViewName("payroll/payItemList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
}
