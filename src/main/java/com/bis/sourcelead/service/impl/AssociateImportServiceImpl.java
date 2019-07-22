package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dao.PaymentAccountDao;
import com.bis.sourcelead.dto.AssociateSalaryIndDTO;
import com.bis.sourcelead.dto.AssociateSalaryPayheadIndDTO;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.AssociatePfEsiDao;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.AssociateImportService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.UserGroupService;

@Service
public class AssociateImportServiceImpl implements  AssociateImportService{

	private static final Logger logger = LoggerFactory.getLogger(AssociateImportServiceImpl.class);
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	AssociatePfEsiService associatePfEsiService;
	
	@Autowired
	AssociatePfEsiDao associatePfEsiDao;
	
	@Autowired
	PaymentAccountDao paymentAccountDao;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Override
	public AssociateBankErrorData saveAssociateInformation(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception{
					
		String fileName = multipartFile.getOriginalFilename();
		AssociateBankErrorData associateBankErrorData = new AssociateBankErrorData();
		List<Candidate> candidateList = candidateService.getAllAssociateByLocation(orgLocation.getId());
		Map<String, Long> canMap = new HashMap<>();
		for(Candidate c : candidateList) {
			canMap.put(c.getEmail().toLowerCase(), c.getId());
		}
		//Map<String, Long> canMap = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
		
		List<AssociatePfEsiDetails> InfoList = associatePfEsiService.getAllPfEsiDetailsByOrgLocationid(orgLocation.getId());
		Map<Long, String> pfeMap = new HashMap<Long, String>();
		for (AssociatePfEsiDetails i : InfoList) pfeMap.put(i.getCandidateId().getId(), i.getUanNumber());
		int insertedCount = 0;
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			List<AssociatePfEsiDetailsDTO> list = new ArrayList<>();
			List<AssociatePfEsiDetailsDTO> errorlist = new ArrayList<>();
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
			
				boolean isValidated = false;
				int count =0;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					AssociatePfEsiDetailsDTO InfoDataDTO = new AssociatePfEsiDetailsDTO();
					if (row.getRowNum() > 0 && row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) { // Excluding column names
						isValidated = validateAssociateInfoExeclData(row,fileName,InfoDataDTO,canMap,pfeMap);
						if(isValidated) {
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								InfoDataDTO.setEmail(getCellValueAsString(row.getCell(1), fileName).toLowerCase());
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								InfoDataDTO.setUanNumber(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString()))
								InfoDataDTO.setPfNumber(getCellValueAsString(row.getCell(3), fileName).replaceAll("[^A-Za-z0-9_]" ,""));
							    InfoDataDTO.setEligibleForPf("Y");
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
								InfoDataDTO.setEsinumber(getCellValueAsString(row.getCell(4), fileName));
							    InfoDataDTO.setEligibleForEsi("Y");
							list.add(InfoDataDTO);
						}else {
							if(row.getCell(0) != null  && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								InfoDataDTO.setUserId(getCellValueAsString(row.getCell(0), fileName));
							 }else{InfoDataDTO.setUserId("-");}
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								InfoDataDTO.setEmail(getCellValueAsString(row.getCell(1), fileName));
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								InfoDataDTO.setUanNumber(getCellValueAsString(row.getCell(2), fileName));	
							  }else{InfoDataDTO.setUanNumber("-");}
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								InfoDataDTO.setPfNumber(getCellValueAsString(row.getCell(3), fileName));
							  }else{InfoDataDTO.setPfNumber("-");}
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								InfoDataDTO.setEsinumber(getCellValueAsString(row.getCell(4), fileName));
							  }else{InfoDataDTO.setEsinumber("-");}
							errorlist.add(InfoDataDTO);
						}
					}
					else{
						count++;
						if(count == 5)	
							break;
					}
				}
			}				
			insertedCount =  associatePfEsiDao.saveAssociateInformation(orgLocation.getId(), list, canMap);
			associateBankErrorData.setPfErrorData(errorlist);
			if(errorlist.isEmpty() && list.isEmpty()){
				associateBankErrorData.setEmptyStatus(true);
			}else{associateBankErrorData.setEmptyStatus(false);
			}
			associateBankErrorData.setInsertedCount(list.size());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return associateBankErrorData;
	}
	
	
	private boolean validateAssociateInfoExeclData(Row row, String fileName, AssociatePfEsiDetailsDTO InfoDataDTO,
			Map<String, Long> canMap, Map<Long, String> pfeMap) {
		
		if(canMap.get(getCellValueAsString(row.getCell(1), fileName).toLowerCase().trim()) != null) {
			if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
				InfoDataDTO.setReason("Email is required");
				return false;
			}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString()) 
					&& !validateEmail(getCellValueAsString(row.getCell(1), fileName))){
				InfoDataDTO.setReason("Invalid Email");
				return false;
			}else if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
			         if (row.getCell(2) == null || "".equalsIgnoreCase(row.getCell(2).toString())) {
			        	 InfoDataDTO.setReason("Uan number is required");
						return false;
					}else if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString()) && !validateUan(getCellValueAsString(row.getCell(2), fileName))){
						InfoDataDTO.setReason("Uan number should be between 1-12 characters");
						return false;
					}else if (row.getCell(3) == null || "".equalsIgnoreCase(row.getCell(3).toString())) {
						InfoDataDTO.setReason("PF number is required");
						return false;	
					}else if(row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString()) && !validatePf(getCellValueAsString(row.getCell(3), fileName).replaceAll("[^A-Za-z0-9_]" ,""))){
						InfoDataDTO.setReason("Pf number should be between 10-25 characters");
						return false;
					/*}else if (row.getCell(4) == null || "".equalsIgnoreCase(row.getCell(4).toString())) {
						InfoDataDTO.setReason("Esi Number Mandatory");
						return false;	*/
					}else if(row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString()) && !validateEsi(getCellValueAsString(row.getCell(4), fileName))){
						InfoDataDTO.setReason("Esi number should be between 10-17 characters");
						return false;
				}
			}else{
					InfoDataDTO.setReason("Uan number is required");
			     	return false;
			     }
			
			if(pfeMap != null ) {
				Long candidateId = canMap.get(getCellValueAsString(row.getCell(1),fileName).toLowerCase());
				if(pfeMap.get(candidateId) != null) {
					String uanNum = pfeMap.get(candidateId);
					if(getCellValueAsString(row.getCell(2), fileName).equalsIgnoreCase(uanNum)){
						InfoDataDTO.setReason("account already existed with this email");
						return false;
					}
				}
			}
		}else
		{
			InfoDataDTO.setReason("Associate doesn't exist");
			return false;
		}
		return true;
	}


	//=============== Associate banking detail import ====================//
	
	
	 @Override
	  public AssociateBankErrorData saveImportBankDetails(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception{
					
		String fileName = multipartFile.getOriginalFilename();
		AssociateBankErrorData associateBankErrorData = new AssociateBankErrorData();
		List<PaymentAccount> paymentDetailsList =paymentAccountDao.getPaymentAccountDetailsByOrglocationId(orgLocation.getId());
		List<Candidate> candidateList =  candidateService.getAllAssociateByLocation(orgLocation.getId());
		//Map<String, Long> canMap = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
		Map<String, Long> canMap = new HashMap<>();
		for(Candidate c : candidateList) {
			canMap.put(c.getEmail().toLowerCase(), c.getId());}
		
		
		
		Map<String,  List<String>> accGroupByEmailList = new HashMap<String, List<String>>();
		canMap.forEach((k, v) -> {
			   accGroupByEmailList.put(k, paymentDetailsList
					   .stream()
					   .filter(t -> t.getEmail() != null && t.getEmail().equals(k) && t.getAccountNumber() != null)
					   .map(PaymentAccount::getAccountNumber)
					   .collect(Collectors.toList()));  });
		
		int insertedCount = 0;
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			List<IndAssociatePaymentAccountDTO> list = new ArrayList<>();
			List<IndAssociatePaymentAccountDTO> errorlist = new ArrayList<>();
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				int count =0;
				boolean isValidated = false;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					IndAssociatePaymentAccountDTO bankDataDTO = new IndAssociatePaymentAccountDTO();
					if (row.getRowNum() > 0 && row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) { // Excluding column names
						isValidated = validateAssociateBankExeclData(row,fileName,bankDataDTO,canMap,accGroupByEmailList,paymentDetailsList);
						if(isValidated) {
							if (row.getCell(0) != null  && !"".equalsIgnoreCase(row.getCell(0).toString())) 
								bankDataDTO.setUserId((getCellValueAsString(row.getCell(0), fileName)));	
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								bankDataDTO.setEmail(getCellValueAsString(row.getCell(1), fileName).toLowerCase());
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								bankDataDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString()))
								bankDataDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
								bankDataDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
							if(row.getCell(5) != null  && !"".equalsIgnoreCase(row.getCell(5).toString()))
								bankDataDTO.setBsrNumber(getCellValueAsString(row.getCell(5), fileName));
						    if(row.getCell(6) != null  && !"".equalsIgnoreCase(row.getCell(6).toString()))
						    	bankDataDTO.setMicrCode(getCellValueAsString(row.getCell(6), fileName));
						    if(row.getCell(7) != null  && !"".equalsIgnoreCase(row.getCell(7).toString()))
						    	bankDataDTO.setRoutingNumber(getCellValueAsString(row.getCell(7), fileName));
						    if(row.getCell(8) != null  && !"".equalsIgnoreCase(row.getCell(8).toString()))
						    	bankDataDTO.setFirstName(getCellValueAsString(row.getCell(8), fileName));
						    if(row.getCell(9) != null  && !"".equalsIgnoreCase(row.getCell(9).toString()))
						    	bankDataDTO.setLastName(getCellValueAsString(row.getCell(9), fileName));
							   list.add(bankDataDTO);
						}else {
							if (row.getCell(0) != null  && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								bankDataDTO.setUserId((getCellValueAsString(row.getCell(0), fileName)));
							     }else{bankDataDTO.setUserId("-");}
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								bankDataDTO.setEmail(getCellValueAsString(row.getCell(1), fileName));
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								bankDataDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								bankDataDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
						         }else{bankDataDTO.setBankName("-");}
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								bankDataDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
						         }else{bankDataDTO.setAccountNumber("-");}
							if(row.getCell(5) != null  && !"".equalsIgnoreCase(row.getCell(5).toString())) 
								bankDataDTO.setBsrNumber(getCellValueAsString(row.getCell(5), fileName));
						    if(row.getCell(6) != null  && !"".equalsIgnoreCase(row.getCell(6).toString())) 
						    	bankDataDTO.setMicrCode(getCellValueAsString(row.getCell(6), fileName));
						    if(row.getCell(7) != null  && !"".equalsIgnoreCase(row.getCell(7).toString())) {
						    	bankDataDTO.setRoutingNumber(getCellValueAsString(row.getCell(7), fileName));
						         }else{bankDataDTO.setRoutingNumber("-");}
						    if(row.getCell(8) != null  && !"".equalsIgnoreCase(row.getCell(8).toString())) {
						    	bankDataDTO.setFirstName(getCellValueAsString(row.getCell(8), fileName));
						         }else{bankDataDTO.setFirstName("-");}
						    if(row.getCell(9) != null  && !"".equalsIgnoreCase(row.getCell(9).toString())) {
						    	bankDataDTO.setLastName(getCellValueAsString(row.getCell(9), fileName));
						    }else{bankDataDTO.setLastName("-");}
							    errorlist.add(bankDataDTO);
						}
						
					}
					else{
						count++;
						if(count == 5)	
							break;
					}
				}
			}				
			
		
			insertedCount =  paymentAccountDao.saveBankingDetailInPaymentAccount(orgLocation.getId(), list, canMap);
			associateBankErrorData.setBdErrorData(errorlist);
			associateBankErrorData.setInsertedCount(list.size());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return associateBankErrorData;
	}

		private boolean validateAssociateBankExeclData(Row row, String fileName, IndAssociatePaymentAccountDTO bankDataDTO, Map<String, Long> map, Map<String,List<String>> accGroupByEmailList ,List<PaymentAccount> paymentDetailsList)
		{
			if(map.get(getCellValueAsString(row.getCell(1), fileName).toLowerCase().trim()) != null) {
				if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
					bankDataDTO.setReason("Email is required");
					return false;
				}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString()) && !validateEmail(getCellValueAsString(row.getCell(1), fileName))){
					bankDataDTO.setReason("Invalid Email");
					return false;
				}else if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
					if("SAVINGS".equalsIgnoreCase(getCellValueAsString(row.getCell(2), fileName))) {
						if(row.getCell(3) == null || "".equalsIgnoreCase(row.getCell(3).toString())) {
							bankDataDTO.setReason("Bank Name is required");
						}else if (row.getCell(4) == null || "".equalsIgnoreCase(row.getCell(4).toString())) {
							bankDataDTO.setReason("Bank Account is required");
							return false;
						}else if(row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString()) && !validateAccount(getCellValueAsString(row.getCell(4), fileName))){
							bankDataDTO.setReason("Bank Account number length should be between 9-15 characters");
							return false;
						}else if(row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString()) && !validateBsrMicr(getCellValueAsString(row.getCell(5), fileName))){
							bankDataDTO.setReason("BSR/MICR length should be between 9-15 characters");
							return false;						
						}else if(row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString()) && !validateBsrMicr(getCellValueAsString(row.getCell(6), fileName))){
							bankDataDTO.setReason("BSR/MICR length should be between 9-15 characters");
							return false;
						}else if (row.getCell(7) == null || "".equalsIgnoreCase(row.getCell(7).toString())) {
							bankDataDTO.setReason("IFSC number is required");
							return false;					
						}else if(row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString()) && !routingNum(getCellValueAsString(row.getCell(7), fileName))){
							bankDataDTO.setReason("IFSC Code length should be 11 characters");
							return false;
						}else if (row.getCell(8) == null || "".equalsIgnoreCase(row.getCell(8).toString())) {
							bankDataDTO.setReason("First Name is required");
							return false;	
						}else if (row.getCell(9) == null || "".equalsIgnoreCase(row.getCell(9).toString())) {
							bankDataDTO.setReason("Last Name is required");
							return false;
						}
					}
				}
				
				if(accGroupByEmailList != null ) {
					if("SAVINGS".equalsIgnoreCase(getCellValueAsString(row.getCell(2), fileName)) && accGroupByEmailList.containsKey(getCellValueAsString(row.getCell(1), fileName).toLowerCase())) {
					List<String> accountNum =accGroupByEmailList.get(getCellValueAsString(row.getCell(1),fileName).toLowerCase());	
					if(accountNum != null && accountNum.size() > 0) {
						 boolean acc = accountNum.contains(getCellValueAsString(row.getCell(4),fileName));
						 if(acc) {
							 bankDataDTO.setReason("account already existed with this email");
								return false;
						 }
					}
					}else {
							Boolean flag = false;        
					    for (PaymentAccount  pList:  paymentDetailsList){ 			
					    	if(pList.getEmail().toLowerCase().equalsIgnoreCase(getCellValueAsString(row.getCell(1), fileName).toLowerCase()) && pList.getAccountType().equalsIgnoreCase(getCellValueAsString(row.getCell(2), fileName)))
					    	{			                        				
					 			flag = true;
					 		}	
					    	if(flag) {
					    		bankDataDTO.setReason("account already existed with this email");
					     	       return false;
					 		}else {
					 			continue; }
					 		}
						 }	 		
				}
				
			}else {
				bankDataDTO.setReason("Associate doesn't exist");
				return false;
			}
			
			return true;
		}  
	
		
