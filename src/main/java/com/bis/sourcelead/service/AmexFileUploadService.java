package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.User;

public interface AmexFileUploadService {

	public String uploadAmexFile(MultipartFile multipartFile, User user, AmexUploadFile amexUploadFile) throws Exception;
}
