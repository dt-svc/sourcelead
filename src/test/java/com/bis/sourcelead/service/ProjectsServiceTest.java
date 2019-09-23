package com.bis.sourcelead.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ProjectsServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceTest.class);

	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	/**
	 * line 205 commented
	 */
	/**
	 * this method for save project
	 * @throws Exception
	 */
//	@Test
	public void saveProject() throws Exception{
		
		Projects projects = new Projects();
		
		User superiorUser = new User();
		superiorUser.setId(3L);
		projects.setSuperiorUser(superiorUser);
		projects.setProjectName("XYZ");
		projects.setProjectStatus(Project_Status.getNameByValue("Started"));
		projects.setDescription("sourcelead desc");
		projects.setStatus("0");
		projects.setStartDate(new Date());
		projects.setEndDate(new Date());
		projects.setTimesheetFrequency("Monthly");
		projects.setCreatedDate(new Date());
		projects.setCreatedBy("prasad");
		projects.setUpdatedDate(new Date());
		projects.setUpdatedBy("Raj");
		
		projectsService.saveProject(projects);
		logger.info("saveProject project added successfully");
	}

	/**
	 * this method for updateProject
	 * @throws Exception
	 */
	//@Test
	public void updateProject() throws Exception{
		Projects projects = new Projects();
		projects.setId(1L);
		User superiorUser = new User();
		superiorUser.setId(1L);
		projects.setSuperiorUser(superiorUser);
		
		projects.setProjectName("XYZ Updated");
		projects.setDescription("project desc");
		projects.setProjectStatus(Project_Status.getNameByValue("Started"));
		projects.setStatus("0");
		projects.setStartDate(new Date());
		projects.setEndDate(new Date());
		projects.setTimesheetFrequency("Monthly");
		projects.setCreatedDate(new Date());
		projects.setCreatedBy("prasad");
		projects.setUpdatedDate(new Date());
		projects.setUpdatedBy("Yaswanth");
		
		projectsService.updateProject(projects);
	}
	
	/**
	 * this method for get project by id
	 * @throws Exception
	 */
	//@Test
	public void getProjectById() throws Exception{
		Projects getProjectbyid = projectsService.getProjectById(1L);
		logger.info("getProjectById added successfully"+getProjectbyid);
	}
	
	
	/**
	 * this method for get all projects
	 * @throws Exception
	 */
	
	//@Test
	public void getallProjects() throws Exception{
		Collection<Projects> allprojects = projectsService.getAllProjects();
		logger.info("getProjects added successfully"+allprojects.size());
	}
	
	//@Test
    public void getPrimaryProjectByOrgLocationsId() throws Exception{
        System.out.println("sadasdsadasdsadsadsa");
        Long orgLocationsId= 3l;
        projectsService.getPrimaryProjectByOrgLocationsId(orgLocationsId);
        logger.info("getPrimaryProjectByOrgLocationsId added successfully");
        
    }
    
   // @Test
    public void getProjectByOrgLocationsId() throws Exception{
        Long orgLocationsId= 3l;
        List<Projects> projects = projectsService.getProjectByOrgLocationsId(orgLocationsId);
        logger.info("project list in getProjectByOrgLocationsId"+projects.size());
    }
	
   // @Test
	public void getAllLoginUserProjectsList() throws Exception{
		List<Long> projectIds=  new ArrayList<Long>();
		projectIds.add(1L);
		projectIds.add(2l);
		Long orgLocationId = 3L;
		List<Projects> projectsList = projectsService.getAllLoginUserProjectsList(projectIds,orgLocationId);
		
		logger.info("projects list in getAllLoginUserProjectsList"+projectsList.size());
	}
	//@Test
	public void getAllClientProjectsByOrgRelationId() throws Exception{
		Collection<Projects> AllClientProjects = projectsService.getAllClientProjectsByOrgRelationId(1l,1l);
		logger.info("projects list in getAllLoginUserProjectsList"+AllClientProjects.size());
	}
	//@Test
	public void updateStartDateOfProjectId() throws Exception{
		Integer updateStartDate = projectsService.updateStartDateOfProjectId(new Date(),1l);
		logger.info("updateStartDate addded successfully"+updateStartDate.intValue());
	}
	//@Test
	public void updateEndDateOfProjectId() throws Exception{
		Integer updateEndDate = projectsService.updateEndDateOfProjectId(new Date(),1l);
		logger.info("updateEndDate addded successfully"+updateEndDate.intValue());
	}

	//@Test
	public void getApprovarAssignedProjects() throws Exception{
		List<Long> updateEndDate = projectsService.getApprovarAssignedProjects(1l,1l);
		logger.info("getApprovarAssignedProjects addded successfully"+updateEndDate.size());
	}
	//@Test
	public void getProjectByProjectSequenceNumber() throws Exception{
		Projects updateEndDate = projectsService.getProjectByProjectSequenceNumber("301");
		logger.info("getProjectByProjectSequenceNumber addded successfully"+updateEndDate);
	}
