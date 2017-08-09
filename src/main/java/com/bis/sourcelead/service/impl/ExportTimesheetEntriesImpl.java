package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
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
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.bis.sourcelead.web.LoginSessionController;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ListItem;
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
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;

	@Override
	public File exportTimesheets(ProjectMembers projectMember, String frequencyStartDate,String frequencyEndDate,OrgLocations orgLocations) throws Exception {

		File file = null;
		Workbook wb = new XSSFWorkbook();
		//DateFormat format = LoginSessionController.DATEFORMAT;
		
		StringBuilder fName=new StringBuilder();
		Sheet sheet = wb.createSheet(fName.append(projectMember.getEmployee().getUser().getFirstName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
		Row writeRow = null;
		
		CellStyle style = wb.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
		
		Date employeeStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),frequencyStartDate.replaceAll("/", "-"));
		Date employeeEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),frequencyEndDate.replaceAll("/", "-"));
		
		SubmissionApproverHierarchy sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(employeeStartDate, employeeEndDate, projectMember.getId());
		Submission_Status submissionStatus = sah.getSubmissionStatus();
		String submissionStatusStr = submissionStatus.getValue();
		int count = 9;
		//int lastRow = 0;
	     Map<String,String> tabFrequency = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(frequencyStartDate, frequencyEndDate);
		 if(tabFrequency != null && !tabFrequency.isEmpty()){
			 Float billableHours = 0f;
			 Float nonBillableHours =  0f;
			 Long grandTotalHours = 0l;
			for (Map.Entry<String, String> entry : tabFrequency.entrySet()) {
				 String key = entry.getKey();
			     String value = entry.getValue();
			     
				 Date weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
				 Date weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
				 
				 String weekMondayDate = null;
				 
				 TimesheetSubmissions ts = timesheetSubmissionsService.getAllEmployeeTimesheetSubmissionsByProjectMemberId(weekStartDate, weekEndDate, projectMember.getId());
				 if(null != ts){
					 if(null != ts.getBillableHours()){
						 Float weekBillable = Float.parseFloat(ts.getBillableHours());
						 float tsHours = weekBillable;
					     float tsHoursValue = (int) tsHours;
					     float tsMinutesValue = tsHours - tsHoursValue;
					     billableHours = billableHours + (tsHoursValue* 60)+(tsMinutesValue*100);
					 }
					 if(null != ts.getNonBillableHours()){
						 
						 Float weekNonBillable = Float.parseFloat(ts.getNonBillableHours());
						 float tsHours = weekNonBillable;
					     float tsHoursValue = (int) tsHours;
					     float tsMinutesValue = tsHours - tsHoursValue;
					     nonBillableHours = nonBillableHours + (tsHoursValue* 60)+(tsMinutesValue*100);
					 }
					 /*if(null != ts.getTotalHours()){
						 grandTotalHours = grandTotalHours+(Double.parseDouble(ts.getTotalHours())*60);
					 }*/
				 
				 int column = 0;
	    		// lastRow = count;
				 
				 Long monTotHours = 0L;
				 Long tueTotHours = 0L;
				 Long wedTotHours = 0L;
				 Long thuTotHours = 0L;
				 Long friTotHours = 0L;
				 Long satTotHours = 0L;
				 Long sunTotHours = 0L;
				 int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key));
		    		switch (dayOfTheDateWithStartDate) {
		    	        case 1:  weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,6);
		    	                 break;
		    	        case 2:  weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,0);
		    	                 break;
		    	        case 3:  weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,1);
		    	             break;
		    	        case 4:  weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,2);
		    	            break;
		    	        case 5: weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,3);
		    	            break;
		    	        case 6:  weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,4);
		    	              break;
		    	        case 7: weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,5);
		    	             break;
		    	        default:   weekMondayDate = CalendarUtil.getPreviousDateByDecrementor(key,0);
		    	                 break;
		           }
		    		writeRow = sheet.createRow(count);
		    		Cell datecell0 = writeRow.createCell(0);
		    		datecell0.setCellValue("Date"); 
		    		datecell0.setCellStyle(style);
		    		
		    		Cell datecell1 = writeRow.createCell(1);
		    		datecell1.setCellValue(weekMondayDate); 
		    		datecell1.setCellStyle(style);
		    		
		    		String tuesDayDate =  CalendarUtil.getNextDate(weekMondayDate);
		    		Cell datecell2 = writeRow.createCell(2);
		    		datecell2.setCellValue(tuesDayDate); 
		    		datecell2.setCellStyle(style);
		    		
		    		String WednesDayDate =  CalendarUtil.getNextDate(tuesDayDate);
		    		Cell datecell3 = writeRow.createCell(3);
		    		datecell3.setCellValue(WednesDayDate); 
		    		datecell3.setCellStyle(style);
		    		
		    		String ThursDayDate =  CalendarUtil.getNextDate(WednesDayDate);
		    		Cell datecell4 = writeRow.createCell(4);
		    		datecell4.setCellValue(ThursDayDate); 
		    		datecell4.setCellStyle(style);
		    		
		    		String FriDayDate =  CalendarUtil.getNextDate(ThursDayDate);
		    		Cell datecell5 = writeRow.createCell(5);
		    		datecell5.setCellValue(FriDayDate); 
		    		datecell5.setCellStyle(style);
		    		
		    		String SaturdayDate =  CalendarUtil.getNextDate(FriDayDate);
		    		Cell datecell6 = writeRow.createCell(6);
		    		datecell6.setCellValue(SaturdayDate); 
		    		datecell6.setCellStyle(style);
		    		
		    		String SundayDate =  CalendarUtil.getNextDate(SaturdayDate);
		    		Cell datecell7 = writeRow.createCell(7);
		    		datecell7.setCellValue(SundayDate);
		    		datecell7.setCellStyle(style);
		    		
		    		count = count+1;
		    		//Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
		    		Collection<TimesheetEntries>  timesheetEntriesList =   getAllTimesheetEntriesBySubmissionId(ts.getId());
		    		Long  totalHours =0l;
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
									    writeRow = sheet.createRow(count); 
									      //lastRow = count+timesheetEntriesList.size();
								              
							            	  if(timesheetEntries.getProjectTask() != null){
							     				 writeRow.createCell(0).setCellValue(timesheetEntries.getProjectTask().getTaskName()); 
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
							            	  
							            	  count++;
								            }
								        totalHours = monTotHours+tueTotHours+wedTotHours+thuTotHours+friTotHours+satTotHours+sunTotHours;
										writeRow = sheet.createRow(count);
										Cell cell1 = writeRow.createCell(0);
										cell1.setCellValue("Total Hours");
										CellStyle style1 =getTotalStyle(wb);
								   	    cell1.setCellStyle(style1);
										writeRow.createCell(1).setCellValue(getTotalHours(monTotHours));
										writeRow.createCell(2).setCellValue(getTotalHours(tueTotHours));
										writeRow.createCell(3).setCellValue(getTotalHours(wedTotHours));
										writeRow.createCell(4).setCellValue(getTotalHours(thuTotHours));
										writeRow.createCell(5).setCellValue(getTotalHours(friTotHours));
										writeRow.createCell(6).setCellValue(getTotalHours(satTotHours));
										writeRow.createCell(7).setCellValue(getTotalHours(sunTotHours));
										Cell cell8 = writeRow.createCell(8);
										cell8.setCellValue(getTotalHours(totalHours));
										cell8.setCellStyle(style1);
										count++;
						          }
			                 
						 grandTotalHours = grandTotalHours + totalHours;
						 StringBuilder fileName=new StringBuilder();
						 String billable = getTotalHours((long) (Math.round(billableHours)));
						 String nonBillable = getTotalHours( (long) (Math.round(nonBillableHours)));
						 String grandtotal = getTotalHours(grandTotalHours);
						 sheet = getExcelSheetWithHeader(sheet, writeRow, wb,frequencyStartDate,frequencyEndDate,projectMember.getEmployee().getUser(),grandtotal,projectMember.getProjects().getProjectName(),submissionStatusStr,billable,nonBillable);
						file = new File(fileName.append(projectMember.getEmployee().getUser().getFirstName()).append("_").append(frequencyStartDate).append(" to ").append(frequencyEndDate).append(".xls").toString());
						FileOutputStream fos = new FileOutputStream(file);
						try {
							wb.write(fos);
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				 }
		     }
		 }
			   else{
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
	

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, String weekStartDate, String weekEndDate, User user, String totalHours, String projectName, String submissionStatusStr, String billableHours, String nonbillableHours) {

		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
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
		 cell10.setCellValue(totalHours); 
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
	
	
	public CellStyle getTotalStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		
		Font font = wb.createFont();
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		return style;
	}
	
	@Override
	public File timeSheetApprovelExport(User user, OrgLocations orgLocations, Date startDate, Date endDate,String timesheetFrequency, String client, String stage,String frequencyMonth,String weekFrequency) throws Exception {
		Long clientId;
		if(!client.equalsIgnoreCase("") && !client.equalsIgnoreCase("All")){
		 clientId = Long.parseLong(client) ;
		}else{
		  clientId = null;
		}
		
		 List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, startDate, endDate, clientId, stage, orgLocations.getId(), user.getId());
		 HashSet<ProjectMembers> freshProjectMembers = new HashSet<>();
         if(projectMembers != null && !projectMembers.isEmpty()){
        	
        	if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
	        	for(ProjectMembers pm :projectMembers){
	        		Date biweeklyCurrentStartMonDayDate = timesheetApprovalFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(pm.getProjects());
	        	    if(CalendarUtil.getNumberOfDays(biweeklyCurrentStartMonDayDate, startDate)%14==0){
	        	    	freshProjectMembers.add(pm);
	        	    }
	        	
	        	}
        	}
        	 else{
		        	freshProjectMembers = new HashSet<>(projectMembers);
		        }
         }
	        
		
		String[] headers;
		int flag = 0;
			StringBuilder StDateAndEndDate = new StringBuilder("");
			StringBuilder separator = new StringBuilder(",");
		    //DateFormat format = Constants.DF_DMY;
			//DateFormat format = LoginSessionController.DATEFORMAT;
		    DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		        
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate),commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
			if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
				for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
		    		 String  k = entry.getKey();
		    		 String  v = entry.getValue();
				        Date weekStartDate;
				        Date weekEndDate;
					try {
						 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
						 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
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
	    //DateFormat dateFormat = LoginSessionController.DATEFORMAT;

	    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append(orgLocations.getOrgLocationName()).append("_TimeSheetApproval_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");

	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        com.itextpdf.text.Font boldFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, com.itextpdf.text.Font.BOLD);

	        BaseColor myBordColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myBordColor);
	        StringBuilder sbReqStyle = new StringBuilder();
	        sbReqStyle.append(orgLocations.getOrgLocationName()).append(" ");

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgLocations.getOrgLocationName()).toString(),boldFont);

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
	        	 table.setWidths(new int[]{5,5,5,5,5,3,3,3,3,3,3,3,5});
	        }else if(timesheetFrequency.equalsIgnoreCase("Semi-monthly")){
            table.setWidths(new int[]{3,5,5,3,5,5,3,3,3,3,5});
	        }else if(timesheetFrequency.equalsIgnoreCase("Bi-weekly")){
	        	table.setWidths(new int[]{3,5,5,3,5,5,3,3,3,5});
	        }else{
	        	table.setWidths(new int[]{3,5,5,3,5,5,3,3,5});
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
	        for(ProjectMembers proMembers : freshProjectMembers){
	        	document =  prepareHeadres(orgLocations,proMembers,file);
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
		int k=5;
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
		    DateFormat format = LoginSessionController.DATEFORMAT;
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
		timeSheetApprovalArray[3] = projMembers.getProjects().getOrgRelationId().getResponderLocationId().getOrgLocationName();
	}else{
		timeSheetApprovalArray[3] = "--";
	}
	if(projMembers.getProjects().getProjectName() != null){
		timeSheetApprovalArray[4] = projMembers.getProjects().getProjectName();
	}else{
		timeSheetApprovalArray[4] = "--";
	}
	
	int count = 5;
		LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
			Float totalHours = 0f;
		    Float estimatedTotalHours = 0f;
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
			if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
				if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
					for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
						
						long diff = CalendarUtil.getNumberOfDays(entry.getKey(), entry.getValue());
		    	    	  Float weekTotal = 0f;
		    	    	  if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 7){
		    	    		  weekTotal = (float) ((diff)*8);
		    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
		    	    	  }
		    	    	  else if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 1){
		    	    		  weekTotal = (float) ((diff-1)*8);
		    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
		    	    	  }
		    	    	  else if(CalendarUtil.getDayOfTheDate(entry.getKey()) == 2){
		    	    		  weekTotal = (float) ((diff+1)*8);
		    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
		    	    	  }
						if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							totalHours = CommonUtil.getCombinedHours(totalHours, sah.getWeekTotals().get(entry.getKey()));
							timeSheetApprovalArray[count] = (sah.getWeekTotals().get(entry.getKey()).toString()) + "/"+weekTotal;
						} else {
							timeSheetApprovalArray[count] = "0/"+weekTotal;
						}
						count++;
					}
				}
			}
	     if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
	    	 timeSheetApprovalArray[count]   = totalHours+"/"+estimatedTotalHours;
	     }else{
	    	 timeSheetApprovalArray[count] = "0.0/"+estimatedTotalHours;
	     }
		if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
			timeSheetApprovalArray[count+1] = sah.getSubmissionApprovalStatus().toString();
		}else{
			timeSheetApprovalArray[count+1] = "--";
		}
		if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
			timeSheetApprovalArray[count+2] = sah.getActionPerformedBy();
		}else{
			timeSheetApprovalArray[count+2] = "--";
		}
			return timeSheetApprovalArray;
	}

	private LinkedHashMap<Date, Date> getTotalCountFromStartAndEndDate(Date startDate,Date endDate) throws Exception {
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		DateFormat format = LoginSessionController.DATEFORMAT;
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

	private Document prepareHeadres(OrgLocations loginOrgLocation,ProjectMembers proMembers, File file) {
	    Document document = new Document(PageSize.LETTER.rotate());
	    StringBuilder orderedListStringBuilder=new StringBuilder();
	    try {
	    	PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        com.itextpdf.text.Font boldFont = new  com.itextpdf.text.Font( com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18,  com.itextpdf.text.Font.BOLD);
	        BaseColor myBordColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myBordColor);
	        StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ");
            Paragraph para = new Paragraph(orderedListStringBuilder.append(loginOrgLocation.getOrgLocationName()).toString(),boldFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
            com.itextpdf.text.List orderedList = new  com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
            StringBuilder reqNameBuilder=new StringBuilder();
            StringBuilder locBuilder=new StringBuilder();
	        orderedList.add(new ListItem(locBuilder.append("Project Frequency :").append(proMembers.getProjects().getInvoiceFrequency()).toString()));
	        document.add(orderedList);
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
		return document;
}
	
	
	
	
	
	
	@Override
	public File timeSheetApprovelExportExcel(User user, OrgLocations orgLocations,Date startDate,Date endDate,String timesheetFrequency,String client,String stage,String frequencyMonth,String weekFrequency) throws Exception {
		File file= null;
		Long clientId;
		if(!client.equalsIgnoreCase("") && !client.equalsIgnoreCase("All")){
		 clientId = Long.parseLong(client) ;
		}else{
		  clientId = null;
		}
		//for creating workbook and sheet in excel.
		Workbook createExcelworkbook = new XSSFWorkbook();
		//DateFormat dateFormat = LoginSessionController.DATEFORMAT;
		StringBuilder excelStringBuilder=new StringBuilder();
		Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append(orgLocations.getOrgLocationName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
		Row writeRow = null;
		// to get list of project members from data base.
		List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, startDate, endDate, clientId, stage, orgLocations.getId(), user.getId());
		HashSet<ProjectMembers> freshProjectMembers = new HashSet<>();
        if(projectMembers != null && !projectMembers.isEmpty()){
       	if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
	        	for(ProjectMembers pm :projectMembers){
	        		Date biweeklyCurrentStartMonDayDate = timesheetApprovalFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(pm.getProjects());
	        	    if(CalendarUtil.getNumberOfDays(biweeklyCurrentStartMonDayDate, startDate)%14==0){
	        	    	freshProjectMembers.add(pm);
	        	    }
	        	
	        	}
       	}
       	 else{
		        	freshProjectMembers = new HashSet<>(projectMembers);
		        }
        }
		
		for (ProjectMembers proMembers : freshProjectMembers){
			createExcelsheet = getExcelSheetWithHeaderTimesheetApproval(createExcelsheet, writeRow, createExcelworkbook,proMembers,startDate,endDate,orgLocations);
		}
		// Iteration of project members list.
		int column = 0;
		for (ProjectMembers proMembers : projectMembers) {
			writeRow = createExcelsheet.createRow(column + 5);
			createExcelsheet = createXlsSheetTimesheetApproval(createExcelsheet, writeRow,proMembers,client,timesheetFrequency,startDate,endDate,user);
			column++;
		}
		// output the file using file output stream.
		StringBuilder tempFileBuilder=new StringBuilder();
		file = File.createTempFile(tempFileBuilder.append(orgLocations.getOrgLocationName()).append("_TimeSheet_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
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
			createExcelsheet.setColumnWidth(0, 10000);
		}
		if (projMembers.getEmployee().getUser().getEmail() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getEmail())) {
			writeRow.createCell(1).setCellValue(projMembers.getEmployee().getUser().getEmail());
			createExcelsheet.setColumnWidth(1, 10000);
		}
		if (projMembers.getEmployee().getUser().getMobilePhone() != null && !"".equalsIgnoreCase(projMembers.getEmployee().getUser().getMobilePhone())) {
			writeRow.createCell(2).setCellValue(projMembers.getEmployee().getUser().getMobilePhone());
			createExcelsheet.setColumnWidth(2, 10000);
		}
		
		if (projMembers.getProjects().getOrgRelationId() != null) {
			writeRow.createCell(3).setCellValue(projMembers.getProjects().getOrgRelationId().getResponderLocationId().getOrgLocationName());
			createExcelsheet.setColumnWidth(3, 10000);
		}
		if (projMembers.getProjects().getProjectName() != null) {
			writeRow.createCell(4).setCellValue(projMembers.getProjects().getProjectName());
			createExcelsheet.setColumnWidth(4, 10000);
		}
		int count = 5;
			LinkedHashMap<Date,Date> displayWeeksList= getTotalCountFromStartAndEndDate(startDate, endDate);
				Float totalHours = 0f;
				Float estimatedTotalHours = 0f;
				SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projMembers.getId(), startDate, endDate);
				if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
					if (displayWeeksList != null && !displayWeeksList.isEmpty()) {
						for (Map.Entry<Date, Date> entry : displayWeeksList.entrySet()) {
							  long diff = CalendarUtil.getNumberOfDays(entry.getKey(), entry.getValue());
			    	    	  Float weekTotal = 0f;
			    	    	  if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 7){
			    	    		  weekTotal = (float) ((diff)*8);
			    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
			    	    	  }
			    	    	  else if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 1){
			    	    		  weekTotal = (float) ((diff-1)*8);
			    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
			    	    	  }
			    	    	  else if(CalendarUtil.getDayOfTheDate(entry.getKey()) == 2){
			    	    		  weekTotal = (float) ((diff+1)*8);
			    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
			    	    	  }
							if (sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()) {
							   totalHours = CommonUtil.getCombinedHours(totalHours, sah.getWeekTotals().get(entry.getKey()));
								writeRow.createCell(count).setCellValue(sah.getWeekTotals().get(entry.getKey()).toString() + "/"+weekTotal);
							} else {
								writeRow.createCell(count).setCellValue( "0/"+weekTotal);
							}
							count++;
						}
					}
				}
				
				 if(totalHours != null && !"".equalsIgnoreCase(totalHours.toString())){
					 writeRow.createCell(count).setCellValue( totalHours.toString()+"/"+estimatedTotalHours);
			     }else{
			    	 writeRow.createCell(count).setCellValue( "0.0/"+estimatedTotalHours);
			     }
				if(sah.getSubmissionApprovalStatus() != null && !"".equalsIgnoreCase(sah.getSubmissionApprovalStatus().toString())){
					writeRow.createCell(count+1).setCellValue( sah.getSubmissionApprovalStatus().toString());
				}else{
					writeRow.createCell(count+1).setCellValue( "--");
				}
				if(sah.getActionPerformedBy() != null && !"".equalsIgnoreCase(sah.getActionPerformedBy())){
					writeRow.createCell(count+2).setCellValue(sah.getActionPerformedBy());
				}else{
					writeRow.createCell(count+2).setCellValue( "--");
				}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeaderTimesheetApproval(Sheet createExcelsheet, Row writeRow,Workbook createExcelworkbook, ProjectMembers proMembers,Date startDate,Date endDate, OrgLocations orgLocations) throws Exception {
	CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		int flag = 0;
		StringBuilder StDateAndEndDate = new StringBuilder("");
		StringBuilder separator = new StringBuilder(",");
		//DateFormat format = LoginSessionController.DATEFORMAT;
	    DateFormat shortormat = Constants.SHORT_DF_STD_DM;
	Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate),commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
	if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
		for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
    		 String  k = entry.getKey();
    		 String  v = entry.getValue();
		        Date weekStartDate;
		        Date weekEndDate;
			try {
				 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
				 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
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
    prepareHeaders(orgLocations,proMembers,createExcelsheet,writeRow,style);
	createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 4);
	return createExcelsheet;
	}

	private void prepareHeaders(OrgLocations orgLocations, ProjectMembers proMembers, Sheet createExcelsheet,Row writeRow, CellStyle style) {
		/*writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Project Name :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
		cell2.setCellValue(proMembers.getProjects().getProjectName());
		cell2.setCellStyle(style);*/

		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("Project Frequency :");
		cell3.setCellStyle(style);

		
		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(proMembers.getProjects().getInvoiceFrequency());
		cell4.setCellStyle(style);
	}

	
}
