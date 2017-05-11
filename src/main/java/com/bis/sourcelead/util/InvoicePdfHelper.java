package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import com.itextpdf.text.Chunk;
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
import com.itextpdf.text.pdf.draw.LineSeparator;

@Component
public class InvoicePdfHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoicePdfHelper.class);
	
	public static final String FONTB = "/fonts/OpenSans-Bold.ttf";
	public static final String FONT = "/fonts/OpenSans-Regular.ttf";
	
	protected Font font10;
	protected Font font10b;
	protected Font font12;
	protected Font font12b;
	protected Font font14;
	
	public InvoicePdfHelper() throws DocumentException, IOException{
		BaseFont bf = BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
		BaseFont bfb = BaseFont.createFont(FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED);
		
		font10 = new Font(bf,10);
		font10b = new Font(bfb,10);
		font12 = new Font(bf,12);
		font12b = new Font(bfb,12);
		font14 = new Font(bf,14);
	}
	
	@Autowired
	EmployeesService employeesService;
	
public void createPdfTableForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws IOException, Exception{
		
	 try {
         OutputStream file = new FileOutputStream(new File("/root/Desktop/PDF_FILE/Test.pdf"));
         Document document = new Document();
         PdfWriter writer = PdfWriter.getInstance(document, file);
         writer.createXmpMetadata();
         document.open();
         Paragraph p;
         p= new Paragraph("InvoiveID -"+invoice.getInvoiceId(),font14);
         p.setAlignment(Element.ALIGN_RIGHT);
         document.add(p);
         p= new Paragraph("Invoive Date : 8-05-2017 ",font14);
         p.setAlignment(Element.ALIGN_RIGHT);
         document.add(p);
         p= new Paragraph("Payment terms :20 Days",font14);
         p.setAlignment(Element.ALIGN_RIGHT);
         document.add(p);
         p= new Paragraph("Due Date :31-12-2017",font14);
         p.setAlignment(Element.ALIGN_RIGHT);
         document.add(p);
         
         PdfPTable table = new PdfPTable(2);
         table.setWidthPercentage(100);
         
         PdfPCell from = getAddress("Billing From:","Rajashekar","rajasekhar@businessintelli.com","9959812177","Business Intelli Solurions","Hyderabad");
         table.addCell(from);
         
         PdfPCell to = getAddress("Billing To:","Snigitha","snigitha@businessintelli.com","8888888888","Tata Consultancy","USA");
         table.addCell(to);
         document.add(table);
         
         table = new PdfPTable(5);
         table.setWidthPercentage(100);
         table.setSpacingBefore(10);
         table.setSpacingAfter(10);
         table.setWidths(new int[]{5,6,2,4,2});
         
         table.addCell(getCell("Associate name",Element.ALIGN_LEFT,font12b));
         table.addCell(getCell("Duration",Element.ALIGN_LEFT,font12b));
         table.addCell(getCell("Rate",Element.ALIGN_LEFT,font12b));
         table.addCell(getCell("Total Hours",Element.ALIGN_LEFT,font12b));
         table.addCell(getCell("Total",Element.ALIGN_LEFT,font12b));
         
         table.addCell(getCell("Rajashekar", Element.ALIGN_LEFT, font12));
         table.addCell(getCell("1-1-2017 to 12-12-2017", Element.ALIGN_RIGHT, font12));
         table.addCell(getCell("55000", Element.ALIGN_RIGHT, font12));
         table.addCell(getCell("812", Element.ALIGN_RIGHT, font12));
         table.addCell(getCell("800000", Element.ALIGN_RIGHT, font12));
         document.add(table);
         
         String[] type = {"vat","vat"};
         String[] percentage = {"%","flat"};
         String currency[] = {"IST","USD"};
         String basic[] = {"50","50"};
         String tax[] = {"50","50"};
         document.add(getTotalsTable(
       		    "6.5", "5000",
       		    "8000", "USD",
       		    type, percentage,
       		    basic, tax,
       		    currency));
         
         document.add(Chunk.NEWLINE);
         LineSeparator ls = new LineSeparator();
         ls.setAlignment(Element.ALIGN_BOTTOM);
         document.add(new Chunk(ls));
         
         document.close();
		  file.close();

       System.out.println("Pdf created successfully..");

   } catch (Exception e) {
       e.printStackTrace();
   }

}
private PdfPCell getCell(String value, int alignLeft, Font font)
{
	PdfPCell cell = new PdfPCell();
	cell.setUseAscender(true);
	cell.setUseAscender(true);
	Paragraph p = new Paragraph(value,font);
	p.setAlignment(alignLeft);
	cell.addElement(p);
	return cell;
}
private PdfPCell getAddress(String who, String name, String email, String mobile, String orgName, String address)
{
	PdfPCell cell = new PdfPCell();
	cell.setBorder(cell.NO_BORDER);
	cell.addElement(new Paragraph(who,font12));
	cell.addElement(new Paragraph(name,font12));
	cell.addElement(new Paragraph(email,font12));
	cell.addElement(new Paragraph(mobile,font12));
	cell.addElement(new Paragraph(orgName,font12));
	cell.addElement(new Paragraph(address,font12));
	return cell;
}


