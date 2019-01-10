package com.bis.sourcelead.util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.InvoiceTableItemsDTO;
import com.bis.sourcelead.dto.InvoiceTableOtherItemsDTO1;
import com.bis.sourcelead.dto.InvoiceTableOtherItemsDTO2;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.CustomInvoiceTemplate;
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
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.dynamicReport.Templates;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListCellBuilder;
import net.sf.dynamicreports.report.builder.style.PenBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.LineStyle;

@Component
public class CustomInvoicePDFHelper {

	private static final Logger logger = LoggerFactory.getLogger(CustomInvoicePDFHelper.class);

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	UserService userService;

	@Autowired
	OrgService orgService;
	
	@Autowired
	private CountriesService countriesService;
	
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

	private void initializeReport(JasperReportBuilder report, int tableIndex, JasperReportBuilder tableReport,
			HorizontalListBuilder[] horizontalListBuilderArray) {
		StyleBuilder columnStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
		try {
			switch (tableIndex) {
			case 0:
				report.setColumnStyle(columnStyle).title(cmp.subreport(tableReport), horizontalListBuilderArray[0],
						horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
//				report.show();
				break;

			case 1:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], cmp.subreport(tableReport),
						horizontalListBuilderArray[1], horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 2:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						cmp.subreport(tableReport), horizontalListBuilderArray[2], horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 3:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], cmp.subreport(tableReport), horizontalListBuilderArray[3],
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 4:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], cmp.subreport(tableReport),
						horizontalListBuilderArray[4], horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 5:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						cmp.subreport(tableReport), horizontalListBuilderArray[5], horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 6:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], cmp.subreport(tableReport), horizontalListBuilderArray[6],
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 7:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], cmp.subreport(tableReport),
						horizontalListBuilderArray[7], horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 8:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						cmp.subreport(tableReport), horizontalListBuilderArray[8], horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 9:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], cmp.subreport(tableReport), horizontalListBuilderArray[9],
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 10:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], cmp.subreport(tableReport),
						horizontalListBuilderArray[10], horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 11:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10],
						cmp.subreport(tableReport), horizontalListBuilderArray[11], horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 12:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10],
						horizontalListBuilderArray[11], cmp.subreport(tableReport), horizontalListBuilderArray[12],
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;

			case 13:
				report.setColumnStyle(columnStyle).title(horizontalListBuilderArray[0], horizontalListBuilderArray[1],
						horizontalListBuilderArray[2], horizontalListBuilderArray[3], horizontalListBuilderArray[4],
						horizontalListBuilderArray[5], horizontalListBuilderArray[6], horizontalListBuilderArray[7],
						horizontalListBuilderArray[8], horizontalListBuilderArray[9], horizontalListBuilderArray[10],
						horizontalListBuilderArray[11], horizontalListBuilderArray[12], cmp.subreport(tableReport),
						horizontalListBuilderArray[13], cmp.verticalGap(10));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File createCustomInvoicePDF(Invoice invoice, OrgLocations orgLocation, User user, Org org,
			String vendorCurrencyCode, String loginOrgCurrencyCode, CustomInvoiceTemplate customInvoiceTemplate,
			String pdfType, String defaultFont, String sourceleadURL) {
		try {
			File invoiceFile = new File(
					orgLocation.getOrgLocationName() + "_" + invoice.getInvoiceId() + new Date().getTime() + ".pdf");
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
						for (int i = 0, index = 0; i < jsonArray.length(); i = i + 2, index++) {
							boolean tableFlag1 = false, tableFlag2 = false, footerFlag1 = false, footerFlag2 = false;
							boolean empty1 = false, empty2 = false;
							boolean cellFlag1 = false, cellFlag2 = false;
							HorizontalListCellBuilder cellBuilder1 = null;
							HorizontalListCellBuilder cellBuilder2 = null;
							if (i < jsonArray.length()) {
								JSONObject jsonObject1 = jsonArray.getJSONObject(i);
								if (jsonObject1 != null && jsonObject1.has("name")) {
									String type = jsonObject1.getString("type");
									switch (type) {
									case "image":
										cellBuilder1 = getLogoDataFromJSON(jsonObject1, sourceleadURL);
										break;
									case "table":
										if (pdfType.equalsIgnoreCase("other")) {
											tableReport = getOtherTableDataFromJSON(jsonObject1,
													invoice.getOrgLocation(), invoice.getInvoiceLineItems(), invoice);
										} else {
											tableReport = getTableDataFromJSON(jsonObject1, invoice.getOrgLocation(),
													invoice.getInvoiceLineItems());
										}
										tableFlag1 = true;
										break;
									case "text":
										cellBuilder1 = getTextDataFromJSON(jsonObject1, invoice.getOrgLocation(), user, invoice, false, defaultFont);
										break;
									case "footer":
										cellBuilder1 = getFooterDataFromJSON(jsonObject1, invoice.getOrgLocation(), defaultFont);
										footerFlag1 = true;
										break;
									default:
										cellBuilder1 = getEmptyDataFromJSON(defaultFont);
										break;
									}
								} else {
									cellBuilder1 = getEmptyDataFromJSON(defaultFont);
									empty1 = true;
								}
								cellFlag1 = true;
							}

							if (i + 1 < jsonArray.length()) {
								JSONObject jsonObject2 = jsonArray.getJSONObject(i + 1);
								if (jsonObject2 != null && jsonObject2.has("name")) {
									String type = jsonObject2.getString("type");
									switch (type) {
									case "image":
										cellBuilder2 = getLogoDataFromJSON(jsonObject2, sourceleadURL);
										break;
									case "table":
										if (pdfType.equalsIgnoreCase("other")) {
											tableReport = getOtherTableDataFromJSON(jsonObject2,
													invoice.getOrgLocation(), invoice.getInvoiceLineItems(), invoice);
										} else {
											tableReport = getTableDataFromJSON(jsonObject2, invoice.getOrgLocation(),
													invoice.getInvoiceLineItems());
										}
										tableFlag2 = true;
										break;
									case "text":
										cellBuilder2 = getTextDataFromJSON(jsonObject2, invoice.getOrgLocation(), user, invoice, false, defaultFont);
										break;
									case "footer":
										cellBuilder2 = getFooterDataFromJSON(jsonObject2, invoice.getOrgLocation(), defaultFont);
										footerFlag2 = true;
										break;
									default:
										cellBuilder2 = getEmptyDataFromJSON(defaultFont);
										break;
									}
								} else {
									cellBuilder2 = getEmptyDataFromJSON(defaultFont);
									empty2 = true;
								}
								cellFlag2 = true;
							}

							if (cellFlag1 && cellFlag2 && cellBuilder1 != null && cellBuilder2 != null) {
								horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
										.setGap(50).add(cellBuilder1.heightFixedOnTop())
										.add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
							} else if (cellFlag1 && cellBuilder1 != null) {
								cellBuilder2 = getEmptyDataFromJSON(defaultFont);
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
								cellBuilder1 = getEmptyDataFromJSON(defaultFont);
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
						for (int i = 0, index = 0; i < jsonArray.length(); i = i + 2, index++) {
							boolean tableFlag1 = false, tableFlag2 = false, footerFlag1 = false, footerFlag2 = false;
							boolean empty1 = false, empty2 = false;
							boolean cellFlag1 = false, cellFlag2 = false;
							HorizontalListCellBuilder cellBuilder1 = null;
							HorizontalListCellBuilder cellBuilder2 = null;
							if (i < jsonArray.length()) {
								JSONObject jsonObject1 = jsonArray.getJSONObject(i);
								if (jsonObject1 != null && jsonObject1.has("name")) {
									String type = jsonObject1.getString("type");
									switch (type) {
									case "image":
										cellBuilder1 = getLogoDataFromJSON(jsonObject1, sourceleadURL);
										break;
									case "table":
										if (pdfType.equalsIgnoreCase("other")) {
											tableReport = getOtherTableDataFromJSON(jsonObject1,
													invoice.getOrgLocation(), invoice.getInvoiceLineItems(), invoice);
										} else {
											tableReport = getTableDataFromJSON(jsonObject1, invoice.getOrgLocation(),
													invoice.getInvoiceLineItems());
										}
										tableFlag1 = true;
										break;
									case "text":
										cellBuilder1 = getTextDataFromJSON(jsonObject1, invoice.getOrgLocation(), user, invoice, false, defaultFont);
										break;
									case "footer":
										cellBuilder1 = getFooterDataFromJSON(jsonObject1, invoice.getOrgLocation(), defaultFont);
										footerFlag1 = true;
										break;
									default:
										cellBuilder1 = getEmptyDataFromJSON(defaultFont);
										break;
									}
								} else {
									cellBuilder1 = getEmptyDataFromJSON(defaultFont);
									empty1 = true;
								}
								cellFlag1 = true;
							}

							if (i + 1 < jsonArray.length()) {
								JSONObject jsonObject2 = jsonArray.getJSONObject(i + 1);
								if (jsonObject2 != null && jsonObject2.has("name")) {
									String type = jsonObject2.getString("type");
									switch (type) {
									case "image":
										cellBuilder2 = getLogoDataFromJSON(jsonObject2, sourceleadURL);
										break;
									case "table":
										if (pdfType.equalsIgnoreCase("other")) {
											tableReport = getOtherTableDataFromJSON(jsonObject2,
													invoice.getOrgLocation(), invoice.getInvoiceLineItems(), invoice);
										} else {
											tableReport = getTableDataFromJSON(jsonObject2, invoice.getOrgLocation(), invoice.getInvoiceLineItems());
										}
										tableFlag2 = true;
										break;
									case "text":
										cellBuilder2 = getTextDataFromJSON(jsonObject2, invoice.getOrgLocation(), user, invoice, false, defaultFont);
										break;
									case "footer":
										cellBuilder2 = getFooterDataFromJSON(jsonObject2, invoice.getOrgLocation(), defaultFont);
										footerFlag2 = true;
										break;
									default:
										cellBuilder2 = getEmptyDataFromJSON(defaultFont);
										break;
									}
								} else {
									cellBuilder2 = getEmptyDataFromJSON(defaultFont);
									empty2 = true;
								}
								cellFlag2 = true;
							}

							if (cellFlag1 && cellFlag2 && cellBuilder1 != null && cellBuilder2 != null) {
								horizontalListBuilderArray[index].add(cmp.horizontalList().setStyle(stl.style(10))
										.setGap(50).add(cellBuilder1.heightFixedOnTop())
										.add(cellBuilder2.heightFixedOnTop())).setFixedWidth(560);
							} else if (cellFlag1 && cellBuilder1 != null) {
								cellBuilder2 = getEmptyDataFromJSON(defaultFont);
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
								cellBuilder1 = getEmptyDataFromJSON(defaultFont);
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
				grandTotal.append(
						"Discount " + "(" + invoice.getDiscountAmount() + " %)" + " : " + invoice.getDiscountValue());
				grandTotal.append(System.getProperty("line.separator"));
			} else {
				grandTotal.append("Discount " + "(" + invoice.getDiscountAmount() + " " + invoice.getDiscountType()
						+ ")" + " : " + invoice.getDiscountValue());
				grandTotal.append(System.getProperty("line.separator"));
			}
		}

		if (!invoice.getInvoiceTax().isEmpty()) {
			Set<InvoiceTax> invoiveTaxDetails = invoice.getInvoiceTax();
			for (InvoiceTax InvoiceTax : invoiveTaxDetails) {
				if (InvoiceTax.getTaxValue() != null) {
					if (InvoiceTax.getTaxName() != null) {
						if (InvoiceTax.getTaxType().equalsIgnoreCase(Constants.PERCENTAGE)) {
							grandTotal.append(InvoiceTax.getTaxName() + "(" + InvoiceTax.getTaxValue() + " %)" + " : "
									+ InvoiceTax.getTaxAmount());
							grandTotal.append(System.getProperty("line.separator"));
						} else {
							grandTotal.append(InvoiceTax.getTaxName() + "(" + InvoiceTax.getTaxValue() + " "
									+ InvoiceTax.getTaxType() + ")" + " : " + InvoiceTax.getTaxAmount());
							grandTotal.append(System.getProperty("line.separator"));
						}
					} else {
						if (InvoiceTax.getTaxType().equalsIgnoreCase(Constants.PERCENTAGE)) {
							grandTotal
									.append("(" + InvoiceTax.getTaxValue() + " %)" + " : " + InvoiceTax.getTaxAmount());
							grandTotal.append(System.getProperty("line.separator"));
						} else {
							grandTotal.append("(" + InvoiceTax.getTaxValue() + " " + InvoiceTax.getTaxType() + ")"
									+ " : " + InvoiceTax.getTaxAmount());
							grandTotal.append(System.getProperty("line.separator"));
						}
					}
				}
			}
		}
		return grandTotal.toString();
	}

	private HorizontalListCellBuilder getLogoDataFromJSON(JSONObject jsonObject, String sourceleadURL) throws JSONException {
		HorizontalListCellBuilder cellBuilder = null;
		JSONArray subItemsArrayJSON = jsonObject.getJSONArray("subItems");
		if (subItemsArrayJSON != null && subItemsArrayJSON.length() > 0) {
			for (int i = 0; i < subItemsArrayJSON.length(); i++) {
				JSONObject subItemObject = subItemsArrayJSON.getJSONObject(i);
				if (subItemObject.getBoolean("checked")) {
					HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
					int width = 140;
					String styleValue = subItemObject.getString("style");
					for (String allStyles : styleValue.split(";")) {
						String style[] = allStyles.split(":");
						if (style.length > 0) {
							for (int j = 0; j < style.length; j++) {
								if (style[0].equalsIgnoreCase("width")) {
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
								if (style[0].equals("float")) {
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
					cellBuilder = cmp.hListCell(cmp.image(sourceleadURL.replace("/app", "") + subItemObject.getString("value"))
							.setStyle(columnStyle).setHorizontalAlignment(horizontalAlignment)
							.setFixedDimension(width, width / 2).setFixedWidth(245)).heightFixedOnTop();
				}
			}
		}
		return cellBuilder;
	}

	private JasperReportBuilder getOtherTableDataFromJSON(JSONObject jsonObject, OrgLocations orgLocation,
			Set<InvoiceLineItems> invoiceLineItemsList, Invoice invoice) {
		try {
			if (invoiceLineItemsList != null && !invoiceLineItemsList.isEmpty()) {
				if (Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType())) {
					return getTableDataFromJSON(jsonObject, orgLocation, invoiceLineItemsList);
				} else {
					if (invoice.getExpensesInvoices() != null) {
						List<InvoiceTableOtherItemsDTO1> tableItemsList = new LinkedList<>();
						for (InvoiceLineItems invoiceLineItems : invoiceLineItemsList) {
							InvoiceTableOtherItemsDTO1 invoiceTableItemsDTO = new InvoiceTableOtherItemsDTO1();
							// Date
							String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
									invoiceLineItems.getStartDate());
							invoiceTableItemsDTO.setDate(startDate);
							// Category
							if (invoiceLineItems.getExpenseCategory() != null
									&& !invoiceLineItems.getExpenseCategory().isEmpty()) {
								invoiceTableItemsDTO.setCategory(invoiceLineItems.getExpenseCategory());
							} else {
								invoiceTableItemsDTO.setCategory("----");
							}
							// Description/Price
							if (invoiceLineItems.getInlineDescription() != null
									&& !invoiceLineItems.getInlineDescription().isEmpty()) {
								invoiceTableItemsDTO.setDescription(invoiceLineItems.getInlineDescription());
							} else {
								invoiceTableItemsDTO.setDescription("----");
							}
							// Amount
							if (invoiceLineItems.getAmount() != null && !invoiceLineItems.getAmount().isEmpty()) {
								invoiceTableItemsDTO.setAmount(invoiceLineItems.getAmount());
							} else {
								invoiceTableItemsDTO.setAmount("----");
							}
							tableItemsList.add(invoiceTableItemsDTO);
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
						// InvoiceTableOtherItemsDTO1
						return createOtherTableItem1(tableItemsList, thStyle, tdStyle);
					} else {
						List<InvoiceTableOtherItemsDTO2> tableItemsList = new LinkedList<>();
						// Date, Description, Quantity, Rate/Price, Total
						for (InvoiceLineItems invoiceLineItems : invoiceLineItemsList) {
							InvoiceTableOtherItemsDTO2 invoiceTableItemsDTO = new InvoiceTableOtherItemsDTO2();
							// Date
							String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
									invoiceLineItems.getStartDate());
							invoiceTableItemsDTO.setDate(startDate);
							// Description/Price
							if (invoiceLineItems.getInlineDescription() != null
									&& !invoiceLineItems.getInlineDescription().isEmpty()) {
								invoiceTableItemsDTO.setDescription(invoiceLineItems.getInlineDescription());
							} else {
								invoiceTableItemsDTO.setDescription("----");
							}
							// Qty
							if (invoiceLineItems.getQuantity() != null && !invoiceLineItems.getQuantity().isEmpty()) {
								invoiceTableItemsDTO.setQuantity(invoiceLineItems.getQuantity());
							} else {
								invoiceTableItemsDTO.setQuantity("----");
							}
							// Rate
							if (invoiceLineItems.getRate() != null && !invoiceLineItems.getRate().isEmpty()) {
								invoiceTableItemsDTO.setRate(invoiceLineItems.getRate());
							} else {
								invoiceTableItemsDTO.setRate("----");
							}
							// Total
							if (invoiceLineItems.getAmount() != null && !invoiceLineItems.getAmount().isEmpty()) {
								invoiceTableItemsDTO.setTotal(invoiceLineItems.getAmount());
							} else {
								invoiceTableItemsDTO.setTotal("----");
							}
							tableItemsList.add(invoiceTableItemsDTO);
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
						// InvoiceTableOtherItemsDTO2
						return createOtherTableItem2(tableItemsList, thStyle, tdStyle);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private JasperReportBuilder getTableDataFromJSON(JSONObject jsonObject, OrgLocations orgLocation,
			Set<InvoiceLineItems> invoiceLineItemsList) {
		try {
			List<InvoiceTableItemsDTO> tableItemsList = new LinkedList<>();
			if (invoiceLineItemsList != null && !invoiceLineItemsList.isEmpty()) {
				for (InvoiceLineItems invoiceLineItems : invoiceLineItemsList) {
					InvoiceTableItemsDTO invoiceTableItemsDTO = new InvoiceTableItemsDTO();
					// ID
					if (invoiceLineItems.getCandidate() != null) {
						Employees employee = null;
						if (invoiceLineItems.getCandidate().getConsultantUser() != null) {
							employee = employeesService
									.getEmployeeByUserId(invoiceLineItems.getCandidate().getConsultantUser().getId());
						}
						invoiceTableItemsDTO.setId(employee != null ? employee.getEmployeeId() : "----");
					} else {
						invoiceTableItemsDTO.setId(invoiceLineItems.getCandidate() != null
								? invoiceLineItems.getCandidate().getId().toString()
								: "----");
					}

					// NAME
					if (invoiceLineItems.getCandidate() != null) {
						invoiceTableItemsDTO.setName(invoiceLineItems.getCandidate().getFirstName() + " "
								+ (invoiceLineItems.getCandidate().getLastName() != null
										? invoiceLineItems.getCandidate().getLastName()
										: ""));
					} else {
						if (invoiceLineItems.getCandidate() != null) {
							invoiceTableItemsDTO.setName(invoiceLineItems.getCandidate().getFirstName() + " "
									+ invoiceLineItems.getCandidate().getLastName());
						} else {
							invoiceTableItemsDTO.setName(invoiceLineItems.getCandidate().getFirstName());
						}
					}

					// PERIOD
					if (null != invoiceLineItems.getStartDate() && null != invoiceLineItems.getEndDate()) {
						String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
								invoiceLineItems.getStartDate());
						String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
								invoiceLineItems.getEndDate());

						invoiceTableItemsDTO.setPeriod(startDate + System.getProperty("line.separator") + " to "
								+ System.getProperty("line.separator") + endDate);
					} else if (null != invoiceLineItems.getStartDate()) {
						String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
								invoiceLineItems.getStartDate());
						invoiceTableItemsDTO.setPeriod(startDate);
					} else {
						invoiceTableItemsDTO.setPeriod("----");
					}

					// BRIEF DESCRIPTION
					if (null != invoiceLineItems.getInlineDescription()
							&& !"".equalsIgnoreCase(invoiceLineItems.getInlineDescription())) {
						invoiceTableItemsDTO.setDescription(invoiceLineItems.getInlineDescription());
					} else {
						invoiceTableItemsDTO.setDescription("----");
					}

					// QTY / HOURS
					if (null != invoiceLineItems.getTotalHours()
							&& !"".equalsIgnoreCase(invoiceLineItems.getTotalHours())) {
						invoiceTableItemsDTO.setQty(invoiceLineItems.getTotalHours());
					} else {
						invoiceTableItemsDTO.setQty("----");
					}

					// RATE
					if (null != invoiceLineItems.getRate() && !"".equalsIgnoreCase(invoiceLineItems.getRate())) {
						invoiceTableItemsDTO.setRate(invoiceLineItems.getRate());
					} else {
						invoiceTableItemsDTO.setRate("----");
					}

					// TOTAL
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

	private JasperReportBuilder createOtherTableItem1(List<InvoiceTableOtherItemsDTO1> tableItemsList,
			String thStyleString, String tdStyleString) {
		TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> categoryColumn = col.column("Category", "category", type.stringType());
		TextColumnBuilder<String> descriptionColumn = col.column("Description/Price", "description", type.stringType())
				.setFixedWidth(80);
		TextColumnBuilder<String> amountColumn = col.column("Amount", "amount", type.stringType()).setFixedWidth(80);
		StyleBuilder tableHeadStyle = stl.style(Templates.columnStyle);
		StyleBuilder tableRowStyle = stl.style(Templates.columnStyle);

		if (thStyleString != null && !thStyleString.isEmpty()) {
			for (String allStyles : thStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if (style.length > 1) {
					switch (style[0]) {
					case "border":
						if (!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");

							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);

							tableHeadStyle.setBorder(pen);
							/* tableHeadStyle.setBorder(stl.pen1Point()); */
						}
						break;
					case "font-size":
						tableHeadStyle.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()));
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

		if (tdStyleString != null && !tdStyleString.isEmpty()) {
			for (String allStyles : tdStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if (style.length > 1) {
					switch (style[0]) {
					case "border":
						if (!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");

							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);

							tableRowStyle.setBorder(pen);
							/* tableHeadStyle.setBorder(stl.pen1Point()); */
						}
						// tableRowStyle.setBorder(stl.pen1Point());
						break;
					case "font-size":
						tableRowStyle.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()));
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
		tableReport.setColumnStyle(tableRowStyle).setColumnTitleStyle(tableHeadStyle)
				.columns(dateColumn, categoryColumn, descriptionColumn, amountColumn)
				.columnGrid(dateColumn, categoryColumn, descriptionColumn, amountColumn).setDataSource(tableItemsList);

		return tableReport;
	}

	private JasperReportBuilder createOtherTableItem2(List<InvoiceTableOtherItemsDTO2> tableItemsList,
			String thStyleString, String tdStyleString) {
		TextColumnBuilder<String> dateColumn = col.column("Date", "date", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> descriptionColumn = col.column("Description", "description", type.stringType());
		TextColumnBuilder<String> qtyColumn = col.column("Quantity", "quantity", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> rateColumn = col.column("Rate/Price", "rate", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> totalColumn = col.column("Total", "total", type.stringType()).setFixedWidth(80);
		StyleBuilder tableHeadStyle = stl.style(Templates.columnStyle);
		StyleBuilder tableRowStyle = stl.style(Templates.columnStyle);

		if (thStyleString != null && !thStyleString.isEmpty()) {
			for (String allStyles : thStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if (style.length > 1) {
					switch (style[0]) {
					case "border":
						if (!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");

							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);

							tableHeadStyle.setBorder(pen);
							/* tableHeadStyle.setBorder(stl.pen1Point()); */
						}
						break;
					case "font-size":
						tableHeadStyle.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()));
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

		if (tdStyleString != null && !tdStyleString.isEmpty()) {
			for (String allStyles : tdStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if (style.length > 1) {
					switch (style[0]) {
					case "border":
						if (!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");

							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);

							tableRowStyle.setBorder(pen);
							/* tableHeadStyle.setBorder(stl.pen1Point()); */
						}
						// tableRowStyle.setBorder(stl.pen1Point());
						break;
					case "font-size":
						tableRowStyle.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()));
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
		tableReport.setColumnStyle(tableRowStyle).setColumnTitleStyle(tableHeadStyle)
				.columns(dateColumn, descriptionColumn, qtyColumn, rateColumn, totalColumn)
				.columnGrid(dateColumn, descriptionColumn, qtyColumn, rateColumn, totalColumn)
				.setDataSource(tableItemsList);

		return tableReport;
	}

	private JasperReportBuilder createTableItem(List<InvoiceTableItemsDTO> tableItemsList, String thStyleString,
			String tdStyleString) {
		TextColumnBuilder<String> idColumn = col.column("Id", "id", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> nameColumn = col.column("Name", "name", type.stringType());
		TextColumnBuilder<String> periodColumn = col.column("Period", "period", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> descriptionColumn = col.column("Brief Description", "description", type.stringType())
				.setFixedWidth(80);
		TextColumnBuilder<String> qtyColumn = col.column("Qty", "qty", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> rateColumn = col.column("Rate", "rate", type.stringType()).setFixedWidth(80);
		TextColumnBuilder<String> totalColumn = col.column("Total", "total", type.stringType()).setFixedWidth(80);
		StyleBuilder tableHeadStyle = stl.style(Templates.columnStyle);
		StyleBuilder tableRowStyle = stl.style(Templates.columnStyle);

		if (thStyleString != null && !thStyleString.isEmpty()) {
			for (String allStyles : thStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if (style.length > 1) {
					switch (style[0]) {
					case "border":
						if (!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");

							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);

							tableHeadStyle.setBorder(pen);
							/* tableHeadStyle.setBorder(stl.pen1Point()); */
						}
						break;
					case "font-size":
						tableHeadStyle.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()));
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

		if (tdStyleString != null && !tdStyleString.isEmpty()) {
			for (String allStyles : tdStyleString.split(";")) {
				String style[] = allStyles.split(":");
				if (style.length > 1) {
					switch (style[0]) {
					case "border":
						if (!style[1].equalsIgnoreCase("none")) {
							PenBuilder pen = stl.pen();
							String[] borderStyles = style[1].split(" ");

							pen.setLineColor(Color.decode(borderStyles[2]));
							pen.setLineStyle(LineStyle.SOLID);
							pen.setLineWidth(1f);

							tableRowStyle.setBorder(pen);
							/* tableHeadStyle.setBorder(stl.pen1Point()); */
						}
						// tableRowStyle.setBorder(stl.pen1Point());
						break;
					case "font-size":
						tableRowStyle.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()));
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
		tableReport.setColumnStyle(tableRowStyle).setColumnTitleStyle(tableHeadStyle)
				.columns(idColumn, nameColumn, periodColumn, descriptionColumn, qtyColumn, rateColumn, totalColumn)
				.columnGrid(idColumn, nameColumn, periodColumn, descriptionColumn, qtyColumn, rateColumn, totalColumn)
				.setDataSource(tableItemsList);

		return tableReport;
	}

	private HorizontalListCellBuilder getFooterDataFromJSON(JSONObject jsonObject, OrgLocations orgLocations, String defaultFont) {
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
					if (subItemObject.getBoolean("checked")) {
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
				cellBuilder = cmp.hListCell(createSectionItem(textAlign, itemTitle, valuesArray, stylesArray, true, defaultFont))
						.heightFixedOnTop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellBuilder;
	}

	private HorizontalListCellBuilder getTextDataFromJSON(JSONObject jsonObject, OrgLocations orgLocation, User user,
			Invoice invoice, boolean topBorderFlag, String defaultFont) throws Exception {
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
					if (subItemObject.getBoolean("checked")) {
						String subItemTitle = subItemObject.getString("value");

						if (itemTitle.equalsIgnoreCase("Invoice Details") || itemTitle.equalsIgnoreCase("To")
								|| itemTitle.equalsIgnoreCase("Banking Details")
								|| itemTitle.equalsIgnoreCase("Memo / Description")) {
							if (i == 0) {
								if (itemTitle.equalsIgnoreCase("Invoice Details")) {
									valuesArray[0] = Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType())
											? "Bill"
											: "Invoice";
								} else {
									valuesArray[0] = itemTitle;
								}

								stylesArray[0] = "font-family:" + defaultFont + ";font-weight:400;font-size:16px;color:#545454;";
								stylesArray[0] += (position % 2 == 0) ? "text-align:right;" : "text-align:left;";
							}
							stylesArray[i + 1] = subItemObject.getString("style");
						} else {
							stylesArray[i] = subItemObject.getString("style");
						}

						switch (itemTitle) {
						// Section - Organisation Name
						case "Organisation Name":
							valuesArray[i] = orgLocation.getOrgLocationName();
							break;
						// Section - Client Details
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
									address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState())
											.append(",").append(orgLocation.getCity()).toString();
								} else if (null != orgLocation.getState()) {
									address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState())
											.toString();
								} else {
									address.append(orgLocation.getCountry()).toString();
								}
								valuesArray[i] = address.toString();
								break;

							default:
								break;
							}
							break;
						// Section - Invoice Details
						case "Invoice Details":
							switch (subItemTitle) {
							case "Invoice Id":
								valuesArray[i + 1] = (Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType())
										? "Billing Id : "
										: "Invoice Id : ") + invoice.getInvoiceId();
								break;

							case "Invoice Date":
								String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
										invoice.getCreatedDate());
								valuesArray[i + 1] = (Constants.Payables.equalsIgnoreCase(invoice.getInvoiceType())
										? "Billing Date : "
										: "Invoice Date : ") + createdDate;
								break;

							case "Due Date":
								String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),
										invoice.getDueDate());
								valuesArray[i + 1] = "Due Date : " + dueDate;
								break;

							case "Payment Terms":
								valuesArray[i + 1] = "Payment Terms : "
										+ (invoice.getPaymentFrequency() != null ? invoice.getPaymentFrequency()
												: "----");
								break;

							default:
								break;
							}
							break;
						// Section - To
						case "To":
							User contactUser = userService
									.getUserById(Long.parseLong(invoice.getContactUsers().split(",")[0]));
							OrgRelations orgRel = orgRelationsService
									.getOrRelationsById(invoice.getOrgRelationID().getId());

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
						// Section - Banking Details
						case "Banking Details":
							if (invoice.getPaymentAccount() != null) {
								PaymentAccountDTO paymentAccount = orgService
										.getPaymentAccount(invoice.getPaymentAccount().getId(), null);
								if (paymentAccount.getAccountType().equalsIgnoreCase("WEPAY")) {
									switch (subItemTitle) {
									case "Account Type":
										valuesArray[i + 1] = "Account Type : " + paymentAccount.getAccountType();
										break;
									case "Email":
										valuesArray[i + 1] = "Email : " + paymentAccount.getEmail();
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
						// Section - Memo / Description
						case "Memo / Description":
							if (invoice.getInvoiceComment() != null && !invoice.getInvoiceComment().isEmpty()) {
								valuesArray[i + 1] = invoice.getInvoiceComment() != null ? invoice.getInvoiceComment()
										: "";
							} else {
								valuesArray[0] = "";
								stylesArray[0] = "";
							}
							// Section - Discount & Total
						case "Total":
							switch (subItemTitle) {
							case "Discount":
								valuesArray[i] = getDiscountAndTotalAmountForInvoice(invoice);
								break;
							case "Grand Total":
								Countries countries = countriesService
										.getCountriesByCountryName(orgLocation.getCountry());
								String countryCurrency = countries.getCurrency();
								valuesArray[i] = "Grand Total : " + countryCurrency + " " + invoice.getTotal();
								break;
							default:
								break;
							}
						default:
							break;
						}
					}
				}

				cellBuilder = cmp
						.hListCell(createSectionItem(textAlign, itemTitle, valuesArray, stylesArray, topBorderFlag, defaultFont))
						.heightFixedOnTop();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cellBuilder;
	}

	private HorizontalListCellBuilder getEmptyDataFromJSON(String defaultFont) {
		HorizontalListCellBuilder cellBuilder = null;
		try {
			cellBuilder = cmp.hListCell(createSectionItem(HorizontalAlignment.LEFT, "", null, null, false, defaultFont)).widthFixed()
					.heightFixedOnTop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellBuilder;
	}

	private ComponentBuilder<?, ?> createSectionItem(HorizontalAlignment horizontalAlignment, String label,
			String[] args, String[] styles, boolean addTopBorder, String defaultFont) {
		HorizontalListBuilder list = null;
		if (addTopBorder)
			list = cmp.horizontalList().setBaseStyle(stl.style().setTopBorder(stl.pen1Point()));
		else
			list = cmp.horizontalList().setBaseStyle(stl.style());

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				StyleBuilder styleBuilder = stl.style();
				styleBuilder.setFontSize(12);
				if (styles[i] != null) {
					for (String allStyles : styles[i].split(";")) {
						String style[] = allStyles.split(":");
						if (style.length > 1) {
							switch (style[0]) {
							case "font-family":
								try {
									styleBuilder.setFontName(style[1]);
								} catch (Exception e) {
									System.out.println("Font Exception : " + style[1]);
									logger.error("Font Exception : " + style[1]);
									styleBuilder.setFontName(defaultFont);
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
								switch (style[1]) {
								case "italic":
									styleBuilder.italic();
									break;
								}
								break;

							case "font-size":
								styleBuilder.setFontSize(Integer.parseInt(style[1].split("px")[0].trim()) - 2);
								break;

							case "text-align":
								styleBuilder.setHorizontalAlignment(
										style[1].trim().equalsIgnoreCase("left") ? HorizontalAlignment.LEFT
												: style[1].trim().equalsIgnoreCase("right") ? HorizontalAlignment.RIGHT
														: HorizontalAlignment.CENTER);
								break;

							case "color":
								if (!style[1].equalsIgnoreCase("transparent")) {
									styleBuilder.setForegroundColor(Color.decode(style[1]));
								}
								break;

							case "background-color":
								if (!style[1].equalsIgnoreCase("transparent")) {
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
								styleBuilder.setTopPadding(Integer.parseInt(style[1].split("px")[0].trim()));
								break;

							case "padding-right":
								styleBuilder.setRightPadding(Integer.parseInt(style[1].split("px")[0].trim()));
								break;

							case "padding-bottom":
								styleBuilder.setBottomPadding(Integer.parseInt(style[1].split("px")[0].trim()));
								break;

							case "padding-left":
								styleBuilder.setLeftPadding(Integer.parseInt(style[1].split("px")[0].trim()));
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

}
