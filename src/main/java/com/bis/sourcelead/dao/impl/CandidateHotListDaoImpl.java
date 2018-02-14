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

import com.bis.sourcelead.dao.CandidateHotListDao;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.CandidateHotList;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class CandidateHotListDaoImpl implements CandidateHotListDao{

	
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

	private static final Logger logger = LoggerFactory.getLogger(CandidateHotListDaoImpl.class);

	@Override
	@Transactional
	public CandidateHotList saveCandidateHotList(CandidateHotList candidateHotList) throws Exception {
		
		logger.debug("Storing saveCandidateHotList");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateHotList != null) {
				session.saveOrUpdate(candidateHotList);
			}
			logger.debug("candidateHotList Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateHotList"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateHotList;
		
		
	}

	@Override
	@Transactional
	public Boolean isCandidateIsFavourite(Long candidateId, Long orgLocationId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidateHotList hotList where hotList.candidateId.id = :candidateId and hotList.orgLocationId.id = :orgLocationId and hotList.status = '1' "; 
		List<CandidateHotList> candidateHotList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
         boolean isCategoryFound = false;
		if (candidateHotList != null && !candidateHotList.isEmpty()) {
         	isCategoryFound = true;
         }
         return isCategoryFound;
	}

	@Override
	@Transactional
	public CandidateHotList getCandidateHotListByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidateHotList hotList where hotList.candidateId.id = :candidateId and hotList.orgLocationId.id = :orgLocationId"; 
		List<CandidateHotList> candidateHotList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
		if (candidateHotList.size() > 0) {
			return candidateHotList.get(0);
		} else {
			return null;
		}
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateHotList> getHotCandidateListForTeamByTeamId(Long teamId, Long orgLocationsId) {

		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from CandidateHotList hotList where hotList.teamId.id =:teamId and hotList.orgLocationId.id =:orgLocationsId  ";
		 List<CandidateHotList> candidateHotList = session.createQuery(query.toString()).setLong( "teamId", teamId).setLong( "orgLocationsId", orgLocationsId).list();
		 return candidateHotList;
	}

	@Override
	@Transactional
	public CandidateHotList getCandidateHotListById(Long id) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from CandidateHotList hotList where hotList.id = :id"; 
		List<CandidateHotList> candidateHotList = session.createQuery(query.toString()).setLong("id", id).list();
		if (candidateHotList.size() > 0) {
			return candidateHotList.get(0);
		} else {
			return null;
		}
	}
}
