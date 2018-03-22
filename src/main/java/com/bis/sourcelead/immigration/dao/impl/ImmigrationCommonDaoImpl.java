package com.bis.sourcelead.immigration.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;


/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
public class ImmigrationCommonDaoImpl implements ImmigrationCommonDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	CandidateDao candidateDao;

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

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationCommonDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		logger.debug("Saving Template Details-->");
		List<ImmigrationTemplate> templateLst = new ArrayList<ImmigrationTemplate>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			System.out.println(" before query execute templateLst size -->"+templateLst.size());
			String queryString  = "from ImmigrationTemplate it where it.status = 1 and it.orgLocations.id =:orgLocationId";
			templateLst= session.createQuery(queryString).setLong("orgLocationId", orgLocations.getId()).list();
			System.out.println("tempalte Details loaded Successfully templateLst size -->"+templateLst.size());
			
			logger.debug("tempalte Details loaded Successfully-->");
		} catch (Exception exception) {
			
				exception.printStackTrace();
		}
		return templateLst;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration savePetitionDetails(Immigration immigration, Candidate candidate,
			CandidateIdendities candidateIdendities, CandidateResumes candidateResume,
			List<CandidateLcaI94Summary> lcaSummaryLst, List<CandidateResidence> residenceList) throws Exception {
		logger.debug("savePetitionDetails starts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidate != null) {
					session.saveOrUpdate(candidate);
			}
			if (immigration != null) {
				session.saveOrUpdate(immigration);
			}
			if(candidateIdendities != null) {
				session.saveOrUpdate(candidateIdendities);
			}
			if(candidateResume!= null && candidateResume.getFileURL() != null) {
				session.saveOrUpdate(candidateResume);
			}
			if(lcaSummaryLst.size() != 0) {
				for(CandidateLcaI94Summary candidateLcaI94Summary : lcaSummaryLst) {
					session.saveOrUpdate(candidateLcaI94Summary);
				}
			}
			if(residenceList != null && residenceList.size() != 0) { // saving multiple residence for candidates only for immigration
				for(CandidateResidence residence : residenceList) {
					session.saveOrUpdate(residence);
				}
			}
		} catch (Exception exception) {
		    logger.error("Exception caught while saving immigration information "+exception.getMessage());
		    exception.printStackTrace();
		}
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Immigration getImmigrationViewList(String immigrationId) {
		logger.debug("getImmigrationViewList starts");
		Session session = this.sessionFactory.getCurrentSession();
		Immigration immigration =  (Immigration) session.get(Immigration.class, Long.parseLong(immigrationId));
		return immigration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getPetitionTypeById(Long petitionTypeId, String petitionTypeConstant) {
		Session session = this.sessionFactory.getCurrentSession();
        String query = "select lud.name from LookUpData lud where lud.category = :category and lud.id = :lookupId";
        String name = (String) session.createQuery(query).setString("category", petitionTypeConstant).setLong("lookupId", petitionTypeId).uniqueResult();
		return name;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateLcaI94Summary getCandidateLCADetails(Long candidateId, String yes) {
		 Session session = this.sessionFactory.getCurrentSession();
	        String query = "from CandidateLcaI94Summary clis where clis.candidateId.id = :candidateId and clis.isCurrent = :isCurrent";
	        CandidateLcaI94Summary lcaI94Summary = (CandidateLcaI94Summary) session.createQuery(query).setLong("candidateId", candidateId).setString("isCurrent", yes).uniqueResult();
		return lcaI94Summary;
	}

}
