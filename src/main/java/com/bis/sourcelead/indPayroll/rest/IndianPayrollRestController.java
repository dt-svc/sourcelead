package com.bis.sourcelead.indPayroll.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.core.internal.runtime.PrintStackUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesHistoryDTO;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionMasterDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserGroupDTO;
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
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.dto.PreCheckINDPayrollDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.indPayroll.service.GenerateINDPayrollService;
import com.bis.sourcelead.indPayroll.service.IndPayrollService;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeeSessionIndPayroll;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
	@Autowired
    private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private IndPayrollService  indPayrollService;
	
	
	
    @RequestMapping(value = "/save-ind-associate-payment-account", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/getIndAssociatePaymentAccountDetails", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/getIndAssociatePFESIAccountDetails", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/validateLoandAndAdvanceName", method = RequestMethod.POST)
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
    
    @RequestMapping(value = "/getNamesByLoanAndAdvanceType", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/getLoanAndAdvanceApprovers", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/getLoanAndAdvanceDetailsByLoanAndAdvanceId", method = RequestMethod.POST)
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
    
    
	@RequestMapping(value = "/getLoanAndAdvanceEndDateByEffectiveDate", method = RequestMethod.GET)
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
	
	
	
    @RequestMapping(value = "/saveAssociateLoanAndAdvanceDetails", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/verifyLoanEligibilityByAssociateIdAndLoanAdv", method = RequestMethod.POST)
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
    
    
    
    @RequestMapping(value = "/getAssociateLoanAndAdvanceDetails", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/getAssociateLoanAndAdvanceDetailsById", method = RequestMethod.POST)
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
    
    @RequestMapping(value = "/deleteAssociateLoanAndAdvanceDetailsById", method = RequestMethod.POST)
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
    
    
    @RequestMapping(value = "/initiateIndPayroll", method = RequestMethod.POST)
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
    

	@RequestMapping(value = "/indianPayrolluserGroupList")
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
    

	
	/**
	 * Initiate payroll
	 * @param generateINDPayrollDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = { "/generateINDPayroll","/rest/generateINDPayroll" }, method = RequestMethod.POST)
	public IndPaysheetFrequencyDTO generateINDPayroll(GenerateINDPayrollDTO generateINDPayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
		try {
			System.out.println(" inisde generateINDPayroll   >>>>>>>>>>>>>");
			PreCheckINDPayrollDTO preCheckINDPayrollDTO = iNDPayrollCommonUtil.payrollPrerequisiteCheck(orgLocation, generateINDPayrollDTO.getPayPeriod());
			if(preCheckINDPayrollDTO.getIsPTConfigured() && preCheckINDPayrollDTO.getIsTDSConfigured()) {
				generateINDPayrollDTO.setPreCheckINDPayrollDTO(preCheckINDPayrollDTO);
				indPaysheetFrequencyDTO =  generateINDPayrollService.generateINDPayroll(orgLocation, generateINDPayrollDTO, loginUser);
				
				if(indPaysheetFrequencyDTO != null) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("groupName", indPaysheetFrequencyDTO.getGroupName());
				jsonObject.put("frequencyStartDateStr", indPaysheetFrequencyDTO.getFrequencyStartDateStr());
				jsonObject.put("frequencyEndDateStr", indPaysheetFrequencyDTO.getFrequencyEndDateStr());
				jsonObject.put("payCheckDateStr", indPaysheetFrequencyDTO.getPayCheckDateStr());
				jsonObject.put("approverStatus", indPaysheetFrequencyDTO.getApproverStatus());
				jsonObject.put("payRunId", indPaysheetFrequencyDTO.getPayRunId());
				jsonObject.put("groupId", indPaysheetFrequencyDTO.getGroupId());
				jsonObject.put("createDate", indPaysheetFrequencyDTO.getCreatedDate());
				jsonObject.put("createdBy", indPaysheetFrequencyDTO.getCreatedBy());
				jsonObject.put("actionPerformedBy", indPaysheetFrequencyDTO.getActionPerformedBy());
				jsonObject.put("orgLocationId", indPaysheetFrequencyDTO.getOrgLocationId());
				jsonObject.put("updatedBy", indPaysheetFrequencyDTO.getUpdatedBy());
				jsonObject.put("updatedDate", indPaysheetFrequencyDTO.getUpdatedDate());
				
				Gson gson = new GsonBuilder().create();
				JSONObject employeeJsonObject = new JSONObject();
	   	 	    for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
	   	 	    	EmployeeSessionIndPayroll empSession = new EmployeeSessionIndPayroll();
	   	 	    	
		   	 	    	if(StringUtils.isNotBlank(ipDTO.getEmpId())) {
		   	 	    		employeeJsonObject.put("empId", ipDTO.getEmpId());
		   	 	    	}else {
		   	 	    		employeeJsonObject.put("empId", "");
		   	 	    	}
	   	 	           employeeJsonObject.put("employee", ipDTO.getEmployee());
	   	 	           employeeJsonObject.put("empName", ipDTO.getEmpName());
	   	 	           employeeJsonObject.put("regularHours", ipDTO.getRegularHours());
	   	 	           employeeJsonObject.put("overtimeHours", ipDTO.getOvertimeHours());
	   	 	           employeeJsonObject.put("totalCtc", ipDTO.getTotalCtc());
	   	 	           employeeJsonObject.put("grossPay", ipDTO.getGrossPay());
	   	 	           employeeJsonObject.put("netPay", ipDTO.getNetPay());
	   	 	           employeeJsonObject.put("lopAmount", ipDTO.getLopAmount());
	   	 	           employeeJsonObject.put("regularHrsAmt", ipDTO.getRegularHrsAmt());
	   	 	           employeeJsonObject.put("noOfLeaves", ipDTO.getNoOfLeaves());
	   	 	           employeeJsonObject.put("noOfDays", ipDTO.getNoOfDays());
	   	 	           employeeJsonObject.put("totalHours", ipDTO.getTotalHours());
	   	 	           employeeJsonObject.put("candidateId", ipDTO.getCandidateId());
	   	 	           employeeJsonObject.put("approverStatus", ipDTO.getApproverStatus());
	   	 	           employeeJsonObject.put("employeeDesignationType", ipDTO.getEmployeeDesignationType());
	   	 	           employeeJsonObject.put("tds", ipDTO.getTds());
	   	 	           employeeJsonObject.put("professionalTax", ipDTO.getProfessionalTax());
	   	 	           employeeJsonObject.put("payType", ipDTO.getPayType());
	   	 	           employeeJsonObject.put("indEmpPayrollUUID", ipDTO.getIndEmpPayrollUUID());
	   	 	           employeeJsonObject.put("payrollPaySlipInd", ipDTO.getPayrollPaySlipInd());
	   	 	           employeeJsonObject.put("paySheetFrequencyUUID", ipDTO.getPaySheetFrequencyUUID());
	   	 	           employeeJsonObject.put("payCheckDate", ipDTO.getPayCheckDate());
	   	 	           employeeJsonObject.put("createdDate", ipDTO.getCreatedDate());
	   	 	           employeeJsonObject.put("createdBy", ipDTO.getCreatedBy());
	   	 	           employeeJsonObject.put("updatedDate", ipDTO.getUpdatedDate());
	   	 	           employeeJsonObject.put("updatedBy", ipDTO.getUpdatedBy());
	   	 	           
	   	 	           empSession.setExcludeUUID(ipDTO.getIndEmpPayrollUUID());
					   empSession.setPaysheetUUID(ipDTO.getPaySheetFrequencyUUID());
					   empSession.setJsonIndEmployeePayrollCache(employeeJsonObject.toString());
					   empSession.setJsonString(jsonObject.toString());
	   	 	    
						/*if(!ipDTO.getEmpId().isEmpty() && ipDTO.getEmpId() != null) {
							empSession.setEmployeeId(ipDTO.getEmpId());
						}else {
							empSession.setEmployeeId(null);
						}
						empSession.setEmployeeName(ipDTO.getEmpName());
						empSession.setRegularHours(ipDTO.getRegularHours());
						empSession.setGrossPay(ipDTO.getGrossPay());
						empSession.setTds(ipDTO.getTds());
						empSession.setNoOfLeaves(ipDTO.getNoOfLeaves());
						empSession.setNetPay(ipDTO.getNetPay());
						empSession.setOvertimeHours(ipDTO.getOvertimeHours());
						empSession.setExcludeUUID(ipDTO.getIndEmpPayrollUUID());
						empSession.setPaysheetUUID(ipDTO.getPaySheetFrequencyUUID());*/

						if(!ipDTO.getPayHeadHistoryDTOList().isEmpty() ) {
							empSession.setJsonPayHeadHistory(gson.toJson(ipDTO.getPayHeadHistoryDTOList()));
						}else {
							empSession.setJsonPayHeadHistory(null);
						}
						if(!ipDTO.getLoanAdvHistoryDTOList().isEmpty()) {
							empSession.setJsonLoanAdvHistory(gson.toJson(ipDTO.getLoanAdvHistoryDTOList()));
						}else {
							empSession.setJsonLoanAdvHistory(null);
						}
						empSession.setJsonEmployee(gson.toJson(indPaysheetFrequencyDTO.getIndPayrollDTO()));
						indPayrollService.saveEmployeeSessionIndPayroll(empSession);
				}
				}
// 				In wars session is not working due to this created temporary table (IND_PAYROLL_CACHE) for saving getting the data across the multiple requests   
//              Storing the list of INDPayrollDTO values in the  IND_PAYROLL_CACHE and also dependency dto's are saving in the form of JSONString 
// 				Dependency columns JSON_STRING - > Payroll related basic information like netpay,empName,grosPay etc...
// 				Dependency columns JSON_PAYHEADHISTORY -> storing list of AssociatePayrollPayheadHistoryDTO information
// 				Dependency columns JSON_LOANADVHISTORY -> storing of AssociateLoanAndAdvancesHistoryDTO information 
//				SourceLeadSessionManager.setSessionAttribute(request, indPaysheetFrequencyDTO.getPaySheetFrequencyUUID(), indPaysheetFrequencyDTO);
				
				System.out.println(" completed generateINDPayroll   >>>>>>>>>>>>>");
				return indPaysheetFrequencyDTO;
			}else {
				indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
				indPaysheetFrequencyDTO.setPreCheckINDPayrollDTO(preCheckINDPayrollDTO);
				return indPaysheetFrequencyDTO;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return indPaysheetFrequencyDTO;
		}
	}
    
	/**
	 * Send payroll for Approval
	 * @param indApprovePayrollDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = { "/sentForApproveIndPayroll","/rest/sentForApproveIndPayroll" }, method = RequestMethod.POST)
   	public IndApprovePayrollDTO sentForApproveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	
    	Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
   //	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//   	    Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   		List<EmployeeSessionIndPayroll> emp = indPayrollService.getEmployeeSessionList(indApprovePayrollDTO.getPaySheetFrequencyUUID());
		 ObjectMapper mapper = new ObjectMapper();
		
   	    if(emp != null && emp.size() > 0) {
   	    	try {
   	    		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
//   	    		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
				  indPaysheetFrequencyDTO = iNDPayrollCommonUtil.indPaySheetFrequencyDTODetails(emp,indPaysheetFrequencyDTO);

				  
   	    		/*//exclude employees logic start
   	    		List<INDPayrollDTO> ipDTOList = new ArrayList<>();
	   	 	    for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
	   	 	    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(ipDTO.getIndEmpPayrollUUID())) {
	   	 	    		ipDTOList.add(ipDTO);
	   	 	    	}
	   	 	    }*/
				//generate the list of employes required to run the payroll 
				List<INDPayrollDTO> ipDTOList = iNDPayrollCommonUtil.genarateTheListOfEmployeforIntitatePayroll(emp,indApprovePayrollDTO,mapper);
				
	   	 	    indPaysheetFrequencyDTO.setIndPayrollDTO(ipDTOList);
	   	 	    //exclude employees logic end
   	    		
   	   	        Response<IndApprovePayrollDTO> res = indPaysheetFrequencyService.sentForApproveIndPayroll(indApprovePayrollDTO,indPaysheetFrequencyDTO, orgLocation,loginUser);
