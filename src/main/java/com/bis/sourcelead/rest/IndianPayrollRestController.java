package com.bis.sourcelead.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndApprovePayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.payroll.service.GenerateINDPayrollService;
import com.bis.sourcelead.payroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.PaymentAccountService;
import com.bis.sourcelead.service.PayrollLoanAndAdvancesService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class IndianPayrollRestController {

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
    IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	AssociateLoanAndAdvancesService associateLoanAndAdvancesService;
	
	@Autowired
	AssociatePayrollPayheadHistoryService associatePayrollPayheadHistoryService;
	
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
 			@RequestParam(value="loanAndAdvType", required=true) String loanAndAdvType){
 		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		List<PayrollLoanAndAdvancesDTO> padvDTOList = new ArrayList<>();
 		 try {
 			List<PayrollLoanAndAdvances> padvList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByTypeAndOrgLocationId(loanAndAdvType, orgLocation.getId()); 
 			if(padvList != null && !padvList.isEmpty()) {
 				for(PayrollLoanAndAdvances padv : padvList) {
 					PayrollLoanAndAdvancesDTO padvDTO = new PayrollLoanAndAdvancesDTO();
 	 				padvDTO.setId(padv.getId());
 	 				padvDTO.setName(padv.getName());
 	 				padvDTOList.add(padvDTO);
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
    

    @RequestMapping(value = { "/app/generateINDPayroll","/app/rest/generateINDPayroll" }, method = RequestMethod.POST)
	public IndPaysheetFrequencyDTO generateINDPayroll(GenerateINDPayrollDTO generateINDPayrollDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		IndPaysheetFrequencyDTO existedData = generateINDPayrollService.getPayrollData(generateINDPayrollDTO,orgLocation,loginUser);
		return generateINDPayrollService.generateINDPayroll(existedData, orgLocation, generateINDPayrollDTO, loginUser);
	}
    
    
    @RequestMapping(value = { "/app/sentForApproveIndPayroll","/app/rest/sentForApproveIndPayroll" }, method = RequestMethod.POST)
   	public IndApprovePayrollDTO sentForApproveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
   		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
   		
   		Response<IndApprovePayrollDTO> res = indPaysheetFrequencyService.sentForApproveIndPayroll(indApprovePayrollDTO, orgLocation,loginUser);
		return res.getObject();
   		
   	}
    
    
    @RequestMapping(value = { "/app/getApproveInfoIndPayroll","/app/rest/getApproveInfoIndPayroll" }, method = RequestMethod.GET)
   	public ModelAndView getApproveInfoIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	ModelAndView model = new ModelAndView();
   		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
   		
   		Response<IndPaysheetFrequencyDTO> freqResponse = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID(), orgLocation);
		model.addObject("freqResponse", freqResponse.getObject());
   		
   		Response<IndApprovePayrollDTO> approveResponse = indPaysheetFrequencyService.getApproveInfoIndPayroll(indApprovePayrollDTO, orgLocation,loginUser);
   		
   		
   		//List<AssociatePayrollPayheadHistory> allAssociatesPayrollPayheadList = associatePayrollPayheadHistoryService.getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID());
   				
		
   		
   		model.addObject("approveResponse", approveResponse.getObject());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("payroll_india/payroll/approveIndiaPayroll");
   		return model;
   	}
    
    
    @RequestMapping(value = { "app/approveIndPayroll","app/rest/approveIndPayroll" }, method = RequestMethod.POST)
	public Response<IndApprovePayrollDTO> approveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO ,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		
		Response<IndApprovePayrollDTO> res = indPaysheetFrequencyService.approveIndPayroll(indApprovePayrollDTO,orgLocation,loginUser);
		return res;

	}

    
}
