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

import com.bis.sourcelead.dao.OrgLocationIndiaBankingDetailsDao;
import com.bis.sourcelead.dto.OrgLocationIndiaBankingDetailsDTO;
import com.bis.sourcelead.entity.OrglocationIndiaBankingDetails;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgLocationIndiaBankingDetailsDaoImpl implements OrgLocationIndiaBankingDetailsDao{

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

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationIndiaBankingDetailsDaoImpl.class);

	@Override
	@Transactional
	public OrglocationIndiaBankingDetails saveOrgLocationIndiaBankingDetails(
			OrglocationIndiaBankingDetails orgLocationIndiaBankingDetails) throws Exception {
		
		logger.debug("Adding orglocationIndiaBankingDetails");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLocationIndiaBankingDetails != null) {
				session.saveOrUpdate(orgLocationIndiaBankingDetails);
			}
			logger.debug("orgLocationIndiaBankingDetails Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_INDIA_BANKING_DETAILS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLocationIndiaBankingDetails;
	}

	@Override
	@Transactional
	public OrglocationIndiaBankingDetails getOrglocationIndiaBankingDetailsByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationIndiaBankingDetails bankingDetails where bankingDetails.uuid = :uuid and bankingDetails.status = '1' ";  
		List<OrglocationIndiaBankingDetails> orglocationBankingDetailsList =  session.createQuery(query).setString("uuid", uuid).list();
		OrglocationIndiaBankingDetails orglocationBankingDetails = null;
		if (orglocationBankingDetailsList != null && orglocationBankingDetailsList.size() > 0) {
			orglocationBankingDetails = (OrglocationIndiaBankingDetails) orglocationBankingDetailsList.iterator().next();
		}
		return orglocationBankingDetails;
	}

	@Override
	@Transactional
	public List<OrgLocationIndiaBankingDetailsDTO> getOrglocationIndiaBankingDetailsList(Long orgLocationId)
			throws Exception {
		List<OrgLocationIndiaBankingDetailsDTO> bankingDetailsDTOList = new ArrayList<>();
		Session session = this.sessionFactory.getCurrentSession();
		List<OrglocationIndiaBankingDetails> orgLocationBankingDetailsList = null;
		Query bankingDetailsList= session.createQuery("from OrglocationIndiaBankingDetails bankingDetails left join fetch bankingDetails.orgLocationId  where bankingDetails.orgLocationId = :orgLocationId ");
		orgLocationBankingDetailsList = bankingDetailsList.setLong("orgLocationId", orgLocationId).list();
		
		if(orgLocationBankingDetailsList != null) {
			for(OrglocationIndiaBankingDetails bankingDetails : orgLocationBankingDetailsList) {
				OrgLocationIndiaBankingDetailsDTO bankingDetailsDTO = new OrgLocationIndiaBankingDetailsDTO();
				bankingDetailsDTO.setAccountName(bankingDetails.getAccountName());
				bankingDetailsDTO.setAccountNumber(bankingDetails.getAccountNumber());
				bankingDetailsDTO.setBankName(bankingDetails.getBankName());
				bankingDetailsDTO.setIfscCode(bankingDetails.getIfscCode());
				bankingDetailsDTO.setBsrNumber(bankingDetails.getBsrNumber());
				bankingDetailsDTO.setMicrCode(bankingDetails.getMicrCode());
				bankingDetailsDTO.setUuid(bankingDetails.getUuid());
				bankingDetailsDTO.setBankAddress(bankingDetails.getBankAddress());
				bankingDetailsDTOList.add(bankingDetailsDTO);
			}
		}
		
		return bankingDetailsDTOList;
	
	}

	@Override
	@Transactional
	public OrglocationIndiaBankingDetails deleteOrglocationIndiaBankingDetails(OrglocationIndiaBankingDetails orgLocationIndiaBankingDetails) throws Exception {
		
		logger.debug("Deleting orglocationIndiaBankingDetails");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLocationIndiaBankingDetails != null) {
				session.delete(orgLocationIndiaBankingDetails);
			}
			logger.debug("orglocationStateTax Deleted Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_INDIA_BANKING_DETAILS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLocationIndiaBankingDetails;
	
	}
}
