package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.PaymentAccount;

public interface PaymentAccountDao {

	PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) throws Exception;
	
	PaymentAccount getPaymentAccountById(Long id) throws Exception;
	
	PaymentAccount getPaymentAccountDetailsByCandidateId(Long candidateId) throws Exception;
	
	boolean getisActivePaymentAccountExistedByCandidateId(Long candidateId) throws Exception;
}

