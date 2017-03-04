package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementReportDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;

@Repository
public class RequirementReportDaoImpl implements RequirementReportDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate,Date startDateUTC, Date endDateUTC) {
		 
		Session session = this.sessionFactory.getCurrentSession();
		//User user = userDao.getUserById(userId);
		
//		UserRole userRole1 = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
				
		Map<String, Object> params = new HashMap<String,Object>();
		String dateSubQuery = " and requirement.createdDate between :startDateUTC AND :endDateUTC";
		
		String from = "from Requirement requirement  left join fetch requirement.parentRequirement  left join fetch requirement.clientId left join fetch requirement.spocId left join fetch requirement.submissionTemplateId left join fetch requirement.clientSubmissionTemplateId left join fetch requirement.parentRequirement"; 
		String subQuery = "";
		
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			subQuery = " where requirement.orgLocationId.id =:orgLocationId and (requirement.isTemplate IS NULL OR requirement.isTemplate <> 'Y') ";
			params.put("orgLocationId",orgLocation.getId());
		}else{
			String subQuery1 = "requirement.createdBy =:loginUserCode and (requirement.isTemplate IS NULL OR requirement.isTemplate <> 'Y') ";
			params.put("loginUserCode",user.getUserCode());
			
			String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ";
			params.put("userId",user.getId());
			params.put("level",1);
			
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  )";
			params.put("approverUserId",user.getId());
			params.put("level1",1);
			params.put("level2",2);
			
			String subQuery4 = "requirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT'";
			params.put("managerUserId",user.getId());
			
			String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",user.getId());
			
			String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId",user.getId());
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 != "" ){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery3+")  OR ("+subQuery4+") OR ("+subQuery5+") OR ("+subQuery6+") )";
				params.put("orgLocationId",orgLocation.getId());
			}
		}
		
		
		String whereCondition = subQuery +dateSubQuery;
		
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")){
				query.setLong(str, (long) params.get( str ));
			}if( str.equalsIgnoreCase("startDateUTC")){
				query.setTimestamp("startDateUTC", startDateUTC);
			}if(str.equalsIgnoreCase("endDateUTC")){
				query.setTimestamp("endDateUTC", endDateUTC);
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

}
