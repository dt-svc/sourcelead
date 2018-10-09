package com.bis.sourcelead.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.IndPayrollPayheadUtilDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.entity.PayrollApprover;

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
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	CommonUtil commonUtil;
	
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
				payheadDTO.setMethod(payRollPayhead.getMethod());
				payheadDTO.setFormula(payRollPayhead.getFormula());
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
			
			indPaysheetFrequency.setApproverStatus(paysheetFrequencyDTO.getApproverStatus());
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
		    
		    indPaysheetFrequencyDTO.setIndPayrollDTO(getINDPayrollDTOFromIndEmployeePayroll(paysheetFrequency.getIndEmployeepayroll(),paysheetFrequency.getOrgLocation()));
		    
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
		if(payrollList != null && !payrollList.isEmpty()) {
			for(IndEmployeePayroll employeePayroll :payrollList) {
				INDPayrollDTO payrollDTO = new INDPayrollDTO();
				payrollDTO.setIndEmpPayrollUUID(employeePayroll.getIndEmpPayrollUUID());
				if(employeePayroll != null && employeePayroll.getEmployee() != null) {
					payrollDTO.setEmpId(employeePayroll.getEmployee().getEmployeeId());
				}else {
					payrollDTO.setEmpId("");
				}
				
				if(employeePayroll != null && employeePayroll.getEmployee() != null) {
					payrollDTO.setEmployee(employeePayroll.getEmployee().getId());
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
				
				List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList = getAssociatePayrollPayheadHistoryDTOListFromAssociatePayrollPayheadHistoryList(employeePayroll, employeePayroll.getAssociatePayrollPayheadHistory(), orgLocations);
				payrollDTO.setPayHeadHistoryDTOList(payHeadHistoryDTOList);
				payrollDTO.setRegularHours(employeePayroll.getRegularHours());
				payrollDTO.setRegularHrsAmt(employeePayroll.getRegularHoursAmount());
				payrollDTO.setOvertimeHours(employeePayroll.getOverTimeHours());
				payrollDTO.setOtHoursAmt(employeePayroll.getOverTimeAmount());
				payrollDTO.setNoOfLeaves(employeePayroll.getNoOfLeaves());
				payrollDTO.setNoOfDays(employeePayroll.getNoOfDays());
				payrollDTO.setGrossPay(employeePayroll.getGrossPay());
				payrollDTO.setNetPay(employeePayroll.getNetPay());
				payrollDTO.setTds(employeePayroll.getTds());
				payrollDTO.setProfessionalTax(employeePayroll.getProfessionalTax());
				payrollDTO.setApproverStatus(employeePayroll.getIndPaysheetFrequency().getApproverStatus());
				payrollDTO.setPayType(employeePayroll.getPayType());
				payrollDTO.setPaySheetFrequencyUUID(employeePayroll.getIndPaysheetFrequency().getPaySheetFrequencyUUID());
				payrollDTOList.add(payrollDTO);
			}
		}
		 return payrollDTOList;
	}
	
 public List<IndEmployeePayroll> getIndEmployeePayrollFromINDPayrollDTO(IndPaysheetFrequency indPaysheetFrequency,List<INDPayrollDTO> payrollDTOList,OrgLocations orgLocations) throws Exception {
		
		List<IndEmployeePayroll> payrollList = new ArrayList<>();
		if(payrollDTOList != null && !payrollDTOList.isEmpty()) {
			for(INDPayrollDTO payrollDTO :payrollDTOList) {
				IndEmployeePayroll employeePayroll = new IndEmployeePayroll();
				employeePayroll.setIndEmpPayrollUUID(payrollDTO.getIndEmpPayrollUUID());
				employeePayroll.setIndPaysheetFrequency(indPaysheetFrequency);
				
				if(payrollDTO.getEmployee() != null) {
					Employees employee = new Employees();
					employee.setId(payrollDTO.getEmployee());
					employeePayroll.setEmployee(employee);
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
				employeePayroll.setCreatedBy(indPaysheetFrequency.getCreatedBy());
				employeePayroll.setCreatedDate(indPaysheetFrequency.getCreatedDate());
				employeePayroll.setUpdatedBy(indPaysheetFrequency.getUpdatedBy());
				employeePayroll.setUpdatedDate(indPaysheetFrequency.getUpdatedDate());
				employeePayroll.setSubmitForAproverStatus(indPaysheetFrequency.getApproverStatus());
				employeePayroll.setStatus(indPaysheetFrequency.getStatus());
				List<AssociatePayrollPayheadHistory> payHeadHistoryList = getAssociatePayrollPayheadHistoryListFromAssociatePayrollPayheadHistoryDTOList(employeePayroll,payrollDTO.getPayHeadHistoryDTOList(), orgLocations);
			    if(payHeadHistoryList != null && !payHeadHistoryList.isEmpty()) {
			    	employeePayroll.setAssociatePayrollPayheadHistory(new HashSet<AssociatePayrollPayheadHistory>(payHeadHistoryList));
			    }
				
				payrollList.add(employeePayroll);
			}
		}
		 return payrollList;
	}
	public IndPayrollPayheadUtilDTO getAssociatePayrollPayHeadHistoryFromAssociateSalaryPayHeads(Set<AssociateSalaryPayheadInd> payheadList,IndEmployeePayroll empPayroll,User loginUser,String payFrequency) throws Exception {
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
				AssociatePayrollPayheadHistory payHeadHistory = new AssociatePayrollPayheadHistory();
				payHeadHistory.setIndEmployeePayrollId(empPayroll);
				payHeadHistory.setPayrollPayhead(payHead.getPayrollPayhead());
				
				if(payHead.getPayrollPayhead() != null 
					&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getShortCode())
					&& payHead.getPayrollPayhead().getShortCode().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_TDS)) {
					
					if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_ANUALLY)) {
						tds = payHead.getAnnualy();
					}else if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY)) {
						tds = payHead.getMonthly();
					}else  {
						tds = payHead.getAnnualy().divide(getProjectInvoiceFrequency(payFrequency));
					}
					   
				}else if(payHead.getPayrollPayhead() != null 
						&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getShortCode())
						&& payHead.getPayrollPayhead().getShortCode().equalsIgnoreCase(Constants.IND_PAYROLL_PROFESSIONAL_TAX)) {
						if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_ANUALLY)) {
							pt = payHead.getAnnualy();
						}else if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY)) {
							pt = payHead.getMonthly();
						}else  {
							pt = payHead.getAnnualy().divide(getProjectInvoiceFrequency(payFrequency));
						}
						
				}
				
				if(payHead.getPayrollPayhead() != null 
						&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getType())
						&& payHead.getPayrollPayhead().getType().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_EARNINGS)) {
					
					if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_ANUALLY)) {
						grossPay = grossPay.add(payHead.getAnnualy());
					}else if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY)) {
						grossPay = grossPay.add(payHead.getMonthly());
					}else  {
						grossPay = grossPay.add(payHead.getAnnualy().divide(getProjectInvoiceFrequency(payFrequency)));
					}
				}
				
				if(payHead.getPayrollPayhead() != null 
						&& StringUtils.isNotEmpty(payHead.getPayrollPayhead().getType())
						&& payHead.getPayrollPayhead().getType().equalsIgnoreCase(Constants.IND_PAYROLL_PAYHEAD_DEDUCTION)) {
					if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_ANUALLY)) {
						allDeductions = allDeductions.add(payHead.getAnnualy());
					}else if(payFrequency.equalsIgnoreCase(Constants.IND_PAYROLL_PAYFREQUENCY_MONTHLY)) {
						allDeductions = allDeductions.add(payHead.getMonthly());
					}else  {
						allDeductions = allDeductions.add(payHead.getAnnualy().divide(getProjectInvoiceFrequency(payFrequency)));
					}
				}
				
				payHeadHistory.setMonthly(payHead.getMonthly());
				payHeadHistory.setAnnualy(payHead.getAnnualy());
				payHeadHistory.setStatus(payHead.getStatus());
				
				payHeadHistory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());

				payHeadHistory.setCreatedBy(loginUser);
				payHeadHistory.setUpdatedBy(loginUser);
				
				payHeadHistory.setCreatedDate(new Date());
				payHeadHistory.setUpdatedDate(new Date());
				payHeadHistoryList.add(payHeadHistory);
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
				payHeadHistoryDTO.setId(payHeadHistory.getId());
				payHeadHistoryDTO.setIndEmployeePayrollId(indEmployeePayroll.getId());
				payHeadHistoryDTO.setIndEmpPayrollUUID(indEmployeePayroll.getIndEmpPayrollUUID());
				if(payHeadHistory.getPayrollPayhead() !=null) {
					payHeadHistoryDTO.setPayrollPayhead(payHeadHistory.getPayrollPayhead().getId());
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
				
				if(payHeadHistory.getCreatedDate() != null) {
					payHeadHistoryDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payHeadHistory.getCreatedDate()));
				}
				if(payHeadHistory.getUpdatedDate() != null) {
					payHeadHistoryDTO.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payHeadHistory.getUpdatedDate()));
				}
				payHeadHistoryDTOList.add(payHeadHistoryDTO);
			}
		}
		return payHeadHistoryDTOList;
	}
	
	
	
	public List<AssociatePayrollPayheadHistory> getAssociatePayrollPayheadHistoryListFromAssociatePayrollPayheadHistoryDTOList(IndEmployeePayroll indEmployeePayroll,List<AssociatePayrollPayheadHistoryDTO> payHeadHistoryDTOList,OrgLocations orgLocations) throws Exception {
		List<AssociatePayrollPayheadHistory> payHeadHistoryList = new ArrayList<>();
		if(payHeadHistoryDTOList != null && !payHeadHistoryDTOList.isEmpty()) {
			for(AssociatePayrollPayheadHistoryDTO payHeadHistoryDTO : payHeadHistoryDTOList) {
				AssociatePayrollPayheadHistory  payHeadHistory = new AssociatePayrollPayheadHistory();
			    if(payHeadHistoryDTO.getId() != null) {
			    	payHeadHistory.setId(payHeadHistoryDTO.getId());
			    }
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
					payHeadHistory.setCreatedBy(user);
				}
				
				if(StringUtils.isNotEmpty(payHeadHistoryDTO.getCreatedDate())) {
					payHeadHistory.setCreatedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),payHeadHistoryDTO.getCreatedDate()));
				}
				if(StringUtils.isNotEmpty(payHeadHistoryDTO.getUpdatedDate())) {
					payHeadHistory.setUpdatedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),payHeadHistoryDTO.getUpdatedDate()));
				}
				payHeadHistoryList.add(payHeadHistory);
			}
		}
		return payHeadHistoryList;
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
	
	
	public void sendMailToIndPayrollApprover(OrgLocations orgLocation, User loginUser, PayrollApprover payrollApprover, IndPaysheetFrequency savedPF) throws Exception {
		
		
	}
	
	
	public void generatePdfForEmployeeInd(Set<IndEmployeePayroll> approvedPayrollList, OrgLocations orgLocation) {
		for (IndEmployeePayroll payroll : approvedPayrollList) {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void sendApprovedMailToRequestedUserInd(OrgLocations orgLocation, IndPaysheetFrequency savedPF, User loginUser){
		
		
	}
	
}

