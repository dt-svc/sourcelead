package com.bis.sourcelead.indPayroll.utill;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesHistoryDTO;
import com.bis.sourcelead.dto.IndPayrollPayheadUtilDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.PayrollTaxDTO;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.indPayroll.dto.INDPayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndApprovePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.dto.PreCheckINDPayrollDTO;
import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.indPayroll.entity.AssociateSalaryHistoryInd;
import com.bis.sourcelead.indPayroll.entity.AssociateSalaryPayheadHistoryInd;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.entity.EmployeeSessionIndPayroll;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesHistoryService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.PayrollTaxService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * This is the commonutil for the certain common methods used throughout the application
 * @author: Naresh Bolisetty
 * 
 */
/**
 * @author Venkat Kesav
 *
 */
@Component
public class INDPayrollCommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(INDPayrollCommonUtil.class);
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${paymentSuccessReturnEmail}")
	private String paymentSuccessReturnEmail;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Value("${pmsUrl}")
	private String pmsUrl;
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	AssociatePayrollPayheadHistoryService associatePayrollPayheadHistoryService;
	
	@Autowired
	AssociateLoanAndAdvancesService associateLoanAndAdvancesService;
	
	@Autowired
	AssociateLoanAndAdvancesHistoryService associateLoanAndAdvancesHistoryService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PayrollTaxService payrollTaxService;
	
	@Autowired
	private TaxService taxService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LeaveService leaveService;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	public List<PayrollPayheadDTO> setPayrollPayheadList(List<PayrollPayhead> payrollPayheadList,OrgLocations orgLocations) throws Exception {
		List<PayrollPayheadDTO> payrollPayheadDTOList = new ArrayList<>();
		if(payrollPayheadList != null && !payrollPayheadList.isEmpty()) {
			for(PayrollPayhead payRollPayhead : payrollPayheadList) {
				PayrollPayheadDTO payheadDTO = new PayrollPayheadDTO();
				payheadDTO.setEffectiveDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payRollPayhead.getEffectiveDate()));
				payheadDTO.setId(payRollPayhead.getId());
				payheadDTO.setName(payRollPayhead.getName());
				payheadDTO.setShortCode(payRollPayhead.getShortCode());
				payheadDTO.setType(payRollPayhead.getType());
				payheadDTO.setComponent(payRollPayhead.getComponent());
				payheadDTO.setTax(payRollPayhead.getTax());
				payheadDTO.setUuid(payRollPayhead.getUuid());
				payheadDTO.setActive(payRollPayhead.getActive());
				
				
				if(Arrays.asList(Constants.BASIC_DEPENDENT_CONSTANT_PAYHEAD_SHORTCODES).contains(payRollPayhead.getShortCode())) {
					payheadDTO.setMethod("% of Payheads");
					PayrollTaxDTO payrollTaxDTO = 	payrollTaxService.getPayrollTaxConfigurationByOrgLocationId(orgLocations.getId());
					if(payrollTaxDTO != null && payRollPayhead.getShortCode().equalsIgnoreCase("ESI")) {
						payheadDTO.setFormula(payrollTaxDTO.getEmployeeESIContribution()+"%(BA)+"+payrollTaxDTO.getEmployerESIContribution()+"%(BA)");
					}
					if(payrollTaxDTO != null && payRollPayhead.getShortCode().equalsIgnoreCase("PF")) {
						payheadDTO.setFormula(payrollTaxDTO.getEmployeePFContribution()+"%(BA)+"+payrollTaxDTO.getEmployerPFContribution()+"%(BA)");
					}
				}else {
					payheadDTO.setMethod(payRollPayhead.getMethod());
					payheadDTO.setFormula(payRollPayhead.getFormula());
				}
				payrollPayheadDTOList.add(payheadDTO);
			}
		}
		return payrollPayheadDTOList;
	}
	
	
	
