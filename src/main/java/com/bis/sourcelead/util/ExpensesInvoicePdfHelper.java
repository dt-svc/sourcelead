package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
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
public class ExpensesInvoicePdfHelper {
	

	private static final Logger logger = LoggerFactory.getLogger(InvoicePdfHelper.class);
	public static final String FONTB = "/fonts/OpenSans-Bold.ttf";
	public static final String FONT = "/fonts/OpenSans-Regular.ttf";
	protected Font font10;
	protected Font font10b;
	protected Font font12;
	protected Font font12b;
	protected Font font14;

	public ExpensesInvoicePdfHelper() throws DocumentException, IOException {
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
	
	@Autowired
	OrgLocationService orgLocationService;
	@Autowired
	UserDao userDao;

	public File createPdfTableForExpensesInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org,
			String invoiceType, String vendorCurrencyCode, String loginOrgCurrencyCode) throws IOException, Exception {
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		String countryCurrency = countries.getCurrency();

		File invoiceFile = new File(orgLocation.getOrgLocationName() + "_" + invoice.getInvoiceId() + ".pdf");
		if (Constants.PAYABLE.equalsIgnoreCase(invoiceType)) {
			Document document = new Document();
			invoiceFile.setReadable(true);
			FileOutputStream os = new FileOutputStream(invoiceFile);
			try {
				PdfWriter writer = PdfWriter.getInstance(document, os);
				document.open();
				// save image file in pdf
				document = addLogoToPdfFile(document, org, orgLocation);

				Chunk glue = new Chunk(new VerticalPositionMark());
				Paragraph p = new Paragraph(orgLocation.getOrgLocationName(), font14);
				p.add(new Chunk(glue));
				p.add("Bill");
				p.setFont(font14);
				p.setSpacingBefore(30f);
				document.add(p);

				StringBuilder address = new StringBuilder();
				if (null != orgLocation.getCity()) {
					address.append(orgLocation.getCountry()).append(orgLocation.getState())
							.append(orgLocation.getCity()).toString();
				} else if (null != orgLocation.getState()) {
					address.append(orgLocation.getCountry()).append(orgLocation.getState()).toString();
				} else {
					address.append(orgLocation.getCountry()).toString();
				}

				String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getCreatedDate());
				String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());

				p = new Paragraph(user.getFullName(), font10);
				p.add(new Chunk(glue));
				p.add("Billing Id :-" + invoice.getInvoiceId());
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(user.getEmail(), font10);
				p.add(new Chunk(glue));
				p.add("Billing Date :" + createdDate);
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(address.toString(), font10);
				p.add(new Chunk(glue));
				p.setFont(font10);
				p.add("Due Date :" + dueDate);
				document.add(p);

				p = new Paragraph(orgLocation.getPhone(), font10);
				p.add(new Chunk(glue));
				p.setFont(font10);
				p.add("Payment Terms-" + invoice.getPaymentFrequency());
				document.add(p);

				OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());

