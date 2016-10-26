package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.RequirementAssignToService;

@Repository
public class RequirementSubmissionDaoImpl implements RequirementSubmissionDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RequirementDao requirementDao;
	
	@Autowired
	TeamDao teamDao;
	
	@Autowired
	TeamMemberDao teamMemberDao;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getRequirementSubmissionById(Long submissionId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();	
		RequirementSubmission submission = session.load(RequirementSubmission.class, new Long(submissionId));
		logger.info("RequirementSubmission loaded successfully, RequirementSubmission details="+submission);
		return submission;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.submissionStatus = 'SHORTLIST' and rs.id NOT IN (:existingApplicantsIds)";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).setCacheable(true).list();
		 }else{
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.submissionStatus = 'SHORTLIST' ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setCacheable(true).list();
		 }
	     return shortListCandidatesList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and ( rs.submissionStatus = 'SUBMISSION' or rs.submissionStatus = 'SHORTLIST' or rs.submissionStatus = 'INTERVIEW'  )  and rs.id NOT IN (:existingApplicantsIds)";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).setCacheable(true).list();
		 }else{
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and ( rs.submissionStatus = 'SUBMISSION' or rs.submissionStatus = 'SHORTLIST' or rs.submissionStatus = 'INTERVIEW'  ) ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setCacheable(true).list();
		 }
	     return shortListCandidatesList;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception {
		logger.debug("Adding RequirementSubmission");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementSubmission != null) {
				session.saveOrUpdate(requirementSubmission);
			}
			logger.debug("RequirementSubmission Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementSubmission;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId,User user, OrgLocations loginOrgLocation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		List<RequirementAssignTo> requirementAssignToList =  requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
		List<RequirementSubmission>  requirementSubmission = null;
		if(requirement.getManagerUserId().getId().equals(user.getId())){
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId";
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
		}
		
		Boolean teamFlag = teamDao.getTeamOwnersListByUserId(user,loginOrgLocation.getId());
		if(requirement.getManagerUserId().getId()!=user.getId()&& teamFlag){
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				if(requirementAssignTo.getTeamId().getId()!=null){
					Team team = teamDao.getTeamByTeamId(requirementAssignTo.getTeamId().getId());
					if(team.getOwnerUserId().getId()==user.getId()){
						List<TeamMembers> teamMembers =  teamMemberDao.getTeamMembersListByTeamID(requirementAssignTo.getTeamId().getId());
						List<String> userCodes = new ArrayList<>();
						userCodes.add(user.getUserCode());
						for (TeamMembers teamMember : teamMembers) {
							User usercode= userDao.getUserById(teamMember.getMemberUserId().getId());
							userCodes.add(usercode.getUserCode());
						}
						
						String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy IN (:usercode) and reqSub.requirement.id =:requirementId";
						  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).list();
					}
				}
			}
			
		}
		boolean flag = false;
		for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
			if(requirementAssignTo.getAssignUserId().getId()==user.getId()){
				flag = true;
			}
		}
		
		if(requirement.getManagerUserId().getId()!=user.getId()&& flag && !teamFlag ){
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId";
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
		}
		return requirementSubmission;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(Long orgLocationsId,
			Long requirementId,String submissionStatus) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String submissionStages = "";
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
			 submissionStages = "and rs.submissionStatus = 'SUBMISSION' ";
		 }
		 
		 else if(submissionStatus.equalsIgnoreCase("SHORTLIST")){
			 submissionStages = "and rs.submissionStatus <> 'SUBMISSION' and rs.submissionStatus <> 'SUBMIT_TO_CLIENT' ";
		 }
		 
	     String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId  "+submissionStages+" ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setCacheable(true).list();
	     return shortListCandidatesList;
	}
	
	
    
  }

