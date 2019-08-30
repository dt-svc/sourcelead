package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.AvailableAssociatesDTO;
import com.bis.sourcelead.dto.ExpensesApproverHierarchyDTO;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.reportsDTO.PendingForExpenseApprovalReportDTO;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.ibm.icu.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.List;
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
 * @author Rajashekar
 * creating pdf table
 *
 */
@Component
public class PdfUtil {
	
	@Autowired
	UserRoleService userRoleService ;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
	ProjectBudgetService projectBudgetService;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
	public File createPdfTable(java.util.List<UserRole> activeList,
			java.util.List<UserRole> inactiveList, java.util.List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,
			String userState, OrgLocations orgLocations) throws Exception{
		String[] headers = Constants.USER_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    //DateFormat dateFormat =LoginSessionController.DATEFORMAT;

	    /*File file = new File("users_"+dateFormat.format(new Date())+".pdf");*/
	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("associates_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("associates_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
	    System.out.println("***************************************"+file.getAbsolutePath());
	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("Associates");
          

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocations.getOrgLocationName()).toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        
	        int inActiveCount = 0; 
            int activeCount = 0; 
            int notLoggedInCount = 0; 
	        List orderedList = new List(List.ORDERED);
	        //userRole list developed by laxman  
	        for(UserRole ur:userRolesList) {
	        	if("ACTIVE".equalsIgnoreCase(ur.getUserState().toString())){
	        		activeCount++;
	        	} 
	        	if("INACTIVE".equalsIgnoreCase(ur.getUserState().toString())){
	        		inActiveCount++;
	        	} 
	        	if("NOT_LOGGEDIN".equalsIgnoreCase(ur.getUserState().toString())){
	        		notLoggedInCount++;
	        	} 
	        }
	      
	        orderedList.add(new ListItem("Active:"+activeCount));
	        orderedList.add(new ListItem("Inactive:"+inActiveCount));
	        orderedList.add(new ListItem("Not Loggedin:"+notLoggedInCount));
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(10);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 5,5,5,5,3});
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
	        java.util.List<String[]> usersList = new ArrayList<>();
	        for(UserRole userRole : userRolesList){
	        	usersList.add(prepareUserStringArray(userRole,headers));
	        }
	        for (String[] userArray: usersList) {
                for (int i = 0; i < userArray.length; i++) {
                    String datum = userArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("Associates Table");
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
	/**
	 * Prepares Array to store data into the Pdf Table
	 * @param headers 
	 * @param orgLocations 
	 * @param userRole
	 * @return
	 * @throws Exception 
	 */
	private String[] prepareUserStringArray(UserRole userRoles, String[] headers) throws Exception {
		String userArray[] = new String[headers.length];
		//Set<Employees> employeeSet = userRoles.getUser().getEmployee();
		  Employees empl = employeesService.getEmployeeByUserId(userRoles.getUser().getId());
			 if(empl.getEmployeeId() !=null){
				 userArray[0] = empl.getEmployeeId();
			 }else{
				 userArray[0] = "--";
			 }
			 if(empl.getHireDate() !=null){
				 String dateOfJoin = commonUtil.dateStrFromOrgDateFormatter(empl.getOrgLocations().getDateFormat(),empl.getHireDate());
				 userArray[5] = dateOfJoin;
			 }else{
				 userArray[5] = "--";
			 }
			 if(empl.getRate() !=null && !"".equalsIgnoreCase(empl.getRate())){
				 StringBuilder ratePerSB = new StringBuilder();
				 userArray[6] = ratePerSB.append(empl.getRate()).append("/").append(empl.getRatePer()).toString();
			 }else{
				 userArray[6] = "--";
			 }
		   if(userRoles.getUser().getFirstName() !=null){
			   userArray[1] = userRoles.getUser().getFirstName();
		   }else{
			   userArray[1] = "--";
		   }
		   if(userRoles.getUser().getLastName() != null && !"".equalsIgnoreCase(userRoles.getUser().getLastName())){
			   userArray[2] = userRoles.getUser().getLastName();
		   }else{
			   userArray[2] = "--";
		   }
		   if(userRoles.getUser().getEmail() !=null){
			   userArray[3] = userRoles.getUser().getEmail();
		   }else{
			   userArray[3] = "--";
		   }
		   if(userRoles.getUser().getMobilePhone() != null && !"".equalsIgnoreCase(userRoles.getUser().getMobilePhone())){
			   userArray[4] = userRoles.getUser().getMobilePhone();
		   }else{
			   userArray[4] = "--";
		   }
		   /*if(userRoles.getUser().getLocation() !=null){
			   userArray[7] = userRoles.getUser().getLocation();
		   }else{
			   userArray[7] = "--";
		   }*/
		   if(userRoles.getRoles() != null){
			   userArray[7] = userRoles.getRoles().getDisplayName();
		   }else{
			   userArray[7] = "--";
		   }StringBuilder fullName=new StringBuilder();
		   if(userRoles.getReportingManager() !=null){
			   if(userRoles.getReportingManager().getLastName()!=null)
			   {
			   userArray[8] = fullName.append(userRoles.getReportingManager().getFirstName()).append(" ").append(userRoles.getReportingManager().getLastName()).toString();
			   }else
			   {
				   userArray[8] = userRoles.getReportingManager().getFirstName();
			   }
		   }else{
			   userArray[8] = "--";
		   }
		   
		   if(userRoles.getIsMailSent() != null && !"".equalsIgnoreCase(userRoles.getIsMailSent().toString()) && "N".equalsIgnoreCase(userRoles.getIsMailSent().toString()) && !"INACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
			   userArray[9] =  "Invite";
	   		   }else if((userRoles.getIsMailSent() != null && !"".equalsIgnoreCase(userRoles.getIsMailSent().toString()) && "Y".equalsIgnoreCase(userRoles.getIsMailSent().toString())) &&  (userRoles.getUserState() !=null && "NOT_LOGGEDIN".equalsIgnoreCase(userRoles.getUserState().toString()))){
	   		   userArray[9] =  "Resend";
	   		   }else if(userRoles.getUserState() !=null && "ACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
	   			 userArray[9] =  "Active";
	   		   }else if(userRoles.getUserState() !=null && "INACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
	   			userArray[9] =  "In-active";
	   		   }
		   
		return userArray;
		
	
	}
	
	
	
	
	
	public  File createCandidatePdfTable(java.util.List<Candidate> candidatesList, String orgName,String isAssociate) throws IOException {

		String[] headers = Constants.CANDIDATE_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    StringBuilder sbCand = new StringBuilder();
	    sbCand.append(orgName).append("_Candidate_").append(dateFormat.format(new Date()));
	    /*File file = new File(orgName+"_Candidate_"+dateFormat.format(new Date())+".pdf");*/

	    
	    StringBuilder fileSB = new StringBuilder();
	    File file;
	    if(isAssociate.equalsIgnoreCase(Constants.YES)){
	    	 file = File.createTempFile(fileSB.append(orgName).append("_Associate_").append(dateFormat.format(new Date())).toString(),".pdf");	
	    }else{
	    	 file = File.createTempFile(fileSB.append(orgName).append("_Candidate_").append(dateFormat.format(new Date())).toString(),".pdf");
	    }
	    

	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	        BaseColor myBordColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myBordColor);
	        StringBuilder sbCandStyle = new StringBuilder();
	        if(isAssociate.equalsIgnoreCase(Constants.YES)){
	        	sbCandStyle.append(orgName).append(" ").append("Associates");
	        }else{
	        	sbCandStyle.append(orgName).append(" ").append("Candidates");
	        }
	        

	        StringBuilder orgNameSB = new StringBuilder();
	        Paragraph para = null;
	        if(isAssociate.equalsIgnoreCase(Constants.YES)){
	        	para = new Paragraph(orgNameSB.append(orgName).append(" Associates").toString(),boldFont);
	        }else{
	        	para = new Paragraph(orgNameSB.append(orgName).append(" Candidates").toString(),boldFont);
	        }
            

            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(sbCandStyle.toString(),font));
	        cell1.setColspan(13);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 3, 3,5, 4, 3,4,3,5,3,4,4,5});
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> candidateList = new ArrayList<>();
	        for(Candidate candidate : candidatesList){
	        	candidateList.add(prepareStringArray(candidate,isAssociate));
	        }
	        
	        for (String[] canArray: candidateList) {
                for (int i = 0; i < canArray.length; i++) {
                    String datum = canArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        
	        if(isAssociate.equalsIgnoreCase(Constants.YES)){
	        	document.addTitle("Associate List Table");
	        }else{
	        	document.addTitle("Candiate List Table");
	        }
	        
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
	private String[] prepareStringArray(Candidate candidate, String isAssociate) {
		String candidateArray[] = new String[Constants.CANDIDATE_EXCEL_HEADERS.length];

		if(candidate.getFirstName() !=null && !"".equalsIgnoreCase(candidate.getFirstName())){
			candidateArray[0] = candidate.getFirstName();
		}else{
			candidateArray[0] = "--";
		}
		if(candidate.getLastName() !=null && !"".equalsIgnoreCase(candidate.getLastName())){
			candidateArray[1] = candidate.getLastName();
		}else{
			candidateArray[1] = "--";
		}
		if(candidate.getEmail() !=null && !"".equalsIgnoreCase(candidate.getEmail())){
			candidateArray[2] = candidate.getEmail();
		}else{
			candidateArray[2] = "--";
		}
		if(candidate.getMobilePhone() !=null && !"".equalsIgnoreCase(candidate.getMobilePhone())){
			candidateArray[3] = candidate.getMobilePhone();
		}
		StringBuilder loc = new StringBuilder();
		if(candidate.getCity() !=null && !"".equalsIgnoreCase(candidate.getCity())){
			candidateArray[4] = loc.append(candidate.getCity()).append(",").append(candidate.getState()).append(",").append(candidate.getCountry()).toString();
		}else if(candidate.getState() !=null && !"".equalsIgnoreCase(candidate.getState())){
			candidateArray[4] = loc.append(candidate.getState()).append(",").append(candidate.getCountry()).toString();
		}else if(candidate.getCountry() !=null && !"".equalsIgnoreCase(candidate.getCountry())){
			candidateArray[4] = candidate.getCountry();
		}else{
			candidateArray[4] = "--";
		}
		if(candidate.getDesignation() !=null && !"".equalsIgnoreCase(candidate.getDesignation())){
			candidateArray[5] = candidate.getDesignation();
		}else{
			candidateArray[5] = "--";
		}
		if(candidate.getCandidateEducation() !=null && candidate.getCandidateEducation().size() >0 ){
			Set<CandidateEducation> candidateEducation = candidate.getCandidateEducation();
			StringBuilder educations = new StringBuilder();
			for(CandidateEducation candidateEdu : candidateEducation){
				educations.append(candidateEdu.getName()).append(",");
			}
			String educationList = educations.toString();
			candidateArray[6] = educationList.substring(0, educations.length()-1);
		}else{
			candidateArray[6] = "--";
		}
		StringBuilder expSB = new StringBuilder();
		if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
			candidateArray[7] = expSB.append(candidate.getExperienceYears()).append(".").append(candidate.getExperienceMonths()).append("years").toString();
		}else{
			if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears())){
				candidateArray[7] = expSB.append(candidate.getExperienceYears()).append("years").toString();
			}else{
				if(candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
					candidateArray[7] = expSB.append(candidate.getExperienceMonths()).append("Months").toString();
				}else{
					candidateArray[7] = "--";
				}
			}
		}
		if(candidate.getSkillsName() !=null){
			StringBuilder skills = new StringBuilder();
			
		//	Set<CandidateSkills> candidateSkillsList = candidate.getCandidateSkills();
			
			String [] candidateSkillsList = candidate.getSkillsName().split(",");
		    for(String skill : candidateSkillsList){
		    	skills.append(skill).append(",");
		    }
		    candidateArray[8] = skills.toString().substring(0, skills.length()-1);
		}else{
			candidateArray[8] = "--";
		}
		if(candidate.getExpectedRate() !=null && !"".equalsIgnoreCase(candidate.getExpectedRate()) && candidate.getRateType() !=null && !"".equalsIgnoreCase(candidate.getRateType())){
			StringBuilder rateSB = new StringBuilder();
			candidateArray[9] = rateSB.append(candidate.getExpectedRate()).append("/").append(candidate.getRateType()).toString();
		}else{
			candidateArray[9] = "--";
		}
		if(null != candidate.getOrgRelationId()){
				OrgLocations orgLocations = null;
				if(candidate.getOrgRelationId().getResponderLocationId().getId().longValue() == candidate.getOrgLocationId().getId().longValue()){
					orgLocations = candidate.getOrgRelationId().getLocationId();
				 }else if(candidate.getOrgRelationId().getLocationId().getId().longValue() == candidate.getOrgLocationId().getId().longValue()){
					 orgLocations = candidate.getOrgRelationId().getResponderLocationId();
				 }
				if(null != orgLocations){
					candidateArray[10] = orgLocations.getOrgLocationName();
				}
		}else{
					candidateArray[10] = "--";
		}
		
		if(null != candidate.getSpocId()){
			if(null != candidate.getSpocId().getVendorUser().getLastName() && !"".equalsIgnoreCase(candidate.getSpocId().getVendorUser().getLastName())){
				StringBuilder vendorName = new StringBuilder();
				candidateArray[11] = vendorName.append(candidate.getSpocId().getVendorUser().getFirstName()).append(" ").append(candidate.getSpocId().getVendorUser().getLastName()).toString();
		}else if(null != candidate.getSpocId().getVendorUser().getFirstName() && !"".equalsIgnoreCase(candidate.getSpocId().getVendorUser().getFirstName())){
				candidateArray[11] = candidate.getSpocId().getVendorUser().getFirstName();
			}
		}else{
				candidateArray[11] = "--";
		}
		if(isAssociate.equalsIgnoreCase(Constants.YES)){
			if(null != candidate.getIsFavouriteAssociate() && candidate.getIsFavouriteAssociate() == 1){
				candidateArray[12] = "Y";
			}else{
				candidateArray[12] = "--";
			}
		}else if(null != candidate.getIsFavourite() && candidate.getIsFavourite() == 1){
				candidateArray[12] = "Y";
			}else{
				candidateArray[12] = "--";
			}
		
		return candidateArray;
		
	}
	public File getRequirementPdf(java.util.List<Requirement> requirementList, String orgName, TimeSheetSubmissionCount requirementSubCount) throws Exception {


		String[] headers = Constants.REQUIREMENT_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
	    StringBuilder sbReq =new StringBuilder();
	    sbReq.append(orgName).append("_Jobs_").append(dateFormat.format(new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append(orgName).append("_Jobs_").append(dateFormat.format(new Date())).toString(),".pdf");

	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	        BaseColor myBordColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myBordColor);
	        StringBuilder sbReqStyle = new StringBuilder();
	        sbReqStyle.append(orgName).append(" ").append("Jobs");

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgName).append(" Jobs.").toString(),boldFont);

            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
            List orderedList = new List(List.ORDERED);
	        orderedList.add(new ListItem("Draft :"+requirementSubCount.getDraftRequirementCount()));
	        orderedList.add(new ListItem("Open :"+requirementSubCount.getOpenRequirementCount()));
	        orderedList.add(new ListItem("New :"+requirementSubCount.getNewRequirementCount()));
	        orderedList.add(new ListItem("Submitted :"+requirementSubCount.getSubmittedRequirementCount()));
	        orderedList.add(new ListItem("On Hold :"+requirementSubCount.getOnHoldRequirementCount()));
	        orderedList.add(new ListItem("Cancel :"+requirementSubCount.getCancelRequirementCount()));
	        document.add(orderedList);
            PdfPTable table = new PdfPTable(headers.length);
            Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(sbReqStyle.toString(),font));
	        cell1.setColspan(13);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 3,5,3,5,5,3,3,5});
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> requirements = new ArrayList<>();
	        for(Requirement requirement : requirementList){
	        	requirements.add(prepareRequirementToArray(requirement));
	        }
	        
	        for (String[] reqArray: requirements) {
                for (int i = 0; i < reqArray.length; i++) {
                    String datum = reqArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        
	        document.addTitle("Jobs List Table");
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
	private String[] prepareRequirementToArray(Requirement requirement) throws Exception {
		String requirementArray[] = new String[Constants.REQUIREMENT_EXCEL_HEADERS.length];
		 java.util.List<String> requirementLocationsList = new ArrayList<>();
		 java.util.List<RequirementLocations> locationsList;
		locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
		if(requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())){
			requirementArray[0] = requirement.getJobId();
		}else{
			requirementArray[0] = "--";
		}
		if(requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			requirementArray[1] = requirement.getJobTitle();
		}else{
			requirementArray[1] = "--";
		}
		StringBuilder expSB = new StringBuilder();
		if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			requirementArray[2] = expSB.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).append(" years").toString(); 
		}else{
			if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())){
				requirementArray[2] =  expSB.append("0 - ").append(requirement.getMinExp()).append("years").toString();
			}else{
				if(requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
					requirementArray[2] = expSB.append("0 - ").append(requirement.getMaxExp()).append("years").toString();
				}
			}
		}
		
		if(requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()) && requirement.getRateType() != null && !"".equalsIgnoreCase(requirement.getRateType())){
			StringBuilder rateSB = new StringBuilder();
			requirementArray[3] =  rateSB.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString();
		}else{
			requirementArray[3] = "--";
		}
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).append(" ").toString();
							requirementArray[4] = location;
						}else{
							requirementArray[4] = "--";
						}
					}
				}
			
		
