package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.beans.TimesheetHours;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
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
import com.bis.sourcelead.web.LoginSessionController;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
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
	
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
    /**
     * 
     * @param user
     * @param navigation
     * @param startDate
     * @param orgLocations
     * @return 
     * @throws Exception 
     */
	public File exportTimesheetPdf(ProjectMembers projectMember, String frequencyStartDate,String frequencyEndDate, OrgLocations orgLocations) throws Exception {

		//DateFormat format = LoginSessionController.DATEFORMAT;
		Map<String,java.util.List<TimesheetHours>> map = new HashMap();
		Map<String,Date> weekDates = new HashMap<>();
		
		File file = null;
		
		Date employeeStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),frequencyStartDate.replaceAll("/", "-"));
		Date employeeEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),frequencyEndDate.replaceAll("/", "-"));
		
		StringBuilder sb = new StringBuilder();
		sb.append(projectMember.getEmployee().getUser().getFirstName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));
		file = File.createTempFile(sb.toString(),".pdf");
		file.setReadable(true);
		
		Document document = new Document();
        try {
        	int [] width = new int[]{0,5,0};
        	PdfPTable table1 = new PdfPTable(3);
        	table1.setWidthPercentage(100);
	        table1.setWidths(width);
        java.util.List<String> timeSheetStatus = new ArrayList<>();
        
        
		
		SubmissionApproverHierarchy sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(employeeStartDate, employeeEndDate, projectMember.getId());
		Submission_Status submissionStatus = sah.getSubmissionStatus();
		String submissionStatusStr = submissionStatus.getValue();
		
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
				 
				 java.util.List<TimesheetHours> hoursList = new ArrayList<>();
				 int column = 0;
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
									
									TimesheetHours timesheetHours = new TimesheetHours();
							            	  if(timesheetEntries.getProjectTask() !=null){
													timesheetHours.setTaskName(timesheetEntries.getProjectTask().getTaskName());
												}
								            	  
								              if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
								            	  if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
														timesheetHours.setMonDayHours(timesheetEntries.getMonHours());
													}
								            	  monTotHours = monTotHours + getTotalHoursFormDayHours(timesheetEntries.getMonHours());
								            	  monHours =  monHours + getTotalHoursFormDayHours(timesheetEntries.getMonHours());
								              }
								              if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
								            	  if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
														timesheetHours.setTueDayHours(timesheetEntries.getTueHours());
													}
								            	  tueTotHours = tueTotHours + getTotalHoursFormDayHours(timesheetEntries.getTueHours());
								            	  tueHours = tueHours + getTotalHoursFormDayHours(timesheetEntries.getTueHours());
								              }
								              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
//								            	  cell3.setCellComment(getCellComment(sheet,wb,timesheetEntries.getTuecomments()));
								              }
//								              Cell cell4 = writeRow.createCell(4);
								              if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
//								            	  cell4.setCellValue(timesheetEntries.getWedHours()); 
								            	  if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
														timesheetHours.setWedDayHours(timesheetEntries.getWedHours());
													}
								            	  wedTotHours = wedTotHours + getTotalHoursFormDayHours(timesheetEntries.getWedHours());
								            	  wedHours = wedHours + getTotalHoursFormDayHours(timesheetEntries.getWedHours());
								              }
								              if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
								            	  if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
														timesheetHours.setThuDayHours(timesheetEntries.getThuHours());
													}
								            	  thuTotHours = thuTotHours + getTotalHoursFormDayHours(timesheetEntries.getThuHours());
								            	  thuHours = thuHours + getTotalHoursFormDayHours(timesheetEntries.getThuHours());
								              }
								              if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
								            	  if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
														timesheetHours.setFriDayHours(timesheetEntries.getFriHours());
													}
								            	  friTotHours = friTotHours + getTotalHoursFormDayHours(timesheetEntries.getFriHours());
								            	  friHours = friHours + getTotalHoursFormDayHours(timesheetEntries.getFriHours());
								              }
								              if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
								            	  if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
														timesheetHours.setSatDayHours(timesheetEntries.getSatHours());
													}
								            	  satTotHours = satTotHours + getTotalHoursFormDayHours(timesheetEntries.getSatHours());
								            	  satHours = satHours + getTotalHoursFormDayHours(timesheetEntries.getSatHours());
								              } 
								              if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
								            	  if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
														timesheetHours.setSunDayHours(timesheetEntries.getSunHours());
													}
								            	  sunTotHours = sunTotHours + getTotalHoursFormDayHours(timesheetEntries.getSunHours());
								            	  sunHours = sunHours + getTotalHoursFormDayHours(timesheetEntries.getSunHours());
								              } 
							            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
//							            		  cell8.setCellComment(getCellComment(sheet,wb,timesheetEntries.getFricomments()));
							            	  }  
							            	  weekTotHours = monHours + tueHours + wedHours + thuHours + friHours + satHours + sunHours;
							            	  timesheetHours.setWeekTotal(getTotalHours(weekTotHours));
								              column++;
								              hoursList.add(timesheetHours);
								     }
								map.put(projectMember.getProjects().getProjectName(), hoursList);
						  }
					Long totalHours = monTotHours+tueTotHours+wedTotHours+thuTotHours+friTotHours+satTotHours+sunTotHours;
