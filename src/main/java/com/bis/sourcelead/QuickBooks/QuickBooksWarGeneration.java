package com.bis.sourcelead.QuickBooks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.csvreader.CsvWriter;
import com.google.gson.Gson;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class QuickBooksWarGeneration {
	
	private static String  quickBoxUrl= "https://quickbooks.api.intuit.com/v3/company/1403370680";
   
    private static  String consumerkey = "qyprd1RKnm3UHszMybazhwsOkEf6fH";
   
    private static  String consumerSecret = "j0quEaLf1iGM5G34jSVcMy72HMUnNT8gk5fhtIzC";
   
    private static  String accessToken = "lvprdk9tVc5xFkX82Mp2K3HpPNO5XLvnzlxJSCmAsbFtF2VR";
   
    private static  String accessSecret = "VzfNZO5am91xeQSDfsZzQpmdHaQY6qLNZQHtnTof";
    
    private static final String XLSX_FILE_PATH="QuickBookInvoice.xlsx";
    
    private static final String CSV_FILE_NAME="QuickBookInvoice.csv";
    
    public static void main(String[] args) throws IOException {
    	QuickBooksWarGeneration qb = new QuickBooksWarGeneration();
    	qb.quickBook(args);
    }

    public void quickBook(String[] args) throws IOException{
    	
    	  String uri = "";
    	  String fromDate = "";
    	  String toDate = "";
    	  Scanner fromDateScanner = null;
    	  Scanner toDateScanner = null;
    	  if(null != args && args.length >= 2){
    		  fromDate = args[0];
    		  toDate = args[1];
    	  }else{
    		  System.out.print("your not providing From date and To date by commandline inputs"+"\n");
    		  
    		  fromDateScanner = new Scanner(System.in);
        	  System.out.print("Enter From Date (YYYY-MM-DD) : ");
              fromDate = fromDateScanner.nextLine();
              
              toDateScanner = new Scanner(System.in);
        	  System.out.print("Enter To Date (YYYY-MM-DD) : ");
              toDate = toDateScanner.nextLine();
    	  }
    	
    	
    	if(null != fromDate && !"".equalsIgnoreCase(fromDate) && null != toDate && !"".equalsIgnoreCase(toDate)){
    		System.out.println("getting date range Records!!!!!!!!");
    		uri = quickBoxUrl+"/query?query=SELECT%20%2A%20FROM%20Invoice%20WHERE%20TxnDate%20%3E%3D%20%27"+fromDate+"%27%20AND%20TxnDate%20%3C%3D%20%27"+toDate+"%27%20%20ORDERBY%20TxnDate%20ASC%20MAXRESULTS%201000&minorversion=4";
    	}else if(((null == fromDate || "".equalsIgnoreCase(fromDate)) && (null != toDate && !"".equalsIgnoreCase(toDate))) 
    			|| ((null == toDate || "".equalsIgnoreCase(toDate)) && (null != fromDate && !"".equalsIgnoreCase(fromDate))) ){
    		System.out.println("Not a valid Input!");
    		System.exit(0);
    	}
    	else{
    		System.out.println("getting all Records!!!!!!!!");
    		uri = quickBoxUrl+"/query?query=SELECT%20%2A%20FROM%20Invoice%20STARTPOSITION%201%20MAXRESULTS%201000&minorversion=4";
    	}
    	
        String outPut = oAuthSignIn(uri);
        Gson gson = new Gson();
		MyPojo staff = gson.fromJson(outPut, MyPojo.class);
		QueryResponse queryResponse = staff.getQueryResponse();
		
		if(null != queryResponse){
			Invoice[] invoiceArray =  queryResponse.getInvoice();
			
			//Code for generate .xlsx file
			/*Workbook createExcelworkbook = new XSSFWorkbook();
	  		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	  		StringBuilder excelStringBuilder=new StringBuilder();
	  		Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append("QuickBook_Invoice").append("_").append(dateFormat.format(new Date())).toString());
	  		Row writeRow = null;
	  		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook);
	  		
	  		int column = 0;
			for (Invoice invoice : invoiceArray) {
	            writeRow = createExcelsheet.createRow(column + 1);
				createExcelsheet = createXlsSheetInvoice(createExcelsheet, writeRow, invoice);
				column++;
	         }
	       
	       //Write the workbook in file system
           FileOutputStream out = new FileOutputStream(new File(XLSX_FILE_PATH));
           createExcelworkbook.write(out);
           out.close();
	       */
		 //end Code for generate .xlsx file
			
			
			
			//Code for generate .CSV file
			CsvWriter csvOutput = new CsvWriter(new FileWriter(CSV_FILE_NAME, true), ',');
			//write out the header line
			csvOutput.write("SKU");
			csvOutput.write("Product/Service");
			csvOutput.write("Name");
			csvOutput.write("Num");
			csvOutput.write("Memo/Description");
			csvOutput.write("Date");
			csvOutput.write("Last Modified");
			csvOutput.write("Due date");
			csvOutput.write("Rate");
			csvOutput.write("Qty");
			csvOutput.write("Amount");
			csvOutput.write("Total Balance");
			csvOutput.write("A/R Paid");
			csvOutput.endRecord();
			for (Invoice invoice : invoiceArray) {
				createInvoiceCSVFile(invoice,csvOutput);
			}
			csvOutput.close();
			//end code for generate .CSV file
			
			
			
			if(null != fromDateScanner){
    			fromDateScanner.close();
    		}
    		if(null != toDateScanner){
    			toDateScanner.close();
    		}
           System.out.println("CSV file Created !!!!!!!!!");
		}else{
	    	   System.out.println("No records !");
	     }
    }
    
    
    private void createInvoiceCSVFile(Invoice invoice, CsvWriter csvOutput) throws IOException {
    	      //for SKU
    	       csvOutput.write(invoice.getId());
    			if (null != invoice.getLine() && invoice.getLine().length > 0) {
    					Line[] lines = invoice.getLine();
    					int count = 0;
    					for (Line line : lines) {
    						//for SKU
    						/*if (null != line && null != line.getSalesItemLineDetail() && null != line.getSalesItemLineDetail().getItemRef() && null != line.getSalesItemLineDetail().getItemRef().getValue() && !"".equalsIgnoreCase(line.getSalesItemLineDetail().getItemRef().getValue())) {
    								 String uri = quickBoxUrl+"/query?query=select%20%2A%20from%20Item%20where%20%20Id%20%3D%20%27"+line.getSalesItemLineDetail().getItemRef().getValue()+"%27&minorversion=4";
    								 String outPut = oAuthSignIn(uri);
    							     Gson gson = new Gson();
    							     MyPojo itemRef = gson.fromJson(outPut, MyPojo.class);
    							     QueryResponse queryResponse = itemRef.getQueryResponse();
    							     if(null != queryResponse){
    							    	 Item[] itemArray =  queryResponse.getItem();
    								     for (Item item : itemArray) {
    								    	csvOutput.write(item.getSku());
    								     }
    							     }
    						}*/
    						
    						//for Product/Service
    						if (count == 0 && null != line && null != line.getSalesItemLineDetail() && null != line.getSalesItemLineDetail().getItemRef() && null != line.getSalesItemLineDetail().getItemRef().getName() && !"".equalsIgnoreCase(line.getSalesItemLineDetail().getItemRef().getName())) {
    							csvOutput.write(line.getSalesItemLineDetail().getItemRef().getName());
    						}
    						count++;
    					}
    			}
    			
    			//for Name
    			if (null != invoice.getCustomerRef() && null != invoice.getCustomerRef().getName() && !"".equalsIgnoreCase(invoice.getCustomerRef().getName())) {
    				csvOutput.write(invoice.getCustomerRef().getName());
    			}
    			
    			//for Num
    			if (null != invoice.getDocNumber() && !"".equalsIgnoreCase(invoice.getDocNumber())) {
    				csvOutput.write(invoice.getDocNumber());
    			}
    			
    			if (null != invoice.getLine() && invoice.getLine().length > 0) {
					Line[] lines = invoice.getLine();
					int count = 0;
					for (Line line : lines) {
						//for Memo/Description
						if(count == 0 && null != line && null != line.getDescription()){
							csvOutput.write(line.getDescription());
						}
						count++;
					}
			    }
    			
    			//for Date
    			if (null != invoice.getTxnDate() && !"".equalsIgnoreCase(invoice.getTxnDate())) {
    				csvOutput.write(invoice.getTxnDate());
    			}
    			
    			//for Last Modified
    			if (null != invoice.getMetaData() && null != invoice.getMetaData().getLastUpdatedTime() && !"".equalsIgnoreCase(invoice.getMetaData().getLastUpdatedTime())) {
    				csvOutput.write(invoice.getMetaData().getLastUpdatedTime());
    			}
    			
    			//for Due Date
    			if (null != invoice.getDueDate() && !"".equalsIgnoreCase(invoice.getDueDate())) {
    				csvOutput.write(invoice.getDueDate());
    			}
    			
    			if (null != invoice.getLine() && invoice.getLine().length > 0) {
					Line[] lines = invoice.getLine();
					int count = 0;
					for (Line line : lines) {
						if(count == 0 && null != line && null != line.getSalesItemLineDetail()){
							//for Rate
							csvOutput.write(line.getSalesItemLineDetail().getUnitPrice());
							//for Qty
							csvOutput.write(line.getSalesItemLineDetail().getQty());
						}
						count++;
					}
			  }
    			

    			//for Amount
    			if(null != invoice.getTotalAmt() && !"".equalsIgnoreCase(invoice.getTotalAmt())){
    				csvOutput.write(invoice.getTotalAmt());
    			}
    			
    			//for Open Balance
    			if (null != invoice.getBalance() && !"".equalsIgnoreCase(invoice.getBalance())) {
    				csvOutput.write(invoice.getBalance());
    			}
    			
    			//for A/R Paid
    			if (null != invoice.getBalance() && !"".equalsIgnoreCase(invoice.getBalance()) && null != invoice.getBalance() && !"".equalsIgnoreCase(invoice.getBalance())) {
    				if(Float.parseFloat(invoice.getBalance()) == 0){
    					csvOutput.write("Paid");
    				}else if(Float.parseFloat(invoice.getTotalAmt()) - Float.parseFloat(invoice.getBalance()) > 0){
    					csvOutput.write("Partial");
    				}else if(Float.parseFloat(invoice.getTotalAmt()) - Float.parseFloat(invoice.getBalance()) == 0){
    					csvOutput.write("Unpaid");
    				}
    			}
    			csvOutput.endRecord();
	}

	private static String oAuthSignIn(String uri) throws ClientProtocolException, IOException {
        System.out.println("method calling!!!!!!!!!");
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerkey,consumerSecret);
        consumer.setTokenWithSecret(accessToken, accessSecret);
       
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("content-type", "application/json");
        httpget.setHeader("Accept","application/json");
        try {
            consumer.sign(httpget);
        } catch (OAuthMessageSignerException ex) {
           System.out.println(ex);
        } catch (OAuthExpectationFailedException ex) {
        	System.out.println(ex);
        } catch (OAuthCommunicationException ex) {
        	System.out.println(ex);
        }
        HttpResponse response = httpclient.execute(httpget);
       
        //logger.debug(response.getStatusLine().toString());
        InputStream inputStraem = response.getEntity().getContent();

        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStraem, writer, "UTF-8");
        String output = writer.toString();
        System.out.println("end method");
        return output;
    }


	private static Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook) {
		String headers[] = {"SKU","Product/Service","Name","Num","Memo/Description","Date","Last Modified","Due date","Rate","Qty","Amount","Total Balance","A/R Paid"};
		CellStyle style = getCellStyle(createExcelworkbook);
		Row excelHeader = createExcelsheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = excelHeader.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
		}
		return createExcelsheet ;
	}

	private static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setColor(HSSFColor.BLUE.index);
		style.setFont(font);
		return style;
	
	}
	
	private static Sheet createXlsSheetInvoice(Sheet createExcelsheet, Row writeRow, Invoice invoice) throws ClientProtocolException, IOException {
		
		//for SKU
		writeRow.createCell(0).setCellValue(invoice.getId());
		if (null != invoice.getLine() && invoice.getLine().length > 0) {
				Line[] lines = invoice.getLine();
				for (Line line : lines) {
					//for SKU
					/*if (null != line && null != line.getSalesItemLineDetail() && null != line.getSalesItemLineDetail().getItemRef() && null != line.getSalesItemLineDetail().getItemRef().getValue() && !"".equalsIgnoreCase(line.getSalesItemLineDetail().getItemRef().getValue())) {
							 String uri = quickBoxUrl+"/query?query=select%20%2A%20from%20Item%20where%20%20Id%20%3D%20%27"+line.getSalesItemLineDetail().getItemRef().getValue()+"%27&minorversion=4";
							 String outPut = oAuthSignIn(uri);
						     Gson gson = new Gson();
						     MyPojo itemRef = gson.fromJson(outPut, MyPojo.class);
						     QueryResponse queryResponse = itemRef.getQueryResponse();
						     if(null != queryResponse){
						    	 Item[] itemArray =  queryResponse.getItem();
							     for (Item item : itemArray) {
							    	 writeRow.createCell(0).setCellValue(item.getSku());
							     }
						     }
					}*/
					
					//for Product/Service
					if (null != line && null != line.getSalesItemLineDetail() && null != line.getSalesItemLineDetail().getItemRef() && null != line.getSalesItemLineDetail().getItemRef().getName() && !"".equalsIgnoreCase(line.getSalesItemLineDetail().getItemRef().getName())) {
						writeRow.createCell(1).setCellValue(line.getSalesItemLineDetail().getItemRef().getName());
					}
					
					
					//for Memo/Description
					if(null != line && null != line.getDescription()){
						writeRow.createCell(4).setCellValue(line.getDescription());
					}
					
					if(null != line && null != line.getSalesItemLineDetail()){
						//for Rate
						writeRow.createCell(8).setCellValue(line.getSalesItemLineDetail().getUnitPrice());
						//for Qty
						writeRow.createCell(9).setCellValue(line.getSalesItemLineDetail().getQty());
					}
					
				}
		}
		
		//for Name
		if (null != invoice.getCustomerRef() && null != invoice.getCustomerRef().getName() && !"".equalsIgnoreCase(invoice.getCustomerRef().getName())) {
			writeRow.createCell(2).setCellValue(invoice.getCustomerRef().getName());
		}
		
		//for Num
		if (null != invoice.getDocNumber() && !"".equalsIgnoreCase(invoice.getDocNumber())) {
			writeRow.createCell(3).setCellValue(invoice.getDocNumber());
		}
		
		//for Date
		if (null != invoice.getTxnDate() && !"".equalsIgnoreCase(invoice.getTxnDate())) {
			writeRow.createCell(5).setCellValue(invoice.getTxnDate());
		}
		
		//for Last Modified
		if (null != invoice.getMetaData() && null != invoice.getMetaData().getLastUpdatedTime() && !"".equalsIgnoreCase(invoice.getMetaData().getLastUpdatedTime())) {
			writeRow.createCell(6).setCellValue(invoice.getMetaData().getLastUpdatedTime());
		}
		
		//for Due Date
		if (null != invoice.getDueDate() && !"".equalsIgnoreCase(invoice.getDueDate())) {
			writeRow.createCell(7).setCellValue(invoice.getDueDate());
		}
		

		//for Amount
		if(null != invoice.getTotalAmt() && !"".equalsIgnoreCase(invoice.getTotalAmt())){
			writeRow.createCell(10).setCellValue(invoice.getTotalAmt());
		}
		
		//for Open Balance
		if (null != invoice.getBalance() && !"".equalsIgnoreCase(invoice.getBalance())) {
			writeRow.createCell(11).setCellValue(invoice.getBalance());
		}
		
		//for A/R Paid
		if (null != invoice.getBalance() && !"".equalsIgnoreCase(invoice.getBalance()) && null != invoice.getBalance() && !"".equalsIgnoreCase(invoice.getBalance())) {
			if(Float.parseFloat(invoice.getBalance()) == 0){
				writeRow.createCell(12).setCellValue("Paid");
			}else if(Float.parseFloat(invoice.getTotalAmt()) - Float.parseFloat(invoice.getBalance()) > 0){
				writeRow.createCell(12).setCellValue("Partial");
			}else if(Float.parseFloat(invoice.getTotalAmt()) - Float.parseFloat(invoice.getBalance()) == 0){
				writeRow.createCell(12).setCellValue("Unpaid");
			}
		}
		
		return createExcelsheet;
	}
}