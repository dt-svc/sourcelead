package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
//import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;
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
 * Helper class for prepare PDF Doc
 * @since 10-12-2016
 *
 */
@Component
public class PdfUtilPipelineHelper  {

	@Autowired
	RequirementService requirementService;
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	private RequirementLocationsService requirementLocationService;
	
	@Autowired
	RequirementWorkFlowService requirementWorkFlowService;
	
	@Autowired
	private CommonUtil commonUtil;
	private static final Logger logger = LoggerFactory.getLogger(PdfUtilPipelineHelper.class);
	
	public File getRequirementPipelenePdf(java.util.List<RequirementSubmission> requirementStatusList, OrgLocations loginOrgLocation,
			String requirementId,String submissionType,RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		//SimpleDateFormat simpleDateFormat=loginOrgLocation.getLocFullFormDateFormatter();
		StringBuilder fileStringBuilder=new StringBuilder();
		if(submissionType.equals("SUBMIT_TO_CLIENT")){
			String[] headers = Constants.REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS;
			//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			
			
			StringBuilder sbClientPipe = new StringBuilder();
			sbClientPipe.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date()));
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/

		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
		    Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getNumOfSubmitToClient(),Constants.SUBMIT_TO_CLIENT_COUNT,file);

		    try {
		    	/** 
		    	 * @author Mahaveer Manoj
		    	 * @commentedBy Mahaveer Manoj
		    	 * @purpose old logic for generating static fields
		    	 * 
		    	 **/
		    	/*int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5,3,5,5,5};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString());
		        table.completeRow();
		        java.util.List<String[]> requirements = new ArrayList<>();
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArraySubmitClient(requirementsub,loginOrgLocation));
		        }
		        
		        for (String[] reqArray: requirements) {
	                for (int i = 0; i < reqArray.length; i++) {
	                    String datum = reqArray[i];
	                    PdfPCell cell = new PdfPCell();
	                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	                    table.addCell(cell);
	                }
	                table.completeRow();
	            }*/
		    	
		    	/** 
		    	 * @author Mahaveer Manoj
		    	 * @purpose new logic for getting dynamic template fields Header & Values based upon Submission Template
		    	 * 
		    	 **/
		    	String[] totalHeaders = null;
				String[] templateHeaders = null;
				String[] extraHeaders = Constants.REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS_FOR_TEMPLATE_CONFIGURED;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					JSONArray jsonArray = new JSONArray(requirementsub.getSubmissionTemplateJsonStringValue());
					if (jsonArray != null && jsonArray.length() > 0) {
						templateHeaders = new String[jsonArray.length()];

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							templateHeaders[i] = jsonObject.getString("name");
						}
						
					}
				}
				totalHeaders = (String[]) ArrayUtils.addAll(templateHeaders, extraHeaders);
		    	
		    	
