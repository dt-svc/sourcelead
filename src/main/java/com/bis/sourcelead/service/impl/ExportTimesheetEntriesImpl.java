package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;
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
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
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
	
	@Autowired
	ProjectMembersService projectMembersService;

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
						            	 // monTotHours = monTotHours + Double.parseDouble(timesheetEntries.getMonHours());
						            	  monTotHours = monTotHours + getTotalHoursFormDayHours(timesheetEntries.getMonHours());
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
					            		  cell2.setCellComment(getCellComment(sheet,wb,timesheetEntries.getMoncomments()));
					            	  }
						              Cell cell3 = writeRow.createCell(3);
						              if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
						            	  cell3.setCellValue(timesheetEntries.getTueHours());
						            	  tueTotHours = tueTotHours + getTotalHoursFormDayHours(timesheetEntries.getTueHours());
						              }
						              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
						            	  cell3.setCellComment(getCellComment(sheet,wb,timesheetEntries.getTuecomments()));
						              }
						              Cell cell4 = writeRow.createCell(4);
						              if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
						            	  cell4.setCellValue(timesheetEntries.getWedHours()); 
						            	  wedTotHours = wedTotHours + getTotalHoursFormDayHours(timesheetEntries.getWedHours());
						              }
						              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
						            	  cell4.setCellComment(getCellComment(sheet,wb,timesheetEntries.getWedcomments()));
						              }
						              Cell cell5 = writeRow.createCell(5);
						              if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
						            	  cell5.setCellValue(timesheetEntries.getThuHours());   
						            	  thuTotHours = thuTotHours + getTotalHoursFormDayHours(timesheetEntries.getThuHours());
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
					            		  cell5.setCellComment(getCellComment(sheet,wb,timesheetEntries.getThucomments()));
					            	  }
						              Cell cell6 = writeRow.createCell(6);
						              if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
						            	  cell6.setCellValue(timesheetEntries.getFriHours()); 
						            	  friTotHours = friTotHours + getTotalHoursFormDayHours(timesheetEntries.getFriHours());
						              }
					            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
					            		  cell6.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }
						              Cell cell7 = writeRow.createCell(7);
						              if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
						            	  cell7.setCellValue(timesheetEntries.getSatHours());   
						            	  satTotHours = satTotHours + getTotalHoursFormDayHours(timesheetEntries.getSatHours());
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
					            		  cell7.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }
						              Cell cell8 = writeRow.createCell(8);
						              if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
						            	  cell8.setCellValue(timesheetEntries.getSunHours());   
						            	  sunTotHours = sunTotHours + getTotalHoursFormDayHours(timesheetEntries.getSunHours());
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
					            		  cell8.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }  
						              column++;
						     }
						                 Cell cell9 = writeRow.createCell(9);
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
							        }
				  }
			count = lastRow+1;
			}
		Long totalHours = monTotHours+tueTotHours+wedTotHours+thuTotHours+friTotHours+satTotHours+sunTotHours;
		writeRow = sheet.createRow(count);
		Cell cell1 = writeRow.createCell(1);
		cell1.setCellValue("Total");
		CellStyle style =getCellStyle(wb,"");
   	    cell1.setCellStyle(style);
		writeRow.createCell(2).setCellValue(getTotalHours(monTotHours));
		writeRow.createCell(3).setCellValue(getTotalHours(tueTotHours));
		writeRow.createCell(4).setCellValue(getTotalHours(wedTotHours));
		writeRow.createCell(5).setCellValue(getTotalHours(thuTotHours));
		writeRow.createCell(6).setCellValue(getTotalHours(friTotHours));
		writeRow.createCell(7).setCellValue(getTotalHours(satTotHours));
		writeRow.createCell(8).setCellValue(getTotalHours(sunTotHours));
		writeRow.createCell(9).setCellValue(getTotalHours(totalHours));
		
		StringBuilder fileName=new StringBuilder();
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
		 StringBuilder val=new StringBuilder();
		 cell6.setCellValue(val.append(weekStartDate).append(" to ").append(weekEndDate).toString()); 
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

	@Override
	public File timeSheetApprovelExport(User user, OrgLocations orgLocations, Date startDate, Date endDate,String timesheetFrequency, String client, String stage,String frequencyMonth,String weekFrequency) throws Exception {
		Long clientId;
		if(!client.equalsIgnoreCase("")){
		 clientId = Long.parseLong(client) ;
		}else{
		  clientId = null;
		}
		
		List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, startDate, endDate, clientId, stage, orgLocations.getId(), user.getId());
		String[] headers;
		int flag = 0;
			StringBuilder StDateAndEndDate = new StringBuilder("");
			StringBuilder separator = new StringBuilder(",");
		    DateFormat format = Constants.DF_DMY;
		    DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		        
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate),format.format(endDate));
			if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
				for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
		    		 String  k = entry.getKey();
		    		 String  v = entry.getValue();
				        Date weekStartDate;
				        Date weekEndDate;
					try {
						 weekStartDate = format.parse(k.replaceAll("/", "-"));
						 weekEndDate = format.parse(v.replaceAll("/", "-"));
						 if(weekStartDate.equals(weekEndDate)){
							 StDateAndEndDate.append(flag != 0 ? separator : "" ).append(shortormat.format(weekStartDate));
						 }else{
							 StDateAndEndDate.append(flag != 0 ? separator : "" ).append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate));
						 }
	   					
					} catch (Exception e) {}
				        
				        flag++;
				}
			}
			String[] headersList = Constants.TIMESHEET_APPROVAL_MECHANISM_HEADER;
			headers = getHeaders(headersList,StDateAndEndDate.toString());
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append(orgLocations.getOrg().getOrgName()).append("_TimeSheetApproval_").append(dateFormat.format(new Date())).toString(),".pdf");

	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        com.itextpdf.text.Font boldFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, com.itextpdf.text.Font.BOLD);

	        BaseColor myBordColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myBordColor);
	        StringBuilder sbReqStyle = new StringBuilder();
	        sbReqStyle.append(orgLocations.getOrg().getOrgName()).append(" ");

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgLocations.getOrg().getOrgName()).toString(),boldFont);

            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
             PdfPTable table = new PdfPTable(headers.length);
            com.itextpdf.text.Font font = new com.itextpdf.text.Font(FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(sbReqStyle.toString(),font));
	        cell1.setColspan(12);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
	        
	        if(timesheetFrequency.equalsIgnoreCase("Monthly")){
	        	 table.setWidths(new int[]{5,5,5,5,3,3,3,3,3,3,3,5});
	        }else if(timesheetFrequency.equalsIgnoreCase("Semi-monthly")){
            table.setWidths(new int[]{3,5,5,3,5,3,3,3,3,5});
	        }else if(timesheetFrequency.equalsIgnoreCase("Bi-weekly")){
	        	table.setWidths(new int[]{3,5,5,3,5,3,3,3,5});
	        }else{
	        	table.setWidths(new int[]{3,5,5,3,5,3,3,5});
	        }
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new com.itextpdf.text.Font(FontFamily.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> timesheetApproval = new ArrayList<>();
	        for(ProjectMembers proMembers : projectMembers){
	        	timesheetApproval.add(prepareTimesheetPdfToArray(proMembers,client,timesheetFrequency,stage,user,startDate,endDate));
	        }
	        
	        for (String[] reqArray: timesheetApproval) {
                for (int i = 0; i < reqArray.length; i++) {
                    String datum = reqArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new com.itextpdf.text.Font(FontFamily.HELVETICA, 10, com.itextpdf.text.Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        
	        document.addTitle("Candiate List Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	    } catch (DocumentException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        document.close();
	    }
		return file;
	}
	
	
	private String[] getHeaders(String[] headers, String dates) {
		String[] weekDates = dates.split(",");
		int k=4;
		List<String> headerList = new ArrayList<String>(Arrays.asList(headers));
		List<String> listDates = new ArrayList<String>(Arrays.asList(weekDates));
	    	for(String dateDynamic :listDates){
	    	headerList.add(k, dateDynamic);
	    	k++;
	    }
	    String [] headerListString = headerList.toArray(new String[headerList.size()]);
		return headerListString;
	}

	private String[] prepareTimesheetPdfToArray(ProjectMembers projMembers,String client,String timesheetFrequency,String Stage,User user,Date startDate, Date endDate) throws Exception {
		String[] timeSheetApprovalArray;
			
			StringBuilder StDateAndEndDate = new StringBuilder("");
			StringBuilder separator = new StringBuilder(",");
		    DateFormat format = Constants.DF_DMY;
		    DateFormat shortormat = Constants.SHORT_DF_STD_DM;
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate),format.format(endDate));
			int flag = 0; 
			if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
				for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
		    		 String  k = entry.getKey();
		    		 String  v = entry.getValue();
				        Date weekStartDate;
				        Date weekEndDate;
					try {
						 weekStartDate = format.parse(k.replaceAll("/", "-"));
						 weekEndDate = format.parse(v.replaceAll("/", "-"));
						 if(weekStartDate.equals(weekEndDate)){
							 StDateAndEndDate.append(flag != 0 ? separator : "" ).append(shortormat.format(weekStartDate));
						 }else{
							 StDateAndEndDate.append(flag != 0 ? separator : "" ).append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate));
						 }
	   					
					} catch (Exception e) {}
				        
				        flag++;
				}
			}
			
			String[] headersList = Constants.TIMESHEET_APPROVAL_MECHANISM_HEADER;
			String [] timeSheetApproval = getHeaders(headersList,StDateAndEndDate.toString());
			timeSheetApprovalArray =new String[timeSheetApproval.length];
			
	if(projMembers.getEmployee().getUser().getFullName() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getFullName())){
		timeSheetApprovalArray[0] = projMembers.getEmployee().getUser().getFullName();
	}else{
		timeSheetApprovalArray[0] = "--";
	}
	if(projMembers.getEmployee().getUser().getEmail() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getEmail())){
		timeSheetApprovalArray[1] = projMembers.getEmployee().getUser().getEmail();
	}else{
		timeSheetApprovalArray[1] = "--";
	}
	if(projMembers.getEmployee().getUser().getMobilePhone() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getMobilePhone())){
		timeSheetApprovalArray[2] = projMembers.getEmployee().getUser().getMobilePhone();
	}else{
		timeSheetApprovalArray[2] = "--";
	}
	if(projMembers.getProjects().getOrgRelationId() != null){
		timeSheetApprovalArray[3] = projMembers.getProjects().getOrgRelationId().getResponderLocationId().getOrg().getOrgName();
	}else{
		timeSheetApprovalArray[3] = "--";
	}
	if(timesheetFrequency.equalsIgnoreCase("Weekly")){
		LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
			Float totalHours = 0f;
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
			if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
				if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
							timeSheetApprovalArray[4] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
						} else {
							timeSheetApprovalArray[4] = "0/40";
						}
					}
				}
			}
	     if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
	    	 timeSheetApprovalArray[5]   = totalHours.toString()+"/40";
	     }else{
	    	 timeSheetApprovalArray[5] = "0.0/40";
	     }
		if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
			timeSheetApprovalArray[6] = sah.getSubmissionApprovalStatus().toString();
		}else{
			timeSheetApprovalArray[6] = "--";
		}
		if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
			timeSheetApprovalArray[7] = sah.getActionPerformedBy();
		}else{
			timeSheetApprovalArray[7] = "--";
		}
	}else if(timesheetFrequency.equalsIgnoreCase("Bi-weekly")){
		LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
		Float totalHours = 0f;
		SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
		if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
			if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
				for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
					if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
						totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
						timeSheetApprovalArray[4] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
					} else {
						timeSheetApprovalArray[4] = "0/40";
					}
				}
				for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
					if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
						totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
						timeSheetApprovalArray[5] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
					} else {
						timeSheetApprovalArray[5] = "0/40";
					}
				}
			}
		}
     if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
    	 timeSheetApprovalArray[6]   = totalHours.toString()+"/40";
     }else{
    	 timeSheetApprovalArray[6] = "0.0/40";
     }
	if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
		timeSheetApprovalArray[7] = sah.getSubmissionApprovalStatus().toString();
	}else{
		timeSheetApprovalArray[7] = "--";
	}
	if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
		timeSheetApprovalArray[8] = sah.getActionPerformedBy();
	}else{	
		timeSheetApprovalArray[8] = "--";
	}
}else if(timesheetFrequency.equalsIgnoreCase("Semi-monthly")){
	LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
	Float totalHours = 0f;
	SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
	if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
		if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[4] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[4] = "0/40";
				}
			}
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[5] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[5] = "0/40";
				}
			}
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[6] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[6] = "0/40";
				}
			}
		}
	}
 if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
	 timeSheetApprovalArray[7]   = totalHours.toString()+"/40";
 }else{
	 timeSheetApprovalArray[7] = "0.0/40";
 }
