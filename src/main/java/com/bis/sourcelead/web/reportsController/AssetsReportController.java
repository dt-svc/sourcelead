package com.bis.sourcelead.web.reportsController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping ("/app")

public class AssetsReportController {

	@Value("${sourceleadUrl}") 
	private String sourceleadUrl;
	
	@Autowired
	AssetCategoryService assetCategoryService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	ShiftService shiftService;
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PdfUtil pdfUtil;
	
	
	
@RequestMapping(value = "/assetsReport")
	
	public ModelAndView assetsReport(HttpServletRequest request)throws Exception{
		ModelAndView model = new ModelAndView();
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

			List<AssetCategory> assetCategoriesList = assetCategoryService.getAssetCategoryListByLocationId(orgLocation.getId());
			model.addObject("assetCategoriesList", assetCategoriesList);
			
			List<Employees> employeesList = employeesService.getEmployeesByLocationId(orgLocation.getId());
			model.addObject("employeesList", employeesList);
			
			List<Asset> assetList = assetService.getAllAssetByOrgId(orgLocation.getId());
			model.addObject("assetList", assetList);
			
			List<Shift> shiftList = shiftService.getShiftList(orgLocation.getId());
			model.addObject("shiftList", shiftList);
		 
		    model.addObject("sourceleadUrl", sourceleadUrl);
	     
		  model.setViewName("reports/assetsReport");
		 return model;
	}
	
	
@RequestMapping(value = "/getAssetReport" ,method = RequestMethod.POST)
public @ResponseBody ResponseList<AssetsReportDTO> getAssetReportList(AssetsReportDTO assetsReportDTO,HttpServletRequest request) throws Exception{
	 //User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 
	 DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		
		Date startTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),assetsReportDTO.getFromDate());
		Date endTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),assetsReportDTO.getToDate());
		String customStartTimeStr = TZ_DF_CSD.format(startTime);  
		String customEndTimeStr = TZ_DF_CED.format(endTime);  
		Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
		Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
	   
	    assetsReportDTO.setConvertedFromDate(startDateUTC);
	    assetsReportDTO.setConvertedToDate(endDateUTC);
	 
	ResponseList<AssetsReportDTO> assetReportList = assetService.getAssetReportList(assetsReportDTO,orgLocation.getId());
	
	return assetReportList; 
}
	
		

@ResponseBody
@RequestMapping(value = "/getAssetsReportExport")
public File assetsReportListForExport(HttpServletRequest request, HttpServletResponse response, AssetsReportDTO assetsReportDTO) throws Exception {
	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

	String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	File file = null;
	
	 DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		
		Date startTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),assetsReportDTO.getFromDate());
		Date endTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),assetsReportDTO.getToDate());
		String customStartTimeStr = TZ_DF_CSD.format(startTime);  
		String customEndTimeStr = TZ_DF_CED.format(endTime);  
		Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
		Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
	   
	    assetsReportDTO.setConvertedFromDate(startDateUTC);
	    assetsReportDTO.setConvertedToDate(endDateUTC);
	 
	ResponseList<AssetsReportDTO> assetReportList = assetService.getAssetReportList(assetsReportDTO,orgLocation.getId());
	
	
	if (assetReportList.getData() != null && !assetReportList.getData().isEmpty()) {

		if (assetsReportDTO.getType().equalsIgnoreCase("pdf")) {
			file = pdfUtil.getAssetsReportListOfPdf(assetReportList.getData(), user, orgLocation,isGlobalAccess);
		} else {
			file = assetService.getAssetsReportListOfExcel(assetReportList.getData(), user, orgLocation, isGlobalAccess);
		}
	}
	String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	if (mimeType == null) {
		mimeType = "application/octet-stream";
	}
	response.setContentType(mimeType);
	StringBuilder fileName = new StringBuilder();
	response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
	response.setContentLength((int) file.length());
	InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	FileCopyUtils.copy(inputStream, response.getOutputStream());
	inputStream.close();
	response.getOutputStream().close();
	response.getOutputStream().flush();
	if (file != null) {
		file.delete();
	}
	return file;

}
	
	
	
	
	
	
	
	
	
	
}
