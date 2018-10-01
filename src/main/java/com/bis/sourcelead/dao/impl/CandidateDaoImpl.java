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
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ImmigrationCandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.SubmissionCandidateListView;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;

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
	private ProjectsDao ProjectsDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private CandidateResumesDao candidateResumesDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
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
	
	@Autowired
	private ReportsUtil reportsUtil;
	
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
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
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
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
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
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId, Integer candidateState, String updatedDateRange,String candidateType,String isAssociate,String candidateFilterSkills,String availability,String candidateTypeFilter,String createdByFilter,User user) throws Exception {

		
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
		
			if(candidateType != null && !candidateType.isEmpty() && candidateType.equalsIgnoreCase("Favourite")){
				candidateTypeSubQuery = " and (cnadidate.isFavourite = '1') ";
				
			}
			
	   String createdBySubQuery = "";
	   if(createdByFilter != null && !createdByFilter.isEmpty() && createdByFilter.equalsIgnoreCase("myCandidates")){
		   createdBySubQuery = " and cnadidate.createdBy = '"+user.getUserCode()+"' ";
	   }
	   
			
			List<String> skillsList = new ArrayList<>();
			if(StringUtils.isNotBlank(candidateFilterSkills)) {
				String[] skills = candidateFilterSkills.split(",");
				for(int i=0 ;i<skills.length; i++) {
					skillsList.add(skills[i]);
				}
				
			}else {
				skillsList.add("0");
			}
			
			
			List<String> avalibilityList = new ArrayList<>();
			if(StringUtils.isNotBlank(availability)) {
				String[] availabilityStr = availability.split(",");
				for(int i=0 ;i<availabilityStr.length; i++) {
					avalibilityList.add(availabilityStr[i]);
				}
				
			}else {
				avalibilityList.add("0");
			}
			
			
			String candidateTypeFilterSubQuery = "";	
			if(StringUtils.isNotBlank(candidateTypeFilter)) {
				String[] candidateTypeFilterStr = candidateTypeFilter.split(",");
				
				if(candidateTypeFilterStr.length == 2) {
					candidateTypeFilterSubQuery = " and (cnadidate.isAssociate = 'N' OR cnadidate.isAssociate = 'Y' )";
				}else {
					if(candidateTypeFilter.equalsIgnoreCase("Associates")){
						candidateTypeFilterSubQuery = " and cnadidate.isAssociate = 'Y'";
					}else if(candidateTypeFilter.equalsIgnoreCase("Candidates")){
						candidateTypeFilterSubQuery = " and cnadidate.isAssociate = 'N'";			
					}
				}
			}
			
			String candidateSkillsSubQuery = " and ((:varZero  IN (:skillsList)) OR (:varZero NOT IN (:skillsList) and cnadidate.skillsName IN (:skillsList)))";
			String avaliabilitySubQuery = " and ((:varZero  IN (:avalibilityList)) OR (:varZero NOT IN (:avalibilityList) and cnadidate.noticePeriod IN (:avalibilityList)))";
    	
			
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
	         
			
   	    	 String query = "from Candidate cnadidate left join fetch cnadidate.orgLocationId orglocation where  cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "+subQuery+" "+candidateTypeSubQuery+" "+candidateSkillsSubQuery+" "+avaliabilitySubQuery+" "+candidateTypeFilterSubQuery+" "+createdBySubQuery+" and ( cnadidate.updatedDate >= :endDate) ORDER BY cnadidate.id DESC ";  
		     candidateList = session.createQuery(query)
				                    .setDate("endDate", endDate)
				                    .setLong("orgLocationId", orgLocationId).setString("varZero", "0").setParameterList("skillsList", skillsList).setParameterList("avalibilityList", avalibilityList)
				                    .setLong("orgId", orgId)
				                    
				                    .list();
		}else{
			
			String query = "from Candidate cnadidate  left join fetch cnadidate.orgLocationId orglocation where cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "+subQuery+" "+candidateTypeSubQuery+" "+candidateSkillsSubQuery+" "+avaliabilitySubQuery+" "+candidateTypeFilterSubQuery+" "+createdBySubQuery+" ORDER BY cnadidate.id DESC";  
		    candidateList = session.createQuery(query)
					               .setLong("orgLocationId", orgLocationId)
					               .setLong("orgId", orgId).setString("varZero", "0").setParameterList("skillsList", skillsList).setParameterList("avalibilityList", avalibilityList).list();
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
		List candidateList = session.createQuery(query).setLong("candidateId", candidateId).list();
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
		List<Candidate> candidateList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
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
			candidate = session.createQuery("from Candidate can left join fetch can.consultantUser left join fetch can.orgRelationId left join fetch can.parentCandidateId left join fetch can.masterCandidateId where can.email=:email and can.orgLocationId.id = :orgLocationId ORDER BY can.id DESC" )
					.setString( "email", email ).setLong("orgLocationId", orgLocationId ).list();
			
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
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setString("candidateType", candidateType).list();
		return candidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId ORDER BY cnadidate.id DESC";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
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
				               .setLong("orgLocationId", orgLocationId).list();
		
		return candidateList;
	}


	@Override
	@Transactional
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId) throws Exception {
		
		logger.debug("In getAllRequestedCandidatesForCampaign method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from Candidate candidate  where candidate.id IN (:candidateId)";  
		List<Candidate> candidateList = session.createQuery(query)
				               .setParameterList("candidateId", candidateId).list();
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
				               .setLong("orgLocationId", orgLocationId).setLong("teamId", teamId).list();
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
				                 
				                .list();
	  /*  System.err.println("das"+candidateList.size());*/
	return candidateList;
	}

	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId, String candidateType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.consultantUser =:userId and candidate.candidateType =:candidateType ";  
		List<Candidate> candidateList =  session.createQuery(query).setLong("userId", userId).setString("candidateType", candidateType).list();
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
		List<Candidate> candidateList =  session.createQuery(query).setLong("recruiterUserId", recruiterUserId).setString("email", email).list();
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
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setString("candidateType", candidateType).list();
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
				+subQuery+" ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
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
		
		return query.list();
	}
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId , String employeementType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<Candidate> candidateList = null;
		
		String subQuery = " and candidate.id NOT IN (select onboarding.candidateId.id from CandidateOnBoarding onboarding where onboarding.orgLocationId.id = :orgLocationId and onboarding.candidateId IS NOT NULL )";
		
		if(!employeementType.equalsIgnoreCase("All")){
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1'  and candidate.designationType =:employeementType and candidate.consultantUser IS NULL "+subQuery+" ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("employeementType", employeementType).list();
		}else{
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1'  and candidate.consultantUser IS NULL "+subQuery+" "; 
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
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
		
		return query.list();
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
			List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
		
		    return candidateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from Candidate candidate where candidate.teamId.id =:teamId and candidate.orgLocationId.id =:orgLocationId  ";
		 List<Candidate> candidateHotList = session.createQuery(query.toString()).setLong( "teamId", teamId).setLong( "orgLocationId", orgLocationId).list();
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
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
		return candidateList;
	}

		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateById(Long candidateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateById start");
		candidate = session.createQuery("from Candidate candidate left join fetch candidate.consultantUser where candidate.id =:candidateId" ).setLong( "candidateId", candidateId ).list();
		logger.debug("getCandidateById end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public BigInteger getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId)
			throws Exception {
		
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		BigInteger count = null;
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			
			query ="SELECT"
					+ " COUNT(CAND.ID) AS CAND_COUNT"
					+ " FROM"
					    + " CANDIDATE CAND,"
					    + " ORG_LOCATIONS OL"
					    
						+ " WHERE  OL.ID = CAND.ORG_LOCATION_ID AND "
					    + " CAND.CREATED_BY = :userCode"
					    + " AND DATE_FORMAT(CAND.CREATED_DATE ,'%Y-%m-%d') BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
					    + " AND  OL.ORG_ID = :orgId"
					    + " GROUP BY CAND.CREATED_BY, OL.ORG_ID, CAND.ORG_LOCATION_ID";
				 
			        
					Query queryString = session.createSQLQuery(query);
					queryString.setParameter("userCode", user.getUserCode()).setParameter("orgId", orgId);
					count  = (BigInteger) queryString.uniqueResult();
					
		}else {
			
			query ="SELECT"
					+ " COUNT(CAND.ID) AS CAND_COUNT"
					+ " FROM"
					    + " CANDIDATE CAND "
						+ " WHERE"
					    + " CAND.CREATED_BY = :userCode"
					    + " AND DATE_FORMAT(CAND.CREATED_DATE ,'%Y-%m-%d') BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
					    + " AND CAND.ORG_LOCATION_ID = :orgLocationId"
					    + " GROUP BY CAND.CREATED_BY, CAND.ORG_LOCATION_ID";
			

					Query queryString = session.createSQLQuery(query);
					queryString.setParameter("userCode", user.getUserCode()).setParameter("orgLocationId", orgLocationId);
					count  = (BigInteger) queryString.uniqueResult();
			
			
		}
		
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByUUID(String candidateUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("getCandidateById start");
		candidate = session.createQuery("from Candidate candidate where candidate.candidateUUID = :candidateUUID" ).setString("candidateUUID", candidateUUID ).list();
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
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId and cnadidate.status ='1' and cnadidate.consultantUser !=null ORDER BY cnadidate.id DESC";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.status ='1' and candidate.isAssociate = 'Y' ORDER BY candidate.id DESC";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Map<String, BigInteger>> getAllActiveCandidatesByLocationName(Long orgLocationId, String name) {
		Session session = this.sessionFactory.getCurrentSession(); // below query is used for auto populate in UI.
		String query = "SELECT LOWER(CONCAT(FIRST_NAME,' ',LAST_NAME,'(',EMAIL,')')) as label, ID as value FROM CANDIDATE WHERE STATUS ='1' AND CONSULTANT_USER_ID IS NOT NULL AND ORG_LOCATION_ID =:orgLocationId AND ( EMAIL LIKE :name  OR LOWER(CONCAT(FIRST_NAME,LAST_NAME)) LIKE :name OR LOWER(CONCAT(FIRST_NAME,' ',LAST_NAME)) LIKE :name) ORDER BY FIRST_NAME";
		return session.createSQLQuery(query).setString("name", name+"%").setLong("orgLocationId", orgLocationId).setMaxResults(10).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId,
			List<Long> associatesIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.id NOT IN (:associatesIds) ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameterList("associatesIds", associatesIds).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId, Long spocId,
			List<Long> associatesIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.spocId.id = :spocId and candidate.id NOT IN (:associatesIds) ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("spocId", spocId).setParameterList("associatesIds", associatesIds).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId, Long spocId) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.spocId.id = :spocId  ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("spocId", spocId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long vendorOrgRelationId,
			List<Long> associatesIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgRelationId.id =:vendorOrgRelationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' and candidate.id NOT IN (:associatesIds) ";  
        return  session.createQuery(query).setLong("vendorOrgRelationId", vendorOrgRelationId).setParameterList("associatesIds", associatesIds).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long vendorOrgRelationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgRelationId.id =:vendorOrgRelationId and "
				+ "candidate.status ='1' and candidate.isAssociate = 'Y' ";  
        return  session.createQuery(query).setLong("vendorOrgRelationId", vendorOrgRelationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId, Long orgRelationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Candidate candidate = null;
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.consultantUser.id= :consultantUserId and candidate.orgRelationId.id= :orgRelationId";
        List<Candidate>  candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("consultantUserId", consultantUserId).setLong("orgRelationId", orgRelationId).list();
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
		try {
			String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.consultantUser.id= :consultantUserId";
	        List<Candidate>  candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("consultantUserId", consultantUserId).list();
			if(null != candidateList && candidateList.size() >0){
				candidate = candidateList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
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
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setString("candidateType", candidateType).list();
		logger.debug("getCandidatesCountByCandidateTypeOrgLocationIDAndUserId() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId, Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.recruiterUserId.id = :userId ";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId, Long orgRelationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.orgLocationId.id =:orgLocationId and candidate.orgRelationId.id = :orgRelationId and candidate.isAssociate = 'Y'";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).list();
	}


	@SuppressWarnings("null")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateDTO>  getRequestedCandidatesList(CandidateDTO candidateDTO,Long orgLocationsId, List<Long> roleIds,List<Long> teamIds,
			String resourceType,List<Long> associatesIds,Long projectId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		List<Candidate> candidateList = new ArrayList<>();
		ResponseList<CandidateDTO> response = new ResponseList<>();
		List<CandidateDTO> candidatesDTOList = new ArrayList<>();
		List<Long> countlist = null;
		StringBuilder query1 = new StringBuilder("");
		if(candidateDTO.getSearchValue() !=null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
			 query1.append(" and ").append("(").append(" candidate.firstName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.lastName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.primarySkill like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.email like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.designationType like '%" + candidateDTO.getSearchValue() + "%' ").append(")");
						
			}
		
		String subQuery1 = "";
		String subQuery2 = "";
		String subQuery3 = "";
		String subQuery4 = "";
		String subQuery6 = "";
		Query queryObj = null;
		
		if(associatesIds != null && associatesIds.size() == 0){
			associatesIds = null;
		}
		
		subQuery1 = " and ((:varZero  IN(:roleIds)) OR (:varZero NOT IN(:roleIds) and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id IN (:roleIds))))"; 
		subQuery4 = " and ((:varZero  IN(:associatesIds)) OR (:varZero NOT IN(:associatesIds) and candidate.id NOT IN (:associatesIds)))";
		subQuery6 = " and ((:varZero  IN(:teamIds)) OR (:varZero NOT IN(:teamIds) and  candidate.consultantUser.id IN (select teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.status = '1' and teamMembers.teamId.id IN (:teamIds))))";
		
		if(roleIds == null){
			roleIds = new ArrayList<>();
			roleIds.add(0L);
		}
		
		if(associatesIds == null){
			associatesIds = new ArrayList<>();
			associatesIds.add(0L);
		}
		
		if(teamIds == null){
			teamIds = new ArrayList<>();
			teamIds.add(0L); 
		}
		
		if(!isNullOrEmpty(resourceType)){
			String[] type = resourceType.split(",");
			if(type.length > 1){
				subQuery2 = " and ((candidate.isFavourite = '1') OR (candidate.consultantUser.id IN (select pm.projectMemberCandidteId.id from ProjectMembers pm where pm.projectMemberCandidteId.orgLocationId.id = :orgLocationsId and pm.status = '1' )))";
			}else{
				if(type[0].equalsIgnoreCase("hotlist")){
				subQuery2 = " and candidate.isFavourite = '1' ";
				}else{
					subQuery2 = " and candidate.consultantUser.id IN (select pm.projectMemberCandidteId.consultantUser.id from ProjectMembers pm where pm.projectMemberCandidteId.orgLocationId.id = :orgLocationsId and pm.status = '1' )  and candidate.orgLocationId.id = :orgLocationsId ";
				}
			}
		}
		
		subQuery3 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :orgLocationsId and userRole.userState != 'INACTIVE')";
		
		
		
		String subQuery5 = " and candidate.consultantUser.id NOT IN (select pm.projectMemberCandidteId.consultantUser.id from ProjectMembers pm where pm.projectClientsId.orgLocationsId.id = :orgLocationsId and pm.projectClientsId.projectId.id = :projectId and pm.projectMemberCandidteId.consultantUser IS NOT NULL ) and candidate.orgLocationId.id = :orgLocationsId ";
		
		// Dont delete this logic., It will be used further
		/*String subQuery6 = " and candidate.consultantUser.id NOT IN (select pah.approverUserId.id from ProjectApproverHierarchy pah where pah.projectId.id = :projectId) and candidate.orgLocationId.id = :orgLocationsId ";
		String query = "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "+subQuery1+" "+subQuery2+" "+subQuery3+" "+subQuery4+" "+subQuery5+" "+subQuery6+" ";
        */
		
		String query = "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "+subQuery1+" "+subQuery6+" "+subQuery2+" "+subQuery3+" "+subQuery4+" "+subQuery5+" "+query1.toString()+" ";
		
		queryObj = session.createQuery(query);
		queryObj.setFirstResult(candidateDTO.getStart());
		queryObj.setMaxResults(candidateDTO.getLength());
		queryObj.setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId).setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds).setParameterList("teamIds", teamIds).setLong("varZero", 0).list();
		candidateList = queryObj.list();
		
		countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId).setLong("projectId", projectId).setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds).setParameterList("teamIds", teamIds).setLong("varZero", 0).list();
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			filterCount = candidateList.size();
		}
		
			
			if(candidateList != null && candidateList.size() > 0) {
			 for(Candidate candidate : candidateList){
				 CandidateDTO candDTO = new CandidateDTO();
				 Integer projectsCount = ProjectsDao.getUserAssignedProjectsCount(candidate.getConsultantUser().getId(), candidateDTO.getOrgLocationId());
				 candDTO.setAssignedProjectsCount(projectsCount);
				 
				 UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(candidate.getConsultantUser().getId(), candidateDTO.getOrgLocationId());
				 candDTO.setUserRole(userRole.getRoles().getDisplayName());
				 if(userRole.getReportingManager() != null){
					 StringBuilder reportingManager = new StringBuilder();
					 if(!isNullOrEmpty(userRole.getReportingManager().getLastName())){
						 reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName()).append(" ").append(userRole.getReportingManager().getLastName());
					 }else{
						 reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName());
					 }
					 candDTO.setReportingManager(reportingManager.toString());
				 }else{
					 candDTO.setReportingManager("----");
				 }
				 
				 CandidateResumes candidateResumes = candidateResumesDao.getCandidateResumeByCandidateId(candidate.getId());
				 if(candidateResumes != null){
					 candDTO.setResumeUUID(candidateResumes.getResumeUUID());
					 candDTO.setFileUrl(candidateResumes.getFileURL());
					 candDTO.setFileName(candidateResumes.getFileName());
				 }
				 
				 candDTO.setId(candidate.getId());
				 candDTO.setFirstName(candidate.getFirstName());
				 candDTO.setLastName(candidate.getLastName());
				 candDTO.setPrimarySkill(candidate.getPrimarySkill());
				 candDTO.setDesignationType(candidate.getDesignationType());
				 candDTO.setDesignationOtherType(candidate.getDesignationOtherType());
				 candDTO.setCurrentRate(candidate.getCurrentRate());
				 candDTO.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
				 candDTO.setCurrentRateType(candidate.getCurrentRateType());
				 candDTO.setEmail(candidate.getEmail());
				 candDTO.setMobilePhone(candidate.getMobilePhone());
				 
				 candidatesDTOList.add(candDTO);
			 }
			}
			
			
            int totalCount = 0;
			
			if (candidateDTO != null) {
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
			
			
			response.setData(candidatesDTOList);
			return response;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate checkCandidateIdNumberInDatebase(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate where candidate.id =(SELECT MAX(candi.id) FROM Candidate candi where candi.orgLocationId.id =:orgLocationId)";  
		List<Candidate> candidate  =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
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
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'"+generatedIdcount+"') AS UNSIGNED)) FROM "+schema+".CANDIDATE WHERE ORG_LOCATION_ID = '"+ orgLocationId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		if(count == 0){
			 query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'7') AS UNSIGNED)) FROM "+schema+".CANDIDATE WHERE ORG_LOCATION_ID = '"+ orgLocationId + "'";
			 maxCountList = session.createSQLQuery(query).list();
			 if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
					count = (Long) maxCountList.iterator().next().longValue();
				}
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
		    associatesList = session.createQuery(query).setLong( "locationId", locationId).list();
		}else if(Constants.Client_SOW.equalsIgnoreCase(sowType)){
			String query = "from Candidate can where can.orgLocationId.id =:locationId and can.isAssociate = 'Y'";
		    associatesList = session.createQuery(query).setLong( "locationId", locationId).list();
		}
		return associatesList;
	}

	@Override
	@Transactional
	public Candidate getCandidateByParentCandidateId(Long parentCandidateId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		candidate = session.createQuery("from Candidate candidate left join fetch candidate.orgLocationId left join fetch candidate.orgRelationId where candidate.parentCandidateId.id =:parentCandidateId" ).setLong( "parentCandidateId", parentCandidateId ).list();
		logger.debug("getCandidateByParentCandidateId end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
   	
	}
	
	@Override
	@Transactional
	public void storeCandidateImportLog(CandidateImportLog candidateImportLog)  {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(candidateImportLog);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public List<CandidateImportLog> getCandidateImportLog(String uploadId, boolean all)  {
		List<CandidateImportLog> importLog = new ArrayList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			importLog = session.createQuery("from CandidateImportLog c where c.uploadId="+uploadId+(all ? "" : " and c.read = false")).list();
			if(importLog != null && !all) {
				for(CandidateImportLog c : importLog) {
					c.setRead(true);
					session.update(c);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return importLog;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByShortURL(String shortURL) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select Candidate from Candidate r where r.Candidate =:shortURL";
	    List<Candidate> Candidate = session.createQuery(query).setString("shortURL",shortURL).list();
		if(!Candidate.isEmpty()){
			return Candidate.get(0);
		}else{
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getCandidatesCountByLocation(Long orgLocationId) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from Candidate candidate where candidate.orgLocationId.id = :orgLocationId ";
		List<Long> candidateCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(candidateCount != null && !candidateCount.isEmpty()){
					count = candidateCount.get(0).intValue();
			}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllAssociatesByOrglocationIdAndEmploymentType(Long orgLocationId, String employeementType)
			throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<Candidate> candidateList = null;
		
		if(employeementType != null && employeementType != "" && employeementType != "undefined"){
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and candidate.isAssociate ='Y'  and candidate.designationType =:employeementType and candidate.consultantUser IS NOT NULL  ";
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("employeementType", employeementType).list();
		}else{
			query = "from Candidate candidate where candidate.orgLocationId.id = :orgLocationId and candidate.status = '1' and candidate.isAssociate ='Y' and candidate.consultantUser IS NOT NULL "; 
			candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}
		  
		
		return candidateList;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CandidateDTO> getLocationCandidatesList(CandidateDTO candidateDTO, Long orgLocationsId,
			List<Long> roleIds, List<Long> associatesIds,Long loginOrgLocationId) throws Exception {
		

		
		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		List<Candidate> candidateList = new ArrayList<>();
		ResponseList<CandidateDTO> response = new ResponseList<>();
		List<CandidateDTO> candidatesDTOList = new ArrayList<>();
		List<Long> countlist = null;
		StringBuilder query1 = new StringBuilder("");
		if(candidateDTO.getSearchValue() !=null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
			 query1.append(" and ").append("(").append(" candidate.firstName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.lastName like '%" + candidateDTO.getSearchValue() + "%' ")
					.append("OR candidate.email like '%" + candidateDTO.getSearchValue() + "%' ").append(")");
						
			}
		
		String subQuery1 = "";
		String subQuery2 = "";
		String subQuery3 = "";
		String subQuery4 = "";
		Query queryObj = null;
		
		if(associatesIds != null && associatesIds.size() == 0){
			associatesIds = null;
		}
		
		if(roleIds != null){
			subQuery1 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id IN (:roleIds) )"; 
		}
		
		subQuery2 = " and candidate.consultantUser.id NOT IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :loginOrgLocationId  )";
		
		subQuery3 = " and candidate.consultantUser.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :orgLocationsId and userRole.userState != 'INACTIVE')";
		
		if(associatesIds != null){
			subQuery4 = " and candidate.id NOT IN (:associatesIds)";
		}
		
		
		String query = "from Candidate candidate  where candidate.status = '1' and candidate.isAssociate = 'Y' and candidate.orgLocationId.id = :orgLocationsId "+subQuery1+" "+subQuery2+" "+subQuery3+" "+subQuery4+"  "+query1.toString()+" ";
		
		if(roleIds != null && associatesIds == null){
			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("roleIds", roleIds).list();
			candidateList = queryObj.list();
			
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("roleIds", roleIds).list();
		
		}
		else if(associatesIds != null && roleIds == null){
			
			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("associatesIds", associatesIds).list();
			candidateList = queryObj.list();
			
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("associatesIds", associatesIds).list();
		}
		else if(associatesIds != null && roleIds != null){
			
			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds).list();
			candidateList = queryObj.list();
			
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).setParameterList("roleIds", roleIds).setParameterList("associatesIds", associatesIds).list();
		}
		else{
			queryObj = session.createQuery(query);
			queryObj.setFirstResult(candidateDTO.getStart());
			queryObj.setMaxResults(candidateDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).list();
			candidateList = queryObj.list();
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationsId", orgLocationsId).setLong("loginOrgLocationId", loginOrgLocationId).list();
		}
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			filterCount = candidateList.size();
		}
		
			
			if(candidateList != null && candidateList.size() > 0) {
			 for(Candidate candidate : candidateList){
				 CandidateDTO candDTO = new CandidateDTO();
				 
				 UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(candidate.getConsultantUser().getId(), candidateDTO.getOrgLocationId());
				 candDTO.setUserRole(userRole.getRoles().getDisplayName());
				 if(userRole.getReportingManager() != null){
					 StringBuilder reportingManager = new StringBuilder();
					 if(!isNullOrEmpty(userRole.getReportingManager().getLastName())){
						 reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName()).append(" ").append(userRole.getReportingManager().getLastName());
					 }else{
						 reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName());
					 }
					 candDTO.setReportingManager(reportingManager.toString());
				 }else{
					 candDTO.setReportingManager("----");
				 }
				 
				 CandidateResumes candidateResumes = candidateResumesDao.getCandidateResumeByCandidateId(candidate.getId());
				 if(candidateResumes != null){
					 candDTO.setResumeUUID(candidateResumes.getResumeUUID());
					 candDTO.setFileUrl(candidateResumes.getFileURL());
					 candDTO.setFileName(candidateResumes.getFileName());
				 }
				 
				 candDTO.setId(candidate.getId());
				 StringBuilder candidateName = new StringBuilder();
				 if(StringUtils.isNotBlank(candidate.getLastName())) {
					 candidateName = candidateName.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
				 }else {
					 candidateName = candidateName.append(candidate.getFirstName());
				 }
				 
				 candDTO.setCandidateName(candidateName.toString());
				 candDTO.setCurrentRate(candidate.getCurrentRate());
				 candDTO.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
				 candDTO.setCurrentRateType(candidate.getCurrentRateType());
				 candDTO.setEmail(candidate.getEmail());
				 
				 candidatesDTOList.add(candDTO);
			 }
			}
			
			
            int totalCount = 0;
			
			if (candidateDTO != null) {
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
			
			
			response.setData(candidatesDTOList);
			return response;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.email = :email and candidate.candidateType = 'Candidate' and candidate.orgLocationId.id = :orgLocationId ";  
		List<Candidate> candidateList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("email", email).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByEmail(String email) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.email = :email  ";  
		List<Candidate> candidateList =  session.createQuery(query).setString("email", email).list();
		Candidate candidate = null;
		if (candidateList != null && candidateList.size() > 0) {
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })

	public CandidateIdendities getCandidateIdentitiesNumberByCandidateId(Long id, String idType) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateIdendities ci  where ci.candidateId.id = :candidateId and ci.idType = :idType ";  
		CandidateIdendities candidateIdendities =  (CandidateIdendities) session.createQuery(query).setLong("candidateId", id).setString("idType", idType).uniqueResult();
		return candidateIdendities;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByParentCandidateIdAndOrgLocationId(Long parentCandidateId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		candidate = session.createQuery("from Candidate candidate left join fetch candidate.orgLocationId left join fetch candidate.orgRelationId "
				+ " where candidate.parentCandidateId.id =:parentCandidateId and candidate.orgLocationId.id = :orgLocationId" ).setLong( "parentCandidateId", parentCandidateId ).setLong( "orgLocationId", orgLocationId ).list();
		logger.debug("getCandidateByParentCandidateId end");
		if (candidate.size() > 0) {
			return candidate.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getAllActiveCandidatesByLocationEmail(String email, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId and cnadidate.status ='1' and cnadidate.consultantUser !=null and cnadidate.email = :email ORDER BY cnadidate.id DESC";  
        return  (Candidate) session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("email", email).uniqueResult();
	}

	@Override
	@Transactional
	public List<RecruiterReportCandidateCountView> getCandidateCountsCreatedUserList(Long orgLocationId,
			List<String> userCodesList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception {
		
		
		


		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<RecruiterReportCandidateCountView> candidatesViewList = null;
		try {
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			
			 query ="SELECT "
						+" USER_CODE AS associateUserCode,SUM(CAND_COUNT) AS candCount"
						+" FROM"
						+" RECRUITER_REPORT_CANDIDATE_COUNT_VIEW"
						+" WHERE"
						+" ORG_ID = :orgId AND USER_CODE IN (:userCodesList)"
						+" AND CREATED_DATE BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
						+" GROUP BY USER_CODE";
			        
			
	            Query queryObj = session.createSQLQuery(query);
				queryObj.setParameterList("userCodesList", userCodesList).setParameter("orgId", orgId);
				queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterReportCandidateCountView.class));
				candidatesViewList = queryObj.list();
				
		}
		else {
			         query ="SELECT "
					+" USER_CODE AS associateUserCode,SUM(CAND_COUNT) AS candCount"
					+" FROM"
					+" RECRUITER_REPORT_CANDIDATE_COUNT_VIEW"
					+" WHERE"
					+" ORG_LOCATION_ID = :orgLocationId AND USER_CODE IN (:userCodesList)"
					+" AND CREATED_DATE BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
					+" GROUP BY USER_CODE";
		
			
		
            Query queryObj = session.createSQLQuery(query);
			queryObj.setParameterList("userCodesList", userCodesList).setParameter("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterReportCandidateCountView.class));
			candidatesViewList = queryObj.list();
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return  candidatesViewList;
		
	
		
		
		
		
	}

	@Override
	@Transactional
	public ResponseList<SubmissionCandidateListView> getSubmissionCandidateListView(CandidateDTO candidateDTO,
			OrgLocations orgLocations) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<SubmissionCandidateListView> response = new ResponseList<>();
		Query queryObj = null;
		String querySeachStr = "";
		StringBuilder advSeach = new StringBuilder("");
		String advSeachStr = "";
		List<SubmissionCandidateListView> candidateList = null;
		boolean flag = false;
		
		
		
		  StringBuilder query1 = new StringBuilder("");
				if(candidateDTO.getSearchValue() != null && !"".equalsIgnoreCase(candidateDTO.getSearchValue())) {
					 query1.append(" Q1.candidateName like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.email like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.employementType like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.vendorName like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.updatedDate like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.noticePeriod like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.primarySkill like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.country like '%" + candidateDTO.getSearchValue() + "%' ")
						.append("OR Q1.location like '%" + candidateDTO.getSearchValue() + "%' ");
					 
				}
				
				if(StringUtils.isNotBlank(candidateDTO.getPrimarySkill())) {
					String primarySkills [] = candidateDTO.getPrimarySkill().split(",");
					String primarySkillsStr = "'" + StringUtils.join(primarySkills,"','") + "'";
					advSeach.append(" Q1.primarySkill IN ("+primarySkillsStr+") ");
					flag = true;
				}
				
				if(StringUtils.isNotBlank(candidateDTO.getDesignationType())) {
					String designationTypes [] = candidateDTO.getDesignationType().split(",");
					String designationTypesStr = "'" + StringUtils.join(designationTypes,"','") + "'";
					if(flag == false) {
						advSeach.append(" Q1.employementType IN ("+designationTypesStr+") ");
						flag = true;
					}else {
						advSeach.append(" OR Q1.employementType IN ("+designationTypesStr+") ");
					}
					
				}
				
				if(candidateDTO.getNoticePeriod() != null && !candidateDTO.getNoticePeriod().isEmpty()) {
					String noticePeriod [] = candidateDTO.getNoticePeriod().split(",");
					String noticePeriodStr = "'" + StringUtils.join(noticePeriod,"','") + "'";
					if(flag == false) {
						advSeach.append(" Q1.noticePeriod IN ("+noticePeriodStr+") ");
						flag = true;
					}else {
						advSeach.append(" OR Q1.noticePeriod IN ("+noticePeriodStr+") ");
					}
					
				}
				
				if(StringUtils.isNotBlank(candidateDTO.getVendor())) {
					String orgRelationIds [] = candidateDTO.getVendor().split(",");
					String orgRelationIdStr = "'" + StringUtils.join(orgRelationIds,"','") + "'";
					if(flag == false) {
						advSeach.append(" Q1.orgRelationId IN ("+orgRelationIdStr+") ");
						flag = true;
					}else {
						advSeach.append(" OR Q1.orgRelationId IN ("+orgRelationIdStr+") ");
					}
					
				}
				
				
				if(StringUtils.isNotBlank(candidateDTO.getCreatedDate()) && !candidateDTO.getCreatedDate().equalsIgnoreCase("All")) {
					Date[] searchDates  = reportsUtil.getJobFilterDate(candidateDTO.getCreatedDate(),orgLocations);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String fromDate = sdf.format(searchDates[0]);
					String toDate = sdf.format(searchDates[1]);
					
					if(flag == false) {
						advSeach.append(" (Q1.updatedDate BETWEEN '"+fromDate+"' AND '"+toDate+"') ");
						flag = true;
					}else {
						advSeach.append(" OR (Q1.updatedDate BETWEEN '"+fromDate+"' AND '"+toDate+"') ");
					}
					
				 }
				
				
				if(StringUtils.isNotBlank(candidateDTO.getCandidateSkills())) {
					String skills [] = candidateDTO.getCandidateSkills().split(",");
					String skillsStr = "'" + StringUtils.join(skills,"','") + "'";
					if(flag == false) {
						advSeach.append(" Q1.skills IN ("+skillsStr+") ");
						flag = true;
					}else {
						advSeach.append(" OR Q1.skills IN ("+skillsStr+") ");
					}
					
				}
				
				
				if(StringUtils.isNotBlank(candidateDTO.getCity())) {
					String cities [] = candidateDTO.getCity().split(",");
					String citiesStr = "'" + StringUtils.join(cities,"','") + "'";
					if(flag == false) {
						advSeach.append(" Q1.city IN ("+citiesStr+") ");
						flag = true;
					}else {
						advSeach.append(" OR Q1.city IN ("+citiesStr+") ");
					}
					
				}
				
				
				String sortingQry = "";
				if(StringUtils.isNotBlank(candidateDTO.getSortColumn()))
				{
					sortingQry = " order by Q1."+ candidateDTO.getSortColumn() +" "+candidateDTO.getSortOrder();
				}
				
				if(query1.toString() != null && !query1.toString().isEmpty()){
					querySeachStr = "where "+"("+query1.toString()+")";
				}
				
				if((query1.toString() == null || query1.toString().isEmpty()) && advSeach.toString() != null && !advSeach.toString().isEmpty()){
					advSeachStr = "where "+"("+advSeach.toString()+")";
				}else if(query1.toString() != null && !query1.toString().isEmpty() && advSeach.toString() != null && !advSeach.toString().isEmpty()){
					advSeachStr = "and "+"("+advSeach.toString()+")";
				}
				
				try { 
					 query = "SELECT Q1.* " + 
					 		 "FROM (" + 
							 "SELECT " + 
					 		 "CAND.ID AS id, " + 
					 		 "CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName," + 
					 		 "CAND.GENERATED_ID AS generatedId, " + 
					 		 "CAND.EMAIL AS email, " + 
					 		 "CAND.DESIGNATION_TYPE AS employementType, " + 
					 		 "CAND.PRIMARY_SKILL AS primarySkill, " + 
					 		 "CAND.SKILLS_NAME AS skills, " + 
					 		 "CAND.ADDRESS AS location, " + 
					 		 "CAND.COUNTRY AS country, " + 
					 		 "CAND.CITY AS city, " + 
					 		 "CAND.NOTICE_PERIOD AS noticePeriod, " + 
					 		 "CAND.IS_ASSOCIATE AS isAssociate, " + 
					 		 "DATE_FORMAT(CAND.CREATED_DATE ,\"%Y-%m-%d\") AS createdDate, " + 
					 		 "DATE_FORMAT(CAND.UPDATED_DATE ,\"%Y-%m-%d\") AS updatedDate, " + 
					 		 "CAND.ORG_LOCATION_ID AS orgLocationId, " + 
					 		 "CAND.ORG_RELATION_ID AS orgRelationId, " + 
					 		 "OL.ORG_ID AS orgId, " + 
					 		 "(SELECT " + 
							 "ORG_NAME " + 
							 "FROM " + 
							 "ORG_LOCATIONS OL,ORG_RELATIONS ORL " + 
							 "WHERE " + 
							 "(OL.ID = ORL.LOCATION_ID OR OL.ID = ORL.RESPONDER_LOCATION_ID) AND OL.ID != :orgLocationId AND ORL.ID = CAND.ORG_RELATION_ID) AS vendorName " + 
					 		 "FROM " + 
					 		 "CANDIDATE CAND, " + 
					 		 "ORG_LOCATIONS OL " + 
					 		 "WHERE  CAND.ORG_LOCATION_ID = OL.ID  AND CAND.STATUS = '1' " + 
					 		 "AND CAND.ORG_LOCATION_ID = :orgLocationId " + 
					 		 "AND CAND.ID NOT IN (SELECT SUB.CANDIDATE_ID FROM REQUIREMENT_SUBMISSION SUB WHERE SUB.REQUIREMENT_ID = :requirementId) "+
					 		 "AND CAND.PROFILE_PERCENTAGE >= (SELECT REQ.PROFILE_PERCENTAGE FROM REQUIREMENT REQ WHERE REQ.ID = :requirementId) "+
					 		 "GROUP BY CAND.ID,  " + 
					 		 "DATE_FORMAT(CAND.CREATED_DATE ,\"%Y-%m-%d\"))Q1 "+querySeachStr+advSeachStr+sortingQry;
				
				 String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
							" FROM" + 
							"(" + query +
							")Q2;" ;
				 
				    queryObj = session.createSQLQuery(query);
					queryObj.setParameter("orgLocationId", orgLocations.getId());
					queryObj.setParameter("requirementId", candidateDTO.getRequirementId());
					queryObj.setResultTransformer(Transformers.aliasToBean(SubmissionCandidateListView.class));
					queryObj.setFirstResult(candidateDTO.getStart());
					queryObj.setMaxResults(candidateDTO.getLength());
					candidateList = queryObj.list();
					
					countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocations.getId()).setLong("requirementId", candidateDTO.getRequirementId()).list();
					filterCount = countlist.iterator().next().intValue();
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				int totalCount = 0;

				
				if (candidateDTO != null) {
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
				
				response.setData(candidateList);
				return response;
	}

	@Override
	@Transactional
	public CandidateDTO getCandidateDTOByCandidateId(Long candidateId) throws Exception {
		
		List<CandidateDTO> candidateDTOList = null;
		CandidateDTO candidateDTO = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				query ="SELECT "
				+" CAND.ID AS candId, "
				+" CAND.FIRST_NAME AS firstName, "
				+" CAND.LAST_NAME AS lastName, "
				+" CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName," 
				+" CAND.MOBILE_PHONE AS phoneNumber, "
				+" CAND.EMAIL AS email, "
				+" CAND.LOCATION AS location, "
				+" CAND.COUNTRY AS country, "
				+" CAND.STATE AS state, "
				+" CAND.CITY AS city, "
				+" CAND.NOTICE_PERIOD AS noticePeriod, "
				+" CAND.PROFILE_PERCENTAGE AS profilePercentage "
				+" FROM CANDIDATE CAND "
				+" WHERE "
				+" CAND.ID = :candidateId ";
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("candidateId", candidateId);
			queryObj.setResultTransformer(Transformers.aliasToBean(CandidateDTO.class));
			candidateDTOList = queryObj.list();
			if(candidateDTOList != null && !candidateDTOList.isEmpty()) {
				candidateDTO = candidateDTOList.get(0);
			}
			
			return candidateDTO;
	
	}

	@Override
	@Transactional
	public List<CandidateDTO> getCandidateListForAddApplicant(OrgLocations orgLcoations,
			Long requirementId,String skills,String candidateDetails,Integer profilePercentage) throws Exception {
		
		List<CandidateDTO> candidateDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		String querySeachStr = "";
		
		  StringBuilder query1 = new StringBuilder("");
			if(candidateDetails != null && !"".equalsIgnoreCase(candidateDetails)) {
				 query1.append(" CAND.FIRST_NAME like '%" + candidateDetails + "%' ")
					.append("OR CAND.LAST_NAME like '%" + candidateDetails + "%' ")
					.append("OR CAND.EMAIL like '%" + candidateDetails + "%' ")
					.append("OR CAND.MOBILE_PHONE like '%" + candidateDetails + "%' ");
				 
			}
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				querySeachStr = " AND "+"("+query1.toString()+")";
			}
			
		if(StringUtils.isNotBlank(skills)) {
			String requirementSkills [] = skills.split(",");
			//String requirementSkillsStr = "'" + StringUtils.join(requirementSkills,"','") + "'";
			//subQuery = " AND CAND.SKILLS_NAME IN ("+requirementSkillsStr+")";
			
			int i = 0;
			int j = requirementSkills.length;
			for(String rSkill : requirementSkills) {
				i++;
				if(i == 1) {
					subQuery = subQuery + " AND (";
				}else {
					subQuery = subQuery + " OR ";
				}
				subQuery = subQuery + " CAND.SKILLS_NAME LIKE '%"+rSkill+"%' ";
				if(i == j) {
					subQuery = subQuery + " )";
				}
			}
		}
		
		String	query ="SELECT "
				+" CAND.ID AS candId, "
				+" CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName," 
				+" CAND.IS_ASSOCIATE AS isAssociate "  
				+" FROM CANDIDATE CAND "
				+" WHERE CAND.STATUS = '1' " 
		 		+" AND CAND.ORG_LOCATION_ID = :orgLocationId " 
		 		+" AND CAND.PROFILE_PERCENTAGE >= :profilePercentage "
				+subQuery
		 		+ " AND CAND.ID NOT IN (SELECT SUB.CANDIDATE_ID FROM REQUIREMENT_SUBMISSION SUB WHERE SUB.REQUIREMENT_ID = :requirementId) "
				+ " ORDER BY CAND.ID DESC "; 
		
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLcoations.getId());
			queryObj.setLong("requirementId", requirementId);
			queryObj.setInteger("profilePercentage", profilePercentage);
			queryObj.setMaxResults(5000);
			queryObj.setResultTransformer(Transformers.aliasToBean(CandidateDTO.class));
			candidateDTOList = queryObj.list();
			return candidateDTOList;
	
	}

	@Override
	@Transactional
	public List<CandidateDTO> getAllActiveCandidatesListByOrglocationId(Long orgLocationId, String employeementType)
			throws Exception {
		
		List<CandidateDTO> candidateDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
			
		if(!employeementType.equalsIgnoreCase("All")){
			query ="SELECT CAND.ID AS candId,"
					 + "CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName"
				     +" FROM CANDIDATE CAND "
					 +" WHERE CAND.ORG_LOCATION_ID = :orgLocationId"
					 +" AND CAND.DESIGNATION_TYPE = '"+employeementType+"' AND CAND.STATUS = '1' AND CAND.CONSULTANT_USER_ID IS NULL"
					 +" AND CAND.ID NOT IN (SELECT ONBOARDING.CANDIDATE_ID FROM CANDIDATE_ON_BOARDING ONBOARDING WHERE ONBOARDING.ORG_LOCATION_ID = :orgLocationId AND ONBOARDING.CANDIDATE_ID IS NOT NULL)";
		}else {
			query ="SELECT CAND.ID AS candId,"
					 + "CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS candidateName"
				     +" FROM CANDIDATE CAND "
					 +" WHERE CAND.ORG_LOCATION_ID = :orgLocationId"
					 +" AND CAND.STATUS = '1' AND CAND.CONSULTANT_USER_ID IS NULL"
					 +" AND CAND.ID NOT IN (SELECT ONBOARDING.CANDIDATE_ID FROM CANDIDATE_ON_BOARDING ONBOARDING WHERE ONBOARDING.ORG_LOCATION_ID = :orgLocationId AND ONBOARDING.CANDIDATE_ID IS NOT NULL)";
		}
				
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(CandidateDTO.class));
			candidateDTOList = queryObj.list();
			return candidateDTOList;
	}

	@Override
	@Transactional
	public List<Candidate> getAllCandidatesList() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidates  ";
		List<Candidate> candidatesList = session.createQuery(query).list();
		return candidatesList;
	
	}

	@Override
	@Transactional
	public Long getCandidateIdByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception {
		
		Long candidateId = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" CAND.ID "
				+" FROM CANDIDATE CAND "
				+" WHERE "
				+" CAND.EMAIL = :email AND CAND.ORG_LOCATION_ID = :orgLocationId";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("email", email);	
		queryObj.setLong("orgLocationId", orgLocationId);	
		BigInteger id = (BigInteger) queryObj.uniqueResult();
		if(id != null) {
			candidateId = id.longValue();
		}
		
		return candidateId;
	}

	@Override
	@Transactional
	public void saveBulkCandidates(List<Candidate> candidatesList,Long orgLocationId) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		for (Candidate candidate : candidatesList) {
			Long candidateId = getCandidateIdByEmailAndOrgLocationId(candidate.getEmail().trim(),orgLocationId);
			if(candidateId == null) {
				    session.save(candidate);
				    candidateService.saveOrUpdateCandidateElkDocument(candidate);
			}
		    
		}
	}

	@Override
	@Transactional
	public List<String> getCandidateEmailsByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" CAND.EMAIL "
				+" FROM CANDIDATE CAND "
				+" WHERE "
				+" CAND.ORG_LOCATION_ID = :orgLocationId";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);	
		List<String> emailsList = (List<String>)queryObj.list();
		
		return emailsList;
	}

}

