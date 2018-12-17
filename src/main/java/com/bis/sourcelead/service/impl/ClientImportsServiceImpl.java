package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.beans.ClientImportObject;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ClientImportDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.ClientVendorUploadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.BulkClientDetails;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.ClientImportsService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;

/**
 * 
 * @author Rajashekar
 * @Date 13th Sep 2016
 */
@Service
public class ClientImportsServiceImpl implements ClientImportsService {
	private static final Logger logger = LoggerFactory.getLogger(ClientImportsServiceImpl.class);
	@Autowired
	ClientService clientService;
	@Autowired
	UserDao userDao;
	@Autowired
	RolesService rolesService;
	@Autowired
	OrgService orgService;
	@Autowired
	OrgSpocService orgSpocService;
	@Autowired
	private OrgRelationsService orgRelationsService;
	@Autowired
	private UserRoleService userRoleService;
	@Value("${sourceLeadBlockedSocialDomains}")
	private String sourceLeadBlockedSocialDomains;
	@Autowired
	UserService userService;
	@Autowired
	private OrgLocationService orgLocationService;
	@Autowired
	private ClientImportDao clientImportDao;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	@Override
	public String clientBulkSavingImp(MultipartFile multipartFile, User user, OrgLocations orgLocation, Org org, String isMailSent, String responderType) throws Exception
	{
		
		boolean isValidated = false;
		boolean isClientFound = false;
		boolean isValidContact = false;
		String isFileCreated = "failed";
		String fn = multipartFile.getOriginalFilename();
		String fname = getFileName(fn);
		File file = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		InputStream inp = new FileInputStream(file);
		String fileExtn = GetFileExtension(fname);
		Workbook workbook; // Declare XSSF WorkBook
		Workbook wb_hssf = null; // Declare HSSF WorkBook
		Sheet sheet = null; // sheet can be used as common for XSSF and HSSF
		if ("xlsx".equalsIgnoreCase(fileExtn)) {
			// wb_xssf = new XSSFWorkbook(inp);
			workbook = WorkbookFactory.create(inp);
			wb_hssf = new XSSFWorkbook();
			logger.info("xlsx=" + workbook.getSheetName(0));
			sheet = workbook.getSheetAt(0);
		}
		if ("xls".equalsIgnoreCase(fileExtn)) {
			POIFSFileSystem fs = new POIFSFileSystem(inp);
			workbook = WorkbookFactory.create(fs);
			wb_hssf = (Workbook) new HSSFWorkbook();
			sheet = workbook.getSheetAt(0);
		}
		Sheet createExcelsheet = wb_hssf.createSheet("createContact");
		Iterator rows = sheet.rowIterator(); // Now we have rows ready from the
												// sheet
		int count = 0;
		List<OrgRelations> vandorsListByOrgLocationId = null;
		createExcelsheet = setClientExcelHeader(createExcelsheet, responderType);
		while (rows.hasNext()) {
			ClientImportObject client = new ClientImportObject();
			Row row = (Row) rows.next();
			if (row.getRowNum() > 0) {
				isValidated = validateClientData(row, fname, client, responderType, orgLocation);
			}
			if (isValidated) {
				if (row.getRowNum() > 0) {
					if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
						String orgName = row.getCell(0).toString().trim();
						client.setClientName(orgName);
					}
					if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
						client.setWebSite(row.getCell(1).toString());
					}
					if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
						client.setCountry(row.getCell(2).toString());
					}
					if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
						client.setState(row.getCell(3).toString());
					}
					if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
						client.setCity(row.getCell(4).toString());
					}
					if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
						client.setLevel(row.getCell(5).toString());
					}
					//
					if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
						client.setFirstName(row.getCell(6).toString());
					}
					if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
						client.setLastName(row.getCell(7).toString());
					}
					if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
						client.setEmailid(row.getCell(8).toString());
					}
					if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
						String mobileNumer = getCellValueAsString(row.getCell(9), fname);
						client.setPhone(mobileNumer);
					}
					
					if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
						String extensionNumber = getCellValueAsString(row.getCell(10), fname);
						client.setPhone(extensionNumber);
					}
					
					if (row.getCell(11) != null
							&& !"".equalsIgnoreCase(row.getCell(11).toString())) {
						String status = getCellValueAsString(row.getCell(11), fname);
						client.setStatus(status);;
					}
					if (row.getCell(12) != null
							&& !"".equalsIgnoreCase(row.getCell(12).toString())) {
						client.setOwner(row.getCell(12).toString());
					}
					else {
						client.setOwner(user.getEmail());
					}
					isClientFound = validateClient(client.getClientName(), client, orgLocation, responderType);
					if (!isClientFound) {
						OrgLocations mainOrgLocation = validateSourceleadOrg(client.getClientName(), org);
						ClientDTO clientDTO = populateJsonObject(client, isMailSent, vandorsListByOrgLocationId, orgLocation, client.getClientName(), "org", mainOrgLocation);
						try {
							clientService.storeClient(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocation, responderType, org);
						} catch (Exception e) {
							logger.error(ExceptionUtils.getFullStackTrace(e));
						}
					}
					isValidContact = validateContactsExeclDataImp(row, fname, client, orgLocation, responderType, client.getClientName());
					if (isValidContact) {
						vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgNameOrgLocationId(client.getClientName(), orgLocation.getId());
						String email = client.getOwner();
						String emails[] = email.split(",");
						for (int i = 0; i < emails.length; i++) {
							UserRole ownerUser = userRoleService.getUserDetailsByEmailAndLocationId(emails[i], orgLocation.getId());
							Long orgRelationId = 0L;
							OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(row.getCell(8).toString(), orgRelationId, ownerUser.getId(), Constants.RECORD_ACTIVE);
							if (orgSpoc == null) {
								if (ownerUser.getId() != null) {
									OrgLocations orgLocations = null;
									client.setOwnerUserId(ownerUser.getUser().getId());
									ClientDTO clientDTO = populateJsonObject(client, isMailSent, vandorsListByOrgLocationId, orgLocation, client.getClientName(), "contact", orgLocations);
									try {
										// clientService.storeClient(jsonObj,
										// user, orgLocation,responderType,org);
										orgSpocService.addVendorContacts(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocation, orgRelationId.toString(), "", responderType, org);
										vandorsListByOrgLocationId.clear();
									} catch (Exception e) {
										logger.error(ExceptionUtils.getFullStackTrace(e));
									}
								}
							}
						}
					}
					else {
						if (!isValidContact && row.getRowNum() > 0) {
							count++;
							createClientXlsErrorSheet(row, createExcelsheet, count, fname, client, orgLocation.getId());
						}
					}
				}
			}
			else {
				if (!isValidated && row.getRowNum() > 0) {
					count++;
					createClientXlsErrorSheet(row, createExcelsheet, count, fname, client, orgLocation.getId());
				}
			}
			if (count > 0) {
				FileOutputStream fos = null;
				if (!fname.endsWith("xls")) {
					fos = new FileOutputStream("client_error.xlsx");
					isFileCreated = "client_error.xlsx";
				}
				else {
					fos = new FileOutputStream("client_error.xls");
					isFileCreated = "client_error.xls";
				}
				wb_hssf.write(fos);
				fos.close();
			}
			else {
				isFileCreated = "failed";
			}
			// if(count > 0){
			// if(!fname.endsWith("xlsx")){
			// isFileCreated = "client_error.xls";
			// }else{
			// isFileCreated = "client_error.xlsx";
			// }
			//
			// }else{
			//
			// }
		}
		inp.close();
		return isFileCreated;
	}
	
	private OrgLocations validateSourceleadOrg(String clientName, Org org)
	{
		// HashMap<String, Long> hm = new HashMap<String, Long>();
		OrgLocations orgLocations = null;
		try {
			List<OrgLocations> orgLocationsList = orgLocationService.getOrgsListForClientAndVendorOrgSearch(org.getOrgCode());
			if (orgLocationsList != null) {
				for (OrgLocations orgLocation : orgLocationsList) {
					if (clientName.equalsIgnoreCase(orgLocation.getOrgLocationName())) {
						orgLocations = orgLocation;
					}
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return orgLocations;
	}
	
	/**
	 * check Clinet is duplicate or not
	 * 
	 * @param email
	 * @param client
	 * @param responderType
	 * @return boolean
	 */
	private boolean validateClient(String clientName, ClientImportObject client, OrgLocations orgLocation, String responderType)
	{
		
		boolean isCLientExist = false;
		try {
			isCLientExist = orgService.isClientNameValid(clientName, orgLocation.getId(), responderType);
			if (isCLientExist) {
				if (responderType.equalsIgnoreCase("Client")) {
					client.setReason("duplicate client");
				}
				if (responderType.equalsIgnoreCase("Vendor")) {
					client.setReason("duplicate vendor");
				}
				isCLientExist = true;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return isCLientExist;
	}
	
	/**
	 * Return File extension
	 * 
	 * @param fname2
	 * @return
	 */
	private static String GetFileExtension(String fname2)
	{
		String fileName = fname2;
		int mid = fileName.lastIndexOf(".");
		return fileName.substring(mid + 1, fileName.length());
	}
	
	/**
	 * Returns cell value as String
	 * 
	 * @param cell
	 * @param fileName
	 * @return String
	 */
	private String getCellValueAsString(Cell cell, String fileName)
	{
		String cellValue = "";
		if (fileName.toLowerCase().endsWith("xls")) {
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				hssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String hssfCellValue = hssfCell.getStringCellValue();
				if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
					cellValue = hssfCellValue;
				}
			}
			else {
				cellValue = cell.getStringCellValue();
			}
		}
		else {
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				xssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String xssfCellValue = xssfCell.getStringCellValue();
				if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
					cellValue = xssfCellValue;
				}
			}
			else {
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}
	
	/**
	 * prepares json for client saving
	 * 
	 * @param clientObject
	 * @param isMailSent
	 * @param orgName
	 * @param mainOrgLocation
	 * @param user
	 * @param string
	 * @param vendorOrg
	 * @param ownerId
	 * @return
	 * @throws JSONException
	 */
	private ClientDTO populateJsonObject(ClientImportObject clientObject, String isMailSent, List<OrgRelations> orgList, OrgLocations orgLocation, String orgName, String type, OrgLocations mainOrgLocation) throws JSONException
	{
		String roleTypeId = "";
		ClientDTO clientDTO = new ClientDTO();
		AddUserDTO addUserDTO = new AddUserDTO();
		CandidateDTO candidateDTO = new CandidateDTO();
		if (orgList != null && orgList.size() > 0) {
			clientDTO.setRoleTypeId(roleTypeId);
			OrgRelations orgRelation = null;
			for (OrgRelations org : orgList) {
				if (orgName.equalsIgnoreCase(org.getResponderLocationId().getOrgLocationName())) {
					orgRelation = org;
				}
				else if (orgName.equalsIgnoreCase(org.getLocationId().getOrgLocationName())) {
					orgRelation = org;
				}
			}
			StringBuilder vendorName = new StringBuilder();
			if (orgLocation.getId().longValue() == orgRelation.getLocationId().getId().longValue()) {
				clientDTO.setVendorName(vendorName.append(orgRelation.getId()).append(",").append(orgRelation.getResponderLocationId().getId()).toString());
			}
			else {
				clientDTO.setVendorName(vendorName.append(orgRelation.getId()).append(",").append(orgRelation.getLocationId().getId()).toString());
			}
		}
		else {
			clientDTO.setOrgName(clientObject.getClientName());
		}
		if (clientObject.getWebSite() != null) {
			clientDTO.setWebSite(clientObject.getWebSite());
		}
		if (clientObject.getCountry() != null) {
			clientDTO.setCountry(clientObject.getCountry());
			addUserDTO.setCountry(clientObject.getCountry());
			candidateDTO.setCountry(clientObject.getCountry());
		}
		else {
			if ("India".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("India");
				addUserDTO.setCountry("India");
				candidateDTO.setCountry("India");
			}
			else if ("Australia".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("Australia");
				addUserDTO.setCountry("Australia");
				candidateDTO.setCountry("Australia");
			}
			else if ("United States".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("United States");
				addUserDTO.setCountry("United States");
				candidateDTO.setCountry("United States");
			}
			else if ("Singapore".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("Singapore");
				addUserDTO.setCountry("Singapore");
				candidateDTO.setCountry("Singapore");
			}
			else if ("United Kingdom".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("United Kingdom");
				addUserDTO.setCountry("United Kingdom");
				candidateDTO.setCountry("United Kingdom");
			}
			else if ("France".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("France");
				addUserDTO.setCountry("France");
				candidateDTO.setCountry("France");
			}
		}
		if (clientObject.getState() != null) {
			clientDTO.setState(clientObject.getState());
		}
		if (clientObject.getCity() != null) {
			clientDTO.setCity(clientObject.getCity());
		}
		if (clientObject.getFirstName() != null) {
			clientDTO.setFirstName(clientObject.getFirstName());
			addUserDTO.setFirstName(clientObject.getFirstName());
			candidateDTO.setFileName(clientObject.getFirstName());
		}
		if (clientObject.getLastName() != null) {
			clientDTO.setLastName(clientObject.getLastName());
		}
		if (clientObject.getPhone() != null) {
			clientDTO.setPhoneNumber(clientObject.getPhone());
		}
		
		if(clientObject.getExtensionNumber() !=null){
			clientDTO.setExtensionNumber(clientObject.getExtensionNumber());
		}
		
		if (clientObject.getStatus() != null) {
			clientDTO.setSpocStatus(clientObject.getStatus());
		}
		if (clientObject.getOwnerUserId() != null) {
			clientDTO.setOwnerUserId(clientObject.getOwnerUserId().toString());
		}
		if (!"org".equals(type)) {
			if (clientObject.getEmailid() != null) {
				clientDTO.setEmail(clientObject.getEmailid());
				addUserDTO.setEmail(clientObject.getEmailid());
				candidateDTO.setEmail(clientObject.getEmailid());
			}
		}
		if (clientObject.getRole() != null) {
			try {
				Roles role = rolesService.getRolesByDisplayName(clientObject.getRole());
				clientDTO.setRoleTypeId(role.getId().toString());
				addUserDTO.setRoleTypeId(role.getId().toString());
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
		if (isMailSent != null) {
			clientDTO.setIsMailSent(isMailSent);
		}
		if (clientObject.getLevel() != null) {
			clientDTO.setLevel(clientObject.getLevel());
		}
		if (null != mainOrgLocation) {
			clientDTO.setOrgLocationId(mainOrgLocation.getId().toString());
		}
		
		clientDTO.setAddUserDTO(addUserDTO);
		clientDTO.setCandidateDTO(candidateDTO);
		return clientDTO;
	}
	
	/**
	 * validates the row data
	 * 
	 * @param row
	 * @param fname
	 * @param client
	 * @param responderType
	 * @param orgLocation
	 * @param orgName
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateContactsExeclDataImp(Row row, String fname, ClientImportObject client, OrgLocations orgLocation, String responderType, String orgName) throws Exception
	{
		boolean isValidated;
		List<OrgRelations> orgList = null;
		if (null != row.getCell(0)) {
			orgList = orgRelationsService.getAllClientsByOrgNameOrgLocationId(client.getClientName(), orgLocation.getId());
		}
		if (row.getRowNum() > 0) {
			
			if (row.getCell(6) == null || "".equalsIgnoreCase(row.getCell(6).toString())) {
				client.setReason("Required fields is not filled");
				return false;
			}
			else if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
				isValidated = isFnameLnameValid(row.getCell(6).toString());
				if (isValidated == false) {
					client.setReason("should contain above 2 char");
					return isValidated;
				}
			}
			if (row.getCell(8) == null || "".equalsIgnoreCase(row.getCell(8).toString())
					|| !validateEmail(row.getCell(8).toString())) {
				client.setReason("Required fields is not filled");
				return false;
			}
			else if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())
					&& !validateEmail(row.getCell(8).toString())) {
				client.setReason("Invalid Email");
				return false;
			}
			else if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
				OrgLocations orgLoc = null;
				for (OrgRelations orgRelation : orgList) {
					if (orgName.equalsIgnoreCase(orgRelation.getResponderLocationId().getOrgLocationName())) {
						orgLoc = orgRelation.getResponderLocationId();
					}
					else if (orgName.equalsIgnoreCase(orgRelation.getLocationId().getOrgLocationName())) {
						orgLoc = orgRelation.getLocationId();
					}
				}
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLoc.getId());
				UserRole userRole = null;
				userRole = userRoleService.getUserDetailsByEmailAndLocationId(row.getCell(8).toString(), orgLocation.getId());
				if (userRole == null) {
					User spocUser = userService.findByEmail(row.getCell(8).toString());
					User spocOwnerUser = userService.findByEmail(client.getOwner());
					if (spocUser != null && spocOwnerUser != null) {
						OrgSpoc orgSpoc = orgSpocService.getValidateOwnersBySpocUserIdAndSpocOwnerUserId(spocUser.getId(), spocOwnerUser.getId(), orgLocations.getId(), "");
						if (orgSpoc != null) {
							client.setReason("spoc already existed.!");
							return false;
						}
					}
				}
				else {
					client.setReason(row.getCell(8).toString()
							+ " should not your org users email");
					return false;
				}
			}
			if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
				String mobileNumber = getCellValueAsString(row.getCell(9), fname);
				if (mobileNumber != null && !"".equalsIgnoreCase(mobileNumber)) {
					isValidated = isMobileNumberValid(mobileNumber);
					if (isValidated == false) {
						client.setReason("Invalid MobileNumber");
						return isValidated;
					}
				}
			}
			if (client.getOwner() != null && !"".equalsIgnoreCase(client.getOwner())) {
				String email = client.getOwner();
				String emails[] = email.split(",");
				for (int i = 0; i < emails.length; i++) {
					if (!validateEmail(emails[i])) {
						client.setReason("Invalid Email");
						return false;
					}
					UserRole user = userRoleService.getUserDetailsByEmailAndLocationId(emails[i], orgLocation.getId());
					if (user == null) {
						StringBuilder owner = new StringBuilder();
						client.setReason(owner.append("Owner doesn't exist with email ").append(emails[i]).toString());
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * check each row validations
	 * 
	 * @param row
	 * @param fname
	 * @param client
	 * @param responderType
	 * @param orgLocation
	 * @return
	 * @throws Exception
	 */
	private boolean validateClientData(Row row, String fname, ClientImportObject client, String responderType, OrgLocations orgLocation) throws Exception
	{
		boolean isValidated;
		if (row.getRowNum() > 0) {
			if (row.getCell(0) == null || "".equalsIgnoreCase(row.getCell(0).toString())) {
				client.setReason("Required fields is not filled");
				return false;
			}
			else if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
				isValidated = isFnameLnameValid(row.getCell(0).toString());
				if (isValidated == false) {
					client.setReason("should contain above 2 char");
					return isValidated;
				}
			}
			if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
				isValidated = isValidDomainName(row.getCell(1).toString());
				if (isValidated == false) {
					client.setReason("Invalid Domain Website");
					return isValidated;
				}
			}
			if (null != row.getCell(5) && !"".equalsIgnoreCase(row.getCell(5).toString())) {
				boolean isValid = checkTierLevel(row.getCell(5).toString());
				if (isValid == false) {
					client.setReason("Tier should <=5");
					return false;
				}
			}
			// }
		}
		return true;
	}
	
	/**
	 * @author Rajashekar It will check tier validation if anybody change level
	 *         other than Tier it will break
	 * @param tier
	 * @return
	 */
	private boolean checkTierLevel(String tier)
	{
		boolean isTierValid = true;
		String[] tierArray = tier.split("-");
		int level = Integer.parseInt(tierArray[1]);
		if (level > 5) {
			isTierValid = false;
		}
		return isTierValid;
	}
	
	/**
	 * checks filed validation including spaces
	 * 
	 * @param name
	 * @return boolean
	 */
	private boolean isFnameLnameValid(String name)
	{
		boolean flag = false;
		boolean istabSpace = name.matches("\\s*");
		if (istabSpace) {
			flag = false;
		}
		else {
			if (name.length() > 2 && name.length() <= 200) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * validate email
	 * 
	 * @param email
	 * @return boolean
	 */
	private boolean validateEmail(String email)
	{
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * Handles Mobile number valid or not
	 * 
	 * @param mobileNmber
	 * @return
	 */
	private boolean isMobileNumberValid(String mobileNmber)
	{
		String phoneNumber = mobileNmber.trim();
		//String regex = "^\\+?[0-9 ()-]{10,25}$";
		String regex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
		boolean flag;
		if (phoneNumber.length() > 8 && phoneNumber.length() <= 20) {
			flag = phoneNumber.matches(regex);
		}
		else {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * prepares Client columns (Headers) for excel
	 * 
	 * @param createExcelsheet
	 * @param responderType
	 * @return
	 */
	private Sheet setClientExcelHeader(Sheet createExcelsheet, String responderType)
	{
		
		Row excelHeader = createExcelsheet.createRow(0);
		if ("Client".equalsIgnoreCase(responderType)) {
			excelHeader.createCell(0).setCellValue(Constants.CLIENT_NAME);
		}
		else {
			excelHeader.createCell(0).setCellValue(Constants.VENDOR_NAME);
		}
		excelHeader.createCell(1).setCellValue(Constants.WEBSITE);
		excelHeader.createCell(2).setCellValue(Constants.COUNTRY);
		excelHeader.createCell(3).setCellValue(Constants.SATATE);
		excelHeader.createCell(4).setCellValue(Constants.CITY);
		excelHeader.createCell(5).setCellValue(Constants.LEVEL);
		excelHeader.createCell(6).setCellValue(Constants.FIRST_NAME);
		excelHeader.createCell(7).setCellValue(Constants.LAST_NAME);
		excelHeader.createCell(8).setCellValue(Constants.EMAIL);
		excelHeader.createCell(9).setCellValue(Constants.PHONE);
		excelHeader.createCell(10).setCellValue(Constants.EXTENSION_NUMBER);
		excelHeader.createCell(11).setCellValue(Constants.STATUS);
		excelHeader.createCell(12).setCellValue(Constants.OWNER);
		excelHeader.createCell(13).setCellValue(Constants.FAILED_REASON);
		return createExcelsheet;
	}
	
	private String[] getClientNameList(Long orgLocationId) throws Exception
	{
		List<String> list = new ArrayList<>();
		List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgLocationId(orgLocationId);
		for (OrgRelations orgRelations : vandorsListByOrgLocationId) {
			if (orgLocationId.longValue() == orgRelations.getLocationId().getId().longValue()) {
				list.add(orgRelations.getResponderLocationId().getOrgLocationName());
			}
			else {
				list.add(orgRelations.getLocationId().getOrgLocationName());
			}
		}
		String[] roleList = list.toArray(new String[list.size()]);
		return roleList;
	}
	
	/**
	 * prepares client excel file when record validation failed
	 * 
	 * @param row
	 * @param createExcelsheet
	 * @param rowIndex
	 * @param fname
	 * @param client
	 * @param orgLocationId
	 * @throws Exception
	 */
	private void createClientXlsErrorSheet(Row row, Sheet createExcelsheet, int rowIndex, String fname, ClientImportObject client, Long orgLocationId) throws Exception
	{
		
		Row writeRow = createExcelsheet.createRow(rowIndex);
		if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
			writeRow.createCell(0).setCellValue(row.getCell(0).toString());
		}
		else {
			writeRow.createCell(0).setCellValue("");
		}
		if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
			writeRow.createCell(1).setCellValue(row.getCell(1).toString());
		}
		else {
			writeRow.createCell(1).setCellValue("");
		}
		if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.COUNTRY_DROPDOWN, 2));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.COUNTRY_DROPDOWN, 2, createExcelsheet));
			}
			writeRow.createCell(2).setCellValue(row.getCell(2).toString());
			writeRow.createCell(2).setCellValue(row.getCell(2).toString());
		}
		else {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.COUNTRY_DROPDOWN, 2));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.COUNTRY_DROPDOWN, 2, createExcelsheet));
			}
			writeRow.createCell(2).setCellValue("");
		}
		if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
			writeRow.createCell(3).setCellValue(row.getCell(3).toString());
		}
		else {
			writeRow.createCell(3).setCellValue("");
		}
		if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
			writeRow.createCell(4).setCellValue(row.getCell(4).toString());
		}
		else {
			writeRow.createCell(4).setCellValue("");
		}
		if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.LEVEL_DROPDOWN, 5));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.LEVEL_DROPDOWN, 5, createExcelsheet));
			}
			writeRow.createCell(5).setCellValue(row.getCell(5).toString());
		}
		else {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.LEVEL_DROPDOWN, 5));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.LEVEL_DROPDOWN, 5, createExcelsheet));
			}
			writeRow.createCell(5).setCellValue("");
		}
		if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
			writeRow.createCell(6).setCellValue(row.getCell(6).toString());
		}
		else {
			writeRow.createCell(6).setCellValue("");
		}
		if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
			writeRow.createCell(7).setCellValue(row.getCell(7).toString());
		}
		else {
			writeRow.createCell(7).setCellValue("");
		}
		if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
			writeRow.createCell(8).setCellValue(row.getCell(8).toString());
		}
		else {
			writeRow.createCell(8).setCellValue("");
		}
		if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
			writeRow.createCell(9).setCellValue(row.getCell(9).toString());
		}
		else {
			writeRow.createCell(9).setCellValue("");
		}
		
		if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
			writeRow.createCell(10).setCellValue(row.getCell(10).toString());
		}
		else {
			writeRow.createCell(10).setCellValue("");
		}
		
		
		if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.STATUS_DROPDOWN, 11));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.STATUS_DROPDOWN, 11, createExcelsheet));
			}
			writeRow.createCell(11).setCellValue(row.getCell(11).toString());
		}
		else {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.STATUS_DROPDOWN, 11));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.STATUS_DROPDOWN, 11, createExcelsheet));
			}
			writeRow.createCell(11).setCellValue("");
		}
		
		if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
			writeRow.createCell(12).setCellValue(row.getCell(12).toString());
		}
		else {
			writeRow.createCell(12).setCellValue("");
		}
		if (client.getReason() != null) {
			writeRow.createCell(13).setCellValue(client.getReason());
		}
	}
	
	/**
	 * Prepares dropdown for Role and status
	 * 
	 * @param ratePerList
	 * @param column
	 * @param fileName
	 * @return
	 */
	private DataValidation getDropDownListForCell(String[] dropDownList, int column)
	{
		CellRangeAddressList addressList = new CellRangeAddressList(1, 500, column, column);
		DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownList);
		DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
		dataValidation.setSuppressDropDownArrow(false);
		return dataValidation;
	}
	
	private XSSFDataValidation getXssfDropDownlist(String[] dropDownList, int column, Sheet sheet)
	{
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(dropDownList);
		CellRangeAddressList addressList = new CellRangeAddressList(1, 500, column, column);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
		validation.setShowErrorBox(true);
		return validation;
	}
	
	/**
	 * Checks domain is valid or not
	 * 
	 * @param domainName
	 * @return
	 */
	private boolean isValidDomainName(String webSite)
	{
		String flag = null;
		boolean isValid = true;
		if (!webSite.startsWith("http") && !webSite.startsWith("https")) {
			webSite = "http://" + webSite;
		}
		URL netUrl = null;
		try {
			netUrl = new URL(webSite);
		} catch (MalformedURLException e) {
			e.printStackTrace();;
		}
		String host = netUrl.getHost();
		if (host.startsWith("www.")) {
			if (host.length() > 3) {
				host = host.substring("www".length() + 1);
			}
			else {
				flag = "Website Duplicated";
			}
		}
		else {
			flag = "Invalid Website";
		}
		// for Blocked Websites Domains
		String[] SourceLead_BLOCKED_SOCIAL_DOMAINS = sourceLeadBlockedSocialDomains.split(",");
		if (Arrays.asList(SourceLead_BLOCKED_SOCIAL_DOMAINS).contains(host)) {
			flag = "Invalid Website";
		}
		if ("Website Duplicated".equalsIgnoreCase(flag)) {
			isValid = false;
		}
		else if ("Invalid Website".equalsIgnoreCase(flag)) {
			isValid = false;
		}
		return isValid;
	}
	
	public File getClientContactsTemplate(Long orgLocationId) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet realSheet = workbook.createSheet("clientContact");
		realSheet = setClientTemplateHeaders(realSheet);
		HSSFSheet hidden = workbook.createSheet("hidden");
		String[] vendorsList = getClientNameList(orgLocationId);
		String[] ownersList = getOwnerList(orgLocationId, Long.valueOf(Constants.TAB_CLIENT));
		for (int i = 0, length = vendorsList.length; i < length; i++) {
			String name = vendorsList[i];
			HSSFRow row = hidden.createRow(i);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(name);
		}
		org.apache.poi.ss.usermodel.Name namedCell = workbook.createName();
		namedCell.setNameName("hidden");
		if (vendorsList.length > 0) {
			namedCell.setRefersToFormula("hidden!$A$1:$A$" + vendorsList.length);
			DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
			CellRangeAddressList addressList = new CellRangeAddressList(1, 500, 0, 0);
			HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
			workbook.setSheetHidden(1, true);
			realSheet.addValidationData(getDropDownListForCell(Constants.CLIENT_CONTACTS_STATUS_DROPDOWN, 5, "xls"));
			realSheet.addValidationData(validation);
		}
		realSheet.addValidationData(getDropDownListForCell(ownersList, 6, "xls"));
		File file = new File("clients_contacts_Import.xls");
		FileOutputStream stream = new FileOutputStream(file);
		workbook.write(stream);
		stream.close();
		return file;
	}
	
	private HSSFSheet setClientTemplateHeaders(HSSFSheet sheet)
	{
		String headers[] = Constants.CLIENT_HEADERS;
		Row excelHeader = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = excelHeader.createCell(i);
			cell.setCellValue(headers[i]);
		}
		return sheet;
	}
	
	public File getVendorContactsTemplate(Long orgLocationId) throws Exception
	{
		
		// modified by prasanna vadla
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet realSheet = workbook.createSheet("vendorContact");
		realSheet = setTemplateHeaders(realSheet);
		HSSFSheet hidden = workbook.createSheet("hidden");
		String[] ownersList = getOwnerList(orgLocationId, Long.valueOf(Constants.TAB_VENDOR));
		for (int i = 0, length = ownersList.length; i < length; i++) {
			String name = ownersList[i];
			HSSFRow row = hidden.createRow(i);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(name);
		}
		org.apache.poi.ss.usermodel.Name namedCell = workbook.createName();
		namedCell.setNameName("hidden");
		if (ownersList.length > 0) {
			namedCell.setRefersToFormula("hidden!$A$1:$A$" + ownersList.length);
			DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
			CellRangeAddressList addressList = new CellRangeAddressList(1, 500, 12, 12);
			HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
			workbook.setSheetHidden(1, true);
			realSheet.addValidationData(getDropDownListForCell(Constants.COUNTRY_DROPDOWN, 2, "xls"));
			realSheet.addValidationData(getDropDownListForCell(Constants.LEVEL_DROPDOWN, 5, "xls"));
			realSheet.addValidationData(getDropDownListForCell(Constants.CLIENT_CONTACTS_STATUS_DROPDOWN, 11, "xls"));
			realSheet.addValidationData(getDropDownListForCell(Constants.CLIENT_PAYMENT_TERMS, 15, "xls"));
			realSheet.addValidationData(validation);
		}
		File file = new File("vendors_contacts_Import.xls");
		FileOutputStream stream = new FileOutputStream(file);
		workbook.write(stream);
		stream.close();
		return file;
	}
	
	private String[] getOwnerList(Long orgLocationId, Long vendortab) throws Exception
	{
		List<String> ownerList = new ArrayList<>();
		List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocationId, vendortab);
		for (User usr : userList) {
			ownerList.add(usr.getEmail());
		}
		String[] ownersList = ownerList.toArray(new String[ownerList.size()]);
		return ownersList;
	}
	
	private HSSFSheet setTemplateHeaders(HSSFSheet sheet)
	{
		String headers[] = Constants.VENDOR_HEADERS;
		Row excelHeader = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = excelHeader.createCell(i);
			cell.setCellValue(headers[i]);
		}
		return sheet;
	}
	
	private DataValidation getDropDownListForCell(String[] ratePerList, int column, String fileName)
	{
		@SuppressWarnings("deprecation")
		CellRangeAddressList addressList = new CellRangeAddressList(1, 500, column, column);
		DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(ratePerList);
		DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
		dataValidation.setSuppressDropDownArrow(false);
		return dataValidation;
	}
	
	private String[] getVendorsList(Long orgLocationId)
	{
		
		List<String> list = new ArrayList<>();
		List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocationId);
		for (OrgRelations orgRelations : vandorsListByOrgLocationId) {
			if (orgLocationId.longValue() == orgRelations.getLocationId().getId().longValue()) {
				list.add(orgRelations.getResponderLocationId().getOrgLocationName());
			}
			else {
				list.add(orgRelations.getLocationId().getOrgLocationName());
			}
		}
		String[] roleList = list.toArray(new String[list.size()]);
		return roleList;
	}
	
	private String getFileName(String file)
	{
		String finalName;
		if (file.contains("(")) {
			StringBuilder sb = new StringBuilder();
			String fileArray[] = file.split("\\.");
			String fileName1 = fileArray[0].substring(0, fileArray[0].indexOf("("));
			sb.append(fileName1).append(".").append(fileArray[1]);
			finalName = sb.toString().replaceAll("\\s", "");
		}
		else {
			finalName = file;
		}
		return finalName;
	}
	
	/**
	 * download client template excel
	 */
	@Override
	public File getClientTemplate(Long orgLocationId) throws Exception
	{
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet realSheet = workbook.createSheet("clientContact");
		realSheet = setClientTemplateHeaders(realSheet);
		HSSFSheet hidden = workbook.createSheet("hidden");
		String[] vendorsList = getClientNameList(orgLocationId);
		String[] ownersList = getOwnerList(orgLocationId, Long.valueOf(Constants.TAB_CLIENT));
		for (int i = 0, length = ownersList.length; i < length; i++) {
			String name = ownersList[i];
			HSSFRow row = hidden.createRow(i);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(name);
		}
		org.apache.poi.ss.usermodel.Name namedCell = workbook.createName();
		namedCell.setNameName("hidden");
		if (ownersList.length > 0) {
			namedCell.setRefersToFormula("hidden!$A$1:$A$" + ownersList.length);
			DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
			CellRangeAddressList addressList = new CellRangeAddressList(1, 500, 12, 12);
			HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
			workbook.setSheetHidden(1, true);
			realSheet.addValidationData(getDropDownListForCell(Constants.COUNTRY_DROPDOWN, 2, "xls"));
			realSheet.addValidationData(getDropDownListForCell(Constants.LEVEL_DROPDOWN, 5, "xls"));
			realSheet.addValidationData(getDropDownListForCell(Constants.CLIENT_CONTACTS_STATUS_DROPDOWN, 11, "xls"));
			realSheet.addValidationData(getDropDownListForCell(Constants.CLIENT_PAYMENT_TERMS, 15, "xls"));
			realSheet.addValidationData(validation);
		}
		// realSheet.addValidationData(getDropDownListForCell(ownersList,6,"xls"));
		File file = new File("clients_contacts_Import.xls");
		FileOutputStream stream = new FileOutputStream(file);
		workbook.write(stream);
		stream.close();
		return file;
		/*
		 * HSSFWorkbook workbook = new HSSFWorkbook(); HSSFSheet realSheet =
		 * workbook.createSheet("clientContact"); realSheet =
		 * setCLirntTemplateHeaders(realSheet); HSSFSheet hidden =
		 * workbook.createSheet("hidden"); org.apache.poi.ss.usermodel.Name
		 * namedCell = workbook.createName(); namedCell.setNameName("hidden");
		 * workbook.setSheetHidden(1, true);
		 * realSheet.addValidationData(getDropDownListForCell(Constants.
		 * COUNTRY_DROPDOWN,2,"xls"));
		 * realSheet.addValidationData(getDropDownListForCell(Constants.
		 * LEVEL_DROPDOWN,5,"xls"));
		 * realSheet.addValidationData(getDropDownListForCell(Constants.
		 * CLIENT_CONTACTS_STATUS_DROPDOWN,10,"xls")); File file = new
		 * File("clients_contacts_Import.xls"); FileOutputStream stream = new
		 * FileOutputStream(file); workbook.write(stream); stream.close();
		 * return file;
		 */
	}
	
	@Override
	public List<BulkClientDetails> clientExcelDataSave(MultipartFile multipartFile, User user, OrgLocations orgLocation, Org org, String isMailSent, String responderType) throws Exception
	{
		
		List<BulkClientDetails> list = new ArrayList<>();
		
		String isFileCreated = "success";
		String fn = multipartFile.getOriginalFilename();
		String fname = getFileName(fn);
		File file = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		InputStream inp = new FileInputStream(file);
		String fileExtn = GetFileExtension(fname);
		Workbook workbook; // Declare XSSF WorkBook
		Workbook wb_hssf = null; // Declare HSSF WorkBook
		Sheet sheet = null; // sheet can be used as common for XSSF and HSSF
		if ("xlsx".equalsIgnoreCase(fileExtn)) {
			// wb_xssf = new XSSFWorkbook(inp);
			workbook = WorkbookFactory.create(inp);
			wb_hssf = new XSSFWorkbook();
			logger.info("xlsx=" + workbook.getSheetName(0));
			sheet = workbook.getSheetAt(0);
		}
		if ("xls".equalsIgnoreCase(fileExtn)) {
			POIFSFileSystem fs = new POIFSFileSystem(inp);
			workbook = WorkbookFactory.create(fs);
			wb_hssf = (Workbook) new HSSFWorkbook();
			sheet = workbook.getSheetAt(0);
		}
		Sheet createExcelsheet = wb_hssf.createSheet("createContact");
		if (sheet.getLastRowNum() < 1) {
			inp.close();
		}
		Iterator rows = sheet.rowIterator(); // Now we have rows ready from the
												// sheet
		createExcelsheet = setClientExcelHeader(createExcelsheet, responderType);
		boolean isValid = isValidTemplate(rows, responderType);
		if (isValid) {
			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				if (row.getRowNum() > 0) {
					BulkClientDetails bulkClientVendors = new BulkClientDetails();
					if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
						String orgName = row.getCell(0).toString().trim();
						bulkClientVendors.setOrgName(orgName);
					}
					if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
						bulkClientVendors.setWebsite(row.getCell(1).toString());
					}
					if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
						bulkClientVendors.setCountry(row.getCell(2).toString());
					}
					if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
						bulkClientVendors.setState(row.getCell(3).toString());
					}
					if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
						bulkClientVendors.setCity(row.getCell(4).toString());
					}
					if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
						bulkClientVendors.setTier(row.getCell(5).toString());
					}
					//
					if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
						bulkClientVendors.setFirstName(row.getCell(6).toString());
					}
					if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
						bulkClientVendors.setLastName(row.getCell(7).toString());
					}
					if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
						bulkClientVendors.setEmail(row.getCell(8).toString());
					}

					if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
						String mobileNumer = getCellValueAsString(row.getCell(9), fname);
						bulkClientVendors.setPhone(mobileNumer);
					}
					
					if(row.getCell(10) !=null && !"".equalsIgnoreCase(row.getCell(10).toString())){
						String extensionNumber = getCellValueAsString(row.getCell(10), fname);
						bulkClientVendors.setExtensionNumber(extensionNumber);
					}
					
					if (row.getCell(11) != null
							&& !"".equalsIgnoreCase(row.getCell(11).toString())) {
						String status = getCellValueAsString(row.getCell(11), fname);
						bulkClientVendors.setStatus(status);
					}
					if (row.getCell(12) != null
							&& !"".equalsIgnoreCase(row.getCell(12).toString())) {
						bulkClientVendors.setOwnerEmail(row.getCell(12).toString());
					}
					else {
						bulkClientVendors.setOwnerEmail(user.getEmail());
					}
					if (row.getCell(13) != null
							&& !"".equalsIgnoreCase(row.getCell(13).toString())) {
						bulkClientVendors.setClientAddress(row.getCell(13).toString());
					}
					if (row.getCell(14) != null
							&& !"".equalsIgnoreCase(row.getCell(14).toString())) {
						bulkClientVendors.setBillingAddress(row.getCell(14).toString());
					}
					if (row.getCell(15) != null
							&& !"".equalsIgnoreCase(row.getCell(15).toString())) {
						String paymentTerms = getCellValueAsString(row.getCell(15), fname);
						bulkClientVendors.setPaymentTerm(paymentTerms);
					}
					if ("Client".equalsIgnoreCase(responderType)) {
						bulkClientVendors.setType(Constants.CLIENT);
					}
					else {
						bulkClientVendors.setType(Constants.VENDOR);
					}
					bulkClientVendors.setOrgLocationId(orgLocation.getId());
					bulkClientVendors.setCreatedBy(user.getUserCode());
					bulkClientVendors.setCreatedDate(new Date());
					bulkClientVendors.setUpdatedBy(user.getUserCode());
					bulkClientVendors.setUpdatedDate(new Date());
					bulkClientVendors.setRecordStatus("0");
