package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
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
import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.InvoiceDataDao;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.service.InvoiceDataService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class InvoiceDataServiceImpl implements InvoiceDataService{
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDataServiceImpl.class);
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	InvoiceDataDao invoiceDataDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public Integer saveInvoiceData(MultipartFile multipartFile, OrgLocations orgLocation, User user,JSONObject jsonObj) throws EncryptedDocumentException, InvalidFormatException {


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
			int isValid = 0;
			List<InvoiceData>  invoicedatalist = new ArrayList<>();
			int version;
			version = getInvoiceDataVersionByOrgLocationId(orgLocation.getId());
			version += 1;
			for (int i = 0; i < 1; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) { // Excluding column names
						InvoiceData invoiceData = new InvoiceData();
						boolean notEmpty = checkRowEmptyOrNot(row);
						if(notEmpty) {
						if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
							String sku = getCellValueAsString(row.getCell(0), fileName);
							invoiceData.setSku(sku.split("\\.")[0]);
							invoiceData.setVersion(version);
						}
						if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
							String productService = getCellValueAsString(row.getCell(1), fileName);
							invoiceData.setProductService(productService);
						}
						if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
							String empName = getCellValueAsString(row.getCell(2), fileName);
							invoiceData.setEmpName(empName);
						}
						if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
							String num = getCellValueAsString(row.getCell(3), fileName);
							invoiceData.setNumber(num);
						}
						if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
							String memoDescription = getCellValueAsString(row.getCell(4), fileName);
							invoiceData.setMemoDescription(memoDescription);
						}
						if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
							Date date = getCellDate(row.getCell(5), fileName);
							invoiceData.setDate(date);
						}
						if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
							Date lastModifiedDate = getCellDateMMDDYYYY_HHMMSS(row.getCell(6), fileName);
							invoiceData.setLastmodified(lastModifiedDate);
						}
						if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
							Date dueDate = getCellDate(row.getCell(7), fileName);
							invoiceData.setDueDate(dueDate);
						}
						if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
							String rate = getCellValueAsString(row.getCell(8), fileName);
							invoiceData.setRate(rate);
						}
						if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
							String qty = getCellValueAsString(row.getCell(9), fileName);
							invoiceData.setQty(qty);
						}else {
							invoiceData.setQty("0");
						}
						if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
							String amount = getCellValueAsString(row.getCell(10), fileName);
							invoiceData.setAmount(amount);
						}else {
							invoiceData.setAmount("0");
						}
						if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
							String arPaid = getCellValueAsString(row.getCell(11), fileName);
							invoiceData.setArPaid(arPaid);
						}else {
							invoiceData.setArPaid(Constants.NONE);
						}
						if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
							String newOld = getCellValueAsString(row.getCell(12), fileName);
							invoiceData.setOldNew(newOld);
						}
						if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
							String payRate = getCellValueAsString(row.getCell(13), fileName);
							invoiceData.setPayRate(payRate);
						}else {
							invoiceData.setPayRate("0");
						}
						invoiceData.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						invoiceData.setCreatedBy(user.getUserCode());
						invoiceData.setCreatedDate(new Date());
						invoiceData.setUpdatedBy(user.getUserCode());
						invoiceData.setUpdatedDate(new Date());
						invoiceData.setOrgLocationId(orgLocation);
						invoicedatalist.add(invoiceData);
						
						InvoiceData saveInvoiceData = invoiceDataDao.saveInvoiceData(invoiceData);
						if (saveInvoiceData != null) {
							count++;
						}
//						count++;
						
					}
				}

			}
			}
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}

		return count;

	
	}
	
	/**
	 * check the rows empty or not
	 * @param row
	 * @return boolean
	 */
		
	private boolean checkRowEmptyOrNot(Row row) {
		       if(row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString()) 
		        || row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString()) 
		        || row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString()) 
		        || row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())
		        || row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())
                || row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())
                || row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())
                || row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())
                || row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())
                || row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString()) 
                || row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())
                || row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString()) 
                || row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())
		    	|| row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())){

			return true;
		}
		return false;
	}
