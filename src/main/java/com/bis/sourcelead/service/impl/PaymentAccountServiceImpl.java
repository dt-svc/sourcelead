package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.PaymentAccountService;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService{

	private static final Logger logger = LoggerFactory.getLogger(PaymentAccountServiceImpl.class);
	
	@Autowired
	PaymentAccountDao paymentAccountDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	private CandidateService candidateService;
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

/*	     reading data from excel sheet      */
	@Override
	public IndAssociatePaymentAccountDTO saveImportBankDetails(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception{
		
		long startTime = System.nanoTime();
	
	
		String fileName = multipartFile.getOriginalFilename();
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			List<IndAssociatePaymentAccountDTO> list = new ArrayList<>();
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				int count =0;
				while (rowIterator.hasNext()) {
					IndAssociatePaymentAccountDTO importDTO = new IndAssociatePaymentAccountDTO();
					Row row = rowIterator.next();
					
						if (row.getRowNum() > 0 && (row.getCell(1) != null && row.getCell(2) != null)) { // Excluding column names
							String assoId = getCellValueAsString(row.getCell(0), fileName);
							if (row.getCell(0) != null) {
								importDTO.setCandidateId(Long.parseLong(assoId));
							}
							String email2 = getCellValueAsString(row.getCell(1), fileName);
			                    importDTO.setEmail(email2);			                    			                    
								importDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));																	
								importDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
								importDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
								
								String Accnum = getCellValueAsString(row.getCell(4), fileName);
							//	if() {
							      importDTO.setAccountNumber(Accnum);
								
								
								
								
								importDTO.setBsrNumber(getCellValueAsString(row.getCell(5), fileName));
								importDTO.setMicrCode(getCellValueAsString(row.getCell(6), fileName));
								importDTO.setRoutingNumber(getCellValueAsString(row.getCell(7), fileName));
								importDTO.setFirstName(getCellValueAsString(row.getCell(8), fileName));
								importDTO.setLastName(getCellValueAsString(row.getCell(9), fileName));
								list.add(importDTO);
							}
						else
						{
							count =count+1;
							if(count == 5)	
							 break;
						}
				          }
				
						}				
			this.saveBanking(list,orgLocation);
			fis.close();
			long endTime   = System.nanoTime();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void saveBanking(List<IndAssociatePaymentAccountDTO> list, OrgLocations orgLocation) throws Exception {
		
		// Get the List<Candidate> based on orgLocationId
           try {
			List<Candidate> candidateList =  candidateService.getAllCandidatesByLocation(orgLocation.getId());
//			 Map<String, Candidate> result3 = candidateList.stream().collect(Collectors.toMap(x -> x.get, x -> x.getName()));
			Map<String, Long> map = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
			  int a = paymentAccountDao.saveBankingDetailInPaymentAccount(orgLocation.getId(),list,map);
			  System.out.println(a);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
           
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
	try {
		Candidate can = new Candidate();
		can.setId(account.getCandidateId());
        pay.setCandidateId(can);
       // pay.setCandidateId(account.getCandidateId());
        pay.setEmail(account.getEmail());
        pay.setAccountType(account.getAccountType());
        pay.setFirstName(account.getFirstName());
        pay.setLastName(account.getLastName());
        pay.setBankName(account.getBankName());
        pay.setBsrNumber(account.getBsrNumber());
        pay.setMicrCode(account.getMicrCode());
        pay.setRoutingNumber(account.getRoutingNumber());
        pay.setAccountNumber(account.getAccountNumber());
           this.savePaymentAccount(pay);
		}	
	   catch (Exception e) {	
		  System.out.println("Invalid data");
		logger.error(ExceptionUtils.getFullStackTrace(e));
	}
	
		return pay;
	}	
	
	public boolean isStringInt(String Accnum)
	{
	    	
	    	if (!Accnum.matches("[a-zA-Z_]+") && (Accnum.length() == 12))	{    	
	    			return true;	
	    	}    	
	    	else {
	    		System.out.println("Invalid account no");
	    		return false;
	    	}
	}
}
