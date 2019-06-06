package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class DataMigrationCommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationCommonController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	ShiftService shiftService;
	
	@Autowired
	AssociateGradesService associateGradesService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	private AssetCategoryService assetCategoryService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	 /*
	  * author Venkat Kesav
	  * redirect to jobsImport page
	  */
	 //@PreAuthorize("hasAnyRole('ROLE_JOBS_BULK_IMPORTS')")
	 @RequestMapping(value = "/uploadJobs")
		public ModelAndView uploadJobs(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Requirement);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadJobForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 @RequestMapping(value = "/uploadImmigrationForm")
		public ModelAndView uploadImmigrationForm(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Immigrations);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadImmigrationForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 @RequestMapping(value = "/uploadLcaForm")
		public ModelAndView uploadLcaForm(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Immigrations);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadLcaForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 @RequestMapping(value = "/uploadProjectTasks")
		public ModelAndView uploadProjectTasks(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) {
		        SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Projects);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadProjectTasksForm");
			model.addObject("projectUUID", projectUUID);
			model.addObject("projectClientUUID", projectClientUUID);
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 
	 
	 @RequestMapping(value = "/uploadassetManagement")
		public ModelAndView uploadassetManagement(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.AssetManagement);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadassetManagement");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 
	 /*
	  * author Venkat Kesav
	  * redirect to timeSheetImport page
	  */
	 @RequestMapping(value = "/uploadTimesheet")
		public ModelAndView uploadTimesheet(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.TimeSheet);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			commonUtil.highLightMenu(Constants.TimeSheet, request);
			model.setViewName("upload/uploadTimeSheetForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
	 
	 
	 @RequestMapping(value = "/downloadDataImportTemplate", method = RequestMethod.GET)
		public void downloadDataImportTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
			logger.debug("download job template is for Import downloadJobsDataImportTemplate method");
	        @SuppressWarnings("resource")
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("JobTemplate");
	        String[] tableColumns = Constants.IMPORT_JOB_EXCEL_HEADERS;
	        
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	            
	            if(key.contentEquals("Notice Period")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.NOTICE_PERIOD, cellnum-1, "xls"));
	            }else if(key.contentEquals("Cooling-off Period")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.COOLING_OFF_PERIOD, cellnum-1, "xls"));
	            }else if(key.contentEquals("Priority")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.PRIORITIES, cellnum-1, "xls"));
	            }else if(key.contentEquals("Travel Allowance")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.TRAVEL_ALLOWANCE, cellnum-1, "xls"));
	            }else if(key.contentEquals("Employment Type")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.EMPLOYMENT_TYPES, cellnum-1, "xls"));
	            }/*else if(key.contentEquals("Industry")) {
		            List<String> industryList = lookUpDataService.getAllSkillsByIndustryCategory();
	            	if(industryList != null && !industryList.isEmpty()) {
	            		String [] industry = industryList.toArray(new String[industryList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(industry, cellnum-1, "xls"));
	            	}
	            }*//*else if(key.contentEquals("Immigration Status")) {
	            	List<String> visaList = lookUpDataService.getAllVisaByCategory();
	            	if(visaList != null && !visaList.isEmpty()) {
	            		String [] visa = visaList.toArray(new String[visaList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(visa, cellnum-1, "xls"));
	            	}
	            } */
	            
	        }
	        try {
	            File file = new File("Job_Import_Template.xls");
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
	 @RequestMapping(value = "/downloadImmigrationTemplate", method = RequestMethod.GET)
		public void downloadImmigrationTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
			logger.debug("download Petition template is for Import downloadImmigrationTemplate method");
	        @SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Petition Template");
	        String[] tableColumns = ImmigrationConstants.IMPORT_DOWNLOAD_IMMIGRATION_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("Petition_Import_Template.xlsx");
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
	 @RequestMapping(value = "/downloadLcaTemplate", method = RequestMethod.GET)
		public void downloadLcaTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
			logger.debug("download LCA template is for Import downloadLcaTemplate method");
	        @SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("LCA Template");
	        String[] tableColumns = ImmigrationConstants.IMPORT_DOWNLOAD_LCA_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("LCA_Template.xlsx");
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
	 @RequestMapping(value = "/downloadDataImportTemplateForAsset", method = RequestMethod.GET)
		public void downloadDataImportTemplateForAsset(HttpServletRequest request,HttpServletResponse response) throws Exception{
			logger.debug("download Asset template is for Import downloadAssetDataImportTemplate method");
	        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

	        @SuppressWarnings("resource")
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("AssetTemplate");
	        String[] tableColumns = Constants.IMPORT_ASSET_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	            
	            if(key.equalsIgnoreCase("Category")) {
	            	List<String> categoryList = assetCategoryService.getAssetCategoryStringtByLocationId(orgLocation.getId());
	            	if(categoryList != null && !categoryList.isEmpty()) {
	            		String [] category = categoryList.toArray(new String[categoryList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(category, cellnum-1, "xls"));
	            	}
	            }else if(key.equalsIgnoreCase("Work Status")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.ASSET_WORKING_STATUS, cellnum-1, "xls"));
	            }
	        }
	        try {
	            File file = new File("Asset_Import_Template.xls");
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
	 
	 
	 @RequestMapping(value = "/downloadDataImportTemplateForProjectTasks", method = RequestMethod.GET)
		public void downloadDataImportTemplateForProjectTasks(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

	        @SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("ProjectTaskTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_PROJECT_TASKS_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	            
	            if(key.equalsIgnoreCase("Task Type*")) {
	            	List<String> taskTypeList = lookUpDataService.getListOfStringLookUpdataByCategoryAndOrgLocationId(Constants.TASKTYPE, orgLocation.getId());
	            	if(taskTypeList != null && !taskTypeList.isEmpty()) {
	            		String [] taskType = taskTypeList.toArray(new String[taskTypeList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(taskType, cellnum-1, "xls"));
	            	}
	            }else if(key.equalsIgnoreCase("Priority")){
	            	List<String> priorityList = lookUpDataService.getListOfStringLookUpdataByCategoryAndOrgLocationId(Constants.TASKPRIORITY, orgLocation.getId());
	            	if(priorityList != null && !priorityList.isEmpty()) {
	            		String [] priority = priorityList.toArray(new String[priorityList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(priority, cellnum-1, "xls"));
	            	}
	            }else if(key.equalsIgnoreCase("Severity")){
	            	List<String> severityList = lookUpDataService.getListOfStringLookUpdataByCategoryAndOrgLocationId(Constants.TASKSEVERITY, orgLocation.getId());
	            	if(severityList != null && !severityList.isEmpty()) {
	            		String [] severity = severityList.toArray(new String[severityList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(severity, cellnum-1, "xls"));
	            	}
	            }else if(key.equalsIgnoreCase("Status*")){
	            	List<String> statusList = lookUpDataService.getListOfStringLookUpdataByCategoryAndOrgLocationId(Constants.TASKSTATUS, orgLocation.getId());
	            	if(statusList != null && !statusList.isEmpty()) {
	            		String [] status = statusList.toArray(new String[statusList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(status, cellnum-1, "xls"));
	            	}
	            }else if(key.contentEquals("Currency Type")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.RATE_CURRENCY, cellnum-1, "xls"));
	            }else if(key.contentEquals("Billing Type*")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.BILLING_TYPE, cellnum-1, "xls"));
	            }
	            
	        }
	        try {
	            File file = new File("Project_Task_Import_Template.xls");
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
	 
	 @RequestMapping(value = "/downloadDataImportTemplateForAssociate", method = RequestMethod.GET)
		public void downloadDataImportTemplateForAssociate(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        @SuppressWarnings("resource")
	        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("AssociateTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_ASSOCIATE_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	            if(key.equalsIgnoreCase("Employment Type*")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.EMPLOYMENT_TYPES, cellnum-1, "xls"));
	            }else if(key.equalsIgnoreCase("Gender*")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.GENDER, cellnum-1, "xls"));
	            }else if(key.equalsIgnoreCase("Marital Status")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.MARITAL_STATUS, cellnum-1, "xls"));
	            }else if(key.startsWith("Role")) {
	            	List<String> rolesList = rolesService.getRolesListByOrgLocationId(orgLocation.getId());
	            	String [] associatesRoles = rolesList.toArray(new String[rolesList.size()]);
	            	sheet.addValidationData(getDropDownListForCell(associatesRoles, cellnum-1, "xls"));
	            }else if(key.startsWith("Shift")) {
	            	List<String> shiftList = shiftService.getAssociatesShiftListByOrgLocationId(orgLocation.getId());
	            	if(shiftList != null && !shiftList.isEmpty()) {
	            		String [] associateshiftS = shiftList.toArray(new String[shiftList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(associateshiftS, cellnum-1, "xls"));
	            	}
	            }else if(key.startsWith("Grade")) {
	            	List<String> gradesList = associateGradesService.getGradesListByOrgLocationId(orgLocation.getId());
	            	if(gradesList != null && !gradesList.isEmpty()) {
	            		String [] associateGrades = gradesList.toArray(new String[gradesList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(associateGrades, cellnum-1, "xls"));
	            	}
	            }else if(key.equalsIgnoreCase("Department*")) {
	            	List<String> deptList = departmentService.getDepartmentList(orgLocation.getId());
	            	if(deptList != null && !deptList.isEmpty()) {
	            		String [] departments = deptList.toArray(new String[deptList.size()]);
		            	sheet.addValidationData(getDropDownListForCell(departments, cellnum-1, "xls"));
	            	}
	            }else if(key.equalsIgnoreCase("Rate Per")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.RATEPER_LIST, cellnum-1, "xls"));
	            }else if(key.equalsIgnoreCase("Send Mail to User")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.YES_NO, cellnum-1, "xls"));
	            }else if(key.equalsIgnoreCase("Is Global Manager")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.YES_NO, cellnum-1, "xls"));
	            }else if(key.equalsIgnoreCase("Rate Currency")) {
	            	sheet.addValidationData(getDropDownListForCell(Constants.RATE_CURRENCY, cellnum-1, "xls"));
	            }
	            
	        }
	        try {
	            File file = new File("Associate_Import_Template.xls");
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
}