if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
	timeSheetApprovalArray[8] = sah.getSubmissionApprovalStatus().toString();
}else{
	timeSheetApprovalArray[8] = "--";
}
if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
	timeSheetApprovalArray[9] = sah.getActionPerformedBy();
}else{
	timeSheetApprovalArray[9] = "--";
}
}else if(timesheetFrequency.equalsIgnoreCase("Monthly")){
	LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
	Float totalHours = 0f;
	SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
	if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
		if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[4] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[4] = "0/40";
				}
			}
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[5] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[5] = "0/40";
				}
			}
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[6] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[6] = "0/40";
				}
			}
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[7] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[7] = "0/40";
				}
			}
			for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
				if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
					totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
					timeSheetApprovalArray[8] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/40";
				} else {
					timeSheetApprovalArray[8] = "0/40";
				}
			}
		}
	}
 if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
	 timeSheetApprovalArray[9]   = totalHours.toString()+"/40";
 }else{
	 timeSheetApprovalArray[9] = "0.0/40";
 }
if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
	timeSheetApprovalArray[10] = sah.getSubmissionApprovalStatus().toString();
}else{
	timeSheetApprovalArray[10] = "--";
}
if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
	timeSheetApprovalArray[11] = sah.getActionPerformedBy();
}else{
	timeSheetApprovalArray[11] = "--";
}
}
			return timeSheetApprovalArray;
	}

	private LinkedHashMap<Date, Date> getTotalCountFromStartAndEndDate(Date startDate,Date endDate) throws Exception {
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		DateFormat format = Constants.DF_DMY;
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate));
		if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
		        Date weekStartDate;
		        Date weekEndDate;
				try {
					 weekStartDate = format.parse(k.replaceAll("/", "-"));
					 weekEndDate = format.parse(v.replaceAll("/", "-"));
					 displayWeeksList.put(weekStartDate, weekEndDate);
				} catch (Exception e) {}
			}
		}
		return displayWeeksList;
	}
	
	
	
	
	private static void headerCellStyle(PdfPCell cell){
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setPaddingTop(0f);
	    cell.setPaddingBottom(7f);
	    cell.setBackgroundColor(new BaseColor(0,121,182));
	    cell.setBorder(0);
	    cell.setBorderWidthBottom(2f);
	    }

	@Override
	public File timeSheetApprovelExportExcel(User user, OrgLocations orgLocations,Date startDate,Date endDate,String timesheetFrequency,String client,String stage,String frequencyMonth,String weekFrequency) throws Exception {
		File file= null;
		Long clientId;
		if(!client.equalsIgnoreCase("")){
		 clientId = Long.parseLong(client) ;
		}else{
		  clientId = null;
		}
		//for creating workbook and sheet in excel.
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		StringBuilder excelStringBuilder=new StringBuilder();
		Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append(orgLocations.getOrg().getOrgName()).append("_").append(dateFormat.format(new Date())).toString());
		Row writeRow = null;
		// to get list of project members from data base.
		List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, startDate, endDate, clientId, stage, orgLocations.getId(), user.getId());
		for (ProjectMembers proMembers : projectMembers){
			createExcelsheet = getExcelSheetWithHeaderTimesheetApproval(createExcelsheet, writeRow, createExcelworkbook,proMembers,startDate,endDate);
		}
		// Iteration of project members list.
		int column = 0;
		for (ProjectMembers proMembers : projectMembers) {
			writeRow = createExcelsheet.createRow(column + 1);
			createExcelsheet = createXlsSheetTimesheetApproval(createExcelsheet, writeRow,proMembers,client,timesheetFrequency,startDate,endDate,user);
			column++;
		}
		// output the file using file output stream.
		StringBuilder tempFileBuilder=new StringBuilder();
		file = File.createTempFile(tempFileBuilder.append(orgLocations.getOrg().getOrgName()).append("_TimeSheet_").append(dateFormat.format(new Date())).toString(),".xls");
		file.setReadable(true);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	// creating excel sheet data here depending upon input frequency.
	private Sheet createXlsSheetTimesheetApproval(Sheet createExcelsheet, Row writeRow,ProjectMembers projMembers,String client,String timesheetFrequency,Date startDate,Date endDate,User user) throws Exception {
		if (projMembers.getEmployee().getUser().getFullName() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getFullName())) {
			writeRow.createCell(0).setCellValue(projMembers.getEmployee().getUser().getFullName());
		}
		if (projMembers.getEmployee().getUser().getEmail() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getEmail())) {
			writeRow.createCell(1).setCellValue(projMembers.getEmployee().getUser().getEmail());
		}
		if (projMembers.getEmployee().getUser().getMobilePhone() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getMobilePhone())) {
			writeRow.createCell(2).setCellValue(projMembers.getEmployee().getUser().getMobilePhone());
		}
		
		if (projMembers.getProjects().getOrgRelationId() != null) {
			writeRow.createCell(3).setCellValue(projMembers.getProjects().getOrgRelationId().getResponderLocationId().getOrg().getOrgName());
		}
		if(timesheetFrequency.equalsIgnoreCase("Weekly")){
			LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
				Float totalHours = 0f;
				SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
				if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
					if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
						for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
							if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
								totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
								writeRow.createCell(4).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/40");
							} else {
								writeRow.createCell(4).setCellValue( "0/40");
							}
						}
					}
				}
				 if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
					 writeRow.createCell(5).setCellValue( totalHours.toString()+"/40");
			     }else{
			    	 writeRow.createCell(5).setCellValue( "0.0/40");
			     }
				if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
					writeRow.createCell(6).setCellValue( sah.getSubmissionApprovalStatus().toString());
				}else{
					writeRow.createCell(6).setCellValue( "--");
				}
				if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
					writeRow.createCell(7).setCellValue(sah.getActionPerformedBy());
				}else{
					writeRow.createCell(7).setCellValue( "--");
				}
				
		}else if(timesheetFrequency.equalsIgnoreCase("Bi-weekly")){
			LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
			Float totalHours = 0f;
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
			if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
				if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
							writeRow.createCell(4).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/40");
						} else {
							writeRow.createCell(4).setCellValue( "0/40");
						}
					}
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
							writeRow.createCell(5).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/40");
						} else {
							writeRow.createCell(5).setCellValue( "0/40");
						}
					}
				}
			}
			 if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
				 writeRow.createCell(6).setCellValue( totalHours.toString()+"/40");
		     }else{
		    	 writeRow.createCell(6).setCellValue( "0.0/40");
		     }
			if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
				writeRow.createCell(7).setCellValue( sah.getSubmissionApprovalStatus().toString());
			}else{
				writeRow.createCell(7).setCellValue( "--");
			}
			if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
				writeRow.createCell(8).setCellValue(sah.getActionPerformedBy());
			}else{
				writeRow.createCell(8).setCellValue( "--");
			}
		}else if(timesheetFrequency.equalsIgnoreCase("Semi-monthly")){
			LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
			Float totalHours = 0f;
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
			if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
				if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
							writeRow.createCell(4).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/40");
						} else {
							writeRow.createCell(4).setCellValue( "0/40");
						}
					}
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
							writeRow.createCell(5).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/40");
						} else {
							writeRow.createCell(5).setCellValue( "0/40");
						}
					}
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = totalHours + sah.getWeekTotals().get(entry.getKey());
							writeRow.createCell(6).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/40");
						} else {
							writeRow.createCell(6).setCellValue( "0/40");
						}
					}
				}
			}
			 if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
				 writeRow.createCell(7).setCellValue( totalHours.toString()+"/40");
		     }else{
		    	 writeRow.createCell(7).setCellValue( "0.0/40");
		     }
			if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
				writeRow.createCell(8).setCellValue( sah.getSubmissionApprovalStatus().toString());
			}else{
				writeRow.createCell(8).setCellValue( "--");
			}
			if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
				writeRow.createCell(9).setCellValue(sah.getActionPerformedBy());
			}else{
				writeRow.createCell(9).setCellValue( "--");
			}
		}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeaderTimesheetApproval(Sheet createExcelsheet, Row writeRow,Workbook createExcelworkbook, ProjectMembers proMembers,Date startDate,Date endDate) throws Exception {
	CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		int flag = 0;
		StringBuilder StDateAndEndDate = new StringBuilder("");
		StringBuilder separator = new StringBuilder(",");
		DateFormat format = Constants.DF_DMY;
	    DateFormat shortormat = Constants.SHORT_DF_STD_DM;
	Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate),format.format(endDate));
	if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
		for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
    		 String  k = entry.getKey();
    		 String  v = entry.getValue();
		        Date weekStartDate;
		        Date weekEndDate;
			try {
				 weekStartDate = format.parse(k.replaceAll("/", "-"));
				 weekEndDate = format.parse(v.replaceAll("/", "-"));
				 if(weekStartDate.equals(weekEndDate)){
					 StDateAndEndDate.append(flag != 0 ? separator : "" ).append(shortormat.format(weekStartDate));
				 }else{
					 StDateAndEndDate.append(flag != 0 ? separator : "" ).append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate));
				 }
					
			} catch (Exception e) {}
		        
		        flag++;
		}
	}
	String head[] = Constants.TIMESHEET_APPROVAL_MECHANISM_HEADER;
	String[] headers = getHeaders(head, StDateAndEndDate.toString());
	createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 0);
	return createExcelsheet;
	}


}
