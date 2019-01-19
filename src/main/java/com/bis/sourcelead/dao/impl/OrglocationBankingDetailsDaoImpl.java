package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrglocationBankingDetailsDao;
import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrglocationBankingDetailsDaoImpl implements OrglocationBankingDetailsDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(OrglocationBankingDetailsDaoImpl.class);


	@Override
	@Transactional
	public OrglocationBankingDetails saveOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails)
			throws Exception {
		
		logger.debug("Adding orglocationBankingDetails");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationBankingDetails != null) {
				session.saveOrUpdate(orglocationBankingDetails);
			}
			logger.debug("orglocationStateTax Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_BANKING_DETAILS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationBankingDetails;
	}


	@Override
	@Transactional
	public OrglocationBankingDetails getOrglocationBankingDetailsByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationBankingDetails bankingDetails where bankingDetails.uuid = :uuid and bankingDetails.status = '1' ";  
		List<OrglocationBankingDetails> orglocationBankingDetailsList =  session.createQuery(query).setString("uuid", uuid).list();
		OrglocationBankingDetails orglocationBankingDetails = null;
		if (orglocationBankingDetailsList != null && orglocationBankingDetailsList.size() > 0) {
			orglocationBankingDetails = (OrglocationBankingDetails) orglocationBankingDetailsList.iterator().next();
		}
		return orglocationBankingDetails;
	}


	@Override
	@Transactional
	public OrglocationBankingDetails getOrglocationBankingDetailsByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationBankingDetails bankingDetails where bankingDetails.orgLocationId.id = :orgLocationId and bankingDetails.status = '1' ";  
		List<OrglocationBankingDetails> orglocationBankingDetailsList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		OrglocationBankingDetails orglocationBankingDetails = null;
		if (orglocationBankingDetailsList != null && orglocationBankingDetailsList.size() > 0) {
			orglocationBankingDetails = (OrglocationBankingDetails) orglocationBankingDetailsList.iterator().next();
		}
		return orglocationBankingDetails;
	}


	@Override
	@Transactional
	public List<OrgLocationBankingDetailsDTO> getOrglocationBankingDetailsListByOrgLocationId(Long orgLocationId)
			throws Exception {
		List<OrgLocationBankingDetailsDTO> bankingDetailsDTOList = new ArrayList<>();
		Session session = this.sessionFactory.getCurrentSession();
		List<OrglocationBankingDetails> orgLocationBankingDetailsList = null;
		Query bankingDetailsList= session.createQuery("from OrglocationBankingDetails bankingDetails left join fetch bankingDetails.orgLocationId  where bankingDetails.orgLocationId = :orgLocationId ");
		orgLocationBankingDetailsList = bankingDetailsList.setLong("orgLocationId", orgLocationId).list();
		
		if(orgLocationBankingDetailsList != null) {
			for(OrglocationBankingDetails bankingDetails : orgLocationBankingDetailsList) {
				OrgLocationBankingDetailsDTO bankingDetailsDTO = new OrgLocationBankingDetailsDTO();
				bankingDetailsDTO.setAccountType(bankingDetails.getAccountType());
				bankingDetailsDTO.setAccountName(bankingDetails.getAccountName());
				bankingDetailsDTO.setAccountNumber(bankingDetails.getAccountNumber());
				bankingDetailsDTO.setBankName(bankingDetails.getBankName());
				bankingDetailsDTO.setRoutingNumber(bankingDetails.getRoutingNumber());
				bankingDetailsDTO.setUuid(bankingDetails.getUuid());
				bankingDetailsDTO.setBankAddress(bankingDetails.getBankAddress());
				bankingDetailsDTOList.add(bankingDetailsDTO);
			}
		}
		
		return bankingDetailsDTOList;
	
	}


	@Override
	@Transactional
	public OrglocationBankingDetails deleteOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails) throws Exception {
		
		logger.debug("Deleting orglocationBankingDetails");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationBankingDetails != null) {
				session.delete(orglocationBankingDetails);
			}
			logger.debug("orglocationStateTax Deleted Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_BANKING_DETAILS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationBankingDetails;
	}

}
