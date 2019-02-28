package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AccountDeductionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.AccountDeductionsDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.PayrollHelper;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.InvoiceDataService;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AccountDeductionsServiceImpl implements AccountDeductionsService{

	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsServiceImpl.class);
	
	@Autowired
	private AccountDeductionsDao  accountDeductionsDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	PayrollHelper payrollHelper;
	
	@Autowired
	InvoiceDataService invoiceDataService;
	
	@Autowired
	WageTaxSummaryService wageTaxSummaryService;
	
	@Autowired
	UserPayrollService userPayrollService;
	
	@Autowired
	BisUserMappingService  bisUserMappingService;
	
	@Override
	public Integer saveAccountDeductions(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception {
		
		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		if (fileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			workbook = (Workbook) new HSSFWorkbook();
		}

		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			int version;
			version = getAccountDeductionsVersionByLocationId(orgLocation.getId());
			version += 1;
			for (int i = 0; i < 1; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				int isValid = 0;
				if(i == 0){
					isValid =  payrollHelper.isValidTemplateForAccountDeductions(rowIterator);
				}
				if(isValid == 1){
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
				/*	if (true) {*/
						if (row.getRowNum() > 0) { // Excluding column names
							AccountDeductions  accountDeductions = new AccountDeductions();
							Set<AccountDeductionsDetails> accountDeductionsDetailsSet = new HashSet<>();
							float  deductionAmount =0f;
							// start AccountDeductions
							
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empId = getCellValueAsString(row.getCell(0), fileName);
								accountDeductions.setEmployeeId(empId.split("\\.")[0]);
								accountDeductions.setVersion(version);
							} 
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								String empName = getCellValueAsString(row.getCell(1), fileName);
								accountDeductions.setEmployeeName(empName);
							} 
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								deductionAmount = Float.parseFloat(getCellValueAsString(row.getCell(2), fileName));
								accountDeductions.setDedAmount(deductionAmount);
							} else{
								accountDeductions.setDedAmount(deductionAmount);
							}
							
							// end AccountDeductions
							
							// Start AccountDeductionsDetails
							
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								float march15ThAMount = Float.parseFloat(getCellValueAsString(row.getCell(3), fileName));
								AccountDeductionsDetails accountDeductionsDetails= getAccountDeductionDetails(march15ThAMount,accountDeductions,orgLocation,user,row.getCell(3),3);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								float april1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(april1stAmount,accountDeductions,orgLocation,user,row.getCell(4),4);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								float april15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(april15thAmount,accountDeductions,orgLocation,user,row.getCell(5),5);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								float may1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(may1stAmount,accountDeductions,orgLocation,user,row.getCell(6),6);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								float may15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(may15thAmount,accountDeductions,orgLocation,user,row.getCell(7),7);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								float june1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(june1stAmount,accountDeductions,orgLocation,user,row.getCell(8),8);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								float june15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(june15thAmount,accountDeductions,orgLocation,user,row.getCell(9),9);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float july1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(july1stAmount,accountDeductions,orgLocation,user,row.getCell(10),10);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float july15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(july15thAmount,accountDeductions,orgLocation,user,row.getCell(11),11);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
								float aug1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(12), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(aug1stAmount,accountDeductions,orgLocation,user,row.getCell(12),12);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
								float aug15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(13), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(aug15thAmount,accountDeductions,orgLocation,user,row.getCell(13),13);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							
							if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(14).toString())) {
								float sep1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(14), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(sep1stAmount,accountDeductions,orgLocation,user,row.getCell(14),14);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(15) != null && !"".equalsIgnoreCase(row.getCell(15).toString())) {
								float sep15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(15), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(sep15thAmount,accountDeductions,orgLocation,user,row.getCell(15),15);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							
							if (row.getCell(16) != null && !"".equalsIgnoreCase(row.getCell(16).toString())) {
								float oct1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(16), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(oct1stAmount,accountDeductions,orgLocation,user,row.getCell(16),16);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(17) != null && !"".equalsIgnoreCase(row.getCell(17).toString())) {
								float oct15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(17), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(oct15thAmount,accountDeductions,orgLocation,user,row.getCell(17),17);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							
							if (row.getCell(18) != null && !"".equalsIgnoreCase(row.getCell(18).toString())) {
								float nov1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(18), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(nov1stAmount,accountDeductions,orgLocation,user,row.getCell(18),18);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(19) != null && !"".equalsIgnoreCase(row.getCell(19).toString())) {
								float nov15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(19), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(nov15thAmount,accountDeductions,orgLocation,user,row.getCell(19),19);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							
							if (row.getCell(20) != null && !"".equalsIgnoreCase(row.getCell(20).toString())) {
								float dec1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(20), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(dec1stAmount,accountDeductions,orgLocation,user,row.getCell(20),20);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(21) != null && !"".equalsIgnoreCase(row.getCell(21).toString())) {
								float dec15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(21), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(dec15thAmount,accountDeductions,orgLocation,user,row.getCell(21),21);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							// END AccountDeductionsDetails
							
							if(jsonObj.has("fromDate") && jsonObj.getString("fromDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("fromDate"))){
								accountDeductions.setFromDate(conversionUtil.getDateFromString(jsonObj.getString("fromDate")));
							}
							if(jsonObj.has("toDate") && jsonObj.getString("toDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("toDate"))){
								accountDeductions.setToDate(conversionUtil.getDateFromString(jsonObj.getString("toDate")));
							}
							if(jsonObj.has("batchNumber")){
								if(jsonObj.getString("batchNumber") !=null && !"".equalsIgnoreCase(jsonObj.getString("batchNumber"))){
									accountDeductions.setBatchNumber(jsonObj.getString("batchNumber"));
								}
							}
							accountDeductions.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							accountDeductions.setCreatedBy(user.getUserCode());
							accountDeductions.setCreatedDate(new Date());
							accountDeductions.setUpdatedBy(user.getUserCode());
							accountDeductions.setUpdatedDate(new Date());
							accountDeductions.setOrgLocationId(orgLocation);
							accountDeductions.setStatus(Constants.MEMBER_ACTIVE);
							accountDeductions.setAccountDeductionsDetails(accountDeductionsDetailsSet);
							AccountDeductions savedAccountDeductions = saveAccountDetails(accountDeductions);
							if(savedAccountDeductions != null){
								count++;
							}
//							accountDeductionsList.add(accountDeductions);
						}
					}
				}else{
					return count;
				}
				}
			
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}
		
		return count;
		
		
	}


	private AccountDeductions saveAccountDetails(AccountDeductions accountDeductions) throws Exception {
		return accountDeductionsDao.saveAccountDeductions(accountDeductions);
	}

	private AccountDeductionsDetails getAccountDeductionDetails(float dedAmount, AccountDeductions accountDeductions, OrgLocations orgLocation,User user, Cell cell,int cellNuber) {
		AccountDeductionsDetails accountDeductionsDetails = new AccountDeductionsDetails();
		accountDeductionsDetails.setDedAmountPerCycle(dedAmount);
		accountDeductionsDetails.setAccountDeductions(accountDeductions);
		accountDeductionsDetails.setOrgLocationId(orgLocation);
		accountDeductionsDetails.setPayCycleName(cell.getSheet().getRow(0).getCell(cellNuber).getRichStringCellValue().toString());
		accountDeductionsDetails.setCreatedBy(user.getUserCode());
		accountDeductionsDetails.setCreatedDate(new Date());
		accountDeductionsDetails.setUpdatedBy(user.getUserCode());
		accountDeductionsDetails.setUpdatedDate(new Date());
		accountDeductionsDetails.setOrgLocationId(orgLocation);
		accountDeductionsDetails.setStatus(Constants.MEMBER_ACTIVE);
		return accountDeductionsDetails;
	}