/*		if(requirement.getCountry() != null && !"".equalsIgnoreCase(requirement.getCountry())){
			requirementArray[4] = requirement.getCountry();
		}else{
			requirementArray[4] = "--";
		}*/
		if(requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())){
			requirementArray[5] = requirement.getSkills();  
		}else{
			requirementArray[5] = "--";
		}
		if(requirement.getManager() != null && !"".equalsIgnoreCase(requirement.getManager())){
			requirementArray[6] = requirement.getManager();
		}else{
			requirementArray[6] = "--";
		}
		if(requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())){
			requirementArray[7] = requirement.getPriority();
		}else{
			requirementArray[7] = "--";
		}
		/*if(requirement.getSubmissionTemplateId() != null ){
			requirementArray[8] = requirement.getSubmissionTemplateId().getTemplateName();
		}else{
			requirementArray[8] = "--";
		}*/
		if(requirement.getClientName() != null && !"".equalsIgnoreCase(requirement.getClientName())){
			requirementArray[8] = requirement.getClientName();
		}else{
			requirementArray[8] = "--";
		}
		if(requirement.getClientContactName() != null && !"".equalsIgnoreCase(requirement.getClientContactName())){
			requirementArray[9] = requirement.getClientContactName();
		}else{
			requirementArray[9] = "--";
		}
		if(requirement.getClientContactEmail() != null && !"".equalsIgnoreCase(requirement.getClientContactEmail())){
			requirementArray[10] = requirement.getClientContactEmail();
		}else{
			requirementArray[10] = "--";
		}
		if(requirement.getRequirementSubmissionStatus() != null && !"".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())){
			requirementArray[11] = requirement.getRequirementSubmissionStatus().toString();
		}else{
			requirementArray[11] = "--";
		}
		
		if(requirement.getRequirementCreatedDateToString() != null && !"".equalsIgnoreCase(requirement.getRequirementCreatedDateToString().toString())){
			requirementArray[12] = requirement.getRequirementCreatedDateToString();
		}else{
			requirementArray[12] = "--";
		}
		return requirementArray;
	}
	
	public File getClientVendorPdfFile(java.util.List<OrgRelations> clientsList, String orgName, String[] clientHeaders, String vendorType) throws DocumentException, IOException {
		Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    /*File file = new File(orgName+"_"+vendorType+"_"+dateFormat.format(new Date())+".pdf");*/
	    
	    StringBuilder sbcv = new StringBuilder();
	    sbcv.append(orgName).append("_").append(vendorType).append("_").append(dateFormat.format(new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append(orgName).append("_").append(vendorType).append("_").append(dateFormat.format(new Date())).toString(),".pdf");

	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder titleName = new StringBuilder();
	        titleName.append(orgName).append(" ").append(vendorType);

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgName).append(" ").append(vendorType).toString(),boldFont);

            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header 
            int[] columnWidths = new int[]{3, 5, 3,5, 2};
            PdfPTable table = getPdfTable(clientHeaders,columnWidths,titleName.toString()); 
	        table.completeRow();
	        java.util.List<String[]> clientList = new ArrayList<>();
	        for(OrgRelations orgRelations : clientsList){
	        	clientList.add(prepareClientsArray(orgRelations,clientHeaders));
	        }
	        
	        for (String[] canArray: clientList) {
                for (int i = 0; i < canArray.length; i++) {
                    String datum = canArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
//	        StringBuilder nameSb = new StringBuilder();
//	        document.addTitle(nameSb.append(vendorType).append("List Table").toString());
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	}catch (FileNotFoundException e) {
		logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
    } finally {
        document.close();
    }
		return file;	
    
	}
		
	private String[] prepareClientsArray(OrgRelations orgRelations, String[] headers) {
		
		String clientArray[] = new String[headers.length];
		if (StringUtils.isNotEmpty(orgRelations.getOrgName())) {
			clientArray[0] = orgRelations.getOrgName();
		}else{
			clientArray[0] = "--";
		}
		if (StringUtils.isNotEmpty(orgRelations.getWebSite())) {
			StringBuilder sb = new StringBuilder();
			clientArray[1] = sb.append("www.").append(orgRelations.getWebSite()).toString();
		}else{
			clientArray[1] = "--";
		}
		StringBuilder sb = new StringBuilder();
		if ((StringUtils.isNotEmpty(orgRelations.getCity())) && (StringUtils.isNotEmpty(orgRelations.getState())) && (StringUtils.isNotEmpty(orgRelations.getCountry()))){
			sb.append(orgRelations.getCity()).append(",").append(orgRelations.getState()).append(",").append(orgRelations.getCountry());
			clientArray[2] = sb.toString();
		}else if ((StringUtils.isNotEmpty(orgRelations.getCity())) && (StringUtils.isNotEmpty(orgRelations.getState()))){
			sb.append(orgRelations.getCity()).append(",").append(orgRelations.getState());
			clientArray[2] = sb.toString();
		}else if((StringUtils.isNotEmpty(orgRelations.getState())) && (StringUtils.isNotEmpty(orgRelations.getCountry()))){
			sb.append(orgRelations.getState()).append(",").append(orgRelations.getCountry());
			clientArray[2] = sb.toString();
		}else if ((StringUtils.isNotEmpty(orgRelations.getCity())) && (StringUtils.isNotEmpty(orgRelations.getCountry()))){
			sb.append(orgRelations.getCity()).append(",").append(orgRelations.getCountry());
			clientArray[2] = sb.toString();
		}else if(StringUtils.isNotEmpty(orgRelations.getCity())){
			clientArray[2] = orgRelations.getCity();
		}else if(StringUtils.isNotEmpty(orgRelations.getState())){
			clientArray[2] = orgRelations.getState();
		}else if(StringUtils.isNotEmpty(orgRelations.getCountry())){
			clientArray[2] = orgRelations.getCountry();
		}else{
			clientArray[2] = "--";
		}
		if (orgRelations.getLevel() != null && !"".equalsIgnoreCase(orgRelations.getLevel())) {
			clientArray[3] = orgRelations.getLevel();
		}else{
			clientArray[3] = "--";
		}
		if (orgRelations.getNoOfContatcs() != null) {
			clientArray[4] = orgRelations.getNoOfContatcs().toString();
		}else{
			clientArray[4] = "--";
		}
	
		return clientArray;
	}
	private PdfPTable getPdfTable(String[] headers, int[] columnWidths, String title) {
		PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        try {
        	    Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(title,font));
		        cell1.setColspan(8);
		        headerCellStyle(cell1);
	            table.addCell(cell1);
			table.setWidths(columnWidths);
			for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return table;
	}
	public File getClientVendorContactsPdfFile(java.util.List<OrgSpoc> vandorsContactList, String orgName,String vendorType, String[] headers) throws Exception {
		Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    /*File file = new File(orgName+"_"+vendorType+" Contacts_"+dateFormat.format(new Date())+".pdf");*/
	    StringBuilder sb = new StringBuilder();
	    sb.append(orgName).append("_").append(vendorType).append("Contacts_").append(dateFormat.format(new Date()));


	      StringBuilder fileSB = new StringBuilder();
		  File file = File.createTempFile(fileSB.append(orgName).append("_").append(vendorType).append(" Contacts_").append(dateFormat.format(new Date())).toString(),".pdf");

		  file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder titleName = new StringBuilder();
	        titleName.append(orgName).append(" ").append(vendorType).append(" ").append("Contacts");

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgName).append(" ").append(vendorType).append(" Contacts.").toString(),boldFont);

            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header 
            int[] columnWidths = new int[]{3, 5, 5,3, 2,3};
            PdfPTable table = getPdfTable(headers,columnWidths,titleName.toString()); 
	        table.completeRow();
	        java.util.List<String[]> cantactsList = new ArrayList<>();
	        for(OrgSpoc orgSpoc : vandorsContactList){
	        	cantactsList.add(prepareContactsArray(orgSpoc,headers));
	        }
	        
	        for (String[] canArray: cantactsList) {
                for (int i = 0; i < canArray.length; i++) {
                    String datum = canArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        
	        document.addTitle("Client Contacts List Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	}catch (FileNotFoundException e) {
		logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
    } finally {
        document.close();
    }
		return file;	
	}
	private String[] prepareContactsArray(OrgSpoc orgSpoc, String[] headers) throws Exception {
		String contactsArray[] = new String[headers.length];
		if (orgSpoc.getOrgName() != null && !"".equalsIgnoreCase(orgSpoc.getOrgName())) {
			contactsArray[0] = orgSpoc.getOrgName();
		}else{
			contactsArray[0] = "--";
		}
		if (orgSpoc.getSpocName() != null && !"".equalsIgnoreCase(orgSpoc.getSpocName())) {
			contactsArray[1] = orgSpoc.getSpocName();
		}else{
			contactsArray[1] = "--";
		}
		if (orgSpoc.getSpocEmail() != null && !"".equalsIgnoreCase(orgSpoc.getSpocEmail())) {
			contactsArray[2] = orgSpoc.getSpocEmail();
		}else{
			contactsArray[2] = "--";
		}
		if (orgSpoc.getSpocMobilePhone() != null && !"".equalsIgnoreCase(orgSpoc.getSpocMobilePhone())) {
			contactsArray[3] = orgSpoc.getSpocMobilePhone();
		}else{
			contactsArray[3] = "--";
		}
		if (orgSpoc.getOwnerName() != null && !"".equalsIgnoreCase(orgSpoc.getOwnerName())) {
			contactsArray[4] = orgSpoc.getOwnerName();
		}else{
			contactsArray[4] = "--";
		}
		
		/*UserRole userRole = userRoleService.getUserRoleByUserId(orgSpoc.getVendorUser().getId());
		
		if(null != userRole){
		if (userRole.getRoles().getDisplayName() != null && !"".equalsIgnoreCase(userRole.getRoles().getDisplayName())) {
			contactsArray[5] = userRole.getRoles().getDisplayName();
		}
		}else{
			contactsArray[5] = "--";
		}*/
		if (orgSpoc.getSpocStatus() != null && !"".equalsIgnoreCase(orgSpoc.getSpocStatus())) {
			contactsArray[5] = orgSpoc.getSpocStatus();
		}else{
			contactsArray[5] = "--";
		}
		return contactsArray;
	}
	public File getProjectEmployeesExportPdf(java.util.List<ProjectMembers> employeeList, String projectName,
			Projects project, String projectManager) throws DocumentException, IOException {
		String[] headers = Constants.PROJECT_ASSIGNED_EMPLOYEES;
	    Document document = new Document(PageSize.LETTER.rotate());
	    //SimpleDateFormat dateFormat=project.getOrgLocationsId().getLocFullFormDateFormatter();
	   // DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	    /*File file = new File(projectName+"_"+dateFormat.format(new Date())+".pdf");*/
	    StringBuilder sbpe = new StringBuilder();
	    sbpe.append(projectName).append("_").append(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append(projectName).append("_").append(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),new Date())).toString(),".pdf");

	    file.setReadable(true);
	    try {
	    	// Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font blue = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
	        StringBuilder projectNameSB = new StringBuilder();
	        Chunk blueText = new Chunk(projectNameSB.append("Project Name : " ).append(projectName).toString(), blue);
//            Paragraph para = new Paragraph();
//            para.add(blueText);
//            document.add(para);

	        
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor1 = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor1);
	        StringBuilder titleName = new StringBuilder();
	        titleName.append("Project Name : ").append(projectName);
            Paragraph para = new Paragraph(titleName.toString(),boldFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
	        List orderedList = new List(List.ORDERED);
	        orderedList.add(new ListItem("Project Name :"+projectName));
	        orderedList.add(new ListItem("Manager :"+projectManager));
	        if(project.getStartDate()!=null && project.getEndDate() !=null){
	        	StringBuilder dateSB = new StringBuilder();
	        	String convertedStartDate = commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),project.getStartDate());
	        	String convertedEndDate = commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),project.getEndDate());
	        	orderedList.add(new ListItem("Date :"+dateSB.append(convertedStartDate).append(" to ").append(convertedEndDate).toString()));
			 }else{
				 String convertedStartDate = commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),project.getStartDate());
				 orderedList.add(new ListItem("Date :"+convertedStartDate));
			 }
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        int[] columnWidths = new int[]{5, 5, 3,3, 3,3,3,3,3};
            PdfPTable table = getProjectPdfTable(headers,columnWidths,titleName.toString()); 
            java.util.List<String[]> assignedEmpList = new ArrayList<>();
            for(ProjectMembers projectMembers : employeeList){
            	assignedEmpList.add(prepareEmployeeArray(projectMembers,headers));
	        }
	        
	        for (String[] canArray: assignedEmpList) {
                for (int i = 0; i < canArray.length; i++) {
                    String datum = canArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
	        }
	        table.completeRow();
	        
	        document.addTitle("Project Employee Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	  
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        document.close();
	    }
	        
		return file;
	}
	
	    

	private PdfPTable getProjectPdfTable(String[] headers, int[] columnWidths, String title) {
		PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        try {
        	    Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(title,font));
		        cell1.setColspan(9);
		        headerCellStyle(cell1);
	            table.addCell(cell1);
			table.setWidths(columnWidths);
			for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return table;
}
	private String[] prepareEmployeeArray(ProjectMembers projectMember, String[] headers) {
			if(projectMember.getProjectMemberCandidteId().getConsultantUser() != null){
				 UserRole userRole;
				try {
					userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getProjectMemberCandidteId().getConsultantUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					 if(userRole != null){
						 projectMember.setUserRoleType(userRole.getUserType());
					 } 
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }else{
				 projectMember.setUserRoleType("Candidate");
			 }
		
		   String empArray[] = new String[headers.length];
			if (projectMember.getEmpName() != null && !"".equalsIgnoreCase(projectMember.getEmpName())) {
				empArray[0] = projectMember.getEmpName();
			}else{
				empArray[0] = "--";
			}
			if (projectMember.getStartDateStr() != null && !"".equalsIgnoreCase(projectMember.getStartDateStr())) {
				empArray[1] = projectMember.getStartDateStr();
			}else{
				empArray[1] = "--";
			}
			if (projectMember.getEndDateStr() != null && !"".equalsIgnoreCase(projectMember.getEndDateStr())) {
				empArray[2] = projectMember.getEndDateStr();
			}else{
				empArray[2] = "--";
			}
			
			if (projectMember.getCurrentBillRate() != null && projectMember.getCurrentBillRate() > 0 ) {
				empArray[3] = projectMember.getBillRateCurrency() +"  "+ projectMember.getCurrentBillRate() +"  "+projectMember.getRateType();
			}else {
				empArray[3] = "--";
						
			}
			if (projectMember.getPayRate() != null && projectMember.getPayRate() > 0 ) {
				empArray[4] = projectMember.getPayRateCurrency() +"  "+ projectMember.getPayRate() +"  "+ projectMember.getPayRateType();
			}else {
				empArray[4] = "--";
			}
			if (projectMember.getEstimatedHours() != null && projectMember.getEstimatedHours() > 0 ) {
				empArray[5] = projectMember.getEstimatedHours().toString();
			}else {
				empArray[5] = "--";
			}
			if (projectMember.getEstimatedBudget() != null && projectMember.getEstimatedBudget() > 0 ) {
				empArray[6] = projectMember.getEstimatedBudget().toString();
			}else {
				empArray[6] = "--";
			}
			if(projectMember.getCandidateId() != null){
				 Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId(), "APPROVED");
				    empArray[7] = String.valueOf(approvalTimesheetsSubmissionsCountAsEmployee);
			}else {
				empArray[7] = "--";
			}
			
			if (projectMember.getPercentageOfWork() != null) {
				empArray[8] = projectMember.getPercentageOfWork().toString();
			}else {
				empArray[8] = "--";
			}
			
		return empArray;
	}
	
	private static void headerCellStyle(PdfPCell cell){
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setPaddingTop(0f);
	    cell.setPaddingBottom(7f);
	    cell.setBackgroundColor(new BaseColor(0,121,182));
	    cell.setBorder(0);
	    cell.setBorderWidthBottom(2f);
	    }
	
	
	
	public File createProjectPdfTable(ResponseList<ProjectsDTO> projectsList, OrgLocations orgLocations)throws Exception {
		String[] headers = Constants.PROJECT_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());

	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("projects_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("projects_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("Projects");
          

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocations.getOrgLocationName()).toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        List orderedList = new List(List.ORDERED);
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(9);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3,5,5,3,3,3,5,4});
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
	        java.util.List<String[]> projectList = new ArrayList<>();
	        java.util.List<ProjectsDTO> list =  projectsList.getData();
	        for(ProjectsDTO projects : list){
	        	projectList.add(prepareProjectsStringArray(projects,headers,orgLocations));
	        }
	        for (String[] userArray: projectList) {
                for (int i = 0; i < userArray.length; i++) {
                    String datum = userArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("Projects Table");
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
	private String[] prepareProjectsStringArray(ProjectsDTO projects, String[] headers,OrgLocations orgLocations) throws Exception {
		String projectArray[] = new String[headers.length];
     	 ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
     	 if(projectClients == null) {
     		Projects clientProject = projectsService.getProjectById(projects.getId());
     		projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientProject.getOrgLocationsId().getId());
     	 }
     	 if(projects.getProjectSequeneNumber() != null) {
     		projectArray[0] = projects.getProjectSequeneNumber();
     	 }else {
     		projectArray[0] = "--";
     	 }
     	 
     	if(projects.getProjectName() != null) {
     		projectArray[1] = projects.getProjectName();
     	}else {
     		projectArray[1] = "--";

     	}
     	 
     	Set<ProjectApproverHierarchy> projectApproverHierarchies = projectClients.getProjectApproverHierarchyList();
	    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
	    	if(approverHierarchy.getLevel() == 1 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
	    		projects.setManagerLevel1(approverHierarchy.getApproverUserId().getFullName());
	    	}else if(approverHierarchy.getLevel() == 2 && approverHierarchy.getStatus().equalsIgnoreCase("1")){
	    		projects.setManagerLevel2(approverHierarchy.getApproverUserId().getFullName());
	    	}
	    }
	    String projectManagers = "";
	    if(projects.getManagerLevel1() != null){
  		  projectManagers = projects.getManagerLevel1();
 		  }
  	    if(projects.getManagerLevel2() != null ){
    	 projectManagers = projects.getManagerLevel2();
		 }
  	  
  	  if(projects.getManagerLevel1() != null  && projects.getManagerLevel2() != null ){
 		   projectManagers = projects.getManagerLevel1()+","+projects.getManagerLevel2();
	 	 }
	  	if(projectManagers != null) {
	 		projectArray[2] = projectManagers;
	 	}else {
	 		projectArray[2] = "--";
	
	 	}
	  	
	  	if(projects.getInvoiceFrequency() != null) {
	 		projectArray[3] = projects.getInvoiceFrequency();
	  	}else {
	 		projectArray[3] = "--";
	  	}
  	  
	  	
	  	
		OrgLocations orglo = orgLocationsDao.getOrgLocations(orgLocations.getId());
	  	if(projects.getOrgLocationsId() == orglo.getId().longValue()) {
			 java.util.List<ProjectBudget>  budgetList = projectBudgetService.getProjectBudgetByProjectTaskId(projects.getId(),orglo.getId());
			    if(budgetList != null && budgetList.size() > 0) {
			    	 double budgetSum = budgetList.stream().filter(p->p.getEstimatedBudgetValue() !=null).mapToDouble(p->p.getEstimatedBudgetValue()).sum();
						if(budgetList.size() >0) {
							ProjectBudget projectBudget = budgetList.iterator().next();
							projects.setProjectbudgetCurrency(projectBudget.getEstimatedBudgetCurrency());
						}
						projects.setProjectbudgetAmount(budgetSum);
						projectArray[4] = projects.getProjectbudgetCurrency()+ " " +budgetSum;
			    }else {
			    	projectArray[4] = "--";
			    }
		 }
	  	
  	   
		if(projects.getEndDateStr() != null && projects.getStartDateStr() != null){
			projectArray[5] = projects.getStartDateStr() +"  " +projects.getEndDateStr();
		}else {
			projectArray[5] = projects.getStartDateStr();
		}
		
		
		 if(projectClients.getOrgRelationId() != null){
    		 
 			if(null != projectClients.getOrgRelationId().getLocationId() && projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
 				projects.setClient(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
 				projectArray[6] = projects.getClient();
				}if(null != projectClients.getOrgRelationId().getResponderLocationId() && projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					projects.setClient(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
					projectArray[6] =projects.getClient();
				}
 	  }else {
			projectArray[6] = "--";
 	  }
		 
		 if(projects.getProjectStatus().toString() != null){
				projectArray[7] = projects.getProjectStatus().toString();
			}else {
				projectArray[7] = "--";
			}
		
		return projectArray;
	}
	public File createProjectTasksPdfTable(ResponseList<ProjectTasks> responseTaskList, OrgLocations orgLocations) throws Exception {
		String[] headers = Constants.PROJECT_TASKS_PDF_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());

	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("projects_Tasks_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("project_Tasks_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("Project_Tasks");
          

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocations.getOrgLocationName()).toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        List orderedList = new List(List.ORDERED);
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(9);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{5,3,3,5,3});
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
	        java.util.List<String[]> arrayTaskLists = new ArrayList<>();
	        for(ProjectTasks tasks : responseTaskList.getData()){
	        	arrayTaskLists.add(prepareProjectTasksStringArray(tasks,headers,orgLocations));
	        }
	        for (String[] userArray: arrayTaskLists) {
                for (int i = 0; i < userArray.length; i++) {
                    String datum = userArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("Projects Table");
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
	private String[] prepareProjectTasksStringArray(ProjectTasks projectTassks, String[] headers, OrgLocations orgLocations) {
		String projectTaskArray[] = new String[headers.length];
		
		if(projectTassks.getGeneratedId() != null) {
			projectTaskArray[0] = projectTassks.getGeneratedId() +"-"+ projectTassks.getTaskName(); 
		}else {
			projectTaskArray[0] = projectTassks.getTaskName(); 
		}
		projectTaskArray[1] = projectTassks.getTaskTypes(); 
		if(projectTassks.getTaskPriotiry() != null) {
			projectTaskArray[2] = projectTassks.getTaskPriotiry(); 
		}else {
			projectTaskArray[2] = "--"; 
		}
		if(projectTassks.getAssignedTaskUsers() != null) {
			String assignedTo = projectTassks.getAssignedTaskUsers().toString().replaceAll("\\[", "").replaceAll("\\]","");
			projectTaskArray[3] = assignedTo; 
		}else {
			projectTaskArray[3] = "--"; 
		}
		projectTaskArray[4] = projectTassks.getTaskStaus(); 
		
		return projectTaskArray;
	}

	public File creategetLeaveBalancePdfTable(Response<LeaveBalanceReportDTO> LeaveBalanceReport,OrgLocations orgLocation,String topleave) throws Exception{
	
	 java.util.List<String> headers = new ArrayList<String>();
		headers.add("Employee Name");
		headers.add("Employee Type");
		headers.add("Department");
		headers.add("Role");
		for(String str:LeaveBalanceReport.getObject().getLeaveTypeNames())
		{
		headers.add(str);
		}
		headers.add("TotalBalanceLeaves");
	    Document document = new Document(PageSize.LETTER.rotate());
	    StringBuilder sbUser =  new StringBuilder();
	    if(topleave ==null) {
	    sbUser.append("Balance_Leave_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date()));
	    }else {
	    	sbUser.append("Top_Leave_Taken_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date()));
	    }

	    StringBuilder fileSB = new StringBuilder();
	    File file =null;
	    if(topleave ==null) {
	     file = File.createTempFile(fileSB.append("Balance_Leave_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
	    }else {
	    	 file = File.createTempFile(fileSB.append("Top_Leave_Taken_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
	    }
	    System.out.println("***************************************"+file.getAbsolutePath());
	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLocation.getOrgLocationName()).append(" ").append("Top_Leave_Taken_Report");
          

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocation.getOrgLocationName()).toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	      
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.size());
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(headers.size());
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
//	        for (int i = 0; i<headers.size(); i++) {
//	        	table.setWidths(new int [] {5});
//	        }
	        //table.setWidths(new int[]{5, 5, 5,5});
	        //for(String str:LeaveBalanceReport.getObject().getLeaveTypeNames())
			 //{
				// int i=4;
				 //table.setWidths(new int[] {5}); 
				 //i++;
			 //}
            table.addCell(cell1);
	        for (String head:headers) {
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(head.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
	            cell.setBackgroundColor(myBorderColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> lrvList = new ArrayList<>();
	        java.util.List<LeaveBalanceReportDTO> list =  LeaveBalanceReport.getObject().getUsers();
	        for(LeaveBalanceReportDTO leaveBalanceReportDTO : list){
	        	lrvList.add(prepareLeaveBalanceReportStringArray(leaveBalanceReportDTO,LeaveBalanceReport,headers));
	        }
	        for (String[] userArray: lrvList) {
                for (int i = 0; i < userArray.length; i++) {
                    String datum = userArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("Balance Leaves Table");
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


	
public File createLeaveReportPdfTable(ResponseList<LeaveReportView> leaveReportsList,OrgLocations orgLocations) throws Exception{
	
		
		
		String[] headers = Constants.LEAVE_REPORT_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("Leave_Report_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("Leave_Report_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
	    System.out.println("***************************************"+file.getAbsolutePath());
	    file.setReadable(true);
	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("Leave_Report");
          

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocations.getOrgLocationName()).toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	      
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(13);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 5, 5,5, 5, 4, 4, 5, 5, 6, 3, 4,4});
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
	        java.util.List<String[]> lrvList = new ArrayList<>();
	        java.util.List<LeaveReportView> list = leaveReportsList.getData();
	        for(LeaveReportView lrv : list){
	        	lrvList.add(prepareLeaveReportStringArray(lrv,headers));
	        }
	        for (String[] userArray: lrvList) {
                for (int i = 0; i < userArray.length; i++) {
                    String datum = userArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("LeaveReport Table");
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

private String[] prepareLeaveReportStringArray(LeaveReportView lrv, String[] headers) throws Exception {
	String userArray[] = new String[headers.length];
		 if(lrv.getEid() != null && !lrv.getEid().isEmpty()){
			 userArray[0] = lrv.getEid().toString();
		 }else{
			 userArray[0] = "--";
		 }
		   if(lrv.getName() !=null){
			   userArray[1] = lrv.getName();
		   }else{
			   userArray[1] = "--";
		   }
		   if(lrv.getUserDeptName() != null){
			   userArray[2] = lrv.getUserDeptName();
		   }else{
			   userArray[2] = "--";
		   }
		   if(lrv.getLeaveTypeName() !=null){
			   userArray[3] = lrv.getLeaveTypeName();
		   }else{
			   userArray[3] = "--";
		   }
		 if(lrv.getReason() !=null){
			 userArray[4] = lrv.getReason();
		 }else{
			 userArray[4] = "--";
		 }
		   if(lrv.getLeaveTypeBillable() != null){
			   userArray[5] = lrv.getLeaveTypeBillable();
		   }else{
			   userArray[5] = "--";
		   }
		 if(lrv.getFromDateStr() != null){
			 userArray[6] = lrv.getFromDateStr();
		 }else{
			 userArray[6] = "--";
		 }
	   if(lrv.getToDateStr() != null){
		   userArray[7] = lrv.getToDateStr();
	   }else{
		   userArray[7] = "--";
	   }
	   if(lrv.getAppliedOnDateStr() !=null){
			   userArray[8] = lrv.getAppliedOnDateStr();
	   }else{
		   userArray[8] = "--";
	   }
	   if(lrv.getNoOfDays() !=null){
	   userArray[9] = lrv.getNoOfDays();
	   }
	   else{
		   userArray[9] = "0.0";
	   }
	   if(lrv.getStatus() !=null){
		   userArray[10] = lrv.getStatus();
   }else{
	   userArray[10] = "--";
   }
	   if(lrv.getApprover() !=null){
		   userArray[11] = lrv.getApprover();
   }else{
	   userArray[11] = "--";
   }
	   if(lrv.getApprovedOnDateStr() !=null){
		   userArray[12] = lrv.getApprovedOnDateStr();
   }else{
	   userArray[12] = "--";
   }
	return userArray;
	

}

private String[] prepareLeaveBalanceReportStringArray(LeaveBalanceReportDTO leaveBalanceReportDTO,Response<LeaveBalanceReportDTO> LeaveBalanceReport, java.util.List<String> headers) throws Exception {
	String userArray[] = new String[headers.size()];
		 if(leaveBalanceReportDTO.getEmpName() !=null){
			 userArray[0] = leaveBalanceReportDTO.getEmpName();
		 }else{
			 userArray[0] = "--";
		 }
		   if(leaveBalanceReportDTO.getEmpType() !=null){
			   userArray[1] = leaveBalanceReportDTO.getEmpType();
		   }else{
			   userArray[1] = "--";
		   }
		   if(LeaveBalanceReport.getObject().getDepart().entrySet() != null) {
		   for(Map.Entry<Long, String> k: LeaveBalanceReport.getObject().getDepart().entrySet()){
				Long ltv =   k.getKey();
				String lti = k.getValue();
				if(ltv == leaveBalanceReportDTO.getUserId().longValue()){ 
					userArray[2]=lti;
		   }}}else{
			   userArray[2] = "--";
		   }
		   if(leaveBalanceReportDTO.getRole() !=null){
			   userArray[3] = leaveBalanceReportDTO.getRole();
		   }else{
			   userArray[3] = "--";
		   }
		   float lo =0;
		   int i=4;
		   float l=0;
		   for (String str :LeaveBalanceReport.getObject().getLeaveTypeNames()) {
				for(Map.Entry<String, Float> k: LeaveBalanceReport.getObject().getMap().entrySet()){
					String[] ltName =   k.getKey().split("-");
					if((Long.parseLong(ltName[1]) == leaveBalanceReportDTO.getUserId().longValue()) && (ltName[0].equalsIgnoreCase(str))){
						 l = k.getValue();
						 userArray[i]=Float.toString(l);
					       lo = lo+l;
						 }else if(ltName[0] == null &&str == null)
							 userArray[i]="--";		
				          }
			i++;} 
		   userArray[i]=Float.toString(lo);
	   
	   
	return userArray;
	

}
public File createDayWiseLeaveReportPdfTable(ResponseList<LeaveReportView> leaveReportsList,OrgLocations orgLocations) throws Exception{

	
	
	
	String[] headers = Constants.DAILY_WISE_LEAVE_REPORT_HEADERS;
    Document document = new Document(PageSize.LETTER.rotate());
    StringBuilder sbUser =  new StringBuilder();
    sbUser.append("Leave_Report_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));

    StringBuilder fileSB = new StringBuilder();
    File file = File.createTempFile(fileSB.append("Daily_Wise_Leave_Report_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
    System.out.println("***************************************"+file.getAbsolutePath());
    file.setReadable(true);
    
    try {
        // Get an instance of PdfWriter and create a Table.pdf file as an output.
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        BaseColor myColor = WebColors.getRGBColor("#000000");
        boldFont.setColor(myColor);
        StringBuilder userStyle = new StringBuilder();
        userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("Day_Wise_Leave_Report");
      

        StringBuilder orgNameSB = new StringBuilder();
        Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocations.getOrgLocationName()).toString());
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
      
        // Create an instance of PdfPTable. After that we transform the header and
        PdfPTable table = new PdfPTable(headers.length);
        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
        cell1.setColspan(14);
        headerCellStyle(cell1);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{5, 5, 5,5, 5, 4, 4, 5, 5, 6, 3, 4, 3,5});
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
        java.util.List<String[]> lrvList = new ArrayList<>();
        java.util.List<LeaveReportView> list = leaveReportsList.getData();
        for(LeaveReportView lrv : list){
        	lrvList.add(prepareDailyWiseLeaveReportStringArray(lrv,headers));
        }
        for (String[] userArray: lrvList) {
            for (int i = 0; i < userArray.length; i++) {
                String datum = userArray[i];
                PdfPCell cell = new PdfPCell();
                cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                table.addCell(cell);
            }
            table.completeRow();
        }
        document.addTitle("DayWiseLeaveReport Table");
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
private String[] prepareApplicantJoinedListStringArray(RequirementSubmissionHistory rsh, String[] headers, OrgLocations loginOrgLocations) throws Exception {
	String userArray[] = new String[headers.length];
		
	StringBuilder candidateName = new StringBuilder();
	
	//Candidate Name
	if(rsh.getSubmissionId().getCandidate().getLastName() != null) {
		userArray[0] = candidateName.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString();
	}else {
		userArray[0] = candidateName.append(rsh.getSubmissionId().getCandidate().getFirstName()).toString();
	}
	
	//Candidate Email
	 userArray[1] = rsh.getSubmissionId().getCandidate().getEmail();
			
			
			if(rsh.getSubmissionId().getCandidate().getMobilePhone() != null) {
				userArray[2] = rsh.getSubmissionId().getCandidate().getMobilePhone();
			}
			else{
				 userArray[2] = "--";
			}
			//Requirement Name
			if(rsh.getSubmissionId().getRequirement() != null) {
				if(rsh.getSubmissionId().getRequirement().getGeneratedId() != null) {
					 userArray[3] = rsh.getSubmissionId().getRequirement().getGeneratedId() +" - "+rsh.getSubmissionId().getRequirement().getJobTitle();
				}else {
					 userArray[3] = rsh.getSubmissionId().getRequirement().getJobTitle();
				}
			}
			
			if(rsh.getSubmissionId().getRequirement().getJobRate() != null){
				userArray[4] = rsh.getSubmissionId().getRequirement().getJobRate();
			}else{
				userArray[4] = "--";
			}
		
			
			if(rsh.getSubmissionId().getRequirement().getProcess() != null){
				userArray[5] = rsh.getSubmissionId().getRequirement().getProcess();
			}else{
				userArray[5] = "--";
			}
		
				//Clinet Name
				if(rsh.getSubmissionId().getRequirement().getClientId() != null) {
					if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
						if (rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId() != null) {
							 userArray[6] = rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName();
						}else {
							userArray[6] = "--";
						}
					}
					if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
						if (rsh.getSubmissionId().getRequirement().getClientId().getLocationId() != null) {
							 userArray[6] = rsh.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName();
						}else {
							userArray[6] = "--";
						}
					}
				}else {
					userArray[6] = "--";
				}
				RequirementInterview reqInterview=requirementInterviewService.getinterviewBySubmissionId(rsh.getSubmissionId().getId());
				
				if(reqInterview != null && reqInterview.getDateOfInterview() !=  null){
					 userArray[7] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),reqInterview.getDateOfInterview());
				}
				else{
					userArray[7] = "--";
				}
		
				RequirementOffers reqOffers=requirementOffersService.getRequirementOfferSubmissionById(rsh.getSubmissionId().getId());
				
				if(reqOffers != null && reqOffers.getAmount() !=  null && reqOffers.getPaymentType() !=null && reqOffers.getPaymentMode() != null){
					 userArray[8] = reqOffers.getAmount()+"-"+reqOffers.getPaymentType()+"/"+reqOffers.getPaymentMode();
				}
				else{
					 userArray[8] = "--";
				}
				//Sourece Name
			   userArray[9] = rsh.getSubmissionId().getCreatedUser();
			 //Submitted On
		   userArray[10] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate());
		   if(rsh.getSubmissionId().getJoinedDate()  != null){
			   userArray[11] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getJoinedDate());
			}
			else{
				userArray[11] = "--";
				}
			if(rsh.getSubmissionId().getRequirement().getClientId() != null){
				String payTerm=rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm();
				payTerm=payTerm.replaceAll("\\D", "");
				if(!payTerm.isEmpty()){
					userArray[12] = payTerm.toString();
				}
				else{
					 userArray[12] = "--";
				}
			}
			else{
				 userArray[12] = "--";
			}
			if(rsh.getSubmissionId().getRequirement().getPayOutPeriod() != null &&  rsh.getSubmissionId().getJoinedDate()  != null){
				String payTerm=rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm();
				 Integer result = 0;
                 try {
                     payTerm=payTerm.replaceAll("\\D", "");
                     result = Integer.parseInt(payTerm);
                 } catch (Exception e) {
                     result = 0;
                 }
                 if(result != 0){
     				Date generatedInvoiceDate=CalendarUtil.addDaysForGenerateInvoice(rsh.getSubmissionId().getJoinedDate(), result);
     				DateTime dtOrg = new DateTime(generatedInvoiceDate);
     				DateTime generatedInvoiceDatePlusOne = dtOrg.plusDays(1);
     			   userArray[13] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),generatedInvoiceDatePlusOne.toDate());

                 }
             	else{
    				userArray[13] = "--";	
    			}
			}
			else{
				userArray[13] = "--";	
			}
	   
	return userArray;
	

}


public File ApplicantsJoinedListPdfTable(ResponseList<RequirementSubmissionHistory> rsh, OrgLocations loginOrgLocations) throws Exception{

	
	
	
	String[] headers = Constants.APPLICANTS_JOINED_HEADERS;
    Document document = new Document(PageSize.LETTER.rotate());
    StringBuilder sbUser =  new StringBuilder();
    sbUser.append("Applicants_Joined_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date()));

    StringBuilder fileSB = new StringBuilder();
    File file = File.createTempFile(fileSB.append("Applicants_Joined_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".pdf");
    System.out.println("***************************************"+file.getAbsolutePath());
    file.setReadable(true);
    
    try {
        // Get an instance of PdfWriter and create a Table.pdf file as an output.
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        BaseColor myColor = WebColors.getRGBColor("#000000");
        boldFont.setColor(myColor);
        StringBuilder userStyle = new StringBuilder();
        userStyle.append(loginOrgLocations.getOrgLocationName()).append(" ").append("Applicants_Joined_Report");
      

        StringBuilder orgNameSB = new StringBuilder();
        Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(loginOrgLocations.getOrgLocationName()).toString());
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
      
        // Create an instance of PdfPTable. After that we transform the header and
        PdfPTable table = new PdfPTable(headers.length);
        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
        cell1.setColspan(14);
        headerCellStyle(cell1);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{5, 5, 5,5, 5, 5, 4, 4, 5, 5, 6, 3, 4,5});
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
        java.util.List<String[]> lrvList = new ArrayList<>();
        java.util.List<RequirementSubmissionHistory> list = rsh.getData();
        for(RequirementSubmissionHistory lrv : list){
        	lrvList.add(prepareApplicantJoinedListStringArray(lrv,headers,loginOrgLocations));
        }
        for (String[] userArray: lrvList) {
            for (int i = 0; i < userArray.length; i++) {
                String datum = userArray[i];
                PdfPCell cell = new PdfPCell();
                cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                table.addCell(cell);
            }
            table.completeRow();
        }
        document.addTitle("Applicants Joined Table");
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


private String[] prepareDailyWiseLeaveReportStringArray(LeaveReportView lrv, String[] headers) throws Exception {
	String userArray[] = new String[headers.length];
		 if(lrv.getName() !=null){
			 userArray[0] = lrv.getName();
		 }else{
			 userArray[0] = "--";
		 }
		   if(lrv.getEmploymentType() !=null){
			   userArray[1] = lrv.getEmploymentType();
		   }else{
			   userArray[1] = "--";
		   }
		   if(lrv.getUserDeptName() != null){
			   userArray[2] = lrv.getUserDeptName();
		   }else{
			   userArray[2] = "--";
		   }
		   if(lrv.getUserRoleName() !=null){
			   userArray[3] = lrv.getUserRoleName();
		   }else{
			   userArray[3] = "--";
		   }
		 if(lrv.getLeaveTypeName() !=null){
			 userArray[4] = lrv.getLeaveTypeName();
		 }else{
			 userArray[4] = "--";
		 }
		   if(lrv.getLeaveTypeBillable() != null){
			   userArray[5] = lrv.getLeaveTypeBillable();
		   }else{
			   userArray[5] = "--";
		   }
		 if(lrv.getDuration() != null){
			 userArray[6] = lrv.getDuration();
		 }else{
			 userArray[6] = "--";
		 }
	   if(lrv.getFromDateStr() != null){
		   userArray[7] = lrv.getFromDateStr();
	   }else{
		   userArray[7] = "--";
	   }
	   if(lrv.getToDateStr() !=null){
			   userArray[8] = lrv.getToDateStr();
	   }else{
		   userArray[8] = "--";
	   }
	   if(lrv.getAppliedOnDateStr() !=null){
		   userArray[9] = lrv.getAppliedOnDateStr();
   }else{
	   userArray[9] = "--";
   }
	   if(lrv.getReason() !=null){
		   userArray[10] = lrv.getReason();
   }else{
	   userArray[10] = "--";
   }
	   if(lrv.getStatus() !=null){
		   userArray[11] = lrv.getStatus();
   }else{
	   userArray[11] = "--";
   }
	   if(lrv.getApprover() !=null){
		   userArray[12] = lrv.getApprover();
   }else{
	   userArray[12] = "--";
   }
	   if(lrv.getApprovedOnDateStr() !=null && lrv.getStatus().equalsIgnoreCase("APPROVED")){
		   userArray[13] = lrv.getApprovedOnDateStr();
   }else{
	   userArray[13] = "--";
   }
	   
	   
	return userArray;
	

}

public File createAllApplicantJoinedListPdfTable(ResponseList<ApplicantsJoinedReportDTO> userDTOList,OrgLocations orgLocations,Date[] searchDates) throws Exception{

	
	 java.util.List<String> headerList = new LinkedList<>();
	 headerList.add("Recruiter Name");
	 String headers[] =null;
	 int[] integers =null;
 if(!userDTOList.getData().isEmpty()){
	 for(String date :userDTOList.getData().get(0).getDatesList()) {
		 headerList.add(date.split("<<>>").length > 1 ? date.split("<<>>")[0] : date);
	 }
	 headerList.add("No Of Jobs");
	 headerList.add("Total Offer");
	 headerList.add("Total Joiners");
	 headerList.add("Total Leaves");
	 
	 headers = new String[headerList.size()];
	 
	 integers = new int[headers.length];
	 
	 int k=0;
	 for(String header : headerList) {
		 headers[k] = header;
		 k++;
	 }
	 }
	 
	 
	
    Document document = new Document(PageSize.LETTER.rotate());
    StringBuilder sbUser =  new StringBuilder();
    sbUser.append("APPLICANTS_JOINED_LIST_REPORT_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()));

    StringBuilder fileSB = new StringBuilder();
    File file = File.createTempFile(fileSB.append("Applicants_Joined_List_Report_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
    System.out.println("***************************************"+file.getAbsolutePath());
    file.setReadable(true);
    
    try {
        // Get an instance of PdfWriter and create a Table.pdf file as an output.
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        BaseColor myColor = WebColors.getRGBColor("#000000");
        boldFont.setColor(myColor);
        StringBuilder userStyle = new StringBuilder();
        userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("Applicants_Joined_List_Report");
      

        StringBuilder orgNameSB = new StringBuilder();
        Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(orgLocations.getOrgLocationName()).toString());
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
      
        // Create an instance of PdfPTable. After that we transform the header and
        if(headers != null && headers.length != 0){
        	 PdfPTable table = new PdfPTable(headers.length);
             Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
             PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
             cell1.setColspan(integers.length);
             headerCellStyle(cell1);
             table.setWidthPercentage(100);
             int[] testarray = new int[integers.length];
             for (int i = 0; i < integers.length; i++) {  
         	    testarray[i]=5;
         	    System.out.println("www "+testarray[i]);
         	}
             
             table.setWidths(testarray);
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
             java.util.List<String[]> lrvList = new ArrayList<>();
             java.util.List<ApplicantsJoinedReportDTO> list = userDTOList.getData();
             for(ApplicantsJoinedReportDTO lrv : list){
             	lrvList.add(prepareApplicantsJoinedListStringArray(lrv,headers,searchDates,orgLocations));
             }
             for (String[] userArray: lrvList) {
                 for (int i = 0; i < userArray.length; i++) {
                     String datum = userArray[i];
                     PdfPCell cell = new PdfPCell();
                     cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                     table.addCell(cell);
                 }
                 table.completeRow();
             }
             document.addTitle("DayWiseLeaveReport Table");
             table.setSpacingBefore(15f);
             document.add(table);
        }
      
       
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


private String[] prepareApplicantsJoinedListStringArray(ApplicantsJoinedReportDTO lrv, String[] headers,Date[] searchDates,OrgLocations orgLocations) throws Exception {
	String userArray[] = new String[headers.length];
	int cellCount = 0;
		 if(lrv.getName()!=null){
			 userArray[cellCount++] = lrv.getName();
		 }else{
			 userArray[0] = "--";
		 }
		/*   if(searchDates !=null){
			java.util.List<Date> duration =  CalendarUtil.getListOfDaysBetweenTwoDates(searchDates[0],searchDates[1]);
			for(Date  holidayDate :duration) {
				String Date= commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),holidayDate).toString();
			}
			userArray[1] = "--";
		   }else{
			   userArray[1] = "--";
		   }*/
		 if(lrv.getDatesList()!=null && !lrv.getDatesList().isEmpty()){
				for(String date : lrv.getDatesList()) {
					userArray[cellCount++] =date.split("<<>>").length > 1 ? date.split("<<>>")[1] : date;
				}
//			 userArray[cellCount++] = lrv.getFromDate().toString();
		 }
		 /*else{
			 userArray[cellCount++] = "--";
		 }*/
		   if(lrv.getRequirementCount() != null){
			   userArray[cellCount++] = lrv.getTotalRequirementCount().toString();
		   }else{
			   userArray[cellCount++] = "0";
		   }
		   if(lrv.getOfferCount() !=null){
			   userArray[cellCount++] = lrv.getOfferCount().toString();
		   }else{
			   userArray[cellCount++] = "0";
		   }
		 if(lrv.getJoinedCount() !=null){
			 userArray[cellCount++] = lrv.getJoinedCount().toString();
		 }else{
			 userArray[cellCount++] = "0";
		 }
		  /* if(lrv.getLeaveTypeBillable() != null){
			   userArray[5] = lrv.getLeaveTypeBillable();
		   }else{
			   userArray[5] = "--";
		   }*/
		 
		   java.util.List<LeaveType> leaveTypesList=leaveTypeService.getLeaveTypeListByLocationId(orgLocations.getId().toString());
			 
			java.util.List<AppliedLeaveDateView> appliedLeaves = null;
			float appliedNoofLeaves = 0;
			float appliedLeaveCount = 0;
			float totalLeavesCount=0;
		   for(LeaveType leaveType:leaveTypesList){
			   AssociateBalanceLeaveView ablv = new AssociateBalanceLeaveView();
				ablv.setIncludeWeekoff(leaveType.getIncludeWeekoff());
				ablv.setIncludeHolidays(leaveType.getIncludeHolidays());
			   appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), lrv.getFromDate(), lrv.getToDate(), lrv.getUserId().longValue(),orgLocations.getOrgId());
			if(!appliedLeaves.isEmpty()){
				   appliedNoofLeaves = (float)associateBalanceLeaveService.getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,lrv.getFromDate(),lrv.getToDate(),leaveType);
			}
				totalLeavesCount=totalLeavesCount+appliedNoofLeaves;
		   }
		   
		   if(totalLeavesCount !=0){
				 userArray[cellCount++] = String.valueOf(totalLeavesCount);
			 }else{
				 userArray[cellCount++] = "0";
			 }
	   
	return userArray;
	

}


	public File getProjectReportListOfPdf(java.util.List<ProjectReportDTO> projectReportDTO, User user, OrgLocations orgLocation, String isGlobalAccess) {

		try {
			long totalBillable = 0l;
			long totalNonbillable = 0l;
			String[] headers = Constants.PROJECT_REPORT_EXPORT_HEADDERS;
			Document document = new Document(PageSize.LETTER.rotate());

			StringBuilder sbUser = new StringBuilder();
			sbUser.append("Project Report")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));

			StringBuilder fileSB = new StringBuilder();
			File file = File.createTempFile(fileSB.append("Project Report")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date())).toString(),
					".pdf");

			file.setReadable(true);

			try {
				// Get an instance of PdfWriter and create a Table.pdf file as an output.
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();

				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
				BaseColor myColor = WebColors.getRGBColor("#000000");
				boldFont.setColor(myColor);
				StringBuilder userStyle = new StringBuilder();
				userStyle.append(orgLocation.getOrgLocationName()).append(" ").append("- Project Report");

				// Create an instance of PdfPTable. After that we transform the header and
				PdfPTable table = new PdfPTable(headers.length);
				Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
				PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(), font));
				cell1.setColspan(18);
				headerCellStyle(cell1);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 5, 5, 5, 7, 5, 5, 5, 5, 5, 5 });
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
				java.util.List<String[]> reportList = prepareProjectReportListStringArray(projectReportDTO, headers,
						orgLocation, user, isGlobalAccess);
				if (reportList != null && reportList.size() > 0) {
					for (String[] reportArray : reportList) {
						for (int i = 0; i < reportArray.length; i++) {
							String datum = reportArray[i];
							PdfPCell cell = new PdfPCell();
							cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
							table.addCell(cell);
						}
						table.completeRow();
					}
				}
				document.addTitle("Project Report Table");
				table.setSpacingBefore(15f);
				document.add(table);
				logger.info("Pdf table created !!");
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
			}
			return file;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	private java.util.List<String[]> prepareProjectReportListStringArray(
			java.util.List<ProjectReportDTO> projectReportDTO, String[] headers, OrgLocations orgLocation, User user,
			String isGlobalAccess) {

		java.util.List<String[]> arrayList = new ArrayList<>();

		for (ProjectReportDTO report : projectReportDTO) {
			String reportArray[] = new String[headers.length];
			if (report.getProjectSeqNo() != null) {
				reportArray[0] = report.getProjectSeqNo();
			} else {
				reportArray[0] = "--";
			}

			if (report.getProjectName() != null) {
				reportArray[1] = report.getProjectName();
			} else {
				reportArray[1] = "--";
			}

			if (report.getProjectStatus() != null) {
				reportArray[2] = report.getProjectStatus();
			} else {
				reportArray[2] = "--";
			}

			if (report.getProjectManagerNames() != null) {
				reportArray[3] = report.getProjectManagerNames();
			} else {
				reportArray[3] = "--";
			}

			if (report.getProjectStartDate() != null) {
				reportArray[4] = report.getProjectStartDate();
			} else {
				reportArray[4] = "--";
			}

			if (report.getProjectEndDate() != null) {
				reportArray[5] = report.getProjectEndDate();
			} else {
				reportArray[5] = "--";
			}

			if (report.getProjectMembers() != null) {
				reportArray[6] = report.getProjectMembers();
			} else {
				reportArray[6] = "--";
			}

			if (report.getMemberStartDate() != null) {
				reportArray[7] = report.getMemberStartDate();
			} else {
				reportArray[7] = "--";
			}

			if (report.getMemberEndDate() != null) {
				reportArray[8] = report.getMemberEndDate();
			} else {
				reportArray[8] = "--";
			}

			if (StringUtils.isNotBlank(report.getMemberStatus())) {
				if (Integer.parseInt(report.getMemberStatus()) == Constants.RECORD_ACTIVE) {

					reportArray[9] = "Active";
				} else {
					reportArray[9] = "In-Active";
				}

			}
			arrayList.add(reportArray);
		}
		return arrayList;
	}

	public File getJobFairListOfPdf(java.util.List<JobFairDTO> jobFairDTO, User user, OrgLocations orgLocation) {
		try {
			String[] headers = Constants.JOBFAIR_EXPORT_HEADERS;
			Document document = new Document(PageSize.LETTER.rotate());

			StringBuilder sbUser = new StringBuilder();
			sbUser.append("Project Report")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));

			StringBuilder fileSB = new StringBuilder();
			DateFormat dateFormat = new SimpleDateFormat(orgLocation.getDateFormat());
			File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName() + "_JobFair_"+ dateFormat.format(new Date()))
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date())).toString(),
					".pdf");

			file.setReadable(true);

			try {
				// Get an instance of PdfWriter and create a Table.pdf file as an output.
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();

				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
				BaseColor myColor = WebColors.getRGBColor("#000000");
				boldFont.setColor(myColor);
				StringBuilder userStyle = new StringBuilder();
				userStyle.append(orgLocation.getOrgLocationName()).append(" ").append("- JobFair");

				// Create an instance of PdfPTable. After that we transform the header and
				PdfPTable table = new PdfPTable(headers.length);
				Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
				PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(), font));
				cell1.setColspan(18);
				headerCellStyle(cell1);
				table.setWidthPercentage(100);
				table.setWidths(new int[] {3, 2, 2, 2, 2, 2, 2, 3, 2});
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
				java.util.List<String[]> reportList = prepareJobFairListStringArray(jobFairDTO, headers, orgLocation,
						user);
				if (reportList != null && reportList.size() > 0) {
					for (String[] reportArray : reportList) {
						for (int i = 0; i < reportArray.length; i++) {
							String datum = reportArray[i];
							PdfPCell cell = new PdfPCell();
							cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
							table.addCell(cell);
						}
						table.completeRow();
					}
				}
				document.addTitle("Project Report Table");
				table.setSpacingBefore(15f);
				document.add(table);
				logger.info("Pdf table of JobFair created !!");
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
			}
			return file;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	private java.util.List<String[]> prepareJobFairListStringArray(java.util.List<JobFairDTO> jobFairDTOList,
			String[] headers, OrgLocations orgLocation, User user) {

		java.util.List<String[]> arrayList = new ArrayList<>();

		for (JobFairDTO jobFairDTO : jobFairDTOList) {
			String reportArray[] = new String[headers.length];
			reportArray[0] = jobFairDTO.getGeneratedId() != null && !jobFairDTO.getGeneratedId().isEmpty()
					? jobFairDTO.getGeneratedId()
					: "----";

			reportArray[1] = jobFairDTO.getName() != null && !jobFairDTO.getName().isEmpty() ? jobFairDTO.getName()
					: "----";

			reportArray[2] = jobFairDTO.getStartDate() != null && !jobFairDTO.getStartDate().isEmpty()
					? jobFairDTO.getStartDate()
					: "----";

			reportArray[3] = jobFairDTO.getEndDate() != null && !jobFairDTO.getEndDate().isEmpty()
					? jobFairDTO.getEndDate()
					: "----";

			reportArray[4] = jobFairDTO.getJobFairRequirementList() != null
					&& !jobFairDTO.getJobFairRequirementList().isEmpty()
							? jobFairDTO.getJobFairRequirementList().size() + ""
							: "0";

			reportArray[5] = jobFairDTO.getApplicantCount() + "";

			reportArray[6] = jobFairDTO.getOfferedCount() + "";

			reportArray[7] = jobFairDTO.getVenue() != null && !jobFairDTO.getVenue().isEmpty() ? jobFairDTO.getVenue()
					: "----";

			reportArray[8] = jobFairDTO.getStatus() == 1 ? "OPEN" : "CLOSE";

			arrayList.add(reportArray);
		}
		return arrayList;
	}
	public File getAssetsReportListOfPdf(java.util.List<AssetsReportDTO> assetsReportDTO, User user, OrgLocations orgLocation,String isGlobalAccess) {
		
		try {
			long totalBillable = 0l;
			long totalNonbillable = 0l;
			String[] headers = Constants.ASSETS_REPORT_EXPORT_HEADDERS;
			Document document = new Document(PageSize.LETTER.rotate());

			StringBuilder sbUser = new StringBuilder();
			sbUser.append("Assets Report")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));

			StringBuilder fileSB = new StringBuilder();
			File file = File.createTempFile(fileSB.append("Assets Report")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date())).toString(),
					".pdf");

			file.setReadable(true);

			try {
				// Get an instance of PdfWriter and create a Table.pdf file as an output.
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();

				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
				BaseColor myColor = WebColors.getRGBColor("#000000");
				boldFont.setColor(myColor);
				StringBuilder userStyle = new StringBuilder();
				userStyle.append(orgLocation.getOrgLocationName()).append(" ").append("- Assets Report");

				// Create an instance of PdfPTable. After that we transform the header and
				PdfPTable table = new PdfPTable(headers.length);
				Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
				PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(), font));
				cell1.setColspan(18);
				headerCellStyle(cell1);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 5, 5, 5, 5, 5, 5, 5, 5,7,5 });
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
				java.util.List<String[]> reportList = prepareAssetsReportListStringArray(assetsReportDTO, headers,orgLocation, user, isGlobalAccess);
				if (reportList != null && reportList.size() > 0) {
					for (String[] reportArray : reportList) {
						for (int i = 0; i < reportArray.length; i++) {
							String datum = reportArray[i];
							PdfPCell cell = new PdfPCell();
							cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
							table.addCell(cell);
						}
						table.completeRow();
					}
				}
				document.addTitle("Project Report Table");
				table.setSpacingBefore(15f);
				document.add(table);
				logger.info("Pdf table created !!");
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
			}
			return file;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	private java.util.List<String[]> prepareAssetsReportListStringArray(java.util.List<AssetsReportDTO> assetsReportDTO,
			String[] headers, OrgLocations orgLocation, User user, String isGlobalAccess) {
	

		java.util.List<String[]> arrayList = new ArrayList<>();

		for (AssetsReportDTO report : assetsReportDTO) {
			String reportArray[] = new String[headers.length];
			if (report.getEmployeeId() != null) {
				reportArray[0] = report.getEmployeeId();
			} else {
				reportArray[0] = "--";
			}

			if (report.getEmployeeName() != null) {
				reportArray[1] = report.getEmployeeName();
			} else {
				reportArray[1] = "--";
			}

			if (report.getAssetName() != null) {
				reportArray[2] = report.getAssetName();
			} else {
				reportArray[2] = "--";
			}
			
			if (report.getCategory() != null) {
				reportArray[3] = report.getCategory();
			} else {
				reportArray[3] = "--";
			}

			if (report.getSerialNo() != null) {
				reportArray[4] = report.getSerialNo();
			} else {
				reportArray[4] = "--";
			}

			
			if (report.getAssignedOn() != null) {
				reportArray[5] = report.getAssignedOn();
			} else {
				reportArray[5] = "--";
			}

			if (report.getReturnedOn() != null) {
				reportArray[6] = report.getReturnedOn();
			} else {
				reportArray[6] = "--";
			}

			if (report.getShift() != null) {
				reportArray[7] = report.getShift();
			} else {
				reportArray[7] = "--";
			}

			if (report.getDocument() != null) {
				reportArray[8] = report.getDocument();
			} else {
				reportArray[8] = "--";
			}

			if (report.getAssignBy()!= null) {
				reportArray[9] = report.getAssignBy();
			} else {
				reportArray[9] = "--";
			}

		
			arrayList.add(reportArray);
		}
		return arrayList;
	}
	
	
	public File createCandidatePdfTable(java.util.List<MarketplacePurchaseOrder> purchaseCandidatesList,Long orgLocationId) throws Exception {
		
		OrgLocations orgLocation = orgLocationsDao.getOrgLocations(orgLocationId);
			
		try {
			String[] headers = Constants.PURCHASE_CANDIDATE_HEADERS;
			Document document = new Document(PageSize.LETTER.rotate());

			StringBuilder sbUser = new StringBuilder();
			sbUser.append("Purchased Candidate")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));

			StringBuilder fileSB = new StringBuilder();
			DateFormat dateFormat = new SimpleDateFormat(orgLocation.getDateFormat());
			File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName() + "PurchaseCandidate"+ dateFormat.format(new Date()))
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date())).toString(),
					".pdf");

			file.setReadable(true);

			try {
				// Get an instance of PdfWriter and create a Table.pdf file as an output.
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();

				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
				BaseColor myColor = WebColors.getRGBColor("#000000");
				boldFont.setColor(myColor);
				StringBuilder userStyle = new StringBuilder();
				userStyle.append(orgLocation.getOrgLocationName()).append(" ").append("- PurchaseCandidate");

				// Create an instance of PdfPTable. After that we transform the header and
				PdfPTable table = new PdfPTable(headers.length);
				Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
				PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(), font));
				cell1.setColspan(18);
				headerCellStyle(cell1);
				table.setWidthPercentage(100);
				table.setWidths(new int[] {3, 2, 3, 2, 2, 2, 2, 3, 2});
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
				java.util.List<String[]> reportList = purchasecandidateListStringArray(purchaseCandidatesList, headers, orgLocation);
				if (reportList != null && reportList.size() > 0) {
					for (String[] reportArray : reportList) {
						for (int i = 0; i < reportArray.length; i++) {
							String datum = reportArray[i];
							PdfPCell cell = new PdfPCell();
							cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
							table.addCell(cell);
						}
						table.completeRow();
					}
				}
				document.addTitle("Project Report Table");
				table.setSpacingBefore(15f);
				document.add(table);
				logger.info("Pdf table of JobFair created !!");
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
			}
			return file;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	private java.util.List<String[]> purchasecandidateListStringArray(
			java.util.List<MarketplacePurchaseOrder> purchaseCandidatesList, String[] headers,
			OrgLocations orgLocation) {


		
		java.util.List<String[]> arrayList = new ArrayList<>();

		for (MarketplacePurchaseOrder report : purchaseCandidatesList) {
			String reportArray[] = new String[headers.length];
			if (report.getOrderNo() != null) {
				reportArray[0] = report.getOrderNo();
			} else {
				reportArray[0] = "--";
			}

			if (report.getOrderDate() != null) {
				reportArray[1] = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), report.getOrderDate());
			} else {
				reportArray[1] = "--";
			}

			if (report.getCompanyName() != null) {
				reportArray[2] = report.getCompanyName();
			} else {
				reportArray[2] = "--";
			}
			
			if(report.getTotalresume() != null)
			{
				reportArray[3] = report.getTotalresume();
			}else {
				reportArray[3] = "--";
			}
		
			if (report.getTotalPrice() != null) {
				reportArray[4] = String.valueOf(report.getTotalPrice());
			} else {
				reportArray[4] = "--";
			}           

			if (report.getServiceFee() != null) {
				reportArray[5] = String.valueOf(report.getServiceFee());
			} else {
				reportArray[5] = "--";
			}			

			if (report.getDiscount() != null) {
				reportArray[6] = String.valueOf(report.getDiscount());
			} else {
				reportArray[6] = "--";
			}
					
			if (report.getGrandTotal() != null) {
				reportArray[7] = String.valueOf(report.getGrandTotal());
			} else {
				reportArray[7] = "--";
			}
			
			if (report.getPaymentStatus() != null) {
				reportArray[8] = report.getPaymentStatus();
			} else {
				reportArray[8] = "--";
			}

			
		
			arrayList.add(reportArray);
		}
		return arrayList;
		
		
	}
	
	// Recruiter reports main list export
		public File recruiterReprtsListPdfTable(ResponseList<UserDTO> userDTOList, OrgLocations loginOrgLocations,Date[] searchDates,String roleNames,String teamNames) throws Exception{

			String[] headers = Constants.RECRUITER_REPORTS_HEADERS;
		    Document document = new Document(PageSize.LETTER.rotate());
		    StringBuilder sbUser =  new StringBuilder();
		    sbUser.append("Recruiter_Reports_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date()));

		    StringBuilder fileSB = new StringBuilder();
		    File file = File.createTempFile(fileSB.append("Recruiter_Reports_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
		    
		    try {
		        // Get an instance of PdfWriter and create a Table.pdf file as an output.
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append(loginOrgLocations.getOrgLocationName()).append(" ").append("Recruiter_Report");
		      
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fromDate = sdf.format(searchDates[0]);
				String toDate = sdf.format(searchDates[1]);

		        StringBuilder orgNameSB = new StringBuilder();
		        Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(loginOrgLocations.getOrgLocationName()).toString());
		        para.setAlignment(Element.ALIGN_CENTER);
		        document.add(para);
		        
		        StringBuilder dateSB = new StringBuilder();
		        Paragraph paraDate = new Paragraph(dateSB.append("Date : ").append(fromDate).append(" To ").append(toDate).toString());
		        paraDate.setAlignment(Element.ALIGN_LEFT);
		        document.add(paraDate);
		        
		        
		        StringBuilder roleSB = new StringBuilder();
		        if(StringUtils.isNotBlank(roleNames)){
		        Paragraph paraRole = new Paragraph(roleSB.append("Roles : ").append(roleNames).toString());
		        paraRole.setAlignment(Element.ALIGN_LEFT);
		        document.add(paraRole);
		        }else{
		        	Paragraph paraRole = new Paragraph(roleSB.append("Roles : ").append("--").toString());
			        paraRole.setAlignment(Element.ALIGN_LEFT);
			        document.add(paraRole);	
		        }
		        
		        StringBuilder teamSB = new StringBuilder();
		        if(StringUtils.isNotBlank(teamNames)){
		        Paragraph paraTeam = new Paragraph(teamSB.append("Team : ").append(teamNames).toString());
		        paraTeam.setAlignment(Element.ALIGN_LEFT);
		        document.add(paraTeam);
		        }else{
		        	Paragraph paraTeam = new Paragraph(teamSB.append("Team : ").append("--").toString());
			        paraTeam.setAlignment(Element.ALIGN_LEFT);
			        document.add(paraTeam);	
		        }
		        // Create an instance of PdfPTable. After that we transform the header and
		        PdfPTable table = new PdfPTable(headers.length);
		        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(14);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
		        table.setWidths(new int[]{4,3,4,4,4,4,4,3,4,3,4,3,4});
		        table.addCell(cell1);
		        for (int i = 0; i < headers.length; i++) {
		            String header = headers[i];
		            PdfPCell cell = new PdfPCell();
		            cell.setGrayFill(0.9f);
		            cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
		            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
		            cell.setBackgroundColor(myBorderColor);
		            table.addCell(cell);
		        }
		        table.completeRow();
		        java.util.List<String[]> dataList = new ArrayList<>();
		        java.util.List<UserDTO> list = userDTOList.getData();
		        for(UserDTO data : list){
		        	dataList.add(prepareRecruiterReportsListStringArray(data,headers,loginOrgLocations));
		        }
		        for (String[] userArray: dataList) {
		            for (int i = 0; i < userArray.length; i++) {
		                String datum = userArray[i];
		                PdfPCell cell = new PdfPCell();
		                cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
		                table.addCell(cell);
		            }
		            table.completeRow();
		        }
		        document.addTitle("Recruiter Reports Table");
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
		
		
		private String[] prepareRecruiterReportsListStringArray(UserDTO userDto, String[] headers, OrgLocations loginOrgLocations) throws Exception {
			String userArray[] = new String[headers.length];
				
			StringBuilder associateName = new StringBuilder();
			
			//Associate Name
				userArray[0] = associateName.append(userDto.getFullName()).toString();
			
			//Requirement Count
			 userArray[1] = userDto.getUserRequirementsCount().toString();
			 
			 //Candidate Count
			 userArray[2] = userDto.getUserCandidatesCount().toString();
			 
			 //Total Applicant Count
			 userArray[3] = userDto.getUserApplicantsCount().toString();
			 
			 //Shortlist count
			 userArray[4] = userDto.getUserShortlistedApplicantsCount().toString();
			 
			 //Submit to client Count
			 userArray[5] = userDto.getUserSubmittedApplicantsCount().toString();
			 
			 //Interview  Count
			 userArray[6] = userDto.getUserInterviewedApplicantsCount().toString();
			 
			 //Offer count
			 userArray[7] = userDto.getUserOfferedApplicantsCount().toString();
			 
			//Rejected Count
			 userArray[8] = userDto.getUserRejectedApplicantsCount().toString();
			 
			 //Joined count
			 userArray[9] = userDto.getUserApplicantsJoinedCount().toString();
			 
			 //Backout Count
			 userArray[10] = userDto.getUserApplicantsBackOutCount().toString();
			 
			 //Onhold  Count
			 userArray[11] = userDto.getUserOnHoldApplicantsCount().toString();
			 
			 //No response count
			 userArray[12] = userDto.getUserNoResponseApplicantsCount().toString();
			   
			return userArray;
			

		}
		
		// Recruiter reports candidate list export 
		public File recruiterReprtsCandidateListPdfTable(ResponseList<Candidate> candidateList, OrgLocations loginOrgLocations,Date[] searchDates) throws Exception{

			String[] headers = Constants.RECRUITER_REPORTS_CANDIDATE_HEADERS;
		    Document document = new Document(PageSize.LETTER.rotate());
		    StringBuilder sbUser =  new StringBuilder();
		    sbUser.append("Recruiter_Report_Candidate_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date()));

		    StringBuilder fileSB = new StringBuilder();
		    File file = File.createTempFile(fileSB.append("Recruiter_Report_Candidate_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
		    
		    try {
		        // Get an instance of PdfWriter and create a Table.pdf file as an output.
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append(loginOrgLocations.getOrgLocationName()).append(" ").append("Recruiter_Report_Candidate");
		      
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fromDate = sdf.format(searchDates[0]);
				String toDate = sdf.format(searchDates[1]);

		        StringBuilder orgNameSB = new StringBuilder();
		        Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(loginOrgLocations.getOrgLocationName()).toString());
		        para.setAlignment(Element.ALIGN_CENTER);
		        document.add(para);
		        
		        StringBuilder dateSB = new StringBuilder();
		        Paragraph paraDate = new Paragraph(dateSB.append("Date : ").append(fromDate).append(" To ").append(toDate).toString());
		        paraDate.setAlignment(Element.ALIGN_LEFT);
		        document.add(paraDate);
		        
		        
		        // Create an instance of PdfPTable. After that we transform the header and
		        PdfPTable table = new PdfPTable(headers.length);
		        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(14);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
		        table.setWidths(new int[]{4,4,4,4,4,4,4,4,4,4,4,4,4});
		        table.addCell(cell1);
		        for (int i = 0; i < headers.length; i++) {
		            String header = headers[i];
		            PdfPCell cell = new PdfPCell();
		            cell.setGrayFill(0.9f);
		            cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
		            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
		            cell.setBackgroundColor(myBorderColor);
		            table.addCell(cell);
		        }
		        table.completeRow();
		        java.util.List<String[]> dataList = new ArrayList<>();
		        java.util.List<Candidate> list = candidateList.getData();
		        for(Candidate data : list){
		        	dataList.add(prepareRecruiterReportsCandidateListStringArray(data,headers,loginOrgLocations));
		        }
		        for (String[] userArray: dataList) {
		            for (int i = 0; i < userArray.length; i++) {
		                String datum = userArray[i];
		                PdfPCell cell = new PdfPCell();
		                cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
		                table.addCell(cell);
		            }
		            table.completeRow();
		        }
		        document.addTitle("Recruiter Reports Table");
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
		
		private String[] prepareRecruiterReportsCandidateListStringArray(Candidate candidate, String[] headers, OrgLocations loginOrgLocations) throws Exception {
			String userArray[] = new String[headers.length];
				
			 userArray[0] = candidate.getFullName();
			
			 userArray[1] = candidate.getEmail();
			 
			 if(StringUtils.isNotBlank(candidate.getMobilePhone())){
			 userArray[2] = candidate.getMobilePhone();
			 }else{
			 userArray[2] = "---"; 
			 }
			 userArray[3] = candidate.getDesignationType();
			 if(candidate.getOrgRelationId() !=null){
			 OrgRelations orgRelations =orgRelationsService.getOrgRelationById(candidate.getOrgRelationId().getId()); 
			 userArray[4] = orgRelations.getResponderLocationId().getOrg().getOrgName();
			 }else{
			 userArray[4] = "---";	 
			 }
			 if(candidate.getSpocId() !=null){
			 OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(candidate.getSpocId().getId());
			 userArray[5] = orgSpoc.getVendorUser().getFullName();
			 }else{
			 userArray[5] = "---"; 
			 }
			 if(StringUtils.isNotBlank(candidate.getLocation())){
			 userArray[6] = candidate.getLocation();
			 }else{
			 userArray[6] = "---"; 
			 }
			 if(StringUtils.isNotBlank(candidate.getDesignation())){
			 userArray[7] = candidate.getDesignation();
			 }else{
			 userArray[7] = "---"; 
				}
			 if(StringUtils.isNotBlank(candidate.getCurrentRate())){
			 userArray[8] = candidate.getCurrentRate();
			 }else{
			 userArray[8] = "---"; 
			 }
			 if(StringUtils.isNotBlank(candidate.getExpectedRate())){
			 userArray[9] = candidate.getExpectedRate();
			 }else{
			 userArray[9] = "---"; 
			 }
			 if(StringUtils.isNotBlank(candidate.getExperienceYears())){
			 userArray[10] = candidate.getExperienceYears();
			 }else{
			 userArray[10] = "---";	 
			 }
			 if(StringUtils.isNotBlank(candidate.getPrimarySkill())){
			 userArray[11] = candidate.getPrimarySkill();
			 }else{
			 userArray[11] = "---";	 
			 }
			 userArray[12] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), candidate.getUpdatedDate());
			   
			return userArray;
			

		}
		
		// Recruiter reports Applicants list export 
				public File recruiterReprtsApplicantsListPdfTable(ResponseList<RequirementSubmissionHistory> historyList, OrgLocations loginOrgLocations,Date[] searchDates,String toStage) throws Exception{
					
					String[] headers = null;
					if(toStage.equals("SUBMISSION")){
					 headers = Constants.RECRUITER_REPORTS_ALL_APPLICANTS_HEADERS;
					}else if(toStage.equals("SHORTLIST")){
					headers = Constants.RECRUITER_REPORTS_SHORTLIST_HEADERS;
					}else if(toStage.equals("SUBMIT_TO_CLIENT")){
					headers = Constants.RECRUITER_REPORTS_SUBMIT_TO_CLIENT_HEADERS;	
					}else if(toStage.equals("INTERVIEW")){
					headers = Constants.RECRUITER_REPORTS_INTERVIEW_HEADERS;	
					}else if(toStage.equals("OFFERMADE")){
					headers = Constants.RECRUITER_REPORTS_OFFERMADE_HEADERS;	
					}else if(toStage.equals("REJECTED")){
					headers = Constants.RECRUITER_REPORTS_REJECTED_HEADERS;	
					}else if(toStage.equals("JOINED")){
					headers = Constants.RECRUITER_REPORTS_JOINED_HEADERS;	
					}else if(toStage.equals("BACKOUT")){
					headers = Constants.RECRUITER_REPORTS_BACKOUT_HEADERS;	
					}
					
				    Document document = new Document(PageSize.LETTER.rotate());
				    StringBuilder sbUser =  new StringBuilder();
				    sbUser.append("Recruiter_Report_Applicants_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date()));

				    StringBuilder fileSB = new StringBuilder();
				    File file = File.createTempFile(fileSB.append("Recruiter_Report_Applicants_List_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".pdf");
				    file.setReadable(true);
				    
				    try {
				        // Get an instance of PdfWriter and create a Table.pdf file as an output.
				        PdfWriter.getInstance(document, new FileOutputStream(file));
				        document.open();

				        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
				        BaseColor myColor = WebColors.getRGBColor("#000000");
				        boldFont.setColor(myColor);
				        StringBuilder userStyle = new StringBuilder();
				        userStyle.append(loginOrgLocations.getOrgLocationName()).append(" ").append("Recruiter_Report_Applicants");
				      
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String fromDate = sdf.format(searchDates[0]);
						String toDate = sdf.format(searchDates[1]);

				        StringBuilder orgNameSB = new StringBuilder();
				        Paragraph para = new Paragraph(orgNameSB.append("Organization : ").append(loginOrgLocations.getOrgLocationName()).toString());
				        para.setAlignment(Element.ALIGN_CENTER);
				        document.add(para);
				        
				        StringBuilder dateSB = new StringBuilder();
				        Paragraph paraDate = new Paragraph(dateSB.append("Date : ").append(fromDate).append(" To ").append(toDate).toString());
				        paraDate.setAlignment(Element.ALIGN_LEFT);
				        document.add(paraDate);
				        
				        
				        // Create an instance of PdfPTable. After that we transform the header and
				        PdfPTable table = new PdfPTable(headers.length);
				        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
				        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
				        cell1.setColspan(14);
				        headerCellStyle(cell1);
				        table.setWidthPercentage(100);
				        if(toStage.equals("SUBMISSION") || toStage.equals("SHORTLIST") || toStage.equals("INTERVIEW")){
				        table.setWidths(new int[]{4,4,4,4,4,4,4,4,4});
				        }else if(toStage.equals("SUBMIT_TO_CLIENT") || toStage.equals("JOINED") || toStage.equals("OFFERMADE")){
				         table.setWidths(new int[]{4,4,4,4,4,4,4,4,4,4});	
				        }else if(toStage.equals("REJECTED") || toStage.equals("BACKOUT")){
				         table.setWidths(new int[]{4,4,4,4,4,4,4,4,4,4,4});	
				        }
				        table.addCell(cell1);
				        for (int i = 0; i < headers.length; i++) {
				            String header = headers[i];
				            PdfPCell cell = new PdfPCell();
				            cell.setGrayFill(0.9f);
				            cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
				            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
				            cell.setBackgroundColor(myBorderColor);
				            table.addCell(cell);
				        }
				        table.completeRow();
				        java.util.List<String[]> dataList = new ArrayList<>();
				        java.util.List<RequirementSubmissionHistory> list = historyList.getData();
				        for(RequirementSubmissionHistory data : list){
				        	if(toStage.equals("SUBMISSION") || toStage.equals("SHORTLIST") || toStage.equals("INTERVIEW")){
				        	dataList.add(prepareRecruiterReportsApplicantsListStringArray(data,headers,loginOrgLocations,toStage));
				        	}else if(toStage.equals("SUBMIT_TO_CLIENT") || toStage.equals("JOINED") || toStage.equals("OFFERMADE")){
					        dataList.add(prepareRecruiterReportsApplicantsListStringArrayOne(data,headers,loginOrgLocations,toStage));	
					        }else if(toStage.equals("REJECTED") || toStage.equals("BACKOUT")){
					        dataList.add(prepareRecruiterReportsApplicantsListStringArrayTwo(data,headers,loginOrgLocations,toStage));	
					        }
				        }
				        for (String[] userArray: dataList) {
				            for (int i = 0; i < userArray.length; i++) {
				                String datum = userArray[i];
				                PdfPCell cell = new PdfPCell();
				                cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
				                table.addCell(cell);
				            }
				            table.completeRow();
				        }
				        document.addTitle("Recruiter Reports Table");
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
			
				// Recruiter reports for all applicants export
				private String[] prepareRecruiterReportsApplicantsListStringArray(RequirementSubmissionHistory submissionHistory, String[] headers, OrgLocations loginOrgLocations,String toStage) throws Exception {
					String userArray[] = new String[headers.length];
						
					 userArray[0] = submissionHistory.getCandidateName();
					
					 userArray[1] = submissionHistory.getEmail();
					 
					 if(submissionHistory.getMobilePhone() !=null){
					 userArray[2] = submissionHistory.getMobilePhone();
					 }else{
					 userArray[2] = "---";	 
					 }
					 
					 userArray[3] = submissionHistory.getJobTitle();
					 
					 if(submissionHistory.getClientName() !=null){
						 userArray[4] = submissionHistory.getClientName();
						 }else{
						 userArray[4] = "---";	 
						 }
					 
					 userArray[5] = submissionHistory.getOrgLocationName();
					 
					 if(submissionHistory.getFinalizedRate() !=null){
						 userArray[6] = submissionHistory.getFinalizedRate();
					 }else{
						 userArray[6] = "---"; 
					 }
					 
					 if(toStage.equals("SUBMISSION")){
					 userArray[7] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getSubmissionId().getCreatedDate());
					 }else if(toStage.equals("SHORTLIST")){
					 userArray[7] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getCreatedDate());
					 }else{
					 userArray[7] = "1";
					 }
					 userArray[8] = submissionHistory.getSubmissionId().getSubmissionStatus().toString();
					   
					return userArray;
					
				}
				
				// Recruiter reports for all applicants export
						private String[] prepareRecruiterReportsApplicantsListStringArrayOne(RequirementSubmissionHistory submissionHistory, String[] headers, OrgLocations loginOrgLocations,String toStage) throws Exception {
							String userArray[] = new String[headers.length];
								
							 userArray[0] = submissionHistory.getCandidateName();
							
							 userArray[1] = submissionHistory.getEmail();
							 
							 if(submissionHistory.getMobilePhone() !=null){
							 userArray[2] = submissionHistory.getMobilePhone();
							 }else{
							 userArray[2] = "---";	 
							 }
							 
							 userArray[3] = submissionHistory.getJobTitle();
							 
							 if(submissionHistory.getClientName() !=null){
							 userArray[4] = submissionHistory.getClientName();
							 }else{
							 userArray[4] = "---";	 
							 }
							 
							 userArray[5] = submissionHistory.getOrgLocationName();
							 
							 if(submissionHistory.getFinalizedRate() !=null){
								 userArray[6] = submissionHistory.getFinalizedRate();
							 }else{
								 userArray[6] = "---"; 
							 }
							 
							 if(toStage.equals("SUBMIT_TO_CLIENT")){
							 if(submissionHistory.getSubmissionId().getFinalizedRate() !=null){
								 userArray[7] = submissionHistory.getSubmissionId().getFinalizedRate();		 
							 }else{
								 userArray[7] = "---";		 	 
							 }
							 userArray[8] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getSubmissionId().getCreatedDate());
							 }else if(toStage.equals("OFFERMADE")){
							 userArray[7] = submissionHistory.getSubmissionId().getOfferRate();
							 userArray[8] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getCreatedDate());
							 }else{
							 userArray[7] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getCreatedDate());
							 userArray[8] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getSubmissionId().getJoinedDate());
							 }
							 userArray[9] = submissionHistory.getSubmissionId().getSubmissionStatus().toString(); 
							return userArray;
							

						}
						
						
						// Recruiter reports for all applicants export
						private String[] prepareRecruiterReportsApplicantsListStringArrayTwo(RequirementSubmissionHistory submissionHistory, String[] headers, OrgLocations loginOrgLocations,String toStage) throws Exception {
							String userArray[] = new String[headers.length];
								
							 userArray[0] = submissionHistory.getCandidateName();
							
							 userArray[1] = submissionHistory.getEmail();
							 
							 if(submissionHistory.getMobilePhone() !=null){
							 userArray[2] = submissionHistory.getMobilePhone();
							 }else{
							 userArray[2] = "---";	 
							 }
							 
							 userArray[3] = submissionHistory.getJobTitle();
							 
							 if(submissionHistory.getClientName() !=null){
							 userArray[4] = submissionHistory.getClientName();
							 }else{
							 userArray[4] = "---";	 
							 }
							 
							 userArray[5] = submissionHistory.getOrgLocationName();
							 
							 if(submissionHistory.getFinalizedRate() !=null){
								 userArray[6] = submissionHistory.getFinalizedRate();
							 }else{
								 userArray[6] = "---"; 
							 }
							 
							 if(toStage.equals("REJECTED")){
							 userArray[7] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getCreatedDate());
							 userArray[8] = submissionHistory.getSubmissionId().getRejectedCategoryType();
							 userArray[9] = submissionHistory.getSubmissionId().getRejectedReason();
							 }else{
							 userArray[7] = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(), submissionHistory.getCreatedDate());
							 userArray[8] = submissionHistory.getSubmissionId().getBackOutCategoryType();
							 userArray[9] = submissionHistory.getSubmissionId().getBackOutReason(); 
							 }
							 userArray[10] = submissionHistory.getSubmissionId().getSubmissionStatus().toString(); 
							
							 return userArray;
						}
						public File availableAssociatereportReportListOfPdf(java.util.List<AvailableAssociatesDTO> data,User user, OrgLocations orgLocations) {
							try {
								long totalBillable = 0l;
								long totalNonbillable = 0l;
								String[] headers = Constants.AVAILABLE_ASSOCIATES_REPORT_EXPORT_HEADDERS;
								Document document = new Document(PageSize.LETTER.rotate());

								StringBuilder sbUser = new StringBuilder();
								sbUser.append("Available Associate Report")
										.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()));

								StringBuilder fileSB = new StringBuilder();
								File file = File.createTempFile(fileSB.append("Available Associate Report")
										.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString(),
										".pdf");

								file.setReadable(true);

								try {
									// Get an instance of PdfWriter and create a Table.pdf file as an output.
									PdfWriter.getInstance(document, new FileOutputStream(file));
									document.open();

									Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
									BaseColor myColor = WebColors.getRGBColor("#000000");
									boldFont.setColor(myColor);
									StringBuilder userStyle = new StringBuilder();
									userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("- Available Associate Report");

									// Create an instance of PdfPTable. After that we transform the header and
									PdfPTable table = new PdfPTable(headers.length);
									Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
									PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(), font));
									cell1.setColspan(18);
									headerCellStyle(cell1);
									table.setWidthPercentage(100);
									table.setWidths(new int[] { 5, 5, 5, 5, 5,5});
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
									String isGlobalAccess = null;
									java.util.List<String[]> reportList = prepareAvailableassociateReportListStringArray(data, headers,orgLocations, user, isGlobalAccess );
									if (reportList != null && reportList.size() > 0) {
										for (String[] reportArray : reportList) {
											for (int i = 0; i < reportArray.length; i++) {
												String datum = reportArray[i];
												PdfPCell cell = new PdfPCell();
												cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
												table.addCell(cell);
											}
											table.completeRow();
										}
									}
									document.addTitle("Available Associate Report Table");
									table.setSpacingBefore(15f);
									document.add(table);
									logger.info("Pdf table created !!");
								} catch (DocumentException e) {
									logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
								} catch (FileNotFoundException e) {
									logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
								} finally {
									document.close();
								}
								return file;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							return null;
						}

						private java.util.List<String[]> prepareAvailableassociateReportListStringArray(
								java.util.List<AvailableAssociatesDTO> data, String[] headers, OrgLocations orgLocations,
								User user, String isGlobalAccess) {
					
							java.util.List<String[]> arrayList = new ArrayList<>();

							for (AvailableAssociatesDTO report : data) {
								String reportArray[] = new String[headers.length];
								
								if (report.getEmpName() != null) {
									reportArray[0] = report.getEmpName();
								} else {
									reportArray[0] = "--";
								}
								
								if (report.getProjectName() != null) {
									reportArray[1] = report.getProjectName();
								} else {
									reportArray[1] = "--";
								}

								if (report.getProjectStartDate() != null) {
									reportArray[2] = (report.getProjectStartDate()+ "  "+ "to" + " " + (report.getProjectEndDate() != null ? report.getProjectEndDate() : "--"));
								} else {
									reportArray[2] = "--";
								}

								if (report.getEmpStartDate() != null) {
									reportArray[3] = (report.getEmpStartDate() + "  "+ "to" + " " + (report.getEmpEndDate() != null ? report.getEmpEndDate() : "--"));
								} else {
									reportArray[3] = "--";
								}
								
								if (report.getAvailableHours() != null) {
									reportArray[4] = report.getAvailableHours();
								} else {
									reportArray[4] = "--";
								}

								if (report.getHoursperWeek() != null) {
									reportArray[5] = report.getHoursperWeek().substring(0, 5);
								} else {
									reportArray[5] = "--";
								}

								
								arrayList.add(reportArray);
							}
							return arrayList;
						}
						public File expensependingApprovalReportListOfPdf(java.util.List<PendingForExpenseApprovalReportDTO> data, User user,OrgLocations orgLocations) {
							
							try {
								String[] headers = Constants.EXPENSE_PENDING_APPROVAL_REPORT_EXPORT_HEADDERS;
								Document document = new Document(PageSize.LETTER.rotate());

								StringBuilder sbUser = new StringBuilder();
								sbUser.append("Expense pending Approval Report")
										.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()));

								StringBuilder fileSB = new StringBuilder();
								File file = File.createTempFile(fileSB.append("Expense pending Approval Report")
										.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString(),
										".pdf");

								file.setReadable(true);

								try {
									// Get an instance of PdfWriter and create a Table.pdf file as an output.
									PdfWriter.getInstance(document, new FileOutputStream(file));
									document.open();

									Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
									BaseColor myColor = WebColors.getRGBColor("#000000");
									boldFont.setColor(myColor);
									StringBuilder userStyle = new StringBuilder();
									userStyle.append(orgLocations.getOrgLocationName()).append(" ").append("- Expense pending Approval Report");

									// Create an instance of PdfPTable. After that we transform the header and
									PdfPTable table = new PdfPTable(headers.length);
									Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
									PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(), font));
									cell1.setColspan(18);
									headerCellStyle(cell1);
									table.setWidthPercentage(100);
									table.setWidths(new int[] { 5, 7, 5,7, 5,7,5,5,7,5});
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
									String isGlobalAccess = null;
									java.util.List<String[]> reportList = prepareReportListStringArray(data, headers,orgLocations, user, isGlobalAccess );
									if (reportList != null && reportList.size() > 0) {
										for (String[] reportArray : reportList) {
											for (int i = 0; i < reportArray.length; i++) {
												String datum = reportArray[i];
												PdfPCell cell = new PdfPCell();
												cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
												table.addCell(cell);
											}
											table.completeRow();
										}
									}
									document.addTitle("Expense pending Approval Report");
									table.setSpacingBefore(15f);
									document.add(table);
									logger.info("Pdf table created !!");
								} catch (DocumentException e) {
									logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
								} catch (FileNotFoundException e) {
									logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
								} finally {
									document.close();
								}
								return file;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							return null;
						}

						private java.util.List<String[]> prepareReportListStringArray(
								java.util.List<PendingForExpenseApprovalReportDTO> data, String[] headers, OrgLocations orgLocations,
								User user, String isGlobalAccess) {
					
							java.util.List<String[]> arrayList = new ArrayList<>();

							for (PendingForExpenseApprovalReportDTO report : data) {
								String reportArray[] = new String[headers.length];
								
								if (report.getEmployeId() != null) {
									reportArray[0] = report.getEmployeId();
								} else {
									reportArray[0] = "--";
								}
								
								if (report.getEmployeName() != null) {
									reportArray[1] = report.getEmployeName();
								} else {
									reportArray[1] = "--";
								}

								if (report.getEmployeType() != null) {
									reportArray[2] = (report.getEmployeType());
								} else {
									reportArray[2] = "--";
								}

								if (report.getProjectName() != null) {
									reportArray[3] = (report.getProjectName());
								} else {
									reportArray[3] = "--";
								}
								
								/*if (report.getFrequency() != null) {
									reportArray[4] = report.getFrequency();
								} else {
									reportArray[4] = "--";
								}*/

								if (report.getApprovalMechanism() != null) {
									reportArray[4] = report.getApprovalMechanism();
								} else {
									reportArray[4] = "--";
								}
								if (report.getManagerOne() != null) {
									reportArray[5] = report.getManagerOne();
								} else {
									reportArray[5] = "--";
								}
								if (report.getManagerTwo() != null) {
									reportArray[6] = report.getManagerTwo();
								} else {
									reportArray[6] = "--";
								}
								if (report.getSubmittedAmount() != null) {
									reportArray[7] = report.getSubmittedAmount();
								} else {
									reportArray[7] = "--";
								}
								if (report.getSubmittedOn() != null) {
									reportArray[8] = report.getSubmittedOn();
								} else {
									reportArray[8] = "--";
								}
								if (report.getApprovedBy() != null) {
									reportArray[9] = report.getApprovedBy();
								} else {
									reportArray[9] = "--";
								}
								arrayList.add(reportArray);
							}
							return arrayList;
						}
						
						
						
						
						public File expensependingApprovalReportListOfExcel(java.util.List<PendingForExpenseApprovalReportDTO> exp, User user,
								OrgLocations orgLoc) throws Exception {

							File file = null;
								Workbook createExcelworkbook = new XSSFWorkbook();
								DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
								Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrgLocationName()+ "_" + dateFormat.format(new Date()));
								Row writeRow = null;
								TimeSheetSubmissionCount requirementSubCount=null;
								createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
								int column = 0;
								for (PendingForExpenseApprovalReportDTO expenses : exp) {
									writeRow = createExcelsheet.createRow(column+1);
									createExcelsheet = createXlsSheet(createExcelsheet, writeRow, expenses,orgLoc,user);
									column++;
								}
								 try {
									file = File.createTempFile(orgLoc.getOrgLocationName() + "_PendingExpensesReport_" + dateFormat.format(new Date()),".xls");
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

							String[] headers = Constants.EXPENSE_PENDING_APPROVAL_REPORT_EXPORT_HEADDERS;;
							createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);

							return createExcelsheet;
						}

						private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, PendingForExpenseApprovalReportDTO expenses,OrgLocations orgLoc,User loginUser) throws Exception {
												
							
							if(expenses.getEmployeId() != null) {
								writeRow.createCell(0).setCellValue(expenses.getEmployeId());
								
							}else {
								writeRow.createCell(0).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(0, 15*255);

							if(expenses.getEmployeName() != null) {
								writeRow.createCell(1).setCellValue(expenses.getEmployeName());
								
							}else {
								writeRow.createCell(1).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(1, 32*255);

							if(expenses.getEmployeType() != null) {
								writeRow.createCell(2).setCellValue(expenses.getEmployeType());
								
							}else {
								writeRow.createCell(2).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(2, 15*255);

							if(expenses.getProjectName() != null) {
								writeRow.createCell(3).setCellValue(expenses.getProjectName());
								
							}else {
								writeRow.createCell(3).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(3, 32*255);
							
							if(expenses.getApprovalMechanism() != null) {
								writeRow.createCell(4).setCellValue(expenses.getApprovalMechanism());
								
							}else {
								writeRow.createCell(4).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(4, 22*255);

							if(expenses.getManagerOne() != null) {
								writeRow.createCell(5).setCellValue(expenses.getManagerOne());
								
							}else {
								writeRow.createCell(5).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(5, 32*255);

							if(expenses.getManagerTwo() != null) {
								writeRow.createCell(6).setCellValue(expenses.getManagerTwo());
								
							}else {
								writeRow.createCell(6).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(6, 25*255);

							if(expenses.getSubmittedAmount()!= null) {
								writeRow.createCell(7).setCellValue(expenses.getSubmittedAmount());
								
							}else {
								writeRow.createCell(7).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(7, 10*255);

							if(expenses.getSubmittedOn() != null) {
								writeRow.createCell(8).setCellValue(expenses.getSubmittedOn());
								
							}else {
								writeRow.createCell(8).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(8, 22*255);

							if(expenses.getApprovedBy() != null) {
								writeRow.createCell(9).setCellValue(expenses.getApprovedBy());
								
							}else {
								writeRow.createCell(9).setCellValue("--");
							}
							createExcelsheet.setColumnWidth(9, 22*255);
							
							return createExcelsheet;
							
						}
}
