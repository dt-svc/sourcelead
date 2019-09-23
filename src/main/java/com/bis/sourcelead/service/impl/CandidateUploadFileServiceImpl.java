package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidatesImport;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateUploadFileService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;

/**
 * 
 * @author
 * @date 6th July 2016
 */
@Service
public class CandidateUploadFileServiceImpl implements CandidateUploadFileService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateUploadFileServiceImpl.class);
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Override
	public String uploadCandidateFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked, Org org, Long roleHierarchyId) throws Exception
	{
		
		String isFileCreated = "";
		String fileName = multipartFile.getOriginalFilename();
		if (!fileName.toLowerCase().endsWith("csv")) {
			isFileCreated = parseCandidatesXlsxandXlsData(multipartFile, orgLocations, user, isMailChecked, org, roleHierarchyId);
		} /*
			 * else { isFileCreated =
			 * parseCSVData(multipartFile,orgLocations,user,isMailChecked); }
			 */
		return isFileCreated;
	}
	
	/**
	 * 
	 * @param multipartFile
	 * @param orgLocations
	 * @param user
	 * @param isMailChecked
	 * @param roleHierarchyId
	 * @param org
	 * @return List<UsersImport>
	 * @throws FileNotFoundException
	 */
	private String parseCandidatesXlsxandXlsData(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked, Org org, Long roleHierarchyId) throws Exception
	{
		
		String isFileCreated = "failure";
		String fileName = multipartFile.getOriginalFilename();
		Workbook createExcelworkbook = null;
		if (fileName.endsWith("xlsx")) {
			createExcelworkbook = new XSSFWorkbook();
		}
		else if (fileName.endsWith("xls")) {
			createExcelworkbook = (Workbook) new HSSFWorkbook();
		}
		Sheet createExcelsheet = createExcelworkbook.createSheet("candidate");
		createExcelsheet = setCandidateExcelHeader(createExcelsheet);
		try {
			int count = 0;
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			boolean isValidated = false;
			boolean isUserFound = false;
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					CandidatesImport candidateImport = new CandidatesImport();
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) {
						isValidated = validateCandidateExeclData(row, candidateImport, fileName, orgLocations);
					}
					if (isValidated) {
						if (row.getRowNum() > 0) { // Excluding column names
							logger.info("Reading Row :" + row.getRowNum() + " records");
							candidateImport.setFirstName(row.getCell(0).toString());
							if (row.getCell(1) == null
									|| "".equalsIgnoreCase(row.getCell(1).toString())) {
								candidateImport.setLastName("");
							}
							else {
								candidateImport.setLastName(row.getCell(1).toString());
							}
							
							candidateImport.setEmailId(row.getCell(2).toString().trim());
							
							if (row.getCell(3) != null) {
								String mobileNumber = getCellValueAsString(row.getCell(3), fileName);
								candidateImport.setMobileNumber(mobileNumber);
							}
							else {
								candidateImport.setMobileNumber("");
							}
							if (row.getCell(4) != null) {
								String country = getCellValueAsString(row.getCell(4), fileName);
								candidateImport.setCountry(country);;
							}
							else {
								candidateImport.setCountry(orgLocations.getCountry());
							}
							if (row.getCell(5) != null) {
								String state = getCellValueAsString(row.getCell(5), fileName);
								candidateImport.setState(state);
							}
							else {
								candidateImport.setState("");
							}
							if (row.getCell(6) != null) {
								String city = getCellValueAsString(row.getCell(6), fileName);
								candidateImport.setCity(city);
							}
							else {
								candidateImport.setCity("");
							}
							if (row.getCell(7) != null) {
								String address = getCellValueAsString(row.getCell(7), fileName);
								candidateImport.setAddress(address);
							}
							else {
								candidateImport.setAddress("");
							}
							if (row.getCell(8) != null) {
								String designation = getCellValueAsString(row.getCell(8), fileName);
								candidateImport.setDesignation(designation);
							}
							else {
								candidateImport.setDesignation("");
							}
							if (row.getCell(9) != null) {
								String designationType = getCellValueAsString(row.getCell(9), fileName);
								candidateImport.setDesignationType(designationType);
							}
							else {
								candidateImport.setDesignationType("");
							}
							
							// add two cells after designationType
							
							// Cell Vendor
							if(row.getCell(10) != null) {
								String vendor = getCellValueAsString(row.getCell(10), fileName);
								candidateImport.setVendor(vendor);
							}else {
								candidateImport.setVendor("");
							}
							
							// Cell Vendor Contact
							
							if(row.getCell(11) != null) {
								String vendorContact = getCellValueAsString(row.getCell(11), fileName);
								candidateImport.setVendorContact(vendorContact);
							}else {
								candidateImport.setVendorContact("");
							}
							
							if (row.getCell(12) != null) {
								String experienceYears = getCellValueAsString(row.getCell(12), fileName);
								candidateImport.setExperience(experienceYears);
							}
							else {
								candidateImport.setExperience("");
							}
							/*if (row.getCell(11) != null) {
								String experienceMonths = getCellValueAsString(row.getCell(11), fileName);
								candidateImport.setExperienceMonths(experienceMonths);
							}
							else {
								candidateImport.setExperienceMonths("");
							}*/
							if (row.getCell(13) != null) {
								String expectedRate = getCellValueAsString(row.getCell(13), fileName);
								
								candidateImport.setExpectedRate(getExpectedRateValid(expectedRate));
							}
							else {
								candidateImport.setExpectedRate("");
							}
							
							if (row.getCell(14) != null) {
								String expectedRateType = getCellValueAsString(row.getCell(14), fileName);
								candidateImport.setExpectedRateType(expectedRateType);
							}
							else {
								candidateImport.setExpectedRateType("");
							}
							
							if (row.getCell(15) != null) {
								String skillsName = getCellValueAsString(row.getCell(15), fileName);
								candidateImport.setSkillsName(skillsName);
							}
							else {
								candidateImport.setSkillsName("");
							}
							
							if (row.getCell(16) != null) {
								String education = getCellValueAsString(row.getCell(16), fileName);
								candidateImport.setEducation(education);
							}
							else {
								candidateImport.setEducation("");
							}
							
							if (row.getCell(17) != null) {
								String employers = getCellValueAsString(row.getCell(17), fileName);
								candidateImport.setEmployers(employers);
							}
							else {
								candidateImport.setEmployers("");
							}
							
							if (row.getCell(18) != null) {
								String primarySkill = getCellValueAsString(row.getCell(18), fileName);
								candidateImport.setPrimarySkill(primarySkill);
							}
							else {
								candidateImport.setPrimarySkill("");
							}
							
							//only India location
							if(orgLocations.getCountry().equalsIgnoreCase("India")) {
							if (row.getCell(19) != null) {
								String panNumber = getCellValueAsString(row.getCell(19), fileName);
								candidateImport.setPanNumber(panNumber);
							}
							else {
								candidateImport.setPanNumber("");
							}
							
							//only India location
							if (row.getCell(20) != null) {
								String aadharNumber = getCellValueAsString(row.getCell(20), fileName);
								candidateImport.setAadharNumber(aadharNumber);
							}
							else {
								candidateImport.setAadharNumber("");
							}
							}
							
							//only United States location
							if(orgLocations.getCountry().equalsIgnoreCase("United States")) {
							if (row.getCell(21) != null) {
								String ssnNumber = getCellValueAsString(row.getCell(21), fileName);
								candidateImport.setSsnNumber(ssnNumber);
							}
							else {
								candidateImport.setSsnNumber("");
							}
							}
							saveBulkCandidates(candidateImport, orgLocations, user, isMailChecked);
						}
					}
					else {
						if (row.getRowNum() > 0) {
							count++;
							createCandidateErrorFile(row, createExcelsheet, count, candidateImport, fileName, org, orgLocations);
						}
					}
				}
				if (count > 0) {
					FileOutputStream fos = null;
					if(!fileName.endsWith("xlsx")){
						fos = new FileOutputStream("CandidateError.xls");
						isFileCreated = "CandidateError.xls";
					}else{
						 fos = new FileOutputStream("CandidateError.xlsx");
						 isFileCreated = "CandidateError.xlsx";
					}
					
					createExcelworkbook.write(fos);
					fos.close();
				}else {
					isFileCreated = "success";
				}
				/*if (!isValidated || isUserFound) {
					FileOutputStream fos = null;
					if (!fileName.endsWith("xlsx")) {
						fos = new FileOutputStream("CandidateError.xls");
					}
					else {
						fos = new FileOutputStream("CandidateError.xlsx");
					}
					createExcelworkbook.write(fos);
					fos.close();
				}
				if (count > 0) {
					if (!fileName.endsWith("xlsx")) {
						isFileCreated = "CandidateError.xls";
					}
					else {
						isFileCreated = "CandidateError.xlsx";
					}
				}*/
				
			}
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}
		return isFileCreated;
	}
	
	private void createCandidateErrorFile(Row row, Sheet createExcelsheet, int rowIndex, CandidatesImport candidateImport, String fileName, Org org, OrgLocations orgLocations)
	{
		
		Row writeRow = createExcelsheet.createRow(rowIndex);
		if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
			String firstName = getCellValueAsString(row.getCell(0), fileName);
			writeRow.createCell(0).setCellValue(firstName);
		}
		else {
			writeRow.createCell(0).setCellValue("");
		}
		if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
			String lastName = getCellValueAsString(row.getCell(1), fileName);
			writeRow.createCell(1).setCellValue(lastName);
		}
		else {
			writeRow.createCell(1).setCellValue("");
		}
		if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
			String emailId = getCellValueAsString(row.getCell(2), fileName).toString();
			emailId.trim();
			writeRow.createCell(2).setCellValue(emailId);
		}
		else {
			writeRow.createCell(2).setCellValue("");
		}
		if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
			String mobileNumber = getCellValueAsString(row.getCell(3), fileName);
			writeRow.createCell(3).setCellValue(mobileNumber);
		}
		else {
			writeRow.createCell(3).setCellValue("");
		}
		if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
			String country = getCellValueAsString(row.getCell(4), fileName);
			writeRow.createCell(4).setCellValue(country);
		}
		else {
			writeRow.createCell(4).setCellValue("");
		}
		if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
			String state = getCellValueAsString(row.getCell(5), fileName);
			writeRow.createCell(5).setCellValue(state);
		}
		else {
			writeRow.createCell(5).setCellValue("");
		}
		if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
			String city = getCellValueAsString(row.getCell(6), fileName);
			writeRow.createCell(6).setCellValue(city);
		}
		else {
			writeRow.createCell(6).setCellValue("");
		}
		if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
			String address = getCellValueAsString(row.getCell(7), fileName);
			writeRow.createCell(7).setCellValue(address);
		}
		else {
			writeRow.createCell(7).setCellValue("");
		}
		if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
			String designation = getCellValueAsString(row.getCell(8), fileName);
			writeRow.createCell(8).setCellValue(designation);
		}
		else {
			writeRow.createCell(8).setCellValue("");
		}
		if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
			String designationType = getCellValueAsString(row.getCell(9), fileName);
			writeRow.createCell(9).setCellValue(designationType);
		}
		else {
			writeRow.createCell(9).setCellValue("");
		}
		
		// Vendor,Vendor Contact
		
		if(row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
			String vendor = getCellValueAsString(row.getCell(10), fileName);
			writeRow.createCell(10).setCellValue(vendor);
		}else {
			writeRow.createCell(10).setCellValue("");
		}
		
		if(row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())){
			String vendorContact = getCellValueAsString(row.getCell(11), fileName);
			writeRow.createCell(11).setCellValue(vendorContact);
		}else {
			writeRow.createCell(11).setCellValue("");
		}
		
		
		if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
			String experienceYears = getCellValueAsString(row.getCell(12), fileName);
			writeRow.createCell(12).setCellValue(experienceYears);
		}
		else {
			writeRow.createCell(12).setCellValue("");
		}
		/*if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
			String experienceMonths = getCellValueAsString(row.getCell(11), fileName);
			writeRow.createCell(11).setCellValue(experienceMonths);
		}
		else {
			writeRow.createCell(11).setCellValue("");
		}*/
		if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
			String expectedRate = getCellValueAsString(row.getCell(13), fileName);
			writeRow.createCell(13).setCellValue(expectedRate);
		}
		else {
			writeRow.createCell(13).setCellValue("");
		}
		
		if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(14).toString())) {
			String expectedRateType = getCellValueAsString(row.getCell(14), fileName);
			writeRow.createCell(14).setCellValue(expectedRateType);
		}
		else {
			writeRow.createCell(14).setCellValue("");
		}
		
		if (row.getCell(15) != null && !"".equalsIgnoreCase(row.getCell(15).toString())) {
			String skillsName = getCellValueAsString(row.getCell(15), fileName);
			writeRow.createCell(15).setCellValue(skillsName);
		}
		else {
			writeRow.createCell(15).setCellValue("");
		}
		
		if (row.getCell(16) != null && !"".equalsIgnoreCase(row.getCell(16).toString())) {
			String education = getCellValueAsString(row.getCell(16), fileName);
			writeRow.createCell(16).setCellValue(education);
		}
		else {
			writeRow.createCell(16).setCellValue("");
		}
		
		if (row.getCell(17) != null && !"".equalsIgnoreCase(row.getCell(17).toString())) {
			String employers = getCellValueAsString(row.getCell(17), fileName);
			writeRow.createCell(17).setCellValue(employers);
		}
		else {
			writeRow.createCell(17).setCellValue("");
		}
		
		if (row.getCell(18) != null && !"".equalsIgnoreCase(row.getCell(18).toString())) {
			String primarySkill = getCellValueAsString(row.getCell(18), fileName);
			writeRow.createCell(18).setCellValue(primarySkill);
		}
		else {
			writeRow.createCell(18).setCellValue("");
		}
		
		if (row.getCell(19) != null && !"".equalsIgnoreCase(row.getCell(19).toString())) {
			String panNumber = getCellValueAsString(row.getCell(19), fileName);
			writeRow.createCell(19).setCellValue(panNumber);
		}
		else {
			writeRow.createCell(19).setCellValue("");
		}
		
		if (row.getCell(20) != null && !"".equalsIgnoreCase(row.getCell(20).toString())) {
			String aadharNumber = getCellValueAsString(row.getCell(20), fileName);
			writeRow.createCell(20).setCellValue(aadharNumber);
		}
		else {
			writeRow.createCell(20).setCellValue("");
		}
		
		if (row.getCell(21) != null && !"".equalsIgnoreCase(row.getCell(21).toString())) {
			String ssnNumber = getCellValueAsString(row.getCell(21), fileName);
			writeRow.createCell(21).setCellValue(ssnNumber);
		}
		else {
			writeRow.createCell(21).setCellValue("");
		}
		
		writeRow.createCell(22).setCellValue(candidateImport.getReason());
	}
	
	private boolean validateCandidateExeclData(Row row, CandidatesImport candidateImport, String fileName, OrgLocations orgLocations) throws Exception
	{
		
		// boolean isValidated;
		if (row.getRowNum() > 0) { // Excluding column names
			if (row.getCell(0) == null || "".equalsIgnoreCase(row.getCell(0).toString())) {
				candidateImport.setReason("Required fields mandatory");
				return false;
			}
			if (row.getCell(2) == null || "".equalsIgnoreCase(row.getCell(2).toString())) {
				candidateImport.setReason("Required fields mandatory");
				return false;
			}
			else if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())
					&& !validateEmail(row.getCell(2).toString())) {
				candidateImport.setReason("Invalid Email");
				return false;
			}
			else if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
				Candidate candidate = candidateService.findByCandidateEmail(row.getCell(2).toString(), orgLocations.getId());
				if (candidate != null) {
					candidateImport.setReason("Candidate already exist.!");
					return false;
				}
			}
			if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
				String mobileNumber = getCellValueAsString(row.getCell(3), fileName);
				if (mobileNumber != null && !"".equalsIgnoreCase(mobileNumber)) {
					boolean isValidated = isMobileNumberValid(mobileNumber);
					if (isValidated == false) {
						candidateImport.setReason("Invalid MobileNumber");
						return isValidated;
					}
				}
			}
			
			if(row.getCell(13) !=null && !"".equalsIgnoreCase(row.getCell(13).toString())){
				String expectedRate=getCellValueAsString(row.getCell(13), fileName);
				
				if(expectedRate !=null && !"".equalsIgnoreCase(expectedRate)){
					boolean isValidated =isExpectedRateValid(expectedRate);
					if(isValidated == false){
						candidateImport.setReason("Expected Rate Allow Only Integers");
						return isValidated;
					}
				}
			}
			
			if (row.getCell(9) == null || "".equalsIgnoreCase(row.getCell(9).toString())) {
				candidateImport.setReason("Employment Type is Required");
				return false;
			}
			
			if("C2C".equalsIgnoreCase(row.getCell(9).getStringCellValue())) {
				
			if(row.getCell(10) !=null && !"".equalsIgnoreCase(row.getCell(10).toString())){
				List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
				Long vendorId = 0l;
				for(OrgRelations rel : vendorsList) {
					if((row.getCell(10).toString().equalsIgnoreCase(rel.getLocationId().getOrgLocationName())) || (row.getCell(10).toString().equalsIgnoreCase(rel.getResponderLocationId().getOrgLocationName()))) {
						vendorId = rel.getId();
						break;
					}
				}
				
				
				if(row.getCell(11) !=null && !"".equalsIgnoreCase(row.getCell(11).toString())){
					List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(vendorId);
					
					for(OrgSpoc vendorContacts : vendorContactsList) {
						if(row.getCell(11).toString().equalsIgnoreCase(vendorContacts.getVendorUser().getEmail())) {
							return true;
						}else {
							candidateImport.setReason("No contact exists with entered email");
							return false;
						}
					}
					if(vendorContactsList == null || vendorContactsList.size() == 0) {
						candidateImport.setReason("No Active contacts exists with this vendor");
						return false;
					}
				}
				
			}
			
			/*else {
				if(row.getCell(11) !=null && !"".equalsIgnoreCase(row.getCell(11).toString())){
					candidateImport.setReason("vendor is mandatory if you have entered contact");
					return false;
				}else {
					candidateImport.setReason("vendor details are mandatory.");
					return false;
				}
			}*/
		}
			
		}
		return true;
	}
	
	/**
	 * Handles bulk candidate savings
	 * 
	 * @param userImport
	 * @param orgLocations
	 * @param loginUser
	 * @param isMailChecked
	 * @param fileName
	 * @throws Exception
	 */
	private void saveBulkCandidates(CandidatesImport candidatesImport, OrgLocations orgLocations, User user, String isMailChecked) throws Exception
	{
		
		CandidateDTO candidateDTO = populateJsonObject(candidatesImport, orgLocations,isMailChecked);
		candidateService.storeCandidate(candidateDTO, orgLocations, user);
		
		
	}
	
	private CandidateDTO populateJsonObject(CandidatesImport candidate, OrgLocations orgLocations, String isMailChecked) throws Exception
	{
		
		CandidateDTO  candidateDTO = new CandidateDTO();
		
		Candidate cand = candidateService.getCandidateByEmailAndOrgLocationId(candidate.getEmailId(), orgLocations.getId());
		
		if(cand != null && cand.getId() != null)
			candidateDTO.setCandidateId(String.valueOf(cand.getId()));
		
		//JSONObject jsonObj = new JSONObject();
		candidateDTO.setFirstName(candidate.getFirstName());
		if (null != candidate.getLastName()) {
			candidateDTO.setLastName(candidate.getLastName());
		}
		candidateDTO.setEmail(candidate.getEmailId());
		
//		jsonObj.put("email", candidate.getEmailId());
		if (null != candidate.getMobileNumber()) {
			candidateDTO.setMobilePhone(candidate.getMobileNumber());
		}
		if (null != candidate.getCountry()) {
			//jsonObj.put("country", candidate.getCountry());
			candidateDTO.setCountry(candidate.getCountry());
		}
		if (null != candidate.getState()) {
			candidateDTO.setState(candidate.getState());
		}
		if (null != candidate.getCity()) {
			candidateDTO.setCity(candidate.getCity());
		}
		if (null != candidate.getAddress()) {
			candidateDTO.setAddress(candidate.getAddress());
		}
		if (null != candidate.getDesignation()) {
			candidateDTO.setDesignation(candidate.getDesignation());
		}
		if (null != candidate.getDesignationType() && !"".equals(candidate.getDesignationType())) {
			candidateDTO.setDesignationType(candidate.getDesignationType());
		}
		else {
			//jsonObj.put("designationType", "C2C");
			candidateDTO.setDesignationType("W-2");
		}
		if (null != candidate.getExperience()) {
			if (candidate.getExperience().matches("[-a-zA-Z]+")) {
				//jsonObj.put("experienceYears", "0");
				candidateDTO.setExperienceYears("0");
				candidateDTO.setExperienceMonths("0");
			}else{
				 String exp[] = getExperienceYearsAndMonths(candidate.getExperience());
				 if(exp.length >1){
					 candidateDTO.setExperienceYears(exp[0]);
						if(!exp[1].matches("[a-zA-Z]+")){
							if(!"years".equalsIgnoreCase(exp[1])){
							int months = ((Number)NumberFormat.getInstance().parse(exp[1])).intValue();
							candidateDTO.setExperienceMonths(String.valueOf(months));
							}
								
						} 
				 }else{
					 if(exp[0].contains("years") || exp[0].contains("Years") ){
						 int years = ((Number)NumberFormat.getInstance().parse(exp[0])).intValue();
						 candidateDTO.setExperienceYears(String.valueOf(years));
						 candidateDTO.setExperienceMonths("0");
					 }else{
						 candidateDTO.setExperienceYears("");
						 candidateDTO.setExperienceMonths("");
					 }
					 
				 }
					
					
			}
		}
		/*if (null != candidate.getExperienceMonths()) {
			jsonObj.put("experienceMonths", candidate.getExperienceMonths());
		}*/
		if (null != candidate.getExpectedRate()) {
			String rate = getExpectedRate(candidate.getExpectedRate());
			if (null != rate) {
				candidateDTO.setExpectedRate(rate);
				if (null != candidate.getExpectedRateType()
						&& !"".equals(candidate.getExpectedRateType())) {
					candidateDTO.setRateType(candidate.getExpectedRateType());
				}
				else {
					// jsonObj.put("rateType", "Hourly");
					String countryCheck = orgLocations.getCountry();
					switch (countryCheck) {
						case "India":
							candidateDTO.setRateType("Monthly");
							break;
						default:
							candidateDTO.setRateType("Hourly");
					}
				}
				if ("India".equals(orgLocations.getCountry())) {
					
					//jsonObj.put("expectedRateCurrencey", "INR");
					candidateDTO.setExpectedRateCurrencey("INR");
				}
				else if ("Australia".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("AUD");
				}
				else if ("United States".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("USD");
				}
				else if ("Singapore".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("SGD");
				}
				else if ("United Kingdom".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("GBP");
				}
				else if ("France".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("EUR");
				}
			}
			else {
				
				if ("India".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("INR");
				}
				else if ("Australia".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("AUD");
				}
				else if ("United States".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("USD");
				}
				else if ("Singapore".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("SGD");
				}
				else if ("United Kingdom".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("GBP");
				}
				else if ("France".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("EUR");
				}
				String countryCheck = orgLocations.getCountry();
				switch (countryCheck) {
					case "India":
						//jsonObj.put("rateType", "Monthly");
						candidateDTO.setRateType("Monthly");
						break;
					default:
						candidateDTO.setRateType("Hourly");
				}
			}
		}
		if (null != candidate.getSkillsName()) {
			//jsonObj.put("candidateSkills", candidate.getSkillsName());
			candidateDTO.setCandidateSkills(candidate.getSkillsName());
		}
		
		if (null != candidate.getEducation()) {
			//jsonObj.put("candidateEducation", candidate.getEducation());
			candidateDTO.setCandidateEducation(candidate.getEducation());
		}
		
		if (null != candidate.getEmployers()) {
			candidateDTO.setCandidateEmployers(candidate.getEmployers());
		}
		
		if (null != candidate.getPrimarySkill()) {
			candidateDTO.setPrimarySkill(candidate.getPrimarySkill());
		}
		
		if (null != isMailChecked) {
			candidateDTO.setInviteEmail(isMailChecked);
		}
		
		// Vendor and Vendor Contact
		
		List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
		Long vendorId = 0l;
		for(OrgRelations rel : vendorsList) {
			if((candidate.getVendor().equalsIgnoreCase(rel.getLocationId().getOrgLocationName())) || (candidate.getVendor().equalsIgnoreCase(rel.getResponderLocationId().getOrgLocationName()))) {
				vendorId = rel.getId();
			}
		}
		if(vendorId > 0) {
			candidateDTO.setVendors(String.valueOf(vendorId));
		}
		
		List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(vendorId);
		
		for(OrgSpoc vendorContacts : vendorContactsList) {
			if(candidate.getVendorContact().equalsIgnoreCase(vendorContacts.getVendorUser().getEmail())) {
				candidateDTO.setVendorcontact(String.valueOf(vendorContacts.getId()));
			}
		}
		
		return candidateDTO;
	}
	
	private String[] getExperienceYearsAndMonths(String experience) {
    
		return experience.split("[\\p{Punct}\\s]+");
		
	}

	private String getExpectedRate(String expectedRate)
	{
		
		String[] Res = expectedRate.split("[\\p{Punct}\\s]+");
		for (String s : Res) {
			if (isNumeric(s)) {
				return s;
			}
		}
		return null;
	}
	
	
	
	/**
	 * Check given string is numeric or not
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	private String getCellValueAsString(Cell cell, String fileName)
	{
		String cellValue = "";
		if (fileName.toLowerCase().endsWith("xls")) {
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				hssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String hssfCellValue = hssfCell.getStringCellValue();
				if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
					cellValue = hssfCellValue;
				}
			}
			else {
				cellValue = cell.getStringCellValue();
			}
		}
		else {
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				xssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String xssfCellValue = xssfCell.getStringCellValue();
				if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
					cellValue = xssfCellValue;
				}
			}
			else {
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}
	
	/**
	 * Handles Mobile number valid or not
	 *  var rgx = /^[0-9]*\.?[0-9]*$/;
	 * @param mobileNmber
	 * @return
	 */
	private boolean isMobileNumberValid(String mobileNmber)
	{
		String regex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
		boolean flag;
		String mobNum = mobileNmber.trim();
		if (mobNum.length() > 9 && mobNum.length() <= 20) {
			flag = mobNum.matches(regex);
		}
		else {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * preparing excel headers (column names)
	 * 
	 * @param createExcelsheet
	 * @return Sheet
	 */
	private Sheet setCandidateExcelHeader(Sheet createExcelsheet)
	{
		
		Row excelHeader = createExcelsheet.createRow(0);
		excelHeader.createCell(0).setCellValue(Constants.CANDIDATE_FIRST_NAME);
		excelHeader.createCell(1).setCellValue(Constants.CANDIDATE_LAST_NAME);
		excelHeader.createCell(2).setCellValue(Constants.CANDIDATE_EAMIL);
		excelHeader.createCell(3).setCellValue(Constants.CANDIDATE_PHONE_NUMBER);
		excelHeader.createCell(4).setCellValue(Constants.CANDIDATE_COUNTRY);
		excelHeader.createCell(5).setCellValue(Constants.CANDIDATE_STATE);
		excelHeader.createCell(6).setCellValue(Constants.CANDIDATE_CITY);
		excelHeader.createCell(7).setCellValue(Constants.CANDIDATE_ADDRESS);
		excelHeader.createCell(8).setCellValue(Constants.CANDIDATE_DESIGNATION);
		excelHeader.createCell(9).setCellValue(Constants.CANDIDATE_TYPE);
		
		//vendor,Vendor Contact
		excelHeader.createCell(10).setCellValue(Constants.CANDIDATE_VENDOR_NAME);
		excelHeader.createCell(11).setCellValue(Constants.CANDIDATE_VENDOR_CONTACT);
		
		excelHeader.createCell(12).setCellValue(Constants.CANDIDATE_EXPERIENCE);
		/*excelHeader.createCell(11).setCellValue(Constants.CANDIDATE_EXPERIENCE_MONTHS);*/
		excelHeader.createCell(13).setCellValue(Constants.CANDIDATE_EXPECTED_RATE);
		excelHeader.createCell(14).setCellValue(Constants.CANDIDATE_EXPECTED_RATE_TYPE);
		excelHeader.createCell(15).setCellValue(Constants.CANDIDATE_SKILLS_NAME);
		excelHeader.createCell(16).setCellValue(Constants.CANDIDATE_EDUCATION);
		excelHeader.createCell(17).setCellValue(Constants.CANDIDATE_EMPLOERS);
		excelHeader.createCell(18).setCellValue(Constants.CANDIDATE_PRIMARY_SKILL);
		
		//India location only
		excelHeader.createCell(19).setCellValue(Constants.CANDIDATE_PAN_NUMBER);
		excelHeader.createCell(20).setCellValue(Constants.CANDIDATE_AADHAR_NUMBER);
		
		//United States location only
		excelHeader.createCell(21).setCellValue(Constants.CANDIDATE_SSN_NUMBER);
		
		excelHeader.createCell(22).setCellValue(Constants.FAILED_REASON);
		return createExcelsheet;
	}
	
	/**
	 * validate email
	 * 
	 * @param email
	 * @return boolean
	 */
	private boolean validateEmail(String email)
	{
		String emailId = email.trim();
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(emailId);
		return matcher.matches();
	}
	
	
	/**
	 * Handles Expected Rate is valid or not
	 * @param expectedRate
	 * @return
	 */
	private boolean isExpectedRateValid(String expectedRate)
	{
		String regex = "^[0-9\b]+$";
		boolean flag;
		String expRate = expectedRate.replaceAll("[^\\d.]", "");
		if (expRate.length() >= 0 && expRate.length() <= 8) {
			flag = expRate.matches(regex);
		}
		else {
			flag = false;
		}
		return flag;
	}
	
	/**Returns Expected RATE
	 * @param expectedRate
	 * @return
	 */
	private String getExpectedRateValid(String expectedRate)
	{
		String regex = "^[0-9\b]+$";
		String  eRate = "";
		String expRate = expectedRate.replaceAll("[^\\d.]", "");
		if (expRate.length() >= 0 && expRate.length() <= 8) {
			boolean flag = expRate.matches(regex);
			if(flag){
				eRate = expRate;
			}
		}
		
		return eRate;
	}
	
	
	/**
	 * validate Pan Number
	 * 
	 * @param panNumber
	 * @return boolean
	 */
	private boolean validatePanNumber(String panNumber)
	{
		String panNo = panNumber.trim();
		Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		Matcher matcher = pattern.matcher(panNo);
		return matcher.matches();
	}
	
	/**
	 * validate Aadhar Number
	 * @param aadharNumber
	 * @return boolean
	 */
	private boolean validateAadharNumber(String aadharNumber)
	{
		String aadharNo = aadharNumber.trim();
		Pattern pattern = Pattern.compile("/^\\d{4}\\s\\d{4}\\s\\d{4}$/g");
		Matcher matcher = pattern.matcher(aadharNo);
		return matcher.matches();
	}
	
	/**
	 * validate SSN Number
	 * @param ssnNumber
	 * @return boolean
	 */
	private boolean validateSsnNumber(String ssnNumber)
	{
		String ssnNo = ssnNumber.trim();
		Pattern pattern = Pattern.compile("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$");
		Matcher matcher = pattern.matcher(ssnNo);
		return matcher.matches();
	}
	
	
}