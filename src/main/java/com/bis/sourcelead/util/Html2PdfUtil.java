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

import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Component
public class Html2PdfUtil {
	
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

	public byte[] getPdfBytes(ContractsTemplate contractsTemplate, Contracts contracts, OrgLocations orgLocation, Org org) throws DocumentException, IOException{
		
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
	
	
	 private String replacePlaceHolders(String templateBody, Contracts contracts, OrgLocations orgLocation, Org org) {
//	        System.out.println("vendor contact "+contracts.getContactUser().getFirstName());
//	        System.out.println("vendor location "+contracts.getContractOrgLocationId().getCountry());
	        
	        String orgName = "";
	        if(contracts.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getLocationId().getOrg().getOrgName();
	        }if(contracts.getOrgRelations().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getResponderLocationId().getOrg().getOrgName();
	        }
//	        System.out.println("vendoe name "+orgName);
	        
	         Pattern logEntry = Pattern.compile("\\[(.*?)\\]");
	            Matcher matchPattern = logEntry.matcher(templateBody);
	            StringBuffer sb = new StringBuffer();
	            while(matchPattern.find()) {
//	                System.out.println(matchPattern.group());
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
	                    
	                }
	            }
	            matchPattern.appendTail(sb);
	        return sb.toString();
	    }
	
}
