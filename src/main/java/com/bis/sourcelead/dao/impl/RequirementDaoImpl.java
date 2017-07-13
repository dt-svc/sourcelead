package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
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
	private UserDao userDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,User user,String userRoleName) throws Exception {
		
		List<Requirement> reqList = null;
		Session session = this.sessionFactory.getCurrentSession();
		//User user = userDao.getUserById(userId);
		
//		UserRole userRole1 = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		String from = "from Requirement requirement  left join fetch requirement.parentRequirement  left join fetch requirement.clientId left join fetch requirement.spocId left join fetch requirement.submissionTemplateId left join fetch requirement.clientSubmissionTemplateId left join fetch requirement.parentRequirement"; 
		String subQuery = "";
		
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			subQuery = " where requirement.orgLocationId.id =:orgLocationId and (requirement.isTemplate IS NULL OR requirement.isTemplate <> 'Y') ";
			params.put("orgLocationId",orgLocationId);
		}else{
			String subQuery1 = "requirement.createdBy =:loginUserCode and (requirement.isTemplate IS NULL OR requirement.isTemplate <> 'Y') ";
			params.put("loginUserCode",user.getUserCode());
			
			String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ";
			params.put("userId",user.getId());
			params.put("level",1);
			
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  )";
			params.put("approverUserId",user.getId());
			params.put("userId",user.getId());
			params.put("level1",1);
			params.put("level2",2);
			
			String subQuery4 = "requirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT'";
			params.put("managerUserId",user.getId());
			
			String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",user.getId());
			
			String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",user.getId());
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 != "" ){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (requirement.releaseJobToAllUsers = 'Y') OR ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery4+") OR ("+subQuery5+") OR ("+subQuery6+") )";
				params.put("orgLocationId",orgLocationId);
			}
		}
		
		String[] client = null;
		if(null != clients && !"".equals(clients)){	
			client = clients.split(",");
		}
		List<Long> clientsList = new ArrayList<>();
		if(client != null){
			for(int i=0;i<client.length;i++){
				clientsList.add(Long.parseLong(client[i]));
			}
		}
		
		String subQuery7 = "";
		if((null == status || "".equals(status)) && (null == clients || "".equals(clients))){
			subQuery7 = " and (requirement.requirementSubmissionStatus ='NEW' or requirement.requirementSubmissionStatus = 'SUBMITTED' or requirement.requirementSubmissionStatus = 'DRAFT' or requirement.requirementSubmissionStatus = 'OPEN') ";
		}
		
		Query query = null;
		if(clientsList.isEmpty()){
			query = session.createQuery(from+" "+subQuery+" "+subQuery7+" ORDER BY requirement.id DESC");
		}
		else{
			query = session.createQuery(from+" "+subQuery+" "+subQuery7+"and requirement.clientId.id IN (:clientList) ORDER BY requirement.id DESC");
			params.put("clientsList",clientsList);
		}
		
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
				query.setLong(str, (long) params.get( str ));
			}
			if(str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")){
				query.setInteger(str, (int) params.get( str ));
			}
			if(!clientsList.isEmpty()){
				query.setParameterList("clientList", clientsList);
			}
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		
		reqList = query.setCacheable(true).list();		
		return reqList;
		
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
		
		String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch requirement.orgLocationId  where requirement.id =:requirementId";
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
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
				String query = "from Requirement requirement where requirement.parentRequirement.id = :requirementId and requirement.spocId = :spocId ";
				List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId).setLong("spocId", spocId).setCacheable(true).list();
		 return requirementList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement  req  left join fetch req.parentRequirement  where req.isTemplate = 'Y' and req.orgLocationId =:orgLocationId and req.status<>'0'";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from RequirementAssignTo reqassignto left join fetch reqassignto.teamId left join fetch reqassignto.roleId left join fetch reqassignto.assignUserId left join fetch reqassignto.requirementId  where reqassignto.assignUserId.id =:userId and reqassignto.roleId.orgLocations.id =:orgLocationId ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode)
			throws Exception {

		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
		
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		
		/*String subQueryRole = "";*/
		String from = "from Requirement requirement left join fetch requirement.parentRequirement "; 
		String subQuery = "";
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId",userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId",userId);
			
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",userId);
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 !=""){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")  or ("+subQuery3+") ) ";
				params.put("orgLocationId",orgLocationId);
			}
		}else{
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";
			
			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId",userId);
			
			if(subQuery1 != "" || subQuery2 !=""){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")) ";
				params.put("orgLocationId",orgLocationId);
			}
		}
		
		
		
		
		String whereCondition = subQuery;
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")){
				query.setLong(str, (long) params.get( str ));
			}
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		return query.setCacheable(true).list();
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(Long orgLocationId, String userType, Long userId, String userCode)
			throws Exception {
		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		/*String subQueryRole = "";*/
		String from = "from Requirement requirement left join fetch requirement.parentRequirement "; 
		String subQuery = "";
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId",userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId",userId);
			
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",userId);
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 !=""){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")  or ("+subQuery3+") ) ";
				params.put("orgLocationId",orgLocationId);
			}
		}else{
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";
			
			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId",userId);
			
			if(subQuery1 != "" || subQuery2 !=""){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")) ";
				params.put("orgLocationId",orgLocationId);
			}
		}
		
		String whereCondition = subQuery;
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY requirement.id DESC");
		query.setMaxResults(10);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")){
				query.setLong(str, (long) params.get( str ));
			}
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		return query.setCacheable(true).list();
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode,String statusType)
			throws Exception {

		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
		
		 String subQuery1  = null;
		 String subQuery2 = null;
		Map<String, Object> params = new HashMap<String,Object>();
		
		String from = "from Requirement requirement left join fetch requirement.parentRequirement "; 
		String subQuery3 = "";
		String finalQuery = "";
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			
			if(StringUtils.equalsIgnoreCase(statusType, "SUBMITTED")){
				
				    subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus <> 'APPROVED' and  (requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus = '"+statusType+"' )) ";
		            params.put("userId",userId);
		            params.put("level",1);
		        
		            subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus <> 'APPROVED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT' AND requirement.requirementSubmissionStatus <> 'OPEN' ) and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
		            params.put("approverUserId",userId);
		            params.put("level1",1);
		            params.put("level2",2);
		            
			}
            if(StringUtils.equalsIgnoreCase(statusType, "APPROVED")){
				
            	subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus = '"+statusType+"' and  (requirement.requirementSubmissionStatus <> 'DRAFT')) ";
	            params.put("userId",userId);
	            params.put("level",1);
	        
	            subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus = 'APPROVED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT') and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
	            params.put("approverUserId",userId);
	            params.put("level1",1);
	            params.put("level2",2);
			}
           if(StringUtils.equalsIgnoreCase(statusType, "REJECTED")){
				
        	    subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus = 'REJECTED' and  (requirement.requirementSubmissionStatus <> 'DRAFT')) ";
	            params.put("userId",userId);
	            params.put("level",1);
	        
	            subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus = 'REJECTED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT' ) and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
	            params.put("approverUserId",userId);
	            params.put("level1",1);
	            params.put("level2",2);
			}
		
			
		}else{
			    if(StringUtils.equalsIgnoreCase(statusType, "REJECTED")){
			    	statusType = "REJECTED";
			    }
			    if(StringUtils.equalsIgnoreCase(statusType, "APPROVED")){
			    	statusType = "OPEN";
			    }
			    if(StringUtils.equalsIgnoreCase(statusType, "SUBMITTED")){
					
				    subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus <> 'APPROVED' and  (requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus = '"+statusType+"' )) ";
		            params.put("userId",userId);
		            params.put("level",1);
		        
		            subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus <> 'APPROVED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT' AND requirement.requirementSubmissionStatus <> 'OPEN' ) and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
		            params.put("approverUserId",userId);
		            params.put("level1",1);
		            params.put("level2",2);
		            
			}
				subQuery3 = " requirement.requirementSubmissionStatus <> 'DRAFT' and  requirement.requirementSubmissionStatus = '"+statusType+"' ";
		}
		
		if(subQuery1 != null || subQuery2 != null ){
			
			StringBuilder sb =  new StringBuilder();
			finalQuery = sb.append("where requirement.orgLocationId.id =:orgLocationId and ").append("( (").append(subQuery1).append(")").append("or").append("(").append(subQuery2).append(") )").toString();
			
			 //finalQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")) ";
			 params.put("orgLocationId",orgLocationId);
		}else{
		  	 StringBuilder sb1 =  new StringBuilder();
		  	finalQuery = sb1.append("where requirement.orgLocationId.id =:orgLocationId and ").append("(").append(subQuery3).append(")").toString();
			 //finalQuery = "where requirement.orgLocationId.id =:orgLocationId and  ("+subQuery3+") ";
			 params.put("orgLocationId",orgLocationId);
		}
		
		String whereCondition = finalQuery;
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY requirement.id DESC");
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
		return query.setCacheable(true).list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, Long userId)
			throws Exception {
		 	
		 
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		String from = "from Requirement requirement left join fetch requirement.parentRequirement "; 
		
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
		return query.setCacheable(true).list();
		
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListById(Long requirementId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "from Requirement requ  left join fetch requ.parentRequirement left join fetch requ.spocId spoc    left join fetch requ.parentRequirement where requ.parentRequirement.id =:requirementId";
		return session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		
	}

	@Override
    @Transactional(rollbackFor = { Exception.class })
    public Requirement validateExistingJobId(String jobId, Long requirementId, Long orgLocationId) throws Exception {
            Session session = this.sessionFactory.getCurrentSession();
            String query ="from Requirement requirement left join fetch requirement.parentRequirement where requirement.id = :requirementId and requirement.jobId = :jobId and requirement.orgLocationId.id =:orgLocationId";
            List requirementList = session.createQuery(query).setLong("requirementId", requirementId).setString("jobId", jobId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
            Requirement requirement = null;
            if(requirementList != null && !requirementList.isEmpty())
            {
                requirement = (Requirement) requirementList.iterator().next();
            }
            return requirement;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer validateJobId(String id,Long orgLocationId) throws Exception {
	Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from Requirement requirement where requirement.jobId = :id and requirement.orgLocationId.id =:orgLocationId";
		
		List<Long> jobCount = session.createQuery(query.toString()).setString("id", id).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
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
		
		String query = "from Requirement requirement left join fetch requirement.parentRequirement  where requirement.parentRequirement.id =:requirementId";
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
	public Boolean validateRequirementTitle(String jobName,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query ="from Requirement requirement left join fetch requirement.parentRequirement  where requirement.jobTitle = :jobName and requirement.isTemplate = 'Y' and requirement.orgLocationId.id =:orgLocationId";
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
			
//			UserRole userRole  = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), loginOrgLocations.getId());
			/*if("Admin".equalsIgnoreCase(userRole.getUserType())){*/
				query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId  and requirement.clientId.id IN (  select orgRelation.id from OrgRelations orgRelation where orgRelation.locationId.id =:orgLocationId )and requirement.parentRequirement IS NOT NULL group by requirement.clientId.id, requirement.parentRequirement.id";
				releasedJobsList = session.createQuery(query).setLong( "orgLocationId", orgLocationId ).setLong( "orgRelationId", orgRelationId ).setCacheable(true).list();
			/*}else{
				query = "from Requirement requirement where requirement.orgLocationId.id =:orgLocationId and requirement.clientId.id =:orgRelationId and parentRequirement !='null' and requirement.spocId.createdBy =:loginUserCode";
				releasedJobsList = session.createQuery(query).setLong( "orgLocationId", orgLocationId ).setLong( "orgRelationId", orgRelationId ).setString( "loginUserCode", user.getUserCode() ).setCacheable(true).list();
			}*/
		}
        return releasedJobsList;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query ="from Requirement requirement where requirement.id = :requirementId and requirement.managerUserId.id =:userId";
		List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId).setLong("userId", userId).setCacheable(true).list();
		if(requirementList !=null && requirementList.size() >0){
			flag = true;
			return flag;
		}else{
			return flag;
		}
			
	
	}

	/**
	 * prasad K
	 * This Method for to get All Template Requirements
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement req  left join fetch req.parentRequirement where req.isTemplate = 'Y' and req.orgLocationId.id =:orgLocationId and req.status <>'0'  ORDER BY req.id DESC ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementTemplateByRequirementId(Long requirementId,Long orgLocationId) throws Exception {
        
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from Requirement requirement  left join fetch requirement.parentRequirement  where requirement.id =:requirementId and requirement.isTemplate = 'Y' and requirement.orgLocationId.id =:orgLocationId ";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}


	@Override
	@Transactional
	public Integer getManagerAssignedRequirementsCount(Long userId, Long orgLocationId) throws Exception {
		logger.debug("getManagerAssignedRequirementsCount start");
		Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(requirement.id) from Requirement requirement "
					+ "where requirement.managerUserId.id = :userId and requirement.orgLocationId.id = :orgLocationId ";
			
			List<Long> jobCount = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			Integer count = 0;
			if(jobCount != null && !jobCount.isEmpty()){
					count = jobCount.get(0).intValue();
			}
			return count;
        
	}

	@Override
	@Transactional
	public Integer getRequirementsCountAsApprover(Long userId,Long orgLocationId) throws Exception {
		logger.debug("getRequirementsCountAsApprover start");
		Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(requirement.id) from Requirement requirement "
					+ "where requirement.id IN (select hierarchy.requirementId.id from RequirementApproverHierarchy hierarchy where hierarchy.approverUserId.id = :userId) and requirement.orgLocationId.id = :orgLocationId ";
			
			List<Long> jobCount = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			Integer count = 0;
			if(jobCount != null && !jobCount.isEmpty()){
					count = jobCount.get(0).intValue();
			}
			return count;
        
	}

	@Override
	@Transactional
	public List<Requirement> getManagerAssignedRequirementsList(Long userId, Long orgLocationId) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join f"
				+ "etch requirement.parentRequirement "
					+ "where requirement.managerUserId.id = :userId and requirement.orgLocationId.id = :orgLocationId";
		return session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
	}
	
	@Override
	@Transactional
	public Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId, Long orgLocationId) throws Exception {
		Boolean check = false;
		Session session = sessionFactory.getCurrentSession();       
		String subQuery = "requirement.id IN ( select rs.requirement.id from RequirementSubmission rs where  rs.submissionRateStatus = 'REQUEST' or rs.submissionRateStatus = 'ACCEPTED' ) ";
		String query = "from Requirement requirement left join fetch requirement.parentRequirement   where requirement.managerUserId.id = :userId and requirement.orgLocationId.id = :orgLocationId and "+subQuery+" ";
		List requirementList = session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			check = true;
		}
		return check;
		
	}

	@Override
	@Transactional
	public Requirement getRequirementByRequirementIdOrgLocationsId(String id, Long requirementId, Long orgLocationsId)
			throws Exception {
		 logger.debug("In EmployeesDaoImpl class getEmployeeByIdAndUserIdAndOrgLocationsId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Requirement requirement left join fetch requirement.parentRequirement where requirement.orgLocationId = :orgLocationsId and requirement.jobId = :id and requirement.id = :requirementId ";
			List<Requirement> requirementList =  session.createQuery(query) .setString("id", id).setLong("requirementId", requirementId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
			Requirement requirement = null;
			if(requirementList != null && !requirementList.isEmpty())
			{
				requirement = (Requirement) requirementList.iterator().next();
			}
			return requirement;
	}

	@Override
	@Transactional
	public Boolean validateJobid(String id, Long orgLocationsId) throws Exception {
		 logger.debug("In Employee getEmployeeByEmployeeId method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(id != null && orgLocationsId != null){
				String query = "from Requirement requirement where requirement.orgLocationId = :orgLocationsId and requirement.jobId = :id ";
				List<Requirement> requirementList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setString("id", id).setCacheable(true).list();

				if (requirementList != null && requirementList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementSkillsByRequirementI(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.skills from Requirement req where req.id =:requirementId";
	    List<String> skillNames = session.createQuery(query).setLong( "requirementId", requirementId).setCacheable(true).list();
        return skillNames;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.industryName from Requirement req where req.id =:requirementId";
	    List<String> industryNames = session.createQuery(query).setLong( "requirementId", requirementId).setCacheable(true).list();
        return industryNames;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getRequirementListByOrgLocationID(Long orgLocationId) {
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select count(*) from Requirement requirement  where requirement.orgLocationId.id =:orgLocationId and requirement.status = '1'";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementUUID(String requirementUUID) throws Exception {
    Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.orgLocationId loc left join fetch loc.org left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation  where requirement.requirementUUID =:requirementUUID";
		List requirementList = session.createQuery(query).setString("requirementUUID", requirementUUID).setCacheable(true).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementsByOrglocationId(Long orgLocationId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
			String query = "from Requirement requirement where requirement.orgLocationId.id = :orgLocationId";
			return session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedRequirementsByParentId(Long parentRequirementId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
			String query = "from Requirement requirement where requirement.parentRequirement.id = :parentRequirementId";
			return session.createQuery(query).setLong("parentRequirementId", parentRequirementId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getNoticePeriodByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.noticePeriod from Requirement req where req.id =:requirementId";
	    List<String> noticePeriodList = session.createQuery(query).setLong( "requirementId", requirementId).setCacheable(true).list();
	    return noticePeriodList.get(0);
	}
	
	
	

@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserId(Long orgLocationId,User user)
			throws Exception {
		
		     Session session = this.sessionFactory.getCurrentSession();
		
			 String requirementQuery = "from Requirement req where  ( (req.managerUserId.id =:userId)  or (req.createdBy =:userCode) or (req.id IN (select  reqAssignTo.requirementId.id  from RequirementAssignTo reqAssignTo  where  reqAssignTo.assignUserId.id =:userId ) ) or (req.id IN (select  reqApproverHirerarchy.requirementId.id  from RequirementApproverHierarchy reqApproverHirerarchy  where  reqApproverHirerarchy.approverUserId.id =:userId ) ) ) and req.orgLocationId.id =:orgLocationId  ";
	         List<Requirement> requirementList = session.createQuery(requirementQuery.toString())
	        		                                    .setLong( "userId", user.getId())
	        		                                    .setLong( "orgLocationId", orgLocationId)
	        		                                    .setString("userCode", user.getUserCode())
	        		                                    .setCacheable(true)
	        		                                    .list();
  
        return requirementList;
	}

     @Override
     @Transactional(rollbackFor = { Exception.class })
		public List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId,User user)
				throws Exception {
			
			      Session session = this.sessionFactory.getCurrentSession();
			     
			      String requirementQuery = "from Requirement req where  (req.id IN (select  reqAssignTo.requirementId.id  from RequirementAssignTo reqAssignTo  where  reqAssignTo.teamId.id IN ( select tm.id from Team  tm where tm.ownerUserId.id =:userId and tm.orgLocationsId.id =:orgLocationsId )) ) ";
		          List<Requirement> requirementList = session.createQuery(requirementQuery.toString())
		        		                                     .setLong( "userId", user.getId())
		        		                                     .setLong( "orgLocationId", orgLocationId)
		        		                                     .setCacheable(true)
		        		                                     .list();
		
			return requirementList;
		}


     @Override
 	 @Transactional(rollbackFor = { Exception.class })
 	public Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user)
 			throws Exception {
 		
 		Session session = this.sessionFactory.getCurrentSession();
 		
 		 java.sql.Date sqlCurrentDate = null;
 	     Date currentDate =  commonUtil.currentDate();
 		// for current Date Converting to Sql Date Format
 		 if(currentDate != null){
 				sqlCurrentDate = new java.sql.Date(currentDate.getTime());
 				java.sql.Timestamp sq = new java.sql.Timestamp(currentDate.getTime());
 		 }
 		
 		  String requirementQuery = "select count(*)  from Requirement req where   req.spocId.clientLocationId.id =:orgLocationId and  req.spocId.clientUser.id =:userId  and DATE(req.createdDate) =:sqlCurrentDate GROUP BY req.id";
           List<Long> requirementList = session.createQuery(requirementQuery.toString())
         		                                     .setLong( "userId", user.getId())
         		                                     .setDate("sqlCurrentDate", sqlCurrentDate)
         		                                     .setLong( "orgLocationId", orgLocationId)
         		                                     .setCacheable(true)
         		                                     .list();
         Integer count = 0;
   		if(requirementList != null && !requirementList.isEmpty()){
   				count = requirementList.get(0).intValue();
   		}
   		return count;
 	}
     
     
	 	@Override
	 	@Transactional(rollbackFor = { Exception.class })
	 	public List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(Long orgLocationId,  TeamMembers teamMembers) throws Exception {
	 		
	 		Session session = this.sessionFactory.getCurrentSession();
	 		
	 		String query = "from Requirement requirement left join fetch requirement.parentRequirement where   requirement.orgLocationId.id =:orgLocationId and  requirement.id  IN (select reqassign.requirementId.id  from RequirementAssignTo reqassign where reqassign.assignUserId.id =:userId  and reqassign.teamId.id =:teamId)";
	 		
	         List<Requirement> requirementList = session.createQuery(query).setLong("userId", teamMembers.getMemberUserId().getId()).setLong("teamId", teamMembers.getTeamId().getId()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	 		return requirementList;
	 	}

		@Override
		 @Transactional(rollbackFor = { Exception.class })
		public Long getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user,Date startDateUTC, Date endDateUTC)
				throws Exception {
			
			Map<String, Object> params = new HashMap<String,Object>();
			String subQueryDateRange = "";
			
			if(null != startDateUTC && null != endDateUTC){
				subQueryDateRange = "and (requirement.createdDate >= :startDateUTC and requirement.createdDate <= :endDateUTC)";
				  params.put("startDateUTC",startDateUTC);
				  params.put("endDateUTC",endDateUTC);
			}
			
			
		    String queryString ="select count(*)from Requirement requirement where requirement.orgLocationId.id =:orgLocationId and requirement.createdBy =:userCode "+subQueryDateRange+" ";
			Session session = this.sessionFactory.getCurrentSession();
		    params.put("orgLocationId",orgLocationId);
		    params.put("userCode",user.getUserCode());
			
			/*String query ="select count(*)from Requirement requirement where requirement.orgLocationId.id =:orgLocationId and requirement.createdBy =:userCode";
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
		public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId,Date startDateUTC, Date endDateUTC) {
			Session session = this.sessionFactory.getCurrentSession();
	 		
	 		String query = "from Requirement requirement left join fetch requirement.parentRequirement where   requirement.clientId.id =:relationId and  requirement.spocId.id =:spocId and ( requirement.createdDate >= :startDateUTC and requirement.createdDate <= :endDateUTC )";
	        List<Requirement> requirementList = session.createQuery(query).setLong("relationId", relationId).setLong("spocId", spocId).setParameter("startDateUTC", startDateUTC).setParameter( "endDateUTC", endDateUTC ).setCacheable(true).list();
	 		return requirementList;
		}
		
		@Override
	 	@Transactional(rollbackFor = { Exception.class })
	 	public List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception {
	 		
	 		Session session = this.sessionFactory.getCurrentSession();
	 		String subQuery = "";
	 		if(sowType.equalsIgnoreCase("Client SOW")){
	 			subQuery = "and requirement.isClientJob = 'Y' and requirement.clientId <> null ";
	 		}else if(sowType.equalsIgnoreCase("Vendor SOW")){
	 			subQuery = " ";
	 		}
	 		
	 		String query = "from Requirement requirement left join fetch requirement.parentRequirement where (requirement.isTemplate = null or requirement.isTemplate = 'N') and requirement.orgLocationId.id =:orgLocationId "+subQuery+" ORDER BY requirement.id DESC";
	 		
	         List<Requirement> requirementList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	 		return requirementList;
	 	}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<String> getAllRequirementEducationByRequirementId(Long requirementId){
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select req.education from Requirement req where req.id =:requirementId";
		    List<String> educationList = session.createQuery(query).setLong( "requirementId", requirementId).setCacheable(true).list();
	        return educationList;
		}
		
		@Transactional(rollbackFor = { Exception.class })
		public Requirement getRequirementDetailsById(long requirementId){
			
			Session session = this.sessionFactory.getCurrentSession();
			Requirement  requirement =  null;
			List<Requirement>  requirementList = session.createQuery("from Requirement req where req.id =:requirementId").setLong("requirementId", requirementId).setCacheable(true).list();
			if(null != requirementList && requirementList.size() >0){
				requirement = requirementList.get(0);
			}
			return requirement;
		}

	
		@SuppressWarnings("unchecked")
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays)
				throws Exception {
			Date currentDate =  commonUtil.currentDate();
			java.sql.Date sqlCurrentDate = null;
			
			 if(currentDate != null){
					sqlCurrentDate = new java.sql.Date(currentDate.getTime());
			 }
			
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     Calendar c = Calendar.getInstance();
		      c.setTime(new Date()); // Now use today date.
		    c.add(Calendar.DATE, -Integer.parseInt(numberOfDays)); // Adding  The DateRange days
		    String updatedDate     = dateFormat.format(c.getTime());
		    Date parsed = dateFormat.parse(updatedDate);
		    java.sql.Date endDate = new java.sql.Date(parsed.getTime());
		
		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
				
		Map<String, Object> params = new HashMap<String,Object>();
		
		/*String subQueryRole = "";*/
		String from = "from Requirement requirement left join fetch requirement.parentRequirement "; 
		String subQuery = "";
		//String dateBetween = "(requirement.createdDate BETWEEN :sqlCurrentDate AND :endDate) and ";
		String dateBetween = "requirement.createdDate >=:endDate and requirement.createdDate <= :sqlCurrentDate and ";
		
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId",userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId",userId);
			
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",userId);
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 !=""){
				subQuery = "where "+dateBetween+" requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")  or ("+subQuery3+") ) ";
				params.put("orgLocationId",orgLocationId);
				params.put("sqlCurrentDate",sqlCurrentDate);
				params.put("endDate",endDate);
			}
		}else{
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";
			
			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId",userId);
			
			if(subQuery1 != "" || subQuery2 !=""){
				subQuery = "where "+dateBetween+" requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") or ("+subQuery2+")) ";
				params.put("orgLocationId",orgLocationId);
				params.put("sqlCurrentDate",sqlCurrentDate);
				params.put("endDate",endDate);
			}
		}
		
		String whereCondition = subQuery;
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")){
				query.setLong(str, (long) params.get( str ));
			}
			else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		return query.setCacheable(true).list();
		
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId,
				Long orgRelationId, Date startDateUTC, Date endDateUTC) {
			logger.debug("getAllReleasedJobsForVendor start");
			Session session = this.sessionFactory.getCurrentSession();
			List<Requirement> releasedJobsList = null;
			String query = "";
			if(orgRelationId != null && orgLocationId != null){
				query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId and ( requirement.createdDate >= :startDateUTC and requirement.createdDate <= :endDateUTC ) and requirement.parentRequirement IS NOT NULL group by requirement.clientId.id, requirement.parentRequirement.id";
				releasedJobsList = session.createQuery(query).setLong( "orgRelationId", orgRelationId ).setParameter("startDateUTC", startDateUTC).setParameter( "endDateUTC", endDateUTC ).setCacheable(true).list();
			}
	        return releasedJobsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Requirement> getAllReleasedJobsBasedOnUserIdAndLocationId(User user, OrgLocations orgLocations)
				throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<Requirement> releasedJobsList = null;
			String query = "";
			if(user != null && orgLocations != null){
				query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.orgLocationId =:orgLocationId and :userId IN (requirement.releasedBy)";
				releasedJobsList = session.createQuery(query).setLong( "orgLocationId", orgLocations.getId()).setLong( "userId", user.getId() ).setCacheable(true).list();
			}
	        return releasedJobsList;
		}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId, Long relationId, String userRoleName, User user, String status) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String from = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId";
		String subQuery = "";
		if (userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName)
				|| "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
			subQuery = " where requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("locationId", locationId);
			params.put("relationId", relationId);
		}
		else {
			String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("userId", user.getId());
			params.put("level", 1);
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ) and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y'";
			params.put("approverUserId", user.getId());
			params.put("userId", user.getId());
			params.put("level1", 1);
			params.put("level2", 2);
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery4 = "requirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("managerUserId", user.getId());
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED' and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("userId", user.getId());
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y'";
			params.put("userId", user.getId());
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			if (subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != ""
					|| subQuery6 != "") {
				subQuery = "where requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and (("
						+ subQuery2 + ") OR (" + subQuery3 + ") OR (" + subQuery4 + ") OR ("
						+ subQuery5 + ") OR (" + subQuery6 + ") )";
				params.put("locationId", locationId);
				params.put("relationId", relationId);
			}
		}
		String[] requirementStatus = status.split(",");
		String subQuery8 = "";
		if (Arrays.asList(requirementStatus).contains("NEW")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'NEW' or ";
		}
		if (Arrays.asList(requirementStatus).contains("OPEN")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'OPEN' or ";
		}
		if (Arrays.asList(requirementStatus).contains("SUBMITTED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'SUBMITTED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("FILLEDCLOSED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'FILLEDCLOSED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("UNFILLEDCLOSED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'UNFILLEDCLOSED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("ONHOLD")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'ONHOLD' or ";
		}
		if (Arrays.asList(requirementStatus).contains("CLOSED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'CLOSED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("CANCEL")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'CANCEL' or ";
		}
		if (Arrays.asList(requirementStatus).contains("REJECTED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'REJECTED' or ";
		}
		if ((requirementStatus[0] != null && requirementStatus[0].length() > 0)) {
			subQuery8 = "and (" + subQuery8.substring(0, subQuery8.length() - 4) + ")";
		}
		/*
		 * String subQuery7 = ""; if(null !=status &&
		 * !"".equalsIgnoreCase(status)){ subQuery7 =
		 * "and (requirement.requirementSubmissionStatus ='NEW' or requirement.requirementSubmissionStatus = 'SUBMITTED' or requirement.requirementSubmissionStatus = 'DRAFT' or requirement.requirementSubmissionStatus = 'OPEN')"
		 * ; }
		 */
		Query query = null;
		query = session.createQuery(from + " " + subQuery + " " + subQuery8
				+ "  ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("relationId") || str.equalsIgnoreCase("locationId")
					|| str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId")
					|| str.equalsIgnoreCase("managerUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1")
					|| str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			}
			else {
				query.setParameter(str, params.get(str));
			}
		}
		releasedJobsList = query.setCacheable(true).list();
		return releasedJobsList;
	}
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Requirement> getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(List<Long> parentreqIds,
				Long relationId) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<Requirement> releasedJobsList = null;
			String query = "";
			if(parentreqIds != null && !parentreqIds.isEmpty() && relationId != null){
				query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.parentRequirement.id IN (:parentreqIds) and requirement.clientId.id =:relationId";
				releasedJobsList = session.createQuery(query).setParameterList("parentreqIds", parentreqIds).setLong("relationId", relationId).setCacheable(true).list();
			}
	        return releasedJobsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();

			String query = "from Requirement requirement where ( requirement.closedDate >= :startDateUTC and requirement.closedDate <= :endDateUTC ) ";
			return session.createQuery(query).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
		}
		
		
		
		
		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId, Long orgRelationId, String userRoleName, User user, String status)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String[] reqStatus = null;
		if (null != status && !"".equals(status)) {
			reqStatus = status.split(",");
		}
		List<REQUIREMENT_SUBMISSION_STATUS> statusList = new ArrayList<>();
		if (reqStatus != null) {
			for (int i = 0; i < reqStatus.length; i++) {
				statusList.add(REQUIREMENT_SUBMISSION_STATUS.getNameByValue(reqStatus[i]));
			}
		}
		String from = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId";
		String subQuery = "";
		if (userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName)
				|| "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
			subQuery = " where requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
		}
		else {
			String subQuery2 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("userId", user.getId());
			params.put("level", 1);
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery3 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.parentRequirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ) and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("approverUserId", user.getId());
			params.put("userId", user.getId());
			params.put("level1", 1);
			params.put("level2", 2);
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery4 = "requirement.parentRequirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("managerUserId", user.getId());
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery5 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("userId", user.getId());
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery6 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("userId", user.getId());
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			if (subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != ""
					|| subQuery6 != "") {
				subQuery = "where  ((" + subQuery2 + ") OR (" + subQuery3 + ")  OR (" + subQuery4
						+ ") OR (" + subQuery5 + ") OR (" + subQuery6 + "))";
				params.put("orgRelationId", orgRelationId);
				params.put("locationId", locationId);
			}
		}
		Query query = null;
		if (null != statusList && !statusList.isEmpty()) {
			query = session.createQuery(from + " " + subQuery
					+ " and requirement.parentRequirement.requirementSubmissionStatus IN (:statusList) ORDER BY requirement.id DESC , requirement.parentRequirement.id");
			params.put("statusList", statusList);
		}
		else {
			query = session.createQuery(from + " " + subQuery + "  ORDER BY requirement.id DESC");
		}
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgRelationId") || str.equalsIgnoreCase("locationId")
					|| str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId")
					|| str.equalsIgnoreCase("managerUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1")
					|| str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			}
			if (!statusList.isEmpty()) {
				query.setParameterList("statusList", statusList);
			}
			else {
				query.setParameter(str, params.get(str));
			}
		}
		releasedJobsList = query.setCacheable(true).list();
		return releasedJobsList;
	}
		
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementVisaByRequirementI(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.visaType from Requirement req where req.id =:requirementId";
	    List<String> skillNames = session.createQuery(query).setLong( "requirementId", requirementId).setCacheable(true).list();
        return skillNames;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Set<RequirementAssignTo> getRequirementAssignedToTeamByRequirementId(Long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo reqAssignedTo left join fetch reqAssignedTo.requirementId  where reqAssignedTo.requirementId =:requirementId";
		List<RequirementAssignTo> reqAssignedTo = session.createQuery(query).setLong( "requirementId", id).setCacheable(true).list();
		Set<RequirementAssignTo> requirementAssignedTo = new HashSet<RequirementAssignTo>(reqAssignedTo);
		return requirementAssignedTo;
	}
}
