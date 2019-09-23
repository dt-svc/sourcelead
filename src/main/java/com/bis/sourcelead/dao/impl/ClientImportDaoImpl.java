package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ClientImportDao;
import com.bis.sourcelead.entity.BulkClientDetails;
import com.bis.sourcelead.entity.User;

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
	public List<BulkClientDetails> getbulkClientDetailsByOrgLocationId(Long orgLocationId, String responderType,User user)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<BulkClientDetails> bulkClientDetailsList = null;
		
		String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.recordStatus = '0' and bulkClientDetails.type =:responderType and bulkClientDetails.createdBy = :userCode";
		bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("responderType", responderType).setString("userCode", user.getUserCode()).list();
		
		return bulkClientDetailsList;
	}

	@Override
	@Transactional
	public BulkClientDetails isRecordExist(BulkClientDetails bulkClientVendors,Long orgLocationId, String responderType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
	    BulkClientDetails bulkClientDetails = null;
	    List<BulkClientDetails> bulkClientDetailsList = new ArrayList<>();
		if(bulkClientVendors.getOrgName()!=null || !"".equalsIgnoreCase(bulkClientVendors.getOrgName())){
			String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.email = :email and bulkClientDetails.ownerEmail = :ownerEmail and bulkClientDetails.orgName = :orgName  and bulkClientDetails.recordStatus ='0' and bulkClientDetails.type =:responderType";
			bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("email", bulkClientVendors.getEmail()).setString("ownerEmail", bulkClientVendors.getOwnerEmail()).setString("orgName", bulkClientVendors.getOrgName()).setString("responderType", responderType).list();
		}
		else{
			String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.email = :email and bulkClientDetails.ownerEmail = :ownerEmail and  bulkClientDetails.recordStatus ='0' and bulkClientDetails.type =:responderType";
			bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("email", bulkClientVendors.getEmail()).setString("ownerEmail", bulkClientVendors.getOwnerEmail()).setString("responderType", responderType).list();
			
		}
		
		if(null!= bulkClientDetailsList && bulkClientDetailsList.size() >0){
			return bulkClientDetailsList.get(0);
		}
		return bulkClientDetails;
		
	}

	@Override
	@Transactional
	public BulkClientDetails getbulkClientByrecordIdAndOrgLocationId(long recordId, Long orgLocationId,String responderType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		BulkClientDetails bulkClientDetails = null;
		String query = "from BulkClientDetails bulkClientDetails  where orgLocationId =:orgLocationId and bulkClientDetails.id = :recordId and bulkClientDetails.type =:responderType";
		List<BulkClientDetails> bulkClientDetailsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("responderType", responderType).setLong("recordId", recordId).list();
		
		if(null != bulkClientDetailsList && bulkClientDetailsList.size() >0){
			bulkClientDetails = bulkClientDetailsList.get(0);
		}
		return bulkClientDetails;
	}

	@Override
	@Transactional
	public void updateBulkImportedClients() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    // Update Employee
        Query query = session.createQuery("update BulkClientDetails bulk set bulk.recordStatus = '1' ");
        int result = query.executeUpdate();
        if(result > 0) {
        	logger.debug("Staus changed successfully");
        }
	}

	
    

}

