package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dao.PayrollContributionDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateContributionServiceImpl implements AssociateContributionService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateContributionServiceImpl.class);
	
	@Autowired
	PayrollContributionDao payrollContributionDao;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	AssociateContributionDao associateContributionDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	PayrollDeductionService payrollDeductionService;
	
	@Autowired
	AssociateDeductionDao associateDeductionDao;
	
	@Override
	public Response<AssociateContributionDTO> saveAssociateContribution(AssociateContributionDTO associateContributionDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<AssociateContributionDTO> resObj = new Response<>();
		try {
			if (Validator.validate(associateContributionDTO)) {
				
				Candidate candidate = candidateDao.getCandidateByConsultantUserId(Long.parseLong(associateContributionDTO.getUserId()),orgLocation.getId());
				
				if(associateContributionDTO.getSaveType().equalsIgnoreCase(Constants.NEW_TYPE))
				associateContributionDao.deleteAssociateContributionByCandidateIdAndOrgLocationId(candidate.getId(),orgLocation.getId());
				
				List<ContributionDTO> contributionDTOList = associateContributionDTO.getContributionDTOList();
				for (ContributionDTO contri : contributionDTOList) {
					AssociateContribution associateContribution = new AssociateContribution();
					if(contri.getContributionType() !=null) {
						PayrollContribution payrollContribution = payrollContributionDao.findPayrollContributionByID(Long.parseLong(contri.getContributionType()));
						associateContribution.setContributionTypeId(payrollContribution);
						associateContribution.setContributionAmount(contri.getContributionAmount());
						associateContribution.setContributionMethod(contri.getContributionMethod());
						
						associateContribution.setCandidate(candidate);
						associateContribution.setOrgLocationId(orgLocation);
						associateContribution.setAssociateContributionUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						associateContribution.setCreatedBy(loginUser.getUserCode());
						associateContribution.setUpdatedBy(loginUser.getUserCode());
						associateContribution.setCreatedDate(new Date());
						associateContribution.setUpdatedDate(new Date());
						associateContribution.setStatus(1);
						AssociateContribution saveAssociateDeduction = associateContributionDao.saveAssociateContribution(associateContribution);
						if (saveAssociateDeduction != null) {
							resObj.setResponse(Result.SUCCESS.name());
							resObj.setResponseMessage(Result.SUCCESS.getValue());
							associateContributionDTO.setId(String.valueOf(saveAssociateDeduction.getId()));
							resObj.setObject(associateContributionDTO);
						}
					}
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
	public ResponseList<ContributionDTO> findAllContributionSummaryByCandidateId(Long candidate, OrgLocations orgLocation)
	{
		ResponseList<ContributionDTO> res = new ResponseList<>();
		try {
			List<AssociateContribution> associateContrList = associateContributionDao.findAllContributionSummaryByCandidateId(candidate,orgLocation);
			List<ContributionDTO> list = new ArrayList<>();
			if(associateContrList != null) {
				associateContrList.forEach(con->{
					ContributionDTO contributionDTO = new ContributionDTO();
					contributionDTO.setId(String.valueOf(con.getId()));
					contributionDTO.setContributionType(con.getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
					contributionDTO.setContributionAmount(con.getContributionAmount());
					contributionDTO.setAmount(con.getContributionAmount().doubleValue());
					contributionDTO.setContributionMethod(con.getContributionMethod());
					contributionDTO.setContributionSchedule(con.getContributionTypeId().getDeductionTypeId().getPayrollScheduled());
					contributionDTO.setDescription(con.getContributionTypeId().getDescription());
					contributionDTO.setAssociateContributionUUID(con.getAssociateContributionUuid());
					contributionDTO.setPayrollDeductionId(String.valueOf(con.getContributionTypeId().getDeductionTypeId().getId()));
					contributionDTO.setTaxType(con.getContributionTypeId().getDeductionTypeId().getTaxType());
					contributionDTO.setStatus(con.getStatus());
					list.add(contributionDTO);
				});
				res.setData(list);
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public AssociateContribution findAssociateContributionById(Long assContriId)
	{
		return associateContributionDao.findAssociateContributionById(assContriId);
	}

	@Override
	public AssociateContribution getValidateContribution(Long candidateId,Long contriTypId, OrgLocations orgLocation) {
		AssociateContribution assContribution = associateContributionDao.getValidateContribution(candidateId,contriTypId,orgLocation);
		return assContribution;
	}

	@Override
	public AssociateContribution findContributionByUserIdContributionId(Long userId, Long contributionId)
	{
		return associateContributionDao.findContributionByUserIdContributionId(userId,contributionId);
	}
	
	@Override
	public String saveAssociateDedAndContriData(MultipartFile multipartFile,
			OrgLocations orgLocations) throws Exception {

		
//		long startTime = System.nanoTime();
		String fileName = multipartFile.getOriginalFilename();
//		AssociateDedAndContriErrorData associateDedAndContriErrorData = new AssociateDedAndContriErrorData();
		
		List<Candidate> candidateList = candidateService.getAllCandidatesByLocation(orgLocations.getId());
		Map<String, List<Candidate>> canMap = candidateList.stream().collect(Collectors.groupingBy(Candidate::getEmail, Collectors.toList()));
		
		Map<String, Long> canMap1 = candidateList.stream().collect(Collectors.toMap(Candidate::getEmail, Candidate::getId));
		
//		List<AssociateContributionDTO> dedDataList = associateContributionDao.getAssociateDedDataListByOrgLocationId(orgLocations.getId());
//		Map<String, List<AssociateContributionDTO>> contriMap = dedDataList.stream().collect(Collectors.groupingBy(AssociateContributionDTO::getCandidateId, Collectors.toList()));
//		
//		List<AssociateDeductionDTO> contriDataList = associateDeductionDao.getAssociateContriDataListByOrgLocationId(orgLocations.getId());
//		Map<String, List<AssociateDeductionDTO>> dedMap = dedDataList.stream().collect(Collectors.groupingBy(AssociateDeductionDTO::getCandidateId, Collectors.toList()));
		
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
				boolean isValid = isValidTemplate(rowIterator);
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
				
					
					if (row.getRowNum() > 0 && row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) { // Excluding column names
						if(isValid) {
							PayrollContribution pc = null;
							AssociateContribution assContribution = null;
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()) && row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								pc = payrollContributionDao.getContributionByDeductionTypeAndLocationId(getCellValueAsString(row.getCell(2), fileName), orgLocations.getId());
								assContribution = associateContributionDao.findAssociateContributionById(pc.getId());
								if(assContribution == null) {
									assContribution = new AssociateContribution();
								}
							if(row.getCell(1) != null  && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								if(!canMap.isEmpty()) {
									if(canMap.get(getCellValueAsString(row.getCell(1), fileName)) != null) {
										assContribution.setCandidate(canMap.get(getCellValueAsString(row.getCell(1), fileName)).get(0));
										
									}
								}
							}
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())   ) {
							
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()) && row.getCell(4) != null  && !"".equalsIgnoreCase(row.getCell(4).toString()))
							{
								if(assContribution.getCandidate() != null) {
									assContribution.setContributionTypeId(pc);
									assContribution.setContributionAmount(new BigDecimal(getCellValueAsString(row.getCell(4), fileName)));
									assContribution.setStatus(Constants.RECORD_ACTIVE);
									assContribution.setContributionMethod(pc.getPayrollContributionMethod());
									assContribution.setCreatedDate(new Date());
									assContribution.setOrgLocationId(orgLocations);
									assContribution.setAssociateContributionUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
									assContribution.setUpdatedDate(new Date());
									associateContributionDao.saveAssociateContribution(assContribution);
								}
							}
							}
							}
							
							
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString()))
							{
							PayrollDeduction pd = null;
							AssociateDeduction assDeduction = null;
							if(row.getCell(2) != null  && !"".equalsIgnoreCase(row.getCell(2).toString()) && row.getCell(3) != null  && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								pd = payrollDeductionService.getDeductionByDeductionTypeAndLocationId(getCellValueAsString(row.getCell(2), fileName), orgLocations.getId());
								 assDeduction = associateDeductionDao.findAssociateDeductionById(pd.getId());
								if(assDeduction == null) {
									assDeduction = new AssociateDeduction();
								}
								assDeduction.setCandidate(canMap.get(getCellValueAsString(row.getCell(1), fileName)).get(0));
								if(assDeduction.getCandidate() != null) {
									assDeduction.setDeductionTypeId(pd);
									assDeduction.setDeductionAmount(new BigDecimal(getCellValueAsString(row.getCell(3), fileName)));
									assDeduction.setStatus(Constants.RECORD_ACTIVE);
									assDeduction.setDeductionMethod(pd.getPayrollDeductionMethod());
									assDeduction.setOrgLocationId(orgLocations);
									assDeduction.setAssociateDeductionUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
									assDeduction.setCreatedDate(new Date());
									assDeduction.setUpdatedDate(new Date());
									associateDeductionDao.saveAssociateDeduction(assDeduction);
								}
							}
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
			
//			insertedCount =  associateContributionDao.saveAssociateDedAndContriDetails(orgLocations.getId(), list, canMap);
//			associateDedAndContriErrorData.setErrorData(errorlist);
//			associateDedAndContriErrorData.setInsertedCount(insertedCount);
			fis.close();
//			long endTime   = System.nanoTime();
//			long totalTime = endTime - startTime;
//			System.out.println(totalTime);
		}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isValidTemplate(Iterator<Row> rowIterator) {

		boolean isValid = false;
		Row row = (Row) rowIterator.next();
		String name = "";
		String address = "";
		if (row.getRowNum() == 0) {
				if (Constants.ASSOCIATE_ID.equalsIgnoreCase(row.getCell(0).getStringCellValue())) {
					if (Constants.ASSOCIATE_EMAIL.equalsIgnoreCase(row.getCell(1).getStringCellValue())) {
						if (Constants.DEDUCTION_TYPE.equalsIgnoreCase(row.getCell(2).getStringCellValue())) {
							if (Constants.DEDUCT_AMOUNT.equalsIgnoreCase(row.getCell(3).getStringCellValue())) {
								if (Constants.CONTRIBUTE_AMOUNT.equalsIgnoreCase(row.getCell(4).getStringCellValue())) {
									isValid = true;
								}
							}
						}
					}
				}
		}
		return isValid;
	
	}
	
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
