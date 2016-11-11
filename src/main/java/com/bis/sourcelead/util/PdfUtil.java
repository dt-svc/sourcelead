package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.UserRole;
import com.itextpdf.text.BaseColor;
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
	   				dateOfJoin = emp.getHireDateStr();
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
	   		   if(userRole.getUser().getUserType() != null){
	   			table.addCell(userRole.getUserType());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getReportingManager() !=null){
	   			table.addCell(userRole.getReportingManager().getFirstName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getStatus() !=null){
	   			table.addCell(userRole.getUserState().toString());
	   		   }else{
	   			table.addCell("--");
	   		   }
	        }
	        document.addTitle("Users Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
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
	        	candidateList.add(prepareStringArray(candidate,candidatesList.size()));
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
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } finally {
	        document.close();
	    }
		return file;
	
	}
	private String[] prepareStringArray(Candidate candidate, int size) {
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

}
