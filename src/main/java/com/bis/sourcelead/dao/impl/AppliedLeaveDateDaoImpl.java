package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.dto.LeaveHolidayHoursDTO;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AppliedLeaveDateDaoImpl implements AppliedLeaveDateDao {

	private static final Logger logger = LoggerFactory.getLogger(AppliedLeaveDateDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public int deleteAppliedLeaveDatesByLeaveId(Long leaveId)
	{
		int isDeleted = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from AppliedLeaveDate aleave where aleave.leave.id =:leaveId ";
			Query qry = session.createQuery(query).setLong("leaveId", leaveId);
			int i = qry.executeUpdate();
			return i;
		} catch (Exception e) {
			logger.error("Delete Applied Leave Date By Leave Id Failed Due to "
					+ e.getLocalizedMessage());
		}
		return isDeleted;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDate> getAppliedLeaveDateByUserId(Long userId,Date sDate,Date eDate)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
		String query = "from AppliedLeaveDate al left join fetch al.leave lv left join fetch lv.leaveType lt  where lt.timeSheetAutofill = 'Y' and lv.user.id =:userId and (lv.status = 'APPROVED' OR  lv.status = 'PENDING') and (al.applyLeaveDate >= :startDate and al.applyLeaveDate <= :endDate)";
		List<AppliedLeaveDate> appliedLeaveList  = session.createQuery(query).setLong("userId", userId).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		return appliedLeaveList;
	}
	
	
	@Override
	@Transactional
	public Map<String,Float> getAppliedLeaveDateAndHolidayHoursByUserId(Long userId,Date sDate,Date eDate,Long worklocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
		String query = "  (SELECT AL.APPLIED_HOURS AS totalHrs, "
				     + "   (  CASE" 
				     + "        WHEN LT.LEAVE_TYPE ='Billable'  THEN AL.APPLIED_HOURS " 
			         + "        ELSE ''"
				     + "      END "
				     + "     ) AS  billableHrs, "
				     + "   (  CASE" 
					 + "        WHEN LT.LEAVE_TYPE !='Billable' THEN AL.APPLIED_HOURS " 
		             + "        ELSE ''"
					 + "      END "
					 + "     ) AS  nonBillableHrs "
				     + " FROM APPLIED_LEAVE_DATE AL "
				     + "    LEFT JOIN USER_LEAVE UL ON UL.ID = AL.LEAVE_ID "
				     + "    LEFT JOIN USER UR ON UR.ID = UL.USER_ID "
				     + "    LEFT JOIN LEAVE_TYPE LT ON LT.ID = UL.LEAVE_TYPE_ID "
				     + "    WHERE  LT.TIMESHEET_AUTOFILL = 'Y' AND  UR.ID =:userId "
				     + "     AND (UL.STATUS = 'APPROVED' OR UL.STATUS = 'PENDING')"
				     + "     AND AL.APPLY_LEAVE_DATE >= :startDate AND AL.APPLY_LEAVE_DATE <= :endDate ) " 
				     
				     + "     UNION ALL " 
				    
				     + "    (SELECT HD.ORG_AVG_HRS AS totalHrs, "
				     + "      (  CASE" 
					 + "        WHEN HD.BILLABLE_TYPE ='Billable'  THEN HD.ORG_AVG_HRS " 
				     + "        ELSE ''"
					 + "      END "
					 + "      ) AS  billableHrs, "
					 + "   (  CASE" 
					 + "        WHEN HD.BILLABLE_TYPE !='Billable' THEN HD.ORG_AVG_HRS " 
	                 + "        ELSE ''"
					 + "      END "
					 + "     ) AS  nonBillableHrs "
				     + "      FROM HOLIDAY HD WHERE "
				     + "         HD.FROM_DATE >= :startDate AND HD.TO_DATE <= :endDate AND HD.STATUS != '0' AND HD.ORGLOCATION_ID = :worklocationId AND HD.ID NOT IN(SELECT UDH.HOLIDAY_ID FROM USER_DROPPED_HOLIDAYS UDH WHERE UDH.FROM_DATE = HD.FROM_DATE AND UDH.TO_DATE = HD.TO_DATE AND UDH.USER_ID = :userId AND UDH.STATUS = '1')"
				     + "       )" ;
		
		//LeaveHolidayHoursDTO
		//from Holiday hday where (hday.fromDate >= :fromDate and hday.fromDate <= :endDate) and hday.orgLocations.id = :orgLocationId and hday.status != '0'
		Query result  = session.createSQLQuery(query).setLong("userId", userId).setLong("worklocationId", worklocationId).setParameter("startDate", startDate).setParameter("endDate", endDate);
		List<LeaveHolidayHoursDTO> reportList  = result.setResultTransformer(Transformers.aliasToBean(LeaveHolidayHoursDTO.class)).list();
		Map<String,Float> hoursMap = null;
		float billableHours = 0f;
		float nonBillableHours = 0f;
		float totalHours = 0f;
		if(reportList != null && !reportList.isEmpty()) {
			 hoursMap = new LinkedHashMap<String, Float>();
			for(LeaveHolidayHoursDTO hrsDTO : reportList) {
				if(StringUtils.isNotEmpty(hrsDTO.getBillableHrs())) {
					float hours1 = Float.valueOf(hrsDTO.getBillableHrs().replace(":","."));
					billableHours = CommonUtil.getCombinedHours(hours1, billableHours);
				}
				
				if(StringUtils.isNotEmpty(hrsDTO.getNonBillableHrs())) {
					float hours1 = Float.valueOf(hrsDTO.getNonBillableHrs().replace(":","."));
					nonBillableHours = CommonUtil.getCombinedHours(hours1, nonBillableHours);
				}
				
				
				if(StringUtils.isNotEmpty(hrsDTO.getTotalHrs())) {
					float hours1 = Float.valueOf(hrsDTO.getTotalHrs().replace(":","."));
					totalHours = CommonUtil.getCombinedHours(hours1, totalHours);
				}
			}
			hoursMap.put("BILLABLE", billableHours);
			hoursMap.put("NONBILLABLE", nonBillableHours);
			hoursMap.put("TOTAL", totalHours);
		}
		return hoursMap;
	}
	
	
	
	@Override
	@Transactional
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId,Date startDate,Date endDate)
	{
		List<AppliedLeaveDateView> appliedLeaveList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and alView.orglocationId = :orgLocationid and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate) and (alView.leaveStatus = 'APPROVED' or alView.leaveStatus = 'PENDING')";
		appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationid).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		return appliedLeaveList;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate,Long userId,Long orgId)
	{
       try {
    		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
    		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
    	   Session session = this.sessionFactory.getCurrentSession();
          
    	   String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId AND alView.userId =:userId AND (alView.orglocationId = :orgLocationid OR alView.orgId = :orgId) AND date(alView.applyLeaveDate) between :startDate and :endDate AND (alView.leaveStatus = 'APPROVED' OR alView.leaveStatus = 'PENDING')";
    	   List<AppliedLeaveDateView> appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationId).setLong("userId", userId).setLong("orgId", orgId).setDate("startDate", startDate).setDate("endDate", endDate).list();
           return appliedLeaveList;
       }catch (Exception e) {
    	   e.printStackTrace();
	}
    	return null;
	}

	@Override
	@Transactional
	public AppliedLeaveDate saveAppliedLeaveDates(AppliedLeaveDate appliedLeaveDate) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (appliedLeaveDate != null) {
				session.saveOrUpdate(appliedLeaveDate);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return appliedLeaveDate;
	}

	@Override
	@Transactional
	public boolean checkForExistedLeave(Leave leave) {
		boolean existed = false;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from AppliedLeaveDate ald where ald.leave.id = :leaveId";
			List<Leave> leaveList = session.createQuery(query).setLong("leaveId", leave.getId()).list();
			if(leaveList != null && leaveList.size() > 0) {
				existed = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existed;
	}
	
	
	@Override
	@Transactional
	public AppliedLeaveDate getAppliedLeaveDateByleaveIdAndAppliedLeaveDate(Date date,Long leaveId) {
		java.sql.Date applyLeaveDate = new java.sql.Date(date.getTime());
		AppliedLeaveDate ald = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from AppliedLeaveDate ald where ald.applyLeaveDate = :applyLeaveDate and ald.leave.id = :leaveId and ald.status = '1'";
			List<AppliedLeaveDate> leaveList = session.createQuery(query).setParameter("applyLeaveDate", applyLeaveDate).setLong("leaveId", leaveId).list();
			if(leaveList != null && leaveList.size() > 0) {
				ald = leaveList.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ald;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserIdAndStatus(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId, String leaveStatus,Long orgId)
	{
		try {
	    	   String subQuery = "";
	    	   if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveStatus) ) {
	    		   subQuery =  " and alView.leaveStatus = 'APPROVED' ";
	    	   }
	    	   if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveStatus)) {
	    		   subQuery =  " and alView.leaveStatus = 'REJECTED'";
	    	   }
	    	   if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveStatus)) {
	    		   subQuery =  " and alView.leaveStatus = 'CANCELLED' ";  
	    	   }
	    	   if(Constants.LEAVE_PENDING.equalsIgnoreCase(leaveStatus)){
	    		   subQuery =  " and alView.leaveStatus = 'PENDING' ";  
	    	   }
	    		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
	    		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
	    	   Session session = this.sessionFactory.getCurrentSession();
	           String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and (alView.orglocationId = :orgLocationid or alView.orgId =:orgId) and alView.userId = :userId and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate) "+subQuery;
	           List<AppliedLeaveDateView> appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationId).setLong("userId", userId).setParameter("startDate", startDate).setParameter("endDate", endDate).setLong("orgId", orgId).list();
	           return appliedLeaveList;
	       }catch (Exception e) {
	    	   e.printStackTrace();
		}
	    	return null;
	}

	@Override
	public List<AppliedLeaveDateView> getAppliedLeaveByUserId(Long orgLocationId, Long leaveTypeId, Date startDate, Date endDate, Long userId,Long orgId)
	{
		try {
	    		java.sql.Date sDate = new java.sql.Date(startDate.getTime());
	    		java.sql.Date eDate = new java.sql.Date(endDate.getTime());
	    	   Session session = this.sessionFactory.getCurrentSession();
	           String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and (alView.orglocationId = :orgLocationid or alView.orgId =:orgId) and alView.userId = :userId and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate) ";
	           List<AppliedLeaveDateView> appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationId).setLong("userId", userId).setParameter("startDate", sDate).setParameter("endDate", eDate).setLong("orgId", orgId).list();
	           return appliedLeaveList;
	       }catch (Exception e) {
	    	   e.printStackTrace();
		}
		return null;
	}
	
}
