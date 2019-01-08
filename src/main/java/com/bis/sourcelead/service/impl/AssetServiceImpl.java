package com.bis.sourcelead.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dto.AssetAssignDTO;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.AssetServiceDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.AssetCustomFieldData;
import com.bis.sourcelead.entity.AssetServices;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.reportsDTO.AssetGrowthDTO;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetCustomFieldService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EmailScenarioService emailScenarioService;
		
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	AssetCategoryService assetCategoryService;
	
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


	@Override
	public AssetServices saveAssetService(AssetServices assetServices) throws Exception {
		return assetDao.saveAssetService(assetServices);
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
					 if(StringUtils.isNotBlank(aad.getAssignedOn())) {
						 aa.setAssignedOn(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), aad.getAssignedOn()));
					 }else {
						 aa.setAssignedOn(null);
					 }
					 if(StringUtils.isNotBlank(aad.getReturnedOn())) {
						 aa.setReturnedOn(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), aad.getReturnedOn()));
						 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						 Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
						 if(aa.getReturnedOn().before(dateWithoutTime)) {
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
		 Asset savedAsset = null;
		 if(assetDTO.getAssetId() != null && !"".equals(assetDTO.getAssetId())){
			 //send assign asset email
			 if(assetDTO .getAssignAsset() != null &&  !assetDTO .getAssignAsset().isEmpty() && assetDTO.getAssetId() != null) {
				 for(AssetAssignDTO aad : assetDTO .getAssignAsset()) {
					 if(aad.getShiftEmp() != null && !aad.getShiftEmp().isEmpty()) {
						 Boolean flag = assetDao.validateAssignAssetByAssetIdAndShiftIdAnsAssignUserId(asset.getId(), Long.valueOf(aad.getShift()), Long.valueOf(aad.getShiftEmp()));
						      if(flag == false)
								assignAssetEmail(aad,asset,user,orgLocations);
						 }
				 }
				 savedAsset = assetDao.saveAsset(asset); 
			 }
		 }else {
			savedAsset = assetDao.saveAsset(asset); 
			 //send assign asset email
			 if(assetDTO .getAssignAsset() != null &&  !assetDTO .getAssignAsset().isEmpty() && savedAsset != null) {
				 for(AssetAssignDTO aad : assetDTO .getAssignAsset()) {
					 if(aad.getShiftEmp() != null && !aad.getShiftEmp().isEmpty()) {
								assignAssetEmail(aad,savedAsset,user,orgLocations);
						 }
				 }
			 }
			 
		}

		
		
		 
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


	
	private void assignAssetEmail(AssetAssignDTO aad, Asset savedAsset, User loginUser, OrgLocations orgLocations) throws Exception {
		UserRole userRole = userRoleService.getUserRoleById(Long.valueOf(aad.getShiftEmp()));
		if(userRole.getUser() != loginUser) {
			AssetCategory cat = assetCategoryService.getAssetCategoryByCategoryId(savedAsset.getAssetCategory().getId());
			StringBuilder fromNameBuilder = new StringBuilder();
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setFirstName(loginUser.getFirstName());
			emailMessage.setLastName(loginUser.getLastName());
			emailMessage.setToEmail(userRole.getUser().getEmail());
			emailMessage.setFromEmail(loginUser.getEmail());
			emailMessage.setCreatedBy(loginUser.getUserCode());
			emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			
			// setting the class filed values
			Map<String, String> globalVarsMap = new HashMap<String, String>();
			globalVarsMap.put("loginUser", loginUser.getFullName());
			if(savedAsset.getName() != null) {
				globalVarsMap.put("assetName", savedAsset.getName());
			}else {
				globalVarsMap.put("assetName", "--");
			}
			globalVarsMap.put("assignMemberName", userRole.getUser().getFullName());
			
			globalVarsMap.put("category", cat.getCategoryName());

			
			if(StringUtils.isNotBlank(aad.getAssignedOn())) {
				globalVarsMap.put("assignedOn", aad.getAssignedOn());
			}else {
				globalVarsMap.put("assignedOn", "--");
			}
			
			 if(StringUtils.isNotBlank(aad.getReturnedOn())) {
				globalVarsMap.put("returnedOn", aad.getReturnedOn());
			}else {
				globalVarsMap.put("returnedOn", "--");
			}
			globalVarsMap.put("loginOrg", orgLocations.getOrgLocationName());
			String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			if (null != orgLocations.getId())
				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.ASSET_ASSIGN_EMAIL,new ArrayList<EmailAttachment>());
			// calling the sendNotification method of emailScenerioUtil
			EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ASSET_ASSIGN_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getSubject(), globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			String actionLink = null;
			if(userRole.getUser() != null) {
				emailScenerioUtil.sendNotification(actionLink, loginUser, userRole.getUser(), orgLocations, notificationScenario, new HashMap<String, String>());
			}	
		}
	}


	@Override
	public AssetServices saveAssetServiceDetails(AssetServiceDTO assetServiceDTO,OrgLocations orgLocations) throws Exception {
		AssetServices assetService = new AssetServices();
		Asset asset = new Asset();
		asset.setId(assetServiceDTO.getAssetId());
		assetService.setAsset(asset);
		assetService.setServiceDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), assetServiceDTO.getServiceDate()));
		assetService.setRecivedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), assetServiceDTO.getReceivedDate()));
		assetService.setCost(assetServiceDTO.getCost());
		assetService.setDescription(assetServiceDTO.getDescription());
		assetService.setStatus(Constants.RECORD_ACTIVE);
		assetDao.saveAssetService(assetService);
		return assetService;
	}


	@Override
	public List<AssetServices> getAssetServicesListByAssetId(Long assetId) throws Exception {
		return assetDao.getAssetServicesListByAssetId(assetId);
	}


	@Override
	public List<AssetGrowthDTO> getAssetGrowthListByOrgloationIdAndAssetGrowthDTO(Long orgLocationId, AssetGrowthDTO assetGrowthDTO,Date[] searchDates) throws Exception {
		return assetDao.getAssetGrowthListByOrgloationIdAndAssetGrowthDTO(orgLocationId, assetGrowthDTO,searchDates);
	}


	@Override
	public ResponseList<AssetAssign> getDynamicAssignAssetFilterListByLocationId(Long orgLocationId, AssetDTO assetDTO) throws Exception {
		return assetDao.getDynamicAssignAssetFilterListByLocationId(orgLocationId, assetDTO);
	}


	@Override
	public Boolean validateAssignAssetByAssetIdAndShiftIdAnsAssignUserId(Long assetId, Long shiftId, Long userRoleId) throws Exception {
		return assetDao.validateAssignAssetByAssetIdAndShiftIdAnsAssignUserId(assetId, shiftId, userRoleId);
	}


	@Override
	public List<Asset> getAllAssetByOrgId(Long orgLocationsId)throws Exception{
		return assetDao.getAllAssetByOrgId(orgLocationsId);
	}
	
	@Override
	public void deleteAsset(Asset asset) throws Exception {
		assetDao.deleteAsset(asset);;
	}


	@Override
	public ResponseList<AssetsReportDTO> getAssetReportList(AssetsReportDTO assetsReportDTO, Long orgLocationId)
			throws Exception {
		ResponseList<AssetsReportDTO> responseList = new ResponseList<>();
		List<AssetsReportDTO> list = new ArrayList<>();
		List<AssetAssign> assetAssignList = assetDao.getAssetsReportList(assetsReportDTO, orgLocationId);
		if (assetAssignList != null && !assetAssignList.isEmpty()) {
			for (AssetAssign assetAssign : assetAssignList) {
				AssetsReportDTO assetDto = new AssetsReportDTO();
				User user = userService.getUserByUserCode(assetAssign.getAsset().getCreatedBy());
				String employeeId = "";
				Employees employees = employeesService.getEmployeeByUserAndOrgLocationId(assetAssign.getUserRole().getUser().getId(), orgLocationId);
				if(employees != null) {
					employeeId = employees.getEmployeeId();
				}
				assetDto.setEmployeeId(employeeId);
				assetDto.setEmployeeName(assetAssign.getUserRole().getUser().getFullName());
				assetDto.setCategory(assetAssign.getAsset().getCategoryName());
				if (assetAssign.getAssignedOn() != null) {
					assetDto.setAssignedOn(commonUtil.dateStrFromOrgDateFormatter(
							assetAssign.getAsset().getOrgLocations().getDateFormat(), assetAssign.getAssignedOn()));
				}
				if (assetAssign.getReturnedOn() != null) {
					assetDto.setReturnedOn(commonUtil.dateStrFromOrgDateFormatter(
							assetAssign.getAsset().getOrgLocations().getDateFormat(), assetAssign.getReturnedOn()));
				}
				assetDto.setShift(assetAssign.getShift().getShiftName());
				assetDto.setFileUrl(assetAssign.getFileURL());
				assetDto.setFileContentType(assetAssign.getFileType());
				assetDto.setDocument(assetAssign.getFileName());
				assetDto.setAssetName(assetAssign.getAsset().getName());
				assetDto.setSerialNo(assetAssign.getAsset().getSlno());
				assetDto.setAssignBy(user.getFullName());
				list.add(assetDto);
			}
		}
		responseList.setData(list);
		return responseList;
	}


	@Override
	public File getAssetsReportListOfExcel(List<AssetsReportDTO> assetsReportDTO, User user, OrgLocations orgLocation,String isGlobalAccess) throws FileNotFoundException {
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLocation.getOrgLocationName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, assetsReportDTO);
		int column = 0;
		for (AssetsReportDTO   reportList : assetsReportDTO) {
			writeRow = createExcelsheet.createRow(column + 1);
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, reportList, orgLocation, user,isGlobalAccess);
			column++;
		}
		try {
			file = File.createTempFile(orgLocation.getOrgLocationName() + "_AssetsReport_" + dateFormat.format(new Date()), ".xlsx");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		file.setReadable(true);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}



	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,List<AssetsReportDTO> assetsReportDTO) {

		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String[] headers = Constants.ASSETS_REPORT_EXPORT_HEADDERS;
		;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 0);
		return createExcelsheet;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, AssetsReportDTO reportList,
			OrgLocations orgLocation, User user, String isGlobalAccess) {
			if (null != reportList.getEmployeeId()) {
				writeRow.createCell(0).setCellValue(reportList.getEmployeeId());
				createExcelsheet.autoSizeColumn(0);
			} else {
				writeRow.createCell(0).setCellValue("--");
				createExcelsheet.autoSizeColumn(0);
			}

			if (null != reportList.getEmployeeName()) {
				writeRow.createCell(1).setCellValue(reportList.getEmployeeName());
				createExcelsheet.autoSizeColumn(1);
			} else {
				writeRow.createCell(1).setCellValue("--");
				createExcelsheet.autoSizeColumn(1);
			}

			if (null != reportList.getAssetName()) {
				writeRow.createCell(2).setCellValue(reportList.getAssetName());
				createExcelsheet.autoSizeColumn(2);
			} else {
				writeRow.createCell(2).setCellValue("--");
				createExcelsheet.autoSizeColumn(2);
			}
			
			if (null != reportList.getCategory()) {
				writeRow.createCell(3).setCellValue(reportList.getCategory());
				createExcelsheet.autoSizeColumn(3);
			} else {
				writeRow.createCell(3).setCellValue("--");
				createExcelsheet.autoSizeColumn(3);
			}

			if (null != reportList.getSerialNo()) {
				writeRow.createCell(4).setCellValue(reportList.getSerialNo());
				createExcelsheet.autoSizeColumn(4);
			} else {
				writeRow.createCell(4).setCellValue("--");
				createExcelsheet.autoSizeColumn(4);
			}
			
			if(StringUtils.isNotBlank(reportList.getAssignedOn())) {
				 writeRow.createCell(5).setCellValue(reportList.getAssignedOn());
				createExcelsheet.autoSizeColumn(5);
			} else {
				writeRow.createCell(5).setCellValue("--");
				createExcelsheet.autoSizeColumn(5);
			}

			if(StringUtils.isNotBlank(reportList.getReturnedOn())) {
				writeRow.createCell(6).setCellValue(reportList.getReturnedOn());
				createExcelsheet.autoSizeColumn(6);
			} else {
				writeRow.createCell(6).setCellValue("--");
				createExcelsheet.autoSizeColumn(6);
			}

			if (null != reportList.getShift()) {
				writeRow.createCell(7).setCellValue(reportList.getShift());
				createExcelsheet.autoSizeColumn(7);
			} else {
				writeRow.createCell(7).setCellValue("--");
				createExcelsheet.autoSizeColumn(7);
			}

			if (null != reportList.getDocument()) {
				writeRow.createCell(8).setCellValue(reportList.getDocument());
				createExcelsheet.autoSizeColumn(8);
			} else {
				writeRow.createCell(8).setCellValue("--");
				createExcelsheet.autoSizeColumn(8);
			}

			if (null != reportList.getAssignBy()) {
				writeRow.createCell(9).setCellValue(reportList.getAssignBy());
				createExcelsheet.autoSizeColumn(9);
			} else {
				writeRow.createCell(9).setCellValue("--");
				createExcelsheet.autoSizeColumn(9);
			}
		
		return createExcelsheet;
	}

}
