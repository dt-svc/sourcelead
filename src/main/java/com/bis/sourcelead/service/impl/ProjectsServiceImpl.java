package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dto.AvailableAssociatesDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientProjectDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectCustomFieldData;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectMembersRateHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectCustomFieldDataService;
import com.bis.sourcelead.service.ProjectDocumentFolderService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersRateHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.bis.sourcelead.web.RegistrationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.layout.element.Cell;

@SuppressWarnings("unchecked")
@Service
public class ProjectsServiceImpl implements ProjectsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceImpl.class);

	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	 
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private ProjectApproverHierarchyDao projectApproverHierarchyDao;
	  
	@Autowired
	private EmailScenarioService emailScenarioService;
	    
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	    
	@Autowired
	private UserService userService;
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	public ProjectMembersService projectMembersService;
	
	 @Autowired
	 private OrgRelationsService orgRelationsService;

	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 private  ProjectMembersHistoryService projectMembersHistoryService;
	 
	 @Autowired
	 public EmployeesService employeesService;
	 
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;
	 
	 @Autowired
	 public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 @Autowired
	 private LookUpDataService lookUpDataService;
	 
	 @Autowired
	 private ProjectTasksService projectTasksService;
	 
	 @Autowired
	 private TimesheetSubmissionsService timesheetSubmissionsService;
	 
	 @Autowired
	 private ExpensesService expensesService;
	 
	 @Autowired
	 private ProjectsIndexHelper projectsIndexHelper;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<ProjectsIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private UniversalSearchHelper universalSearchHelper;
	 
	 @Autowired
	 private ProjectClientService projectClientService;
	 
	 @Autowired
	 RegistrationController registrationController;
	 
     @Autowired
     ProjectBudgetService projectBudgetService;
     
	 @Autowired
	 ProjectDocumentFolderService projectDocumentFolderService;
	 
	 
	 @Autowired
	 private OrglocationFormFieldsService orglocationFormFieldsService;
	 
	 @Autowired
	 private ProjectCustomFieldDataService projectCustomFieldDataService;
	 
	 @Autowired
	 private ProjectTasksDao tasksDao;
	 
	 @Autowired
	 OrgSpocDao orgSpocDao;
	 
	 @Autowired
	 CandidateService candidateService;
	 
	 @Autowired
	 MasterCandidateService masterCandidateService;
	 
	 @Autowired
	 ExpensesApprovalService expensesApprovalService;
	 
	 @Autowired
	 ProjectMembersRateHistoryService projectMembersRateHistoryService;

	private UserDTO userStatus;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private LeaveTypeService leaveTypeService;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioconService;
	
	
	@Autowired
	public ExpenseProjectApproverHierarchyService expenseProjectApproverHierarchyService;
	 
