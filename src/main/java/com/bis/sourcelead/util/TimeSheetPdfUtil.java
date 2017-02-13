package com.bis.sourcelead.util;

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
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.beans.TimesheetHours;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.ExportTimesheetEntriesImpl;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author Rajashekar
 * write time sheet entries data as pdf file
 * @since 28-12-2016
 * 
 *
 */

@Component
public class TimeSheetPdfUtil {
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
	
    /**
     * 
     * @param user
     * @param navigation
     * @param startDate
     * @param orgLocations
     * @return 
     * @throws Exception 
     */
	public File exportTimesheetPdf(User user, String navigation, String startDate, OrgLocations orgLocations) throws Exception {

		Map<String,java.util.List<TimesheetHours>> map = new HashMap();
		File file = null;
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
		   StringBuilder sb = new StringBuilder();
		   sb.append(user.getFirstName()).append("_").append(weekStartDate).append(" to ").append(weekEndDate);
		   file = File.createTempFile(sb.toString(),".pdf");
		    file.setReadable(true);
		   
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
        Document document =  prepareHeadres(user,weekStartDate,weekEndDate,file);
        try {
        	int [] width = new int[]{1,5,1};
        	PdfPTable table1 = new PdfPTable(3);
        	table1.setWidthPercentage(100);
	        table1.setWidths(width);
        java.util.List<String[]> timeSheetList = new ArrayList<>();
        java.util.List<String> timeSheetStatus = new ArrayList<>();
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			 int column = 0;
			String projectName ="";
			isThereAnyApproverApproved = new ArrayList<Boolean>();
			java.util.List<TimesheetHours> hoursList =  new ArrayList();
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
							TimesheetHours timesheetHours = new TimesheetHours();
						              if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
						            	  if(timesheetEntries.getProjectTask().getTaskName() !=null && !"".equalsIgnoreCase(timesheetEntries.getProjectTask().getTaskName())){
												timesheetHours.setTaskName(timesheetEntries.getProjectTask().getTaskName());
											}
						              }
						              if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
						            	  if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
												timesheetHours.setMonDayHours(timesheetEntries.getMonHours());
											}
						            	  monTotHours = monTotHours + getTotalHoursFormDayHours(timesheetEntries.getMonHours());
						              }
						              if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
						            	  if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
												timesheetHours.setTueDayHours(timesheetEntries.getTueHours());
											}
						            	  tueTotHours = tueTotHours + getTotalHoursFormDayHours(timesheetEntries.getTueHours());
						              }
						              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
//						            	  cell3.setCellComment(getCellComment(sheet,wb,timesheetEntries.getTuecomments()));
						              }
//						              Cell cell4 = writeRow.createCell(4);
						              if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
//						            	  cell4.setCellValue(timesheetEntries.getWedHours()); 
						            	  if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
												timesheetHours.setWedDayHours(timesheetEntries.getWedHours());
											}
						            	  wedTotHours = wedTotHours + getTotalHoursFormDayHours(timesheetEntries.getWedHours());
						              }
						              if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
						            	  if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
												timesheetHours.setThuDayHours(timesheetEntries.getThuHours());
											}
						            	  thuTotHours = thuTotHours + getTotalHoursFormDayHours(timesheetEntries.getThuHours());
						              }
						              if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
						            	  if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
												timesheetHours.setFriDayHours(timesheetEntries.getFriHours());
											}
						            	  friTotHours = friTotHours + getTotalHoursFormDayHours(timesheetEntries.getFriHours());
						              }
						              if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
						            	  if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
												timesheetHours.setSatDayHours(timesheetEntries.getSatHours());
											}
						            	  satTotHours = satTotHours + getTotalHoursFormDayHours(timesheetEntries.getSatHours());
						              } 
						              if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
						            	  if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
												timesheetHours.setSunDayHours(timesheetEntries.getSunHours());
											}
						            	  sunTotHours = sunTotHours + getTotalHoursFormDayHours(timesheetEntries.getSunHours());
						              } 
					            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
