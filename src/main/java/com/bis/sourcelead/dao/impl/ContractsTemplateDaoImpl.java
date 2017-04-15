package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ContractsTemplateDaoImpl implements ContractsTemplateDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ContractsTemplateDaoImpl.class);
	
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
	
	@Override
	@Transactional
	public ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception {
		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contractsTemplate != null) {
				session.saveOrUpdate(contractsTemplate);
			}
			logger.debug("contractsTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contractsTemplate;
	}

	@Override
	@Transactional
	public ContractsTemplate saveContractTemplate(Long contractTemplateId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate> contractsTemplateList = new ArrayList<>();
		logger.debug("getOrgSpocById start");
		contractsTemplateList = session.createQuery("from ContractsTemplate ct where id=:contractTemplateId " ).setLong( "contractTemplateId", contractTemplateId ).setCacheable(true).list();
		logger.debug("getOrgSpocById end");
		if (contractsTemplateList.size() > 0) {
			return contractsTemplateList.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation) throws Exception
	{
		Long orgLocationId = orgLocation.getId();
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.orgLocationId.id =:orgLocationId and ct.templateStatus = '0'" ).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
			return contractsTemplateList;
		}

	@Override
	@Transactional
	public List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.orgLocationId.id =:orgLocationId and ct.templateType = :contractType and ct.templateStatus = '0'" ).setLong("orgLocationId", orgLocationId ).setString("contractType", contractType).setCacheable(true).list();
		return contractsTemplateList;
	}

	@Override
	@Transactional
	public ContractsTemplate getContractTemplateById(long templateId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractsTemplate  contractsTemplate =  null;
		List<ContractsTemplate>  contractsTemplateList = session.createQuery("from ContractsTemplate ct where ct.id =:templateId").setLong("templateId", templateId).setCacheable(true).list();
		if(null != contractsTemplateList && contractsTemplateList.size() >0){
			contractsTemplate = contractsTemplateList.get(0);
		}
		return contractsTemplate;
	}


	@Override
	@Transactional
	public Contracts saveNewContract(Contracts contracts) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contracts != null) {
				session.saveOrUpdate(contracts);
			}
			logger.debug("contractsTemplate Added Successfully");
		} catch (Exception exception) {
			   StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contracts;
	}

	

	
}
