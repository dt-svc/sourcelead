package com.bis.sourcelead.payroll.rest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.AssociateSalaryHistoryDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UsMyPayrollFormsDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dao.impl.ApproverDTO;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.dto.CommonPayrollDataDTO;
import com.bis.sourcelead.payroll.dto.ConfirmPayTotalSumDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.FilteredClientDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.IntiateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayAdjustmentDTO;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollScheduleCalenderDTO;
import com.bis.sourcelead.payroll.dto.PayrollScheduleDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.RecentPayrollRunDataDTO;
import com.bis.sourcelead.payroll.dto.StaffingFilteredDTO;
import com.bis.sourcelead.payroll.dto.StaffingFilteredDetailsDTO;
import com.bis.sourcelead.payroll.dto.StaffingFilteredReviewsDTO;
import com.bis.sourcelead.payroll.dto.USPayrollAssociateTaxHistoryDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPaysheetDTO;
import com.bis.sourcelead.payroll.dto.VendorRecruitersDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.BankDataHistory;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayItem;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayrollAssociateTaxHistory;
import com.bis.sourcelead.payroll.entity.UsMyPayrollForms;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayItemService;
import com.bis.sourcelead.payroll.service.PayrollAdjustmentService;
import com.bis.sourcelead.payroll.service.PayrollContributionService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.payroll.service.PayrollScheduleService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.service.USPayrollAssociateTaxHistoryService;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.service.UsMyPayrollFormService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.AssociateSalaryHistoryService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class USPayrollRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollRestController.class);
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	USPayrollService uSPayrollService;
	
	@Autowired
	PayrollDeductionService payrollDeductionService;
	
	@Autowired
	PayrollContributionService payrollContributionService;
	
	@Autowired
	AssociateBankDataService associateBankDataService;
	
	@Autowired
	AssociateTaxService associateTaxService;
	
	@Autowired
	AssociateDeductionService associateDeductionService;
	
	@Autowired
	AssociateContributionService associateContributionService;
	
	@Autowired
	AssociateDeductionDao associateDeductionDao;
	
	@Autowired
	AssociateContributionDao associateContributionDao;
	
	@Autowired
	GenerateUSPayrollService generateUSPayrollService;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PayItemService payItemService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;

	@Autowired
	AssociateSalaryService associateSalaryService;

	
	@Autowired
	AssociateSalaryHistoryService associateSalaryHistoryService;
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	PayrollAdjustmentService payrollAdjustmentService; 
	
	@Autowired
	PayrollScheduleService  payrollScheduleService;

	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;
	
	@Autowired
	private USPayPeriodService usPayPeriodService;
	
	@Autowired
	UsMyPayrollFormService usMyPayrollFormService;
	
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AssociateLoanAndAdvancesService associateLoanAndAdvancesService;
    
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private UserGroupDao userGroupDao;
	
	@Autowired
	private PayrollUtil payrollUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	USPayrollAssociateTaxHistoryService usPayrollAssociateTaxHistoryService;

	@Autowired
	private UserRoleService userRoleService;

	
	/**
	 * find all deduction types from lookup_data table 
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllLookUpDeductionTypes","/rest/findAllLookUpDeductionTypes" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<PayrollDeductionDTO> findAllDeduction(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
			return  uSPayrollService.findAllDeduction();	
	}
	
	/**
	 * Add Payroll Deductions 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/savePayrollDeduction","/rest/savePayrollDeduction" }, method = RequestMethod.POST)
	@ResponseBody
	public PayrollDeductionDTO savePayrollDeduction(PayrollDeductionDTO payrollDeductionDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return  payrollDeductionService.savePayrollDeduction(payrollDeductionDTO,orgLocation,user);	
	}
	
	/**
	 * Add Payroll Deductions 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllPayrollDeduction","/rest/findAllPayrollDeduction" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollDeductionDTO> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"payrollDeductionType", "description", "payrollDeductionMethod", "deductionAmount"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollDeductionDTO.getOrder() != null && !payrollDeductionDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollDeductionDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollDeductionDTO.getOrder().get(0).get("dir");
		}
		payrollDeductionDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollDeductionDTO.setSortOrder(sortColumnOrder);
		return  payrollDeductionService.findAllPayrollDeduction(payrollDeductionDTO,orgLocation);	
	}
	
	
	@RequestMapping(value = { "/getAllDeductionType","/rest/getAllDeductionType" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollDeductionDTO> getAllDeductionTypeByLocationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return  payrollDeductionService.getAllDeductionTypeByLocationId(orgLocation);	
	}
	
	/**
	 * Add Payroll Contribution
	 * 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/savePayrollContribution","/rest/savePayrollContribution" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollContributionDTO> savePayrollContribution(PayrollContributionDTO payrollContributionDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return payrollContributionService.savePayrollContribution(payrollContributionDTO, orgLocation, user);
	}
	
	
	/**
	 * Add Payroll Deductions 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllPayrollContribution","/rest/findAllPayrollContribution" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollContributionDTO> findAllPayrollContribution(PayrollContributionDTO payrollContributionDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"", "description","payrollContributionMethod", "contributionAmount", };
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollContributionDTO.getOrder() != null && !payrollContributionDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollContributionDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollContributionDTO.getOrder().get(0).get("dir");
		}
		payrollContributionDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollContributionDTO.setSortOrder(sortColumnOrder);
		return  payrollContributionService.findAllPayrollContribution(payrollContributionDTO,orgLocation);	
	}
	
	/**
	 * Edit Payroll Deduction Type
	 * @param deductionUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDeductionType")
	public ModelAndView editDeductionType(@RequestParam(value="deductionUUID", required=false) String deductionUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   PayrollDeduction payrollDeduction = payrollDeductionService.findPayrollDeductionByUUID(deductionUUID);
		   model.addObject("payrollDeduction",payrollDeduction);
		   model.setViewName("payroll/addDeduction");
	       return model;
	}
	
	/**
	 * Edit Payroll Contribution Type
	 * @param contributionUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editContributionType")
	public ModelAndView editContributionType(@RequestParam(value="contributionUUID", required=false) String contributionUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   PayrollContribution payrollContribution = payrollContributionService.findPayrollContributionByUUID(contributionUUID);
		   model.addObject("deductionType",payrollContribution.getDeductionTypeId().getPayrollDeductionType());
		   model.addObject("payrollContribution",payrollContribution);
		   model.setViewName("payroll/addContribution");
	       return model;
	}
	
	/**
	 * Delete Contribution Type by Id
	 * @param payrollContributionDTO
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "/deleteContributionType","/rest/deleteContributionType" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteSelectedContributionType(@RequestParam(value = "contributionId", required = false)Long contributionId,@RequestParam(value = "changeStatus", required = false)String changeStatus, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
//		if (payrollContributionDTO.getContributionId() !=0 ) {
			return payrollContributionService.deleteContributionById(contributionId,changeStatus);
//		}
//		return false;
	}
	
	/**
	 * Delete Deduction Type by Id
	 * 
	 * @param payrollDeduction
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "/deleteDeductionType","/rest/deleteDeductionType" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteSelectedDeductionType(@RequestParam(value = "id", required = false)Long deductionId,@RequestParam(value = "changeStatus", required = false)String changeStatus, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
			return payrollDeductionService.deleteDeductionById(deductionId,changeStatus);
	}
	
	
	/**
	 * Is Valid Deduction Type
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/isValidDeductionType","/rest/isValidDeductionType" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean isValidDeductionType(PayrollDeductionDTO payrollDeductionDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (StringUtils.isNotBlank(payrollDeductionDTO.getPayrollDeductionType())) {
			return payrollDeductionService.isValidDeductionType(payrollDeductionDTO.getPayrollDeductionType(),orgLocation);
		}
		return false;
	}
	
	/**
	 * Is Valid Contribution Type
	 * @param payrollContributionDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/isValidContributionType","/rest/isValidContributionType" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean isValidContributionType(PayrollContributionDTO payrollContributionDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		    OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			return payrollContributionService.isValidContributionType(payrollContributionDTO.getDeductionTypeId(),orgLocation);
	}
	
	
	/**
	 * to save associate bank details
	 * @param associateBankDataDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateBankDataDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveAssociateBankData","/rest/saveAssociateBankData" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<AssociateBankDataDTO> saveAssociateBankData(AssociateBankDataDTO associateBankDataDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<AssociateBankDataDTO>  resObj = associateBankDataService.saveAssociateBankData(associateBankDataDTO,orgLocation,loginUser);	
		return resObj;
	}
	
	/**
	 * save associate tax
	 * @param associateTaxDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateTaxDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveAssociateTax","/rest/saveAssociateTax" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<AssociateTaxDTO> saveAssociateTax(AssociateTaxDTO associateTaxDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<AssociateTaxDTO>  resObj = associateTaxService.saveAssociateTax(associateTaxDTO,orgLocation,loginUser);	
		return resObj;
	}
	
	
	/**
	 * save associate tax
	 * @param associateTaxDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateTaxDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/changeActiveAccount","/rest/changeActiveAccount" }, method = RequestMethod.GET)
	@ResponseBody
	public boolean changeActiveAccount(@RequestParam(value = "associateBankId", required = false) Long associateBankId ,@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return associateBankDataService.changeActiveAccount(associateBankId,candidateId);	
	}
	
	/**
	 * check state has Local tax or not
	 * @param associateTaxDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateTaxDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/isLocalTaxYes","/rest/isLocalTaxYes" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<AssociateTaxDTO> isLocalTaxYes(AssociateTaxDTO associateTaxDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<AssociateTaxDTO>  resObj = associateTaxService.isLocalTaxYes(associateTaxDTO,orgLocation,loginUser);	
		return resObj;
	}
	
	/**
	 * save Associate Deduction
	 * @param associateDeductionDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateDeductionDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveAssociateDeduction","/rest/saveAssociateDeduction" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<AssociateDeductionDTO> saveAssociateDeduction(AssociateDeductionDTO associateDeductionDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<AssociateDeductionDTO>  resObj = associateDeductionService.saveAssociateDeduction(associateDeductionDTO,orgLocation,loginUser);	
		return resObj;
	}
	
	/**
	 * Get Deduction methdo and amount by Id
	 * @param associateDeductionDTO
	 * @param request
	 * @param response
	 * @return PayrollDeductionDTO
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getDeductionAmount","/rest/getDeductionAmount" }, method = RequestMethod.POST)
	@ResponseBody
	public PayrollDeductionDTO getDeductionAmount(AssociateDeductionDTO associateDeductionDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PayrollDeductionDTO  resObj = associateDeductionService.getDeductionAmount(Long.parseLong(associateDeductionDTO.getDedType()));	
		return resObj;
	}
	
	/**
	 * Gat all Contribution types by loaction Id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getAllContributionType","/rest/getAllContributionType" }, method = RequestMethod.POST)
	@ResponseBody
	public List<PayrollContributionDTO> getAllContributionType(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<PayrollContributionDTO>  resObj = payrollContributionService.getAllContributionTypeByLocationId(orgLocation);	
		return resObj;
	}
	
	
	 /**
	  * Get Contribution Amount and Method by Id
	  * @param payrollContributionDTO
	  * @param request
	  * @param response
	  * @return PayrollContributionDTO
	  * @throws Exception
	  */
		@RequestMapping(value = { "/getContributionAmount","/rest/getContributionAmount" }, method = RequestMethod.POST)
		@ResponseBody
		public PayrollContributionDTO getContributionAmount(PayrollContributionDTO payrollContributionDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			PayrollContributionDTO  resObj = payrollContributionService.getContributionAmount(payrollContributionDTO.getContributionId());	
			return resObj;
		}
		
		
		/**
		 * save Associate Contribution
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO>
		 * @throws Exception
		 */
		@RequestMapping(value = { "/saveAssociateContribution","/rest/saveAssociateContribution" }, method = RequestMethod.POST)
		@ResponseBody
		public Response<AssociateContributionDTO> saveAssociateContribution(AssociateContributionDTO associateContributionDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Response<AssociateContributionDTO>  resObj = associateContributionService.saveAssociateContribution(associateContributionDTO,orgLocation,loginUser);	
			return resObj;
		}
		
		
		
		/**
		 * find Associate bank Data By User Id
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO>
		 * @throws Exception
		 */
		@RequestMapping(value = { "/findAssociateBankDataByUserId","/rest/findAssociateBankDataByUserId" }, method = RequestMethod.GET)
		@ResponseBody
		public List<AssociateBankData> findAssociateBankDataByUserId(@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			List<AssociateBankData> associateBankDataList =  associateBankDataService.findAllAssociateBankDataByCandidateId(candidate.getId(),orgLocation.getId());
			return associateBankDataList;
		}
		
		
		/**
		 * find Associate bank Data By UUID
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO>
		 * @throws Exception
		 */
		@RequestMapping(value = { "/findAssociateBankDataByUUID","/rest/findAssociateBankDataByUUID" }, method = RequestMethod.GET)
		@ResponseBody
		public AssociateBankData findAssociateBankDataByUUID(@RequestParam(value = "associateBankUUID", required = false) String associateBankUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			return  associateBankDataService.findAssociateBankDataByUUID(associateBankUUID,orgLocation.getId());
		}

		/**
		 * find Associate Tax Summary By User Id
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO>
		 * @throws Exception
		 */
		@RequestMapping(value = { "/findAssociateTaxSummaryByUserId","/rest/findAssociateTaxSummaryByUserId" }, method = RequestMethod.GET)
		@ResponseBody
		public AssociateTaxDTO findAssociateTaxSummaryByUserId(@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId,orgLocation.getId());
			AssociateTaxDTO  associateTaxDTO = associateTaxService.findAssociateTaxSummaryByCandidateId(candidate.getId(),orgLocation);	
			return associateTaxDTO;
		}
		
		
		/**
		 * find Associate Deduction Summary By User Id
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO>
		 * @throws Exception
		 */
		@RequestMapping(value = { "/findAllDeductionSummaryByUserId","/rest/findAllDeductionSummaryByUserId" }, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<DeductionDTO> findAllDeductionSummaryByUserId(@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId, orgLocation.getId());
			ResponseList<DeductionDTO>  res = associateDeductionService.findAllDeductionSummaryByCandidateId(candidate.getId(),orgLocation);	
			return res;
		}
		
		
		/**
		 * find Associate Contribution Summary By User Id
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO>
		 * @throws Exception
		 */
		@RequestMapping(value = { "/findAllContributionSummaryByUserId","/rest/findAllContributionSummaryByUserId" }, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<ContributionDTO> findAllContributionSummaryByUserId(@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId,orgLocation.getId());
			ResponseList<ContributionDTO>  res = associateContributionService.findAllContributionSummaryByCandidateId(candidate.getId(),orgLocation);	
			return res;
		}
		
		/**
		 * Update Deduction Amount 
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO> 
		 * @throws Exception
		 */
		@RequestMapping(value = { "/updateAssociateDeductionAmount","/rest/updateAssociateDeductionAmount" }, method = RequestMethod.POST)
		public AssociateDeduction updateAssociateDeductionAmount(@RequestParam(value = "associateDeductionId", required = false) Long associateDeductionId,
				@RequestParam(value = "dedAmount", required = false) String dedAmount,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			AssociateDeduction associateDeduction = associateDeductionService.findAssociateDeductionById(associateDeductionId);
			associateDeduction.setDeductionAmount(new BigDecimal(dedAmount));
			return associateDeductionDao.saveAssociateDeduction(associateDeduction);
		}
		
		/**
		 * Delete Association Deduction  
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO> 
		 * @throws Exception
		 */
		@RequestMapping(value = { "/deleteAssociateDeduction","/rest/deleteAssociateDeduction" }, method = RequestMethod.POST)
		public AssociateDeduction deleteAssociateDeduction(@RequestParam(value = "assDedId", required = false) Long assDedId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			AssociateDeduction associateDeduction = associateDeductionService.findAssociateDeductionById(assDedId);
			associateDeduction.setStatus(0);
			return associateDeductionDao.saveAssociateDeduction(associateDeduction);
		}
		
		
		/**
		 * Delete Association Contribution Amount 
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO> 
		 * @throws Exception
		 */
		@RequestMapping(value = { "/updateAssociateContributionAmount","/rest/updateAssociateContributionAmount" }, method = RequestMethod.POST)
		public AssociateContribution updateAssociateContributionAmount(@RequestParam(value = "associateContributionId", required = false) Long associateContributionId,
				@RequestParam(value = "contriAmount", required = false) String contriAmount,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			AssociateContribution associateContribution = associateContributionService.findAssociateContributionById(associateContributionId);
			associateContribution.setContributionAmount(new BigDecimal(contriAmount));
			return associateContributionDao.saveAssociateContribution(associateContribution);
		}
		
		
		/**
		 * Delete Association Contribution 
		 * @param associateDeductionDTO
		 * @param request
		 * @param response
		 * @return Response<AssociateDeductionDTO> 
		 * @throws Exception
		 */
		@RequestMapping(value = { "/deleteAssociateContribution","/rest/deleteAssociateContribution" }, method = RequestMethod.POST)
		public AssociateContribution deleteAssociateContribution(@RequestParam(value = "assContriId", required = false) Long assContriId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			AssociateContribution associateContribution = associateContributionService.findAssociateContributionById(assContriId);
			associateContribution.setStatus(0);
			return associateContributionDao.saveAssociateContribution(associateContribution);
		}
		
		/**
		 * Returning Generate Paysheet JSP
		 * @param request
		 * @param response
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = { "/generatePaysheet","/rest/generatePaysheet" })
		public ModelAndView usPayroll(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
			model.addObject("userGroupList",userGroupList);
			model.setViewName("payroll/usPayroll");
			return model;
		}
		
		
		/**
		 * Returning payroll JSP
		 * @param request
		 * @param response
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = { "/runPayroll","/rest/runPayroll" })
		public ModelAndView payroll(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
			model.addObject("userGroupList",userGroupList);
			model.setViewName("payroll/runPayroll");
			return model;
		}
		
		
		@RequestMapping(value = { "/generateUSPayroll","/rest/generateUSPayroll" }, method = RequestMethod.POST)
		public PaysheetFrequencyDTO generateUSPayroll(GenerateUSPayrollDTO generateUSPayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			PaysheetFrequencyDTO existedData = generateUSPayrollService.getPayrollData(generateUSPayrollDTO,orgLocation,loginUser);
//			if(existedData.getPayrollFrequencyUUID() != null) {
//				return existedData;
//			}else {
				return generateUSPayrollService.generateUSPayroll(existedData,orgLocation,generateUSPayrollDTO,loginUser);
//			}
		}
		
		
		@RequestMapping(value = { "/generatePaySheet","/rest/generatePaySheet" }, method = RequestMethod.POST)
		public ResponseList<USPaysheetDTO> generatePaySheet(GenerateUSPayrollDTO generateUSPayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ResponseList<USPaysheetDTO> res = uSPaysheetService.generateUSPaysheet(orgLocation,generateUSPayrollDTO);
			return res;
		}
		/**
		 * Get  user groups Deductions 
		 * @param payrollDeductionDTO
		 * @param request
		 * @param response
		 * @return json
		 * @throws Exception
		 */
		@RequestMapping(value = { "/getUserGroupList","/rest/getUserGroupList" }, method = RequestMethod.POST)
		@ResponseBody
		public ResponseList<UserGroupDTO> getUserGroupList(UserGroupDTO userGroupDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String[] tableColumns = {"groupId", "groupName","numOfAssociates", "payFrequency"};
		    int sortColumnIndex = 1;		 //by default sorting using updated date
			String sortColumnOrder = "asc";  //by default descending order
			if(userGroupDTO.getOrder() != null && !userGroupDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(userGroupDTO.getOrder().get(0).get("column"));
				sortColumnOrder = userGroupDTO.getOrder().get(0).get("dir");
			}
			userGroupDTO.setSortColumn(tableColumns[sortColumnIndex]);
			userGroupDTO.setSortOrder(sortColumnOrder);
			return  userGroupService.findAllUserGroup(userGroupDTO,orgLocation);	
		}
		
		/**
		 * Get Pay Frequency By Group Frequency
		 * @param groupId
		 * @param request
		 * @param response
		 * @return List<String>
		 * @throws Exception
		 */
	@RequestMapping(value = { "/getGroupPayPeriod","/rest/getUsersVGroupPayPeriod" }, method = RequestMethod.GET)
	public List<PayrollScheduleCalenderDTO> getGroupPayPeriod(@RequestParam(value = "groupUUID", required = false) String groupUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<PayrollScheduleCalenderDTO> listDates = null;
		UsPayrollSchedule usPayrollSchedule = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String [] str = groupUUID.split(",");
		Response<UserGroupDTO> userGroup = userGroupService.findUserGroupById(str[0]);
		
		if (userGroup.getObject() != null && PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(userGroup.getObject().getPayFrequency()) && userGroup.getObject().getPayFrequency() != null) {
			 usPayrollSchedule = payrollScheduleService.findScheduleByFrequency(PayrollConstant.PAY_WEEKLY, orgLocation.getId(), "");
//			List<String> list = new ArrayList<>();
//			for(PayrollScheduleCalenderDTO ) {
//				
//			}
//			Calendar now = Calendar.getInstance(); // Gets the current date and
//			int year = now.get(Calendar.YEAR);
//			int month = now.get(Calendar.MONTH);
//			listDates = payrollUtil.getNumberOfWeeks(year, month, orgLocation);
		}else if(userGroup.getObject() != null && PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(userGroup.getObject().getPayFrequency()) && userGroup.getObject().getPayFrequency() != null) {
			 usPayrollSchedule = payrollScheduleService.findScheduleByFrequency(PayrollConstant.PAY_MONTHLY, orgLocation.getId(), "");
//			Calendar now = Calendar.getInstance(); // Gets the current date and
//			int month = now.get(Calendar.MONTH);
//			listDates = payrollUtil.getNumberOfMonths(month, orgLocation);
			
			
		}else if(userGroup.getObject() != null && PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(userGroup.getObject().getPayFrequency()) && userGroup.getObject().getPayFrequency() != null) {
			 usPayrollSchedule = payrollScheduleService.findScheduleByFrequency(PayrollConstant.PAY_BI_WEEKLY, orgLocation.getId(), "");
			//			Calendar now = Calendar.getInstance();
//			int month = now.get(Calendar.MONTH);
//			listDates = payrollUtil.getNumberOfBiWeeks(orgLocation);
		}else if(userGroup.getObject() != null && PayrollConstant.ANNUALLY.equalsIgnoreCase(userGroup.getObject().getPayFrequency()) && userGroup.getObject().getPayFrequency() != null) {
			Calendar now = Calendar.getInstance(); 
//			listDates = payrollUtil.getAnnuallyDates(orgLocation);
		}
		listDates = payrollUtil.getPayScheduleWeeksByDate(usPayrollSchedule, orgLocation);
		return listDates;
	}
	
	/**"
	 * Add Pay Item for Organization
	 * @param payItemDTO
	 * @param request
	 * @param response
	 * @return Response<PayItemDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/createPayItem","/rest/createPayItem" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<PayItemDTO> createPayItem(PayItemDTO payItemDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<PayItemDTO>  resObj = payItemService.addPayItem(orgLocation,payItemDTO,loginUser);
		return resObj;
	}
	
	/**
	 * Get Pay Item List By OrgLocation Id
	 * @param payItemDTO
	 * @param request
	 * @param response
	 * @return ResponseList<PayItemDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPayItemList","/rest/getPayItemList" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<PayItemDTO> getPayItemList(PayItemDTO payItemDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<PayItemDTO> resObj = payItemService.getPayItemByLocationId(orgLocation,payItemDTO.getAssociateEmpType());
		return resObj;
	}
	
	
	/**
	 * Add Associate Salary
	 * @param associateSalaryDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateSalaryDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/addAssociateSal","/rest/addAssociateSal" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<AssociateSalaryDTO> addAssociateSal(AssociateSalaryDTO associateSalaryDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<AssociateSalaryDTO>  resObj = associateSalaryService.addAssociateSal(orgLocation,associateSalaryDTO,loginUser);
		return resObj;
	}
	

	@RequestMapping(value = "/editPayItem")
	public ModelAndView editPayItem(PayItemDTO payItemDTO ,HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		if(StringUtils.isNotBlank(payItemDTO.getPayItemUUID())){
			List<String> empList = Arrays.asList(Constants.EMPLOYMENT_TYPES);
			model.addObject("empList" , empList);
			PayItem payItem = payItemService.getPayItemByUUID(payItemDTO.getPayItemUUID());
			payItem.setEmpType(Arrays.asList(payItem.getAllowEmployeementType().split("\\s*,\\s*")));
			model.addObject("payItem", payItem);
		}
		model.setViewName("payroll/addPayItem");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/deletePayItem")
	public Response<PayItem> deletePayItem(PayItemDTO payItemDTO ,HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<PayItem> res = payItemService.deletePayItemByUUID(payItemDTO,user);
		return res;
	}
	
	@RequestMapping(value = "/validatePayItemName")
	public Response<PayItem> validatePayItemName(PayItemDTO payItemDTO ,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<PayItem> res = payItemService.validatePayItemName(payItemDTO,orgLocation);
		return res;
	}


	@RequestMapping(value = { "/findAssociateSalary","/rest/findAssociateSalary" }, method = RequestMethod.GET)
	@ResponseBody
	public AssociateSalaryDTO findAssociateSalary(@RequestParam(value = "userId",required = false) Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId,orgLocation.getId());
		AssociateSalaryDTO  associateSalaryDTO = associateSalaryService.findAssociateSalaryByCandidateId(candidate.getId(),orgLocation);
		return associateSalaryDTO;
	}
	
	/**
	 * Find Assoviate Salary History
	 * @param associateSalid
	 * @param request
	 * @param response
	 * @return AssociateSalaryDTO
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAssociateSalHistory","/rest/findAssociateSalHistory" }, method = RequestMethod.GET)
	@ResponseBody
	public List<AssociateSalaryHistoryDTO> findAssociateSalHistory(@RequestParam(value = "associateSalid",required = false) Long associateSalid, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<AssociateSalaryHistoryDTO>  associateSalaryHisDTO = null;
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    associateSalaryHisDTO = associateSalaryHistoryService.findAssociateSalHistory(associateSalid,orgLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return associateSalaryHisDTO;
	}
	
	
	/**
	 * save paysheet for approvel
	 * @param associateSalaryDTO
	 * @param request
	 * @param response
	 * @return Response<AssociateSalaryDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/submitPaysheet","/rest/submitPaysheet" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<PaysheetFrequencyDTO> submitPaysheet(PaysheetFrequencyDTO paysheetFrequencyDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<PaysheetFrequencyDTO>  resObj = uSPaysheetService.submitPaysheet(orgLocation,paysheetFrequencyDTO,loginUser);
		return resObj;
	}
	
	@RequestMapping(value = "/addAssociateDeduction")
	public ModelAndView addAssociateDeduction(HttpServletRequest request,@RequestParam(value = "userCode",required = false) String userCode,@RequestParam(value = "saveType",required = false) String saveType) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User user = userService.getUserByUserCode(userCode);
		model.addObject("userCode", user.getUserCode());
		model.addObject("userId", user.getId());
		model.addObject("saveType", saveType);
		model.setViewName("users/addAssociateSummaryDeduction");
		return model;
	}
	
	@RequestMapping(value = "/addAssociateContribution")
	public ModelAndView addAssociateContribution(HttpServletRequest request,@RequestParam(value = "userCode",required = false) String userCode,@RequestParam(value = "saveType",required = false) String saveType) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User user = userService.getUserByUserCode(userCode);
		model.addObject("userCode", user.getUserCode());
		model.addObject("userId", user.getId());
		model.addObject("saveType", saveType);
		model.setViewName("users/addAssociateSummaryContribution");
		return model;
	}
	
	
	@RequestMapping(value = "/updateAssociatePayItem",method = RequestMethod.POST)
	public boolean updateAssociatePayItem(HttpServletRequest request,AssociatePayItemDTO associatePayItemDTO) throws Exception
	{
		boolean result = false;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		AssociatePayItem associatePayItem = associateSalaryService.findPayItemByPayItemId(Long.valueOf(associatePayItemDTO.getId()));
		associatePayItem.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),associatePayItemDTO.getEffectiveDate()));
		if(StringUtils.isNotBlank(associatePayItemDTO.getEndDate())) {
			associatePayItem.setEndDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),associatePayItemDTO.getEndDate()));
		}else {
			associatePayItem.setEndDate(null);
		}
		
		if(StringUtils.isNotBlank(associatePayItemDTO.getPayItemAmount())) {
			associatePayItem.setPayItemAmount(new BigDecimal(associatePayItemDTO.getPayItemAmount()));
		}else {
			associatePayItem.setPayItemAmount(null);
		}
		
		associatePayItem = associateSalaryService.saveUpdatedPayItem(associatePayItem);
		
		if(associatePayItem != null) {
			result = true;
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteAssociatePayItem",method = RequestMethod.POST)
	public boolean deleteAssociatePayItem(HttpServletRequest request,@RequestParam(value = "payItemId",required = false) Long payItemId) throws Exception
	{
		boolean result = false;
		AssociatePayItem associatePayItem = associateSalaryService.findPayItemByPayItemId(payItemId);
		associatePayItem.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		associatePayItem = associateSalaryService.saveUpdatedPayItem(associatePayItem);
		if(associatePayItem != null) {
			result = true;
		}
		
		return result;
	}
	/**
	 * Get Particular Employee Payroll 
	 * @param associateSalid
	 * @param request
	 * @param response
	 * @return Response<USPayrollDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getEmployeePayroll","/rest/getEmployeePayroll" }, method = RequestMethod.GET)
	@ResponseBody
	public Response<USPayrollDTO> getEmployeePayroll(@RequestParam(value = "payrollUUID",required = false) String payrollUUID,@RequestParam(value = "status",required = false) String approverStatus, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<USPayrollDTO>  resObj = generateUSPayrollService.getEmployeePayrollByUUID(payrollUUID,orgLocation,approverStatus);
		return resObj;
	}
	
	/**
	 * update pay adjustmetns in employee payroll table
	 * 
	 * @param payAdjustmentDTO
	 * @param request
	 * @param response
	 * @return Response<PayAdjustmentDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/editEmployeePayroll","/rest/editEmployeePayroll" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<PayAdjustmentDTO> editEmployeePayroll(PayAdjustmentDTO payAdjustmentDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			return  generateUSPayrollService.updateEmployeePayroll(payAdjustmentDTO,orgLocation,loginUser);
	}
	
	/**
	 * Imports History payroll Data
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/payrollImports")
	public ModelAndView getPayrollImports(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("payroll/payrollImportList");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		return model;
	}
	
	/**
	 * Return payroll list jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/payrollList")
	public ModelAndView getPayrollList(@RequestParam(value="redirect",required=false)String redirect,HttpServletRequest request, HttpServletResponse response)
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
		model.addObject("userGroupList",userGroupList);		
		model.addObject("redirect",redirect);		
//		model.setViewName("payroll/usPayrollDashboard");
		model.setViewName("payroll/payrollCalculator");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		return model;
	}
	
	/**
	 * Return pay schedule jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/paySchedule")
	public ModelAndView getpayScheduleJSP(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("payroll/paySchedule");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		return model;
	}
	
	/**
	 * return payroll approver jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/payrollApprover")
	public ModelAndView getpayrollApproverJSP(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("payroll/payrollApprover");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		return model;
	}
	
	
	/**
	 * save Payroll Schedule
	 * @param payrollScheduleDTO
	 * @param request
	 * @param response
	 * @return Response<PayrollScheduleDTO>
	 * @throws Exception
	 */
	@RequestMapping(value =  "/savePayrollSchedule", method = RequestMethod.POST)
	@ResponseBody
	public Response<PayrollScheduleDTO> savePayrollSchedule(PayrollScheduleDTO payrollScheduleDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return payrollScheduleService.savePayrollSchedule(payrollScheduleDTO,orgLocation,loginUser);	
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/payrollScheduleList")
	public ModelAndView getPayrollScheduleListJSP(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("payroll/payrollScheduleList");
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		return model;
	}
	
	
	/*@RequestMapping(value =  "/getWeeklyFrequenciesBySelectedDate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<String> getWeeklyFrequenciesBySelectedDate(PayrollScheduleDTO payrollScheduleDTO , HttpServletRequest request) throws Exception
	{
		ResponseList<String> response = new ResponseList<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<String> datesList = payrollScheduleService.getWeeklyFrequenciesBySelectedDate(payrollScheduleDTO.getSelectedDate(),orgLocation.getDateFormat(),payrollScheduleDTO.getDateFrequency());
		response.setData(datesList);
		
		return response;
	}*/
	
	/*@RequestMapping(value =  "/getDisableApplyFrequencies", method = RequestMethod.GET)
	@ResponseBody
	public boolean getDisableApplyFrequencies(PayrollScheduleDTO payrollScheduleDTO , HttpServletRequest request) throws Exception
	{
		boolean bol = false;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		UsPayrollSchedule disable = payrollScheduleService.getWeeklyFrequenciesBySelectedDate(payrollScheduleDTO.getDateFrequency(),orgLocation);
		if(disable != null) {
			bol = true;
		}
		return bol;
		
	}*/

	/**
	 * Get Pay sheet frequency data
	 * 
	 * @param paysheetFrequencyDTO
	 * @param searchValue
	 * @param request
	 * @param response
	 * @return ResponseList<PaysheetFrequencyDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPaySheetFrequencyData", "/rest/getPaySheetFrequencyData" })
	@ResponseBody
	public ResponseList<PaysheetFrequencyDTO> getPaySheetFrequencyList(PaysheetFrequencyDTO paysheetFrequencyDTO,
			@RequestParam(value = "paySheetFrequencyUUID", required = false) String searchValue,
			@RequestParam(value = "searchFrequency", required = false) String searchFrequency,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(searchFrequency !=null)
			paysheetFrequencyDTO.setPayFrequency(searchFrequency);
		return usPayPeriodService.getPayPeriodListByOrgLocationId(orgLocation, paysheetFrequencyDTO);
	}
	
	/**
	 * Get all Payroll Schedule List
	 * @param request
	 * @param response
	 * @return List<PayrollScheduleDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllPayrollScheduleList", "/rest/findAllPayrollScheduleList" })
	@ResponseBody
	public List<PayrollScheduleDTO> findAllPayrollScheduleList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return payrollScheduleService.findAllPayrollScheduleList(orgLocation.getId(),orgLocation.getDateFormat());
	}
	
	
	/**
	 * View Payroll Calender List view and grid view
	 * @param holidayUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollScheduleCalender")
	public ModelAndView payrollCalenderView(@RequestParam(value = "payScheduleUUID", required = false) String payScheduleUUID,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   model.addObject("payScheduleUUID",payScheduleUUID);
		   model.setViewName("payroll/payScheduleCalender");
	       return model;
	}
	
	
	/**
	 * View Payroll Calender List view and grid view
	 * @param holidayUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payScheduleCalEdit")
	public ModelAndView payScheduleCalEdit(@RequestParam(value = "payScheduleUUID", required = false) String payScheduleUUID,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   model.addObject("payScheduleUUID",payScheduleUUID);
		   model.setViewName("payroll/paySchedule");
	       return model;
	}
	
	/**
	 * Get all Payroll Schedule List
	 * @param request
	 * @param response
	 * @return List<PayrollScheduleDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findScheduleDates", "/rest/findScheduleDates" },method = RequestMethod.POST)
	@ResponseBody
	public List<PayrollScheduleCalenderDTO> findScheduleDates(PayrollScheduleDTO payrollScheduleDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		UsPayrollSchedule paySchedule = payrollScheduleService.getPayScheduleByUUID(payrollScheduleDTO.getPayrollScheduleUUID(),orgLocation.getId());
		List<PayrollScheduleCalenderDTO> calenderList  = payrollUtil.getPayScheduleWeeksByDate(paySchedule,orgLocation);
		return calenderList;
	}
	
	/**
	 * 
	 * @param paySheetFrequencyUUID
	 * @param request /viewPaySheetFrequencyData?
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaySheetFrequencyData")
	public ModelAndView viewPaySheetFrequencyData(@RequestParam(value = "paySheetFrequencyUUID",required = true) String paySheetFrequencyUUID,@RequestParam(value = "approverStatus",required = false) String approverStatus,@RequestParam(value = "payPeriodNum",required = false) Integer payPeriodNum,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("payPeriodNum", payPeriodNum);
		model.addObject("paySheetFrequencyUUID", paySheetFrequencyUUID);
		if(approverStatus.equalsIgnoreCase(Constants.SENT)) {
			model.setViewName("payroll/payrollSentSummary");
		}else if(approverStatus.equalsIgnoreCase(Constants.APPROVED)){
			model.setViewName("payroll/payrollApprovedSummary");
		}else if(approverStatus.equalsIgnoreCase(Constants.DRAFT)) {
			model.setViewName("payroll/payrollDraftSummary");
		}
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		model.addObject("approverStatus",approverStatus);
		sessionData.setNav(Constants.PAYROLL);
		request.getSession().setAttribute("sessionData", sessionData);
		return model;
	}
	
	/**
	 * Gett Pay Sheet Frequency Summary
	 * @param paySheetFrequencyUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPaySheetFrequencySummary","/rest/getPaySheetFrequencySummary" })
	public PaysheetFrequencyDTO getPaySheetFrequencySummary(PaysheetFrequencyDTO paysheetFrequencyDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return uSPaysheetService.getPaySheetFrequencySummary(paysheetFrequencyDTO,orgLocation);
	}

	
	/**
	 * Get All Users as a approvers by location Id
	 * @param associateSalid
	 * @param request
	 * @param response
	 * @return Response<USPayrollDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPayrollApprover","/rest/getPayrollApprover" }, method = RequestMethod.GET)
	@ResponseBody
	public ApproverDTO getPayrollApprover(@RequestParam(value = "payrollUUID",required = false) String payrollUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ApproverDTO approverDTO = new ApproverDTO();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		approverDTO = configurePayrollApproverService.getPayrollApprover(orgLocation,loginUser);
		return approverDTO;
	}
	
	/**
	 * Save Payroll Approvers
	 * @param associateSalid
	 * @param request
	 * @param response
	 * @return Response<USPayrollDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/savePayrollApprover","/rest/savePayrollApprover" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<PayrollApproverDTO> savePayrollApprover(PayrollApproverDTO payrollApproverDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<PayrollApproverDTO>  resObj = configurePayrollApproverService.savePayrollApprover(payrollApproverDTO,orgLocation,loginUser);
		return resObj;	
	}
	

	
	/**
	 * Get approvers
	 * 
	 * @param request
	 * @param response
	 * @return ResponseList<PayrollApproverDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getConfigurePayrollApprover","/rest/getConfigurePayrollApprover" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<PayrollApproverDTO> getConfigurePayrollApprover(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<PayrollApproverDTO> resObj = configurePayrollApproverService.getConfigurePayrollApprover(orgLocation);
		return resObj;
	}
	
	
	
	@RequestMapping(value = { "/sendPayrollForApprover","/rest/sendPayrollForApprover" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<PayrollApproverDTO> res = paysheetFrequencyService.sendPayrollForApprover(payrollApproverDTO,orgLocation,loginUser);
		return res;
	}
	
	
	/**
	 * Confirm payroll (jsut calculating total pay sum)
	 * @param payrollApproverDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/confirmPayroll","/rest/confirmPayroll" }, method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPayTotalSumDTO confirmPayroll(PayrollApproverDTO payrollApproverDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ConfirmPayTotalSumDTO paySum = uSPayrollService.calculateTotalPaySum(payrollApproverDTO,orgLocation);
		return paySum;
	}
	
	/**
	 * Approve Payroll
	 * @param confirmPayTotalSumDto
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/approvePayroll","/rest/approvePayroll" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<ConfirmPayTotalSumDTO> approvePayroll(ConfirmPayTotalSumDTO confirmPayTotalSumDto ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<ConfirmPayTotalSumDTO> res = uSPayrollService.approvePayroll(confirmPayTotalSumDto,orgLocation,loginUser);
		return res;

	}
	
	/**
	 * Validate Deduction
	 * @param deductionTypeId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateDeduction", method = RequestMethod.GET)
	@ResponseBody
	public boolean validateDeduction(@RequestParam(value = "dedType",required = false)Long deductionTypeId, @RequestParam(value = "userId",required = false)Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean bol = false;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId,orgLocation.getId());
		AssociateDeduction  validateDed = associateDeductionService.getValidateDeduction(deductionTypeId,orgLocation,candidate.getId());	
		if(validateDed != null) {
			bol = true;
		}
		return bol;
	}
	
	/**
	 * Validate Contribution
	 * @param contriTypId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateContribution", method = RequestMethod.GET)
	@ResponseBody
	public boolean validateContribution(@RequestParam(value = "contriType",required = false)Long contriTypId,@RequestParam(value = "userId",required = false)Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean bol = false;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId,orgLocation.getId());
		AssociateContribution  validateCon = associateContributionService.getValidateContribution(candidate.getId(),contriTypId,orgLocation);	
		if(validateCon != null) {
			bol = true;
		}
		return bol;
	}
	
	/**
	 * Get Paygroup by Pay frequency
	 * @param userGroupDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPayGroupsByPayFrequency","/rest/getPayGroupsByPayFrequency" })
	@ResponseBody
	public List<UserGroup> getPayGroupsByPayFrequency(UserGroupDTO userGroupDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<UserGroup> filteredGroupList = new ArrayList<>();
//		List<UserGroup> groupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
		List<UserGroup> groupList = userGroupService.getAllUserGroupsByLocationIdAndDepartmentId(orgLocation,userGroupDTO.getUserId());
		if(groupList != null)
		filteredGroupList = groupList.stream().filter(i->i.getPayFrequency().equalsIgnoreCase(userGroupDTO.getPayFrequency())).collect(Collectors.toList());
		return filteredGroupList;
	}
	
	
	@RequestMapping(value = "/myPayroll")
	public ModelAndView myPayrollJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/myPayroll");
		return model;
	}
	
	/**
	 * @author pavan
	 * 
	 * @param usMyPayrollFormsDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveUsMyPayrollForms", "/rest/saveUsMyPayrollForms" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<String> saveUsMyPayrollForms(UsMyPayrollFormsDTO usMyPayrollFormsDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		Response<String> res = new Response<>();
		try {
			if (orgLocation != null && candidateId != null) {
				usMyPayrollFormsDTO.setOrgLocationId(String.valueOf(orgLocation.getId()));
				usMyPayrollFormsDTO.setCandidate(String.valueOf(candidateId));
				usMyPayrollFormsDTO.setStaticPath(staticPath);
				UsMyPayrollForms payrollForm = usMyPayrollFormService.saveUsMyPayrollForm(usMyPayrollFormsDTO, orgLocation, user);
				if (payrollForm != null) {
					res.setResponse(Result.SUCCESS.name());
					res.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					res.setResponse(Result.FAILED.name());
					res.setResponseMessage(Result.FAILED.getValue());
				}
			}
			else {
				res.setResponse(Result.SESSION_TIMEDOUT.name());
				res.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}
	
	/**
	 * @author pavan
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getUsMyPayrollFormsByOrgLocationId", "/rest/getUsMyPayrollFormsByOrgLocationId" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<UsMyPayrollForms> getUsMyPayrollFormsByOrgLocationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		ResponseList<UsMyPayrollForms> res = new ResponseList<>();
		try {
			if (orgLocation != null && candidateId != null) {
				res = usMyPayrollFormService.getUsMyPayrollFormByLocationIdAndCandidateId(orgLocation.getId(), candidateId);
			}
			else {
				res.setResponse(Result.SESSION_TIMEDOUT.name());
				res.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}
	
	@RequestMapping(value = { "/deleteUsMyPayrollFormByUUID","/rest/deleteUsMyPayrollFormByUUID" }, method = RequestMethod.GET)
	@ResponseBody
	public Response<String> deleteUsMyPayrollFormByUUID(UsMyPayrollFormsDTO usMyPayrollFormsDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> res = new Response<>();
		try {
			if (user != null) {
				usMyPayrollFormsDTO.setUpdatedBy(user.getUserCode());
				res = usMyPayrollFormService.deleteUsMyPayrollFormByUUID(usMyPayrollFormsDTO);
			}
			else {
				res.setResponse(Result.SESSION_TIMEDOUT.name());
				res.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}

	/**
	 * My Payroll Data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getMyPayrollList","/rest/getMyPayrollList"}, method = RequestMethod.GET)
	@ResponseBody
	public MyPayrollSummaryDTO getMyPayrollList(@RequestParam(value = "candidateId",required = false)Long candidateId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(candidateId == null) {
			candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		}
		return paysheetFrequencyService.getMyPayrollListByCandidateId(candidateId,orgLocation);
	}
	
	@RequestMapping(value = {"/getPaysheetFrequencyDraftData","/rest/getPaysheetFrequencyDraftData"}, method = RequestMethod.GET)
	@ResponseBody
	public List<PaysheetFrequencyDTO> getPaysheetFrequencyDraftData(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return paysheetFrequencyService.getPaysheetFrequencyDraftDataByOrgLocationId(orgLocation);
	}
	
	@RequestMapping(value = "/getEmpOutstandingLoansAdvances", method = RequestMethod.GET)
	@ResponseBody
	public List<AssociateLoanAndAdvancesDTO> getEmpOutstandingLoansAdvances(@RequestParam(value = "type",required = false)String type,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		return associateLoanAndAdvancesService.getAssociateLoanAndAdvancesListByCandidateIdByOrgLocationId(candidateId,orgLocation,type);
	}
	
	@RequestMapping(value = "/getLoansAndAdvancesCounts", method = RequestMethod.GET)
	public @ResponseBody Map<String, Double> getLoansAndAdvancesCounts(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		Map<String, Double> loansAndAdvancescount = associateLoanAndAdvancesService.getEmpLoansAndAdvancesCountListByOrgLocationId(candidateId);
		
		return loansAndAdvancescount;
		
	}
	 
	 /**
		 * User Group List JSp
		 * @param deductionUUID
		 * @param request
		 * @param response
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = "/userGroupList")
		public ModelAndView userBatchList(HttpServletRequest request,HttpServletResponse response) throws Exception {
			   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			   ModelAndView model = new ModelAndView();
			   List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(loginOrgLocation.getId());
			   model.addObject("departmentList", departmentList);
			   model.addObject("sourceleadUrl", sourceleadUrl);
			   model.setViewName("payroll/usergroupList");
		       return model;
		}
		
		
		@RequestMapping(value = "/addUserGroup")
		public ModelAndView addUserGroup(UserGroupDTO userGroupDto , HttpServletRequest request,HttpServletResponse response) throws Exception {
			   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			   ModelAndView model = new ModelAndView();
			   List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(loginOrgLocation.getId());
			   model.addObject("departmentList", departmentList);
			   if(StringUtils.isNotBlank(userGroupDto.getGroupUUID())){
				   UserGroup userGroup = userGroupDao.findUserGroupById(userGroupDto.getGroupUUID());
				   model.addObject("userGroup", userGroup);
			/*	   if(userGroup.getUserId() != null) {
					   String[] userIds = userGroup.getUserId().split(",");
					   List<Long> userIdList = new ArrayList<>();
					   for(int i=0;i<userIds.length;i++){
							userIdList.add(Long.parseLong(userIds[i]));
					   }
					   List<UserRole> userList = userRoleService.getUserListByUserIdList(userIdList,loginOrgLocation.getId());
					   model.addObject("selectedUserList", userList);
				   }*/
			   }
			   model.addObject("fromType", userGroupDto.getFromType());
			   model.addObject("sourceleadUrl", sourceleadUrl);
			   model.setViewName("payroll/addUserGroup");
		       return model;
		}
		
		@RequestMapping(value = "/numberOfEmployeesInGroup")
		public ModelAndView numberOfEmployeesInGroup(@RequestParam(value = "groupUUID",required = false) String groupUUID,HttpServletRequest request) throws Exception
		{
			ModelAndView model = new ModelAndView();
			model.addObject("groupUUID", groupUUID);
			model.setViewName("payroll/employeeGroups");
			return model;
		}
		
		@RequestMapping(value = {"/getAllEmployeesInGroups","/rest/getAllEmployeesInGroups"}, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<CandidateDTO> getAllEmployeesInGroups(CandidateDTO dto,@RequestParam(value = "groupUUID",required = false) String groupUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			UserGroup userGroup = userGroupDao.findUserGroupById(groupUUID);
			
			ResponseList<CandidateDTO> resList = new ResponseList<>();
			List<CandidateDTO> listDto = new ArrayList<>();
			
			if(orgLocation.getCountry().equalsIgnoreCase("India")) {
				ResponseList<AssociateSalaryInd> assSalIndList = associateSalaryService.getAssociateSalaryIndListByLocationIdAndGroupId(orgLocation, userGroup.getId(),dto);
				for (AssociateSalaryInd associateSalary : assSalIndList.getData()) {
					 dto = new CandidateDTO();
					
					StringBuilder name = new StringBuilder();
					if(StringUtils.isNotBlank(associateSalary.getCandidate().getMiddelName()) && StringUtils.isNotBlank(associateSalary.getCandidate().getLastName())){
						name.append(associateSalary.getCandidate().getFirstName()).append(" ").append(associateSalary.getCandidate().getMiddelName()).append(" ").append(associateSalary.getCandidate().getLastName());
					}else if(StringUtils.isNotBlank(associateSalary.getCandidate().getLastName()) && StringUtils.isBlank(associateSalary.getCandidate().getMiddelName())){
						name.append(associateSalary.getCandidate().getFirstName()).append(" ").append(associateSalary.getCandidate().getLastName());
					}else {
						name.append(associateSalary.getCandidate().getFirstName());
					}
					dto.setCandidateName(name.toString());
					dto.setEmail(associateSalary.getCandidate().getEmail());
					dto.setMobilePhone(associateSalary.getCandidate().getMobilePhone());
					if(associateSalary.getCandidate().getDesignationType() != null) {
						dto.setDesignationType(associateSalary.getCandidate().getDesignationType());
					}else {
						dto.setDesignationType("--");
					}
					
					listDto.add(dto);
				}
				resList.setData(listDto);
				resList.setRecordsFiltered(assSalIndList.getRecordsFiltered());
				resList.setRecordsTotal(assSalIndList.getRecordsTotal());
			}else {
				ResponseList<AssociateSalary> assSalList = associateSalaryService.getAssociateSalaryListByLocationIdAndGroupId(orgLocation, userGroup.getId(),dto);
				for (AssociateSalary associateSalary : assSalList.getData()) {
					 dto = new CandidateDTO();
					
					StringBuilder name = new StringBuilder();
					if(StringUtils.isNotBlank(associateSalary.getCandidate().getLastName())){
						name.append(associateSalary.getCandidate().getFirstName()).append(" ").append(associateSalary.getCandidate().getLastName());
					}else {
						name.append(associateSalary.getCandidate().getFirstName());
					}
					dto.setCandidateName(name.toString());
					dto.setEmail(associateSalary.getCandidate().getEmail());
					dto.setMobilePhone(associateSalary.getCandidate().getMobilePhone());
					if(associateSalary.getCandidate().getDesignationType() != null) {
						dto.setDesignationType(associateSalary.getCandidate().getDesignationType());
					}else {
						dto.setDesignationType("--");
					}
					
					listDto.add(dto);
				}
				resList.setData(listDto);
				resList.setRecordsFiltered(assSalList.getRecordsFiltered());
				resList.setRecordsTotal(assSalList.getRecordsTotal());
			}
			return resList;
		}
		
		@RequestMapping(value = "/getResidenceLocation",method = RequestMethod.GET)
		@ResponseBody
		public String getResidenceLocation(@RequestParam(value = "userId",required = false)Long userId,HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			StringBuilder sb = new StringBuilder();
			sb.append(candidate.getResidenceCity()).append(",").append(candidate.getResidenceStateCode());
			
			return sb.toString();
		}
		
		
		@RequestMapping(value = {"/getContributionList","/rest/getContributionList"}, method = RequestMethod.GET)
		@ResponseBody
		public List<PayrollContributionDTO> getContributionList(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			return payrollContributionService.getAllContributionTypeByLocationId(orgLocation);
		}
		
		
		
		
		/**
		 * Find all payroll Approvers
		 * @param payrollUUID
		 * @param request
		 * @param response
		 * @return ApproverDTO
		 * @throws Exception
		 */
		@RequestMapping(value = { "/findPayrollApprover","/rest/findPayrollApprover" }, method = RequestMethod.GET)
		@ResponseBody
		public List<UserDTO>  findPayrollApprover(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			List<UserDTO> userList = new ArrayList<>();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ConfigurePayrollApprover configurePayrollApprover = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
			Set<PayrollApprover>  payrollApprover = configurePayrollApprover.getPayrollApprover();
			for(PayrollApprover approver :payrollApprover ) {
				UserDTO userDTO = new UserDTO();
				userDTO.setId(approver.getId());
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(approver.getUser().getLastName())) {
					sb.append(approver.getUser().getFirstName()).append(" ").append(approver.getUser().getLastName());
				}else {
					sb.append(approver.getUser().getFirstName());
				}
				userDTO.setFullName(sb.toString());
				userList.add(userDTO);
			}
			return userList;
		}
		
		@RequestMapping(value = {"/findPayrollScheduleByUUID","/rest/findPayrollScheduleByUUID"}, method = RequestMethod.GET)
		@ResponseBody
		public  PayrollScheduleDTO findPayrollScheduleByUUID(@RequestParam(value = "payrollScheduleUUID",required = false) String payrollScheduleUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			PayrollScheduleDTO payrollScheduleDTO = new PayrollScheduleDTO();
			List<Long> notifiMemIds = new ArrayList<>();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserDTO> approvers = getApproverData(orgLocation);
			UsPayrollSchedule schedule = payrollScheduleService.getPayScheduleByUUID(payrollScheduleUUID,orgLocation.getId()) ;
			if(schedule != null) {
				String payPeriodStartdate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),schedule.getPayPeriodStartDate());
				String payCheckDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),schedule.getPayCheckDate());
				String runPayDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),schedule.getRunPayrollDate());
				payrollScheduleDTO.setPayPeriodStartDate(payPeriodStartdate);
				payrollScheduleDTO.setPayCheckDate(payCheckDate);
				payrollScheduleDTO.setRunPayDate(runPayDate);
				payrollScheduleDTO.setPayFrequency(schedule.getPayFrequency());
				payrollScheduleDTO.setNextPayMonth(schedule.getNextPayMonth());
				payrollScheduleDTO.setNotifiMemIds(notifiMemIds);
				payrollScheduleDTO.setNotifyMember(schedule.getNotifyMember());
			}
			payrollScheduleDTO.setApprovers(approvers);
			List<String> month = new ArrayList<>();
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			SimpleDateFormat sds = new SimpleDateFormat("MMM-YYYY");
			for(int h=0;h<2;h++) {
				cal.set(Calendar.YEAR,year-h);
				for(int i=0;i<12;i++) {
					cal.set(Calendar.MONTH, i);
					cal.set(Calendar.DATE,1);
					month.add(sds.format(cal.getTime()).toUpperCase());
				}
			}
			payrollScheduleDTO.setMonth(month);
			return payrollScheduleDTO;
		}

		/**
		 * populate approvers for notifying payroll schedule time
		 * @param orgLocation
		 * @return
		 * @throws Exception 
		 */
		private List<UserDTO> getApproverData(OrgLocations orgLocation) throws Exception
		{
			List<UserDTO> userList = new ArrayList<>();
			ConfigurePayrollApprover configurePayrollApprover = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
			if(configurePayrollApprover != null) {
				Set<PayrollApprover>  payrollApprover = configurePayrollApprover.getPayrollApprover();
				for(PayrollApprover approver :payrollApprover ) {
					UserDTO userDTO = new UserDTO();
					userDTO.setId(approver.getId());
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(approver.getUser().getLastName())) {
						sb.append(approver.getUser().getFirstName()).append(" ").append(approver.getUser().getLastName());
					}else {
						sb.append(approver.getUser().getFirstName());
					}
					
					UserRole uRole = userRoleService.getUserDetailsByUserIdAndLocationId(approver.getUser().getId(), orgLocation.getId());
					userDTO.setFullName(sb.toString());
					userDTO.setRole(uRole.getRoles().getDisplayName());
					userList.add(userDTO);
				}
			}
			return userList;
		}
		
		
		@RequestMapping(value = {"/checkDuplicatePaySchedule","/rest/checkDuplicatePaySchedule"}, method = RequestMethod.POST)
		@ResponseBody
		public boolean getContributionList(PayrollScheduleDTO payrollScheduleDTO, HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			boolean isPayFrequencyExisted = false;
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			UsPayrollSchedule usPayrollSchedule = payrollScheduleService.findScheduleByFrequency(payrollScheduleDTO.getPayFrequency(),orgLocation.getId(),payrollScheduleDTO.getPayrollScheduleUUID());
			if(usPayrollSchedule != null) {
				isPayFrequencyExisted = true;
			}
			return isPayFrequencyExisted;
		}
		
		@RequestMapping(value = {"/getAssociateTaxHistoryByUserId","/rest/getAssociateTaxHistoryByUserId"}, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<USPayrollAssociateTaxHistoryDTO> getAssociateTaxHistoryByUserId(@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			ResponseList<USPayrollAssociateTaxHistoryDTO> res = new ResponseList<>();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(userId,orgLocation.getId());
			List<USPayrollAssociateTaxHistoryDTO> hisList = usPayrollAssociateTaxHistoryService.getAssociateTaxHistoryByCandidateId(candidate.getId(),orgLocation.getDateFormat());
			res.setData(hisList);
			return res;
		}
		
		@RequestMapping(value = { "/getClientFiltered" }, method = RequestMethod.GET)
		public ModelAndView getClientFiltered(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("payroll/filteredclients");
			return model;
		}
		
		@RequestMapping(value = "/staffingFilteredList", method = RequestMethod.GET)
		public ModelAndView staffingFilteredList (HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("payroll/staffingFiltered");
			return model;
		}
		
		@RequestMapping(value = { "/getVendorRecruiters" }, method = RequestMethod.GET)
		public ModelAndView getVendorRecruiters(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("payroll/vendorRecruiters");
			return model;
		}
		
		@RequestMapping(value = {"/getFilteredClientList","/rest/getFilteredClientList"}, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<FilteredClientDTO>getFilteredClientList(HttpServletRequest request,HttpServletResponse response) 
		{
			ResponseList<FilteredClientDTO> res = new ResponseList<>();
			String DEST = "/home/confidential/clients_filtered.json";
			try (FileReader reader = new FileReader(DEST))
			{
				res = payrollUtil.getFilteredClientListData(reader);
			 } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		
			return res;
		}
		
		@RequestMapping(value = {"/getCategoriesForStaffingFiltered","/rest/getCategoriesForStaffingFiltered"}, method = RequestMethod.GET)
		@ResponseBody
		public Response<Map<String,String>> getCategoriesForStaffingFiltered(HttpServletRequest request,HttpServletResponse response) 
		{
			Response<Map<String,String>> res = new Response<>();
			
			String DEST = "/home/confidential/staffing_filtered.json";
			try (FileReader reader = new FileReader(DEST))
			{
				res = payrollUtil.getCategoriesForStaffingFilteredData(reader);
			 } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			return res;
		}
		
		@RequestMapping(value = {"/getStaffingFilteredListByCategoryId","/rest/getStaffingFilteredListByCategoryId"}, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<StaffingFilteredDTO> getStaffingFilteredListByCategoryId(@RequestParam(value="categoryId",required=false) String categoryId, HttpServletRequest request,HttpServletResponse response) 
		{
			ResponseList<StaffingFilteredDTO> res = new ResponseList<>();
			JSONParser jsonParser = new JSONParser();
			String DEST = "/home/confidential/staffing_filtered.json";
			try (FileReader reader = new FileReader(DEST))
			{
				res = payrollUtil.getStaffingFilteredListByCategoryIdData(reader,categoryId);
			 } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			return res;
		}

		@RequestMapping(value = {"/getVendorRecruiter","/rest/getVendorRecruiter"}, method = RequestMethod.GET)
		@ResponseBody
		public ResponseList<VendorRecruitersDTO>getVendorRecruiter(HttpServletRequest request,HttpServletResponse response) 
		{
			ResponseList<VendorRecruitersDTO> res = new ResponseList<>();
			JSONParser jsonParser = new JSONParser();
			String DEST = "/home/confidential/vendor_recruiters.json";
			try (FileReader reader = new FileReader(DEST))
			{
				res = payrollUtil.getVendorRecruiterData(reader);
			 } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		
			return res;
		}
		
	
	/**
	 * Return US Payroll Dashboard  JSP
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/uSPayrollDashboard")
	public ModelAndView uSPayrollDashboardJsp(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
			ConfigurePayrollApprover configurePayrollApprover = configurePayrollApproverService.getConfigurePayrollApproverByLocAndStatus(orgLocation);
			if (sessionData == null) {
				sessionData = new SessionMaintainanceData();
			}
			if(configurePayrollApprover != null && StringUtils.isNotBlank(configurePayrollApprover.getAutoPayrollFrequency())) {
				model.addObject("payFrequency",configurePayrollApprover.getAutoPayrollFrequency());
			}
			
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
			model.addObject("userGroupList",userGroupList);		
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("payroll/payrollCalculator");
			sessionData.setNav(Constants.PAYROLL);
			request.getSession().setAttribute("sessionData", sessionData);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
       
		@RequestMapping(value = "/savePayItemsFromSummary",method = RequestMethod.POST)
		public AssociatePayItem savePayItemsFromSummary(HttpServletRequest request,AssociateSalaryDTO associateSalaryDTO) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			return payItemService.savePayItemsFromSummary(associateSalaryDTO,orgLocation,user);
		
		}
		
		
		/**}
		 * Get Next Payroll Date by Frequency
		 * @param request
		 * @param intiateUSPayrollDTO
		 * @return CommonPayrollDataDTO
		 * @throws Exception
		 */
		@RequestMapping(value = "/getNextPayrollDate",method = RequestMethod.POST)
		public CommonPayrollDataDTO getNextPayrollDate(HttpServletRequest request,IntiateUSPayrollDTO intiateUSPayrollDTO) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			CommonPayrollDataDTO commonPayrollDataDTO = paysheetFrequencyService.getNextPayrollDate(intiateUSPayrollDTO,orgLocation.getId(),orgLocation.getDateFormat());
			List<PaysheetFrequency> paySheetFrequencyList = paysheetFrequencyService.getRecentlyRunPayrollListByFrequency(intiateUSPayrollDTO,orgLocation.getId(),orgLocation.getDateFormat());
			if(paySheetFrequencyList != null) {
				List<RecentPayrollRunDataDTO> recentDataList = new ArrayList<>();
				for(PaysheetFrequency paysheetFrequency : paySheetFrequencyList) {
					Set<EmployeePayroll> payrollList = paysheetFrequency.getEmployeepayroll();
					if(payrollList != null && payrollList.size() >0) {
						double netAmount = payrollList.stream().filter(p->"APPROVED".equalsIgnoreCase(p.getSubmitForAproverStatus())).mapToDouble(p->p.getNetPay().doubleValue()).sum(); 
					  if(netAmount > 0) {
						  RecentPayrollRunDataDTO recentPayrollRunDataDTO = new RecentPayrollRunDataDTO();
						  StringBuilder sb = new StringBuilder();
						  sb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getFrequencyStartDate())).append(" to ")
						     .append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getFrequencyEndDate()));
						  recentPayrollRunDataDTO.setPayFrequency(sb.toString());
						  recentPayrollRunDataDTO.setNetPay(new BigDecimal(netAmount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
						  recentPayrollRunDataDTO.setStartDate(paysheetFrequency.getFrequencyStartDate());
						  recentPayrollRunDataDTO.setPayrollCheckDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getPayCheckDate()));
						  recentPayrollRunDataDTO.setEndDate(paysheetFrequency.getFrequencyEndDate());
						  recentDataList.add(recentPayrollRunDataDTO);
					  }
					}
				}
				commonPayrollDataDTO.setPayrollGraphData(recentDataList);
			}
			List<PaysheetFrequency> recentPayrollRunData = paysheetFrequencyService.getRecentlyRunPayrollData(orgLocation.getId());
			if(recentPayrollRunData != null) {
				List<RecentPayrollRunDataDTO> gridDataList = new ArrayList<>();
				for(PaysheetFrequency paysheetFrequency : recentPayrollRunData) {
					Set<EmployeePayroll> payrollList = paysheetFrequency.getEmployeepayroll();
					RecentPayrollRunDataDTO recentPayrollRunDataDTO = new RecentPayrollRunDataDTO();
					recentPayrollRunDataDTO.setPaysheetFreqUUID(paysheetFrequency.getPaySheetFrequencyUUID());
					StringBuilder sb = new StringBuilder();
					  sb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getFrequencyStartDate())).append(" to ")
					     .append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getFrequencyEndDate()));
					  List<EmployeePayroll> approvedPayroll = payrollList.stream().filter(p->"APPROVED".equalsIgnoreCase(p.getSubmitForAproverStatus())).collect(Collectors.toList());
					  int dd = 0;
					  int cheque = 0;
					  BigDecimal netPay = BigDecimal.ZERO;
					  BigDecimal grossPay = BigDecimal.ZERO;
					  if(approvedPayroll.size() > 0) {
						  for(EmployeePayroll payroll : approvedPayroll) {
							  
//							  if(payroll.getSubmitForAproverStatus().equalsIgnoreCase(Constants.APPROVED))
								  recentPayrollRunDataDTO.setPayPeriodNum(payroll.getPayPeriodNum());
							  
							  if(payroll.getBankDataHistory() != null && payroll.getBankDataHistory().size() > 0) {
								  BankDataHistory bankData = payroll.getBankDataHistory().iterator().next();
								  if("Direct Deposit".equalsIgnoreCase(bankData.getAccountType())) {
									  dd++;
								  }else if("Cheque".equalsIgnoreCase(bankData.getAccountType())) {
									  cheque++; 
								  }  
							  }
							  if(payroll.getNetPay() != null) {
								  netPay = netPay.add(payroll.getNetPay());
							  }
							  
							  if(payroll.getGrossPay() != null) {
								  grossPay = grossPay.add(payroll.getGrossPay()) ;
							  }
						  }	  
						  recentPayrollRunDataDTO.setPayFrequency(sb.toString());
						  recentPayrollRunDataDTO.setPayGroupName(paysheetFrequency.getUserGroup().getGroupName());
						  recentPayrollRunDataDTO.setGrossPay(grossPay);
						  recentPayrollRunDataDTO.setPayrollCheckDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getPayCheckDate()));
						  recentPayrollRunDataDTO.setPayrollRunDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), paysheetFrequency.getCreatedDate()));
						  recentPayrollRunDataDTO.setNoOfEmployees(approvedPayroll.size());
						  recentPayrollRunDataDTO.setNoOfDirectDeposit(dd);
						  recentPayrollRunDataDTO.setNoOfCheques(cheque);
						  recentPayrollRunDataDTO.setNetPay(netPay);
						  
						  gridDataList.add(recentPayrollRunDataDTO);
					  }
				}	
				commonPayrollDataDTO.setPayrollGridData(gridDataList);
			}
			
			return commonPayrollDataDTO;
		}
}