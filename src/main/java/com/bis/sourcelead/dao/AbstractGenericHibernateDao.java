package com.bis.sourcelead.dao;

import java.io.Serializable;
import java.text.MessageFormat;
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
		List<ProjectDocuments> projectDocumentList = session.createQuery(query).setLong("projectId", projectId).setCacheable(true).list();
		return projectDocumentList;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectDocuments getDocumentById(Long porjectDocumentId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectDocuments  projectDoc  where projectDoc.Id =:porjectDocumentId";
		@SuppressWarnings("unchecked")
		List<ProjectDocuments> projectDocumentList = session.createQuery(query).setLong("porjectDocumentId", porjectDocumentId).setCacheable(true).list();
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
}
