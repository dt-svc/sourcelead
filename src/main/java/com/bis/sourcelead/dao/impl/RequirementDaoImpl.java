package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
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
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class RequirementDaoImpl implements RequirementDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
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
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 * @param requirement This is the first paramter to storeRequirement method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement storeRequirement(Requirement requirement) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirement != null) {
				session.saveOrUpdate(requirement);
			}
			logger.debug("Requirement Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Requirement"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirement;
	}

	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get RequirementList by orgLocationId .
	 *  
	 * @param orgLocationId This is the first paramter to getRequirementListByOrgLocationID method
	 * 
	 * @param status This is the second paramter to getRequirementListByOrgLocationID method
	 * 
	 * @param clients This is the third paramter to getRequirementListByOrgLocationID method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,Long  userId) throws Exception {
		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
		
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		String from = "from Requirement requirement"; 
		
		String subQuery1 = "requirement.createdBy =:loginUserCode";
		params.put("loginUserCode",user.getUserCode());
		
		String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ";
		params.put("userId",userId);
		params.put("level",1);
		
		String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  )";
		params.put("approverUserId",userId);
		params.put("level1",1);
		params.put("level2",2);
		
		
		String subQuery4 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
		params.put("managerUserId",userId);
		
		
		
		String subQuery = "";
		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" ){
			subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") or ("+subQuery3+")  or ("+subQuery4+") )";
			params.put("orgLocationId",orgLocationId);
		}
		String whereCondition = subQuery;
		
		Query query = session.createQuery(from+" "+whereCondition);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
				query.setLong(str, (long) params.get( str ));
			}
			if(str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")){
				query.setInteger(str, (int) params.get( str ));
			}
			
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		
		return query.list();
		
		
		/*String query = "from Requirement requirement  where requirement.orgLocationId =:orgLocationId and  ( requirement.createdBy =:loginUserCode  or  )";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("loginUserCode", user.getUserCode()).setInteger("userId", userId.intValue()).list();
		*/
		
		
		/*String[] stat = status.split(",");
		ArrayList<Requirement> requirementList = new ArrayList<Requirement>();
		String[] client = clients.split(",");
		Integer ra = stat.length; 
		String subQuery2 = "";
		
		if(!Arrays.asList(stat).contains("")){
		
			if (Arrays.asList(stat).contains("New")) {
				subQuery2 = subQuery2 + "requirement.status = 'New' or ";
			}
			if (Arrays.asList(stat).contains("Draft")) {
				subQuery2 = subQuery2 + "requirement.status = 'Draft' or ";
			}
			if (Arrays.asList(stat).contains("Waiting for approval")) {
				subQuery2 = subQuery2 + "requirement.status = 'Waiting for approval ' or ";
			}
			if (Arrays.asList(stat).contains("Open")) {
				subQuery2 = subQuery2 + "requirement.status = 'Open' or  ";
			}
			if (Arrays.asList(stat).contains("Cancelled")) { 
				subQuery2 = subQuery2 + "requirement.status = 'Cancelled' or ";
			}
			if (Arrays.asList(stat).contains("On Hold")) {
				subQuery2 = subQuery2 + "requirement.status = 'On Hold' or ";
			}
			
			// to remove  the last conditional statement if any 
		    subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			String query = "from Requirement requirement  where  "+subQuery2+" and requirement.orgLocationId =:orgLocationId    ";
			return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}else{
			
			subQuery2 =  subQuery2 + "  ( requirement.status = 'New' or requirement.status = 'Draft' or requirement.status = 'Waiting for approval' or requirement.status = 'Open' or requirement.status = 'On Hold' or requirement.status = 'Cancelled' )";
			String query = "from Requirement requirement  where  requirement.orgLocationId =:orgLocationId  and  "+subQuery2+" ";
			return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}*/
	}

	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get Requirement by requirementId .
	 *  
	 * @param requirementId This is the first paramter to getRequirementByRequirementId method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation  where requirement.id =:requirementId";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	
	}
	
	
	@Override
    @Transactional(rollbackFor = { Exception.class })
    public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception {

        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation  where requirement.parentRequirement.id =:requirementId";
        List requirementList = session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
        
        Requirement requirement = null;
        if(requirementList != null && !requirementList.isEmpty())
        {
            requirement = (Requirement) requirementList.iterator().next();
        }
        return requirement;
    
    }
	
	/**
	 * @author venkat kesav
	 * @since  oct 21 2016
	 * @version 0.0.1
	 * This method for validate requirement by requirementId and SpocId. It returns boolean value
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(requirementId != null && spocId != null){
				String query = "from Requirement requirement where requirement.parentRequirement.id = :requirementId and requirement.spocId = :spocId ";
				List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId).setLong("spocId", spocId).setCacheable(true).list();
				if (requirementList != null && requirementList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement req where isTemplate = 'Y' and req.orgLocationId =:orgLocationId";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementAssignTo> getUserAssignJobCount(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from RequirementAssignTo reqassignto left join fetch reqassignto.teamId left join fetch reqassignto.roleId left join fetch reqassignto.assignUserId left join fetch reqassignto.requirementId  where reqassignto.assignUserId.id =:userId";
		return session.createQuery(query).setLong("userId", userId).list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode)
			throws Exception {

		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
		
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		
		String subQueryRole = "";
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			subQueryRole = "and (requirement.managerUserId.id =:userId OR requirement.createdBy =:userCode)";
			params.put("userId",userId);
			params.put("userCode",userCode);
			params.put("orgLocationId",orgLocationId);
		}else{
			params.put("orgLocationId",orgLocationId);
		}
		
		
		String from = "from Requirement requirement"; 
		
		String subQuery1 = "requirement.createdBy =:loginUserCode";
		params.put("loginUserCode",user.getUserCode());
		
		String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT' ) ";
		params.put("userId",userId);
		params.put("level",1);
		
		String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  and  requirement.requirementSubmissionStatus <>  'DRAFT'  )";
		params.put("approverUserId",userId);
		params.put("level1",1);
		params.put("level2",2);
		
		/*String subQuery4 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and requirement.requirementSubmissionStatus = 'APPROVED' ";
		params.put("userId",userId);*/
		
		
		String subQuery = "";
		if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" ){
			subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+") or ("+subQuery3+") ) "+subQueryRole;
			params.put("orgLocationId",orgLocationId);
		}
		String whereCondition = subQuery;
		
		Query query = session.createQuery(from+" "+whereCondition);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId")){
				query.setLong(str, (long) params.get( str ));
			}
			if(str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")){
				query.setInteger(str, (int) params.get( str ));
			}
			
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		
		return query.list();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, Long userId)
			throws Exception {
		 	
		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		String from = "from Requirement requirement"; 
		
		String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level )";
		params.put("userId",userId);
		params.put("level",1);
		
		String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))) ";
		params.put("approverUserId",userId);
		params.put("level1",1);
		params.put("level2",2);
			
		
		String subQuery = "";
		if(subQuery2 != "" || subQuery3 != "" ){
			subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery2+") or ("+subQuery3+") )";
			params.put("orgLocationId",orgLocationId);
		}
		String whereCondition = subQuery;
		
		Query query = session.createQuery(from+" "+whereCondition);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId")){
				query.setLong(str, (long) params.get( str ));
			}
			if(str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")){
				query.setInteger(str, (int) params.get( str ));
			}
			
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		
		return query.list();
		
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListById(Long requirementId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "from Requirement requ  left join fetch requ.spocId spoc   left join fetch spoc.spocUser left join fetch requ.parentRequirement where requ.parentRequirement.id =:requirementId";
		return session.createQuery(query).setLong("requirementId", requirementId).list();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer validateJobId(Long id) throws Exception {
	Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from Requirement requirement where requirement.jobId = :id";
		
		List<Long> jobCount =   session.createQuery(query.toString()).setLong("id", id).list();
			Integer count = 0;
			if(jobCount != null && !jobCount.isEmpty()){
					count = jobCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement checkRequirementIsReleasedOrNot(Long requirementId,Long orgLocationId) throws Exception {
		

        Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from Requirement requirement left join fetch requirement.parentRequirement  where requirement.parentRequirement.id =:requirementId and requirement.orgLocationId.id =:orgLocationId ";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateRequirementTitle(String jobName,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query ="from Requirement requirement where requirement.jobTitle = :jobName and requirement.orgLocationId.id =:orgLocationId";
		List<Requirement> requirementList = session.createQuery(query).setString("jobName", jobName).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(requirementList !=null && requirementList.size() >0){
			flag = true;
			return flag;
		}else{
			return flag;
		}
			
		
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId,User user,OrgLocations loginOrgLocations) throws Exception {
		
		logger.debug("getAllReleasedJobsForVendor start");
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if(orgRelationId != null && orgLocationId != null){
			
			UserRole userRole  = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), loginOrgLocations.getId());
			/*if("Admin".equalsIgnoreCase(userRole.getUserType())){*/
				query = "from Requirement requirement where requirement.orgLocationId.id =:orgLocationId and requirement.clientId.id =:orgRelationId and parentRequirement !='null'";
				releasedJobsList = session.createQuery(query).setLong( "orgLocationId", orgLocationId ).setLong( "orgRelationId", orgRelationId ).setCacheable(true).list();
			/*}else{
				query = "from Requirement requirement where requirement.orgLocationId.id =:orgLocationId and requirement.clientId.id =:orgRelationId and parentRequirement !='null' and requirement.spocId.createdBy =:loginUserCode";
				releasedJobsList = session.createQuery(query).setLong( "orgLocationId", orgLocationId ).setLong( "orgRelationId", orgRelationId ).setString( "loginUserCode", user.getUserCode() ).setCacheable(true).list();
			}*/
		}
        return releasedJobsList;
		
	}

	
	
}
