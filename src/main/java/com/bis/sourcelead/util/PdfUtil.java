package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.UserRole;
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
	private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
	public File createPdfTable(java.util.List<UserRole> activeList,
			java.util.List<UserRole> inactiveList, java.util.List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,
			String userState, String orgName){
		String[] headers = Constants.USER_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File("users_"+dateFormat.format(new Date())+".pdf");
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
            Paragraph para = new Paragraph("Organization : " +orgName);
            document.add(para);
	        
	        int inActiveCount = 0; 
            int activeCount = 0; 
            int notLoggedInCount = 0; 
	        List orderedList = new List(List.ORDERED);
	        
	        if("ACTIVE".equalsIgnoreCase(userState)){
	        	activeCount = userRolesList.size();
			 }else{
				 if(activeList !=null){
					 activeCount = activeList.size();
				 }
			 }
	        if("INACTIVE".equalsIgnoreCase(userState)){
	        	inActiveCount = userRolesList.size();
			 }else{
				 if(inactiveList !=null){
					 inActiveCount = inactiveList.size();
				 }
			 }
	        if("NOT_LOGGEDIN".equalsIgnoreCase(userState)){
	        	notLoggedInCount = userRolesList.size(); 
			 }else{
				 if(notLoggedInList != null){
					 notLoggedInCount = notLoggedInList.size();
				 }
			 }
	        orderedList.add(new ListItem("Active :"+activeCount));
	        orderedList.add(new ListItem("Inactive :"+inActiveCount));
	        orderedList.add(new ListItem("Not Loggedin :"+notLoggedInCount));
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 3,5,3,5,5,3});
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#33caff");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        for(UserRole userRole : userRolesList){
	        	Set<Employees> employeeSet = userRole.getUser().getEmployee();
	        	String dateOfJoin = "";
	        	String rate = "";
	   		 for(Employees emp : employeeSet){
	   			 if(emp.getEmployeeId() !=null){
	   				table.addCell(emp.getEmployeeId());
	   			 }else{
	   				table.addCell("--");
	   			 }
	   			 if(emp.getHireDate() !=null){
	   				  dateOfJoin = Constants.DF_YMD.format(emp.getHireDate());
	   			 }else{
	   				dateOfJoin = "--";
	   			 }
	   			 if(emp.getRate() !=null && !"".equalsIgnoreCase(emp.getRate())){
	   				rate = emp.getRate()+"/"+emp.getRatePer();
	   			 }else{
	   				rate = "--";
	   			 }
	     	    } 
	   		 
	   		   if(userRole.getUser().getFirstName() !=null){
	   			table.addCell(userRole.getUser().getFirstName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getLastName() != null){
	   			table.addCell(userRole.getUser().getLastName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getEmail() !=null){
	   			table.addCell(userRole.getUser().getEmail());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getMobilePhone() != null){
	   			table.addCell(userRole.getUser().getMobilePhone());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   table.addCell(dateOfJoin);
	   		   table.addCell(rate);
	   		   if(userRole.getUser().getLocation() !=null){
	   			table.addCell(userRole.getUser().getLocation());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUserType() != null){
	   			table.addCell(userRole.getUserType());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getReportingManager() !=null){
	   			table.addCell(userRole.getReportingManager().getFirstName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   
	   		   if(userRole.getUser().getIsMailSent() != null && !"".equalsIgnoreCase(userRole.getUser().getIsMailSent().toString()) && "N".equalsIgnoreCase(userRole.getUser().getIsMailSent().toString()) && !"INACTIVE".equalsIgnoreCase(userRole.getUserState().toString())){
	   			table.addCell("Invite");
	   		   }else if ( (userRole.getUser().getIsMailSent() != null && !"".equalsIgnoreCase(userRole.getUser().getIsMailSent().toString()) && "Y".equalsIgnoreCase(userRole.getUser().getIsMailSent().toString())) &&  (userRole.getUserState() !=null && "NOT_LOGGEDIN".equalsIgnoreCase(userRole.getUserState().toString()))){
	   			table.addCell("Resend");
	   		   }else if(userRole.getUserState() !=null && "ACTIVE".equalsIgnoreCase(userRole.getUserState().toString())){
	   			table.addCell("Active");
	   		   }else if(userRole.getUserState() !=null && "INACTIVE".equalsIgnoreCase(userRole.getUserState().toString())){
	   			table.addCell("In-active");
	   		   }
	        }
	        document.addTitle("Users Table");
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
	public  File createCandidatePdfTable(java.util.List<Candidate> candidatesList, String orgName) {

		String[] headers = Constants.CANDIDATE_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File(orgName+"_Candidate_"+dateFormat.format(new Date())+".pdf");
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor1 = WebColors.getRGBColor("#33caff");
	        boldFont.setColor(myColor1);
            Paragraph para = new Paragraph(orgName+ " Candidates.",boldFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 3,5,3,5,5});
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
	        	candidateList.add(prepareStringArray(candidate));
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
	private String[] prepareStringArray(Candidate candidate) {
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
		if(candidate.getCity() !=null && !"".equalsIgnoreCase(candidate.getCity())){
			candidateArray[4] = candidate.getCity();
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
				educations.append(candidateEdu.getName()+",");
			}
			String educationList = educations.toString();
			candidateArray[6] = educationList.substring(0, educations.length()-1);
		}else{
			candidateArray[6] = "--";
		}
		if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
			candidateArray[7] = candidate.getExperienceYears()+"."+candidate.getExperienceMonths()+"years";
		}else{
			if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears())){
				candidateArray[7] = candidate.getExperienceYears()+"years";
			}else{
				if(candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
					candidateArray[7] = candidate.getExperienceMonths()+"Months";
				}else{
					candidateArray[7] = "--";
				}
			}
		}
		if(candidate.getCandidateSkills() !=null && candidate.getCandidateSkills().size() >0){
			StringBuilder skills = new StringBuilder();
			Set<CandidateSkills> candidateSkillsList = candidate.getCandidateSkills();
		    for(CandidateSkills candidateSkills : candidateSkillsList){
		    	skills.append(candidateSkills.getName()+",");
		    }
		    candidateArray[8] = skills.toString().substring(0, skills.length()-1);
		}else{
			candidateArray[8] = "--";
		}
		if(candidate.getExpectedRate() !=null && !"".equalsIgnoreCase(candidate.getExpectedRate()) && candidate.getRateType() !=null && !"".equalsIgnoreCase(candidate.getRateType())){
			candidateArray[9] = candidate.getExpectedRate()+"/"+candidate.getRateType();
		}else{
			candidateArray[9] = "--";
		}
		return candidateArray;
		
	}
	public File getRequirementPdf(java.util.List<Requirement> requirementList, String orgName, TimeSheetSubmissionCount requirementSubCount) {


		String[] headers = Constants.REQUIREMENT_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor1 = WebColors.getRGBColor("#33caff");
	        boldFont.setColor(myColor1);
            Paragraph para = new Paragraph(orgName+" Requirements.",boldFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
            List orderedList = new List(List.ORDERED);
	        orderedList.add(new ListItem("Draft :"+requirementSubCount.getDraftRequirementCount()));
	        orderedList.add(new ListItem("Open :"+requirementSubCount.getOpenRequirementCount()));
	        orderedList.add(new ListItem("New :"+requirementSubCount.getNewRequirementCount()));
	        orderedList.add(new ListItem("Submitted :"+requirementSubCount.getSubmittedRequirementCount()));
	        orderedList.add(new ListItem("On Hold :"+requirementSubCount.getOnHoldRequirementCount()));
	        orderedList.add(new ListItem("Closed :"+requirementSubCount.getCancelRequirementCount()));
	        document.add(orderedList);
            PdfPTable table = new PdfPTable(headers.length);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 3,5,3,5,5,3,3});
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
	private String[] prepareRequirementToArray(Requirement requirement) {
		String requirementArray[] = new String[Constants.REQUIREMENT_EXCEL_HEADERS.length];
		if(requirement.getId() != null){
			requirementArray[0] = requirement.getId().toString();
		}
		if(requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			requirementArray[1] = requirement.getJobTitle();
		}else{
			requirementArray[1] = "--";
		}
		if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			requirementArray[2] = requirement.getMinExp()+"-"+requirement.getMaxExp()+" years"; 
		}else if((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) || (requirement.getMaxExp() ==null)){
			requirementArray[2] = requirement.getMinExp()+" years";
		}else if((requirement.getMinExp() == null ) || (requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			requirementArray[2] = requirement.getMaxExp()+" years";
		}else{
			requirementArray[2] = "--";
		}
		
		if(requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()) && requirement.getRateType() != null && !"".equalsIgnoreCase(requirement.getRateType())){
			requirementArray[3] =  requirement.getRate()+" / "+requirement.getRateType();
		}else{
			requirementArray[3] = "--";
		}
		if(requirement.getCity() != null && !"".equalsIgnoreCase(requirement.getCity())){
			requirementArray[4] = requirement.getCity();
		}else{
			requirementArray[4] = "--";
		}
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
		if(requirement.getSubmissionTemplateId() != null ){
			requirementArray[8] = requirement.getSubmissionTemplateId().getTemplateName();
		}else{
			requirementArray[8] = "--";
		}
		if(requirement.getClientName() != null && !"".equalsIgnoreCase(requirement.getClientName())){
			requirementArray[9] = requirement.getClientName();
		}else{
			requirementArray[9] = "--";
		}
		if(requirement.getClientContactName() != null && !"".equalsIgnoreCase(requirement.getClientContactName())){
			requirementArray[10] = requirement.getClientContactName();
		}else{
			requirementArray[10] = "--";
		}
		if(requirement.getClientContactEmail() != null && !"".equalsIgnoreCase(requirement.getClientContactEmail())){
			requirementArray[11] = requirement.getClientContactEmail();
		}else{
			requirementArray[11] = "--";
		}
		return requirementArray;
	}
	public File getClientVendorPdfFile(java.util.List<OrgRelations> clientsList, String orgName, String[] clientHeaders, String vendorType) throws DocumentException {
		Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File(orgName+"_"+vendorType+"_"+dateFormat.format(new Date())+".pdf");
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor1 = WebColors.getRGBColor("#33caff");
	        boldFont.setColor(myColor1);
            Paragraph para = new Paragraph(orgName+" "+vendorType,boldFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header 
            int[] columnWidths = new int[]{3, 5, 3,5, 2};
            PdfPTable table = getPdfTable(clientHeaders,columnWidths); 
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
	        
	        document.addTitle("Client List Table");
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
		if (orgRelations.getOrgName() != null && !"".equalsIgnoreCase(orgRelations.getOrgName())) {
			clientArray[0] = orgRelations.getOrgName();
		}else{
			clientArray[0] = "--";
		}
		if (orgRelations.getWebSite() != null && !"".equalsIgnoreCase(orgRelations.getWebSite())) {
			clientArray[1] = orgRelations.getWebSite();
		}else{
			clientArray[1] = "--";
		}
		if (orgRelations.getCity() != null && !"".equalsIgnoreCase(orgRelations.getCity())) {
			clientArray[2] = orgRelations.getWebSite();
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
	private PdfPTable getPdfTable(String[] headers, int[] columnWidths) {
		PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        try {
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
	public File getClientVendorContactsPdfFile(java.util.List<OrgSpoc> vandorsContactList, String orgName,String vendorType, String[] headers) throws DocumentException {
		Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File(orgName+"_"+vendorType+" Contacts_"+dateFormat.format(new Date())+".pdf");
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor1 = WebColors.getRGBColor("#33caff");
	        boldFont.setColor(myColor1);
            Paragraph para = new Paragraph(orgName+" "+vendorType+" Contacts.",boldFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header 
            int[] columnWidths = new int[]{3, 5, 3,5, 2,3,3};
            PdfPTable table = getPdfTable(headers,columnWidths); 
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
	private String[] prepareContactsArray(OrgSpoc orgSpoc, String[] headers) {
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
		contactsArray[5] = "--";
		if (orgSpoc.getSpocStatus() != null && !"".equalsIgnoreCase(orgSpoc.getSpocStatus())) {
			contactsArray[6] = orgSpoc.getSpocStatus();
		}else{
			contactsArray[6] = "--";
		}
		return contactsArray;
	}
	public File getProjectEmployeesExportPdf(java.util.List<ProjectMembers> employeeList, String projectName,
			Projects project, String projectManager) throws DocumentException {
		String[] headers = Constants.PROJECT_ASSIGNED_EMPLOYEES;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File(projectName+"_"+dateFormat.format(new Date())+".pdf");
	    try {
	    	// Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font blue = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
	        Chunk blueText = new Chunk("Project Name : " +projectName, blue);
            Paragraph para = new Paragraph();
            para.add(blueText);
            document.add(para);
	        
	        List orderedList = new List(List.ORDERED);
	        
	        orderedList.add(new ListItem("Project Name :"+projectName));
	        orderedList.add(new ListItem("Manager :"+projectManager));
	        if(project.getStartDate()!=null && project.getEndDate() !=null){
	        	orderedList.add(new ListItem("Date :"+project.getStartDate()+" to "+project.getEndDate()));
			 }else{
				 orderedList.add(new ListItem("Date :"+project.getStartDate()));
			 }
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        int[] columnWidths = new int[]{3, 5, 3,5, 2,3,3,5};
            PdfPTable table = getPdfTable(headers,columnWidths); 
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
	
	    

	private String[] prepareEmployeeArray(ProjectMembers projectMember, String[] headers) {
		
		   String empArray[] = new String[headers.length];
			if (projectMember.getEmpName() != null && !"".equalsIgnoreCase(projectMember.getEmpName())) {
				empArray[0] = projectMember.getEmpName();
			}else{
				empArray[0] = "--";
			}
			if (projectMember.getEmployee().getUser().getEmail() != null && !"".equalsIgnoreCase(projectMember.getEmployee().getUser().getEmail())) {
				empArray[1] = projectMember.getEmployee().getUser().getEmail();
			}else{
				empArray[1] = "--";
			}
			if (projectMember.getProjectAssignedDateStr() != null && !"".equalsIgnoreCase(projectMember.getProjectAssignedDateStr())) {
				empArray[2] = projectMember.getProjectAssignedDateStr();
			}else{
				empArray[2] = "--";
			}
			if (projectMember.getProjectEndDateStr() != null && !"".equalsIgnoreCase(projectMember.getProjectEndDateStr())) {
				empArray[3] = projectMember.getProjectEndDateStr();
			}else{
				empArray[3] = "--";
			}
			if (projectMember.getApprovedHrs() != null && !"".equalsIgnoreCase(projectMember.getApprovedHrs())) {
				empArray[4] = projectMember.getApprovedHrs();
			}else{
				empArray[4] = "--";
			}
			if (projectMember.getRejectedHrs() != null && !"".equalsIgnoreCase(projectMember.getRejectedHrs())) {
				empArray[5] = projectMember.getRejectedHrs();
			}else{
				empArray[5] = "--";
			}
			if (projectMember.getSubmittedHrs() != null && !"".equalsIgnoreCase(projectMember.getSubmittedHrs())) {
				empArray[6] = projectMember.getSubmittedHrs();
			}else{
				empArray[6] = "--";
			}
			if (projectMember.getDraftHrs() != null && !"".equalsIgnoreCase(projectMember.getDraftHrs())) {
				empArray[7] = projectMember.getDraftHrs();
			}else{
				empArray[7] = "--";
			}
		return empArray;
	}
	
	}
