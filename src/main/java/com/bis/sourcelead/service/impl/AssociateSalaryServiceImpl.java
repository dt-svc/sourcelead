package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.LeaveTypeImportDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SalaryDTO;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PayItemDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.entity.PayItem;
import com.bis.sourcelead.payroll.service.PayItemService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateSalaryServiceImpl implements AssociateSalaryService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateSalaryServiceImpl.class);
		
	@Autowired
	AssociateSalaryDao associateSalaryDao;
	
	@Autowired
	private UserGroupDao userGroupDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	private PayItemDao payItemDao;

	@Override
	public Response<AssociateSalaryDTO> addAssociateSal(OrgLocations orgLocation, AssociateSalaryDTO associateSalaryDTO, User loginUser)
	{
		
		Response<AssociateSalaryDTO> resObj = new Response<>();
		try {
			Date createdDate = null;
			Date updatedDate = null;
			Long createdBy = null;
			Long updatedBy = null;
			if (Validator.validate(associateSalaryDTO)) {
				AssociateSalary associateSalary = null;
				if(associateSalaryDTO.getUserId() != null) {
					Candidate candidate = null;
//					associateSalary = associateSalaryDao.findAssociateSalaryById(associateSalaryDTO.getId());
					candidate = candidateDao.getCandidateByConsultantUserId(associateSalaryDTO.getUserId(),orgLocation.getId());
					associateSalary = associateSalaryDao.findAssociateSalaryByCandidateId(candidate.getId());
		
					if(associateSalary != null) {
						associateSalary.setUpdatedDate(new Date());
						associateSalary.setUpdatedBy(loginUser.getId());
						
						createdDate = associateSalary.getEffectiveDate();
						
					}else {
						associateSalary  = new AssociateSalary();
//						User user = userService.getUserById(associateSalaryDTO.getUserId());
//						 candidate = candidateDao.getCandidateByConsultantUserId(associateSalaryDTO.getUserId(),orgLocation.getId());
						associateSalary.setCandidate(candidate);
						Set<AssociatePayItem> assoPayItemSet = getAssociatePayItemSet(associateSalary,associateSalaryDTO,orgLocation,loginUser);
						associateSalary.setAssociatePayItem(assoPayItemSet);
						associateSalary.setCreatedDate(new Date());
						associateSalary.setCreatedBy(loginUser.getId());
					}
				}
				
				if(StringUtils.isNotBlank(associateSalaryDTO.getEffectiveDate()))
					updatedDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), associateSalaryDTO.getEffectiveDate());
				
				/*else {
					associateSalary  = new AssociateSalary();
//					User user = userService.getUserById(associateSalaryDTO.getUserId());
					Candidate candidate = candidateDao.getCandidateByConsultantUserId(associateSalaryDTO.getUserId(),orgLocation.getId());
					associateSalary.setCandidate(candidate);
					Set<AssociatePayItem> assoPayItemSet = getAssociatePayItemSet(associateSalary,associateSalaryDTO,orgLocation,loginUser);
					associateSalary.setAssociatePayItem(assoPayItemSet);
				}*/
				UserGroup uesrgroup = userGroupDao.findUserGroupById(associateSalaryDTO.getPayGroup());
				associateSalary.setGroupId(uesrgroup);
				associateSalary.setPayType(associateSalaryDTO.getPayType());
				associateSalary.setPayFrequency(associateSalaryDTO.getPayFrequency());
				associateSalary.setStandardHours(associateSalaryDTO.getStandardHours());
				associateSalary.setAnnualSalary(new BigDecimal(associateSalaryDTO.getAnnualSal()));
				associateSalary.setGrossSalary(new BigDecimal(associateSalaryDTO.getPerPaySal()));
				if(StringUtils.isNotBlank(associateSalaryDTO.getHourlyRate())) {
					associateSalary.setHourlyRate(new BigDecimal(associateSalaryDTO.getHourlyRate()));
				}
				associateSalary.setAllowOverTimePay(associateSalaryDTO.getAllowOverTimePay());
				associateSalary.setOrgLocations(orgLocation);
//				associateSalary.setCreatedDate(new Date());
				associateSalary.setUpdatedDate(new Date());
//				associateSalary.setCreatedBy(loginUser.getId());
				associateSalary.setUpdatedBy(loginUser.getId());
				associateSalary.setStatus(1);
				associateSalary.setAssociateSalaryUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				if(associateSalaryDTO.getIncreasePer() != null) {
					associateSalary.setIncrementPercentage(associateSalaryDTO.getIncreasePer());
				}
				if(associateSalaryDTO.getEffectiveDate() != null && !"".equalsIgnoreCase(associateSalaryDTO.getEffectiveDate())) {
					associateSalary.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), associateSalaryDTO.getEffectiveDate()));
				}
				if(associateSalaryDTO.getChangeReason() != null) {
					associateSalary.setChangeReason(associateSalaryDTO.getChangeReason());
				}
				if(associateSalaryDTO.getSalaryType() != null) {
					associateSalary.setSalaryType(associateSalaryDTO.getSalaryType());
				}
				
				AssociateSalary saveAssociateSalary = associateSalaryDao.addAssociateSal(associateSalary);
				if (saveAssociateSalary != null) {
					if(StringUtils.isNotBlank(associateSalaryDTO.getId())) {
						updateAssociateSalHistory(saveAssociateSalary,associateSalaryDTO,loginUser,orgLocation,createdBy,updatedBy,createdDate,updatedDate);
					}
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					associateSalaryDTO.setId(String.valueOf(saveAssociateSalary.getId()));
					resObj.setObject(associateSalaryDTO);
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

	private void updateAssociateSalHistory(AssociateSalary associateSalary, AssociateSalaryDTO associateSalaryDTO, User loginUser, OrgLocations orgLocation, Long preCreatedBy, Long preUpdatedBy, Date preCreatedDate, Date preUpdatedDate)
	{
		AssociateSalaryHistory associateSalHis = new AssociateSalaryHistory();
		associateSalHis.setAssociateSalary(associateSalary);
		associateSalHis.setPayType(associateSalary.getPayType());
		associateSalHis.setPayFrequency(associateSalary.getPayFrequency());
		associateSalHis.setStandardHours(associateSalary.getStandardHours());
		associateSalHis.setAnnualSalary(associateSalary.getAnnualSalary());
		associateSalHis.setGrossSalary(associateSalary.getGrossSalary());
		associateSalHis.setHourlyRate(associateSalary.getHourlyRate());
		associateSalHis.setPreviousPayType(associateSalaryDTO.getPreviousPayType());
		associateSalHis.setPreviousPayFrequency(associateSalaryDTO.getPreviousPayFrequency());
		associateSalHis.setPreviousStandardHours(associateSalaryDTO.getPreviousStandardHours());
		associateSalHis.setPreviousAnnualSalary(new BigDecimal(associateSalaryDTO.getPreviousAnnualSal()));
		associateSalHis.setPreviousGrossSalary(new BigDecimal(associateSalaryDTO.getPreviousPerPaySal()));
		associateSalHis.setPreviousHourlyRate(new BigDecimal(associateSalaryDTO.getPreviousHourlyRate()));
		associateSalHis.setAssociateSalHistoryUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		associateSalHis.setStatus(1);
		if(associateSalaryDTO.getIncreasePer() != null) {
			associateSalHis.setIncreasePercentage(associateSalaryDTO.getIncreasePer());
		}
		if(associateSalaryDTO.getEffectiveDate() != null && !"".equalsIgnoreCase(associateSalaryDTO.getEffectiveDate())) {
			associateSalHis.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), associateSalaryDTO.getEffectiveDate()));
		}
		if(associateSalaryDTO.getChangeReason() != null) {
			associateSalHis.setChangeReason(associateSalaryDTO.getChangeReason());
		}
		if(associateSalaryDTO.getSalaryType() != null) {
			associateSalHis.setSalaryType(associateSalaryDTO.getSalaryType());
		}
		associateSalHis.setCreatedDate(new Date());
		associateSalHis.setUpdatedDate(new Date());
		associateSalHis.setCreatedBy(loginUser.getId());
		associateSalHis.setUpdatedBy(loginUser.getId());
		associateSalHis.setPreviousCreatedBy(preCreatedBy);
		associateSalHis.setPreviousUpdatedBy(preUpdatedBy);
		associateSalHis.setPreviousCreatedDate(preCreatedDate);
		associateSalHis.setPreviousUpdatedDate(preUpdatedDate);
		
		
		AssociateSalaryHistory associateSalaryHistory = associateSalaryDao.updaetAssociateSalHis(associateSalHis);
	}

	/**
	 * prepare child table (ASSOCIATE_PAY_ITEM) data from Associate Salary
	 * 
	 * @param associateSalary
	 * @param associateSalaryDTO
	 * @param orgLocation
	 * @param loginUser 
	 * @return Set<AssociatePayItem>
	 */
	private Set<AssociatePayItem> getAssociatePayItemSet(AssociateSalary associateSalary, AssociateSalaryDTO associateSalaryDTO, OrgLocations orgLocation, User loginUser)
	{
		List<AssociatePayItemDTO> list = associateSalaryDTO.getAssociatePayItemDto();
		
		Set<AssociatePayItem> set = new HashSet<>();
		if(list != null) {
			list.forEach(assoPayItem -> {
					if (assoPayItem.getPayItemCheck() != null && !"".equalsIgnoreCase(assoPayItem.getPayItemCheck())) {
						String amount = "0";
						AssociatePayItem associatePayItem = new AssociatePayItem();
						associatePayItem.setPayItemName(assoPayItem.getPayItemName());
						associatePayItem.setPayItemSchedule(assoPayItem.getPayItemSchedule());
						if (assoPayItem.getOverRide() != null) {
							associatePayItem.setOverRide(assoPayItem.getOverRide());
						}
						if (assoPayItem.getEffectiveDate() != null && !"".equalsIgnoreCase(assoPayItem.getEffectiveDate())) {
							associatePayItem.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assoPayItem.getEffectiveDate()));
						}
						if (assoPayItem.getEndDate() != null && !"".equalsIgnoreCase(assoPayItem.getEndDate())) {
							associatePayItem.setEndDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assoPayItem.getEndDate()));
						}
						if (assoPayItem.getPayItemAmount() != null && !"".equalsIgnoreCase(assoPayItem.getPayItemAmount())) {
							amount = assoPayItem.getPayItemAmount();
						}
						associatePayItem.setOrgPayItemId(assoPayItem.getPayItemId());
						associatePayItem.setAssociateSalary(associateSalary);
						associatePayItem.setPayItemAmount(new BigDecimal(amount));
						associatePayItem.setCreatedDate(new Date());
						associatePayItem.setUpdatedDate(new Date());
						associatePayItem.setCreatedBy(loginUser.getUserCode());
						associatePayItem.setUpdatedBy(loginUser.getUserCode());
						associatePayItem.setStatus(1);
						set.add(associatePayItem);
					}
			});
		}
		return set;
	}

	@Override
	public AssociateSalaryDTO findAssociateSalaryByCandidateId(Long cndidateId,OrgLocations orgLocation)
	{
		AssociateSalaryDTO associateSalaryDTO = null;
		try {
			 AssociateSalary asal = associateSalaryDao.findAssociateSalaryByCandidateId(cndidateId);
			    if(asal != null) {
			    	 associateSalaryDTO = new AssociateSalaryDTO();
					associateSalaryDTO.setId(String.valueOf(asal.getId()));
					associateSalaryDTO.setPayType(asal.getPayType());
					associateSalaryDTO.setPayFrequency(asal.getPayFrequency());
					associateSalaryDTO.setStandardHours(asal.getStandardHours());
					associateSalaryDTO.setAnnualSal(String.valueOf(asal.getAnnualSalary()));
					associateSalaryDTO.setPerPaySal(String.valueOf(asal.getGrossSalary()));
					associateSalaryDTO.setHourlyRate(String.valueOf(asal.getHourlyRate()));
					associateSalaryDTO.setAssociateSalUUID(asal.getAssociateSalaryUuid());
					associateSalaryDTO.setPayGroupUUID(asal.getGroupId().getGroupUUID());
					associateSalaryDTO.setPayGroup(asal.getGroupId().getGroupName());
					
					if(asal.getEffectiveDate() != null) {
						associateSalaryDTO.setEffectiveDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), asal.getEffectiveDate()));
					}else {
						associateSalaryDTO.setEffectiveDate("");
					}
					
					if(StringUtils.isNotBlank(asal.getIncrementPercentage())) {
						associateSalaryDTO.setIncreasePer(asal.getIncrementPercentage());
					}else {
						associateSalaryDTO.setIncreasePer("");
					}
					
					Set<AssociatePayItem> payItemList = asal.getAssociatePayItem();
					List<AssociatePayItemDTO> list = new ArrayList<>();
					List<PayItemDTO> allList = new ArrayList<>();
					if(payItemList != null && payItemList.size() > 0) {
						payItemList.forEach(pi->{
							AssociatePayItemDTO associatePayItemDTO = new AssociatePayItemDTO();
							associatePayItemDTO.setId(String.valueOf(pi.getId()));
							associatePayItemDTO.setPayItemId(pi.getOrgPayItemId());
							associatePayItemDTO.setPayItemName(pi.getPayItemName());
							associatePayItemDTO.setPayItemSchedule(pi.getPayItemSchedule());
							if(pi.getOverRide() != null && !"".equalsIgnoreCase(pi.getOverRide())) {
								associatePayItemDTO.setOverRide(pi.getOverRide());
							}else {
								associatePayItemDTO.setOverRide("--");
							}
							if(pi.getEffectiveDate() != null) {
								associatePayItemDTO.setEffectiveDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pi.getEffectiveDate()));
							}else {
								associatePayItemDTO.setEffectiveDate("--");
							}
							if(pi.getEndDate() !=null) {
								associatePayItemDTO.setEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pi.getEndDate()));
							}else {
								associatePayItemDTO.setEndDate("--");
							}
							associatePayItemDTO.setPayItemAmount(String.valueOf(pi.getPayItemAmount()));
							if(pi.getStatus() == 1)
							list.add(associatePayItemDTO);
						});
						
					}
					
					ResponseList<PayItem> allPayItemList = payItemDao.getPayItemByLocationId(orgLocation);
					
					if (allPayItemList.getData() != null && allPayItemList.getData().size() > 0) {
						
						if(list != null) {
							for(AssociatePayItemDTO payItem : list) {
								boolean s = allPayItemList.getData().removeIf(p->p.getId().longValue()== payItem.getPayItemId());
							}
						}
						allPayItemList.getData().forEach(item -> {
							PayItemDTO payItemDTO = new PayItemDTO();
							payItemDTO.setId(item.getId());
							payItemDTO.setPayItemName(item.getPayItemName());
							payItemDTO.setPaySchedule(item.getPaySchedule());
							payItemDTO.setEmpType(item.getAllowEmployeementType());
							payItemDTO.setAllowOverTime(item.getAllowOverTime());
							payItemDTO.setDescription(item.getDescription());
							payItemDTO.setPayItemUUID(item.getPayItemUuid());
							if(StringUtils.isNotBlank(asal.getCandidate().getDesignationType())){
								if(item.getAllowEmployeementType().contains(asal.getCandidate().getDesignationType())) {
									allList.add(payItemDTO);
								}
							}else {
								allList.add(payItemDTO);
							}
							
						});
					}
					
					associateSalaryDTO.setAssociatePayItemDto(list);
					associateSalaryDTO.setOrgPayItems(allList);
			    }	
		}catch (Exception e) {
			e.printStackTrace();
		}
		   
			
			
		return associateSalaryDTO;
	}

	@Override
	public AssociatePayItem findPayItemByPayItemId(Long payItemId) {
		return associateSalaryDao.findPayItemByPayItemId(payItemId);
	}

	@Override
	public AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem) {
		return associateSalaryDao.saveUpdatedPayItem(associatePayItem);
	}

	@Override
	public List<AssociateSalary> getAssociateSalaryListByGroupId(OrgLocations orgLocation,long groupId,String payType) {
		
		List<AssociateSalary> list = associateSalaryDao.getAssociateSalaryListByGroupId(orgLocation,groupId,payType);
		return list;
	}

	@Override
	public List<AssociatePayItemDTO> findAssociatePayItemByPayrollId(Long payrollId)
	{
		return associateSalaryDao.findAssociatePayItemByPayrollId(payrollId);
	}
	
	public AssociateSalary getAssociateSalaryByCandidateIdAndOrgLocationId(Long candidateId, Long locationId)
	{
		return associateSalaryDao.getAssociateSalaryByCandidateIdAndOrgLocationId(candidateId, locationId);
	}

	@Override
	public ResponseList<AssociateSalary> getAssociateSalaryListByLocationIdAndGroupId(OrgLocations orgLocation, Long groupId,CandidateDTO dto) {
		return associateSalaryDao.getAssociateSalaryListByLocationIdAndGroupId(orgLocation, groupId,dto);
	}
	
	@Override
	public ResponseList<AssociateSalaryInd> getAssociateSalaryIndListByLocationIdAndGroupId(OrgLocations orgLocation, Long groupId,CandidateDTO dto) {
		return associateSalaryDao.getAssociateSalaryIndListByLocationIdAndGroupId(orgLocation, groupId,dto);
	}

	@Override
	public List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId) {
		List<AssociateSalaryInd> list = associateSalaryDao.getIndAssociateSalaryListByGroupId(orgLocation, groupId);
		return list;
	}

	@Override
	public SalaryDTO getSalaryByGroupId(OrgLocations orgLocation, Long candidateId,Date freqStartDate, Date freqEndDate)
	{
		return associateSalaryDao.getSalaryByGroupId(orgLocation, candidateId,freqStartDate,freqEndDate);
	}
	
	@Override
	public List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId,
			String payType) {
		return associateSalaryDao.getIndAssociateSalaryListByGroupId(orgLocation,groupId,payType);
	}

	@Override
	public List<AssociateSalaryInd> getIndAssociateSalaryListByGroupIdForInitiatePayRoll(OrgLocations orgLocation,
			long groupId, String payType, Date fqStartDate, Date fqEndDate) {
		return associateSalaryDao.getIndAssociateSalaryListByGroupIdForInitiatePayRoll(orgLocation, groupId, payType, fqStartDate, fqEndDate);
	}

	@Override
	public AssociateSalary getAssociateSalaryBySalaryId(Long salaryId) {
		return associateSalaryDao.getAssociateSalaryBySalaryId(salaryId);
	}

}
