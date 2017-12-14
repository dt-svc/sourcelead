package com.bis.sourcelead.dto;

import java.util.List;

public class OrglocationOnboardDocumentsDTO {

	List<FileInfo> businessLicense;
	
	List<FileInfo> bankCheque;
	
	List<FileInfo> registrationDocument;
	
	List<FileInfo> annualBusinessLicense;
	
	List<FileInfo> w9Document;
	
	private String staticPath;
	
	

	public List<FileInfo> getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(List<FileInfo> businessLicense) {
		this.businessLicense = businessLicense;
	}

	public List<FileInfo> getBankCheque() {
		return bankCheque;
	}

	public void setBankCheque(List<FileInfo> bankCheque) {
		this.bankCheque = bankCheque;
	}

	public List<FileInfo> getRegistrationDocument() {
		return registrationDocument;
	}

	public void setRegistrationDocument(List<FileInfo> registrationDocument) {
		this.registrationDocument = registrationDocument;
	}

	public List<FileInfo> getAnnualBusinessLicense() {
		return annualBusinessLicense;
	}

	public void setAnnualBusinessLicense(List<FileInfo> annualBusinessLicense) {
		this.annualBusinessLicense = annualBusinessLicense;
	}

	public List<FileInfo> getW9Document() {
		return w9Document;
	}

	public void setW9Document(List<FileInfo> w9Document) {
		this.w9Document = w9Document;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
}
