package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.util.CommonUtil;

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
	CommonUtil commonUtil;
	
	@Autowired
	TeamMemberDao teamMemberDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getRequirementSubmissionById(Long submissionId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();	
		String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate candidate left join fetch candidate.consultantUser left join fetch rs.sourceSubmission left join fetch rs.candidateResumes   where rs.id = :submissionId";
		List<RequirementSubmission>  submission = session.createQuery(query).setLong( "submissionId", submissionId).setCacheable(true).list();
		logger.info("RequirementSubmission loaded successfully !!!");
		return submission.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getClientRequierementSubmission(Long submissionId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();	
		String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes   where rs.sourceSubmission.id = :submissionId";
		List<RequirementSubmission>  submission = session.createQuery(query).setLong( "submissionId", submissionId).setCacheable(true).list();
		logger.info("RequirementSubmission loaded successfully !!!");
		if (submission.size() > 0) {
			return submission.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientInterview,User user,String loginUserRole) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 if(isClientInterview != null && "Y".equalsIgnoreCase(isClientInterview)){
			 subQuery = " and (rs.submissionStatus = 'SUBMIT_TO_CLIENT' OR rs.submissionStatus = 'CLIENT_INTERVIEW' ) "; 
		 }else{
			 subQuery = " and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' ) "; 
		 }
		 
		 String subQuery2 = "";
		 
		 Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,orgLocations.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery2 = " and ((rs.createdBy = '"+user.getUserCode()+"')  OR (rs.vendorLocationId IS NOT NULL) OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL)) ";
		 }
		 
		 
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.id NOT IN (:existingApplicantsIds) "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).setCacheable(true).list();
		 }else{
			 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setCacheable(true).list();
		 }
	     return shortListCandidatesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer,User user,String loginUserRole) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String subQuery = "";
		 if(isClientOffer != null && "Y".equalsIgnoreCase(isClientOffer)){
			 subQuery = " and (rs.submissionStatus = 'SUBMIT_TO_CLIENT' OR rs.submissionStatus = 'CLIENT_INTERVIEW' ) "; 
		 }else{
			 subQuery = " and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' OR "
			 		+ " rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'CLIENT_OFFERREJECTED' OR rs.submissionStatus = 'CLIENT_OFFERHIRED') "; 
		 }
		 
		 String subQuery2 = "";
		 
		 Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,orgLocations.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery2 = " and ((rs.createdBy = '"+user.getUserCode()+"') OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL)) ";
		 }
		 
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.id NOT IN (:existingApplicantsIds) "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).setCacheable(true).list();
		 }else{
			 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setCacheable(true).list();
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId,User user, OrgLocations loginOrgLocation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		String[] releasedBy =null;
		
		ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
		
		if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			releasedBy = requirement.getReleasedBy().split(",");
		}
		 boolean requirementReleased = false;
		 List<Long> releasedByUserIds = new ArrayList<Long>();
		 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
		 for (String s : requirement.getReleasedBy().split(",")){
			 releasedByUserIds.add(Long.parseLong(s));
    	 }
		 if(releasedByUserIds != null ){
			 requirementReleased = releasedByUserIds.contains(user.getId());
		 }
		 }
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
        Map<String, Object> params = new HashMap<String,Object>();
		String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
		String subQuery = "";
		
		
		// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
			if(null != releasedBy && releasedBy.length > 0){
				
				String subQueryForVendors = "where req.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
												params.put("userId",user.getId());
												params.put("loginOrgLocation",loginOrgLocation.getId());
												params.put("requirementId",requirementId);
				
	            String whereCondition = subQueryForVendors;
				
				Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
				for (String str : queryForVendors.getNamedParameters())
		        {
					if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
						queryForVendors.setLong(str, (long) params.get( str ));
					}else{
						queryForVendors.setParameter( str, params.get( str ) );
					}
		        }
				
				List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.setCacheable(true).list() ; 
				 List<Long> sourceSubmissionIds = new ArrayList<Long>();
				 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
					 for(RequirementSubmission rs :vendorSubmittedApplicants){
							sourceSubmissionIds.add(rs.getId());
						}
						String sourceSubmissionIdQuery = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  "; 
						List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString())
																			.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
																			.setCacheable(true).list();
						finalList.addAll(finalSourceList);
						
				   }
				
			}
			
			/// These Conditions are for  own Organization  Submitted Applicants
			if(isadmin){
				subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
				params.put("loginOrgLocation",loginOrgLocation.getId());
				params.put("requirementId",requirementId);
				
				// This Condition is if The Requirement is Released by the login User
			}else{
				//This is for Requirement Manger 
				String subQuery1 = "reqSub.requirement.managerUserId.id  =:userId ";
				params.put("userId",user.getId());
				
				//This is for Requirement Created by
				String subQuery6 = "reqSub.requirement.createdBy =:requirementUsercode ";
				params.put("requirementUsercode",user.getUserCode());
				
				// This is if the user is  created by 
				String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
				params.put("usercode",user.getUserCode());
				
				//  this is if the  user is   Assign Team  Owner
				String subQuery3 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
				params.put("userId",user.getId());
				
			   //  this is if the  user is   Assign Team  Member
				String subQuery4 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
				params.put("userId",user.getId());
				params.put("usercode",user.getUserCode());
				
				
				 //  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
				String subQuery5 = "req.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
				params.put("userId",user.getId());
				
				if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 !="" ){
					subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and req.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery6+")  OR ("+subQuery4+") OR ("+subQuery5+")   )";
					params.put("loginOrgLocation",loginOrgLocation.getId());
					params.put("requirementId",requirementId);
				}
			}
			
			String whereCondition = subQuery;
			
			Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
			for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
					query.setLong(str, (long) params.get( str ));
				}else{
					query.setParameter( str, params.get( str ) );
				}
	        }
			
			List<RequirementSubmission> ownApplicantsList = query.setCacheable(true).list() ;
		    finalList.addAll(ownApplicantsList);
		    List<RequirementSubmission> listDistinct = finalList.stream().distinct().collect(Collectors.toList());
		return listDistinct;
		
		/*
		
		//to get all applicates list if login user is manager or admin
		if (isadmin || requirement.getManagerUserId().getId().longValue() == user.getId().longValue() ) {
			String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch reqSub.candidate left "
					+ "join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes  "
					+ "where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ORDER BY reqSub.candidateScore DESC ";
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
		}
		//to get applicates list of the team lead and team submissions
		
		
		
		Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,loginOrgLocation.getId(),requirement.getId());
		if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag){
			
			
			List<String> userCodes = new ArrayList<>();
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				User usercode= userDao.getUserById(requirementAssignTo.getAssignUserId().getId());
				userCodes.add(usercode.getUserCode());
			}
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy IN (:usercode) and reqSub.requirement.id =:requirementId ORDER BY reqSub.id DESC";
			requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).setCacheable(true).list();

			  
		}  
		boolean flag = false;
		for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
			if(requirementAssignTo.getAssignUserId().getId().longValue() == user.getId().longValue()){
				flag = true;
			}
		}
		//to get applicates list of the team member 
		if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
			String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch "
					+ "reqSub.candidate left join fetch reqSub.sourceSubmission "
					+ "left join fetch reqSub.candidateResumes where (reqSub.orgLocations.id =:loginOrgLocation or "
					+ "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.requirement.id =:requirementId ORDER BY reqSub.id DESC";
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
		}else if(!isadmin && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && !flag && !teamFlag){
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and  reqSub.requirement.id  IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId) ORDER BY reqSub.candidateScore DESC ";
			requirementSubmission = session.createQuery(query).setLong("userId", user.getId()).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();

		}
		
		
		
		return requirementSubmission;*/
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(OrgLocations loginOrgLocation,
			Long requirementId,String submissionStatus,User user,String loginUserRole) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String subQuery = "";
		 
		 Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,loginOrgLocation.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery = " and ((rs.createdBy = '"+user.getUserCode()+"') OR (rs.vendorLocationId IS NOT NULL) OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL)) ";
		 }
		 
		 String submissionStages = "";
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
			 submissionStages = "and rs.submissionStatus = 'SUBMISSION' ";
		 }
		 
		 else if(submissionStatus.equalsIgnoreCase("SHORTLIST")){
			 submissionStages = "and rs.submissionStatus <> 'SUBMISSION' and rs.submissionStatus <> 'SUBMIT_TO_CLIENT' ";
		 }
		 
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId  "+submissionStages+" "+subQuery+" ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", loginOrgLocation.getId()).setLong( "requirementId", requirementId).setCacheable(true).list();
	     return shortListCandidatesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus, String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate) {
		
        Session session = this.sessionFactory.getCurrentSession();
        
        String submissionStages = "";
        if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
			 submissionStages = "and rs.submissionStatus = 'SUBMISSION' ";
		}else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
			 submissionStages = "and rs.submissionStatus = 'SUBMIT_TO_CLIENT' ";
		}else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
			submissionStages = "and (rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'CLIENT_INTERVIEW') ";
		}else if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
			submissionStages = "and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' "
					+ " OR rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'CLIENT_OFFERREJECTED') ";
		}
        
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
    				subQuery = "where rs.orgLocations.id = :orgLocationsId "+submissionStages+" and rs.status = 1 and rs.updatedDate >= :startDate and rs.updatedDate <= :endDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) ";
    			}else if(dateRange != null && "previous".equals(dateRange)){
    			    subQuery = "where rs.orgLocations.id = :orgLocationsId "+submissionStages+" and rs.status = 1 and rs.updatedDate < :startDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) ";
    			}
    			params.put("orgLocationsId",orgLocationsId);
    			params.put("startDate",startUTCDate);
    			params.put("endDate",endUTCDate);
    			//params.put("submissionStatus",submissionStatus);
    		}
    		String whereCondition = subQuery;
    		Query query =  session.createQuery(from+" "+whereCondition+" ");
    		
    		for (String str : query.getNamedParameters())
            {
    			if(str.equalsIgnoreCase("orgLocationsId")  || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
    				query.setLong(str, (long) params.get( str ));
    			}else{
    				query.setParameter( str, params.get( str ) );
    			}
            	
            }
    		
    		List<Long> reqSubmsCount  = query.setCacheable(true).list();
    		Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.get(0).intValue();
			}
			return count;
			
        }else{
        	List<Long> reqSubmsCount = null;
        	String query = "";
        	if(dateRange != null && "today".equals(dateRange)){
                query ="select count(*) from RequirementSubmission rs where rs.orgLocations = :orgLocationsId "+submissionStages+" and rs.status = 1  and rs.updatedDate >= :startDate and rs.updatedDate <= :endDate";
                 reqSubmsCount =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).setCacheable(true).list();
     		}else if(dateRange != null && "previous".equals(dateRange)){
     			query ="select count(*) from RequirementSubmission rs where rs.orgLocations = :orgLocationsId "+submissionStages+" and rs.status = 1  and rs.updatedDate < :startDate";
     			 reqSubmsCount =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setCacheable(true).list();
     		}
        	
        	//List<Long> reqSubmsCount =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setDate("startDate",startUTCDate).setDate("endDate",endUTCDate).setCacheable(true).list();
			Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.get(0).intValue();
			}
			return count;
        	
        }
		
		
		    
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId,User user,String loginUserRole) throws Exception {
		
        Session session = this.sessionFactory.getCurrentSession();
        
        
        String subQuery = "";
		 
		 Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,orgLocations.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery = " and rs.createdBy = '"+user.getUserCode()+"' ";
		 }
        
        String submissionStages = "";
        
    		String query ="select count(*) from RequirementSubmission rs where rs.orgLocations = :orgLocationsId and rs.requirement.id =:requirementId "+subQuery+" ";
    		
    		List<Long> reqSubmsCount  = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocations.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
    		Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.get(0).intValue();
			}
			return count;
	}
	
	
	//to get candidates for submit to client
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(OrgLocations loginOrgLocation, Requirement requirement,User user,String loginUserRole) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String subQuery = "";
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,loginOrgLocation.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery = " and ((rs.createdBy = '"+user.getUserCode()+"') OR (rs.vendorLocationId IS NOT NULL) OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL)) ";
		 }
		 
		 
		 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId "
		 		+ " and rs.requirement.id = :requirementId "+subQuery+" and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERHIRED' ) "
		 				+ " and rs.id NOT IN (select submission.sourceSubmission from RequirementSubmission submission) ";
		 List<RequirementSubmission>  shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", loginOrgLocation.getId()).setLong( "requirementId", requirement.getId()).setCacheable(true).list();
	     return shortListCandidatesList;
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
	public RequirementSubmission validateRequirementCandidateEmail(String email, Long requirementId, Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> submission = new ArrayList<RequirementSubmission>();
		logger.debug("findByEmail start");
		try{
			submission = session.createQuery("from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.candidate.email = :email and rs.requirement.id = :requirementId and rs.orgLocations.id = :orgLocationsId " )
					.setString( "email", email ).setLong("requirementId", requirementId ).setLong("orgLocationsId", orgLocationsId ).setCacheable(true).list();
			
			logger.debug("findByEmail end");
			if (submission.size() > 0) {
				return submission.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId,Long orgLocationsId) {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.id =:sourceSubmissionId and rs.vendorLocationId.id =:orgLocationsId ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "sourceSubmissionId", sourceSubmissionId).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	   
	     if(shortListCandidatesList != null && shortListCandidatesList.size() >0){
				return shortListCandidatesList.get(0);
			}
			return null;
	   	     	     
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getResumeIdsByRequirementSubmissionId(List<Long> submissionIds) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select rs.candidateResumes.id from RequirementSubmission rs where rs.id IN (:submissionIds) " ;
	    List<Long> resumeIds = session.createQuery(query).setParameterList( "submissionIds", submissionIds).setCacheable(true).list();
        return resumeIds;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,
			String submissionStatus) throws Exception {
		
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.id = :submissionId and rs.submissionStatus = :submissionStatus ";
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
			 submissionStages = "";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("SHORTLISTED")){
			 submissionStages = "and (rs.submissionStatus = 'SHORTLIST')";
		 }
		 if(submissionStatus.equalsIgnoreCase("BACKOUT")){
			 submissionStages = "and (rs.submissionStatus = 'BACKOUT')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMITTED")){
			 submissionStages = "and (rs.submissionStatus = 'SUBMIT_TO_CLIENT')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("INTERVIEWED")){
			 submissionStages = "and (rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'CLIENT_INTERVIEW')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("OFFERED")){
			 submissionStages = "and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' OR rs.submissionStatus = 'OFFERHIRED' OR "
			 		+ " rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'CLIENT_OFFERREJECTED' OR rs.submissionStatus = 'CLIENT_OFFERHIRED' )";
		 }
		 
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId  "+submissionStages+" ";
	     List<Long> candidatesCount = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setCacheable(true).list();
	     Integer count = 0;
			if(candidatesCount != null && !candidatesCount.isEmpty()){
					count = candidatesCount.size();
			}
			return count;
	}
	
	
	@Override
    @Transactional(rollbackFor = { Exception.class })
    public Integer getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long requirementId, User user, OrgLocations loginOrgLocation, String submissionStatus) throws Exception{
         Session session = this.sessionFactory.getCurrentSession();
        
         String submissionStages = "";
        
         if(submissionStatus.equalsIgnoreCase("APPLICANTS")){
             submissionStages = "";
         }else if(submissionStatus.equalsIgnoreCase("SHORTLISTED")){
             submissionStages = "and (reqSub.submissionStatus = 'SHORTLIST')";
         }else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
             submissionStages = "and (reqSub.submissionStatus = 'SUBMIT_TO_CLIENT')";
         }else if(submissionStatus.equalsIgnoreCase("INTERVIEWED")){
             submissionStages = "and (reqSub.submissionStatus = 'INTERVIEW' OR reqSub.submissionStatus = 'CLIENT_INTERVIEW')";
         }else if(submissionStatus.equalsIgnoreCase("REJECTED")){
             submissionStages = "and (reqSub.submissionStatus = 'REJECTED')";
         }else if(submissionStatus.equalsIgnoreCase("REQUESTSENT")){
             submissionStages = "and (reqSub.submissionStatus = 'REQUEST_SENT')";
         }else if(submissionStatus.equalsIgnoreCase("BACKOUT")){
             submissionStages = "and (reqSub.submissionStatus = 'BACKOUT')";
         }else if(submissionStatus.equalsIgnoreCase("OFFERED")){
             submissionStages = "and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' OR reqSub.submissionStatus = 'OFFERHIRED' OR "
                     + " reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED' OR reqSub.submissionStatus = 'CLIENT_OFFERHIRED' )";
         }
        
