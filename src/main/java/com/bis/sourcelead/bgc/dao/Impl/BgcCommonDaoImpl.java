package com.bis.sourcelead.bgc.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.bgc.dao.BgcCommonDao;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;

/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
@Transactional(rollbackFor = { Exception.class })
public class BgcCommonDaoImpl implements BgcCommonDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BgcCommonDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetails BgcCandidateDetails) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(BgcCandidateDetails);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while saving Bgc Candidate Details in Database "+e.getMessage());
			return null;
		}
		return BgcCandidateDetails;
	}

}
