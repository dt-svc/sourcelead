package com.bis.sourcelead.util;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.transport.TransportResponse.Empty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
	
	public File createPdfTableForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org,String invoiceType) throws IOException, Exception
	{
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		String countryCurrency  = countries.getCurrency();
		
		File invoiceFile = new File(org.getOrgName() + "_" + invoice.getInvoiceId() + ".pdf");
		if(invoiceType.equalsIgnoreCase("payable")){
		//StringBuilder fileSB = new StringBuilder();
		Document document = new Document();
		//File invoiceFile = File.createTempFile(fileSB.append(org.getOrgName()).append(invoice.getInvoiceId()).toString(),".pdf");
		invoiceFile.setReadable(true);
		FileOutputStream os = new FileOutputStream(invoiceFile);
		try {
			PdfWriter.getInstance(document, os);
			document.open();
			// save image file in pdf
			document = addLogoToPdfFile(document, org);
			Paragraph p;
			p = new Paragraph("Billing Id :" + invoice.getInvoiceId(), font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			String createdDate = Constants.DF_DMY.format(invoice.getCreatedDate());
			p = new Paragraph("Billing Date :" + createdDate, font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			if(null != invoice.getPaymentFrequency()){
			p = new Paragraph("Payment Terms :" + invoice.getPaymentFrequency(), font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			}
			String dueDate = Constants.DF_DMY.format(invoice.getDueDate());
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
			PdfPCell from = getAddress("Billing From:", user.getFullName(), user.getEmail(), user.getMobilePhone(), orgLocation.getOrg().getOrgName(), address.toString());
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
			PdfPCell to = getAddress("Billing To:", invoice.getContactUser().getFullName(), invoice.getContactUser().getEmail(), invoice.getContactUser().getMobilePhone(), orgRel.getResponderLocationId().getOrg().getOrgName(), clientAddress.toString());
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
				Employees employeeId=employeesService.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
				if(null != employeeId.getEmployeeId() && !"".equalsIgnoreCase(employeeId.getEmployeeId())){
					table.addCell(getCell(employeeId.getEmployeeId(), Element.ALIGN_CENTER, font10));
				}else{
					table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
				}
				StringBuffer name = new StringBuffer();
				if (null != invoiceLineItems.getCandidate()) {
					table.addCell(getCell(name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ").append(invoiceLineItems.getCandidate().getLastName()).toString(), Element.ALIGN_CENTER, font10));
				}
				else {
					table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER, font10));
				}
				StringBuffer startAndEndDate = new StringBuffer();
				if (null != invoiceLineItems.getStartDate()
						&& null != invoiceLineItems.getEndDate()) {
					String StartDate = Constants.DF_DMY.format(invoiceLineItems.getStartDate());
					String endDate = Constants.DF_DMY.format(invoiceLineItems.getEndDate());
					table.addCell(getCell(startAndEndDate.append(StartDate).append("to").append(endDate).toString(), Element.ALIGN_CENTER, font10));
				}
				else if (null != invoiceLineItems.getStartDate()) {
					String StartDate = Constants.DF_DMY.format(invoiceLineItems.getStartDate());
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
			
			p= new Paragraph("Grand Total: " + countryCurrency  +  invoice.getTotal(), font12b);
	          p.setAlignment(Element.ALIGN_RIGHT );
	          document.add(p);
			document.add(Chunk.NEWLINE);
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
				document = addLogoToPdfFile(document, org);
				Paragraph p;
				p = new Paragraph("Invoice Id :" + invoice.getInvoiceId(), font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				String createdDate = Constants.DF_DMY.format(invoice.getCreatedDate());
				p = new Paragraph("Invoice Date :" + createdDate, font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				if(null != invoice.getPaymentFrequency()){
				p = new Paragraph("Payment Terms :" + invoice.getPaymentFrequency(), font10);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				}
				String dueDate = Constants.DF_DMY.format(invoice.getDueDate());
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
				PdfPCell from = getAddress("Invoice From:", user.getFullName(), user.getEmail(), user.getMobilePhone(), orgLocation.getOrg().getOrgName(), address.toString());
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
				PdfPCell to = getAddress("Invoice To:", invoice.getContactUser().getFullName(), invoice.getContactUser().getEmail(), invoice.getContactUser().getMobilePhone(), orgRel.getResponderLocationId().getOrg().getOrgName(), clientAddress.toString());
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
					Employees employee=employeesService.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
					if(null != employee.getEmployeeId()  &&  !"".equalsIgnoreCase(employee.getEmployeeId())){
						table.addCell(getCell(employee.getEmployeeId(), Element.ALIGN_CENTER, font10));
					}else{
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
					StringBuffer name = new StringBuffer();
					if (null != invoiceLineItems.getCandidate()) {
						table.addCell(getCell(name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ").append(invoiceLineItems.getCandidate().getLastName()).toString(), Element.ALIGN_CENTER, font10));
					}
					else {
						table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER, font10));
					}
					StringBuffer startAndEndDate = new StringBuffer();
					if (null != invoiceLineItems.getStartDate()
							&& null != invoiceLineItems.getEndDate()) {
						String StartDate = Constants.DF_DMY.format(invoiceLineItems.getStartDate());
						String endDate = Constants.DF_DMY.format(invoiceLineItems.getEndDate());
						table.addCell(getCell(startAndEndDate.append(StartDate).append("to").append(endDate).toString(), Element.ALIGN_CENTER, font10));
					}
					else if (null != invoiceLineItems.getStartDate()) {
						String StartDate = Constants.DF_DMY.format(invoiceLineItems.getStartDate());
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
					table.addCell(getCell(" " + countryCurrency  +  invoice.getSubTotal(), Element.ALIGN_CENTER, font10b));
				
				document.add(table);
				
				document.add(getTotalsTable(invoice));
				document.add(discountTotaltable(invoice));
				
				p= new Paragraph("Grand Total: " + countryCurrency  +  invoice.getTotal(), font12b);
		          p.setAlignment(Element.ALIGN_RIGHT );
		          document.add(p);
				document.add(Chunk.NEWLINE);
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
	
	private Document addLogoToPdfFile(Document document, Org org) throws MalformedURLException, IOException, DocumentException
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
		p = new Paragraph("Org Name: " + org.getOrgName(), font12b);
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
		table.addCell(getCell(" " + invoice.getDiscountAmount(), Element.ALIGN_CENTER, font10));
		table.addCell(getCell(" " + invoice.getDiscountValue(), Element.ALIGN_CENTER, font12b));
		table.setSpacingAfter(20);
		}
			return table;
	}
}
