package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgRelations;
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
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId, Integer candidateState, String updatedDateRange ) throws Exception {

		
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
	         
			
   	    	 String query = "from Candidate cnadidate left join fetch cnadidate.orgLocationId orglocation where  cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "+subQuery+" and ( cnadidate.updatedDate >= :endDate)  ";  
		     candidateList = session.createQuery(query)
				                    .setDate("endDate", endDate)
				                    .setLong("orgLocationId", orgLocationId)
				                    .setLong("orgId", orgId)
				                    .setCacheable(true)
				                    .list();
		}else{
			
			String query = "from Candidate cnadidate  left join fetch cnadidate.orgLocationId orglocation where cnadidate.orgLocationId.id =:orgLocationId and  orglocation.org.id =:orgId and "+subQuery+"";  
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
		String query = "from Candidate cnadidate  where cnadidate.id =:candidateId";
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
		String query = "from Candidate cnadidate  where cnadidate.id = :candidateId and cnadidate.orgLocationId.id =:orgLocationId";  
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
		logger.debug("findByEmail start");
		try{
			candidate = session.createQuery("from Candidate can where can.email=:email and can.orgLocationId.id = :orgLocationId" )
					.setString( "email", email ).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
			
			logger.debug("findByEmail end");
			if (candidate.size() > 0) {
				return candidate.get(0);
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
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId) throws Exception {
		logger.debug("In getCandidatesByOrgLocationID method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidatesList = null;
		Query candidateListQuery= session.createQuery("from Candidate candidate where candidate.orgLocationId.id = :orgLocationId "
				+ "and candidate.status = '1' and candidate.id NOT IN (select submission.candidate.id from RequirementSubmission submission "
				+ "where submission.requirement.id = :requirementId) ");
		candidatesList = candidateListQuery.setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setCacheable(true).list();
		return candidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId";  
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}
	
}
