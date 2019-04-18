package com.bis.sourcelead.payroll.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.AssociateReportDTO;
import com.bis.sourcelead.payroll.dto.CommonPayrollDataDTO;
import com.bis.sourcelead.payroll.dto.ConfirmPayTotalSumDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.EmployeeHoursDTO;
import com.bis.sourcelead.payroll.dto.IntiateUSPayrollDTO;
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
import com.bis.sourcelead.payroll.entity.AssociateTaxHistory;
import com.bis.sourcelead.payroll.entity.BankDataHistory;
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
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
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
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
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
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	EmployeesService employeesService;
	
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
		
		//Redirection URL
		String redirectURL = "/viewPaySheetFrequencyData-params-approverStatus=SENT-and-paySheetFrequencyUUID="
				+ savedPF.getPaySheetFrequencyUUID() + "-and-payPeriodNum=" + uSPayPeriodw.getPayPeriodNum();
		Long redirectOrgLocationId = orgLocation.getId();
				
		
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
		
		Set<EmployeePayroll> filteredEmpPayroll = savedPF.getEmployeepayroll().stream().filter(p->!"MISSED".equalsIgnoreCase(p.getSubmitForAproverStatus())).collect(Collectors.toSet());
		double amount = filteredEmpPayroll.stream().mapToDouble(p->p.getNetPay().doubleValue()).sum();
		globalVarsMap.put("amount", String.valueOf(amount));
		
		globalVarsMap.put("loginOrg", orgLocation.getOrgLocationName());
		
		StringBuilder stringBuilderLoginname = new StringBuilder();
		globalVarsMap.put("loginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		StringBuilder stringBuildforLink = new StringBuilder();
		
		
		globalVarsMap.put("payrollLink", stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Approve Payroll</div></a>").toString());
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
		

		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder
				.append("").append(sourceleadUrl).append("/").append("viewPaySheetFrequencyData?approverStatus=SENT&paySheetFrequencyUUID="
						+ savedPF.getPaySheetFrequencyUUID() + "&payPeriodNum=" + uSPayPeriodw.getPayPeriodNum())
				.toString();
		
		emailScenerioUtil.sendNotification(actionLink, loginUser, payrollApprover.getUser(), orgLocation, notificationScenario,new HashMap<String,String>());

		
		
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
			double loanAmountTotal = 0;
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
					
					
					//Loan and advances
					BigDecimal lATotal = BigDecimal.ZERO;
					List<AssociateLoanAndAdvancesDTO> loanAdvance = payrollUtil.getLoanAndAdvancesData(payroll);
					if(loanAdvance != null && loanAdvance.size() >0) {
						response.setLoanAdvance(loanAdvance);
						loanAmountTotal = loanAdvance.stream().mapToDouble(lAmt->Double.valueOf(lAmt.getLoanAmount())).sum();
					}
					lATotal = BigDecimal.valueOf(loanAmountTotal);
					response.setLoanAmountTotal(lATotal);
					
					
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
					Date todayDate = new Date();
					 if (todayDate.after(payroll.getPayCheckDate())) {
						 myPayrollDTO.setEnablePayslip(true); 
					 }else {
						 myPayrollDTO.setEnablePayslip(false);
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
				double totalHours = otTotalHours  = commonUtil.getTotalRegularOverTimeHours(regularTotalHrs,otTotalHours);
				hourDTO.setTotalHours(new BigDecimal(totalHours));
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
			}else {
				Map<String, Double> preDList = new HashMap<>();
			    response.setPreDed(preDList);
			    response.setPostDed(preDList);
			    response.setContribution(preDList);
			    response.setPayItem(preDList);
			    paySum.setFederalWithholding(federalWithholding);
				paySum.setFicaWithholding(fica);
				paySum.setMedicareWithholding(medicare);
				paySum.setEarnedIncCredit(advancedEarnedIC);
				paySum.setStateWithholding(stateWithholding);
				paySum.setCountyWithholding(countyWithholding);
				paySum.setLocalTax(localTax);
				paySum.setTotalTax(empTotalTax);
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
				response.setConfirmPayTotalSum(paySum);
				List<AssociateLoanAndAdvancesDTO> loanAdvance = new ArrayList<>();
				list = new ArrayList<>();
				response.setMyPayroll(list);
				response.setLoanAdvance(loanAdvance);
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
	public List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation,String approverStatus,int periodYear)
	{
		return paysheetFrequencyDao.employeePayrollYTD(candidate,orgLocation,approverStatus,periodYear);
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
		payPeriodDTO.setOrgDateFormat(orgLocations.getDateFormat());
		ResponseList<EmployeePayroll> list = paysheetFrequencyDao.getPayrollRegisterReports(payPeriodDTO);
		for(EmployeePayroll emp : list.getData()) {
//			for(EmployeePayroll emp : freq.getEmployeepayroll()) {
			PayrollReportDTO dto = new PayrollReportDTO();
			dto.setEmpId(emp.getEmployee().getEmployeeId());
			StringBuilder sb = new StringBuilder();
			sb.append(emp.getCandidate().getFirstName());
			if(StringUtils.isNotBlank(emp.getCandidate().getLastName())) {
				sb.append(" ").append(emp.getCandidate().getLastName());
			}
			dto.setEmpName(sb.toString());
			if(StringUtils.isNotBlank(emp.getPayPeriodNum()))
				dto.setRunNo(emp.getPayPeriodNum());
			else
				dto.setRunNo("0");
			if(emp.getCandidate().getWorkLocation() != null)
				dto.setCandidateWorkAddress(emp.getCandidate().getWorkLocation().getOrgLocationAddress());
			dto.setSsnNumber(emp.getCandidate().getConsultantUser().getSsn());
			dto.setDepartmentName(emp.getEmployee().getDepartmentId().getDepartmentName());
			dto.setRegularHours(emp.getRegularHours());
			dto.setGrossPay(emp.getGrossPay());
			dto.setTaxes(emp.getTaxes());
			
			String dateStr = payPeriodDTO.getFreqStartDate();
			Date date = null;
			SimpleDateFormat format = orgLocations.getLocFullFormDateFormatter();
			try {
				 date = format.parse(dateStr);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			
			YTDPayrollDTO ytdDTO = payrollUtil.getEmployeeYTDPayroll(emp.getCandidate(), orgLocations, Constants.APPROVED,year);
			dto.setYtdTaxes(ytdDTO.getYtdTaxes());
			dto.setDeductions(emp.getPreTaxDeduction().add(emp.getPostTaxDeduction()));
			dto.setYtdDeductions(ytdDTO.getYtdDeduction());
			dto.setNetPay(emp.getNetPay());
			dto.setYtdNet(ytdDTO.getYtdNet());
			dto.setYtdHours("NA");
			dto.setType(emp.getType());
			dto.setEmpPayRate(emp.getEmpPayRate());
//			dto.setYtdPayrollDTO(ytdPayrollDTO);
			dto.setEmpRegularAmount(emp.getRegularHoursAmount());	
			
			dto.setCheckDate(commonUtil.orgLocationCommonDate(emp.getPayCheckDate(), orgLocations));
			dto.setGroupName(emp.getPaysheetFrequency().getUserGroup().getGroupName());
			dto.setGroupFrequency(emp.getPaysheetFrequency().getUserGroup().getPayFrequency());
			dto.setFreqStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), emp.getPaysheetFrequency().getFrequencyStartDate()));
			dto.setFreqEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), emp.getPaysheetFrequency().getFrequencyEndDate()));
			dto.setRunDate(commonUtil.orgLocationCommonDate(emp.getCreatedDate(), orgLocations));
			
			for(BankDataHistory bank : emp.getBankDataHistory()) {
				dto.setDepositType(bank.getAccountType());
			}
			
			dto.setResState(emp.getCandidate().getResidenceStateCode());
			if(emp.getEmployee().getHireDate() != null)
				dto.setDateOfJoin(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),emp.getEmployee().getHireDate()));
			else 
				dto.setDateOfJoin("NA");
			
			UserRole uRole = null;
			try {
				uRole = userRoleService.getUserRoleByUserIdAndLocationId(emp.getEmployee().getUser().getId(), emp.getEmployee().getOrgLocations().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			dto.setEmpStatus(uRole.getUserState().toString());
			for(AssociateTaxHistory tax : emp.getAssociateTaxHistory()) {
				dto.setFedMaritalStat(tax.getFederalTaxFilliingStatus());
				dto.setStateMaritalStat(tax.getResidenceTaxFilliingStatus());
				dto.setFedExempt(tax.getFederalExempt().toString());
				dto.setFedAddTax(tax.getFederalTaxAdditionnalWithHolding());
				dto.setStateAddTax(tax.getResidenceTaxAdditionnalWithHolding());
			}
			
			List<PayrollReportDTO> dedList = new ArrayList<>();
			for(DeductionHistory ded : emp.getDeductionHistory()) {
				PayrollReportDTO repoDto = new PayrollReportDTO();
				BigDecimal totalDed = ded.getPreDeductionAmount().add(ded.getPostDeductionAmount());
				repoDto.setDeductions(totalDed);
				YTDPayrollDTO ytd = payrollUtil.getDeductionYTD(emp, ded.getDeductionId().getId(), orgLocations);
				repoDto.setYtdDeductions(ytd.getDeductionYTD());
				dedList.add(repoDto);
			}
			
			dto.setTaxHistory(emp.getUsPayrollTax());
			dto.setYtdPayrollDTO(ytdDTO);
			dto.setDeductionsList(dedList);	
			payrollList.add(dto);
//			}
		}
		dtoList.setData(payrollList);
		dtoList.setRecordsTotal(list.getRecordsTotal());
		dtoList.setRecordsFiltered(list.getRecordsTotal());
		return dtoList;
	}

	@Override
	public YTDPayrollDTO getContributionYTD(EmployeePayroll payroll,Long contributionId,OrgLocations orgLocations,String reportType)
	{
		return paysheetFrequencyDao.getContributionYTD(payroll,contributionId,orgLocations,reportType);
	}

	@Override
	public YTDPayrollDTO getDeductionYTD(EmployeePayroll payroll, Long deductionId, OrgLocations orgLocation,String reportType) {
		
		return paysheetFrequencyDao.getDeductionYTD(payroll,deductionId,orgLocation,reportType);
	}

	@Override
	public YTDPayrollDTO getPayrollTaxesYTD(EmployeePayroll payroll, OrgLocations orgLocation)
	{
		YTDPayrollDTO yTDPayrollDTO = new YTDPayrollDTO();
		try {
			List<EmployeePayroll> payrollList = paysheetFrequencyDao.getPayrollTaxesYTD(payroll,orgLocation);
			 BigDecimal ytdFICA = BigDecimal.ZERO;
			 BigDecimal ytdMedicare = BigDecimal.ZERO;
			 BigDecimal ytdFederal = BigDecimal.ZERO;
			 BigDecimal ytdEarnedIncCredit = BigDecimal.ZERO;
			 BigDecimal ytdStateWithholding = BigDecimal.ZERO;
			 BigDecimal ytdCounty = BigDecimal.ZERO;
			 BigDecimal ytdSUI = BigDecimal.ZERO;
			 BigDecimal ytdSDI = BigDecimal.ZERO;
			 BigDecimal ytdFLI = BigDecimal.ZERO;
			 BigDecimal ytdHCSF = BigDecimal.ZERO;
			 BigDecimal ytdWC = BigDecimal.ZERO;
			 BigDecimal ytdLocaltax = BigDecimal.ZERO;
			for(EmployeePayroll payll : payrollList) {
				EmployeePayrollTax tax = payll.getUsPayrollTax().iterator().next();
				ytdFICA = ytdFICA.add(tax.getFicaWithholding());
				ytdMedicare = ytdMedicare.add(tax.getMedicareWithholding());
				ytdFederal = ytdFederal.add(tax.getFederalWithholding());
				ytdEarnedIncCredit = ytdEarnedIncCredit.add(tax.getEarnedIncCredit());
				ytdStateWithholding = ytdStateWithholding.add(tax.getStateWithholding());
				ytdCounty = ytdCounty.add(tax.getCountyWithholding());
				ytdSUI = ytdSUI.add(tax.getSUI());
				ytdSDI = ytdSDI.add(tax.getSDI());
				ytdFLI = ytdFLI.add(tax.getFLI());
				ytdHCSF = ytdHCSF.add(tax.getHCSF());
				ytdWC = ytdWC.add(tax.getWC());
				ytdLocaltax = ytdLocaltax.add(tax.getLocalTax());
			}
			yTDPayrollDTO.setYtdFICA(ytdFICA);
			yTDPayrollDTO.setYtdMedicare(ytdMedicare);
			yTDPayrollDTO.setYtdFederal(ytdFederal);
			yTDPayrollDTO.setYtdEarnedIncCredit(ytdEarnedIncCredit);
			yTDPayrollDTO.setytdStateWithholding(ytdStateWithholding);
			yTDPayrollDTO.setYtdCounty(ytdCounty);
			yTDPayrollDTO.setYtdSUI(ytdSUI);
			yTDPayrollDTO.setYtdSDI(ytdSDI);
			yTDPayrollDTO.setYtdFLI(ytdFLI);
			yTDPayrollDTO.setYtdHCSF(ytdHCSF);
			yTDPayrollDTO.setYtdWC(ytdWC);
			yTDPayrollDTO.setYtdlocaltax(ytdLocaltax);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return yTDPayrollDTO;
	}

	@Override
	public ResponseList<PayrollReportDTO> getpayrollTaxAndWagesReport(PayrollReportDTO payrollReportDTO, OrgLocations orgLocations)
	{
		ResponseList<PayrollReportDTO> dtoList = new ResponseList<>();
		List<PayrollReportDTO> payrollList = new ArrayList<>();
		payrollReportDTO.setOrgLocationid(orgLocations.getId());
		payrollReportDTO.setOrgDateFormat(orgLocations.getDateFormat());
		ResponseList<EmployeePayroll> list = paysheetFrequencyDao.getPayrollRegisterReports(payrollReportDTO);
		Map<Long, PayrollReportDTO> userMap = new HashMap<>();
		for(EmployeePayroll emp : list.getData()) {
			PayrollReportDTO dto = null;
			
			BigDecimal grossPay = BigDecimal.ZERO;
			BigDecimal ytdTaxes = BigDecimal.ZERO;
			BigDecimal empPayRate = BigDecimal.ZERO;
			BigDecimal federalTax = BigDecimal.ZERO;
			BigDecimal mediCare = BigDecimal.ZERO;
			BigDecimal socialSecTax = BigDecimal.ZERO;
			BigDecimal stateTax = BigDecimal.ZERO;
			BigDecimal localTax = BigDecimal.ZERO;
			BigDecimal futa = BigDecimal.ZERO;
			BigDecimal suta = BigDecimal.ZERO;
			
			if(userMap.containsKey(emp.getCandidate().getId())) {
				dto = userMap.get(emp.getCandidate().getId());
				grossPay = dto.getGrossPay();
				ytdTaxes = dto.getYtdTaxes();
				empPayRate = dto.getEmpPayRate();
				federalTax = dto.getFederalTax();
				mediCare = dto.getMedicare();
				socialSecTax = dto.getSocialSecTax();
				stateTax = dto.getStateTax();
				localTax = dto.getLocalTax();
				futa = dto.getFuta();
				suta = dto.getSuta();
			}else {
				dto = new PayrollReportDTO();
			}
			dto.setEmpId(emp.getEmployee().getEmployeeId());
			StringBuilder sb = new StringBuilder();
			sb.append(emp.getCandidate().getFirstName());
			if(StringUtils.isNotBlank(emp.getCandidate().getLastName())) {
				sb.append(" ").append(emp.getCandidate().getLastName());
			}
			dto.setEmpName(sb.toString());
			if(emp.getCandidate().getWorkLocation() != null)
				dto.setCandidateWorkAddress(emp.getCandidate().getWorkLocation().getOrgLocationAddress());
			
			String ssn = "";
			if(StringUtils.isNotBlank(emp.getEmployee().getUser().getSsn())){
				StringBuilder sb1 = new StringBuilder();
				sb1.append("xxx-xx-");
				try {
					String[] ssnNum = emp.getEmployee().getUser().getSsn().split("-");
					sb1.append(ssnNum[2]);
					ssn = sb1.toString();
				} catch (Exception e) {
					String ssnNum = emp.getEmployee().getUser().getSsn();
					if (ssnNum.length() == 3) {
		    		  sb1.append(ssnNum);
		    		} else if (ssnNum.length() > 3) {
		    			sb1.append(ssnNum.substring(ssnNum.length() - 4));
		    		} 
					ssn = sb1.toString();
				}
				/*String[] ssnNum = payrollReportDto.getSsnNumber().split("-");
				sb1.append(ssnNum[2]);
				ssn = sb1.toString();*/
			}else {
				ssn = "NA";
			}
			
			/*StringBuilder sb1 = new StringBuilder();
			sb1.append("xxx-xx-");
			String[] ssnNum = emp.getCandidate().getConsultantUser().getSsn().split("-");
			sb1.append(ssnNum[2]);*/
			dto.setSsnNumber(ssn);
			dto.setDepartmentName(emp.getEmployee().getDepartmentId().getDepartmentName());
			dto.setGrossPay(grossPay.add(emp.getGrossPay()));
			
			
			//passing frequency year to calculate ytd
			Date date = emp.getPaysheetFrequency().getFrequencyStartDate();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			
			YTDPayrollDTO ytdDTO = payrollUtil.getEmployeeYTDPayroll(emp.getCandidate(), orgLocations, Constants.APPROVED,year);
			dto.setYtdTaxes(ytdTaxes.add(ytdDTO.getYtdTaxes()));
			dto.setType(emp.getType());
			dto.setEmpPayRate(emp.getEmpPayRate());
			
			dto.setCheckDate(commonUtil.orgLocationCommonDate(emp.getPayCheckDate(), orgLocations));
			dto.setFreqStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), emp.getPaysheetFrequency().getFrequencyStartDate()));
			dto.setFreqEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), emp.getPaysheetFrequency().getFrequencyEndDate()));
			dto.setRunDate(commonUtil.orgLocationCommonDate(emp.getCreatedDate(), orgLocations));
			
			dto.setResState(emp.getCandidate().getResidenceStateCode());
			if(emp.getEmployee().getHireDate() != null)
				dto.setDateOfJoin(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),emp.getEmployee().getHireDate()));
			else 
				dto.setDateOfJoin("NA");
			
