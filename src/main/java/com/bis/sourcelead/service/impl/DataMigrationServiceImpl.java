package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.dto.AssetAssignDTO;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.AssetsDataImportDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationPassportVisaDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementLocationsDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.DataMigrationCommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationServiceImpl.class);
	@Autowired
	DataMigrationDao dataMigrationDao;
	
	@Autowired
	private RequirementService requirementService;

	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	private DataMigrationCommonUtil dataMigrationCommonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private ProjectTasksService tasksService;
	
	@Autowired
	private ProjectsService projectService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	@Autowired
	private ImmigrationCommonService immigrationCommonService;
	
	@Override
	public Map<String, String> getfileData(String filePath, String headerPosition) throws Exception
	{
		FileInputStream inputStream = new FileInputStream(new File(filePath.replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = null;
		if (filePath.endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}
		else {
			workbook = new XSSFWorkbook(inputStream);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		List<String> fieldList = new ArrayList<String>();
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if(nextRow.getRowNum()+1 == Integer.parseInt(headerPosition) ) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
					if (!fieldList.contains(CellReference.convertNumToColString(cell.getColumnIndex())) && StringUtils.isNotEmpty(cellValueStr))
						dataWithCellId.put(CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(), cellValueStr);
						fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
				}
			}
		}
		workbook.close();
		inputStream.close();
		return dataWithCellId;
	}

	@Override
	public List<SourceleadFormFields> getfieldsByActionIdAndImportStatus(Long actionId, int importStatus) throws Exception
	{

		return dataMigrationDao.getfieldsByActionIdAndImportStatus(actionId, importStatus);
	}

	@Override
	public String validateImportedFormData(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String importStatus = null;
		String finalImportStatus = null;
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		FileInputStream inputStream = new FileInputStream(new File(ac
				+ dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator;
		if (dataMigrationDTO.getExportFilePath().endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}
		else {
			workbook = new XSSFWorkbook(inputStream);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		int colCount = 0;
		Boolean isEmptyExcel = true;
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if(nextRow.getRowNum()+1 == Integer.parseInt(dataMigrationDTO.getHeaderPosition()) ) {
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						colCount++;
					continue;
				}
			}
			if(nextRow.getRowNum()+1 > Integer.parseInt(dataMigrationDTO.getHeaderPosition()) ) {
				isEmptyExcel = false;
				HashMap< String, String> errorSet = new HashMap< String, String>();
				Gson gson = new Gson();
				String gsonOutput = gson.toJson(dataMigrationDTO);
				JSONObject dataMigrationDTOJson = new JSONObject(gsonOutput);
				DataMigrationDTO dataMigrationDTOCopy = new DataMigrationDTO();
				JSONObject dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOCopy);
				Iterator<?> keys = dataMigrationDTOJson.keys();
				
				/*this code is for assign projectUUI(in project Task upload) from dataMigrationDTO to dataMigrationDTOJsonCopy json object*/
				if(dataMigrationDTO.getUploadType().equalsIgnoreCase("projectTasks")) {
					dataMigrationDTOJsonCopy.put("projectUUID", dataMigrationDTO.getProjectUUID());
					dataMigrationDTOJsonCopy.put("projectClientUUID", dataMigrationDTO.getProjectClientUUID());
				}
	
				while( keys.hasNext() ) {
				     String k = (String)keys.next();
				      if(StringUtils.isNotEmpty(k) && !k.equalsIgnoreCase("days")) {
				    	  Iterator<Cell> cellIterator = nextRow.cellIterator();
				    	  StringBuffer assertBuffer = new StringBuffer();
				    	  while (cellIterator.hasNext()) {
								Cell cell = cellIterator.next();
								if (dataMigrationDTOJson.get((String) k) != null) {
									
								if(k.equalsIgnoreCase("assignAsset")) {
										Gson converter = new Gson();
										java.lang.reflect.Type listType = new TypeToken<List<AssetAssignDTO>>() {}.getType();
										List<AssetAssignDTO> list =  converter.fromJson(dataMigrationDTOJson.get((String) k).toString(), listType );
										int incr = 0;
										for(AssetAssignDTO aa :list) {
											incr++;
											if ((aa.getXlCol().replaceAll("\\d", "")).equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
												assertBuffer.append(objDefaultFormat.formatCellValue(cell, objFormulaEvaluator).toString().trim());
												assertBuffer.append("#").append(aa.getShift());
												if(list.size() > 1 && incr < list.size()) {
													assertBuffer.append("&");
												}
											}
											
										}
										dataMigrationDTOJsonCopy.put("assignAssetStr", assertBuffer.toString());
									}
									if ((dataMigrationDTOJson.get((String) k).toString().replaceAll("\\d", "")).equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
										try {
											if (DateUtil.isCellDateFormatted(cell)) {
												SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
												   dataMigrationDTOJsonCopy.put((String) k,dateFormat.format(cell.getDateCellValue()));
												}else {
											     dataMigrationDTOJsonCopy.put((String) k, objDefaultFormat.formatCellValue(cell, objFormulaEvaluator).toString().trim());
												}
										}catch (Exception e) {
										     dataMigrationDTOJsonCopy.put((String) k, objDefaultFormat.formatCellValue(cell, objFormulaEvaluator).toString().trim());

										} 
										
										//dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, (String) k, objFormulaEvaluator, objDefaultFormat, user, orgLocation);
									}
								}
							}
				      }else {
				    	  JSONArray ja = dataMigrationDTOJson.getJSONArray("days");
				    	  LinkedList<String> days = new LinkedList<>();
				    	  if(ja != null && ja.length() >0) {
				    		  for(int j=0; j<ja.length(); j++) {
				    			     Iterator<Cell> cellIterator = nextRow.cellIterator();
									 String dayKey =  ja.getString(j);
									 if (StringUtils.isNotEmpty(dayKey)) {
										 while (cellIterator.hasNext()) {
										        Cell cell = cellIterator.next();
												if (dayKey.toString().replaceAll("\\d", "").equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
													days.add(objDefaultFormat.formatCellValue(cell, objFormulaEvaluator).toString().trim());
												}
										  } 
									 }else {
										 days.add(dayKey);
									 }
									  
				    		  }
				    	  }
				    	  dataMigrationDTOJsonCopy.put((String) k, days);
				      }
				}
				
				switch(dataMigrationDTO.getUploadType()){  
			    case "jobUpload": dataMigrationCommonUtil.dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, user, orgLocation);break;  
			    case "timesheetUpload": 
			    	String timeFrequency = dataMigrationDTOJson.get("timeFrequencyType").toString();
			    	dataMigrationCommonUtil.dataValidationForTimesheets(errorSet, dataMigrationDTOJsonCopy, objFormulaEvaluator, objDefaultFormat, user, orgLocation,timeFrequency);break;  
			    case "assetUpload": dataMigrationCommonUtil.dataValidationForAssets(errorSet, dataMigrationDTOJsonCopy, user, orgLocation);break;
			    
			    case "projectTasks": dataMigrationCommonUtil.dataValidationForProjectTasks(errorSet, dataMigrationDTOJsonCopy, user, orgLocation);break;
			    
			    case "immigrationUpload": dataMigrationCommonUtil.dataValidationForImmigration(errorSet, dataMigrationDTOJsonCopy, user, orgLocation);break;

				}
				
				if(errorSet.isEmpty()){
					mapToEntities(dataMigrationDTOJsonCopy, user, orgLocation, dataMigrationDTO.getUploadType());
					importStatus = "success";
				}else{
					int errorCount = 0;
					StringBuilder errorSB = new StringBuilder();
					for (Map.Entry<String, String> error : errorSet.entrySet()) {
						errorCount++;
						errorSB.append(errorCount).append(") ").append(error.getValue()).append("\n");
					}
						CellStyle style = workbook.createCellStyle();
				        Font font = workbook.createFont();
				        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				        font.setColor(HSSFColor.RED.index);
				        style.setFont(font);
				        style.setWrapText(true);
					 	nextRow.createCell(colCount+5).setCellValue(errorSB.toString());
					 	nextRow.getCell(colCount+5).setCellStyle(style);
	
				        FileOutputStream outputStream = new FileOutputStream(new File(ac + dataMigrationDTO.getExportFilePath()));
				        workbook.write(outputStream);
				        //outputStream.close();
				        //importStatus = dataMigrationDTO.getExportFilePath();
				        finalImportStatus = dataMigrationDTO.getExportFilePath();
				}
			}
		}
		workbook.close();
		inputStream.close();
		if(isEmptyExcel) {
			return "fail";
		}else if(finalImportStatus != null) {
			return finalImportStatus;
		}else {
			return importStatus;
		}
		
	}
	
	@SuppressWarnings("unused")
	private void mapToEntities(JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation, String uploadType) throws Exception
	{
		Gson gson = new Gson();
		switch(uploadType){
		    case "jobUpload": 
		    	RequirementDTO reqDTO = new RequirementDTO();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				reqDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), RequirementDTO.class);
				
				//reqDTO = mapper.readValue(dataMigrationDTOJsonCopy, RequirementDTO.class);
				reqDTO.setDateFormat(orgLocation.getDateFormat());
				if(reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("SUBMITTED")) {
					reqDTO.setSaveType("submitForApproval");
				}
				if(reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("DRAFT")) {
					reqDTO.setSaveType("draft");
				}
				if(reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("OPEN")) {
					reqDTO.setSaveType("save");
				}
				
				if(reqDTO.getParallelApprover() != null){
					reqDTO.setApprover("Parallel");
				}
				if(reqDTO.getCoolingOff() == null || reqDTO.getCoolingOff() == "" || reqDTO.getCoolingOff() == "null"){
					reqDTO.setCoolingOff(orgLocation.getCoolingOffPeriod());
				}
				reqDTO.setImportValue("Y");
				if(StringUtils.isNotEmpty(reqDTO.getClientSubmissionForm()) && StringUtils.isNotEmpty(reqDTO.getSelectContact()) && StringUtils.isNotEmpty(reqDTO.getSelectClient())){
					reqDTO.setIsClient("Y");
					reqDTO.setApplicationFrom(null);
				}else if(StringUtils.isEmpty(reqDTO.getIsClient()) || !reqDTO.getIsClient().equalsIgnoreCase("Y")){
					if(StringUtils.isEmpty(reqDTO.getApplicationFrom())) {
						SubmissionTemplate   submissionTempletes = submissionTemplateService.getDefaultSubmissionTemplateByOrgLocationId(orgLocation.getId());
						if(submissionTempletes != null) {
							reqDTO.setApplicationFrom(submissionTempletes.getId().toString());
						}
					}
				}
				if (reqDTO.getFileURL() != null && !reqDTO.getFileURL().isEmpty()) {
					List<FileInfo> fileInfoList = new ArrayList<>();
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileName(reqDTO.getFileName());
					fileInfo.setFileURL(reqDTO.getFileURL());
					fileInfo.setContentType(reqDTO.getFileContentType());
					fileInfoList.add(fileInfo);
					reqDTO.setFileInfo(fileInfoList);
				}
				
				List<RequirementLocationsDTO> locationDTOList = new ArrayList<>();
				RequirementLocationsDTO locationDTO = new RequirementLocationsDTO();
				locationDTO.setLocationName(reqDTO.getLocation());
				if(StringUtils.isNotBlank(reqDTO.getCountry())) {
					locationDTO.setCountry(reqDTO.getCountry());
				}
				if(StringUtils.isNotBlank(reqDTO.getState())) {
					locationDTO.setState(reqDTO.getState());
				}
				if(StringUtils.isNotBlank(reqDTO.getCity())) {
					locationDTO.setCity(reqDTO.getCity());
				}
				if(StringUtils.isNotBlank(reqDTO.getZipCode())) {
					locationDTO.setZipcode(reqDTO.getZipCode());
				}
				locationDTOList.add(locationDTO);
				reqDTO.setLocationInfo(locationDTOList);
				Requirement requirement = requirementService.storeRequirement(reqDTO, orgLocation, user);
		    break;
		    
		    	case "assetUpload": 
		    	AssetDTO assetDTO = new AssetDTO();
		    	AssetsDataImportDTO assetMigDTO = new AssetsDataImportDTO();
		    	ObjectMapper mapper1 = new ObjectMapper();
				mapper1.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				assetMigDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), AssetsDataImportDTO.class);

				List<AssetAssignDTO> assignAssetList = new ArrayList<>();
				if(StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("assignAssetStr"))) {
					String[] assignUsere = dataMigrationDTOJsonCopy.getString("assignAssetStr").split("&");
					for(String as:assignUsere) {
						AssetAssignDTO aDTO = new AssetAssignDTO();
						String[] email = as.split("#");
						aDTO.setShiftEmp(email[0]);
						aDTO.setShift(email[1]);
						assignAssetList.add(aDTO);
					}
				}
				assetDTO.setAssignAsset(assignAssetList);
				assetDTO.setCategory(assetMigDTO.getCategory());
				assetDTO.setWorkingStatus(assetMigDTO.getWorkingStatus());
				assetDTO.setCpu(assetMigDTO.getCpu());
				assetDTO.setSno(assetMigDTO.getSno());
				assetDTO.setMemory(assetMigDTO.getMemory());
				assetDTO.setModel(assetMigDTO.getModel());
				assetDTO.setMake(assetMigDTO.getMake());
				assetDTO.setDescription(assetMigDTO.getDescription());
				assetDTO.setHarddisk(assetMigDTO.getHarddisk());
				
				Asset asset = assetService.saveAssetDetails(assetDTO, user, orgLocation);
		    	break;
		    
		    case "timesheetUpload": 
		    	System.out.println("Time Sheet block");	    
		    break;
		    
		    
		    case "projectTasks": 
		    	System.out.println("projectTasks");	
		    	
		    	ProjectTasksDTO projectTasksDTO = new ProjectTasksDTO();
				ObjectMapper tasksMapper = new ObjectMapper();
				tasksMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				tasksMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				projectTasksDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), ProjectTasksDTO.class);
