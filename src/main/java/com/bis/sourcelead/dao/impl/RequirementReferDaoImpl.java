package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementReferDao;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementReferDaoImpl implements RequirementReferDao {

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
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementReferDaoImpl.class);
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	@Transactional
	public RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception {
		logger.debug("Adding addRequirementRefer");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementRefer != null) {
				session.saveOrUpdate(requirementRefer);
			}
			logger.debug("RequirementRefer Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_REFER"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementRefer;
	}

	@Override
	@Transactional
	public Boolean isProfessionalReferToRequirement(String email, Long requirementId, Long userId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where user.id = :userId and requirement.id = :requirementId and refer.referToEmail = :email"; 
		List<RequirementRefer> requirementReferList = session.createQuery(query.toString()).setString("email", email).setLong("requirementId", requirementId).setLong("userId", userId).list();
         boolean isReferFound = false;
		if (requirementReferList != null && !requirementReferList.isEmpty()) {
			isReferFound = true;
         }
         return isReferFound;
	}

	@Override
	@Transactional
	public Boolean isReferToRequirement(String fromEmail, Long requirementId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where requirement.id = :requirementId and refer.referFromEmail = :email"; 
		List<RequirementRefer> requirementReferList = session.createQuery(query.toString()).setString("email", fromEmail).setLong("requirementId", requirementId).list();
         boolean isReferFound = false;
		if (requirementReferList != null && !requirementReferList.isEmpty()) {
			isReferFound = true;
         }
         return isReferFound;
	}

	@Override
	@Transactional
	public RequirementRefer getProfessionalReferToRequirement(String email, Long requirementId, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementRefer> requirementRefer = new ArrayList<RequirementRefer>();
		logger.debug("getProfessionalReferToRequirement start");
		requirementRefer = session.createQuery("from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where user.id = :userId and requirement.id = :requirementId and refer.referToEmail = :email" ).setString("email", email).setLong("requirementId", requirementId).setLong("userId", userId).list();
		logger.debug("getProfessionalReferToRequirement end");
		if (requirementRefer.size() > 0) {
			return requirementRefer.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public List<Long> getReferByMeRequirementsByUserId(Long userId,Integer offset,Integer maxResults) throws Exception {
		
		logger.debug("In getReferByMeRequirementsByUserId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select distinct refer.requirementId.id from RequirementRefer refer  where refer.referFromUser.id = :userId ";
		List<Long> modulesIdList =  session.createQuery(query.toString()).setLong("userId", userId)
									.list();
		

		/*if(modulesIdList.size() >= offset){
			if(modulesIdList.size() > offset + maxResults){
				return   modulesIdList.subList(offset, (offset+maxResults));
			}else{
				return   modulesIdList.subList(offset, (offset + (modulesIdList.size() - offset)));
			}
		}*/
		
		return modulesIdList;
	}

	@Override
	@Transactional
	public List<RequirementRefer> getRequirementReferListByEmail(String email) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = " from RequirementRefer refer where refer.referToEmail = :email ";
		List<RequirementRefer> referList = session.createQuery(query.toString()).setString("email", email).list();
        return referList;
	}

	@Override
	@Transactional
	public List<RequirementRefer> getReferedToMeRequirementsByUserId(Long userId,Integer offset,Integer maxResults,String jobTitle,String location,String postedOn) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		
		String jobTitleSubQuery = "";
		if(!isNullOrEmpty(jobTitle)){
			jobTitleSubQuery = " and requirement.jobTitle LIKE '%" + jobTitle + "%'";
		}
		
		String locationSubQuery = "";
		if(!isNullOrEmpty(location)){
			locationSubQuery = " and (requirement.country LIKE  '" + location + "%' OR requirement.state LIKE '" + location + "%' OR requirement.city LIKE '" + location + "%' )";
		}
		
		String postedOnSubQuery = "";
		if(!isNullOrEmpty(postedOn)){
			postedOn = UpdateOnToDate(postedOn);
			postedOnSubQuery = " and requirement.createdDate >= '" + postedOn + "'";
		}
		
		String query = " from RequirementRefer refer left join fetch refer.referToUser left join fetch refer.referFromUser left join fetch refer.requirementId requirement "
				+ "where refer.referToUser.id = :userId and requirement.requirementSubmissionStatus = 'OPEN' " + jobTitleSubQuery + " " + locationSubQuery + " " + postedOnSubQuery + "  ";
		List<RequirementRefer> referList = session.createQuery(query.toString()).setLong("userId", userId).list();
		
		if(referList.size() >= offset){
				if(referList.size() > offset + maxResults){
					return   referList.subList(offset, (offset+maxResults));
				}else{
					return   referList.subList(offset, (offset + (referList.size() - offset)));
				}
		}
		
        return referList;
	}
	
	
	 public String UpdateOnToDate(String postedOn){
			
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     if((Constants.ONE_DAY_AGO).equalsIgnoreCase(postedOn)){
	        cal.add(Calendar.DATE, -1);    
	        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_DAY_AGO).equalsIgnoreCase(postedOn)){
			 cal.add(Calendar.DATE, -3);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_WEEK_AGO).equalsIgnoreCase(postedOn)){
			 cal.add(Calendar.DATE, -7);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.TWO_WEEK_AGO).equalsIgnoreCase(postedOn)){
			 cal.add(Calendar.DATE, -14);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_MONTH_AGO).equalsIgnoreCase(postedOn)){
			 cal.add(Calendar.DATE, -30);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_MONTH_AGO).equalsIgnoreCase(postedOn)){
			 cal.add(Calendar.DATE, -90);    
		        return  dateFormat.format(cal.getTime());
		 }else{
			 return postedOn;
		 }
	 }


	@Override
	@Transactional
	public RequirementRefer getRequirementReferById(Long referId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementRefer> requirementRefer = new ArrayList<RequirementRefer>();
		logger.debug("getRequirementReferById start");
		requirementRefer = session.createQuery("from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where refer.id = :referId" ).setLong("referId", referId).list();
		logger.debug("getRequirementReferById end");
		if (requirementRefer.size() > 0) {
			return requirementRefer.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional
	public RequirementRefer getAppliedRequirementReferByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementRefer> requirementRefer = new ArrayList<RequirementRefer>();
		logger.debug("getAppliedRequirementReferByRequirementIdAndUserId start");
		requirementRefer = session.createQuery("from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "left join fetch refer.referFromUser toUser where toUser.id = :userId and requirement.id = :requirementId and refer.referStatus = 'Applied' " ).setLong("userId", userId).setLong("requirementId", requirementId).list();
		logger.debug("getAppliedRequirementReferByRequirementIdAndUserId end");
		if (requirementRefer.size() > 0) {
			return requirementRefer.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional
	public Integer getReferJobsByReferType(Long userId, String referType, Date startDateFormat, Date endDateFormat)throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
		 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
		 
		 if(referType.equalsIgnoreCase("referToMe")){
			 subQuery = "refer.referToUser.id = :userId ";
		 }else if(referType.equalsIgnoreCase("referByMe")){
			 subQuery = "refer.referFromUser.id = :userId ";
		 }
	     
		 String query = "select  count(distinct refer.requirementId) from RequirementRefer refer where "+subQuery+" and (refer.createdDate BETWEEN :startDate and :endDate))";
	     
	     List<Long> referCount = session.createQuery(query).setLong("userId", userId)
	    		   .setDate("startDate", startDate)
				   .setDate("endDate", endDate).list();
		 Integer count= 0;
		 
		 if(referCount != null && !referCount.isEmpty()){
				count = referCount.get(0).intValue();
		 }
		return count;
	}


	@Override
	@Transactional
	public Integer getReferJobsByReferTypeByReferedDate(Long userId, String referType, Date referedDate)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 
		 if(referType.equalsIgnoreCase("referToMe")){
			 subQuery = "refer.referToUser.id = :userId ";
		 }else if(referType.equalsIgnoreCase("referByMe")){
			 subQuery = "refer.referFromUser.id = :userId ";
		 }
		 
		 java.sql.Date ourJavaDateObject = new java.sql.Date(referedDate.getTime());
	     
		 String query = "select  count(distinct refer.requirementId) from RequirementRefer refer where "+subQuery+" and DATE(refer.createdDate) = :referedDate ";
	     
	     List<Long> referCount = session.createQuery(query).setLong("userId", userId)
				   .setDate("referedDate", ourJavaDateObject).list();
		 Integer count= 0;
		 
		 if(referCount != null && !referCount.isEmpty()){
				count = referCount.get(0).intValue();
		 }
		return count;
	}


	@Override
   @Transactional
	public List<RequirementRefer> getReferJobsListByReferTypeByReferedDate(Long userId, String referType,
			Date referedDate) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 
		 if(referType.equalsIgnoreCase("referToMe")){
			 subQuery = "refer.referToUser.id = :userId ";
		 }else if(referType.equalsIgnoreCase("referByMe")){
			 subQuery = "refer.referFromUser.id = :userId ";
		 }
		 
		 java.sql.Date ourJavaDateObject = new java.sql.Date(referedDate.getTime());
		 String query = "from RequirementRefer refer left join fetch refer.requirementId  where "+subQuery+" and DATE(refer.createdDate) = :referedDate ";
		 List<RequirementRefer>  referCountList = session.createQuery(query).setLong("userId", userId).setDate("referedDate", ourJavaDateObject).list();
		return referCountList;
	}


	@Override
	@Transactional
	public List<RequirementRefer> getReferJobsListByReferType(Long userId, String referType, Date startDate,
			Date endDate) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 
		 if(referType.equalsIgnoreCase("referToMe")){
			 subQuery = "refer.referToUser.id = :userId ";
		 }else if(referType.equalsIgnoreCase("referByMe")){
			 subQuery = "refer.referFromUser.id = :userId ";
		 }
		 String query = "from RequirementRefer refer left join fetch refer.requirementId where "+subQuery+" and (refer.createdDate BETWEEN :startDate and :endDate))";
		 List<RequirementRefer>  referCountList = session.createQuery(query).setLong("userId", userId) .setDate("startDate", startDate).setDate("endDate", endDate).list();
		return referCountList;
	}
	
	@Override
	@Transactional
	public Integer getReferredByMeCountByStartDateAndEndDate(Long userId, Date startDateFormat, Date endDateFormat)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
		 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());    
		 String query = "select  count(*) from RequirementRefer refer where refer.referFromUser.id = :userId  and (refer.createdDate BETWEEN :startDate and :endDate))";
	     
	     List<Long> referCount = session.createQuery(query).setLong("userId", userId)
	    		   .setDate("startDate", startDate)
				   .setDate("endDate", endDate).list();
		 Integer count= 0;
		 
		 if(referCount != null && !referCount.isEmpty()){
				count = referCount.get(0).intValue();
		 }
		return count;

	}
	
	@Override
	@Transactional
	public List<RequirementRefer> getReferredByMeCountListByStartDateAndEndDate(Long userId, Date startDateFormat, Date endDateFormat) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 java.sql.Timestamp startDate = new java.sql.Timestamp(startDateFormat.getTime());
		 java.sql.Timestamp endDate = new java.sql.Timestamp(endDateFormat.getTime());
		 String query = "from RequirementRefer refer left join fetch refer.requirementId left join fetch refer.referToUser left join fetch refer.referFromUser  where refer.referFromUser.id = :userId  and (refer.createdDate BETWEEN :startDate and :endDate))";
		 List<RequirementRefer> referCountList = session.createQuery(query).setLong("userId", userId).setDate("startDate", startDate).setDate("endDate", endDate).list();
		
		return referCountList;
	}


	@Override
	@Transactional
	public List<RequirementRefer> getLoginUserReferredListByUserId(Long userId, Long requirementId) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 
		 String query = "from RequirementRefer refer left join fetch refer.requirementId left join fetch refer.referToUser where "
		 		+ "refer.referFromUser.id = :userId and refer.requirementId.id = :requirementId)";
		 List<RequirementRefer>  referCountList = session.createQuery(query).setLong("userId", userId) .setLong("requirementId", requirementId).list();
		return referCountList;
	}


	@Override
	@Transactional
	public List<RequirementRefer> getLoginUserReferToMeListByUserId(Long userId) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		 
		 String query = "from RequirementRefer refer left join fetch refer.requirementId left join fetch refer.referToUser where refer.referToUser.id = :userId ";
		 List<RequirementRefer>  referList = session.createQuery(query).setLong("userId", userId).list();
		return referList;
	
	}


	@Override
	@Transactional
	public RequirementRefer getRequirementReferByReferUUID(String referUUID) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementRefer> requirementRefer = new ArrayList<RequirementRefer>();
		logger.debug("getRequirementReferByReferUUID start");
		requirementRefer = session.createQuery("from RequirementRefer refer left join fetch refer.referFromUser user left join fetch refer.requirementId requirement "
				+ "where refer.referUUID = :referUUID" ).setString("referUUID", referUUID).list();
		logger.debug("getRequirementReferByReferUUID end");
		if (requirementRefer.size() > 0) {
			return requirementRefer.get(0);
		} else {
			return null;
		}
	}
}
