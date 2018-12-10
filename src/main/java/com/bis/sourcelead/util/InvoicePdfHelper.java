package com.bis.sourcelead.util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.cnd;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.org.eclipse.jdt.internal.compiler.ast.ConditionalExpression;
import org.dbunit.ant.Export;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.builder.ConditionExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.InvoiceTableItemsDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.CustomInvoiceTemplate;
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
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.dynamicReport.Templates;
import com.bis.sourcelead.web.LoginSessionController;
import com.google.gson.JsonObject;
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

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListCellBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.PenBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.LineStyle;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.jasperreports.export.Exporter;

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
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private UserDao userDao;

	OrgLocationService orgLocationService;
	
	private HorizontalListBuilder[] initializeHorizontalList() {
		HorizontalListBuilder[] horizontalListBuilderArray = new HorizontalListBuilder[14];
		
		HorizontalListBuilder horizontalListBuilder1 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder2 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder3 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder4 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder5 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder6 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder7 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder8 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder9 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder10 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder11 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder12 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder13 = cmp.horizontalList();
		HorizontalListBuilder horizontalListBuilder14 = cmp.horizontalList();
		
		horizontalListBuilderArray[0] = horizontalListBuilder1;
		horizontalListBuilderArray[1] = horizontalListBuilder2;
		horizontalListBuilderArray[2] = horizontalListBuilder3;
		horizontalListBuilderArray[3] = horizontalListBuilder4;
		horizontalListBuilderArray[4] = horizontalListBuilder5;
		horizontalListBuilderArray[5] = horizontalListBuilder6;
		horizontalListBuilderArray[6] = horizontalListBuilder7;
		horizontalListBuilderArray[7] = horizontalListBuilder8;
		horizontalListBuilderArray[8] = horizontalListBuilder9;
		horizontalListBuilderArray[9] = horizontalListBuilder10;
		horizontalListBuilderArray[10] = horizontalListBuilder11;
		horizontalListBuilderArray[11] = horizontalListBuilder12;
		horizontalListBuilderArray[12] = horizontalListBuilder13;
		horizontalListBuilderArray[13] = horizontalListBuilder14;
		
		return horizontalListBuilderArray;
	}
	
	private void initializeReport(JasperReportBuilder report, int tableIndex,JasperReportBuilder tableReport, HorizontalListBuilder[] horizontalListBuilderArray) {
		StyleBuilder columnStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
		try {
			switch (tableIndex) {
			case 0:
				report
				.setColumnStyle(columnStyle)
				.title(cmp.subreport(tableReport),
						horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
			
			case 1:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
			
			case 2:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 3:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 4:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 5:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 6:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 7:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 8:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 9:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;

			case 10:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], 
						cmp.subreport(tableReport),
						horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 11:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], 
						cmp.subreport(tableReport),
						horizontalListBuilderArray[11],
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 12:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[12], horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
				
			case 13:
				report
				.setColumnStyle(columnStyle)
				.title(horizontalListBuilderArray[0], horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10], horizontalListBuilderArray[11],
						horizontalListBuilderArray[12],
						cmp.subreport(tableReport),
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				report.show();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File createCustomInvoicePDF(Invoice invoice, OrgLocations orgLocation, User user, Org org,
			String vendorCurrencyCode, String loginOrgCurrencyCode, CustomInvoiceTemplate customInvoiceTemplate) {
		try {
			File invoiceFile = new File(orgLocation.getOrgLocationName() + "_" + invoice.getInvoiceId() + new Date().getTime()+ ".pdf");
			if (Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType())) {
				invoiceFile.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(invoiceFile);
				try {
					StringBuilder address = new StringBuilder();
					if (null != orgLocation.getCity()) {
						address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(",")
								.append(orgLocation.getCity()).toString();
					} else if (null != orgLocation.getState()) {
						address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).toString();
					} else {
						address.append(orgLocation.getCountry()).toString();
					}
					
					JasperReportBuilder report = report();
					JasperReportBuilder tableReport = report();
					
					HorizontalListBuilder[] horizontalListBuilderArray = initializeHorizontalList();
					int tableIndex = 0;
					JSONArray jsonArray = new JSONArray(customInvoiceTemplate.getItems());
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0, index = 0; i < jsonArray.length(); i = i+2, index++) {
							boolean tableFlag1 = false, tableFlag2 = false, footerFlag1 = false, footerFlag2 = false;
							boolean empty1 = false, empty2 = false;
							boolean cellFlag1 = false, cellFlag2 = false;
							HorizontalListCellBuilder cellBuilder1 = null;
							HorizontalListCellBuilder cellBuilder2 = null;
							if(i < jsonArray.length()) {
								JSONObject jsonObject1 = jsonArray.getJSONObject(i);
								if(jsonObject1 != null && jsonObject1.has("name")) {
									String type = jsonObject1.getString("type");
									switch(type) {
										case "image": 
											cellBuilder1 = getLogoDataFromJSON(jsonObject1); 
											break;
										case "table" : 
											tableReport = getTableDataFromJSON(jsonObject1, invoice.getOrgLocation(), invoice.getInvoiceLineItems()); 
											tableFlag1 = true;
											break;
										case "text" : 
											cellBuilder1 = getTextDataFromJSON(jsonObject1, invoice.getOrgLocation(), user, invoice, false);
											break;
										case "footer" : 
											cellBuilder1 = getFooterDataFromJSON(jsonObject1, invoice.getOrgLocation()); 
											footerFlag1 = true;
											break;
										default : 
											cellBuilder1 = getEmptyDataFromJSON();
											break;
									}
								} else {
									cellBuilder1 = getEmptyDataFromJSON();
									empty1 = true;
								}
								cellFlag1 = true;
							}
							
							if(i+1 < jsonArray.length()) {
								JSONObject jsonObject2 = jsonArray.getJSONObject(i+1);
								if(jsonObject2 != null && jsonObject2.has("name")) {
									String type = jsonObject2.getString("type");
									switch(type) {
										case "image": 
											cellBuilder2 = getLogoDataFromJSON(jsonObject2); 
											break;
										case "table" : 
											tableReport = getTableDataFromJSON(jsonObject2, invoice.getOrgLocation(), invoice.getInvoiceLineItems()); 
											tableFlag2 = true;
											break;
										case "text" : 
											cellBuilder2 = getTextDataFromJSON(jsonObject2, invoice.getOrgLocation(), user, invoice, false);
											break;
										case "footer" : 
											cellBuilder2 = getFooterDataFromJSON(jsonObject2, invoice.getOrgLocation());
											footerFlag2 = true;
											break;
										default : 
											cellBuilder2 = getEmptyDataFromJSON();
											break;
									}
								} else {
									cellBuilder2 = getEmptyDataFromJSON();
									empty2 = true;
								}
								cellFlag2 = true;
							}
							
							if(cellFlag1 && cellFlag2 && cellBuilder1 != null && cellBuilder2 != null) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10)).setGap(50)
											.add(cellBuilder1.heightFixedOnTop())
											.add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
							} else if (cellFlag1 && cellBuilder1 != null) {
								cellBuilder2 = getEmptyDataFromJSON();
								if (tableFlag2) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);
									index++;
									tableIndex = index;
								} else if (footerFlag1) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);
								} else {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder2.heightFixedOnTop())
											.add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);

								}
							} else if (cellFlag2 && cellBuilder2 != null) {
								cellBuilder1 = getEmptyDataFromJSON();
								if (tableFlag1) {
									tableIndex = index;
									if (empty1) {
										horizontalListBuilderArray[index].add(cmp.horizontalList()
												.setStyle(stl.style(10)).setGap(50).add(cellBuilder2.heightFixedOnTop())
												.add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);
										i++;
									} else
										i--;
								} else if (footerFlag2) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
								} else {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder1.heightFixedOnTop())
											.add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
								}
							}
						}
					}
					initializeReport(report, tableIndex, tableReport, horizontalListBuilderArray);
					report.toPdf(pdfExporter);
				} catch (Exception e) {
					logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
				}
			} else {
				invoiceFile.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(invoiceFile);
				try {
					StringBuilder address = new StringBuilder();
					if (null != orgLocation.getCity()) {
						address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(",")
								.append(orgLocation.getCity()).toString();
					} else if (null != orgLocation.getState()) {
						address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).toString();
					} else {
						address.append(orgLocation.getCountry()).toString();
					}
					
					JasperReportBuilder report = report();
					JasperReportBuilder tableReport = report();
					
					HorizontalListBuilder[] horizontalListBuilderArray = initializeHorizontalList();
					int tableIndex = 0;
					JSONArray jsonArray = new JSONArray(customInvoiceTemplate.getItems());
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0, index = 0; i < jsonArray.length(); i = i+2, index++) {
							boolean tableFlag1 = false, tableFlag2 = false, footerFlag1 = false, footerFlag2 = false;
							boolean empty1 = false, empty2 = false;
							boolean cellFlag1 = false, cellFlag2 = false;
							HorizontalListCellBuilder cellBuilder1 = null;
							HorizontalListCellBuilder cellBuilder2 = null;
							if(i < jsonArray.length()) {
								JSONObject jsonObject1 = jsonArray.getJSONObject(i);
								if(jsonObject1 != null && jsonObject1.has("name")) {
									String type = jsonObject1.getString("type");
									switch(type) {
										case "image": 
											cellBuilder1 = getLogoDataFromJSON(jsonObject1); 
											break;
										case "table" : 
											tableReport = getTableDataFromJSON(jsonObject1, invoice.getOrgLocation(), invoice.getInvoiceLineItems()); 
											tableFlag1 = true;
											break;
										case "text" : 
											cellBuilder1 = getTextDataFromJSON(jsonObject1, invoice.getOrgLocation(), user, invoice, false);
											break;
										case "footer" : 
											cellBuilder1 = getFooterDataFromJSON(jsonObject1, invoice.getOrgLocation()); 
											footerFlag1 = true;
											break;
										default : 
											cellBuilder1 = getEmptyDataFromJSON();
											break;
									}
								} else {
									cellBuilder1 = getEmptyDataFromJSON();
									empty1 = true;
								}
								cellFlag1 = true;
							}
							
							if(i+1 < jsonArray.length()) {
								JSONObject jsonObject2 = jsonArray.getJSONObject(i+1);
								if(jsonObject2 != null && jsonObject2.has("name")) {
									String type = jsonObject2.getString("type");
									switch(type) {
										case "image": 
											cellBuilder2 = getLogoDataFromJSON(jsonObject2); 
											break;
										case "table" : 
											tableReport = getTableDataFromJSON(jsonObject2, invoice.getOrgLocation(), invoice.getInvoiceLineItems()); 
											tableFlag2 = true;
											break;
										case "text" : 
											cellBuilder2 = getTextDataFromJSON(jsonObject2, invoice.getOrgLocation(), user, invoice, false);
											break;
										case "footer" : 
											cellBuilder2 = getFooterDataFromJSON(jsonObject2, invoice.getOrgLocation());
											footerFlag2 = true;
											break;
										default : 
											cellBuilder2 = getEmptyDataFromJSON();
											break;
									}
								} else {
									cellBuilder2 = getEmptyDataFromJSON();
									empty2 = true;
								}
								cellFlag2 = true;
							}
							
							if(cellFlag1 && cellFlag2 && cellBuilder1 != null && cellBuilder2 != null) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10)).setGap(50)
											.add(cellBuilder1.heightFixedOnTop())
											.add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
							} else if (cellFlag1 && cellBuilder1 != null) {
								cellBuilder2 = getEmptyDataFromJSON();
								if (tableFlag2) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);
									index++;
									tableIndex = index;
								} else if (footerFlag1) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);
								} else {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder2.heightFixedOnTop())
											.add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);

								}
							} else if (cellFlag2 && cellBuilder2 != null) {
								cellBuilder1 = getEmptyDataFromJSON();
								if (tableFlag1) {
									tableIndex = index;
									if (empty1) {
										horizontalListBuilderArray[index].add(cmp.horizontalList()
												.setStyle(stl.style(10)).setGap(50).add(cellBuilder2.heightFixedOnTop())
												.add(cellBuilder1.heightFixedOnTop())).setFixedWidth(560);
										i++;
									} else
										i--;
								} else if (footerFlag2) {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
								} else {
									horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
											.setGap(50).add(cellBuilder1.heightFixedOnTop())
											.add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
								}
							}
						}
					}
					initializeReport(report, tableIndex, tableReport, horizontalListBuilderArray);
					report.toPdf(pdfExporter);
				} catch (Exception e) {
					logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
				}
			}
			return invoiceFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getDiscountAndTotalAmountForInvoice(Invoice invoice) {
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
		return grandTotal.toString();
	}
	
	private HorizontalListCellBuilder getLogoDataFromJSON(JSONObject jsonObject) throws JSONException {
		HorizontalListCellBuilder cellBuilder = null;
		JSONArray subItemsArrayJSON = jsonObject.getJSONArray("subItems");
		if (subItemsArrayJSON != null && subItemsArrayJSON.length() > 0) {
			for (int i = 0; i < subItemsArrayJSON.length(); i++) {
				JSONObject subItemObject = subItemsArrayJSON.getJSONObject(i);
				if(subItemObject.getBoolean("checked")) {
					HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
					int width = 140;
					String styleValue = subItemObject.getString("style");
					for(String allStyles : styleValue.split(";")) {
						String style[] = allStyles.split(":");
						if(style.length > 0) {
							for(int j = 0; j < style.length; j++) {
								if(style[0].equalsIgnoreCase("width")) {
									switch (style[1].trim()) {
									case "25%":
										width = 1 * 35;
										break;
									case "50%":
										width = 2 * 35;
										break;
									case "75%":
										width = 3 * 35;
										break;
									case "100%":
										width = 4 * 35;
										break;
									default:
										width = 2 * 35;
										break;
									}
								}
								if(style[0].equals("float")) {
									switch (style[1]) {
									case "left":
										horizontalAlignment = HorizontalAlignment.LEFT;
										break;
									case "right":
										horizontalAlignment = HorizontalAlignment.RIGHT;
										break;
									default:
										horizontalAlignment = HorizontalAlignment.CENTER;
										break;
									}
								}
							}
						}
					}
					StyleBuilder columnStyle = stl.style(Templates.columnStyle);
					cellBuilder = cmp.hListCell(
							cmp.image("http://localhost:8080" + subItemObject.getString("value")).setStyle(columnStyle)
								.setHorizontalAlignment(horizontalAlignment)
								.setFixedDimension(width, width/2)
								.setFixedWidth(245)
							).heightFixedOnTop();
				}
			}
		}
		return cellBuilder;
	}
	
	private JasperReportBuilder getTableDataFromJSON(JSONObject jsonObject, OrgLocations orgLocation, Set<InvoiceLineItems> invoiceLineItemsList) {
		try {
			List<InvoiceTableItemsDTO> tableItemsList = new LinkedList<>();
			if(invoiceLineItemsList != null && !invoiceLineItemsList.isEmpty()) {
				for(InvoiceLineItems invoiceLineItems : invoiceLineItemsList) {
					InvoiceTableItemsDTO invoiceTableItemsDTO = new InvoiceTableItemsDTO();
					//ID
					if(invoiceLineItems.getCandidate() != null) {
						Employees employee = null;
						if (invoiceLineItems.getCandidate().getConsultantUser() != null) {
							employee = employeesService.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
						}
						invoiceTableItemsDTO.setId(employee != null ? employee.getEmployeeId() : "----");
					} else {
						invoiceTableItemsDTO.setId(invoiceLineItems.getCandidate() != null ? invoiceLineItems.getCandidate().getId().toString() : "----");
					}
					
					//NAME
					if (invoiceLineItems.getCandidate() != null) {
						invoiceTableItemsDTO.setName(invoiceLineItems.getCandidate().getFirstName() + " " + (invoiceLineItems.getCandidate().getLastName() != null ? invoiceLineItems.getCandidate().getLastName() : ""));
					} else {
						if (invoiceLineItems.getCandidate() != null) {
							invoiceTableItemsDTO.setName(invoiceLineItems.getCandidate().getFirstName() + " " + invoiceLineItems.getCandidate().getLastName());
						} else {
							invoiceTableItemsDTO.setName(invoiceLineItems.getCandidate().getFirstName()); 
						}
					}
					
					//PERIOD
					if (null != invoiceLineItems.getStartDate() && null != invoiceLineItems.getEndDate()) {
						String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getStartDate());
						String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getEndDate());

						invoiceTableItemsDTO.setPeriod(startDate + System.getProperty("line.separator") + " to " + System.getProperty("line.separator") + endDate);
					} else if (null != invoiceLineItems.getStartDate()) {
						String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoiceLineItems.getStartDate());
						invoiceTableItemsDTO.setPeriod(startDate);
					} else {
						invoiceTableItemsDTO.setPeriod("----");
					}
					
					//BRIEF DESCRIPTION
					if (null != invoiceLineItems.getInlineDescription() && !"".equalsIgnoreCase(invoiceLineItems.getInlineDescription())) {
						invoiceTableItemsDTO.setDescription(invoiceLineItems.getInlineDescription());
					} else {
						invoiceTableItemsDTO.setDescription("----");
					}
					
					//QTY / HOURS
					if (null != invoiceLineItems.getTotalHours() && !"".equalsIgnoreCase(invoiceLineItems.getTotalHours())) {
						invoiceTableItemsDTO.setQty(invoiceLineItems.getTotalHours());
					} else {
						invoiceTableItemsDTO.setQty("----");
					}
					
					//RATE
					if (null != invoiceLineItems.getRate() && !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
						invoiceTableItemsDTO.setRate(invoiceLineItems.getRate());
					} else {
						invoiceTableItemsDTO.setRate("----");
					}
					
					//TOTAL
					if (null != invoiceLineItems.getAmount() && !"".equalsIgnoreCase(invoiceLineItems.getAmount())) {
						invoiceTableItemsDTO.setTotal(invoiceLineItems.getAmount());
					} else {
						invoiceTableItemsDTO.setTotal("----");
					}
					tableItemsList.add(invoiceTableItemsDTO);
				}
			}
			
			String thStyle = "";
			String tdStyle = "";
			
			JSONArray subItemsArrayJSON = jsonObject.getJSONArray("subItems");
			for (int i = 0; i < subItemsArrayJSON.length(); i++) {
				JSONObject subItemObject = subItemsArrayJSON.getJSONObject(i);
				switch (subItemObject.getString("value")) {
				case "th":
					thStyle = subItemObject.getString("style");
					break;
				case "td":
					tdStyle = subItemObject.getString("style");
					break;
				default:
					break;
				}
			}
			return createTableItem(tableItemsList, thStyle, tdStyle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private JasperReportBuilder createTableItem(List<InvoiceTableItemsDTO> tableItemsList, String thStyleString, String tdStyleString) {
		TextColumnBuilder<String> idColumn = col.column("Id", "id", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType());
		TextColumnBuilder<String> periodColumn = col.column("Period", "period", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> descriptionColumn = col.column("Brief Description", "description", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> rateColumn = col.column("Rate", "rate", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> totalColumn = col.column("Total", "total", type.stringType()).setFixedWidth(80);
		StyleBuilder tableHeadStyle = stl.style(Templates.columnStyle);
		StyleBuilder tableRowStyle = stl.style(Templates.columnStyle);
		
		if(thStyleString != null && !thStyleString.isEmpty()) {
			for(String allStyles : thStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if(style.length > 1) {
					switch (style[0]) {
					case "border":
						if(!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");
							
							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);
							
							tableHeadStyle.setBorder(pen);
							/*tableHeadStyle.setBorder(stl.pen1Point());*/
						}
						break;
					case "font-size":
						tableHeadStyle.setFontSize(Integer.parseInt(style[1].split("px")[0]));
						break;
					
					case "background-color":
						tableHeadStyle.setBackgroundColor(Color.decode(style[1].replace("!important", "")));
						break;
						
					case "color":
						tableHeadStyle.setForegroundColor(Color.decode(style[1].replace("!important", "")));
						break;
					default:
						break;
					}
				}
			}
		} else {
			PenBuilder pen = stl.pen();
			pen.setLineColor(Color.decode("#989898"));
			pen.setLineStyle(LineStyle.SOLID);
			pen.setLineWidth(1f);
			
			tableHeadStyle.setBorder(pen);
			tableHeadStyle.setFontSize(10);
			tableHeadStyle.setBackgroundColor(Color.decode("#6C7686"));
			tableHeadStyle.setForegroundColor(Color.decode("#FFFFFF"));
			tableHeadStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		}
		
		if(tdStyleString != null && !tdStyleString.isEmpty()) {
			for(String allStyles : tdStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if(style.length > 1) {
					switch (style[0]) {
					case "border":
						if(!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");
							
							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);
							
							tableRowStyle.setBorder(pen);
							/*tableHeadStyle.setBorder(stl.pen1Point());*/
						}
//						tableRowStyle.setBorder(stl.pen1Point());
						break;
					case "font-size":
						tableRowStyle.setFontSize(Integer.parseInt(style[1].split("px")[0]));
						break;
					
					case "background-color":
						tableRowStyle.setBackgroundColor(Color.decode(style[1].replace("!important", "")));
						break;
						
					case "color":
						tableRowStyle.setForegroundColor(Color.decode(style[1].replace("!important", "")));
						break;
					default:
						break;
					}
				}
			}
		} else {
			PenBuilder pen = stl.pen();
			pen.setLineColor(Color.decode("#989898"));
			pen.setLineStyle(LineStyle.SOLID);
			pen.setLineWidth(1f);
			
			tableRowStyle.setBorder(pen);
			tableRowStyle.setFontSize(8);
			tableRowStyle.setBackgroundColor(Color.decode("#EAE6E6"));
			tableRowStyle.setForegroundColor(Color.decode("#333"));
			tableRowStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		}
		
		JasperReportBuilder tableReport = report();		
		tableReport
		.setColumnStyle(tableRowStyle)
		.setColumnTitleStyle(tableHeadStyle)
		.columns(idColumn, nameColumn, periodColumn, descriptionColumn, qtyColumn, rateColumn, totalColumn)
		.columnGrid(idColumn, nameColumn, periodColumn, descriptionColumn, qtyColumn, rateColumn, totalColumn)
		.setDataSource(tableItemsList);
		
		return tableReport;
	}

	private HorizontalListCellBuilder getFooterDataFromJSON(JSONObject jsonObject, OrgLocations orgLocations) {
		HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(stl.style());
		HorizontalListCellBuilder cellBuilder = null;
		try {
			String itemTitle = jsonObject.getString("name");
			JSONArray subItemsArrayJSON = jsonObject.getJSONArray("subItems");
			if (subItemsArrayJSON != null && subItemsArrayJSON.length() > 0) {
				String[] valuesArray = new String[subItemsArrayJSON.length()];
				String[] stylesArray = new String[subItemsArrayJSON.length()];
				for (int i = 0; i < subItemsArrayJSON.length(); i++) {
					JSONObject subItemObject = subItemsArrayJSON.getJSONObject(i);
					if(subItemObject.getBoolean("checked")) {
						String subItemTitle = subItemObject.getString("value");
						stylesArray[i] = subItemObject.getString("style");
						switch (subItemTitle) {
						case "Org Name":
							valuesArray[i] = orgLocations.getOrgLocationName();
							break;
						case "Address":
							valuesArray[i] = orgLocations.getLocation();
							break;
						case "ZipCode":
							valuesArray[i] = orgLocations.getZipCode();
							break;
						}
					}
				}
				HorizontalAlignment textAlign = HorizontalAlignment.LEFT;
				cellBuilder = cmp.hListCell(createSectionItem(textAlign, itemTitle, valuesArray, stylesArray, true)).heightFixedOnTop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellBuilder;
	}
	
	private HorizontalListCellBuilder getTextDataFromJSON(JSONObject jsonObject, OrgLocations orgLocation, User user, Invoice invoice, boolean topBorderFlag) throws Exception {
		HorizontalListCellBuilder cellBuilder = null;
		try {
			String itemTitle = jsonObject.getString("name");
			int position = jsonObject.getInt("position");
			JSONArray subItemsArrayJSON = jsonObject.getJSONArray("subItems");
			HorizontalAlignment textAlign = (position % 2 == 0 ? HorizontalAlignment.RIGHT : HorizontalAlignment.LEFT);
			if (subItemsArrayJSON != null && subItemsArrayJSON.length() > 0) {
				String[] valuesArray = new String[subItemsArrayJSON.length() + 1];
				String[] stylesArray = new String[subItemsArrayJSON.length() + 1];
				for (int i = 0; i < subItemsArrayJSON.length(); i++) {
					JSONObject subItemObject = subItemsArrayJSON.getJSONObject(i);
					if(subItemObject.getBoolean("checked")) {
						String subItemTitle = subItemObject.getString("value");
						
						if (itemTitle.equalsIgnoreCase("Invoice Details") || itemTitle.equalsIgnoreCase("To") || itemTitle.equalsIgnoreCase("Banking Details") || itemTitle.equalsIgnoreCase("Memo / Description")) {
							if (i == 0) {
								if(itemTitle.equalsIgnoreCase("Invoice Details")) {
									valuesArray[0] = Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType()) ? "Bill" : "Invoice";
								} else {
									valuesArray[0] = itemTitle;
								}
								
								stylesArray[0] = "font-family:Times New Roman;font-weight:400;font-size:16px;color:#545454;";
								stylesArray[0] += (position % 2 == 0) ? "text-align:right;" : "text-align:left;"; 
							}
							stylesArray[i + 1] = subItemObject.getString("style");
						} else {
							stylesArray[i] = subItemObject.getString("style");
						}
						
						switch (itemTitle) {
						//Section - Organisation Name
						case "Organisation Name":
							valuesArray[i] = orgLocation.getOrgLocationName();
							break;
						//Section - Client Details
						case "Client Details":
							switch (subItemTitle) {
							case "Organisation Name":
								valuesArray[i] = orgLocation.getOrgLocationName();
								break;

							case "Sender Name":
								valuesArray[i] = user.getFullName();
								break;

							case "Sender Email Id":
								valuesArray[i] = user.getEmail();
								break;

							case "Address":
								StringBuilder address = new StringBuilder();
								if (null != orgLocation.getCity()) {
									address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(",")
											.append(orgLocation.getCity()).toString();
								} else if (null != orgLocation.getState()) {
									address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).toString();
								} else {
									address.append(orgLocation.getCountry()).toString();
								}
								valuesArray[i] = address.toString();
								break;

							default:
								break;
							}
							break;
						//Section - Invoice Details
						case "Invoice Details":
							switch (subItemTitle) {
							case "Invoice Id":
								valuesArray[i + 1] = (Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType()) ? "Billing Id : " : "Invoice Id : ") + invoice.getInvoiceId();
								break;
								
							case "Invoice Date":
								String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getCreatedDate());
								valuesArray[i + 1] = (Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType()) ? "Billing Date : " : "Invoice Date : ") + createdDate;
								break;
								
							case "Due Date":
								String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());
								valuesArray[i + 1] = "Due Date : " + dueDate;
								break;
								
							case "Payment Terms":
								valuesArray[i + 1] = "Payment Terms : " + (invoice.getPaymentFrequency() != null ? invoice.getPaymentFrequency() : "----");
								break;

							default:
								break;
							}
							break;
						//Section - To
						case "To":
							User contactUser = userService.getUserById(Long.parseLong(invoice.getContactUsers().split(",")[0]));
							OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());
							
							StringBuilder clientAddress = new StringBuilder();
							if (null != orgRel.getResponderLocationId().getCity()) {
								clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(",")
										.append(orgRel.getResponderLocationId().getState()).append(",")
										.append(orgRel.getResponderLocationId().getCity()).toString();
							} else if (null != orgRel.getResponderLocationId().getState()) {
								clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(",")
										.append(orgRel.getResponderLocationId().getState()).toString();
							} else {
								clientAddress.append(orgRel.getResponderLocationId().getCountry()).toString();
							}
							
							switch (subItemTitle) {
							case "Recipient Name":
								valuesArray[i + 1] = contactUser.getFullName();
								break;

							case "Recipient Email Id":
								valuesArray[i + 1] = contactUser.getEmail();
								break;
							case "Client Name":
								valuesArray[i + 1] = orgRel.getResponderLocationId().getOrgLocationName();
								break;
							case "Address":
								valuesArray[i + 1] = clientAddress.toString();
								break;
							case "Mobile Number":
								valuesArray[i + 1] = contactUser.getMobilePhone();
							default:
								break;
							}
							break;
						//Section - Banking Details
						case "Banking Details":
							if(invoice.getPaymentAccount() != null) {
								PaymentAccountDTO paymentAccount  = orgService.getPaymentAccount(invoice.getPaymentAccount().getId(), null);
								if(paymentAccount.getAccountType().equalsIgnoreCase("WEPAY")) {
									switch (subItemTitle) {
									case "Account Type":
										valuesArray[i + 1] = "Account Type : " + paymentAccount.getAccountType();
										break;
									case "Email":
										valuesArray[i + 1] = "Email : "+ paymentAccount.getEmail();
										break;
									case "First Name":
										valuesArray[i + 1] = "First Name : " + paymentAccount.getFirstName();
										break;
									case "Last Name":
										valuesArray[i + 1] = "Last Name : " + paymentAccount.getLastName();
										break;
									default:
										break;
									}
								} else {
									switch (subItemTitle) {
									case "Account Type":
										valuesArray[i + 1] = "Account Type : " + paymentAccount.getAccountType();
										break;
									case "Bank Name":
										valuesArray[i + 1] = "Bank Name : " + paymentAccount.getBankName();
										break;
									case "Account No":
										valuesArray[i + 1] = "Account Number : " + paymentAccount.getAccountNumber();
										break;
									case "IFSC Code":
										valuesArray[i + 1] = "IFSC Code : " + paymentAccount.getRoutingNumber();
										break;
									case "First Name":
										valuesArray[i + 1] = "First Name : " + paymentAccount.getFirstName();
										break;
									case "Last Name":
										valuesArray[i + 1] = "Last Name : " + paymentAccount.getLastName();
										break;
									default:
										break;
									}
								}
							} else {
								valuesArray[i] = "";
							}
							break;
						//Section - Memo / Description
						case "Memo / Description":
							if(invoice.getInvoiceComment() != null && !invoice.getInvoiceComment().isEmpty()) {
								valuesArray[i + 1] = invoice.getInvoiceComment() != null ? invoice.getInvoiceComment() : "";
							} else {
								valuesArray[0] = "";
								stylesArray[0] = "";
							}
						//Section - Discount & Total
						case "Total":
							switch (subItemTitle) {
							case "Discount":
								valuesArray[i] = getDiscountAndTotalAmountForInvoice(invoice);
								break;
							case "Grand Total":
								Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
								String countryCurrency = countries.getCurrency();
								valuesArray[i] = "Grand Total : " +countryCurrency + " " + invoice.getTotal();
								break;	
							default:
								break;
							}
						default:
							break;
						}
					}
				}
				
				cellBuilder = cmp.hListCell(createSectionItem(textAlign, itemTitle, valuesArray, stylesArray, topBorderFlag)).heightFixedOnTop();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cellBuilder;
	}
	
	private HorizontalListCellBuilder getEmptyDataFromJSON() {
		HorizontalListCellBuilder cellBuilder = null;
		try {
			cellBuilder = cmp.hListCell(createSectionItem(HorizontalAlignment.LEFT, "", null, null, false)).widthFixed().heightFixedOnTop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellBuilder;
	}
	
	private ComponentBuilder<?, ?> createSectionItem(HorizontalAlignment horizontalAlignment, String label, String[] args, String[] styles, boolean addTopBorder) {
		HorizontalListBuilder list = null;
		if (addTopBorder)
			list = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()));
		else
			list = cmp.horizontalList().setBaseStyle(stl.style());
		
		if(args != null) {
			for (int i = 0; i < args.length; i++) {
				StyleBuilder styleBuilder = stl.style();
				styleBuilder.setFontSize(12);
				if(styles[i] != null) {
					for(String allStyles : styles[i].split(";")) {
						String style[] = allStyles.split(":");
						if(style.length > 1) {
							switch (style[0]) {
								case "font-family":
									try {
										styleBuilder.setFontName(style[1]);
									} catch (Exception e) {
										System.out.println("Font Exception : " + style[1]);
										styleBuilder.setFontName("Arial");
									}
									break;
									
								case "font-weight":
									switch (style[1]) {
										case "400":
											break;
										case "600":
											styleBuilder.bold();
											break;
										case "700":
											styleBuilder.bold();
											break;
										default:
											break;
										}
									break;
									
								case "font-style":
									switch(style[1]) {
										case "italic":
											styleBuilder.italic();
											break;
									}
									break;
									
								case "font-size":
									styleBuilder.setFontSize(Integer.parseInt(style[1].split("px")[0]) - 2);
									break;
									
								case "text-align":
									styleBuilder.setHorizontalAlignment(style[1].trim().equalsIgnoreCase("left") ? HorizontalAlignment.LEFT : style[1].trim().equalsIgnoreCase("right") ? HorizontalAlignment.RIGHT : HorizontalAlignment.CENTER);
									break;
									
								case "color":
									if(!style[1].equalsIgnoreCase("transparent")) {
										styleBuilder.setForegroundColor(Color.decode(style[1]));
									}
									break;
									
								case "background-color":
									if(!style[1].equalsIgnoreCase("transparent")) {
										styleBuilder.setBackgroundColor(Color.decode(style[1]));
									}
									break;
								
								case "margin-top":
									break;
									
								case "margin-right":
									break;
									
								case "margin-bottom":
									break;
									
								case "margin-left":
									break;
									
								case "padding-top":
									styleBuilder.setTopPadding(Integer.parseInt(style[1].split("px")[0]));
									break;
									
								case "padding-right":
									styleBuilder.setRightPadding(Integer.parseInt(style[1].split("px")[0]));
									break;
									
								case "padding-bottom":
									styleBuilder.setBottomPadding(Integer.parseInt(style[1].split("px")[0]));
									break;
									
								case "padding-left":
									styleBuilder.setLeftPadding(Integer.parseInt(style[1].split("px")[0]));
									break;
							}
						}
					}
				}
				if (args[i] != null) {
					list.add(cmp.text(args[i]).setStyle(styleBuilder)).newRow();
				}
			}
		} else {
			StyleBuilder styleBuilder = stl.style();
			list.add(cmp.text("").setStyle(styleBuilder).setFixedWidth(245)).newRow();
		}
		return cmp.verticalList(list);
	}
	
	public File createPdfTableForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org,
			String invoiceType, String vendorCurrencyCode, String loginOrgCurrencyCode) throws IOException, Exception {
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		String countryCurrency = countries.getCurrency();
		/*SimpleDateFormat simpleDateFormat = orgLocation.getLocFullFormDateFormatter();*/
		File invoiceFile = new File(orgLocation.getOrgLocationName() + "_" + invoice.getInvoiceId() + new Date().getTime()+ ".pdf");
		User contactUser = userService.getUserById(Long.parseLong(invoice.getContactUsers().split(",")[0]));
		if (Constants.PAYABLE.equalsIgnoreCase(invoiceType)) {
			Document document = new Document();
			invoiceFile.setReadable(true);
			FileOutputStream os = new FileOutputStream(invoiceFile);
			try {
				PdfWriter writer = PdfWriter.getInstance(document, os);
				document.open();
				// save image file in pdf
				document = addLogoToPdfFile(document, org, orgLocation, null);

				Chunk glue = new Chunk(new VerticalPositionMark());
				Paragraph p = new Paragraph(orgLocation.getOrgLocationName(), font14);
				p.add(new Chunk(glue));
				p.add("Bill");
				p.setFont(font14);
				p.setSpacingBefore(30f);
				document.add(p);

				StringBuilder address = new StringBuilder();
				if (null != orgLocation.getCity()) {
					address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(",")
							.append(orgLocation.getCity()).toString();
				} else if (null != orgLocation.getState()) {
					address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).toString();
				} else {
					address.append(orgLocation.getCountry()).toString();
				}

				String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getCreatedDate());
				String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());

				p = new Paragraph(user.getFullName(), font10);
				p.add(new Chunk(glue));
				p.add("Billing Id : " + invoice.getInvoiceId());
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(user.getEmail(), font10);
				p.add(new Chunk(glue));
				p.add("Billing Date : " + createdDate);
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(address.toString(), font10);
				p.add(new Chunk(glue));
				p.setFont(font10);
				p.add("Due Date : " + dueDate);
				document.add(p);

				p = new Paragraph(orgLocation.getPhone(), font10);
				p.add(new Chunk(glue));
				p.setFont(font10);
				if(StringUtils.isNotBlank(invoice.getPaymentFrequency()))
				p.add("Payment Terms : " + invoice.getPaymentFrequency());
				else
					p.add("Payment Terms : " + "--");
				document.add(p);

				OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());

				StringBuilder clientAddress = new StringBuilder();
				if (null != orgRel.getResponderLocationId().getCity()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(",")
							.append(orgRel.getResponderLocationId().getState()).append(",")
							.append(orgRel.getResponderLocationId().getCity()).toString();
				} else if (null != orgRel.getResponderLocationId().getState()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(",")
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
				
				if(invoice.getPaymentAccount() != null) {
					PaymentAccountDTO paymentAccount  = orgService.getPaymentAccount(invoice.getPaymentAccount().getId(), null);
					if(paymentAccount.getAccountType().equalsIgnoreCase("WEPAY")) {
						p = new Paragraph(contactUser.getFullName(), font10);
						p.add(new Chunk(glue));
						p.add("Account Type : " + paymentAccount.getAccountType());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getEmail(), font10);
						p.add(new Chunk(glue));
						p.add("Email : " + paymentAccount.getEmail());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(orgRel.getResponderLocationId().getOrgLocationName(), font10);
						p.add(new Chunk(glue));
						p.add("First Name : " + paymentAccount.getFirstName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(clientAddress.toString(), font10);
						p.add(new Chunk(glue));
						p.add("Last Name : " + paymentAccount.getLastName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getMobilePhone(), font10);
						document.add(p);
						
					}else {

						p = new Paragraph(contactUser.getFullName(), font10);
						p.add(new Chunk(glue));
						p.add("Account Type : " + paymentAccount.getAccountType());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getEmail(), font10);
						p.add(new Chunk(glue));
						p.add("Bank Name : " + paymentAccount.getBankName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(orgRel.getResponderLocationId().getOrgLocationName(), font10);
						p.add(new Chunk(glue));
						p.add("Account Number : " + paymentAccount.getAccountNumber());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(clientAddress.toString(), font10);
						p.add(new Chunk(glue));
						p.add("IFSC Code : " + paymentAccount.getRoutingNumber());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getMobilePhone(), font10);
						p.add(new Chunk(glue));
						p.add("First Name : " + paymentAccount.getFirstName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph("", font10);
						p.add(new Chunk(glue));
						p.add("Last Name : " + paymentAccount.getLastName());
						p.setFont(font10);
						document.add(p);
						
					}
				}else {

					PdfPTable tableq = new PdfPTable(2);
					tableq.setWidthPercentage(100);
					StringBuilder toAddress = new StringBuilder();

					toAddress.append(contactUser.getFullName());
					toAddress.append(System.getProperty("line.separator"));

					toAddress.append(contactUser.getEmail());
					toAddress.append(System.getProperty("line.separator"));

					toAddress.append(orgRel.getResponderLocationId().getOrgLocationName());
					toAddress.append(System.getProperty("line.separator"));

					toAddress.append(clientAddress.toString());
					toAddress.append(System.getProperty("line.separator"));

					if (contactUser.getMobilePhone() != null
							&& !"".equals(contactUser.getMobilePhone())) {
						toAddress.append(contactUser.getMobilePhone());
					}

					tableq.addCell(getCellValue(toAddress.toString(), PdfPCell.ALIGN_LEFT));
					tableq.addCell(getCellValue("", PdfPCell.ALIGN_RIGHT));

					document.add(tableq);
				}

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
						if (invoiceLineItems.getCandidate() != null) {
							table.addCell(getCell(invoiceLineItems.getCandidate().getId().toString(), Element.ALIGN_CENTER,
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
						if (null != invoiceLineItems.getCandidate()) {
							table.addCell(getCell(
									name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ")
											.append(invoiceLineItems.getCandidate().getLastName()).toString(),
									Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(
									getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER, font10));
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
				
				
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));
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
				document = addLogoToPdfFile(document, org, orgLocation, null);

				Chunk glue = new Chunk(new VerticalPositionMark());
				Paragraph p = new Paragraph(orgLocation.getOrgLocationName(), font14);
				p.add(new Chunk(glue));
				p.add("Invoice");
				p.setFont(font14);
				p.setSpacingBefore(30f);
				document.add(p);

				StringBuilder address = new StringBuilder();
				if (null != orgLocation.getCity()) {
					address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(",")
							.append(orgLocation.getCity()).toString();
				} else if (null != orgLocation.getState()) {
					address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).toString();
				} else {
					address.append(orgLocation.getCountry()).toString();
				}

				String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getCreatedDate());
				String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());

				p = new Paragraph(user.getFullName(), font10);
				p.add(new Chunk(glue));
				p.add("Invoice Id : " + invoice.getInvoiceId());
				p.setFont(font10);
				document.add(p);

				p = new Paragraph(user.getEmail(), font10);
				p.add(new Chunk(glue));
				p.add("Invoice Date : " + createdDate);
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
				if(StringUtils.isNotBlank(invoice.getPaymentFrequency()))
					p.add("Payment Terms : " + invoice.getPaymentFrequency());
					else
						p.add("Payment Terms : " + "--");
				document.add(p);

				OrgRelations orgRel = orgRelationsService.getOrRelationsById(invoice.getOrgRelationID().getId());

				StringBuilder clientAddress = new StringBuilder();
				if (null != orgRel.getResponderLocationId().getCity()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(",")
							.append(orgRel.getResponderLocationId().getState()).append(",")
							.append(orgRel.getResponderLocationId().getCity()).toString();
				} else if (null != orgRel.getResponderLocationId().getState()) {
					clientAddress.append(orgRel.getResponderLocationId().getCountry()).append(",")
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
				
				if(invoice.getPaymentAccount() != null) {
					PaymentAccountDTO paymentAccount  = orgService.getPaymentAccount(invoice.getPaymentAccount().getId(), null);
					if(paymentAccount.getAccountType().equalsIgnoreCase("WEPAY")) {
						p = new Paragraph(contactUser.getFullName(), font10);
						p.add(new Chunk(glue));
						p.add("Account Type : " + paymentAccount.getAccountType());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getEmail(), font10);
						p.add(new Chunk(glue));
						p.add("Email : " + paymentAccount.getEmail());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(orgRel.getResponderLocationId().getOrgLocationName(), font10);
						p.add(new Chunk(glue));
						p.add("First Name : " + paymentAccount.getFirstName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(clientAddress.toString(), font10);
						p.add(new Chunk(glue));
						p.add("Last Name : " + paymentAccount.getLastName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getMobilePhone(), font10);
						document.add(p);
						
					}else {

						p = new Paragraph(contactUser.getFullName(), font10);
						p.add(new Chunk(glue));
						p.add("Account Type : " + paymentAccount.getAccountType());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getEmail(), font10);
						p.add(new Chunk(glue));
						p.add("Bank Name : " + paymentAccount.getBankName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(orgRel.getResponderLocationId().getOrgLocationName(), font10);
						p.add(new Chunk(glue));
						p.add("Account Number : " + paymentAccount.getAccountNumber());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(clientAddress.toString(), font10);
						p.add(new Chunk(glue));
						p.add("IFSC Code : " + paymentAccount.getRoutingNumber());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph(contactUser.getMobilePhone(), font10);
						p.add(new Chunk(glue));
						p.add("First Name : " + paymentAccount.getFirstName());
						p.setFont(font10);
						document.add(p);
						
						p = new Paragraph("", font10);
						p.add(new Chunk(glue));
						p.add("Last Name : " + paymentAccount.getLastName());
						p.setFont(font10);
						document.add(p);
						
					}
				}else {

					PdfPTable tableq = new PdfPTable(2);
					tableq.setWidthPercentage(100);
					StringBuilder toAddress = new StringBuilder();

					toAddress.append(contactUser.getFullName());
					toAddress.append(System.getProperty("line.separator"));

					toAddress.append(contactUser.getEmail());
					toAddress.append(System.getProperty("line.separator"));

					toAddress.append(orgRel.getResponderLocationId().getOrgLocationName());
					toAddress.append(System.getProperty("line.separator"));

					toAddress.append(clientAddress.toString());
					toAddress.append(System.getProperty("line.separator"));

					if (contactUser.getMobilePhone() != null
							&& !"".equals(contactUser.getMobilePhone())) {
						toAddress.append(contactUser.getMobilePhone());
					}

					tableq.addCell(getCellValue(toAddress.toString(), PdfPCell.ALIGN_LEFT));
					tableq.addCell(getCellValue("", PdfPCell.ALIGN_RIGHT));

					document.add(tableq);
				}
				
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
						if (invoiceLineItems.getCandidate() != null) {
							table.addCell(getCell(invoiceLineItems.getCandidate().getId().toString(), Element.ALIGN_CENTER,
									font10));
						} else {
							table.addCell(getCell("--", Element.ALIGN_CENTER, font10));
						}
					}
					StringBuffer name = new StringBuffer();

					if (null != invoiceLineItems.getCandidate()) {
						if (invoiceLineItems.getCandidate().getLastName() != null) {
							table.addCell(getCell(
									name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ")
											.append(invoiceLineItems.getCandidate().getLastName()).toString(),
									Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER,
									font10));
						}

					} else {
						if (null != invoiceLineItems.getCandidate()) {
							table.addCell(getCell(
									name.append(invoiceLineItems.getCandidate().getFirstName()).append(" ")
											.append(invoiceLineItems.getCandidate().getLastName()).toString(),
									Element.ALIGN_CENTER, font10));
						} else {
							table.addCell(
									getCell(invoiceLineItems.getCandidate().getFirstName(), Element.ALIGN_CENTER, font10));
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

				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));
				grandTotal.append(System.getProperty("line.separator"));
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

	private Document addLogoToPdfFile(Document document, Org org, OrgLocations orgLocations, String imageUrl)
			throws MalformedURLException, IOException, DocumentException {
		if(imageUrl != null && !imageUrl.isEmpty()) {
			
		} else {
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
		}
		return document;
	}

	private PdfPCell getCell(String value, int alignLeft, Font font) {

		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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

	public File getBillingPdf(ResponseList<Invoice> invoiceList, String orgName, InvoiceAmount totalStagesAmount,
			String exportStartDate, String exportEndDate, String invoiceType,OrgLocations locations) throws Exception {
		
//		 OrgLocations orglo = orgLocationService.getOrgLocations(locations.getId());

		String[] headers = null;
		if (Constants.Payables.equalsIgnoreCase(invoiceType)) {
			headers = Constants.BILLING_PAYABLE_HEADERS;
		} else {
			headers = Constants.BILLING_HEADERS;
		}
		Document document = new Document(PageSize.LETTER.rotate());
		//DateFormat dateFormat = LoginSessionController.DATEFORMAT;

		StringBuilder sbReq = new StringBuilder();
		sbReq.append(orgName).append(Constants.BILLING).append(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),new Date()));

		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(
				fileSB.append(orgName).append(Constants.BILLING).append(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),new Date())).toString(),
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
			p.setIndentationRight(3);

			document.add(p);

			Chunk glue1 = new Chunk(new VerticalPositionMark());
			Paragraph p1 = new Paragraph("Total Unpaid Amount(Open) :" + totalStagesAmount.getTotalOpenUnpaidAmount());
			p1.add(new Chunk(glue1));
			p1.setIndentationLeft(0);
			p1.add("Total Unpaid Amount(OverDue) :" + totalStagesAmount.getTotalOverDueUnpaidAmount());
			p1.setIndentationRight(0);
			document.add(p1);
			
			Chunk glue2 = new Chunk(new VerticalPositionMark());
			Paragraph p2 = new Paragraph("Total Paid Amount :" + totalStagesAmount.getTotalPaidAmount());
			p2.add(new Chunk(glue2));
			p2.setIndentationLeft(0);
			p2.add("Total Unbilled Amount  :" + totalStagesAmount.getTotalUnbilledAmount());
			p2.setIndentationRight(20);
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
			for (Invoice invoice : invoiceList.getData()) {
				invoices.add(prepareBillingToArray(invoice,locations));
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

	private String[] prepareBillingToArray(Invoice invoice, OrgLocations locations) throws Exception {

		String billingArray[] = new String[Constants.BILLING_HEADERS.length];

		if (null != invoice.getInvoiceId() && !"".equalsIgnoreCase(invoice.getInvoiceId())) {
			billingArray[0] = invoice.getInvoiceId();
		} else {
			billingArray[0] = "--";
		}

		OrgLocations orgLocations = null;
		
//		 OrgLocations orglo = orgLocationService.getOrgLocations(invoice.getOrgLocation().getId());
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

		if (null != invoice.getContactUsers()) {
			List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(invoice.getContactUsers());
			String contactName = "";
			int count = contactUsersList.size();
			for(User contactUser : contactUsersList){
				if(null != contactUser.getLastName()
						&& !"".equalsIgnoreCase(contactUser.getLastName())) {
					StringBuilder contactNameBuilder = new StringBuilder();
							contactName = contactName + contactNameBuilder.append(contactUser.getFirstName()).append(" ")
									.append(contactUser.getLastName()).toString();
				}else{
					contactName = contactName + contactUser.getFirstName();
				}
				count--;
				if(count != 0){
					contactName = contactName + ", ";
				}
			}
			billingArray[2] = contactName;
		} else {
			billingArray[2] = "--";
		}

		if (null != invoice.getContactUsers()) {
			List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(invoice.getContactUsers());
			String contactEmail = "";
			int count = contactUsersList.size();
			for(User contactUser : contactUsersList){
				if(null != contactUser.getEmail()
						&& !"".equalsIgnoreCase(contactUser.getEmail())) {
							contactEmail = contactEmail + contactUser.getEmail();
				}
				count--;
				if(count != 0){
					contactEmail = contactEmail + ", ";
				}
			}
			billingArray[3] = contactEmail;
		} else {
			billingArray[3] = "--";
		}

		if (null != invoice.getContactUsers()) {
			List<User> contactUsersList = userDao.getUsersListUsingMultipleStringIds(invoice.getContactUsers());
			String contactMobile = "";
			int count = contactUsersList.size();
			for(User contactUser : contactUsersList){
				if(null != contactUser.getMobilePhone()
						&& !"".equalsIgnoreCase(contactUser.getMobilePhone())) {
					StringBuilder contactNameBuilder = new StringBuilder();
							contactMobile = contactMobile + contactUser.getMobilePhone();
				}
				count--;
				if(count != 0 && null != contactUser.getMobilePhone()
						&& !"".equalsIgnoreCase(contactUser.getMobilePhone())){
					contactMobile = contactMobile + ", ";
				}
			}
			billingArray[4] = contactMobile;
		} else {
			billingArray[4] = "--";
		}

		if (null != invoice.getInvoiceDate()) {
			String invoiceDate = commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),invoice.getInvoiceDate());
			billingArray[5] = invoiceDate;
		} else {
			billingArray[5] = "--";
		}

		if (null != invoice.getDueDate()) {
			String dueDate = commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),invoice.getDueDate());
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
			if(Constants.SENT.equalsIgnoreCase(invoice.getInvoiceStatus())){
				billingArray[10] = "Sent";
			}else if(Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus())){
				billingArray[10] = "Draft";
			}else{
				billingArray[10] = invoice.getInvoiceStatus();
			}
		} else {
			billingArray[10] = "--";
		}

		if (null != invoice.getPaymentStatus() && !"".equalsIgnoreCase(invoice.getPaymentStatus())) {
			billingArray[11] = invoice.getPaymentStatus();
		} else {
			billingArray[11] = "--";
		}

		if (null != invoice.getLinkInvoiceRefInvoiceId()
				&& !"".equalsIgnoreCase(String.valueOf(invoice.getLinkInvoiceRefInvoiceId()))) {
			billingArray[12] = String.valueOf(invoice.getLinkInvoiceRefInvoiceId());
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
	
	public static void main(String[] args) {
		System.out.println("Available Fonts");
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for(String font : g.getAvailableFontFamilyNames()) {
			System.out.println(font);
		}
	}
}