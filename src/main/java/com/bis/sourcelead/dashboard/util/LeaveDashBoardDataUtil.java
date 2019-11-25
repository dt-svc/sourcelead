package com.bis.sourcelead.dashboard.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionsView;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OnboardTasksService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectPlanningNewResourceRequestService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.service.ProjectPlanningResourceRequestService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
=======
import com.bis.sourcelead.clockinout.service.ClockInOutConfigService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensesCount;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesApproverHierarchyService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.util.Calendar;

@Component
public class LeaveDashBoardDataUtil {
	
	@Autowired
	LeaveService leaveService;
	
	@Autowired

	private SessionFactory sessionFactory;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	OnboardTasksService  onboardTasksService;

	 private RequirementSubmissionDao requirementSubmissionDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;

	
	@Autowired
	RequirementService requirementService;
	
	@Autowired

	ProjectsService  projectsService;
	
	@Autowired
	ProjectBudgetService projectBudgetService;
	
	@Autowired
	ProjectPlanningRequestService projectPlanningRequestService;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	

	ExpensesApproverHierarchyService expensesApproverHierarchyService;


	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired

	CandidateOnBoardingService candidateOnBoardingService;
	
	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;
	
	@Autowired
	ProjectPlanningResourceRequestService  projectPlanningResourceRequestService;
	
	@Autowired
	ProjectPlanningNewResourceRequestService projectPlanningNewResourceRequestService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	ClockInOutConfigService clockInOutConfigService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
	/**
	 * Leave DashBoard Data 
	 * approved,rejected,pending and canceled leav count
	 * 
	 * @param commonDto
	 * @return
	 */
	
