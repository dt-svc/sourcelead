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

import com.bis.sourcelead.dao.OnboardTasksDao;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OnboardTasksDaoImpl implements OnboardTasksDao{


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
	

	private static final Logger logger = LoggerFactory.getLogger(OnboardTasksDaoImpl.class);


	@Override
	@Transactional
	public OnboardTasks saveOnboardTasks(OnboardTasks onboardTasks) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (onboardTasks != null) {
				session.saveOrUpdate(onboardTasks);
			}
			logger.debug("onboardTasks Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ONBOARD_TASKS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return onboardTasks;
	}


	@Override
	@Transactional
	public List<OnboardTasks> getOnboardTasksByTemplateId(Long templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnboardTasks tasks left join fetch tasks.onboardingTemplateId where tasks.onboardingTemplateId.id = :templateId "
	    		+ " and tasks.status = '1' ";
	    List<OnboardTasks> tasksList = session.createQuery(query.toString()).setLong("templateId", templateId).list();
        return tasksList;
	}


	@Override
	@Transactional
	public Integer getOnBoardTasksCountByCandidateOnBoardingId(Long candidateOnBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from OnboardTasks tasks where tasks.candidateOnBoardingId.id =:candidateOnBoardingId ";
		List<Long> tasksCount = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).list();
			Integer count = 0;
			if(tasksCount != null && !tasksCount.isEmpty()){
					count = tasksCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public List<OnboardTasks> getOnBoardTasksListByCandidateOnBoardingId(Long candidateOnBoardingId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnboardTasks tasks left join fetch tasks.candidateOnBoardingId where tasks.candidateOnBoardingId.id = :candidateOnBoardingId "
	    		+ " and tasks.status = '1' ";
	    List<OnboardTasks> tasksList = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).list();
        return tasksList;
	}


	@Override
	@Transactional
	public List<OnboardTasks> getOnboardTasksByTemplateIdList(List<Long> templateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnboardTasks tasks left join fetch tasks.onboardingTemplateId where tasks.onboardingTemplateId.id IN (:templateId) "
	    		+ " and tasks.status = '1' ";
	    List<OnboardTasks> tasksList = session.createQuery(query.toString()).setParameterList("templateId", templateId).list();
        return tasksList;
	}


	@Override
	@Transactional
	public OnboardTasks getOnboardTasksByTaskId(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OnboardTasks> onboardTasks = session.createQuery("from OnboardTasks tasks where tasks.id =:id" ).setLong( "id", Id ).list();
		if (onboardTasks.size() > 0) {
			return onboardTasks.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<OnboardTasks> getOnBoardTasksListAssignedToCandidateByCandidateOnBoardingId(Long candidateOnBoardingId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OnboardTasks tasks left join fetch tasks.candidateOnBoardingId where tasks.candidateOnBoardingId.id = :candidateOnBoardingId "
	    		+ " and tasks.assignToCandidate = 'Y' and tasks.status = '1' ";
	    List<OnboardTasks> tasksList = session.createQuery(query.toString()).setLong("candidateOnBoardingId", candidateOnBoardingId).list();
        return tasksList;
	}
	
}
