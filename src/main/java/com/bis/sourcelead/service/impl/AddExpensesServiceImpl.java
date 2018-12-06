package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class AddExpensesServiceImpl implements AddExpensesService{

	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ExpensesEntriesService expensesEntriesService;
	
	@Autowired
	ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	ExpensesService expensesService;
	
	@Autowired
	private AddExpensesDao addExpensesDao;
	
	@Autowired
	ExpenseProjectApproverHierarchyService expenseProjectApproverHierarchyService;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;	
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	@Override
	public String getExpenseId(OrgLocations orgLoc) {
		//String expensesId = SourceLeadRandomCodeHelper.generateRandomExpensesIdCode();
		/*Long number = 000001L;
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
		return ConvetredexpensesId;*/
		
		String companyShortCode = null;
    	Long number = 1L;
		String expId = null;
		Integer generatedIdcount =0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLoc.getGeneratedUserId()).append(Constants.EXPENSES_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for(int i=0;i< generatedUsreId.length();i++){
			char ch = generatedUsreId.charAt(i);
			if(Character.isAlphabetic(ch)){
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount+1;
		Long checkNumber = addExpensesDao.checkExpensIdNumberInDatebase(orgLoc.getId(),generatedIdcount);
		if(checkNumber == null){
			expId = number.toString();
		}else{
		Long reqId = checkNumber+1;
		expId = reqId.toString();
		}
		//final DecimalFormat decimalFormat = new DecimalFormat("0"); /*OLD CODE*/  
		final DecimalFormat decimalFormat = new DecimalFormat("000000");                
        String convetredExpId = decimalFormat.format(Long.parseLong(expId));
        StringBuffer sb = new StringBuffer();
        if(orgLoc.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLoc.getGeneratedUserId())){
        	 companyShortCode = orgLoc.getGeneratedUserId();
        }
		return sb.append(companyShortCode).append(Constants.EXPENSES_ENTITY).append(convetredExpId).toString();
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
public void storeAddExpensesData(ExpensesDTO expensesDTO, User loginUser, OrgLocations orgLoc,HttpServletRequest request,String currencyCode) throws NumberFormatException, Exception {
	Expenses expenses = null;
	ProjectMembers projMembers = null;
	String status = "draft";
	if(StringUtils.isNotEmpty(expensesDTO.getExpenseStatus())) {
		status = expensesDTO.getExpenseStatus();
	}
	//SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();

	if(expensesDTO.getId() != null){
		expenses = expensesService.getExpenseById(expensesDTO.getId());
	}else {
		expenses = new Expenses();
	}
	
	if (StringUtils.isNotBlank(expensesDTO.getExpensePurpose())){
		expenses.setPurpose(expensesDTO.getExpensePurpose());	
	 }
	if (expensesDTO.getProjectMemberId()!= null){
		projMembers = projectMembersService.getProjectMemberById(expensesDTO.getProjectMemberId());
		expenses.setProjectMembers(projMembers);	
	 }
	if (StringUtils.isNotBlank(expensesDTO.getClientName())){
		expenses.setClientName(expensesDTO.getClientName());	
	 }
	if (StringUtils.isNotBlank(expensesDTO.getStartDate())){
		 Date startdate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), expensesDTO.getStartDate());
		expenses.setStartDate(startdate);	
	 }
	if (StringUtils.isNotBlank(expensesDTO.getEndDate())){
		Date enddate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), expensesDTO.getEndDate());
		expenses.setEndDate(enddate);	
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
	Set<ExpensesEntries>  expensesEntriesList = expensesEntriesService.prepareExpensesEntriesListByJson(expensesDTO, request, expenses);
	expenses.setExpensesEntries(expensesEntriesList);
	
	expenses.setNoOfExpenses(String.valueOf(expensesEntriesList.size()));
//	float totalAmount = 0;
	BigDecimal bd = new BigDecimal(0);
	for(ExpensesEntries expensesEntry : expensesEntriesList){
//		totalAmount = Float.sum(totalAmount, expensesEntry.getAmount());
		bd = bd.add(expensesEntry.getAmount());
	}
	expenses.setTotalAmount(bd);
	BigDecimal sa =new BigDecimal(0);
	for(ExpensesEntries expent : expensesEntriesList) {
		sa = sa.add(expent.getSubmittedamount());
	}
	expenses.setTotalsubmittedAmount(sa);
	HashMap<Integer,User> managers = new HashMap<>() ;
	if(projMembers != null){
		HashSet<ExpensesApproverHierarchy> eaHierarchyList = new HashSet<ExpensesApproverHierarchy>();
		if (expensesDTO.getId() != null){
		   List<ExpensesApproverHierarchy>eaSubHierarchyList  = expensesApprovalService.getExpensesApproverHierarchyListByExpenseId(expensesDTO.getId());
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
				    managers.put(eaHierarchy.getHierarchyId().getLevel(),eaHierarchy.getHierarchyId().getApproverUserId());
					eaHierarchyList.add(eaHierarchy);
			   }
		   }
		}
		else{
			/*List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(projMembers.getProjects().getId());*/
			List<ExpenseProjectApproverHierarchy> hierarchyList = expenseProjectApproverHierarchyService.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projMembers.getMasterCandidateId().getId(),projMembers.getProjects().getId());
			 if(hierarchyList != null && !hierarchyList.isEmpty()){
						for(ExpenseProjectApproverHierarchy hierarchy : hierarchyList){
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
							managers.put(hierarchy.getLevel(),hierarchy.getApproverUserId());
							eaHierarchyList.add(eaHierarchy);
						}
				}
		}
		
		if(eaHierarchyList != null && !eaHierarchyList.isEmpty()){
			expenses.setApproverHierarchyList(eaHierarchyList);
		}
	 }
	
	expenses.setMasterCandidateId(projMembers.getMasterCandidateId());
	expenses.setProjects(projMembers.getProjects());
	if(null != expenses.getProjects() && expenses.getProjects().getOrgLocationsId() != null && expenses.getProjects().getOrgLocationsId().getOrg() != null && StringUtils.isNotEmpty(expenses.getProjects().getOrgLocationsId().getOrg().getOrgType())
			&& !expenses.getProjects().getOrgLocationsId().getOrg().getOrgType().equalsIgnoreCase(Constants.GUEST)){
		expenses.setOrgLocationId(expenses.getProjects().getOrgLocationsId());
	}
	else{
		expenses.setOrgLocationId(orgLoc);
	}
	
	expenses.setExpensesLocationId(orgLoc.getId());
	
	if(expenses != null && expenses.getProjects() != null && StringUtils.isNotEmpty( expenses.getProjects().getIsReportingManagerAsApprover())
			   &&  expenses.getProjects().getIsReportingManagerAsApprover().equalsIgnoreCase("Y")) {
		   if(projMembers != null && projMembers.getProjectMemberCandidteId() != null &&  projMembers.getProjectMemberCandidteId().getConsultantUser() != null
				   &&  projMembers.getProjectMemberCandidteId().getConsultantUser()!= null && projMembers.getProjectMemberCandidteId().getOrgLocationId() != null) {
			   UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(projMembers.getProjectMemberCandidteId().getConsultantUser().getId(), projMembers.getProjectMemberCandidteId().getOrgLocationId().getId());
			   if(userRole != null && userRole.getReportingManager() != null) {
				   managers.put(1,userRole.getReportingManager());
			   }
		   }
	   }
	
	if (StringUtils.isEmpty(expenses.getExpenseId())){
	    /*OrgLocations orgLocations = null;
	    Projects projects =projMembers.getProjects();
		if(projects != null && projects.getOrgLocationsId() != null && projects.getOrgLocationsId().getOrg() != null && StringUtils.isNotEmpty(projects.getOrgLocationsId().getOrg().getOrgType()) && !projects.getOrgLocationsId().getOrg().getOrgType().equalsIgnoreCase(Constants.GUEST)) {
			orgLocations = projects.getOrgLocationsId();
		}else {
			orgLocations = orgLoc;
		}*/
		 synchronized (this) {
			   String expenseId = getExpenseId(orgLoc);
			   expenses.setExpenseId(expenseId);
			   expenses = addExpensesDao.storeAddExpensesData(expenses);
			   storeExpenseApprovers(status, expenses, managers, loginUser, orgLoc,currencyCode);
		 }
		   
    }else {
    	 expenses = addExpensesDao.storeAddExpensesData(expenses);
    	 storeExpenseApprovers(status, expenses, managers, loginUser, orgLoc,currencyCode);
    }
	
}

