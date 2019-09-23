package com.bis.sourcelead.timesheet.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimeSheetDayEntriesHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesHistoryDao;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.util.Calendar;

@Repository
public class TimeSheetDayEntriesDaoImpl implements TimeSheetDayEntriesDao{
 
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	private TimeSheetDayEntriesHistoryDao timeSheetDayEntriesHistoryDao;
	
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

	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesDaoImpl.class);
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries timeSheetDayEntries) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timeSheetDayEntries);
			TimeSheetDayEntriesHistory timesheetHistory = getHistoryRecordFromTimeSheetDayEntries(timeSheetDayEntries);
			if(timesheetHistory != null){
				timeSheetDayEntriesHistoryDao.saveTimeSheetDayEntriesHistory(timesheetHistory);
			}
		}
		catch(HibernateException exception)	{
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TimeSheetDayEntries"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return timeSheetDayEntries;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntries getTimesheetDayEntriesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.timesheetSubmissions left join fetch tde.projectTask   where tde.Id =:id ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("id", id).list();
		TimeSheetDayEntries tde = null;
		if(timesheetEntriesList !=null && !timesheetEntriesList.isEmpty()){
             tde = timesheetEntriesList.iterator().next();
		}
		return tde;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt  where tde.timesheetSubmissions.Id =:submissionId and tde.status = '1'";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).list();	
		return timesheetEntriesList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllTimeSheetDayEntryIdsBySubmissionId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "Select tde.Id from TimeSheetDayEntries tde  where tde.timesheetSubmissions.Id =:submissionId and tde.status = '1'";
		List<Long> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).list();	
		return timesheetEntriesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(
			Long submissionId, Date startDate, Date endDate) throws Exception {
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime()); 
		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt  where tde.timesheetSubmissions.Id =:submissionId and tde.timesheetDate between :startDate and :endDate and tde.status = '1' ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();	
		return timesheetEntriesList;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(Long projectId, Date timesheetDate,Long projectMemberId) throws Exception {
		java.sql.Date sqlTimesheetDate = null;
		if(timesheetDate != null){
			sqlTimesheetDate = new java.sql.Date(timesheetDate.getTime());
		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt left join fetch pt.projectId proj left join fetch tde.timesheetSubmissions sub left join fetch sub.projectMembers proMember  where proj.id =:projectId and tde.timesheetDate=:timesheetDate and proMember.id=:projectMemberId and tde.status = '1' ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("projectId", projectId).setParameter("timesheetDate", sqlTimesheetDate).setLong("projectMemberId", projectMemberId).list();	
		return timesheetEntriesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(Long projectId, Date startDate,Date endDate,Long projectMemberId) throws Exception {
		java.sql.Date sqlTimesheetStartDate = null;
		java.sql.Date sqlTimesheetEndDate = null;
		if(startDate != null){
			sqlTimesheetStartDate = new java.sql.Date(startDate.getTime());
		}
		if(endDate != null){
			sqlTimesheetEndDate = new java.sql.Date(endDate.getTime());
		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt left join fetch pt.projectId proj left join fetch tde.timesheetSubmissions sub left join fetch sub.projectMembers proMember  where proj.id =:projectId and proMember.id=:projectMemberId and tde.status = '1' and tde.timesheetDate between :sqlTimesheetStartDate AND :sqlTimesheetEndDate ORDER BY tde.timesheetDate";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("projectId", projectId).setParameter("sqlTimesheetStartDate", sqlTimesheetStartDate).setParameter("sqlTimesheetEndDate", sqlTimesheetEndDate).setLong("projectMemberId", projectMemberId).list();	
		return timesheetEntriesList;
	}
	
	private TimeSheetDayEntriesHistory  getHistoryRecordFromTimeSheetDayEntries(TimeSheetDayEntries timeSheetDayEntries){
		TimeSheetDayEntriesHistory timeSheetDayEntriesHistory = null;
		if(timeSheetDayEntries != null){
			timeSheetDayEntriesHistory = new TimeSheetDayEntriesHistory();
			timeSheetDayEntriesHistory.setTimeSheetDayEntries(timeSheetDayEntries);
			timeSheetDayEntriesHistory.setTimesheetSubmissions(timeSheetDayEntries.getTimesheetSubmissions());
			timeSheetDayEntriesHistory.setProjectTask(timeSheetDayEntries.getProjectTask());
			timeSheetDayEntriesHistory.setTimesheetDate(timeSheetDayEntries.getTimesheetDate());
			timeSheetDayEntriesHistory.setBillableHours(timeSheetDayEntries.getBillableHours());
			timeSheetDayEntriesHistory.setNonBillableHours(timeSheetDayEntries.getNonBillableHours());
			timeSheetDayEntriesHistory.setTotalHours(timeSheetDayEntries.getTotalHours());
			timeSheetDayEntriesHistory.setTaskType(timeSheetDayEntries.getTaskType());
			timeSheetDayEntriesHistory.setComments(timeSheetDayEntries.getComments());
			timeSheetDayEntriesHistory.setCreatedBy(timeSheetDayEntries.getUpdatedBy());
			timeSheetDayEntriesHistory.setCreatedDate(timeSheetDayEntries.getCreatedDate());
			timeSheetDayEntriesHistory.setUpdatedBy(timeSheetDayEntries.getUpdatedBy());
			timeSheetDayEntriesHistory.setUpdatedDate(timeSheetDayEntries.getUpdatedDate());
			timeSheetDayEntriesHistory.setTimesheetActivityId(timeSheetDayEntries.getTimesheetActivityId());
		}
		return timeSheetDayEntriesHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTimeSheetDayEntries(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		TimeSheetDayEntries tde = getTimesheetDayEntriesById(id);
		Boolean flag = false;
		try {
			timeSheetDayEntriesHistoryDao.deleteAllTimeSheetDayEntriesHistoryByEntryId(id);
			if(tde != null){
				session.delete(tde);
			    logger.debug("TIMESHEET_DAY_ENTRIES Record Deleted Successfully");
			    flag = true;
			}
			
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_DAY_ENTRIES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntries getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(Long submissionId,
			Long taskId, Date timesheetDate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlDate = null;
		if(timesheetDate != null){
			sqlDate = new java.sql.Date(timesheetDate.getTime());
		}
		String query = "from TimeSheetDayEntries tde left join fetch tde.timesheetSubmissions left join fetch tde.projectTask  where tde.timesheetDate =:timesheetDate and tde.timesheetSubmissions.Id =:submissionId and tde.projectTask.Id =:taskId ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setParameter("timesheetDate", sqlDate).setLong("submissionId", submissionId).setLong("taskId", taskId).list();
		TimeSheetDayEntries tde = null;
		if(timesheetEntriesList !=null && !timesheetEntriesList.isEmpty()){
             tde = timesheetEntriesList.iterator().next();
		}
		return tde;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<TimeSheetDayEntries> getTimesheetDayEntriesByTaskUUIDAndUserId(String taskUUID, Long userId,Long locationId,int start, int length, String searchValue) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		ResponseList<TimeSheetDayEntries> response = new ResponseList<>();
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		StringBuilder searchQuery = new StringBuilder();
		StringBuilder searchCountQuery = new StringBuilder();
		if(StringUtils.isNotEmpty(searchValue) && searchValue != null) {
			  Matcher matcher = pattern.matcher(searchValue);
			 searchQuery.append(" and ( ");
			 searchCountQuery.append(" and ( ");
			 searchQuery.append("  user.firstName like '%" + searchValue + "%' ")
						.append("OR user.lastName like '%" + searchValue + "%' ")
						.append("OR (concat(user.firstName,user.lastName) like '%" + searchValue.replaceAll("[\\s]+", "").trim() + "%')" )
			 			.append("OR tde.timesheetSubmissions.id IN(select sah.submissionId.id from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus  like '%"+ searchValue +"%'  and sah.hierarchyId.level IN(select MAX(pah.level) from ProjectApproverHierarchy pah where pah.projectId.id = projects.id and pah.status != '0'))");
				
			 searchCountQuery.append("  tde.timesheetSubmissions.projectMembers.employee.user.firstName like '%" + searchValue + "%' ")
				.append("OR tde.timesheetSubmissions.projectMembers.employee.user.lastName like '%" + searchValue + "%' ")
				.append("OR (concat(tde.timesheetSubmissions.projectMembers.employee.user.firstName,tde.timesheetSubmissions.projectMembers.employee.user.lastName) like '%" + searchValue.replaceAll("[\\s]+", "").trim() + "%')" )
	 			.append("OR tde.timesheetSubmissions.id IN(select sah.submissionId.id from SubmissionApproverHierarchy sah where sah.submissionApprovalStatus  like '%"+ searchValue +"%'  and sah.hierarchyId.level IN(select MAX(pah.level) from ProjectApproverHierarchy pah where pah.projectId.id = tde.projectTask.projectId.id and pah.status != '0'))");
			   if(matcher.matches() == true) {
					java.sql.Date sqlDate = null;
					if(searchValue.contains("/")||(searchValue.contains("-"))) {
						String replacedValue = searchValue.replaceAll("/", "-");
						    OrgLocations orgLoc = orgLocationService.getOrgLocations(locationId);
							SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					        java.util.Date utilDate = format.parse(replacedValue);
					        sqlDate = new java.sql.Date(utilDate.getTime());
					}
					searchQuery.append("OR tde.timesheetDate like '" + sqlDate + "' ");
					searchCountQuery.append("OR tde.timesheetDate like '" + sqlDate + "' ");
				}
				searchQuery.append(" ) ");
				searchCountQuery.append(" ) ");
				
		}
		
		String query = "from  TimeSheetDayEntries tde left join fetch tde.projectTask pt left join fetch tde.timesheetSubmissions ts left join fetch ts.projects projects left join fetch ts.projectMembers pm left join fetch pm.projectMemberCandidteId can left join fetch can.masterCandidateId left join fetch  pm.employee emp left join fetch emp.user user  "
				+ " where  pt.projectTasksUUID = :taskUUID and tde.isManuallyAdded = 'Y' and tde.status = '1' and "
				+ " ( "
				
				+"  ( user.id = :userId ) "
				+" OR "
				    /*
				    *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				    */
				+ "  ( "
				+ "   :userId IN (select pah.approverUserId.id from ProjectApproverHierarchy pah where pah.projectId.id = projects.id ) "
				+ "   ) "
				    /*
					 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
					 */
				+ "  OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :locationId)) "
				+ "   )" ;
		
		String orderQuery = "order by tde.timesheetDate ASC"; 

		String queryCount = " Select Count (*) from  TimeSheetDayEntries tde  where  tde.projectTask.projectTasksUUID = :taskUUID and tde.isManuallyAdded = 'Y' and tde.status = '1' and "
                + " ( "
				+"  ( tde.timesheetSubmissions.projectMembers.employee.user.id = :userId ) "
				+" OR "
				    /*
				    *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
				    */
				+ "  ( "
				+ "   :userId IN (select pah.approverUserId.id from ProjectApproverHierarchy pah where pah.projectId.id = tde.projectTask.projectId.id ) "
				+ "   ) "
				    /*
					 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
					 */
				+ "  OR (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :locationId)) "
				+ "   ) " ;
		
		Query query1 = session.createQuery(query+" "+searchQuery.toString()+" "+orderQuery).setLong("userId", userId).setLong("locationId",locationId).setString("taskUUID", taskUUID);
		if(length>0){
			query1.setFirstResult(start).setMaxResults(length);
		}
		List<TimeSheetDayEntries> tslist = query1.list();
		response.setData(tslist);
		if(length>0){
			Query countQ = session.createQuery(queryCount+" "+searchCountQuery.toString());
			 countQ.setLong("userId", userId).setLong("locationId",locationId).setString("taskUUID", taskUUID);
			
			List<Long> countList  = null;
			if(countQ != null) {
				countList  = countQ.list();
			}
			int recordsTotal=0;
			if(countList != null && !countList.isEmpty()) {
				recordsTotal  =  countList.iterator().next().intValue();
			}
			response.setRecordsTotal(recordsTotal);
			
			int recordsFiltered = 0;
			if(tslist != null && !tslist.isEmpty()) {
				recordsFiltered = tslist.size();
			}
			if(searchQuery.toString() != null && !searchQuery.toString().isEmpty()){
				response.setRecordsFiltered(recordsFiltered);
			}
			else {
				response.setRecordsFiltered(recordsTotal);
			}
		}
		
		return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(Long projectId,Long masterCandidateId,Date timesheetDate) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlDate = new java.sql.Date(timesheetDate.getTime());
		String query = "from TimeSheetDayEntries timeDayEntry  left join fetch timeDayEntry.timesheetSubmissions timeSubmissions where timeDayEntry.timesheetDate = :timeDate and timeDayEntry.status = '1' and timeSubmissions.projects.id = :projectId and timeSubmissions.masterCandidateId.id = :masterCandidateId  ";
		List<TimeSheetDayEntries> tdeList = session.createQuery(query).setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("timeDate", sqlDate).list();
		Float totalHours = 0.0F;
	    if(tdeList != null && !tdeList.isEmpty()){
		   for(TimeSheetDayEntries tde :tdeList) {
			   totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
		   }
	   }
		return totalHours;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntries getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(Long projectId,Long masterCandidateId,Date timesheetDate,Long projectTaskId) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlDate = new java.sql.Date(timesheetDate.getTime());
		String query = "from TimeSheetDayEntries timeDayEntry left join fetch timeDayEntry.projectTask proTask left join fetch timeDayEntry.timesheetSubmissions timeSubmissions where timeDayEntry.timesheetDate = :timeDate  and timeSubmissions.projects.id = :projectId and timeSubmissions.masterCandidateId.id = :masterCandidateId and proTask.Id = :projectTaskId";
		List<TimeSheetDayEntries> tdeList = session.createQuery(query).setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("timeDate", sqlDate).setLong("projectTaskId", projectTaskId).list();
		if(tdeList!= null && !tdeList.isEmpty()) {
			return tdeList.get(0);
		}else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllTimeSheetDayEntryIdsBySubmissionIdAndEntryDate(Long submissionId,Date entryDate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlEntryDate = null;
		if(entryDate != null){
			sqlEntryDate = new java.sql.Date(entryDate.getTime());
		}
		String query = "Select tde.Id from TimeSheetDayEntries tde  where tde.timesheetDate = :entryDate and tde.timesheetSubmissions.Id =:submissionId and tde.status = '1'";
		List<Long> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).setParameter("entryDate", sqlEntryDate).list();	
		return timesheetEntriesList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Double getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,Date startDate,Date endDate,OrgLocations orgLocations) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		long weekOffCount = 0;
		if(StringUtils.isNotEmpty(orgLocations.getWeekOffDays())) {
			 String[] strArray = orgLocations.getWeekOffDays().split(",");
		     List<String> weekOffs = new ArrayList<String>(Arrays.asList(strArray));
		     if(weekOffs != null && !weekOffs.isEmpty()) {
		    	 for(String day_Of_Week : weekOffs) {
		    		 weekOffCount = weekOffCount+CalendarUtil.numberOfDayOccurancesBetweenTwoDates(startDate, endDate, day_Of_Week);
		    	 }
		     }
		    
		}
		 long noOfDays =  CalendarUtil.getNumberOfDaysCount(startDate, endDate);
		 long workingDays = noOfDays-weekOffCount;
		 Integer avgWorkingHoursPerDay = orgLocations.getAverageWorkingHours();
         if(avgWorkingHoursPerDay == null || avgWorkingHoursPerDay == 0) {
				avgWorkingHoursPerDay = 8; 
		 }
         double estimatedHours = avgWorkingHoursPerDay*workingDays;
 		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt  left join fetch tde.timesheetSubmissions ts left join fetch ts.projectMembers pm left join fetch pm.projectMemberCandidteId can left join fetch can.orgLocationId loc where pm.masterCandidateId.id =:masterCandidateId and loc.id =:locationId and tde.timesheetDate between :startDate and :endDate and tde.status = '1' ";
 		List<TimeSheetDayEntries> tdeList = session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("locationId", orgLocations.getId()).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();	
 		double overTimeHours = 0;
 		if (tdeList != null && !tdeList.isEmpty()) {
 			float weekTotalHours = 0f;
			for (TimeSheetDayEntries tde : tdeList) {
				float totalHours = 0f;
				if (StringUtils.isNotEmpty(tde.getTotalHours())) {
					totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
				}
				weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
			}
			overTimeHours = weekTotalHours-estimatedHours;
		}
 		if(overTimeHours <0) {
 			overTimeHours = 0;
 		}
 		return overTimeHours;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,Date startDate,Date endDate,OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		long weekOffCount = 0;
		if(StringUtils.isNotEmpty(orgLocations.getWeekOffDays())) {
			 String[] strArray = orgLocations.getWeekOffDays().split(",");
		     List<String> weekOffs = new ArrayList<String>(Arrays.asList(strArray));
		     if(weekOffs != null && !weekOffs.isEmpty()) {
		    	 for(String day_Of_Week : weekOffs) {
		    		 weekOffCount = weekOffCount+CalendarUtil.numberOfDayOccurancesBetweenTwoDates(startDate, endDate, day_Of_Week);
		    	 }
		     }
		    
		}else {
			 ArrayList<String> weekOffs = new ArrayList<String>();
			 weekOffs.add("Sat");
			 weekOffs.add("Sun");
	    	 for(String day_Of_Week : weekOffs) {
	    		 weekOffCount = weekOffCount+CalendarUtil.numberOfDayOccurancesBetweenTwoDates(startDate, endDate, day_Of_Week);
	    	 }
		}
		 long noOfDays =  CalendarUtil.getNumberOfDaysCount(startDate, endDate);
		 long workingDays = noOfDays-weekOffCount;
		 Integer avgWorkingHoursPerDay = orgLocations.getAverageWorkingHours();
         if(avgWorkingHoursPerDay == null || avgWorkingHoursPerDay == 0) {
				avgWorkingHoursPerDay = 8; 
		 }
         double estimatedHours = avgWorkingHoursPerDay*workingDays;
         String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt  left join fetch tde.timesheetSubmissions ts left join fetch ts.projectMembers pm left join fetch pm.projectMemberCandidteId can left join fetch can.orgLocationId loc where pm.masterCandidateId.id =:masterCandidateId and loc.id =:locationId and tde.timesheetDate between :startDate and :endDate and tde.status = '1' ";
 		List<TimeSheetDayEntries> tdeList = session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("locationId", orgLocations.getId()).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();	
 		boolean flag = true;
 		if (tdeList != null && !tdeList.isEmpty()) {
 			float weekTotalHours = 0f;
			for (TimeSheetDayEntries tde : tdeList) {
				float totalHours = 0f;
				if (StringUtils.isNotEmpty(tde.getTotalHours())) {
					totalHours = CommonUtil.getCombinedHours(Float.parseFloat(tde.getTotalHours().replaceAll(":", ".")), totalHours);
				}
				weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, totalHours);
			}
			if(weekTotalHours >= estimatedHours) {
				flag = false;
			}
		}
 		return flag;
	}
	

}
