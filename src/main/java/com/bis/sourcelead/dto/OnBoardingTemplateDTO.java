package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnBoardingTemplateDTO implements Serializable {
	
	 private Long id;
	
	 private String templateId;
	 
	 private String templateName;
	 
	 private String description;

	 private String contractsAttcahmentMaxRowNumber;
	 
	 private String formsAttcahmentMaxRowNumber;
	 
	 private String policiesAttcahmentMaxRowNumber;
	 
	 private String supportingAttcahmentMaxRowNumber;
	 
	 private String staticPath;
	
	 private List<FileInfo> contractsFileInfo;
	
	 private List<FileInfo> formsFileInfo;
	
	 private List<FileInfo> polaciesFileInfo;
	
	 private List<FileInfo> supportingFileInfo;
	 
	 private List<FileInfo> vendorDocumentsFileInfo;
	 
	 private List<FileInfo> requestDocumentList;
	 
	 private List<VendorsDocumentsDTO> vendorTemplateDocumentsFileInfo;
	 
	 private List<VendorsDocumentsDTO> vendorOnboardDocumentsFileInfo;
	 
	 private Long vendorOnboardId;
	 
	 
	public String getTemplateId() {
		return templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getDescription() {
		return description;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContractsAttcahmentMaxRowNumber() {
		return contractsAttcahmentMaxRowNumber;
	}

	public String getFormsAttcahmentMaxRowNumber() {
		return formsAttcahmentMaxRowNumber;
	}

	public String getPoliciesAttcahmentMaxRowNumber() {
		return policiesAttcahmentMaxRowNumber;
	}

	public String getSupportingAttcahmentMaxRowNumber() {
		return supportingAttcahmentMaxRowNumber;
	}

	public void setContractsAttcahmentMaxRowNumber(String contractsAttcahmentMaxRowNumber) {
		this.contractsAttcahmentMaxRowNumber = contractsAttcahmentMaxRowNumber;
	}

	public void setFormsAttcahmentMaxRowNumber(String formsAttcahmentMaxRowNumber) {
		this.formsAttcahmentMaxRowNumber = formsAttcahmentMaxRowNumber;
	}

	public void setPoliciesAttcahmentMaxRowNumber(String policiesAttcahmentMaxRowNumber) {
		this.policiesAttcahmentMaxRowNumber = policiesAttcahmentMaxRowNumber;
	}

	public void setSupportingAttcahmentMaxRowNumber(String supportingAttcahmentMaxRowNumber) {
		this.supportingAttcahmentMaxRowNumber = supportingAttcahmentMaxRowNumber;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public List<FileInfo> getContractsFileInfo() {
		return contractsFileInfo;
	}

	public List<FileInfo> getFormsFileInfo() {
		return formsFileInfo;
	}

	public List<FileInfo> getPolaciesFileInfo() {
		return polaciesFileInfo;
	}

	public List<FileInfo> getSupportingFileInfo() {
		return supportingFileInfo;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public void setContractsFileInfo(List<FileInfo> contractsFileInfo) {
		this.contractsFileInfo = contractsFileInfo;
	}

	public void setFormsFileInfo(List<FileInfo> formsFileInfo) {
		this.formsFileInfo = formsFileInfo;
	}

	public void setPolaciesFileInfo(List<FileInfo> polaciesFileInfo) {
		this.polaciesFileInfo = polaciesFileInfo;
	}

	public void setSupportingFileInfo(List<FileInfo> supportingFileInfo) {
		this.supportingFileInfo = supportingFileInfo;
	}

	public List<FileInfo> getVendorDocumentsFileInfo() {
		return vendorDocumentsFileInfo;
	}

	public void setVendorDocumentsFileInfo(List<FileInfo> vendorDocumentsFileInfo) {
		this.vendorDocumentsFileInfo = vendorDocumentsFileInfo;
	}

	public List<FileInfo> getRequestDocumentList() {
		return requestDocumentList;
	}

	public void setRequestDocumentList(List<FileInfo> requestDocumentList) {
		this.requestDocumentList = requestDocumentList;
	}

	public List<VendorsDocumentsDTO> getVendorTemplateDocumentsFileInfo() {
		return vendorTemplateDocumentsFileInfo;
	}

	public void setVendorTemplateDocumentsFileInfo(List<VendorsDocumentsDTO> vendorTemplateDocumentsFileInfo) {
		this.vendorTemplateDocumentsFileInfo = vendorTemplateDocumentsFileInfo;
	}

	public List<VendorsDocumentsDTO> getVendorOnboardDocumentsFileInfo() {
		return vendorOnboardDocumentsFileInfo;
	}

	public void setVendorOnboardDocumentsFileInfo(List<VendorsDocumentsDTO> vendorOnboardDocumentsFileInfo) {
		this.vendorOnboardDocumentsFileInfo = vendorOnboardDocumentsFileInfo;
	}

	public Long getVendorOnboardId() {
		return vendorOnboardId;
	}

	public void setVendorOnboardId(Long vendorOnboardId) {
		this.vendorOnboardId = vendorOnboardId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}