//		    	int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5,3,5,5,5};
		    	int[] width = new int[totalHeaders.length];
		    	for(int i=0; i < totalHeaders.length; i++) {
		    		width[i] = 1;
		    	}
		    	PdfPTable table = getPdfTable(totalHeaders,width,sbPipeHearder.toString(), totalHeaders.length);
		        table.completeRow();
		        java.util.List<String[]> requirements = new ArrayList<>();
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	String[] totalValues = new String[totalHeaders.length];
		        	String[] templateValues = new String[templateHeaders.length];
		        	String[] extraValues = new String[extraHeaders.length];
		        	
		        	JSONArray jsonArray = new JSONArray(requirementsub.getSubmissionTemplateJsonStringValue());
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							templateValues[i] = jsonObject.getString("value");
						}
					}
		        	
		        	extraValues = prepareRequirementPipelineToArraySubmitClient(requirementsub,loginOrgLocation, extraHeaders.length);
		        	
		        	totalValues = (String[]) ArrayUtils.addAll(templateValues, extraValues);
		        	requirements.add(totalValues);
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}
		if(submissionType.equals("REQUEST_SENT")){
			String[] headers = Constants.REQUIREMENT_PIPELINE_REQUEST_SENT_HEADERS;
			//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
		    Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getNumOfRequestSent(),Constants.REQUEST_SENT_COUNT,file);

		    try {
		    	int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString(), 0);
		        table.completeRow();
		        java.util.List<String[]> requirements = new ArrayList<>();
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementPipelineToArrayRequestSent(requirementsub));
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}else if(submissionType.equals("SHORTLIST")){
			String[] headers = Constants.REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS;
			//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
		    Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getNumOfShortList(),Constants.SHORTLIST_COUNT,file);

		    try {
		    	int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5,5,3};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString(), 0);
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}else if(submissionType.equals("INTERVIEW")){
			String[] headers = Constants.REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS;
		   // DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
		    Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getNumOfInterview(),Constants.INTERVIEW_COUNT,file);
	   
		    try {
		    	int [] width = new int[]{3,5,3,3,3,5,5};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString(), 0);
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}else if(submissionType.equals("OFFERMADE")){
			String[] headers = Constants.REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS;
		   // DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
	        StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
		    Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getNumOfOffers(),Constants.OFFER_COUNT,file);

		    try {
		    	int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString(), 0);
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}else if(submissionType.equals("REJECTED")){
			String[] headers = Constants.REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS;
		  //  DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
		    Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getNumOfRejected(),Constants.REJECTED_COUNT,file);

		    try {
		    	int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5,5};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString(), 0);
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}else{
			String[] headers = Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS;
		   // DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    /*File file = new File(orgName+"_Requirements_"+dateFormat.format(new Date())+".pdf");*/
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
	        Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getSubmittedCount(),Constants.SUBMITTED_APPLICANTS_COUNT,file);
		    try {
		    	int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5,5,5};
		    	PdfPTable table = getPdfTable(headers,width,sbPipeHearder.toString(), 0);
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
		}
		}
		
	public File getRequirementWorkflowPipelenePdf(java.util.List<RequirementSubmission> requirementStatusList, OrgLocations loginOrgLocation,
			String requirementId,String submissionType,HashMap<String, String> stageColumnMap ,RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		StringBuilder fileStringBuilder=new StringBuilder();
		
		
			//String[] headers = Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS;
		      String[] headers = Arrays.copyOf(stageColumnMap.keySet().toArray(), stageColumnMap.size(), String[].class);
		    File file = File.createTempFile(fileStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
			StringBuilder sbPipeHearder = new StringBuilder();
	        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
	        Document document =  prepareHeadres(loginOrgLocation,requirementId,requirementSubmissionCounts.getWorkflowStageCount(),submissionType,file);
		    try {
		      // int [] width = new int[]{3,5, 3,5, 5, 3,5,3,5,5,5,5}; // these are each column fixed size widths. 
		    	// we are getting headers are dynamically thats why above width properties are not used.
		    	PdfPTable table = getWorkflowPdfTable(headers, sbPipeHearder.toString(), 0);
		        table.completeRow();
		        java.util.List<String[]> requirements = new ArrayList<>();
		        for(RequirementSubmission requirementsub : requirementStatusList){
		        	requirements.add(prepareRequirementWorkflowPipelineToArray(requirementsub, stageColumnMap));
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
		        
		        document.addTitle("Applicants Table");
		        table.setSpacingBefore(15f);
		        document.add(table);
		        logger.info("Pdf table created !!");
		    } catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		  }
		    return file;
		}
	private String[] prepareRequirementPipelineToArraySubmitClient(RequirementSubmission requirementsub,OrgLocations loginOrgLocation, int columnCount) {
		/*String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS.length];*/
		String requirementArray[] = new String[columnCount];
		int count = 0;
/*		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
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
		}*/
		if(requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())){
			requirementArray[count++] = requirementsub.getResumeName();

		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getCreatedDate() != null && !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())){
			requirementArray[count++] = requirementsub.getCreatedDate().toString();

		}else{
			requirementArray[count++] = "--";
		}
		
		if(requirementsub.getCandidate().getExpectedRate() != null && !"".equalsIgnoreCase(requirementsub.getCandidate().getExpectedRate())){
			requirementArray[count++] = requirementsub.getCandidate().getExpectedRate();
		}else{
			requirementArray[count++] = "--";
		}
