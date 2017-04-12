package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ClientImportDao;
import com.bis.sourcelead.entity.BulkClientDetails;

@Repository
public class ClientImportDaoImpl implements ClientImportDao{
	private static final Logger logger = LoggerFactory.getLogger(ClientImportDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public BulkClientDetails saveClientVendorExcelData(BulkClientDetails bulkClientDetails) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (bulkClientDetails != null) {
				session.saveOrUpdate(bulkClientDetails);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
		}
		return bulkClientDetails;
	}

	@Override
	@Transactional
	public List<BulkClientDetails> getbulkClientDetailsByOrgLocationId(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<BulkClientDetails> bulkClientDetailsList = null;
		
		String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.recordStatus = '0'";
		bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return bulkClientDetailsList;
	}

	@Override
	@Transactional
	public BulkClientDetails isRecordExist(BulkClientDetails bulkClientVendors,Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
	    BulkClientDetails bulkClientDetails = null;
		
		String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.email = :email and bulkClientDetails.ownerEmail = :ownerEmail and bulkClientDetails.orgName = :orgName  ";
		List<BulkClientDetails> bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("email", bulkClientVendors.getEmail()).setString("ownerEmail", bulkClientVendors.getOwnerEmail()).setString("orgName", bulkClientVendors.getOrgName()).setCacheable(true).list();
		if(null!= bulkClientDetailsList && bulkClientDetailsList.size() >0){
			return bulkClientDetailsList.get(0);
		}
		return bulkClientDetails;
		
	}

	@Override
	@Transactional
	public BulkClientDetails getbulkClientByrecordIdAndOrgLocationId(long recordId, Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		BulkClientDetails bulkClientDetails = null;
		
		String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.id = :recordId";
		List<BulkClientDetails> bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("recordId", recordId).setCacheable(true).list();
		
		if(null != bulkClientDetailsList && bulkClientDetailsList.size() >0){
			bulkClientDetails = bulkClientDetailsList.get(0);
		}
		return bulkClientDetails;
	}

	
    

}

