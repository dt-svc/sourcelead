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
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Repository
public class DayEntryReportsDaoImpl implements DayEntryReportsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<DayWiseTimesheetReoprtsListDTO> getDayWiseReportListBasedOnProjectListIdStartDateEndDate(List<Long> projectId,Date startDate, Date endDate,String searchValue,int start,int length,OrgLocations orgloc , User loginUser,String isGlobalAccess,List<Long> projectMemberIds) {
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<DayWiseTimesheetReoprtsListDTO> response = new ResponseList<DayWiseTimesheetReoprtsListDTO>();
		Session session = sessionFactory.getCurrentSession();
		if(projectId!= null && !projectId.isEmpty()) {
		java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
		java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());
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
		for(Long pId : projectId) {
			subQuery = subQuery+"PROJ.ID = '"+pId+"' OR ";
		}
		subQuery = "("+subQuery.substring(0,subQuery.length()-3)+")";
		
		String pmSubQuery = "";
		if(projectMemberIds != null && !projectMemberIds.isEmpty()) {
			for(Long pId : projectMemberIds) {
				pmSubQuery = pmSubQuery+" (PM.MASTER_CANDIDATE_ID = '"+pId+"' AND PM.PROJECT_ID = PROJ.ID) OR ";
			}
			pmSubQuery = "("+pmSubQuery.substring(0,pmSubQuery.length()-3)+")";
		}
		
