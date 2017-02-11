package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ExportTimesheetEntriesImpl implements ExportTimesheetEntries{
	private static final Logger logger = LoggerFactory.getLogger(ExportTimesheetEntriesImpl.class);
	
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	TimesheetSubmissionsDocumentsService  timesheetSubmissionsDocumentsService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Override
	public File exportTimesheets(User user, String navigation, String startDate,OrgLocations orgLocations) throws Exception {

		File file = null;
		Workbook wb = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		StringBuilder fName=new StringBuilder();
		Sheet sheet = wb.createSheet(fName.append(user.getFirstName()).append("_").append(dateFormat.format(new Date())).toString());
		Row writeRow = null;
		
		HashMap<String, Date> submissionDateRange =	timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(user.getId(),orgLocations.getId());
		Date employeeStartDate = submissionDateRange.get("STARTDATE");
		Date employeeEndDate = submissionDateRange.get("ENDDATE");
		
		Long monTotHours = 0L;
		Long tueTotHours = 0L;
		Long wedTotHours = 0L;
		Long thuTotHours = 0L;
		Long friTotHours = 0L;
		Long satTotHours = 0L;
		Long sunTotHours = 0L;
		
		
		/**
		  *calculating prior 6th week mondaydate and post 6th week sunday day for employee
		  *
	      *
	    */
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(commonUtil.currentDate());
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
		Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN");
		
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		Date previousMondayDate = previousMonday.getTime();
		
		Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
		Date nextSundayDate = nextSunday.getTime();
		
		
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = null;
		if(employeeStartDate != null && employeeStartDate != null){
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
		 
		  String pattern = "dd-MM-yyyy";
          SimpleDateFormat format = new SimpleDateFormat(pattern);
          Date currentStartDate = null;
          Date currentEndDate = null;
          Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		   startCal.setLenient(false);
		   /**
			 * startDate == null means current week timesheets should be shown
		   */
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
		   /**
			 * startDate comes from ui screen when << or >> links clicked
		   */
		  else{
               Date sDate  =  Constants.DMY.parse(startDate);
			   startCal.setTime(sDate);
		  }
		   String weekStartDate = format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime());
		   String weekEndDate = format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime());
		  
         timesheetSubmissionsList = timesheetSubmissionsService.getAllEmployeeTimesheetSubmissions(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime(), user.getId());
         currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
         currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
        
		
	    /**
		 * check whether previous submission records are available or not
	    */
		Date leftNavStartDate = timesheetSubmissionsService.getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(user.getId(), currentStartDate);
		/**
		 * check whether next submission records are available or not
	    */
		Date rightNavStartDate = timesheetSubmissionsService.getRightNavStartDateForEmployeeBasedOnCurrentStartDate(user.getId(), currentStartDate);
		if(rightNavStartDate != null){
	    Calendar rightCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);	
		rightCal.setLenient(false);
		rightCal.setTime(rightNavStartDate);
		Calendar rightNavEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(rightCal, "SUN");
		}               
        String approvalStatus = null;
        HashSet<String> combinedApprovalStatus = new HashSet<String>();
        ArrayList<Boolean> isThereAnyApproverApproved = null;
        int count = 9;
        int lastRow =0;
        String projectName ="";
        String billableHours = "";
        String nonbillableHours = "";
        String submissionStatusStr = "";
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			 int column = 0;
			
			lastRow = count;
			isThereAnyApproverApproved = new ArrayList<Boolean>();
			/**
			 * For to show submission status of each submission record(Hint:As per employee one submission related to one project)
		    */
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(ts.getId());
			/**
			 * Don't show withdraw button if any approver(either level1 or level2) is approved
		    */
			SubmissionApproverHierarchy isSah = submissionApproverHierarchyService.isThereAnyApproverChangesStatusForTheSubmission(ts.getId(), "APPROVED");
			if(isSah != null){
				isThereAnyApproverApproved.add(true);
			}
			else{
				isThereAnyApproverApproved.add(false);
			}
			
			Submission_Status submissionStatus = sah.getSubmissionStatus();
			
			
			/**
			 * Here getting  dates which are enabled in the current submission
			 * (Hint:In particular project,the member may assigned in the mid of the week) 
		    */
			HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(ts.getProjectMembers().getId(), currentStartDate, currentEndDate);
			
			submissionStatusStr = submissionStatus.getValue();
			String disbleCondition1 = "";
			 Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 currentCal.setLenient(false);
			 currentCal.setTime(currentStartDate);
            if(sah.getSubmissionApprovalStatus() != null){
				approvalStatus = sah.getSubmissionApprovalStatus().getValue();
			}
			else{
				approvalStatus = Submission_ApprovalStatus.getNameByValue("new").getValue();
			}
            
            if(isSah != null && approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())){
            	combinedApprovalStatus.add(Submission_ApprovalStatus.getNameByValue("approved").getValue());
            }
            else{
            	combinedApprovalStatus.add(approvalStatus);
            }
			if(ts.getProjectMembers() != null && ts.getProjectMembers().getProjects() != null){
				  projectName = ts.getProjectMembers().getProjects().getProjectName(); 
			}
			
			if(ts.getBillableHours() != null){
				  billableHours = ts.getBillableHours();
			}
			
			if(ts.getNonBillableHours() != null){
				  nonbillableHours = ts.getNonBillableHours();
			}
			
            StringBuilder disbleCondition = new StringBuilder("");
    		StringBuilder disbleCondition2 = new StringBuilder("");
    		StringBuilder disbleCondition3 = new StringBuilder("");
    		StringBuilder disbleCondition4 = new StringBuilder("");
    		StringBuilder disbleCondition5 = new StringBuilder("");
    		StringBuilder disbleCondition6 = new StringBuilder("");
    		StringBuilder disbleCondition7 = new StringBuilder("");
    		
    		
            DateFormat dateFor = Constants.DF_DMY;
    		Date actualStartDate = dateFor.parse(weekStartDate.replaceAll("/", "-"));
    	    Date actualEndDate = format.parse(weekEndDate.replaceAll("/", "-"));
    		int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(format.parse(weekStartDate));
    		String weekStartDate1 = weekStartDate;
    		switch (dayOfTheDateWithStartDate) {
    	        case 1:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,6);
    	                 disbleCondition.append("disabled");
    	                 disbleCondition2.append("disabled");
    	                 disbleCondition3.append("disabled");
    	                 disbleCondition4.append("disabled");
    	                 disbleCondition5.append("disabled");
    	                 disbleCondition6.append("disabled");
    	                 break;
    	        case 2:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,0);
    	                 break;
    	        case 3:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,1);
    	                 disbleCondition.append("disabled");
    	             break;
    	        case 4:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,2);
    			         disbleCondition.append("disabled");
    		             disbleCondition2.append("disabled");
    	            break;
    	        case 5: weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,3);
    			        disbleCondition.append("disabled");
    		            disbleCondition2.append("disabled");
    		            disbleCondition3.append("disabled");
    	            break;
    	        case 6:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,4);
    			         disbleCondition.append("disabled");
    		             disbleCondition2.append("disabled");
    		             disbleCondition3.append("disabled");
    		             disbleCondition4.append("disabled");
    	              break;
    	        case 7: weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,5);
    			        disbleCondition.append("disabled");
    		            disbleCondition2.append("disabled");
    		            disbleCondition3.append("disabled");
    		            disbleCondition4.append("disabled");
    		            disbleCondition5.append("disabled");
    	             break;
    	        default:   weekStartDate = CalendarUtil.getPreviousDateByDecrementor(weekStartDate,0);
    	                 break;
           }
    		int dayOfTheDateWithEndDate = CalendarUtil.getDayOfTheDate(format.parse(weekEndDate));
    		switch (dayOfTheDateWithEndDate) {
    			case 2:    disbleCondition2.append("disabled");
    	                   disbleCondition3.append("disabled");
    	                   disbleCondition4.append("disabled");
    	                   disbleCondition5.append("disabled");
    	                   disbleCondition6.append("disabled");
    	                   disbleCondition7.append("disabled");
    	                   break;
    	                   
    	        case 3:  disbleCondition3.append("disabled");
    			         disbleCondition4.append("disabled");
    			         disbleCondition5.append("disabled");
    			         disbleCondition6.append("disabled");
    			         disbleCondition7.append("disabled");
    			          break;
    	        case 4:  disbleCondition4.append("disabled");
    			         disbleCondition5.append("disabled");
    			         disbleCondition6.append("disabled");
    			         disbleCondition7.append("disabled");
    			         break;
    	        case 5:  disbleCondition5.append("disabled");
    		             disbleCondition6.append("disabled");
    		             disbleCondition7.append("disabled");
    	                 break;
    	        case 6:   disbleCondition6.append("disabled");
    		             disbleCondition7.append("disabled");
    	                 break;
    	        case 7:  disbleCondition7.append("disabled");
    	                 break;
            }
    		
    		writeRow = sheet.createRow(column+count);
    		Cell datecell1 = writeRow.createCell(1);
    		datecell1.setCellValue(weekStartDate1); 
    		
    		String tuesDayDate =  CalendarUtil.getNextDate(weekStartDate1);
    		Cell datecell2 = writeRow.createCell(2);
    		datecell2.setCellValue(tuesDayDate); 
    		
    		String WednesDayDate =  CalendarUtil.getNextDate(tuesDayDate);
    		Cell datecell3 = writeRow.createCell(3);
    		datecell3.setCellValue(WednesDayDate); 
    		
    		String ThursDayDate =  CalendarUtil.getNextDate(WednesDayDate);
    		Cell datecell4 = writeRow.createCell(4);
    		datecell4.setCellValue(ThursDayDate); 
    		
    		String FriDayDate =  CalendarUtil.getNextDate(ThursDayDate);
    		Cell datecell5 = writeRow.createCell(5);
    		datecell5.setCellValue(FriDayDate); 
    		
    		String SaturdayDate =  CalendarUtil.getNextDate(FriDayDate);
    		Cell datecell6 = writeRow.createCell(6);
    		datecell6.setCellValue(SaturdayDate); 
    		
    		String SundayDate =  CalendarUtil.getNextDate(SaturdayDate);
    		Cell datecell7 = writeRow.createCell(7);
    		datecell7.setCellValue(SundayDate); 
            
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			Collection<TimesheetEntries>  timesheetEntriesList =   getAllTimesheetEntriesBySubmissionId(ts.getId());
			if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
						Integer maxCount = timesheetEntriesList.size();
						maxCount = maxCount+1;
						for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
							  Long weekTotHours = 0L;
							    Long monHours = 0L;
								Long tueHours = 0L;
								Long wedHours = 0L;
								Long thuHours = 0L;
								Long friHours = 0L;
								Long satHours = 0L;
								Long sunHours = 0L;
							writeRow = sheet.createRow(column+count+1); 
