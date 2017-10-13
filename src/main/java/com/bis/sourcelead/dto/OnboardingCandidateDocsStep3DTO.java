package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnboardingCandidateDocsStep3DTO implements Serializable {
	
	private String candidateOnboardId;
	
	private String staticPath;
	
	private List<FileInfo> uploadContractsFile; 
	
	private List<FileInfo> uploadFormsFile;

	public String getCandidateOnboardId() {
		return candidateOnboardId;
	}

	public List<FileInfo> getUploadContractsFile() {
		return uploadContractsFile;
	}

	public List<FileInfo> getUploadFormsFile() {
		return uploadFormsFile;
	}

	public void setCandidateOnboardId(String candidateOnboardId) {
		this.candidateOnboardId = candidateOnboardId;
	}

	public void setUploadContractsFile(List<FileInfo> uploadContractsFile) {
		this.uploadContractsFile = uploadContractsFile;
	}

	public void setUploadFormsFile(List<FileInfo> uploadFormsFile) {
		this.uploadFormsFile = uploadFormsFile;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
}