package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.bis.sourcelead.payroll.service.PayrollAdjustmentService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.util.PayrollPdfUtil;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
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
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PayrollUtil payrollUtil;
	
	@Autowired
	AssociateTaxDao associateTaxDao;
	
	@Autowired
	GenerateUSPayrollService generateUSPayrollService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	PayrollPdfUtil payrollPdfUtil;
	
	@Autowired
	PayrollAdjustmentService payrollAdjustmentService;
	
	
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
			
			Set<EmployeePayroll> empPayroll = pf.getEmployeepayroll();
			/*if(!"All".equalsIgnoreCase(payrollApproverDTO.getPayType())) {
				 empPayroll = pf.getEmployeepayroll().stream().filter(p->payrollApproverDTO.getPayType().equalsIgnoreCase(p.getPayType())).collect(Collectors.toSet());	
			}else {
				 empPayroll = pf.getEmployeepayroll();
			}*/
			
			List<EmployeePayroll> payrollListt = null;
			if(Constants.DRAFT.equalsIgnoreCase(payrollApproverDTO.getConfirmType())) {
				 payrollListt = empPayroll
						.stream()
						.filter(payy-> !Constants.APPROVED.equalsIgnoreCase(payy.getSubmitForAproverStatus()) && payy.getSubmitForAproverStatus().equalsIgnoreCase(payrollApproverDTO.getConfirmType()))
						.collect(Collectors.toList());
			}else {
				 payrollListt = empPayroll
						.stream()
						.filter(payy-> !Constants.APPROVED.equalsIgnoreCase(payy.getSubmitForAproverStatus()) && payy.getSubmitForAproverStatus().equalsIgnoreCase(payrollApproverDTO.getConfirmType()) && Integer.parseInt(payy.getPayPeriodNum()) == Integer.parseInt(payrollApproverDTO.getPayPeriodNum()))
						.collect(Collectors.toList());
			}
			CopyOnWriteArraySet<EmployeePayroll> payrollList = new CopyOnWriteArraySet<>(payrollListt);
			List<Long> payrollIds = null;
			for (EmployeePayroll payroll : payrollList) {
				for (USPayrollDTO payrollDTO : payrollApproverDTO.getUspayroll()) {
					if ((payrollDTO.getIsExcept() != null && "YES".equalsIgnoreCase(payrollDTO.getIsExcept())) && payrollDTO.getEmpPayrollId().longValue() == payroll.getId().longValue()) {
						boolean s = payrollList.removeIf(x -> x.getId().longValue() == payroll.getId());
					}
				}
			}
			
				payrollIds = payrollList.stream().map(p -> p.getId()).collect(Collectors.toList());
			
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
			if(StringUtils.isNotBlank(payrollApproverDTO.getPayPeriodNum())) {
				paySum.setPpn(payrollApproverDTO.getPayPeriodNum());
			}
			if(dedList.size() >0) {
			    Map<String, Double> list = dedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
			    paySum.setDeduction(list);
			}
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
			Set<EmployeePayroll> approvedPayrollList = new HashSet<>();
			for (EmployeePayroll payroll : payrollList) {
				for (Long payrollId : confirmPaySumDto.getPid()) {
					if (payrollId.longValue() == payroll.getId().longValue()) {
						payroll.setSubmitForAproverStatus(PayrollConstant.APPROVE);
						if (PayrollConstant.DRAFT.equalsIgnoreCase(confirmPaySumDto.getConfirmType())) {
							payroll.setPayPeriodNum(String.valueOf(uSPayPeriod.getPayPeriodNum()));
						}
						approvedPayrollList.add(payroll);
					}
				}
			}
			pf.setEmployeepayroll(payrollList);
			PaysheetFrequency savedPF = uSPaysheetDao.addPaysheetFrequency(pf);
			if (savedPF != null) {
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(confirmPaySumDto);
				
				generatePdfForEmployee(approvedPayrollList,orgLocation);
				
				sendMailApprovedMailToRequestedUser(orgLocation,savedPF,loginUser);
				
                 
			}
		} catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}
	
	/**
	 * Generate pay slip for an approved employees
	 * @param approvedPayrollList
	 * @param orgLocation
	 */
	private void generatePdfForEmployee(Set<EmployeePayroll> approvedPayrollList, OrgLocations orgLocation) {
		for (EmployeePayroll payroll : approvedPayrollList) {
			try {
				AssociateTax associateTax= associateTaxDao.taxDetailsByOrglocationIdAndCandidateId(payroll.getCandidate().getId(),orgLocation.getId());
				File file = payrollPdfUtil.generateEmployeePayrollPayslipPdf(payroll,orgLocation,associateTax);
				payroll.setPaySlipFileName(file.getName());
				payroll.setPaySlipFileURL(FileUploadUtil.moveFile(staticPath, orgLocation.getOrg().getId(), orgLocation.getId(), "US_PAYROLL", file));
				EmployeePayroll updatedPayroll = payrollAdjustmentService.updateEmployeePayroll(payroll);
				if(updatedPayroll != null) {
					logger.info("Pay Slip generated for an employee "+payroll.getCandidate().getFirstName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
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
		
		//Redirection URl
		String redirectURL = "/viewPaySheetFrequencyData-params-approverStatus=SENT-and-paySheetFrequencyUUID="
				+ savedPF.getPaySheetFrequencyUUID() + "-and-payPeriodNum=" + uSPayPeriodw.getPayPeriodNum();
		Long redirectOrgLocationId = orgLocation.getId();
		
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
		
		Set<EmployeePayroll> filteredEmpPayroll = savedPF.getEmployeepayroll().stream().filter(p->!"MISSED".equalsIgnoreCase(p.getSubmitForAproverStatus())).collect(Collectors.toSet());
		double amount = filteredEmpPayroll.stream().mapToDouble(p->p.getNetPay().doubleValue()).sum();
		globalVarsMap.put("amount", String.valueOf(amount));
		
		StringBuilder stringBuilderLoginname = new StringBuilder();
		globalVarsMap.put("approveLoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		StringBuilder stringBuildforLink = new StringBuilder();
		//globalVarsMap.put("payrollLink", stringBuildforLink.append("<a href='").append(sourceleadUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		globalVarsMap.put("payrollLink", stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Payroll Approved</div></a>")
				.toString());
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
	
	

	
	}

	
