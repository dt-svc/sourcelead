package com.bis.sourcelead.dao;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocumentPermission;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;


public abstract class AbstractGenericHibernateDao<T extends Serializable> implements GenericDao<T> {
	
	private Class<T> entityClass;

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
	private SessionFactory sessionFactory;
	
	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void setEntityClass(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public T findById(long id) {
		return getCurrentSession().get(this.entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<T> findAll() {
		return getCurrentSession().createQuery( "from " + this.entityClass.getName() ).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<T> findByCriteria(String property, Long value){
		Criteria criteria = getCurrentSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.eq(property, value));
		return criteria.list();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void save(T entity) {
		getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public T update(T entity) {
		return (T) getCurrentSession().merge(entity);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void delete(T entity) {
		getCurrentSession().delete(entity);

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void deleteById(long id) {
		T entity = findById(id);
		delete(entity);

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectDocuments> getProjectDocumentsListByProjectIdId(Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocuments  projectDoc  where projectDoc.projects.id =:projectId and projectDoc.status ='1'";
		List<ProjectDocuments> projectDocumentList = session.createQuery(query).setLong("projectId", projectId).list();
		return projectDocumentList;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocuments getDocumentById(Long porjectDocumentId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocuments  projectDoc  where projectDoc.Id =:porjectDocumentId";
		@SuppressWarnings("unchecked")
		List<ProjectDocuments> projectDocumentList = session.createQuery(query).setLong("porjectDocumentId", porjectDocumentId).list();
		ProjectDocuments projectDocument = null;
		if(projectDocumentList != null){
			projectDocument = projectDocumentList.iterator().next();
		}
		return projectDocument;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocuments updateProjectDocuments(ProjectDocuments projectDocuments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectDocuments != null){
				session.saveOrUpdate(projectDocuments);
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_DOCUMENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return projectDocuments;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectDocuments> getEachProjectDocumentsListByProjectIdIdAndFolderId(Long projectId,Long roleId,String userRoleType,Long folderId,Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectDocuments> projectDocumentList  = null;
		
		String subQuery = "and (projectDoc.id IN (select pdp.projectDocumentFolderId.id from ProjectDocumentPermission pdp where pdp.userRoleId =:roleId)"
				+ "or projectDoc.id NOT IN (select pdp.projectDocumentFolderId.id from ProjectDocumentPermission pdp)"
				+ "or projectDoc.id in (select pdp.projectDocumentFolderId.id from ProjectDocumentPermission pdp where pdp.projectMemberId.projectMemberCandidteId.id =:candidateId))";
		
		 
		//Admin based condition 
		if(userRoleType.equalsIgnoreCase("ROLE_ADMIN") || userRoleType.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			if(folderId != null) {
					    String query = "from ProjectDocuments  projectDoc  where projectDoc.projects.id =:projectId and projectFolderId.id =:folderId and projectDoc.status ='1' "+subQuery+"";
					   projectDocumentList = session.createQuery(query).setLong("projectId", projectId).setLong("folderId", folderId).setLong("roleId", roleId).setLong("candidateId", candidateId).list();
				}else {
					   String query = "from ProjectDocuments  projectDoc  where projectDoc.projects.id =:projectId and projectDoc.status ='1'";
					   projectDocumentList = session.createQuery(query).setLong("projectId", projectId).list();
				}
		}else {
			if(folderId != null) {
				    String query = "from ProjectDocuments  projectDoc  where projectDoc.projects.id =:projectId and projectFolderId.id =:folderId and projectDoc.status ='1' "+subQuery+"";
				   projectDocumentList = session.createQuery(query).setLong("projectId", projectId).setLong("folderId", folderId).setLong("roleId", roleId).setLong("candidateId", candidateId).list();
			}else {
				   String query = "from ProjectDocuments  projectDoc  where projectDoc.projects.id =:projectId and projectDoc.status ='1'";
				   projectDocumentList = session.createQuery(query).setLong("projectId", projectId).list();
			}
		}
		//Permissons bases queryList
		for(ProjectDocuments pd : projectDocumentList) {
			pd.setPermissionAccess("1");
			String query2 = "from ProjectDocumentPermission pdp where pdp.projectDocumentFolderId.id = :folderId and (pdp.projectDocumentId.id IS NULL OR pdp.projectDocumentId.id =:documentId)  and pdp.projectMemberId.projectMemberCandidteId =:candidateId";
			List<ProjectDocumentPermission>  projectDocumentPermissionList = session.createQuery(query2).setLong("folderId", pd.getProjectFolderId().getId()).setLong("documentId", pd.getId()).setLong("candidateId", candidateId).list();
			if(projectDocumentPermissionList.isEmpty()) {
				String query3 = "from ProjectDocumentPermission pdp where pdp.projectDocumentFolderId.id = :folderId and (pdp.projectDocumentId.id IS NULL OR pdp.projectDocumentId.id =:documentId) and  pdp.userRoleId =:roleId";
				List<ProjectDocumentPermission>  projectDocumentPermissionList2 = session.createQuery(query3).setLong("folderId", pd.getProjectFolderId().getId()).setLong("documentId", pd.getId()).setLong("roleId", roleId).list();
				if(!projectDocumentPermissionList2.isEmpty()) {
					for(ProjectDocumentPermission pdp:projectDocumentPermissionList2) {
						pd.setPermissionAccess(pdp.getAccess());
						break;
					}
				}
			} else {
				for(ProjectDocumentPermission pdp:projectDocumentPermissionList) {
					pd.setPermissionAccess(pdp.getAccess());
					break;
				}
			}
		}
		return projectDocumentList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateProjectDocumentFolderNameByProjectIdIdAndFolderName(Long projectId,String folderName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from ProjectDocumentFolder pdf  where pdf.projectId.id =:projectId and pdf.foderName =:folderName ";
				Collection<ProjectDocumentFolder> projectDocumentFolderList = session.createQuery(query) .setString("folderName", folderName).setLong("projectId", projectId).list();
				if (projectDocumentFolderList != null && projectDocumentFolderList.size() > 0) {
					flag = true;
				}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void delteEachDocumentById(Long porjectDocumentId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocuments pd where pd.id=:porjectDocumentId";
		List<ProjectDocuments> projectDocumentsList = session.createQuery(query).setLong("porjectDocumentId", porjectDocumentId).list();
	   if(projectDocumentsList != null && !projectDocumentsList.isEmpty()) {
		   for(ProjectDocuments pdf :projectDocumentsList) {
			   session.delete(pdf);
		   }
	   }
	}
	 
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateProjectDocumentNameByProjectIdIdAndDocumentName(Long projectId,String documentName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from ProjectDocuments pd  where pd.projects.id =:projectId and pd.fileName =:documentName ";
				Collection<ProjectDocuments> projectDocumentsList = session.createQuery(query) .setString("documentName", documentName).setLong("projectId", projectId).list();
				if (projectDocumentsList != null && projectDocumentsList.size() > 0) {
					flag = true;
				}
		return flag;
	}
	
}
