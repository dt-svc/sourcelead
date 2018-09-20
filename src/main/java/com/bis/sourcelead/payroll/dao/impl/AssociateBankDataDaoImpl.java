package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AssociateBankDataDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;

@Repository
public class AssociateBankDataDaoImpl implements AssociateBankDataDao{

	private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public AssociateBankData saveAssociateBankData(AssociateBankData associateBankData)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateBankData != null) {
				session.saveOrUpdate(associateBankData);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateBankData;
	}

	@Override
	@Transactional
	public AssociateBankData findAssociateBankDataByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBankData associateBankData = null;
		try {
			List<AssociateBankData>  associateBankDataList = session.createQuery("from AssociateBankData bankData where bankData.candidate.id=:candidateId and bankData.orgLocationId.id= :orgLocationId" ).setLong( "candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).list();
		    if(associateBankDataList !=null && associateBankDataList.size() >0) {
		    	associateBankData = associateBankDataList.get(0);
		    }
		
		}catch (Exception e) {
		}
		return associateBankData;
	}

	@Override
	@Transactional
	public AssociateBankData findAssociateBankDataById(long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBankData associateBankData = null;
		try {
			List<AssociateBankData>  associateBankDataList = session.createQuery("from AssociateBankData bankData where bankData.id=:id" ).setLong( "id", id).list();
		    if(associateBankDataList !=null && associateBankDataList.size() >0) {
		    	associateBankData = associateBankDataList.get(0);
		    }
		
		}catch (Exception e) {
		}
		return associateBankData;
	}
}
