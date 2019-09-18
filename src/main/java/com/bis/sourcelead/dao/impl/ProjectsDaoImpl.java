package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.AvailableAssociatesDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;

@Repository
public class ProjectsDaoImpl implements ProjectsDao{
	 
	@Autowired
	private SessionFactory sessionFactory;
	
    
    @Autowired
   	private UserRoleDao userRoleDao;
    
    @Autowired
    OrgLocationsDao orgLocationsDao;
    
    @Autowired
    ProjectClientDao  projectClientDao;
    
    @Autowired
    CommonUtil commonUtil;
    
    @Autowired
    ProjectMembersHistoryService projectMembersHistoryService;
    
    @Autowired
    TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
    
    @Autowired
    ProjectMembersService projectMembersService;
    
    @Autowired
    ProjectBudgetService projectBudgetService;
    
    @Autowired
    OrgLocationService orgLocationService;
    
    @Autowired
    ProjectsService projectsService;
    
    @Autowired 
    UserDao userDao;
    
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
			Date weekStartDate = timesheetApprovalFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projects, projects.getStartDate());
			projects.setWeekStartDate(weekStartDate);
			session.saveOrUpdate(projects);
			if(projects != null && projects.getProjectDocumentFolder() != null &&  !projects.getProjectDocumentFolder().isEmpty()) {
				for(ProjectDocumentFolder pdf :projects.getProjectDocumentFolder()){
				pdf.setProjectId(projects);
				session.saveOrUpdate(pdf);
				}
			}
			if(projects != null && projects.getProjectDocuments() != null &&  !projects.getProjectDocuments().isEmpty()) {
				for(ProjectDocuments pd :projects.getProjectDocuments()) {
					pd.setProjects(projects);
					session.saveOrUpdate(pd);
				}
			}
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
				Date weekStartDate = timesheetApprovalFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projects, projects.getStartDate());
				projects.setWeekStartDate(weekStartDate);
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
		/*Session session = this.sessionFactory.getCurrentSession();
		return (Projects) session.get(Projects.class, id);*/
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project left join fetch project.orgRelationId left join fetch project.orgLocationsId orgLoc "
				+ "left join fetch orgLoc.org left join fetch project.sharedLocationId  where project.id = :id ";
		Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("id", id).list();

		Projects projects = null;
		if (projectsList != null && projectsList.size() > 0) {
			projects = (Projects) projectsList.iterator().next();
		}
		return projects;
	}
	
	/**
	 * This method for get project by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.projectMode = 'Finished' ";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
		
		return projectsList;
	}
	
	
	/**
	 * This method for get project Ids by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> getProjectIdsByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getProjectIdsByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select project.id from Projects project where project.orgLocationsId = :orgLocationsId ";
		List<Long> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
		
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
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("currentDate", currentDate).list();
		
		return projectsList;
	}
	
	
	/**
	 * This method for get all project EndDate Graterthan CurrentDate
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getAllProjectsEndDateGraterthanCurrentDate() throws Exception {
		logger.debug("In ProjectsDaoImpl class getAllProjectsEndDateGraterthanCurrentDate method");
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = "from Projects project where  project.endDate < :currentDate";
		List<Projects> projectsList =  session.createQuery(query.toString()).setParameter("currentDate", currentDate).list();
		
		return projectsList;
	}
	
	
	/**
	 * This method for get primary project by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.isPrimary = '1' ";
		Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();

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
				Collection<Projects> projectsList = session.createQuery(query.toString())
						                                   .setLong("orgLocationsId", orgLocationsId)
						                                   .setString("projectSequeneNumber", projectSequeneNumber)
						                                   
						                                   .list();

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
		 List<Projects> projectsList = null;
        Session session = this.sessionFactory.getCurrentSession();
        projectsList = session.createQuery("from Projects projects " ).list();
        return projectsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception {
		logger.info("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "from  Projects projects  left join fetch projects.orgRelationId  orgrelation left join fetch orgrelation.responderLocationId orgLocation "	

        		+ "left join fetch orgLocation.org  where projects.id IN (:projectIds) and projects.orgLocationsId = :orgLocationId";
        List<Projects> projectsList  =   session.createQuery(query.toString())
								        		.setParameterList("projectIds", projectIds)
								        		.setLong("orgLocationId", orgLocationId)
								        		
								        		.list();
       
        return projectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer projectIdValidation(Long projectId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from Projects projects where projects.projectSequeneNumber = :projectId";
		
		List<Long> deptCount =   session.createQuery(query.toString())
				                         .setLong("projectId", projectId)
				                         
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
		List<Projects> clientProjectsList =  session.createQuery(query.toString())
													.setLong("orgRelationId", orgRelationId)
													.setLong("locationId", locationId)
													
													.list();
		
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
									.list();
		
		return projectsIdList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects p where p.projectSequeneNumber = :projectSequeneNumber";
		List<Projects> projectsList =  session.createQuery(query.toString())
											  .setString("projectSequeneNumber", projectSequeneNumber)
											  .list();
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
					+ "ps.projectStatus = 'STARTED' or ps.projectStatus = 'INPROGRESS' or ps.projectStatus = 'COMPLETED' or ps.projectStatus = 'ONHOLD' or ps.projectStatus = 'NOTSTARTED' or ps.projectStatus = 'DROPPED' or  ";
		}

		if (Arrays.asList(tokens).contains("STARTED")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'STARTED' or ";
		}
		
		if (Arrays.asList(tokens).contains("INPROGRESS")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'INPROGRESS' or ";
		}
		
		if (Arrays.asList(tokens).contains("NOTSTARTED")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'NOTSTARTED' or ";
		}

		if (Arrays.asList(tokens).contains("DROPPED")) {
			subQuery2 = subQuery2 + "ps.projectStatus = 'DROPPED' or ";
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
					plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
					projectssList.addAll(plist);

				}
			} else if (subQuery2 != null && !"".equals(subQuery2)) {

				String query = "from  Projects ps   left join fetch ps.orgLocationsId  where " + subQuery2 + " and  ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
				projectssList.addAll(plist);
			} else {
				String query = "from  Projects ps   left join fetch ps.orgLocationsId  where ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
				projectssList.addAll(plist);
			}

		} else {

			if (client[0] != null && client[0].length() > 0) {
				for (String clnt : client) {

					String subquery1 = "and ps.orgRelationId =" + clnt;
					String subquery3 = " (ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))";
					String query = "from  Projects ps   left join fetch ps.orgLocationsId  where " + subquery3 + " and  ps.orgLocationsId = :orgLocationsId "
							+ subquery1 + " " + subQuery2 + " ORDER BY ps.createdDate DESC";
					plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).list();
					projectssList.addAll(plist);

				}
			} else if(tokens[0] != null && tokens[0].length() > 0){
				
				String subquery3 = " and (ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))";
				String query = "from  Projects ps  left join fetch ps.orgLocationsId  where " + subQuery2 + " "
						+ subquery3 + " and ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).list();
				projectssList.addAll(plist);
			}
			else{
				
				String subquery3 = "(ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))";
				String query = "from  Projects ps  left join fetch ps.orgLocationsId  where "+ subquery3 + " and ps.orgLocationsId = :orgLocationsId ORDER BY ps.createdDate DESC";
				plist = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).list();
				projectssList.addAll(plist);
			}
		}
		return projectssList;

	}
	
	
	
	@Override
	@Transactional
	public ResponseList<ProjectsDTO> getProjectsListByClientsAndStatus(ProjectsDTO projectsDTO,Long userId, String clients, String status,
			Long orgLocationsId,String userRole,Long candidateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String approverQuery = "";
		String projectModeQuery = "";
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<ProjectsDTO> response = new ResponseList<>();
		Query queryObj = null;
		
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
	   Matcher matcher = pattern.matcher(projectsDTO.getSearchValue());
	   java.sql.Date sqlDate = null;
	    if(matcher.matches() == true) {
					if(projectsDTO.getSearchValue().contains("/")||(projectsDTO.getSearchValue().contains("-"))) {
						String replacedValue = projectsDTO.getSearchValue().replaceAll("/", "-");
						    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationsId);
							SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					        java.util.Date utilDate = format.parse(replacedValue);
					        sqlDate = new java.sql.Date(utilDate.getTime());
					}
	    }
		
		StringBuilder query1 = new StringBuilder("");
		if(projectsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(projectsDTO.getSearchValue())) {
		 query1.append(" ps.projectSequeneNumber like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ")
				.append("OR ps.projectName like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ")
				.append("OR ps.invoiceFrequency like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ")
				.append("OR ps.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.firstName like '" + projectsDTO.getSearchValue().replace("'", "") + "%' OR pah.approverUserId.lastName like '%" + projectsDTO.getSearchValue().replace("'", "") + "' ) ")
				.append("OR ps.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id IN (select user.id from User user where (concat(user.firstName,user.lastName) like '%" + projectsDTO.getSearchValue().replace("'", "").replaceAll("[\\s]+", "").trim() + "%')))" )
				.append("OR ps.id IN (select pex.projectId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId.firstName like '" + projectsDTO.getSearchValue().replace("'", "") + "%' OR pex.approverUserId.lastName like '%" + projectsDTO.getSearchValue().replace("'", "") + "' ) ")
				.append("OR ps.id IN (select pex.projectId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId.id IN (select user.id from User user where (concat(user.firstName,user.lastName) like '%" + projectsDTO.getSearchValue().replace("'", "").replaceAll("[\\s]+", "").trim() + "%')))" )
				.append("OR ps.projectMode like '%" + projectsDTO.getSearchValue().replace("'", "") + "%' ");
		 
			 if(projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("in-progress") || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("in progress") 
					 || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("inprogress")) {
				 query1.append("OR ps.projectStatus like '%INPROGRESS%' ");
			 }else if(projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("on-hold") || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("on hold") 
					 || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("onhold")) {
				 query1.append("OR ps.projectStatus like '%ONHOLD%' ");
			 }else if(projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("not started") || projectsDTO.getSearchValue().toLowerCase().equalsIgnoreCase("notstarted")) {
				 query1.append("OR ps.projectStatus like '%NOTSTARTED%' ");
			 }else {
				 query1.append("OR ps.projectStatus like '%" + Project_Status.getNameByValue(projectsDTO.getSearchValue()) + "%' ");
			 }
			 
		     if(sqlDate != null) {
		    	query1.append("OR ps.startDate like '%" + sqlDate + "%' OR ps.endDate like '%" + sqlDate + "%' ");
			 }
			
					
		}
		
		
		List<Long> clientIdsList = new ArrayList<>();
		if(clients != null && !clients.isEmpty()){
			String[] client = clients.split(",");
			for(int i=0; i<client.length; i++){
				clientIdsList.add(Long.valueOf(client[i]));
			}
		}else {
			clientIdsList.add(0L);
		}
		
		List<Long> managerIdsList = new ArrayList<>();
		if(projectsDTO.getProjectManagers() != null && !projectsDTO.getProjectManagers().isEmpty()){
			String[] manager = projectsDTO.getProjectManagers().split(",");
			for(int i=0; i<manager.length; i++){
				managerIdsList.add(Long.valueOf(manager[i]));
			}
		}else {
			managerIdsList.add(0L);
		}
		
		
		List<String> tagsList = new ArrayList<>();
		if(projectsDTO.getTags() != null && !projectsDTO.getTags().isEmpty()) {
			String[] tags = projectsDTO.getTags().split(",");
			for(int i=0; i<tags.length; i++){
				tagsList.add(tags[i]);
			}
		}else {
			tagsList.add("");
		}
		
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			approverQuery = " and (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId = "+userId+" and pah.status ='1') OR "
					+ " pc.id IN (select pex.projectClientsId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId = "+userId+" and pex.status = '1') OR pc.createdBy.id = "+userId+" "
					+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.id = "+candidateId+" and pm.status = '1') "
							+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.parentCandidateId.id = "+candidateId+" and pm.status = '1')) ";
		}
		
		/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
			projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId  OR ps.projectMode = 'Finished')";
		}else {
			projectModeQuery = " and (ps.orgLocationsId = :orgLocationsId OR ps.sharedLocationId = :orgLocationsId  OR ps.projectMode = 'Finished')";
		}*/
		
		projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId  OR ps.projectMode = 'Finished')";
		
		
		
		String subQuery1 ="";
		
		List<Projects> projectsList = null;
		if(status != null && !status.isEmpty()){
			String[] statusFilter = status.split(",");
			if (Arrays.asList(statusFilter).contains("In-Progress")) {
				subQuery1 = subQuery1 + "ps.projectStatus = 'INPROGRESS' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Completed")) {
				subQuery1 = subQuery1 + "ps.projectStatus = 'COMPLETED' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("On-Hold")) {
				subQuery1 = subQuery1 + "ps.projectStatus = 'ONHOLD' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Dropped")) {
				subQuery1 = subQuery1 + "ps.projectStatus = 'DROPPED' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Not Started")) {
				subQuery1 = subQuery1 + "ps.projectStatus = 'NOTSTARTED' or ";
			}
			
			
			subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
		}
		
		String subQuery2 = " and ((:varZero  IN (:clientIdsList)) OR (:varZero NOT IN (:clientIdsList) and pc.orgRelationId.id IN (:clientIdsList)))";
		
		String subQuery3 ="";
		if(projectsDTO.getProjectType() != null && !projectsDTO.getProjectType().isEmpty()){
			String[] projectsType = projectsDTO.getProjectType().split(",");
			if (Arrays.asList(projectsType).contains("Internal")) {
				/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
					subQuery3 = subQuery3 + "ps.orgLocationsId.org.id = :orgId or ";
				}else {
					subQuery3 = subQuery3 + "ps.orgLocationsId = :orgLocationsId or ";
				}*/
				
				subQuery3 = subQuery3 + "ps.orgLocationsId.org.id = :orgId or ";
				
			}
			if (Arrays.asList(projectsType).contains("Client")) {
				subQuery3 = subQuery3 + "ps.orgLocationsId <> '"+orgLocationsId+"' or ";
			}
			subQuery3 = "and (" + subQuery3.substring(0, subQuery3.length() - 4) + ")";
		}
		
		String subQuery4 = " and ((:varZero  IN (:managerIdsList)) OR (:varZero NOT IN (:managerIdsList) and "
				+ " (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId.id IN (:managerIdsList)) OR "
				+ " pc.id IN (select pex.projectClientsId.id from ExpenseProjectApproverHierarchy pex where pex.approverUserId.id IN (:managerIdsList))))) ";
		
		String subQuery6 = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and pc.projectId.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

		
		String subQuery5 ="";
		if(projectsDTO.getInvoiceFrequency() != null && !projectsDTO.getInvoiceFrequency().isEmpty()){
			String[] frequencyFilter = projectsDTO.getInvoiceFrequency().split(",");
			if (Arrays.asList(frequencyFilter).contains("Weekly")) {
				subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Weekly' or ";
			}
			
			if (Arrays.asList(frequencyFilter).contains("Bi-Weekly")) {
				subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Bi-Weekly' or ";
			}
			
			if (Arrays.asList(frequencyFilter).contains("Semi-Monthly")) {
				subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Semi-Monthly' or ";
			}
			
			if (Arrays.asList(frequencyFilter).contains("Monthly")) {
				subQuery5 = subQuery5 + "ps.invoiceFrequency = 'Monthly' or ";
			}
			
			subQuery5 = "and (" + subQuery5.substring(0, subQuery5.length() - 4) + ")";
		}
		
		
		
		//usertList shorting order
				String sortingQry = "";
				if(StringUtils.isNotBlank(projectsDTO.getSortColumn())) {
					sortingQry = " order by ps."+ projectsDTO.getSortColumn() +" "+projectsDTO.getSortOrder();
				}
				String projectAccessQuery = "";
				
				if(userRole.equalsIgnoreCase("ROLE_ADMIN") && (StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES))){
							 projectAccessQuery =  " and ("
				                       + "  ps.id IN(select distinct subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.approverUserId ='"+userId+"' and subpah.status !='0')"
				                       +" OR "
				                       + "  ps.id IN( select subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.status = '1' and subpah.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationsId+"'))"
			                           +" OR "
			                           + "  ps.id IN(select distinct epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.approverUserId ='"+userId+"' and epx.status !='0')"
				                       +" OR "
				                       + "  ps.id IN( select epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.status = '1' and epx.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationsId+"'))"
				                       +" )";
				}else if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
					 projectAccessQuery =  " and ("
		                       + "  ps.id IN( select subpah.projectId.id from ProjectApproverHierarchy subpah where subpah.status = '1' and subpah.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationsId+"'))"
	                           +" OR "
		                       + "  ps.id IN( select epx.projectId.id from ExpenseProjectApproverHierarchy epx where epx.status = '1' and epx.projectClientsId.orgLocationsId.id IN(select userRole.orgLocations.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = '"+orgLocationsId+"'))"
		                       +" )";
				}
				
				
				
				
		
				/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
					query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId "+subQuery2+" "+approverQuery+" "+subQuery4+" "+subQuery6+") "+projectModeQuery+" "+subQuery1+" "+subQuery3+" "+subQuery5+" ";
				}else {
					query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orgLocationsId "+subQuery2+" "+approverQuery+" "+subQuery4+" "+subQuery6+") "+projectModeQuery+" "+subQuery1+" "+subQuery3+" "+subQuery5+" ";
				}*/
				
				query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId and pc.status = 1 "+subQuery2+" "+approverQuery+" "+subQuery4+" "+subQuery6+") "+projectModeQuery+" "+subQuery1+" "+subQuery3+" "+subQuery5+" ";
		         query = query+projectAccessQuery;
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+sortingQry);
		}else{
			 queryObj = session.createQuery(query + query1.toString()+sortingQry);
		}
		
		queryObj.setFirstResult(projectsDTO.getStart());
		queryObj.setMaxResults(projectsDTO.getLength());
		/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
			queryObj.setLong("orgId", projectsDTO.getOrgId()).setString("varString", "").setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
		}else {
			queryObj.setLong("orgLocationsId", orgLocationsId).setString("varString", "").setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
		}*/
		
		queryObj.setLong("orgId", projectsDTO.getOrgId()).setString("varString", "").setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
		
		projectsList = queryObj.list();
		
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
				countlist = session.createQuery("select count(*) " + query +projectAccessQuery + "and "+"("+query1.toString()+")").setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			}else {
				countlist = session.createQuery("select count(*) " + query +projectAccessQuery + "and "+"("+query1.toString()+")").setString("varString", "").setLong("varZero", 0).setLong("orgLocationsId", orgLocationsId).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			}*/
			
			countlist = session.createQuery("select count(*) " + query +projectAccessQuery + "and "+"("+query1.toString()+")").setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			
			
			filterCount = countlist.iterator().next().intValue();
		}else {
			/*if(StringUtils.isNotBlank(projectsDTO.getIsGlobalAccess()) && projectsDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
				countlist = session.createQuery("select count(*) " + query +projectAccessQuery).setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			}else {
				countlist = session.createQuery("select count(*) " + query +projectAccessQuery).setString("varString", "").setLong("varZero", 0).setLong("orgLocationsId", orgLocationsId).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			}*/
			
			countlist = session.createQuery("select count(*) " + query +projectAccessQuery).setString("varString", "").setLong("varZero", 0).setLong("orgId", projectsDTO.getOrgId()).setParameterList("clientIdsList", clientIdsList).setParameterList("managerIdsList", managerIdsList).setParameterList("tagsList", tagsList).list();
			
			filterCount = countlist.iterator().next().intValue();
		}
		
		
		int totalCount = 0;

		
		if (projectsDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		
		response.setData(getProjectClientAndManagerDetails(projectsList,orgLocationsId,userRole,projectsDTO));
		return response;
	}
	
	
	
	@Override
	@Transactional
	public List<Projects> getProjectsListByUserIdAndOrgLocationIdAndStatus(Long userId,Long orgLocationsId,Long orgId,Long candidateId,String userRole,String globalAccess) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String approverQuery = "";
			String query = null;
			Query queryObj = null;
		    StringBuilder query1 = new StringBuilder("");
			if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
				approverQuery = " and ( (pc.id IN (select pah.projectClientsId.id from ProjectApproverHierarchy pah where pah.approverUserId = "+userId+") OR pc.id IN (select epx.projectClientsId.id from ExpenseProjectApproverHierarchy epx where epx.approverUserId = "+userId+" and epx.status = '1' )) OR pc.createdBy.id = "+userId+" "
						+ " OR pc.id IN (select pm.projectClientsId.id from ProjectMembers pm where pm.projectMemberCandidteId.id = "+candidateId+" and pm.status = '1') ) ";
			}
			String projectModeQuery = "";
			if(StringUtils.isNotBlank(globalAccess) && globalAccess.equalsIgnoreCase(Constants.YES)) {
				projectModeQuery = " and (ps.orgLocationsId.org.id = :orgId OR ps.projectMode = 'Finished')";
			}else {
				projectModeQuery = " and (ps.orgLocationsId = :orgLocationsId  OR ps.projectMode = 'Finished')";
			}
			if(StringUtils.isNotBlank(globalAccess) && globalAccess.equalsIgnoreCase(Constants.YES)) {
				query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.org.id = :orgId and pc.status = '1' "+approverQuery+" ) "+projectModeQuery+"  ";
			}else {
				query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orgLocationsId and pc.status = '1' "+approverQuery+" ) "+projectModeQuery+" ";
			}
		
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")");
			}else{
				 queryObj = session.createQuery(query + query1.toString());
			}
			
		if(StringUtils.isNotBlank(globalAccess) && globalAccess.equalsIgnoreCase(Constants.YES)) {
			queryObj.setLong("orgId", orgId).list();
		}else {
			queryObj.setLong("orgLocationsId", orgLocationsId).list();
		}
		
		List<Projects> projectsList = queryObj.list();
		 return projectsList;
		
	}




		private List<ProjectsDTO> getProjectClientAndManagerDetails(List<Projects> projectsList,Long orgLocationsId,String userRole,ProjectsDTO projectDTO) throws Exception {
			OrgLocations orglo = orgLocationsDao.getOrgLocations(orgLocationsId);
			List<ProjectsDTO> projectsDTOList = new ArrayList<>();
			for (Projects projects :projectsList ){
				ProjectClients projectClients = null;
				/*if(StringUtils.isNotBlank(projectDTO.getIsGlobalAccess()) && projectDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {*/
					if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
						 projects = projectsService.getProjectById(projects.getId());
						 if(projects.getSharedLocationId().getId().equals(orglo.getId())) {
							 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getSharedLocationId().getId());
						 }else{
							 if(projects.getOrgLocationsId().getId().equals(orglo.getId())) {
								 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getOrgLocationsId().getId());
							 }else {
								 ProjectClients clientProjectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orglo.getId());
								 if(clientProjectClients == null) {
									 clientProjectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getSharedLocationId().getId());
								 }
								 if(clientProjectClients != null && clientProjectClients.getOrgRelationId() != null && !clientProjectClients.getOrgRelationId().getResponderLocationId().getId().equals(orglo.getId())) {
									 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientProjectClients.getOrgRelationId().getLocationId().getId());
								 }else {
									 if(clientProjectClients.getOrgRelationId() != null) {
										 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientProjectClients.getOrgRelationId().getResponderLocationId().getId());
									 }else {
										 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
									 }
								 }
							 }
							 
						 }
						
					}else {
						 projects = projectsService.getProjectById(projects.getId());
						 
						 if(projects.getOrgLocationsId().getId().equals(orglo.getId())) {
							 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
						 }else {
							 if(projects.getOrgLocationsId().getOrg().getId().equals(orglo.getOrg().getId())) {
								 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getOrgLocationsId().getId());
							 }else {
								 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
								 if(projectClients != null && projectClients.getOrgRelationId() != null) {
									 OrgRelations orgRel = projectClients.getOrgRelationId();
									 OrgLocations clientOrgLocation = null;
									 if(!orgRel.getResponderLocationId().getId().equals(orglo.getId())) {
										 clientOrgLocation = orgRel.getLocationId();
									 }else {
										 clientOrgLocation = orgRel.getResponderLocationId();
									 } 
									 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientOrgLocation.getId());
								 }else {
									 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgId(projects.getId(), projectDTO.getOrgId());
								 }
							 }
							
						 }
						
						
					}
				/*}else {
					 projectClients = projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocationsId);
				}*/
				    	 if(projectClients != null){
				    		 
				    			 ProjectsDTO projectsDTO = new ProjectsDTO();
				    			 projectsDTO.setId(projects.getId());
				    			 projectsDTO.setIsProjectShared(projects.getIsProjectShared());
				    			 projectsDTO.setSharedClientOrgLocationId(projects.getOrgLocationsId().getId());
				    			 projectsDTO.setProjectClientUUID(projectClients.getUuid());
				    			 projectsDTO.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getStartDate()));
				    			 projectsDTO.setEmployeeStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getStartDate()));
								    if(projects.getEndDate() != null){
								    	projectsDTO.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getEndDate()));
								    	projectsDTO.setEmployeeEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projects.getEndDate()));
								    }
				    			 
				    			 if(projectClients.getOrgRelationId() != null){
						    		 
						    			if(null != projectClients.getOrgRelationId().getLocationId() && projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocationsId.longValue()){
						    				projectsDTO.setClient(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
										}if(null != projectClients.getOrgRelationId().getResponderLocationId() && projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocationsId.longValue()){
											projectsDTO.setClient(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
										}
										
										projectsDTO.setOrgRelationUUID(projectClients.getOrgRelationId().getUUID());
						    	  }
						    	 
					    	    Set<ProjectApproverHierarchy> projectApproverHierarchies = projectClients.getProjectApproverHierarchyList();
							    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
							    	if(approverHierarchy.getLevel() == 1 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
							    		StringBuilder manager1 = new StringBuilder();
							    		if(approverHierarchy.getApproverUserId().getLastName() != null){
							    			projectsDTO.setManagerLevel1(manager1.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());	
							    		}else{
							    			projectsDTO.setManagerLevel1(manager1.append(approverHierarchy.getApproverUserId().getFirstName()).toString());
							    		}
							    		
							    	}else if(approverHierarchy.getLevel() == 2 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
							    		StringBuilder manager2 = new StringBuilder();
							    		if(approverHierarchy.getApproverUserId().getLastName() != null){
							    			projectsDTO.setManagerLevel2(manager2.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());	
							    		}else{
							    			projectsDTO.setManagerLevel2(manager2.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").toString());
							    		}
							    		
							    	}
							    }
							    
							 
							 if(projects.getOrgLocationsId().getId().longValue() == orglo.getId().longValue()) {
								 projectsDTO.setOrgLocationsId(projects.getOrgLocationsId().getId());
								 List<ProjectBudget>  budgetList = projectBudgetService.getProjectBudgetByProjectTaskId(projects.getId(),orglo.getId());
								    if(budgetList != null && budgetList.size() > 0) {
								    	 double budgetSum = budgetList.stream().filter(p->p.getEstimatedBudgetValue() !=null).mapToDouble(p->p.getEstimatedBudgetValue()).sum();
											if(budgetList.size() >0) {
												ProjectBudget projectBudget = budgetList.iterator().next();
												projectsDTO.setProjectbudgetCurrency(projectBudget.getEstimatedBudgetCurrency());
											}
											projectsDTO.setProjectbudgetAmount(budgetSum);
								    } 
							 }
							   
							   
							    projectsDTO.setProjectName(projects.getProjectName());
							    projectsDTO.setProjectSequeneNumber(projects.getProjectSequeneNumber());
							    projectsDTO.setProjectMode(projects.getProjectMode());
							    projectsDTO.setProjectUUID(projects.getProjectUUID());
							    projectsDTO.setInvoiceFrequency(projects.getInvoiceFrequency());
							    projectsDTO.setProjectStatus(projects.getProjectStatus().toString());
							    projectsDTOList.add(projectsDTO);
				    		 
				    	 }
				    	
			}
			
			return projectsDTOList;
		
	}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Projects> getProjectByOrgLocationsIdAndUserId(Long orgLocationsId, Long userId) {
			logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Projects pj where pj.orgLocationsId = :orgLocationsId and pj.id NOT IN(select pm.projects.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )) ";
			List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).list();
			return projectsList;
		}
		
		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Projects> getProjectsByOrgLocationsIdAndApproverUserId(Long orgLocationsId, Long userId) {
			logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query= " from Projects projects where  projects.id IN "
					   +"("
						 + " select hierarchy.projectId.id from ProjectApproverHierarchy hierarchy  where  "
						 + " (hierarchy.projectClientsId.orgLocationsId.id = :orgLocationsId and hierarchy.approverUserId.id = :userId "
						 + " OR "
						 + " (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = hierarchy.projectClientsId.orgLocationsId.id) )"
					  + " ))";
			List<Projects> projectsList =  session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).list();
			return projectsList;
		}

		
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Projects getProjectByIdAndLoginOrgId(Long projectId, Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Projects project left join fetch project.orgRelationId where project.id = :projectId and project.orgLocationsId = :orgLocationId ";
			List<Projects> projectsList =  session.createQuery(query.toString()).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId)
					.list();
			
			
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
					+ "where pm.projectMemberCandidteId.id IN (select cand.id from Candidate cand where cand.consultantUser.id = :userId and cand.orgLocationId.id = :orgLocationId )  and pm.status = '1') and pj.projectMode <> 'Draft' " ;
			List<Projects> projectsList =session.createQuery(query.toString()).setLong("userId", userId)
					.setLong("orgLocationId", orgLocationId).list(); 
			      
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
			List<User> userList  =  session.createQuery(query.toString()).setDate("frmDate", previousWeekStartDayDate).setDate("endsDate", currentWeekStartDayDate).list(); 
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
			List<SubmissionApproverHierarchy> userList  = session.createQuery(query.toString()).setLong("userId", userId).list(); 
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
		userList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", currentMondayDate).setString("invoiceFrequency", invoiceFrequency).list(); 
	}else{
		String query = "from User user where user.id IN (select emp.user from Employees emp where emp.id IN(select pm.employee from ProjectMembers pm where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
		userList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", currentMondayDate).list(); 
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

@Override
@Transactional
public Integer getProjectApproversCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId) throws Exception {
	logger.debug("getProjectApproversCountByUserIdAndOrgLocationId start");
	Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(pc.projectId.id) from ProjectClients pc "
				+ "where pc.projectId.id IN (select hierarchy.projectId.id from ProjectApproverHierarchy hierarchy where hierarchy.approverUserId.id = :userId) and pc.orgLocationsId.id = :orgLocationId ";
		
		List<Long> projectsCount = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if(projectsCount != null && !projectsCount.isEmpty()){
				count = projectsCount.get(0).intValue();
		}
		return count;
    
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
      List<User> userList = session.createQuery(query.toString()).setDate("startDate", previousDayDate).list(); 
      logger.debug("The Total Employees which are not submitted the Time sheets within a week are : "+userList.size());
      HashSet<User> userSet  = null;
		if(userList != null && !userList.isEmpty()){
			userSet = new HashSet<>(userList);
		}
		return userSet;

}*/

	@Override
	@Transactional
	public Integer getProjectCountByOrgLocationId(Long orgLocationId) throws Exception {
		logger.debug("getProjectCountByOrgLocationId start");
		Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(projects.id) from Projects projects where projects.orgLocationsId.id = :orgLocationId ";
			
			List<Long> projectsCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(projectsCount != null && !projectsCount.isEmpty()){
					count = projectsCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public Boolean validateTasksName(String taskname, Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(taskname != null && projectId != null){
				String query = "from ProjectTasks projectTasks where projectTasks.projectId = :projectId and projectTasks.taskName = :taskname ";
				Collection<ProjectTasks> projectTaskList = session.createQuery(query.toString())
						                                   .setLong("projectId", projectId)
						                                   .setString("taskname", taskname)
						                                   
						                                   .list();

				if (projectTaskList != null && projectTaskList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Projects> getClientProjectsByOrgRelationId(Long orgRelationId, Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects p where  p.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgRelationId.id = :orgRelationId and (pc.orgRelationId.locationId.id = :orgLocationsId  OR pc.orgRelationId.responderLocationId.id = :orgLocationsId ))  and p.projectStatus <> 'ONHOLD'";
		return session.createQuery(query.toString()).setLong("orgRelationId", orgRelationId).setLong("orgLocationsId", orgLocationId).list();
	}

	@Override
	@Transactional
	public List<Projects> getAllProjectsByOrgLocationIdAndStatus(Long orgLocationsId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		//String query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orgLocationsId) and  ps.projectStatus <> 'ONHOLD' and (ps.orgLocationsId = :orgLocationsId OR ps.projectMode = 'Finished') "; 
		String query = "from Projects p where  p.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgRelationId.id = :orgRelationId and (pc.orgRelationId.locationId.id = :orgLocationsId  OR pc.orgRelationId.responderLocationId.id = :orgLocationsId ))  and p.projectStatus <> 'ONHOLD'";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
		return projectsList;
	}

	@Override
	@Transactional
	public Projects getProjectByProjectUUID(String projectUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Projects> projects = new ArrayList<Projects>();
		logger.debug("getProjectByProjectUUID start");
		projects = session.createQuery("from Projects projects left join fetch projects.orgLocationsId left join fetch projects.sharedLocationId where projects.projectUUID = :projectUUID" ).setString("projectUUID", projectUUID ).list();
		logger.debug("getProjectByProjectUUID end");
		if (projects != null && projects.size() > 0) {
			return projects.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Projects checkProjectIdNumberInDatebase(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects projects where projects.id =(SELECT MAX(pj.id) FROM Projects pj where pj.orgLocationsId.id =:orgLocationId)";  
		List<Projects> project  =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if(project.isEmpty()){
			return null;
		}
		return project.iterator().next();	
	}

	@Override
	@Transactional
	public List<Projects> getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(Long orgLocationsId,
			Long clientOrgLocationId) throws Exception {
		
		logger.debug("In ProjectsDaoImpl class getEndClientProjectByOrgLocationsIdAndClientOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects projects where projects.orgLocationsId = :clientOrgLocationId and projects.projectMode = 'Finished' "
				+ "and projects.id NOT IN (select pc.projectId.id from ProjectClients pc where pc.orgLocationsId.id = :orgLocationsId)";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("clientOrgLocationId", clientOrgLocationId).setLong("orgLocationsId", orgLocationsId).list();
		
		return projectsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateProjectShortCode(String shortCode, Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsDaoImpl validateProjectSequeneNumber method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(shortCode != null && orgLocationsId != null){
				String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.projectShortCode = :shortCode ";
				Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("shortCode", shortCode).list();

				if (projectsList != null && projectsList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	@Override
	@Transactional
	public Integer getProjectCountByOrgLocationIdAndUserCode(Long orgLocationId, String userCode) throws Exception {
		logger.debug("getProjectCountByOrgLocationId start");
		Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(projects.id) from Projects projects where projects.orgLocationsId.id = :orgLocationId and projects.createdBy =:userCode";
			
			List<Long> projectsCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("userCode", userCode).list();
			Integer count = 0;
			if(projectsCount != null && !projectsCount.isEmpty()){
					count = projectsCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public Projects getProjectByOrgLocationsIdAndProjectName(Long orgLocationsId, String projectName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.projectName = :projectName ";
		Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("projectName", projectName).list();

		Projects projects = null;
		if (projectsList != null && projectsList.size() > 0) {
			projects = (Projects) projectsList.iterator().next();
		}
		return projects;
	}
	
	
	@Override
	@Transactional
	public Projects getProjectByProjectName(String projectName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.projectName = :projectName ";
		Collection<Projects> projectsList = session.createQuery(query.toString()).setString("projectName", projectName).list();
		Projects projects = null;
		if (projectsList != null && projectsList.size() > 0) {
			projects = (Projects) projectsList.iterator().next();
		}
		return projects;
	}

	@Override
	@Transactional
	public List<Projects> getAllProjectsByOrgLocationIdAndStatusForTransfer(Long orgLocationId,
			String projectClientUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  Projects ps where ps.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orgLocationsId and pc.uuid <> :uuid ) and  ps.projectStatus <> 'ONHOLD' and (ps.orgLocationsId = :orgLocationsId OR ps.projectMode = 'Finished') "; 
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationId).setString("uuid", projectClientUUID).list();
		return projectsList;
	}

	@Override
	@Transactional
	public List<Projects> getAllProjectByOrgLocationsId(Long orgLocationId) throws Exception {
	
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects ps where ps.id IN (select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId = :orgLocationsId) ";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationId).list();
		
		return projectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Projects> getAllProjectsBasedOnProjectType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String projectTypeQuery = "";
		
		
		projectTypeQuery = " and (projects.isClientProject IS NULL OR projects.isClientProject = 'N')";
		
		String query= "from Projects projects where  projects.id IN "
				   +"("
					 + " select hierarchy.projectId.id from ProjectApproverHierarchy hierarchy  where  "
					 + " (hierarchy.projectClientsId.orgLocationsId.id = :orgLocationsId and hierarchy.approverUserId.id = :userId "
					 + " OR "
					 + " (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = hierarchy.projectClientsId.orgLocationsId.id) )"
				  + " ))  "+projectTypeQuery;
		List<Projects> projectsList =  session.createQuery(query).setLong("orgLocationsId", dayWiseTimesheetReoprtsDto.getLocationsId()).setLong("userId", dayWiseTimesheetReoprtsDto.getLoginUserId()).list();
		return projectsList;
	}

	@Override
	@Transactional
	public List<Projects> getAllClientProjectsByOrgLocationId(Long locationId) throws Exception {
		
		logger.debug("In ProjectsDaoImpl class getEndClientProjectByOrgLocationsIdAndClientOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects projects where projects.id  IN (select pc.projectId.id from ProjectClients pc where pc.orgLocationsId.id = :orgLocationsId and pc.orgRelationId IS NOT NULL)";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", locationId).list();
		
		return projectsList;
	}

	@Override
	@Transactional
	public List<ProjectsDTO> getProjectListForDaywiseReports(Long orgLocationsId, Long orgId, String isGlobalManager,Long userId,String type,String userRole)
			throws Exception {
	
		List<ProjectsDTO> projectsDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		String subQuery = "";
		String projectTypesubQuery = "";
		
		String userRoleQuery = "";
		
		List<Long> userHierarchyIds =  userDao.getUserHierarchyIdList(userId);
		if(userHierarchyIds == null || userHierarchyIds.isEmpty()) {
			userHierarchyIds.add(0l);
		}
		if(StringUtils.isNotBlank(isGlobalManager) && isGlobalManager.equalsIgnoreCase(Constants.YES)) {
			subQuery = " ORG.ID = :orgId ";
		}else {
			subQuery = " PC.ORG_LOCATIONS_ID = :orgLocationsId ";
		}
		
		if(type.equalsIgnoreCase("internal")) {
			projectTypesubQuery = " AND PC.ORG_RELATION_ID IS NULL ";
		}else if(type.equalsIgnoreCase("client")) {
			projectTypesubQuery = " AND PC.ORG_RELATION_ID IS NOT NULL ";
		}
		
		
			userRoleQuery = " AND ((PROJ.ID IN (SELECT PAH.PROJECT_ID FROM PROJECT_APPROVER_HIERARCHY PAH WHERE PAH.APPROVER_USER_ID = :userId AND PAH.STATUS = '1'))"
					 +" OR "
					 +" (CAND.CONSULTANT_USER_ID IS NOT NULL"
					 +" AND CAND.CONSULTANT_USER_ID IN (SELECT UR.USER_ID FROM USER_ROLE UR WHERE (UR.REPORTING_MANAGER_ID = :userId OR UR.RESOURCE_MANAGER_ID = :userId)))"
					 +" OR"
					 +" (CAND.CONSULTANT_USER_ID IS NOT NULL AND CAND.CONSULTANT_USER_ID IN (:userHierarchyIds))"
					 +" OR"
					 + "(:userId IN (SELECT UR.USER_ID FROM USER_ROLE UR LEFT JOIN  ROLES R  ON R.ID = UR.ROLE_ID   WHERE R.ROLE_NAME = 'ROLE_ADMIN' AND UR.USER_STATE = 'ACTIVE' AND UR.ORG_LOCATIONS_ID = :orgLocationsId)) "
					 +" ) ";
	
		
		 query ="SELECT "
				 +" PROJ.ID AS projId, "
				 +" PROJ.PROJECT_NAME AS projectName "
				 +" FROM "
			     +" PROJECT_CLIENTS PC"
			     +" LEFT JOIN PROJECTS PROJ ON PROJ.ID = PC.PROJECT_ID"
			     +" INNER JOIN PROJECT_MEMBERS PM ON PM.PROJECT_ID = PC.PROJECT_ID"
				 +" LEFT JOIN CANDIDATE CAND ON CAND.ID = PM.CANDIDATE_ID"
				 +" LEFT JOIN ORG_LOCATIONS ORL ON ORL.ID = PC.ORG_LOCATIONS_ID"
				 +" LEFT JOIN ORG ORG ON ORG.ID = ORL.ORG_ID"
			     +" WHERE "+subQuery
				 +userRoleQuery
				 +projectTypesubQuery
				 +" AND PROJ.PROJECT_MODE = 'Finished' "
				 +" GROUP BY PROJ.ID";
		 
            Query queryObj = session.createSQLQuery(query);
            if(StringUtils.isNotBlank(isGlobalManager) && isGlobalManager.equalsIgnoreCase(Constants.YES)) {
            	queryObj.setLong("orgId", orgId);
            }
            
            queryObj.setLong("orgLocationsId", orgLocationsId);
            queryObj.setParameterList("userHierarchyIds", userHierarchyIds);
            queryObj.setLong("userId", userId);
            
			queryObj.setResultTransformer(Transformers.aliasToBean(ProjectsDTO.class));
			projectsDTOList = queryObj.list();
			return projectsDTOList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Projects> getProjectReportList(ProjectReportDTO projectReportDTO, Long orgLocationId) {

		try {
			Session session = this.sessionFactory.getCurrentSession();

			// java.sql.Date sqlDate = null;
			
			Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			   Matcher matcher = pattern.matcher(projectReportDTO.getSearchValue());
			   java.sql.Date sqlDate = null;
			    if(matcher.matches() == true) {
							if(projectReportDTO.getSearchValue().contains("/")||(projectReportDTO.getSearchValue().contains("-"))) {
								String replacedValue = projectReportDTO.getSearchValue().replaceAll("/", "-");
								    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
									SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
							        java.util.Date utilDate = format.parse(replacedValue);
							        sqlDate = new java.sql.Date(utilDate.getTime());
							}
			    }
	
			StringBuilder query1 = new StringBuilder("");
			if(projectReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(projectReportDTO.getSearchValue())) {
			 query1.append(" and ps.projectSequeneNumber like '%" + projectReportDTO.getSearchValue() + "%' ")
					.append("OR ps.projectName like '%" + projectReportDTO.getSearchValue() + "%' ")
					.append("OR ps.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.firstName like '" + projectReportDTO.getSearchValue() + "%' OR pah.approverUserId.lastName like '%" + projectReportDTO.getSearchValue() + "' ) ")
					.append("OR ps.id IN (select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id IN (select user.id from User user where (concat(user.firstName,user.lastName) like '%" + projectReportDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')))" )
					.append("OR ps.id IN (select pm.projects.id from ProjectMembers pm where pm.projectMemberCandidteId.firstName like '" + projectReportDTO.getSearchValue() + "%' OR pm.projectMemberCandidteId.lastName like '%" + projectReportDTO.getSearchValue() + "' ) ")
					.append("OR ps.id IN (select pm.projects.id from ProjectMembers pm where pm.projectMemberCandidteId.id IN (select can.id from Candidate can where (concat(can.firstName,can.lastName) like '%" + projectReportDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')))" )
					.append("OR ps.projectMode like '%" + projectReportDTO.getSearchValue() + "%' ");
			 
			 
			 
			 
				 if(projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("in-progress") || projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("in progress") 
						 || projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("inprogress")) {
					 query1.append("OR ps.projectStatus like '%INPROGRESS%' ");
				 }else if(projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("on-hold") || projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("on hold") 
						 || projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("onhold")) {
					 query1.append("OR ps.projectStatus like '%ONHOLD%' ");
				 }else if(projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("not started") || projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("notstarted")) {
					 query1.append("OR ps.projectStatus like '%NOTSTARTED%' ");
				 }else {
					 query1.append("OR ps.projectStatus like '%" + Project_Status.getNameByValue(projectReportDTO.getSearchValue()) + "%' ");
				 }
				 if(projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("In-Active") ) {
					 query1.append("OR ps.id IN(select pm.projects.id from ProjectMembers pm where pm.status ='0')");

				 }else if(projectReportDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Active") ) {
					 query1.append("OR ps.id IN(select pm.projects.id from ProjectMembers pm where pm.status ='1')");

				 }
				 
			     if(sqlDate != null) {
			    	query1.append("OR ps.startDate like '%" + sqlDate + "%' OR ps.endDate like '%" + sqlDate + "%' ");
			    	query1.append("OR ps.id IN(select pm.projects.id from ProjectMembers pm where pm.projectAssignedDate like '%" + sqlDate + "%' OR pm.projectEndDate like '%" + sqlDate + "%' )");
				 }
				
						
			}
			
			
			String clientQuery = "";
			List<Long> clientIdsList = new LinkedList<>();
			if (projectReportDTO.getProjectClients() != null && !projectReportDTO.getProjectClients().isEmpty()) {
				clientIdsList = projectReportDTO.getProjectClients();
				if (clientIdsList == null || clientIdsList.isEmpty()) {
					clientIdsList.add(0L);
				}
				clientQuery = " and ((:varZero  IN (:clientIdsList)) OR (:varZero NOT IN (:clientIdsList) and pc.orgRelationId.id IN (:clientIdsList)))";
			}
			
			
			String query = "select ps from Projects ps ";
			if (projectReportDTO.getProjectManagers() != null && !projectReportDTO.getProjectManagers().isEmpty()) {
				query += " join ps.projectApprovers pa";
			}
			/*query += " where ps.orgLocationsId.id = :orgLocationsId and ps.id IN(select pc.projectId.id from ProjectClients pc where pc.orgLocationsId = :orgLocationsId "
					+ clientQuery + " "+query1+")";*/
			query += " where ps.id IN(select pc.projectId.id from ProjectClients pc where pc.orgLocationsId = :orgLocationsId "
					+ clientQuery + " "+query1+")";
			if (projectReportDTO.getProjectManagers() != null && !projectReportDTO.getProjectManagers().isEmpty()) {
				query += " and pa.level in (1,2) and pa.approverUserId.id in (:projectManagers)";
			}
			if (projectReportDTO.getProjectStatus() != null && !projectReportDTO.getProjectStatus().isEmpty()) {
				query +=  " and ps.projectStatus in (:projectStatus)";
			}
			if(projectReportDTO.isRefreshFlag()) {
				query += "and ps.id IN(select pm.projects.id from ProjectMembers pm where pm.status ='1')";
			} else if(projectReportDTO.getAssociateStatus() != null && projectReportDTO.getAssociateStatus().length > 0) {
				boolean activeFlag = false, inactiveFlag = false;
				for(String status : projectReportDTO.getAssociateStatus()) {
					if(status.equalsIgnoreCase("ACTIVE")) {
						activeFlag = true;
					}
					if(status.equalsIgnoreCase("INACTIVE")) {
						inactiveFlag = true;
					}
				}
				if (activeFlag && inactiveFlag) {
					
				} else if (inactiveFlag) {
					query += "and ps.id IN(select pm.projects.id from ProjectMembers pm where pm.status ='0')";
				} else if (activeFlag) {
					query += "and ps.id IN(select pm.projects.id from ProjectMembers pm where pm.status ='1')";
				}
			}
			if (projectReportDTO.getProjectIds() != null && !projectReportDTO.getProjectIds().isEmpty()) {
				query += " and ps.id in (:projectIds)";
			}
			
			Query queryObj = session.createQuery(query);
			if (projectReportDTO.getProjectClients() != null && !projectReportDTO.getProjectClients().isEmpty()) {
				queryObj.setLong("varZero", 0).setParameterList("clientIdsList", clientIdsList);
			}
			if (projectReportDTO.getProjectManagers() != null && !projectReportDTO.getProjectManagers().isEmpty()) {
				queryObj.setParameterList("projectManagers", projectReportDTO.getProjectManagers());
			}
			if (projectReportDTO.getProjectStatus() != null && !projectReportDTO.getProjectStatus().isEmpty()) {
				List<Project_Status> enumList = new LinkedList<>();
				for(String status : projectReportDTO.getProjectStatus().split(",")) {
					enumList.add(Project_Status.valueOf(status));
				}
				queryObj.setParameterList("projectStatus", enumList);
			}
			if (projectReportDTO.getProjectIds() != null && !projectReportDTO.getProjectIds().isEmpty()) {
				queryObj.setParameterList("projectIds", projectReportDTO.getProjectIds());
			}
			
			/*if (projectReportDTO.getAssociateStatus() != null && !projectReportDTO.getAssociateStatus().isEmpty()) {
				queryObj.setParameter("associateStatus", projectReportDTO.getAssociateStatus());
			}*/
			
			
			queryObj.setLong("orgLocationsId", orgLocationId);

			List<Projects> projectList = queryObj.list();
			if (projectList != null && !projectList.isEmpty()) {
				return projectList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	  @SuppressWarnings("unchecked")
	    @Override
	    @Transactional
	    public List<Object[]> getAvailableAssociatesReport(AvailableAssociatesDTO availableAssociatesDTO, OrgLocations orgLocations) {
	    try {
	        Session session = this.sessionFactory.getCurrentSession();       
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String fromDate = dateFormat.format(availableAssociatesDTO.getFromDate());
	        String toDate = dateFormat.format(availableAssociatesDTO.getToDate());
	        int noOfHours =((orgLocations.getAverageWorkingHours())*(orgLocations.getWorkingDaysPerWeek()));
	        String query = "select pm.id as id,"
	                + " pm.employee.id as empId,"
	                + " concat(pm.projectMemberCandidteId.firstName,' ',(case when pm.projectMemberCandidteId.lastName is null then '' else pm.projectMemberCandidteId.lastName end)) as empName,"
	                + " pm.projects.projectName as projectName,"
	                + " pm.projects.startDate as projectStartDate,"
	                + " pm.projects.endDate as projectEndDate ,"
	                + " pmHistory.projectAssignedDate as empStartDate,"
	                + " pmHistory.projectTerminationDate as empEndDate,"
	                + "  pm.percentageOfWork as availableHours,"
	                + "  (( pm.percentageOfWork)/100) * " + noOfHours + " as hoursperWeek,"
	                + "  pm.masterCandidateId.id as masterCandidateId"
	                + " from ProjectMembers pm "
	                + " join pm.memberHistoryList pmHistory "
	                + " where pmHistory.status='OPEN' and pm.projectClientsId.orgLocationsId =:orgLocations and pm.projectAssignedDate <= '"+toDate+"'"
	                + "";
	    

	        if (availableAssociatesDTO.getProjectsIds() != null && !availableAssociatesDTO.getProjectsIds().isEmpty()) {
	            String projectIds = "";
	            for (long id : availableAssociatesDTO.getProjectsIds()) {
	                projectIds += (projectIds.isEmpty() ? "" : ", ") + id;
	            }
	             query += " and pm.projects.id in (" + projectIds + ")";
	             
	        }
	        if(availableAssociatesDTO.getUserIds() != null && !availableAssociatesDTO.getUserIds().isEmpty()) {
	            String userIds = "";
	            for (long id : availableAssociatesDTO.getUserIds()) {
	                userIds += (userIds.isEmpty() ? "" : ", ") + id;
	            }
	            query += " and pm.projectMemberCandidteId.id in (" + userIds + ")";
	            
	        }
	        query += " and pm.projects.projectStatus = '" +  Project_Status.INPROGRESS.name()+ "'";
	        Query queryObj = session.createQuery(query);
	        queryObj.setLong("orgLocations",orgLocations.getId());
	        List<Object[]> queryObjList = queryObj.list();
	     return queryObjList;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	    }

	@Override
	@Transactional
	public List<Projects> getProjectsListByCanddateIdAndOrgId(String associatesId, Long orglocationId) {
		try {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pm.projects from ProjectMembers pm  where pm.status = '1' and pm.projectMemberCandidteId.id in(" + associatesId+  ") ";
		List<Projects> projectsList = session.createQuery(query).list();
		return projectsList;

		} catch (Exception e) {
		e.printStackTrace();
		}
		return null;
		}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ProjectMembers isUserAssignedToProject(User user, Long orgLocationId,Long projectId)
	{
		ProjectMembers projectMember = null;;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<ProjectMembers> assignedList = session.createQuery("from ProjectMembers pm where pm.projectMemberCandidteId.consultantUser.id = :userId AND pm.projectMemberCandidteId.orgLocationId.id =:orglocationId AND pm.projects.id = :projectId AND pm.status = 1")
													   .setLong("userId", user.getId())
													   .setLong("orglocationId", orgLocationId)
													   .setLong("projectId", projectId).list();
			if (assignedList != null && assignedList.size() > 0) {
				projectMember = assignedList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectMember;
	}
	
	
	@Override
	@Transactional
	public List<ProjectsDTO> getProjectListForProjectTimeEntriesReports(Long orgLocationsId, Long orgId,
			String isGlobalManager, Long userId, String type, String userRole) throws Exception {
		

		
		List<ProjectsDTO> projectsDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		String subQuery = "";
		String projectTypesubQuery = "";
		
		String userRoleQuery = "";
		
		List<Long> userHierarchyIds =  userDao.getUserHierarchyIdList(userId);
		if(userHierarchyIds == null || userHierarchyIds.isEmpty()) {
			userHierarchyIds.add(0l);
		}
		if(StringUtils.isNotBlank(isGlobalManager) && isGlobalManager.equalsIgnoreCase(Constants.YES)) {
			subQuery = " ORG.ID = :orgId ";
		}else {
			subQuery = " PC.ORG_LOCATIONS_ID = :orgLocationsId ";
		}
		
		if(type.equalsIgnoreCase("internal")) {
			projectTypesubQuery = " AND PC.ORG_RELATION_ID IS NULL ";
		}else if(type.equalsIgnoreCase("client")) {
			projectTypesubQuery = " AND PC.ORG_RELATION_ID IS NOT NULL ";
		}
		
		
			userRoleQuery = " AND ((PROJ.ID IN (SELECT PAH.PROJECT_ID FROM PROJECT_APPROVER_HIERARCHY PAH WHERE PAH.APPROVER_USER_ID = :userId AND PAH.STATUS = '1'))"
					 +" OR"
					 + "(:userId IN (SELECT UR.USER_ID FROM USER_ROLE UR LEFT JOIN  ROLES R  ON R.ID = UR.ROLE_ID   WHERE R.ROLE_NAME = 'ROLE_ADMIN' AND UR.USER_STATE = 'ACTIVE' AND UR.ORG_LOCATIONS_ID = :orgLocationsId)) "
					 +" ) ";
	
		
		 query ="SELECT "
				 +" PROJ.ID AS projId, "
				 +" PROJ.PROJECT_NAME AS projectName "
				 +" FROM "
			     +" PROJECT_CLIENTS PC"
			     +" LEFT JOIN PROJECTS PROJ ON PROJ.ID = PC.PROJECT_ID"
			     +" INNER JOIN PROJECT_MEMBERS PM ON PM.PROJECT_ID = PC.PROJECT_ID"
				 +" LEFT JOIN CANDIDATE CAND ON CAND.ID = PM.CANDIDATE_ID"
				 +" LEFT JOIN ORG_LOCATIONS ORL ON ORL.ID = PC.ORG_LOCATIONS_ID"
				 +" LEFT JOIN ORG ORG ON ORG.ID = ORL.ORG_ID"
			     +" WHERE "+subQuery
				 +userRoleQuery
				 +projectTypesubQuery
				 +" AND PROJ.PROJECT_MODE = 'Finished' "
				 +" GROUP BY PROJ.ID";
		 
            Query queryObj = session.createSQLQuery(query);
            if(StringUtils.isNotBlank(isGlobalManager) && isGlobalManager.equalsIgnoreCase(Constants.YES)) {
            	queryObj.setLong("orgId", orgId);
            }
            
            queryObj.setLong("orgLocationsId", orgLocationsId);
            queryObj.setLong("userId", userId);
            
			queryObj.setResultTransformer(Transformers.aliasToBean(ProjectsDTO.class));
			projectsDTOList = queryObj.list();
			return projectsDTOList;
		
	
	}

	@Override
	@Transactional
	public List<ProjectsDTO> getProjectListForResourceTimeEntriesReports(Long orgLocationsId, Long orgId,
			String isGlobalManager, Long userId, String type, String userRole) throws Exception {
		
		List<ProjectsDTO> projectsDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		String subQuery = "";
		String projectTypesubQuery = "";
		
		String userRoleQuery = "";
		
		List<Long> userHierarchyIds =  userDao.getUserHierarchyIdList(userId);
		if(userHierarchyIds == null || userHierarchyIds.isEmpty()) {
			userHierarchyIds.add(0l);
		}
		if(StringUtils.isNotBlank(isGlobalManager) && isGlobalManager.equalsIgnoreCase(Constants.YES)) {
			subQuery = " ORG.ID = :orgId ";
		}else {
			subQuery = " PC.ORG_LOCATIONS_ID = :orgLocationsId ";
		}
		
		if(type.equalsIgnoreCase("internal")) {
			projectTypesubQuery = " AND PC.ORG_RELATION_ID IS NULL ";
		}else if(type.equalsIgnoreCase("client")) {
			projectTypesubQuery = " AND PC.ORG_RELATION_ID IS NOT NULL ";
		}
		
		
			userRoleQuery = " AND ((CAND.CONSULTANT_USER_ID IS NOT NULL"
					 +" AND CAND.CONSULTANT_USER_ID IN (SELECT UR.USER_ID FROM USER_ROLE UR WHERE (UR.REPORTING_MANAGER_ID = :userId OR UR.RESOURCE_MANAGER_ID = :userId)))"
					 +" OR"
					 + "(:userId IN (SELECT UR.USER_ID FROM USER_ROLE UR LEFT JOIN  ROLES R  ON R.ID = UR.ROLE_ID   WHERE R.ROLE_NAME = 'ROLE_ADMIN' AND UR.USER_STATE = 'ACTIVE' AND UR.ORG_LOCATIONS_ID = :orgLocationsId)) "
					 +" ) ";
	
		
		 query ="SELECT "
				 +" PROJ.ID AS projId, "
				 +" PROJ.PROJECT_NAME AS projectName "
				 +" FROM "
			     +" PROJECT_CLIENTS PC"
			     +" LEFT JOIN PROJECTS PROJ ON PROJ.ID = PC.PROJECT_ID"
			     +" INNER JOIN PROJECT_MEMBERS PM ON PM.PROJECT_ID = PC.PROJECT_ID"
				 +" LEFT JOIN CANDIDATE CAND ON CAND.ID = PM.CANDIDATE_ID"
				 +" LEFT JOIN ORG_LOCATIONS ORL ON ORL.ID = PC.ORG_LOCATIONS_ID"
				 +" LEFT JOIN ORG ORG ON ORG.ID = ORL.ORG_ID"
			     +" WHERE "+subQuery
				 +userRoleQuery
				 +projectTypesubQuery
				 +" AND PROJ.PROJECT_MODE = 'Finished' "
				 +" GROUP BY PROJ.ID";
		 
            Query queryObj = session.createSQLQuery(query);
            if(StringUtils.isNotBlank(isGlobalManager) && isGlobalManager.equalsIgnoreCase(Constants.YES)) {
            	queryObj.setLong("orgId", orgId);
            }
            
            queryObj.setLong("orgLocationsId", orgLocationsId);
            queryObj.setLong("userId", userId);
            
			queryObj.setResultTransformer(Transformers.aliasToBean(ProjectsDTO.class));
			projectsDTOList = queryObj.list();
			return projectsDTOList;
	
	}

}

