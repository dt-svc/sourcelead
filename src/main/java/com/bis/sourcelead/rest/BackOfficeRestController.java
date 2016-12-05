package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.entity.BoJmpcTrans;
import com.bis.sourcelead.entity.BoUploadedFiles;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.BoAmexTransService;
import com.bis.sourcelead.service.BoComericaTransService;
import com.bis.sourcelead.service.BoJmpcTransService;
import com.bis.sourcelead.service.BoUploadedFilesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class BackOfficeRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BackOfficeRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
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
	UserService userService;
	
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
	 * @param fileType
	 * @param assetId
	 * @param response
	 * @throws Exception
	 * To download attached files
	 */
	@RequestMapping(value = "/downloadAssetFile", method = RequestMethod.GET)
    public void downloadAssetFile(@RequestParam(value="fileType", required=false) String fileType ,@RequestParam(value="assetId", required=false) Long assetId ,HttpServletResponse response) throws Exception {
		
		     Asset asset = assetService.getAssetById(assetId);
		    
		     InputStream inputStream;
		     if(null != fileType && "shiftAfile".equals(fileType)){
		    	 response.setHeader("content-disposition", "attachment; filename=\"" + asset.getShiftAFileName() + "\"");
				 inputStream = new ByteArrayInputStream(asset.getShiftADocFile()); 
		     }else if(null != fileType && "shiftBfile".equals(fileType)){
		    	 response.setHeader("content-disposition", "attachment; filename=\"" + asset.getShiftBFileName() + "\"");
				 inputStream = new ByteArrayInputStream(asset.getShiftBDocFile()); 
		     }else{
		    	 response.setHeader("content-disposition", "attachment; filename=\"" + asset.getFileName() + "\"");
				 inputStream = new ByteArrayInputStream(asset.getDocFile()); 
		     }
		     
	        
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
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
	 @RequestMapping(value = "/removeAssetManagement", method = RequestMethod.POST)
	 @ResponseBody String removeAssetManagement(@RequestParam(value = "assetId", required = false) Long assetId) throws Exception {
	       Asset asset = assetService.getAssetById(assetId);
           if(asset != null){
				asset.setStatus(Constants.RECORD_INACTIVE);
				assetService.saveAsset(asset);
			}
			return "success";
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
	public String getAssetById(HttpServletRequest request,@RequestParam(value="assetId", required=false) Long assetId) throws Exception {
		String resultJson = null;
		Asset asset = assetService.getAssetById(assetId);
		resultJson = toJSON(asset);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getAssetCategoryList")
	@ResponseBody
	public String getAssetCategoryList(HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		
		List<AssetCategory> assetCategoryList = assetCategoryService.getAssetCategoryListByLocationId(orgLocations.getId());
		for(AssetCategory assetCategory : assetCategoryList){
			User user = userService.getUserByUserCode(assetCategory.getCreatedBy());
			assetCategory.setCreatedUser(user.getFirstName()+" "+user.getLastName());
			
			 if(assetCategory.getUpdatedDate() != null){
				 assetCategory.setDateStr(Constants.DF_DMY.format(assetCategory.getUpdatedDate()));
			    }
		}
		
		resultJson = toJSON(assetCategoryList);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/addCategoryAssets" , method = RequestMethod.POST)
	   public String addCategoryAssets(@RequestParam(value="json", required=false) String json,MultipartHttpServletRequest request) throws Exception {
			
		    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			assetService.saveAssetDetails(jsonObj, user, orgLocations, request);
			
		
		   return "home";
	    
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
