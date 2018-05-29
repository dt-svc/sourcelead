package com.bis.sourcelead.payroll.rest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.PayItem;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayItemService;
import com.bis.sourcelead.payroll.service.PayrollContributionService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.CommonUtil;
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
		 * Returning US Payroll Generate JSP
		 * @param request
		 * @param response
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = { "/usPayroll","/rest/usPayroll" })
		public ModelAndView usPayroll(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
			model.addObject("userGroupList",userGroupList);
			model.setViewName("payroll/usPayroll");
			return model;
		}
		
		@RequestMapping(value = { "/generateUSPayroll","/rest/generateUSPayroll" }, method = RequestMethod.POST)
		public ResponseList<USPayrollDTO> generateUSPayroll(GenerateUSPayrollDTO generateUSPayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ResponseList<USPayrollDTO> res = generateUSPayrollService.generateUSPayroll(orgLocation,generateUSPayrollDTO);
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
	@RequestMapping(value = { "/getUsersVGroupPayPeriod","/rest/getUsersVGroupPayPeriod" }, method = RequestMethod.GET)
	public List<String> getUsersVGroupPayPeriod(@RequestParam(value = "groupId", required = false) String groupUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<String> listDates = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<UserGroupDTO> userGroup = userGroupService.findUserGroupById(groupUUID);
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
	private List<String> getNumberOfWeeks(int year, int month1, OrgLocations orgLocation)
	{
		List<String> payFrequency = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<List<String>> weekdates = new ArrayList<List<String>>();
		List<String> dates;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		for (int i = 0; i < month1; i++) {
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
		ResponseList<PayItemDTO> resObj = payItemService.getPayItemByLocationId(orgLocation);
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
}
