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
import com.bis.sourcelead.payroll.dao.AssociatePfEsiDao;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

@Repository
@Transactional
public class AssociatePfEsiDaoImpl implements AssociatePfEsiDao{

	private static final Logger logger = LoggerFactory.getLogger(AssociatePfEsiDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public AssociatePfEsiDetails saveAssociatePfEsi(AssociatePfEsiDetails associatePfEsiDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associatePfEsiDetails != null) {
				session.saveOrUpdate(associatePfEsiDetails);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associatePfEsiDetails;
	}

	@Override
	public AssociatePfEsiDetails findAssociatePfEsiByCandidateId(Long candidateId, OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		AssociatePfEsiDetails associatePfEsi = null;
		try {
			List<AssociatePfEsiDetails>  associatePfEsiList = session.createQuery("from AssociatePfEsiDetails ape left join fetch ape.candidateId where "
					+ "ape.candidateId.id = :candidateId and ape.orgLocationId.id= :orgLocationId" ).setLong( "candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).list();
		    if(associatePfEsiList !=null && associatePfEsiList.size() >0) {
		    	associatePfEsi = associatePfEsiList.get(0);
		    }
		}catch (Exception e) {
		}
		return associatePfEsi;
	}

	@Override
	public AssociatePfEsiDetails findAssociatePfEsiId(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		AssociatePfEsiDetails associatePfEsi = null;
		try {
			List<AssociatePfEsiDetails>  associatePfEsiList = session.createQuery("from AssociatePfEsiDetails ape where ape.id=:id" ).setLong( "id", id).list();
		    if(associatePfEsiList !=null && associatePfEsiList.size() >0) {
		    	associatePfEsi = associatePfEsiList.get(0);
		    }
		
		}catch (Exception e) {
		}
		return associatePfEsi;
	}
	 
}
