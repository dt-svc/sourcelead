package com.bis.sourcelead.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.AssociateJoineReportDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionsView;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.util.CommonUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

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
	
	@Autowired
	RequirementApproverHierarchyDao requirementApproverHierarchyDao;
	@Autowired
	OrgLocationService orgLocationService;
	
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
		String query = "from RequirementSubmission rs left join fetch rs.orgLocations left join fetch rs.requirement left join fetch rs.requirement.parentRequirement left join fetch rs.candidate candidate left join fetch candidate.consultantUser left join fetch rs.sourceSubmission left join fetch rs.candidateResumes   where rs.id = :submissionId";
		List<RequirementSubmission>  submission = session.createQuery(query).setLong( "submissionId", submissionId).list();
		logger.info("RequirementSubmission loaded successfully !!!");
		return submission.get(0);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getClientRequierementSubmission(Long submissionId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();	
		String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes   where rs.sourceSubmission.id = :submissionId";
		List<RequirementSubmission>  submission = session.createQuery(query).setLong( "submissionId", submissionId).list();
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
	public List<Object[]> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientInterview,User user,String loginUserRole) throws Exception {
		List<Object[]> objList = null;
		Calendar c1 = Calendar.getInstance(); 
		Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 if(isClientInterview != null && "Y".equalsIgnoreCase(isClientInterview)){
			 subQuery = " and (rs.submissionStatus = 'SUBMIT_TO_CLIENT' OR rs.submissionStatus = 'CLIENT_INTERVIEW' ) "; 
		 }else{
			 subQuery = " and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SELECTED_ON_INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' ) "; 
		 }
		 
		 String subQuery2 = "";
		 
		 Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,orgLocations.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery2 = " and ((rs.createdBy = '"+user.getUserCode()+"')  OR (rs.vendorLocationId IS NOT NULL AND rs.requirement.releasedBy = '"+user.getId().toString()+"') OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL) OR ('"+user.getId()+"' IN (SELECT rah.approverUserId.id FROM RequirementApproverHierarchy rah WHERE rah.requirementId.id = :requirementId))) ";
		 }
		 
		 
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "select rs.id, rs.createdUser, rs.requirement.isClientJob from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.id NOT IN (:existingApplicantsIds) "+subQuery+" "+subQuery2+" ";
			 objList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).list();
		 }else{
			 String testQuery = "select rs.id, rs.createdUser, rs.requirement.isClientJob from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId "+subQuery+" "+subQuery2+" ";
			 objList = session.createQuery(testQuery).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).list();

			 /*String query = "from RequirementSubmission rs left join fetch rs.requirement where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).list();*/
		 }
		 Calendar c2 = Calendar.getInstance();
		 System.out.println("Total Execution Time : " + (c2.getTimeInMillis() - c1.getTimeInMillis()));
	     return objList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer,User user,String loginUserRole,String isClientRequirement) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String subQuery = "";
		 if(isClientOffer != null && "Y".equalsIgnoreCase(isClientOffer)){
			 subQuery = " and (rs.submissionStatus = 'SUBMIT_TO_CLIENT' OR rs.submissionStatus = 'CLIENT_INTERVIEW' OR rs.submissionStatus = 'SELECTED_ON_INTERVIEW') "; 
		 }else{
			if(isClientRequirement.equalsIgnoreCase(Constants.NO)){
				 /*subQuery = " and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' OR "*/
				subQuery = " and (rs.submissionStatus = 'SELECTED_ON_INTERVIEW' OR "
					 		+ " rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'CLIENT_OFFERREJECTED' OR rs.submissionStatus = 'CLIENT_OFFERHIRED') ";
			}else{
				 subQuery = " and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' "
					 		+ "  OR rs.submissionStatus = 'CLIENT_OFFERREJECTED' OR rs.submissionStatus = 'CLIENT_OFFERHIRED' OR rs.submissionStatus = 'SELECTED_ON_INTERVIEW') ";
			}
			 
		 }
		 
		 String subQuery2 = "";
		 
		 Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		 Boolean teamFlag = teamDao.getTeamOwnersListByUserIdAndRequirementId(user,orgLocations.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false ) {
			 subQuery2 = " and ((rs.createdBy = '"+user.getUserCode()+"') OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL) OR ('"+user.getId()+"' IN (SELECT rah.approverUserId.id FROM RequirementApproverHierarchy rah WHERE rah.requirementId.id = :requirementId)))";
		 }
		 
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.id NOT IN (:existingApplicantsIds) "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).list();
		 }else{
			 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId "+subQuery+" "+subQuery2+" ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).list();
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
	public List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId,User user, OrgLocations loginOrgLocation,String loginUserRole) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		String[] releasedBy =null;
		
		ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
		
		if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			releasedBy = requirement.getReleasedBy().split(",");
		}
		
		
	    /*	
	     requirementReleased varaiable doesnt used any where
	     boolean requirementReleased = false;
		 List<Long> releasedByUserIds = new ArrayList<Long>();
		 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
		 for (String s : requirement.getReleasedBy().split(",")){
			 releasedByUserIds.add(Long.parseLong(s));
    	 }
		 if(releasedByUserIds != null ){
			 requirementReleased = releasedByUserIds.contains(user.getId());
		 }
		 }*/
		 
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
				
				List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
				 List<Long> sourceSubmissionIds = new ArrayList<Long>();
				 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
					 for(RequirementSubmission rs :vendorSubmittedApplicants){
							sourceSubmissionIds.add(rs.getId());
						}
						String sourceSubmissionIdQuery = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  "; 
						List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString())
																			.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
																			.list();
						finalList.addAll(finalSourceList);
						
				   }
				
			}
			
			/// These Conditions are for  own Organization  Submitted Applicants
			if(loginUserRole.equalsIgnoreCase("ROLE_ADMIN") || loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
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
				params.put("requirementUsercode",user.getId());
				
				// This is if the user is  created by 
				String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
				params.put("usercode",user.getUserCode());
				
				//  this is if the  user is   Assign Team  Owner
				String subQuery3 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
				params.put("userId",user.getId());
				
			/*   //  this is if the  user is   Assign Team  Member
				String subQuery4 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
				params.put("userId",user.getId());
				params.put("usercode",user.getUserCode());*/
				
				
				 //  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
				String subQuery5 = "req.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
				params.put("userId",user.getId());
				
				if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery5 != "" || subQuery6 !="" ){
					subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and req.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery6+")  OR ("+subQuery5+")   )";
					params.put("loginOrgLocation",loginOrgLocation.getId());
					params.put("requirementId",requirementId);
				}
			}
			
			String whereCondition = subQuery;
			
			Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
			for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
					query.setLong(str, (long) params.get( str ));
				}else{
					query.setParameter( str, params.get( str ) );
				}
	        }
			
			List<RequirementSubmission> ownApplicantsList = query.list() ;
		    finalList.addAll(ownApplicantsList);
		    List<RequirementSubmission> listDistinct = finalList.stream().distinct().collect(Collectors.toList());
		return listDistinct;
		
		/*
		
		//to get all applicates list if login user is manager or admin
		if (isadmin || requirement.getManagerUserId().getId().longValue() == user.getId().longValue() ) {
			String query = "from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch reqSub.candidate left "
					+ "join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes  "
					+ "where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ORDER BY reqSub.candidateScore DESC ";
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
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
			requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).list();

			  
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
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
		}else if(!isadmin && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && !flag && !teamFlag){
			String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and  reqSub.requirement.id  IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId) ORDER BY reqSub.candidateScore DESC ";
			requirementSubmission = session.createQuery(query).setLong("userId", user.getId()).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();

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
			 subQuery = " and ((rs.createdBy = '"+user.getUserCode()+"') OR (rs.vendorLocationId IS NOT NULL) OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL) OR ('"+user.getId()+"' IN (SELECT rah.approverUserId.id FROM RequirementApproverHierarchy rah WHERE rah.requirementId.id = :requirementId))) ";
		 }
		 
		 String submissionStages = "";
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
			 submissionStages = "and (rs.submissionStatus = 'SUBMISSION' OR rs.submissionStatus = 'SELECTED_ON_INTERVIEW' OR rs.submissionStatus = 'INTERVIEW' ) and (rs.id NOT IN(SELECT sh.submissionId.id FROM RequirementSubmissionHistory sh WHERE sh.toStage = 'SHORTLIST' and sh.submissionId.orgLocations.id = :orgLocationsId and sh.submissionId.requirement.id = :requirementId )) ";
		 }
		 
		 else if(submissionStatus.equalsIgnoreCase("SHORTLIST")){
			 submissionStages = "and rs.submissionStatus <> 'SUBMISSION' and rs.submissionStatus <> 'SUBMIT_TO_CLIENT' ";
		 }
		 
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId  "+submissionStages+" "+subQuery+" ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", loginOrgLocation.getId()).setLong( "requirementId", requirementId).list();
	     return shortListCandidatesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus, String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate) {
		
        Session session = this.sessionFactory.getCurrentSession();
        
        String submissionStages = "";
        if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
        	submissionStages = " and rsh.toStage = 'SUBMISSION'";
		}else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
			submissionStages = " and rsh.toStage = 'SUBMIT_TO_CLIENT'";
		}else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
			submissionStages = " and (rsh.toStage = 'INTERVIEW' or rsh.toStage = 'CLIENT_INTERVIEW')";
		}else if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
			submissionStages = "and (rsh.toStage = 'OFFERMADE' OR rsh.toStage = 'OFFERACCEPTED' OR rsh.toStage = 'OFFERREJECTED' "
					+ " OR rsh.toStage = 'CLIENT_OFFERMADE' OR rsh.toStage = 'CLIENT_OFFERACCEPTED' OR rsh.toStage = 'CLIENT_OFFERREJECTED') ";
		}
        
        if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
        	Map<String, Object> params = new HashMap<String,Object>();
    		String from = " from RequirementSubmissionHistory rsh  ";
    		//login user as created by
    		String subQuery1 = "rsh.submissionId.requirement.id IN (select req.id from Requirement req where req.createdBy =:userId)";
    		params.put("userId",user.getId());
    		
    		//login user as a approver for this requirement  or  assigned to.
    		String subQuery2 = "rsh.submissionId.requirement.id IN (select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId )  "
    				+ "or (rsh.submissionId.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) ) "
    				+ "or (rsh.submissionId.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and rsh.submissionId.createdBy =:usercode)";
    		params.put("approverUserId",user.getId());
    		params.put("userId",user.getId());
    		params.put("usercode",user.getUserCode());
    		
    		//login user as a  manager for this job.
    		String subQuery3 = "rsh.submissionId.requirement.id IN ( select requirement.id from Requirement requirement where  requirement.managerUserId.id = :managerUserId )  ";
    		params.put("managerUserId",user.getId());
    		
    		String subQuery4 = " rsh.submissionId.requirement.releaseJobToAllUsers = 'Y' and  rsh.submissionId.createdBy =:usercode";
    		params.put("usercode",user.getUserCode());
    		
    		String subQuery = "";
    		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" ){
    			if(dateRange != null && "today".equals(dateRange)){
    				subQuery = "where rsh.submissionId.orgLocations.id = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") or ("+subQuery4+") ) ";
    			}else if(dateRange != null && "previous".equals(dateRange)){
    			    subQuery = "where rsh.submissionId.orgLocations.id = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.updatedDate < :startDate and rsh.submissionId.requirement IS NOT NULL and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") or ("+subQuery4+") ) ";
    			}
    			params.put("orgLocationsId",orgLocationsId);
    			params.put("startDate",startUTCDate);
    			params.put("endDate",endUTCDate);
    		}
    		String whereCondition = subQuery;
    		Query query =  session.createQuery("select distinct rsh.submissionId "+ from +" "+whereCondition+" ");
    		
    		for (String str : query.getNamedParameters())
            {
    			if(str.equalsIgnoreCase("orgLocationsId")  || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
    				query.setLong(str, (long) params.get( str ));
    			}else{
    				query.setParameter( str, params.get( str ) );
    			}
            }
    		List<Long> reqSubmsCount  = query.list();
    		Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.size();
			}
			return count;
			
        }else{
        	List<Long> reqSubmsCount = null;
        	String query = "";
        	if(dateRange != null && "today".equals(dateRange)){
                query ="select distinct rsh.submissionId from RequirementSubmissionHistory rsh  where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate";
                 reqSubmsCount =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).list();
     		}else if(dateRange != null && "previous".equals(dateRange)){
     			query ="select distinct rsh.submissionId from RequirementSubmissionHistory rsh  where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate < :startDate";
     			 reqSubmsCount =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).list();
     		}
			Integer count = 0;
			if(reqSubmsCount != null && !reqSubmsCount.isEmpty()){
					count = reqSubmsCount.size();
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
    		
    		List<Long> reqSubmsCount  = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocations.getId()).setLong("requirementId", requirementId).list();
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
		 Boolean approverFlag = requirementApproverHierarchyDao.getisRequirementApprover(user.getId(),requirement.getId());
		 if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && teamFlag == false && approverFlag == false ) {
			 subQuery = " and ((rs.requirement.primaryRecruiterUserId.id = '"+user.getId()+"') OR (rs.createdBy = '"+user.getUserCode()+"') OR (rs.vendorLocationId IS NOT NULL) OR (rs.candidate.candidateType = 'Professional') OR (rs.recruiterUserId IS NOT NULL)) ";
		 }
		 
		 
		 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId "
		 		+ " and rs.requirement.id = :requirementId "+subQuery+" and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SELECTED_ON_INTERVIEW' OR rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERHIRED' ) "
		 				+ " and rs.id NOT IN (select submission.sourceSubmission from RequirementSubmission submission) ";
		 List<RequirementSubmission>  shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", loginOrgLocation.getId()).setLong( "requirementId", requirement.getId()).list();
	     return shortListCandidatesList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getRequirementPerCandidate(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.vendorLocationId left join fetch rs.orgLocations left join fetch rs.candidateResumes left join fetch rs.sourceSubmission left join fetch rs.candidate where rs.candidate.id = :candidateId";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong("candidateId", candidateId).list();
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
					.setString( "email", email ).setLong("requirementId", requirementId ).setLong("orgLocationsId", orgLocationsId ).list();
			
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
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "sourceSubmissionId", sourceSubmissionId).setLong( "orgLocationsId", orgLocationsId).list();
	   
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
	    List<Long> resumeIds = session.createQuery(query).setParameterList( "submissionIds", submissionIds).list();
        return resumeIds;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,
			String submissionStatus) throws Exception {
		
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.id = :submissionId and rs.submissionStatus = :submissionStatus ";
		List<RequirementSubmission> requirementSubmissionsList =  session.createQuery(query.toString()).setLong("submissionId", submissionId).setString("submissionStatus", submissionStatus).list();
		
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
			 submissionStages = "and (rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'CLIENT_INTERVIEW' OR rs.submissionStatus = 'SELECTED_ON_INTERVIEW')";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("OFFERED")){
			 submissionStages = "and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED' OR rs.submissionStatus = 'OFFERREJECTED' OR rs.submissionStatus = 'OFFERHIRED' OR "
			 		+ " rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'CLIENT_OFFERREJECTED' OR rs.submissionStatus = 'CLIENT_OFFERHIRED' )";
		 }
		 
		 if(submissionStatus.equalsIgnoreCase("JOINED")){
			 submissionStages = "and (rs.submissionStatus = 'JOINED')";
		 }
		 
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId  "+submissionStages+" ";
	     List<Long> candidatesCount = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).list();
	     Integer count = 0;
			if(candidatesCount != null && !candidatesCount.isEmpty()){
					count = candidatesCount.size();
			}
			return count;
	}
	
	
	@Override
    @Transactional(rollbackFor = { Exception.class })
    public RequirementSubmissionsCountDTO getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long requirementId, User user, OrgLocations loginOrgLocation, String submissionStatus) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
        
         Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
         String[] releasedBy =null;
         
         if(requirement != null && !"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
             releasedBy = requirement.getReleasedBy().split(",");
         }
          boolean requirementReleased = false;
          List<Long> releasedByUserIds = new ArrayList<Long>();
          if(requirement != null && !"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
          for (String s : requirement.getReleasedBy().split(",")){
              releasedByUserIds.add(Long.parseLong(s));
          }
          if(releasedByUserIds != null ){
              requirementReleased = releasedByUserIds.contains(user.getId());
          }
          }
         Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(user.getId(),loginOrgLocation.getId());
         Map<String, Object> params = new HashMap<String,Object>();
         
         List<String> statusList = new ArrayList<>();
         if(submissionStatus.equalsIgnoreCase("ALL")){
        	 statusList.add("APPLICANTS");
        	 statusList.add("SHORTLISTED");
        	 statusList.add("SUBMIT_TO_CLIENT");
        	 statusList.add("INTERVIEWED");
        	 statusList.add("REJECTED");
        	 statusList.add("REQUESTSENT");
        	 statusList.add("BACKOUT");
        	 statusList.add("OFFERED");
        	 statusList.add("JOINED");
        	 statusList.add("ONHOLD");
        	 statusList.add("NORESPONSE");
        	 
         }else {
        	 statusList.add(submissionStatus);
         }
         RequirementSubmissionsCountDTO rsc = new RequirementSubmissionsCountDTO();
         for(String ss : statusList) {
        	String submissionStages = "";
             if(ss.equalsIgnoreCase("APPLICANTS")){
                 submissionStages = "";
             }else if(ss.equalsIgnoreCase("SHORTLISTED")){
                 submissionStages = "and (reqSub.submissionStatus = 'SHORTLIST')";
             }else if(ss.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
                 submissionStages = "and (reqSub.submissionStatus = 'SUBMIT_TO_CLIENT')";
             }else if(ss.equalsIgnoreCase("INTERVIEWED")){
                 submissionStages = "and (reqSub.submissionStatus = 'INTERVIEW' OR reqSub.submissionStatus = 'CLIENT_INTERVIEW' OR reqSub.submissionStatus = 'SELECTED_ON_INTERVIEW' OR reqSub.submissionStatus = 'INTERVIEW_ACCEPT')";
             }else if(ss.equalsIgnoreCase("REJECTED")){
                 submissionStages = "and (reqSub.submissionStatus = 'REJECTED')";
             }else if(ss.equalsIgnoreCase("REQUESTSENT")){
                 submissionStages = "and (reqSub.submissionStatus = 'REQUEST_SENT')";
             }else if(ss.equalsIgnoreCase("BACKOUT")){
                 submissionStages = "and (reqSub.submissionStatus = 'BACKOUT')";
             }else if(ss.equalsIgnoreCase("OFFERED")){
                 submissionStages = "and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' OR reqSub.submissionStatus = 'OFFERHIRED' OR "
                         + " reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED' OR reqSub.submissionStatus = 'CLIENT_OFFERHIRED' )";
             }else if(ss.equalsIgnoreCase("JOINED")){
                 submissionStages = "and (reqSub.submissionStatus = 'JOINED')";
             }
             else if(ss.equalsIgnoreCase("ONHOLD")){
                 submissionStages = "and (reqSub.submissionStatus = 'ONHOLD')";
             }
             else if(ss.equalsIgnoreCase("NORESPONSE")){
                 submissionStages = "and (reqSub.submissionStatus = 'NORESPONSE')";
             }
             
             ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
             List<RequirementSubmission> listDistinct = null;
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
                        
                        List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ;
                         List<Long> sourceSubmissionIds = new ArrayList<Long>();
                         if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
                             for(RequirementSubmission rs :vendorSubmittedApplicants){
                                    sourceSubmissionIds.add(rs.getId());
                                }
                                String sourceSubmissionIdQuery = "from RequirementSubmission reqSub  where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)   " +submissionStages;
                                List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString())
                                                                                    .setParameterList("sourceSubmissionIds", sourceSubmissionIds)
                                                                                    .list();
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
                        String subQuery1 = "(reqSub.requirement.managerUserId.id  =:userId or reqSub.requirement.primaryRecruiterUserId.id = :userId)  ";
                        params.put("userId",user.getId());
                        
                        // This is if the user is  created by
                        String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
                        params.put("usercode",user.getUserCode());
                        
                        //  this is if the  user is   Assign Team  Owner
                       String subQuery3 = "reqSub.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.requirementId.id=:requirementId and rah.teamId.id IN ( select team.id from Team team where team.ownerUserId.id =:userId))";
                        params.put("userId",user.getId());
                        
                       //  this is if the  user is   Assign Team  Member
                        String subQuery4 = "reqSub.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
                        params.put("userId",user.getId());
                        params.put("usercode",user.getUserCode());
                        
                        
                        //  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
        				String subQuery5 = "reqSub.requirement.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
        				params.put("userId",user.getId());
        				
        				
                        
                        if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != ""  || subQuery5 != ""  ){
                            subQuery = "where reqSub.orgLocations.id =:loginOrgLocation  "+submissionStages+" and reqSub.requirement.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+")  OR ("+subQuery4+") OR ("+subQuery5+")  )";
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
                    
                    List<RequirementSubmission> ownApplicantsList = query.list() ;
                    finalList.addAll(ownApplicantsList);
                    listDistinct = finalList.stream().distinct().collect(Collectors.toList());
                    
                    
                    switch (ss) 
                    {
                        case "APPLICANTS":  rsc.setApplicantsCount(listDistinct.size());
                                 break;
                        case "SHORTLISTED":  rsc.setShortlistCount(listDistinct.size());
                                 break;
                        case "SUBMIT_TO_CLIENT":  rsc.setSubmitToClientCount(listDistinct.size());
                                 break;
                        case "INTERVIEWED":  rsc.setInterviewCount(listDistinct.size());
                                 break;
                        case "REJECTED":  rsc.setRejectedCount(listDistinct.size());
                                 break;
                        case "REQUESTSENT":  rsc.setRequestSent(listDistinct.size());
                                 break;
                        case "BACKOUT":  rsc.setBackoutCount(listDistinct.size());
                                 break;
                        case "OFFERED":  rsc.setOffersCount(listDistinct.size());
                        		break;
                        case "JOINED":  rsc.setJoinedCount(listDistinct.size());
                        		break;
                        case "ONHOLD":  rsc.setOnHoldCount(listDistinct.size());
                		break;
                        case "NORESPONSE":  rsc.setNoResponseCount(listDistinct.size());
                		break;
                    }
        	 
         }
         return rsc;
    }
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer duplicateCandidateCountsByRequirementId(Long requirementId, User user,
			OrgLocations loginOrgLocation,CandidateVerificationConfig candidateVerificationConfig) throws Exception {
		  Session session = this.sessionFactory.getCurrentSession();
//		    List<Long> candidatesCount = null;
		  String statusSubQuery = "";
			String statusSubQuery1 = "";
			String statusSubQuery2 = "";
			String statusSubQuery3 = "";
			String statusSubQuery4 = "";

		   statusSubQuery = " reqSub.candidate.email IN(select tempReqSub.candidate.email from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.email<>'') and reqSub.submissionStatus <> 'REJECTED'";
		  if(candidateVerificationConfig != null) {
		  if(candidateVerificationConfig.getAadharNumber() == true){
			 	statusSubQuery1 = " OR reqSub.candidate.aadharNumber IN(select tempReqSub.candidate.aadharNumber from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.aadharNumber<>'')";
			}
		   if(candidateVerificationConfig.getPanNumber() == true){
					statusSubQuery2 = " OR reqSub.candidate.panNumber IN(select tempReqSub.candidate.panNumber from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.panNumber<>'')";
			}
			if(candidateVerificationConfig.getMobilePhone()== true){
			 statusSubQuery3 = " OR reqSub.candidate.mobilePhone IN(select tempReqSub.candidate.mobilePhone from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.mobilePhone<>'')";
			}
			if(candidateVerificationConfig.getSsnNumber()== true){
				 statusSubQuery4 = " OR reqSub.candidate.ssnNumber IN(select tempReqSub.candidate.ssnNumber from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.ssnNumber<>'')";
			}
		  }
			statusSubQuery = "and reqSub.submissionStatus <> 'REJECTED' and ("+statusSubQuery+statusSubQuery1+statusSubQuery2+statusSubQuery3+statusSubQuery4+")";
		  
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
					
					List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
					 List<Long> sourceSubmissionIds = new ArrayList<Long>();
					 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
						 for(RequirementSubmission rs :vendorSubmittedApplicants){
								sourceSubmissionIds.add(rs.getId());
							}
							String sourceSubmissionIdQuery = "from RequirementSubmission reqSub left join fetch reqSub.requirement req   where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  " +statusSubQuery; 
							List<RequirementSubmission> finalSourceList = null;
																				
										finalSourceList =		session.createQuery(sourceSubmissionIdQuery.toString())
										.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
										.setLong("requirementId",requirementId).list();
										
																				
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
					params.put("requirementUsercode",user.getId());
					
					
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
					if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
						query.setLong(str, (long) params.get( str ));
					}else{
						query.setParameter( str, params.get( str ) );
					}
		        }
				
				List<RequirementSubmission> ownApplicantsList = query.list() ;
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
				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
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
				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).list();
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
//				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
//			}
			//to get applicates list of the team member 
			if(requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && flag && !teamFlag){
				String query = "select count(*) from RequirementSubmission reqSub left join fetch reqSub.requirement left join fetch "
						+ "reqSub.candidate left join fetch reqSub.sourceSubmission "
						+ "left join fetch reqSub.candidateResumes where (reqSub.orgLocations.id =:loginOrgLocation or "
						+ "reqSub.sourceSubmission.id =:loginOrgLocation) and reqSub.createdBy =:usercode and reqSub.requirement.id =:requirementId "+subQuery+" ORDER BY reqSub.id DESC";
				candidatesCount = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
			}else if(!isadmin && requirement.getManagerUserId().getId().longValue() != user.getId().longValue() && !flag && !teamFlag){
				String query = "select count(*) from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and  reqSub.requirement.id  IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId) "+subQuery+" ORDER BY reqSub.candidateScore DESC ";
				candidatesCount = session.createQuery(query).setLong("userId", user.getId()).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();

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
		     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "vendorSubmissionId", vendorSubmissionId).setLong( "vendororgLocationsId", vendororgLocationsId).list();
		   
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
		
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.id = :clientSubmissionId and rs.orgLocations.id =:clientOrgLocationId ";
	     List<RequirementSubmission> vendorsubmissionList = session.createQuery(query).setLong( "clientSubmissionId", clientSubmissionId).setLong( "clientOrgLocationId", clientOrgLocationId).list();
	   
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
		List<RequirementRateSubmissionNegotiation>  submission = session.createQuery(query).setLong( "submissionId", submissionId).list();
		logger.info("RequirementRateSubmission loaded successfully !!!");
		return submission.get(0);
		
	}
		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType,String loginUserRole) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String statusSubQuery = "";
		if(submissionType != null && "INTERVIEW".equalsIgnoreCase(submissionType)){
			statusSubQuery = " and (reqSub.submissionStatus = 'INTERVIEW' OR reqSub.submissionStatus = 'CLIENT_INTERVIEW') "; 
		}else if(submissionType != null && "OFFERMADE".equalsIgnoreCase(submissionType)){
			statusSubQuery = " and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' "
					+ " OR reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED') "; 
		}
		else if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(submissionType)){
			statusSubQuery = " and reqSub.candidate.email IN(select tempReqSub.candidate.email from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id) and reqSub.submissionStatus <> 'REJECTED'";
			//subQuery = " and reqSub.submissionStatus = '"+submissionType+"' ";
		}
		else{
			statusSubQuery = " and reqSub.submissionStatus = '"+submissionType+"' ";
		}
		
		Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		String[] releasedBy =null;
		
		ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
		
		if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
			releasedBy = requirement.getReleasedBy().split(",");
		}
		
      /* requirementReleased doesnt used any where
		 boolean requirementReleased = false;
		 List<Long> releasedByUserIds = new ArrayList<Long>();
		 if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
		 for (String s : requirement.getReleasedBy().split(",")){
			 releasedByUserIds.add(Long.parseLong(s));
    	 }
		 if(releasedByUserIds != null ){
			 requirementReleased = releasedByUserIds.contains(user.getId());
		 }
		 }*/
		 
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
				
				List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
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
									.setLong("requirementId",requirementId).list();
									
								}else{
									finalSourceList =	session.createQuery(sourceSubmissionIdQuery.toString())
									.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
									.list();
								}
																			
						finalList.addAll(finalSourceList);
						
				   }
				
			}
			
			/// These Conditions are for  own Organization  Submitted Applicants
			if(loginUserRole.equalsIgnoreCase("ROLE_ADMIN") || loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
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
				params.put("requirementUsercode",user.getId());
				
				// This is if the user is  created by 
				String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
				params.put("usercode",user.getUserCode());
				
				//  this is if the  user is   Assign Team  Owner
				String subQuery3 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId))";
				params.put("userId",user.getId());
				
			   //  this is if the  user is   Assign Team  Member
			/*	String subQuery4 = "req.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and reqSub.createdBy =:usercode";
				params.put("userId",user.getId());
				params.put("usercode",user.getUserCode());*/
				
				
			//  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
				String subQuery5 = "req.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
				params.put("userId",user.getId());
				
				if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery5 != "" || subQuery6 !=""  ){
					subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and req.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery3+")  OR ("+subQuery5+")    )";
					params.put("loginOrgLocation",loginOrgLocation.getId());
					params.put("requirementId",requirementId);
				}
			}
			
			String whereCondition = subQuery+statusSubQuery;
			
			Query query = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
			for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
					query.setLong(str, (long) params.get( str ));
				}else{
					query.setParameter( str, params.get( str ) );
				}
	        }
			
			List<RequirementSubmission> ownApplicantsList = query.list() ;
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
			 requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
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
			requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setParameterList("usercode", userCodes).setLong("requirementId", requirementId).list();

			  
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
		  requirementSubmission = session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setString("usercode", user.getUserCode()).setLong("requirementId", requirementId).list();
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
		
		List<RequirementSubmission> list = query.list() ;
		return list;
		return requirementSubmission;*/
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.sourceSubmission =:submissionId and rs.vendorLocationId =:orgLocationId";
	     List<RequirementSubmission> vendorsubmissionList = session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationId", orgLocationId).list();
	   
	     if(vendorsubmissionList != null && vendorsubmissionList.size() >0){
				return vendorsubmissionList.get(0);
			}
	  return null;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getRequirementHistoryListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.candidate.id =:candidateId and rs.orgLocations =:orgLocationId and rs.requirement IS NOT NULL ";
	     List<RequirementSubmission> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
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
	     List<RequirementSubmission> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).list();
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
				subQuery = " where reqSub.orgLocations.id =:loginOrgLocation  and  (reqSub.requirement.managerUserId.id  =:userId or reqSub.createdUser =:userCreatedBy) and "   +statusQuery;
				params.put("loginOrgLocation",orgLocationsId);
				params.put("userId",user.getId());
				if(user.getLastName() != null && user.getLastName() !=""){
				params.put("userCreatedBy",user.getFirstName() +" "+user.getLastName());
				}else{
				params.put("userCreatedBy",user.getFirstName());	
				}
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
			
			List<RequirementSubmission> list = query.list() ;
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
										.list();
			
			return candidateIdsList;
			
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Integer getRequirementPerCandidateCount(Long candudateId,Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select count(*) from RequirementSubmission rs  where rs.candidate.id = :candudateId and rs.orgLocations.id = :orgLocationId";
		     List<Long> requirementPerCandidateCount = session.createQuery(query).setLong("candudateId", candudateId).setLong("orgLocationId", orgLocationId).list();
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
		    		
		    		List<Long> reqSubmsCount  = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationId).setLong("requirementId", requirementId).list();
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
		     List<RequirementSubmission> submissionList = session.createQuery(query).setLong( "requirementId", requirementId).setLong( "candidateId", candidateId).list();
		   
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
						.setString( "email", email ).setLong("requirementId", requirementId ).setLong("recruiterUserId", recruiterUserId ).list();
				
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
		     List<Long> requirementPerCandidateCount = session.createQuery(query).setLong("requirementId", requirementId).setLong("recruiterUserId", userId).list();
			Integer count= 0;
			if(requirementPerCandidateCount != null && !requirementPerCandidateCount.isEmpty()){
					count = requirementPerCandidateCount.get(0).intValue();
			}
			return count;
		}

		@Override
		@Transactional
		public Integer getProfessionalJobAppliedCount(Long candidateId, Date startDateFormat,Date endDateFormat) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
		     
			 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
			 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
			 
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where (rs.candidate.id = :candidateId OR (rs.vendorCandidateId.id = :candidateId AND rs.vendorCandidateId IS NOT NULL)) and "
			 		+ "(rs.createdDate BETWEEN :startDate and :endDate) and rs.sourceSubmission IS NULL";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("candidateId", candidateId)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getProfessionalJobSubmisssionsCountBySubmissionType(Long candidateId, String submissionStatus,
				Date startDateFormat, Date endDateFormat) throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
		     
			 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
			 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
			 
			 
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where (rs.candidate.id = :candidateId OR (rs.vendorCandidateId.id = :candidateId AND rs.vendorCandidateId IS NOT NULL)) and "
			 		+ "rs.submissionStatus = :submissionStatus and (rs.createdDate BETWEEN :startDate and :endDate)";
		     
		     List<Long> submissionsCount = session.createQuery(query).setLong("candidateId", candidateId)
		    		   .setString("submissionStatus", submissionStatus)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).list();
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
					 query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate where (rs.candidate.id = :candidateId OR (rs.vendorCandidateId.id = :candidateId AND rs.vendorCandidateId IS NOT NULL)) and rs.submissionStatus = :submissionStatus and rs.sourceSubmission IS NULL and (rs.createdDate BETWEEN :startDate and :endDate))";
					 submissionsCountList = session.createQuery(query).setLong("candidateId", candidateId) .setString("submissionStatus", submissionStatus)
				    		   .setDate("startDate", startDate) .setDate("endDate", endDate).list();
				}else{
					 query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate where (rs.candidate.id = :candidateId OR (rs.vendorCandidateId.id = :candidateId AND rs.vendorCandidateId IS NOT NULL)) and (rs.createdDate BETWEEN :startDate and :endDate) and rs.sourceSubmission IS NULL)";
					 submissionsCountList = session.createQuery(query).setLong("candidateId", candidateId) .setDate("startDate", startDate)
							   .setDate("endDate", endDate).list();
				}
			  
			 return submissionsCountList;  
		}


		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public List<RequirementSubmission> getProfessionalJobAppliedCountList(Long candidateId, Date startDate, Date endDate) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementSubmission rs where (rs.candidate.id = :candidateId OR (rs.vendorCandidateId.id = :candidateId AND rs.vendorCandidateId IS NOT NULL)) and (rs.createdDate BETWEEN :startDate and :endDate))";
			List<RequirementSubmission> appliedCountList = session.createQuery(query).setLong("candidateId", candidateId) .setDate("startDate", startDate)
					   .setDate("endDate", endDate).list();
			return appliedCountList;
		}

		@Override
		@Transactional
		public Integer getProfessionalJobAppliedCountByappliedDate(Long candidateId, Date appliedDate)
				throws Exception {
			
			 Session session = this.sessionFactory.getCurrentSession();
			 
			 java.sql.Date ourJavaDateObject = new java.sql.Date(appliedDate.getTime());
		     
			 String query = "select count(distinct rs.requirement) from RequirementSubmission rs where (rs.candidate.id = :candidateId OR (rs.vendorCandidateId.id = :candidateId AND rs.vendorCandidateId IS NOT NULL)) and DATE(rs.createdDate) = :appliedDate and rs.sourceSubmission IS NULL ";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("candidateId", candidateId).setDate("appliedDate", ourJavaDateObject).list();
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
					   .setDate("endDate", endDate).list();
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
					   .setDate("endDate", endDate).list();
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
			    		                                            .setDate("startDate", startDate).setDate("endDate", endDate).list();
			}else{
				 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate where "
				 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
				 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate))";
			     
				submissionsCountList = session.createQuery(query).setLong("userId", userId).setDate("startDate", startDate).setDate("endDate", endDate).list();
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
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("appliedDate", ourJavaDateObject).list();
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
					   .setDate("endDate", endDate).list();
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
					   .setDate("endDate", endDate).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmittedCandidatesCountByStage(Long userId, String submissionStatus, Date startDateFormat,
				Date endDateFormat) throws Exception {
			 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
			 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
			 Session session = this.sessionFactory.getCurrentSession();
			 String query = "";
			 
			 if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
				 query = "select count(*) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId "
					 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and (rs.submissionStatus = 'OFFERMADE' OR  rs.submissionStatus = 'OFFERACCEPTED') ";
				 
				 List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
						   .setDate("endDate", endDate).list();
				 
				 Integer count= 0;
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;
				 
			 }else{
				 
				 query = "select count(*) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId "
					 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and rs.submissionStatus = :submissionStatus ";
				 
				 List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
						   .setDate("endDate", endDate).setString("submissionStatus", submissionStatus).list();
				 
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
				return  session.createQuery(query).setLong("orgLocation", orgLocation.getId()).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
			}
			else{
				String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.vendorLocationId <> null and rs.orgLocations.id = :orgLocation group by rs.vendorLocationId.id";
				return  session.createQuery(query).setLong("orgLocation", orgLocation.getId()).list();
			}
		}
		
		@Override
		@Transactional
		public Integer getReferByMeJobAppliedCandidateCount(Long userId, Date startDateFormat, Date endDateFormat)
				throws Exception {
			 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
			 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
			Session session = this.sessionFactory.getCurrentSession();
		     
			 String query = "select count(*) from RequirementSubmission rs where "
			 		+ "rs.candidate.consultantUser.id IN (select refer.referToUser from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate)) and "
			 		+ "rs.requirement.id IN (select refer.requirementId from RequirementRefer refer where refer.referFromUser = :userId and refer.referStatus = 'Applied' and (refer.createdDate BETWEEN :startDate and :endDate))";
		     
		     List<Long> appliedCount = session.createQuery(query).setLong("userId", userId)
		    		   .setDate("startDate", startDate)
					   .setDate("endDate", endDate).list();
			 Integer count= 0;
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;
		}

		@Override
		@Transactional
		public Integer getRecruiterSubmittedCandidateCountByStartDateAndEndDate(Long userId, Date startDateFormat,
				Date endDateFormat) throws Exception { 
			
			 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
			 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
			Session session = this.sessionFactory.getCurrentSession();
				 
				 String query = "select count(*) from RequirementSubmission rs where rs.recruiterUserId.id = :recruiterUserId and (rs.createdDate BETWEEN :startDate and :endDate) ";
			     
			     List<Long> appliedCount = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
						   .setDate("endDate", endDate).list();
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
				String submissionStatus, Date startDateFormat, Date endDateFormat) throws Exception {
			java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
			 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
				 Session session = this.sessionFactory.getCurrentSession();
				 List<RequirementSubmission>  submissionsCountList = null ;
				 String query;
				 if(null != submissionStatus && !"".equalsIgnoreCase(submissionStatus) && submissionStatus != "undefined"){
					 
					 if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
						 
						 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.candidate left join fetch rs.requirement where rs.recruiterUserId.id = :recruiterUserId "
							 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and (rs.submissionStatus = 'OFFERMADE' OR rs.submissionStatus = 'OFFERACCEPTED')  ";
						     submissionsCountList = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
									                                          .setDate("endDate", endDate).list();
						     
					 }else{
						
						 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.candidate left join fetch rs.requirement where rs.recruiterUserId.id = :recruiterUserId "
							 		+ "and (rs.createdDate BETWEEN :startDate and :endDate) and rs.submissionStatus = :submissionStatus ";
						     submissionsCountList = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
									                                          .setDate("endDate", endDate).setString("submissionStatus", submissionStatus).list();
					 }
					 
					 
				 }else{
					 query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.candidate  left join fetch rs.requirement where rs.recruiterUserId.id = :recruiterUserId and (rs.createdDate BETWEEN :startDate and :endDate) ";
					 submissionsCountList = session.createQuery(query).setLong("recruiterUserId", userId).setDate("startDate", startDate)
							                                           .setDate("endDate", endDate).list();
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
		     List<RequirementSubmission> submissionList = session.createQuery(query).setLong( "requirementId", requirementId).setLong( "userId", userId).list();
		   
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
			 
			 List<RequirementSubmission> submissionList  = session.createQuery(query).setLong("recruiterUserId", recruiterUserId) .setLong("requirementId", requirementId).list();
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
			
			List<RequirementSubmission> ownApplicantsList = query.list() ;
			
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
						.setString( "email", email ).setLong("requirementId", requirementId ).setLong("recruiterUserId", userId ).list();
				
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
		 
		 List<RequirementSubmission> submissionList  = session.createQuery(query).setLong("orgLocationId", loginOrgLocations.getId()).setParameter("startDateUTC", startUTCDate).setParameter("endDateUTC", endUTCDate).setParameterList("candidateIds", applicantsList).list();
		 return submissionList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Integer getRequirementHistoryCountByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "select count(*) from RequirementSubmission rs where rs.candidate.id =:candidateId and rs.orgLocations =:orgLocationId";
		     List<Long> requirementHistoryCount = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
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
			String query ="from RequirementSubmission submission where submission.candidate.id = :candidateId and submission.requirement.id = :requirementId  ";
			List<CandidateEducation> educationList = session.createQuery(query).setLong("candidateId", candidateId).setLong("requirementId", requirementId).list();
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
		     List<Long> candidateList = session.createQuery(query).setParameterList("submissionsIds", submissionsIds).setLong("orgLocationId", orgLocationId).list();
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
		     List<Long> submissionIds = session.createQuery(query).setParameterList("candidateIds", candidateIds).setLong("orgLocationId", orgLocationId).list();
		     Integer count = 0;
				if(submissionIds != null && !submissionIds.isEmpty()){
						count = submissionIds.get(0).intValue();
				}
				return submissionIds;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmissionsView> getOfferAcceptedCandidatesListByOrgLocationId(Long orgLocationId) {
			 Session session = this.sessionFactory.getCurrentSession();
			 String Query = "from RequirementSubmissionsView where orgLocationId =:orgLocationId " ; 
			 List<RequirementSubmissionsView>  offerAcceptedCandidatesList = session.createQuery(Query).setLong( "orgLocationId", orgLocationId).list();
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
											  					  .list();
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
						
						List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
						 List<Long> sourceSubmissionIds = new ArrayList<Long>();
						 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
							 for(RequirementSubmission rs :vendorSubmittedApplicants){
									sourceSubmissionIds.add(rs.getId());
								}
								String sourceSubmissionIdQuery = "from RequirementSubmission reqSub   where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  and  reqSub.createdDate >= :sqlCurrentDate "; 
								List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString())
																					.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
																					.setDate("sqlCurrentDate", sqlCurrentDate)
																					.list();
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
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).list();
		    return subList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getVendorSpocSubmissions(String userCode, Long orgLocationId, Date startDateUTC, Date endDateUTC) {
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.orgLocations.id =:orgLocationId and rs.createdBy =:userCode and (rs.createdDate >= :startDateUTC and rs.createdDate <= :endDateUTC)";
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("userCode", userCode).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		    return subList;
		}
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getSubmissionsForSOWByRequirementId(Long requirementId, String sowType,OrgLocations loginOrgLocation) {
			 Session session = this.sessionFactory.getCurrentSession();
			 String subQuery = "";
			 if(Constants.Client_SOW.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.submissionStatus = 'CLIENT_OFFERMADE' OR rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}else if(Constants.Vendor_SOW.equalsIgnoreCase(sowType)  ){
		 			subQuery = "and (rs.vendorLocationId <> null or ((rs.candidate.designationType = 'C2C' or rs.candidate.designationType = 'C2C-1099') and (rs.candidate.orgRelationId <> null ))) and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}
		 		else if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED')  ";
		 		}
			 String leftJoins = "left join fetch rs.requirement req left join fetch rs.candidate left join fetch rs.candidate.orgRelationId left join fetch rs.sourceSubmission left join fetch rs.candidateResumes left join fetch rs.orgLocations left join fetch rs.vendorLocationId left join fetch rs.recruiterUserId ";
			 String query = "from RequirementSubmission rs "+leftJoins+" where req.id = :requirementId "+subQuery+" ORDER BY rs.id DESC";
			 List<RequirementSubmission>  offerAcceptedCandidatesList = session.createQuery(query).setLong("requirementId", requirementId).list();
		     return offerAcceptedCandidatesList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmission> getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(Long orgLocationId,
				Long orgRelationId, Date startUTCDate, Date endUTCDate) {
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.orgLocations.id =:orgLocationId and rs.vendorLocationId.id =:orgRelationId and rs.sourceSubmission <> NULL and (date(rs.createdDate) BETWEEN :startDateUTC and :endDateUTC)";
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setParameter("startDateUTC", startUTCDate).setParameter("endDateUTC", endUTCDate).list();
		    return subList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public RequirementSubmission getRequirementSubmissionByCandidateIdAndRequirementId(Long candidateId, Long requirementId) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
			
		     String query = "from RequirementSubmission submission left join fetch submission.candidate left join fetch submission.candidateResumes where submission.candidate.id = :candidateId and submission.requirement.id = :requirementId ";
		     List<RequirementSubmission> requirementSubmissionList = session.createQuery(query).setLong( "candidateId", candidateId).setLong( "requirementId", requirementId).list();
		   
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
			List<RequirementSubmission>  subList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setLong("vendorLocationID", vendorLocationID).list();
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
		 		}else if(Constants.Vendor_SOW.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.vendorLocationId <> null or ((rs.candidate.designationType = 'C2C' or rs.candidate.designationType = 'C2C-1099') and (rs.candidate.orgRelationId <> null ))) and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED') ";
		 		}
		 		else if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(sowType)){
		 			subQuery = "and (rs.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rs.submissionStatus = 'OFFERACCEPTED')  ";
		 		}
			 
			 String leftJoins = "left join fetch rs.requirement req left join fetch rs.candidate left join fetch rs.candidate.orgRelationId left join fetch rs.sourceSubmission left join fetch rs.candidateResumes left join fetch rs.orgLocations left join fetch rs.vendorLocationId left join fetch rs.recruiterUserId ";
			 String query = "from RequirementSubmission rs "+leftJoins+" where rs.orgLocations.id = :loginOrgLocationId "+subQuery+" ORDER BY rs.id DESC";
			 List<RequirementSubmission>  offerAcceptedCandidatesList = session.createQuery(query).setLong("loginOrgLocationId", loginOrgLocation.getId()).list();
		     return offerAcceptedCandidatesList;
		}

		
		@SuppressWarnings({ "unchecked", "unused" })
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public ResponseList<RequirementSubmissionHistory> getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO,Long orgLocationsId, String submissionStatus, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate,OrgLocations orgLocations) throws Exception
		{
			ResponseList<RequirementSubmissionHistory> response = new ResponseList<>();
			try {
				Session session = this.sessionFactory.getCurrentSession();
				Integer filterCount = 0;
	
		        String submissionStages = "";
		        if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
		        	submissionStages = " and rsh.toStage = 'SUBMISSION'";
				}else if(submissionStatus.equalsIgnoreCase("SHORTLIST")){
					submissionStages = " and rsh.toStage = 'SHORTLIST'";	
				}else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
					submissionStages = " and rsh.toStage = 'SUBMIT_TO_CLIENT'";
				}else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
					submissionStages = " and (rsh.toStage = 'INTERVIEW' or rsh.toStage = 'CLIENT_INTERVIEW')";
				}else if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
					submissionStages = "and (rsh.toStage = 'OFFERMADE' OR rsh.toStage = 'OFFERACCEPTED' OR rsh.toStage = 'OFFERREJECTED' "
							+ " OR rsh.toStage = 'CLIENT_OFFERMADE' OR rsh.toStage = 'CLIENT_OFFERACCEPTED' OR rsh.toStage = 'CLIENT_OFFERREJECTED') ";
				}else if(submissionStatus.equalsIgnoreCase("REJECTED")) {
					submissionStages = " and rsh.toStage = 'REJECTED'";
				}else if(submissionStatus.equalsIgnoreCase("JOINED")) {
					submissionStages = " and rsh.toStage = 'JOINED'";
				}else if(submissionStatus.equalsIgnoreCase("BACKOUT")) {
					submissionStages = " and rsh.toStage = 'BACKOUT'";
				}
		        
		        //Employement Type Filter
		        String empQuery =  "";
		        if(requirementSubmissionHistoryDTO.getEmploymentType() != null && requirementSubmissionHistoryDTO.getEmploymentType() != "") {
		        	 empQuery = " and rsh.submissionId.requirement.jobType IN('"+StringUtils.join(requirementSubmissionHistoryDTO.getEmploymentType().split(","),"','")+"') ";
		        }
		        
		        //Clinet reated filter 
		        List<Long> clientIdsList = new ArrayList<>();
		        List<String> userList = new ArrayList<>();
		        String clientSubQuery = "";
				if(requirementSubmissionHistoryDTO.getClientId() != null && !requirementSubmissionHistoryDTO.getClientId().isEmpty()){
					String[] client = requirementSubmissionHistoryDTO.getClientId().split(",");
					for(int i=0; i<client.length; i++){
						clientIdsList.add(Long.valueOf(client[i]));
					}
				}else {
					clientIdsList = null;
				}
				
				//Team reated filter 
		        List<Long> teamIdsList = new ArrayList<>();
		        String teamSubQuery = "";
				if(requirementSubmissionHistoryDTO.getTeamId() != null && !requirementSubmissionHistoryDTO.getTeamId().isEmpty()){
					String[] team = requirementSubmissionHistoryDTO.getTeamId().split(",");
					for(int i=0; i<team.length; i++){
						teamIdsList.add(Long.valueOf(team[i]));
					}
				}else {
					teamIdsList = null;
				}
		        
	
				//Incentivized filter
				String incentivizedQuery =  "";
		        if(requirementSubmissionHistoryDTO.getIncentivizedType() != null && requirementSubmissionHistoryDTO.getIncentivizedType() != "") {
		        	incentivizedQuery = " and rsh.submissionId.requirement.incentivized IN('"+StringUtils.join(requirementSubmissionHistoryDTO.getIncentivizedType().split(","),"','")+"') ";
		        }
		        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
				Matcher searchValueType=special.matcher(requirementSubmissionHistoryDTO.getSearchValue());
		        StringBuilder query1 = new StringBuilder("");
	 			
	 		    String regx = "^[a-zA-Z0-9 ]*$";
	 		    Pattern strpattern = Pattern.compile(regx);
	 		    Matcher strmatcher = strpattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
	 		    
	 		   String emailregex = "^(.+)@(.+)$";
	 		  
	 		  Pattern emailpattern = Pattern.compile(emailregex);
	 		 Matcher emailMatcher = emailpattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
				if(requirementSubmissionHistoryDTO.getSearchValue() !=null && !"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue())) {
				
					 Pattern pattern = Pattern.compile("^(([0-9]{0,4})[-{1}]([0-9]{0,4})[-{1}]([0-9]{0,4}))$");
					 Matcher matcher = pattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
					 java.sql.Date sqlDate = null;
					 Pattern p = Pattern.compile("(?:\\(\\d{3}\\)|\\d{3}[-]*)\\d{3}[-]*\\d{4}");
					 Matcher mobileNumMacher=p.matcher(requirementSubmissionHistoryDTO.getSearchValue());
						if (matcher.find() && !mobileNumMacher.find()) {
							try {
								String searchValue = requirementSubmissionHistoryDTO.getSearchValue();
								DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
								LocalDate parsedDate = LocalDate.parse(searchValue, f);
								DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								String newDate = parsedDate.format(f2);
								Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
								sqlDate = new java.sql.Date(searchDate.getTime()); 
								query1.append("and (rs.joinedDate like '" + sqlDate + "%' )");
							 }catch (Exception e) {
								 query1.append("");
							 }
						}
						else if(mobileNumMacher.find()){
							query1.append("and (rsh.submissionId.candidate.mobilePhone like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' )");
						}
						else if(searchValueType.matches() || strmatcher.matches() || emailMatcher.matches()){
					query1.append("and( (rsh.submissionId.candidate.firstName like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' OR rsh.submissionId.candidate.lastName like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' )")
					      .append("OR (concat(rsh.submissionId.candidate.firstName,rsh.submissionId.candidate.lastName) like '%" + requirementSubmissionHistoryDTO.getSearchValue().trim() + "%')" )
					      .append("OR rsh.submissionId.candidate.email like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				          .append("OR rsh.submissionId.candidate.mobilePhone like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				          .append("OR rsh.submissionId.createdUser like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				          .append("OR rsh.submissionId.candidateScore like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				          .append("OR rsh.submissionId.submissionStatus like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				          .append("OR rsh.submissionId.requirement.jobTitle like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
					      .append("OR rsh.toStage like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
					      .append("OR rsh.createdUser like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%') ");
				
						}
						}
				
				if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
		        	Map<String, Object> params = new HashMap<String,Object>();
		    		
		    		String from = " from RequirementSubmissionHistory rsh left join fetch rsh.submissionId.candidate can left join fetch rsh.submissionId.requirement req left join fetch rsh.submissionId rs";
		    		String fromQuryCount = "select count(*) from RequirementSubmissionHistory rsh  ";
		    		
		    		String subQuery1 = "";
		    		String subQuery2 = "";
		    		String subQuery3 = "";
		    		String subQuery4 = "";
		    		String subQuery5 = "";
		    		
		    		if(requirementSubmissionHistoryDTO.getSearchType().equalsIgnoreCase("MY") || (requirementSubmissionHistoryDTO.getApplicants() != null && requirementSubmissionHistoryDTO.getApplicants().equalsIgnoreCase("MYApplicants"))) {
		    			subQuery1 = " (rsh.submissionId.createdBy =:userCode)";
		    			params.put("userCode",user.getUserCode());
		    		}else {
		    			
		    			userList = userDao.getUserHierarchyUserCodeList(user.getId());
		    			
		    			//login user as created by
			    		subQuery1 = " (rsh.submissionId.requirement.id IN (select req.id from Requirement req where req.createdBy =:userId))";
			    		params.put("userId",user.getId());
			    		
			    		
			    		//login user as a approver for this requirement  or  assigned to.
			    		subQuery2 = " or (rsh.submissionId.requirement.id IN (select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId )  or (rsh.submissionId.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) ) or (rsh.submissionId.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and rsh.submissionId.createdBy =:usercode))";
			    		params.put("approverUserId",user.getId());
			    		params.put("userId",user.getId());
			    		params.put("usercode",user.getUserCode());
			    		
			    		//login user as a  manager for this job.
			    		subQuery3 = " or (rsh.submissionId.requirement.id IN ( select requirement.id from Requirement requirement where  requirement.managerUserId.id = :managerUserId or requirement.primaryRecruiterUserId.id = :managerUserId) ) ";
			    		params.put("managerUserId",user.getId());
			    		
			    		subQuery4 = " or (rsh.submissionId.requirement.releaseJobToAllUsers = 'Y' and  rsh.submissionId.createdBy =:usercode)";
			    		params.put("usercode",user.getUserCode());
			    		
			    		if(userList != null && userList.size() > 0) {
			    			subQuery5 = " or (rsh.createdBy IN (:userList))";
			    			params.put("userList",userList);
			    		}
		    		}
		    		
		    		if(clientIdsList != null) {
		    			clientSubQuery = " and (rsh.submissionId.requirement.clientId.id IN (:clientIdsList))";
						params.put("clientIdsList",clientIdsList);
		    		}
		    		
		    		if(teamIdsList != null) {
		    			teamSubQuery = " and (rsh.submissionId.requirement.id IN (select ateam.requirementId.id from RequirementAssignTo ateam where ateam.requirementId.id = rsh.submissionId.requirement.id and ateam.teamId.id IN (:teamIdsList)))";
						params.put("teamIdsList",teamIdsList);
		    		}
					
		    		String subQuery = "";
		    		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" ){
		    			subQuery = "where rsh.submissionId.orgLocations.id = :orgLocationsId "+submissionStages+" "+empQuery+" "+clientSubQuery+" "+teamSubQuery+" "+incentivizedQuery+" and rsh.submissionId.status = 1 and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate and ( ("+subQuery1+" "+subQuery2+" "+subQuery3+" "+subQuery4+" ) "+subQuery5+") ";
		    			params.put("orgLocationsId",orgLocationsId);
		    			params.put("startDate",startUTCDate);
		    			params.put("endDate",endUTCDate);
		    		}
		    		String whereCondition = subQuery;
		    		Query query =  session.createQuery(from+" "+whereCondition+"  "+query1+"").setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength());
		    		
		    		for (String str : query.getNamedParameters())
		            {
		    			if(str.equalsIgnoreCase("orgLocationsId")  || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
		    				query.setLong(str, (long) params.get( str ));
		    			}else if(str.equalsIgnoreCase("clientIdsList")){
		    				query.setParameterList(str, clientIdsList);
		    			}else if(str.equalsIgnoreCase("userList")){
		    				query.setParameterList(str, userList);
		    			}else if(str.equalsIgnoreCase("teamIdsList")){
		    				query.setParameterList(str, teamIdsList);
		    			}else{
		    				query.setParameter( str, params.get( str ) );
		    			}
		            }
		    		
		    		Query countQuery = session.createQuery(fromQuryCount+" "+whereCondition+" "+query1+" ");
	
		    		for (String str : countQuery.getNamedParameters())
		            {
		    			if(str.equalsIgnoreCase("orgLocationsId")  || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
		    				countQuery.setLong(str, (long) params.get( str ));
		    			}else if(str.equalsIgnoreCase("clientIdsList")){
		    				countQuery.setParameterList(str, clientIdsList);
		    			}else if(str.equalsIgnoreCase("userList")){
		    				countQuery.setParameterList(str, userList);
		    			}else if(str.equalsIgnoreCase("teamIdsList")){
		    				countQuery.setParameterList(str, teamIdsList);
		    			}else{
		    				countQuery.setParameter( str, params.get( str ) );
		    			}
		            }
		    		
		    		
		    		int totalCount = 0;
		    		if (requirementSubmissionHistoryDTO != null) {
		    			if(countQuery.list() != null && !countQuery.list().isEmpty()) {
		    				totalCount = ((Long) countQuery.list().iterator().next()).intValue();
		    			}
		    		} 
		    		response.setRecordsTotal(totalCount);
		    		
		    		if(query1.toString() != null && !query1.toString().isEmpty()){
		    			response.setRecordsFiltered(filterCount);
		    		}else {
		    			response.setRecordsFiltered(totalCount);
		    		}
		    		
		    		response.setData(query.list());
		    		return response;
		        }else{
		        	if(!searchValueType.matches()){
			        	Map<String, Object> params = new HashMap<String,Object>();
			    		List<Long> countlist = null;
			    		List  requirementSubmissionHistoryList = null;
			        	
			        	if(clientIdsList != null) {
			    			clientSubQuery = " and (rsh.submissionId.requirement.clientId.id IN (:clientIdsList))";
			    		}
			        	if(teamIdsList != null) {
			        		teamSubQuery = " and (rsh.submissionId.requirement.id IN (select ateam.requirementId.id from RequirementAssignTo ateam where ateam.requirementId.id = rsh.submissionId.requirement.id and ateam.teamId.id IN (:teamIdsList)))";
			    		}
			        	
			        	String subQuery1 = "";
			    		if(requirementSubmissionHistoryDTO.getSearchType().equalsIgnoreCase("MY") || (requirementSubmissionHistoryDTO.getApplicants() != null && requirementSubmissionHistoryDTO.getApplicants().equalsIgnoreCase("MYApplicants"))) {
			    			subQuery1 = " and (rsh.submissionId.createdBy =:userCode)";
			    			params.put("userCode",user.getUserCode());
			    		}
			    		String query="";
			        	if(requirementSubmissionHistoryDTO.getType() != null &&requirementSubmissionHistoryDTO.getType().equalsIgnoreCase("applicantReport")){
				     		query =" from RequirementSubmissionHistory rsh left join fetch rsh.submissionId.candidate can left join fetch rsh.submissionId.requirement req left join fetch rsh.submissionId rs where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" "+empQuery+" "+clientSubQuery+" "+teamSubQuery+" "+incentivizedQuery+" "+subQuery1+" and rsh.submissionId.status = 1  and rsh.submissionId.requirement IS NOT NULL and rs.joinedDate >= :startDate and rs.joinedDate <= :endDate "+query1+"";
			        	}
			        	else{
				     		query =" from RequirementSubmissionHistory rsh left join fetch rsh.submissionId.candidate can left join fetch rsh.submissionId.requirement req left join fetch rsh.submissionId rs where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" "+empQuery+" "+clientSubQuery+" "+teamSubQuery+" "+incentivizedQuery+" "+subQuery1+" and rsh.submissionId.status = 1  and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate "+query1+"";
			        	}
	//		     		String query =" from RequirementSubmissionHistory rsh left join fetch rsh.submissionId.candidate can left join fetch rsh.submissionId.requirement req left join fetch rsh.submissionId rs where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" "+empQuery+" "+clientSubQuery+" "+teamSubQuery+" "+incentivizedQuery+" "+subQuery1+" and rsh.submissionId.status = 1  and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate "+query1+"";
		     			String countQuery ="select count(*) from RequirementSubmissionHistory rsh  where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" "+empQuery+" "+clientSubQuery+" "+teamSubQuery+" "+incentivizedQuery+" "+subQuery1+" and rsh.submissionId.status = 1  and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate";
		     			params.put("orgLocationsId",orgLocationsId);
		     			if(clientIdsList != null) {
		     				params.put("clientIdsList",clientIdsList);
		     			}
		     			if(teamIdsList != null) {
		     				params.put("teamIdsList",teamIdsList);
		     			}
		     			params.put("startDate",startUTCDate);
		     			params.put("endDate",endUTCDate);
		     			
		     			Query listCountQuery = session.createQuery(countQuery);
		     			Query listQuery = session.createQuery(query);
		     			
		     			for (String str : listQuery.getNamedParameters())
			            {
			    			if(str.equalsIgnoreCase("orgLocationsId")){
			    				listCountQuery.setLong(str, (long) params.get( str ));
			    				listQuery.setLong(str, (long) params.get( str ));
			    			}else if(str.equalsIgnoreCase("userCode")){
			    				listCountQuery.setString(str, params.get( str ).toString());
			    				listQuery.setString(str, params.get( str ).toString());
			    			}else if(str.equalsIgnoreCase("clientIdsList")){
			    				listCountQuery.setParameterList(str, clientIdsList);
			    				listQuery.setParameterList(str, clientIdsList);
			    			}else if(str.equalsIgnoreCase("teamIdsList")){
			    				listCountQuery.setParameterList(str, teamIdsList);
			    				listQuery.setParameterList(str, teamIdsList);
			    			}else{
			    				listCountQuery.setParameter( str, params.get( str ) );
			    				listQuery.setParameter( str, params.get( str ) );
			    			}
			            }
	
		     			 Pattern p1 = Pattern.compile("(?:\\(\\d{3}\\)|\\d{3}[-]*)\\d{3}[-]*\\d{4}");
						 Matcher mobNumMacher=p1.matcher(requirementSubmissionHistoryDTO.getSearchValue());
						 
		     			if((strmatcher.matches() || emailMatcher.matches()) && (!"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue()))){
		     				requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.list();
		     			}
		     			
		     			
		     			else if((mobNumMacher.matches()) && (!"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue()))){
		     				requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.list();
		     			}
		     			
		     			else if((!searchValueType.matches() && strmatcher.matches()) && (!"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue()))){
		     				requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.list();
		     			}
		     			
		     			else if((!strmatcher.matches() && searchValueType.matches()) || ("".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue()))){
		     				requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.list();
		     			}
		     			else{
		     				requirementSubmissionHistoryList=new ArrayList<>();
		     				countlist=new ArrayList<>();	
		     			}
		 /*    			if(!mobNumMacher.find() && strmatcher.find() && !searchValueType.find() && "".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue())){
			     			requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.list();	
		     			}
		     			else if(mobNumMacher.find() && !strmatcher.find() && !searchValueType.find() && !"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue())){
		     				requirementSubmissionHistoryList=new ArrayList<>();
		     				countlist=new ArrayList<>();
		     			}
		     			else if(!mobNumMacher.find() && !strmatcher.find() && !searchValueType.find() && !"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue())){
		     				requirementSubmissionHistoryList=new ArrayList<>();
		     				countlist=new ArrayList<>();
		     			}
		     			else{
		     				requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.list();	
		     			}*/
		     			
		     			/*if(clientIdsList != null) {
		     				requirementSubmissionHistoryList =   listQuery.setLong("orgLocationsId", orgLocationsId).setParameterList("clientIdsList", clientIdsList).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
		        		    countlist =listCountQuery.setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameterList("clientIdsList", clientIdsList).setParameter("endDate",endUTCDate).list();
			    		}else {
			    			requirementSubmissionHistoryList =   listQuery.setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
			    		    countlist =listCountQuery.setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).list();
			    		}*/
			        	
			        	int totalCount = 0;
			        	if(requirementSubmissionHistoryDTO.getType() != null &&requirementSubmissionHistoryDTO.getType().equalsIgnoreCase("applicantReport")){
			        		totalCount = listQuery.list().size();
			        		if(query1.toString() != null && !query1.toString().isEmpty()){
				    			response.setRecordsFiltered(filterCount);
				    		}else {
				    			response.setRecordsFiltered(totalCount);
				    		}
				    		response.setRecordsTotal(totalCount);
			        	}
			        	else{
			    		if (requirementSubmissionHistoryDTO != null) {
			    			if(countlist != null && !countlist.isEmpty()) {
			    				totalCount = countlist.iterator().next().intValue();
			    			}
			    		} 
			    		response.setRecordsTotal(totalCount);
			    		
			    		if(query1.toString() != null && !query1.toString().isEmpty()){
			    			response.setRecordsFiltered(filterCount);
			    		}else {
			    			response.setRecordsFiltered(totalCount);
			    		}
			        	}
			    		response.setData(requirementSubmissionHistoryList);
					}
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}
		
		
		@SuppressWarnings({ "unchecked", "unused" })
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public ResponseList<RequirementSubmission> getRequirementSubmissionsListWithJobIdByOrgLocationsIdAndSubmissionStatus(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO,Long requirementId, String submissionStatus, String dateRange, String userType, User user,OrgLocations orgLocations) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
			Integer filterCount = 0;
	        ResponseList<RequirementSubmission> response = new ResponseList<>();

	        String submissionStages = "";
	        if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
	        	submissionStages = " and rsh.submissionStatus = 'SUBMISSION'";
			}else if(submissionStatus.equalsIgnoreCase("SHORTLIST")){
				submissionStages = " and rsh.submissionStatus = 'SHORTLIST'";
			}else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
				submissionStages = " and rsh.submissionStatus = 'SUBMIT_TO_CLIENT'";
			}else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
				submissionStages = " and (rsh.submissionStatus = 'INTERVIEW' or rsh.submissionStatus = 'CLIENT_INTERVIEW' or rsh.submissionStatus ='SELECTED_ON_INTERVIEW')";
			}else if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
				submissionStages = " and (rsh.submissionStatus = 'OFFERMADE' OR rsh.submissionStatus = 'OFFERACCEPTED' OR rsh.submissionStatus = 'OFFERREJECTED' "
						+ " OR rsh.submissionStatus = 'CLIENT_OFFERMADE' OR rsh.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rsh.submissionStatus = 'CLIENT_OFFERREJECTED') ";
			}else if(submissionStatus.equalsIgnoreCase("REJECTED")) {
				submissionStages = " and rsh.submissionStatus = 'REJECTED'";
			}else if(submissionStatus.equalsIgnoreCase("JOINED")) {
				submissionStages = " and rsh.submissionStatus = 'JOINED'";
			}else if(submissionStatus.equalsIgnoreCase("BACKOUT")) {
				submissionStages = " and rsh.submissionStatus = 'BACKOUT'";
			}else if(submissionStatus.equalsIgnoreCase("All")){
				submissionStages = " and (rsh.submissionStatus = 'SUBMISSION' or rsh.submissionStatus = 'SHORTLIST' or rsh.submissionStatus = 'SUBMIT_TO_CLIENT'"
						+ "or rsh.submissionStatus = 'SUBMIT_TO_CLIENT' or rsh.submissionStatus = 'INTERVIEW' or rsh.submissionStatus = 'CLIENT_INTERVIEW' or rsh.submissionStatus = 'OFFERMADE' OR rsh.submissionStatus = 'OFFERACCEPTED' OR rsh.submissionStatus = 'OFFERREJECTED'"
						+ "or rsh.submissionStatus = 'CLIENT_OFFERMADE' OR rsh.submissionStatus = 'CLIENT_OFFERACCEPTED' OR rsh.submissionStatus = 'CLIENT_OFFERREJECTED' or rsh.submissionStatus = 'REJECTED'"
						+ "or rsh.submissionStatus = 'JOINED' or rsh.submissionStatus = 'BACKOUT')";	
			}
	        
	        //Clinet reated filter 
	        List<String> userList = new ArrayList<>();
	        
			//Team reated filter 
	        List<Long> teamIdsList = new ArrayList<>();
	        String teamSubQuery = "";
			if(requirementSubmissionHistoryDTO.getTeamId() != null && !requirementSubmissionHistoryDTO.getTeamId().isEmpty()){
				String[] team = requirementSubmissionHistoryDTO.getTeamId().split(",");
				for(int i=0; i<team.length; i++){
					teamIdsList.add(Long.valueOf(team[i]));
				}
			}else {
				teamIdsList = null;
			}
	        

	        StringBuilder query1 = new StringBuilder("");
			if(requirementSubmissionHistoryDTO.getSearchValue() !=null && !"".equalsIgnoreCase(requirementSubmissionHistoryDTO.getSearchValue())) {
			
				 Pattern pattern = Pattern.compile("^(([0-9]{0,4})[-{1}]([0-9]{0,4})[-{1}]([0-9]{0,4}))$");
				 Matcher matcher = pattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
				 java.sql.Date sqlDate = null;
				 Pattern p = Pattern.compile("(?:\\(\\d{3}\\)|\\d{3}[-]*)\\d{3}[-]*\\d{4}");
				 Matcher mobileNumMacher=p.matcher(requirementSubmissionHistoryDTO.getSearchValue());
					if (matcher.find() && !mobileNumMacher.find()) {
						try {
							String searchValue = requirementSubmissionHistoryDTO.getSearchValue();
							DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
							LocalDate parsedDate = LocalDate.parse(searchValue, f);
							DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							String newDate = parsedDate.format(f2);
							Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
							sqlDate = new java.sql.Date(searchDate.getTime()); 
							query1.append("and (rs.joinedDate like '" + sqlDate + "%' )");
						 }catch (Exception e) {
							 query1.append("");
						 }
					}
					else if(mobileNumMacher.find()){
						query1.append("and (rsh.candidate.mobilePhone like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' )");
					}
					else{
						if(!requirementSubmissionHistoryDTO.getSearchValue().equals("'")){
				query1.append(" and( (rsh.candidate.firstName like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' OR rsh.candidate.lastName like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' )")
				      .append("OR (concat(rsh.candidate.firstName,rsh.candidate.lastName) like '%" + requirementSubmissionHistoryDTO.getSearchValue().trim() + "%')" )
				      .append("OR rsh.candidate.email like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
			          .append("OR rsh.candidate.mobilePhone like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
			          .append("OR rsh.requirement.jobTitle like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				      .append("OR rsh.submissionStatus like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%' ")
				      .append("OR rsh.createdUser like '%" + requirementSubmissionHistoryDTO.getSearchValue() + "%') ");
						}
					}
					}
			
			if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
	        	Map<String, Object> params = new HashMap<String,Object>();
	    		
	    		String from = " from RequirementSubmission rsh left join fetch rsh.candidate can left join fetch rsh.requirement";
	    		String fromQuryCount = "select count(*) from RequirementSubmission rsh  ";
	    		
	    		String subQuery1 = "";
	    		String subQuery2 = "";
	    		String subQuery3 = "";
	    		String subQuery4 = "";
	    		String subQuery5 = "";
	    		
	    			userList = userDao.getUserHierarchyUserCodeList(user.getId());
	    			
	    			//login user as created by
		    		subQuery1 = " (rsh.requirement.id IN (select req.id from Requirement req where req.createdBy =:userId))";
		    		params.put("userId",user.getId());
		    		
		    		
		    		//login user as a approver for this requirement  or  assigned to.
		    		subQuery2 = " or (rsh.requirement.id IN (select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId )  or (rsh.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) ) or (rsh.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and rsh.createdBy =:usercode))";
		    		params.put("approverUserId",user.getId());
		    		params.put("userId",user.getId());
		    		params.put("usercode",user.getUserCode());
		    		
		    		//login user as a  manager for this job.
		    		subQuery3 = " or (rsh.requirement.id IN ( select req.id from Requirement req where  req.managerUserId.id = :managerUserId ) ) ";
		    		params.put("managerUserId",user.getId());
		    		
		    		subQuery4 = " or (rsh.requirement.releaseJobToAllUsers = 'Y' and  rsh.createdBy =:usercode)";
		    		params.put("usercode",user.getUserCode());
		    		
		    		if(userList != null && userList.size() > 0) {
		    			subQuery5 = " or (rsh.createdBy IN (:userList))";
		    			params.put("userList",userList);
		    		}
	    		
	    		
	    		if(teamIdsList != null) {
	    			teamSubQuery = " and (rsh.requirement.id IN (select ateam.requirementId.id from RequirementAssignTo ateam where ateam.requirementId.id = rsh.requirement.id and ateam.teamId.id IN (:teamIdsList)))";
					params.put("teamIdsList",teamIdsList);
	    		}
				
	    		String subQuery = "";
	    		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" ){
	    			subQuery = "where rsh.orgLocations.id = :orgLocationsId and rsh.requirement.id =:requirementId"+submissionStages+" "+teamSubQuery+" and rsh.status = 1 and ( ("+subQuery1+" "+subQuery2+" "+subQuery3+" "+subQuery4+" ) "+subQuery5+") ";
	    			params.put("orgLocationsId",orgLocations.getId());
	    			params.put("requirementId",requirementId);
	    		}
	    		String whereCondition = subQuery;
	    		Query query =  session.createQuery(from+" "+whereCondition+"  "+query1).setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength());
	    		
	    		for (String str : query.getNamedParameters())
	            {
	    			if(str.equalsIgnoreCase("orgLocationsId")  || str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
	    				query.setLong(str, (long) params.get( str ));
	    			}else if(str.equalsIgnoreCase("userList")){
	    				query.setParameterList(str, userList);
	    			}else if(str.equalsIgnoreCase("teamIdsList")){
	    				query.setParameterList(str, teamIdsList);
	    			}else{
	    				query.setParameter( str, params.get( str ) );
	    			}
	            }
	    		
	    		Query countQuery = session.createQuery(fromQuryCount+" "+whereCondition+" "+query1);

	    		for (String str : countQuery.getNamedParameters())
	            {
	    			if(str.equalsIgnoreCase("orgLocationsId") || str.equalsIgnoreCase("requirementId")  || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
	    				countQuery.setLong(str, (long) params.get( str ));
	    			}else if(str.equalsIgnoreCase("userList")){
	    				countQuery.setParameterList(str, userList);
	    			}else if(str.equalsIgnoreCase("teamIdsList")){
	    				countQuery.setParameterList(str, teamIdsList);
	    			}else{
	    				countQuery.setParameter( str, params.get( str ) );
	    			}
	            }
	    		
	    		
	    		int totalCount = 0;
	    		if (requirementSubmissionHistoryDTO != null) {
	    			if(countQuery.list() != null && !countQuery.list().isEmpty()) {
	    				totalCount = ((Long) countQuery.list().iterator().next()).intValue();
	    			}
	    		} 
	    		response.setRecordsTotal(totalCount);
	    		
	    		if(query1.toString() != null && !query1.toString().isEmpty()){
	    			response.setRecordsFiltered(totalCount);
	    		}else {
	    			response.setRecordsFiltered(totalCount);
	    		}
	    		
	    		response.setData(query.list());
	    		return response;
	        }else{
	        	Map<String, Object> params = new HashMap<String,Object>();
	    		List<Long> countlist = null;
	    		List  requirementSubmissionHistoryList = null;
	        	
	        	if(teamIdsList != null) {
	        		teamSubQuery = " and (rsh.requirement.id IN (select ateam.requirementId.id from RequirementAssignTo ateam where ateam.requirementId.id = rsh.submissionId.requirement.id and ateam.teamId.id IN (:teamIdsList)))";
	    		}
	        	
	        	
	    		String query="";
	        	if(requirementSubmissionHistoryDTO.getType() != null &&requirementSubmissionHistoryDTO.getType().equalsIgnoreCase("applicantReport")){
		     		query =" from RequirementSubmission rsh left join fetch rsh.candidate can left join fetch rsh.requirement req  where rsh.orgLocations = :orgLocationsId and rsh.requirement =:requirementId"+submissionStages+" "+teamSubQuery+" and rsh.status = 1  and rsh.requirement IS NOT NULL "+query1+"";
	        	}
	        	else{
		     		query =" from RequirementSubmission rsh left join fetch rsh.candidate can left join fetch rsh.requirement req  where rsh.orgLocations = :orgLocationsId and rsh.requirement =:requirementId"+submissionStages+" "+teamSubQuery+" and rsh.status = 1  and rsh.requirement IS NOT NULL "+query1+"";
	        	}
//	     		String query =" from RequirementSubmissionHistory rsh left join fetch rsh.submissionId.candidate can left join fetch rsh.submissionId.requirement req left join fetch rsh.submissionId rs where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" "+empQuery+" "+clientSubQuery+" "+teamSubQuery+" "+incentivizedQuery+" "+subQuery1+" and rsh.submissionId.status = 1  and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate "+query1+"";
     			String countQuery ="select count(*) from RequirementSubmission rsh  where rsh.orgLocations = :orgLocationsId and rsh.requirement =:requirementId"+submissionStages+" "+teamSubQuery+" and rsh.status = 1  and rsh.requirement IS NOT NULL "+query1+"";
     			params.put("orgLocationsId",orgLocations.getId());
     			params.put("requirementId",requirementId);
     			
     			if(teamIdsList != null) {
     				params.put("teamIdsList",teamIdsList);
     			}
     			
     			Query listCountQuery = session.createQuery(countQuery);
     			Query listQuery = session.createQuery(query);
     			
     			for (String str : listQuery.getNamedParameters())
	            {
	    			if(str.equalsIgnoreCase("orgLocationsId") || str.equalsIgnoreCase("requirementId")){
	    				listCountQuery.setLong(str, (long) params.get( str ));
	    				listQuery.setLong(str, (long) params.get( str ));
	    			}else if(str.equalsIgnoreCase("userCode")){
	    				listCountQuery.setString(str, params.get( str ).toString());
	    				listQuery.setString(str, params.get( str ).toString());
	    			}else if(str.equalsIgnoreCase("teamIdsList")){
	    				listCountQuery.setParameterList(str, teamIdsList);
	    				listQuery.setParameterList(str, teamIdsList);
	    			}else{
	    				listCountQuery.setParameter( str, params.get( str ) );
	    				listQuery.setParameter( str, params.get( str ) );
	    			}
	            }
     			
     			requirementSubmissionHistoryList =   listQuery.setFirstResult(requirementSubmissionHistoryDTO.getStart()).setMaxResults(requirementSubmissionHistoryDTO.getLength()).list();
    		    countlist =listCountQuery.list();
	        	int totalCount = 0;
	    		if (requirementSubmissionHistoryDTO != null) {
	    			if(countlist != null && !countlist.isEmpty()) {
	    				totalCount = countlist.iterator().next().intValue();
	    			}
	    		} 
	    		response.setRecordsTotal(totalCount);
	    		
	    		if(query1.toString() != null && !query1.toString().isEmpty()){
	    			response.setRecordsFiltered(totalCount);
	    		}else {
	    			response.setRecordsFiltered(totalCount);
	    		}
	    		
	    		response.setData(requirementSubmissionHistoryList);
	    		return response;
	        }
		}
		

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<RequirementSubmissionHistory> getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate)
		{
			  Session session = this.sessionFactory.getCurrentSession();
		        String submissionStages = "";
		        if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
		        	submissionStages = " and rsh.toStage = 'SUBMISSION'";
				}else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
					submissionStages = " and rsh.toStage = 'SUBMIT_TO_CLIENT'";
				}else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
					submissionStages = " and (rsh.toStage = 'INTERVIEW' or rsh.toStage = 'CLIENT_INTERVIEW')";
				}else if(submissionStatus.equalsIgnoreCase("OFFERMADE")){
					submissionStages = "and (rsh.toStage = 'OFFERMADE' OR rsh.toStage = 'OFFERACCEPTED' OR rsh.toStage = 'OFFERREJECTED' "
							+ " OR rsh.toStage = 'CLIENT_OFFERMADE' OR rsh.toStage = 'CLIENT_OFFERACCEPTED' OR rsh.toStage = 'CLIENT_OFFERREJECTED') ";
				}
		        
		        if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
		        	Map<String, Object> params = new HashMap<String,Object>();
		    		
		    		String from = " from RequirementSubmissionHistory rsh  ";
		    		
		    		//login user as created by
		    		String subQuery1 = "rsh.submissionId.requirement.id IN (select req.id from Requirement req where req.createdBy =:userId)";
		    		params.put("userId",user.getId());
		    		
		    		//login user as a approver for this requirement  or  assigned to.
		    		String subQuery2 = "rsh.submissionId.requirement.id IN (select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId )  or (rsh.submissionId.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) ) or (rsh.submissionId.requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId) and rsh.submissionId.createdBy =:usercode)";
		    		params.put("approverUserId",user.getId());
		    		params.put("userId",user.getId());
		    		params.put("usercode",user.getUserCode());
		    		
		    		//login user as a  manager for this job.
		    		String subQuery3 = "rsh.submissionId.requirement.id IN ( select requirement.id from Requirement requirement where  requirement.managerUserId.id = :managerUserId )  ";
		    		params.put("managerUserId",user.getId());
		    		
		    		String subQuery4 = " rsh.submissionId.requirement.releaseJobToAllUsers = 'Y' and  rsh.submissionId.createdBy =:usercode";
		    		params.put("usercode",user.getUserCode());
		    		
		    		String subQuery = "";
		    		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" ){
		    			if(dateRange != null && "today".equals(dateRange)){
		    				subQuery = "where rsh.submissionId.orgLocations.id = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") or ("+subQuery4+") ) ";
		    			}else if(dateRange != null && "previous".equals(dateRange)){
		    			    subQuery = "where rsh.submissionId.orgLocations.id = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.updatedDate < :startDate and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") or ("+subQuery4+") ) ";
		    			}
		    			params.put("orgLocationsId",orgLocationsId);
		    			params.put("startDate",startUTCDate);
		    			params.put("endDate",endUTCDate);
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
		    		
		    		List<RequirementSubmissionHistory> reqSubmsHistoryList  = query.list();
					return reqSubmsHistoryList;
					
		        }else{
		        	List<RequirementSubmissionHistory> reqSubmsHistoryList = null;
		        	String query = "";
		        	if(dateRange != null && "today".equals(dateRange)){
		                query =" from RequirementSubmissionHistory rsh  where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1 and rsh.submissionId.requirement IS NOT NULL  and rsh.updatedDate >= :startDate and rsh.updatedDate <= :endDate";
		                reqSubmsHistoryList =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).list();
		     		}else if(dateRange != null && "previous".equals(dateRange)){
		     			query =" from RequirementSubmissionHistory rsh  where rsh.submissionId.orgLocations = :orgLocationsId "+submissionStages+" and rsh.submissionId.status = 1  and rsh.submissionId.requirement IS NOT NULL and rsh.updatedDate < :startDate";
		     			reqSubmsHistoryList =   session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setParameter("startDate",startUTCDate).list();
		     		}
		        	
					return reqSubmsHistoryList;
	        }
		}

		@Override
		@Transactional
		public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidatesForClientOffer(
				OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer)
				throws Exception {
			
			  Session session = this.sessionFactory.getCurrentSession();
			
			  /*String  subQuery = " and (rs.submissionStatus = 'SHORTLIST' OR rs.submissionStatus = 'INTERVIEW' OR rs.submissionStatus = 'SUBMISSION' OR rs.submissionStatus = 'SUBMIT_TO_CLIENT') ";*/
			  String  subQuery = " and (rs.submissionStatus = 'SELECTED_ON_INTERVIEW' OR rs.submissionStatus = 'SUBMIT_TO_CLIENT') ";
			 
			 List<RequirementSubmission> shortListCandidatesList;
			 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
				 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.candidate.orgLocationId.id <> :orgLocationsId  and rs.id NOT IN (:existingApplicantsIds) "+subQuery+" ";
				 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).list();
			 }else{
				 String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.candidate.orgLocationId.id <> :orgLocationsId "+subQuery+"";
				 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocations.getId()).setLong( "requirementId", requirementId).list();
			 }
		     return shortListCandidatesList;
		}

		@Override
		@Transactional
		public RequirementSubmission getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(
				Long sourceSubmissionId, Long vendorLocationId) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
		     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.sourceSubmission.id =:sourceSubmissionId and rs.vendorLocationId.id =:vendorLocationId ";
		     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "sourceSubmissionId", sourceSubmissionId).setLong( "vendorLocationId", vendorLocationId).list();
		   
		     if(shortListCandidatesList != null && shortListCandidatesList.size() >0){
					return shortListCandidatesList.get(0);
				}
				return null;
		}
		
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public ResponseList<RequirementSubmission> loadWorkflowSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocations,CandidateVerificationConfig candidateVerificationConfig) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<RequirementSubmission> requirementSubmissionList = null;
			ResponseList<RequirementSubmission> response = new ResponseList<>();
			List<String> chosenColumnsLst = new ArrayList<String>(Arrays.asList(requirementSubmissionsDTO.getWorkflowSatgeColumns().split(",")));
			System.out.println(" chosen columns are here ::::::::::::>"+requirementSubmissionsDTO.getWorkflowSatgeColumns());
			if(StringUtils.isNotBlank(requirementSubmissionsDTO.getWorkflowSatgeColumns())) {
				
			}
			StringBuilder searchQuery = new StringBuilder("");
			if(requirementSubmissionsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(requirementSubmissionsDTO.getSearchValue())) {
				
				java.sql.Date sqlDate = null;
				 
				 Pattern pattern = Pattern.compile("^(([0-9]{0,4})[-{1}]([0-9]{0,4})[-{1}]([0-9]{0,4}))$");
				 Matcher matcher = pattern.matcher(requirementSubmissionsDTO.getSearchValue());
				 if (matcher.find()) {
					try {
						String searchValue = requirementSubmissionsDTO.getSearchValue();
						DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
						LocalDate parsedDate = LocalDate.parse(searchValue, f);
						DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String newDate = parsedDate.format(f2);
						Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
						sqlDate = new java.sql.Date(searchDate.getTime()); 
						// if columns list contains "Applied On" and "DOB" columns in header list.
						 if(chosenColumnsLst.contains("createdDateStr") && chosenColumnsLst.contains("dateOfBirthStr")) {
							 searchQuery.append("and (reqSub.createdDate like '" + sqlDate + "%' OR reqSub.candidate.dateOfBirth like '" + sqlDate + "%')");
						 }
						 //if contains "Applied On" 
						 else if(chosenColumnsLst.contains("createdDateStr")) {
							 searchQuery.append("and (reqSub.createdDate like '" + sqlDate + "%' )");
						 } //if contains "DOB" 
						 else if(chosenColumnsLst.contains("dateOfBirthStr")) {
							 searchQuery.append("and (reqSub.candidate.dateOfBirth like like '" + sqlDate + "%' )");
						 }
					 }catch (Exception e) {
						 searchQuery.append("");
					 }
				}else {
		         // below are the list of mandatory search fileds for every stage in a workflow.
				searchQuery.append(" and (reqSub.candidate.firstName like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidate.lastName like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR (concat(reqSub.candidate.firstName,' ',reqSub.candidate.lastName) like '%" + requirementSubmissionsDTO.getSearchValue().trim().replaceAll(" +", " ")+ "%')" )
					.append("OR reqSub.candidate.email like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidate.mobilePhone like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
				// above are mandatory columns for every stage hence included in filters as default columns.
					 if(chosenColumnsLst.contains("alternateNumber")) {
						 searchQuery.append("OR reqSub.candidate.alternateMobilePhone like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("location")) {
						 searchQuery.append("OR reqSub.candidate.location like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("workExperience")) {
						 searchQuery.append("OR reqSub.candidate.experienceYears like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("currentCtc")) {
						 searchQuery.append("OR reqSub.candidate.currentRate like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("expectedCTC")) {
						 searchQuery.append("OR reqSub.candidate.expectedRate like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					if(chosenColumnsLst.contains("candidateScore")) {
						 searchQuery.append("OR reqSub.candidateEducation.name like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("noticePeriod")) {
						 searchQuery.append("OR reqSub.candidate.noticePeriod like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("createdUser")) { // below filter is used for "SOURCE" column UI header.						 
						 searchQuery.append("OR reqSub.createdUser like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
					 if(chosenColumnsLst.contains("Status")) {
						 searchQuery.append("OR reqSub.submissionStatus like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
					 }
				   
					/*String[] nameBytes = requirementSubmissionsDTO.getSearchValue().split(" ");
					for(String nameByte : nameBytes) {
						searchQuery.append("OR reqSub.candidate.firstName like '%" + nameByte + "%' ")
						.append("OR reqSub.candidate.lastName like '%" + nameByte + "%' ");
						
					}*/
					searchQuery.append(" )");
					}
				}
			System.out.println("requirementSubmissionsDTO.getSubmissionType())   "+requirementSubmissionsDTO.getSubmissionType());
			
			if(StringUtils.isNotBlank(requirementSubmissionsDTO.getSubmissionType())){
				Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementSubmissionsDTO.getRequirementId());
				String[] releasedBy =null;
				
				ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
				
				if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
					releasedBy = requirement.getReleasedBy().split(",");
				}
		        Map<String, Object> params = new HashMap<String,Object>();
		        //left join fetch reqSub.requirement req left join fetch reqSub.candidate reqSubCandidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes
				String from = "from RequirementSubmission reqSub  "; 
				String subQuery = "";
				// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
					if(null != releasedBy && releasedBy.length > 0){
						
						String subQueryForVendors = "where reqSub.requirement.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( "
								+ "                                                    select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   "
								+ "                                                      and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
														params.put("userId",requirementSubmissionsDTO.getUserId());
														params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
														params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
			            String whereCondition = subQueryForVendors;
						
						Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
						queryForVendors.setFirstResult(requirementSubmissionsDTO.getStart());
						queryForVendors.setMaxResults(requirementSubmissionsDTO.getLength());
						for (String str : queryForVendors.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
								queryForVendors.setLong(str, (long) params.get( str ));
							}else{
								queryForVendors.setParameter( str, params.get( str ) );
							}
				        }
						
						List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
						 List<Long> sourceSubmissionIds = new ArrayList<Long>();
						 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
							 for(RequirementSubmission rs :vendorSubmittedApplicants){
									sourceSubmissionIds.add(rs.getId());
								}
							 //left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes
								String sourceSubmissionIdQuery = "from RequirementSubmission reqSub  where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  "; 
								List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString()+" "+searchQuery)
																					.setFirstResult(requirementSubmissionsDTO.getStart())
																					.setMaxResults(requirementSubmissionsDTO.getLength())
																					.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
																					.list();
								finalList.addAll(finalSourceList);
						   }
					}
					/// These Conditions are for  own Organization  Submitted Applicants
					if(requirementSubmissionsDTO.getLoginUserRole().equalsIgnoreCase("ROLE_ADMIN") || requirementSubmissionsDTO.getLoginUserRole().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
						subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId and reqSub.submissionStatus = '"+requirementSubmissionsDTO.getSubmissionType()+"'";
						params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
						params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
						
						// This Condition is if The Requirement is Released by the login User
					}else{
						//This is for Requirement Manger 
						String subQuery1 = "(reqSub.requirement.managerUserId.id  =:userId or reqSub.requirement.primaryRecruiterUserId.id  =:userId) ";
						params.put("userId",requirementSubmissionsDTO.getUserId());
						
						//This is for Requirement Created by
						String subQuery6 = "reqSub.requirement.createdBy =:requirementUsercode ";
						params.put("requirementUsercode",requirementSubmissionsDTO.getUserId());
						
						// This is if the user is  created by 
						String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
						params.put("usercode",requirementSubmissionsDTO.getUserCode());
						
						//  this is if the  user is   Assign Team  Owner
						String subQuery3 = "reqSub.requirement.id IN ( select rat.requirementId from RequirementAssignTo rat where rat.teamId.id IN ( select t.id from Team t where t.ownerUserId.id =:userId))";
						params.put("userId",requirementSubmissionsDTO.getUserId());
						
						 //  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
						String subQuery5 = "reqSub.requirement.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
						params.put("userId",requirementSubmissionsDTO.getUserId());
						
						if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery5 != "" || subQuery6 !="" ){
							subQuery = "where reqSub.submissionStatus = '"+requirementSubmissionsDTO.getSubmissionType()+"' and reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery6+")  OR ("+subQuery5+")   )";
							params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
							params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
						}
					}
					
					String whereCondition = subQuery;
					
					Query query = session.createQuery(from+" "+whereCondition+" "+searchQuery+" ORDER BY reqSub.id DESC");
					query.setFirstResult(requirementSubmissionsDTO.getStart());
					query.setMaxResults(requirementSubmissionsDTO.getLength());
					for (String str : query.getNamedParameters())
			        {
						if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
							query.setLong(str, (long) params.get( str ));
						}else{
							query.setParameter( str, params.get( str ) );
						}
			        }
					
					List<RequirementSubmission> ownApplicantsList = query.list() ;
				    finalList.addAll(ownApplicantsList);
				    requirementSubmissionList = finalList.stream().distinct().collect(Collectors.toList());
				    
				    int count = 0;
				    int filteredCount = 0;
				    Query countQuery = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
					for (String str : countQuery.getNamedParameters())
			        {
						if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
							countQuery.setLong(str, (long) params.get( str ));
						}else{
							countQuery.setParameter( str, params.get( str ) );
						}
			        }
					List<Long> countlist = countQuery.list();
					if(countlist != null && !countlist.isEmpty()) {
						count = countlist.size();
					}
					
					
					if (requirementSubmissionsDTO != null) {
						Query filteredCountQuery = session.createQuery(from+" "+whereCondition+" "+searchQuery+" ORDER BY reqSub.id DESC");
						for (String str : filteredCountQuery.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
								filteredCountQuery.setLong(str, (long) params.get( str ));
							}else{
								filteredCountQuery.setParameter( str, params.get( str ) );
							}
				        }
						List<Long> filteredCountlist = countQuery.list();
						if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
							filteredCount = filteredCountlist.size();
						}
					}
					response.setRecordsFiltered(filteredCount);
					response.setRecordsTotal(count);
			}else{
				System.out.println("else condition executed in DAO imple service ...................>");
			}
				response.setData(requirementSubmissionList);
				return response;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public ResponseList<RequirementSubmission> loadSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocations,CandidateVerificationConfig candidateVerificationConfig) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<RequirementSubmission> requirementSubmissionList = null;
			ResponseList<RequirementSubmission> response = new ResponseList<>();
			
			StringBuilder searchQuery = new StringBuilder("");
			if(requirementSubmissionsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(requirementSubmissionsDTO.getSearchValue())) {
				
				java.sql.Date sqlDate = null;
				 
				 Pattern pattern = Pattern.compile("^(([0-9]{0,4})[-{1}]([0-9]{0,4})[-{1}]([0-9]{0,4}))$");
				 Matcher matcher = pattern.matcher(requirementSubmissionsDTO.getSearchValue());
				 if (matcher.find()) {
					try {
						String searchValue = requirementSubmissionsDTO.getSearchValue();
						DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
						LocalDate parsedDate = LocalDate.parse(searchValue, f);
						DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String newDate = parsedDate.format(f2);
						Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
						sqlDate = new java.sql.Date(searchDate.getTime()); 
						searchQuery.append("and (reqSub.createdDate like '" + sqlDate + "%' )");
					 }catch (Exception e) {
						 searchQuery.append("");
					 }
				}else {
		 searchQuery.append(" and (reqSub.candidate.firstName like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidate.lastName like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR (concat(reqSub.candidate.firstName,' ',reqSub.candidate.lastName) like '%" + requirementSubmissionsDTO.getSearchValue().trim().replaceAll(" +", " ")+ "%')" )
					.append("OR reqSub.candidate.email like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidate.mobilePhone like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidate.expectedRate like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.createdUser like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidateScore like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.candidate.experienceYears like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ")
					.append("OR reqSub.submissionStatus like '%" + requirementSubmissionsDTO.getSearchValue() + "%' ");
				   
					/*String[] nameBytes = requirementSubmissionsDTO.getSearchValue().split(" ");
					for(String nameByte : nameBytes) {
						searchQuery.append("OR reqSub.candidate.firstName like '%" + nameByte + "%' ")
						.append("OR reqSub.candidate.lastName like '%" + nameByte + "%' ");
						
					}*/
					searchQuery.append(" )");
					}
				}
			
			if(Constants.All_APPLICANTS.equalsIgnoreCase(requirementSubmissionsDTO.getSubmissionType())){
				Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementSubmissionsDTO.getRequirementId());
				String[] releasedBy =null;
				
				ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
				
				if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
					releasedBy = requirement.getReleasedBy().split(",");
				}
		        Map<String, Object> params = new HashMap<String,Object>();
		        //left join fetch reqSub.requirement req left join fetch reqSub.candidate reqSubCandidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes
				String from = "from RequirementSubmission reqSub  "; 
				String subQuery = "";
				// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
					if(null != releasedBy && releasedBy.length > 0){
						
						String subQueryForVendors = "where reqSub.requirement.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
														params.put("userId",requirementSubmissionsDTO.getUserId());
														params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
														params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
			            String whereCondition = subQueryForVendors;
						
						Query queryForVendors = session.createQuery(from+" "+whereCondition+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
						queryForVendors.setFirstResult(requirementSubmissionsDTO.getStart());
						queryForVendors.setMaxResults(requirementSubmissionsDTO.getLength());
						for (String str : queryForVendors.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
								queryForVendors.setLong(str, (long) params.get( str ));
							}else{
								queryForVendors.setParameter( str, params.get( str ) );
							}
				        }
						
						List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
						 List<Long> sourceSubmissionIds = new ArrayList<Long>();
						 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
							 for(RequirementSubmission rs :vendorSubmittedApplicants){
									sourceSubmissionIds.add(rs.getId());
								}
							 //left join fetch reqSub.requirement req   left join fetch reqSub.candidate left join fetch reqSub.sourceSubmission left join fetch reqSub.candidateResumes
								String sourceSubmissionIdQuery = "from RequirementSubmission reqSub  where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  "; 
								List<RequirementSubmission> finalSourceList = session.createQuery(sourceSubmissionIdQuery.toString()+" "+searchQuery)
																					.setFirstResult(requirementSubmissionsDTO.getStart())
																					.setMaxResults(requirementSubmissionsDTO.getLength())
																					.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
																					.list();
								finalList.addAll(finalSourceList);
						   }
					}
					/// These Conditions are for  own Organization  Submitted Applicants
					if(requirementSubmissionsDTO.getLoginUserRole().equalsIgnoreCase("ROLE_ADMIN") || requirementSubmissionsDTO.getLoginUserRole().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
						subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
						params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
						params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
						
						// This Condition is if The Requirement is Released by the login User
					}else{
						//This is for Requirement Manger 
						String subQuery1 = "(reqSub.requirement.managerUserId.id  =:userId or reqSub.requirement.primaryRecruiterUserId.id  =:userId) ";
						params.put("userId",requirementSubmissionsDTO.getUserId());
						
						//This is for Requirement Created by
						String subQuery6 = "reqSub.requirement.createdBy =:requirementUsercode ";
						params.put("requirementUsercode",requirementSubmissionsDTO.getUserId());
						
						// This is if the user is  created by 
						String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
						params.put("usercode",requirementSubmissionsDTO.getUserCode());
						
						//  this is if the  user is   Assign Team  Owner
						String subQuery3 = "reqSub.requirement.id IN ( select rat.requirementId from RequirementAssignTo rat where rat.teamId.id IN ( select t.id from Team t where t.ownerUserId.id =:userId))";
						params.put("userId",requirementSubmissionsDTO.getUserId());
						
						 //  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
						String subQuery5 = "reqSub.requirement.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
						params.put("userId",requirementSubmissionsDTO.getUserId());
						
						if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery5 != "" || subQuery6 !="" ){
							subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery6+")  OR ("+subQuery5+")   )";
							params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
							params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
						}
					}
					
					String whereCondition = subQuery;
					
					Query query = session.createQuery(from+" "+whereCondition+" "+searchQuery+" ORDER BY reqSub.id DESC");
					query.setFirstResult(requirementSubmissionsDTO.getStart());
					query.setMaxResults(requirementSubmissionsDTO.getLength());
					for (String str : query.getNamedParameters())
			        {
						if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
							query.setLong(str, (long) params.get( str ));
						}else{
							query.setParameter( str, params.get( str ) );
						}
			        }
					
					List<RequirementSubmission> ownApplicantsList = query.list() ;
				    finalList.addAll(ownApplicantsList);
				    requirementSubmissionList = finalList.stream().distinct().collect(Collectors.toList());
				    
				    int count = 0;
				    int filteredCount = 0;
				    Query countQuery = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
					for (String str : countQuery.getNamedParameters())
			        {
						if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
							countQuery.setLong(str, (long) params.get( str ));
						}else{
							countQuery.setParameter( str, params.get( str ) );
						}
			        }
					List<Long> countlist = countQuery.list();
					if(countlist != null && !countlist.isEmpty()) {
						count = countlist.size();
					}
					
					
					if (requirementSubmissionsDTO != null) {
						Query filteredCountQuery = session.createQuery(from+" "+whereCondition+" "+searchQuery+" ORDER BY reqSub.id DESC");
						for (String str : filteredCountQuery.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
								filteredCountQuery.setLong(str, (long) params.get( str ));
							}else{
								filteredCountQuery.setParameter( str, params.get( str ) );
							}
				        }
						List<Long> filteredCountlist = countQuery.list();
						if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
							filteredCount = filteredCountlist.size();
						}
					}
					response.setRecordsFiltered(filteredCount);
					response.setRecordsTotal(count);
			}else{
					String statusSubQuery = "";
					String emailSubQuery = "";
					String statusSubQuery1 = "";
					String statusSubQuery2 = "";
					String statusSubQuery3 = "";
					String statusSubQuery4 = "";
					if(requirementSubmissionsDTO.getSubmissionType() != null && "INTERVIEW".equalsIgnoreCase(requirementSubmissionsDTO.getSubmissionType())){
						statusSubQuery = " and (reqSub.submissionStatus = 'INTERVIEW' OR reqSub.submissionStatus = 'CLIENT_INTERVIEW' OR reqSub.submissionStatus = 'SELECTED_ON_INTERVIEW' OR reqSub.submissionStatus = 'INTERVIEW_ACCEPT') "; 
					}else if(requirementSubmissionsDTO.getSubmissionType() != null && "OFFERMADE".equalsIgnoreCase(requirementSubmissionsDTO.getSubmissionType())){
						statusSubQuery = " and (reqSub.submissionStatus = 'OFFERMADE' OR reqSub.submissionStatus = 'OFFERACCEPTED' OR reqSub.submissionStatus = 'OFFERREJECTED' "
								+ " OR reqSub.submissionStatus = 'CLIENT_OFFERMADE' OR reqSub.submissionStatus = 'CLIENT_OFFERACCEPTED' OR reqSub.submissionStatus = 'CLIENT_OFFERREJECTED') "; 
					}else if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(requirementSubmissionsDTO.getSubmissionType())){
						emailSubQuery = "reqSub.candidate.email IN(select tempReqSub.candidate.email from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.email<>'')";
						if(candidateVerificationConfig !=null) {
						if(candidateVerificationConfig.getAadharNumber() == true){
							statusSubQuery1 = " or reqSub.candidate.aadharNumber IN(select tempReqSub.candidate.aadharNumber from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.aadharNumber<>'')";
						}
						if(candidateVerificationConfig.getPanNumber() == true){
							statusSubQuery2 = " or reqSub.candidate.panNumber IN(select tempReqSub.candidate.panNumber from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.panNumber<>'')";
						}
						 if(candidateVerificationConfig.getMobilePhone()== true){
							statusSubQuery3 = " or reqSub.candidate.mobilePhone IN(select tempReqSub.candidate.mobilePhone from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.mobilePhone<>'')";
						}
						if(candidateVerificationConfig.getSsnNumber()== true){
							statusSubQuery4 = " or reqSub.candidate.ssnNumber IN(select tempReqSub.candidate.ssnNumber from RequirementSubmission tempReqSub where tempReqSub.requirement.id =:requirementId  and tempReqSub.id <> reqSub.id and tempReqSub.candidate.ssnNumber<>'')";
						}
						}
						
						statusSubQuery = " and reqSub.submissionStatus <> 'REJECTED' and ("+emailSubQuery+statusSubQuery1+statusSubQuery2+statusSubQuery3+statusSubQuery4+")";
					}
					
					else{
						statusSubQuery = " and reqSub.submissionStatus = '"+requirementSubmissionsDTO.getSubmissionType()+"' ";
					}
					
					Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementSubmissionsDTO.getRequirementId());
					String[] releasedBy =null;
					
					ArrayList<RequirementSubmission> finalList  = new ArrayList<RequirementSubmission>();
					
					if(!"".equalsIgnoreCase(requirement.getReleasedBy()) && requirement.getReleasedBy()!= null ){
						releasedBy = requirement.getReleasedBy().split(",");
					}
					 
			        Map<String, Object> params = new HashMap<String,Object>();
					String from = "from RequirementSubmission reqSub "; 
					String subQuery = "";
					
					
					// These conditions are to check  for vendors  Submitted Applicants  and Requirement who Released
						if(null != releasedBy && releasedBy.length > 0){
							
							String subQueryForVendors = "where reqSub.requirement.id IN ( select rq.id from Requirement rq where     rq.parentRequirement.id =:requirementId and  rq.spocId.id IN ( select orgSpock.id from OrgSpoc orgSpock  where  orgSpock.clientUser.id =:userId and orgSpock.clientLocationId.id =:loginOrgLocation )   and reqSub.id IN( select sourceSub.sourceSubmission.id from   RequirementSubmission sourceSub    ) ) ";
															params.put("userId",requirementSubmissionsDTO.getUserId());
															params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
															params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
							
				            String whereCondition = subQueryForVendors;
							
							Query queryForVendors = session.createQuery(from+" "+whereCondition+" "+searchQuery+"  GROUP BY reqSub.id  ORDER BY reqSub.id DESC");
							queryForVendors.setFirstResult(requirementSubmissionsDTO.getStart());
							queryForVendors.setMaxResults(requirementSubmissionsDTO.getLength());
							for (String str : queryForVendors.getNamedParameters())
					        {
								if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") ){
									queryForVendors.setLong(str, (long) params.get( str ));
								}else{
									queryForVendors.setParameter( str, params.get( str ) );
								}
					        }
							List<RequirementSubmission> vendorSubmittedApplicants = queryForVendors.list() ; 
							 List<Long> sourceSubmissionIds = new ArrayList<Long>();
							 if(null != vendorSubmittedApplicants && vendorSubmittedApplicants.size() >0 ){
								 for(RequirementSubmission rs :vendorSubmittedApplicants){
										sourceSubmissionIds.add(rs.getId());
									}
									String sourceSubmissionIdQuery = "from RequirementSubmission reqSub where  reqSub.sourceSubmission.id  IN (:sourceSubmissionIds)  " +statusSubQuery; 
									List<RequirementSubmission> finalSourceList = null;
											if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(requirementSubmissionsDTO.getSubmissionType())){
												finalSourceList =		session.createQuery(sourceSubmissionIdQuery.toString())
														.setFirstResult(requirementSubmissionsDTO.getStart())
														.setMaxResults(requirementSubmissionsDTO.getLength())
														.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
												.setLong("requirementId",requirementSubmissionsDTO.getRequirementId()).list();
											}else{
												finalSourceList =	session.createQuery(sourceSubmissionIdQuery.toString())
														.setFirstResult(requirementSubmissionsDTO.getStart())
														.setMaxResults(requirementSubmissionsDTO.getLength())
														.setParameterList("sourceSubmissionIds", sourceSubmissionIds)
												.list();
											}
									finalList.addAll(finalSourceList);
							   }
						}
							// These Conditions are for  own Organization  Submitted Applicants
						if(requirementSubmissionsDTO.getLoginUserRole().equalsIgnoreCase("ROLE_ADMIN") || requirementSubmissionsDTO.getLoginUserRole().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
							subQuery = " where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId ";
							params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
							params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
							// This Condition is if The Requirement is Released by the login User
						}else{
							//This is for Requirement Manger 
							String subQuery1 = "(reqSub.requirement.managerUserId.id  =:userId or reqSub.requirement.primaryRecruiterUserId.id  =:userId) ";
							params.put("userId",requirementSubmissionsDTO.getUserId());
							//This is for Requirement Created by
							String subQuery6 = "reqSub.requirement.createdBy =:requirementUsercode ";
							params.put("requirementUsercode",requirementSubmissionsDTO.getUserId());
							// This is if the user is  created by 
							String subQuery2 = "((reqSub.createdBy =:usercode) OR (reqSub.candidate.candidateType = 'Professional') OR (reqSub.recruiterUserId IS NOT NULL))";
							params.put("usercode",requirementSubmissionsDTO.getUserCode());
							//  this is if the  user is   Assign Team  Owner
							String subQuery3 = "reqSub.requirement.id IN ( select rat.requirementId from RequirementAssignTo rat where rat.teamId.id IN ( select t.id from Team t where t.ownerUserId.id =:userId))";
							params.put("userId",requirementSubmissionsDTO.getUserId());
							//  this is if the  user is   ApproverHirachy   Member  from Requirement i,e login user is approver for given Requirement
							String subQuery5 = "reqSub.requirement.id IN ( select reqApproverHirachy.requirementId from RequirementApproverHierarchy reqApproverHirachy where reqApproverHirachy.approverUserId.id =:userId)";
							params.put("userId",requirementSubmissionsDTO.getUserId());
							if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery5 != "" || subQuery6 !=""  ){
								subQuery = "where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId  and  ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+")  OR ("+subQuery5+")    )";
								params.put("loginOrgLocation",requirementSubmissionsDTO.getOrgLocationId());
								params.put("requirementId",requirementSubmissionsDTO.getRequirementId());
							}
						}
						String whereCondition = "";
						if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(requirementSubmissionsDTO.getSubmissionType())){
							 whereCondition = subQuery+statusSubQuery;
						}else {
							 whereCondition = subQuery+statusSubQuery;	
						}
						
						Query query = session.createQuery(from+" "+whereCondition+" "+searchQuery+" ORDER BY reqSub.id DESC");
						query.setFirstResult(requirementSubmissionsDTO.getStart());
						query.setMaxResults(requirementSubmissionsDTO.getLength());
						for (String str : query.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
								query.setLong(str, (long) params.get( str ));
							}else{
								query.setParameter( str, params.get( str ) );
							}
				        }
						List<RequirementSubmission> ownApplicantsList = query.list() ;
					    finalList.addAll(ownApplicantsList);
					    requirementSubmissionList = finalList.stream().distinct().collect(Collectors.toList());
					    
					    int count = 0;
					    int filteredCount = 0;
					    Query countQuery = session.createQuery(from+" "+whereCondition+" ORDER BY reqSub.id DESC");
						for (String str : countQuery.getNamedParameters())
				        {
							if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
								countQuery.setLong(str, (long) params.get( str ));
							}else{
								countQuery.setParameter( str, params.get( str ) );
							}
				        }
						List<Long> countlist = countQuery.list();
						if(countlist != null && !countlist.isEmpty()) {
							count = countlist.size();
						}
						
						if (requirementSubmissionsDTO != null) {
							Query filteredCountQuery = session.createQuery(from+" "+whereCondition+" "+searchQuery+" ORDER BY reqSub.id DESC");
							for (String str : filteredCountQuery.getNamedParameters())
					        {
								if(str.equalsIgnoreCase("requirementId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("loginOrgLocation") || str.equalsIgnoreCase("requirementUsercode")){
									filteredCountQuery.setLong(str, (long) params.get( str ));
								}else{
									filteredCountQuery.setParameter( str, params.get( str ) );
								}
					        }
							List<Long> filteredCountlist = countQuery.list();
							if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
								filteredCount = filteredCountlist.size();
							}
						}
						response.setRecordsFiltered(filteredCount);
						response.setRecordsTotal(count);
				}
				response.setData(requirementSubmissionList);
				return response;
		}

		@Override
		@Transactional
		public List<RequirementSubmission> getSubmmisionsForIsClientJob(List<Long> submissionIds) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			String query =" from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.orgLocations left join fetch rs.candidateResumes where rs.sourceSubmission.id IN (:submissionIds)";
			List<RequirementSubmission>  subList = session.createQuery(query).setParameterList("submissionIds", submissionIds).list();
		    return subList;
		}

		@Override
		@Transactional
		public RequirementSubmission getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(
				Long requirementId, Long candidateId) throws Exception {
			

			 Session session = this.sessionFactory.getCurrentSession();
		     String query = "from RequirementSubmission rs left join fetch rs.candidateResumes left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.vendorCandidateId "
		     		+ "where rs.requirement.id = :requirementId and rs.vendorCandidateId.id =:candidateId ";
		     List<RequirementSubmission> submissionList = session.createQuery(query).setLong( "requirementId", requirementId).setLong( "candidateId", candidateId).list();
		   
		     if(submissionList != null && submissionList.size() >0){
					return submissionList.get(0);
				}
		  return null;
	
		}
		
		@Override
		@Transactional
		public boolean getRequirementSubmissionByCandidateIdAndJobId(Long candidateId, Long requirementId)
				throws Exception {
			
			List<RequirementSubmission> submissionList = null;
			boolean flag = false;
			Session session = this.sessionFactory.getCurrentSession();
			String query = null;
						query ="SELECT * FROM "
						+" REQUIREMENT_SUBMISSION "
						+" WHERE "
						+" CANDIDATE_ID = :candidateId AND REQUIREMENT_ID = :requirementId";
			    
	            Query queryObj = session.createSQLQuery(query);
				queryObj.setLong("candidateId", candidateId).setLong("requirementId", requirementId);
				submissionList = queryObj.list();
				if(submissionList != null && !submissionList.isEmpty()) {
					flag = true;
				}
				
				return flag;
		}
		
		

		
				@Override
				@Transactional
				public Integer []  getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(
				Long orgLocationsId, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate) throws Exception {
				Object []  counts = null;
				Integer [] countArr = new Integer[4];
				String dateQury;
				String  startDate =commonUtil.elkDateFormatter(startUTCDate);
				String  endDate  = commonUtil.elkDateFormatter(endUTCDate);
				
				List<String> userList = userDao.getUserHierarchyUserCodeList(user.getId());
					  
				if(dateRange != null && "today".equals(dateRange)){
				dateQury = " and DATE_FORMAT(rsh.UPDATED_DATE,'%Y-%m-%d') BETWEEN  '"+startDate +"' and '"+endDate+"'";
				}else {
				dateQury = " and DATE_FORMAT(rsh.UPDATED_DATE,'%Y-%m-%d')  BETWEEN  '"+startDate +"' and '"+endDate+"'";
				}
				String commanQury =  "select SUM(CASE "+
						" WHEN UPPER(rsh.TO_STAGE) = 'SUBMISSION' THEN 1 "+
						" ELSE 0 "+
					" END) AS APPLICANT_COUNT, "+
					" SUM(CASE "+
						" WHEN UPPER(rsh.TO_STAGE) = 'INTERVIEW' THEN 1 "+
						" WHEN UPPER(rsh.TO_STAGE) = 'CLIENT_INTERVIEW' THEN 1 "+
						" ELSE 0 "+
					" END) AS CLIENT_INTERVIEW_COUNT, "+
					 " SUM(CASE "+
						" WHEN UPPER(rsh.TO_STAGE) = 'SUBMIT_TO_CLIENT' THEN 1 "+
						" ELSE 0 "+
					" END) AS SUBMISSION_COUNT,"+
					" SUM(CASE"+
					   " WHEN UPPER(rsh.TO_STAGE) = 'OFFERMADE' THEN 1 "+
					   " WHEN UPPER(rsh.TO_STAGE) = 'OFFERACCEPTED' THEN 1 "+
				   " WHEN UPPER(rsh.TO_STAGE) = 'OFFERREJECTED' THEN 1 "+
					   " WHEN UPPER(rsh.TO_STAGE) = 'CLIENT_OFFERMADE' THEN 1 "+
					   " WHEN UPPER(rsh.TO_STAGE) = 'CLIENT_OFFERACCEPTED' THEN 1 "+
					   " WHEN UPPER(rsh.TO_STAGE) = 'CLIENT_OFFERREJECTED' THEN 1 "+
					   " ELSE 0 "+
					" END) AS OFFER_COUNT ";
				try {
						Session session = this.sessionFactory.getCurrentSession();
						if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
						 String query1 = commanQury +
					 " FROM REQUIREMENT_SUBMISSION_HISTORY rsh, "+ 
					 " REQUIREMENT_SUBMISSION rs, "+
					 " REQUIREMENT req, "+
					 " ORG_LOCATIONS ol "+
					 " where rsh.REQUIREMENT_SUBMISSION_ID = rs.ID and "+
							   " rs.ORG_LOCATION_ID = ol.ID "+
					   " and rs.ORG_LOCATION_ID = "+orgLocationsId+
							   " and rsh.TO_STAGE IN('SUBMISSION','INTERVIEW','SUBMIT_TO_CLIENT','CLIENT_INTERVIEW','OFFERMADE','OFFERACCEPTED','OFFERREJECTED','CLIENT_OFFERMADE','CLIENT_OFFERACCEPTED','CLIENT_OFFERREJECTED')"+
					   " and rs.STATUS = 1 "+
					   " and rs.REQUIREMENT_ID = req.ID "+
					   " and rs.REQUIREMENT_ID IS not NULL "+ dateQury +
					   //login user as created by
					   " and ((req.ID IN(select ID from REQUIREMENT  where CREATED_BY ="+user.getId()+")" + 
					   
					   //login user as a approver for this requirement  or  assigned to.
					   " OR (req.ID IN(select rah. REQUIREMENT_ID from REQUIREMENT_APPROVER_HIERARCHY rah where      rah.APPROVER_USER_ID = "+user.getId()+") " + 
					   "OR (req.ID IN(select rah.REQUIREMENT_ID from REQUIREMENT_ASSIGN_TO rah where rah.TEAM_ID IN(select team.ID from TEAM team where team.OWNER_USER_ID = "+user.getId()+"))) " + 
					   "OR (req.ID IN(select REQUIREMENT_ID from REQUIREMENT_ASSIGN_TO rat where rat.ASSIGN_USER_ID ="+user.getId()+") and rs.CREATED_BY = '"+user.getUserCode()+"'))" + 
					   
				 //login user as a  manager for this job.
					   "OR (req.ID IN(select ID from REQUIREMENT requirement where  requirement.MANAGER_USER_ID = "+user.getId()+" OR requirement.PRIMARY_RECRUITER_USER_ID = "+user.getId()+"))" + 
					   
					   "OR(req.RELEASE_JOB_TO_ALL_USERS = 'Y' and rs.CREATED_BY = '"+user.getUserCode()+"') )";
					   
					   if(userList.size() > 0) {
						   query1 = query1 + " OR (rsh.CREATED_BY IN (:userList) )";
					   }
					   query1 = query1 + ")" ;
					  
					   if(userList.size() > 0) {
						   Query queryObj = session.createSQLQuery(query1);
							queryObj.setParameterList("userList", userList);
							counts = (Object[]) queryObj.uniqueResult();
					   }else {
						   counts =   (Object[]) session.createSQLQuery(query1).uniqueResult();
					   }
					 
					for (int i = 0; i < counts.length; i++) {
					 Object obj = counts[i];
					 if(obj == null) {
						 countArr[i] =0;
					 }else {
						 countArr[i] = ((BigDecimal)obj).intValue();
					 }
				  }
				}else{
					 String query1 = commanQury +
					 " FROM REQUIREMENT_SUBMISSION_HISTORY rsh, "+ 
					 " REQUIREMENT_SUBMISSION rs, "+
					 " REQUIREMENT req, "+
					 " ORG_LOCATIONS ol "+
					 " where rsh.REQUIREMENT_SUBMISSION_ID = rs.ID and "+
							   " rs.ORG_LOCATION_ID = ol.ID "+
					   " and rs.ORG_LOCATION_ID = "+orgLocationsId+
							   " and rsh.TO_STAGE IN('SUBMISSION','INTERVIEW','SUBMIT_TO_CLIENT','CLIENT_INTERVIEW','OFFERMADE','OFFERACCEPTED','OFFERREJECTED','CLIENT_OFFERMADE','CLIENT_OFFERACCEPTED','CLIENT_OFFERREJECTED')"+
					   " and rs.STATUS = 1 "+
					   " and rs.REQUIREMENT_ID = req.ID "+
					   " and rs.REQUIREMENT_ID IS not NULL "+ dateQury ;
					  
					 counts =   (Object[]) session.createSQLQuery(query1).uniqueResult();
					 for (int i = 0; i < counts.length; i++) {
						Object obj = counts[i];
						 if(obj == null) {
							 countArr[i] =0;
						 }else {
							 countArr[i] = ((BigDecimal)obj).intValue();
						 }
					  }
					}
				} catch (Exception e) {
				e.printStackTrace();
				}
				return countArr;
				}
				
				@Override
				@Transactional
				public List<RequirementSubmission> getAllSubmissionsNotInTemplateFileds() throws Exception {
					 Session session = this.sessionFactory.getCurrentSession();
					 
				     String query = "from RequirementSubmission rs where rs.id NOT IN (select rsf.requirementSubmission.id from RequirementSubmissionTemplateFields rsf)";
				     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).list();
				     return shortListCandidatesList;
				}
				
				@Override
				@Transactional
				public Integer getRequirementSubmissionCountByCandiateIdAndOrgLocationId(Long candidateId,
						Long orgLocationId) throws Exception {
					
					
					List<Long> submissionsList = null;
					Session session = this.sessionFactory.getCurrentSession();
					String query = "select distinct count(rs.requirement.id)  from RequirementSubmission rs  where rs.candidate.id = :candidateId and rs.orgLocations.id = :orgLocationId ";  
					submissionsList = session.createQuery(query)
													       .setLong("orgLocationId", orgLocationId)
													       .setLong("candidateId", candidateId)
													       .list();
					
					  Integer count = 0;
				  		if(submissionsList != null && !submissionsList.isEmpty()){
				  				count = submissionsList.get(0).intValue();
				  		}
			  		return count;
				}
				
				@Override
				@Transactional
				public List<RequirementSubmissionsDTO> getCandidateSubmittedRequirementsListByCandidateId(
						Long candidateId, Long orgLocationId,String dateFormat) throws Exception {
					
				    List<RequirementSubmissionsDTO> requirementSubmissionsDTOList = null;
					Session session = this.sessionFactory.getCurrentSession();
					String query = null;
					
				    String locationDateFormat = null;
					 if(dateFormat.equalsIgnoreCase("yyyy-MM-dd")){
						 locationDateFormat = "%Y-%m-%d";
					  }if(dateFormat.equalsIgnoreCase("dd-MM-yyyy")){
						  locationDateFormat = "%d-%m-%Y";
					  }if(dateFormat.equalsIgnoreCase("MM-dd-yyyy")){
						  locationDateFormat = "%m-%d-%Y";
					  }if(dateFormat.equalsIgnoreCase("yyyy-dd-MM")){
						  locationDateFormat = "%Y-%d-%m";
					  }
					  
					query =  "SELECT "
							+" REQ.ID AS reqId, "
							+" REQ.GENERATED_ID AS generatedId,"  
							+" REQ.JOB_TITLE AS requirementName, "
							+" SUBMISSION.SUBMISSION_STATUS AS submissionStatus, "
							+" DATE_FORMAT(SUBMISSION.CREATED_DATE, '"+locationDateFormat+"') AS submissionDate, "
							+" DATE_FORMAT(SUBMISSION.CREATED_DATE, '%h:%i:%s %p') AS submissionTime, "
							+ " (SELECT " + 
							  " CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) " + 
							  " FROM " + 
							  " USER U " + 
							  " WHERE " + 
							  " U.USER_CODE = SUBMISSION.CREATED_BY) AS submittedBy " 
							+" FROM REQUIREMENT_SUBMISSION SUBMISSION, "
							+ "REQUIREMENT REQ "
							+" WHERE SUBMISSION.REQUIREMENT_ID = REQ.ID  AND"
							+" SUBMISSION.CANDIDATE_ID = :candidateId AND "
							+" SUBMISSION.ORG_LOCATION_ID = :orgLocationId ORDER BY SUBMISSION.ID DESC";
					
					Query queryObj = session.createSQLQuery(query);
					queryObj.setLong("candidateId", candidateId);
					queryObj.setLong("orgLocationId", orgLocationId);
					queryObj.setResultTransformer(Transformers.aliasToBean(RequirementSubmissionsDTO.class));
					requirementSubmissionsDTOList = queryObj.list();
					
					return requirementSubmissionsDTOList;
				}
				
				
				@Override
				@Transactional(rollbackFor = { Exception.class })
				public Integer getRequirementCountBySubmissionStatusJoinedandAssignedUsercode(Long requirementId, String userCode,Date[] searchDates) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
					String dateQury = null;
					java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
					java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
					java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
					java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
					dateQury = " and DATE_FORMAT(rs.createdDate,'%Y-%m-%d') BETWEEN  '" + fromDate+ "' and '" + toDate + "'";		
					String query ="select count(rs.requirement.id)from RequirementSubmission rs where rs.createdBy =:userCode and rs.submissionStatus='JOINED' " +dateQury+ " GROUP BY createdBy";
					List<Long> submissionCount =   session.createQuery(query.toString()).setString("userCode", userCode)
							                                                            .list();
						Integer count= 0;
						if(submissionCount != null && !submissionCount.isEmpty()){
								count = submissionCount.get(0).intValue();
						}
						return count;
				}
				
			/*	@SuppressWarnings("unchecked")
				@Override
				@Transactional
				public List<RequirementSubmission> getApplicantsJoinedListIncentives(List<String> userCodeList, Long orgLocationId, Date[] searchDates) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
					java.sql.Timestamp fromDate = new java.sql.Timestamp(searchDates[0].getTime());
					 java.sql.Timestamp toDate = new java.sql.Timestamp(searchDates[1].getTime());
					 java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime()); 
					 java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime()); 
					 String dateQury;
//						dateQury = " and DATE_FORMAT(rsh.UPDATED_DATE,'%Y-s%m-%d') BETWEEN  '"+startDate +" ' and '"+endDate+"'";

						dateQury = " AND DATE_FORMAT(rs.CREATED_DATE,'%Y-%m-%d') BETWEEN  '"+sqlFromDate+"' and  '"+sqlToDate+"' ";

				     String query = "select rs from  RequirementSubmission rs where rs.requirement.id AND rs.createdBy IN (:userCodeList) AND rs.orgLocations.id=:orgLocationId  "+dateQury+" ";

				     String query = "from RequirementSubmission rs left join fetch rs.requirement where rs.createdBy IN (:userCodeList) AND rs.orgLocations=:orgLocationId AND date(rs.createdDate) BETWEEN :fromDate and :toDate";
				     Query queryObj = session.createQuery(query);
				     queryObj.setParameterList("userCodeList", userCodeList);
					queryObj.setLong("orgLocationId", orgLocationId);
					queryObj.setParameter("fromDate",sqlFromDate);
					queryObj.setParameter("toDate",sqlToDate);
				     List<RequirementSubmission> applicantJoinedList = queryObj.list();
				     return applicantJoinedList;
				}*/
				@Override
				public RequirementIncentive getRequirementIncentiveByRequirementId(Long requirementId) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
				     String query = "from RequirementIncentive rs  where rs.requirementId=:requirementId";
					List<RequirementIncentive> requirementIncentiveList =  session.createQuery(query.toString()).setLong("requirementId", requirementId).list();
					RequirementIncentive requirementIncentive = null;
					if (requirementIncentiveList != null && requirementIncentiveList.size() > 0) {
						requirementIncentive = (RequirementIncentive) requirementIncentiveList.iterator().next();
					}
					return requirementIncentive;
				}
				@Override
				@Transactional
				public List<ApplicantsJoinedReportDTO> getRequirementIncentiveByRequirementIdAndLocation(Long requirementId,String usercode, Long orgLocationId, Date[] searchDates) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
					 Query queryString = null;
					 List<ApplicantsJoinedReportDTO> applicantJoinedReportList = null;
					 java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
						java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
						java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
						java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
		String query = " SELECT reqInst.REQUIREMENT_ID as requirementID,"
				+ " reqSub.ID as reqSubId," + " reqInst.JOINERS_AMOUNT_TYPE as incentAmountType,"
				+ "reqInst.JOINERS as incentJoiners," + " reqSub.ORG_LOCATION_ID as orglocationId,"
				+ "reqInst.JOINERS_AMOUNT as incentAmount," + "reqSub.FINALIZED_RATE finalRate,"
				+ "reqOff.AMOUNT as OfferAmount," + " reqSub.CREATED_BY createdBy"
				+ " from REQUIREMENT_SUBMISSION reqSub, REQUIREMENT_INCENTIVE reqInst, REQUIREMENT_OFFERS reqOff "
				+ "where reqSub.REQUIREMENT_ID=reqInst.REQUIREMENT_ID and reqSub.SUBMISSION_STATUS = 'JOINED' and reqOff.SUBMISSION_ID = reqSub.ID and reqSub.ORG_LOCATION_ID='"
				+ orgLocationId + "' and reqSub.CREATED_BY='" + usercode
				+"' and DATE_FORMAT(reqSub.UPDATED_DATE,'%Y-%m-%d') BETWEEN  '" + fromDate
				+ "' and '" + toDate + ""
				+ "' group by reqInst.JOINERS ";
				     queryString =  session.createSQLQuery(query.toString());
					ApplicantsJoinedReportDTO applicantsJoinedReportDTO = null;