		if(StringUtils.isNotEmpty(pmSubQuery)) {
			subQuery = subQuery+" AND "+pmSubQuery;
		}
		String query = "SELECT Q1.*  "
				+"FROM ( "
				+ "(SELECT TDE.TIMESHEET_DATE as timesheetDate,"
				+ " TDE.ID as timeEntryId,"
				+ " TDE.BILLABLE_HOURS as billableHrs,"
				+ " TDE.TICKET_NUMBER as ticketNo,"
				+ " TDE.NONBILLABLE_HOURS as nonBillableHrs,"
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
				+ " CONCAT(IF(ORGLOC.COUNTRY IS NOT NULL, CONCAT(ORGLOC.COUNTRY,','),'') ,IF(ORGLOC.STATE IS NOT NULL, CONCAT(ORGLOC.STATE,','),''),IF(ORGLOC.CITY IS NOT NULL, CONCAT(ORGLOC.CITY),'')) as candidateLocation"
				+ " FROM SUBMISSION_APPROVER_HIERARCHY SAH,TIMESHEET_DAY_ENTRIES TDE "
				+ " INNER JOIN PROJECT_TASKS PT ON PT.ID = TDE.PROJECT_TASK_ID "
				+ " INNER JOIN PROJECTS PROJ ON PROJ.ID =  PT.PROJECT_ID "
                + " INNER JOIN TIMESHEET_SUBMISSIONS TS ON TS.ID =  TDE.SUBMISSION_ID "
				+ " LEFT JOIN PROJECT_MEMBERS PM ON PM.ID =  TS.PROJECT_MEMBER_ID "
                + " LEFT JOIN CANDIDATE CAN ON CAN.ID =  PM.CANDIDATE_ID "
                + " LEFT JOIN EMPLOYEE EMP ON EMP.ID =  PM.EMPLOYEE_ID "
                + " LEFT JOIN DEPARTMENT DEPART ON DEPART.ID =  EMP.DEPARTMENT_ID "
                + " INNER JOIN PROJECT_CLIENTS PC ON PC.PROJECT_ID =  PROJ.ID AND PC.ORG_LOCATIONS_ID = '"+orgloc.getId()+"' "
                + " LEFT JOIN ORG_LOCATIONS ORGLOC ON ORGLOC.ID =  PC.ORG_LOCATIONS_ID "
				+ " WHERE "+subQuery+" AND SAH.SUBMISSION_ID =  TS.ID  " 
				+ " AND SAH.ID IN (SELECT SUBSAH.ID FROM SUBMISSION_APPROVER_HIERARCHY SUBSAH WHERE "
				+ "                  SUBSAH.SUBMISSION_ID = TS.ID "
				+ "                  AND  "
				+ "                   ( "
				+ "                     SUBSAH.HIERARCHY_ID IN(SELECT PAH.ID FROM PROJECT_APPROVER_HIERARCHY PAH WHERE PAH.PROJECT_ID = PROJ.ID AND PAH.APPROVER_USER_ID = '"+loginUser.getId()+"' AND PAH.PROJECT_CLIENTS_ID = PC.ID AND PAH.STATUS != '0' )"
				+ "                              OR "
				+ "                     SUBSAH.HIERARCHY_ID IN(SELECT PAH.ID FROM PROJECT_APPROVER_HIERARCHY PAH where '"+loginUser.getId()+"' IN "
				+ "                                               ( SELECT UR.USER_ID FROM USER_ROLE UR LEFT JOIN  ROLES R  ON R.ID = UR.ROLE_ID   WHERE R.ROLE_NAME = 'ROLE_ADMIN' AND UR.USER_STATE = 'ACTIVE' AND UR.ORG_LOCATIONS_ID = PC.ORG_LOCATIONS_ID) "
				+ "                                                    AND  PAH.LEVEL IN(select MAX(SUBPAH.LEVEL) FROM PROJECT_APPROVER_HIERARCHY SUBPAH WHERE SUBPAH.PROJECT_CLIENTS_ID = PC.ID AND PAH.STATUS != '0') )                    "
				+ "                    )"
			    + "              ) "
				+ " AND ((TDE.BILLABLE_HOURS IS NOT NULL AND TDE.BILLABLE_HOURS != '00:00')  OR (TDE.NONBILLABLE_HOURS IS NOT NULL AND TDE.NONBILLABLE_HOURS != '00:00' )) TDE.TIMESHEET_DATE BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' group by TDE.ID)"
				+ "  UNION ALL "
				+ " ( "
				+ "   SELECT "
				+ "   AL.APPLY_LEAVE_DATE AS timesheetDate,"
				+ "   NULL AS timeEntryId,"
				+ "   (  CASE" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '1' THEN '01:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '2' THEN '02:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '3' THEN '03:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '4' THEN '04:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '5' THEN '05:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '6' THEN '06:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '7' THEN '07:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '8' THEN '08:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '9' THEN '09:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '10' THEN '10:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '11' THEN '11:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '12' THEN '12:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '13' THEN '13:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '14' THEN '14:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '15' THEN '15:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '16' THEN '16:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '17' THEN '17:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '18' THEN '18:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '19' THEN '19:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '20' THEN '20:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '21' THEN '21:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '22' THEN '22:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '23' THEN '23:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE ='Billable' AND LOC.AVERAGE_WORKING_HOURS = '24' THEN '24:00'"
				+ "        WHEN (LOC.AVERAGE_WORKING_HOURS IS NULL OR LOC.AVERAGE_WORKING_HOURS = '0') AND  LT.LEAVE_TYPE ='Billable' THEN '08:00' "
			    + "        ELSE ''"
				+ "      END "
				+ "     ) AS  billableHrs, "
				+ "   NULL AS ticketNo,"
				+ "   (  CASE" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '1' THEN '01:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '2' THEN '02:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '3' THEN '03:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '4' THEN '04:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '5' THEN '05:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '6' THEN '06:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '7' THEN '07:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '8' THEN '08:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '9' THEN '09:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '10' THEN '10:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '11' THEN '11:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '12' THEN '12:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '13' THEN '13:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '14' THEN '14:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '15' THEN '15:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '16' THEN '16:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '17' THEN '17:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '18' THEN '18:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '19' THEN '19:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '20' THEN '20:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '21' THEN '21:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '22' THEN '22:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '23' THEN '23:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LT.LEAVE_TYPE !='Billable' AND LOC.AVERAGE_WORKING_HOURS = '24' THEN '24:00'"
				+ "        WHEN (LOC.AVERAGE_WORKING_HOURS IS NULL OR LOC.AVERAGE_WORKING_HOURS = '0') AND  LT.LEAVE_TYPE !='Billable' THEN '08:00' "
                + "        ELSE ''"
				+ "      END "
				+ "     ) AS  nonBillableHrs, "
				+ "   CONCAT(IF(CAN.FIRST_NAME IS NOT NULL, CONCAT(CAN.FIRST_NAME,' '),'') ,IF(CAN.LAST_NAME IS NOT NULL, CONCAT(CAN.LAST_NAME),'')) as employeeName,"
				+ "   CAN.EMAIL AS employeeEmail, "
				+ "   NULL AS projectName,"
				+ "   NULL AS comments,"
				+ "   LT.NAME AS task, "
				+ "   NULL AS status,"
				+ "   DEP.DEPARTMENT_NAME AS departmentName,"
				+ "   NULL AS projectId,"
				+ "   PM.MASTER_CANDIDATE_ID AS masterCandidateId,"
				+ "   NULL AS submittedDate,"
				+ "   NULL AS approvedDate,"
				+ "   NULL AS sahFrequenchStartDate,"
				+ "   NULL AS sahFrequenchEndDate,"
				+ "   CONCAT(IF(LOC.COUNTRY IS NOT NULL, CONCAT(LOC.COUNTRY,','),'') ,IF(LOC.STATE IS NOT NULL, CONCAT(LOC.STATE,','),''),IF(LOC.CITY IS NOT NULL, CONCAT(LOC.CITY),'')) as candidateLocation"
				+ "   FROM PROJECT_MEMBERS PM "
				+ "   INNER JOIN PROJECTS PROJ ON PROJ.ID = PM.PROJECT_ID "
			    + "   LEFT JOIN CANDIDATE CAN ON CAN.ID =  PM.CANDIDATE_ID "
				+ "   LEFT JOIN USER U ON U.ID = CAN.CONSULTANT_USER_ID "
				+ "   LEFT JOIN USER_LEAVE LV ON LV.USER_ID = U.ID "
			    + "   LEFT JOIN LEAVE_TYPE LT ON LT.ID = LV.LEAVE_TYPE_ID"
			    + "   LEFT JOIN APPLIED_LEAVE_DATE AL ON AL.LEAVE_ID = LV.ID "
			    + "   LEFT JOIN USER_ROLE UR ON UR.USER_ID = U.ID AND UR.IS_PRIMARY = '1' "
			    + "   LEFT JOIN ORG_LOCATIONS LOC ON LOC.ID = UR.ORG_LOCATIONS_ID "
			    + "   LEFT JOIN DEPARTMENT DEP ON DEP.ID = LT.DEPARTMENT_ID"
				+ "   WHERE "+subQuery
				+ "   AND LT.TIMESHEET_AUTOFILL = 'Y' AND (LV.STATUS = 'APPROVED' OR LV.STATUS = 'PENDING')AND AL.APPLY_LEAVE_DATE IS NOT NULL AND AL.APPLY_LEAVE_DATE BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' group by CONCAT(U.ID,AL.APPLY_LEAVE_DATE)"
				+ ") "
				+ "  UNION ALL "
				+ " ( "
				+ "   SELECT "
				+ "   HRD.DAY AS timesheetDate,"
				+ "   NULL AS timeEntryId,"
				+ "   (  CASE" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '1' THEN '01:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '2' THEN '02:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '3' THEN '03:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '4' THEN '04:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '5' THEN '05:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '6' THEN '06:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '7' THEN '07:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '8' THEN '08:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '9' THEN '09:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '10' THEN '10:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '11' THEN '11:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '12' THEN '12:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '13' THEN '13:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '14' THEN '14:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '15' THEN '15:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '16' THEN '16:00'"  
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '17' THEN '17:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '18' THEN '18:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '19' THEN '19:00'" 
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '20' THEN '20:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '21' THEN '21:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '22' THEN '22:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '23' THEN '23:00'"
				+ "        WHEN LOC.AVERAGE_WORKING_HOURS IS NOT NULL AND LOC.AVERAGE_WORKING_HOURS != '0' AND LOC.AVERAGE_WORKING_HOURS = '24' THEN '24:00'"
				+ "        ELSE '08:00'"
				+ "      END "
				+ "     ) AS  billableHrs, "
				+ "   NULL AS ticketNo,"
				+ "   NULL AS nonBillableHrs,"
				+ "   CONCAT(IF(CAN.FIRST_NAME IS NOT NULL, CONCAT(CAN.FIRST_NAME,' '),'') ,IF(CAN.LAST_NAME IS NOT NULL, CONCAT(CAN.LAST_NAME),'')) as employeeName,"
				+ "   CAN.EMAIL AS employeeEmail, "
				+ "   NULL AS projectName,"
				+ "   NULL AS comments,"
				+ "   HD.HOLIDAY_NAME AS task, "
				+ "   NULL AS status,"
				+ "   DEP.DEPARTMENT_NAME AS departmentName,"
				+ "   NULL AS projectId,"
				+ "   PM.MASTER_CANDIDATE_ID AS masterCandidateId,"
				+ "   NULL AS submittedDate,"
				+ "   NULL AS approvedDate,"
				+ "   NULL AS sahFrequenchStartDate,"
				+ "   NULL AS sahFrequenchEndDate,"
				+ "   CONCAT(IF(LOC.COUNTRY IS NOT NULL, CONCAT(LOC.COUNTRY,','),'') ,IF(LOC.STATE IS NOT NULL, CONCAT(LOC.STATE,','),''),IF(LOC.CITY IS NOT NULL, CONCAT(LOC.CITY),'')) AS candidateLocation"
				+ "   FROM PROJECT_MEMBERS PM "
				+ "   INNER JOIN PROJECTS PROJ ON PROJ.ID = PM.PROJECT_ID "
			    + "   LEFT JOIN CANDIDATE CAN ON CAN.ID =  PM.CANDIDATE_ID "
				+ "   LEFT JOIN USER U ON U.ID = CAN.CONSULTANT_USER_ID "
				+ "   LEFT JOIN USER_ROLE UR ON UR.USER_ID = U.ID AND UR.IS_PRIMARY = '1' "
			    + "   LEFT JOIN HOLIDAY_RESTRICT_DAYS HRD ON HRD.ORGLOCATION_ID = UR.ORG_LOCATIONS_ID "
			    + "   LEFT JOIN HOLIDAY HD ON HD.ID = HRD.HOLIDAY_ID "
			    + "   LEFT JOIN ORG_LOCATIONS LOC ON LOC.ID = UR.ORG_LOCATIONS_ID "
			    + "   LEFT JOIN EMPLOYEE EMP ON EMP.USER_ID = U.ID AND EMP.ORG_LOCATIONS_ID = LOC.ID "
			    + "   LEFT JOIN DEPARTMENT DEP ON DEP.ID = EMP.DEPARTMENT_ID "
				+ "   WHERE "+subQuery
				+ "    AND HRD.DAY IS NOT NULL AND HRD.DAY BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' group by CONCAT(HRD.ORGLOCATION_ID,HRD.DAY)"
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
