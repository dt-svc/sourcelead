package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OffboardTasksDao;
import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OffboardTasksDaoImpl implements OffboardTasksDao{

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

	private static final Logger logger = LoggerFactory.getLogger(OffboardTasksDaoImpl.class);

	@Override
	@Transactional
	public OffboardTasks saveOffboardTasks(OffboardTasks offboardTasks) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (offboardTasks != null) {
				session.saveOrUpdate(offboardTasks);
			}
			logger.debug("offboardTasks Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OFFBOARD_TASKS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return offboardTasks;
	}

	@Override
	@Transactional
	public List<OffboardTasks> getOffboardTasksByTemplateId(Long templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffboardTasks tasks left join fetch tasks.offboardingTemplateId where tasks.offboardingTemplateId.id = :templateId "
	    		+ " and tasks.status = '1' ";
	    List<OffboardTasks> tasksList = session.createQuery(query.toString()).setLong("templateId", templateId).list();
        return tasksList;
	}

	@Override
	@Transactional
	public Integer getOffBoardTasksCountByCandidateOffBoardingId(Long candidateOffBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OffboardTasks tasks where tasks.candidateOffBoardingId.id =:candidateOffBoardingId ";
		List<Long> tasksCount = session.createQuery(query.toString()).setLong("candidateOffBoardingId", candidateOffBoardingId).list();
			Integer count = 0;
			if(tasksCount != null && !tasksCount.isEmpty()){
					count = tasksCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public List<OffboardTasks> getOffBoardTasksListByCandidateOffBoardingId(Long candidateOffBoardingId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffboardTasks tasks left join fetch tasks.candidateOffBoardingId where tasks.candidateOffBoardingId.id = :candidateOffBoardingId "
	    		+ " and tasks.status = '1' and tasks.isTaskSumbitted = 'N' ";
	    List<OffboardTasks> tasksList = session.createQuery(query.toString()).setLong("candidateOffBoardingId", candidateOffBoardingId).list();
        return tasksList;
	}

	@Override
	@Transactional
	public List<OffboardTasks> getOffboardTasksByTemplateIdList(List<Long> templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffboardTasks tasks left join fetch tasks.offboardingTemplateId where tasks.offboardingTemplateId.id IN (:templateId) "
	    		+ " and tasks.status = '1' ";
	    List<OffboardTasks> tasksList = session.createQuery(query.toString()).setParameterList("templateId", templateId).list();
        return tasksList;
	}

	@Override
	@Transactional
	public OffboardTasks getOffboardTasksByTaskId(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OffboardTasks> offboardTasks = session.createQuery("from OffboardTasks tasks where tasks.id =:id" ).setLong( "id", Id ).list();
		if (offboardTasks.size() > 0) {
			return offboardTasks.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<OffboardTasks> getOffBoardTasksListAssignedToCandidateByCandidateOffBoardingId(
			Long candidateOffBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffboardTasks tasks left join fetch tasks.candidateOffBoardingId where tasks.candidateOffBoardingId.id = :candidateOffBoardingId "
	    		+ " and tasks.assignToCandidate = 'Y' and tasks.status = '1' ";
	    List<OffboardTasks> tasksList = session.createQuery(query.toString()).setLong("candidateOffBoardingId", candidateOffBoardingId).list();
        return tasksList;
	}

	@Override
	@Transactional
	public List<OffboardTasks> getOffBoardAddedTasksListByCandidateOffBoardingId(Long candidateOffBoardingId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OffboardTasks tasks left join fetch tasks.candidateOffBoardingId where tasks.candidateOffBoardingId.id = :candidateOffBoardingId "
	    		+ " and tasks.status = '1' ";
	    List<OffboardTasks> tasksList = session.createQuery(query.toString()).setLong("candidateOffBoardingId", candidateOffBoardingId).list();
        return tasksList;
	}

}