/*	// Below method is the sending mail when user request for approe the ayroll (mail to approver)

@Override
public void sendMailToIndianPayrollApprover(User loginUser,  OrgLocations orgLoc,) throws Exception {
	
	 EmailMessage emailMessage = new EmailMessage();
	    
		 emailMessage.setToEmail( );
		 emailMessage.setFromEmail(loginUser.getEmail());
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(orgLoc.getId());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder employee =new StringBuilder();
			 globalVarsMap.put("loginUser", employee.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("loginUser",loginUser.getFirstName());
		 }
	
			   globalVarsMap.put("batchName",);
			   globalVarsMap.put("noOfAssociates",);
		       globalVarsMap.put("frequency",);
		       globalVarsMap.put("amount",);globalVarsMap.put("frequency",);
		       globalVarsMap.put("loginOrg",);

		       StringBuilder stringBuildforLink = new StringBuilder();
			globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_REQUEST_FOR_APROVING_INDIAN_PAYROLL_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_REQUEST_FOR_APROVING_INDIAN_PAYROLL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLoc.getOrg(),orgLoc,Constants.USER_REQUEST_FOR_APROVING_INDIAN_PAYROLL_EMAIL);
			
		 String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,emailScenario);
			 notificationScenario.setBody(notificationBody);
			 StringBuilder notificationLink = new StringBuilder();
			 String actionLink="";
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();

			emailScenerioUtil.sendNotification(actionLink, loginUser,  orgLoc, notificationScenario,new HashMap<String,String>());
	}
}

// Below method is the sending mail when approver apprves the indian payroll (mail to user)
public void sendMailToUserFromndIanPayrolApprover(User loginUser,  OrgLocations orgLoc,) throws Exception {
	
	 EmailMessage emailMessage = new EmailMessage();
	    
		 emailMessage.setToEmail( );
		 emailMessage.setFromEmail(loginUser.getEmail());
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(orgLoc.getId());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder employee =new StringBuilder();
			 globalVarsMap.put("loginUser", employee.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("loginUser",loginUser.getFirstName());
		 }
	
			   globalVarsMap.put("batchName",);
			   globalVarsMap.put("noOfAssociates",);
		       globalVarsMap.put("frequency",);
		       globalVarsMap.put("amount",);globalVarsMap.put("frequency",);
		       globalVarsMap.put("loginOrg",);

		       StringBuilder stringBuildforLink = new StringBuilder();
			globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_MAIL_TO_USER_REGARDING_INDIAN_PAYROLL_APPROVE_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_MAIL_TO_USER_REGARDING_INDIAN_PAYROLL_APPROVE_NOIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLoc.getOrg(),orgLoc,Constants.USER_REQUEST_FOR_APROVING_INDIAN_PAYROLL_EMAIL);
			
		 String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,emailScenario);
			 notificationScenario.setBody(notificationBody);
			 StringBuilder notificationLink = new StringBuilder();
			 String actionLink="";
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();

			emailScenerioUtil.sendNotification(actionLink, loginUser,  orgLoc, notificationScenario,new HashMap<String,String>());
	}
}

	*/
	
	
	public IndPaysheetFrequency getIndPaysheetFrequencyFromIndPaysheetFrequencyDTO(IndPaysheetFrequencyDTO paysheetFrequencyDTO,OrgLocations orgLocation) throws Exception {
		IndPaysheetFrequency indPaysheetFrequency = null;
		if(paysheetFrequencyDTO != null) {
			indPaysheetFrequency = new IndPaysheetFrequency();
			if(paysheetFrequencyDTO.getId() != null) {
				indPaysheetFrequency.setId(paysheetFrequencyDTO.getId());
			}
			
			indPaysheetFrequency.setPaySheetFrequencyUUID(paysheetFrequencyDTO.getPaySheetFrequencyUUID());
			if(paysheetFrequencyDTO.getOrgLocationId() != null) {
				OrgLocations orgLocations = new OrgLocations();
				orgLocations.setId(paysheetFrequencyDTO.getOrgLocationId());
				indPaysheetFrequency.setOrgLocation(orgLocations);
			}
			if(StringUtils.isNotEmpty(paysheetFrequencyDTO.getPayRunId())) {
				indPaysheetFrequency.setPayRunId(Long.parseLong(paysheetFrequencyDTO.getPayRunId()));
			}
			
			if(StringUtils.isNotEmpty(paysheetFrequencyDTO.getGroupId())) {
				UserGroup userGroup = new UserGroup();
				userGroup.setId(Long.parseLong(paysheetFrequencyDTO.getGroupId()));
				userGroup.setGroupName(paysheetFrequencyDTO.getGroupName());
				indPaysheetFrequency.setUserGroup(userGroup);
			}
			if(StringUtils.isNotEmpty(paysheetFrequencyDTO.getFrequencyStartDateStr())) {
				indPaysheetFrequency.setFrequencyStartDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),paysheetFrequencyDTO.getFrequencyStartDateStr()));
			}
			
			if(StringUtils.isNotEmpty(paysheetFrequencyDTO.getFrequencyEndDateStr())) {
				indPaysheetFrequency.setFrequencyEndDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),paysheetFrequencyDTO.getFrequencyEndDateStr()));
			}
			
			if(StringUtils.isNotEmpty(paysheetFrequencyDTO.getPayCheckDateStr())) {
				indPaysheetFrequency.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),paysheetFrequencyDTO.getPayCheckDateStr()));
			}
			
			//indPaysheetFrequency.setApproverStatus(paysheetFrequencyDTO.getApproverStatus());
			indPaysheetFrequency.setStatus(paysheetFrequencyDTO.getStatus());
		    
			List<IndEmployeePayroll> payrollList = getIndEmployeePayrollFromINDPayrollDTO(indPaysheetFrequency,paysheetFrequencyDTO.getIndPayrollDTO(),orgLocation);
			if(payrollList != null && !payrollList.isEmpty()) {
				indPaysheetFrequency.setIndEmployeepayroll(new HashSet<IndEmployeePayroll>(payrollList));
			}
			indPaysheetFrequency.setCreatedBy(paysheetFrequencyDTO.getCreatedBy());
		    indPaysheetFrequency.setUpdatedBy(paysheetFrequencyDTO.getUpdatedBy());
		    indPaysheetFrequency.setUpdatedDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),paysheetFrequencyDTO.getUpdatedDate()));
		    indPaysheetFrequency.setCreatedDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),paysheetFrequencyDTO.getCreatedDate()));
		    indPaysheetFrequency.setActionPerformedBy(paysheetFrequencyDTO.getActionPerformedBy());
		}
		 return indPaysheetFrequency;
	}
	
	public IndPaysheetFrequencyDTO getIndPaysheetFrequencyDTOFromIndPaysheetFrequency(IndPaysheetFrequency paysheetFrequency,OrgLocations orgLocation) throws Exception {
		IndPaysheetFrequencyDTO	indPaysheetFrequencyDTO = null;
		if(paysheetFrequency != null) {
			indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
			indPaysheetFrequencyDTO.setId(paysheetFrequency.getId());
			indPaysheetFrequencyDTO.setPaySheetFrequencyUUID(paysheetFrequency.getPaySheetFrequencyUUID());
			indPaysheetFrequencyDTO.setOrgLocationId(paysheetFrequency.getOrgLocation().getId());
			indPaysheetFrequencyDTO.setPaySheetFrequencyId(paysheetFrequency.getId());
			indPaysheetFrequencyDTO.setPayRunId(paysheetFrequency.getPayRunId().toString());
			indPaysheetFrequencyDTO.setGroupId(paysheetFrequency.getUserGroup().getId().toString());
		    indPaysheetFrequencyDTO.setGroupName(paysheetFrequency.getUserGroup().getGroupName());
		    indPaysheetFrequencyDTO.setFrequencyStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),paysheetFrequency.getFrequencyStartDate()));
		    indPaysheetFrequencyDTO.setFrequencyEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),paysheetFrequency.getFrequencyEndDate()));
		    indPaysheetFrequencyDTO.setPayCheckDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),paysheetFrequency.getPayCheckDate()));
		    indPaysheetFrequencyDTO.setApproverStatus(paysheetFrequency.getApproverStatus());
		    indPaysheetFrequencyDTO.setStatus(paysheetFrequency.getStatus());
		    
		    List<INDPayrollDTO> iNDPayrollDTOList =new ArrayList<>();
		    List<INDPayrollDTO> dtoList =    getINDPayrollDTOFromIndEmployeePayroll(paysheetFrequency.getIndEmployeepayroll(),paysheetFrequency.getOrgLocation());
		   for(  INDPayrollDTO obj : dtoList) {
			   if(paysheetFrequency.getApproverStatus().equalsIgnoreCase(obj.getApproverStatus()))
				   iNDPayrollDTOList.add(obj);
		   }
		    indPaysheetFrequencyDTO.setIndPayrollDTO(iNDPayrollDTOList);
		    
		    indPaysheetFrequencyDTO.setCreatedBy(paysheetFrequency.getCreatedBy());
		    indPaysheetFrequencyDTO.setUpdatedBy(paysheetFrequency.getUpdatedBy());
		    indPaysheetFrequencyDTO.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),paysheetFrequency.getUpdatedDate()));
		    indPaysheetFrequencyDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),paysheetFrequency.getCreatedDate()));
		    indPaysheetFrequencyDTO.setActionPerformedBy(paysheetFrequency.getActionPerformedBy());
		}
		 return indPaysheetFrequencyDTO;
	}
	
	public List<INDPayrollDTO> getINDPayrollDTOFromIndEmployeePayroll(Set<IndEmployeePayroll> payrollList,OrgLocations orgLocations) throws Exception {
		List<INDPayrollDTO> payrollDTOList = new ArrayList<>();
		System.out.println(" inside getINDPayrollDTOFromIndEmployeePayroll   >>>>>>>>>>>>>");
		if(payrollList != null && !payrollList.isEmpty()) {		
			for(IndEmployeePayroll employeePayroll :payrollList) {
				if(employeePayroll != null && employeePayroll.getStatus() == 0)
					continue;
					
				INDPayrollDTO payrollDTO = new INDPayrollDTO();
				if(employeePayroll.getId() != null) {
					payrollDTO.setId(employeePayroll.getId());
				}
				payrollDTO.setIndEmpPayrollUUID(employeePayroll.getIndEmpPayrollUUID());
				if(employeePayroll != null && employeePayroll.getEmployee() != null) {
					payrollDTO.setEmpId(employeePayroll.getEmployee().getEmployeeId());
				}else {
					payrollDTO.setEmpId("");
				}
				
				if(employeePayroll != null && employeePayroll.getEmployee() != null) {
					payrollDTO.setEmployee(employeePayroll.getEmployee().getId());
				}
				
				if(employeePayroll != null && employeePayroll.getPayrollPaySlipInd() != null) {
					payrollDTO.setPayrollPaySlipInd(employeePayroll.getPayrollPaySlipInd().getId());
				}
				
				if(employeePayroll != null && employeePayroll.getEmployee() != null && employeePayroll.getEmployee().getUser() != null) {
					payrollDTO.setEmpName(employeePayroll.getEmployee().getUser().getFullName());
				}else {
					payrollDTO.setEmpName("");
				}
				
				if(employeePayroll != null && employeePayroll.getCandidate() != null) {
					payrollDTO.setEmployeeDesignationType(employeePayroll.getCandidate().getDesignationType());
					payrollDTO.setCandidateId(employeePayroll.getCandidate().getId());
				}
				Set<AssociatePayrollPayheadHistory> payHeadHistorySet = null;
				if(employeePayroll != null && employeePayroll.getId() != null) {
					List<AssociatePayrollPayheadHistory> payHeadHistoryList = associatePayrollPayheadHistoryService.getLatestAssociatePayrollPayheadByEmpPayrollId(employeePayroll.getId());
				   if(payHeadHistoryList != null && !payHeadHistoryList.isEmpty()) {
					   payHeadHistorySet = new HashSet<>(payHeadHistoryList);
				   }
				
				}else {
					payHeadHistorySet = employeePayroll.getAssociatePayrollPayheadHistory();
				}
						
				List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList = getAssociatePayrollPayheadHistoryDTOListFromAssociatePayrollPayheadHistoryList(employeePayroll, payHeadHistorySet, orgLocations);
				payrollDTO.setPayHeadHistoryDTOList(payHeadHistoryDTOList);
				
				
				// for loan and advances
				Set<AssociateLoanAndAdvanceHistory> loanAdvHistorySet = employeePayroll.getLoanHistory();
				payrollDTO.setLoanAdvHistoryDTOList(getAssociateLoanAndAdvancesHistoryDTOListFromAssociateLoanAndAdvancesHistoryList(employeePayroll, loanAdvHistorySet, orgLocations));
				
				
				//get yearly taxable amount in current frequency
				BigDecimal totalCtcAmountGrossPay = new BigDecimal("0.00");
				for(AssociatePayrollPayheadHistory associatePayrollPayheadHistory : payHeadHistorySet) {
					if(Constants.IND_PAYROLL_PAYHEAD_EARNINGS.equalsIgnoreCase(associatePayrollPayheadHistory.getPayrollPayhead().getType())) {
						if(Constants.TAXABLE.equalsIgnoreCase(associatePayrollPayheadHistory.getPayrollPayhead().getTax())) {
							
							totalCtcAmountGrossPay = totalCtcAmountGrossPay.add(associatePayrollPayheadHistory.getMonthly());
						}
					}
				}
				payrollDTO.setTotalCtc(totalCtcAmountGrossPay);
				
				//Monthly deduction total 
				BigDecimal totalDeductions = new BigDecimal("0.00");
				for(AssociatePayrollPayheadHistory associatePayrollPayheadHistory : payHeadHistorySet) {
					if(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION.equalsIgnoreCase(associatePayrollPayheadHistory.getPayrollPayhead().getType())) {
						totalDeductions = totalDeductions.add(associatePayrollPayheadHistory.getMonthly());
					}
				}
				payrollDTO.setNetPay(totalCtcAmountGrossPay.subtract(totalDeductions));
				
				payrollDTO.setRegularHours(employeePayroll.getRegularHours());
				payrollDTO.setRegularHrsAmt(employeePayroll.getRegularHoursAmount());
				payrollDTO.setOvertimeHours(employeePayroll.getOverTimeHours());
				payrollDTO.setOtHoursAmt(employeePayroll.getOverTimeAmount());
				payrollDTO.setNoOfLeaves(employeePayroll.getNoOfLeaves());
				payrollDTO.setNoOfDays(employeePayroll.getNoOfDays());
				payrollDTO.setGrossPay(employeePayroll.getGrossPay());
				payrollDTO.setLopAmount(employeePayroll.getLopAmount());
				payrollDTO.setTds(employeePayroll.getTds());
				payrollDTO.setProfessionalTax(employeePayroll.getProfessionalTax());
				payrollDTO.setApproverStatus(employeePayroll.getSubmitForAproverStatus());
				payrollDTO.setPayType(employeePayroll.getPayType());
				payrollDTO.setPaySheetFrequencyUUID(employeePayroll.getIndPaysheetFrequency().getPaySheetFrequencyUUID());
				payrollDTO.setPayCheckDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), employeePayroll.getPayCheckDate()));
				payrollDTO.setCreatedBy(employeePayroll.getCreatedBy());
				payrollDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), employeePayroll.getCreatedDate()));
				payrollDTO.setUpdatedBy(employeePayroll.getUpdatedBy());
				payrollDTO.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),employeePayroll.getUpdatedDate()));
				payrollDTOList.add(payrollDTO);
			}
		}
		System.out.println(" completed getINDPayrollDTOFromIndEmployeePayroll   >>>>>>>>>>>>>");
		 return payrollDTOList;
	}
	
 public List<IndEmployeePayroll> getIndEmployeePayrollFromINDPayrollDTO(IndPaysheetFrequency indPaysheetFrequency,List<INDPayrollDTO> payrollDTOList,OrgLocations orgLocations) throws Exception {
		
		List<IndEmployeePayroll> payrollList = new ArrayList<>();
		if(payrollDTOList != null && !payrollDTOList.isEmpty()) {
			for(INDPayrollDTO payrollDTO :payrollDTOList) {
				IndEmployeePayroll employeePayroll = new IndEmployeePayroll();
				if(payrollDTO.getId() != null) {
					employeePayroll.setId(payrollDTO.getId());
				}
				employeePayroll.setIndEmpPayrollUUID(payrollDTO.getIndEmpPayrollUUID());
				employeePayroll.setIndPaysheetFrequency(indPaysheetFrequency);
				
				if(payrollDTO.getEmployee() != null) {
					Employees employee = new Employees();
					employee.setId(payrollDTO.getEmployee());
					employeePayroll.setEmployee(employee);
				}
				if(payrollDTO.getPayrollPaySlipInd() != null) {
					PayrollPaySlipInd payrollPaySlipInd = new PayrollPaySlipInd();
					payrollPaySlipInd.setId(payrollDTO.getPayrollPaySlipInd());
					employeePayroll.setPayrollPaySlipInd(payrollPaySlipInd);
				}
				if(payrollDTO.getCandidateId() != null) {
					Candidate candidate = new Candidate();
					candidate.setId(payrollDTO.getCandidateId());
					employeePayroll.setCandidate(candidate);
				}
				employeePayroll.setRegularHours(payrollDTO.getRegularHours());
				employeePayroll.setRegularHoursAmount(payrollDTO.getRegularHrsAmt());
				employeePayroll.setOverTimeHours(payrollDTO.getOvertimeHours());
				employeePayroll.setOverTimeAmount(payrollDTO.getOtHoursAmt());
				employeePayroll.setNoOfLeaves(payrollDTO.getNoOfLeaves());
				employeePayroll.setNoOfDays(payrollDTO.getNoOfDays());
				employeePayroll.setGrossPay(payrollDTO.getGrossPay());
				employeePayroll.setNetPay(payrollDTO.getNetPay());
				employeePayroll.setTds(payrollDTO.getTds());
				employeePayroll.setProfessionalTax(payrollDTO.getProfessionalTax());
				employeePayroll.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollDTO.getPayCheckDate()));
				employeePayroll.setCreatedBy(payrollDTO.getCreatedBy());
				employeePayroll.setCreatedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollDTO.getCreatedDate()));
				employeePayroll.setUpdatedBy(payrollDTO.getUpdatedBy());
				employeePayroll.setUpdatedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollDTO.getUpdatedDate()));
