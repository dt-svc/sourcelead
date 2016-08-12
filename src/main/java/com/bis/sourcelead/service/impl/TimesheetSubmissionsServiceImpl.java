package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	@Override
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		return timesheetSubmissionsDao.addTimesheetSubmissions(timesheetSubmissions);
	}
	
	@Override
	public HashMap<String, Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getMinAndMaxTimesheetSubmissionDatesForEmployee(userId);
	}

	@Override
	public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.updateTimesheetSubmissions(timesheetSubmissions);
	}

	@Override
	public TimesheetSubmissions getTimesheetSubmissions(String id) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getTimesheetSubmissions(id);
	}

	@Override
	public Boolean deleteTimesheetSubmissions(String id) throws Exception {
		Boolean status = timesheetSubmissionsDao.deleteTimesheetSubmissions(id);
		return status;
		// TODO Auto-generated method stub
	}

	@Override
	public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate ,Date endDate,Long userId) throws Exception {
		List<TimesheetSubmissions> list = timesheetSubmissionsDao.getAllEmployeeTimesheetSubmissions(startDate,endDate,userId);
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissions(Date startDate ,Date endDate,Long userId) throws Exception {
		TimesheetSubmissions list = timesheetSubmissionsDao.getAllApprovalEmployeeTimesheetSubmissions(startDate,endDate,userId);
		// TODO Auto-generated method stub
		return list;
	}
	
	
	@Override
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate ,Date endDate,Long userId,Long memberId,Long employeeId) throws Exception {
		TimesheetSubmissions list = timesheetSubmissionsDao.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(startDate,endDate,userId,memberId,employeeId);
		// TODO Auto-generated method stub
		return list;
	}
	
	
	@Override
	public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
		 timesheetSubmissionsDao.saveAllTimesheetSubmissions(timesheetSubmissionsList);
	}

	@Override
	public Date getNextMondayByProjectMemberId(Long pojectMemberId) {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getNextMondayByProjectMemberId(pojectMemberId);
	}

	@Override
	public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getApprovalTimesheetSubmissions(userId);
		return list;
	}

	@Override
	public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId, Long userId,Date startDate)
			throws Exception {
		// TODO Auto-generated method stub
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimesheetSubmissionsByAproverId(projectMemberId,userId,startDate);
		return list;
	}
	
	@Override
	public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus) throws Exception {
		
		Integer approvalStatusCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId,approvalStatus);
		return approvalStatusCount;
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus) {
		return timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId,approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus) {
		return timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId,submissionStatus);
	}
	
	@Override
	public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId, String approvalStatus)
			throws Exception {
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimeSheetSubmissionsListByStatus(userId,approvalStatus);
		return list;
	}

	
	
	// Sammeta David: this  controller method is for getting TimesheetSubmission Counts based on Status.
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

			// Sammeta David: this  controller method is for getting TimesheetSubmission Hours Counts based on login User Id.
			@Override
			public TimeSheetSubmissionCount getTimeSheetSubmissionsHoursCountByUserId(Long userId) {
				// TODO Auto-generated method stub
				
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
			
			

	@Override
		public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId,
				String clientId, String values) throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getProjectsBasedOnUserIdAndStatusAndClientForFilters(userId,clientId,values);
			return list;
		}
	
	@Override
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId, String approvalStatus,Long orgLocationId) throws Exception {
		Integer approvalStatusCount = timesheetSubmissionsDao.getUsersTimeSheetSubmissionsCountBasedOnStatus(userId,approvalStatus,orgLocationId);
		return approvalStatusCount;
	}

	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		
		 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		 
		Collection<SubmissionApproverHierarchy> approvedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "APPROVED");
		timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeList.size()));
		 
		Collection<SubmissionApproverHierarchy> rejectedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "REJECTED");
		timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeList.size()));
		
		Collection<SubmissionApproverHierarchy> submittedForApprovalindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"SUBMITTEDFORAPPROVAL");
		timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeList.size()));
		
		Collection<SubmissionApproverHierarchy> newindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"NEW");
		
		Collection<SubmissionApproverHierarchy> draftindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"DRAFT");
		Integer savedTimesheetSubmissionCount = newindividualEmployeeList.size() +draftindividualEmployeeList.size();
		
		timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
		
		return timeSheetSubmissionCount;
		 
	}

	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long userId,
			Long orgLocationId) throws Exception {
		
		 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		
		 
		    Collection<SubmissionApproverHierarchy> approvedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,  "APPROVED");
			timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeList.size()));
			 
			Collection<SubmissionApproverHierarchy> rejectedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, "REJECTED");
			timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeList.size()));
			
			Collection<SubmissionApproverHierarchy> submittedForApprovalindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"SUBMITTEDFORAPPROVAL");
			timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeList.size()));
			
			Collection<SubmissionApproverHierarchy> newindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"NEW");
			
			Collection<SubmissionApproverHierarchy> draftindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"DRAFT");
			Integer savedTimesheetSubmissionCount = newindividualEmployeeList.size() +draftindividualEmployeeList.size();
			
			timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
			
		return timeSheetSubmissionCount;
	}
	
	  @Override
		public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(Long userId,
				String employeeId) throws Exception {
			Collection<TimesheetSubmissions> thslist = timesheetSubmissionsDao.getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(userId,employeeId);
			return thslist;
		}
		
		
		@Override
		public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId)
				throws Exception {
			// TODO Auto-generated method stubgetSubmissionApproverHierarchyForEmployeeList
			List<TimesheetSubmissions> list = timesheetSubmissionsDao.getSubmissionApproverHierarchyForEmployeeList(approverUserId);
			return list;
		}

		@Override
		public TimesheetSubmissions getTimesheetSubmissionsByMemberIdAndStartDate(Long memberId, Date startDate)
				throws Exception {
			// TODO Auto-generated method stub
			return timesheetSubmissionsDao.getTimesheetSubmissionsByMemberIdAndStartDate(memberId, startDate);
		}

		@Override
		public List<TimesheetSubmissions> getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(
				Long memberId, Date terminatedDate) throws Exception {
			// TODO Auto-generated method stub
			return timesheetSubmissionsDao.getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(memberId, terminatedDate);
		}

		@Override
		public String getArrovalSubmissions(String employeeId,User user) throws Exception {
			String resultJson = null;
			Collection<TimesheetSubmissions> timesheetSubmissionsList  = null;
	        if(!employeeId.isEmpty()){
	        	
	        	 timesheetSubmissionsList =   getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(user.getId(),employeeId);
	        	 
	        }else{
	        	
	        	  timesheetSubmissionsList =  getApprovalTimesheetSubmissions(user.getId());
	        }
	      
	        
	        ArrayList<TimesheetSubmissions> timesheetSubmissionsArrList = new ArrayList<TimesheetSubmissions>();
	        
	        StringBuilder sb = new StringBuilder();
			/*sb.append(" <table>");
			sb.append(" <tbody>");
			sb.append(" <thead>");
			sb.append("     <tr>");
			sb.append("         <th>Employee</th>");
			sb.append("         <th>Frequency</th>");
			sb.append("         <th>Total hours</th>");
			sb.append("         <th>Stage</th>");
			sb.append("         <th>Action</th>");
			sb.append("     </tr>");
			sb.append(" </thead>");*/
			
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				
	        sb.append("	<div>");
			sb.append("	<ul class='table_head'>");
			sb.append("    <li>Employee</li> ");
			sb.append("    <li>Frequency</li>            ");
			sb.append("    <li>Total hours</li>          ");
			sb.append("    <li>Stage</li>                ");
			sb.append("    <li>Action</li>               ");
			sb.append("    <div class='clearfix'></div> ");
			sb.append("</ul>");
	       	sb.append(" <ul class='table_details'> ");
			 
	        String pattern = "dd-MM-yyyy";
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
	        Date currentStartDate = null;
	        Date currentEndDate = null;
	        
	        
	        for(TimesheetSubmissions ts : timesheetSubmissionsList){
	        	 
	        	
	        	   HashMap<String, Date> submissionDateRange = ts.getDateList();
	        	   Date employeeStartDate = submissionDateRange.get("STARTDATE");
	        	   Date employeeEndDate = submissionDateRange.get("ENDDATE");
	        	   
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
	        	   
	               				sb.append("<div id='row"+ts.getProjectMembers().getEmployee().getUser().getId()+"'>");
			                    sb.append("  <li><div class='time_appr_user'>");
			                    sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='/displayUserImage/"+ts.getUserId()+"' alt='Profile Image' class='img-circle img-responsive'>");
			                    sb.append("    <span><b>"+ts.getFullName()+"</b></span></div>");
			                    sb.append("    </li>");
			                    
			                    sb.append("<li><div>");
			     		       if(employeeStartDate.before(currentStartDate)){
			     		       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i>");
			     	       	   }
			     	       	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDate+" To "+endDate+"</span>");
			     	       		
			     	       	   if(currentEndDate.before(employeeEndDate)){
			     	       			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i><abbr></abbr> ");	
			     	       	   }
			     	       	   sb.append("    </div>");
			     	       	   sb.append("    </li>");
			     	       	   
			                    sb.append("    <li><div> " );
			                    sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
			                    sb.append("    </div>");
			                    sb.append("    </li>");
			                    
			                    sb.append("  <li><div>"+ts.getCombinedStatus()+"</div></li>");
			                    
			                    sb.append("    <li><div> " );
			     	               if(ts.getCombinedStatus().equalsIgnoreCase("NEW") || ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL") ){
			     	                   sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
			     	                   sb.append("<div id='rejectedPopup'></div>");
			     	               }
			     	               if(ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
			     	            	  sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
			     	                  sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
			     	                  sb.append(" <div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>"+ts.getRejectedReason()+"</div>");
			     	                  sb.append(" </i>");
			     	                  sb.append(" </div>");
			     	              }
			     	              if(ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
			     	            	  sb.append(" <div><h3>Approved</h3></div>");
			     	              }  
			                    sb.append("    </div>");
			                    sb.append("    </li>");
			                    sb.append(" <div class='clearfix'></div>");
			                    sb.append("</div>");
	       	
	        	   /*sb.append("  <tr id='row"+ts.getId()+"'>");
	               sb.append("  <td><div class='time_appr_user'>");
	               sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='/displayUserImage/"+ts.getUserId()+"' alt='Profile Image' class='img-circle img-responsive'>");
	               sb.append("    <span><b>"+ts.getFullName()+"</b></span></div></div>");
	               sb.append("    </td>");
	               
	               sb.append("<td><div>");
			       if(employeeStartDate.before(currentStartDate)){
			       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getId()+"\");'></i>");
		       	   }
		       	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDate+" To "+endDate+"</span>");
		       		
		       	   if(currentEndDate.before(employeeEndDate)){
		       			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getId()+"\");'></i><abbr></abbr> ");	
		       	   }
		       	   sb.append("    </div>");
		       	   sb.append("    </td>");
		       	   
	               sb.append("    <td><div> " );
	               sb.append(" <a onclick='myTimesheet(\'"+ts.getEmployeeId()+"\',\'"+ts.getStartDateStr()+"\',\'"+ts.getEndDateStr()+"\');'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a></div></td>");
	               sb.append("    </div>");
	               sb.append("    </td>");
	               
	               sb.append("  <td><div>"+ts.getCombinedStatus()+"</div></td>");
	               
	               sb.append("    <td><div> " );
		               if(ts.getCombinedStatus().equalsIgnoreCase("NEW") || ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL") ){
		                   sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\'"+ts.getEmployeeId()+"\',\'"+ts.getStartDateStr()+"\',\'"+ts.getEndDateStr()+"\');'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\'"+ts.getId()+"\',\'"+ts.getEmployeeId()+"\',\'"+ts.getStartDateStr()+"\',\'"+ts.getEndDateStr()+"\');'></i></div>");
		                   sb.append("<div id='rejectedPopup'></div>");
		               }
		               if(ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
		                 sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
		                 sb.append(" <i class='glyphicon glyphicon-comment' onclick='rejectedMessage(\'rejectedMessage"+ts.getId()+"\');'>");
		                 sb.append(" <strong id='rejectedMessage"+ts.getId()+"'><p>"+ts.getRejectedReason()+"</p></strong>");
		                 sb.append(" </i>");
		                 sb.append(" </div>");
		              }
		              if(ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
		            	  sb.append(" <div><h3>Approved</h3></div>");
		              }  
	               sb.append("    </div>");
	               sb.append("    </td>");
	               sb.append("</tr>");*/
	             
	         }
	         sb.append("	<div>");
			}
	        /* sb.append(" </tbody>");
	         sb.append(" </table>");
	          */
	       return sb.toString();
			
		}

		@Override
		public String dynamicTimesheet(User user,String navigation,String startDate,Long memberId,Long projectMemberUserId, Long employeeId) throws Exception {
			 
			HashMap<String, Date> submissionDateRange =	 getMinAndMaxTimesheetSubmissionDatesForEmployee(projectMemberUserId);
			Date employeeStartDate = submissionDateRange.get("STARTDATE");
			Date employeeEndDate = submissionDateRange.get("ENDDATE");
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
			 if(startDate == null || startDate.equalsIgnoreCase("undefined") || startDate.isEmpty() ){  
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
				      
		        	  
			         startDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime()));
			         endDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime()));
			         ts = getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime(), user.getId(),memberId,employeeId);
	                 
	                 currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
	                 currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
	        }
	        else{
	                
	                 if(navigation.equalsIgnoreCase("left")){
	                     
	                     
	                     Date sDate  =  Constants.DMY.parse(startDate);
		                 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						 startCal.setLenient(false);
						 startCal.setTime(sDate);
						 
							                
		                startDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime()));
				        endDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime()));
		                
				        ts =  getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime(), user.getId(),memberId,employeeId);
		                currentStartDate = CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime();
		                currentEndDate = CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime();
	                 }
	                 else{ 
	                 
		                 Date sDate  =  Constants.DMY.parse(startDate);
		                 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						 startCal.setLenient(false);
						 startCal.setTime(sDate);
						 
							                
		                startDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,7).getTime()));
				        endDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,13).getTime()));
		                
				        ts =  getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getDateIncrementByincrement(startCal,7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,13).getTime(), user.getId(),memberId,employeeId);
		                currentStartDate = CalendarUtil.getDateIncrementByincrement(startCal,7).getTime();
		                currentEndDate = CalendarUtil.getDateIncrementByincrement(startCal,13).getTime();
	                 }
	        }
	        
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
		   		
	         sb.append("  <li><div class='time_appr_user'>");
	         sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='/displayUserImage/"+ts.getUserId()+"' alt='Profile Image' class='img-circle img-responsive'>");
	         sb.append("    <span><b>"+ts.getFullName()+"</b></span>");
	         sb.append(" <div></li>");
	         
	         sb.append("<li><div>");
		       if(employeeStartDate.before(currentStartDate)){
		       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDateBuilder+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i>");
	     	   }
	     	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDateBuilder+" To "+endDateBuilder+"</span>");
	     		
	     	   if(currentEndDate.before(employeeEndDate)){
	     			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDateBuilder+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i><abbr></abbr> ");	
	     	   }
	     	   sb.append("    </div>");
	     	   sb.append("    </li>");
	     	   
	     	 
	         sb.append("    <li><div> " );
	         sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
	         sb.append("    </div>");
	         sb.append("    </li>");
	         
	         sb.append("  <li><div>"+ts.getCombinedStatus()+"</div></li>");
	         
	         sb.append("    <li><div> " );
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
	            sb.append("    </li>");
	            sb.append("<div class='clearfix'></div>");
			}
	        
	        return sb.toString();
		}
}
