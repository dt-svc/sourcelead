package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementAssignToDao;
import com.bis.sourcelead.entity.RequirementAssignTo;

@Repository
public class RequirementAssignToDaoImpl implements RequirementAssignToDao{
	
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
	
	
	@Transactional(rollbackFor = { Exception.class })	
	@Override
	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo rat where rat.requirementId.id =:requirementId";
		return session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		
		
	}
	
    
  }

