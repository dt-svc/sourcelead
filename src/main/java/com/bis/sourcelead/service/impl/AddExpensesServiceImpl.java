package com.bis.sourcelead.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.ibm.icu.math.BigDecimal;

@Service
public class AddExpensesServiceImpl implements AddExpensesService{

	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ExpensesEntriesService expensesEntriesService;
	
	@Autowired
	ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	ExpensesService expensesService;
	
	@Autowired
	private AddExpensesDao addExpensesDao;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public String getExpenseId(OrgLocations orgLoc) {
		//String expensesId = SourceLeadRandomCodeHelper.generateRandomExpensesIdCode();
		Long number = 000001L;
		String expensesId = null;
		Expenses checkNumber = addExpensesDao.checkExpensIdNumberInDatebase(orgLoc.getId());
		if(checkNumber == null){
			 expensesId = number.toString();
		}else{
		String expIdNumber = checkNumber.getExpenseId();
		Long expId = Long.parseLong(expIdNumber)+1;
			expensesId = expId.toString();
		}
		
        final DecimalFormat decimalFormat = new DecimalFormat("0000");                
        String ConvetredexpensesId = decimalFormat.format(Long.parseLong(expensesId));
		
		return ConvetredexpensesId;
		
	}
	
@Override
public List<ProjectMembers> getAllProjectsForExpenses(OrgLocations orgLoc,User loginUser) {
	List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndEmpUserId(orgLoc.getId(),loginUser.getId());
	
	return projectMembersList;
	
	}

@Override
public Expenses storeAddExpensesData(Expenses expenses) throws Exception{
	return addExpensesDao.storeAddExpensesData(expenses);
}

@Override
public void storeAddExpensesData(ExpensesDTO addExpensesDTO, User loginUser, OrgLocations orgLoc,HttpServletRequest request,String status,String currencyCode) throws NumberFormatException, Exception {
	Expenses expenses = null;
	ProjectMembers projMembers = null;
	
	//SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
	
	if(addExpensesDTO.getId() != null){
		expenses = expensesService.getExpenseById(addExpensesDTO.getId());
	}else {
		expenses = new Expenses();
	}
	if (StringUtils.isNotBlank(addExpensesDTO.getExpenseAutoId())){
		expenses.setExpenseId(addExpensesDTO.getExpenseAutoId());	
	 }
	if (StringUtils.isNotBlank(addExpensesDTO.getExpensePurpose())){
		expenses.setPurpose(addExpensesDTO.getExpensePurpose());	
	 }
	if (addExpensesDTO.getProjectMemberId()!= null){
		projMembers = projectMembersService.getProjectMemberById(addExpensesDTO.getProjectMemberId());
		expenses.setProjectMembers(projMembers);	
	 }
	if (StringUtils.isNotBlank(addExpensesDTO.getClientName())){
		expenses.setClientName(addExpensesDTO.getClientName());	
	 }
	if (StringUtils.isNotBlank(addExpensesDTO.getStartDate())){
		 Date startdate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), addExpensesDTO.getStartDate());
		expenses.setStartDate(startdate);	
	 }
	if (StringUtils.isNotBlank(addExpensesDTO.getEndDate())){
		Date enddate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), addExpensesDTO.getEndDate());
		expenses.setEndDate(enddate);	
	 }
	if(null != orgLoc){
		expenses.setOrgLocationId(orgLoc);
	}
	if(null == expenses.getId()){
		expenses.setCreatedBy(loginUser.getUserCode());
	}
	expenses.setUpdatedBy(loginUser.getUserCode());
	if(null == expenses.getId()){
		expenses.setCreatedDate(CommonUtil.currentDate());
	}
	expenses.setUpdatedDate(CommonUtil.currentDate());
	if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())){
		expenses.setExpenseStatus(Submission_Status.SUBMITTED);
	}
	else if(status.equalsIgnoreCase(Submission_Status.DRAFT.getValue())){
		expenses.setExpenseStatus(Submission_Status.DRAFT);
	}
	/*else if(status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())){
		expenses.setExpenseStatus(Submission_Status.APPROVED);
	}*/
	/*else if("approverSubmitted".equalsIgnoreCase(status) || "approverApproved".equalsIgnoreCase(status)){
		expenses.setExpenseStatus(Submission_Status.SUBMITTED);
	}*/
	/*else if("approverApproved".equalsIgnoreCase(status)){
		
	}*/
	expenses.setStatus(Constants.EXPENSES_STATUS);
	Set<ExpensesEntries>  expensesEntriesList = expensesEntriesService.prepareExpensesEntriesListByJson(addExpensesDTO, request, expenses);
	expenses.setExpensesEntries(expensesEntriesList);
	
	expenses.setNoOfExpenses(String.valueOf(expensesEntriesList.size()));
	float totalAmount = 0;
	for(ExpensesEntries expensesEntry : expensesEntriesList){
		totalAmount = Float.sum(totalAmount, expensesEntry.getAmount());
	}
	expenses.setTotalAmount(totalAmount);
	
	if(projMembers != null){
		ArrayList<User> managers = new ArrayList<User>() ;
		HashSet<ExpensesApproverHierarchy> eaHierarchyList = new HashSet<ExpensesApproverHierarchy>();
		if (addExpensesDTO.getId() != null){
		   List<ExpensesApproverHierarchy>eaSubHierarchyList  = expensesApprovalService.getExpensesApproverHierarchyListByExpenseId(addExpensesDTO.getId());
		   if(eaSubHierarchyList != null && !eaSubHierarchyList.isEmpty()){
			   for(ExpensesApproverHierarchy eaHierarchy : eaSubHierarchyList){
					if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue()) || status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())){
						eaHierarchy.setExpenseStatus(Submission_Status.SUBMITTED);
						eaHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);	
					}
					else if(status.equalsIgnoreCase(Submission_Status.DRAFT.getValue())){
						eaHierarchy.setExpenseStatus(Submission_Status.DRAFT);
						eaHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.NEW);
					}
					eaHierarchy.setUpdatedDate(expenses.getUpdatedDate());
					eaHierarchy.setUpdatedBy(expenses.getUpdatedBy());
					managers.add(eaHierarchy.getHierarchyId().getApproverUserId());
					eaHierarchyList.add(eaHierarchy);
			   }
		   }
		}
		else{
			/*List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(projMembers.getProjects().getId());*/
			List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projMembers.getMasterCandidateId().getId(),projMembers.getProjects().getId());
			 if(hierarchyList != null && !hierarchyList.isEmpty()){
		        	
						for(ProjectApproverHierarchy hierarchy : hierarchyList){
							ExpensesApproverHierarchy eaHierarchy = new ExpensesApproverHierarchy();
							eaHierarchy.setHierarchyId(hierarchy);
							eaHierarchy.setExpenseId(expenses);
							
							if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue()) || status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())){
								eaHierarchy.setExpenseStatus(Submission_Status.SUBMITTED);
								eaHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);	
							}
							else if(status.equalsIgnoreCase(Submission_Status.DRAFT.getValue())){
								eaHierarchy.setExpenseStatus(Submission_Status.DRAFT);
								eaHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.NEW);
							}
							eaHierarchy.setCreatedDate(expenses.getCreatedDate());
							eaHierarchy.setCreatedBy(expenses.getCreatedBy());
							eaHierarchy.setUpdatedDate(expenses.getUpdatedDate());
							eaHierarchy.setUpdatedBy(expenses.getUpdatedBy());
							eaHierarchy.setStatus(Constants.RECORD_ACTIVE+"");
							managers.add(hierarchy.getApproverUserId());
							eaHierarchyList.add(eaHierarchy);
						}
				}
		}
		
		if(eaHierarchyList != null && !eaHierarchyList.isEmpty()){
			expenses.setApproverHierarchyList(eaHierarchyList);
		}
		if(managers != null && !managers.isEmpty()){
			expenses.setProjectManagerUserList(managers);
		}
	 }
	
	expenses.setMasterCandidateId(projMembers.getMasterCandidateId());
	expenses.setProjects(projMembers.getProjects());
	
	
	 expenses = addExpensesDao.storeAddExpensesData(expenses);
	 if(status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())){
		 expensesApprovalService.updateApprovalStatusByApproverForExpense(expenses.getId(), loginUser.getId(),"APPROVED", "");
	 }
	 if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())){
		 sendExpensesEmailToManager(loginUser,expenses,orgLoc,currencyCode);
	 }
}