				StringBuilder clientAddress = new StringBuilder();
				if (null != orgRel.getResponderLocationId().getCity()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry())
							.append(orgRel.getResponderLocationId().getState())
							.append(orgRel.getResponderLocationId().getCity()).toString();
				} else if (null != orgRel.getResponderLocationId().getState()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry())
							.append(orgRel.getResponderLocationId().getState()).toString();
				} else {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).toString();
				}

				p = new Paragraph("To", font14);
				p.setSpacingBefore(15f);
				p.add(new Chunk(glue));
				p.setFont(font14);
				p.add("Bank Details");
				p.setSpacingAfter(8);
				document.add(p);

				PdfPTable tableq = new PdfPTable(2);
				tableq.setWidthPercentage(100);
				StringBuilder toAddress = new StringBuilder();
				User contactUser=null;
				if(invoice.getContactUsers() != null && !invoice.getContactUsers().isEmpty()) {
					String[] contactUsers = invoice.getContactUsers().split(",");
					for(int i=0; i < contactUsers.length; i++) {
						contactUser = userDao.getUserById(Long.parseLong(contactUsers[i]));
						toAddress.append(contactUser.getFullName());
						toAddress.append(System.getProperty("line.separator"));

						toAddress.append(contactUser.getEmail());
						toAddress.append(System.getProperty("line.separator"));
						if (contactUser.getMobilePhone() != null
								&& !"".equals(contactUser.getMobilePhone())) {
							toAddress.append(contactUser.getMobilePhone());
						}

					}
				}
				
				toAddress.append(orgRel.getResponderLocationId().getOrgLocationName());
				toAddress.append(System.getProperty("line.separator"));

				toAddress.append(clientAddress.toString());
				toAddress.append(System.getProperty("line.separator"));

				
				tableq.addCell(getCellValue(toAddress.toString(), PdfPCell.ALIGN_LEFT));
				if (invoice.getAccountDetails() != null && !"".equals(invoice.getAccountDetails())) {
					tableq.addCell(getCellValue(invoice.getAccountDetails(), PdfPCell.ALIGN_RIGHT));
				} else {
					tableq.addCell(getCellValue("--", PdfPCell.ALIGN_RIGHT));
				}

				document.add(tableq);

				PdfPTable table = new PdfPTable(2);
				table = new PdfPTable(7);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10);
				table.setSpacingAfter(10);
				table.setWidths(new int[] { 3, 3, 3, 5, 2, 3, 3 });
				table.addCell(getHeaderCell("Id", Element.ALIGN_CENTER, font12));
				table.addCell(getHeaderCell("Name", Element.ALIGN_CENTER, font12));
				table.addCell(getHeaderCell("Period", Element.ALIGN_CENTER, font12));
				table.addCell(getHeaderCell("Brief Description", Element.ALIGN_CENTER, font12));
				table.addCell(getHeaderCell("Qty", Element.ALIGN_CENTER, font12));
				table.addCell(getHeaderCell("Rate", Element.ALIGN_CENTER, font12));
				table.addCell(getHeaderCell("Total", Element.ALIGN_CENTER, font12));
				Set<InvoiceLineItems> invoiveAssociateData = invoice.getInvoiceLineItems();
				for (InvoiceLineItems invoiceLineItems : invoiveAssociateData) {
					if (invoiceLineItems.getCandidate() != null) {
						Employees employee = null;
						if (invoiceLineItems.getCandidate().getConsultantUser() != null) {
							employee = employeesService
									.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
						}
						if (employee != null) {
							if (null != employee.getEmployeeId() && !"".equalsIgnoreCase(employee.getEmployeeId())) {
								table.addCell(getCell(employee.getEmployeeId(), Element.ALIGN_CENTER, font10));
							} else {
								table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
							}
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					} else {
						if (invoiceLineItems.getUser() != null) {
							table.addCell(getCell(invoiceLineItems.getUser().getId().toString(), Element.ALIGN_CENTER,
									font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					}
					StringBuffer name = new StringBuffer();

					if (null != invoiceLineItems.getCandidate()) {
						if (invoiceLineItems.getCandidate().getFirstName() != null
								&& invoiceLineItems.getCandidate().getLastName() != null) {
							table.addCell(getCell(
									name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ")
											.append(invoiceLineItems.getCandidate().getLastName()).toString(),
									Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER,
									font10));
						}

					} else {
						if (null != invoiceLineItems.getUser()) {
							table.addCell(getCell(
									name.append(invoiceLineItems.getUser().getFirstName()).append(" ")
											.append(invoiceLineItems.getUser().getLastName()).toString(),
									Element.ALIGN_CENTER, font10));
						} else {
							if(invoiceLineItems.getUser() !=null) {
								table.addCell(getCell(invoiceLineItems.getUser().getFirstName(), Element.ALIGN_CENTER, font10));
							}else {
								table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
							}
							
						}
					}

					StringBuilder startAndEndDate = new StringBuilder();
					if (null != invoiceLineItems.getStartDate() && null != invoiceLineItems.getEndDate()) {
						String StartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getStartDate());
						String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getEndDate());
						startAndEndDate.append(StartDate).append(System.getProperty("line.separator")).append("to")
								.append(System.getProperty("line.separator")).append(endDate);
						table.addCell(getCell(startAndEndDate.toString(), Element.ALIGN_CENTER, font10));
					} else if (null != invoiceLineItems.getStartDate()) {
						String StartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getStartDate());
						table.addCell(
								getCell(startAndEndDate.append(StartDate).toString(), Element.ALIGN_CENTER, font10));
					} else {
						table.addCell(getCell(startAndEndDate.append("--").toString(), Element.ALIGN_CENTER, font10));
					}
					if (null != invoiceLineItems.getInlineDescription()
							&& !"".equalsIgnoreCase(invoiceLineItems.getInlineDescription())) {
						table.addCell(getCell(invoiceLineItems.getInlineDescription(), Element.ALIGN_CENTER, font10));
					} else {
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}

					if (null != invoiceLineItems.getTotalHours()
							&& !"".equalsIgnoreCase(invoiceLineItems.getTotalHours())) {
						table.addCell(getCell(invoiceLineItems.getTotalHours(), Element.ALIGN_CENTER, font10));
					} else {
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
					if (null != invoiceLineItems.getRate() && !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
						table.addCell(getCell(invoiceLineItems.getRate(), Element.ALIGN_CENTER, font10));
					} else {
						table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					}
					if (null != invoiceLineItems.getAmount() && !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
						table.addCell(getCell(invoiceLineItems.getAmount(), Element.ALIGN_CENTER, font10));
					} else {
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
				table.addCell(getCell("Sub Total", Element.ALIGN_CENTER, font10));
				table.addCell(getCell(countryCurrency + " " + invoice.getSubTotal(), Element.ALIGN_CENTER, font10));

				document.add(table);
				document.add(Chunk.NEWLINE);

				PdfPTable table2 = new PdfPTable(2);
				table2.setWidthPercentage(100);

				Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
				Paragraph paragraph2 = new Paragraph();
				paragraph2.setSpacingAfter(25);
				paragraph2.setSpacingBefore(25);
				paragraph2.setAlignment(Element.ALIGN_LEFT);
				paragraph2.setIndentationLeft(0);
				paragraph2.setIndentationRight(300);
				paragraph2.setFont(normalFont);

				if (null != invoice.getInvoiceComment()) {
					p = new Paragraph("Memo/Description", font14);
					p.setAlignment(Element.ALIGN_LEFT);
					p.setSpacingAfter(8);
					document.add(p);
					// document.add( Chunk.NEWLINE );
					paragraph2.add(invoice.getInvoiceComment());
					table2.addCell(getMemoCell(PdfPCell.ALIGN_LEFT, paragraph2));
				} else {
					paragraph2.add("");
					table2.addCell(getMemoCell(PdfPCell.ALIGN_LEFT, paragraph2));
				}

				StringBuilder grandTotal = new StringBuilder();

				if (invoice.getDiscountAmount() != null) {
					if (invoice.getDiscountType().equalsIgnoreCase(Constants.PERCENTAGE)) {
						grandTotal.append("Discount " + "(" + invoice.getDiscountAmount() + " %)" + " : "
								+ invoice.getDiscountValue());
						grandTotal.append(System.getProperty("line.separator"));
					} else {
						grandTotal.append("Discount " + "(" + invoice.getDiscountAmount() + " "
								+ invoice.getDiscountType() + ")" + " : " + invoice.getDiscountValue());
						grandTotal.append(System.getProperty("line.separator"));
					}
				}

				if (!invoice.getInvoiceTax().isEmpty()) {
					Set<InvoiceTax> invoiveTaxDetails = invoice.getInvoiceTax();
					for (InvoiceTax InvoiceTax : invoiveTaxDetails) {
						if (InvoiceTax.getTaxValue() != null) {
							if (InvoiceTax.getTaxName() != null) {
								if (InvoiceTax.getTaxType().equalsIgnoreCase(Constants.PERCENTAGE)) {
									grandTotal.append(InvoiceTax.getTaxName() + "(" + InvoiceTax.getTaxValue() + " %)"
											+ " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								} else {
									grandTotal.append(InvoiceTax.getTaxName() + "(" + InvoiceTax.getTaxValue() + " "
											+ InvoiceTax.getTaxType() + ")" + " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								}
							} else {
								if (InvoiceTax.getTaxType().equalsIgnoreCase(Constants.PERCENTAGE)) {
									grandTotal.append(
											"(" + InvoiceTax.getTaxValue() + " %)" + " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								} else {
									grandTotal.append("(" + InvoiceTax.getTaxValue() + " " + InvoiceTax.getTaxType()
											+ ")" + " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								}
							}
						}
					}
				}

				grandTotal.append("Grand Total :" + countryCurrency + "   " + invoice.getTotal());
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));

				if (!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)) {
					grandTotal.append("Conversion From :" + invoice.getCurrencyCode() + "   " + invoice.getTotal());
					grandTotal.append(System.getProperty("line.separator"));
					grandTotal.append(
							"Conversion To :" + invoice.getConvertCurrencyCode() + "   " + invoice.getConvertAmount());
					grandTotal.append(System.getProperty("line.separator"));
					grandTotal.append("Exchange Rate :" + invoice.getCurrencyCode() + " 1 = "
							+ invoice.getConvertCurrencyCode() + "   " + invoice.getExchangeRate());
					grandTotal.append(System.getProperty("line.separator"));
				}

				table2.addCell(getCell2(grandTotal, PdfPCell.ALIGN_RIGHT));
				document.add(table2);

				LineSeparator ls = new LineSeparator();
				ls.setAlignment(Element.ALIGN_BOTTOM);
				document.add(new Chunk(ls));
				Font GREEN_ITALIC = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.LIGHT_GRAY);
				p = new Paragraph("Thank you for your business!", GREEN_ITALIC);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);

				// file.close();
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
				os.flush();
				os.close();
			}

		} else {
			Document document = new Document();
			invoiceFile.setReadable(true);
			FileOutputStream os = new FileOutputStream(invoiceFile);
			try {
				PdfWriter writer = PdfWriter.getInstance(document, os);
				document.open();
				// save image file in pdf
				document = addLogoToPdfFile(document, org, orgLocation);

				Chunk glue = new Chunk(new VerticalPositionMark());
				Paragraph p = new Paragraph(orgLocation.getOrgLocationName(), font14);
				p.add(new Chunk(glue));
				p.add("Invoice");
				p.setFont(font14);
				p.setSpacingBefore(30f);
				document.add(p);

				StringBuilder address = new StringBuilder();
				if (null != orgLocation.getCity()) {
					address.append(orgLocation.getCountry()).append(orgLocation.getState())
							.append(orgLocation.getCity()).toString();
				} else if (null != orgLocation.getState()) {
					address.append(orgLocation.getCountry()).append(orgLocation.getState()).toString();
				} else {
					address.append(orgLocation.getCountry()).toString();
				}

				String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getCreatedDate());
				String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());

				p = new Paragraph(user.getFullName(), font10);
				p.add(new Chunk(glue));
				p.add("Invoice Id-" + invoice.getInvoiceId());
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(user.getEmail(), font10);
				p.add(new Chunk(glue));
				p.add("Invoice Date-" + createdDate);
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(address.toString(), font10);
				p.add(new Chunk(glue));
				p.setFont(font10);
				p.add("Due Date-" + dueDate);
				document.add(p);

				p = new Paragraph(orgLocation.getPhone(), font10);
				p.add(new Chunk(glue));
				p.setFont(font10);
				p.add("Payment Terms-" + invoice.getPaymentFrequency());
				document.add(p);

				OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());

				StringBuilder clientAddress = new StringBuilder();
				if (null != orgRel.getResponderLocationId().getCity()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry())
							.append(orgRel.getResponderLocationId().getState())
							.append(orgRel.getResponderLocationId().getCity()).toString();
				} else if (null != orgRel.getResponderLocationId().getState()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry())
							.append(orgRel.getResponderLocationId().getState()).toString();
				} else {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).toString();
				}

				p = new Paragraph("", font14);
				p.setSpacingBefore(15f);
				p.add(new Chunk(glue));
				p.setFont(font14);
				p.add("Bank Details");
				p.setSpacingAfter(8);
				document.add(p);
				PdfPTable tableq = new PdfPTable(2);
				tableq.setWidthPercentage(100);
				StringBuilder toAddress = new StringBuilder();
				User contactUser=null;
				if(invoice.getContactUsers() != null && !invoice.getContactUsers().isEmpty()) {
					String[] orgDepts = invoice.getContactUsers().split(",");
					for(int i=0; i < orgDepts.length; i++) {
						contactUser = userDao.getUserById(Long.parseLong(orgDepts[i]));
						toAddress.append(contactUser.getFullName());
						toAddress.append(System.getProperty("line.separator"));
						toAddress.append(contactUser.getEmail());
						toAddress.append(System.getProperty("line.separator"));
						if (contactUser.getMobilePhone() != null
								&& !"".equals(contactUser.getMobilePhone())) {
							toAddress.append(contactUser.getMobilePhone());
						}
					}
				}
			
				toAddress.append(orgRel.getResponderLocationId().getOrgLocationName());
				toAddress.append(System.getProperty("line.separator"));

				toAddress.append(clientAddress.toString());
				toAddress.append(System.getProperty("line.separator"));

			

				tableq.addCell(getCellValue(toAddress.toString(), PdfPCell.ALIGN_LEFT));
				if (invoice.getAccountDetails() != null && !"".equals(invoice.getAccountDetails())) {
					tableq.addCell(getCellValue(invoice.getAccountDetails(), PdfPCell.ALIGN_RIGHT));
				} else {
					tableq.addCell(getCellValue("--", PdfPCell.ALIGN_RIGHT));
				}

				document.add(tableq);
				PdfPTable table = new PdfPTable(2);
				if(invoice.getExpensesInvoices() !=null){
					table = new PdfPTable(4);
					table.setWidthPercentage(100);
					table.setSpacingBefore(10);
					table.setSpacingAfter(10);
					table.setWidths(new int[] { 3, 3, 3, 5 });
					table.addCell(getHeaderCell("Date", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Category", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Description/Price", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Amount", Element.ALIGN_CENTER, font12));
					Set<InvoiceLineItems> invoiveAssociateData = invoice.getInvoiceLineItems();
					for (InvoiceLineItems invoiceLineItems : invoiveAssociateData) {
						if (null != invoiceLineItems.getStartDate()) {
							String StartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getStartDate());
							table.addCell(getCell(StartDate, Element.ALIGN_CENTER, font10));
						} 
						if (null != invoiceLineItems.getExpenseCategory()
								&& !"".equalsIgnoreCase(invoiceLineItems.getExpenseCategory())) {
							table.addCell(getCell(invoiceLineItems.getExpenseCategory(), Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
						if (null != invoiceLineItems.getInlineDescription()
								&& !"".equalsIgnoreCase(invoiceLineItems.getInlineDescription())) {
							table.addCell(getCell(invoiceLineItems.getInlineDescription(), Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
						if (null != invoiceLineItems.getAmount()
								&& !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
							table.addCell(getCell(invoiceLineItems.getAmount(), Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
					  }
					}
				}else{
					table = new PdfPTable(5);
					table.setWidthPercentage(100);
					table.setSpacingBefore(10);
					table.setSpacingAfter(10);
					table.setWidths(new int[] { 3, 3, 3, 5, 2 });
					table.addCell(getHeaderCell("Date", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Description", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Quantity", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Rate/Price", Element.ALIGN_CENTER, font12));
					table.addCell(getHeaderCell("Total", Element.ALIGN_CENTER, font12));
					Set<InvoiceLineItems> invoiveAssociateData = invoice.getInvoiceLineItems();
					for (InvoiceLineItems invoiceLineItems : invoiveAssociateData) {
						if (null != invoiceLineItems.getStartDate()) {
							String StartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getStartDate());
							table.addCell(getCell(StartDate, Element.ALIGN_CENTER, font10));
						}  else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
						if (null != invoiceLineItems.getInlineDescription()
								&& !"".equalsIgnoreCase(invoiceLineItems.getInlineDescription())) {
							table.addCell(getCell(invoiceLineItems.getInlineDescription(), Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
						if (null != invoiceLineItems.getQuantity()
								&& !"".equalsIgnoreCase(invoiceLineItems.getQuantity())) {
							table.addCell(getCell(invoiceLineItems.getQuantity(), Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					
						if (null != invoiceLineItems.getRate() && !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
							table.addCell(getCell(invoiceLineItems.getRate(), Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
						if (null != invoiceLineItems.getAmount()
								&& !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
							table.addCell(getCell(invoiceLineItems.getAmount(), Element.ALIGN_CENTER, font10));
						} else {
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
				//table.addCell(cell);
				table.addCell(getCell("Sub Total", Element.ALIGN_CENTER, font10));
				table.addCell(getCell(countryCurrency + " " + invoice.getSubTotal(), Element.ALIGN_CENTER, font10));

				document.add(table);
				document.add(Chunk.NEWLINE);

				PdfPTable table2 = new PdfPTable(2);
				table2.setWidthPercentage(100);

				Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
				Paragraph paragraph2 = new Paragraph();
				paragraph2.setSpacingAfter(25);
				paragraph2.setSpacingBefore(25);
				paragraph2.setAlignment(Element.ALIGN_LEFT);
				paragraph2.setIndentationLeft(0);
				paragraph2.setIndentationRight(300);
				paragraph2.setFont(normalFont);

				if (null != invoice.getInvoiceComment()) {
					p = new Paragraph("Memo/Description", font14);
					p.setAlignment(Element.ALIGN_LEFT);
					p.setSpacingAfter(8);
					document.add(p);
					// document.add( Chunk.NEWLINE );
					paragraph2.add(invoice.getInvoiceComment());
					table2.addCell(getMemoCell(PdfPCell.ALIGN_LEFT, paragraph2));
				} else {
					paragraph2.add("");
					table2.addCell(getMemoCell(PdfPCell.ALIGN_LEFT, paragraph2));
				}

				StringBuilder grandTotal = new StringBuilder();

				if (invoice.getDiscountAmount() != null) {
					if (invoice.getDiscountType().equalsIgnoreCase(Constants.PERCENTAGE)) {
						grandTotal.append("Discount " + "(" + invoice.getDiscountAmount() + " %)" + " : "
								+ invoice.getDiscountValue());
						grandTotal.append(System.getProperty("line.separator"));
					} else {
						grandTotal.append("Discount " + "(" + invoice.getDiscountAmount() + " "
								+ invoice.getDiscountType() + ")" + " : " + invoice.getDiscountValue());
						grandTotal.append(System.getProperty("line.separator"));
					}
				}

				if (!invoice.getInvoiceTax().isEmpty()) {
					Set<InvoiceTax> invoiveTaxDetails = invoice.getInvoiceTax();
					for (InvoiceTax InvoiceTax : invoiveTaxDetails) {
						if (InvoiceTax.getTaxValue() != null) {
							if (InvoiceTax.getTaxName() != null) {
								if (InvoiceTax.getTaxType().equalsIgnoreCase(Constants.PERCENTAGE)) {
									grandTotal.append(InvoiceTax.getTaxName() + "(" + InvoiceTax.getTaxValue() + " %)"
											+ " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								} else {
									grandTotal.append(InvoiceTax.getTaxName() + "(" + InvoiceTax.getTaxValue() + " "
											+ InvoiceTax.getTaxType() + ")" + " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								}
							} else {
								if (InvoiceTax.getTaxType().equalsIgnoreCase(Constants.PERCENTAGE)) {
									grandTotal.append(
											"(" + InvoiceTax.getTaxValue() + " %)" + " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								} else {
									grandTotal.append("(" + InvoiceTax.getTaxValue() + " " + InvoiceTax.getTaxType()
											+ ")" + " : " + InvoiceTax.getTaxAmount());
									grandTotal.append(System.getProperty("line.separator"));
								}
							}
						}
					}
				}

				grandTotal.append("Grand Total :" + countryCurrency + "   " + invoice.getTotal());
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));

				if (!loginOrgCurrencyCode.equalsIgnoreCase(vendorCurrencyCode)) {
					grandTotal.append("Conversion From :" + invoice.getCurrencyCode() + "   " + invoice.getTotal());
					grandTotal.append(System.getProperty("line.separator"));
					grandTotal.append(
							"Conversion To :" + invoice.getConvertCurrencyCode() + "   " + invoice.getConvertAmount());
					grandTotal.append(System.getProperty("line.separator"));
					grandTotal.append("Exchange Rate :" + invoice.getCurrencyCode() + " 1 = "
							+ invoice.getConvertCurrencyCode() + "   " + invoice.getExchangeRate());
					grandTotal.append(System.getProperty("line.separator"));
				}

				table2.addCell(getCell2(grandTotal, PdfPCell.ALIGN_RIGHT));
				document.add(table2);

				LineSeparator ls = new LineSeparator();
				ls.setAlignment(Element.ALIGN_BOTTOM);
				document.add(new Chunk(ls));
				Font GREEN_ITALIC = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.LIGHT_GRAY);
				p = new Paragraph("Thank you for your business!", GREEN_ITALIC);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);

				}	// file.close();
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
				os.flush();
				os.close();
			}

		}
		return invoiceFile;
	}

	private PdfPCell getCellValue(String text, int alignment) {
		Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
		PdfPCell cell = new PdfPCell(new Phrase(text, normalFont));
		cell.setPadding(0);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	private Document addLogoToPdfFile(Document document, Org org, OrgLocations orgLocations)
			throws MalformedURLException, IOException, DocumentException {
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
		} else {
			Paragraph p;
			p = new Paragraph(" " + orgLocations.getOrgLocationName(), font12b);
			p.setAlignment(Element.ALIGN_TOP);
			document.add(p);
		}
		return document;
	}

	private PdfPCell getCell(String value, int alignLeft, Font font) {

		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseAscender(true);
		Paragraph p = new Paragraph(value, font);
		p.setAlignment(alignLeft);
		cell.addElement(p);
		return cell;
	}

	private PdfPCell getHeaderCell(String value, int alignLeft, Font font) {

		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseAscender(true);
		Paragraph p = new Paragraph(value, font);
		p.setAlignment(alignLeft);
		cell.addElement(p);
		cell.setBackgroundColor(new BaseColor(30, 144, 255));
		return cell;
	}

	public PdfPTable getTotalsTable(Invoice invoice) throws Exception {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 4, 3, 3, 3 });
		if (!invoice.getInvoiceTax().isEmpty()) {
			table.addCell(getCell("TAX", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Type", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Base Amount", Element.ALIGN_CENTER, font12b));
			table.addCell(getCell("Tax Amount", Element.ALIGN_CENTER, font12b));
			// table.addCell(getCell("Currency Type", Element.ALIGN_LEFT,
			// font12b));
			Set<InvoiceTax> invoiveTaxDetails = invoice.getInvoiceTax();
			for (InvoiceTax InvoiceTax : invoiveTaxDetails) {
				table.addCell(getCell(InvoiceTax.getTaxName(), Element.ALIGN_CENTER, font10));
				table.addCell(getCell(InvoiceTax.getTaxType(), Element.ALIGN_CENTER, font10));
				table.addCell(getCell(InvoiceTax.getTaxValue(), Element.ALIGN_CENTER, font10));
				table.addCell(getCell(InvoiceTax.getTaxAmount(), Element.ALIGN_CENTER, font10));
				// table.addCell(getCell("currency", Element.ALIGN_LEFT,
				// font10));
			}
			table.setSpacingAfter(20);
		}
		return table;
	}

	public File getBillingPdf(java.util.List<Invoice> invoiceList, String orgName, InvoiceAmount totalStagesAmount,
			String exportStartDate, String exportEndDate, String invoiceType,OrgLocations locations) throws Exception {
		
		 OrgLocations orglo = orgLocationService.getOrgLocations(locations.getId());

		String[] headers = null;
		if (Constants.Payables.equalsIgnoreCase(invoiceType)) {
			headers = Constants.BILLING_PAYABLE_HEADERS;
		} else {
			headers = Constants.BILLING_HEADERS;
		}
		Document document = new Document(PageSize.LETTER.rotate());
	//	DateFormat dateFormat = LoginSessionController.DATEFORMAT;

		StringBuilder sbReq = new StringBuilder();
		sbReq.append(orgName).append(Constants.BILLING).append(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),new Date()));

		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(
				fileSB.append(orgName).append(Constants.BILLING).append(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),new Date())).toString(),
				".pdf");

		file.setReadable(true);
		try {
			// Get an instance of PdfWriter and create a Table.pdf file as an
			// output.
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

			BaseColor myBordColor = WebColors.getRGBColor("#000000");
			boldFont.setColor(myBordColor);

			StringBuilder orgNameSB = new StringBuilder();
			Paragraph para = new Paragraph(orgNameSB.append(orgName).append(" ").append(Constants.BILLING).toString(),
					boldFont);

			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);

			Chunk glue = new Chunk(new VerticalPositionMark());
			Paragraph p = new Paragraph("Company Name :" + orgName);
			p.add(new Chunk(glue));
			p.setIndentationLeft(0);
			p.add("Duration  :" + exportStartDate + " - " + exportEndDate);
			p.setIndentationRight(223);
			document.add(p);

			Chunk glue1 = new Chunk(new VerticalPositionMark());
			Paragraph p1 = new Paragraph("Total Unpaid Amount(Open) :" + totalStagesAmount.getTotalOpenUnpaidAmount());
			p1.add(new Chunk(glue1));
			p1.setIndentationLeft(0);
			p1.add("Total Unpaid Amount(OverDue) :" + totalStagesAmount.getTotalOverDueUnpaidAmount());
			p1.setIndentationRight(220);
			document.add(p1);

			Chunk glue2 = new Chunk(new VerticalPositionMark());
			Paragraph p2 = new Paragraph("Total Paid Amount :" + totalStagesAmount.getTotalPaidAmount());
			p2.add(new Chunk(glue2));
			p2.setIndentationLeft(0);
			p2.add("Total Unbilled Amount  :" + totalStagesAmount.getTotalUnbilledAmount());
			p2.setIndentationRight(227);
			document.add(p2);

			PdfPTable table = new PdfPTable(headers.length);
			Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
			PdfPCell cell1 = null;
			if (Constants.Payables.equalsIgnoreCase(invoiceType)) {
				cell1 = new PdfPCell(new Phrase(Constants.BILLING_LIST, font));
			} else {
				cell1 = new PdfPCell(new Phrase(Constants.INVOICE_LIST, font));
			}
			cell1.setColspan(13);
			headerCellStyle(cell1);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 5, 3, 5, 5, 3, 5, 3, 5, 5, 3, 3, 5 });
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
			for (Invoice invoice : invoiceList) {
				invoices.add(prepareBillingToArray(invoice));
			}

			for (String[] invoiceArray : invoices) {
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
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		} catch (FileNotFoundException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		} finally {
			document.close();
		}
		return file;
	}

	private static void headerCellStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		cell.setBackgroundColor(new BaseColor(0, 121, 182));
		cell.setBorder(0);
		cell.setBorderWidthBottom(2f);
	}

	private String[] prepareBillingToArray(Invoice invoice) throws Exception {

		String billingArray[] = new String[Constants.BILLING_HEADERS.length];
		
		 OrgLocations orglo = orgLocationService.getOrgLocations(invoice.getOrgLocation().getId());

		if (null != invoice.getInvoiceId() && !"".equalsIgnoreCase(invoice.getInvoiceId())) {
			billingArray[0] = invoice.getInvoiceId();
		} else {
			billingArray[0] = "--";
		}

		OrgLocations orgLocations = null;
		if (invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == invoice.getOrgLocation().getId()
				.longValue()) {
			orgLocations = invoice.getOrgRelationID().getLocationId();
		} else if (invoice.getOrgRelationID().getLocationId().getId().longValue() == invoice.getOrgLocation().getId()
				.longValue()) {
			orgLocations = invoice.getOrgRelationID().getResponderLocationId();
		}
		if (null != orgLocations) {
			billingArray[1] = orgLocations.getOrgLocationName();
		} else {
			billingArray[1] = "--";
		}

		if (null != invoice.getContactUser()) {
			if (null != invoice.getContactUser().getLastName()
					&& !"".equalsIgnoreCase(invoice.getContactUser().getLastName())) {
				StringBuilder contactName = new StringBuilder();
				billingArray[2] = contactName.append(invoice.getContactUser().getFirstName()).append(" ")
						.append(invoice.getContactUser().getLastName()).toString();
			} else if (null != invoice.getContactUser().getFirstName()) {
				billingArray[2] = invoice.getContactUser().getFirstName();
			} else {
				billingArray[2] = "--";
			}
		} else {
			billingArray[2] = "--";
		}

		if (null != invoice.getContactUser()) {
			if (null != invoice.getContactUser().getEmail()
					&& !"".equalsIgnoreCase(invoice.getContactUser().getEmail())) {
				billingArray[3] = invoice.getContactUser().getEmail();
			} else {
				billingArray[3] = "--";
			}
		} else {
			billingArray[3] = "--";
		}

		if (null != invoice.getContactUser()) {
			if (invoice.getContactUser().getMobilePhone() != null
					&& !"".equalsIgnoreCase(invoice.getContactUser().getMobilePhone())) {
				billingArray[4] = invoice.getContactUser().getMobilePhone();
			} else {
				billingArray[4] = "--";
			}
		} else {
			billingArray[4] = "--";
		}

		if (null != invoice.getInvoiceDate()) {
			String invoiceDate = commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),invoice.getInvoiceDate());
			billingArray[5] = invoiceDate;
		} else {
			billingArray[5] = "--";
		}

		if (null != invoice.getDueDate()) {
			String dueDate = commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),invoice.getDueDate());
			billingArray[6] = dueDate;
		} else {
			billingArray[6] = "--";
		}

		if (null != invoice.getTotal() && !"".equalsIgnoreCase(invoice.getTotal())) {
			billingArray[7] = invoice.getTotal();
		} else {
			billingArray[7] = "--";
		}

		if (null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount())) {
			billingArray[8] = invoice.getDueAmount();
		} else {
			billingArray[8] = "--";
		}

		if (null != invoice.getDueAmount() && !"".equalsIgnoreCase(invoice.getDueAmount())) {
			Double paidAmount = Double.parseDouble(invoice.getTotal()) - Double.parseDouble(invoice.getDueAmount());
			String convertedPaidAmount = String.valueOf(paidAmount);
			billingArray[9] = convertedPaidAmount;
		} else {
			billingArray[9] = "--";
		}

		if (null != invoice.getInvoiceStatus() && !"".equalsIgnoreCase(invoice.getInvoiceStatus())) {
			billingArray[10] = invoice.getInvoiceStatus();
		} else {
			billingArray[10] = "--";
		}

		if (null != invoice.getPaymentStatus() && !"".equalsIgnoreCase(invoice.getPaymentStatus())) {
			billingArray[11] = invoice.getPaymentStatus();
		} else {
			billingArray[11] = "--";
		}

		if (null != invoice.getLinkInvoiceReferenceId()
				&& !"".equalsIgnoreCase(String.valueOf(invoice.getLinkInvoiceReferenceId()))) {
			billingArray[12] = String.valueOf(invoice.getLinkInvoiceReferenceId());
		} else {
			billingArray[12] = "--";
		}

		return billingArray;

	}

	public PdfPCell getMemoCell(int alignment, Paragraph paragraph2) {
		PdfPCell cell = new PdfPCell(new Phrase(paragraph2));
		cell.setPadding(0);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public PdfPCell getCell2(StringBuilder sb, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(sb.toString()));
		cell.setPadding(0);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}


}
