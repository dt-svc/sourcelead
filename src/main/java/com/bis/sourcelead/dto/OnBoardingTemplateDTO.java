package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnBoardingTemplateDTO implements Serializable {
	
	 private String templateId;
	 
	 private String templateName;
	 
	 private String description;

	 private String contractsAttcahmentMaxRowNumber;
	 
	 private String formsAttcahmentMaxRowNumber;
	 
	 private String policiesAttcahmentMaxRowNumber;
	 
	 private String supportingAttcahmentMaxRowNumber;
	 
	 private String staticPath;
		
//    private List<Map<String, String>> fileInfo;
	
	private List<FileInfo> contractsFileInfo;
	
	private List<FileInfo> formsFileInfo;
	
	private List<FileInfo> polaciesFileInfo;
	
	private List<FileInfo> supportingFileInfo;
	
	/*private List<String> formFileName;

	private List<String> contractFileName;
	
	private List<String> policiesFileName;

	private List<String> supportingFileName;*/
	 
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

	/*public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}*/
	

	
}