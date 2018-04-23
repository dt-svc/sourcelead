package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
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
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorImportService;

@Service
public class VendorImportServiceImpl implements VendorImportService {
	
	private static final Logger logger = LoggerFactory.getLogger(VendorImportService.class);
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
	
	@Override
	public String vendorBulkSaving(MultipartFile multipartFile, User user, OrgLocations orgLocation, Org org, String isMailSent, String responderType) throws Exception
	{
		
		boolean isValidated = false;
		boolean isClientFound = false;
		boolean isValidContact = false;
		String isFileCreated = "failure";
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
			// wb_hssf = (Workbook) new HSSFWorkbook(fs);
			workbook = WorkbookFactory.create(fs);
			wb_hssf = (Workbook) new HSSFWorkbook();
			// logger.info("xls="+workbook.getSheetName(0));
			sheet = workbook.getSheetAt(0);
		}
		Sheet createExcelsheet = wb_hssf.createSheet("createContact");
		Iterator rows = sheet.rowIterator(); // Now we have rows ready from the
												// sheet
		int count = 0;
		List<OrgRelations> vandorsListByOrgLocationId = null;
		while (rows.hasNext()) {
			ClientImportObject client = new ClientImportObject();
			Row row = (Row) rows.next();
			createExcelsheet = setClientExcelHeader(createExcelsheet, responderType);
			if (row.getRowNum() > 0) {
				isValidated = validateVendorData(row, fname, client, responderType, orgLocation);
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
						 String mobileNumer = getCellValueAsString(row.getCell(9),fname);
			    		 client.setPhone(mobileNumer);
					}
					if (row.getCell(10) != null
							&& !"".equalsIgnoreCase(row.getCell(10).toString())) {
						client.setStatus(row.getCell(10).toString());
					}
					if (row.getCell(11) != null
							&& !"".equalsIgnoreCase(row.getCell(11).toString())) {
						client.setOwner(row.getCell(11).toString());
					}
					else {
						client.setOwner(user.getEmail());
					}
					isClientFound = validateVendor(client.getClientName(), client, orgLocation, responderType);
					if (!isClientFound) {
						ClientDTO clientDTO = populateJsonObject(client, isMailSent, vandorsListByOrgLocationId, orgLocation, client.getClientName(), "org");
						try {
							clientService.storeClient(clientDTO,clientDTO.getAddUserDTO(),clientDTO.getCandidateDTO(), user, orgLocation, responderType, org);
						} catch (Exception e) {
							logger.error(ExceptionUtils.getFullStackTrace(e));
						}
					}
					isValidContact = validateVendorExeclDataImp(row, fname, client, orgLocation, responderType, client.getClientName());
					if (isValidContact) {
						vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgName(client.getClientName(), orgLocation.getId());
						String email = client.getOwner();
						String emails[] = email.split(",");
						for (int i = 0; i < emails.length; i++) {
							UserRole ownerUser = userRoleService.getUserDetailsByEmailAndLocationId(emails[i], orgLocation.getId());
							Long orgRelationId = 0L;
							OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(row.getCell(8).toString().trim(), orgRelationId, ownerUser.getId(), Constants.RECORD_ACTIVE);
							if (orgSpoc == null) {
								if (ownerUser.getId() != null) {
									client.setOwnerUserId(ownerUser.getUser().getId());
									ClientDTO clientDTO = populateJsonObject(client, isMailSent, vandorsListByOrgLocationId, orgLocation, client.getClientName(), "contact");
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
							createVendorErrorSheet(row, createExcelsheet, count, fname, client, orgLocation.getId());
						}
					}
				}
			}
			else {
				if (!isValidated && row.getRowNum() > 0) {
					count++;
					createVendorErrorSheet(row, createExcelsheet, count, fname, client, orgLocation.getId());
				}
			}
			if (count > 0) {
				FileOutputStream fos = null;
				if (!fname.endsWith("xls")) {
					fos = new FileOutputStream("vendor_error.xlsx");
					isFileCreated ="vendor_error.xlsx";
				}
				else {
					fos = new FileOutputStream("vendor_error.xls");
					isFileCreated = "vendor_error.xls";
				}
				wb_hssf.write(fos);
				fos.close();
			}
//			if (count > 0) {
//				if (!fname.endsWith("xlsx")) {
//					isFileCreated = "vendor_error.xls";
//				}
//				else {
//					isFileCreated = "vendor_error.xlsx";
//				}
//			}
			else {
				isFileCreated = "failed";
			}
		}
		inp.close();
		return isFileCreated;
	}
	
	/**
	 * to get file name
	 * 
	 * @param file
	 * @return
	 */
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
		excelHeader.createCell(10).setCellValue(Constants.STATUS);
		excelHeader.createCell(11).setCellValue(Constants.OWNER);
		excelHeader.createCell(12).setCellValue(Constants.FAILED_REASON);
		return createExcelsheet;
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
	private boolean validateVendorData(Row row, String fname, ClientImportObject client, String responderType, OrgLocations orgLocation) throws Exception
	{
		boolean isValidated;
		if (row.getRowNum() > 0) {
			if (row.getCell(0) == null || "".equalsIgnoreCase(row.getCell(0).toString())) {
				client.setReason("Required fields is not filled");
				return false;
			}
			else if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
				isValidated = isFnameLnameValid(row.getCell(0).toString().trim());
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
			// if(row.getCell(2) == null){
			// client.setReason("Country is required");
			// return false;
			// }
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
	 * check Clinet is duplicate or not
	 * 
	 * @param email
	 * @param client
	 * @param responderType
	 * @return boolean
	 */
	private boolean validateVendor(String clientName, ClientImportObject client, OrgLocations orgLocation, String responderType)
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
	 * prepares json for client saving
	 * 
	 * @param clientObject
	 * @param isMailSent
	 * @param orgName
	 * @param user
	 * @param string
	 * @param vendorOrg
	 * @param ownerId
	 * @return
	 * @throws JSONException
	 */
	private ClientDTO populateJsonObject(ClientImportObject clientObject, String isMailSent, List<OrgRelations> orgList, OrgLocations orgLocation, String orgName, String type) throws JSONException
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
	private boolean validateVendorExeclDataImp(Row row, String fname, ClientImportObject client, OrgLocations orgLocation, String responderType, String orgName) throws Exception
	{
		boolean isValidated;
		List<OrgRelations> orgList = null;
		if (null != row.getCell(0)) {
			orgList = orgRelationsService.getAllVendorsListByOrgName(client.getClientName(), orgLocation.getId());
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
					// if(orgLocation.getId() ==
					// orgRelation.getLocationId().getId() ){
					// userRole =
					// userRoleService.getUserDetailsByEmailAndLocationId(row.getCell(8).toString(),
					// orgRelation.getLocationId().getId());
					// }else{
					// userRole =
					// userRoleService.getUserDetailsByEmailAndLocationId(row.getCell(8).toString(),
					// orgRelation.getResponderLocationId().getId());
					// }
					User spocUser = userService.findByEmail(row.getCell(8).toString());
					User spocOwnerUser = userService.findByEmail(client.getOwner());
					if (spocUser != null && spocOwnerUser != null) {
						OrgSpoc orgSpoc = orgSpocService.getValidateOwnersByVendorUserIdAndSpocOwnerUserId(spocUser.getId(), spocOwnerUser.getId(), orgLocations.getId());
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
			/*
			 * if (row.getCell(11) == null ||
			 * "".equalsIgnoreCase(row.getCell(11).toString())) {
			 * client.setReason("Required fields is not filled"); return false;
			 * }else
			 */ if (client.getOwner() != null && !"".equalsIgnoreCase(client.getOwner())) {
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
	private void createVendorErrorSheet(Row row, Sheet createExcelsheet, int rowIndex, String fname, ClientImportObject client, Long orgLocationId) throws Exception
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
		//
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
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.STATUS_DROPDOWN, 10));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.STATUS_DROPDOWN, 10, createExcelsheet));
			}
			writeRow.createCell(10).setCellValue(row.getCell(10).toString());
		}
		else {
			if (!fname.endsWith("xlsx")) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.STATUS_DROPDOWN, 10));
			}
			else {
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.STATUS_DROPDOWN, 10, createExcelsheet));
			}
			writeRow.createCell(10).setCellValue("");
		}
		if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
			
