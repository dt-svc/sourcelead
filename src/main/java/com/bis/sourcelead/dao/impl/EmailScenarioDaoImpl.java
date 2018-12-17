package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
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

import com.bis.sourcelead.dao.EmailScenarioDao;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.exception.NoRecordsFoundException;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

/**
 * 
 * @author Prasad salina
 *
 */
@Repository
public class EmailScenarioDaoImpl implements EmailScenarioDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(EmailScenarioDaoImpl.class);

	/**
	 * This method for get the EmailScenario
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(String uniqueScenarioName,String entityType,
			String orgCode, String locationCode) throws Exception {
logger.debug("In EmailScenarioDaoImpl class getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode method");
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from EmailScenario es where es.uniqueScenarioName = :uniqueScenarioName and es.entityType = :entityType and es.locationCode = :locationCode and es.orgCode =:orgCode";
		 
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString()) .setString("uniqueScenarioName", uniqueScenarioName)
				                                                                        .setString("entityType", entityType)
																	                    .setString("locationCode", locationCode)
																	                    .setString("orgCode", orgCode).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

	/**
	 * This method for save the EmailScenario
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario save(EmailScenario scenario) throws Exception {
		logger.debug("In EmailScenarioDaoImpl class save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(scenario != null){
				session.saveOrUpdate(scenario);
				logger.debug("EmailScenario Added Successfully");
			}
		}catch(Exception exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"EmailScenario"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return scenario;	
	}

	/**
	 * This method for get the EmailScenario by using uniqueScenarioName
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailScenarioByUniqueName(String uniqueScenarioName, String entityType) throws Exception{
		logger.debug("In EmailScenarioDaoImpl class getEmailScenarioByUniqueName method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenario es left join fetch es.parentId where es.uniqueScenarioName = :uniqueScenarioName and es.entityType = :entityType and es.defaultTemplate = 'Y' ";
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString())
				                                         .setString("uniqueScenarioName", uniqueScenarioName)
				                                         .setString("entityType", entityType).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailTemplateById(Long id) {
		// TODO Auto-generated method stub
		logger.debug("In EmailScenarioDaoImpl class getEmailTemplateById method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenario es left join fetch es.parentId where es.id = :id ";
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString())
				                                         .setLong("id", id).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailDefTemplateNo(String uniqueScenarioName, String defaultTemp) throws Exception {
		logger.debug("In EmailScenarioDaoImpl class getEmailDefTemplateNo method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenario es where es.uniqueScenarioName = :uniqueScenarioName and es.defaultTemplate = :defaultTemp";
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString())
				                                         .setString("uniqueScenarioName", uniqueScenarioName)
				                                         .setString("defaultTemp", defaultTemp).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmailScenario> getEmailScenarioList(String templateType,List<Long> tabIds, List<String> specification) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<EmailScenario> emailScenarioList=null;
		if(templateType.equalsIgnoreCase("sourcelead")) {
			String subQuery = "";
			String subQuery1 = "";
			if(tabIds != null && !tabIds.isEmpty()){
				subQuery = " and emailScenario.tabId.id IN(:tabIds)";
			}
			if(specification != null && !specification.isEmpty()){
				subQuery1 = " and emailScenario.templateType IN (:specification)";
			}
			if(subQuery !="" && subQuery1 != ""){
				 query = "from EmailScenario emailScenario where emailScenario.defaultTemplate = 'Y'  "+subQuery+" "+subQuery1+" and emailScenario.status = '1' "; 
				 emailScenarioList =session.createQuery(query) .setParameterList("tabIds", tabIds).setParameterList("specification", specification).list();
			}else if(subQuery !=""){
				query = "from EmailScenario emailScenario where emailScenario.defaultTemplate = 'Y'  "+subQuery+" and emailScenario.status = '1' "; 
				 emailScenarioList =session.createQuery(query) .setParameterList("tabIds", tabIds).list();
			}else if(subQuery1 != ""){
				query = "from EmailScenario emailScenario where emailScenario.defaultTemplate = 'Y'  "+subQuery1+" and emailScenario.status = '1' "; 
				 emailScenarioList =session.createQuery(query) .setParameterList("specification", specification).list();
			}else{
				query = "from EmailScenario emailScenario where emailScenario.defaultTemplate = 'Y'  and emailScenario.status = '1' "; 
				 emailScenarioList =session.createQuery(query) .list();
			}
			
		}else if(templateType.equalsIgnoreCase("org")){
			if(tabIds != null && !tabIds.isEmpty()){
				query = "from EmailScenario emailScenario where emailScenario.defaultTemplate = 'Y' and emailScenario.templateType = 'org' and emailScenario.entityType = 'Email' and emailScenario.tabId.id IN(:tabIds) and emailScenario.status = '1' "; 
				 emailScenarioList =session.createQuery(query) .setParameterList("tabIds", tabIds).list();
				}else{
					query = "from EmailScenario emailScenario where emailScenario.defaultTemplate = 'Y' and emailScenario.templateType = 'org' and emailScenario.entityType = 'Email' and emailScenario.status = '1' "; 
					 emailScenarioList =session.createQuery(query).list();
				}
			 }
		
		return emailScenarioList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailScenarioByUniqueNameAndOrgLocationId(String uniqueScenarioName, String entityType,
			Long orgLocationId) throws Exception {
		logger.debug("In EmailScenarioDaoImpl class getEmailScenarioByUniqueName method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenario es left join fetch es.parentId where es.uniqueScenarioName = :uniqueScenarioName "
				+ " and es.entityType = :entityType and es.orgLocationId.id = :orgLocationId";
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString())
				                                         .setString("uniqueScenarioName", uniqueScenarioName)
				                                         .setString("entityType", entityType).setLong("orgLocationId", orgLocationId).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmailScenario> getEmailScenarioListByOrgLocId(Long orgLocId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenario emailScenario where  emailScenario.orgLocationId.id=:orgLocId "; 
		List<EmailScenario> emailScenarioList =session.createQuery(query.toString()).setLong("orgLocId", orgLocId).list();
		return emailScenarioList;
	}
		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailTemplateByUUID(String templateUUID) {
		// TODO Auto-generated method stub
		logger.debug("In EmailScenarioDaoImpl class getEmailTemplateByUUID method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenario es left join fetch es.parentId where es.emailUUID = :templateUUID ";
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString())
				                                         .setString("templateUUID", templateUUID).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

}
