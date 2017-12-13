package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;

@Service
public class AssetServiceImpl implements AssetService{
	
	@Autowired
	AssetDao assetDao;

	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public Asset saveAsset(Asset asset) throws Exception {
		asset.setUpdatedDate(commonUtil.currentDate());
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
	  			 StringBuilder shiftABuilder = new StringBuilder();
	  			 shiftAEmpName = shiftABuilder.append(shiftAEmpName).append(" ").append(asset.getShiftAEmployee().getUser().getLastName()).toString();
	  		   }
	  		   asset.setShiftAEmpName(shiftAEmpName);
		 }
		 
		 if(asset.getShiftBEmployee() != null && asset.getShiftBEmployee().getUser() != null){
			   String shiftBEmpName = "";
	  		   if(StringUtils.isNotEmpty(asset.getShiftBEmployee().getUser().getFirstName())){
	  			 shiftBEmpName = asset.getShiftBEmployee().getUser().getFirstName();
	  		   }
	  		   if(StringUtils.isNotEmpty(asset.getShiftBEmployee().getUser().getLastName()) && StringUtils.isNotBlank(asset.getShiftBEmployee().getUser().getLastName())){
	  			 StringBuilder shiftBBuilder = new StringBuilder();
	  		     shiftBEmpName=shiftBBuilder.append(shiftBEmpName).append(" ").append(asset.getShiftBEmployee().getUser().getLastName()).toString();
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
	public void saveAssetDetails(AssetDTO assetDTO,String assetId, User user, OrgLocations orgLocations) throws Exception {
		
		Asset asset ;
		if(assetId != null && !"".equals(assetId)){
			asset = assetDao.getAssetById(Long.parseLong(assetId));
		}else{
			asset = new Asset();
			asset.setCreatedBy(user.getUserCode());
			asset.setCreatedDate(new Date());
		}
		
		 if(StringUtils.isNotBlank(assetDTO.getSno())){
			 asset.setSlno(assetDTO.getSno());
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getMake())){
			 asset.setMake(assetDTO.getMake());
		 }
		 
		 else{
			 
			 asset.setMake(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getModel())){
			 asset.setModel(assetDTO.getModel());
		 }
		 else{
			 asset.setModel(null);
			 
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getName())){
			 asset.setName(assetDTO.getName());
		 }
		 
		 else{
			 asset.setName(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getMemory())){
			 asset.setMemory(assetDTO.getMemory());
		 }
		 else{
			 
			 asset.setMemory(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getCpu())){
			 asset.setCpu(assetDTO.getCpu());
		 }
		 else{
			 asset.setCpu(null);
		 }
		 if(StringUtils.isNotBlank(assetDTO.getHarddisk())){
			 asset.setHardDisk(assetDTO.getHarddisk());
		 }
		 
		 else{
			 asset.setHardDisk(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getMonitor())){
			 asset.setMonitor(assetDTO.getMonitor());
		 }
		 
		 else{
			 
			 asset.setMonitor(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getCategory())){
			 String[] category = assetDTO.getCategory().split(",");
			 AssetCategory assetCategory = new AssetCategory();
			 assetCategory.setId(Long.valueOf(category[1]));
			 asset.setAssetCategory(assetCategory);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getWorkingStatus())){
			 asset.setWorkingStatus(assetDTO.getWorkingStatus());
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getShiftAEmp())){
			 Employees shiftAEmp = new Employees();
			 shiftAEmp.setId(Long.parseLong(assetDTO.getShiftAEmp()));
			 asset.setShiftAEmployee(shiftAEmp);
		 }else{
			 asset.setShiftAEmployee(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getShiftBEmp())){
			 Employees shiftBEmp = new Employees();
			 shiftBEmp.setId(Long.parseLong(assetDTO.getShiftBEmp()));
			 asset.setShiftBEmployee(shiftBEmp);
		 }else{
			 asset.setShiftBEmployee(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getDescription())){
			 asset.setDescription(assetDTO.getDescription());
		 }
		 
		 else{
			 
			 asset.setDescription(null);
		 }
		 
		 if(assetDTO.getAttachmentUpload() != null){
			 FileInfo fileInfo = assetDTO.getAttachmentUpload().get(0);
			 asset.setFileUrl(fileInfo.getFileURL());
			 asset.setFileName(fileInfo.getFileName());
			 asset.setFileContentType(fileInfo.getContentType());
			 FileUploadUtil.moveFile(assetDTO.getStaticPath(), fileInfo, "assetDocuments");
		 }
		 
		 if(assetDTO.getUploadBtndskAUpload() != null){
			 FileInfo fileInfo1 = assetDTO.getUploadBtndskAUpload().get(0);
			 asset.setShiftAFileUrl(fileInfo1.getFileURL());
			 asset.setShiftAFileName(fileInfo1.getFileName());
			 asset.setShiftAFileContentType(fileInfo1.getContentType());
			 FileUploadUtil.moveFile(assetDTO.getStaticPath(), fileInfo1, "assetDocuments");
			 }
			 
			 if(assetDTO.getUploadBtndskBUpload()!= null){
			 FileInfo fileInfo2 = assetDTO.getUploadBtndskBUpload().get(0);
			 asset.setShiftBFileUrl(fileInfo2.getFileURL());
			 asset.setShiftBFileName(fileInfo2.getFileName());
			 asset.setShiftBFileContentType(fileInfo2.getContentType());
			 FileUploadUtil.moveFile(assetDTO.getStaticPath(), fileInfo2, "assetDocuments");
			 }
		    		 
		 asset.setOrgLocations(orgLocations);
		 asset.setUpdatedBy(user.getUserCode());
		 asset.setUpdatedDate(new Date());
		 asset.setStatus(Constants.RECORD_ACTIVE);
		 
		 assetDao.saveAsset(asset);		 
	}

}
