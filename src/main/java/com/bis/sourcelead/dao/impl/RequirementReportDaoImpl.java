package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementReportDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;

@Repository
public class RequirementReportDaoImpl implements RequirementReportDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate,Date startDateUTC, Date endDateUTC,String status) {
		 
		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Object> params = new HashMap<String,Object>();
		List<Requirement> mainList = null;
		String filterQuery = "";
		String dateSubQuery = "";
		String[] reqStatus = null;
		if(null != status && !"".equals(status)){	
			reqStatus = status.split(",");
		}
	    List<String> list = new ArrayList<String>();
	    if(reqStatus != null){
	    	for(int i=0; i < reqStatus.length; i++){
		    	if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.NEW.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.NEW.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.DRAFT.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.DRAFT.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.OPEN.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.OPEN.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.ONHOLD.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.ONHOLD.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.CANCEL.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.CANCEL.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.FILLEDCLOSED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.FILLEDCLOSED.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.UNFILLEDCLOSED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.UNFILLEDCLOSED.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.REJECTED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.REJECTED.toString());
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.CLOSED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.CLOSED.toString());
		    	}
		    }
	    }
		
	    if(null != startDateUTC && null != endDateUTC){
			 dateSubQuery = " AND REQ.CREATED_DATE >= :startDateUTC AND REQ.CREATED_DATE <= :endDateUTC";
			params.put("startDateUTC",startDateUTC);
			params.put("endDateUTC",endDateUTC);
		}
	    
	    if(!list.isEmpty() && null != list){
	    	filterQuery = " AND REQ.REQUIREMENT_SUBMISSION_STATUS IN (:statusList)";
	    	params.put("statusList",list);
	    }
		String from = "SELECT REQ.* FROM REQUIREMENT REQ LEFT JOIN ORG_RELATIONS ORGL ON ORGL.ID = REQ.ORG_RELATION_ID"; 
		String subQuery = "";
		
		if(userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)){
			subQuery = " WHERE REQ.ORG_LOCATION_ID = :orgLocationId AND (REQ.IS_Template IS NULL OR REQ.IS_Template <> 'Y')";
			params.put("orgLocationId",orgLocation.getId());
		}else{
			String subQuery1 = "REQ.CREATED_BY = :loginUserCode AND (REQ.IS_Template IS NULL OR REQ.IS_Template <> 'Y')";
			params.put("loginUserCode",user.getUserCode());
			
			String subQuery2 = "REQ.ID IN ( SELECT RAH.REQUIREMENT_ID FROM REQUIREMENT_APPROVER_HIERARCHY RAH WHERE RAH.APPROVER_USER_ID = :userId AND RAH.LEVEL = :level AND REQ.REQUIREMENT_SUBMISSION_STATUS <> 'DRAFT'  ) OR (REQ.ID IN ( SELECT RAT.REQUIREMENT_ID FROM REQUIREMENT_ASSIGN_TO RAT WHERE RAT.ASSIGN_USER_ID = :userId) AND ( REQ.REQUIREMENT_SUBMISSION_STATUS = 'APPROVED' OR REQ.REQUIREMENT_SUBMISSION_STATUS = 'OPEN' ))";
			params.put("userId",user.getId());
			params.put("level",1);
			
			String subQuery3 = "REQ.ID IN ( SELECT RAH2.REQUIREMENT_ID FROM REQUIREMENT_APPROVER_HIERARCHY RAH2 WHERE RAH2.APPROVER_USER_ID = :approverUserId AND RAH2.LEVEL = :level2  AND  REQ.REQUIREMENT_SUBMISSION_STATUS <> 'DRAFT' AND ( REQ.ID IN (SELECT RAH1.REQUIREMENT_ID FROM REQUIREMENT_APPROVER_HIERARCHY RAH1 WHERE RAH1.LEVEL = :level1 AND RAH1.STATUS = 'APPROVED'))  OR (REQ.ID IN ( SELECT RAT1.REQUIREMENT_ID FROM REQUIREMENT_ASSIGN_TO RAT1 WHERE RAT1.ASSIGN_USER_ID = :approverUserId) AND (REQ.REQUIREMENT_SUBMISSION_STATUS = 'APPROVED' OR REQ.REQUIREMENT_SUBMISSION_STATUS = 'OPEN' )))";
			params.put("approverUserId",user.getId());
			params.put("level1",1);
			params.put("level2",2);
			
			String subQuery4 = "REQ.MANAGER_USER_ID = :managerUserId AND REQ.REQUIREMENT_SUBMISSION_STATUS <> 'DRAFT'";
			params.put("managerUserId",user.getId());
			
			String subQuery5 = "REQ.ID IN ( SELECT RAT2.REQUIREMENT_ID FROM REQUIREMENT_ASSIGN_TO RAT2 WHERE RAT2.TEAM_ID IN ( SELECT TM.ID FROM TEAM TM WHERE TM.OWNER_USER_ID = :userId)) AND REQ.REQUIREMENT_SUBMISSION_STATUS <> 'DRAFT' AND REQ.REQUIREMENT_SUBMISSION_STATUS <> 'SUBMITTED'";
			params.put("userId",user.getId());
			
			String subQuery6 = "REQ.ID IN ( SELECT RAT3.REQUIREMENT_ID from REQUIREMENT_ASSIGN_TO RAT3 WHERE RAT3.ASSIGN_USER_ID = :userId) AND REQ.REQUIREMENT_SUBMISSION_STATUS <> 'DRAFT' and REQ.REQUIREMENT_SUBMISSION_STATUS <> 'SUBMITTED'";
			params.put("userId",user.getId());
			
			if(subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 != "" ){
				subQuery = "WHERE REQ.ORG_LOCATION_ID =:orgLocationId AND ( ("+subQuery1+") OR ("+subQuery2+") OR ("+subQuery3+") OR ("+subQuery4+") OR ("+subQuery5+") OR ("+subQuery6+") )";
				params.put("orgLocationId",orgLocation.getId());
			}
			
		}
		String whereCondition = subQuery +dateSubQuery+filterQuery;
		String listQuery = from+" "+whereCondition+" ORDER BY REQ.ID DESC";
		
		Query query = session.createSQLQuery(listQuery).addEntity(Requirement.class);
		for (String str : query.getNamedParameters())
		{
			if (str.equalsIgnoreCase("orgLocationId")) {
				query.setLong(str, orgLocation.getId());
			} else if (str.equalsIgnoreCase("userId")) {
				query.setLong(str, user.getId());
			} else if (str.equalsIgnoreCase("approverUserId")) {
				query.setLong(str, user.getId());
			} else if (str.equalsIgnoreCase("managerUserId")) {
				query.setLong(str, user.getId());
			} else if (str.equalsIgnoreCase("level")) {
				query.setInteger(str, 1);
			} else if (str.equalsIgnoreCase("level1")) {
				query.setInteger(str, 1);
			} else if (str.equalsIgnoreCase("level2")) {
				query.setInteger(str, 2);
			} else if (str.equalsIgnoreCase("loginUserCode")) {
				query.setLong(str, user.getId());
			}else if (str.equalsIgnoreCase("statusList")) {
				query.setParameterList(str, list);
			}else if (str.equalsIgnoreCase("startDateUTC")) {
				query.setDate(str,startDateUTC);
			}else if (str.equalsIgnoreCase("endDateUTC")) {
				query.setDate(str, endDateUTC);
			}

		}
		mainList = query.list();
		return mainList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementReports(Long requirementId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh "
				+ " left join fetch rsh.submissionId rs left join fetch rsh.submissionId.requirement requirement where requirement.id = :requirementId";
		return  session.createQuery(query).setLong("requirementId", requirementId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getApplicantsCountsForReports(Long orgLocationId,String applicantStatus) throws Exception {
		int totalCount = 0;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh  left join fetch rsh.submissionId rs where rs.orgLocations = :orgLocationsId and rsh.toStage = :applicantStatus";
	List<RequirementSubmissionHistory> rshList = session.createQuery(query).setLong("orgLocationsId",orgLocationId).setString("applicantStatus", applicantStatus).list();
	if(null != rshList){
		totalCount = rshList.size();
	}
	return totalCount;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where requirement.id = :requirementId  and rs.vendorLocationId <> null ";
		return  session.createQuery(query).setLong("requirementId", requirementId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId,Long requirementId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh left join fetch rsh.submissionId rs where rs.orgLocations.id = :orgLocationId and rs.requirement.id = :requirementId and  rs.vendorLocationId <> null ";
		return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh  left join fetch rsh.submissionId rs where rs.orgLocations = :orgLocationsId";
	List<RequirementSubmissionHistory> rshList = session.createQuery(query).setLong("orgLocationsId",orgLocationId).list();
	return rshList;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementStageReports(OrgLocations orgLocation, String status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String[] reqStatus = null;
		if(null != status && !"".equals(status)){	
			reqStatus = status.split(",");
		}
	    List<REQUIREMENT_SUBMISSION_STATUS> list = new ArrayList<>();
	    if(reqStatus != null){
	    	for(int i=0; i < reqStatus.length; i++){
		    	if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.NEW.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.NEW);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.DRAFT.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.DRAFT);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.OPEN.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.OPEN);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.ONHOLD.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.ONHOLD);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.CANCEL.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.CANCEL);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.FILLEDCLOSED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.FILLEDCLOSED);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.UNFILLEDCLOSED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.UNFILLEDCLOSED);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.REJECTED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
		    	}else if(reqStatus[i].equals(REQUIREMENT_SUBMISSION_STATUS.CLOSED.toString())){
		    		list.add(REQUIREMENT_SUBMISSION_STATUS.CLOSED);
		    	}
		    }
	    }
	    String query = "";
	    List<Requirement> reqList = null;
	    if(!list.isEmpty() && null != list){
	    	query = "from Requirement req where req.orgLocationId =:orgLocId and req.requirementSubmissionStatus IN (:statusList)";
	    	reqList = session.createQuery(query).setLong("orgLocId",orgLocation.getId()).setParameterList("statusList", list).list();
	    }else{
	    	query = "from Requirement req where req.orgLocationId =:orgLocId and (req.isTemplate IS NULL OR req.isTemplate <> 'Y') ORDER BY req.id DESC";
	    	reqList = session.createQuery(query).setLong("orgLocId",orgLocation.getId()).list();
	    }
		return reqList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getReceivedApplicantsCountForReportsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId,Date startDateUTC,Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();
		if(null != startDateUTC && null != endDateUTC ){
				String query = "from RequirementSubmissionHistory rsh left join fetch rsh.submissionId rs where rs.orgLocations.id = :orgLocationId and rs.vendorLocationId.id = :vendorLocationId and (rsh.createdDate >= :startDateUTC and rs.createdDate <=  :endDateUTC) ";
				return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("vendorLocationId", vendorLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		}
		else{
			String query = "from RequirementSubmissionHistory rsh left join fetch rsh.submissionId rs where rs.orgLocations.id = :orgLocationId and rs.vendorLocationId.id = :vendorLocationId and  rs.vendorLocationId <> null  ";
			return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("vendorLocationId", vendorLocationId).list();
		}
		
			}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getTotalApplicants(OrgLocations orgLocation, Date startDateUTC, Date endDateUTC)
			throws Exception {
		
		String subQuery = "";
		Map<String, Object> params = new HashMap<String,Object>();
		if(null != orgLocation){
			params.put("orgLocationId", orgLocation.getId());
		}
		if(null != startDateUTC && null != endDateUTC){
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and rs.createdDate >= :startDateUTC and rs.createdDate <= :endDateUTC";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmission rs left join fetch rs.candidate left join fetch rs.requirement requirement where rs.orgLocations.id = :orgLocationId "+subQuery;
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") ){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if(str.equalsIgnoreCase("startDateUTC") ){
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if(str.equalsIgnoreCase("endDateUTC") ){
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
        }
		return qry.list();
		/*return  session.createQuery(query).setLong("requirementId", requirementId).list();*/

	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getReceivedApplicantsCountsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId,Date startDateUTC, Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();
		if(null != startDateUTC && null != endDateUTC ){
			String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.orgLocations.id = :orgLocationId and rs.vendorLocationId.id = :vendorLocationId and  rs.vendorLocationId <> null and  (rs.createdDate >= :startDateUTC and rs.createdDate <= :endDateUTC) ";
			return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("vendorLocationId", vendorLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		}
		else{
			String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.orgLocations.id = :orgLocationId and rs.vendorLocationId.id = :vendorLocationId and  rs.vendorLocationId <> null  ";
			return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("vendorLocationId", vendorLocationId).list();
		
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long submissionId, Date startDateUTC,Date endDateUTC) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		Map<String, Object> params = new HashMap<String,Object>();
		if(null != submissionId){
			params.put("submissionId", submissionId);
		}
		if(null != startDateUTC && null != endDateUTC){
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and rsh.createdDate >= :startDateUTC and rsh.createdDate <= :endDateUTC";
		}
		String query = "from RequirementSubmissionHistory rsh left join fetch rsh.submissionId rs left join fetch rsh.submissionId.requirement requirement where rsh.submissionId.id = :submissionId "+subQuery;
		
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if(str.equalsIgnoreCase("submissionId") ){
				qry.setLong(str, (long) params.get("submissionId"));
			}
			if(str.equalsIgnoreCase("startDateUTC") ){
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if(str.equalsIgnoreCase("endDateUTC") ){
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
        }
		return qry.list();
		
		//return  session.createQuery(query).setLong("requirementId", requirementId).list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getReceivedApplicantsCountsPerOrgLocId(Long orgLocationId, Date startDateUTC,Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();
		if(null != startDateUTC && null != endDateUTC ){
			String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.orgLocations.id = :orgLocationId and  rs.vendorLocationId <> null  and  (rs.createdDate >= :startDateUTC and rs.createdDate <= :endDateUTC) ";
			return  session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();

		}
		else{
			String query = "from RequirementSubmission rs left join fetch rs.requirement requirement where rs.orgLocations.id = :orgLocationId and  rs.vendorLocationId <> null ";
			return  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmissionsByAssignTeamMember(Long orgLocationId,Set<String> userCodesList, Date startDateUTC, Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmission rs left join fetch rs.requirement left join fetch rs.candidateResumes left join fetch rs.orgLocations left join fetch rs.vendorLocationId where rs.createdBy IN (:userCodesList)";
		return session.createQuery(query).setParameterList("userCodesList", userCodesList).list();
	}

}
