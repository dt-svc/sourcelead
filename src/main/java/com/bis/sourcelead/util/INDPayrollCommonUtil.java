package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;

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
	
	public HashSet<AssociatePayrollPayheadHistory> getAssociatePayrollPayHeadHistoryFromAssociateSalaryPayHeads(Set<AssociateSalaryPayheadInd> payheadList,IndEmployeePayroll empPayroll,User loginUser) throws Exception {
		HashSet<AssociatePayrollPayheadHistory> payHeadHistoryList = new HashSet<>();
		if(payheadList != null && !payheadList.isEmpty()) {
			for(AssociateSalaryPayheadInd payHead : payheadList) {
				AssociatePayrollPayheadHistory payHeadHistory = new AssociatePayrollPayheadHistory();
				payHeadHistory.setIndEmployeePayrollId(empPayroll);
				payHeadHistory.setPayrollPayhead(payHead.getPayrollPayhead());
				payHeadHistory.setMonthly(payHead.getMonthly());
				payHeadHistory.setAnnualy(payHead.getAnnualy());
				payHeadHistory.setStatus(payHead.getStatus());

				payHeadHistory.setCreatedBy(loginUser);
				payHeadHistory.setUpdatedBy(loginUser);
				
				payHeadHistory.setCreatedDate(new Date());
				payHeadHistory.setUpdatedDate(new Date());
				payHeadHistoryList.add(payHeadHistory);
			}
		}
		return payHeadHistoryList;
	}
	
	
	
}