//	@Test
	public void getProjectsBasedOnClientsAndStatusSelectionInFilters() throws Exception{
		Long userId = 1l;
		String clients ="1";
		String status ="STARTED";
		Long orgLocationId =3l;
		List<Projects> ProjectsBasedOnClients = projectsService.getProjectsBasedOnClientsAndStatusSelectionInFilters(userId,clients,status,orgLocationId);
		logger.info("getProjectsBasedOnClientsAndStatusSelectionInFilters addded successfully"+ProjectsBasedOnClients);
	}
	//@Test
	public void getProjectByOrgLocationsIdAndUserId() throws Exception{
		List<Projects> ProjectByOrgLocationsId = projectsService.getProjectByOrgLocationsIdAndUserId(1l,1l);
		logger.info("getProjectByOrgLocationsIdAndUserId addded successfully"+ProjectByOrgLocationsId.size());
	}
	//@Test
	public void getProjectByIdAndLoginOrgId() throws Exception{
		Projects ProjectByIdAndLogin = projectsService.getProjectByIdAndLoginOrgId(1l,1l);
		logger.info("getProjectByIdAndLoginOrgId addded successfully"+ProjectByIdAndLogin);
	}
	//@Test
	public void getUserAssignedProjectsCount() throws Exception{
		Integer UserAssignedProjectsCount = projectsService.getUserAssignedProjectsCount(1l,1l);
		logger.info("getUserAssignedProjectsCount addded successfully"+UserAssignedProjectsCount.intValue());
	}
	

	/**
	 * test case Weekly base Emails and Notification
	 * @throws Exception
	 */
	//@Test
	public void getAllUserTimeSheetSubmissionStatus() throws Exception{
		HashSet<UserRole> usrRoleList = userRoleService.getAllUserTimeSheetSubmissionStatusCurrentWeek();
		for(UserRole userRole : usrRoleList){
			if("Admin".equalsIgnoreCase(userRole.getUserType()))
				continue;
//			HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(userRole.getUser().getId(),userRole.getOrgLocations().getId());
			//projectsService.userEmailNotificationForTimeSheetSubmission(user,timesheetSubmissions);
			logger.info("getPreviousWeekTimeShhetSubmissionStatus addded successfully"+usrRoleList.size());
			
		}
	}
	/**
	 * @author Rajashekar
	 * Junit test for employess Manager
	 * @throws Exception
	 */
	//@Test
	public void getPreviousWeekTimeShhetSubmissionStatus() throws Exception{
		HashSet<ProjectMembers> ProjectMembersList  = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.WEEKLY,1l);
		logger.info("getPreviousWeekTimeShhetSubmissionStatus addded successfully"+ProjectMembersList.size());
		if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
			for(ProjectMembers projectMember : ProjectMembersList){
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjectClientsId().getOrgLocationsId().getId());
				if("Admin".equalsIgnoreCase(userRole.getUserType()))
					continue;
					String level ="";
					DateFormat format = Constants.DF_STD_FORMAT;
					String dateFormart = "MM-dd-yyyy";
					Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember,projectMember.getProjectClientsId().getOrgLocationsId(),"scheduler");
					LinkedHashMap<String,String> finalTFMap = new LinkedHashMap<>();
					if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
						for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
						    String key = entry.getKey();
						    String value =entry.getValue();
						    Date frequencyStartDate = format.parse(key.replaceAll("/", "-"));
							Date frequencyEndDate = format.parse(value.replaceAll("/", "-"));
							
							Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
							currentCal.setLenient(false);
							currentCal.setTime(new Date());
							Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "FRI",dateFormart);
							
				             Boolean flag = true;
				            /* if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentMonday.getTime()) <-2){
				            	 flag = false;
				             }*/
							
						    if(submissionApproverHierarchyService.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMember.getMasterCandidateId().getId(),projectMember.getProjects().getId()) && (flag)){
						    	finalTFMap.put(key, value);
							}
						}
						
					}
					if(null != finalTFMap && !finalTFMap.isEmpty()){
						projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),finalTFMap,projectMember.getProjects(),level);	
						logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
					}
			}
		}
		
	}
	
	
	//@Test
	public void getPreviousWeekTimeShhetSubmissionStatusToLevel2() throws Exception{
		HashSet<ProjectMembers> ProjectMembersList  = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.WEEKLY,1l);
		logger.info("getPreviousWeekTimeShhetSubmissionStatus addded successfully"+ProjectMembersList.size());
		Map<UserRole,HashSet<TimesheetSubmissions>> timesheetSubmissionsMap = new HashMap<>();
		if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
			for(ProjectMembers projectMember : ProjectMembersList){
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
				if("Admin".equalsIgnoreCase(userRole.getUserType()))
					continue;
				HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(projectMember.getId(),userRole.getOrgLocations().getId(),userRole.getUser().getId());
				timesheetSubmissionsMap.put(userRole, timesheetSubmissions);
				//projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),timesheetSubmissions);
				logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
			}
			int level = 2;