//			dto.setTaxHistory(emp.getUsPayrollTax());
			for(EmployeePayrollTax tax : emp.getUsPayrollTax()) {
				dto.setFederalTax(federalTax.add(tax.getFederalWithholding()));
				dto.setMedicare(mediCare.add(tax.getMedicareWithholding()));
				dto.setSocialSecTax(socialSecTax.add(tax.getFicaWithholding()));
				dto.setStateTax(stateTax.add(tax.getStateWithholding()));
				dto.setLocalTax(localTax.add(tax.getLocalTax()));
			}
			
			for(EmployerPayrollTax tax : emp.getEmployerpayrolltax()) {
				dto.setFuta(futa.add(tax.getEmployerFUTA()));
				dto.setSuta(suta.add(tax.getEmployerSUTA()));
			}
//			payrollList.add(dto);
			userMap.put(emp.getCandidate().getId(), dto);
		}
		payrollList = new ArrayList<>(userMap.values());
		dtoList.setData(payrollList);
		dtoList.setRecordsTotal(list.getRecordsTotal());
		dtoList.setRecordsFiltered(list.getRecordsTotal());
		return dtoList;
	}

	@Override
	public ResponseList<AssociateReportDTO> getEmployeePayrollListByPayCheckDate(PayrollReportDTO payrollReportDTO,OrgLocations orgLocation) {
		
		List<EmployeePayroll> emp = paysheetFrequencyDao.getEmployeePayrollListByPayCheckDate(payrollReportDTO,orgLocation);
		
		
		 Map<Candidate, List<EmployeePayroll>> uniqueData = emp.stream().collect(Collectors.groupingBy(EmployeePayroll::getCandidate));
		 
		 BigDecimal totalFederalWithholding = BigDecimal.ZERO;
		 BigDecimal totalStateWithholding = BigDecimal.ZERO;
		 BigDecimal totalMedicareWithholding = BigDecimal.ZERO;
		 BigDecimal totalSui = BigDecimal.ZERO;
		 BigDecimal totalfica = BigDecimal.ZERO;
		 
		 ResponseList<AssociateReportDTO> resList = new ResponseList<>();
		 
		 List<AssociateReportDTO> ardList = new ArrayList<>();
		 
		 for (Entry<Candidate, List<EmployeePayroll>> entry : uniqueData.entrySet()) {
			 
			 	AssociateReportDTO ard = new AssociateReportDTO();
			 
				BigDecimal totalGrossPay = entry.getValue().stream().map(EmployeePayroll::getGrossPay).reduce(BigDecimal.ZERO, BigDecimal::add);
				ard.setGrossPay(totalGrossPay);
				
				BigDecimal preTaxDeduction = entry.getValue().stream().map(EmployeePayroll::getPreTaxDeduction).reduce(BigDecimal.ZERO, BigDecimal::add);
				ard.setPreTaxDeduction(preTaxDeduction);
				
				BigDecimal postTaxDeduction = entry.getValue().stream().map(EmployeePayroll::getPostTaxDeduction).reduce(BigDecimal.ZERO, BigDecimal::add);
				ard.setPostTaxDeduction(postTaxDeduction);
				
				BigDecimal netPay = entry.getValue().stream().map(EmployeePayroll::getNetPay).reduce(BigDecimal.ZERO, BigDecimal::add);
				ard.setNetPay(netPay);
				
				for (EmployeePayroll empPayroll : entry.getValue()) {
					
					if(StringUtils.isNotBlank(empPayroll.getEmployee().getEmployeeId())) {
						ard.setEmpId(empPayroll.getEmployee().getEmployeeId());
					}else {
						ard.setEmpId("--");
					}
					
					if(StringUtils.isNotBlank(empPayroll.getEmployee().getUser().getLastName())) {
						StringBuilder sbName = new StringBuilder();
						ard.setEmpName(sbName.append(empPayroll.getEmployee().getUser().getFirstName()).append(" ").append(empPayroll.getEmployee().getUser().getLastName()).toString());
					}else {
						ard.setEmpName(empPayroll.getEmployee().getUser().getFirstName());
					}
					
						
						BigDecimal federalWithholding = empPayroll.getUsPayrollTax().stream().map(EmployeePayrollTax::getFederalWithholding).reduce(BigDecimal.ZERO, BigDecimal::add);
						totalFederalWithholding = totalFederalWithholding.add(federalWithholding);
						
						BigDecimal stateWithholding = empPayroll.getUsPayrollTax().stream().map(EmployeePayrollTax::getStateWithholding).reduce(BigDecimal.ZERO, BigDecimal::add);
						totalStateWithholding = totalStateWithholding.add(stateWithholding);
						
						BigDecimal medicareWithholding = empPayroll.getUsPayrollTax().stream().map(EmployeePayrollTax::getMedicareWithholding).reduce(BigDecimal.ZERO, BigDecimal::add);
						totalMedicareWithholding = totalMedicareWithholding.add(medicareWithholding);
						
						BigDecimal sui = empPayroll.getUsPayrollTax().stream().map(EmployeePayrollTax::getSUI).reduce(BigDecimal.ZERO, BigDecimal::add);
						totalSui = totalSui.add(sui);
						
						BigDecimal fica = empPayroll.getUsPayrollTax().stream().map(EmployeePayrollTax::getFicaWithholding).reduce(BigDecimal.ZERO, BigDecimal::add);
						totalfica = totalSui.add(fica);
						
					
				}
				
				ard.setTotalFederalWithholding(totalFederalWithholding);
				ard.setTotalMedicareWithholding(totalMedicareWithholding);
				ard.setTotalStateWithholding(totalStateWithholding);
				ard.setTotalSui(totalSui);
				ard.setTotalfica(totalfica);
				
				ardList.add(ard);
			}
		 
		 resList.setData(ardList);
		 resList.setRecordsTotal(ardList.size());
		 resList.setRecordsFiltered(uniqueData.size());

		return resList;
	}

	@Override
	public ResponseList<PayrollReportDTO> getContractEmployeeReportList(PayrollReportDTO payrollReportDTO, OrgLocations orgLocation)
	{
		ResponseList<PayrollReportDTO> responseList = new ResponseList<>();
		try {
			payrollReportDTO.setOrgLocationid(orgLocation.getId());
			payrollReportDTO.setOrgDateFormat(orgLocation.getDateFormat());
			List<InvoicePayment> invoicePayItemList = paysheetFrequencyDao.getContractEmployeeReportList(payrollReportDTO);
			List<PayrollReportDTO> payrollReportList = new ArrayList<>();
			Map<Long,PayrollReportDTO> userMap = new HashMap<>();
			for(InvoicePayment invoicePayment : invoicePayItemList) {
				Invoice invoice = invoicePayment.getInvoiceId();
				for(InvoiceLineItems invLine : invoice.getInvoiceLineItems()) {
					PayrollReportDTO dto = new PayrollReportDTO();
					StringBuilder nameSb = new StringBuilder();
					if(StringUtils.isNotBlank(invLine.getCandidate().getLastName()))
						nameSb.append(invLine.getCandidate().getFirstName()).append(" ").append(invLine.getCandidate().getLastName());
					else
						nameSb.append(invLine.getCandidate().getFirstName());
					dto.setEmpName(nameSb.toString());
					if(invLine.getCandidate().getConsultantUser()!= null) {
						Employees emp = employeesService.getEmployeeByUserAndOrgLocationId(invLine.getCandidate().getConsultantUser().getId(), orgLocation.getId());
						if(StringUtils.isNotBlank(emp.getEmployeeId()))
							dto.setEmpId(emp.getEmployeeId());
						else
							dto.setEmpId("");
					}
					
					if(orgLocation.getId().longValue() == invoice.getOrgRelationID().getLocationId().getId().longValue()) {
						dto.setVendorName(invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName());
					}else {
						dto.setVendorName(invoice.getOrgRelationID().getLocationId().getOrgLocationName());
					}
					dto.setPayCheckDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoicePayment.getPaymentDate()));
					dto.setInvoiceId(invoice.getInvoiceId());
					if(StringUtils.isNotBlank(invLine.getAmount()))
						dto.setAssAmount(invLine.getAmount());
					else
						dto.setAssAmount("0");
					Double totalPaid = invoicePayItemList.stream().mapToDouble(ip -> ip.getPaidAmount()).sum();
					dto.setInvPaidAmount(totalPaid.toString());
					userMap.put(invoice.getId(), dto);
					payrollReportList.add(dto);
				}
			}
			responseList.setData(payrollReportList);
			responseList.setRecordsTotal(payrollReportList.size());
			responseList.setRecordsFiltered(payrollReportList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@Override
	public CommonPayrollDataDTO getNextPayrollDate(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId,String orgDateFormat)
	{
		return paysheetFrequencyDao.getNextPayrollDate(intiateUSPayrollDTO,orgLocationId,orgDateFormat);
	}

	@Override
	public List<PaysheetFrequency> getRecentlyRunPayrollListByFrequency(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId, String dateFormat)
	{
		return paysheetFrequencyDao.getRecentlyRunPayrollListByFrequency(intiateUSPayrollDTO, orgLocationId,dateFormat);
	}

	@Override
	public List<PaysheetFrequency> getRecentlyRunPayrollData( Long orgLocationId)
	{
		return paysheetFrequencyDao.getRecentlyRunPayrollData(orgLocationId);
	}

}
