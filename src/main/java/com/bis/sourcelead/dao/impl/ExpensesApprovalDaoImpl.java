package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.OrgLocationService;

@Repository
public class ExpensesApprovalDaoImpl implements ExpensesApprovalDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ExpensesDao expensesDao;
	
	@Autowired
	AddExpensesDao addExpensesDao;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId approver  left join fetch sah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user "
			+ " where exp.id = :expenseId ";
				
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("expenseId", expenseId).list();
		return tslist;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long userId,Long orgLocationId, String expenseStatus, Date startDateUTC, Date endDateUTC,String searchValue,int start,int length,String isGlobalUser) throws Exception {
		ResponseList<ExpensesApproverHierarchy> response = new ResponseList<>();
		Map<String, Object> params = new HashMap<String,Object>();
		String[] expStatus = expenseStatus.split(",");
		String subQuery2 = "";
		if (Arrays.asList(expStatus).contains("ALL") || StringUtils.isEmpty(expenseStatus)) {
			subQuery2 = subQuery2
					+ "  sah.expenseApprovalStatus = 'SUBMITTEDFORAPPROVAL' or sah.expenseApprovalStatus = 'APPROVED' or sah.expenseApprovalStatus = 'REJECTED' or sah.expenseApprovalStatus = 'REOPENED' ";
		}
		else{
			if (Arrays.asList(expStatus).contains("SUBMITTEDFORAPPROVAL")||Arrays.asList(expStatus).contains("submitted")||Arrays.asList(expStatus).contains("SUBMITTED")) {
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'SUBMITTEDFORAPPROVAL' ";
			}
			if (Arrays.asList(expStatus).contains("APPROVED")||Arrays.asList(expStatus).contains("approved")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'APPROVED' ";
			}
			if (Arrays.asList(expStatus).contains("REJECTED")||Arrays.asList(expStatus).contains("rejected")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'REJECTED' ";
			}
			if (Arrays.asList(expStatus).contains("REOPENED")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'REOPENED' ";
			}
		}
		
		String startDateSubQuery = "";
		if(null != startDateUTC){
			startDateSubQuery = "and (sah.expenseId.startDate >= :startDateUTC)";
			  params.put("startDateUTC",startDateUTC);
		}
		
		String endDateSubQuery = "";
		if(null != endDateUTC){
			endDateSubQuery = "and (sah.expenseId.endDate <= :endDateUTC)";
			  params.put("endDateUTC",endDateUTC);
		}
		
		StringBuilder query1 = new StringBuilder();
		if(searchValue !=null && !"".equalsIgnoreCase(searchValue)) {
			Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
	        Matcher matcher = pattern.matcher(searchValue);
		   query1.append(" and (sah.expenseId.purpose like '%" + searchValue + "%' ")
				.append("OR sah.expenseId.projectMembers.projects.projectName like '%" + searchValue + "%' ")
				.append("OR (sah.expenseId.projectMembers.projectMemberCandidteId.firstName like '" + searchValue + "%' OR sah.expenseId.projectMembers.projectMemberCandidteId.lastName like '%" + searchValue + "' )")
				.append("OR (concat(sah.expenseId.projectMembers.projectMemberCandidteId.firstName,' ',sah.expenseId.projectMembers.projectMemberCandidteId.lastName) like '%" + searchValue.trim().replaceAll(" +", " ")+ "%')" )
				.append("OR sah.expenseId.projectMembers.projectMemberCandidteId.email like '" + searchValue + "%'")
				.append("OR sah.expenseId.startDate like '%" + searchValue + "%' ")
				.append("OR sah.expenseId.expenseId like '%" + searchValue + "%' ")
				.append("OR sah.expenseId.billingStatus = '" + searchValue + "' ")
				.append("OR sah.expenseId.projectMembers.projectMemberCandidteId.designationType like '%" + searchValue + "%' ")
				.append("OR sah.expenseApprovalStatus like '%" + searchValue + "%' ");
		   

		   
				if(matcher.matches() == false) {
					query1.append("OR sah.expenseId.endDate like '%" + searchValue + "%' )");
				}
			    
				if(matcher.matches() == true) {
					java.sql.Date sqlDate = null;
					if(searchValue.contains("/")||(searchValue.contains("-"))) {
						String replacedValue = searchValue.replaceAll("/", "-");
						    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
							SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					        java.util.Date utilDate = format.parse(replacedValue);
					        sqlDate = new java.sql.Date(utilDate.getTime());
					}
					query1.append("OR sah.expenseId.endDate like '%" + searchValue + "%'")
					      .append("OR sah.expenseId.startDate like '" + sqlDate + "' ")
					      .append("OR sah.expenseId.endDate like '" + sqlDate + "')");
				}
		   
		   
		  }
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String globalUserQuery = "";
         if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
       	     globalUserQuery =   "(sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)) ";
         }else {
       	     globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :orgLocationId OR (:orgLocationId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.expenseId.projects.id and subpm.masterCandidateId.id = sah.expenseId.masterCandidateId.id) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.expenseId.projects.id and subpm.masterCandidateId.id = sah.expenseId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)))";
         }
		
		 String from = "from  ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch pc.orgRelationId orgRelation left join fetch hierarchy.approverUserId approver left join fetch sah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projects projects"
					+ " left join fetch pm.masterCandidateId mc left join fetch pm.employee emp left join fetch emp.user user where "
				 + globalUserQuery
				 //isApprover
	             + "  and   ("
		      	 + "  (      sah.hierarchyId.approverUserId.id = :userId  " 
				 + "            and  (sah.hierarchyId.projectId.expenseApprovalType  ='"+Constants.PARALLEL+"'"
				 +"                   OR (sah.hierarchyId.projectId.expenseApprovalType  ='"+Constants.SEQUENTIAL+"' and "
				 + "                          (sah.hierarchyId.level = '1' and sah.hierarchyId.projectClientsId.level IN(select Max(subsah.hierarchyId.projectClientsId.level) from ExpensesApproverHierarchy subsah where subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.expenseId.projectMembers.masterCandidateId.id = sah.expenseId.projectMembers.masterCandidateId.id and subsah.hierarchyId.projectClientsId.orgLocationsId.org.orgType <> 'GUEST'))"
				 + "                             OR  (sah.hierarchyId.level= '1' and (sah.hierarchyId.projectClientsId.level+1) IN ( select subsah.hierarchyId.projectClientsId.level from ExpensesApproverHierarchy subsah where subsah.expenseApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.expenseId.id = sah.expenseId.id))"
				 + "                             OR  (sah.hierarchyId.level= '2' and '1' IN (select subsah.hierarchyId.level from ExpensesApproverHierarchy subsah where subsah.expenseApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.expenseId.id = sah.expenseId.id)) "
				 + "                     )          "
				 + "             ))"
	           
			     /*
				 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
				 */

			    + "  OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = sah.hierarchyId.projectClientsId.orgLocationsId.id ) and  sah.hierarchyId.level IN(select MAX(pah.level) from ExpenseProjectApproverHierarchy pah where pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id and pah.status != '0' ) and (sah.hierarchyId.projectId.expenseApprovalType = '"+Constants.PARALLEL+"' OR (sah.hierarchyId.projectId.expenseApprovalType = '"+Constants.SEQUENTIAL+"' and ((1 IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id)) OR (1 NOT IN(select Max(pah.level) from ProjectApproverHierarchy pah where  pah.projectClientsId.id = sah.hierarchyId.projectClientsId.id) and '1' IN (select subsah.hierarchyId.level from ExpensesApproverHierarchy subsah where subsah.expenseApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.hierarchyId.projectClientsId.id = sah.hierarchyId.projectClientsId.id and subsah.expenseId.id = sah.expenseId.id)))))) "
			
			    +"   OR (:userId NOT IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = sah.hierarchyId.projectClientsId.orgLocationsId.id ) and (sah.hierarchyId.level = '1' and  sah.hierarchyId.status = '1' and sah.expenseId.projectMembers.projects.isReportingManagerAsApprover = 'Y'  and sah.expenseId.projectMembers.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = sah.expenseId.projectMembers.projectMemberCandidteId.consultantUser.id)) and (sah.hierarchyId.projectId.expenseApprovalType= '"+Constants.PARALLEL+"'  OR (sah.hierarchyId.projectId.expenseApprovalType = '"+Constants.SEQUENTIAL+"' and (sah.hierarchyId.projectClientsId.level IN(select Max(subsah.hierarchyId.projectClientsId.level) from ExpensesApproverHierarchy subsah where subsah.hierarchyId.projectId.id = sah.hierarchyId.projectId.id and subsah.expenseId.projectMembers.masterCandidateId.id = sah.expenseId.projectMembers.masterCandidateId.id and subsah.hierarchyId.projectClientsId.orgLocationsId.org.orgType <> 'GUEST') OR ((sah.hierarchyId.projectClientsId.level+1) IN ( select subsah.hierarchyId.projectClientsId.level from ExpensesApproverHierarchy subsah where subsah.expenseApprovalStatus = '"+Submission_ApprovalStatus.APPROVED+"' and subsah.expenseId.id = sah.expenseId.id ))))))"
				+ "   ) and ( " + subQuery2 +" )"+startDateSubQuery+" "+endDateSubQuery+" "+query1.toString() + "GROUP BY sah.expenseId.id order By sah.id desc" ;

		
					 
		 
		params.put("userId",userId);
		params.put("orgLocationId",orgLocationId);
		
		/*List<Long> filteredCountlist  = session.*/
		
		Query query = session.createQuery(from);
		
		
		/*if(length>0) {
			countQuery =session.createQuery(countFrom);
		}*/
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("userId")){
				query.setParameter(str, (long) params.get(str));
				/*if(countQuery != null)
				countQuery.setParameter(str, (long) params.get(str));*/
			}if(str.equalsIgnoreCase("startDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
				/*if(countQuery != null)
				countQuery.setParameter( str, (Date) params.get(str) );*/
			}
			if(str.equalsIgnoreCase("endDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
				/*if(countQuery != null)
				countQuery.setParameter( str, (Date) params.get(str) );*/
			}
			if(str.equalsIgnoreCase("orgLocationId")){
				query.setParameter(str, (long) params.get(str));
				/*if(countQuery != null)
				countQuery.setParameter(str, (long) params.get(str));*/
			}
			
        }
		Query countQuery =query;
		List<ExpensesApproverHierarchy> tsCountlist = countQuery.list();
		List<ExpensesApproverHierarchy> tslist = query.setFirstResult(start).setMaxResults(length).list();
		response.setData(tslist); 
		
		/*List<Long> countList  = null;
		if(tslist != null) {
			countList  = countQuery.list();
		}*/
		int recordsTotal=0;
		if(tsCountlist != null && !tsCountlist.isEmpty()) {
			recordsTotal = tsCountlist.size();
		}
		response.setRecordsTotal(recordsTotal);
		
		int recordsFiltered = 0;
		if(tslist != null && !tslist.isEmpty()) {
			recordsFiltered = tslist.size();
		}
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(recordsFiltered);
		}
		else {
			response.setRecordsFiltered(recordsTotal);
		}
		
	    return response;
		/*@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		return tslist;*/
	}
	
	@Override	
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy getApprovedExpenseByExpenseIdAndLocationId(Long expenseId,Long locationsId){
		Session session = this.sessionFactory.getCurrentSession();
		// Getting Max Hierarchy Level for which employee is Assigned
		String subQueryForMaxLevel = " select MAX(eah.hierarchyId.level) from ExpensesApproverHierarchy eah where eah.expenseId.id = :expenseId and eah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId";
		String query = "from ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pclient  "
				+ " where pclient.orgLocationsId.id = :locationsId and eah.expenseId.id = :expenseId "
				+ " and ( hierarchy.level IN(" + subQueryForMaxLevel
				+ ")) and eah.expenseApprovalStatus = :approvalStatus ";
		
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> eahList = session.createQuery(query.toString())
				.setLong("expenseId", expenseId).setLong("locationsId", locationsId).setParameter("approvalStatus", Submission_Status.APPROVED).list();
		ExpensesApproverHierarchy eah = null;
		if(eahList != null && !eahList.isEmpty()) {
			eah = eahList.iterator().next();
		}
		return eah;
	}
	
	
	@Override	
	@Transactional(rollbackFor = { Exception.class })
	public User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId,String reason,User loginUser, String type, String isSuperUser) {
		Session session = this.sessionFactory.getCurrentSession();
		Query expenseApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense  where expense.id = :expenseId");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy>  expenseApproverHierarchyList=expenseApproverHierarchyListQuery.setLong("expenseId", expenseId).list();
	    if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
	    	for(ExpensesApproverHierarchy eah :expenseApproverHierarchyList){
	    		if(null != type && "notify".equalsIgnoreCase(type) && null != isSuperUser && isSuperUser.equalsIgnoreCase(Constants.YES)){
	    			eah.setReopenReason(reason);
	    			eah.setReopenedUser(loginUser);
	    			
	    			Expenses expenses =eah.getExpenseId();
	    			expenses.setExpenseStatus(Submission_Status.REOPENED);
	    			session.saveOrUpdate(expenses);
	    			
	    			eah.setExpenseStatus(Submission_Status.REOPENED);
	    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.REOPENED);
	    			eah.setReopenRejectedReason(null);
	    		}else{
	    			if(null != type && "notify".equalsIgnoreCase(type)){
	    				eah.setReopenReason(reason);
	    				eah.setReopenedUser(loginUser);
	    				eah.setReopenRejectedReason(null);
		    		}else if(null != type && "reject".equalsIgnoreCase(type)){
		    			eah.setReopenRejectedReason(reason);
		    			eah.setReopenReason(null);
		    			eah.setExpenseStatus(Submission_Status.APPROVED);
		    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
		    		}
		    		else{
		    			Expenses expenses =eah.getExpenseId();
		    			expenses.setExpenseStatus(Submission_Status.REOPENED);
		    			session.saveOrUpdate(expenses);
		    			
		    			eah.setExpenseStatus(Submission_Status.REOPENED);
		    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.REOPENED);
		    			eah.setReopenRejectedReason(null);
		    			//eah.setReopenReason(null);
		    		}
	    		}
	    		try {
	    			 session.saveOrUpdate(eah);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	return expenseApproverHierarchyList.get(0).getReopenedUser();
	    }else{
	    	return null;
	    }
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason,OrgLocations location,String isGlobalUser) throws Exception {
		Integer maxLevel = 0;
		Session session = this.sessionFactory.getCurrentSession();
		User loginUser = userDao.getUserById(approverUserId);
		String actionPerformedBy= loginUser.getFullName();
		Long actionPerformedByLocationId = null;
		if(location != null && StringUtils.isNotEmpty(location.getOrgLocationName())){
			//actionPerformedBy =location.getOrgLocationName();
			actionPerformedByLocationId = location.getId();
		}
		
		 String globalUserQuery = "";
         if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
       	     globalUserQuery =   "(sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)) ";
         }else {
       	     globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationId OR (:locationId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.expenseId.projects.id and subpm.masterCandidateId.id = sah.expenseId.masterCandidateId.id) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.expenseId.projects.id and subpm.masterCandidateId.id = sah.expenseId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)))";
         }
		/*
		 *  get ExpensesApproverHierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " left join fetch pm.projectClientsId pc where expense.id =:expenseId "
				        + " and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    hierarchy.approverUserId.id = :approverUserId  "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where  subsah.expenseApprovalStatus != 'APPROVED' and subsah.hierarchyId.projectClientsId.orgLocationsId.id =:locationId and subsah.expenseId.id = :expenseId ) and  "+Constants.loginUserAsAdminWithMaxHeirarchyLevelForExp+")  "

					          
					          + "  OR  ("
							 + "        :approverUserId NOT IN("
							 + "                select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseApprovalStatus != 'APPROVED' and subsah.expenseId.id = :expenseId  "
							 + "           ) and pm.projectMemberCandidteId.consultantUser IS NOT NULL "
							 + "       and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id)  "
							 + "      and sah.hierarchyId.level IN(select MIN(pah.level) from ExpenseProjectApproverHierarchy pah where ((pah.projectId.expenseApprovalType  ='"+Constants.PARALLEL+"'  and pah.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)) OR (pah.projectId.expenseApprovalType  ='"+Constants.SEQUENTIAL+"' and "+globalUserQuery+"))  and pah.status != '0')  "
							 + "            and ((sah.hierarchyId.projectId.expenseApprovalType  ='"+Constants.PARALLEL+"'  and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId)) OR (sah.hierarchyId.projectId.expenseApprovalType  ='"+Constants.SEQUENTIAL+"' and "+globalUserQuery+")))"
							  + ") order by pc.level ASC";

		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("expenseId", expenseId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("userId",approverUserId)
																	  .setLong("locationId",location.getId())
				                                                      .list();
		
		
		
		if(sahMainList != null && !sahMainList.isEmpty()){
	    	for(ExpensesApproverHierarchy sah :sahMainList){
	    		/*
	    		 * get Max Level
	    	    */
	    		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from ExpensesApproverHierarchy sah  where sah.hierarchyId.projectClientsId.id = :projectClientsId and  sah.expenseId.id = :expenseId   ");
	    		List<Integer> maxList = queryForMaxLevel.setLong("projectClientsId", sah.getHierarchyId().getProjectClientsId().getId()).setLong("expenseId", expenseId).list();
	    		
	    		if(maxList !=null && !maxList.isEmpty()){
	    			maxLevel = maxList.iterator().next();
	    		}
	    		
	    		if(status!=null && status.equalsIgnoreCase("REJECTED")){
	    			//expenseStatus.add(Submission_Status.REJECTED.getValue());
            	 	sah.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
            	 	sah.setExpenseStatus(Submission_Status.REJECTED);
            	 	sah.setActionPerformedBy(""+actionPerformedBy);
            	 	sah.setActionbyorglocid(actionPerformedByLocationId);
            	 	sah.setRejectedReason(rejectedReason);
            	 	sah.setReopenReason(null);
            	    sah.setReopenedUser(null);
            	    sah.setReopenRejectedReason(null);
            	 	
	             }else if(status!=null && status.equalsIgnoreCase("APPROVED")){
	            	// expenseStatus.add(Submission_Status.APPROVED.getValue());
	            	 sah.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 sah.setExpenseStatus(Submission_Status.APPROVED);
	            	 sah.setReopenReason(null);
	            	 sah.setReopenedUser(null);
	            	 sah.setReopenRejectedReason(null);
	            	 sah.setActionPerformedBy(""+actionPerformedBy);
	            	 sah.setActionbyorglocid(actionPerformedByLocationId);
	             }
	    		 try {
	    			 updateSubmissionApproverHierarchy(sah);
				} catch (Exception e) {
				}
	    		
	    		String queryForAllApprovers = " from ExpensesApproverHierarchy sah left join fetch "
	    				+ " sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and  sah.expenseId = :expenseId";
	    		@SuppressWarnings("unchecked")
        		List<ExpensesApproverHierarchy> approversList = session.createQuery(queryForAllApprovers)
        																	  .setLong("expenseId", sah.getExpenseId().getId())
        																	  .setLong("id", sah.getId())
        				                                                      .list();
	    		
                	if(approversList != null && !approversList.isEmpty()){
                		for(ExpensesApproverHierarchy approver  :  approversList){
                			 if(status!=null && status.equalsIgnoreCase("REJECTED")){
                			    //expenseStatus.add(Submission_Status.APPROVED.getValue());
                			    Expenses expenses =approver.getExpenseId();
            	    			expenses.setExpenseStatus(Submission_Status.REJECTED);
            	    			session.saveOrUpdate(expenses);
                				approver.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
                				approver.setExpenseStatus(Submission_Status.REJECTED);
                				approver.setActionPerformedBy(" "+actionPerformedBy);
                				approver.setActionbyorglocid(actionPerformedByLocationId);
                				approver.setRejectedReason(rejectedReason);
                				approver.setReopenReason("");
                				approver.setReopenedUser(null);
                				approver.setReopenRejectedReason("");
                        	  	
            	              }
                			 else if(status!=null && status.equalsIgnoreCase("APPROVED")){
                				 if(approver.getHierarchyId().getProjectClientsId() != null && 
                					approver.getHierarchyId().getProjectClientsId().getOrgLocationsId() != null && 
                				    approver.getHierarchyId().getProjectClientsId().getOrgLocationsId().getOrg() != null && 
                				     approver.getHierarchyId().getProjectClientsId().getOrgLocationsId().getOrg().getOrgType().equals(Constants.GUEST)) {
                					    // expenseStatus.add(Submission_Status.APPROVED.getValue());
                					     Expenses expenses =approver.getExpenseId();
                     	    			 expenses.setExpenseStatus(Submission_Status.APPROVED);
                     	    			 session.saveOrUpdate(expenses);
    	            				 	 approver.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
    	            	            	 approver.setExpenseStatus(Submission_Status.APPROVED);
    	            	            	 approver.setActionPerformedBy(" "+actionPerformedBy);
    	            	            	 approver.setActionbyorglocid(actionPerformedByLocationId);
    	            	            	 approver.setReopenReason("");
    	                 				 approver.setReopenedUser(null);
    	                 				 approver.setReopenRejectedReason("");
                				 }
                				 else if((approver.getHierarchyId().getProjectClientsId().getLevel() >  sah.getHierarchyId().getProjectClientsId().getLevel())
                					     || (approver.getHierarchyId().getProjectClientsId().getLevel() ==  sah.getHierarchyId().getProjectClientsId().getLevel() && sah.getHierarchyId().getLevel()==(maxLevel))){
                					     // expenseStatus.add(Submission_Status.APPROVED.getValue());
                					      Expenses expenses =approver.getExpenseId();
                      	    			  expenses.setExpenseStatus(Submission_Status.APPROVED);
                      	    			  session.saveOrUpdate(expenses);
                					      approver.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
    	            	            	 approver.setExpenseStatus(Submission_Status.APPROVED);
    	            	            	 approver.setActionPerformedBy(" "+actionPerformedBy);
    	            	            	 approver.setActionbyorglocid(actionPerformedByLocationId);
    	            	            	 approver.setReopenReason("");
    	                 				 approver.setReopenedUser(null);
    	                 				 approver.setReopenRejectedReason("");
                    		     }
                				/* else{
                					 expenseStatus.add(Submission_Status.SUBMITTED.getValue());
                				 }*/
                			 }
                	        try {
                	        	updateSubmissionApproverHierarchy(approver);
             				} catch (Exception e) {
             				}
                	   }
                	}
	    	}
	    }
		Expenses expenses = expensesDao.getExpenseById(expenseId);
		List<String> expenseStatus = new ArrayList<>();
		expenseStatus =   expenses.getApproverHierarchyList().stream().map(hierarchy -> hierarchy.getExpenseApprovalStatus().getValue()).collect(Collectors.toList());
		if(expenseStatus.contains(Submission_ApprovalStatus.REJECTED.getValue())){
			expenses.setExpenseStatus(Submission_Status.REJECTED);
		}else if(expenseStatus.contains(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL.getValue())){
			expenses.setExpenseStatus(Submission_Status.SUBMITTED);
		}else if(expenseStatus.contains(Submission_ApprovalStatus.REOPENED.getValue())){
			expenses.setExpenseStatus(Submission_Status.REOPENED);
		}else{
			expenses.setExpenseStatus(Submission_Status.APPROVED);
		}
		addExpensesDao.storeAddExpensesData(expenses);
		return maxLevel;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateExpenseStatusByExpenseId(Long expenseId,String status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query expenseApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expenses where expenses.id = :expenseId");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy>  expensesApproverHierarchyList=expenseApproverHierarchyListQuery.setLong("expenseId", expenseId).list();
	    if(expensesApproverHierarchyList != null && !expensesApproverHierarchyList.isEmpty()){
	    	for(ExpensesApproverHierarchy eah :expensesApproverHierarchyList){
	    		if(status.equalsIgnoreCase("Draft")){
	    		    Expenses expenses =eah.getExpenseId();
 	    			expenses.setExpenseStatus(Submission_Status.DRAFT);
 	    			session.saveOrUpdate(expenses);
	    			eah.setExpenseStatus(Submission_Status.DRAFT);
	    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.NEW);
	    		}
	    		else if(status.equalsIgnoreCase("Submit")){
	    			 Expenses expenses =eah.getExpenseId();
 	    			expenses.setExpenseStatus(Submission_Status.SUBMITTED);
 	    			session.saveOrUpdate(expenses);
	    			eah.setExpenseStatus(Submission_Status.SUBMITTED);
	    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
	    		}
	    		try {
	    			 session.saveOrUpdate(eah);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    }
	
	
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy updateSubmissionApproverHierarchy(
			ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		if(expensesApproverHierarchy != null){
			 session.saveOrUpdate(expensesApproverHierarchy);
		}
		if(expensesApproverHierarchy == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
	   
	    return expensesApproverHierarchy;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		
		Query expensesApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense where expense.id = :expenseId  and eah.expenseApprovalStatus = :approvalStatus");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).setString("approvalStatus", "APPROVED").list();
		ExpensesApproverHierarchy eah = null;
		if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
			eah = expenseApproverHierarchyList.iterator().next();
		}
		return eah;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isTheExpenseTotallyApproved(Long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query expensesApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense where expense.id = :expenseId  and eah.expenseApprovalStatus != :approvalStatus");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).setString("approvalStatus", "APPROVED").list();
		Boolean flag = true;
		if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
			flag = false;
		}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isReopenButtonVisibleForLoginOrg(Long expenseId,Long locationsId,String isGlobalUser){
		Session session = this.sessionFactory.getCurrentSession();
		 String globalUserQuery = "";
         if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
       	     globalUserQuery =   "(eah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)) ";
         }else {
       	     globalUserQuery = " (eah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = eah.expenseId.projects.id and subpm.masterCandidateId.id = eah.expenseId.masterCandidateId.id) and eah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = eah.expenseId.projects.id and subpm.masterCandidateId.id = eah.expenseId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and eah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
         }
		
		Query expensesApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy "
				+ " left join fetch hierarchy.projectClientsId pc "
				+ " left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense where expense.id = :expenseId  and expense.expenseStatus = :approvalStatus"
				+ "  and "+globalUserQuery+" and (pc.level IN(select max(subEah.hierarchyId.projectClientsId.level) from ExpensesApproverHierarchy subEah where subEah.expenseId.id = expense.id) OR pc.level IN(select min(subEah.hierarchyId.projectClientsId.level) from ExpensesApproverHierarchy subEah where subEah.expenseId.id = expense.id)) ");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).setLong("locationsId", locationsId).setString("approvalStatus", "APPROVED").list();
		Boolean flag = false;
		if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
			flag = true;
		}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query expensesApproverHierarchyListQuery= session.createQuery("from ExpensesApproverHierarchy eah left join fetch eah.hierarchyId left join fetch eah.expenseId expense  left join fetch expense.projectMembers pm where expense.id = :expenseId");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).list();
		//return expensesApproverHierarchyList;
		
		Boolean isRejected = false; 
	    Boolean isREOPENED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isDRAFT = false;
	    ExpensesApproverHierarchy expensesApproverHierarchy = null;
	    if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
	    	for(ExpensesApproverHierarchy eah : expenseApproverHierarchyList){
				
				if(eah.getExpenseStatus() == Submission_Status.REJECTED){
					isRejected   = true;  
					expensesApproverHierarchy = eah;
				}
				else if(!isRejected  && eah.getExpenseStatus() == Submission_Status.NEW){
					 isNEW   = true;  
					 expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW &&  eah.getExpenseStatus() == Submission_Status.REOPENED){
					 isREOPENED   = true;  
					 expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW && !isREOPENED &&  eah.getExpenseStatus() == Submission_Status.DRAFT){
					 isDRAFT   = true;  
					 expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW &&  !isREOPENED && !isDRAFT && eah.getExpenseStatus() == Submission_Status.SUBMITTED){
					isSUBMITTED   = true;  
					expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW &&  !isREOPENED && !isDRAFT && !isSUBMITTED && eah.getExpenseStatus() == Submission_Status.APPROVED ){
					 expensesApproverHierarchy = eah;
				}
	      }
	    }
	    else{
	    	 expensesApproverHierarchy  = new ExpensesApproverHierarchy();
	    	 expensesApproverHierarchy.setExpenseStatus(Submission_Status.getNameByValue("new"));
	    	 expensesApproverHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
	    }
		
		// TODO Auto-generated method stub
		return expensesApproverHierarchy;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId,Long locationsId,String isGlobalUser) throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		 String globalUserQuery = "";
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
       	     globalUserQuery =   "(sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)) ";
         }else {
       	     globalUserQuery = " (sah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId OR (:locationsId NOT IN (select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.expenseId.projects.id and subpm.masterCandidateId.id = sah.expenseId.masterCandidateId.id) and sah.hierarchyId.projectClientsId.orgLocationsId.id IN(select subpm.projectMemberCandidteId.orgLocationId.id from ProjectMembers subpm where subpm.projects.id = sah.expenseId.projects.id and subpm.masterCandidateId.id = sah.expenseId.masterCandidateId.id and subpm.projectMemberCandidteId.designationType = '"+Constants.OFFSHORE_C2C+"') and sah.hierarchyId.projectClientsId.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)))";
         }
		 
		/*
		 *  get ExpensesApproverHierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " left join fetch hierarchy.projectClientsId pc where expense.id =:expenseId and " +globalUserQuery
				        + " and  ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    (hierarchy.approverUserId.id = :approverUserId)  "
					        
					        +" OR  (  :userId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseId.id = :expenseId and  subsah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId) and  sah.hierarchyId.level = '1' and  sah.hierarchyId.status = '1'  and pm.projects.isReportingManagerAsApprover = 'Y'  and sah.hierarchyId.projectClientsId.orgLocationsId.id = pm.projectMemberCandidteId.orgLocationId.id and pm.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id))"
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseId.id = :expenseId and  subsah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId) and  (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id =  :locationsId and userRole.userState = 'ACTIVE') and  sah.hierarchyId.level IN(select MAX(pah.level) from ExpenseProjectApproverHierarchy pah where pah.projectClientsId.id = pc.id and pah.status != '0' )))"
				            + ") ";
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("expenseId", expenseId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("locationsId",locationsId)
																	  .setLong("userId",approverUserId)
				                                                      .list();
		
		
		ExpensesApproverHierarchy eah = null;
		if(sahMainList != null && !sahMainList.isEmpty()){
			eah = sahMainList.iterator().next();
		}
		return eah;
	}

	@Override
	@Transactional
	public ExpensesApproverHierarchy saveExpensesApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception{
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (expensesApproverHierarchy != null) {
				session.saveOrUpdate(expensesApproverHierarchy);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ExpensesApproverHierarchy"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return expensesApproverHierarchy;
	}


	@SuppressWarnings("unchecked")
	@Override	
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> makeReopenReasonStatusNull(Long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ExpensesApproverHierarchy> eah=null;
		String query = "from ExpensesApproverHierarchy eah  where eah.expenseId =:expenseId"; 
		 eah = session.createQuery(query).setLong("expenseId", expenseId).list();
			if(eah != null && !eah.isEmpty())
			{
				for(ExpensesApproverHierarchy e :eah) {
					e.setReopenReason(null);
					session.saveOrUpdate(e);
				}
				
			}
			return eah;
		
		
	}


}