package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.CandidateHotList;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class CandidateDaoImpl implements CandidateDao {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * @author prasad k
	 * @since  sep 1 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 * @param candidate This is the first paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate storeCandidate(Candidate candidate) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidate != null) {
				session.saveOrUpdate(candidate);
			}
			logger.debug("Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Candidate"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidate;
	}
	

	/**
	 * to Candidates Count by  orgLocationId and candidate status for candidate elastic search
	 * 
	 * @author Naresh Bolisetty
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws NullPointerException
    */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId,String candidateStatus) {
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		
		
		String subQuery  = null;
		if(null != candidateStatus && !candidateStatus.isEmpty() && candidateStatus.equalsIgnoreCase(Constants.ACTIVE)){
			subQuery = "and candidate.status = '1'"; 
		}else if(null != candidateStatus && !candidateStatus.isEmpty() && candidateStatus.equalsIgnoreCase(Constants.INACTIVE)){
			subQuery = "and candidate.status = '0'"; 
		}else{
			subQuery = "and (candidate.status = '1' or candidate.status = '0')";
		}
		
		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "+subQuery+" ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByOrgLocationIDAndStatus(Long orgLocationId,String candidateStatus,String isAssociate) {
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		
		
		String subQuery  = null;
		if(null != candidateStatus && !candidateStatus.isEmpty() && candidateStatus.equalsIgnoreCase(Constants.ACTIVE)){
			subQuery = "and candidate.status = '1'"; 
		}else if(null != candidateStatus && !candidateStatus.isEmpty() && candidateStatus.equalsIgnoreCase(Constants.INACTIVE)){
			subQuery = "and candidate.status = '0'"; 
		}else{
			subQuery = "and (candidate.status = '1' or candidate.status = '0')";
		}
		
		String subQuery1  = null;
		if(isAssociate.equalsIgnoreCase(Constants.YES)){
			subQuery1 = "and candidate.isAssociate = 'Y'"; 
		}else{
			subQuery1 = "and candidate.isAssociate = 'Y' OR candidate.isAssociate = 'N'";  
		}
		
		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "+subQuery1+"  "+subQuery+" ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * 
	 * @author David Sammeta
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws NullPointerException
    */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId, Integer candidateState, String updatedDateRange,String candidateType,String isAssociate) throws Exception {

		
		String subQuery = null;
		Integer dateRange ;
		String updatedDate  ="";
		Date currentDate =  commonUtil.currentDate();
		List<Candidate> candidateList ;
		java.sql.Date sqlCurrentDate = null;
		
		// for current Date Converting to Sql Date Format
		 if(currentDate != null){
				sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		 }
		
		
		//Checking for Candidate Status 
		Session session = this.sessionFactory.getCurrentSession();
		String  dateLimit = "";
		if(candidateState.equals(Constants.CANDIDATE_ACTIVE)){
			  subQuery ="(cnadidate.status = 1)";
		}else if(candidateState.equals(Constants.CANDIDATE_ALL)){
			  subQuery ="(cnadidate.status = 1 or cnadidate.status = 0)";
		}else{
			 subQuery ="(cnadidate.status = 0)";
		}
		
		
		String candidateTypeSubQuery = "";
		/*if(candidateType != null && !candidateType.isEmpty() && candidateType.equalsIgnoreCase("All")){
			
			candidateTypeSubQuery = " and (cnadidate.candidateType = 'Candidate' OR cnadidate.candidateType = 'Consultant')";
			
		}else{
			candidateTypeSubQuery = " and cnadidate.candidateType = '"+candidateType+"' ";
		}*/
		
			if(candidateType != null && !candidateType.isEmpty() && candidateType.equalsIgnoreCase("All")){
				if(isAssociate.equalsIgnoreCase(Constants.YES)){
					candidateTypeSubQuery = " and (cnadidate.isAssociate = 'Y')";
				}else{
					candidateTypeSubQuery = " and (cnadidate.isAssociate = 'N' OR cnadidate.isAssociate = 'Y' )";					
				}
				
			}else{
				if(isAssociate.equalsIgnoreCase(Constants.YES)){
					candidateTypeSubQuery = " and (cnadidate.isFavouriteAssociate = '"+candidateType+"') ";
				}else{
					candidateTypeSubQuery = " and (cnadidate.isFavourite = '"+candidateType+"') ";
				}
			}
    	
			
		if(StringUtils.isNotEmpty(updatedDateRange) && !"null".equalsIgnoreCase(updatedDateRange)   && !updatedDateRange.equalsIgnoreCase("undefined")){
			
			// This Code is for  getting the sql enddate based on range
		     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	 Calendar c = Calendar.getInstance();
		     c.setTime(new Date()); // Now use today date.
		     dateRange = Integer.parseInt(updatedDateRange);
	         c.add(Calendar.DATE, -dateRange); // Subtracting dateRange days
	         updatedDate     = dateFormat.format(c.getTime());
	         Date parsed = dateFormat.parse(updatedDate);
	         java.sql.Date endDate = new java.sql.Date(parsed.getTime());
	         
			
   	    	 String query = "from Candidate cnadidate left join fetch cnadidate.orgLocationId orglocation where  cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "+subQuery+" "+candidateTypeSubQuery+" and ( cnadidate.updatedDate >= :endDate) ORDER BY cnadidate.id DESC ";  
		     candidateList = session.createQuery(query)
				                    .setDate("endDate", endDate)
				                    .setLong("orgLocationId", orgLocationId)
				                    .setLong("orgId", orgId)
				                    .setCacheable(true)
				                    .list();
		}else{
			
			String query = "from Candidate cnadidate  left join fetch cnadidate.orgLocationId orglocation where cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "+subQuery+" "+candidateTypeSubQuery+" ORDER BY cnadidate.id DESC";  
		    candidateList = session.createQuery(query)
					               .setLong("orgLocationId", orgLocationId)
					               .setLong("orgId", orgId).setCacheable(true).list();
		}
		
		return candidateList;
	}
	
	/**
	 * to get Candidate By CandidateId
	 * 
	 * @author Venkat Kesav
	 * 
	 * @param candidateId
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointerException
    */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  "
				+ "left join fetch cnadidate.consultantUser "
				+ "left join fetch cnadidate.teamId "
				+ "left join fetch cnadidate.recruiterUserId "
				+ "left join fetch cnadidate.masterCandidateId "
				+ "left join fetch cnadidate.parentCandidateId "
				+ "left join fetch cnadidate.orgRelationId left "
				+ "join fetch cnadidate.spocId left join fetch cnadidate.orgLocationId left join fetch cnadidate.orgLocationId.user where cnadidate.id =:candidateId";
		List candidateList = session.createQuery(query).setLong("candidateId", candidateId).setCacheable(true).list();
		Candidate candidate = null;
		if(candidateList != null && !candidateList.isEmpty())
		{
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}
	
	
	/**
	 * @author David Raju Sammeta
	 * 
	 * This Method is for getting Candidate by Candidate Id OrgId and OrgLocationID
	 * 
	 * @return Candidate
	 * 
	 * @exception NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId)throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate left join fetch cnadidate.parentCandidateId  where cnadidate.id = :candidateId and cnadidate.orgLocationId.id =:orgLocationId";  
		List<Candidate> candidateList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		Candidate candidate = null;
		if(candidateList != null && !candidateList.isEmpty()){
			candidate = candidateList.iterator().next();
		}
		return candidate;
		
	}

	/**
	 * @author prasad.k
	 * 
	 * This mothod is used to get CandidateEmail
	 * 
	 * @param email
	 * 
	 * @return CandidateEmail
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate findByCandidateEmail(String email, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("findByCandidateEmail start");
		try{
			candidate = session.createQuery("from Candidate can left join fetch can.orgRelationId left join fetch can.parentCandidateId left join fetch can.masterCandidateId where can.email=:email and can.orgLocationId.id = :orgLocationId ORDER BY can.id DESC" )
					.setString( "email", email ).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
			
			logger.debug("findByCandidateEmail end");
			if (candidate.size() > 0) {
				return candidate.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception {
		logger.debug("In getCandidatesByOrgLocationID method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidatesList = null;
		Query candidateListQuery= session.createQuery("from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.candidateType = :candidateType "
				+ "and candidate.status = '1' and candidate.id NOT IN (select submission.candidate.id from RequirementSubmission submission "
				+ "where submission.requirement.id = :requirementId) ORDER BY candidate.id DESC ");
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setString("candidateType", candidateType).setCacheable(true).list();
		return candidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId ORDER BY cnadidate.id DESC";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}

	@Override
	@Transactional
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {

		
		//Checking for Candidate Status 
		Session session = this.sessionFactory.getCurrentSession();
		
		String candidateTypeSubQuery = "";
		if(candidateType.equalsIgnoreCase("All")){
			candidateTypeSubQuery = " and (candidate.candidateType = 'Candidate' OR candidate.candidateType = 'Consultant')";
		}else{
			candidateTypeSubQuery = " and candidate.candidateType = '"+candidateType+"' ";
		}
    	

		String query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.isFavourite = '1' and candidate.teamId IS NULL "+candidateTypeSubQuery+" ";  
		List<Candidate> candidateList = session.createQuery(query)
				               .setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return candidateList;
	}


	@Override
	@Transactional
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId) throws Exception {
		
		logger.debug("In getAllRequestedCandidatesForCampaign method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from Candidate candidate  where candidate.id IN (:candidateId)";  
		List<Candidate> candidateList = session.createQuery(query)
				               .setParameterList("candidateId", candidateId).setCacheable(true).list();
		return candidateList;
	}

	

	@Override
	@Transactional
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.teamId.id = :teamId ";
		List<Long> favouriteCount = session.createQuery(query.toString()).setLong("teamId", teamId).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(favouriteCount != null && !favouriteCount.isEmpty()){
					count = favouriteCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and candidate.teamId.id = :teamId  ";  
		List<Candidate> candidateList = session.createQuery(query)
				               .setLong("orgLocationId", orgLocationId).setLong("teamId", teamId).setCacheable(true).list();
		return candidateList;
	}

	@Override
	@Transactional
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId, String type) throws Exception {
		logger.debug("In getAllActiveCandidatesCountForCampaignByLocation method");
		Session session = this.sessionFactory.getCurrentSession();

		
		String subQuery = null;
		if(type.equalsIgnoreCase(Constants.ASSOCIATE)){
			subQuery = "candidate.isAssociate = 'Y' ";
		}else{
			subQuery = "candidate.isAssociate = 'N' ";
		}
		
		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and "+subQuery+" and candidate.status = '1' and (candidate.isFavourite = '0' OR candidate.isFavourite IS NULL) ";  
		List<Long> candidatesCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if(candidatesCount != null && !candidatesCount.isEmpty()){
				count = candidatesCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId) throws Exception {
		
		logger.debug("In getAllHotlistCandidatesCountNotAssignedToTeamByLocation method");
		Session session = this.sessionFactory.getCurrentSession();

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and (candidate.isFavourite = '1' OR candidate.isFavouriteAssociate = '1') and candidate.teamId IS NULL ";  
		List<Long> candidatesCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if(candidatesCount != null && !candidatesCount.isEmpty()){
				count = candidatesCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId, String type,Long orgLocationId) throws Exception {
		
		logger.debug("In getAllCandidatesCountForCampaignByTeam method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = null;
		if(type.equalsIgnoreCase(Constants.ASSOCIATE)){
			subQuery = "candidate.isAssociate = 'Y' ";
		}else{
			subQuery = "candidate.isAssociate = 'N' ";
		}

		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id = :orgLocationId and "+subQuery+" and candidate.status = '1' and candidate.teamId.id IN (:teamId) ";  
		List<Long> candidatesCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setParameterList("teamId", teamId).list();
		Integer count = 0;
		if(candidatesCount != null && !candidatesCount.isEmpty()){
				count = candidatesCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId, Integer dateRange,Long reportingManagerUserId)
			throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = null;
		String updatedDate  ="";
		Date currentDate =  commonUtil.currentDate();
		List<Candidate> candidateList ;
		java.sql.Date sqlCurrentDate = null;
		
		// for current Date Converting to Sql Date Format
		 if(currentDate != null){
				sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		 }
		
		 
		// This Code is for  getting the sql enddate based on range
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Calendar c = Calendar.getInstance();
	      c.setTime(new Date()); // Now use today date.
	    dateRange = dateRange;
	    c.add(Calendar.DATE, +dateRange); // Adding  The DateRange days
	    updatedDate     = dateFormat.format(c.getTime());
	    Date parsed = dateFormat.parse(updatedDate);
	    java.sql.Date endDate = new java.sql.Date(parsed.getTime());
	   
	    subQuery =   " from Candidate candidate where candidate.orgLocationId.id =:orgLocationId  and candidate.consultantUser.id IN (select userRole.user.id  from UserRole  userRole where userRole.reportingManager.id =:reportingManagerUserId )  and  candidate.candidateType = 'Consultant' and candidate.isFavourite <> '1'  and candidate.consultantUser.id IN (select emp.user.id from Employees emp where emp.id IN (select pm.employee.id from ProjectMembers pm where pm.id IN ( select pmh.projectMemberId.id from ProjectMembersHistory pmh  where  pmh.projectTerminationDate BETWEEN :sqlCurrentDate AND :endDate)  ) )"; 

	    candidateList = session.createQuery(subQuery)
					    		 .setDate("sqlCurrentDate", sqlCurrentDate)
					    		 .setDate("endDate", endDate)
					    		 .setLong("reportingManagerUserId", reportingManagerUserId)
				                 .setLong("orgLocationId", orgLocationId)
				                 .setCacheable(true)
				                .list();
	  /*  System.err.println("das"+candidateList.size());*/
	return candidateList;
	}

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId, String candidateType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.consultantUser =:userId and candidate.candidateType =:candidateType ";  
		List<Candidate> candidateList =  session.createQuery(query).setLong("userId", userId).setString("candidateType", candidateType).setCacheable(true).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByEmailAndRecruiterUserId(String email, Long recruiterUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.email = :email and candidate.candidateType = 'Candidate' and candidate.recruiterUserId.id = :recruiterUserId ";  
		List<Candidate> candidateList =  session.createQuery(query).setLong("recruiterUserId", recruiterUserId).setString("email", email).setCacheable(true).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId, Long requirementId,
			String candidateType, String searchApplicant) throws Exception {
		logger.debug("In getCandidatesByOrgLocationID method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidatesList = null;
		String subQuery = "";
		if(!isNullOrEmpty(searchApplicant)){
			subQuery = "and (candidate.firstName LIKE '" + searchApplicant + "%') OR (candidate.lastName LIKE '" +searchApplicant + "%')";
		}
		
		Query candidateListQuery= session.createQuery("from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.candidateType = :candidateType "
				+ "and candidate.status = '1' and candidate.id NOT IN (select submission.candidate.id from RequirementSubmission submission "
				+ "where submission.requirement.id = :requirementId) "+subQuery+" ORDER BY candidate.id DESC ");
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setString("candidateType", candidateType).setCacheable(true).list();
		return candidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations,
			User user) throws Exception {
		
		
		java.sql.Date sqlCurrentDate = null;
		Date currentDate =  commonUtil.currentDate();
		// for current Date Converting to Sql Date Format
		 if(currentDate != null){
				sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		 }
		 
		List<Long> candidatesList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*)  from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId  and cnadidate.createdBy =:userCode and  DATE(cnadidate.createdDate) = :sqlCurrentDate   ORDER BY cnadidate.id DESC";  
		candidatesList = session.createQuery(query)
										       .setLong("orgLocationId", orgLocations.getId())
										       .setString("userCode", user.getUserCode())
										       .setDate("sqlCurrentDate", sqlCurrentDate)
										       .setCacheable(true)
										       .list();
		
		  Integer count = 0;
	  		if(candidatesList != null && !candidatesList.isEmpty()){
	  				count = candidatesList.get(0).intValue();
	  		}
	  		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long userId,Long orgLocationId, String candidateType) {
		
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery  = null;
		if(null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)){
			subQuery = "and candidateType = 'Consultant'"; 
		}else{
			subQuery = "and candidateType = 'Candidate'"; 
		}
			
		
		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "
				+ " and (candidate.consultantUser IS NULL OR candidate.consultantUser.id <> :userId) "+subQuery+" ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidateReports(Long orgLocationId, String candidateType, String filterDate,
			Date startDateUTC, Date endDateUTC,String ownersList) {
		
		List<String> userCodesList = new ArrayList<String>();
		if(null != ownersList && !"".equals(ownersList)){
			for (String s : ownersList.split(",")){
				 userCodesList.add(s);
	   	    }
		}
		
		 Session session = this.sessionFactory.getCurrentSession();
		 Map<String, Object> params = new HashMap<String,Object>();
		
		String subQuery1 = "";
		 if(userCodesList != null && userCodesList.size() >0){
			 subQuery1 =  " and candidate.createdBy IN (:userCodesList)";
			 params.put("userCodesList", userCodesList);
		 }
		 
		
		String dateSubQuery = "";
		if(null != startDateUTC && null != endDateUTC){
			 dateSubQuery = "and (candidate.createdDate >= :startDateUTC and candidate.createdDate <= :endDateUTC)";
			  params.put("startDateUTC",startDateUTC);
			  params.put("endDateUTC",endDateUTC);
		}
		
		 
		 
		String subQuery  = "";
		if(null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)){
			subQuery = "and candidateType = 'Consultant'"; 
		}else{
			subQuery = "and candidateType = 'Candidate'"; 
		}
		
		String from = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId "+subQuery+" "+subQuery1+" "+dateSubQuery;  
		params.put("orgLocationId",orgLocationId);
		
		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId")){
				query.setParameter(str, (long) params.get(str));
			}if(str.equalsIgnoreCase("startDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
			}
			if(str.equalsIgnoreCase("endDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
			}
			if(str.equalsIgnoreCase("userCodesList")){
				query.setParameterList("userCodesList", userCodesList);
			}
			
        }
		
		return query.setCacheable(true).list();
	}
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId , String employeementType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<Candidate> candidateList = null;
		
		String subQuery = " and candidate.id NOT IN (select onboarding.candidateId.id from CandidateOnBoarding onboarding where onboarding.orgLocationId.id = :orgLocationId)";
		
		if(employeementType != null && employeementType != "" && employeementType != "undefined"){
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and candidate.isAssociate ='Y'  and candidate.designationType =:employeementType and candidate.consultantUser IS NULL "+subQuery+" ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("employeementType", employeementType).setCacheable(true).list();
		}else{
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and candidate.isAssociate ='Y' and candidate.consultantUser IS NULL "+subQuery+" "; 
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		  
		 
		
		return candidateList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getOwnersByCandidates(Long orgLocationId, String candidateType,String ownersList) {
		
		
		 List<String> userCodesList = new ArrayList<String>();
		 if(!"".equalsIgnoreCase(ownersList) && ownersList != null ){
			for (String s : ownersList.split(",")){
			 userCodesList.add(s);
    	    }
		 }
		 
		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Object> params = new HashMap<String,Object>();
		
		String subQuery1 = null;
		 if(userCodesList != null && userCodesList.size() >0){
			 subQuery1 =  " and candidate.createdBy IN (:userCodesList)";
			 params.put("userCodesList", userCodesList);
		 }
		 
		 
		String subQuery  = null;
		if(null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)){
			subQuery = "and candidateType = 'Consultant'"; 
		}else{
			subQuery = "and candidateType = 'Candidate'"; 
		}
		
		String from = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId "+subQuery+"  "+subQuery1;  
		params.put("orgLocationId",orgLocationId);
		
		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId")){
				query.setParameter(str, (long) params.get(str));
			}
			if(str.equalsIgnoreCase("userCodesList")){
				query.setParameterList("userCodesList", userCodesList);
			}
        }
		
		return query.setCacheable(true).list();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {
			
		    
			//Checking for Candidate Status 
		    Session session = this.sessionFactory.getCurrentSession();
			
			String subQuery  = null;
			if(null != candidateType && !candidateType.isEmpty() && candidateType.equalsIgnoreCase(Constants.CONSULTANT)){
				subQuery = "and candidateType = 'Consultant'"; 
			}else{
				subQuery = "and candidateType = 'Candidate'"; 
			}
			
			String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId "+subQuery+" ";
			List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		
		    return candidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from Candidate candidate where candidate.teamId.id =:teamId and candidate.orgLocationId.id =:orgLocationId  ";
		 List<Candidate> candidateHotList = session.createQuery(query.toString()).setLong( "teamId", teamId).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 return candidateHotList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String timePeriod)
			throws Exception {
			
		    String query ="select count(*)from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.createdBy =:userCode";
			Session session = this.sessionFactory.getCurrentSession();
		    long count = ((Long)session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("userCode",user.getUserCode()).uniqueResult()).intValue();
		    
		    return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveCandidatesCountForCampaignByLocationAndStatus(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.isFavouriteAssociate = '1' and candidate.isAssociate ='Y' and candidate.teamId IS NULL  ";  
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return candidateList;
	}

		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateById(Long candidateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateById start");
		candidate = session.createQuery("from Candidate candidate left join fetch candidate.consultantUser where candidate.id =:candidateId" ).setCacheable(true).setLong( "candidateId", candidateId ).setCacheable(true).list();
		logger.debug("getCandidateById end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, Date startDateUTC, Date endDateUTC)
			throws Exception {
		
			Map<String, Object> params = new HashMap<String,Object>();
			String subQueryDateRange = "";
			
			if(null != startDateUTC && null != endDateUTC){
				  subQueryDateRange = "and (candidate.createdDate >= :startDateUTC and candidate.createdDate <= :endDateUTC)";
				  params.put("startDateUTC",startDateUTC);
				  params.put("endDateUTC",endDateUTC);
			}
			
			String queryString ="select count(*)from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.createdBy =:userCode "+subQueryDateRange+" ";
			Session session = this.sessionFactory.getCurrentSession();
		    params.put("orgLocationId",orgLocationId);
		    params.put("userCode",user.getUserCode());
			
		    /*String query ="select count(*)from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.createdBy =:userCode";
			Session session = this.sessionFactory.getCurrentSession();
		    long count = ((Long)session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("userCode",user.getUserCode()).uniqueResult()).intValue();*/
		    
		    Query query = session.createQuery(queryString);
		    for (String str : query.getNamedParameters())
	        {
				if(str.equalsIgnoreCase("startDateUTC")){
					query.setParameter( str, (Date) params.get(str) );
				}
				if(str.equalsIgnoreCase("endDateUTC")){
					query.setParameter( str, (Date) params.get(str) );
				}
				
				if(str.equalsIgnoreCase("orgLocationId")){
					query.setParameter(str, (long) params.get(str));
				}
				
				if(str.equalsIgnoreCase("userCode")){
					query.setParameter(str, params.get(str));
				}
	        }
		    return (Long)query.uniqueResult();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByUUID(String candidateUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateById start");
		candidate = session.createQuery("from Candidate candidate where candidate.candidateUUID = :candidateUUID" ).setCacheable(true).setString("candidateUUID", candidateUUID ).setCacheable(true).list();
		logger.debug("getCandidateById end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveCandidatesByLocation(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId and cnadidate.status ='1' ORDER BY cnadidate.id DESC";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.status ='1' and candidate.isAssociate = 'Y' ORDER BY candidate.id DESC";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId,
			List<Long> associatesIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.id NOT IN (:associatesIds) ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameterList("associatesIds", associatesIds).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId, Long spocId,
			List<Long> associatesIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.spocId.id = :spocId and candidate.id NOT IN (:associatesIds) ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("spocId", spocId).setParameterList("associatesIds", associatesIds).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId, Long spocId) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.spocId.id = :spocId  ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("spocId", spocId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long vendorOrgRelationId,
			List<Long> associatesIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgRelationId.id =:vendorOrgRelationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.id NOT IN (:associatesIds) ";  
        return  session.createQuery(query).setLong("vendorOrgRelationId", vendorOrgRelationId).setParameterList("associatesIds", associatesIds).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long vendorOrgRelationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgRelationId.id =:vendorOrgRelationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' ";  
        return  session.createQuery(query).setLong("vendorOrgRelationId", vendorOrgRelationId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId, Long orgRelationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Candidate candidate = null;
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.consultantUser.id= :consultantUserId and candidate.orgRelationId.id= :orgRelationId";
        List<Candidate>  candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("consultantUserId", consultantUserId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		if(null != candidateList && candidateList.size() >0){
			candidate = candidateList.get(0);
		}
        return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByConsultantUserId(Long consultantUserId, Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Candidate candidate = null;
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.consultantUser.id= :consultantUserId";
        List<Candidate>  candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("consultantUserId", consultantUserId).setCacheable(true).list();
		if(null != candidateList && candidateList.size() >0){
			candidate = candidateList.get(0);
		}
        return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(Long orgLocationId, Long userId,
			String candidateType) throws Exception {
		
		logger.debug("getCandidatesCountByCandidateTypeOrgLocationIDAndUserId() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select count(*) from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.recruiterUserId.id = :userId and candidate.candidateType = :candidateType ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setString("candidateType", candidateType).setCacheable(true).list();
		logger.debug("getCandidatesCountByCandidateTypeOrgLocationIDAndUserId() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId, Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.recruiterUserId.id = :userId ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId, Long orgRelationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.orgRelationId.id = :orgRelationId and candidate.isAssociate = 'Y'";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
	}


	@SuppressWarnings("null")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getRequestedCandidatesList(Long orgLocationsId, List<Long> roleIds,
			String resourceType,List<Long> associatesIds,Long projectId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery1 = "";
		String subQuery2 = "";
		String subQuery3 = "";
		String subQuery4 = "";
		
		if(associatesIds != null && associatesIds.size() == 0){
			associatesIds = null;
		}
		
		if(roleIds != null){
			subQuery1 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id IN (:roleIds) )"; 
		}
		
		if(!isNullOrEmpty(resourceType)){
			String[] type = resourceType.split(",");
			if(type.length > 1){
				subQuery2 = " and ((candidate.isFavouriteAssociate = '1') OR (candidate.consultantUser.id IN (select employees.user.id from Employees employees where employees.orgLocations.id = :orgLocationsId and employees.id IN (select pm.employee.id from ProjectMembers pm where pm.projects.orgLocationsId.id = :orgLocationsId and pm.status = '1' ))))";
			}else{
				if(type[0].equalsIgnoreCase("hotlist")){
				subQuery2 = " and candidate.isFavouriteAssociate = '1' ";
				}else{
					subQuery2 = " and candidate.consultantUser.id IN (select employees.user.id from Employees employees where employees.orgLocations.id = :orgLocationsId and employees.id IN (select pm.employee.id from ProjectMembers pm where pm.projects.orgLocationsId.id = :orgLocationsId and pm.status = '1' ))  and candidate.orgLocationId.id = :orgLocationsId ";
				}
			}
		}
		
		subQuery3 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :orgLocationsId and userRole.userState = 'ACTIVE')";
		
		if(associatesIds != null){
			subQuery4 = " and candidate.id NOT IN (:associatesIds)";
		}
		
		String subQuery5 = " and candidate.consultantUser.id NOT IN (select employees.user.id from Employees employees where employees.orgLocations.id = :orgLocationsId and employees.id IN (select pm.employee.id from ProjectMembers pm where pm.projects.orgLocationsId.id = :orgLocationsId and pm.projects.id = :projectId and pm.status = '1' )) and candidate.orgLocationId.id = :orgLocationsId ";
		
		String subQuery6 = " and candidate.consultantUser.id NOT IN (select pah.approverUserId.id from ProjectApproverHierarchy pah where pah.projectId.id = :projectId) and candidate.orgLocationId.id = :orgLocationsId ";
		
		String query = "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "+subQuery1+" "+subQuery2+" "+subQuery3+" "+subQuery4+" "+subQuery5+" "+subQuery6+" ";
        
		if(roleIds != null && associatesIds == null){
			return  session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId).setParameterList("roleIds", roleIds).setCacheable(true).list();
		}
		else if(associatesIds != null && roleIds == null){
			return  session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId).setParameterList("associatesIds", associatesIds).setCacheable(true).list();
		}
		else if(associatesIds != null && roleIds != null){
			return  session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId).setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds).setCacheable(true).list();
		}
		else{
			return  session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId).setCacheable(true).list();
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate checkCandidateIdNumberInDatebase(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.id =(SELECT MAX(candi.id) FROM Candidate candi where candi.orgLocationId.id =:orgLocationId)";  
		List<Candidate> candidate  =  session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(candidate.isEmpty()){
			return null;
		}
		return candidate.iterator().next();	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCandidateGeneratedIdByOrgLocationId(Long orgLocationId,Integer generatedIdcount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'"+generatedIdcount+"') AS UNSIGNED)) FROM sourcelead.CANDIDATE WHERE ORG_LOCATION_ID = '"+ orgLocationId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
		
	}
	
	
	@Override
	@Transactional
	public List<Candidate> getAssociatesListForSow(Long locationId,String sowType)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> associatesList = null;
		if(Constants.Vendor_SOW.equalsIgnoreCase(sowType)){
			String query = "from Candidate can where can.orgLocationId.id =:locationId and can.isAssociate = 'Y' and can.orgRelationId <> NULL and can.spocId <> NULL and can.designationType IN ('C2C' , 'C2C-1099')";
		    associatesList = session.createQuery(query).setLong( "locationId", locationId).setCacheable(true).list();
		}else if(Constants.Client_SOW.equalsIgnoreCase(sowType)){
			String query = "from Candidate can where can.orgLocationId.id =:locationId and can.isAssociate = 'Y'";
		    associatesList = session.createQuery(query).setLong( "locationId", locationId).setCacheable(true).list();
		}
		return associatesList;
	}

	@Override
	@Transactional
	public Candidate getCandidateByParentCandidateId(Long parentCandidateId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateByParentCandidateId start");
		candidate = session.createQuery("from Candidate candidate  left join fetch candidate.orgRelationId where candidate.parentCandidateId.id =:parentCandidateId" ).setCacheable(true).setLong( "parentCandidateId", parentCandidateId ).setCacheable(true).list();
		logger.debug("getCandidateByParentCandidateId end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
   	
	}
}

