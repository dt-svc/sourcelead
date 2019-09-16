package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.bis.sourcelead.dao.CandidateGlobalDao;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class CandidateGlobalDaoImpl implements CandidateGlobalDao{
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateGlobalDaoImpl.class);

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

	@Value("${jdbc.schema}")
	private String schema;


	@Override
	public CandidateGlobal getCandidateGlobalByCandidateId(Long candidateGlobalId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateGlobal candidate  " + "left join fetch candidate.consultantUser "
				+ "left join fetch candidate.teamId " + "left join fetch candidate.recruiterUserId "
				+ "left join fetch candidate.masterCandidateId " + "left join fetch candidate.parentCandidateId "
				+ "left join fetch candidate.orgRelationId left "
				+ "join fetch candidate.spocId left join fetch candidate.orgLocationId left join fetch candidate.orgLocationId.user where candidate.id =:candidateGlobalId";
		List candidateList = session.createQuery(query).setLong("candidateGlobalId", candidateGlobalId).list();
		CandidateGlobal candidate = null;
		if (candidateList != null && !candidateList.isEmpty()) {
			candidate = (CandidateGlobal) candidateList.iterator().next();
		}
		return candidate;
	
	}


	@Override
	public CandidateGlobal storeCandidateGlobal(CandidateGlobal candidateGlobal) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateGlobal != null) {
				session.saveOrUpdate(candidateGlobal);
			}
			logger.debug("CandidateGlobal Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "CandidateGlobal"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return candidateGlobal;
	}


	@Override
	public List<CandidateEducation> getCandidateGlobalEducationListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation ce where  ce.candidateGlobalId.id =:candidateId and ce.status ='1' ORDER BY ce.id DESC";
		List<CandidateEducation> candidateEducation = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateEducation;
	}


	@Override
	public CandidateGlobal findByCandidateGlobalEmail(String email, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateGlobal> candidate = new ArrayList<CandidateGlobal>();
		logger.debug("findByCandidateEmail start");
		try {
			candidate = session
					.createQuery(
							"from CandidateGlobal can left join fetch can.consultantUser left join fetch can.orgRelationId left join fetch can.parentCandidateId left join fetch can.masterCandidateId where can.email=:email and can.orgLocationId.id = :orgLocationId ORDER BY can.id DESC")
					.setString("email", email).setLong("orgLocationId", orgLocationId).list();

			logger.debug("findByCandidateEmail end");
			if (candidate.size() > 0) {
				return candidate.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}


	@Override
	public HashSet<CandidateHistory> getCandidateGlobalHistoryDetails(CandidateGlobal candidate, User user)
			throws Exception {
		
		HashSet<CandidateHistory> candidatehistoryList = new HashSet<CandidateHistory>(); 
		
		CandidateHistory candidateHistory = new CandidateHistory();
		candidateHistory.setCandidateGlobalId(candidate);
		candidateHistory.setCandidateType(candidate.getCandidateType());
		candidateHistory.setCandidateUUID(candidate.getCandidateUUID());
		candidateHistory.setIsFavourite(candidate.getIsFavourite());
		candidateHistory.setIsFavouriteAssociate(candidate.getIsFavouriteAssociate());
		candidateHistory.setFirstName(candidate.getFirstName());
		candidateHistory.setLastName(candidate.getLastName());
		candidateHistory.setCountry(candidate.getCountry());
		candidateHistory.setState(candidate.getState());
		candidateHistory.setCity(candidate.getCity());
		candidateHistory.setLocation(candidate.getLocation());
		candidateHistory.setZipCode(candidate.getZipCode());
		candidateHistory.setLatitude(candidate.getLatitude());
		candidateHistory.setLongitude(candidate.getLongitude());
		candidateHistory.setMobilePhone(candidate.getMobilePhone());
		candidateHistory.setAlternateMobilePhone(candidate.getAlternateMobilePhone());
		candidateHistory.setEmail(candidate.getEmail());
		candidateHistory.setCurrentRate(candidate.getCurrentRate());
		candidateHistory.setCurrentRateType(candidate.getCurrentRateType());
		candidateHistory.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
		candidateHistory.setDesignation(candidate.getDesignation());
		candidateHistory.setDesignationOtherType(candidate.getDesignationOtherType());
		candidateHistory.setExpectedRate(candidate.getExpectedRate());
		candidateHistory.setRateType(candidate.getRateType());
		candidateHistory.setExpectedRateCurrencey(candidate.getExpectedRateCurrencey());
		candidateHistory.setSummary(candidate.getSummary());
		candidateHistory.setDesignationType(candidate.getDesignationType());
		candidateHistory.setExperienceYears(candidate.getExperienceYears());
		candidateHistory.setExperienceMonths(candidate.getExperienceMonths());
		candidateHistory.setNoticePeriod(candidate.getNoticePeriod());
		candidateHistory.setAddress(candidate.getAddress());
		candidateHistory.setSkillsName(candidate.getSkillsName());
		candidateHistory.setVisaType(candidate.getVisaType());
		candidateHistory.setPrimarySkill(candidate.getPrimarySkill());
		candidateHistory.setIsAssociate(candidate.getIsAssociate());
		candidateHistory.setStatus(candidate.getStatus());
		candidateHistory.setCreatedUser(user);
		candidateHistory.setCreatedBy(user.getUserCode());
		candidatehistoryList.add(candidateHistory);
		return candidatehistoryList;
	
	}

}
