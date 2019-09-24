package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.openxml4j.opc.OPCPackage;
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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.BulkImportCacheDTO;
import com.bis.sourcelead.couchbase.BulkImportFailureCacheDTO;
import com.bis.sourcelead.couchbase.BulkImportFailureDocumentsCacheDTO;
import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.dto.AssetAssignDTO;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.AssetsDataImportDTO;
import com.bis.sourcelead.dto.CandidateBulkUploadDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaPlaceOfEmploymentDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementLocationsDTO;
import com.bis.sourcelead.dto.UploadEntriesDTO;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateVisa;
import com.bis.sourcelead.entity.CandidateVisaHistory;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.UploadXlsHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.entity.XlsBulkUploadHistroy;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CouchbaseClusterService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.DataMigrationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.service.UploadXlsHistoryService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.XlsBulkUploadHistroyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.DataMigrationCommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.icu.util.Calendar;
import com.monitorjbl.xlsx.StreamingReader;


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
	private ImmigrationCommonService immigrationCommonService;
	
	@Autowired
	CandidateService candidateService;

	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	RequirementElkUtil requirementElkUtil;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	UploadXlsHistoryService uploadXlsHistoryService;
	
	@Autowired
	SourceLeadSessionManager sourceLeadSessionManager;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CouchbaseClusterService couchbaseClusterService;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	@Autowired
	XlsBulkUploadHistroyService xlsBulkUploadHistroyService;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	    
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	RequirementWorkFlowService requirementWorkflowService;
	
	@Value("${env}")
	 private String environment;
	
	int candidateIndexKey = 0;
	List<Candidate> uploadedCandidatesList = null;
	
	Map<String, String> candidateVisaMap = new HashMap<String, String>();
	Immigration immigration = null;
	@Override
	public Map<String, String> getfileData(String filePath, String headerPosition) throws Exception {
	   // clearing the map for a new file import not to insert duplicate immigration petition for when user uploaded with multiple visas.
			candidateVisaMap.clear();
		FileInputStream inputStream = new FileInputStream(new File(filePath.replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = null;
		if (filePath.endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		} else {
			workbook = new XSSFWorkbook(inputStream);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		Map<String, String> dataWithCellId = new TreeMap<String, String>();
		List<String> fieldList = new ArrayList<String>();
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if (nextRow.getRowNum() + 1 == Integer.parseInt(headerPosition)) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValueStr = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
					if (!fieldList.contains(CellReference.convertNumToColString(cell.getColumnIndex()))
							&& StringUtils.isNotEmpty(cellValueStr))
						dataWithCellId.put(
								CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(),
								cellValueStr);
					fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
				}
			}
		}
		workbook.close();
		inputStream.close();
		return dataWithCellId;
	}

	@Override
	public List<SourceleadFormFields> getfieldsByActionIdAndImportStatus(Long actionId, int importStatus)
			throws Exception {

		return dataMigrationDao.getfieldsByActionIdAndImportStatus(actionId, importStatus);
	}

	@Override
	public String validateImportedFormData(DataMigrationDTO dataMigrationDTO, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		String importStatus = null;
		String finalImportStatus = null;
		String jsonString = null;
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator;
		if (dataMigrationDTO.getExportFilePath().endsWith(".xls")) {
			workbook = WorkbookFactory.create(new File(ac + dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		} else {
			OPCPackage pkg = OPCPackage.open(new File(ac + dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
			workbook = new XSSFWorkbook(pkg);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Integer rowCount = firstSheet.getLastRowNum();
		String[] fileName = dataMigrationDTO.getExportFilePath().split("/");
		UploadXlsHistory history = null;
		history = saveUploadXlsHistory(rowCount, fileName[5], null, dataMigrationDTO, user, orgLocation);
		uploadXlsHistoryService.storeUploadXlsHistory(history);
		Iterator<Row> rowIterator = firstSheet.iterator();
		int colCount = 0;
		Boolean isEmptyExcel = true;
		logger.debug("Upload Start Time>>>"+Calendar.getInstance().getTime());
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if (nextRow.getRowNum() + 1 == Integer.parseInt(dataMigrationDTO.getHeaderPosition())) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					colCount++;
					continue;
				}
			}
			
			
			int rw = 0;
			if (nextRow.getRowNum() + 1 > Integer.parseInt(dataMigrationDTO.getHeaderPosition())) {
				System.out.println(++rw + "Row Processing Entry" + Calendar.getInstance().getTime());
				isEmptyExcel = false;
				HashMap<String, String> errorSet = new HashMap<String, String>();
				Gson gson = new Gson();
				String gsonOutput = gson.toJson(dataMigrationDTO);
				JSONObject dataMigrationDTOJson = null;
				DataMigrationDTO dataMigrationDTOCopy = new DataMigrationDTO();
				Iterator<?> keys = null;
				JSONObject dataMigrationDTOJsonCopy = null;
				
				if(dataMigrationDTO.getUploadType().equalsIgnoreCase("submmissionTemplateUpload") && dataMigrationDTO.getRequirementId() != null) {
					    String json1 = "{" + dataMigrationDTO.getJsonData() + "}";
				     	dataMigrationDTOJson = new JSONObject(json1);
						dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOJson);
						dataMigrationDTOJsonCopy.put("requirementId", dataMigrationDTO.getRequirementId());
						keys = dataMigrationDTOJson.keys();
						
						Requirement requirement = requirementService.getRequirementDetailsById(Long.valueOf(dataMigrationDTOJsonCopy.getString("requirementId")));
						if(StringUtils.isNotBlank(requirement.getClientSubmissionTemplateJsonString())) {
							jsonString = requirement.getClientSubmissionTemplateJsonString();
						}else {
							jsonString = requirement.getSubmissionTemplateJsonString();
						}
					
				}else {
					 dataMigrationDTOJson = new JSONObject(gsonOutput);
					 dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOCopy);
					 keys = dataMigrationDTOJson.keys();
				}
				
				/*
				 * this code is for assign projectUUI(in project Task upload) from
				 * dataMigrationDTO to dataMigrationDTOJsonCopy json object
				 */
				if (dataMigrationDTO.getUploadType().equalsIgnoreCase("projectTasks")) {
					dataMigrationDTOJsonCopy.put("projectUUID", dataMigrationDTO.getProjectUUID());
					dataMigrationDTOJsonCopy.put("projectClientUUID", dataMigrationDTO.getProjectClientUUID());
				}

				while (keys.hasNext()) {
					String k = (String) keys.next();
					if (StringUtils.isNotEmpty(k) && !k.equalsIgnoreCase("days")) {
						Iterator<Cell> cellIterator = nextRow.cellIterator();
						StringBuffer assertBuffer = new StringBuffer();
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							if (dataMigrationDTOJson.has((String) k) && dataMigrationDTOJson.get((String) k) != null) {

								if (k.equalsIgnoreCase("assignAsset")) {
									Gson converter = new Gson();
									java.lang.reflect.Type listType = new TypeToken<List<AssetAssignDTO>>() {
									}.getType();
									List<AssetAssignDTO> list = converter
											.fromJson(dataMigrationDTOJson.get((String) k).toString(), listType);
									int incr = 0;
									for (AssetAssignDTO aa : list) {
										incr++;
										if ((aa.getXlCol().replaceAll("\\d", "")).equalsIgnoreCase(
												CellReference.convertNumToColString(cell.getColumnIndex()))) {
											assertBuffer.append(objDefaultFormat
													.formatCellValue(cell, objFormulaEvaluator).toString().trim());
											assertBuffer.append("#").append(aa.getShift());
											if (list.size() > 1 && incr < list.size()) {
												assertBuffer.append("&");
											}
										}

									}
									dataMigrationDTOJsonCopy.put("assignAssetStr", assertBuffer.toString());
								}
								if ((dataMigrationDTOJson.get((String) k).toString().replaceAll("\\d", ""))
										.equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
									try {
										if (DateUtil.isCellDateFormatted(cell)) {
											SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
											dataMigrationDTOJsonCopy.put((String) k,
													dateFormat.format(cell.getDateCellValue()));
										} else {
											dataMigrationDTOJsonCopy.put((String) k, objDefaultFormat
													.formatCellValue(cell, objFormulaEvaluator).toString().trim());
										}
									} catch (Exception e) {
										dataMigrationDTOJsonCopy.put((String) k, objDefaultFormat
												.formatCellValue(cell, objFormulaEvaluator).toString().trim());

									}

								}
							}
						}
					} else {
						JSONArray ja = dataMigrationDTOJson.getJSONArray("days");
						LinkedList<String> days = new LinkedList<>();
						if (ja != null && ja.length() > 0) {
							for (int j = 0; j < ja.length(); j++) {
								Iterator<Cell> cellIterator = nextRow.cellIterator();
								String dayKey = ja.getString(j);
								if (StringUtils.isNotEmpty(dayKey)) {
									while (cellIterator.hasNext()) {
										Cell cell = cellIterator.next();
										if (dayKey.toString().replaceAll("\\d", "").equalsIgnoreCase(
												CellReference.convertNumToColString(cell.getColumnIndex()))) {
											days.add(objDefaultFormat.formatCellValue(cell, objFormulaEvaluator)
													.toString().trim());
										}
									}
								} else {
									days.add(dayKey);
								}

							}
						}
						dataMigrationDTOJsonCopy.put((String) k, days);
					}
				}
				
				switch (dataMigrationDTO.getUploadType()) {
				case "jobUpload":
					dataMigrationCommonUtil.dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation);
					break;
									
					
				case "timesheetUpload":
					String timeFrequency = dataMigrationDTOJson.get("timeFrequencyType").toString();
					dataMigrationCommonUtil.dataValidationForTimesheets(errorSet, dataMigrationDTOJsonCopy,
							 objDefaultFormat, user, orgLocation, timeFrequency);
					break;
					
				case "assetUpload":
					dataMigrationCommonUtil.dataValidationForAssets(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation);
					break;

				case "projectTasks":
					dataMigrationCommonUtil.dataValidationForProjectTasks(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation,isGlobalAccess);
					break;

				case "associateUpload":
					dataMigrationCommonUtil.dataValidationForAsscoaites(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation,isGlobalAccess);
					break;

				case "immigrationUpload":
					dataMigrationCommonUtil.dataValidationForImmigration(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation);
					break;
				case "lcaUpload":
					  dataMigrationCommonUtil.dataValidationForLCA(errorSet, dataMigrationDTOJsonCopy, user, orgLocation);
					
					  break;
					
				case "submmissionTemplateUpload":
					dataMigrationCommonUtil.dataValidationForRequirementSubmisssionTemplate(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation,jsonString);

					break;

				}

				if (errorSet.isEmpty()) {
					System.out.println(++rw + "Row Saving Entry Starts" + Calendar.getInstance().getTime());
					mapToEntities(dataMigrationDTOJsonCopy, user, orgLocation, dataMigrationDTO.getUploadType(),jsonString,dataMigrationDTO.getUploadid());
					System.out.println(++rw + "Row Saving Entry Ends" + Calendar.getInstance().getTime());
					importStatus = "success";
					FileOutputStream outputStream = new FileOutputStream(
							new File(ac + dataMigrationDTO.getExportFilePath()));
					workbook.write(outputStream);
					history = saveUploadXlsHistory(rowCount, fileName[5], dataMigrationDTO.getExportFilePath(), dataMigrationDTO, user, orgLocation);
					if(history != null) {
						history.setSuccessCount(history.getSuccessCount()+1);
					}
					history.setFileUrl(dataMigrationDTO.getExportFilePath());
					uploadXlsHistoryService.storeUploadXlsHistory(history);
				} else {
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
					nextRow.createCell(colCount + 5).setCellValue(errorSB.toString());
					nextRow.getCell(colCount + 5).setCellStyle(style);
                     
					FileOutputStream outputStream = new FileOutputStream(
							new File(ac + dataMigrationDTO.getExportFilePath()));
					workbook.write(outputStream);
					finalImportStatus = dataMigrationDTO.getExportFilePath();
					
					history = saveUploadXlsHistory(rowCount, fileName[5], dataMigrationDTO.getExportFilePath(), dataMigrationDTO, user, orgLocation);
					if(history != null) {
						history.setFailureCount(history.getFailureCount()+1);
					}
					history.setUploadStatus("Failed");
					history.setFileUrl(dataMigrationDTO.getExportFilePath());
					uploadXlsHistoryService.storeUploadXlsHistory(history);
				}
			}
			
		}
		logger.debug("Upload End Time>>>"+Calendar.getInstance().getTime());
		
		history.setUploadProcessingStatus("Completed");
		history.setEndDate(new Date());
		uploadXlsHistoryService.storeUploadXlsHistory(history);
		
		
	/*	if(dataMigrationDTO.getUploadType().equalsIgnoreCase("candidatesUpload")) {
			ExecutorService executorService  = null;
			if(executorService == null) {
				executorService = Executors.newSingleThreadExecutor();
			}
			
			executorService.execute(new UploadCandidateThread(uploadedCandidatesList,"Thread "+candidateIndexKey,orgLocation.getId()));
			uploadedCandidatesList = null;
			candidateIndexKey = 0;
		}*/
		
		
		workbook.close();
		if (isEmptyExcel) {
			return "fail";
		} else if (finalImportStatus != null) {
			return finalImportStatus;
		} else {
			return importStatus;
		}

	}
	
	
    public UploadXlsHistory saveUploadXlsHistory(Integer rowCount,String fileName,String fileUrl,DataMigrationDTO dataMigrationDTO,User user,OrgLocations orgLocations) {
    
    	UploadXlsHistory history = null;
		try {
			history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(dataMigrationDTO.getUploadid());
			if(history == null) {
	    		history = new UploadXlsHistory();
	    		history.setUserId(user);
	    		history.setOrgLocationId(orgLocations);
	    		history.setTotalCount(rowCount);
	    		history.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	    		history.setCreatedDate(new Date());
	    		history.setStatus(1);
	    		history.setFileName(fileName);
	    		history.setFileContentType("application/vnd.ms-excel");
	    		history.setUploadId(dataMigrationDTO.getUploadid());
	    		history.setSessionId(dataMigrationDTO.getSessionId());
	    		history.setUploadProcessingStatus("Inprogress");
	    		
	    		switch (dataMigrationDTO.getUploadType()) {
				case "jobUpload":
					history.setUploadEntityType("Jobs");
					break;
					
				case "candidatesUpload":
					history.setUploadEntityType("Candidates");
					break;
					
				case "timesheetUpload":
					history.setUploadEntityType("Timesheets");
					break;
				case "assetUpload":
					history.setUploadEntityType("Assets");
					break;

				case "projectTasks":
					history.setUploadEntityType("ProjectTasks");
					break;

				case "associateUpload":
					history.setUploadEntityType("Associates");
					break;

				case "immigrationUpload":
					history.setUploadEntityType("Immigration");
					break;
				case "lcaUpload":
					history.setUploadEntityType("LCA");
					  break;
					
				case "submmissionTemplateUpload":
					history.setUploadEntityType("Submissions");
					break;

				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return history;
    }

	@SuppressWarnings("unused")
	private void mapToEntities(JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation,
			String uploadType,String jsonString,String uploadId) throws Exception {
		Gson gson = new Gson();
		switch (uploadType) {
		case "jobUpload":
			try {
				RequirementDTO reqDTO = new RequirementDTO();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
				reqDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), RequirementDTO.class);
			  
				//updated on 24/09/2019
				// if valid workflow name is not found then setting default WORKFLOW for the JOB. SRINIVAS SABBU.
	            if(StringUtils.isEmpty(reqDTO.getWorkflow())){
	            	RequirementWorkFlowDTO workflow = requirementWorkflowService.getRequirementDefaultWorkflow(orgLocation);
	        	   reqDTO.setWorkflow(String.valueOf(workflow.getId()));
	           	}
				// reqDTO = mapper.readValue(dataMigrationDTOJsonCopy, RequirementDTO.class);
				reqDTO.setDateFormat(orgLocation.getDateFormat());
				if (reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("SUBMITTED")) {
					reqDTO.setSaveType("submitForApproval");
				}
				if (reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("DRAFT")) {
					reqDTO.setSaveType("draft");
				}
				if (reqDTO.getRequirementSubmissionStatus().equalsIgnoreCase("OPEN")) {
					reqDTO.setSaveType("save");
				}
	
				if (reqDTO.getParallelApprover() != null) {
					reqDTO.setApprover("Parallel");
				}
				if (reqDTO.getCoolingOff() == null || reqDTO.getCoolingOff() == "" || reqDTO.getCoolingOff() == "null") {
					reqDTO.setCoolingOff(orgLocation.getCoolingOffPeriod());
				}
				reqDTO.setImportValue("Y");
				if (StringUtils.isNotEmpty(reqDTO.getClientSubmissionForm())
						&& StringUtils.isNotEmpty(reqDTO.getSelectContact())
						&& StringUtils.isNotEmpty(reqDTO.getSelectClient())) {
					reqDTO.setIsClient("Y");
					reqDTO.setApplicationFrom(null);
				} else if (StringUtils.isEmpty(reqDTO.getIsClient()) || !reqDTO.getIsClient().equalsIgnoreCase("Y")) {
					if (StringUtils.isEmpty(reqDTO.getApplicationFrom())) {
						SubmissionTemplate submissionTempletes = submissionTemplateService
								.getDefaultSubmissionTemplateByOrgLocationId(orgLocation.getId());
						if (submissionTempletes != null) {
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
				if (StringUtils.isNotBlank(reqDTO.getCountry())) {
					locationDTO.setCountry(reqDTO.getCountry());
				}
				if (StringUtils.isNotBlank(reqDTO.getState())) {
					locationDTO.setState(reqDTO.getState());
				}
				if (StringUtils.isNotBlank(reqDTO.getCity())) {
					locationDTO.setCity(reqDTO.getCity());
				}
				if (StringUtils.isNotBlank(reqDTO.getZipCode())) {
					locationDTO.setZipcode(reqDTO.getZipCode());
				}
				locationDTOList.add(locationDTO);
				reqDTO.setLocationInfo(locationDTOList);
				Requirement requirement = requirementService.storeRequirement(reqDTO, orgLocation, user);
			}catch (Exception e) {
				UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
				history.setUploadProcessingStatus("Completed");
				history.setEndDate(new Date());
				uploadXlsHistoryService.storeUploadXlsHistory(history);
				e.printStackTrace();
			}
			
			break;
			
			
		case "submmissionTemplateUpload":
			
			try {
			RequirementSubmission requirementSubmission = null;
			Countries countries = null;
			if(StringUtils.isNotBlank(orgLocation.getCountry())) {
				 countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
			 }
		
				JSONObject labelItems = new JSONObject();
				JSONArray array = new JSONArray();
				Iterator<?> keys = dataMigrationDTOJsonCopy.keys();
				while (keys.hasNext()) {
					String k = (String) keys.next();
					if(!k.equalsIgnoreCase("requirementId")) {
						if(!dataMigrationDTOJsonCopy.get(k).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k).toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get(k).toString().equalsIgnoreCase("undefined")) {
							 JSONArray jsonarray = new JSONArray(jsonString);
							 for(int i=0; i<jsonarray.length(); i++){
							        JSONObject obj = jsonarray.getJSONObject(i);
							        if(obj.getString("label").replaceAll("\\s+","").equalsIgnoreCase(k)) {
							        	JSONObject firstNameItem = new JSONObject();
										String[] labelName = k.split("(?<=.)(?=\\p{Lu})");
										firstNameItem.put("label", StringUtils.join(labelName," "));
										firstNameItem.put("type", obj.getString("type"));
										firstNameItem.put("id", "text-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
										firstNameItem.put("name", k);
										if(k.equalsIgnoreCase("CurrentCTC") || k.equalsIgnoreCase("ExpectedCTC")){
											firstNameItem.put("value", countries.getCurrency()+" / "+dataMigrationDTOJsonCopy.getString(k)+" Monthly");
										}else {
											firstNameItem.put("value", dataMigrationDTOJsonCopy.getString(k));
										}
										
										labelItems.put(k, dataMigrationDTOJsonCopy.getString(k));
										array.put(firstNameItem);
							        }
							 }
							
						}
					}
				}
				
				String con = array.toString();
				
				
					requirementSubmission = requirementSubmissionService.saveRequirementSubmission(dataMigrationDTOJsonCopy,user, orgLocation, con, null);
					
					RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
					requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
					requirementSubmissionHistory.setFromStage("NEW");
					requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
					Requirement req = requirementSubmission.getRequirement();
					if(null != req){
						Integer totalApplicatsCountForRequirement = 0;
					   //updating total number of applicants for requrement
						totalApplicatsCountForRequirement = requirementSubmissionService.getNumberOfApplicantsForRequirement(req.getOrgLocationId().getId(), req.getId());
						if(totalApplicatsCountForRequirement > 0){
							req.setTotalApplicants(totalApplicatsCountForRequirement); 
							requirementService.updateRequirement(req);
						}else{
							req.setTotalApplicants(0); 
						}
						
						// pass dto as parameter
						RequirementDTO requirementDTO = commonUtil.getRequirementDTO(req);
						requirementElkUtil.saveElkRequirement(req,requirementDTO);	
					}
				}catch (Exception e) {
					UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
					history.setUploadProcessingStatus("Completed");
					history.setEndDate(new Date());
					uploadXlsHistoryService.storeUploadXlsHistory(history);
					e.printStackTrace();
				}
			
			
			break;

		case "assetUpload":
			try {
			AssetDTO assetDTO = new AssetDTO();
			AssetsDataImportDTO assetMigDTO = new AssetsDataImportDTO();
			ObjectMapper mapper1 = new ObjectMapper();
			mapper1.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			assetMigDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), AssetsDataImportDTO.class);

	
			assetDTO.setCategory(assetMigDTO.getCategory());
			assetDTO.setWorkingStatus(assetMigDTO.getWorkingStatus());
			assetDTO.setCpu(assetMigDTO.getCpu());
			assetDTO.setSno(assetMigDTO.getSno());
			assetDTO.setMemory(assetMigDTO.getMemory());
			assetDTO.setModel(assetMigDTO.getModel());
			assetDTO.setMake(assetMigDTO.getMake());
			assetDTO.setDescription(assetMigDTO.getDescription());
			assetDTO.setHarddisk(assetMigDTO.getHarddisk());
			assetDTO.setName(assetMigDTO.getName());
			assetDTO.setVendorDetails(assetMigDTO.getVendorDetails());
			assetDTO.setOperatingSystem(assetMigDTO.getOperatingSystem());
			assetDTO.setOsKey(assetMigDTO.getOsKey());
			assetDTO.setAntiVirus(assetMigDTO.getAntiVirus());
			assetDTO.setPurchaseDate(assetMigDTO.getPurchaseDate());
			assetDTO.setDepreciationAmount(assetMigDTO.getDepreciationAmount());
			assetDTO.setCost(assetMigDTO.getCost());
			assetDTO.setDepreciationDate(assetMigDTO.getDepreciationDate());
			assetDTO.setIssueDate(assetMigDTO.getIssueDate());
			assetDTO.setMacAddress(assetMigDTO.getMacAddress());
			
			
				Asset asset = assetService.saveAssetDetails(assetDTO, user, orgLocation);
			}catch (Exception e) {
				UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
				history.setUploadProcessingStatus("Completed");
				history.setEndDate(new Date());
				uploadXlsHistoryService.storeUploadXlsHistory(history);
				e.printStackTrace();
			}
			
			
			break;

		case "timesheetUpload":
			logger.debug("Time Sheet block");
			break;

		case "projectTasks":
			logger.debug("projectTasks");
			try {
			ProjectTasksDTO projectTasksDTO = new ProjectTasksDTO();
			ObjectMapper tasksMapper = new ObjectMapper();
			tasksMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			tasksMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			projectTasksDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), ProjectTasksDTO.class);
			// projectTasksDTO = tasksMapper.readValue(dataMigrationDTOJsonCopy,
			// ProjectTasksDTO.class);
			
			
				Projects project = projectService.getProjectByProjectUUID(projectTasksDTO.getProjectUUID());
				tasksService.saveProjectTasksDTOByProjectId(project.getId(), projectTasksDTO, user.getId());
			}catch (Exception e) {
				UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
				history.setUploadProcessingStatus("Completed");
				history.setEndDate(new Date());
				uploadXlsHistoryService.storeUploadXlsHistory(history);
				e.printStackTrace();
			}
			
			break;

		case "associateUpload":
			try {
			logger.debug("upload Associate");
			UsersImport userImport = new UsersImport();
			ObjectMapper userImporMmapper = new ObjectMapper();
			userImporMmapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			userImporMmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			userImport = gson.fromJson(dataMigrationDTOJsonCopy.toString(), UsersImport.class);
			
			
				uploadFileService.saveBulkAssociate(userImport, orgLocation, user);
			}catch (Exception e) {
				UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
				history.setUploadProcessingStatus("Completed");
				history.setEndDate(new Date());
				uploadXlsHistoryService.storeUploadXlsHistory(history);
				e.printStackTrace();
			}
			
			

			break;
		case "lcaUpload":
			logger.debug("lcaUpload");
			try {
			ObjectMapper lcaMapper = new ObjectMapper();
			lcaMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			lcaMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			//userImport = gson.fromJson(dataMigrationDTOJsonCopy.toString(), lcaDTO.class);
			//uploadFileService.saveBulkAssociate(userImport, orgLocation, user);
			
			LcaDTO lcaDTO = new LcaDTO();
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("newLcaNumber")) && !"null".equals(dataMigrationDTOJsonCopy.getString("newLcaNumber"))) {
				   lcaDTO.setLcaNo(dataMigrationDTOJsonCopy.getString("newLcaNumber"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("newLcaTitle")) && !"null".equals(dataMigrationDTOJsonCopy.getString("newLcaTitle"))) {
				 lcaDTO.setLcaTitle(dataMigrationDTOJsonCopy.getString("newLcaTitle"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("noOfPos")) && !"null".equals(dataMigrationDTOJsonCopy.getString("noOfPos"))) {
				 lcaDTO.setNoOfPositions(dataMigrationDTOJsonCopy.getString("noOfPos"));
			}
			
			// LCAPLACE OF EMPLOYMENT TABLE SAVING
			
			LcaPlaceOfEmploymentDTO locDTO = null;
			lcaDTO.setLcaPlaceOfEmploymentList(new ArrayList<LcaPlaceOfEmploymentDTO>()); // setting empty list
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("currClientLocation")) && !"null".equals(dataMigrationDTOJsonCopy.getString("currClientLocation"))) {

			    locDTO= new LcaPlaceOfEmploymentDTO();
				locDTO.setLocation(dataMigrationDTOJsonCopy.getString("currClientLocation"));
				locDTO.setAddressLine1(dataMigrationDTOJsonCopy.getString("currClientLocation"));
				lcaDTO.getLcaPlaceOfEmploymentList().add((LcaPlaceOfEmploymentDTO)locDTO);
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("curPrimrVenodorLocation")) && !"null".equals(dataMigrationDTOJsonCopy.getString("curPrimrVenodorLocation"))) {
			        locDTO= new LcaPlaceOfEmploymentDTO();
					locDTO.setLocation(dataMigrationDTOJsonCopy.getString("curPrimrVenodorLocation"));
					locDTO.setAddressLine1(dataMigrationDTOJsonCopy.getString("curPrimrVenodorLocation"));
					lcaDTO.getLcaPlaceOfEmploymentList().add((LcaPlaceOfEmploymentDTO)locDTO);
			}else {
				lcaDTO.setLocation("");
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("curVenodorLocation")) && !"null".equals(dataMigrationDTOJsonCopy.getString("curVenodorLocation"))) {
				    locDTO= new LcaPlaceOfEmploymentDTO();
					locDTO.setLocation(dataMigrationDTOJsonCopy.getString("curVenodorLocation"));
					locDTO.setAddressLine1(dataMigrationDTOJsonCopy.getString("curVenodorLocation"));
					lcaDTO.getLcaPlaceOfEmploymentList().add((LcaPlaceOfEmploymentDTO)locDTO);
			}else {
				lcaDTO.setLocation("");
			}
			
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("socCode")) && !"null".equals(dataMigrationDTOJsonCopy.getString("socCode"))) {
				 lcaDTO.setSocCode(dataMigrationDTOJsonCopy.getString("socCode"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("socTitle")) && !"null".equals(dataMigrationDTOJsonCopy.getString("socTitle"))) {
				 lcaDTO.setSocOccupationTitle(dataMigrationDTOJsonCopy.getString("socTitle"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("socJobTitle")) && !"null".equals(dataMigrationDTOJsonCopy.getString("socJobTitle"))) {
				lcaDTO.setJobTitle(dataMigrationDTOJsonCopy.getString("socJobTitle"));
			}
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("wageLevel")) && !"null".equals(dataMigrationDTOJsonCopy.getString("wageLevel"))) {
				 lcaDTO.setWagesLevel(dataMigrationDTOJsonCopy.getString("wageLevel"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevailWageCurr")) && !"null".equals(dataMigrationDTOJsonCopy.getString("prevailWageCurr"))) {
				 lcaDTO.setPrivilingWageCurrency(dataMigrationDTOJsonCopy.getString("prevailWageCurr"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevailWageRate")) && !"null".equals(dataMigrationDTOJsonCopy.getString("prevailWageRate"))) {
				 lcaDTO.setPrevailingWage(dataMigrationDTOJsonCopy.getString("prevailWageRate"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevailWageRatePer")) && !"null".equals(dataMigrationDTOJsonCopy.getString("prevailWageRatePer"))) {
				 lcaDTO.setPrivilingWageRateType(dataMigrationDTOJsonCopy.getString("prevailWageRatePer"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("newLcaStartDate")) && !"null".equals(dataMigrationDTOJsonCopy.getString("newLcaStartDate"))) {
				
				 lcaDTO.setStartDate(dataMigrationDTOJsonCopy.getString("newLcaStartDate"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("newLcaEndDate")) && !"null".equals(dataMigrationDTOJsonCopy.getString("newLcaEndDate"))) {
				 lcaDTO.setEndDate(dataMigrationDTOJsonCopy.getString("newLcaEndDate"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaStatus")) && !"null".equals(dataMigrationDTOJsonCopy.getString("lcaStatus"))) {
				 lcaDTO.setLcaStatus(dataMigrationDTOJsonCopy.getString("lcaStatus"));
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("newStatus")) && !"null".equals(dataMigrationDTOJsonCopy.getString("newStatus"))) {
				 lcaDTO.setStatus(dataMigrationDTOJsonCopy.getString("newStatus"));
			}
			
			
				immigrationCommonService.saveLcaDetails(lcaDTO, user, orgLocation);
			}catch (Exception e) {
				UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
				history.setUploadProcessingStatus("Completed");
				history.setEndDate(new Date());
				uploadXlsHistoryService.storeUploadXlsHistory(history);
				e.printStackTrace();
			}
			
			
			
			break;
		case "immigrationUpload":
			logger.debug("inside Immigration uplod---->>>>>");
			try {
			ObjectMapper immigratoinMapper = new ObjectMapper();
			immigratoinMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			immigratoinMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// below details need to pushed into immigration table.
			logger.debug("dataMigrationDTOJsonCopy. get Template  ---->>>>>"
					+ dataMigrationDTOJsonCopy.getString("template"));
			ImmigrationDTO immDto = new ImmigrationDTO();
			// immDto.setEmpId(dataMigrationDTOJsonCopy.getString("empId"));
			immDto.setCandidateId(dataMigrationDTOJsonCopy.getString("candidateId")); // setting candidate id manually
		
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("beneficiaryFirstName"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("beneficiaryFirstName"))) {																			// for saving.
				immDto.setFirstName(dataMigrationDTOJsonCopy.getString("beneficiaryFirstName"));
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("beneficiaryLastName"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("beneficiaryLastName"))) {
				immDto.setLastName(dataMigrationDTOJsonCopy.getString("beneficiaryLastName"));
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("beneficiaryEmail"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("beneficiaryEmail"))) {
				immDto.setEmail(dataMigrationDTOJsonCopy.getString("beneficiaryEmail"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("beneficiaryPhone"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("beneficiaryPhone"))) {
				immDto.setMobile(dataMigrationDTOJsonCopy.getString("beneficiaryPhone"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("experience"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("experience"))) {
				if (dataMigrationDTOJsonCopy.getString("experience").matches("[-a-zA-Z]+")) {
					// jsonObj.put("experienceYears", "0");
					immDto.setExperienceYears("0");
					immDto.setExperienceMonths("0");
				} else {
					String exp[] = getExperienceYearsAndMonths(dataMigrationDTOJsonCopy.getString("experience"));
					if (exp.length > 1) {
						immDto.setExperienceYears(exp[0]);
						if (!exp[1].matches("[a-zA-Z]+")) {
							if (!"years".equalsIgnoreCase(exp[1])) {
								int months = ((Number) NumberFormat.getInstance().parse(exp[1])).intValue();
								immDto.setExperienceMonths(String.valueOf(months));
							}

						}
					} else {
						if (exp[0].contains("years") || exp[0].contains("Years")) {
							int years = ((Number) NumberFormat.getInstance().parse(exp[0])).intValue();
							immDto.setExperienceYears(String.valueOf(years));
							immDto.setExperienceMonths("0");
						} else {
							immDto.setExperienceYears("");
							immDto.setExperienceMonths("");
						}

					}

				}
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("ssn"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("ssn"))) {
				immDto.setSsnNumber(dataMigrationDTOJsonCopy.getString("ssn"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("i94Number"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("i94Number"))) {
				immDto.setI_94_number(dataMigrationDTOJsonCopy.getString("i94Number"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("i20Number"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("i20Number"))) {
				immDto.setI_20_number(dataMigrationDTOJsonCopy.getString("i20Number"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("eadNumber"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("eadNumber"))) {
				immDto.setEadNumber(dataMigrationDTOJsonCopy.getString("eadNumber"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("dateOfBirth"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("dateOfBirth"))) {
				immDto.setBirthDate(dataMigrationDTOJsonCopy.getString("dateOfBirth"));
			}
			//
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("maritalStatus"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("maritalStatus"))) {
				immDto.setMaritalStatus(dataMigrationDTOJsonCopy.getString("maritalStatus"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("petitionType"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("petitionType"))) {
				immDto.setPetitionType(dataMigrationDTOJsonCopy.getString("petitionType"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("template"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("template"))) {
				immDto.setTemplateId(dataMigrationDTOJsonCopy.getString("template"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("dateOfArrival"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("dateOfArrival"))) {
				immDto.setArrivalDate(dataMigrationDTOJsonCopy.getString("dateOfArrival"));
			}
			
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("openOn"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("openOn"))) {
				immDto.setOpenDate(dataMigrationDTOJsonCopy.getString("openOn"));
			}
			/*
			 * immDto.setAttorneyContactName(dataMigrationDTOJsonCopy.getString(
			 * "companyName")); immDto.setAttorneyEmail(dataMigrationDTOJsonCopy.getString(
			 * "companyContactEmail"));
			 * 
			 * 
			 */
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("immigrationJobId"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("immigrationJobId"))) {
				immDto.setJobId(dataMigrationDTOJsonCopy.getString("immigrationJobId"));// Sql as of now hard coded value.
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("company"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("company"))) {
				immDto.setAttorneyId(dataMigrationDTOJsonCopy.getString("company")); // immigrationServices.
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("companyContactEmail"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("companyContactEmail"))) {
				immDto.setAttorneyContact(dataMigrationDTOJsonCopy.getString("companyContactEmail"));
			}
			//
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("status"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("status"))) {
				immDto.setCurrUscisStatus(dataMigrationDTOJsonCopy.getString("status")); // currUscisStatus status in
			}
			// immigration.
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("receiptNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("receiptNo"))) {
				immDto.setReceiptNo(dataMigrationDTOJsonCopy.getString("receiptNo"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("benficiarylocation"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("benficiarylocation"))) {
				immDto.setLocation(dataMigrationDTOJsonCopy.getString("benficiarylocation"));
			}
			// depends on associate id populate visa Object.
			ImmigrationVisaDTO visaDto = new ImmigrationVisaDTO(); // need to do later
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("visaNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("visaNo"))) {
			//	passVisaDto.setIdType(Constants.ID_TYPE_VISA);
				visaDto.setCandidateId(Long.parseLong(dataMigrationDTOJsonCopy.getString("candidateId")));
				visaDto.setVisaNumber(dataMigrationDTOJsonCopy.getString("visaNo"));
				
				visaDto.setValidFrom(dataMigrationDTOJsonCopy.getString("visaStartDate"));
				visaDto.setValidTo(dataMigrationDTOJsonCopy.getString("visaEndDate"));
				
				visaDto.setCountry(dataMigrationDTOJsonCopy.getString("visaCountry"));
			    visaDto.setVisaType(dataMigrationDTOJsonCopy.getString("visaType"));

			}
			
			ImmigrationPassportDTO passVisaDto = new ImmigrationPassportDTO();
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("passportNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("passportNo"))) {
			//	passVisaDto.setIdType(Constants.ID_TYPE_PASSPORT);
				passVisaDto.setPassportNumber(dataMigrationDTOJsonCopy.getString("passportNo"));
				passVisaDto.setCandidateId(dataMigrationDTOJsonCopy.getString("candidateId"));
			//	passVisaDto.setIdNumber(dataMigrationDTOJsonCopy.getString("passportNo"));
				passVisaDto.setValidFrom(dataMigrationDTOJsonCopy.getString("passportStartDate"));
				passVisaDto.setValidTo(dataMigrationDTOJsonCopy.getString("passportEndDate"));
				passVisaDto.setCountry(dataMigrationDTOJsonCopy.getString("passportCountry"));
			}
			// immDto.setOpenDate(dataMigrationDTOJsonCopy.getString("lawFirmClient"));
			// for lca details need to push lca table.
			// CandidateLcaI94Summary lcaCurrSummary = new CandidateLcaI94Summary();
			Lca lca = immigrationCommonService.getLcaNumber(dataMigrationDTOJsonCopy.getString("lcaNumber"));
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaStartDate"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("lcaStartDate"))) {
				immDto.setLcaStartDate(dataMigrationDTOJsonCopy.getString("lcaStartDate"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaEndDate"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("lcaEndDate"))) {
				immDto.setLcaEndDate(dataMigrationDTOJsonCopy.getString("lcaEndDate"));
			}
			// immDto.setLcaTitle(dataMigrationDTOJsonCopy.getString("lcaTitle"));
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaNumber"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("lcaNumber"))) {
				immDto.setLcaNumber(dataMigrationDTOJsonCopy.getString("lcaNumber"));
				immDto.setLcaId(lca.getId().toString());
			}
			
			if(StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaRate")) 
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("lcaRate"))){
			immDto.setRate(dataMigrationDTOJsonCopy.getString("lcaRate"));
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaLocation"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("lcaLocation"))) {
				immDto.setJobLocation(dataMigrationDTOJsonCopy.getString("lcaLocation"));
			}else {
				immDto.setJobLocation("");
			}
			// /lcaCurrSummary.setIsCurrent(Constants.YES);
			// candidate previous lca summary detials below
			// CandidateLcaI94Summary lcaPrevSummary = new CandidateLcaI94Summary();
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevLcaStartDate"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("prevLcaStartDate"))) {
				immDto.setPrevLcaStartDate(dataMigrationDTOJsonCopy.getString("prevLcaStartDate"));
			}
			
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevLcaEndDate"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("prevLcaEndDate"))) {
				immDto.setPrevLcaEndDate(dataMigrationDTOJsonCopy.getString("prevLcaEndDate"));
			}
			
			// lcaSummary.setPrevLcaTitle(dataMigrationDTOJsonCopy.getString("prevLcaTitle"));prevLcaTitle
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevLcaNumber"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("prevLcaNumber"))) {
				immDto.setPrevLcaNumber(dataMigrationDTOJsonCopy.getString("prevLcaNumber"));
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("prevLcaLocation"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("prevLcaLocation"))) {
				immDto.setJobLocation(dataMigrationDTOJsonCopy.getString("prevLcaLocation"));
			}else {
				immDto.setJobLocation("");
			}
			immDto.setEditMode("false");
			String candiateId = dataMigrationDTOJsonCopy.getString("candidateId");
			String canVisaNumber = dataMigrationDTOJsonCopy.getString("visaNo");
			
			if(candidateVisaMap.get(canVisaNumber) == null) {
			// pending prevLcaTitle in excel mapping.
			   immigration = immigrationCommonService.savePetitionDetails(immDto, user, orgLocation, null);
			}
			 CandidatePassport candidatePassport = null;
			
			//save visa details here based on visa number active or in-active
			 
			CandidateVisa candidateVisa = null;
			CandidateVisaHistory candidateVisaHistory = null;
			String visaEndDate = dataMigrationDTOJsonCopy.getString("visaEndDate");
			String visaStartDate = dataMigrationDTOJsonCopy.getString("visaStartDate");
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("visaNo")) && !"null".equals(dataMigrationDTOJsonCopy.getString("visaNo"))
					&& StringUtils.isNotBlank(visaStartDate) &&  StringUtils.isNotBlank(visaEndDate)) {
				
			   ImmigrationVisaDTO candRecentVisaDetails = immigrationCommonService.getCandidateVisaDetailsByCandidateIdAndVisaNumber(immigration.getCandidate().getId(), dataMigrationDTOJsonCopy.getString("visaNo"));
			   
			   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    Date toDayDate = new Date();
				String currentDateStr = dateFormat.format(toDayDate);
				Date newDate = dateFormat.parse(currentDateStr);
				
			    Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), newDate);
			    
			  if(candRecentVisaDetails == null) {
				  candidateVisa = immigrationCommonService.saveVisaDetails(visaDto, user, orgLocation, immigration.getId(), null);
			  }else {
				  	visaDto.setCandidateVisaId(candRecentVisaDetails.getCandidateVisaId());
				    candidateVisa = immigrationCommonService.saveVisaDetails(visaDto, user, orgLocation, immigration.getId(), null);
			  }
			
			}
			if(candidateVisaMap.get(canVisaNumber) != null)  break;
			//passport saving from import
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("passportNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("passportNo"))) {
				candidatePassport = immigrationCommonService.savePassportDetails(passVisaDto, user, orgLocation,
						immigration.getId());
			}
				candidateVisaMap.put(candidateVisa.getVisaNumber(), candiateId); 
			  //preparing map not to regenerate the petition for the same visa,  for same candidate, for multiple records for same visa number
			}catch (Exception e) {
				UploadXlsHistory history = uploadXlsHistoryService.getUploadXlsHistoryDetailsByUploadId(uploadId);
				history.setUploadProcessingStatus("Completed");
				history.setEndDate(new Date());
				uploadXlsHistoryService.storeUploadXlsHistory(history);
				e.printStackTrace();
			}

			break;
		}
	}
	
	private String[] getExperienceYearsAndMonths(String experience) {
	    
		return experience.split("[\\p{Punct}\\s]+");
		
	}

	@Override
	public List<SourceleadFormFields> getSubmissionTemplateFormFields(Long requirementId) throws Exception {
		List<SourceleadFormFields> formFieldsList = new ArrayList<>();
		Requirement requirement = requirementService.getRequirementDetailsById(requirementId);
		String jsonString = null;
		if(StringUtils.isNotBlank(requirement.getClientSubmissionTemplateJsonString())) {
			jsonString = requirement.getClientSubmissionTemplateJsonString();
		}else {
			jsonString = requirement.getSubmissionTemplateJsonString();
		}
		 JSONArray jsonarray = new JSONArray(jsonString);
		 for(int i=0; i<jsonarray.length(); i++){
		        JSONObject obj = jsonarray.getJSONObject(i);
		        SourceleadFormFields formFields = new SourceleadFormFields();
		        formFields.setLabelName(obj.getString("label"));
		        formFields.setIsFieldRequired(Integer.valueOf(obj.getString("req")));
		        formFields.setFieldName(obj.getString("label").replaceAll("\\s+",""));
		        formFieldsList.add(formFields);
		 }
		return formFieldsList;
	}
	
	
	class UploadCandidateThread implements Runnable{

		List<Candidate> uploadCandidateList = new ArrayList<>();
		String threadName = null;
		Long orgLocationId = null;
		
		public UploadCandidateThread() {
			
		}

		public UploadCandidateThread(List<Candidate> candidateList,String threadName,Long orgLocationId) {
			uploadCandidateList.addAll(candidateList);
			this.threadName = threadName;
			this.orgLocationId = orgLocationId;
		}
		
		@Override
		public void run() {
			logger.debug("Thread Start Time>>>>"+Calendar.getInstance().getTime()+">>> Theread "+threadName);
			try {
				candidateService.saveBulkCandidates(uploadCandidateList,orgLocationId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("Thread End Time>>>>"+Calendar.getInstance().getTime()+">>> Theread "+threadName);
		}

		
	}
	
	
	
	@Override
	public Map<String, String> getCandidateMappingFileData(String filePath, String headerPosition) throws Exception {
			 
		     InputStream inputStream = new FileInputStream(new File(filePath.replace("/dataMigration/", "/temp/")));
			 Workbook workbook = StreamingReader.builder()
			            .rowCacheSize(100)    
			            .bufferSize(8192)     
			            .open(inputStream); 
			 
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			Map<String, String> dataWithCellId = new TreeMap<String, String>();
			List<String> fieldList = new ArrayList<String>();
			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				if (nextRow.getRowNum() + 1 == Integer.parseInt(headerPosition)) {
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if(StringUtils.isNotBlank(cell.getStringCellValue())) {
							dataWithCellId.put(
									CellReference.convertNumToColString(cell.getColumnIndex()) + cell.getRowIndex(),
									cell.getStringCellValue());
						    fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
						}
						fieldList.add(CellReference.convertNumToColString(cell.getColumnIndex()));
					}
				}
				
			}
			workbook.close();
			inputStream.close();
			return dataWithCellId;
		}


	@Override
	public String storeCandidateBulkUploadToCouchbase(CandidateBulkUploadDTO candidateBulkUploadDTO,UploadEntriesDTO uploadEntriesDTO,HttpServletRequest request)
			throws Exception {
		
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		 Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		 String loginUserFullName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserFullName");
		 String orgLocationName = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationName");
		
		 String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		 InputStream inputStream = new FileInputStream(new File(ac + uploadEntriesDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
		 Workbook workbook = StreamingReader.builder()
		            .rowCacheSize(100)    
		            .bufferSize(8192)     
		            .open(inputStream); 
		 
		 FileInfo fileInfo = new FileInfo();
		 fileInfo.setFileURL(uploadEntriesDTO.getExportFilePath());
		 FileUploadUtil.moveFile(candidateBulkUploadDTO.getStaticPath(), fileInfo, "dataMigration");
			
		    Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			int i = 0;
			String documentQueueId = null;
			List<String> doucumentQueueIdsList = new ArrayList<>();
			
			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				if (nextRow.getRowNum() + 1 > Integer.parseInt(uploadEntriesDTO.getHeaderPosition())) {
					Gson gson = new Gson();
					String gsonOutput = gson.toJson(candidateBulkUploadDTO);
					CandidateBulkUploadDTO dataMigrationDTOCopy = new CandidateBulkUploadDTO();
					Iterator<?> keys = null;
					JSONObject dataMigrationDTOJson = new JSONObject(gsonOutput);
					JSONObject dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOCopy);
					keys = dataMigrationDTOJson.keys();
					dataMigrationDTOJsonCopy.put("orgLocationId", orgLocationId);
					boolean flag = false;
					while (keys.hasNext()) {
						String k = (String) keys.next();
						if (StringUtils.isNotEmpty(k)) {
							Iterator<Cell> cellIterator = nextRow.cellIterator();
							while (cellIterator.hasNext()) {
								Cell cell = cellIterator.next();
								if(StringUtils.isNotBlank(cell.getStringCellValue()) && flag == false) {
									flag = true;
								}
								if (dataMigrationDTOJson.has((String) k) && dataMigrationDTOJson.get((String) k) != null) {
									if ((dataMigrationDTOJson.get((String) k).toString().replaceAll("\\d", ""))
											.equalsIgnoreCase(CellReference.convertNumToColString(cell.getColumnIndex()))) {
										dataMigrationDTOJsonCopy.put((String) k, cell.getStringCellValue()).toString().trim();
									}
								}
							}
						}
					}
					
					if(flag == true) {
						 String documentId = SourceLeadRandomCodeHelper.generateRandomCustomToken();
						 JsonObject jsonObject = JsonObject.fromJson(dataMigrationDTOJsonCopy.toString());
						 sourceLeadSessionManager.setCouchbaseBulkImportJsonString(documentId, "bulkImports", jsonObject);
						 
						 
						 if(i == 10000 || i == 0 ) {
							 documentQueueId = SourceLeadRandomCodeHelper.generateRandomCustomToken();
							 doucumentQueueIdsList.add(documentQueueId);
							 i = 0;
						 }
						 
						 i++;
						 
					 try {
						 sourceLeadSessionManager.setCouchbaseQueue(documentQueueId, documentId);	
						 }catch (Exception e) {
							 e.printStackTrace();
						}
					}
			
				}
		
			}
			
			BulkImportCacheDTO importDTO = new BulkImportCacheDTO();
			importDTO.setDocumentType("Candidates");
			importDTO.setUserId(user.getId().toString());
			importDTO.setOrgLocationId(String.valueOf(orgLocationId));
			importDTO.setEmail(user.getEmail());
			importDTO.setUserName(loginUserFullName);
			importDTO.setOrgName(orgLocationName);
			importDTO.setQueueDocumentsList(doucumentQueueIdsList);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		    LocalDateTime now = LocalDateTime.now();  
		    importDTO.setCreatedDate(dtf.format(now));
		    
		    ObjectMapper sessionObj = new ObjectMapper();
		    String sessionJsonStr = sessionObj.writeValueAsString(importDTO); 
			JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
			String key = SourceLeadRandomCodeHelper.generateRandomCustomToken();
			sourceLeadSessionManager.setCouchbaseQueueJsonString(key, "bulkImports", jsonObject);
			workbook.close();
			
			saveXlsBulkUploadHistory(key, Constants.COUCH_DOCUMENT_UPLOAD_TYPE, orgLocations, user, uploadEntriesDTO.getExportFilePath(), "Candidates",0);
			
			return key;
	}
	
	
	public void saveCandidateBulkImports(String documentKey,HttpServletRequest request) throws Exception {
		
	 XlsBulkUploadHistroy history = xlsBulkUploadHistroyService.getXlsBulkUploadHistroyByDocumentKey(documentKey);
	 if(history != null) {
		try {
			
		 history.setUploadStatus("InProgress");
		 xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
		 
		 ApplicationContext ctx = new AnnotationConfigApplicationContext(CouchbasePropertyPathConfiguration.class);
	     CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
	     Bucket bucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(), couchConfig.getPassword());
	     
	     List<String> queueDocumentsList =  userStatsCacheHelper.getCouchbaseQueueDocumentsList(documentKey);
	     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	     User uploadedUser = history.getCreatedBy();
	     
		 List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(history.getOrgLocationId().getId());
	   
		 List<BulkImportFailureCacheDTO> failureList = new ArrayList<>();
	    for(String document : queueDocumentsList){
				JsonArray doucumentsList =  bucket.get(JsonArrayDocument.create(document)).content();
				doucumentsList.forEach(docs -> {
					 try {
						 HashMap<String, String> errorSet = new HashMap<String, String>();
						 org.json.simple.JSONObject candidateJosn = userStatsCacheHelper.getCandidateDocument(docs.toString());
						
						if(!candidateJosn.isEmpty()) {
							 dataMigrationCommonUtil.dataValidationForCandidateBulkImports(errorSet, candidateJosn,history.getOrgLocationId(),vendorsList);
								if (errorSet.isEmpty()) {
									Gson gson = new Gson();
									CandidateDTO candidateDTO = gson.fromJson(candidateJosn.toString(), CandidateDTO.class);
									if(candidateDTO.getCountry() == null || candidateDTO.getCountry().isEmpty()) {
										candidateDTO.setCountry(history.getOrgLocationId().getCountry());
									}
									candidateService.saveCandidateBulkImports(candidateDTO, history.getOrgLocationId(), uploadedUser, vendorsList);
									 bucket.remove(docs.toString());
								}else {
									BulkImportFailureCacheDTO failureCacheDTO = new BulkImportFailureCacheDTO();
									failureCacheDTO.setDocumentId(docs.toString());
									failureCacheDTO.setErrorMsg(errorSet);
									failureList.add(failureCacheDTO);
									
								}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
				 });
			}
	    
	    if(failureList != null && !failureList.isEmpty()) {
			
			try {
				
				BulkImportFailureDocumentsCacheDTO failureDocsDTO = new BulkImportFailureDocumentsCacheDTO();
				failureDocsDTO.setDocumentType("Candidates Upload Failure");
				failureDocsDTO.setUserId(uploadedUser.getId().toString());
				failureDocsDTO.setOrgLocationId(String.valueOf(history.getOrgLocationId().getId()));
				failureDocsDTO.setEmail(uploadedUser.getEmail());
				failureDocsDTO.setFailureList(failureList);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			    LocalDateTime now = LocalDateTime.now();  
			    failureDocsDTO.setCreatedDate(dtf.format(now));
			    
			    ObjectMapper sessionObj = new ObjectMapper();
			    String sessionJsonStr = sessionObj.writeValueAsString(failureDocsDTO); 
				JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
				String failureDocumentKey = SourceLeadRandomCodeHelper.generateRandomCustomToken();
				sourceLeadSessionManager.setCouchbaseQueueJsonString(failureDocumentKey, "bulkImports", jsonObject);
				
				saveXlsBulkUploadHistory(failureDocumentKey, Constants.COUCH_DOCUMENT_UPLOAD_FAILURE_TYPE, history.getOrgLocationId(), user, history.getFileUrl(), "Candidates",failureList.size());
				history.setUploadStatus("Completed");
				history.setUpdatedBy(user);
				history.setUpdatedDate(new Date());
				xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
				
			} catch (Exception e) {
				history.setUploadStatus("New");
				xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
				e.printStackTrace();
			}
		}
	    
	    history.setUploadStatus("Completed");
		history.setUpdatedBy(user);
		history.setUpdatedDate(new Date());
		xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
	    
	 }catch (Exception e) {
		 history.setUploadStatus("New");
		 xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
		 e.printStackTrace();
	}
	    
	} 
}

    public void saveXlsBulkUploadHistory(String documentKey, String uploadType,OrgLocations orgLocations,User user,String fileUrl,String uploadEntityType,Integer count) {
    
		try {
			XlsBulkUploadHistroy history = new XlsBulkUploadHistroy();
			history.setDocumentKey(documentKey);
			history.setUploadEntityType(uploadEntityType);
			history.setOrgLocation(orgLocations.getOrgName());
			history.setOrgLocationId(orgLocations);
			history.setFileUrl(fileUrl);
			history.setUploadType(uploadType);
			history.setUploadStatus(Constants.NEW_TYPE);
			history.setUuid(SourceLeadRandomCodeHelper.generateRandomUniquUserName());
			history.setStatus(Constants.RECORD_ACTIVE);
			history.setFailedCount(count);
			history.setCreatedDate(new Date());
			history.setCreatedBy(user);
			xlsBulkUploadHistroyService.storeXlsBulkUploadHistroy(history);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void bulkUploadEmails(String couchbaseQueueKey, String uploadedSize, String type, HttpServletRequest request)
			throws Exception {
		org.json.simple.JSONObject queueDetails =  userStatsCacheHelper.getUploadedQueueUserDetails(couchbaseQueueKey);
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 if (environment.equalsIgnoreCase("prod")) {
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(queueDetails.get("userName").toString());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			 String[] toEmails = {"support@sourcelead.com","info@sourcelead.com"};
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 for(String toEmail : toEmails) {
				 if(toEmail.equalsIgnoreCase("info@sourcelead.com")) {
					 globalVarsMap.put("userName",queueDetails.get("userName").toString());
					 emailMessage.setFromEmail(toEmail);
					 emailMessage.setToEmail("support@sourcelead.com");
					 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.CANDIDATE_BULK_IMPORT_EMAIL,new ArrayList<EmailAttachment>());
				 }else if(toEmail.equalsIgnoreCase("support@sourcelead.com")){
					 Double mb = (double)Double.parseDouble(uploadedSize)/(1024*1024);
					 emailMessage.setFromEmail("support@sourcelead.com");
					 emailMessage.setToEmail(queueDetails.get("email").toString());
					 globalVarsMap.put("userName",queueDetails.get("userName").toString());
					 globalVarsMap.put("orgName",queueDetails.get("orgName").toString());
					 globalVarsMap.put("fileSize",String.format("%.2f", mb));
					 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.CANDIDATE_BULK_IMPORT_SUPPORT_EMAIL,new ArrayList<EmailAttachment>());
				 }
			 }
		 }else {
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(queueDetails.get("userName").toString());
			 emailMessage.setFromEmail("qa@sourcelead.com");
			 emailMessage.setToEmail(queueDetails.get("email").toString());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("userName",queueDetails.get("userName").toString());
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.CANDIDATE_BULK_IMPORT_EMAIL,new ArrayList<EmailAttachment>());
		 }
	}
}