private void storeExpenseApprovers(String status,Expenses expenses,HashMap<Integer,User> managers,User loginUser,OrgLocations orgLoc,String currencyCode) throws Exception{
	 if(status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())){
		 if(managers != null && !managers.isEmpty()){
				expenses.setProjectManagerUserList(new ArrayList(managers.values()));
	     }
		 expensesApprovalService.updateApprovalStatusByApproverForExpense(expenses.getId(), loginUser.getId(),"APPROVED", "",orgLoc);
	 }
	 
	 if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())){
		managers.remove(2);
		 if(managers != null && !managers.isEmpty()){
				expenses.setProjectManagerUserList(new ArrayList(managers.values()));
		 }
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
		 
		 
		 Set<ExpensesEntries> expensesEntries = expenses.getExpensesEntries();
		 for(ExpensesEntries exp : expensesEntries) {
			 
			 globalVarsMap.put("totalAmount",exp.getSubmittedamount().toString());
			 
		 }
			 
		 
		/* StringBuilder str = new StringBuilder();
		 BigDecimal cg = new BigDecimal(0);
				 cg.add(expenses.getTotalAmount());
		 
			BigDecimal cg = new BigDecimal(Double.toString(expenses.getTotalAmount()));
			cg = cg.setScale(2,BigDecimal.ROUND_HALF_UP );		
			
		 globalVarsMap.put("totalAmount",str.append(cg).append(" ").append(currencyCode).toString());*/

			StringBuilder stringBuildforLink = new StringBuilder();
			globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_EMAIL_TO_MANAGER,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_EMAIL_TO_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);

		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLoc.getOrg(),orgLoc,Constants.EXPENSES_EMAIL_TO_MANAGER,new ArrayList<EmailAttachment>());
			
		 String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,emailScenario);
			 notificationScenario.setBody(notificationBody);
			 StringBuilder notificationLink = new StringBuilder();
			 String actionLink="";
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();

			emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, orgLoc, notificationScenario,new HashMap<String,String>());
	}
}
}

@Override
public void deleteDocumentByDocumentId(String documentId) {
	addExpensesDao.deleteDocumentByDocumentId(Long.parseLong(documentId));
	
}


@Override
public Response<String> changeStatusToBillable(long expenseEntriesId,String billableStatus) {
	Response<String> response = new Response<>();
	try {
		long result = addExpensesDao.changeStatusToBillable(expenseEntriesId,billableStatus);
		if (result > 0) {
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} else if (result == -2) {
			response.setResponse(Result.INVALID_DATA.name());
			response.setResponseMessage(Result.INVALID_DATA.getValue());
		} else {
			response.setResponse(Result.FAILED.name());
			response.setResponseMessage(Result.FAILED.getValue());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return response;
}
}