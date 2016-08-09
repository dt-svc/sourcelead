package com.bis.sourcelead.web;


import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller 
public class TimesheetSubmissionsController {
	
	 
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
		
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
		         ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime(), user.getId(),memberId,employeeId);
                 
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
	                
			        ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime(), user.getId(),memberId,employeeId);
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
	                
			        ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getDateIncrementByincrement(startCal,7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,13).getTime(), user.getId(),memberId,employeeId);
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
               sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
               sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
               sb.append("<div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>"+ts.getRejectedReason()+"</div>");
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
	
	@RequestMapping(value = "/saveSubmissionApprovalStatus", method = RequestMethod.POST)
    public  String saveSubmissionApprovalStatus(HttpServletRequest request,
									    		@RequestParam(value="employeeId", required=false) Long employeeId,
												@RequestParam(value="startDate", required=false) String startDate,
												@RequestParam(value="endDate", required=false) String endDate,
												@RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
									             
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date sDate  =  Constants.DMY.parse(startDate);
        
        
        submissionApproverHierarchyService.updateApprovalStatusForApprover(employeeId, user.getId(), sDate, approvalStatus, rejectedReason);
        
        sendEmailToEmployeeTimesheetApprovalStatus(employeeId, user, startDate, endDate, approvalStatus, rejectedReason, loginOrgLocation, loginOrgCode);
        
      /*  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(employeeId, user.getId(), sDate);
        if(timesheetSubmissionsList!=null){
        	for(TimesheetSubmissions timesheetSubmissions :timesheetSubmissionsList){
	             if(approvalStatus.equalsIgnoreCase("REJECTED") && approvalStatus!=null){
	            	 	timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
	            	 	timesheetSubmissions.setSubmissionStatus(Submission_Status.REJECTED);
	            	 	timesheetSubmissions.setRejectedReason(rejectedReason);
	             }else if(approvalStatus.equalsIgnoreCase("APPROVED") && approvalStatus!=null){
	            	 timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 timesheetSubmissions.setSubmissionStatus(Submission_Status.APPROVED);
	             }
	             
	             timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
        	}  
        }*/
        return "success";
    }
	
	
	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
    public void downloadDocument(@RequestParam(value="submissionId", required=false) Long submissionId ,HttpServletResponse response) throws Exception {
		
		    TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment;filename="+timesheetSubmissions.getFileName());
		    
			InputStream inputStream = new ByteArrayInputStream(timesheetSubmissions.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}  
		 

	
	@RequestMapping(value = "/timesheetApproval")
	public ModelAndView timesheetApproval(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(com.bis.sourcelead.util.Constants.TimeSheetApproval);
			
			request.getSession().setAttribute("sessionData",sessionData);
			
			Collection<TimesheetSubmissions> timesheetSubmissionsList =  timesheetSubmissionsService.getApprovalTimesheetSubmissions(user.getId());
			model.addObject("sourcelead_timesheetSubmissionsList",timesheetSubmissionsList);
			
			
			List<TimesheetSubmissions> thslist = timesheetSubmissionsService.getSubmissionApproverHierarchyForEmployeeList(user.getId());
			for(TimesheetSubmissions ths : thslist ){
				ths.setFullName(ths.getProjectMembers().getEmployee().getUser().getFirstName() +""+ths.getProjectMembers().getEmployee().getUser().getLastName());
				ths.setEmployeeId(ths.getProjectMembers().getEmployee().getId().toString());
			}
			model.addObject("thslist",thslist);

			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheetApproval");
	        return model;
	}
	
	 public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
 
}
