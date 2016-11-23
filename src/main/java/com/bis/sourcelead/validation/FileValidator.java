package com.bis.sourcelead.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.UploadedFile;

@Component
public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object uploadedFile, Errors errors) {

		UploadedFile file = (UploadedFile) uploadedFile;
       
		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "uploadForm.salectFile", "Please select a file !...;");
		}
		if(file.getFile().getSize() != 0){
			 MultipartFile multiPartFile = file.getFile();
		        File convFile = new File( multiPartFile.getOriginalFilename());
		        try {
					multiPartFile.transferTo(convFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
		        double fileSize = convFile.length();
		        double kilobytes = (fileSize / 1024);
		if (!file.getFile().getOriginalFilename().endsWith("xlsx") && !file.getFile().getOriginalFilename().endsWith("xls") && !file.getFile().getOriginalFilename().endsWith("csv")) {
			errors.rejectValue("file", "uploadForm.salectFile", "accept only xlsx and xls !...;");
		}
        if (kilobytes > 500.0) {
			
			errors.rejectValue("file", "uploadForm.salectFile", "File size should not be exceed 500KB !..;");
		}
		}
		

	}
	
	
	public void validateTransFile(Object amexUploadFile, Errors errors) {

		AmexUploadFile file = (AmexUploadFile) amexUploadFile;
       
		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "uploadForm.salectFile", "Please select a file !...;");
		}
		if(file.getFile().getSize() != 0){
			 MultipartFile multiPartFile = file.getFile();
		        File convFile = new File( multiPartFile.getOriginalFilename());
		        try {
					multiPartFile.transferTo(convFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
		        double fileSize = convFile.length();
		        double kilobytes = (fileSize / 1024);
		if (!file.getFile().getOriginalFilename().endsWith("xlsx") && !file.getFile().getOriginalFilename().endsWith("xls") && !file.getFile().getOriginalFilename().endsWith("csv")) {
			errors.rejectValue("file", "uploadForm.salectFile", "accept only xlsx and xls and csv !...;");
		}
        /*if (kilobytes > 500.0) {
			errors.rejectValue("file", "uploadForm.salectFile", "File size should not be exceed 500KB !..;");
		}*/
		}
		

	}

}
