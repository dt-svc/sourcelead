package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.List;

public class PerformanceAppraisalDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	PerformanceAppraisalTemplateDetailsDTO appraisalTemplate;
	
	List<PerformanceAppraisalPeersDTO> peersList;

	public PerformanceAppraisalTemplateDetailsDTO getAppraisalTemplate() {
		return appraisalTemplate;
	}

	public void setAppraisalTemplate(PerformanceAppraisalTemplateDetailsDTO appraisalTemplate) {
		this.appraisalTemplate = appraisalTemplate;
	}

	public List<PerformanceAppraisalPeersDTO> getPeersList() {
		return peersList;
	}

	public void setPeersList(List<PerformanceAppraisalPeersDTO> peersList) {
		this.peersList = peersList;
	}
	
	
	
}
