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
import java.util.List;

import org.apache.poi.hslf.model.Comment;
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
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
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
		Sheet sheet = wb.createSheet(user.getFirstName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		
		HashMap<String, Date> submissionDateRange =	timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(user.getId(),orgLocations.getId());
		Date employeeStartDate = submissionDateRange.get("STARTDATE");
		Date employeeEndDate = submissionDateRange.get("ENDDATE");
		
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
		   sheet = getExcelSheetWithHeader(sheet, writeRow, wb,weekStartDate,weekEndDate,user);
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
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			 int column = 0;
			String projectName ="";
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
			
			String submissionStatusStr = submissionStatus.getValue();
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
			
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			Collection<TimesheetEntries>  timesheetEntriesList =   getAllTimesheetEntriesBySubmissionId(ts.getId());
			if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
						Integer maxCount = timesheetEntriesList.size();
						maxCount = maxCount+1;
						for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
							writeRow = sheet.createRow(column+count); 
							writeRow.createCell(0).setCellValue(projectName);
							 lastRow = count+timesheetEntriesList.size();
							 sheet.addMergedRegion(new CellRangeAddress(
									count, //first row (0-based)
									lastRow, //last row  (0-based)
							        0, //first column (0-based)
							        0  //last column  (0-based)
							));
						              
						              if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
						            	  if(timesheetEntries.getProjectTask() != null){
						            	  if(timesheetEntries.getProjectTask() != null ){
						     				 writeRow.createCell(1).setCellValue(timesheetEntries.getProjectTask().getTaskName()); 
						     			}
						            	  }
						              }
						              Cell cell2 = null;
						              if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
						            	  cell2 = writeRow.createCell(2);
						            	  cell2.setCellValue(timesheetEntries.getMonHours());  
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
					            		  cell2.setCellComment(getCellComment(sheet,wb,timesheetEntries.getMoncomments()));
					            	  }
						              Cell cell3 = writeRow.createCell(3);
						              if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
						            	  cell3.setCellValue(timesheetEntries.getTueHours());   
						              }
						              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
						            	  cell3.setCellComment(getCellComment(sheet,wb,timesheetEntries.getTuecomments()));
						              }
						              Cell cell4 = writeRow.createCell(4);
						              if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
						            	  cell4.setCellValue(timesheetEntries.getWedHours());   
						              }
						              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
						            	  cell4.setCellComment(getCellComment(sheet,wb,timesheetEntries.getWedcomments()));
						              }
						              Cell cell5 = writeRow.createCell(5);
						              if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
						            	  cell5.setCellValue(timesheetEntries.getThuHours());   
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
					            		  cell5.setCellComment(getCellComment(sheet,wb,timesheetEntries.getThucomments()));
					            	  }
						              Cell cell6 = writeRow.createCell(6);
						              if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
						            	  cell6.setCellValue(timesheetEntries.getFriHours());   
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
					            		  cell6.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }
						              Cell cell7 = writeRow.createCell(7);
						              if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
						            	  cell7.setCellValue(timesheetEntries.getSatHours());   
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
					            		  cell7.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }
						              Cell cell8 = writeRow.createCell(8);
						              if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
						            	  cell8.setCellValue(timesheetEntries.getSunHours());   
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
					            		  cell8.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }  
						              column++;
						     }
//						       
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
							            	  writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
						            	  writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
							        	writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        	if(isSah == null ){
							        		writeRow.createCell(9).setCellValue(submissionStatusStr);
							        	}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
							        	writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
							        	writeRow.createCell(9).setCellValue(submissionStatusStr);
							        }
				  }
			count = lastRow+1;
			}
		
		
		file = new File(user.getFirstName()+"_"+weekStartDate+" to "+weekEndDate+".xls");
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
	

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, String weekStartDate, String weekEndDate, User user) {

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
		 
		 writeRow = createExcelsheet.createRow(2);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell3 = writeRow.createCell(0);
		 cell3.setCellValue("Email Id :");
		 cell3.setCellStyle(style);
		 
		 Cell cell4 = writeRow.createCell(1);
		 cell4.setCellValue(user.getEmail());
		 cell4.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(3);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Week : ");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
		 cell6.setCellValue(weekStartDate+" to "+weekEndDate); 
		 cell6.setCellStyle(style);
		 
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
}
