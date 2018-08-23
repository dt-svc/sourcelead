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
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.MyPayrollDTO;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
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
				List<PayrollApprover> pa = configurePayrollApproverService.getPayrollApproversByCPAID(cpa.getId());
				for (PayrollApprover payrollApprover : pa) {
					sendMailToPayrollApprover(orgLocation,loginUser,payrollApprover,savedPF);
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

	@Override
	public MyPayrollSummaryDTO getMyPayrollListByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		MyPayrollSummaryDTO response = new MyPayrollSummaryDTO();
		try {
			List<EmployeePayroll> empPayroll = paysheetFrequencyDao.getMyPayrollListByCandidateId(candidateId);
			List<MyPayrollDTO> list = null;
			List<DeductionDTO> dedHisList = new ArrayList<>();
			List<ContributionDTO> contributionHisList = new ArrayList<>();
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
					
					List<DeductionDTO> dlist = getDeductionData(payroll);
					dedHisList.addAll(dlist);
					List<ContributionDTO> clist = getContributionData(payroll);
					contributionHisList.addAll(clist);
					
//			        Map<String, Double> sum = s.stream().collect(Collectors.groupingBy(DeductionHistory::getName, Collectors.summingDouble(Item::getQty)));
					
					
					
					list.add(myPayrollDTO);
				}
				
				if(dedHisList.size() >0) {
					List<DeductionDTO> preDedList = dedHisList.stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				    List<DeductionDTO> postDedList = dedHisList.stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
				    Map<String, Double> preDList = preDedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
				    response.setPreDed(preDList);
				    Map<String, Double> postDList = postDedList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getPostDeductionAmt)));
				    response.setPostDed(postDList);
				}
				
				if(contributionHisList.size() >0) {
//					List<ContributionDTO> preConList = contributionHisList.stream().filter(con->"pretax".equalsIgnoreCase(con.getTaxType())).collect(Collectors.toList());
//					List<ContributionDTO> postConList = contributionHisList.stream().filter(con->"posttax".equalsIgnoreCase(con.getTaxType())).collect(Collectors.toList());
//					Map<String, Double> preCList = preConList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getAmount)));
//					response.setPreContribution(preCList);
					Map<String, Double> contribution = contributionHisList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getPostContributionAmt)));
					response.setContribution(contribution);
				}
				response.setMyPayroll(list);
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
					if (dedHis.getContributionId() != null) {
						ContributionDTO contDTO = new ContributionDTO();
						if (dedHis.getPreContributionAmount() != null) {
							precontrAmt = dedHis.getPreContributionAmount().doubleValue();
						}
						if (dedHis.getPostContributionAmount() != null) {
							postcontrAmt = dedHis.getPostContributionAmount().doubleValue();
						}
						contDTO.setAmount(precontrAmt);
						contDTO.setPostContributionAmt(postcontrAmt);
						contDTO.setId(String.valueOf(dedHis.getContributionId().getId()));
						contDTO.setContributionType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
						contDTO.setTaxType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getTaxType());
						contDTO.setContributionAmount(dedHis.getContributionId().getContributionAmount());
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
}
