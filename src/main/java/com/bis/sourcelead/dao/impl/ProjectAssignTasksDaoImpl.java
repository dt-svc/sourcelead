package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dao.ProjectAssignTasksDao;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional(rollbackFor = { Exception.class })
public class ProjectAssignTasksDaoImpl implements ProjectAssignTasksDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    OrgLocationService orgLocationService;
	
	@Autowired
	CommonUtil commonUtil;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectAssignTasksDaoImpl.class);

	@Override
	public ProjectAssignTask saveProjectAssignTask(ProjectAssignTask projectAssignTask) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectAssignTask != null) {
				session.saveOrUpdate(projectAssignTask);
			}
			logger.debug("Saving Department");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ProjectAssignTask"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectAssignTask;
	}

	@Override
	public ProjectAssignTask getProjectAssignTaskById(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.status ='1'";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).list();
		ProjectAssignTask projectAssignTask = null;
		if (projectassignTaskList != null && projectassignTaskList.size() > 0) {
			projectAssignTask = (ProjectAssignTask) projectassignTaskList.iterator().next();
		}
		return projectAssignTask;
	}

	@Override
	public List<ProjectAssignTask> getProjectAssignTaskListByProjectTaskId(Long projectTaskId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.status ='1'";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).list();
		return projectassignTaskList;
	}

	@Override
	public List<Long> getProjectAssignTaskListOfLongByProjectTaskId(Long projectTaskId,List<Long> assignedMembersIds) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select projectAssignTask.projectMemberId.id from ProjectAssignTask  projectAssignTask  "
				+ "where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.status ='1' "
				+ " and projectAssignTask.projectMemberId.id IN (:assignedMembersIds) ";
		List<Long> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).setParameterList("assignedMembersIds", assignedMembersIds).list();
		return projectassignTaskList;
	}

	@Override
	public ProjectAssignTask deleteProjectAssignTask(ProjectAssignTask ProjectAssignTask) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (ProjectAssignTask != null) {
				session.delete(ProjectAssignTask);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_ASSIGN_TASK"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return null;
	}

	@Override
	public ProjectAssignTask getProjectAssignTaskByIdAndMemberId(Long projectTaskId, Long memberId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectAssignTask  projectAssignTask  where projectAssignTask.projectTaskId.id =:projectTaskId and projectAssignTask.projectMemberId.id =:memberId and projectAssignTask.status ='1'";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("projectTaskId", projectTaskId).setLong("memberId", memberId).list();
		ProjectAssignTask projectAssignTask = null;
		if (projectassignTaskList != null && projectassignTaskList.size() > 0) {
			projectAssignTask = (ProjectAssignTask) projectassignTaskList.iterator().next();
		}
		return projectAssignTask;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProjectassignTasksDTO> getProjectAssignTaskListByOrglocationId(Long orglocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " SELECT assignTask.ID as assignId, " + 
				       "  can.ID as candidateId, " + 
				       "  can.FIRST_NAME as firstName, " + 
				       "  can.LAST_NAME as lastName, " + 
				       "  can.MIDDLE_NAME as middleName ,  " + 
				       "  can.FILE_URL as fileUrl,"+
				       "  assignTask.PROJECT_MEMBER_ID as memberId, " + 
				       "  assignTask.PROJECT_TASK_ID as taskId, " + 
				       "  proj.PROJECT_NAME as projectName, " + 
				       "  lData.ID as lookupId, " + 
				       "  lData.NAME as taskStatus " + 
				       " FROM sourcelead.PROJECT_ASSIGN_TASK assignTask,PROJECT_TASKS ptasks,PROJECTS proj,LOOKUP_DATA lData,PROJECT_MEMBERS projMem,CANDIDATE can, PROJECT_CLIENTS pc " + 
				       " where assignTask.PROJECT_TASK_ID = ptasks.ID AND ptasks.PROJECT_ID = proj.ID  " + 
				       " AND lData.ID = ptasks.TASK_STATUS " + 
				       " AND projMem.ID = assignTask.PROJECT_MEMBER_ID " + 
				       " AND can.ID = projMem.CANDIDATE_ID " + 
				       " AND pc.PROJECT_ID = proj.ID "+
				       " AND can.ORG_LOCATION_ID = pc.ORG_LOCATIONS_ID "+
				       " AND pc.ORG_LOCATIONS_ID ="+orglocationId;
				       
				        Query queryObj = session.createSQLQuery(query);
						queryObj.setResultTransformer(Transformers.aliasToBean(ProjectassignTasksDTO.class));
						List<ProjectassignTasksDTO> projectassignTaskList = queryObj.list();
						return projectassignTaskList;
	}

	@Override
	public ResponseList<ProjectAssignTask> getProjectAssignTaskListByCandidateIdOrglocationId(ProjectassignTasksDTO projectassignTasksDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<ProjectAssignTask> response = new ResponseList<>();
		OrgLocations orgLoc = orgLocationService.getOrgLocations(projectassignTasksDTO.getOrgLocationId());
		SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(projectassignTasksDTO.getSearchValue());
		   java.sql.Date sqlDate = null;
		    if(matcher.matches() == true) {
						if(projectassignTasksDTO.getSearchValue().contains("/")||(projectassignTasksDTO.getSearchValue().contains("-"))) {
							String replacedValue = projectassignTasksDTO.getSearchValue().replaceAll("/", "-");
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		    
		StringBuilder query1 = new StringBuilder("");
		if(projectassignTasksDTO.getSearchValue() !=null && !"".equalsIgnoreCase(projectassignTasksDTO.getSearchValue())) {
		 query1.append("and (projectAssignTask.projectTaskId.taskName like '%" + projectassignTasksDTO.getSearchValue() + "%' ")
		       .append("OR projectAssignTask.projectTaskId.priority.name like '%" + projectassignTasksDTO.getSearchValue() + "%' ")
		       .append("OR projectAssignTask.projectTaskId.taskStatus.name like '%" + projectassignTasksDTO.getSearchValue() + "%' ")
		       .append("OR projectAssignTask.projectTaskId.severity.name like '%" + projectassignTasksDTO.getSearchValue() + "%' ")
		       .append("OR projectAssignTask.projectTaskId.projectId.projectName like '%" + projectassignTasksDTO.getSearchValue() + "%' )");
		    if(sqlDate != null) {
		    	query1.append("OR projectAssignTask.projectTaskId.dueDate like '%" + sqlDate + "%' ");
			 }
		}
		
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(projectassignTasksDTO.getSortColumn())) {
			if(projectassignTasksDTO.getSortColumn().equalsIgnoreCase("projectName")) {
				sortingQry = "order by projectAssignTask.projectTaskId.projectId."+ projectassignTasksDTO.getSortColumn() +" "+projectassignTasksDTO.getSortOrder();
			}else {
				sortingQry = "order by projectAssignTask.projectTaskId."+ projectassignTasksDTO.getSortColumn() +" "+projectassignTasksDTO.getSortOrder();
			}
		}
		
		 String subQueryStr = "";
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getPriorityFilter())) {
			 subQueryStr = subQueryStr+" and projectAssignTask.projectTaskId.priority.id IN ("+projectassignTasksDTO.getPriorityFilter()+") ";
		 }
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getTaskStatusFilter())) {
			 subQueryStr = subQueryStr+" and projectAssignTask.projectTaskId.taskStatus.id IN ("+projectassignTasksDTO.getTaskStatusFilter()+") ";
		 }
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getTaskTypeFilter())) {
			 subQueryStr = subQueryStr+" and projectAssignTask.projectTaskId.taskType.id IN ("+projectassignTasksDTO.getTaskTypeFilter()+") ";
		 }
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getProjectFilter())) {
			 subQueryStr = subQueryStr+" and projectAssignTask.projectTaskId.projectId.id IN ("+projectassignTasksDTO.getProjectFilter()+") ";
		 }
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getSeverityFilter())) {
			 subQueryStr = subQueryStr+" and projectAssignTask.projectTaskId.severity.id IN ("+projectassignTasksDTO.getSeverityFilter()+") ";
		 }
		 
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getTaskType())) {
			 subQueryStr = subQueryStr+" and projectAssignTask.projectTaskId.taskStatus.name IN ('"+projectassignTasksDTO.getTaskType()+"') ";
		 }
		 
		 
		 if(StringUtils.isNotEmpty(projectassignTasksDTO.getToDate()) && StringUtils.isNotEmpty(projectassignTasksDTO.getFromDate())) {
			 Date fromDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), projectassignTasksDTO.getFromDate());
			 java.sql.Date startDate = new java.sql.Date(fromDate.getTime()); 
			 Date toDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), projectassignTasksDTO.getToDate());
			 java.sql.Date  endDate = new java.sql.Date(toDate.getTime()); 
			 
			 subQueryStr = subQueryStr+" and date(projectAssignTask.projectTaskId.dueDate) BETWEEN '"+startDate+"' and '"+endDate+"' ";
		 }
		String query = "from ProjectAssignTask  projectAssignTask "
				      + " where projectAssignTask.projectMemberId.projectMemberCandidteId.id =:candidateId"
				      + " and projectAssignTask.projectTaskId.projectId.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.id = :orglocationId ) and projectAssignTask.status ='1'"
				      + " "+subQueryStr+" "+query1+" "+sortingQry+" ";
		List<ProjectAssignTask> projectassignTaskList = session.createQuery(query).setLong("candidateId", projectassignTasksDTO.getCandidateid()).setLong("orglocationId", projectassignTasksDTO.getOrgLocationId()).setFirstResult(projectassignTasksDTO.getStart()).setMaxResults(projectassignTasksDTO.getLength()).list();
		
		String queryStrForCount = "Select count (*) from ProjectAssignTask  projectAssignTask "
				+ " where projectAssignTask.projectMemberId.projectMemberCandidteId.id =:candidateId"
				+ " and projectAssignTask.projectTaskId.projectId.id IN(select pc.projectId.id from  ProjectClients pc where pc.orgLocationsId.id = :orglocationId ) and projectAssignTask.status ='1' "
				+ " "+subQueryStr+" "+query1+" "+sortingQry+" " ;
		countlist = session.createQuery(queryStrForCount.toString()).setLong("candidateId", projectassignTasksDTO.getCandidateid()).setLong("orglocationId", projectassignTasksDTO.getOrgLocationId()).list();

		filterCount = countlist.iterator().next().intValue();
		int totalCount = 0;
		if (projectassignTasksDTO != null) {
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
		
		response.setData(projectassignTaskList);
		return response;
	}


	 
}
