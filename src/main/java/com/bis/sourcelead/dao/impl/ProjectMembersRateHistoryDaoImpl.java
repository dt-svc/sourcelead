package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectMembersRateHistoryDao;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersRateHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectMembersRateHistoryDaoImpl implements ProjectMembersRateHistoryDao{

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

	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersRateHistoryDaoImpl.class);

	@Override
	@Transactional
	public ProjectMembersRateHistory saveProjectMembersRateHistory(ProjectMembersRateHistory projectMembersRateHistory)
			throws Exception {
		
		logger.debug("In ProjectMembersRateHistory Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectMembersRateHistory != null){
				session.saveOrUpdate(projectMembersRateHistory);
				logger.debug("Project Members Rate History saved successfully..");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_MEMBERS_RATE_HISTORY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectMembersRateHistory;
	}

	@Override
	@Transactional
	public ProjectMembersRateHistory getProjectMembersRateHistoryByMemberIdAndStatus(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = " from ProjectMembersRateHistory pmrh left join fetch pmrh.projectMemberId projectMember left join fetch pmrh.candidateId candidate "
				+ " left join fetch pmrh.rateHistoryId where pmrh.projectMemberId.id = :Id and pmrh.status = '1' " ;
		List<ProjectMembersRateHistory> pmrhList = session.createQuery(query).setLong("Id", Id).list(); 
		ProjectMembersRateHistory pmrh = null;
		if(pmrhList != null && !pmrhList.isEmpty())  {
			pmrh  = pmrhList.iterator().next();
		}
		return pmrh; 
	}

	@Override
	@Transactional
	public List<ProjectMembersRateHistory> getProjectMembersRateHistoryListByProjectMemberId(Long projectMemberId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectMembersRateHistory pmrh left join fetch pmrh.projectMemberId projectMember left join fetch pmrh.candidateId candidate "
				+ " left join fetch pmrh.createdBy where pmrh.projectMemberId.id = :projectMemberId order by pmrh.id ASC )" ;
		List<ProjectMembersRateHistory> pmrhList = session.createQuery(query)
											.setLong("projectMemberId", projectMemberId)
										    .list(); 
		return pmrhList;  
		
	}

	@Override
	@Transactional
	public ProjectMembersRateHistory getProjectMembersRateHistoryById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		String query = " from ProjectMembersRateHistory pmrh left join fetch pmrh.projectMemberId projectMember left join fetch pmrh.candidateId candidate "
				+ " left join fetch pmrh.rateHistoryId where pmrh.id = :Id " ;
		List<ProjectMembersRateHistory> pmrhList = session.createQuery(query).setLong("Id", Id).list(); 
		ProjectMembersRateHistory pmrh = null;
		if(pmrhList != null && !pmrhList.isEmpty())  {
			pmrh  = pmrhList.iterator().next();
		}
		return pmrh; 
	}
}
