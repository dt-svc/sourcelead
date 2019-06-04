package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ProjectTasksDaoImpl implements ProjectTasksDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	OrgLocationService orgLocationService;
	
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
	
	@Value("${jdbc.schema}")
	private String schema;

	private static final Logger logger = LoggerFactory.getLogger(ProjectTasksDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks addTasks(ProjectTasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(tasks);
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECTS_TASKS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return tasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks updateTasks(ProjectTasks tasks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try{
			if(tasks != null){
				session.saveOrUpdate(tasks);
			}
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"PROJECTS_TASKS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return tasks;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks getTasksId(String projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt left join fetch pt.severity severity left join fetch pt.projectId left join fetch pt.taskType taskType left join fetch pt.priority priority where pt.id = :projectTaskId " ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setString("projectTaskId", projectTaskId) .list(); 
		ProjectTasks projectTasks = null;
		if(projectTasksList != null && !projectTasksList.isEmpty())
		{
			projectTasks = (ProjectTasks) projectTasksList.iterator().next();
		}
		return projectTasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTask(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try{
			ProjectTasks tasks = (ProjectTasks) session.get(ProjectTasks.class, Long.valueOf(id));
			tasks.setStatus("1");
			session.saveOrUpdate(tasks);
			isDeleted = true;
			logger.info("Successfully deleted !!!");
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"TASK"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		
		return isDeleted;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasks() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectTasks> adTaskList = null;
		Query taskListQuery = session.createQuery("from ProjectTasks");
		adTaskList = taskListQuery.list();
		;
		return adTaskList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.status = '1' " ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()) 
			    .list(); 
		return projectTasksList;  
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,
			List<Long> avilableProjectTaskIds) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		if(avilableProjectTaskIds != null  && !avilableProjectTaskIds.isEmpty()){
			String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.status = '1' and pt.id NOT IN (:avilableProjectTaskIds) ";
			List<ProjectTasks> projectTasksList = session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setParameterList("avilableProjectTaskIds", avilableProjectTaskIds).list();
			return projectTasksList;
			
		}else{
			String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.status = '1'";
			List<ProjectTasks> projectTasksList = session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).list();
			return projectTasksList;
			
		}
		
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllSourceleadTasks() throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks projectsTasks where projectsTasks.projectId IS NULL and  projectsTasks.createdBy = 'SLADMIN'";
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).list();
		return projectTasksList; 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveAll(Collection<ProjectTasks> taskList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(taskList != null && !taskList.isEmpty()){
				for(ProjectTasks tasks :taskList){
					session.save(tasks);
				}
			}
		
		} catch (HibernateException exception) {
		}
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks validateTaskNameByTaskId(Long taskId, String taskName, Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks projectsTasks where projectsTasks.projectId =:projectId and projectsTasks.taskName =:taskName and projectsTasks.id =:taskId";
		List<ProjectTasks> projectTasksList =session.createQuery(query) .setString("taskName", taskName).setLong("taskId", taskId).setLong("projectId", projectId).list();
		ProjectTasks projectTasks = null;
		if(projectTasksList != null && !projectTasksList.isEmpty())
		{
			projectTasks = (ProjectTasks) projectTasksList.iterator().next();
		}
		return projectTasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId, Long TaskType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.taskType = :TaskType and pt.status ='1'" ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setLong("TaskType", TaskType).list(); 
		return projectTasksList; 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndSubTaskType(Long projectId, Long subTaskType)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.taskType <> :subTaskType and pt.status ='1'" ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setLong("subTaskType", subTaskType).list(); 
		return projectTasksList; 
	} 
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId, String taskType, String taskStatus) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		
		String[] type1 = null;
		if(null != taskType && !"".equals(taskType)){	
			 type1 = taskType.split(",");
		}
		List<Long> taskTypeList = new ArrayList<>();
		if(type1 != null){
			for(int i=0;i<type1.length;i++){
				taskTypeList.add(Long.parseLong(type1[i]));
			}
		}
		
		String[] status1 = null;
		if(null != taskStatus && !"".equals(taskStatus)){	
			status1 = taskStatus.split(",");
		}
		List<Long> taskStatusList = new ArrayList<>();
		if(status1 != null){
			for(int i=0;i<status1.length;i++){
				taskStatusList.add(Long.parseLong(status1[i]));
			}
		}
		
		String query = null;
		List<ProjectTasks> projectTasksList  = null;
		if((taskType != null && taskType != "")  || (taskStatus != null && taskStatus != "")) {
			if(taskType != null && taskType != "") {
				 query = "from ProjectTasks pt where pt.projectId.id = :projectId  and pt.taskType.id IN (:taskTypeList)" ;
				 projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setParameterList("taskTypeList", taskTypeList).list(); 
			}
			if(taskStatus != null && taskStatus != "") {
				query = "from ProjectTasks pt where pt.projectId.id = :projectId  and pt.taskStatus.id IN (:taskStatusList)" ;
				 projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).setParameterList("taskStatusList", taskStatusList).list(); 
			}
		}else {
			 query = "from ProjectTasks pt where pt.projectId.id = :projectId" ;
			 projectTasksList =session.createQuery(query.toString()).setInteger("projectId", projectId.intValue()).list(); 
			
		}
	   return projectTasksList;  
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndParentTaskId(Long projectId, Long parentTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query  = "from ProjectTasks pt where pt.projectId.id = :projectId  and pt.parentTaskId =:parentTaskId and pt.status ='1'" ;
		List<ProjectTasks>  projectTasksList =session.createQuery(query.toString()).setLong("projectId", projectId).setLong("parentTaskId", parentTaskId).list(); 
		 return projectTasksList;  
	}
	
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getRequirementGeneratedIdByOrgLocationId(Long orgLocationId,Integer generatedIdCount) {
	Session session = this.sessionFactory.getCurrentSession();
	String query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'"+generatedIdCount+"') AS UNSIGNED)) FROM "+schema+".requirement WHERE ORG_LOCATION_ID = '"+ orgLocationId + "'";
	List<BigInteger> maxCountList = session.createSQLQuery(query).list();
	Long count = 0L;
	if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
	count = (Long) maxCountList.iterator().next().longValue();
	}
	return count;
	}
