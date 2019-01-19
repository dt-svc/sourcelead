package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SourceleadTaxDocumentsDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<MultipartFile> files;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
