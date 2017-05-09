package com.bis.sourcelead.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmployeesService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class InvoicePdfHelper {
	
	//  public static final String ICC = "resources/data/sRGB_CS_profile.icm";

//	    public static final String FONT = "resources/fonts/OpenSans-Regular.ttf";
	    Font FONT =new Font(FontFamily.TIMES_ROMAN,50.0f,Font.UNDERLINE,BaseColor.RED);

	   // public static final String FONTB = "resources/fonts/OpenSans-Bold.ttf";
	
	private static final Logger logger = LoggerFactory.getLogger(InvoicePdfHelper.class);
	
	@Autowired
	EmployeesService employeesService;
	
public void createPdfTableForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws IOException, Exception{
		
		
		String[] headers = Constants.INVOICE_PDF_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");


	    StringBuilder fileSB = new StringBuilder();
	    fileSB = fileSB.append(invoice.getInvoiceId()+"invoice_").append(dateFormat.format(new Date())+".pdf");

	    
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	    	PdfWriter.getInstance(document,new FileOutputStream("/root/Desktop/PDF_FILE/"+fileSB));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append("Invoice");
	        
	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append("INVOICE").toString());
            para.setAlignment(100);
            
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
    			StringBuilder contactName = new StringBuilder();
    			if(invoice.getContactContactUser().getLastName() != null){
    				contactName.append(invoice.getContactContactUser().getFirstName()).append(" ").append(invoice.getContactContactUser().getLastName()).toString();	
    			}else{
    				contactName.append(invoice.getContactContactUser().getFirstName()).toString();
    			}
	        List orderedList = new List(List.UNORDERED);
//	        orderedList.getLastItem().setAlignment(Element.ALIGN_JUSTIFIED_ALL);
	        orderedList.add(new ListItem("Contact Name:"+contactName));
	        orderedList.add(new ListItem("Email:"+invoice.getContactContactUser().getEmail()));
	        orderedList.add(new ListItem("Phone:"+invoice.getContactContactUser().getMobilePhone()));
	        if(invoice.getClientOrgRelationID().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
	        	orderedList.add(new ListItem("Company Name:"+invoice.getClientOrgRelationID().getResponderLocationId().getOrg().getOrgName()));
			}
			if(invoice.getClientOrgRelationID().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orderedList.add(new ListItem("Company Name:"+invoice.getClientOrgRelationID().getLocationId().getOrg().getOrgName()));
			}

			 if(invoice.getClientOrgRelationID().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
		        	orderedList.add(new ListItem("Company address:"+invoice.getClientOrgRelationID().getResponderLocationId().getCountry()));
			}
				if(invoice.getClientOrgRelationID().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					orderedList.add(new ListItem("Company address:"+invoice.getClientOrgRelationID().getLocationId().getCountry()));
			}
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        
	        //========================================
	        PdfPTable table1 = new PdfPTable(2);

	        table.setWidthPercentage(100);

	       PdfPCell seller = getPartyAddress("From:",

	                user.getFirstName(),

	                user.getEmail(),

	                user.getMobilePhone(),

	                org.getOrgName(),

	                orgLocation.getCountry());

	        table.addCell(seller);

	        PdfPCell buyer = getPartyAddress("To:",

	                invoice.getContactContactUser().getFirstName(),

	                invoice.getContactContactUser().getEmail(),

	                invoice.getContactContactUser().getMobilePhone(),

	                invoice.getClientName(),

	                "Company Address Pending");

	        table1.addCell(buyer);
	       
	        table1.addCell(buyer);

	        document.add(table1);
	        
	        //========================================
	        
	        
	        
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(11);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5});
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
	        java.util.Set<String[]> usersList = new HashSet<>();
	        Set<InvoiceLineItems> invoiceLineItems = invoice.getInvoiceLineItems();
	        for (InvoiceLineItems invoiceLineItems1 : invoiceLineItems) {
	        	usersList.add(prepareInvoiceStringArray(invoiceLineItems1,headers));
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
	        document.addTitle("Sales Invoice");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        
	        List orderedList1 = new List(List.ALIGN_LEFT);
	        if(invoice.getSubTotal()!=null){
	        	orderedList1.add(new ListItem("Sub Total = "+invoice.getSubTotal()));
	        }
	        if(invoice.getDiscountAmount()!=null){
	        	orderedList1.add(new ListItem("Discount value:"+invoice.getDiscountAmount()));
	        }
	        
	        orderedList1.add(new ListItem("Service Tax:"));
	        orderedList1.add(new ListItem("============================="));
	        if(invoice.getTotal()!=null){
	        orderedList1.add(new ListItem("Total:"+invoice.getTotal()));
	        }
	        document.add(orderedList1);
	        logger.info("Pdf table created !!");
	    } catch (DocumentException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        document.close();
	    }
	  
	}
	

private String[] prepareInvoiceStringArray(InvoiceLineItems lineItems, String[] headers) throws Exception {
	String userArray[] = new String[headers.length];
		 String val = lineItems.getCandidate().getFirstName();
		 if(null != lineItems.getCandidate().getConsultantUser()){
			 Employees emp = employeesService.getEmployeeByUserId(lineItems.getCandidate().getConsultantUser().getId());
			 val = val+"&"+emp.getEmployeeId();
		 }
		 if(lineItems.getCandidate() !=null){
			 userArray[0] = val;
		 }else{
			 userArray[0] = "--";
		 }
		 if(lineItems.getStartDate() !=null){
			 String startDate = Constants.DF_YMD.format(lineItems.getStartDate());
			 if(lineItems.getEndDate() !=null){
				 startDate= startDate +"-"+Constants.DF_YMD.format(lineItems.getEndDate());
			 }
			 userArray[1] = startDate;
		 }
		 else{
			 userArray[1] = "--";
		 }
		 if(lineItems.getRate() !=null){
			 userArray[2] = lineItems.getRate();
		 }else{
			 userArray[2] = "--";
		 }
		 if(lineItems.getTotalHours() !=null){
			 userArray[3] = lineItems.getTotalHours();
		 }else{
			 userArray[3] = "--";
		 }
		 if(lineItems.getAmount() !=null){
			 userArray[4] = lineItems.getAmount();
		 }else{
			 userArray[4] = "--";
		 }
	   
	return userArray;
}


public PdfPCell getPartyAddress(String who,String name, String email, String mobile, String orgName, String companyAddress) throws DocumentException, IOException {
	
//	   BaseFont bf = BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
//
//     BaseFont bfb = BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
     
//	    Font font10 = new Font(bf, 10);
//
//	    Font font10b = new Font(bfb, 10);
//
//	    Font font12 = new Font(bf, 12);
//
//	    Font font12b = new Font(bfb, 12);
//
//	    Font font14 = new Font(bf, 14);
	   
	   PdfPCell cell = new PdfPCell();

     cell.setBorder(PdfPCell.NO_BORDER);

     cell.addElement(new Paragraph(who, FONT));
     
     cell.addElement(new Paragraph(name, FONT));

     cell.addElement(new Paragraph(email, FONT));

     cell.addElement(new Paragraph(mobile, FONT));

     cell.addElement(new Paragraph(orgName, FONT));

     cell.addElement(new Paragraph(orgName, FONT));

     return cell;

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
