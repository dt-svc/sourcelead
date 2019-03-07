package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;


public interface PaymentAccountService {

	PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) throws Exception;
	
	PaymentAccount savePaymentAccountDetails(IndAssociatePaymentAccountDTO paymentAccountDTO,OrgLocations orgLocations) throws Exception;
	
	PaymentAccount getPaymentAccountById(Long id) throws Exception;
	
	PaymentAccount getPaymentAccountDetailsByCandidateId(Long candidateId) throws Exception;
	
	IndAssociatePaymentAccountDTO saveImportBankDetails(MultipartFile multipartFile, OrgLocations orgLocation) throws Exception;
	
	//PaymentAccount saveBankingAccountDetails(IndAssociatePaymentAccountDTO paymentAccountDTO,OrgLocations orgLocations) throws Exception;
	
}