//			String[] ownersList = getOwnerList(orgLocationId, Long.valueOf(Constants.TAB_VENDOR));
//			if (null != ownersList) {
//				if (!fname.endsWith("xlsx")) {
//					createExcelsheet.addValidationData(getDropDownListForCell(ownersList, 11));
//				}
//				else {
//					createExcelsheet.addValidationData(getXssfDropDownlist(ownersList, 11, createExcelsheet));
//				}
//			}
			writeRow.createCell(11).setCellValue(row.getCell(11).toString());
		}
		else {
//			String[] ownersList = getOwnerList(orgLocationId, Long.valueOf(Constants.TAB_VENDOR));
//			if (null != ownersList) {
//				if (!fname.endsWith("xlsx")) {
//					createExcelsheet.addValidationData(getDropDownListForCell(ownersList, 11));
//				}
//				else {
//					createExcelsheet.addValidationData(getXssfDropDownlist(ownersList, 11, createExcelsheet));
//				}
//			}
			writeRow.createCell(11).setCellValue("");
		}
		// if (row.getCell(11) != null &&
		// !"".equalsIgnoreCase(row.getCell(11).toString())) {
		// writeRow.createCell(11).setCellValue(row.getCell(11).toString());
		// } else {
		// writeRow.createCell(11).setCellValue("");
		// }
		if (client.getReason() != null) {
			writeRow.createCell(12).setCellValue(client.getReason());
		}
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
		String regex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
		boolean flag;
		if (phoneNumber.length() > 9 && phoneNumber.length() <= 20) {
			flag = phoneNumber.matches(regex);
		}
		else {
			flag = false;
		}
		return flag;
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
	
	/**
	 * to add Xssf dropdowns
	 * 
	 * @param dropDownList
	 * @param column
	 * @param sheet
	 * @return
	 */
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
	 * to get owner emails
	 * 
	 * @param orgLocationId
	 * @param vendortab
	 * @return
	 * @throws Exception
	 */
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
}
