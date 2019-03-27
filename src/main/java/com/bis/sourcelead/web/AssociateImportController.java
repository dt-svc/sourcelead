package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import com.bis.sourcelead.dto.AssociateSalaryPayheadIndDTO;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dto.AssociateBankErrorData;
import com.bis.sourcelead.rest.PayrollConfigurationRestController;
import com.bis.sourcelead.service.AssociateImportService;
import com.bis.sourcelead.service.CandidateService;
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
	
	@Autowired
	AssociateImportService associateImportService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	/*  ---------------------Associate banking Details import starts from here---------------------------*/
	
	 @PreAuthorize("hasRole('ROLE_USER_BULK_IMPORTS')")
		@RequestMapping(value = "/app/uploadAssociateBankingDetailsForm")
		public ModelAndView uploadAssociateBankDetail(HttpServletRequest request) {
			SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession()
					.getAttribute("sessionData");
			if (sessionData == null) {
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Users);

			request.getSession().setAttribute("sessionData", sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadAssociateBankingDetailsForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	
	/**
	 * @rajat
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/uploadbankdetails", method = RequestMethod.POST)
	@ResponseBody
	public AssociateBankErrorData uploadbankdetails(MultipartHttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		AssociateBankErrorData response = null;
		if (multipartFile != null) {
			response = associateImportService.saveImportBankDetails(multipartFile, orgLocation);
			return response;
		}
		return response;
	}
	
	
	// method for creating excel sheet
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
	 
	 /*---Ends here---*/
	 
	 
/*---------------------------------for associate Information import--------------------------------------------------*/
	 
	 @PreAuthorize("hasRole('ROLE_USER_BULK_IMPORTS')")
		@RequestMapping(value = "/app/uploadAssociateInformation")
		public ModelAndView uploadAssociateInformationForm(HttpServletRequest request) {
			SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession()
					.getAttribute("sessionData");
			if (sessionData == null) {
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Users);
			request.getSession().setAttribute("sessionData", sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadAssociateInformation");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	
	/**
	 * @rajat
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/uploadAssociateInfomation", method = RequestMethod.POST)
	@ResponseBody
	public AssociateBankErrorData uploadAssociateInfomation(MultipartHttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		AssociateBankErrorData response = null;
		if (multipartFile != null) {
			response = associateImportService.saveAssociateInformation(multipartFile, orgLocation);
			return response;
		}
		return response;
	}
	
	
	// method for creating excel sheet
	 @RequestMapping(value = "/app/downloadTemplateForAssociateInfomation", method = RequestMethod.GET)
		public void downloadTemplateForAssociateInfomation(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        @SuppressWarnings("resource")
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("AssociateInformationTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_ASSOCIATE_INFO_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellType(Cell.CELL_TYPE_STRING);
	            cell.setCellValue((String)key);
	        }	        	        
	        try {
	            File file = new File("Associate_Information_Template.xls");
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
	 
	 /*   --------Ends here--------   */
	 

	 
//------------------import associate salary starts --------------------------------
	 
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
		
		@RequestMapping(value = "/app/saveAssociateSalaryDetailsTemplate", method = RequestMethod.POST)
		@ResponseBody
		public ResponseList<AssociateSalaryIndDTO> saveAssociateSalaryDetailsTemplate(MultipartHttpServletRequest request) throws Exception
		{	
			ResponseList<AssociateSalaryIndDTO> response = new ResponseList<>();
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			MultipartFile multipartFile = null;
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multipartFile = request.getFile(itrator.next());
			}
			if (multipartFile != null) {
				response = associateImportService.saveAssociateSalaryDetails(multipartFile, orgLocation, user);
			}
			return response;
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
	            if(key.equalsIgnoreCase("Pay Group*") && userGroupList.size() > 0) {
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
		
		
		private DataValidation getDropDownListForCell(String[] ratePerList, int column, String fileName)
		{
			@SuppressWarnings("deprecation")
			CellRangeAddressList addressList = new CellRangeAddressList(1, 500, column, column);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(ratePerList);
			DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
			dataValidation.setSuppressDropDownArrow(false);
			return dataValidation;
		}
	
		/**method for reading data from excel
		 * Read cell value as String
		 * @param cell
		 * @param fileName
		 * @return String
		 */
		/*private String getCellValueAsString(Cell cell, String fileName) {
			String cellValue = "";
			if(fileName.toLowerCase().endsWith("xls")){
					HSSFCell hssfCell = (HSSFCell) cell;
					if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						hssfCell.setCellType(Cell.CELL_TYPE_STRING);
						String hssfCellValue = hssfCell.getStringCellValue();
						if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
							cellValue = hssfCellValue;
						} 
					}else{
						cellValue = cell.getStringCellValue();
					}
				}else{
					XSSFCell xssfCell = (XSSFCell) cell;
					if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						xssfCell.setCellType(Cell.CELL_TYPE_STRING);
						String xssfCellValue = xssfCell.getStringCellValue();
						if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
							cellValue = xssfCellValue;
						} 
					}else{
						cellValue = cell.getStringCellValue();
					}
				}
				return cellValue;
			}*/
}

