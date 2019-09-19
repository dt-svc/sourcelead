


package com.bis.sourcelead.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
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
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ExpensesApproverDTO;
import com.bis.sourcelead.dto.ExpensesApproverHierarchyDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.ibm.icu.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class ExpensesApprovalServiceImpl implements ExpensesApprovalService{

	@Autowired
	private ExpensesApprovalDao expensesApprovalDao;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	 @Value("${pmsUrl}")
		private String pmsUrl;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesApprovalServiceImpl.class);
	
	
	@Override
	public ExpensesApproverDTO getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser, OrgLocations orgLoc,ExpensesApproverDTO expensesApproverDTO,String isGlobalUser) throws Exception {
		Date endDateUTC = null;
		Date startDateUTC = null;
	//	SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLoc.getTimeZoneId()));
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
		if(StringUtils.isNotEmpty(expensesApproverDTO.getStartDate()) && !expensesApproverDTO.getStartDate().equalsIgnoreCase("undefined")){
			Date startTime = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),expensesApproverDTO.getStartDate());
			String customStartTimeStr = TZ_DF_CSD.format(startTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
			startDateUTC = TZ_DF_TS.parse(startDateString);
		}
		
		if(StringUtils.isNotEmpty(expensesApproverDTO.getEndDate()) && !expensesApproverDTO.getStartDate().equalsIgnoreCase("undefined")){
			Date endTime =  commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),expensesApproverDTO.getEndDate());
			String customEndTimeStr = TZ_DF_CED.format(endTime);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		
		int start = expensesApproverDTO.getStart();
		int length = 0;
		if(expensesApproverDTO.getPageNo()<1) {
			expensesApproverDTO.setPageNo(1);
		}
		if(expensesApproverDTO.getPageSize()<1) {
			length = 5;
			expensesApproverDTO.setPageSize(5);
		}else {
			length = expensesApproverDTO.getPageSize();
		}
		ResponseList<ExpensesApproverHierarchy> expApperoverHierarchy = null;
		if(expensesApproverDTO.getType()!= null) {
			expApperoverHierarchy = expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser.getId(),orgLoc.getId(), expensesApproverDTO.getExpenseStatus(), startDateUTC, endDateUTC,null,0,0,isGlobalUser);
		}else {
			expApperoverHierarchy = expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser.getId(),orgLoc.getId(), expensesApproverDTO.getExpenseStatus(), startDateUTC, endDateUTC,expensesApproverDTO.getSearchValue(),start,length,isGlobalUser);	
		}
		
		LinkedList<ExpensesApproverHierarchyDTO> freshEahList = new LinkedList<>();
		
		for(ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy.getData()){
			Expenses expenses = expensesApproverHierarchy.getExpenseId();
			if(expensesApproverHierarchy.getExpenseId() != null && expensesApproverHierarchy.getExpenseId().getLocationsCode() != null){
			expenses.setLocationsCode(expensesApproverHierarchy.getExpenseId().getOrgLocationId().getLocationCode());
			if(null != expensesApproverHierarchy.getExpenseId().getEndDate()){
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getEndDate()));
			}else{
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			
			if (null != expensesApproverHierarchy.getExpenseId()) {
				
				if(expensesApproverHierarchy.getExpenseId().getPurpose()!=null){
					expensesApproverHierarchy.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpenseId().getPurpose());
				}else{
					expensesApproverHierarchy.setExpensesApproverHierarchyPurpose("--");
				}
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()){
								expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
							}else{
								expensesApproverHierarchy.setExpensesProjectName("--");
							}
							
					 ProjectMembers projectMember = projectMembersDao.getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForExpences(expensesApproverHierarchy.getExpenseId().getProjectMembers().getMasterCandidateId().getId(), expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getId(),orgLoc.getId(),isGlobalUser);
					 ProjectMembers pmWithUser =projectMembersDao.getConsultantUserProjectMemberByProjectMemberId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getId());
					 if (projectMember != null && null != projectMember.getProjectMemberCandidteId()) {
							if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && StringUtils.isNotBlank(projectMember.getProjectMemberCandidteId().getDesignationType())){
								 String empType = null; empType = projectMember.getProjectMemberCandidteId().getDesignationType();
								 expensesApproverHierarchy.setEmpType(empType);
								if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
									OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
									if(orgRelation !=  null && orgRelation.getResponderLocationId() != null && StringUtils.isNotBlank(orgRelation.getResponderLocationId().getOrgLocationName())) {
										expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrgLocationName());
										expensesApproverHierarchy.setExpenseEmpUserType(empType);
									}else{
										expensesApproverHierarchy.setExpenseEmpUserType("--");
									}
								 }else{
									 expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }
							}else{
								expensesApproverHierarchy.setExpenseEmpUserType("--");
							}
						
						if (null != projectMember.getProjectMemberCandidteId()) {
							Candidate projectMemberCandidte = candidateService.getCandidateByCandidateId(projectMember.getProjectMemberCandidteId().getId());
							expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(projectMemberCandidte.getFullName());
							expensesApproverHierarchy.setExpenseEmpUserEmail(projectMemberCandidte.getEmail());
							if(pmWithUser != null && projectMemberCandidte!= null && projectMemberCandidte.getConsultantUser() != null){
								expensesApproverHierarchy.setExpensesUserid(projectMemberCandidte.getConsultantUser().getId());
								expensesApproverHierarchy.setUserFileUrl(projectMemberCandidte.getConsultantUser().getFileUrl());
							}
						}
						
						if (null != expensesApproverHierarchy.getExpenseId()) {
							if(null != expensesApproverHierarchy.getExpenseId().getPurpose()){
								expensesApproverHierarchy.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpenseId().getPurpose());
							}else{
								expensesApproverHierarchy.setExpensesApproverHierarchyPurpose("-----");
							}
						}
						Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUser.getId(),orgLoc.getId());
						if(StringUtils.isNotEmpty(expensesApproverHierarchy.getReopenRejectedReason()) && !isadmin){
							expensesApproverHierarchy.setReopenRejectedReason(expensesApproverHierarchy.getReopenRejectedReason());
						}
						expensesApproverHierarchy.setRejectedReason(expensesApproverHierarchy.getRejectedReason());
						expensesApproverHierarchy.setReopenReason(expensesApproverHierarchy.getReopenReason());
					}else {
						expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(pmWithUser.getProjectMemberCandidteId().getFullName());
						expensesApproverHierarchy.setExpenseEmpUserEmail(pmWithUser.getProjectMemberCandidteId().getEmail());
					}
				}
			}
			ExpensesApproverHierarchyDTO expensesApproverHierarchyDto = convertExpensesApproverHierarchyTOExpensesApproverHierarchyDTO(expensesApproverHierarchy,orgLoc,isGlobalUser);
			expensesApproverDTO.setTotalRows(expApperoverHierarchy.getRecordsTotal());
			int totalRows = expApperoverHierarchy.getRecordsTotal();
			if(totalRows > 0) {
				 int remainder = totalRows % length;
				 int pageCount = totalRows / length;
				 if(remainder > 0) {
					 expensesApproverDTO.setPageCount(pageCount+1);
				 }
				 else {
					 expensesApproverDTO.setPageCount(pageCount);
				 }
			}
			freshEahList.add(expensesApproverHierarchyDto);
		 }
	}
		expensesApproverDTO.setExpensesApproverHierarchyDTO(freshEahList);
		//expApperoverHierarchy.setData(freshEahList);
		return expensesApproverDTO;
	}


	private ExpensesApproverHierarchyDTO convertExpensesApproverHierarchyTOExpensesApproverHierarchyDTO(ExpensesApproverHierarchy expensesApproverHierarchy,OrgLocations orgLocations,String isGlobalUser) throws Exception {
		ProjectMembers projectMember = projectMembersDao.getProjectMembersByMasterCandidateIdAndProjectIdLocationId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getMasterCandidateId().getId(), expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getId(),orgLocations.getId(),isGlobalUser);
		ExpensesApproverHierarchyDTO expensesApproverHierarchyDto = new ExpensesApproverHierarchyDTO();
		expensesApproverHierarchyDto.setExpenseEmpUserFirstNameLastName(expensesApproverHierarchy.getExpenseEmpUserFirstNameLastName());
		expensesApproverHierarchyDto.setExpenseEmpUserEmail(expensesApproverHierarchy.getExpenseEmpUserEmail());
		expensesApproverHierarchyDto.setUserFileUrl(expensesApproverHierarchy.getUserFileUrl());
		expensesApproverHierarchyDto.setExpenseEmpUserType(expensesApproverHierarchy.getExpenseEmpUserType());
		expensesApproverHierarchyDto.setExpenseStartDateString(expensesApproverHierarchy.getExpenseStartDateString());
		expensesApproverHierarchyDto.setExpenseEndDateString(expensesApproverHierarchy.getExpenseEndDateString());
		expensesApproverHierarchyDto.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpensesApproverHierarchyPurpose());
		expensesApproverHierarchyDto.setExpensesProjectName(expensesApproverHierarchy.getExpensesProjectName());
		expensesApproverHierarchyDto.setExpensesUserid(expensesApproverHierarchy.getExpensesUserid());
		expensesApproverHierarchyDto.setLocationsCode(expensesApproverHierarchy.getExpenseId().getLocationsCode());
		expensesApproverHierarchyDto.setRejectedReason(expensesApproverHierarchy.getRejectedReason());
		expensesApproverHierarchyDto.setReopenReason(expensesApproverHierarchy.getReopenReason());
		expensesApproverHierarchyDto.setReopenRejectedReason(expensesApproverHierarchy.getReopenRejectedReason());
		expensesApproverHierarchyDto.setUserTypeVendorName(expensesApproverHierarchy.getUserTypeVendorName());
		expensesApproverHierarchyDto.setExpenseId(expensesApproverHierarchy.getExpenseId().getExpenseId());
		expensesApproverHierarchyDto.setExpensesStatus(expensesApproverHierarchy.getExpenseStatus().getValue());
		expensesApproverHierarchyDto.setBillingStatus(expensesApproverHierarchy.getExpenseId().getBillingStatus());
		expensesApproverHierarchyDto.setExpenseApprovalStatus(expensesApproverHierarchy.getExpenseApprovalStatus().toString());
		 if(null != projectMember && 
				 null != projectMember.getProjectClientsId().getOrgRelationId()){
	    	 OrgRelations orgRelations = orgRelationsService.getOrgRelationById(projectMember.getProjectClientsId().getOrgRelationId().getId());
	   	 if(null != projectMember.getProjectClientsId().getOrgRelationId().getLocationId() && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelations.getResponderLocationId() != null){
						expensesApproverHierarchyDto.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
					}
				}
				if(null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() && null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId() && projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelations.getLocationId() != null){
						expensesApproverHierarchyDto.setClientName(orgRelations.getLocationId().getOrgLocationName());
					}
				}
	    }
		 

		//BigDecimal totalAmo = new BigDecimal(expensesApproverHierarchy.getExpenseId().getTotalAmount());
		//BigDecimal totalAmountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		 
		expensesApproverHierarchyDto.setTotalAmount(expensesApproverHierarchy.getExpenseId().getTotalAmount());
		expensesApproverHierarchyDto.setTdSpanLength("9");
		//if(expensesApproverHierarchy.getActionbyorglocid()!= null) {
		if(StringUtils.isNotEmpty(expensesApproverHierarchy.getActionPerformedBy())) {
			expensesApproverHierarchyDto.setActionPerformedBy(expensesApproverHierarchy.getActionPerformedBy());
		}else if(expensesApproverHierarchy.getActionbyorglocid()!= null){
			 OrgLocations actionPerformedBy = orgLocationService.getOrgLocations(expensesApproverHierarchy.getActionbyorglocid());
			 expensesApproverHierarchyDto.setActionPerformedBy(actionPerformedBy.getOrgLocationName());
		}
		expensesApproverHierarchyDto.setNoOfExpenses(expensesApproverHierarchy.getExpenseId().getNoOfExpenses());
		expensesApproverHierarchyDto.setExpId(expensesApproverHierarchy.getExpenseId().getId());
		expensesApproverHierarchyDto.setProjectMemberId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getId());
		
		if(expensesApproverHierarchy.getExpenseId() != null && expensesApproverHierarchy.getExpenseId().getProjectMembers() != null && expensesApproverHierarchy.getExpenseId().getProjectMembers().getOrgRelationUUID() != null) {
			expensesApproverHierarchyDto.setOrgRelationUUID(expensesApproverHierarchy.getExpenseId().getProjectMembers().getOrgRelationUUID());
		}
		return expensesApproverHierarchyDto;
	}

	@Override
	public ExpensesApproverHierarchy updateSubmissionApproverHierarchy(
			ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception {
		return expensesApprovalDao.updateSubmissionApproverHierarchy(expensesApproverHierarchy);
	}




	@Override
	public Integer updateApprovalStatusByApproverForExpense(Long expenseId, Long approverUserId, String status,
			String rejectedReason,OrgLocations location,String isGlobalUser) throws Exception {
		return expensesApprovalDao.updateApprovalStatusByApproverForExpense(expenseId, approverUserId, status, rejectedReason,location,isGlobalUser);
	}


	 @Override
	    public File getExpensesApproverHierarchyListForPdf(List<ExpensesApproverHierarchyDTO> expApperoverHierarchy, OrgLocations orgLoc,User loginUser,String isGlobalUser) throws Exception {
	        String[] headers = Constants.EXPENSES_APPROVAL_lIST_HEADERS;
	     Document document = new Document(PageSize.LETTER.rotate());
	     DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	     StringBuilder sbUser = new StringBuilder();
	     sbUser.append("ExpensesApproval_").append(dateFormat.format(new Date()));
	     StringBuilder fileSB = new StringBuilder();
	     File file = File.createTempFile(fileSB.append("_ExpensesApproval_").append(dateFormat.format(new Date())).toString(),".pdf");
	     file.setReadable(true);
	     try {
	     // Get an instance of PdfWriter and create a Table.pdf file as an output.
	     PdfWriter.getInstance(document, new FileOutputStream(file));
	     document.open();
	     Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	     BaseColor myColor = WebColors.getRGBColor("#000000");
	     boldFont.setColor(myColor);
	     StringBuilder userStyle = new StringBuilder();
	     userStyle.append(orgLoc.getOrgLocationName()).append(" ").append("- Employee Expenses");
	     // Create an instance of PdfPTable. After that we transform the header and
	     PdfPTable table = new PdfPTable(headers.length);
	     Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	     PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	     cell1.setColspan(18);
	     headerCellStyle(cell1);
	     table.setWidthPercentage(100);
	table.setWidths(new int[]{7,7,7,7,4,5,5,5,7,6,6,4,5,5,7,5});
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
	     java.util.List<String[]> expenseList = prepareExpenseApprovalListStringArray(expApperoverHierarchy,headers,orgLoc,loginUser,isGlobalUser);
	     if(expenseList != null && expenseList.size() >0){
	         for (String[] expenseArray: expenseList) {
	             for (int i = 0; i < expenseArray.length; i++) {
	                 String datum = expenseArray[i];
	                 PdfPCell cell = new PdfPCell();
	                 cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	                 table.addCell(cell);
	             }
	             table.completeRow();
	         }
	     }
	     document.addTitle("Expenses Approval Table");
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
	private List<String[]> prepareExpenseApprovalListStringArray(List<ExpensesApproverHierarchyDTO> expApperoverHierarchy, String[] headers,OrgLocations orgLoc,User loginUser,String isGlobalUser) throws Exception {
	    List<String[]> arrayList = new ArrayList<>();
	    for(ExpensesApproverHierarchyDTO ex:expApperoverHierarchy){
	        String expenseArray[] = new String[headers.length];
	        if(ex.getExpenseId() !=null){
	            Expenses expenseId =expensesService.getExpenseById(ex.getExpId());
	            if(null != expenseId.getProjectMembers()){
	                if(null != expenseId.getExpenseId()){
	                    expenseArray[0] = expenseId.getExpenseId();
	                }else{
	                    expenseArray[0] = "--";
	                }
	                //if(null != expenseId.getProjectMembers().getProjectMemberCandidteId()){
	                    if(null != expenseId.getProjectMembers().getProjectMemberCandidteId()){
	                        if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName() && null != expenseId.getProjectMembers().getProjectMemberCandidteId().getLastName()){
	                            StringBuffer sb = new StringBuffer();
	                            expenseArray[1] = sb.append(expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName()).append(" ").append(expenseId.getProjectMembers().getProjectMemberCandidteId().getLastName()).toString();
	                        }else if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName()){
	                            expenseArray[1] = expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName();
	                        }
	                        else
	                        {
	                            expenseArray[1] = "--";
	                        }
	                        if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getEmail()){
	                            expenseArray[2] = expenseId.getProjectMembers().getProjectMemberCandidteId().getEmail();
	                        }else{
	                            expenseArray[2] = "--";
	                        }
	                        if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getMobilePhone() && !"".equalsIgnoreCase(expenseId.getProjectMembers().getProjectMemberCandidteId().getMobilePhone())){
	                            expenseArray[3] = expenseId.getProjectMembers().getProjectMemberCandidteId().getMobilePhone();

	                        }else{
	                            expenseArray[3] = "--";
	                        }
	                    }
	                    if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(expenseId.getProjectMembers().getProjectMemberCandidteId().getDesignationType())){
	                        expenseArray[4] = expenseId.getProjectMembers().getProjectMemberCandidteId().getDesignationType();
	                    }else{
	                        expenseArray[4] = "--";
	                    }
	                if(ex.getProjectMemberId()!= null) {
	                ProjectMembers projectMember = projectMembersService.getProjectMemberById(ex.getProjectMemberId());
	                    if(projectMember.getProjectMemberCandidteId() != null && null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
	                        String empType = projectMember.getProjectMemberCandidteId().getDesignationType();
	                            if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
	                                OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
	                                if(orgRelation != null){
	                                    expenseArray[5] = orgRelation.getResponderLocationId().getOrgLocationName();
	                                }else{
	                                    expenseArray[5] = "--";
	                                }
	                             }
	                        }else{
	                            expenseArray[5] = "--";
	                        }
	                    }
	                //}

	                if(null != expenseId.getProjectMembers().getProjects()){

	                    if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
	                        expenseArray[6] = expenseId.getProjectMembers().getProjects().getProjectName();
	                    }
	                }else{
	                    expenseArray[6] = "--";
	                }
	            }
	            if(null != expenseId.getClientName()){
	                expenseArray[7] = expenseId.getClientName();
	            }else{
	                expenseArray[7] = "--";
	            }
	            if(null != expenseId.getPurpose()){
	                expenseArray[8] = expenseId.getPurpose();
	            }else{
	                expenseArray[8] = "--";
	            }
	            if(null != expenseId.getStartDate()){
	                expenseId.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getStartDate()));
	                expenseArray[9] = expenseId.getStartDateStr();
	            }else{
	                expenseArray[9] = "--";
	            }
	            if(null != expenseId.getEndDate()){
	                expenseId.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getEndDate()));
	                expenseArray[10] = expenseId.getEndDateStr();
	            }else{
	                expenseArray[10] = "--";
	            }
	            if(null != expenseId.getNoOfExpenses()){
	                expenseArray[11] = expenseId.getNoOfExpenses();
	            }else{
	                expenseArray[11] = "--";
	            }
	            if(expenseId.getTotalAmount().intValue() > 0){
	                StringBuilder str = new StringBuilder();
	                BigDecimal bd = new BigDecimal((expenseId.getTotalAmount()));
	                //bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );
	                expenseArray[12] = str.append(bd).toString();
	            }else{
	                expenseArray[12] = "--";
	            }
	            if(null != expenseId.getStatus()){
	                ExpensesApproverHierarchy eah = getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId.getId(),orgLoc.getId(),isGlobalUser);
	             Submission_Status submissionStatus = eah.getExpenseStatus();
	                 if(submissionStatus != null) {
	                     expenseArray[13] = submissionStatus.getValue().toUpperCase();
	                 }else {
	                     expenseArray[13] = "Rejected";
	                 }
	            }else{
	                expenseArray[13] = "--";
	            }
	            if(null != ex.getActionPerformedBy()){
	                expenseArray[14] = ex.getActionPerformedBy();
	            }else{
	                expenseArray[14] = "--";
	            }
	            if(null != ex.getBillingStatus()){
	                expenseArray[15] = ex.getBillingStatus();
	            }else{
	                expenseArray[15] = "--";
	            }
	        }
	        arrayList.add(expenseArray);
	    }
	    return arrayList;
	}
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId)
			throws Exception {
		return expensesApprovalDao.getExpensesApproverHierarchyListByExpenseId(expenseId);
	}

	@Override
	public ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId) {
		return expensesApprovalDao.isThereAnyApproverApprovedTheExpense(expenseId);
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
public File getExpensesApproverHierarchyListForExcel(List<ExpensesApproverHierarchyDTO> expApperoverHierarchy,OrgLocations orgLoc,User loginUser,String isGlobalUser) throws Exception {
	
	File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrgLocationName()+ "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		TimeSheetSubmissionCount requirementSubCount=null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
		int column = 0;
		for (ExpensesApproverHierarchyDTO expensesList : expApperoverHierarchy) {
			writeRow = createExcelsheet.createRow(column+1);
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, expensesList,orgLoc,loginUser,isGlobalUser);
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

private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, TimeSheetSubmissionCount requirementSubCount) {
	CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
	String[] headers = Constants.EXPENSES_APPROVAL_lIST_HEADERS;;
	createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
	return createExcelsheet;
}
private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ExpensesApproverHierarchyDTO expensesList,OrgLocations orgLoc,User loginUser,String isGlobalUser) throws Exception {
		if(expensesList.getExpenseId() !=null){
			Expenses expenseId =expensesService.getExpenseById(expensesList.getExpId());
			if(null != expenseId.getProjectMembers()){
					if(null != expenseId.getProjectMembers().getProjectMemberCandidteId()){
						
						if(null != expenseId.getExpenseId()){
							writeRow.createCell(0).setCellValue(expenseId.getExpenseId());
							createExcelsheet.autoSizeColumn(0);
						}
						if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName() && null != expenseId.getProjectMembers().getProjectMemberCandidteId().getLastName()){
							StringBuffer sb = new StringBuffer();
							writeRow.createCell(1).setCellValue(sb.append(expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName()).append(" ").append(expenseId.getProjectMembers().getProjectMemberCandidteId().getLastName()).toString());
							createExcelsheet.autoSizeColumn(1);
						}else if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName()){
							writeRow.createCell(1).setCellValue(expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName());
							createExcelsheet.autoSizeColumn(1);
						}
						/*if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName() && null != expenseId.getProjectMembers().getProjectMemberCandidteId().getLastName()){
							writeRow.createCell(0).setCellValue(expenseId.getProjectMembers().getProjectMemberCandidteId().getFirstName());
							createExcelsheet.autoSizeColumn(0);
						}*/
						if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getEmail()){
							writeRow.createCell(2).setCellValue(expenseId.getProjectMembers().getProjectMemberCandidteId().getEmail());
							createExcelsheet.autoSizeColumn(2);
						}
						if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getMobilePhone()){
							writeRow.createCell(3).setCellValue(expenseId.getProjectMembers().getProjectMemberCandidteId().getMobilePhone());
							createExcelsheet.autoSizeColumn(3);
						}
					}
					if(null != expenseId.getProjectMembers().getProjectMemberCandidteId().getDesignationType()){
						writeRow.createCell(4).setCellValue(expenseId.getProjectMembers().getProjectMemberCandidteId().getDesignationType());
						createExcelsheet.autoSizeColumn(4);
					}
					if(expensesList.getProjectMemberId()!= null) {
					ProjectMembers projectMember = projectMembersService.getProjectMemberById(expensesList.getProjectMemberId());
						if(projectMember.getProjectMemberCandidteId() != null && null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
							String empType = projectMember.getProjectMemberCandidteId().getDesignationType();
							if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
								OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
								if(orgRelation != null){
								 writeRow.createCell(5).setCellValue(orgRelation.getResponderLocationId().getOrgLocationName());
								 createExcelsheet.autoSizeColumn(5);
							 }
							}
						}
					}
				
				if(null != expenseId.getProjectMembers().getProjects()){
					if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
						writeRow.createCell(6).setCellValue(expenseId.getProjectMembers().getProjects().getProjectName());
						createExcelsheet.autoSizeColumn(5);
					}
				}
			}
			if(null != expenseId.getClientName()){
				writeRow.createCell(7).setCellValue(expenseId.getClientName());
				createExcelsheet.autoSizeColumn(7);
			
			}
			if(null != expenseId.getPurpose()){
				writeRow.createCell(8).setCellValue(expenseId.getPurpose());
				createExcelsheet.autoSizeColumn(8);
			}
			if(null != expenseId.getStartDate()){
				expenseId.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getStartDate()));
				writeRow.createCell(9).setCellValue(expenseId.getStartDateStr());
				createExcelsheet.autoSizeColumn(9);
			}
			if(null != expenseId.getEndDate()){
				expenseId.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getEndDate()));
				writeRow.createCell(10).setCellValue(expenseId.getEndDateStr());
				createExcelsheet.autoSizeColumn(10);
			}
			if(null != expenseId.getNoOfExpenses()){
				writeRow.createCell(11).setCellValue(expenseId.getNoOfExpenses());
				createExcelsheet.autoSizeColumn(11);
			}
			if(expenseId.getTotalAmount().doubleValue() > 0){
				StringBuilder str = new StringBuilder();
				BigDecimal bd = new BigDecimal((expenseId.getTotalAmount()));
				//bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );                
                writeRow.createCell(12).setCellValue(str.append(bd).toString());
                createExcelsheet.autoSizeColumn(12);
			}
			if(null != expenseId.getExpenseStatus()){
				ExpensesApproverHierarchy  eah = getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId.getId(),orgLoc.getId(),isGlobalUser);
			    Submission_Status submissionStatus = eah.getExpenseStatus();
			    if(eah.getReopenRejectedReason()!= null) {
			    	writeRow.createCell(13).setCellValue(submissionStatus.getValue().toUpperCase());
			    }else {
			    	writeRow.createCell(13).setCellValue(expensesList.getExpensesStatus().toUpperCase());
				    createExcelsheet.autoSizeColumn(12);
			    }
			}
			if(null != expensesList.getActionPerformedBy()){
				writeRow.createCell(14).setCellValue(expensesList.getActionPerformedBy());
				createExcelsheet.autoSizeColumn(14);
			}
			if(null != expensesList.getBillingStatus()){
				writeRow.createCell(15).setCellValue(expensesList.getBillingStatus());
				createExcelsheet.autoSizeColumn(15);
			}
			
		}
	return createExcelsheet;
	
}

	@Override
	public  Boolean isTheExpenseTotallyApproved(Long expenseId){
		return expensesApprovalDao.isTheExpenseTotallyApproved(expenseId);
	}

	@Override
	public Boolean isReopenButtonVisibleForLoginOrg(Long expenseId,Long locationsId,String isGlobalUser){
		return expensesApprovalDao.isReopenButtonVisibleForLoginOrg(expenseId, locationsId,isGlobalUser);
	}

	@Override
	public ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId) {
		return expensesApprovalDao.getexpensesApproverHierarchyForEmployeeByExpenseId(expenseId);
	}

	@Override
	public void updateExpenseStatusByExpenseId(Long expenseId, String status) {
		 expensesApprovalDao.updateExpenseStatusByExpenseId(expenseId, status);
		
	}
	
	@Override
	public ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,
			Long expenseId,Long locationsId,String isGlobalUser) throws Exception {
		return expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(approverUserId, expenseId,locationsId,isGlobalUser);
	}


	@Override
	public User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId, String reason, User loginUser,
			String type, String isSuperUser) {
		return expensesApprovalDao.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId, reason, loginUser, type, isSuperUser);
	}

	@Override
	public ExpensesApproverHierarchy saveExpensesApproverHierarchy(
			ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception{
		return expensesApprovalDao.saveExpensesApproverHierarchy(expensesApproverHierarchy);
	}

	@Override
	public ExpensesApproverHierarchy getApprovedExpenseByExpenseIdAndLocationId(Long expenseId, Long locationsId)
	{
		return expensesApprovalDao.getApprovedExpenseByExpenseIdAndLocationId(expenseId, locationsId);
	}

	 @Override
	    public void sendExpenseReopenAcceptMailToEmployee(Expenses expenses, OrgLocations orgLoc, String type,
	    User reopenedUser, User loginUser) throws Exception {

	        EmailMessage emailMessage = new EmailMessage();
	        emailMessage.setFirstName(expenses.getProjectMembers().getProjectMemberCandidteId().getFirstName());
	        emailMessage.setLastName(expenses.getProjectMembers().getProjectMemberCandidteId().getLastName());
	        emailMessage.setToEmail(expenses.getProjectMembers().getProjectMemberCandidteId().getEmail());
	        emailMessage.setFromEmail(loginUser.getEmail());
	        emailMessage.setCreatedBy(loginUser.getUserCode());
	        StringBuilder userNameSB = new StringBuilder();
	        emailMessage.setFromName(userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	        Map<String, String> globalVarsMap = new HashMap<>();

	        if (null != expenses.getExpenseId()) {
	            if (null != expenses.getStartDate()) {
	                globalVarsMap.put("startDate",
	                        commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), expenses.getStartDate()));
	            }
	        }
	        if (null != expenses.getExpenseId()) {
	            if (null != expenses.getEndDate()) {
	                globalVarsMap.put("endDate",
	                        commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), expenses.getEndDate()));
	            }
	        }

	        StringBuilder amopuntRate = new StringBuilder();
	        amopuntRate.append((expenses.getTotalAmount())).append(" ");
	        globalVarsMap.put("amount", amopuntRate.toString());
	        globalVarsMap.put("loginOrg", orgLoc.getOrg().getOrgName());
	        globalVarsMap.put("employeeName", expenses.getProjectMembers().getProjectMemberCandidteId().getFirstName());
	        globalVarsMap.put("approverName",
	                userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	        globalVarsMap.put("projectName", expenses.getProjectName());
	        if (expenses.getClientName() != null) {
	            globalVarsMap.put("clientName", expenses.getClientName());
	        } else {
	            globalVarsMap.put("clientName", "--");
	        }
	        ExpensesApproverHierarchy eah = expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(reopenedUser.getId(), expenses.getId(), orgLoc.getId(), "");
	        globalVarsMap.put("reason", eah.getReopenReason());
	        List<ExpensesApproverHierarchy> eah1 = expensesApprovalDao.makeReopenReasonStatusNull(eah.getExpenseId().getId());
	        // setting the Recipient information
	        InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
	        EmailScenario emailScenario = null;
	        // getting the current EmailScenario by EmailScenario unique name and entity
	        // type
	        emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
	                Constants.MAIL_REOPEN_APPROVED_MAIL_TO_EMPLOYEE, Constants.ENTITY_TYPE_EMAIL);

	        // covert subject,body and signature
	        String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(), globalVarsMap,
	                emailScenario);
	        String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap, emailScenario);
	        String signature = null;
	        if (StringUtils.isNotBlank(emailScenario.getSignature())) {
	            signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(), globalVarsMap,
	                    emailScenario);
	        }
	        String url = sourceleadUrl;

	        // construct HTML template using required data
	        String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
	                signature, url, orgLoc.getOrg(), orgLoc);
	        emailMessage.setSubject(subject);
	        emailMessage.setMessageBody(emailTemplate);

	        // calling the sendEmail method of emailScenerioUtil
	        emailScenerioUtil.sendEmail(emailMessage, orgLoc.getId(), iAdressArray, new ArrayList<EmailAttachment>());

	        // commonUtil.sendTemplateEmail(emailMessage,
	        // globalVarsMap,orgLoc.getOrg(),orgLoc,Constants.MAIL_REOPEN_APPROVED_MAIL_TO_EMPLOYEE,new
	        // ArrayList<EmailAttachment>());

	        EmailScenario notificationScenario = null;
	        // if("REOPEN".equalsIgnoreCase(reason)){
	        notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
	                Constants.MAIL_REOPEN_APPROVED_MAIL_TO_EMPLOYEE_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
	        String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
	        notificationScenario.setBody(notificationBody);
	        notificationScenario.setOrgCode(orgLoc.getOrg().getOrgCode());
	        StringBuilder notificationLink = new StringBuilder();
	        String actionLink = "";
	         actionLink = notificationLink.append(pmsUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();
	        emailScenerioUtil.sendNotification(actionLink, loginUser,
	                expenses.getProjectMembers().getProjectMemberCandidteId().getConsultantUser(),
	                expenses.getProjectMembers().getProjectMemberCandidteId().getOrgLocationId(), notificationScenario,
	                new HashMap<String, String>());

	    }

	    @Override
	    public void sendReopenExpenseAcceptOrRejectMail(Expenses expenses, String reason, User superUser,
	            OrgLocations orgLoc, String type, User reopenedUser, User loginUser, String projectName) throws Exception {

	        EmailScenario emailScenario = null;
	        EmailScenario notificationScenario = null;
	        EmailMessage emailMessage = new EmailMessage();
	        emailMessage.setFirstName(reopenedUser.getFirstName());
	        emailMessage.setLastName(reopenedUser.getLastName());
	        emailMessage.setToEmail(reopenedUser.getEmail());
	        emailMessage.setFromEmail(loginUser.getEmail());
	        emailMessage.setCreatedBy(loginUser.getUserCode());
	        emailMessage.setFromName(orgLoc.getUser().getFirstName());
	        emailMessage.setCreatedBy(loginUser.getFullName());
	        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	        Map<String, String> globalVarsMap = new HashMap<String, String>();
	        globalVarsMap.put("employeeUser", expenses.getProjectMembers().getProjectMemberCandidteId().getFirstName());
	        globalVarsMap.put("approverUser", reopenedUser.getFirstName());
	        globalVarsMap.put("reopenUser", loginUser.getFullName());
	        globalVarsMap.put("projectName", projectName);
	        globalVarsMap.put("clientName", expenses.getClientName());

	        if (null != expenses.getExpenseId()) {
	            if (null != expenses.getStartDate()) {
	                expenses.setStartDateStr(expenses.getStartDate().toString());
	                globalVarsMap.put("startDate", expenses.getStartDateStr());
	            }
	        }
	        if (null != expenses.getExpenseId()) {
	            if (null != expenses.getEndDate()) {
	                expenses.setEndDateStr(expenses.getEndDate().toString());
	                globalVarsMap.put("endDate", expenses.getEndDateStr());
	            }
	        }
	        globalVarsMap.put("amount", (expenses.getTotalAmount()) + "");
	        globalVarsMap.put("reason", reason);
	        if (null != expenses.getExpenseId()) {
	            globalVarsMap.put("Expense_Id", expenses.getExpenseId());
	        }
	        String redirectURL = "/expensesApprovalList/";
	        Long redirectOrgLocationId = orgLoc.getId();
	        StringBuilder stringBuildforLink = new StringBuilder();
	        globalVarsMap.put("loginUrl", stringBuildforLink.append("<a href='")
	                .append(sourceleadLoginUrl + "?redirectURL=" + redirectURL + "&redirectOrgLocationId="
	                        + redirectOrgLocationId)
	                .append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
	                .toString());

	        // setting the Recipient information
	        InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

	        // getting the current EmailScenario by EmailScenario unique name and entity
	        // type
	        if (null != type && "accept".equalsIgnoreCase(type))
	            try {
	                {
	                    emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
	                            Constants.MAIL_REOPEN_APPROVED_EXPENSES_EMAIL_TO_APPROVERS, Constants.ENTITY_TYPE_EMAIL);
	                    notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
	                            Constants.MAIL_REOPEN_APPROVED_EXPENSES_EMAIL_TO_APPROVERS,
	                            Constants.ENTITY_TYPE_NOTIFICATION);
	                }
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        if (null != type && "reject".equalsIgnoreCase(type))
	            try {
	                {
	                    emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
	                            Constants.MAIL_REOPEN_REJECT_EXPENSES_EMAIL_TO_APPROVERS, Constants.ENTITY_TYPE_EMAIL);
	                    notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
	                            Constants.MAIL_REOPEN_REJECT_EXPENSES_EMAIL_TO_APPROVERS,
	                            Constants.ENTITY_TYPE_NOTIFICATION);
	                }
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        // covert subject,body and signature
	        String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(), globalVarsMap,
	                emailScenario);
	        String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap, emailScenario);
	        String signature = null;
	        if (StringUtils.isNotBlank(emailScenario.getSignature())) {
	            signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(), globalVarsMap,
	                    emailScenario);
	        }
	        String url = sourceleadUrl;

	        // construct HTML template using required data
	        String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
	                signature, url, orgLoc.getOrg(), orgLoc);
	        emailMessage.setSubject(subject);
	        emailMessage.setMessageBody(emailTemplate);

	        // calling the sendEmail method of emailScenerioUtil
	        emailScenerioUtil.sendEmail(emailMessage, orgLoc.getId(), iAdressArray, new ArrayList<EmailAttachment>());

	        // calling the sendNotification method of emailScenerioUtil
	        String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
	        notificationScenario.setBody(notificationBody);
	        StringBuilder notificationLink = new StringBuilder();
	        String actionLink = "";
	         actionLink = notificationLink.append(pmsUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();
	        emailScenerioUtil.sendNotification(actionLink, orgLoc.getUser(), reopenedUser, orgLoc, notificationScenario,
	                new HashMap<String, String>());

	    }
	}