//         Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
//            List<RequirementAssignTo> requirementAssignToList =  requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
//            List<Long> candidatesCount = null;
//            Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
//            
//            //to get all applicates list if login user is manager or admin
//            if (isadmin || requirement.getManagerUserId().getId().longValue() == user.getId().longValue() ) {
//                String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId "+submissionStages+" ";
//                candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
//            }
//            //to get applicates list of the team lead and team submissions
//            
//            Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,loginOrgLocation.getId(),requirement.getId());
//            if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag){
//                List<String> userCodes = new ArrayList<>();
//                for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
//                    User usercode= userDao.getUserById(requirementAssignTo.getAssignUserId().getId());
//                    userCodes.add(usercode.getUserCode());
//                }
//                String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy IN (:usercode) and reqSub.requirement.id =:requirementId "+submissionStages+" ";
//                candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).setCacheable(true).list();
//            }  
//            
//            
//            boolean flag = false;
//            for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
//                if(requirementAssignTo.getAssignUserId().getId().longValue() == user.getId().longValue()){
//                    flag = true;
//                }
//            }
//            //to get applicates list of the team member
////            if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
////                String query = "select count(*) from RequirementSubmission reqSub where (reqSub.orgLocations.id =:loginOrgLocation or "
////                        + "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId "+submissionStages+" ";
////                candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).setCacheable(true).list();
////            }
//            //to get applicates list of the team member
//            if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
//                String query = "select count(*) from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch "
//                        + "reqSub.candidate left join fetch reqSub.sourceSubmission "
//                        + "left join fetch reqSub.candidateResumes where (reqSub.orgLocations.id =:loginOrgLocation or "
//                        + "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId "+submissionStages+" ORDER BY reqSub.id DESC";
//                candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).setCacheable(true).list();
//            }else if(!isadmin && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && !flag && !teamFlag){
//                String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and  reqSub.requirement.id  IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId) "+submissionStages+" ORDER BY reqSub.candidateScore DESC ";
//                candidatesCount = session.createQuery(query).setLong("userId", user.getId()).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
//
//            }
//        
//            Integer count = 0;
//            if(candidatesCount != null && !candidatesCount.isEmpty()){
//                    count = candidatesCount.get(0).intValue();
//            }
//            return count;
        
         Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
            String[] releasedBy =null;
            
            ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
            
            if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
                releasedBy = requirement.getReleasedBy().split(",");
            }
             boolean requirementReleased = false;
             List<Long> releasedByUserIds = new ArrayList<Long>();
             if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
             for (String s : requirement.getReleasedBy().split(",")){
                 releasedByUserIds.add(Long.parseLong(s));
             }
             if(releasedByUserIds != null ){
                 requirementReleased = releasedByUserIds.contains(user.getId());
             }
             }
            Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
            Map<String, Object> params = new HashMap<String,Object>();
            String from = "from RequirementSubmission reqSub";
            String subQuery = "";
            
            
            // These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
                if(null != releasedBy && releasedBy.length > 0){
                    
                    String subQueryForVendors = "where reqSub.requirement.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
                                                    params.put("userId",user.getId());
                                                    params.put("loginOrgLocation",loginOrgLocation.getId());
                                                    params.put("requirementId",requirementId);
                    
                    String whereCondition = subQueryForVendors;
                    
                    Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
                    for (String str : queryForVendors.getNamedParameters())
                    {
                        if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
                            queryForVendors.setLong(str, (long) params.get( str ));
                        }else{
                            queryForVendors.setParameter( str, params.get( str ) );
                        }
                    }
                    
                    List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.setCacheable(true).list() ;
                     List<Long> sourceSubmissionIds = new ArrayList<Long>();
                     if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
                         for(RequirementSubmission rs :vendorSubmittedApplicants){
                                sourceSubmissionIds.add(rs.getId());
                            }
                            String sourceSubmissionIdQuery = "from RequirementSubmission reqSub  where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)   " +submissionStages;
                            List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString())
                                                                                .setParameterList("sourceSubmissionIds", sourceSubmissionIds)
                                                                                .setCacheable(true).list();
                            finalList.addAll(finalSourceList);
                            
                       }
                    
                }
                
                /// These Conditions are for  own Organization  Submitted Applicants
                if(isadmin){
                    subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId "+submissionStages;
                    params.put("loginOrgLocation",loginOrgLocation.getId());
                    params.put("requirementId",requirementId);
                    
                    // This Condition is if The Requirement is Released by the login User
                }else{
                    //This is for Requirement Manger
                    String subQuery1 = "reqSub.requirement.managerUserId.id  =:userId ";
                    params.put("userId",user.getId());
                    
                    // This is if the user is  created by
                    String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
                    params.put("usercode",user.getUserCode());
                    
                    //  this is if the  user is   Assign Team  Owner
                    String subQuery3 = "reqSub.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
                    params.put("userId",user.getId());
                    
                   //  this is if the  user is   Assign Team  Member
                    String subQuery4 = "reqSub.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
                    params.put("userId",user.getId());
                    params.put("usercode",user.getUserCode());
                    
                    
                    //  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
    				String subQuery5 = "reqSub.requirement.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
    				params.put("userId",user.getId());
    				
    				
                    
                    if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != ""  || subQuery5 != ""  ){
                        subQuery = "where reqSub.orgLocations.id =:loginOrgLocation  "+submissionStages+" and reqSub.requirement.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery3+")  OR ("+subQuery4+") OR ("+subQuery5+")  )";
                        params.put("loginOrgLocation",loginOrgLocation.getId());
                        params.put("requirementId",requirementId);
                    }
                }
                
                String whereCondition = subQuery;
                
                Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
                for (String str : query.getNamedParameters())
                {
                    if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
                        query.setLong(str, (long) params.get( str ));
                    }else{
                        query.setParameter( str, params.get( str ) );
                    }
                }
                
                List<RequirementSubmission> ownApplicantsList = query.setCacheable(true).list() ;
                finalList.addAll(ownApplicantsList);
                List<RequirementSubmission> listDistinct = finalList.stream().distinct().collect(Collectors.toList());
            return listDistinct.size();
    }
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer duplicateCandidateCountsByRequirementId(Long requirementId, User user,
			OrgLocations loginOrgLocation) throws Exception {
		  Session session = this.sessionFactory.getCurrentSession();
//		    List<Long> candidatesCount = null;

		  String statusSubQuery = " and reqSub.candidate.email IN(select tempReqSub.candidate.email from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id) and reqSub.submissionStatus <> 'REJECTED'";
		  
		  
		  
		  Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
			String[] releasedBy =null;
			
			ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
			
			if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
				releasedBy = requirement.getReleasedBy().split(",");
			}
			 boolean requirementReleased = false;
			 List<Long> releasedByUserIds = new ArrayList<Long>();
			 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			 for (String s : requirement.getReleasedBy().split(",")){
				 releasedByUserIds.add(Long.parseLong(s));
	    	 }
			 if(releasedByUserIds != null ){
				 requirementReleased = releasedByUserIds.contains(user.getId());
			 }
			 }
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
	        Map<String, Object> params = new HashMap<String,Object>();
			String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
			String subQuery = "";
			
			
			// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
				if(null != releasedBy && releasedBy.length > 0){
					
					String subQueryForVendors = "where req.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
													params.put("userId",user.getId());
													params.put("loginOrgLocation",loginOrgLocation.getId());
													params.put("requirementId",requirementId);
					
		            String whereCondition = subQueryForVendors;
					
					Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
					for (String str : queryForVendors.getNamedParameters())
			        {
						if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
							queryForVendors.setLong(str, (long) params.get( str ));
						}else{
							queryForVendors.setParameter( str, params.get( str ) );
						}
			        }
					
					List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.setCacheable(true).list() ; 
					 List<Long> sourceSubmissionIds = new ArrayList<Long>();
					 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
						 for(RequirementSubmission rs :vendorSubmittedApplicants){
								sourceSubmissionIds.add(rs.getId());
							}
							String sourceSubmissionIdQuery = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  " +statusSubQuery; 
							List<RequirementSubmission> finalSourceList = null;
																				
										finalSourceList =		session.createQuery(sourceSubmissionIdQuery.toString())
										.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
										.setLong("requirementId",requirementId).setCacheable(true).list();
										
																				
							finalList.addAll(finalSourceList);
							
					   }
					
				}
				
				/// These Conditions are for  own Organization  Submitted Applicants
				if(isadmin){
					subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
					params.put("loginOrgLocation",loginOrgLocation.getId());
					params.put("requirementId",requirementId);
					
					// This Condition is if The Requirement is Released by the login User
				}else{
					//This is for Requirement Manger 
					String subQuery1 = "reqSub.requirement.managerUserId.id  =:userId ";
					params.put("userId",user.getId());
					
					//This is for Requirement Created by
					String subQuery6 = "reqSub.requirement.createdBy =:requirementUsercode ";
					params.put("requirementUsercode",user.getUserCode());
					
					
					// This is if the user is  created by 
					String subQuery2 = "reqSub.createdBy =:usercode";
					params.put("usercode",user.getUserCode());
					
					//  this is if the  user is   Assign Team  Owner
					String subQuery3 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
					params.put("userId",user.getId());
					
				   //  this is if the  user is   Assign Team  Member
					String subQuery4 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
					params.put("userId",user.getId());
					params.put("usercode",user.getUserCode());
					
				//  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
    				String subQuery5 = "req.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
    				params.put("userId",user.getId());
    				
    				
					
					if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != ""  || subQuery6 !=""){
						subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and req.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery3+")  OR ("+subQuery4+")  OR ("+subQuery5+")   )";
						params.put("loginOrgLocation",loginOrgLocation.getId());
						params.put("requirementId",requirementId);
					}
				}
				
				String whereCondition = subQuery+statusSubQuery;
				
				Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
				for (String str : query.getNamedParameters())
		        {
					if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
						query.setLong(str, (long) params.get( str ));
					}else{
						query.setParameter( str, params.get( str ) );
					}
		        }
				
				List<RequirementSubmission> ownApplicantsList = query.setCacheable(true).list() ;
			    finalList.addAll(ownApplicantsList);
			    List<RequirementSubmission> listDistinct = finalList.stream().distinct().collect(Collectors.toList());
			return listDistinct.size();
			
			
		  
		  
		  
		  
		  /*
		    Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
			List<RequirementAssignTo> requirementAssignToList =  requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
			List<Long> candidatesCount = null;
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
			
			//to get all applicates list if login user is manager or admin
			if (isadmin || requirement.getManagerUserId().getId().longValue() == user.getId().longValue() ) {
				String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId "+subQuery+" ";
				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
			}
			//to get applicates list of the team lead and team submissions
			
			Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,loginOrgLocation.getId(),requirement.getId());
			if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag){
				List<String> userCodes = new ArrayList<>();
				for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
					User usercode= userDao.getUserById(requirementAssignTo.getAssignUserId().getId());
					userCodes.add(usercode.getUserCode());
				}
				String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy IN (:usercode) and reqSub.requirement.id =:requirementId "+subQuery+" ";
				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).setCacheable(true).list();
			}  
			
			
			boolean flag = false;
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				if(requirementAssignTo.getAssignUserId().getId().longValue() == user.getId().longValue()){
					flag = true;
				}
			}
			//to get applicates list of the team member 
//			if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
//				String query = "select count(*) from RequirementSubmission reqSub where (reqSub.orgLocations.id =:loginOrgLocation or "
//						+ "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId "+submissionStages+" ";
//				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).setCacheable(true).list();
//			}
			//to get applicates list of the team member 
			if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
				String query = "select count(*) from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch "
						+ "reqSub.candidate left join fetch reqSub.sourceSubmission "
						+ "left join fetch reqSub.candidateResumes where (reqSub.orgLocations.id =:loginOrgLocation or "
						+ "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId "+subQuery+" ORDER BY reqSub.id DESC";
				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).setCacheable(true).list();
			}else if(!isadmin && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && !flag && !teamFlag){
				String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and  reqSub.requirement.id  IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId) "+subQuery+" ORDER BY reqSub.candidateScore DESC ";
				candidatesCount = session.createQuery(query).setLong("userId", user.getId()).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();

			}
		 
	        Integer count = 0;
			if(candidatesCount != null && !candidatesCount.isEmpty()){
					count = candidatesCount.get(0).intValue();
			}
			return count;*/
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long vendorSubmissionId,
			Long vendororgLocationsId) {
		
	
			Session session = this.sessionFactory.getCurrentSession();
			
		     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.sourceSubmission.id = :vendorSubmissionId and rs.vendorLocationId.id =:vendororgLocationsId ";
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
				                                                            .setCacheable(true).list();
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
		
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.id = :clientSubmissionId and rs.orgLocations.id =:clientOrgLocationId ";
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
		String query = "from RequirementRateSubmissionNegotiation rs where rs.createdDate IN( select max(rs1.createdDate) from  RequirementRateSubmissionNegotiation rs1 where rs1.requirementsubmissionId.id = :submissionId  )";
		List<RequirementRateSubmissionNegotiation>  submission = session.createQuery(query).setLong( "submissionId", submissionId).setCacheable(true).list();
		logger.info("RequirementRateSubmission loaded successfully !!!");
		return submission.get(0);
		
	}
		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String statusSubQuery = "";
		if(submissionType != null && "INTERVIEW".equalsIgnoreCase(submissionType)){
			statusSubQuery = " and (reqSub.submissionStatus = 'INTERVIEW' OR reqSub.submissionStatus = 'CLIENT_INTERVIEW') "; 
		}else if(submissionType != null && "OFFERMADE".equalsIgnoreCase(submissionType)){
			statusSubQuery = " and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' "
					+ " OR reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED') "; 
		}else if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(submissionType)){
			statusSubQuery = " and reqSub.candidate.email IN(select tempReqSub.candidate.email from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id) and reqSub.submissionStatus <> 'REJECTED'";
			//subQuery = " and reqSub.submissionStatus = '"+submissionType+"' ";
		}else{
			statusSubQuery = " and reqSub.submissionStatus = '"+submissionType+"' ";
		}
		
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		String[] releasedBy =null;
		
		ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
		
		if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			releasedBy = requirement.getReleasedBy().split(",");
		}
		 boolean requirementReleased = false;
		 List<Long> releasedByUserIds = new ArrayList<Long>();
		 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
		 for (String s : requirement.getReleasedBy().split(",")){
			 releasedByUserIds.add(Long.parseLong(s));
    	 }
		 if(releasedByUserIds != null ){
			 requirementReleased = releasedByUserIds.contains(user.getId());
		 }
		 }
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
        Map<String, Object> params = new HashMap<String,Object>();
		String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
		String subQuery = "";
		
		
		// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
			if(null != releasedBy && releasedBy.length > 0){
				
				String subQueryForVendors = "where req.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
												params.put("userId",user.getId());
												params.put("loginOrgLocation",loginOrgLocation.getId());
												params.put("requirementId",requirementId);
				
	            String whereCondition = subQueryForVendors;
				
				Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
				for (String str : queryForVendors.getNamedParameters())
		        {
					if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
						queryForVendors.setLong(str, (long) params.get( str ));
					}else{
						queryForVendors.setParameter( str, params.get( str ) );
					}
		        }
				
				List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.setCacheable(true).list() ; 
				 List<Long> sourceSubmissionIds = new ArrayList<Long>();
				 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
					 for(RequirementSubmission rs :vendorSubmittedApplicants){
							sourceSubmissionIds.add(rs.getId());
						}
						String sourceSubmissionIdQuery = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  " +statusSubQuery; 
						List<RequirementSubmission> finalSourceList = null;
																			
								if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(submissionType)){
									finalSourceList =		session.createQuery(sourceSubmissionIdQuery.toString())
									.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
									.setLong("requirementId",requirementId).setCacheable(true).list();
									
								}else{
									finalSourceList =	session.createQuery(sourceSubmissionIdQuery.toString())
									.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
									.setCacheable(true).list();
								}
																			
						finalList.addAll(finalSourceList);
						
				   }
				
			}
			
			/// These Conditions are for  own Organization  Submitted Applicants
			if(isadmin){
				subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
				params.put("loginOrgLocation",loginOrgLocation.getId());
				params.put("requirementId",requirementId);
				
				// This Condition is if The Requirement is Released by the login User
			}else{
				//This is for Requirement Manger 
				String subQuery1 = "reqSub.requirement.managerUserId.id  =:userId ";
				params.put("userId",user.getId());
				
				//This is for Requirement Created by
				String subQuery6 = "reqSub.requirement.createdBy =:requirementUsercode ";
				params.put("requirementUsercode",user.getUserCode());
				
				// This is if the user is  created by 
				String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
				params.put("usercode",user.getUserCode());
				
				//  this is if the  user is   Assign Team  Owner
				String subQuery3 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
				params.put("userId",user.getId());
				
			   //  this is if the  user is   Assign Team  Member
				String subQuery4 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
				params.put("userId",user.getId());
				params.put("usercode",user.getUserCode());
				
				
			//  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
				String subQuery5 = "req.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
				params.put("userId",user.getId());
				
				if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 !=""  ){
					subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and req.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery3+")  OR ("+subQuery4+") OR ("+subQuery5+")    )";
					params.put("loginOrgLocation",loginOrgLocation.getId());
					params.put("requirementId",requirementId);
				}
			}
			
			String whereCondition = subQuery+statusSubQuery;
			
			Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
			for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
					query.setLong(str, (long) params.get( str ));
				}else{
					query.setParameter( str, params.get( str ) );
				}
	        }
			
			List<RequirementSubmission> ownApplicantsList = query.setCacheable(true).list() ;
		    finalList.addAll(ownApplicantsList);
		    List<RequirementSubmission> listDistinct = finalList.stream().distinct().collect(Collectors.toList());
		return listDistinct;
		
		/*Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		List<RequirementAssignTo> requirementAssignToList =  requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
		List<RequirementSubmission>  requirementSubmission = null;
		
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
		if (isadmin || requirement.getManagerUserId().getId().longValue() == user.getId().longValue() ) {
			String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch reqSub.candidate left "
					+ "join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes  "
					+ "where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId "+subQuery+" ";
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).setCacheable(true).list();
		}
		//to get applicates list of the team lead and team submissions
		
		
		
		Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,loginOrgLocation.getId(),requirement.getId());
		if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag){
			List<String> userCodes = new ArrayList<>();
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				User usercode= userDao.getUserById(requirementAssignTo.getAssignUserId().getId());
				userCodes.add(usercode.getUserCode());
			}
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.createdBy IN (:usercode) and reqSub.requirement.id =:requirementId "+subQuery+" ";
			requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).setCacheable(true).list();

			  
		}  
		boolean flag = false;
		for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
			if(requirementAssignTo.getAssignUserId().getId().longValue() == user.getId().longValue()){
				flag = true;
			}
		}
		//to get applicates list of the team member 
		if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
			String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch "
					+ "reqSub.candidate left join fetch reqSub.sourceSubmission "
					+ "left join fetch reqSub.candidateResumes where (reqSub.orgLocations.id =:loginOrgLocation or "
					+ "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId "+subQuery+" ORDER BY reqSub.candidateScore DESC ";
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).setCacheable(true).list();
		}
		
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		String[] releasedBy =null;
		if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			releasedBy = requirement.getReleasedBy().split(",");
		}
		 boolean requirementReleased = false;
		 List<Long> releasedByUserIds = new ArrayList<Long>();
		 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
		 for (String s : requirement.getReleasedBy().split(",")){
			 releasedByUserIds.add(Long.parseLong(s));
    	 }
		 if(releasedByUserIds != null ){
			 requirementReleased = releasedByUserIds.contains(user.getId());
		 }
		 }
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
        Map<String, Object> params = new HashMap<String,Object>();
		String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
		String subQuery = "";
		
		if(isadmin){
			subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
			params.put("loginOrgLocation",loginOrgLocation.getId());
			params.put("requirementId",requirementId);
			
			// This Condition is if The Requirement is Released by the login User
		}else if(null != releasedBy && requirementReleased){
			
			subQuery = "where req.id IN ( select rq.id from Requirement rq where  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation ) ) ";
			params.put("userId",user.getId());
			params.put("loginOrgLocation",loginOrgLocation.getId());
			
     
		}else{
			//This is for Requirement Manger 
			String subQuery1 = "reqSub.requirement.managerUserId.id  =:userId ";
			params.put("userId",user.getId());
			
			// This is if the user is  created by 
			String subQuery2 = "reqSub.createdBy =:usercode";
			params.put("usercode",user.getUserCode());
			
			//  this is if the  user is   Assign Team  Owner
			String subQuery3 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
			params.put("userId",user.getId());
			
		   //  this is if the  user is   Assign Team  Member
			String subQuery4 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
			params.put("userId",user.getId());
			params.put("usercode",user.getUserCode());
			
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" ){
				subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and req.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery3+")  OR ("+subQuery4+") )";
				params.put("loginOrgLocation",loginOrgLocation.getId());
				params.put("requirementId",requirementId);
			}
		}
		
		
		String whereCondition = subQuery+statusSubQuery;
		
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId")){
				query.setLong(str, (long) params.get( str ));
			}else{
				query.setParameter( str, params.get( str ) );
			}
        }
		
		List<RequirementSubmission> list = query.setCacheable(true).list() ;
		return list;
		return requirementSubmission;*/
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.sourceSubmission =:submissionId and rs.vendorLocationId =:orgLocationId";
	     List<RequirementSubmission> vendorsubmissionList = session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	   
	     if(vendorsubmissionList != null && vendorsubmissionList.size() >0){
				return vendorsubmissionList.get(0);
			}
	  return null;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getRequirementHistoryListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.candidate.id =:candidateId and rs.orgLocations =:orgLocationId";
	     List<RequirementSubmission> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	  return requirementHistoryList;
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


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementId(
			Long candidateId, Long orgLocationId, Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.candidate.id =:candidateId and rs.requirement.id =:requirementId and rs.orgLocations =:orgLocationId";
	     List<RequirementSubmission> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setCacheable(true).list();
	  return requirementHistoryList;
	}
    

	
	
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> duplicateCandidateCountList(Long requirementId, User user,
				OrgLocations loginOrgLocation) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			String subQuery = "";
			List<RequirementSubmission>  requirementSubmission = null;

		 		subQuery = " and reqSub.candidate.email IN(select tempReqSub.candidate.email from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id)";
				
		 		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
				
		 		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
				if(requirement.getManagerUserId() !=null){
					if (isadmin && requirement.getManagerUserId().getId().longValue() !=user.getId().longValue()) {
						String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId "+subQuery+" ";
						 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
					}	
				}
				if(requirement.getManagerUserId().getId() !=null){
					if(requirement.getManagerUserId().getId().longValue() == user.getId().longValue()){
						String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId "+subQuery+" ";
						requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
					}
				}
				
			return requirementSubmission;
		}


		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getApplicantsByStatus(Long orgLocationsId, User user,String status) {

			Session session = this.sessionFactory.getCurrentSession();
			String subQuery = "";
			String statusQuery ="";
			String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),orgLocationsId);
			List<RequirementSubmission> requirementSubmissionList = null;
			 Map<String, Object> params = new HashMap<String,Object>();
			
			 
			 String	 statusQuery1  ="";
		//This is for Requirement Manger 
			   String subManagerQuery1 = "reqSub.requirement.managerUserId.id  =:userId ";
				params.put("userId",user.getId());
				
			if(isadmin){
				 if("accepted".equalsIgnoreCase(status)){
					 statusQuery = "reqSub.submissionRateStatus = 'ACCEPTED' and reqSub.needApplicantPermit <>'N'  and reqSub.submissionRateStatus IS NOT NULL ";
				}else if("requestSent".equalsIgnoreCase(status)){
					statusQuery= "  reqSub.submissionRateStatus = 'REQUEST' and reqSub.needApplicantPermit <>'N' and reqSub.submissionRateStatus IS NOT NULL";
				}
				else if("REJECTED".equalsIgnoreCase(status)){
					statusQuery= "  reqSub.submissionRateStatus = 'REJECTED' and reqSub.needApplicantPermit <>'N' and reqSub.submissionRateStatus IS NOT NULL";
				}
				subQuery = " where reqSub.orgLocations.id =:loginOrgLocation  and  "   +statusQuery;
				params.put("loginOrgLocation",orgLocationsId);
				
				// This Condition is if The Requirement is Released by the login User
			}else{
			
				 if("accepted".equalsIgnoreCase(status)){
					 statusQuery = "reqSub.submissionRateStatus = 'ACCEPTED' and reqSub.needApplicantPermit <>'N'  and reqSub.submissionRateStatus IS NOT NULL ";
				}else if("requestSent".equalsIgnoreCase(status)){
					statusQuery= "  reqSub.submissionRateStatus = 'REQUEST' and reqSub.needApplicantPermit <>'N' and reqSub.submissionRateStatus IS NOT NULL";
				}
				else if("REJECTED".equalsIgnoreCase(status)){
					statusQuery= "  reqSub.submissionRateStatus = 'REJECTED' and reqSub.needApplicantPermit <>'N' and reqSub.submissionRateStatus IS NOT NULL";
				}
				subQuery = " where reqSub.orgLocations.id =:loginOrgLocation  and  reqSub.requirement.managerUserId.id  =:userId and "   +statusQuery;
				params.put("loginOrgLocation",orgLocationsId);
				params.put("userId",user.getId());
					
				
			}
			String whereCondition = subQuery;
			
			
			Query query = session.createQuery(from +" "+whereCondition+" ORDER BY reqSub.id DESC");
			for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("userId")){
					query.setLong(str, (long) params.get( str ));
				}else{
					query.setParameter( str, params.get( str ) );
				}
	        }
			
			List<RequirementSubmission> list = query.setCacheable(true).list() ;
			return list;
			//return requirementSubmissionList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Long> getSubmittedCandidateIdsByRequirementId(Long requirementId) throws Exception {
			
			logger.debug("In ModulesDaoImpl class getSubmittedCandidateIdsByRequirementId method");
			Session session = this.sessionFactory.getCurrentSession();
			
				 
			String query = "select reqSub.candidate.id from RequirementSubmission reqSub where reqSub.requirement.id = :requirementId ";
			List<Long> candidateIdsList =  session.createQuery(query.toString()).setLong("requirementId", requirementId)
										.setCacheable(true).list();
			
			return candidateIdsList;
			
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Integer getRequirementPerCandidateCount(Long candudateId,Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select count(*) from RequirementSubmission rs  where rs.candidate.id = :candudateId and rs.orgLocations.id = :orgLocationId";
		     List<Long> requirementPerCandidateCount = session.createQuery(query).setLong("candudateId", candudateId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			Integer count= 0;
			if(requirementPerCandidateCount != null && !requirementPerCandidateCount.isEmpty()){
					count = requirementPerCandidateCount.get(0).intValue();
			}
			return count;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Integer getNumberOfApplicantsForRequirement(Long orgLocationId, Long requirementId) throws Exception {
			 Session session = this.sessionFactory.getCurrentSession();
		        
		        
		    		String query ="select count(*) from RequirementSubmission rs where rs.orgLocations = :orgLocationsId and rs.requirement.id =:requirementId";
		    		
		    		List<Long> reqSubmsCount  = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationId).setLong("requirementId", requirementId).setCacheable(true).list();
		    		Integer count = 0;
					if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
							count = reqSubmsCount.get(0).intValue();
					}
					return count;
		}

		@Override
		@Transactional
		public RequirementSubmission getRequirementSubmissionByRequirementIdAndCandidateId(Long requirementId,
				Long candidateId) throws Exception {
			 Session session = this.sessionFactory.getCurrentSession();
		     String query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate "
		     		+ "where rs.requirement.id = :requirementId and rs.candidate.id =:candidateId ";
		     List<RequirementSubmission> submissionList = session.createQuery(query).setLong( "requirementId", requirementId).setLong( "candidateId", candidateId).setCacheable(true).list();
		   
		     if(submissionList != null && submissionList.size() >0){
					return submissionList.get(0);
				}
		  return null;
	}

		@Override
		@Transactional
		public RequirementSubmission validateRequirementByRecruiterCandidateEmail(String email, Long requirementId,
				Long recruiterUserId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<RequirementSubmission> submission = new ArrayList<RequirementSubmission>();
			logger.debug("validateRequirementByRecruiterCandidateEmail start");
			try{
				submission = session.createQuery("from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate "
						+ "left join fetch rs.sourceSubmission left join fetch rs.candidateResumes left join fetch rs.recruiterUserId "
						+ "where rs.candidate.email = :email and rs.requirement.id = :requirementId and rs.recruiterUserId.id = :recruiterUserId " )
						.setString( "email", email ).setLong("requirementId", requirementId ).setLong("recruiterUserId", recruiterUserId ).setCacheable(true).list();
				
				logger.debug("validateRequirementByRecruiterCandidateEmail end");
				if (submission.size() > 0) {
					return submission.get(0);
				} else {
					return null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmissionsCountByRecruiterIdAndRequirementId(Long requirementId, Long userId)
				throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select count(*) from RequirementSubmission rs  where rs.requirement.id = :requirementId and rs.recruiterUserId.id = :recruiterUserId ";
		     List<Long> requirementPerCandidateCount = session.createQuery(query).setLong("requirementId", requirementId).setLong("recruiterUserId", userId).setCacheable(true).list();
			Integer count= 0;
			if(requirementPerCandidateCount != null && !requirementPerCandidateCount.isEmpty()){
					count = requirementPerCandidateCount.get(0).intValue();
			}
			return count;
		}

		@Override
		@Transactional
		public Integer getProfessionalJobAppliedCount(Long candidateId, Date startDate,Date endDate) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where rs.candidate.id = :candidateId and (rs.createdDate BETWEEN :startDate and :endDate)";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("candidateId", candidateId)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getProfessionalJobSubmisssionsCountBySubmissionType(Long candidateId, String submissionStatus,
				Date startDate, Date endDate) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where rs.candidate.id = :candidateId and "
			 		+ "rs.submissionStatus = :submissionStatus and (rs.createdDate BETWEEN :startDate and :endDate)";
		     
		     List<Long> submissionsCount = session.createQuery(query).setLong("candidateId", candidateId)
		    		   .setString("submissionStatus", submissionStatus)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(submissionsCount != null && !submissionsCount.isEmpty()){
					count = submissionsCount.get(0).intValue();
			 }
			return count;
			
		}
		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public List<RequirementSubmission> getProfessionalJobSubmisssionsCountListBySubmissionType(Long candidateId,
				String submissionStatus, Date startDate, Date endDate) throws Exception {
				Session session = this.sessionFactory.getCurrentSession();
				String query;
				List<RequirementSubmission>  submissionsCountList = null ;
				if(null != submissionStatus && !"".equalsIgnoreCase(submissionStatus) && submissionStatus != "undefined"){
					 query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate where rs.candidate.id = :candidateId and rs.submissionStatus = :submissionStatus and (rs.createdDate BETWEEN :startDate and :endDate))";
					 submissionsCountList = session.createQuery(query).setLong("candidateId", candidateId) .setString("submissionStatus", submissionStatus)
				    		   .setDate("startDate", startDate) .setDate("endDate", endDate).setCacheable(true).list();
				}else{
					 query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate where rs.candidate.id = :candidateId and (rs.createdDate BETWEEN :startDate and :endDate))";
					 submissionsCountList = session.createQuery(query).setLong("candidateId", candidateId) .setDate("startDate", startDate)
							   .setDate("endDate", endDate).setCacheable(true).list();
				}
			  
			 return submissionsCountList;  
		}


		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public List<RequirementSubmission> getProfessionalJobAppliedCountList(Long candidateId, Date startDate, Date endDate) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementSubmission rs where rs.candidate.id = :candidateId and (rs.createdDate BETWEEN :startDate and :endDate))";
			List<RequirementSubmission> appliedCountList = session.createQuery(query).setLong("candidateId", candidateId) .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			return appliedCountList;
		}

		@Override
		@Transactional
		public Integer getProfessionalJobAppliedCountByappliedDate(Long candidateId, Date appliedDate)
				throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 
			 java.sql.Date ourJavaDateObject = new java.sql.Date(appliedDate.getTime());
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where rs.candidate.id = :candidateId and DATE(rs.createdDate) = :appliedDate ";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("candidateId", candidateId).setDate("appliedDate", ourJavaDateObject).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getReferByMeJobAppliedCount(Long userId, Date startDate, Date endDate) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where "
			 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
			 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate))";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("userId", userId)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		
		}

		@Override
		@Transactional
		public Integer getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(Long userId,
				String submissionStatus, Date startDate, Date endDate) throws Exception {
			
			
			Session session = this.sessionFactory.getCurrentSession();
		     
			String query = "select count(*) from RequirementSubmission rs where "
			 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
			 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
			 		+ "rs.submissionStatus = :submissionStatus";
		      
		     
		     List<Long> submissionsCount = session.createQuery(query).setLong("userId", userId)
		    		   .setString("submissionStatus", submissionStatus)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(submissionsCount != null && !submissionsCount.isEmpty()){
					count = submissionsCount.get(0).intValue();
			 }
			return count;
			
		}


		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public List<RequirementSubmission> getReferByMeProfessionalJobSubmisssionsCountListBySubmissionType(Long userId, String submissionStatus, Date startDate, Date endDate) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<RequirementSubmission>  submissionsCountList = null ;
			String query;
			if(null != submissionStatus && !"".equalsIgnoreCase(submissionStatus) && submissionStatus != "undefined"){
				 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate where "
				 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
				 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
				 		+ "rs.submissionStatus = :submissionStatus";
				   submissionsCountList = session.createQuery(query).setLong("userId", userId).setString("submissionStatus", submissionStatus)
			    		                                            .setDate("startDate", startDate).setDate("endDate", endDate).setCacheable(true).list();
			}else{
				 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate where "
				 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
				 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate))";
			     
				submissionsCountList = session.createQuery(query).setLong("userId", userId).setDate("startDate", startDate).setDate("endDate", endDate).setCacheable(true).list();
			}
			
			return submissionsCountList;
		}

		
		@Override
		@Transactional
		public Integer getReferByMeJobAppliedCountByappliedDate(Long userId, Date appliedDate) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 java.sql.Date ourJavaDateObject = new java.sql.Date(appliedDate.getTime());
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where "
			 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied') and "
			 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and DATE(rs.createdDate) = :appliedDate )";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("userId", userId)
		    		 .setDate("appliedDate", ourJavaDateObject).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		
		
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmittedJobCountByappliedDate(Long userId, Date appliedDate) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			 
			 java.sql.Date ourJavaDateObject = new java.sql.Date(appliedDate.getTime());
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId and DATE(rs.createdDate) = :appliedDate ";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("appliedDate", ourJavaDateObject).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getRequiterReferJobAppliedCount(Long userId, Date startDate, Date endDate)
				throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
		     
			String query = "select count(distinct rs.requirement) from RequirementSubmission rs where "
			 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
			 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) ";
		      
		     
		     List<Long> submissionsCount = session.createQuery(query).setLong("userId", userId)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(submissionsCount != null && !submissionsCount.isEmpty()){
					count = submissionsCount.get(0).intValue();
			 }
			return count;
			
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmittedJobCountByStartDateAndEndDate(Long userId, Date startDate, Date endDate)
				throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId and (rs.createdDate BETWEEN :startDate and :endDate) ";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmittedCandidatesCountByStage(Long userId, String submissionStatus, Date startDate,
				Date endDate) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 String query = "";
			 
			 if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
				 query = "select count(*) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId "
					 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and (rs.submissionStatus = 'OFFERMADE' OR  rs.submissionStatus = 'OFFERACCEPTED') ";
				 
				 List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
						   .setDate("endDate", endDate).setCacheable(true).list();
				 
				 Integer count= 0;
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;
				 
			 }else{
				 
				 query = "select count(*) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId "
					 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and rs.submissionStatus = :submissionStatus ";
				 
				 List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
						   .setDate("endDate", endDate).setString("submissionStatus", submissionStatus).setCacheable(true).list();
				 
				 Integer count= 0;
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;
				 
			 }
			
		}
		
		
		@Override
		@Transactional
		public List<RequirementSubmission> getAllVendorsIdbyorgLocationId(OrgLocations orgLocation,Date startDateUTC,Date endDateUTC) {
			Session session = this.sessionFactory.getCurrentSession();
			if(null != startDateUTC && null != endDateUTC ){
				String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.vendorLocationId <> null and rs.orgLocations.id = :orgLocation and (rs.createdDate BETWEEN :startDateUTC and :endDateUTC) group by rs.vendorLocationId.id";
				return  session.createQuery(query).setLong("orgLocation", orgLocation.getId()).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
			}
			else{
				String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.vendorLocationId <> null and rs.orgLocations.id = :orgLocation group by rs.vendorLocationId.id";
				return  session.createQuery(query).setLong("orgLocation", orgLocation.getId()).setCacheable(true).list();
			}
		}
		
		@Override
		@Transactional
		public Integer getReferByMeJobAppliedCandidateCount(Long userId, Date startDate, Date endDate)
				throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
		     
			 String query = "select count(*) from RequirementSubmission rs where "
			 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
			 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate))";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("userId", userId)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).setCacheable(true).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmittedCandidateCountByStartDateAndEndDate(Long userId, Date startDate,
				Date endDate) throws Exception { Session session = this.sessionFactory.getCurrentSession();
				 
				 String query = "select count(*) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId and (rs.createdDate BETWEEN :startDate and :endDate) ";
			     
			     List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
						   .setDate("endDate", endDate).setCacheable(true).list();
				 Integer count= 0;
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;
		}

		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public List<RequirementSubmission> getRecruiterSubmittedCandidatesCountListByStage(Long userId,
				String submissionStatus, Date startDate, Date endDate) throws Exception {
				 Session session = this.sessionFactory.getCurrentSession();
				 List<RequirementSubmission>  submissionsCountList = null ;
				 String query;
				 if(null != submissionStatus && !"".equalsIgnoreCase(submissionStatus) && submissionStatus != "undefined"){
					 
					 if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
						 
						 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.candidate left join fetch rs.requirement where rs.recruiterUserId.id = :recruiterUserId "
							 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED')  ";
						     submissionsCountList = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
									                                          .setDate("endDate", endDate).setCacheable(true).list();
						     
					 }else{
						
						 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.candidate left join fetch rs.requirement where rs.recruiterUserId.id = :recruiterUserId "
							 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and rs.submissionStatus = :submissionStatus ";
						     submissionsCountList = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
									                                          .setDate("endDate", endDate).setString("submissionStatus", submissionStatus).setCacheable(true).list();
					 }
					 
					 
				 }else{
					 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.candidate  left join fetch rs.requirement where rs.recruiterUserId.id = :recruiterUserId and (rs.createdDate BETWEEN :startDate and :endDate) ";
					 submissionsCountList = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
							                                           .setDate("endDate", endDate).setCacheable(true).list();
				 }
				 
				
				return submissionsCountList;
		}
		
		

		
		@Override
		@Transactional
		public RequirementSubmission getRequirementSubmissionByRequirementIdAndUserId(Long requirementId, Long userId)
				throws Exception {
			 
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.candidate.consultantUser user "
		     		+ "where rs.requirement.id = :requirementId and user.id =:userId ";
		     List<RequirementSubmission> submissionList = session.createQuery(query).setLong( "requirementId", requirementId).setLong( "userId", userId).setCacheable(true).list();
		   
		     if(submissionList != null && submissionList.size() >0){
					return submissionList.get(0);
				}
		  return null;
	}

		@Override
		@Transactional
		public List<RequirementSubmission> getRecruiterSubmittedCandidatesList(Long recruiterUserId, Long requirementId)
				throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 String subQuery = "";
			 
			 String query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.candidate.consultantUser user "
			     		+ " left join fetch rs.recruiterUserId recruiter where rs.requirement.id = :requirementId and recruiter.id =:recruiterUserId ";
			 
			 List<RequirementSubmission> submissionList  = session.createQuery(query).setLong("recruiterUserId", recruiterUserId) .setLong("requirementId", requirementId).setCacheable(true).list();
			return submissionList;
		}


		@Override
		@Transactional
		public List<RequirementSubmission> getRecruiterSubmittedCandidatesListForReportsByLocationIdAndStatus(
				Long loginOrgLocationId, String client,String filterDate,Date startDateUTC,Date endDateUTC) throws Exception {
			
		     Session session = this.sessionFactory.getCurrentSession();
		     Map<String, Object> params = new HashMap<String,Object>();
		     
		     String[] clients = null;
				if(null != client && !"".equals(client)){
					client = client.replaceAll("[\\\"\\[\\]]", ""); 
					clients = client.split(",");
				}
				List<Long> clientsList = new ArrayList<>();
				if(clients != null){
					for(int i=0;i<clients.length;i++){
						clientsList.add(Long.parseLong(clients[i]));
					}
				}
		     
			String dateSubQuery = "";
			if(null != startDateUTC && null != endDateUTC){
				 dateSubQuery = " and reqSub.createdDate >= :startDateUTC and reqSub.createdDate <= :endDateUTC";
				params.put("startDateUTC",startDateUTC);
				params.put("endDateUTC",endDateUTC);
			}
				
			/*String clientSubQuery = "";
			if(!clientsList.isEmpty() && null != clientsList){
				for(int i=0; i<clientsList.size(); i++){
					clientSubQuery = " and req.clientId.id =:"+clientsList.get(i)+"";
				}
			}*/
			   
				 String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
				 String subQuery = "";
			
				subQuery = " where reqSub.orgLocations.id =:loginOrgLocation  and reqSub.submissionStatus = 'SUBMIT_TO_CLIENT' ";
				params.put("loginOrgLocation",loginOrgLocationId);
			
			Query query = null;
			if(clientsList.isEmpty()){
				query = session.createQuery(from+" "+subQuery+" "+dateSubQuery+" ORDER BY reqSub.id DESC");
			}else{
				query = session.createQuery(from+" "+subQuery+" "+dateSubQuery+" and req.clientId.id IN (:clientList) ORDER BY reqSub.id DESC");
				params.put("clientList",clientsList);
			}
				
				
			
			for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("loginOrgLocationId") ){
					query.setLong(str, (long) params.get( str ));
				}
				else if(str.equalsIgnoreCase("clientList") && clientsList.size()>0){
					query.setParameterList("clientList", clientsList);
				}
				else{
					query.setParameter( str, params.get( str ) );
				}
	        }
			
			List<RequirementSubmission> ownApplicantsList = query.setCacheable(true).list() ;
			
			return ownApplicantsList;
		}
		
		
		@Override
		@Transactional
		public RequirementSubmission validateRecruiterSubmittedCandidateEmail(String email, Long requirementId,
				Long userId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<RequirementSubmission> submission = new ArrayList<RequirementSubmission>();
			logger.debug("findByEmail start");
			try{
				submission = session.createQuery("from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes left join fetch rs.recruiterUserId where rs.candidate.email = :email and rs.requirement.id = :requirementId and rs.recruiterUserId.id = :recruiterUserId " )
						.setString( "email", email ).setLong("requirementId", requirementId ).setLong("recruiterUserId", userId ).setCacheable(true).list();
				
				logger.debug("findByEmail end");
				if (submission.size() > 0) {
					return submission.get(0);
				} else {
					return null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getApplicantsListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
				List<Long> applicantsList, Date startUTCDate, Date endUTCDate) {
			Session session = this.sessionFactory.getCurrentSession();
			
			String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate where rs.orgLocations.id = :orgLocationId and rs.candidate.id IN(:candidateIds) and rs.id IN(select interview.submissionId.id from RequirementInterview interview where interview.startTime >= :startDateUTC and interview.endTime <= :endDateUTC) and (rs.submissionStatus ='INTERVIEW' or rs.submissionStatus = 'CLIENT_INTERVIEW')";
		 
		 List<RequirementSubmission> submissionList  = session.createQuery(query).setLong("orgLocationId", loginOrgLocations.getId()).setParameter("startDateUTC", startUTCDate).setParameter("endDateUTC", endUTCDate).setParameterList("candidateIds", applicantsList).setCacheable(true).list();
		 return submissionList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Integer getRequirementHistoryCountByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select count(*) from RequirementSubmission rs where rs.candidate.id =:candidateId and rs.orgLocations =:orgLocationId";
		     List<Long> requirementHistoryCount = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		     Integer count = 0;
				if(requirementHistoryCount != null && !requirementHistoryCount.isEmpty()){
						count = requirementHistoryCount.get(0).intValue();
				}
				return count;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public boolean isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(Long candidateId,
				Long requirementId) throws Exception {

			Session session = this.sessionFactory.getCurrentSession();
			boolean flag = false;
			String query ="from RequirementSubmission submission left join fetch submission.candidate left join fetch submission.candidateResumes where submission.candidate.id = :candidateId and submission.requirement.id = :requirementId  ";
			List<CandidateEducation> educationList = session.createQuery(query).setLong("candidateId", candidateId).setLong("requirementId", requirementId).setCacheable(true).list();
			if(educationList !=null && educationList.size() >0){
				flag = true;
				return flag;
			}else{
				return flag;
			}
		
			
			
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Long> getcandidateListBySubmissionId(List<Long> submissionsIds,Long orgLocationId) {
			
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select rs.candidate.id from RequirementSubmission rs where  rs.id IN (:submissionsIds) and rs.orgLocations =:orgLocationId";
		     List<Long> candidateList = session.createQuery(query).setParameterList("submissionsIds", submissionsIds).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		     Integer count = 0;
				if(candidateList != null && !candidateList.isEmpty()){
						count = candidateList.get(0).intValue();
				}
				return candidateList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Long> getSubmissionIdByCandidateId(List<Long> candidateIds, Long orgLocationId) {
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select rs.id from RequirementSubmission rs where  rs.candidate.id IN (:candidateIds) and rs.orgLocations =:orgLocationId";
		     List<Long> submissionIds = session.createQuery(query).setParameterList("candidateIds", candidateIds).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		     Integer count = 0;
				if(submissionIds != null && !submissionIds.isEmpty()){
						count = submissionIds.get(0).intValue();
				}
				return submissionIds;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getOfferAcceptedCandidatesListByOrgLocationId(Long orgLocationId) {
			 Session session = this.sessionFactory.getCurrentSession();
			 String query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate candidate "
			 		+ "left join fetch candidate.consultantUser consultantUser where consultantUser.id IS NULL and rs.orgLocations.id = :orgLocationId and rs.submissionStatus = 'OFFERACCEPTED'";
			 List<RequirementSubmission>  offerAcceptedCandidatesList = session.createQuery(query).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		     return offerAcceptedCandidatesList;
		}
		
		
		
		

        @Override
		@Transactional
		public Integer getConsolidatedReportsForMailingForIndividualUserForToday(User user,
				 OrgLocations loginOrgLocation,String submissionStatus,Requirement requirement) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 String submissionStages = "";
			 
				java.sql.Date sqlCurrentDate = null;
				Date currentDate =  commonUtil.currentDate();
				// for current Date Converting to Sql Date Format
				 if(currentDate != null){
						sqlCurrentDate = new java.sql.Date(currentDate.getTime());
						java.sql.Timestamp sq = new java.sql.Timestamp(currentDate.getTime());
				 }
				 
		     
	         if(submissionStatus.equalsIgnoreCase("APPLICANTS")){
	             submissionStages = "";
	         }else if(submissionStatus.equalsIgnoreCase("SHORTLISTED")){
	             submissionStages = " and (reqSub.submissionStatus = 'SHORTLIST')";
	         }else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
	             submissionStages = "and (reqSub.submissionStatus = 'SUBMIT_TO_CLIENT')";
	         }else if(submissionStatus.equalsIgnoreCase("INTERVIEWED")){
	             submissionStages = "and (reqSub.submissionStatus = 'INTERVIEW' OR reqSub.submissionStatus = 'CLIENT_INTERVIEW')";
	         }else if(submissionStatus.equalsIgnoreCase("REJECTED")){
	             submissionStages = "and (reqSub.submissionStatus = 'REJECTED')";
	         }else if(submissionStatus.equalsIgnoreCase("REQUESTSENT")){
	             submissionStages = "and (reqSub.submissionStatus = 'REQUEST_SENT')";
	         }else if(submissionStatus.equalsIgnoreCase("OFFERED")){
	             submissionStages = "and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' OR reqSub.submissionStatus = 'OFFERHIRED' OR "
	                     + " reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED' OR reqSub.submissionStatus = 'CLIENT_OFFERHIRED' )";
	         }
	     
	         else if(submissionStatus.equalsIgnoreCase("CLIENT_SUBMISSIONS")){
	             submissionStages = "and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' OR reqSub.submissionStatus = 'OFFERHIRED' OR "
	                     + " reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED' OR reqSub.submissionStatus = 'CLIENT_OFFERHIRED' )";
	         }
	         
             String query = "select reqSub.id from RequirementSubmission  reqSub  where reqSub.orgLocations.id =:orgLocations and reqSub.createdBy =:userCode  and reqSub.requirement.id =:requirememntId and  reqSub.createdDate >= :sqlCurrentDate  "+submissionStages+"";
  			 List<RequirementSubmission> submissionList  = session.createQuery(query)
  					                                              .setLong("orgLocations", loginOrgLocation.getId())
											  					  .setLong("requirememntId", requirement.getId())
											  					  .setString("userCode", user.getUserCode())
											  					  .setDate("sqlCurrentDate", sqlCurrentDate)
											  					  .setCacheable(true).list();
             return submissionList.size();
	            
		}

		@Override
		@Transactional
		public List<RequirementSubmission> getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(User user,
				OrgLocations orgLocations, String status, Requirement requirement) throws Exception {
			
			
			String[] releasedBy =null;
			
			ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
			
			if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
				releasedBy = requirement.getReleasedBy().split(",");
			}
			 boolean requirementReleased = false;
			 List<Long> releasedByUserIds = new ArrayList<Long>();
			 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			 for (String s : requirement.getReleasedBy().split(",")){
				 releasedByUserIds.add(Long.parseLong(s));
	    	 }
			 if(releasedByUserIds != null ){
				 requirementReleased = releasedByUserIds.contains(user.getId());
			 }
			 }
			     java.sql.Date sqlCurrentDate = null;
			     Date currentDate =  commonUtil.currentDate();
				// for current Date Converting to Sql Date Format
				 if(currentDate != null){
						sqlCurrentDate = new java.sql.Date(currentDate.getTime());
						java.sql.Timestamp sq = new java.sql.Timestamp(currentDate.getTime());
				 }
			 
			    Session session = this.sessionFactory.getCurrentSession();
			    Map<String, Object> params = new HashMap<String,Object>();
				String from = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes "; 
				String subQuery = "";
				
				
				// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
					if(null != releasedBy && releasedBy.length > 0){
						
						String subQueryForVendors = "where req.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
														params.put("userId",user.getId());
														params.put("loginOrgLocation",orgLocations.getId());
														params.put("requirementId",requirement.getId());
						
			            String whereCondition = subQueryForVendors;
						
						Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
						for (String str : queryForVendors.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
								queryForVendors.setLong(str, (long) params.get( str ));
							}else{
								queryForVendors.setParameter( str, params.get( str ) );
							}
				        }
						
						List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.setCacheable(true).list() ; 
						 List<Long> sourceSubmissionIds = new ArrayList<Long>();
						 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
							 for(RequirementSubmission rs :vendorSubmittedApplicants){
									sourceSubmissionIds.add(rs.getId());
								}
								String sourceSubmissionIdQuery = "from RequirementSubmission reqSub   where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  and  reqSub.createdDate >= :sqlCurrentDate "; 
								List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString())
																					.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
																					.setDate("sqlCurrentDate", sqlCurrentDate)
																					.setCacheable(true).list();
								finalList.addAll(finalSourceList);
								
						   }
						 

					}
			
					return	finalList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Long getCountOfAllApplicantsByCreatedUserAndOrglocationIdByStage(Long orgLocationId, User user,String stage,
				String timePeriod) throws Exception {
				String subQuery = "";
				if(stage == ""){
					subQuery = "";
				}else{
					subQuery = "rs.submissionStatus =:stage  and";
				}
			    String query ="select count(*)from RequirementSubmission rs where "+subQuery+"  rs.orgLocations.id =:orgLocationId and rs.createdBy =:userCode";
				Session session = this.sessionFactory.getCurrentSession();
				long count = 0;
				if(stage == ""){
					count = ((Long)session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("userCode",user.getUserCode()).uniqueResult()).intValue();
				}else{
					count = ((Long)session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("userCode",user.getUserCode()).setString("stage",stage).uniqueResult()).intValue();
				}
		       return count;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getApplicantCountSubmittedByVendor(Long orgLocationId, Long orgRelationId) {
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.orgLocations.id =:orgLocationId and rs.vendorLocationId.id =:orgRelationId and rs.sourceSubmission <> NULL";
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		    return subList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getVendorSpocSubmissions(String userCode, Long orgLocationId, Date startDateUTC, Date endDateUTC) {
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.orgLocations.id =:orgLocationId and rs.createdBy =:userCode and (rs.createdDate >= :startDateUTC and rs.createdDate <= :endDateUTC)";
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("userCode", userCode).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
		    return subList;
		}
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getSubmissionsForSOWByRequirementId(Long requirementId, String sowType,OrgLocations loginOrgLocation) {
			 Session session = this.sessionFactory.getCurrentSession();
			 String subQuery = "";
			 if(Constants.Client_SOW.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}else if(Constants.Vendor_SOW.equalsIgnoreCase(sowType) || Constants.FULL_TIME_OFFER.equalsIgnoreCase(sowType) ){
		 			subQuery = "and (rs.vendorLocationId <> null or ((rs.candidate.designationType = 'C2C' or rs.candidate.designationType = 'C2C-1099') and (rs.candidate.orgRelationId <> null ))) and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}
			 String leftJoins = "left join fetch rs.requirement req left join fetch rs.candidate left join fetch rs.candidate.orgRelationId left join fetch rs.sourceSubmission left join fetch rs.candidateResumes left join fetch rs.orgLocations left join fetch rs.vendorLocationId left join fetch rs.recruiterUserId ";
			 String query = "from RequirementSubmission rs "+leftJoins+" where req.id = :requirementId "+subQuery+" ORDER BY rs.id DESC";
			 List<RequirementSubmission>  offerAcceptedCandidatesList = session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		     return offerAcceptedCandidatesList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(Long orgLocationId,
				Long orgRelationId, Date startUTCDate, Date endUTCDate) {
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.orgLocations.id =:orgLocationId and rs.vendorLocationId.id =:orgRelationId and rs.sourceSubmission <> NULL and (rs.createdDate >= :startDateUTC and rs.createdDate <= :endDateUTC)";
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setParameter("startDateUTC", startUTCDate).setParameter("endDateUTC", endUTCDate).setCacheable(true).list();
		    return subList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public RequirementSubmission getRequirementSubmissionByCandidateIdAndRequirementId(Long candidateId, Long requirementId) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
			
		     String query = "from RequirementSubmission submission left join fetch submission.candidate left join fetch submission.candidateResumes where submission.candidate.id = :candidateId and submission.requirement.id = :requirementId ";
		     List<RequirementSubmission> requirementSubmissionList = session.createQuery(query).setLong( "candidateId", candidateId).setLong( "requirementId", requirementId).setCacheable(true).list();
		   
		     if(requirementSubmissionList != null && requirementSubmissionList.size() >0){
					return requirementSubmissionList.get(0);
				}else{
					return null;
				}
		}
		
		
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getApplicantRequirmentCountSubmittedByVendor(Long orgLocationId, Long requirementId, Long vendorLocationID)
		{
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.orgLocations.id =:orgLocationId and rs.vendorLocationId.id =:vendorLocationID and rs.requirement.id = :requirementId and rs.sourceSubmission <> NULL";
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setLong("vendorLocationID", vendorLocationID).setCacheable(true).list();
		    return subList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getSubmissionsForSOWByOrgLocationId(String sowType, OrgLocations loginOrgLocation)
		{
			 Session session = this.sessionFactory.getCurrentSession();
			 String subQuery = "";
			 if(Constants.Client_SOW.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}else if(Constants.Vendor_SOW.equalsIgnoreCase(sowType) || Constants.FULL_TIME_OFFER.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.vendorLocationId <> null or ((rs.candidate.designationType = 'C2C' or rs.candidate.designationType = 'C2C-1099') and (rs.candidate.orgRelationId <> null ))) and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}
			 String leftJoins = "left join fetch rs.requirement req left join fetch rs.candidate left join fetch rs.candidate.orgRelationId left join fetch rs.sourceSubmission left join fetch rs.candidateResumes left join fetch rs.orgLocations left join fetch rs.vendorLocationId left join fetch rs.recruiterUserId ";
			 String query = "from RequirementSubmission rs "+leftJoins+" where rs.orgLocations.id = :loginOrgLocationId "+subQuery+" ORDER BY rs.id DESC";
			 List<RequirementSubmission>  offerAcceptedCandidatesList = session.createQuery(query).setLong("loginOrgLocationId", loginOrgLocation.getId()).setCacheable(true).list();
		     return offerAcceptedCandidatesList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate)
		{
			Session session = this.sessionFactory.getCurrentSession();
	        
	        String submissionStages = "";
	        if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
				 submissionStages = "and rs.submissionStatus = 'SUBMISSION' ";
			}else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
				 submissionStages = "and rs.submissionStatus = 'SUBMIT_TO_CLIENT' ";
			}else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
				submissionStages = "and (rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'CLIENT_INTERVIEW') ";
			}else if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
				submissionStages = "and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' "
						+ " OR rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'CLIENT_OFFERREJECTED') ";
			}
	        
	        if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
	        	Map<String, Object> params = new HashMap<String,Object>();
	    		
	    		String from = "from RequirementSubmission rs";
	    		
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
	    				subQuery = "where rs.orgLocations.id = :orgLocationsId "+submissionStages+" and rs.status = 1 and rs.updatedDate >= :startDate and rs.updatedDate <= :endDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) ";
	    			}else if(dateRange != null && "previous".equals(dateRange)){
	    			    subQuery = "where rs.orgLocations.id = :orgLocationsId "+submissionStages+" and rs.status = 1 and rs.updatedDate < :startDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) ";
	    			}
	    			params.put("orgLocationsId",orgLocationsId);
	    			params.put("startDate",startUTCDate);
	    			params.put("endDate",endUTCDate);
	    			//params.put("submissionStatus",submissionStatus);
	    		}
	    		String whereCondition = subQuery;
	    		Query query =  session.createQuery(from+" "+whereCondition+" ");
	    		
	    		for (String str : query.getNamedParameters())
	            {
	    			if(str.equalsIgnoreCase("orgLocationsId")  || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
	    				query.setLong(str, (long) params.get( str ));
	    			}else{
	    				query.setParameter( str, params.get( str ) );
	    			}
	            	
	            }
	    		
	    		List<RequirementSubmission> reqSubms  = query.setCacheable(true).list();
				return reqSubms;
				
	        }else{
	        	List<RequirementSubmission> reqSubms = new ArrayList<>();
	        	String query = "";
	        	if(dateRange != null && "today".equals(dateRange)){
	                query ="from RequirementSubmission rs left join fetch rs.candidate left join fetch rs.candidateResumes where rs.orgLocations = :orgLocationsId "+submissionStages+" and rs.status = 1  and rs.updatedDate >= :startDate and rs.updatedDate <= :endDate";
	                reqSubms =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).setCacheable(true).list();
	     		}else if(dateRange != null && "previous".equals(dateRange)){
	     			query ="from RequirementSubmission rs left join fetch rs.candidate left join fetch rs.candidateResumes where rs.orgLocations = :orgLocationsId "+submissionStages+" and rs.status = 1  and rs.updatedDate < :startDate";
	     			reqSubms =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setCacheable(true).list();
	     		}
	        	
				return reqSubms;
	        	
	        }
			
		}
		
  }






