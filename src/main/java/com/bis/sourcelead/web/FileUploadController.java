package com.bis.sourcelead.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.UploadFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;



/**
 * Handles requests for the application file upload requests
 */
@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	private static final String [] FILE_HEADER_MAPPING = {"Employee ID","First Name","Last Name","Email id","Phone Number","Rate","Rate Per","Date of Join","Role","Invoice Frequency","Day Hours"};
	
	private static final String EMPLOYEE_ID = "Employee ID";
	private static final String FIRST_NAME = "First Name";
	private static final String LAST_NAME = "Last Name";
	private static final String EMAIL_ID = "Email id";
	private static final String PHONE_NUMBER = "Phone Number";
	private static final String RATE = "Rate";
	private static final String RATEPER = "Rate Per";
	private static final String DATEOFJOIN = "Date of Join";
	private static final String ROLE = "Role";
	private static final String INVOICE_FREQUENCY = "Invoice Frequency";
	private static final String DAY_HOURS = "Day Hours";


	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/fileUpload", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@ModelAttribute("FileUploadForm") UploadFile uploadfile) {
           MultipartFile multipartFile = uploadfile.getFile();
			FileReader fileReader = null;
			CSVParser csvFileParser = null;
		    //CSVFormat object with the header mapping
			 CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
			try {
				InputStream inputStream = multipartFile.getInputStream();
		       	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		        //initialize CSVParser object
		        csvFileParser = new CSVParser(bufferedReader, csvFileFormat);
		        List csvRecords = csvFileParser.getRecords(); 
//		        List<User> userList = new ArrayList<User>(); 
//		        Set<UserRole> setUserRole1 = new HashSet<UserRole>();
		           //Read the CSV file records starting from the second record to skip the header
		           for (int i = 1; i < csvRecords.size(); i++) {
		               CSVRecord record = (CSVRecord) csvRecords.get(i);
		               //Create a new user object and fill the data
		             /*  User user = new User();
		               UserRole userRole = new UserRole();
		               user.setId(Long.parseLong(record.get(EMPLOYEE_ID)));
		               user.setFirstName(record.get(FIRST_NAME));
		               user.setLastName(record.get(LAST_NAME));
		               user.setEmail(record.get(EMAIL_ID));
		               user.setMobilePhone(record.get(PHONE_NUMBER));
		               userRole.setUser(user);
		               setUserRole1.add(userRole);
		               user.setUserRole(setUserRole1);*/
		               
		               logger.info("==========="+record.get(EMPLOYEE_ID));// User
		               logger.info("=========="+record.get(FIRST_NAME));// User
		               logger.info("==========="+record.get(LAST_NAME));// User
		               logger.info("=========="+record.get(EMAIL_ID));// User
		               logger.info("==========="+record.get(PHONE_NUMBER));// User
		               logger.info("=========="+record.get(RATE));
		               logger.info("==========="+record.get(RATEPER));
		               logger.info("=========="+record.get(DATEOFJOIN));// Employee table
		               logger.info("==========="+record.get(ROLE));
		               logger.info("=========="+record.get(INVOICE_FREQUENCY));
		               logger.info("=========="+record.get(DAY_HOURS));
		           }
//		        logger.info("+++++++++++++++++++++++++++++++++++"+userList.size());
			} catch (Exception e) {}
		
		return "sucess";
	}
	




}
