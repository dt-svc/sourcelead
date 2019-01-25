package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.dto.RequirementInterviewerDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementInterviewerDaoImpl implements RequirementInterviewerDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewerDaoImpl.class);
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterviewer storeRequirementInterviewer(RequirementInterviewer interviewer) throws Exception {
		logger.debug("Adding RequirementInterviewer");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (interviewer != null) {
				session.saveOrUpdate(interviewer);
			}
			logger.debug("RequirementInterviewer Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return interviewer;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId) {
		logger.debug("In RequirementInterviewerDaoImpl class getInterviwersListByInterviewId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewer interviewer left join fetch interviewer.interviewerUser where interviewer.requirementInterview.id = :requirementInterviewId and interviewer.status = 1";
		List<RequirementInterviewer> requirementInterviewersList =  session.createQuery(query).setLong("requirementInterviewId", requirementInterviewId).list();
		return requirementInterviewersList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewerDTO>  getInterviwersListByInterviewIdFromDTO(Long requirementInterviewId) {
		List<RequirementInterviewerDTO> list = new ArrayList<>();
		logger.debug("In RequirementInterviewerDaoImpl class getInterviwersListByInterviewId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewer interviewer left join fetch interviewer.interviewerUser where interviewer.requirementInterview.id = :requirementInterviewId and interviewer.status = 1";
		List<RequirementInterviewer> requirementInterviewersList =  session.createQuery(query).setLong("requirementInterviewId", requirementInterviewId).list();
		for(RequirementInterviewer requirementInterviewer : requirementInterviewersList) {
			RequirementInterviewerDTO requirementInterviewerDTO = new RequirementInterviewerDTO();
			requirementInterviewerDTO.setId(requirementInterviewer.getId());
			requirementInterviewerDTO.setUuid(requirementInterviewer.getUuid());
			
			if(requirementInterviewer.getInterviewerUser() != null) {
				if(requirementInterviewer.getInterviewerUser().getLastName() != null) {
					StringBuilder intName = new StringBuilder();
					requirementInterviewerDTO.setInterviewerName(intName.append(requirementInterviewer.getInterviewerUser().getFirstName()).append(requirementInterviewer.getInterviewerUser().getLastName()).toString());
				}else {
					requirementInterviewerDTO.setInterviewerName(requirementInterviewer.getInterviewerUser().getFirstName());
				}
			}else {
				requirementInterviewerDTO.setInterviewerName(requirementInterviewer.getExternalInterviewerName());
			}
		
			list.add(requirementInterviewerDTO);
		}
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterviewer getInterviwerstByInterviewId(Long requirementInterviewerId) {
		logger.debug("In RequirementInterview getRequirementInterviewById method");
		Long requirementInterviewerIdValue = requirementInterviewerId;
		Session session = this.sessionFactory.getCurrentSession();
		return (RequirementInterviewer) session.get(RequirementInterviewer.class, requirementInterviewerIdValue);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterviewer getInterviwerByInterviewIdAndUserId(Long requirementInterviewerId, Long userId) {
	

		logger.debug("In RequirementInterviewer getInterviwerstByInterviewIdAndUserId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterviewer rir where rir.requirementInterview.id = :requirementInterviewerId and rir.interviewerUser.id = :userId" ;
		List<RequirementInterviewer> interviewerList =session.createQuery(query).setLong("requirementInterviewerId", requirementInterviewerId).setLong("userId", userId).list(); 
		RequirementInterviewer interviewer = null;
		if(interviewerList != null && !interviewerList.isEmpty()){
			interviewer = interviewerList.iterator().next();
		}
		return interviewer;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewer> getinterviewListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
			List<Long> interviewerIds,Date startUTCDate, Date endUTCDate) {
		logger.debug("In RequirementInterviewer getInterviwerstByInterviewIdAndUserId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterviewer rir where rir.requirementInterview.submissionId.orgLocations.id = :loginOrgLocations and rir.interviewerUser.id IN(:userId) and( rir.requirementInterview.startTime >= :startDateUTC and rir.requirementInterview.endTime <= :endDateUTC)" ;
		List<RequirementInterviewer> interviewerList =session.createQuery(query).setLong("loginOrgLocations", loginOrgLocations.getId()).setParameter("startDateUTC", startUTCDate).setParameter("endDateUTC", endUTCDate).setParameterList("userId", interviewerIds).list(); 
		return interviewerList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean isInterviwerForByRequirementIdInterviewIdAndUserId(String reqUUID, Long requirementInterviewerId, Long userId){
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(requirementInterviewerId != null && reqUUID != null && userId != null){
				String query = "from RequirementInterviewer ri where ri.interviewerUser.id = :userId and ri.requirementInterview.submissionId.requirement.requirementUUID = :reqUUID and ri.requirementInterview.id = :requirementInterviewerId";
				List<RequirementInterviewer> interviewList = session.createQuery(query).setLong("userId", userId).setLong("requirementInterviewerId", requirementInterviewerId).setString("reqUUID", reqUUID).list();
				if (interviewList != null && interviewList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public RequirementInterviewer getRequirementInterviewerByUUID(String uuid) throws Exception {
		logger.debug("In RequirementInterviewerDaoImpl class getInterviwersListByInterviewId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewer interviewer left join fetch interviewer.interviewerUser where interviewer.uuid = :uuid and interviewer.status = 1";
		List<RequirementInterviewer> interviewerList =  session.createQuery(query).setString("uuid", uuid).list();
		RequirementInterviewer interviewer = null;
		if(interviewerList != null && !interviewerList.isEmpty()){
			interviewer = interviewerList.iterator().next();
		}
		return interviewer;
	}

	@Override
	@Transactional
	public List<RequirementInterviewerDTO> getInterviwersListByInterviewIdAndSubmissionIdFromDTO(Long interviewId,
			Long submissionId) {
		List<RequirementInterviewerDTO> list = new ArrayList<>();
		logger.debug("In RequirementInterviewerDaoImpl class getInterviwersListByInterviewId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewer interviewer left join fetch interviewer.interviewerUser left join fetch interviewer.requirementInterview interview left join fetch interview.submissionId submission where interviewer.requirementInterview.id = :requirementInterviewId "
				+ " and submission.id = :submissionId and interviewer.status = 1";
		List<RequirementInterviewer> requirementInterviewersList =  session.createQuery(query).setLong("requirementInterviewId", interviewId).setLong("submissionId", submissionId).list();
		for(RequirementInterviewer requirementInterviewer : requirementInterviewersList) {
			RequirementInterviewerDTO requirementInterviewerDTO = new RequirementInterviewerDTO();
			requirementInterviewerDTO.setId(requirementInterviewer.getId());
			requirementInterviewerDTO.setUuid(requirementInterviewer.getUuid());
			
			if(requirementInterviewer.getInterviewerUser() != null) {
				if(requirementInterviewer.getInterviewerUser().getLastName() != null) {
					StringBuilder intName = new StringBuilder();
					requirementInterviewerDTO.setInterviewerName(intName.append(requirementInterviewer.getInterviewerUser().getFirstName()).append(requirementInterviewer.getInterviewerUser().getLastName()).toString());
				}else {
					requirementInterviewerDTO.setInterviewerName(requirementInterviewer.getInterviewerUser().getFirstName());
				}
			}else {
				requirementInterviewerDTO.setInterviewerName(requirementInterviewer.getExternalInterviewerName());
			}
		
			list.add(requirementInterviewerDTO);
		}
		return list;
	}

	@Override
	@Transactional
	public RequirementInterviewer getRequirementInterviewerByInterviewIdAndExternalInterviewerEmail(
			Long requirementInterviewId, String email) throws Exception {
	

		logger.debug("In RequirementInterviewer getInterviwerstByInterviewIdAndUserId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterviewer rir where rir.requirementInterview.id = :requirementInterviewId and rir.externalInterviewerEmail = :email" ;
		List<RequirementInterviewer> interviewerList =session.createQuery(query).setLong("requirementInterviewId", requirementInterviewId).setString("email", email).list(); 
		RequirementInterviewer interviewer = null;
		if(interviewerList != null && !interviewerList.isEmpty()){
			interviewer = interviewerList.iterator().next();
		}
		return interviewer;
		
	}

	@Override
	@Transactional
	public List<RequirementInterviewer> getInterviwersListByInterviewIdAndUserID(Long requirementInterviewId,Long interviewerUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewer interviewer left join fetch interviewer.interviewerUser where interviewer.requirementInterview.id = :requirementInterviewId and interviewer.interviewerUser.id = :interviewerUserId and interviewer.status = 1";
		List<RequirementInterviewer> requirementInterviewersList =  session.createQuery(query).setLong("requirementInterviewId", requirementInterviewId).setLong("interviewerUserId", interviewerUserId).list();
		return requirementInterviewersList;
	}
	
}
