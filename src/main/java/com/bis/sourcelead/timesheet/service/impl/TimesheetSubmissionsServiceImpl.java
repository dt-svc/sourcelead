package com.bis.sourcelead.timesheet.service.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.timesheet.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class TimesheetSubmissionsServiceImpl implements TimesheetSubmissionsService {

	@Autowired
	TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
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

	/* @Override
	 public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserId(Long userId) throws Exception {
		
			 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
			 
			            Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(userId, new Long(3), "DRAFT");
		        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "DRAFT");
		        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
		        timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(draftTimesheetsSubmissionsCount);
	
		        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(userId,new Long(3), "SUBMITTED");
		        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "SUBMITTED");
		        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
		        timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(submittedTimesheetsSubmissionsCount);
				
			    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(userId,new Long(3), "APPROVED");
			    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "APPROVED");
		        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
		        timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(approvalTimesheetsSubmissionsCount);
				
				Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(userId,new Long(3), "REJECTED");
			    Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "REJECTED");
				    Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
				    timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(rejectedTimesheetsSubmissionsCount);
		
		        return timeSheetSubmissionCount;
	 }*/

	 
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
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(Long masterCandidateId, Long orgLocationId,Long orgId, String countType)
			throws Exception {
		
	    TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		 
	    if(countType.equalsIgnoreCase("APPROVED")) {
	    Integer approvedindividualEmployeeCount = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "APPROVED",orgLocationId);
		timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeCount));
	    }
	    if(countType.equalsIgnoreCase("REJECTED")) {
		Integer rejectedindividualEmployeeCount = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "REJECTED",orgLocationId);
		timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeCount));
	    }
		 if(countType.equalsIgnoreCase("SUBMITTED")) {
		 Integer submittedForApprovalindividualCount = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "SUBMITTED",orgLocationId,orgId);
		timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualCount));
		 }
		 if(countType.equalsIgnoreCase("DRAFT")) {
		Integer draftindividualEmployeeCount = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "DRAFT",orgLocationId,orgId);
		Integer savedTimesheetSubmissionCount = draftindividualEmployeeCount;
		timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
		 }
		 if(countType.equalsIgnoreCase("All")) {
			 Integer approvedindividualEmployeeCount = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "APPROVED",orgLocationId);
				timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeCount));
			 Integer rejectedindividualEmployeeCount = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "REJECTED",orgLocationId);
				timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeCount));	
		     Integer submittedForApprovalindividualCount = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "SUBMITTED",orgLocationId,orgId);
				timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualCount));
			 Integer draftindividualEmployeeCount = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLoginCount(masterCandidateId, "DRAFT",orgLocationId,orgId);
				Integer savedTimesheetSubmissionCount = draftindividualEmployeeCount;
				timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
				 }
		 

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
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long userId,Long orgLocationId,Long orgId,String countType,String isGlobalUser,Boolean isAdmin) throws Exception {
		
		    TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		   if(countType.equalsIgnoreCase("APPROVED")) {
			Integer approvedindividualEmployeeCount = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId,  "APPROVED",isGlobalUser);
			timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeCount));
		   }
		   if(countType.equalsIgnoreCase("REJECTED")) {
			Integer rejectedindividualEmployeeCount = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId, "REJECTED",isGlobalUser);
			timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeCount));
		   }
		   if(countType.equalsIgnoreCase("SUBMITTED")) {
			Integer submittedForApprovalindividualEmployeeCount   = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId,"SUBMITTED",isGlobalUser);
			timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeCount));
		   }
		   if(countType.equalsIgnoreCase("DRAFT")) {
			Integer draftindividualEmployeeCount   = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId,"DRAFT",isGlobalUser);
			Integer savedTimesheetSubmissionCount = draftindividualEmployeeCount;
			timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
		   }
		   if(countType.equalsIgnoreCase("All")) {
			   Integer approvedindividualEmployeeCount = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId,  "APPROVED",isGlobalUser);
				timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeCount));
			   Integer rejectedindividualEmployeeCount = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId, "REJECTED",isGlobalUser);
				timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeCount));
			   Integer submittedForApprovalindividualEmployeeCount   = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId,"SUBMITTED",isGlobalUser);
				timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeCount));
			   Integer draftindividualEmployeeCount   = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(userId,isAdmin, orgLocationId,orgId,"DRAFT",isGlobalUser);
				Integer savedTimesheetSubmissionCount = draftindividualEmployeeCount;
				timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));	
		   }
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
		public String getArrovalSubmissions(String employeeId,User user,Long locationsId) throws Exception {
			
			/**
		     * Get all employeeList(who is member in the project,in which logged in user is approver or logged in user is admin)  with date range
			*/
			Collection<TimesheetSubmissions> timesheetSubmissionsList =  getApprovalTimesheetSubmissions(user.getId(),locationsId);
			OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
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
			 
	        //String pattern = "dd-MM-yyyy";
	        		 //DateFormat  format = LoginSessionController.DATEFORMAT;
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
	 		      		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
	 		      		Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
	 		      		
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
	        	   
	        	   
	        	   
	        	   
	        	   Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON",orgLocations.getDateFormat());
				   Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				   startCal.setLenient(false);
	        	   
	        	   if( (employeeStartDate !=null && currentWeekStartDate.equals(employeeStartDate) ) || ( (employeeEndDate !=null && currentWeekStartDate.before(employeeEndDate)) ) &&  (employeeStartDate !=null && currentWeekStartDate.after(employeeStartDate)) ){
				    	  startCal.setTime(currentWeekStartDate);
			       }
			       else if(employeeEndDate != null && employeeEndDate.before(currentWeekStartDate)){
			    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    	  endCal.setLenient(false);
			    	  endCal.setTime(employeeEndDate);
			    	  startCal.setTime(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(endCal,"MON",orgLocations.getDateFormat()).getTime());
			       }
			       else if(employeeStartDate != null && employeeStartDate.after(currentWeekStartDate)){
			    	  startCal.setTime(employeeStartDate);
			       }
	        	   
	        	   String startDate =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON",orgLocations.getDateFormat()).getTime());
			       String endDate   =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN",orgLocations.getDateFormat()).getTime());
	        		 		
			       ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
			       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
	        	   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
	        		   StringBuilder stringBuilder = new StringBuilder();
	        		   ts.setFullName(stringBuilder.append(ts.getProjectMembers().getEmployee().getUser().getFirstName()).append(" ").append(ts.getProjectMembers().getEmployee().getUser().getLastName()).toString());
	        	   }
	        	   
	        	   currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON",orgLocations.getDateFormat()).getTime();
	               currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN",orgLocations.getDateFormat()).getTime();
	               
	               if(currentStartDate !=null){
	            	   	ts.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentStartDate));
	               }
	        	   if(currentEndDate !=null){
	        		   ts.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentEndDate));
	        	   }
	        	   
	        	   
	        	  String rows = dynamicTimesheet(user, "left", startDate, ts.getProjectMembers().getId(), ts.getProjectMembers().getEmployee().getUser().getId(), ts.getProjectMembers().getEmployee().getId(),locationsId);
	        	  sb.append("<tr id='row").append(ts.getProjectMembers().getEmployee().getUser().getId()).append("'>");
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
		public String dynamicTimesheet(User user,String navigation,String startDate,Long memberId,Long projectMemberUserId, Long employeeId,Long locationsId) throws Exception {
			
			Boolean isViewMemberTimeSheet = commonUtil.hasRole("ROLE_VIEW_MEMBER_TIMESHEETS");
			HashMap<String, Date> submissionDateRange =	 timesheetSubmissionsDao.getMinAndMaxTimesheetSubmissionDatesForApprover(user.getId(),projectMemberUserId,locationsId);
			Date employeeStartDate = submissionDateRange.get("STARTDATE");
			Date employeeEndDate = submissionDateRange.get("ENDDATE");
			OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
			String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
      	   
      	    if(StringUtils.isNotEmpty(loginUserRole) && (loginUserRole.equalsIgnoreCase("ROLE_APPROVER") || loginUserRole.equalsIgnoreCase("ROLE_ADMIN"))){
      		   /**
	      		   *calculating prior 6th week mondaydate and post 6th week sunday day for employee
	      		   *
	      	       *
	      	       */
		      		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		      		cal.setLenient(false);
		      		cal.setTime(new Date());
		      		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		      		Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
		      		
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
		    l1 :{
			if(employeeStartDate != null && employeeStartDate != null){
			
			  StringBuilder sb1 = new StringBuilder();
			 
			  StringBuilder startDateBuilder = new StringBuilder();
			  StringBuilder endDateBuilder = new StringBuilder();
			 
			  //String pattern = "dd-MM-yyyy";
	          //DateFormat  format = LoginSessionController.DATEFORMAT;
	          Date currentStartDate = null;
	          Date currentEndDate = null;
	          Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			   startCal.setLenient(false);
			   if(startDate == null || startDate.equalsIgnoreCase("undefined") || startDate.isEmpty()){  
				      Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON",orgLocations.getDateFormat());
				      if(currentWeekStartDate.equals(employeeStartDate)|| (currentWeekStartDate.before(employeeEndDate) && currentWeekStartDate.after(employeeStartDate))){
				    	  startCal.setTime(currentWeekStartDate);
				      }
				      else if(employeeEndDate.before(currentWeekStartDate)){
				    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				    	  endCal.setLenient(false);
				    	  endCal.setTime(employeeEndDate);
				    	  startCal.setTime(CalendarUtil.getPreviousWeekDayByCalendar(endCal,"MON", orgLocations.getDateFormat()).getTime());
				      }
				      else if(employeeStartDate.after(currentWeekStartDate)){
				    	  startCal.setTime(employeeStartDate);
				      }
	          }
			  else{
	               Date sDate  =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate);
	               if(sDate != null && employeeEndDate.before(sDate)){
	            	      Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				    	  endCal.setLenient(false);
				    	  endCal.setTime(employeeEndDate);
				    	  startCal.setTime(CalendarUtil.getPreviousWeekDayByCalendar(endCal,"MON", orgLocations.getDateFormat()).getTime());
	               }
	               else{
	            	   startCal.setTime(sDate);
	               }
				  
			  }
			   startDateBuilder.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON",orgLocations.getDateFormat()).getTime()));
		       endDateBuilder.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN",orgLocations.getDateFormat()).getTime()));
		         
		         ts = getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON",orgLocations.getDateFormat()).getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN",orgLocations.getDateFormat()).getTime(), user.getId(),projectMemberUserId,employeeId);
		         if(ts != null){
		        	 if(!StringUtils.isNotEmpty(ts.getCombinedStatus())){
			        	 break l1;
			         }
		         }
		         
		        
		         currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON",orgLocations.getDateFormat()).getTime();
		         currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN",orgLocations.getDateFormat()).getTime();
			
	        
			if(currentStartDate !=null){
	      	   	ts.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentStartDate));
	        }
	  	    if(currentEndDate !=null){
	  		   ts.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),currentEndDate));
	  	    }
	  	   ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
	       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
		   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
			   StringBuilder stringBuilder = new StringBuilder();	
			   ts.setFullName(stringBuilder.append(ts.getProjectMembers().getEmployee().getUser().getFirstName()).append(" ").append(ts.getProjectMembers().getEmployee().getUser().getLastName()).toString());
		   }
		   		
	         sb.append("  <td><div class='time_appr_user tbldata-dotdot-me'>");
	         sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='").append(request.getContextPath()).append("/displayUserImage/").append(ts.getProjectMembers().getEmployee().getUser().getId()).append("' alt='Profile Image' class='img-circle img-responsive'>");
	         sb.append("    <span><b>").append(ts.getFullName()).append("</b></span>");
	         sb.append(" <div></td>");
	         
	           sb.append("<td><div id='datefrequency"+ts.getUserId()+ts.getStartDateStr().replaceAll("-", "")+"'>");
	           Date leftNavStartDate = getLeftNavStartDateForApproverBasedOnCurrentStartDate(user.getId(), ts.getProjectMembers().getEmployee().getUser().getId(), currentStartDate);
	           if(leftNavStartDate != null && (leftNavStartDate.after(employeeStartDate) || employeeStartDate.equals(leftNavStartDate))){
		       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),leftNavStartDate)+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i>");
	     	   }
	           else{
	        	   sb.append("       	<i class='glyphicon glyphicon-none'></i>");
	           }
	     	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>").append(startDateBuilder).append(" To ").append(endDateBuilder).append("</span>");
	     	   Date rightNavStartDate = getRightNavStartDateForApproverBasedOnCurrentStartDate(user.getId(), ts.getProjectMembers().getEmployee().getUser().getId(), currentStartDate);
	           if(rightNavStartDate != null){
		     		Calendar rightCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
	    			rightCal.setLenient(false);
	    			rightCal.setTime(rightNavStartDate);
	    			Calendar rightNavEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(rightCal, "SUN",orgLocations.getDateFormat());
				    if(rightNavEndDate.getTime().before(employeeEndDate) || employeeEndDate.equals(rightNavEndDate.getTime())){
		     			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rightNavStartDate)+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i><abbr></abbr> ");	
		     	    }
	           }
	           else{
	        	   sb.append("       	<i class='glyphicon glyphicon-none'></i>");
	           }
	     	   sb.append("    </div>");
	     	   sb.append("    </td>");
	     	   
	     	 
	         sb.append("    <td><div> " );
	         if(isViewMemberTimeSheet){
	        	 sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'  id='timesheetPopup"+ts.getId()+"'> ").append(ts.getCombinedTotalHours()).append("</a>");
	         }else{
	        	 sb.append(ts.getCombinedTotalHours());
	         }
	         sb.append("    </div>");
	         sb.append("    </td>");
	         
	         sb.append("  <td><div id='status"+ts.getUserId()+"'>").append(ts.getCombinedStatus()).append("</div></td>");
	         
	         sb.append("    <td><div id='action"+ts.getUserId()+"'> " );
	             if( (ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("NEW")) || ( ts.getCombinedStatus() != null && ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL")) ){
	                 sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getUserId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getUserId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
	                 sb.append("<div id='rejectedPopup'></div>");
	             }
	             if(ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
	               sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
	               sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
	               sb.append(" <div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>").append(ts.getRejectedReason()).append("</div>");
	               sb.append(" </i>");
	               sb.append(" </div>");
	            }
	            if(ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
	          	  sb.append(" <div><h3>Approved</h3></div>");
	            }  
	            sb.append(" </div>");
	            sb.append("    </td>");
	            sb.append("<div class='clearfix'></div>");
		    	}
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
			timesheetSubmissions.setUpdatedDate(commonUtil.currentDate());
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
		public HashMap<String, Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long userId,Long locationsId) throws Exception {
			return timesheetSubmissionsDao.getMinAndMaxTimesheetSubmissionDatesForEmployee(userId,locationsId);
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
		public List<TimesheetSubmissions> saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
			 return timesheetSubmissionsDao.saveAllTimesheetSubmissions(timesheetSubmissionsList);
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
		 * @throws Exception 
		 * 
		 * @throws NullPointer
		 */
		@Override
		public Date getNextMondayByProjectMemberId(Long projectMemberId) throws Exception {
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
		public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId,Long locationsId) throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getApprovalTimesheetSubmissions(userId,locationsId);
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
		public List<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long empId, Long userId,Date startDate)
				throws Exception {
			List<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimesheetSubmissionsByAproverId(empId,userId,startDate);
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

/**
 * Read week frequency for time sheet submissions those who are not send
 * 
 */
		@Override
		public HashSet<TimesheetSubmissions> getWeekFrequency(Long projectId,Long locationsId,Long userId) throws Exception {
			return timesheetSubmissionsDao.getWeekFrequency(projectId,locationsId,userId);
		}


	@Override
	public HashSet<TimesheetSubmissions> getWeekFrequencyByProjMemId(Long projectId) throws Exception {
		return timesheetSubmissionsDao.getWeekFrequencyByProjMemId(projectId);
	}

	@Override
	public List<TimesheetSubmissions> getTimeSheetSubmissionByProjectMemberId(Long pojectMemberId) throws Exception {
		return timesheetSubmissionsDao.getTimeSheetSubmissionByProjectMemberId(pojectMemberId);
	}
	
	@Override
	public List<TimesheetSubmissions> getTimesheetSubmissionsByProjectIdAndMasterCandidateId(Long pojectId,Long masterCandidateId) throws Exception {
		return timesheetSubmissionsDao.getTimesheetSubmissionsByProjectIdAndMasterCandidateId(pojectId,masterCandidateId);
	}


	/*@Override
	public TimesheetSubmissions getAllEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate, Date endDate,
			Long projectMemberId) throws Exception {
		return timesheetSubmissionsDao.getAllEmployeeTimesheetSubmissionsByProjectMemberId(startDate, endDate, projectMemberId);
	}*/
	
	@Override
	public TimesheetSubmissions getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(Date startDate, Date endDate,
			Long projectId,Long masterCandidateId) throws Exception {
		return timesheetSubmissionsDao.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate, endDate, projectId,masterCandidateId);
	}
	
	
	@Override
	public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissionsByProjectMemberIdForUpdation(Date startDate, Date endDate,
			Long projectMemberId) throws Exception {
		return timesheetSubmissionsDao.getAllEmployeeTimesheetSubmissionsByProjectMemberIdForUpdation(startDate, endDate, projectMemberId);
	}

	
	@Override
	public List<TimesheetSubmissions> getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(Date startDate,Date endDate,  Long  projectId,Long masterCandidateId) throws Exception {
		return timesheetSubmissionsDao.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(startDate, endDate, projectId, masterCandidateId);
	}


	@Override
	public TimesheetSubmissions getSubmissionDetailsByProjectIdMasterCandiDateIdStartDateandEndDate(Long projectId,Long masterCanidateId, Date frequencyStartDate, Date frequencyEndDate) {
		return timesheetSubmissionsDao.getSubmissionDetailsByProjectIdMasterCandiDateIdStartDateandEndDate(projectId,masterCanidateId,frequencyStartDate,frequencyEndDate);
	}


	@Override
	public String getWeekCommentByCurrentFrequency(Date startDate, Date endDate, Long projectId, Long masterCandidateId)
			throws Exception {
		return timesheetSubmissionsDao.getWeekCommentByCurrentFrequency(startDate, endDate, projectId, masterCandidateId);
	}


	@Override
	public void updateWeekCommentForAllSubmissions(String weekComment,Date startDate, Date endDate, Long projectId, Long masterCandidateId)
			throws Exception {
		timesheetSubmissionsDao.updateWeekCommentForAllSubmissions(weekComment,startDate, endDate, projectId, masterCandidateId);
		
	}
	
	

		
}