/*	 @Override
		public String getProjectId(OrgLocations orgLocations) {
			
	    	Long number = 000000001L;
			String candidateId = null;
			Projects checkNumber = projectsDao.checkProjectIdNumberInDatebase(orgLocations.getId());
			if(checkNumber == null || checkNumber.getGeneratedId().isEmpty()){
				candidateId = number.toString();
			}else{
			String canIdNumber = checkNumber.getGeneratedId();
			String canIdNum = canIdNumber.substring(2, canIdNumber.length());
			Long canId = Long.parseLong(canIdNum)+1;
				candidateId = canId.toString();
			}
			
	        final DecimalFormat decimalFormat = new DecimalFormat("000000000");                
	        String ConvetredCandidatesId = decimalFormat.format(Long.parseLong(candidateId));
	        StringBuffer sb = new StringBuffer();
			return sb.append(Constants.PROJECT_ENTITY).append(ConvetredCandidatesId).toString();
			
		}*/
	 
	@Override
	public Projects saveProject(Projects projects) throws Exception{
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		projects.setUpdatedDate(commonUtil.currentDate());
		return  projectsDao.saveProject(projects);
	}

	@Override
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In ProjectsServiceImpl class updateProject method...");
		projects.setUpdatedDate(commonUtil.currentDate());
		return projectsDao.updateProject(projects);
	}

	@Override
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProject method...");
		return projectsDao.getProjectById(id);
	}
	
	@Override
	public List<Long> getProjectIdsByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectIdsByOrgLocationsId method...");
		return projectsDao.getProjectIdsByOrgLocationsId(orgLocationsId);
	}
	
	@Override
	public List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectByOrgLocationsId method...");
		return projectsDao.getProjectByOrgLocationsId(orgLocationsId);
	}
	
	@Override
	public List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate method...");
		return projectsDao.getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(orgLocationsId, loginUserId);
	}
	
	@Override
	public List<Projects> getAllProjectsEndDateGraterthanCurrentDate() throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjectsEndDateGraterthanCurrentDate method...");
		return projectsDao.getAllProjectsEndDateGraterthanCurrentDate();
	}
	
	@Override
	public Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getPrimaryProjectByOrgLocationsId method...");
		return projectsDao.getPrimaryProjectByOrgLocationsId(orgLocationsId);
	}

	@Override
	public Boolean deleteProjectById(Long Id) throws Exception {
		logger.debug("In ProjectsServiceImpl class deleteProject method...");
		Boolean isDeleted = projectsDao.deleteProjectById(Id);
		return isDeleted;
	}
	
	@Override
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class validateProjectSequeneNumber method...");
		Boolean flag = projectsDao.validateProjectSequeneNumber(projectSequeneNumber,orgLocationsId);
		return flag;
	}

	@Override
	public List<Projects> getAllProjects() throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjects method...");
		List<Projects> projectsList = projectsDao.getAllProjects();
		return projectsList;
	}
	
	public List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjectsAndManagers method...");
		List<Projects> projectsList = projectsDao.getAllLoginUserProjectsList(projectIds,orgLocationId);
		return projectsList;
	}
	
	@Override
	public Integer projectIdValidation(Long projectId) throws Exception {
		logger.debug("In ProjectsServiceImpl class deleteProject method...");
		Integer validation = projectsDao.projectIdValidation(projectId);
		return validation;
	}

	@Override
	public List<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllClientProjectsByOrgRelationId method...");
		List<Projects> clientProjectsList = projectsDao.getAllClientProjectsByOrgRelationId(orgRelationId,locationId);
		return clientProjectsList;
	}

	@Override
	public Integer updateStartDateOfProjectId(Date startDate , Long projectId) {
		return projectsDao.updateStartDateOfProjectId(startDate,projectId);
	}

	@Override
	public Integer updateEndDateOfProjectId(Date endDate, Long projectId) {
		return projectsDao.updateEndDateOfProjectId(endDate,projectId);
	}
	
	@Override
	public List<Long> getApprovarAssignedProjects(Long userId, Long orgLocationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getApprovarAssignedProjects method...");
		List<Long> projectIdsList = projectsDao.getApprovarAssignedProjects(userId, orgLocationId);
		return projectIdsList;
	}

	@Override
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception {
		
		return projectsDao.getProjectByProjectSequenceNumber(projectSequeneNumber);
	}
	
	@Override
	public Boolean validateProjectShortCode(String shortCode, Long orgLocationsId) throws Exception {
		return projectsDao.validateProjectShortCode(shortCode, orgLocationsId);
	}
	 /**
	 * @author sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save projects
	 * 1.from general project saving
	 * 2.from user saving
	 * 
	 * @param jsonObj This is the first paramter to storeProject method
	 *  
	 * @return string 
	 * 
	 * @throws JSONException
	 */
	
	@Override
	public Projects storeProject(ProjectsDTO projectsDTO) throws Exception {
		    
		  
	 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
        
	 	Projects projects = null;
	 	if(!isNullOrEmpty(projectsDTO.getProjectId())){
	 		projects =  projectsDao.getProjectById(Long.parseLong(projectsDTO.getProjectId()));
	 	}else{
	 		projects =  new Projects();
	 		projects.setProjectUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	 		projects.setIsDefaultProject(Constants.NO);
	 	}
    	
    	projects.setProjectName(projectsDTO.getProjectName().trim());
    	if(!isNullOrEmpty(projectsDTO.getProjectSequeneNumber())) {
    		projects.setProjectSequeneNumber(projectsDTO.getProjectSequeneNumber().trim());
    	}
    	
    	if(!isNullOrEmpty(projectsDTO.getIsReportingManagerAsApprover()) && projectsDTO.getIsReportingManagerAsApprover().equalsIgnoreCase(Constants.YES)) {
    		projects.setIsReportingManagerAsApprover(Constants.YES);
    	}else {
    		projects.setIsReportingManagerAsApprover(Constants.NO);
    	}
    	
    	
    	if(!isNullOrEmpty(projectsDTO.getIsTicketNumberRequired()) && projectsDTO.getIsTicketNumberRequired().equalsIgnoreCase(Constants.YES)) {
    		projects.setIsTicketNumberRequired(Constants.YES);
    	}else {
    		projects.setIsTicketNumberRequired(Constants.NO);
    	}
    	
    	if(!isNullOrEmpty(projectsDTO.getIsTaskCommentsRequired()) && projectsDTO.getIsTaskCommentsRequired().equalsIgnoreCase(Constants.YES)) {
    		projects.setIsTaskCommentsRequired(Constants.YES);
    	}else {
    		projects.setIsTaskCommentsRequired(Constants.NO);
    	}
    	
    	if(!isNullOrEmpty(projectsDTO.getProjectDescription())){
    	projects.setDescription(projectsDTO.getProjectDescription());
    	}
    	if(!isNullOrEmpty(projectsDTO.getStartDate())){
    		Date startDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectsDTO.getStartDate());
	    	projects.setStartDate(startDate);
    	}
    	if(!isNullOrEmpty(projectsDTO.getEndDate())){
    		Date endDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectsDTO.getEndDate());
	    	projects.setEndDate(endDate);
    	}
    	
    	if(!isNullOrEmpty(projectsDTO.getProjectForValue())) {
    		if(projectsDTO.getProjectForValue().equalsIgnoreCase("Timesheets")) {
    			projects.setIsProjectForTimeSheets(Constants.YES);
    			projects.setIsProjectForExpenses(Constants.NO);
    		}else if(projectsDTO.getProjectForValue().equalsIgnoreCase("Expenses")) {
    			projects.setIsProjectForExpenses(Constants.YES);
    			projects.setIsProjectForTimeSheets(Constants.NO);
    		}else {
    			projects.setIsProjectForTimeSheets(Constants.YES);
    			projects.setIsProjectForExpenses(Constants.YES);
    		}
    	}
    	
    	
		if(!isNullOrEmpty(projectsDTO.getInvoiceFrequency())){
			projects.setInvoiceFrequency(projectsDTO.getInvoiceFrequency());
			projects.setTimesheetFrequency(projectsDTO.getInvoiceFrequency());
	    }else{
	    	projects.setInvoiceFrequency(Constants.WEEKLY);
	    	projects.setTimesheetFrequency(Constants.WEEKLY);
	    }
		
		/*if(!isNullOrEmpty(projectsDTO.getTimeSheetFrequency())){
			projects.setTimesheetFrequency(projectsDTO.getTimeSheetFrequency());
		}
		*/
		
		if(!isNullOrEmpty(projectsDTO.getIsAllowFreelanceDevelopers())){
			projects.setAllowFreeLance(BooleanUtils.toBoolean(projectsDTO.getIsAllowFreelanceDevelopers(),"Y",""));
		}else{
			projects.setAllowFreeLance(null);
		}
		
		if(!isNullOrEmpty(projectsDTO.getIsAllowRemoteWork())){
			projects.setAllowRemote(BooleanUtils.toBoolean(projectsDTO.getIsAllowRemoteWork(),"Y",""));
		}else{
			projects.setAllowRemote(null);
		}
		
		if(!isNullOrEmpty(projectsDTO.getIsAllowOverTime())){
			projects.setAllowOvertime(BooleanUtils.toBoolean(projectsDTO.getIsAllowOverTime(),"Y",""));
		}else{
			projects.setAllowOvertime(null);
		}
		
		if(!projectsDTO.getFlatPercentage().isEmpty()){
			projects.setFlatPercent(Double.parseDouble(projectsDTO.getFlatPercentage()));
		}
		
		if(!isNullOrEmpty(projectsDTO.getFlatPercentageId())){
			projects.setOvertimeRateType(projectsDTO.getFlatPercentageId());
		}
		
		
		if(projectsDTO.getIsTimesheetAttachmentRequired() != null){
	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_ACTIVE);
	    }else{
	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_INACTIVE);
	    }	
		
		if(!isNullOrEmpty(projectsDTO.getIsAllowOffshoreResource())){
			projects.setAllowOffShore(BooleanUtils.toBoolean(projectsDTO.getIsAllowOffshoreResource(),"Y",""));
		}else{
			projects.setAllowOffShore(null);
		}
		
		if(StringUtils.isNotBlank(projectsDTO.getEstimatedBudgetCurrency())) {
			projects.setCurrency(projectsDTO.getEstimatedBudgetCurrency());
		}
		
		if(!isNullOrEmpty(projectsDTO.getIsTimeEntryAfterDeadline())){
			projects.setAllowTimeEntryAfterDeadLine(BooleanUtils.toBoolean(projectsDTO.getIsTimeEntryAfterDeadline(),"Y",""));
		}else{
			projects.setAllowTimeEntryAfterDeadLine(null);
		}
		
		if(!isNullOrEmpty(projectsDTO.getIsAllowBidding())){
			projects.setAllowBidding(BooleanUtils.toBoolean(projectsDTO.getIsAllowBidding(),"Y",""));
		}else{
			projects.setAllowBidding(null);
		}
		
		if(!isNullOrEmpty(projectsDTO.getProjectApprovalTypeValue())){
			projects.setProjectApprovalType(projectsDTO.getProjectApprovalTypeValue());
		}else {
			projects.setProjectApprovalType(Constants.PARALLEL);
		}
		
		if(!isNullOrEmpty(projectsDTO.getExpenseApprovalTypeValue())){
			projects.setExpenseApprovalType(projectsDTO.getExpenseApprovalTypeValue());
		}else {
			projects.setExpenseApprovalType(Constants.PARALLEL);
		}
		
		Set<ProjectDocuments> projDocDBList = new HashSet<>();
		if(projectsDTO .getUploadedFileInfo() != null &&  !projectsDTO.getUploadedFileInfo().isEmpty()) {
			
			Set<ProjectDocumentFolder> projectDocumentFolder = storeDocumentFolder(projects,user);
			projects.setProjectDocumentFolder(projectDocumentFolder);
			
			//save documents
			for(ProjectDocumentFolder pdf : projectDocumentFolder) {
				List<FileInfo> projectDocumentsList = projectsDTO .getUploadedFileInfo();
				for(FileInfo fileInfo : projectDocumentsList){
					ProjectDocuments documents = new ProjectDocuments();
					documents.setFileName(fileInfo.getFileName());
					documents.setFileType(fileInfo.getContentType());
					documents.setFileUrl(fileInfo.getFileURL());
					documents.setStatus(Constants.CANDIDATE_ACTIVE);
					documents.setProjects(projects);
					documents.setProjectFolderId(pdf);
					documents.setCreatedBy(user);
					documents.setCreatedByName(user.getFullName());
					documents.setCreatedDate(commonUtil.currentDate());
					projDocDBList.add(documents);
				}
			}
			
			projects.setProjectDocuments(projDocDBList);
	        for(FileInfo fileInfo : projectsDTO.getUploadedFileInfo()){
				FileUploadUtil.moveFile(projectsDTO.getStaticPath(), fileInfo, "projects");
			}
		}
		
			Set<ProjectBudget> projectBudget = storeProjectBudget(projectsDTO,user,projects);
			projects.setProjectBudget(projectBudget);
		
		
		if(!isNullOrEmpty(projectsDTO.getStatus())){
    	projects.setProjectStatus(Project_Status.getNameByValue((projectsDTO.getStatus())));
    	}
		if(StringUtils.isNotBlank(projectsDTO.getNotifyBudgetAt())) {
			projects.setNotifyBudgetAt(projectsDTO.getNotifyBudgetAt());
		}else {
			projects.setNotifyBudgetAt(null);
		}
		
		
		if(!isNullOrEmpty(projectsDTO.getProjectShortCode())){
			projects.setProjectShortCode(projectsDTO.getProjectShortCode());
		}else {
			projects.setProjectShortCode(null);
		}
		
    	projects.setCreatedDate(commonUtil.currentDate());
    	projects.setUpdatedDate(commonUtil.currentDate());
    	if(user.getUserCode() != null){
    		projects.setCreatedBy(user.getUserCode());	
    	}
    	
    	
    	
    	//save the default tasks
	    if(projects.getId() == null){
	    	Set<ProjectTasks>  projectTasksList = getDefaultTasksForProject(projects);
	    	Set<ProjectTasks> leaveTypeTasksList  = getLeaveTasksForProjectBasedOnOrgLocationId(projects,loginOrgLocation);
	    	if(leaveTypeTasksList != null && !leaveTypeTasksList.isEmpty()) {
	    		projectTasksList.addAll(leaveTypeTasksList);
	    	}
			projects.setProjectTasks(projectTasksList);
	    }

		
		projects.setProjectMode("Draft");
		projects.setIsProjectDetailsFilled(Constants.YES);
		projects.setIsProjectMembersFilled(Constants.NO);
		projects.setIsProjectAdditionalDetailsFilled(Constants.NO);
		
		if((projectsDTO.getSharedClientOrgLocId() != null && !projectsDTO.getSharedClientOrgLocId().isEmpty()) 
				|| (projectsDTO.getSavedSharedClientOrgLocId() != null && !projectsDTO.getSavedSharedClientOrgLocId().isEmpty())) {
			OrgLocations sharedClientOrgLocation = new OrgLocations();
			Long clientOrgLocationId = null;
			if(projectsDTO.getSharedClientOrgLocId() != null && !projectsDTO.getSharedClientOrgLocId().isEmpty()) {
				clientOrgLocationId = Long.valueOf(projectsDTO.getSharedClientOrgLocId());
			}else {
				clientOrgLocationId = Long.valueOf(projectsDTO.getSavedSharedClientOrgLocId());
			}
			sharedClientOrgLocation.setId(clientOrgLocationId);
			projects.setOrgLocationsId(sharedClientOrgLocation);
			projects.setSharedLocationId(loginOrgLocation);
			projects.setIsProjectShared(Constants.YES);
			projects.setIsClientProject(Constants.YES);
			OrgRelations orgRelations = null;
			orgRelations = orgRelationsService.getVendorOrgRelationByResponderLocationIdAndLocationId(loginOrgLocation.getId(), clientOrgLocationId);
			if(orgRelations == null) {
				orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(loginOrgLocation.getId(), clientOrgLocationId);
			}
			if(projects.getId() == null) {
				Set<ProjectClients> clientProjectClientsList = getProjectClientsForSharedClientProject(projectsDTO,projects,loginOrgLocation,clientOrgLocationId,user,orgRelations);
				projects.setSharedClientProjectClients(clientProjectClientsList);
			}
			
			Set<ProjectClients> projectClientsList = getProjectClientsForSharedProject(projectsDTO,projects,loginOrgLocation,user,orgRelations);
			projects.setProjectClients(projectClientsList);
			
		}else {
			projects.setOrgLocationsId(loginOrgLocation);
			projects.setIsClientProject(Constants.NO);
			projects.setIsProjectShared(Constants.NO);
			Set<ProjectClients> projectClientsList = getProjectClientsForProject(projectsDTO,projects,loginOrgLocation,user);
			projects.setProjectClients(projectClientsList);
		}
		
		
		
		
		Projects updatedProjects = saveProject(projects);
		
		
		if(projectsDTO.getCustomFormData() !=null) {
			for( CustomFormFieldDTO customFields:projectsDTO.getCustomFormData()) {
				if(customFields != null && customFields.getCustomFormFieldId() > 0) {
					ProjectCustomFieldData projectsCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						projectsCustomFields = projectCustomFieldDataService.getprojectFieldDataById(customFields.getEntityId());
					}else {
						projectsCustomFields = new ProjectCustomFieldData();
					}
				projectsCustomFields.setProjects(updatedProjects);
				OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
				projectsCustomFields.setOrglocationsFormFields(orgFormField);
				projectsCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
				projectCustomFieldDataService.saveProjectCustomFieldData(projectsCustomFields);
				}
				
			}	
		}
		
		/*
		 * Saving Project DB data as ElasticSearch Documents
		 */
		saveOrUpdateProjectElkDocument(updatedProjects);
		
	    return updatedProjects;
		 
		
	}
	
	private Set<ProjectDocumentFolder> storeDocumentFolder(Projects projects, User user) throws Exception {
		Set<ProjectDocumentFolder> projectDocumentFolderList = new HashSet<ProjectDocumentFolder>();
		ProjectDocumentFolder pdf = null;
		if(projects.getId() != null) {
			pdf = projectDocumentFolderService.getProjectDocumentFolderByProjectId(projects.getId());
		}
		if(pdf == null){
			pdf  = new ProjectDocumentFolder();
		}
		pdf.setProjectId(projects);
		pdf.setFoderName("Default Folder");
		pdf.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
		pdf.setCreatedBy(user);
		pdf.setCreatedDate(commonUtil.currentDate());
		projectDocumentFolderList.add(pdf);
		return projectDocumentFolderList;
	}

	private Set<ProjectBudget> storeProjectBudget(ProjectsDTO projectsDTO ,User user,Projects projects) throws  Exception {
		ProjectBudget projectBudget = null;
		HashSet<ProjectBudget> projectBudgetList = new HashSet<ProjectBudget>(); 
		if(projectsDTO.getEstimatedBudget() > 0 || projectsDTO.getEstimatedHours() > 0 || StringUtils.isNotBlank(projectsDTO.getBudgetApproverUser())) {
			if(projects.getId() != null) {
				projectBudget = projectBudgetService.getProjectBudgetsByProjectId(projects.getId());
			}
			 if(projectBudget == null) {
				    projectBudget = new ProjectBudget();
					projectBudget.setCreateUserId(user);
					projectBudget.setUpdateUserId(user);
					projectBudget.setCreatedDate(new Date());
					projectBudget.setBudgetRequestedId(generatebudgetRequestId(projects,projectsDTO));
			 }else {
				 projectBudget.setUpdatedDate(new Date());
				 projectBudget.setUpdateUserId(user);
			 }
			projectBudget.setProjectId(projects);
			projectBudget.setBudgetStatus(Constants.BUDGET_REQUEST_APPROVED);
			projectBudget.setEstimatedHours((double)projectsDTO.getEstimatedHours());
			projectBudget.setEstimatedBudgetCurrency(projectsDTO.getEstimatedBudgetCurrency());
			projectBudget.setStatus(Constants.RECORD_ACTIVE);
			projectBudget.setEstimatedBudgetValue((double)projectsDTO.getEstimatedBudget());
			projectBudget.setBudgetDescription(projectsDTO.getProjectName());
			if(StringUtils.isNotBlank(projectsDTO.getBudgetApproverUser())) {
				User use = userService.getUserById(Long.valueOf(projectsDTO.getBudgetApproverUser()));
				projectBudget.setApproverUserId(use);
			}else {
				projectBudget.setApproverUserId(null);
			}
			projectBudgetList.add(projectBudget);
		}else {
			if(projects.getId() != null) {
				 projectBudget = projectBudgetService.getProjectBudgetsByProjectId(projects.getId());
				 if(projectBudget != null) {
					 projectBudget.setEstimatedHours(null);
					 projectBudget.setEstimatedBudgetCurrency(projectsDTO.getEstimatedBudgetCurrency());
					 projectBudget.setEstimatedBudgetValue(null);
					 projectBudget.setStatus(Constants.RECORD_INACTIVE);
					 projectBudget.setApproverUserId(null);
					 projectBudgetList.add(projectBudget);
				 }
				 
			}
		}
		return projectBudgetList;
	}

	
	private String generatebudgetRequestId(Projects projects, ProjectsDTO projectsDTO) {
		String companyShortCode = null;
		String resultString = null;
       	Long number = 1L;
   		String candidateId = null;
   		Integer generatedIdcount = 0;
   		StringBuilder sbuilder = new StringBuilder();
   		if(projectsDTO.getProjectShortCode() != null && !projectsDTO.getProjectShortCode().isEmpty()) {
   			sbuilder.append(projectsDTO.getProjectShortCode()).append("BR");
   		}else {
   			sbuilder.append("BR");
   		}
   		
   		String generatedUsreId = sbuilder.toString();
   		for(int i=0;i< generatedUsreId.length();i++){
   			char ch = generatedUsreId.charAt(i);
   			if(Character.isAlphabetic(ch)){
   				generatedIdcount++;
   			}
   		}
   		// in dao to count from next character
   		generatedIdcount = generatedIdcount+1;
   		Long checkNumber = null;
   		if(projects.getId() != null) {
   			 checkNumber = projectBudgetService.getProjectBudgetReqestedIdByProjectId(projects.getId(),generatedIdcount);
   		}
   		 
   		if(checkNumber == null){
   			candidateId = number.toString();
   		}else{
   		Long canId = checkNumber+1;
   		candidateId = canId.toString();
   		}
   		final DecimalFormat decimalFormat = new DecimalFormat("0");                
           String convetredCandidateId = decimalFormat.format(Long.parseLong(candidateId));
           StringBuffer sb = new StringBuffer();
           if(projectsDTO.getProjectShortCode() != null && !"".equalsIgnoreCase(projectsDTO.getProjectShortCode())){
           	 companyShortCode = projectsDTO.getProjectShortCode();
           	 resultString = sb.append(companyShortCode).append("BR").append(convetredCandidateId).toString();
           }else {
        	   resultString = sb.append("BR").append(convetredCandidateId).toString();
           }
              
   		return resultString;
	}

	/*@Override
   	public String getCandidateId(OrgLocations orgLocations) {
       	String companyShortCode = null;
       	Long number = 1L;
   		String candidateId = null;
   		Integer generatedIdcount = 0;
   		StringBuilder sbuilder = new StringBuilder();
   		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.CANDIDATE_ENTITY);
   		String generatedUsreId = sbuilder.toString();
   		for(int i=0;i< generatedUsreId.length();i++){
   			char ch = generatedUsreId.charAt(i);
   			if(Character.isAlphabetic(ch)){
   				generatedIdcount++;
   			}
   		}
   		// in dao to count from next character
   		generatedIdcount = generatedIdcount+1;
   		 Long checkNumber = candidateDao.getCandidateGeneratedIdByOrgLocationId(orgLocations.getId(),generatedIdcount);
   		if(checkNumber == null){
   			candidateId = number.toString();
   		}else{
   		Long canId = checkNumber+1;
   		candidateId = canId.toString();
   		}
   		final DecimalFormat decimalFormat = new DecimalFormat("0");                
           String convetredCandidateId = decimalFormat.format(Long.parseLong(candidateId));
           StringBuffer sb = new StringBuffer();
           if(orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())){
           	 companyShortCode = orgLocations.getGeneratedUserId();
           }
   		return sb.append(companyShortCode).append(Constants.CANDIDATE_ENTITY).append(convetredCandidateId).toString();
   	}*/
    
	
	/**
	 * @author Laxman
	 * setting default tasks for every project
	 * @param projects
	 * @return set
	 * @throws Exception 
	 */
	private Set<ProjectTasks> getDefaultTasksForProject(Projects projects) throws Exception {
		Set<ProjectTasks> set = new HashSet<>();
		String[] defaultTasks = Constants.DEFAULT_TASKS;
		int generatedId = 1;
		for (String taskName : defaultTasks) {
			ProjectTasks projectTasks = new ProjectTasks();
			projectTasks.setTaskName(taskName);
			projectTasks.setDescription(taskName);
			if (!Constants.HOLIDAY.equalsIgnoreCase(taskName) && !Constants.COMPENSATORY_LEAVE.equalsIgnoreCase(taskName) && !Constants.WORK_FROM_HOME.equalsIgnoreCase(taskName) && !Constants.SYSTEM_TASK_BILLABE.equalsIgnoreCase(taskName)) {
				projectTasks.setBillableType(Constants.TASK_NON_BILLABLE);
			} else {
				projectTasks.setBillableType(Constants.TASK_BILLABLE);
			}
			LookUpData taskTypeLookUpData = new LookUpData();
			taskTypeLookUpData.setId(1060L);
			projectTasks.setTaskType(taskTypeLookUpData);
			
			LookUpData taskStatusLookUpData = new LookUpData();
			taskStatusLookUpData.setId(1069L);
			projectTasks.setTaskStatus(taskStatusLookUpData);
			projectTasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
			projectTasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			if(projects.getProjectShortCode() != null && !projects.getProjectShortCode().isEmpty()) {
				projectTasks.setGeneratedId(commonUtil.getProjectTaskGenaratedId(projects, generatedId));
			}
			projectTasks.setCreatedBy(projects.getCreatedBy());
			projectTasks.setCreatedDate(new Date());
			projectTasks.setPercentage(0);
			projectTasks.setProjectId(projects);
			set.add(projectTasks);
			generatedId++;
		}
		return set;
	}
	
	private Set<ProjectTasks> getLeaveTasksForProjectBasedOnOrgLocationId(Projects projects, OrgLocations loginOrgLocation) throws Exception {
		Set<ProjectTasks> set = new HashSet<>();			
		List<LeaveType> leaveTypeList  = leaveTypeService.getLeaveTypeListByLocationId(loginOrgLocation.getId().toString());
					for(LeaveType leaveType:leaveTypeList) {
						if(projects != null) {
							ProjectTasks  tasks  = new ProjectTasks();
							LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(Constants.GENERAL);
							tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
							tasks.setTaskName(leaveType.getName());
							tasks.setDescription(leaveType.getName());
							if(leaveType.getLeaveType().equalsIgnoreCase("Non Billable")) {
								tasks.setBillableType("Non-Billable");
							}else {
								tasks.setBillableType("Billable");
							}
							tasks.setProjectId(projects);
							tasks.setTaskType(taskType);
							tasks.setLeaveType(leaveType);
							LookUpData taskStatus = lookUpDataService.getTasksById(Constants.TaskStatusID);
							tasks.setTaskStatus(taskStatus);
							tasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
							tasks.setCreatedBy(leaveType.getCreatedBy());
							tasks.setCreatedDate(new Date());
							tasks.setUpdatedBy(leaveType.getCreatedBy());
							tasks.setUpdatedDate(new Date());
							set.add(tasks);
							}
						}
			return set;
	}
	
	
	public String getProjectTaskGenaratedId(Projects projects) throws Exception {
		Integer generatedIdcount = 0;
		Long number = 1L;
		String requirementId = null;
		 
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(projects.getProjectShortCode());
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Integer checkNumber = tasksDao.getProjectTasksCountByProjectId(projects.getId()); 
		if (checkNumber == null) {
			requirementId = number.toString();
		} else {
			Integer reqId = checkNumber + 1;
			requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
		
		return sbuilder.append(convetredRequirementsId).toString();
	}
	
	
	private Set<ProjectClients> getProjectClientsForSharedClientProject(ProjectsDTO projectsDTO,Projects projects,OrgLocations orglocations,Long clientOrgLocation,User user,OrgRelations orgRelations) throws Exception {
		Set<ProjectClients> projectClientsSet = new HashSet<>();
		ProjectClients projectClients = null;
		
			projectClients = new ProjectClients();
			projectClients.setProjectId(projects);
			OrgLocations clientOrgLoc = new OrgLocations();
			clientOrgLoc.setId(clientOrgLocation);
			projectClients.setOrgLocationsId(clientOrgLoc);
			projectClients.setOrgId(clientOrgLoc.getOrg());
			projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			projectClients.setLevel(1);
			projectClients.setStatus(1);
			projectClients.setIsProjectShared(Constants.NO);
			projectClients.setIsEndClient(Constants.YES);
			projectClients.setCreatedDate(new Date());
			projectClients.setUpdatedDate(new Date());
			projectClients.setCreatedBy(user);
			projectClients.setUpdatedBy(user);
	
		HashSet<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
		OrgSpoc orgSpoc = orgSpocDao.getGusetClientOrgSpocByOrgRelationId(orgRelations.getId());
		ProjectApproverHierarchy projectApproverHierarchy = new ProjectApproverHierarchy();
		projectApproverHierarchy.setProjectId(projects);
		projectApproverHierarchy.setProjectClientsId(projectClients);
		projectApproverHierarchy.setApproverUserId(orgSpoc.getClientUser());
		projectApproverHierarchy.setLevel(1);
		projectApproverHierarchy.setStatus("1");
		projectApproverHierarchyList.add(projectApproverHierarchy);
		//projectApproverHierarchy.setApprovalMechanismType(projectsDTO.getProjectApprovalTypeValue());
		projectClients.setSharedClientProjectApproverHierarchyList(projectApproverHierarchyList);
		
		
		HashSet<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList = new HashSet<ExpenseProjectApproverHierarchy>();
		ExpenseProjectApproverHierarchy expenseApproverHierarchy = new ExpenseProjectApproverHierarchy();
		expenseApproverHierarchy.setProjectId(projects);
		expenseApproverHierarchy.setProjectClientsId(projectClients);
		expenseApproverHierarchy.setApproverUserId(orgSpoc.getClientUser());
		expenseApproverHierarchy.setLevel(1);
		expenseApproverHierarchy.setStatus("1");
		//expenseApproverHierarchy.setApprovalMechanismType(projectsDTO.getExpenseApprovalTypeValue());
		expenseApproverHierarchyList.add(expenseApproverHierarchy);
		projectClients.setSharedClientProjectExpenseApproverHierarchyList(expenseApproverHierarchyList);
		
    	projectClientsSet.add(projectClients);
		return projectClientsSet;
	}
	
	
	private Set<ProjectClients> getProjectClientsForSharedProject(ProjectsDTO projectsDTO,Projects projects,OrgLocations orglocations,User user,OrgRelations orgRelations) throws Exception {
		Set<ProjectClients> projectClientsSet = new HashSet<>();
		ProjectClients projectClients = null;
		if(projects.getId() == null){
			projectClients = new ProjectClients();
			projectClients.setProjectId(projects);
			projectClients.setOrgLocationsId(orglocations);
			projectClients.setOrgId(orglocations.getOrg());
			projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			projectClients.setOrgRelationId(orgRelations);
			projectClients.setLevel(2);
			projectClients.setIsProjectShared(Constants.YES);
			projectClients.setStatus(1);
			projectClients.setIsEndClient(Constants.NO);
			projectClients.setCreatedDate(new Date());
			projectClients.setUpdatedDate(new Date());
			projectClients.setCreatedBy(user);
			projectClients.setUpdatedBy(user);
		}else{
			projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(projects.getId(), orglocations.getId(), orgRelations.getId());
		}
		
		//prepare ProjectApproverHierarchy
		Set<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.prepareProjectApproverHierarchyListByJson(projectsDTO,request,projects,projectClients);
		projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
		
		//prepare ProjectExpenseApproverHierarchy
    	Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList = projectApproverHierarchyService.prepareExpenseProjectApproverHierarchyList(projectsDTO,request,projects,projectClients);
    	projectClients.setExpenseProjectApproverHierarchyList(expenseApproverHierarchyList);
    	
    	
    	projectClientsSet.add(projectClients);
		return projectClientsSet;
	}
	
	private Set<ProjectClients> getProjectClientsForProject(ProjectsDTO projectsDTO,Projects projects,OrgLocations orglocations,User user) throws Exception {
		Set<ProjectClients> projectClientsSet = new HashSet<>();
		ProjectClients projectClients = null;
		if(projects.getId() == null){
			projectClients = new ProjectClients();
			projectClients.setProjectId(projects);
			projectClients.setOrgLocationsId(orglocations);
			projectClients.setOrgId(orglocations.getOrg());
			projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			projectClients.setLevel(1);
			projectClients.setStatus(1);
			projectClients.setIsEndClient(Constants.YES);
			projectClients.setCreatedDate(new Date());
			projectClients.setUpdatedDate(new Date());
			projectClients.setCreatedBy(user);
			projectClients.setUpdatedBy(user);
		}else{
			Set<ProjectClients> projectClientsList = projects.getProjectClients();
			if(projectClientsList != null){
				for(ProjectClients projClients : projectClientsList){
					if(projClients.getOrgLocationsId().getId().equals(orglocations.getId())){
						projectClients = projClients;
					}
				}
			}
		}
		
		//prepare ProjectApproverHierarchy
		Set<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.prepareProjectApproverHierarchyListByJson(projectsDTO,request,projects,projectClients);
		projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
    	
		//prepare ProjectExpenseApproverHierarchy
    	Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList = projectApproverHierarchyService.prepareExpenseProjectApproverHierarchyList(projectsDTO,request,projects,projectClients);
    	projectClients.setExpenseProjectApproverHierarchyList(expenseApproverHierarchyList);
    	
    	projectClientsSet.add(projectClients);
    	
		return projectClientsSet;
	}

	@Override
	public List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId, String clients,
			String status,Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectsBasedOnClientsAndStatusSelectionInFilters method...");
    	List<Projects> projectList = projectsDao.getProjectsBasedOnClientsAndStatusSelectionInFilters(userId,clients,status,orgLocationsId);
    	 OrgLocations orglo = orgLocationService.getOrgLocations(orgLocationsId);
         //SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
    	for (Projects projects :projectList ){
	    	    projects.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getStartDate()));
	    	    projects.setEmployeeStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getStartDate()));
			    if(projects.getEndDate() != null){
			    	 projects.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getEndDate()));
			    	 projects.setEmployeeEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getEndDate()));
			    }
			    
			    if(null != projects.getOrgRelationId()){
					if(null != projects.getOrgRelationId().getLocationId() && projects.getOrgRelationId().getLocationId().getId().longValue() == orgLocationsId.longValue()){
						projects.setClient(projects.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					}if(null != projects.getOrgRelationId().getResponderLocationId() && projects.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocationsId.longValue()){
						projects.setClient(projects.getOrgRelationId().getLocationId().getOrgLocationName());
					}
				}
			    
			    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyDao.getApproverUsersByProjectId(projects.getId());
			    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
			    	if(approverHierarchy.getLevel()==1){
			    		StringBuilder manager1 = new StringBuilder();
			    		if(approverHierarchy.getApproverUserId().getLastName() != null){
			    			projects.setManagerLevel1(manager1.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());	
			    		}else{
			    			projects.setManagerLevel1(manager1.append(approverHierarchy.getApproverUserId().getFirstName()).toString());
			    		}
			    		
			    	}else if(approverHierarchy.getLevel()==2){
			    		StringBuilder manager2 = new StringBuilder();
			    		if(approverHierarchy.getApproverUserId().getLastName() != null){
			    			projects.setManagerLevel2(manager2.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());	
			    		}else{
			    			projects.setManagerLevel2(manager2.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").toString());
			    		}
			    		
			    	}
			    }
	    }
		return projectList;
		
	}
	
	
	
	@Override
	public ResponseList<ProjectsDTO> getProjectsListByClientsAndStatus(ProjectsDTO projectsDTO,Long userId, String clients, String status,
			Long orgLocationsId,String userRole,Long candidateId) throws Exception {
		
		ResponseList<ProjectsDTO> responseProjectsList = projectsDao.getProjectsListByClientsAndStatus(projectsDTO,userId,clients,status,orgLocationsId,userRole,candidateId);
		return responseProjectsList;
	}


	@Override
	public List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId) {
		// TODO Auto-generated method stub
		return projectsDao.getProjectByOrgLocationsIdAndUserId(id,userId);
	}

	@Override
	public List<Projects> getProjectsByOrgLocationsIdAndApproverUserId(Long id, Long userId) {
		// TODO Auto-generated method stub
		return projectsDao.getProjectsByOrgLocationsIdAndApproverUserId(id,userId);
	}
	
	

	@Override
	public Projects getProjectByIdAndLoginOrgId(Long projectId, Long orgLocationId) throws Exception {
		// TODO Auto-generated method stub
		return projectsDao.getProjectByIdAndLoginOrgId(projectId, orgLocationId);
	}

	
	

    @Override
	public Integer getUserAssignedProjectsCount(Long userId, Long orgLocationId) {
		// TODO Auto-generated method stub
		return projectsDao.getUserAssignedProjectsCount(userId, orgLocationId);
	}

	/*@Override
	public HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception {
		
		return projectsDao.getAllUserTimeSheetSubmissionStatus();
	}*/

	@Override
	public void userEmailNotificationForTimeSheetSubmission(User user,Long locationsId,Map<String, String> timesheetFrequencyMap,Projects projects,String level) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
		UserRole userStatus = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(),locationsId);
		if("ACTIVE".equalsIgnoreCase(userStatus.getUserState().toString())) {
		Set<Projects> projectsSet = new HashSet<>();
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 
		 String fromEmail = null;
		 EmailConfigurationFromMail confFromMail=null;
		 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocations.getId());
		 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
			 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
				 if( fromMail.getEmailModules() != null){
					String[] jobIds = fromMail.getEmailModules().split(",");
						for(int j=0; j<jobIds.length; j++) {
							if(Long.valueOf(jobIds[j]) == 5L ){
								confFromMail=fromMail;
							}
						}
				 }
			 } 
			 
			 if(confFromMail == null){
				 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
				 if(confFromMail != null){
					 fromEmail = confFromMail.getEmail();
				 }
			 }else{
				 fromEmail = confFromMail.getEmail();
			 }
		 }
		 
		 if(StringUtils.isNotBlank(fromEmail)){
			 emailMessage.setFromEmail(fromEmail);
		 }
		 
		 
		 StringBuilder fromNameSB = new StringBuilder();
		 emailMessage.setFromName(orgLocations.getOrgName());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("FirstName", emailMessage.getFirstName());
		 globalVarsMap.put("LastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", "SourceLead");
		 globalVarsMap.put("administratorLastName","Admin");
		 globalVarsMap.put("role",user.getUserType());
		 globalVarsMap.put("UserEmailId",user.getEmail()); //
		 LinkedList<String> frequency = new LinkedList<>();
		 StringBuilder tempHTML = new StringBuilder();
		 tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
			        tempHTML.append("<tr>");
					tempHTML.append("<th> Project Name </th>");
					tempHTML.append("<th> Frequency </th>");
					tempHTML.append("</tr>");
		
			/*if(timesheetSubmissions != null && !timesheetSubmissions.isEmpty()){
				 for(TimesheetSubmissions ts :timesheetSubmissions){
					 tempHTML.append("<tr>");
					 tempHTML.append("<td>").append(ts.getProjectMembers().getProjects().getProjectName()).append("</td>").toString();
					 tempHTML.append("<td>").append(Constants.DMY.format(ts.getStartDate())).append(" To ").append(Constants.DMY.format(ts.getEndDate())).append("</td>").toString();
					 tempHTML.append("</tr>");
					 projectsSet.add(ts.getProjectMembers().getProjects());
				 }
			}*/
					
			if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
				for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
					    String key = entry.getKey();
					    String value =entry.getValue();
				    	 tempHTML.append("<tr>");
						 tempHTML.append("<td>").append(projects.getProjectName()).append("</td>").toString();
						 tempHTML.append("<td>").append(key.replaceAll("/", "-")).append(" To ").append(value.replaceAll("/", "-")).append("</td>").toString();
						 tempHTML.append("</tr>");
						 projectsSet.add(projects);
				}
			}
			
			
	     tempHTML.append("</table> ");
		 globalVarsMap.put("WeekFrequency",tempHTML.toString());
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(orgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
			   }
			  //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
				 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
				 String signature = null;
				 if(StringUtils.isNotBlank(emailScenario.getSignature())){
					 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
				 }
				 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		//configured Email
			
		 String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
		 if(StringUtils.isNotBlank(fromMail)){
			 emailMessage.setFromEmail(fromMail);
		 } 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationsId,iAdressArray,new ArrayList<EmailAttachment>());
		 sendWeeklyBaseNotiFication(user,orgLocations,projectsSet);
		}
	}


	private void sendWeeklyBaseNotiFicationToAdmin(User user, OrgLocations orgLocations) throws Exception {
		if(user.getId() !=1 && user.getId() !=2){
			List<Employees> empList = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(user.getId());
		    Employees emp = empList.get(0);
		   /* logger.info("Employee Location code :"+emp.getOrgLocations().getLocationCode());
			logger.info("Employee org code :"+emp.getOrgLocations().getOrg().getOrgCode());*/
		    User toUser = emp.getUser();
		    User fromUser = null;
		    if(user.getId() != orgLocations.getUser().getId()){
		    	  fromUser = orgLocations.getUser();
		    }else{
		    	fromUser = userService.getUserById(1L);
		    }
		    logger.info("Aprover for the Employee "+user.getFirstName()+" is :"+fromUser.getFirstName()+"("+fromUser.getUserType()+")");
		        EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
			    Map<String, String> globalVarsMap = new HashMap<String, String>();
			    globalVarsMap.put("firstName", user.getFirstName());
			    globalVarsMap.put("lastName",user.getLastName());
			    String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
				emailScenario.setBody(body);
				emailScenario.setOrgCode(emp.getOrgLocations().getOrg().getOrgCode());
				String actionLink = "";
				OrgLocations loginOrgLocation = new OrgLocations();
			   loginOrgLocation.setLocationCode(emp.getOrgLocations().getLocationCode());
			   loginOrgLocation.setOrg(emp.getOrgLocations().getOrg());
			   emailScenerioUtil.sendNotification(actionLink, fromUser, toUser, loginOrgLocation, emailScenario,new HashMap<String,String>());	
		}
	}
	

	private void sendWeeklyBaseNotiFication(User user, OrgLocations orgLocations, Set<Projects> projectsSet) throws Exception {
		if(user.getId().longValue() !=1l && user.getId().longValue() !=2l){
			List<Employees> empList = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(user.getId());
		    Employees emp = empList.get(0);
		    logger.info("Employee Location code :"+emp.getOrgLocations().getLocationCode());
			logger.info("Employee org code :"+emp.getOrgLocations().getOrg().getOrgCode());
		    User toUser = emp.getUser();
		    for(Projects projects :projectsSet){
		    	 User fromUser = null;
		    	List<ProjectApproverHierarchy> pahList = projectApproverHierarchyService.getApproverUsersByProjectId(projects.getId());
		    	//for(ProjectApproverHierarchy pah :pahList){
		    		//if(pah.getLevel() != null && pah.getLevel().intValue() == 1){
//		    	for(ProjectApproverHierarchy pah :pahList){
		    	ProjectApproverHierarchy pah = pahList.get(0);
		    		if(pah.getLevel() != null && pah.getLevel().intValue() == 1){
		    			fromUser = pah.getApproverUserId();
		    		}
		    		 logger.info("Aprover for the Employee "+user.getFirstName()+" is :"+fromUser.getFirstName()+"("+fromUser.getUserType()+")");
				        EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
					    Map<String, String> globalVarsMap = new HashMap<String, String>();
					    globalVarsMap.put("firstName", user.getFirstName());
					    globalVarsMap.put("lastName",user.getLastName());
					    String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
						emailScenario.setBody(body);
						emailScenario.setOrgCode(emp.getOrgLocations().getOrg().getOrgCode());
						String actionLink = "";
						OrgLocations loginOrgLocation = new OrgLocations();
					   loginOrgLocation.setLocationCode(emp.getOrgLocations().getLocationCode());
					   loginOrgLocation.setOrg(emp.getOrgLocations().getOrg());
					   emailScenerioUtil.sendNotification(actionLink, fromUser, toUser, loginOrgLocation, emailScenario,new HashMap<String,String>());
//		    	}
		    }
		}
	}
	

	@Override
	public void saveAllProjects(List<Projects> projectsList) throws Exception {
		// TODO Auto-generated method stub
		 projectsDao.saveAllProjects(projectsList);
	}

	/**
	 * get project details by project Id
	 * @author Laxman
	 * @param id
	 * @return Json
	 * @throws Exception
	 */
	
	@Override
	public ProjectsDTO getProjectDetailsByProjectIdAndLoginOrgId(Long projectId, User user, String loginOrgLocation,String isGlobalAccess)throws Exception {
		OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		
		Projects projects = getProjectById(projectId);
		ProjectsDTO projectsDTO = new ProjectsDTO();
		
		if(projects.getCreatedDate() != null){
			projectsDTO.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),projects.getCreatedDate()));	
		}
		projectsDTO.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),projects.getStartDate()));
	    if(projects.getEndDate() != null){
	    	projectsDTO.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),projects.getEndDate()));
	    }
	    if(projects.getUpdatedDate() != null){
	    	projectsDTO.setUpdatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),projects.getUpdatedDate()));
	    }
	    if(projects.getProjectSequeneNumber() != null){
	    	projectsDTO.setProjectSequeneNumber(projects.getProjectSequeneNumber());
	    }
	   
	    if(projects.getProjectShortCode() != null){
	    	projectsDTO.setProjectShortCode(projects.getProjectShortCode());
	    }
	    
	    if(projects.getProjectApprovalType() != null){
	    	projectsDTO.setProjectApprovalType(projects.getProjectApprovalType());
	    }
	    
	    if(projects.getExpenseApprovalType() != null){
	    	projectsDTO.setExpenseApprovalType(projects.getExpenseApprovalType());
	    }
	  
	    projectsDTO.setIsProjectForTimeSheets(projects.getIsProjectForTimeSheets());
	    projectsDTO.setIsProjectForExpenses(projects.getIsProjectForExpenses());
	    
	    projectsDTO.setIsReportingManagerAsApprover(projects.getIsReportingManagerAsApprover());
	    projectsDTO.setIsTaskCommentsRequired(projects.getIsTaskCommentsRequired());
	    projectsDTO.setIsTicketNumberRequired(projects.getIsTicketNumberRequired());
	    if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
	    	 ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), loginOrgLocations.getId());
	    	 if(pc != null && StringUtils.isNotBlank(pc.getIsProjectShared()) && pc.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
	    		 projectsDTO.setIsProjectShared(projects.getIsProjectShared());
		    	 projectsDTO.setSharedProjectCreatedOrgLocId(loginOrgLocations.getId());
	    	 }
	    	
	    }
	  
	    
	    User createdByUseraName= userService.getUserDetailsByUserCode(projects.getCreatedBy());
	    if(null !=createdByUseraName){
	    	StringBuilder userName = new StringBuilder();
	    	if(null != createdByUseraName.getLastName()){
	    		projectsDTO.setCreatedByName(userName.append(createdByUseraName.getFirstName()).append(" ").append(createdByUseraName.getLastName()).toString());	
	    	}else{
	    		projectsDTO.setCreatedByName(userName.append(createdByUseraName.getLastName()).toString());
	    	}
	    	
	    }
	    StringBuilder projectApprover = new StringBuilder();
	   
	   
	    List<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projects.getId(), loginOrgLocations.getId());
	    /**
	     * If the Project has Only Manager the His Level is taken as Higher Level  
	     */
	  
	    if(projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
		 	for(ProjectApproverHierarchy approverHierarchy : projectApproverHierarchies){
		    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 1 && (approverHierarchy.getStatus() == null || approverHierarchy.getStatus().equalsIgnoreCase("1"))){
		    		projectsDTO.setManager1(approverHierarchy.getApproverUserId().getId().intValue());
		    		projectsDTO.setManager1Name(approverHierarchy.getApproverUserId().getFullName());
		    		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(approverHierarchy.getApproverUserId().getId(),loginOrgLocations.getId());
		    		if(null != userRole){
		    			projectsDTO.setManagerLevel1UserType(userRole.getUserType());
		    		}else {
		    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(approverHierarchy.getApproverUserId().getId());
		    			projectsDTO.setManagerLevel1UserType(primaryRole.getUserType());
		    		}
		    		projectsDTO.setManagerLevel1(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
		    	}
		    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 2 && (approverHierarchy.getStatus() == null || approverHierarchy.getStatus().equalsIgnoreCase("1"))){
		    		projectsDTO.setManager2(approverHierarchy.getApproverUserId().getId().intValue());
		    		projectsDTO.setManager2Name(approverHierarchy.getApproverUserId().getFullName());
		    		projectsDTO.setManagerLevel2(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
		    	}
		    	
		    }
	    }
	    
	    
	    List<ExpenseProjectApproverHierarchy> expenseApproversList = expenseProjectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projects.getId(), loginOrgLocations.getId());
		 if(expenseApproversList != null && expenseApproversList.size() > 0) {
			 StringBuilder expenseApprover = new StringBuilder();
			 	for(ExpenseProjectApproverHierarchy expenseApproverHierarchy : expenseApproversList){
			    	if(expenseApproverHierarchy.getLevel() != null && expenseApproverHierarchy.getLevel() == 1 && (expenseApproverHierarchy.getStatus() == null || expenseApproverHierarchy.getStatus().equalsIgnoreCase("1"))){
			    		projectsDTO.setExpenseApprover1(expenseApproverHierarchy.getApproverUserId().getId().intValue());
			    		projectsDTO.setExpenseApprover1Name(expenseApproverHierarchy.getApproverUserId().getFullName());
			    		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(expenseApproverHierarchy.getApproverUserId().getId(),loginOrgLocations.getId());
			    		if(null != userRole){
			    			projectsDTO.setExpenseApproverLevel1UserType(userRole.getUserType());
			    		}else {
			    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(expenseApproverHierarchy.getApproverUserId().getId());
			    			projectsDTO.setManagerLevel1UserType(primaryRole.getUserType());
			    		}
			    		projectsDTO.setExpenseApproverLevel1(expenseApprover.append(expenseApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(expenseApproverHierarchy.getApproverUserId().getLastName()).toString());
			    	}
			    	if(expenseApproverHierarchy.getLevel() != null && expenseApproverHierarchy.getLevel() == 2 && (expenseApproverHierarchy.getStatus() == null || expenseApproverHierarchy.getStatus().equalsIgnoreCase("1"))){
			    		projectsDTO.setExpenseApprover2(expenseApproverHierarchy.getApproverUserId().getId().intValue());
			    		projectsDTO.setExpenseApprover2Name(expenseApproverHierarchy.getApproverUserId().getFullName());
			    		projectsDTO.setExpenseApproverLevel2(expenseApprover.append(expenseApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(expenseApproverHierarchy.getApproverUserId().getLastName()).toString());
			    	}
			    	
			    }
		    }
	    
	    //projects.setProjectApproverHierarchiesList(projectApproverHierarchies);
	    
	    List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
	    List<User> globalUserList = userService.getTabAccessGlobalOwnersByOrgIdAndTabId(loginOrgLocations.getId(),loginOrgLocations.getOrg().getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
		List<User> level1ApproversList = new CopyOnWriteArrayList<>();
		if(userList != null && !userList.isEmpty()) {
			level1ApproversList.addAll(userList);
		}
		if(globalUserList != null && !globalUserList.isEmpty()) {
			level1ApproversList.addAll(globalUserList);
		}
		HashMap<Long,User> userMap = new HashMap<>();
	    List<UserDTO> userDTOList = new ArrayList<>();
	    if(level1ApproversList != null && !level1ApproversList.isEmpty()) {
	    	for(User mrguser : level1ApproversList) {
	    		
	    		User tempUser = userMap.get(mrguser.getId());
				if(tempUser == null) {
					userMap.put(mrguser.getId(),mrguser);
					
					UserDTO userDTO = new UserDTO();
		    		userDTO.setId(mrguser.getId());
		    		UserRole roleType = userRoleService.getUserRoleByUserIdAndLocationId(mrguser.getId(),loginOrgLocations.getId());
		    		if(roleType != null) {
		    			userDTO.setUserType(roleType.getUserType());
		    		}else {
		    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(mrguser.getId());
		    			userDTO.setUserType(primaryRole.getUserType());
		    		}
		    		userDTO.setName(mrguser.getFullName());
		    		userDTOList.add(userDTO);
		    		
				}else {
					level1ApproversList.remove(mrguser);
					continue;
				}
				
	    		
	    	}
	    }
	    projectsDTO.setManagersList(userDTOList);
	    
	    if(projectsDTO.getManager1() != 0){
	    	  List<User> userListwithoutmaneger1 = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getManager1()),loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
	    	  List<User> globalUserListwithoutmaneger1 = userService.getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getManager1()),loginOrgLocations.getOrg().getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
	    	  List<User> managersList = new CopyOnWriteArrayList<>();
	    	  if(userListwithoutmaneger1 != null & !userListwithoutmaneger1.isEmpty()) {
	    		  managersList.addAll(userListwithoutmaneger1);
	    	  }
	    	  if(globalUserListwithoutmaneger1 != null & !globalUserListwithoutmaneger1.isEmpty()) {
	    		  managersList.addAll(globalUserListwithoutmaneger1);
	    	  }
	    	  
	    	  HashMap<Long,User> userMap2 = new HashMap<>();
	    	  List<UserDTO> mrguserDTOList = new ArrayList<>();
			    if(userList != null && managersList.size() > 0) {
			    	for(User mrguser2 : managersList) {
			    		
			    		User tempUser = userMap2.get(mrguser2.getId());
			    		if(tempUser == null) {
			    			userMap2.put(mrguser2.getId(),mrguser2);
			    	   		UserRole roleType = userRoleService.getUserRoleByUserIdAndLocationId(mrguser2.getId(),loginOrgLocations.getId());
				    		UserDTO userDTO = new UserDTO();
				    		userDTO.setId(mrguser2.getId());
				    		if(roleType != null) {
				    			userDTO.setUserType(roleType.getUserType());
				    		}else {
				    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(mrguser2.getId());
				    			userDTO.setUserType(primaryRole.getUserType());
				    		}
				    		
				    		userDTO.setName(mrguser2.getFullName());
				    		mrguserDTOList.add(userDTO);
			    		}else {
			    			mrguserDTOList.remove(mrguser2);
							continue;
			    		}
			    		
			 
			    	}
			    }
			    projectsDTO.setManagersListwithoutManager1(mrguserDTOList);
	    }
	    
	    
	    
	    if(projectsDTO.getExpenseApprover1() != 0){
	    	  List<User> userListwithoutmaneger1 = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getExpenseApprover1()),loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
	    	  List<User> globalUserListwithoutmaneger1 = userService.getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getExpenseApprover1()),loginOrgLocations.getOrg().getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
	    	  List<User> managersList = new CopyOnWriteArrayList<>();
	    	  if(userListwithoutmaneger1 != null & !userListwithoutmaneger1.isEmpty()) {
	    		  managersList.addAll(userListwithoutmaneger1);
	    	  }
	    	  if(globalUserListwithoutmaneger1 != null & !globalUserListwithoutmaneger1.isEmpty()) {
	    		  managersList.addAll(globalUserListwithoutmaneger1);
	    	  }
	    	  
	    	  HashMap<Long,User> userMap2 = new HashMap<>();
	    	  List<UserDTO> mrguserDTOList = new ArrayList<>();
			    if(userList != null && managersList.size() > 0) {
			    	for(User mrguser2 : managersList) {
			    		
			    		User tempUser = userMap2.get(mrguser2.getId());
			    		if(tempUser == null) {
			    			userMap2.put(mrguser2.getId(),mrguser2);
			    	   		UserRole roleType = userRoleService.getUserRoleByUserIdAndLocationId(mrguser2.getId(),loginOrgLocations.getId());
				    		UserDTO userDTO = new UserDTO();
				    		userDTO.setId(mrguser2.getId());
				    		if(roleType != null) {
				    			userDTO.setUserType(roleType.getUserType());
				    		}else {
				    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(mrguser2.getId());
				    			userDTO.setUserType(primaryRole.getUserType());
				    		}
				    		
				    		userDTO.setName(mrguser2.getFullName());
				    		mrguserDTOList.add(userDTO);
			    		}else {
			    			mrguserDTOList.remove(mrguser2);
							continue;
			    		}
			    		
			 
			    	}
			    }
			    projectsDTO.setExpensesApproversListwithoutApprover1(mrguserDTOList);
	    }
	    
	    
	    String[] approvers = new String[projectApproverHierarchies.size()];
	    int i = 0;
	    for(ProjectApproverHierarchy php : projectApproverHierarchies){
	    	StringBuilder approverHierarchies=new StringBuilder();
	    	approvers[i] = approverHierarchies.append(php.getApproverUserId().getFirstName()).append("&").append(php.getApproverUserId().getId()).toString();
	    	i++;
	    }
	    projectsDTO.setProjectManagerName(approvers);
	    
		
		if(projects.getIsTimesheetAttachmentRequired() != null) {
			projectsDTO.setIsTimesheetAttachmentRequired(projects.getIsTimesheetAttachmentRequired().toString());
		}
		
		if(projects.getAllowFreeLance() != null) {
			projectsDTO.setAllowFreeLance(projects.getAllowFreeLance().toString());
		}
		
		if(projects.getAllowRemote() != null) {
			projectsDTO.setAllowRemote(projects.getAllowRemote().toString());
		}
		
		if(projects.getAllowOvertime() != null) {
			projectsDTO.setAllowOvertime(projects.getAllowOvertime().toString());
		}
		
		if(projects.getAllowTimeEntryAfterDeadLine() != null) {
			projectsDTO.setAllowTimeEntryAfterDeadLine(projects.getAllowTimeEntryAfterDeadLine().toString());
		}
		
		if(projects.getAllowBidding() != null) {
			projectsDTO.setAllowBidding(projects.getAllowBidding().toString());
		}
		
		if(projects.getAllowOffShore() != null) {
			projectsDTO.setAllowOffShore(projects.getAllowOffShore().toString());
		}
		
		projectsDTO.setDescription(projects.getDescription());
		projectsDTO.setProjectSequeneNumber(projects.getProjectSequeneNumber());
		projectsDTO.setOvertimeRateType(projects.getOvertimeRateType());
		projectsDTO.setProjectStatus(projects.getProjectStatus().toString());
		projectsDTO.setProjectName(projects.getProjectName());
		projectsDTO.setOrgLocationId(projects.getOrgLocationsId().getId().intValue());
		projectsDTO.setTimeSheetFrequency(projects.getTimesheetFrequency());
		projectsDTO.setInvoiceFrequency(projects.getInvoiceFrequency());
		projectsDTO.setProjectUUID(projects.getProjectUUID());
		if(projects.getFlatPercent() != null) {
			projectsDTO.setFlatPercent(projects.getFlatPercent().toString());
		}
		
		 return projectsDTO;
	}

	 /**
	  * update the project by project ID
	  * @author Laxman
	  * @param projectId
	  * @param json
	  * @param request
	  * @return Projects
	  * @throws Exception
	  * 
	  * update by prasad salina here changed method return type String to Projects
	  */
	
	
	 private void sendProjectEditMailtoProjectManager(Projects projects, User loginUser, User approverUserId1, OrgLocations loginOrgLocation, String teamMembersList,String loginOrgCode,Date changedEndDate) throws Exception {
	  	 User projectManager = userService.getUserById(approverUserId1.getId());
	  	 
	  	 if(isNullOrEmpty(teamMembersList) && ("undefined".equals(teamMembersList))){
	  		teamMembersList = "-";
	  	 }
	  	 StringBuilder fromNameBuilder=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(projectManager.getFirstName());
		 emailMessage.setLastName(projectManager.getLastName());
		 emailMessage.setToEmail(projectManager.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 //SimpleDateFormat dateFormat=loginOrgLocation.getLocFullFormDateFormatter();
		 
		 projects.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),projects.getStartDate()));
		    if(changedEndDate != null){
		    	 projects.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),changedEndDate));
		    }
			
		    
		    
		 globalVarsMap.put("projectStartdate",projects.getStartDateStr());
		 globalVarsMap.put("projectEnddate",projects.getEndDateStr());
		 
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",loginUser.getFirstName());
			}
		 if(projectManager.getFirstName() != null && projectManager.getLastName()!=null && !"".equalsIgnoreCase(projectManager.getFirstName()) && !"".equalsIgnoreCase(projectManager.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("managerName",userBuild.append(projectManager.getFirstName()).append(" ").append(projectManager.getLastName()).toString());
			 }else{
				 globalVarsMap.put("managerName",projectManager.getFirstName());
			}
		 globalVarsMap.put("projectName", projects.getProjectName());
		 globalVarsMap.put("loginOrgName",loginOrgLocation.getOrgLocationName());
		 //calling the sendEmail method of emailScenerioUtil
		 if(null != projects.getOrgLocationsId())
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.EDITPROJECT_MAIL_MANAGER,new ArrayList<EmailAttachment>());
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario,new HashMap<String,String>());
	}
	 
	 
	 private void sendProjectEditMailtoTeamMembers(Projects projects, Employees emp, User loginUser, User approverUserId1, OrgLocations loginOrgLocation,String loginOrgCode, Date changedEndDate) throws Exception {
	  	 StringBuilder fromNameBuilder=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(emp.getUser().getFirstName());
		 emailMessage.setLastName(emp.getUser().getLastName());
		 emailMessage.setToEmail(emp.getUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 //SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		 
		 projects.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),projects.getStartDate()));
		    if(changedEndDate != null){
		    	 projects.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),changedEndDate));
		    }
			
		    
		    
		 globalVarsMap.put("projectStartdate",projects.getStartDateStr());
		 globalVarsMap.put("projectEnddate",projects.getEndDateStr());
		 
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",loginUser.getFirstName());
			}
		 if(emailMessage.getFirstName() != null && emailMessage.getLastName()!=null && !"".equalsIgnoreCase(emailMessage.getFirstName()) && !"".equalsIgnoreCase(emailMessage.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("employeeName",userBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
			 }else{
				 globalVarsMap.put("employeeName",emailMessage.getFirstName());
			}
		 globalVarsMap.put("projectName", projects.getProjectName());
		 globalVarsMap.put("loginOrgName",loginOrgLocation.getOrgLocationName());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 if(null != projects.getOrgLocationsId())
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.EDITPROJECT_MAIL_TEAMMEMBER,new ArrayList<EmailAttachment>());
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_TEAMMEMBER,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
	}
	
	@Override
	public Projects getProjectDetailsUpdateByProjectId(Long projectId, User user, ProjectsDTO projectDTO) throws Exception {
	 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
	 	String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	 	//SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		 Date currentDate = CalendarUtil.removeTime(new Date());
		 Date previousEndDate = null;
         Projects projects = getProjectById(projectId);
	        	 
               if(StringUtils.isNotBlank(projectDTO.getProjectSequeneNumber())){
			    	 projects.setProjectSequeneNumber(projectDTO.getProjectSequeneNumber());
			     }else {
			    	 projects.setProjectSequeneNumber(null); 
			     }
		        
		    	projects.setProjectName(projectDTO.getProjectName());
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getIsReportingManagerAsApprover())){
		    		projects.setIsReportingManagerAsApprover(Constants.YES);
		    	}else {
		    		projects.setIsReportingManagerAsApprover(Constants.NO);
		    	}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getProjectShortCode())){
					projects.setProjectShortCode(projectDTO.getProjectShortCode());
				}else {
					projects.setProjectShortCode(null);
				}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getProjectForValue())){	
		    		if(projectDTO.getProjectForValue().equalsIgnoreCase("Timesheets")) {
		    			projects.setIsProjectForTimeSheets(Constants.YES);
		    			projects.setIsProjectForExpenses(Constants.NO);
		    		}else if(projectDTO.getProjectForValue().equalsIgnoreCase("Expenses")) {
		    			projects.setIsProjectForExpenses(Constants.YES);
		    			projects.setIsProjectForTimeSheets(Constants.NO);
		    		}else {
		    			projects.setIsProjectForTimeSheets(Constants.YES);
		    			projects.setIsProjectForExpenses(Constants.YES);
		    		}
		    	}
		    	
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getIsTicketNumberRequired())){
		    		projects.setIsTicketNumberRequired(Constants.YES);
		    	}else {
		    		projects.setIsTicketNumberRequired(Constants.NO);
		    	}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getIsTaskCommentsRequired())){
		    		projects.setIsTaskCommentsRequired(Constants.YES);
		    	}else {
		    		projects.setIsTaskCommentsRequired(Constants.NO);
		    	}
		    	
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getProjectApprovalTypeValue())){
		    		projects.setProjectApprovalType(projectDTO.getProjectApprovalTypeValue());
		    	}else {
		    		projects.setProjectApprovalType(Constants.PARALLEL);
		    	}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getExpenseApprovalTypeValue())){
		    		projects.setExpenseApprovalType(projectDTO.getExpenseApprovalTypeValue());
		    	}else {
		    		projects.setExpenseApprovalType(Constants.PARALLEL);
		    	}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getDescription())){
		    		projects.setDescription(projectDTO.getDescription());
		    	}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getIsAllowFreelanceDevelopers())){
					projects.setAllowFreeLance(BooleanUtils.toBoolean(projectDTO.getIsAllowFreelanceDevelopers(),"Y",""));
				}else{
					projects.setAllowFreeLance(null);
				}
		    	
		    	if(StringUtils.isNotBlank(projectDTO.getIsAllowRemoteWork())){
					projects.setAllowRemote(BooleanUtils.toBoolean(projectDTO.getIsAllowRemoteWork(),"Y",""));
				}else{
					projects.setAllowRemote(null);
				}
				
				if(StringUtils.isNotBlank(projectDTO.getIsAllowOverTime())){
					projects.setAllowOvertime(BooleanUtils.toBoolean(projectDTO.getIsAllowOverTime(),"Y",""));
				}else{
					projects.setAllowOvertime(null);
				}
				if(StringUtils.isNotBlank(projectDTO.getIsAllowOffshoreResource())){
					projects.setAllowOffShore(BooleanUtils.toBoolean(projectDTO.getIsAllowOffshoreResource(),"Y",""));
				}else{
					projects.setAllowOffShore(null);
				}
				if(StringUtils.isNotBlank(projectDTO.getIsTimeEntryAfterDeadline())){
					projects.setAllowTimeEntryAfterDeadLine(BooleanUtils.toBoolean(projectDTO.getIsTimeEntryAfterDeadline(),"Y",""));
				}else{
					projects.setAllowTimeEntryAfterDeadLine(null);
				}
				if(StringUtils.isNotBlank(projectDTO.getIsAllowBidding())){
					projects.setAllowBidding(BooleanUtils.toBoolean(projectDTO.getIsAllowBidding(),"Y",""));
				}else{
					projects.setAllowBidding(null);
				}
				
				if(projectDTO.getFlatPercentage() != null && !projectDTO.getFlatPercentage().isEmpty()){
					projects.setFlatPercent(Double.parseDouble(projectDTO.getFlatPercentage()));
				}else{
					projects.setFlatPercent(null);
				}
				
				if(StringUtils.isNotBlank(projectDTO.getFlatPercentageId())){
					projects.setOvertimeRateType(projectDTO.getFlatPercentageId());
				}else{
					projects.setOvertimeRateType(null);
				}
				
					    	
		    	projects.setProjectStatus(Project_Status.getNameByValue(projectDTO.getStatus()));
		    	
		    			 //SimpleDateFormat format=loginOrgLocation.getLocFullFormDateFormatter();