//=======================Associate salary import=========================//
		
	@Override
	public ResponseList<AssociateSalaryIndDTO> saveAssociateSalaryDetails(MultipartFile multipartFile,
				OrgLocations orgLocation, User user) {
			String fileName = multipartFile.getOriginalFilename();
			ResponseList<AssociateSalaryIndDTO> response = new ResponseList<>();
			List<AssociateSalaryIndDTO> associateSalaryIndDTOList = new ArrayList<AssociateSalaryIndDTO>();
			int recordUpdatedCount = 0;
			try {
				File file = new File(multipartFile.getOriginalFilename());
				multipartFile.transferTo(file);
				FileInputStream fis = new FileInputStream(file);
				Workbook workbook;
				logger.debug("File Extension is: " + multipartFile.getOriginalFilename());
				
				
				List<Candidate> candidateList =  candidateService.getAllActiveAssociatesByLocation(orgLocation.getId());
				Map<String, Long> mapEmailAndId = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
				//Map<String, String> mapEmailAndAssId = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getGeneratedId));
				
				List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation);
				Map<String, String> userGroupNameAndUUID = userGroupList.stream().collect(Collectors.toMap(UserGroup::getGroupName, UserGroup::getGroupUUID));
				
				List<PayrollPayheadDTO> payHeadDTOList = payrollPayheadService.getPayrollPayheadList(orgLocation).getData();
				Map<Long, String> mapPayheadIdAndName = payHeadDTOList.stream().collect(Collectors.toMap(PayrollPayheadDTO::getId, PayrollPayheadDTO::getName));
				Map<String, String> mapPayheadShortCodeAndName = payHeadDTOList
																		       .stream()
																		       .filter(t -> t.getActive().equalsIgnoreCase("YES"))
																		       .collect(Collectors.toMap(PayrollPayheadDTO::getShortCode, PayrollPayheadDTO::getName));
				
				List<AssociateSalaryInd> associateSalaryIndList = payrollPayheadService.getAssociateSalaryIndListByOrgLocationsId(orgLocation.getId());
				Map<String, String> associateSalaryIndCanEmailAndUUID = new HashMap<String, String>();
				for (AssociateSalaryInd i : associateSalaryIndList) associateSalaryIndCanEmailAndUUID.put(i.getCandidate().getEmail(), i.getUuid());
				
				workbook = WorkbookFactory.create(fis);
				int numberOfSheets = workbook.getNumberOfSheets();
				for (int i = 0; i < numberOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					Iterator<Row> rowIterator = sheet.iterator();
					
					// Assuming "column headers" are in the first row
					Row header_row = sheet.getRow(0);
					
					int count =0;
					while (rowIterator.hasNext()) {
						AssociateSalaryIndDTO associateSalaryIndDTO = new AssociateSalaryIndDTO();
						Row row = rowIterator.next();
							if (row.getRowNum() > 0 && (row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null  && row.getCell(6) != null)) {
								/*String assoId = getCellValueAsString(row.getCell(0), fileName);
								if (row.getCell(0) != null && StringUtils.isNotBlank(mapEmailAndAssId.get(assoId))) {
									associateSalaryIndDTO.setCandidateId(Long.parseLong(assoId));
								}*/
								
								if(validateEmail(getCellValueAsString(row.getCell(1), fileName).trim()) && mapEmailAndId.get(getCellValueAsString(row.getCell(1), fileName).trim()) != null) {
									associateSalaryIndDTO.setAssociateEmail(getCellValueAsString(row.getCell(1), fileName).trim());
									associateSalaryIndDTO.setCandidateId(mapEmailAndId.get(getCellValueAsString(row.getCell(1), fileName).trim()));
									associateSalaryIndDTO.setUuid(associateSalaryIndCanEmailAndUUID.get(getCellValueAsString(row.getCell(1), fileName).trim()));
								}else {
									associateSalaryIndDTO.setAssociateEmail(getCellValueAsString(row.getCell(1), fileName).trim());
									associateSalaryIndDTO.setErrorMessage("Invalid Email");
									associateSalaryIndDTOList.add(associateSalaryIndDTO);
									continue;
								}
								
								if(Arrays.stream(Constants.ASSOCIATE_SALARY_PAYTYPE).anyMatch(getCellValueAsString(row.getCell(2), fileName).trim() :: equals)) {
									associateSalaryIndDTO.setPayType(getCellValueAsString(row.getCell(2), fileName));
								}else {
									associateSalaryIndDTO.setErrorMessage("Invalid Salary Type");
									associateSalaryIndDTOList.add(associateSalaryIndDTO);
									continue;
								}
								if(Arrays.stream(Constants.ASSOCIATE_SALARY_PAYFREQUENCY).anyMatch(getCellValueAsString(row.getCell(3), fileName).trim() :: equals)) {
									associateSalaryIndDTO.setPayFrequency(getCellValueAsString(row.getCell(3), fileName));
								}else {
									associateSalaryIndDTO.setErrorMessage("Invalid Salary Freaquency");
									associateSalaryIndDTOList.add(associateSalaryIndDTO);
									continue;
								}
								if(row.getCell(4) != null && userGroupNameAndUUID.get(getCellValueAsString(row.getCell(4), fileName).trim()) != null) {
									associateSalaryIndDTO.setPayGroup(userGroupNameAndUUID.get(getCellValueAsString(row.getCell(4), fileName).trim()));
								}else {
									associateSalaryIndDTO.setErrorMessage("Invalid Pay Group");
									associateSalaryIndDTOList.add(associateSalaryIndDTO);
									continue;
								}
								if(row.getCell(5) != null && getCellValueAsString(row.getCell(5), fileName).trim() != null) {
									try{
										  Integer.parseInt(getCellValueAsString(row.getCell(5), fileName).trim());
										  associateSalaryIndDTO.setStandardHours(getCellValueAsString(row.getCell(5), fileName));
										} catch (NumberFormatException e) {
											associateSalaryIndDTO.setStandardHours(orgLocation.getAverageWorkingHours().toString());
										}
								}else {
									associateSalaryIndDTO.setStandardHours(orgLocation.getAverageWorkingHours().toString());
								}
								
								if(row.getCell(6) != null && StringUtils.isNumeric(getCellValueAsString(row.getCell(6), fileName).trim())) {
									associateSalaryIndDTO.setAnnualSal(getCellValueAsString(row.getCell(6), fileName).trim());
								}else {
									associateSalaryIndDTO.setErrorMessage("Invalid Annual Salary");
									associateSalaryIndDTOList.add(associateSalaryIndDTO);
									continue;
								}
								
								switch(associateSalaryIndDTO.getPayFrequency()){
							     case "Annual":
							    	 associateSalaryIndDTO.setPerPaySal(String.format("%.02f", Float.parseFloat(associateSalaryIndDTO.getAnnualSal()) / 1));
							     break;

							     case "Monthly":
							    	 associateSalaryIndDTO.setPerPaySal(String.format("%.02f", Float.parseFloat(associateSalaryIndDTO.getAnnualSal()) / 12));
							     break;

							     case "Bi-Weekly":
							    	 associateSalaryIndDTO.setPerPaySal(String.format("%.02f", Float.parseFloat(associateSalaryIndDTO.getAnnualSal()) / 26));
							     break;

							     case "Weekly":
							    	 associateSalaryIndDTO.setPerPaySal(String.format("%.02f", Float.parseFloat(associateSalaryIndDTO.getAnnualSal()) / 52));
							     break;
								}
								
								associateSalaryIndDTO.setHourlyRate(String.format("%.02f", Float.parseFloat(associateSalaryIndDTO.getPerPaySal()) / Float.parseFloat(associateSalaryIndDTO.getStandardHours())));
								
								List<AssociateSalaryPayheadIndDTO> assoSalPayheadIndDTOList = new ArrayList<>();
								Iterator<Cell> cells = row.cellIterator();
						        while (cells.hasNext())
						        {
						        	Cell cell = cells.next();
						        	if(cell.getColumnIndex() > 6) {
						        		AssociateSalaryPayheadIndDTO assoSalPayheadIndDTO = new AssociateSalaryPayheadIndDTO();
										String header = header_row.getCell(cell.getColumnIndex()).getStringCellValue();
										
										for (Long id : mapPayheadIdAndName.keySet())  
								        { 
								            if (mapPayheadIdAndName.get(id).equalsIgnoreCase(header)) {
										        assoSalPayheadIndDTO.setPayrollPayheadId(id);
										    }
								        }
										
										for (String sc : mapPayheadShortCodeAndName.keySet())  
								        { 
								            if (mapPayheadShortCodeAndName.get(sc).equalsIgnoreCase(header)) {
								            	assoSalPayheadIndDTO.setPayrollPayheadShortCode(sc);
										    }
								        }
										
										if(row.getCell(cell.getColumnIndex()) != null && StringUtils.isNumeric(getCellValueAsString(row.getCell(cell.getColumnIndex()), fileName).trim())) {
											assoSalPayheadIndDTO.setChecked(true);
											assoSalPayheadIndDTO.setMonthly(new BigDecimal(getCellValueAsString(row.getCell(cell.getColumnIndex()), fileName).trim()));
											assoSalPayheadIndDTO.setAnnualy(new BigDecimal(getCellValueAsString(row.getCell(cell.getColumnIndex()), fileName).trim()).multiply(new BigDecimal(12)));
										}else {
											associateSalaryIndDTO.setErrorMessage("Invalid Amount");
											associateSalaryIndDTOList.add(associateSalaryIndDTO);
											continue;
										}
										
										assoSalPayheadIndDTOList.add(assoSalPayheadIndDTO);
									}
						        }
						        associateSalaryIndDTO.setAssociateSalaryPayheadIndList(assoSalPayheadIndDTOList);
								
						        payrollPayheadService.saveAssociateSalaryInd(associateSalaryIndDTO, user, orgLocation);
						        recordUpdatedCount ++;
							}else if(row.getRowNum() > 0 && row.getCell(1) != null && !validateEmail(getCellValueAsString(row.getCell(1), fileName).trim())) {
								associateSalaryIndDTO.setAssociateEmail(getCellValueAsString(row.getCell(1), fileName).trim());
								associateSalaryIndDTO.setErrorMessage("Invalid Email");
								associateSalaryIndDTOList.add(associateSalaryIndDTO);
							}else{
								count = count++;
								if(count == 5)	
								 break;
							}
					 }				
				}	
				response.setResponse(String.valueOf(recordUpdatedCount));
				response.setData(associateSalaryIndDTOList);
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
				response.setResponse(String.valueOf(recordUpdatedCount));
				response.setData(associateSalaryIndDTOList);
			}
			return response;
		}
		
	 
	
		
		
	// ---------------------methods used for validations --------------------------//
		/**
		 * validate email
		 * @param email
		 * @return boolean
		 */
			private boolean validateEmail(String email) {
				 
				  String emailId =email.trim();
				  Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				  Matcher matcher = pattern.matcher(emailId);
				  return matcher.matches();
				  
			}
			
			/**
			 * validate bank account
			 * @param accnum
			 * @return boolean
			 */
			private boolean validateUan(String uan){
				String acNo = uan.trim();
				Pattern pattern = Pattern.compile("[0-9]{1,12}");
				Matcher matcher = pattern.matcher(acNo);
				return matcher.matches();
	    	}
			
			//validateRouting
			/**
			 * validate routing Number
			 * @param routingNum
			 * @return boolean
			 */
			private boolean validatePf(String pfNum){
				String rutNo = pfNum.trim();
				Pattern pattern = Pattern.compile("^[A-Z][A-Z0-9]{10,25}"); 
				Matcher matcher = pattern.matcher(rutNo);
				return matcher.matches();
	    	}
			
			
			//validateRouting
			/**
			 * validate routing Number
			 * @param routingNum
			 * @return boolean
			 */
			private boolean validateEsi(String esiNum){
				String rutNo = esiNum.trim();
				Pattern pattern = Pattern.compile("[0-9]{10,17}");
				Matcher matcher = pattern.matcher(rutNo);
				return matcher.matches();
	    	}
			
			// method for validating Bsrcode and MicrCode 
			
			public boolean validateBsrMicr(String num) //not the mandatory fields
			{
				  String emailId =num.trim();
				  Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{9,15}$");
				  Matcher matcher = pattern.matcher(emailId);
				  return matcher.matches();
				}
			
			//Method for validating Routing num/ifsc code
			public boolean routingNum(String routcode)
			{
				  String emailId =routcode.trim();
				  Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{11}$");
				  Matcher matcher = pattern.matcher(emailId);
				  return matcher.matches();
				}
			
			/**
			 * validate bank account
			 * @param accnum
			 * @return boolean
			 */
			private boolean validateAccount(String accNum){
				String acNo = accNum.trim();
				Pattern pattern = Pattern.compile("[0-9]{9,15}");
				Matcher matcher = pattern.matcher(acNo);
				return matcher.matches();
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
		
}
