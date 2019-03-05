package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.PaymentAccountDao;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.service.impl.AssociateBankDataServiceImpl;
import com.bis.sourcelead.service.PaymentAccountService;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService{

	private static final Logger logger = LoggerFactory.getLogger(PaymentAccountServiceImpl.class);
	
	@Autowired
	PaymentAccountDao paymentAccountDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Override
	public PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) throws Exception {
		return paymentAccountDao.savePaymentAccount(paymentAccount);
	}

	@Override
	public PaymentAccount savePaymentAccountDetails(IndAssociatePaymentAccountDTO account, OrgLocations orgLocations)
			throws Exception {
		
		PaymentAccount pay = null;
		if(account.getPaymentAccountId() != null && !account.getPaymentAccountId().isEmpty()) {
			pay = paymentAccountDao.getPaymentAccountById(Long.valueOf(account.getPaymentAccountId()));
		}else {
			pay = new PaymentAccount();
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(account.getAssociateUserId(), orgLocations.getId());
		    pay.setCandidateId(candidate);
		    boolean flag = paymentAccountDao.getisActivePaymentAccountExistedByCandidateId(candidate.getId());
		    if(flag == false) {
		    	pay.setDefaultAccount(true);
		    }
		}
        pay.setAccountId(account.getAccountId());
        pay.setAccountType(account.getAccountType());
        pay.setOrgLocation(orgLocations);
        pay.setFirstName(account.getFirstName());
        pay.setLastName(account.getLastName());
        pay.setBankName(account.getBankName());
        pay.setBsrNumber(account.getBsrNumber());
        pay.setMicrCode(account.getMicrCode());
        pay.setAccountNumber(account.getAccountNumber());
        pay.setRoutingNumber(account.getRoutingNumber());
        pay.setDisabled(false);
        PaymentAccount paymentAccount = savePaymentAccount(pay);
		return paymentAccount;
	}

	@Override
	public PaymentAccount getPaymentAccountById(Long id) throws Exception {
		return paymentAccountDao.getPaymentAccountById(id);
	}

	@Override
	public PaymentAccount getPaymentAccountDetailsByCandidateId(Long candidateId) throws Exception {
		return paymentAccountDao.getPaymentAccountDetailsByCandidateId(candidateId);
	}

	@Override
	public IndAssociatePaymentAccountDTO saveImportBankDetails(MultipartFile multipartFile, OrgLocations orgLocation) {
		String fileName = multipartFile.getOriginalFilename();
		/*LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();*/
		//List<AssociateBankDataDTO> list = new ArrayList<>();
		
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					IndAssociatePaymentAccountDTO importDTO = new IndAssociatePaymentAccountDTO();
					Row row = rowIterator.next();
				/*	if (row.getRowNum() > 0) {
						isValidated = validateExeclData(row, importDTO, fileName, orgLocation);*/
					
				/*	if (isValidated) {*/
						if (row.getRowNum() > 0) { // Excluding column names
							if (row.getCell(0) != null) {
								String assoId = getCellValueAsString(row.getCell(0), fileName);
								importDTO.setCandidateId(Long.parseLong(assoId));
							}
							importDTO.setEmail(getCellValueAsString(row.getCell(1), fileName));
							importDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));							
							importDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
							importDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
							importDTO.setBsrNumber(getCellValueAsString(row.getCell(5), fileName));
							importDTO.setMicrCode(getCellValueAsString(row.getCell(6), fileName));
							importDTO.setIfscCode(getCellValueAsString(row.getCell(7), fileName));
							importDTO.setFirstName(getCellValueAsString(row.getCell(8), fileName));
							importDTO.setLastName(getCellValueAsString(row.getCell(9), fileName));
							
							try {
								this.saveBankingAccountDetails(importDTO,orgLocation);
							} catch (Exception e) {
								e.printStackTrace();
							}
							}
					
						}
					
					
					}				
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Read cell value as String
	 * @param cell
	 * @param fileName
	 * @return String
	 */
	private String getCellValueAsString(Cell cell, String fileName) {
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

	@Override
	public PaymentAccount saveBankingAccountDetails(IndAssociatePaymentAccountDTO account, OrgLocations orgLocations)
			throws Exception {
	
		PaymentAccount pay= new PaymentAccount();
		boolean flag= candidateDao.getCandidateIsanAssociate(account.getCandidateId());
		try {
		if(flag) {
			Candidate can = new Candidate();
			can.setId(account.getCandidateId());
        pay.setCandidateId(can);
        pay.setEmail(account.getEmail());
        pay.setAccountType(account.getAccountType());
        pay.setFirstName(account.getFirstName());
        pay.setLastName(account.getLastName());
        pay.setBankName(account.getBankName());
        pay.setBsrNumber(account.getBsrNumber());
        pay.setMicrCode(account.getMicrCode());
        pay.setAccountNumber(account.getAccountNumber());
        pay.setRoutingNumber(account.getRoutingNumber());
           this.savePaymentAccount(pay);
			}
		else
		 {
			System.out.println("Invalid data");}
		
	   }catch (Exception e) {		
		logger.error(ExceptionUtils.getFullStackTrace(e));
	}
		return pay;
	}	
}
