package com.bis.sourcelead.payroll.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.ConfirmPayTotalSumDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.EmployeeHoursDTO;
import com.bis.sourcelead.payroll.dto.MyPayrollDTO;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;
import com.bis.sourcelead.payroll.entity.PayitemHistory;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class PaysheetFrequencyServiceImpl implements PaysheetFrequencyService{

	private static final Logger logger = LoggerFactory.getLogger(PaysheetFrequencyServiceImpl.class);
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	USPaysheetDao uSPaysheetDao;
	
	@Autowired
	USPayPeriodService uSPayPeriodService;
	
	@Autowired
	private ConfigurePayrollApproverService configurePayrollApproverService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	PaysheetFrequencyDao paysheetFrequencyDao;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Override
	public Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation,User loginUser)
	{
		
		Response<PayrollApproverDTO> res = new Response<>();
		try {
			PaysheetFrequency pf = uSPaysheetService.getPayFrequencyByUUID(payrollApproverDTO.getPayrollFrequencyUUID());
			pf.setApproverStatus(PayrollConstant.SENT);
		    USPayPeriod period = uSPayPeriodService.getPayPeriodNumber(pf.getId());
		    int payPeriodNum = 1;
		    if(period != null) {
		    	payPeriodNum = payPeriodNum + period.getPayPeriodNum();
		    }
		    
		    USPayPeriod uSPayPeriod = saevPayPeriodNumber(pf,payPeriodNum,orgLocation);
		    
			Set<EmployeePayroll> payrollList = pf.getEmployeepayroll();
			for (EmployeePayroll payroll : payrollList) {
				for (USPayrollDTO payrollDTO : payrollApproverDTO.getUspayroll()) {
					if (payrollDTO.getIsExcept() == null && payrollDTO.getEmpPayrollId().longValue() == payroll.getId().longValue()) {
						payroll.setSubmitForAproverStatus(PayrollConstant.SENT);
						payroll.setPayPeriodNum(String.valueOf(uSPayPeriod.getPayPeriodNum()));
					}
				}
				
			}
			pf.setEmployeepayroll(payrollList);
			PaysheetFrequency savedPF = uSPaysheetDao.addPaysheetFrequency(pf);
			if (savedPF != null) {
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(payrollApproverDTO);
				
				
				ConfigurePayrollApprover cpa = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
				if(cpa != null) {
					List<PayrollApprover> pa = configurePayrollApproverService.getPayrollApproversByCPAID(cpa.getId());
					for (PayrollApprover payrollApprover : pa) {
						sendMailToPayrollApprover(orgLocation,loginUser,payrollApprover,savedPF);
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

	private void sendMailToPayrollApprover(OrgLocations orgLocation, User loginUser, PayrollApprover payrollApprover, PaysheetFrequency savedPF) throws Exception {
		
		
		int sentSize = 0;
		USPayPeriod uSPayPeriodw = uSPayPeriodService.getPayPeriodByOrglocationId(orgLocation, savedPF.getId());
        	 List<EmployeePayroll> empList = uSPayPeriodService.getEmployeePayrollListByPayperiodNum(uSPayPeriodw.getPayPeriodNum());
        	 for (EmployeePayroll employeePayroll : empList) {
        		 if(employeePayroll.getSubmitForAproverStatus().equalsIgnoreCase(Constants.SENT)){
        			 sentSize += 1;
      			}
			}
		
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(payrollApprover.getUser().getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		SimpleDateFormat dateFormat = orgLocation.getLocFullFormDateFormatter();
		
		
		StringBuilder sb = new StringBuilder();
		if(payrollApprover.getUser().getLastName() != null) {
			globalVarsMap.put("approverName", sb.append(payrollApprover.getUser().getFirstName()).append(" ").append(payrollApprover.getUser().getLastName()).toString());
		}else {
			globalVarsMap.put("approverName", payrollApprover.getUser().getFirstName());
		}
		
		globalVarsMap.put("batchName", savedPF.getUserGroup().getGroupName());
		globalVarsMap.put("noOfAssociates", String.valueOf(sentSize));
		
		StringBuilder sbb = new StringBuilder();
		if(savedPF.getFrequencyEndDate() != null) {
			globalVarsMap.put("frequency", sbb.append(dateFormat.format(savedPF.getFrequencyStartDate())).append(" to ").append(dateFormat.format(savedPF.getFrequencyEndDate())).toString());
		}else {
			globalVarsMap.put("frequency", dateFormat.format(savedPF.getFrequencyStartDate()).toString());	
		}
		
		double amount = savedPF.getEmployeepayroll().stream().mapToDouble(p->p.getNetPay().doubleValue()).sum();
		globalVarsMap.put("amount", String.valueOf(amount));
		
		globalVarsMap.put("loginOrg", orgLocation.getOrgLocationName());
		
		StringBuilder stringBuilderLoginname = new StringBuilder();
		globalVarsMap.put("loginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("payrollLink", stringBuildforLink.append("<a href='").append(sourceleadUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUEST_FOR_APPROVING_PAYROLL,Constants.ENTITY_TYPE_EMAIL);
       
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUEST_FOR_APPROVING_PAYROLL, Constants.ENTITY_TYPE_NOTIFICATION);


        //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = null;
		 if(StringUtils.isNotBlank(emailScenario.getSignature())){
			 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
		 }
		 String url = sourceleadUrl;
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
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
		
		emailScenerioUtil.sendNotification(null, loginUser, payrollApprover.getUser(), orgLocation, notificationScenario,new HashMap<String,String>());
		
		
	}

	private USPayPeriod saevPayPeriodNumber(PaysheetFrequency pf, int payPeriodNum, OrgLocations orgLocation)
	{
		USPayPeriod  uSPayPeriod = new USPayPeriod();
		uSPayPeriod.setPaysheetFrequency(pf);
		uSPayPeriod.setPayPeriodNum(payPeriodNum);
		uSPayPeriod.setStatus(1);
		uSPayPeriod.setOrgLocation(orgLocation);
		uSPayPeriod.setCreatedDate(new Date());
		uSPayPeriod.setUpdatedDate(new Date());
		uSPayPeriod.setApproverStatus(PayrollConstant.SENT);
		return uSPayPeriodService.savePayPeriodNumber(uSPayPeriod);
	}

	@SuppressWarnings("static-access")
	@Override
	public MyPayrollSummaryDTO getMyPayrollListByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		MyPayrollSummaryDTO response = new MyPayrollSummaryDTO();
		try {
			List<EmployeePayroll> empPayroll = paysheetFrequencyDao.getMyPayrollListByCandidateId(candidateId);
			List<MyPayrollDTO> list = null;
			List<DeductionDTO> dedHisList = new ArrayList<>();
			List<ContributionDTO> contributionHisList = new ArrayList<>();
			List<AssociatePayItemDTO> payItemList = new ArrayList<>();
			
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
			double regularTotalHrs = 0;
			double otTotalHours = 0;
			BigDecimal regTotalAmount = BigDecimal.ZERO;
			BigDecimal otTotalAmount = BigDecimal.ZERO;
			ConfirmPayTotalSumDTO paySum = new ConfirmPayTotalSumDTO();
			Map<Double, BigDecimal> hrsMap = new HashMap<>();
			if (empPayroll != null && empPayroll.size() > 0) {
				list = new ArrayList<>();
				for (EmployeePayroll payroll : empPayroll) {
					MyPayrollDTO myPayrollDTO = new MyPayrollDTO();
					String ferquencyStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), payroll.getPaysheetFrequency().getFrequencyStartDate());
					String ferquencyEndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), payroll.getPaysheetFrequency().getFrequencyEndDate());
					String payCheckDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), payroll.getPayCheckDate());
					myPayrollDTO.setPayPeriodStartDate(ferquencyStartDate);
					myPayrollDTO.setPayPeriodEndDate(ferquencyEndDate);
					myPayrollDTO.setPayDate(payCheckDate);
					myPayrollDTO.setAccountType(payroll.getDepositType());
					myPayrollDTO.setContribution(payroll.getPostTaxContribution().add(payroll.getPreTaxContribution()));
					myPayrollDTO.setDeductions(payroll.getPreTaxDeduction().add(payroll.getPostTaxDeduction()));
					myPayrollDTO.setGrossPay(payroll.getGrossPay());
					myPayrollDTO.setNetPay(payroll.getNetPay());
					myPayrollDTO.setTaxes(payroll.getTaxes());
					myPayrollDTO.setPayrollUUID(payroll.getUsEmpPayrollUUID());
					if(StringUtils.isNotBlank(payroll.getPaySlipFileURL())) {
						myPayrollDTO.setPaySlipFileURL(payroll.getPaySlipFileURL());
					}
					List<DeductionDTO> dlist = payrollUtil.getDeductionData(payroll);
					dedHisList.addAll(dlist);
					List<ContributionDTO> clist = payrollUtil.getContributionData(payroll);
					contributionHisList.addAll(clist);
					
					List<AssociatePayItemDTO> pList = payrollUtil.getPayItemData(payroll);
					payItemList.addAll(pList);
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
					
					if(StringUtils.isNotBlank(payroll.getRegularHours())) {
						regularTotalHrs  = commonUtil.getTotalRegularOverTimeHours(Double.parseDouble(payroll.getRegularHours()),regularTotalHrs);
						regTotalAmount = regTotalAmount.add(payroll.getRegularHoursAmount());
					}
					if(StringUtils.isNotBlank(payroll.getOverTimeHours())) {
						otTotalHours  = commonUtil.getTotalRegularOverTimeHours(Double.parseDouble(payroll.getOverTimeHours()),otTotalHours);
						otTotalAmount = otTotalAmount.add(payroll.getOverTimeAmount());
					}
					
					
					list.add(myPayrollDTO);
				}
				empTotalTax = empTotalTax.add(federalWithholding).add(fica).add(medicare).add(advancedEarnedIC).add(stateWithholding).add(countyWithholding).add(localTax);
				emplrTotalTax = emplrTotalTax.add(emplrFUTA).add(emplrMedicare).add(emplrFUTA).add(emplrSUTA).add(emplrRUIA)
				        .add(emplrSUTASuracharge).add(emplrSDI).add(emplrTransitTaxes).add(emplrWyomingWC).add(emplrOregonWC); 
				if(dedHisList.size() >0) {
					List<DeductionDTO> preDedList = dedHisList.stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				    List<DeductionDTO> postDedList = dedHisList.stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				    Map<String, Double> preDList = preDedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getPreDeductionAmount)));
				    response.setPreDed(preDList);
				    Map<String, Double> postDList = postDedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getPostDeductionAmt)));
				    response.setPostDed(postDList);
				}
				
				if(contributionHisList.size() >0) {
					Map<String, Double> contribution = contributionHisList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getContributionAmt)));
					response.setContribution(contribution);
				}
				
				if(payItemList.size() >0) {
					Map<String, Double> payItem = payItemList.stream().collect(Collectors.groupingBy(AssociatePayItemDTO::getPayItemName,Collectors.summingDouble(AssociatePayItemDTO::getPayItemAmt)));
					response.setPayItem(payItem);
				}
				
				paySum.setFederalWithholding(federalWithholding);
				paySum.setFicaWithholding(fica);
				paySum.setMedicareWithholding(medicare);
				paySum.setEarnedIncCredit(advancedEarnedIC);
				paySum.setStateWithholding(stateWithholding);
				paySum.setCountyWithholding(countyWithholding);
				paySum.setLocalTax(localTax);
				paySum.setTotalTax(empTotalTax);
				
				EmployeeHoursDTO hourDTO = new EmployeeHoursDTO();
				hourDTO.setRegularHours(regularTotalHrs);
				hourDTO.setRegularTimeAmount(regTotalAmount);
				hourDTO.setOtHours(otTotalHours);
				hourDTO.setOverTimeAmount(otTotalAmount);
				response.setHours(hourDTO);
