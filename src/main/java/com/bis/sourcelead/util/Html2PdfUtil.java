package com.bis.sourcelead.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Component
public class Html2PdfUtil {
	
	@Autowired
	RequirementService requirementService;
	
	public void convertHtml2PdfUsingHtmlWorker(){
		try {
			//TODO replace the string with actual HTML content.
			String k = "<html><body> This is from iText PDF HTML Worker </body></html>";
			OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingHtmlWorker.pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();
			//TODO use the latest XMLWorkerHelper class in below method instead of deprectaed HTMLWorker class
			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));
			document.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertHtml2PdfUsingXMLWorker(){
		try {
			//TODO replace the string with actual HTML content.
		    String k = "<html><body> This is from iText PDF XML Worker   </body></html>";
		    OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingXMLWorker.pdf"));
			Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, file);
		    document.open();
		    InputStream is = new ByteArrayInputStream(k.getBytes());
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
		    document.close();
		    file.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public byte[] getPdfBytes(ContractsTemplate contractsTemplate, Contracts contracts, OrgLocations orgLocation, Org org) throws Exception{
		
		String templateContent = replacePlaceHolders(contractsTemplate.getTemplateContent(),contracts,orgLocation,org);
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(templateContent));
  	    document.close();
  	    byte[] pdfBytes = byteArray.toByteArray();
		return pdfBytes;
	}
	
	
	 private String replacePlaceHolders(String templateBody, Contracts contracts, OrgLocations orgLocation, Org org) throws Exception {
	        
	        String orgName = "";
	        if(contracts.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getLocationId().getOrg().getOrgName();
	        }
	        else if(contracts.getOrgRelations().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getResponderLocationId().getOrg().getOrgName();
	        }
	        
	         Pattern logEntry = Pattern.compile("\\[(.*?)\\]");
	            Matcher matchPattern = logEntry.matcher(templateBody);
	            StringBuffer sb = new StringBuffer();
	            while(matchPattern.find()) {
	                if(matchPattern.group().equalsIgnoreCase("[Client Name]") || matchPattern.group().equalsIgnoreCase("[Vendor Name]")){
	                    matchPattern.appendReplacement(sb, orgName);
	                }else if(matchPattern.group().equalsIgnoreCase("[Client location]") || matchPattern.group().equalsIgnoreCase("[Vendor location]")){
	                    matchPattern.appendReplacement(sb, contracts.getContractOrgLocationId().getCountry());    
	                }else if(matchPattern.group().equalsIgnoreCase("[Client Contact]") || matchPattern.group().equalsIgnoreCase("[Vendor Contact]")){
	                    matchPattern.appendReplacement(sb, contracts.getContactUser().getFirstName());
	                }else if(matchPattern.group().equalsIgnoreCase("[Company Location]")){
	                    matchPattern.appendReplacement(sb, orgLocation.getCountry());
	                }else if(matchPattern.group().equalsIgnoreCase("[Company Name]")){
	                    matchPattern.appendReplacement(sb, org.getOrgName());
		            }else if(matchPattern.group().equalsIgnoreCase("[Associate Name]")){
	                    matchPattern.appendReplacement(sb, org.getOrgName());
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Requirement Title]")){
		            	Requirement req = requirementService.getRequirementByRequirementId(contracts.getRequirementSubmission().getRequirement().getId());
	                    matchPattern.appendReplacement(sb, req.getJobTitle());
	                }
	                // need to know about offer rate
		           /* else if(matchPattern.group().equalsIgnoreCase("[Offer Rate]")){
	                    matchPattern.appendReplacement(sb, contracts.geto);
	                }*/
		            else if(matchPattern.group().equalsIgnoreCase("[Billing Rate]")){
	                    matchPattern.appendReplacement(sb, contracts.getBillingAmount());
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Overtime]")){
	                    matchPattern.appendReplacement(sb, contracts.getOverTimeHours());
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Payment Term]")){
	                    matchPattern.appendReplacement(sb, contracts.getPaymentTerm());
	                }
	    			
		            else if(matchPattern.group().equalsIgnoreCase("[Start Date]")){
	                    matchPattern.appendReplacement(sb, Constants.DF_DMY.format(contracts.getStartDate()));
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[End date]")){
		            	if(null != contracts.getEndDate()){
		            		matchPattern.appendReplacement(sb, Constants.DF_DMY.format(contracts.getEndDate()));
		            	}
		            }  
		            else if(matchPattern.group().equalsIgnoreCase("[State Of Jurisdiction]")){
		            	if(null != contracts.getStateOfJurisdiction()){
		            		matchPattern.appendReplacement(sb, contracts.getStateOfJurisdiction());
		            	}
		            }  
	            }
	            matchPattern.appendTail(sb);
	        return sb.toString();
	    }
	
}
