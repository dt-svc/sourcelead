package com.bis.sourcelead.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementWorkFlow;

@Repository
public class RequirementWorkFlowDaoImpl implements RequirementWorkFlowDao {
	
@Autowired
private SessionFactory sessionFactory;

private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowDaoImpl.class);

@Override
@Transactional(rollbackFor = { Exception.class })
public RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlow requirementWorkFlow,
		OrgLocations orgLocations) {
	logger.debug("saveRequirementWorkFlowDetails starts");
	Session session = this.sessionFactory.getCurrentSession();
	try {
		
		if (requirementWorkFlow != null) {
			session.saveOrUpdate(requirementWorkFlow);
		}
		
	} catch (Exception exception) {
		logger.error("Exception caught while saving requirementWorkFlow information " + exception.getMessage());
		exception.printStackTrace();
	}
	return requirementWorkFlow;
}

@Override
@Transactional(rollbackFor = { Exception.class })
public RequirementWorkFlow getRequirementWorkFlowById(Long id) {
	
	RequirementWorkFlow requirementWorkFlow = null;
	try {
		Session session = this.sessionFactory.getCurrentSession();
		requirementWorkFlow = (RequirementWorkFlow) session.get(RequirementWorkFlow.class, id);
	} catch (HibernateException e) {
		logger.error("SQL Exception occured while doing getting record -->" + e.getMessage());
		e.printStackTrace();
	}
	logger.debug("got the record id -->" + id);
	return requirementWorkFlow;
}
        
       

}