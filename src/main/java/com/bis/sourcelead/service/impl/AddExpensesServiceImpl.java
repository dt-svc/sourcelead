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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Candidate;
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
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class AddExpensesServiceImpl implements AddExpensesService{

    @Autowired
    ProjectMembersService projectMembersService;
    
    @Autowired
    ExpensesEmailsUtil expensesEmailsUtil;
    
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
    private CandidateDao candidateDao;
    
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


public void storeAddExpensesData(ExpensesDTO expensesDTO, User loginUser, OrgLocations orgLoc,HttpServletRequest request,String currencyCode,String isGlobalUser,String loginUserType) throws NumberFormatException, Exception {
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
    if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())){
        expenses.setSubmittedDate(CommonUtil.currentDate());
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
//    float totalAmount = 0;
    BigDecimal bd = new BigDecimal(0);
    for(ExpensesEntries expensesEntry : expensesEntriesList){
//        totalAmount = Float.sum(totalAmount, expensesEntry.getAmount());
        bd = bd.add(expensesEntry.getAmount());
    }
    expenses.setTotalAmount(bd);
    BigDecimal sa =new BigDecimal(0);
    for(ExpensesEntries expent : expensesEntriesList) {
        sa = sa.add(expent.getSubmittedamount());
    }
    expenses.setTotalsubmittedAmount(sa);
    ArrayList<User> managers = new ArrayList<>();
    if(projMembers != null){
        List<ExpensesApproverHierarchy> eaHierarchyList = new ArrayList<ExpensesApproverHierarchy>();
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
                    User managerUser = eaHierarchy.getHierarchyId().getApproverUserId();
                    managerUser.setLocationCode(eaHierarchy.getHierarchyId().getProjectClientsId().getOrgLocationsId().getLocationCode());
                    managers.add(managerUser);
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
                            User approverUser = hierarchy.getApproverUserId();
                            approverUser.setLocationCode(hierarchy.getProjectClientsId().getOrgLocationsId().getLocationCode());
                            approverUser.setOrgLocationId(hierarchy.getProjectClientsId().getOrgLocationsId().getId());
                            managers.add(approverUser);
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
    
//    expenses.setExpensesLocationId(orgLoc.getId());
    
    if(expenses != null && expenses.getProjects() != null && StringUtils.isNotEmpty( expenses.getProjects().getIsReportingManagerAsApprover())
               &&  expenses.getProjects().getIsReportingManagerAsApprover().equalsIgnoreCase("Y")) {
           if(projMembers != null && projMembers.getProjectMemberCandidteId() != null &&  projMembers.getProjectMemberCandidteId().getConsultantUser() != null
                   &&  projMembers.getProjectMemberCandidteId().getConsultantUser()!= null && projMembers.getProjectMemberCandidteId().getOrgLocationId() != null) {
               UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(projMembers.getProjectMemberCandidteId().getConsultantUser().getId(), projMembers.getProjectMemberCandidteId().getOrgLocationId().getId());
               if(userRole != null && userRole.getReportingManager() != null) {
                   User reportingManagerUser = userRole.getReportingManager();
                   reportingManagerUser.setLocationCode(userRole.getOrgLocations().getLocationCode());
                   reportingManagerUser.setOrgLocationId(userRole.getOrgLocations().getId());
                   managers.add(reportingManagerUser);
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
                String expenseId = "";
                Long expenseLocationId = 0l;
                if(expenses.getOrgLocationId() != null) {
                    expenseLocationId =expenses.getOrgLocationId().getId();
                    expenseId = getExpenseId(expenses.getOrgLocationId());
                }else {
                    expenseLocationId =orgLoc.getId();
                    expenseId = getExpenseId(orgLoc);
                }
                
                expenses.setExpensesLocationId(expenseLocationId);
                
               expenses.setExpenseId(expenseId);
               expenses = addExpensesDao.storeAddExpensesData(expenses);
               storeExpenseApprovers(status, expenses, managers, loginUser, orgLoc,currencyCode,isGlobalUser,loginUserType);
         }
           
    }else {
         expenses = addExpensesDao.storeAddExpensesData(expenses);
         storeExpenseApprovers(status, expenses, managers, loginUser, orgLoc,currencyCode,isGlobalUser,loginUserType);
    }
    
}

private void storeExpenseApprovers(String status, Expenses expenses, ArrayList<User> managers, User loginUser,
        OrgLocations orgLoc, String currencyCode, String isGlobalUser, String loginUserType) throws Exception {

     if(status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())){
         if(managers != null && !managers.isEmpty()){
             
                expenses.setProjectManagerUserList(managers);
         }
         expensesApprovalService.updateApprovalStatusByApproverForExpense(expenses.getId(), loginUser.getId(),"APPROVED", "",orgLoc,isGlobalUser);
     }
     
     
     if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())){
        //managers.remove(2);
         if(managers != null && !managers.isEmpty()){
                expenses.setProjectManagerUserList(managers);
         }
         if(loginUserType.equalsIgnoreCase("Employee")){
         expensesEmailsUtil.sendExpensesEmailToManager(loginUser,expenses,orgLoc,currencyCode);
     }
         if(loginUserType.equalsIgnoreCase("Manager")||loginUserType.equalsIgnoreCase("Admin")){
         expensesEmailsUtil.sendExpenseMailToEmployee(loginUser,expenses,orgLoc,currencyCode);
         expensesEmailsUtil.sendExpensesEmailToManagerWhenManagerSubmitsExpenseBehalfofEmployee(loginUser,expenses,orgLoc,currencyCode);
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