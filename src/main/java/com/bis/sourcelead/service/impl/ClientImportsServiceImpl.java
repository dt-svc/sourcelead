package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.beans.ClientImportObject;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientImportsService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RolesService;
/**
 * 
 * @author Rajashekar
 * @Date 13th Sep 2016
 */
@Service
public class ClientImportsServiceImpl implements ClientImportsService{
	private static final Logger logger = LoggerFactory.getLogger(ClientImportsServiceImpl.class);
    
	@Autowired
    ClientService clientService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	OrgService orgService;
	
	@Override
	public String clientBulkSaving(MultipartFile multipartFile,User user,OrgLocations orgLocation,Org  org,String isMailSent,String responderType) throws IOException, JSONException {
		
		  boolean isValidated = false;
		  boolean isUserFound = false;
		  
		  String isFileCreated = "failure";
		 String fname = multipartFile.getOriginalFilename();
		  File file = new File(multipartFile.getOriginalFilename());
		  multipartFile.transferTo(file);
		  
		  InputStream inp = new FileInputStream(file);
	      String fileExtn = GetFileExtension(fname);
	      Workbook wb_xssf; //Declare XSSF WorkBook
	      Workbook wb_hssf = null ; //Declare HSSF WorkBook
	      Sheet sheet = null; // sheet can be used as common for XSSF and HSSF
	      
	 
			
	      if (fileExtn.equalsIgnoreCase("xlsx"))
	      {
		       wb_xssf = new XSSFWorkbook(inp);
		       logger.info("xlsx="+wb_xssf.getSheetName(0));
		      sheet = wb_xssf.getSheetAt(0);
	      }
	      if (fileExtn.equalsIgnoreCase("xls"))
	      {
		  POIFSFileSystem fs = new POIFSFileSystem(inp);
	    	  wb_hssf = (Workbook) new HSSFWorkbook(fs);
	    	  logger.info("xls="+wb_hssf.getSheetName(0));
	    	  sheet = wb_hssf.getSheetAt(0);
	      }
	      Sheet createExcelsheet = wb_hssf.createSheet("createContact");
	 			
	      Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
	      int count = 0;
	     List<ClientImportObject> clientList = new ArrayList<ClientImportObject>();
	     while (rows.hasNext()) 
		      { 
			       ClientImportObject client = new ClientImportObject();
			       Row row = (Row) rows.next();
			       if(fname.contains("contacts")){
			    	   createExcelsheet = setContactExcelHeader(createExcelsheet,responderType);
			    	   if (row.getRowNum() > 0) {
							isValidated = validateContactsExeclData(row,fname,client,orgLocation,responderType);
						}
			    	   if(isValidated){
			    		   if(row.getRowNum() > 0){
				    		   
				    		   if(row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())){
								   	  client.setClientName(row.getCell(0).toString());
								    }
						    	   if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())){
								   	  client.setFirstName(row.getCell(1).toString());
								   }
						    	   if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())){
							    	  client.setLastName(row.getCell(2).toString());
							       }
						    	   if(row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())){
							    	  client.setEmailid(row.getCell(3).toString());
							        }
						    	   if(row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())){
								      client.setRole(row.getCell(4).toString());
								    }
						    	   if(row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())){
						    		   String mobileNumer = getCellValueAsString(row.getCell(5),fname);
						    		   client.setPhone(mobileNumer);
						    	   }
						    	   if(row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())){
						    		   client.setOwner(row.getCell(6).toString());   
						    	   }
						    	   if(row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())){
						    		   client.setOwner(row.getCell(7).toString()); 
						    	   }
						    	   isUserFound = validateContactPerson(row.getCell(3).toString(),client);
						    	   if(isUserFound){
						    		   if (row.getRowNum() > 0) {
						    			   count++;
											createContactXlsSheet(row, createExcelsheet,count, fname,client);
										}
						    	   }else{
						    		   JSONObject jsonObj = populateJsonObject(client,isMailSent);
						    			try {
											clientService.storeClient(jsonObj, user, orgLocation,responderType,org);
										} catch (Exception e) {
											e.printStackTrace();
										}
						    	   }
				    	   }
			    	   }else{
			    		   if (row.getRowNum() > 0) {
			    			   count++;
								createContactXlsSheet(row, createExcelsheet,count, fname,client);
							}
			    	   }
			         }else{
			        	 createExcelsheet = setClientExcelHeader(createExcelsheet,responderType);
			        	 if (row.getRowNum() > 0) {
								isValidated = validateClientExeclData(row,fname,client);
							}
			        	 if(isValidated){
			        		   if(row.getRowNum() > 0){
						    	      if(row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())){
								    	  client.setClientName(row.getCell(0).toString());
								       }
									   if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())){
										   client.setWebSite(row.getCell(1).toString());  
									   }
									   if(row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())){
										   client.setCountry(row.getCell(2).toString());
									   }
									   if(row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())){
										   client.setState(row.getCell(3).toString());
									   }
									   if(row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())){
										   client.setCity(row.getCell(4).toString());
									   }  
									   if(row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())){
										   client.setLevel(row.getCell(5).toString());
									   }  
									   isUserFound = validateClient(row.getCell(0).toString(), client,orgLocation,responderType);
									   
									   if(isUserFound){
							    		   if (row.getRowNum() > 0) {
							    			   count++;
												createClientXlsSheet(row, createExcelsheet,count, fname,client);
											}
							    	   }else{
							    		   JSONObject jsonObj = populateJsonObject(client,isMailSent);
							    			try {
												clientService.storeClient(jsonObj, user, orgLocation,responderType,org);
											} catch (Exception e) {
												e.printStackTrace();
											}
							    	   }
						       } 
			        	 }else{
			        		 if (row.getRowNum() > 0) {
				    			   count++;
									createClientXlsSheet(row, createExcelsheet,count, fname,client);
								}
			        	 }
			       }
			       if (!isValidated || isUserFound) {
						FileOutputStream fos = new FileOutputStream("FailureUsersForCheck.xls");
						wb_hssf.write(fos);
						fos.close();
					}
					if(count > 0){
						isFileCreated = "FailureUsersForCheck.xls";
					}else{
						isFileCreated = "failed";
					}
		      }
		  inp.close();
		return isFileCreated;
	}
	/**
	 * check person is duplicate or not 
	 * @param email
	 * @param client
	 * @return boolean
	 */
	private boolean validateContactPerson(String email, ClientImportObject client) {
		User user = userDao.findByEmail(email);
		if(user != null){
			client.setReason("duplicate data");
			return true;
		}
		return false;
	}
	
	/**
	 * check Clinet is duplicate or not 
	 * @param email
	 * @param client
	 * @param responderType 
	 * @return boolean
	 */
	private boolean validateClient(String clientName, ClientImportObject client,OrgLocations orgLocation, String responderType) {
		
	    boolean isCLientExist = false;
		try {
			isCLientExist =  orgService.isClientNameValid(clientName,orgLocation.getId(),responderType);
			if(isCLientExist){
				client.setReason("duplicate client");
				isCLientExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return isCLientExist;
	}
	
/**
 * Return File extension
 * @param fname2
 * @return
 */
	private static String GetFileExtension(String fname2)
	  {
	      String fileName = fname2;
	      String ext="";
	      int mid= fileName.lastIndexOf(".");
	      ext=fileName.substring(mid+1,fileName.length());
	      return ext;
	  }

	/**
	 * Returns cell value as String  
	 * @param cell
	 * @param fileName
	 * @return String
	 */
	  private String getCellValueAsString(Cell cell, String fileName) {
			String cellValue = "";
			if(fileName.toLowerCase().endsWith("xls")){
					HSSFCell hssfCell = (HSSFCell) cell;
					if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						hssfCell.setCellType(Cell.CELL_TYPE_STRING);
						String hssfCellValue = hssfCell.getStringCellValue();
						if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
							cellValue = hssfCellValue;
						} 
					}else{
						cellValue = cell.getStringCellValue();
					}
				}else{
					XSSFCell xssfCell = (XSSFCell) cell;
					if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						xssfCell.setCellType(Cell.CELL_TYPE_STRING);
						String xssfCellValue = xssfCell.getStringCellValue();
						if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
							cellValue = xssfCellValue;
						} 
					}else{
						cellValue = cell.getStringCellValue();
					}
				}
				return cellValue;
			}
	  /**
	   * prepares json for client saving
	   * @param clientObject
	   * @param isMailSent
	   * @return
	   * @throws JSONException
	   */
	  private JSONObject populateJsonObject(ClientImportObject clientObject,String isMailSent) throws JSONException {
		  
		  JSONObject jsonObj = new JSONObject(); 
		  jsonObj.put("orgName", clientObject.getClientName());
		  if(clientObject.getWebSite() != null){
			  jsonObj.put("webSite",clientObject.getWebSite());
		  }
		  if(clientObject.getCountry() != null){
			  jsonObj.put("country",clientObject.getCountry());
		  }
		  if(clientObject.getState() != null){
			  jsonObj.put("state",clientObject.getState());
		  }
		  if(clientObject.getCity() != null){
			  jsonObj.put("city",clientObject.getCity());
		  }
		  if(clientObject.getFirstName() != null){
			  jsonObj.put("firstName",clientObject.getFirstName());
		  }
		  if(clientObject.getLastName() != null){
			  jsonObj.put("lastName",clientObject.getLastName());  
		  }
		  if(clientObject.getPhone() != null){
			  jsonObj.put("phoneNumber",clientObject.getPhone());
		  }
		  if(clientObject.getStatus() != null){
			  jsonObj.put("spocStatus",clientObject.getStatus()); 
		  }
		  if(clientObject.getOwner() != null){
			  StringBuilder sb = new StringBuilder();
			  sb.append("[");
			  String [] array = clientObject.getOwner().split(",");
				 for(int i=0;i<array.length;i++){
					 User user = userDao.findByEmail(array[i]);
					 
					 sb.append("\"" + user.getId() + "\"");
					 sb.append(",");
				 }
				 sb.append("]");
				 sb.deleteCharAt(sb.lastIndexOf(","));
				 jsonObj.put("owner",sb.toString());
				 logger.info(sb.toString());
				 
		  }
		  if(clientObject.getEmailid() != null){
			  jsonObj.put("email",clientObject.getEmailid());
			 
		  }
		  if(clientObject.getRole() != null){
			  try {
				Roles role = rolesService.getRolesByDisplayName(clientObject.getRole());
				  jsonObj.put("roleType",role.getId());  
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		  }
		  if(isMailSent != null){
			  jsonObj.put("isMailSent",isMailSent);
			 
		  }
		  
		  if(clientObject.getLevel() != null){
			  jsonObj.put("level",clientObject.getLevel()); 
		  }
		  
		 return jsonObj;
	}
	  
	 /**
	  * validates the row data  
	  * @param row
	  * @param fname
	  * @param client
	 * @param responderType 
	 * @param orgLocation 
	  * @return boolean
	  */
	  private boolean validateContactsExeclData(Row row, String fname, ClientImportObject client, OrgLocations orgLocation, String responderType) {
		  boolean isValidated; 
		  if (row.getRowNum() > 0) { 
			  if (row.getCell(0) == null || "".equalsIgnoreCase(row.getCell(0).toString())) {
					client.setReason("Required fields is not filled");
					return false;
				}else if(row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())){
					isValidated = isFnameLnameValid(row.getCell(0).toString());
					if(isValidated){
						boolean found = validateClient(row.getCell(0).toString(), client,orgLocation,responderType);
						if(found == false){
							client.setReason(responderType+" does not exist");
							return false;
						}
					}else{
						client.setReason("should contain above 2 char");
						return isValidated;
					}
				}
			  
			  if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
					client.setReason("Required fields is not filled");
					return false;
				}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())){
					isValidated = isFnameLnameValid(row.getCell(1).toString());
					if(isValidated == false){
						client.setReason("should contain above 2 char");
						return isValidated;
					}
				}
			  
			  if (row.getCell(4) == null || "".equalsIgnoreCase(row.getCell(4).toString())) {
					client.setReason("Required fields is not filled");
					return false;
				}
			  
			  
			  if (row.getCell(3) == null || "".equalsIgnoreCase(row.getCell(3).toString()) || !validateEmail(row.getCell(3).toString())) {
					client.setReason("Required fields is not filled");
					return false;
				}else if(row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString()) && !validateEmail(row.getCell(3).toString())){
					client.setReason("Invalid Email");
					return false;
				}
			  
			  if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
					String mobileNumber = getCellValueAsString(row.getCell(5), fname);
					if (mobileNumber != null && !"".equalsIgnoreCase(mobileNumber)) {
						isValidated = isMobileNumberValid(mobileNumber);
						if (isValidated == false) {
							client.setReason("Invalid MobileNumber");
							return isValidated;
						}
					}
				}
			  
			  if (row.getCell(7) == null || "".equalsIgnoreCase(row.getCell(7).toString())) {
					client.setReason("Required fields is not filled");
					return false;
				}else if(row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())){
					boolean isUserFound = false;
					String email = row.getCell(7).toString();
					String emails [] = email.split(",");
					for(int i=0;i<emails.length;i++){
						if(!validateEmail(emails[i])){
							client.setReason("Invalid Email");
							return false;
						}
						 isUserFound = validateContactPerson(emails[i],client);
						 if(isUserFound == false){
								client.setReason("Owner doesn't exist with email "+emails[i]);
					            return false;
							}
				}
			  
		  }
		  }
		return true;
		}
	  
	  
	  private boolean validateClientExeclData(Row row, String fname, ClientImportObject client) {
		  boolean isValidated; 
		  if (row.getRowNum() > 0) { 
			  if (row.getCell(0) == null || "".equalsIgnoreCase(row.getCell(0).toString())) {
					client.setReason("Required fields is not filled");
					return false;
				}else if(row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())){
					isValidated = isFnameLnameValid(row.getCell(0).toString());
					if(isValidated == false){
						client.setReason("should contain above 2 char");
						return isValidated;
					}
				}
			  
			  if (row.getCell(1) != null || !"".equalsIgnoreCase(row.getCell(1).toString())) {
				  isValidated = isValidDomainName(row.getCell(1).toString());
				  if(isValidated == false){
						client.setReason("Invalid Domain Website");
						return isValidated;
					}
				}
			  
		  }
			return true;
		}
	  
	  
	  /**
	   * checks filed validation including spaces
	   * @param name
	   * @return boolean
	   */
	  private boolean isFnameLnameValid(String name){
			boolean flag = false;
			boolean istabSpace = name.matches("\\s*");
			if(istabSpace){
				flag = false;
			}else{
				if(name.length() > 2 && name.length() <= 200){
					flag = true;
				}
		}
			return flag;
		}
	  
	  /**
	   * validate email
	   * @param email
	   * @return boolean
	   */
	  	private boolean validateEmail(String email) {
	  		Pattern pattern = Pattern.compile(
	  				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	  		Matcher matcher = pattern.matcher(email);
	  		return matcher.matches();
	  	}
	  	
	  	/**
	  	 * Handles Mobile number valid or not
	  	 * @param mobileNmber
	  	 * @return
	  	 */
	  		private boolean isMobileNumberValid(String mobileNmber){
	  			String regex = "([\\.\\-\\s+\\/()]*[0-9][\\.\\-\\s+\\/()]*){8,15}";
	  			boolean flag;
	  			if(mobileNmber.length() > 9 && mobileNmber.length() <= 20){
	  				flag = mobileNmber.matches(regex);
	  			}else{
	  				flag=false;
	  			}
	  			return flag;
	  		}
	  		
	  		/**
	  		 * prepares Contacts columns (Headers) for excel
	  		 * @param createExcelsheet
	  		 * @return
	  		 */
		private Sheet setContactExcelHeader(Sheet createExcelsheet,String responderType) {
			
			Row excelHeader = createExcelsheet.createRow(0);
			if("Client".equalsIgnoreCase(responderType)){
				excelHeader.createCell(0).setCellValue(Constants.CLIENT_NAME);	
			}else{
				excelHeader.createCell(0).setCellValue(Constants.VENDOR_NAME);	
			}
			excelHeader.createCell(1).setCellValue(Constants.FIRST_NAME);
			excelHeader.createCell(2).setCellValue(Constants.LAST_NAME);
			excelHeader.createCell(3).setCellValue(Constants.EMAIL);
			excelHeader.createCell(4).setCellValue(Constants.ROLE);
			excelHeader.createCell(5).setCellValue(Constants.PHONE);
			excelHeader.createCell(6).setCellValue(Constants.STATUS);
			excelHeader.createCell(7).setCellValue(Constants.OWNER);
			excelHeader.createCell(8).setCellValue(Constants.FAILED_REASON);
			return createExcelsheet;
		}
		
		
		
		
		/**
  		 * prepares Client columns (Headers) for excel
  		 * @param createExcelsheet
		 * @param responderType 
  		 * @return
  		 */
	private Sheet setClientExcelHeader(Sheet createExcelsheet, String responderType) {
		
		Row excelHeader = createExcelsheet.createRow(0);
		if("Client".equalsIgnoreCase(responderType)){
			excelHeader.createCell(0).setCellValue(Constants.CLIENT_NAME);
		}else{
			excelHeader.createCell(0).setCellValue(Constants.VENDOR_NAME);	
		}
		excelHeader.createCell(1).setCellValue(Constants.WEBSITE);
		excelHeader.createCell(2).setCellValue(Constants.COUNTRY);
		excelHeader.createCell(3).setCellValue(Constants.SATATE);
		excelHeader.createCell(4).setCellValue(Constants.CITY);
		excelHeader.createCell(5).setCellValue(Constants.LEVEL);
		excelHeader.createCell(6).setCellValue(Constants.FAILED_REASON);
		return createExcelsheet;
	}
		/**
		 * prepares contact excel file when record validation failed
		 * @param row
		 * @param createExcelsheet
		 * @param rowIndex
		 * @param fname
		 * @param client
		 */
		private void createContactXlsSheet(Row row, Sheet createExcelsheet, int rowIndex ,String fname, ClientImportObject client) {
			Row writeRow = createExcelsheet.createRow(rowIndex);
			
			if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
		        writeRow.createCell(0).setCellValue(row.getCell(0).toString());
			} else {
				writeRow.createCell(0).setCellValue("");
			}
			if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
		        writeRow.createCell(1).setCellValue(row.getCell(1).toString());
			} else {
				writeRow.createCell(1).setCellValue("");
			}
			if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
			    writeRow.createCell(2).setCellValue(row.getCell(2).toString());
			} else {
				 writeRow.createCell(2).setCellValue("");
			}
			if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
				 writeRow.createCell(3).setCellValue(row.getCell(3).toString());
			} else {
				 writeRow.createCell(3).setCellValue("");
			}
			if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.ROLE_DROPDOWN,4,fname));
				writeRow.createCell(4).setCellValue(row.getCell(4).toString());
			} else {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.ROLE_DROPDOWN,4,fname));
			    writeRow.createCell(4).setCellValue("");
			}
			if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
				String mobileNumber = getCellValueAsString(row.getCell(5), fname);
			      writeRow.createCell(5).setCellValue(mobileNumber);
			} else {
			    writeRow.createCell(5).setCellValue("");
			}
			if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.STATUS_DROPDOWN,6,fname));
				writeRow.createCell(6).setCellValue(row.getCell(6).toString());
			} else {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.STATUS_DROPDOWN,6,fname));
			    writeRow.createCell(6).setCellValue("");
			}
			if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
				writeRow.createCell(7).setCellValue(row.getCell(7).toString());
			} else {
			    writeRow.createCell(7).setCellValue("");
			}
			if (client.getReason() !=null) {
				writeRow.createCell(8).setCellValue(client.getReason());
			} 
		}
		
		
		/**
		 * prepares client excel file when record validation failed
		 * @param row
		 * @param createExcelsheet
		 * @param rowIndex
		 * @param fname
		 * @param client
		 */
		private void createClientXlsSheet(Row row, Sheet createExcelsheet, int rowIndex ,String fname, ClientImportObject client) {
			Row writeRow = createExcelsheet.createRow(rowIndex);
			
			if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
		        writeRow.createCell(0).setCellValue(row.getCell(0).toString());
			} else {
				writeRow.createCell(0).setCellValue("");
			}
			if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
		        writeRow.createCell(1).setCellValue(row.getCell(1).toString());
			} else {
				writeRow.createCell(1).setCellValue("");
			}
			if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
			    writeRow.createCell(2).setCellValue(row.getCell(2).toString());
			} else {
				 writeRow.createCell(2).setCellValue("");
			}
			if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
				 writeRow.createCell(3).setCellValue(row.getCell(3).toString());
			} else {
				 writeRow.createCell(3).setCellValue("");
			}
			if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
				writeRow.createCell(4).setCellValue(row.getCell(4).toString());
			} else {
			    writeRow.createCell(4).setCellValue("");
			}
			if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.LEVEL_DROPDOWN,5,fname));
				writeRow.createCell(4).setCellValue(row.getCell(4).toString());
			} else {
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.LEVEL_DROPDOWN,5,fname));
			    writeRow.createCell(5).setCellValue("");
			}
			if (client.getReason() !=null) {
				writeRow.createCell(6).setCellValue(client.getReason());
			} 
		}
		
/**
 * Prepares dropdown for Role and status		
 * @param ratePerList
 * @param column
 * @param fileName
 * @return
 */
		private DataValidation getDropDownListForCell(String[] ratePerList, int column, String fileName) {
			@SuppressWarnings("deprecation")
			  CellRangeAddressList addressList = new CellRangeAddressList(1,500,column,column);
			  DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(ratePerList);
			  DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
			  dataValidation.setSuppressDropDownArrow(false);
			  return dataValidation;
//			  XSSFDataValidation
	}
		
		
		private boolean isValidDomainName(String domainName){
			Pattern pDomainNameOnly = Pattern.compile("^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$");
			return pDomainNameOnly.matcher(domainName).find();
		}
		
		
		
}
