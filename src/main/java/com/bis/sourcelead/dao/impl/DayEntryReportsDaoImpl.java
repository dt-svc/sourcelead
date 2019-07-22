package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.DayEntryReportsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.util.CalendarUtil;

@Repository
public class DayEntryReportsDaoImpl implements DayEntryReportsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	UserRoleDao userRoleDao;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<DayWiseTimesheetReoprtsListDTO> getDayWiseReportListBasedOnProjectListIdStartDateEndDate(List<Long> projectId,Date startDate, Date endDate,String searchValue,int start,int length,OrgLocations orgloc , User loginUser,String isGlobalAccess,List<Long> mastercandidateIds,String dateFormat) throws Exception {
		
		String orgDateFormat = "";
		if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%Y-%m-%d\"";
		}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%d-%m-%Y\"";
		}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%m-%d-%Y\"";
		}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
			orgDateFormat = "\"%Y-%d-%m\"";
		}

		
		List<BigInteger> countlist = null;  
		
		Integer filterCount = 0;
		ResponseList<DayWiseTimesheetReoprtsListDTO> response = new ResponseList<DayWiseTimesheetReoprtsListDTO>();
		Session session = sessionFactory.getCurrentSession();
		UserRole userRole = userRoleDao.getUserRoleByUserIdAndLocationId(loginUser.getId(), orgloc.getId());
		
		String userRoleQuery = "";
		if(userRole.getRoles() != null && !userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
			userRoleQuery = " AND UR.REPORTING_MANAGER_ID = '"+loginUser.getId()+"'";
		}
			
		
		List<Long> userHierarchyIds =  userDao.getUserHierarchyIdList(loginUser.getId());
		String userHierarchyIdString = "0";
		if(userHierarchyIds != null && !userHierarchyIds.isEmpty()) {
			userHierarchyIdString = StringUtils.join(userHierarchyIds, ',');
		}
		if((projectId!= null && !projectId.isEmpty()) || (mastercandidateIds != null && !mastercandidateIds.isEmpty())) {
		java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
		java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());
		String weekOffs ="(";
		String[] strArray = null;
		if(StringUtils.isNotEmpty(orgloc.getWeekOffDays())) {
			strArray = orgloc.getWeekOffDays().split(",");
		}
		 if(strArray != null && strArray.length > 0) {
			 int count = 0;
			 for(String day :strArray) {
				 if(count >0) {
					 weekOffs = weekOffs+","+CalendarUtil.getDayOffsetByWeekName(day.toUpperCase());
				 }else {
					 weekOffs = weekOffs+CalendarUtil.getDayOffsetByWeekName(day.toUpperCase());
				 }
				 count++;
			 }
		 }else {
			 weekOffs =weekOffs+"0"; 
		 }
		 weekOffs =weekOffs+")";
		//StringBuilder searchQuery = new StringBuilder("");
	/*	if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
		 searchQuery.append(" timeEntryId like '%" + searchValue + "%' ")
					.append("OR ticketNo like '%" + searchValue + "%' ")
					.append("OR employeeName like '%" + searchValue + "%' ")
					.append("OR employeeEmail like '%" + searchValue + "%' ")
					.append("OR projectName like '%" + searchValue + "%' ")
					.append("OR task like '%" + searchValue + "%' ")
					.append("OR status like '%" + searchValue + "%' ")
					.append("OR departmentName like '%" + searchValue + "%' ");
		}*/
		
		
		String subQuery = "";
		if(projectId != null && !projectId.isEmpty()) {
			for(Long pId : projectId) {
				subQuery = subQuery+"PROJ.ID = '"+pId+"' OR ";
			}
			subQuery = "("+subQuery.substring(0,subQuery.length()-3)+")";
		}
		
		
		String pmSubQuery = "";
		if(mastercandidateIds != null && !mastercandidateIds.isEmpty()) {
			for(Long pId : mastercandidateIds) {
				pmSubQuery = pmSubQuery+" (PM.MASTER_CANDIDATE_ID = '"+pId+"' AND PM.PROJECT_ID = PROJ.ID) OR ";
			}
			pmSubQuery = "("+pmSubQuery.substring(0,pmSubQuery.length()-3)+")";
		}
		
		if(StringUtils.isNotEmpty(subQuery) && StringUtils.isNotEmpty(pmSubQuery)) {
			subQuery = subQuery+" AND "+pmSubQuery;
		}else if(StringUtils.isEmpty(subQuery) && StringUtils.isNotEmpty(pmSubQuery)) {
			subQuery = pmSubQuery;
		}
		String query = "SELECT Q1.*  "
				+"FROM ( "
				+ "(SELECT DATE_FORMAT(TDE.TIMESHEET_DATE,"+orgDateFormat+") as timesheetDate,"
				+ " TDE.ID as timeEntryId,"
				+ " TDE.BILLABLE_HOURS as billableHrs,"
				+ " TDE.TICKET_NUMBER as ticketNo,"
				+ " TDE.NONBILLABLE_HOURS as nonBillableHrs,"
				+ " EMP.EMPLOYEE_ID as employeeId,"
				+ " CONCAT(IF(CAN.FIRST_NAME IS NOT NULL, CONCAT(CAN.FIRST_NAME,' '),'') ,IF(CAN.LAST_NAME IS NOT NULL, CONCAT(CAN.LAST_NAME),'')) as employeeName,"
				+ " CAN.EMAIL as employeeEmail,"
				+ " PROJ.PROJECT_NAME as projectName,"
				+ " TDE.COMMENTS as comments,"
				+ " PT.TASK_NAME as task,"
				+ " SAH.SUBMISSION_APPROVALSTATUS as status,"
				+ " DEPART.DEPARTMENT_NAME AS departmentName,"
				+ " PROJ.ID AS projectId,"
				+ " PM.MASTER_CANDIDATE_ID AS masterCandidateId,"
				+ " SAH.CREATED_DATE  AS submittedDate,"
				+ " SAH.UPDATED_DATE AS approvedDate,"
				+ " SAH.FREQUENCY_START_DATE AS sahFrequenchStartDate,"
				+ " SAH.FREQUENCY_END_DATE AS sahFrequenchEndDate,"
				+ "   (CASE " 
				+ "        WHEN CAN.WORK_LOCATION IS NOT NULL THEN ( "
				+ "        CONCAT( "
				+ "        IF(CANLOC.COUNTRY IS NOT NULL, CONCAT(CANLOC.COUNTRY,','),'') ,"
				+ "        IF(CANLOC.STATE IS NOT NULL, CONCAT(CANLOC.STATE,','),''),"
				+ "        IF(CANLOC.CITY IS NOT NULL, CONCAT(CANLOC.CITY),'')))"
				+ "        ELSE  '' "
				+ "      END "
				+ "     ) AS  candidateLocation "
				+ " FROM SUBMISSION_APPROVER_HIERARCHY SAH,TIMESHEET_DAY_ENTRIES TDE "
				+ " INNER JOIN PROJECT_TASKS PT ON PT.ID = TDE.PROJECT_TASK_ID "
				+ " INNER JOIN PROJECTS PROJ ON PROJ.ID =  PT.PROJECT_ID "
                + " INNER JOIN TIMESHEET_SUBMISSIONS TS ON TS.ID =  TDE.SUBMISSION_ID "
				+ " LEFT JOIN PROJECT_MEMBERS PM ON PM.ID =  TS.PROJECT_MEMBER_ID "
                
				//To get candidate work location
				+ "   LEFT JOIN CANDIDATE CAN ON CAN.ID =  PM.CANDIDATE_ID "
			    + "   LEFT JOIN ORG_LOCATIONS CANLOC ON CANLOC.ID =  CAN.WORK_LOCATION "
			    + "   LEFT JOIN ORG CANORG ON CANORG.ID = CANLOC.ORG_ID "
			    + "   LEFT JOIN CANDIDATE ACANLOC ON ACANLOC.MASTER_CANDIDATE_ID = PM.MASTER_CANDIDATE_ID  AND ( ACANLOC.ORG_LOCATION_ID = '"+orgloc.getId()+"' OR CANORG.ID =  '"+orgloc.getOrg().getId()+"' )"
			    			       
			    //To get Employee and department details
				+ "   LEFT JOIN USER U ON U.ID = CAN.CONSULTANT_USER_ID "
			    + "   LEFT JOIN USER_ROLE UR ON UR.USER_ID = U.ID AND UR.ORG_LOCATIONS_ID = ACANLOC.ORG_LOCATION_ID "
			    + "   LEFT JOIN EMPLOYEE EMP ON EMP.USER_ID = U.ID AND EMP.ORG_LOCATIONS_ID = UR.ORG_LOCATIONS_ID "
			    + "   LEFT JOIN DEPARTMENT DEPART ON DEPART.ID = EMP.DEPARTMENT_ID ";
            
			    //To get current  orglocation details 
			    if(StringUtils.isNotEmpty(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase("Y")) {
			    	query = query+ " LEFT JOIN PROJECT_CLIENTS PC ON PC.PROJECT_ID =  PROJ.ID AND (PC.ORG_LOCATIONS_ID = '"+orgloc.getId()+"' OR  (PC.ORG_LOCATIONS_ID != '"+orgloc.getId()+"' AND PC.ORG_ID = '"+orgloc.getOrg().getId()+"'))";
			    }else {
			    	query = query+ " LEFT JOIN PROJECT_CLIENTS PC ON PC.PROJECT_ID =  PROJ.ID AND PC.ORG_LOCATIONS_ID = '"+orgloc.getId()+"'";
			    			
			    }
                query = query+ " LEFT JOIN ORG_LOCATIONS ORGLOC ON ORGLOC.ID =  PC.ORG_LOCATIONS_ID "
                + " WHERE "+subQuery+" "+userRoleQuery+" AND SAH.SUBMISSION_ID =  TS.ID  " 
				+ " AND SAH.ID IN (SELECT SUBSAH.ID FROM SUBMISSION_APPROVER_HIERARCHY SUBSAH WHERE "
				+ "                  SUBSAH.SUBMISSION_ID = TS.ID "
				+ "                  AND  "
				+ "                   ( "
				+ "                     SUBSAH.HIERARCHY_ID IN(SELECT PAH.ID FROM PROJECT_APPROVER_HIERARCHY PAH WHERE PAH.PROJECT_ID = PROJ.ID AND PAH.APPROVER_USER_ID = '"+loginUser.getId()+"'  AND PAH.STATUS != '0' )"
				+ "                              OR "
				+ "                     SUBSAH.HIERARCHY_ID IN(SELECT PAH.ID FROM PROJECT_APPROVER_HIERARCHY PAH where '"+loginUser.getId()+"' IN "
				+ "                                               ( SELECT UR.USER_ID FROM USER_ROLE UR LEFT JOIN  ROLES R  ON R.ID = UR.ROLE_ID   WHERE R.ROLE_NAME = 'ROLE_ADMIN' AND UR.USER_STATE = 'ACTIVE' AND UR.ORG_LOCATIONS_ID = PC.ORG_LOCATIONS_ID) "
				+ "                                                    AND  PAH.LEVEL IN(select MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.STATUS != '0') )                    "
				+ "                              OR "
				+ "                     SUBSAH.HIERARCHY_ID IN(SELECT PAH.ID FROM PROJECT_APPROVER_HIERARCHY PAH where '"+loginUser.getId()+"' IN(SELECT UR.REPORTING_MANAGER_ID FROM USER_ROLE UR WHERE UR.USER_ID = CAN.CONSULTANT_USER_ID) "
				+ "                        AND  PAH.PROJECT_ID = PROJ.ID  AND  PAH.LEVEL IN(select MIN(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.STATUS != '0'))             "
				+ "                              OR "
				+ "                     SUBSAH.HIERARCHY_ID IN(SELECT PAH.ID FROM PROJECT_APPROVER_HIERARCHY PAH where APPROVER_USER_ID IN("+userHierarchyIdString+") "
				+ "                        AND  PAH.PROJECT_ID = PROJ.ID  AND  PAH.LEVEL IN(select MIN(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND SUBPAH.STATUS != '0'))             "
				+ "                    )"
			    + "              ) AND SAH.ID IS NOT NULL"
				// AND ( SAH.SUBMISSION_APPROVALSTATUS = '"+Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL+"' OR SAH.SUBMISSION_APPROVALSTATUS = '"+Submission_ApprovalStatus.REJECTED+"' OR SAH.SUBMISSION_APPROVALSTATUS = '"+Submission_ApprovalStatus.APPROVED+"')
				+ " AND ((TDE.BILLABLE_HOURS IS NOT NULL AND TDE.BILLABLE_HOURS != '00:00')  OR (TDE.NONBILLABLE_HOURS IS NOT NULL AND TDE.NONBILLABLE_HOURS != '00:00' )) AND TDE.TIMESHEET_DATE BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' group by TDE.ID)"
				+ "  UNION ALL "
				+ " ( "
				+ "   SELECT "
				+ "   DATE_FORMAT(AL.APPLY_LEAVE_DATE,"+orgDateFormat+") AS timesheetDate,"
				+ "   NULL AS timeEntryId,"
				+ "   (  CASE" 
				+ "        WHEN LT.LEAVE_TYPE ='Billable'  THEN AL.APPLIED_HOURS " 
			    + "        ELSE ''"
				+ "      END "
				+ "     ) AS  billableHrs, "
				+ "   NULL AS ticketNo,"
				+ "   (  CASE" 
				+ "        WHEN LT.LEAVE_TYPE !='Billable' THEN AL.APPLIED_HOURS " 
                + "        ELSE ''"
				+ "      END "
				+ "     ) AS  nonBillableHrs, "
				+ "	  EMP.EMPLOYEE_ID as employeeId,"
				+ "   CONCAT(IF(CAN.FIRST_NAME IS NOT NULL, CONCAT(CAN.FIRST_NAME,' '),'') ,IF(CAN.LAST_NAME IS NOT NULL, CONCAT(CAN.LAST_NAME),'')) as employeeName,"
				+ "   CAN.EMAIL AS employeeEmail, "
				+ "   NULL AS projectName,"
				+ "   LV.REASON AS comments,"
				+ "   LT.NAME AS task, "
				+ "   NULL AS status,"
				+ "   DEP.DEPARTMENT_NAME AS departmentName,"
				+ "   NULL AS projectId,"
				+ "   PM.MASTER_CANDIDATE_ID AS masterCandidateId,"
				+ "   NULL AS submittedDate,"
				+ "   NULL AS approvedDate,"
				+ "   NULL AS sahFrequenchStartDate,"
				+ "   NULL AS sahFrequenchEndDate,"
				+ "   (CASE " 
				+ "        WHEN CAN.WORK_LOCATION IS NOT NULL THEN ( "
				+ "        CONCAT( "
				+ "        IF(CANLOC.COUNTRY IS NOT NULL, CONCAT(CANLOC.COUNTRY,','),'') ,"
				+ "        IF(CANLOC.STATE IS NOT NULL, CONCAT(CANLOC.STATE,','),''),"
				+ "        IF(CANLOC.CITY IS NOT NULL, CONCAT(CANLOC.CITY),'')))"
                + "        ELSE  '' "
				+ "      END "
				+ "     ) AS  candidateLocation "
				+ "   FROM PROJECT_MEMBERS PM "
				+ "   INNER JOIN PROJECTS PROJ ON PROJ.ID = PM.PROJECT_ID "
								
				 //To get candidate work location
				+ "   LEFT JOIN CANDIDATE CAN ON CAN.ID =  PM.CANDIDATE_ID "
			    + "   LEFT JOIN ORG_LOCATIONS CANLOC ON CANLOC.ID =  CAN.WORK_LOCATION "
			    + "   LEFT JOIN ORG CANORG ON CANORG.ID = CANLOC.ORG_ID "
			    + "   LEFT JOIN CANDIDATE ACANLOC ON ACANLOC.MASTER_CANDIDATE_ID = PM.MASTER_CANDIDATE_ID  AND ( ACANLOC.ORG_LOCATION_ID = '"+orgloc.getId()+"' OR CANORG.ID =  '"+orgloc.getOrg().getId()+"' )"
			    			       
			    //To get Employee and department details
				+ "   LEFT JOIN USER U ON U.ID = CAN.CONSULTANT_USER_ID "
			    + "   LEFT JOIN USER_ROLE UR ON UR.USER_ID = U.ID AND UR.ORG_LOCATIONS_ID = ACANLOC.ORG_LOCATION_ID "
			    + "   LEFT JOIN EMPLOYEE EMP ON EMP.USER_ID = U.ID AND EMP.ORG_LOCATIONS_ID = UR.ORG_LOCATIONS_ID "
			    + "   LEFT JOIN DEPARTMENT DEP ON DEP.ID = EMP.DEPARTMENT_ID "
				
			    //To get user leave details
				+ "   LEFT JOIN USER_LEAVE LV ON LV.USER_ID = U.ID "
			    + "   LEFT JOIN LEAVE_TYPE LT ON LT.ID = LV.LEAVE_TYPE_ID"
			    + "   LEFT JOIN APPLIED_LEAVE_DATE AL ON AL.LEAVE_ID = LV.ID "
			    
			    + "   LEFT JOIN ORG_LOCATIONS LOC ON LOC.ID = UR.ORG_LOCATIONS_ID "
			   
			    
				+ "   WHERE "+subQuery+userRoleQuery 
				+ "   AND LT.TIMESHEET_AUTOFILL = 'Y' AND (LV.STATUS = 'APPROVED' OR LV.STATUS = 'PENDING')AND AL.APPLY_LEAVE_DATE IS NOT NULL AND AL.APPLY_LEAVE_DATE BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' group by CONCAT(U.ID,AL.APPLY_LEAVE_DATE)"
				+ ") "
				+ "  UNION ALL "
				+ " ( "
				+ "   SELECT "
				+ "   DATE_FORMAT(HRD.DAY,"+orgDateFormat+") AS timesheetDate,"
				+ "   NULL AS timeEntryId,"
				+ "   (  CASE" 
				/*+ "        WHEN HD.BILLABLE_TYPE ='Billable'  THEN HD.ORG_AVG_HRS " */
				+ "        WHEN HD.BILLABLE_TYPE ='Billable'  THEN (CASE  WHEN DAYOFWEEK(HRD.DAY) IN "+weekOffs+" THEN '00:00' ELSE HD.ORG_AVG_HRS END) " 
			    + "        ELSE ''"
				+ "      END "
				+ "     ) AS  billableHrs, "
				+ "   NULL AS ticketNo,"
				+ "   (  CASE" 
				/*+ "        WHEN HD.BILLABLE_TYPE !='Billable' THEN HD.ORG_AVG_HRS "*/ 
				+ "        WHEN HD.BILLABLE_TYPE !='Billable' THEN (CASE  WHEN DAYOFWEEK(HRD.DAY) IN "+weekOffs+" THEN '00:00' ELSE HD.ORG_AVG_HRS  END)"
                + "        ELSE ''"
				+ "      END "
				+ "     ) AS  nonBillableHrs, "
				+ "	  EMP.EMPLOYEE_ID as employeeId,"
				+ "   CONCAT(IF(CAN.FIRST_NAME IS NOT NULL, CONCAT(CAN.FIRST_NAME,' '),'') ,IF(CAN.LAST_NAME IS NOT NULL, CONCAT(CAN.LAST_NAME),'')) as employeeName,"
				+ "   CAN.EMAIL AS employeeEmail, "
				+ "   NULL AS projectName,"
				+ "   HD.DESCRIPTION AS comments,"
				+ "   HD.HOLIDAY_NAME AS task, "
				+ "   NULL AS status,"
				
				+ "   DEP.DEPARTMENT_NAME AS departmentName,"
				+ "   NULL AS projectId,"
				+ "   PM.MASTER_CANDIDATE_ID AS masterCandidateId,"
				+ "   NULL AS submittedDate,"
				+ "   NULL AS approvedDate,"
				+ "   NULL AS sahFrequenchStartDate,"
				+ "   NULL AS sahFrequenchEndDate,"
				+ "   (CASE" 
				+ "        WHEN CAN.WORK_LOCATION IS NOT NULL THEN ( "
				+ "        CONCAT( "
				+ "        IF(CANLOC.COUNTRY IS NOT NULL, CONCAT(CANLOC.COUNTRY,','),'') ,"
				+ "        IF(CANLOC.STATE IS NOT NULL, CONCAT(CANLOC.STATE,','),''),"
				+ "        IF(CANLOC.CITY IS NOT NULL, CONCAT(CANLOC.CITY),'')))"
				+ "        ELSE  '' "
				+ "      END "
				+ "     ) AS  candidateLocation "
				+ "   FROM PROJECT_MEMBERS PM "
				+ "   INNER JOIN PROJECTS PROJ ON PROJ.ID = PM.PROJECT_ID "
			   
			    //To get candidate work location
				+ "   LEFT JOIN CANDIDATE CAN ON CAN.ID =  PM.CANDIDATE_ID "
			    + "   LEFT JOIN ORG_LOCATIONS CANLOC ON CANLOC.ID =  CAN.WORK_LOCATION "
			    + "   LEFT JOIN ORG CANORG ON CANORG.ID = CANLOC.ORG_ID "
			    + "   LEFT JOIN CANDIDATE ACANLOC ON ACANLOC.MASTER_CANDIDATE_ID = PM.MASTER_CANDIDATE_ID  AND ( ACANLOC.ORG_LOCATION_ID = '"+orgloc.getId()+"' OR CANORG.ID =  '"+orgloc.getOrg().getId()+"' )"
			    			       
			    //To get Employee and department details
				+ "   LEFT JOIN USER U ON U.ID = CAN.CONSULTANT_USER_ID "
			    + "   LEFT JOIN USER_ROLE UR ON UR.USER_ID = U.ID AND UR.ORG_LOCATIONS_ID = ACANLOC.ORG_LOCATION_ID "
			    + "   LEFT JOIN EMPLOYEE EMP ON EMP.USER_ID = U.ID AND EMP.ORG_LOCATIONS_ID = UR.ORG_LOCATIONS_ID "
			    + "   LEFT JOIN DEPARTMENT DEP ON DEP.ID = EMP.DEPARTMENT_ID "
			    
			    + "   LEFT JOIN HOLIDAY_RESTRICT_DAYS HRD ON HRD.ORGLOCATION_ID = CAN.WORK_LOCATION "
			    + "   LEFT JOIN HOLIDAY HD ON HD.ID = HRD.HOLIDAY_ID "
				+ "   WHERE "+subQuery+userRoleQuery
				+ "    AND HRD.DAY IS NOT NULL AND HRD.DAY BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' AND HD.ID NOT IN(SELECT UDH.HOLIDAY_ID FROM USER_DROPPED_HOLIDAYS UDH WHERE UDH.USER_ID = U.ID AND UDH.STATUS = '1') group by CONCAT(U.ID,HRD.DAY)"
				+ ") "
				+ " )Q1 ORDER BY Q1.timesheetDate ASC  "; 
		
		Query result = session.createSQLQuery(query).setFirstResult(start).setMaxResults(length);
		List<DayWiseTimesheetReoprtsListDTO> reportList  = result.setResultTransformer(Transformers.aliasToBean(DayWiseTimesheetReoprtsListDTO.class)).list();
		String  countQuery = "SELECT COUNT(*) AS TOTAL FROM (" + query +")Q2;" ;
		countlist = session.createSQLQuery(countQuery).list();
		
		filterCount = countlist.iterator().next().intValue();
		
		int totalCount = 0;
		if(countlist != null && !countlist.isEmpty()) {
			totalCount = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(totalCount);
		
		if(query.toString() != null && ! query.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		response.setData(reportList);
		}
		return response;
  }
}