//					if (applicantsJoinedReportDTOList != null && applicantsJoinedReportDTOList.size() > 0) {
//						applicantsJoinedReportDTO = (ApplicantsJoinedReportDTO) applicantsJoinedReportDTOList.iterator().next();
//					}
	                queryString.setResultTransformer(Transformers.aliasToBean(ApplicantsJoinedReportDTO.class));
	                applicantJoinedReportList = queryString.list();
					return applicantJoinedReportList;
				}

				@Override
				@Transactional
				public ResponseList<AssociateJoineReportDTO> getAssociateJoinedGraph(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
					 Query queryString = null;
					 ResponseList<AssociateJoineReportDTO> associateJoineReportDTO = new ResponseList<>();
		             List<AssociateJoineReportDTO> associateJoineReportDTOList = null;
					 java.sql.Timestamp fromDate1 = new java.sql.Timestamp(startUTCDate.getTime());
						java.sql.Timestamp toDate1 = new java.sql.Timestamp(endUTCDate.getTime());
						java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
						java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
						OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationsId);
					    SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					    Date date = null;
					    java.sql.Date sqlDate = null;
						Query countQueryString;
						List<Long> countlist = null;
						String sortingQry = "";
					 String query="SELECT emp.EMPLOYEE_ID as employeeId, emp.USER_ID as userId,emp.ORG_LOCATIONS_ID as orgLocationsId, emp.HIRE_DATE as hireDate, urole.TERMINATE_DATE as terminateDate,urole.USER_TYPE as userRole,"
