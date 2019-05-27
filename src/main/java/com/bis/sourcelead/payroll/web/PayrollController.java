package com.bis.sourcelead.payroll.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.dto.INDPayrollDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.entity.File;
import com.bis.sourcelead.payroll.entity.PayrollFileValidator;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.PayItemService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * 
 * @author 
 *
 */
@Controller
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
	private IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	private AssociatePfEsiService associatePfEsiService;
	
	@Autowired
	PayItemService payItemService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	PayrollPaySlipIndService payrollPaySlipIndService;
	//This method commented by Laxman 
	/*@InitBinder
	private void initBinder(WebDataBinder binder)
	{
		binder.setValidator(validator);
	}*/
	
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
	 * 
	 * Return Payroll Configuration Jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollConfiguration")
	public ModelAndView payrollConfiguration(HttpServletRequest request) throws Exception
	{
		String country = (String) SourceLeadSessionManager.getSessionAttribute(request, "country");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		ModelAndView model = new ModelAndView();
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		// sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		if("India".equalsIgnoreCase(country)) {
			model.setViewName("indPayroll/payrollConfiguration");
		}else if("United States".equalsIgnoreCase(country)) {
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
	
	@RequestMapping(value = "/indPayrollPaySlipView") 
	public ModelAndView indPayrollPaySlipView(HttpServletRequest request,@RequestParam(value="empPayrollUUID", required=false)String empPayrollUUID) throws Exception
	{
		ModelAndView model = new ModelAndView();
		if(empPayrollUUID != null && empPayrollUUID != "") {
			Response<IndEmployeePayroll> indEmployeePayroll  = indPaysheetFrequencyService.getIndEmployeePayrollByEmpPayrollUUID(empPayrollUUID);
			
			//Company Details
			if(indEmployeePayroll.getObject().getIndPaysheetFrequency()   != null) {
				OrgLocations orgLocation = indEmployeePayroll.getObject().getIndPaysheetFrequency().getOrgLocation();
				if(orgLocation.getOrgLocationAddress() != null) {
					model.addObject("companyAddress", orgLocation.getOrgLocationAddress());
				}else {
					StringBuilder companyAddress = new StringBuilder();
					if(orgLocation.getZipCode() != null) {
						companyAddress.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(orgLocation.getCity()).append("-").append(orgLocation.getZipCode());
					}else {
						companyAddress.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(orgLocation.getCity());
					}
					model.addObject("companyAddress", companyAddress);
				}
				model.addObject("companyName", orgLocation.getOrgLocationName());
				if(orgLocation.getOrg().getFileUrl() != null) {
					model.addObject("copanyImage", orgLocation.getOrg().getFileUrl());
				}
			}
			PayrollPaySlipInd ppsi  = indEmployeePayroll.getObject().getPayrollPaySlipInd();
			if(ppsi!= null) {
				model.addObject("ppsi", ppsi);
			}else {
				PayrollPaySlipInd ppsisDefault= payrollPaySlipIndService.deletePermissionStatsuByDeletePermission(Constants.NO, indEmployeePayroll.getObject().getIndPaysheetFrequency().getOrgLocation().getId());
				model.addObject("ppsi", ppsisDefault);
			}
			model.addObject("empPayrollUUID", empPayrollUUID);
			
			//Eploye  ESI & PF Details
			AssociatePfEsiDetails ape = associatePfEsiService.findAssociatePfEsiByCandidateId(indEmployeePayroll.getObject().getCandidate().getId(), indEmployeePayroll.getObject().getCandidate().getOrgLocationId());
			if(ape != null) {
				model.addObject("pfEsi", ape);
			}
			
			Employees employee = indEmployeePayroll.getObject().getEmployee();
			
			Candidate candidate = indEmployeePayroll.getObject().getCandidate();
			model.addObject("candidate",  candidate);
			
			PaymentAccount paymentAccount = orgService.getCandidateAcitivatePaymentAccount(candidate.getId());
			if(paymentAccount != null) {
				model.addObject("paymentAccount",  paymentAccount);
			}
			
			if(employee.getHireDate() != null) {
				model.addObject("joinDate", commonUtil.dateStrFromOrgDateFormatter(employee.getOrgLocations().getDateFormat(), employee.getHireDate()));
			}
			if(employee.getEmployeeId() != null ) {
				model.addObject("employeeId",  employee.getEmployeeId());
			}
			
			
			if(indEmployeePayroll.getObject().getPayCheckDate() != null) {
				SimpleDateFormat month_date = new SimpleDateFormat("MMMM - yyyy");
				String payslipDate = month_date.format(indEmployeePayroll.getObject().getPayCheckDate());
				model.addObject("payslipDate",  payslipDate);
			}
			
			model.addObject("indEmployeePayroll",  indEmployeePayroll.getObject());
			
			//payslip Table data
 		   		HashSet<IndEmployeePayroll> payrollList = new HashSet<>();
		   		payrollList.add(indEmployeePayroll.getObject());
		   		INDPayrollDTO indPayrollDTO = iNDPayrollCommonUtil.getINDPayrollDTOFromIndEmployeePayroll(payrollList, indEmployeePayroll.getObject().getIndPaysheetFrequency().getOrgLocation()).iterator().next();
		   		List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList = indPayrollDTO.getPayHeadHistoryDTOList();
	 		
		   	if(associatePayrollPayheadDTOList != null && !associatePayrollPayheadDTOList.isEmpty()) {
				List<AssociatePayrollPayheadHistoryDTO> earningList = associatePayrollPayheadDTOList
						.stream()
						.filter(app -> (app.getPayrollPayheadType().equals("Earnings") && app.getPaySlip().equals("Y")  && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0)))
						.collect(Collectors.toList());				
				model.addObject("earningPayhead", earningList);
				
				List<AssociatePayrollPayheadHistoryDTO> decuctionList =  associatePayrollPayheadDTOList
						.stream()
						.filter(app -> app.getPayrollPayheadType().equals("Deduction")  && app.getPaySlip().equals("Y") && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0))
						.collect(Collectors.toList());
				model.addObject("decuctionPayhead", decuctionList);
			 }
		   	model.addObject("indPayrollDTO", indPayrollDTO);
			
		   	model.addObject("numbersToWords", commonUtil.convertToIndianCurrency(indPayrollDTO.getNetPay()));
			
		}
		model.setViewName("payroll/indPayrollPaySlipView");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
}