//		proposedSubmissionRate
		if(requirementsub.getProposedSubmissionRate() != null && !"".equalsIgnoreCase(requirementsub.getProposedSubmissionRate())){
			requirementArray[count++] = requirementsub.getProposedSubmissionRate();
		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())){
			requirementArray[count++] = requirementsub.getUserName();
		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())){
			requirementArray[count++] = requirementsub.getScore();
		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			StringBuilder expSB = new StringBuilder();
			requirementArray[count++] = expSB.append(requirementsub.getWorkExperience()).append(" years").toString();
		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getStatus() != null){
			requirementArray[count++] = requirementsub.getStatus().toString();
		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getSubmittedOn() != null){
			requirementArray[count++] = requirementsub.getSubmittedOn();
		}else{
			requirementArray[count++] = "--";
		}
		if(requirementsub.getSubmittedBy() != null){
			requirementArray[count++] = requirementsub.getSubmittedBy();
		}else{
			requirementArray[count++] = "--";
		}
		
		//client contact name related code start
		if(requirementsub.getRequirement().getId() != null){
			 if(requirementsub.getRequirement().getSpocId().getId() != null){
				 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getId() != null){
					 StringBuilder spocSB = new StringBuilder();
					 if(requirementsub.getRequirement().getSpocId().getClientUser().getLastName() != null ){
						 requirementArray[count++] = spocSB.append(requirementsub.getRequirement().getSpocId().getClientUser().getFirstName()).append(" ").append(requirementsub.getRequirement().getSpocId().getClientUser().getLastName()).toString();
					 }else{
						 requirementArray[count++] =spocSB.append(requirementsub.getRequirement().getSpocId().getClientUser().getFirstName()).toString();
					 }
				 }
			 }
		 }
		
		
		 /*if(requirementsub.getRequirement().getSpocId().getInitiatorLocationId().getId().longValue() == loginOrgLocation.getId().longValue() ){
			 if(requirementsub.getRequirement().getId() != null){
				 if(requirementsub.getRequirement().getSpocId().getId() != null){
					 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getId() != null){
						 StringBuilder spocSB = new StringBuilder();
						 if(requirementsub.getRequirement().getSpocId().getSpocUser().getLastName() != null ){
							 requirementArray[13] = spocSB.append(requirementsub.getRequirement().getSpocId().getSpocUser().getFirstName()).append(" ").append(requirementsub.getRequirement().getSpocId().getSpocUser().getLastName()).toString();
						 }else{
							 requirementArray[13] =spocSB.append(requirementsub.getRequirement().getSpocId().getSpocUser().getFirstName()).toString();
						 }
					 }
				 }
			 }
		 }
		  
		 if(requirementsub.getRequirement().getSpocId().getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()){
			 if(requirementsub.getRequirement().getId() != null){
				 if(requirementsub.getRequirement().getSpocId().getId() != null){
					 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getId() != null){
						 StringBuilder spocSB1 = new StringBuilder();
						 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getLastName() != null){
							 requirementArray[13] = spocSB1.append(requirementsub.getRequirement().getSpocId().getOwnerUser().getFirstName()).append(" ").append(requirementsub.getRequirement().getSpocId().getOwnerUser().getLastName()).toString();
						 }else{
							 requirementArray[13] = spocSB1.append(requirementsub.getRequirement().getSpocId().getOwnerUser().getFirstName()).toString();
						 }
					 }
				 }
			 }
		}*/
		 //client contact name related code end
		 
		/*if(requirementsub.getRequirement().getSpocId().getSpocUser().getMobilePhone() != null){
			requirementArray[13] = requirementsub.getRequirement().getSpocId().getSpocUser().getMobilePhone();
		}else{
			requirementArray[13] = "--";
		}*/
		
		
		return requirementArray;
	}


	private String[] prepareRequirementPipelineToArrayRequestSent(RequirementSubmission requirementsub) {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_REQUEST_SENT_HEADERS.length];
		
		
		
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
		if(requirementsub.getSubmissionStatus().toString() != null){
			requirementArray[9] = requirementsub.getSubmissionStatus().toString();
		}else{
			requirementArray[9] = "--";
		}
		return requirementArray;
	}


	/**
	 * @author Rajashekar
	 * prepares column names for pdf table
	 * @param headers
	 * @param width
	 * @param title 
	 * @return PdfPTable
	 */
	private PdfPTable getPdfTable(String[] headers, int[] width, String titleName, int columnCount) {
		PdfPTable table = new PdfPTable(headers.length);
		try{
			Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(titleName.toString(),font));
	        cell1.setColspan(columnCount > 0 ? columnCount : 14);
	        headerCellStyle(cell1);
            table.addCell(cell1);
			table.setWidthPercentage(100);
	        table.setWidths(width);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * @author Srinivas Sabbu
	 * prepares column names for pdf table
	 * @param headers
	 * @param width
	 * @param title 
	 * @return PdfPTable
	 */
	private PdfPTable getWorkflowPdfTable(String[] headers, String titleName, int columnCount) {
		PdfPTable table = new PdfPTable(headers.length);
		try{
			Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(titleName.toString(),font));
	        cell1.setColspan(columnCount > 0 ? columnCount : 14);
	        headerCellStyle(cell1);
            table.addCell(cell1);
			table.setWidthPercentage(100);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return table;
	}
/**
 * @author Rajashekar
 * prepares header for pdf
 * @param orgName
 * @param requirementId
 * @param count
 * @param headerName
 * @param file 
 * @return Document
 * 
 */
	private Document prepareHeadres(OrgLocations loginOrgLocation, String requirementId, int count, String headerName, File file) {
		    Document document = new Document(PageSize.LETTER.rotate());
		    StringBuilder orderedListStringBuilder=new StringBuilder();
		    try {
		    	Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
		    	PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();
		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myBordColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myBordColor);
		        StringBuilder sbPipeHearder = new StringBuilder();
		        sbPipeHearder.append(loginOrgLocation.getOrgLocationName()).append(" ").append("Jobs");
	            Paragraph para = new Paragraph(orderedListStringBuilder.append(loginOrgLocation.getOrgLocationName()).append(" Jobs.").toString(),boldFont);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
		        // Create an instance of PdfPTable. After that we transform the header and
	            List orderedList = new List(List.ORDERED);
	           // List orderedList = new ArrayList<>();
	            StringBuilder reqNameBuilder=new StringBuilder();
	            String jobloclist = "";
	            StringBuilder locBuilder=new StringBuilder();
	            StringBuilder joblocBuilder=new StringBuilder();
	            StringBuilder expBuilder=new StringBuilder();
		        orderedList.add(new ListItem(reqNameBuilder.append("Job Name :").append(requirement.getJobTitle()).toString()));
		        java.util.List<RequirementLocations> locationsList;
		        try {
					locationsList = requirementLocationService.getRequirementLocationsListByRequirementId(requirement.getId());
					if(locationsList != null && !locationsList.isEmpty()) {
						for(RequirementLocations locationsJob : locationsList) {
							if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
								jobloclist = joblocBuilder.append(locationsJob.getLocation()).append("\n").append("         ").toString();

							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 orderedList.add(new ListItem(locBuilder.append("Location :").append(jobloclist).toString()));

		        //orderedList.add(new ListItem(locBuilder.append("Location :").append(requirement.getCountry()).toString()));
		        if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			        orderedList.add(new ListItem(expBuilder.append("Experience :").append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString()));
				}else if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())){
			        orderedList.add(new ListItem(expBuilder.append("Experience :" ).append("0 -").append(requirement.getMinExp()).append("years").toString()));
				}else if(requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())){
				        orderedList.add(new ListItem(expBuilder.append("Experience :").append("0 -").append(requirement.getMaxExp()).append("years").toString()));
				}else{
				        orderedList.add(new ListItem("Experience : --"));
				}
		        StringBuilder headerNameBuilder=new StringBuilder();
		        orderedList.add(new ListItem(headerNameBuilder.append(headerName).append(" ").append(count).toString()));

		        document.add(orderedList);
		    }catch(Exception ex){
		    	ex.printStackTrace();
		    }
			return document;
	}


