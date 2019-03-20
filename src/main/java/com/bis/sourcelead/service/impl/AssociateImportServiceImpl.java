package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AssociatePfEsiDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.payroll.service.impl.AssociateBankDataServiceImpl;
import com.bis.sourcelead.service.AssociateImportService;
import com.bis.sourcelead.service.CandidateService;

@Service
public class AssociateImportServiceImpl implements  AssociateImportService{

	private static final Logger logger = LoggerFactory.getLogger(AssociateImportServiceImpl.class);
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	AssociatePfEsiService associatePfEsiService;
	
	@Autowired
	AssociatePfEsiDao associatePfEsiDao;
	
	
	
	@Override
	public AssociateBankErrorData saveAssociateInformation(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception{
					
		String fileName = multipartFile.getOriginalFilename();
		AssociateBankErrorData associateBankErrorData = new AssociateBankErrorData();
		List<Candidate> candidateList = candidateService.getAllAssociateByLocation(orgLocation.getId());
		Map<String, Long> canMap = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
		
		List<AssociatePfEsiDetails> InfoList = associatePfEsiService.getAllPfEsiDetailsByOrgLocationid(orgLocation.getId());
		Map<String, Candidate> pfeMap = InfoList.stream().collect(Collectors.toMap(AssociatePfEsiDetails::getUanNumber, AssociatePfEsiDetails::getCandidateId));
	//	Map<String, List<AssociateBankDataDTO>> bankMap = bankDataList.stream().collect(Collectors.groupingBy(AssociateBankDataDTO::getCandidateId, Collectors.toList()));
		
		int insertedCount = 0;
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			List<AssociateBankDataDTO> list = new ArrayList<>();
			List<AssociateBankDataDTO> errorlist = new ArrayList<>();
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				int count =0;
				boolean isValidated = false;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					AssociateBankDataDTO bankDataDTO = new AssociateBankDataDTO();
					if (row.getRowNum() > 0 && row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) { // Excluding column names
						isValidated = validateAssociateBankExeclData(row,fileName,bankDataDTO,canMap,pfeMap);
						if(isValidated) {
							if (row.getCell(0) != null  && !"".equalsIgnoreCase(row.getCell(0).toString()))
								bankDataDTO.setId(Long.parseLong(getCellValueAsString(row.getCell(0), fileName)));	
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								bankDataDTO.setEmailId(getCellValueAsString(row.getCell(1), fileName));
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								bankDataDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString()))
								bankDataDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
								bankDataDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
							if(row.getCell(5) != null  && !"".equalsIgnoreCase(row.getCell(5).toString()))
								bankDataDTO.setRoutingNumber(getCellValueAsString(row.getCell(5), fileName));
							list.add(bankDataDTO);
						}else {
							if(row.getCell(0) != null  && !"".equalsIgnoreCase(row.getCell(0).toString()))
								bankDataDTO.setAssociateId(getCellValueAsString(row.getCell(0), fileName));	
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								bankDataDTO.setEmailId(getCellValueAsString(row.getCell(1), fileName));
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								bankDataDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString()))
								bankDataDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
								bankDataDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
							if(row.getCell(5) != null  && !"".equalsIgnoreCase(row.getCell(5).toString()))
								bankDataDTO.setRoutingNumber(getCellValueAsString(row.getCell(5), fileName));
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
			
			/*
			 * Iterator itr=map.entrySet().iterator(); while(itr.hasNext()) { Map.Entry
			 * entry=(Map.Entry)itr.next(); List<AssociateBankDataDTO> s =
			 * (List<AssociateBankDataDTO>) entry.getValue(); for(AssociateBankDataDTO
			 * abdList : s) {
			 * if(abdList.getEmailId().equalsIgnoreCase(entry.getKey().toString())){
			 * System.out.println("True"); } else { System.out.println("False"); } } }
			 */
			
			insertedCount =  associatePfEsiDao.saveAssociateInformation(orgLocation.getId(), list, canMap);
			associateBankErrorData.setErrorData(errorlist);
			associateBankErrorData.setInsertedCount(insertedCount);
			fis.close();
//			long endTime   = System.nanoTime();
//			long totalTime = endTime - startTime;
//			System.out.println(totalTime);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return associateBankErrorData;
	}
	
	/**
	 * Validate Excel Data
	 * @param row
	 * @param fileName
	 * @param bankDataDTO 
	 * @param map 
	 * @param pfeMap 
	 * @return boolean
	 */
	private boolean validateAssociateBankExeclData(Row row, String fileName, AssociateBankDataDTO bankDataDTO, Map<String, Long> map, Map<String, Candidate> pfeMap)
	{
		if(map.get(getCellValueAsString(row.getCell(1), fileName)) != null) {
			if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
				bankDataDTO.setReason("Email Mandatory");
				return false;
			}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString()) && !validateEmail(getCellValueAsString(row.getCell(1), fileName))){
				bankDataDTO.setReason("Invalid Email");
				return false;
			}else if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
				if("Direct Deposit".equalsIgnoreCase(getCellValueAsString(row.getCell(2), fileName))) {
					if (row.getCell(4) == null || "".equalsIgnoreCase(row.getCell(4).toString())) {
						bankDataDTO.setReason("Bank Account Mandatory");
						return false;
					}else if(row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString()) && !validateAccount(getCellValueAsString(row.getCell(4), fileName))){
						bankDataDTO.setReason("Invalid Bank Account");
						return false;
					}else if (row.getCell(5) == null || "".equalsIgnoreCase(row.getCell(5).toString())) {
						bankDataDTO.setReason("Routing Number Mandatory");
						return false;
					}else if(row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString()) && !validateRouting(getCellValueAsString(row.getCell(5), fileName))){
						bankDataDTO.setReason("Invalid Routing Number");
						return false;
					}
				}
			}
			
			if(pfeMap != null ) {
				Long candidateId = map.get(getCellValueAsString(row.getCell(1),fileName));
				if(pfeMap.get(candidateId.toString()) != null) {
					List<AssociateBankDataDTO> list = pfeMap.get(candidateId.toString());
					for(AssociateBankDataDTO b : list) {
						if(row.getCell(4) != null && b.getAccountNumber() != null) {
							if(getCellValueAsString(row.getCell(4), fileName).equalsIgnoreCase(b.getAccountNumber())) {
								bankDataDTO.setReason("account already existed with this email");
								return false;
							}
						}
					}
				}
				
			}
			
		}else {
			bankDataDTO.setReason("candidate does't existed with given email");
		}
		
		return true;
	}

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
		private boolean validateAccount(String accNum){
			String acNo = accNum.trim();
			Pattern pattern = Pattern.compile("[0-9]{10,12}");
			Matcher matcher = pattern.matcher(acNo);
			return matcher.matches();
    	}
		
		//validateRouting
		/**
		 * validate routing Number
		 * @param routingNum
		 * @return boolean
		 */
		private boolean validateRouting(String routingNum){
			String rutNo = routingNum.trim();
			Pattern pattern = Pattern.compile("[0-9]{9,9}");
			Matcher matcher = pattern.matcher(rutNo);
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
