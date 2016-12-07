package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
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
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
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
	UserRoleDao userRoleDao;
	
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
		String query = "from RequirementSubmission rs left join fetch rs.requirement where rs.id = :submissionId";
		List<RequirementSubmission>  submission = session.createQuery(query).setLong( "submissionId", submissionId).list();
		logger.info("RequirementSubmission loaded successfully, RequirementSubmission details="+submission);
		return submission.get(0);
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' ) and rs.id NOT IN (:existingApplicantsIds)";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).setCacheable(true).list();
		 }else{
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' ) ";
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
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
		if (isadmin && requirement.getManagerUserId().getId()!=user.getId()) {
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
		}
		
		if(requirement.getManagerUserId().getId()== user.getId()){
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
			requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
		}
		
		Boolean teamFlag = teamDao.getTeamOwnersListByUserId(user,loginOrgLocation.getId());
		if(requirement.getManagerUserId().getId()!=user.getId()&& teamFlag){
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				if(requirementAssignTo.getTeamId()!=null){
					Team team = teamDao.getTeamByTeamId(requirementAssignTo.getTeamId().getId());
					if(team.getOwnerUserId()!=null){
						if(team.getOwnerUserId().getId()==user.getId()){
							List<TeamMembers> teamMembers =  teamMemberDao.getTeamMembersListByTeamID(requirementAssignTo.getTeamId().getId(),loginOrgLocation.getId());
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
		}
		boolean flag = false;
		for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
			if(requirementAssignTo.getAssignUserId().getId()==user.getId()){
				flag = true;
			}
		}
		
		if(requirement.getManagerUserId().getId()!=user.getId()&& flag && !teamFlag ){
			String query = "from RequirementSubmission reqSub where (reqSub.orgLocations.id =:loginOrgLocation or reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId";
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
		}
		
		if(requirement.getManagerUserId().getId()!=user.getId()&& !flag && !teamFlag && !isadmin){
			String query = "from RequirementSubmission reqSub where (reqSub.orgLocations.id =:loginOrgLocation or reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId";
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus, String dateRange,String userType,User user) {
		
        Session session = this.sessionFactory.getCurrentSession();
        
        if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
        	Map<String, Object> params = new HashMap<String,Object>();
    		
    		String from = "select count(*) from RequirementSubmission rs";
    		
    		//login user as created by
    		String subQuery1 = "rs.requirement.id IN (select req.id from Requirement req where req.createdBy =:loginUserCode)";
    		params.put("loginUserCode",user.getUserCode());
    		
    		//login user as a approver for this requirement  or  assigned to.
    		String subQuery2 = "rs.requirement.id IN (select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId )  or (rs.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) ) ";
    		params.put("approverUserId",user.getId());
    		params.put("userId",user.getId());
    		
    		
    		//login user as a  manager for this job.
    		String subQuery3 = "rs.requirement.id IN ( select requirement.id from Requirement requirement where  requirement.managerUserId.id = :managerUserId )  ";
    		params.put("managerUserId",user.getId());
    		
    		
    		String subQuery = "";
    		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" ){
    			if(dateRange != null && "today".equals(dateRange)){
    				subQuery = "where rs.orgLocations.id = :orgLocationsId and rs.submissionStatus = :submissionStatus and  rs.status = 1 and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) ";
    			}else if(dateRange != null && "previous".equals(dateRange)){
    			    subQuery = "where rs.orgLocations.id = :orgLocationsId and rs.submissionStatus = :submissionStatus and rs.status = 1  and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) ";
    			}
    			params.put("orgLocationsId",orgLocationsId);
    			params.put("submissionStatus",submissionStatus);
    		}
    		String whereCondition = subQuery;
    		Query query =  session.createQuery(from+" "+whereCondition+" ");
    		
    		for (String str : query.getNamedParameters())
            {
    			if(str.equalsIgnoreCase("orgLocationsId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
    				query.setLong(str, (long) params.get( str ));
    			}
    			if(str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")){
    				query.setInteger(str, (int) params.get( str ));
    			}
    			if(str.equalsIgnoreCase("submissionStatus") ){
    				query.setParameter(str,ApplicantSubmissionStatus.getNameByValue(submissionStatus));
    			}
    			else{
    				query.setParameter( str, params.get( str ) );
    			}
            	
            }
    		
    		List<Long> reqSubmsCount  = query.list();
    		Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.get(0).intValue();
			}
			return count;
			
        }else{
        	
        	String query = "";
        	if(dateRange != null && "today".equals(dateRange)){
                query ="select count(*) from RequirementSubmission rs where rs.orgLocations = :orgLocationsId and rs.submissionStatus = :submissionStatus and rs.status = 1  and DATE(rs.createdDate) = CURDATE()";
     		}else if(dateRange != null && "previous".equals(dateRange)){
     			query ="select count(*) from RequirementSubmission rs where rs.orgLocations = :orgLocationsId and rs.submissionStatus = :submissionStatus and rs.status = 1  and DATE(rs.createdDate) < CURDATE()";
     		}
        	
        	List<Long> reqSubmsCount =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("submissionStatus", submissionStatus).list();
			Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.get(0).intValue();
			}
			return count;
        	
        }
		
		
		    
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getRequirementPerCandidate(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.vendorLocationId left join fetch rs.orgLocations left join fetch rs.candidateResumes left join fetch rs.sourceSubmission left join fetch rs.candidate where rs.candidate.id = :candidateId";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong("candidateId", candidateId).setCacheable(true).list();
	     return shortListCandidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId,Long orgLocationsId) {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs where rs.id =:sourceSubmissionId and rs.vendorLocationId.id =:orgLocationsId ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "sourceSubmissionId", sourceSubmissionId).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	   
	     if(shortListCandidatesList != null && shortListCandidatesList.size() >0){
				return shortListCandidatesList.get(0);
			}
			return null;
	   	     	     
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,
			String submissionStatus) throws Exception {
		
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmission rs left join fetch rs.requirement where rs.id = :submissionId and rs.submissionStatus = :submissionStatus ";
		List<RequirementSubmission> requirementSubmissionsList =  session.createQuery(query.toString()).setLong("submissionId", submissionId).setString("submissionStatus", submissionStatus).setCacheable(true).list();
		
		RequirementSubmission requirementSubmission = null;
		if (requirementSubmissionsList != null && requirementSubmissionsList.size() > 0) {
			requirementSubmission = (RequirementSubmission) requirementSubmissionsList.iterator().next();
		}
		return requirementSubmission;
	
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId,
			Long requirementId, String submissionStatus){
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String submissionStages = "";
		 
		 if(submissionStatus.equalsIgnoreCase("APPLICANTS")){
			 submissionStages = "and (rs.submissionStatus = 'SUBMISSION' OR rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'REJECTED' OR rs.submissionStatus = 'SUBMIT_TO_CLIENT' OR rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' OR rs.submissionStatus = 'OFFERHIRED')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("SHORTLISTED")){
			 submissionStages = "and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'REJECTED' OR rs.submissionStatus = 'SUBMIT_TO_CLIENT' OR rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' OR rs.submissionStatus = 'OFFERHIRED')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMITTED")){
			 submissionStages = "and (rs.submissionStatus = 'SUBMIT_TO_CLIENT')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("INTERVIEWED")){
			 submissionStages = "and (rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'REJECTED' OR rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' OR rs.submissionStatus = 'OFFERHIRED')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("OFFERED")){
			 submissionStages = "and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' OR rs.submissionStatus = 'OFFERHIRED')";
		 }
		 
	     String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId  "+submissionStages+" ";
	     List<Long> candidatesCount = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).list();
	     Integer count = 0;
			if(candidatesCount != null && !candidatesCount.isEmpty()){
					count = candidatesCount.size();
			}
			return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long vendorSubmissionId,
			Long vendororgLocationsId) {
		
	
			Session session = this.sessionFactory.getCurrentSession();
			
		     String query = "from RequirementSubmission rs where rs.sourceSubmission.id = :vendorSubmissionId and rs.vendorLocationId.id =:vendororgLocationsId ";
		     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "vendorSubmissionId", vendorSubmissionId).setLong( "vendororgLocationsId", vendororgLocationsId).setCacheable(true).list();
		   
		     if(shortListCandidatesList != null && shortListCandidatesList.size() >0){
					return shortListCandidatesList.get(0);
				}
		  return null;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*)from RequirementSubmission rs where rs.requirement.id =:requirementId and rs.createdBy =:userCode";
		List<Long> submissionCount =   session.createQuery(query.toString()).setLong("requirementId", requirementId).setString("userCode", userCode)
        										.list();
			Integer count= 0;
			if(submissionCount != null && !submissionCount.isEmpty()){
					count = submissionCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getVendorSubmissionByClientSubmissionIdAndCLientLocationId(Long clientSubmissionId,
			Long clientOrgLocationId) {
		

		Session session = this.sessionFactory.getCurrentSession();
		
	     String query = "from RequirementSubmission rs where rs.id = :clientSubmissionId and rs.orgLocations.id =:clientOrgLocationId ";
	     List<RequirementSubmission> vendorsubmissionList = session.createQuery(query).setLong( "clientSubmissionId", clientSubmissionId).setLong( "clientOrgLocationId", clientOrgLocationId).setCacheable(true).list();
	   
	     if(vendorsubmissionList != null && vendorsubmissionList.size() >0){
				return vendorsubmissionList.get(0);
			}
	  return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId) {
		Session session = this.sessionFactory.getCurrentSession();	
		String query = "from RequirementRateSubmissionNegotiation rs left join fetch rs.requirementsubmissionId where rs.createdDate IN( select max(rs1.createdDate) from  RequirementRateSubmissionNegotiation rs1 where rs1.requirementsubmissionId.id = :submissionId  )";
		List<RequirementRateSubmissionNegotiation>  submission = session.createQuery(query).setLong( "submissionId", submissionId).list();
		logger.info("RequirementRateSubmission loaded successfully, RequirementRateSubmission details="+submission);
		return submission.get(0);
		
	}
		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		List<RequirementAssignTo> requirementAssignToList =  requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
		List<RequirementSubmission>  requirementSubmission = null;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
		if (isadmin && requirement.getManagerUserId().getId()!=user.getId()) {
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and reqSub.submissionStatus = :submissionType";
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setString("submissionType", submissionType).list();
		}
		
		if(requirement.getManagerUserId().getId()== user.getId()){
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and reqSub.submissionStatus = :submissionType";
			requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setString("submissionType", submissionType).list();
		}
		
		Boolean teamFlag = teamDao.getTeamOwnersListByUserId(user,loginOrgLocation.getId());
		if(requirement.getManagerUserId().getId()!=user.getId()&& teamFlag){
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				if(requirementAssignTo.getTeamId()!=null){
					Team team = teamDao.getTeamByTeamId(requirementAssignTo.getTeamId().getId());
					if(team.getOwnerUserId()!=null){
						if(team.getOwnerUserId().getId()==user.getId()){
							List<TeamMembers> teamMembers =  teamMemberDao.getTeamMembersListByTeamID(requirementAssignTo.getTeamId().getId(),loginOrgLocation.getId());
							List<String> userCodes = new ArrayList<>();
							userCodes.add(user.getUserCode());
							for (TeamMembers teamMember : teamMembers) {
								User usercode= userDao.getUserById(teamMember.getMemberUserId().getId());
								userCodes.add(usercode.getUserCode());
							}
							
							String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy IN (:usercode) and reqSub.requirement.id =:requirementId and reqSub.submissionStatus = :submissionType";
							  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).setString("submissionType", submissionType).list();
						}
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
			String query = "from RequirementSubmission reqSub where (reqSub.orgLocations.id =:loginOrgLocation or reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId";
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
		}
		
		if(requirement.getManagerUserId().getId()!=user.getId()&& !flag && !teamFlag && !isadmin){
			String query = "from RequirementSubmission reqSub where (reqSub.orgLocations.id =:loginOrgLocation or reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId";
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
		}
		return requirementSubmission;
	}
	
	
	/*
	SUBMISSION("submission"),
	SHORTLIST("shortList"),
	INTERVIEW("interview"),
	REJECTED("rejected"),
    SUBMIT_TO_CLIENT("submit_to_client"),
    OFFERMADE("offer_made"),
    OFFERACCEPTED("offer_accepted"),
    OFFERREJECTED("offer_rejected"),
    OFFERHIRED("hired");
	*/
    
  }