//							writeRow.createCell(0).setCellValue(projectName);
							 lastRow = count+timesheetEntriesList.size();
							 /*sheet.addMergedRegion(new CellRangeAddress(
									count, //first row (0-based)
									lastRow, //last row  (0-based)
							        0, //first column (0-based)
							        0  //last column  (0-based)
							));*/
						              
						              if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
						            	  if(timesheetEntries.getProjectTask() != null){
						            	  if(timesheetEntries.getProjectTask() != null ){
						     				 writeRow.createCell(0).setCellValue(timesheetEntries.getProjectTask().getTaskName()); 
						     			}
						            	  }
						              }
						              Cell cell2 = null;
						              if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
						            	  cell2 = writeRow.createCell(1);
						            	  cell2.setCellValue(timesheetEntries.getMonHours()); 
						            	 // monTotHours = monTotHours + Double.parseDouble(timesheetEntries.getMonHours());
						            	  monTotHours = monTotHours + getTotalHoursFormDayHours(timesheetEntries.getMonHours());
						            	  monHours =  monHours + getTotalHoursFormDayHours(timesheetEntries.getMonHours());
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
					            		  cell2.setCellComment(getCellComment(sheet,wb,timesheetEntries.getMoncomments()));
					            	  }
						              Cell cell3 = writeRow.createCell(2);
						              if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
						            	  cell3.setCellValue(timesheetEntries.getTueHours());
						            	  tueTotHours = tueTotHours + getTotalHoursFormDayHours(timesheetEntries.getTueHours());
						            	  tueHours = tueHours + getTotalHoursFormDayHours(timesheetEntries.getTueHours());
						              }
						              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
						            	  cell3.setCellComment(getCellComment(sheet,wb,timesheetEntries.getTuecomments()));
						              }
						              Cell cell4 = writeRow.createCell(3);
						              if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
						            	  cell4.setCellValue(timesheetEntries.getWedHours()); 
						            	  wedTotHours = wedTotHours + getTotalHoursFormDayHours(timesheetEntries.getWedHours());
						            	  wedHours = wedHours + getTotalHoursFormDayHours(timesheetEntries.getWedHours());
						              }
						              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
						            	  cell4.setCellComment(getCellComment(sheet,wb,timesheetEntries.getWedcomments()));
						              }
						              Cell cell5 = writeRow.createCell(4);
						              if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
						            	  cell5.setCellValue(timesheetEntries.getThuHours());   
						            	  thuTotHours = thuTotHours + getTotalHoursFormDayHours(timesheetEntries.getThuHours());
						            	  thuHours = thuHours + getTotalHoursFormDayHours(timesheetEntries.getThuHours());
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
					            		  cell5.setCellComment(getCellComment(sheet,wb,timesheetEntries.getThucomments()));
					            	  }
						              Cell cell6 = writeRow.createCell(5);
						              if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
						            	  cell6.setCellValue(timesheetEntries.getFriHours()); 
						            	  friTotHours = friTotHours + getTotalHoursFormDayHours(timesheetEntries.getFriHours());
						            	  friHours = friHours + getTotalHoursFormDayHours(timesheetEntries.getFriHours());
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
					            		  cell6.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }
						              Cell cell7 = writeRow.createCell(6);
						              if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
						            	  cell7.setCellValue(timesheetEntries.getSatHours());   
						            	  satTotHours = satTotHours + getTotalHoursFormDayHours(timesheetEntries.getSatHours());
						            	  satHours = satHours + getTotalHoursFormDayHours(timesheetEntries.getSatHours());
						            	  
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
					            		  cell7.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }
						              Cell cell8 = writeRow.createCell(7);
						              if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
						            	  cell8.setCellValue(timesheetEntries.getSunHours());   
						            	  sunTotHours = sunTotHours + getTotalHoursFormDayHours(timesheetEntries.getSunHours());
						            	  sunHours = sunHours + getTotalHoursFormDayHours(timesheetEntries.getSunHours());
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
					            		  cell8.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }  
					            	  
					            	
					            	  weekTotHours = monHours + tueHours + wedHours + thuHours + friHours + satHours + sunHours;
					            	  Cell cell9 = writeRow.createCell(8);
					            	  cell9.setCellValue(getTotalHours(weekTotHours)); 
					            	  
						              column++;
						     }
						                /* Cell cell9 = writeRow.createCell(8);
						              if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
						            	  cell9.setCellValue(submissionStatusStr); 
						            	  CellStyle style =getCellStyle(wb,submissionStatusStr);
						            	  cell9.setCellStyle(style);
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
							        	cell9.setCellValue(submissionStatusStr); 
							        	 CellStyle style =getCellStyle(wb,submissionStatusStr);
						            	  cell9.setCellStyle(style);
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
							        	cell9.setCellValue(submissionStatusStr);   
							        	 CellStyle style =getCellStyle(wb,submissionStatusStr);
						            	  cell9.setCellStyle(style);
							        	if(isSah == null ){
							        		cell9.setCellValue(submissionStatusStr);
							            	  cell9.setCellStyle(style);
							        	}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
							        	cell9.setCellValue(submissionStatusStr);   
							        	 CellStyle style =getCellStyle(wb,submissionStatusStr);
						            	  cell9.setCellStyle(style);
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
							        	cell9.setCellValue(submissionStatusStr);
							        	 CellStyle style =getCellStyle(wb,submissionStatusStr);
						            	  cell9.setCellStyle(style);
							        }*/
				  }
			count = lastRow+1;
			}
		Long totalHours = monTotHours+tueTotHours+wedTotHours+thuTotHours+friTotHours+satTotHours+sunTotHours;
		writeRow = sheet.createRow(count);
		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Total");
		CellStyle style =getCellStyle(wb,"");
   	    cell1.setCellStyle(style);
		writeRow.createCell(1).setCellValue(getTotalHours(monTotHours));
		writeRow.createCell(2).setCellValue(getTotalHours(tueTotHours));
		writeRow.createCell(3).setCellValue(getTotalHours(wedTotHours));
		writeRow.createCell(4).setCellValue(getTotalHours(thuTotHours));
		writeRow.createCell(5).setCellValue(getTotalHours(friTotHours));
		writeRow.createCell(6).setCellValue(getTotalHours(satTotHours));
		writeRow.createCell(7).setCellValue(getTotalHours(sunTotHours));
		writeRow.createCell(8).setCellValue(getTotalHours(totalHours));
		
		StringBuilder fileName=new StringBuilder();
		 sheet = getExcelSheetWithHeader(sheet, writeRow, wb,weekStartDate,weekEndDate,user,totalHours,projectName,submissionStatusStr,billableHours,nonbillableHours);
		file = new File(fileName.append(user.getFirstName()).append("_").append(weekStartDate).append(" to ").append(weekEndDate).append(".xls").toString());
		FileOutputStream fos = new FileOutputStream(file);
		try {
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else{
			logger.info("No Time sheets");
		}
		return file;
	}
	
	 /**
		 * @author Rajashekar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * This method is used to get all timesheets by id.
		 * @param submissionId This is the first paramter to getAllTimesheetEntriesBySubmissionId method
		 * @return Collection<TimesheetEntries> 
		 * @throws NullPointer
		 */
    public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		Collection<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(submissionId);
		return list;
		
	}
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, String weekStartDate, String weekEndDate, User user, Long totalHours, String projectName, String submissionStatusStr, String billableHours, String nonbillableHours) {

		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
         font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(1);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell1 = writeRow.createCell(0);
		 cell1.setCellValue("Name :");
		 cell1.setCellStyle(style);
		 
		 Cell cell2 = writeRow.createCell(1);
		 cell2.setCellValue(user.getFirstName());
		 cell2.setCellStyle(style);
		 
		 createExcelsheet.setColumnWidth(3, 10000);
		 createExcelsheet.setColumnWidth(4, 10000);
		 
		 Cell cell11 = writeRow.createCell(3);
		 cell11.setCellValue("Status :");
		 cell11.setCellStyle(style);
		 
		 Cell cell21 = writeRow.createCell(4);
		 cell21.setCellValue(submissionStatusStr);
		 cell21.setCellStyle(style);
		 
		 
		 writeRow = createExcelsheet.createRow(2);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell3 = writeRow.createCell(0);
		 cell3.setCellValue("Email Id :");
		 cell3.setCellStyle(style);
		 
		 Cell cell4 = writeRow.createCell(1);
		 cell4.setCellValue(user.getEmail());
		 cell4.setCellStyle(style);
		 
		 createExcelsheet.setColumnWidth(3, 10000);
		 createExcelsheet.setColumnWidth(4, 10000);
		 
		 Cell cell31 = writeRow.createCell(3);
		 cell31.setCellValue("Billable Hours :");
		 cell31.setCellStyle(style);
		 
		 Cell cell41 = writeRow.createCell(4);
		 cell41.setCellValue(billableHours);
		 cell41.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(3);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Project : ");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
//		 StringBuilder val=new StringBuilder();
		 cell6.setCellValue(projectName); 
		 cell6.setCellStyle(style);
		 
		 createExcelsheet.setColumnWidth(3, 10000);
		 createExcelsheet.setColumnWidth(4, 10000);
		 
		 Cell cell51 = writeRow.createCell(3);
		 cell51.setCellValue("Non-Billable Hours :");
		 cell51.setCellStyle(style);
		 
		 Cell cell61 = writeRow.createCell(4);
		 cell61.setCellValue(nonbillableHours); 
		 cell61.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(4);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell7 = writeRow.createCell(0);
		 cell7.setCellValue("Frequency : ");
		 cell7.setCellStyle(style);
		 
		 Cell cell8 = writeRow.createCell(1);
		 StringBuilder val=new StringBuilder();
		 cell8.setCellValue(val.append(weekStartDate).append(" to ").append(weekEndDate).toString()); 
		 cell8.setCellStyle(style);
		 
		 createExcelsheet.setColumnWidth(3, 10000);
		 createExcelsheet.setColumnWidth(4, 10000);
		 
		 Cell cell9 = writeRow.createCell(3);
		 cell9.setCellValue("Total Hours :");
		 cell9.setCellStyle(style);
		 
		 Cell cell10 = writeRow.createCell(4);
		 cell10.setCellValue(getTotalHours(totalHours)); 
		 cell10.setCellStyle(style);
		 //=================================================
		 
		return createExcelsheet = setExcelHeader(createExcelsheet,style,createExcelworkbook);
	}

	private Sheet setExcelHeader(Sheet createExcelsheet, CellStyle style, Workbook wb) {
		String headers [] = Constants.TIMESHEET_EXCEL_HEADERS;
		Row excelHeader = createExcelsheet.createRow(8);
		 for (int i=0; i<headers.length;i++){
		         Cell cell = excelHeader.createCell(i);
		         cell.setCellValue(headers[i]);
		            Font font = wb.createFont();
		            font.setColor(HSSFColor.BLUE.index);
		            style.setFont(font);
		            cell.setCellStyle(style);
//		         cell.setCellStyle(style);
		     }
		return createExcelsheet;
	}
	
	private org.apache.poi.ss.usermodel.Comment getCellComment(Sheet sheet, Workbook wb,String commentValue){
		/*cell comments start here */
        Drawing drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = wb.getCreationHelper();
        // When the comment box is visible, have it show in a 1x3 space
        ClientAnchor anchor = factory.createClientAnchor();
        org.apache.poi.ss.usermodel.Comment comment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(commentValue);
        comment.setString(str);
		return comment;
       
        /*cell comments end here */
	}
	
	private Long getTotalHoursFormDayHours(String dayHours){
		Long total = 0l;
		String[] parts = dayHours.split(":");
		String hours = parts[0]; 
		String min = parts[1];
		
		Long  localHours = Long.parseLong(hours) * 60;
		Long  localHoursMinute = Long.parseLong(min);
		Long  finalmin = localHours + localHoursMinute;
		total = total + finalmin;
		return total;
	
}
	private String getTotalHours(Long total){
		String actualTimeString = null;
		if(total != null){
            int actualHours = (int) (total / 60);
            int actualMinutes =   (int)(total % 60);
            String actualMinutesStr = ""+actualMinutes;
            String startWithZero = "";
            if(StringUtils.isNotEmpty(actualMinutesStr) && actualMinutesStr.length()==1)
            	startWithZero ="0";	
            StringBuilder actualTime=new StringBuilder();
            actualTimeString =  actualTime.append(actualHours).append(".").append(startWithZero+actualMinutes).toString();
            
		}
		return actualTimeString;
	}
	public CellStyle getCellStyle(Workbook wb, String submissionStatusStr) {
		CellStyle style = wb.createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		if("SUBMITTED".equalsIgnoreCase(submissionStatusStr)){
			style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
			font.setColor(HSSFColor.WHITE.index);
		}else{
			style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			font.setColor(HSSFColor.WHITE.index);
		}
		style.setFont(font);
		return style;
	}
}