/*	private String getCellValueAsString(Cell cell, String fileName) {
		String cellValue = "";
		if(fileName.toLowerCase().endsWith("xls")){
				HSSFCell hssfCell = (HSSFCell) cell;
				if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					hssfCell.setCellType(Cell.CELL_TYPE_STRING);
					String hssfCellValue = hssfCell.getStringCellValue();
					if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
						cellValue = hssfCellValue;
					} 
				}else{
					cellValue = cell.getStringCellValue();
				}
			}else{
				XSSFCell xssfCell = (XSSFCell) cell;
				if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					xssfCell.setCellType(Cell.CELL_TYPE_STRING);
					String xssfCellValue = xssfCell.getStringCellValue();
					if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
						cellValue = xssfCellValue;
					} 
				}else{
					cellValue = cell.getStringCellValue();
				}
			}
			return cellValue;
		}
*/
	
	private String getCellValueAsString(Cell cell, String fileName) {
		String cellValue = "";
		double xssfCellValue = 0;
		if (fileName.toLowerCase().endsWith("xls")) {
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				xssfCellValue = hssfCell.getNumericCellValue();
				cellValue = String.valueOf(xssfCellValue);
			}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA && hssfCell.getCachedFormulaResultType() == HSSFCell.CELL_TYPE_NUMERIC){
				cellValue = String.valueOf(hssfCell.getNumericCellValue());
			}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA && hssfCell.getCachedFormulaResultType() == HSSFCell.CELL_TYPE_STRING){
				cellValue = String.valueOf(hssfCell.getStringCellValue());	
			}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				cellValue = cell.getStringCellValue();
			}
		} else {
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				xssfCellValue = xssfCell.getNumericCellValue();
				cellValue = String.valueOf(xssfCellValue);
			} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA
					&& xssfCell.getCachedFormulaResultType() == XSSFCell.CELL_TYPE_NUMERIC) {
				cellValue = String.valueOf(xssfCell.getNumericCellValue());
			} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA
					&& xssfCell.getCachedFormulaResultType() == XSSFCell.CELL_TYPE_STRING) {
				cellValue = String.valueOf(xssfCell.getStringCellValue());
			} else if(xssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}
	@Override
	public ResponseList<AccountDeductions> getAccountDeductionsListByOrgLocationId(OrgLocations orgLocation, AccountDeductionsDTO accountDeductionsDTO) throws Exception{
		return accountDeductionsDao.getAccountDeductionsListByOrgLocationId(orgLocation, accountDeductionsDTO); 
		
	}

	@Override
	public List<AccountDeductionsDetails> getDeductionsDetailsbyId(Long deductionId)
	{
		return accountDeductionsDao.getDeductionsDetailsbyId(deductionId);
	}


	@Override
	public AccountDeductions getDeductionsbyId(Long deductionId)
	{
		return accountDeductionsDao.getDeductionsbyId(deductionId);
	}


	@Override
	public AccountDeductions saveAccountDeductions(AccountDeductions accountDeductions) throws Exception
	{
		return accountDeductionsDao.saveAccountDeductions(accountDeductions);
	}


	@Override
	public List<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)
			throws Exception {
		return accountDeductionsDao.getAccountDeductionsByEmployeeId(orgLocations,empId);
	}


	@Override
	public PayrollBalance getBalance(Long empId, OrgLocations orgLocations,String loginUserRole) throws Exception {
		PayrollBalance payrollBalance = new PayrollBalance();
		try {
			DecimalFormat df = new DecimalFormat("#0.00");
			
			double billRateIncome = 0; 
			double payRateIncome = 0;
			double due = 0; 
			double directDepositGross = 0; // A
			double directDepositNet = 0; // B 
			double processedWithCheckOptionGross = 0; // C
			double processedWithCheckOptionNet = 0; // D
			double actualChecksDepositedNet = 0; // E
			double manualAdvance = 0; //  F
			double generalExpenses = 0; // G
			double vendorPayment = 0; // H
			double healthcareInsurance = 0; // I
			double immigration = 0; // J
			double directDepositAdvanceNegetive = 0; // K
			double directDepositDeductionPositive = 0; // L 
			
			
			if(empId !=null){
				 List<InvoiceData> invoiceDataList = invoiceDataService.getInvoiceDetailsByEmpId(empId, orgLocations);
				 if(invoiceDataList !=null && invoiceDataList.size() >0){
					 if("Employee".equalsIgnoreCase(loginUserRole)){
						 
						 for(InvoiceData i : invoiceDataList) {
							 if(i.getPayRate() != null && "Paid".equalsIgnoreCase(i.getArPaid())) {
								 try {
									 double payRate = Double.parseDouble(i.getPayRate());
									 double qty = Double.parseDouble(i.getQty());
									 payRateIncome = payRateIncome + (payRate * qty);
								} catch (Exception e) {
									System.out.println(i.getPayRate());
									System.out.println(i.getQty());
									e.printStackTrace();
								}
							 }
						 }
						 
//						 payRateIncome = invoiceDataList.stream().filter(i->i.getPayRate() !=null && "Paid".equalsIgnoreCase(i.getArPaid())).mapToDouble(i->Double.parseDouble(i.getPayRate())*Double.parseDouble(i.getQty())).sum();
						 due = invoiceDataList.stream().filter(i->(i.getAmount() !=null && "Unpaid".equalsIgnoreCase(i.getArPaid()))).mapToDouble(i-> Double.parseDouble(i.getPayRate())*Double.parseDouble(i.getQty())).sum();
					 }else if("Admin".equalsIgnoreCase(loginUserRole)){
						 
						 for(InvoiceData i : invoiceDataList) {
							 if(i.getAmount() != null && "Paid".equalsIgnoreCase(i.getArPaid())) {
								try {
									 double rate = Double.parseDouble(i.getRate());
									 double qty = Double.parseDouble(i.getQty());
									 billRateIncome = billRateIncome + (rate * qty);
								} catch (Exception e) {
									System.out.println(i.getRate());
									System.out.println(i.getQty());
									e.printStackTrace();
								}
							 }
						 }
						 
//						 billRateIncome = invoiceDataList.stream().filter(i->(i.getAmount() !=null && "Paid".equalsIgnoreCase(i.getArPaid()))).mapToDouble(i-> Double.parseDouble(i.getRate())*Double.parseDouble(i.getQty())).sum();
//						 payRateIncome = invoiceDataList.stream().filter(i->i.getPayRate() !=null && "Paid".equalsIgnoreCase(i.getArPaid())).mapToDouble(i->Double.parseDouble(i.getPayRate())*Double.parseDouble(i.getQty())).sum();
						 // we are calculating admin due amount
						 due = invoiceDataList.stream().filter(i->(i.getAmount() !=null && "Unpaid".equalsIgnoreCase(i.getArPaid()))).mapToDouble(i-> Double.parseDouble(i.getRate())*Double.parseDouble(i.getQty())).sum();
					 }
				 }
				 
				 List<WageTaxSummary> wagesAndTaxList = wageTaxSummaryService.getWagesAndTaxListByEmployeeId(orgLocations,empId);
				if(wagesAndTaxList !=null && wagesAndTaxList.size() >0){
					 for(WageTaxSummary wageTaxSummary : wagesAndTaxList){
						 if(wageTaxSummary.getCheckNo() !=null && !"".equals(wageTaxSummary.getCheckNo())){
							if(Constants.DIR_DEP.equalsIgnoreCase(wageTaxSummary.getCheckNo())){
								directDepositGross = directDepositGross + wageTaxSummary.getGross();
								directDepositNet = directDepositNet + wageTaxSummary.getNet();
							}
							if(!Constants.DIR_DEP.equalsIgnoreCase(wageTaxSummary.getCheckNo())){
								processedWithCheckOptionGross = processedWithCheckOptionGross + wageTaxSummary.getGross();
								processedWithCheckOptionNet = processedWithCheckOptionNet + wageTaxSummary.getNet();
							}
						 }
						 if(String.valueOf(wageTaxSummary.getDeds()).contains("-")) {
							 directDepositAdvanceNegetive = directDepositAdvanceNegetive + wageTaxSummary.getDeds();
						 }else {
							 directDepositDeductionPositive = directDepositDeductionPositive + wageTaxSummary.getDeds();
						 }
					 }	
				}
				
				 
				List<Checks> checksList =userPayrollService.getChecksListByEmployeeId(empId, orgLocations);
				if(checksList !=null && checksList.size() >0){
					actualChecksDepositedNet = checksList.stream().mapToDouble(c->c.getPayrollChecks()).sum();
					manualAdvance = checksList.stream().mapToDouble(c->c.getAdvance()).sum();
					 generalExpenses = checksList.stream().mapToDouble(c->c.getGeneralExpenses()).sum();
					 vendorPayment = checksList.stream().mapToDouble(c->c.getVendorPayments()).sum();
					 
					 healthcareInsurance = checksList.stream().filter(m->(m.getHealthInsurance() !=null) ).mapToDouble(m->Double.parseDouble(m.getHealthInsurance())).sum();
					 immigration = checksList.stream().filter(m->(m.getImmigration() !=null) ).mapToDouble(m->Double.parseDouble(m.getImmigration())).sum();
				}
				
//				List<BisUserMapping> masterList = bisUserMappingService.getMasterListByEmpId(empId, orgLocations);
//				if(masterList !=null && masterList.size() >0){
//					  healthcareInsurance = masterList.stream().mapToDouble(m->m.getInsurance()).sum();
//					 immigration = masterList.stream().filter(m->(m.getImmigration() !=null) ).mapToDouble(m->Double.parseDouble(m.getImmigration())).sum();	
//				}
				
			}
			 if("Employee".equalsIgnoreCase(loginUserRole)){
				 payrollBalance.setIncome(df.format(payRateIncome));
			 }else if("Admin".equalsIgnoreCase(loginUserRole)){
				 payrollBalance.setIncome(df.format(billRateIncome));
				 payrollBalance.setEmpIncome(df.format(payRateIncome));
			 }
			payrollBalance.setDirectDepositGross(df.format(directDepositGross));
			payrollBalance.setDirectDepositNet(df.format(directDepositNet));
			payrollBalance.setProcessedWithCheckOptionGross(df.format(processedWithCheckOptionGross));
			payrollBalance.setProcessedWithCheckOptionNet(df.format(processedWithCheckOptionNet));
			payrollBalance.setActualChecksDepositedNet(df.format(actualChecksDepositedNet));
			payrollBalance.setManualAdvance(df.format(manualAdvance));
			payrollBalance.setGeneralExpenses(df.format(generalExpenses));
			payrollBalance.setVendorPayment(df.format(vendorPayment));
			payrollBalance.setHealthcareInsurance(df.format(healthcareInsurance));
			payrollBalance.setImmigration(df.format(immigration));
			double ddAdvanceVal = 0;
			if(directDepositAdvanceNegetive < 0) {
				ddAdvanceVal = -(directDepositAdvanceNegetive);
			}else {
				ddAdvanceVal = directDepositAdvanceNegetive;
			}
			payrollBalance.setDirectDepositAdvanceNegetive(df.format(ddAdvanceVal));
			payrollBalance.setDirectDepositDeductionPositive(df.format(directDepositDeductionPositive));
			payrollBalance.setDue(df.format(due));
			double deductionTotal = directDepositGross + processedWithCheckOptionGross + manualAdvance + generalExpenses + vendorPayment + healthcareInsurance + immigration + ddAdvanceVal;
			//
			double total =0;
			double empTotal =0;
			 if("Employee".equalsIgnoreCase(loginUserRole)){
				 total = payRateIncome + directDepositDeductionPositive;
			 }else{
				 total = billRateIncome + directDepositDeductionPositive;
				 empTotal = payRateIncome + directDepositDeductionPositive;
			 }
			double balance = total - deductionTotal + (processedWithCheckOptionNet - actualChecksDepositedNet);
			double empBalance = empTotal - deductionTotal;
			//
			double billed = 0;
			double empBilled = 0;
			if("Employee".equalsIgnoreCase(loginUserRole)){
				billed = payRateIncome + due;
			}else if("Admin".equalsIgnoreCase(loginUserRole)){
				billed = billRateIncome + due;
				empBilled = payRateIncome + due;
			}
			payrollBalance.setBalance(String.valueOf(Math.round(balance * 100D) / 100D));
			payrollBalance.setBilled(String.valueOf(Math.round(billed * 100D) / 100D));
			
			payrollBalance.setEmpBalance(String.valueOf(Math.round(empBalance * 100D) / 100D));
			payrollBalance.setEmpBilled(String.valueOf(Math.round(empBilled * 100D) / 100D));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payrollBalance;
	}


	@Override
	public int getAccountDeductionsVersionByLocationId(Long locationId)
	{
		return accountDeductionsDao.getAccountDeductionsVersionByLocationId(locationId);
	}


	@Override
	public ResponseList<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocation,
			AccountDeductionsDTO accountDeductionsDTO) {
		return accountDeductionsDao.getAccountDeductionsByEmployeeId(orgLocation,accountDeductionsDTO);
	}
}
