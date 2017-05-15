package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
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
	
	public File createPdfTableForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws IOException, Exception
	{
		
		File invoiceFile = new File(invoice.getInvoiceId() + ".pdf");
		OutputStream file = new FileOutputStream(invoiceFile);
		try {
			
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, file);
			writer.createXmpMetadata();
			document.open();
			// save image file in pdf
			document = addLogoToPdfFile(document, org);
			Paragraph p;
			p = new Paragraph("InvoiveID :" + invoice.getInvoiceId(), font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			String createdDate = Constants.DF_DMY.format(invoice.getCreatedDate());
			p = new Paragraph("Invoive Date :" + createdDate, font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			p = new Paragraph("Payment terms :" + invoice.getPaymentFrequency(), font10);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
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
			table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10);
			table.setSpacingAfter(10);
			table.setWidths(new int[] { 5, 6, 2, 4, 2 });
			table.addCell(getCell("Associate name", Element.ALIGN_LEFT, font12b));
			table.addCell(getCell("Duration", Element.ALIGN_LEFT, font12b));
			table.addCell(getCell("Rate", Element.ALIGN_LEFT, font12b));
			table.addCell(getCell("Total Hours", Element.ALIGN_LEFT, font12b));
			table.addCell(getCell("Total", Element.ALIGN_LEFT, font12b));
			Set<InvoiceLineItems> invoiveAssociateData = invoice.getInvoiceLineItems();
			for (InvoiceLineItems invoiceLineItems : invoiveAssociateData) {
				StringBuffer name = new StringBuffer();
				if (null != invoiceLineItems.getCandidate()) {
					table.addCell(getCell(name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ").append(invoiceLineItems.getCandidate().getLastName()).toString(), Element.ALIGN_LEFT, font10));
				}
				else {
					table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_LEFT, font10));
				}
				StringBuffer startAndEndDate = new StringBuffer();
				if (null != invoiceLineItems.getStartDate()
						&& null != invoiceLineItems.getEndDate()) {
					String StartDate = Constants.DF_DMY.format(invoiceLineItems.getStartDate());
					String endDate = Constants.DF_DMY.format(invoiceLineItems.getEndDate());
					table.addCell(getCell(startAndEndDate.append(StartDate).append("to").append(endDate).toString(), Element.ALIGN_RIGHT, font10));
				}
				else if (null != invoiceLineItems.getStartDate()) {
					String StartDate = Constants.DF_DMY.format(invoiceLineItems.getStartDate());
					table.addCell(getCell(startAndEndDate.append(StartDate).toString(), Element.ALIGN_RIGHT, font10));
				}
				else {
					table.addCell(getCell(startAndEndDate.append("--").toString(), Element.ALIGN_RIGHT, font10));
				}
				if (null != invoiceLineItems.getRate()
						&& !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
					table.addCell(getCell(invoiceLineItems.getRate(), Element.ALIGN_RIGHT, font10));
				}
				else {
					table.addCell(getCell("--", Element.ALIGN_RIGHT, font10));
				}
				if (null != invoiceLineItems.getTotalHours()
						&& !"".equalsIgnoreCase(invoiceLineItems.getTotalHours())) {
					table.addCell(getCell(invoiceLineItems.getTotalHours(), Element.ALIGN_RIGHT, font10));
				}
				else {
					table.addCell(getCell("--", Element.ALIGN_RIGHT, font10));
				}
				if (null != invoiceLineItems.getAmount()
						&& !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
					table.addCell(getCell(invoiceLineItems.getAmount(), Element.ALIGN_RIGHT, font10));
				}
				else {
					table.addCell(getCell("--", Element.ALIGN_RIGHT, font10));
				}
			}
			document.add(table);
			document.add(getTotalsTable(invoice));
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
		return invoiceFile;
	}
	
	private Document addLogoToPdfFile(Document document, Org org) throws MalformedURLException, IOException, DocumentException
	{
		if (null != org.getDocFile()) {
			Image image = Image.getInstance(org.getDocFile());
			image.scalePercent(300f);
			image.setAbsolutePosition(0, (float) (PageSize.A4.getHeight() - 25.0));
			System.out.println(image.getScaledHeight());
			image.setAbsolutePosition(100, 500);
			// Fixed Positioning
			image.setAbsolutePosition(40f, 780f);
			// Scale to new height and new width of image
			image.scaleAbsolute(100, 50);
			document.add(image);
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
	
	public PdfPTable getTotalsTable(Invoice invoice) throws DocumentException
	{
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1, 1, 3, 3, 3 });
		table.addCell(getCell("TAX", Element.ALIGN_LEFT, font12b));
		table.addCell(getCell("%", Element.ALIGN_RIGHT, font12b));
		table.addCell(getCell("Base amount:", Element.ALIGN_LEFT, font12b));
		table.addCell(getCell("Tax amount:", Element.ALIGN_LEFT, font12b));
		table.addCell(getCell("Currency Type", Element.ALIGN_LEFT, font12b));
		Set<InvoiceTax> invoiveTaxDetails = invoice.getInvoiceTax();
		for (InvoiceTax InvoiceTax : invoiveTaxDetails) {
			table.addCell(getCell(InvoiceTax.getTaxName(), Element.ALIGN_RIGHT, font10));
			table.addCell(getCell(InvoiceTax.getTaxType(), Element.ALIGN_RIGHT, font10));
			table.addCell(getCell(InvoiceTax.getTaxValue(), Element.ALIGN_RIGHT, font10));
			table.addCell(getCell(InvoiceTax.getTaxAmount(), Element.ALIGN_RIGHT, font10));
			table.addCell(getCell("currency", Element.ALIGN_LEFT, font10));
		}
		table = grandTotaltable(invoice);
		/*
		 * PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
		 * cell.setBorder(PdfPCell.NO_BORDER); table.addCell(cell);
		 * table.setWidths(new int[]{5,3});
		 * table.addCell(getCell("GrandTotal"+invoice.getTotal(),
		 * Element.ALIGN_LEFT, font12b)); table.addCell(getCell("Currency Type",
		 * Element.ALIGN_LEFT, font12b));
		 */
		return table;
	}
	
private PdfPTable grandTotaltable(Invoice invoice) throws DocumentException {
PdfPTable table = new PdfPTable(2);
PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
cell.setBorder(PdfPCell.NO_BORDER);
table.addCell(cell);
table.setWidthPercentage(100);
table.setWidths(new int[]{5, 3});
table.addCell(getCell("GrandTotal:"+invoice.getTotal(), Element.ALIGN_LEFT, font12b));
table.addCell(getCell("Currency Type", Element.ALIGN_LEFT, font12b));
table.getDefaultCell().setBorder(0);
return table;
}
}

