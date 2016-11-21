package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Repository
public class ProjectsDaoImpl implements ProjectsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	

    @Autowired
	private ProjectApproverHierarchyDao projectApproverHierarchyDao;
    
    @Autowired
   	private UserRoleDao userRoleDao;
    
    @Autowired
    private EmailScenarioService emailScenarioService;
    
    @Autowired
    private EmailScenerioUtil emailScenerioUtil;
    
    @Autowired
    private UserService userService;
    
    @Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
    
    @Autowired
    
    private ProjectsService projectsService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDaoImpl.class);

	/**
	 * This method for save project
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects saveProject(Projects projects) throws Exception {
		logger.debug("In Projects Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.save(projects);
		}catch(Exception exception){
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projects;
	}

	/**
	 * This method for update project
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In Projects Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projects != null){
				session.saveOrUpdate(projects);
				logger.info("Record updated successfully..!");
			}
					
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"PROJECTS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return projects;
	}

	/**
	 * This method for get project by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In Projects getProject method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Projects) session.get(Projects.class, id);
	}
	
	/**
	 * This method for get project by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId ";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		
		return projectsList;
	}
	
	/**
	 * This method for get project by orgLocationsId And EndDate Graterthan CurrentDate
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long userId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		//String subQueryForUserIdChecking = "project.id IN ( select pm.projects.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)) OR project.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.endDate < :currentDate";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("currentDate", currentDate).setCacheable(true).list();
		
		return projectsList;
	}
	
	
	/**
	 * This method for get primary project by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("=========================================In ProjectsDaoImpl getPrimaryProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.isPrimary = '1' ";
		Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();

		Projects projects = null;
		if (projectsList != null && projectsList.size() > 0) {
			projects = (Projects) projectsList.iterator().next();
		}
		return projects;
	}

	/**
	 * This method for delete project by id.
	 * Here we are doing soft deletion only, record not deleted from DB just we have to change the status
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean deleteProjectById(Long id) throws Exception {
		 logger.debug("In Projects deleteProject method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean isDeleted = false;
		 try{
			 if(id != null){
				 Projects projects= (Projects)session.load(Projects.class,id);
				 projects.setStatus("0");
				 session.saveOrUpdate(projects);
				 logger.info("Successfully deleted !!!");
				 isDeleted = true;
			 }	 
		 }catch(Exception e){
			    StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"PROJECTS"));
				throw new SourceLeadObjectNotFoundException(message.toString());	
		 }
		
		return isDeleted;
	}
	
	
	/**
	 * This method for validate project by projectSequeneNumber. It returns boolean value
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception {
		 logger.debug("In ProjectsDaoImpl validateProjectSequeneNumber method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(projectSequeneNumber != null && orgLocationsId != null){
				String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.projectSequeneNumber = :projectSequeneNumber ";
				Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("projectSequeneNumber", projectSequeneNumber).list();

				if (projectsList != null && projectsList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	/**
	 * This method for get all projects
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getAllProjects() throws Exception {
		logger.debug("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Projects.class);
        List<Projects> projectsList = crit.list();
        return projectsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception {
		logger.info("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "from  Projects projects  left join fetch projects.orgRelationId  orgrelation left join fetch orgrelation.responderLocationId orgLocation "	

        		+ "left join fetch orgLocation.org  where projects.id IN (:projectIds) and projects.orgLocationsId = :orgLocationId";
        List<Projects> projectsList  =   session.createQuery(query.toString()).setParameterList("projectIds", projectIds).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
       
        return projectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer projectIdValidation(Long projectId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from Projects projects where projects.projectSequeneNumber = :projectId";
		
		List<Long> deptCount =   session.createQuery(query.toString()).setLong("projectId", projectId)
        										.list();
			Integer count = 0;
			if(deptCount != null && !deptCount.isEmpty()){
					count = deptCount.get(0).intValue();
			}
			return count;
		
	}

     public class FilterNameValueMap<key,value> extends HashMap<key,value>{

	private static final long serialVersionUID = -2650993424650202629L;

     }
    /**
     * This method for get projects based on OrgRelationId (particular client)
     */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getAllProjectsByOrgRelationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects projects  left join fetch projects.orgRelationId orgre  where  orgre.responderLocationId  IN(select orgrel.responderLocationId from OrgRelations  orgrel where orgrel.responderLocationId = :orgRelationId and orgrel.locationId = :locationId) and projects.orgLocationsId = :locationId";
		List<Projects> clientProjectsList =  session.createQuery(query.toString()).setLong("orgRelationId", orgRelationId).setLong("locationId", locationId).list();
		
		return clientProjectsList;
	}

	@Override

	@Transactional(rollbackFor = { Exception.class })
	public Integer updateStartDateOfProjectId(Date startDate ,Long projectId) {
		Session session =  this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects pj set pj.startDate =:startDate where pj.id = :projectId");
		query.setLong("projectId", projectId);
		query.setDate("startDate", startDate);
		int result = query.executeUpdate();
		return result;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer updateEndDateOfProjectId(Date endDate, Long projectId) {
		Session session =  this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects pj set pj.endDate =:endDate where pj.id = :projectId");
		query.setLong("projectId", projectId);
		query.setDate("endDate", endDate);
		int result = query.executeUpdate();
		return result;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> getApprovarAssignedProjects(Long userId, Long orgLocationId) throws Exception{
		logger.debug("In ProjectsDaoImpl class getApprovarAssignedProjects method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select projects.id from Projects projects where projects.orgLocationsId = :orgLocationId and "
				+ "projects.id IN (select pah.projectId from ProjectApproverHierarchy pah  where pah.approverUserId = :userId)";
		List<Long> projectsIdList =  session.createQuery(query.toString())
									.setLong("userId", userId)
									.setLong("orgLocationId", orgLocationId)
									.setCacheable(true).list();
		
		return projectsIdList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects p where p.projectSequeneNumber = :projectSequeneNumber";
		List<Projects> projectsList =  session.createQuery(query.toString())
				.setString("projectSequeneNumber", projectSequeneNumber)
				.setCacheable(true).list();
		if(projectsList != null){
			return projectsList.get(0);
		}
		return null;
	}
	
	
	

	/**
	 * This method for get projects based on clients and Status (Any number of
	 * Client)
	 */

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId, String clients,
			String status, Long orgLocationsId) throws Exception {

		Collection<Projects> plist = null;
		Session session = this.sessionFactory.getCurrentSession();
		String[] tokens = status.split(",");
		ArrayList<Projects> projectssList = new ArrayList<Projects>();
		String[] client = clients.split(",");
		String subQuery2 = "";
		if (Arrays.asList(tokens).contains("All")) {
			subQuery2 = subQuery2
					+ "ps.projectStatus = 'STARTED' or ps.projectStatus = 'INPROGRESS' or ps.projectStatus = 'COMPLETED' or ps.projectStatus = 'ONHOLD' or ";
		}

		if (Arrays.asList(tokens).contains("STARTED")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'STARTED' or ";
		}

		if (Arrays.asList(tokens).contains("INPROGRESS")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'INPROGRESS' or ";
		}
		if (Arrays.asList(tokens).contains("COMPLETED")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'COMPLETED' or ";
		}
		if (Arrays.asList(tokens).contains("ONHOLD")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'ONHOLD' or ";
		}

		if ((tokens[0] != null && tokens[0].length() > 0) && (client[0] != null && client[0].length() > 0)) {
			subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
		} else if (subQuery2 != null && !"".equals(subQuery2)) {
			subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
		}

		Boolean isadmin = userRoleDao.isUserAdmin(userId);

		if (isadmin) {
			if (client[0] != null && client[0].length() > 0) {
				for (String clnt : client) {

					String subquery1 = "ps.orgRelationId =" + clnt;
					String query = "from  Projects ps   left join fetch ps.orgLocationsId  where " + subquery1 + " "
							+ subQuery2 + "   and ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
					plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
					projectssList.addAll(plist);

				}
			} else if (subQuery2 != null && !"".equals(subQuery2)) {

				String query = "from  Projects ps   left join fetch ps.orgLocationsId  where " + subQuery2 + " and  ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
				projectssList.addAll(plist);
			} else {
				String query = "from  Projects ps   left join fetch ps.orgLocationsId  where ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
				projectssList.addAll(plist);
			}

		} else {

			if (client[0] != null && client[0].length() > 0) {
				for (String clnt : client) {

					String subquery1 = "and ps.orgRelationId =" + clnt;
					String subquery3 = " (ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))";
					String query = "from  Projects ps   left join fetch ps.orgLocationsId  where " + subquery3 + " and  ps.orgLocationsId = :orgLocationsId "
							+ subquery1 + " " + subQuery2 + " ORDER BY ps.createdDate DESC";
					plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).setCacheable(true).list();
					projectssList.addAll(plist);

				}
			} else if(tokens[0] != null && tokens[0].length() > 0){
				
				String subquery3 = " and (ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))";
				String query = "from  Projects ps  left join fetch ps.orgLocationsId  where " + subQuery2 + " "
						+ subquery3 + " and ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).setCacheable(true).list();
				projectssList.addAll(plist);
			}
			else{
				
				String subquery3 = "(ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))";
				String query = "from  Projects ps  left join fetch ps.orgLocationsId  where "+ subquery3 + " and ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).setCacheable(true).list();
				projectssList.addAll(plist);
			}
		}
		return projectssList;

	}




		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Projects> getProjectByOrgLocationsIdAndUserId(Long orgLocationsId, Long userId) {
			logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Projects pj where pj.orgLocationsId = :orgLocationsId and pj.id NOT IN(select pm.projects.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )) ";
			List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).setCacheable(true).list();
			return projectsList;
		}


		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Projects getProjectByIdAndLoginOrgId(Long projectId, Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Projects project left join fetch project.orgRelationId where project.id = :projectId and project.orgLocationsId = :orgLocationId ";
			List<Projects> projectsList =  session.createQuery(query.toString()).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId)
					.setCacheable(true).list();
			
			
			Projects projects = null;
			if (projectsList != null && projectsList.size() > 0) {
				projects = (Projects) projectsList.iterator().next();
			}
			return projects;
		}

		
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Integer getUserAssignedProjectsCount(Long userId, Long orgLocationId) {
			
			Session session = this.sessionFactory.getCurrentSession();
			
			String query = "select pj from  Projects pj where pj.id IN (select pm.projects.id from ProjectMembers pm  "
					+ "where pm.employee.id IN (select emp.id from Employees emp where emp.user.id = :userId and pm.status <> 'IN-ACTIVE'  and  emp.orgLocations.id = :orgLocationId ))" ;
			List<Projects> projectsList =session.createQuery(query.toString()).setLong("userId", userId)
					.setLong("orgLocationId", orgLocationId).setCacheable(true).list(); 
			      
		    return projectsList.size();
			  
			
}
/**
 * Reading all the Employees those who are not submitted the Time sheets
 * @return List<User>
 */
	/*	@Override
		@Transactional(rollbackFor = { Exception.class })
		public HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			Date currentWeekStartDate = CalendarUtil.getCurrentWeekDay("MON");
			java.sql.Date  currentWeekStartDayDate = new java.sql.Date(currentWeekStartDate.getTime());
			
			Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			cal.setLenient(false);
			cal.setTime(new Date());
			
			Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
			
			Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
			Date previousWeekStartDate = previousMonday.getTime();
			java.sql.Date  previousWeekStartDayDate = new java.sql.Date(previousWeekStartDate.getTime());
			
			
			
			String query = "from User user where user.id IN (select emp.user from Employees emp where emp.id IN(select pm.employee from ProjectMembers pm where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate >= :frmDate and ts.endDate < :endsDate)  and ts.status = '1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
			List<User> userList  =  session.createQuery(query.toString()).setDate("frmDate", previousWeekStartDayDate).setDate("endsDate", currentWeekStartDayDate).setCacheable(true).list(); 
			logger.debug("The Total Employees which are not submitted the Time sheets within a week are : "+userList.size());
			HashSet<User> userSet  = null;
			if(userList != null && !userList.isEmpty()){
				userSet = new HashSet<>(userList);
			}
			return userSet;
		}*/


