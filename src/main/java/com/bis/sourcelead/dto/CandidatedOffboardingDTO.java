package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class CandidatedOffboardingDTO implements Serializable {
	
	 private String candidateoffboardingId;
	 
	 private String staticPath;
	 
	 private List<FileInfo> resignationFileInfo;
	 
	 private List<FileInfo> offboardPackFileInfo;

	 private String savedResignationAttcahments;
	 
	 private String savedOffboardPackAttcahments;
	 
	 
	public String getCandidateoffboardingId() {
		return candidateoffboardingId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public List<FileInfo> getResignationFileInfo() {
		return resignationFileInfo;
	}

	public List<FileInfo> getOffboardPackFileInfo() {
		return offboardPackFileInfo;
	}

	public void setCandidateoffboardingId(String candidateoffboardingId) {
		this.candidateoffboardingId = candidateoffboardingId;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public void setResignationFileInfo(List<FileInfo> resignationFileInfo) {
		this.resignationFileInfo = resignationFileInfo;
	}

	public void setOffboardPackFileInfo(List<FileInfo> offboardPackFileInfo) {
		this.offboardPackFileInfo = offboardPackFileInfo;
	}

	public String getSavedResignationAttcahments() {
		return savedResignationAttcahments;
	}

	public String getSavedOffboardPackAttcahments() {
		return savedOffboardPackAttcahments;
	}

	public void setSavedResignationAttcahments(String savedResignationAttcahments) {
		this.savedResignationAttcahments = savedResignationAttcahments;
	}

	public void setSavedOffboardPackAttcahments(String savedOffboardPackAttcahments) {
		this.savedOffboardPackAttcahments = savedOffboardPackAttcahments;
	}
}