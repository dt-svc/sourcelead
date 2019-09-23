package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractRequestDocumentsDao;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ContractRequestDocumentsDaoImpl implements ContractRequestDocumentsDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ContractRequestDocumentsDaoImpl.class);
	
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
	public ContractRequestDocuments saveContractRequestDocuments(ContractRequestDocuments contractRequestDocuments) throws Exception
	{
		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contractRequestDocuments != null) {
				session.saveOrUpdate(contractRequestDocuments);
			}
			logger.debug("contract request document  Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CONTRACT_REQUEST_DOCUMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contractRequestDocuments;
	}


	@Override
	@Transactional
	public Integer getCountOfDocByContractedId(Long contractId)
	{
			Session session = this.sessionFactory.getCurrentSession();
			List<ContractRequestDocuments>  contractRequestDocumentsList = session.createQuery("from ContractRequestDocuments crq where crq.contractId.id =:contractId and crq.status = '"+Constants.MEMBER_ACTIVE+"'").setLong("contractId", contractId).list();
			return (Integer) contractRequestDocumentsList.size();
	}


	@Override
	@Transactional
	public List<ContractRequestDocuments> getAllDocByContractId(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<ContractRequestDocuments>  contractRequestDocumentsList = session.createQuery("from ContractRequestDocuments crq where crq.contractId.id =:contractId and crq.status = '"+Constants.MEMBER_ACTIVE+"'").setLong("contractId", contractId).list();
		return contractRequestDocumentsList;
	}


	@Override
	@Transactional
	public ContractRequestDocuments getContractRequestDocumentsById(Long docId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractRequestDocuments  contractRequestDocuments =  null;
		List<ContractRequestDocuments>  contractRequestDocumentseList = session.createQuery("from ContractRequestDocuments crq left join fetch crq.contractId where crq.id =:docId and crq.status = '"+Constants.MEMBER_ACTIVE+"'").setLong("docId", docId).list();
		if(null != contractRequestDocumentseList && contractRequestDocumentseList.size() >0){
			contractRequestDocuments = contractRequestDocumentseList.get(0);
		}
		return contractRequestDocuments;
	}


	@Override
	@Transactional
	public ContractRequestDocuments getContractRequestDocumentsByContractUUID(String contractUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ContractRequestDocuments  contractRequestDocuments =  null;
		List<ContractRequestDocuments>  contractRequestDocumentseList = session.createQuery("from ContractRequestDocuments crq  left join fetch crq.contractId contract where contract.contractUUID =:contractUUID and crq.status = '"+Constants.MEMBER_ACTIVE+"'").setString("contractUUID", contractUUID).list();
		if(null != contractRequestDocumentseList && contractRequestDocumentseList.size() >0){
			contractRequestDocuments = contractRequestDocumentseList.get(0);
		}
		return contractRequestDocuments;
	}

	@Override
	@Transactional
	public ContractRequestDocuments getContractByContractId(Long contractId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query="from ContractRequestDocuments cd  left join fetch crq.contractId contract where contract.id =:contractId and crq.status = '"+Constants.MEMBER_ACTIVE+"')";
	  List contractList = session.createQuery(query).setLong("contractId", contractId).list();
      
	  ContractRequestDocuments requirement = null;
      if(contractList != null && !contractList.isEmpty())
      {
          requirement = (ContractRequestDocuments) contractList.iterator().next();
      }
      return requirement;
	}
}
