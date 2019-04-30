package com.bis.sourcelead.indPayroll.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionMasterDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.indPayroll.dao.GenerateINDPayrollDao;
import com.bis.sourcelead.indPayroll.dto.INDPayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndApprovePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.dto.PreCheckINDPayrollDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.indPayroll.service.GenerateINDPayrollService;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.PaymentAccountService;
import com.bis.sourcelead.service.PayrollLoanAndAdvancesService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.IndPaySlipPdf;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class IndianPayrollRestController {
	private static final Logger logger = LoggerFactory.getLogger(IndianPayrollRestController.class);

	@Autowired
	PaymentAccountService paymentAccountService;
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	AssociatePfEsiService associatePfEsiService;
	
	@Autowired
	PayrollLoanAndAdvancesService payrollLoanAndAdvancesService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	DepartmentService departmentService;
	
    @Autowired
	GenerateINDPayrollService generateINDPayrollService;
    
    @Autowired
    GenerateINDPayrollDao generateINDPayrollDao;

    @Autowired
    IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	AssociateLoanAndAdvancesService associateLoanAndAdvancesService;
	
	@Autowired
	AssociatePayrollPayheadHistoryService associatePayrollPayheadHistoryService;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	EmployeesService employeeService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	IndPaySlipPdf indPaySlipPdf;
	
	@Autowired
	private TaxService taxService;
	
    @RequestMapping(value = "/app/save-ind-associate-payment-account", method = RequestMethod.POST)
	public Response<IndAssociatePaymentAccountDTO> savePaymentAccount(HttpServletRequest request, IndAssociatePaymentAccountDTO account,
			@RequestParam(value="userId", required=false) Long userId){
		 Response<IndAssociatePaymentAccountDTO> resObj = new Response<>();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 try {
			 account.setOrgLocationId(orgLocation.getId());
			 if(Validator.validate(account)){
		     account.setAssociateUserId(userId);
	         PaymentAccount paymentAccount = paymentAccountService.savePaymentAccountDetails(account, orgLocation);
			 account.setPaymentAccountId(String.valueOf(paymentAccount.getId()));
			 resObj.setObject(account);
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    }
			 else {
				 resObj.setResponse(Result.INVALID_DATA.name());
				 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
				
	    	 }
		 }catch (Exception e) {
			 e.printStackTrace();
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
   }
    
    
    @RequestMapping(value = "/app/getIndAssociatePaymentAccountDetails", method = RequestMethod.POST)
	public IndAssociatePaymentAccountDTO getIndAssociatePaymentAccountDetails(HttpServletRequest request,
			@RequestParam(value="userId", required=false) Long userId){
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 IndAssociatePaymentAccountDTO paymentAccountDTO = null;
		 try {
			 Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			 PaymentAccount paymentAccount = paymentAccountService.getPaymentAccountDetailsByCandidateId(candidate.getId());
			 if(paymentAccount != null) {
				 paymentAccountDTO = new IndAssociatePaymentAccountDTO();
				 paymentAccountDTO.setPaymentAccountId(String.valueOf(paymentAccount.getId()));
				 paymentAccountDTO.setFirstName(paymentAccount.getFirstName());
				 paymentAccountDTO.setLastName(paymentAccount.getLastName());
				 paymentAccountDTO.setAccountType(paymentAccount.getAccountType());
				 paymentAccountDTO.setBankName(paymentAccount.getBankName());
				 paymentAccountDTO.setAccountNumber(paymentAccount.getAccountNumber());
				 paymentAccountDTO.setBsrNumber(paymentAccount.getBsrNumber());
				 paymentAccountDTO.setMicrCode(paymentAccount.getMicrCode());
				 paymentAccountDTO.setRoutingNumber(paymentAccount.getRoutingNumber());
			 }
		
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		 return paymentAccountDTO;
    }  
    
    
    @RequestMapping(value = "/app/getIndAssociatePFESIAccountDetails", method = RequestMethod.POST)
	public AssociatePfEsiDetailsDTO getIndAssociatePFESIAccountDetails(HttpServletRequest request,
			@RequestParam(value="userId", required=false) Long userId){
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 AssociatePfEsiDetailsDTO pfesiDTO = null;
		 try {
			 Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			 pfesiDTO = associatePfEsiService.getAssociatePfEsiDetailsDTOByCandidateId(candidate.getId(), orgLocation);
		
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		 return pfesiDTO;
    } 
    
    
    @RequestMapping(value = "/app/validateLoandAndAdvanceName", method = RequestMethod.POST)
   	public String validateLoandAndAdvanceName(HttpServletRequest request,
   			@RequestParam(value="loanAdvType", required=true) String loanAdvType,
   			@RequestParam(value="loanAdvName", required=true) String loanAdvName,
   			@RequestParam(value="uuid", required=false) String uuid){
   		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   		 String result = "false";
   		 try {
   			
   			 if(StringUtils.isNotBlank(uuid)) {
   				PayrollLoanAndAdvances padv = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByUUID(uuid);
   				if(!padv.getName().equalsIgnoreCase(loanAdvName)) {
   					List<PayrollLoanAndAdvances> padvList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByTypeAndOrgLocationId(loanAdvType, orgLocation.getId()); 
   	   				if(padvList != null && !padvList.isEmpty()) {
   	   					boolean flag = padvList.stream().anyMatch(p->p.getName().equalsIgnoreCase(loanAdvName.trim()));
   	   					if(flag == true) {
   	   						result = "true";
   	   					}
   	   				}
   				}
   			 }else {
   				List<PayrollLoanAndAdvances> padvList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByTypeAndOrgLocationId(loanAdvType, orgLocation.getId()); 
   				if(padvList != null && !padvList.isEmpty()) {
   					boolean flag = padvList.stream().anyMatch(p->p.getName().equalsIgnoreCase(loanAdvName.trim()));
   					if(flag == true) {
   						result = "true";
   					}
   				}
   			 }
   			
   		
   		 }catch (Exception e) {
   			 e.printStackTrace();
   		}
   		 return result;
       }  
    
    @RequestMapping(value = "/app/getNamesByLoanAndAdvanceType", method = RequestMethod.POST)
 	public List<PayrollLoanAndAdvancesDTO> getNamesByLoanAndAdvanceType(HttpServletRequest request,
 			@RequestParam(value="loanAndAdvType", required=true) String loanAndAdvType,
 			@RequestParam(value="associateUserId", required=true) Long associateUserId,
 			@RequestParam(value="loanAndAdvId", required=false) Long loanAndAdvId) throws Exception{
 		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		 Candidate candidate = candidateService.getCandidateByConsultantUserId(associateUserId, orgLocation.getId());
 		// Employees employees = employeeService.getEmployeeByUserAndOrgLocationId(candidate.getConsultantUser().getId(), orgLocation.getId());
 		 
 		List<PayrollLoanAndAdvancesDTO> padvDTOList = new ArrayList<>();
 		HashMap<Long,PayrollLoanAndAdvancesDTO> loanAndAdvMap = new HashMap<>();
 		 try {
 			 
 			 
 			if(loanAndAdvId != null) {
 				PayrollLoanAndAdvances padv = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesById(loanAndAdvId);
 				PayrollLoanAndAdvancesDTO padvDTO = new PayrollLoanAndAdvancesDTO();
 				padvDTO.setId(padv.getId());
 				padvDTO.setName(padv.getName());
 				loanAndAdvMap.put(padv.getId(),padvDTO);
 				padvDTOList.add(padvDTO);
 			}
 			
 			List<PayrollLoanAndAdvances> padvList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByTypeAndOrgLocationId(loanAndAdvType, orgLocation.getId()); 
 			if(padvList != null && !padvList.isEmpty()) {
 				for(PayrollLoanAndAdvances padv : padvList) {
 					PayrollLoanAndAdvancesDTO padvDTO = loanAndAdvMap.get(padv.getId());
 					if(padvDTO == null) {
 						padvDTO = new PayrollLoanAndAdvancesDTO();
 	 					Integer loanAndAdvExperienceInMonths = 0;
 	 					if(StringUtils.isNotBlank(padv.getApplicableFor()) && padv.getApplicableFor().equalsIgnoreCase("Experienced")) {
 	 							
 	 	 						 if(padv.getExperienceYears() != null) {
 	 	 							loanAndAdvExperienceInMonths = (padv.getExperienceYears() * 12);
 	 	 				 		 }
 	 	 				 		 
 	 	 				 		 if(padv.getExperienceMonths() != null) {
 	 	 				 			loanAndAdvExperienceInMonths = loanAndAdvExperienceInMonths+padv.getExperienceMonths();
 	 	 						 }
 	 					}
 	 					
 	 						padvDTO.setId(padv.getId());
 	 	 	 				padvDTO.setName(padv.getName());
 	 	 	 				loanAndAdvMap.put(padv.getId(),padvDTO);
 	 	 	 				padvDTOList.add(padvDTO);
 					}
 					
 				}
 				
 			}
 		 }catch (Exception e) {
 			 e.printStackTrace();
 		}
 		 return padvDTOList;
     } 
    
    
    @RequestMapping(value = "/app/getLoanAndAdvanceApprovers", method = RequestMethod.POST)
 	public List<UserDTO> getLoanAndAdvanceApprovers(HttpServletRequest request){
 		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		List<UserDTO> userDTOList = new ArrayList<>();
 		 try {
 			List<User> usersList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(), Long.valueOf(Constants.TAB_RUN_PAYROLL));
 			if(usersList != null && !usersList.isEmpty()) {
 				for(User user : usersList) {
 					UserDTO userDTO = new UserDTO();
 					userDTO.setId(user.getId());
 					userDTO.setUserName(user.getFullName());
 					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocation.getId());
 					userDTO.setRole(userRole.getRoles().getDisplayName());
 					userDTOList.add(userDTO);
 				}
 				
 			}
 		 }catch (Exception e) {
 			 e.printStackTrace();
 		}
 		 return userDTOList;
     }  
    
    
    @RequestMapping(value = "/app/getLoanAndAdvanceDetailsByLoanAndAdvanceId", method = RequestMethod.POST)
 	public PayrollLoanAndAdvancesDTO getLoanAndAdvanceDetailsByLoanAndAdvanceId(HttpServletRequest request,
 			@RequestParam(value="loanAndAdvId", required=true) Long loanAndAdvId){
    	
 		  PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO = new PayrollLoanAndAdvancesDTO();
 		 try {
 			PayrollLoanAndAdvances padv = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesById(loanAndAdvId);
 			if(padv != null) {
 				
 				if(padv.getMinAmount() != null) {
 					payrollLoanAndAdvancesDTO.setMinAmount(String.valueOf(padv.getMinAmount()));
 				}
 				
 				if(padv.getMaxAmount() != null) {
 					payrollLoanAndAdvancesDTO.setMaxAmount(String.valueOf(padv.getMaxAmount()));
 				}
 				
 				if(padv.getInterestRate() != null) {
 					payrollLoanAndAdvancesDTO.setInterestRate(String.valueOf(padv.getInterestRate()));
 				}
 				
 			}
 				
 		 }catch (Exception e) {
 			 e.printStackTrace();
 		}
 		 return payrollLoanAndAdvancesDTO;
     }  
    
    
	@RequestMapping(value = "/app/getLoanAndAdvanceEndDateByEffectiveDate", method = RequestMethod.GET)
	public @ResponseBody String getLoanAndAdvanceEndDateByEffectiveDate(@RequestParam(value = "effectiveDate", required = true) String effectiveDate, 
			@RequestParam(value = "noOfSchedules", required = false) String noOfSchedules,
			@RequestParam(value = "scheduler", required = false) String scheduler,HttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
		String endDateStr = null;
		try {
			
			Integer schedulerMonths = 0;
			if(scheduler.equalsIgnoreCase("Every Pay")) {
				schedulerMonths = (Integer.parseInt(noOfSchedules)*1);
			}else if(scheduler.equalsIgnoreCase("Every Quarter")) {
				schedulerMonths = (Integer.parseInt(noOfSchedules)*3);
			}else if(scheduler.equalsIgnoreCase("Half Yearly")) {
				schedulerMonths = (Integer.parseInt(noOfSchedules)*6);
			}else if(scheduler.equalsIgnoreCase("Annually")) {
				schedulerMonths = (Integer.parseInt(noOfSchedules)*12);
			}
			
			Date date = formatter.parse(effectiveDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, schedulerMonths);
			endDateStr = formatter.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDateStr;
	}
	
	
	
    @RequestMapping(value = "/app/saveAssociateLoanAndAdvanceDetails", method = RequestMethod.POST)
	public Response<AssociateLoanAndAdvancesDTO> saveAssociateLoanAndAdvanceDetails(HttpServletRequest request, 
			AssociateLoanAndAdvancesDTO loanAndAdvanceDTO){
		 Response<AssociateLoanAndAdvancesDTO> resObj = new Response<>();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 try {
			 if(Validator.validate(loanAndAdvanceDTO)){
			 AssociateLoanAndAdvances loanAndAdvances = associateLoanAndAdvancesService.storeAssociateLoanAndAdvances(loanAndAdvanceDTO, orgLocation,user);
			 loanAndAdvanceDTO.setId(loanAndAdvances.getId());
			 resObj.setObject(loanAndAdvanceDTO);
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    }
			 else {
				 resObj.setResponse(Result.INVALID_DATA.name());
				 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
				
	    	 }
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
    }
    
    
    @RequestMapping(value = "/app/verifyLoanEligibilityByAssociateIdAndLoanAdv", method = RequestMethod.POST)
	public Response<Boolean> verifyLoanEligibilityByAssociateIdAndLoanAdv(HttpServletRequest request, AssociateLoanAndAdvancesDTO loanAndAdvanceDTO){
		 Response<Boolean> resObj = new Response<>();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 try {
				 boolean isLoanAdvEligible = true;
				 PayrollLoanAndAdvances payrollLoanAndAdvances = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesById(Long.parseLong(loanAndAdvanceDTO.getLoanAndAdvName()));
				 Employees employee = employeeService.getEmployeeByUserAndOrgLocationId(Long.parseLong(loanAndAdvanceDTO.getAssociateUserId()), orgLocation.getId());
				 if(StringUtils.isNotBlank(payrollLoanAndAdvances.getApplicableFor()) && payrollLoanAndAdvances.getApplicableFor().equalsIgnoreCase("Experienced")) {
						Date joinDate = null;
						if(employee.getHireDate() != null) {
							joinDate = employee.getHireDate();
						}else {
							joinDate = employee.getCreatedDate();
						}
						Date eligibleDate = null;
						eligibleDate = DateUtils.addYears(joinDate, joinDate !=  null && payrollLoanAndAdvances.getExperienceYears() != null ? payrollLoanAndAdvances.getExperienceYears() : 0 );
						eligibleDate = DateUtils.addMonths(eligibleDate, payrollLoanAndAdvances.getExperienceMonths() != null ? payrollLoanAndAdvances.getExperienceMonths() : 0);
						if(eligibleDate != null && eligibleDate.after(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), loanAndAdvanceDTO.getEffectiveDate()))) {
							isLoanAdvEligible = false;
						}
				 }
			 resObj.setObject(isLoanAdvEligible);
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
    }
    
    
    
    @RequestMapping(value = "/app/getAssociateLoanAndAdvanceDetails", method = RequestMethod.POST)
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvanceDetails(HttpServletRequest request,
			@RequestParam(value="userId", required=false) Long userId){
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 AssociateLoanAndAdvancesDTO loanAndAdvDTO = null;
		 try {
			 Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			 loanAndAdvDTO = associateLoanAndAdvancesService.getAssociateLoanAndAssociateDetails(candidate.getId(), orgLocation);
		
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		 return loanAndAdvDTO;
    } 
    
    
    @RequestMapping(value = "/app/getAssociateLoanAndAdvanceDetailsById", method = RequestMethod.POST)
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvanceDetailsById(HttpServletRequest request,
			@RequestParam(value="loanAdvId", required=false) Long loanAdvId){
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 AssociateLoanAndAdvancesDTO loanAndAdvDTO = null;
		 try {
			 loanAndAdvDTO = associateLoanAndAdvancesService.getAssociateLoanAndAdvancesDetailsById(loanAdvId, orgLocation);
		
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		 return loanAndAdvDTO;
    } 
    
    @RequestMapping(value = "/app/deleteAssociateLoanAndAdvanceDetailsById", method = RequestMethod.POST)
	public String deleteAssociateLoanAndAdvanceDetailsById(HttpServletRequest request,
			@RequestParam(value="loanAdvId", required=false) Long loanAdvId){
		 try {
			 AssociateLoanAndAdvances loanAndAdv = associateLoanAndAdvancesService.getAssociateLoanAndAdvancesById(loanAdvId);
			 loanAndAdv.setStatus(0);
			 associateLoanAndAdvancesService.saveAssociateLoanAndAdvances(loanAndAdv);
		
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		 return "success";
    } 
    
    
    @RequestMapping(value = "/app/initiateIndPayroll", method = RequestMethod.POST)
   	public ModelAndView initiateIndPayroll(HttpServletRequest request){
    	ModelAndView modelAndView = new ModelAndView();
   		 try {
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocations);
			modelAndView.addObject("userGroupList",userGroupList);
			modelAndView.setViewName("payroll_india/payroll/initiatePayrollInd");
   		 }catch (Exception e) {
   			 e.printStackTrace();
   		}
   		 return modelAndView;
      } 
    

	@RequestMapping(value = "/app/indianPayrolluserGroupList")
	public ModelAndView userBatchList(HttpServletRequest request) throws Exception {
		   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   ModelAndView model = new ModelAndView();
		   try {
		   List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(loginOrgLocation.getId());
		   model.addObject("departmentList", departmentList);
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("payroll_india/payroll/indianPayrollGroups");
	   		 }catch (Exception e) {
	   			 e.printStackTrace();
	   		}
	   		 return model;
	      } 
    

    @SuppressWarnings("null")
	@RequestMapping(value = { "/app/generateINDPayroll","/app/rest/generateINDPayroll" }, method = RequestMethod.POST)
	public IndPaysheetFrequencyDTO generateINDPayroll(GenerateINDPayrollDTO generateINDPayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		try {
			PreCheckINDPayrollDTO preCheckINDPayrollDTO = iNDPayrollCommonUtil.payrollPrerequisiteCheck(orgLocation, generateINDPayrollDTO.getPayPeriod());
			if(preCheckINDPayrollDTO.getIsPTConfigured() && preCheckINDPayrollDTO.getIsTDSConfigured()) {
				generateINDPayrollDTO.setPreCheckINDPayrollDTO(preCheckINDPayrollDTO);
				indPaysheetFrequencyDTO =  generateINDPayrollService.generateINDPayroll(orgLocation, generateINDPayrollDTO, loginUser);
				SourceLeadSessionManager.setSessionAttribute(request, indPaysheetFrequencyDTO.getPaySheetFrequencyUUID(), indPaysheetFrequencyDTO);
				return indPaysheetFrequencyDTO;
			}else {
				indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
				indPaysheetFrequencyDTO.setPreCheckINDPayrollDTO(preCheckINDPayrollDTO);
				return indPaysheetFrequencyDTO;
			}
		}catch (Exception e) {
			return indPaysheetFrequencyDTO;
		}
	}
    
    
    @RequestMapping(value = { "/app/sentForApproveIndPayroll","/app/rest/sentForApproveIndPayroll" }, method = RequestMethod.POST)
   	public IndApprovePayrollDTO sentForApproveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
   	    Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   	    if(sessionObj != null) {
   	    	try {
   	    		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
   	    		
   	    		//exclude employees logic start
   	    		List<INDPayrollDTO> ipDTOList = new ArrayList<>();
	   	 	    for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
	   	 	    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(ipDTO.getIndEmpPayrollUUID())) {
	   	 	    		ipDTOList.add(ipDTO);
	   	 	    	}
	   	 	    }
	   	 	    indPaysheetFrequencyDTO.setIndPayrollDTO(ipDTOList);
	   	 	    //exclude employees logic end
   	    		
   	   	        Response<IndApprovePayrollDTO> res = indPaysheetFrequencyService.sentForApproveIndPayroll(indApprovePayrollDTO,indPaysheetFrequencyDTO, orgLocation,loginUser);
   	   	        SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   	   	        return res.getObject();
   	    	}catch(Exception e) {
   	    	     SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   	    	}
	    }else {
	    	 Response<IndPaysheetFrequencyDTO> resp = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID(),orgLocation);
	    	 IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = resp.getObject();
	    	 
	    	//exclude employees logic start
    		List<INDPayrollDTO> ipDTOList = new ArrayList<>();
   	 	    for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
   	 	    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(ipDTO.getIndEmpPayrollUUID())) {
   	 	    		ipDTOList.add(ipDTO);
   	 	    	}
   	 	    }
   	 	    indPaysheetFrequencyDTO.setIndPayrollDTO(ipDTOList);
   	 	    //exclude employees logic end
	   	 	    
	    	 Response<IndApprovePayrollDTO> res = indPaysheetFrequencyService.sentForApproveIndPayroll(indApprovePayrollDTO,indPaysheetFrequencyDTO, orgLocation,loginUser);
   	         SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   	         return res.getObject();
	    }
		return indApprovePayrollDTO;
   		
   	}
    
    
    @RequestMapping(value = { "/app/getApproveInfoIndPayroll","/app/rest/getApproveInfoIndPayroll" }, method = RequestMethod.GET)
   	public ModelAndView getApproveInfoIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	ModelAndView model = new ModelAndView();
   		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		try {
	   		Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
	   		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		    if(sessionObj != null) {
		    	indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
		    }else {
		    	Response<IndPaysheetFrequencyDTO> freqResponse = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID(), orgLocation);
		    	indPaysheetFrequencyDTO =freqResponse.getObject();
		    }
		    List<INDPayrollDTO> ipDTOList = new ArrayList<>();
		    for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
		    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(ipDTO.getIndEmpPayrollUUID())) {
		    		ipDTOList.add(ipDTO);
		    	}
		    }
	   		model.addObject("freqResponse",indPaysheetFrequencyDTO);
	   		
	   		List<AssociatePayrollPayheadHistoryDTO> allAssociatesPayrollPayheadList =ipDTOList.stream().map(empPayrollDTO->empPayrollDTO.getPayHeadHistoryDTOList()).flatMap(List::stream).collect(Collectors.toList());
	   		
	   		ResponseList<PayrollPayheadDTO> orgLevelPayheads = payrollPayheadService.getPayrollPayheadList(orgLocation);
	   		
	   		if(allAssociatesPayrollPayheadList != null && !allAssociatesPayrollPayheadList.isEmpty()) {
	   			for(PayrollPayheadDTO payheadDto : orgLevelPayheads.getData()) {
	   	   			
	   	   			if(payheadDto.getType().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_EARNINGS)) {
	   	   				payheadDto.setTotalForfrequencyLevel(BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_EARNINGS)).filter(ap->ap.getPayrollPayheadShortCode().equalsIgnoreCase(payheadDto.getShortCode()) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   	   			}
	   	   			if(payheadDto.getType().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION)) {
	   	   				payheadDto.setTotalForfrequencyLevel(BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION)).filter(ap->ap.getPayrollPayheadShortCode().equalsIgnoreCase(payheadDto.getShortCode()) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   	   			}
	   	   		}
	   	   		model.addObject("orgLevelPayheads", orgLevelPayheads.getData());
	   	   		model.addObject("earningTotal", BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_EARNINGS) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   	   		model.addObject("deductionTotal", BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   		}
	   		
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("payroll_india/payroll/approveIndiaPayroll");
		}catch (Exception e) {
			e.printStackTrace();
		}
   		return model;
   	}
    
    @RequestMapping(value = { "/app/removePaysheetFrequencyFromSession","/app/rest/removePaysheetFrequencyFromSession" }, method = RequestMethod.POST)
   	public String getApproveInfoIndPayroll(@RequestParam(value="paySheetFrequencyUUID", required=false) String paySheetFrequencyUUID , HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	if(StringUtils.isNotEmpty(paySheetFrequencyUUID)) {
    		SourceLeadSessionManager.removeSessionAttribute(request,paySheetFrequencyUUID);
    	}
    	return "success";
   	}
    
    
    @RequestMapping(value = { "app/approveIndPayroll","app/rest/approveIndPayroll" }, method = RequestMethod.POST)
	public Response<IndApprovePayrollDTO> approveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<IndApprovePayrollDTO> res = null;
		try {
			IndPaysheetFrequency indPaysheetFrequency = null;
			Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
		    if(sessionObj != null) {
		    	IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
		    	indPaysheetFrequency = iNDPayrollCommonUtil.getIndPaysheetFrequencyFromIndPaysheetFrequencyDTO(indPaysheetFrequencyDTO, orgLocation);
		    }else {
		    	 indPaysheetFrequency = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID());
		    }
		    SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
			 res = indPaysheetFrequencyService.approveIndPayroll(indApprovePayrollDTO,indPaysheetFrequency,orgLocation,loginUser);
		}catch(Exception e) {
			SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
		}
	 	
		return res;

	}

    
    
    @RequestMapping(value = { "app/getIndPayPeriodByGroupId","app/rest/getIndPayPeriodByGroupId" }, method = RequestMethod.GET)
	@ResponseBody
	public List<IndPaysheetFrequency> getIndPayPeriodByGroupId(@RequestParam(value = "groupId",required = false) Long groupId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<IndPaysheetFrequency> PayPeriodDTOList = userGroupService.getIndPayPeriodsByGroupId(groupId,orgLocation);
		return PayPeriodDTOList;
	}
    
    
    @RequestMapping(value = { "app/runIndPayrollregister","app/rest/runIndPayrollregister" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<IndEmployeePayrollDTO> runPayrollregister(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) {
				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
				}
				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
				
				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollRegisterReports(indEmployeePayrollDTO,orgLocations);
				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
				for(IndEmployeePayroll iepdt :payResponseList.getData()) {
					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
					dto.setEmployeeName(iepdt.getCandidate().getFullName());
					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
					BigDecimal currentHours = new BigDecimal("0");
					if(iepdt.getRegularHours() != null) {
						currentHours.add(new BigDecimal(iepdt.getRegularHours()));
					}
					if(iepdt.getOverTimeHours() != null) {
						currentHours.add(new BigDecimal(iepdt.getOverTimeHours()));
					}
					dto.setCurrentHours(currentHours);
					dto.setGrossPay(iepdt.getGrossPay());
					dto.setDeducationAmount(iepdt.getGrossPay().subtract(iepdt.getNetPay()));
					dto.setNetPay(iepdt.getNetPay());
					
					List<IndEmployeePayroll> ytdList = indPaysheetFrequencyService.getYTDIndEmployeePayrollList(iepdt.getCandidate().getId(), orgLocations.getStartMonth());
					if(ytdList != null && ytdList.size() > 0) {
						double ytdPT = ytdList.stream().mapToDouble(ap->ap != null && ap.getProfessionalTax()!= null ? ap.getProfessionalTax().doubleValue(): 0).sum();
						double ytdTDS = ytdList.stream().mapToDouble(ap->ap != null && ap.getTds() != null ? ap.getTds().doubleValue() : 0).sum();
						double ytdNetPay = ytdList.stream().mapToDouble(ap->ap != null && ap.getNetPay() != null ? ap.getNetPay().doubleValue() : 0).sum();
						dto.setYtdTaxes(new BigDecimal(ytdPT).add(new BigDecimal(ytdTDS)));
						dto.setYtdNetPay(new BigDecimal(ytdNetPay));
					}else {
						dto.setYtdTaxes(new BigDecimal(0.00));
						dto.setYtdNetPay(new BigDecimal(0.00));
					}
					indEmployeePayrollList.add(dto);
				}
				responseList.setRecordsTotal(indEmployeePayrollList.size());
				responseList.setRecordsFiltered(indEmployeePayrollList.size());
				responseList.setData(indEmployeePayrollList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
    
    
    
    
    @RequestMapping(value = { "app/exportIndPayrollRegisterReportPdf"})
	public void exportIndPayrollRegisterReportPdf(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollRegisterReports(indEmployeePayrollDTO,orgLocations);
				if(payResponseList.getData().size() > 0) {
					File file = indPaySlipPdf.getIndPayrollRegisterReportsPdf(payResponseList,orgLocations);
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					StringBuilder fileName = new StringBuilder();
					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
					response.setContentLength((int) file.length());
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
					inputStream.close();
					response.getOutputStream().close();
					response.getOutputStream().flush();
					if (file != null) {
						file.delete();
					}
				}
			}
	}
    
    
    
    
    @RequestMapping(value = { "app/indPayrollBankTranseferReport","app/rest/indPayrollBankTranseferReport" }, method = RequestMethod.POST)
   	@ResponseBody
   	public ResponseList<IndEmployeePayrollDTO> indPayrollBankTranseferReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
   		try {
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) {
   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
   			    int sortColumnIndex = 1;		 //by default sorting using updated date
   				String sortColumnOrder = "asc";  //by default descending order
   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
   				}
   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
   				
   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollBankReportReportList(indEmployeePayrollDTO,orgLocations);
   				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
   				for(IndEmployeePayroll iepdt :payResponseList.getData()) {
   					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
   					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
   					dto.setEmployeeName(iepdt.getCandidate().getFullName());
   					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
   				 
   					if(iepdt.getPaymentAccount() != null) {
   						Boolean flage = iepdt.getPaymentAccount().isDefaultAccount();
   						if(flage) {
   							dto.setBankName(iepdt.getPaymentAccount().getBankName());
   	   						dto.setAccountNumber(iepdt.getPaymentAccount().getAccountNumber());
   	   						dto.setIfscCode(iepdt.getPaymentAccount().getRoutingNumber());
   						}
   					}
   					dto.setGrossPay(iepdt.getGrossPay());
   					dto.setDeducationAmount(iepdt.getGrossPay().subtract(iepdt.getNetPay()));
   					dto.setNetPay(iepdt.getNetPay());
   					indEmployeePayrollList.add(dto);
   				}
   				responseList.setRecordsTotal(indEmployeePayrollList.size());
   				responseList.setRecordsFiltered(indEmployeePayrollList.size());
   				responseList.setData(indEmployeePayrollList);
   				responseList.setResponse(Result.SUCCESS.name());
   				responseList.setResponseMessage(Result.SUCCESS.getValue());
   			}else{
   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			responseList.setResponse(Result.AWKWARD.name());
   			responseList.setResponseMessage(Result.AWKWARD.getValue());
   		}
   		return responseList;
   	}
       
    
    @RequestMapping(value = { "app/indPayrollBankTransferReports"})
   	public void indPayrollBankTransferReports(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) { 
   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollBankReportReportList(indEmployeePayrollDTO,orgLocations);
   				if(payResponseList.getData().size() > 0) {
   					File file = null;
   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
      					 file = indPaySlipPdf.getIndPayrollBankTransferReportsPdf(payResponseList,orgLocations);
   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
      					 file = indPaySlipPdf.getIndPayrollBankTransferReportsExcel(payResponseList,orgLocations);
   					}
   					
   					
   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
   					if (mimeType == null) {
   						mimeType = "application/octet-stream";
   					}
   					response.setContentType(mimeType);
   					StringBuilder fileName = new StringBuilder();
   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
   					response.setContentLength((int) file.length());
   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
   					FileCopyUtils.copy(inputStream, response.getOutputStream());
   					inputStream.close();
   					response.getOutputStream().close();
   					response.getOutputStream().flush();
   					if (file != null) {
   						file.delete();
   					}
   				}
   			}
   	}
       
       
    @RequestMapping(value = { "app/indPayrollYTDSummaryReport","app/rest/indPayrollYTDSummaryReport" }, method = RequestMethod.POST)
   	@ResponseBody
   	public ResponseList<IndEmployeePayrollDTO> indPayrollYTDSummaryReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
   		try {
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) {
   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
   			    int sortColumnIndex = 1;		 //by default sorting using updated date
   				String sortColumnOrder = "asc";  //by default descending order
   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
   				}
   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
   				
   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollYTDSummaryReportReportList(indEmployeePayrollDTO,orgLocations);
   				Map<Long,List<IndEmployeePayroll>> mapList = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayroll::getCandidateId,Collectors.toList()));
   				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
   				for (Map.Entry<Long, List<IndEmployeePayroll>> entry : mapList.entrySet()) {
   					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
   					IndEmployeePayroll iepdt = entry.getValue().get(0);
   					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
   					dto.setEmployeeName(iepdt.getCandidate().getFullName());
   					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
   					double noOfLeaves = entry.getValue().stream().mapToDouble(i->i.getNoOfLeaves() != null ? i.getNoOfLeaves().doubleValue() : 0).sum();
   					double noOfDays = entry.getValue().stream().mapToDouble(i->i.getNoOfDays() != null ? i.getNoOfDays().doubleValue() : 0).sum();
   					double grossPay = entry.getValue().stream().mapToDouble(i->i.getGrossPay() != null ? i.getGrossPay().doubleValue() : 0).sum();
   					double netPay = entry.getValue().stream().mapToDouble(i->i.getNetPay() != null ? i.getNetPay().doubleValue() : 0).sum();
   	   				dto.setNoOfLeaves(new BigDecimal(noOfLeaves));
   	   			    dto.setNoOfDays(new BigDecimal(noOfDays));
   					dto.setGrossPay(new BigDecimal(grossPay));
   					dto.setNetPay(new BigDecimal(netPay));
   					dto.setDeducationAmount(dto.getGrossPay().subtract(dto.getNetPay()));
   					indEmployeePayrollList.add(dto);
   				}
   				responseList.setRecordsTotal(indEmployeePayrollList.size());
   				responseList.setRecordsFiltered(indEmployeePayrollList.size());
   				responseList.setData(indEmployeePayrollList);
   				responseList.setResponse(Result.SUCCESS.name());
   				responseList.setResponseMessage(Result.SUCCESS.getValue());
   			}else{
   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			responseList.setResponse(Result.AWKWARD.name());
   			responseList.setResponseMessage(Result.AWKWARD.getValue());
   		}
   		return responseList;
   	}
       
    
    @RequestMapping(value = { "app/indPayrollYTDSummaryExport"})
   	public void indPayrollYTDSummaryExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) { 
   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollYTDSummaryReportReportList(indEmployeePayrollDTO,orgLocations);
   				if(payResponseList.getData().size() > 0) {
   					File file = null;
   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
      					 file = indPaySlipPdf.getIndPayrollYTDPdf(payResponseList,orgLocations);
   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
      					 file = indPaySlipPdf.getIndPayrollYTDExcel(payResponseList,orgLocations);
   					}
   					
   					
   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
   					if (mimeType == null) {
   						mimeType = "application/octet-stream";
   					}
   					response.setContentType(mimeType);
   					StringBuilder fileName = new StringBuilder();
   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
   					response.setContentLength((int) file.length());
   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
   					FileCopyUtils.copy(inputStream, response.getOutputStream());
   					inputStream.close();
   					response.getOutputStream().close();
   					response.getOutputStream().flush();
   					if (file != null) {
   						file.delete();
   					}
   				}
   			}
   	}
       
    
    @RequestMapping(value = { "app/indPayrollAnnualTaxReport","app/rest/indPayrollAnnualTaxReport" }, method = RequestMethod.POST)
   	@ResponseBody
   	public ResponseList<IndEmployeePayrollDTO> indPayrollAnnualTaxReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
   		try {
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) {
   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
   			    int sortColumnIndex = 1;		 //by default sorting using updated date
   				String sortColumnOrder = "asc";  //by default descending order
   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
   				}
   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
   				
   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollAnnualTaxReportReportList(indEmployeePayrollDTO,orgLocations);
   				Map<Long,List<IndEmployeePayroll>> mapList = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayroll::getCandidateId,Collectors.toList()));
   				List<IndEmployeePayrollDTO> indEmployeePayrollList = new ArrayList<>();
   				for (Map.Entry<Long, List<IndEmployeePayroll>> entry : mapList.entrySet()) {
   					IndEmployeePayrollDTO dto = new IndEmployeePayrollDTO();
   					IndEmployeePayroll iepdt = entry.getValue().get(0);
   					dto.setEmployeeid(iepdt.getEmployee().getEmployeeId());
   					dto.setEmployeeName(iepdt.getCandidate().getFullName());
   					dto.setDepartmentName(iepdt.getEmployee().getDepartmentId().getDepartmentName());
   					double professionalTax = entry.getValue().stream().mapToDouble(i->i.getProfessionalTax() != null ? i.getProfessionalTax().doubleValue() : 0).sum();
   					double tds = entry.getValue().stream().mapToDouble(i->i.getTds() != null ? i.getTds().doubleValue() : 0).sum();
   					double grossPay = entry.getValue().stream().mapToDouble(i->i.getGrossPay() != null ? i.getGrossPay().doubleValue() : 0).sum();
   					double netPay = entry.getValue().stream().mapToDouble(i->i.getNetPay() != null ? i.getNetPay().doubleValue() : 0).sum();
   	   				dto.setProfessionalTax(new BigDecimal(professionalTax));
   	   			    dto.setTds(new BigDecimal(tds));
   					dto.setGrossPay(new BigDecimal(grossPay));
   					dto.setNetPay(new BigDecimal(netPay));
   					dto.setDeducationAmount(dto.getGrossPay().subtract(dto.getNetPay()));
   					indEmployeePayrollList.add(dto);
   				}
   				responseList.setRecordsTotal(indEmployeePayrollList.size());
   				responseList.setRecordsFiltered(indEmployeePayrollList.size());
   				responseList.setData(indEmployeePayrollList);
   				responseList.setResponse(Result.SUCCESS.name());
   				responseList.setResponseMessage(Result.SUCCESS.getValue());
   			}else{
   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			responseList.setResponse(Result.AWKWARD.name());
   			responseList.setResponseMessage(Result.AWKWARD.getValue());
   		}
   		return responseList;
   	}
       
    @RequestMapping(value = { "app/indPayrollAnnualTaxExport"})
   	public void indPayrollAnnualTaxExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) { 
   				ResponseList<IndEmployeePayroll> payResponseList = indPaysheetFrequencyService.getPayrollAnnualTaxReportReportList(indEmployeePayrollDTO,orgLocations);
   				if(payResponseList.getData().size() > 0) {
   					File file = null;
   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
      					 file = indPaySlipPdf.getIndPayrollAnnualTaxPdf(payResponseList,orgLocations);
   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
      					 file = indPaySlipPdf.getIndPayrollAnnualTaxExcel(payResponseList,orgLocations);
   					}
   					
   					
   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
   					if (mimeType == null) {
   						mimeType = "application/octet-stream";
   					}
   					response.setContentType(mimeType);
   					StringBuilder fileName = new StringBuilder();
   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
   					response.setContentLength((int) file.length());
   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
   					FileCopyUtils.copy(inputStream, response.getOutputStream());
   					inputStream.close();
   					response.getOutputStream().close();
   					response.getOutputStream().flush();
   					if (file != null) {
   						file.delete();
   					}
   				}
   			}
   	}
    
    @RequestMapping(value ="app/indPayrollSaleryStatementReport")
	public @ResponseBody List<IndEmployeePayrollDTO> indPayrollSaleryStatementReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request)throws Exception{
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    	List<IndEmployeePayrollDTO> salareyStatementReportList = indPaysheetFrequencyService.getPayrollSalaryStatementReportList(indEmployeePayrollDTO, orgLocations);
	    	Map<String, List<IndEmployeePayrollDTO>> map = salareyStatementReportList.stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO :: getPayHeadName,Collectors.toList()));
	    	Map<BigInteger, List<IndEmployeePayrollDTO>> canMap = salareyStatementReportList.stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO::getCanId,Collectors.toList()));
	    	List<IndEmployeePayrollDTO> mp = new ArrayList<>();
	    	 for (Map.Entry<BigInteger, List<IndEmployeePayrollDTO>> entry : canMap.entrySet())  {
	    		 IndEmployeePayrollDTO dty = new IndEmployeePayrollDTO();
	    		 Map<String,String> innerMap = new HashMap<>();
	    		 IndEmployeePayrollDTO dto = entry.getValue().get(0);
	    		 if(dto.getLastName() != null) {
	    			 dty.setEmployeeName(dto.getFirstName() +" "+ dto.getLastName());
		    	  }else {
		    		 dty.setEmployeeName(dto.getFirstName());
		          }
				  dty.setEmployeeid(dto.getEmployeeid());
				  dty.setDepartmentName(dto.getDepartmentName());
				  dty.setGrossPay(dto.getGrossPay());
				  dty.setDeducations(dto.getGrossPay().subtract(dto.getNetPay()));
				  dty.setNetPay(dto.getNetPay());
				  
				  dty.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), dto.getDeteOfJoin()));
				  for (Map.Entry<String, List<IndEmployeePayrollDTO>> canEntry : map.entrySet())  {
						  List<IndEmployeePayrollDTO> s = entry.getValue().stream().filter(pd->canEntry.getKey().equalsIgnoreCase(pd.getPayHeadName())).collect(Collectors.toList());
						  if(s.size() > 0) {
							  innerMap.put(canEntry.getKey(), String.valueOf(s.get(0).getPayHeadAmount()));
						  }else {
							  innerMap.put(canEntry.getKey(), String.valueOf(0.00));
						  }
					  }
				  dty.setPayHeadMapList(innerMap);
				  mp.add(dty);
	    	 }
	    	return mp;
    	
    } 
    
    @RequestMapping(value ="app/indPayrollESIAndPFReport", method = RequestMethod.POST)
  	public @ResponseBody ResponseList<IndEmployeePayrollDTO> indPayrollESIAndPFReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request)throws Exception{
    	ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();	
    	try {
  	  			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
  	   			if (orgLocations != null) {
  	   				String[] tableColumns = {"empId", "empName", "", "", "", "", "", "", ""};
  	   			    int sortColumnIndex = 1;		 //by default sorting using updated date
  	   				String sortColumnOrder = "asc";  //by default descending order
  	   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
  	   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
  	   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
  	   				}
  	   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
  	   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
  	   				

  	   			ResponseList<IndEmployeePayrollDTO> pfESIReportList = indPaysheetFrequencyService.getPayrollESIAndPFReportList(indEmployeePayrollDTO, orgLocations);
  	  	    	Map<BigInteger, List<IndEmployeePayrollDTO>> canMap = pfESIReportList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO::getCanId,Collectors.toList()));
  	  	    	List<IndEmployeePayrollDTO> list = new ArrayList<>();
  	  	    	
  	  	    	for (Map.Entry<BigInteger, List<IndEmployeePayrollDTO>> entry : canMap.entrySet())  {
  	  	    		 IndEmployeePayrollDTO dty = new IndEmployeePayrollDTO();
  	  	    		 IndEmployeePayrollDTO dto = entry.getValue().get(0);
  	  	    		 if(dto.getLastName() != null) {
  	  	    			 dty.setEmployeeName(dto.getFirstName() +" "+ dto.getLastName());
  	  		    	  }else {
  	  		    		 dty.setEmployeeName(dto.getFirstName());
  	  		          }
  	  				  dty.setEmployeeid(dto.getEmployeeid());
  	  				  dty.setEsiNumber(dto.getEsiNumber());
  	  				  dty.setDepartmentName(dto.getDepartmentName());
  	  				  dty.setPfNumber(dto.getPfNumber());
  	  				  dty.setUanNumber(dto.getUanNumber());
  	  				  if(dto.getEligibleForEsi() != null && dto.getEligibleForEsi().equalsIgnoreCase("Y")) {
  	  				  Double employeeESI = 0d;
  	  				  Double employerESI = 0d;
  	  				  for(IndEmployeePayrollDTO  ipd :entry.getValue()) {
  	  					  if(ipd.getEmployeeEsi() != null && ipd.getPayHeadAmount().longValue() > 0) {
  	  						  employeeESI = employeeESI + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployeeEsi())/100);
  	  					  }
  	  					  if(ipd.getEmployerEsi() != null && ipd.getPayHeadAmount().longValue() > 0) {
  	  						  employerESI = employerESI + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployerEsi())/100);
  	  					  }
  	  				  }
  	  				  dty.setEmployeeEsi(employeeESI);
  	  				  dty.setEmployerEsi(employerESI);
  	  				  dty.setTotalESI(employeeESI+employerESI);
  	  				  }
  	  				
  	  				  if(dto.getEligibleForPf() != null && dto.getEligibleForPf().equalsIgnoreCase("Y")) {
  	  				  Double employeePF = 0d;
  	  				  Double employerPF = 0d;
  	  				  for(IndEmployeePayrollDTO  ipd :entry.getValue()) {
  	  					  if(ipd.getEmployeePf() != null && ipd.getPayHeadAmount().longValue() > 0) {
  	  						  employeePF = employeePF + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployeePf())/100);
  	  					  }
  	  					  if(ipd.getEmployerPf() != null && ipd.getPayHeadAmount().longValue() > 0) {
  	  						  employerPF = employerPF + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployerPf())/100);
  	  					  }
  	  				  }
  	  				  dty.setEmployeePf(employeePF);
  	  				  dty.setEmployerPf(employerPF);
  	  				  dty.setTotalPF(employeePF+employerPF);
  	  				  }
  	  				  
  	  				  
  	  				  list.add(dty);
  	  	    	    }
  	  	    	
  	   			responseList.setRecordsTotal(list.size());
   				responseList.setRecordsFiltered(list.size());
   				responseList.setData(list);
   				responseList.setResponse(Result.SUCCESS.name());
   				responseList.setResponseMessage(Result.SUCCESS.getValue());
   			}else{
   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			responseList.setResponse(Result.AWKWARD.name());
   			responseList.setResponseMessage(Result.AWKWARD.getValue());
   		}
	return responseList;
    }
    
    
    @RequestMapping(value = { "app/indPayrollESIExport"})
   	public void indPayrollESIExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) { 
   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollESIAndPFReportList(indEmployeePayrollDTO,orgLocations);
   				if(payResponseList.getData().size() > 0) {
   					File file = null;
   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
      					 file = indPaySlipPdf.getIndPayrollESIPdf(payResponseList,orgLocations);
   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
      					 file = indPaySlipPdf.getIndPayrollESIExcel(payResponseList,orgLocations);
   					}
   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
   					if (mimeType == null) {
   						mimeType = "application/octet-stream";
   					}
   					response.setContentType(mimeType);
   					StringBuilder fileName = new StringBuilder();
   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
   					response.setContentLength((int) file.length());
   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
   					FileCopyUtils.copy(inputStream, response.getOutputStream());
   					inputStream.close();
   					response.getOutputStream().close();
   					response.getOutputStream().flush();
   					if (file != null) {
   						file.delete();
   					}
   				}
   			}
   	}
    
    
    @RequestMapping(value = { "app/indPayrollPFExport"})
   	public void indPayrollPFExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) { 
   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollESIAndPFReportList(indEmployeePayrollDTO,orgLocations);
   				if(payResponseList != null) {
   					File file = null;
   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
      					 file = indPaySlipPdf.getIndPayrollPFPdf(payResponseList,orgLocations);
   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
      					 file = indPaySlipPdf.getIndPayrollPFExcel(payResponseList,orgLocations);
   					}
   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
   					if (mimeType == null) {
   						mimeType = "application/octet-stream";
   					}
   					response.setContentType(mimeType);
   					StringBuilder fileName = new StringBuilder();
   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
   					response.setContentLength((int) file.length());
   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
   					FileCopyUtils.copy(inputStream, response.getOutputStream());
   					inputStream.close();
   					response.getOutputStream().close();
   					response.getOutputStream().flush();
   					if (file != null) {
   						file.delete();
   					}
   				}
   			}
   	}
    
 
    @RequestMapping(value = { "app/indPayrollSalaryRegisterReport","app/rest/indPayrollSalaryRegisterReport" }, method = RequestMethod.POST)
   	@ResponseBody
   	public ResponseList<IndEmployeePayrollDTO> indPayrollSalaryRegisterReport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<>();
   		try {
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) {
   				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", ""};
   			    int sortColumnIndex = 1;		 //by default sorting using updated date
   				String sortColumnOrder = "asc";  //by default descending order
   				if(indEmployeePayrollDTO.getOrder() != null && !indEmployeePayrollDTO.getOrder().isEmpty()) {
   					sortColumnIndex = Integer.parseInt(indEmployeePayrollDTO.getOrder().get(0).get("column"));
   					sortColumnOrder = indEmployeePayrollDTO.getOrder().get(0).get("dir");
   				}
   				indEmployeePayrollDTO.setSortColumn(tableColumns[sortColumnIndex]);
   				indEmployeePayrollDTO.setSortOrder(sortColumnOrder);
   				
   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollSalaryRegisterReportReportList(indEmployeePayrollDTO,orgLocations);
	   			List<IndEmployeePayrollDTO> salaryRegisterList = new ArrayList<>();
	   				for(IndEmployeePayrollDTO asi:payResponseList.getData()) {
	   					IndEmployeePayrollDTO iepd = new IndEmployeePayrollDTO();
	   					iepd.setEmployeeid(asi.getEmployeeid());
	   					if(asi.getLastName() != null && asi.getLastName() !="" && asi.getLastName() !="undefined") {
	   						iepd.setEmployeeName(asi.getFirstName()+" "+asi.getLastName());
	   					}else {
	   						iepd.setEmployeeName(asi.getFirstName());
	   					}
	   					iepd.setDepartmentName(asi.getDepartmentName());
	   					iepd.setEffectiveDate(asi.getEffectiveDate());
	   					iepd.setIncresePercentage(asi.getIncresePercentage());
	   					iepd.setAnnualSalary(asi.getAnnualSalary());
	   				    salaryRegisterList.add(iepd);
	   			  }
   				responseList.setRecordsTotal(salaryRegisterList.size());
   				responseList.setRecordsFiltered(salaryRegisterList.size());
   				responseList.setData(salaryRegisterList);
   				responseList.setResponse(Result.SUCCESS.name());
   				responseList.setResponseMessage(Result.SUCCESS.getValue());
   			}else{
   				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
   				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			responseList.setResponse(Result.AWKWARD.name());
   			responseList.setResponseMessage(Result.AWKWARD.getValue());
   		}
   		return responseList;
   	}
    
    
    
    @RequestMapping(value = { "app/indPayrollSalaryRegisterExport"})
   	public void indPayrollSalaryRegisterExport(IndEmployeePayrollDTO indEmployeePayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   			if (orgLocations != null) { 
   				ResponseList<IndEmployeePayrollDTO> payResponseList = indPaysheetFrequencyService.getPayrollSalaryRegisterReportReportList(indEmployeePayrollDTO,orgLocations);
   				if(payResponseList.getData().size() > 0) {
   					File file = null;
   					if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("pdf")) {
      					 file = indPaySlipPdf.getIndPayrollSalaryRegisterPdf(payResponseList,orgLocations);
   					}else if(indEmployeePayrollDTO.getExportType().equalsIgnoreCase("excel")) {
      					 file = indPaySlipPdf.getIndPayrollSalaryRegisterExcel(payResponseList,orgLocations);
   					}
   					
   					
   					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
   					if (mimeType == null) {
   						mimeType = "application/octet-stream";
   					}
   					response.setContentType(mimeType);
   					StringBuilder fileName = new StringBuilder();
   					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
   					response.setContentLength((int) file.length());
   					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
   					FileCopyUtils.copy(inputStream, response.getOutputStream());
   					inputStream.close();
   					response.getOutputStream().close();
   					response.getOutputStream().flush();
   					if (file != null) {
   						file.delete();
   					}
   				}
   			}
   	}
    
    
    
    @RequestMapping(value = "/app/getFromMonthNames", method = RequestMethod.POST)
   	public List<String> getMonthNames(@RequestParam(value="financialYear", required=false) String financialYear , HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//        String[] finYears  = financialYear.split("-");
        
       /* //Start Year
		 Date startDate = new SimpleDateFormat("MMMM").parse(orgLocation.getStartMonth());
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(startDate);
		 cal.set(Calendar.YEAR, Integer.valueOf(finYears[0]));
		 Date sDate = cal.getTime();
		 java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
		 
		 //End Year
		 Date endDate = new SimpleDateFormat("MMMM").parse(orgLocation.getStartMonth());
		 Calendar endCal = Calendar.getInstance();
		 endCal.setTime(endDate);
		 endCal.set(Calendar.YEAR, Integer.valueOf(finYears[1]));
		 endCal.add(Calendar.MONTH, -1);  
		 endCal.set(Calendar.DAY_OF_MONTH, 1);  
		 endCal.add(Calendar.DATE, 1);  
	     Date eeDate = endCal.getTime();
	     java.sql.Date freqEndDate = new java.sql.Date(eeDate.getTime());
	     */
	     
	     
	     List<Date> finYearList = iNDPayrollCommonUtil.getFinancialYearStartAndEndDateByOrgLocations(orgLocation, financialYear);
	     java.sql.Date freqStartDate = new java.sql.Date(finYearList.get(0).getTime());
	     java.sql.Date freqEndDate = new java.sql.Date(finYearList.get(1).getTime());
	    	String date1 = freqStartDate.toString();
	        String date2 = freqEndDate.toString();
	        

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ROOT);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM-uuuu", Locale.ROOT);
        YearMonth endMonth = YearMonth.parse(date2, dateFormatter);
        List<String> months = new ArrayList<>();
        for (YearMonth month = YearMonth.parse(date1, dateFormatter);
                ! month.isAfter(endMonth);
                month = month.plusMonths(1)) {
        	months.add(month.format(monthFormatter));
        }
		return months;
   	}
    
    
    @RequestMapping(value = "/app/getToMonthNames", method = RequestMethod.POST)
   	public List<String> getToMonthNames(@RequestParam(value="financialYear", required=false) String financialYear,
   			@RequestParam(value="fromMonth", required=false) String fromMonth,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        
        String[] selectMonth = fromMonth.split("-");
        //Start Year
		 Date startDate = new SimpleDateFormat("MMMM").parse(selectMonth[0]);
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(startDate);
		 cal.set(Calendar.YEAR, Integer.valueOf(selectMonth[1]));
		 Date sDate = cal.getTime();
		 java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
		 
		 //End Year
		 Date eeDate = null;
		 Date endDate = new SimpleDateFormat("MMMM").parse(orgLocation.getStartMonth());
		 Calendar endCal = Calendar.getInstance();
		 endCal.setTime(endDate);
		 if(financialYear.contains("-")) {
			 String[] finYears  = financialYear.split("-");
			 endCal.set(Calendar.YEAR, Integer.valueOf(finYears[1]));
			 endCal.add(Calendar.MONTH, -1);  
			 endCal.set(Calendar.DAY_OF_MONTH, 1);  
			 endCal.add(Calendar.DATE, 1);  
		      eeDate = endCal.getTime();
		 }else {
			 endCal.set(Calendar.YEAR, Integer.valueOf(financialYear));
			 endCal.set(Calendar.MONTH, 11);
			 endCal.set(Calendar.DAY_OF_MONTH, 31);
			 eeDate = endCal.getTime();
		 }
		 
	     java.sql.Date freqEndDate = new java.sql.Date(eeDate.getTime());
	     
    	String date1 = freqStartDate.toString();
        String date2 = freqEndDate.toString();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ROOT);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM-uuuu", Locale.ROOT);
        YearMonth endMonth = YearMonth.parse(date2, dateFormatter);
        List<String> months = new ArrayList<>();
        for (YearMonth month = YearMonth.parse(date1, dateFormatter);
                ! month.isAfter(endMonth);
                month = month.plusMonths(1)) {
        	months.add(month.format(monthFormatter));
        }
		return months;
   	}
    
    
    
    
    @RequestMapping(value = "/app/myPayrollInd")
	public ModelAndView paySlips(HttpServletRequest request, @RequestParam(defaultValue = "") String redirectionTab) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.MYPAYROLL, request);
			modelAndView.addObject("activeTaxTab", "myPayrollInd");
			modelAndView.addObject("redirectionTab", redirectionTab);
			modelAndView.setViewName("payroll_india/payroll/myPayrollInd");
			modelAndView.addObject("sourceleadUrl", sourceleadUrl);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	 

	@RequestMapping(value = "/app/forms/all", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<SourceleadTaxSubmissionMasterDTO> getCentralTaxes(HttpServletRequest request,
			@RequestParam(defaultValue = "") String financialYear, Paginator paginator) {
		ResponseList<SourceleadTaxSubmissionMasterDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				List<SourceleadTaxSubmissionMasterDTO> taxSubmissionMasterList = taxService.getTaxSubmissionMaster(user.getId(), orgLocations.getDateFormat(), financialYear, paginator);
				response.setData(taxSubmissionMasterList);
				response.setPaginator(paginator);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/app/payrollListSummary")
	public ModelAndView payrollListSummary(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
			modelAndView.setViewName("payroll_india/org/payrollListSummary");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/app/runPayroll")
	public ModelAndView runPayroll(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocations);
			modelAndView.addObject("userGroupList",userGroupList);
			modelAndView.setViewName("payroll_india/org/runPayroll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping (value = "/app/runIndianPayRoll")
	public ModelAndView indianRunPayRoll(@RequestParam(defaultValue = "") String stab,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
		modelAndView.addObject("stab",stab);
		modelAndView.addObject("sourceleadUrl", sourceleadUrl);
		try {
			modelAndView.setViewName("payroll_india/payroll/runPayrollInd");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping (value = "/app/getLoanNames", method = RequestMethod.GET)
	@ResponseBody
	public List<PayrollLoanAndAdvances> getLoanNames(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<PayrollLoanAndAdvances> padvList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByOrgLocationId(orgLocation.getId()); 
	 return padvList;
	
	}
	
}