	public String getLeaveDashBoardData(CommonDataDto commonDto) {
		Map<String, String> map = new HashedMap();
		try {
			LeaveDTO leaveDto = leaveService.getAllEmployeesLeavesCounts(commonDto.getOrgLocation().getId(), commonDto.getUserId(), commonDto.getLoginUserRole(),commonDto.getOrgId());
			map.put("Requested", leaveDto.getEmployeeRequestedCounts());
			map.put("Rejected", leaveDto.getEmployeeRejectedCounts());
			map.put("Cancelled", leaveDto.getEmployeeCancelledCounts());
			map.put("Approved", leaveDto.getEmployeeApprovedCounts());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(map);
	}


	/**
	 * Getting projects Request for Dashboard view
	 * @param commonDto
	 * @return String
	 */
	public String getProjectRequests(CommonDataDto commonDto) {
		Map<String, String> map = new HashedMap();
		try {
			String assignedReqCount = "";
			String ResReqCount = "";
			int newResourceSize =0;
			int totalRequestCount = 0;
			String sizeofBudget = "";
			User user  = userService.getUserById(commonDto.getUserId());
			    List<Projects> projects = projectsService.getProjectByOrgLocationsIdAndUserId(commonDto.getOrgLocation().getId(), commonDto.getUserId());
			    for(Projects project : projects) {
				int projectPlanningRequestCount = projectPlanningRequestService.getProjectPlanningRequestCountByProjectId(project.getId());
			    totalRequestCount = projectPlanningRequestCount + totalRequestCount;
				List<ProjectPlanningNewResourceRequest> reqList = projectPlanningNewResourceRequestService.getNewResourceRequestListByApproverIdAndOrgLocationIdAndStatus(user.getId(), commonDto.getOrgLocation().getId(), commonDto.getLoginUserRole());
				newResourceSize = reqList.size();
				ResReqCount = String.valueOf(newResourceSize);
			    List<ProjectBudget>  projectBudget = 	projectBudgetService.getProjectBudgetListByOrgLocationId(user.getId(),commonDto.getOrgLocation().getId() , commonDto.getLoginUserRole());
				int budgetsize = projectBudget.size();
			    sizeofBudget = String.valueOf(budgetsize);
			}
			int associateReq = totalRequestCount - newResourceSize;
			assignedReqCount = String.valueOf(associateReq);
			map.put("Associate Requests", assignedReqCount);
			map.put("New Resource Requests", ResReqCount);
			map.put("Budget Resource Requests", sizeofBudget);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(map);
	}
	
	/**
	 * Get Assigned Projects Tasks for DashBoard View
	 * @param commonDataDto
	 * @return String
	 */
	public String getAssignedProjectTasks(CommonDataDto commonDataDto) {
		Map<String, String> map = new HashedMap();
		try {
			List<Projects> projects = projectsService.getProjectByOrgLocationsIdAndUserId(commonDataDto.getOrgLocation().getId(), commonDataDto.getUserId());
			int generalCount = 0;
			int userStoryCount = 0;
			int bugCount = 0;
			int epicCount = 0;
			int subTaskCount = 0;
			for(Projects pro : projects) {
			Set<ProjectTasks> tasks = pro.getProjectTasks();
			 generalCount = generalCount +  (int) tasks.stream().filter(p->"General".equals(p.getTaskTypes())).count();
			 userStoryCount = userStoryCount +  (int) tasks.stream().filter(p->"UserStory".equals(p.getTaskTypes())).count();
			 epicCount = epicCount + (int) tasks.stream().filter(p->"Epic".equals(p.getTaskTypes())).count();
			 bugCount = bugCount +  (int) tasks.stream().filter(p->"Bug".equals(p.getTaskTypes())).count();
			 subTaskCount = subTaskCount + (int) tasks.stream().filter(p->"SubTask".equals(p.getTaskTypes())).count();
			}
			
			map.put("General", String.valueOf(generalCount));
			map.put("UserStory", String.valueOf(userStoryCount));
			map.put("SubTask",String.valueOf(subTaskCount) );
			map.put("Bug", String.valueOf(bugCount));
			map.put("Epic",String.valueOf(epicCount));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(map);
	}
	
	/**
	 * Get Current Candiate On Boarding
	 * @param commonDataDto
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String getCandidateOnBoarding(CommonDataDto commonDataDto) {
		JSONArray Jsonarray = new JSONArray();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			
			String query="from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId where onboarding.orgLocationId.id =:orgLocationId order by onboarding.id DESC";
			Query qeury = session.createQuery(query).setLong("orgLocationId",  commonDataDto.getOrgLocation().getId());
			List<CandidateOnBoarding> candidateOnBoarding1 = qeury.setMaxResults(5).list();
			JSONObject Jsonobject = null;
					CandidateOnBoardingDTO candi = new CandidateOnBoardingDTO();
							for(CandidateOnBoarding candidateOnBoarding : candidateOnBoarding1) {
								Jsonobject = new JSONObject();
								String date = commonUtil.dateStrFromOrgDateFormatter(commonDataDto.getOrgLocation().getDateFormat(), candidateOnBoarding.getStartDate());
								Jsonobject.put("Name",candidateOnBoarding.getFirstName());
								Jsonobject.put("Submitted Documents",candidateOnBoarding.getDocumentsCount());
								Jsonobject.put("Accepted Documents",candidateOnBoarding.getDocumentsAcceptedCount());
								Jsonobject.put("Date",date);
								Jsonobject.put("Status",candidateOnBoarding.getCandidateOnBoardStatus());
								Jsonarray.add(Jsonobject);
							}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Jsonarray.toString();	
		}
	
	
	@Transactional
	public String getCandidateOFFBoarding(CommonDataDto commonDataDto) {
		JSONArray Jsonarray = new JSONArray();
		try {
			Session session = this.sessionFactory.getCurrentSession();
		String query="from CandidateOffBoarding offboarding left join fetch offboarding.orgLocationId where offboarding.orgLocationId.id =:orgLocationId order by offboarding.id DESC";
		Query qeury = session.createQuery(query).setLong("orgLocationId",  commonDataDto.getOrgLocation().getId());
		List<CandidateOffBoarding> candidateOffBoarding = qeury.setMaxResults(5).list();
			for(CandidateOffBoarding CandiOffBoarding : candidateOffBoarding) {
				JSONObject Jsonobject = new JSONObject();
				DateFormat Datetype = new SimpleDateFormat ("yyyy-MM-dd");
				if(CandiOffBoarding.getDateOfJoin()!=null) {
					String Startdate = Datetype.format(CandiOffBoarding.getDateOfJoin());
					Jsonobject.put("Start Date",Startdate);
				}
				else {
					Jsonobject.put("Start Date","");
				}
				String Effectivedate = Datetype.format(CandiOffBoarding.getEffectiveDate());
				String Enddate = Datetype.format(CandiOffBoarding.getEndDate());
				Jsonobject.put("Name",CandiOffBoarding.getFirstName());
				Jsonobject.put("Effective date",Effectivedate);
				Jsonobject.put("End date",Enddate);
				Jsonobject.put("Status",CandiOffBoarding.getOffBoardStatus());
				Jsonarray.add(Jsonobject);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Jsonarray.toString();	
		}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public String getCliendbycontacts(CommonDataDto commonDataDto) {
		JSONArray Jsonarray = new JSONArray();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			User user = userService.getUserById(commonDataDto.getUserId());
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),commonDataDto.getOrgLocation().getId());
			ArrayList<OrgSpoc> finalList = new ArrayList<OrgSpoc>();
			List<OrgSpoc> spocsList = null;
			if (isadmin) {
				String MainQuery = "from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status = '1' and (spoc.spocStatus = 'Active' OR spoc.spocStatus = 'Contracted') order by spoc.id DESC";
	            Query query= session.createQuery(MainQuery).setLong("orgLocationId", commonDataDto.getOrgLocation().getId()).setString("relationType", "Vendor");
	            spocsList = query.setMaxResults(5).list();
	            finalList.addAll(spocsList);
			}else{
				String Mainquery = "from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where  (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType ))  and spoc.status = '1' and (spoc.spocStatus = 'Active' OR spoc.spocStatus = 'Contracted') order by spoc.id DESC";
	            Query query= session.createQuery(Mainquery).setLong("orgLocationId", commonDataDto.getOrgLocation().getId()).setLong("loginUserId", user.getId()).setString("relationType", "Vendor");
	            spocsList = query.setMaxResults(5).list();
	            finalList.addAll(spocsList);
			}
			for(OrgSpoc orgSpoc : spocsList ) {
				JSONObject Jsonobject = new JSONObject();
				Jsonobject.put("Contact Name",orgSpoc.getClientUser().getFirstName());
				Jsonobject.put("Email", orgSpoc.getClientUser().getEmail());
				Jsonobject.put("Mobile Phone",orgSpoc.getClientUser().getMobilePhone());
				Jsonobject.put("Owner Name",orgSpoc.getOwnerUser().getFirstName());
				Jsonarray.add(Jsonobject);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Jsonarray.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public String getCandidatesOnBoarding(CommonDataDto commonDataDto) {
		JSONArray Jsonarray = new JSONArray();
		try {
		Session session = this.sessionFactory.getCurrentSession();
		 String Query = "from RequirementSubmissionsView rsv where orgLocationId =:orgLocationId order by rsv.id DESC" ; 
		 Query query= session.createQuery(Query).setLong( "orgLocationId", commonDataDto.getOrgLocation().getId());
		 List<RequirementSubmissionsView>  submissionList = query.setMaxResults(5).list();
		for(RequirementSubmissionsView requirementSubmissionsView : submissionList) {
			JSONObject Jsonobject = new JSONObject();
			Date date1=new SimpleDateFormat("dd MM yyyy").parse(requirementSubmissionsView.getOfferJoinDate());
			DateFormat Datetype = new SimpleDateFormat (commonUtil.dateStrFromOrgDateFormatter(commonDataDto.getOrgLocation().getDateFormat(), date1));
			String Startdate = Datetype.format(date1);
					Jsonobject.put("Date Of Joining",Startdate);
			        Jsonobject.put("Name",requirementSubmissionsView.getCandidateName());
 					Jsonobject.put("Email",requirementSubmissionsView.getCandidateEmail());
					Jsonobject.put("Position",requirementSubmissionsView.getPositionType());
					Jsonobject.put("Title",requirementSubmissionsView.getRequirementName());
					Jsonarray.add(Jsonobject);
				}
			}
		
		
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return Jsonarray.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public String getCandidateReadyForInvoice(CommonDataDto commonDataDto) {
		JSONArray Jsonarray = new JSONArray();
		try {
			User user = userService.getUserById(commonDataDto.getUserId());
			List<RequirementSubmissionsDTO> listOfApplicants = new ArrayList<RequirementSubmissionsDTO>();
			Session session = this.sessionFactory.getCurrentSession();
			List<RequirementSubmission> finalList = null;
			String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.submissionStatus ='JOINED' and rs.invoiceGenerated IS NULL and (req.jobType ='Full Time' or req.jobType ='W-2') and req.isClientJob = 'Y' order by rs.id DESC";
			Query finalQuery = session.createQuery(query).setParameter("locationId", commonDataDto.getOrgLocation().getId());
			finalList =finalQuery.setMaxResults(5).list();
			for(RequirementSubmission reqSub : finalList ) {
				RequirementSubmissionsDTO requirementSubmissionsDTO = new RequirementSubmissionsDTO();
				JSONObject Jsonobject = new JSONObject();
				Jsonobject.put("Name ",reqSub.getCandidate().getFullName());
				Jsonobject.put("Job Title ",reqSub.getRequirement().getJobTitle());
				Jsonobject.put("ClientOrVendor",reqSub.getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
				String term = reqSub.getRequirement().getClientId().getPaymentTerm();
				term = term.replaceAll("\\D", "");
				int result = Integer.parseInt(term);
			Date generatedInvoiceDate = CalendarUtil.addDaysForGenerateInvoice(reqSub.getJoinedDate(), result);
			DateTime dtOrg = new DateTime(generatedInvoiceDate);
			Date locationDate = commonUtil.orgLocationCommonDate(new Date(),commonDataDto.getOrgLocation());
				Jsonobject.put("Join Date",commonUtil.dateStrFromOrgDateFormatter(commonDataDto.getOrgLocation().getDateFormat(),reqSub.getJoinedDate()));
				Jsonobject.put("Invoice Date",commonUtil.dateStrFromOrgDateFormatter(commonDataDto.getOrgLocation().getDateFormat(), dtOrg.toDate()));
				Jsonarray.add(Jsonobject);
				
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Jsonarray.toString();
}
	

	
	@SuppressWarnings("unchecked")
	public String getSubmitToClientCandidatesDashBoardData(CommonDataDto commonDto) {
		 JSONArray jsonArray = new JSONArray();
		try {
		RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO = new RequirementSubmissionHistoryDTO();
		User user = userService.getUserById(commonDto.getUserId());
		user.setId(commonDto.getUserId());
		requirementSubmissionHistoryDTO.setSubmissionStatus("SUBMIT_TO_CLIENT");
		requirementSubmissionHistoryDTO.setTabType("previous");
		requirementSubmissionHistoryDTO.setSearchValue("");
		requirementSubmissionHistoryDTO.setSearchType("");
			DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String currentDate = commonUtil.dateStrFromDate(new Date());
			Date endTime =  commonUtil.orgLocationDateFormat(commonDto.getOrgLocation().getDateFormat(),currentDate);
			String customEndTimeStr = TZ_DF_CED.format(endTime);  
			Date endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
			String userType = null;
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(commonDto.getLoginUserRole()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDto.getLoginUserRole())){
				userType = Constants.ADMIN;
			} 
		
			   ResponseList<RequirementSubmissionHistory> toDayReqApplicantscount =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, commonDto.getOrgLocation().getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,null,endUTCDate,commonDto.getOrgLocation(),Constants.Dashboard);
			  
			   ArrayList<RequirementSubmissionHistory> subList =  (ArrayList<RequirementSubmissionHistory>) toDayReqApplicantscount.getData();
			  
			  
			   for(RequirementSubmissionHistory req : subList) {
				   JSONObject jsonObj = new JSONObject();
				   jsonObj.put("Applicant Name",req.getSubmissionId().getCandidate().getFirstName());
				   jsonObj.put("Job", req.getSubmissionId().getRequirement().getJobTitle());
				   jsonObj.put("Client", req.getSubmissionId().getRequirement().getClientName());
				   jsonObj.put("Source",req.getSubmissionId().getCreatedUser());
				   jsonArray.add(jsonObj);
		     }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public String getInterviewDashBoardData(CommonDataDto commonDataDto) {
		 JSONArray jsonArray = new JSONArray();
		try {
		RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO = new RequirementSubmissionHistoryDTO();
		User user = userService.getUserById(commonDataDto.getUserId());
		user.setId(commonDataDto.getUserId());
		requirementSubmissionHistoryDTO.setSubmissionStatus("INTERVIEW");
		requirementSubmissionHistoryDTO.setTabType("previous");
		requirementSubmissionHistoryDTO.setSearchValue("");
		requirementSubmissionHistoryDTO.setSearchType("");
			DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String currentDate = commonUtil.dateStrFromDate(new Date());
			Date endTime =  commonUtil.orgLocationDateFormat(commonDataDto.getOrgLocation().getDateFormat(),currentDate);
			String customEndTimeStr = TZ_DF_CED.format(endTime);  
			Date endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
			String userType = null;
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())){
				userType = Constants.ADMIN;
			} 
		
			   ResponseList<RequirementSubmissionHistory> toDayReqApplicantscount =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, commonDataDto.getOrgLocation().getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,null,endUTCDate,commonDataDto.getOrgLocation(),Constants.Dashboard);
			  
			   ArrayList<RequirementSubmissionHistory> subList =  (ArrayList<RequirementSubmissionHistory>) toDayReqApplicantscount.getData();
			  
			   for(RequirementSubmissionHistory req : subList) {
				   JSONObject jsonObj  = new JSONObject();
				   jsonObj.put("Applicant Name",req.getSubmissionId().getCandidate().getFirstName());
				   jsonObj.put("Job", req.getSubmissionId().getRequirement().getJobTitle());
				   jsonObj.put("Client", req.getSubmissionId().getRequirement().getClientName());
				   jsonObj.put("Source",req.getSubmissionId().getCreatedUser());
				   jsonArray.add(jsonObj);
				   
		     }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toJSONString();
	}
	/**
	 * converting map to String  
	 * @param map
	 * @return String
	 */
	public String getExpensesDashBoardData(CommonDataDto commonDataDto) {
		Map<String, String> map = new HashedMap();
		try {
		ExpensesCount expensesCount = new ExpensesCount();
		expensesCount = expensesApproverHierarchyService.getExpensesCountByUserIdAndOrgLocationIdForApprover(commonDataDto.getUserId(), commonDataDto.getOrgLocation().getId(),"isGlobalAccess");
		map.put("Approved", expensesCount.getEmpExpensesApprovedCount().toString());
		map.put("Rejected", expensesCount.getEmpExpensesRejectedCount().toString());
		map.put("Waiting", expensesCount.getEmpExpensesSubmittedForApprovalCount().toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return getJsonString(map);
	}
	
	
	@SuppressWarnings("unchecked")
	private String getJsonString(Map<String, String> map) {
		String convertedString ="";
		try {
			JSONObject jsonObj = new JSONObject();
			map.forEach((key, value) -> {
				jsonObj.put(key, value);
			});
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(jsonObj);
			 convertedString = jsonArray.toString().replace("\"", "\\\"");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return convertedString;
	}


	@SuppressWarnings("unchecked")
	public String getJobFairDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		
		try {
			JobFairDTO jobFairDTO = new JobFairDTO();
			User user = userService.getUserById(commonDataDto.getUserId());
			String[] tableColumns = {"generatedId" ,"name", "startDate", "endDate", "noOfJobs", "", "", "", "status", "id"};
			/*jobFairDTO.setSortColumn("id");
			jobFairDTO.setSortOrder("desc");*/
			
			int sortColumnIndex = 9; //by default sorting using id
			boolean sortColumnOrder = false; //by default descending order
			if(jobFairDTO.getOrder() != null && !jobFairDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(jobFairDTO.getOrder().get(0).get("column"));
				sortColumnOrder = !jobFairDTO.getOrder().get(0).get("dir").equalsIgnoreCase("desc");					
			}
			jobFairDTO.setSortColumn(tableColumns[sortColumnIndex]);
			jobFairDTO.setSortOrder(sortColumnOrder ? "asc" : "desc");
			
			jobFairDTO.setUserId(user.getId());
			jobFairDTO.setOrgLocationId(commonDataDto.getOrgLocation().getId());
			
			if("Admin".equalsIgnoreCase(Constants.ADMIN)) {
				jobFairDTO.setAdminUser(true);
			}
			
			ResponseList<JobFairDTO> response = requirementService.getJobFairsList(jobFairDTO, false);
			   
			   for(JobFairDTO job  : response.getData()) {
			   JSONObject jsonObj = new JSONObject();
			   jsonObj.put("Title",job.getName());
			   jsonObj.put("offered", job.getOfferedCount());
			   jsonObj.put("Applicants", job.getApplicantCount());
			   jsonArray.add(jsonObj);
				   
		     }

		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}

	@SuppressWarnings("unchecked")
	public String getApplicantPremissionDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
			User user = userService.getUserById(commonDataDto.getUserId());
			String requestSent= "requestSent";
			List<RequirementSubmission>  requirementSubmissionList = requirementSubmissionService.getApplicantsByStatus(commonDataDto.getOrgLocation().getId(), user,requestSent);
			for(RequirementSubmission req  : requirementSubmissionList) {
				JSONObject jsonObj = new JSONObject();
				   jsonObj.put("Applicant",req.getCandidateName());
				   jsonObj.put("Job", req.getRequirement().getJobTitle());
				   jsonObj.put("Team member", req.getCreatedUser());
				   jsonArray.add(jsonObj);
					   
			     }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}

	@SuppressWarnings("unchecked")
	public String getAssignedJobsDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
		User user = userService.getUserById(commonDataDto.getUserId());
		String userType = null;
		List<Requirement> requirementList = null;
		if (Constants.ROLE_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())
				|| Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())) {
			userType = Constants.ADMIN;
			requirementList = clockInOutConfigService.getAssignedRequirementListByOrgLocationIDAndUserId(commonDataDto.getOrgLocation().getId(), userType, user.getId());
		}
		else {
			requirementList = clockInOutConfigService.getAssignedRequirementListByOrgLocationIDAndUserId(commonDataDto.getOrgLocation().getId(), userType, user.getId());
		}
		for(Requirement assign :requirementList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("Job", assign.getJobTitle());
			jsonObj.put("Applicants", assign.getTotalApplicants().toString());
			jsonObj.put("Generated Id", assign.getGeneratedId());
			jsonArray.add(jsonObj);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}
	@SuppressWarnings("unchecked")
	public String getJobsPipeLineDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
			User user = userService.getUserById(commonDataDto.getUserId());
			if(user != null) {
					String userCode = user.getUserCode();
					String userType = null;
					List<Requirement>   requirementList = null;
					String dayString = "";
					int walkInDay = -1;
					int selectedDay = walkInDay;
					Boolean isWalkIn = false;
					if(isWalkIn) {
						Calendar c = Calendar.getInstance();
						if(walkInDay >= 0) {
							selectedDay = walkInDay;
							c.set(Calendar.DAY_OF_WEEK, walkInDay);
						} else {
							selectedDay = c.get(Calendar.DAY_OF_WEEK);
						}
						dayString = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(c.getTime());
					}
				
					if(Constants.ROLE_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())){
						userType = Constants.ADMIN;
						requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(commonDataDto.getOrgLocation().getId(), userType, user.getId(), userCode, isWalkIn, dayString);
					}else{
						requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(commonDataDto.getOrgLocation().getId(), userType, user.getId(), userCode, isWalkIn, dayString);
					}
					// your code here...
					 
					for(Requirement requirement : requirementList){
						 RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirement.getId(), user, commonDataDto.getOrgLocation(), "ALL");						;
			    		JSONObject jsonObj = new JSONObject();
			    			jsonObj.put("Job", requirement.getJobTitle());
			    			jsonObj.put("GeneratedId", requirement.getGeneratedId());
			    			jsonObj.put("Applicants", rsCount.getApplicantsCount());
			    			jsonObj.put("Shortlist",rsCount.getShortlistCount());
			    			jsonObj.put("Interview",rsCount.getInterviewCount());
			    			jsonObj.put("Submitted", rsCount.getSubmitToClientCount());
			    			jsonObj.put("Offered", rsCount.getOffersCount());
			    			jsonObj.put("Joined", rsCount.getJoinedCount());
			    			jsonArray.add(jsonObj);
						}
						
					
		
		}
		}
			catch(Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();

}

}