//				projectTasksDTO = tasksMapper.readValue(dataMigrationDTOJsonCopy, ProjectTasksDTO.class);
				Projects project  = projectService.getProjectByProjectUUID(projectTasksDTO.getProjectUUID());
				 
				tasksService.saveProjectTasksDTOByProjectId(project.getId(), projectTasksDTO, user.getId());
		    break;
		   
		    case "immigrationUpload": 
		    	System.out.println("inside Immigration uplod---->>>>>");	
		    	ObjectMapper immigratoinMapper = new ObjectMapper();
		    	immigratoinMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		    	immigratoinMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    	// below details need to pushed into immigration table.
		    	System.out.println("dataMigrationDTOJsonCopy. get Template  ---->>>>>"+dataMigrationDTOJsonCopy.getString("template"));
		    	ImmigrationDTO immDto = new ImmigrationDTO();
		    	//immDto.setEmpId(dataMigrationDTOJsonCopy.getString("empId"));
		    	immDto.setCandidateId(dataMigrationDTOJsonCopy.getString("candidateId")); // setting candidate id manually for saving.
		    	immDto.setFirstName(dataMigrationDTOJsonCopy.getString("beneficiaryFirstName"));
		    	immDto.setLastName(dataMigrationDTOJsonCopy.getString("beneficiaryLastName"));
		    	immDto.setEmail(dataMigrationDTOJsonCopy.getString("beneficiaryEmail"));
		    	immDto.setExperience(dataMigrationDTOJsonCopy.getString("experience"));
		    	immDto.setSsnNumber(dataMigrationDTOJsonCopy.getString("ssn"));
		    	immDto.setI_94_number(dataMigrationDTOJsonCopy.getString("i94Number"));
		    	immDto.setI_20_number(dataMigrationDTOJsonCopy.getString("i20Number"));
		    	immDto.setEadNumber(dataMigrationDTOJsonCopy.getString("eadNumber"));
		    	immDto.setBirthDate(dataMigrationDTOJsonCopy.getString("dateOfBirth"));
		    	//
		    	immDto.setMaritalStatus(dataMigrationDTOJsonCopy.getString("maritalStatus"));
		    	immDto.setPetitionType(dataMigrationDTOJsonCopy.getString("petitionType"));
		    	immDto.setTemplateId(dataMigrationDTOJsonCopy.getString("template"));
		    	immDto.setArrivalDate(dataMigrationDTOJsonCopy.getString("dateOfArrival"));
		    	immDto.setOpenDate(dataMigrationDTOJsonCopy.getString("openOn"));
		    	immDto.setAttorneyContactName(dataMigrationDTOJsonCopy.getString("companyName"));
		    	immDto.setAttorneyEmail(dataMigrationDTOJsonCopy.getString("companyContactEmail"));
		    	
		    	immDto.setJobId("3");// Sql as of now hard coded value.
		    	immDto.setAttorneyId(dataMigrationDTOJsonCopy.getString("company")); // immigrationServices.
		    	immDto.setAttorneyContact(dataMigrationDTOJsonCopy.getString("companyContactEmail"));
		    	//
		    	immDto.setCurrUscisStatus(dataMigrationDTOJsonCopy.getString("status")); // currUscisStatus status in immigration.
		    	immDto.setReceiptNo(dataMigrationDTOJsonCopy.getString("receiptNo"));
		    	immDto.setLocation(dataMigrationDTOJsonCopy.getString("benficiarylocation"));
		    	// depends on associate id populate visa Object.
		    	/*ImmigrationPassportVisaDTO passVisaDto = new ImmigrationPassportVisaDTO(); // need to do later
		    	passVisaDto.setIdNumber(dataMigrationDTOJsonCopy.getString("visaNo"));
		    	passVisaDto.setValidFrom(dataMigrationDTOJsonCopy.getString("visaStartDate"));
		    	passVisaDto.setValidTo(dataMigrationDTOJsonCopy.getString("visaEndDate"));
		    	passVisaDto.setValidTo(dataMigrationDTOJsonCopy.getString("visaEndDate"));*/
		    	//immDto.setOpenDate(dataMigrationDTOJsonCopy.getString("lawFirmClient"));
		    	// for lca details need to push lca table.
		    	//CandidateLcaI94Summary lcaCurrSummary = new CandidateLcaI94Summary();
		    //	lcaSummary.setStartDate(dataMigrationDTOJsonCopy.getString("lcaStartDate")); format the date with org location.
		    //	lcaSummary.setEndDate(dataMigrationDTOJsonCopy.getString("lcaEndDate"));format the date with org location.
		   // 	lcaSummary.setLcaTitle(dataMigrationDTOJsonCopy.getString("lcaTitle"));// pending lcaTitle in excel mapping.
		    	immDto.setLcaNumber(dataMigrationDTOJsonCopy.getString("lcaNumber"));
		    	immDto.setRate(dataMigrationDTOJsonCopy.getString("lcaRate"));
		    	immDto.setJobLocation(dataMigrationDTOJsonCopy.getString("lcaLocation")); 
