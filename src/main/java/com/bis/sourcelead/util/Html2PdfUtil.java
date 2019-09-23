package com.bis.sourcelead.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ContractOwnerHierarchy;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Component
public class Html2PdfUtil {
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private RequirementOffersService requirementOffersService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Value("${static.path}")
	private String staticPath;
	
	public void convertHtml2PdfUsingHtmlWorker(){
		try {
			//TODO replace the string with actual HTML content.
			String k = "<html><body> This is from iText PDF HTML Worker </body></html>";
			OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingHtmlWorker.pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();
			//TODO use the latest XMLWorkerHelper class in below method instead of deprectaed HTMLWorker class
			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));
			document.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertHtml2PdfUsingXMLWorker(){
		try {
			//TODO replace the string with actual HTML content.
		    String k = "<html><body> This is from iText PDF XML Worker   </body></html>";
		    OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingXMLWorker.pdf"));
			Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, file);
		    document.open();
		    InputStream is = new ByteArrayInputStream(k.getBytes());
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
		    document.close();
		    file.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public byte[] getPdfBytes(ContractsTemplate contractsTemplate, Contracts contracts, OrgLocations orgLocation, Org org) throws Exception{
		
		String templateContent = replacePlaceHolders(contractsTemplate.getTemplateContent(),contracts,orgLocation,org);
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(templateContent));
  	    document.close();
  	    byte[] pdfBytes = byteArray.toByteArray();
		return pdfBytes;
	}
	
	
	 private String replacePlaceHolders(String templateBody, Contracts contracts, OrgLocations orgLocation, Org org) throws Exception {
	        
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 String orgName = "";
		 if (!"Full time offer".equalsIgnoreCase(contracts.getContractType())){
			  orgName = contracts.getContractOrgLocationId().getOrgLocationName();
		 }
	        /*if(contracts.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getLocationId().getOrgLocationName();
	        }
	        else if(contracts.getOrgRelations().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getResponderLocationId().getOrgLocationName();
	        }*/
	        
	         Pattern logEntry = Pattern.compile("\\[(.*?)\\]");
	            Matcher matchPattern = logEntry.matcher(templateBody);
	            StringBuffer sb = new StringBuffer();
	            while(matchPattern.find()) {
	                if(matchPattern.group().equalsIgnoreCase("[Client Name]") || matchPattern.group().equalsIgnoreCase("[Vendor Name]")){
	                    matchPattern.appendReplacement(sb, orgName);
	                }else if(matchPattern.group().equalsIgnoreCase("[Client location]") || matchPattern.group().equalsIgnoreCase("[Vendor location]")){
	                	StringBuffer location = new StringBuffer();
	                	if(null != contracts.getContractOrgLocationId().getCountry()&& !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getCountry())){
	            			location.append(contracts.getContractOrgLocationId().getCountry());
	            		}else if(null != contracts.getContractOrgLocationId().getState()&& !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getState())){
	            			location.append(contracts.getContractOrgLocationId().getState());
	            		}else if(null != contracts.getContractOrgLocationId().getCity()&& !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getCity())){
	            			location.append(contracts.getContractOrgLocationId().getCity());
	            		}
	                    matchPattern.appendReplacement(sb, location.toString());    
	                }else if(matchPattern.group().equalsIgnoreCase("[Client Contact]") || matchPattern.group().equalsIgnoreCase("[Vendor Contact]")){
	                    if(contracts.getParentContractId() != null){
	                    	for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
	                    		if(Constants.PARALLEL.equalsIgnoreCase(coh.getHierarchyType().getValue())){
	                    			StringBuilder ownersb = new StringBuilder();
	                    			if(StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())){
	                    				ownersb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName());
	                    			}else{
	                    				ownersb.append(coh.getOwnerUserId().getFirstName());
	                    			}
	                    			matchPattern.appendReplacement(sb, ownersb.toString());
	                    			break;
	                    		}else if(Constants.SEQUENTIAL.equalsIgnoreCase(coh.getHierarchyType().getValue())){
	                    			if(coh.getLevel() == 2){
	                    				StringBuilder ownersb = new StringBuilder();
		                    			if(StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())){
		                    				ownersb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName());
		                    			}else{
		                    				ownersb.append(coh.getOwnerUserId().getFirstName());
		                    			}
		                    			matchPattern.appendReplacement(sb, ownersb.toString());
		                    			break;
	                    			}
	                    		}
	                    	}
	                    }else {
	                    	StringBuilder ownersb = new StringBuilder();
                			if(StringUtils.isNotBlank(contracts.getContactUser().getLastName())){
                				ownersb.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName());
                			}else{
                				ownersb.append(contracts.getContactUser().getFirstName());
                			}
                			matchPattern.appendReplacement(sb, ownersb.toString());
	                    }
	                	
	                	
	                	//matchPattern.appendReplacement(sb, contracts.getContactUser().getFirstName());
	                    
	                    
	                    
	                    
	                    
	                }else if(matchPattern.group().equalsIgnoreCase("[Company Location]")){
	                	StringBuffer location = new StringBuffer();
	                	if(null != contracts.getOrgLocationId().getCountry()&& !"".equalsIgnoreCase(contracts.getOrgLocationId().getCountry())){
	            			location.append(contracts.getOrgLocationId().getCountry());
	            		}else if(null != contracts.getOrgLocationId().getState()&& !"".equalsIgnoreCase(contracts.getOrgLocationId().getState())){
	            			location.append(contracts.getOrgLocationId().getState());
	            		}else if(null != contracts.getOrgLocationId().getCity()&& !"".equalsIgnoreCase(contracts.getOrgLocationId().getCity())){
	            			location.append(contracts.getOrgLocationId().getCity());
	            		}
	                    matchPattern.appendReplacement(sb, location.toString());    
	                }else if(matchPattern.group().equalsIgnoreCase("[Company Name]")){
	                    matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getOrgLocationName());
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company Address]")){
	                    if(null != contracts.getOrgLocationId().getOrg().getOrgAddress()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getOrg().getOrgAddress());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company City]")){
	                	if(null != contracts.getOrgLocationId().getCity()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getCity());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company State]")){
	                	if(null != contracts.getOrgLocationId().getState()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getState());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company Country]")){
	                    if(null != contracts.getOrgLocationId().getCountry()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getCountry());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Associate Name]") || matchPattern.group().equalsIgnoreCase("[Employee Name]")){
		            	if(null !=contracts.getRequirementSubmission() && null != contracts.getRequirementSubmission().getId()){
			            	RequirementSubmission reqSub = requirementSubmissionService.getRequirementSubmissionById(contracts.getRequirementSubmission().getId());
			            	if(null != reqSub.getCandidate().getLastName()){
								matchPattern.appendReplacement(sb, reqSub.getCandidate().getFirstName()+" "+reqSub.getCandidate().getLastName());	
							}else{
								matchPattern.appendReplacement(sb, reqSub.getCandidate().getFirstName());
							}
		            	}
		            	else{
		            		matchPattern.appendReplacement(sb,"");
		            	}
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Job Title]") || matchPattern.group().equalsIgnoreCase("[Requirement Title]")){
		            	if(null !=contracts.getRequirementSubmission() && null !=contracts.getRequirementSubmission().getRequirement().getId()){
				            	Requirement req = requirementService.getRequirementByRequirementId(contracts.getRequirementSubmission().getRequirement().getId());
			                    matchPattern.appendReplacement(sb, req.getJobTitle());
		            	}
		            	else{
		            		if(contracts.getParentContractId() != null) {
		            			Contracts con = contractsTemplateService.getContractById(contracts.getParentContractId().getId());
			            		Requirement req = requirementService.getRequirementByRequirementId(con.getRequirementSubmission().getRequirement().getId());
			                    matchPattern.appendReplacement(sb, req.getJobTitle());
		            		}
		            	}
	                }
	                // need to know about offer rate
		           else if(matchPattern.group().equalsIgnoreCase("[Offer Rate]")){
		        	   if(null !=contracts.getRequirementSubmission() && null !=contracts.getRequirementSubmission().getId()){
		        		   RequirementOffers requirementOffers =  requirementOffersService.getRequirementOfferSubmissionById(contracts.getRequirementSubmission().getId());
		        		   matchPattern.appendReplacement(sb,requirementOffers.getAmount());
		        	   }
		           }
		           else if(matchPattern.group().equalsIgnoreCase("[Rate Per]")){
	                    matchPattern.appendReplacement(sb, contracts.getBillingRatePer());
	                }
		           else if(matchPattern.group().equalsIgnoreCase("[Rate Type]")){
	                   matchPattern.appendReplacement(sb, contracts.getBillingRateType());
	                }
	                
		            else if(matchPattern.group().equalsIgnoreCase("[Billing Rate]")||matchPattern.group().equalsIgnoreCase("[Rate]") || matchPattern.group().equalsIgnoreCase("[Billing Amount]")){
		            	if(null !=contracts.getBillingAmount()){
		            		matchPattern.appendReplacement(sb, contracts.getBillingAmount());
		            	}
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Overtime]") || matchPattern.group().equalsIgnoreCase("[Overtime Hours]")){
		            	if(null !=contracts.getOverTimeHours()){
		            		matchPattern.appendReplacement(sb, contracts.getOverTimeHours());
		            	}else {
		            		matchPattern.appendReplacement(sb, "");
		            	}
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Payment Term]") || matchPattern.group().equalsIgnoreCase("[Payment Frequency]")){
		            	if(null !=contracts.getPaymentTerm()){
		            		matchPattern.appendReplacement(sb, contracts.getPaymentTerm());
		            	}else {
		            		matchPattern.appendReplacement(sb, "");
		            	}
	                }
	              
		    		 else if(matchPattern.group().equalsIgnoreCase("[Join Date]")){
		            	String currentTimeStr = Constants.TZ_DF.format(contracts.getJoinDate());  
		        		String startTimeUTC= CommonUtil.convertTimeZones("UTC",timezone.getTimeZoneId(), currentTimeStr);
		        		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		        		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
		                matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),convertedDate));
		            }
		            else if(matchPattern.group().equalsIgnoreCase("[Start Date]")){
		            	String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
	            		String startTimeUTC= CommonUtil.convertTimeZones("UTC",timezone.getTimeZoneId(), currentTimeStr);
	            		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	            		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
	                    matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),convertedDate));
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[End date]")){
		            	if(null != contracts.getEndDate()){
		            		String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
		            		String startTimeUTC= CommonUtil.convertTimeZones("UTC",timezone.getTimeZoneId(), currentTimeStr);
		            		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		            		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
		            		matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),convertedDate));
		            	}
		            	else{
		            		matchPattern.appendReplacement(sb,"");
		            	}
		            }  
		            else if(matchPattern.group().equalsIgnoreCase("[State Of Jurisdiction]")){
		            	if(null != contracts.getStateJurisdiction()){
		            		matchPattern.appendReplacement(sb, contracts.getStateJurisdiction());
		            	}
		            } 
		            else if(matchPattern.group().equalsIgnoreCase("[Work Location]")){
		            	if(null != contracts.getWorkLocation()){
		            		matchPattern.appendReplacement(sb, contracts.getWorkLocation());
		            	}
		            } 
		            else if(matchPattern.group().equalsIgnoreCase("[Billing Cycle]")){
		            	if(null != contracts.getInvoiceFrequency()){
		            		matchPattern.appendReplacement(sb, contracts.getInvoiceFrequency());
		            	}
		            } 
	        		else if(matchPattern.group().equalsIgnoreCase("[Employee Location]")){
		            	if(null != contracts.getRequirementSubmission().getId()){
			            	RequirementSubmission reqSub = requirementSubmissionService.getRequirementSubmissionById(contracts.getRequirementSubmission().getId());
			            	if(null != reqSub.getCandidate().getAddress()){
			            		matchPattern.appendReplacement(sb, reqSub.getCandidate().getAddress());
			            	}
			            	else{
			            		matchPattern.appendReplacement(sb,"");
			            	}
		            	}
		            	else{
		            		matchPattern.appendReplacement(sb,"");
		            	}
	                }
	            }
	            matchPattern.appendTail(sb);
	        return sb.toString();
	    }
	 
	 
	 public byte[] getOfferLetterPdfBytes(RequirementOffers requirementOffers) throws Exception {
			String templateContent = replaceOfferLetterPlaceHolders(requirementOffers);
	        
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	  	    Document document = new Document();
	  	    byte[] pdfBytes = null;
	  	    LetterHead letterHead =  requirementOffers.getOfferLetterTemplate().getLetterHeadId();
	  	    if (letterHead != null && StringUtils.isNotBlank(letterHead.getTemplateType())) {
	  	    	String name = "<div style='text-align: center;'>"+requirementOffers.getOfferLetterTemplate().getTemplateName()+"<br></br></div><br></br><br></br><br></br>";
				String totalBody = name+ requirementOffers.getOfferLetterTemplate().getTemplateContent();
				
	  	    	org.jsoup.nodes.Document doc = Jsoup.parse(totalBody);
			    doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);    
		        String finalString=doc.html();
		        
				Rectangle pageSize;
				if ("A4".equalsIgnoreCase(letterHead.getTemplateType())) {
					 pageSize = new Rectangle(PageSize.A4);
					 pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
					document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
							Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
							Float.parseFloat(letterHead.getPaddingBottom()));
					
				} else if ("Letter".equalsIgnoreCase(letterHead.getTemplateType())) {
					pageSize = new Rectangle(PageSize.LETTER);
					pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
					document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
							Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
							Float.parseFloat(letterHead.getPaddingBottom()));
				} else if ("A6".equalsIgnoreCase(letterHead.getTemplateType())) {
					pageSize = new Rectangle(PageSize.A6);
					pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
					document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
							Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
							Float.parseFloat(letterHead.getPaddingBottom()));
				} else if ("Legal".equalsIgnoreCase(letterHead.getTemplateType())) {
					pageSize = new Rectangle(PageSize.LEGAL);
					pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
					document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
							Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
							Float.parseFloat(letterHead.getPaddingBottom()));
				}
			
	  	       PdfWriter writer = PdfWriter.getInstance(document, byteArray);
		       document.open();
		    
		    
		       PdfPTable header = new PdfPTable(1);
		       header.setWidths(new int[]{2});
		       header.setTotalWidth(527);
		       header.setLockedWidth(true);
		       header.getDefaultCell().setFixedHeight(40);
		       header.getDefaultCell().setBorder(Rectangle.BOTTOM);
		       header.getDefaultCell().setBorderColor(BaseColor.BLUE);
		       header.addCell(getCell(letterHead));
		       document.add(header);
		       
		       Paragraph p = new Paragraph(letterHead.getHeaderText(),new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
		       p.setAlignment(2);
		       document.add(p);

		       PdfPTable footer = new PdfPTable(1);
		       // set defaults
				footer.setWidths(new int[]{2});
				footer.setTotalWidth(527);
				footer.setLockedWidth(true);
				footer.getDefaultCell().setFixedHeight(40);
				footer.getDefaultCell().setBorder(Rectangle.TOP);
				footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
		   	
				PdfPCell footerCell = new PdfPCell();
				Paragraph p1 = new Paragraph(letterHead.getFooterText(),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
				if("Left".equalsIgnoreCase(letterHead.getAlignFooterText())) {
			    	p1.setAlignment(Element.ALIGN_LEFT);
			    }else if("Right".equalsIgnoreCase(letterHead.getAlignFooterText())){
			    	p1.setAlignment(Element.ALIGN_RIGHT);
			    }else {
			    	p1.setAlignment(Element.ALIGN_CENTER);
			    }
						
				if(letterHead.getFooterBgColor() != null && letterHead.getFooterBgColor() != "") {
			    	BaseColor myColor = WebColors.getRGBColor(letterHead.getFooterBgColor());
			    	footerCell.setBackgroundColor(myColor);
			    }
				footerCell.addElement(p1);
		     	footer.addCell(footerCell).setPaddingTop(-4);
				
			   	PdfPCell totalPageCount = new PdfPCell();
			   	totalPageCount.setBorder(Rectangle.TOP);
			   	totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
			   	footer.addCell(totalPageCount);
			   	
			   	// write page
			   	PdfContentByte canvas = writer.getDirectContent();
			   	canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
			   	footer.writeSelectedRows(0, -1, 34, 50, canvas);
			   	canvas.endMarkedContentSequence();
			   	
			    HTMLWorker htmlWorker = new HTMLWorker(document);
		  	    htmlWorker.parse(new StringReader(templateContent));
		  	    
//			    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
			    document.close();
			    pdfBytes = byteArray.toByteArray();
	  	  }else {
	  		    PdfWriter.getInstance(document, byteArray);
		  	    document.open();
		  	    HTMLWorker htmlWorker = new HTMLWorker(document);
		  	    htmlWorker.parse(new StringReader(templateContent));
		  	    document.close();
	  	        pdfBytes = byteArray.toByteArray();
	  	  }
			return pdfBytes;
		}

		private String replaceOfferLetterPlaceHolders(RequirementOffers requirementOffers) throws Exception {
			Pattern logEntry = Pattern.compile("\\[(.*?)\\]");
	        Matcher matchPattern = logEntry.matcher(requirementOffers.getOfferLetterTemplate().getTemplateContent());
	        StringBuffer sb = new StringBuffer();
	        while(matchPattern.find()) {
	        	Requirement req = requirementOffers.getSubmissionId().getRequirement();
	            if(matchPattern.group().equalsIgnoreCase("[Job id]")){
	            	if(req.getJobId() != null) {
	            		matchPattern.appendReplacement(sb, req.getJobId());
	            	}else {
	            		matchPattern.appendReplacement(sb, "");
	            	}
	            }else if(matchPattern.group().equalsIgnoreCase("[Job title]")) {
	            	matchPattern.appendReplacement(sb, req.getJobTitle());
	            }else if(matchPattern.group().equalsIgnoreCase("[Applicant Name]")) {
	            	matchPattern.appendReplacement(sb, requirementOffers.getSubmissionId().getCandidate().getFullName());
	            }else if(matchPattern.group().equalsIgnoreCase("[Salary]")) {
	            	matchPattern.appendReplacement(sb, requirementOffers.getAmount());
	            }else if(matchPattern.group().equalsIgnoreCase("[Employment Type]")) {
	            	if(req.getJobType() != null) {
	            		matchPattern.appendReplacement(sb, req.getJobType());
	            	}else {
	            		matchPattern.appendReplacement(sb, "");
	            	}
	            }else if(matchPattern.group().equalsIgnoreCase("[Company Address]")) {
	            	if(req.getOrgLocationId().getLocation() != null) {
	            		matchPattern.appendReplacement(sb, req.getOrgLocationId().getLocation());
	            	}else {
	            		matchPattern.appendReplacement(sb, "");
	            	}
	            }else if(matchPattern.group().equalsIgnoreCase("[Date of Join]")) {
	            	matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(req.getOrgLocationId().getDateFormat(), requirementOffers.getDateOfJoining()));
	            }else if(matchPattern.group().equalsIgnoreCase("[Job Location]")) {
	            	List<String> locations  = requirementLocationsService.getRequirementLocationsStringListByRequirementId(req.getId());
	            	StringBuffer locationSb = new StringBuffer();
	            	for(String location :locations) {
	            		locationSb.append(location).append("<br>");
	            	}
	            	matchPattern.appendReplacement(sb, locationSb.toString());
	            }else if(matchPattern.group().equalsIgnoreCase("[Job Description]")) {
	            	if(req.getDescription() != null) {
	            		matchPattern.appendReplacement(sb, req.getDescription());
	            	}else {
	            		matchPattern.appendReplacement(sb, "");
	            	}

	            }else if(matchPattern.group().equalsIgnoreCase("[Offer Expire Date]")) {
	            	if(requirementOffers.getOfferExpireDate() != null) {
	            		matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(req.getOrgLocationId().getDateFormat(), requirementOffers.getOfferExpireDate()));
	            	}else {
	                	matchPattern.appendReplacement(sb, "");
	            	}
	            }
	        }
	        matchPattern.appendTail(sb);
	        return sb.toString();
		}
		private PdfPCell getCell(LetterHead letterHead) throws Exception
		{
			 PdfPCell cell = new PdfPCell();
			    
			    if(letterHead.getHeaderBgColor() != null && letterHead.getHeaderBgColor() != "") {
			    	BaseColor myColor = WebColors.getRGBColor(letterHead.getHeaderBgColor());
			    	cell.setBackgroundColor(myColor);
			    }
			    
			    String removedUrl = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
			    Image img1 = Image.getInstance(removedUrl+letterHead.getFileURL());
			    img1.setAbsolutePosition(50,50);
			    
			    if("Right".equalsIgnoreCase(letterHead.getAlignLogo())) {
			    	cell.setPaddingRight(0);
			    	cell.setPaddingLeft(476);
			    }else if("Left".equalsIgnoreCase(letterHead.getAlignLogo())){
			    	cell.setPaddingRight(476);
			    	cell.setPaddingLeft(0);
			    }else {
			    	cell.setPaddingRight(233);
			    	cell.setPaddingLeft(233);
			    }
		    	cell.addElement(img1);
			    return cell;  
			
			
		}
}
