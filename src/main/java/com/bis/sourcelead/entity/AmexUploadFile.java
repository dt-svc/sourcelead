package com.bis.sourcelead.entity;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class AmexUploadFile  implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private MultipartFile file;
	
	private String startDate;
	
	private String endDate;
	
	private String financialInstituteName;
	
	

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFinancialInstituteName() {
		return financialInstituteName;
	}

	public void setFinancialInstituteName(String financialInstituteName) {
		this.financialInstituteName = financialInstituteName;
	}
}
