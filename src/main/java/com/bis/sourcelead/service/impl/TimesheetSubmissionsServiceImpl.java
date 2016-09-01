package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class TimesheetSubmissionsServiceImpl implements TimesheetSubmissionsService {

	@Autowired
	TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
	HttpServletRequest request;
	
	
	 /**
	 * @author  Sammeta David
	 * @since   aug 19 2016
	 * @version 0.0.1
	 * 
	 * This  controller method is for getting TimesheetSubmission Counts based on Status
	 * 
	 * @param user This is the first parameter TimesheetSubmission  Counts
	 *  
	 * @return TimeSheetSubmissionCount 
	 * 
	 * @throws NullPointer
	 */

	 @Override
	 public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserId(Long userId) throws Exception {
		
			 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
			 
			            Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, new Long(3), "DRAFT");
		        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "DRAFT");
		        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
		        timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(draftTimesheetsSubmissionsCount);
	
		        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId,new Long(3), "SUBMITTED");
		        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "SUBMITTED");
		        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
		        timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(submittedTimesheetsSubmissionsCount);
				
			    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId,new Long(3), "APPROVED");
			    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "APPROVED");
		        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
		        timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(approvalTimesheetsSubmissionsCount);
				
				Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId,new Long(3), "REJECTED");
			    Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "REJECTED");
				    Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
				    timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(rejectedTimesheetsSubmissionsCount);
		
		        return timeSheetSubmissionCount;
	 }

	 
	/**
	 * @author  Sammeta David
	 * @since   aug 19 2016
	 * @version 0.0.1
	 * 
	 *  This  controller method is for getting TimesheetSubmission Hours Counts based on login User Id
	 * 
	 * @param user This is the first parameter TimesheetSubmission  Counts
	 *  
	 * @return TimeSheetSubmissionCount 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsHoursCountByUserId(Long userId) {
		
		    TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		    
		    Float draftTimesheetsSubmissionsHoursCount = null;
		    Float submittedTimesheetsSubmissionsHoursCount = null;
		    Float approvalTimesheetsSubmissionsHoursCount = null;
		    Float rejectedTimesheetsSubmissionsHoursCount = null;
			
				       //draft
				         draftTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId, "NEW");
			             timeSheetSubmissionCount.setDraftTimesheetsSubmissionsHoursCount(draftTimesheetsSubmissionsHoursCount.toString());
			           //Submitted
			             submittedTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId, "SUBMITTEDFORAPPROVAL");
			             timeSheetSubmissionCount.setSubmittedTimesheetsSubmissionsHoursCount(submittedTimesheetsSubmissionsHoursCount.toString());
			           //Approved
			             approvalTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId, "APPROVED");
			  	         timeSheetSubmissionCount.setApprovalTimesheetsSubmissionsHoursCount(approvalTimesheetsSubmissionsHoursCount.toString());
			  	       //Rejected  
			  	        rejectedTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId, "REJECTED");
				  	    timeSheetSubmissionCount.setRejectedTimesheetsSubmissionsHoursCount(rejectedTimesheetsSubmissionsHoursCount.toString());
				  	  
		  return timeSheetSubmissionCount;
	}
	
			
	/**
	 * @author  Sammeta David
	 * @since   aug 19 2016
	 * @version 0.0.1
	 * 
	 *  This   method is for getting Projects Based On UserId And Status And Client For Filters
	 * 
	 * @param user This is the first parameter to get Projects  
	 * 
	 * @param user This is the first parameter to get Projects  
	 * 
	 * @param user This is the first parameter to get Projects 
	 *  
	 * @return Collection<TimesheetSubmissions> 
	 * 
	 * @throws NullPointer
	 */
	@Override
		public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId,
				String clientId, String values) throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getProjectsBasedOnUserIdAndStatusAndClientForFilters(userId,clientId,values);
			return list;
		}
	
	
	/**
	 * @author   naresh bolisetty and sarath kumar
	 * @since   aug 19 2016
	 * @version 0.0.1
	 * 
	 *  This   method is for getting Users TimeSheetSubmissions Count Based On Status
	 * 
	 * @param userId This is the first parameter to get Count  
	 * 
	 * @param approvalStatus This is the second parameter to get Count  
	 * 
	 * @param orgLocationId This is the third parameter to get Count 
	 *  
	 * @return Integer
	 * 
	 * @throws NullPointer
	 */
	@Override
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId, String approvalStatus,Long orgLocationId) throws Exception {
		Integer approvalStatusCount = timesheetSubmissionsDao.getUsersTimeSheetSubmissionsCountBasedOnStatus(userId,approvalStatus,orgLocationId);
		return approvalStatusCount;
	}

	
	/**
	 * @author   naresh bolisetty and sarath kumar
	 * @since   aug 19 2016
	 * @version 0.0.1
	 * 
	 * This Method is for  getting  TimeSheetSubmissions Count By UserId And OrgLocationId
	 * 
	 * @param user This is the first parameter TimesheetSubmission  Counts
	 * 
	 * @param orgLocationId This is the Second parameter TimesheetSubmission  Counts
	 *  
	 * @return TimeSheetSubmissionCount 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		
		 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		 
		Collection<SubmissionApproverHierarchy> approvedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "APPROVED");
		timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeList.size()));
		 
		Collection<SubmissionApproverHierarchy> rejectedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "REJECTED");
		timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeList.size()));
		
		
		Collection<SubmissionApproverHierarchy> submittedForApprovalindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"SUBMITTED");
		timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeList.size()));
		
		Collection<SubmissionApproverHierarchy> newindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"NEW");
		
		Collection<SubmissionApproverHierarchy> draftindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"DRAFT");
		
		Integer savedTimesheetSubmissionCount = newindividualEmployeeList.size() + draftindividualEmployeeList.size();
		timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
		
		return timeSheetSubmissionCount;
		 
	}

	/**
	 * @author   naresh bolisetty and sarath kumar
	 * @since   aug 19 2016
	 * @version 0.0.1
	 * 
	 *  This Method is to get TimeSheet Submissions Count By UserId And OrgLocationId For Approver Login
	 * 
	 * @param user This is the first parameter TimesheetSubmission  Counts
	 * 
	 * @param orgLocationId This is the Second parameter TimesheetSubmission  Counts
	 *  
	 * @return TimeSheetSubmissionCount 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long userId,
			Long orgLocationId) throws Exception {
		
		    TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		
		 
		    Collection<SubmissionApproverHierarchy> approvedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,  "APPROVED");
			timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeList.size()));
			 
			Collection<SubmissionApproverHierarchy> rejectedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, "REJECTED");
			timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeList.size()));
			
			Collection<SubmissionApproverHierarchy> submittedForApprovalindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"SUBMITTED");
			timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeList.size()));
			
			Collection<SubmissionApproverHierarchy> newindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"NEW");
			
			Collection<SubmissionApproverHierarchy> draftindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"DRAFT");
			Integer savedTimesheetSubmissionCount = newindividualEmployeeList.size() +draftindividualEmployeeList.size();
			
			timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
			
		 return timeSheetSubmissionCount;
	   }
		
	  /**
		 * @author   naresh bolisetty and sarath kumar
		 * @since   aug 19 2016
		 * @version 0.0.1
		 * 
		 *  This Method is get SubmissionApproverHierarchy For EmployeeList
		 * 
		 * @param approverUserId This is the first parameter TimesheetSubmission  Counts
		 * 
		 * @return List<TimesheetSubmissions>
		 * 
		 * @throws NullPointer
		 */
		@Override
		public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId)
				throws Exception {
			List<TimesheetSubmissions> list = timesheetSubmissionsDao.getSubmissionApproverHierarchyForEmployeeList(approverUserId);
			return list;
		}

		/**
		 * @author   naresh bolisetty and sarath kumar
		 * @since   aug 19 2016
		 * @version 0.0.1
		 * 
		 *  This Method is get TimesheetSubmissions By MemberId And StartDate 
		 * 
		 * @param memberId This is the first parameter TimesheetSubmissions  
		 * 
		 * @return TimesheetSubmissions
		 * 
		 * @throws NullPointer
		 */
		@Override
		public TimesheetSubmissions getTimesheetSubmissionsByMemberIdAndStartDate(Long memberId, Date startDate)
				throws Exception {
			return timesheetSubmissionsDao.getTimesheetSubmissionsByMemberIdAndStartDate(memberId, startDate);
		}

		
	
		/**
		 * @author   naresh bolisetty and sarath kumar
		 * @since   aug 19 2016
		 * @version 0.0.1
		 * 
		 *  This Method is get All TimesheetSubmissions GreaterThan ProjectMember Terminated Date And MemberId 
		 * 
		 *  @param memberId This is the first parameter TimesheetSubmissions 
		 *  @param terminatedDate This is the Second parameter TimesheetSubmissions 
		 * 
		 * @return TimesheetSubmissions
		 * 
		 * @throws NullPointer
		 */
		@Override
		public List<TimesheetSubmissions> getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(
				Long memberId, Date terminatedDate) throws Exception {
			return timesheetSubmissionsDao.getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(memberId, terminatedDate);
		}

		
		 /**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is used to getArrovalSubmissions
		 * 
		 * @param employeeId This is the first paramter to storeProject method
		 * @param user This is the second paramter to storeProject method
		 *  
		 * @return string 
		 * 
		 * @throws NullPointer
		 */
		
		@Override
		public String getArrovalSubmissions(String employeeId,User user) throws Exception {
			Collection<TimesheetSubmissions> timesheetSubmissionsList =  getApprovalTimesheetSubmissions(user.getId());
	        StringBuilder sb = new StringBuilder();
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				
			sb.append(" <table id='example' class='display' cellspacing='0' width='100%' >");
			sb.append(" <thead>");
			sb.append("     <tr>");
			sb.append("         <th>Employee</th>");
			sb.append("         <th>Frequency</th>");
			sb.append("         <th>Total hours</th>");
			sb.append("         <th>Stage</th>");
			sb.append("         <th>Action</th>");
			sb.append("     </tr>");
			sb.append(" </thead>");
			sb.append(" <tbody>");	
			 
	        String pattern = "dd-MM-yyyy";
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
	        Date currentStartDate = null;
	        Date currentEndDate = null;
	        
	        
	        for(TimesheetSubmissions ts : timesheetSubmissionsList){
	        	 
	        	
	        	   HashMap<String, Date> submissionDateRange = ts.getDateList();
	        	   Date employeeStartDate = submissionDateRange.get("STARTDATE");
	        	   Date employeeEndDate = submissionDateRange.get("ENDDATE");
	        	   
	        	   
	        	   String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	        	   
	        	   if(StringUtils.isNotEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_APPROVER")){
	        		   /**
	 	      		   *calculating prior 6th week mondaydate and post 6th week sunday day for employee
	 	      		   *
	 	      	       *
	 	      	       */
	 		      		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
	 		      		cal.setLenient(false);
	 		      		cal.setTime(new Date());
	 		      		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
	 		      		Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN");
	 		      		
	 		      		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
	 		      		Date previousMondayDate = previousMonday.getTime();
	 		      		
	 		      		Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
	 		      		Date nextSundayDate = nextSunday.getTime();
	 		      		
	 		      	   /**
	 		  		   * checking whether prior 6th week monday date is greaterthan employee startdate or not
	 		  	       */
	 		  		   if(employeeStartDate.before(previousMondayDate)){
	 		  			   employeeStartDate = previousMondayDate;
	 		  		   }
	 		  		   
	 		  		  /**
	 		  		   * checking whether post 6th week sunday date is lesserthan employee enddate or not
	 		  	       */
	 		  		   if(employeeEndDate.after(nextSundayDate)){
	 		  			   employeeEndDate = nextSundayDate;
	 		  		   }
	        	   }
	        	   
	        	   Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
				   Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				   startCal.setLenient(false);
	        	   
	        	   if(currentWeekStartDate.equals(employeeStartDate)|| (currentWeekStartDate.before(employeeEndDate) && currentWeekStartDate.after(employeeStartDate))){
				    	  startCal.setTime(currentWeekStartDate);
			       }
			       else if(employeeEndDate.before(currentWeekStartDate)){
			    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    	  endCal.setLenient(false);
			    	  endCal.setTime(employeeEndDate);
			    	  startCal.setTime(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(endCal,"MON").getTime());
			       }
			       else if(employeeStartDate.after(currentWeekStartDate)){
			    	  startCal.setTime(employeeStartDate);
			       }
	        	   
	        	   String startDate =  format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime());
			       String endDate   =  format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime());
				 
	        		 		
			       ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
			       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
	        	   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
	        		   ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
	        	   }
	        	   
	        	   currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
	               currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
	               
	               if(currentStartDate !=null){
	            	   	ts.setStartDateStr(Constants.DF_DMY.format(currentStartDate));
	               }
	        	   if(currentEndDate !=null){
	        		   ts.setEndDateStr(Constants.DF_DMY.format(currentEndDate));
	        	   }
	        	   
	        	   
	        	  String rows = dynamicTimesheet(user, "left", startDate, ts.getProjectMembers().getId(), ts.getProjectMembers().getEmployee().getUser().getId(), ts.getProjectMembers().getEmployee().getId());
	        	  sb.append("<tr id='row"+ts.getProjectMembers().getEmployee().getUser().getId()+"'>");
	        	  sb.append(rows);
	        	  sb.append("</tr>");
	         }
	         sb.append("	<div>");
			}
	       return sb.toString();
		}

		 /**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is used to dynamicTimesheet
		 * 
		 * @param user This is the first paramter to storeProject method
		 * @param navigation This is the second paramter to storeProject method
		 * @param startDate This is the third paramter to storeProject method
		 * @param memberId This is the fourth paramter to storeProject method
		 * @param projectMemberUserId This is the fifth paramter to storeProject method
		 * @param employeeId This is the sixth paramter to storeProject method
		 *  
		 * @return string 
		 * 
		 * @throws NullPointer
		 */
		
		@Override
		public String dynamicTimesheet(User user,String navigation,String startDate,Long memberId,Long projectMemberUserId, Long employeeId) throws Exception {
			 
			HashMap<String, Date> submissionDateRange =	 timesheetSubmissionsDao.getMinAndMaxTimesheetSubmissionDatesForApprover(user.getId(),projectMemberUserId);
			Date employeeStartDate = submissionDateRange.get("STARTDATE");
			Date employeeEndDate = submissionDateRange.get("ENDDATE");
			
			String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
      	   
      	    if(StringUtils.isNotEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_APPROVER")){
      		   /**
	      		   *calculating prior 6th week mondaydate and post 6th week sunday day for employee
	      		   *
	      	       *
	      	       */
		      		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		      		cal.setLenient(false);
		      		cal.setTime(new Date());
		      		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
		      		Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN");
		      		
		      		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		      		Date previousMondayDate = previousMonday.getTime();
		      		
		      		Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
		      		Date nextSundayDate = nextSunday.getTime();
		      		
		      	   /**
		  		   * checking whether prior 6th week monday date is greaterthan employee startdate or not
		  	       */
		  		   if(employeeStartDate.before(previousMondayDate)){
		  			   employeeStartDate = previousMondayDate;
		  		   }
		  		   
		  		  /**
		  		   * checking whether post 6th week sunday date is lesserthan employee enddate or not
		  	       */
		  		   if(employeeEndDate.after(nextSundayDate)){
		  			   employeeEndDate = nextSundayDate;
		  		   }
      	    }
			TimesheetSubmissions  ts = null;
			
			StringBuilder sb = new StringBuilder();	
			if(employeeStartDate != null && employeeStartDate != null){
			
			  StringBuilder sb1 = new StringBuilder();
			 
			  StringBuilder startDateBuilder = new StringBuilder();
			  StringBuilder endDateBuilder = new StringBuilder();
			 
			  String pattern = "dd-MM-yyyy";
	          SimpleDateFormat format = new SimpleDateFormat(pattern);
	          Date currentStartDate = null;
	          Date currentEndDate = null;
	          Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			   startCal.setLenient(false);
			   if(startDate == null || startDate.equalsIgnoreCase("undefined") || startDate.isEmpty() ){  
				      Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
				      if(currentWeekStartDate.equals(employeeStartDate)|| (currentWeekStartDate.before(employeeEndDate) && currentWeekStartDate.after(employeeStartDate))){
				    	  startCal.setTime(currentWeekStartDate);
				      }
				      else if(employeeEndDate.before(currentWeekStartDate)){
				    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				    	  endCal.setLenient(false);
				    	  endCal.setTime(employeeEndDate);
				    	  startCal.setTime(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(endCal,"MON").getTime());
				      }
				      else if(employeeStartDate.after(currentWeekStartDate)){
				    	  startCal.setTime(employeeStartDate);
				      }
	          }
			  else{
	               Date sDate  =  Constants.DMY.parse(startDate);
				   startCal.setTime(sDate);
			  }
			   startDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime()));
		       endDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime()));
		         
		         ts = getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime(), user.getId(),projectMemberUserId,employeeId);
		         currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
		         currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
			
	        
			if(currentStartDate !=null){
	      	   	ts.setStartDateStr(Constants.DF_DMY.format(currentStartDate));
	        }
	  	    if(currentEndDate !=null){
	  		   ts.setEndDateStr(Constants.DF_DMY.format(currentEndDate));
	  	    }
	  	    
	  	   ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
	       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
		   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
			   ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
		   }
		   		
	         sb.append("  <td><div class='time_appr_user'>");
	         sb.append("   <img  id='userImage' onerror='predefineProfileImage(this,"+ts.getProjectMembers().getEmployee().getUser().getId()+");' src='/displayUserImage/"+ts.getProjectMembers().getEmployee().getUser().getId()+"' alt='Profile Image' class='img-circle img-responsive'>");
	         sb.append("    <span><b>"+ts.getFullName()+"</b></span>");
	         sb.append(" <div></td>");
	         
	           sb.append("<td><div>");
	           Date leftNavStartDate = getLeftNavStartDateForApproverBasedOnCurrentStartDate(user.getId(), ts.getProjectMembers().getEmployee().getUser().getId(), currentStartDate);
	           if(leftNavStartDate != null && (leftNavStartDate.after(employeeStartDate) || employeeStartDate.equals(leftNavStartDate))){
		       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+format.format(leftNavStartDate)+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i>");
	     	   }
	     	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDateBuilder+" To "+endDateBuilder+"</span>");
	     	   Date rightNavStartDate = getRightNavStartDateForApproverBasedOnCurrentStartDate(user.getId(), ts.getProjectMembers().getEmployee().getUser().getId(), currentStartDate);
	           if(rightNavStartDate != null){
		     		Calendar rightCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
	    			rightCal.setLenient(false);
	    			rightCal.setTime(rightNavStartDate);
	    			Calendar rightNavEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(rightCal, "SUN");
				    if(rightNavEndDate.getTime().before(employeeEndDate) || employeeEndDate.equals(rightNavEndDate.getTime())){
		     			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+format.format(rightNavStartDate)+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i><abbr></abbr> ");	
		     	    }
	           }
	     	   sb.append("    </div>");
	     	   sb.append("    </td>");
	     	   
	     	 
	         sb.append("    <td><div> " );
	         sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
	         sb.append("    </div>");
	         sb.append("    </td>");
	         
	         sb.append("  <td><div>"+ts.getCombinedStatus()+"</div></td>");
	         
	         sb.append("    <td><div> " );
	             if(ts.getCombinedStatus().equalsIgnoreCase("NEW") || ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL") ){
	                 sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
	                 sb.append("<div id='rejectedPopup'></div>");
	             }
	             if(ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
	               /*  sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
	               sb.append(" <i class='glyphicon glyphicon-comment' onclick='rejectedMessage(\'rejectedMessage"+ts.getId()+"\');'>");
	               sb.append(" <strong id='rejectedMessage"+ts.getId()+"'><p>"+ts.getRejectedReason()+"</p></strong>");
	               sb.append(" </i>");
	               sb.append(" </div>");
	               */
	               sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
	               sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
	               sb.append(" <div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>"+ts.getRejectedReason()+"</div>");
	               sb.append(" </i>");
	               sb.append(" </div>");
	                
	                
	            }
	            if(ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
	          	  sb.append(" <div><h3>Approved</h3></div>");
	            }  
	            sb.append(" </div>");
	            sb.append("    </td>");
	            sb.append("<div class='clearfix'></div>");
			}
	        
	        return sb.toString();
		}
		
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is get LeftNavStartDate For Employee Based On CurrentStartDate
		 * 
		 * @param userId This is the first parameter to getLeftNavStartDateForEmployeeBasedOnCurrentStartDate method
		 * @param currentStartDate This is the second parameter to getLeftNavStartDateForEmployeeBasedOnCurrentStartDate method
		 *  
		 * @return Date 
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Date getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId, Date currentStartDate) {
			return timesheetSubmissionsDao.getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(userId, currentStartDate);
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is get get RightNavStartDate For Employee Based On Current StartDate
		 * 
		 * @param userId This is the first parameter to getRightNavStartDateForEmployeeBasedOnCurrentStartDate method
		 * @param currentStartDate This is the second parameter to getRightNavStartDateForEmployeeBasedOnCurrentStartDate method
		 *  
		 * @return Date 
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Date getRightNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId, Date currentStartDate) {
			return timesheetSubmissionsDao.getRightNavStartDateForEmployeeBasedOnCurrentStartDate(userId, currentStartDate);
		}

		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is get LeftNav StartDate For Approver Based On CurrentStartDate
		 * 
		 * @param approverUserId This is the first parameter to getLeftNavStartDateForApproverBasedOnCurrentStartDate method
		 *
		 * @param empUserId This is the second parameter to getLeftNavStartDateForApproverBasedOnCurrentStartDate method
		 *  
		 *  @param currentStartDate This is the third parameter to getLeftNavStartDateForApproverBasedOnCurrentStartDate method

		 * @return Date 
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Date getLeftNavStartDateForApproverBasedOnCurrentStartDate(Long approverUserId, Long empUserId,
				Date currentStartDate) {
			return timesheetSubmissionsDao.getLeftNavStartDateForApproverBasedOnCurrentStartDate(approverUserId, empUserId, currentStartDate);
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is get RightNav StartDate For Approver Based On Current StartDate
		 * 
		 * @param approverUserId This is the first parameter to getRightNavStartDateForApproverBasedOnCurrentStartDate method
		 *
		 * @param empUserId This is the second parameter to getRightNavStartDateForApproverBasedOnCurrentStartDate method
		 *  
		 *  @param currentStartDate This is the third parameter to getRightNavStartDateForApproverBasedOnCurrentStartDate method

		 * @return Date 
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Date getRightNavStartDateForApproverBasedOnCurrentStartDate(Long approverUserId, Long empUserId,
				Date currentStartDate) {
			return timesheetSubmissionsDao.getRightNavStartDateForApproverBasedOnCurrentStartDate(approverUserId, empUserId, currentStartDate);
		}
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  add TimesheetSubmissions
		 * 
		 * @param timesheetSubmissions This is the first parameter to addTimesheetSubmissions method
		 *

		 * @return Date 
		 * 
		 * @throws NullPointer
		 */
		@Override
		public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
			return timesheetSubmissionsDao.addTimesheetSubmissions(timesheetSubmissions);
		}
		
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get Min And Max TimesheetSubmission Dates For Employee
		 * 
		 * @param userId This is the first parameter to getMinAndMaxTimesheetSubmissionDatesForEmployee method
		 *

		 * @return  HashMap<String, Date>
		 * 
		 * @throws NullPointer
		 */
		@Override
		public HashMap<String, Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long userId) throws Exception {
			return timesheetSubmissionsDao.getMinAndMaxTimesheetSubmissionDatesForEmployee(userId);
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get update TimesheetSubmissions
		 * 
		 * @param timesheetSubmissions This is the first parameter to updateTimesheetSubmissions method
		 *

		 * @return  TimesheetSubmissions
		 * 
		 * @throws NullPointer
		 */
		@Override
		public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
			return timesheetSubmissionsDao.updateTimesheetSubmissions(timesheetSubmissions);
		}

		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is   get TimesheetSubmissions
		 * 
		 * @param id This is the first parameter to getTimesheetSubmissions method
		 *

		 * @return  TimesheetSubmissions
		 * 
		 * @throws NullPointer
		 */
		@Override
		public TimesheetSubmissions getTimesheetSubmissions(String id) throws Exception {
			return timesheetSubmissionsDao.getTimesheetSubmissions(id);
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is   delete TimesheetSubmissions
		 * 
		 * @param id This is the first parameter to deleteTimesheetSubmissions method
		 *

		 * @return  Boolean
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Boolean deleteTimesheetSubmissions(String id) throws Exception {
			Boolean status = timesheetSubmissionsDao.deleteTimesheetSubmissions(id);
			return status;
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is   get All Employee TimesheetSubmissions
		 * 
		 * @param startDate This is the first parameter to getAllEmployeeTimesheetSubmissions method
		 * 
		 * @param endDate This is the second parameter to getAllEmployeeTimesheetSubmissions method
		 * 
		 * @param userId This is the third parameter to getAllEmployeeTimesheetSubmissions method
		 *
		 * @return  List<TimesheetSubmissions>
		 * 
		 * @throws NullPointer
		 */
		@Override
		public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate ,Date endDate,Long userId) throws Exception {
			List<TimesheetSubmissions> list = timesheetSubmissionsDao.getAllEmployeeTimesheetSubmissions(startDate,endDate,userId);
			return list;
		}
		
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is   get All Approval Employee TimesheetSubmissions
		 * 
		 * @param startDate This is the first parameter to getAllEmployeeTimesheetSubmissions method
		 * 
		 * @param endDate This is the second parameter to getAllEmployeeTimesheetSubmissions method
		 * 
		 * @param userId This is the third parameter to getAllEmployeeTimesheetSubmissions method
		 *
		 * @return  TimesheetSubmissions
		 * 
		 * @throws NullPointer
		 */
		@Override
		public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissions(Date startDate ,Date endDate,Long userId) throws Exception {
			TimesheetSubmissions list = timesheetSubmissionsDao.getAllApprovalEmployeeTimesheetSubmissions(startDate,endDate,userId);
			return list;
		}
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is   get All Approval Employee TimesheetSubmissions By Project MemberId
		 * 
		 * @param startDate This is the first parameter to getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId method
		 * 
		 * @param endDate This is the second parameter to getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId method
		 * 
		 * @param userId This is the third parameter to getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId method
		 *
		 * @param employeeId This is the third parameter to getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId method
		 
		 * @param memberId This is the fourth parameter to getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId method

		 * @return  TimesheetSubmissions
		 * 
		 * @throws NullPointer
		 */
		@Override
		public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(Date startDate ,Date endDate,Long userId,Long empUserId,Long employeeId) throws Exception {
			TimesheetSubmissions list = timesheetSubmissionsDao.getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(startDate,endDate,userId,empUserId,employeeId);
			return list;
		}
		
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  save All TimesheetSubmissions
		 * 
		 * @param timesheetSubmissionsList This is the first parameter to saveAllTimesheetSubmissions method
		 * 

		 * @return  void
		 * 
		 * @throws NullPointer
		 */
		@Override
		public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
			 timesheetSubmissionsDao.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		}

		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get NextMonday By ProjectMemberId
		 * 
		 * @param projectMemberId This is the first parameter to getNextMondayByProjectMemberId method
		 * 

		 * @return  Date
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Date getNextMondayByProjectMemberId(Long projectMemberId) {
			return timesheetSubmissionsDao.getNextMondayByProjectMemberId(projectMemberId);
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get Approval TimesheetSubmissions
		 * 
		 * @param userId This is the first parameter to getApprovalTimesheetSubmissions method
		 * 

		 * @return  Collection<TimesheetSubmissions>
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getApprovalTimesheetSubmissions(userId);
			return list;
		}

		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get TimesheetSubmissions By AproverId
		 * 
		 * @param projectMemberId This is the first parameter to getApprovalTimesheetSubmissions method
		 * 
		 * @param userId This is the second parameter to getApprovalTimesheetSubmissions method
		 *  
		 * @param startDate This is the third parameter to getApprovalTimesheetSubmissions method
		 * 

		 * @return  Collection<TimesheetSubmissions>
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId, Long userId,Date startDate)
				throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimesheetSubmissionsByAproverId(projectMemberId,userId,startDate);
			return list;
		}
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get TimeSheetSubmissions Count Based On Status and User Id
		 * 
		 * @param userId This is the first parameter to getTimeSheetSubmissionsCountBasedOnStatus method
		 * 
		 * @param approvalStatus This is the second parameter to getTimeSheetSubmissionsCountBasedOnStatus method
		 *  
		 * 

		 * @return Integer
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus) throws Exception {
			
			Integer approvalStatusCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId,approvalStatus);
			return approvalStatusCount;
		}
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get Total Approved Or Rejected Hours By userId
		 * 
		 * @param userId This is the first parameter to getTimeSheetSubmissionsCountBasedOnStatus method
		 * 
		 * @param approvalStatus This is the second parameter to getTimeSheetSubmissionsCountBasedOnStatus method
		 *  
		 * 

		 * @return Float
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus) {
			return timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId,approvalStatus);
		}
		
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get Total Draft And Submitted Hours By userId
		 * 
		 * @param userId This is the first parameter to getTotalDraftAndSubmittedHoursByuserId method
		 * 
		 * @param submissionStatus This is the second parameter to getTotalDraftAndSubmittedHoursByuserId method
		 *  

		 * @return Float
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus) {
			return timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId,submissionStatus);
		}
		
		
		/**
		 * @author naresh bolisetty and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is  get TimeSheetSubmissions List By Status
		 * 
		 * @param userId This is the first parameter to getTimeSheetSubmissionsListByStatus method
		 * 
		 * @param approvalStatus This is the second parameter to getTimeSheetSubmissionsListByStatus method
		 *  

		 * @return Collection<TimesheetSubmissions>
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId, String approvalStatus)
				throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimeSheetSubmissionsListByStatus(userId,approvalStatus);
			return list;
		}


		
}
