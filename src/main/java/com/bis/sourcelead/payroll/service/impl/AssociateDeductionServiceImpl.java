package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateDeductionServiceImpl implements AssociateDeductionService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateDeductionServiceImpl.class);
	
	@Autowired
	PayrollDeductionService payrollDeductionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateDeductionDao associateDeductionDao;
	
	@Autowired
	private CandidateService candidateService;
	
	@Override
	public Response<AssociateDeductionDTO> saveAssociateDeduction(AssociateDeductionDTO associateDeductionDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<AssociateDeductionDTO> resObj = new Response<>();
		try {
			if (Validator.validate(associateDeductionDTO)) {
				
				Candidate candidate = candidateService.getCandidateByConsultantUserId(Long.valueOf(associateDeductionDTO.getUserId()), orgLocation.getId());
				
				if(associateDeductionDTO.getSaveType().equalsIgnoreCase(Constants.NEW_TYPE))
				associateDeductionDao.deleteAssociateDeductionByCandidateIdAndOrgLocationId(candidate.getId(),orgLocation.getId());
				
				List<DeductionDTO> deductionDTOList = associateDeductionDTO.getDeductionDTOList();
				for (DeductionDTO ded : deductionDTOList) {
					AssociateDeduction associateDeduction = new AssociateDeduction();
					if(StringUtils.isNotBlank(ded.getDeductionType())) {
						PayrollDeduction deductionType = payrollDeductionService.getPayrollDeductionTypeById(Long.parseLong(ded.getDeductionType()));
						associateDeduction.setDeductionTypeId(deductionType);
						associateDeduction.setDeductionAmount(ded.getAmount());
						associateDeduction.setDeductionMethod(ded.getMethod());
//						User user = userService.getUserById(Long.parseLong(associateDeductionDTO.getUserId()));
//						associateDeduction.setUserId(user);
						
						associateDeduction.setCandidate(candidate);
						associateDeduction.setOrgLocationId(orgLocation);
						associateDeduction.setAssociateDeductionUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						associateDeduction.setCreatedBy(loginUser.getUserCode());
						associateDeduction.setUpdatedBy(loginUser.getUserCode());
						associateDeduction.setCreatedDate(new Date());
						associateDeduction.setUpdatedDate(new Date());
						associateDeduction.setStatus(1);
						
						AssociateDeduction saveAssociateDeduction = associateDeductionDao.saveAssociateDeduction(associateDeduction);
						if (saveAssociateDeduction != null) {
							resObj.setResponse(Result.SUCCESS.name());
							resObj.setResponseMessage(Result.SUCCESS.getValue());
							associateDeductionDTO.setId(String.valueOf(saveAssociateDeduction.getId()));
							resObj.setObject(associateDeductionDTO);
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
	public PayrollDeductionDTO getDeductionAmount(long deductionTypeId)
	{
		PayrollDeductionDTO payrollDeductionDTO = new PayrollDeductionDTO();
		PayrollDeduction payrollDeduction = payrollDeductionService.getPayrollDeductionTypeById(deductionTypeId);
		payrollDeductionDTO.setPayrollDeductionMethod(payrollDeduction.getPayrollDeductionMethod());
		payrollDeductionDTO.setDeductionAmount(payrollDeduction.getDeductionAmount());
		return payrollDeductionDTO;
	}

	@Override
	public ResponseList<DeductionDTO> findAllDeductionSummaryByCandidateId(Long candidate, OrgLocations orgLocation)
	{
		ResponseList<DeductionDTO>  res = new ResponseList<>();
		try {
			List<AssociateDeduction> associateDedList = associateDeductionDao.findAllDeductionSummaryByCandidateId(candidate,orgLocation);
			List<DeductionDTO> list = new ArrayList<>();
			if(associateDedList != null) {
				associateDedList.forEach(ad->{
					DeductionDTO deductionDTO = new DeductionDTO();
					deductionDTO.setId(String.valueOf(ad.getId()));
					deductionDTO.setDeductionType(ad.getDeductionTypeId().getPayrollDeductionType());
					deductionDTO.setDedSchedule(ad.getDeductionTypeId().getPayrollScheduled());
					deductionDTO.setMethod(ad.getDeductionMethod());
					deductionDTO.setAmount(ad.getDeductionAmount());
					deductionDTO.setDedAmount(ad.getDeductionAmount().doubleValue());
					deductionDTO.setDescription(ad.getDeductionTypeId().getDescription());
					deductionDTO.setAssociateDeductionUUID(ad.getAssociateDeductionUuid());
					deductionDTO.setDedTaxType(ad.getDeductionTypeId().getTaxType());
					if(ad.getDeductionTypeId().getStatus() == 1)
					list.add(deductionDTO);
				});
				res.setData(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public AssociateDeduction findAssociateDeductionById(Long associateDeductionId)
	{
		return associateDeductionDao.findAssociateDeductionById(associateDeductionId);
	}

	@Override
	public AssociateDeduction getValidateDeduction(Long deductionTypeId, OrgLocations orgLocation,Long candidateId) {
		AssociateDeduction associateDeduction = associateDeductionDao.getValidateDeduction(deductionTypeId,orgLocation,candidateId);
		return associateDeduction;
	}
	
	@Override
	public AssociateDeduction getAsociateDedByUserIdDeductionId(long deductionId, Long candidateId)
	{
		return associateDeductionDao.getAsociateDedByUserIdDeductionId(deductionId,candidateId);
	}
}
