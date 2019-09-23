package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ClientVendorUploadDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<String> templateId;

	public List<String> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(List<String> templateId) {
		this.templateId = templateId;
	}
}
