package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
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
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientImportsService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.web.ClientUploadController;
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
	
	@Override
	public String clientBulkSaving(MultipartFile multipartFile,User user,OrgLocations orgLocation,Org  org) throws IOException, JSONException {
		
//		  String fname = "/root/Downloads/Clients_import_template.xls"; // or  "C:\\Test.xls" C:\\SDI-XL.xls
		 String fname = multipartFile.getOriginalFilename();
		  File file = new File(multipartFile.getOriginalFilename());
		  multipartFile.transferTo(file);
//		 FileInputStream fis = new FileInputStream(file);
		  
		  
		  
		  InputStream inp = new FileInputStream(file);
	      String fileExtn = GetFileExtension(fname);
	      Workbook wb_xssf; //Declare XSSF WorkBook
	      Workbook wb_hssf; //Declare HSSF WorkBook
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
	      Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
	      int i = sheet.getPhysicalNumberOfRows();
	      logger.info("================="+i);
	     List<ClientImportObject> clientList = new ArrayList<ClientImportObject>();
	     while (rows.hasNext()) 
		      { 
			       ClientImportObject client = new ClientImportObject();
			       Row row = (Row) rows.next();
			       logger.info("****************"+row.getLastCellNum());
			
			       if(fname.contains("contacts")){
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
					    	   
					    	   clientList.add(client);   
			    	   }
			         }else{
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
							   clientList.add(client);
				       }
				     
				       
			       }
		      }
		  inp.close();
		  for(ClientImportObject clientObject :clientList){
			  JSONObject jsonObj = populateJsonObject(clientObject);
			  try {
				clientService.storeClient(jsonObj, user, orgLocation,"Client",org);
				} catch (Exception e) {
					e.printStackTrace();
				}
		  }
		  logger.info("*********************"+clientList.size());
		return "success";
	}
	
	private static String GetFileExtension(String fname2)
	  {
	      String fileName = fname2;
	      String ext="";
	      int mid= fileName.lastIndexOf(".");
	      ext=fileName.substring(mid+1,fileName.length());
	      return ext;
	  }

	  
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
	  
	  private JSONObject populateJsonObject(ClientImportObject clientObject) throws JSONException {
		  
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
		  
		 return jsonObj;
	}
}
