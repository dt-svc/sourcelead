package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

import com.bis.sourcelead.constants.Constants;
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
		
		String fileName = multipartFile.getOriginalFilename();
		List<IndAssociatePaymentAccountDTO> list = new ArrayList<>();
		
		 List<PaymentAccount> paymentDetailsList =paymentAccountDao.getPaymentAccountDetailsByOrglocationId(orgLocation.getId());
		 List<Candidate> candidateList =  candidateService.getAllAssociateByLocation(orgLocation.getId());
		 Map<String, Long> map = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));		
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("File Extension is: " + multipartFile.getOriginalFilename());
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				int count =0;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
						if (row.getRowNum() > 0 && (row.getCell(1) != null && row.getCell(2) != null)) { // Excluding column names
							IndAssociatePaymentAccountDTO importDTO = new IndAssociatePaymentAccountDTO();
								
									String email = getCellValueAsString(row.getCell(1), fileName);	
									if(validateEmail(email)) {
					                importDTO.setEmail(email);
					                //defalut values
					                importDTO.setAccess(false);
					                importDTO.setDefaultAccount(false);
					                importDTO.setDisabled(false);
					                importDTO.setOrgLocationId(orgLocation.getId());
					      
				                
				                    String paymentMode = getCellValueAsString(row.getCell(2), fileName);
				                    importDTO.setAccountType(paymentMode);
				                    String  assoId = null;
				                    String bankName = null;
				                    String accnum = null;
				                    String bsrcode =null;
				                    String micrcode = null;
				                    String routcode= null;
				                    String fName = null;
				                    String Lname = null;
				                    if( paymentMode.equalsIgnoreCase(Constants.PAYMENT_MODE_SAVINGS)){
				                    	
						     			
				            		    Map<String,  List<String>> accGroupByEmailList = new HashMap<String, List<String>>();
				            		    map.forEach((k, v) -> {
				            			   accGroupByEmailList.put(k, paymentDetailsList.stream().filter(t -> t.getEmail().equals(k)).map(PaymentAccount::getAccountNumber).collect(Collectors.toList()));
				            		   });
				            		    List<String> accountNum =accGroupByEmailList.get(importDTO.getEmail());							    	  
				            		    
						                    	if(row.getCell(3) != null && row.getCell(4) != null&& row.getCell(7) != null && row.getCell(8) != null && row.getCell(9) != null) {
						                    		  bankName =  getCellValueAsString(row.getCell(3), fileName);
						                    		  if(row.getCell(0) != null) {
						                    			  assoId = getCellValueAsString(row.getCell(0), fileName);
						                    			  importDTO.setCandidateId(Long.parseLong(assoId));}
								                      accnum = getCellValueAsString(row.getCell(4), fileName);
								                      if(row.getCell(5) != null) {
								                    	  bsrcode = getCellValueAsString(row.getCell(5), fileName); }					                    		  
								                      if(row.getCell(6) != null) {
								                    	  micrcode = getCellValueAsString(row.getCell(6), fileName); }						                    		 
								                      routcode = getCellValueAsString(row.getCell(7), fileName);
								                      fName = getCellValueAsString(row.getCell(8), fileName);	
								                      Lname = getCellValueAsString(row.getCell(9), fileName);												  																												
												     if(isStringInt(accnum) && validateBsrMicr(bsrcode) && validateBsrMicr(micrcode) &&routingNum(routcode)) {
												    	importDTO.setBankName(bankName);
												    	//account no validation for saving payment mode	 
												    	 importDTO.setAccountNumber(accnum);	
													    boolean a = accountNum.contains(importDTO.getAccountNumber());
													    if(!a) {													    	   
													    	    importDTO.setBsrNumber(bsrcode);
															    importDTO.setMicrCode(micrcode);
																importDTO.setRoutingNumber(routcode);
																importDTO.setFirstName(fName);
																importDTO.setLastName(Lname);
																list.add(importDTO); }
														}
						                    	        }				     						                    	
				                                	else{
								                    		break;
								                    	}				                    	
											        }
								                    else{
								                    	//email validation for cash and cheque	 
								                    String pEmail = importDTO.getEmail();
								                   Boolean flag = false;
				                        		for (PaymentAccount  py:  paymentDetailsList)
				                        		{ 				                        			
				                        			if(py.getEmail().equalsIgnoreCase(pEmail) && py.getAccountType().equalsIgnoreCase(paymentMode))
				                        			{
				                        				flag = true;				                        				
				                        			}
				                        		}
				                        		if(flag) {
				                        			continue;
				                        		}else {
						                    		list.add(importDTO);
				                        		}
				                    		}			                  			
								}			
						}        
						else{
							count = count+1;
							if(count == 5)	
							 break;}				      
						}				
					}	
		if(list.size() > 0) {
			this.saveBanking(list,orgLocation,candidateList,map);
			fis.close();}
			else {System.out.println("Invalid Data");}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		
	  // importDTO.setRecordCount(Integer.toString(list.size()));
		return null;
	}
	
	private void saveBanking(List<IndAssociatePaymentAccountDTO> list, OrgLocations orgLocation,List<Candidate> candidateList,Map<String, Long> map) throws Exception {
		
           try {			
			    paymentAccountDao.saveBankingDetailInPaymentAccount(orgLocation.getId(),list,map);
			  System.out.println("Details Added");			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		          
	}

	/**method for reading data from excel
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
	
	//method for validating bank account number
	public boolean isStringInt(String accnum)
	{    	
	    	if (StringUtils.isBlank(accnum) || accnum.matches("[0-9]{9,12}")){    	
	    			return true;
	    			}    	
	    	else {
	    		System.out.println("Invalid account no");
	    		return false;
	    	}}
	
	// method for validating Bsrcode and MicrCode 
	
	public boolean validateBsrMicr(String num) //not the mandatory fields
	{
		if (StringUtils.isBlank(num) || num.matches("^[a-zA-Z0-9]{9,15}$"))
		{
			return true;
		}
		else {
    		System.out.println("Invalid entry");
    		return false;
    	}}
	
	//Method for validating Routing num/ifsc code
	public boolean routingNum(String routcode)
	{
		if (StringUtils.isBlank(routcode) || routcode.matches("^[a-zA-Z0-9]{11}$"))
		{
			return true;
		}
		else {
    		System.out.println("Invalid entry");
    		return false;
    	}}
	
	//method for email validation
	private boolean validateEmail(String email) {
		 
		  String emailId =email.trim();
		  Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		  Matcher matcher = pattern.matcher(emailId);
		  return matcher.matches();
		  
	}
}
