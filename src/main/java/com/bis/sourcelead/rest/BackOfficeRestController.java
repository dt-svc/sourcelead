package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dto.AssetCategoryDTO;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.AssetServiceDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.AssetServices;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.entity.BoJmpcTrans;
import com.bis.sourcelead.entity.BoUploadedFiles;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetCustomFieldService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.BoAmexTransService;
import com.bis.sourcelead.service.BoComericaTransService;
import com.bis.sourcelead.service.BoJmpcTransService;
import com.bis.sourcelead.service.BoUploadedFilesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class BackOfficeRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BackOfficeRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	BoAmexTransService boAmexTransService;
	
	@Autowired
	BoComericaTransService bBoComericaTransService;
	
	@Autowired
	BoJmpcTransService boJmpcTransService;
	
	@Autowired
	BoUploadedFilesService boUploadedFilesService;
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	AssetCategoryService assetCategoryService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private AssetCustomFieldService assetCustomFieldService;
	
	@Autowired
	DynamicReport dynamicReport;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@RequestMapping(value = "/getAccountsList")
	@ResponseBody
	public String getAccountsList(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		
		
		
		List<BoAmexTrans> boAmexTransList = boAmexTransService.getBoAmexTransByFilters(jsonObj);
		Float boamexCreditAmount = 0f; 
		Float boamexDebitAmount = 0f;
		Float boamexBalanceAmount = 0f;
		BoAmexTrans boamexTrans = new BoAmexTrans(); 
		for(BoAmexTrans amexTrans : boAmexTransList){
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("CREDIT")){
				boamexCreditAmount = boamexCreditAmount+amexTrans.getAmount();
			}
			
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("DEBIT")){
				boamexDebitAmount = boamexDebitAmount+amexTrans.getAmount();
			}
		}
		
		boamexTrans.setFinancialInstituteName("American Exchange Credit Card");
		boamexTrans.setDebitAmount(boamexDebitAmount);
		boamexTrans.setCreditAmount(boamexCreditAmount);
		if(boamexDebitAmount < 0){
			boamexBalanceAmount = boamexCreditAmount + (boamexDebitAmount);
		}else{
			boamexBalanceAmount = boamexCreditAmount - boamexDebitAmount;
		}
		
		boamexTrans.setBalanceAmount(boamexBalanceAmount);
		boamexTrans.setIdentity("amex");
		
		Float commericaCreditAmount = 0f; 
		Float commericaDebitAmount = 0f;
		Float commericabalanceAmount = 0f;
		BoComericaTrans boComericaTrans = new BoComericaTrans(); 		
		List<BoComericaTrans> BboComericaTransList = bBoComericaTransService.getBoComericaTransByFilters(jsonObj);
		
		for(BoComericaTrans comericaTrans : BboComericaTransList){
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("CREDIT")){
				commericaCreditAmount = commericaCreditAmount+comericaTrans.getAmount();
			}
			
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("DEBIT")){
				commericaDebitAmount = commericaDebitAmount+comericaTrans.getAmount();
			}
		}
		
		boComericaTrans.setFinancialInstituteName("Comerica Bank");
		boComericaTrans.setDebitAmount(commericaDebitAmount);
		boComericaTrans.setCreditAmount(commericaCreditAmount);
		if(commericaDebitAmount < 0){
			commericabalanceAmount = commericaCreditAmount + (commericaDebitAmount);
		}else{
			commericabalanceAmount = commericaCreditAmount - commericaDebitAmount;
		}
		
		boComericaTrans.setBalanceAmount(commericabalanceAmount);
		boComericaTrans.setIdentity("comerica");
		
		Float jmpcCreditAmount = 0f; 
		Float jmpcDebitAmount = 0f;
		Float jmpcBalanceAmount = 0f;
		BoJmpcTrans boJmpcTrans = new BoJmpcTrans();
		List<BoJmpcTrans> boJmpcTransList = boJmpcTransService.getBoJmpcTransByFilters(jsonObj);
		
		for(BoJmpcTrans jmpcTrans : boJmpcTransList){
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("CREDIT")){
				jmpcCreditAmount = jmpcCreditAmount+jmpcTrans.getAmount();
			}
			
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("DEBIT")){
				jmpcDebitAmount = jmpcDebitAmount+jmpcTrans.getAmount();
			}
		}
		
		boJmpcTrans.setFinancialInstituteName("Chase Bank");
		boJmpcTrans.setDebitAmount(jmpcDebitAmount);
		boJmpcTrans.setCreditAmount(jmpcCreditAmount);
		if(jmpcDebitAmount < 0){
			jmpcBalanceAmount = jmpcCreditAmount + (jmpcDebitAmount);
		}else{
			jmpcBalanceAmount = jmpcCreditAmount - jmpcDebitAmount;
		}
		
		boJmpcTrans.setBalanceAmount(jmpcBalanceAmount);
		boJmpcTrans.setIdentity("jmpc");
		
		List<Object> finalcialInstituesList = new ArrayList<Object>();
		
		finalcialInstituesList.addAll(Arrays.asList(boamexTrans));
		finalcialInstituesList.addAll(Arrays.asList(boComericaTrans));
		finalcialInstituesList.addAll(Arrays.asList(boJmpcTrans));
		
		resultJson = toJSON(finalcialInstituesList);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getAmexTransactions")
	@ResponseBody
	public String getAmexTransactions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		List<BoAmexTrans> boAmexTransList = boAmexTransService.getBoAmexTrans();
		Float boamexCreditAmount = 0f; 
		Float boamexDebitAmount = 0f;
		Float boamexBalanceAmount = 0f;
		BoAmexTrans boamexTrans = new BoAmexTrans(); 
		for(BoAmexTrans amexTrans : boAmexTransList){
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("CREDIT")){
				boamexCreditAmount = boamexCreditAmount+amexTrans.getAmount();
			}
			
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("DEBIT")){
				boamexDebitAmount = boamexDebitAmount+amexTrans.getAmount();
			}
		}
		
		boamexTrans.setDebitAmount(boamexDebitAmount);
		boamexTrans.setCreditAmount(boamexCreditAmount);
		boamexBalanceAmount = boamexCreditAmount - boamexDebitAmount;
		boamexTrans.setBalanceAmount(boamexBalanceAmount);
		boamexTrans.setIdentity("amex");
		
		
		resultJson = toJSON(boamexTrans);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/getComericaTransactions")
	@ResponseBody
	public String getComericaTransactions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		Float commericaCreditAmount = 0f; 
		Float commericaDebitAmount = 0f;
		Float commericabalanceAmount = 0f;
		BoComericaTrans boComericaTrans = new BoComericaTrans(); 		
		List<BoComericaTrans> BboComericaTransList = bBoComericaTransService.getBoComericaTrans();
		
		for(BoComericaTrans comericaTrans : BboComericaTransList){
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("CREDIT")){
				commericaCreditAmount = commericaCreditAmount+comericaTrans.getAmount();
			}
			
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("DEBIT")){
				commericaDebitAmount = commericaDebitAmount+comericaTrans.getAmount();
			}
		}
		
		boComericaTrans.setDebitAmount(commericaDebitAmount);
		boComericaTrans.setCreditAmount(commericaCreditAmount);
		commericabalanceAmount = commericaCreditAmount - commericaDebitAmount;
		boComericaTrans.setBalanceAmount(commericabalanceAmount);
		boComericaTrans.setIdentity("comerica");
		
		resultJson = toJSON(boComericaTrans);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getChaseTransactions")
	@ResponseBody
	public String getChaseTransactions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		Float jmpcCreditAmount = 0f; 
		Float jmpcDebitAmount = 0f;
		Float jmpcBalanceAmount = 0f;
		BoJmpcTrans boJmpcTrans = new BoJmpcTrans();
		List<BoJmpcTrans> boJmpcTransList = boJmpcTransService.getBoJmpcTrans();
		
		for(BoJmpcTrans jmpcTrans : boJmpcTransList){
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("CREDIT")){
				jmpcCreditAmount = jmpcCreditAmount+jmpcTrans.getAmount();
			}
			
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("DEBIT")){
				jmpcDebitAmount = jmpcDebitAmount+jmpcTrans.getAmount();
			}
		}
		
		boJmpcTrans.setDebitAmount(jmpcDebitAmount);
		boJmpcTrans.setCreditAmount(jmpcCreditAmount);
		jmpcBalanceAmount = jmpcCreditAmount - jmpcDebitAmount;
		boJmpcTrans.setBalanceAmount(jmpcBalanceAmount);
		boJmpcTrans.setIdentity("jmpc");
		
		resultJson = toJSON(boJmpcTrans);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/validateTransDates",method = RequestMethod.POST)
	@ResponseBody
	public String validateTransDates(HttpServletRequest request,
			@RequestParam(value="fromDate", required=false) String fromDate,
			@RequestParam(value="toDate", required=false) String toDate,
			@RequestParam(value="financialInstituteName", required=false) String financialInstituteName) throws Exception {
		
		String status = null;
		
		List<BoUploadedFiles> filesList = boUploadedFilesService.getBoUploadedFilesByFinancialInstitueName(financialInstituteName);
		if(filesList != null){
			for(BoUploadedFiles boUploadedFiles : filesList){
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date transFromDate = sdf.parse(fromDate);
				Date transToDate = sdf.parse(toDate);
				
				if(transFromDate.equals(boUploadedFiles.getFromDate()) && 
						transFromDate.before(boUploadedFiles.getToDate()) && 
						transToDate.equals(boUploadedFiles.getToDate()) &&
						transToDate.before(boUploadedFiles.getToDate())){
					
					status = "Invalid";
					
				}else{
					
					status = "valid";
				}
				
			}
		}
		
		return status;
	}
	
	
	
	
	/**
	 * @author Prasad Salina
	 * @param request
	 * @param categoryIds
	 * @param workingStatus
	 * @return
	 * @throws Exception
	 * To get the Asset Management List by using filters
	 */
	@RequestMapping(value = "/getAssetList")
	@ResponseBody
	public String getAssetList(HttpServletRequest request,@RequestParam(value="categoryIds", required=false) String categoryIds,@RequestParam(value="workingStatus", required=false) String workingStatus) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		
		List<Asset> assetList = assetService.getAssetFilterListByLocationId(orgLocations.getId(),categoryIds,workingStatus);
		ArrayList<Asset> assetsList = new ArrayList<Asset>();
		for(Asset asset : assetList){
			asset.setCategoryName(asset.getAssetCategory().getCategoryName());
			assetsList.add(asset);
			
		}
		resultJson = toJSON(assetsList);
		return resultJson;
	}
	
	
	/**
	 * @author Laxman
	 * @param assetDTO
	 * @param request
	 * @return assetList
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getAssetDynamicList")
	@ResponseBody
	public ResponseList<Asset> getAssetDynamicList(AssetDTO assetDTO,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
  		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<Asset> responseList = new ResponseList<>();
		//System.out.println(" inside ATS getAssetDynamicList ");
		try {
			
			if (user != null && orgLocations != null) {
				
				String[] tableColumns = {"id","slno", "assetCategory", "model", "issueDate","macAddress","workingStatus" ,""};
				
				int sortColumnIndex = 8; //by default sorting using id
				boolean sortColumnOrder = false; //by default descending order
				if(assetDTO.getOrder() != null && !assetDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(assetDTO.getOrder().get(0).get("column"));
					sortColumnOrder = !assetDTO.getOrder().get(0).get("dir").equalsIgnoreCase("desc");					
				}
		   /* int sortColumnIndex = 1; //by default sorting using updated date
			String sortColumnOrder = "desc"; //by default referContactsDTO order
			if(assetDTO.getOrder() != null && !assetDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(assetDTO.getOrder().get(0).get("column"));
				sortColumnOrder = assetDTO.getOrder().get(0).get("dir");
			}*/
				assetDTO.setSortColumn(tableColumns[sortColumnIndex]);
				assetDTO.setSortOrder(sortColumnOrder ? "asc" : "desc");
			responseList = assetService.getDynamicAssetFilterListByLocationId(orgLocations.getId(), assetDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				
			responseList.setResponse(Result.SESSION_TIMEDOUT.name());
			responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}
	
	/**
	 * @author Laxman
	 * @param assetDTO
	 * @param request
	 * @return responseList
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAssignAssetDynamicList")
	@ResponseBody
	public ResponseList<AssetAssign> getAssignAssetDynamicList(AssetDTO assetDTO,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
  		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<AssetAssign> responseList = new ResponseList<>();
		try {
			
			if (user != null && orgLocations != null) {
				
			String[] tableColumns = {"employeeId","firstName", "name", "slno", "assignedOn", "returnedOn", "shift", "assetCategory"};
		    int sortColumnIndex = 1; //by default sorting using updated date
			String sortColumnOrder = "asc"; //by default referContactsDTO order
			if(assetDTO.getOrder() != null && !assetDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(assetDTO.getOrder().get(0).get("column"));
				sortColumnOrder = assetDTO.getOrder().get(0).get("dir");
			}
			assetDTO.setSortColumn(tableColumns[sortColumnIndex]);
			assetDTO.setSortOrder(sortColumnOrder);
			responseList = assetService.getDynamicAssignAssetFilterListByLocationId(orgLocations.getId(), assetDTO);
			for(AssetAssign aa:responseList.getData()) {
				Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(aa.getUserRole().getUser().getId(),orgLocations.getId());
				if(emp != null && emp.getEmployeeId() != null) {
					aa.setEmployeeId(emp.getEmployeeId());
				}
				aa.setEmployeeName(aa.getUserRole().getUser().getFullName());
				if(aa.getAsset().getName() != null) {
					aa.setAssetName(aa.getAsset().getName());
				}
				aa.setAssetUUID(aa.getAsset().getAssetUUID());
				aa.setSerialNumber(aa.getAsset().getSlno());
				if(aa.getAssignedOn() != null) {
					aa.setAssignedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), aa.getAssignedOn()));
				}
				if(aa.getReturnedOn() != null) {
					aa.setReturnedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), aa.getReturnedOn()));
				}
				aa.setCategoryName(aa.getAsset().getAssetCategory().getCategoryName());
				aa.setShiftName(aa.getShift().getShiftName());
			 }
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				
			responseList.setResponse(Result.SESSION_TIMEDOUT.name());
			responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}
	
	
	/**
	 * @author Prasad Salina
	 * @param assetId
	 * @param workingStatus
	 * @return
	 * @throws Exception
	 * To update working status of Asset Management
	 */
	 @RequestMapping(value = "/updateAssetWorkingStatus", method = RequestMethod.POST)
	 @ResponseBody String updateAssetWorkingStatus(@RequestParam(value = "assetId", required = false) Long assetId,@RequestParam(value="workingStatus", required=false) String workingStatus) throws Exception {
	       Asset asset = assetService.getAssetById(assetId);
           if(asset != null){
				asset.setWorkingStatus(workingStatus);
				assetService.saveAsset(asset);
			}
			return "success";
	 }
	 
	 /**
	  * @author Prasad Salina
	  * @param assetId
	  * @return
	  * @throws Exception
	  * To remove Asset Management
	  */
	 @RequestMapping(value = "/removeAssocateAssingAssetManagement", method = RequestMethod.POST)
	 @ResponseBody  Response<String> removeAssocateAssingAssetManagement(@RequestParam(value = "assignAssetId", required = false) Long assignAssetId,HttpServletRequest request) throws Exception {
		 
		    Response<String> response = new Response<>();
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			try {
				if (loginUser != null) {
					AssetAssign aa =assetService.getAssignAssetByAssetId(assignAssetId);
					if(aa != null){
						assetService.deleteAssignAssetByAssignId(aa);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());	
				}
				}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
	 }
	
	 
	 @RequestMapping(value = "/removeAssetManagement", method = RequestMethod.POST)
	 @ResponseBody  Response<String> removeAssetManagement(@RequestParam(value = "assetUUID", required = false) String assetUUID,HttpServletRequest request) throws Exception {
		 
		 Response<String> response = new Response<>();
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			try {
				if (loginUser != null) {
					Asset asset = assetService.getAssetByUUID(assetUUID);
					
					if(asset != null){
						asset.setStatus(Constants.RECORD_INACTIVE);
						assetService.saveAsset(asset);
						
						List<AssetAssign> assetList =  assetService.getAllAssignAssetListByAssetId(asset.getId());
						for(AssetAssign aa :assetList) {
						  assetService.deleteAssignAssetByAssignId(aa);
						}
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());	
				}
				}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
	 }
	 
	/**
	 * @author Prasad Salina
	 * @param request
	 * @param assetId
	 * @return
	 * @throws Exception
	 * To get asset by Id
	 */
	@RequestMapping(value = "/getAssetById")
	@ResponseBody
	public AssetDTO getAssetById(HttpServletRequest request,@RequestParam(value="assetUUID", required=false) String assetUUID) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		//String resultJson = null;
		Asset asset = assetService.getAssetByUUID(assetUUID);
		List<Employees> employeesList = employeesService.getEmployeesByLocationId(orgLocations.getId());
		AssetDTO assetDTO = new AssetDTO();
		
		if(asset.getAssetCategory() != null){
			assetDTO.setCategoryName(asset.getAssetCategory().getCategoryName());	
			assetDTO.setCategoryId(asset.getAssetCategory().getId().toString());
			assetDTO.setCatagoryHiddenId(asset.getAssetCategory().getId());
		}
		assetDTO.setMacAddress(asset.getMacAddress());
		assetDTO.setSno(asset.getSlno());
		assetDTO.setMake(asset.getMake());
		assetDTO.setMonitor(asset.getMonitor());
		assetDTO.setCpu(asset.getCpu());
		assetDTO.setWorkingStatus(asset.getWorkingStatus());
		assetDTO.setModel(asset.getModel());
		assetDTO.setMemory(asset.getMemory());
		assetDTO.setHarddisk(asset.getHardDisk());
		assetDTO.setFileName(asset.getFileName());
		assetDTO.setFileUrl(asset.getFileUrl());
		assetDTO.setFileContentType(asset.getFileContentType());
		
		assetDTO.setOperatingSystem(asset.getOperatingSystem());
		assetDTO.setOsKey(asset.getOsKey());
		assetDTO.setAntiVirus(asset.getAntiVirus());
		if(asset.getPurchaseDate() != null) {
			assetDTO.setPurchaseDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),asset.getPurchaseDate()));
		}
		if(asset.getDepreciationDate() != null) {
			assetDTO.setDepreciationDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),asset.getDepreciationDate()));
		}
		assetDTO.setName(asset.getName());
		assetDTO.setCost(asset.getCost());
		assetDTO.setDepreciationAmount(asset.getDepreciationAmount());
		assetDTO.setVendorDetails(asset.getVendorDetails());
		
		
		List<AssetAssign> assetAssignList = assetService.getAssignAssetListByAssetId(asset.getId());
		for(AssetAssign aa:assetAssignList) {
			aa.setAssignedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),aa.getAssignedOn()));
			aa.setReturnedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),aa.getReturnedOn()));
		}
		assetDTO.setAssetAssignList(assetAssignList);
		
		assetDTO.setName(asset.getName());
		assetDTO.setDescription(asset.getDescription());
		if(asset.getIssueDate() != null){
		assetDTO.setIssueDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),asset.getIssueDate()));
		}else{
			assetDTO.setIssueDate("");
		}
		 return assetDTO;
	}
	
	
	
	@RequestMapping(value = "/getAssetCategoryList")
	@ResponseBody
	public String getAssetCategoryList(HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		
		List<AssetCategory> assetCategoryList = assetCategoryService.getAssetCategoryListByLocationId(orgLocations.getId());
		for(AssetCategory assetCategory : assetCategoryList){
			StringBuilder name = new StringBuilder();
			User user = userService.getUserByUserCode(assetCategory.getCreatedBy());
			assetCategory.setCreatedUser(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			assetCategory.setOrgLocationId(assetCategory.getOrgLocations().getId());
			 if(assetCategory.getUpdatedDate() != null){
				 assetCategory.setDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),assetCategory.getUpdatedDate()));
			    }
		}
		
		resultJson = toJSON(assetCategoryList);
		return resultJson;
	}
	
	

	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	 @ResponseBody Response<String> saveCategory(AssetCategoryDTO assetCategoryDTO,
			 @RequestParam(value = "categoryId", required = false) String categoryId,
			 @RequestParam(value = "catLocationId", required = false) String catLocationId,
			HttpServletRequest request) throws Exception {
		
			Response<String> response = new Response<>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			try {
				if (user != null && orgLocation != null) {
	
					if (Validator.validate(assetCategoryDTO)) {
						assetCategoryService.addAssetCategory(assetCategoryDTO,orgLocation,user,categoryId,catLocationId);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
	
				} else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
	} 
	
	@RequestMapping(value = "/getCategoryById")
	 @ResponseBody String getCategoryById(@RequestParam(value = "categoryId", required = false) Long categoryId,
			 @RequestParam(value = "catLocationId", required = false) Long catLocationId,
			 HttpServletRequest request) throws Exception{
		 
	       AssetCategory assetCategory = assetCategoryService.getAssetCategoryListByLocationIdAndCatId(catLocationId,categoryId);
	         
		 return commonUtil.toJSON(assetCategory);
		}

	@RequestMapping(value = "/addCategoryAssets" , method = RequestMethod.POST)
	   public Response<String> addCategoryAssets(AssetDTO assetDTO,HttpServletRequest request) throws Exception {
			
		Response<String> response = new Response<>();
		Long orgLocationId = (long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (user != null && orgLocations != null) {
				if (Validator.validate(assetDTO)) {
					assetDTO.setStaticPath(staticPath);
					assetDTO.setDateFormat(orgLocations.getDateFormat());
					assetService.saveAssetDetails(assetDTO, user, orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}

			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response; 
	    
	   }
	
	@RequestMapping(value = "/validateAssetSno",  method = RequestMethod.POST)
    public @ResponseBody Boolean validateAssetSno(@RequestParam(value="assetSno", required=false) String assetSno, @RequestParam(value="assetId", required=false) Long assetId,
    		HttpServletRequest request) throws Exception {
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       Asset asset=null;
	       if(null != assetId){
	        asset = assetService.getAssetByAssetSnoAndId(assetSno, assetId, orgLocation.getId());
	       }
	       Boolean flag = false;
		    if(asset == null){
		    	flag =  assetService.validateAssetId(assetSno, orgLocation.getId());
		    	
		    }
			return flag;
		 
	    }
	
	@RequestMapping(value = "/validateCategoryName" , method = RequestMethod.GET)
	   public Boolean validateCategoryName(@RequestParam(value="categoryName", required=false) String categoryName,
			   @RequestParam(value="categoryId", required=false) String categoryId,
			   HttpServletRequest request) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    AssetCategory assetCategory = null ;
			if(!categoryId.isEmpty()){
				assetCategory = assetCategoryService.getAssetCategoryByCategoryIdAndLocationId(categoryName,Long.parseLong(categoryId),orgLocations.getId());
			}
			Boolean flag = false;
			if(assetCategory == null){
				 flag = assetCategoryService.validateCategoryName(categoryName,orgLocations.getId());	
			}
		   return flag;
	    
	   }
	
	@RequestMapping(value = "/terminateCategory", method = RequestMethod.GET)
	 @ResponseBody Response<String> terminateCategory(@RequestParam(value = "categoryId", required = false) String categoryId,
			HttpServletRequest request) throws Exception {
		
		Response<String> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		try {
			if (loginUser != null && orgLocation != null) {
			if(categoryId != null) {
				AssetCategory assetCategory = assetCategoryService.getAssetCategoryListByLocationIdAndCatId(orgLocation.getId(),Long.parseLong(categoryId));
				assetCategory.setId(Long.parseLong(categoryId));
				assetCategory.setStatus(Integer.parseInt(Constants.MEMBER_IN_ACTIVE));
				assetCategoryService.addAssetCategory(assetCategory);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());	
			}
			}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return response;
	} 
	
	
	@RequestMapping(value = "/deleteAssignAssetDoc", method = RequestMethod.POST)
	public String deleteAssignAssetDoc(@RequestParam(value="assetAssignId", required=false) Long assetAssignId,HttpServletRequest request) throws Exception {
		AssetAssign assetAssign = assetService.getAssignAssetByAssetId(assetAssignId);
		if(assetAssign != null) {
			assetAssign.setFileName(null);
			assetAssign.setFileType(null);
			assetAssign.setFileURL(null);
			assetService.saveAssetAssign(assetAssign);
		} 
		return "success";

	}
	
	
	@RequestMapping(value = "/deleteAssetDocument", method = RequestMethod.POST)
	public String deleteDoc(@RequestParam(value="assetId", required=false) String assetId,@RequestParam(value="url", required=false) String url,HttpServletRequest request) throws Exception {
	       Asset asset = assetService.getAssetById(Long.parseLong(assetId));
	       if(url.equals(asset.getFileUrl())){
	       asset.setFileUrl(null);
	       asset.setFileContentType(null);
	       asset.setFileName(null);
	       } 
	       assetDao.saveAsset(asset);	

		return "success";

	}
	
	
	 @RequestMapping(value = "/getReturnedOnAssetDetaisl", method = RequestMethod.POST)
	 @ResponseBody  List<AssetAssign> getReturnedOnAssetDetaisl(@RequestParam(value = "assetUUID", required = false) String assetUUID,HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Asset asset = assetService.getAssetByUUID(assetUUID);
		 List<AssetAssign> assignAssetList =  assetService.getReturnedOnAssignAssetListByAssetId(asset.getId());
		 for(AssetAssign aa :assignAssetList) {
				if(aa.getReturnedOn() != null) {
					aa.setReturnedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),aa.getReturnedOn()));
				}
				if(aa.getAssignedOn() != null) {
					aa.setAssignedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),aa.getAssignedOn()));
				}
			}
		 return assignAssetList;
	 }
	 

	 @RequestMapping(value = "/saveAssetService" , method = RequestMethod.POST)
	   public Response<String> saveAssetService(AssetServiceDTO assetServiceDTO,HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (user != null && orgLocations != null) {
				if (Validator.validate(assetServiceDTO)) {
					assetServiceDTO.setDateFormat(orgLocations.getDateFormat());
					assetService.saveAssetServiceDetails(assetServiceDTO,orgLocations);
					
					Asset asset = assetService.getAssetById(assetServiceDTO.getAssetId());
			        if(asset != null){
						asset.setWorkingStatus(assetServiceDTO.getWorkingStatus());
						assetService.saveAsset(asset);
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}

			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response; 
	    
	   }
	 
	 @RequestMapping(value = "/cancelAssetManagementService", method = RequestMethod.POST)
		public Asset cancelAssetManagementService(@RequestParam(value="assetUUID", required=false) String assetUUID,HttpServletRequest request) throws Exception {
		       Asset asset = assetService.getAssetByUUID(assetUUID);
			return asset;
		}
	 
	 
	 @RequestMapping(value = "/getAssetManagementServiceHistory", method = RequestMethod.POST)
	 @ResponseBody  List<AssetServices> getAssetManagementServiceHistory(@RequestParam(value = "assetUUID", required = false) String assetUUID,HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Asset asset = assetService.getAssetByUUID(assetUUID);
		 List<AssetServices> assetServicesList =  assetService.getAssetServicesListByAssetId(asset.getId());
		 for(AssetServices as :assetServicesList) {
				 as.setServiceDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),as.getServiceDate()));
				 as.setReceivedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),as.getRecivedDate()));
			}
		 return assetServicesList;
	 }
	 
	@RequestMapping(value = "/getAssetmanagementExport")
	@ResponseBody
	public void getAssetmanagementExport(AssetDTO assetDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    ResponseList<Asset>  responseList = assetService.getDynamicAssetFilterListByLocationId(orgLocations.getId(), assetDTO);
		    File file = null;
		    if(assetDTO.getExportType().equalsIgnoreCase("pdf")) {
		    	 file=  dynamicReport.assetmanagementPdfExport(responseList,orgLocations);
		    }else {
		    	 file=  dynamicReport.assetmanagementExcelExport(responseList,orgLocations);
		    }
		    
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
			if(file!= null){
				file.delete();
			}
		}
	 
	
	@RequestMapping(value = "/getAssignAssetmanagementExport")
	@ResponseBody
	public void getAssignAssetmanagementExport(AssetDTO assetDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    ResponseList<AssetAssign>  responseList = assetService.getDynamicAssignAssetFilterListByLocationId(orgLocations.getId(), assetDTO);
		    File file = null;
		    if(assetDTO.getExportType().equalsIgnoreCase("pdf")) {
		    	 file=  dynamicReport.assignAssetmanagementPdfExport(responseList,orgLocations);
		    }else {
		    	 file=  dynamicReport.assignAssetmanagementExcelExport(responseList,orgLocations);
		    }
		    
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
			if(file!= null){
				file.delete();
			}
		}
	 
	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
}
