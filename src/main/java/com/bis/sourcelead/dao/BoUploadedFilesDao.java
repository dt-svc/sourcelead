package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.BoUploadedFiles;

public interface BoUploadedFilesDao {

	public BoUploadedFiles saveBoUploadedFiles(BoUploadedFiles boUploadedFiles) throws Exception;
	
	public BoUploadedFiles getBoUploadedFileById(Long id) throws Exception;
	
	List<BoUploadedFiles> getBoUploadedFilesByFinancialInstitueName(String bankName) throws Exception;
}
