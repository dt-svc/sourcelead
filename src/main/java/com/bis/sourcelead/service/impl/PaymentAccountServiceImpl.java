package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.PaymentAccountDao;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.service.PaymentAccountService;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService{

	
	@Autowired
	PaymentAccountDao paymentAccountDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Override
	public PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) throws Exception {
		return paymentAccountDao.savePaymentAccount(paymentAccount);
	}

	@Override
	public PaymentAccount savePaymentAccountDetails(IndAssociatePaymentAccountDTO account, OrgLocations orgLocations)
			throws Exception {
		
		PaymentAccount pay = null;
		if(account.getPaymentAccountId() != null && !account.getPaymentAccountId().isEmpty()) {
			pay = paymentAccountDao.getPaymentAccountById(Long.valueOf(account.getPaymentAccountId()));
		}else {
			pay = new PaymentAccount();
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(account.getAssociateUserId(), orgLocations.getId());
		    pay.setCandidateId(candidate);
		    boolean flag = paymentAccountDao.getisActivePaymentAccountExistedByCandidateId(candidate.getId());
		    if(flag == false) {
		    	pay.setDefaultAccount(true);
		    }
		}
        pay.setAccountId(account.getAccountId());
        pay.setAccountType(account.getAccountType());
        pay.setOrgLocation(orgLocations);
        pay.setFirstName(account.getFirstName());
        pay.setLastName(account.getLastName());
        pay.setBankName(account.getBankName());
        pay.setBsrNumber(account.getBsrNumber());
        pay.setMicrCode(account.getMicrCode());
        pay.setAccountNumber(account.getAccountNumber());
        pay.setRoutingNumber(account.getRoutingNumber());
        pay.setDisabled(false);
        PaymentAccount paymentAccount = savePaymentAccount(pay);
		return paymentAccount;
	}

	@Override
	public PaymentAccount getPaymentAccountById(Long id) throws Exception {
		return paymentAccountDao.getPaymentAccountById(id);
	}

	@Override
	public PaymentAccount getPaymentAccountDetailsByCandidateId(Long candidateId) throws Exception {
		return paymentAccountDao.getPaymentAccountDetailsByCandidateId(candidateId);
	}

}