//			projectsService.userEmailNotificationForTimeSheetSubmissionToManagers(timesheetSubmissionsMap,level);
		}
	}

	/**
	 * @author Rajashekar
	 * Junit test for level2 Manager
	 * @throws Exception
	 */
	//@Test
	public void sendMailToLevel2Manager() throws Exception{
		logger.info("******************  Notification Job Scheduler WednesDay of every week of the month started..! **********");
		try {
			
			List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
			HashSet<TimesheetSubmissions> timesheetSubmissions = null;
			for (OrgLocations orgLoc : orgLocationsList) {
				ProjectApproverHierarchy projectApproverHierarchy = null;
				User user = null;
				StringBuilder tempHTML = new StringBuilder();
				int count=0;
				tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
				
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLoc.getId());
				
				List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
				if(count==0){	
					 tempHTML.append("<tr>");
						        tempHTML.append("<th> Employee Name </th>");
						        tempHTML.append("<th> Frequency </th>");
								tempHTML.append("<th> Project Name </th>");
								tempHTML.append("<th> Manager Name </th>");
								tempHTML.append("</tr>");
								count++;
					}
				
				for (Projects projects : projectsList) {
					
					Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projects.getId());
					for (ProjectMembers projectMembers : projectMembersList) {
						timesheetSubmissions = timesheetSubmissionsService.getWeekFrequencyByProjMemId(projectMembers.getId());        
						  if(timesheetSubmissions !=null){
					          TimesheetSubmissions ts = timesheetSubmissions.iterator().next();
					          List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectIdForManager(ts.getProjectMembers().getProjects().getId(), projectMembers.getEmployee().getUser().getId(),2);
					           if(projectApproverHierarchyList.size() > 0){
					        	   projectApproverHierarchy = projectApproverHierarchyList.get(0);
					        	   if(projectApproverHierarchy.getLevel() == 2){
					        		   user = projectApproverHierarchy.getApproverUserId();
					        	   }
					           }
					          LinkedList<String> frequency = new LinkedList<>();
								if(timesheetSubmissions != null && !timesheetSubmissions.isEmpty()){
									if(projectApproverHierarchyList.size() == 0){
										 tempHTML.append("<tr>");
										 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
											 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
										 }else{
											 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
										 }
										 
										 for(TimesheetSubmissions ts1 :timesheetSubmissions){
											 frequency.add("<b>"+Constants.DF_STD_FORMAT.format(ts1.getStartDate())+" To "+Constants.DF_STD_FORMAT.format(ts1.getEndDate()));
										   }
									    if(frequency != null && !frequency.isEmpty()){
										 tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
									     }
										  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
										  ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
										  tempHTML.append("<td>"+managerProjectApproverHierarchy.getApproverUserId().getFirstName()+"</td>");
										 tempHTML.append("</tr>");
									
									}
								}
						}
						
						        
							}
					}
				tempHTML.append("</table> ");
				if(user !=null && timesheetSubmissions!=null){
					projectsService.userEmailToAdmin(tempHTML,orgLocations,user);
				}
				}
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	
	/**
	 * @author Rajashekar
	 * Junit test for level1 Manager
	 * @throws Exception
	 */
	//@Test
	public void sendMailToLevel1Manager() throws Exception{

		logger.info("******************  Notification Job Scheduler MONDAY of every week of the month started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			Map<String,String>  timesheetFrequencyMap = null;
			DateFormat format = Constants.DF_DMY;
			for (Org org : orgList) {
					StringBuilder tempHTML = new StringBuilder();
					int count=0;
					tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
					
					List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
					for (OrgLocations ol : orgLocations) {
						     if(count==0){	
							            tempHTML.append("<tr>");
								        tempHTML.append("<th> Employee Name </th>");
								        tempHTML.append("<th> Frequency </th>");
										tempHTML.append("<th> Project Name </th>");
										/*tempHTML.append("<th> Manager Name </th>");*/
										tempHTML.append("</tr>");
										count++;
							  }
						     
						     List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(ol.getId(), Constants.BI_WEEKLY, Constants.LEVEL1);
						   
						     for (ProjectApproverHierarchy pah : projectApproverHierarchyList) {
						    	
						    	 HashSet<ProjectMembers> projectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(Constants.BI_WEEKLY,pah.getApproverUserId().getId(),1L);
								
						    	 if(null != projectMembersList && !projectMembersList.isEmpty()){
									for (ProjectMembers projectMembers : projectMembersList) {
										timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,projectMembers.getProjectClientsId().getOrgLocationsId(),"approverScheduler");
										  if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
										            LinkedList<String> frequency = new LinkedList<>();
													 tempHTML.append("<tr>");
													 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
														 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
													 }else{
														 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
													 }
													 
													 for(Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
														    String key = entry.getKey();
														    String value =entry.getValue();
															
														    Date frequencyEndDate = format.parse(value.replaceAll("/", "-"));
															Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
															currentCal.setLenient(false);
															currentCal.setTime(CommonUtil.currentDate());
															
												             Boolean flag = true;
												             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 ){
												            	 flag = false;
												             }
												             
												             if(flag){
												            	 frequency.add("<b>"+key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-"));
												             }
													 }
												     if(frequency != null && !frequency.isEmpty()){
													    tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
												     }
													  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
													 /* ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
													  tempHTML.append("<td>").append(managerProjectApproverHierarchy.getApproverUserId().getFullName()).append("</td>");*/
													  tempHTML.append("</tr>");
										          }
									       }	
									}
						    	 tempHTML.append("</table> ");
									if(pah.getApproverUserId() !=null && null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
									   projectsService.userEmailToAdmin(tempHTML,ol,pah.getApproverUserId());
									}
							}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Rajashekar
	 * Junit test for Admin 
	 * @throws Exception
	 */
	@Test
	public void sendMailAdmin() throws Exception{
		logger.info("******************  Notification Job Scheduler FRIDAY of every week of the month started..!For Admin **********");
		try {List<Org> orgList = orgService.getOrgs();
		Map<String,String>  timesheetFrequencyMap = null;
		DateFormat format = Constants.DF_DMY;
		for (Org org : orgList) {
				StringBuilder tempHTML = new StringBuilder();
				int count=0;
				tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
				
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				for (OrgLocations ol : orgLocations) {
					     if(count==0){	
						            tempHTML.append("<tr>");
							        tempHTML.append("<th> Employee Name </th>");
							        tempHTML.append("<th> Frequency </th>");
									tempHTML.append("<th> Project Name </th>");
									tempHTML.append("<th> Manager Name </th>");
									tempHTML.append("</tr>");
									count++;
						   }
					    	
					    	 HashSet<ProjectMembers> projectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.SEMI_MONTHLY,1l);
							
					    	 if(null != projectMembersList && !projectMembersList.isEmpty()){
								for (ProjectMembers projectMembers : projectMembersList) {
									timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,projectMembers.getProjectClientsId().getOrgLocationsId(),"approverScheduler");
									  if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
									            LinkedList<String> frequency = new LinkedList<>();
												 tempHTML.append("<tr>");
												 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
												 }else{
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
												 }
												 
												 for(Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
													    String key = entry.getKey();
													    String value =entry.getValue();
													    
														Date frequencyEndDate = format.parse(value.replaceAll("/", "-"));
														Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
														currentCal.setLenient(false);
														currentCal.setTime(CommonUtil.currentDate());
														
											             Boolean flag = true;
											             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2){
											            	 flag = false;
											             }
											             if(flag){
											            	 frequency.add("<b>"+key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-"));
											             }
													    
												 }
											     if(frequency != null && !frequency.isEmpty()){
												    tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
											     }
												  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
												  Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectMembers.getProjects().getId());
												   
												  StringBuilder projectStringBuilder=new StringBuilder();
												  for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
													    if(StringUtils.isNoneEmpty(projectStringBuilder.toString())){
													    	projectStringBuilder.append(" , ");
													    }
												    	if(approverHierarchy.getLevel()==1){
												    		projectStringBuilder.append(approverHierarchy.getApproverUserId().getFullName()).toString();
												    	}else if(approverHierarchy.getLevel()==2){
												    		projectStringBuilder.append(approverHierarchy.getApproverUserId().getFullName()).toString();
												    	}
												    }
												    
												  
												 // ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
												  tempHTML.append("<td>").append(projectStringBuilder).append("</td>");
												  tempHTML.append("</tr>");
									          }
								       }	
								}
					    	 tempHTML.append("</table> ");
								if(null != ol.getUser() && null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
								   projectsService.userEmailToAdmin(tempHTML,ol,ol.getUser());
								}
				  }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
    
		
	}

	//@Test
	public void saveAllProjects() throws Exception{
		List<Projects> projectsList=new ArrayList<>();
		Projects pro = new Projects();
		pro.setProjectName("java");
		pro.setCreatedBy("shivayogi");
		Projects pro1 = new Projects();
		pro1.setProjectName("java");
		pro1.setCreatedBy("shivayogi");
		projectsList.addAll(projectsList);
		projectsService.saveAllProjects(projectsList);
		logger.info("getDeleteProject ");
	}
	
	/*@Test
	public void storeProject() throws Exception{
			JSONObject jsonObj= new JSONObject();
			User user= new User();
			user.setId(1l);
			user.setUserCode("JAN2009");
			jsonObj.put("ProjectSequeneNumber", "1");
			jsonObj.put("ProjectName", "SQLLite");
			jsonObj.put("ProjectDescription", "Hi hello whats up");
			jsonObj.put("StartDate", "2006-02-09");
			jsonObj.put("EndDate", "2006-05-09");
			OrgLocations loginOrgLocation=new OrgLocations();
			loginOrgLocation.setId(1l);
			String loginOrgCode="2oo6IND";
			projectsService.storeProject(jsonObj, user, loginOrgLocation, loginOrgCode);
			logger.info("Project saved ");
		}*/
	
		

	
	/**
	 * this method for delete project by id
	 * @return
	 * @throws Exception
	 */
	//@Test
	public void getDeleteProject() throws Exception{
		Boolean isDeleted = projectsService.deleteProjectById(1L);
		logger.info("getDeleteProject "+isDeleted.booleanValue());
	}
	
	
}
