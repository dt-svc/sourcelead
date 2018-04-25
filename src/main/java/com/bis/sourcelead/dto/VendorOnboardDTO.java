package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;

public class VendorOnboardDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String clientName;
	
	private String clientWebsite;
	
	private String clientLocation;
	
	private String clientAddress;
	
	private String vendorName;
	
	private String vendorWebsite;
	
	private String vendorLocation;
	
	private String vendorAddress;
	
	private String clientContactName;
	
	private String clientContactEmail;
	
	private String clientContactPhone;
	
	private String paymentTerm;
	
	private String tier;
	
	private String industryName;
	
	private String currency;
	
	private String sicCode;
	
    private String naicsCode;
	
	private String einCode;
	
	private String taxRegNumber;
	
	private String dunsNumber;
	
	private String bankName;
	
	private String accountName;
	
	private String accountNumber;
	
	private String branchNumber;
	
	private String bankAddress;
	
	private String bicNumber;
	
	private String staticPath;
	
	private List<VendorDocumentsFileInfo> vendorDocumentsFileInfo;
	
	@CleanHtml(escape=false)
	private String vendorBillingAddress;
	
	private String poEmail;
	
	private String paymentRemittanceEmail;
	
	

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientWebsite() {
		return clientWebsite;
	}

	public void setClientWebsite(String clientWebsite) {
		this.clientWebsite = clientWebsite;
	}

	public String getClientLocation() {
		return clientLocation;
	}

	public void setClientLocation(String clientLocation) {
		this.clientLocation = clientLocation;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorWebsite() {
		return vendorWebsite;
	}

	public void setVendorWebsite(String vendorWebsite) {
		this.vendorWebsite = vendorWebsite;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getClientContactName() {
		return clientContactName;
	}

	public void setClientContactName(String clientContactName) {
		this.clientContactName = clientContactName;
	}

	public String getClientContactEmail() {
		return clientContactEmail;
	}

	public void setClientContactEmail(String clientContactEmail) {
		this.clientContactEmail = clientContactEmail;
	}

	public String getClientContactPhone() {
		return clientContactPhone;
	}

	public void setClientContactPhone(String clientContactPhone) {
		this.clientContactPhone = clientContactPhone;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getTaxRegNumber() {
		return taxRegNumber;
	}

	public void setTaxRegNumber(String taxRegNumber) {
		this.taxRegNumber = taxRegNumber;
	}

	public String getDunsNumber() {
		return dunsNumber;
	}

	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBicNumber() {
		return bicNumber;
	}

	public void setBicNumber(String bicNumber) {
		this.bicNumber = bicNumber;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<VendorDocumentsFileInfo> getVendorDocumentsFileInfo() {
		return vendorDocumentsFileInfo;
	}

	public void setVendorDocumentsFileInfo(List<VendorDocumentsFileInfo> vendorDocumentsFileInfo) {
		this.vendorDocumentsFileInfo = vendorDocumentsFileInfo;
	}

	public String getVendorBillingAddress() {
		return vendorBillingAddress;
	}

	public void setVendorBillingAddress(String vendorBillingAddress) {
		this.vendorBillingAddress = vendorBillingAddress;
	}

	public String getNaicsCode() {
		return naicsCode;
	}

	public void setNaicsCode(String naicsCode) {
		this.naicsCode = naicsCode;
	}

	public String getEinCode() {
		return einCode;
	}

	public void setEinCode(String einCode) {
		this.einCode = einCode;
	}

	public String getPoEmail() {
		return poEmail;
	}

	public void setPoEmail(String poEmail) {
		this.poEmail = poEmail;
	}

	public String getPaymentRemittanceEmail() {
		return paymentRemittanceEmail;
	}

	public void setPaymentRemittanceEmail(String paymentRemittanceEmail) {
		this.paymentRemittanceEmail = paymentRemittanceEmail;
	}


	
}