//					BulkClientDetails isBulkClientVendors = clientImportDao.isRecordExist(bulkClientVendors, orgLocation.getId(), responderType);
//					if (isBulkClientVendors == null) {
//						clientImportDao.saveClientVendorExcelData(bulkClientVendors);
//					}
					list.add(bulkClientVendors);
				} /*
					 * else{ inp.close(); return "empty"; }
					 */
			}
			inp.close();
			// return isFileCreated;
		}
		else {
			isFileCreated = "invalid";
		}
//		return isFileCreated;
		if(list != null) {
			clientImportDao.updateBulkImportedClients();
		}
		return list;
	}
	
	private boolean isValidTemplate(Iterator rows, String responderType)
	{
		boolean isValid = false;
		Row row = (Row) rows.next();
		String name = "";
		String address = "";
		if (responderType.equalsIgnoreCase(Constants.CLIENT)) {
			name = Constants.CLIENT_NAME;
			address = Constants.CLIENT_ADDRESS;
		}
		else {
			name = Constants.VENDOR_NAME;
			address = Constants.VENDOR_ADDRESS;
		}
		if (row.getRowNum() == 0) {
			if (name.equalsIgnoreCase(row.getCell(0).getStringCellValue())) {
				if (Constants.WEBSITE.equalsIgnoreCase(row.getCell(1).getStringCellValue())) {
					if (Constants.country.equalsIgnoreCase(row.getCell(2).getStringCellValue())) {
						if (Constants.SATATE.equalsIgnoreCase(row.getCell(3).getStringCellValue())) {
							if (Constants.CITY.equalsIgnoreCase(row.getCell(4).getStringCellValue())) {
								if (Constants.LEVEL.equalsIgnoreCase(row.getCell(5).getStringCellValue())) {
									if (Constants.FIRST_NAME.equalsIgnoreCase(row.getCell(6).getStringCellValue())) {
										if (Constants.LAST_NAME.equalsIgnoreCase(row.getCell(7).getStringCellValue())) {
											if (Constants.EMAIL_ID_BULK_IMPORT.equalsIgnoreCase(row.getCell(8).getStringCellValue())) {
												if (Constants.PHONE_NUMBER_BULK_IMPORT.equalsIgnoreCase(row.getCell(9).getStringCellValue())) {
												if(Constants.EXTENSION_NUMBER.equalsIgnoreCase(row.getCell(10).getStringCellValue())){	
													if (Constants.STATUS.equalsIgnoreCase(row.getCell(11).getStringCellValue())) {
														if (Constants.OWNER.equalsIgnoreCase(row.getCell(12).getStringCellValue())) {
															if (address.equalsIgnoreCase(row.getCell(13).getStringCellValue())) {
																if (Constants.BILLING_ADDRESS.equalsIgnoreCase(row.getCell(14).getStringCellValue())) {
																	if (Constants.PAYMENT_TERMS.equalsIgnoreCase(row.getCell(15).getStringCellValue())) {
																		isValid = true;
																	}
																}
															}
														}
													}
												}
											  }
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return isValid;
	}
	
	@Override
	public ResponseList<BulkClientDetails> getbulkClientDetailsByOrgLocationId(List<BulkClientDetails> fileList,ClientVendorUploadDTO clientVendorUploadDTO,OrgLocations orgLocations, User user, Org org, String isMailSent, String responderType) throws Exception
	{
//		List<BulkClientDetails> importClietDetails = clientImportDao.getbulkClientDetailsByOrgLocationId(orgLocations.getId(), responderType, user);
		ResponseList<BulkClientDetails> responseList = new ResponseList<>();
		List<BulkClientDetails> duplicateClietDetails = null;
		for (BulkClientDetails clientOrVendor : fileList) {
			List<OrgRelations> spocList = null;
			duplicateClietDetails = new ArrayList<>();
			boolean isValidate = validateBulkImportedData(clientOrVendor, responderType);
			if (isValidate) {
				UserRole userRole1 = userRoleService.getUserDetailsByEmailAndLocationId(clientOrVendor.getEmail(), orgLocations.getId());
				if (userRole1 == null) {
					if (!orgLocations.getOrgLocationName().equalsIgnoreCase(clientOrVendor.getOrgName())) {
						List<OrgRelations> orgRelationsList;
						if ("Client".equalsIgnoreCase(responderType)) {
							orgRelationsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
						}
						else {
							orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
						}
						if (null != orgRelationsList && orgRelationsList.size() > 0) {
							
							boolean isSimilarOrExist = isSimilarOrgExist(orgRelationsList, orgLocations.getId(), clientOrVendor);
							if (!isSimilarOrExist) {
								for (OrgRelations orgRelations : orgRelationsList) {
									// Org Duplicate
									String orgName = "";
									if (orgRelations.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()) {
										orgName = orgRelations.getLocationId().getOrgLocationName();
									}
									if (orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
										orgName = orgRelations.getResponderLocationId().getOrgLocationName();
									}
									if (clientOrVendor.getOrgName().equalsIgnoreCase(orgName.trim())) {
										User spocUser = userService.findByEmail(clientOrVendor.getEmail());
										User spocOwnerUser = userService.findByEmail(clientOrVendor.getOwnerEmail());
										if (spocUser != null && spocOwnerUser != null) {
											List<OrgRelations> orgList;
											if ("Client".equalsIgnoreCase(responderType)) {
												orgList = orgRelationsService.getAllClientsByOrgNameOrgLocationId(clientOrVendor.getOrgName(), orgLocations.getId());
											}
											else {
												orgList = orgRelationsService.getAllVendorsListByOrgName(clientOrVendor.getOrgName(), orgLocations.getId());
											}
											OrgLocations orgLoc = null;
											for (OrgRelations orgRelation : orgList) {
												if (orgName.equalsIgnoreCase(orgRelation.getResponderLocationId().getOrgLocationName())) {
													orgLoc = orgRelation.getResponderLocationId();
												}
												else if (orgName.equalsIgnoreCase(orgRelation.getLocationId().getOrgLocationName())) {
													orgLoc = orgRelation.getLocationId();
												}
											}
											OrgLocations vendorLocations = orgLocationService.getOrgLocations(orgLoc.getId());
											OrgSpoc existedOrgSpoc = orgSpocService.getValidateOwnersBySpocUserIdAndSpocOwnerUserId(spocUser.getId(), spocOwnerUser.getId(), vendorLocations.getId(), responderType);
											if (existedOrgSpoc != null) {
//												clientOrVendor.setReason("Spoc existed");
												clientOrVendor.setFileReason("Spoc existed");
												BulkClientDetails isBulkClientVendors = clientImportDao.isRecordExist(clientOrVendor, orgLocations.getId(), responderType);
												if (isBulkClientVendors == null) {
													BulkClientDetails blk = clientImportDao.saveClientVendorExcelData(clientOrVendor);
													duplicateClietDetails.add(blk);
												}
												
											}
											else {
												UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(clientOrVendor.getEmail(), vendorLocations.getId());
												if (userRole != null) {
													List<OrgRelations> vandorsListByOrgLocationId;
													if ("Client".equalsIgnoreCase(responderType)) {
														vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgNameOrgLocationId(clientOrVendor.getOrgName(), orgLocations.getId());
													}
													else {
														vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgName(clientOrVendor.getOrgName(), orgLocations.getId());
													}
													OrgLocations orgLocation = null;
													ClientDTO clientDTO = populateJsonObjectTemp(clientOrVendor, isMailSent, vandorsListByOrgLocationId, orgLocations, clientOrVendor.getOrgName(), "contact", orgLocation,user);
													try {
														orgSpocService.addVendorContacts(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocations, orgRelations.getId().toString(), "", responderType, org);
														vandorsListByOrgLocationId.clear();
														clientOrVendor.setRecordStatus("1");
														responseList.setStatusCode("success");
//														clientImportDao.saveClientVendorExcelData(clientOrVendor);
														
//														BulkClientDetails isBulkClientVendors = clientImportDao.isRecordExist(clientOrVendor, orgLocations.getId(), responderType);
//														if (isBulkClientVendors == null) {
//															clientImportDao.saveClientVendorExcelData(clientOrVendor);
//														}
													} catch (Exception e) {
														logger.error(ExceptionUtils.getFullStackTrace(e));
													}
												}
												else {
//													clientOrVendor.setReason("Current org user");
													clientOrVendor.setFileReason("Current org user");
													BulkClientDetails blk = clientImportDao.saveClientVendorExcelData(clientOrVendor);
													duplicateClietDetails.add(blk);
												}
											}
										}
										else {
											UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(clientOrVendor.getEmail(), orgLocations.getId());
											if (userRole == null) {
												List<OrgRelations> vandorsListByOrgLocationId;
												if ("Client".equalsIgnoreCase(responderType)) {
													vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgNameOrgLocationId(clientOrVendor.getOrgName(), orgLocations.getId());
												}
												else {
													vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgName(clientOrVendor.getOrgName(), orgLocations.getId());
												}
												OrgLocations orgLocation = null;
												ClientDTO clientDTO = populateJsonObjectTemp(clientOrVendor, isMailSent, vandorsListByOrgLocationId, orgLocations, clientOrVendor.getOrgName(), "contact", orgLocation,user);
												try {
													orgSpocService.addVendorContacts(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocations, orgRelations.getId().toString(), "", responderType, org);
													vandorsListByOrgLocationId.clear();
													clientOrVendor.setRecordStatus("1");
													responseList.setStatusCode("success");
//													clientImportDao.saveClientVendorExcelData(clientOrVendor);
//													BulkClientDetails isBulkClientVendors = clientImportDao.isRecordExist(clientOrVendor, orgLocations.getId(), responderType);
//													if (isBulkClientVendors == null) {
//														clientImportDao.saveClientVendorExcelData(clientOrVendor);
//													}
												} catch (Exception e) {
													logger.error(ExceptionUtils.getFullStackTrace(e));
												}
											}
											else {
//												clientOrVendor.setReason("Current org user");
												clientOrVendor.setFileReason("Current org user");
												BulkClientDetails blk = clientImportDao.saveClientVendorExcelData(clientOrVendor);
												duplicateClietDetails.add(blk);
											}
										}
									}
									else {
										List<String> list = new ArrayList<>();
										List<OrgRelations> orgRelationsList2;
										if ("Client".equalsIgnoreCase(responderType)) {
											orgRelationsList2 = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
										}
										else {
											orgRelationsList2 = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
										}
										for (OrgRelations orgRelations2 : orgRelationsList2) {
											String orgName1 = "";
											if (orgRelations2.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()) {
												orgName1 = orgRelations2.getLocationId().getOrgLocationName();
											}
											if (orgRelations2.getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
												orgName1 = orgRelations2.getResponderLocationId().getOrgLocationName();
											}
											list.add(orgName1.toLowerCase());
										}
										if (!list.contains(clientOrVendor.getOrgName().toLowerCase())) {
											if (spocList == null) {
												logger.info("domain from email is......."
														+ clientOrVendor.getEmail());
												String domain = clientOrVendor.getEmail().split("@")[1];
												// List<OrgSpoc> spocList =
												// orgSpocService.getSpoclistByDomain(orgRelations.getId(),domain);
												spocList = orgRelationsService.getAllClientsByOrgLocationIdAndDomain(orgLocations.getId(), domain, responderType);
												if (null != spocList && spocList.size() > 0) {
													if ("Client".equalsIgnoreCase(responderType)) {
//														clientOrVendor.setReason("Similar client exist with domain");
														clientOrVendor.setFileReason("Similar client exist with domain");
													}
													else {
//														clientOrVendor.setReason("Similar vendor exist with domain");
														clientOrVendor.setFileReason("Similar vendor exist with domain");
													}
													BulkClientDetails blk = clientImportDao.saveClientVendorExcelData(clientOrVendor);
													duplicateClietDetails.add(blk);
												}
												else {
													OrgLocations mainOrgLocation = validateSourceleadOrg(clientOrVendor.getOrgName(), org);
													ClientDTO clientDTO = populateJsonObjectTemp(clientOrVendor, isMailSent, null, orgLocations, clientOrVendor.getOrgName(), "org", mainOrgLocation,user);
													try {
														clientDTO.setTemplateId(clientVendorUploadDTO.getTemplateId());
//														clientDTO.getAddUserDTO().setIsMailSent(Constants.YES);
														OrgLocations invitedOrgLocation = clientService.storeClient(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocations, responderType, org);
														clientOrVendor.setRecordStatus("1");
														responseList.setStatusCode("success");
//														clientImportDao.saveClientVendorExcelData(clientOrVendor);
														
//														BulkClientDetails isBulkClientVendors = clientImportDao.isRecordExist(clientOrVendor, orgLocations.getId(), responderType);
//														if (isBulkClientVendors == null) {
//															clientImportDao.saveClientVendorExcelData(clientOrVendor);
//														}
														
														if ("Vendor".equalsIgnoreCase(responderType)) {
															User spocUser = userDao.getUserById(invitedOrgLocation.getVendorSpocId());
															VendorOnboarding vendorOnboarding = vendorOnboardService.saveVendorOnboarding(invitedOrgLocation, orgLocations, user);
															vendorOnboardService.saveVendorOnboardForVendorBulkImports(vendorOnboarding.getId(),user,spocUser,orgLocations);
															responseList.setStatusCode("success");
														}
													} catch (Exception e) {
														logger.error(ExceptionUtils.getFullStackTrace(e));
													}
												}
											}
										}
									}
								}
							}
							else {
								if ("Client".equalsIgnoreCase(responderType)) {
//									clientOrVendor.setReason("Similar client matched with client name");
									clientOrVendor.setFileReason("Similar client matched with client name");
								}
								else {
//									clientOrVendor.setReason("Similar Vendor matched with Vendor name");
									clientOrVendor.setFileReason("Similar vendor matched with vendor name");
									
								}
								BulkClientDetails blk = clientImportDao.saveClientVendorExcelData(clientOrVendor);
								duplicateClietDetails.add(blk);
							}
						}
						else {
							
							OrgLocations mainOrgLocation = validateSourceleadOrg(clientOrVendor.getOrgName(), org);
							ClientDTO clientDTO = populateJsonObjectTemp(clientOrVendor, isMailSent, null, orgLocations, clientOrVendor.getOrgName(), "org", mainOrgLocation,user);
							try {
								clientDTO.setTemplateId(clientVendorUploadDTO.getTemplateId());
								OrgLocations invitedOrgLocation = clientService.storeClient(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocations, responderType, org);
								clientOrVendor.setRecordStatus("1");
								responseList.setStatusCode("success");
								
//								BulkClientDetails isBulkClientVendors = clientImportDao.isRecordExist(clientOrVendor, orgLocations.getId(), responderType);
//								if (isBulkClientVendors == null) {
//									clientImportDao.saveClientVendorExcelData(clientOrVendor);
//								}
								
//								clientImportDao.saveClientVendorExcelData(clientOrVendor);
								
								if ("Vendor".equalsIgnoreCase(responderType)) {
									User spocUser = userDao.getUserById(invitedOrgLocation.getVendorSpocId());
									VendorOnboarding vendorOnboarding = vendorOnboardService.saveVendorOnboarding(invitedOrgLocation, orgLocations, user);
									vendorOnboardService.saveVendorOnboardForVendorBulkImports(vendorOnboarding.getId(),user,spocUser,orgLocations);
									responseList.setStatusCode("success");
								}
								
							} catch (Exception e) {
								logger.error(ExceptionUtils.getFullStackTrace(e));
							}
						}
					}
				}
				else {
//					clientOrVendor.setReason("Current org user");
					clientOrVendor.setFileReason("Current org user");
					BulkClientDetails blk = clientImportDao.saveClientVendorExcelData(clientOrVendor);
					duplicateClietDetails.add(blk);
				}
			}
			else {
				duplicateClietDetails.add(clientOrVendor);
			}
		}
		List<BulkClientDetails> importClietDetails = clientImportDao.getbulkClientDetailsByOrgLocationId(orgLocations.getId(), responderType, user);
		responseList.setData(importClietDetails);
		return responseList;
	}
	
	private ClientDTO populateJsonObjectTemp(BulkClientDetails client, String isMailSent, List<OrgRelations> orgList, OrgLocations orgLocation, String orgName, String type, OrgLocations mainOrgLocation,User user) throws Exception
	{
		
		String roleTypeId = "";
		ClientDTO clientDTO = new ClientDTO();
		AddUserDTO addUserDTO = new AddUserDTO();
		CandidateDTO candidateDTO = new CandidateDTO();
		if (orgList != null && orgList.size() > 0) {
			clientDTO.setRoleTypeId(roleTypeId);
			addUserDTO.setRoleTypeId(roleTypeId);
			OrgRelations orgRelation = null;
			for (OrgRelations org : orgList) {
				if (orgName.equalsIgnoreCase(org.getResponderLocationId().getOrgLocationName())) {
					orgRelation = org;
				}
				else if (orgName.equalsIgnoreCase(org.getLocationId().getOrgLocationName())) {
					orgRelation = org;
				}
			}
			StringBuilder vendorName = new StringBuilder();
			if (orgLocation.getId().longValue() == orgRelation.getLocationId().getId().longValue()) {
				clientDTO.setVendorName(vendorName.append(orgRelation.getId()).append(",").append(orgRelation.getResponderLocationId().getId()).toString());
			}
			else {
				clientDTO.setVendorName(vendorName.append(orgRelation.getId()).append(",").append(orgRelation.getLocationId().getId()).toString());
			}
		}
		else {
			clientDTO.setOrgName(client.getOrgName());
		}
		if (client.getWebsite() != null) {
			clientDTO.setWebSite(client.getWebsite());
		}
		if (client.getCountry() != null) {
			clientDTO.setCountry(client.getCountry());
			addUserDTO.setCountry(client.getCountry());
			candidateDTO.setCountry(client.getCountry());
		}
		else {
			if ("India".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("India");
				addUserDTO.setCountry("India");
				candidateDTO.setCountry("India");
				
			}
			else if ("Australia".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("Australia");
				addUserDTO.setCountry("Australia");
				candidateDTO.setCountry("Australia");
			}
			else if ("United States".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("United States");
				addUserDTO.setCountry("United States");
				candidateDTO.setCountry("United States");
			}
			else if ("Singapore".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("Singapore");
				addUserDTO.setCountry("Singapore");
				candidateDTO.setCountry("Singapore");
			}
			else if ("United Kingdom".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("United Kingdom");
				addUserDTO.setCountry("United Kingdom");
				candidateDTO.setCountry("United Kingdom");
			}
			else if ("France".equals(orgLocation.getCountry())) {
				
				clientDTO.setCountry("France");
				addUserDTO.setCountry("France");
				candidateDTO.setCountry("France");
			}
		}
		if (client.getState() != null) {
			clientDTO.setState(client.getState());
		}
		if (client.getCity() != null) {
			clientDTO.setCity(client.getCity());
		}
		if (client.getFirstName() != null) {
			clientDTO.setFirstName(client.getFirstName());
			addUserDTO.setFirstName(client.getFirstName());
			candidateDTO.setFileName(client.getFirstName());
		}
		if (client.getLastName() != null) {
			clientDTO.setLastName(client.getLastName());
			addUserDTO.setLastName(client.getLastName());
			candidateDTO.setLastName(client.getLastName());
		}
		if (client.getPhone() != null) {
			clientDTO.setPhoneNumber(client.getPhone());
			addUserDTO.setPhoneNumber(client.getPhone());
			candidateDTO.setPhoneNumber(client.getPhone());
		}
		
		if(client.getExtensionNumber() !=null){
			clientDTO.setExtensionNumber(client.getExtensionNumber());
			addUserDTO.setExtensionNumber(client.getExtensionNumber());
		}
		
		if (client.getStatus() != null) {
			clientDTO.setSpocStatus(client.getStatus());
		}
	
		List<String> ownersList = new ArrayList<>();
		if (client.getOwnerEmail() != null) {
			UserRole ownerUser = userRoleService.getUserDetailsByEmailAndLocationId(client.getOwnerEmail(), orgLocation.getId());
			if(ownerUser !=null) {
				ownersList.add(ownerUser.getUser().getId().toString());
				clientDTO.setOwner(ownersList);				
			}
		}else{
			ownersList.add(user.getId().toString());
			clientDTO.setOwner(ownersList);
		}
		// if(!"org".equals(type)){
		if (client.getEmail() != null) {
			clientDTO.setEmail(client.getEmail());
			addUserDTO.setEmail(client.getEmail());
			candidateDTO.setEmail(client.getEmail());
			// }
		}
		clientDTO.setRoleTypeId("");
		if (isMailSent != null) {
			clientDTO.setIsMailSent(isMailSent);
			addUserDTO.setIsMailSent(isMailSent);
		}
		if (client.getTier() != null) {
			clientDTO.setLevel(client.getTier());
		}
		if (null != mainOrgLocation) {
			clientDTO.setOrgLocationId(mainOrgLocation.getId().toString());
		}
		if (null != client.getClientAddress() && !"".equalsIgnoreCase(client.getClientAddress())) {
			clientDTO.setAddress(client.getClientAddress());
		}
		if (null != client.getBillingAddress()
				&& !"".equalsIgnoreCase(client.getBillingAddress())) {
			clientDTO.setBillingAddress(client.getBillingAddress());
		}
		if (null != client.getPaymentTerm() && !"".equalsIgnoreCase(client.getPaymentTerm())) {
			clientDTO.setPaymentTerms(client.getPaymentTerm());
		}
		
		clientDTO.setAddUserDTO(addUserDTO);
		clientDTO.setCandidateDTO(candidateDTO);
		return clientDTO;
	}
	
	private boolean isSimilarOrgExist(List<OrgRelations> orgRelationsList2, Long orgLocationsId, BulkClientDetails client)
	{
		boolean isExist = false;
		for (OrgRelations orgRelations2 : orgRelationsList2) {
			String orgName = "";
			if (orgRelations2.getResponderLocationId().getId().longValue() == orgLocationsId.longValue()) {
				orgName = orgRelations2.getLocationId().getOrgLocationName();
			}
			if (orgRelations2.getLocationId().getId().longValue() == orgLocationsId.longValue()) {
				orgName = orgRelations2.getResponderLocationId().getOrgLocationName();
			}
			String existedClient = orgName;
			String newClient = client.getOrgName();
			if (existedClient.equalsIgnoreCase(newClient)) {
				return isExist = false;
			}
			else if (orgName.length() > 4 && client.getOrgName().length() > 4) {
				existedClient = orgName.substring(0, 4);
				newClient = client.getOrgName().substring(0, 4);
				if (existedClient.equalsIgnoreCase(newClient)) {
					return isExist = true;
				}
			}
		}
		return isExist;
	}
	
	private boolean validateBulkImportedData(BulkClientDetails client, String responderType) throws Exception
	{
		if ("Client".equalsIgnoreCase(responderType)) {
			if (client.getOrgName() == null || "".equalsIgnoreCase(client.getOrgName())) {
//				client.setReason("Client name is mandatory");
				client.setFileReason("Client name is mandatory");
				clientImportDao.saveClientVendorExcelData(client);
				return false;
			}
			else if (client.getOrgName() != null || !"".equalsIgnoreCase(client.getOrgName())) {
				boolean isValidated = isFnameLnameValid(client.getOrgName());
				if (isValidated == false) {
//					client.setReason("Client name should contain above 2 char");
					client.setFileReason("Client name should contain above 2 characters");
					clientImportDao.saveClientVendorExcelData(client);
					return isValidated;
				}
			}
		}
		else {
			if (client.getOrgName() == null || "".equalsIgnoreCase(client.getOrgName())) {
//				client.setReason("Vendor name is mandatory");
				client.setFileReason("Vendor name is mandatory");
				clientImportDao.saveClientVendorExcelData(client);
				return false;
			}
			else if (client.getOrgName() != null || !"".equalsIgnoreCase(client.getOrgName())) {
				boolean isValidated = isFnameLnameValid(client.getOrgName());
				if (isValidated == false) {
//					client.setReason("Vendor name should contain above 2 char");
					client.setFileReason("Vendor name should contain above 2 characters");
					clientImportDao.saveClientVendorExcelData(client);
					return isValidated;
				}
			}
		}
		if (client.getFirstName() == null || "".equalsIgnoreCase(client.getFirstName())) {
//			client.setReason("First Name is mandatory");
			client.setFileReason("First Name is mandatory");
			clientImportDao.saveClientVendorExcelData(client);
			return false;
		}
		if (client.getEmail() == null || "".equalsIgnoreCase(client.getEmail())) {
//			client.setReason("Email is Mandatory");
			client.setFileReason("Email is Mandatory");
			clientImportDao.saveClientVendorExcelData(client);
			return false;
		}
		if (client.getEmail() != null && !"".equalsIgnoreCase(client.getEmail())
				&& !validateEmail(client.getEmail())) {
//			client.setReason("Invalid email");
			client.setFileReason("Invalid email");
			clientImportDao.saveClientVendorExcelData(client);
			return false;
		}
		if (client.getPhone() != null && !"".equalsIgnoreCase(client.getPhone())) {
			boolean isValidated = isMobileNumberValid(client.getPhone());
			if (isValidated == false) {
//				client.setReason("Invalid moblie number");
				client.setFileReason("Invalid moblie number");
				clientImportDao.saveClientVendorExcelData(client);
				return isValidated;
			}
		}
		
		if (client.getWebsite() != null && !"".equalsIgnoreCase(client.getWebsite())) {
			boolean isValidated = isWebsiteValid(client.getWebsite());
			if (isValidated == false) {
				client.setFileReason("Invalid Website");
				clientImportDao.saveClientVendorExcelData(client);
				return isValidated;
			}
		}
		
		return true;
	}
	
	private boolean isWebsiteValid(String website) {
		try{
			if(Pattern.matches("[w]{3}[.]{1}[a-zA-Z0-9]*[.]{1}[com]{3}", website))
				return true;
			else 
				return false;
			}catch (Exception e){return false;}
	}

	@Override
	public BulkClientDetails deleteDuplicateRecord(String recordId, OrgLocations orgLocation, String responderType) throws Exception
	{
		BulkClientDetails bulkClientDetails = null;
		bulkClientDetails = clientImportDao.getbulkClientByrecordIdAndOrgLocationId(Long.parseLong(recordId), orgLocation.getId(), responderType);
		if (null != bulkClientDetails) {
			bulkClientDetails.setRecordStatus("1");
			bulkClientDetails = clientImportDao.saveClientVendorExcelData(bulkClientDetails);
		}
		return bulkClientDetails;
	}
	
	@Override
	public BulkClientDetails saveClientRecord(String recordId, OrgLocations orgLocation, Org org, User user, String responderType,List<String> templateId) throws Exception
	{
		BulkClientDetails bulkClientDetails = clientImportDao.getbulkClientByrecordIdAndOrgLocationId(Long.parseLong(recordId), orgLocation.getId(), responderType);
		OrgLocations mainOrgLocation = validateSourceleadOrg(bulkClientDetails.getOrgName(), org);
		ClientDTO clientDTO = populateJsonObjectTemp(bulkClientDetails, "Y", null, orgLocation, bulkClientDetails.getOrgName(), "org", mainOrgLocation,user);
		try {
			if("Vendor".equalsIgnoreCase(responderType)){
				clientDTO.setTemplateId(templateId);
				clientDTO.getAddUserDTO().setIsMailSent(Constants.YES);
				clientDTO.setClientSaveFromType("BulkVendorSaving");
				}
			clientService.storeClient(clientDTO , clientDTO.getAddUserDTO(), clientDTO.getCandidateDTO(), user, orgLocation, responderType, org);
			bulkClientDetails.setRecordStatus("1");
			clientImportDao.saveClientVendorExcelData(bulkClientDetails);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}
	
	@Override
	public OrgRelations getMatchedClientList(String recordId, OrgLocations orgLocation, Org org, User user, String responderType) throws Exception
	{
		BulkClientDetails bulkClientDetails = clientImportDao.getbulkClientByrecordIdAndOrgLocationId(Long.parseLong(recordId), orgLocation.getId(), responderType);
		String domain = bulkClientDetails.getEmail().split("@")[1];
		OrgRelations finalClient = new OrgRelations();
		List<OrgRelations> orgRelationsListbyClientName = new ArrayList<>();
		// with Domain
		List<OrgRelations> orgRelationsListWithDomain = orgRelationsService.getAllClientsByOrgLocationIdAndDomain(orgLocation.getId(), domain, responderType);
		List<OrgRelations> orgRelationsList;
		if ("Client".equalsIgnoreCase(responderType)) {
			orgRelationsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		}
		else {
			orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		}
		for (OrgRelations orgRelations2 : orgRelationsList) {
			String orgName = "";
			if (orgRelations2.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations2.getLocationId().getOrgLocationName();
			}
			if (orgRelations2.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations2.getResponderLocationId().getOrgLocationName();
			}
			if (orgName.equals(bulkClientDetails.getOrgName())) {
				orgRelationsListbyClientName.add(orgRelations2);
				continue;
			}
			else if ((orgName.length() > 4 && bulkClientDetails.getOrgName().length() > 4)) {
				
				String existedClient = orgName.substring(0, 4);
				String newClient = bulkClientDetails.getOrgName().substring(0, 4);
				if (existedClient.equalsIgnoreCase(newClient)) {
					orgRelationsListbyClientName.add(orgRelations2);
				}
			}
		}
		for (OrgRelations orgRelations : orgRelationsListWithDomain) {
			String orgName = "";
			if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}
			if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName + "(with Domain)");
		}
		for (OrgRelations orgRelations : orgRelationsListbyClientName) {
			String orgName = "";
			if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}
			if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			if ("Client".equalsIgnoreCase(responderType)) {
				orgRelations.setOrgName(orgName + "(with Client Name)");
			}
			else {
				orgRelations.setOrgName(orgName + "(with Vendor Name)");
			}
		}
		finalClient.setMatchedDomainClientList(orgRelationsListWithDomain);
		finalClient.setMatchedClientNameList(orgRelationsListbyClientName);
		return finalClient;
	}
	
	@Override
	public String mergeSpocWithClient(String bulkRecordId, OrgLocations orgLocation, Org org, User user, String orgRelationId, String responderType) throws Exception
	{
		
		BulkClientDetails bulkClientDetails = clientImportDao.getbulkClientByrecordIdAndOrgLocationId(Long.parseLong(bulkRecordId), orgLocation.getId(), responderType);
		OrgRelations orgRel = orgRelationsService.getOrRelationsById(Long.parseLong(orgRelationId));
		String orgName = "";
		if (orgRel.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			orgName = orgRel.getLocationId().getOrgLocationName();
		}
		if (orgRel.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			orgName = orgRel.getResponderLocationId().getOrgLocationName();
		}
		List<OrgRelations> vandorsListByOrgLocationId;
		if ("Client".equalsIgnoreCase(responderType)) {
			vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgNameOrgLocationId(orgName, orgLocation.getId());
		}
		else {
			vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgName(orgName, orgLocation.getId());
		}
		OrgLocations mainOrgLocations = null;
		ClientDTO clientDTO = populateJsonObjectTemp(bulkClientDetails, "Y", vandorsListByOrgLocationId, orgLocation, orgName, "org", mainOrgLocations,user);
		try {
			orgSpocService.addVendorContacts(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocation, orgRelationId.toString(), "", responderType, org);
			vandorsListByOrgLocationId.clear();
			bulkClientDetails.setRecordStatus("1");
			clientImportDao.saveClientVendorExcelData(bulkClientDetails);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return "success";
	}
}
