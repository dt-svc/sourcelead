package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.EmployerPayrollCostDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.EmployerPayrollCostDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.PayrollHelper;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class EmployerPayrollCostServiceImpl implements EmployerPayrollCostService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployerPayrollCostServiceImpl.class);
	DecimalFormat df = new DecimalFormat("#.00");
	
	@Autowired
	EmployerPayrollCostDao employerPayrollCostDao;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	PayrollHelper payrollHelper;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	private UserPayrollService userPayrollService;
	
	@Autowired
	private WageTaxSummaryService wageTaxSummaryService;
	
	@Autowired
	private AccountDeductionsService accountDeductionsService;

	@Override
	public Integer saveEmployerPayrollCost(MultipartFile multipartFile, OrgLocations orgLocation, User user,JSONObject jsonObj)
			throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		List<EmployerPayrollCost> employerPayrollCostList = new ArrayList<>();
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
			int isValid = 0;
			int version = 0;
			version = getEmployerPayrollCostVersionByLocationId(orgLocation.getId());
			version += 1;
			for (int i = 0; i < 1; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				if(i == 0){
					isValid =  payrollHelper.isValidTemplateForEmployeePayRoll(rowIterator);
				}
				if(isValid == 1){
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
				/*	if (true) {*/
						if (row.getRowNum() > 0) { // Excluding column names
							EmployerPayrollCost  employerPayrollCost = new EmployerPayrollCost();
							float grossWages =0f;
							float fica =0f;
							float futa =0f;
							float sui =0f;
							float erLocals =0f; 
							float wcCost =0f;
							float adminFee =0f; 
							float totalErCost =0f; 
							String empId = null;
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								empId = getCellValueAsString(row.getCell(1), fileName);
								employerPayrollCost.setEmployeeId(empId.split("\\.")[0]);
								employerPayrollCost.setVersion(version);
							}
							
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empName = getCellValueAsString(row.getCell(0), fileName);
								employerPayrollCost.setEmployeeName(empName);
							} 
							 
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								String ssn = getCellValueAsString(row.getCell(2), fileName);
								StringBuilder sb = new StringBuilder();
								String[] arr = ssn.split(":");
								String ssNbr = arr[1];
								String last4DigitsSsn = ssNbr.substring(ssNbr.length() - 4);
								String enCodedSsnNumber = sourceleadPasswordEncoder.encode(ssNbr);
								sb.append(last4DigitsSsn).append("-").append(enCodedSsnNumber);
								employerPayrollCost.setSsn(sb.toString());
							} 
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								String totalHrs = getCellValueAsString(row.getCell(3), fileName);
								employerPayrollCost.setTotalHrs(totalHrs);
							} 
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								 grossWages = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
								employerPayrollCost.setGrossWages(grossWages);
							} 
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								 fica = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
								employerPayrollCost.setFica(fica);
							} 
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								 futa = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
								employerPayrollCost.setFuta(futa);
							} 
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								 sui = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
								employerPayrollCost.setSui(sui);
							} 
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								 erLocals = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
								employerPayrollCost.setErLocals(erLocals);
							} 
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								 wcCost = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
								employerPayrollCost.setWcCost(wcCost);
							} 
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								 adminFee = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
								employerPayrollCost.setAdminFee(adminFee);
							} 
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								 totalErCost = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
								employerPayrollCost.setTotalErCost(totalErCost);
							} 
							if(jsonObj.has("fromDate") && jsonObj.getString("fromDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("fromDate"))){
								employerPayrollCost.setFromDate(conversionUtil.getDateFromString(jsonObj.getString("fromDate")));
							}
							if(jsonObj.has("toDate") && jsonObj.getString("toDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("toDate"))){
								employerPayrollCost.setToDate(conversionUtil.getDateFromString(jsonObj.getString("toDate")));
							}
							if(jsonObj.has("batchNumber")){
								if(jsonObj.getString("batchNumber") !=null && !"".equalsIgnoreCase(jsonObj.getString("batchNumber"))){
									employerPayrollCost.setBatchNumber(jsonObj.getString("batchNumber"));
								}
							}
							employerPayrollCost.setCreatedBy(user.getUserCode());
							employerPayrollCost.setCreatedDate(new Date());
							employerPayrollCost.setUpdatedBy(user.getUserCode());
							employerPayrollCost.setUpdatedDate(new Date());
							employerPayrollCost.setOrgLocationId(orgLocation);
							employerPayrollCost.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							EmployerPayrollCost empPayrollCost = saveEmpPayrollCost(employerPayrollCost);
							if(empPayrollCost != null){
								count++;
							}
							employerPayrollCostList.add(employerPayrollCost);
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


	/**
	 * save EmployerPayrollCost 
	 * @param employerPayrollCost
	 * @return EmployerPayrollCost
	 * @throws Exception 
	 */
	public EmployerPayrollCost saveEmpPayrollCost(EmployerPayrollCost employerPayrollCost) throws Exception {
		return employerPayrollCostDao.saveEmployerPayrollCost(employerPayrollCost);
	}

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
	public ResponseList<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation,EmployerPayrollCostDTO employerPayrollCostDTO) throws Exception {
		return employerPayrollCostDao.loadEmployerPayrollCostByOrgLocationId(orgLocation,employerPayrollCostDTO);
	}

	@Override
	public List<UserPayroll> runPayrollCycle(OrgLocations orgLocation, User user,String isGlobalAccess, JSONObject jsonObj) throws Exception {
		
		List<UserPayroll> userPayrollList = new ArrayList<>();
		Date startDate = null, endDate = null;
		if(jsonObj.has("StartDate")){
			startDate = commonUtil.getDateFormat(jsonObj.getString("StartDate"));
		}
		if(jsonObj.has("EndDate")){
			endDate = commonUtil.getDateFormat(jsonObj.getString("EndDate"));
		}
		
		
//		if(jsonObj.has("payrollCycle")){
//			Date[] dates = null;
//			String payrollCycle = jsonObj.getString("payrollCycle");
//			String[] cycle = payrollCycle.split("_");
//			if(cycle.length >1){
//				dates = get15thToEndDateOfMonth(Integer.parseInt(cycle[0]));
//			}else{
//				 dates = get1to15thDateOfMonth(Integer.parseInt(cycle[0]));
//			}
//			Date startDate = dates[0];
//			Date endDate = dates[1];
			
//			userPayrollList = userPayrollService.getUserPayrollsBystartDateEndDte(orgLocation.getId(),startDate,endDate);
//			if(userPayrollList.size() >= 0){
				List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectsByOrgLocationId(orgLocation.getId());
				if(projectMembersList !=null && projectMembersList.size() >0){
					long i = 1;
					for(ProjectMembers pm:projectMembersList){
						StringBuilder sb = new StringBuilder();
						double amount = 0;
						double finalAmount = 0;
						Map<String, Float> hoursMap = submissionApproverHierarchyService.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(pm.getId(),orgLocation.getId(),startDate,endDate,isGlobalAccess);
						if(hoursMap.get(Constants.BILLABLE_HOURS) >0){
							UserPayroll userPayroll = new UserPayroll();
//							FinalPayrollReport finalPayrollReport = new FinalPayrollReport();
							userPayroll.setId(i);
							if(pm.getProjectMemberCandidteId() !=null) {
								String employeeId = wageTaxSummaryService.getWagesAndTaxEmployeeId(pm.getCandidateId());
								userPayroll.setEmpId(employeeId);	
								userPayroll.setUserId(pm.getProjectMemberCandidteId().getConsultantUser());
							}
							userPayroll.setOrgLocationId(orgLocation);
							if(pm.getProjectMemberCandidteId().getLastName() !=null){
								sb.append(pm.getProjectMemberCandidteId().getFirstName()).append(pm.getProjectMemberCandidteId().getLastName());
							}else{
								sb.append(pm.getProjectMemberCandidteId().getFirstName());
							}
							userPayroll.setEmployeeName(sb.toString());
							userPayroll.setTotalHours(df.format(hoursMap.get(Constants.BILLABLE_HOURS)));
							userPayroll.setHalfHours(df.format(hoursMap.get(Constants.BILLABLE_HOURS)/2));
							if(pm.getPayRate() !=null){
								double payRateD =  pm.getPayRate();
								userPayroll.setPayRate(df.format(payRateD));
								 amount = getAmount(hoursMap.get(Constants.BILLABLE_HOURS),pm);
								 finalAmount = amount;
							}
							if(amount >0){
								userPayroll.setAmount(df.format(Math.round(amount * 100D) / 100D));
								userPayroll.setFinalAmount(df.format(Math.round(finalAmount * 100D) / 100D));
							}else{
								userPayroll.setAmount(String.valueOf(Math.round(amount * 100D) / 100D));
								userPayroll.setFinalAmount(String.valueOf(Math.round(finalAmount * 100D) / 100D));
							}
							userPayroll.setPayrollStartDate(startDate);
							userPayroll.setPayrollEndDate(endDate);
							userPayroll.setCreatedBy(user.getUserCode());
							userPayroll.setCreatedDate(new Date());
							userPayroll.setUpdatedBy(user.getUserCode());
							userPayroll.setUpdatedDate(new Date());
							userPayroll.setStatus("1");
							userPayrollList.add(userPayroll);
//							userPayrollService.saveUserPayroll(userPayroll);
						}
						
//					}
					i++;
				}
//				userPayrollList = userPayrollService.getUserPayrollsBystartDateEndDte(orgLocation.getId(),startDate,endDate);
			}
			
			
//		}
		return userPayrollList;
	}

	private double getAmount(float hours, ProjectMembers pm) {
		double amount = 0;
		if(Constants.RATE_TYPE_HOURLY.equalsIgnoreCase(pm.getPayRateType())){
			double rate = pm.getPayRate();
			String hr = String.valueOf(hours);
			String[] hh = hr.split("\\.");
			double hrs = Double.parseDouble(hh[0]);
			double minutes = Double.parseDouble(hh[1]);
			double totalHourMin = hrs * 60;
			double totMinutes = totalHourMin + minutes;
			amount = totMinutes * (rate/60);
		}else if(Constants.RATE_TYPE_MONTHLY.equalsIgnoreCase(pm.getPayRateType())){
			double rate = pm.getPayRate();
			amount = rate/2;
		}
		return amount;
	}

	private Date[] get15thToEndDateOfMonth(int month) {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 15);
		cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH));
		dates[0] = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		dates[1] = cal.getTime();
		return dates;
	}

	private Date[] get1to15thDateOfMonth(int month) {
		Date[] dates = new Date[2];
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		dates[0] = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.DAY_OF_MONTH,14);
		dates[1] = calendar.getTime();
		return dates;
			
	}


	@Override
	public int getEmployerPayrollCostVersionByLocationId(Long locationId)
	{
		return employerPayrollCostDao.getEmployerPayrollCostVersionByLocationId(locationId);
	}


	@Override
	public ResponseList<PayrollBalance> getAllPayrollBalanceSheetByOrgLocationId(OrgLocations orgLocation,PayrollBalance payrollBalance,String loginUserRole) throws Exception {
		
//		  List<WageTaxSummary> wageTaxSummaryList =  wageTaxSummaryService.getUniqueWagesAndTaxListByOrgLocationId(orgLocation);
		  ResponseList<WageTaxSummary> wageTaxSummaryList =  wageTaxSummaryService.getUniqueWagesAndTaxListByOrgLocationIdAndPagination(orgLocation,payrollBalance);
		 ResponseList<PayrollBalance> responseList = new ResponseList<>();
		 List<PayrollBalance> balanceList = new ArrayList<>();
		  if(wageTaxSummaryList !=null) {
			 for(WageTaxSummary WageTaxSummary : wageTaxSummaryList.getData()) {
				 PayrollBalance balance = accountDeductionsService.getBalance(Long.parseLong(WageTaxSummary.getEmployeeId()), orgLocation,loginUserRole);
				 balance.setEmpName(WageTaxSummary.getEmployeeName());
				 balanceList.add(balance);
			 }
			 responseList.setData(balanceList);
			 responseList.setRecordsTotal(wageTaxSummaryList.getRecordsFiltered());
			 responseList.setRecordsFiltered(wageTaxSummaryList.getRecordsTotal());
		  }else {
			  responseList.setData(new ArrayList<PayrollBalance>());
		  }
		  
		  
		return responseList; 
	}


	@Override
	public List<EmployerPayrollCost> getEmployerPayrollCostByLocationId(Long locationId)
	{
		return employerPayrollCostDao.getEmployerPayrollCostByLocationId(locationId);
	}

}

