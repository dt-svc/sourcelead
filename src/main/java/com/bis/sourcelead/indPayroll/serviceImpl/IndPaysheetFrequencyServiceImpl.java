package com.bis.sourcelead.indPayroll.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.indPayroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.indPayroll.dto.IndApprovePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.IndPayrollService;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.EmployeeSessionIndPayroll;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.PaymentAccountService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class IndPaysheetFrequencyServiceImpl implements IndPaysheetFrequencyService{
	
	private static final Logger logger = LoggerFactory.getLogger(IndPaysheetFrequencyServiceImpl.class);

	@Autowired
	IndPaysheetFrequencyDao indPaysheetFrequencyDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil indPayrollCommonUtil;
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	@Autowired
	PaymentAccountService paymentAccountService;
	
	@Autowired
	IndPayrollService indPayrollService;
	
	@Override
	public ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation,
			IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception {
		return indPaysheetFrequencyDao.getIndPaysheetFrequencyListByOrgLocationId(orgLocation, indPaysheetFrequencyDTO);
	}

	@Override
	public IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq) {
		return indPaysheetFrequencyDao.addIndPaysheetFrequency(paySheetFreq);
	}
	
	@Override
	public ResponseList<IndEmployeePayroll> getIndEmployeePayrollListByPaysheetFrequencyUuid(String paysheetFrequencyUuid) throws Exception {
		return indPaysheetFrequencyDao.getIndEmployeePayrollListByPaysheetFrequencyUuid(paysheetFrequencyUuid);
	}
	
	@Override
	public Response<IndEmployeePayroll> getIndEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		return indPaysheetFrequencyDao.getIndEmployeePayrollByEmpPayrollUUID(empPayrollUUID);
	}

	@Override
	public Response<IndPaysheetFrequencyDTO> getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID, OrgLocations orgLocation)
			throws Exception {
		Response<IndPaysheetFrequencyDTO> response = new Response<>();
		IndPaysheetFrequency freq = indPaysheetFrequencyDao.getIndPaysheetFrequencyByEmpPayrollUUID(payrollFrequencyUUID);
		if(freq != null) {
			IndPaysheetFrequencyDTO freqDTO =indPayrollCommonUtil.getIndPaysheetFrequencyDTOFromIndPaysheetFrequency(freq, orgLocation);
			response.setObject(freqDTO);
		}
		return response;
	}
	

	@Override
	public Response<IndApprovePayrollDTO> sentForApproveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO,IndPaysheetFrequencyDTO indPaysheetFrequencyDTO ,
			OrgLocations orgLocation, User loginUser) throws Exception {
		Response<IndApprovePayrollDTO> res = new Response<>();
		try {
			    IndPaysheetFrequency indPaysheetFrequency = null;
				if(indPaysheetFrequencyDTO != null) {
					 indPaysheetFrequency = indPayrollCommonUtil.getIndPaysheetFrequencyFromIndPaysheetFrequencyDTO(indPaysheetFrequencyDTO, orgLocation);
				}else {
					indPaysheetFrequency = indPaysheetFrequencyDao.getIndPaysheetFrequencyByEmpPayrollUUID(indApprovePayrollDTO.getPaySheetFrequencyUUID());
				}
				
				if(StringUtils.isNotEmpty(indApprovePayrollDTO.getApproverStatus()) && indApprovePayrollDTO.getApproverStatus().equalsIgnoreCase(PayrollConstant.DRAFT)) {
					indPaysheetFrequency.setApproverStatus(PayrollConstant.DRAFT); 
				}else {
					indPaysheetFrequency.setApproverStatus(PayrollConstant.SENT); 
				}
				
				
				indPaysheetFrequency.setUpdatedBy(loginUser.getId());
				indPaysheetFrequency.setUpdatedDate(new Date());
				CopyOnWriteArraySet<IndEmployeePayroll> indEmployeepayrollList = new CopyOnWriteArraySet<IndEmployeePayroll>(indPaysheetFrequency.getIndEmployeepayroll());
				for (IndEmployeePayroll indEmployeepayroll : indEmployeepayrollList) {
					if(!Arrays.asList(indApprovePayrollDTO.getExcludeEmpPayrollUUID()).contains(indEmployeepayroll.getIndEmpPayrollUUID())) {
						if(StringUtils.isNotEmpty(indApprovePayrollDTO.getApproverStatus()) && indApprovePayrollDTO.getApproverStatus().equalsIgnoreCase(PayrollConstant.DRAFT)) {
							indEmployeepayroll.setSubmitForAproverStatus(PayrollConstant.DRAFT);
						}else {
							indEmployeepayroll.setSubmitForAproverStatus(PayrollConstant.SENT); 
						}
						indEmployeepayroll.setUpdatedBy(loginUser.getId());
						indEmployeepayroll.setUpdatedDate(new Date());
					}else {
						if((StringUtils.isNotEmpty(indApprovePayrollDTO.getApproverStatus()) && indApprovePayrollDTO.getApproverStatus().equalsIgnoreCase(PayrollConstant.NEW))) {
								indEmployeepayrollList.remove(indEmployeepayroll);
						}
						else{
							indEmployeepayroll.setUpdatedBy(loginUser.getId());
							indEmployeepayroll.setUpdatedDate(new Date());
							indEmployeepayroll.setStatus(0);
						}
					}
				}
				indPaysheetFrequency.setIndEmployeepayroll(indEmployeepayrollList);
				IndPaysheetFrequency savedPF = indPaysheetFrequencyDao.addIndPaysheetFrequency(indPaysheetFrequency);
				//ind payroll cache removing unnecessary value after aprove and submitForAproval
				List<EmployeeSessionIndPayroll> IndPayrollCahe = indPayrollService.getEmployeeSessionList(indApprovePayrollDTO.getPaySheetFrequencyUUID());
				if(IndPayrollCahe != null && IndPayrollCahe.size() > 0)
				indPayrollService.deleteIndPayrollCaheRecoedByPaySheetFrequencyUUID(IndPayrollCahe);
				if (savedPF != null && StringUtils.isNotEmpty(savedPF.getApproverStatus()) && savedPF.getApproverStatus().equalsIgnoreCase(PayrollConstant.SENT)) {
					res.setResponse(Result.SUCCESS.name());
					res.setResponseMessage(Result.SUCCESS.getValue());
					res.setObject(indApprovePayrollDTO);
					
					ConfigurePayrollApprover cpa = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
					if(cpa != null) {
						Set<PayrollApprover> pa =  cpa.getPayrollApprover();
						for (PayrollApprover payrollApprover : pa) {
							indPayrollCommonUtil.sendMailToIndPayrollApprover(orgLocation,loginUser,payrollApprover,indPaysheetFrequency);
						}
					}
				}
			}catch (Exception e) {
				res.setResponse(Result.AWKWARD.name());
				res.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return res;
	}
	
	
	@Override
	public Response<IndApprovePayrollDTO> getApproveInfoIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO,
			OrgLocations orgLocation, User loginUser) throws Exception {
		Response<IndApprovePayrollDTO> res = new Response<>();
		try {
				ResponseList<IndEmployeePayroll> empPayrollResponse = indPaysheetFrequencyDao.getIndPayrollApprovedEmployeeListByPaysheetFrequencyUuidAndEmpApproveStatus(indApprovePayrollDTO.getPaySheetFrequencyUUID(), PayrollConstant.SENT);
				
				
				
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(indApprovePayrollDTO);
			}catch (Exception e) {
				res.setResponse(Result.AWKWARD.name());
				res.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
			return res;
	}
	
	
	@Override
	public Response<IndApprovePayrollDTO> approveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO,IndPaysheetFrequency indPaysheetFrequency,OrgLocations orgLocation, User loginUser) throws Exception {
			Response<IndApprovePayrollDTO> res = new Response<>();
	   	    
			try {
				indPaysheetFrequency.setApproverStatus(PayrollConstant.APPROVE);
				indPaysheetFrequency.setUpdatedBy(loginUser.getId());
				indPaysheetFrequency.setUpdatedDate(new Date());
			    
				Set<IndEmployeePayroll> payrollList = indPaysheetFrequency.getIndEmployeepayroll();
				for (IndEmployeePayroll payroll : payrollList) {
						payroll.setSubmitForAproverStatus(PayrollConstant.APPROVE);
						if(payroll.getCandidate() != null) {
							payroll.setPaymentAccount(paymentAccountService.getPaymentAccountDetailsByCandidateId(payroll.getCandidate().getId()));
						}
						payroll.setUpdatedBy(loginUser.getId());
						payroll.setUpdatedDate(new Date());
				}
				indPaysheetFrequency.setIndEmployeepayroll(payrollList);
				IndPaysheetFrequency savedPF = indPaysheetFrequencyDao.addIndPaysheetFrequency(indPaysheetFrequency);
				
				//ind payroll cache removing unnecessary value after aprove and submitForAproval
				List<EmployeeSessionIndPayroll> IndPayrollCahe = indPayrollService.getEmployeeSessionList(indApprovePayrollDTO.getPaySheetFrequencyUUID());
				if(IndPayrollCahe != null && IndPayrollCahe.size() > 0)
				indPayrollService.deleteIndPayrollCaheRecoedByPaySheetFrequencyUUID(IndPayrollCahe);
				
				if (savedPF != null) {
					res.setResponse(Result.SUCCESS.name());
					res.setResponseMessage(Result.SUCCESS.getValue());
					res.setObject(indApprovePayrollDTO);
					
					//indPayrollCommonUtil.generatePdfForEmployeeInd(payrollList, orgLocation);
					indPayrollCommonUtil.sendApprovedMailToRequestedUserInd(orgLocation,savedPF,loginUser);
				}
			}catch (Exception e) {
				res.setResponse(Result.AWKWARD.name());
				res.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return res;
	}
	
	
	@Override
	public ResponseList<IndEmployeePayrollDTO> getEmployeePaysByOrgLocationIdAndUserId(OrgLocations orgLocation, User user, IndEmployeePayrollDTO indEmployeePayrollDTO) throws Exception {
		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<IndEmployeePayrollDTO>();
		
		ResponseList<IndEmployeePayroll> response = indPaysheetFrequencyDao.getEmployeePaysByOrgLocationIdAndUserId(orgLocation, user, indEmployeePayrollDTO);
		
		List<IndEmployeePayrollDTO> list = new ArrayList<>();
		for(IndEmployeePayroll imp : response.getData()) {
			IndEmployeePayrollDTO impDTO = new IndEmployeePayrollDTO();
			impDTO.setId(imp.getId());
			
			impDTO.setPayPeriod(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),imp.getIndPaysheetFrequency().getFrequencyStartDate())+" - "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),imp.getIndPaysheetFrequency().getFrequencyEndDate()));
			impDTO.setPayCheckDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),imp.getPayCheckDate()));
			impDTO.setNoOfDays(imp.getNoOfDays());
			impDTO.setNoOfLeaves(imp.getNoOfLeaves());
			impDTO.setGrossPay(imp.getGrossPay());
			impDTO.setNetPay(imp.getNetPay());
			impDTO.setTds(imp.getTds());
			impDTO.setSubmitForApproverStatus(imp.getSubmitForAproverStatus());
			impDTO.setIndEmpPayrollUUID(imp.getIndEmpPayrollUUID());
			list.add(impDTO);
		}
		responseList.setData(list);
		return responseList;
	}

	@Override
	public IndPaysheetFrequency getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID) throws Exception {
		return indPaysheetFrequencyDao.getIndPaysheetFrequencyByEmpPayrollUUID(payrollFrequencyUUID);
	}

	@Override
	public IndEmployeePayroll indEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		return indPaysheetFrequencyDao.indEmployeePayrollByEmpPayrollUUID(empPayrollUUID);
	}

	@Override
	public ResponseList<IndEmployeePayroll> getPayrollRegisterReports(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
		return indPaysheetFrequencyDao.getPayrollRegisterReports(indEmployeePayrollDTO, orgLocations);
	}

	@Override
	public List<IndEmployeePayroll> getYTDIndEmployeePayrollList(Long candidateId, String startMonth) throws Exception {
		return indPaysheetFrequencyDao.getYTDIndEmployeePayrollList(candidateId, startMonth);
	}

	@Override
	public ResponseList<IndEmployeePayroll> getPayrollBankReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
		return indPaysheetFrequencyDao.getPayrollBankReportReportList(indEmployeePayrollDTO, orgLocations);
	}

	@Override
	public ResponseList<IndEmployeePayroll> getPayrollYTDSummaryReportReportList( IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
		return indPaysheetFrequencyDao.getPayrollYTDSummaryReportReportList(indEmployeePayrollDTO, orgLocations);
	}

	@Override
	public ResponseList<IndEmployeePayroll> getPayrollAnnualTaxReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) throws Exception {
		return indPaysheetFrequencyDao.getPayrollAnnualTaxReportReportList(indEmployeePayrollDTO, orgLocations);
	}

	@Override
	public List<IndEmployeePayrollDTO> getPayrollSalaryStatementReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,	OrgLocations orgLocations) throws Exception {
		return indPaysheetFrequencyDao.getPayrollSalaryStatementReportList(indEmployeePayrollDTO, orgLocations);
	}

	@Override
	public ResponseList<IndEmployeePayrollDTO> getPayrollESIAndPFReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) throws Exception {
		return indPaysheetFrequencyDao.getPayrollESIAndPFReportList(indEmployeePayrollDTO, orgLocations);
	}

	@Override
	public ResponseList<IndEmployeePayrollDTO> getPayrollSalaryRegisterReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
		return indPaysheetFrequencyDao.getPayrollSalaryRegisterReportReportList(indEmployeePayrollDTO, orgLocations);
	}
 
}
