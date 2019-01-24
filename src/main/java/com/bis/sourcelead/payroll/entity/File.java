package com.bis.sourcelead.payroll.entity;

import org.springframework.web.multipart.MultipartFile;
/**
 * 
 * @author Rajashekar
 * date 15-06-2017
 *
 */
public class File {
	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