//			    	     SimpleDateFormat format = new SimpleDateFormat(pattern);
		    	          if(StringUtils.isNotBlank(projectDTO.getEndDateStr1())){
					    		previousEndDate = projects.getEndDate();
					    		Date endDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectDTO.getEndDateStr1());
					    		//this method is used to send email for edit project end date
								
					    		 if(projects.getEndDate() != null ){		
					                  if(endDate.compareTo(projects.getEndDate()) != 0 ){
								    		sendProjectEditMailtoTeamMembersAndOwnersWhenProjectEndDateIsEdited(projectDTO,projects,user,loginOrgLocation,loginOrgCode,isGlobalAccess);

					                  }
					                  
					    		 } else if(StringUtils.isNotBlank(projectDTO.getEndDateStr1())){
							    		sendProjectEditMailtoTeamMembersAndOwnersWhenProjectEndDateIsEdited(projectDTO,projects,user,loginOrgLocation,loginOrgCode,isGlobalAccess);
					    		 }
								 projects.setEndDate(endDate);

					    	}else{
					    		Date endDate = null;
					    		projects.setEndDate(endDate);
					    	}
		    	
		    /*		if(jsonObj.has("clientId")){
			    		OrgRelations orgRelations = new OrgRelations();
			    		if(!jsonObj.getString("clientId").isEmpty()){
			    			orgRelations.setId(Long.valueOf(jsonObj.getString("clientId")));
					    	projects.setOrgRelationId(orgRelations);
			    		}
			    		
			    	}*/
		    		
		    		if(StringUtils.isNotEmpty(projectDTO.getIsTimesheetAttachmentRequired()) && projectDTO.getIsTimesheetAttachmentRequired().equalsIgnoreCase("Y")){
		    	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_ACTIVE);
		    	    }else{
		    	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_INACTIVE);
		    	    }	
		    		
		    	if(StringUtils.isNotBlank(projectDTO.getLevel1Manager())){
			    		HashSet<Long> oldlist = new HashSet<>();
			    		HashSet<Long> freshlist = new HashSet<>();
			    		HashSet<Long> templist = null;
			    		Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectId,loginOrgLocation.getId());
			    		ProjectClients pc = null;
			    		Boolean isLevel2ManagerExists = false;
			    		Long level1HierarchyId = null;
			    		Long level2HierarchyId = null;
			    		for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
				    			 if(prHierarchy.getLevel() == 1){
						    		 level1HierarchyId = prHierarchy.getId();
						    	 }else {
						    		 level2HierarchyId = prHierarchy.getId();
						    	 }
			    			     pc = prHierarchy.getProjectClientsId();
								 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
								 Long fieldValuelong = null;
								     String level1UserDetails[] = projectDTO.getLevel1Manager().split(",");
								     if(prHierarchy.getLevel() == 1 || (prHierarchy.getLevel() == 2 && projectApproverHierarchies.size() == 1))
								     {
								    	 fieldValuelong = Long.valueOf(level1UserDetails[0]);
								     }
								     if(prHierarchy.getLevel() == 2 &&  StringUtils.isNotBlank(projectDTO.getLevel2Manager())){
								    	 if(!"".equalsIgnoreCase(projectDTO.getLevel2Manager())){
								    		 isLevel2ManagerExists = true;
								    		 fieldValuelong = Long.valueOf(projectDTO.getLevel2Manager());
								    	 }
								    	 
								     }
								      if(prHierarchy.getLevel() == 2 && (StringUtils.isEmpty(projectDTO.getLevel2Manager()))){
								    	 oldlist.add(pah.getApproverUserId().getId());
								    	 pah.setStatus("0");
								    	// pah.setApprovalMechanismType(projectDTO.getProjectApprovalTypeValue());
								    	 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
								     }
								     
								     if(fieldValuelong != null){
								    	 User usr = userService.getUserById(fieldValuelong);
										 oldlist.add(pah.getApproverUserId().getId());
										 freshlist.add(usr.getId());
										 pah.setApproverUserId(usr);
										 pah.setStatus("1");
										// pah.setApprovalMechanismType(projectDTO.getProjectApprovalTypeValue());
										 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
								     }
								    
			    	    }
			    		
			    		// if   we  are adding new  level 2 manager while editing
			    		if(projectApproverHierarchies.size()== 1){ 	
							if(StringUtils.isNotBlank(projectDTO.getLevel2Manager())){
								Long[] ids = new Long[10];
								User approverUserId2 = new User();
								ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
								pah2.setProjectId(projects);
								approverUserId2.setId(Long.parseLong(projectDTO.getLevel2Manager()));
								pah2.setApproverUserId(approverUserId2);
								pah2.setLevel(2);
								pah2.setProjectClientsId(pc);
								pah2.setStatus("1");
								//pah2.setApprovalMechanismType(projectDTO.getProjectApprovalTypeValue());
								ids[1] = approverUserId2.getId();
								projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
								freshlist.add(Long.parseLong(projectDTO.getLevel2Manager()));
								
							}
						 }
			    		if(oldlist != null && !oldlist.isEmpty()){
			    			templist = (HashSet<Long>) oldlist.clone();
			    		}
			    		//getting projectmember userId's who are terminated or who are dropped from project
			    		oldlist.removeAll(freshlist);
			    		if(oldlist != null && !oldlist.isEmpty()){
			    			for(Long terminatedUserId : oldlist){
			    				if(projects != null && projects.getId() != null){
			    					ProjectMembers pm = projectMembersService.getProjectMemberByUserIdAndProjectId(terminatedUserId, projects.getId());
				    				if(null != pm && pm.getId() != null){
				    					ProjectMembersHistory projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
				    					if(projectMembersHistory != null){
				    						projectMembersHistory.setProjectTerminationDate(currentDate);
					    					projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
				    					}
					    				commonUtil.terminateProjectMemberByProjectMemberId(pm.getId(), Primary.Yes);
				    				}
				    				 
			    				}
			    			}
			    		}
			    		
			    		
			    		
			    		Collection<ExpenseProjectApproverHierarchy> expenseApproverHierarchiesList = expenseProjectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectId,loginOrgLocation.getId());
			    		for(ExpenseProjectApproverHierarchy expenseHierarchy : expenseApproverHierarchiesList) {
			    			if(expenseHierarchy.getLevel() == 1) {
			    				  String level1ApproverUserDetails[] = projectDTO.getLevel1ExpenseApprover().split(",");
			    				  User approverUser = new User();
			    				  approverUser.setId(Long.valueOf(level1ApproverUserDetails[0]));
			    				  expenseHierarchy.setApproverUserId(approverUser);
			    				//  expenseHierarchy.setApprovalMechanismType(projectDTO.getExpenseApprovalTypeValue());
			    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(expenseHierarchy);
			    			}
			    			
			    			  if(expenseHierarchy.getLevel() == 2){
			    				  
			    				  if((projectDTO.getLevel2ExpenseApprover() == null || projectDTO.getLevel2ExpenseApprover().isEmpty())) {
			    					  expenseHierarchy.setStatus("0");
			    					 // expenseHierarchy.setApprovalMechanismType(projectDTO.getExpenseApprovalTypeValue());
				    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(expenseHierarchy);
			    				  }else {
				    				  User approverUser2 = new User();
				    				  approverUser2.setId(Long.parseLong(projectDTO.getLevel2ExpenseApprover()));
				    				  expenseHierarchy.setApproverUserId(approverUser2);
				    				  expenseHierarchy.setStatus("1");
				    				//  expenseHierarchy.setApprovalMechanismType(projectDTO.getExpenseApprovalTypeValue());
				    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(expenseHierarchy);
				    			  }
			    				 
			    			  }
			    		}
			    		
			      		if(expenseApproverHierarchiesList != null && expenseApproverHierarchiesList.size() == 1 && (projectDTO.getLevel2ExpenseApprover() != null && !projectDTO.getLevel2ExpenseApprover().isEmpty()))	{
		    				ExpenseProjectApproverHierarchy eah2 = new ExpenseProjectApproverHierarchy();
		    				  User approverUser2 = new User();
		    				  approverUser2.setId(Long.parseLong(projectDTO.getLevel2ExpenseApprover()));
		    				  eah2.setApproverUserId(approverUser2);
		    				  eah2.setProjectId(projects);
		    				  ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), loginOrgLocation.getId());
		    				  eah2.setProjectClientsId(projectClients);
		    				  //eah2.setApprovalMechanismType(projectDTO.getExpenseApprovalTypeValue());
		    				  eah2.setStatus("1");
		    				  eah2.setLevel(2);
		    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(eah2);
		    		   }
			    		
			    	
		    		/*if(templist != null && !templist.isEmpty()){
			    			//getting projectmember userId's who are added to the project
			    			freshlist.removeAll(templist);
			    			if(freshlist != null && !freshlist.isEmpty()){
			    				for(Long freshlyaddedUserId : freshlist){
			    					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(freshlyaddedUserId,projects.getOrgLocationsId().getId());
			    					if(userRole != null && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)){
			    						storeProjectMemberByMemberUserIdAndLoginUser(freshlyaddedUserId, user, projects, currentDate);
			    					}
			    				}
			    			}
			    			
			    		}*/
			    		
			    		
			    		
			    		 /**
			    		  * Storing Submission Approver Hierarchy Records based on Changes Made Against Level1 and Level2 Approvers
			    		  */
			    		 LinkedList<SubmissionApproverHierarchy> saHierarchyList = new LinkedList<SubmissionApproverHierarchy>();
			    		 if(isLevel2ManagerExists){
			    			 List<SubmissionApproverHierarchy> sahList =  submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectApproverHierarchyId(level1HierarchyId);
				    	     if(sahList != null && !sahList.isEmpty()){
				    	    	 for(SubmissionApproverHierarchy sah :sahList){
				    	    		 SubmissionApproverHierarchy subSah =  submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectApproverHierarchyIdAndSubmissionId(level2HierarchyId, sah.getSubmissionId().getId());
				    	    		 if(subSah != null && (StringUtils.isEmpty(subSah.getStatus())  || (StringUtils.isNotEmpty(subSah.getStatus()) && subSah.getStatus().equalsIgnoreCase("0")))){
				    	    			    subSah.setSubmissionStatus(sah.getSubmissionStatus());
				    	    			    subSah.setBillingStatus(sah.getBillingStatus());
				    	    			    subSah.setInvoiceStatus(sah.getInvoiceStatus());
				    	    			    subSah.setSubmissionApprovalStatus(sah.getSubmissionApprovalStatus());
											subSah.setCreatedDate(commonUtil.currentDate());
											subSah.setCreatedBy(user.getUserCode());
											subSah.setUpdatedDate(commonUtil.currentDate());
											subSah.setUpdatedBy(user.getUserCode());
											subSah.setRejectedReason(sah.getRejectedReason());
											subSah.setActionPerformedBy(sah.getActionPerformedBy());
											subSah.setReopenedUser(sah.getReopenedUser());
											subSah.setReopenReason(sah.getReopenReason());
											subSah.setReopenRejectedReason(sah.getReopenRejectedReason());
											subSah.setStatus("1");
											saHierarchyList.add(subSah);
				    	    		 }
				    	    		 else{
				    	    			    SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
				    	    			    ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
				    	    			    pah.setId(level2HierarchyId);
											saHierarchy.setHeirarchyId(pah);
											saHierarchy.setSubmissionId(sah.getSubmissionId());
											saHierarchy.setSubmissionStatus(sah.getSubmissionStatus());
											saHierarchy.setSubmissionApprovalStatus(sah.getSubmissionApprovalStatus());
											saHierarchy.setFrequencyStartDate(sah.getFrequencyStartDate());
											saHierarchy.setFrequencyEndDate(sah.getFrequencyEndDate());
											saHierarchy.setCreatedDate(commonUtil.currentDate());
											saHierarchy.setCreatedBy(user.getUserCode());
											saHierarchy.setUpdatedDate(commonUtil.currentDate());
											saHierarchy.setBillingStatus(sah.getBillingStatus());
											saHierarchy.setInvoiceStatus(sah.getInvoiceStatus());
											saHierarchy.setRejectedReason(sah.getRejectedReason());
											saHierarchy.setActionPerformedBy(sah.getActionPerformedBy());
											saHierarchy.setReopenedUser(sah.getReopenedUser());
											saHierarchy.setReopenReason(sah.getReopenReason());
											saHierarchy.setReopenRejectedReason(sah.getReopenRejectedReason());
											saHierarchy.setUpdatedBy(user.getUserCode());
											saHierarchy.setStatus("1");
											saHierarchyList.add(saHierarchy);
				    	    		 }
				    	    	 }
				    	     }
						 }
			    		 else{
			    			if(level2HierarchyId != null) {
			    				 List<SubmissionApproverHierarchy> sahList =  submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectApproverHierarchyId(level2HierarchyId);
					    	     if(sahList != null && !sahList.isEmpty()){
					    	    	 for(SubmissionApproverHierarchy sah :sahList){
					    	    		 sah.setStatus("0");
										 saHierarchyList.add(sah);
					    	    	 }
					    	     }
			    			}
			    		
			    		 }
			    		 if(saHierarchyList != null && !saHierarchyList.isEmpty()){
			    			 for(SubmissionApproverHierarchy sah : saHierarchyList){
			    				 submissionApproverHierarchyService.updateSubmissionApproverHierarchy(sah);
			    			 }
			    			 
			    		 }
		    	}
			    projects.setUpdatedBy(user.getUserCode());
			    projects.setUpdatedDate(currentDate);
			    if(projects.getProjectStatus().equals(Project_Status.COMPLETED)){
			    	projects = commonUtil.terminateProject(projects);
			    	 updateProject(projects);
			    }
			    else if(projects.getEndDate() != null && currentDate.after(projects.getEndDate())) {
			    		projects = commonUtil.terminateProject(projects);
				    	updateProject(projects);
			    }
			    else{
			    	projects = updateProject(projects);
			    	if((projects.getEndDate() == null) || (previousEndDate == null) || (previousEndDate!= null && projects.getEndDate() != null && !projects.getEndDate().equals(previousEndDate) && projects.getEndDate().after(previousEndDate))){
			    		 List<ProjectMembers> pmList = projectMembersService.getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(projects.getId(), Primary.NO);
			    	     if(pmList != null && !pmList.isEmpty()){
			    	    	 for(ProjectMembers projectMember :pmList){
			    	    		 projectMember.setStatus(Constants.MEMBER_ACTIVE);
			    	    		 projectMember.setIsManuallyTerminated(null);
			    	    		 projectMember.setTerminateReason(null);
			    	    		 projectMember.setUpdatedDate(new Date());
			    	    		 projectMember.setUpdatedBy(user.getUserCode());
			    	    		 HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
							  	  ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMember.getId());
								  if(pmh != null){
									  pmh.setProjectTerminationDate(projects.getEndDate());
									  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
									  pmh.setUpdatedDate(new Date());
									  pmh.setUpdatedBy(user.getUserCode());
									  memberHistoryList.add(pmh);
									  projectMember.setMemberHistoryList(memberHistoryList);
								  }
								  projectMembersService.saveProjectMember(projectMember);
			    	    		/*if(projectMember.getEmployee()!= null && projectMember.getEmployee().getUser() != null){
			    	    			 commonUtil.storeTimesheetSubmissionRecords(projectMember.getEmployee().getUser());
			    	    		 }*/
			    	    	 }
			    	     }
			    	}
			    }
		 
			   
				/*
	    	     * Termination Of ProjectMember whose assignDate is greaterthan project endDate /
	    	     *   in-activate the submissions whose startDate is greaterthan project endDate
	    	     */
	    	    if(projects.getEndDate() != null) {
	    	    		commonUtil.terminateProjectMembersWhoseAssignedDateOrTerminatedisGreaterThanProjectEndDate(projects, user);
	    	    }
	    	    

	    	    /**
	    	     * To use onclick parameters for emp tab and task tab
	    	     */
	    	    projects.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),projects.getStartDate()));
			    if(projects.getEndDate() != null){
			    	 projects.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),projects.getEndDate()));
			    }
				
			    
			    if(projectDTO.getCustomFormData() !=null) {
					for( CustomFormFieldDTO customFields:projectDTO.getCustomFormData()) {
						if(customFields != null && customFields.getCustomFormFieldId() > 0) {
							ProjectCustomFieldData projectsCustomFields = null;
							if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
								projectsCustomFields = projectCustomFieldDataService.getprojectFieldDataById(customFields.getEntityId());
							}else {
								projectsCustomFields = new ProjectCustomFieldData();
							}
						projectsCustomFields.setProjects(projects);
						OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
						projectsCustomFields.setOrglocationsFormFields(orgFormField);
						projectsCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
						projectCustomFieldDataService.saveProjectCustomFieldData(projectsCustomFields);
						}
						
					}	
				}
			    
			    
			  /*  Set<ProjectMembers>  projectMemberList = projectMembersService.prepareProjectMembersListByJson(jsonObj,request, projects);
				projects.setProjectMembers(projectMemberList);*/
				 /*
	    		 * Updating Project data as ElasticSearch Documents
	    		 */
			    
			    if(projectDTO.getClientlocationId() != null && !projectDTO.getClientlocationId().isEmpty()) {
			    	
			    	String[] clientDetails = projectDTO.getClientlocationId().split(",");
			    	makeInternalProjectAsClientProject(projects, Long.parseLong(clientDetails[0]),Long.parseLong(clientDetails[1]),loginOrgLocation);
			    }
			    
	    		saveOrUpdateProjectElkDocument(projects);

	    		
	    	
		 return projects;

	}
	
	 void  sendProjectEditMailtoTeamMembersAndOwnersWhenProjectEndDateIsEdited(ProjectsDTO projectDTO ,Projects projects,User user, OrgLocations loginOrgLocation,String loginOrgCode,String isGlobalAccess){
		 

		 try{
			 
			//  This is to send mail to managers TeamMembers when project end Date is Edited
			  Date previousEndDate = null;
		    		/*String pattern = "dd-MM-yyyy";
		    	     SimpleDateFormat format = new SimpleDateFormat(pattern);*/
		    	     if(StringUtils.isNotBlank(projectDTO.getEndDateStr1()) && !"NO DATA".equalsIgnoreCase(projectDTO.getEndDateStr1())){
				    		previousEndDate = projects.getEndDate();
				    		Date changedEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectDTO.getEndDateStr1());
							 
				    		if(projects.getEndDate() != null ){
				    			projects.getEndDate();
				    		}
				    		
	                 	    User  approverUserId2  = null;
	         				User  approverUserId1 = null;
	         				if(StringUtils.isNotBlank(projectDTO.getLevel1Manager())){
	         					  String level1UserDetails[] = projectDTO.getLevel1Manager().split(",");
	         					  approverUserId1 =   userService.getUserById(Long.valueOf(level1UserDetails[0]));
	         				if(StringUtils.isNotBlank(projectDTO.getLevel2Manager())){
	         			      approverUserId2 =   userService.getUserById(Long.valueOf(projectDTO.getLevel2Manager()));
	         				}
	         				
	         			    Collection<ProjectMembers>  projectMemberList = projectMembersService.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projects.getId(),loginOrgLocation.getId(),isGlobalAccess) ;

	         			    if(projectMemberList != null && projectMemberList.size() >0){
	         			    	for(ProjectMembers projectMembers : projectMemberList){
		         					if(null != projectMembers.getEmployee()){
		         						Employees emp = projectMembers.getEmployee();
		         							
		         						//if  we have   both level1 and Level2 ProjectManagers
		         						if( null != approverUserId1 && null != approverUserId2){
		         							  if(approverUserId1.getId().longValue() != emp.getUser().getId().longValue() && approverUserId2.getId().longValue() != emp.getUser().getId().longValue()){
		         								  if(user.getId().longValue() != emp.getUser().getId().longValue()){
		         									  sendProjectEditMailtoTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation,loginOrgCode, changedEndDate);
		         								  }
		         					    }
		         							//if  we have   both level1 only One ProjectManagers
		         						}else if(approverUserId1.getId().longValue() != emp.getUser().getId().longValue()){
		         							 if(user.getId().longValue() != emp.getUser().getId().longValue()){
		         								 sendProjectEditMailtoTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation,loginOrgCode, changedEndDate);
		         							 }
		         							 
		         							 }   
		         							
		         						}
		         					}
	         			      }
	         				}
	         					if(StringUtils.isNotBlank(projectDTO.getLevel1Manager())){
	         						User  approverUserId111 =new User();
	         						String level1UserDetails[] = projectDTO.getLevel1Manager().split(",");
	         						approverUserId111.setId(Long.valueOf(level1UserDetails[0]));
	         						if(approverUserId111 != null && user.getId() != approverUserId111.getId()){
	         							sendProjectEditMailtoProjectManager(projects, user, approverUserId111, loginOrgLocation,"",loginOrgCode,changedEndDate);
	         						}
	         						
	         					}
	         					

	         					/*
	         					 * This is Mailing for Level2 Manager who was assigned to Project
	         					 * 
	         					 */
	         					
	         					if(StringUtils.isNotBlank(projectDTO.getLevel2Manager())){
	         						User  approverUserId22 =new User();
	         						approverUserId22.setId(Long.valueOf(projectDTO.getLevel2Manager()));
	         						if(null !=  approverUserId22 && user.getId() != approverUserId22.getId()){
	         							 if(user.getId().longValue() != approverUserId22.getId().longValue()){
	         								 	sendProjectEditMailtoProjectManager(projects, user, approverUserId22, loginOrgLocation,"",loginOrgCode,changedEndDate);
	         							 }
	         						 }
	         					}
	         				
	                
				    	}else{
				    		Date endDate = null;
				    		projects.setEndDate(endDate);
				    	}
		    	
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
			
	   }

	 /**
	  * Save Employee from project summary
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	
	@Override
	public String storeEmployeeInProjectDashboard(OrgLocations loginOrgLocation, User users, JSONObject jsonObj)
			throws Exception {
		// User saving
	 	String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		User user = populateUser(jsonObj,users,password);
		User  usr=	userService.addUser(user);
		
		//Employee Saving
		Employees employee = populateEMployee(user,loginOrgLocation,jsonObj,users);
		Employees  emp=	employeesService.addEmployee(employee);
		
		// UserRoles saving
		UserRole userRole = populateUserRole(usr,loginOrgLocation,users);
 		userRoleService.addUserRole(userRole);
 		
		//send mail to user
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){

			userService.sendEmail(user,users,password,loginOrgLocation.getId());

		}
		userService.saveOrUpdateUserElkDocument(userRole,emp,users.getUserCode());

		return null;
	}

	/**
	 * populate User Data
	 * @param jsonObj
	 * @param users
	 * @param password
	 * @return User
	 * @throws JSONException
	 */
	private User populateUser(JSONObject jsonObj, User users, String password) throws JSONException {
		User user= new User();
		user.setFirstName(jsonObj.getString("employeeFirstName"));
		user.setLastName(jsonObj.getString("employeeLastName"));
		user.setEmail(jsonObj.getString("EmailId"));
		user.setCreatedDate(new Date());
		user.setCreatedBy(users.getUserCode());
		user.setUserName(jsonObj.getString("EmailId"));
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			user.setIsMailSent("Y");
		}else{
			user.setIsMailSent("N");
		}
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		return user;
		
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	private ProjectMembers storeProjectMemberByMemberUserIdAndLoginUser(Long memberUserId,User loginUser,Projects projects,Date currentDate) throws Exception{
		
		Employees emp = employeesService.getEmployeeByUserId(memberUserId);
		
		ProjectMembers pm = projectMembersService.getProjectMemberByEmpIdAndProjectId(emp.getId(), projects.getId());
		HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
		if(pm == null){
			pm = new ProjectMembers();
			ProjectMembersHistory pmh = new ProjectMembersHistory();
			pmh.setProjectMemberId(pm);
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
			pmh.setProjectAssignedDate(currentDate);
			pmh.setProjectTerminationDate(projects.getEndDate());
	 	    pmh.setCreatedDate(currentDate);
			pmh.setCreatedBy(loginUser.getUserCode());
			pmh.setUpdatedDate(currentDate);
			pmh.setUpdatedBy(loginUser.getUserCode());
			pmhList.add(pmh);
	    	pm.setProjects(projects);
	    	pm.setEmployee(emp);
			pm.setStatus(Constants.MEMBER_ACTIVE);
			pm.setCreatedDate(currentDate);
			pm.setUpdatedDate(currentDate);
			pm.setCreatedBy(loginUser.getUserCode());
			pm.setUpdatedBy(loginUser.getUserCode());
			pm.setMemberHistoryList(pmhList);
			pm = projectMembersService.saveProjectMember(pm);
		}else{
			pm.setStatus(Constants.MEMBER_ACTIVE);
			pm.setCreatedDate(currentDate);
			pm.setUpdatedDate(currentDate);
			pm.setCreatedBy(loginUser.getUserCode());
			pm.setUpdatedBy(loginUser.getUserCode());
			pm.setMemberHistoryList(pmhList);
			pm = projectMembersService.updateProjectMember(pm);
		}
		
		
		return pm;
	}
	
	
	/**
	 * populate Employees Data	
	 * @param user
	 * @param loginOrgLocation
	 * @param jsonObj
	 * @param users
	 * @return Employees
	 * @throws JSONException
	 */
	private Employees populateEMployee(User user, OrgLocations loginOrgLocation, JSONObject jsonObj, User users) throws JSONException {
		Employees employee  = new Employees();
		employee.setUser(user);
		/*if(!jsonObj.getString("invoiceFrequency").isEmpty()){
			employee.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		}*/
		employee.setOrgLocations(loginOrgLocation);
		employee.setCreatedBy(users.getUserCode());
		employee.setStatus("ACTIVE");
		employee.setCreatedDate(new Date());
		return employee;
	}
		
	/**
	 * 	populate UserRole Data
	 * @param usr
	 * @param loginOrgLocation
	 * @param users
	 * @return
	 * @throws Exception
	 */
		private UserRole populateUserRole(User usr, OrgLocations loginOrgLocation, User users) throws Exception {
			String roleName = Constants.ROLE_USER;
			Roles roles = rolesService.getRolesByRoleName(roleName);
			UserRole userRole = new UserRole();
			userRole.setRoles(roles);
			userRole.setUser(usr);
			userRole.setUserType("Employee");
			userRole.setUserState(Status.NOT_LOGGEDIN);
			userRole.setOrgLocations(loginOrgLocation);
			userRole.setIsPrimary(1);
			userRole.setCreatedBy(users.getUserCode());
			userRole.setCreatedDate(new Date());
			return userRole;	
		}
			
	/**
	 * Checks String empty or null
	 * @param string
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * Get Employees list based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	
	@Override
	public ProjectMembers getProjectEmployeeListByProjectId(Long projectId) throws Exception {
		
		ProjectMembers myPM =  new ProjectMembers();
		
		
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
		Projects pr = projectsDao.getProjectById(projectId);
		
		//SimpleDateFormat dateFormat=pr.getOrgLocationsId().getLocFullFormDateFormatter();
		ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		 for(ProjectMembers pm : projectMembersList){
		
			 pm.setProjectStatus(pr.getProjectStatus().getValue());
			 
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(pm.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(commonUtil.dateStrFromOrgDateFormatter(pr.getOrgLocationsId().getDateFormat(),pmh.getProjectAssignedDate()));
            }
	       	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
	       		pm.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(pr.getOrgLocationsId().getDateFormat(),pmh.getProjectTerminationDate()));
	       	 }
	       	 //to set actual project end date
	       	 if(pm.getProjects().getEndDate() != null){
	        		pm.setEndDate(commonUtil.dateStrFromOrgDateFormatter(pr.getOrgLocationsId().getDateFormat(),pm.getProjects().getEndDate()));
	        	 }
	       	 if(pm.getEmployee() != null && pm.getEmployee().getUser() != null && pm.getEmployee().getUser().getId() != null){
	          		pm.setUserId(pm.getEmployee().getUser().getId().toString());
	          	 }
		      if(pm.getTerminateReason() != null){
		    	  pm.setTerminateReason(pm.getTerminateReason());
		      }
	       	 
	       	 if(pm.getEmployee()!=null){
	
	                Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "DRAFT");
	    	      //  Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
	    	      //  Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
	    	        pm.setDraftHrs(draftTimesheetsSubmissionsCountAsEmployee.toString());
	
	    	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "SUBMITTED");
	    	       // Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
	    	       // Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
	    	        pm.setSubmittedHrs(submittedTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "APPROVED");
	    		   // Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
	    	       // Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
	    	        pm.setApprovedHrs(approvalTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "REJECTED");
	    		   // Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
	    		    //Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
	    		    pm.setRejectedHrs(rejectedTimesheetsSubmissionsCountAsEmployee.toString());
	                
	                
	       	 }	
			 projectMembersArrList.add(pm);
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonInString = mapper.writeValueAsString(projectMembersArrList);
		 
		 
		 myPM.setProjectMembersList(projectMembersArrList);
		 if(pr.getProjectStatus() != null){
			 myPM.setStatus(pr.getProjectStatus().getValue());	 
		 }
		 
		 
		 return myPM;
	}
	
	
	@Override
	public List<ProjectMembers> getProjectEmployeeListByProjectIdAndStatus(Long projectId,String status,OrgLocations loginOrgLocation,String roleIds,String teamIds,String isGlobalAccess) throws Exception {
		
		List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectIdAndStatus(projectId,status,loginOrgLocation,roleIds,teamIds,isGlobalAccess);
		Projects pr = projectsDao.getProjectById(projectId);
		
		ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		if(projectMembersList != null && projectMembersList.size() > 0){ 
		for(ProjectMembers pm : projectMembersList){
			 if(pm.getCandidateId() != null){
				 StringBuilder membersListStringBuilder=new StringBuilder();
				 if(pm.getProjectMemberCandidteId().getLastName() != null) {
					 pm.setEmpName(membersListStringBuilder.append(pm.getProjectMemberCandidteId().getFirstName()).append(" ").append(pm.getProjectMemberCandidteId().getLastName()).toString());	 
				 }else {
					 pm.setEmpName(membersListStringBuilder.append(pm.getProjectMemberCandidteId().getFirstName()).toString());	 
				 }
				 if(pm.getProjectMemberCandidteId().getEmail() != null) {
					 pm.setEmpEmail(pm.getProjectMemberCandidteId().getEmail());
				 }
				 if(pm.getProjectMemberCandidteId().getConsultantUser() != null){
					 UserRole userRole = null;
					 if(pm.getProjects().getOrgLocationsId().getId().compareTo(loginOrgLocation.getId()) != 1) {
						 userRole = userRoleService.getUserRoleByUserIdAndLocationId(pm.getProjectMemberCandidteId().getConsultantUser().getId(), pm.getProjects().getOrgLocationsId().getId());
					 }else {
						 userRole = userRoleService.getUserRoleByUserIdAndLocationId(pm.getProjectMemberCandidteId().getConsultantUser().getId(), loginOrgLocation.getId());
					 }
					 
					 if(userRole != null){
						 pm.setUserRoleType(userRole.getUserType());
						 pm.setUserRoleId(userRole.getId());
						 pm.setProjectMemberStatus(userRole.getUserState().name());
					 } 
				 }else{
					 pm.setUserRoleType("Candidate");
				 }
				
					
			 }
			 pm.setProjectStatus(pr.getProjectStatus().getValue());
			 
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(pm.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),pmh.getProjectAssignedDate()));
            }
	       	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
	       		pm.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),pmh.getProjectTerminationDate()));
	       	 }
	       	 //to set actual project end date
	       	 if(pm.getProjects().getEndDate() != null){
	        		pm.setEndDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),pm.getProjects().getEndDate()));
	        	 }
	       	 if(pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getConsultantUser() != null && pm.getProjectMemberCandidteId().getConsultantUser().getId() != null){
	          		pm.setUserId(pm.getProjectMemberCandidteId().getConsultantUser().getId().toString());
	          	 }
		      if(pm.getTerminateReason() != null){
		    	  pm.setTerminateReason(pm.getTerminateReason());
		      }
	       	 
		      if(pm.getPercentageOfWork() != null) {
		    	  pm.setPercentageOfWork(pm.getPercentageOfWork());
		      }
		      
	       	 if(pm.getCandidateId() != null){
	
	                Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "DRAFT");
	    	        pm.setDraftHrs(draftTimesheetsSubmissionsCountAsEmployee.toString());
	
	    	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "SUBMITTED");
	    	        pm.setSubmittedHrs(submittedTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "APPROVED");
	    	        pm.setApprovedHrs(approvalTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(pm.getMasterCandidateId().getId(), projectId, "REJECTED");
	    		    pm.setRejectedHrs(rejectedTimesheetsSubmissionsCountAsEmployee.toString());
	                
	                
	       	 }	
	       	 
	       	 
	       	 List<ProjectMembersRateHistory> pmrhList = projectMembersRateHistoryService.getProjectMembersRateHistoryListByProjectMemberId(pm.getId());
	       	 if(pmrhList != null && !pmrhList.isEmpty()) {
	       		Date date = commonUtil.orgLocationCommonDate(new Date(), loginOrgLocation);
	       		for(ProjectMembersRateHistory pmrh : pmrhList) {
	       			if(pmrh.getPayRateEndDate() != null && date.before((pmrh.getPayRateEndDate()))) {
	       				pm.setPayRate(pmrh.getPayRate());
	       				pm.setPayRateCurrency(pmrh.getPayRateCurrency());
	       				pm.setPayRateType(pmrh.getPayRateType());
	       				
	       				break;
	       			}
	       		}
	       		
	       		
	       		for(ProjectMembersRateHistory pmrh : pmrhList) {
	       			if(pmrh.getBillRateEndDate() != null && date.before((pmrh.getBillRateEndDate()))) {
	       				pm.setCurrentBillRate(pmrh.getBillRate());
	       				pm.setBillRateCurrency(pmrh.getBillRateCurrency());
	       				pm.setRateType(pmrh.getBillRateType());
	       				
	       				break;
	       			}
	       		}
	       		 
	       		pm.setRateHistroyList(Constants.YES);
	       	 }else {
	       		pm.setRateHistroyList(Constants.NO);
	       	 }
			 projectMembersArrList.add(pm);
		 }
		}
		 return projectMembersList;
	}

/**
 * prepares email boday with table 
 */
