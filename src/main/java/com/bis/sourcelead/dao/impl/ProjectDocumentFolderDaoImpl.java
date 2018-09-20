package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectDocumentFloderDao;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocumentPermission;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectDocumentFolderDaoImpl implements ProjectDocumentFloderDao{
	
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDocumentFolderDaoImpl.class);

	
	@Override
	@Transactional
	public ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectDocumentFolder != null) {
				session.saveOrUpdate(projectDocumentFolder);
			}
			logger.debug("Saving ProjectDocumentFolder");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ProjectDocumentFolder"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectDocumentFolder;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
		public List<ProjectDocumentFolder> getProjectDocumentFolderByProjectId(Long projectId,Long roleId,String userRoleType,Long candidateId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<ProjectDocumentFolder> projectDocumentFolderList = null;
			String query = null; 
			
			String subQuery = "and (pdf.id IN (select pdp.projectDocumentFolderId.id from ProjectDocumentPermission pdp where pdp.userRoleId =:roleId)"
					+ "or pdf.id NOT IN (select pdp.projectDocumentFolderId.id from ProjectDocumentPermission pdp)"
					+ "or pdf.id in (select pdp.projectDocumentFolderId.id from ProjectDocumentPermission pdp where pdp.projectMemberId.projectMemberCandidteId.id =:candidateId))";
			
			 
			if(userRoleType.equalsIgnoreCase("ROLE_ADMIN") || userRoleType.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
				 query = "from ProjectDocumentFolder pdf  where  pdf.projectId.id =:projectId and pdf.status = '1' "; 
					 projectDocumentFolderList = session.createQuery(query).setLong("projectId", projectId).list();
			}else {
				 query = "from ProjectDocumentFolder pdf  where  pdf.projectId.id =:projectId and pdf.status = '1' "; 
					projectDocumentFolderList = session.createQuery(query).setLong("projectId", projectId).list();
			}
			String query1 = "select docs.projectFolderId.id, count(id) from ProjectDocuments docs where docs.status = 1 and docs.projects.id = :projectId group by docs.projectFolderId.id";
			List<Object[]> data = session.createQuery(query1).setLong("projectId", projectId).list();
			if(data != null && !data.isEmpty()) {
				Map<Long, Integer> counts = new HashMap<>();
				for(Object[] o : data) {
					if(o != null && o[0] != null)
						counts.put(Long.parseLong(o[0].toString()), Integer.parseInt(o[1].toString()));
				}
				if(!counts.isEmpty()) {
					for(ProjectDocumentFolder pdf : projectDocumentFolderList) {
						pdf.setPermissionAccess("1");
						String query2 = "from ProjectDocumentPermission pdp where  pdp.access IN( select max(access) from ProjectDocumentPermission p where p.projectDocumentId.id IS NULL) and pdp.projectDocumentFolderId.id = :folderId and pdp.projectDocumentId.id IS NULL and  pdp.projectMemberId.projectMemberCandidteId =:candidateId";
						List<ProjectDocumentPermission>  projectDocumentPermissionList = session.createQuery(query2).setLong("folderId", pdf.getId()).setLong("candidateId", candidateId).list();
						if(projectDocumentPermissionList.isEmpty()) {
							String query3 = "from ProjectDocumentPermission pdp  where pdp.access IN( select access from ProjectDocumentPermission p where p.projectDocumentId IS NULL) and pdp.projectDocumentFolderId.id = :folderId and pdp.projectDocumentId IS NULL and  pdp.userRoleId.id =:roleId";
							List<ProjectDocumentPermission>  projectDocumentPermissionList2 = session.createQuery(query3).setLong("folderId", pdf.getId()).setLong("roleId", roleId).list();
							if(!projectDocumentPermissionList2.isEmpty()) {
								for(ProjectDocumentPermission pdp:projectDocumentPermissionList2) {
									pdf.setPermissionAccess(pdp.getAccess());
									break;
								}
							}
						} else {
							for(ProjectDocumentPermission pdp:projectDocumentPermissionList) {
								pdf.setPermissionAccess(pdp.getAccess());
								break;
							}
						}
						pdf.setCount(counts.containsKey(pdf.getId()) ? counts.get(pdf.getId()) : 0);
					}
				}
			}
			
			return projectDocumentFolderList;
		}
	
	
	 

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentFolder pdf  where pdf.id =:folderId";
		@SuppressWarnings("unchecked")
		List<ProjectDocumentFolder> projectDocumentFolderList = session.createQuery(query).setLong("folderId", folderId).list();
		ProjectDocumentFolder projectDocumentFolder = null;
		if(projectDocumentFolderList != null){
			projectDocumentFolder = projectDocumentFolderList.iterator().next();
		}
		return projectDocumentFolder;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocumentFolder validateProjectDocumentFolderByFolderIdAndFolderName(Long folder, String folderName)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentFolder pdf  where pdf.id =:folder and pdf.foderName =:folderName ";
		List<ProjectDocumentFolder> projectDocumentFolderList =session.createQuery(query) .setString("folderName", folderName).setLong("folder", folder).list();
		ProjectDocumentFolder projectDocumentFolder = null;
		if(projectDocumentFolderList != null && !projectDocumentFolderList.isEmpty())
		{
			projectDocumentFolder = (ProjectDocumentFolder) projectDocumentFolderList.iterator().next();
		}
		return projectDocumentFolder;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteProjectFolderByFolderId(Long folderId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentFolder pdf  where pdf.id =:folderId";
		List<ProjectDocumentFolder> projectDocumentFolderList =session.createQuery(query).setLong("folderId", folderId).list();
		if(projectDocumentFolderList != null && !projectDocumentFolderList.isEmpty()) {
			   for(ProjectDocumentFolder pdf :projectDocumentFolderList) {
				   session.delete(pdf);
			   }
		   }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocumentFolder getProjectDocumentFolderByProjectId(Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocumentFolder pdf  where pdf.projectId.id =:projectId";
		List<ProjectDocumentFolder> projectDocumentFolderList = session.createQuery(query).setLong("projectId", projectId).list();
		ProjectDocumentFolder projectDocumentFolder = null;
		if(projectDocumentFolderList != null && projectDocumentFolderList.size()>0 ){
			projectDocumentFolder = projectDocumentFolderList.iterator().next();
		}
		return projectDocumentFolder;
	}
}
