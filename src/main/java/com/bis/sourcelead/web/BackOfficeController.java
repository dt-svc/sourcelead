package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.BoPaymentTypes;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AmexFileUploadService;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.BoComericaTransService;
import com.bis.sourcelead.service.BoPaymentTypesService;
import com.bis.sourcelead.service.BoUploadedFilesService;
import com.bis.sourcelead.service.ChaseUploadFileService;
import com.bis.sourcelead.service.ComericaFileUploadService;

import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UploadFileService;

import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.validation.FileValidator;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Controller
public class BackOfficeController {
	
private static final Logger logger = LoggerFactory.getLogger(BackOfficeController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	FileValidator fileValidator;

	@Autowired
	AmexFileUploadService amexFileUploadService;
	
	@Autowired
	BoUploadedFilesService boUploadedFilesService;
	
	@Autowired
	ComericaFileUploadService comericaFileUploadService;
	
	@Autowired
	ChaseUploadFileService chaseUploadFileService;
	
	@Autowired
	BoPaymentTypesService boPaymentTypesService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	AssetCategoryService assetCategoryService;
	
	@RequestMapping(value = "/finance")
	public ModelAndView finance(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/finance");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		List<BoPaymentTypes> paymentTypesList =  boPaymentTypesService.getAllBoPaymentTypes();
		model.addObject("paymentTypesList", paymentTypesList);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/transactionsImports")
	public ModelAndView transactionsImports(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/transactionsImports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	@RequestMapping("/saveImports")
	public String saveImports(@ModelAttribute("amexUploadFile") AmexUploadFile amexUploadFile, BindingResult result,
			Model model, HttpServletRequest request) {
		
		String isFileCreated = null;
		
		if(amexUploadFile.getFile() != null){
			fileValidator.validateTransFile(amexUploadFile, result);
		}else{
			return "backOffice/transactionsImports";
		}
		if (result.hasErrors()) {
			return "backOffice/transactionsImports";
		}
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		try {
			if(amexUploadFile.getFinancialInstituteName().equalsIgnoreCase("American Exchange Credit Card")){
				isFileCreated = amexFileUploadService.uploadAmexFile(amexUploadFile.getFile(),user,amexUploadFile);
			}
			else if(amexUploadFile.getFinancialInstituteName().equalsIgnoreCase("Comerica Bank")){
				isFileCreated = comericaFileUploadService.uploadComericaFile(amexUploadFile.getFile(), user, amexUploadFile);
			}else{
				isFileCreated = chaseUploadFileService.uploadChaseFile(amexUploadFile.getFile(), user, amexUploadFile);
			}
			
			if ("FailureTransactionsCheck.xlsx".equalsIgnoreCase(isFileCreated)) {
				model.addAttribute("errorMessage", "Please download Error file here");
                model.addAttribute("fileName", "xlsx");
                 return "backOffice/transactionsImports";
			}else if("FailureTransactionsCheck.xls".equalsIgnoreCase(isFileCreated)){
				model.addAttribute("errorMessage", "Please download Error file here");
                model.addAttribute("fileName", "xls");
                return "backOffice/transactionsImports";
			}
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
		}
		
		try {
			boUploadedFilesService.addBoUploadedFiles(user, amexUploadFile, amexUploadFile.getFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("msg", "successfully uploaded");
		model.addAttribute("fileName", "");
		model.addAttribute("sourceleadUrl", sourceleadUrl);
		
		return "backOffice/finance";
	}
	
	
	@RequestMapping(value = "/assetManagement")
	public ModelAndView assetManagement(HttpServletRequest request) throws Exception {
	
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/assetManagement");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		List<AssetCategory> assetCategoriesList = assetCategoryService.getAssetCategoryListByLocationId(orgLocations.getId());
		model.addObject("assetCategoriesList", assetCategoriesList);
		
		List<Employees> employeesList = employeesService.getEmployeesByLocationId(orgLocations.getId());
		model.addObject("employeesList", employeesList);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.AssetManagement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	  @RequestMapping(value = "/getAssetCategories")
	    public String getAssetCategories(HttpServletRequest request) throws Exception {
	    	
	    	String resultJson = null;
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<AssetCategory> categoryList = assetCategoryService.getAssetCategoryListByLocationId(orgLocations.getId());
	    	resultJson = toJSON(categoryList);
			return resultJson;
			
		}
	  
	  @RequestMapping(value = "/getAssetEmployeesList")
	    public String getAssetEmployeesList(HttpServletRequest request) throws Exception {
	    	
	    	String resultJson = null;
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    	List<Employees> employeesList = employeesService.getEmployeesByLocationId(orgLocations.getId());
	    	resultJson = toJSON(employeesList);
			return resultJson;
			
		}
	    
	
	

	@RequestMapping(value = "/assetCategory")
	public ModelAndView assetCategory(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/assetCategory");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.AssetManagement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/downloadFailedTransactions/{fileName}", method = RequestMethod.GET)
	public void downloadFailedTransactions(@PathVariable String fileName,HttpServletResponse response) throws IOException {
		
			File file;
			if("xlsx".equalsIgnoreCase(fileName)){
				file = new File("FailureTransactionsCheck.xlsx");
			}else{
				 file = new File("FailureTransactionsCheck.xls");
			}
			
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
	}
	
	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }

}
