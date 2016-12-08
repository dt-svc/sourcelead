package com.bis.sourcelead.service.impl;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.RequirementService;
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

@Component
public class PdfUtilPipelineHelper  {

	@Autowired
	RequirementService requirementService;
	
	private static final Logger logger = LoggerFactory.getLogger(PdfUtilPipelineHelper.class);
	
	public File getRequirementPipelenePdf(java.util.List<RequirementSubmission> requirementStatusList, String orgName,
			String requirementId,String submissionType) throws NumberFormatException, Exception {
		
		if(submissionType.equals("SHORTLIST")){
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
			String[] headers = Constants.REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS;
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
		        orderedList.add(new ListItem("Requirement Name :"+requirement.getJobTitle()));
		        orderedList.add(new ListItem("Location :"+requirement.getCountry()));
		        orderedList.add(new ListItem("Experience :"+requirement.getMaxExp()+"-"+requirement.getMinExp()));
		        document.add(orderedList);
	            PdfPTable table = new PdfPTable(headers.length);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{3,5, 3,5, 5, 3,5,3,5,5,5,3});
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
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArrayShortedListed(requirementsub));
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
		}else if(submissionType.equals("INTERVIEW")){
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
			String[] headers = Constants.REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS;
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
		        orderedList.add(new ListItem("Requirement Name :"+requirement.getJobTitle()));
		        orderedList.add(new ListItem("Location :"+requirement.getCountry()));
		        orderedList.add(new ListItem("Experience :"+requirement.getMaxExp()+"-"+requirement.getMinExp()));
		        document.add(orderedList);
	            PdfPTable table = new PdfPTable(headers.length);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{3,5, 3,5});
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
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArrayInterview(requirementsub));
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
		}else if(submissionType.equals("OFFERMADE")){
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
			String[] headers = Constants.REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS;
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
		        orderedList.add(new ListItem("Requirement Name :"+requirement.getJobTitle()));
		        orderedList.add(new ListItem("Location :"+requirement.getCountry()));
		        orderedList.add(new ListItem("Experience :"+requirement.getMaxExp()+"-"+requirement.getMinExp()));
		        document.add(orderedList);
	            PdfPTable table = new PdfPTable(headers.length);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{3,5, 3,5, 5, 3,5,3,5});
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
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArrayOffer(requirementsub));
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
		}else if(submissionType.equals("REJECTED")){
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
			String[] headers = Constants.REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS;
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
		        orderedList.add(new ListItem("Requirement Name :"+requirement.getJobTitle()));
		        orderedList.add(new ListItem("Location :"+requirement.getCountry()));
		        orderedList.add(new ListItem("Experience :"+requirement.getMaxExp()+"-"+requirement.getMinExp()));
		        document.add(orderedList);
	            PdfPTable table = new PdfPTable(headers.length);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{3,5, 3,5, 5, 3,5,3,5,5,5});
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
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArrayRejected(requirementsub));
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
		}else{
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
			String[] headers = Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS;
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
		        orderedList.add(new ListItem("Requirement Name :"+requirement.getJobTitle()));
		        orderedList.add(new ListItem("Location :"+requirement.getCountry()));
		        orderedList.add(new ListItem("Experience :"+requirement.getMaxExp()+"-"+requirement.getMinExp()));
		        document.add(orderedList);
	            PdfPTable table = new PdfPTable(headers.length);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{3,5, 3,5, 5, 3,5,3,5,5,5,5});
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
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArray(requirementsub));
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
		}
		
	
	
	private String[] prepareRequirementPipelineToArrayRejected(RequirementSubmission requirementsub) {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS.length];
		
		
		
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			requirementArray[0] = requirementsub.getCandidateName();
		}else{
			requirementArray[0] = "--";
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			requirementArray[1] = requirementsub.getCandidateEmail();
		}else{
			requirementArray[1] = "--";
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			requirementArray[2] = requirementsub.getPhoneNumber();

		}else{
			requirementArray[2] = "--";
		}
		if(requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())){
			requirementArray[3] = requirementsub.getResumeName();

		}else{
			requirementArray[3] = "--";
		}
		if(requirementsub.getCreatedDate() != null && !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())){
			requirementArray[4] = requirementsub.getCreatedDate().toString();

		}else{
			requirementArray[4] = "--";
		}
		
		if(requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())){
			requirementArray[5] = requirementsub.getExpectedCTC();
		}else{
			requirementArray[5] = "--";
		}
		if(requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())){
			requirementArray[6] = requirementsub.getUserName();
		}else{
			requirementArray[6] = "--";
		}
		if(requirementsub.getUpDatedStr() != null && !"".equalsIgnoreCase(requirementsub.getUpDatedStr())){
			requirementArray[7] = requirementsub.getUpDatedStr();
		}else{
			requirementArray[7] = "--";
			
		}
		if(requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())){
			requirementArray[8] = requirementsub.getSubmittedBy();
		}else{
			requirementArray[8] = "--";
			
		}if(requirementsub.getPreviousSubmissionStatus() != null){
			requirementArray[9] = requirementsub.getPreviousSubmissionStatus().toString();
		}else{
			requirementArray[9] = "--";
		}
		if(requirementsub.getSubmissionStatus() != null){
			requirementArray[10] = requirementsub.getSubmissionStatus().toString();
		}else{
			requirementArray[10] = "--";
		}
		
		
		
		return requirementArray;
		
	}



	private String[] prepareRequirementPipelineToArrayOffer(RequirementSubmission requirementsub) {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS.length];
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			requirementArray[0] = requirementsub.getCandidateName();
		}else{
			requirementArray[0] = "--";
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			requirementArray[1] = requirementsub.getCandidateEmail();
		}else{
			requirementArray[1] = "--";
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			requirementArray[2] = requirementsub.getPhoneNumber();

		}else{
			requirementArray[2] = "--";
		}
		
		if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			requirementArray[3] = requirementsub.getWorkExperience();
		}else{
			requirementArray[3] = "--";
		}
		if(requirementsub.getOfferJoinDate() != null && !"".equalsIgnoreCase(requirementsub.getOfferJoinDate())){
			requirementArray[4] = requirementsub.getOfferJoinDate();
		}else{
			requirementArray[4] = "--";
		}
		if(requirementsub.getPositionType() != null && !"".equalsIgnoreCase(requirementsub.getPositionType())){
			requirementArray[5] = requirementsub.getPositionType();
		}else{
			requirementArray[5] = "--";
		}
		if(requirementsub.getOfferRate() != null && !"".equalsIgnoreCase(requirementsub.getOfferRate())){
			requirementArray[6] = requirementsub.getOfferRate();
		}else{
			requirementArray[6] = "--";
		}
		if(requirementsub.getSubmittedOn() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedOn())){
			requirementArray[7] = requirementsub.getSubmittedOn();
		}else{
			requirementArray[7] = "--";
		}
		if(requirementsub.getAttachment() != null && !"".equalsIgnoreCase(requirementsub.getAttachment())){
			requirementArray[8] = requirementsub.getAttachment();
		}else{
			requirementArray[8] = "--";
		}
		return requirementArray;
	}



	private String[] prepareRequirementPipelineToArrayInterview(RequirementSubmission requirementsub) {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS.length];
		
		
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			requirementArray[0] = requirementsub.getCandidateName();
		}else{
			requirementArray[0] = "--";
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			requirementArray[1] = requirementsub.getCandidateEmail();
		}else{
			requirementArray[1] = "--";
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			requirementArray[2] = requirementsub.getPhoneNumber();

		}else{
			requirementArray[2] = "--";
		}
		
		if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			requirementArray[3] = requirementsub.getWorkExperience();
		}else{
			requirementArray[3] = "--";
		}
		return requirementArray;
	}



	/**
	 * @param requirementsub
	 * @return
	 * @Description: pdf for shortlisted.
	 */
	private String[] prepareRequirementPipelineToArrayShortedListed(RequirementSubmission requirementsub) {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS.length];
		
		
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			requirementArray[0] = requirementsub.getCandidateName();
		}else{
			requirementArray[0] = "--";
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			requirementArray[1] = requirementsub.getCandidateEmail();
		}else{
			requirementArray[1] = "--";
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			requirementArray[2] = requirementsub.getPhoneNumber();

		}else{
			requirementArray[2] = "--";
		}
		if(requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())){
			requirementArray[3] = requirementsub.getResumeName();

		}else{
			requirementArray[3] = "--";
		}
		if(requirementsub.getCreatedDate() != null && !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())){
			requirementArray[4] = requirementsub.getCreatedDate().toString();

		}else{
			requirementArray[4] = "--";
		}
		
		if(requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())){
			requirementArray[5] = requirementsub.getExpectedCTC();
		}else{
			requirementArray[5] = "--";
		}
		if(requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())){
			requirementArray[6] = requirementsub.getUserName();
		}else{
			requirementArray[6] = "--";
		}
		if(requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())){
			requirementArray[7] = requirementsub.getScore();
		}else{
			requirementArray[7] = "--";
		}
		if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			requirementArray[8] = requirementsub.getWorkExperience();
		}else{
			requirementArray[8] = "--";
		}
		if(requirementsub.getStatus() != null){
			requirementArray[9] = requirementsub.getStatus().toString();
		}else{
			requirementArray[9] = "--";
		}
		if(requirementsub.getSubmittedBy() != null){
			requirementArray[10] = requirementsub.getSubmittedBy();
		}else{
			requirementArray[10] = "--";
		}
		if(requirementsub.getSubmittedOn() != null){
			requirementArray[11] = requirementsub.getSubmittedOn();
		}else{
			requirementArray[11] = "--";
		}
		
		return requirementArray;
	}

	private String[] prepareRequirementPipelineToArray(RequirementSubmission requirementsub) {
			String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS.length];
			
			
		
			if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
				requirementArray[0] = requirementsub.getCandidateName();
			}else{
				requirementArray[0] = "--";
			}
			
			if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
				requirementArray[1] = requirementsub.getCandidateEmail();
			}else{
				requirementArray[1] = "--";
			}
			if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
				requirementArray[2] = requirementsub.getPhoneNumber();

			}else{
				requirementArray[2] = "--";
			}
			if(requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())){
				requirementArray[3] = requirementsub.getResumeName();

			}else{
				requirementArray[3] = "--";
			}
			if(requirementsub.getCreatedDate() != null && !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())){
				requirementArray[4] = requirementsub.getCreatedDate().toString();

			}else{
				requirementArray[4] = "--";
			}
			
			if(requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())){
				requirementArray[5] = requirementsub.getExpectedCTC();
			}else{
				requirementArray[5] = "--";
			}
			if(requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())){
				requirementArray[6] = requirementsub.getUserName();
			}else{
				requirementArray[6] = "--";
			}
			if(requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())){
				requirementArray[7] = requirementsub.getScore();
			}else{
				requirementArray[7] = "--";
			}
			if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
				requirementArray[8] = requirementsub.getWorkExperience();
			}else{
				requirementArray[8] = "--";
			}
			if(requirementsub.getStatus() != null){
				requirementArray[9] = requirementsub.getStatus().toString();
			}else{
				requirementArray[9] = "--";
			}
			if(requirementsub.getSubmittedOn() != null){
				requirementArray[10] = requirementsub.getSubmittedOn();
			}else{
				requirementArray[10] = "--";
			}
			if(requirementsub.getSubmittedBy() != null){
				requirementArray[11] = requirementsub.getSubmittedBy();
			}else{
				requirementArray[11] = "--";
			}
			
			
			return requirementArray;
		}


}
