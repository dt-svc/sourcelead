package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
@RequestMapping("/app")
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
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("JobTemplate");
	        String[] tableColumns = Constants.IMPORT_JOB_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("Job_Import_Template.xlsx");
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
			logger.debug("download Immigration template is for Import downloadImmigrationTemplate method");
	        @SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Immigration Template");
	        String[] tableColumns = ImmigrationConstants.IMPORT_DOWNLOAD_IMMIGRATION_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("Immigration_Import_Template.xlsx");
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
	        @SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("AssetTemplate");
	        String[] tableColumns = Constants.IMPORT_ASSET_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("Asset_Import_Template.xlsx");
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
	        @SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("ProjectTaskTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_PROJECT_TASKS_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("Project_Task_Import_Template.xlsx");
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
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("AssociateTemplate");
	        String[] tableColumns = Constants.DATA_MIGRATION_ASSOCIATE_EXCEL_HEADERS;
	 
	        Row row = sheet.createRow(0);
	        int cellnum = 0;
	        for (String key : tableColumns) {
	            Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)key);
	        }
	        try {
	            File file = new File("Associate_Import_Template.xlsx");
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
	 
	 
}