+" emp.EMPLOYMENT_TYPE as empType,user.GENDER as gender, COUNT(urole.TERMINATE_DATE) as terminatedCount,COUNT(emp.HIRE_DATE) as hiredCount, "
+" COUNT(case when emp.EMPLOYMENT_TYPE='Full Time' then 1 end) as empTypeFullTimeCount,"
+"COUNT(case when emp.EMPLOYMENT_TYPE='Part Time' then 1 end) as empTypePartTimeCount,"
+"COUNT(case when user.GENDER='Male' and  emp.EMPLOYMENT_TYPE='Full Time' then 1 end) as ftMaleCnt,"
+"COUNT(case when user.GENDER='Female' and  emp.EMPLOYMENT_TYPE='Full Time' then 1 end) as ftFemaleCnt,"
+"COUNT(case when user.GENDER='Male' and  emp.EMPLOYMENT_TYPE='Part Time' then 1 end) as ptMaleCnt,"
+"COUNT(case when user.GENDER='Female' and  emp.EMPLOYMENT_TYPE='Part Time' then 1 end) as ptFemaleCnt"
+" from EMPLOYEE emp, USER_ROLE urole, USER user "
 +" where urole.USER_ID = emp.USER_ID and user.ID = emp.USER_ID "
+" and urole.ORG_LOCATIONS_ID = emp.ORG_LOCATIONS_ID and  emp.ORG_LOCATIONS_ID = '"
				+ orgLocationsId + "' "
						+" and (DATE_FORMAT(urole.TERMINATE_DATE,'%Y-%m-%d') BETWEEN  '" + fromDate
						+ "' and '" + toDate + " ' or  DATE_FORMAT(emp.HIRE_DATE,'%Y-%m-%d') BETWEEN  '" + fromDate
						+ "' and '" + toDate + " ')";
					 StringBuilder query1 = new StringBuilder();
					 if (StringUtils.isNotBlank(requirementSubmissionHistoryDTO.getSearchValue())) {
						 
					       Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			                Matcher matcher = pattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
			                if (matcher.matches() == true) {
			                    if (requirementSubmissionHistoryDTO.getSearchValue().contains("/")
			                            || (requirementSubmissionHistoryDTO.getSearchValue().contains("-"))) {
			                        String replacedValue = requirementSubmissionHistoryDTO.getSearchValue().replaceAll("/", "-");
			                        Date date1 = format.parse(replacedValue);
			                        sqlDate = new java.sql.Date(date1.getTime());
			                    }
			                    if (sqlDate != null) {
			                        query1.append(" and (emp.HIRE_DATE like '%" + sqlDate +"%'" + ") ");
			                    }
			                }
						 
			                else{
								 query1.append(" and (user.FIRST_NAME  like '%" + requirementSubmissionHistoryDTO.getSearchValue()+ "%' ")
								 .append("OR user.LAST_NAME like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR emp.EMPLOYMENT_TYPE like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR CONCAT(user.FIRST_NAME,space(1),user.LAST_NAME) like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR urole.USER_TYPE like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR emp.EMPLOYEE_ID like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%')");
			                }
					 }
					 
			/*		 
			            if (StringUtils.isNotBlank(requirementSubmissionHistoryDTO.getSortColumn())) {
			                sortingQry = " order by " + requirementSubmissionHistoryDTO.getSortColumn() + " "
			                        + requirementSubmissionHistoryDTO.getSortOrder();
			            }*/
					 
					 
					  queryString =  session.createSQLQuery(query.toString() + " " + query1.toString() + " " + sortingQry).addScalar("employeeId", StandardBasicTypes.STRING)
							  		.addScalar("empType", StandardBasicTypes.STRING)
							  		.addScalar("userRole", StandardBasicTypes.STRING)
							  		.addScalar("gender", StandardBasicTypes.STRING)
							  		.addScalar("hireDate", StandardBasicTypes.DATE)
							  		.addScalar("terminatedCount", StandardBasicTypes.LONG)
							  		.addScalar("empTypeFullTimeCount", StandardBasicTypes.LONG)
							  		.addScalar("empTypePartTimeCount", StandardBasicTypes.LONG)
							  		.addScalar("ftMaleCnt", StandardBasicTypes.LONG)
							  		.addScalar("ftFemaleCnt", StandardBasicTypes.LONG)
							  		.addScalar("ptMaleCnt", StandardBasicTypes.LONG)
							  		.addScalar("ptFemaleCnt", StandardBasicTypes.LONG)
									.addScalar("hiredCount", StandardBasicTypes.LONG)
							  		.addScalar("terminateDate", StandardBasicTypes.DATE);
		                queryString.setResultTransformer(Transformers.aliasToBean(AssociateJoineReportDTO.class));
		                associateJoineReportDTOList = queryString.list();
		                
		                
		                
						associateJoineReportDTO.setData(associateJoineReportDTOList);
				         int count = 0;
				            int filteredCount = 0;
				            if (associateJoineReportDTOList != null && !associateJoineReportDTOList.isEmpty()) {
				                count = queryString.list().size();
				                filteredCount = queryString.list().size();
				            }
				            associateJoineReportDTO.setRecordsTotal(count);
				            associateJoineReportDTO.setRecordsFiltered(filteredCount);
						return associateJoineReportDTO;
				}
				@Override
				@Transactional
				public ResponseList<AssociateJoineReportDTO> getAssociateTerminateReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
					 Query queryString = null;
					 ResponseList<AssociateJoineReportDTO> associateJoineReportDTO = new ResponseList<>();
		             List<AssociateJoineReportDTO> associateJoineReportDTOList = null;
					 java.sql.Timestamp fromDate1 = new java.sql.Timestamp(startUTCDate.getTime());
						java.sql.Timestamp toDate1 = new java.sql.Timestamp(endUTCDate.getTime());
						java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
						java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
						OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationsId);
					    SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					    Date date = null;
					    java.sql.Date sqlDate = null;
						Query countQueryString;
						List<Long> countlist = null;
						String sortingQry = "";
					 String query="SELECT emp.EMPLOYEE_ID as employeeId, emp.USER_ID as userId,emp.ORG_LOCATIONS_ID as orgLocationsId,"
					 		+ " urole.TERMINATE_DATE as terminateDate, urole.TERMINATE_REASON as terminatedReason,emp.HIRE_DATE as hireDate,  urole.USER_TYPE as userRole,"
