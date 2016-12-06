package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService{
	
	@Autowired
	AssetDao assetDao;

	@Override
	public Asset saveAsset(Asset asset) throws Exception {
		return assetDao.saveAsset(asset);
	}
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	/**
	 * @author Prasad salina
	 * @param Id
	 * @return
	 * @throws Exception
	 * To get the asset by Id.
	 * This method used in BackOfficeRestController
	 */
	@Override
	public Asset getAssetById(Long Id) throws Exception {
		 Asset asset = assetDao.getAssetById(Id);
		 asset = getEmpNames(asset);
		 return asset;
	}

	/**
	 * 
	 * @param orgLocationId
	 * @param categoryIds
	 * @return
	 * @throws Exception
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	@Override
	public List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds, String workingStatus) throws Exception {
		List<Asset> assetsList =  assetDao.getAssetFilterListByLocationId(orgLocationId, categoryIds,workingStatus);
		if(assetsList != null && !assetsList.isEmpty()){
			for (Asset asset : assetsList) {
				asset = getEmpNames(asset);
			}
		}
		return assetsList;
	}
	
	/**
	 * @author Prasad Salina
	 * @param asset
	 * @return
	 * To set the Shift A and Shift B emp names
	 */
	private Asset getEmpNames(Asset asset) {
		if(asset.getShiftAEmployee() != null && asset.getShiftAEmployee().getUser() != null){
			   String shiftAEmpName = "";
	  		   if(StringUtils.isNotEmpty(asset.getShiftAEmployee().getUser().getFirstName())){
	  			 shiftAEmpName = asset.getShiftAEmployee().getUser().getFirstName();
	  		   }
	  		   if(StringUtils.isNotEmpty(asset.getShiftAEmployee().getUser().getLastName()) && StringUtils.isNotBlank(asset.getShiftAEmployee().getUser().getLastName())){
	  			 shiftAEmpName = shiftAEmpName+" "+asset.getShiftAEmployee().getUser().getLastName();
	  		   }
	  		   asset.setShiftAEmpName(shiftAEmpName);
		 }
		 
		 if(asset.getShiftBEmployee() != null && asset.getShiftBEmployee().getUser() != null){
			   String shiftBEmpName = "";
	  		   if(StringUtils.isNotEmpty(asset.getShiftBEmployee().getUser().getFirstName())){
	  			 shiftBEmpName = asset.getShiftBEmployee().getUser().getFirstName();
	  		   }
	  		   if(StringUtils.isNotEmpty(asset.getShiftBEmployee().getUser().getLastName()) && StringUtils.isNotBlank(asset.getShiftBEmployee().getUser().getLastName())){
	  			 shiftBEmpName = shiftBEmpName+" "+asset.getShiftBEmployee().getUser().getLastName();
	  		   }
	  		   asset.setShiftBEmpName(shiftBEmpName);
		 }
		return asset;
	}

	@Override
	public List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception {
		return assetDao.getAssetListByLocationId(orgLocationId);
	}
	
	
	@Override
	public void saveAssetDetails(JSONObject jsonObj, User user, OrgLocations orgLocations,
			MultipartHttpServletRequest request) throws Exception {
		
		Asset asset = new Asset();
		
		 if(jsonObj.has("sno") && !isNullOrEmpty(jsonObj.getString("sno"))){
			 asset.setSlno(jsonObj.getString("sno"));
		 }
		 
		 if(jsonObj.has("make") && !isNullOrEmpty(jsonObj.getString("make"))){
			 asset.setMake(jsonObj.getString("make"));
		 }
		 
		 if(jsonObj.has("model") && !isNullOrEmpty(jsonObj.getString("model"))){
			 asset.setModel(jsonObj.getString("model"));
		 }
		 
		 if(jsonObj.has("name") && !isNullOrEmpty(jsonObj.getString("name"))){
			 asset.setName(jsonObj.getString("name"));
		 }
		 
		 if(jsonObj.has("memory") && !isNullOrEmpty(jsonObj.getString("memory"))){
			 asset.setMemory(jsonObj.getString("memory"));
		 }
		 
		 if(jsonObj.has("cpu") && !isNullOrEmpty(jsonObj.getString("cpu"))){
			 asset.setCpu(jsonObj.getString("cpu"));
		 }
		 
		 if(jsonObj.has("harddisk") && !isNullOrEmpty(jsonObj.getString("harddisk"))){
			 asset.setHardDisk(jsonObj.getString("harddisk"));
		 }
		 
		 if(jsonObj.has("monitor") && !isNullOrEmpty(jsonObj.getString("monitor"))){
			 asset.setMonitor(jsonObj.getString("monitor"));
		 }
		 
		 if(jsonObj.has("category") && !isNullOrEmpty(jsonObj.getString("category"))){
			 String[] category = jsonObj.getString("category").split(",");
			 AssetCategory assetCategory = new AssetCategory();
			 assetCategory.setId(Long.valueOf(category[1]));
			 asset.setAssetCategory(assetCategory);
		 }
		 
		 if(jsonObj.has("workingStatus") && !isNullOrEmpty(jsonObj.getString("workingStatus"))){
			 asset.setWorkingStatus(jsonObj.getString("workingStatus"));
		 }
		 
		 if(jsonObj.has("shiftAEmp") && !isNullOrEmpty(jsonObj.getString("shiftAEmp"))){
			 Employees shiftAEmp = new Employees();
			 shiftAEmp.setId(Long.parseLong(jsonObj.getString("shiftAEmp")));
			 asset.setShiftAEmployee(shiftAEmp);
		 }
		 
		 if(jsonObj.has("shiftBEmp") && !isNullOrEmpty(jsonObj.getString("shiftBEmp"))){
			 Employees shiftBEmp = new Employees();
			 shiftBEmp.setId(Long.parseLong(jsonObj.getString("shiftBEmp")));
			 asset.setShiftBEmployee(shiftBEmp);
		 }
		 
		 if(jsonObj.has("description") && !isNullOrEmpty(jsonObj.getString("description"))){
			 asset.setDescription(jsonObj.getString("description"));
		 }
		 
		 
		    		 
		   MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
	       final Map files = multiRequest.getFileMap();
	       MultipartFile multiFile = (MultipartFile)files.get("attachment");
	        if(null != multiFile && null != multiFile.getContentType() && !isNullOrEmpty(multiFile.getOriginalFilename())){
		     	  try {
		     		 asset.setFileContentType(multiFile.getContentType());
		     		 asset.setDocFile(multiFile.getBytes());
		     		 asset.setFileName(multiFile.getOriginalFilename());
		     	   
		 	       }catch (Exception e) {
		     	      	 e.printStackTrace();
		 			    }
		         }
	       
	       
	        MultipartFile multiFile1 = (MultipartFile)files.get("uploadBtn-dskA");
	       if(null != multiFile1 && null != multiFile1.getContentType() && !isNullOrEmpty(multiFile1.getOriginalFilename())){
		     	  try {
		     		 asset.setShiftAFileContentType(multiFile1.getContentType());
		     		 asset.setShiftADocFile(multiFile1.getBytes());
		     		 asset.setShiftAFileName(multiFile1.getOriginalFilename());
		     	   
		 	       }catch (Exception e) {
		     	      	 e.printStackTrace();
		 			    }
		         }
	       
	        
	        MultipartFile multiFile2 = (MultipartFile)files.get("uploadBtn-dskB");
		       if(null != multiFile2 && null != multiFile2.getContentType() && !isNullOrEmpty(multiFile2.getOriginalFilename())){
			     	  try {
			     		 asset.setShiftBFileContentType(multiFile2.getContentType());
			     		 asset.setShiftBDocFile(multiFile2.getBytes());
			     		 asset.setShiftBFileName(multiFile2.getOriginalFilename());
			     	   
			 	       }catch (Exception e) {
			     	      	 e.printStackTrace();
			 			    }
			         }
	       
		 
		 asset.setOrgLocations(orgLocations);
		 asset.setCreatedBy(user.getUserCode());
		 asset.setCreatedDate(new Date());
		 asset.setUpdatedBy(user.getUserCode());
		 asset.setUpdatedDate(new Date());
		 asset.setStatus(1);
		 
		 assetDao.saveAsset(asset);		 
	}

}
