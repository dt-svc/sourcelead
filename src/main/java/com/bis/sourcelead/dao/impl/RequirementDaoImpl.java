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

import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class RequirementDaoImpl implements RequirementDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDaoImpl.class);
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * @author prasad k
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 * @param requirement This is the first paramter to storeRequirement method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement storeRequirement(Requirement requirement) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirement != null) {
				session.saveOrUpdate(requirement);
			}
			logger.debug("Requirement Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Requirement"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirement;
	}

	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get RequirementList by orgLocationId .
	 *  
	 * @param orgLocationId This is the first paramter to getRequirementListByOrgLocationID method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId) throws Exception {
		 
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement  where requirement.orgLocationId =:orgLocationId";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
	}

	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get Requirement by requirementId .
	 *  
	 * @param requirementId This is the first paramter to getRequirementByRequirementId method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.id =:requirementId";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement req where isTemplate = 'Y' and req.orgLocationId =:orgLocationId";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
	}
	

	
	
}