//   	   	        SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   	   	        return res.getObject();
   	    	}catch(Exception e) {
   	    		e.printStackTrace();
//   	    	     SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
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
//   	         SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
   	         return res.getObject();
	    }
		return indApprovePayrollDTO;
   		
   	}
    
    
    /**
     * Confirm payroll
     * @param indApprovePayrollDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("null")
	@RequestMapping(value = { "/getApproveInfoIndPayroll","/rest/getApproveInfoIndPayroll" }, method = RequestMethod.GET)
   	public ModelAndView getApproveInfoIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	ModelAndView model = new ModelAndView();
   		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		try {
			
			List<EmployeeSessionIndPayroll> emp = indPayrollService.getEmployeeSessionList(indApprovePayrollDTO.getPaySheetFrequencyUUID());
			 ObjectMapper mapper = new ObjectMapper();
			IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
			List<INDPayrollDTO> ipDTOList = new ArrayList<>();
//	 		Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indPaysheetFrequencyDTO.getPaySheetFrequencyUUID());
		    
			if(emp != null && emp.size() > 0) {
				  indPaysheetFrequencyDTO = iNDPayrollCommonUtil.indPaySheetFrequencyDTODetails(emp,indPaysheetFrequencyDTO);
				  ipDTOList = iNDPayrollCommonUtil.genarateTheListOfEmployeforIntitatePayroll(emp,indApprovePayrollDTO,mapper);
			}else {
				Response<IndPaysheetFrequencyDTO> freqResponse = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID(), orgLocation);
		    	indPaysheetFrequencyDTO =freqResponse.getObject();
		    	
		    	  for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
				    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(ipDTO.getIndEmpPayrollUUID())) {
				    		ipDTOList.add(ipDTO);
				    	}
				    }
		    	  
		    	System.out.println(" freqResponse data inside getApproveInfoIndPayroll   >>>>>>>>>>>>>" +freqResponse);
			}
			
			
			/*System.out.println(" inside getApproveInfoIndPayroll   >>>>>>>>>>>>>");
	   		Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
	   		System.out.println(" after sessionObj getApproveInfoIndPayroll   >>>>>>>>>>>>>" +sessionObj);
	   		
		    if(sessionObj != null) {
		    	indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
		     }else {
		    	Response<IndPaysheetFrequencyDTO> freqResponse = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID(), orgLocation);
		    	indPaysheetFrequencyDTO =freqResponse.getObject();
		    	System.out.println(" freqResponse data inside getApproveInfoIndPayroll   >>>>>>>>>>>>>" +freqResponse);
		    }
		    List<INDPayrollDTO> ipDTOList = new ArrayList<>();
		    for(INDPayrollDTO ipDTO : indPaysheetFrequencyDTO.getIndPayrollDTO()) {
		    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(ipDTO.getIndEmpPayrollUUID())) {
		    		ipDTOList.add(ipDTO);
		    	}
		    }*/
		    
		   //generate the list of employes required to run the payroll 
		    
		   
	   		model.addObject("freqResponse",indPaysheetFrequencyDTO);
	   		List<AssociatePayrollPayheadHistoryDTO> allAssociatesPayrollPayheadList = ipDTOList.stream()
	   																				  .map(empPayrollDTO->empPayrollDTO.getPayHeadHistoryDTOList())
	   																				  .flatMap(List::stream).collect(Collectors.toList());
	   		
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
	   	   		model.addObject("earningTotal", BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_EARNINGS) && ap.getTaxStatus().equals(Constants.TAXABLE) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   	   		model.addObject("employerContribution", BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_EARNINGS) && ap.getTaxStatus().equals(Constants.NON_TAXABLE) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   	   		
	   	   		model.addObject("deductionTotal", BigDecimal.valueOf(allAssociatesPayrollPayheadList.stream().filter(ap->ap.getPayrollPayheadType().equals(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION) && ap.getMonthly()!=null).mapToDouble(ap->ap.getMonthly().doubleValue()).sum()));
	   		}
	   		
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("payroll_india/payroll/approveIndiaPayroll");
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" completed  getApproveInfoIndPayroll   >>>>>>>>>>>>>");
   		return model;
   		
   	}
    
    @RequestMapping(value = { "/removePaysheetFrequencyFromSession","/rest/removePaysheetFrequencyFromSession" }, method = RequestMethod.POST)
   	public String getApproveInfoIndPayroll(@RequestParam(value="paySheetFrequencyUUID", required=false) String paySheetFrequencyUUID , HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	if(StringUtils.isNotEmpty(paySheetFrequencyUUID)) {
    		SourceLeadSessionManager.removeSessionAttribute(request,paySheetFrequencyUUID);
    	}
    	return "success";
   	}
    
    
    /**
     * Approve the Payroll
     * @param indApprovePayrollDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("null")
	@RequestMapping(value = { "/approveIndPayroll","/rest/approveIndPayroll" }, method = RequestMethod.POST)
	public Response<IndApprovePayrollDTO> approveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	Long orgLocationId = (Long)SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
    	OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<IndApprovePayrollDTO> res = null;
		try {
			IndPaysheetFrequency indPaysheetFrequency = null;
//			Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
			
			List<EmployeeSessionIndPayroll> emp = indPayrollService.getEmployeeSessionList(indApprovePayrollDTO.getPaySheetFrequencyUUID());
			ObjectMapper mapper = new ObjectMapper();
			IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();

			if(emp != null && emp.size() > 0) {
				  indPaysheetFrequencyDTO = iNDPayrollCommonUtil.indPaySheetFrequencyDTODetails(emp,indPaysheetFrequencyDTO);

				   List<INDPayrollDTO> ipDTOList = iNDPayrollCommonUtil.genarateTheListOfEmployeforIntitatePayroll(emp,indApprovePayrollDTO,mapper);
				
				   indPaysheetFrequencyDTO.setIndPayrollDTO(ipDTOList);
		    	indPaysheetFrequency = iNDPayrollCommonUtil.getIndPaysheetFrequencyFromIndPaysheetFrequencyDTO(indPaysheetFrequencyDTO, orgLocation);
		    }else {
		    	
		    	 indPaysheetFrequency = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID());
//		    		Exclude logic
		    	  Set<IndEmployeePayroll> payrollList = new HashSet<IndEmployeePayroll>();
				    for(IndEmployeePayroll payroll : indPaysheetFrequency.getIndEmployeepayroll()) {
				    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(payroll.getIndEmpPayrollUUID())) {
				    		payrollList.add(payroll);
				    	}
				    }
					indPaysheetFrequency.setIndEmployeepayroll(payrollList);
		    }

			 res = indPaysheetFrequencyService.approveIndPayroll(indApprovePayrollDTO,indPaysheetFrequency,orgLocation,loginUser);
		}catch(Exception e) {
			e.printStackTrace();
//			SourceLeadSessionManager.removeSessionAttribute(request, indApprovePayrollDTO.getPaySheetFrequencyUUID());
		}
	 	
		return res;

	}

    @RequestMapping(value = "/myPayrollInd")
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
	 

	@RequestMapping(value = "/forms/all", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/payrollListSummary")
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
	
	@RequestMapping(value = "/runPayroll")
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

	@RequestMapping (value = "/runIndianPayRoll")
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
	
	
	/**
	 * @author Laxman
	 * @param userGroupDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUserGroupIdOrGroupName" , method = RequestMethod.GET)
	public Response<UserGroup> validateUserGroupIdOrGroupName(UserGroupDTO userGroupDTO,HttpServletRequest request)throws Exception {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	return userGroupService.validateUserGroupIdOrGroupName(orgLocations,userGroupDTO);
	}
	
	 /**"
	  * create user group
	  * @param userGroupDTO
	  * @param request
	  * @return UserGroup
	  * @throws Exception
	  */
	 @RequestMapping(value = "/createUserGroup" , method = RequestMethod.POST)
	 public Response<UserGroup> createUserGroup(UserGroupDTO userGroupDTO,HttpServletRequest request)throws Exception {
		  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		  return userGroupService.createUserGroup(orgLocations,userGroupDTO,loginUser);
	 }
	 
	 
	 
	 @RequestMapping(value = "/change-associate-active-payment-account", method = RequestMethod.POST)
	    @ResponseBody
	    public com.bis.sourcelead.dto.Response changeAssociateActiveAccount(HttpServletRequest request,  long id) {
	        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        response.setResponse(Result.INVALID_DATA.name());
	        response.setResponseMessage(Result.INVALID_DATA.getValue());
	        if (orgLocations != null) {
	            int flag = orgService.changePaymentAccountStatus(id, orgLocations.getId());
	            if (flag > 0) {
	                response.setResponse(Result.SUCCESS.name());
	                response.setResponseMessage(Result.SUCCESS.getValue());
	            } else if (flag < 0) {
	                response.setResponse(Result.DUPLICATE_EMAIL.name());
	                response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
	            } else if (flag == 0) {
	                response.setResponse(Result.FAILED.name());
	                response.setResponseMessage(Result.FAILED.getValue());
	            }
	        } else {
	            response.setResponse(Result.SESSION_TIMEDOUT.name());
	            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	        }
	        return response;
	    }
	 
	  @RequestMapping(value = "/change-associate-default-payment-account", method = RequestMethod.POST)
	    @ResponseBody
	    public com.bis.sourcelead.dto.Response changeassociateDefaultAccount(HttpServletRequest request, 
	            long id,long candidateId, boolean defaultAccount) {
	        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        response.setResponse(Result.INVALID_DATA.name());
	        response.setResponseMessage(Result.INVALID_DATA.getValue());
	        if (orgLocations != null) {
	            int flag = orgService.changeAssociateDefaultPaymentAccount(id,candidateId, defaultAccount);
	            if (flag > 0) {
	                response.setResponse(Result.SUCCESS.name());
	                response.setResponseMessage(Result.SUCCESS.getValue());
	            } else if (flag < 0) {
	                response.setResponse(Result.DUPLICATE_EMAIL.name());
	                response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
	            } else if (flag == 0) {
	                response.setResponse(Result.FAILED.name());
	                response.setResponseMessage(Result.FAILED.getValue());
	            }
	        } else {
	            response.setResponse(Result.SESSION_TIMEDOUT.name());
	            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	        }
	        return response;
	    }
	  
	  @RequestMapping (value = "/getLoanNames", method = RequestMethod.GET)
		@ResponseBody
		public List<PayrollLoanAndAdvances> getLoanNames(HttpServletRequest request,HttpServletResponse response) throws Exception{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<PayrollLoanAndAdvances> padvList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByOrgLocationId(orgLocation.getId()); 
		 return padvList;
		
		}
	  
}
