package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateTaxDao;
import com.bis.sourcelead.payroll.dao.USPayrollDao;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.ConfirmPayTotalSumDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class USPayrollServiceImpl  implements USPayrollService{
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollServiceImpl.class);
	
	public static final String DEST = "/home/vikram/properties/cell_heights.pdf4";
	
	@Autowired
	USPayrollDao uSPayrollDao;
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	PayrollDeductionService payrollDeductionService;
	
	@Autowired
	AssociateDeductionService associateDeductionService;
	
	@Autowired
	AssociateContributionService associateContributionService;
	
	@Autowired
	USPayPeriodService uSPayPeriodService;
	
	@Autowired
	USPaysheetDao uSPaysheetDao;
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PayrollUtil payrollUtil;
	
	@Autowired
	AssociateTaxDao associateTaxDao;
	
	@Autowired
	GenerateUSPayrollService generateUSPayrollService;
	
	
	@Override
	public ResponseList<PayrollDeductionDTO> findAllDeduction()
	{
		ResponseList<PayrollDeductionDTO> response = new ResponseList<>();
		List<LookUpData> lookupDataList = uSPayrollDao.findAllDeduction();
		List<PayrollDeductionDTO> list = new ArrayList<>();
		lookupDataList.forEach(l -> {
			PayrollDeductionDTO payrollDeductionDTO = new PayrollDeductionDTO();
			payrollDeductionDTO.setPayrollDeductionTypeName(l.getName());
			payrollDeductionDTO.setLookupDataId(l.getId());
			list.add(payrollDeductionDTO);
		});
		if (list.size() > 0) {
			response.setData(list);
			response.setStatusCode("200");
		}
		else {
			response.setStatusCode("400");
		}
		return response;
	}

	@Override
	public ConfirmPayTotalSumDTO calculateTotalPaySum(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation)
	{
		ConfirmPayTotalSumDTO paySum = new ConfirmPayTotalSumDTO();
		
		try {
			// Emplyee Taxes
			BigDecimal federalWithholding = BigDecimal.ZERO;
			BigDecimal fica = BigDecimal.ZERO;
			BigDecimal medicare = BigDecimal.ZERO;
			BigDecimal advancedEarnedIC = BigDecimal.ZERO;
			BigDecimal stateWithholding = BigDecimal.ZERO;
			BigDecimal countyWithholding = BigDecimal.ZERO;
			BigDecimal localTax = BigDecimal.ZERO;
			BigDecimal empTotalTax = BigDecimal.ZERO;
			
			// Employer Taxes 
			
			BigDecimal emplrFICA = BigDecimal.ZERO;
			BigDecimal emplrMedicare = BigDecimal.ZERO;
			BigDecimal emplrFUTA = BigDecimal.ZERO;
			BigDecimal emplrSUTA = BigDecimal.ZERO;
			BigDecimal emplrRUIA = BigDecimal.ZERO;
			BigDecimal emplrSUTASuracharge = BigDecimal.ZERO;
			BigDecimal emplrSDI = BigDecimal.ZERO;
			BigDecimal emplrTransitTaxes = BigDecimal.ZERO;
			BigDecimal emplrWyomingWC = BigDecimal.ZERO;
			BigDecimal emplrOregonWC = BigDecimal.ZERO;
			BigDecimal emplrTotalTax = BigDecimal.ZERO;
			
			PaysheetFrequency pf = uSPaysheetService.getPayFrequencyByUUID(payrollApproverDTO.getPayrollFrequencyUUID());
			paySum.setPfUUID(pf.getPaySheetFrequencyUUID());
			
//			CopyOnWriteArraySet<EmployeePayroll> payrollList = new CopyOnWriteArraySet<EmployeePayroll>(pf.getEmployeepayroll());
			List<EmployeePayroll> payrollListt = null;
			if(Constants.DRAFT.equalsIgnoreCase(payrollApproverDTO.getConfirmType())) {
				 payrollListt = pf.getEmployeepayroll()
						.stream()
						.filter(payy-> !Constants.APPROVED.equalsIgnoreCase(payy.getSubmitForAproverStatus()) && payy.getSubmitForAproverStatus().equalsIgnoreCase(payrollApproverDTO.getConfirmType()))
						.collect(Collectors.toList());
			}else {
				 payrollListt = pf.getEmployeepayroll()
						.stream()
						.filter(payy-> !Constants.APPROVED.equalsIgnoreCase(payy.getSubmitForAproverStatus()) && payy.getSubmitForAproverStatus().equalsIgnoreCase(payrollApproverDTO.getConfirmType()) && Integer.parseInt(payy.getPayPeriodNum()) == Integer.parseInt(payrollApproverDTO.getPayPeriodNum()))
						.collect(Collectors.toList());
			}
			CopyOnWriteArraySet<EmployeePayroll> payrollList = new CopyOnWriteArraySet<>(payrollListt);
			List<Long> payrollIds = null;
		/*	if(!PayrollConstant.SENT.equalsIgnoreCase(payrollApproverDTO.getConfirmType())) {
			for (EmployeePayroll payroll : payrollList) {
				for (USPayrollDTO payrollDTO : payrollApproverDTO.getUspayroll()) {
					if ((payrollDTO.getIsExcept() != null && "YES".equalsIgnoreCase(payrollDTO.getIsExcept())) && payrollDTO.getEmpPayrollId().longValue() == payroll.getId().longValue()) {
						boolean s = payrollList.removeIf(x -> x.getId().longValue() == payroll.getId());
					}
				}
			}
			payrollIds = payrollList.stream().map(p -> p.getId()).collect(Collectors.toList());
			}else {
				List<EmployeePayroll> pidList = payrollList.stream().filter(ep->PayrollConstant.SENT.equalsIgnoreCase(ep.getSubmitForAproverStatus())).collect(Collectors.toList());
				payrollIds = pidList.stream().map(p -> p.getId()).collect(Collectors.toList());
			}*/
			
			for (EmployeePayroll payroll : payrollList) {
				for (USPayrollDTO payrollDTO : payrollApproverDTO.getUspayroll()) {
					if ((payrollDTO.getIsExcept() != null && "YES".equalsIgnoreCase(payrollDTO.getIsExcept())) && payrollDTO.getEmpPayrollId().longValue() == payroll.getId().longValue()) {
						boolean s = payrollList.removeIf(x -> x.getId().longValue() == payroll.getId());
					}
				}
			}
			
//			if(!PayrollConstant.SENT.equalsIgnoreCase(payrollApproverDTO.getConfirmType())) {
				payrollIds = payrollList.stream().map(p -> p.getId()).collect(Collectors.toList());
//				}else {
//					List<EmployeePayroll> pidList = payrollList.stream().filter(ep->PayrollConstant.SENT.equalsIgnoreCase(ep.getSubmitForAproverStatus())).collect(Collectors.toList());
//					payrollIds = pidList.stream().map(p -> p.getId()).collect(Collectors.toList());
//				}
			
			paySum.setPid(payrollIds);
			double regularHours = payrollList.stream().filter(p->p.getRegularHours() != null).mapToDouble(p->Double.parseDouble(p.getRegularHours())).sum();
			double overTimeHours = payrollList.stream().filter(p->p.getOverTimeHours() !=null).mapToDouble(p->Double.parseDouble(p.getOverTimeHours())).sum();
			double regularTimeEarnings = payrollList.stream().mapToDouble(p->p.getRegularHoursAmount().doubleValue()).sum();
			double overTimeEarnings = payrollList.stream().filter(p->p.getOverTimeAmount() != null).mapToDouble(p->p.getOverTimeAmount().doubleValue()).sum();
			double grossPay = payrollList.stream().mapToDouble(p->p.getGrossPay().doubleValue()).sum();
			double netPay = payrollList.stream().mapToDouble(p->p.getNetPay().doubleValue()).sum();
			
			 
			List<DeductionDTO> dedList = new ArrayList<>();
			List<ContributionDTO> contriList = new ArrayList<>();
			
			for(EmployeePayroll payroll : payrollList) {
				/*
				ResponseList<DeductionDTO> dedRes = associateDeductionService.findAllDeductionSummaryByCandidateId(payroll.getCandidate().getId(), orgLocation);
				if(dedRes.getData() != null) {
					dedList.addAll(dedRes.getData());
				}
				
				ResponseList<ContributionDTO> contributionRes = associateContributionService.findAllContributionSummaryByCandidateId(payroll.getCandidate().getId(), orgLocation);
				if(contributionRes.getData() != null) {
					contriList.addAll(contributionRes.getData());
				}*/
				// Deductions
				List<DeductionDTO> dlist = payrollUtil.getDeductionData(payroll);
				dedList.addAll(dlist);
				
				// Contributions
				List<ContributionDTO> clist = payrollUtil.getContributionData(payroll);
				contriList.addAll(clist);
				
				// Employee Taxes
				Set<EmployeePayrollTax> payrollTax = payroll.getUsPayrollTax();
				EmployeePayrollTax tax = payrollTax.iterator().next();
				federalWithholding = federalWithholding.add(tax.getFederalWithholding());
				fica = fica.add(tax.getFicaWithholding());
				medicare = medicare.add(tax.getMedicareWithholding());
				advancedEarnedIC = advancedEarnedIC.add(tax.getEarnedIncCredit());
				stateWithholding = stateWithholding.add(tax.getStateWithholding());
				countyWithholding = countyWithholding.add(tax.getCountyWithholding());
				localTax = localTax.add(tax.getLocalTax());
				
				// Employer Taxes
				Set<EmployerPayrollTax> emplrPayrollTax = payroll.getEmployerpayrolltax();
				EmployerPayrollTax emplrTax = emplrPayrollTax.iterator().next();
				emplrFICA = emplrFICA.add(emplrTax.getEmployerFICA());
				emplrMedicare = emplrMedicare.add(emplrTax.getEmployerMedicare());
				emplrFUTA = emplrFUTA.add(emplrTax.getEmployerFUTA());
				emplrSUTA = emplrSUTA.add(emplrTax.getEmployerSUTA());
				emplrRUIA = emplrRUIA.add(emplrTax.getEmployerRUIA());
				emplrSUTASuracharge = emplrSUTASuracharge.add(emplrTax.getEmployerSutaSurchargeTax());
				if(emplrTax.getEmployerSDI() != null) {
					emplrSDI = emplrSDI.add(emplrTax.getEmployerSDI());
				}
				emplrTransitTaxes = emplrTransitTaxes.add(emplrTax.getEmployerTransitTaxes());
				emplrWyomingWC = emplrWyomingWC.add(emplrTax.getEmployerWyomingWcTax());
				emplrOregonWC = emplrOregonWC.add(emplrTax.getEmployerOregonWcTax());
				
			}
			empTotalTax = empTotalTax.add(federalWithholding).add(fica).add(medicare).add(advancedEarnedIC).add(stateWithholding).add(countyWithholding).add(localTax);
			
			paySum.setRegularHours(regularHours);
			paySum.setOverTimeHours(overTimeHours);
			paySum.setRegularHoursAmount(BigDecimal.valueOf(regularTimeEarnings));
			paySum.setOverTimeHoursAmount(BigDecimal.valueOf(overTimeEarnings));
			paySum.setTotalHours(regularHours+overTimeHours);
			paySum.setTotalEarnings(BigDecimal.valueOf(grossPay));
			// Setting Employee Taxes 
			paySum.setFederalWithholding(federalWithholding);
			paySum.setFicaWithholding(fica);
			paySum.setMedicareWithholding(medicare);
			paySum.setEarnedIncCredit(advancedEarnedIC);
			paySum.setStateWithholding(stateWithholding);
			paySum.setCountyWithholding(countyWithholding);
			paySum.setLocalTax(localTax);
			paySum.setTotalTax(empTotalTax);
			paySum.setNetPay(BigDecimal.valueOf(netPay));
			
			//Setting Employer Taxes
			emplrTotalTax = emplrTotalTax.add(emplrFUTA).add(emplrMedicare).add(emplrFUTA).add(emplrSUTA).add(emplrRUIA)
					        .add(emplrSUTASuracharge).add(emplrSDI).add(emplrTransitTaxes).add(emplrWyomingWC).add(emplrOregonWC); 
			paySum.setEmployerFICA(emplrFUTA);
			paySum.setEmployerMedicare(emplrMedicare);
			paySum.setEmployerFUTA(emplrFUTA);
			paySum.setEmployerSUTA(emplrSUTA);
			paySum.setEmployerRUIA(emplrRUIA);
			paySum.setEmployerSutaSurchargeTax(emplrSUTASuracharge);
			paySum.setEmployerSDI(emplrSDI);
			paySum.setEmployerTransitTaxes(emplrTransitTaxes);
			paySum.setEmployerWyomingWcTax(emplrWyomingWC);
			paySum.setEmployerOregonWcTax(emplrOregonWC);
			paySum.setEmployerTotalPay(emplrTotalTax);
			paySum.setConfirmType(payrollApproverDTO.getConfirmType());
			paySum.setPpn(payrollApproverDTO.getPayPeriodNum());
			/*Map<String, Double> dedMap = dedLis1t.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
		     for (Map.Entry<String,Double> entry : dedMap.entrySet()) {
		    	 DeductionDTO dedDTO = new DeductionDTO();
		    	 dedDTO.setDeductionType(entry.getKey());
		    	 dedDTO.setAmount(BigDecimal.valueOf(entry.getValue()));
		    	 deduction.add(dedDTO);
		    }
		     if(deduction.size()>0) {
		    	 paySum.setDeduction(deduction);
		     }*/
			if(dedList.size() >0) {
//				List<DeductionDTO> preDedList = dedList.stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
//			    List<DeductionDTO> postDedList = dedList.stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
			    Map<String, Double> list = dedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
			    paySum.setDeduction(list);
//			    Map<String, Double> postDList = postDedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getPostDeductionAmt)));
//			    response.setPostDed(postDList);
			}
			
		    /* List<ContributionDTO> contribution = new ArrayList<>();
				Map<String, Double> contriMmap = contriLis1t.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getAmount)));
				 for (Map.Entry<String,Double> entry : contriMmap.entrySet()) {
					 ContributionDTO contDTO = new ContributionDTO();
					 contDTO.setContributionType(entry.getKey());
					 contDTO.setContributionAmount(BigDecimal.valueOf(entry.getValue()));
					 contribution.add(contDTO);
			    }
				 if(contribution.size()>0) {
			    	 paySum.setContribution(contribution);
			     }*/
				 
				 if(contriList.size() >0) {
						Map<String, Double> contribution = contriList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getContributionAmt)));
						paySum.setContribution(contribution);
					}
				 
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return paySum;
	
	}

	@Override
	public Response<ConfirmPayTotalSumDTO> approvePayroll(ConfirmPayTotalSumDTO confirmPaySumDto, OrgLocations orgLocation,User loginUser)
	{
		Response<ConfirmPayTotalSumDTO> res = new Response<>();
		try {
			PaysheetFrequency pf = uSPaysheetService.getPayFrequencyByUUID(confirmPaySumDto.getPfUUID());
			pf.setApproverStatus(PayrollConstant.SENT);
			USPayPeriod uSPayPeriod = null;
			if (PayrollConstant.DRAFT.equalsIgnoreCase(confirmPaySumDto.getConfirmType())) {
				USPayPeriod period = uSPayPeriodService.getPayPeriodNumber(pf.getId());
				int payPeriodNum = 1;
				if (period != null) {
					payPeriodNum = payPeriodNum + period.getPayPeriodNum();
				}
				uSPayPeriod = saevPayPeriodNumber(pf, payPeriodNum, orgLocation,loginUser);
			}else {
				int isUpadted = uSPayPeriodService.updatedPayperiod(confirmPaySumDto.getPpn());
			}
			Set<EmployeePayroll> payrollList = pf.getEmployeepayroll();
			for (EmployeePayroll payroll : payrollList) {
				for (Long payrollId : confirmPaySumDto.getPid()) {
					if (payrollId.longValue() == payroll.getId().longValue()) {
						payroll.setSubmitForAproverStatus(PayrollConstant.APPROVE);
						if (PayrollConstant.DRAFT.equalsIgnoreCase(confirmPaySumDto.getConfirmType())) {
							payroll.setPayPeriodNum(String.valueOf(uSPayPeriod.getPayPeriodNum()));
						}
					}
				}
			}
			pf.setEmployeepayroll(payrollList);
			PaysheetFrequency savedPF = uSPaysheetDao.addPaysheetFrequency(pf);
			if (savedPF != null) {
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(confirmPaySumDto);
				
				generatePdfForEmployee(savedPF,orgLocation);
				
				sendMailApprovedMailToRequestedUser(orgLocation,savedPF,loginUser);
				
                 
			}
		} catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}
	

	private void generatePdfForEmployee(PaysheetFrequency savedPF, OrgLocations orgLocation) {
		
		for (EmployeePayroll payroll : savedPF.getEmployeepayroll()) {
			
			try {
				AssociateTax associateTax= associateTaxDao.taxDetailsByOrglocationIdAndCandidateId(payroll.getCandidate().getId(),orgLocation.getId());
				generatePdf(payroll,orgLocation,associateTax);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}


	private void getMyPayrollDownload(EmployeePayroll payroll, OrgLocations orgLocation) {
		// TODO Auto-generated method stub
		
	}

	private void sendMailApprovedMailToRequestedUser(OrgLocations orgLocation,PaysheetFrequency savedPF,User loginUser) throws Exception {
		
		User user = null;
		int approvedSize = 0;
		USPayPeriod uSPayPeriodw = uSPayPeriodService.getPayPeriodByOrglocationId(orgLocation, savedPF.getId());
        user = userService.getUserById(savedPF.getCreatedBy());
        	 List<EmployeePayroll> empList = uSPayPeriodService.getEmployeePayrollListByPayperiodNum(uSPayPeriodw.getPayPeriodNum());
        	 for (EmployeePayroll employeePayroll : empList) {
        		 if(employeePayroll.getSubmitForAproverStatus().equalsIgnoreCase(Constants.APPROVED)){
      				approvedSize += 1;
      			}
			}
		
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(user.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		SimpleDateFormat dateFormat = orgLocation.getLocFullFormDateFormatter();
		
		
		StringBuilder sb = new StringBuilder();
		if(user.getLastName() != null) {
			globalVarsMap.put("requestUserName", sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		}else {
			globalVarsMap.put("requestUserName", user.getFirstName());
		}
		
		globalVarsMap.put("batchName", savedPF.getUserGroup().getGroupName());
		globalVarsMap.put("noOfAssociates", String.valueOf(approvedSize));
		globalVarsMap.put("loginOrg", orgLocation.getOrgLocationName());
		
		StringBuilder sbb = new StringBuilder();
		if(savedPF.getFrequencyEndDate() != null) {
			globalVarsMap.put("frequency", sbb.append(dateFormat.format(savedPF.getFrequencyStartDate())).append(" to ").append(dateFormat.format(savedPF.getFrequencyEndDate())).toString());
		}else {
			globalVarsMap.put("frequency", dateFormat.format(savedPF.getFrequencyStartDate()).toString());	
		}
		
		double amount = savedPF.getEmployeepayroll().stream().mapToDouble(p->p.getNetPay().doubleValue()).sum();
		globalVarsMap.put("amount", String.valueOf(amount));
		
		StringBuilder stringBuilderLoginname = new StringBuilder();
		globalVarsMap.put("approveLoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("payrollLink", stringBuildforLink.append("<a href='").append(sourceleadUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVE_MAIL_TO_REQUEST_USER,Constants.ENTITY_TYPE_EMAIL);
       
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVE_MAIL_TO_REQUEST_USER, Constants.ENTITY_TYPE_NOTIFICATION);


        //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = null;
		 if(StringUtils.isNotBlank(emailScenario.getSignature())){
			 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
		 }
		 String url = sourceleadUrl;
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getSubject(), globalVarsMap,emailScenario);
        notificationScenario.setBody(notificationbody);


        // construct HTML template using required data
        String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,signature, url, orgLocation.getOrg(),orgLocation);
        emailMessage.setSubject(subject);
        emailMessage.setMessageBody(emailTemplate);


		try {
			
        emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
		}catch (MessagingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(new RuntimeException(e)));
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		emailScenerioUtil.sendNotification(null, loginUser, user, orgLocation, notificationScenario,new HashMap<String,String>());
		
	}

	private USPayPeriod saevPayPeriodNumber(PaysheetFrequency pf, int payPeriodNum, OrgLocations orgLocation, User loginUser)
	{
		USPayPeriod  uSPayPeriod = new USPayPeriod();
		uSPayPeriod.setPaysheetFrequency(pf);
		uSPayPeriod.setPayPeriodNum(payPeriodNum);
		uSPayPeriod.setStatus(1);
		uSPayPeriod.setOrgLocation(orgLocation);
		uSPayPeriod.setCreatedDate(new Date());
		uSPayPeriod.setUpdatedDate(new Date());
		uSPayPeriod.setApproverStatus(PayrollConstant.APPROVE);
		uSPayPeriod.setCreatedBy(loginUser.getId());
		uSPayPeriod.setUpdatedBy(loginUser.getId());
		return uSPayPeriodService.savePayPeriodNumber(uSPayPeriod);
	}
	
	public File generatePdf(EmployeePayroll payroll, OrgLocations orgLocation, AssociateTax associateTax) throws Exception {
		
	 List<DeductionDTO> deductionTaxes=payrollUtil.getDeductionData(payroll);
	 List<ContributionDTO> contributionTaxes=payrollUtil.getContributionData(payroll);
	 String approverStatus ="APPROVED";
	// YTDPayrollDTO YTDTaxes= generateUSPayrollService.getEmployeeYTDPayroll(payroll,  orgLocation,approverStatus);
		 Document document = new Document();
		    File file = new File(DEST);
	        file.getParentFile().mkdirs();
	        int viki =1;
		    if(viki==1) {	
		        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();
		        Font f3 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f,Font.NORMAL, BaseColor.BLACK);
		        Font f4 = new Font(Font.FontFamily.TIMES_ROMAN, 6.0f,Font.BOLD, BaseColor.BLACK);
		        Paragraph   bell=null;
		        PdfPTable table = new PdfPTable(2); // 3 columns.
		        table.setWidthPercentage(100); //Width 100%
		        table.setSpacingBefore(10f); //Space before table
		        //Set Column widths
		        float[] columnWidths = {1f, 1f};
		        table.setWidths(columnWidths);
		        bell =new Paragraph( " Acme Corporation\n\n" +
		                "1000 main Street\n\n" +
		                "Capital City, ST 99999 .",f3);
		        PdfPCell cell1 = new PdfPCell(bell);
		        cell1.setBorderColor(BaseColor.BLACK);
		        cell1.setPaddingLeft(10);
		        cell1.setFixedHeight(50f);
		        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell1.setVerticalAlignment(Element.ALIGN_LEFT);
		        bell = new Paragraph("PayGroup:"+" "+payroll.getPaysheetFrequency().getUserGroup().getGroupName()+" "+"                            Businessunit:Sales\n\n" +
		                "PayBeginDate:"+" "+payroll.getPaysheetFrequency().getFrequencyStartDate() +"          Advice #:1006795\n\n"+
		                "PayEndDate:"+" "+payroll.getPaysheetFrequency().getFrequencyEndDate() +"              AdviceDate#:01/31/2020",f3);
		        PdfPCell cell2 = new PdfPCell(bell);
		        cell2.setBorderColor(BaseColor.BLACK);
		        cell2.setPaddingLeft(10);
		        cell2.setCalculatedHeight(10f);
		        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell2.setVerticalAlignment(Element.ALIGN_LEFT);
		        table.addCell(cell1);
		        table.addCell(cell2);
		        document.add(table);
		        
		        PdfPTable table1 = new PdfPTable(3);
		        table1.setWidthPercentage(100); //Width 100%
		        table1.setSpacingBefore(10f); //Space before table
		        table1.setSpacingAfter(10f);
		        
		        float[] columnWidths1 = {1f,1f,1f};
		        table1.setWidths(columnWidths1);
		        bell =new Paragraph( "Trace Smith\n\n" +
		                "123 Oak Street\n\n" +
		                "Home Town, Sx 12345\n\n"+"SSN: ",f3);
		        PdfPCell cell3 = new PdfPCell(new Paragraph(bell));
		        cell3.setBorderColor(BaseColor.BLACK);
		        cell3.setFixedHeight(50f);
		        cell3.setPaddingLeft(10);
		        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell3.setVerticalAlignment(Element.ALIGN_LEFT);
		        bell =new Paragraph( "Employee ID:"+payroll.getEmployee().getEmployeeId()+"\n\n" +
		                "Department:"+payroll.getEmployee().getDepartmentId().getDepartmentName()+" \n\n" ,f3);
		        PdfPCell cell4 = new PdfPCell(new Paragraph(bell));
		        cell4.setBorderColor(BaseColor.BLACK);
		        cell4.setPaddingLeft(10);
		        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell4.setVerticalAlignment(Element.ALIGN_LEFT);
		       // 
		        PdfPTable table5 = new PdfPTable(3);
		        table5.setWidthPercentage(100); //Width 100%
		        bell =new Paragraph( "TAX DATA: "+"      "+"FEDERAL "+"     "+"STATE",f4);
		        PdfPCell cell15 = new PdfPCell(new Paragraph(bell));
		        cell15.setBorderColor(BaseColor.BLACK);
		        cell15.setColspan(3);
		        cell4.setPaddingLeft(10);
	            cell15.setHorizontalAlignment(Element.ALIGN_LEFT);
	            cell15.setVerticalAlignment(Element.ALIGN_LEFT);
	                    
	            float[] columnWidths5 = {1f,1f,1f};
		        table5.setWidths(columnWidths5);
		        bell =new Paragraph( "MaritalStatus:\n"+"Allowance:\n"+"AdditionalWithHolding:\n",f3);
		        PdfPCell cell16 = new PdfPCell(new Paragraph(bell));
		        cell16.setBorderColor(BaseColor.BLACK);
		        cell16.setFixedHeight(60f);
		        cell16.setPaddingLeft(10);
		        cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell16.setVerticalAlignment(Element.ALIGN_LEFT);
		        bell =new Paragraph(associateTax.getFederalTaxFilliingStatus()+ "\n\n"+associateTax.getFederalTaxAllowances()+"\n\n"+associateTax.getFederalTaxAdditionnalWithHolding()+"\n"+" ",f3);
		        PdfPCell cell17 = new PdfPCell(new Paragraph(bell));
		       cell17.setBorderColor(BaseColor.BLACK);
		       cell17.setFixedHeight(60f);
		       cell17.setPaddingLeft(10);
		       cell17.setHorizontalAlignment(Element.ALIGN_LEFT);
		       cell17.setVerticalAlignment(Element.ALIGN_LEFT);
		       bell =new Paragraph(associateTax.getStateTaxFilliingStatus()+ "\n\n"+associateTax.getStateTaxAllowances()+"\n\n"+associateTax.getStateTaxAdditionnalWithHolding()+"\n"+" ",f3);
		       PdfPCell cell18 = new PdfPCell(new Paragraph(bell));
		       cell18.setBorderColor(BaseColor.BLACK);
		       cell18.setPaddingLeft(10);
		       cell18.setFixedHeight(60f);
		       cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
		       cell18.setVerticalAlignment(Element.ALIGN_LEFT);
		        table5.addCell(cell15);
		        table5.addCell(cell16);
		        table5.addCell(cell17);
		        table5.addCell(cell18);
		        //
		        table1.addCell(cell3);
		        table1.addCell(cell4);
		        table1.addCell(table5);
		        document.add(table1);
		        
		        PdfPTable table2 = new PdfPTable(2);
		        table2.setWidthPercentage(100); //Width 100%  
		        bell =new Paragraph( "HOURS AND EARNINGS",f4);
		        PdfPCell cell19 = new PdfPCell(new Paragraph(bell));
		        cell19.setBorderColor(BaseColor.BLACK);
		        cell19.setColspan(1);
	            cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell19.setVerticalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "TAXES",f4);
	            PdfPCell cell20 = new PdfPCell(new Paragraph(bell));
		        cell20.setBorderColor(BaseColor.BLACK);
		        cell20.setColspan(1);
	            cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell20.setVerticalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "DESCRIPTION "+"                              CURRENT EARNINGS "+"                          YTD EARNINGS",f4);
		        PdfPCell cell21 = new PdfPCell(new Paragraph(bell));
	            cell21.setBorderColor(BaseColor.BLACK);
		        cell21.setColspan(1);
		        cell21.setFixedHeight(20f);
	            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell21.setVerticalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "DESCRIPTION "+"     CURRENT "+"      YTD",f4);
	            PdfPCell cell22 = new PdfPCell(new Paragraph(bell));
		        cell22.setBorderColor(BaseColor.BLACK);
		        cell22.setColspan(1);
		        cell22.setFixedHeight(20f);
		        cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell22.setVerticalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "Total "+"                                                2062.53 "+"                                       4125.06 ",f3);
	            PdfPCell cell23 = new PdfPCell(new Paragraph(bell));
	            cell23.setBorderColor(BaseColor.BLACK);
		        cell23.setColspan(1);
		        cell23.setFixedHeight(20f);
		        cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell23.setVerticalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "Total "+"       617.37 "+"         1234.71",f3);
	            PdfPCell cell24 = new PdfPCell(new Paragraph(bell));
		        cell24.setBorderColor(BaseColor.BLACK);
		        cell24.setColspan(1);
		        cell24.setFixedHeight(20f);
		        cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell24.setVerticalAlignment(Element.ALIGN_CENTER);
		        float[] columnWidths2 = {2f, 1f};
		        table2.setWidths(columnWidths2);
		        bell =new Paragraph( "Regular "+"                                                "+payroll.getNetPay() +"                                       xxxxx ",f3);
		        PdfPCell cell6 = new PdfPCell(new Paragraph(bell));
		        cell6.setBorderColor(BaseColor.BLACK);
		        cell6.setFixedHeight(120f);
		        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell6.setVerticalAlignment(Element.ALIGN_CENTER);
		        bell =new Paragraph( ("Fed withholding "+"        "+payroll.getUsPayrollTax().iterator().next().getFederalWithholding()+"         xxxxx\n\n")+("Medicare "+"                  "+payroll.getUsPayrollTax().iterator().next().getMedicareWithholding()+"         xxxxxx\n\n")+("Fica "+"                       "+payroll.getUsPayrollTax().iterator().next().getFicaWithholding()+"         xxxxx\n\n")+("Statewithholding "+"                       "+payroll.getUsPayrollTax().iterator().next().getStateWithholding()+"         xxxxxx\n\n")+("LocalTax"+"              "+payroll.getUsPayrollTax().iterator().next().getLocalTax()+"           xxxxx\n\n")+("CountryWithHolding"+"             "+payroll.getUsPayrollTax().iterator().next().getCountyWithholding()+"          xxxxx\n"),f3);
		        PdfPCell cell8 = new PdfPCell(new Paragraph(bell));
		       cell8.setBorderColor(BaseColor.BLACK);
		       cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
		       cell8.setVerticalAlignment(Element.ALIGN_LEFT);
		        table2.addCell(cell19);
		        table2.addCell(cell20);
		        table2.addCell(cell21);
		        table2.addCell(cell22);
		        table2.addCell(cell6);
		        table2.addCell(cell8);
		        table2.addCell(cell23);
		        table2.addCell(cell24);
		        
		        document.add(table2);
		        
		        PdfPTable table3 = new PdfPTable(3);
		        table3.setWidthPercentage(100); //Width 100%
		        bell =new Paragraph( "BEFORE-TAXDEDUCTIONS",f4);
		        PdfPCell cell25 = new PdfPCell(new Paragraph(bell));
		        cell25.setBorderColor(BaseColor.BLACK);
		        cell25.setColspan(1);
		        cell25.setFixedHeight(20f);
	            cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "AFTER-TAXDEDUCTIONS",f4);
	            PdfPCell cell26 = new PdfPCell(new Paragraph(bell));
		        cell26.setBorderColor(BaseColor.BLACK);
		        cell26.setColspan(1);
		        cell26.setFixedHeight(20f);
	            cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "EMPLOYEER-PAID BENIFITS",f4);
		        PdfPCell cell27 = new PdfPCell(new Paragraph(bell));
	            cell27.setBorderColor(BaseColor.BLACK);
		        cell27.setColspan(1);
		        cell27.setFixedHeight(20f);
	            cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "DESCRIPTION "+"     CURRENT "+"      YTD",f4);
	            PdfPCell cell28 = new PdfPCell(new Paragraph(bell));
		        cell28.setBorderColor(BaseColor.BLACK);
		        cell28.setColspan(1);
		        cell28.setFixedHeight(20f);
	            cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "DESCRIPTION "+"     CURRENT "+"      YTD",f4);
	            PdfPCell cell29 = new PdfPCell(new Paragraph(bell));
		        cell29.setBorderColor(BaseColor.BLACK);
		        cell29.setColspan(1);
		        cell29.setFixedHeight(20f);
	            cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "DESCRIPTION "+"     CURRENT "+"      YTD",f4);
	            PdfPCell cell30 = new PdfPCell(new Paragraph(bell));
		        cell30.setBorderColor(BaseColor.BLACK);
		        cell30.setColspan(1);
		        cell30.setFixedHeight(20f);
	            cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
	            bell =new Paragraph( "Total "+"     94.25 "+"      188.50",f3);
	            PdfPCell cell31 = new PdfPCell(new Paragraph(bell));
	            cell31.setBorderColor(BaseColor.BLACK);
		        cell31.setColspan(1);
		        cell31.setFixedHeight(20f);
	            cell31.setHorizontalAlignment(Element.ALIGN_BOTTOM);
	            bell =new Paragraph( "Total "+"     30.70 "+"      61.40",f3);
	            PdfPCell cell32 = new PdfPCell(new Paragraph(bell));
		        cell32.setBorderColor(BaseColor.BLACK);
		        cell32.setColspan(1);
		        cell32.setFixedHeight(20f);
	            cell32.setHorizontalAlignment(Element.ALIGN_BOTTOM);
	            bell =new Paragraph( "Taxable "+"     30.70 "+"      61.40",f3);
	            PdfPCell cell33 = new PdfPCell(new Paragraph(bell));
		        cell33.setBorderColor(BaseColor.BLACK);
		        cell33.setColspan(1);
		        cell33.setFixedHeight(20f);
	            cell33.setHorizontalAlignment(Element.ALIGN_BOTTOM);
		        float[] columnWidths3 = {1f, 1f,1f};
		        table3.setWidths(columnWidths3);
		        PdfPCell cell9 =null;
		        for(DeductionDTO ded:deductionTaxes) 
		        bell =new Paragraph( (ded.getPreDeductionType()+"             "+ded.getPreDeductionAmount()+"         xxxxxx\n"),f3);
		        cell9 = new PdfPCell(new Paragraph(bell));
		        cell9.setBorderColor(BaseColor.BLACK);
		        cell9.setPaddingLeft(10);
		        cell9.setFixedHeight(100f);
		        cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell9.setVerticalAlignment(Element.ALIGN_LEFT);
		        
		        PdfPCell cell10=null;
		        for(DeductionDTO ded:deductionTaxes) 
		        bell =new Paragraph( (ded.getDeductionType()+"          "+ded.getPostDeductionAmt()+"               xxxxxx\n"),f3);
		        cell10= new PdfPCell(new Paragraph(bell));
		        cell10.setBorderColor(BaseColor.BLACK);
		        cell10.setPaddingLeft(10);
		        cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell10.setVerticalAlignment(Element.ALIGN_LEFT);
		        
		        
		        for(ContributionDTO ct:contributionTaxes)
		        bell =new Paragraph( (ct.getContributionType()+"       "+ct.getContributionAmount()+"         xxxxxx\n")+(ct.getPreContributionType()+"                  "+ct.getPreContributionAmt()+"         xxxxxxx\n"),f3);
		        PdfPCell cell11 = new PdfPCell(new Paragraph(bell));
		       cell11.setBorderColor(BaseColor.BLACK);
		       cell11.setPaddingLeft(10);
		       cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
		       cell11.setVerticalAlignment(Element.ALIGN_LEFT);
		       table3.addCell(cell25);
		       table3.addCell(cell26);
		       table3.addCell(cell27);
		       table3.addCell(cell28);
		       table3.addCell(cell29);
		       table3.addCell(cell30);
		        table3.addCell(cell9);
		        table3.addCell(cell10);
		        table3.addCell(cell11);
		        table3.addCell(cell31);
		        table3.addCell(cell32);
			    table3.addCell(cell33);
		        
		        document.add(table3);
		        
		        PdfPTable table4 = new PdfPTable(1);
		        table4.setWidthPercentage(100); //Width 100%
		        bell =new Paragraph( "TOTALGROSS "+"      "+"FED-TAXABLEGROSS "+"     "+"FICAGROSS"+"       "+"TOTALTAXES "+"      "+"TOTALDEDUCTIONS "+"     "+"NETPAY",f4);
		        PdfPCell cell34 = new PdfPCell(new Paragraph(bell));
	            cell34.setBorderColor(BaseColor.BLACK);
		        cell34.setColspan(1);
		        cell34.setFixedHeight(20f);
	            cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell34.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        float[] columnWidths4 = {1f};
		        table4.setWidths(columnWidths4);
		        bell =new Paragraph( ("Current:"+"       2062.53 "+"                             1995.67"+"                  1995.67 "+"                         617.37 "+"                            124.95"+"                           1320.21 "+"\n\nYTD: "+"         4125.06"+"                           3991.34 "+"                3991.34"+"                             1234.71 "+"                            249.90"+"                             2640.45 "),f3);
		        PdfPCell cell12 = new PdfPCell(new Paragraph(bell));
		        cell12.setBorderColor(BaseColor.BLACK);
		        cell12.setFixedHeight(50f);
		        cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell12.setVerticalAlignment(Element.ALIGN_LEFT);
	            table4.addCell(cell34);
		        table4.addCell(cell12);
	     
		        document.add(table4);
		        
		        
		        PdfPTable table6 = new PdfPTable(1);
		        table6.setWidthPercentage(30); //Width 100%
		        PdfPCell cell35 = new PdfPCell(new Paragraph("Cell 3"));
	            cell35.setBorderColor(BaseColor.BLACK);
		        cell35.setColspan(1);
		        cell35.setFixedHeight(20f);
		        cell35.setPaddingTop(10);
	            cell35.setHorizontalAlignment(Element.ALIGN_TOP);
		        float[] columnWidths6 = {1f};
		        table6.setWidths(columnWidths6);
		        
		        PdfPCell cell36 = new PdfPCell(new Paragraph(""));
		       cell36.setBorderColor(BaseColor.BLACK);
		       cell36.setFixedHeight(50f);
		       cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
		       cell36.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           table6.addCell(cell35);
		       table6.addCell(cell36);     
		        document.add(table6);
		        
		        document.close();
		        writer.close();
	}
			return file;
		    }

	
	}

	
