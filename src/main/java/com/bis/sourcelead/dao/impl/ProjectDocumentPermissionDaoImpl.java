package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectDocumentPermissionDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocumentPermission;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.util.CommonUtil;
import com.healthmarketscience.jackcess.query.Query;

@Repository
public class ProjectDocumentPermissionDaoImpl implements ProjectDocumentPermissionDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDocumentPermissionDaoImpl.class);

	@Override
	@Transactional
	public ProjectDocumentPermission saveProjectDocumentPermission(ProjectDocumentPermission projectDocumentPermission) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectDocumentPermission != null) {
				session.saveOrUpdate(projectDocumentPermission);
			}
			logger.debug("Saving ProjectDocumentPermission");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ProjectDocumentPermission"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectDocumentPermission;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectDocumentPermission> projectDocumentPermissionListByFolderIdAndAccessStatus(Long folderId, Long documentId,String accessStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String query;
		List<ProjectDocumentPermission> projectDocumentPermissionList = null;
		if(documentId !=null) {
			 query = "from ProjectDocumentPermission pdp  where pdp.projectDocumentId.id =:documentId and pdp.access =:accessStatus "; 
			 projectDocumentPermissionList = session.createQuery(query).setLong("documentId", documentId).setString("accessStatus", accessStatus).list();
		}else {
			 query = "from ProjectDocumentPermission pdp  where pdp.projectDocumentFolderId.id =:folderId and  pdp.projectDocumentId.id IS NULL and pdp.access =:accessStatus "; 
		     projectDocumentPermissionList = session.createQuery(query).setLong("folderId", folderId).setString("accessStatus", accessStatus).list();
		}
		return projectDocumentPermissionList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(Long folderId,Long documentid, String accessStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> projectDocumentPermissionList = null;
		if(documentid != null) {
			String query = "Select pdp.userRoleId.id from ProjectDocumentPermission pdp  where pdp.projectDocumentId.id =:documentid and pdp.access =:accessStatus"; 
			projectDocumentPermissionList = session.createQuery(query).setLong("documentid", documentid).setString("accessStatus", accessStatus).list();
		}else {
			String query = "Select pdp.userRoleId.id from ProjectDocumentPermission pdp  where pdp.projectDocumentFolderId.id =:folderId and pdp.access =:accessStatus and pdp.projectDocumentId.id IS NULL"; 
			projectDocumentPermissionList = session.createQuery(query).setLong("folderId", folderId).setString("accessStatus", accessStatus).list();
		}
		if(projectDocumentPermissionList != null && !projectDocumentPermissionList.isEmpty() && projectDocumentPermissionList.get(0) != null) {
			return projectDocumentPermissionList;
		}else {
			return new ArrayList<>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> projectDocumentPermissionMemberIdListByFolderIdAndAccessStatus(Long folderId,Long documentid, String accessStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> projectDocumentPermissionList = null;
		if(documentid != null) {
			 String query = "Select pdp.projectMemberId.id from ProjectDocumentPermission pdp  where pdp.projectDocumentId.id =:documentid and pdp.access =:accessStatus"; 
			  projectDocumentPermissionList = session.createQuery(query).setLong("documentid", documentid).setString("accessStatus", accessStatus).list();
			
		}else {
			String query = "Select pdp.projectMemberId.id from ProjectDocumentPermission pdp  where pdp.projectDocumentFolderId.id =:folderId and pdp.access =:accessStatus and pdp.projectDocumentId.id IS NULL"; 
			  projectDocumentPermissionList = session.createQuery(query).setLong("folderId", folderId).setString("accessStatus", accessStatus).list();
			
		}
		return projectDocumentPermissionList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteAllProjectDocumentPermissionByFoderIdAndMemberAndAccess(Long foderId, List<Long> memberId, String access) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentPermission pdp where pdp.projectDocumentFolderId.id =:foderId and pdp.projectMemberId.id IN(:memberId) and pdp.access =:access ";
		List<ProjectDocumentPermission> projectDocumentPermissionList = session.createQuery(query).setLong("foderId", foderId).setParameterList("memberId", memberId).setString("access", access).list();
       if(projectDocumentPermissionList != null && !projectDocumentPermissionList.isEmpty()) {
    	   for(ProjectDocumentPermission pdp :projectDocumentPermissionList) {
    		   session.delete(pdp);
    	   }
       }
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteAllProjectDocumentPermissionByFoderIdAndRoleIdAndAccess(Long foderId, List<Long> roleIds, String access) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentPermission pdp where pdp.projectDocumentFolderId.id =:foderId and pdp.userRoleId.id IN(:roleIds) and pdp.access =:access ";
		List<ProjectDocumentPermission> projectDocumentPermissionList = session.createQuery(query).setLong("foderId", foderId).setParameterList("roleIds", roleIds).setString("access", access).list();
       if(projectDocumentPermissionList != null && !projectDocumentPermissionList.isEmpty()) {
    	   for(ProjectDocumentPermission pdp :projectDocumentPermissionList) {
    		   session.delete(pdp);
    	   }
       }
	
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocumentPermission getDocumentPermissionByDocumentId(Long documentId,Long projectMemberId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentPermission pdp  where pdp.projectDocumentId.id =:documentId and pdp.projectMemberId.id =:projectMemberId";
		List<ProjectDocumentPermission> ProjectDocumentPermissionList = session.createQuery(query).setLong("documentId", documentId).setLong("projectMemberId", projectMemberId).list();
		ProjectDocumentPermission projectDocumentPermission = null;
		if(ProjectDocumentPermissionList != null && !ProjectDocumentPermissionList.isEmpty()){
			projectDocumentPermission = ProjectDocumentPermissionList.iterator().next();
		}
		return projectDocumentPermission;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void deleteAllProjectDocumentByFoderId(Long foderId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentPermission pdp where pdp.projectDocumentFolderId.id =:foderId";
		List<ProjectDocumentPermission> projectDocumentPermissionList = session.createQuery(query).setLong("foderId", foderId).list();
       if(projectDocumentPermissionList != null && !projectDocumentPermissionList.isEmpty()) {
    	   for(ProjectDocumentPermission pdp :projectDocumentPermissionList) {
    		   session.delete(pdp);
    	   }
       }
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void deleteAllProjectDocumentByDocumentId(Long documentId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentPermission pdp where pdp.projectDocumentId.id =:documentId";
		List<ProjectDocumentPermission> projectDocumentPermissionList = session.createQuery(query).setLong("documentId", documentId).list();
       if(projectDocumentPermissionList != null && !projectDocumentPermissionList.isEmpty()) {
    	   for(ProjectDocumentPermission pdp :projectDocumentPermissionList) {
    		   session.delete(pdp);
    	   }
       }
	}
	 
	
}