/**
 * @author Rakshith
 * convert pojo to Array of Rejected
 * @param requirementsub
 * @return
 * @throws Exception 
 */
	private String[] prepareRequirementPipelineToArrayRejected(RequirementSubmission requirementsub) throws Exception {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS.length];
		
		
		OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		
		// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
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
			//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementsub.getCreatedDate());
			requirementArray[4] = createdDate;

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



/**
 * @author Rakshith
 * convert pojo to Array of Offer
 * @param requirementsub
 * @return
 */
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



/**
 * @author Rakshith
 * convert pojo to Array of Interview
 * @param requirementsub
 * @return
 * @throws Exception 
 */
	private String[] prepareRequirementPipelineToArrayInterview(RequirementSubmission requirementsub) throws Exception {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS.length];
		
		OrgLocations orglo = orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
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
		
		java.util.List<RequirementInterview> rquirementInterviews = requirementsub.getRequirementInterviewList();
		StringBuilder interviewStringBuilder = new StringBuilder();
		for(RequirementInterview listInterviews:rquirementInterviews){
			if(listInterviews.getDateOfInterview() != null && !"".equalsIgnoreCase(listInterviews.getDateOfInterview().toString())){
				requirementArray[4] = commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),listInterviews.getDateOfInterview());
			}else{
				requirementArray[4] = "--";
			}
			if(listInterviews.getStartTimeStr() != null && !"".equalsIgnoreCase(listInterviews.getStartTimeStr()) && listInterviews.getEndTimeStr() != null && !"".equalsIgnoreCase(listInterviews.getEndTimeStr())){
				requirementArray[5] = interviewStringBuilder.append(listInterviews.getStartTimeStr()).append("-").append(listInterviews.getEndTimeStr()).toString();
			}else if(listInterviews.getStartTimeStr() != null && !"".equalsIgnoreCase(listInterviews.getEndTimeStr())){
				requirementArray[5] = interviewStringBuilder.append(listInterviews.getStartTimeStr()).toString();
			}else{
				requirementArray[5] = "--";
			}
			if(listInterviews.getLocation() != null && !"".equalsIgnoreCase(listInterviews.getLocation())){
				requirementArray[6] = listInterviews.getLocation();
			}else{
				requirementArray[6] = "--";
				}
		}
		return requirementArray;
	}



	/**
	 * @param requirementsub
	 * @return
	 * @throws Exception 
	 * @Description: pdf for shortlisted.
	 */
	private String[] prepareRequirementPipelineToArrayShortedListed(RequirementSubmission requirementsub) throws Exception {
		String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS.length];
		
		OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		
		 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		
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
			//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementsub.getCreatedDate());
			requirementArray[4] = createdDate;

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
		if(requirementsub.getSubmissionStatus() != null){
			requirementArray[9] = requirementsub.getSubmissionStatus().toString();
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

/**
 * @author Rakshith
 * convert pojo to Array Applicants
 * @param requirementsub
 * @return
 * @throws Exception 
 */
	private String[] prepareRequirementPipelineToArray(RequirementSubmission requirementsub) throws Exception {
			String requirementArray[] = new String[Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS.length];
			
			OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
			
			// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		
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
				//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
				String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementsub.getCreatedDate());
				requirementArray[4] = createdDate;

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
				requirementArray[7] = requirementsub.getScore()+"%";
			}else{
				requirementArray[7] = "--";
			}
			if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
				StringBuilder expSB = new StringBuilder();
				requirementArray[8] = expSB.append(requirementsub.getWorkExperience()).append(" years").toString();
			}else{
				requirementArray[8] = "--";
			}
			if(requirementsub.getSubmissionStatus() != null){
				String stageName = null;
				HashMap <String, String> submissionStatusMap = Constants.DEFAULT_WORKFLOW_STAGES_AND_SHORTCODES_MAP;
				submissionStatusMap.put("SELECTED_ON_INTERVIEW", "SELECTED_ON_INTERVIEW");
				submissionStatusMap.put("CLIENT_INTERVIEW", "CLIENT_INTERVIEW");
				if(submissionStatusMap.containsValue(requirementsub.getSubmissionStatus())){
					requirementArray[9] = requirementsub.getSubmissionStatus().toString();
				}else{
					stageName = requirementWorkFlowService.getWorkflowStageNameByShortCode(requirementsub.getSubmissionStatus().toString());
					requirementArray[9] = stageName;
				}
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
	/**
	 * @author SRINIVAS SABBU.
	 * convert pojo to Array Applicants
	 * @param requirementsub
	 * @return
	 * @throws Exception 
	 */
		private String[] prepareRequirementWorkflowPipelineToArray(RequirementSubmission requirementsub, HashMap<String, String> stageColumnMap) throws Exception {
				String requirementArray[] = new String[stageColumnMap.size()];
				
				ArrayList<String> colPropLst =  new ArrayList(stageColumnMap.values());
				int colIndex = 0;
				Class mainClass = RequirementSubmission.class;
			 // Here we are using JAVA 8 Reflection API to get dynamic object properties  of an object below.
				Field field = null;
				for(String getColumn : colPropLst) {
					if(getColumn.equalsIgnoreCase("submissionStatus")){
						String stageName = null;
						field= mainClass.getDeclaredField(getColumn);
						 field.setAccessible(true);
							HashMap <String, String> submissionStatusMap = Constants.DEFAULT_WORKFLOW_STAGES_AND_SHORTCODES_MAP;
							submissionStatusMap.put("SELECTED_ON_INTERVIEW", "SELECTED_ON_INTERVIEW");
							submissionStatusMap.put("CLIENT_INTERVIEW", "CLIENT_INTERVIEW");
							if(submissionStatusMap.containsValue(requirementsub.getSubmissionStatus())){
								requirementArray[colIndex++]=field.get(requirementsub) == null ?"--":field.get(requirementsub).toString();
							}else{
								stageName = requirementWorkFlowService.getWorkflowStageNameByShortCode(requirementsub.getSubmissionStatus().toString());
								requirementArray[colIndex++]=stageName == null ?"--":stageName;
							}
					}else{
						field= mainClass.getDeclaredField(getColumn);
						 field.setAccessible(true);
						 requirementArray[colIndex++]=field.get(requirementsub) == null ?"--":field.get(requirementsub).toString();
					}
					 
				}
				
				return requirementArray;
			}
	private static void headerCellStyle(PdfPCell cell){
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setPaddingTop(0f);
	    cell.setPaddingBottom(7f);
	    cell.setBackgroundColor(new BaseColor(0,121,182));
	    cell.setBorder(0);
	    cell.setBorderWidthBottom(2f);
	    }


}
