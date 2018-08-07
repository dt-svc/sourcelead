package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.ProjectBudgetDao;
import com.bis.sourcelead.dao.ProjectTaskActivityDao;
import com.bis.sourcelead.dao.ProjectTaskCommentsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ProjectBudgetDaoImpl implements ProjectBudgetDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectBudgetDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectBudget saveProjectTaskComments(ProjectBudget projectBudget) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectBudget != null) {
				session.saveOrUpdate(projectBudget);
			}
			logger.debug("Saving ProjectBudget");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_BUDGET"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectBudget;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectBudget getProjectBudgetsByProjectId(Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectBudget  projectBudget  where projectBudget.projectId.id =:projectId";
		List<ProjectBudget> projectBudget = session.createQuery(query).setLong("projectId", projectId).list();
		ProjectBudget projectBd = null;
		if (projectBudget != null && projectBudget.size() > 0) {
			projectBd = (ProjectBudget) projectBudget.iterator().next();
		}
		return projectBd;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectBudget> getProjectBudgetByProjectTaskId(Long projectId,Long loginOrgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectBudget  projectbudget  where projectbudget.projectId.id =:projectId  and projectbudget.status = '1'";
		List<ProjectBudget> ProjectBudgetList = session.createQuery(query).setLong("projectId", projectId).list();
		return ProjectBudgetList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Long getProjectBudgetReqestedIdByProjectId(Long projectId, Integer generatedIdcount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(BUDGET_REQUESTED_ID),'"+generatedIdcount+"') AS UNSIGNED)) FROM "+schema+".PROJECT_BUDGET WHERE PROJECT_ID = '"+ projectId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectBudget getProjectBudgetsById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectBudget  projectBudget  where projectBudget.id =:Id";
		List<ProjectBudget> projectBudget = session.createQuery(query).setLong("Id", Id).list();
		ProjectBudget projectBd = null;
		if (projectBudget != null && projectBudget.size() > 0) {
			projectBd = (ProjectBudget) projectBudget.iterator().next();
		}
		return projectBd;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectBudget> getProjectBudgetListByOrgLocationIdAndBudgetStatus(Long approverUserId,Long orgLocationId, String budgetStatus, String userRole) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectBudget> ProjectBudgetList = null;
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			String query = "from ProjectBudget  projectbudget left join fetch projectbudget.projectId  projects left join fetch projectbudget.projectId.orgLocationsId orgLoaction where projectbudget.budgetStatus =:budgetStatus and projectbudget.approverUserId.id =:approverUserId and orgLoaction.id = :orgLocationId and projectbudget.status = '1' ";
			ProjectBudgetList = session.createQuery(query).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).setString("budgetStatus", budgetStatus).list();
		}else {
			String query = "from ProjectBudget  projectbudget left join fetch projectbudget.projectId  projects left join fetch projectbudget.projectId.orgLocationsId orgLoaction where projectbudget.budgetStatus =:budgetStatus and orgLoaction.id = :orgLocationId and projectbudget.status = '1' ";
			ProjectBudgetList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("budgetStatus", budgetStatus).list();
		}
		
		return ProjectBudgetList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectBudget> getProjectBudgetListByOrgLocationId(Long approverUserId,Long orgLocationId,String userRole) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectBudget> ProjectBudgetList = null;
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			String query = "from ProjectBudget  projectbudget left join fetch projectbudget.projectId.orgLocationsId orgLoaction where projectbudget.approverUserId.id =:approverUserId and orgLoaction.id = :orgLocationId and projectbudget.status = '1'";
			 ProjectBudgetList = session.createQuery(query).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).list();
		}else {
			String query = "from ProjectBudget  projectbudget left join fetch projectbudget.projectId.orgLocationsId orgLoaction where orgLoaction.id = :orgLocationId and projectbudget.status = '1'";
			 ProjectBudgetList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}
		return ProjectBudgetList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectBudget> getProjectBudgetByProjectTaskIdAndApprovedStatus(Long projectId,Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectBudget  projectbudget  where projectbudget.projectId.id =:projectId and projectbudget.budgetStatus = 'Approved' and projectbudget.projectId.orgLocationsId =:orgLocationId";
		List<ProjectBudget> ProjectBudgetList = session.createQuery(query).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId).list();
		return ProjectBudgetList;
	}
}