//				employeePayroll.setSubmitForAproverStatus(payrollDTO.getApproverStatus());
				employeePayroll.setStatus(indPaysheetFrequency.getStatus());
				List<AssociatePayrollPayheadHistory> payHeadHistoryList = null;
				if(employeePayroll != null && employeePayroll.getId() != null) {
					 payHeadHistoryList = associatePayrollPayheadHistoryService.getLatestAssociatePayrollPayheadByEmpPayrollId(employeePayroll.getId());
				
				}else {
					payHeadHistoryList =  getAssociatePayrollPayheadHistoryListFromAssociatePayrollPayheadHistoryDTOList(employeePayroll,payrollDTO.getPayHeadHistoryDTOList(), orgLocations);
				}
			    if(payHeadHistoryList != null && !payHeadHistoryList.isEmpty()) {
			    	employeePayroll.setAssociatePayrollPayheadHistory(new HashSet<AssociatePayrollPayheadHistory>(payHeadHistoryList));
			    }
			    
			    //for loan and advances
			    List<AssociateLoanAndAdvanceHistory> loanAdvHistoryList  =  getAssociateLoanAndAdvanceHistoryListFromAssociateLoanAndAdvancesHistoryDTOList(employeePayroll,payrollDTO.getLoanAdvHistoryDTOList(), orgLocations);
			    if(loanAdvHistoryList != null && !loanAdvHistoryList.isEmpty()) {
			    	employeePayroll.setLoanHistory(new HashSet<AssociateLoanAndAdvanceHistory>(loanAdvHistoryList));
			    }
				
				payrollList.add(employeePayroll);
			}
		}
		 return payrollList;
	}
	public IndPayrollPayheadUtilDTO getAssociatePayrollPayHeadHistoryFromAssociateSalaryPayHeads(Set<AssociateSalaryPayheadInd> payheadList,IndEmployeePayroll empPayroll,User loginUser,String payFrequency, BigDecimal payAdjustmentConst) throws Exception {
		IndPayrollPayheadUtilDTO utilDTO = new IndPayrollPayheadUtilDTO();
		BigDecimal grossPay = BigDecimal.ZERO;
		BigDecimal allDeductions = BigDecimal.ZERO;
		BigDecimal tds = BigDecimal.ZERO;
		BigDecimal pt = BigDecimal.ZERO;
		BigDecimal netPay = BigDecimal.ZERO;
		if(StringUtils.isEmpty(payFrequency)) {
			payFrequency = Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY;
		}
		if(payheadList != null && !payheadList.isEmpty()) {
			HashSet<AssociatePayrollPayheadHistory> payHeadHistoryList = new HashSet<>();
			for(AssociateSalaryPayheadInd payHead : payheadList) {
				if(payHead.getMonthly() != null && payHead.getAnnualy() != null) {
					AssociatePayrollPayheadHistory payHeadHistory = new AssociatePayrollPayheadHistory();
					payHeadHistory.setIndEmployeePayrollId(empPayroll);
					payHeadHistory.setPayrollPayhead(payHead.getPayrollPayhead());
					
					if(payHead.getPayrollPayhead() != null 
						&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getShortCode())
						&& payHead.getPayrollPayhead().getShortCode().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_TDS)) {
						continue;
					}else if(payHead.getPayrollPayhead() != null 
							&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getShortCode())
							&& payHead.getPayrollPayhead().getShortCode().equalsIgnoreCase(Constants.IND_PAYROLL_PROFESSIONAL_TAX)) {
						continue;
					}
					
					if(payHead.getPayrollPayhead() != null 
							&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getType())
							&& payHead.getPayrollPayhead().getType().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_EARNINGS)) {
						
						if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_ANUALLY) && payHead.getAnnualy() != null) {
							grossPay = grossPay.add(payHead.getAnnualy().multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}else if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY) && payHead.getMonthly() != null) {
							grossPay = grossPay.add(payHead.getMonthly().multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}else  {
							if(payHead.getAnnualy() != null)
							grossPay = grossPay.add(payHead.getAnnualy().divide(getProjectInvoiceFrequency(payFrequency), 2)).multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
						}
					}
					
					if(payHead.getPayrollPayhead() != null 
							&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getType())
							&& payHead.getPayrollPayhead().getType().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION)) {
						if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_ANUALLY) && payHead.getAnnualy() != null) {
							allDeductions = allDeductions.add(payHead.getAnnualy().multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}else if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY) && payHead.getMonthly() != null) {
							allDeductions = allDeductions.add(payHead.getMonthly().multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}else  {
							if(payHead.getAnnualy() != null)
							allDeductions = allDeductions.add(payHead.getAnnualy().divide(getProjectInvoiceFrequency(payFrequency), 2)).multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
						}
					}
					
					payHeadHistory.setMonthly(payHead.getMonthly().multiply("Y".equalsIgnoreCase(payHead.getPayrollPayhead().getLeaveDependent()) ? payAdjustmentConst : new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					payHeadHistory.setAnnualy(payHead.getAnnualy());
					payHeadHistory.setStatus(payHead.getStatus());
					
					payHeadHistory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		
					payHeadHistory.setCreatedBy(loginUser);
					payHeadHistory.setUpdatedBy(loginUser);
					
					payHeadHistory.setCreatedDate(new Date());
					payHeadHistory.setUpdatedDate(new Date());
					payHeadHistoryList.add(payHeadHistory);
				}
			}
			utilDTO.setAssociatePayrollPayheadHistory(payHeadHistoryList);
			utilDTO.setTds(tds);
			utilDTO.setProfessionalTax(pt);
			utilDTO.setGrossPay(grossPay);
		    netPay = grossPay.subtract(allDeductions);
			utilDTO.setNetPay(netPay);
		}
		return utilDTO;
	}
	
	public List<AssociatePayrollPayheadHistoryDTO> getAssociatePayrollPayheadHistoryDTOListFromAssociatePayrollPayheadHistoryList(IndEmployeePayroll indEmployeePayroll,Set<AssociatePayrollPayheadHistory> payHeadHistoryList,OrgLocations orgLocations) throws Exception {
		List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList = new ArrayList<>();
		if(payHeadHistoryList != null && !payHeadHistoryList.isEmpty()) {
			for(AssociatePayrollPayheadHistory payHeadHistory : payHeadHistoryList) {
				AssociatePayrollPayheadHistoryDTO  payHeadHistoryDTO = new AssociatePayrollPayheadHistoryDTO();
				payHeadHistoryDTO.setIndEmployeePayrollId(indEmployeePayroll.getId());
				payHeadHistoryDTO.setIndEmpPayrollUUID(indEmployeePayroll.getIndEmpPayrollUUID());
				payHeadHistoryDTO.setIndEmployeePayrollStatus(indEmployeePayroll.getStatus());
				if(payHeadHistory.getPayrollPayhead() !=null) {
					payHeadHistoryDTO.setPayrollPayhead(payHeadHistory.getPayrollPayhead().getId());
					payHeadHistoryDTO.setPayrollPayheadType(payHeadHistory.getPayrollPayhead().getType());
					payHeadHistoryDTO.setPayrollPayheadName(payHeadHistory.getPayrollPayhead().getName());
					payHeadHistoryDTO.setPayrollPayheadShortCode(payHeadHistory.getPayrollPayhead().getShortCode());
					payHeadHistoryDTO.setPaySlip(payHeadHistory.getPayrollPayhead().getShowInPayslip());
					payHeadHistoryDTO.setTaxStatus(payHeadHistory.getPayrollPayhead().getTax());
				}
				payHeadHistoryDTO.setMonthly(payHeadHistory.getMonthly());
				payHeadHistoryDTO.setAnnualy(payHeadHistory.getAnnualy());
				payHeadHistoryDTO.setUuid(payHeadHistory.getUuid());
				payHeadHistoryDTO.setStatus(payHeadHistory.getStatus());
				if(payHeadHistory.getCreatedBy() != null) {
					payHeadHistoryDTO.setCreatedBy(payHeadHistory.getCreatedBy().getId());
				}
				if(payHeadHistory.getUpdatedBy() != null) {
					payHeadHistoryDTO.setUpdatedBy(payHeadHistory.getUpdatedBy().getId());
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(payHeadHistory.getCreatedDate() != null) {
					payHeadHistoryDTO.setCreatedDate(df.format(payHeadHistory.getCreatedDate()));
				}
				if(payHeadHistory.getUpdatedDate() != null) {
					payHeadHistoryDTO.setUpdatedDate(df.format(payHeadHistory.getUpdatedDate()));
				}
				payHeadHistoryDTOList.add(payHeadHistoryDTO);
			}
		}
		return payHeadHistoryDTOList;
	}
	
	
	public List<AssociateLoanAndAdvancesHistoryDTO> getAssociateLoanAndAdvancesHistoryDTOListFromAssociateLoanAndAdvancesHistoryList(IndEmployeePayroll indEmployeePayroll,Set<AssociateLoanAndAdvanceHistory> loanAdvHistoryList,OrgLocations orgLocations) throws Exception {
		List<AssociateLoanAndAdvancesHistoryDTO> loanAdvHistoryDTOList = new ArrayList<>();
		if(loanAdvHistoryList != null && !loanAdvHistoryList.isEmpty()) {
			for(AssociateLoanAndAdvanceHistory loanAdvHistory : loanAdvHistoryList) {
				AssociateLoanAndAdvancesHistoryDTO  loanAdvHistoryDTO = new AssociateLoanAndAdvancesHistoryDTO();
				
				loanAdvHistoryDTO.setAssociateLoanAndAdvancesId(loanAdvHistory.getAssociateLoanAndAdvances().getId());
				loanAdvHistoryDTO.setIndEmployeePayrollId(loanAdvHistory.getIndEmployeePayrollId().getId());
				loanAdvHistoryDTO.setOrgLocationsId(orgLocations.getId());
				loanAdvHistoryDTO.setPayrollType(loanAdvHistory.getPayrollType());
				loanAdvHistoryDTO.setDeductAmount(loanAdvHistory.getDeductAmount());
				loanAdvHistoryDTO.setPaidAmount(loanAdvHistory.getPaidAmount());
				loanAdvHistoryDTO.setDueAmount(loanAdvHistory.getDueAmount());
				loanAdvHistoryDTO.setSchedule(loanAdvHistory.getSchedule());
				loanAdvHistoryDTO.setScheduleStartDate(loanAdvHistory.getScheduleStartDate());
				loanAdvHistoryDTO.setScheduleEndDate(loanAdvHistory.getScheduleEndDate());
				loanAdvHistoryDTO.setCreatedDate(loanAdvHistory.getCreatedDate());
				loanAdvHistoryDTO.setCreatedBy(loanAdvHistory.getCreatedBy());
				loanAdvHistoryDTO.setUpdatedBy(loanAdvHistory.getUpdatedBy());
				loanAdvHistoryDTO.setUpdatedDate(loanAdvHistory.getUpdatedDate());
				loanAdvHistoryDTO.setName(loanAdvHistory.getName());
				loanAdvHistoryDTO.setType(loanAdvHistory.getType());
				loanAdvHistoryDTO.setTotalAmount(loanAdvHistory.getTotalAmount());
				loanAdvHistoryDTO.setRequestedTo(loanAdvHistory.getRequestedTo());
				loanAdvHistoryDTO.setRequestedOn(loanAdvHistory.getRequestedOn());
				loanAdvHistoryDTO.setNoOfShedules(loanAdvHistory.getNoOfShedules());
				loanAdvHistoryDTO.setLastPaidOn(loanAdvHistory.getLastPaindOn());
				loanAdvHistoryDTOList.add(loanAdvHistoryDTO);
			}
		}
		return loanAdvHistoryDTOList;
	}
	
	
	
	public List<AssociatePayrollPayheadHistory> getAssociatePayrollPayheadHistoryListFromAssociatePayrollPayheadHistoryDTOList(IndEmployeePayroll indEmployeePayroll,List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList,OrgLocations orgLocations) throws Exception {
		List<AssociatePayrollPayheadHistory> payHeadHistoryList = new ArrayList<>();
		if(payHeadHistoryDTOList != null && !payHeadHistoryDTOList.isEmpty()) {
			for(AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO : payHeadHistoryDTOList) {
				AssociatePayrollPayheadHistory  payHeadHistory = new AssociatePayrollPayheadHistory();
			    payHeadHistory.setIndEmployeePayrollId(indEmployeePayroll);
				if(payHeadHistoryDTO.getPayrollPayhead() !=null) {
					PayrollPayhead payrollPayhead = new PayrollPayhead();
					payrollPayhead.setId(payHeadHistoryDTO.getPayrollPayhead());
					payHeadHistory.setPayrollPayhead(payrollPayhead);
				}
				payHeadHistory.setMonthly(payHeadHistoryDTO.getMonthly());
				payHeadHistory.setAnnualy(payHeadHistoryDTO.getAnnualy());
				payHeadHistory.setUuid(payHeadHistoryDTO.getUuid());
				payHeadHistory.setStatus(payHeadHistoryDTO.getStatus());
				if(payHeadHistoryDTO.getCreatedBy() != null) {
					User user = new User();
					user.setId(payHeadHistoryDTO.getCreatedBy());
					payHeadHistory.setCreatedBy(user);
				}
				if(payHeadHistoryDTO.getUpdatedBy() != null) {
					User user = new User();
					user.setId(payHeadHistoryDTO.getUpdatedBy());
					payHeadHistory.setUpdatedBy(user);
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(StringUtils.isNotEmpty(payHeadHistoryDTO.getCreatedDate())) {
					payHeadHistory.setCreatedDate(df.parse(payHeadHistoryDTO.getCreatedDate()));
				}
				if(StringUtils.isNotEmpty(payHeadHistoryDTO.getUpdatedDate())) {
					payHeadHistory.setUpdatedDate(df.parse(payHeadHistoryDTO.getUpdatedDate()));
				}
				payHeadHistoryList.add(payHeadHistory);
			}
		}
		return payHeadHistoryList;
	}
	
	public List<AssociateLoanAndAdvanceHistory> getAssociateLoanAndAdvanceHistoryListFromAssociateLoanAndAdvancesHistoryDTOList(IndEmployeePayroll indEmployeePayroll,List<AssociateLoanAndAdvancesHistoryDTO> loanAdvHistoryDTOList,OrgLocations orgLocations) throws Exception {
		List<AssociateLoanAndAdvanceHistory> loanAdvHistoryList = new ArrayList<>();
		if(loanAdvHistoryDTOList != null && !loanAdvHistoryDTOList.isEmpty()) {
			for(AssociateLoanAndAdvancesHistoryDTO loanAdvHistoryDTO : loanAdvHistoryDTOList) {
				AssociateLoanAndAdvanceHistory  loanAdvHistory = new AssociateLoanAndAdvanceHistory();
				AssociateLoanAndAdvances ala = new AssociateLoanAndAdvances();
				ala.setId(loanAdvHistoryDTO.getAssociateLoanAndAdvancesId());
				loanAdvHistory.setAssociateLoanAndAdvances(ala);
				loanAdvHistory.setIndEmployeePayrollId(indEmployeePayroll);
				loanAdvHistory.setOrgLocationsId(orgLocations);
				loanAdvHistory.setPayrollType(loanAdvHistoryDTO.getPayrollType());
				loanAdvHistory.setDeductAmount(loanAdvHistoryDTO.getDeductAmount());
				loanAdvHistory.setPaidAmount(loanAdvHistoryDTO.getPaidAmount());
				loanAdvHistory.setDueAmount(loanAdvHistoryDTO.getDueAmount());
				loanAdvHistory.setSchedule(loanAdvHistoryDTO.getSchedule());
				loanAdvHistory.setScheduleStartDate(loanAdvHistoryDTO.getScheduleStartDate());
				loanAdvHistory.setScheduleEndDate(loanAdvHistoryDTO.getScheduleEndDate());
				loanAdvHistory.setCreatedDate(loanAdvHistoryDTO.getCreatedDate());
				loanAdvHistory.setCreatedBy(loanAdvHistoryDTO.getCreatedBy());
				loanAdvHistory.setUpdatedBy(loanAdvHistoryDTO.getUpdatedBy());
				loanAdvHistory.setUpdatedDate(loanAdvHistoryDTO.getUpdatedDate());
				loanAdvHistory.setName(loanAdvHistoryDTO.getName());
				loanAdvHistory.setType(loanAdvHistoryDTO.getType());
				loanAdvHistory.setTotalAmount(loanAdvHistoryDTO.getTotalAmount());
				loanAdvHistory.setRequestedTo(loanAdvHistoryDTO.getRequestedTo());
				loanAdvHistory.setRequestedOn(loanAdvHistoryDTO.getRequestedOn());
				loanAdvHistory.setNoOfShedules(loanAdvHistoryDTO.getNoOfShedules());
				loanAdvHistory.setLastPaindOn(loanAdvHistoryDTO.getLastPaidOn());
				loanAdvHistoryList.add(loanAdvHistory);
			}
		}
		return loanAdvHistoryList;
	}
	
	@SuppressWarnings("null")
	public List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadAdjustIndSessionList(List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList, INDPayrollDTO indPayrollDTO, IndEmployeePayrollDTO indEmployeePayrollDTO, User loginUser, OrgLocations orgLocation) throws Exception{
		List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOLists = new ArrayList<AssociatePayrollPayheadHistoryDTO>();
		if(indEmployeePayrollDTO != null && indEmployeePayrollDTO.getAssociateSalaryPayheadIndEarningList() != null) {
			for(AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO : indEmployeePayrollDTO.getAssociateSalaryPayheadIndEarningList()) {
				if(payHeadHistoryDTO.getMonthlyOld() != payHeadHistoryDTO.getMonthly()) {
					associatePayrollPayheadDTOLists.add(associatePayrollPayheadAdjustIndSession(indPayrollDTO, payHeadHistoryDTO, loginUser, orgLocation));
				}
			}
		}
		if(indEmployeePayrollDTO != null && indEmployeePayrollDTO.getAssociateSalaryPayheadIndDeductionList() != null) {
			for(AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO : indEmployeePayrollDTO.getAssociateSalaryPayheadIndDeductionList()) {
				if(payHeadHistoryDTO.getMonthlyOld() != payHeadHistoryDTO.getMonthly()) {
					associatePayrollPayheadDTOLists.add(associatePayrollPayheadAdjustIndSession(indPayrollDTO, payHeadHistoryDTO, loginUser, orgLocation));
				}
			}
		}
		//associatePayrollPayheadDTOLists.addAll(associatePayrollPayheadDTOList);
		return associatePayrollPayheadDTOLists;
	}
	
	public AssociatePayrollPayheadHistoryDTO associatePayrollPayheadAdjustIndSession(INDPayrollDTO indPayrollDTO, AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO, User loginUser, OrgLocations orgLocation) throws Exception {
		AssociatePayrollPayheadHistoryDTO  associatePayrollPayheadHistoryDTO = new AssociatePayrollPayheadHistoryDTO();
		associatePayrollPayheadHistoryDTO.setIndEmployeePayrollId(indPayrollDTO.getId());
		if(payHeadHistoryDTO.getPayrollPayhead() !=null) {
			associatePayrollPayheadHistoryDTO.setPayrollPayhead(payHeadHistoryDTO.getPayrollPayhead());
		}
		associatePayrollPayheadHistoryDTO.setPayrollPayheadName(payHeadHistoryDTO.getPayrollPayheadName());
		associatePayrollPayheadHistoryDTO.setPayrollPayheadShortCode(payHeadHistoryDTO.getPayrollPayheadShortCode());
		associatePayrollPayheadHistoryDTO.setPayrollPayheadType(payHeadHistoryDTO.getPayrollPayheadType());
		
		associatePayrollPayheadHistoryDTO.setTaxStatus(payHeadHistoryDTO.getTaxStatus());
		
		associatePayrollPayheadHistoryDTO.setMonthly(payHeadHistoryDTO.getMonthly());
		associatePayrollPayheadHistoryDTO.setAnnualy(payHeadHistoryDTO.getMonthly().multiply(new BigDecimal("12")));
		associatePayrollPayheadHistoryDTO.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		associatePayrollPayheadHistoryDTO.setStatus(Constants.RECORD_ACTIVE);
		associatePayrollPayheadHistoryDTO.setCreatedBy(loginUser.getId());
		associatePayrollPayheadHistoryDTO.setUpdatedBy(loginUser.getId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		associatePayrollPayheadHistoryDTO.setCreatedDate(df.format(new Date()));
		associatePayrollPayheadHistoryDTO.setUpdatedDate(df.format(new Date()));
		return associatePayrollPayheadHistoryDTO;
	}
	
	@SuppressWarnings("null")
	public Set<AssociatePayrollPayheadHistory> associatePayrollPayheadAdjustIndList(IndEmployeePayroll indEmployeePayroll, IndEmployeePayrollDTO indEmployeePayrollDTO, User loginUser) throws Exception {
		Set<AssociatePayrollPayheadHistory> apphList = new HashSet<AssociatePayrollPayheadHistory>();
		if(indEmployeePayrollDTO != null && indEmployeePayrollDTO.getAssociateSalaryPayheadIndEarningList() != null) {
			for(AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO : indEmployeePayrollDTO.getAssociateSalaryPayheadIndEarningList()) {
				if(payHeadHistoryDTO.getMonthlyOld() != payHeadHistoryDTO.getMonthly()) {
					apphList.add(associatePayrollPayheadAdjustInd(indEmployeePayroll, payHeadHistoryDTO, loginUser));
				}
			}
		}
		if(indEmployeePayrollDTO != null && indEmployeePayrollDTO.getAssociateSalaryPayheadIndDeductionList() != null) {
			for(AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO : indEmployeePayrollDTO.getAssociateSalaryPayheadIndDeductionList()) {
				if(payHeadHistoryDTO.getMonthlyOld() != payHeadHistoryDTO.getMonthly()) {
					apphList.add(associatePayrollPayheadAdjustInd(indEmployeePayroll, payHeadHistoryDTO, loginUser));
				}
			}
		}
		return apphList;
	}
	
	public AssociatePayrollPayheadHistory associatePayrollPayheadAdjustInd(IndEmployeePayroll indEmployeePayroll, AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO, User loginUser) throws Exception {
		AssociatePayrollPayheadHistory  associatePayrollPayheadHistory = new AssociatePayrollPayheadHistory();
		associatePayrollPayheadHistory.setIndEmployeePayrollId(indEmployeePayroll);
		if(payHeadHistoryDTO.getPayrollPayhead() !=null) {
			PayrollPayhead payrollPayhead = new PayrollPayhead();
			payrollPayhead.setId(payHeadHistoryDTO.getPayrollPayhead());
			payrollPayhead.setType(payHeadHistoryDTO.getPayrollPayheadType());
			payrollPayhead.setShortCode(payHeadHistoryDTO.getPayrollPayheadShortCode());
			associatePayrollPayheadHistory.setPayrollPayhead(payrollPayhead);
		}
		associatePayrollPayheadHistory.setMonthly(payHeadHistoryDTO.getMonthly());
		associatePayrollPayheadHistory.setAnnualy(payHeadHistoryDTO.getMonthly().multiply(new BigDecimal("12")));
		associatePayrollPayheadHistory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		associatePayrollPayheadHistory.setStatus(Constants.RECORD_ACTIVE);
		associatePayrollPayheadHistory.setCreatedBy(loginUser);
		associatePayrollPayheadHistory.setUpdatedBy(loginUser);
		associatePayrollPayheadHistory.setCreatedDate(new Date());
		associatePayrollPayheadHistory.setUpdatedDate(new Date());
		return associatePayrollPayheadHistory;
	}
	
	
	public AssociateLoanAndAdvancesDTO getAssLoanAndAdvanceDedAmountInd(OrgLocations orgLocation, IndPaysheetFrequency paySheetFreq, User loginUser, Candidate candidate, IndEmployeePayroll employeePayroll)
	{
		AssociateLoanAndAdvancesDTO assLoanAdvDto = new AssociateLoanAndAdvancesDTO();
		try {
			List<String> loanStatus = new ArrayList<>();
			loanStatus.add("Outstanding");
			List<AssociateLoanAndAdvances> loanAndAdvList = associateLoanAndAdvancesService.getAssociateLoanAndAdvancesListByCandidateIdOrgLocationIdAndLoanStatus(candidate.getId(), orgLocation.getId(), loanStatus);
			BigDecimal totalLoanAmount = BigDecimal.ZERO, totalAdvanceAmount = BigDecimal.ZERO; 
			Set<AssociateLoanAndAdvanceHistory> his = new HashSet<>();
			for(AssociateLoanAndAdvances assLoanAndAdv : loanAndAdvList) {
				boolean thisPayCheck = false;
				if(assLoanAndAdv.getDeductStartDate() == null) {
					thisPayCheck = true;
				}
				if(paySheetFreq.getFrequencyStartDate().after(assLoanAndAdv.getEffectiveDate()) || paySheetFreq.getFrequencyStartDate().equals(assLoanAndAdv.getEffectiveDate())) {
					thisPayCheck = true;
				}
				AssociateLoanAndAdvances assLoan = assLoanAndAdv;
				int scheduleCount = 0;
				AssociateLoanAndAdvanceHistory assLoanAndAdvHis = associateLoanAndAdvancesHistoryService.getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(assLoan.getId());
				if(assLoanAndAdvHis == null || assLoanAndAdvHis.getSchedule() < assLoan.getNoOfSchedules()) {
					if(thisPayCheck) {
						scheduleCount = 0;
						if(assLoanAndAdvHis != null && assLoanAndAdvHis.getSchedule() != null) {
							scheduleCount = assLoanAndAdvHis.getSchedule() + 1;
						}else {
							scheduleCount += 1;
						}
					}
				}else {
					thisPayCheck = false;
				}
				
				if(thisPayCheck) {
					Date[] dedFreqDates = getLoanAndAdvancesScheduleDatesInd(paySheetFreq.getFrequencyStartDate(), assLoanAndAdv.getScheduler());
					if("Loan".equalsIgnoreCase(assLoanAndAdv.getLoanAdvanceType())){
						totalLoanAmount = totalLoanAmount.add(new BigDecimal(assLoanAndAdv.getDeductAmount()));
					}else if("Advance".equalsIgnoreCase(assLoanAndAdv.getLoanAdvanceType())){
						totalAdvanceAmount = totalAdvanceAmount.add(new BigDecimal(assLoanAndAdv.getDeductAmount()));
					}
					assLoan.setDeductStartDate(dedFreqDates[0]);
					if(dedFreqDates[1] != null)
						assLoan.setDeductEndDate(dedFreqDates[1]);
					else
						assLoan.setDeductEndDate(paySheetFreq.getFrequencyEndDate());
					assLoan.setDeductionStatus(Constants.DEDUCTED);
					assLoan.setUpdatedDate(new Date());
					
					assLoanAdvDto.setAssociateLoanAndAdvances(assLoan);
					//associateLoanAndAdvancesService.saveAssociateLoanAndAdvances(assLoan);
					
					AssociateLoanAndAdvanceHistory assLoanHistory = new AssociateLoanAndAdvanceHistory();
					assLoanHistory.setAssociateLoanAndAdvances(assLoan);
					assLoanHistory.setOrgLocationsId(orgLocation);
					assLoanHistory.setPayrollType(Constants.IND_PAYROLL);
					assLoanHistory.setDeductAmount(assLoanAndAdv.getDeductAmount());
					assLoanHistory.setPaidAmount(associateLoanAndAdvancesHistoryService.getEmpLoansAndAdvancesPaidAmountByOrgLocationIdCandidateIdLoansAndAdId(candidate.getId(), orgLocation.getId(), assLoanAndAdv.getId()) + assLoanHistory.getDeductAmount());
					assLoanHistory.setDueAmount((assLoanAndAdv.getAmount() + (assLoanAndAdv.getAmount() * (assLoanAndAdv.getPayrollLoanAndAdvanceId().getInterestRate()/100) )) - assLoanHistory.getPaidAmount());
					assLoanHistory.setSchedule(scheduleCount);
					assLoanHistory.setScheduleStartDate(assLoan.getDeductStartDate());
					assLoanHistory.setScheduleEndDate(assLoan.getDeductEndDate());
					assLoanHistory.setCreatedDate(new Date());
					assLoanHistory.setIndEmployeePayrollId(employeePayroll);
					assLoanHistory.setCreatedBy(loginUser.getUserCode());
					assLoanHistory.setUpdatedBy(loginUser.getUserCode());
					assLoanHistory.setUpdatedDate(new Date());
					his.add(assLoanHistory);
 				}
			}
			assLoanAdvDto.setTotalAdvanceAmount(totalAdvanceAmount);
			assLoanAdvDto.setTotalLoanAmount(totalLoanAmount);
			assLoanAdvDto.setLoanHis(his);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assLoanAdvDto;
	}
	
	
	public Date[] getLoanAndAdvancesScheduleDatesInd(Date currentDate, String schedule)
	{
		Date[] dates = new Date[2];
		dates[0] = currentDate;
		try {
			Calendar cal = Calendar.getInstance();
			 cal.setTime(currentDate);
			switch (schedule) {
				case PayrollConstant.LOAN_EVERY_PAY:
					break;
				case PayrollConstant.LOAN_EVERY_QUARTER:
					    cal.add(Calendar.MONTH, +3);  
				        dates[1]  = cal.getTime();
					break;
				case PayrollConstant.LOAN_HALF_YEARLY:
				    cal.add(Calendar.MONTH, +6);  
			        dates[1]  = cal.getTime();
				break;
				case PayrollConstant.LOAN_ANNUALLY:
					 cal.add(Calendar.MONTH, +12);  
				        dates[1]  = cal.getTime();
					break;
				default:
					break;
			}
		}catch (Exception e) {
		 e.printStackTrace();
		}
		return dates;
	}
	
	
	public Double calculateSumOfAllLoanAdvOfEmp(AssociateLoanAndAdvancesDTO assLoanAndAdvDTO) {
		Double loanAdvTotal = 0.0;
		for(AssociateLoanAndAdvanceHistory la : assLoanAndAdvDTO.getLoanHis()) {
			loanAdvTotal = loanAdvTotal + la.getDeductAmount();
		}
		return loanAdvTotal;
	}
	
	
	@SuppressWarnings("static-access")
	public Double calculatePTforAssociate(AssociateSalaryInd assoSal, Date freqStartDate, Date freqEndDate, StateTaxDTO taxDTO) throws ParseException {
		Double ptAmount = 0.0;
		if(assoSal != null && assoSal.getAssociateSalaryPayheadIndList() != null && assoSal.getAssociateSalaryPayheadIndList().size()>0) {
			AssociateSalaryPayheadInd asph = assoSal.getAssociateSalaryPayheadIndList().stream().filter(h -> h.getPayrollPayhead().getShortCode().equalsIgnoreCase("BA")).findAny().get();
			if(asph != null) {
				BigDecimal ptSum = new BigDecimal(
						assoSal.getAssociateSalaryPayheadIndList()
						.stream()
						.filter(p ->p.getMonthly() != null && p.getPayrollPayhead() !=null && p.getPayrollPayhead().getPtApplicable() != null && "Y".equalsIgnoreCase(p.getPayrollPayhead().getPtApplicable()) && Constants.IND_PAYROLL_PAYHEAD_EARNINGS.equalsIgnoreCase(p.getPayrollPayhead().getType()))
						.mapToDouble(s -> s.getMonthly()
						.doubleValue()).sum())
						.setScale(2, BigDecimal.ROUND_HALF_EVEN);
				
				try {
					if(taxDTO != null && taxDTO.getProfessionalTaxList().size()>0) {
						ProfessionalTaxDTO ptDTO = taxDTO.getProfessionalTaxList().stream().filter(t -> new BigDecimal(t.getAmountFrom()).compareTo(ptSum) <= 0 && new BigDecimal(t.getAmountTo()).compareTo(ptSum) >= 0 ).findAny().get();
						if(ptDTO != null)
							ptAmount = Double.valueOf(ptDTO.getAmount());
					}
				}catch (Exception e) {
					//e.printStackTrace();
					return ptAmount;
				}
			}
		}
		return ptAmount;
	}
	
	
	
	private BigDecimal getProjectInvoiceFrequency(String invoiceFrequency)
	{
		BigDecimal payPeriod = BigDecimal.ZERO;
		if ("Weekly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = BigDecimal.valueOf(52);
		}
		else if ("Bi-Weekly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod =  BigDecimal.valueOf(26);
		}
		return payPeriod;
	}
	
	
	
	/**
	 * Calculating LOP amount for Leaves
	 * @param employee
	 * @param orgLocation 
	 * @param freqEndDate 
	 * @param freqStartDate 
	 * @param AssociateSalaryInd 
	 * @param amount 
	 * @param IndEmployeePayroll 
	 * @return
	 */
	public Set<PayrollLopHis> getLOPAmountInd(Employees employee, AssociateSalaryInd assoSal, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation, BigDecimal amount, IndEmployeePayroll employeePayroll)
	{
		String dayHours = "";
		BigDecimal lopAmt = BigDecimal.ZERO;
		Set<PayrollLopHis> lopHiList = null;
		try {
			lopHiList = leaveService.getLeaveByFrequencyInd(assoSal.getCandidate().getConsultantUser().getId(), freqStartDate,freqEndDate,orgLocation,employeePayroll);
			if(lopHiList != null && lopHiList.size() > 0 ) {
				for(PayrollLopHis payrollLopHis : lopHiList) {
	    			BigDecimal lopLeaves = new BigDecimal(payrollLopHis.getLopLeaves());
	    			BigDecimal compareLeave = new BigDecimal("0.5");
	    			int res = compareLeave.compareTo(lopLeaves);
	    			BigDecimal hourlyRate = BigDecimal.ZERO;
	    			if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
	    				  hourlyRate = assoSal.getHourlyRate();
	    			}else {
	    				  hourlyRate = amount.divide(new BigDecimal(176));
	    			}
	    			if( res == 0 || res == 1) {
	    				 //Calculating Half Day
	    					 if(employee != null && StringUtils.isNotBlank(employee.getDailyHours())) {
	    						 BigDecimal hours = new BigDecimal(dayHours.replace(":", "."));
	    						 BigDecimal dayHrs = hours.divide(new BigDecimal(2));
	    						 lopAmt = dayHrs.multiply(hourlyRate);
	    					 }else {
	    						 BigDecimal dayHrs = new BigDecimal(4);
	    						 lopAmt = dayHrs.multiply(hourlyRate);
	    					 }
	    			}else if( res == -1 ) {
	    				 //Calculating Full Day
	    				 if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
	    					 String averageHours = "";
	    					 if(employee != null && StringUtils.isNotBlank(employee.getDailyHours())) {
	    						 averageHours = employee.getDailyHours().replace(":", ".");
	    					 }else {
	    						 averageHours = "8";
	    					 }
	    					 BigDecimal one = new BigDecimal("1");
	    					 double avgHrs = new BigDecimal(averageHours).doubleValue();
	    					 double leaves = lopLeaves.doubleValue();
	    					 double dyHours = 0;
	    					 int res1 = one.compareTo(lopLeaves);
	    					    if(res1 == 0 || res1 == 1) {
	    					    	 dyHours = avgHrs/2;
	    					    }else if(res1 == -1) {
	    					    	  dyHours = (avgHrs * leaves);
	    					    }
	    						lopAmt = payrollUtil.getTotalAmount(dyHours, hourlyRate);
	    				 }
	    			 }
	    			 payrollLopHis.setLopAmount(lopAmt);
			    }	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lopHiList;
	}
	
	
	
	public void sendMailToIndPayrollApprover(OrgLocations orgLocation, User loginUser, PayrollApprover payrollApprover, IndPaysheetFrequency frequency) throws Exception {
		if(payrollApprover.getUser().getId() != loginUser.getId()){
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setFirstName(payrollApprover.getUser().getFirstName());
			emailMessage.setLastName(payrollApprover.getUser().getLastName());
			emailMessage.setCreatedBy(loginUser.getUserCode());
			emailMessage.setFirstName(loginUser.getFirstName());
			emailMessage.setFirstName(loginUser.getLastName());
			emailMessage.setToEmail(payrollApprover.getUser().getEmail());
			emailMessage.setFromEmail(loginUser.getEmail());

			emailMessage.setFromName(loginUser.getFullName());
			User user = userService.findByEmail(payrollApprover.getUser().getEmail());
			//emailMessage.setToEmail(payrollApprover.getUser().getEmail());

			//redirection uRl
			String redirectURL ="/employeePayrollList-params-paySheetFrequencyUUID=" + frequency.getPaySheetFrequencyUUID();
			Long redirectOrgLocationId = orgLocation.getId();
			
			// setting the class field values...............
			Map<String, String> globalVarsMap = new HashMap<String, String>();
			globalVarsMap.put("loginUser", loginUser.getFullName());
			if (orgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocation.getOrgLocationName())) {
				globalVarsMap.put("LoginOrgName", orgLocation.getOrgLocationName());
			} else {
				globalVarsMap.put("LoginOrgName", "--");
			}
			globalVarsMap.put("ApproverName", payrollApprover.getUser().getFullName());

			globalVarsMap.put("Frequency",
					commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
							frequency.getFrequencyStartDate()) + " to "
							+ commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
									frequency.getFrequencyEndDate()));

			globalVarsMap.put("GetTheBatchName", frequency.getUserGroup().getGroupName());

			globalVarsMap.put("GetTheCountNoOfAssociates", "" + frequency.getIndEmployeepayroll().size());

			
			BigDecimal totAmount = BigDecimal.ZERO; 
			for (IndEmployeePayroll indEmployeepayroll : frequency.getIndEmployeepayroll()) {
				totAmount = totAmount.add(indEmployeepayroll.getNetPay());
			}
			globalVarsMap.put("GetTheTotalAmount", totAmount.toString());
			// Set the login link when user click this link the page goes to redirect the
			// sourcelead login page..

			StringBuilder loginLinkBuild = new StringBuilder();
			globalVarsMap.put("loginLink", loginLinkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Approve Payroll</div></a>")
					.toString());
			// Set the notification link when user click this link the page goes to
			// respective payroll summary.......

			StringBuilder notifyBuild = new StringBuilder();
			globalVarsMap.put("NotificationLink",
					notifyBuild.append(sourceleadUrl).append("/")
							.append("employeePayrollList?paySheetFrequencyUUID=" + frequency.getPaySheetFrequencyUUID())
							.toString());

			// getting the current email scenario by uniqueNotification

			EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.SEND_NOTIFICATION_TO_INDPAYROLL_APPROVER, Constants.ENTITY_TYPE_NOTIFICATION);

			String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
					notificationScenario);
			notificationScenario.setBody(notificationbody);

			// getting the current email scenario by uniqueEmail

			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,
					Constants.SEND_MAIL_TO_INDPAYROLL_APPROVER, new ArrayList<EmailAttachment>());
			
			StringBuilder notificationLink = new StringBuilder();
			String actionLink="";
		    actionLink = notificationLink.append(pmsUrl).append("/")
					    .append("employeePayrollList?paySheetFrequencyUUID=" + frequency.getPaySheetFrequencyUUID())
					    .toString();

		    emailScenerioUtil.sendNotification(actionLink, loginUser, user, orgLocation, notificationScenario,new HashMap<String,String>());

		}
	}
	
	@SuppressWarnings("unused")
	public PreCheckINDPayrollDTO payrollPrerequisiteCheck(OrgLocations orgLocation, String payPeriod) throws ParseException{
		
		String dates[] = payPeriod.split(",");
		Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		PreCheckINDPayrollDTO preCheckINDPayrollDTO = new PreCheckINDPayrollDTO();
		
		List<String> financialYearStr = getFinancialYearByOrgLocationAndDate(orgLocation, freqStartDate);
		
		StateTaxDTO stateTaxDTO = taxService.getStateTaxByStateNameAndFinancialYear(orgLocation.getState(), financialYearStr.get(0) + "-" + financialYearStr.get(1));
		preCheckINDPayrollDTO.setStateTaxDTO(stateTaxDTO);
		if(stateTaxDTO != null && stateTaxDTO.getId() > 0) {
			preCheckINDPayrollDTO.setIsPTConfigured(true);
		}else {
			preCheckINDPayrollDTO.setIsPTConfigured(false);
		}
		
		List<Tds> tdsList = taxService.getTDSListByFinancialYear(financialYearStr.get(0) + "-" + financialYearStr.get(1));
		preCheckINDPayrollDTO.setTdsList(tdsList);
		if(tdsList != null && tdsList.size() > 0) {
			preCheckINDPayrollDTO.setIsTDSConfigured(true);
		}else {
			preCheckINDPayrollDTO.setIsTDSConfigured(false);
		}
		
		return preCheckINDPayrollDTO;
	}
	
	public void generatePdfForEmployeeInd(Set<IndEmployeePayroll> approvedPayrollList, OrgLocations orgLocation) {
		for (IndEmployeePayroll payroll : approvedPayrollList) {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void sendApprovedMailToRequestedUserInd(OrgLocations orgLocation, IndPaysheetFrequency frequency, User loginUser) throws Exception{
		if (frequency.getCreatedBy() != loginUser.getId()) {
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setFirstName(loginUser.getFirstName());
			emailMessage.setFirstName(loginUser.getLastName());
			User user = userService.getUserById(frequency.getCreatedBy());
			emailMessage.setToEmail(user.getEmail());
			emailMessage.setFromEmail(loginUser.getEmail());
			emailMessage.setFromName(loginUser.getFullName());
			//redirection uRl
			String redirectURL ="/employeePayrollList-params-paySheetFrequencyUUID=" + frequency.getPaySheetFrequencyUUID();
			Long redirectOrgLocationId = orgLocation.getId();

			// setting the class field values...............
			Map<String, String> globalVarsMap = new HashMap<String, String>();
				globalVarsMap.put("RequestUsername", user.getFullName());
			if (orgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocation.getOrgLocationName())) {
				globalVarsMap.put("LoginOrgName", orgLocation.getOrgLocationName());
			} else {
				globalVarsMap.put("LoginOrgName", "--");
			}
			globalVarsMap.put("loginUser",loginUser.getFullName());

			globalVarsMap.put("Frequency", commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),frequency.getFrequencyStartDate()) + " to " + commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),frequency.getFrequencyEndDate()));

			globalVarsMap.put("GetTheBatchName",frequency.getUserGroup().getGroupName());

			globalVarsMap.put("GetTheCountNoOfAssociates",""+frequency.getIndEmployeepayroll().size());

			BigDecimal totAmount = new BigDecimal(0.0);
			for (IndEmployeePayroll indEmployeepayroll : frequency.getIndEmployeepayroll()) {
				totAmount = totAmount.add(indEmployeepayroll.getNetPay());
			}
			globalVarsMap.put("GetTheTotalAmount", totAmount.toString());
			// Set the login link when user click this link the page goes to redirect the sourcelead login page..


			StringBuilder loginLinkBuild = new StringBuilder();
			globalVarsMap.put("loginLink", loginLinkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
			"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Payroll Approved</div></a>")
			.toString());

			// Set the notification link when user click this link the page goes to respective payroll summary.......

			StringBuilder notifyBuild = new StringBuilder();
			globalVarsMap.put("NotificationLink",notifyBuild.append(sourceleadUrl).append("/").append("employeePayrollList?paySheetFrequencyUUID="+frequency.getPaySheetFrequencyUUID()).toString());

			// getting the current email scenario by uniqueNotification

			EmailScenario notificationScenario = null;
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SEND_APPROVED_NOTIFICATION_TO_REQUESTE_DUSERIND, Constants.ENTITY_TYPE_NOTIFICATION);

			String notificationbody = null;
			notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationbody);
			StringBuilder notificationLink = new StringBuilder();
			String actionLink="";
		    actionLink = notificationLink.append(pmsUrl).append("/").append("employeePayrollList?paySheetFrequencyUUID="+frequency.getPaySheetFrequencyUUID()).toString();

		    commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,Constants.SEND_APPROVED_MAIL_TO_REQUESTED_USERIND, new ArrayList<EmailAttachment>());
		
		    emailScenerioUtil.sendNotification(actionLink, loginUser,user, orgLocation, notificationScenario,new HashMap<String,String>());
		}
	}
	
	
	public AssociateSalaryHistoryInd setPreviousDataToAssociateSalaryHistoryInd(AssociateSalaryInd associateSalaryInd, AssociateSalaryHistoryInd associateSalaryHistoryInd) {
		try {
				associateSalaryHistoryInd.setPreviousAnnualSalary(associateSalaryInd.getAnnualSalary());
				associateSalaryHistoryInd.setPreviousChangeReason(associateSalaryInd.getChangeReason());
				associateSalaryHistoryInd.setPreviousCreatedBy(associateSalaryInd.getCreatedBy());
				associateSalaryHistoryInd.setPreviousCreatedDate(associateSalaryInd.getCreatedDate());
				associateSalaryHistoryInd.setPreviousEffectiveDate(associateSalaryInd.getEffectiveDate());
				associateSalaryHistoryInd.setPreviousGrossSalary(associateSalaryInd.getGrossSalary());
				associateSalaryHistoryInd.setPreviousGroupId(associateSalaryInd.getGroupId());
				associateSalaryHistoryInd.setPreviousHourlyRate(associateSalaryInd.getHourlyRate());
				associateSalaryHistoryInd.setPreviousIncrementPercentage(associateSalaryInd.getIncrementPercentage());
				associateSalaryHistoryInd.setPreviousPayFrequency(associateSalaryInd.getPayFrequency());
				associateSalaryHistoryInd.setPreviousPayType(associateSalaryInd.getPayType());
				associateSalaryHistoryInd.setPreviousSalaryType(associateSalaryInd.getSalaryType());
				associateSalaryHistoryInd.setPreviousStandardHours(associateSalaryInd.getStandardHours());
				associateSalaryHistoryInd.setPreviousStopSalary(associateSalaryInd.getStopSalary());
				associateSalaryHistoryInd.setPreviousUpdatedBy(associateSalaryInd.getUpdatedBy());
				associateSalaryHistoryInd.setPreviousUpdatedDate(associateSalaryInd.getUpdatedDate());
				
				HashSet<AssociateSalaryPayheadHistoryInd> associateSalaryPayheadHistoryIndList = new HashSet<AssociateSalaryPayheadHistoryInd>();
				for(AssociateSalaryPayheadInd associateSalaryPayheadInd : associateSalaryInd.getAssociateSalaryPayheadIndList()){
					AssociateSalaryPayheadHistoryInd associateSalaryPayheadHistoryInd = new AssociateSalaryPayheadHistoryInd();
					associateSalaryPayheadHistoryInd.setPreviousAnnualy(associateSalaryPayheadInd.getAnnualy());
					associateSalaryPayheadHistoryInd.setPreviousMonthly(associateSalaryPayheadInd.getMonthly());
					associateSalaryPayheadHistoryInd.setPreviousPayrollPayhead(associateSalaryPayheadInd.getPayrollPayhead());
					associateSalaryPayheadHistoryInd.setPreviousCreatedBy(associateSalaryPayheadInd.getCreatedBy());
					associateSalaryPayheadHistoryInd.setPreviousCreatedDate(associateSalaryPayheadInd.getCreatedDate());
					associateSalaryPayheadHistoryInd.setPreviousUpdatedBy(associateSalaryPayheadInd.getUpdatedBy());
					associateSalaryPayheadHistoryInd.setPreviousUpdatedDate(associateSalaryPayheadInd.getUpdatedDate());
					associateSalaryPayheadHistoryIndList.add(associateSalaryPayheadHistoryInd);
				}
				associateSalaryHistoryInd.setAssociateSalaryPayheadHistoryIndList(associateSalaryPayheadHistoryIndList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return associateSalaryHistoryInd;
	}
	
	
	@SuppressWarnings("unused")
	public AssociateSalaryHistoryInd setNewDataToAssociateSalaryHistoryInd(AssociateSalaryInd associateSalaryInd, AssociateSalaryHistoryInd associateSalaryHistoryInd) {
		try {
				associateSalaryHistoryInd.setAssociateSalaryInd(associateSalaryInd);
				associateSalaryHistoryInd.setAnnualSalary(associateSalaryInd.getAnnualSalary());
				associateSalaryHistoryInd.setChangeReason(associateSalaryInd.getChangeReason());
				associateSalaryHistoryInd.setCreatedBy(associateSalaryInd.getCreatedBy());
				associateSalaryHistoryInd.setCreatedDate(associateSalaryInd.getCreatedDate());
				associateSalaryHistoryInd.setEffectiveDate(associateSalaryInd.getEffectiveDate());
				associateSalaryHistoryInd.setGrossSalary(associateSalaryInd.getGrossSalary());
				associateSalaryHistoryInd.setGroupId(associateSalaryInd.getGroupId());
				associateSalaryHistoryInd.setHourlyRate(associateSalaryInd.getHourlyRate());
				associateSalaryHistoryInd.setIncrementPercentage(associateSalaryInd.getIncrementPercentage());
				associateSalaryHistoryInd.setPayFrequency(associateSalaryInd.getPayFrequency());
				associateSalaryHistoryInd.setPayType(associateSalaryInd.getPayType());
				associateSalaryHistoryInd.setSalaryType(associateSalaryInd.getSalaryType());
				associateSalaryHistoryInd.setStandardHours(associateSalaryInd.getStandardHours());
				associateSalaryHistoryInd.setStopSalary(associateSalaryInd.getStopSalary());
				associateSalaryHistoryInd.setUpdatedBy(associateSalaryInd.getUpdatedBy());
				associateSalaryHistoryInd.setUpdatedDate(associateSalaryInd.getUpdatedDate());
				associateSalaryHistoryInd.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				associateSalaryHistoryInd.setStatus(Constants.RECORD_ACTIVE);
				
				HashSet<AssociateSalaryPayheadHistoryInd> associateSalaryPayheadHistoryIndList = new HashSet<AssociateSalaryPayheadHistoryInd>();
				for(AssociateSalaryPayheadInd associateSalaryPayheadInd : associateSalaryInd.getAssociateSalaryPayheadIndList()){
					if(associateSalaryPayheadInd.getPayrollPayhead() != null && associateSalaryPayheadInd.getPayrollPayhead().getShortCode() != null) {
						AssociateSalaryPayheadHistoryInd associateSalaryPayheadHistoryInd = associateSalaryHistoryInd.getAssociateSalaryPayheadHistoryIndList().stream().filter(a -> a.getPreviousPayrollPayhead() != null && a.getPreviousPayrollPayhead().getShortCode() != null && a.getPreviousPayrollPayhead().getShortCode().equalsIgnoreCase(associateSalaryPayheadInd.getPayrollPayhead().getShortCode())).findFirst().orElse(null);
						if(associateSalaryPayheadInd.getStatus() > 0) {
							if(associateSalaryPayheadHistoryInd == null) {
								associateSalaryPayheadHistoryInd = new AssociateSalaryPayheadHistoryInd();
							}
							associateSalaryPayheadHistoryInd.setAnnualy(associateSalaryPayheadInd.getAnnualy());
							associateSalaryPayheadHistoryInd.setMonthly(associateSalaryPayheadInd.getMonthly());
							associateSalaryPayheadHistoryInd.setPayrollPayhead(associateSalaryPayheadInd.getPayrollPayhead());
							associateSalaryPayheadHistoryInd.setAssociateSalaryHistoryInd(associateSalaryHistoryInd);
							associateSalaryPayheadHistoryInd.setAssociateSalaryInd(associateSalaryInd);
							associateSalaryPayheadHistoryInd.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							associateSalaryPayheadHistoryInd.setStatus(Constants.RECORD_ACTIVE);
							associateSalaryPayheadHistoryInd.setCreatedBy(associateSalaryPayheadInd.getCreatedBy());
							associateSalaryPayheadHistoryInd.setCreatedDate(associateSalaryPayheadInd.getCreatedDate());
							associateSalaryPayheadHistoryInd.setUpdatedBy(associateSalaryPayheadInd.getUpdatedBy());
							associateSalaryPayheadHistoryInd.setUpdatedDate(associateSalaryPayheadInd.getUpdatedDate());
							associateSalaryPayheadHistoryIndList.add(associateSalaryPayheadHistoryInd);
						}
					}
				}
				associateSalaryHistoryInd.getAssociateSalaryPayheadHistoryIndList().clear();
				associateSalaryHistoryInd.setAssociateSalaryPayheadHistoryIndList(associateSalaryPayheadHistoryIndList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return associateSalaryHistoryInd;
	}
	
	@SuppressWarnings("unused")
	public List<String> getFinancialYearByOrgLocation(OrgLocations orgLocation) {
		List<String> finYearList = new ArrayList<>();
		try {
            Calendar orgCal = Calendar.getInstance();
            orgCal.setTime(orgLocation.getCreatedDate());
			 int orgRegisteredYear = orgCal.get(Calendar.YEAR);
             int orgRegisteredMonth = orgCal.get(Calendar.MONTH);
			 int fromYear = 0;
			 int toYear = 0;
			Calendar c = Calendar.getInstance();
			int cYear = c.get(Calendar.YEAR);
			int cMonth = c.get(Calendar.MONTH);
			if (StringUtils.isNotBlank(orgLocation.getStartMonth())) {
				Calendar cal = Calendar.getInstance();
				Date date = new SimpleDateFormat("MMMM").parse(orgLocation.getStartMonth());
				cal.setTime(date);
				int startFinMonth = cal.get(Calendar.MONTH);
				
				if(orgRegisteredMonth < startFinMonth) {
					fromYear = orgRegisteredYear - 1;
					toYear = orgRegisteredYear;
				}else {
					fromYear = orgRegisteredYear;
					toYear = orgRegisteredYear + 1;
				}
				
				while(fromYear < (startFinMonth <= cMonth ? cYear+1 : cYear )) {
					String finYear = fromYear+"-"+String.valueOf(fromYear+1);
					finYearList.add(finYear);
					fromYear++;
				}
			}
			 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finYearList;
	}
	
	
	@SuppressWarnings("unused")
	public List<Date> getFinancialYearStartAndEndDateByOrgLocations(OrgLocations orgLocations, String finYear) {
		List<Date> finYearDates = new ArrayList<>();
		try {
			Date sDate = null;
			 Date eDate = null;
			if(finYear.contains("-")) {
				String [] fYears = finYear.split("-");
				Date startDate = new SimpleDateFormat("MMMM").parse(orgLocations.getStartMonth());
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(startDate);
				 cal.set(Calendar.YEAR, Integer.valueOf(fYears[0].trim()));
				 sDate = cal.getTime();
				 
				 Date endDate = new SimpleDateFormat("MMMM").parse(orgLocations.getEndMonth());
				 cal.setTime(endDate);
				 cal.set(Calendar.YEAR, Integer.valueOf(fYears[1].trim()));
				 eDate = cal.getTime();
			}else {
				 Date startDate = new SimpleDateFormat("MMMM").parse(orgLocations.getStartMonth());
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(startDate);
				 cal.set(Calendar.YEAR, Integer.valueOf(finYear.trim()));
				 sDate = cal.getTime();
				 
				 cal.set(Calendar.MONTH, 11);
				 cal.set(Calendar.DAY_OF_MONTH, 31);
				 eDate = cal.getTime();
			}
			finYearDates.add(sDate);
			finYearDates.add(eDate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finYearDates;
	}
	
	@SuppressWarnings("unused")
	public List<String> getFinancialYearByOrgLocationAndDate(OrgLocations orgLocation, Date freqStartDate) {
		List<String> finYearList = new ArrayList<>();
		try {
			String fromYear = null;
			String toYear = null;
			Calendar c = Calendar.getInstance();
			c.setTime(freqStartDate);
			int fYear = c.get(Calendar.YEAR);
			int fMonth = c.get(Calendar.MONTH);
			if (StringUtils.isNotBlank(orgLocation.getStartMonth())) {
				Calendar cal = Calendar.getInstance();
				Date date = new SimpleDateFormat("MMMM").parse(orgLocation.getStartMonth());
				cal.setTime(date);
				int startFinMonth = cal.get(Calendar.MONTH);
				if (fMonth >= startFinMonth && fMonth < 12) {
					fromYear = Integer.toString(fYear);
					toYear = Integer.toString(fYear + 1);
				} else {
					fromYear = Integer.toString(fYear - 1);
					toYear = Integer.toString(fYear);
				}
			} else {
				fromYear = Integer.toString(fYear);
				toYear = Integer.toString(fYear);
			}
			finYearList.add(fromYear);
			finYearList.add(toYear);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finYearList;
	}
	
	@SuppressWarnings("unused")
	public AssociatePayrollPayheadHistory calculateTDSForEmployee(AssociateSalaryInd assoSal, Employees employee, OrgLocations orgLocation, Date freqStartDate, PreCheckINDPayrollDTO preCheckINDPayrollDTO, IndEmployeePayroll employeePayroll, User loginUser) 
	{AssociatePayrollPayheadHistory ph = new AssociatePayrollPayheadHistory();
	
	// TDS process Calculation
	try {
		Double tdsYearlyTotal = 0.0;
		Double tdsMonthlyTotal = 0.0;
		Double tdsExemptionTotal = 0.0;
		Double tdsEligibilityTotal = 0.0;
		
		//get yearly taxable amount in current frequency
		BigDecimal taxableYearlyAmount = new BigDecimal("0.00");
		for(AssociatePayrollPayheadHistory associatePayrollPayheadHistory : employeePayroll.getAssociatePayrollPayheadHistory()) {
			if(Constants.TAXABLE.equalsIgnoreCase(associatePayrollPayheadHistory.getPayrollPayhead().getTax())) {
				if(Constants.IND_PAYROLL_PAYHEAD_EARNINGS.equalsIgnoreCase(associatePayrollPayheadHistory.getPayrollPayhead().getType())) {
					taxableYearlyAmount = taxableYearlyAmount.add(associatePayrollPayheadHistory.getMonthly().multiply(new BigDecimal(12)));
				}
				if(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION.equalsIgnoreCase(associatePayrollPayheadHistory.getPayrollPayhead().getType())) {
					taxableYearlyAmount = taxableYearlyAmount.subtract(associatePayrollPayheadHistory.getMonthly().multiply(new BigDecimal(12)));
				}
			}
		}
		
		List<String> financialYearStr = this.getFinancialYearByOrgLocationAndDate(orgLocation, freqStartDate);
		String fromYear = financialYearStr.get(0);
		String toYear = financialYearStr.get(1);
		
		//age calculation
		int age = 0;
		if(assoSal.getCandidate().getConsultantUser().getDateOfBirth() != null) {
			LocalDate today = LocalDate.now();  //Today's date
			LocalDate birthdate = assoSal.getCandidate().getConsultantUser().getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();	//Birth date
			Period p = Period.between(birthdate, today);
			age = p.getYears();
		}
		
			/*
			 * //Eligible percent of tax based on age and salary Double taxPercent = 0d;
			 * if(preCheckINDPayrollDTO != null && preCheckINDPayrollDTO.getTdsList() !=
			 * null && preCheckINDPayrollDTO.getTdsList().size() > 0) { for(Tds tds :
			 * preCheckINDPayrollDTO.getTdsList()) { if(age <= 60 &&
			 * tds.getAge().equalsIgnoreCase("0 - 60 Years") &&
			 * taxableYearlyAmount.compareTo(new BigDecimal(tds.getFromamount())) > -1 &&
			 * taxableYearlyAmount.compareTo(new BigDecimal(tds.getToamount())) < 1) {
			 * taxPercent = tds.getPercentage(); }else if(age > 60 && age <= 80 &&
			 * tds.getAge().equalsIgnoreCase("60 - 80 Years") &&
			 * taxableYearlyAmount.compareTo(new BigDecimal(tds.getFromamount())) > -1 &&
			 * taxableYearlyAmount.compareTo(new BigDecimal(tds.getToamount())) < 1) {
			 * taxPercent = tds.getPercentage(); }else if(age > 80 &&
			 * tds.getAge().equalsIgnoreCase("Above 80") &&
			 * taxableYearlyAmount.compareTo(new BigDecimal(tds.getFromamount())) > -1 &&
			 * taxableYearlyAmount.compareTo(new BigDecimal(tds.getToamount())) < 1){
			 * taxPercent = tds.getPercentage(); } } }
			 */
		
		SourceleadTaxITDeclarationSubmission itDeclarationSubmission = taxService.getITDeclarationSubmissionByUserIdOrgLocationAndFinancialYear(employee.getUser().getId(), orgLocation.getId(), fromYear + "-" + toYear);
		
		if(itDeclarationSubmission != null && itDeclarationSubmission.getStatus() ==1) {
			JSONArray jsonArray = new JSONArray(itDeclarationSubmission.getSubmissionItems());
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					if (jsonObject.getJSONArray("items") != null) {
						for (int j = 0; j < jsonObject.getJSONArray("items").length(); j++) {
							JSONObject itemsJsonObject = jsonObject.getJSONArray("items").getJSONObject(j);
							itemsJsonObject.get("amount");
							itemsJsonObject.get("maxLimit");
							if(StringUtils.isNotBlank(itemsJsonObject.get("amount").toString()) 
									&& NumberUtils.isNumber(itemsJsonObject.get("amount").toString()) 
									&& Double.parseDouble(itemsJsonObject.get("amount").toString()) > 0) {
								if(Double.parseDouble(itemsJsonObject.get("maxLimit").toString()) > 0) {
									//when amount is greater then maxLimit
									if(NumberUtils.isNumber(itemsJsonObject.get("maxLimit").toString()) 
										&& Double.parseDouble(itemsJsonObject.get("amount").toString()) > Double.parseDouble(itemsJsonObject.get("maxLimit").toString())) {
										tdsExemptionTotal = tdsExemptionTotal + Double.parseDouble(itemsJsonObject.get("maxLimit").toString());
									//When amount is small or equal to maxLimit 	
								    }else if(NumberUtils.isNumber(itemsJsonObject.get("maxLimit").toString()) 
										 && Double.parseDouble(itemsJsonObject.get("amount").toString()) <= Double.parseDouble(itemsJsonObject.get("maxLimit").toString())) {
										 tdsExemptionTotal = tdsExemptionTotal + Double.parseDouble(itemsJsonObject.get("amount").toString());}
							    }else { 
								    tdsExemptionTotal = tdsExemptionTotal + Double.parseDouble(itemsJsonObject.get("amount").toString());   }
							}
						}
						//if tdsTaxExemption for section exceeds the Tax section Max limit 
						if(jsonObject.has("taxSectionMaxLimit")  && Double.parseDouble(jsonObject.get("taxSectionMaxLimit").toString()) > 0 
								&& Double.parseDouble(jsonObject.get("taxSectionMaxLimit").toString()) <= tdsExemptionTotal) {
						    if(StringUtils.isNotBlank(jsonObject.get("taxSectionMaxLimit").toString()) && NumberUtils.isNumber(jsonObject.get("taxSectionMaxLimit").toString())) {
							         tdsExemptionTotal = tdsExemptionTotal + Double.parseDouble(jsonObject.get("taxSectionMaxLimit").toString());
						     }
						}
					}
				}
			}
		}
		
		taxableYearlyAmount =(taxableYearlyAmount).subtract(new BigDecimal(tdsExemptionTotal));
		
		//Eligible percent of tax based on age and salary
		Double taxPercent = 0d;
		
		// TDS calculation start here 
		if(preCheckINDPayrollDTO != null && preCheckINDPayrollDTO.getTdsList() != null && preCheckINDPayrollDTO.getTdsList().size() > 0) {
			
			for(Tds tds : preCheckINDPayrollDTO.getTdsList()) {
				
				taxPercent = tds.getPercentage();
				BigDecimal fromAmount =  new BigDecimal(tds.getFromamount());
				BigDecimal toAmount =    new BigDecimal(tds.getToamount());
				BigDecimal diffAmount =   toAmount.subtract(fromAmount);
				
				if(age <= 60 && tds.getAge().equalsIgnoreCase("0 - 60 Years")) {
					
					
							if (diffAmount.compareTo(taxableYearlyAmount) < 0) {
								
								  taxableYearlyAmount = taxableYearlyAmount.subtract(diffAmount);
								  Double  slabAmount =     diffAmount.doubleValue(); 
								  tdsYearlyTotal =   tdsYearlyTotal + (slabAmount * taxPercent )/100;
							      
							}else {
								
	                               Double  slabAmount =   taxableYearlyAmount.doubleValue(); 
	                               tdsYearlyTotal =  tdsYearlyTotal + (slabAmount * taxPercent )/100;
							       break;
						    }
					
				}else if(age > 60 && age <= 80 && tds.getAge().equalsIgnoreCase("60 - 80 Years")) {
					
							if (diffAmount.compareTo(taxableYearlyAmount) < 0) {
								
								  taxableYearlyAmount = taxableYearlyAmount.subtract(diffAmount);
								  Double  slabAmount =     diffAmount.doubleValue(); 
								  tdsYearlyTotal =   tdsYearlyTotal + (slabAmount * taxPercent )/100;
							      
							}else {
								
	                             Double  slabAmount =   taxableYearlyAmount.doubleValue(); 
	                             tdsYearlyTotal =  tdsYearlyTotal + (slabAmount * taxPercent )/100;
							       break;
						    }
					
				}else if(age > 80 && tds.getAge().equalsIgnoreCase("Above 80")){
					
							if (diffAmount.compareTo(taxableYearlyAmount) < 0) {
								
								  taxableYearlyAmount = taxableYearlyAmount.subtract(diffAmount);
								  Double  slabAmount =     diffAmount.doubleValue(); 
								  tdsYearlyTotal =   tdsYearlyTotal + (slabAmount * taxPercent )/100;
							      
							}else {
								
	                             Double  slabAmount =   taxableYearlyAmount.doubleValue(); 
	                             tdsYearlyTotal =  tdsYearlyTotal + (slabAmount * taxPercent )/100;
							       break;
						    }
				}
			}
		}
		
		
		//TDS amount calculation
	//	tdsEligibilityTotal = (taxableYearlyAmount).subtract(new BigDecimal(tdsExemptionTotal)).doubleValue();
	//	tdsYearlyTotal = (tdsEligibilityTotal * taxPercent) / 100 ;
		tdsMonthlyTotal = (tdsYearlyTotal / 12);
		
		ph.setIndEmployeePayrollId(employeePayroll);
		ph.setPayrollPayhead(payrollPayheadService.getPayrollPayheadByShortCodeAndOrglocation("TDS", orgLocation.getId()));
		ph.setAnnualy(new BigDecimal(tdsYearlyTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		ph.setMonthly(new BigDecimal(tdsMonthlyTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		ph.setStatus(Constants.RECORD_ACTIVE);
		ph.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		ph.setCreatedBy(loginUser);
		ph.setUpdatedBy(loginUser);
		ph.setCreatedDate(new Date());
		ph.setUpdatedDate(new Date());
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	return ph;
	}



	/**
	 * @author Laxman
	 * @param emp
	 * @param indApprovePayrollDTO
	 * @param mapper
	 * @return
	 * @throws Exception
	 */
	public List<INDPayrollDTO> genarateTheListOfEmployeforIntitatePayroll(List<EmployeeSessionIndPayroll> emp,IndApprovePayrollDTO indApprovePayrollDTO, ObjectMapper mapper) throws Exception {
		List<INDPayrollDTO> ipDTOList = new ArrayList<>();
		   JSONObject jsonEmployeeObject = null;
		    for(EmployeeSessionIndPayroll employeeSession : emp) {
		    	if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(employeeSession.getExcludeUUID())) {
		    		INDPayrollDTO indDTO = new INDPayrollDTO();
		    		
		    		jsonEmployeeObject = new JSONObject(employeeSession.getJsonIndEmployeePayrollCache());
		 			indDTO.setEmployee(jsonEmployeeObject.getLong("employee"));
		 			indDTO.setEmpName(jsonEmployeeObject.getString("empName"));
		 			indDTO.setOvertimeHours(jsonEmployeeObject.getString("overtimeHours"));
		 			indDTO.setGrossPay(new BigDecimal(jsonEmployeeObject.getString("grossPay")));
		 			indDTO.setTotalCtc(new BigDecimal(jsonEmployeeObject.getString("totalCtc")));
		 			indDTO.setNetPay(new BigDecimal(jsonEmployeeObject.getString("netPay")));
		 			indDTO.setRegularHrsAmt(new BigDecimal(jsonEmployeeObject.getString("regularHrsAmt")));
		 			indDTO.setNoOfLeaves(new BigDecimal(jsonEmployeeObject.getString("noOfLeaves")));
		 			indDTO.setNoOfDays(new BigDecimal(jsonEmployeeObject.getString("noOfDays")));
		 			indDTO.setTotalHours(Double.valueOf(jsonEmployeeObject.getLong("totalHours")));
		 			indDTO.setTds(new BigDecimal(jsonEmployeeObject.getString("tds")));
		 			indDTO.setIndEmpPayrollUUID(jsonEmployeeObject.getString("indEmpPayrollUUID"));
		 			indDTO.setPaySheetFrequencyUUID(jsonEmployeeObject.getString("paySheetFrequencyUUID"));
		 			indDTO.setEmployeeDesignationType(jsonEmployeeObject.getString("employeeDesignationType"));
		 			indDTO.setApproverStatus(jsonEmployeeObject.getString("approverStatus"));
		 			indDTO.setEmpId(jsonEmployeeObject.getString("empId"));
		 			indDTO.setRegularHours(jsonEmployeeObject.getString("regularHours"));
		 			indDTO.setCandidateId(jsonEmployeeObject.getLong("candidateId"));
		 			indDTO.setPayCheckDate(jsonEmployeeObject.getString("payCheckDate"));
		 			indDTO.setPayType(jsonEmployeeObject.getString("payType"));
		 			indDTO.setProfessionalTax(new BigDecimal(jsonEmployeeObject.getString("professionalTax")));
		 			indDTO.setPayrollPaySlipInd(jsonEmployeeObject.getLong("payrollPaySlipInd"));
		 			indDTO.setCreatedDate(jsonEmployeeObject.getString("createdDate"));
		 			indDTO.setUpdatedDate(jsonEmployeeObject.getString("updatedDate"));
		 			indDTO.setLopAmount(new BigDecimal(jsonEmployeeObject.getString("lopAmount")));
		 			indDTO.setCreatedBy(jsonEmployeeObject.getLong("createdBy"));
		 			indDTO.setUpdatedBy(jsonEmployeeObject.getLong("updatedBy"));
		 			
		 			//we are converting jsonString to the list of AssociatePayrollPayheadHistoryDTO by using mapper .
		 			if(StringUtils.isNotBlank(employeeSession.getJsonPayHeadHistory())) {
		 				indDTO.setPayHeadHistoryDTOList(Arrays.asList(mapper.readValue(employeeSession.getJsonPayHeadHistory(), AssociatePayrollPayheadHistoryDTO[].class)));
		 			} else {
		 				indDTO.setPayHeadHistoryDTOList(Collections.EMPTY_LIST);
		 			}
		 			//we are converting jsonString to the list of AssociateLoanAndAdvancesHistoryDTO by using mapper .
		 			if(StringUtils.isNotBlank(employeeSession.getJsonLoanAdvHistory())) {
		 					indDTO.setLoanAdvHistoryDTOList(Arrays.asList(mapper.readValue(employeeSession.getJsonLoanAdvHistory(), AssociateLoanAndAdvancesHistoryDTO[].class)));
		 			}else {
		 				indDTO.setLoanAdvHistoryDTOList(Collections.EMPTY_LIST);
		 			}
		 			ipDTOList.add(indDTO);
		    	}
		    }
		    return ipDTOList;
	}



	public IndPaysheetFrequencyDTO indPaySheetFrequencyDTODetails(List<EmployeeSessionIndPayroll> emp, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception {
		  JSONObject jsonObject= new JSONObject(emp.get(0).getJsonString());
		  indPaysheetFrequencyDTO.setGroupName(jsonObject.getString("groupName"));
	   	  indPaysheetFrequencyDTO.setFrequencyStartDateStr(jsonObject.getString("frequencyStartDateStr"));
	   	  indPaysheetFrequencyDTO.setFrequencyEndDateStr(jsonObject.getString("frequencyEndDateStr"));
	   	  indPaysheetFrequencyDTO.setPayCheckDateStr(jsonObject.getString("payCheckDateStr"));
	   	  indPaysheetFrequencyDTO.setApproverStatus(jsonObject.getString("approverStatus"));
	   	  indPaysheetFrequencyDTO.setPayRunId(jsonObject.getString("payRunId"));
	   	  indPaysheetFrequencyDTO.setGroupId(jsonObject.getString("groupId"));
	   	  indPaysheetFrequencyDTO.setActionPerformedBy(jsonObject.getString("actionPerformedBy"));
	   	  indPaysheetFrequencyDTO.setOrgLocationId(jsonObject.getLong("orgLocationId"));
	   	  indPaysheetFrequencyDTO.setCreatedDate(jsonObject.getString("createDate"));
	   	  indPaysheetFrequencyDTO.setUpdatedDate(jsonObject.getString("updatedDate"));
	   	  indPaysheetFrequencyDTO.setCreatedBy(jsonObject.getLong("createdBy"));
	   	  indPaysheetFrequencyDTO.setUpdatedBy(jsonObject.getLong("updatedBy"));
	   	  indPaysheetFrequencyDTO.setPaySheetFrequencyUUID(emp.get(0).getPaysheetUUID());
	   	  indPaysheetFrequencyDTO.setStatus(Constants.RECORD_ACTIVE);
		return indPaysheetFrequencyDTO;
	}
}