//				paySum.setNetPay(BigDecimal.valueOf(netPay));
				
				//Setting Employer Taxes
				
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
				
				response.setMyPayroll(list);
				response.setConfirmPayTotalSum(paySum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}



	/**
	 * Calculation Deduction of employee payroll
	 * 
	 * @param payroll
	 */
	private List<ContributionDTO> getContributionData(EmployeePayroll payroll)
	{
		List<ContributionDTO> contributionList = new ArrayList<>();
		try {
			Set<DeductionHistory> hisList = payroll.getDeductionHistory();
			if (hisList != null && hisList.size() > 0) {
				for (DeductionHistory dedHis : hisList) {
					double precontrAmt = 0;
					double postcontrAmt = 0;
					double amount = 0;
					if (dedHis.getContributionId() != null) {
						ContributionDTO contDTO = new ContributionDTO();
						if (dedHis.getPreContributionAmount() != null) {
							precontrAmt = dedHis.getPreContributionAmount().doubleValue();
							if (dedHis.getPreContributionAmount().compareTo(BigDecimal.ZERO) !=  0) {
								amount = dedHis.getPreContributionAmount().doubleValue();
							}
						}
						if (dedHis.getPostContributionAmount() != null) {
							postcontrAmt = dedHis.getPostContributionAmount().doubleValue();
							if (dedHis.getPostContributionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
								amount = dedHis.getPostContributionAmount().doubleValue();
							}
						}
						contDTO.setAmount(precontrAmt);
						contDTO.setPostContributionAmt(postcontrAmt);
						contDTO.setId(String.valueOf(dedHis.getContributionId().getId()));
						contDTO.setContributionType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
						contDTO.setTaxType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getTaxType());
						contDTO.setContributionAmount(dedHis.getContributionId().getContributionAmount());
						contDTO.setContributionAmt(amount);
						contributionList.add(contDTO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contributionList;
	}

	/**
	 * Calculation Deduction of employee payroll
	 * @param payroll
	 */
	private List<DeductionDTO> getDeductionData(EmployeePayroll payroll)
	{
		List<DeductionDTO> dedList = new ArrayList<>();
		try {
			Set<DeductionHistory> hisList = payroll.getDeductionHistory();
			if (hisList != null && hisList.size() > 0) {
				for (DeductionHistory dedHis : hisList) {
					DeductionDTO dedDTO = new DeductionDTO();
					dedDTO.setId(String.valueOf(dedHis.getDeductionId().getId()));
					dedDTO.setDeductionType(dedHis.getDeductionId().getDeductionTypeId().getPayrollDeductionType());
					double preDedAmt = 0;
					double postDedAmt = 0;
					if (dedHis.getPreDeductionAmount() != null) {
						preDedAmt = dedHis.getPreDeductionAmount().doubleValue();
					}
					if (dedHis.getPostDeductionAmount() != null) {
						postDedAmt = dedHis.getPostDeductionAmount().doubleValue();
					}
					dedDTO.setDedAmount(preDedAmt);
					dedDTO.setPostDeductionAmt(postDedAmt);
					dedDTO.setDedTaxType(dedHis.getDeductionId().getDeductionTypeId().getTaxType());
					dedDTO.setAmount(dedHis.getDeductionId().getDeductionAmount());
					dedList.add(dedDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dedList;
	}

	@Override
	public List<PaysheetFrequencyDTO> getPaysheetFrequencyDraftDataByOrgLocationId(OrgLocations orgLocation) {
		
		List<PaysheetFrequency> paySheetFreqList = paysheetFrequencyDao.getPaysheetFrequencyDraftDataByOrgLocationId(orgLocation.getId());
		 
		List<PaysheetFrequencyDTO> list = new ArrayList<>();
		paySheetFreqList.forEach(pay -> {
			List<String> statusList = new ArrayList<>();
			PaysheetFrequencyDTO payFrequency = new PaysheetFrequencyDTO();
			payFrequency.setPaySheetFrequencyuuid(pay.getPaySheetFrequencyUUID());
			payFrequency.setGroupName(pay.getUserGroup().getGroupName());
			StringBuilder str = new StringBuilder();
			payFrequency.setPayFrequency(str.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyStartDate())).append(" - ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyEndDate())).toString());
			payFrequency.setPayDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPayCheckDate()));
			payFrequency.setApproverStatus(pay.getApproverStatus());
			
			
			for (EmployeePayroll emp : pay.getEmployeepayroll()) {
				statusList.add(emp.getSubmitForAproverStatus());
			}

			if(statusList.contains(Constants.DRAFT) && statusList.size() > 0)
			list.add(payFrequency);
		});
		
		return list;
	}

	@Override
	public List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation,String approverStatus)
	{
		return paysheetFrequencyDao.employeePayrollYTD(candidate,orgLocation,approverStatus);
	}

	@Override
	public PaysheetFrequency getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(String date, OrgLocations orgLocation,Long groupId) {
		return paysheetFrequencyDao.getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(date,orgLocation,groupId);
	}
	
	@Override
	public ResponseList<PayrollReportDTO> getPayrollRegisterReports(PayrollReportDTO payPeriodDTO,OrgLocations orgLocations)
	{
		ResponseList<PayrollReportDTO> dtoList = new ResponseList<>();
		List<PayrollReportDTO> payrollList = new ArrayList<>();
		payPeriodDTO.setOrgLocationid(orgLocations.getId());
		ResponseList<EmployeePayroll> list = paysheetFrequencyDao.getPayrollRegisterReports(payPeriodDTO);
		for(EmployeePayroll emp : list.getData()) {
//			for(EmployeePayroll emp : freq.getEmployeepayroll()) {
			PayrollReportDTO dto = new PayrollReportDTO();
			dto.setEmpId(emp.getEmployee().getEmployeeId());
			StringBuilder sb = new StringBuilder();
			sb.append(emp.getCandidate().getFirstName());
			if(StringUtils.isNotBlank(emp.getCandidate().getLastName())) {
				sb.append(emp.getCandidate().getLastName());
			}
			dto.setEmpName(sb.toString());
			dto.setDepartmentName(emp.getEmployee().getDepartmentId().getDepartmentName());
			dto.setRegularHours(emp.getRegularHours());
			dto.setGrossPay(emp.getGrossPay());
			dto.setTaxes(emp.getTaxes());
			YTDPayrollDTO ytdDTO = payrollUtil.getEmployeeYTDPayroll(emp.getCandidate(), orgLocations, Constants.APPROVED);
			dto.setYtdTaxes(ytdDTO.getYtdTaxes());
			dto.setDeductions(emp.getPreTaxDeduction().add(emp.getPostTaxDeduction()));
			dto.setYtdDeductions(ytdDTO.getYtdDeduction());
			dto.setNetPay(emp.getNetPay());
			dto.setYtdNet(ytdDTO.getYtdNet());
			payrollList.add(dto);
//			}
		}
		dtoList.setData(payrollList);
		dtoList.setRecordsTotal(list.getRecordsTotal());
		dtoList.setRecordsFiltered(list.getRecordsTotal());
		return dtoList;
	}

}