@Override
public void userEmailToAdmin(StringBuilder tempHTML, OrgLocations orgLocations,User user1) throws Exception {
	     User user = null;
	     
	     if(user1!=null){
	    	 user = user1;
	     }else{
	    	 user = orgLocations.getUser();
	     }
	     if(user != null){
	    	 UserRole userStatus = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(),orgLocations.getId());
	    	 if("ACTIVE".equalsIgnoreCase(userStatus.getUserState().toString())){
	    	 StringBuilder fNameStringBuilder=new StringBuilder();
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setFromName(Constants.SOURCELEAD_ADMIN_UNIQUE);
			// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("FirstName", emailMessage.getFirstName());
			 globalVarsMap.put("LastName",emailMessage.getLastName());
			 globalVarsMap.put("administratorFirstName", user.getFirstName());
			 globalVarsMap.put("administratorLastName",user.getLastName());
			 globalVarsMap.put("role",user.getUserType());
			 globalVarsMap.put("UserEmailId",user.getEmail()); //
			 globalVarsMap.put("WeekFrequency",tempHTML.toString());
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if(orgLocations != null)
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.TIMESHEET_MANAGER_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_MANAGER_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
				   }
				  //covert subject,body and signature
					 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
					 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
					 String signature = null;
					 if(StringUtils.isNotBlank(emailScenario.getSignature())){
						 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
					 }
					 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
			 if(StringUtils.isNotBlank(fromMail)){
				 emailMessage.setFromEmail(fromMail);
			 } 
			 
			 //calling the sendEmail method of emailScenerioUtil
			emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			if(orgLocations != null){
				sendWeeklyBaseNotiFicationToAdmin(user,orgLocations);	
			}
			 
	     }
	     }
		
}


			@Override
			public Integer getProjectApproversCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
					throws Exception {
				return projectsDao.getProjectApproversCountByUserIdAndOrgLocationId(userId, orgLocationId);
			}

			
			
			@Override
			public void tereminateAsProjectApprover(Long approverUser,User user,Long userId,OrgLocations orgLocations) throws Exception {
				
				   boolean flag = false;
		    		List<ProjectApproverHierarchy> projectApproverList = projectApproverHierarchyService.getProjectApproversListByUserIdAndOrgLocationId(userId,orgLocations.getId());
		    		Integer projectApproverCount = getProjectApproversCountByUserIdAndOrgLocationId(userId,orgLocations.getId());
		    		for(ProjectApproverHierarchy hierarchy : projectApproverList){
		    			User usr = userService.getUserById(Long.valueOf(approverUser));
		    			User terminatedUser = hierarchy.getApproverUserId();
							ProjectApproverHierarchy pah = projectApproverHierarchyService.getProjectApproverByUserIdAndProjectIdOrgLocationId(Long.valueOf(approverUser), hierarchy.getProjectId().getId(), orgLocations.getId());
							if(pah != null) {
								if(pah.getLevel() == 2) {
									pah.setStatus(String.valueOf(Constants.RECORD_INACTIVE));
									projectApproverHierarchyService.saveProjectApproverHierarchy(pah);
									hierarchy.setApproverUserId(usr);
									hierarchy.setUpdatedBy(user.getUserCode());
									hierarchy.setUpdatedDate(new Date());
								}else {
									hierarchy.setStatus(String.valueOf(Constants.RECORD_INACTIVE));
								}
								
							}else {
								hierarchy.setApproverUserId(usr);
								hierarchy.setUpdatedBy(user.getUserCode());
								hierarchy.setUpdatedDate(new Date());
							}
							
							
							/*List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(hierarchy.getProjectId().getId(), orgLocations.getId());	
							for(ProjectApproverHierarchy approverHierarchy : hierarchyList){
								if(approverHierarchy.getApproverUserId().getId().equals(usr.getId())){
									flag = true;
								}
							}*/
							
							/*if(flag == false){
								
					    			//getting projectmember userId's who are added to the project
					    					ProjectMembers pm = storeProjectMemberByMemberUserIdAndLoginUser(usr.getId(), user, hierarchy.getProjectId(), new Date());
					    					if(pm != null){
					    						commonUtil.storeTimesheetSubmissionRecords(user);
					    					}
										 
							}*/
							ProjectApproverHierarchy assignedProjectManager=null;
							 if(hierarchy.getProjectId() != null && hierarchy.getProjectId().getId() != null){
			    					ProjectMembers pm1 = projectMembersService.getProjectMemberByUserIdAndProjectId(terminatedUser.getId(), hierarchy.getProjectId().getId());
				    				if(pm1 != null && pm1.getId() != null){
				    					ProjectMembersHistory projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm1.getId());
				    					
				    					if(null != projectMembersHistory){
				    						projectMembersHistory.setProjectTerminationDate(new Date());
					    					projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
				    					}
				    					
					    				commonUtil.terminateProjectMemberByProjectMemberId(pm1.getId(), Primary.Yes);
					    				
					    				List<TimesheetSubmissions> submissionsList = timesheetSubmissionsService.getTimeSheetSubmissionByProjectMemberId(pm1.getId());
					    				if(null != submissionsList && submissionsList.size() > 0){
					    					for(TimesheetSubmissions submissions : submissionsList){
					    						submissions.setStatus(Constants.MEMBER_IN_ACTIVE);
					    						submissions.setUpdatedBy(user.getUserCode());
					    						submissions.setUpdatedDate(new Date());
					    						timesheetSubmissionsService.addTimesheetSubmissions(submissions);
					    					}
					    				}
				    				}
				    				
				    		
				    				 
			    				}
						
							 assignedProjectManager=projectApproverHierarchyService.updateProjectApproverHierarchy(hierarchy);
							 assignedNewUserAsaassignedProjectManager(user,usr,userId,orgLocations,projectApproverCount);
		    		}
		    		
			}
			
			
			
			
	private void assignedNewUserAsaassignedProjectManager(User loginUser, User assUserName, Long userId, OrgLocations orgLocations, Integer projectApproverCount) throws Exception
	{
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(assUserName.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		User termentaedUserName = userService.getUserById(userId);
		globalVarsMap.put("loginUser", loginUser.getFullName());
		globalVarsMap.put("assignedUser", assUserName.getFirstName());
		globalVarsMap.put("reqCount", projectApproverCount.toString());
		globalVarsMap.put("terminatedUser", termentaedUserName.getFirstName());
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginURL", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_As_A_PROJECT_APROVER, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.USER_NEW_ACTION_ASSIGNED_As_A_PROJECT_APROVER,new ArrayList<EmailAttachment>());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, assUserName, orgLocations, notificationScenario, new HashMap<String, String>());
	}
			

			/*@Override
			public HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek() throws Exception {
				return projectsDao.getAllUserTimeSheetSubmissionStatusCurrentWeek();
			}
			  */
			
			@Override
			public void saveOrUpdateProjectElkDocument(Projects projects) throws Exception{
				List<ProjectApproverHierarchy> projectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectId(projects.getId());
				Collection<ProjectMembers> projectMembers = projectMembersService.getAllProjectMembersByProjectId(projects.getId());
				Projects updatedProject = getProjectById(projects.getId());
				 //Elastic Search Document Saving
				ProjectsIndex projectsIndex = projectsIndexHelper.getProjectsIndexFromProjects(updatedProject, projectApproverHierarchy, projectMembers);
				sourceleadELKBaseIndexer.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,updatedProject.getId().toString());
			}
			
			@Override
			public Integer getProjectCountByOrgLocationId(Long orgLocationId)
					throws Exception {
				return projectsDao.getProjectCountByOrgLocationId(orgLocationId);
			}

			@Override
			public List<Object> getAllUniversalSearchProjectsList(OrgLocations orgLocation, String searchValue) throws Exception {
				List<Object>   searchList = new ArrayList<>();
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
				 //Integer maxResults = projectsService.getAllProjects().size();
				 
				 Integer maxResults = getProjectCountByOrgLocationId(orgLocation.getId());
					
					//All Keywords
				 ArrayList<String> anyKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(searchValue);
					if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : anyKeyWordArray){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getProjectsQueryBuilderList(keywd);
							if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
								for(QueryBuilder QueryBuilder :QueryBuilderList){
									anyBoolSubQueryBuilder.should(QueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(anyBoolQueryBuilder);
					}
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					df.setTimeZone(TimeZone.getTimeZone("GMT"));
					SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
					if(searchHits != null && searchHits.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHits) {
							String json = hit.getSourceAsString();
							mapper.setDateFormat(df);
							ProjectsIndex projectsIndex = mapper.readValue(json, ProjectsIndex.class);
							searchList.add(projectsIndex);
						}
					}
				
				return searchList;
			}

			@Override
			public Boolean validateTasksName(String taskname, Long projectId) throws Exception {
				Boolean flag = projectsDao.validateTasksName(taskname,projectId);
				return flag;
			}

			@Override
			public List<Projects> getClientProjectsByOrgRelationId(Long orgRelationId, Long orgLocationId) throws Exception
			{
				return projectsDao.getClientProjectsByOrgRelationId(orgRelationId,orgLocationId);
			}

			@Override
			public List<Projects> getAllProjectsByOrgLocationIdAndStatus(Long orgLocationId) throws Exception
			{
				return projectsDao.getAllProjectsByOrgLocationIdAndStatus(orgLocationId);
			}

			@Override
			public Projects getProjectByProjectUUID(String projectUUID) throws Exception {
				return projectsDao.getProjectByProjectUUID(projectUUID);
			}

			@Override
			public Projects storeClientProject(ClientProjectDTO clientProjectDTO) throws Exception {
				
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	
			 	Projects projects = projectsDao.getProjectById(Long.valueOf(clientProjectDTO.getClientProject()));
			 	Set<ProjectClients> projectClientsList = new HashSet<>();
			 	
			 	OrgRelations orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(loginOrgLocation.getId(), Long.valueOf(clientProjectDTO.getClientOrgLocId()));
			 	if(orgRelations == null) {
			 		orgRelations = orgRelationsService.getVendorOrgRelationByResponderLocationIdAndLocationId(loginOrgLocation.getId(), Long.valueOf(clientProjectDTO.getClientOrgLocId()));
			 	}
			 	
			 	projectClientsList = getProjectClientsForClientProject(clientProjectDTO,projects,loginOrgLocation,user,orgRelations,projectClientsList);
	 			projects.setProjectClients(projectClientsList);
			 	
			 	Projects updatedProjects = saveProject(projects);
			 	ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(projects.getId(), loginOrgLocation.getId(), orgRelations.getId());
			 	updatedProjects.setProjectClientUUID(projectClients.getUuid());
				saveOrUpdateProjectElkDocument(updatedProjects);
			    return updatedProjects;
			}
			
			
			private Set<ProjectClients> getProjectClientsForClientProject(ClientProjectDTO clientProjectDTO,Projects projects,OrgLocations orglocations,User user,OrgRelations orgRelations,Set<ProjectClients> projectClientsList) throws Exception {
				    
				    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				    ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(projects.getId(), orglocations.getId(), orgRelations.getId());
					if(projectClients == null){
							projectClients = new ProjectClients();
							projectClients.setProjectId(projects);
							projectClients.setOrgLocationsId(orglocations);
							projectClients.setOrgId(orglocations.getOrg());
							projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							projectClients.setStatus(1);
							projectClients.setLevel(2);
							projectClients.setOrgRelationId(orgRelations);
							projectClients.setCreatedDate(new Date());
							projectClients.setUpdatedDate(new Date());
							projectClients.setCreatedBy(user);
							projectClients.setUpdatedBy(user);
							
					}
						projectClientsList = projects.getProjectClients();
						if(projectClientsList != null){
							for(ProjectClients projClients : projectClientsList){
								if(projClients.getOrgLocationsId().getId().equals(loginOrgLocation.getId())){
									projectClients = projClients;
								}
							}
						}
						
						ProjectsDTO projectsDTO = new ProjectsDTO();
						projectsDTO.setLevel1Manager(clientProjectDTO.getLevel1Manager());
						projectsDTO.setLevel2Manager(clientProjectDTO.getLevel2Manager());
						projectsDTO.setLevel1ExpenseApprover(clientProjectDTO.getLevel1ExpenseApprover());
						projectsDTO.setLevel2ExpenseApprover(clientProjectDTO.getLevel2ExpenseApprover());
						Set<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.prepareProjectApproverHierarchyListByJson(projectsDTO,request,projects,projectClients);
						projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
						
						Set<ExpenseProjectApproverHierarchy> projectExpenseApproverHierarchyList = projectApproverHierarchyService.prepareExpenseProjectApproverHierarchyList(projectsDTO,request,projects,projectClients);
						projectClients.setExpenseProjectApproverHierarchyList(projectExpenseApproverHierarchyList);
					
					projectClientsList.add(projectClients);
					
				return projectClientsList;	
			}
			
			public OrgRelations saveOrgRelations(OrgLocations vendorOrgLocations,OrgLocations clientOrgLocation,User user){
				
				OrgRelations orgRelations = new OrgRelations();
 				orgRelations.setLocationId(vendorOrgLocations);
 				orgRelations.setResponderLocationId(clientOrgLocation);
 				orgRelations.setInitiatorType("Vendor");
 				orgRelations.setResponderType("Client");
 				orgRelations.setInitiatorLevel("Tier-5");
 				orgRelations.setResponderLevel("Tier-5");
 				orgRelations.setStatus("ACTIVE");
 				orgRelations.setGenerateInvoice("Bulk");
 				orgRelations.setCreatedBy(user.getUserCode());
 				orgRelations.setCreatedDate(new Date());
 				orgRelations.setUpdatedBy(user.getUserCode());
 				orgRelations.setUpdatedDate(new Date());
 				
 				Set<OrgSpoc> orgSpocSet = new HashSet<>();
 				OrgSpoc orgSpoc = new OrgSpoc();
 				orgSpoc.setOrgRelations(orgRelations);
 				orgSpoc.setClientUser(clientOrgLocation.getUser());
 				orgSpoc.setVendorUser(user);
 				orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
 				orgSpoc.setClientLocationId(clientOrgLocation);
				orgSpoc.setVendorLocationId(vendorOrgLocations);
				orgSpoc.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				orgSpoc.setCreatedDate(new Date());
				orgSpoc.setCreatedBy(user.getUserCode());
				orgSpoc.setUpdatedDate(new Date());
				orgSpoc.setUpdatedBy(user.getUserCode());
				orgRelations.setOrgSpocList(orgSpocSet);
				return orgRelations;
			}

			@Override
			public List<Projects> getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(Long orgLocationsId,
					Long clientOrgLocationId) throws Exception {
				return projectsDao.getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(orgLocationsId, clientOrgLocationId);
			}

			@Override
			public Integer getProjectCountByOrgLocationIdAndUserCode(Long orgLocationId, String userCode) throws Exception {
				return projectsDao.getProjectCountByOrgLocationIdAndUserCode(orgLocationId, userCode);
			}

			@Override
			public Projects getProjectByOrgLocationsIdAndProjectName(Long orgLocationsId,String projectName) throws Exception {
				return projectsDao.getProjectByOrgLocationsIdAndProjectName(orgLocationsId,projectName);
			}

			@Override
			public List<Projects> getAllProjectsByOrgLocationIdAndStatusForTransfer(Long orgLocationId,
					String projectClientUUID) throws Exception {
				return projectsDao.getAllProjectsByOrgLocationIdAndStatusForTransfer(orgLocationId, projectClientUUID);
			}

			@Override
			public Projects makeInternalProjectAsClientProject(Projects projects, Long orgRelationId,Long clientOrgLocationId,OrgLocations loginOrgLocation)
					throws Exception {
				
				OrgRelations orgRelations = orgRelationsService.getOrgRelationById(orgRelationId);
				OrgLocations clientOrgLocations = orgLocationService.getOrgLocations(clientOrgLocationId);
				if(clientOrgLocations != null && orgRelations != null) {
					projects.setOrgLocationsId(clientOrgLocations);
					updateProject(projects);
					List<ProjectClients> projectClientsList = projectClientService.getProjectClientsListByProjectId(projects.getId());
					if(projectClientsList != null && projectClientsList.size() > 0) {
						for(ProjectClients projectClients : projectClientsList) {
							if(projectClients.getOrgLocationsId().getId().equals(loginOrgLocation.getId()) && projectClients.getOrgRelationId() == null) {
								projectClients.setOrgRelationId(orgRelations);
							}
							if(projectClients.getLevel() != null) {
								Integer level = projectClients.getLevel();
								projectClients.setLevel(level+1);
							}
							projectClients.setIsEndClient(null);
							projectClientService.storeProjectClients(projectClients);
						}
					}
					
					ProjectClients projectClients = new ProjectClients();
					OrgSpoc orgSpoc = orgSpocDao.getGusetClientOrgSpocByOrgRelationId(orgRelationId);
					projectClients.setProjectId(projects);
					projectClients.setOrgLocationsId(clientOrgLocations);
					projectClients.setOrgId(clientOrgLocations.getOrg());
					projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					projectClients.setStatus(1);
					projectClients.setLevel(1);
					projectClients.setIsEndClient(Constants.YES);
					projectClients.setCreatedDate(projects.getCreatedDate());
					projectClients.setUpdatedDate(projects.getUpdatedDate());
					projectClients.setCreatedBy(orgSpoc.getClientUser());
					projectClients.setUpdatedBy(orgSpoc.getClientUser());
					projectClients = projectClientService.storeProjectClients(projectClients);
					
					ProjectApproverHierarchy projectApproverHierarchy = new ProjectApproverHierarchy();
					projectApproverHierarchy.setProjectId(projects);
					projectApproverHierarchy.setProjectClientsId(projectClients);
					projectApproverHierarchy.setApproverUserId(orgSpoc.getClientUser());
					projectApproverHierarchy.setLevel(1);
					projectApproverHierarchy.setStatus("1");
					projectApproverHierarchyService.saveProjectApproverHierarchy(projectApproverHierarchy);
					
					ExpenseProjectApproverHierarchy projectExpenseApproverHierarchy = new ExpenseProjectApproverHierarchy();
					projectExpenseApproverHierarchy.setProjectId(projects);
					projectExpenseApproverHierarchy.setProjectClientsId(projectClients);
					projectExpenseApproverHierarchy.setApproverUserId(orgSpoc.getClientUser());
					projectExpenseApproverHierarchy.setLevel(1);
					projectExpenseApproverHierarchy.setStatus("1");
					expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(projectExpenseApproverHierarchy);
					
					ProjectBudget projectBudget = projectBudgetService.getProjectBudgetsByProjectId(projects.getId());
					if(projectBudget != null) {
						projectBudget.setApproverUserId(orgSpoc.getClientUser());
						projectBudgetService.saveProjectBudget(projectBudget);
					}
					
					
					ProjectClients loginOrgProjectClient = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), loginOrgLocation.getId());
					if(loginOrgProjectClient != null) {
						List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectClientId(loginOrgProjectClient.getId());
						if(projectMembersList != null && projectMembersList.size() > 0) {
							for(ProjectMembers projectMembers : projectMembersList) {
								Candidate candidate = null;
								candidate = candidateService.findByCandidateEmail(projectMembers.getProjectMemberCandidteId().getEmail(), clientOrgLocationId);
								CandidateDTO candidateDTO = new CandidateDTO();
								candidateDTO.setEmail(projectMembers.getProjectMemberCandidteId().getEmail());
								candidateDTO.setMobilePhone(projectMembers.getProjectMemberCandidteId().getMobilePhone());
								candidateDTO.setFirstName(projectMembers.getProjectMemberCandidteId().getFirstName());
								candidateDTO.setLastName(projectMembers.getProjectMemberCandidteId().getLastName());
								MasterCandidate masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
								if(candidate == null) {
									candidate = new Candidate();
									if(null == masterCandidate){
										masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, orgSpoc.getClientUser());
									}
									
									candidate.setMasterCandidateId(masterCandidate);
									candidate.setFirstName(projectMembers.getProjectMemberCandidteId().getFirstName());
									candidate.setLastName(projectMembers.getProjectMemberCandidteId().getLastName());
									candidate.setEmail(projectMembers.getProjectMemberCandidteId().getEmail());
									candidate.setOrgRelationId(orgRelations);
									candidate.setSpocId(orgSpoc);
									candidate.setParentCandidateId(projectMembers.getProjectMemberCandidteId());
									candidate.setDesignationType("C2C");
									candidate.setIsAssociate(Constants.NO);
									candidate.setOrgLocationId(clientOrgLocations);
									candidateService.addCandidate(candidate);
									candidateService.saveOrUpdateCandidateElkDocument(candidate);
								}else {
									candidate.setOrgRelationId(orgRelations);
									candidate.setSpocId(orgSpoc);
									candidate.setParentCandidateId(projectMembers.getProjectMemberCandidteId());
									candidateService.addCandidate(candidate);
									candidateService.saveOrUpdateCandidateElkDocument(candidate);
								}
								
								ProjectMembers clientProjectMembers = new ProjectMembers();
								clientProjectMembers.setProjects(projects);
								clientProjectMembers.setProjectClientsId(projectClients);
								clientProjectMembers.setProjectMemberCandidteId(candidate);
								clientProjectMembers.setMasterCandidateId(masterCandidate);
								clientProjectMembers.setProjectAssignedDate(projectMembers.getProjectAssignedDate());
								clientProjectMembers.setProjectEndDate(projectMembers.getProjectEndDate());
								clientProjectMembers.setCurrentBillRate(projectMembers.getCurrentBillRate());
								clientProjectMembers.setRateType(projectMembers.getRateType());
								clientProjectMembers.setBillRateCurrency(projectMembers.getBillRateCurrency());
								clientProjectMembers.setStatus(projectMembers.getStatus());
								clientProjectMembers.setIsManuallyTerminated(projectMembers.getIsManuallyTerminated());
								clientProjectMembers.setCreatedDate(projectMembers.getCreatedDate());
								clientProjectMembers.setCreatedBy(projectMembers.getCreatedBy());
								clientProjectMembers.setUpdatedBy(projectMembers.getUpdatedBy());
								clientProjectMembers.setUpdatedDate(projectMembers.getUpdatedDate());
								clientProjectMembers.setTerminateReason(projectMembers.getTerminateReason());
								clientProjectMembers.setPayRateCurrency(projectMembers.getPayRateCurrency());
								clientProjectMembers.setPayRate(projectMembers.getPayRate());
								clientProjectMembers.setPayRateType(projectMembers.getPayRateType());
								clientProjectMembers.setEstimatedHours(projectMembers.getEstimatedHours());
								clientProjectMembers.setEstimatedBudget(projectMembers.getEstimatedBudget());
								clientProjectMembers = projectMembersService.saveProjectMember(clientProjectMembers);
								
								ProjectMembersHistory projectMembersHistory = projectMembersHistoryService.getProjectMembersAllHistoryByProjectMemberId(projectMembers.getId());
								if(projectMembersHistory != null) {
									ProjectMembersHistory clientProjectMembersHistory = new ProjectMembersHistory();
									clientProjectMembersHistory.setProjectMemberId(clientProjectMembers);
									clientProjectMembersHistory.setProjectAssignedDate(projectMembersHistory.getProjectAssignedDate());
									clientProjectMembersHistory.setProjectTerminationDate(projectMembersHistory.getProjectTerminationDate());
									clientProjectMembersHistory.setStatus(projectMembersHistory.getStatus());
									clientProjectMembersHistory.setCreatedDate(projectMembersHistory.getCreatedDate());
									clientProjectMembersHistory.setCreatedBy(projectMembersHistory.getCreatedBy());
									clientProjectMembersHistory.setUpdatedBy(projectMembersHistory.getUpdatedBy());
									clientProjectMembersHistory.setUpdatedDate(projectMembersHistory.getUpdatedDate());
									projectMembersHistoryService.updateProjectMembersHistory(clientProjectMembersHistory);
								}
								
								List<TimesheetSubmissions> timesheetSubmissionsList =  timesheetSubmissionsService.getTimesheetSubmissionsByProjectIdAndMasterCandidateId(projects.getId(),masterCandidate.getId());
								if(timesheetSubmissionsList != null && timesheetSubmissionsList.size() > 0) {
									for(TimesheetSubmissions submissions : timesheetSubmissionsList){
										if(projectApproverHierarchy != null) {
											SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(submissions.getId());
											SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
											saHierarchy.setHeirarchyId(projectApproverHierarchy);
											saHierarchy.setSubmissionId(submissions);
					    			        saHierarchy.setFrequencyStartDate(sah.getFrequencyStartDate());
										    saHierarchy.setFrequencyEndDate(sah.getFrequencyEndDate());
											saHierarchy.setSubmissionStatus(sah.getSubmissionStatus());
											saHierarchy.setSubmissionApprovalStatus(sah.getSubmissionApprovalStatus());
											saHierarchy.setCreatedDate(sah.getCreatedDate());
											saHierarchy.setCreatedBy(sah.getCreatedBy());
											saHierarchy.setActionPerformedBy(sah.getActionPerformedBy());
											saHierarchy.setRejectedReason(sah.getRejectedReason());
											saHierarchy.setReopenedUser(sah.getReopenedUser());
											saHierarchy.setReopenRejectedReason(sah.getReopenRejectedReason());
											saHierarchy.setUpdatedDate(sah.getUpdatedDate());
											saHierarchy.setUpdatedBy(sah.getUpdatedBy());
											saHierarchy.setStatus(sah.getStatus());
											submissionApproverHierarchyService.addSubmissionApproverHierarchy(saHierarchy);
										}
									}
								}
								
								
								List<Expenses> expList  = expensesService.getExpensesByProjectIdAndMasterCandidateId(projects.getId(),masterCandidate.getId());
								if(expList != null && !expList.isEmpty()) {
									for(Expenses expense : expList){
										ExpensesApproverHierarchy eah = expensesApprovalService.getexpensesApproverHierarchyForEmployeeByExpenseId(expense.getId());
										ExpensesApproverHierarchy eaHierarchy = new ExpensesApproverHierarchy();
										eaHierarchy.setHierarchyId(projectExpenseApproverHierarchy);
										eaHierarchy.setExpenseId(expense);
										eaHierarchy.setExpenseStatus(eah.getExpenseStatus());
										eaHierarchy.setExpenseApprovalStatus(eah.getExpenseApprovalStatus());
										eaHierarchy.setCreatedDate(eah.getCreatedDate());
										eaHierarchy.setCreatedBy(eah.getCreatedBy());
										eaHierarchy.setUpdatedDate(eah.getUpdatedDate());
										eaHierarchy.setUpdatedBy(eah.getUpdatedBy());
										eaHierarchy.setStatus(eah.getStatus());
										eaHierarchy.setActionPerformedBy(eah.getActionPerformedBy());
										eaHierarchy.setRejectedReason(eah.getRejectedReason());
										eaHierarchy.setReopenedUser(eah.getReopenedUser());
										eaHierarchy.setReopenRejectedReason(eah.getReopenRejectedReason());
										expensesApprovalService.saveExpensesApproverHierarchy(eaHierarchy);
									}
								}
							}
						}
					}
					
					
				}
				
				projectsDao.updateProject(projects);
				return projects;
			}

			@Override
			public List<Projects> getAllProjectByOrgLocationsId(Long orgLocationId) throws Exception {
				return projectsDao.getAllProjectByOrgLocationsId(orgLocationId);
			}

			@Override
			public Projects getClientProjectApproversUpdate(String projectClientUUID, User user,
					ProjectsDTO projectDTO) throws Exception {
				
				ProjectClients projectClients = projectClientService.getProjectClientsByUUID(projectClientUUID); 
				Projects projects = getProjectById(projectClients.getProjectId().getId());
				
		    	if(!isNullOrEmpty(projectDTO.getProjectForValue())) {
		    		if(projectDTO.getProjectForValue().equalsIgnoreCase("Timesheets")) {
		    			projects.setIsProjectForTimeSheets(Constants.YES);
		    			projects.setIsProjectForExpenses(Constants.NO);
		    		}else if(projectDTO.getProjectForValue().equalsIgnoreCase("Expenses")) {
		    			projects.setIsProjectForExpenses(Constants.YES);
		    			projects.setIsProjectForTimeSheets(Constants.NO);
		    		}else {
		    			projects.setIsProjectForTimeSheets(Constants.YES);
		    			projects.setIsProjectForExpenses(Constants.YES);
		    		}
		    		
		    		projectsDao.updateProject(projects);
		    	}
				
				if(StringUtils.isNotBlank(projectDTO.getLevel1Manager()) && projectClients != null){
		    		HashSet<Long> oldlist = new HashSet<>();
		    		HashSet<Long> freshlist = new HashSet<>();
		    		HashSet<Long> templist = null;
		    		List<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getProjectsHierarchyListByProjectClientId(projectClients.getId());
		    		ProjectClients pc = null;
		    		Boolean isLevel2ManagerExists = false;
		    		Long level1HierarchyId = null;
		    		Long level2HierarchyId = null;
		    		for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
			    			 if(prHierarchy.getLevel() == 1){
					    		 level1HierarchyId = prHierarchy.getId();
					    	 }else {
					    		 level2HierarchyId = prHierarchy.getId();
					    	 }
		    			     pc = prHierarchy.getProjectClientsId();
							 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
							 Long fieldValuelong = null;
							     String level1UserDetails[] = projectDTO.getLevel1Manager().split(",");
							     if(prHierarchy.getLevel() == 1 || (prHierarchy.getLevel() == 2 && projectApproverHierarchies.size() == 1))
							     {
							    	 fieldValuelong = Long.valueOf(level1UserDetails[0]);
							     }
							     if(prHierarchy.getLevel() == 2 &&  StringUtils.isNotBlank(projectDTO.getLevel2Manager())){
							    	 if(!"".equalsIgnoreCase(projectDTO.getLevel2Manager())){
							    		 isLevel2ManagerExists = true;
							    		 fieldValuelong = Long.valueOf(projectDTO.getLevel2Manager());
							    	 }
							    	 
							     }
							      if(prHierarchy.getLevel() == 2 && (StringUtils.isEmpty(projectDTO.getLevel2Manager()))){
							    	 oldlist.add(pah.getApproverUserId().getId());
							    	 pah.setStatus("0");
							    	// pah.setApprovalMechanismType(projectDTO.getProjectApprovalType());
							    	 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
							     }
							     
							     if(fieldValuelong != null){
							    	 User usr = userService.getUserById(fieldValuelong);
									 oldlist.add(pah.getApproverUserId().getId());
									 freshlist.add(usr.getId());
									 pah.setApproverUserId(usr);
									 pah.setStatus("1");
									 //pah.setApprovalMechanismType(projectDTO.getProjectApprovalType());
									 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
							     }
							    
		    	    }
		    		
		    		// if   we  are adding new  level 2 manager while editing
		    		if(projectApproverHierarchies.size()== 1){ 	
						if(StringUtils.isNotBlank(projectDTO.getLevel2Manager())){
							Long[] ids = new Long[10];
							User approverUserId2 = new User();
							ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
							pah2.setProjectId(projects);
							approverUserId2.setId(Long.parseLong(projectDTO.getLevel2Manager()));
							pah2.setApproverUserId(approverUserId2);
							pah2.setLevel(2);
							pah2.setProjectClientsId(pc);
							pah2.setStatus("1");
							//pah2.setApprovalMechanismType(projectDTO.getProjectApprovalType());
							ids[1] = approverUserId2.getId();
							projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
							freshlist.add(Long.parseLong(projectDTO.getLevel2Manager()));
							
						}
					 }
		    		if(oldlist != null && !oldlist.isEmpty()){
		    			templist = (HashSet<Long>) oldlist.clone();
		    		}
		    		//getting projectmember userId's who are terminated or who are dropped from project
		    		oldlist.removeAll(freshlist);
		    		if(oldlist != null && !oldlist.isEmpty()){
		    			for(Long terminatedUserId : oldlist){
		    				if(projects != null && projects.getId() != null){
		    					ProjectMembers pm = projectMembersService.getProjectMemberByUserIdAndProjectId(terminatedUserId, projects.getId());
			    				if(null != pm && pm.getId() != null){
			    					ProjectMembersHistory projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
			    					if(projectMembersHistory != null){
			    						projectMembersHistory.setProjectTerminationDate(commonUtil.currentDate());
				    					projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
			    					}
				    				commonUtil.terminateProjectMemberByProjectMemberId(pm.getId(), Primary.Yes);
			    				}
			    				 
		    				}
		    			}
		    		}
		    		
		    		if(templist != null && !templist.isEmpty()){
		    			//getting projectmember userId's who are added to the project
		    			freshlist.removeAll(templist);
		    			if(freshlist != null && !freshlist.isEmpty()){
		    				for(Long freshlyaddedUserId : freshlist){
		    					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(freshlyaddedUserId,projects.getOrgLocationsId().getId());
		    					if(userRole != null && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)){
		    						storeProjectMemberByMemberUserIdAndLoginUser(freshlyaddedUserId, user, projects, commonUtil.currentDate());
		    					}
		    				}
		    			}
		    			
		    		}
		    		
		    		
		    		
		    		Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchiesList = projectClients.getExpenseProjectApproverHierarchyList();
			    		for(ExpenseProjectApproverHierarchy expenseHierarchy : expenseApproverHierarchiesList) {
			    			if(expenseHierarchy.getLevel() == 1) {
			    				  String level1ApproverUserDetails[] = projectDTO.getLevel1ExpenseApprover().split(",");
			    				  User approverUser = new User();
			    				  approverUser.setId(Long.valueOf(level1ApproverUserDetails[0]));
			    				  expenseHierarchy.setApproverUserId(approverUser);
			    				  //expenseHierarchy.setApprovalMechanismType(projectDTO.getExpenseApprovalType());
			    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(expenseHierarchy);
			    			}
			    			
			    			  if(expenseHierarchy.getLevel() == 2){
			    				  
			    				  if((projectDTO.getLevel2ExpenseApprover() == null || projectDTO.getLevel2ExpenseApprover().isEmpty())) {
			    					  expenseHierarchy.setStatus("0");
			    					 // expenseHierarchy.setApprovalMechanismType(projectDTO.getExpenseApprovalType());
				    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(expenseHierarchy);
			    				  }else {
				    				  User approverUser2 = new User();
				    				  approverUser2.setId(Long.parseLong(projectDTO.getLevel2ExpenseApprover()));
				    				  expenseHierarchy.setApproverUserId(approverUser2);
				    				  expenseHierarchy.setStatus("1");
				    				 // expenseHierarchy.setApprovalMechanismType(projectDTO.getExpenseApprovalType());
				    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(expenseHierarchy);
				    			  }
			    				 
			    			  }
		    		}
			    		
		    		if(expenseApproverHierarchiesList != null && expenseApproverHierarchiesList.size() == 1 && (projectDTO.getLevel2ExpenseApprover() != null && !projectDTO.getLevel2ExpenseApprover().isEmpty()))	{
		    				ExpenseProjectApproverHierarchy eah2 = new ExpenseProjectApproverHierarchy();
		    				  User approverUser2 = new User();
		    				  approverUser2.setId(Long.parseLong(projectDTO.getLevel2ExpenseApprover()));
		    				  eah2.setApproverUserId(approverUser2);
		    				  eah2.setProjectId(projects);
		    				  eah2.setProjectClientsId(projectClients);
		    				  eah2.setStatus("1");
		    				 // eah2.setApprovalMechanismType(projectDTO.getExpenseApprovalType());
		    				  eah2.setLevel(2);
		    				  expenseProjectApproverHierarchyService.saveProjectExpenseProjectApproverHierarchy(eah2);
		    		}
			    		
		    		 /**
		    		  * Storing Submission Approver Hierarchy Records based on Changes Made Against Level1 and Level2 Approvers
		    		  */
		    		 LinkedList<SubmissionApproverHierarchy> saHierarchyList = new LinkedList<SubmissionApproverHierarchy>();
		    		 if(isLevel2ManagerExists){
		    			 List<SubmissionApproverHierarchy> sahList =  submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectApproverHierarchyId(level1HierarchyId);
			    	     if(sahList != null && !sahList.isEmpty()){
			    	    	 for(SubmissionApproverHierarchy sah :sahList){
			    	    		 SubmissionApproverHierarchy subSah =  submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectApproverHierarchyIdAndSubmissionId(level2HierarchyId, sah.getSubmissionId().getId());
			    	    		 if(subSah != null && (StringUtils.isEmpty(subSah.getStatus())  || (StringUtils.isNotEmpty(subSah.getStatus()) && subSah.getStatus().equalsIgnoreCase("0")))){
			    	    			    subSah.setSubmissionStatus(sah.getSubmissionStatus());
			    	    			    subSah.setBillingStatus(sah.getBillingStatus());
			    	    			    subSah.setInvoiceStatus(sah.getInvoiceStatus());
			    	    			    subSah.setSubmissionApprovalStatus(sah.getSubmissionApprovalStatus());
										subSah.setCreatedDate(commonUtil.currentDate());
										subSah.setCreatedBy(user.getUserCode());
										subSah.setUpdatedDate(commonUtil.currentDate());
										subSah.setUpdatedBy(user.getUserCode());
										subSah.setRejectedReason(sah.getRejectedReason());
										subSah.setActionPerformedBy(sah.getActionPerformedBy());
										subSah.setReopenedUser(sah.getReopenedUser());
										subSah.setReopenReason(sah.getReopenReason());
										subSah.setReopenRejectedReason(sah.getReopenRejectedReason());
										subSah.setStatus("1");
										saHierarchyList.add(subSah);
			    	    		 }
			    	    		 else{
			    	    			    SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
			    	    			    ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
			    	    			    pah.setId(level2HierarchyId);
										saHierarchy.setHeirarchyId(pah);
										saHierarchy.setSubmissionId(sah.getSubmissionId());
										saHierarchy.setSubmissionStatus(sah.getSubmissionStatus());
										saHierarchy.setSubmissionApprovalStatus(sah.getSubmissionApprovalStatus());
										saHierarchy.setFrequencyStartDate(sah.getFrequencyStartDate());
										saHierarchy.setFrequencyEndDate(sah.getFrequencyEndDate());
										saHierarchy.setCreatedDate(commonUtil.currentDate());
										saHierarchy.setCreatedBy(user.getUserCode());
										saHierarchy.setUpdatedDate(commonUtil.currentDate());
										saHierarchy.setBillingStatus(sah.getBillingStatus());
										saHierarchy.setInvoiceStatus(sah.getInvoiceStatus());
										saHierarchy.setRejectedReason(sah.getRejectedReason());
										saHierarchy.setActionPerformedBy(sah.getActionPerformedBy());
										saHierarchy.setReopenedUser(sah.getReopenedUser());
										saHierarchy.setReopenReason(sah.getReopenReason());
										saHierarchy.setReopenRejectedReason(sah.getReopenRejectedReason());
										saHierarchy.setUpdatedBy(user.getUserCode());
										saHierarchy.setStatus("1");
										saHierarchyList.add(saHierarchy);
			    	    		 }
			    	    	 }
			    	     }
					 }
		    		 else{
		    			if(level2HierarchyId != null) {
		    				 List<SubmissionApproverHierarchy> sahList =  submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectApproverHierarchyId(level2HierarchyId);
				    	     if(sahList != null && !sahList.isEmpty()){
				    	    	 for(SubmissionApproverHierarchy sah :sahList){
				    	    		 sah.setStatus("0");
									 saHierarchyList.add(sah);
				    	    	 }
				    	     }
		    			}
		    		
		    		 }
		    		 if(saHierarchyList != null && !saHierarchyList.isEmpty()){
		    			 for(SubmissionApproverHierarchy sah : saHierarchyList){
		    				 submissionApproverHierarchyService.updateSubmissionApproverHierarchy(sah);
		    			 }
		    			 
		    		 }
	    	}
				return projects;
			}

			
			@Override
			public Projects getProjectByProjectName(String projectName) throws Exception {
				return projectsDao.getProjectByProjectName(projectName);
			}

			@Override
			public File getPdfFile(ResponseList<ProjectsDTO> projectsList, OrgLocations orgLocations) throws Exception {
				return pdfUtil.createProjectPdfTable(projectsList, orgLocations);
			}
			
			
			@Override
			public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<ProjectsDTO> projectsList, OrgLocations orgLocations)  throws Exception{
				Row writeRow = null;
		        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,projectsList,orgLocations);
		        java.util.List<ProjectsDTO> list =  projectsList.getData();
		        int column=0;
		        for(ProjectsDTO projects : list){
		       	 writeRow = createExcelsheet.createRow(column+1);
		       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,projects,orgLocations);
		       	 column++;
		        }
				return createExcelsheet;
			}

			private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column, ProjectsDTO projects,OrgLocations orgLocations) throws Exception {

			     	 ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
		
			     	 if(projects.getProjectSequeneNumber() != null) {
			     		 writeRow.createCell(0).setCellValue(projects.getProjectSequeneNumber()); 
			     	 }else {
			     		writeRow.createCell(0).setCellValue("--");
			     	 }
			     	 
			     	 if(projects.getProjectName() != null) {
			     		writeRow.createCell(1).setCellValue(projects.getProjectName());
			     	 } 
			     	 
			     	Set<ProjectApproverHierarchy> projectApproverHierarchies = projectClients.getProjectApproverHierarchyList();
				    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
				    	if(approverHierarchy.getLevel() == 1 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
				    		projects.setManagerLevel1(approverHierarchy.getApproverUserId().getFullName());
				    	}else if(approverHierarchy.getLevel() == 2 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
				    		projects.setManagerLevel2(approverHierarchy.getApproverUserId().getFullName());
				    	}
				    }
				    String projectManagers = "";
				    if(projects.getManagerLevel1() != null){
			  		  projectManagers = projects.getManagerLevel1();
			 		  }
			  	    if(projects.getManagerLevel2() != null ){
			    	 projectManagers = projects.getManagerLevel2();
					 }
			  	  
			  	  if(projects.getManagerLevel1() != null  && projects.getManagerLevel2() != null ){
			 		   projectManagers = projects.getManagerLevel1()+","+projects.getManagerLevel2();
				 	 }
				  	if(projectManagers != null) {
				  		writeRow.createCell(2).setCellValue(projectManagers);
				 	} 
				  	
				  	if(projects.getInvoiceFrequency() != null) {
				  		writeRow.createCell(3).setCellValue(projects.getInvoiceFrequency());
				  	}else {
				  		writeRow.createCell(3).setCellValue("--");
				  	}
			  	  
				  	
				  	
					OrgLocations orglo = orgLocationService.getOrgLocations(orgLocations.getId());
				  	if(projects.getOrgLocationsId() == orglo.getId().longValue()) {
						 java.util.List<ProjectBudget>  budgetList = projectBudgetService.getProjectBudgetByProjectTaskId(projects.getId(),orglo.getId());
						    if(budgetList != null && budgetList.size() > 0) {
						    	 double budgetSum = budgetList.stream().filter(p->p.getEstimatedBudgetValue() !=null).mapToDouble(p->p.getEstimatedBudgetValue()).sum();
									if(budgetList.size() >0) {
										ProjectBudget projectBudget = budgetList.iterator().next();
										projects.setProjectbudgetCurrency(projectBudget.getEstimatedBudgetCurrency());
									}
									projects.setProjectbudgetAmount(budgetSum);
							  		writeRow.createCell(4).setCellValue(projects.getProjectbudgetCurrency()+ " " +budgetSum);
		
						    }else {
						    	writeRow.createCell(4).setCellValue("--");
						    }
					 }
				  	
				  	
					if(projects.getEndDateStr() != null && projects.getStartDateStr() != null){
						writeRow.createCell(5).setCellValue(projects.getStartDateStr() +" To " +projects.getEndDateStr());
					}else {
						writeRow.createCell(5).setCellValue(projects.getStartDateStr());
					}
					
					
					 if(projectClients.getOrgRelationId() != null){
			    		 
			 			if(null != projectClients.getOrgRelationId().getLocationId() && projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
			 				projects.setClient(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
			 				writeRow.createCell(6).setCellValue(projects.getClient());
							}if(null != projectClients.getOrgRelationId().getResponderLocationId() && projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
								projects.setClient(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
								writeRow.createCell(6).setCellValue(projects.getClient());
							}
			 	  }else {
			 		 writeRow.createCell(6).setCellValue("--");
			 	  }
					 
				 if(projects.getProjectStatus().toString() != null){
					 writeRow.createCell(7).setCellValue(projects.getProjectStatus().toString());
				} 
				return createExcelsheet;
			}

			private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, ResponseList<ProjectsDTO> projectsList, OrgLocations orgLocations) {
				 CellStyle style = createExcelworkbook.createCellStyle();
			     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			     Font font = createExcelworkbook.createFont();
		         font.setColor(IndexedColors.BLACK.getIndex());
		         style.setFont(font);
		         String headers [] = Constants.PROJECT_EXCEL_HEADERS;
		 		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
			}

			@Override
			public List<Projects> getAllProjectsBasedOnStartDateEndDateAndProjectType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) {
				return projectsDao.getAllProjectsBasedOnProjectType(dayWiseTimesheetReoprtsDto);
			}

			@Override
			public List<Projects> getAllClientProjectsByOrgLocationId(Long locationId) throws Exception {
				return projectsDao.getAllClientProjectsByOrgLocationId(locationId);
			}

			@Override
			public List<ProjectsDTO> getProjectListForDaywiseReports(Long orgLocationsId, Long orgId,
					String isGlobalManager,Long userId,String type,String userRole) throws Exception {
				return projectsDao.getProjectListForDaywiseReports(orgLocationsId, orgId, isGlobalManager,userId,type,userRole);
			}

	@Override
	public ResponseList<ProjectReportDTO> getProjectReportList(ProjectReportDTO projectReportDTO, Long orgLocationId) throws Exception{
		ResponseList<ProjectReportDTO> responseList = new ResponseList<>();
		List<ProjectReportDTO> projectReportDTOList = new LinkedList<>();
		List<Projects> projectList = projectsDao.getProjectReportList(projectReportDTO,orgLocationId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(projectList!=null && !projectList.isEmpty()) { 
			int projectMemberStatus = 1;
			boolean activeFlag = false, inactiveFlag = false;
			
			if(projectReportDTO.isRefreshFlag()) {
				projectMemberStatus = 1;
			} else if(projectReportDTO.getAssociateStatus() != null && projectReportDTO.getAssociateStatus().length > 0) {
				for(String status : projectReportDTO.getAssociateStatus()) {
					if(status.equalsIgnoreCase("ACTIVE")) {
						activeFlag = true;
					}
					if(status.equalsIgnoreCase("INACTIVE")) {
						inactiveFlag = true;
					}
				}
				
				if(activeFlag && inactiveFlag) {
					projectMemberStatus = -1;
				} else if(activeFlag) {
					projectMemberStatus = 1;
				} else if(inactiveFlag) {
					projectMemberStatus = 0;
				}
			}
			
			for(Projects p : projectList) {
				Set<ProjectMembers> projectMemebersList = p.getProjectMembers();
				if(projectMemebersList != null && !projectMemebersList.isEmpty()) {
					for(ProjectMembers projectMember : projectMemebersList) {
						boolean flag = false;
						if(projectMemberStatus == -1) {
							flag = true;
						} else if(Integer.parseInt(projectMember.getStatus()) == projectMemberStatus) {
							flag = true;
						}
						if(flag) {
							if(projectMember.getProjectMemberCandidteId() != null && projectMember.getProjectMemberCandidteId().getOrgLocationId().getId().compareTo(orgLocationId) == 0) {
								ProjectReportDTO reportDTO = getProjectReport(p);
								//Project Members
								reportDTO.setProjectMembers(projectMember.getProjectMemberCandidteId().getFullName());
								ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
								if (pmh == null) {
									pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMember.getId());
								}
	/*							if(projectMember.getProjectAssignedDate() != null) {
									reportDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(projectMember.getProjects().getOrgLocationsId().getDateFormat(), projectMember.getProjectAssignedDate()));
								}
								if(projectMember.getProjectEndDate() != null) {
									reportDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(projectMember.getProjects().getOrgLocationsId().getDateFormat(), projectMember.getProjectEndDate())) ;
								}*/
								if (pmh != null && pmh.getProjectAssignedDate() != null) {
									reportDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(
											projectMember.getProjects().getOrgLocationsId().getDateFormat(),
											pmh.getProjectAssignedDate()));
								}
								if (pmh != null && pmh.getProjectTerminationDate() != null) {
									reportDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(
											projectMember.getProjects().getOrgLocationsId().getDateFormat(),
											pmh.getProjectTerminationDate()));
								}
								reportDTO.setMemberStatus(projectMember.getStatus());
								projectReportDTOList.add(reportDTO);
							}
						}
					}
				}
				/*else {
					projectReportDTOList.add(getProjectReport(p));
				}*/
			}
		}
		responseList.setResponse(Result.SUCCESS.name());
		responseList.setData(projectReportDTOList);
		return responseList;
	}

	private ProjectReportDTO getProjectReport(Projects p) {
		ProjectReportDTO reportDTO = new ProjectReportDTO();
		reportDTO.setProjectName(p.getProjectName());
		reportDTO.setProjectSeqNo(p.getProjectSequeneNumber());
		reportDTO.setProjectStatus(p.getProjectStatus().name());

		// managers
//		Collection<ProjectApproverHierarchy> projectApproverHierarchyList = p.getProjectApproverHierarchiesList();
		Set<ProjectApproverHierarchy> projectApproverHierarchyList =  p.getProjectApprovers();
		String managerLevel1 = "", managerLevel2 = "";
		for (ProjectApproverHierarchy pah : projectApproverHierarchyList) {
			if (pah.getApproverUserId() != null) {
				if (pah.getLevel() == 1 && pah.getStatus().equalsIgnoreCase("1"))
					managerLevel1 += (managerLevel1.isEmpty() ? "" : ", ")
							+ (pah.getApproverUserId().getFirstName() + " "
									+ (pah.getApproverUserId().getLastName() != null
											? pah.getApproverUserId().getLastName()
											: ""));

				if (pah.getLevel() == 2 && pah.getStatus().equalsIgnoreCase("1"))
					managerLevel2 += (managerLevel2.isEmpty() ? "" : ", ")
							+ (pah.getApproverUserId().getFirstName() + " "
									+ (pah.getApproverUserId().getLastName() != null
											? pah.getApproverUserId().getLastName()
											: ""));
			}
		}
		reportDTO.setProjectManagerNames(!managerLevel2.isEmpty() ? managerLevel2 : managerLevel1);
		reportDTO.setProjectStartDate(p.getStartDateStr());
		reportDTO.setProjectEndDate(p.getEndDateStr());
		return reportDTO;
	}

	@Override
	public File getProjectReportListOfExcel(List<ProjectReportDTO> projectReportDTO, User user,
			OrgLocations orgLocation, String isGlobalAccess) throws Exception {
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLocation.getOrgLocationName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, projectReportDTO);
		int column = 0;
		for (ProjectReportDTO reportList : projectReportDTO) {
			writeRow = createExcelsheet.createRow(column + 1);
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, reportList, orgLocation, user,isGlobalAccess);
			column++;
		}
		try {
			file = File.createTempFile(orgLocation.getOrgLocationName() + "_ProjectReport_" + dateFormat.format(new Date()), ".xlsx");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		file.setReadable(true);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			List<ProjectReportDTO> projectReportDTO) {

		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String[] headers = Constants.PROJECT_REPORT_EXPORT_HEADDERS;
		;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 0);
		return createExcelsheet;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ProjectReportDTO reportList,
			OrgLocations orgLocation, User loginUser, String isGlobalUser) throws Exception {
			if (null != reportList.getProjectSeqNo()) {
				writeRow.createCell(0).setCellValue(reportList.getProjectSeqNo());
				createExcelsheet.autoSizeColumn(0);
			} else {
				writeRow.createCell(0).setCellValue("--");
				createExcelsheet.autoSizeColumn(0);
			}

			if (null != reportList.getProjectName()) {
				writeRow.createCell(1).setCellValue(reportList.getProjectName());
				createExcelsheet.autoSizeColumn(1);
			} else {
				writeRow.createCell(1).setCellValue("--");
				createExcelsheet.autoSizeColumn(1);
			}

			if (null != reportList.getProjectStatus()) {
				writeRow.createCell(2).setCellValue(reportList.getProjectStatus());
				createExcelsheet.autoSizeColumn(2);
			} else {
				writeRow.createCell(2).setCellValue("--");
				createExcelsheet.autoSizeColumn(2);
			}

			if (null != reportList.getProjectManagerNames()) {
				 writeRow.createCell(3).setCellValue(reportList.getProjectManagerNames());
				createExcelsheet.autoSizeColumn(3);
			} else {
				writeRow.createCell(3).setCellValue("--");
				createExcelsheet.autoSizeColumn(3);
			}

			if (null != reportList.getProjectStartDate()) {
				writeRow.createCell(4).setCellValue(reportList.getProjectStartDate());
				createExcelsheet.autoSizeColumn(4);
			} else {
				writeRow.createCell(4).setCellValue("--");
				createExcelsheet.autoSizeColumn(4);
			}

			if (null != reportList.getProjectEndDate()) {
				writeRow.createCell(5).setCellValue(reportList.getProjectEndDate());
				createExcelsheet.autoSizeColumn(5);
			} else {
				writeRow.createCell(5).setCellValue("--");
				createExcelsheet.autoSizeColumn(5);
			}

			if (null != reportList.getProjectMembers()) {
				writeRow.createCell(6).setCellValue(reportList.getProjectMembers());
				createExcelsheet.autoSizeColumn(6);
			} else {
				writeRow.createCell(6).setCellValue("--");
				createExcelsheet.autoSizeColumn(6);
			}

			if (null != reportList.getMemberStartDate()) {
				writeRow.createCell(7).setCellValue(reportList.getMemberStartDate());
				createExcelsheet.autoSizeColumn(7);
			} else {
				writeRow.createCell(7).setCellValue("--");
				createExcelsheet.autoSizeColumn(7);
			}

			if (null != reportList.getMemberEndDate()) {
				writeRow.createCell(8).setCellValue(reportList.getMemberEndDate());
				createExcelsheet.autoSizeColumn(8);
			} else {
				writeRow.createCell(8).setCellValue("--");
				createExcelsheet.autoSizeColumn(8);
			}

			if(StringUtils.isNotBlank(reportList.getMemberStatus())) {
				if ( Integer.parseInt(reportList.getMemberStatus()) == Constants.RECORD_ACTIVE) {
					writeRow.createCell(9).setCellValue("Active");
					createExcelsheet.autoSizeColumn(9);
				} else {
					writeRow.createCell(9).setCellValue("In-Active");
					createExcelsheet.autoSizeColumn(9);
				}
			}
		
		
		return createExcelsheet;
	}

	

	@Override
	public Map<String, List<AvailableAssociatesDTO>> getAvailableAssociatesReport(
			AvailableAssociatesDTO availableAssociatesDTO, OrgLocations orgLocations) {
		Map<String, List<AvailableAssociatesDTO>> map = null;
		try {
			
			if(availableAssociatesDTO.getUserIds() == null || availableAssociatesDTO.getUserIds().isEmpty()) {
				List<Long> userIdList = new LinkedList<>();
				List<Candidate> empList = projectMembersService.getAllAvailableAssociates(orgLocations.getId());
				for(Candidate c : empList) {
					userIdList.add(c.getId());
				}
				availableAssociatesDTO.setUserIds(userIdList);
			}
			
			
			List<Object[]> objectList = projectsDao.getAvailableAssociatesReport(availableAssociatesDTO, orgLocations);
			List<AvailableAssociatesDTO> availableAssociatesDTOList = new ArrayList<>();
			if (objectList != null && !objectList.isEmpty()) {
				map = new HashMap<>();
				for (Object[] obj : objectList) {
					AvailableAssociatesDTO dto = new AvailableAssociatesDTO();
					SimpleDateFormat dateForm1 = new SimpleDateFormat(orgLocations.getDateFormat());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (obj[1] != null)
						dto.setEmpId(String.valueOf(obj[1]));
					if (obj[2] != null)
						dto.setEmpName(String.valueOf(obj[2]));
					if (obj[3] != null)
						dto.setProjectName(String.valueOf(obj[3]));
					if (obj[4] != null)
						dto.setProjectStartDate(dateForm1.format(sdf.parse(String.valueOf(obj[4]))));
					if (obj[5] != null)
						dto.setProjectEndDate(dateForm1.format(sdf.parse(String.valueOf(obj[5]))));
					if (obj[6] != null)
						dto.setEmpStartDate(dateForm1.format(sdf.parse(String.valueOf(obj[6]))));
					if (obj[7] != null)
						dto.setEmpEndDate(dateForm1.format(sdf.parse(String.valueOf(obj[7]))));
					if (obj[8] != null)
						dto.setAvailableHours(String.valueOf(obj[8]));
					if (obj[9] != null)
						dto.setHoursperWeek(String.valueOf(obj[9]));
					dto.setOrgWorkingHours(
							orgLocations.getAverageWorkingHours() * orgLocations.getWorkingDaysPerWeek());
					availableAssociatesDTOList.add(dto);

					List<AvailableAssociatesDTO> mapDTOList = map
							.get(String.valueOf(obj[10]) + "<<>>" + String.valueOf(obj[2])) == null ? new LinkedList<>()
									: map.get(String.valueOf(obj[10]) + "<<>>" + String.valueOf(obj[2]));
					mapDTOList.add(dto);
					map.put(String.valueOf(obj[10]) + "<<>>" + String.valueOf(obj[2]), mapDTOList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<Projects> getProjectsListByCanddateIdAndOrgId(String associatesId, Long orglocationId) {
		// TODO Auto-generated method stub
		return projectsDao.getProjectsListByCanddateIdAndOrgId(associatesId, orglocationId);
	}

	@Override
	public File availableAssociatereportReportListOfExcel(Map<String, List<AvailableAssociatesDTO>> object, User user,
			OrgLocations orgLocations) {
		File file = null;
		try {
			Workbook createExcelworkbook = new XSSFWorkbook();
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet createExcelsheet = createExcelworkbook
					.createSheet(orgLocations.getOrgLocationName() + "_" + dateFormat.format(new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetofWithHeader(createExcelsheet, createExcelworkbook);
			int column = 0;
			for (String key : object.keySet()) {
				writeRow = createExcelsheet.createRow(column + 1);
				org.apache.poi.ss.usermodel.Cell cell = writeRow.createCell(0);
				cell.setCellValue(key.split("<<>>")[1]);
				CellStyle cellStyle = createExcelworkbook.createCellStyle();
				org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
				cellStyle.setFont(font);
				font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
				cell.setCellStyle(cellStyle);
				column++;

				List<AvailableAssociatesDTO> availableAssociatesDTOList = object.get(key);
				for (AvailableAssociatesDTO reportList : availableAssociatesDTOList) {
					writeRow = createExcelsheet.createRow(column + 1);
					String isGlobalAccess = null;
					createExcelsheet = createXlsSheet(createExcelsheet, writeRow, reportList, orgLocations, user,
							isGlobalAccess);
					column++;
				}
				writeRow = createExcelsheet.createRow(column + 1);
				writeRow.createCell(0).setCellValue("");
				column++;
			}

			file = File.createTempFile(
					orgLocations.getOrgLocationName() + "_Available Associate Report_" + dateFormat.format(new Date()),
					".xlsx");
			file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return file;
	}

	private Sheet getExcelSheetofWithHeader(Sheet createExcelsheet, Workbook createExcelworkbook) {

		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String[] headers = Constants.AVAILABLE_ASSOCIATES_REPORT_EXPORT_HEADDERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 0);
		return createExcelsheet;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, AvailableAssociatesDTO reportList,
			OrgLocations orgLocations, User user, String isGlobalAccess) {

		if (null != reportList.getProjectName()) {
			writeRow.createCell(0).setCellValue(reportList.getProjectName());
		} else {
			writeRow.createCell(0).setCellValue("--");
		}
		createExcelsheet.setColumnWidth(0, 32 * 255);

		if (null != reportList.getProjectStartDate()) {
			writeRow.createCell(1).setCellValue(reportList.getProjectStartDate() + " " + "to" + " "
					+ (reportList.getProjectEndDate() != null ? reportList.getProjectEndDate() : "--"));
		} else {
			writeRow.createCell(1).setCellValue("--");
		}
		createExcelsheet.setColumnWidth(1, 22 * 255);

		if (null != reportList.getEmpStartDate()) {
			writeRow.createCell(2).setCellValue(reportList.getEmpStartDate() + " " + "to" + " "
					+ (reportList.getEmpEndDate() != null ? reportList.getProjectEndDate() : "--"));
		} else {
			writeRow.createCell(2).setCellValue("--");
		}
		createExcelsheet.setColumnWidth(2, 22 * 255);

		if (null != reportList.getAvailableHours()) {
			writeRow.createCell(3).setCellValue(reportList.getAvailableHours());
		} else {
			writeRow.createCell(3).setCellValue("--");
		}
		createExcelsheet.setColumnWidth(3, 7 * 255);

		if (null != reportList.getHoursperWeek()) {
			writeRow.createCell(4).setCellValue(reportList.getHoursperWeek().substring(0, 5));
		} else {
			writeRow.createCell(4).setCellValue("--");
		}
		createExcelsheet.setColumnWidth(4, 7 * 255);
		return createExcelsheet;
	}

	@Override
	public List<ProjectsDTO> getProjectListForProjectTimeEntriesReports(Long orgLocationsId, Long orgId,
			String isGlobalManager, Long userId, String type, String userRole) throws Exception {
		return projectsDao.getProjectListForProjectTimeEntriesReports(orgLocationsId, orgId, isGlobalManager, userId, type, userRole);
	}
	
	@Override
	public List<ProjectsDTO> getProjectListForResourceTimeEntriesReports(Long orgLocationsId, Long orgId,
			String isGlobalManager, Long userId, String type, String userRole) throws Exception {
		return projectsDao.getProjectListForResourceTimeEntriesReports(orgLocationsId, orgId, isGlobalManager, userId, type, userRole);
	}

}	