/**
 * To read cell value as string
 * 
 * @param cell
 * @param fileName
 * @return
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
	/**
	 * To get cell date
	 * @param cell
	 * @param fileName
	 * @return Date
	 */
	private Date getCellDate(Cell cell, String fileName) {
		Date date = null;
	  try {
		  if(fileName.toLowerCase().endsWith("xls")){
			   HSSFCell hssfCell = (HSSFCell) cell;
				if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) hssfCell)) {
						date = hssfCell.getDateCellValue();
				}
			}else{
				XSSFCell xssfFCell = (XSSFCell) cell;
				if (xssfFCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) xssfFCell)) {
						date = xssfFCell.getDateCellValue();
				}if (xssfFCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					date  = conversionUtil.getDateFromMMDDYYY(xssfFCell.getStringCellValue());
					}
			}  
	  }catch (Exception e) {
		  e.printStackTrace();
	}
		
		return date;
	}
	
	private Date getCellDateMMDDYYYY_HHMMSS(Cell cell, String fileName) {
		Date date = null;
		  try {
			  if(fileName.toLowerCase().endsWith("xls")){
				   HSSFCell hssfCell = (HSSFCell) cell;
					if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) hssfCell)) {
							date = hssfCell.getDateCellValue();
					}
				}else{
					XSSFCell xssfFCell = (XSSFCell) cell;
					if (xssfFCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) xssfFCell)) {
							date = xssfFCell.getDateCellValue();
					}if (xssfFCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						date  = conversionUtil.getDateFromMMDDYYY_HHMMSS(xssfFCell.getStringCellValue());
						}
				}  
		  }catch (Exception e) {
			  e.printStackTrace();
		}
			return date;
	}

	@Override
	public ResponseList<InvoiceData> getInvoiceDetails(InvoiceDetailsDTO invoiceDetailsDTO, OrgLocations orgLocation) throws Exception {
		ResponseList<InvoiceData> list = invoiceDataDao.getInvoiceDetails(invoiceDetailsDTO, orgLocation);
		for (InvoiceData invoiceData : list.getData()) {
			if(invoiceData.getDate() != null)
			invoiceData.setDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoiceData.getDate()));
			if(invoiceData.getLastmodified() != null)
			invoiceData.setLastModifiedStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoiceData.getLastmodified()));
			if(invoiceData.getDueDate() != null)
			invoiceData.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoiceData.getDueDate()));
		}
		return list;

		// TODO do not delete @Rajashekar
		/*List<InvoiceData> invoiceDataList = invoiceDataDao.getInvoiceDetails(userId, orgLocationId);
		
		for (InvoiceData invoiceData : invoiceDataList) {
			if (invoiceData.getDate() != null) {
				invoiceData.setDateStr(conversionUtil.getMM_DD_YYYAsString(invoiceData.getDate()));
			}
			if (invoiceData.getDueDate() != null) {
				invoiceData.setDueDateStr(conversionUtil.getMM_DD_YYYAsString(invoiceData.getDueDate()));
			}
			if (invoiceData.getLastmodified() != null) {
				invoiceData.setLastModifiedStr(conversionUtil.getDateAsDD_MM_YYYY_HHMMSS(invoiceData.getLastmodified()));
			}
		}
		Collections.sort(invoiceDataList, new Comparator() {  
            @Override  
            public int compare(Object obj1, Object obj2) {  
            	InvoiceData invoiceData1 = (InvoiceData)obj1;  
            	InvoiceData invoiceData2 = (InvoiceData)obj2; 
            	return invoiceData1.getEmpName().compareToIgnoreCase(invoiceData2.getEmpName());  
            }  
        });
		return invoiceDataList;*/
	}
	@Override
	public ResponseList<InvoiceData> getInvoiceDetailsByEmpId(OrgLocations orgLocations,InvoiceDetailsDTO invoiceDetailsDTO) throws Exception {
		return invoiceDataDao.getInvoiceDetailsByEmpId(orgLocations,invoiceDetailsDTO);
	}
	
	@Override
	public int getInvoiceDataVersionByOrgLocationId(Long orgLocationId)
	{
		return invoiceDataDao.getInvoiceDataVersionByOrgLocationId(orgLocationId);
	}

	@Override
	public ResponseList<InvoiceData> getAllEmployeeInvoiceDetailsByEmpIdAndOrgLocationId(Long orgLocationId, Long userId)
	{
		return invoiceDataDao.getAllEmployeeInvoiceDetailsByEmpIdAndOrgLocationId(orgLocationId,userId);
	}

	@Override
	public List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations) {
		return invoiceDataDao.getInvoiceDetailsByEmpId(empId,orgLocations);
	}
}