//					            		  cell8.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
					            	  }  
						              column++;
						              hoursList.add(timesheetHours);
						     }
						              if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
						            	  timeSheetStatus.add(submissionStatusStr);
						            	  if(submissionStatusStr !=null && !"".equalsIgnoreCase(submissionStatusStr)){
						            		  timeSheetStatus.add(submissionStatusStr);
											}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
							        	 timeSheetStatus.add(submissionStatusStr);
							        	if(submissionStatusStr !=null && !"".equalsIgnoreCase(submissionStatusStr)){
							        		 timeSheetStatus.add(submissionStatusStr);
										}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
							        	 timeSheetStatus.add(submissionStatusStr);
							        	if(submissionStatusStr !=null && !"".equalsIgnoreCase(submissionStatusStr)){
							        		 timeSheetStatus.add(submissionStatusStr);
										}
							        	if(isSah == null ){
							        		 timeSheetStatus.add(submissionStatusStr);
							        		if(submissionStatusStr !=null && !"".equalsIgnoreCase(submissionStatusStr)){
							        			 timeSheetStatus.add(submissionStatusStr);
											}
							        	}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
							        	 timeSheetStatus.add(submissionStatusStr);
							        	if(submissionStatusStr !=null && !"".equalsIgnoreCase(submissionStatusStr)){
							        		 timeSheetStatus.add(submissionStatusStr);
										}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
							        	 timeSheetStatus.add(submissionStatusStr);
							        	if(submissionStatusStr !=null && !"".equalsIgnoreCase(submissionStatusStr)){
							        		 timeSheetStatus.add(submissionStatusStr);
										}
							        }
				  }
			map.put(projectName, hoursList);
			
			}
		Long totalHours = monTotHours+tueTotHours+wedTotHours+thuTotHours+friTotHours+satTotHours+sunTotHours;
		int [] width2 = new int[]{5,5,5,5,5,5,5,5};
		String[] headers2 = Constants.TIMESHEET_EXCEL_HEADERS2;
		int [] width3 = new int[]{1};
		String[] headers3 = Constants.TIMESHEET_EXCEL_HEADERS3;
		int k=0;
		 for (Map.Entry<String, java.util.List<TimesheetHours>> entry : map.entrySet())
	        {
			 java.util.List<String[]> innerTableArrayData = new ArrayList();
	        	 table1.addCell(entry.getKey());
	        	 PdfPTable table2 = getPdfTable(headers2,width2,k);
	        	 PdfPTable table3 = getPdfTable(headers3,width3,k);
//	        	 java.util.List<Employee> = entry.getValue();
	        	for(TimesheetHours hours:entry.getValue()){
	        		String innerTable [] = new String[headers2.length];
	        		if(hours.getTaskName() != null && !"".equalsIgnoreCase(hours.getTaskName())){
	        			innerTable[0] = hours.getTaskName();
	        		}else{
	        			innerTable[0] = "--";
	        		}
	        		if(hours.getMonDayHours() !=null && !"".equalsIgnoreCase(hours.getMonDayHours())){
	        			innerTable[1] = hours.getMonDayHours();
	        		}else{
	        			innerTable[1] = "--";
	        		}
	        		if(hours.getTueDayHours() !=null && !"".equalsIgnoreCase(hours.getTueDayHours())){
	        			innerTable[2] = hours.getTueDayHours();
	        		}else{
	        			innerTable[2] = "--";
	        		}
	        		if(hours.getWedDayHours() !=null && !"".equalsIgnoreCase(hours.getWedDayHours())){
	        			innerTable[3] = hours.getWedDayHours();
	        		}else{
	        			innerTable[3] = "--";
	        		}
	        		if(hours.getThuDayHours() !=null && !"".equalsIgnoreCase(hours.getThuDayHours())){
	        			innerTable[4] = hours.getThuDayHours();
	        		}else{
	        			innerTable[4] = "--";
	        		}
	        		if(hours.getFriDayHours() !=null && !"".equalsIgnoreCase(hours.getFriDayHours())){
	        			innerTable[5] = hours.getFriDayHours();
	        		}else{
	        			innerTable[5] = "--";
	        		}
	        		if(hours.getFriDayHours() !=null && !"".equalsIgnoreCase(hours.getFriDayHours())){
	        			innerTable[5] = hours.getFriDayHours();
	        		}else{
	        			innerTable[5] = "--";
	        		}
	        		if(hours.getSatDayHours() !=null && !"".equalsIgnoreCase(hours.getSatDayHours())){
	        			innerTable[6] = hours.getSatDayHours();
	        		}else{
	        			innerTable[6] = "--";
	        		}
	        		if(hours.getSunDayHours() !=null && !"".equalsIgnoreCase(hours.getSunDayHours())){
	        			innerTable[7] = hours.getSunDayHours();
	        		}else{
	        			innerTable[7] = "--";
	        		}
	        		innerTableArrayData.add(innerTable);
	        	}
	        	for (String[] timeSheetArray: innerTableArrayData) {
	              for (int i = 0; i < timeSheetArray.length; i++) {
	                  String datum = timeSheetArray[i];
	                  PdfPCell cell2 = new PdfPCell();
	                  cell2.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	                  cell2.setRowspan(5);
	                  table2.addCell(cell2);
	              }
	              table2.completeRow();
	          }
	        	table1.addCell(table2);
	        	if(null != timeSheetStatus && !timeSheetStatus.isEmpty()){
	        		table3.addCell(timeSheetStatus.get(k));
	        	}
	        	table1.addCell(table3);
	        	k++;
	        }
		 int [] width4 = new int[]{5,5,5,5,5,5,5,5};
		 PdfPTable table4 = new PdfPTable(8);
		 table4.setWidths(width4);
		  table4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		 table4.addCell("Total");
		 table4.addCell(getTotalHours(monTotHours));
		 table4.addCell(getTotalHours(tueTotHours));
		 table4.addCell(getTotalHours(wedTotHours));
		 table4.addCell(getTotalHours(thuTotHours));
		 table4.addCell(getTotalHours(friTotHours));
		 table4.addCell(getTotalHours(satTotHours));
		 table4.addCell(getTotalHours(sunTotHours));
		 table1.addCell("");
		 table1.addCell(table4);
		 table1.addCell(getTotalHours(totalHours));
        document.addTitle("Pdf table created for TimeSheet Entries..!!");
        table1.setSpacingBefore(15f);
        document.add(table1);
        logger.info("Pdf table created !!");
        }catch(Exception ex){
        	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(ex));
        }finally{
        	 document.close();
        }
		
		}
		return file;
	}

	private PdfPTable getPdfTable(String[] headers, int[] width, int k) {
		PdfPTable table = new PdfPTable(headers.length);
		try{
			table.setWidthPercentage(100);
	        table.setWidths(width);
	        if(k == 0 ){
	        	   for (int i = 0; i < headers.length; i++) {
	   	            String header = headers[i];
	   	            PdfPCell cell = new PdfPCell();
	   	            cell.setGrayFill(0.9f);
	   	            cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	   	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	   	            cell.setBackgroundColor(myColor);
	   	            table.addCell(cell);
	   	        }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return table;
	}

	private Document prepareHeadres(User user, String weekStartDate, String weekEndDate, File file) {
		 Document document = new Document(PageSize.LETTER.rotate());
		    try {
		    	StringBuilder nameSb = new StringBuilder();
		    	StringBuilder weekSb = new StringBuilder();
		    	PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();
		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor1 = WebColors.getRGBColor("#33caff");
		        boldFont.setColor(myColor1);
		        // Create an instance of PdfPTable. After that we transform the header and
	            List orderedList = new List(List.ORDERED);
	            if(user.getFirstName()!=null && user.getLastName()!=null){
	            	nameSb.append(user.getFirstName()).append(" ").append(user.getLastName());
	            }else{
	            	nameSb.append(user.getFirstName());
	            }
		        orderedList.add(new ListItem("Name :"+nameSb.toString()));
		        orderedList.add(new ListItem("Email Id :"+user.getEmail()));
		        orderedList.add(new ListItem("Week : "+weekSb.append(weekStartDate).append("_").append(weekEndDate).toString()));

		        document.add(orderedList);
		    }catch(Exception ex){
		    	ex.printStackTrace();
		    }
			return document;
	}

	 public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
			Collection<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(submissionId);
			return list;
			
		}
	 
	 public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
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
	            actualTimeString =  actualHours+"."+startWithZero+actualMinutes;
	            
			}
			return actualTimeString;
		}

}