//					grandTotalHours = getTotalHours(totalHours);
					grandTotalHours = grandTotalHours + totalHours;
					String billable = getTotalHours((long) (Math.round(billableHours)));
					String nonBillable = getTotalHours((long) (Math.round(nonBillableHours)));
					String grandtotal = getTotalHours(grandTotalHours);
					document =  prepareHeadres(projectMember.getEmployee().getUser(),frequencyStartDate,frequencyEndDate,file,submissionStatusStr,orgLocations,projectMember.getProjects().getProjectName(),billable,nonBillable,grandtotal);
					int [] width2 = new int[]{5,5,5,5,5,5,5,5,5};
					String[] headers2 = Constants.TIMESHEET_EXCEL_HEADERS2;
					int [] width3 = new int[]{1};
					String[] headers3 = Constants.TIMESHEET_EXCEL_HEADERS3;
					int k=0;
					 for (Map.Entry<String, java.util.List<TimesheetHours>> entry1 : map.entrySet())
				        {
						 java.util.List<String[]> innerTableArrayData = new ArrayList<String[]>();
					       String [] weekDayDate = getWeekDayDates(weekMondayDate);
						    innerTableArrayData.add(weekDayDate);
				        	 table1.addCell(entry1.getKey());
				        	 
				        	 PdfPTable table2 = getPdfTable(headers2,width2,k);
				        	 PdfPTable table3 = getPdfTable(headers3,width3,k);
//				        	 java.util.List<Employee> = entry.getValue();
				        	for(TimesheetHours hours:entry1.getValue()){
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
				        		if(hours.getWeekTotal() !=null){
				        			innerTable[8] = hours.getWeekTotal().toString();
				        		}else{
				        			innerTable[8] = "--";
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
					 int [] width4 = new int[]{5,5,5,5,5,5,5,5,5};
					 PdfPTable table4 = new PdfPTable(9);
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
					 table4.addCell(getTotalHours(totalHours));
					 table1.addCell("");
					 table1.addCell(table4);
					 table1.addCell(getTotalHours(totalHours));
			        document.addTitle("Pdf table created for TimeSheet Entries..!!");
			        table1.setSpacingBefore(15f);
			        document.add(table1);
			        logger.info("Pdf table created !!");
			}
		  }
        }
        }catch(Exception ex){
        	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(ex));
        }finally{
        	 document.close();
        }
		return file;
	}

	private String[] getWeekDayDates(String mondayDate) throws ParseException {
//			String mondayDate = weekMondayDate;
			String tuesDayDate = CalendarUtil.getNextDate(mondayDate);
			String wedDayDate = CalendarUtil.getNextDate(tuesDayDate);
			String thusDayDate = CalendarUtil.getNextDate(wedDayDate);
			String friDayDate = CalendarUtil.getNextDate(thusDayDate);
			String satDayDate = CalendarUtil.getNextDate(friDayDate);
			String sunDayDate = CalendarUtil.getNextDate(satDayDate);
			
			 
			String innerDateValues[] = { "", mondayDate, tuesDayDate, wedDayDate, thusDayDate, friDayDate,satDayDate, sunDayDate, "" };
	return innerDateValues;
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

	private Document prepareHeadres(User user, String weekStartDate, String weekEndDate, File file, String submissionStatusStr, OrgLocations orgLocations,String projectName,String billableHours,String nonbillableHours, String totalHours) {
		 Document document = new Document(PageSize.LETTER.rotate());
		    try {
		    	StringBuilder nameSb = new StringBuilder();
		    	StringBuilder weekSb = new StringBuilder();
		    	StringBuilder sb = new StringBuilder();
		    	PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();
		        
		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myBordColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myBordColor);
		        StringBuilder sbPipeHearder = new StringBuilder();
		        sbPipeHearder.append(orgLocations.getOrgLocationName()).append(" ").append("TimeSheet");
	            Paragraph para = new Paragraph(sb.append(orgLocations.getOrgLocationName()).append(" TimeSheet.").toString(),boldFont);
	            para.setAlignment(Element.ALIGN_CENTER);
	            para.add("\n\n");
	            document.add(para);
	            

	            PdfPTable table = new PdfPTable(2);
	            table.setWidthPercentage(120);
	            table.setHorizontalAlignment(Element.ALIGN_LEFT);
	            table.setWidths(new int[]{10,10});
	            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	            if(user.getFirstName()!=null && user.getLastName()!=null){
	            	nameSb.append(user.getFirstName()).append(" ").append(user.getLastName());
	            }else{
	            	nameSb.append(user.getFirstName());
	            }
	            table.addCell("Name : "+nameSb.toString());
	            table.addCell("Status : "+submissionStatusStr);
	            table.addCell("Email Id : "+user.getEmail());
	            table.addCell("Billabe Hours : "+billableHours);
	            table.addCell("Phone Number : "+user.getMobilePhone());
	            table.addCell("Non-Billabe Hours : "+nonbillableHours);
	            table.addCell("Project : "+projectName);
	            table.addCell("Total Hours : "+totalHours);
	            table.addCell("Frequency : "+weekSb.append(weekStartDate).append("_").append(weekEndDate).toString());
	            table.addCell(" ");
	            document.add(table);

	            
		        // Create an instance of PdfPTable. After that we transform the header and
	            /*List orderedList = new List(List.ORDERED);
	            
	            if(user.getFirstName()!=null && user.getLastName()!=null){
	            	nameSb.append(user.getFirstName()).append(" ").append(user.getLastName());
	            }else{
	            	nameSb.append(user.getFirstName());
	            }
		        orderedList.add(new ListItem("Name :"+nameSb.toString()));
		        orderedList.add(new ListItem("Email Id :"+user.getEmail()));
		        orderedList.add(new ListItem("Phone Number :"+user.getMobilePhone()));
		        for(TimesheetSubmissions ts : timesheetSubmissionsList){
		        	orderedList.add(new ListItem("Project :"+ts.getProjectMembers().getProjects().getProjectName()));
		        }
		        orderedList.add(new ListItem("Week : "+weekSb.append(weekStartDate).append("_").append(weekEndDate).toString()));
		        document.add(orderedList);*/
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