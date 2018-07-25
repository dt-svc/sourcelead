package com.bis.sourcelead.payroll.rest;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.AssociateSalaryHistoryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dao.impl.ApproverDTO;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayAdjustmentDTO;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPaysheetDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.PayItem;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
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
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.service.AssociateSalaryHistoryService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserGroupService;
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

	@Autowired
	AssociateSalaryService associateSalaryService;

	
	@Autowired
	AssociateSalaryHistoryService associateSalaryHistoryService;
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	PayrollAdjustmentService payrollAdjustmentService; 
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;
	
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
	public boolean deleteSelectedContributionType(PayrollContributionDTO payrollContributionDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (payrollContributionDTO.getContributionId() !=0 ) {
			return payrollContributionService.deleteContributionById(payrollContributionDTO.getContributionId());
		}
		return false;
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
	public boolean deleteSelectedContributionType(PayrollDeduction payrollDeduction, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (payrollDeduction.getId() !=0 ) {
			return payrollDeductionService.deleteDeductionById(payrollDeduction.getId());
		}
		return false;
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
		if (StringUtils.isNoneBlank(payrollDeductionDTO.getPayrollDeductionType())) {
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
		public AssociateBankDataDTO findAssociateBankDataByUserId(@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			AssociateBankDataDTO  associateBankDataDTO = associateBankDataService.findAssociateBankDataByUserId(userId,orgLocation);	
			return associateBankDataDTO;
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
			AssociateTaxDTO  associateTaxDTO = associateTaxService.findAssociateTaxSummaryByUserId(userId,orgLocation);	
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
			ResponseList<DeductionDTO>  res = associateDeductionService.findAllDeductionSummaryByUserId(userId,orgLocation);	
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
			ResponseList<ContributionDTO>  res = associateContributionService.findAllContributionSummaryByUserId(userId,orgLocation);	
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
			if(existedData.getPayrollDTO() != null && existedData.getPayrollDTO().size() >0) {
				return existedData;
			}else {
				return generateUSPayrollService.generateUSPayroll(orgLocation,generateUSPayrollDTO,loginUser);
			}
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
	public List<String> getUsersVGroupPayPeriod(@RequestParam(value = "groupUUID", required = false) String groupUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<String> listDates = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String [] str = groupUUID.split(",");
		Response<UserGroupDTO> userGroup = userGroupService.findUserGroupById(str[0]);
		if ("Weekly".equalsIgnoreCase(userGroup.getObject().getPayFrequency())) {
			Calendar now = Calendar.getInstance(); // Gets the current date and
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			listDates = getNumberOfWeeks(year, month, orgLocation);
		}
		return listDates;
	}
		
		
		/**
		 * Get all week start date and end date of year
		 * @param year
		 * @param month1
		 * @param orgLocation 
		 * @return List<List<String>> 
		 */
	private List<String> getNumberOfWeeks(int year, int month, OrgLocations orgLocation)
	{
		List<String> payFrequency = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<List<String>> weekdates = new ArrayList<List<String>>();
		List<String> dates;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		for (int i = 0; i <=month; i++) {
			c.set(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, 1);
			while (c.get(Calendar.MONTH) == i) {
				dates = new ArrayList<String>();
				while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					c.add(Calendar.DAY_OF_MONTH, -1);
				}
				dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), c.getTime()));
				c.add(Calendar.DAY_OF_MONTH, 6);
				dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), c.getTime()));
				weekdates.add(dates);
				c.add(Calendar.DAY_OF_MONTH, 7);
			}
		}
		for (List<String> innerList : weekdates) {
			StringBuilder sb = new StringBuilder();
			sb.append(innerList.get(0)).append(",").append(innerList.get(1));
			payFrequency.add(sb.toString());
		}
		return payFrequency;
	}
	
//	
	
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
		AssociateSalaryDTO  associateSalaryDTO = associateSalaryService.findAssociateSalaryByUserId(userId,orgLocation);
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
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<AssociateSalaryHistoryDTO>  associateSalaryHisDTO = associateSalaryHistoryService.findAssociateSalHistory(associateSalid,orgLocation);
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
	public ModelAndView addAssociateDeduction(HttpServletRequest request,@RequestParam(value = "userId",required = false) Long userId) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("userId", userId);
		model.setViewName("users/addAssociateSummaryDeduction");
		return model;
	}
	
	@RequestMapping(value = "/addAssociateContribution")
	public ModelAndView addAssociateContribution(HttpServletRequest request,@RequestParam(value = "userId",required = false) Long userId) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("userId", userId);
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
		associatePayItem.setEndDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),associatePayItemDTO.getEndDate()));
		associatePayItem.setPayItemAmount(new BigDecimal(associatePayItemDTO.getPayItemAmount()));
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
	public Response<USPayrollDTO> getEmployeePayroll(@RequestParam(value = "payrollUUID",required = false) String payrollUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<USPayrollDTO>  resObj = generateUSPayrollService.getEmployeePayrollByUUID(payrollUUID,orgLocation);
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
	public ModelAndView getPayrollList(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("payroll/payrollList");
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
	public ResponseList<PaysheetFrequencyDTO> getPaySheetFrequencyList(PaysheetFrequencyDTO paysheetFrequencyDTO, @RequestParam(value = "paySheetFrequencyUUID", required = false) String searchValue, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return uSPaysheetService.getPaySheetFrequencyListByOrgLocationId(orgLocation, paysheetFrequencyDTO);
	}
	
	/**
	 * 
	 * @param paySheetFrequencyUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaySheetFrequencyData")
	public ModelAndView viewPaySheetFrequencyData(@RequestParam(value = "paySheetFrequencyUUID",required = true) String paySheetFrequencyUUID,@RequestParam(value = "approverStatus",required = true) String approverStatus,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("paySheetFrequencyUUID", paySheetFrequencyUUID);
		if(approverStatus.equalsIgnoreCase(Constants.SENT)) {
			model.setViewName("payroll/confirmPayroll");
		}else {
			model.setViewName("payroll/payrollSummary");
		}
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
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
	@RequestMapping(value = "/getPaySheetFrequencySummary")
	public List<USPayrollDTO> getPaySheetFrequencySummary(@RequestParam(value = "uuid",required = true) String paySheetFrequencyUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return uSPaysheetService.getPaySheetFrequencySummary(paySheetFrequencyUUID);
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
		List<UserDTO>  userlist = configurePayrollApproverService.getPayrollApprover(orgLocation,loginUser);
		ConfigurePayrollApprover  configurePayrollApprover = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
		approverDTO.setUser(userlist);
		List<Long> approverList = configurePayrollApprover.getPayrollApprover().stream().map(ap->ap.getUser().getId()).collect(Collectors.toList());
		approverDTO.setPayrollApprover(approverList);
		approverDTO.setPayrollType(configurePayrollApprover.getPayrollType());
		approverDTO.setAutomaticPayroll(configurePayrollApprover.getAutomaticPayroll());
		approverDTO.setConfAprrover(String.valueOf(configurePayrollApprover.getId()));
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
		Response<PayrollApproverDTO> res = paysheetFrequencyService.sendPayrollForApprover(payrollApproverDTO,orgLocation);
		return res;
	}
}
