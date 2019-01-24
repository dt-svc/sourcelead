package com.bis.sourcelead.payroll.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PayrollPdfUtil {
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	public File generateEmployeePayrollPayslipPdf(EmployeePayroll payroll, OrgLocations orgLocation, AssociateTax associateTax) throws Exception {
		
			StringBuilder sb = new StringBuilder();
			if(StringUtils.isNotBlank(payroll.getCandidate().getLastName())) {
				sb.append(payroll.getCandidate().getFirstName()).append(" ").append(payroll.getCandidate().getLastName());
	        }else {
	        	sb.append(payroll.getCandidate().getFirstName());
	        }
			sb.append("_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),payroll.getPayCheckDate())).append("_")
			.append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append(".pdf");
		  File file = new File(sb.toString());
		try {
			// YTD 
			
			//passing frequency year to calculate ytd
			Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			
	 	    YTDPayrollDTO ytdTaxes= payrollUtil.getEmployeeYTDPayroll(payroll.getCandidate(),orgLocation,"APPROVED",year);
			   Document document = new Document();
//		        file.getParentFile().mkdirs();
			        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			        document.open();
			        Font f3 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f,Font.NORMAL, BaseColor.BLACK);
			        Font f4 = new Font(Font.FontFamily.TIMES_ROMAN, 6.0f,Font.BOLD, BaseColor.BLACK);
			        Paragraph   bell=null;
			        PdfPTable table = new PdfPTable(2); // 3 columns.
			        table.setWidthPercentage(100); //Width 100%
			        table.setSpacingBefore(10f); //Space before table
			        //Set Column widths
			        float[] columnWidths = {1f, 1f};
			        table.setWidths(columnWidths);
			        String zipcode="";
			        if(StringUtils.isNotBlank(orgLocation.getZipCode())) {
			        	zipcode = orgLocation.getZipCode();
			        }
			        StringBuilder address = new StringBuilder();
			        String streetAddress = payroll.getPaysheetFrequency().getOrgLocation().getLocation().substring( 0, payroll.getPaysheetFrequency().getOrgLocation().getLocation().indexOf(","));
			        address.append(payroll.getPaysheetFrequency().getOrgLocation().getOrgLocationName()+"\n\n")
			         .append(streetAddress+"\n\n")
			        .append(payroll.getPaysheetFrequency().getOrgLocation().getCity()+"\n\n")
			        .append(payroll.getPaysheetFrequency().getOrgLocation().getState()+"\n\n").append(zipcode);
			        bell =new Paragraph(address.toString(),f3);
//			        bell =new Paragraph(payroll.getCandidate().getResidenceAddress(),f3);
			        PdfPCell cell1 = new PdfPCell(bell);
			        cell1.setBorderColor(BaseColor.BLACK);
			        cell1.setPaddingLeft(10);
			        cell1.setFixedHeight(50f);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        cell1.setVerticalAlignment(Element.ALIGN_LEFT);
			        bell = new Paragraph("PayGroup:"+" "+payroll.getPaysheetFrequency().getUserGroup().getGroupName()+"\n\n"+"Pay Begin Date:"+" "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), payroll.getPaysheetFrequency().getFrequencyStartDate()) +"\n\n"+
			                "Pay End Date:"+" "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),payroll.getPaysheetFrequency().getFrequencyEndDate())+"\n\n"+"Pay Check Date :"+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),payroll.getPayCheckDate()),f3);
			        PdfPCell cell2 = new PdfPCell(bell);
			        cell2.setBorderColor(BaseColor.BLACK);
			        cell2.setPaddingLeft(10);
			        cell2.setCalculatedHeight(10f);
			        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			        cell2.setVerticalAlignment(Element.ALIGN_LEFT);
			        table.addCell(cell1);
			        table.addCell(cell2);
			        document.add(table);
			        
			        PdfPTable table1 = new PdfPTable(3);
			        table1.setWidthPercentage(100); //Width 100%
			        table1.setSpacingBefore(10f); //Space before table
			        table1.setSpacingAfter(10f);
			        
			        float[] columnWidths1 = {1f,1f,1f};
			        table1.setWidths(columnWidths1);
			        
			        StringBuilder candidateAddress = new StringBuilder();
			        String candidateZipcode="";
			        if(StringUtils.isNotBlank(payroll.getCandidate().getResidenceZipCode())) {
			        	candidateZipcode = payroll.getCandidate().getResidenceZipCode();
			        }
			        if(StringUtils.isNotBlank(payroll.getCandidate().getLastName())) {
			        	candidateAddress.append("Emplyee Name : "+payroll.getCandidate().getFirstName()).append(" ").append(payroll.getCandidate().getLastName()+"\n\n");
			        }else {
			        	candidateAddress.append("Emplyee Name : "+payroll.getCandidate().getFirstName()+"\n\n");
			        }
			        String candidateStreetAddress = orgLocation.getLocation().substring( 0, orgLocation.getLocation().indexOf(","));
			        candidateAddress.append("Address : \n")
			        .append(candidateStreetAddress+"\n")
			        .append(payroll.getCandidate().getResidenceCity()+"\n")
			        .append(payroll.getCandidate().getResidenceState()+"\n")
			        .append(payroll.getCandidate().getResidenceCountry()+"\n")
			        .append(candidateZipcode);
			        bell =new Paragraph(candidateAddress.toString(),f3);
			        PdfPCell cell3 = new PdfPCell(new Paragraph(bell));
			        cell3.setBorderColor(BaseColor.BLACK);
			        cell3.setFixedHeight(50f);
			        cell3.setPaddingLeft(10);
			        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        cell3.setVerticalAlignment(Element.ALIGN_LEFT);
			        String empId = "--";
			        if(payroll.getEmployee() != null && payroll.getEmployee().getEmployeeId() != null) {
			        	empId = payroll.getEmployee().getEmployeeId();
			        }
			        String depositType = "";
			        if(payroll.getDepositType() != null){
			        	depositType = payroll.getDepositType(); 
			        }
			        bell =new Paragraph( "Employee ID:"+empId+"\n\n" +"Department:"+payroll.getEmployee().getDepartmentId().getDepartmentName()+" \n\n"+"Deposit Type : "+depositType ,f3);
			        PdfPCell cell4 = new PdfPCell(new Paragraph(bell));
			        cell4.setBorderColor(BaseColor.BLACK);
			        cell4.setPaddingLeft(10);
			        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        cell4.setVerticalAlignment(Element.ALIGN_LEFT);
			       // 
			        PdfPTable table5 = new PdfPTable(3);
			        table5.setWidthPercentage(100); //Width 100%
			        bell =new Paragraph( "TAX DATA: "+"      "+"FEDERAL "+"     "+"STATE",f4);
			        PdfPCell cell15 = new PdfPCell(new Paragraph(bell));
			        cell15.setBorderColor(BaseColor.BLACK);
			        cell15.setColspan(3);
			        cell4.setPaddingLeft(10);
		            cell15.setHorizontalAlignment(Element.ALIGN_LEFT);
		            cell15.setVerticalAlignment(Element.ALIGN_LEFT);
		                    
		            float[] columnWidths5 = {1f,1f,1f};
			        table5.setWidths(columnWidths5);
			        bell =new Paragraph( "Marital Status:\n"+"Allowance:\n"+"Additional WithHolding:\n",f3);
			        PdfPCell cell16 = new PdfPCell(new Paragraph(bell));
			        cell16.setBorderColor(BaseColor.BLACK);
			        cell16.setFixedHeight(60f);
			        cell16.setPaddingLeft(10);
			        cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
			        cell16.setVerticalAlignment(Element.ALIGN_LEFT);
			        if(associateTax == null) {
			        	associateTax = new AssociateTax();
			        	associateTax.setFederalTaxFilliingStatus("Single");
			        	associateTax.setFederalTaxAllowances("0");
			        	associateTax.setFederalTaxAdditionnalWithHolding("0");
			        	associateTax.setStateTaxFilliingStatus("Single");
			        	associateTax.setStateTaxAllowances("0");
			        	associateTax.setStateTaxAdditionnalWithHolding("0");
			        }
			        bell =new Paragraph(associateTax.getFederalTaxFilliingStatus()+ "\n\n"+associateTax.getFederalTaxAllowances()+"\n\n"+associateTax.getFederalTaxAdditionnalWithHolding()+"\n"+" ",f3);
			        PdfPCell cell17 = new PdfPCell(new Paragraph(bell));
			       cell17.setBorderColor(BaseColor.BLACK);
			       cell17.setFixedHeight(60f);
			       cell17.setPaddingLeft(10);
			       cell17.setHorizontalAlignment(Element.ALIGN_LEFT);
			       cell17.setVerticalAlignment(Element.ALIGN_LEFT);
			       String StateTaxFilliingStatus = "--", StateTaxAllowances = "--", StateTaxAdditionnalWithHolding = "--";
			       if(StringUtils.isNoneBlank(associateTax.getStateTaxFilliingStatus())) {
			    	   StateTaxFilliingStatus = associateTax.getStateTaxFilliingStatus();
			       }
			       if(StringUtils.isNoneBlank(associateTax.getStateTaxAllowances())) {
			    	   StateTaxAllowances = associateTax.getStateTaxAllowances();
			       }
			       if(StringUtils.isNoneBlank(associateTax.getStateTaxAdditionnalWithHolding())) {
			    	   StateTaxAdditionnalWithHolding = associateTax.getStateTaxAdditionnalWithHolding();
			       }
			       bell =new Paragraph(StateTaxFilliingStatus+ "\n\n"+StateTaxAllowances+"\n\n"+StateTaxAdditionnalWithHolding+"\n"+" ",f3);
			       PdfPCell cell18 = new PdfPCell(new Paragraph(bell));
			       cell18.setBorderColor(BaseColor.BLACK);
			       cell18.setPaddingLeft(10);
			       cell18.setFixedHeight(60f);
			       cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
			       cell18.setVerticalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell15);
			        table5.addCell(cell16);
			        table5.addCell(cell17);
			        table5.addCell(cell18);
			        //
			        table1.addCell(cell3);
			        table1.addCell(cell4);
			        table1.addCell(table5);
			        document.add(table1);
			        
			        PdfPTable table2 = new PdfPTable(2);
			        table2.setWidthPercentage(100); //Width 100%  
			        bell =new Paragraph( "HOURS AND EARNINGS",f4);
			        PdfPCell cell19 = new PdfPCell(new Paragraph(bell));
			        cell19.setBorderColor(BaseColor.BLACK);
			        cell19.setColspan(1);
		            cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell19.setVerticalAlignment(Element.ALIGN_CENTER);
		            bell =new Paragraph( "TAXES",f4);
		            PdfPCell cell20 = new PdfPCell(new Paragraph(bell));
			        cell20.setBorderColor(BaseColor.BLACK);
			        cell20.setColspan(1);
		            cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell20.setVerticalAlignment(Element.ALIGN_CENTER);
		            
		            PdfPTable cc = new PdfPTable(3);
		            PdfPCell cc1 = new PdfPCell(new Paragraph("Description",f4));
		            PdfPCell cc2 = new PdfPCell(new Paragraph("Current Earnings",f4));
		            PdfPCell cc3 = new PdfPCell(new Paragraph("YTD Earnings",f4));
		            PdfPCell c41 = new PdfPCell(new Paragraph("Regular",f4));
		            PdfPCell c51 = new PdfPCell(new Paragraph("$ "+String.valueOf(payroll.getGrossPay()),f3));
		            PdfPCell c61 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdGross().toString(),f3));
		            cc.addCell(cc1);
		            cc.addCell(cc2);
		            cc.addCell(cc3);
		            cc.addCell(c41);
		            cc.addCell(c51);
		            cc.addCell(c61);
		            
		            EmployeePayrollTax taxes = payroll.getUsPayrollTax().iterator().next();
		            PdfPTable ccx1 = new PdfPTable(3);
		            PdfPCell cc11 = new PdfPCell(new Paragraph("Description",f4));
		            PdfPCell cc21 = new PdfPCell(new Paragraph("Current",f4));
		            PdfPCell cc31 = new PdfPCell(new Paragraph("YTD",f4));
		            PdfPCell cc41 = new PdfPCell(new Paragraph("Federal",f4));
		            PdfPCell cc51 = new PdfPCell(new Paragraph("$ "+taxes.getFederalWithholding().toString(),f3));
		            PdfPCell cc61 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdFederal().toString(),f4));
		            PdfPCell cc6fica = new PdfPCell(new Paragraph("FICA",f4));
		            PdfPCell cc6ficaWith = new PdfPCell(new Paragraph("$ "+taxes.getFicaWithholding().toString(),f3));
		            PdfPCell cc6ficaYTD = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdFICA().toString(),f3));
		            PdfPCell medicare = new PdfPCell(new Paragraph("Medicare",f4));
		            PdfPCell medicareTax = new PdfPCell(new Paragraph("$ "+taxes.getMedicareWithholding().toString(),f3));
		            PdfPCell medicareYTD = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdMedicare().toString(),f4));
		            
		            PdfPCell county = new PdfPCell(new Paragraph("County",f4));
		            PdfPCell countyTax = new PdfPCell(new Paragraph("$ "+taxes.getCountyWithholding().toString(),f3));
		            PdfPCell countyYTD = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdCounty().toString(),f3));
		            
		            PdfPCell local = new PdfPCell(new Paragraph("Local tax",f4));
		            PdfPCell localTax = new PdfPCell(new Paragraph("$ "+taxes.getLocalTax().toString(),f3));
		            PdfPCell localYTD = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdlocaltax().toString(),f3));
		            
		            ccx1.addCell(cc11);
		            ccx1.addCell(cc21);
		            ccx1.addCell(cc31);
		            ccx1.addCell(cc41);
		            ccx1.addCell(cc51);
		            ccx1.addCell(cc61);
		            
		            ccx1.addCell(cc6fica);
		            ccx1.addCell(cc6ficaWith);
		            ccx1.addCell(cc6ficaYTD);
		            ccx1.addCell(medicare);
		            ccx1.addCell(medicareTax);
		            ccx1.addCell(medicareYTD);
		            ccx1.addCell(county);
		            ccx1.addCell(countyTax);
		            ccx1.addCell(countyYTD);
		            ccx1.addCell(local);
		            ccx1.addCell(localTax);
		            ccx1.addCell(localYTD);
		            
			        table2.addCell(cell19);
			        table2.addCell(cell20);
			        table2.addCell(cc);
			        table2.addCell(ccx1);
			        
			        document.add(table2);
			        
			        PdfPTable table3 = new PdfPTable(3);
			        table3.setWidthPercentage(100); //Width 100%
			        bell =new Paragraph( "PRE-TAXDEDUCTIONS",f4);
			        PdfPCell cell25 = new PdfPCell(new Paragraph(bell));
			        cell25.setBorderColor(BaseColor.BLACK);
			        cell25.setColspan(1);
			        cell25.setFixedHeight(20f);
		            cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
		            bell =new Paragraph( "POST-TAXDEDUCTIONS",f4);
		            PdfPCell cell26 = new PdfPCell(new Paragraph(bell));
			        cell26.setBorderColor(BaseColor.BLACK);
			        cell26.setColspan(1);
			        cell26.setFixedHeight(20f);
		            cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
		            bell =new Paragraph( "CONTRIBUTIONS",f4);
			        PdfPCell cell27 = new PdfPCell(new Paragraph(bell));
		            cell27.setBorderColor(BaseColor.BLACK);
			        cell27.setColspan(1);
			        cell27.setFixedHeight(20f);
		            cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
		            
		            // Pre tax Deduction table
		            PdfPTable preDedTable = new PdfPTable(3);
		            PdfPCell preDedTableCell1 = new PdfPCell(new Paragraph("Description",f4));
		            PdfPCell preDedTableCell2 = new PdfPCell(new Paragraph("Current",f4));
		            PdfPCell preDedTableCell3 = new PdfPCell(new Paragraph("YTD",f4));
		            preDedTable.addCell(preDedTableCell1);
		            preDedTable.addCell(preDedTableCell2);
		            preDedTable.addCell(preDedTableCell3);
		            
		            // post Tax Deduction Table
		            PdfPTable postDedTable = new PdfPTable(3);
		            PdfPCell postDedTableCell1 = new PdfPCell(new Paragraph("Description",f4));
		            PdfPCell postDedTableCell2 = new PdfPCell(new Paragraph("Current",f4));
		            PdfPCell postDedTableCell3 = new PdfPCell(new Paragraph("YTD",f4));
		            
		            postDedTable.addCell(postDedTableCell1);
		            postDedTable.addCell(postDedTableCell2);
		            postDedTable.addCell(postDedTableCell3);
		            
		            // Contribution Table
		            PdfPTable contribtionTable = new PdfPTable(3);
		            PdfPCell contribtionTableCell1 = new PdfPCell(new Paragraph("Description",f4));
		            PdfPCell contribtionTableCell2 = new PdfPCell(new Paragraph("Current",f4));
		            PdfPCell contribtionTableCell3 = new PdfPCell(new Paragraph("YTD",f4));
		            
		            contribtionTable.addCell(contribtionTableCell1);
		            contribtionTable.addCell(contribtionTableCell2);
		            contribtionTable.addCell(contribtionTableCell3);
		            
		            
		            List<DeductionDTO> dlist = payrollUtil.getDeductionData(payroll);
		            if(dlist != null) {
		            	 Map<String, Double> map = ytdTaxes.getYtdDedTypeMap();
		            	 List<DeductionDTO> preDedList = dlist.stream().filter(ded->"pretax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
		 			     List<DeductionDTO> postDedList = dlist.stream().filter(ded->"posttax".equalsIgnoreCase(ded.getDedTaxType())).collect(Collectors.toList());
		 			   if(preDedList != null && preDedList.size() >0) {
		 				  for(DeductionDTO preDed : preDedList) {
			 			    	PdfPCell p1 = new PdfPCell(new Paragraph("$ "+preDed.getDeductionType(),f4));
				  	            PdfPCell p2 = new PdfPCell(new Paragraph("$ "+String.valueOf(preDed.getPreDeductionAmount()),f3));
				  	            PdfPCell p3 = new PdfPCell(new Paragraph("$ "+map.get(preDed.getDeductionType()).toString(),f3));
				  	            preDedTable.addCell(p1);
				  	            preDedTable.addCell(p2);
				  	            preDedTable.addCell(p3); 
			 			     }
			 			       
		 			   }else {
		 				  PdfPCell p1 = new PdfPCell(new Paragraph("--",f4));
			  	            PdfPCell p2 = new PdfPCell(new Paragraph("--",f4));
			  	            PdfPCell p3 = new PdfPCell(new Paragraph("--",f4));
			  	            preDedTable.addCell(p1);
			  	            preDedTable.addCell(p2);
			  	            preDedTable.addCell(p3);
		 			   }
		 			    if(postDedList != null && postDedList.size() >0) {
		 			    	 for(DeductionDTO preDed : postDedList) {
		 	 			    	PdfPCell cp1 = new PdfPCell(new Paragraph("$ "+preDed.getDeductionType(),f4));
		 		  	            PdfPCell cp2 = new PdfPCell(new Paragraph("$ "+String.valueOf(preDed.getPostDeductionAmt()),f3));
		 		  	            PdfPCell cp3 = new PdfPCell(new Paragraph("$ "+map.get(preDed.getDeductionType()).toString(),f3));
		 		  	         postDedTable.addCell(cp1);
		 		  	      postDedTable.addCell(cp2);
		 		  	   postDedTable.addCell(cp3); 
		 	 			     } 	
		 			    }else {
		 			    	     PdfPCell cp1 = new PdfPCell(new Paragraph("--",f4));
		 		  	             PdfPCell cp2 = new PdfPCell(new Paragraph("--",f4));
		 		  	             PdfPCell cp3 = new PdfPCell(new Paragraph("--",f4));
		 		  	          postDedTable.addCell(cp1);
		 		  	       postDedTable.addCell(cp2);
		 		  	    postDedTable.addCell(cp3);	
		 			    }
		 			   
		 			     
		            }else {
		            	PdfPCell p1 = new PdfPCell(new Paragraph("--",f4));
		  	            PdfPCell p2 = new PdfPCell(new Paragraph("--",f4));
		  	            PdfPCell p3 = new PdfPCell(new Paragraph("--",f4));
		  	            preDedTable.addCell(p1);
		  	            preDedTable.addCell(p2);
		  	            preDedTable.addCell(p3);
		  	            
		  	            PdfPCell cp1 = new PdfPCell(new Paragraph("--",f4));
		  	            PdfPCell cp2 = new PdfPCell(new Paragraph("--",f4));
		  	            PdfPCell cp3 = new PdfPCell(new Paragraph("--",f4));
		  	            postDedTable.addCell(cp1);
		  	            postDedTable.addCell(cp2);
		  	            postDedTable.addCell(cp3);
		            }
		           
		            
		            List<ContributionDTO> contributionList = payrollUtil.getContributionData(payroll);
		            
		            if(contributionList != null && contributionList.size() >0) {
		            	 Map<String, Double> map = ytdTaxes.getYtdContributionTypeMap();
		            	for(ContributionDTO contributionDTO : contributionList) {
		            		PdfPCell cp1 = new PdfPCell(new Paragraph("$ "+contributionDTO.getContributionType(),f4));
		  	  	            PdfPCell cp2 = new PdfPCell(new Paragraph("$ "+String.valueOf(contributionDTO.getContributionAmt()),f4));
		  	  	            PdfPCell cp3 = new PdfPCell(new Paragraph("$ "+map.get(contributionDTO.getContributionType()).toString(),f4));
		  	  	            contribtionTable.addCell(cp1);
		  	  	            contribtionTable.addCell(cp2);
		  	  	            contribtionTable.addCell(cp3);
		            	}
		            	
		            }else {
		  	            PdfPCell cp1 = new PdfPCell(new Paragraph("--",f4));
		  	            PdfPCell cp2 = new PdfPCell(new Paragraph("--",f4));
		  	            PdfPCell cp3 = new PdfPCell(new Paragraph("--",f4));
		  	            contribtionTable.addCell(cp1);
		  	            contribtionTable.addCell(cp2);
		  	            contribtionTable.addCell(cp3);
		            } 
		          
			       table3.addCell(cell25);
			       table3.addCell(cell26);
			       table3.addCell(cell27);
			       table3.addCell(preDedTable);
			       table3.addCell(postDedTable);
			       table3.addCell(contribtionTable);
			        
			        document.add(table3);
			        
			       

			        
			        PdfPTable table4 = new PdfPTable(6); // 3 columns.
			        table4.setWidthPercentage(100); //Width 100%
			        table4.setSpacingBefore(10f); //Space before table
//			        table4.setWidths(columnWidths);
			        PdfPCell t4p1 = new PdfPCell(new Paragraph("Total gross",f4));
	  	            PdfPCell t4p2 = new PdfPCell(new Paragraph("Federal Tax Gross",f4));
	  	            PdfPCell t4p3 = new PdfPCell(new Paragraph("Fica Gross",f4));
	  	            PdfPCell t4p4 = new PdfPCell(new Paragraph("Total Taxes",f4));
		            PdfPCell t4p5 = new PdfPCell(new Paragraph("Total Deductions",f4));
		            PdfPCell t4p6 = new PdfPCell(new Paragraph("Net Pay",f4));
			        
		            table4.addCell(t4p1);
		            table4.addCell(t4p2);
		            table4.addCell(t4p3);
		            table4.addCell(t4p4);
		            table4.addCell(t4p5);
		            table4.addCell(t4p6);
		            
		            PdfPCell curt4p1 = new PdfPCell(new Paragraph("Current",f4));
	  	            PdfPCell curt4p2 = new PdfPCell(new Paragraph("$ "+taxes.getFederalWithholding().toString(),f3));
	  	            PdfPCell curt4p3 = new PdfPCell(new Paragraph("$ "+taxes.getFicaWithholding().toString(),f3));
	  	            PdfPCell curt4p4 = new PdfPCell(new Paragraph("$ "+payroll.getTaxes().toString(),f3));
		            PdfPCell curt4p5 = new PdfPCell(new Paragraph("$ "+payroll.getPreTaxDeduction().add(payroll.getPostTaxDeduction()).toString(),f3));
		            PdfPCell curt4p6 = new PdfPCell(new Paragraph("$ "+payroll.getNetPay().toString(),f3));
		            
		            table4.addCell(curt4p1);
		            table4.addCell(curt4p2);
		            table4.addCell(curt4p3);
		            table4.addCell(curt4p4);
		            table4.addCell(curt4p5);
		            table4.addCell(curt4p6);
		            
		            PdfPCell ytdt4p1 = new PdfPCell(new Paragraph("YTD",f4));
	  	            PdfPCell ytdt4p2 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdFederal().toString(),f3));
	  	            PdfPCell ytdt4p3 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdFICA().toString(),f3));
	  	            PdfPCell ytdt4p4 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdTaxes().toString(),f3));
		            PdfPCell ytdt4p5 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdDeduction().toString(),f3));
		            PdfPCell ytdt4p6 = new PdfPCell(new Paragraph("$ "+ytdTaxes.getYtdNet().toString(),f3));
		            
		            table4.addCell(ytdt4p1);
		            table4.addCell(ytdt4p2);
		            table4.addCell(ytdt4p3);
		            table4.addCell(ytdt4p4);
		            table4.addCell(ytdt4p5);
		            table4.addCell(ytdt4p6);
			        document.add(table4);
			        document.close();
			        writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 
			return file;
		    }

	
	}