+" emp.EMPLOYMENT_TYPE as empType,user.GENDER as gender, emp.CURRENT_CURRENCY as currency,emp.RATE as rate,emp.RATE_PER as ratePer,emp.PROBATION_STATUS as probationStatus,"
+" (SELECT CONCAT(u.FIRST_NAME,space(1),u.LAST_NAME) from USER u where u.ID = emp.USER_ID) as empName,"
+"(SELECT CONCAT(u.FIRST_NAME,space(1),u.LAST_NAME) from USER u where u.ID = urole.REPORTING_MANAGER_ID) as reportingManager," 
+"(SELECT shft.SHIFT_NAME from SHIFT shft where shft.ID = urole.SHIFT_ID) as shiftName,"
+" (SELECT dep.DEPARTMENT_NAME from DEPARTMENT dep where dep.ID = emp.DEPARTMENT_ID) as deptName " 
+" from EMPLOYEE emp, USER_ROLE urole, USER user "
+" where urole.USER_ID = emp.USER_ID and user.ID = emp.USER_ID "
+" and urole.ORG_LOCATIONS_ID = emp.ORG_LOCATIONS_ID and  emp.ORG_LOCATIONS_ID = '"
				+ orgLocationsId + "' "
						+" and DATE_FORMAT(urole.TERMINATE_DATE,'%Y-%m-%d') BETWEEN  '" + fromDate
						+ "' and '" + toDate + " ' ";
					 StringBuilder query1 = new StringBuilder();
					 if (StringUtils.isNotBlank(requirementSubmissionHistoryDTO.getSearchValue())) {
						 
					       Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			                Matcher matcher = pattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
			                if (matcher.matches() == true) {
			                    if (requirementSubmissionHistoryDTO.getSearchValue().contains("/")
			                            || (requirementSubmissionHistoryDTO.getSearchValue().contains("-"))) {
			                        String replacedValue = requirementSubmissionHistoryDTO.getSearchValue().replaceAll("/", "-");
			                        Date date1 = format.parse(replacedValue);
			                        sqlDate = new java.sql.Date(date1.getTime());
			                    }
			                    if (sqlDate != null) {
			                        query1.append(" and (urole.TERMINATE_DATE like '%" + sqlDate +"%'" + ") ");
			                    }
			                }
						 
			                else{
								 query1.append(" and (user.FIRST_NAME  like '%" + requirementSubmissionHistoryDTO.getSearchValue()+ "%' ")
								 .append("OR user.LAST_NAME like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR emp.EMPLOYMENT_TYPE like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR CONCAT(user.FIRST_NAME,space(1),user.LAST_NAME) like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR urole.USER_TYPE like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR emp.EMPLOYEE_ID like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%')");
			                }
					 }
					 
					 
			            if (StringUtils.isNotBlank(requirementSubmissionHistoryDTO.getSortColumn())) {
			                sortingQry = " order by " + requirementSubmissionHistoryDTO.getSortColumn() + " "
			                        + requirementSubmissionHistoryDTO.getSortOrder();
			            }
					 
					 
					  queryString =  session.createSQLQuery(query.toString() + " " + query1.toString() + " " + sortingQry).addScalar("employeeId", StandardBasicTypes.STRING)
							  		.addScalar("empName",StandardBasicTypes.STRING).addScalar("empType", StandardBasicTypes.STRING)
							  		.addScalar("deptName", StandardBasicTypes.STRING).addScalar("userRole", StandardBasicTypes.STRING)
							  		.addScalar("reportingManager", StandardBasicTypes.STRING)
							  		.addScalar("gender", StandardBasicTypes.STRING)
							  		.addScalar("currency", StandardBasicTypes.STRING)
							  		.addScalar("rate", StandardBasicTypes.STRING)
							  		.addScalar("ratePer", StandardBasicTypes.STRING)
							  		.addScalar("shiftName", StandardBasicTypes.STRING)
							  		.addScalar("terminatedReason", StandardBasicTypes.STRING)
							  		.addScalar("probationStatus", StandardBasicTypes.STRING)
							  		.addScalar("terminateDate", StandardBasicTypes.DATE)
					  				.addScalar("hireDate", StandardBasicTypes.DATE);
		                queryString.setResultTransformer(Transformers.aliasToBean(AssociateJoineReportDTO.class));
		                associateJoineReportDTOList = queryString.list();
		                
		                
		                
						associateJoineReportDTO.setData(associateJoineReportDTOList);
				         int count = 0;
				            int filteredCount = 0;
				            if (associateJoineReportDTOList != null && !associateJoineReportDTOList.isEmpty()) {
				                count = queryString.list().size();
				                filteredCount = queryString.list().size();
				            }
				            associateJoineReportDTO.setRecordsTotal(count);
				            associateJoineReportDTO.setRecordsFiltered(filteredCount);
						return associateJoineReportDTO;
				}

				
				
				
				@Override
				@Transactional
				public ResponseList<AssociateJoineReportDTO> getAssociateJoineReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception
				{
					Session session = this.sessionFactory.getCurrentSession();
					 Query queryString = null;
					 ResponseList<AssociateJoineReportDTO> associateJoineReportDTO = new ResponseList<>();
		             List<AssociateJoineReportDTO> associateJoineReportDTOList = null;
					 java.sql.Timestamp fromDate1 = new java.sql.Timestamp(startUTCDate.getTime());
						java.sql.Timestamp toDate1 = new java.sql.Timestamp(endUTCDate.getTime());
						java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
						java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
						OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationsId);
					    SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					    Date date = null;
					    java.sql.Date sqlDate = null;
						Query countQueryString;
						List<Long> countlist = null;
						String sortingQry = "";
					 String query="SELECT emp.EMPLOYEE_ID as employeeId, emp.USER_ID as userId,emp.ORG_LOCATIONS_ID as orgLocationsId, emp.HIRE_DATE as hireDate, urole.USER_TYPE as userRole,"
