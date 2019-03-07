package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateBankDataDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateBankDataServiceImpl implements AssociateBankDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataServiceImpl.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateBankDataDao associateBankDataDao;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Override
	public Response<AssociateBankDataDTO> saveAssociateBankData(AssociateBankDataDTO associateBankDataDTO, OrgLocations orgLocation, User loginUser)
	{
		AssociateBankData associateBankData = null;
		Response<AssociateBankDataDTO> resObj = new Response<>();
		try {
			if (Validator.validate(associateBankDataDTO)) {
				if(associateBankDataDTO.getUserId() != null){
					Candidate candidate = candidateDao.getCandidateByConsultantUserId(Long.parseLong(associateBankDataDTO.getUserId()),orgLocation.getId());
					associateBankData = associateBankDataDao.findAssociateBankDataByCandidateId(candidate.getId(),orgLocation);
					if(associateBankData != null) {
						associateBankData.setUpdatedBy(loginUser.getUserCode());
						associateBankData.setUpdatedDate(new Date());
					}else {
						associateBankData = new AssociateBankData();
//						User user = userService.getUserById(Long.parseLong(associateBankDataDTO.getUserId()));
//						candidate = candidateService.getCandidateByConsultantUserId(Long.parseLong(associateBankDataDTO.getUserId()), orgLocation.getId());
						associateBankData.setCandidate(candidate);
						associateBankData.setCreatedDate(new Date());
						associateBankData.setCreatedBy(loginUser.getUserCode());
					}
				
				}
				associateBankData.setAccountType(associateBankDataDTO.getAccountType());
				if(StringUtils.isNotBlank(associateBankDataDTO.getBankName()))
				{
					associateBankData.setBankName(associateBankDataDTO.getBankName());
				}else {
					associateBankData.setBankName(null);
				}
				if(StringUtils.isNotBlank(associateBankDataDTO.getAccountNumber()))
					{
					associateBankData.setAccountNumber(associateBankDataDTO.getAccountNumber());
					}
				else {
					associateBankData.setAccountNumber(null);
				}
				if(StringUtils.isNotBlank(associateBankDataDTO.getRoutingNumber()))
					{
					associateBankData.setRoutingNumber(associateBankDataDTO.getRoutingNumber());
					}
				
				else {
					associateBankData.setRoutingNumber(null);
				}
				
				associateBankData.setOrgLocationId(orgLocation);
				associateBankData.setStatus(1);
				associateBankData.setAssociateBankUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
//				associateBankData.setCreatedBy(loginUser.getUserCode());
//				associateBankData.setUpdatedBy(loginUser.getUserCode());
//				associateBankData.setCreatedDate(new Date());
//				associateBankData.setUpdatedDate(new Date());
				AssociateBankData bankData = associateBankDataDao.saveAssociateBankData(associateBankData);
				if (bankData != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					associateBankDataDTO.setId(bankData.getId());
					resObj.setObject(associateBankDataDTO);
				}
			}
			else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return resObj;
	}

	@Override
	public AssociateBankDataDTO findAssociateBankDataByUserId(Long userId, OrgLocations orgLocation)
	{
		AssociateBankDataDTO associateBankDataDTO = null;
		try {
			Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			AssociateBankData associateBankData =  associateBankDataDao.findAssociateBankDataByCandidateId(candidate.getId(),orgLocation);
			if(associateBankData != null){
				associateBankDataDTO = new AssociateBankDataDTO();
//				associateBankDataDTO = new AssociateBankDataDTO();
				associateBankDataDTO.setId(associateBankData.getId());
				associateBankDataDTO.setAccountType(associateBankData.getAccountType());
				associateBankDataDTO.setBankName(associateBankData.getBankName());
				associateBankDataDTO.setRoutingNumber(associateBankData.getRoutingNumber());
				associateBankDataDTO.setAssociateBankDataUUID(associateBankData.getAssociateBankUuid());
				associateBankDataDTO.setAccountNumber(associateBankData.getAccountNumber());
				return associateBankDataDTO;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return associateBankDataDTO;
	}

	@Override
	public AssociateBankData getAssociateBankDataByOrgLocationId(Long locationId) {
		return associateBankDataDao.getAssociateBankDataByOrgLocationId(locationId);
	}
	
	@Override
	public AssociateBankData saveAssociateBankData(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception{
					
		long startTime = System.nanoTime();
		String fileName = multipartFile.getOriginalFilename();
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			List<AssociateBankDataDTO> list = new ArrayList<>();
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				int count =0;
				while (rowIterator.hasNext()) {
					AssociateBankDataDTO importDTO = new AssociateBankDataDTO();
					Row row = rowIterator.next();
					if (row.getRowNum() > 0 && (row.getCell(1) != null && row.getCell(2) != null)) { // Excluding column names
						if (row.getCell(0) != null)
							importDTO.setId(Long.parseLong(getCellValueAsString(row.getCell(0), fileName)));
						importDTO.setEmailId(getCellValueAsString(row.getCell(1), fileName));
	                    importDTO.setAccountType(getCellValueAsString(row.getCell(2), fileName));																	
						importDTO.setBankName(getCellValueAsString(row.getCell(3), fileName));
						importDTO.setAccountNumber(getCellValueAsString(row.getCell(4), fileName));
						importDTO.setRoutingNumber(getCellValueAsString(row.getCell(5), fileName));
						list.add(importDTO);
					}
					else{
						count++;
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
	
private void saveBanking(List<AssociateBankDataDTO> list, OrgLocations orgLocation) throws Exception {
		// Get the List<Candidate> based on orgLocationId
           try {
			List<Candidate> candidateList =  candidateService.getAllCandidatesByLocation(orgLocation.getId());
			Map<String, Long> map = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
			associateBankDataDao.saveAssociateBankingDetailInAssociateBankData(orgLocation.getId(),list,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
           
	}
}