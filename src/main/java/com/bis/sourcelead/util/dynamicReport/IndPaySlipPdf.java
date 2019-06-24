package com.bis.sourcelead.util.dynamicReport;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.indPayroll.dto.INDPayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPayrollPaySlipDeduction;
import com.bis.sourcelead.indPayroll.dto.IndPayrollPaySlipDeductionTotal;
import com.bis.sourcelead.indPayroll.dto.IndPayrollPaySlipEarning;
import com.bis.sourcelead.indPayroll.dto.IndPayrollPaySlipEarningAndDeducationTotal;
import com.bis.sourcelead.indPayroll.dto.IndPayrollPaySlipEarningTotal;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.text.SimpleDateFormat;

import edu.emory.mathcs.backport.java.util.LinkedList;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.style.PenBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.LineStyle;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Laxman
 */
@SuppressWarnings("unchecked")
@Component
public class IndPaySlipPdf {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private AssociatePfEsiService associatePfEsiService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	PayrollPaySlipIndService payrollPaySlipIndService;
	
	
	public File getIndiaPaySlipPdf(IndEmployeePayroll indEmployeePayroll, OrgLocations orgLocations) throws Exception {
		 File file = null;
			try {
			    String payslipDate = "";
				if(indEmployeePayroll.getPayCheckDate() != null) {
					SimpleDateFormat month_date = new SimpleDateFormat("MMMM - yyyy");
					payslipDate = month_date.format(indEmployeePayroll.getPayCheckDate());
				}
				
				file = File.createTempFile(payslipDate+ " Payslip_",".pdf");
				file.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
				
				//init styles
				StyleBuilder columnStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
				StyleBuilder subtotalStyle = stl.style(Templates.columnStyle);
				
				
				//Earning table related columns
				TextColumnBuilder<String> earnings = col.column("Earnings", "earnings", type.stringType()).setFixedWidth(150);
				TextColumnBuilder<BigDecimal> amount = col.column("Amount", "amount", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
				TextColumnBuilder<BigDecimal> grossSalary = col.column("Gross Salary", "grossSalary", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
				TextColumnBuilder<String> earningsTotal = col.column("Total", "total", type.stringType()).setFixedWidth(150);

				//Dudaction table related columns
				TextColumnBuilder<String> deduction = col.column("Deduction", "deduction", type.stringType()).setFixedWidth(215);
				TextColumnBuilder<BigDecimal> deductionAmount = col.column("Amount", "deductionAmount", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
				TextColumnBuilder<String> deductionTotal = col.column("Total", "deductionTotal", type.stringType()).setFixedWidth(215);

				//Tabele border style related code
				PenBuilder pen = stl.pen();
				pen.setLineColor(Color.decode("#989898"));
				pen.setLineStyle(LineStyle.SOLID);
				pen.setLineWidth(1f);

				columnStyle.setBorder(pen);
				columnStyle.setFontSize(10);
				columnStyle.setBackgroundColor(Color.decode("#6C7686"));
				columnStyle.setForegroundColor(Color.decode("#FFFFFF"));
				columnStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
				columnStyle.bold();
				
				subtotalStyle.setBorder(pen);
				
				OrgLocations orgLocation = indEmployeePayroll.getIndPaysheetFrequency().getOrgLocation();
				PayrollPaySlipInd ppsi = null;
				if(indEmployeePayroll.getPayrollPaySlipInd() != null) {
					ppsi = indEmployeePayroll.getPayrollPaySlipInd();
				}else {
					ppsi = payrollPaySlipIndService.deletePermissionStatsuByDeletePermission(Constants.NO, orgLocations.getId());
				}
				
				StyleBuilder leftStyle = stl.style();
				leftStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
				
				StyleBuilder rightStyle = stl.style();
				rightStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
				String sourceleadurl = sourceleadUrl.replaceAll("app", "");
				String companyImage = "";
				String companyAddress = "";
				
				//company logo payslip configuration condition
				if(ppsi.getCompanyLogo() != null && ppsi.getCompanyLogo().equalsIgnoreCase("Y")) {
					if(orgLocation.getOrg().getFileUrl() != null) {
						companyImage = sourceleadurl+orgLocation.getOrg().getFileUrl();
					}else {
						companyImage = sourceleadurl+"/resources/images/office-image.jpg";
					}
				}else {
					companyImage = "";
				}
			
				//company address payslip configuration condition
				if(ppsi.getCompanyAddress() != null && ppsi.getCompanyAddress().equalsIgnoreCase("Y")) {
					if(orgLocation.getOrgLocationAddress() != null) {
						companyAddress = orgLocation.getOrgLocationAddress();
					}else {
						StringBuilder address = new StringBuilder();
						if(orgLocation.getZipCode() != null) {
							address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(orgLocation.getCity()).append("-").append(orgLocation.getZipCode());
						}else {
							address.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(orgLocation.getCity());
						}
						companyAddress = address.toString();
					}
				}else {
					companyAddress = "";
				}
				
				
				
				Candidate candidate = indEmployeePayroll.getCandidate();
				//Earning table
				List<IndPayrollPaySlipEarning> tableItems1 = new LinkedList();
				
				//Earning total table 
				List<IndPayrollPaySlipEarningTotal> tableItemsTotal = new LinkedList();
				
				//Deducation total table
				List<IndPayrollPaySlipDeductionTotal> tableItemsDeductionTotal = new LinkedList();
				
				//Earning & Deducation total tabel
				List<IndPayrollPaySlipEarningAndDeducationTotal> tableItemsEarningDeductionTotal = new LinkedList();
				
				//Deducation table
				List<IndPayrollPaySlipDeduction> tableItems2 = new LinkedList();
				
				
				BigDecimal  deducationTotal = new BigDecimal(0.00);
				BigDecimal earningTotal = new BigDecimal(0.00);
				HashSet<IndEmployeePayroll> payrollList = new HashSet<>();
		   		payrollList.add(indEmployeePayroll);
		   		INDPayrollDTO indPayrollDTO = iNDPayrollCommonUtil.getINDPayrollDTOFromIndEmployeePayroll(payrollList, indEmployeePayroll.getIndPaysheetFrequency().getOrgLocation()).iterator().next();
		   		List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList = indPayrollDTO.getPayHeadHistoryDTOList();
		   		if(associatePayrollPayheadDTOList != null && !associatePayrollPayheadDTOList.isEmpty()) {
					//Earning list
		   			List<AssociatePayrollPayheadHistoryDTO> earningList = associatePayrollPayheadDTOList
		   					    .stream()
		   				    	.filter(app -> (app.getPayrollPayheadType().equals("Earnings") && app.getPaySlip().equals("Y") && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0)))
		   					    .collect(Collectors.toList());
		   			
		   			IndPayrollPaySlipEarning ippe = null;
		   			if(!earningList.isEmpty() && earningList.size() > 0) {
						for(AssociatePayrollPayheadHistoryDTO apph:earningList) {
							ippe = new IndPayrollPaySlipEarning();
				   			ippe.setEarnings(apph.getPayrollPayheadName());
				   			ippe.setAmount(apph.getMonthly());
				   			ippe.setGrossSalary(apph.getMonthly());
				   			earningTotal = earningTotal.add(apph.getMonthly());
				   			tableItems1.add(ippe);
						}
					}else {
						tableItems1.add(ippe);
					}
					
					//Deducation list
					List<AssociatePayrollPayheadHistoryDTO> decuctionList =  associatePayrollPayheadDTOList
						    	.stream()
							    .filter(app -> app.getPayrollPayheadType().equals("Deduction") && app.getPaySlip().equals("Y")  && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0))
							    .collect(Collectors.toList());
					
					IndPayrollPaySlipDeduction ippd = null;
					if(!decuctionList.isEmpty() && decuctionList.size() > 0) {
						for(AssociatePayrollPayheadHistoryDTO apph:decuctionList) {
							ippd = new IndPayrollPaySlipDeduction();
							ippd.setDeduction(apph.getPayrollPayheadName());
							ippd.setDeductionAmount(apph.getMonthly());
							deducationTotal = deducationTotal.add(apph.getMonthly());
				   			tableItems2.add(ippd);
						}
					}else {
						tableItems2.add(ippd);
					}
				
				 }
		   		
		   		//Earning Tabel content
				JasperReportBuilder earningsTable = report();
				earningsTable.setColumnTitleStyle(columnStyle).setColumnStyle(subtotalStyle)
				.columns(earnings,amount,grossSalary)
				.columnGrid(earnings,amount,grossSalary)
				.setDataSource(tableItems1);
				
				//Deducation Table content
				JasperReportBuilder deductionTable = report();
				deductionTable.setColumnTitleStyle(columnStyle).setColumnStyle(subtotalStyle)
				.columns(deduction,deductionAmount)
				.columnGrid(deduction,deductionAmount)
				.setDataSource(tableItems2);
				
				//earning total content
				StyleBuilder subtotalEarningStyle = stl.style(Templates.columnStyle).setBackgroundColor(Color.decode("#ededed")).bold();
				subtotalEarningStyle.setBorder(pen);
				IndPayrollPaySlipEarningTotal ipet = new IndPayrollPaySlipEarningTotal();
				ipet.setTotal("Total");
				ipet.setAmount(earningTotal);
				ipet.setGrossSalary(earningTotal);
				tableItemsTotal.add(ipet);
			 
				StyleBuilder columnStyleSize = stl.style(Templates.columnStyle).setFontSize(0).setTopPadding(0);
				JasperReportBuilder earningsTotalTable = report();
				earningsTotalTable.setColumnTitleStyle(columnStyleSize).setColumnStyle(subtotalEarningStyle)
				.columns(earningsTotal, amount, grossSalary)
				.columnGrid(earningsTotal, amount, grossSalary)
				.setDataSource(tableItemsTotal);
				
				
				//deducation total content
				StyleBuilder subtotalDeducationStyle = stl.style(Templates.columnStyle).setBackgroundColor(Color.decode("#ededed")).bold();
				subtotalDeducationStyle.setBorder(pen);
				
				IndPayrollPaySlipDeductionTotal ipsdt = new IndPayrollPaySlipDeductionTotal();
				ipsdt.setDeductionTotal("Total");
				ipsdt.setDeductionAmount(deducationTotal);
				tableItemsDeductionTotal.add(ipsdt);
				
				JasperReportBuilder earningsDeductionTable = report();
				earningsDeductionTable.setColumnTitleStyle(columnStyleSize)
				.setColumnStyle(subtotalDeducationStyle)
				.columns(deductionTotal, deductionAmount)
				.columnGrid(deductionTotal, deductionAmount)
				.setDataSource(tableItemsDeductionTotal);
				
				//earning and deducation table contenet
				IndPayrollPaySlipEarningAndDeducationTotal ipedt = new IndPayrollPaySlipEarningAndDeducationTotal();
				ipedt.setEarnings("Gross Salary");
				ipedt.setAmount(earningTotal);
				ipedt.setGrossSalary(earningTotal);
				ipedt.setDeduction("Net Salary");
				ipedt.setDeductionAmount(indPayrollDTO.getNetPay());
				tableItemsEarningDeductionTotal.add(ipedt);
				

				JasperReportBuilder earningsDeductionTotalTable = report();
				earningsDeductionTotalTable.setColumnTitleStyle(columnStyleSize)
				.setColumnStyle(subtotalStyle)
				.columns(earnings, amount, grossSalary,deduction,deductionAmount)
				.columnGrid(earnings, amount, grossSalary,deduction,deductionAmount)
				.setDataSource(tableItemsEarningDeductionTotal);
				
				String companyName;
				String candidateName;
				if(ppsi.getCompanyName() != null && ppsi.getCompanyName().equalsIgnoreCase("Y")) {
					companyName = orgLocation.getOrgLocationName();
				}else {
					companyName = "";
				}
				if(ppsi.getEmployeeName() != null && ppsi.getEmployeeName().equalsIgnoreCase("Y")) {
					candidateName = candidate.getFullName();
				}else {
					candidateName = "";
				}
				StyleBuilder footerCenteredStyle = stl.style().setPadding(2).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(12);
				StyleBuilder addressStyle = stl.style().setLeftPadding(2).setBottomPadding(10);
				report().setTemplate(Templates.reportTemplate)
						.setColumnStyle(columnStyle)
						.setSubtotalStyle(subtotalStyle)
						.title(
								cmp.horizontalList(	
								cmp.image(companyImage).setFixedDimension(60, 60),
								cmp.verticalList(
								cmp.text(companyName).setStyle(Templates.bold12CenteredStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
								cmp.text(companyAddress).setStyle(addressStyle).setHorizontalAlignment(HorizontalAlignment.LEFT))).setFixedWidth(450),
								cmp.verticalGap(10),
								cmp.text("Pay slip for : "+payslipDate).setStyle(Templates.boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER),
								cmp.text(candidateName).setStyle(Templates.boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER),
								cmp.horizontalList().add(
									cmp.hListCell(createEmployeeComponent1(indEmployeePayroll).setStyle(leftStyle)).heightFixedOnTop(),
									cmp.hListCell(createEmployeeComponent2(indEmployeePayroll,indPayrollDTO).setStyle(rightStyle)).heightFixedOnTop()),
								cmp.verticalGap(10),
								cmp.horizontalList().add(
								cmp.verticalList().add(
										cmp.subreport(earningsTable),
										cmp.subreport(earningsTotalTable)),
								cmp.verticalList().add(
										cmp.subreport(deductionTable),
										cmp.subreport(earningsDeductionTable))),
								cmp.verticalGap(10),
								cmp.subreport(earningsDeductionTotalTable),
								cmp.verticalGap(10))
						       .summary(
								cmp.text("Amount (in words) : "+commonUtil.convertToIndianCurrency(indPayrollDTO.getNetPay())).setStyle(Templates.rootStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
								cmp.verticalGap(40),
								cmp.text("This is a Computer Generated Pay Slip").setStyle(footerCenteredStyle))
						.toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
			return file;
	}
	
	
	private ComponentBuilder<?, ?> createEmployeeComponent2(IndEmployeePayroll indEmployeePayroll,INDPayrollDTO indPayrollDTO) throws Exception {
		Candidate candidate = indEmployeePayroll.getCandidate();
		AssociatePfEsiDetails ape = associatePfEsiService.findAssociatePfEsiByCandidateId(indEmployeePayroll.getCandidate().getId(), indEmployeePayroll.getCandidate().getOrgLocationId());
		PayrollPaySlipInd ppsi = null;
		if(indEmployeePayroll.getPayrollPaySlipInd() != null) {
			ppsi = indEmployeePayroll.getPayrollPaySlipInd();
		}else {
			ppsi = payrollPaySlipIndService.deletePermissionStatsuByDeletePermission(Constants.NO, indEmployeePayroll.getIndPaysheetFrequency().getOrgLocation().getId());
		}
		HorizontalListBuilder list = cmp.horizontalList();
		if(candidate.getPanNumber() != null) {
			if(ppsi.getEmployeePanNumber() != null && ppsi.getEmployeePanNumber().equalsIgnoreCase("Y")) {
				addEmployeeAttribute(list, "Income Tax Number (PAN) ", candidate.getPanNumber());
			}
		}
		if(ape != null && ape.getEligibleForPf() != null && ape.getEligibleForPf().equalsIgnoreCase("Y")) {
			if(ape.getUanNumber() != null) {
				if(ppsi.getEmployeeUanNumber() != null && ppsi.getEmployeeUanNumber().equalsIgnoreCase("Y")) {
					addEmployeeAttribute(list, "Universal Account Number (UAN) ", ape.getUanNumber());
				}
			}
			if(ape.getPfNumber() != null) {
				if(ppsi.getEmployeePfNumber() != null && ppsi.getEmployeePfNumber().equalsIgnoreCase("Y")) {
					addEmployeeAttribute(list, "PF account numbe  ", ape.getPfNumber());
				}
			}
		}
		if(ape != null && ape.getEligibleForEsi() != null) {
			if(ape.getEsinumber() != null) {
				if(ppsi.getEmployeeEsiNumber() != null && ppsi.getEmployeeEsiNumber().equalsIgnoreCase("Y")) {
					addEmployeeAttribute(list, "ESI Number ", ape.getEsinumber());
				}
			}
		}
		if(ppsi.getLeaveAttended() != null && ppsi.getLeaveAttended().equalsIgnoreCase("Y") && indPayrollDTO.getNoOfDays() != null) {
			addEmployeeAttribute(list, "Attended ", String.format("%.0f", indPayrollDTO.getNoOfDays())+" "+"Days");
		}
		if(ppsi.getLeaveLeaves() != null && ppsi.getLeaveLeaves().equalsIgnoreCase("Y") && indPayrollDTO.getNoOfLeaves() != null) {
			addEmployeeAttribute(list, "Leaves ", String.format("%.0f", indPayrollDTO.getNoOfLeaves()));
		}
		
		if(ppsi.getLeaveLop() != null && ppsi.getLeaveLop().equalsIgnoreCase("Y")) {
			if(indPayrollDTO.getLopAmount() != null) {
				addEmployeeAttribute(list, "LOP ", indPayrollDTO.getLopAmount().toString());
			}
		}
		
		if(ppsi.getRegularHours() != null && ppsi.getRegularHours().equalsIgnoreCase("Y")) {
			addEmployeeAttribute(list, "Regular hours ", indPayrollDTO.getRegularHours().toString());
		}
		
		if(ppsi.getOvertimeHours() != null && ppsi.getOvertimeHours().equalsIgnoreCase("Y")) {
			addEmployeeAttribute(list, "Overtime hours ", indPayrollDTO.getOvertimeHours().toString());
		}
		
		return cmp.verticalList(cmp.text("").setStyle(Templates.rootStyle),list);
	}
	
	private void addEmployeeAttribute(HorizontalListBuilder list, String label, String value) {
		if (value != null) {
			list.add(cmp.text(label + ":").setFixedColumns(18).setFixedWidth(180).setStyle(Templates.rootStyle), cmp.text(value).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(120)).newRow();
		}
	}
	
	private ComponentBuilder<?, ?> createEmployeeComponent1(IndEmployeePayroll indEmployeePayroll) throws Exception {
		Employees employee = indEmployeePayroll.getEmployee();
		Candidate candidate = indEmployeePayroll.getCandidate();
		PayrollPaySlipInd ppsi = null;
		if(indEmployeePayroll.getPayrollPaySlipInd() != null) {
			ppsi = indEmployeePayroll.getPayrollPaySlipInd();
		}else {
			ppsi = payrollPaySlipIndService.deletePermissionStatsuByDeletePermission(Constants.NO, indEmployeePayroll.getIndPaysheetFrequency().getOrgLocation().getId());
		}
		PaymentAccount paymentAccount = orgService.getCandidateAcitivatePaymentAccount(candidate.getId());

		HorizontalListBuilder list = cmp.horizontalList();
		if(employee.getEmployeeId() != null) {
			if(ppsi.getEmployeeId() != null && ppsi.getEmployeeId().equalsIgnoreCase("Y")) {
				addCustomerAttribute(list, "Employee Number ", employee.getEmployeeId());
			}
		}
		if(candidate.getDesignationType() != null) {
			if(ppsi.getEmployeeType() != null && ppsi.getEmployeeType().equalsIgnoreCase("Y")) {
				addCustomerAttribute(list, "Employee Type ", candidate.getDesignationType());
			}
		}
		if(candidate.getDesignation() != null) {
			if(ppsi.getEmployeeDesignation() != null && ppsi.getEmployeeDesignation().equalsIgnoreCase("Y")) {
				addCustomerAttribute(list, "Designation ", candidate.getDesignation());
			}
		}
		if(ppsi.getEmployeeLocation() != null && ppsi.getEmployeeLocation().equalsIgnoreCase("Y")) {
			if(candidate.getState() != null && candidate.getCity() != null) {
				addCustomerAttribute(list, "Location ", candidate.getCountry() +","+candidate.getState()+","+candidate.getCity());
			}else if(candidate.getState() == null && candidate.getCity() != null) {
				addCustomerAttribute(list, "Location ", candidate.getCountry() +","+candidate.getCity() );
			}else if(candidate.getState() != null && candidate.getCity() == null) {
				addCustomerAttribute(list, "Location ", candidate.getCountry() +","+candidate.getState());
			}else if(candidate.getState() == null && candidate.getCity() == null) {
				addCustomerAttribute(list, "Location ", candidate.getCountry());
			}
			
		}
		if(ppsi.getEmployeeBankDetails() != null && ppsi.getEmployeeBankDetails().equalsIgnoreCase("Y")) {
			if(paymentAccount != null) {
				addCustomerAttribute(list, "Bank Details ", paymentAccount.getAccountNumber() +"," +paymentAccount.getBankName());
			}
		}
		if(employee.getHireDate() != null) {
			if(ppsi.getEmployeeJoinDate() != null && ppsi.getEmployeeJoinDate().equalsIgnoreCase("Y")) {
				addCustomerAttribute(list, "Date of joining ", commonUtil.dateStrFromOrgDateFormatter(employee.getOrgLocations().getDateFormat(), employee.getHireDate()));
			}
		}
		return cmp.verticalList(cmp.text("").setStyle(Templates.rootStyle), list);
	}


	private void addCustomerAttribute(HorizontalListBuilder list, String label, String value) {
		if (value != null) {
			list.add(cmp.text(label + ":").setFixedColumns(10).setFixedWidth(120).setStyle(Templates.rootStyle), cmp.text(value).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(130)).newRow();
		}
	}


	public File getIndPayrollRegisterReportsPdf(ResponseList<IndEmployeePayroll> payResponseList, OrgLocations orgLocations) throws IOException {
		 File file = null;
			try {
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("payrollRegister_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
				file.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
				
				report().setTemplate(Templates.reportTemplate)
						.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
						.columns(
								col.column("Employee Id", "employeeId", type.stringType()),
								col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("Current Hours", "currentHours", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("Grass Pay", "grossPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("YTD Taxes", "ytdTaxes", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("Deducation", "deducation", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("Net Pay", "netPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
								col.column("YTD Net Pay", "ytdNetPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
						.pageFooter(Templates.footerComponent)
						.setDataSource(createPdfDataSource(payResponseList)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
			return file;
	}


	private DRDataSource createPdfDataSource(ResponseList<IndEmployeePayroll> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "currentHours", "grossPay", "ytdTaxes", "deducation", "netPay", "ytdNetPay");
		for(IndEmployeePayroll iep:payResponseList.getData()) {
			
			String employeeId = "--";
			if(iep.getEmployee().getEmployeeId() != null) {
				employeeId = iep.getEmployee().getEmployeeId();
			} 
			BigDecimal currentHours = new BigDecimal("0");
			if(iep.getRegularHours() != null) {
				currentHours.add(new BigDecimal(iep.getRegularHours()));
			}
			if(iep.getOverTimeHours() != null) {
				currentHours.add(new BigDecimal(iep.getOverTimeHours()));
			}
			dataSource.add(employeeId,iep.getCandidate().getFullName(), iep.getEmployee().getDepartmentId().getDepartmentName(), currentHours,iep.getGrossPay(),new BigDecimal(0.00),iep.getGrossPay().subtract(iep.getNetPay()),iep.getNetPay(),new BigDecimal(0.00));
		}
		return dataSource;
	}


	public File getIndPayrollBankTransferReportsPdf(ResponseList<IndEmployeePayroll> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollBankTransfer_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "employeeId", type.stringType()),
							col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Account Numaber", "acoountNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Bank Name", "bankName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("IFSC Code", "ifscCode", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Amount", "amount", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createBankTransferDataSource(payResponseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	

	public File getIndPayrollBankTransferReportsExcel(ResponseList<IndEmployeePayroll> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollBankTransfer_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "employeeId", type.stringType()),
							col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Account Numaber", "acoountNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Bank Name", "bankName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("IFSC Code", "ifscCode", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Amount", "amount", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createBankTransferDataSource(payResponseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}

	
	private DRDataSource createBankTransferDataSource(ResponseList<IndEmployeePayroll> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "acoountNumber", "bankName", "ifscCode", "amount");
       for(IndEmployeePayroll iep:payResponseList.getData()) {
			String employeeId = "--";
			String accontNumber = "--";
			String bankName  = "--";
			String ifscCode = "--";
			
			if(iep.getEmployee().getEmployeeId() != null) {
				employeeId = iep.getEmployee().getEmployeeId();
			} 
			 if(iep.getPaymentAccount() != null) {
				 ifscCode = iep.getPaymentAccount().getRoutingNumber();
				 accontNumber = iep.getPaymentAccount().getAccountNumber();
				 bankName = iep.getPaymentAccount().getBankName();
			 }
			dataSource.add(employeeId,iep.getCandidate().getFullName(), iep.getEmployee().getDepartmentId().getDepartmentName(), accontNumber,bankName,ifscCode,iep.getNetPay());
		}
		
		return dataSource;
	}


	public File getIndPayrollYTDPdf(ResponseList<IndEmployeePayroll> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollYTDSummary_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "employeeId", type.stringType()),
							col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("No Of Working Days", "noOfWorkingDays", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("No Of Leaves", "noOfLeaves", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Gross Pay", "grossPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Deducations", "deducatons", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Net Pay", "netPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createYTDSummaryDataSource(payResponseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	public File getIndPayrollYTDExcel(ResponseList<IndEmployeePayroll> payResponseList, OrgLocations orgLocations)throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollYTDSummary_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "employeeId", type.stringType()).setColumns(15),
							col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("No Of Working Days", "noOfWorkingDays", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(30),
							col.column("No Of Leaves", "noOfLeaves", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Gross Pay", "grossPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Deducations", "deducatons", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Net Pay", "netPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createYTDSummaryDataSource(payResponseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	private DRDataSource createYTDSummaryDataSource(ResponseList<IndEmployeePayroll> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "noOfWorkingDays", "noOfLeaves", "grossPay", "deducatons", "netPay");
		Map<Long,List<IndEmployeePayroll>> mapList = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayroll::getCandidateId,Collectors.toList()));
			for (Map.Entry<Long, List<IndEmployeePayroll>> entry : mapList.entrySet()) {
				IndEmployeePayroll iepdt = entry.getValue().get(0);
				double noOfLeaves = entry.getValue().stream().mapToDouble(i->i.getNoOfLeaves().doubleValue()).sum();
				double noOfDays = entry.getValue().stream().mapToDouble(i->i.getNoOfDays().doubleValue()).sum();
				double doubleGrossPay = entry.getValue().stream().mapToDouble(i->i.getGrossPay().doubleValue()).sum();
				double doubleNetPay = entry.getValue().stream().mapToDouble(i->i.getNetPay().doubleValue()).sum();
				BigDecimal days = new BigDecimal(noOfDays);
				BigDecimal leaves = new BigDecimal(noOfLeaves);
				BigDecimal grossPay = new BigDecimal(doubleGrossPay);
				BigDecimal netPay = new BigDecimal(doubleNetPay);
				String employeeId = "--";
				if(iepdt.getEmployee().getEmployeeId() != null) {
					employeeId = iepdt.getEmployee().getEmployeeId();
				} 
				dataSource.add(employeeId,iepdt.getCandidate().getFullName(), iepdt.getEmployee().getDepartmentId().getDepartmentName(), days,leaves,grossPay,grossPay.subtract(netPay),netPay);
			}
			return dataSource;
	}


	public File getIndPayrollAnnualTaxPdf(ResponseList<IndEmployeePayroll> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollAnnualTax_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "employeeId", type.stringType()),
							col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Gross Pay", "grossPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("PF", "pf", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("PT", "pt", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("TDS", "tds", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Net Pay", "netPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createAnnualTaxDataSource(payResponseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	public File getIndPayrollAnnualTaxExcel(ResponseList<IndEmployeePayroll> payResponseList,OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollAnnualTax_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "employeeId", type.stringType()).setColumns(15),
							col.column("Employee Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Gross Pay", "grossPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("PF", "pf", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("PT", "pt", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("TDS", "tds", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Net Pay", "netPay", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createAnnualTaxDataSource(payResponseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	private DRDataSource createAnnualTaxDataSource(ResponseList<IndEmployeePayroll> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "grossPay", "pf", "pt", "tds", "netPay");
		Map<Long,List<IndEmployeePayroll>> mapList = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayroll::getCandidateId,Collectors.toList()));
			for (Map.Entry<Long, List<IndEmployeePayroll>> entry : mapList.entrySet()) {
				IndEmployeePayroll iepdt = entry.getValue().get(0);
				
				double professionalTax = entry.getValue().stream().mapToDouble(i->i.getProfessionalTax().doubleValue()).sum();
				double doubleTds = entry.getValue().stream().mapToDouble(i->i.getTds().doubleValue()).sum();
				double doubleGrossPay = entry.getValue().stream().mapToDouble(i->i.getGrossPay().doubleValue()).sum();
				double doubleNetPay = entry.getValue().stream().mapToDouble(i->i.getNetPay().doubleValue()).sum();
				BigDecimal pt = new BigDecimal(professionalTax);
				BigDecimal tds = new BigDecimal(doubleTds);
				BigDecimal grossPay = new BigDecimal(doubleGrossPay);
				BigDecimal netPay = new BigDecimal(doubleNetPay);
				
				String employeeId = "--";
				if(iepdt.getEmployee().getEmployeeId() != null) {
					employeeId = iepdt.getEmployee().getEmployeeId();
				} 
				dataSource.add(employeeId,iepdt.getCandidate().getFullName(), iepdt.getEmployee().getDepartmentId().getDepartmentName(), grossPay,grossPay.subtract(netPay),pt,tds,netPay);
			}
			return dataSource;
	}


	public File getIndPayrollESIPdf(ResponseList<IndEmployeePayrollDTO> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
			 StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("payrollEsi_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
				file.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "employeeId", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Associate Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("ESI Number", "esiNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Associate Contribution(INR)", "asContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Emplpyer Contribution(INR)", "empContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Total(INR)", "total", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER)).ignorePageWidth()
					.pageFooter(Templates.footerComponent)
					.setDataSource(createEsiDataSource(payResponseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	public File getIndPayrollESIExcel(ResponseList<IndEmployeePayrollDTO> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollEsi_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenRows(true)
                    .setRemoveEmptySpaceBetweenColumns(true);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "employeeId", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Associate Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("ESI Number", "esiNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Associate Contribution(INR)", "asContribution",  type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Emplpyer Contribution(INR)", "empContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Total(INR)", "total", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER)).ignorePageWidth()
					.setDataSource(createEsiDataSource(payResponseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}

	private DRDataSource createEsiDataSource(ResponseList<IndEmployeePayrollDTO> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "esiNumber", "asContribution", "empContribution", "total");
	    Map<BigInteger, List<IndEmployeePayrollDTO>> canMap = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO::getCanId,Collectors.toList()));
	    for (Map.Entry<BigInteger, List<IndEmployeePayrollDTO>> entry : canMap.entrySet())  {
	    	 IndEmployeePayrollDTO dto = entry.getValue().get(0);
	    	String employeeId = "--";
	    	String candidateName = "--";
	    	String departmentName = "--";
	    	if(dto.getEmployeeid() != null) {
	    		employeeId = dto.getEmployeeid();
	    	}
	    	if(dto.getLastName() != null) {
	    		candidateName  = dto.getFirstName() +" "+ dto.getLastName();
	    	  }else {
	    		  candidateName = dto.getFirstName();
	          }
	    	if(dto.getDepartmentName() != null) {
	    		departmentName = dto.getDepartmentName();
	    	}
	    	
	    	  Double employeeESI = 0d;
			  Double employerESI = 0d;
			  for(IndEmployeePayrollDTO  ipd :entry.getValue()) {
				  if(ipd.getEmployeeEsi() != null && ipd.getPayHeadAmount().longValue() > 0) {
					  employeeESI = employeeESI + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployeeEsi())/100);
				  }
				  if(ipd.getEmployerEsi() != null && ipd.getPayHeadAmount().longValue() > 0) {
					  employerESI = employerESI + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployerEsi())/100);
				  }
			  }
	    	Double total = employeeESI + employerESI;
	    	
			dataSource.add(employeeId,candidateName, departmentName, dto.getEsiNumber(),BigDecimal.valueOf(employeeESI),BigDecimal.valueOf(employerESI),BigDecimal.valueOf(total));

	    }
	 return dataSource;
	}


	public File getIndPayrollPFPdf(ResponseList<IndEmployeePayrollDTO> payResponseList, OrgLocations orgLocations)throws Exception {
		File file = null;
		try {
			 StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("payrollPf_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
				file.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "employeeId", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Associate Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("PF Number", "pfNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("UAN Number", "uanNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Associate Contribution(INR)", "asContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Emplpyer Contribution(INR)", "empContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Total", "total(INR)", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER)).ignorePageWidth()
					.pageFooter(Templates.footerComponent)
					.setDataSource(createPfDataSource(payResponseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	public File getIndPayrollPFExcel(ResponseList<IndEmployeePayrollDTO> payResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollPf_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(true)
                    .setRemoveEmptySpaceBetweenRows(true)
                    .setRemoveEmptySpaceBetweenColumns(true);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "employeeId", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Associate Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("PF Number", "pfNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(20),
							col.column("UAN Number", "uanNumber", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(20),
							col.column("Associate Contribution(INR)", "asContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Emplpyer Contribution(INR)", "empContribution", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Total(INR)", "total", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER)).ignorePageWidth()
					.setDataSource(createPfDataSource(payResponseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}


	private DRDataSource createPfDataSource(ResponseList<IndEmployeePayrollDTO> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "pfNumber","uanNumber", "asContribution", "empContribution", "total");
	    Map<BigInteger, List<IndEmployeePayrollDTO>> canMap = payResponseList.getData().stream().collect(Collectors.groupingBy(IndEmployeePayrollDTO::getCanId,Collectors.toList()));
	    for (Map.Entry<BigInteger, List<IndEmployeePayrollDTO>> entry : canMap.entrySet())  {
	    	 IndEmployeePayrollDTO dto = entry.getValue().get(0);
	    	String employeeId = "--";
	    	String candidateName = "--";
	    	String departmentName = "--";
	    	if(dto.getEmployeeid() != null) {
	    		employeeId = dto.getEmployeeid();
	    	}
	    	if(dto.getLastName() != null) {
	    		candidateName  = dto.getFirstName() +" "+ dto.getLastName();
	    	  }else {
	    		  candidateName = dto.getFirstName();
	          }
	    	if(dto.getDepartmentName() != null) {
	    		departmentName = dto.getDepartmentName();
	    	}
	    	
	    	  Double employeePF = 0d;
			  Double employerPF = 0d;
			  for(IndEmployeePayrollDTO  ipd :entry.getValue()) {
				  if(ipd.getEmployeePf() != null && ipd.getPayHeadAmount().longValue() > 0) {
					  employeePF = employeePF + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployeePf())/100);
				  }
				  if(ipd.getEmployerPf() != null && ipd.getPayHeadAmount().longValue() > 0) {
					  employerPF = employerPF + ((ipd.getPayHeadAmount().doubleValue() * ipd.getEmployerPf())/100);
				  }
			  }
	    	Double total = employeePF + employerPF;
	    	
			dataSource.add(employeeId,candidateName, departmentName, dto.getPfNumber(),dto.getUanNumber(),BigDecimal.valueOf(employeePF),BigDecimal.valueOf(employerPF),BigDecimal.valueOf(total));

	    }
	 return dataSource;
	}


	public File getIndPayrollSalaryRegisterPdf(ResponseList<IndEmployeePayrollDTO> payResponseList,OrgLocations orgLocations)throws Exception {
		File file = null;
		try {
			 StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("payrollSalaryRegister_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
				file.setReadable(true);
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "employeeId", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Associate Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Effective Date", "effectiveDate", type.dateDayType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Increase %", "increase", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Annual CTC(INR)", "annualCTC", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER)).ignorePageWidth()
					.pageFooter(Templates.footerComponent)
					.setDataSource(createSalaryRegisterDataSource(payResponseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}
	

	public File getIndPayrollSalaryRegisterExcel(ResponseList<IndEmployeePayrollDTO> payResponseList,OrgLocations orgLocations)throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("payrollSalaryRegister_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(true)
                    .setRemoveEmptySpaceBetweenRows(true)
                    .setRemoveEmptySpaceBetweenColumns(true);
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "employeeId", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Associate Name", "employeeName", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Department", "department", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Effective Date", "effectiveDate", type.dateDayType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Increase %", "increase", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setColumns(15),
							col.column("Annual CTC(INR)", "annualCTC", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.CENTER)).ignorePageWidth()
					.setDataSource(createSalaryRegisterDataSource(payResponseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}

	
	private DRDataSource createSalaryRegisterDataSource(ResponseList<IndEmployeePayrollDTO> payResponseList) {
		DRDataSource dataSource = new DRDataSource("employeeId","employeeName", "department", "effectiveDate","increase", "annualCTC");
				for(IndEmployeePayrollDTO asi:payResponseList.getData()) {
   					String employeeId = "--";
   					String candidateName = "--";
   					String incresePercentage = "--";
   					if(!StringUtils.isEmpty(asi.getEmployeeid())) {
   						employeeId = asi.getEmployeeid();
   					}
   					if(!StringUtils.isEmpty(asi.getLastName())) {
   						candidateName = asi.getFirstName()+" "+asi.getLastName();
   					}else {
   						candidateName = asi.getFirstName();
   					}
   					if(asi.getIncresePercentage() != null && asi.getIncresePercentage() != "" && asi.getIncresePercentage() !="undefined") {
   						incresePercentage = asi.getIncresePercentage();
   					}
   					
   					dataSource.add(employeeId,candidateName, asi.getDepartmentName(), asi.getEffectiveDate(),incresePercentage,asi.getAnnualSalary());

   			  }
		return dataSource;
	}

	
}