@Override
public void sendExpensesEmailToManager(User loginUser, Expenses expenses, OrgLocations orgLoc,String currencyCode) throws Exception {
	
	 
	
	 EmailMessage emailMessage = new EmailMessage();
	
	 if(null != expenses.getProjectManagerUserList()){
	for(User managerUser :expenses.getProjectManagerUserList()){
		
		 StringBuilder sb = new StringBuilder();
		 emailMessage.setFirstName(managerUser.getFirstName());
		 if(null != managerUser.getLastName()){
			emailMessage.setLastName(managerUser.getLastName());
		 }
		 emailMessage.setToEmail(managerUser.getEmail());
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
			 globalVarsMap.put("employeeUser", employee.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("employeeUser",loginUser.getFirstName());
		 }
		 
		 if(null != managerUser.getLastName() && !"".equalsIgnoreCase(managerUser.getLastName())){
			 StringBuilder manager =new StringBuilder();
			 globalVarsMap.put("managerUser", manager.append(managerUser.getFirstName()).append(" ").append(managerUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("managerUser",managerUser.getFirstName());
		 }
		 
		 if(null != expenses.getProjectMembers()){
			 if(null != expenses.getProjectMembers().getProjects()){
		 globalVarsMap.put("projectName", expenses.getProjectMembers().getProjects().getProjectName());
			 }
		 }
		 
		 if(null != expenses.getPurpose()){
		 globalVarsMap.put("purposeOfVisit",expenses.getPurpose());
		 }
		 
		 if(null != expenses.getNoOfExpenses()){
		 globalVarsMap.put("expenseTypes",expenses.getNoOfExpenses());
		 }
		 
		 StringBuilder str = new StringBuilder();
			BigDecimal cg = new BigDecimal(Double.toString(expenses.getTotalAmount()));
			cg = cg.setScale(2,BigDecimal.ROUND_HALF_UP );		
			
		 globalVarsMap.put("totalAmount",str.append(cg).append(" ").append(currencyCode).toString());
		 
		 

			StringBuilder stringBuildforLink = new StringBuilder();
			globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_EMAIL_TO_MANAGER,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_EMAIL_TO_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,orgLoc.getOrg(),orgLoc);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLoc.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		//calling the sendNotification method of emailScenerioUtil
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, orgLoc, notificationScenario);
	}
}
}

@Override
public void deleteDocumentByDocumentId(String documentId) {
	addExpensesDao.deleteDocumentByDocumentId(Long.parseLong(documentId));
	
}
}