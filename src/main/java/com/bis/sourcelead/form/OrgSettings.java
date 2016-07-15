package com.bis.sourcelead.form;

import org.springframework.web.multipart.MultipartFile;

public class OrgSettings {
	
	private Integer Id;
	
	private MultipartFile file;
	
	

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
