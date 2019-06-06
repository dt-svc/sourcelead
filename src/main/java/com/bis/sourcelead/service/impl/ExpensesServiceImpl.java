package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApproverHierarchyDao;
import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ExpensePendingApproveReportView;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.reportsDTO.PendingForExpenseApprovalReportDTO;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.ibm.icu.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author : Pradeep Kumar Reddy
 *
 */

@Service
public class ExpensesServiceImpl implements ExpensesService{
	
	@Autowired
	ExpensesDao expensesDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;	
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private ExpensesApproverHierarchyDao expensesApproverHierarchyDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ExpensesEntriesService expensesEntriesService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesServiceImpl.class);
	
	@Override
	public ResponseList<Expenses> getAllExpenses(OrgLocations orgLoc,User loginUser, String expenseStatus, String startDate, String endDate,String searchValue,int start,int length) throws Exception {
			Date endDateUTC = null;
			Date startDateUTC = null;
			Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLoc.getTimeZoneId()));
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
			if(StringUtils.isNotEmpty(startDate)){
				Date startTime = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate);
				String customStartTimeStr = TZ_DF_CSD.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			}
			Candidate candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLoc.getId());
			if(StringUtils.isNotEmpty(endDate)){
				Date endTime = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endDate);
				String customEndTimeStr = TZ_DF_CED.format(endTime);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				endDateUTC = TZ_DF_TS.parse(endDateString);
			}
			//orgLoc.getId(),loginUser.getId()
		ResponseList<Expenses> expensesList =  expensesDao.getAllExpenses(candidate.getMasterCandidateId().getId(),expenseStatus, startDateUTC, endDateUTC,searchValue,start,length,orgLoc);

		return expensesList;

	}
	
	@Override
	public List<Expenses> getIndividualExpense(OrgLocations orgLoc,User loginUser,String expenseId) {
		List<Expenses> expensesList =  expensesDao.getIndividualExpense(orgLoc.getId(),loginUser.getId(),expenseId);

		return expensesList;

	}
	
	@Override
	public List<ExpensesEntries> getExpensesEntries(String expenseId) {
		List<ExpensesEntries> expensesEntriesList =  expensesDao.getExpensesEntries(expenseId);

		return expensesEntriesList;

	}
	@Override
	public Expenses getExpenseById(long id) {
		return expensesDao.getExpenseById(id);
	}
	
	@Override
	public Expenses getExpenseByExpenseUniqueId(String expenseId, Long orgLocationId,String isGlobalUser) throws Exception {
		return expensesDao.getExpenseByExpenseUniqueId(expenseId,orgLocationId,isGlobalUser);
	}
	@Override
	public File exportMyExpensesToPdf(Expenses expenses, OrgLocations orgLoc, User loginUser,String approverType,String isGlobalUser) throws Exception {
		
		SimpleDateFormat dateFormatter=orgLoc.getLocFullFormDateFormatter();
		String[] myExpensesHeaders = null;
		 if(approverType.equalsIgnoreCase("USER")){
				myExpensesHeaders = Constants.MY_EXPENSES_HEADERS;
		 }
		 if(approverType.equalsIgnoreCase("APPROVER")){
			 	myExpensesHeaders = Constants.MY_EXPENSES_HEADERS_APPROVER;
		 }
		Document document = new Document(PageSize.LETTER.rotate());
		//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    StringBuilder sbExpenses =  new StringBuilder();
		    StringBuilder hedderLineString =  new StringBuilder();
		    sbExpenses.append("Expenses_").append(dateFormatter.format(new Date()));
		    StringBuilder fileSB = new StringBuilder();
		    File file = File.createTempFile(fileSB.append("Expenses_").append(dateFormatter.format(new Date())).toString(),".pdf");
		    file.setReadable(true);
		    
		    try {
		        // Get an instance of PdfWriter and create a Table.pdf file as an output.
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			    Paragraph para = new Paragraph(hedderLineString.append(orgLoc.getOrgLocationName()).toString(),boldFont);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
		        
		        
		       // Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append("EMPLOYEE EXPENSES").append(" ");
	            PdfPTable table = new PdfPTable(myExpensesHeaders.length);
		        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(11);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
		        if(approverType.equalsIgnoreCase("APPROVER")){
		        	table.setWidths(new int[]{5,3,5,3,3,3,7,5,5});
		        }
		        if(approverType.equalsIgnoreCase("USER")){
	            table.setWidths(new int[]{5,3,5,3,3,3,7});
		        }
	            table.addCell(cell1);
		        for (int i = 0; i < myExpensesHeaders.length; i++) {
		            String header = myExpensesHeaders[i];
		            PdfPCell cell = new PdfPCell();
		            cell.setGrayFill(0.9f);
		            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
		            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
		            cell.setBackgroundColor(myBorderColor);
		            table.addCell(cell);
		        }
		            table.completeRow();
			        List<String[]> expensespdfArrayList = new ArrayList<>();
			        List<ExpensesEntries> expenseEntries = getExpensesEntries(expenses.getId().toString());
			        for(ExpensesEntries expEntries :expenseEntries){
			        	expensespdfArrayList.add(prepareMyExpensesStringArray(expEntries,myExpensesHeaders,orgLoc,loginUser,approverType,isGlobalUser));
			        }
			        	
			        for (String[] expensesArray: expensespdfArrayList) {
		                for (int i = 0; i < expensesArray.length; i++) {
		                    String datum = expensesArray[i];
		                    PdfPCell cell = new PdfPCell();
		                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
		                    table.addCell(cell);
		                }
		                table.completeRow();
		            }
			        document.addTitle("My Expenses");
			        table.setSpacingBefore(15f);
			        
			        com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
			        if(approverType.equalsIgnoreCase("USER")){
		            StringBuilder expenseId=new StringBuilder();
		            StringBuilder locBuilder=new StringBuilder();
		            StringBuilder client=new StringBuilder();
		            StringBuilder StDate=new StringBuilder();
		            StringBuilder edDate=new StringBuilder();
		            StringBuilder purposeStringBuilder =new StringBuilder();
		            StringBuilder totalStringBuilder =new StringBuilder();
		            StringBuilder statusStringBuilder = new StringBuilder();
		            ProjectMembers pm= projectMembersService.getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(expenses.getMasterCandidateId().getId(),expenses.getProjects().getId(),orgLoc.getId(),isGlobalUser);
		            
			        orderedList.add(new ListItem(expenseId.append("Expense Id :").append(expenses.getExpenseId()).toString()));
			        orderedList.add(new ListItem(locBuilder.append("Project :").append(pm.getProjects().getProjectName()).toString()));
			        if(pm.getProjectClientsId()!=null && pm.getProjectClientsId().getOrgRelationId()!= null){
		            	orderedList.add(new ListItem(client.append("Client :").append(pm.getProjectClientsId().getOrgRelationId().getClientName()).toString()));	
		            }else{
			    	   orderedList.add(new ListItem(client.append("Client :").append("--").toString()));  
			       }
			        if(null != expenses.getStartDate()){
			        	String StartDate = dateFormatter.format(expenses.getStartDate());
			        	orderedList.add(new ListItem(StDate.append("Start Date :").append(StartDate).toString()));
			        }else{
			        	orderedList.add(new ListItem(StDate.append("Start Date :").append("--").toString()));	
			        }
			        if(null != expenses.getEndDate()){
			        	String EndDate = dateFormatter.format(expenses.getEndDate());
			        	orderedList.add(new ListItem(edDate.append("End Date :").append(EndDate).toString()));
			        }else{
			        	orderedList.add(new ListItem(edDate.append("End Date :").append("--").toString()));
			        }
			        if(null != expenses.getPurpose()){
			        	orderedList.add(new ListItem(purposeStringBuilder.append("Purpose :").append(expenses.getPurpose()).toString()));
			        	}else{
			        		orderedList.add(new ListItem(purposeStringBuilder.append("Purpose :").append("--").toString()));	
			        	}
			      
			        if(expenses.getTotalAmount().doubleValue() > 0){
			        	StringBuilder str = new StringBuilder();
			        	BigDecimal bd = new BigDecimal((expenses.getTotalAmount()));
			        	//bd = bd.setScale(2,BigDecimal.ROUND_UP);
			        	orderedList.add(new ListItem(str.append("TotalAmount :").append(bd).toString()));
			        	}else{
			        		orderedList.add(new ListItem(totalStringBuilder.append("TotalAmount :").append("00.00").toString()));	
			        	}
			        if(null != expenses.getExpenseStatus()){
			        	orderedList.add(new ListItem(statusStringBuilder.append("Status :").append(expenses.getExpenseStatus()).toString()));
			        }else {
			        	orderedList.add(new ListItem(statusStringBuilder.append("Status :").append("--").toString()));	
			        }
			       }else if(approverType.equalsIgnoreCase("APPROVER")){
			    	   StringBuilder expenseId=new StringBuilder();
			    	   	StringBuilder nameBuilder=new StringBuilder();
			    	   	StringBuilder emailBuilder=new StringBuilder();
			            StringBuilder mobileBuilder=new StringBuilder();
			            StringBuilder empType=new StringBuilder();
			            StringBuilder vendor=new StringBuilder();
			            StringBuilder projectName=new StringBuilder();
			            StringBuilder client=new StringBuilder();
			            StringBuilder StDate=new StringBuilder();
			            StringBuilder edDate=new StringBuilder();
			            StringBuilder purposeStringBuilder =new StringBuilder();
			            StringBuilder totalStringBuilder =new StringBuilder();
			            orderedList.add(new ListItem(expenseId.append("Expense Id :").append(expenses.getExpenseId()).toString()));
			            if(expenses.getProjects() != null && expenses.getMasterCandidateId() != null){
			            	ProjectMembers pm= projectMembersService.getProjectMembersByMasterCandidateIdAndProjectIdLocationId(expenses.getMasterCandidateId().getId(),expenses.getProjects().getId(),orgLoc.getId(),isGlobalUser);
			            	
			            	if(pm.getProjectMemberCandidteId()!= null){
			            			if(pm.getProjectMemberCandidteId().getLastName()!= null){
			            				orderedList.add(new ListItem(nameBuilder.append("Employee Name :").
			            						append(pm.getProjectMemberCandidteId().getFirstName()).
			            						append(pm.getProjectMemberCandidteId().getLastName()).toString()));
			            			}else{
			            				orderedList.add(new ListItem(nameBuilder.append("Employee Name :").
			            						append(pm.getProjectMemberCandidteId().getFirstName()).toString()));
			            			}
			            			if(pm.getProjectMemberCandidteId().getEmail()!= null){
			            				 orderedList.add(new ListItem(emailBuilder.append("Email Id :").append(pm.getProjectMemberCandidteId().getEmail()).toString()));	
			            			}else{
			            				orderedList.add(new ListItem(emailBuilder.append("Email Id :").append("--").toString()));
			            			}
			            			if(pm.getProjectMemberCandidteId().getMobilePhone()!= null){
			            				 orderedList.add(new ListItem(mobileBuilder.append("Phone Number :").append(pm.getProjectMemberCandidteId().getMobilePhone()).toString()));	
			            			}else{
			            				orderedList.add(new ListItem(mobileBuilder.append("Phone Number :").append("--").toString()));
			            			}
			            		if(StringUtils.isNotEmpty(pm.getProjectMemberCandidteId().getDesignationType())){
		            				 orderedList.add(new ListItem(empType.append("Employment Type :").append(pm.getProjectMemberCandidteId().getDesignationType()).toString()));	
		            			}else{
		            				orderedList.add(new ListItem(empType.append("Employment Type :").append("--").toString()));
		            			}
			            		
			            		
		            		 if(null != pm.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(pm.getProjectMemberCandidteId().getDesignationType()) && (pm.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||pm.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
		           			   OrgRelations orgRelation = pm.getProjectMemberCandidteId().getOrgRelationId();
		           			   orderedList.add(new ListItem(vendor.append("Vendor :").append(pm.getProjectMemberCandidteId().getDesignationType()).append(",").append(orgRelation.getResponderLocationId().getOrgLocationName()).toString())); 
			           	     }else{
			           	    	 orderedList.add(new ListItem(vendor.append("Vendor :").append("--").toString())); 
			           	     	}
			            		
			            	}
			            	
			            	if(pm.getProjects().getProjectName()!=null){
			            		orderedList.add(new ListItem(projectName.append("Project :").append(pm.getProjects().getProjectName()).toString()));
			            	}else{
			            		orderedList.add(new ListItem(projectName.append("Project :").append("--").toString()));
			            	}
			            
			            if(pm.getProjectClientsId()!=null && pm.getProjectClientsId().getOrgRelationId()!= null){
			            	orderedList.add(new ListItem(client.append("Client :").append(pm.getProjectClientsId().getOrgRelationId().getClientName()).toString()));	
			            }else{
			            	orderedList.add(new ListItem(client.append("Client :").append("--").toString()));
			            }
				        if(null != expenses.getStartDate()){
				        	String StartDate = dateFormatter.format(expenses.getStartDate());
				        	orderedList.add(new ListItem(StDate.append("Start Date :").append(StartDate).toString()));
				        }else{
				        	orderedList.add(new ListItem(StDate.append("Start Date :").append("--").toString()));
				        }
				        if(null != expenses.getEndDate()){
				        	String EndDate = dateFormatter.format(expenses.getEndDate());
				        	orderedList.add(new ListItem(edDate.append("End Date :").append(EndDate).toString()));
				        }else{
				        	orderedList.add(new ListItem(edDate.append("End Date :").append("--").toString()));
				        }
				        if(null != expenses.getPurpose()){
				        	orderedList.add(new ListItem(purposeStringBuilder.append("Purpose :").append(expenses.getPurpose()).toString()));
				        	}else{
				        		orderedList.add(new ListItem(purposeStringBuilder.append("Purpose :").append("--").toString()));
				        	}
				        if( expenses.getTotalAmount().doubleValue() > 0){
				        	StringBuilder str = new StringBuilder();
				    		BigDecimal bd = new BigDecimal((expenses.getTotalAmount()));
				    		//bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );	
				        	orderedList.add(new ListItem(str.append("TotalAmount :").append(bd).toString()));
				        	}else{
				        		orderedList.add(new ListItem(totalStringBuilder.append("TotalAmount :").append("00.00").toString()));
				        	}
			        }
			       }
			        document.add(orderedList);
			        
			        document.add(table);
			        
			        
			        
			        
		    }catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } catch (FileNotFoundException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
	}

	private String[] prepareMyExpensesStringArray(ExpensesEntries expEntries, String[] myExpensesHeaders, OrgLocations orgLoc, User loginUser,String approverType,String isGlobalUser) throws Exception {
		String userArray[] = new String[myExpensesHeaders.length+2];
		SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
				if(null != expEntries.getExpensesCategoryId()){
					if(expEntries.getExpensesCategoryId().getExpensesName() != null && !"".equalsIgnoreCase(expEntries.getExpensesCategoryId().getExpensesName())){	
						   userArray[0] = expEntries.getExpensesCategoryId().getExpensesName();
					   }else{
						   userArray[0] = "--";
					  }	
				}
			if(null != expEntries.getExpenseDate())
			{
				expEntries.setExpenseDateString(dateFormat.format(expEntries.getExpenseDate()));
				 userArray[1] = expEntries.getExpenseDateString();
			}
			if(expEntries.getReceiptIssuedBy() != null && !"".equalsIgnoreCase(expEntries.getReceiptIssuedBy())){	
				   userArray[2] = expEntries.getReceiptIssuedBy();
			   }else{
				   userArray[2] = "--";
			 }
			if(expEntries.getPaymentMode() != null && !"".equalsIgnoreCase(expEntries.getPaymentMode().toString())){	
				   userArray[3] = expEntries.getPaymentMode().getValue();
			   }else{
				   userArray[3] = "--";
			 }
			if(expEntries.getCurrencyType() != null && !"".equalsIgnoreCase(expEntries.getCurrencyType())){	
				   userArray[4] = expEntries.getCurrencyType();
			   }else{
				   userArray[4] = "--";
			 }
			
			if(expEntries.getAmount() != null && !"".equalsIgnoreCase(expEntries.getCurrencyType())){
				StringBuilder amount = new StringBuilder();
				   userArray[5] = amount.append(expEntries.getAmount()).toString();
			   }else{
				   userArray[5] = "--";
			
			/*if(expEntries.getAmount() != 0){
				StringBuilder str = new StringBuilder();
				BigDecimal bd = new BigDecimal(Double.toString(expEntries.getExpensesId().getTotalAmount()));
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );				
				   userArray[5] = str.append(bd).toString();
			   }else{
				   userArray[5] = "00.00";*/
			 }
			
			/*Countries country = countriesService.getCountriesByCountryName(orgLoc.getCountry());
			if(!country.getCurrency().equalsIgnoreCase(expEntries.getCurrencyType())){
				 Float amount = conversionUtil.convert(expEntries.getCurrencyType(),country.getCurrency(),expEntries.getAmount());
				 String countryCode = country.getCurrency();
				 userArray[6] =countryCode+""+amount.toString();
				}else{
					userArray[6] ="--";	
				}*/
			 if(expEntries.getDescription() != null && !"".equalsIgnoreCase(expEntries.getDescription())){	
				   userArray[6] = expEntries.getDescription();
			   }else{
				   userArray[6] = "--";
			 }
			 
			 if(approverType.equalsIgnoreCase("APPROVER")){
				 if(expEntries.getExpensesId() != null){	
					 ExpensesApproverHierarchy  eah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expEntries.getExpensesId().getId(),orgLoc.getId(),isGlobalUser);
					   userArray[7] = eah.getExpenseStatus().getValue().toUpperCase();
				   }else{
					   userArray[7] = "--";
				 } 
				 if(expEntries.getBillable() != null){	
					 if(expEntries.getBillable().equals(true)){
						 userArray[8] = "YES";
					 }
					 else{
						   userArray[8] = "NO";
	 
					 }
				   }else{
					   userArray[8] = "--";
				 }
			 }
			
	return userArray;
	}

	@Override
	public File exportMyExpensesToExcel(Expenses expenseDetails, OrgLocations orgLoc, User loginUser,String approverType,String isGlobalUser) throws Exception {
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrgLocationName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,expenseDetails,approverType,orgLoc,isGlobalUser);
		int column = 0;
		
		List<ExpensesEntries> expenseEntries = getExpensesEntries(expenseDetails.getId().toString());
		
		for (ExpensesEntries expensesEntries : expenseEntries) {
			if(approverType.equalsIgnoreCase("APPROVER")){
				writeRow = createExcelsheet.createRow(column + 14);	
			}else if(approverType.equalsIgnoreCase("USER")){
				writeRow = createExcelsheet.createRow(column + 9);
			}
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, expensesEntries,approverType,orgLoc,isGlobalUser);
			column++;
		}

		 //file = new File(orgLoc.getOrgLocationName() + "_Expenses_" + dateFormat.format(new Date()) + ".xls");
		 file = File.createTempFile(orgLoc.getOrgLocationName() + "_ExpensesApproval_" + dateFormat.format(new Date()),".xls");
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
	
	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ExpensesEntries expEntries, String approverType,OrgLocations orgLocations,String isGlobalUser) {
		if(null != expEntries.getExpensesCategoryId()){
			if(expEntries.getExpensesCategoryId().getExpensesName() != null && !"".equalsIgnoreCase(expEntries.getExpensesCategoryId().getExpensesName())){	
				writeRow.createCell(0).setCellValue(expEntries.getExpensesCategoryId().getExpensesName());
				createExcelsheet.autoSizeColumn(0);
			   }
		}
		
	if(null != expEntries.getExpenseDate()){
		expEntries.setExpenseDateString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expEntries.getExpenseDate()));
		writeRow.createCell(1).setCellValue( expEntries.getExpenseDateString());
		createExcelsheet.autoSizeColumn(1);
	}
	if(expEntries.getReceiptIssuedBy() != null && !"".equalsIgnoreCase(expEntries.getReceiptIssuedBy())){	
		writeRow.createCell(2).setCellValue( expEntries.getReceiptIssuedBy());
		createExcelsheet.autoSizeColumn(2);
	}
	if(expEntries.getPaymentMode() != null && !"".equalsIgnoreCase(expEntries.getPaymentMode().toString())){	
		writeRow.createCell(3).setCellValue(expEntries.getPaymentMode().getValue());
		createExcelsheet.autoSizeColumn(3);
	   }
	if(expEntries.getCurrencyType() != null && !"".equalsIgnoreCase(expEntries.getCurrencyType())){	
		writeRow.createCell(4).setCellValue(expEntries.getCurrencyType());
		createExcelsheet.autoSizeColumn(4);
	   }
	
	if(expEntries.getAmount() != null && !"".equalsIgnoreCase(expEntries.getAmount().toString())){	
		StringBuilder amount1 = new StringBuilder();
		
		writeRow.createCell(5).setCellValue( amount1.append(expEntries.getAmount()).toString());
		createExcelsheet.autoSizeColumn(4);
	   }
	
	if(expEntries.getAmount() != null && !"".equalsIgnoreCase(expEntries.getCurrencyType()))
	
	
	
	/*StringBuilder amount = new StringBuilder();
	if(expEntries.getAmount() != 0){
		StringBuilder str = new StringBuilder();
		BigDecimal bd = new BigDecimal(Double.toString(expEntries.getExpensesId().getTotalAmount()));
		bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );			
		writeRow.createCell(5).setCellValue(str.append(bd).toString());
		createExcelsheet.autoSizeColumn(5);
	   }else{
		writeRow.createCell(5).setCellValue(amount.append("00.00").toString());
		createExcelsheet.autoSizeColumn(5);
	}*/
	 if(expEntries.getDescription() != null && !"".equalsIgnoreCase(expEntries.getDescription())){	
		 writeRow.createCell(6).setCellValue( expEntries.getDescription());
		 createExcelsheet.autoSizeColumn(6);
	   }
	 if(approverType.equalsIgnoreCase("APPROVER")){
		 if(expEntries.getExpensesId().getExpenseStatus()!= null && !"".equalsIgnoreCase(expEntries.getExpensesId().getExpenseStatus().getValue())){	
			 writeRow.createCell(7).setCellValue( expEntries.getExpensesId().getExpenseStatus().getValue());
			 createExcelsheet.autoSizeColumn(7);
		   }
		 if(expEntries.getBillable() != null){	
			 if(expEntries.getBillable().equals(true)){
				 writeRow.createCell(8).setCellValue("YES");
				 createExcelsheet.autoSizeColumn(8);
			 }
			 else{
				   writeRow.createCell(8).setCellValue("NO");
					 createExcelsheet.autoSizeColumn(8);
			 }
		   }
	 }
	
		return createExcelsheet;
	}
	

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, Expenses exp, String approverType,OrgLocations orgLocations,String isGlobalUser) throws Exception {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		writeRow = createExcelsheet.createRow(0);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
			if(approverType.equalsIgnoreCase("APPROVER")){
			prepareHeadderDataForApprover(writeRow,exp,createExcelsheet,style,orgLocations,isGlobalUser);
			}else if(approverType.equalsIgnoreCase("USER")){
			
		 StringBuilder projectName = new StringBuilder();	
		 StringBuilder clientName = new StringBuilder();
		 if(exp.getProjects() != null && exp.getMasterCandidateId() != null){
	      ProjectMembers pm= projectMembersService.getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(exp.getMasterCandidateId().getId(),exp.getProjects().getId(),orgLocations.getId(),isGlobalUser);
	      	  if(pm.getProjects().getProjectName()!= null){    
	      		  projectName.append(pm.getProjects().getProjectName());
	      	  }else{
	      		projectName.append("--");
	      	  }
	          if(pm.getProjectClientsId()!=null && pm.getProjectClientsId().getOrgRelationId()!= null){
	        	  clientName.append(pm.getProjectClientsId().getOrgRelationId().getClientName()).toString();	
	          }else{
	           clientName.append("--").toString();
	          }
	          
		 }
		 
		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Expense id :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
	    cell2.setCellValue(exp.getExpenseId());
		cell2.setCellStyle(style);

		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("Project :");
		cell3.setCellStyle(style);

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(projectName.toString());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Client : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(clientName.toString());
		cell6.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Start Date : ");
		cell7.setCellStyle(style);

		Cell cell8 = writeRow.createCell(1);
		String StrtDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),exp.getStartDate());
		cell8.setCellValue(StrtDate);
		cell8.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell9 = writeRow.createCell(0);
		cell9.setCellValue("End Date : ");
		cell9.setCellStyle(style);

		Cell cell10 = writeRow.createCell(1);
		
		String EndDate = null;
		if(null != exp.getEndDate() && !"".equalsIgnoreCase(exp.getEndDate().toString())){
			EndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),exp.getEndDate());	
		}else{
			EndDate  = ("--");	
		}
		cell10.setCellValue(EndDate);
		cell10.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(5);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell11 = writeRow.createCell(0);
		cell11.setCellValue("Purpose : ");
		cell11.setCellStyle(style);

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(exp.getPurpose());
		cell12.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(6);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell13 = writeRow.createCell(0);
		cell13.setCellValue("TotalAmount : ");
		cell13.setCellStyle(style);

		Cell cell14 = writeRow.createCell(1);
		StringBuilder str = new StringBuilder();
    	BigDecimal bd = new BigDecimal((exp.getTotalAmount()));
    	//bd = bd.setScale(2,BigDecimal.ROUND_UP);
		cell14.setCellValue(str.append(bd).toString());
		cell14.setCellStyle(style);
		
		Cell cell15 = writeRow.createCell(0);
		cell15.setCellValue("Status :");
		cell15.setCellStyle(style);

		Cell cell16 = writeRow.createCell(1);
		cell16.setCellValue(exp.getExpenseStatus().toString());
		cell16.setCellStyle(style);

		writeRow = createExcelsheet.createRow(7);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		
		}
		String[] headers = null;
		
		if(approverType.equalsIgnoreCase("USER")){
			 headers  = Constants.MY_EXPENSES_HEADERS;
			 createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,8);
		}
		if(approverType.equalsIgnoreCase("APPROVER")){
			 headers  = Constants.MY_EXPENSES_HEADERS_APPROVER;
			 createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,13);
		}
		

		return createExcelsheet;
	}
	
	private void prepareHeadderDataForApprover(Row writeRow, Expenses expenseDetails, Sheet createExcelsheet, CellStyle style,OrgLocations orgLocations,String isGlobalUser) throws Exception {
		StringBuilder employeeId=new StringBuilder();
		StringBuilder nameBuilder=new StringBuilder();
	   	StringBuilder emailBuilder=new StringBuilder();
        StringBuilder mobileBuilder=new StringBuilder();
        StringBuilder empType=new StringBuilder();
        StringBuilder vendor=new StringBuilder();
        StringBuilder projectName=new StringBuilder();
        StringBuilder client=new StringBuilder();
        StringBuilder StDate=new StringBuilder();
        StringBuilder edDate=new StringBuilder();
        StringBuilder purposeStringBuilder =new StringBuilder();
        StringBuilder totalStringBuilder =new StringBuilder();
        if(expenseDetails.getExpenseId() != null){
        employeeId.append(expenseDetails.getExpenseId());
        }
        if(expenseDetails.getProjects() != null && expenseDetails.getMasterCandidateId() != null){
        	ProjectMembers pm= projectMembersService.getProjectMembersByMasterCandidateIdAndProjectIdLocationId(expenseDetails.getMasterCandidateId().getId(),expenseDetails.getProjects().getId(),orgLocations.getId(),isGlobalUser);
        	if(pm.getProjectMemberCandidteId()!= null){
        			if(pm.getProjectMemberCandidteId().getLastName()!= null){
        				nameBuilder.append(pm.getProjectMemberCandidteId().getFirstName()).
        						append(pm.getProjectMemberCandidteId().getLastName()).toString();
        			}else{
        				nameBuilder.append(pm.getProjectMemberCandidteId().getFirstName()).toString();
        			}
        			if(pm.getProjectMemberCandidteId().getEmail()!= null){
        				 emailBuilder.append(pm.getProjectMemberCandidteId().getEmail()).toString();	
        			}else{
        				emailBuilder.append("--").toString();
        			}
        			if(pm.getProjectMemberCandidteId().getMobilePhone()!= null){
        				 mobileBuilder.append(pm.getProjectMemberCandidteId().getMobilePhone()).toString();	
        			}else{
        				mobileBuilder.append("--").toString();
        			}
        		if(pm.getProjectMemberCandidteId().getDesignationType()!= null){
    				 empType.append(pm.getProjectMemberCandidteId().getDesignationType()).toString();	
    			}else{
    				empType.append("--").toString();
    			}
        		
        		if(null != pm.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(pm.getProjectMemberCandidteId().getDesignationType()) && (pm.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||pm.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
         			   OrgRelations orgRelation = pm.getProjectMemberCandidteId().getOrgRelationId();
         			   vendor.append(pm.getProjectMemberCandidteId().getDesignationType()).append(",").append(orgRelation.getResponderLocationId().getOrgLocationName()).toString(); 
				} else {
					vendor.append("--").toString();
				}
        		/*if(expenseDetails.getProjectMembers().getEmployee().getOrgLocations()!= null && expenseDetails.getProjectMembers().getEmployee().getOrgRelationId() != null){
        		OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expenseDetails.getProjectMembers().getEmployee().getOrgLocations().getId(), expenseDetails.getProjectMembers().getEmployee().getOrgRelationId().getId());
            	if(orgRelation.getResponderLocationId() !=null && (expenseDetails.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C")||expenseDetails.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099"))){
            		vendor.append(orgRelation.getResponderLocationId().getOrgLocationName()).toString();
            	}else{
            		vendor.append("--").toString();
            	}
        	  }else{
            		vendor.append("--").toString();
            	}*/
        	
        	if(pm.getProjects().getProjectName()!=null){
        		projectName.append(pm.getProjects().getProjectName()).toString();
        	}else{
        		projectName.append("--").toString();
        	}
        
	        if(pm.getProjectClientsId()!=null && pm.getProjectClientsId().getOrgRelationId()!= null){
	        	client.append(pm.getProjectClientsId().getOrgRelationId().getClientName()).toString();	
	        }else{
	        	client.append("--").toString();
	        }
          }
        }
        if(null != expenseDetails.getStartDate()){
        	String StartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expenseDetails.getStartDate());
        	StDate.append(StartDate).toString();
        }else{
        	StDate.append("--").toString();
        }
        if(null != expenseDetails.getEndDate()){
        	String EndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expenseDetails.getEndDate());
        	edDate.append(EndDate).toString();
        }else{
        	edDate.append("--").toString();
        }
        if(null != expenseDetails.getPurpose()){
        	purposeStringBuilder.append(expenseDetails.getPurpose()).toString();
        	}else{
        		purposeStringBuilder.append("--").toString();
        	}
        if(expenseDetails.getTotalAmount().doubleValue() > 0){
        	StringBuilder str = new StringBuilder();
    		BigDecimal bd = new BigDecimal((expenseDetails.getTotalAmount()));
    		//bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );	
        
        	str.append(bd).toString();
        	}else{
        		totalStringBuilder.append("00.00").toString();
        	}
		
        
        Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Employee ID :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
	    cell2.setCellValue(employeeId.toString());
		cell2.setCellStyle(style);

		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
        
		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("Employee Name :");
		cell3.setCellStyle(style);
		Cell cell4 = writeRow.createCell(1);
	    cell4.setCellValue(nameBuilder.toString());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Email :");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(emailBuilder.toString());
		cell6.setCellStyle(style);

		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Phone No : ");
		cell7.setCellStyle(style);

		Cell cell8 = writeRow.createCell(1);
		cell8.setCellValue(mobileBuilder.toString());
		cell8.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell9 = writeRow.createCell(0);
		cell9.setCellValue("Employment Type : ");
		cell9.setCellStyle(style);

		Cell cell10 = writeRow.createCell(1);
		cell10.setCellValue(empType.toString());
		cell10.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(5);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell11 = writeRow.createCell(0);
		cell11.setCellValue("Vendor : ");
		cell11.setCellStyle(style);

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(vendor.toString());
		cell12.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(6);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell13 = writeRow.createCell(0);
		cell13.setCellValue("ProjectName : ");
		cell13.setCellStyle(style);

		Cell cell14 = writeRow.createCell(1);
		cell14.setCellValue(projectName.toString());
		cell14.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(7);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell15 = writeRow.createCell(0);
		cell15.setCellValue("Client : ");
		cell15.setCellStyle(style);

		Cell cell16 = writeRow.createCell(1);
		cell16.setCellValue(client.toString());
		cell16.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(8);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell17 = writeRow.createCell(0);
		cell17.setCellValue("StartDate : ");
		cell17.setCellStyle(style);

		Cell cell18 = writeRow.createCell(1);
		cell18.setCellValue(StDate.toString());
		cell18.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(9);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell19 = writeRow.createCell(0);
		cell19.setCellValue("EndDate : ");
		cell19.setCellStyle(style);

		Cell cell20 = writeRow.createCell(1);
		cell20.setCellValue(edDate.toString());
		cell20.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(10);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell21 = writeRow.createCell(0);
		cell21.setCellValue("Purpose : ");
		cell21.setCellStyle(style);

		Cell cell22 = writeRow.createCell(1);
		cell22.setCellValue(purposeStringBuilder.toString());
		cell22.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(11);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell23 = writeRow.createCell(0);
		cell23.setCellValue("TotalAmount : ");
		cell23.setCellStyle(style);

		Cell cell24 = writeRow.createCell(1);
		BigDecimal bd = new BigDecimal((expenseDetails.getTotalAmount()));
		//bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );
		cell24.setCellValue(bd.toString());
		
		cell24.setCellStyle(style);
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
	public File exportAllMyExpensesToPdf(List<Expenses> expenseList, OrgLocations orgLoc, User loginUser,String isGlobalUser) throws Exception {
		String[] headers = Constants.ALL_MY_EXPENSES_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("Expenses_").append(dateFormat.format(new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("_Expenses_").append(dateFormat.format(new Date())).toString(),".pdf");
	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLoc.getOrgLocationName()).append(" ").append("- My Expenses");
	       
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(18);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{5,5,5,7,6,6,4,5,5});
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
	            cell.setBackgroundColor(myBorderColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> expenseAllList = prepareExpenseListStringArray(expenseList,headers,orgLoc);
	        for (String[] expenseArray: expenseAllList) {
                for (int i = 0; i < expenseArray.length; i++) {
                    String datum = expenseArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("All Expenses Table");
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
	
	private List<String[]> prepareExpenseListStringArray(List<Expenses> expList, String[] headers,OrgLocations orgLocations) {
		List<String[]> arrayList = new ArrayList<>();
		for(Expenses ex:expList){
			String expenseArray[] = new String[headers.length];
			if(ex.getExpenseId() !=null){
				Expenses expenseId = getExpenseById(ex.getId());
				
							if(null != expenseId.getExpenseId()){
								expenseArray[0] = expenseId.getExpenseId();
							}else{
								expenseArray[0] = "--";
							}
							if(null != expenseId.getPurpose()){

								expenseArray[1] = expenseId.getPurpose();
							}else{
								expenseArray[1] = "--";
							}
							
							if(null != expenseId.getProjectMembers().getProjects()){
								if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
									expenseArray[2] = expenseId.getProjectMembers().getProjects().getProjectName();
								}
							}else{
								expenseArray[2] = "--";
							}
							
							if(null != expenseId.getClientName()){

								expenseArray[3] = expenseId.getClientName();
							}else{
								expenseArray[3] = "--";
							}
							
							if(null != expenseId.getStartDate()){
								expenseId.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expenseId.getStartDate()));
								expenseArray[4] = expenseId.getStartDateStr();
							}else{
								expenseArray[4] = "--";
							}
							
							if(null != expenseId.getEndDate()){
								expenseId.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expenseId.getEndDate()));
								expenseArray[5] = expenseId.getEndDateStr();
							}else{
								expenseArray[5] = "--";
							}
							
							if(null != expenseId.getNoOfExpenses()){

								expenseArray[6] = expenseId.getNoOfExpenses();
							}else{
								expenseArray[6] = "--";
							}
							

							if(expenseId.getTotalAmount().doubleValue() > 0){
								StringBuilder str = new StringBuilder();
								BigDecimal bd = new BigDecimal((expenseId.getTotalAmount()));
								bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );
								expenseArray[7] = str.append(bd).toString();
								
							}else{
								expenseArray[7] = "--";
							}
							
							if(null != expenseId.getStatus()){
								expenseArray[8] = expenseId.getExpenseStatus().getValue();
							}else{
								expenseArray[8] = "--";
							}
							
			}
			arrayList.add(expenseArray);
		}
		return arrayList;
	}

	@Override
	public File exportAllMyExpensesToExcel(List<Expenses> expenseList,OrgLocations orgLoc,String isGlobalUser) throws IOException {
		
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrgLocationName()+ "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		TimeSheetSubmissionCount requirementSubCount=null;
		createExcelsheet = getAllExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
		int column = 0;
		for (Expenses expensesList : expenseList) {
			writeRow = createExcelsheet.createRow(column+1);
			createExcelsheet = createAllXlsSheet(createExcelsheet, writeRow, expensesList,orgLoc);
			column++;
		}
		 try {
			file = File.createTempFile(orgLoc.getOrgLocationName() + "_ExpensesApproval_" + dateFormat.format(new Date()),".xls");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
	
	private Sheet getAllExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, TimeSheetSubmissionCount requirementSubCount) {
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

		String[] headers = Constants.ALL_MY_EXPENSES_HEADERS;;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);

		return createExcelsheet;
	}
	
	private Sheet createAllXlsSheet(Sheet createExcelsheet, Row writeRow, Expenses ex,OrgLocations orgLocations) {
		if(ex.getExpenseId() !=null){
			Expenses expenseId =getExpenseById(ex.getId());
			
						if(null != expenseId.getExpenseId()){
							writeRow.createCell(0).setCellValue(expenseId.getExpenseId());
							createExcelsheet.autoSizeColumn(0);
						}
						if(null != expenseId.getPurpose()){
							writeRow.createCell(1).setCellValue(expenseId.getPurpose());
							createExcelsheet.autoSizeColumn(1);
						}
						if(null != expenseId.getProjectMembers().getProjects()){
							if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
								writeRow.createCell(2).setCellValue(expenseId.getProjectMembers().getProjects().getProjectName());
								createExcelsheet.autoSizeColumn(2);
							}
						}
						
						if(null != expenseId.getClientName()){
							writeRow.createCell(3).setCellValue(expenseId.getClientName());
							createExcelsheet.autoSizeColumn(3);
						}
						
						if(null != expenseId.getStartDate()){
							expenseId.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expenseId.getStartDate()));
							writeRow.createCell(4).setCellValue(expenseId.getStartDateStr());
							createExcelsheet.autoSizeColumn(4);
						}
						if(null != expenseId.getEndDate()){
							expenseId.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),expenseId.getEndDate()));
							writeRow.createCell(5).setCellValue(expenseId.getEndDateStr());
							createExcelsheet.autoSizeColumn(5);
						}
						
						if(null != expenseId.getNoOfExpenses()){
							writeRow.createCell(6).setCellValue(expenseId.getNoOfExpenses());
							createExcelsheet.autoSizeColumn(6);
						}
						if(expenseId.getTotalAmount().doubleValue() > 0){
			                StringBuilder str = new StringBuilder();
			                BigDecimal bd = new BigDecimal((expenseId.getTotalAmount()));
							//bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );
			                writeRow.createCell(7).setCellValue(str.append(bd).toString());
			                createExcelsheet.autoSizeColumn(7);
						}
						if(null != expenseId.getExpenseStatus()){
							writeRow.createCell(8).setCellValue(expenseId.getExpenseStatus().getValue());
							createExcelsheet.autoSizeColumn(8);
						}
		}
	return createExcelsheet;
}

	@Override
	public List<ExpensesApproverHierarchy> getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId, Long orgLocationId, String expenseStatus,String isGlobalAccess) throws Exception
	{
		String submissionStatus = null;
		if ("submitted".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "SUBMITTEDFORAPPROVAL";
		}
		if ("approved".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "APPROVED";
		}
		if ("rejected".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "REJECTED";
		}
		if ("reopen".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "REOPENED";
		}
		
		return expensesApproverHierarchyDao.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, submissionStatus,isGlobalAccess);
	}

	@Override
	public List<Expenses> getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String expenseStatus) throws Exception
	{
		String submissionStatus = null;
		if ("submitted".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "SUBMITTED";
		}
		if ("approved".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "APPROVED";
		}
		if ("rejected".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "REJECTED";
		}
		if ("draft".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "DRAFT";
		}
		return expensesApproverHierarchyDao.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(masterCandidateId, submissionStatus);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public List<Expenses> getExpensesByProjectMemberId(Long projectMemberId, String invoiceType,Long expenseId) throws Exception {
		List<Expenses> expensesList = expensesDao.getExpensesByProjectMemberId(projectMemberId,invoiceType,expenseId);
		expensesList.forEach(expense->{
			StringBuilder sb = new StringBuilder();
			String startDate  = conversionUtil.getDateAsString(expense.getStartDate());
			String endDate  = conversionUtil.getDateAsString(expense.getEndDate());
			sb.append(startDate).append(" / ").append(endDate);
			if(expense.getPurpose() !=null && !"".equals(expense.getPurpose())) {
				sb.append("(").append(expense.getPurpose()).append(")");
			}
			expense.setDateFrequency(sb.toString());
		});
		return expensesList;
	}

	@Override
	public List<ExpensesEntriesDTO> getApprovedExpenses(Long expenseId,Long locationsId) throws Exception {
		List<ExpensesEntriesDTO> expensesEntriesDTOList = new ArrayList<>();
		ExpensesApproverHierarchy eah = expensesApprovalService.getApprovedExpenseByExpenseIdAndLocationId(expenseId, locationsId);
		if(eah != null) {
			List<ExpensesEntries> entriesList = expensesEntriesService.getExpensesEntriesByExpenseId(expenseId);
			for(ExpensesEntries entries : entriesList){
					ExpensesEntriesDTO  expensesEntriesDTO = new ExpensesEntriesDTO();
					expensesEntriesDTO.setDateOccurred(ConversionUtil.getDateAsString(entries.getExpenseDate()));
					expensesEntriesDTO.setEntryDescription(entries.getDescription());
					expensesEntriesDTO.setCategoryAmount(String.valueOf(entries.getAmount()));
					expensesEntriesDTO.setAmount(entries.getAmount());
					expensesEntriesDTO.setCategoryName(entries.getExpensesCategoryId().getExpensesName());
					expensesEntriesDTOList.add(expensesEntriesDTO);
			}
		}
		
		return expensesEntriesDTOList;
	}

	@Override
	public List<Expenses> getExpensesByProjectIdAndMasterCandidateId(Long projectId, Long masterCandidateId)
			throws Exception {
		return expensesDao.getExpensesByProjectIdAndMasterCandidateId(projectId, masterCandidateId);
	}

	@Override
	public List<Expenses> getAllExpensesByOrgLocationId(Long orgLocationId) throws Exception {
		return expensesDao.getAllExpensesByOrgLocationId(orgLocationId);
	}

	@Override
	public List<Expenses> getAllExpensesByProjectIdOrgLocationIdStartDateAndEndDate(Long projectId, Long OrglocationId,Date startDate, Date endDate) throws Exception {
		return expensesDao.getAllExpensesByProjectIdOrgLocationIdStartDateAndEndDate(projectId, OrglocationId,startDate,endDate);
	}
	
	@Override
	public boolean changeExpenseBillingStatus(long expenseId, String billingStatus) {
		// TODO Auto-generated method stub
		return expensesDao.changeExpenseBillingStatus(expenseId,billingStatus);
	}

	@Override
	public List<Expenses> getAllExpenses() {
		// TODO Auto-generated method stub
		return  expensesDao.getAllExpenses();
	}

	@Override
	public List<PendingForExpenseApprovalReportDTO> getPendingForApprovalExpenseReportByProjectIds(String projectIds, Date frequencyStartDate,Date frequencyEndDate,String locationDateFormat) {
		List<PendingForExpenseApprovalReportDTO> pendingForExpenseApprovalReportDTOList = new LinkedList<>();
		try {
			List<ExpensePendingApproveReportView> expenseList = expensesDao.getPendingForApprovalExpenseReportByProjectIds(projectIds, frequencyStartDate, frequencyEndDate);
			if(expenseList != null && !expenseList.isEmpty()) {
				for(ExpensePendingApproveReportView expenses : expenseList) {
					PendingForExpenseApprovalReportDTO pendingForExpenseApprovalReportDTO = new PendingForExpenseApprovalReportDTO();
					pendingForExpenseApprovalReportDTO.setEmployeId(expenses.getEmpId().toString());
					pendingForExpenseApprovalReportDTO.setEmployeName(expenses.getEmpName());
					pendingForExpenseApprovalReportDTO.setEmployeType(expenses.getEmploymentType());
					pendingForExpenseApprovalReportDTO.setApprovalMechanism(expenses.getApprovalMechanism());
					pendingForExpenseApprovalReportDTO.setApprovedBy(expenses.getActionPerformedby());
					pendingForExpenseApprovalReportDTO.setManagerOne(expenses.getLevel1Name());
					pendingForExpenseApprovalReportDTO.setManagerTwo(expenses.getLevel2Name());
					//pendingForExpenseApprovalReportDTO.setNoofDaysPending(expenses);
					pendingForExpenseApprovalReportDTO.setFrequency(expenses.getDuration());
					pendingForExpenseApprovalReportDTO.setSubmittedAmount(expenses.getSubmittedAmount().toString());
					pendingForExpenseApprovalReportDTO.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(locationDateFormat, expenses.getSubmittedDate()));		
					pendingForExpenseApprovalReportDTO.setProjectName(expenses.getProjectName());
					//set all DTO values
					
					pendingForExpenseApprovalReportDTOList.add(pendingForExpenseApprovalReportDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pendingForExpenseApprovalReportDTOList;
	}
	
}