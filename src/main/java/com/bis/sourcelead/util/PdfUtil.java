package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.UserRole;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PdfUtil {
	private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
	public File createPdfTable(java.util.List<UserRole> activeList,
			java.util.List<UserRole> inactiveList, java.util.List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,
			String userState, String orgName){
		String[] headers = Constants.USER_EXCEL_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    File file = new File("users_"+dateFormat.format(new Date())+".pdf");
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
            Paragraph para = new Paragraph("Organization : " +orgName);
            document.add(para);
	        
	        int inActiveCount = 0; 
            int activeCount = 0; 
            int notLoggedInCount = 0; 
	        List orderedList = new List(List.ORDERED);
	        
	        if("ACTIVE".equalsIgnoreCase(userState)){
//				 cell2.setCellValue(userRolesList.size());
	        	activeCount = userRolesList.size();
			 }else{
				 if(activeList !=null){
//				 cell2.setCellValue(activeList.size());
					 activeCount = activeList.size();
				 }
			 }
	        if("INACTIVE".equalsIgnoreCase(userState)){
//				 cell4.setCellValue(userRolesList.size()); 
	        	inActiveCount = userRolesList.size();
			 }else{
				 if(inactiveList !=null){
//				 cell4.setCellValue(inactiveList.size()); 
					 inActiveCount = inactiveList.size();
				 }
			 }
	        if("NOT_LOGGEDIN".equalsIgnoreCase(userState)){
	        	notLoggedInCount = userRolesList.size(); 
			 }else{
				 if(notLoggedInList != null){
					 notLoggedInCount = notLoggedInList.size();
				 }
			 }
	        orderedList.add(new ListItem("Active :"+activeCount));
	        orderedList.add(new ListItem("Inactive :"+inActiveCount));
	        orderedList.add(new ListItem("Not Loggedin :"+notLoggedInCount));
	        document.add(orderedList);
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 5, 3,5, 5, 3,5,3,5,5,3});
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myColor = WebColors.getRGBColor("#33caff");
	            cell.setBackgroundColor(myColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        for(UserRole userRole : userRolesList){
	        	Set<Employees> employeeSet = userRole.getUser().getEmployee();
	        	String dateOfJoin = "";
	        	String rate = "";
	   		 for(Employees emp : employeeSet){
	   			 if(emp.getEmployeeId() !=null){
	   				table.addCell(emp.getEmployeeId());
	   			 }else{
	   				table.addCell("--");
	   			 }
	   			 if(emp.getHireDate() !=null){
	   				dateOfJoin = emp.getHireDateStr();
	   			 }else{
	   				dateOfJoin = "--";
	   			 }
	   			 if(emp.getRate() !=null && !"".equalsIgnoreCase(emp.getRate())){
	   				rate = emp.getRate()+"/"+emp.getRatePer();
	   			 }else{
	   				rate = "--";
	   			 }
	     	    } 
	   		 
	   		   if(userRole.getUser().getFirstName() !=null){
	   			table.addCell(userRole.getUser().getFirstName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getLastName() != null){
	   			table.addCell(userRole.getUser().getLastName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getEmail() !=null){
	   			table.addCell(userRole.getUser().getEmail());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getMobilePhone() != null){
	   			table.addCell(userRole.getUser().getMobilePhone());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   table.addCell(dateOfJoin);
	   		   table.addCell(rate);
	   		   if(userRole.getUser().getLocation() !=null){
	   			table.addCell(userRole.getUser().getLocation());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getUserType() != null){
	   			table.addCell(userRole.getUser().getUserType());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getReportingManager() !=null){
	   			table.addCell(userRole.getReportingManager().getFirstName());
	   		   }else{
	   			table.addCell("--");
	   		   }
	   		   if(userRole.getUser().getStatus() !=null){
	   			table.addCell(userRole.getUser().getStatus());
	   		   }else{
	   			table.addCell("--");
	   		   }
	        }
	        document.addTitle("Table Demo");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } finally {
	        document.close();
	    }
		return file;
	}

    

}