//		    	/lcaCurrSummary.setIsCurrent(Constants.YES);
		    	// candidate previous lca summary detials below
		    	// CandidateLcaI94Summary lcaPrevSummary = new CandidateLcaI94Summary();
		    	//lcaPrevSummary.setStartDate(dataMigrationDTOJsonCopy.getString("prevLcaStartDate")); format the date with org location.
			    //lcaPrevSummary.setEndDate(dataMigrationDTOJsonCopy.getString("prevLcaEndDate")); format the date with org location.
		     	//lcaSummary.setPrevLcaTitle(dataMigrationDTOJsonCopy.getString("prevLcaTitle"));prevLcaTitle
		    	immDto.setPrevLcaNumber(dataMigrationDTOJsonCopy.getString("prevLcaNumber"));
		    	immDto.setRate(dataMigrationDTOJsonCopy.getString("pevLcaRate"));
		    	immDto.setJobLocation(dataMigrationDTOJsonCopy.getString("prevLcaLocation"));
		    	immDto.setEditMode("false");
		    	// pending prevLcaTitle in excel mapping.
		    	Immigration immigration= immigrationCommonService.savePetitionDetails(immDto, user, orgLocation, null);
		    	System.out.println("New Immigration successfully created ---->>>>>"+immigration.getGeneratedId());	
		    	
		    break;
		}
	}
}