+" emp.EMPLOYMENT_TYPE as empType,user.GENDER as gender, emp.CURRENT_CURRENCY as currency,emp.RATE as rate,emp.RATE_PER as ratePer,emp.PROBATION_STATUS as probationStatus,"
+" (SELECT CONCAT(u.FIRST_NAME,space(1),u.LAST_NAME) from USER u where u.ID = emp.USER_ID) as empName,"
+"(SELECT CONCAT(u.FIRST_NAME,space(1),u.LAST_NAME) from USER u where u.ID = urole.REPORTING_MANAGER_ID) as reportingManager," 
+"(SELECT shft.SHIFT_NAME from SHIFT shft where shft.ID = urole.SHIFT_ID) as shiftName,"
+" (SELECT dep.DEPARTMENT_NAME from DEPARTMENT dep where dep.ID = emp.DEPARTMENT_ID) as deptName " 
+" from EMPLOYEE emp, USER_ROLE urole, USER user "
 +" where urole.USER_ID = emp.USER_ID and user.ID = emp.USER_ID "
+" and urole.ORG_LOCATIONS_ID = emp.ORG_LOCATIONS_ID and  emp.ORG_LOCATIONS_ID = '"
				+ orgLocationsId + "' "
						+" and DATE_FORMAT(emp.HIRE_DATE,'%Y-%m-%d') BETWEEN  '" + fromDate
						+ "' and '" + toDate + " ' ";
					 StringBuilder query1 = new StringBuilder();
					 if (StringUtils.isNotBlank(requirementSubmissionHistoryDTO.getSearchValue())) {
						 
					       Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			                Matcher matcher = pattern.matcher(requirementSubmissionHistoryDTO.getSearchValue());
			                if (matcher.matches() == true) {
			                    if (requirementSubmissionHistoryDTO.getSearchValue().contains("/")
			                            || (requirementSubmissionHistoryDTO.getSearchValue().contains("-"))) {
			                        String replacedValue = requirementSubmissionHistoryDTO.getSearchValue().replaceAll("/", "-");
			                        Date date1 = format.parse(replacedValue);
			                        sqlDate = new java.sql.Date(date1.getTime());
			                    }
			                    if (sqlDate != null) {
			                        query1.append(" and (emp.HIRE_DATE like '%" + sqlDate +"%'" + ") ");
			                    }
			                }
						 
			                else{
								 query1.append(" and (user.FIRST_NAME  like '%" + requirementSubmissionHistoryDTO.getSearchValue()+ "%' ")
								 .append("OR user.LAST_NAME like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR emp.EMPLOYMENT_TYPE like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR CONCAT(user.FIRST_NAME,space(1),user.LAST_NAME) like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR urole.USER_TYPE like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%'")
								 .append("OR emp.EMPLOYEE_ID like '%"
			                                + requirementSubmissionHistoryDTO.getSearchValue()
			                                + "%')");
			                }
					 }
					 
					 
			            if (StringUtils.isNotBlank(requirementSubmissionHistoryDTO.getSortColumn())) {
			                sortingQry = " order by " + requirementSubmissionHistoryDTO.getSortColumn() + " "
			                        + requirementSubmissionHistoryDTO.getSortOrder();
			            }
					 
					 
					  queryString =  session.createSQLQuery(query.toString() + " " + query1.toString() + " " + sortingQry).addScalar("employeeId", StandardBasicTypes.STRING)
							  		.addScalar("empName",StandardBasicTypes.STRING).addScalar("empType", StandardBasicTypes.STRING)
							  		.addScalar("deptName", StandardBasicTypes.STRING).addScalar("userRole", StandardBasicTypes.STRING)
							  		.addScalar("reportingManager", StandardBasicTypes.STRING)
							  		.addScalar("gender", StandardBasicTypes.STRING)
							  		.addScalar("currency", StandardBasicTypes.STRING)
							  		.addScalar("rate", StandardBasicTypes.STRING)
							  		.addScalar("ratePer", StandardBasicTypes.STRING)
							  		.addScalar("shiftName", StandardBasicTypes.STRING)
							  		.addScalar("probationStatus", StandardBasicTypes.STRING)
							  		.addScalar("hireDate", StandardBasicTypes.DATE);
		                queryString.setResultTransformer(Transformers.aliasToBean(AssociateJoineReportDTO.class));
		                associateJoineReportDTOList = queryString.list();
		                
		                
		                
						associateJoineReportDTO.setData(associateJoineReportDTOList);
				         int count = 0;
				            int filteredCount = 0;
				            if (associateJoineReportDTOList != null && !associateJoineReportDTOList.isEmpty()) {
				                count = queryString.list().size();
				                filteredCount = queryString.list().size();
				            }
				            associateJoineReportDTO.setRecordsTotal(count);
				            associateJoineReportDTO.setRecordsFiltered(filteredCount);
						return associateJoineReportDTO;
				}
				
				
	@Override
	@Transactional
	public List<RequirementSubmission> getCandidateForInvoiceDashBoard(OrgLocations orgLocations, User user) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> finalList = null;
		String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.submissionStatus ='JOINED' and rs.invoiceGenerated IS NULL and (req.jobType ='Full Time' or req.jobType ='W-2') "
				+ "and req.isClientJob = 'Y'";
		Query finalQuery = session.createQuery(query).setParameter("locationId", orgLocations.getId());
		finalList =finalQuery.list();
		return finalList;
	}
	
	@Override
	@Transactional
	public List<RequirementSubmission> getCandidateForInvoiceJobClosure(OrgLocations orgLocations, User user,
			Long orgRelationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> finalList = null;
		String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.submissionStatus ='JOINED' and rs.invoiceGenerated IS NULL and (req.jobType ='Full Time' or req.jobType ='W-2') "
				+ "and req.isClientJob = 'Y' and req.clientId.id =:orgRelationId";
		Query finalQuery = session.createQuery(query).setParameter("locationId", orgLocations.getId()).setParameter("orgRelationId", orgRelationId);
		finalList =finalQuery.list();
		return finalList;
	}
	
	@Override
	@Transactional
	public 	List<RequirementSubmission> getCandidateInvoiceJobClosureDashboard(OrgLocations orgLocations,Long orgRelationId,Long summissionId,Long candidateId)
 {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> finalSubmission = null;
		String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.candidate.id =:candidateId and rs.id =:summissionId and req.isClientJob = 'Y' and req.clientId.id =:orgRelationId";
		Query finalQuery = session.createQuery(query).setParameter("locationId", orgLocations.getId()).setParameter("orgRelationId", orgRelationId).setParameter("candidateId", candidateId).setParameter("summissionId", summissionId);
		finalSubmission =finalQuery.list();
		return finalSubmission;
	}
	
	
	@Override
	@Transactional
	public List<RequirementSubmission> getCandidateForInvoiceJobClosureEdit(OrgLocations orgLocations, User user,
			Long orgRelationId, List<Long> selectedAssociates) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> finalList = null;
		String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.submissionStatus ='JOINED' and rs.invoiceGenerated IS NULL and (req.jobType ='Full Time' or req.jobType ='W-2') "
				+ "and req.isClientJob = 'Y' and req.clientId.id =:orgRelationId and can.id NOT IN(:selectedAssociates)";
		Query finalQuery = session.createQuery(query).setParameter("locationId", orgLocations.getId()).setParameter("orgRelationId", orgRelationId).setParameterList("selectedAssociates", selectedAssociates);
		finalList =finalQuery.list();
		return finalList;
	}
	
	@Override
	@Transactional
	public List<RequirementSubmission> getCandidateForBillJobClosure(OrgLocations orgLocations, User user,Long orgRelationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> finalList = null;
		String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.submissionStatus ='JOINED' and rs.invoiceGenerated IS NULL and (req.jobType ='Full Time' or req.jobType ='W-2') "
				+ "and can.orgRelationId.id =:orgRelationId";
		Query finalQuery = session.createQuery(query).setParameter("locationId", orgLocations.getId()).setParameter("orgRelationId", orgRelationId);
		finalList =finalQuery.list();
		return finalList;
	}
	
	
	@Override
	@Transactional
	public List<RequirementSubmission> getCandidateForBillJobClosureEdit(OrgLocations orgLocations, User user,
			Long orgRelationId, List<Long> selectedAssociates) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission> finalList = null;
		String query = "from RequirementSubmission rs left join fetch rs.candidate can left join fetch rs.requirement req where rs.orgLocations.id =:locationId and rs.submissionStatus ='JOINED' and rs.invoiceGenerated <> 'Y' and (req.jobType ='Full Time' or req.jobType ='W-2') "
				+ "and can.orgRelationId.id =:orgRelationId and can.id NOT IN(:selectedAssociates)";
		Query finalQuery = session.createQuery(query).setParameter("locationId", orgLocations.getId()).setParameter("orgRelationId", orgRelationId).setParameterList("selectedAssociates", selectedAssociates);
		finalList =finalQuery.list();
		return finalList;
	}
	
	@Override
	@Transactional
	public long getRequirementClientCountByRequirementIdAndUserIdAndDates(BigInteger requirementID, BigInteger userId, Date[] searchDates)
	{
		 Integer count= 0;
		 try {
				java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
				java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
				java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
				java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
				String betweenDateQuery = "";
				String resultQuery = "";
				Session session = this.sessionFactory.getCurrentSession();
				String query = "select count(*) from Requirement requirement where (requirement.managerUserId = :userId OR requirement.primaryRecruiterUserId = :userId) and  DATE_FORMAT(requirement.createdDate,'%Y-%m-%d') BETWEEN :fromDate AND :toDate";
				
			     List<Long> appliedCount = session.createQuery(query).setLong("userId", userId.intValue()).setDate("fromDate", fromDate).setDate("toDate", toDate).list();
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;

			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	@Transactional
	public long getRequirementCountByUserIdAndDates(BigInteger userId, Date[] searchDates)
	{
		 Integer count= 0;
		try {
			java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
			java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
			java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
			java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
			String betweenDateQuery = "";
			String resultQuery = "";
			Session session = this.sessionFactory.getCurrentSession();

			String query = "select count(*) from RequirementAssignTo reqAssign where reqAssign.assignUserId = :userId";
			
			if (fromDate != null && toDate != null) {
				betweenDateQuery = " and DATE_FORMAT(reqAssign.createdDate,'%Y-%m-%d') BETWEEN :fromDate AND :toDate ";
			}
			resultQuery = query.concat(betweenDateQuery);
		     List<Long> appliedCount = session.createQuery(resultQuery).setLong("userId", userId.intValue())
		    		 .setDate("fromDate", fromDate)
		    		 .setDate("toDate", toDate).list();
			
			 
			 if(appliedCount != null && !appliedCount.isEmpty()){
					count = appliedCount.get(0).intValue();
			 }
			return count;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	@Transactional
	public long getOfferCountByUserIdAndDates(String userCode, Date[] searchDates)
	{
		 Integer count= 0;
			try {
				java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
				java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
				java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
				java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
				String betweenDateQuery = "";
				String resultQuery = "";
				Session session = this.sessionFactory.getCurrentSession();

				String query = "select count(*) from RequirementSubmission reqSub where (reqSub.submissionStatus = 'OFFERMADE' or reqSub.submissionStatus='OFFERACCEPTED' or reqSub.previousSubmissionStatus='OFFERMADE' or reqSub.previousSubmissionStatus='OFFERACCEPTED') and reqSub.createdBy = :userCode ";
				
				if (fromDate != null && toDate != null) {
					betweenDateQuery = " and DATE_FORMAT(reqSub.updatedDate,'%Y-%m-%d') BETWEEN :fromDate AND :toDate ";
				}
				resultQuery = query.concat(betweenDateQuery);
			     List<Long> appliedCount = session.createQuery(resultQuery).setString("userCode", userCode)
			    		 .setDate("fromDate", fromDate)
			    		 .setDate("toDate", toDate).list();
				
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
	}
	
	@Override
	@Transactional
	public long getJoinedCountByUserIdAndDates(String userCode, Date[] searchDates)
	{
		 Integer count= 0;
			try {
				java.sql.Timestamp fromDate1 = new java.sql.Timestamp(searchDates[0].getTime());
				java.sql.Timestamp toDate1 = new java.sql.Timestamp(searchDates[1].getTime());
				java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
				java.sql.Date toDate = new java.sql.Date(toDate1.getTime());
				String betweenDateQuery = "";
				String resultQuery = "";
				Session session = this.sessionFactory.getCurrentSession();
				
				String query = "select count(*) from RequirementSubmission reqSub where (reqSub.submissionStatus = 'JOINED' or  reqSub.previousSubmissionStatus='JOINED' ) and reqSub.createdBy = :userCode ";
				
				if (fromDate != null && toDate != null) {
					betweenDateQuery = " and date(reqSub.updatedDate) BETWEEN :fromDate AND :toDate ";
				}
				resultQuery = query.concat(betweenDateQuery);
			     List<Long> appliedCount = session.createQuery(resultQuery).setString("userCode", userCode)
			    		 .setDate("fromDate", fromDate)
			    		 .setDate("toDate", toDate).list();
				
				 
				 if(appliedCount != null && !appliedCount.isEmpty()){
						count = appliedCount.get(0).intValue();
				 }
				return count;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
	}
	
	
	@Override
	@Transactional
	public List<RequirementSubmission> getSubmissionListByOrgLocationAndSubmissionStatusAndJoiningDate(Long orgLocationId, String submissionStatus, Date joiningDate) {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 java.sql.Date sqlJoiningDate = new java.sql.Date(joiningDate.getTime());
		 
	     String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidate left join fetch rs.sourceSubmission left join fetch rs.candidateResumes where rs.orgLocations.id = :orgLocationsId and rs.submissionStatus = 'SUBMISSION' and date(rs.joinedDate) =:joiningDate and rs.status = '1' ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationId).setDate("joiningDate", sqlJoiningDate).list();
	     return shortListCandidatesList;
	}

}

