package com.bis.sourcelead.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.BoUploadedFiles;
import com.bis.sourcelead.entity.User;

public interface BoUploadedFilesService {

	public BoUploadedFiles saveBoUploadedFiles(BoUploadedFiles boUploadedFiles) throws Exception;
	
	public void addBoUploadedFiles(User user,AmexUploadFile amexUploadFile,MultipartFile file) throws Exception;
	
	List<BoUploadedFiles> getBoUploadedFilesByFinancialInstitueName(String bankName) throws Exception;
	
	public BoUploadedFiles getBoUploadedFileById(Long id) throws Exception;
}
