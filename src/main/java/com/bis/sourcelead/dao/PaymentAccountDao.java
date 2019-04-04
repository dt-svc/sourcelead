package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.entity.PaymentAccount;

public interface PaymentAccountDao {

	PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) throws Exception;
	
	PaymentAccount getPaymentAccountById(Long id) throws Exception;
	
	PaymentAccount getPaymentAccountDetailsByCandidateId(Long candidateId) throws Exception;
	
	boolean getisActivePaymentAccountExistedByCandidateId(Long candidateId) throws Exception;

	int saveBankingDetailInPaymentAccount(Long orgLocationId, List<IndAssociatePaymentAccountDTO> list, Map<String, Long> map)  throws Exception;
	
	List<PaymentAccount> getPaymentAccountDetailsByOrglocationId(Long id) throws Exception;
}

