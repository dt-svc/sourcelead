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
		StringBuilder searchQuery = new StringBuilder("");
		if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
		 searchQuery.append(" timeEntryId like '%" + searchValue + "%' ")
					.append("OR ticketNo like '%" + searchValue + "%' ")
					.append("OR employeeName like '%" + searchValue + "%' ")
					.append("OR employeeEmail like '%" + searchValue + "%' ")
					.append("OR projectName like '%" + searchValue + "%' ")
					.append("OR task like '%" + searchValue + "%' ")
					.append("OR status like '%" + searchValue + "%' ")
					.append("OR departmentName like '%" + searchValue + "%' ");
		}
		
		
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
		String query = " (SELECT TDE.TIMESHEET_DATE as timesheetDate,"
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
				+"  LEFT JOIN PROJECT_MEMBERS PM ON PM.ID =  TS.PROJECT_MEMBER_ID "
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
				+ " AND TDE.TIMESHEET_DATE BETWEEN '"+startDateSql+"' AND '"+endDateSql+"' group by TDE.ID ORDER BY TDE.TIMESHEET_DATE)"
				/*+ "  OR "
				+ " ( SELECT FROM) "*/; 
		
		
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