public PdfPTable getTotalsTable(String tBase, String tTax, String tTotal,
	    String tCurrency, String[] type, String[] percentage, String base[],
	    String tax[], String currency[]) throws DocumentException {
	    PdfPTable table = new PdfPTable(6);
	    table.setWidthPercentage(100);
	    table.setWidths(new int[]{1, 1, 3, 3, 3, 1});
	    table.addCell(getCell("TAX", Element.ALIGN_LEFT, font12b));
	    table.addCell(getCell("%", Element.ALIGN_RIGHT, font12b));
	    table.addCell(getCell("Base amount:", Element.ALIGN_LEFT, font12b));
	    table.addCell(getCell("Tax amount:", Element.ALIGN_LEFT, font12b));
	    table.addCell(getCell("Total:", Element.ALIGN_LEFT, font12b));
	    table.addCell(getCell("", Element.ALIGN_LEFT, font12b));
	    int n = type.length;
	    for (int i = 0; i < n; i++) {
	        table.addCell(getCell(type[i], Element.ALIGN_RIGHT, font12));
	        table.addCell(getCell(percentage[i], Element.ALIGN_RIGHT, font12));
	        table.addCell(getCell(base[i], Element.ALIGN_RIGHT, font12));
	        table.addCell(getCell(tax[i], Element.ALIGN_RIGHT, font12));
	        double total = Double.parseDouble(base[i]) + Double.parseDouble(tax[i]);
	        table.addCell(getCell("5000", Element.ALIGN_RIGHT, font12));
	        table.addCell(getCell(currency[i], Element.ALIGN_LEFT, font12));
	    }
	    PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
	    cell.setColspan(2);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    table.addCell(getCell(tBase, Element.ALIGN_RIGHT, font12b));
	    table.addCell(getCell(tTax, Element.ALIGN_RIGHT, font12b));
	    table.addCell(getCell(tTotal, Element.ALIGN_RIGHT, font12b));
	    table.addCell(getCell(tCurrency, Element.ALIGN_LEFT, font12b));
	    return table;
	}
		/*String[] headers = Constants.INVOICE_PDF_HEADERS;
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
            Paragraph para = new Paragraph(orgNameSB.append(" try {
              OutputStream file = new FileOutputStream(new File("C:\\Users\\Welcome\\Desktop\\JAVA_BOOKS\\Test.pdf"));
	          Document document = new Document();
	          PdfWriter writer = PdfWriter.getInstance(document, file);
	          writer.createXmpMetadata();
	          document.open();
	          Paragraph p;
	          p= new Paragraph("InvoiveID -12345 ",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          p= new Paragraph("Invoive Date : 8-05-2017 ",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          p= new Paragraph("Payment terms :20 Days",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          p= new Paragraph("Due Date :31-12-2017",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          
	          PdfPTable table = new PdfPTable(2);
	          table.setWidthPercentage(100);
	          
	          PdfPCell from = getAddress("Billing From:","Rajashekar","rajasekhar@businessintelli.com","9959812177","Business Intelli Solurions","Hyderabad");
	          table.addCell(from);
	          
	          PdfPCell to = getAddress("Billing To:","Snigitha","snigitha@businessintelli.com","8888888888","Tata Consultancy","USA");
	          table.addCell(to);
	          document.add(table);
	          
	          table = new PdfPTable(5);
	          table.setWidthPercentage(100);
	          table.setSpacingBefore(10);
	          table.setSpacingAfter(10);
	          table.setWidths(new int[]{5,6,2,4,2});
	          
	          table.addCell(getCell("Associate name",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Duration",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Rate",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Total Hours",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Total",Element.ALIGN_LEFT,font12b));
	          
	          table.addCell(getCell("Rajashekar", Element.ALIGN_LEFT, font12));
	          table.addCell(getCell("1-1-2017 to 12-12-2017", Element.ALIGN_RIGHT, font12));
	          table.addCell(getCell("55000", Element.ALIGN_RIGHT, font12));
	          table.addCell(getCell("812", Element.ALIGN_RIGHT, font12));
	          table.addCell(getCell("800000", Element.ALIGN_RIGHT, font12));
	          document.add(table);
	          
	          String[] type = {"vat","vat"};
	          String[] percentage = {"%","flat"};
	          String currency[] = {"IST","USD"};
	          String basic[] = {"50","50"};
	          String tax[] = {"50","50"};
	          document.add(getTotalsTable(
	        		    "6.5", "5000",
	        		    "8000", "USD",
	        		    type, percentage,
	        		    basic, tax,
	        		    currency));
	          
	          document.add(Chunk.NEWLINE);
	          LineSeparator ls = new LineSeparator();
	          ls.setAlignment(Element.ALIGN_BOTTOM);
	          document.add(new Chunk(ls));
	          
	          document.close();
			  file.close();
 
            System.out.println("Pdf created successfully..");
 
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	private PdfPCell getCell(String value, int alignLeft, Font font)
	{
		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseAscender(true);
		Paragraph p = new Paragraph(value,font);
		p.setAlignment(alignLeft);
		cell.addElement(p);
		return cell;
	}
	private PdfPCell getAddress(String who, String name, String email, String mobile, String orgName, String address)
	{
		PdfPCell cell = new PdfPCell();
		cell.setBorder(cell.NO_BORDER);
		cell.addElement(new Paragraph(who,font12));
		cell.addElement(new Paragraph(name,font12));
		cell.addElement(new Paragraph(email,font12));
		cell.addElement(new Paragraph(mobile,font12));
		cell.addElement(new Paragraph(orgName,font12));
		cell.addElement(new Paragraph(address,font12));
		return cell;
	}
	
	
	public PdfPTable getTotalsTable(String tBase, String tTax, String tTotal,
		    String tCurrency, String[] type, String[] percentage, String base[],
		    String tax[], String currency[]) throws DocumentException {
		    PdfPTable table = new PdfPTable(6);
		    table.setWidthPercentage(100); try {
              OutputStream file = new FileOutputStream(new File("C:\\Users\\Welcome\\Desktop\\JAVA_BOOKS\\Test.pdf"));
	          Document document = new Document();
	          PdfWriter writer = PdfWriter.getInstance(document, file);
	          writer.createXmpMetadata();
	          document.open();
	          Paragraph p;
	          p= new Paragraph("InvoiveID -12345 ",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          p= new Paragraph("Invoive Date : 8-05-2017 ",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          p= new Paragraph("Payment terms :20 Days",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          p= new Paragraph("Due Date :31-12-2017",font14);
	          p.setAlignment(Element.ALIGN_RIGHT);
	          document.add(p);
	          
	          PdfPTable table = new PdfPTable(2);
	          table.setWidthPercentage(100);
	          
	          PdfPCell from = getAddress("Billing From:","Rajashekar","rajasekhar@businessintelli.com","9959812177","Business Intelli Solurions","Hyderabad");
	          table.addCell(from);
	          
	          PdfPCell to = getAddress("Billing To:","Snigitha","snigitha@businessintelli.com","8888888888","Tata Consultancy","USA");
	          table.addCell(to);
	          document.add(table);
	          
	          table = new PdfPTable(5);
	          table.setWidthPercentage(100);
	          table.setSpacingBefore(10);
	          table.setSpacingAfter(10);
	          table.setWidths(new int[]{5,6,2,4,2});
	          
	          table.addCell(getCell("Associate name",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Duration",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Rate",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Total Hours",Element.ALIGN_LEFT,font12b));
	          table.addCell(getCell("Total",Element.ALIGN_LEFT,font12b));
	          
	          table.addCell(getCell("Rajashekar", Element.ALIGN_LEFT, font12));
	          table.addCell(getCell("1-1-2017 to 12-12-2017", Element.ALIGN_RIGHT, font12));
	          table.addCell(getCell("55000", Element.ALIGN_RIGHT, font12));
	          table.addCell(getCell("812", Element.ALIGN_RIGHT, font12));
	          table.addCell(getCell("800000", Element.ALIGN_RIGHT, font12));
	          document.add(table);
	          
	          String[] type = {"vat","vat"};
	          String[] percentage = {"%","flat"};
	          String currency[] = {"IST","USD"};
	          String basic[] = {"50","50"};
	          String tax[] = {"50","50"};
	          document.add(getTotalsTable(
	        		    "6.5", "5000",
	        		    "8000", "USD",
	        		    type, percentage,
	        		    basic, tax,
	        		    currency));
	          
	          document.add(Chunk.NEWLINE);
	          LineSeparator ls = new LineSeparator();
	          ls.setAlignment(Element.ALIGN_BOTTOM);
	          document.add(new Chunk(ls));
	          
	          document.close();
			  file.close();
 
            System.out.println("Pdf created successfully..");
 
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	private PdfPCell getCell(String value, int alignLeft, Font font)
	{
		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseAscender(true);
		Paragraph p = new Paragraph(value,font);
		p.setAlignment(alignLeft);
		cell.addElement(p);
		return cell;
	}
	private PdfPCell getAddress(String who, String name, String email, String mobile, String orgName, String address)
	{
		PdfPCell cell = new PdfPCell();
		cell.setBorder(cell.NO_BORDER);
		cell.addElement(new Paragraph(who,font12));
		cell.addElement(new Paragraph(name,font12));
		cell.addElement(new Paragraph(email,font12));
		cell.addElement(new Paragraph(mobile,font12));
		cell.addElement(new Paragraph(orgName,font12));
		cell.addElement(new Paragraph(address,font12));
		return cell;
	}
	
	
	public PdfPTable getTotalsTable(String tBase, String tTax, String tTotal,
		    String tCurrency, String[] type, String[] percentage, String base[],
		    String tax[], String currency[]) throws DocumentException {
		    PdfPTable table = new PdfPTable(6);
		    table.setWidthPercentage(100);
		    table.setWidths(new int[]{1, 1, 3, 3, 3, 1});
		    table.addCell(getCell("TAX", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("%", Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell("Base amount:", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("Tax amount:", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("Total:", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("", Element.ALIGN_LEFT, font12b));
		    int n = type.length;
		    for (int i = 0; i < n; i++) {
		        table.addCell(getCell(type[i], Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(percentage[i], Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(base[i], Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(tax[i], Element.ALIGN_RIGHT, font12));
		        double total = Double.parseDouble(base[i]) + Double.parseDouble(tax[i]);
		        table.addCell(getCell("5000", Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(currency[i], Element.ALIGN_LEFT, font12));
		    }
		    PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
		    cell.setColspan(2);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    table.addCell(cell);
		    table.addCell(getCell(tBase, Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell(tTax, Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell(tTotal, Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell(tCurrency, Element.ALIGN_LEFT, font12b));
		    return table;
		}
		    table.setWidths(new int[]{1, 1, 3, 3, 3, 1});
		    table.addCell(getCell("TAX", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("%", Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell("Base amount:", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("Tax amount:", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("Total:", Element.ALIGN_LEFT, font12b));
		    table.addCell(getCell("", Element.ALIGN_LEFT, font12b));
		    int n = type.length;
		    for (int i = 0; i < n; i++) {
		        table.addCell(getCell(type[i], Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(percentage[i], Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(base[i], Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(tax[i], Element.ALIGN_RIGHT, font12));
		        double total = Double.parseDouble(base[i]) + Double.parseDouble(tax[i]);
		        table.addCell(getCell("5000", Element.ALIGN_RIGHT, font12));
		        table.addCell(getCell(currency[i], Element.ALIGN_LEFT, font12));
		    }
		    PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
		    cell.setColspan(2);
		    cell.setBorder(PdfPCell.NO_BORDER);
		    table.addCell(cell);
		    table.addCell(getCell(tBase, Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell(tTax, Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell(tTotal, Element.ALIGN_RIGHT, font12b));
		    table.addCell(getCell(tCurrency, Element.ALIGN_LEFT, font12b));
		    return table;
		}INVOICE").toString());
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
	    }*/
	  
	

}
