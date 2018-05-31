package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
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
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
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
	public Map<String, String> getfileData(String filePath, String headerPosition) throws Exception {
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
		String ac = StringUtils.removeEnd(staticPath, "/slead-static");
		FileInputStream inputStream = new FileInputStream(
				new File(ac + dataMigrationDTO.getExportFilePath().replace("/dataMigration/", "/temp/")));
		Workbook workbook = null;
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator;
		if (dataMigrationDTO.getExportFilePath().endsWith(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
			objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		} else {
			workbook = new XSSFWorkbook(inputStream);
			objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		int colCount = 0;
		Boolean isEmptyExcel = true;
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
			if (nextRow.getRowNum() + 1 > Integer.parseInt(dataMigrationDTO.getHeaderPosition())) {
				isEmptyExcel = false;
				HashMap<String, String> errorSet = new HashMap<String, String>();
				Gson gson = new Gson();
				String gsonOutput = gson.toJson(dataMigrationDTO);
				JSONObject dataMigrationDTOJson = new JSONObject(gsonOutput);
				DataMigrationDTO dataMigrationDTOCopy = new DataMigrationDTO();
				JSONObject dataMigrationDTOJsonCopy = new JSONObject(dataMigrationDTOCopy);
				Iterator<?> keys = dataMigrationDTOJson.keys();

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
							if (dataMigrationDTOJson.get((String) k) != null) {

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

									// dataValidationForJobs(errorSet, dataMigrationDTOJsonCopy, (String) k,
									// objFormulaEvaluator, objDefaultFormat, user, orgLocation);
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
							objFormulaEvaluator, objDefaultFormat, user, orgLocation, timeFrequency);
					break;
				case "assetUpload":
					dataMigrationCommonUtil.dataValidationForAssets(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation);
					break;

				case "projectTasks":
					dataMigrationCommonUtil.dataValidationForProjectTasks(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation,isGlobalAccess);
					break;

				case "immigrationUpload":
					dataMigrationCommonUtil.dataValidationForImmigration(errorSet, dataMigrationDTOJsonCopy, user,
							orgLocation);
					break;

				}

				if (errorSet.isEmpty()) {
					mapToEntities(dataMigrationDTOJsonCopy, user, orgLocation, dataMigrationDTO.getUploadType());
					importStatus = "success";
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
					// outputStream.close();
					// importStatus = dataMigrationDTO.getExportFilePath();
					finalImportStatus = dataMigrationDTO.getExportFilePath();
				}
			}
		}
		workbook.close();
		inputStream.close();
		if (isEmptyExcel) {
			return "fail";
		} else if (finalImportStatus != null) {
			return finalImportStatus;
		} else {
			return importStatus;
		}

	}

	@SuppressWarnings("unused")
	private void mapToEntities(JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation,
			String uploadType) throws Exception {
		Gson gson = new Gson();
		switch (uploadType) {
		case "jobUpload":
			RequirementDTO reqDTO = new RequirementDTO();
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			reqDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), RequirementDTO.class);

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
			break;

		case "assetUpload":
			AssetDTO assetDTO = new AssetDTO();
			AssetsDataImportDTO assetMigDTO = new AssetsDataImportDTO();
			ObjectMapper mapper1 = new ObjectMapper();
			mapper1.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			assetMigDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), AssetsDataImportDTO.class);

			List<AssetAssignDTO> assignAssetList = new ArrayList<>();
			if (StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("assignAssetStr"))) {
				String[] assignUsere = dataMigrationDTOJsonCopy.getString("assignAssetStr").split("&");
				for (String as : assignUsere) {
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
			logger.debug("Time Sheet block");
			break;

		case "projectTasks":
			logger.debug("projectTasks");

			ProjectTasksDTO projectTasksDTO = new ProjectTasksDTO();
			ObjectMapper tasksMapper = new ObjectMapper();
			tasksMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			tasksMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			projectTasksDTO = gson.fromJson(dataMigrationDTOJsonCopy.toString(), ProjectTasksDTO.class);
			// projectTasksDTO = tasksMapper.readValue(dataMigrationDTOJsonCopy,
			// ProjectTasksDTO.class);
			Projects project = projectService.getProjectByProjectUUID(projectTasksDTO.getProjectUUID());

			tasksService.saveProjectTasksDTOByProjectId(project.getId(), projectTasksDTO, user.getId());
			break;

		case "immigrationUpload":
			logger.debug("inside Immigration uplod---->>>>>");
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
			ImmigrationPassportVisaDTO passVisaDto = new ImmigrationPassportVisaDTO(); // need to do later
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("visaNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("visaNo"))) {
				passVisaDto.setIdType(Constants.ID_TYPE_VISA);
				passVisaDto.setCandidateId(dataMigrationDTOJsonCopy.getString("candidateId"));
				passVisaDto.setIdNumber(dataMigrationDTOJsonCopy.getString("visaNo"));
				passVisaDto.setValidFrom(dataMigrationDTOJsonCopy.getString("visaStartDate"));
				passVisaDto.setValidTo(dataMigrationDTOJsonCopy.getString("visaEndDate"));
				passVisaDto.setCountry(dataMigrationDTOJsonCopy.getString("visaCountry"));
				passVisaDto.setVisaType(dataMigrationDTOJsonCopy.getString("visaType"));

			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("passportNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("passportNo"))) {
				passVisaDto.setIdType(Constants.ID_TYPE_PASSPORT);
				passVisaDto.setCandidateId(dataMigrationDTOJsonCopy.getString("candidateId"));
				passVisaDto.setIdNumber(dataMigrationDTOJsonCopy.getString("passportNo"));
				passVisaDto.setValidFrom(dataMigrationDTOJsonCopy.getString("passportStartDate"));
				passVisaDto.setValidTo(dataMigrationDTOJsonCopy.getString("passportEndDate"));
				passVisaDto.setCountry(dataMigrationDTOJsonCopy.getString("passportCountry"));
			}
			// immDto.setOpenDate(dataMigrationDTOJsonCopy.getString("lawFirmClient"));
			// for lca details need to push lca table.
			// CandidateLcaI94Summary lcaCurrSummary = new CandidateLcaI94Summary();
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
			}
			immDto.setRate(dataMigrationDTOJsonCopy.getString("lcaRate"));
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("lcaLocation"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("lcaLocation"))) {
				immDto.setJobLocation(dataMigrationDTOJsonCopy.getString("lcaLocation"));
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
			immDto.setJobLocation(dataMigrationDTOJsonCopy.getString("prevLcaLocation"));
			immDto.setEditMode("false");
			// pending prevLcaTitle in excel mapping.
			Immigration immigration = immigrationCommonService.savePetitionDetails(immDto, user, orgLocation, null);
			CandidatePassportVisa candidatePassportVisa = null;
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("visaNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("visaNo"))
					&& passVisaDto.getIdType().equalsIgnoreCase(Constants.ID_TYPE_VISA)) {
				candidatePassportVisa = immigrationCommonService.savePassportDetails(passVisaDto, user, orgLocation,
						immigration.getId());
			}
			if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("passportNo"))
					&& !"null".equals(dataMigrationDTOJsonCopy.getString("passportNo"))
					&& passVisaDto.getIdType().equalsIgnoreCase(Constants.ID_TYPE_PASSPORT)) {
				candidatePassportVisa = immigrationCommonService.savePassportDetails(passVisaDto, user, orgLocation,
						immigration.getId());
			}

			break;
		}
	}
	
	private String[] getExperienceYearsAndMonths(String experience) {
	    
		return experience.split("[\\p{Punct}\\s]+");
		
	}
}
