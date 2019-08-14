package com.bis.sourcelead.timesheet.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimesheetReportsDTO;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetReportService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class TimesheetReportServiceImpl implements TimesheetReportService {


	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private EmployeesService employeesService;
	
	
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
	
	@Value("${sourceleadUrl}")
    String sourceleadUrl;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetReportServiceImpl.class);

	@SuppressWarnings("deprecation")
	@Override
	public File prepareTimeSheetExport(TimesheetReportsDTO timesheetReportsDTO,User loginUser,OrgLocations orgLocations,String globalUser) throws Exception {
		File file = null;
		StringBuilder weekFrequencyDates = new StringBuilder(); 
		//DateFormat format = Constants.DF_DMY;
		 //DateFormat  format = LoginSessionController.DATEFORMAT;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(timesheetReportsDTO.getFrequencyMonth(),timesheetReportsDTO.getFrequencyYear());
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 //Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 //Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();
		 int tdSpanLength = 6;
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate),orgLocations.getDateFormat());
		if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
				  tdSpanLength =tdSpanLength+1;
			        Date weekStartDate;
			        Date weekEndDate;
				try {
					 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
					 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
					 displayWeeksList.put(weekStartDate, weekEndDate);
					 if(weekStartDate.equals(weekEndDate)){
						weekFrequencyDates.append(shortormat.format(weekStartDate)).append(",");
					 }else{
						 weekFrequencyDates.append((shortormat.format(weekStartDate))).append("-").append(shortormat.format(weekEndDate)).append(",");
					 }
				} catch (Exception e) {
					logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
				}
			}
		}
		
		String hed = weekFrequencyDates.toString();
		String[] dateHeaders =hed.split(",");
		LinkedList<String> headersLinkedList = new LinkedList<>();
		String[] firstheaders = {"Employee Name","Employee Email","Phnone No","Emp Type","Rate","Project","Client","Vendor","Frequency"};
		String[] secondHeader ={"Total","Total Billable","Total Non-Billable","ProjectToDate","Project Hours Billable","Project Hours Non-Billable","LastMonth","LastMonth Billable","LastMonth  Non-Billable","Stage"};
		
		
				headersLinkedList.addAll(Arrays.asList(firstheaders));
				headersLinkedList.addAll(Arrays.asList(dateHeaders));
				headersLinkedList.addAll(Arrays.asList(secondHeader));
		List<String> headderList = new ArrayList<String>();
		for(String headerArray :headersLinkedList){
			headderList.add(headerArray);
		}
		String [] head = new String[headderList.size()];
		String [] header = headderList.toArray(head);
		
	    Document document = new Document(PageSize.A2.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    
	    StringBuilder sbTsReports =  new StringBuilder();
	    sbTsReports.append("timesheetSheet_").append(dateFormat.format(new Date()));
	    StringBuilder fileSB = new StringBuilder();
	    file = File.createTempFile(fileSB.append(orgLocations.getOrgLocationName()).append("_TimeSheetReports_").append(timesheetReportsDTO.getFrequencyMonth()).append("-").append(timesheetReportsDTO.getFrequencyYear()).append("-").toString(),".pdf");
	    file.setReadable(true);

	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        StringBuilder userStyle = new StringBuilder();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			BaseColor myColor = WebColors.getRGBColor("#000000");
			userStyle.append(orgLocations.getOrgLocationName()).append("-").append(timesheetReportsDTO.getFrequencyMonth()).append("-").append(timesheetReportsDTO.getFrequencyYear()).append(" ").append("TimeSheetReports");
			
			StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgLocations.getOrgLocationName()).append("-").append(timesheetReportsDTO.getFrequencyMonth()).append("-").append(timesheetReportsDTO.getFrequencyYear()).append(" ").append("TimeSheetReports").toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
			
	        boldFont.setColor(myColor);
	        PdfPTable table = new PdfPTable(header.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(26);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
	        if(header.length > 24){
            table.setWidths(new int[]{5,5,5,5,5,5,5,5,5,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5});
	        }else{
	        table.setWidths(new int[]{5,5,5,5,5,5,5,5,5,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5});	
	        }
            table.addCell(cell1);
            for (int i = 0; i < header.length; i++) {
	            String headerString = header[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(1f);
	            cell.setPhrase(new Phrase(headerString.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
	            cell.setBackgroundColor(myBorderColor);
	            table.addCell(cell);
	        }
            table.completeRow();
            ResponseList<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(null,startDate, endDate, null,null,null,orgLocations.getId(), loginUser.getId(), timesheetReportsDTO.getSearchValue(),null, 0,0,"reports",globalUser);
            
            java.util.List<String[]> reportList = new ArrayList<>();
            for(ProjectMembers promembers:projectMembers.getData()){
            	reportList.add(prepareTimesheetReportData(promembers,orgLocations.getId(),header,displayWeeksList,loginUser,startDate,endDate));
            }
            for (String[] tsreportList: reportList) {
                for (int i = 0; i < tsreportList.length; i++) {
                    String datum = tsreportList[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
            document.addTitle("TimeSheet Reports Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	    }catch (DocumentException e){
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    }finally {
	        document.close();
	    }
		return file;
	}
	
	private String[] prepareTimesheetReportData(ProjectMembers projectMember,Long locationId, String[] header, LinkedHashMap<Date, Date> displayWeeksList, User loginUser, Date startDate, Date endDate) throws Exception {
		 ProjectMembers pm = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
		String timeSheetReportsArray[] = new String[header.length];
		 User consultantUser = null;
		 Long workLocationId = 0l;
		 StringBuilder nameAndId = new StringBuilder();
		    if(pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getWorkLocation() != null && pm.getProjectMemberCandidteId().getWorkLocation().getId() != null) {
				workLocationId = pm.getProjectMemberCandidteId().getWorkLocation().getId();
			 }
			if(null != pm.getProjectMemberCandidteId()){
				        nameAndId.append(projectMember.getProjectMemberCandidteId().getFullName());
				        consultantUser = pm.getProjectMemberCandidteId().getConsultantUser();
				        Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(consultantUser.getId(),locationId);
						if(employee!= null &&employee.getEmployeeId() != null && StringUtils.isNotBlank(employee.getEmployeeId())){
							nameAndId.append("-").append(employee.getEmployeeId()).toString();
						}
						timeSheetReportsArray[0] =	nameAndId.toString();	
						
			}else{
					timeSheetReportsArray[0] = nameAndId.append("--").toString();	
			}
			if(projectMember.getProjectMemberCandidteId() !=null && StringUtils.isNotEmpty(projectMember.getProjectMemberCandidteId().getEmail())){
				timeSheetReportsArray[1] = projectMember.getProjectMemberCandidteId().getEmail();
			 }else{
				 timeSheetReportsArray[1] = "--";
			 }
			if(projectMember.getProjectMemberCandidteId() !=null && StringUtils.isNotBlank(projectMember.getProjectMemberCandidteId().getMobilePhone() )){
				 timeSheetReportsArray[2] = projectMember.getProjectMemberCandidteId().getMobilePhone();
			 }else{
				 timeSheetReportsArray[2] = "--";
			 }
			
			 if(projectMember.getProjectMemberCandidteId() != null && StringUtils.isNotEmpty(projectMember.getProjectMemberCandidteId().getDesignationType())){
				 timeSheetReportsArray[3] = projectMember.getProjectMemberCandidteId().getDesignationType();
			 }else{
				 timeSheetReportsArray[3] = "--";
			 }
			 if(projectMember.getPayRate() != null){
				 StringBuilder rateString = new StringBuilder();
				 timeSheetReportsArray[4] = rateString.append(projectMember.getPayRate()).append("/").append(projectMember.getPayRateType()).toString();
			 }else{
				 timeSheetReportsArray[4] = "--";
			 }
			
			 if(projectMember.getProjects().getProjectName() !=null){
				timeSheetReportsArray[5] = projectMember.getProjects().getProjectName();
			 }else{
				 timeSheetReportsArray[5] = "--";
			 }
			 
			 if(null != projectMember.getProjectClientsId().getOrgRelationId()){
			   	 OrgRelations orgRelations = orgRelationsService.getOrgRelationById(projectMember.getProjectClientsId().getOrgRelationId().getId());
			  	 if(null != projectMember.getProjectClientsId().getOrgRelationId().getLocationId() && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId().longValue() == locationId.longValue()){
							if(orgRelations.getResponderLocationId() != null){
								timeSheetReportsArray[6] = orgRelations.getResponderLocationId().getOrgLocationName();
								
							}
						}
						if(null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() && null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId() && projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId().longValue() == locationId.longValue()){
							if(orgRelations.getLocationId() != null){
								timeSheetReportsArray[6] = orgRelations.getLocationId().getOrgLocationName();
							}
						}
			    }else{
			    	timeSheetReportsArray[6] = "--";
				}
			 
			if(null != projectMember.getProjectMemberCandidteId() && null !=  projectMember.getProjectMemberCandidteId().getOrgRelationId() && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
				OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
				timeSheetReportsArray[7] = orgRelation.getResponderLocationId().getOrgLocationName(); 
			}
			
			if(projectMember.getProjects().getInvoiceFrequency() !=null){
				timeSheetReportsArray[8] = projectMember.getProjects().getInvoiceFrequency();
			 }else{
				 timeSheetReportsArray[8] = "--";
			 }
			 int count = 9;
			 SubmissionApproverHierarchy sah= null;
			 float monthTotalHours = 0f;
			 float  monthBillableTotalHours = 0f;
			 float monthNonBillableTotalHours = 0f;
			 Submission_ApprovalStatus combinedApprovalStatus = null; 
			 sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),locationId,projectMember.getId(),startDate,endDate,"");
			if(displayWeeksList != null && !displayWeeksList.isEmpty()){
	    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
	    	    	 //String approvalClass = "class='draft'";
	    	    	 float weekTotalHours = 0f;
	    	    	 float  weekBillableHours = 0f;
	    	    	 float weekNonBillableHours = 0f;
	    	    	 HashSet<Date> pmhDateList = projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), entry.getKey(), entry.getValue());
		    	     Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
	    	    	 if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
	    	    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
	    	    	}
	    	    	if(pmhDateList == null  ||  pmhDateList.isEmpty()){
		    	    	approvalStatus = Submission_ApprovalStatus.NOTAPPLICABLE;
		    	    }
		    	    if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
		    	    	 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
		    	    }
	    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
	    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
	    	    	}
	    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
	    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
	    	    	}
	    	    	if(approvalStatus == Submission_ApprovalStatus.REJECTED){
	    	        	if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.REJECTED){
	    	        		combinedApprovalStatus = Submission_ApprovalStatus.REJECTED;
	    	        	}
	    	        	else{
	    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
	    	        	}
	 					
	 				}
	 				else if(approvalStatus == Submission_ApprovalStatus.NEW){
	 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.NEW){
	 						combinedApprovalStatus = Submission_ApprovalStatus.NEW;
	 					}
	 					else{
	    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
	    	        	}
	 					
	 				}
	 				else if(approvalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
	 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
	 						combinedApprovalStatus = Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL;
	 					}
	 					else{
	    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
	    	        	}
	 					
	 				}
	 				else if(approvalStatus == Submission_ApprovalStatus.APPROVED){
	 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.APPROVED){
	 						combinedApprovalStatus = Submission_ApprovalStatus.APPROVED;
	 					}
	 					else{
	    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
	    	        	}
	 					
	 				 }
	    	    	
	    	    	Float leaveTotal = 0f;
	    	    	Float leaveBillableTotal = 0f;
	    	    	Float leaveNonBillableTotal = 0f;
		    	    if(consultantUser != null) {
		    	    	 Map<String,Float> leaveTotalMap = appliedLeaveDateService.getAppliedLeaveDateAndHolidayHoursByUserId(consultantUser.getId(), entry.getKey(), entry.getValue(),workLocationId);
			    	     if(leaveTotalMap != null && !leaveTotalMap.isEmpty()) {
			    	    	 leaveTotal = leaveTotalMap.get("TOTAL");
			    	    	 leaveBillableTotal = leaveTotalMap.get("BILLABLE");
			    	    	 leaveNonBillableTotal = leaveTotalMap.get("NONBILLABLE");
			    	     }
			    	     
		    	    }
		    	    if(leaveTotal != 0) {
		    	    	weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, leaveTotal);
	   	    	     }
		    	     if(leaveBillableTotal != 0) {
		    	    	 weekBillableHours = CommonUtil.getCombinedHours(weekBillableHours, leaveBillableTotal);
		    	     }
	                 if(leaveNonBillableTotal != 0) {
	                	 weekNonBillableHours = CommonUtil.getCombinedHours(weekNonBillableHours, leaveNonBillableTotal);
		    	     }
	    	    	 monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
		    	     monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableHours);
		    	     monthNonBillableTotalHours =  CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableHours);
		    	     
	    	    	 StringBuilder listSb = new StringBuilder();
	    	         timeSheetReportsArray[count] = listSb.append(CommonUtil.getHoursDisplayFormat(weekTotalHours,null)).toString();
	    	         timeSheetReportsArray[count+1] = listSb.append(CommonUtil.getHoursDisplayFormat(weekBillableHours,null)).toString();
	    	         timeSheetReportsArray[count+2] = listSb.append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours,null)).toString();
	    	         count++;
	    	     }
	            }
				timeSheetReportsArray[count] =  CommonUtil.getHoursDisplayFormat(monthTotalHours,null);
				timeSheetReportsArray[count+1] = CommonUtil.getHoursDisplayFormat(monthBillableTotalHours,null);
				timeSheetReportsArray[count+2] = CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours,null);
				
				timeSheetReportsArray[count+3] = CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours(),null);
				timeSheetReportsArray[count+4] = CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours(),null);
				timeSheetReportsArray[count+5] = CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours(),null);
				
				timeSheetReportsArray[count+6] =CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours(),null);
				timeSheetReportsArray[count+7] =CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours(),null);
				timeSheetReportsArray[count+8] =CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours(),null);
				
				timeSheetReportsArray[count+9] = combinedApprovalStatus.toString();
			
		return timeSheetReportsArray;
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
	public File prepareTimeSheetExportExcel(TimesheetReportsDTO timesheetReportsDTO, User loginUser,OrgLocations orgLocations,String globalUser) throws IOException, Exception {
		File file = null;
		
		StringBuilder weekFrequencyDates = new StringBuilder(); 
		//DateFormat format = Constants.DF_DMY;
		 //DateFormat  format = LoginSessionController.DATEFORMAT;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(timesheetReportsDTO.getFrequencyMonth(),timesheetReportsDTO.getFrequencyYear());
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 //Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 //Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();
		 int tdSpanLength = 6;
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate),orgLocations.getDateFormat());
		if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
				  tdSpanLength =tdSpanLength+1;
			        Date weekStartDate;
			        Date weekEndDate;
				try {
					 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
					 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
					 displayWeeksList.put(weekStartDate, weekEndDate);
					 if(weekStartDate.equals(weekEndDate)){
						weekFrequencyDates.append(shortormat.format(weekStartDate)).append(",");
					 }else{
						 weekFrequencyDates.append((shortormat.format(weekStartDate))).append("-").append(shortormat.format(weekEndDate)).append(",");
					 }
				} catch (Exception e) {
					logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
				}
			}
		}
		
		String replacedOrgName = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		timesheetReportsExcelBorder(createExcelworkbook);
		StringBuilder sheetName = new StringBuilder();
		
		Pattern special = Pattern.compile ("[a-zA-Z0-9]*");
		Matcher hasSpecial = special.matcher(orgLocations.getOrgLocationName());
		if(!hasSpecial.matches()){
		 replacedOrgName = orgLocations.getOrgLocationName().replaceAll("\\\\", " ").replaceAll("/", " ").replaceAll("[^a-zA-Z0-9][-]", "");
		}else{
		 replacedOrgName = orgLocations.getOrgLocationName();
		}
		Sheet createExcelsheet = createExcelworkbook.createSheet(sheetName.append(replacedOrgName).append("-").append(timesheetReportsDTO.getFrequencyMonth()).append("-").append(timesheetReportsDTO.getFrequencyYear()).append(" ").append("TimeSheetReports").toString());
		Row writeRow = null;
		createExcelsheet = gettimesheetReportsExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,weekFrequencyDates);
		
		
		int column = 0;
		 ResponseList<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(null,startDate, endDate, null,null,null,orgLocations.getId(), loginUser.getId(), timesheetReportsDTO.getSearchValue(),null, 0,0,"reports",globalUser);
		 for(ProjectMembers promembers:projectMembers.getData()){
			 writeRow = createExcelsheet.createRow(column + 1);
			 createExcelsheet = prepareTimesheetReportDataExcel(promembers,displayWeeksList,loginUser,createExcelsheet,writeRow,startDate,endDate,orgLocations);
			 column++;
         }
		 StringBuilder fileSB = new StringBuilder();
		 file = File.createTempFile(fileSB.append(replacedOrgName).append("_TimeSheetReports_").append(timesheetReportsDTO.getFrequencyMonth()).append("-").append(timesheetReportsDTO.getFrequencyYear()).append("-").toString(),".xls");
		 file.setReadable(true);
		 FileOutputStream fos = new FileOutputStream(file);
		 try {
			createExcelworkbook.write(fos);
			fos.close();
		 }catch (IOException e) {
			e.printStackTrace();
		 }
		return file;
	}

	private Sheet gettimesheetReportsExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow,Workbook createExcelworkbook, StringBuilder weekFrequencyDates) {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String hed = weekFrequencyDates.toString();
		String[] dateHeaders =hed.split(",");
		LinkedList<String> headersLinkedList = new LinkedList<>();
		String[] firstheaders = {"Emp Id","Employee Name","Employee Email","Phnone No","Emp Type","Rate","Project","Client","Vendor","Frequency"};
		String[] secondHeader ={"Total","Total Billable","Total Non-Billable","ProjectToDate","Project Hours Billable","Project Hours Non-Billable","LastMonth","LastMonth Billable","LastMonth  Non-Billable","Stage"};
				headersLinkedList.addAll(Arrays.asList(firstheaders));
				headersLinkedList.addAll(Arrays.asList(dateHeaders));
				headersLinkedList.addAll(Arrays.asList(secondHeader));
		List<String> headderList = new ArrayList<String>();
		for(String headerArray :headersLinkedList){
			headderList.add(headerArray);
		}
		String [] head = new String[headderList.size()];
		String [] header = headderList.toArray(head);
		String headers [] = header;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
	   return createExcelsheet;
	}
	
	private Sheet prepareTimesheetReportDataExcel(ProjectMembers projectMember, LinkedHashMap<Date, Date> displayWeeksList,User loginUser, Sheet createExcelsheet, Row writeRow, Date startDate, Date endDate, OrgLocations orgLocations) throws Exception {
		createExcelsheet.getDefaultRowHeight();
		
		ProjectMembers  consultantProjectMembers = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
		User consultantUser = null;
		StringBuilder sbid = new StringBuilder();
		Long workLocationId = 0l;
		if(projectMember.getProjectMemberCandidteId() != null && projectMember.getProjectMemberCandidteId().getWorkLocation() != null && projectMember.getProjectMemberCandidteId().getWorkLocation().getId() != null) {
			workLocationId = consultantProjectMembers.getProjectMemberCandidteId().getWorkLocation().getId();
		 }
		if(projectMember.getProjectMemberCandidteId()!= null) {
			 consultantUser = consultantProjectMembers.getProjectMemberCandidteId().getConsultantUser(); 
			 Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(consultantUser.getId(),orgLocations.getId());
		  if(employee != null && StringUtils.isNotEmpty(employee.getEmployeeId())) {
		    	 writeRow.createCell(0).setCellValue(sbid.append(employee.getEmployeeId()).toString());
				 createExcelsheet.autoSizeColumn(0);
		 }
		if(null != projectMember.getProjectMemberCandidteId()){
			 writeRow.createCell(1).setCellValue(projectMember.getProjectMemberCandidteId().getFullName()); 
			 createExcelsheet.autoSizeColumn(1);
		}
		if(null != projectMember.getProjectMemberCandidteId()){
			 writeRow.createCell(2).setCellValue(projectMember.getProjectMemberCandidteId().getEmail());
			 createExcelsheet.autoSizeColumn(2);
		}
		if( projectMember.getProjectMemberCandidteId().getMobilePhone() != null ){
			 writeRow.createCell(3).setCellValue(projectMember.getProjectMemberCandidteId().getMobilePhone());
			 createExcelsheet.autoSizeColumn(3);
		}
		if(null != projectMember.getProjectMemberCandidteId()){
			 writeRow.createCell(4).setCellValue(projectMember.getProjectMemberCandidteId().getDesignationType());
			 createExcelsheet.autoSizeColumn(4);
		}
		if( projectMember.getPayRate() != null){
			StringBuilder rateString = new StringBuilder();
			 writeRow.createCell(5).setCellValue(rateString.append(projectMember.getPayRate()).append("/").append(projectMember.getPayRateType()).toString());
			 createExcelsheet.autoSizeColumn(5);
		}
	}
		if(null != projectMember.getProjects().getProjectName()){
			 writeRow.createCell(6).setCellValue(projectMember.getProjects().getProjectName()); 
			 createExcelsheet.autoSizeColumn(6);
		}
		
		
		if(null != projectMember.getProjectMemberCandidteId() && null !=  projectMember.getProjectMemberCandidteId().getOrgRelationId()){
			OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
				if(orgRelation.getLocationId().getId().longValue()  == orgLocations.getId().longValue()) {
					writeRow.createCell(7).setCellValue(" "); 
					 createExcelsheet.autoSizeColumn(7);
				}else {
					writeRow.createCell(7).setCellValue(orgRelation.getLocationId().getOrgLocationName()); 
					 createExcelsheet.autoSizeColumn(7);
				} 
			
		}
		
		if(projectMember.getProjectClientsId() != null ){
			 OrgRelations orgRelation = projectMember.getProjectClientsId().getOrgRelationId();
			 if(projectMember.getProjectClientsId().getOrgRelationId() != null) { 
			 OrgLocations orglocations = orgLocationService.getOrgLocations(orgRelation.getResponderLocationId().getId());
			 writeRow.createCell(7).setCellValue(orglocations.getOrg().getOrgName());
			 createExcelsheet.autoSizeColumn(7);
			 	}else {
			 		 writeRow.createCell(7).setCellValue(" ");
				}
			}else {
				 writeRow.createCell(7).setCellValue(" ");
			}
		
		
		if(null != projectMember.getProjectMemberCandidteId() && null !=  projectMember.getProjectMemberCandidteId().getOrgRelationId() && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
			OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
			writeRow.createCell(8).setCellValue(orgRelation.getResponderLocationId().getOrgLocationName()); 
			 createExcelsheet.autoSizeColumn(8);
		}
		
		
		if(null != projectMember.getProjects().getInvoiceFrequency()){
			 writeRow.createCell(9).setCellValue(projectMember.getProjects().getInvoiceFrequency()); 
			 createExcelsheet.autoSizeColumn(9);
		}
		int count = 10;
		 SubmissionApproverHierarchy sah= null;
		 float monthTotalHours = 0f;
		 float  monthBillableTotalHours = 0f;
		 float monthNonBillableTotalHours = 0f;
		 Submission_ApprovalStatus combinedApprovalStatus = null;
    	sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),orgLocations.getId(),projectMember.getId(),startDate,endDate,"");
		if(displayWeeksList != null && !displayWeeksList.isEmpty()){
    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
    	     float weekTotalHours = 0f;
   	    	 float  weekBillableHours = 0f;
   	    	 float weekNonBillableHours = 0f;
   	    	    HashSet<Date> pmhDateList = projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), entry.getKey(), entry.getValue());
		        Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
		    	if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
		    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
		    	}
		    	if(pmhDateList == null  ||  pmhDateList.isEmpty()){
    	    		approvalStatus = Submission_ApprovalStatus.NOTAPPLICABLE;
    	    	}
	    	    if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
	    	    	 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
	    	    }
    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
    	    	}
    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
    	    	}
    	    	if(approvalStatus == Submission_ApprovalStatus.REJECTED){
    	        	if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.REJECTED){
    	        		combinedApprovalStatus = Submission_ApprovalStatus.REJECTED;
    	        	}
    	        	else{
    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
    	        	}
 					
 				}
 				else if(approvalStatus == Submission_ApprovalStatus.NEW){
 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.NEW){
 						combinedApprovalStatus = Submission_ApprovalStatus.NEW;
 					}
 					else{
    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
    	        	}
 					
 				}
 				else if(approvalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
 						combinedApprovalStatus = Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL;
 					}
 					else{
    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
    	        	}
 					
 				}
 				else if(approvalStatus == Submission_ApprovalStatus.APPROVED){
 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.APPROVED){
 						combinedApprovalStatus = Submission_ApprovalStatus.APPROVED;
 					}
 					else{
    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
    	        	}
 					
 				}
    	    	
    	    	Float leaveTotal = 0f;
    	    	Float leaveBillableTotal = 0f;
    	    	Float leaveNonBillableTotal = 0f;
	    	    if(consultantUser != null) {
	    	    	 Map<String,Float> leaveTotalMap = appliedLeaveDateService.getAppliedLeaveDateAndHolidayHoursByUserId(consultantUser.getId(), entry.getKey(), entry.getValue(),workLocationId);
		    	     if(leaveTotalMap != null && !leaveTotalMap.isEmpty()) {
		    	    	 leaveTotal = leaveTotalMap.get("TOTAL");
		    	    	 leaveBillableTotal = leaveTotalMap.get("BILLABLE");
		    	    	 leaveNonBillableTotal = leaveTotalMap.get("NONBILLABLE");
		    	     }
		    	     
	    	    }
	    	     if(leaveTotal != 0) {
	    	    	weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, leaveTotal);
   	    	     }
	    	     if(leaveBillableTotal != 0) {
	    	    	 weekBillableHours = CommonUtil.getCombinedHours(weekBillableHours, leaveBillableTotal);
	    	     }
                 if(leaveNonBillableTotal != 0) {
                	 weekNonBillableHours = CommonUtil.getCombinedHours(weekNonBillableHours, leaveNonBillableTotal);
	    	     }

    	    	 monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
	    	     monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableHours);
	    	     monthNonBillableTotalHours =  CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableHours);
	    	     
    	    	
    	    	 StringBuilder listSb = new StringBuilder();
    	    	 writeRow.createCell(count).setCellValue(listSb.append(CommonUtil.getHoursDisplayFormat(weekTotalHours,null)).toString());
    	    	 createExcelsheet.autoSizeColumn(count);
    	    	 writeRow.createCell(count+1).setCellValue( listSb.append(CommonUtil.getHoursDisplayFormat(weekBillableHours,null)).toString());
    	    	 createExcelsheet.autoSizeColumn(count+1);
    	    	 writeRow.createCell(count+2).setCellValue(listSb.append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours,null)).toString());
    	    	 createExcelsheet.autoSizeColumn(count+2);
    	         count++;
    	         
    	     }
     }
		
		
		writeRow.createCell(count).setCellValue( CommonUtil.getHoursDisplayFormat(monthTotalHours,null));
		createExcelsheet.autoSizeColumn(count);
		writeRow.createCell(count+1).setCellValue( CommonUtil.getHoursDisplayFormat(monthBillableTotalHours,null));
		 createExcelsheet.autoSizeColumn(count+1);
		writeRow.createCell(count+2).setCellValue( CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours,null));
		 createExcelsheet.autoSizeColumn(count+2);
		writeRow.createCell(count+3).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours(),null));
		createExcelsheet.autoSizeColumn(count+3);
		writeRow.createCell(count+4).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours(),null));
		createExcelsheet.autoSizeColumn(count+4);
		writeRow.createCell(count+5).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours(),null));
		createExcelsheet.autoSizeColumn(count+5);
		writeRow.createCell(count+6).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours(),null));
		createExcelsheet.autoSizeColumn(count+6);
		writeRow.createCell(count+7).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours(),null));
		createExcelsheet.autoSizeColumn(count+7);
		writeRow.createCell(count+8).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours(),null));
		createExcelsheet.autoSizeColumn(count+8);
		writeRow.createCell(count+9).setCellValue(combinedApprovalStatus.toString());
		createExcelsheet.autoSizeColumn(count+9);
		return createExcelsheet;
	}
	
	public void timesheetReportsExcelBorder(Workbook createExcelworkbook){
	XSSFCellStyle style = (XSSFCellStyle) createExcelworkbook.createCellStyle();
	style.setBorderTop(BorderStyle.MEDIUM);
	style.setBorderBottom(BorderStyle.MEDIUM);
	style.setBorderLeft(BorderStyle.MEDIUM);
	style.setBorderRight(BorderStyle.MEDIUM);
	}
}