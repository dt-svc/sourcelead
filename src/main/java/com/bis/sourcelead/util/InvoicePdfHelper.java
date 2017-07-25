package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

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
	
	public InvoicePdfHelper() throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
		BaseFont bfb = BaseFont.createFont(FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED);
		font10 = new Font(bf, 10);
		font10b = new Font(bfb, 10);
		font12 = new Font(bf, 12);
		font12b = new Font(bfb, 12);
		font14 = new Font(bf, 14);
	}
	
	@Autowired
	EmployeesService employeesService;
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	public File createPdfTableForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org,String invoiceType, String vendorCurrencyCode, String loginOrgCurrencyCode) throws IOException, Exception
	{
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		String countryCurrency  = countries.getCurrency();
		SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
		File invoiceFile = new File(orgLocation.getOrgLocationName() + "_" + invoice.getInvoiceId() + ".pdf");
		if(Constants.PAYABLE.equalsIgnoreCase(invoiceType)){
		//StringBuilder fileSB = new StringBuilder();
		Document document = new Document();
		//File invoiceFile = File.createTempFile(fileSB.append(org.getOrgName()).append(invoice.getInvoiceId()).toString(),".pdf");
		invoiceFile.setReadable(true);
		FileOutputStream os = new FileOutputStream(invoiceFile);
		try {
			PdfWriter.getInstance(document, os);
			document.open();
			// save image file in pdf
			document = addLogoToPdfFile(document, org,orgLocation);
			Paragraph p;
			p= new Paragraph("Bill", font12b);
		        p.setAlignment(Element.ALIGN_CENTER );
		        document.add(p);
			p = new Paragraph("Billing Id :" + invoice.getInvoiceId(), font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			String createdDate = dateFormat.format(invoice.getCreatedDate());
			p = new Paragraph("Billing Date :" + createdDate, font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			if(null != invoice.getPaymentFrequency()){
			p = new Paragraph("Payment Terms :" + invoice.getPaymentFrequency(), font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			}
			String dueDate = dateFormat.format(invoice.getDueDate());
			p = new Paragraph("Due Date :" + dueDate, font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());
			StringBuilder address = new StringBuilder();
			if (null != orgLocation.getCity()) {
				address.append(orgLocation.getCountry()).append(orgLocation.getState()).append(orgLocation.getCity()).toString();
			}
			else if (null != orgLocation.getState()) {
				address.append(orgLocation.getCountry()).append(orgLocation.getState()).toString();
			}
			else {
				address.append(orgLocation.getCountry()).toString();
			}
			PdfPCell from = getAddress("Billing From:", user.getFullName(), user.getEmail(), user.getMobilePhone(), orgLocation.getOrgLocationName(), address.toString());
			table.addCell(from);
			StringBuilder clientAddress = new StringBuilder();
			if (null != orgRel.getResponderLocationId().getCity()) {
				clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(orgRel.getResponderLocationId().getState()).append(orgRel.getResponderLocationId().getCity()).toString();
			}
			else if (null != orgRel.getResponderLocationId().getState()) {
				clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(orgRel.getResponderLocationId().getState()).toString();
			}
			else {
				clientAddress.append(orgRel.getResponderLocationId().getCountry()).toString();
			}
			PdfPCell to = getAddress("Billing To:", invoice.getContactUser().getFullName(), invoice.getContactUser().getEmail(), invoice.getContactUser().getMobilePhone(), orgRel.getResponderLocationId().getOrgLocationName(), clientAddress.toString());
			table.addCell(to);
			document.add(table);
			table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10);
			table.setSpacingAfter(10);
			table.setWidths(new int[] { 3, 5, 5, 3, 3, 3 });
			table.addCell(getCell("Associate Id", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Associate Name", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Date", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Rate", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Total Hours", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Total", Element.ALIGN_CENTER, font12b));
			Set<InvoiceLineItems> invoiveAssociateData = invoice.getInvoiceLineItems();

			for (InvoiceLineItems invoiceLineItems : invoiveAssociateData) {
				if(invoiceLineItems.getCandidate() !=null){
					Employees employee = null;
						if(invoiceLineItems.getCandidate().getConsultantUser() != null){
						 employee = employeesService.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
						}
					if(employee != null){
						if(null != employee.getEmployeeId()  &&  !"".equalsIgnoreCase(employee.getEmployeeId())){
							table.addCell(getCell(employee.getEmployeeId(), Element.ALIGN_CENTER, font10));
						}else{
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					}else{
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
				}else{
					if(invoiceLineItems.getUser() !=null){
						table.addCell(getCell(invoiceLineItems.getUser().getId().toString(), Element.ALIGN_CENTER, font10));
					}
					else{
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
				}
			
				StringBuffer name = new StringBuffer();
				
				if (null != invoiceLineItems.getCandidate()) {
					if (invoiceLineItems.getCandidate().getFirstName() !=null && invoiceLineItems.getCandidate().getLastName() != null) {
						table.addCell(getCell(name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ").append(invoiceLineItems.getCandidate().getLastName()).toString(), Element.ALIGN_CENTER, font10));
					}else {
						table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER, font10));
					}
				}else{
					if(invoiceLineItems.getUser() !=null){
						table.addCell(getCell(name.append(invoiceLineItems.getUser().getFirstName()).append(" ").append(invoiceLineItems.getUser().getLastName()).toString(), Element.ALIGN_CENTER, font10));
					}else{
						table.addCell(getCell(invoiceLineItems.getUser().getFirstName(), Element.ALIGN_CENTER, font10));
					}
				}
				
				StringBuffer startAndEndDate = new StringBuffer();
				if (null != invoiceLineItems.getStartDate()
						&& null != invoiceLineItems.getEndDate()) {
					String StartDate =dateFormat.format(invoiceLineItems.getStartDate());
					String endDate = dateFormat.format(invoiceLineItems.getEndDate());
					table.addCell(getCell(startAndEndDate.append(StartDate).append("to").append(endDate).toString(), Element.ALIGN_CENTER, font10));
				}
				else if (null != invoiceLineItems.getStartDate()) {
					String StartDate = dateFormat.format(invoiceLineItems.getStartDate());
					table.addCell(getCell(startAndEndDate.append(StartDate).toString(), Element.ALIGN_CENTER, font10));
				}
				else {
					table.addCell(getCell(startAndEndDate.append("--").toString(), Element.ALIGN_CENTER, font10));
				}
				if (null != invoiceLineItems.getRate()
						&& !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
					table.addCell(getCell(invoiceLineItems.getRate(), Element.ALIGN_CENTER, font10));
				}
				else {
					table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
				}
				if (null != invoiceLineItems.getTotalHours()
						&& !"".equalsIgnoreCase(invoiceLineItems.getTotalHours())) {
					table.addCell(getCell(invoiceLineItems.getTotalHours(), Element.ALIGN_CENTER, font10));
				}
				else {
					table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
				}
				if (null != invoiceLineItems.getAmount()
						&& !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
					table.addCell(getCell(invoiceLineItems.getAmount(), Element.ALIGN_CENTER, font10));
				}
				else {
					table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
				}
			}
			
			 PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
			 	cell.setBorder(PdfPCell.NO_BORDER);
				cell.setBorder(PdfPCell.NO_BORDER);
				cell.setBorder(PdfPCell.NO_BORDER);
				cell.setBorder(PdfPCell.NO_BORDER);
	            table.addCell(cell);
				table.addCell(cell);
				table.addCell(cell);
				table.addCell(cell);
				table.addCell(getCell("Sub Total", Element.ALIGN_CENTER, font10b));
				table.addCell(getCell(" " + countryCurrency  +invoice.getSubTotal(), Element.ALIGN_CENTER, font10b));
			
			document.add(table);
			
			document.add(getTotalsTable(invoice));
			document.add(discountTotaltable(invoice));
			
			p= new Paragraph("Grand Total  " + countryCurrency  +  "   "+ invoice.getTotal(), font12b);
	        p.setAlignment(Element.ALIGN_RIGHT );
	        document.add(p);
			document.add(Chunk.NEWLINE);
			
			if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
				p= new Paragraph("Conversion From  "  + invoice.getCurrencyCode()  + "   "+ invoice.getTotal(), font12b);
	            p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
				document.add(Chunk.NEWLINE);
				
				p= new Paragraph("Conversion To  "  + invoice.getConvertCurrencyCode()  + "   "+ invoice.getConvertAmount(), font12b);
		        p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
		        
				document.add(Chunk.NEWLINE);
				
				p= new Paragraph("Exchange Rate  "  + invoice.getCurrencyCode()  +" 1 = "+invoice.getConvertCurrencyCode()+"   "+  invoice.getExchangeRate(), font12b);
		        p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
		        document.add(Chunk.NEWLINE);
			}
			
			
			LineSeparator ls = new LineSeparator();
			ls.setAlignment(Element.ALIGN_BOTTOM);
			document.add(new Chunk(ls));
			if(null != invoice.getInvoiceComment()){
				p= new Paragraph("Note :"+invoice.getInvoiceComment(), font12);
		          p.setAlignment(Element.ALIGN_LEFT);
		          document.add(p);
				}
			//file.close();
		}  catch (DocumentException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        document.close();
	        os.flush();
	        os.close();
	    }
		}else{
			Document document = new Document();
			//File invoiceFile = File.createTempFile(fileSB.append(org.getOrgName()).append(invoice.getInvoiceId()).toString(),".pdf");
			invoiceFile.setReadable(true);
			FileOutputStream os = new FileOutputStream(invoiceFile);
			try {
				PdfWriter.getInstance(document, os);
				document.open();
				// save image file in pdf
				document = addLogoToPdfFile(document, org,orgLocation);
				Paragraph p;
				p= new Paragraph("Invoice", font12b);
			        p.setAlignment(Element.ALIGN_CENTER );
			        document.add(p);
				p = new Paragraph("Invoice Id :" + invoice.getInvoiceId(), font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				String createdDate = dateFormat.format(invoice.getCreatedDate());
				p = new Paragraph("Invoice Date :" + createdDate, font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				if(null != invoice.getPaymentFrequency()){
				p = new Paragraph("Payment Terms :" + invoice.getPaymentFrequency(), font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				}
				String dueDate = dateFormat.format(invoice.getDueDate());
				p = new Paragraph("Due Date :" + dueDate, font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(100);
				OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());
				StringBuilder address = new StringBuilder();
				if (null != orgLocation.getCity()) {
					address.append(orgLocation.getCountry()).append(orgLocation.getState()).append(orgLocation.getCity()).toString();
				}
				else if (null != orgLocation.getState()) {
					address.append(orgLocation.getCountry()).append(orgLocation.getState()).toString();
				}
				else {
					address.append(orgLocation.getCountry()).toString();
				}
				PdfPCell from = getAddress("Invoice From:", user.getFullName(), user.getEmail(), user.getMobilePhone(), orgLocation.getOrgLocationName(), address.toString());
				table.addCell(from);
				StringBuilder clientAddress = new StringBuilder();
				if (null != orgRel.getResponderLocationId().getCity()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(orgRel.getResponderLocationId().getState()).append(orgRel.getResponderLocationId().getCity()).toString();
				}
				else if (null != orgRel.getResponderLocationId().getState()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(orgRel.getResponderLocationId().getState()).toString();
				}
				else {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).toString();
				}
				PdfPCell to = getAddress("Invoice To:", invoice.getContactUser().getFullName(), invoice.getContactUser().getEmail(), invoice.getContactUser().getMobilePhone(), orgRel.getResponderLocationId().getOrgLocationName(), clientAddress.toString());
				table.addCell(to);
				document.add(table);
				table = new PdfPTable(6);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10);
				table.setSpacingAfter(10);
				table.setWidths(new int[] { 3, 5, 5, 3, 3, 3 });
				table.addCell(getCell("Associate Id", Element.ALIGN_CENTER, font12b));
				table.addCell(getCell("Associate Name", Element.ALIGN_CENTER, font12b));
				table.addCell(getCell("Date", Element.ALIGN_CENTER, font12b));
				table.addCell(getCell("Rate", Element.ALIGN_CENTER, font12b));
				table.addCell(getCell("Total Hours", Element.ALIGN_CENTER, font12b));
				table.addCell(getCell("Total", Element.ALIGN_CENTER, font12b));
				Set<InvoiceLineItems> invoiveAssociateData = invoice.getInvoiceLineItems();
				for (InvoiceLineItems invoiceLineItems : invoiveAssociateData) {
					//table.addCell(getCell(invoiceLineItems.getCandidate().getId().toString(), Element.ALIGN_CENTER, font10));
					if(invoiceLineItems.getCandidate() !=null){
						Employees employee = null;
//						if(invoiceLineItems.getCandidate() !=null){
							if(invoiceLineItems.getCandidate().getConsultantUser() != null){
							 employee = employeesService.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
							}
//						}else{
//							 employee = employeesService.getEmployeeByUserId(invoiceLineItems.getUser().getId());
//						}
						if(employee != null){
							if(null != employee.getEmployeeId()  &&  !"".equalsIgnoreCase(employee.getEmployeeId())){
								table.addCell(getCell(employee.getEmployeeId(), Element.ALIGN_CENTER, font10));
							}else{
								table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
							}
						}else{
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					}else{
						if(invoiceLineItems.getUser() !=null){
							table.addCell(getCell(invoiceLineItems.getUser().getId().toString(), Element.ALIGN_CENTER, font10));
						}else{
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					}
					StringBuffer name = new StringBuffer();
					
					
					if (null != invoiceLineItems.getCandidate()) {
						if (invoiceLineItems.getCandidate().getFirstName() !=null && invoiceLineItems.getCandidate().getLastName() !=null) {
							table.addCell(getCell(name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ").append(invoiceLineItems.getCandidate().getLastName()).toString(), Element.ALIGN_CENTER, font10));
						}else {
							table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER, font10));
						}
						
					}else{
						if (null != invoiceLineItems.getUser()) {
							table.addCell(getCell(name.append(invoiceLineItems.getUser().getFirstName()).append(" ").append(invoiceLineItems.getUser().getLastName()).toString(), Element.ALIGN_CENTER, font10));
						}else{
							table.addCell(getCell(invoiceLineItems.getUser().getFirstName(), Element.ALIGN_CENTER, font10));
						}
					}
					
					StringBuffer startAndEndDate = new StringBuffer();
					if (null != invoiceLineItems.getStartDate()
							&& null != invoiceLineItems.getEndDate()) {
						String StartDate = dateFormat.format(invoiceLineItems.getStartDate());
						String endDate = dateFormat.format(invoiceLineItems.getEndDate());
						table.addCell(getCell(startAndEndDate.append(StartDate).append("to").append(endDate).toString(), Element.ALIGN_CENTER, font10));
					}
					else if (null != invoiceLineItems.getStartDate()) {
						String StartDate = dateFormat.format(invoiceLineItems.getStartDate());
						table.addCell(getCell(startAndEndDate.append(StartDate).toString(), Element.ALIGN_CENTER, font10));
					}
					else {
						table.addCell(getCell(startAndEndDate.append("--").toString(), Element.ALIGN_CENTER, font10));
					}
					if (null != invoiceLineItems.getRate()
							&& !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
						table.addCell(getCell(invoiceLineItems.getRate(), Element.ALIGN_CENTER, font10));
					}
					else {
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
					if (null != invoiceLineItems.getTotalHours()
							&& !"".equalsIgnoreCase(invoiceLineItems.getTotalHours())) {
						table.addCell(getCell(invoiceLineItems.getTotalHours(), Element.ALIGN_CENTER, font10));
					}
					else {
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
					if (null != invoiceLineItems.getAmount()
							&& !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
						table.addCell(getCell(invoiceLineItems.getAmount(), Element.ALIGN_CENTER, font10));
					}
					else {
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
				}
				
				 PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setBorder(PdfPCell.NO_BORDER);
		            table.addCell(cell);
					table.addCell(cell);
					table.addCell(cell);
					table.addCell(cell);
					table.addCell(getCell("Sub Total", Element.ALIGN_CENTER, font10b));
					table.addCell(getCell(countryCurrency + " "+  invoice.getSubTotal(), Element.ALIGN_CENTER, font10b));
				
				document.add(table);
				
				document.add(getTotalsTable(invoice));
				document.add(discountTotaltable(invoice));
				
				p= new Paragraph("Grand Total  " + countryCurrency  +  "   "+ invoice.getTotal(), font12b);
		        p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
				document.add(Chunk.NEWLINE);
				
				if(!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)){
				p= new Paragraph("Conversion From  "  + invoice.getCurrencyCode()  + "   "+ invoice.getTotal(), font12b);
	            p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
				document.add(Chunk.NEWLINE);
				
				p= new Paragraph("Conversion To  "  + invoice.getConvertCurrencyCode()  + "   "+ invoice.getConvertAmount(), font12b);
		        p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
				document.add(Chunk.NEWLINE);
				
				p= new Paragraph("Exchange Rate  "  + invoice.getCurrencyCode()  +" 1 = "+invoice.getConvertCurrencyCode()+"   "+  invoice.getExchangeRate(), font12b);
		        p.setAlignment(Element.ALIGN_RIGHT );
		        document.add(p);
				document.add(Chunk.NEWLINE);
				}
				LineSeparator ls = new LineSeparator();
				ls.setAlignment(Element.ALIGN_BOTTOM);
				document.add(new Chunk(ls));
				if(null != invoice.getInvoiceComment()){
				p= new Paragraph("Note :"+invoice.getInvoiceComment(), font12);
		          p.setAlignment(Element.ALIGN_LEFT);
		          document.add(p);
				}
				//file.close();
			}  catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } catch (FileNotFoundException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		        os.flush();
		        os.close();
		    }
			
		}
		return invoiceFile;
	}
	
	private Document addLogoToPdfFile(Document document, Org org, OrgLocations orgLocations) throws MalformedURLException, IOException, DocumentException
	{
		if (null != org.getDocFile()) {
			Image image = Image.getInstance(org.getDocFile());
			image.scalePercent(300f);
			image.setAbsolutePosition(0, (float) (PageSize.A4.getHeight() - 25.0));
			image.setAbsolutePosition(100, 500);
			// Fixed Positioning
			image.setAbsolutePosition(40f, 780f);
			// Scale to new height and new width of image
			image.scaleAbsolute(100, 50);
			document.add(image);
		}else{
		Paragraph p;
		p = new Paragraph(" " + orgLocations.getOrgLocationName(), font12b);
		p.setAlignment(Element.ALIGN_TOP);
		document.add(p);
		}
		return document;
	}
	
	private PdfPCell getCell(String value, int alignLeft, Font font)
	{
		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseAscender(true);
		Paragraph p = new Paragraph(value, font);
		p.setAlignment(alignLeft);
		cell.addElement(p);
		return cell;
	}
	
	private PdfPCell getAddress(String who, String name, String email, String mobile, String orgName, String address)
	{
		PdfPCell cell = new PdfPCell();
		cell.setBorder(cell.NO_BORDER);
		cell.addElement(new Paragraph(who, font12b));
		cell.addElement(new Paragraph(name, font10));
		cell.addElement(new Paragraph(email, font10));
		cell.addElement(new Paragraph(mobile, font10));
		cell.addElement(new Paragraph(orgName, font10));
		cell.addElement(new Paragraph(address, font10));
		return cell;
	}
	
	public PdfPTable getTotalsTable(Invoice invoice) throws Exception
	{
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 4, 3, 3, 3 });
		if(!invoice.getInvoiceTax().isEmpty()){
		table.addCell(getCell("TAX", Element.ALIGN_CENTER, font12b));
		table.addCell(getCell("Type", Element.ALIGN_CENTER, font12b));
		table.addCell(getCell("Base Amount", Element.ALIGN_CENTER, font12b));
		table.addCell(getCell("Tax Amount", Element.ALIGN_CENTER, font12b));
		//table.addCell(getCell("Currency Type", Element.ALIGN_LEFT, font12b));
		Set<InvoiceTax> invoiveTaxDetails = invoice.getInvoiceTax();
		for (InvoiceTax InvoiceTax : invoiveTaxDetails) {
			table.addCell(getCell(InvoiceTax.getTaxName(), Element.ALIGN_CENTER, font10));
			table.addCell(getCell(InvoiceTax.getTaxType(), Element.ALIGN_CENTER, font10));
			table.addCell(getCell(InvoiceTax.getTaxValue(), Element.ALIGN_CENTER, font10));
			table.addCell(getCell(InvoiceTax.getTaxAmount(), Element.ALIGN_CENTER, font10));
			//table.addCell(getCell("currency", Element.ALIGN_LEFT, font10));
		}
		/*
		 * PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
		 * cell.setBorder(PdfPCell.NO_BORDER); table.addCell(cell);
		 * table.setWidths(new int[]{5,3});
		 * table.addCell(getCell("GrandTotal"+invoice.getTotal(),
		 * Element.ALIGN_LEFT, font12b)); table.addCell(getCell("Currency Type",
		 * Element.ALIGN_LEFT, font12b));
		 * 
		 */
		table.setSpacingAfter(20);
		}
		return table;
	}
	
	/*private PdfPTable grandTotaltable(Invoice invoice) throws DocumentException
	{
		PdfPTable table = new PdfPTable(2);
		PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 5, 3 });
		// table.addCell(getCell("Discount:"+invoice.getDiscountValue(),
		// Element.ALIGN_LEFT, font12b));
		table.addCell(getCell("GrandTotal:" + invoice.getTotal(), Element.ALIGN_LEFT, font12b));
		table.addCell(getCell("Currency Type", Element.ALIGN_LEFT, font12b));
		table.getDefaultCell().setBorder(0);
		return table;
	}*/
	
	/*private PdfPTable discountTotaltable(Invoice invoice) throws DocumentException
	{
		PdfPTable table = new PdfPTable(1);
		PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 5 });
		table.addCell(getCell("Discount:"
				+ invoice.getDiscountValue(), Element.ALIGN_LEFT, font12b));
		table.getDefaultCell().setBorder(0);
		return table;
	}*/
	
	private PdfPTable discountTotaltable(Invoice invoice) throws Exception
	{
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 2,2, 2,2 });
		if(invoice.getDiscountType() != null && invoice.getDiscountValue() != null){
		table.addCell(getCell("Discount", Element.ALIGN_CENTER, font12b));
		table.addCell(getCell(" " + invoice.getDiscountType(), Element.ALIGN_CENTER, font10));
		if(invoice.getDiscountAmount() != null){
		table.addCell(getCell(" " + invoice.getDiscountAmount(), Element.ALIGN_CENTER, font10));
		}else{
		table.addCell(getCell(" " + "--", Element.ALIGN_CENTER, font10));
	
		}
		table.addCell(getCell(" " + invoice.getDiscountValue(), Element.ALIGN_CENTER, font12b));
		table.setSpacingAfter(20);
		}
			return table;
	}
	
	public File getBillingPdf(java.util.List<Invoice> invoiceList, String orgName,InvoiceAmount totalStagesAmount,String exportStartDate,String exportEndDate, String invoiceType) throws Exception {
		
		String[] headers = null;
		if(Constants.Payables.equalsIgnoreCase(invoiceType)){
		 headers = Constants.BILLING_PAYABLE_HEADERS;
		}else{
		 headers = Constants.BILLING_HEADERS;
		}
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    
	   
	    StringBuilder sbReq =new StringBuilder();
	    sbReq.append(orgName).append(Constants.BILLING).append(dateFormat.format(new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append(orgName).append(Constants.BILLING).append(dateFormat.format(new Date())).toString(),".pdf");

	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	        BaseColor myBordColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myBordColor);
//	        StringBuilder sbReqStyle = new StringBuilder();
//	        sbReqStyle.append(orgName).append(" ").append(Constants.BILLING);

	        StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgName).append(" ").append(Constants.BILLING).toString(),boldFont);

            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
	        // Create an instance of PdfPTable. After that we transform the header and
//            List orderedList = new List(List.ORDERED);
//	        orderedList.add(new ListItem("Company Name :" +orgName));
//	        orderedList.add(new ListItem("Duration  :" +exportStartDate+" - "+exportEndDate));
//	        orderedList.add(new ListItem("Total Unpaid Amount(Open) :"+totalStagesAmount.getTotalOpenUnpaidAmount()));
//	        orderedList.add(new ListItem("Total Unpaid Amount(OverDue) :"+totalStagesAmount.getTotalOverDueUnpaidAmount()));
//	        orderedList.add(new ListItem("Total Paid Amount :"+totalStagesAmount.getTotalPaidAmount()));
//	        orderedList.add(new ListItem("Total Unbilled Amount  :"+totalStagesAmount.getTotalUnbilledAmount()));
//	        document.add(orderedList);
            
            Chunk glue = new Chunk(new VerticalPositionMark());
	        Paragraph p = new Paragraph("Company Name :" +orgName);
	        p.add(new Chunk(glue));
	        p.setIndentationLeft(0);
	        p.add("Duration  :" +exportStartDate+" - "+exportEndDate);
	        p.setIndentationRight(223);
	        document.add(p);  
	        
	        Chunk glue1 = new Chunk(new VerticalPositionMark());
	        Paragraph p1 = new Paragraph("Total Unpaid Amount(Open) :"+totalStagesAmount.getTotalOpenUnpaidAmount());
	        p1.add(new Chunk(glue1));
	        p1.setIndentationLeft(0);
	        p1.add("Total Unpaid Amount(OverDue) :"+totalStagesAmount.getTotalOverDueUnpaidAmount());
	        p1.setIndentationRight(220);
	        document.add(p1); 
	        
	        Chunk glue2 = new Chunk(new VerticalPositionMark());
	        Paragraph p2 = new Paragraph("Total Paid Amount :"+totalStagesAmount.getTotalPaidAmount());
	        p2.add(new Chunk(glue2));
	        p2.setIndentationLeft(0);
	        p2.add("Total Unbilled Amount  :"+totalStagesAmount.getTotalUnbilledAmount());
	        p2.setIndentationRight(227);
	        document.add(p2); 
            
            PdfPTable table = new PdfPTable(headers.length);
            Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
            PdfPCell cell1 = null;
            if(Constants.Payables.equalsIgnoreCase(invoiceType)){
	         cell1 = new PdfPCell(new Phrase(Constants.BILLING_LIST,font));
            }else{
             cell1 = new PdfPCell(new Phrase(Constants.INVOICE_LIST,font));
            }
	        cell1.setColspan(13);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 3,5,3,5,5,3,3,5});
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> invoices = new ArrayList<>();
	        for(Invoice invoice : invoiceList){
	        	invoices.add(prepareBillingToArray(invoice));
	        }
	        
	        for (String[] invoiceArray: invoices) {
                for (int i = 0; i < invoiceArray.length; i++) {
                    String datum = invoiceArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        
	        document.addTitle("Billing List Table");
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
	
	 private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
		{
			DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
			String yesterDayDate = outputFormatter.print(yesterDayZoned);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
			return TZ_DF_TS.parse(startDateString);
		}
	
	
	private static void headerCellStyle(PdfPCell cell){
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setPaddingTop(0f);
	    cell.setPaddingBottom(7f);
	    cell.setBackgroundColor(new BaseColor(0,121,182));
	    cell.setBorder(0);
	    cell.setBorderWidthBottom(2f);
	    }
	
	
	
	private String[] prepareBillingToArray(Invoice invoice) {
		
	String billingArray[] = new String[Constants.BILLING_HEADERS.length];
	
	if(null != invoice.getInvoiceId() && !"".equalsIgnoreCase(invoice.getInvoiceId())){
		billingArray[0] = invoice.getInvoiceId();
	}else{
		billingArray[0] = "--";
	}
	
	OrgLocations orgLocations = null;
	if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == invoice.getOrgLocation().getId().longValue()){
		orgLocations = invoice.getOrgRelationID().getLocationId();
	}else if(invoice.getOrgRelationID().getLocationId().getId().longValue() == invoice.getOrgLocation().getId().longValue()){
		orgLocations = invoice.getOrgRelationID().getResponderLocationId();
	}
	if(null != orgLocations){
			billingArray[1] = orgLocations.getOrgLocationName();
	}else{
			billingArray[1] = "--";
	}
	
	if(null != invoice.getContactUser()){
		if(null != invoice.getContactUser().getLastName() && !"".equalsIgnoreCase(invoice.getContactUser().getLastName())){
		StringBuilder contactName = new StringBuilder();
		billingArray[2] = contactName.append(invoice.getContactUser().getFirstName()).append(" ").append(invoice.getContactUser().getLastName()).toString();
	}else if(null != invoice.getContactUser().getFirstName()){
		billingArray[2] = invoice.getContactUser().getFirstName();
	}else{
		billingArray[2] = "--";
	}
	}else{
		billingArray[2] = "--";
	}
	
	if(null != invoice.getContactUser()){
		if(null != invoice.getContactUser().getEmail() && !"".equalsIgnoreCase(invoice.getContactUser().getEmail())){
		billingArray[3] = invoice.getContactUser().getEmail(); 
	}else{
		billingArray[3] =  "--";
	}
	}else{
		billingArray[3] = "--";
	}

	if(null != invoice.getContactUser()){
		if(invoice.getContactUser().getMobilePhone() != null && !"".equalsIgnoreCase(invoice.getContactUser().getMobilePhone())){
			billingArray[4] = invoice.getContactUser().getMobilePhone();
		}else{
			billingArray[4] = "--";	
		}
	}else{
		billingArray[4] = "--";
	}
	
	if(null != invoice.getInvoiceDate()){
		String invoiceDate = Constants.DF_DMY.format(invoice.getInvoiceDate());
		billingArray[5] = invoiceDate;
	}else{
		billingArray[5] = "--";
	}
	
	if(null != invoice.getDueDate()){
		String dueDate = Constants.DF_DMY.format(invoice.getDueDate());
		billingArray[6] = dueDate;  
	}else{
		billingArray[6] = "--";
	}
	
	if(null != invoice.getTotal() && !"".equalsIgnoreCase(invoice.getTotal())){
		billingArray[7] = invoice.getTotal();
	}else{
		billingArray[7] = "--";
	}
	
	if(null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount())){
		billingArray[8] = invoice.getDueAmount();
	}else{
		billingArray[8] = "--";
	}
	
	if(null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount()) ){
		Double paidAmount = Double.parseDouble(invoice.getTotal()) - Double.parseDouble(invoice.getDueAmount());
		String convertedPaidAmount = String.valueOf(paidAmount);
		billingArray[9] = convertedPaidAmount;
	}else{
		billingArray[9] = "--";
	}
	
	
	if(null != invoice.getInvoiceStatus() && !"".equalsIgnoreCase(invoice.getInvoiceStatus())){
		billingArray[10] = invoice.getInvoiceStatus();
	}else{
		billingArray[10] = "--";
	}
	
	if(null != invoice.getPaymentStatus() && !"".equalsIgnoreCase(invoice.getPaymentStatus())){
		billingArray[11] = invoice.getPaymentStatus();
	}else{
		billingArray[11] = "--";
	}
	
	if(null != invoice.getLinkInvoiceReferenceId() && !"".equalsIgnoreCase(String.valueOf(invoice.getLinkInvoiceReferenceId()))){
		billingArray[12] = String.valueOf(invoice.getLinkInvoiceReferenceId());
	}else{
		billingArray[12] = "--";
	}
	
	return billingArray;
	
	}
	
	
}
