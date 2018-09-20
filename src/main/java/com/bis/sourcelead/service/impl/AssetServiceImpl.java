package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dto.AssetAssignDTO;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.AssetCustomFieldData;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserCustomFieldData;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetCustomFieldService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssetServiceImpl implements AssetService{
	
	@Autowired
	AssetDao assetDao;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private AssetCustomFieldService assetCustomFieldService;
	
	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
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
		 return asset;
	}
	
	@Override
	public Asset getAssetByUUID(String assetUUID) throws Exception {
		 Asset asset = assetDao.getAssetByUUID(assetUUID);
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
		return assetsList;
	}

	@Override
	public List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception {
		return assetDao.getAssetListByLocationId(orgLocationId);
	}
	
	
	@Override
	public Asset saveAssetDetails(AssetDTO assetDTO, User user, OrgLocations orgLocations) throws Exception {
		
		Asset asset ;
		if(assetDTO.getAssetId() != null && !"".equals(assetDTO.getAssetId())){
			asset = assetDao.getAssetById(Long.parseLong(assetDTO.getAssetId()));
		}else{
			asset = new Asset();
			asset.setCreatedBy(user.getUserCode());
			asset.setCreatedDate(new Date());
			asset.setAssetUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		//Laptop Serial Number
		 if(StringUtils.isNotBlank(assetDTO.getSno())){
			 asset.setSlno(assetDTO.getSno());
		 }
		 
		 //CPU Serial Number
		 if(StringUtils.isNotBlank(assetDTO.getCpuSerialNumber())){
			 asset.setSlno(assetDTO.getCpuSerialNumber());
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getMake())){
			 asset.setMake(assetDTO.getMake());
		 }else{
			 asset.setMake(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getModel())){
			 asset.setModel(assetDTO.getModel());
		 } else{
			 asset.setModel(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getName())){
			 asset.setName(assetDTO.getName());
		 } else{
			 asset.setName(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getMemory())){
			 asset.setMemory(assetDTO.getMemory());
		 } else{
			 asset.setMemory(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getCpu())){
			 asset.setCpu(assetDTO.getCpu());
		 } else{
			 asset.setCpu(null);
		 }
		 if(StringUtils.isNotBlank(assetDTO.getHarddisk())){
			 asset.setHardDisk(assetDTO.getHarddisk());
		 } else{
			 asset.setHardDisk(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getMacAddress())){
			 asset.setMacAddress(assetDTO.getMacAddress());
		 } 
		 
		 if(StringUtils.isNotBlank(assetDTO.getMonitor())){
			 asset.setMonitor(assetDTO.getMonitor());
		 } else{
			 asset.setMonitor(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getIssueDate())){
			 try {
				 asset.setIssueDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), assetDTO.getIssueDate()));
				  
				} catch (Exception e) {
				  e.printStackTrace();
				}
		 }
		 
		 //Purchase Date
		 if(StringUtils.isNotBlank(assetDTO.getPurchaseDate())){
			 asset.setPurchaseDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), assetDTO.getPurchaseDate()));
		 }else {
			 asset.setPurchaseDate(null);
		 }
		 
		 //Name
		 if(StringUtils.isNotBlank(assetDTO.getName())){
			 asset.setName(assetDTO.getName());
		 }else {
			 asset.setName(null);
		 }
		 
		 //Depreciation Date
		 if(StringUtils.isNotBlank(assetDTO.getDepreciationDate())){
			 asset.setDepreciationDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), assetDTO.getDepreciationDate()));
		 }else {
			 asset.setDepreciationDate(null);
		 }
		 
		//Operating System
		 if(StringUtils.isNotBlank(assetDTO.getOperatingSystem())){
			 asset.setOperatingSystem(assetDTO.getOperatingSystem());
		 }else {
			 asset.setOperatingSystem(null);
		 }
		 
		 //osKey
		 if(StringUtils.isNotBlank(assetDTO.getOsKey())){
			 asset.setOsKey(assetDTO.getOsKey());
		 }else {
			 asset.setOsKey(null);
		 }
		 
		 //antiVirus
		 if(StringUtils.isNotBlank(assetDTO.getAntiVirus())){
			 asset.setAntiVirus(assetDTO.getAntiVirus());
		 }else {
			 asset.setAntiVirus(null);
		 }
		 
		 //cost
		 if(assetDTO.getCost()  != null){
			 asset.setCost(assetDTO.getCost());
		 }else {
			 asset.setCost(null);
		 }
		 
		 //depreciationAmount
		 if(assetDTO.getDepreciationAmount()  != null){
			 asset.setDepreciationAmount(assetDTO.getDepreciationAmount());
		 }else {
			 asset.setDepreciationAmount(null);
		 }
		 
		 //Vendor Details
		 if(assetDTO.getVendorDetails()  != null){
			 asset.setVendorDetails(assetDTO.getVendorDetails());
		 }else {
			 asset.setVendorDetails(null);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getCategory())){
			 AssetCategory assetCategory = new AssetCategory();
			 assetCategory.setId(Long.parseLong(assetDTO.getCategory()));
			 asset.setAssetCategory(assetCategory);
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getWorkingStatus())){
			 asset.setWorkingStatus(assetDTO.getWorkingStatus());
		 }
		 
		 if(StringUtils.isNotBlank(assetDTO.getDescription())){
			 asset.setDescription(assetDTO.getDescription());
		 } else{
			 asset.setDescription(null);
		 }
		 
		 if(assetDTO.getAttachmentUpload() != null){
			 FileInfo fileInfo = assetDTO.getAttachmentUpload().get(0);
			 asset.setFileUrl(fileInfo.getFileURL());
			 asset.setFileName(fileInfo.getFileName());
			 asset.setFileContentType(fileInfo.getContentType());
			 FileUploadUtil.moveFile(assetDTO.getStaticPath(), fileInfo, "assetDocuments");
		 }
		 
		 Set<AssetAssign> assetAssignDBList = new HashSet<>();
		 if(assetDTO .getAssignAsset() != null &&  !assetDTO .getAssignAsset().isEmpty()) {
			 AssetAssign aa = null;
			 for(AssetAssignDTO aad : assetDTO .getAssignAsset()) {
					if(aad.getShiftEmp() != null && !aad.getShiftEmp().isEmpty()) {
					  aa  = new AssetAssign();
					 if(aad.getId() != null) {
						 aa.setId(aad.getId());
						 aa = assetDao.getAssignAssetByAssetId(Long.valueOf(aad.getId()));
						 if(aa.getFileName() != null && aa.getFileName() != "") {
							 aa.setFileName(aa.getFileName());
							 aa.setFileType(aa.getFileType());
							 aa.setFileURL(aa.getFileURL());
						 }
					 }
					 aa.setAsset(asset);
					 
					 //userRole id saving
					 UserRole  userRole = new UserRole();
					 userRole.setId(Long.valueOf(aad.getShiftEmp()));
					 aa.setUserRole(userRole);
					 
					 //shift id saving
					 Shift shift = new Shift();
					 shift.setId(Long.valueOf(aad.getShift()));
					 aa.setShift(shift);
					 
					 aa.setStatus(Constants.RECORD_ACTIVE);
					 if(aad.getAssignedOn() != null) {
						 aa.setAssignedOn(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), aad.getAssignedOn()));
					 }else {
						 aa.setAssignedOn(null);
					 }
					 if(StringUtils.isNotBlank(aad.getReturnedOn())) {
						 aa.setReturnedOn(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), aad.getReturnedOn()));
						 Date currentDate  = new Date();
						 if(currentDate.before(aa.getReturnedOn())) {
							 aa.setStatus(Constants.RECORD_INACTIVE);
						 }
					 }else {
						 aa.setReturnedOn(null);
					 }
					 
					 
					 if(aad .getFileInfo() != null) {
						List<FileInfo> assetAssignDocumentsList = aad .getFileInfo();
						for(FileInfo fileInfo : assetAssignDocumentsList){
							aa.setFileName(fileInfo.getFileName());
							aa.setFileType(fileInfo.getContentType());
							aa.setFileURL(fileInfo.getFileURL());
							FileUploadUtil.moveFile(assetDTO.getStaticPath(), fileInfo, "assetDocuments");
						}
					  }
						assetAssignDBList.add(aa);
				    }else {
				    	if(aad.getId() != null && aad.getShiftEmp().isEmpty()) {
				    		 aa = assetDao.getAssignAssetById(aad.getId());
						    	if(aa != null) {
						    		assetDao.deleteAssignAssetByAssignId(aa);
						    	}
				    	}
				    }
				 }
				  asset.setAssetAssign(assetAssignDBList); 
		 }
		    		 
		 
		 asset.setOrgLocations(orgLocations);
		 asset.setUpdatedBy(user.getUserCode());
		 asset.setUpdatedDate(new Date());
		 asset.setStatus(Constants.RECORD_ACTIVE);
		 Asset savedAsset = assetDao.saveAsset(asset);		

			if(assetDTO.getCustomFormData() !=null) {
				for( CustomFormFieldDTO customFields:assetDTO.getCustomFormData()) {
					if(customFields.getCustomFormFieldId() > 0 &&StringUtils.isNotEmpty(customFields.getCustomFormFieldData())) {
					AssetCustomFieldData assetCustomFields = null;
						if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
							assetCustomFields = assetCustomFieldService.getAssetCustomFieldDataById(customFields.getEntityId());
						}else {
							assetCustomFields = new AssetCustomFieldData();
						}
						assetCustomFields.setAssets(savedAsset);
					OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					assetCustomFields.setOrglocationsFormFields(orgFormField);
					assetCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					assetCustomFieldService.saveAssetCustomFieldData(assetCustomFields);
					}
					
				}	
			}
		 
		 return asset;
	}


	@Override
	public Asset getAssetByAssetSnoAndId(String assetSno, Long assetId, Long orgLocationsId) throws Exception {
		
		return assetDao.getAssetByAssetSnoAndId(assetSno,assetId,orgLocationsId);
	}
	


	@Override
	public Asset validateAssetSno(String assetSno, Long orgLocationsId) throws Exception {
		return assetDao.validateAssetSno(assetSno,orgLocationsId);
	}
	
	@Override
	public Boolean validateAssetId(String assetSno, Long orgLocationsId) throws Exception {
		return assetDao.validateAssetId(assetSno,orgLocationsId);
	}


	@Override
	public ResponseList<Asset> getDynamicAssetFilterListByLocationId(Long orgLocationId, AssetDTO assetDTO) throws Exception {
		return assetDao.getDynamicAssetFilterListByLocationId(orgLocationId, assetDTO);
	}


	@Override
	public List<AssetAssign> getUserBasedAssetManagementListandOrgLocation(Long orgLocationId, Long UserId) throws Exception {
		return assetDao.getUserBasedAssetManagementListandOrgLocation(orgLocationId, UserId);
	}


	@Override
	public List<AssetAssign> getAssignAssetListByAssetId(Long assetId) {
		return assetDao.getAssignAssetListByAssetId(assetId);
	}


	@Override
	public AssetAssign getAssignAssetByAssetId(Long assetId) throws Exception {
		return assetDao.getAssignAssetByAssetId(assetId);
	}


	@Override
	public AssetAssign saveAssetAssign(AssetAssign assetAssign) throws Exception {
		return assetDao.saveAssetAssign(assetAssign);
	}


	@Override
	public List<AssetAssign> getAssignAssetListByShiftId(Long shiftId) throws Exception {
		return assetDao.getAssignAssetListByShiftId(shiftId);
	}


	@Override
	public void deleteAssignAssetByAssignId(AssetAssign assetAssign) throws Exception {
		assetDao.deleteAssignAssetByAssignId(assetAssign);
	}


	@Override
	public List<AssetAssign> getReturnedOnAssignAssetListByAssetId(Long assetId) {
		return assetDao.getReturnedOnAssignAssetListByAssetId(assetId);
	}


	@Override
	public List<AssetAssign> getAllAssignAssetListByAssetId(Long assetId) {
		return assetDao.getAllAssignAssetListByAssetId(assetId);
	}
}
