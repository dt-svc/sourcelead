package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociateSalaryIndDTO;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.rest.PayrollConfigurationRestController;
import com.bis.sourcelead.service.PaymentAccountService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;



@Controller
@PropertySource("${propertyLocation:app.properties}")
public class AssociateImportController {
	private static final Logger logger = LoggerFactory.getLogger(PayrollConfigurationRestController.class);

	@Autowired
	PaymentAccountService paymentAccountService;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	/**
	 * @rajat
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/uploadbankdetails", method = RequestMethod.POST)
	@ResponseBody
	public IndAssociatePaymentAccountDTO uploadbankdetails(MultipartHttpServletRequest request) throws Exception
	{
		//User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		//Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		IndAssociatePaymentAccountDTO response = new IndAssociatePaymentAccountDTO();
		if (multipartFile != null) {
			response = paymentAccountService.saveImportBankDetails(multipartFile, orgLocation);
			return response;
		}
		return response;
	}
	
	
	 @RequestMapping(value = "/app/downloadTemplateForAssociateBankingDetails", method = RequestMethod.GET)
		public void downloadTemplateForAssociateBankingDetails(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        @SuppressWarnings("resource")
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("AssociateBankingTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_ASSOCIATE_BANKING_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	            if(key.equalsIgnoreCase("Payment Mode*")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.PAYMENT_MODE, cellnum-1, "xls"));
	            }
	            
	        }
	        try {
	            File file = new File("Associate_Banking_Template.xls");
	    		FileOutputStream out = new FileOutputStream(file);
	    		workbook.write(out);
	            
	            out.close();
	            out.flush();
	            
	            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
	            
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
			
		}
	 
	 
	 @PreAuthorize("hasRole('ROLE_USER_BULK_IMPORTS')")
		@RequestMapping(value = "/app/uploadAssociateSalary")
		public ModelAndView uploadAssociateSalary(HttpServletRequest request) {
			SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession()
					.getAttribute("sessionData");
			if (sessionData == null) {
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Users);

			request.getSession().setAttribute("sessionData", sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadAssociateSalaryForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
		
		
		@RequestMapping(value = "/app/downloadDataImportTemplateForAssociateSalary")
		public void downloadDataImportTemplateForAssociateSalary(HttpServletRequest request, HttpServletResponse response) throws Exception{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			@SuppressWarnings("resource")
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("AssociateSalaryTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_ASSOCIATE_SALARY_EXCEL_HEADERS;
	        
	        List<PayrollPayheadDTO> payHeadDTOList = payrollPayheadService.getPayrollPayheadList(orgLocation).getData();
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	            if(key.equalsIgnoreCase("Pay Type*")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.ASSOCIATE_SALARY_PAYTYPE, cellnum-1, "xls"));
	            }
	            if(key.equalsIgnoreCase("Pay Frequency*")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.ASSOCIATE_SALARY_PAYFREQUENCY, cellnum-1, "xls"));
	            }
	            List<String> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocation).stream().map(a -> a.getGroupName()).collect(Collectors.toList());
	            if(key.equalsIgnoreCase("Pay Group") && userGroupList.size() > 0) {
	            	sheet.addValidationData(getDropDownListForCell(userGroupList.stream().toArray(String[]::new), cellnum-1, "xls"));
	            }
	        }
	        
	        if(payHeadDTOList.size() > 0)
	        for(PayrollPayheadDTO ph : payHeadDTOList) {
	        	Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)ph.getName());
	        }
	        
	        try {
	            File file = new File("Associate_Salary_Template.xls");
	    		FileOutputStream out = new FileOutputStream(file);
	    		workbook.write(out);
	            
	            out.close();
	            out.flush();
	            
	            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
	            
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		
		@RequestMapping(value = "/app/saveAssociateSalaryDetailsTemplate", method = RequestMethod.POST)
		@ResponseBody
		public AssociateSalaryIndDTO saveAssociateSalaryDetailsTemplate(MultipartHttpServletRequest request) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			MultipartFile multipartFile = null;
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multipartFile = request.getFile(itrator.next());
			}
			AssociateSalaryIndDTO response = null;
			if (multipartFile != null) {
				response = this.saveAssociateSalaryDetails(multipartFile, orgLocation);
				return response;
			}
			return response;
		}
		
	 
		private AssociateSalaryIndDTO saveAssociateSalaryDetails(MultipartFile multipartFile,
				OrgLocations orgLocation) {
			String fileName = multipartFile.getOriginalFilename();
			try {
				File file = new File(multipartFile.getOriginalFilename());
				multipartFile.transferTo(file);
				FileInputStream fis = new FileInputStream(file);
				Workbook workbook;
				logger.debug("File Extension is: " + multipartFile.getOriginalFilename());
				//list of dto
				List<IndAssociatePaymentAccountDTO> list = new ArrayList<>();
				workbook = WorkbookFactory.create(fis);
				int numberOfSheets = workbook.getNumberOfSheets();
				for (int i = 0; i < numberOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					Iterator<Row> rowIterator = sheet.iterator();
					
					int count =0;
					while (rowIterator.hasNext()) {
						IndAssociatePaymentAccountDTO importDTO = new IndAssociatePaymentAccountDTO();
						Row row = rowIterator.next();
							if (row.getRowNum() > 0 && (row.getCell(1) != null && row.getCell(2) != null)) {
								
								
								
								
							}else{
								count = count++;
								if(count == 5)	
								 break;
							}
					 }				
				}	
				int size =list.size();
				
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	 
		private DataValidation getDropDownListForCell(String[] ratePerList, int column, String fileName)
		{
			@SuppressWarnings("deprecation")
			CellRangeAddressList addressList = new CellRangeAddressList(1, 500, column, column);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(ratePerList);
			DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
			dataValidation.setSuppressDropDownArrow(false);
			return dataValidation;
		}
}
