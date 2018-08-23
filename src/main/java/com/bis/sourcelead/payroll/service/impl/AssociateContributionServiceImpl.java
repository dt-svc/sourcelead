package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.bis.sourcelead.payroll.dao.PayrollContributionDao;
import com.bis.sourcelead.payroll.dto.AssociateContributionDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
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

}
