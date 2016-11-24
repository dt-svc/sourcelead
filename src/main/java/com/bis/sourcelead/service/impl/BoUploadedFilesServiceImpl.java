package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.BoUploadedFilesDao;
import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.BoUploadedFiles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.BoUploadedFilesService;

@Service
public class BoUploadedFilesServiceImpl implements BoUploadedFilesService{

	@Autowired
	BoUploadedFilesDao boUploadedFilesDao;
	
	@Override
	public BoUploadedFiles saveBoUploadedFiles(BoUploadedFiles boUploadedFiles) throws Exception {
		return boUploadedFilesDao.saveBoUploadedFiles(boUploadedFiles);
	}

	@Override
	public void addBoUploadedFiles(User user, AmexUploadFile amexUploadFile, MultipartFile multiFile) throws Exception {
		
		BoUploadedFiles boUploadedFiles = new BoUploadedFiles();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = sdf.parse(amexUploadFile.getStartDate());
		Date toDate = sdf.parse(amexUploadFile.getEndDate());
		
		boUploadedFiles.setFromDate(fromDate);
		boUploadedFiles.setToDate(toDate);
		boUploadedFiles.setBankName(amexUploadFile.getFinancialInstituteName());
		
        if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
    	  try {
    		  boUploadedFiles.setFileContentType(multiFile.getContentType());
    		  boUploadedFiles.setDocFile(multiFile.getBytes());
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }
        
        boUploadedFiles.setCreatedBy(user.getUserCode());
        boUploadedFiles.setCreatedDate(new Date());
        boUploadedFiles.setUpdatedBy(user.getUserCode());
        boUploadedFiles.setUpdatedDate(new Date());
        boUploadedFilesDao.saveBoUploadedFiles(boUploadedFiles);
		
	}

	@Override
	public List<BoUploadedFiles> getBoUploadedFilesByFinancialInstitueName(String bankName) throws Exception {
		return boUploadedFilesDao.getBoUploadedFilesByFinancialInstitueName(bankName);
	}

}