/**
 * Read the aprover for the individual Employee based on the projects assigned to them
 */
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<SubmissionApproverHierarchy> getApproveUserForTimeSheets(Long userId) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SubmissionApproverHierarchy sah left join fetch sah.submissionId ts left join fetch ts.projectMembers pm left join fetch pm.employee emp left join fetch emp.user where emp.user.id = :userId";
			List<SubmissionApproverHierarchy> userList  = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list(); 
			return userList;
		}
/**
 * Reads all the users those who are not submitted time sheets even sent notification
 */
/*@Override
@Transactional(rollbackFor = { Exception.class })
public HashSet<User> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
	
	Session session = this.sessionFactory.getCurrentSession();
	Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
	cal.setLenient(false);
	cal.setTime(new Date());
	
	Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
	
	Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
	Date previousDate = previousMonday.getTime();
	java.sql.Date  previousDayDate = new java.sql.Date(previousDate.getTime());
	
	Date currentWeekMondayDate = CalendarUtil.getCurrentWeekDay("MON");
	java.sql.Date  currentMondayDate = new java.sql.Date(currentWeekMondayDate.getTime());
	List<User> userList = null;
	if(invoiceFrequency !=null && !"".equalsIgnoreCase(invoiceFrequency)){
		String query = "from User user where user.id IN (select emp.user from Employees emp where emp.InvoiceFrequency = :invoiceFrequency and emp.id IN(select pm.employee from ProjectMembers pm where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
		userList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", currentMondayDate).setString("invoiceFrequency", invoiceFrequency).setCacheable(true).list(); 
	}else{
		String query = "from User user where user.id IN (select emp.user from Employees emp where emp.id IN(select pm.employee from ProjectMembers pm where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
		userList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", currentMondayDate).setCacheable(true).list(); 
	}
	
	logger.debug("The Total Employees which are not submitted the Time sheets within a week are : ***********************"+userList.size());
	 HashSet<User> userSet  = null;
	 if(userList != null && !userList.isEmpty()){
			userSet = new HashSet<>(userList);
		}
		return userSet;
}*/

