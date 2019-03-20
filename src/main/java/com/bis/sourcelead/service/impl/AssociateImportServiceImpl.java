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

import com.bis.sourcelead.entity.AssociateSalaryInd;
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
						isValidated = validateAssociateBankExeclData(row,fileName,InfoDataDTO,canMap,pfeMap);
						if(isValidated) {
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								InfoDataDTO.setEmail(getCellValueAsString(row.getCell(1), fileName));
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								InfoDataDTO.setUanNumber(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString()))
								InfoDataDTO.setPfNumber(getCellValueAsString(row.getCell(3), fileName));
							    InfoDataDTO.setEligibleForPf("Y");
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
								InfoDataDTO.setEsinumber(getCellValueAsString(row.getCell(4), fileName));
							    InfoDataDTO.setEligibleForEsi("Y");
							list.add(InfoDataDTO);
						}else {
							if(row.getCell(0) != null  && !"".equalsIgnoreCase(row.getCell(0).toString()))
								InfoDataDTO.setUserId(getCellValueAsString(row.getCell(0), fileName));
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString()))
								InfoDataDTO.setEmail(getCellValueAsString(row.getCell(1), fileName));
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()))
								InfoDataDTO.setUanNumber(getCellValueAsString(row.getCell(2), fileName));																	
							if(row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString()))
								InfoDataDTO.setPfNumber(getCellValueAsString(row.getCell(3), fileName));
							if(row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
								InfoDataDTO.setEsinumber(getCellValueAsString(row.getCell(4), fileName));
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
			associateBankErrorData.setInsertedCount(insertedCount);
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
	
	/*
	 * 
	 */
	private boolean validateAssociateBankExeclData(Row row, String fileName, AssociatePfEsiDetailsDTO InfoDataDTO,
			Map<String, Long> canMap, Map<Long, String> pfeMap) {
		if(canMap.get(getCellValueAsString(row.getCell(1), fileName)) != null) {
			if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
				InfoDataDTO.setReason("Email Mandatory");
				return false;
			}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString()) 
					&& !validateEmail(getCellValueAsString(row.getCell(1), fileName))){
				InfoDataDTO.setReason("Invalid Email");
				return false;
			}else if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
			         if (row.getCell(2) == null || "".equalsIgnoreCase(row.getCell(2).toString())) {
			        	 InfoDataDTO.setReason("Uan number Mandatory");
						return false;
					}else if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString()) && !validateUan(getCellValueAsString(row.getCell(2), fileName))){
						InfoDataDTO.setReason("Invalid Uan Number");
						return false;
					}else if (row.getCell(3) == null || "".equalsIgnoreCase(row.getCell(3).toString())) {
						InfoDataDTO.setReason("Pf Number Mandatory");
						return false;
					}else if(row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString()) && !validatePf(getCellValueAsString(row.getCell(3), fileName))){
						InfoDataDTO.setReason("Invalid Pf Number");
						return false;
					}else if(row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString()) && !validateEsi(getCellValueAsString(row.getCell(4), fileName))){
						InfoDataDTO.setReason("Invalid Esi Number");
						return false;
				}
			}
			
			if(pfeMap != null ) {
				Long candidateId = canMap.get(getCellValueAsString(row.getCell(1),fileName));
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
			InfoDataDTO.setReason("candidate does't existed with given email");	
			return false;
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
		private boolean validateUan(String uan){
			String acNo = uan.trim();
			Pattern pattern = Pattern.compile("[0-9]{12}");
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
			Pattern pattern = Pattern.compile("[0-9]{15}");
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
			Pattern pattern = Pattern.compile("[0-9]{10,15}");
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
