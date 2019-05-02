package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.AssociateSalaryIndDTO;
import com.bis.sourcelead.dto.AssociateSalaryPayheadIndDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalaryHistoryInd;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.PayrollPayheadSalarySetup;
import com.bis.sourcelead.entity.PayrollPaytype;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PayrollPayheadServiceImpl implements PayrollPayheadService{
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil payrollCommonUtil;
	
	@Autowired
	UserGroupDao userGroupDao;

	@Override
	public PayrollPayhead savePayrollPayhead(PayrollPayheadDTO payrollPayheadDTO, User user, OrgLocations orgLocations) throws Exception {
		PayrollPayhead payrollPayhead = null;
		if(StringUtils.isNotBlank(payrollPayheadDTO.getUuid())) {
			payrollPayhead = getPayrollPayheadByUUID(payrollPayheadDTO.getUuid());
			
			payrollPayhead.setUpdatedDate(new Date());
			payrollPayhead.setUpdatedBy(user);
		}else {
			payrollPayhead = new PayrollPayhead();
			payrollPayhead.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			payrollPayhead.setActive(Constants.STATUS_YES);
			payrollPayhead.setCreatedDate(new Date());
			payrollPayhead.setCreatedBy(user);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getName())) {
			payrollPayhead.setName(payrollPayheadDTO.getName());
		}else {
			payrollPayhead.setName(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getShortCode())) {
			payrollPayhead.setShortCode(payrollPayheadDTO.getShortCode().toUpperCase());
		}else {
			payrollPayhead.setShortCode(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getEffectiveDate())) {
			Date paymentDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollPayheadDTO.getEffectiveDate());
			payrollPayhead.setEffectiveDate(paymentDate);
		}else {
			payrollPayhead.setEffectiveDate(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getType())) {
			payrollPayhead.setType(payrollPayheadDTO.getType());
		}else {
			payrollPayhead.setType(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPayType())) {
			PayrollPaytype payrollPaytype = new PayrollPaytype();
			payrollPaytype.setId(Long.parseLong(payrollPayheadDTO.getPayType()));
			payrollPayhead.setPayType(payrollPaytype);
		}else {
			payrollPayhead.setPayType(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getForm16())) {
			payrollPayhead.setForm16(payrollPayheadDTO.getForm16());
		}else {
			payrollPayhead.setForm16(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getTax())) {
			payrollPayhead.setTax(payrollPayheadDTO.getTax());
		}else {
			payrollPayhead.setTax(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getApplicableFor())) {
			payrollPayhead.setApplicableFor(payrollPayheadDTO.getApplicableFor());
			if(payrollPayheadDTO.getApplicableFor().equalsIgnoreCase("Experienced")) {
				if(StringUtils.isNotBlank(payrollPayheadDTO.getExperienceYears())) {
					payrollPayhead.setExperienceYears(payrollPayheadDTO.getExperienceYears());
				}else {
					payrollPayhead.setExperienceYears(null);
				}
				if(StringUtils.isNotBlank(payrollPayheadDTO.getExperienceMonths())) {
					payrollPayhead.setExperienceMonths(payrollPayheadDTO.getExperienceMonths());
				}else {
					payrollPayhead.setExperienceMonths(null);
				}
			}else {
				payrollPayhead.setExperienceYears(null);
				payrollPayhead.setExperienceMonths(null);
			}
		}else {
			payrollPayhead.setApplicableFor(null);
		}
		
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getComponent())) {
			payrollPayhead.setComponent(payrollPayheadDTO.getComponent());
		}else {
			payrollPayhead.setComponent(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPaidComponent())) {
			payrollPayhead.setPaidComponent(payrollPayheadDTO.getPaidComponent());
		}else {
			payrollPayhead.setPaidComponent("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPartOfGratitutiy())) {
			payrollPayhead.setPartOfGratitutiy(payrollPayheadDTO.getPartOfGratitutiy());
		}else {
			payrollPayhead.setPartOfGratitutiy("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getFbpComponent())) {
			payrollPayhead.setFbpComponent(payrollPayheadDTO.getFbpComponent());
		}else {
			payrollPayhead.setFbpComponent("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getLeaveDependent())) {
			payrollPayhead.setLeaveDependent(payrollPayheadDTO.getLeaveDependent());
		}else {
			payrollPayhead.setLeaveDependent("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getShowInPayslip())) {
			payrollPayhead.setShowInPayslip(payrollPayheadDTO.getShowInPayslip());
		}else {
			payrollPayhead.setShowInPayslip("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPfApplicable())) {
			payrollPayhead.setPfApplicable(payrollPayheadDTO.getPfApplicable());
		}else {
			payrollPayhead.setPfApplicable("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getEsiApplicable())) {
			payrollPayhead.setEsiApplicable(payrollPayheadDTO.getEsiApplicable());
		}else {
			payrollPayhead.setEsiApplicable("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getMethod())) {
			payrollPayhead.setMethod(payrollPayheadDTO.getMethod());
		}else {
			payrollPayhead.setMethod(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getFormula())) {
			payrollPayhead.setFormula(payrollPayheadDTO.getFormula());
		}else {
			payrollPayhead.setFormula(null);
		}
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPtApplicable())) {
			payrollPayhead.setPtApplicable(payrollPayheadDTO.getPtApplicable());
		}else {
			payrollPayhead.setPtApplicable("N");
		}
		
		
		payrollPayhead.setOrgLocationId(orgLocations);
		payrollPayhead.setStatus(Constants.RECORD_ACTIVE);
		
		return payrollPayheadDao.savePayrollPayhead(payrollPayhead);
	}

	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadList(OrgLocations orgLocations) throws Exception {
		List<PayrollPayhead> payrollPayheadList = payrollPayheadDao.getPayrollPayheadList(orgLocations);
		ResponseList<PayrollPayheadDTO> payrollPayheadDTOList = new ResponseList<>();
		payrollPayheadDTOList.setData(payrollCommonUtil.setPayrollPayheadList(payrollPayheadList,orgLocations));
		return payrollPayheadDTOList;
	}
	
	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadListByPayheadComponentType(OrgLocations orgLocations, String component) throws Exception{
		List<PayrollPayhead> payrollPayheadList = payrollPayheadDao.getPayrollPayheadListByPayheadComponentType(orgLocations, component);
		ResponseList<PayrollPayheadDTO> payrollPayheadDTOList = new ResponseList<>();
		payrollPayheadDTOList.setData(payrollCommonUtil.setPayrollPayheadList(payrollPayheadList,orgLocations));
		return payrollPayheadDTOList;
	}
	
	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,
			OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao.getPayrollPayheadListWithPagination(payrollPayheadDTO,orgLocations);
	}
	
	
	@Override
	public PayrollPayhead getPayrollPayheadByUUID(String uuid) throws Exception {
		return payrollPayheadDao.getPayrollPayheadByUUID(uuid);
	}

	@Override
	public Boolean findPayrollPayheadByNameAndLocationId(PayrollPayheadDTO payrollPayheadDTO) throws Exception {
		Boolean flag = payrollPayheadDao.findPayrollPayheadByNameAndLocationId(payrollPayheadDTO);
		return flag;
	}
	
	@Override
	public PayrollPayheadSalarySetup savePayrollPayheadSalarySetup(PayrollPayheadSalarySetup payrollPayheadSalarySetup) throws Exception {
		return payrollPayheadDao.savePayrollPayheadSalarySetup(payrollPayheadSalarySetup);
	}

	/*@Override
	public ResponseList<PayrollPayheadSalarySetupDTO> getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(String uuid,
			OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao. getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(uuid, orgLocations);
	}*/
	
	
	@Override
	public AssociateSalaryInd saveAssociateSalaryInd(AssociateSalaryIndDTO associateSalaryIndDTO, User user, OrgLocations orgLocations) throws Exception {
		AssociateSalaryInd associateSalaryInd = null;
		AssociateSalaryHistoryInd associateSalaryHistoryInd = new AssociateSalaryHistoryInd();
		if(StringUtils.isNotBlank(associateSalaryIndDTO.getUuid())) {
			associateSalaryInd = getAssociateSalaryIndByUUID(associateSalaryIndDTO.getUuid());
			associateSalaryHistoryInd =  payrollCommonUtil.setPreviousDataToAssociateSalaryHistoryInd(associateSalaryInd, associateSalaryHistoryInd);
			associateSalaryInd.setUpdatedDate(new Date());
			associateSalaryInd.setUpdatedBy(user);
		}else {
			associateSalaryInd = new AssociateSalaryInd();
			associateSalaryInd.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			associateSalaryInd.setCreatedDate(new Date());
			associateSalaryInd.setCreatedBy(user);
		}
		
		UserGroup uesrgroup = userGroupDao.findUserGroupById(associateSalaryIndDTO.getPayGroup());
		associateSalaryInd.setGroupId(uesrgroup);
		associateSalaryInd.setPayType(associateSalaryIndDTO.getPayType());
		associateSalaryInd.setPayFrequency(associateSalaryIndDTO.getPayFrequency());
		associateSalaryInd.setStandardHours(associateSalaryIndDTO.getStandardHours());
		associateSalaryInd.setAnnualSalary(new BigDecimal(associateSalaryIndDTO.getAnnualSal()));
		associateSalaryInd.setGrossSalary(new BigDecimal(associateSalaryIndDTO.getPerPaySal()));
		if(StringUtils.isNotBlank(associateSalaryIndDTO.getHourlyRate())) {
			associateSalaryInd.setHourlyRate(new BigDecimal(associateSalaryIndDTO.getHourlyRate()));
		}
		associateSalaryInd.setOrgLocations(orgLocations);
		if(associateSalaryIndDTO.getIncreasePer() != null) {
			associateSalaryInd.setIncrementPercentage(associateSalaryIndDTO.getIncreasePer());
		}
		if(associateSalaryIndDTO.getEffectiveDate() != null && !"".equalsIgnoreCase(associateSalaryIndDTO.getEffectiveDate())) {
			associateSalaryInd.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateSalaryIndDTO.getEffectiveDate()));
		}
		if(associateSalaryIndDTO.getChangeReason() != null) {
			associateSalaryInd.setChangeReason(associateSalaryIndDTO.getChangeReason());
		}
		if(associateSalaryIndDTO.getSalaryType() != null) {
			associateSalaryInd.setSalaryType(associateSalaryIndDTO.getSalaryType());
		}
		
		if(associateSalaryIndDTO.getCandidateId() != null) {
			Candidate can = new Candidate();
			can.setId(associateSalaryIndDTO.getCandidateId());
			associateSalaryInd.setCandidate(can);
		}else {
			associateSalaryInd.setCandidate(null);
		}
		
		associateSalaryInd.setStatus(Constants.RECORD_ACTIVE);
		
		
		HashSet<AssociateSalaryPayheadInd> associateSalaryPayheadIndList = new HashSet<AssociateSalaryPayheadInd>();
		if (associateSalaryIndDTO.getAssociateSalaryPayheadIndList() != null && !associateSalaryIndDTO.getAssociateSalaryPayheadIndList().isEmpty()) {
			for (AssociateSalaryPayheadIndDTO associateSalaryPayheadIndDTO : associateSalaryIndDTO.getAssociateSalaryPayheadIndList()) {
				 if(associateSalaryPayheadIndDTO.getChecked() != null && associateSalaryPayheadIndDTO.getChecked()) {
					 AssociateSalaryPayheadInd associateSalaryPayheadInd = new AssociateSalaryPayheadInd();
						if(associateSalaryPayheadIndDTO.getId() != null && associateSalaryPayheadIndDTO.getId()>0) {
							associateSalaryPayheadInd.setId(associateSalaryPayheadIndDTO.getId());
							associateSalaryPayheadInd.setUpdatedDate(new Date());
							associateSalaryPayheadInd.setUpdatedBy(user);
						}
						associateSalaryPayheadInd.setAssociateSalaryInd(associateSalaryInd);
						if (associateSalaryPayheadIndDTO.getPayrollPayheadId() != null) {
							PayrollPayhead payrollPayhead = new PayrollPayhead();
							payrollPayhead.setId(associateSalaryPayheadIndDTO.getPayrollPayheadId());
							payrollPayhead.setShortCode(associateSalaryPayheadIndDTO.getPayrollPayheadShortCode());
							associateSalaryPayheadInd.setPayrollPayhead(payrollPayhead);
						}
						if (associateSalaryPayheadIndDTO.getMonthly() != null) {
							associateSalaryPayheadInd.setMonthly(associateSalaryPayheadIndDTO.getMonthly());
						}
						if (associateSalaryPayheadIndDTO.getAnnualy() != null) {
							associateSalaryPayheadInd.setAnnualy(associateSalaryPayheadIndDTO.getAnnualy());
						}
						associateSalaryPayheadInd.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						associateSalaryPayheadInd.setStatus(Constants.RECORD_ACTIVE);
						associateSalaryPayheadInd.setCreatedDate(new Date());
						associateSalaryPayheadInd.setCreatedBy(user);
						associateSalaryPayheadIndList.add(associateSalaryPayheadInd);
				 }else if((associateSalaryPayheadIndDTO.getChecked() == null || !associateSalaryPayheadIndDTO.getChecked()) && (associateSalaryPayheadIndDTO.getId() != null && associateSalaryPayheadIndDTO.getId()>0)) {
					 	AssociateSalaryPayheadInd associateSalaryPayheadInd = payrollPayheadDao.getAssociateSalaryPayheadIndById(associateSalaryPayheadIndDTO.getId());
						associateSalaryPayheadInd.setStatus(Constants.RECORD_INACTIVE);
						associateSalaryPayheadInd.setUpdatedDate(new Date());
						associateSalaryPayheadInd.setUpdatedBy(user);
						associateSalaryPayheadIndList.add(associateSalaryPayheadInd);
				 }
				 
			}
		}
		associateSalaryInd.setAssociateSalaryPayheadIndList(associateSalaryPayheadIndList);
		
		associateSalaryInd = payrollPayheadDao.saveAssociateSalaryInd(associateSalaryInd);
		
		//save History
		associateSalaryHistoryInd =  payrollCommonUtil.setNewDataToAssociateSalaryHistoryInd(associateSalaryInd, associateSalaryHistoryInd);
		payrollPayheadDao.saveAssociateSalaryHistoryInd(associateSalaryHistoryInd);
		
		return associateSalaryInd;
	}
	
	@Override
	public AssociateSalaryInd getAssociateSalaryIndByUUID(String uuid) throws Exception {
		return payrollPayheadDao.getAssociateSalaryIndByUUID(uuid);
	}
	
	@Override
	public AssociateSalaryInd getAssociateSalaryIndByCandidateId(Long id) throws Exception {
		return payrollPayheadDao.getAssociateSalaryIndByCandidateId(id);
	}

	@Override
	public PayrollPayhead getPayrollPayheadByShortCodeAndOrglocation(String shortCode, Long orgLocationId)
			throws Exception {
		
		return payrollPayheadDao.getPayrollPayheadByShortCodeAndOrglocation(shortCode, orgLocationId);
	}

	@Override
	public AssociateSalaryInd saveAssociateSalaryInd(AssociateSalaryInd associateSalaryInd) throws Exception {
		return payrollPayheadDao.saveAssociateSalaryInd(associateSalaryInd);
	}
	
	@Override
	public AssociateSalaryHistoryInd saveAssociateSalaryHistoryInd(AssociateSalaryHistoryInd associateSalaryHistoryInd) throws Exception{
		return payrollPayheadDao.saveAssociateSalaryHistoryInd(associateSalaryHistoryInd);
	}

	@Override
	public List<AssociateSalaryInd> getAssociateSalaryIndListByOrgLocationsId(Long orgLocationId) throws Exception {
		return payrollPayheadDao.getAssociateSalaryIndListByOrgLocationsId(orgLocationId);
	}

	@Override
	public Response<String> refreshPayheadsForOrg(Long orgLocationId) throws Exception {
		Response<String> response = new Response<>();
		OrgLocations sourceLeadorgLocation = new OrgLocations();
		sourceLeadorgLocation.setId(1l);
		List<PayrollPayhead> defaultPayheadList = payrollPayheadDao.getPayrollPayheadList(sourceLeadorgLocation);
		List<PayrollPayhead> payheadList = payrollPayheadDao.getAllPayrollPayheadListByOrgLocationId(orgLocationId);
		for(PayrollPayhead pph : defaultPayheadList) {
			if(payheadList.stream().filter(ph -> ph.getShortCode().equalsIgnoreCase(pph.getShortCode())).findAny().orElse(null) == null) {
				OrgLocations orgLoc = new OrgLocations();
				orgLoc.setId(orgLocationId);
				pph.setId(null);
				pph.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				pph.setActive(Constants.STATUS_YES);
				pph.setCreatedDate(new Date());
				pph.setUpdatedDate(new Date());
				pph.setOrgLocationId(orgLoc);
				payrollPayheadDao.savePayrollPayhead(pph);
			}
		}
		return response;
	}
	
	@Override
	public Response<String> payheadHandlerByOrg(PayrollPayheadDTO payrollPayheadDTO) throws Exception {
		return payrollPayheadDao.payheadHandlerByOrg(payrollPayheadDTO);
	}
	
}
