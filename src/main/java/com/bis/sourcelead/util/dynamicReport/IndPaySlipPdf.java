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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPayrollPaySlipDeduction;
import com.bis.sourcelead.payroll.dto.IndPayrollPaySlipDeductionTotal;
import com.bis.sourcelead.payroll.dto.IndPayrollPaySlipEarning;
import com.bis.sourcelead.payroll.dto.IndPayrollPaySlipEarningAndDeducationTotal;
import com.bis.sourcelead.payroll.dto.IndPayrollPaySlipEarningTotal;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.INDPayrollCommonUtil;
import com.ibm.icu.text.SimpleDateFormat;

import edu.emory.mathcs.backport.java.util.LinkedList;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
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
import net.sf.jasperreports.engine.JRDataSource;

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
		   			List<AssociatePayrollPayheadHistoryDTO> earningList = associatePayrollPayheadDTOList.stream().filter(app -> (app.getPayrollPayheadType().equals("Earnings") && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0))).collect(Collectors.toList());
					for(AssociatePayrollPayheadHistoryDTO apph:earningList) {
						IndPayrollPaySlipEarning ippe = new IndPayrollPaySlipEarning();
			   			ippe.setEarnings(apph.getPayrollPayheadName());
			   			ippe.setAmount(apph.getMonthly());
			   			ippe.setGrossSalary(apph.getMonthly());
			   			earningTotal = earningTotal.add(apph.getMonthly());
			   			tableItems1.add(ippe);
					}
					
					//Deducation list
					List<AssociatePayrollPayheadHistoryDTO> decuctionList =  associatePayrollPayheadDTOList.stream().filter(app -> app.getPayrollPayheadType().equals("Deduction") && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0)).collect(Collectors.toList());
					for(AssociatePayrollPayheadHistoryDTO apph:decuctionList) {
						IndPayrollPaySlipDeduction ippd = new IndPayrollPaySlipDeduction();
						ippd.setDeduction(apph.getPayrollPayheadName());
						ippd.setDeductionAmount(apph.getMonthly());
						deducationTotal = deducationTotal.add(apph.getMonthly());
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

	
	 
	
}