*/
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getProjectTasksGeneratedIdByProjectId(Long projectId, Integer generatedIdCount) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT count(*) FROM "+schema+".PROJECT_TASKS WHERE PROJECT_ID = '"+ projectId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
		count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks getProjectTasksByUUID(String taskUUD) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt left join fetch pt.severity severity left join fetch pt.taskType taskType left join fetch pt.priority priority where pt.projectTasksUUID = :taskUUD " ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setString("taskUUD", taskUUD) .list(); 
		ProjectTasks projectTasks = null;
		if(projectTasksList != null && !projectTasksList.isEmpty())
		{
			projectTasks = (ProjectTasks) projectTasksList.iterator().next();
		}
		return projectTasks;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getProjectTasksListCountByProjectIdAndStatus(Long projectId, String status) throws Exception {
		int totalCount = 0;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.projectId.id =:projectId and pt.taskStatus =:status";
		List<ProjectTasks> ptList = session.createQuery(query).setLong("projectId",projectId).setString("status", status).list();
		if(!ptList.isEmpty() && ptList.size() > 0){
			totalCount = ptList.size();
		}
		return totalCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getOverdueTaskListByProjectId(Long projectId, Date startDateUTC, Date endDateUTC) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ProjectTasks pt where pt.projectId.id = :projectId and pt.status='1' and pt.dueDate < :dt and pt.createdDate >= :startDateUTC and pt.createdDate <= :endDateUTC" ;
			List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setLong("projectId", projectId).setDate("dt", commonUtil.currentDate()).setDate("startDateUTC", startDateUTC).setDate("endDateUTC", endDateUTC) .list(); 
			return projectTasksList;  
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getAllTasksByProjectIdAndFilter(Long projectId, Date startDateUTC, Date endDateUTC) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt  where pt.projectId.id = :projectId and pt.status='1' and pt.createdDate >= :startDateUTC and pt.createdDate <= :endDateUTC and pt.taskType.name != 'General'" ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setLong("projectId", projectId).setDate("startDateUTC", startDateUTC).setDate("endDateUTC", endDateUTC).list(); 
		return projectTasksList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getProjectTasksCountByProjectId(Long projectId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from ProjectTasks pt where pt.projectId.id = :projectId ";
		List<Long> tasksCount = session.createQuery(query.toString()).setLong("projectId", projectId).list();
			Integer count = 0;
			if(tasksCount != null && !tasksCount.isEmpty()){
					count = tasksCount.get(0).intValue();
			}
			return count;
	}
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })*/
	public List<ProjectTasks> addProjectTasksForLoginUserAndLeaveType(User user,Long orgId,Long orgLocationId,String leaveType,String billableType) throws Exception {
		List<ProjectTasks> projectTaskList = new ArrayList<>();
		Candidate candidate = candidateDao.findByCandidateEmail(user.getEmail(), orgLocationId);
		if(candidate != null){
			List<ProjectMembers> projectMembersList =  projectMembersDao.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLocationId,orgId,candidate.getId());
			if(projectMembersList != null && !projectMembersList.isEmpty()){
				
			}
		}
		
		/*Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from ProjectTasks pt where pt.projectId.id = :projectId ";
		List<Long> tasksCount = session.createQuery(query.toString()).setLong("projectId", projectId).list();
			Integer count = 0;
			if(tasksCount != null && !tasksCount.isEmpty()){
					count = tasksCount.get(0).intValue();
			}*/
			return projectTaskList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ProjectTasks> getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(ProjectTasksDTO projectTasksDTO, Long projectId, String taskType, String taskStatus, String userRole,String assignedMembers) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		List<Long> teamsIdsList = null;
		Integer filterCount = 0;
		ResponseList<ProjectTasks> response = new ResponseList<>();
		 
		StringBuilder query1 = new StringBuilder("");
		if(projectTasksDTO.getSearchValue() !=null && !"".equalsIgnoreCase(projectTasksDTO.getSearchValue())) {
		 query1.append("and (pt.taskName like '%" + projectTasksDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (pt.generatedId like '%" + projectTasksDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (concat(pt.generatedId,pt.taskName) like '%" + projectTasksDTO.getSearchValue().replace("'", "").replace("-", "").replaceAll("[\\s]+", "").trim() + "%' )")
		       .append("OR (pt.priority IS NOT NULL and pt.priority.id IN(Select ld.id from LookUpData ld where ld.name like '%" + projectTasksDTO.getSearchValue().replace("'", "") + "%' ))")
			   .append("OR pt.taskType.name = '%" + projectTasksDTO.getSearchValue().replace("'", "") + "%' ")
			   .append("OR pt.taskStatus.name like '%" + projectTasksDTO.getSearchValue().replace("'", "") + "%' )");
					
		}
		 //concat(userRole.user.firstName,userRole.user.lastName) like
		 String queryStr = "";
		 String queryStrForCount = "";
		 if(projectTasksDTO.getTasksFilter().equalsIgnoreCase("all")) {
			  queryStr = "from ProjectTasks pt left join fetch pt.priority priority  where pt.projectId.id = :projectId and pt.status ='1'" ;
			  queryStrForCount = "Select count (*) from ProjectTasks pt where pt.projectId.id = :projectId and pt.status ='1' " ;

		 }else {
			 String myAssign = "pt.id IN(SELECT pat.projectTaskId.id from ProjectAssignTask pat where pat.projectMemberId.projectMemberCandidteId.id IN ("+projectTasksDTO.getCandidateId()+"))";
			  queryStr = "from ProjectTasks pt left join fetch pt.priority priority  where pt.projectId.id = :projectId and (pt.createdBy ='"+projectTasksDTO.getUserCode()+"' OR "+myAssign+")  and pt.status ='1'" ;
			  
			  queryStrForCount = "Select count (*) from ProjectTasks pt where pt.projectId.id = :projectId and (pt.createdBy ='"+projectTasksDTO.getUserCode()+"' OR "+myAssign+") and pt.status ='1' " ;

		 }
		 
		 String subQueryStr = "";
		 if(StringUtils.isNotEmpty(taskType)) {
			 subQueryStr = subQueryStr+" and pt.taskType.id IN ("+taskType+")";
		 }
		 if(StringUtils.isNotEmpty(taskStatus)) {
			 subQueryStr = subQueryStr+" and pt.taskStatus.id IN ("+taskStatus+") ";
		 }
		 if(StringUtils.isNotEmpty(assignedMembers)) {
			 subQueryStr = subQueryStr+" and pt.id IN(SELECT pat.projectTaskId.id from ProjectAssignTask pat where pat.projectMemberId.id IN ("+assignedMembers+")) ";
		 } 
		 
		 if(StringUtils.isNotEmpty(projectTasksDTO.getCreatedByFilter())) {
			 subQueryStr = subQueryStr+"  and pt.createdBy IN(select pm.projectMemberCandidteId.consultantUser.userCode from ProjectMembers pm where pm.id IN ("+projectTasksDTO.getCreatedByFilter()+")) ";
		 }
		 
		 if(StringUtils.isNotEmpty(projectTasksDTO.getPriorityFilter())) {
			 subQueryStr = subQueryStr+" and pt.priority.id IN ("+projectTasksDTO.getPriorityFilter()+") ";
		 }
		 
		 if(StringUtils.isNotEmpty(projectTasksDTO.getSeverityFilter())) {
			 subQueryStr = subQueryStr+" and pt.severity.id IN ("+projectTasksDTO.getSeverityFilter()+") ";
		 }
		 
		 List<String> tagsList = new ArrayList<>();
			if(projectTasksDTO.getTags() != null && !projectTasksDTO.getTags().isEmpty()) {
				String[] tags = projectTasksDTO.getTags().split(",");
				for(int i=0; i<tags.length; i++){
					tagsList.add(tags[i]);
				}
			}else {
				tagsList.add("");
			}
			
			String tagsQuery = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and pt.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

			
		 if(StringUtils.isNotEmpty(projectTasksDTO.getTeams())) {
			String[] teamIds = projectTasksDTO.getTeams().split(",");
			teamsIdsList = new ArrayList<>();
			for(int i=0; i<teamIds.length; i++) {
				teamsIdsList.add(Long.valueOf(teamIds[i]));
			}
			 subQueryStr = subQueryStr+" and pt.id IN(SELECT pat.projectTaskId.id from ProjectAssignTask pat where pat.projectMemberId.projectMemberCandidteId.consultantUser.id IN (select teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.status = '1' and teamMembers.teamId.id IN (:teamsIdsList))) ";
		 }
		 
		 
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(projectTasksDTO.getSortColumn())) {
				sortingQry = " order by pt."+ projectTasksDTO.getSortColumn() +" "+projectTasksDTO.getSortOrder();
		}
		
		 Query query = session.createQuery(queryStr+subQueryStr+query1+tagsQuery+sortingQry);
		 Query countQuery = session.createQuery(queryStrForCount+subQueryStr+query1+tagsQuery+sortingQry);
		 List<ProjectTasks> projectTasksList = null;
		 List<ProjectTasks> tasksList = new CopyOnWriteArrayList<ProjectTasks>();
		 if(StringUtils.isNotEmpty(projectTasksDTO.getTeams())) {
			 projectTasksList = query.setLong("projectId", projectId).setParameterList("teamsIdsList", teamsIdsList).setString("varString", "").setParameterList("tagsList", tagsList).setFirstResult(projectTasksDTO.getStart()).setMaxResults(projectTasksDTO.getLength()).list();
			 countlist = countQuery.setLong("projectId", projectId).setParameterList("teamsIdsList", teamsIdsList).setString("varString", "").setParameterList("tagsList", tagsList).list();
			 filterCount = countlist.iterator().next().intValue();
		 }else {
			 projectTasksList = query.setLong("projectId", projectId).setFirstResult(projectTasksDTO.getStart()).setString("varString", "").setParameterList("tagsList", tagsList).setMaxResults(projectTasksDTO.getLength()).list();
			 countlist = countQuery.setLong("projectId", projectId).setString("varString", "").setParameterList("tagsList", tagsList).list();
			 filterCount = countlist.iterator().next().intValue();
		 }
		 
		
		int totalCount = 0;
		if (projectTasksDTO != null) {
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
		
		if(projectTasksList != null && !projectTasksList.isEmpty()) {
			List<Long> assignedMembersIds = projectMembersDao.getAllProjectMembersIdsByProjectIdAndOrgLocationId(projectId, projectTasksDTO.getOrgLocationId());
			if(!assignedMembersIds.isEmpty()) {
				for(ProjectTasks pt : projectTasksList) {
					List<String> assignedTaskUsersList =  new ArrayList<>();
					Set<ProjectAssignTask> assignTasksList = pt.getProjectAssignTask();
					if(assignTasksList != null && !assignTasksList.isEmpty()) {
						for(ProjectAssignTask pat :assignTasksList) {
							if(assignedMembersIds.contains(pat.getProjectMemberId().getId().longValue())) {
								assignedTaskUsersList.add(pat.getProjectMemberId().getProjectMemberCandidteId().getFullName());
							}
						}
					}
					
					pt.setAssignedTaskUsers(assignedTaskUsersList);
					tasksList.add(pt);
				}
			}
			
		}
		
		
		response.setData(projectTasksList);
		return response;
		}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks getProjectTasksByProjectIdAndProjectIdAndTaskName(Long projectId, String taskName)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks projectsTasks left join fetch projectsTasks.projectId where projectsTasks.projectId =:projectId and projectsTasks.taskName =:taskName";
		List<ProjectTasks> projectTasksList =session.createQuery(query) .setString("taskName", taskName).setLong("projectId", projectId).list();
		ProjectTasks projectTasks = null;
		if(projectTasksList != null && !projectTasksList.isEmpty())
		{
			projectTasks = (ProjectTasks) projectTasksList.iterator().next();
		}
		return projectTasks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks getProjectTaskByleaveTypeId(long leaveTypeId,Long projectId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks projectsTasks left join fetch projectsTasks.leaveType leave left join fetch projectsTasks.projectId proId where leave.id =:leaveTypeId and proId =:projectId";
		List<ProjectTasks> projectTasksList =session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("projectId", projectId).list();
		ProjectTasks projectTasks = null;
		if(projectTasksList != null && !projectTasksList.isEmpty())
		{
			projectTasks = (ProjectTasks) projectTasksList.iterator().next();
		}
		return projectTasks;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getTasksListByLeaveType(Long levaTypeId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectTasks pt where pt.leaveType.id = :levaTypeId ";
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setLong("levaTypeId", levaTypeId).list(); 
		return projectTasksList; 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectTasks> getTasksListByLookupIdAndCategoryType(Long lookupId, String taskType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String quey = "";
		if(taskType.equalsIgnoreCase(Constants.TASKTYPE)) {
			quey = "pt.taskType.id =:lookupId";
		}else if(taskType.equalsIgnoreCase(Constants.TASKSEVERITY)) {
			quey = "pt.severity.id =:lookupId";
		}else if(taskType.equalsIgnoreCase(Constants.TASKPRIORITY)) {
			quey = "pt.priority.id =:lookupId";
		}else if(taskType.equalsIgnoreCase(Constants.TASKSTATUS)) {
			quey = "pt.taskStatus.id =:lookupId";
		} 
		String query = "from ProjectTasks pt where "+quey+" ";
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setLong("lookupId", lookupId).list(); 
		return projectTasksList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ProjectTasks> getOverdueTaskReportListByOrglocationId(ProjectTasksDTO  projectTasksDTO,Date[] searchDates) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<ProjectTasks> response = new ResponseList<>();
		Date startDate = new java.sql.Date(searchDates[0].getTime());
		Date endDate = new java.sql.Date(searchDates[1].getTime());
		
		OrgLocations orgLoc = orgLocationService.getOrgLocations(projectTasksDTO.getOrgLocationId());
		SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(projectTasksDTO.getSearchValue());
		   java.sql.Date sqlDate = null;
		    if(matcher.matches() == true) {
						if(projectTasksDTO.getSearchValue().contains("/")||(projectTasksDTO.getSearchValue().contains("-"))) {
							String replacedValue = projectTasksDTO.getSearchValue().replaceAll("/", "-");
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		    
		StringBuilder query1 = new StringBuilder("");
		if(projectTasksDTO.getSearchValue() !=null && !"".equalsIgnoreCase(projectTasksDTO.getSearchValue())) {
		 query1.append("and (pt.taskName like '%" + projectTasksDTO.getSearchValue() + "%' ")
		       .append("OR pt.priority.name like '%" + projectTasksDTO.getSearchValue() + "%' ")
		       .append("OR pt.taskType.name like '%" + projectTasksDTO.getSearchValue() + "%' ")
		       .append("OR pt.projectId.projectName like '%" + projectTasksDTO.getSearchValue() + "%' )");
		    if(sqlDate != null) {
		    	query1.append("OR pt.dueDate like '%" + sqlDate + "%' ");
			 }
		}
		
		//usertList sorting order
				String sortingQry = "";
				if(StringUtils.isNotBlank(projectTasksDTO.getSortColumn())) {
					if(projectTasksDTO.getSortColumn().equalsIgnoreCase("projectName")) {
						sortingQry = "order by pt.projectId."+ projectTasksDTO.getSortColumn() +" "+projectTasksDTO.getSortOrder();
					}else {
						sortingQry = "order by pt."+ projectTasksDTO.getSortColumn() +" "+projectTasksDTO.getSortOrder();
					}
				}
				
				 String subQueryStr = "";
				 if(StringUtils.isNotEmpty(projectTasksDTO.getPriorityFilter())) {
					 subQueryStr = subQueryStr+" and pt.priority.id IN ("+projectTasksDTO.getPriorityFilter()+") ";
				 }
				  
				 if(StringUtils.isNotEmpty(projectTasksDTO.getTaskTypeFilter())) {
					 subQueryStr = subQueryStr+" and pt.taskType.id IN ("+projectTasksDTO.getTaskTypeFilter()+") ";
				 }
				 if(StringUtils.isNotEmpty(projectTasksDTO.getProjectFilter())) {
					 subQueryStr = subQueryStr+" and pt.projectId.id IN ("+projectTasksDTO.getProjectFilter()+") ";
				 }
				 
				 if(StringUtils.isNotEmpty(projectTasksDTO.getToDate()) && StringUtils.isNotEmpty(projectTasksDTO.getFromDate())) {
					 Date fromDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), projectTasksDTO.getFromDate());
					 java.sql.Date customeStartDate = new java.sql.Date(fromDate.getTime()); 
					 Date toDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), projectTasksDTO.getToDate());
					 java.sql.Date  customeEndDate = new java.sql.Date(toDate.getTime()); 
					 
					 subQueryStr = subQueryStr+" and date(pt.dueDate) BETWEEN '"+customeStartDate+"' and '"+customeEndDate+"' ";
				 }
				 
		String query = "from ProjectTasks pt where pt.projectId.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.id = :orgLocationId) and pt.status='1'"
				+ " and pt.dueDate < :dt and pt.createdDate >= :startDateUTC and pt.createdDate <= :endDateUTC "+query1+" "+subQueryStr+" "+sortingQry+" " ;
		List<ProjectTasks> projectTasksList =session.createQuery(query.toString()).setLong("orgLocationId", projectTasksDTO.getOrgLocationId()).setDate("dt", commonUtil.currentDate()).setDate("startDateUTC", startDate).setDate("endDateUTC", endDate).setFirstResult(projectTasksDTO.getStart()).setMaxResults(projectTasksDTO.getLength()).list(); 
		
		String queryStrForCount = "Select count (*) from ProjectTasks pt "
				+ " where pt.projectId.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.id = :orgLocationId)  and pt.status='1' "
				+ "and pt.dueDate < :dt and pt.createdDate >= :startDateUTC and pt.createdDate <= :endDateUTC "+query1+" "+subQueryStr+" "+sortingQry+" ";
		countlist = session.createQuery(queryStrForCount.toString()).setLong("orgLocationId", projectTasksDTO.getOrgLocationId()).setDate("dt", commonUtil.currentDate()).setDate("startDateUTC", startDate).setDate("endDateUTC", endDate).list();

		filterCount = countlist.iterator().next().intValue();
		
		int totalCount = 0;
		if (projectTasksDTO != null) {
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
		
		response.setData(projectTasksList);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectTasks getTasksById(Long projectsTaskId)
	{
		ProjectTasks projectTasks = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from ProjectTasks pt where pt.projectsTaskId = :projectsTaskId ";
			List<ProjectTasks> projectTasksList = session.createQuery(query.toString()).setLong("projectsTaskId", projectsTaskId).list();
			if (projectTasksList != null && projectTasksList.size() > 0) {
				projectTasks = projectTasksList.get(0);
			}
		} catch (Exception e) {
		}
		return projectTasks;
	}
}