@Override
@Transactional(rollbackFor = { Exception.class })
public void saveAllProjects(List<Projects> projectsList) throws Exception {
	logger.debug("In Save All Projects  method");
	Session session = this.sessionFactory.getCurrentSession();
	if(projectsList != null && projectsList.isEmpty()){
		for(Projects projects :projectsList){
			try{
				session.saveOrUpdate(projects);
			}catch(Exception exception){
				 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
		}
	}
}

/*@Override
@Transactional(rollbackFor = { Exception.class })
public HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek() {
      Session session = this.sessionFactory.getCurrentSession(); 
      Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL); 
      cal.setLenient(false); 
      cal.setTime(new Date()); 
      Date previousDate = CalendarUtil.getCurrentWeekDay("MON"); 
      java.sql.Date previousDayDate = new java.sql.Date(previousDate.getTime()); 
      String query = "from User user where user.id IN (select emp.user from Employees emp where emp.InvoiceFrequency = 'Weekly' and emp.id IN(select pm.employee from ProjectMembers pm where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where ts.startDate = :startDate and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))"; 
      List<User> userList = session.createQuery(query.toString()).setDate("startDate", previousDayDate).setCacheable(true).list(); 
      logger.debug("The Total Employees which are not submitted the Time sheets within a week are : "+userList.size());
      HashSet<User> userSet  = null;
		if(userList != null && !userList.isEmpty()){
			userSet = new HashSet<>(userList);
		}
		return userSet;

}*/

          }

